package com.interceptor;

import com.ansel.pojo.User;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Ansel Zhong
 * coding time
 */
public class LoginInterceptor implements HandlerInterceptor {
  // return true; 执行下一个拦截器
  // return false 不执行
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    if (user == null) {
      response.sendRedirect("/ad/toLogin");
    }
//    if (request.getRequestURI().contains("login")) {
//      return true;
//    }
    //request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);

    return true;
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    System.out.println("======处理后======");
  }

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    System.out.println("=======清理======");
  }
}
