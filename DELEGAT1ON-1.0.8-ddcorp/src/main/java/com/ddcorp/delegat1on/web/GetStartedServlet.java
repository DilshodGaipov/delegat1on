package com.ddcorp.delegat1on.web;
import javax.servlet.http.*; import java.io.IOException;
public class GetStartedServlet extends HttpServlet {
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html;charset=UTF-8");
    resp.getWriter().println("<html><head><title>DELEGAT1ON — Get Started</title>"
      + "<link rel=\"stylesheet\" href=\"/download/resources/com.ddcorp.delegat1on:delegat1on-res/delegat1on.css\"></head><body class='dd-docs'>"
      + "<h1>DELEGAT1ON — Get Started</h1><ul>"
      + "<li><a href='/plugins/servlet/delegat1on/category'>Categories</a></li>"
      + "<li><a href='/plugins/servlet/delegat1on/permissions'>Permissions</a></li>"
      + "<li><a href='/plugins/servlet/delegat1on/configforadmin'>Delegations (Admin)</a></li>"
      + "<li><a href='/plugins/servlet/delegat1on/configforuser'>Delegations (User)</a></li>"
      + "<li><a href='/plugins/servlet/delegat1on/docs'>Documentation</a></li>"
      + "</ul></body></html>");
  }
}
