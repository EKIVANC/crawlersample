package com.ekivanc.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.ekivanc.util.Constants;

public final class PageSourceProvider {


    private PageSourceProvider() {}

    private static final String USER_AGENT_NAME = "User-Agent";
    // private static final String CHAR_SET = "UTF-8";
    /**
     * Provides the source code of given page as String
     * @param url the HTTP URL of page
     * @return page source
     * @throws IOException
     */
    public String getPageSource(String url) throws IOException {

        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty(USER_AGENT_NAME, Constants.USER_AGENT_KEY.getValue());
        connection.setConnectTimeout(Integer.parseInt(Constants.TIMEOUT_VALUE.getValue()));
        connection.setReadTimeout(Integer.parseInt(Constants.TIMEOUT_VALUE.getValue()));
        connection.connect();
        BufferedReader reader  = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8  ));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return  sb.toString();
    }

    public static PageSourceProvider populate() {
        return new PageSourceProvider();
    }
}

