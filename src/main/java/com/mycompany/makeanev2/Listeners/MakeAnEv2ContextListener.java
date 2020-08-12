package com.mycompany.makeanev2.Listeners;

import java.time.ZoneId;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MakeAnEv2ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //определяем часовой пояс для всего приложения
        //TODO: определять часовой пояс с какого-нибудь сервиса, который предоставляет эту услугу, что позволит для конкретных пользователей опредлять часовой пояс
        //это позволит отображать корректное время для пользователя из любой точки мира
        //пока ставим "заглушку" и для всех пользователей ставим московский часовой пояс
        sce.getServletContext().setAttribute("ZoneId", ZoneId.of("Europe/Moscow"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //nothing
    }
}
