package pe.com.tumi.seguridad.domain;

import java.util.ArrayList;
import java.util.List;
import pe.com.tumi.common.domain.TipoArbol;

public class OpcionMenu extends TipoArbol {
	
	private List roles = new ArrayList();
	private String navigationRule;
	private String controller;
	private String initMethod;
	private String tipoNodo;
	private String idPadre;	
	private String nomOpcion;
	
	public List getRoles() {
		return roles;
	}

	public void setRoles(List roles) {
		this.roles = roles;
	}

	public void setNavigationRule(String navigationRule) {
		this.navigationRule = navigationRule;
	}

	public String getNavigationRule() {
		return navigationRule;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getController() {
		return controller;
	}
	
	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}

	public String getInitMethod() {
		return initMethod;
	}

	public void setTipoNodo(String tipoNodo) {
		this.tipoNodo = tipoNodo;
	}

	public String getTipoNodo() {
		return tipoNodo;
	}
	
	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getNomOpcion() {
		return nomOpcion;
	}

	public void setNomOpcion(String nomOpcion) {
		this.nomOpcion = nomOpcion;
	}
	
	public String toString(){
		String result = OpcionMenu.class.toString();
		if (getNombre()!= null && !getNombre().equals("")){
			result = String.valueOf(getId());
		}
		return result;
	}
	
}