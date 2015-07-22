package Junit4.test;

import java.lang.reflect.Field;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.online.dao.OrganizeDAO;
import org.online.dao.TablelistDAO;
import org.online.model.Acounts;
import org.online.model.Organize;
import org.online.model.Tabledir;
import org.online.model.Tablelist;
import org.online.model.User;
import org.online.services.AcountsService;
import org.online.services.DealsService;
import org.online.services.OrganizeService;
import org.online.services.TabledirService;
import org.online.services.TablelistService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


public class OrganizeDAOTest {
@Test
	public void testGetTrees() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jerseyPU");
		EntityManager em = emf.createEntityManager();
		Query query=em.createQuery("select model from Tablelist model ");
		query.getResultList();
		em.getTransaction().commit();
		// List result = query.getResultList();
		// Iterator iterator=result.iterator();
		// while( iterator.hasNext() ){
		// Object[] record=(Object[])iterator.next();
		// String str="";
		// for(int i=0;i<record.length;i++)
		// {
		// str+="++"+(String)record[i].toString();
		// }
		// System.out.println(str);
		//System.out.println(test(query));

	}
@Test
public void tes()
{
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"config.xml");
	
	TablelistService orgs = (TablelistService) ctx.getBean("tablelistService");
	System.out.println(orgs.findByac("cassenl1"));
}
	@SuppressWarnings("unchecked")
	public JSONArray test(Query query) {
		JSONArray jsons = new JSONArray();
		List result = query.getResultList();
		Iterator iterator = result.iterator();
		while (iterator.hasNext()) {
			JSONObject jsobj = new JSONObject();
			Object[] record = (Object[]) iterator.next();
			try {
				jsobj.put("id", (Integer) record[1]);
				jsobj.put("text", (String) record[2]);
				jsobj.put("leaf", (Boolean) record[4]);
				if (!(Boolean) record[4]) {
					jsobj.put("expanded", true);
					query.setParameter(1, (Integer) record[1]);
					jsobj.put("children", test(query));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsons.put(jsobj);
		}
		return jsons;

	}
	@Test
	public void daoTest() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"config.xml");
		OrganizeDAO orgdao = (OrganizeDAO) ctx.getBean("OrganizeDAO");
		OrganizeService orgs = (OrganizeService) ctx.getBean("organizeService");
		Organize org = orgdao.findById("cassen");
		System.out.println(org.getText());
		org.setText("计算机系");
		orgs.update(org);
		System.out.println(orgdao.findById("cassen").getText());
	}

	public void acountsTest() throws SQLException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"config.xml");
		AcountsService orgdao = (AcountsService) ctx.getBean("acountsService");
		Acounts act = new Acounts();
		act.setAcname("20078214");
		orgdao.delete(act);
		// System.out.println(orgdao.findByAcorgid("cassen",0));
	}
//@Test
	public void tabledirTest() throws JSONException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"config.xml");
		TabledirService orgdao = (TabledirService) ctx
				.getBean("tabledirService");
		System.out.println( orgdao.getTree("root","cassenl1"));


	}

	//@Test
	public void dealsTest() throws JSONException, SecurityException, NoSuchFieldException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"config.xml");
		DealsService orgdao = (DealsService) ctx
				.getBean("dealsService");
		List ls = orgdao.getLink("f7853bab2fb34b07012fb34b07340000");
		Iterator it = ls.iterator();
		while (it.hasNext()) {
			Object[] ac=(Object[]) it.next();
			System.out.println(ac[0]+"\t"+ac[1]+"\t"+ac[2]);
		}

	}
	//@Test
	public void tablelistTest() throws JSONException, SecurityException, NoSuchFieldException, SQLException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"config.xml");
		TablelistDAO orgdao = (TablelistDAO) ctx
				.getBean("tablelistDao");
		System.out.println(orgdao.findById("8ad080ec309d7ebb01309d82c9ba0005"));
	}	
	public void dataSource() throws SQLException
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		"config.xml");
		DriverManagerDataSource ds=(DriverManagerDataSource) ctx.getBean("dataSource");
		Connection con=ds.getConnection();
		Statement st=(Statement) con.createStatement();
		ResultSet rs=st.executeQuery("select * from 8ad080ec309d7ebb01309d82c9ba0005");
		int cols=rs.getMetaData().getColumnCount();
		System.out.println(cols);
		while(rs.next())
		{
			for(int i=0;i<cols;i++)
			{
			System.out.print("\t"+rs.getString(i));
			}
			System.out.println();
		}
		
	}
	// @Test
	// public void tabledirTest() throws JSONException
	// {
	// ApplicationContext ctx=new ClassPathXmlApplicationContext("config.xml");
	// TabledirService orgdao=(TabledirService)ctx.getBean("tabledirService");
	// System.out.println(orgdao.getTree("root"));
	// }
	// public void tablelistTest()
	// {
	// ApplicationContext ctx=new ClassPathXmlApplicationContext("config.xml");
	// TablelistService
	// orgdao=(TablelistService)ctx.getBean("tablelistService");
	// System.out.println(orgdao.findByTaorgid("cassen",0));
	// }
	// public void dealsTest()
	// {
	// ApplicationContext ctx=new ClassPathXmlApplicationContext("config.xml");
	// DealsService orgdao=(DealsService)ctx.getBean("dealsService");
	// //System.out.println(orgdao.findByAcountid("acid1",0));
	// System.out.println(orgdao.findByTableid("taid4",0));
	// }
}
