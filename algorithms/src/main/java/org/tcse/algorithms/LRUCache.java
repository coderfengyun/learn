package org.tcse.algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import org.junit.Test;

public class LRUCache{
	@Test
	public void test() {
		Solution1 cache = new Solution1(2);
		cache.set(2, 1);
		cache.set(1, 1);
		cache.get(2);
		cache.set(4, 1);
		cache.get(1);
		cache.get(2);
	}
}
class Solution1 {
	private Map<Integer, ListIterator<Integer>> keyToIterator;
	private Map<Integer, Integer> valToKey;
	private LinkedList<Integer> list;
	private int capacity;


	public Solution1(int capacity) {
		this.capacity = capacity;
		list = new LinkedList<Integer>();
		this.keyToIterator = new HashMap<Integer, ListIterator<Integer>>();
		this.valToKey = new HashMap<Integer, Integer>(capacity);
	}

	public synchronized int get(int key) {
		if (!keyToIterator.containsKey(key)) {
			return -1;
		}
		ListIterator<Integer> valIterator = keyToIterator.get(key);
		int result = valIterator.next();
		valIterator.remove();
		this.list.addFirst(result);
		keyToIterator.put(key, this.list.listIterator(0));
		return result;
	}

	public synchronized void set(int key, int value) {
		if (keyToIterator.containsKey(key)) {
			ListIterator<Integer> itr = keyToIterator.get(key);
			this.valToKey.remove(itr.next());
			itr.remove();
		} else if (this.list.size() == this.capacity) {
			int tailKey = this.valToKey.get(this.list.removeLast());
			this.keyToIterator.remove(tailKey);
		}
		this.list.addFirst(value);
		this.keyToIterator.put(key, this.list.listIterator(0));
		this.valToKey.put(value, key);
	}

	
}
