package org.tcse.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumer {
	private static final Lock REENTRANT_LOCK = new ReentrantLock();
	private static Condition full = REENTRANT_LOCK.newCondition();
	private static Condition empty = REENTRANT_LOCK.newCondition();
	private static int[] buffer = new int[100];
	private static int size = 0;

	private static class Producer implements Runnable {

		public void run() {
			for (int i = 0; i < 20; i++) {
				addOne(1);
				Thread.yield();
			}
		}
	}

	private static class Consumer implements Runnable {

		public void run() {
			for (int i = 0; i < 20; i++) {
				removeOne();
			}
		}

	}

	static void addOne(int val) {
		REENTRANT_LOCK.lock();
		try {
			System.out.println("add one when size = " + size);
			while (size == 100) {
				full.await();
			}
			buffer[size++] = val;
			if (size == 1) {
				empty.signalAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			REENTRANT_LOCK.unlock();
		}
	}

	static int removeOne() {
		REENTRANT_LOCK.lock();
		try {
			System.out.println("remove one when size = " + size);
			while (size == 0) {
				empty.await();
			}
			int result = buffer[--size];
			if (size < buffer.length) {
				full.signalAll();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			REENTRANT_LOCK.unlock();
		}

	}

	public static void main(String[] args) {
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Producer());
			threads.add(thread);
			thread.start();
		}

		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Consumer());
			threads.add(thread);
			thread.start();
		}

		for (int i = 0; i < threads.size(); i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(size);
	}
}
