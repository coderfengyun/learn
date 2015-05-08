package org.tcse.algorithms.java;

public class InnerStaticClass {
	private static InnerStaticClass instance = new InnerStaticClass();

	public static InnerStaticClass getSingleton() {
		return instance;
	}

	public void hah() {
		System.out.println("hah");
	}

	public static class B {
		private static InnerStaticClass outterInstance = InnerStaticClass
				.getSingleton();

		public void bilibili() {
			outterInstance.hah();
			System.out.println("bilibili");
		}

	}
}
