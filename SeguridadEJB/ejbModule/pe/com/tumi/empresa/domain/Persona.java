package pe.com.tumi.empresa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.popup.domain.Comunicacion;

public class Persona extends TumiDomain{
	private 	Integer 	intIdPersona;
	private 	Integer 	intTipoPersona;
	private 	String 		strTipoPersona;
	private 	Long 		intRuc;
	private 	Date  		dtFechaBajaRuc;
	private 	String 		strFechaBajaRuc;
	private 	Integer		intEstado;
	private 	ArrayList<Domicilio> listDirecciones;
	private 	ArrayList<Comunicacion> listComunicaciones;
	
	
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntEstado() {
		return intEstado;
	}
	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}
	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}
	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}
	public Long getIntRuc() {
		return intRuc;
	}
	public void setIntRuc(Long intRuc) {
		this.intRuc = intRuc;
	}
	public Date getDtFechaBajaRuc() {
		return dtFechaBajaRuc;
	}
	public void setDtFechaBajaRuc(Date dtFechaBajaRuc) {
		this.dtFechaBajaRuc = dtFechaBajaRuc;
	}
	public String getStrFechaBajaRuc() {
		return strFechaBajaRuc;
	}
	public void setStrFechaBajaRuc(String strFechaBajaRuc) {
		this.strFechaBajaRuc = strFechaBajaRuc;
	}
	public ArrayList<Domicilio> getListDirecciones() {
		return listDirecciones;
	}
	public void setListDirecciones(ArrayList<Domicilio> listDirecciones) {
		this.listDirecciones = listDirecciones;
	}
	public ArrayList<Comunicacion> getListComunicaciones() {
		return listComunicaciones;
	}
	public void setListComunicaciones(ArrayList<Comunicacion> listComunicaciones) {
		this.listComunicaciones = listComunicaciones;
	}
	public String getStrTipoPersona() {
		return strTipoPersona;
	}
	public void setStrTipoPersona(String strTipoPersona) {
		this.strTipoPersona = strTipoPersona;
	}
	
}
