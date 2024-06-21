package com.example.client.radio86java;

import java.util.regex.Pattern;

public class Listing {

  private final String text;

  public Listing(String text) {
    if (text == null) {
      text = "";
    }
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public Language getLanguage() {
    if (Pattern.matches("^(?s)\\s*\\d+\\s+.*", text)) {
      return Language.BASIC;
    } else {
      return Language.JS;
    }
  }

}
