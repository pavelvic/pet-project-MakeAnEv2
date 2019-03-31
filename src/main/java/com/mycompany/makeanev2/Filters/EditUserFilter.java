package com.mycompany.makeanev2.Filters;

import com.mycompany.makeanev2.UserGroup;
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
      //подготовить список групп пользователей для формирования списка на странице
      
      try {
          Connection con = DbConnection.getConnection();
          List<UserGroup> userGroups = DbQuery.selectUserGroup(con);
          request.setAttribute("usergroups", userGroups);
          
      } catch (SQLException | NamingException ex) {
         String resultString = "Ошибка! " + ex.getMessage();
         request.setAttribute("resultString", resultString);
      }
      
      
      
      chain.doFilter(request, response);
  }
}
