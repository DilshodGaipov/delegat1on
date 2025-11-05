package com.ddcorp.delegat1on.api;
import java.util.Date;
public class DelegationRecord {
  public long id;
  public String delegatorKey;
  public String delegateKey;
  public Date start;
  public Date end;
  public String category;
  public boolean disabled;
  public boolean isActiveAt(Date when){
    if (disabled) return false;
    if (when == null) when = new Date();
    boolean afterStart = (start == null) || !when.before(start);
    boolean beforeEnd = (end == null) || !when.after(end);
    return afterStart && beforeEnd;
  }
}
