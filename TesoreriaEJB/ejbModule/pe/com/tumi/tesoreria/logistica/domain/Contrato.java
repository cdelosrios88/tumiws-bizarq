package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.core.domain.Persona;

public class Contrato extends TumiDomain{

	private ContratoId	id;
	private Integer	intPersEmpresaRequisicion;
	private Integer	intItemRequisicion;
	private Timestamp	tsFechaContrato;
	private Integer	intParaTipoContrato;
	private Integer	intParatTipoEmpresaServicio;
	private Integer	intPersPersonaServicio;
	private Integer	intDomicilio;
	private Integer	intParaTipoEmpresaSolicita;
	private Integer	intPersPersonaSolicita;
	private Integer	intPersEmpresaSucursal;
	private Integer	intSucuIdSucursal;
	private Integer	intIdAreaSolicitante;
	private Date	dtFechaInicio;
	private Date	dtFechaFin;
	private Integer	intParaTipoFrecuencia;
	private Integer	intPagoUnico;
	private BigDecimal	bdMontoContrato;
	private Integer	intParaTipoMoneda;
	private Integer	intRenovacion;
	private BigDecimal	bdMontoGarantia;
	private Integer	intParaTipoMonedaGarantia;
	private String	strObservacion;
	private Integer	intPersEmpresaAnterior;
	private Integer	intItemContratoAnterior;
	private Integer	intPersEmpresaUsuario;
	private Integer	intPersPersonaUsuario;
	private Timestamp	tsFechaAnula;
	private Integer	intPersEmpresaAnula;
	private Integer	intPersPersonaAnula;
	private Integer	intParaTipo;
	private Integer	intItemArchivo;
	private Integer	intItemHistorico;
	private Integer intParaEstado;	
	
	private Requisicion	requisicion;
	private Persona		empresaServicio;
	private Domicilio	domicilioEmpresaServicio;
	private Persona		empresaSolicita;
	private Archivo 	archivoDocumento;
	private Sucursal	sucursal;
	private Contrato	contratoAnterior;
	private OrdenCompra	ordenCompra;

	private boolean	seleccionaPagoUnico;
	private boolean	seleccionaRenovacion;
	
	private Date dtFiltroInicioDesde;
	private Date dtFiltroInicioHasta;
	private Date dtFiltroFinDesde;
	private Date dtFiltroFinHasta;
	private String	strEtiqueta;
	private String	strCantidadDias;
	
