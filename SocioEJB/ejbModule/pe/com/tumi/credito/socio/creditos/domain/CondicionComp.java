package pe.com.tumi.credito.socio.creditos.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class CondicionComp extends TumiDomain{
	private		CondicionCredito 	condicionCredito;
	private		Tabla		tabla;
	private		List<Tabla> listaTabla;
	private		Boolean		chkSocio;
	
	//Getters y Setters
	public CondicionCredito getCondicion() {
		return condicionCredito;
	}
	public void setCondicion(CondicionCredito condicionCredito) {
		this.condicionCredito = condicionCredito;
	}
	public Tabla getTabla() {
		return tabla;
	}
	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}
	public List<Tabla> getListaTabla() {
		return listaTabla;
	}
	public void setListaTabla(List<Tabla> listaTabla) {
		this.listaTabla = listaTabla;
	}
	public Boolean getChkSocio() {
		return chkSocio;
	}
	public void setChkSocio(Boolean chkSocio) {
		this.chkSocio = chkSocio;
	}
}
