package br.com.sistema.repository.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-09T19:17:49.873-0300")
@StaticMetamodel(Autor.class)
public class Autor_ extends AbstractBaseEntity_ {
	public static volatile SingularAttribute<Autor, String> nome;
	public static volatile SingularAttribute<Autor, String> email;
	public static volatile ListAttribute<Autor, Autoria> autorias;
}
