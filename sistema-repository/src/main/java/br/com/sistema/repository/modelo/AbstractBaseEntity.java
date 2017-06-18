package br.com.sistema.repository.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Version
	private Integer versao;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar criacao;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar atualizacao;

	@PrePersist
	private void atribuirId() {
		this.id = UUID.randomUUID().toString();
		this.criacao = this.atualizacao = Calendar.getInstance();
	}
	
	@PreUpdate
	private void atualizar(){
		this.atualizacao = Calendar.getInstance(TimeZone.getDefault());
	}

	public AbstractBaseEntity() {
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractBaseEntity)) {
			return false;
		}
		AbstractBaseEntity other = (AbstractBaseEntity) obj;
		return getId().equals(other.getId());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public Calendar getCriacao() {
		return criacao;
	}

	public void setCriacao(Calendar criacao) {
		this.criacao = criacao;
	}

	public Calendar getAtualizacao() {
		return atualizacao;
	}

	public void setAtualizacao(Calendar atualizacao) {
		this.atualizacao = atualizacao;
	}
	

}
