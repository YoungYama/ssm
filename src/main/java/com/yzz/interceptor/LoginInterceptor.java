package com.yzz.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	/**
	 * 该方法将在请求处理之前进行调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(
				"----------------------- in LoginInterceptor: " + request.getSession(true).getAttribute("USER"));
		if (request.getSession(true).getAttribute("USER") == null) {
			request.getRequestDispatcher("/pages/login.html").forward(request, response);

			return false;
		}

		return true;
	}

	/**
	 * preHandle 方法的返回值为true 时才能被调用
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * preHandle 方法的返回值为true 时才会执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
