package org.tcse.algorithms.logparser.generator;

import java.util.List;

import org.junit.Test;
import org.tcse.algorithms.learn.logparser.generator.Bench4QScriptGenerator;

public class Test_Bench4QScriptGenerator {
	@Test
	public void testGenerateScript() {
		Bench4QScriptGenerator generator = new Bench4QScriptGenerator(
				"org.tcse.algorithms.learn.logparser.TomcatLogParser",
				"localhost", "8080");
		generator
				.setLogFilePath("C:\\Users\\tienan\\Documents\\GitHub\\learn\\algorithms\\Case\\LogParser\\localhost_access_log.2015-01-07.txt");
		List<String> result = generator.generateScript();
		for (String content : result) {
			System.out.println(content);
		}
	}
}
