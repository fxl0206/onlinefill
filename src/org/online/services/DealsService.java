package org.online.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.online.dao.DealsDAO;
import org.online.model.Acounts;
import org.online.model.Deals;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public class DealsService {
	@PersistenceContext(unitName = "onlinefillPU")
	private EntityManager entityManager;
	private DealsDAO dealsDAO;
	public List getLink(String taid) {
		Query query=entityManager.createNativeQuery("select acname,acowner,acphone,acdetail from acounts where acname in(select acdeid from deals where tadeid=?)");
		query.setParameter(1,taid);
		return query.getResultList();
	}
	public void save(Deals entity)
	{
		dealsDAO.save(entity);
	}
	public void delete(String acname,String taid)
	{
		Query query=entityManager.createNativeQuery("delete from deals where acdeid='"+acname+"' and tadeid='"+taid+"'");
		query.executeUpdate();
	}
	public DealsDAO getDealsDAO() {
		return dealsDAO;
	}

	public void setDealsDAO(DealsDAO dealsDAO) {
		this.dealsDAO = dealsDAO;
	}
}
