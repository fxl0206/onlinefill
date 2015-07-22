package org.online.dao;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.online.model.Tablelist;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tablelist entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.online.dao.Tablelist
 * @author MyEclipse Persistence Tools
 */

public class TablelistDAO extends JpaDaoSupport {
	// property constants
	public static final String TADIRID = "tadirid";
	public static final String TANAME = "taname";
	public static final String TAFINICOUNT = "tafinicount";
	public static final String TAFROM = "tafrom";
	public static final String TATYPE = "tatype";

	/**
	 * Perform an initial save of a previously unsaved Tablelist entity. All
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
	 * TablelistDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Tablelist entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Tablelist entity) {
		logger.info("saving Tablelist instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Tablelist entity. This operation must be performed
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
	 * TablelistDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Tablelist entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Tablelist entity) {
		logger.info("deleting Tablelist instance");
		try {
			entity = getJpaTemplate().getReference(Tablelist.class,
					entity.getTaid());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Tablelist entity and return it or a copy of it
	 * to the sender. A copy of the Tablelist entity parameter is returned when
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
	 * entity = TablelistDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Tablelist entity to update
	 * @return Tablelist the persisted Tablelist entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Tablelist update(Tablelist entity) {
		logger.info("updating Tablelist instance");
		try {
			Tablelist result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public Tablelist findById(String id) {
		logger.info("finding Tablelist instance with id: " + id);
		try {
			Tablelist instance = getJpaTemplate().find(Tablelist.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all Tablelist entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Tablelist property to query
	 * @param value
	 *            the property value to match
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            number of results to return.
	 * @return List<Tablelist> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Tablelist> findByProperty(String propertyName,
			final Object value, final int... rowStartIdxAndCount) {
		logger.info("finding Tablelist instance with property: " + propertyName
				+ ", value: " + value);
		try {
			final String queryString = "select model from Tablelist model where model."
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

	public List<Tablelist> findByTadirid(Object tadirid,
			int... rowStartIdxAndCount) {
		return findByProperty(TADIRID, tadirid, rowStartIdxAndCount);
	}

	public List<Tablelist> findByTaname(Object taname,
			int... rowStartIdxAndCount) {
		return findByProperty(TANAME, taname, rowStartIdxAndCount);
	}

	public List<Tablelist> findByTafinicount(Object tafinicount,
			int... rowStartIdxAndCount) {
		return findByProperty(TAFINICOUNT, tafinicount, rowStartIdxAndCount);
	}

	public List<Tablelist> findByTafrom(Object tafrom,
			int... rowStartIdxAndCount) {
		return findByProperty(TAFROM, tafrom, rowStartIdxAndCount);
	}

	public List<Tablelist> findByTatype(Object tatype,
			int... rowStartIdxAndCount) {
		return findByProperty(TATYPE, tatype, rowStartIdxAndCount);
	}

	/**
	 * Find all Tablelist entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<Tablelist> all Tablelist entities
	 */
	@SuppressWarnings("unchecked")
	public List<Tablelist> findAll(final int... rowStartIdxAndCount) {
		logger.info("finding all Tablelist instances");
		try {
			final String queryString = "select model from Tablelist model";
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

	public static TablelistDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TablelistDAO) ctx.getBean("TablelistDAO");
	}
}