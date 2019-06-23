package com.ekivanc.parser;

public class Parser4JScript implements HtmlParser {


  @Override
  public String parseMatchedString(String tmp) {
    int lastIndexedSlash = tmp.lastIndexOf('/');
    return  tmp.substring(lastIndexedSlash+1);
  }

}
