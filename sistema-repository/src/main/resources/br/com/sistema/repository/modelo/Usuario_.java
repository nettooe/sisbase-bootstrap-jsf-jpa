package br.com.sistema.repository.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-09T19:17:49.880-0300")
@StaticMetamodel(Usuario.class)
public class Usuario_ extends AbstractBaseEntity_ {
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> senha;
}
