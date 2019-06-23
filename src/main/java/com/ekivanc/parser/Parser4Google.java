package com.ekivanc.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * google parser library
 * @author ekivanc
 *
 */
public class Parser4Google implements HtmlParser {

  @Override
  public String parseMatchedString(String tmp) {
    int startIndex = tmp.indexOf("<a href=\"");
    String domainName = tmp.substring(startIndex+9);
    return domainName.substring(0,domainName.length()-1);
  }
}
