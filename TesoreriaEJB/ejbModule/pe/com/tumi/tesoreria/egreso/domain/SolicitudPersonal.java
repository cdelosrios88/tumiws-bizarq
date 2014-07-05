package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;

public class SolicitudPersonal extends TumiDomain{

	private SolicitudPersonalId id;
	private Timestamp	tsFechaRegistro;
	private Integer 	intParaDocumentoGeneral;
	private Integer 	intParaSubTipoDocumentoPlanilla;
	private Integer 	intParaAgrupacionPago;
	private Integer 	intPersEmpresaPersona;
	private Integer 	intPersPersonaGiro;
	private Integer 	intParaTipoMoneda;
	private BigDecimal 	bdMontoTotalSolicitud;
	private Integer 	intPeriodoPago;
	private String 		strTipoPlanilla;
	private Integer 	intParaTipoPeriodo;
	private String 		strObservacion;
	private Integer 	intParaEstado;
	private Integer 	intPersEmpresaUsuario;
	private Integer 	intPersPersonaUsuario;
	private Timestamp	tsFechaEliminacion;
	private Integer		intPersEmpresaElimina;
	private Integer		intPersPersonaElimina;
	private Integer 	intPersEmpresaLibro;
	private Integer 	intContPeriodoLibro;
	private Integer 	intContCodigoLibro;
	private BigDecimal 	bdMontoSaldoSolicitud;
	private Integer 	intParaEstadoPago;
	private Integer 	intParaTipoSustento;
	private Integer 	intItemArchivoSustento;
	private Integer 	intItemHistoricoSustento;
	private Integer		intSucuIdSucursal;
	private Integer		intSudeIdSubsucursal;
	
	private Sucursal	sucursal;
	private Subsucursal	subsucursal;
	private Persona		persona;
	private Archivo		archivo;
	private List<SolicitudPersonalDetalle>	listaSolicitudPersonalDetalle;
	private List<SolicitudPersonalPago>		listaSolicitudPersonalPago;
	private Integer		intAño;
	private Integer		intMes;
	private Integer		intTipoBusqueda;
	
	
	public SolicitudPersonal(){
		id = new SolicitudPersonalId();
		listaSolicitudPersonalDetalle = new ArrayList<SolicitudPersonalDetalle>();
		listaSolicitudPersonalPago = new ArrayList<SolicitudPersonalPago>();
	}
	
