package br.com.sistema.web.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.sistema.repository.facade.AutoriaFacade;
import br.com.sistema.repository.modelo.Autor;
import br.com.sistema.web.util.JsfUtil;
import br.com.sistema.web.util.random.Random;

@Named("geradorController")
@RequestScoped
public class GeradorController implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @EJB
    private AutoriaFacade facade;
    
    public GeradorController() {
    }

    public String gerar(){
    	
    	for(int i = 0; i < 150; i++){
    		Autor autorGerado = Random.gerarAutor();
			facade.create(autorGerado);
    	}
    	
    	JsfUtil.addSuccessMessage("Autores gerados automaticamente com sucesso");
    	
    	return "";
    }
    
    
}
