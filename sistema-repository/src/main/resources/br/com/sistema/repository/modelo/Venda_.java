package br.com.sistema.repository.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-09T19:17:49.881-0300")
@StaticMetamodel(Venda.class)
public class Venda_ extends AbstractBaseEntity_ {
	public static volatile SingularAttribute<Venda, Livro> livro;
	public static volatile SingularAttribute<Venda, Integer> quantidade;
}
