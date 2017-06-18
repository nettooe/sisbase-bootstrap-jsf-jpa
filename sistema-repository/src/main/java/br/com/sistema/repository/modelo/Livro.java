package br.com.sistema.repository.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="livro")
public class Livro extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String titulo;
	
	private String isbn;
	
	private double preco;

	@Temporal(TemporalType.DATE)
	private Calendar dataLancamento = Calendar.getInstance();

	@OneToMany(fetch=FetchType.EAGER, mappedBy="livro", cascade=CascadeType.ALL)
	private List<Autoria> autorias = new ArrayList<Autoria>();


	public Livro() {
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public void removeAutoria(Autoria autoria) {
		this.autorias.remove(autoria);
	}

	public List<Autoria> getAutorias() {
		return autorias;
	}

	public void setAutorias(List<Autoria> autorias) {
		this.autorias = autorias;
	}

}