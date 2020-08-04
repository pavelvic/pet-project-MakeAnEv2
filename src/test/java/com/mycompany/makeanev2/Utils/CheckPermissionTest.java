package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.Exceptions.EventException;
import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.Participant;
import com.mycompany.makeanev2.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CheckPermissionTest {

    private final User userToAccess = new User(2, 0, null, null, 0, null, null, null, null, null, null);
    private final User userInSessionNull = null;
    private final User userInSessionSup = new User(3, 1, null, null, 0, null, null, null, null, null, null);
    private final User userInSessionAdm = new User(1, 2, null, null, 0, null, null, null, null, null, null);
    private final User userInSessionMng = new User(1, 3, null, null, 0, null, null, null, null, null, null);
    private final User userInSessionUser = new User(1, 4, null, null, 0, null, null, null, null, null, null);
    private final User userInSessionBlocked = new User(1, 5, null, null, 0, null, null, null, null, null, null);
    private final Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);


    //попытка неавторизованного действия не зарегистрированным пользователем
    @Test(expected = UserException.class)
    public void checkNotLoginTest() throws UserException{
        CheckPermission.checkNotLogin(userInSessionNull);
    }

    //попытка неавторизованного действия заблокированным пользователем (группа = 5)
    @Test(expected = UserException.class)
    public void checkBlockUserTest() throws UserException {

        CheckPermission.checkBlockUser(userInSessionBlocked);
    }

    //попытка досутпа к странице регистрации после входа в систему
    @Test(expected = UserException.class)
    public void checkLoginAccessTest() throws UserException {
        CheckPermission.checkLoginAccess(userInSessionUser);
    }
    
    //просмотр списка пользователей не зарегистированным
    @Test(expected = UserException.class)
    public void checkViewUserAccessNotLogTest() throws UserException {
        CheckPermission.checkViewUserAccess(userInSessionNull, userToAccess);
    }
    
    //просмотр списка пользователя заблокированным (группа 5)
    @Test(expected = UserException.class)
    public void checkViewUserAccessBlockedTest() throws UserException {
       CheckPermission.checkViewUserAccess(userInSessionBlocked, userToAccess);
    }

    //просмотр списка пользователя рядовым пользователем (группа 4)
    @Test(expected = UserException.class)
    public void checkViewUserAccessUserTest() throws UserException {
        CheckPermission.checkViewUserAccess(userInSessionUser, userToAccess); 
    }
    
    //просмотр списка пользователя менеджером (группа 3)
    @Test(expected = UserException.class)
    public void checkViewUserAccessMngTest() throws UserException {
        CheckPermission.checkViewUserAccess(userInSessionMng, userToAccess); 
    }
    
    //редактирование чужого пользователя не зарегистрированным
    @Test(expected = UserException.class)
    public void checkEditUserAccessNotLogTest() throws UserException {
        CheckPermission.checkEditUserAccess(userInSessionNull, userToAccess); 
    }
    
    //редактирование чужого пользователя заблокированным (группа 5)
    @Test(expected = UserException.class)
    public void checkEditUserAccessBlockedTest() throws UserException {
        CheckPermission.checkEditUserAccess(userInSessionBlocked, userToAccess); 
    }

    //редактирование чужого пользователя рядовым пользователем (группа 4)
    @Test(expected = UserException.class)
    public void checkEditUserAccessUserTest() throws UserException {
        CheckPermission.checkEditUserAccess(userInSessionUser, userToAccess); 
    }
    
    //редактирование чужого пользователя менеджером (группа 3)
    @Test(expected = UserException.class)
    public void checkEditUserAccessMngTest() throws UserException {
        CheckPermission.checkEditUserAccess(userInSessionMng, userToAccess);  
    }
    
    //просмотр списка пользователей не зарегистрированным
    @Test(expected = UserException.class)
    public void checkUserListAccessNotLogTest() throws UserException {
        CheckPermission.checkUserListAccess(userInSessionNull); 
    }
    
    //просмотр списка пользователей заблокированным (группа 5)
    @Test(expected = UserException.class)
    public void checkUserListAccessBlockedTest() throws UserException {
        CheckPermission.checkUserListAccess(userInSessionBlocked);
    }
    
    //просмотр списка пользователей рядовым пользователем (группа 4)
    @Test(expected = UserException.class)
    public void checkUserListAccessUserTest() throws UserException {
        CheckPermission.checkUserListAccess(userInSessionUser);
    }

    //просмотр списка пользователей менеджером (группа 3)
    @Test(expected = UserException.class)
    public void checkUserListAccessMngTest() throws UserException {
        CheckPermission.checkUserListAccess(userInSessionMng); 
    }
    
    //попытка сбороса пароля для не зарегистрированного
    @Test(expected = UserException.class)
    public void checkResetPasswordNotLogTest() throws UserException {
        
        CheckPermission.checkResetPassword(userInSessionNull);
    }
    
    //попытка сбороса пароля для заблокированного (группа 5)
    @Test(expected = UserException.class)
    public void checkResetPasswordBlockedTest() throws UserException {
        CheckPermission.checkResetPassword(userInSessionBlocked);
    }
    
    //попытка сбороса пароля для рядового пользователя (группа 4)
    @Test(expected = UserException.class)
    public void checkResetPasswordUserTest() throws UserException {
        CheckPermission.checkResetPassword(userInSessionUser);
    }
    
    //попытка сбороса пароля для менеджера (группа 3)
    @Test(expected = UserException.class)
    public void checkResetPassworMngTest() throws UserException {
        CheckPermission.checkResetPassword(userInSessionMng);
    }
    
    //попытка сбороса пароля для администратора (группа 2)
    @Test(expected = UserException.class)
    public void checkResetPassworAdmTest() throws UserException {
        CheckPermission.checkResetPassword(userInSessionAdm);
    }

    //попытка удаления чужого пользователя для не зарегистрированного
    @Test(expected = UserException.class)
    public void checkDeleteUserByNotLogTest() throws UserException {
        CheckPermission.checkDeleteUser(userInSessionNull, userToAccess);
    }
    
    //попытка удаления чужого пользователя заблокированным (группа 5)
    @Test(expected = UserException.class)
    public void checkDeleteUserByBlockedTest() throws UserException {
        CheckPermission.checkDeleteUser(userInSessionBlocked, userToAccess); 
    }
    
    //попытка удаления чужого пользователя рядовым пользователем (группа 4)
    @Test(expected = UserException.class)
    public void checkDeleteUserByUserTest() throws UserException {
        CheckPermission.checkDeleteUser(userInSessionUser, userToAccess); 
    }
    
    //попытка удаления чужого пользователя менеджером (группа 3)
    @Test(expected = UserException.class)
    public void checkDeleteUserByMngTest() throws UserException {
        CheckPermission.checkDeleteUser(userInSessionMng, userToAccess); 
    }
    
    //попытка удаления чужого пользователя администратором (группа 2)
    @Test(expected = UserException.class)
    public void checkDeleteUserByAdmTest() throws UserException {
        CheckPermission.checkDeleteUser(userInSessionAdm, userToAccess); 
    }
    
    
    //попытка редактирования пароля чужого пользователя не зарегистирированным
    @Test(expected = UserException.class)
    public void checkEditPasswordNotLogTest() throws UserException {
        CheckPermission.checkEditPassword(userInSessionNull, userToAccess);
    }
    
    
    //попытка редактирования пароля чужого пользователя заблокированным (группа 5)
    @Test(expected = UserException.class)
    public void checkEditPasswordBlockedTest() throws UserException {
        CheckPermission.checkEditPassword(userInSessionBlocked, userToAccess);
    }
    
    //попытка редактирования пароля чужого пользователя рядовым пользователем (группа 4)
    @Test(expected = UserException.class)
    public void checkEditPasswordUserTest() throws UserException {
        CheckPermission.checkEditPassword(userInSessionUser, userToAccess);
    }
    
    //попытка редактирования пароля чужого пользователя менеджером (группа 3)
    @Test(expected = UserException.class)
    public void checkEditPasswordMngTest() throws UserException {
        CheckPermission.checkEditPassword(userInSessionMng, userToAccess);
    }
    
    //попытка редактирования пароля чужого пользователя админом (группа 2)
    @Test(expected = UserException.class)
    public void checkEditPasswordAdmTest() throws UserException {
        CheckPermission.checkEditPassword(userInSessionAdm, userToAccess);
    }
    
    
    //checkCreateEventAccess (события могут создавать только админы и суперпользователи)
    //попытка создать событие без регистрации и авторизации
    @Test(expected = UserException.class)
     public void checkCreateEventAccessNotLogTest() throws UserException {
        CheckPermission.checkCreateEventAccess(userInSessionNull);
        
    }
     
     //попытка создать событие от заблокированного пользователя (группа 5)
    @Test(expected = UserException.class)
     public void checkCreateEventAccessBlockedTest() throws UserException {
        CheckPermission.checkCreateEventAccess(userInSessionBlocked);
        
    }
     
     //попытка создать событие рядовым пользователем (группа 4)
     @Test(expected = UserException.class)
     public void checkCreateEventAccessUserTest() throws UserException {
        CheckPermission.checkCreateEventAccess(userInSessionUser);
        
    }
     
     //попытка создать событие менеджером (группа 3)
     @Test(expected = UserException.class)
     public void checkCreateEventAccessMngTest() throws UserException {
        CheckPermission.checkCreateEventAccess(userInSessionMng);   
    }
    
    
    //checkEventListAccess (смотреть списки событий могут все, кроме заблокированным и не зарегистрированных)
    //попытка просмотра списка событий от не зарегистрированного пользователя
    @Test(expected = UserException.class)
    public void checkEventListAccessNotLogTest() throws UserException {
        CheckPermission.checkEventListAccess(userInSessionNull);
    }
    
    //попытка просмотра списка событий от заблокированного (группа 5)
    @Test(expected = UserException.class)
    public void checkEventListAccessBlockedTest() throws UserException {
        CheckPermission.checkEventListAccess(userInSessionBlocked);
    }
    
    
    
    //checkDeleteEvent(все мероприятия могут удалять только суперпользователи, админы могут удалять только свои мероприятия (где являются авторами))
    //попытка удалить мероприятие не заргистрированным пользователем
    @Test(expected = UserException.class)
    public void checkDeleteEventNotLogTest() throws UserException, EventException {
        CheckPermission.checkDeleteEvent(userInSessionNull,ev);
    }
    
    //попытка удалить мероприятие заблокированным пользователем (группа 5)
    @Test(expected = UserException.class)
    public void checkDeleteEventBlockedTest() throws UserException, EventException {
        CheckPermission.checkDeleteEvent(userInSessionBlocked,ev);
    }
    
    //попытка удалить мероприятие рядовым пользователем (группа 4)
    @Test(expected = EventException.class)
    public void checkDeleteEventUserTest() throws UserException, EventException {
        CheckPermission.checkDeleteEvent(userInSessionUser,ev);
    }
    
    //попытка удалить мероприятие менеджером (группа 3)
    @Test(expected = EventException.class)
    public void checkDeleteEventMngTest() throws UserException, EventException {
        CheckPermission.checkDeleteEvent(userInSessionMng,ev);
    }
    
    //попытка удалить чужое мероприятие админом (группа 2)
    @Test(expected = EventException.class)
    public void checkDeleteEventAdmTest() throws UserException, EventException {
        //создаем автора мероприятия (пользователь с айди 3)
        Participant pc = new Participant(userInSessionSup, null, null, null);
        pc.setAsAuthor();
        //создаем список участников и добавляем автора в участники
        List <Participant> participantList = new ArrayList<>();
        participantList.add(pc);
        //присваиваем списко участников мероприятию
        ev.setParticipants(participantList); 
        
        //проверяем будет ли исключение при попытке удалить мероприятие пользователем с айди 1
        CheckPermission.checkDeleteEvent(userInSessionAdm,ev);
    }   
}