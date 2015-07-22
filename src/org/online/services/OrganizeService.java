package org.online.services;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.online.dao.OrganizeDAO;
import org.online.model.Organize;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public class OrganizeService {
	public void Save(Organize organize) {
		organizeDAO.save(organize);
	}
	public void update(Organize organize) {
		organizeDAO.update(organize);
	}

	public void delete(Organize organize) {
		organizeDAO.delete(organize);
	}
	public Organize findById(String orgid)
	{
		return organizeDAO.findById(orgid);
	}
	public List findByPid(String pid)
	{
		return organizeDAO.findByPid(pid);
	}
	public JSONArray getTreeJson(String pid) {
		JSONArray jsons = new JSONArray();
		List<Organize> result = organizeDAO.findByPid(pid);
		Iterator<Organize> iterator = result.iterator();
		while (iterator.hasNext()) {
			JSONObject jsobj = new JSONObject();
			Organize record = iterator.next();
			try {
				jsobj.put("id", record.getOrgid());
				jsobj.put("text", record.getText());
				jsobj.put("leaf", record.getLeaf());
				if (!record.getLeaf()) {
					jsobj.put("expanded", true);
					jsobj.put("children", getTreeJson(record.getOrgid()));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsons.put(jsobj);
		}
		return jsons;

	}

	public OrganizeDAO getOrganizeDAO() {
		return organizeDAO;
	}

	public void setOrganizeDAO(OrganizeDAO organizeDAO) {
		this.organizeDAO = organizeDAO;
	}
	private OrganizeDAO organizeDAO;
}
