package org.tcse.algorithms.learn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class TryAsychronize<T> {
	ExecutorService executorService = Executors.newFixedThreadPool(1);

	public Future<T> asychronizeExecute(Callable<T> callable) {
		return executorService.submit(callable);
	}

	@Test
	public void test() {
		Future<String> result = new TryAsychronize<String>().asychronizeExecute(new Callable<String>() {
			public String call() {
				try {
					char[] cBuffer = new char[1024];
					InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(
							"src/main/java/org/tcse/algorithms/Zigzag.java"));
					inputStreamReader.read(cBuffer);
					inputStreamReader.close();
					return new StringBuilder().append(cBuffer).toString();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
