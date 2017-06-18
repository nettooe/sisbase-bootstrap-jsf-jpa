package br.com.sistema.repository.facade;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.sistema.repository.LivroRepository;
import br.com.sistema.repository.modelo.Livro;

@Path("/autoria")
public class AutoriaFacadeRS {

	@Inject
	private LivroRepository repositoryLivro;
	
//	@Resource(name="jdbc/livraria")
//	private DataSource dataSource;
	

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarTodosLivros() throws JSONException {

//		JSONObject jsonObject = new JSONObject();
//		Double fahrenheit = 98.24;
//		Double celsius;
//		celsius = (fahrenheit - 32) * 5 / 9;
//		jsonObject.put("F Value", fahrenheit);
//		jsonObject.put("C Value", celsius);
//
//		String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;

		List<Livro> livros = this.repositoryLivro.findAll();
		ListLivros list = new ListLivros();
		list.entities = livros;
		
		return Response.status(200).entity(list).build();
	}

	@Path("{f}")
	@GET
	@Produces("application/json")
	public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		float celsius;
		celsius = (f - 32) * 5 / 9;
		jsonObject.put("F Value", f);
		jsonObject.put("C Value", celsius);

		String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
	
	
	@GET
	@Path("/grafico")
	@Produces(MediaType.APPLICATION_JSON)
	public Response grafico() throws JSONException {
		
//		JSONObject colsA = new JSONObject();
//		colsA.put("id", "");
//		colsA.put("label", "Topping");
//		colsA.put("pattern", "");
//		colsA.put("type", "string");
//		
//		JSONObject colsB = new JSONObject();
//		colsB.put("id", "");
//		colsB.put("label", "Topping");
//		colsB.put("pattern", "");
//		colsB.put("type", "string");
//		
////		String[] cols = {"name:'Ano'", "type:'string'"};
//		String[] rows = {"1", "2"};
//		
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("col", colsA);
////		jsonObject.put("rows", rows);

		String result = "{"
				+ " \"cols\": ["
				+ "   {\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"},"
			    + "   {\"id\":\"\",\"label\":\"Slices\",\"pattern\":\"\",\"type\":\"number\"}"
			    + "  ],"
			    + " \"rows\": ["
			    + "    {\"c\":[{\"v\":\"Mushrooms\",\"f\":null},{\"v\":3,\"f\":null}]},"
			    + "    {\"c\":[{\"v\":\"Onions\",\"f\":null},{\"v\":1,\"f\":null}]},"
			    + "    {\"c\":[{\"v\":\"Olives\",\"f\":null},{\"v\":1,\"f\":null}]},"
			    + "    {\"c\":[{\"v\":\"Zucchini\",\"f\":null},{\"v\":1,\"f\":null}]},"
			    + "    {\"c\":[{\"v\":\"Pepperoni\",\"f\":null},{\"v\":2,\"f\":null}]}"
			    + "  ]"
			    + "}";
		return Response.status(200).entity(result).build();
	}
	
	
	
	
	@XmlRootElement
	public static class ListLivros {
	  @XmlElement(name = "livros")
	  private List<Livro> entities;
	}

}
