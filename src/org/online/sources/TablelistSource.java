package org.online.sources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.online.model.Tablelist;
import org.online.services.TablelistService;
import org.online.util.POIExcelUtil;
import org.online.util.UUIDGenerator;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.spi.inject.Inject;

@Path("/tablelist")
public class TablelistSource {
	@Inject
	private TablelistService tablssv;

	@SuppressWarnings("unchecked")
	@GET
	@Path("/findbydirid")
	public JSONObject findBydirid(@QueryParam("dirid") String dirid)
			throws JSONException {
		JSONArray jsarr = new JSONArray();
		List<Tablelist> ls = tablssv.getBydir(dirid);
		Iterator it = ls.iterator();
		while (it.hasNext()) {
			Tablelist talss = (Tablelist) it.next();
			JSONObject tmp = new JSONObject();
			// {taskname:'入党申请书，登记',startdate:'2011-3-5',enddata:'2011-4-9',adddate:'2011-03-21',contentcount:9}
			tmp.put("taskid", talss.getTaid()+"#"+talss.getTatype());
			tmp.put("taskname", talss.getTaname());
			tmp.put("startdate", talss.getTastdate());
			tmp.put("enddate", talss.getTaendate());
			tmp.put("adddate", talss.getTapubdate());
			tmp.put("contentcount", talss.getTafinicount());
			jsarr.put(tmp);
		}
		JSONObject jsrt = new JSONObject();
		jsrt.put("tasks", jsarr);
		return jsrt;
	}
	@Consumes("application/json")
	@POST
	@Path("addword")
	@Produces("application/json")
	public JSONObject addword(JSONObject obj,
			@Context HttpServletRequest request
	) throws JSONException {
		String uuid = String.valueOf((new UUIDGenerator()).generate());
		Timestamp tapubdate = new Timestamp(new Date().getTime());
		Timestamp taenddate=new Timestamp(new Date().getTime());
		String username = (String) request.getSession()
		.getAttribute("username");
		String name=(String) request.getSession()
		.getAttribute("name");
		String tabname=obj.getString("tn");
		String dirid=obj.getString("dirid");
		//String content=obj.getString("content");
		System.out.println(tabname+"#############");
		System.out.println(dirid+"@@@@@@@@@@@@@@@");
		//System.out.println(content+"$$$$$$$$$$$$$");
		Tablelist talist = new Tablelist();
		talist.setTadirid(dirid);
		talist.setTaid(uuid);
		talist.setTaname(tabname);
		talist.setTastdate(tapubdate);
		//talist.setTaendate(taenddate);
		talist.setTapubdate(tapubdate);
		talist.setTafinicount(0);
		talist.setTafrom(username);
		talist.setTatype("word");
		//System.out.println(tabname);
		tablssv.save(talist);
		tablssv.createWordTable(uuid,username,name,obj.getString("content"));
		return new JSONObject();
	}
	@SuppressWarnings("unchecked")
	@POST
	@Path("/add")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("text/html")
	public JSONObject add(@FormParam("dirid") String dirid,
			@FormParam("tafile") FormDataContentDisposition tadis,
			@FormParam("tafile") File tafile,
			@FormParam("taname") String taname,
			@FormParam("tastdate") String tastdate,
			@FormParam("taendate") String taendate,
			@Context HttpServletRequest request)
			throws Exception {	
		JSONObject jsobj = new JSONObject();
		try{
		String uuid = String.valueOf((new UUIDGenerator()).generate());
		String rtname = taname;
		List dataList = new POIExcelUtil().read(tafile,tadis.getFileName());
		Iterator its = dataList.iterator();
		Timestamp tapubdate = new Timestamp(new Date().getTime());
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date nstdate = format1.parse(tastdate);
		Timestamp st = new Timestamp(nstdate.getTime());
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date nendate = format2.parse(taendate);
		Timestamp end = new Timestamp(nendate.getTime());
		String username = (String) request.getSession()
				.getAttribute("username");
		Tablelist talist = new Tablelist();
		talist.setTadirid(dirid);
		talist.setTaid(uuid);
		talist.setTaname(rtname);
		talist.setTastdate(st);
		talist.setTaendate(end);
		talist.setTapubdate(tapubdate);
		talist.setTafinicount(0);
		talist.setTafrom(username);
		talist.setTatype("excel");
		tablssv.save(talist);
		if (its.hasNext()) {
			ArrayList als = (ArrayList) its.next();
			tablssv.createTable(uuid, als);
		}
		while(its.hasNext())
		{
			String tmpid = String.valueOf((new UUIDGenerator()).generate());
			ArrayList als = (ArrayList) its.next();
			tablssv.inDefault(uuid, als,tmpid);
		}
		JSONObject rtjs = new JSONObject();
		rtjs.put("taskid", uuid);
		rtjs.put("taskname", rtname);
		rtjs.put("startdate", st);
		rtjs.put("enddate", end);
		rtjs.put("adddate", tapubdate);
		rtjs.put("contentcount", 0);
		jsobj.put("newtask", rtjs);
		String msg= new String("上传成功！".getBytes("ISO-8859-1"),"gbk");
		// {taskname:'入党申请书，登记',startdate:'2011-3-5',enddata:'2011-4-9',adddate:'2011-03-21',contentcount:9}
		jsobj.put("msg",msg);
		jsobj.put("success","true");
		return jsobj;
		}
		catch(Exception e)
		{
			jsobj.put("success",false);
			jsobj.put("msg","上传失败!");
			return jsobj;
		}
	}
	@GET
	@Path("/delete")
	@Produces("application/json")
	@Consumes("text/plain")
	public JSONObject delete(@QueryParam("taid") String taid)
			throws JSONException {
		JSONObject jsobj = new JSONObject();
		try{
		Tablelist tali = new Tablelist();
		taid=taid.split("#")[0];
		tali.setTaid(taid);
		tablssv.delete(tali);
		tablssv.deleteTable(taid.split("#")[0]);
		jsobj.put("success",true);
		jsobj.put("msg","删除成功！");
		return jsobj;
		}
		catch(Exception e)
		{
			jsobj.put("success",false);
			jsobj.put("msg","删除失败！");
			return jsobj;
		}

	}

