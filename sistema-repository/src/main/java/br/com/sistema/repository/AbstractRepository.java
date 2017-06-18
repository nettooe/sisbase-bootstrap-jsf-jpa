package br.com.sistema.repository;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractRepository<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Class<T> entityClass;

	@PersistenceContext(unitName = "livraria")
	private EntityManager em; // =
								// Persistence.createEntityManagerFactory("livraria").createEntityManager();

	public AbstractRepository(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	// protected abstract EntityManager getEntityManager();

//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(T entity) {
		getEntityManager().persist(entity);
		getEntityManager().flush();
	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T edit(T entity) {
		T merge = getEntityManager().merge(entity);
		getEntityManager().flush();
		return merge;
	}

//	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	public List<T> findAll() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	public List<T> findRange(int[] range) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public int count() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
