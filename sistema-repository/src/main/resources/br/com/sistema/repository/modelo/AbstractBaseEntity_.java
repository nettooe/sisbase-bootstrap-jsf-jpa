package br.com.sistema.repository.modelo;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-09T19:17:49.764-0300")
@StaticMetamodel(AbstractBaseEntity.class)
public class AbstractBaseEntity_ {
	public static volatile SingularAttribute<AbstractBaseEntity, String> id;
	public static volatile SingularAttribute<AbstractBaseEntity, Integer> versao;
	public static volatile SingularAttribute<AbstractBaseEntity, Calendar> criacao;
	public static volatile SingularAttribute<AbstractBaseEntity, Calendar> atualizacao;
}
