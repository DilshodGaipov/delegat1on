package com.ddcorp.delegat1on.web;
import com.ddcorp.delegat1on.api.ConfigService; import javax.inject.*; import javax.servlet.http.*; import java.io.IOException; import java.util.*;
public class PermissionsServlet extends HttpServlet {
  @Inject @Named("delegationConfig") private ConfigService cfg;
  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Map<String,Boolean> p=new HashMap<>();
    p.put("delegateAssignee", "on".equals(req.getParameter("delegateAssignee")));
    p.put("delegateReporter", "on".equals(req.getParameter("delegateReporter")));
    p.put("postFunctionAssign", "on".equals(req.getParameter("postFunctionAssign")));
    cfg.setPermissions(p);
    resp.sendRedirect(req.getRequestURI());
  }
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html;charset=UTF-8");
    Map<String,Boolean> p=cfg.getPermissions();
    String cb = "<input type='checkbox' name='%s' %s/> %s";
    StringBuilder sb=new StringBuilder("<h2>Permissions</h2><form method='post'>");
    sb.append("<p>").append(String.format(cb,"delegateAssignee", p.getOrDefault("delegateAssignee",true)?"checked":"", "Allow delegate for Assignee")).append("</p>");
    sb.append("<p>").append(String.format(cb,"delegateReporter", p.getOrDefault("delegateReporter",true)?"checked":"", "Allow delegate for Reporter")).append("</p>");
    sb.append("<p>").append(String.format(cb,"postFunctionAssign", p.getOrDefault("postFunctionAssign",true)?"checked":"", "Allow Post-function to assign to delegate")).append("</p>");
    sb.append("<p><button type='submit'>Save</button></p></form>");
    resp.getWriter().println(sb.toString());
  }
}
