package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*класс для работы с аутентификацией пользователя*/
public class AuthUtils {

    private static final String ATT_NAME_USER_NAME_COOKIE = "UserNameCookieAttribute";

    //сохранить пользователя в сессию (login)
    public static void storeLoginedUser(HttpSession session, User loginedUser) {
        session.setAttribute("loginedUser", loginedUser);
    }

    //удалить из сессии (logout)
    public static void deleteLoginedUser(HttpSession session) {
        session.removeAttribute("loginedUser");
    }

    //получить пользователя из сессии (как объект User)
    public static User getLoginedUser(HttpSession session) {
        User loginedUser = (User) session.getAttribute("loginedUser");
        return loginedUser;
    }

    //обновить пользователя в сессии + куках (удаляем сохраненного, добавляем указанного)
    public static void refreshLoginedUser(HttpServletRequest request, HttpServletResponse response, User userToUpdate) {
        //сначала обновляем данные куки, если есть
        if (AuthUtils.getLoginedUserCookie(request) != null) {
            AuthUtils.deleteLoginedUserCookie(response, AuthUtils.getLoginedUser(request.getSession()));
            AuthUtils.storeLoginedUserCookie(response, userToUpdate);
        }

        //потом данные самой session    
        AuthUtils.deleteLoginedUser(request.getSession());
        AuthUtils.storeLoginedUser(request.getSession(), userToUpdate);
    }

    //сохранить пользователя в cookies
    public static void storeLoginedUserCookie(HttpServletResponse response, User user) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME_COOKIE, user.getUsername());
        cookieUserName.setMaxAge(24 * 60 * 60); //один день куки живет (в секундах)
        response.addCookie(cookieUserName);
    }

    //получить пользователя из cookies
    public static String getLoginedUserCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME_COOKIE.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    //удалить cookies для пользователя
    public static void deleteLoginedUserCookie(HttpServletResponse response, User user) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME_COOKIE, user.getUsername());
        cookieUserName.setMaxAge(0); //этот куки недействителен после создания
        response.addCookie(cookieUserName);
    }

}
