package com.kaiser.financ.controllers.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UrlUtils {

  private UrlUtils() {}

  public static String decodeParam(String s) {
    return URLDecoder.decode(s, StandardCharsets.UTF_8);
  }

  public static List<Integer> decodeIntList(String s) {
    String[] vet = s.split(",");
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < vet.length; i++) {
      list.add(Integer.parseInt(vet[i]));
    }
    return list;
  }
}
