package org.online.sources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.online.model.Tabledir;
import org.online.services.TabledirService;
import org.online.services.TablelistService;
import org.online.util.UUIDGenerator;

import com.sun.jersey.spi.inject.Inject;

@Path("/tabledir")
public class TabledirSource {
@Inject
private TabledirService tabsv;
@Inject
private TablelistService tabls;
	@GET
	@Path("/gettree")
	@Produces("application/json")
	public JSONArray getTree(@Context HttpServletRequest request) throws JSONException {
		JSONArray rtarr=new JSONArray();
		JSONObject rtjs=new JSONObject();
		String username=(String) request.getSession().getAttribute("username");
		rtjs.put("id",username);
		rtjs.put("text","我的表格");
		rtjs.put("expanded",true);
		rtjs.put("children",tabsv.getTree(username,username));
		rtjs.put("leaf",false);
		rtarr.put(rtjs);
		return rtarr;
	}
	@POST
	@Path("/add")
	@Produces("text/plain")
	@Consumes("application/json")
	public JSONObject addDir(JSONObject obj,@Context HttpServletRequest request) throws JSONException {
		try{
		String uuid=String.valueOf((new UUIDGenerator()).generate());
		String text=(String) obj.get("text");
		String pid=(String) obj.get("pid");
		Boolean leaf=(Boolean) obj.get("leaf");
		String username=(String) request.getSession().getAttribute("username");
		Tabledir tdir=new Tabledir(uuid,username,text,pid,leaf);
		tabsv.save(tdir);
		obj.put("id",uuid);
		obj.put("msg","添加成功！");
		obj.put("success",true);
		return obj;
		}
		catch(Exception x)
		{
			obj.put("msg","提示:你提交的数据不正确！");
			obj.put("success",false);
			return obj;
		}
	}
	@POST
	@Path("/update")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject olEdit(JSONObject obj) throws JSONException {
		try{
		String uuid=(String)obj.get("id");
		String text=(String)obj.get("text");
		Tabledir org=tabsv.findById(uuid);
		org.setText(text);
		tabsv.update(org);
		obj.put("msg","修改成功");
		obj.put("success",true);
		return obj;
		}
		catch(Exception e)
		{
			obj.put("msg","提示：系统目录不能修改");
			obj.put("success",false);
			return obj;
		}
	}
	@POST
	@Path("/delete")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject olDelete(JSONObject obj) throws JSONException {
		try{
		String uuid=(String) obj.get("id");
		List list=tabls.getBydir(uuid);
		boolean isnull=true;
		if(list.size()<=0)
		{
			List list2=(List) tabsv.findByPid(uuid);
			if(list2.size()>0)
				isnull=false;
		}
		else
		{
			isnull=false;
		}
		if(isnull)
		{
		Tabledir tabs=new Tabledir();
		tabs.setDirid(uuid);
		tabsv.delete(tabs);
		obj.put("success",true);
		obj.put("msg","删除成功！");
		}
		else
		{
			obj.put("success",false);
			obj.put("msg","该目录不为空！");
		}
		return obj;
		}
		catch(Exception e)
		{
			obj.put("success",false);
			obj.put("msg","提示：系统目录不能删除！");
			return obj;
		}
	}
}
