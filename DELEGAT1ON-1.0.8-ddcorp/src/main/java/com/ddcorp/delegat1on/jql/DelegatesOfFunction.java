package com.ddcorp.delegat1on.jql;
import com.atlassian.jira.JiraDataType; import com.atlassian.jira.component.ComponentAccessor; import com.atlassian.jira.jql.operand.QueryLiteral; import com.atlassian.jira.jql.query.QueryCreationContext; import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.query.clause.TerminalClause; import com.atlassian.query.operand.FunctionOperand; import com.atlassian.query.jql.function.JqlFunction; import com.atlassian.jira.util.*; import com.ddcorp.delegat1on.api.DelegationService; import javax.inject.*; import java.util.*;
public class DelegatesOfFunction implements JqlFunction {
  @Inject @Named("delegationService") private DelegationService service;
  @Override public String getFunctionName(){ return "delegatesOf"; }
  @Override public int getMinimumNumberOfExpectedArguments(){ return 1; }
  @Override public boolean isList(){ return true; }
  @Override public List<QueryLiteral> getValues(QueryCreationContext ctx, FunctionOperand op, TerminalClause clause){
    List<QueryLiteral> out=new ArrayList<>(); String who = op.getArgs().isEmpty()?null:op.getArgs().get(0); String category = op.getArgs().size()>1 ? op.getArgs().get(1) : null;
    ApplicationUser delegator = ("currentUser()".equalsIgnoreCase(who)||"currentUser".equalsIgnoreCase(who)) ? ctx.getUser() : ComponentAccessor.getUserManager().getUserByName(who);
    if (delegator==null) return out;
    Date now = new Date();
    // if category provided, filter records by category then map to users
    if (category != null && !category.trim().isEmpty()){
      for (com.ddcorp.delegat1on.api.DelegationRecord r : service.findActiveDelegationsForDelegator(delegator.getKey(), now)){
        if (category.equalsIgnoreCase(r.category)){
          ApplicationUser u = ComponentAccessor.getUserManager().getUserByKey(r.delegateKey);
          if (u!=null && u.getId()!=null) out.add(new QueryLiteral(op, u.getId()));
        }
      }
    } else {
      for (ApplicationUser u : service.activeDelegatesOf(delegator, now)){ if (u.getId()!=null) out.add(new QueryLiteral(op, u.getId())); }
    }
    return out;
  }
  @Override public MessageSet validate(ApplicationUser user, FunctionOperand op, TerminalClause clause){ return new MessageSetImpl(); }
  @Override public JiraDataType getDataType(){ return JiraDataType.USER; }
}
