package org.online.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogNotFilter implements javax.servlet.Filter {
	private FilterConfig config;
	private String logon_page;
	private String home_page;

	public void destroy() {
		config = null;

	}

	public void init(FilterConfig filterconfig) throws ServletException {
		// 从部署描述符中获取登录页面和首页的URI
//		config = filterconfig;
//		logon_page = filterconfig.getInitParameter("LOGON_URI");
//		home_page = filterconfig.getInitParameter("HOME_URI");
//		System.out.println(home_page);
//		if (null == logon_page || null == home_page) {
//			throw new ServletException("没有找到登录页面或主页");
//		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rpo = (HttpServletResponse) response;   
		javax.servlet.http.HttpSession session = req.getSession();

		try {
			req.setCharacterEncoding("utf-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String userId = (String) session.getAttribute("username");
		if (userId!=null) {
			try {
				chain.doFilter(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				out.print("{'success':false,'msg':'未登录或登录失效'}");
				out.flush();
				out.close();
				//rpo.sendRedirect("/login.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
