package Junit4.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;



public class DataSourceTest {
	@Test
public void st() throws SQLException
{
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		"config.xml");
		DriverManagerDataSource ds=(DriverManagerDataSource) ctx.getBean("dataSource");
		Connection con=ds.getConnection();
		Statement st=(Statement) con.createStatement();
		ResultSet rs=st.executeQuery("select * from organize");
		//int cols=rs.getMetaData().getColumnCount();
		while(rs.next())
		{
			System.out.print("\t"+rs.getString(2));
			System.out.println();
		}
}
}
