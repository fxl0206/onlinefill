package org.online.sources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.BatchUpdateException;
import java.sql.Timestamp;
import java.text.DateFormat;
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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.online.model.Acounts;
import org.online.model.Tablelist;
import org.online.services.AcountsService;
import org.online.util.POIExcelUtil;
import org.online.util.UUIDGenerator;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.spi.inject.Inject;

@Path("/acounts")
public class AcountsSource {
@Inject
AcountsService acountsService;
	@POST
	@Path("/add")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject add(JSONObject jsobj) throws JSONException {
		try{
		Acounts acount=new Acounts();
		String acorgid=jsobj.getString("acorgid");
		String acname=jsobj.getString("acname");
		String acpwd=jsobj.getString("acpwd");
		String acowner=jsobj.getString("acowner");
		String acphone=jsobj.getString("acphone");
		String acemail=jsobj.getString("acemail");
		//Boolean acstatu=(Boolean)jsobj.get("acstatu");
		Timestamp accdate=new Timestamp((new Date().getTime()));
		String acdetail=jsobj.getString("acdetail");
		acount.setAcorgid(acorgid);
		acount.setAcname(acname);
		acount.setAcpwd(acpwd);
		acount.setAcowner(acowner);
		acount.setAcphone(acphone);
		acount.setAcemail(acemail);
		acount.setAcstatu(true);
		acount.setAccdate(accdate);
		acount.setAcdetail(acdetail);
		acountsService.save(acount);
		jsobj.put("credate",accdate);
		jsobj.put("acdetail",acdetail);
		jsobj.put("success",true);
		jsobj.put("msg","添加成功！");
		return jsobj;
		}
		catch(Exception e)
		{
			jsobj.put("success",false);
			jsobj.put("msg","该账号已经存在！");
			return jsobj;
		}
	}

	@POST
	@Path("/edit")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject edit(JSONObject jsobj) throws JSONException {
		try{
		Acounts acount=new Acounts();
		String acname=jsobj.getString("acount");
		String acpwd=jsobj.getString("pwd");
		String acowner=jsobj.getString("owner");
		String acphone=jsobj.getString("tel");
		String acemail=jsobj.getString("email");
		Boolean acstatu=(Boolean)jsobj.get("sta");
		//Timestamp accdate=new Timestamp((new Date(jsobj.getString("credate")).getTime()));
		String acdetail=jsobj.getString("detail");
		acount=acountsService.findeById(acname);
		acount.setAcpwd(acpwd);
		acount.setAcowner(acowner);
		acount.setAcphone(acphone);
		acount.setAcemail(acemail);
		acount.setAcstatu(acstatu);
		//acount.setAccdate(accdate);
		acount.setAcdetail(acdetail);
		acountsService.update(acount);
		jsobj.put("msg","修改成功");
		jsobj.put("success",true);
		return jsobj;
		}
		catch(Exception e)
		{
			jsobj.put("msg","所有者，密码不能为空！");
			jsobj.put("success",false);
			return jsobj;
		}
	}

	@POST
	@Path("/delete")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject delete(JSONArray jsin) throws JSONException {
		JSONObject json=new JSONObject();
		int len=jsin.length();
		String msg="";
		for(int i=0;i<len;i++)
		{
			try{
			Acounts act=new Acounts();
			act.setAcname(jsin.getString(i));
			acountsService.delete(act);
			msg+=jsin.getString(i)+"#删除成功!<br>";
			}catch(Exception e)
			{
				System.out.println(e.toString()+"##################");
				msg+=jsin.getString(i)+"#不存在!<br>";
			}

		}
		json.put("msg",msg);
		json.put("success",true);
		return json;

	}

