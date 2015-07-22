package org.online.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.online.dao.TablelistDAO;
import org.online.model.Tablelist;
import org.online.util.UUIDGenerator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class TablelistService {

	@PersistenceContext(unitName = "onlinefillPU")
	private EntityManager entityManager;
	private DriverManagerDataSource dataSource;
	private TablelistDAO tablelistDAO;

	public List<Tablelist> getBydir(Object tadirid) {
		return tablelistDAO.findByTadirid(tadirid, 0);
	}

	public TablelistDAO getTablelistDAO() {
		return tablelistDAO;
	}

	public void setTablelistDAO(TablelistDAO tablelistDAO) {
		this.tablelistDAO = tablelistDAO;
	}

	public void save(Tablelist talist) {
		tablelistDAO.save(talist);

	}

	public void delete(Tablelist entity) {
		tablelistDAO.delete(entity);
	}

	public void update(Tablelist entity) {
		tablelistDAO.update(entity);
	}

	public Tablelist findById(String uuid) {
		return tablelistDAO.findById(uuid);
	}
	@SuppressWarnings("unchecked")
	public void createTable(String tableName,ArrayList ls)
	{
		String sql1="create table `"+tableName+"`(rowid VARCHAR(32)";
		String sql2="insert into `"+tableName+"` values('head'";
		Iterator it=ls.iterator();
		int i=0;
		while(it.hasNext())
		{
			sql1+=",col"+(++i)+" TEXT";
			String tmp=(String) it.next();
			sql2+=",'"+tmp+"'";			
		}
		sql1+=")";
		sql2+=")";
		//System.out.println(sql1);
		Query query1=entityManager.createNativeQuery(sql1);
		query1.executeUpdate();
		//System.out.println(sql2);
		Query query2=entityManager.createNativeQuery(sql2);
		query2.executeUpdate();
	}
	public void createWordTable(String tableName,String userName,String ownerName,String content)
	{
		String sql1="create table `"+tableName+"`(rowid VARCHAR(32),acname varchar(255),ownername varchar(255)," +
				"col LONGTEXT)";
		Query query1=entityManager.createNativeQuery(sql1);
		query1.executeUpdate();
		//System.out.println(sql2);
		String sql2="insert into `"+tableName+"` values('head','"+userName+"','"+ownerName+"','"+content.replace("'","\\\'")+"')";
		Query query2=entityManager.createNativeQuery(sql2);
		query2.executeUpdate();
	}
	public void inDefault(String tableName,ArrayList ls,String rowid)
	{
		String sql2="insert into `"+tableName+"` values('"+rowid+"'";
		Iterator it=ls.iterator();
		int i=0;
		while(it.hasNext())
		{
			String tmp=(String) it.next();
			sql2+=",'"+tmp+"'";			
		}
		sql2+=")";
		Query query2=entityManager.createNativeQuery(sql2);
		query2.executeUpdate();
	}
	public void deleteTable(String tableName)
	{
		Query query=entityManager.createNativeQuery("drop table `"+tableName+"`");
		query.executeUpdate();
	}
	public JSONObject getwordtable(String taid) throws SQLException, JSONException
	{
		JSONObject jsobj=new JSONObject();
		JSONArray jsfeilds=new JSONArray();
		Connection con=this.dataSource.getConnection();
		Statement st=(Statement) con.createStatement();
		ResultSet rs=st.executeQuery("select rowid,acname,ownername from `"+taid+"`");
		while(rs.next())
		{
			JSONObject jsrows=new JSONObject();
			jsrows.put("rowid",rs.getString(1));
			jsrows.put("acname",rs.getString(2));
			jsrows.put("owner",rs.getString(3));
			jsfeilds.put(jsrows);
		}
		jsobj.put("rows", jsfeilds);
		return jsobj;
	}
	public JSONObject getwordtable1(String taid) throws SQLException, JSONException
	{
		JSONObject jsobj=new JSONObject();
		JSONArray jsfeilds=new JSONArray();
		Connection con=this.dataSource.getConnection();
		Statement st=(Statement) con.createStatement();
		ResultSet rs=st.executeQuery("select rowid from `"+taid+"` where rowid<>'head'");
		while(rs.next())
		{
			JSONObject jsrows=new JSONObject();
			jsrows.put("rowid",rs.getString(1));
			jsfeilds.put(jsrows);
		}
		jsobj.put("rows", jsfeilds);
		return jsobj;
	}
	public String getwordcontent(String taid,String rowid) throws SQLException
	{
		Connection con=this.dataSource.getConnection();
		Statement st=(Statement) con.createStatement();
		ResultSet rs=st.executeQuery("select col from `"+taid+"` where rowid='"+rowid+"'");
		if(rs.next())
		{
			return rs.getString(1);
		}
		else
			return null;
	}
	public String getwordbyuser(String taid,String acname) throws SQLException
	{
		Connection con=this.dataSource.getConnection();
		Statement st=(Statement) con.createStatement();
//		ResultSet rs=st.executeQuery("select col from `"+taid+"` where acname='"+acname+"'");
//		if(rs.next())
//		{
//			//System.out.println(rs.getString(1));
//			return rs.getString(1);
//		}
//		else
//		{
			ResultSet rs=st.executeQuery("select col from `"+taid+"` where rowid='head'");
			if(rs.next())
			{
				return rs.getString(1);
			}
//		}
			return null;
	}
	public void updatewordcontent(String taid,String rowid,String content) throws SQLException
	{
		//System.out.println("##################################");
		//System.out.println(content.replace("\\\"","\""));
		String sql="update `"+taid+"` set col='"+content.replace("\'","\\\'")+"' where rowid='"+rowid+"'";
		Query query1=entityManager.createNativeQuery(sql);
		query1.executeUpdate();
	}
	public void userdealword(String taid,String acname,String uname,String content) throws SQLException
	{
		//System.out.println("##################################");
		//System.out.println(content.replace("\\\"","\""));		
		//String sql="select rowid from `"+taid+"` where acname='"+acname+"'";
		Connection con=this.dataSource.getConnection();
		Statement st=(Statement) con.createStatement();
//		ResultSet rs=st.executeQuery(sql);
//		if(rs.next())
//		{
//			sql="update `"+taid+"` set col='"+content.replace("\'","\\\'")+"' where acname='"+acname+"'";
//		}
//		else
//		{
			String uuid = String.valueOf((new UUIDGenerator()).generate());
			//content.replace("\'","\\\'")
		String sql="insert into `"+taid+"` values('"+uuid+"','"+acname+"','"+uname+"','"+content.replace("\'","\\\'")+"')";
		//System.out.println(sql);
		st.executeUpdate(sql);
		
	}
	public void deletewordcontent(String taid,String rowid) throws SQLException
	{
		//System.out.println("##################################");
		//System.out.println(content.replace("\\\"","\""));
		String sql="delete from `"+taid+"` where rowid='"+rowid+"'";
		Query query1=entityManager.createNativeQuery(sql);
		query1.executeUpdate();
	}
	public JSONObject getTable(String taid) throws JSONException, SQLException
	{
		JSONObject jsobj=new JSONObject();
		JSONObject jsrows=new JSONObject();
		JSONArray jsfeilds=new JSONArray();
		JSONArray jscolumns=new JSONArray();
		Connection con=this.dataSource.getConnection();
		Statement st=(Statement) con.createStatement();
		ResultSet rs=st.executeQuery("select * from `"+taid+"`");
		ResultSetMetaData rsm=rs.getMetaData();
		int cols=rsm.getColumnCount();
		
		if(rs.next())
		{
			for(int i=1;i<=cols;i++)
			{
			JSONObject feildtmp=new JSONObject();
			String colName=rsm.getColumnName(i);
			String colValue=rs.getString(i);
			float pers=1/cols;
			feildtmp.put("name",colName);
			jsfeilds.put(feildtmp);
			JSONObject columntmp=new JSONObject();
			columntmp.put("id",colName);
			if(colValue.equals("head"))
				columntmp.put("hidden",true);
			columntmp.put("header",colValue);
			columntmp.put("columnWidth",pers);	
			JSONObject edittmp=new JSONObject();
			edittmp.put("xtype","textfield");
			columntmp.put("editor",edittmp);
			columntmp.put("sortable",false);
			columntmp.put("dataIndex",colName);
			jscolumns.put(columntmp);
			}
		}
		JSONArray rowsarr=new JSONArray();
		while(rs.next())
		{
			JSONObject rowtmp=new JSONObject();
			for(int i=1;i<=cols;i++)
			{
				rowtmp.put(rsm.getColumnName(i),rs.getString(i));
			}
			rowsarr.put(rowtmp);
		}
		jsrows.put("rows",rowsarr);
		jsobj.put("feilds",jsfeilds);
		jsobj.put("columns",jscolumns);		
		jsobj.put("rows",jsrows);
		return jsobj;
	}
	public void insertValue()
	{
		
	}
	public void injectTest() {
		//String sql2="insert into test values('廖雪峰')";
		Query query;String sql1;
		for(int i=10;i<50;i++)
		{
		sql1="create table  test"+i+"(cdate   VARCHAR(4000))";
		query = entityManager
		.createNativeQuery(sql1);
		//query.setParameter("tablename","test"+i);
		query.executeUpdate();
		}
	}
public List<Tablelist> findByac(String acname)
{
	Query query=entityManager.createQuery("select model from Tablelist model where model.taid in(select m.tadeid from Deals m)");
	return query.getResultList();
}
	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}
public void fill(String sql){
	Query query=entityManager.createNativeQuery(sql);
	query.executeUpdate();
}

public static String byte2hex(byte[] b) // 二进制转字符串

{

String hs = "";

String stmp = "";

for (int n = 0; n < b.length; n++) {

stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

if (stmp.length() == 1)

hs = hs + "0" + stmp;

else

hs = hs + stmp;

}

return hs;

}

public static byte[] hex2byte(String str) { // 字符串转二进制

if (str == null)

return null;

str = str.trim();

int len = str.length();

if (len == 0 || len % 2 == 1)

return null;

byte[] b = new byte[len / 2];

try {

for (int i = 0; i < str.length(); i += 2) {

b[i / 2] = (byte) Integer

.decode("0x" + str.substring(i, i + 2)).intValue();

}

return b;

} catch (Exception e) {

return null;

}
}
//	public void Test() {
//		tablelistDAO.getJpaTemplate().execute(new JpaCallback() {
//			public Object doInJpa(EntityManager em) throws PersistenceException {
//				Query query = em
//						.createNativeQuery("create table  test(cdate       DATETIME)");
//				query.executeUpdate();
//				return null;
//			}
//		});
//	}
}
