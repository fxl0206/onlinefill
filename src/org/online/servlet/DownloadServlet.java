package org.online.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 492108703051298668L;

	private org.springframework.web.context.WebApplicationContext wac;

	public void init() {

		wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this
				.getServletContext());

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tableid = (String) request.getParameter("tabid");
		String rowid=(String)request.getParameter("rowid");
		DriverManagerDataSource datasource = (DriverManagerDataSource) wac
				.getBean("dataSource");
		Connection con;
		try {
			con = (Connection) datasource.getConnection();
			Statement st = (Statement) con.createStatement();
			ResultSet rs1 = st
					.executeQuery("select taname,tatype from tablelist where taid='"
							+ tableid + "'");
			if (rs1.next()) {
				String tatype=".xls";
				String cotype="application/vnd.ms-excel";
				String ftype=rs1.getString("tatype");
				String sql="select * from `" + tableid+"`";
				if(ftype.equals("word"))
				{
					tatype=".doc";
					cotype="application/msword";
					cotype="text/html";					
					if(rowid!=null)
					sql="select col from `"+tableid+"` where rowid='"+request.getParameter("rowid")+"'";
				}
				String tabname=rs1.getString("taname")+tatype;
				ResultSet rs = st.executeQuery(sql);
				response.reset();
				response.setCharacterEncoding("GBK");
				response.setContentType(cotype);
				response.setHeader("Content-Disposition", "inline;filename="
						+ new String(tabname.getBytes(), "iso8859-1"));
				PrintWriter out = response.getWriter();
				out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"> ");
				if(ftype.equals("excel"))
				{	
				ResultSetMetaData rsm = rs.getMetaData();	
				out.println("<table border=1>");
				while (rs.next()) {
					out.println("<tr>");
					for (int i = 2; i <= rsm.getColumnCount(); i++) {
						out.println(" <td>");
						out.print(rs.getString(i));
						out.print("</td>");
					}
					out.println("</tr>");
				}
				out.println("</table>");
				}
				else
				{
					while(rs.next())
					{
						out.println("<div style='width:100%;border-top:solid 1px black;height:1px'></div>");
						out.println(rs.getString("col"));						
						//System.out.println("#################################");
					}
					out.println("<style>" );
					out.println("th, td { border-top: 1px solid black;height:auto}");
					out.println("th, td { border-left: 1px solid black;}");
					out.println("table{ border:1px solid black;border-right:2px solid black;border-bottom: 2px solid black;}");
					out.println("p{margin:2px}");
					out.println("</style>");
				}
				out.flush();
				out.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public DownloadServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
}
// // 创建新的Excel 工作簿
// HSSFWorkbook workbook = new HSSFWorkbook();
// // 在Excel工作簿中建一工作表，其名为缺省值
// // 如要新建一名为"效益指标"的工作表，其语句为：
// // HSSFSheet sheet = workbook.createSheet("效益指标");
// HSSFSheet sheet = workbook.createSheet();
// // 在索引0的位置创建行（最顶端的行）
// HSSFRow row = sheet.createRow((short) 0);
// // 在索引0的位置创建单元格（左上端）
// HSSFCell cell = row.createCell((short) 0);
// // 定义单元格为字符串类型
// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
// // 在单元格中输入一些内容
// cell.setCellValue("增加值");
// // 新建一输出文件流
// //FileOutputStream fOut = new FileOutputStream(outputFile);
// //// 把相应的Excel 工作簿存盘
// //workbook.write(fOut);
// //fOut.flush();
// // 操作结束，关闭文件
// //fOut.close();
// //File file = new File("d:/acount.xls");
// /* 如果文件存在 */
// if (file.exists()) {
// // String filename = URLEncoder.encode(file.getName(), enc);
// response.reset();
// response.setContentType("application/vnd.ms-excel");
// response.setHeader("Content-Disposition", "inline;filename="
// + new String("系统日志.xls".getBytes(), "iso8859-1"));
// int fileLength = (int) file.length();
// response.setContentLength(fileLength);
// ServletOutputStream out = response.getOutputStream();
// BufferedInputStream bis = null;
// BufferedOutputStream bos = null;
// try {
//
// bis = new BufferedInputStream(new InputStream(workbook));
// bos = new BufferedOutputStream(out);
//
// byte[] buff = new byte[1024];
// int bytesRead;
//
// // Simple read/write loop.
// while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
// bos.write(buff, 0, bytesRead);
// }
// out.flush();
//
// } catch (final IOException e) {
// System.out.println("IOException.");
// throw e;
// } finally {
// if (bis != null)
// bis.close();
// if (bos != null)
// bos.close();
// }
