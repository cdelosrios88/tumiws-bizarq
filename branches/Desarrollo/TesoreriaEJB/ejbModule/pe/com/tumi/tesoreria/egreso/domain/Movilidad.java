package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;

public class Movilidad extends TumiDomain{

	private	MovilidadId	id;
	private Integer 	intParaDocumentoGeneral;
	private Timestamp 	tsFechaRegistro;
	private Integer 	intPeriodo;
	private Integer 	intPersEmpresaUsuario;
	private Integer 	intPersPersonaUsuario;
	private Integer 	intSucuIdSucursal;
	private Integer 	intSudeIdSubsucursal;
	private Integer 	intParaEstado;
	private Integer 	intParaEstadoPago;
	private Integer 	intPersEmpresaLibro;
	private Integer 	intContPeriodoLibro;
	private Integer 	intContCodigoLibro;
	private Integer 	intPersEmpresaLibroAnula;
	private Integer 	intContPeriodoLibroAnula;
	private Integer 	intContCodigoLibroAnula;
	private Integer		intItemEgresoGeneral;
	private Integer		intPersEmpresaEgreso;
	private	Integer		intIdArea;
	
	private List<MovilidadDetalle> listaMovilidadDetalle;

	
	//interfaz
	private String		strEtiquetaEmpresa;
	private String		strEtiquetaUsuario;
	private String		strEtiquetaUsuarioBusqueda;
	private String		strEtiquetaSucursalBusqueda;
	private Timestamp 	tsFechaDesde;
	private Timestamp 	tsFechaHasta;
	private	Integer		intTipoBusqueda;
	private	Integer		intAño;
	private	Integer		intMes;
	private	String		strTextoFiltro;
	private	BigDecimal	bdMontoAcumulado;
	private Persona		persona;
	private Sucursal	sucursal;
	private	Integer		intPersPersonaEdita;
	private	Integer		intPersEmpresaEdita;
	private	Subsucursal	subsucursal;
	private	String		strGlosaEgreso;
	private List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz;
	private	Egreso		egreso;
	
	//Agregado 16.12.2013 JCHAVEZ
	private String 		strPersonaUsuario;
	private String 		strDescSucuSubcucu;
	//Agregado 23.01.2014 JCHAVEZ
	private LibroDiario libroDiario;
	private List<LibroDiarioDetalle> listaLibroDiarioDetalle;
	
