package com.ddcorp.delegat1on.workflow;
import com.opensymphony.workflow.Condition; import com.opensymphony.workflow.WorkflowContext; import com.opensymphony.module.propertyset.PropertySet; import java.util.Map;
import javax.inject.*; import javax.inject.Named; import com.atlassian.jira.issue.Issue; import com.atlassian.jira.user.ApplicationUser; import com.atlassian.jira.component.ComponentAccessor;
import com.ddcorp.delegat1on.api.*;
public class IsDelegateOfCondition implements Condition {
  @Inject @Named("delegationService") private DelegationService service;
  @Inject @Named("delegationConfig") private ConfigService cfg;
  @Override public boolean passesCondition(Map tv, Map args, PropertySet ps) {
    Object c=tv.get("context"); if(!(c instanceof WorkflowContext)) return false; String caller=((WorkflowContext)c).getCaller(); if(caller==null) return false;
    Issue issue=(Issue) tv.get("issue"); if(issue==null) return false;
    String field = args!=null?(String)args.get("field"):"assignee";
    if ("assignee".equalsIgnoreCase(field) && !cfg.getPermissions().getOrDefault("delegateAssignee", true)) return false;
    if ("reporter".equalsIgnoreCase(field) && !cfg.getPermissions().getOrDefault("delegateReporter", true)) return false;
    ApplicationUser delegator = "reporter".equalsIgnoreCase(field)?issue.getReporter():issue.getAssignee();
    if (delegator==null) return false;
    ApplicationUser delegate = ComponentAccessor.getUserManager().getUserByName(caller);
    if (delegate==null) return false;
    return service.isDelegateOf(delegate.getKey(), delegator.getKey(), new java.util.Date());
  }
}
