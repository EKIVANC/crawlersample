package com.ekivanc.parser;

import java.util.List;

public interface HtmlParser {
    List<String> parse(String htmlDocument);
}
