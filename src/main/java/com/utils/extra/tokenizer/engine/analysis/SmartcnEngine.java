package com.utils.extra.tokenizer.engine.analysis;

import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;

/**
 * Lucene-smartcn分词引擎实现<br>
 * 项目地址：https://github.com/apache/lucene-solr/tree/master/lucene/analysis/smartcn
 * 
 * @author looly
 *
 */
public class SmartcnEngine extends AnalysisEngine {
	/**
	 * 构造
	 *
	 * @param analyzer 分析器{@link Analyzer}
	 */
	public SmartcnEngine(Analyzer analyzer) {
		super(analyzer);
	}

	/**
	 * 构造
	 */
	/*public SmartcnEngine() {

		//super(new SmartChineseAnalyzer());

	}*/
	
}
