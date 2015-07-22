package org.online.services;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.online.dao.TabledirDAO;
import org.online.model.Tabledir;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TabledirService {
	@PersistenceContext(unitName = "onlinefillPU")
	private EntityManager entityManager;
	private TabledirDAO tabledirDAO;

	public void save(Tabledir entity) {
		tabledirDAO.save(entity);
	}
    public List<Tabledir> findByPid(String pid)
    {
    	return tabledirDAO.findByPid(pid);
    }
	public Tabledir findById(String id) {
		return tabledirDAO.findById(id);
	}

	public void update(Tabledir entity) {
		tabledirDAO.update(entity);
	}

	public void delete(Tabledir entity) {
		tabledirDAO.delete(entity);
	}

	@SuppressWarnings("unchecked")
	public JSONArray getTree(String pid, String username) throws JSONException {
		JSONArray jarr = new JSONArray();
		Query query=entityManager.createNativeQuery("select * from tabledir where pid='"+pid+"' and ownername='"+username+"'",Tabledir.class);
//		query.setParameter(1,username);
		List<Tabledir> tabdir=query.getResultList();
//		List<Tabledir> tabdir = tabledirDAO.findByPid(pid);
		Iterator tabs = tabdir.iterator();
		while (tabs.hasNext()) {
			JSONObject jtemp = new JSONObject();
			Tabledir record = (Tabledir) tabs.next();
			jtemp.put("id", record.getDirid());
			jtemp.put("text", record.getText());
			jtemp.put("leaf", record.getLeaf());
			if (!record.getLeaf()) {
				jtemp.put("expanded", true);
				jtemp.put("children", getTree(record.getDirid(), username));
			}
			jarr.put(jtemp);
		}
		return jarr;
	}

	public TabledirDAO getTabledirDAO() {
		return tabledirDAO;
	}

	public void setTabledirDAO(TabledirDAO tabledirDAO) {
		this.tabledirDAO = tabledirDAO;
	}
}
