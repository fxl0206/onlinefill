package org.online.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.online.model.Organize;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Organize entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.online.dao.Organize
 * @author MyEclipse Persistence Tools
 */

public class OrganizeDAO extends JpaDaoSupport {
	// property constants
	public static final String TEXT = "text";
	public static final String PID = "pid";
	public static final String LEAF = "leaf";

	/**
	 * Perform an initial save of a previously unsaved Organize entity. All
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
	 * OrganizeDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Organize entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Organize entity) {
		logger.info("saving Organize instance");
		try {
			getJpaTemplate().persist(entity);
			System.out.println("save successful");
			logger.info("save successful");
		} catch (RuntimeException re) {
			System.out.println(re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Organize entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * OrganizeDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Organize entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Organize entity) {
		logger.info("deleting Organize instance");
		try {
			entity = getJpaTemplate().getReference(Organize.class,
					entity.getOrgid());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Organize entity and return it or a copy of it
	 * to the sender. A copy of the Organize entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
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
	 * entity = OrganizeDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Organize entity to update
	 * @return Organize the persisted Organize entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Organize update(Organize entity) {
		logger.info("updating Organize instance");
		try {
			Organize result =this.getJpaTemplate().merge(entity);
			System.out.println("update sffffffcessful");
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public Organize findById(String id) {
		logger.info("finding Organize instance with id: " + id);
		try {
			Organize instance = getJpaTemplate().find(Organize.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all Organize entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Organize property to query
	 * @param value
	 *            the property value to match
	 * @return List<Organize> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Organize> findByProperty(String propertyName, final Object value) {
		logger.info("finding Organize instance with property: " + propertyName
				+ ", value: " + value);
		try {
			final String queryString = "select model from Organize model where model."
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

	public List<Organize> findByText(Object text) {
		return findByProperty(TEXT, text);
	}

	public List<Organize> findByPid(Object pid) {
		return findByProperty(PID, pid);
	}

	public List<Organize> findByLeaf(Object leaf) {
		return findByProperty(LEAF, leaf);
	}

	/**
	 * Find all Organize entities.
	 * 
	 * @return List<Organize> all Organize entities
	 */
	@SuppressWarnings("unchecked")
	public List<Organize> findAll() {
		logger.info("finding all Organize instances");
		try {
			final String queryString = "select model from Organize model";
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
//
//	public static OrganizeDAO getFromApplicationContext(ApplicationContext ctx) {
//		return (OrganizeDAO) ctx.getBean("OrganizeDAO");
//	}
}