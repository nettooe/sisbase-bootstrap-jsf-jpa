package br.com.sistema.web.controller.autor;

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
import br.com.sistema.repository.modelo.Autor;
import br.com.sistema.web.util.JsfUtil;

@Named("autorController")
@ViewScoped
public class AutorController implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private AutoriaFacade ejbAutorFacade;

	private List<Autor> items = null;

	private Autor entityPesquisar = new Autor();

	private Autor selected;

	// paginação
	private int pageSize;
	private int totalRows;
	private int totalPages;
	private int currentPage;

	public AutorController() {
		selected = new Autor();
		entityPesquisar = new Autor();
		entityPesquisar.setNome("%ci%");
	}

	@PostConstruct
	public void verificarId() {
		Object id = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("id");
		if (id != null) {
			selected = ejbAutorFacade.findAutor((String) id);
		}
	}
	
	public void excluirSelecionado() {
		try {
			this.ejbAutorFacade.remove(selected);
			selected = new Autor();
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

	public Autor getSelected() {
		System.out.println("Obtendo autor: " + selected.getNome());
		return selected;
	}

	public void setSelected(Autor selected) {
		this.selected = selected;
		System.out.println("Autor selecionado: " + selected.getNome());
	}

	private AutoriaFacade getFacade() {
		return ejbAutorFacade;
	}

	public void gravar() {
		try {
			if (selected.getId() == null) {
				selected = this.ejbAutorFacade.create(selected);
			} else {
				selected = this.ejbAutorFacade.edit(selected);
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

	public Autor getAutor(java.lang.String id) {
		return getFacade().findAutor(id);
	}

	public List<Autor> getItemsAvailableSelectMany() {
		return getFacade().findAllAutor();
	}

	public List<Autor> getItemsAvailableSelectOne() {
		return getFacade().findAllAutor();
	}

	public List<Autor> getItems() {
		if (items == null || items.isEmpty()) {
			// items = ejbFacade.findAll();
			items = new ArrayList<Autor>();
		}
		return items;
	}

	public void setItems(List<Autor> items) {
		this.items = items;
	}

	@FacesConverter(forClass = Autor.class)
	public static class AutorControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			AutorController controller = (AutorController) facesContext.getApplication().getELResolver()
					.getValue(facesContext.getELContext(), null, "autorController");
			return controller.getAutor(getKey(value));
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
			if (object instanceof Autor) {
				Autor o = (Autor) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Autor.class.getName() });
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

		if (null != entityPesquisar.getNome() && !entityPesquisar.getNome().replaceAll("%", "").trim().isEmpty()) {
			filters.put("nome", entityPesquisar.getNome());
		}
		if (null != entityPesquisar.getEmail() && !entityPesquisar.getEmail().replaceAll("%", "").trim().isEmpty()) {
			filters.put("email", entityPesquisar.getEmail());
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

		Object[] retorno = this.ejbAutorFacade.findRangeAutor(first, pageSize, sortField, sortOrder, filters);

		this.items = (List<Autor>) retorno[1];

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

	public Autor getEntityPesquisar() {
		return entityPesquisar;
	}

	public void setEntityPesquisar(Autor entityPesquisar) {
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
