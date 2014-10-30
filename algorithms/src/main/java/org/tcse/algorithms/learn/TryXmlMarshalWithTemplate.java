package org.tcse.algorithms.learn;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Test;

public class TryXmlMarshalWithTemplate {
	@Test
	public void test() throws JAXBException {
		Template<Integer> o = new Template<Integer>();
		o.t = 12;
		JAXBContext.newInstance(Template.class, Integer.class)
				.createMarshaller().marshal(o, System.out);
	}

	@XmlRootElement
	@XmlAccessorType(value = XmlAccessType.PROPERTY)
	public static class Template<T> {
		String name;
		int age;
		T t;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public T getT() {
			return t;
		}

		public void setT(T t) {
			this.t = t;
		}

	}
}
