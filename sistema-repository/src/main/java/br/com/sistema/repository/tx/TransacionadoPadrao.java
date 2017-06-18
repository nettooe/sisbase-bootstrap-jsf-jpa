package br.com.sistema.repository.tx;

import javax.enterprise.inject.Typed;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Priority(Interceptor.Priority.APPLICATION) /// posterior
@Typed(Transacionado.class)
public class TransacionadoPadrao implements Transacionado{

	private static final long serialVersionUID = -6107081772273959401L;
	
	@PersistenceContext(unitName = "livraria")
	protected EntityManager em;

	public Object executaComTransacao(InvocationContext context){
		em.getTransaction().begin();
		
		try {		
			Object resultado = context.proceed();
			
			em.getTransaction().commit();
			
			return resultado;
		} catch (Exception e) {
			em.getTransaction().rollback();
			
			throw new RuntimeException(e);
		}
	}
	
}
