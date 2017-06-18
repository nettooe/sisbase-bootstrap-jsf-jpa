/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.repository.facade;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.swing.SortOrder;

import br.com.sistema.repository.AutorRepository;
import br.com.sistema.repository.LivroRepository;
import br.com.sistema.repository.modelo.Autor;
import br.com.sistema.repository.modelo.Livro;

/**
 *
 * @author oliver
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AutoriaFacade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private AutorRepository repositoryAutor;

	@Inject
	private LivroRepository repositoryLivro;

	public AutoriaFacade() {
	}

	public Object[] findRangeAutor(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		return this.repositoryAutor.findRange(first, pageSize, sortField, sortOrder, filters);
	}

	public Autor findAutor(String id) {
		return this.repositoryAutor.find(id);
	}

	public void remove(Autor entity) {
		this.repositoryAutor.remove(entity);

	}

	public Autor create(Autor entity) {
		this.repositoryAutor.create(entity);
		return this.repositoryAutor.find(entity.getId());
	}

	public Autor edit(Autor entity) {
		return this.repositoryAutor.edit(entity);
	}

	public List<Autor> findAllAutor() {
		return this.repositoryAutor.findAll();
	}
	
	
	///////////////////////////// Livro
	
	public Object[] findRangeLivro(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		return this.repositoryLivro.findRange(first, pageSize, sortField, sortOrder, filters);
	}
	
	public Livro findLivro(String id) {
		return this.repositoryLivro.find(id);
	}
	
	public void remove(Livro entity) {
		this.repositoryLivro.remove(entity);
	}
	
	public Livro create(Livro entity) {
		this.repositoryLivro.create(entity);
		return this.repositoryLivro.find(entity.getId());
	}
	
	public Livro edit(Livro entity) {
		return this.repositoryLivro.edit(entity);
	}
	
	public List<Livro> findAllLivro() {
		return this.repositoryLivro.findAll();
	}

}
