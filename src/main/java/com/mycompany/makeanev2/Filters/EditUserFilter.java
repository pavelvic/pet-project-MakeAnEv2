package com.mycompany.makeanev2.Filters;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.UserGroup;
import com.mycompany.makeanev2.Utils.AuthUtils;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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
          request.setAttribute("usergroups", userGroups);
          
          //далее
          //получаем пользователя к которому хотим получить доступ
          
            String id_user = (String) request.getParameter("id_user"); //параметры Object, приводим к String
            User userToAccess = DbQuery.selectUser(con, id_user);
            con.close();
            
          //определяем залогинишвегося
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();
            User userInSession = AuthUtils.getLoginedUser(session);
          
          
          //проверяем есть ли доступ до данному запросу
          CheckPermission.checkEditUserAccess(userInSession, userToAccess);
          
          
          //сохраняем в запрос самого пользователя для дальнейшего исопльзования
          request.setAttribute("user", userToAccess);
          
          
          //если все хорошо и исключений мы не получили, устанавливаем диспетчера для перехода в сервлете
            switch (userInSession.getGroup_id()) {
                case 1:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/ownerview/edituser.jsp"));
                    break;
                case 2:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/adminview/edituser.jsp"));
                    break;
                case 3:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/managerview/edituser.jsp"));
                    break;
                case 4:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/userview/edituser.jsp"));
                    break;
                case 5:
                    throw new UserException("Доступ запрещен. Пользователь заблокирован"); //на всякий случай...
                    
                default:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/login.jsp")); //на всякий случай...
            }
          
          //идем дальше
          chain.doFilter(request, response);
          
      } catch (SQLException | NamingException | UserException  ex) {
            String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/userlist"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
      }
  }
}