	public Movilidad(){
		id = new MovilidadId();
		listaMovilidadDetalle = new ArrayList<MovilidadDetalle>();
	}
	
	
	public MovilidadId getId() {
		return id;
	}
	public void setId(MovilidadId id) {
		this.id = id;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
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
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}
	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
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
	public Integer getIntPersEmpresaLibroAnula() {
		return intPersEmpresaLibroAnula;
	}
	public void setIntPersEmpresaLibroAnula(Integer intPersEmpresaLibroAnula) {
		this.intPersEmpresaLibroAnula = intPersEmpresaLibroAnula;
	}
	public Integer getIntContPeriodoLibroAnula() {
		return intContPeriodoLibroAnula;
	}
	public void setIntContPeriodoLibroAnula(Integer intContPeriodoLibroAnula) {
		this.intContPeriodoLibroAnula = intContPeriodoLibroAnula;
	}
	public Integer getIntContCodigoLibroAnula() {
		return intContCodigoLibroAnula;
	}
	public void setIntContCodigoLibroAnula(Integer intContCodigoLibroAnula) {
		this.intContCodigoLibroAnula = intContCodigoLibroAnula;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public List<MovilidadDetalle> getListaMovilidadDetalle() {
		return listaMovilidadDetalle;
	}
	public void setListaMovilidadDetalle(List<MovilidadDetalle> listaMovilidadDetalle) {
		this.listaMovilidadDetalle = listaMovilidadDetalle;
	}
	public Timestamp getTsFechaDesde() {
		return tsFechaDesde;
	}
	public void setTsFechaDesde(Timestamp tsFechaDesde) {
		this.tsFechaDesde = tsFechaDesde;
	}
	public Timestamp getTsFechaHasta() {
		return tsFechaHasta;
	}
	public void setTsFechaHasta(Timestamp tsFechaHasta) {
		this.tsFechaHasta = tsFechaHasta;
	}
	public Integer getIntTipoBusqueda() {
		return intTipoBusqueda;
	}
	public void setIntTipoBusqueda(Integer intTipoBusqueda) {
		this.intTipoBusqueda = intTipoBusqueda;
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
	public String getStrTextoFiltro() {
		return strTextoFiltro;
	}
	public void setStrTextoFiltro(String strTextoFiltro) {
		this.strTextoFiltro = strTextoFiltro;
	}
	public String getStrEtiquetaUsuario() {
		return strEtiquetaUsuario;
	}
	public void setStrEtiquetaUsuario(String strEtiquetaUsuario) {
		this.strEtiquetaUsuario = strEtiquetaUsuario;
	}
	public String getStrEtiquetaEmpresa() {
		return strEtiquetaEmpresa;
	}
	public void setStrEtiquetaEmpresa(String strEtiquetaEmpresa) {
		this.strEtiquetaEmpresa = strEtiquetaEmpresa;
	}
	public BigDecimal getBdMontoAcumulado() {
		return bdMontoAcumulado;
	}
	public void setBdMontoAcumulado(BigDecimal bdMontoAcumulado) {
		this.bdMontoAcumulado = bdMontoAcumulado;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getStrEtiquetaUsuarioBusqueda() {
		return strEtiquetaUsuarioBusqueda;
	}
	public void setStrEtiquetaUsuarioBusqueda(String strEtiquetaUsuarioBusqueda) {
		this.strEtiquetaUsuarioBusqueda = strEtiquetaUsuarioBusqueda;
	}
	public Integer getIntIdArea() {
		return intIdArea;
	}
	public void setIntIdArea(Integer intIdArea) {
		this.intIdArea = intIdArea;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public String getStrEtiquetaSucursalBusqueda() {
		return strEtiquetaSucursalBusqueda;
	}
	public void setStrEtiquetaSucursalBusqueda(String strEtiquetaSucursalBusqueda) {
		this.strEtiquetaSucursalBusqueda = strEtiquetaSucursalBusqueda;
	}
	public Integer getIntPersPersonaEdita() {
		return intPersPersonaEdita;
	}
	public void setIntPersPersonaEdita(Integer intPersPersonaEdita) {
		this.intPersPersonaEdita = intPersPersonaEdita;
	}
	public Integer getIntPersEmpresaEdita() {
		return intPersEmpresaEdita;
	}
	public void setIntPersEmpresaEdita(Integer intPersEmpresaEdita) {
		this.intPersEmpresaEdita = intPersEmpresaEdita;
	}
	public Subsucursal getSubsucursal() {
		return subsucursal;
	}
	public void setSubsucursal(Subsucursal subsucursal) {
		this.subsucursal = subsucursal;
	}
	public String getStrGlosaEgreso() {
		return strGlosaEgreso;
	}
	public void setStrGlosaEgreso(String strGlosaEgreso) {
		this.strGlosaEgreso = strGlosaEgreso;
	}
	public List<EgresoDetalleInterfaz> getListaEgresoDetalleInterfaz() {
		return listaEgresoDetalleInterfaz;
	}
	public void setListaEgresoDetalleInterfaz(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz) {
		this.listaEgresoDetalleInterfaz = listaEgresoDetalleInterfaz;
	}
	public Egreso getEgreso() {
		return egreso;
	}
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}


	@Override
	public String toString() {
		return "Movilidad [id=" + id + ", intParaDocumentoGeneral="
				+ intParaDocumentoGeneral + ", tsFechaRegistro="
				+ tsFechaRegistro + ", intPeriodo=" + intPeriodo
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSubsucursal=" + intSudeIdSubsucursal
				+ ", intParaEstado=" + intParaEstado + ", intParaEstadoPago="
				+ intParaEstadoPago + ", intPersEmpresaLibro="
				+ intPersEmpresaLibro + ", intContPeriodoLibro="
				+ intContPeriodoLibro + ", intContCodigoLibro="
				+ intContCodigoLibro + ", intPersEmpresaLibroAnula="
				+ intPersEmpresaLibroAnula + ", intContPeriodoLibroAnula="
				+ intContPeriodoLibroAnula + ", intContCodigoLibroAnula="
				+ intContCodigoLibroAnula + ", intPersEmpresaEgreso="
				+ intPersEmpresaEgreso + ", intItemEgresoGeneral="
				+ intItemEgresoGeneral + "]";
	}

	//Agregado 16.12.2013 JCHAVEZ
	public String getStrPersonaUsuario() {
		return strPersonaUsuario;
	}
	public void setStrPersonaUsuario(String strPersonaUsuario) {
		this.strPersonaUsuario = strPersonaUsuario;
	}
	public String getStrDescSucuSubcucu() {
		return strDescSucuSubcucu;
	}
	public void setStrDescSucuSubcucu(String strDescSucuSubcucu) {
		this.strDescSucuSubcucu = strDescSucuSubcucu;
	}
	//Agregado 23.01.2014 JCHAVEZ
	public LibroDiario getLibroDiario() {
		return libroDiario;
	}
	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}
	public List<LibroDiarioDetalle> getListaLibroDiarioDetalle() {
		return listaLibroDiarioDetalle;
	}
	public void setListaLibroDiarioDetalle(
			List<LibroDiarioDetalle> listaLibroDiarioDetalle) {
		this.listaLibroDiarioDetalle = listaLibroDiarioDetalle;
	}
}
