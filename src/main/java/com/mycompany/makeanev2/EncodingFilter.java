
package com.mycompany.makeanev2;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class EncodingFilter implements Filter {
//Заранее ставим правильну кодировку, иначе в БД пишет в ISO из за чего проблема с кириллицей
    
  @Override
  public void init(FilterConfig fConfig) throws ServletException {
 
  }
  
  @Override
  public void destroy() {
 
  }
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {
      request.setCharacterEncoding("UTF-8");
      chain.doFilter(request, response);
  }
}
