package com.ekivanc.parser;

public class HtmlParserFactory implements IHtmlParserFactory<HtmlParser> {

  @Override
  public HtmlParser create(ParserType parserType) {

    switch (parserType) {
      case GOOGLE:
          return new Parser4Google();
      case SCRIPT:
        return new Parser4JScript();
      default:
    	   return null;
    }
    
  }
}