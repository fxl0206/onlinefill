package org.online.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.online.model.Tabledir;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tabledir entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.online.dao.Tabledir
 * @author MyEclipse Persistence Tools
 */

public class TabledirDAO extends JpaDaoSupport {
	// property constants
	public static final String OWNERNAME = "ownername";
	public static final String TEXT = "text";
	public static final String PID = "pid";
	public static final String LEAF = "leaf";

	/**
	 * Perform an initial save of a previously unsaved Tabledir entity. All
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
	 * TabledirDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Tabledir entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Tabledir entity) {
		logger.info("saving Tabledir instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Tabledir entity. This operation must be performed
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
	 * TabledirDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Tabledir entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Tabledir entity) {
		logger.info("deleting Tabledir instance");
		try {
			entity = getJpaTemplate().getReference(Tabledir.class,
					entity.getDirid());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Tabledir entity and return it or a copy of it
	 * to the sender. A copy of the Tabledir entity parameter is returned when
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
	 * entity = TabledirDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Tabledir entity to update
	 * @return Tabledir the persisted Tabledir entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Tabledir update(Tabledir entity) {
		logger.info("updating Tabledir instance");
		try {
			Tabledir result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public Tabledir findById(String id) {
		logger.info("finding Tabledir instance with id: " + id);
		try {
			Tabledir instance = getJpaTemplate().find(Tabledir.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all Tabledir entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Tabledir property to query
	 * @param value
	 *            the property value to match
	 * @return List<Tabledir> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Tabledir> findByProperty(String propertyName, final Object value) {
		logger.info("finding Tabledir instance with property: " + propertyName
				+ ", value: " + value);
		try {
			final String queryString = "select model from Tabledir model where model."
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

	public List<Tabledir> findByOwnername(Object ownername) {
		return findByProperty(OWNERNAME, ownername);
	}

	public List<Tabledir> findByText(Object text) {
		return findByProperty(TEXT, text);
	}

	public List<Tabledir> findByPid(Object pid) {
		return findByProperty(PID, pid);
	}

	public List<Tabledir> findByLeaf(Object leaf) {
		return findByProperty(LEAF, leaf);
	}

	/**
	 * Find all Tabledir entities.
	 * 
	 * @return List<Tabledir> all Tabledir entities
	 */
	@SuppressWarnings("unchecked")
	public List<Tabledir> findAll() {
		logger.info("finding all Tabledir instances");
		try {
			final String queryString = "select model from Tabledir model";
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

	public static TabledirDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TabledirDAO) ctx.getBean("TabledirDAO");
	}
}