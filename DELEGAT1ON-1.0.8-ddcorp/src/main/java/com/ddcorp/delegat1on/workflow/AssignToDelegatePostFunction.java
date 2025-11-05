package com.ddcorp.delegat1on.workflow;
import com.atlassian.jira.component.ComponentAccessor; import com.atlassian.jira.issue.MutableIssue; import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.workflow.function.issue.AbstractJiraFunctionProvider; import com.opensymphony.module.propertyset.PropertySet; import com.opensymphony.workflow.WorkflowException;
import java.util.Map; import javax.inject.*; import com.ddcorp.delegat1on.api.*;
public class AssignToDelegatePostFunction extends AbstractJiraFunctionProvider {
  @Inject @Named("delegationService") private DelegationService service;
  @Inject @Named("delegationConfig") private ConfigService cfg;
  @Override public void execute(Map tv, Map args, PropertySet ps) throws WorkflowException {
    if (!cfg.getPermissions().getOrDefault("postFunctionAssign", true)) return;
    MutableIssue issue=getIssue(tv); if(issue==null) return;
    String field=args!=null?(String)args.get("field"):"assignee";
    ApplicationUser delegator="reporter".equalsIgnoreCase(field)?issue.getReporter():issue.getAssignee();
    if (delegator==null) return;
    java.util.List<ApplicationUser> cands=service.activeDelegatesOf(delegator,new java.util.Date());
    if (cands.isEmpty()) return;
    issue.setAssignee(cands.get(0));
  }
}
