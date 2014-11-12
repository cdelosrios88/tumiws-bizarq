package pe.com.tumi.contabilidad.impuesto.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;



import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class Impuesto extends TumiDomain{
	private ImpuestoId 	id;
	private Integer 	intParaDocumentoGeneralCod;
	private Integer 	intTipoImpuestoCod;
	private Integer 	intTipoRectificatoriaCod;
	private Integer 	intPersEmpresaEntidadPk;
	private Integer 	intPersPersonaEntidadPk;
	private Integer 	intPersEmpresaPersonaPk;
	private Integer 	intPersPersonaEncargadaPk;
	private Timestamp 	tsImpuFechaPresentacionD;
	private Integer 	intImpuPeriodo;
	private Integer 	intParaTipoMonedaCod;
	private BigDecimal 	bdImpuMonto;
	private String 		strImpuGlosa;
	private Integer 	intParaEstadoCod;
	private Integer 	intParaEstadoPagoCod;
	private Timestamp 	tsImpuFechaRegistro;
	private Integer 	intPersEmpresaUsuarioPk;
	private Integer 	intPersPersonaUsuarioPk;
	private Integer 	intPersEmpresaLibroPK;
	private Integer 	intContPeriodoLibro;
	private Integer 	intContCodigoLibro;
	private Timestamp 	tsImpuFechaEliminacionD;
	private Integer 	intPersEmpresaEliminaPk;
	private Integer 	intPersPersonaElimina;
	private Integer 	intPersEmpresaLibroAnulaPk;
	private Integer 	intContPeriodoLibroAnula;
	private Integer 	intContCodigoLibroAnula;
	private Integer 	intPersEmpresaRectPk;
	private Integer 	intContItemImpuestoRectPk;
	
	//datos de persona, Juridica y rol autor Rodolfo villarreal Acuña
	private Integer		intPersPersona;
	private String		strJuriRazonsocial;
	private String		strPersRuc;
	//Datos Persona
	private Integer		intPersPerson;
	private String 		strNombreCompleto;
	private String 		strNumeroDocumento;
	private Integer		intMeses;
	private String 		strDescEstadoPago;
	private String 		strDescTipoImpuesto; //Funciona tambien para para_tipo_rectificatoria
	private String 		strDescEstado;
	private String		strConcatenado;
	
	private Persona persona;
	
	// Autor: jchavez / Tarea: Modificación / Fecha: 1108.2014
	private String 		strDescTipoRegistro;
	private String		strDescTipoMoneda;

	public ImpuestoId getId() {
		return id;
	}
	public void setId(ImpuestoId id) {
		this.id = id;
	}
	public Integer getIntParaDocumentoGeneralCod() {
		return intParaDocumentoGeneralCod;
	}
	public void setIntParaDocumentoGeneralCod(Integer intParaDocumentoGeneralCod) {
		this.intParaDocumentoGeneralCod = intParaDocumentoGeneralCod;
	}
	public Integer getIntTipoImpuestoCod() {
		return intTipoImpuestoCod;
	}
	public void setIntTipoImpuestoCod(Integer intTipoImpuestoCod) {
		this.intTipoImpuestoCod = intTipoImpuestoCod;
	}
	public Integer getIntTipoRectificatoriaCod() {
		return intTipoRectificatoriaCod;
	}
	public void setIntTipoRectificatoriaCod(Integer intTipoRectificatoriaCod) {
		this.intTipoRectificatoriaCod = intTipoRectificatoriaCod;
	}
	public Integer getIntPersEmpresaEntidadPk() {
		return intPersEmpresaEntidadPk;
	}
	public void setIntPersEmpresaEntidadPk(Integer intPersEmpresaEntidadPk) {
		this.intPersEmpresaEntidadPk = intPersEmpresaEntidadPk;
	}
	public Integer getIntPersPersonaEntidadPk() {
		return intPersPersonaEntidadPk;
	}
	public void setIntPersPersonaEntidadPk(Integer intPersPersonaEntidadPk) {
		this.intPersPersonaEntidadPk = intPersPersonaEntidadPk;
	}
	public Integer getIntPersEmpresaPersonaPk() {
		return intPersEmpresaPersonaPk;
	}
	public void setIntPersEmpresaPersonaPk(Integer intPersEmpresaPersonaPk) {
		this.intPersEmpresaPersonaPk = intPersEmpresaPersonaPk;
	}
	public Integer getIntPersPersonaEncargadaPk() {
		return intPersPersonaEncargadaPk;
	}
	public void setIntPersPersonaEncargadaPk(Integer intPersPersonaEncargadaPk) {
		this.intPersPersonaEncargadaPk = intPersPersonaEncargadaPk;
	}
	public Timestamp getTsImpuFechaPresentacionD() {
		return tsImpuFechaPresentacionD;
	}
	public void setTsImpuFechaPresentacionD(Timestamp tsImpuFechaPresentacionD) {
		this.tsImpuFechaPresentacionD = tsImpuFechaPresentacionD;
	}
	public Integer getIntImpuPeriodo() {
		return intImpuPeriodo;
	}
	public void setIntImpuPeriodo(Integer intImpuPeriodo) {
		this.intImpuPeriodo = intImpuPeriodo;
	}
	public Integer getIntParaTipoMonedaCod() {
		return intParaTipoMonedaCod;
	}
	public void setIntParaTipoMonedaCod(Integer intParaTipoMonedaCod) {
		this.intParaTipoMonedaCod = intParaTipoMonedaCod;
	}

	public String getStrImpuGlosa() {
		return strImpuGlosa;
	}
	public void setStrImpuGlosa(String strImpuGlosa) {
		this.strImpuGlosa = strImpuGlosa;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntParaEstadoPagoCod() {
		return intParaEstadoPagoCod;
	}
	public void setIntParaEstadoPagoCod(Integer intParaEstadoPagoCod) {
		this.intParaEstadoPagoCod = intParaEstadoPagoCod;
	}
	public Timestamp getTsImpuFechaRegistro() {
		return tsImpuFechaRegistro;
	}
	public void setTsImpuFechaRegistro(Timestamp tsImpuFechaRegistro) {
		this.tsImpuFechaRegistro = tsImpuFechaRegistro;
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
	public Integer getIntPersEmpresaLibroPK() {
		return intPersEmpresaLibroPK;
	}
	public void setIntPersEmpresaLibroPK(Integer intPersEmpresaLibroPK) {
		this.intPersEmpresaLibroPK = intPersEmpresaLibroPK;
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
	public Timestamp getTsImpuFechaEliminacionD() {
		return tsImpuFechaEliminacionD;
	}
	public void setTsImpuFechaEliminacionD(Timestamp tsImpuFechaEliminacionD) {
		this.tsImpuFechaEliminacionD = tsImpuFechaEliminacionD;
	}
	public Integer getIntPersEmpresaEliminaPk() {
		return intPersEmpresaEliminaPk;
	}
	public void setIntPersEmpresaEliminaPk(Integer intPersEmpresaEliminaPk) {
		this.intPersEmpresaEliminaPk = intPersEmpresaEliminaPk;
	}
	public Integer getIntPersPersonaElimina() {
		return intPersPersonaElimina;
	}
	public void setIntPersPersonaElimina(Integer intPersPersonaElimina) {
		this.intPersPersonaElimina = intPersPersonaElimina;
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
	public Integer getIntPersEmpresaRectPk() {
		return intPersEmpresaRectPk;
	}
	public void setIntPersEmpresaRectPk(Integer intPersEmpresaRectPk) {
		this.intPersEmpresaRectPk = intPersEmpresaRectPk;
	}
	public Integer getIntContItemImpuestoRectPk() {
		return intContItemImpuestoRectPk;
	}
	public void setIntContItemImpuestoRectPk(Integer intContItemImpuestoRectPk) {
		this.intContItemImpuestoRectPk = intContItemImpuestoRectPk;
	}
	public Integer getIntPersPersona() {
		return intPersPersona;
	}
	public void setIntPersPersona(Integer intPersPersona) {
		this.intPersPersona = intPersPersona;
	}
	public String getStrJuriRazonsocial() {
		return strJuriRazonsocial;
	}
	public void setStrJuriRazonsocial(String strJuriRazonsocial) {
		this.strJuriRazonsocial = strJuriRazonsocial;
	}
	public String getStrPersRuc() {
		return strPersRuc;
	}
	public void setStrPersRuc(String strPersRuc) {
		this.strPersRuc = strPersRuc;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getStrNombreCompleto() {
		return strNombreCompleto;
	}
	public void setStrNombreCompleto(String strNombreCompleto) {
		this.strNombreCompleto = strNombreCompleto;
	}
	public String getStrNumeroDocumento() {
		return strNumeroDocumento;
	}
	public void setStrNumeroDocumento(String strNumeroDocumento) {
		this.strNumeroDocumento = strNumeroDocumento;
	}
	public Integer getIntPersPerson() {
		return intPersPerson;
	}
	public void setIntPersPerson(Integer intPersPerson) {
		this.intPersPerson = intPersPerson;
	}
	public BigDecimal getBdImpuMonto() {
		return bdImpuMonto;
	}
	public void setBdImpuMonto(BigDecimal bdImpuMonto) {
		this.bdImpuMonto = bdImpuMonto;
	}
	public Integer getIntMeses() {
		return intMeses;
	}
	public void setIntMeses(Integer intMeses) {
		this.intMeses = intMeses;
	}
	public String getStrDescEstadoPago() {
		return strDescEstadoPago;
	}
	public void setStrDescEstadoPago(String strDescEstadoPago) {
		this.strDescEstadoPago = strDescEstadoPago;
	}
	public String getStrDescTipoImpuesto() {
		return strDescTipoImpuesto;
	}
	public void setStrDescTipoImpuesto(String strDescTipoImpuesto) {
		this.strDescTipoImpuesto = strDescTipoImpuesto;
	}
	public String getStrDescEstado() {
		return strDescEstado;
	}
	public void setStrDescEstado(String strDescEstado) {
		this.strDescEstado = strDescEstado;
	}
	public String getStrConcatenado() {
		return strConcatenado;
	}
	public void setStrConcatenado(String strConcatenado) {
		this.strConcatenado = strConcatenado;
	}
	public String getStrDescTipoRegistro() {
		return strDescTipoRegistro;
	}
	public void setStrDescTipoRegistro(String strDescTipoRegistro) {
		this.strDescTipoRegistro = strDescTipoRegistro;
	}
	public String getStrDescTipoMoneda() {
		return strDescTipoMoneda;
	}
	public void setStrDescTipoMoneda(String strDescTipoMoneda) {
		this.strDescTipoMoneda = strDescTipoMoneda;
	}
}
