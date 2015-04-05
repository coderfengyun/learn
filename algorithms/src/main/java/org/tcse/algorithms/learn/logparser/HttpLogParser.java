package org.tcse.algorithms.learn.logparser;

public interface HttpLogParser {
	public static HttpLogParser VOID_PARSER = new VoidHttpLogParser();

	/**
	 * close the any streams or readers.
	 */
	void close();

	/**
	 * the method will parse the given number of lines. Pass "-1" to parse the
	 * entire file. If the end of the file is reached without parsing a line, a
	 * 0 is returned. If the method is subsequently called again, it will
	 * restart parsing at the beginning.
	 *
	 * @param count
	 *            max lines to parse, or <code>-1</code> for the entire file
	 * @param el
	 *            {@link TestElement} to read lines into
	 * @return number of lines parsed
	 */
	int parseAndConfigure(int count, LogParserCallback callback);

	/**
	 * We allow for filters, so that users can simply point to an Access log
	 * without having to clean it up. This makes it significantly easier and
	 * reduces the amount of work. Plus I'm lazy, so going through a log file to
	 * clean it up is a bit tedious. One example of this is using the filter to
	 * exclude any log entry that has a 505 response code.
	 *
	 * @param filter
	 *            {@link Filter} to use
	 */
	void setFilter(Filter filter);

	/**
	 * The method is provided to make it easy to dynamically create new classes
	 * using Class.newInstance(). Then the access log file is set using this
	 * method.
	 *
	 * @param source
	 *            name of the access log file
	 */
	void setSourceFile(String source);

	public static class VoidHttpLogParser implements HttpLogParser {

		public void close() {
			throw new RuntimeException("Void Http Log Parser!");
		}

		public int parseAndConfigure(int count, LogParserCallback callback) {
			throw new RuntimeException("Void Http Log Parser!");
		}

		public void setFilter(Filter filter) {
			throw new RuntimeException("Void Http Log Parser!");
		}

		public void setSourceFile(String source) {
			throw new RuntimeException("Void Http Log Parser!");
		}

	}
}
