package org.tcse.algorithms.learn.logparser.generator;

import java.util.HashMap;
import java.util.Map;

import org.tcse.algorithms.learn.logparser.HttpConstant;
import org.tcse.algorithms.learn.logparser.LogParserCallback;

public class LineGenerator implements LogParserCallback {
	private Map<String, String> properties = new HashMap<String, String>();
	private Map<String, String> queryParams = new HashMap<String, String>();
	private String domainName;
	private String port;

	public LineGenerator(String domainName, String port) {
		this.domainName = domainName;
		this.port = port;
	}

	public void setProperty(String key, String value) {
		this.properties.put(key, value);
	}

	public void parseQueryAndArguments(String text) {

	}

	public String generateBehavior() {
		String path = this.properties.get(HttpConstant.PATH);
		if (!(path.startsWith("http") || path.startsWith("https"))) {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			path = "http://" + this.domainName + ":" + this.port + path;
		}
		return "method," + this.properties.get(HttpConstant.METHOD)
				+ "; path ," + path;
	}

	public void cleanUp() {
		this.properties.clear();
		this.queryParams.clear();
	}
}
