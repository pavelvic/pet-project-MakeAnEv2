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


public class UserListFilter implements Filter {

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
        
                  //определяем залогинишвегося
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();
            User userInSession = AuthUtils.getLoginedUser(session);
          
          
        try {
            //проверяем есть ли доступ до данному запросу
            CheckPermission.checkUserListAccess(userInSession);
            
            //если всё хорошо, определяем диспетчера для дальнейшего использования в сервлете
            switch (userInSession.getGroup_id()) {
                case 1:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/ownerview/userlist.jsp"));
                    break;
                case 2:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/adminview/userlist.jsp"));
                    break;
                case 3:
                    throw new UserException("Доступ запрещен. Недостаточно полномочий Менежера");
                case 4:
                    throw new UserException("Доступ запрещен. Недостаточно полномочий Пользователя");
                case 5:
                    throw new UserException("Доступ запрещен. Пользователь заблокирован"); //на всякий случай...
                    
                default:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/login.jsp")); //на всякий случай...
            }
          //идем дальше
          chain.doFilter(request, response);
          
        } catch (UserException ex) {
            String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }

    }    
}