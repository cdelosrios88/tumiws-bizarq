package pe.com.tumi.creditos.domain;

import java.util.Date;
import java.util.List;

public class Aportes {
	private Integer		intChkTasaInteres;
	private Integer		intChkEdadLimite;
	private Integer		intChkAportVigentes;
	private Integer		intChkAportCaduco;
	
	private Integer		intIdEmpresa;
	private Integer		intIdTipoCaptacion;
	private Integer		intIdCodigo;
	private Integer		intIdCondicionSocio;
	private String		strDescripcion;	
	private String		daFecIni;
	private String		daFecFin;
	private Integer		intIdTipoPersona;
	private Integer		intIdRol;
	private Integer		intIdTipoDcto;
	private Integer		intIdTipoConfig;
	private String		strTipoConfig;
	private Float		flValorConfig;
	private Float		flValor;
	private Integer		intIdAplicacion;
	private Integer		intIdMoneda;
	private Float		flTem;
	private Integer		intIdTasaNaturaleza;
	private Integer		intIdTasaFormula;
	private Float		flTea;
	private Float		flTna;
	private Integer		intEdadLimite;
	private String		strFecRegistro;
	private Integer		intIdEstSolicitud;
	private Integer		intIdEstado;
	private List 		cursorLista;
	private Integer		intCntCondSoc;
	
	
	private String		strCondicionSocio;
	private String 		strEstSolicitud;
	
	
	
	
	//plrt Para el llenado de la tabla CRE_V_CONFCAPTCONDICION
	//private Integer		intIdEmpresa;
	//private Integer		intIdTipoCaptacion;
	//private Integer		intIdCodigo;
	private Integer		intIdCondSocio;		
	private Integer		intIdValor;	
	
	//plrt Para el llenado de la tabla CRE_V_CONFCAPTCONDICION
	//private Integer		intIdEmpresa;
	//private Integer		intIdTipoCaptacion;
	//private Integer		intIdCodigo;
	//private Integer		intIdCondSocio;		
	//private Integer		intIdValor;		
	
	//plrt para la inserciòn de la tabla de mantCuentas	
	//private Integer		intIdEmpresa;
	//private Integer		intIdTipoCaptacion;
	//private Integer		intIdCodigo;	
	//private String	strDescripcion;
	private String	strFecIni;
	private String	strFecFin;
	//private Integer intIdTipoPersona;
	//private Integer intIdRol;
	//private Integer intIdTipoDscto;
	//private Integer intIdTipoConfig;
	private Integer intFlValorConfig;
	//private Integer intIdAplicacion;
	//private Integer intIdMoneda;
	private Integer intFltem;
	//private Integer intIdTasaNaturaleza;
	//private Integer intIdTasaFormula;
	private Integer intFltea;
	private Integer intFltna;
	//private Integer intEdadLimite;
	//private Integer intIdEstadoSolic;
	//private Integer intIdEstado;
	
	
	//para q renderize el tipo de condiciòn laboral, este datro todavía no lo veo en la DB
	private Integer intIdTipoCondicionLaboral;
	//para q renederice el tipo de cuenta en el combo Tipo de Cuenta
	private Integer intTipoCuenta;

	
	//Para llenar el grid de cuentaasd consideradas
	//private Integer intIdCuentasConsideradas;
	private Integer intIdTipoCaptacionAfecta;
	
	
	
	
	public Aportes(){		
		return;
	}	
	

