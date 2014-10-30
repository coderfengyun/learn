package org.tcse.algorithms;

public class Singleton {
	private static final Singleton instance = new Singleton();

	public static Singleton getInstance() {
		return instance;
	}

	private Singleton() {
			System.out.println(Singleton.class.getClassLoader());
		}

	public static void main(String[] args) {
		getInstance();
	}

}
