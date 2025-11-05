package com.ddcorp.delegat1on.web;
import com.ddcorp.delegat1on.api.*; import javax.inject.*; import javax.servlet.http.*; import java.io.IOException; import java.text.SimpleDateFormat; import java.util.*;
public class AdminServlet extends HttpServlet {
  @Inject @Named("delegationService") private DelegationService service;
  private Date parse(String s){ try{ return (s==null||s.isEmpty())?null:new SimpleDateFormat("yyyy-MM-dd").parse(s); }catch(Exception e){ return null; } }
  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String action = req.getParameter("action");
    if ("create".equals(action)){
      DelegationRecord r = new DelegationRecord();
      r.delegatorKey = req.getParameter("delegatorKey");
      r.delegateKey  = req.getParameter("delegateKey");
      r.start = parse(req.getParameter("start"));
      r.end   = parse(req.getParameter("end"));
      r.category = req.getParameter("category");
      r.disabled = "on".equals(req.getParameter("disabled"));
      service.create(r);
    } else if ("delete".equals(action)){
      try { long id = Long.parseLong(req.getParameter("id")); service.delete(id); } catch(Exception ignore){}
    }
    resp.sendRedirect(req.getRequestURI());
  }
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html;charset=UTF-8");
    List<DelegationRecord> list = service.all(); Date now=new Date();
    StringBuilder sb = new StringBuilder();
    sb.append("<h2>Delegations (Admin)</h2>");
    sb.append("<form method='post'><input type='hidden' name='action' value='create'/>");
    sb.append("<p><b>Create:</b> DelegatorKey <input name='delegatorKey'/> → DelegateKey <input name='delegateKey'/> ");
    sb.append("Start <input name='start' type='date'/> End <input name='end' type='date'/> ");
    sb.append("Category <input name='category'/> Disabled <input type='checkbox' name='disabled'/> ");
    sb.append("<button type='submit'>Add</button></p></form>");
    sb.append("<table border='1' cellspacing='0' cellpadding='4'><tr><th>ID</th><th>Delegator</th><th>Delegate</th><th>Start</th><th>End</th><th>Category</th><th>Disabled</th><th>Active now</th><th>Action</th></tr>");
    for (DelegationRecord r: list){
      boolean active = r.isActiveAt(now);
      sb.append("<tr><td>").append(r.id).append("</td><td>").append(r.delegatorKey).append("</td><td>").append(r.delegateKey)
        .append("</td><td>").append(r.start).append("</td><td>").append(r.end).append("</td><td>").append(r.category)
        .append("</td><td>").append(r.disabled).append("</td><td>").append(active).append("</td>")
        .append("<td><form method='post' style='display:inline'><input type='hidden' name='action' value='delete'/>")
        .append("<input type='hidden' name='id' value='").append(r.id).append("'/><button>Delete</button></form></td></tr>");
    }
    sb.append("</table>");
    resp.getWriter().println(sb.toString());
  }
}
