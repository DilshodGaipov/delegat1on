package com.ddcorp.delegat1on.web;
import com.atlassian.templaterenderer.TemplateRenderer; import javax.inject.Inject; import javax.servlet.http.*; import java.io.IOException; import java.util.HashMap;
public class DocsServlet extends HttpServlet {
  private final TemplateRenderer renderer; @Inject public DocsServlet(TemplateRenderer renderer){ this.renderer = renderer; }
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException { resp.setContentType("text/html;charset=UTF-8"); renderer.render("templates/docs.vm", new HashMap<String,Object>(), resp.getWriter()); }
}
