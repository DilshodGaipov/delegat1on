package com.ddcorp.delegat1on.web;
import com.ddcorp.delegat1on.api.ConfigService; import javax.inject.*; import javax.servlet.http.*; import java.io.IOException; import java.util.*;
public class CategoryServlet extends HttpServlet {
  @Inject @Named("delegationConfig") private ConfigService cfg;
  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String action=req.getParameter("action"); String name=req.getParameter("name");
    if ("add".equals(action)) cfg.addCategory(name);
    else if ("del".equals(action)) cfg.removeCategory(name);
    resp.sendRedirect(req.getRequestURI());
  }
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html;charset=UTF-8");
    List<String> cats=cfg.getCategories();
    StringBuilder sb=new StringBuilder("<h2>Categories</h2><form method='post'><input type='hidden' name='action' value='add'/>");
    sb.append("<p><input name='name' placeholder='Category name'/> <button type='submit'>Add</button></p></form>");
    sb.append("<ul>");
    for(String c: cats){
      sb.append("<li>").append(c).append(" <form method='post' style='display:inline'><input type='hidden' name='action' value='del'/>")
        .append("<input type='hidden' name='name' value='").append(c).append("'/><button>Delete</button></form></li>");
    }
    sb.append("</ul>");
    resp.getWriter().println(sb.toString());
  }
}
