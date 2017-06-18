package br.com.sistema.repository.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Usuario extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String email;
	
	private String senha;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
