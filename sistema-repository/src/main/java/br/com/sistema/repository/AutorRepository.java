/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.repository;

import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.SortOrder;

import br.com.sistema.repository.modelo.Autor;

/**
 *
 * @author oliver
 */
//@TransactionManagement(TransactionManagementType.BEAN)
public class AutorRepository extends AbstractRepository<Autor> {
	private static final long serialVersionUID = 1L;

	public AutorRepository() {
		super(Autor.class);
	}

	@SuppressWarnings("unused")
	public Object[] findRange(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		Object retorno[] = new Object[2];

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Autor> query = cb.createQuery(Autor.class);
		CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);

		Root<Autor> from = query.from(Autor.class);
		Root<Autor> fromCount = queryCount.from(Autor.class);

		CriteriaQuery<Autor> select = query.select(from);
		CriteriaQuery<Long> selectCount = queryCount.select(cb.count(fromCount));

		for (Entry<String, Object> filter : filters.entrySet()) {
			Expression<String> path = from.get(filter.getKey());
			Predicate predicate = cb.like(path, filter.getValue().toString());
			CriteriaQuery<Autor> where = select.where(predicate);

			Expression<String> pathCount = fromCount.get(filter.getKey());
			Predicate predicateCount = cb.like(pathCount, filter.getValue().toString());
			CriteriaQuery<Long> whereCount = selectCount.where(predicateCount);
			selectCount.where(predicateCount);

		}

		if (null != sortField) {
			if (sortOrder.equals(SortOrder.DESCENDING)) {
				query.orderBy(cb.desc(from.get(sortField)));
			} else {
				query.orderBy(cb.asc(from.get(sortField)));
			}
		}

		javax.persistence.Query q = getEntityManager().createQuery(query);

		q.setFirstResult(first);
		q.setMaxResults(pageSize);

		TypedQuery<Long> queryCount2 = getEntityManager().createQuery(selectCount);
		Long count = queryCount2.getSingleResult();

		retorno[0] = count;
		retorno[1] = q.getResultList();

		return retorno;
	}

}
