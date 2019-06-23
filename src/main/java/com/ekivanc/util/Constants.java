package com.ekivanc.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Properties;

public enum Constants {

  GOOGLE_PAGE_URL,
  GOOGLE_SEARCH_RESULT_NUMBER,
  USER_AGENT_KEY,
  TIMEOUT_VALUE,
  PATTERN_GOOGLE_PAGE_RESULT_URL_START,
  PATTERN_GOOGLE_PAGE_RESULT_URL_END,
  PATTERN_JS_PAGE_RESULT_URL_START,
  PATTERN_JS_PAGE_RESULT_URL_END;


  private static final String PATH = "config.properties";
  private static Properties properties;
  private String value;

  private void init() {
    final Logger logger = LogManager.getLogger("Constants");
    if (properties == null) {
      properties = new Properties();
      try {
        properties.load(Constants.class.getClassLoader().getResourceAsStream(PATH));
      }
      catch (Exception e) {
        logger.error(e.getMessage());
        // also log console..
        // System.out.println("Unable to load " + PATH + " file from classpath.");
//        System.out.println(e.getStackTrace());
      }
    }
    value = (String) properties.get(this.toString());
  }

  public String getValue() {
    if (value == null) {
      init();
    }
    return value;
  }

}
