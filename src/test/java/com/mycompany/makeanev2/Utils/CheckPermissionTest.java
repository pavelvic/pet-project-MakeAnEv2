package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import org.junit.Before;
import org.junit.Test;

public class CheckPermissionTest {
    
    private User userToAccess;
    private User userInSessionNull;
    private User userInSessionSup;
    private User userInSessionAdm;
    private User userInSessionMng;
    private User userInSessionUser;
    private User userInSessionBlocked;
   
    @Before
    public void setUp() {
        userToAccess = new User(2, 0, null, null, 0, null, null, null, null, null, null);
        userInSessionNull = null;
        userInSessionSup = new User(1, 1, null, null, 0, null, null, null, null, null, null);
        userInSessionAdm = new User(1, 2, null, null, 0, null, null, null, null, null, null);
        userInSessionMng = new User(1, 3, null, null, 0, null, null, null, null, null, null);
        userInSessionUser = new User(1, 4, null, null, 0, null, null, null, null, null, null);
        userInSessionBlocked = new User(1, 5, null, null, 0, null, null, null, null, null, null);
    }

    @Test (expected = UserException.class)
    public void checkNotLoginTest() throws UserException {
        CheckPermission.checkNotLogin(userInSessionNull);     
    }
    
    @Test (expected = UserException.class)
    public void checkBlockUserTest() throws UserException {
        
        CheckPermission.checkBlockUser(userInSessionBlocked);     
    }
    
    @Test (expected = UserException.class)
    public void checkLoginAccessTest() throws UserException {
        CheckPermission.checkLoginAccess(userInSessionUser);     
    }
    
    @Test (expected = UserException.class)
    public void checkViewUserAccessTest() throws UserException {
        CheckPermission.checkViewUserAccess(userInSessionBlocked, userToAccess); //попытка прочитать данные будучи заблокированным
        CheckPermission.checkViewUserAccess(userInSessionUser, userToAccess); //попытка прочитать данные при отсутсвии прав (группа пользователей = 4)
        CheckPermission.checkViewUserAccess(userInSessionMng, userToAccess); //попытка прочитать данные при отсутсвии прав (группа пользователей = 3)  
    }
    
    @Test (expected = UserException.class)
    public void checkEditUserAccessTest() throws UserException {
        CheckPermission.checkEditUserAccess(userInSessionBlocked, userToAccess); //попытка прочитать данные будучи заблокированным
        CheckPermission.checkEditUserAccess(userInSessionUser, userToAccess); //попытка прочитать данные при отсутсвии прав (группа пользователей = 4)
        CheckPermission.checkEditUserAccess(userInSessionMng, userToAccess); //попытка прочитать данные при отсутсвии прав (группа пользователей = 3)  
    }
    
    @Test (expected = UserException.class)
    public void checkUserListAccessTest() throws UserException {
        //только суперпользователь и администратор может смотреть списки пользователей
        CheckPermission.checkUserListAccess(userInSessionBlocked); //попытка попытка просмотра списка всех пользователей будучи заблокированным
        CheckPermission.checkUserListAccess(userInSessionUser); //попытка попытка просмотра списка всех пользователей от имени Пользователя (группа пользователей = 4)
        CheckPermission.checkUserListAccess(userInSessionMng); //попытка просмотра списка всех пользователей от лица Менеджера (группа пользователей = 3)  
    }
    
    @Test (expected = UserException.class)
    public void checkResetPasswordTest() throws UserException {
        //только суперпользователь может сбрасывать пароли другим пользователям, для остальных - нельзя, что мы тут и проверяем
        CheckPermission.checkUserListAccess(userInSessionBlocked); //попытка сбросить пароль будучи заблокированным
        CheckPermission.checkUserListAccess(userInSessionUser); //попытка сбросить пароль от имени Пользователя (группа пользователей = 4)
        CheckPermission.checkUserListAccess(userInSessionMng); //попытка сбросить пароль от лица Менеджера (группа пользователей = 3)  
        CheckPermission.checkUserListAccess(userInSessionAdm); //попытка сбросить пароль от лица Админа (группа пользователей = 2)
    }
    
    @Test (expected = UserException.class)
    public void checkDeleteUserTest() throws UserException {
        //только суперпользователь может удалять других пользователей или пользователь может удалить сам себя. В других случаях - нельзя
        CheckPermission.checkDeleteUser(userInSessionBlocked, userToAccess); //попытка удалить будучи заблокированным
        CheckPermission.checkDeleteUser(userInSessionUser, userToAccess); //попытка удалить от имени Пользователя (группа пользователей = 4)
        CheckPermission.checkDeleteUser(userInSessionMng, userToAccess); //попытка удалить от лица Менеджера (группа пользователей = 3)
        CheckPermission.checkDeleteUser(userInSessionAdm, userToAccess); //попытка удалить от лица Админа (группа пользователей = 2)
    }
    
    @Test (expected = UserException.class)
    public void checkEditPasswordTest() throws UserException {
        /*только супер пользователь может редактировать чужие пароли, либо любой пользователь может редактировать свой пароль
        в остальных кейсах даём исключение*/
        CheckPermission.checkEditPassword(userInSessionBlocked, userToAccess); //попытка изменить пароль будучи заблокированным
        CheckPermission.checkEditPassword(userInSessionUser, userToAccess); //попытка изменить пароль от имени Пользователя (группа пользователей = 4)
        CheckPermission.checkEditPassword(userInSessionMng, userToAccess); //попытка изменить пароль от лица Менеджера (группа пользователей = 3)
        CheckPermission.checkEditPassword(userInSessionAdm, userToAccess); //попытка изменить пароль от лица Админа (группа пользователей = 2)
    }  
}