	@POST
	@Path("/edit")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject edit(JSONObject obj) throws JSONException, ParseException {
		try{
		String uuid = obj.getString("taskid").split("#")[0];
		String taname = obj.getString("taskname");
		Tablelist talis = tablssv.findById(uuid);
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date nstdate = format1.parse(obj.getString("startdate"));
		Timestamp st = new Timestamp(nstdate.getTime());
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date nendate = format2.parse(obj.getString("enddate"));
		@SuppressWarnings("unused")
		Timestamp end = new Timestamp(nendate.getTime());
		talis.setTaname(taname);
		talis.setTastdate(st);
		talis.setTaendate(st);
		// talis.setTapubdate(tapubdate);
		// talis.setTafinicount(tafinicount);
		tablssv.update(talis);
		obj.put("success",true);
		obj.put("msg","修改成功！");
		return obj;
		}
		catch(Exception e)
		{
			obj.put("success",false);
			obj.put("msg","修改失败！");
			return obj;
		}
	}

	@GET
	@Path("/gettab")
	@Produces("application/json")
	@Consumes("text/plain")
	public JSONObject getTab(@QueryParam("taid") String taid)
			throws JSONException, SQLException {
		return tablssv.getTable(taid);
	}

	@GET
	@Path("/findbyacname")
	@Produces("application/json")
	public JSONObject findbyacname(@Context HttpServletRequest request)
			throws JSONException {
		String acname = (String) request.getSession().getAttribute("username");
		JSONArray jsarr = new JSONArray();
		List<Tablelist> ls = tablssv.findByac(acname);
		Iterator it = ls.iterator();
		while (it.hasNext()) {
			Tablelist talss = (Tablelist) it.next();
			JSONObject tmp = new JSONObject();
			// {taskname:'入党申请书，登记',startdate:'2011-3-5',enddata:'2011-4-9',adddate:'2011-03-21',contentcount:9}
			tmp.put("taskid", talss.getTaid()+"#"+talss.getTatype());
			tmp.put("taskname", talss.getTaname());
			tmp.put("startdate", talss.getTastdate());
			tmp.put("enddate", talss.getTaendate());
			tmp.put("adddate", talss.getTapubdate());
			tmp.put("contentcount", talss.getTafinicount());
			jsarr.put(tmp);
		}
		JSONObject jsrt = new JSONObject();
		jsrt.put("tasks", jsarr);
		return jsrt;
	}

