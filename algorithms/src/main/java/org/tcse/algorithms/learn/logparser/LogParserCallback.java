package org.tcse.algorithms.learn.logparser;

public interface LogParserCallback {
	void setProperty(String key, String value);

	void parseQueryAndArguments(String text);
}
