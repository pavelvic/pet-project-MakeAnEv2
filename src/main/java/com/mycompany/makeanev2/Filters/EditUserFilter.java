package com.mycompany.makeanev2.Filters;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.UserGroup;
import com.mycompany.makeanev2.Utils.CheckPermission;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.DbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class EditUserFilter implements Filter {

    
  @Override
  public void init(FilterConfig fConfig) throws ServletException {
 
  }
  
  @Override
  public void destroy() {
 
  }
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {
      
      try {
          Connection con = DbConnection.getConnection();
          //подготовить список групп пользователей для формирования списка на странице
          List<UserGroup> userGroups = DbQuery.selectUserGroup(con);
          con.close();
          request.setAttribute("usergroups", userGroups);
          String test = (String) request.getParameter("id_user");
          //направить в нужную область сайти в зависимости от роли
//          String redirect = CheckPermission.getUsergroupRedirect(request,"editprofile.jsp");
//          request.setAttribute("redirUsergroup", redirect);
          chain.doFilter(request, response);
          
      } catch (SQLException | NamingException  ex) {
         String resultString = "Ошибка! " + ex.toString();
         request.setAttribute("resultString", resultString);
      }
      
      //маршрутизация: в зависимости от группы пользователей у залогинившегося пользователя отправляев в соотв область сайта
      
      
      

  }
}
