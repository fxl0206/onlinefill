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
import org.online.model.Organize;
import org.online.services.AcountsService;
import org.online.services.OrganizeService;
import org.online.util.UUIDGenerator;

import com.sun.jersey.spi.inject.Inject;

@Path("/orgsource")
public class OrganizeSource {
@Inject
private OrganizeService orgService;
@Inject
private AcountsService actService;
	@GET
	@Path("/tree")
	@Produces("application/json")
	@Consumes("text/plain")
	public JSONArray getTree(@Context HttpServletRequest request) {
		String root=(String) request.getSession().getAttribute("orgid");
		return orgService.getTreeJson(root);
		//return (new JSONArray()).put(json);
	}
	@POST
	@Path("/add")
	@Produces("text/plain")
	@Consumes("application/json")
	public JSONObject addOrg(JSONObject obj) throws JSONException {
		try{
		String uuid=String.valueOf((new UUIDGenerator()).generate());
		String text=(String) obj.get("text");
		String pid=(String) obj.get("pid");
		Boolean leaf=(Boolean) obj.get("leaf");
		Organize org=new Organize(uuid,text,pid,leaf);
		orgService.Save(org);
		obj.put("id",uuid);
		obj.put("msg","添加成功！");
		obj.put("success",true);
		return obj;
		}
		catch(Exception x)
		{
			obj.put("msg","服务端处理错误！");
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
		boolean isnull=true;
		String uuid=(String) obj.get("id");
		if(uuid.equals("system"))
		{
			obj.put("msg","系统目录不能删除！");
			obj.put("success",false);
			return obj;
		}
		List ls2=actService.findByAcorgid(uuid,0);		
		if(ls2.size()<=0)
		{
			System.out.println("################");
			List ls1=orgService.findByPid(uuid);
			if(ls1.size()>0)
				isnull=false;
		}
		else
		{
			isnull=false;
		}
		if(isnull)
		{
		Organize org=new Organize();
		org.setOrgid(uuid);
		orgService.delete(org);
		obj.put("msg","删除成功！");
		obj.put("success",true);
		}
		else
		{
			obj.put("msg","该机构目录不为空!");
			obj.put("success",false);
		}
		return obj;
		}
		catch(Exception e)
		{
			obj.put("msg","服务端处理错误！");
			obj.put("success",false);
			return obj;
		}		
	}
	@POST
	@Path("/edit")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject olEdit(JSONObject obj) throws JSONException {
		try{
		String uuid=(String)obj.get("id");
		String text=(String)obj.get("text");
		Organize org=orgService.findById(uuid);
		org.setText(text);
		orgService.update(org);
		obj.put("msg","修改成功！");
		obj.put("success",true);
		return obj;
		}
		catch(Exception e)
		{
			obj.put("msg","服务端处理错误！");
			obj.put("success",false);
			return obj;
		}
	}

}
