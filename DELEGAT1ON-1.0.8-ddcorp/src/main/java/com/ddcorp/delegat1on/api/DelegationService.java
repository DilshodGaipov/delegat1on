package com.ddcorp.delegat1on.api;
import java.util.*; import com.atlassian.jira.user.ApplicationUser;
public interface DelegationService {
  List<DelegationRecord> all();
  DelegationRecord get(long id);
  DelegationRecord create(DelegationRecord rec);
  DelegationRecord update(DelegationRecord rec);
  boolean delete(long id);
  List<DelegationRecord> findActiveDelegationsForDelegator(String delegatorKey, Date when);
  List<DelegationRecord> findActiveDelegationsForDelegate(String delegateKey, Date when);
  boolean isDelegateOf(String delegateKey, String delegatorKey, Date when);
  List<ApplicationUser> activeDelegatesOf(ApplicationUser delegator, Date when);
  List<ApplicationUser> activeDelegatorsOf(ApplicationUser delegate, Date when);
}
