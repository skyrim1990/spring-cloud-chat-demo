package com.tao.coreservice.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {
  public static List<Long> stringToIds(String ids) {
    log.info("--------------ids: " + ids);
    List<Long> result = new ArrayList<>();
    if(ids == null) {
      log.info("null");
      return result; 
    }
    if(ids.equals("")) {
      log.info("empty");
      return result;
    }
    if(ids.length() == 1) {
      log.info("single");
      result.add(Long.parseLong(ids));
    } else {
      log.info("multi");
      result = Arrays.asList(ids.split(",")).parallelStream().map((o) -> {
        log.info("map: " + o);
        return Long.valueOf(o);
      }).collect(Collectors.toList());
    }
    return result;
  }

  public static String idsToString(List<Long> ids) {
    if(ids == null) {
      return "";
    }
    if (ids.size() == 0) {
      return "";
    }
    if (ids.size() == 1) {
      return ids.get(0).toString();
    }
    return ids.stream().map((Long o) -> {
      return o.longValue() + "";
    }).reduce((String a, String b) -> {
      return a + "," + b;
    }).get();
  }

}
