package org.online.model;

import java.sql.Timestamp;
import java.util.List;

/**
 * Interface for TablelistDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ITablelistDAO {
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
	 * ITablelistDAO.save(entity);
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
	public void save(Tablelist entity);

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
	 * ITablelistDAO.delete(entity);
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
	public void delete(Tablelist entity);

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
	 * entity = ITablelistDAO.update(entity);
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
	public Tablelist update(Tablelist entity);

	public Tablelist findById(String id);

	/**
	 * Find all Tablelist entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Tablelist property to query
	 * @param value
	 *            the property value to match
	 * @return List<Tablelist> found by query
	 */
	public List<Tablelist> findByProperty(String propertyName, Object value);

	public List<Tablelist> findByTadirid(Object tadirid);

	public List<Tablelist> findByTaname(Object taname);

	public List<Tablelist> findByTafinicount(Object tafinicount);

	public List<Tablelist> findByTafrom(Object tafrom);

	public List<Tablelist> findByTatype(Object tatype);

	/**
	 * Find all Tablelist entities.
	 * 
	 * @return List<Tablelist> all Tablelist entities
	 */
	public List<Tablelist> findAll();
}