	@POST
	@Path("/fill")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject fill(JSONObject obj) throws JSONException, ParseException {
		JSONObject jsobj=new JSONObject();
		try{
		jsobj.put("rowid","null");
		int colscount=obj.getInt("colscount");
		String tabid=obj.getString("tabid");
		String rowid=obj.getString("rowid");
		String sql=null;
		if(colscount>1)
		{
		if(rowid.length()!=0)	
		{
		sql="update `"+tabid+"` set col1='"+obj.getString("col1")+"'";
		
		for(int i=2;i<colscount;i++)
		{
			sql+=",col"+i+"='"+obj.getString("col"+i)+"'";			
		}
		sql+=" where rowid='"+rowid+"'";
		}
		else
		{
			String uuid = String.valueOf((new UUIDGenerator()).generate());
			jsobj.put("rowid",uuid);
			sql="insert into `"+tabid+"` values('"+uuid+"'";
			for(int i=1;i<colscount;i++)
			{
				sql+=",'"+obj.getString("col"+i)+"'";			
			}
			sql+=")";
			//tablssv.fill("update "+tabid+" set tafinicount=tafinicout+1");
			Tablelist tmp=tablssv.findById(tabid);
			int t=tmp.getTafinicount();
			t++;
			tmp.setTafinicount(t);
			tablssv.update(tmp);
		}
		//System.out.println(sql);
		tablssv.fill(sql);
		}		
		jsobj.put("success",true);
		jsobj.put("msg","同步成功！");
		return jsobj;
		}catch(Exception e)
		{
			jsobj.put("success",false);
			jsobj.put("msg","同步失败！");
			return jsobj;
		}
	}
	@GET
	@Path("/getwordlist")
	@Produces("application/json")
	@Consumes("text/plain")
	public JSONObject getwordlist(@QueryParam("taid") String taid) throws JSONException, SQLException {
		JSONObject jsobj=new JSONObject();
		jsobj=tablssv.getwordtable(taid);
		return jsobj;
	}
	@GET
	@Path("/getwordlist1")
	@Produces("application/json")
	@Consumes("text/plain")
	public JSONObject getwordlist1(@QueryParam("taid") String taid) throws JSONException, SQLException {
		JSONObject jsobj=new JSONObject();
		jsobj=tablssv.getwordtable1(taid);
		return jsobj;
	}
	@POST
	@Path("/getwordrow")
	@Produces("text/html")
	@Consumes("application/json")
	public String getwordrow(JSONObject obj) throws JSONException, SQLException {
		return tablssv.getwordcontent(obj.getString("taid"),obj.getString("rowid"));	
		//return "<h1>来至getwordrow的数据 </h1>";
	}
	@POST
	@Path("/getwordbyuser")
	@Produces("text/html")
	@Consumes("application/json")
	public String getwordbyuser(JSONObject obj,
			@Context HttpServletRequest request) throws JSONException, SQLException {
		String acname=(String) request.getSession().getAttribute("username");
		return tablssv.getwordbyuser(obj.getString("taid"),acname);	
		//return "<h1>xxx</h1>";
		//return "<h1>来至getwordrow的数据 </h1>";
	}
	@POST
	@Path("/updatewordrow")
	@Produces("text/html")
	@Consumes("application/json")
	public String updatewordrow(JSONObject obj) throws JSONException, SQLException {
		tablssv.updatewordcontent(obj.getString("taid"),obj.getString("rowid"),obj.getString("content"));
		return "同步成功！";	
		//return "<h1>来至getwordrow的数据 </h1>";
	}
	@POST
	@Path("/userdealword")
	@Produces("text/html")
	@Consumes("application/json")
	public String userdealword(JSONObject obj,
			@Context HttpServletRequest request) throws JSONException, SQLException {
		String acname=(String) request.getSession().getAttribute("username");
		String uname=(String) request.getSession().getAttribute("name");
		tablssv.userdealword(obj.getString("taid"),acname,uname,obj.getString("content"));
		return "添加成功！";	
		//return "<h1>来至getwordrow的数据 </h1>";
	}
	@POST
	@Path("/deletewordrow")
	@Produces("text/html")
	@Consumes("application/json")
	public String deletewordrow(JSONObject obj) throws JSONException, SQLException {
		String rowid=obj.getString("rowid");
		if(!rowid.equals("head"))
		{
		//	System.out.print(obj.getString("rowid")+"cccccccccccc");
		tablssv.deletewordcontent(obj.getString("taid"),rowid);
		return "删除成功！";	
		}
		else
		{
			return "不能删除模板";
		}
		//return "<h1>来至getwordrow的数据 </h1>";
	}
//	@Produces(MediaType.APPLICATION_OCTET_STREAM)
//	  public byte[] download(@PathParam("id") long id)throws Exception{
//	//ApplicationContext ac=WebApplicationContextUtils.getWebApplicationContext(servletContext);
//	//resourceService=(ResourceService)ac.getBean("resourceService");
//
//	Resource resource=(Resource)resourceService.getObject(Resource.class, id);
//	String path=servletContext.getRealPath(resource.getFilePath());
//	  FileInputStream fis=new FileInputStream(new File(path));
//	  byte [] b=new byte[fis.available()];
//	  fis.read(b);
//	  return b;
//	  }

}
