package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthUtils {
    private static final String ATT_NAME_USER_NAME_COOKIE = "UserNameCookieAttribute";
    //сохранить пользователя в сессию
    public static void storeLoginedUser(HttpSession session, User loginedUser) {
        session.setAttribute("loginedUser", loginedUser);
    }
    
    public static void deleteLoginedUser(HttpSession session) {
        session.removeAttribute("loginedUser");
    }

    //получить пользователя из сессии
    public static User getLoginedUser(HttpSession session) {
        User loginedUser = (User) session.getAttribute("loginedUser");
        return loginedUser;
    }

    //сохранить пользователя в cookies
    public static void storeLoginedUserCookie(HttpServletResponse response, User user) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME_COOKIE, user.getUsername());
        cookieUserName.setMaxAge(24 * 60 * 60); //один день куки живет в секундах
        response.addCookie(cookieUserName);
    }

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
    
    public static void deleteLoginedUserCookie(HttpServletResponse response, User user) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME_COOKIE, user.getUsername());
        cookieUserName.setMaxAge(0); //этот куки недействителен после создания
        response.addCookie(cookieUserName);
    }

}
