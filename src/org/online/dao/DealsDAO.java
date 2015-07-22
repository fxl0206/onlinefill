package org.online.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.online.model.Deals;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Deals
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see org.online.dao.Deals
 * @author MyEclipse Persistence Tools
 */

public class DealsDAO extends JpaDaoSupport {
	// property constants
	public static final String TADEID = "tadeid";
	public static final String DESTATU = "destatu";
	public static final String ACDEID = "acdeid";

	/**
	 * Perform an initial save of a previously unsaved Deals entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * DealsDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Deals entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Deals entity) {
		logger.info("saving Deals instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Deals entity. This operation must be performed within
	 * the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * DealsDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Deals entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Deals entity) {
		logger.info("deleting Deals instance");
		try {
			entity = getJpaTemplate().getReference(Deals.class,
					entity.getDeid());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Deals entity and return it or a copy of it to
	 * the sender. A copy of the Deals entity parameter is returned when the JPA
	 * persistence mechanism has not previously been tracking the updated
	 * entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = DealsDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Deals entity to update
	 * @return Deals the persisted Deals entity instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Deals update(Deals entity) {
		logger.info("updating Deals instance");
		try {
			Deals result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public Deals findById(String id) {
		logger.info("finding Deals instance with id: " + id);
		try {
			Deals instance = getJpaTemplate().find(Deals.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all Deals entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Deals property to query
	 * @param value
	 *            the property value to match
	 * @return List<Deals> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Deals> findByProperty(String propertyName, final Object value) {
		logger.info("finding Deals instance with property: " + propertyName
				+ ", value: " + value);
		try {
			final String queryString = "select model from Deals model where model."
					+ propertyName + "= :propertyValue";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Deals> findByTadeid(Object tadeid) {
		return findByProperty(TADEID, tadeid);
	}

	public List<Deals> findByDestatu(Object destatu) {
		return findByProperty(DESTATU, destatu);
	}

	public List<Deals> findByAcdeid(Object acdeid) {
		return findByProperty(ACDEID, acdeid);
	}

	/**
	 * Find all Deals entities.
	 * 
	 * @return List<Deals> all Deals entities
	 */
	@SuppressWarnings("unchecked")
	public List<Deals> findAll() {
		logger.info("finding all Deals instances");
		try {
			final String queryString = "select model from Deals model";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	public static DealsDAO getFromApplicationContext(ApplicationContext ctx) {
		return (DealsDAO) ctx.getBean("DealsDAO");
	}
}