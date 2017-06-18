package br.com.sistema.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sistema.repository.modelo.Autor;

@FacesConverter("autorConverter")
public class AutorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String[] strArr = value.split("-");
		if (strArr.length >= 3) {
			Autor obj = new Autor();
			obj.setId(strArr[0]);
			obj.setNome(strArr[1]);
			obj.setEmail(strArr[2]);
			return obj;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null ){
			Autor obj = (Autor)value;
			StringBuffer strBuff = new StringBuffer(obj.getId()).append("-");
			strBuff.append(obj.getNome()).append("-").append(obj.getEmail());
			String studentStr = strBuff.toString();
			return studentStr;
		}
		return null;
	}

}
