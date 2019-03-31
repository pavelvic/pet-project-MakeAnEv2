package com.mycompany.makeanev2.Filters;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ViewUserFilter implements Filter {

    
  @Override
  public void init(FilterConfig fConfig) throws ServletException {
 
  }
  
  @Override
  public void destroy() {
 
  }
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {
      //в фильтре делаем редирект на правильную область сайта в зависимости от роли
      HttpServletRequest req = (HttpServletRequest) request;
      HttpSession session = req.getSession(); 
      User userInSession = AuthUtils.getLoginedUser(session);
      
      //если не залогинены, идем на страницу логина
      if (userInSession == null) request.getRequestDispatcher("/login.jsp").forward(request, response);
      
      try {
          String redirect = CheckPermission.getUserRedirect(
                  "/WEB-INF/ownerview/viewuser.jsp", 
                  "/WEB-INF/adminview/viewuser.jsp", 
                  "/WEB-INF/managerview/viewuser.jsp", 
                  "/WEB-INF/userview/viewuser.jsp", 
                  userInSession);
          
          req.getSession().setAttribute("redirRole", redirect);
          chain.doFilter(request, response);
      } catch (UserException ex) {
              request.setAttribute("resultString", "Ошибка! "+ ex.toString());
              request.setAttribute("redirect", "/");
              request.getRequestDispatcher("/resultpage.jsp").forward(request, response);
      }
      
  }
}