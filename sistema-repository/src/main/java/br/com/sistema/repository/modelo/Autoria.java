package br.com.sistema.repository.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Autoria extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(targetEntity=Livro.class)
	private Livro livro;

	@NotNull
	@ManyToOne(targetEntity=Autor.class)
	private Autor autor;

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

}
