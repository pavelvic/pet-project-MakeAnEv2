package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.Exceptions.EventException;
import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;

/*класс проверок полномочий на доступ к данным*/
public class CheckPermission {

    //метод для проверки полномочий для страницы с просмотром / редактированием данных о пользователе
    public static void checkViewUserAccess(User userInSession, User userToAccess) throws UserException {
        //в методе проверяем кто какого пользователя хочет смотреть и разрешаем / заперщаем в зависимости от группы пользователя
        // 1 - суперпользователь, 2 - администратор, 3 - менеджер, 4 - пользователь, 5 - заблокированный

        //проверим логин
        checkNotLogin(userInSession);

        //проверим заблокирован ли пользователь
        checkBlockUser(userInSession);

        //проверим куда пользователь хочет попасть 
        //свой профиль смотреть можно (id юзера в сессии = id юзера для просмотра)
        //если хотим смотреть чужого пользователя
        if (userInSession.getId_user() != userToAccess.getId_user()) {

            //проверяем можем ли это делать, а в зависимости от наличия у нас полномочий (группа пользователя)
            //выбрасываем исключения, если доступ запрещен
            switch (userInSession.getGroup_id()) {
                case 3:
                    throw new UserException("Доступ запрещен. Попытка доступа к данным чужого пользователя, полномочия отсутствуют");
                case 4:
                    throw new UserException("Доступ запрещен. Попытка доступа к данным чужого пользователя, полномочия отсутствуют");
            }
        }
    }

    public static void checkEditUserAccess(User userInSession, User userToAccess) throws UserException {
        /*логика работы аналогична checkViewUserAccess*/

        checkNotLogin(userInSession);
        checkBlockUser(userInSession);

        //своего пользователя можно редактировать, только админы (группа 2) и суперпользователи (группа 1) могут редактировтаь чужих
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
        /*логика работы аналогична checkViewUserAccess*/
        checkNotLogin(userInSession);
        checkBlockUser(userInSession);

        //только 1 - суперпользователь и 2 - адмнинистратор могут смотреть список всех пользователей
        switch (userInSession.getGroup_id()) {

            case 3:
                throw new UserException("Доступ запрещён.");
            case 4:
                throw new UserException("Доступ запрещён.");
            case 5:
                throw new UserException("Доступ запрещён.");
        }

    }

    public static void checkResetPassword(User userInSession) throws UserException {
        /*логика работы аналогична checkViewUserAccess*/
        checkNotLogin(userInSession);
        checkBlockUser(userInSession);

        //функционал сброса пароля доступен только суперпользователю
        switch (userInSession.getGroup_id()) {
            case 2:
                throw new UserException("Доступ запрещен. Сброс пароля может делать только Администратор");
            case 3:
                throw new UserException("Доступ запрещен. Сброс пароля может делать только Администратор");
            case 4:
                throw new UserException("Доступ запрещен. Сброс пароля может делать только Администратор");
        }
    }

    public static void checkDeleteUser(User userInSession, User userToAccess) throws UserException {
        /*логика работы аналогична checkViewUserAccess*/
        checkNotLogin(userInSession);
        checkBlockUser(userInSession);

        if (userInSession.getId_user() != userToAccess.getId_user()) {

            switch (userInSession.getGroup_id()) {
                case 2:
                    throw new UserException("Доступ запрещен. Нет полномочий на удаление пользователя");
                case 3:
                    throw new UserException("Доступ запрещен. Нет полномочий на удаление пользователя");
                case 4:
                    throw new UserException("Доступ запрещен. Нет полномочий на удаление пользователя");
            }
        }
    }

    public static void checkEditPassword(User userInSession, User userToAccess) throws UserException {
        /*логика работы аналогична checkViewUserAccess*/
        checkNotLogin(userInSession);
        checkBlockUser(userInSession);

        /*только супер пользователь может редактировать чужие пароли, либо любой пользователь может редактировать свой пароль*/
        if (userInSession.getId_user() != userToAccess.getId_user()) {

            switch (userInSession.getGroup_id()) {
                case 2:
                    throw new UserException("Доступ запрещен. Нет полномочий на редактирование чужого пароля");
                case 3:
                    throw new UserException("Доступ запрещен. Нет полномочий на редактирование чужого пароля");
                case 4:
                    throw new UserException("Доступ запрещен. Нет полномочий на редактирование чужого пароля");
            }
        }
    }

    //метод проверки при открытии страницы логина
    public static void checkLoginAccess(User userInSession) throws UserException {

        //если пользователь уже вошёл, передаем об этом сообщение и не пускаем на старницу логина
        if (userInSession != null) {
            throw new UserException("Вы уже вошли на сайт как " + userInSession.getUsername());
        }

    }

    //общий метод проверки заблокирован ли пользователь (если группа пользователей 5 - заблокирован)
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

    public static void checkCreateEventAccess(User userInSession) throws UserException {
        /*логика работы аналогична checkViewUserAccess*/
        checkNotLogin(userInSession);
        checkBlockUser(userInSession);

        switch (userInSession.getGroup_id()) {
            case 3:
                throw new UserException("Невозможно создать мероприятие. Функция доступна только администраторам");
            case 4:
                throw new UserException("Невозможно создать мероприятие. Функция доступна только администраторам");
            case 5:
                throw new UserException("Невозможно создать мероприятие. Функция доступна только администраторам");
        }
    }

    public static void checkEventListAccess(User userInSession) throws UserException {

        checkNotLogin(userInSession);
        checkBlockUser(userInSession);
    }

    public static void checkDeleteEvent(User userInSession, Event event) throws UserException, EventException {
        
        checkNotLogin(userInSession);
        checkBlockUser(userInSession);
        
        switch (userInSession.getGroup_id()) {
            case 2:
                if (userInSession.getId_user() != event.findAuthor().getPerson().getId_user()) {
                    throw new EventException("Нет полномочий на удаление чужого мероприятия");
                }
                break;
            case 3:
                throw new EventException("Нет полномочий на удаление любых мероприятий");
            case 4:
                throw new EventException("Нет полномочий на удаление любых мероприятий");
        }
    }
}
