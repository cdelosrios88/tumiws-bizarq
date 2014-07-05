package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class Requisicion extends TumiDomain{

	private RequisicionId id;
	private Timestamp 	tsFechaRequisicion;
	private Integer 	intParaTipoRequisicion;
	private Integer 	intParaTipoAprobacion;
	private Integer 	intPersEmpresaSolicitante;
	private Integer 	intPersPersonaSolicitante;
	private Integer 	intSucuIdSucursal;
	private Integer 	intIdArea;
	private String 		strObservacion;
	private BigDecimal 	bdMontoEstimado;
	private Integer 	intParaTipoMoneda;
	private Integer 	intParaEstado;
	private Integer 	intPersEmpresaJefatura;
	private Integer		intPersPersonaJefatura;
	private Integer 	intSucuIdSucursalJefatura;
	private String 		strFundamentoJefatura;
	private Integer 	intParaEstadoAprobacionJefatura;
	private Integer 	intPersEmpresaLogistica;
	private Integer 	intPersPersonaLogistica;
	private Integer 	intSucuIdSucursalLogistica;
	private String 		strFundamentoLogistica;
	private Integer 	intParaEstadoAprobacionLogistica;
	private Timestamp 	tsFechaAnula;
	private Integer 	intPersEmpresaAnula;
	private Integer 	intPersPersonaAnula;
	private BigDecimal	bdMontoJefatura;
	private BigDecimal	bdMontoLogistica;
	
	private List<RequisicionDetalle>	listaRequisicionDetalle;
	private	Persona		personaSolicitante;
	private	Persona		personaJefatura;
	private	Persona		personaLogistica;
	private Sucursal	sucursalSolicitante;
	private Sucursal	sucursalJefatura;
	private Sucursal	sucursalLogistica;
	private Area		area;
	private String		strEtiqueta;
	
	private Contrato			contrato;
	private InformeGerencia		informeGerencia;
	private CuadroComparativo	cuadroComparativo;
	private OrdenCompra			ordenCompra;
	
	//Agregado por cdelosrios, 28/09/2013
	private Date 	tsFechaRequisicionIni;
	private Date	tsFechaRequisicionFin;
	//Fin agregado por cdelosrios, 28/09/2013
	
	public Requisicion(){
		id = new RequisicionId();
		listaRequisicionDetalle = new ArrayList<RequisicionDetalle>();
	}

	public RequisicionId getId() {
		return id;
	}
	public void setId(RequisicionId id) {
		this.id = id;
	}	
	public Timestamp getTsFechaRequisicion() {
		return tsFechaRequisicion;
	}
	public void setTsFechaRequisicion(Timestamp tsFechaRequisicion) {
		this.tsFechaRequisicion = tsFechaRequisicion;
	}
	public Integer getIntParaTipoRequisicion() {
		return intParaTipoRequisicion;
	}
	public void setIntParaTipoRequisicion(Integer intParaTipoRequisicion) {
		this.intParaTipoRequisicion = intParaTipoRequisicion;
	}
	public Integer getIntParaTipoAprobacion() {
		return intParaTipoAprobacion;
	}
	public void setIntParaTipoAprobacion(Integer intParaTipoAprobacion) {
		this.intParaTipoAprobacion = intParaTipoAprobacion;
	}
	public Integer getIntPersEmpresaSolicitante() {
		return intPersEmpresaSolicitante;
	}
	public void setIntPersEmpresaSolicitante(Integer intPersEmpresaSolicitante) {
		this.intPersEmpresaSolicitante = intPersEmpresaSolicitante;
	}
	public Integer getIntPersPersonaSolicitante() {
		return intPersPersonaSolicitante;
	}
	public void setIntPersPersonaSolicitante(Integer intPersPersonaSolicitante) {
		this.intPersPersonaSolicitante = intPersPersonaSolicitante;
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
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public BigDecimal getBdMontoEstimado() {
		return bdMontoEstimado;
	}
	public void setBdMontoEstimado(BigDecimal bdMontoEstimado) {
		this.bdMontoEstimado = bdMontoEstimado;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntPersEmpresaJefatura() {
		return intPersEmpresaJefatura;
	}
	public void setIntPersEmpresaJefatura(Integer intPersEmpresaJefatura) {
		this.intPersEmpresaJefatura = intPersEmpresaJefatura;
	}
	public Integer getIntSucuIdSucursalJefatura() {
		return intSucuIdSucursalJefatura;
	}
	public void setIntSucuIdSucursalJefatura(Integer intSucuIdSucursalJefatura) {
		this.intSucuIdSucursalJefatura = intSucuIdSucursalJefatura;
	}
	public String getStrFundamentoJefatura() {
		return strFundamentoJefatura;
	}
	public void setStrFundamentoJefatura(String strFundamentoJefatura) {
		this.strFundamentoJefatura = strFundamentoJefatura;
	}
	public Integer getIntParaEstadoAprobacionJefatura() {
		return intParaEstadoAprobacionJefatura;
	}
	public void setIntParaEstadoAprobacionJefatura(Integer intParaEstadoAprobacionJefatura) {
		this.intParaEstadoAprobacionJefatura = intParaEstadoAprobacionJefatura;
	}
	public Integer getIntPersEmpresaLogistica() {
		return intPersEmpresaLogistica;
	}
	public void setIntPersEmpresaLogistica(Integer intPersEmpresaLogistica) {
		this.intPersEmpresaLogistica = intPersEmpresaLogistica;
	}
	public Integer getIntPersPersonaLogistica() {
		return intPersPersonaLogistica;
	}
	public void setIntPersPersonaLogistica(Integer intPersPersonaLogistica) {
		this.intPersPersonaLogistica = intPersPersonaLogistica;
	}
	public Integer getIntSucuIdSucursalLogistica() {
		return intSucuIdSucursalLogistica;
	}
	public void setIntSucuIdSucursalLogistica(Integer intSucuIdSucursalLogistica) {
		this.intSucuIdSucursalLogistica = intSucuIdSucursalLogistica;
	}
	public String getStrFundamentoLogistica() {
		return strFundamentoLogistica;
	}
	public void setStrFundamentoLogistica(String strFundamentoLogistica) {
		this.strFundamentoLogistica = strFundamentoLogistica;
	}
	public Integer getIntParaEstadoAprobacionLogistica() {
		return intParaEstadoAprobacionLogistica;
	}
	public void setIntParaEstadoAprobacionLogistica(Integer intParaEstadoAprobacionLogistica) {
		this.intParaEstadoAprobacionLogistica = intParaEstadoAprobacionLogistica;
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
	public Integer getIntPersPersonaJefatura() {
		return intPersPersonaJefatura;
	}
	public void setIntPersPersonaJefatura(Integer intPersPersonaJefatura) {
		this.intPersPersonaJefatura = intPersPersonaJefatura;
	}
	public List<RequisicionDetalle> getListaRequisicionDetalle() {
		return listaRequisicionDetalle;
	}
	public void setListaRequisicionDetalle(List<RequisicionDetalle> listaRequisicionDetalle) {
		this.listaRequisicionDetalle = listaRequisicionDetalle;
	}
	public Persona getPersonaSolicitante() {
		return personaSolicitante;
	}
	public void setPersonaSolicitante(Persona personaSolicitante) {
		this.personaSolicitante = personaSolicitante;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public BigDecimal getBdMontoJefatura() {
		return bdMontoJefatura;
	}
	public void setBdMontoJefatura(BigDecimal bdMontoJefatura) {
		this.bdMontoJefatura = bdMontoJefatura;
	}
	public BigDecimal getBdMontoLogistica() {
		return bdMontoLogistica;
	}
	public void setBdMontoLogistica(BigDecimal bdMontoLogistica) {
		this.bdMontoLogistica = bdMontoLogistica;
	}
	public Persona getPersonaJefatura() {
		return personaJefatura;
	}
	public void setPersonaJefatura(Persona personaJefatura) {
		this.personaJefatura = personaJefatura;
	}
	public Persona getPersonaLogistica() {
		return personaLogistica;
	}
	public void setPersonaLogistica(Persona personaLogistica) {
		this.personaLogistica = personaLogistica;
	}
	public Sucursal getSucursalSolicitante() {
		return sucursalSolicitante;
	}
	public void setSucursalSolicitante(Sucursal sucursalSolicitante) {
		this.sucursalSolicitante = sucursalSolicitante;
	}
	public Sucursal getSucursalJefatura() {
		return sucursalJefatura;
	}
	public void setSucursalJefatura(Sucursal sucursalJefatura) {
		this.sucursalJefatura = sucursalJefatura;
	}
	public Sucursal getSucursalLogistica() {
		return sucursalLogistica;
	}
	public void setSucursalLogistica(Sucursal sucursalLogistica) {
		this.sucursalLogistica = sucursalLogistica;
	}
	public String getStrEtiqueta() {
		return strEtiqueta;
	}
	public void setStrEtiqueta(String strEtiqueta) {
		this.strEtiqueta = strEtiqueta;
	}
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	public InformeGerencia getInformeGerencia() {
		return informeGerencia;
	}
	public void setInformeGerencia(InformeGerencia informeGerencia) {
		this.informeGerencia = informeGerencia;
	}
	public CuadroComparativo getCuadroComparativo() {
		return cuadroComparativo;
	}
	public void setCuadroComparativo(CuadroComparativo cuadroComparativo) {
		this.cuadroComparativo = cuadroComparativo;
	}
	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}
	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public Date getTsFechaRequisicionIni() {
		return tsFechaRequisicionIni;
	}

	public void setTsFechaRequisicionIni(Date tsFechaRequisicionIni) {
		this.tsFechaRequisicionIni = tsFechaRequisicionIni;
	}

	public Date getTsFechaRequisicionFin() {
		return tsFechaRequisicionFin;
	}

	public void setTsFechaRequisicionFin(Date tsFechaRequisicionFin) {
		this.tsFechaRequisicionFin = tsFechaRequisicionFin;
	}

	@Override
	public String toString() {
		return "Requisicion [id=" + id + ", tsFechaRequisicion="
				+ tsFechaRequisicion + ", intParaTipoRequisicion="
				+ intParaTipoRequisicion + ", intParaTipoAprobacion="
				+ intParaTipoAprobacion + ", intPersEmpresaSolicitante="
				+ intPersEmpresaSolicitante + ", intPersPersonaSolicitante="
				+ intPersPersonaSolicitante + ", intSucuIdSucursal="
				+ intSucuIdSucursal + ", intIdArea=" + intIdArea
				+ ", strObservacion=" + strObservacion + ", bdMontoEstimado="
				+ bdMontoEstimado + ", intParaTipoMoneda=" + intParaTipoMoneda
				+ ", intParaEstado=" + intParaEstado
				+ ", intPersEmpresaJefatura=" + intPersEmpresaJefatura
				+ ", intPersPersonaJefatura=" + intPersPersonaJefatura
				+ ", intSucuIdSucursalJefatura=" + intSucuIdSucursalJefatura
				+ ", strFundamentoJefatura=" + strFundamentoJefatura
				+ ", intParaEstadoAprobacionJefatura="
				+ intParaEstadoAprobacionJefatura
				+ ", intPersEmpresaLogistica=" + intPersEmpresaLogistica
				+ ", intPersPersonaLogistica=" + intPersPersonaLogistica
				+ ", intSucuIdSucursalLogistica=" + intSucuIdSucursalLogistica
				+ ", strFundamentoLogistica=" + strFundamentoLogistica
				+ ", intParaEstadoAprobacionLogistica="
				+ intParaEstadoAprobacionLogistica + ", tsFechaAnula="
				+ tsFechaAnula + ", intPersEmpresaAnula=" + intPersEmpresaAnula
				+ ", intPersPersonaAnula=" + intPersPersonaAnula + "]";
	}	
}