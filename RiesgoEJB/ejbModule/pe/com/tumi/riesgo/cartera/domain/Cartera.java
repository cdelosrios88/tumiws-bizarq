package pe.com.tumi.riesgo.cartera.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cartera {

	private Integer intItemCartera;
	private Integer intPersEmpresaPk;
	private String strNombre;
	private Integer intParaTipoCobranzaCod;
	private Date dtFechaInicio;
	private Date dtFechaFin;
	private Integer intPersPersonaRegistraPk;
	private Date dtFechaRegistra;
	private Integer intPersPersonaEliminaPk;
	private Date dtFechaElimina;
	private Integer intParaEstadoCod;
	private List<Producto> listaProducto;
	private List<Especificacion> listaEspecificacionProvision;
	private List<Especificacion> listaEspecificacionProciclico;
	private List<String> listaStrProductos;
	private List<Tiempo> listaTiempo;
	
	public Cartera(){
		listaProducto = new ArrayList<Producto>();
		listaEspecificacionProvision = new ArrayList<Especificacion>();
		listaEspecificacionProciclico = new ArrayList<Especificacion>();
		listaStrProductos = new ArrayList<String>();
		listaTiempo = new ArrayList<Tiempo>();
	}
	
	public Integer getIntItemCartera() {
		return intItemCartera;
	}
	public void setIntItemCartera(Integer intItemCartera) {
		this.intItemCartera = intItemCartera;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public String getStrNombre() {
		return strNombre;
	}
	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}
	public Integer getIntParaTipoCobranzaCod() {
		return intParaTipoCobranzaCod;
	}
	public void setIntParaTipoCobranzaCod(Integer intParaTipoCobranzaCod) {
		this.intParaTipoCobranzaCod = intParaTipoCobranzaCod;
	}
	public Date getDtFechaInicio() {
		return dtFechaInicio;
	}
	public void setDtFechaInicio(Date dtFechaInicio) {
		this.dtFechaInicio = dtFechaInicio;
	}
	public Date getDtFechaFin() {
		return dtFechaFin;
	}
	public void setDtFechaFin(Date dtFechaFin) {
		this.dtFechaFin = dtFechaFin;
	}
	public Integer getIntPersPersonaRegistraPk() {
		return intPersPersonaRegistraPk;
	}
	public void setIntPersPersonaRegistraPk(Integer intPersPersonaRegistraPk) {
		this.intPersPersonaRegistraPk = intPersPersonaRegistraPk;
	}
	public Date getDtFechaRegistra() {
		return dtFechaRegistra;
	}
	public void setDtFechaRegistra(Date dtFechaRegistra) {
		this.dtFechaRegistra = dtFechaRegistra;
	}
	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}
	public Date getDtFechaElimina() {
		return dtFechaElimina;
	}
	public void setDtFechaElimina(Date dtFechaElimina) {
		this.dtFechaElimina = dtFechaElimina;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public List<Producto> getListaProducto() {
		return listaProducto;
	}
	public List<Especificacion> getListaEspecificacionProvision() {
		return listaEspecificacionProvision;
	}
	public void setListaEspecificacionProvision(List<Especificacion> listaEspecificacionProvision) {
		this.listaEspecificacionProvision = listaEspecificacionProvision;
	}
	public void setListaProducto(List<Producto> listaProducto) {
		this.listaProducto = listaProducto;
	}

	public List<Especificacion> getListaEspecificacionProciclico() {
		return listaEspecificacionProciclico;
	}
	public void setListaEspecificacionProciclico(List<Especificacion> listaEspecificacionProciclico) {
		this.listaEspecificacionProciclico = listaEspecificacionProciclico;
	}
	public List<Tiempo> getListaTiempo() {
		return listaTiempo;
	}
	public void setListaTiempo(List<Tiempo> listaTiempo) {
		this.listaTiempo = listaTiempo;
	}
	public List<String> getListaStrProductos() {
		return listaStrProductos;
	}
	public void setListaStrProductos(List<String> listaStrProductos) {
		this.listaStrProductos = listaStrProductos;
	}

	@Override
	public String toString() {
		return "Cartera [intItemCartera=" + intItemCartera
				+ ", intPersEmpresaPk=" + intPersEmpresaPk + ", strNombre="
				+ strNombre + ", intParaTipoCobranzaCod="
				+ intParaTipoCobranzaCod + ", dtFechaInicio=" + dtFechaInicio
				+ ", dtFechaFin=" + dtFechaFin + ", intPersPersonaRegistraPk="
				+ intPersPersonaRegistraPk + ", dtFechaRegistra="
				+ dtFechaRegistra + ", intPersPersonaEliminaPk="
				+ intPersPersonaEliminaPk + ", dtFechaElimina="
				+ dtFechaElimina + "]";
	}
	
	
}
