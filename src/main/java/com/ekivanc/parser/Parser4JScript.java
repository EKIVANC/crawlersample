package com.ekivanc.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser4JScript implements HtmlParser {

  final String patternPageResultUrlStart ="/";
  final String patternPageResultUrlEnd =".\\.js";

  @Override
  public List<String> parse(String htmlDocument) {

    Pattern pattern = Pattern.compile(patternPageResultUrlStart+ "(.*?)" +patternPageResultUrlEnd);
    Matcher matcher = pattern.matcher(htmlDocument);

    ArrayList<String> retVal = new ArrayList<>();

    while(matcher.find()) {
      String tmp = matcher.group(0).trim();
      int lastIndexedSlash = tmp.lastIndexOf("/");
      retVal.add(tmp.substring(lastIndexedSlash+1));
    }

    return retVal;
  }

}
