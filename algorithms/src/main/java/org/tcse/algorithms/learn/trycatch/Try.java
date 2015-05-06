package org.tcse.algorithms.learn.trycatch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.junit.Test;

public class Try {
	public void tryTry() throws FileNotFoundException {
		try {
			new FileInputStream("a.adf");
			System.out.println(1);
		} finally {
			System.out.println("execute the finally block!");
		}
	}

	@Test
	public void test() throws FileNotFoundException {
		tryTry();
	}
}
