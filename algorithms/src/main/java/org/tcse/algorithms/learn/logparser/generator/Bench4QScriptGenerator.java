package org.tcse.algorithms.learn.logparser.generator;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.tcse.algorithms.learn.logparser.HttpLogParser;

public class Bench4QScriptGenerator implements ScriptGeneratorFromLog {
	private Logger logger = Logger.getLogger(Bench4QScriptGenerator.class);
	private List<String> scriptContent = new LinkedList<String>();
	private HttpLogParser logParser;
	private final String domainName;
	private final String port;

	public Bench4QScriptGenerator(String logParserClassName, String domainName,
			String port) {
		this.domainName = domainName;
		this.port = port;
		try {
			this.logParser = (HttpLogParser) Class.forName(logParserClassName)
					.newInstance();
		} catch (Exception e) {
			this.logger.error(e, e);
			this.logParser = HttpLogParser.VOID_PARSER;
		}
	}

	public void setLogFilePath(String path) {
		this.logParser.setSourceFile(path);
	}

	public List<String> generateScript() {
		LineGenerator lineGenerator = new LineGenerator(this.domainName,
				this.port);
		while (this.logParser.parseAndConfigure(1, lineGenerator) > 0) {
			String behavior = lineGenerator.generateBehavior();
			addToScript(behavior);
			lineGenerator.cleanUp();
		}
		return this.scriptContent;
	}

	private void addToScript(String behavior) {
		this.scriptContent.add(behavior);
	}

}
