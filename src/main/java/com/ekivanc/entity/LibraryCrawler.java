package com.ekivanc.entity;

import java.util.List;
import java.util.concurrent.Callable;

import com.ekivanc.parser.HtmlParser;
import com.ekivanc.parser.HtmlParserFactory;
import com.ekivanc.parser.ParserType;
import com.ekivanc.provider.PageSourceProvider;
import com.ekivanc.util.Constants;

/**
 * @author ekivanc
 * Callable Script Crawler will be used in parsing JS libraries
 */
public final class LibraryCrawler implements Callable<List<String>>  {


	
	private final String webPageUrl;
	
	/**
	 * 
	 * @param webPageUrl Page HTTP URL address
	 */
	public LibraryCrawler(String webPageUrl) {
		this.webPageUrl = webPageUrl;
	}

	@Override
	public List<String> call() throws Exception {
		HtmlParserFactory parserFactory = new HtmlParserFactory();
		HtmlParser scriptParser = parserFactory.create(ParserType.SCRIPT);
    	String pageSource = PageSourceProvider.populate().getPageSource(webPageUrl);
		return scriptParser.parse(pageSource, Constants.PATTERN_JS_PAGE_RESULT_URL_START.getValue(), Constants.PATTERN_JS_PAGE_RESULT_URL_END.getValue());
	}

}
