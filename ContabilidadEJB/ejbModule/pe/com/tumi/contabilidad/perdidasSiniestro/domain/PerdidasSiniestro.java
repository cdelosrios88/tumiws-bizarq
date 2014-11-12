package pe.com.tumi.contabilidad.perdidasSiniestro.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PerdidasSiniestro extends TumiDomain{
	private PerdidasSiniestroId id;
	private Integer intParaDocumentoGeneral;
	private Integer intPersEmpresaAseguraPk;
	private Integer intPersPersonaAseguraPk;
	private Integer intParaTipoSiniestro;
	private Date dtSiniFechaSiniestro;
	private Integer intPersEmpresaAfectadoPk;
	private Integer intPersonaAfectadoPk;
	private Integer intParaTipoMonedaCod;
	private BigDecimal bdSiniMontoDado;
	private Integer intParaTipoMonedaAsegCod;
	private BigDecimal bdSiniMontoAsegurado;
	private String strSiniGlosa;
	private Timestamp tsSiniFechaRegistro;
	private Integer intPersEmpresaUsuarioPk;
	private Integer intPersPersonaUsuarioPk;
	private Integer intPersEmpresaLibroPk;
	private Integer intContPeriodoLibro;
	private Integer intContCodigoLibro;
	private Integer intParaEstadoCod;
	private Integer intParaEstadoCobroCod;
	private Timestamp tsSiniFechaEliminacion;
	private Integer intPersEmpresaEliminarPk;
	private Integer intPersPersonaEliminarPk;
	private Integer intPersEmpresaLibroAnulaPk;
	private Integer intContPeriodoLibroAnula;
	private Integer intContCodigoLibroAnula;
	
	//datos Persona Juridica
	private Integer intIdPersJuridica;
	private String strNombreJuridica;
	private String strNumeroRuc;
	//valores de entrada
	private Integer intCodigoBuscar;
	private String strNombreBuscar;
	//Datos de la persona afectada
	private Integer intIdPersPersona;
	private String strNombreCompletoPerdido;
	private String strDocumentoIdentidad;
	//variables de la descripción del tipo y estados.
	private String strDescripcionTipo;
	private String strDescripcionEstado;
	private String strDescripcionPago;
	
	public PerdidasSiniestroId getId() {
		return id;
	}
	public void setId(PerdidasSiniestroId id) {
		this.id = id;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Integer getIntPersEmpresaAseguraPk() {
		return intPersEmpresaAseguraPk;
	}
	public void setIntPersEmpresaAseguraPk(Integer intPersEmpresaAseguraPk) {
		this.intPersEmpresaAseguraPk = intPersEmpresaAseguraPk;
	}
	public Integer getIntPersPersonaAseguraPk() {
		return intPersPersonaAseguraPk;
	}
	public void setIntPersPersonaAseguraPk(Integer intPersPersonaAseguraPk) {
		this.intPersPersonaAseguraPk = intPersPersonaAseguraPk;
	}
	public Integer getIntParaTipoSiniestro() {
		return intParaTipoSiniestro;
	}
	public void setIntParaTipoSiniestro(Integer intParaTipoSiniestro) {
		this.intParaTipoSiniestro = intParaTipoSiniestro;
	}
	public Date getDtSiniFechaSiniestro() {
		return dtSiniFechaSiniestro;
	}
	public void setDtSiniFechaSiniestro(Date dtSiniFechaSiniestro) {
		this.dtSiniFechaSiniestro = dtSiniFechaSiniestro;
	}
	public Integer getIntPersEmpresaAfectadoPk() {
		return intPersEmpresaAfectadoPk;
	}
	public void setIntPersEmpresaAfectadoPk(Integer intPersEmpresaAfectadoPk) {
		this.intPersEmpresaAfectadoPk = intPersEmpresaAfectadoPk;
	}
	public Integer getIntPersonaAfectadoPk() {
		return intPersonaAfectadoPk;
	}
	public void setIntPersonaAfectadoPk(Integer intPersonaAfectadoPk) {
		this.intPersonaAfectadoPk = intPersonaAfectadoPk;
	}
	public Integer getIntParaTipoMonedaCod() {
		return intParaTipoMonedaCod;
	}
	public void setIntParaTipoMonedaCod(Integer intParaTipoMonedaCod) {
		this.intParaTipoMonedaCod = intParaTipoMonedaCod;
	}
	public BigDecimal getBdSiniMontoDado() {
		return bdSiniMontoDado;
	}
	public void setBdSiniMontoDado(BigDecimal bdSiniMontoDado) {
		this.bdSiniMontoDado = bdSiniMontoDado;
	}
	public Integer getIntParaTipoMonedaAsegCod() {
		return intParaTipoMonedaAsegCod;
	}
	public void setIntParaTipoMonedaAsegCod(Integer intParaTipoMonedaAsegCod) {
		this.intParaTipoMonedaAsegCod = intParaTipoMonedaAsegCod;
	}
	public BigDecimal getBdSiniMontoAsegurado() {
		return bdSiniMontoAsegurado;
	}
	public void setBdSiniMontoAsegurado(BigDecimal bdSiniMontoAsegurado) {
		this.bdSiniMontoAsegurado = bdSiniMontoAsegurado;
	}
	public String getStrSiniGlosa() {
		return strSiniGlosa;
	}
	public void setStrSiniGlosa(String strSiniGlosa) {
		this.strSiniGlosa = strSiniGlosa;
	}
	public Timestamp getTsSiniFechaRegistro() {
		return tsSiniFechaRegistro;
	}
	public void setTsSiniFechaRegistro(Timestamp tsSiniFechaRegistro) {
		this.tsSiniFechaRegistro = tsSiniFechaRegistro;
	}
	public Integer getIntPersEmpresaUsuarioPk() {
		return intPersEmpresaUsuarioPk;
	}
	public void setIntPersEmpresaUsuarioPk(Integer intPersEmpresaUsuarioPk) {
		this.intPersEmpresaUsuarioPk = intPersEmpresaUsuarioPk;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public Integer getIntPersEmpresaLibroPk() {
		return intPersEmpresaLibroPk;
	}
	public void setIntPersEmpresaLibroPk(Integer intPersEmpresaLibroPk) {
		this.intPersEmpresaLibroPk = intPersEmpresaLibroPk;
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
	public Timestamp getTsSiniFechaEliminacion() {
		return tsSiniFechaEliminacion;
	}
	public void setTsSiniFechaEliminacion(Timestamp tsSiniFechaEliminacion) {
		this.tsSiniFechaEliminacion = tsSiniFechaEliminacion;
	}
	public Integer getIntPersEmpresaEliminarPk() {
		return intPersEmpresaEliminarPk;
	}
	public void setIntPersEmpresaEliminarPk(Integer intPersEmpresaEliminarPk) {
		this.intPersEmpresaEliminarPk = intPersEmpresaEliminarPk;
	}
	public Integer getIntPersPersonaEliminarPk() {
		return intPersPersonaEliminarPk;
	}
	public void setIntPersPersonaEliminarPk(Integer intPersPersonaEliminarPk) {
		this.intPersPersonaEliminarPk = intPersPersonaEliminarPk;
	}
	public Integer getIntPersEmpresaLibroAnulaPk() {
		return intPersEmpresaLibroAnulaPk;
	}
	public void setIntPersEmpresaLibroAnulaPk(Integer intPersEmpresaLibroAnulaPk) {
		this.intPersEmpresaLibroAnulaPk = intPersEmpresaLibroAnulaPk;
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
	public Integer getIntIdPersJuridica() {
		return intIdPersJuridica;
	}
	public void setIntIdPersJuridica(Integer intIdPersJuridica) {
		this.intIdPersJuridica = intIdPersJuridica;
	}
	public String getStrNombreJuridica() {
		return strNombreJuridica;
	}
	public void setStrNombreJuridica(String strNombreJuridica) {
		this.strNombreJuridica = strNombreJuridica;
	}
	public String getStrNumeroRuc() {
		return strNumeroRuc;
	}
	public void setStrNumeroRuc(String strNumeroRuc) {
		this.strNumeroRuc = strNumeroRuc;
	}
	public Integer getIntCodigoBuscar() {
		return intCodigoBuscar;
	}
	public void setIntCodigoBuscar(Integer intCodigoBuscar) {
		this.intCodigoBuscar = intCodigoBuscar;
	}
	public String getStrNombreBuscar() {
		return strNombreBuscar;
	}
	public void setStrNombreBuscar(String strNombreBuscar) {
		this.strNombreBuscar = strNombreBuscar;
	}
	public Integer getIntIdPersPersona() {
		return intIdPersPersona;
	}
	public void setIntIdPersPersona(Integer intIdPersPersona) {
		this.intIdPersPersona = intIdPersPersona;
	}
	public String getStrNombreCompletoPerdido() {
		return strNombreCompletoPerdido;
	}
	public void setStrNombreCompletoPerdido(String strNombreCompletoPerdido) {
		this.strNombreCompletoPerdido = strNombreCompletoPerdido;
	}
	public String getStrDocumentoIdentidad() {
		return strDocumentoIdentidad;
	}
	public void setStrDocumentoIdentidad(String strDocumentoIdentidad) {
		this.strDocumentoIdentidad = strDocumentoIdentidad;
	}
	public String getStrDescripcionTipo() {
		return strDescripcionTipo;
	}
	public void setStrDescripcionTipo(String strDescripcionTipo) {
		this.strDescripcionTipo = strDescripcionTipo;
	}
	public String getStrDescripcionEstado() {
		return strDescripcionEstado;
	}
	public void setStrDescripcionEstado(String strDescripcionEstado) {
		this.strDescripcionEstado = strDescripcionEstado;
	}
	public String getStrDescripcionPago() {
		return strDescripcionPago;
	}
	public void setStrDescripcionPago(String strDescripcionPago) {
		this.strDescripcionPago = strDescripcionPago;
	}
	
}
