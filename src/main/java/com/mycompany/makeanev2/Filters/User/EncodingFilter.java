package com.mycompany.makeanev2.Filters.User;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*фильтр раотает при открытии каждой страницы и устанавливает для http-запроса корректную кодировку UTF-8
без данного фильтра кодировка по умолчанию - ISO, из-за чего введенная в формы ввода кириллица
сохранялась в БД (кодировка UTF-8) именно в формате ISO, что приводило к некорректному сохранению кириллицы в БД*/
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8"); //стандартный инструмент определения кодировки для http-запроса
        chain.doFilter(request, response);
    }
}
