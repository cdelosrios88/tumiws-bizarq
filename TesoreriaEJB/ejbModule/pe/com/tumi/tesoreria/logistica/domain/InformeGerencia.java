package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;

public class InformeGerencia extends TumiDomain{

	private InformeGerenciaId id;
	private Timestamp 	tsFechaRegistro;
	private Integer 	intParaTipoInforme;
	private Integer 	intPersEmpresaRequisicion;
	private Integer 	intItemRequisicion;
	private Integer 	intPersPersonaServicio;
	private Integer 	intPersPersonaAutoriza;
	private Integer 	intSucuIdSucursal;
	private Integer 	intIdArea;
	private Date 		dtFechaInforme;
	private BigDecimal 	bdMontoAutorizado;
	private Integer 	intParaTipoMoneda;
	private String	 	strObservacion;
	private Integer 	intParaTipo;
	private Integer 	intItemArchivo;
	private Integer 	intItemHistorico;
	private Integer 	intPersEmpresaUsuario;
	private Integer 	intPersPersonaUsuario;
	private Timestamp 	tsFechaAnula;
	private Integer 	intPersEmpresaAnula;
	private Integer 	intPersPersonaAnula;
	private Integer		intParaEstado;
	
	private Requisicion	requisicion;
	private Persona 	empresaServicio;
	private Persona		personaAutoriza;
	private Sucursal	sucursal;
	private Archivo		archivoDocumento;
	private OrdenCompra	ordenCompra;
	
	private Date		dtFiltroDesde;
	private Date		dtFiltroHasta;
	
	public InformeGerencia(){
		id = new  InformeGerenciaId();
	}
	
