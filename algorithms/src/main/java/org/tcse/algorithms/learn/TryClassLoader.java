package org.tcse.algorithms.learn;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

public class TryClassLoader {

	@Test
	public void test() {
		MyClassLoader myClassLoader = null;
		try {
			myClassLoader = new MyClassLoader(
					new URL[] { new URL(
							"/Users/chentienan/Desktop/Bench4Q/Bench4Q-Agent/target/classes/org/bench4q/agent") });
			try {
				Class<?> class1 = myClassLoader
						.findClass("org.bench4q.agent.AgentServer");
				System.out.println(class1.getName());
				Object agentServer = class1.getConstructors()[0].newInstance();
				assertNotNull(agentServer);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} finally {
			try {
				myClassLoader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static class MyClassLoader extends URLClassLoader {
		private static final String packageName = "org.bench4q.agent";

		public MyClassLoader(URL[] classPath) {
			super(classPath);
		}

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			Class<?> aClass = findLoadedClass(name);
			if (aClass != null) {
				return aClass;
			}
			if (!packageName.startsWith(name)) {
				return super.loadClass(name);
			} else {
				return findClass(name);
			}
		}

	}

	@Test
	public void testPathClassLoader() {
		PathClassLoader pathClassLoader = new PathClassLoader(
				"/Users/chentienan/Desktop/Bench4Q/Bench4Q-Agent/target/classes/");
		try {
			Class<?> class1 = pathClassLoader
					.findClass("org.bench4q.agent.AgentServer");
			System.out.println(class1.getName());
			Object agentServer = class1.getConstructors()[0].newInstance();
			assertNotNull(agentServer);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private static class PathClassLoader extends ClassLoader {
		private String classPath;

		public PathClassLoader(String classPath) {
			this.classPath = classPath;
		}

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			byte[] classData = getData(name);
			if (classData == null) {
				return super.findClass(name);
			} else {
				return defineClass(name, classData, 0, classData.length);
			}
		}

		private byte[] getData(String name) {
			String path = classPath + java.io.File.separatorChar
					+ name.replace('.', java.io.File.separatorChar) + ".class";
			ByteArrayOutputStream out = null;
			try {
				InputStream inputStream = new FileInputStream(path);
				out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int num = -1;
				while ((num = inputStream.read(buffer)) != -1) {
					out.write(buffer, 0, num);
				}
				inputStream.close();
				return out.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
