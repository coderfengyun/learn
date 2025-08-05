
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
    
    def encode(self, text):
        return self.vocab.encode(text)

    def encode_iterable(self, iterable: Iterable[str]) -> Iterator[int]:
        for text in iterable:
            yield from self.encode(text)

    def decode(self, tokens):
        return self.vocab.decode(tokens)