	@GET
	@Path("/getbyorgid")
	@Produces("application/json")
	@Consumes("text/plain")
	public JSONObject getByOrgid(@QueryParam("orgid") String orgid) throws JSONException {
		JSONObject jsobj=new JSONObject();
		JSONArray jsarr=new JSONArray();
		List<Acounts> acounts=acountsService.findByAcorgid(orgid,0);
		for(Acounts act:acounts)
		{
			JSONObject temjson=new JSONObject();
			temjson.put("acount",act.getAcname());
			temjson.put("pwd",act.getAcpwd());
			temjson.put("owner",act.getAcowner());
			temjson.put("email",act.getAcemail());
			temjson.put("tel",act.getAcphone());
			temjson.put("credate",act.getAccdate());
			temjson.put("sta",act.getAcstatu());
			temjson.put("detail",act.getAcdetail());
			jsarr.put(temjson);
		}
		jsobj.put("acounts", jsarr);
		return jsobj;
	}

	@POST
	@Path("/modpwd")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject modpwd(JSONObject jsobj,@Context HttpServletRequest request) throws JSONException {
		JSONObject rtjs=new JSONObject();
		String username=(String) request.getSession().getAttribute("username");
		String oldpwd=jsobj.getString("oldpwd");
		String newpwd=jsobj.getString("newpwd");
		String repick=jsobj.getString("repick");
		if(!newpwd.equals(repick))
		{
			rtjs.put("success",false);
			rtjs.put("msg","你输入的两次密码不一致！，重新修改");
			return rtjs;
		}
		Acounts tmp=acountsService.findeById(username);
		if(!tmp.getAcpwd().equals(oldpwd))
		{
			rtjs.put("success",false);
			rtjs.put("msg","你输入的旧密码不正确！，重新修改");
			return rtjs;
		}
		tmp.setAcpwd(newpwd);
		acountsService.update(tmp);
		rtjs.put("success",true);
		rtjs.put("msg","恭喜你，密码修改成功！");
		return rtjs;
	}
	@SuppressWarnings("unchecked")
	@POST
	@Path("/batadd")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("text/html")
	public JSONObject add(@FormParam("orgid") String orgid,
			@FormParam("dbfile") FormDataContentDisposition dbdis,
			@FormParam("dbfile") File dbfile)
			throws Exception {
		List dataList = new POIExcelUtil().read(dbfile,dbdis.getFileName());
		Iterator its = dataList.iterator();
		its.next();
		JSONObject rtjs = new JSONObject();
		JSONArray rtarr=new JSONArray();
		while (its.hasNext()) {
			ArrayList als = (ArrayList) its.next();
			Timestamp accdate=new Timestamp((new Date().getTime()));
			Acounts actmp=new Acounts();
			actmp.setAccdate(accdate);
			actmp.setAcorgid(orgid);
			actmp.setAcstatu(true);
			actmp.setAcname((String) als.get(0));
			actmp.setAcpwd((String) als.get(1));
			actmp.setAcowner((String)als.get(2));
			actmp.setAcemail((String) als.get(3));
			actmp.setAcphone((String) als.get(4));
			actmp.setAcdetail((String) als.get(5));
			try{
			acountsService.save(actmp);
			}
				catch(Exception e)
			{
				rtarr.put(als.get(0)+"  ");
			}
		}
		rtjs.put("msg",rtarr);
		rtjs.put("success", "true");
		return rtjs;
	}
}
//java.util.StringTokenizer a = new java.util.StringTokenizer(dbdis
//.getFileName(), ".");
//String str = null;
//while (a.hasMoreTokens()) {
//str = a.nextToken();
//}
//uuid = uuid + "." + str;
//String realPath = context.getRealPath("/onlinedb/");
//copyUpload(realPath,uuid, dbfile);
//public void copyUpload(String realPath, String fileName, File fupload)
//throws Exception {
//FileOutputStream fos = new FileOutputStream(realPath + "/" + fileName);
//FileInputStream fis = new FileInputStream(fupload);
//byte[] buffer = new byte[1024];
//int len = 0;
//while ((len = fis.read(buffer)) > 0) {
//fos.write(buffer, 0, len);
//}
//fos.close();
//fis.close();
//}
