package br.com.sistema.repository.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Autor extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String nome;

	@NotNull
	private String email;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "autor", cascade = CascadeType.ALL)
	private List<Autoria> autorias = new ArrayList<Autoria>();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Autoria> getAutorias() {
		return autorias;
	}

	public void setAutorias(List<Autoria> autorias) {
		this.autorias = autorias;
	}

}
