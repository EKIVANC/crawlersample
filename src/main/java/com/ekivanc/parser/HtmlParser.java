package com.ekivanc.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface HtmlParser {


    default List<String> parse(String htmlDocument,String patternPageResultUrlStart, String patternPageResultUrlEnd) {
        List<String> domainNames = new ArrayList<>();
        Pattern pattern = Pattern.compile(patternPageResultUrlStart+ "(.*?)" +patternPageResultUrlEnd);
        Matcher matcher = pattern.matcher(htmlDocument);
        while(matcher.find()) {
            String tmp = matcher.group(0).trim();
            domainNames.add(this.parseMatchedString(tmp));
        }
        return domainNames;
    }


    String parseMatchedString(String tmp);
}
