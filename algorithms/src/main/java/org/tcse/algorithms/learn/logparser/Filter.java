package org.tcse.algorithms.learn.logparser;

public interface Filter {

	boolean isFiltered(String line, LogParserCallback callback);

	String filter(String cleanedLine);

}
