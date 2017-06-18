package br.com.sistema.web.util;

public class Autorizador {

//	@Inject
//	private FacesContext context;
//	
//	@Inject @ScopeMap(Scope.SESSION)
//	private Map<String, Object> sessionMap;
//	
//	@Inject
//	private NavigationHandler handler;
//
//	public void autoriza( @Observes @After @Phase(Phases.RESTORE_VIEW) PhaseEvent event) {
//		String nomePagina = context.getViewRoot().getViewId();
//
//		System.out.println(nomePagina);
//
//		if ("/login.xhtml".equals(nomePagina)) {
//			return;
//		} else if("/index2.xhtml".equals(nomePagina)) {
//			return; // força
//		}
//
//		Usuario usuarioLogado = (Usuario) sessionMap.get("usuarioLogado");
//
//		if (usuarioLogado != null) {
//			return;
//		}
//
//		// redirecionamento para login.xhtml
//
//		handler.handleNavigation(context, null, "/login?faces-redirect=true");
//		context.renderResponse();
//	}


}
