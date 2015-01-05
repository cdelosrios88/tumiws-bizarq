package pe.com.tumi.servicio.solicitudPrestamo.domain.composite;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;

public class CronogramaCreditoComp extends TumiDomain {
	private CronogramaCredito cronogramaCredito;
	private String strFechaEnvio;
	private String strFechaVencimiento;
	private Integer intDiasTranscurridos;
	private BigDecimal bdSaldoCapital;
	private BigDecimal bdAmortizacion;
	private BigDecimal bdInteres;
	private BigDecimal bdCuotaMensual;
	private BigDecimal bdAportes;
	private BigDecimal bdTotalCuotaMensual;
	private Integer intParaTipoFormapago;
	private String strEstadoDescripcion;
	private BigDecimal bdSaldoCuota;
	
    private BigDecimal	bdCuotaFijaExpediente1;
    private BigDecimal	bdCuotaFijaExpediente2;
    private BigDecimal	bdCuotaFijaExpediente3;
    private BigDecimal	bdCuotaFijaExpediente4;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 02.09.2014
	private Integer intPeriodoPlanilla;
	private List<CronogramaCredito> lstCronogramaCreditoExpCredVigentes;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 08.09.2014
    private String 	strConcatenadoExpediente1;
    private String 	strConcatenadoExpediente2;
    private String 	strConcatenadoExpediente3;
    private String 	strConcatenadoExpediente4;
    
    
	public CronogramaCredito getCronogramaCredito() {
		return cronogramaCredito;
	}
	public void setCronogramaCredito(CronogramaCredito cronogramaCredito) {
		this.cronogramaCredito = cronogramaCredito;
	}
	public String getStrFechaEnvio() {
		return strFechaEnvio;
	}
	public void setStrFechaEnvio(String strFechaEnvio) {
		this.strFechaEnvio = strFechaEnvio;
	}
	public String getStrFechaVencimiento() {
		return strFechaVencimiento;
	}
	public void setStrFechaVencimiento(String strFechaVencimiento) {
		this.strFechaVencimiento = strFechaVencimiento;
	}
	public Integer getIntDiasTranscurridos() {
		return intDiasTranscurridos;
	}
	public void setIntDiasTranscurridos(Integer intDiasTranscurridos) {
		this.intDiasTranscurridos = intDiasTranscurridos;
	}
	public BigDecimal getBdSaldoCapital() {
		return bdSaldoCapital;
	}
	public void setBdSaldoCapital(BigDecimal bdSaldoCapital) {
		this.bdSaldoCapital = bdSaldoCapital;
	}
	public BigDecimal getBdAmortizacion() {
		return bdAmortizacion;
	}
	public void setBdAmortizacion(BigDecimal bdAmortizacion) {
		this.bdAmortizacion = bdAmortizacion;
	}
	public BigDecimal getBdInteres() {
		return bdInteres;
	}
	public void setBdInteres(BigDecimal bdInteres) {
		this.bdInteres = bdInteres;
	}
	public BigDecimal getBdAportes() {
		return bdAportes;
	}
	public void setBdAportes(BigDecimal bdAportes) {
		this.bdAportes = bdAportes;
	}
	public BigDecimal getBdCuotaMensual() {
		return bdCuotaMensual;
	}
	public void setBdCuotaMensual(BigDecimal bdCuotaMensual) {
		this.bdCuotaMensual = bdCuotaMensual;
	}
	public BigDecimal getBdTotalCuotaMensual() {
		return bdTotalCuotaMensual;
	}
	public void setBdTotalCuotaMensual(BigDecimal bdTotalCuotaMensual) {
		this.bdTotalCuotaMensual = bdTotalCuotaMensual;
	}
	public Integer getIntParaTipoFormapago() {
		return intParaTipoFormapago;
	}
	public void setIntParaTipoFormapago(Integer intParaTipoFormapago) {
		this.intParaTipoFormapago = intParaTipoFormapago;
	}
	public String getStrEstadoDescripcion() {
		return strEstadoDescripcion;
	}
	public void setStrEstadoDescripcion(String strEstadoDescripcion) {
		this.strEstadoDescripcion = strEstadoDescripcion;
	}
	public BigDecimal getBdSaldoCuota() {
		return bdSaldoCuota;
	}
	public void setBdSaldoCuota(BigDecimal bdSaldoCuota) {
		this.bdSaldoCuota = bdSaldoCuota;
	}
	public Integer getIntPeriodoPlanilla() {
		return intPeriodoPlanilla;
	}
	public void setIntPeriodoPlanilla(Integer intPeriodoPlanilla) {
		this.intPeriodoPlanilla = intPeriodoPlanilla;
	}
	public List<CronogramaCredito> getLstCronogramaCreditoExpCredVigentes() {
		return lstCronogramaCreditoExpCredVigentes;
	}
	public void setLstCronogramaCreditoExpCredVigentes(
			List<CronogramaCredito> lstCronogramaCreditoExpCredVigentes) {
		this.lstCronogramaCreditoExpCredVigentes = lstCronogramaCreditoExpCredVigentes;
	}
	public BigDecimal getBdCuotaFijaExpediente1() {
		return bdCuotaFijaExpediente1;
	}
	public void setBdCuotaFijaExpediente1(BigDecimal bdCuotaFijaExpediente1) {
		this.bdCuotaFijaExpediente1 = bdCuotaFijaExpediente1;
	}
	public BigDecimal getBdCuotaFijaExpediente2() {
		return bdCuotaFijaExpediente2;
	}
	public void setBdCuotaFijaExpediente2(BigDecimal bdCuotaFijaExpediente2) {
		this.bdCuotaFijaExpediente2 = bdCuotaFijaExpediente2;
	}
	public BigDecimal getBdCuotaFijaExpediente3() {
		return bdCuotaFijaExpediente3;
	}
	public void setBdCuotaFijaExpediente3(BigDecimal bdCuotaFijaExpediente3) {
		this.bdCuotaFijaExpediente3 = bdCuotaFijaExpediente3;
	}
	public BigDecimal getBdCuotaFijaExpediente4() {
		return bdCuotaFijaExpediente4;
	}
	public void setBdCuotaFijaExpediente4(BigDecimal bdCuotaFijaExpediente4) {
		this.bdCuotaFijaExpediente4 = bdCuotaFijaExpediente4;
	}
	public String getStrConcatenadoExpediente1() {
		return strConcatenadoExpediente1;
	}
	public void setStrConcatenadoExpediente1(String strConcatenadoExpediente1) {
		this.strConcatenadoExpediente1 = strConcatenadoExpediente1;
	}
	public String getStrConcatenadoExpediente2() {
		return strConcatenadoExpediente2;
	}
	public void setStrConcatenadoExpediente2(String strConcatenadoExpediente2) {
		this.strConcatenadoExpediente2 = strConcatenadoExpediente2;
	}
	public String getStrConcatenadoExpediente3() {
		return strConcatenadoExpediente3;
	}
	public void setStrConcatenadoExpediente3(String strConcatenadoExpediente3) {
		this.strConcatenadoExpediente3 = strConcatenadoExpediente3;
	}
	public String getStrConcatenadoExpediente4() {
		return strConcatenadoExpediente4;
	}
	public void setStrConcatenadoExpediente4(String strConcatenadoExpediente4) {
		this.strConcatenadoExpediente4 = strConcatenadoExpediente4;
	}
}
