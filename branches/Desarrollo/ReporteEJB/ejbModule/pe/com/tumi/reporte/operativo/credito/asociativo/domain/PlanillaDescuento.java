package pe.com.tumi.reporte.operativo.credito.asociativo.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PlanillaDescuento extends TumiDomain{
	//Planilla Descuento Desagregado
	private String strUnidadEjecutora;
	private String strModalidad;
	private String strTipo;
	private BigDecimal bdMontoEnviado;
	private Integer intNroSociosEnviado;
	private BigDecimal bdMontoEfectuado;
	private Integer intNroSociosEfectuado;
	private BigDecimal bdMontoDiferencia;
	private Integer intNroSociosDiferencia;
	
	//Pendiente Cobro
	private String strUnidadEjecutoraPC;
	private String strPeriodoPC;
	private String strModalidadPC;
	private BigDecimal bdEfectuadoPC;
	private BigDecimal bdPagadoPC;
	private BigDecimal bdSaldoPC;
	
	//Morosidad Planilla
	private String strUnidadEjecutoraMP;
	private String strModalidadMP;
	private String strTipoMP;
	private BigDecimal bdAdicionReferencialMP;
	private BigDecimal bdPlanillaGeneradaMP;
	private BigDecimal bdAdicionEjecutadaMP;
	private BigDecimal bdTotalEnviadaMP;
	private BigDecimal bdPlanillaIngresadaMP;
	private BigDecimal bdMontoAdicionalMP;
	private BigDecimal bdTotalEfectuadaMP;
	private BigDecimal bdMorosidadDiferenciaMP;
	private BigDecimal bdMorosidadPorcMP;
	private BigDecimal bdMorosidadIngresosCajaMP;
	private BigDecimal bdMorosidadDiferenciaCajaMP;
	private BigDecimal bdMorosidadPorcentajeCajaMP;
	
	//Pendiente Cobro
	private String strCuenta;
	private String strTipoSD;
	private String strModalidadSD;
	private BigDecimal bdEnviadoSD;
	private BigDecimal bdEfectuadoSD;
	private BigDecimal bdDiferenciaPllaSD;
	private BigDecimal bdIngresoCajaSD;
	private BigDecimal bdDiferenciaCajaSD;
	private BigDecimal bdIngresoTransferenciaSD;
	private BigDecimal bdDiferenciaTransferenciaSD;
	
	//Lista Entidad
	private Integer intIdUnidadEjecutora;
	private String strDescUnidadEjecutora;
	
	//GETTERS Y SETTERS
	public String getStrUnidadEjecutora() {
		return strUnidadEjecutora;
	}
	public void setStrUnidadEjecutora(String strUnidadEjecutora) {
		this.strUnidadEjecutora = strUnidadEjecutora;
	}
	public String getStrModalidad() {
		return strModalidad;
	}
	public void setStrModalidad(String strModalidad) {
		this.strModalidad = strModalidad;
	}
	public String getStrTipo() {
		return strTipo;
	}
	public void setStrTipo(String strTipo) {
		this.strTipo = strTipo;
	}
	public BigDecimal getBdMontoEnviado() {
		return bdMontoEnviado;
	}
	public void setBdMontoEnviado(BigDecimal bdMontoEnviado) {
		this.bdMontoEnviado = bdMontoEnviado;
	}
	public Integer getIntNroSociosEnviado() {
		return intNroSociosEnviado;
	}
	public void setIntNroSociosEnviado(Integer intNroSociosEnviado) {
		this.intNroSociosEnviado = intNroSociosEnviado;
	}
	public BigDecimal getBdMontoEfectuado() {
		return bdMontoEfectuado;
	}
	public void setBdMontoEfectuado(BigDecimal bdMontoEfectuado) {
		this.bdMontoEfectuado = bdMontoEfectuado;
	}
	public Integer getIntNroSociosEfectuado() {
		return intNroSociosEfectuado;
	}
	public void setIntNroSociosEfectuado(Integer intNroSociosEfectuado) {
		this.intNroSociosEfectuado = intNroSociosEfectuado;
	}
	public BigDecimal getBdMontoDiferencia() {
		return bdMontoDiferencia;
	}
	public void setBdMontoDiferencia(BigDecimal bdMontoDiferencia) {
		this.bdMontoDiferencia = bdMontoDiferencia;
	}
	public Integer getIntNroSociosDiferencia() {
		return intNroSociosDiferencia;
	}
	public void setIntNroSociosDiferencia(Integer intNroSociosDiferencia) {
		this.intNroSociosDiferencia = intNroSociosDiferencia;
	}
	public String getStrUnidadEjecutoraPC() {
		return strUnidadEjecutoraPC;
	}
	public void setStrUnidadEjecutoraPC(String strUnidadEjecutoraPC) {
		this.strUnidadEjecutoraPC = strUnidadEjecutoraPC;
	}
	public String getStrPeriodoPC() {
		return strPeriodoPC;
	}
	public void setStrPeriodoPC(String strPeriodoPC) {
		this.strPeriodoPC = strPeriodoPC;
	}
	public String getStrModalidadPC() {
		return strModalidadPC;
	}
	public void setStrModalidadPC(String strModalidadPC) {
		this.strModalidadPC = strModalidadPC;
	}
	public BigDecimal getBdEfectuadoPC() {
		return bdEfectuadoPC;
	}
	public void setBdEfectuadoPC(BigDecimal bdEfectuadoPC) {
		this.bdEfectuadoPC = bdEfectuadoPC;
	}
	public BigDecimal getBdPagadoPC() {
		return bdPagadoPC;
	}
	public void setBdPagadoPC(BigDecimal bdPagadoPC) {
		this.bdPagadoPC = bdPagadoPC;
	}
	public BigDecimal getBdSaldoPC() {
		return bdSaldoPC;
	}
	public void setBdSaldoPC(BigDecimal bdSaldoPC) {
		this.bdSaldoPC = bdSaldoPC;
	}
	public String getStrUnidadEjecutoraMP() {
		return strUnidadEjecutoraMP;
	}
	public void setStrUnidadEjecutoraMP(String strUnidadEjecutoraMP) {
		this.strUnidadEjecutoraMP = strUnidadEjecutoraMP;
	}
	public String getStrModalidadMP() {
		return strModalidadMP;
	}
	public void setStrModalidadMP(String strModalidadMP) {
		this.strModalidadMP = strModalidadMP;
	}
	public String getStrTipoMP() {
		return strTipoMP;
	}
	public void setStrTipoMP(String strTipoMP) {
		this.strTipoMP = strTipoMP;
	}
	public BigDecimal getBdAdicionReferencialMP() {
		return bdAdicionReferencialMP;
	}
	public void setBdAdicionReferencialMP(BigDecimal bdAdicionReferencialMP) {
		this.bdAdicionReferencialMP = bdAdicionReferencialMP;
	}
	public BigDecimal getBdPlanillaGeneradaMP() {
		return bdPlanillaGeneradaMP;
	}
	public void setBdPlanillaGeneradaMP(BigDecimal bdPlanillaGeneradaMP) {
		this.bdPlanillaGeneradaMP = bdPlanillaGeneradaMP;
	}
	public BigDecimal getBdAdicionEjecutadaMP() {
		return bdAdicionEjecutadaMP;
	}
	public void setBdAdicionEjecutadaMP(BigDecimal bdAdicionEjecutadaMP) {
		this.bdAdicionEjecutadaMP = bdAdicionEjecutadaMP;
	}
	public BigDecimal getBdTotalEnviadaMP() {
		return bdTotalEnviadaMP;
	}
	public void setBdTotalEnviadaMP(BigDecimal bdTotalEnviadaMP) {
		this.bdTotalEnviadaMP = bdTotalEnviadaMP;
	}
	public BigDecimal getBdPlanillaIngresadaMP() {
		return bdPlanillaIngresadaMP;
	}
	public void setBdPlanillaIngresadaMP(BigDecimal bdPlanillaIngresadaMP) {
		this.bdPlanillaIngresadaMP = bdPlanillaIngresadaMP;
	}
	public BigDecimal getBdMontoAdicionalMP() {
		return bdMontoAdicionalMP;
	}
	public void setBdMontoAdicionalMP(BigDecimal bdMontoAdicionalMP) {
		this.bdMontoAdicionalMP = bdMontoAdicionalMP;
	}
	public BigDecimal getBdTotalEfectuadaMP() {
		return bdTotalEfectuadaMP;
	}
	public void setBdTotalEfectuadaMP(BigDecimal bdTotalEfectuadaMP) {
		this.bdTotalEfectuadaMP = bdTotalEfectuadaMP;
	}
	public BigDecimal getBdMorosidadDiferenciaMP() {
		return bdMorosidadDiferenciaMP;
	}
	public void setBdMorosidadDiferenciaMP(BigDecimal bdMorosidadDiferenciaMP) {
		this.bdMorosidadDiferenciaMP = bdMorosidadDiferenciaMP;
	}
	public BigDecimal getBdMorosidadPorcMP() {
		return bdMorosidadPorcMP;
	}
	public void setBdMorosidadPorcMP(BigDecimal bdMorosidadPorcMP) {
		this.bdMorosidadPorcMP = bdMorosidadPorcMP;
	}
	public BigDecimal getBdMorosidadIngresosCajaMP() {
		return bdMorosidadIngresosCajaMP;
	}
	public void setBdMorosidadIngresosCajaMP(BigDecimal bdMorosidadIngresosCajaMP) {
		this.bdMorosidadIngresosCajaMP = bdMorosidadIngresosCajaMP;
	}
	public BigDecimal getBdMorosidadDiferenciaCajaMP() {
		return bdMorosidadDiferenciaCajaMP;
	}
	public void setBdMorosidadDiferenciaCajaMP(
			BigDecimal bdMorosidadDiferenciaCajaMP) {
		this.bdMorosidadDiferenciaCajaMP = bdMorosidadDiferenciaCajaMP;
	}
	public BigDecimal getBdMorosidadPorcentajeCajaMP() {
		return bdMorosidadPorcentajeCajaMP;
	}
	public void setBdMorosidadPorcentajeCajaMP(
			BigDecimal bdMorosidadPorcentajeCajaMP) {
		this.bdMorosidadPorcentajeCajaMP = bdMorosidadPorcentajeCajaMP;
	}
	public String getStrCuenta() {
		return strCuenta;
	}
	public void setStrCuenta(String strCuenta) {
		this.strCuenta = strCuenta;
	}
	public String getStrTipoSD() {
		return strTipoSD;
	}
	public void setStrTipoSD(String strTipoSD) {
		this.strTipoSD = strTipoSD;
	}
	public String getStrModalidadSD() {
		return strModalidadSD;
	}
	public void setStrModalidadSD(String strModalidadSD) {
		this.strModalidadSD = strModalidadSD;
	}
	public BigDecimal getBdEnviadoSD() {
		return bdEnviadoSD;
	}
	public void setBdEnviadoSD(BigDecimal bdEnviadoSD) {
		this.bdEnviadoSD = bdEnviadoSD;
	}
	public BigDecimal getBdEfectuadoSD() {
		return bdEfectuadoSD;
	}
	public void setBdEfectuadoSD(BigDecimal bdEfectuadoSD) {
		this.bdEfectuadoSD = bdEfectuadoSD;
	}
	public BigDecimal getBdDiferenciaPllaSD() {
		return bdDiferenciaPllaSD;
	}
	public void setBdDiferenciaPllaSD(BigDecimal bdDiferenciaPllaSD) {
		this.bdDiferenciaPllaSD = bdDiferenciaPllaSD;
	}
	public BigDecimal getBdIngresoCajaSD() {
		return bdIngresoCajaSD;
	}
	public void setBdIngresoCajaSD(BigDecimal bdIngresoCajaSD) {
		this.bdIngresoCajaSD = bdIngresoCajaSD;
	}
	public BigDecimal getBdDiferenciaCajaSD() {
		return bdDiferenciaCajaSD;
	}
	public void setBdDiferenciaCajaSD(BigDecimal bdDiferenciaCajaSD) {
		this.bdDiferenciaCajaSD = bdDiferenciaCajaSD;
	}
	public BigDecimal getBdIngresoTransferenciaSD() {
		return bdIngresoTransferenciaSD;
	}
	public void setBdIngresoTransferenciaSD(BigDecimal bdIngresoTransferenciaSD) {
		this.bdIngresoTransferenciaSD = bdIngresoTransferenciaSD;
	}
	public BigDecimal getBdDiferenciaTransferenciaSD() {
		return bdDiferenciaTransferenciaSD;
	}
	public void setBdDiferenciaTransferenciaSD(
			BigDecimal bdDiferenciaTransferenciaSD) {
		this.bdDiferenciaTransferenciaSD = bdDiferenciaTransferenciaSD;
	}
	public Integer getIntIdUnidadEjecutora() {
		return intIdUnidadEjecutora;
	}
	public void setIntIdUnidadEjecutora(Integer intIdUnidadEjecutora) {
		this.intIdUnidadEjecutora = intIdUnidadEjecutora;
	}
	public String getStrDescUnidadEjecutora() {
		return strDescUnidadEjecutora;
	}
	public void setStrDescUnidadEjecutora(String strDescUnidadEjecutora) {
		this.strDescUnidadEjecutora = strDescUnidadEjecutora;
	}
}