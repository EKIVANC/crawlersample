package com.ekivanc.parser;

public interface IHtmlParserFactory<T> {
  T create(ParserType parserType) ;
}
