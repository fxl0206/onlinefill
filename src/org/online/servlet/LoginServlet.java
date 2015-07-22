package org.online.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sun.jersey.server.impl.application.WebApplicationContext;

public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */

	private org.springframework.web.context.WebApplicationContext wac;

	public void init() {

		wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this
				.getServletContext());

	}

	public LoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		javax.servlet.http.HttpSession session = request.getSession();
		//获取用户Session变量
		session.removeAttribute("username");
		//清除username Session变量
		session.removeAttribute("orgid");
		//清除orgid Session变量
		session.removeAttribute("name");
		//清除name Session变量
		response.sendRedirect("/login.jsp");
		//重定向到登录页面
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("username");
		//获取表单页面username文本域内容
		String pwd = request.getParameter("pwd");
		//获取表单页面pwd密码域内容
		DriverManagerDataSource datasource=(DriverManagerDataSource) wac.getBean("dataSource");
		//获取系统Mysql数据库连接池
		Connection con=null;
		//定义数据库连接对象
		try {
			con = datasource.getConnection();
			//从数据库连接池中请求一个数据库连接
			Statement st=(Statement) con.createStatement();
			//通过数据库连接创建一个数据库操作对象			
			ResultSet rs=st.executeQuery("select acorgid,acname,acpwd,acowner from acounts where acname='"+userName+"'");
			//从数据库中查询需要验证的帐号记录
			if(rs.next())
			//判断相关记录是否存在
			{
			String rspwd=rs.getString("acpwd");
			//从查询记录中提取用户密码值
			if(rspwd.equals(pwd))
			//判断提交的密码是否正确
			{
			javax.servlet.http.HttpSession session = request.getSession();
			//为用户创建session变量
			session.setAttribute("orgid",rs.getString("acorgid"));
			//设置用户所属机构ID标识Session变量
			session.setAttribute("username",userName);
			//设置用户帐号名Session变量
			session.setAttribute("name",rs.getString("acowner"));
			//设置用户真实姓名Session变量
			response.sendRedirect("/index.jsp");
			//重定向到主页
			}
			else
			{
				request.setAttribute("msg","你的密码错误，请重新登录!");
				//如果密码错误，设置错误提示消息
				getServletContext().getRequestDispatcher("/login.jsp").forward(request,response);
				//系统重新转发到登录页面

			}
			}
			else
			{
				request.setAttribute("msg","用户名不正确，请重新登录!");
				//如果在数据库中未找到相关提交用户的记录，设置错误提示消息
				getServletContext().getRequestDispatcher("/login.jsp").forward(request,response);
				//系统重新转发到登录页面

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//如果此过程发生异常，则抛出异常
		}finally{
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
}