	//Getters y Setters
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntIdTipoCaptacion() {
		return intIdTipoCaptacion;
	}
	public void setIntIdTipoCaptacion(Integer intIdTipoCaptacion) {
		this.intIdTipoCaptacion = intIdTipoCaptacion;
	}
	public Integer getIntIdCodigo() {
		return intIdCodigo;
	}
	public void setIntIdCodigo(Integer intIdCodigo) {
		this.intIdCodigo = intIdCodigo;
	}
	public Integer getIntIdCondicionSocio() {
		return intIdCondicionSocio;
	}
	public void setIntIdCondicionSocio(Integer intIdCondicionSocio) {
		this.intIdCondicionSocio = intIdCondicionSocio;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public String getDaFecIni() {
		return daFecIni;
	}
	public void setDaFecIni(String daFecIni) {
		this.daFecIni = daFecIni;
	}
	public String getDaFecFin() {
		return daFecFin;
	}
	public void setDaFecFin(String daFecFin) {
		this.daFecFin = daFecFin;
	}
	public Integer getIntIdTipoPersona() {
		return intIdTipoPersona;
	}
	public void setIntIdTipoPersona(Integer intIdTipoPersona) {
		this.intIdTipoPersona = intIdTipoPersona;
	}
	public Integer getIntIdRol() {
		return intIdRol;
	}
	public void setIntIdRol(Integer intIdRol) {
		this.intIdRol = intIdRol;
	}
	public Integer getIntIdTipoDcto() {
		return intIdTipoDcto;
	}
	public void setIntIdTipoDcto(Integer intIdTipoDcto) {
		this.intIdTipoDcto = intIdTipoDcto;
	}
	public Integer getIntIdTipoConfig() {
		return intIdTipoConfig;
	}
	public void setIntIdTipoConfig(Integer intIdTipoConfig) {
		this.intIdTipoConfig = intIdTipoConfig;
	}
	
	public String getStrTipoConfig() {
		return strTipoConfig;
	}


	public void setStrTipoConfig(String strTipoConfig) {
		this.strTipoConfig = strTipoConfig;
	}


	public Float getFlValorConfig() {
		return flValorConfig;
	}
	public void setFlValorConfig(Float flValorConfig) {
		this.flValorConfig = flValorConfig;
	}
	public Float getFlValor() {
		return flValor;
	}
	public void setFlValor(Float flValor) {
		this.flValor = flValor;
	}
	public Integer getIntIdAplicacion() {
		return intIdAplicacion;
	}
	public void setIntIdAplicacion(Integer intIdAplicacion) {
		this.intIdAplicacion = intIdAplicacion;
	}
	public Integer getIntIdMoneda() {
		return intIdMoneda;
	}
	public void setIntIdMoneda(Integer intIdMoneda) {
		this.intIdMoneda = intIdMoneda;
	}
	public Float getFlTem() {
		return flTem;
	}
	public void setFlTem(Float flTem) {
		this.flTem = flTem;
	}
	public Integer getIntIdTasaNaturaleza() {
		return intIdTasaNaturaleza;
	}
	public void setIntIdTasaNaturaleza(Integer intIdTasaNaturaleza) {
		this.intIdTasaNaturaleza = intIdTasaNaturaleza;
	}
	public Integer getIntIdTasaFormula() {
		return intIdTasaFormula;
	}
	public void setIntIdTasaFormula(Integer intIdTasaFormula) {
		this.intIdTasaFormula = intIdTasaFormula;
	}
	public Float getFlTea() {
		return flTea;
	}
	public void setFlTea(Float flTea) {
		this.flTea = flTea;
	}
	public Float getFlTna() {
		return flTna;
	}
	public void setFlTna(Float flTna) {
		this.flTna = flTna;
	}
	public Integer getIntEdadLimite() {
		return intEdadLimite;
	}
	public void setIntEdadLimite(Integer intEdadLimite) {
		this.intEdadLimite = intEdadLimite;
	}
	public String getStrFecRegistro() {
		return strFecRegistro;
	}
	public void setStrFecRegistro(String strFecRegistro) {
		this.strFecRegistro = strFecRegistro;
	}
	
	public Integer getIntIdEstSolicitud() {
		return intIdEstSolicitud;
	}
	public void setIntIdEstSolicitud(Integer intIdEstSolicitud) {
		this.intIdEstSolicitud = intIdEstSolicitud;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public List getCursorLista() {
		return cursorLista;
	}
	public void setCursorLista(List cursorLista) {
		this.cursorLista = cursorLista;
	}


	public String getStrFecIni() {
		return strFecIni;
	}


	public void setStrFecIni(String strFecIni) {
		this.strFecIni = strFecIni;
	}


	public String getStrFecFin() {
		return strFecFin;
	}


	public void setStrFecFin(String strFecFin) {
		this.strFecFin = strFecFin;
	}

/*
	public Integer getIntIdTipoDscto() {
		return intIdTipoDscto;
	}


	public void setIntIdTipoDscto(Integer intIdTipoDscto) {
		this.intIdTipoDscto = intIdTipoDscto;
	}
*/

	public Integer getIntFlValorConfig() {
		return intFlValorConfig;
	}


	public void setIntFlValorConfig(Integer intFlValorConfig) {
		this.intFlValorConfig = intFlValorConfig;
	}


	public Integer getIntFltem() {
		return intFltem;
	}


	public void setIntFltem(Integer intFltem) {
		this.intFltem = intFltem;
	}


	public Integer getIntFltea() {
		return intFltea;
	}


	public void setIntFltea(Integer intFltea) {
		this.intFltea = intFltea;
	}


	public Integer getIntFltna() {
		return intFltna;
	}


	public void setIntFltna(Integer intFltna) {
		this.intFltna = intFltna;
	}
/*

	public Integer getIntIdEstadoSolic() {
		return intIdEstadoSolic;
	}


	public void setIntIdEstadoSolic(Integer intIdEstadoSolic) {
		this.intIdEstadoSolic = intIdEstadoSolic;
	}
*/

	public Integer getIntIdCondSocio() {
		return intIdCondSocio;
	}


	public void setIntIdCondSocio(Integer intIdCondSocio) {
		this.intIdCondSocio = intIdCondSocio;
	}


	public Integer getIntIdValor() {
		return intIdValor;
	}


	public void setIntIdValor(Integer intIdValor) {
		this.intIdValor = intIdValor;
	}




	public Integer getIntIdTipoCondicionLaboral() {
		return intIdTipoCondicionLaboral;
	}


	public void setIntIdTipoCondicionLaboral(Integer intIdTipoCondicionLaboral) {
		this.intIdTipoCondicionLaboral = intIdTipoCondicionLaboral;
	}


	public Integer getIntTipoCuenta() {
		return intTipoCuenta;
	}


	public void setIntTipoCuenta(Integer intTipoCuenta) {
		this.intTipoCuenta = intTipoCuenta;
	}


	
	

	public Integer getIntIdTipoCaptacionAfecta() {
		return intIdTipoCaptacionAfecta;
	}


	public void setIntIdTipoCaptacionAfecta(Integer intIdTipoCaptacionAfecta) {
		this.intIdTipoCaptacionAfecta = intIdTipoCaptacionAfecta;
	}


	public Integer getIntChkTasaInteres() {
		return intChkTasaInteres;
	}


	public void setIntChkTasaInteres(Integer intChkTasaInteres) {
		this.intChkTasaInteres = intChkTasaInteres;
	}


	public Integer getIntChkEdadLimite() {
		return intChkEdadLimite;
	}


	public void setIntChkEdadLimite(Integer intChkEdadLimite) {
		this.intChkEdadLimite = intChkEdadLimite;
	}


	public Integer getIntChkAportVigentes() {
		return intChkAportVigentes;
	}


	public void setIntChkAportVigentes(Integer intChkAportVigentes) {
		this.intChkAportVigentes = intChkAportVigentes;
	}


	public Integer getIntChkAportCaduco() {
		return intChkAportCaduco;
	}


	public void setIntChkAportCaduco(Integer intChkAportCaduco) {
		this.intChkAportCaduco = intChkAportCaduco;
	}


	public Integer getIntCntCondSoc() {
		return intCntCondSoc;
	}


	public void setIntCntCondSoc(Integer intCntCondSoc) {
		this.intCntCondSoc = intCntCondSoc;
	}


	public String getStrCondicionSocio() {
		return strCondicionSocio;
	}


	public void setStrCondicionSocio(String strCondicionSocio) {
		this.strCondicionSocio = strCondicionSocio;
	}


	public String getStrEstSolicitud() {
		return strEstSolicitud;
	}


	public void setStrEstSolicitud(String strEstSolicitud) {
		this.strEstSolicitud = strEstSolicitud;
	}




	
}