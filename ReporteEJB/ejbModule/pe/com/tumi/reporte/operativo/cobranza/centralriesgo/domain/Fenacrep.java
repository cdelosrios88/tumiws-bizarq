package pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Fenacrep extends TumiDomain {
	private String strCooperativa;
	private String strMes;
	private String strAnio;
	private Integer intSecuencia;
	private String strApepatRazSoc;
	private String strApeCasada;
	private String strApematRazSoc;
	private String strNombre;
	private String strSegundoNombre;
	private String strFecNac;
	private String strGenero;
	private String strEstCivil;
	private String strSiglaEmpresa;
	private Integer intCodSocio;
	private String strPartidaRegistral;
	private String strTipoDocumento;
	private String strNroDoc;
	private String strTipoPersona;
	private String strDomicilio;
	private String strClasifDeudor;
	private String strRol;
	private String strClasifDeudorAlineam;
	private String strCodAgencia;
	private String strNroCuenta;
	private String strMoneda;
	private String strNroCredito;
	private String strTipoSBS;
	private String strActividad;
	private String strFechaDesembolso;
	private BigDecimal bdMontoDesembolso;
	private BigDecimal bdTasaAnual;
	private BigDecimal bdSaldoColocacion;
	private String strMora;
	private String strGarantiaPreferida;
	private String strGarantiaAutoliq;
	private BigDecimal bdProvisionReq;
	private BigDecimal bdProvisionConst;
	private BigDecimal bdProvisionProciclica;
	private BigDecimal bdSaldoCastigado;
	private String strEstContable;
	private BigDecimal bdRendDevengado;
	private BigDecimal bdInteresSuspenso;
	
	//GETTERS Y SETTERS
	public String getStrCooperativa() {
		return strCooperativa;
	}
	public void setStrCooperativa(String strCooperativa) {
		this.strCooperativa = strCooperativa;
	}
	public String getStrMes() {
		return strMes;
	}
	public void setStrMes(String strMes) {
		this.strMes = strMes;
	}
	public String getStrAnio() {
		return strAnio;
	}
	public void setStrAnio(String strAnio) {
		this.strAnio = strAnio;
	}
	public Integer getIntSecuencia() {
		return intSecuencia;
	}
	public void setIntSecuencia(Integer intSecuencia) {
		this.intSecuencia = intSecuencia;
	}
	public String getStrApepatRazSoc() {
		return strApepatRazSoc;
	}
	public void setStrApepatRazSoc(String strApepatRazSoc) {
		this.strApepatRazSoc = strApepatRazSoc;
	}
	public String getStrApeCasada() {
		return strApeCasada;
	}
	public void setStrApeCasada(String strApeCasada) {
		this.strApeCasada = strApeCasada;
	}
	public String getStrApematRazSoc() {
		return strApematRazSoc;
	}
	public void setStrApematRazSoc(String strApematRazSoc) {
		this.strApematRazSoc = strApematRazSoc;
	}
	public String getStrNombre() {
		return strNombre;
	}
	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}
	public String getStrSegundoNombre() {
		return strSegundoNombre;
	}
	public void setStrSegundoNombre(String strSegundoNombre) {
		this.strSegundoNombre = strSegundoNombre;
	}
	public String getStrFecNac() {
		return strFecNac;
	}
	public void setStrFecNac(String strFecNac) {
		this.strFecNac = strFecNac;
	}
	public String getStrGenero() {
		return strGenero;
	}
	public void setStrGenero(String strGenero) {
		this.strGenero = strGenero;
	}
	public String getStrEstCivil() {
		return strEstCivil;
	}
	public void setStrEstCivil(String strEstCivil) {
		this.strEstCivil = strEstCivil;
	}
	public String getStrSiglaEmpresa() {
		return strSiglaEmpresa;
	}
	public void setStrSiglaEmpresa(String strSiglaEmpresa) {
		this.strSiglaEmpresa = strSiglaEmpresa;
	}
	public Integer getIntCodSocio() {
		return intCodSocio;
	}
	public void setIntCodSocio(Integer intCodSocio) {
		this.intCodSocio = intCodSocio;
	}
	public String getStrPartidaRegistral() {
		return strPartidaRegistral;
	}
	public void setStrPartidaRegistral(String strPartidaRegistral) {
		this.strPartidaRegistral = strPartidaRegistral;
	}
	public String getStrTipoDocumento() {
		return strTipoDocumento;
	}
	public void setStrTipoDocumento(String strTipoDocumento) {
		this.strTipoDocumento = strTipoDocumento;
	}
	public String getStrNroDoc() {
		return strNroDoc;
	}
	public void setStrNroDoc(String strNroDoc) {
		this.strNroDoc = strNroDoc;
	}
	public String getStrTipoPersona() {
		return strTipoPersona;
	}
	public void setStrTipoPersona(String strTipoPersona) {
		this.strTipoPersona = strTipoPersona;
	}
	public String getStrDomicilio() {
		return strDomicilio;
	}
	public void setStrDomicilio(String strDomicilio) {
		this.strDomicilio = strDomicilio;
	}
	public String getStrClasifDeudor() {
		return strClasifDeudor;
	}
	public void setStrClasifDeudor(String strClasifDeudor) {
		this.strClasifDeudor = strClasifDeudor;
	}
	public String getStrRol() {
		return strRol;
	}
	public void setStrRol(String strRol) {
		this.strRol = strRol;
	}
	public String getStrClasifDeudorAlineam() {
		return strClasifDeudorAlineam;
	}
	public void setStrClasifDeudorAlineam(String strClasifDeudorAlineam) {
		this.strClasifDeudorAlineam = strClasifDeudorAlineam;
	}
	public String getStrCodAgencia() {
		return strCodAgencia;
	}
	public void setStrCodAgencia(String strCodAgencia) {
		this.strCodAgencia = strCodAgencia;
	}
	public String getStrNroCuenta() {
		return strNroCuenta;
	}
	public void setStrNroCuenta(String strNroCuenta) {
		this.strNroCuenta = strNroCuenta;
	}
	public String getStrMoneda() {
		return strMoneda;
	}
	public void setStrMoneda(String strMoneda) {
		this.strMoneda = strMoneda;
	}
	public String getStrNroCredito() {
		return strNroCredito;
	}
	public void setStrNroCredito(String strNroCredito) {
		this.strNroCredito = strNroCredito;
	}
	public String getStrTipoSBS() {
		return strTipoSBS;
	}
	public void setStrTipoSBS(String strTipoSBS) {
		this.strTipoSBS = strTipoSBS;
	}
	public String getStrActividad() {
		return strActividad;
	}
	public void setStrActividad(String strActividad) {
		this.strActividad = strActividad;
	}
	public String getStrFechaDesembolso() {
		return strFechaDesembolso;
	}
	public void setStrFechaDesembolso(String strFechaDesembolso) {
		this.strFechaDesembolso = strFechaDesembolso;
	}
	public BigDecimal getBdMontoDesembolso() {
		return bdMontoDesembolso;
	}
	public void setBdMontoDesembolso(BigDecimal bdMontoDesembolso) {
		this.bdMontoDesembolso = bdMontoDesembolso;
	}
	public BigDecimal getBdTasaAnual() {
		return bdTasaAnual;
	}
	public void setBdTasaAnual(BigDecimal bdTasaAnual) {
		this.bdTasaAnual = bdTasaAnual;
	}
	public BigDecimal getBdSaldoColocacion() {
		return bdSaldoColocacion;
	}
	public void setBdSaldoColocacion(BigDecimal bdSaldoColocacion) {
		this.bdSaldoColocacion = bdSaldoColocacion;
	}
	public String getStrMora() {
		return strMora;
	}
	public void setStrMora(String strMora) {
		this.strMora = strMora;
	}
	public String getStrGarantiaPreferida() {
		return strGarantiaPreferida;
	}
	public void setStrGarantiaPreferida(String strGarantiaPreferida) {
		this.strGarantiaPreferida = strGarantiaPreferida;
	}
	public String getStrGarantiaAutoliq() {
		return strGarantiaAutoliq;
	}
	public void setStrGarantiaAutoliq(String strGarantiaAutoliq) {
		this.strGarantiaAutoliq = strGarantiaAutoliq;
	}
	public BigDecimal getBdProvisionReq() {
		return bdProvisionReq;
	}
	public void setBdProvisionReq(BigDecimal bdProvisionReq) {
		this.bdProvisionReq = bdProvisionReq;
	}
	public BigDecimal getBdProvisionConst() {
		return bdProvisionConst;
	}
	public void setBdProvisionConst(BigDecimal bdProvisionConst) {
		this.bdProvisionConst = bdProvisionConst;
	}
	public BigDecimal getBdProvisionProciclica() {
		return bdProvisionProciclica;
	}
	public void setBdProvisionProciclica(BigDecimal bdProvisionProciclica) {
		this.bdProvisionProciclica = bdProvisionProciclica;
	}
	public String getStrEstContable() {
		return strEstContable;
	}
	public void setStrEstContable(String strEstContable) {
		this.strEstContable = strEstContable;
	}
	public BigDecimal getBdSaldoCastigado() {
		return bdSaldoCastigado;
	}
	public void setBdSaldoCastigado(BigDecimal bdSaldoCastigado) {
		this.bdSaldoCastigado = bdSaldoCastigado;
	}
	public BigDecimal getBdRendDevengado() {
		return bdRendDevengado;
	}
	public void setBdRendDevengado(BigDecimal bdRendDevengado) {
		this.bdRendDevengado = bdRendDevengado;
	}
	public BigDecimal getBdInteresSuspenso() {
		return bdInteresSuspenso;
	}
	public void setBdInteresSuspenso(BigDecimal bdInteresSuspenso) {
		this.bdInteresSuspenso = bdInteresSuspenso;
	}
}
