package br.com.sistema.repository.tx;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Typed;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Alternative
@Priority(Interceptor.Priority.APPLICATION)
@Typed(Transacionado.class)
public class MeuGerenciadorDeTransacao implements Transacionado {

	private static final long serialVersionUID = -8590951365580768798L;

	@PersistenceContext(unitName = "livraria")
	private EntityManager em;

	@Override
	public Object executaComTransacao(InvocationContext context) {

		System.out.println("Abrindo uma tranasação");
		em.getTransaction().begin();

		try {
			System.out.println("Antes de executar o método interceptado");
			Object resultado = context.proceed();

			System.out.println("Antes de commitar a transação");
			em.getTransaction().commit();

			return resultado;
		} catch (Exception e) {

			System.out.println("Antes de efetuar o rollback da transação");
			em.getTransaction().rollback();

			throw new RuntimeException(e);
		}

	}

}
