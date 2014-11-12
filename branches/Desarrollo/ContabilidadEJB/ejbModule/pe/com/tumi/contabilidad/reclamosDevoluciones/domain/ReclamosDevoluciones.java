package pe.com.tumi.contabilidad.reclamosDevoluciones.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 08.08.2014 /
public class ReclamosDevoluciones extends TumiDomain{
	private ReclamosDevolucionesId id;
	private Integer intParaDocumentoGeneral;
	private Integer intReclamoDevolucion;
	private Integer intPersEmpresaAfecto;
	private Integer intPersPersonaAfecto;
	private Integer intTipoMoneda;
	private BigDecimal bdRedeMonto;
	private Integer intRedePlanillaAfectada;
	private Integer intParaTipoCod;
	private Integer intMaeItemArchivo;
	private Integer intMaeItemHistorico;
	private String strRedeGlosa;
	private Timestamp tsRedeFechaRegistro;
	private Integer intPersEmpresaUsuario;
	private Integer intPersPersonaUsuario;
	private Integer intPersEmpresaLibro;
	private Integer intContPeriodoLibro;
	private Integer intContCodigoLibro;
	private Integer intParaEstadoCod;
	private Integer intParaEstadoCobroCod;
	private Integer intParaEstadoPago;
	private Timestamp tsRedeFechaEliminacion;
	private Integer intPersEmpresaEliminar;
	private Integer intPersPersonaEliminar;
	private Integer intPersEmpresaLibroAnula;
	private Integer intContPeriodoLibroAnula;
	private Integer intContCodigoLibroAnula;
	
	//datos de parametro
	private Integer intIdMaestro;
	private Integer intIdDetalle;
	private String strDescripcion;
	private Integer intEstado;
	
	//Datos Persona
	private String 		strNombreCompleto;
	
	//Descripcion de los item's
	private String strDocumentoGeneral;
	private String strReclamoDevolucion;
	private String strEstado;
	private String strDescripcionEstado;
	
	
	//Getter and Setters
	public ReclamosDevolucionesId getId() {
		return id;
	}
	public void setId(ReclamosDevolucionesId id) {
		this.id = id;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Integer getIntReclamoDevolucion() {
		return intReclamoDevolucion;
	}
	public void setIntReclamoDevolucion(Integer intReclamoDevolucion) {
		this.intReclamoDevolucion = intReclamoDevolucion;
	}
	public Integer getIntPersEmpresaAfecto() {
		return intPersEmpresaAfecto;
	}
	public void setIntPersEmpresaAfecto(Integer intPersEmpresaAfecto) {
		this.intPersEmpresaAfecto = intPersEmpresaAfecto;
	}
	public Integer getIntPersPersonaAfecto() {
		return intPersPersonaAfecto;
	}
	public void setIntPersPersonaAfecto(Integer intPersPersonaAfecto) {
		this.intPersPersonaAfecto = intPersPersonaAfecto;
	}
	public Integer getIntTipoMoneda() {
		return intTipoMoneda;
	}
	public void setIntTipoMoneda(Integer intTipoMoneda) {
		this.intTipoMoneda = intTipoMoneda;
	}
	public BigDecimal getBdRedeMonto() {
		return bdRedeMonto;
	}
	public void setBdRedeMonto(BigDecimal bdRedeMonto) {
		this.bdRedeMonto = bdRedeMonto;
	}
	public Integer getIntRedePlanillaAfectada() {
		return intRedePlanillaAfectada;
	}
	public void setIntRedePlanillaAfectada(Integer intRedePlanillaAfectada) {
		this.intRedePlanillaAfectada = intRedePlanillaAfectada;
	}
	public Integer getIntParaTipoCod() {
		return intParaTipoCod;
	}
	public void setIntParaTipoCod(Integer intParaTipoCod) {
		this.intParaTipoCod = intParaTipoCod;
	}
	public Integer getIntMaeItemArchivo() {
		return intMaeItemArchivo;
	}
	public void setIntMaeItemArchivo(Integer intMaeItemArchivo) {
		this.intMaeItemArchivo = intMaeItemArchivo;
	}
	public Integer getIntMaeItemHistorico() {
		return intMaeItemHistorico;
	}
	public void setIntMaeItemHistorico(Integer intMaeItemHistorico) {
		this.intMaeItemHistorico = intMaeItemHistorico;
	}
	public String getStrRedeGlosa() {
		return strRedeGlosa;
	}
	public void setStrRedeGlosa(String strRedeGlosa) {
		this.strRedeGlosa = strRedeGlosa;
	}
	public Timestamp getTsRedeFechaRegistro() {
		return tsRedeFechaRegistro;
	}
	public void setTsRedeFechaRegistro(Timestamp tsRedeFechaRegistro) {
		this.tsRedeFechaRegistro = tsRedeFechaRegistro;
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
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntParaEstadoCobroCod() {
		return intParaEstadoCobroCod;
	}
	public void setIntParaEstadoCobroCod(Integer intParaEstadoCobroCod) {
		this.intParaEstadoCobroCod = intParaEstadoCobroCod;
	}
	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}
	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
	}
	public Timestamp getTsRedeFechaEliminacion() {
		return tsRedeFechaEliminacion;
	}
	public void setTsRedeFechaEliminacion(Timestamp tsRedeFechaEliminacion) {
		this.tsRedeFechaEliminacion = tsRedeFechaEliminacion;
	}
	public Integer getIntPersEmpresaEliminar() {
		return intPersEmpresaEliminar;
	}
	public void setIntPersEmpresaEliminar(Integer intPersEmpresaEliminar) {
		this.intPersEmpresaEliminar = intPersEmpresaEliminar;
	}
	public Integer getIntPersPersonaEliminar() {
		return intPersPersonaEliminar;
	}
	public void setIntPersPersonaEliminar(Integer intPersPersonaEliminar) {
		this.intPersPersonaEliminar = intPersPersonaEliminar;
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

	public Integer getIntIdMaestro() {
		return intIdMaestro;
	}
	public void setIntIdMaestro(Integer intIdMaestro) {
		this.intIdMaestro = intIdMaestro;
	}
	public Integer getIntIdDetalle() {
		return intIdDetalle;
	}
	public void setIntIdDetalle(Integer intIdDetalle) {
		this.intIdDetalle = intIdDetalle;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntEstado() {
		return intEstado;
	}
	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}
	public String getStrNombreCompleto() {
		return strNombreCompleto;
	}
	public void setStrNombreCompleto(String strNombreCompleto) {
		this.strNombreCompleto = strNombreCompleto;
	}
	public String getStrDocumentoGeneral() {
		return strDocumentoGeneral;
	}
	public void setStrDocumentoGeneral(String strDocumentoGeneral) {
		this.strDocumentoGeneral = strDocumentoGeneral;
	}
	public String getStrReclamoDevolucion() {
		return strReclamoDevolucion;
	}
	public void setStrReclamoDevolucion(String strReclamoDevolucion) {
		this.strReclamoDevolucion = strReclamoDevolucion;
	}
	public String getStrEstado() {
		return strEstado;
	}
	public void setStrEstado(String strEstado) {
		this.strEstado = strEstado;
	}
	public String getStrDescripcionEstado() {
		return strDescripcionEstado;
	}
	public void setStrDescripcionEstado(String strDescripcionEstado) {
		this.strDescripcionEstado = strDescripcionEstado;
	}
	
}
