package org.tcse.algorithms.learn.logparser.generator;

import java.util.List;

public interface ScriptGeneratorFromLog {
	public void setLogFilePath(String path);

	public List<String> generateScript();
}
