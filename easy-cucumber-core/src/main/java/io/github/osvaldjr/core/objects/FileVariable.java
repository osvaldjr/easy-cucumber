package io.github.osvaldjr.core.objects;

import java.util.HashMap;
import java.util.Map;

public class FileVariable {

  public static Map<String, String> value = new HashMap<>();

  public static void register(String keyVar, String valueVar) {
    value.put(keyVar, valueVar);
  }

  public static void clear() {
    value = new HashMap<>();
  }
}
