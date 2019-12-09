package com.utils.extra.tokenizer.engine.analysis;

import com.utils.extra.tokenizer.Result;
import com.utils.extra.tokenizer.TokenizerEngine;
import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;

/**
 * Lucene-analysis分词抽象封装<br>
 * 项目地址：https://github.com/apache/lucene-solr/tree/master/lucene/analysis
 * 
 * @author looly
 *
 */
public class AnalysisEngine implements TokenizerEngine {

	private Analyzer analyzer;
	
	/**
	 * 构造
	 * 
	 * @param analyzer 分析器{@link Analyzer}
	 */
	public AnalysisEngine(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	@Override
	public Result parse(CharSequence text) {/*
		TokenStream stream;
		try {
			stream =null ;// analyzer.tokenStream("text", StrUtil.str(text));
			//stream.reset();
		} catch (IOException e) {
			throw new TokenizerException(e);
		}
		return new AnalysisResult(stream);
	}*/
		return null;
	}

}
