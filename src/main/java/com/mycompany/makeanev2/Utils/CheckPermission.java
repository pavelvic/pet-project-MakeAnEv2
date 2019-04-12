package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;

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

    public static void checkEditUserAccess(User userInSession, User userToAccess) throws UserException {
        //проверим логин
        checkNotLogin(userInSession);

        //проверим заблокирован ли пользователь
        checkBlockUser(userInSession);

        if (userInSession.getId_user() != userToAccess.getId_user()) {

            switch (userInSession.getGroup_id()) {
                case 3:
                    throw new UserException("Доступ запрещен. Попытка доступа к данным чужого пользователя, полномочия отсутствуют");
                case 4:
                    throw new UserException("Доступ запрещен. Попытка доступа к данным чужого пользователя, полномочия отсутствуют");
            }
        }
    }

    public static void checkUserListAccess(User userInSession) throws UserException {
        //проверим логин
        checkNotLogin(userInSession);

        //проверим заблокирован ли пользователь
        checkBlockUser(userInSession);

        switch (userInSession.getGroup_id()) {
//            case 1:
//            case 2:
            case 3:
                throw new UserException("Доступ запрещён.");
            case 4:
                throw new UserException("Доступ запрещён.");
            case 5:
                throw new UserException("Доступ запрещён.");

        }

    }

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

}
