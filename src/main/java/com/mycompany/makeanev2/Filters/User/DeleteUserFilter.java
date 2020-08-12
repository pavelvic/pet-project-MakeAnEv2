package com.mycompany.makeanev2.Filters.User;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.UserDbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*фильтр для предобработки события удаления пользователя (при переходе в сервлет /deleteuser?id_user = ?) */
public class DeleteUserFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //определяем залогинишвегося из сессии
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User userInSession = AuthUtils.getLoginedUser(session);

        try {
            //получаем пользователя к которому запрашивается доступ
            Connection con = DbConnection.getConnection();
            String id_user = (String) request.getParameter("id_user"); //параметры Object, приводим к String
            User userToAccess = UserDbQuery.selectUser(con, Integer.parseInt(id_user));
            con.close();

            //проверяем полномочия (текущий пользователь должен иметь возможность удалять запрашиваемого, права определены группой пользователя)
            //если не проходим проверку, выдаем исключение UserException и код ниже не выполняется
            CheckPermission.checkDeleteUser(userInSession, userToAccess);

            //сохраняем в http-запрос пользоваеля, которого надо удалить, дальнейшая обработка в сервлете EditUserServlet
            request.setAttribute("user", userToAccess);

            //идем дальше
            chain.doFilter(request, response);

            //блок исключений, если что-то пошло не так, прервываем загрузку страницы и выдаем пользователю сообщение о проблеме    
        } catch (UserException | SQLException | NamingException | NumberFormatException | NullPointerException ex) {
            String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }
}
