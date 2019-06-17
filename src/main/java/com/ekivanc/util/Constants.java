package com.ekivanc.util;

import java.util.Properties;

public enum Constants {
  GOOGLE_PAGE_URL,
  GOOGLE_SEARCH_RESULT_NUMBER,
  USER_AGENT_KEY,
  TIMEOUT_VALUE;

  private static final String PATH = "config.properties";
  private static Properties properties;
  private String value;

  private void init() {
    if (properties == null) {
      properties = new Properties();
      try {
        properties.load(Constants.class.getClassLoader().getResourceAsStream(PATH));
      }
      catch (Exception e) {
        System.out.println("Unable to load " + PATH + " file from classpath.");
        System.out.println(e.getStackTrace());
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
