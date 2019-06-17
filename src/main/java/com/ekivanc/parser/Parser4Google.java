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

    private final String patternPageResultUrlStart ="<div class=\"rc\"><div class=\"r\"><a href=\"";
    private final String patternPageResultUrlEnd ="\"";

  @Override
  public List<String> parse(String htmlDocument) {
    List<String> domainNames = new ArrayList<>();
    Pattern pattern = Pattern.compile(patternPageResultUrlStart+ "(.*?)" +patternPageResultUrlEnd);
    Matcher matcher = pattern.matcher(htmlDocument);
    while(matcher.find()) {
      String tmp = matcher.group(0).trim();
      int startIndex = tmp.indexOf("<a href=\"");
      String domainName = tmp.substring(startIndex+9);
      domainNames.add(domainName.substring(0,domainName.length()-1));
    }
    return domainNames;
  }
}
