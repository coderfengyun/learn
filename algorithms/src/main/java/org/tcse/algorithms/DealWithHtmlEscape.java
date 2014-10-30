package org.tcse.algorithms;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DealWithHtmlEscape {
	private static int NORMAL_STATE = 0;
	private static int MAYBE_ESCAPE_END_STATE = 1;
	Map<String, String> map = new HashMap<String, String>();
	{
		map.put("&#34;", "\"");
		map.put("&quot;", "\"");
		map.put("&#38;", "&");
		map.put("&amp;", "&");
		map.put("&#60;", "<");
		map.put("&lt;", "<");
		map.put("&#62;", ">");
		map.put("&gt;", ">");
		map.put("&#160;", " ");
		map.put("&nbsp;", " ");
	}

	public String parse(String escapedContent) {
		if (escapedContent == null || escapedContent.length() == 0) {
			return escapedContent;
		}
		int state = NORMAL_STATE, endIndex = -1;
		// escaped content -> origin content

		StringBuilder result = new StringBuilder();
		for (int i = escapedContent.length() - 1; i >= 0; i--) {
			char ch = escapedContent.charAt(i);
			if (state == NORMAL_STATE) {
				if (ch == ';') {
					state = MAYBE_ESCAPE_END_STATE;
					endIndex = i;
				}
				result.append(ch);
			} else {
				if (ch == '&') {
					String suspiciousContent = escapedContent.substring(i,
							endIndex + 1);
					if (this.map.containsKey(suspiciousContent)) {
						result.delete(result.length() - (endIndex - i),
								result.length());
						result.append(new StringBuilder(this.map.get(suspiciousContent))
								.reverse());
					} else {
						state = NORMAL_STATE;
						result.append(ch);
					}
				} else if (ch == ';') {
					endIndex = i;
					result.append(ch);
				} else {
					result.append(ch);
				}
			}

		}
		return result.reverse().toString();
	}
	
	@Test
	public void test(){
		System.out.println(this.parse("&lt;&nbsp;&gt;"));
	}
}