	public SolicitudPersonalId getId() {
		return id;
	}
	public void setId(SolicitudPersonalId id) {
		this.id = id;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Integer getIntParaSubTipoDocumentoPlanilla() {
		return intParaSubTipoDocumentoPlanilla;
	}
	public void setIntParaSubTipoDocumentoPlanilla(Integer intParaSubTipoDocumentoPlanilla) {
		this.intParaSubTipoDocumentoPlanilla = intParaSubTipoDocumentoPlanilla;
	}
	public Integer getIntParaAgrupacionPago() {
		return intParaAgrupacionPago;
	}
	public void setIntParaAgrupacionPago(Integer intParaAgrupacionPago) {
		this.intParaAgrupacionPago = intParaAgrupacionPago;
	}
	public Integer getIntPersEmpresaPersona() {
		return intPersEmpresaPersona;
	}
	public void setIntPersEmpresaPersona(Integer intPersEmpresaPersona) {
		this.intPersEmpresaPersona = intPersEmpresaPersona;
	}
	public Integer getIntPersPersonaGiro() {
		return intPersPersonaGiro;
	}
	public void setIntPersPersonaGiro(Integer intPersPersonaGiro) {
		this.intPersPersonaGiro = intPersPersonaGiro;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public BigDecimal getBdMontoTotalSolicitud() {
		return bdMontoTotalSolicitud;
	}
	public void setBdMontoTotalSolicitud(BigDecimal bdMontoTotalSolicitud) {
		this.bdMontoTotalSolicitud = bdMontoTotalSolicitud;
	}
	public Integer getIntPeriodoPago() {
		return intPeriodoPago;
	}
	public void setIntPeriodoPago(Integer intPeriodoPago) {
		this.intPeriodoPago = intPeriodoPago;
	}
	public String getStrTipoPlanilla() {
		return strTipoPlanilla;
	}
	public void setStrTipoPlanilla(String strTipoPlanilla) {
		this.strTipoPlanilla = strTipoPlanilla;
	}
	public Integer getIntParaTipoPeriodo() {
		return intParaTipoPeriodo;
	}
	public void setIntParaTipoPeriodo(Integer intParaTipoPeriodo) {
		this.intParaTipoPeriodo = intParaTipoPeriodo;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
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
	public Integer getIntPersEmpresaLibro() {
		return intPersEmpresaLibro;
	}
	public void setIntPersEmpresaLibro(Integer intPersEmpresaLibro) {
		this.intPersEmpresaLibro = intPersEmpresaLibro;
	}
	public Integer getIntContPeriodoLibro() {
		return intContPeriodoLibro;
	}
	public void setIntContPeriodoLibro(Integer intContPeriodoLibro) {
		this.intContPeriodoLibro = intContPeriodoLibro;
	}
	public Integer getIntContCodigoLibro() {
		return intContCodigoLibro;
	}
	public void setIntContCodigoLibro(Integer intContCodigoLibro) {
		this.intContCodigoLibro = intContCodigoLibro;
	}
	public BigDecimal getBdMontoSaldoSolicitud() {
		return bdMontoSaldoSolicitud;
	}
	public void setBdMontoSaldoSolicitud(BigDecimal bdMontoSaldoSolicitud) {
		this.bdMontoSaldoSolicitud = bdMontoSaldoSolicitud;
	}
	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}
	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
	}
	public Integer getIntParaTipoSustento() {
		return intParaTipoSustento;
	}
	public void setIntParaTipoSustento(Integer intParaTipoSustento) {
		this.intParaTipoSustento = intParaTipoSustento;
	}
	public Integer getIntItemArchivoSustento() {
		return intItemArchivoSustento;
	}
	public void setIntItemArchivoSustento(Integer intItemArchivoSustento) {
		this.intItemArchivoSustento = intItemArchivoSustento;
	}
	public Integer getIntItemHistoricoSustento() {
		return intItemHistoricoSustento;
	}
	public void setIntItemHistoricoSustento(Integer intItemHistoricoSustento) {
		this.intItemHistoricoSustento = intItemHistoricoSustento;
	}	
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Integer getIntPersEmpresaElimina() {
		return intPersEmpresaElimina;
	}
	public void setIntPersEmpresaElimina(Integer intPersEmpresaElimina) {
		this.intPersEmpresaElimina = intPersEmpresaElimina;
	}
	public Integer getIntPersPersonaElimina() {
		return intPersPersonaElimina;
	}
	public void setIntPersPersonaElimina(Integer intPersPersonaElimina) {
		this.intPersPersonaElimina = intPersPersonaElimina;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Subsucursal getSubsucursal() {
		return subsucursal;
	}
	public void setSubsucursal(Subsucursal subsucursal) {
		this.subsucursal = subsucursal;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public List<SolicitudPersonalDetalle> getListaSolicitudPersonalDetalle() {
		return listaSolicitudPersonalDetalle;
	}
	public void setListaSolicitudPersonalDetalle(List<SolicitudPersonalDetalle> listaSolicitudPersonalDetalle) {
		this.listaSolicitudPersonalDetalle = listaSolicitudPersonalDetalle;
	}
	public Archivo getArchivo() {
		return archivo;
	}
	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}
	public Integer getIntAño() {
		return intAño;
	}
	public void setIntAño(Integer intAño) {
		this.intAño = intAño;
	}
	public Integer getIntMes() {
		return intMes;
	}
	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}	
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSubsucursal() {
		return intSudeIdSubsucursal;
	}
	public void setIntSudeIdSubsucursal(Integer intSudeIdSubsucursal) {
		this.intSudeIdSubsucursal = intSudeIdSubsucursal;
	}
	public List<SolicitudPersonalPago> getListaSolicitudPersonalPago() {
		return listaSolicitudPersonalPago;
	}
	public void setListaSolicitudPersonalPago(List<SolicitudPersonalPago> listaSolicitudPersonalPago) {
		this.listaSolicitudPersonalPago = listaSolicitudPersonalPago;
	}
	public Integer getIntTipoBusqueda() {
		return intTipoBusqueda;
	}
	public void setIntTipoBusqueda(Integer intTipoBusqueda) {
		this.intTipoBusqueda = intTipoBusqueda;
	}

	@Override
	public String toString() {
		return "SolicitudPersonal [id=" + id + ", tsFechaRegistro="
				+ tsFechaRegistro + ", intParaDocumentoGeneral="
				+ intParaDocumentoGeneral
				+ ", intParaSubTipoDocumentoPlanilla="
				+ intParaSubTipoDocumentoPlanilla + ", intParaAgrupacionPago="
				+ intParaAgrupacionPago + ", intPersEmpresaPersona="
				+ intPersEmpresaPersona + ", intPersPersonaGiro="
				+ intPersPersonaGiro + ", intParaTipoMoneda="
				+ intParaTipoMoneda + ", bdMontoTotalSolicitud="
				+ bdMontoTotalSolicitud + ", intPeriodoPago=" + intPeriodoPago
				+ ", strTipoPlanilla=" + strTipoPlanilla
				+ ", intParaTipoPeriodo=" + intParaTipoPeriodo
				+ ", strObservacion=" + strObservacion + ", intParaEstado="
				+ intParaEstado + ", intPersEmpresaUsuario="
				+ intPersEmpresaUsuario + ", intPersPersonaUsuario="
				+ intPersPersonaUsuario + ", intPersEmpresaLibro="
				+ intPersEmpresaLibro + ", intContPeriodoLibro="
				+ intContPeriodoLibro + ", intContCodigoLibro="
				+ intContCodigoLibro + ", bdMontoSaldoSolicitud="
				+ bdMontoSaldoSolicitud + ", intParaEstadoPago="
				+ intParaEstadoPago + ", intParaTipoSustento="
				+ intParaTipoSustento + ", intItemArchivoSustento="
				+ intItemArchivoSustento + ", intItemHistoricoSustento="
				+ intItemHistoricoSustento + "]";
	}	
}