	public InformeGerenciaId getId() {
		return id;
	}
	public void setId(InformeGerenciaId id) {
		this.id = id;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaTipoInforme() {
		return intParaTipoInforme;
	}
	public void setIntParaTipoInforme(Integer intParaTipoInforme) {
		this.intParaTipoInforme = intParaTipoInforme;
	}
	public Integer getIntPersEmpresaRequisicion() {
		return intPersEmpresaRequisicion;
	}
	public void setIntPersEmpresaRequisicion(Integer intPersEmpresaRequisicion) {
		this.intPersEmpresaRequisicion = intPersEmpresaRequisicion;
	}
	public Integer getIntItemRequisicion() {
		return intItemRequisicion;
	}
	public void setIntItemRequisicion(Integer intItemRequisicion) {
		this.intItemRequisicion = intItemRequisicion;
	}
	public Integer getIntPersPersonaServicio() {
		return intPersPersonaServicio;
	}
	public void setIntPersPersonaServicio(Integer intPersPersonaServicio) {
		this.intPersPersonaServicio = intPersPersonaServicio;
	}
	public Integer getIntPersPersonaAutoriza() {
		return intPersPersonaAutoriza;
	}
	public void setIntPersPersonaAutoriza(Integer intPersPersonaAutoriza) {
		this.intPersPersonaAutoriza = intPersPersonaAutoriza;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntIdArea() {
		return intIdArea;
	}
	public void setIntIdArea(Integer intIdArea) {
		this.intIdArea = intIdArea;
	}
	public Date getDtFechaInforme() {
		return dtFechaInforme;
	}
	public void setDtFechaInforme(Date dtFechaInforme) {
		this.dtFechaInforme = dtFechaInforme;
	}
	public BigDecimal getBdMontoAutorizado() {
		return bdMontoAutorizado;
	}
	public void setBdMontoAutorizado(BigDecimal bdMontoAutorizado) {
		this.bdMontoAutorizado = bdMontoAutorizado;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntParaTipo() {
		return intParaTipo;
	}
	public void setIntParaTipo(Integer intParaTipo) {
		this.intParaTipo = intParaTipo;
	}
	public Integer getIntItemArchivo() {
		return intItemArchivo;
	}
	public void setIntItemArchivo(Integer intItemArchivo) {
		this.intItemArchivo = intItemArchivo;
	}
	public Integer getIntItemHistorico() {
		return intItemHistorico;
	}
	public void setIntItemHistorico(Integer intItemHistorico) {
		this.intItemHistorico = intItemHistorico;
	}
	public Integer getIntPersEmpresaUsuario() {
		return intPersEmpresaUsuario;
	}
	public void setIntPersEmpresaUsuario(Integer intPersEmpresaUsuario) {
		this.intPersEmpresaUsuario = intPersEmpresaUsuario;
	}
	public Integer getIntPersPersonaUsuario() {
		return intPersPersonaUsuario;
	}
	public void setIntPersPersonaUsuario(Integer intPersPersonaUsuario) {
		this.intPersPersonaUsuario = intPersPersonaUsuario;
	}
	public Timestamp getTsFechaAnula() {
		return tsFechaAnula;
	}
	public void setTsFechaAnula(Timestamp tsFechaAnula) {
		this.tsFechaAnula = tsFechaAnula;
	}
	public Integer getIntPersEmpresaAnula() {
		return intPersEmpresaAnula;
	}
	public void setIntPersEmpresaAnula(Integer intPersEmpresaAnula) {
		this.intPersEmpresaAnula = intPersEmpresaAnula;
	}
	public Integer getIntPersPersonaAnula() {
		return intPersPersonaAnula;
	}
	public void setIntPersPersonaAnula(Integer intPersPersonaAnula) {
		this.intPersPersonaAnula = intPersPersonaAnula;
	}
	public Requisicion getRequisicion() {
		return requisicion;
	}
	public void setRequisicion(Requisicion requisicion) {
		this.requisicion = requisicion;
	}
	public Persona getEmpresaServicio() {
		return empresaServicio;
	}
	public void setEmpresaServicio(Persona empresaServicio) {
		this.empresaServicio = empresaServicio;
	}
	public Persona getPersonaAutoriza() {
		return personaAutoriza;
	}
	public void setPersonaAutoriza(Persona personaAutoriza) {
		this.personaAutoriza = personaAutoriza;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Archivo getArchivoDocumento() {
		return archivoDocumento;
	}
	public void setArchivoDocumento(Archivo archivoDocumento) {
		this.archivoDocumento = archivoDocumento;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Date getDtFiltroDesde() {
		return dtFiltroDesde;
	}
	public void setDtFiltroDesde(Date dtFiltroDesde) {
		this.dtFiltroDesde = dtFiltroDesde;
	}
	public Date getDtFiltroHasta() {
		return dtFiltroHasta;
	}
	public void setDtFiltroHasta(Date dtFiltroHasta) {
		this.dtFiltroHasta = dtFiltroHasta;
	}
	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}
	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	@Override
	public String toString() {
		return "InformeGerencia [id=" + id + ", tsFechaRegistro="
				+ tsFechaRegistro + ", intParaTipoInforme="
				+ intParaTipoInforme + ", intPersEmpresaRequisicion="
				+ intPersEmpresaRequisicion + ", intItemRequisicion="
				+ intItemRequisicion + ", intPersPersonaServicio="
				+ intPersPersonaServicio + ", intPersPersonaAutoriza="
				+ intPersPersonaAutoriza + ", intSucuIdSucursal="
				+ intSucuIdSucursal + ", intIdArea=" + intIdArea
				+ ", dtFechaInforme=" + dtFechaInforme + ", bdMontoAutorizado="
				+ bdMontoAutorizado + ", intParaTipoMoneda="
				+ intParaTipoMoneda + ", strObservacion=" + strObservacion
				+ ", intParaTipo=" + intParaTipo + ", intItemArchivo="
				+ intItemArchivo + ", intItemHistorico=" + intItemHistorico
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", tsFechaAnula=" + tsFechaAnula + ", intPersEmpresaAnula="
				+ intPersEmpresaAnula + ", intPersPersonaAnula="
				+ intPersPersonaAnula + "]";
	}	
}
