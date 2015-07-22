package org.online.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.online.model.Acounts;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Acounts entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.online.dao.Acounts
 * @author MyEclipse Persistence Tools
 */

public class AcountsDAO extends JpaDaoSupport {
	// property constants
	public static final String ACORGID = "acorgid";
	public static final String ACPWD = "acpwd";
	public static final String ACOWNER = "acowner";
	public static final String ACEMAIL = "acemail";
	public static final String ACPHONE = "acphone";
	public static final String ACSTATU = "acstatu";
	public static final String ACDETAIL = "acdetail";

	/**
	 * Perform an initial save of a previously unsaved Acounts entity. All
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
	 * AcountsDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Acounts entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Acounts entity) {
		logger.info("saving Acounts instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Acounts entity. This operation must be performed
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
	 * AcountsDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Acounts entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Acounts entity) {
		logger.info("deleting Acounts instance");
		try {
			entity = getJpaTemplate().getReference(Acounts.class,
					entity.getAcname());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Acounts entity and return it or a copy of it
	 * to the sender. A copy of the Acounts entity parameter is returned when
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
	 * entity = AcountsDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Acounts entity to update
	 * @return Acounts the persisted Acounts entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Acounts update(Acounts entity) {
		logger.info("updating Acounts instance");
		try {
			Acounts result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public Acounts findById(String id) {
		logger.info("finding Acounts instance with id: " + id);
		try {
			Acounts instance = getJpaTemplate().find(Acounts.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all Acounts entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Acounts property to query
	 * @param value
	 *            the property value to match
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            number of results to return.
	 * @return List<Acounts> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Acounts> findByProperty(String propertyName,
			final Object value, final int... rowStartIdxAndCount) {
		logger.info("finding Acounts instance with property: " + propertyName
				+ ", value: " + value);
		try {
			final String queryString = "select model from Acounts model where model."
					+ propertyName + "= :propertyValue";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					if (rowStartIdxAndCount != null
							&& rowStartIdxAndCount.length > 0) {
						int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
						if (rowStartIdx > 0) {
							query.setFirstResult(rowStartIdx);
						}

						if (rowStartIdxAndCount.length > 1) {
							int rowCount = Math.max(0, rowStartIdxAndCount[1]);
							if (rowCount > 0) {
								query.setMaxResults(rowCount);
							}
						}
					}
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Acounts> findByAcorgid(Object acorgid,
			int... rowStartIdxAndCount) {
		return findByProperty(ACORGID, acorgid, rowStartIdxAndCount);
	}

	public List<Acounts> findByAcpwd(Object acpwd, int... rowStartIdxAndCount) {
		return findByProperty(ACPWD, acpwd, rowStartIdxAndCount);
	}

	public List<Acounts> findByAcowner(Object acowner,
			int... rowStartIdxAndCount) {
		return findByProperty(ACOWNER, acowner, rowStartIdxAndCount);
	}

	public List<Acounts> findByAcemail(Object acemail,
			int... rowStartIdxAndCount) {
		return findByProperty(ACEMAIL, acemail, rowStartIdxAndCount);
	}

	public List<Acounts> findByAcphone(Object acphone,
			int... rowStartIdxAndCount) {
		return findByProperty(ACPHONE, acphone, rowStartIdxAndCount);
	}

	public List<Acounts> findByAcstatu(Object acstatu,
			int... rowStartIdxAndCount) {
		return findByProperty(ACSTATU, acstatu, rowStartIdxAndCount);
	}

	public List<Acounts> findByAcdetail(Object acdetail,
			int... rowStartIdxAndCount) {
		return findByProperty(ACDETAIL, acdetail, rowStartIdxAndCount);
	}

	/**
	 * Find all Acounts entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<Acounts> all Acounts entities
	 */
	@SuppressWarnings("unchecked")
	public List<Acounts> findAll(final int... rowStartIdxAndCount) {
		logger.info("finding all Acounts instances");
		try {
			final String queryString = "select model from Acounts model";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					if (rowStartIdxAndCount != null
							&& rowStartIdxAndCount.length > 0) {
						int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
						if (rowStartIdx > 0) {
							query.setFirstResult(rowStartIdx);
						}

						if (rowStartIdxAndCount.length > 1) {
							int rowCount = Math.max(0, rowStartIdxAndCount[1]);
							if (rowCount > 0) {
								query.setMaxResults(rowCount);
							}
						}
					}
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	public static AcountsDAO getFromApplicationContext(ApplicationContext ctx) {
		return (AcountsDAO) ctx.getBean("AcountsDAO");
	}
}