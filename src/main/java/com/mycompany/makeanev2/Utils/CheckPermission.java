package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CheckPermission {

    //метод для проверки полномочий для страницы с просмотром / редактированием данных о пользователе
    public static void checkViewUserAccess(User userInSession, User userToAccess) throws UserException {
        //в методе проверяем кто какого пользователя хочет смотреть и разрешаем / заперщаем

        //проверим логин
        checkNotLogin(userInSession);

        //проверим заблокирован ли пользователь
        checkBlockUser(userInSession);

        //проверим куда пользователь хочет попасть (свой профиль смотреть можно, но админы могут смотреть любые профили)
        if (userInSession.getId_user() != userToAccess.getId_user()) {

            switch (userInSession.getGroup_id()) {
                case 3:
                    throw new UserException("Доступ запрещен. Попытка доступа к данным чужого пользователя, полномочия отсутствуют");
                case 4:
                    throw new UserException("Доступ запрещен. Попытка доступа к данным чужого пользователя, полномочия отсутствуют");
            }
        }
    }
    
    
    public static String getEditUserRedirect (User userInSession, User userToAccess) throws UserException {
                //проверим логин
        checkNotLogin(userInSession);

        //проверим заблокирован ли пользователь
        checkBlockUser(userInSession);
        
        
        if (userInSession != null) {
        switch (userInSession.getGroup_id()) {
            case 1: return "/WEB-INF/ownerview/editprofile.jsp";
            case 2: if (userInSession.getId_user() == userToAccess.getId_user()) {
                    return "/WEB-INF/adminview/editprofile.jsp";
                    } else {
                    return "/WEB-INF/adminview/editusergroup.jsp";
                    } 
            case 3: return "/WEB-INF/managerview/editprofile.jsp";
            case 4: return "/WEB-INF/userview/editprofile.jsp";
            case 5: throw new UserException("Доступ запрещен. Пользователь заблокирован");
        }
        } else {
            throw new UserException("Доступ запрещен. Залогиньтесь");    
        }
        return "/login.jsp";
     
    }
    
    //метод для просмотра

    //метод проверки при открытии страницы логина
    public static void checkLoginAccess(User userInSession) throws UserException {

        //если пользователь уже вошёл, передаем об этом сообщение и не пускаем на старницу логина
        if (userInSession != null) {
            throw new UserException("Вы уже вошли на сайт как " + userInSession.getUsername());
        }

    }

    //общий метод проверки при заблокирован ли пользователь
    public static void checkBlockUser(User userInSession) throws UserException {
        if (userInSession.getGroup_id() == 5) {
            throw new UserException("Доступ запрещен. Ваш пользователь заблокирован");
        }
    }

    //общий метод проверки залогинен ли пользователь
    public static void checkNotLogin(User userInSession) throws UserException {
        if (userInSession == null) {
            throw new UserException("Доступ запрещен. Войдите на сайт");
        }
    }

    //общий метод для редиректов на разные области сайтов в завсисмости от группы пользователя
    public static String getUsergroupRedirect(ServletRequest request, String page)
            throws UserException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User userInSession = AuthUtils.getLoginedUser(session);

        String redirect = "/index.jsp";
        if (userInSession != null) {
            switch (userInSession.getGroup_id()) {
                case 1:
                    ///WEB-INF/ownerview
                    return "/WEB-INF/ownerview/" + page;
                case 2:
                    ///WEB-INF/adminview
                    
                    
                    return "/WEB-INF/adminview/" + page;
                case 3:
                    ///WEB-INF/managerview
                    return "/WEB-INF/managerview/" + page;
                case 4:
                    ///WEB-INF/userview
                    return "/WEB-INF/userview/" + page;
                case 5:
                    throw new UserException("Доступ запрещен. Пользователь заблокирован"); //Обработка блокировки
            }
        } else {
            throw new UserException("Доступ запрещен. Залогиньтесь");
        }
        return "/index.jsp"; //по дефолту идем на главную страницу
    }
}
