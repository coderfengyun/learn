package org.tcse.algorithms.learn;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.junit.Test;

public class ByteArrayAndString {
	StringBuffer stringBuffer = new StringBuffer();
	char[] buffer = new char[1024];

	@Test
	public void test_ByteToChar() {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(
					"src/main/java/org/tcse/algorithms/Zigzag.java");
			while (fileReader.read(buffer) > 0) {
				stringBuffer.append(buffer);
			}
			System.out.println(stringBuffer.toString());
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void test_ByteToChar_ByInputStreamReader_With_ISO() {
		try {
			//ISO-8859-1是不会损失内容的
			StringBuffer stringBuffer = readFileContentToString(
					"Chinese/hanoi.txt", "ISO-8859-1");
			String content = stringBuffer.toString();
			System.out.println(content);
			stringBuffer.delete(0, stringBuffer.length());
			InputStreamReader inputStreamReader = new InputStreamReader(
					new ByteArrayInputStream(content.getBytes(Charset
							.forName("ISO-8859-1"))), "UTF-8");
			while (inputStreamReader.read(buffer) > 0) {
				stringBuffer.append(buffer);
			}
			System.out.println(stringBuffer.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private StringBuffer readFileContentToString(String filePath, String charSet)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		InputStreamReader inputStreamReader;
		StringBuffer stringBuffer = new StringBuffer();
		inputStreamReader = new InputStreamReader(
				new FileInputStream(filePath), charSet);
		while (inputStreamReader.read(buffer) > 0) {
			stringBuffer.append(buffer);
		}
		inputStreamReader.close();
		return stringBuffer;
	}

	@Test
	public void test_Byte2Char2Byte_WithGBK() {
		try {
			//按照GBK会损失一部分utf-8的标记位
			StringBuffer stringBuffer = readFileContentToString(
					"Chinese/hanoi.txt", "GBK");
			String contentString = stringBuffer.toString();
			System.out.println(contentString);
			stringBuffer.delete(0, stringBuffer.length());
			InputStreamReader inputStreamReader = new InputStreamReader(
					new ByteArrayInputStream(contentString.getBytes(Charset
							.forName("GBK"))), "UTF-8");
			while (inputStreamReader.read(buffer) > 0) {
				stringBuffer.append(buffer);
			}
			inputStreamReader.close();
			System.out.println(stringBuffer.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
