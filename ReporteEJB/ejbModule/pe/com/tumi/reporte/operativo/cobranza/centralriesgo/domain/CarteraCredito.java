package pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CarteraCredito extends TumiDomain {
	private Integer intCodigoDeudor;
	private String strNumeroCredito;
	private String strCodigoAgencia;
	private String strTipoMoneda;
	private String strNombreDeudor;
	private String strTipoCredito;
	private String strTipoProducto;
	private String strFechaDesembolso;
	private BigDecimal bdMontoOtorgado;
	private BigDecimal bdSaldoCapitalDeuda;
	private BigDecimal bdTasaInteresNominalMensual;
	private Integer intNroCuotasPactadas;
	private Integer intDiasAtraso;
	private String strFechaVencimientoGral;
	private String strFechaUltimoMovimiento;
	private BigDecimal bdRequerida;
	private BigDecimal bdConstituida;
	private BigDecimal bdSuperavit;
	private BigDecimal bdPorCobrar;
	private BigDecimal bdSuspenso;
	private Integer intTipoOperacion;
	private Integer intClasificacionDeudor;
	private String strTipoGarantia;
	private String strNombreAdmin;
	private BigDecimal bdMontoCuotaPactada;
	private String strGenero;
	private String strDNI;
	private Integer intRelacLaboral;
	private String strFecNacimiento;
	private String strLugarResidencia;
	private String strDistrito;
	private String strProvincia;
	private String strDepartamento;
	private String strCargo;
	
	//GETTERS Y SETTERS
	public Integer getIntCodigoDeudor() {
		return intCodigoDeudor;
	}
	public void setIntCodigoDeudor(Integer intCodigoDeudor) {
		this.intCodigoDeudor = intCodigoDeudor;
	}
	public String getStrNumeroCredito() {
		return strNumeroCredito;
	}
	public void setStrNumeroCredito(String strNumeroCredito) {
		this.strNumeroCredito = strNumeroCredito;
	}
	public String getStrCodigoAgencia() {
		return strCodigoAgencia;
	}
	public void setStrCodigoAgencia(String strCodigoAgencia) {
		this.strCodigoAgencia = strCodigoAgencia;
	}
	public String getStrTipoMoneda() {
		return strTipoMoneda;
	}
	public void setStrTipoMoneda(String strTipoMoneda) {
		this.strTipoMoneda = strTipoMoneda;
	}
	public String getStrNombreDeudor() {
		return strNombreDeudor;
	}
	public void setStrNombreDeudor(String strNombreDeudor) {
		this.strNombreDeudor = strNombreDeudor;
	}
	public String getStrTipoCredito() {
		return strTipoCredito;
	}
	public void setStrTipoCredito(String strTipoCredito) {
		this.strTipoCredito = strTipoCredito;
	}
	public String getStrTipoProducto() {
		return strTipoProducto;
	}
	public void setStrTipoProducto(String strTipoProducto) {
		this.strTipoProducto = strTipoProducto;
	}
	public String getStrFechaDesembolso() {
		return strFechaDesembolso;
	}
	public void setStrFechaDesembolso(String strFechaDesembolso) {
		this.strFechaDesembolso = strFechaDesembolso;
	}
	public BigDecimal getBdMontoOtorgado() {
		return bdMontoOtorgado;
	}
	public void setBdMontoOtorgado(BigDecimal bdMontoOtorgado) {
		this.bdMontoOtorgado = bdMontoOtorgado;
	}
	public BigDecimal getBdSaldoCapitalDeuda() {
		return bdSaldoCapitalDeuda;
	}
	public void setBdSaldoCapitalDeuda(BigDecimal bdSaldoCapitalDeuda) {
		this.bdSaldoCapitalDeuda = bdSaldoCapitalDeuda;
	}
	public BigDecimal getBdTasaInteresNominalMensual() {
		return bdTasaInteresNominalMensual;
	}
	public void setBdTasaInteresNominalMensual(
			BigDecimal bdTasaInteresNominalMensual) {
		this.bdTasaInteresNominalMensual = bdTasaInteresNominalMensual;
	}
	public Integer getIntNroCuotasPactadas() {
		return intNroCuotasPactadas;
	}
	public void setIntNroCuotasPactadas(Integer intNroCuotasPactadas) {
		this.intNroCuotasPactadas = intNroCuotasPactadas;
	}
	public Integer getIntDiasAtraso() {
		return intDiasAtraso;
	}
	public void setIntDiasAtraso(Integer intDiasAtraso) {
		this.intDiasAtraso = intDiasAtraso;
	}
	public String getStrFechaVencimientoGral() {
		return strFechaVencimientoGral;
	}
	public void setStrFechaVencimientoGral(String strFechaVencimientoGral) {
		this.strFechaVencimientoGral = strFechaVencimientoGral;
	}
	public String getStrFechaUltimoMovimiento() {
		return strFechaUltimoMovimiento;
	}
	public void setStrFechaUltimoMovimiento(String strFechaUltimoMovimiento) {
		this.strFechaUltimoMovimiento = strFechaUltimoMovimiento;
	}
	public BigDecimal getBdRequerida() {
		return bdRequerida;
	}
	public void setBdRequerida(BigDecimal bdRequerida) {
		this.bdRequerida = bdRequerida;
	}
	public BigDecimal getBdConstituida() {
		return bdConstituida;
	}
	public void setBdConstituida(BigDecimal bdConstituida) {
		this.bdConstituida = bdConstituida;
	}
	public BigDecimal getBdSuperavit() {
		return bdSuperavit;
	}
	public void setBdSuperavit(BigDecimal bdSuperavit) {
		this.bdSuperavit = bdSuperavit;
	}
	public BigDecimal getBdPorCobrar() {
		return bdPorCobrar;
	}
	public void setBdPorCobrar(BigDecimal bdPorCobrar) {
		this.bdPorCobrar = bdPorCobrar;
	}
	public BigDecimal getBdSuspenso() {
		return bdSuspenso;
	}
	public void setBdSuspenso(BigDecimal bdSuspenso) {
		this.bdSuspenso = bdSuspenso;
	}
	public Integer getIntTipoOperacion() {
		return intTipoOperacion;
	}
	public void setIntTipoOperacion(Integer intTipoOperacion) {
		this.intTipoOperacion = intTipoOperacion;
	}
	public Integer getIntClasificacionDeudor() {
		return intClasificacionDeudor;
	}
	public void setIntClasificacionDeudor(Integer intClasificacionDeudor) {
		this.intClasificacionDeudor = intClasificacionDeudor;
	}
	public String getStrTipoGarantia() {
		return strTipoGarantia;
	}
	public void setStrTipoGarantia(String strTipoGarantia) {
		this.strTipoGarantia = strTipoGarantia;
	}
	public String getStrNombreAdmin() {
		return strNombreAdmin;
	}
	public void setStrNombreAdmin(String strNombreAdmin) {
		this.strNombreAdmin = strNombreAdmin;
	}
	public BigDecimal getBdMontoCuotaPactada() {
		return bdMontoCuotaPactada;
	}
	public void setBdMontoCuotaPactada(BigDecimal bdMontoCuotaPactada) {
		this.bdMontoCuotaPactada = bdMontoCuotaPactada;
	}
	public String getStrGenero() {
		return strGenero;
	}
	public void setStrGenero(String strGenero) {
		this.strGenero = strGenero;
	}
	public String getStrDNI() {
		return strDNI;
	}
	public void setStrDNI(String strDNI) {
		this.strDNI = strDNI;
	}
	public Integer getIntRelacLaboral() {
		return intRelacLaboral;
	}
	public void setIntRelacLaboral(Integer intRelacLaboral) {
		this.intRelacLaboral = intRelacLaboral;
	}
	public String getStrFecNacimiento() {
		return strFecNacimiento;
	}
	public void setStrFecNacimiento(String strFecNacimiento) {
		this.strFecNacimiento = strFecNacimiento;
	}
	public String getStrLugarResidencia() {
		return strLugarResidencia;
	}
	public void setStrLugarResidencia(String strLugarResidencia) {
		this.strLugarResidencia = strLugarResidencia;
	}
	public String getStrDistrito() {
		return strDistrito;
	}
	public void setStrDistrito(String strDistrito) {
		this.strDistrito = strDistrito;
	}
	public String getStrProvincia() {
		return strProvincia;
	}
	public void setStrProvincia(String strProvincia) {
		this.strProvincia = strProvincia;
	}
	public String getStrDepartamento() {
		return strDepartamento;
	}
	public void setStrDepartamento(String strDepartamento) {
		this.strDepartamento = strDepartamento;
	}
	public String getStrCargo() {
		return strCargo;
	}
	public void setStrCargo(String strCargo) {
		this.strCargo = strCargo;
	}
}
