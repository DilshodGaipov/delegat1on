package com.ddcorp.delegat1on.web;
import javax.servlet.http.*; import java.io.IOException;
public class UserServlet extends HttpServlet {
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html;charset=UTF-8");
    resp.getWriter().println("<h2>Delegations (User)</h2><p>Personal settings placeholder.</p>");
  }
}
