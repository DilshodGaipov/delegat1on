package com.ddcorp.delegat1on.api;
import java.util.*;
public interface ConfigService {
  List<String> getCategories();
  void addCategory(String name);
  void removeCategory(String name);
  Map<String, Boolean> getPermissions();
  void setPermissions(Map<String, Boolean> perms);
}