	public Contrato(){
		id = new ContratoId();
	}
	
	
	public ContratoId getId() {
		return id;
	}
	public void setId(ContratoId id) {
		this.id = id;
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
	public Timestamp getTsFechaContrato() {
		return tsFechaContrato;
	}
	public void setTsFechaContrato(Timestamp tsFechaContrato) {
		this.tsFechaContrato = tsFechaContrato;
	}
	public Integer getIntParaTipoContrato() {
		return intParaTipoContrato;
	}
	public void setIntParaTipoContrato(Integer intParaTipoContrato) {
		this.intParaTipoContrato = intParaTipoContrato;
	}
	public Integer getIntParatTipoEmpresaServicio() {
		return intParatTipoEmpresaServicio;
	}
	public void setIntParatTipoEmpresaServicio(Integer intParatTipoEmpresaServicio) {
		this.intParatTipoEmpresaServicio = intParatTipoEmpresaServicio;
	}
	public Integer getIntPersPersonaServicio() {
		return intPersPersonaServicio;
	}
	public void setIntPersPersonaServicio(Integer intPersPersonaServicio) {
		this.intPersPersonaServicio = intPersPersonaServicio;
	}
	public Integer getIntDomicilio() {
		return intDomicilio;
	}
	public void setIntDomicilio(Integer intDomicilio) {
		this.intDomicilio = intDomicilio;
	}
	public Integer getIntParaTipoEmpresaSolicita() {
		return intParaTipoEmpresaSolicita;
	}
	public void setIntParaTipoEmpresaSolicita(Integer intParaTipoEmpresaSolicita) {
		this.intParaTipoEmpresaSolicita = intParaTipoEmpresaSolicita;
	}
	public Integer getIntPersPersonaSolicita() {
		return intPersPersonaSolicita;
	}
	public void setIntPersPersonaSolicita(Integer intPersPersonaSolicita) {
		this.intPersPersonaSolicita = intPersPersonaSolicita;
	}
	public Integer getIntPersEmpresaSucursal() {
		return intPersEmpresaSucursal;
	}
	public void setIntPersEmpresaSucursal(Integer intPersEmpresaSucursal) {
		this.intPersEmpresaSucursal = intPersEmpresaSucursal;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntIdAreaSolicitante() {
		return intIdAreaSolicitante;
	}
	public void setIntIdAreaSolicitante(Integer intIdAreaSolicitante) {
		this.intIdAreaSolicitante = intIdAreaSolicitante;
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
	public Integer getIntParaTipoFrecuencia() {
		return intParaTipoFrecuencia;
	}
	public void setIntParaTipoFrecuencia(Integer intParaTipoFrecuencia) {
		this.intParaTipoFrecuencia = intParaTipoFrecuencia;
	}
	public Integer getIntPagoUnico() {
		return intPagoUnico;
	}
	public void setIntPagoUnico(Integer intPagoUnico) {
		this.intPagoUnico = intPagoUnico;
	}
	public BigDecimal getBdMontoContrato() {
		return bdMontoContrato;
	}
	public void setBdMontoContrato(BigDecimal bdMontoContrato) {
		this.bdMontoContrato = bdMontoContrato;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public Integer getIntRenovacion() {
		return intRenovacion;
	}
	public void setIntRenovacion(Integer intRenovacion) {
		this.intRenovacion = intRenovacion;
	}
	public BigDecimal getBdMontoGarantia() {
		return bdMontoGarantia;
	}
	public void setBdMontoGarantia(BigDecimal bdMontoGarantia) {
		this.bdMontoGarantia = bdMontoGarantia;
	}
	public Integer getIntParaTipoMonedaGarantia() {
		return intParaTipoMonedaGarantia;
	}
	public void setIntParaTipoMonedaGarantia(Integer intParaTipoMonedaGarantia) {
		this.intParaTipoMonedaGarantia = intParaTipoMonedaGarantia;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntPersEmpresaAnterior() {
		return intPersEmpresaAnterior;
	}
	public void setIntPersEmpresaAnterior(Integer intPersEmpresaAnterior) {
		this.intPersEmpresaAnterior = intPersEmpresaAnterior;
	}
	public Integer getIntItemContratoAnterior() {
		return intItemContratoAnterior;
	}
	public void setIntItemContratoAnterior(Integer intItemContratoAnterior) {
		this.intItemContratoAnterior = intItemContratoAnterior;
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
	public Requisicion getRequisicion() {
		return requisicion;
	}
	public void setRequisicion(Requisicion requisicion) {
		this.requisicion = requisicion;
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
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Persona getEmpresaServicio() {
		return empresaServicio;
	}
	public void setEmpresaServicio(Persona empresaServicio) {
		this.empresaServicio = empresaServicio;
	}
	public Persona getEmpresaSolicita() {
		return empresaSolicita;
	}
	public void setEmpresaSolicita(Persona empresaSolicita) {
		this.empresaSolicita = empresaSolicita;
	}
	public Contrato getContratoAnterior() {
		return contratoAnterior;
	}
	public void setContratoAnterior(Contrato contratoAnterior) {
		this.contratoAnterior = contratoAnterior;
	}
	public boolean isSeleccionaPagoUnico() {
		return seleccionaPagoUnico;
	}
	public void setSeleccionaPagoUnico(boolean seleccionaPagoUnico) {
		this.seleccionaPagoUnico = seleccionaPagoUnico;
	}
	public boolean isSeleccionaRenovacion() {
		return seleccionaRenovacion;
	}
	public void setSeleccionaRenovacion(boolean seleccionaRenovacion) {
		this.seleccionaRenovacion = seleccionaRenovacion;
	}
	public Domicilio getDomicilioEmpresaServicio() {
		return domicilioEmpresaServicio;
	}
	public void setDomicilioEmpresaServicio(Domicilio domicilioEmpresaServicio) {
		this.domicilioEmpresaServicio = domicilioEmpresaServicio;
	}
	public Date getDtFiltroInicioDesde() {
		return dtFiltroInicioDesde;
	}
	public void setDtFiltroInicioDesde(Date dtFiltroInicioDesde) {
		this.dtFiltroInicioDesde = dtFiltroInicioDesde;
	}
	public Date getDtFiltroInicioHasta() {
		return dtFiltroInicioHasta;
	}
	public void setDtFiltroInicioHasta(Date dtFiltroInicioHasta) {
		this.dtFiltroInicioHasta = dtFiltroInicioHasta;
	}
	public Date getDtFiltroFinDesde() {
		return dtFiltroFinDesde;
	}
	public void setDtFiltroFinDesde(Date dtFiltroFinDesde) {
		this.dtFiltroFinDesde = dtFiltroFinDesde;
	}
	public Date getDtFiltroFinHasta() {
		return dtFiltroFinHasta;
	}
	public void setDtFiltroFinHasta(Date dtFiltroFinHasta) {
		this.dtFiltroFinHasta = dtFiltroFinHasta;
	}
	public String getStrEtiqueta() {
		return strEtiqueta;
	}
	public void setStrEtiqueta(String strEtiqueta) {
		this.strEtiqueta = strEtiqueta;
	}
	public String getStrCantidadDias() {
		return strCantidadDias;
	}
	public void setStrCantidadDias(String strCantidadDias) {
		this.strCantidadDias = strCantidadDias;
	}
	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}
	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}


	@Override
	public String toString() {
		return "Contrato [id=" + id + ", intPersEmpresaRequisicion="
				+ intPersEmpresaRequisicion + ", intItemRequisicion="
				+ intItemRequisicion + ", tsFechaContrato=" + tsFechaContrato
				+ ", intParaTipoContrato=" + intParaTipoContrato
				+ ", intParatTipoEmpresaServicio="
				+ intParatTipoEmpresaServicio + ", intPersPersonaServicio="
				+ intPersPersonaServicio + ", intDomicilio=" + intDomicilio
				+ ", intParaTipoEmpresaSolicita=" + intParaTipoEmpresaSolicita
				+ ", intPersPersonaSolicita=" + intPersPersonaSolicita
				+ ", intPersEmpresaSucursal=" + intPersEmpresaSucursal
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intIdAreaSolicitante=" + intIdAreaSolicitante
				+ ", dtFechaInicio=" + dtFechaInicio + ", dtFechaFin="
				+ dtFechaFin + ", intParaTipoFrecuencia="
				+ intParaTipoFrecuencia + ", intPagoUnico=" + intPagoUnico
				+ ", bdMontoContrato=" + bdMontoContrato
				+ ", intParaTipoMoneda=" + intParaTipoMoneda
				+ ", intRenovacion=" + intRenovacion + ", bdMontoGarantia="
				+ bdMontoGarantia + ", intParaTipoMonedaGarantia="
				+ intParaTipoMonedaGarantia + ", strObservacion="
				+ strObservacion + ", intPersEmpresaAnterior="
				+ intPersEmpresaAnterior + ", intItemContratoAnterior="
				+ intItemContratoAnterior + ", intPersEmpresaUsuario="
				+ intPersEmpresaUsuario + ", intPersPersonaUsuario="
				+ intPersPersonaUsuario + ", tsFechaAnula=" + tsFechaAnula
				+ ", intPersEmpresaAnula=" + intPersEmpresaAnula
				+ ", intPersPersonaAnula=" + intPersPersonaAnula
				+ ", intParaTipo=" + intParaTipo + ", intItemArchivo="
				+ intItemArchivo + ", intItemHistorico=" + intItemHistorico
				+ ", intParaEstado=" + intParaEstado
				+ "]";
	}
}