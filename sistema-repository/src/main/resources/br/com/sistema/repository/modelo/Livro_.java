package br.com.sistema.repository.modelo;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-09T19:17:49.879-0300")
@StaticMetamodel(Livro.class)
public class Livro_ extends AbstractBaseEntity_ {
	public static volatile SingularAttribute<Livro, String> titulo;
	public static volatile SingularAttribute<Livro, String> isbn;
	public static volatile SingularAttribute<Livro, Double> preco;
	public static volatile SingularAttribute<Livro, Calendar> dataLancamento;
	public static volatile ListAttribute<Livro, Autoria> autorias;
}
