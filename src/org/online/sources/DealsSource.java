package org.online.sources;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.online.model.Deals;
import org.online.services.DealsService;
import org.online.util.UUIDGenerator;

import com.sun.jersey.spi.inject.Inject;

@Path("/deals")
public class DealsSource {
@Inject
private DealsService dealsService;
	@GET
	@Path("/getlink")
	@Produces("application/json")
	@Consumes("text/plain")
	public JSONObject getlink(@QueryParam("taid") String taid) throws JSONException {
		JSONObject jsobj=new JSONObject();
		JSONArray jsarr=new JSONArray();
		List ls=dealsService.getLink(taid.split("#")[0]);
		Iterator it=ls.iterator();
		while(it.hasNext())
		{
			Object[] objs=(Object[]) it.next();
				JSONObject tmp=new JSONObject();
				tmp.put("acount",objs[0]);
				tmp.put("owner",objs[1]);
				tmp.put("tel",objs[2]);
				tmp.put("detail",objs[3]);		
				jsarr.put(tmp);
		}
		jsobj.put("acounts",jsarr);
		return jsobj;
	}
	@POST
	@Path("/add")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject add(JSONArray arr) throws JSONException {
		
		JSONObject js=new JSONObject();
		JSONArray jsarr=new JSONArray();
		for(int i=0;i<arr.length();i++)
		{
			try{
			JSONObject jstmp=arr.getJSONObject(i);
			String uuid=String.valueOf((new UUIDGenerator()).generate());
			String taid=jstmp.getString("taid").split("#")[0];
			String acname=jstmp.getString("acname");
//			String deid, String tadeid, Boolean destatu, String acdeid
			Deals tmp=new Deals(uuid,taid,false,acname);
			dealsService.save(tmp);	
			jsarr.put("ok");
			}catch(Exception e)
			{
				jsarr.put("no");
			}
			
		}
		js.put("success",true);
		js.put("sus", jsarr);
		js.put("msg","返回成功！");
		return js;
	}
	@POST
	@Path("/delete")
	@Produces("application/json")
	@Consumes("application/json")
	public JSONObject delete(JSONArray jsa) throws JSONException {
		JSONObject js=new JSONObject();
		try{
		for(int i=0;i<jsa.length();i++)
		{
		String taid=jsa.getJSONObject(i).getString("taid").split("#")[0];
		String acname=jsa.getJSONObject(i).getString("acname");
		dealsService.delete(acname, taid);
		}
		js.put("success",true);
		js.put("msg","删除成功！");
		return js;
		}
		catch(Exception e)
		{
			js.put("success",false);
			js.put("msg","某些数据不存在！");
			return js;
		}
	}

}
