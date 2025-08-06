
from collections.abc import Iterable
import json
import os
from typing import Iterator


class BPETokenizer:
    def __init__(self, vocab: dict[int, bytes], merges: list[tuple[bytes, bytes]], special_tokens: list[str] | None = None): 
        self.vocab = vocab
        self.merges = merges
        self.special_tokens = special_tokens

    @classmethod
    def from_files(cls, vocab_filepath: str | os.PathLike, merges_filepath: str | os.PathLike, special_tokens: list[str] | None = None):
        with open(vocab_filepath, 'r') as f:
            vocab = json.load(f)
        with open(merges_filepath, 'r') as f:
            merges = json.load(f)
        return cls(vocab, merges, special_tokens)
    
    encode = lambda self, text: self.vocab.encode(text)

    def encode_iterable(self, iterable: Iterable[str]) -> Iterator[int]:
        for text in iterable:
            yield from self.encode(text)

    decode = lambda self, tokens: self.vocab.decode(tokens)

def train(text: str, vocab_size: int):
        assert vocab_size >= 256
        num_merges = vocab_size - 256

        GPT2_SPLIT_PATTERN = r"""'(?:[sdmt]|ll|ve|re)| ?\p{L}+| ?\p{N}+| ?[^\s\p{L}\p{N}]+|\s+(?!\S)|\s+"""
        text_chunks: list[str] = re.findall(GPT2_SPLIT_PATTERN, text)

        # preprocessing
        ids: list[list[int]] = [list(ch.encode("utf-8")) for ch in text_chunks]
        merges: dict[tuple[int, int], int] = {}
        vocab = {idx: bytes([idx]) for idx in range(256)}
        # iteratively merge the most frequent pair
        for i in range(num_merges):
            stats: dict[tuple[int, int], int] = {}
            for chunk_ids in ids:
                stats = cal_pair_freq(chunk_ids, stats)
            
            # find the most frequent pair
            most_freqent_pair, freq = max(stats.items(), key=lambda x: x[1])
            idx = vocab_size + i

            # replace the pair with the new token
            merges[idx] = most_freqent_pair
            ids = [replace_pair(chunk_ids, most_freqent_pair, idx) for chunk_ids in ids]
        return merges, vocab

def replace_pair(chunk_ids: list[int], pair: tuple[int, int], idx: int) -> list[int]:
    new_ids: list[int] = []
    i = 0
    while i < len(chunk_ids):
        if chunk_ids[i] == pair[0] and i + 1 < len(chunk_ids) and chunk_ids[i + 1] == pair[1]:
            new_ids.append(idx)
            i += 2
        else:
            new_ids.append(chunk_ids[i])
            i += 1
    return new_ids

def cal_pair_freq(chunk_ids: list[int], stats: dict[tuple[int, int], int]) -> dict[tuple[int, int], int]:
    for i in range(len(chunk_ids) - 1):
        pair = (chunk_ids[i], chunk_ids[i + 1])
        stats[pair] = stats.get(pair, 0) + 1
    return stats

def merge_pair(pair: tuple[int, int], vocab: dict[int, bytes], merges: list[tuple[bytes, bytes]]) -> tuple[dict[int, bytes], list[tuple[bytes, bytes]]]:
    vocab[len(vocab)] = pair[0]
    merges.append(pair)