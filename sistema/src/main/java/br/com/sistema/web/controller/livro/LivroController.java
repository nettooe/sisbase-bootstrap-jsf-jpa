package br.com.sistema.web.controller.livro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.swing.SortOrder;

import br.com.sistema.repository.facade.AutoriaFacade;
import br.com.sistema.repository.modelo.Livro;
import br.com.sistema.web.util.JsfUtil;

@Named("livroController")
@ViewScoped
public class LivroController implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private AutoriaFacade ejbAutoriaFacade;

	private List<Livro> items = null;

	private Livro entityPesquisar = new Livro();

	private Livro selected;

	// paginação
	private int pageSize;
	private int totalRows;
	private int totalPages;
	private int currentPage;

	public LivroController() {
		selected = new Livro();
		entityPesquisar = new Livro();
		entityPesquisar.setTitulo("%a%");
	}

	@PostConstruct
	public void verificarId() {
		Object id = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("id");
		if (id != null) {
			selected = ejbAutoriaFacade.findLivro((String) id);
		}
	}

	public void excluirSelecionado() {
		try {
			this.ejbAutoriaFacade.remove(selected);
			selected = new Livro();
			JsfUtil.addSuccessMessage("Registro excluído com sucesso.");
		} catch (EJBException ex) {
			String msg = "";
			Throwable cause = ex.getCause();
			if (cause != null) {
				msg = cause.getLocalizedMessage();
			}
			if (msg.length() > 0) {
				JsfUtil.addErrorMessage(msg);
			} else {
				JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("message").getString("PersistenceErrorOccured"));
			}
		} catch (Exception ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("message").getString("PersistenceErrorOccured"));
		}
	}

	public String iniciarEdicao(String id) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id", id);
		return "novo?faces-redirect=true";
	}

	public Livro getSelected() {
		System.out.println("Obtendo livro: " + selected.getTitulo());
		return selected;
	}

	public void setSelected(Livro selected) {
		this.selected = selected;
		System.out.println("Livro selecionado: " + selected.getIsbn());
	}

	private AutoriaFacade getFacade() {
		return ejbAutoriaFacade;
	}

	public void gravar() {
		try {
			if (selected.getId() == null) {
				selected = this.ejbAutoriaFacade.create(selected);
			} else {
				selected = this.ejbAutoriaFacade.edit(selected);
			}
			JsfUtil.addSuccessMessage("Registro gravado com sucesso.");
		} catch (EJBException ex) {
			String msg = "";
			Throwable cause = ex.getCause();
			if (cause != null) {
				msg = cause.getLocalizedMessage();
			}
			if (msg.length() > 0) {
				JsfUtil.addErrorMessage(msg);
			} else {
				JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("message").getString("PersistenceErrorOccured"));
			}
		} catch (Exception ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("message").getString("PersistenceErrorOccured"));
		}
	}

	public Livro getLivro(java.lang.String id) {
		return getFacade().findLivro(id);
	}

	public List<Livro> getItemsAvailableSelectMany() {
		return getFacade().findAllLivro();
	}

	public List<Livro> getItemsAvailableSelectOne() {
		return getFacade().findAllLivro();
	}

	public List<Livro> getItems() {
		if (items == null || items.isEmpty()) {
			// items = ejbFacade.findAll();
			items = new ArrayList<Livro>();
		}
		return items;
	}

	public void setItems(List<Livro> items) {
		this.items = items;
	}

	@FacesConverter(forClass = Livro.class)
	public static class LivroControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			LivroController controller = (LivroController) facesContext.getApplication().getELResolver()
					.getValue(facesContext.getELContext(), null, "livroController");
			return controller.getLivro(getKey(value));
		}

		java.lang.String getKey(String value) {
			java.lang.String key;
			key = String.valueOf(value);
			return key;
		}

		String getStringKey(java.lang.String value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Livro) {
				Livro o = (Livro) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Livro.class.getName() });
				return null;
			}
		}

	}

	public void paginarPrimeira() {
		setCurrentPage(1);
		this.pesquisar();
	}

	public void paginarAnterior() {
		setCurrentPage(this.getCurrentPage() - 1);
		this.pesquisar();
	}

	public void paginarProxima() {
		setCurrentPage(this.getCurrentPage() + 1);
		this.pesquisar();
	}

	public void paginarUltima() {
		setCurrentPage(this.getTotalPages());
		this.pesquisar();
	}

	@SuppressWarnings("unchecked")
	public void pesquisar() {

		String sortField = null;
		SortOrder sortOrder = null;
		Map<String, Object> filters = new HashMap<String, Object>();

		if (null != entityPesquisar.getTitulo() && !entityPesquisar.getTitulo().replaceAll("%", "").trim().isEmpty()) {
			filters.put("titulo", entityPesquisar.getTitulo());
		}
		if (null != entityPesquisar.getIsbn() && !entityPesquisar.getIsbn().replaceAll("%", "").trim().isEmpty()) {
			filters.put("isbn", entityPesquisar.getIsbn());
		}

		if (filters.isEmpty()) {
			JsfUtil.addWarningMessage("É necessário informar algum parâmetro para pesquisa");
			return;
		}

		if (pageSize == 0) {
			pageSize = 10;
		}
		int first = (this.getCurrentPage() - 1) * pageSize;

		// se o usuário navegar para uma página que não existe..
		if (first > totalRows) {
			// então a primeira página adeverá ser a última possível
			first = (totalRows / pageSize);
			if (totalRows % pageSize > 0) {
				first++;
			}
			this.setCurrentPage(first);
		}

		Object[] retorno = this.ejbAutoriaFacade.findRangeLivro(first, pageSize, sortField, sortOrder, filters);

		this.items = (List<Livro>) retorno[1];

		if (this.items.isEmpty()) {
			JsfUtil.addWarningMessage("Nenhum registro foi encontrado com os parâmetros informados");
		}

		this.totalRows = Long.valueOf((Long) retorno[0]).intValue();
		this.totalPages = totalRows / pageSize;

		if (Double.valueOf(totalRows) / pageSize % totalPages > 0) {
			totalPages++;
		}
		if (totalPages == 0) {
			totalPages++;
		}

	}

	public Livro getEntityPesquisar() {
		return entityPesquisar;
	}

	public void setEntityPesquisar(Livro entityPesquisar) {
		this.entityPesquisar = entityPesquisar;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		if (currentPage == 0) {
			currentPage = 1;
		}
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
