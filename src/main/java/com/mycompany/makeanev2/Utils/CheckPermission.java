package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;

public class CheckPermission {

    public static void checkViewUserAcces(User userInSession, User userToAcces) throws UserException {
        //в методе проверяем кто какого пользователя хочет смотреть и разрешаем / заперщаем

        //проверим логин
        if (userInSession == null) {
            throw new UserException("Доступ запрещен. Войдите на сайт");
        }

        //проверим заблокирован ли пользователь
        if (userInSession.getGroup_id() == 5) {
            throw new UserException("Доступ запрещен. Ваш пользователь заблокирован");
        }

        //проверим куда пользователь хочет попасть (свой профиль смотреть можно, но админы могут смотреть любые профили)
        if (userInSession.getId_user() != userToAcces.getId_user()) {

            switch (userInSession.getGroup_id()) {
                case 3:
                    throw new UserException("Доступ запрещен. Попытка посмотреть данные чужого пользователя, полномочия отсутствуют");
                case 4:
                    throw new UserException("Доступ запрещен. Попытка посмотреть данные чужого пользователя, полномочия отсутствуют");
            }
        }
    }

    public static String getUserRedirect(String superUserRedir,
            String adminRedir,
            String managerRedir,
            String userRedir,
            User userInSession) throws UserException {

        String redirect = "/login.jsp";
        if (userInSession != null) {
            switch (userInSession.getGroup_id()) {
                case 1:
                    return superUserRedir;
                case 2:
                    return adminRedir;
                case 3:
                    return managerRedir;
                case 4:
                    return userRedir;
                case 5:
                    throw new UserException("Доступ запрещен. Пользователь заблокирован"); //Обработка блокировки
            }
        } else {
            throw new UserException("Доступ запрещен. Войдите на сайт");
        }
        return "/login.jsp"; //по дефолту идем на страницу регистрации
    }
}