package pe.com.tumi.reporte.operativo.credito.asociativo.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Servicio extends TumiDomain{
	//Generales
	private Integer	intPersEmpresaPk;
	private	Integer	intIdSucursal;
	private	Integer	intIdSubSucursal;
	
	private String strFmtPeriodo; //Nombre_Mes - Año
	private Integer intPeriodo; //Año y mes concatenados YYYYMM
	private Integer intAnio;
	private Integer intMes;
	
	private Integer intTipoSocio;
	private Integer intModalidad;
	
	private BigDecimal bdProyectado;	
	private BigDecimal bdMontoEjecutado;
	private BigDecimal bdDiferencia;
	
	//Detalle Prestamos
	private String strNroCuenta;
	private String strNombreSocio;
	private String strFechaEgreso;
	private BigDecimal bdMontoTotal;
	private BigDecimal bdMontoFdoRetiro;
	private String strUnidadEjecutora;
	
	//Agregado por cdelosrios, 28/11/2013
	//Expediente de Previsión
	private Integer intNroSolicitud;
	private String strCuenta;
	private String strTipo;
	private BigDecimal bdMontoBruto;
	private BigDecimal bdMontoGastos;
	private BigDecimal bdMontoNeto;
	private String strFechaFallecimiento;
	private String strFechaSolicitud;
	private String strEvaluacionEstado;
	private String strEvaluacionFecha;
	private String strEgresoFecha;
	//Fin agregado por cdelosrios, 28/11/2013
	
	//Agregado por cdelosrios, 19/01/2014
	//Saldos y Servicios
	private String strCuentaSaldo;
	private String strFechaSolicitudSaldo;
	private String strTipoSaldo;
	private String strNumeroSolicitudServicio;
	private BigDecimal bdMontoSolicitud;
	private BigDecimal bdMontoCancelado;
	private BigDecimal bdMontoSaldo;
	private Integer intNroCuotasTotales;
	private Integer intCuotasFaltantes;
	//Fin agregado por cdelosrios, 19/01/2014
	
	//Agregado por cdelosrios, 27/01/2014
	//Analisis Primer Dscto
	private String strCuentaPrimerDscto;
	private String strTipoSocio;
	private Integer intIdSucursalSocio;
	private Integer intIdSucursalAtencion;
	private String strTipoSolicitud;
	private String strNroSolicitud;
	private String strReprestamo;
	private String strFechaSolicitudPrimerDscto;
	private String strPrimerMes;
	private BigDecimal bdMontoSolicitudPrimerDscto;
	private BigDecimal bdCapacidadSolicitudPrimerDscto;
	private BigDecimal bdCuotaFijaSolicitudPrimerDscto;
	private BigDecimal bdDsctoEnviado;
	private BigDecimal bdDsctoEfectuado;
	private BigDecimal bdDiferenciaDscto;
	private BigDecimal bdIngresoCaja;
	private BigDecimal bdDiferenciaCaja;
	//Fin agregado por cdelosrios, 27/01/2014
	
	//Inicio agregado por cdelosrios, 05/02/2014
	private String strSucursalSocio;
	private String strSucursalAtencion;
	//Fin agregado por cdelosrios, 05/02/2014
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public Integer getIntIdSubSucursal() {
		return intIdSubSucursal;
	}
	public void setIntIdSubSucursal(Integer intIdSubSucursal) {
		this.intIdSubSucursal = intIdSubSucursal;
	}
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	public Integer getIntMes() {
		return intMes;
	}
	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}
	public BigDecimal getBdProyectado() {
		return bdProyectado;
	}
	public void setBdProyectado(BigDecimal bdProyectado) {
		this.bdProyectado = bdProyectado;
	}
	public BigDecimal getBdMontoEjecutado() {
		return bdMontoEjecutado;
	}
	public void setBdMontoEjecutado(BigDecimal bdMontoEjecutado) {
		this.bdMontoEjecutado = bdMontoEjecutado;
	}
	public BigDecimal getBdDiferencia() {
		return bdDiferencia;
	}
	public void setBdDiferencia(BigDecimal bdDiferencia) {
		this.bdDiferencia = bdDiferencia;
	}
	public Integer getIntAnio() {
		return intAnio;
	}
	public void setIntAnio(Integer intAnio) {
		this.intAnio = intAnio;
	}
	public String getStrFmtPeriodo() {
		return strFmtPeriodo;
	}
	public void setStrFmtPeriodo(String strFmtPeriodo) {
		this.strFmtPeriodo = strFmtPeriodo;
	}
	public Integer getIntTipoSocio() {
		return intTipoSocio;
	}
	public void setIntTipoSocio(Integer intTipoSocio) {
		this.intTipoSocio = intTipoSocio;
	}
	public Integer getIntModalidad() {
		return intModalidad;
	}
	public void setIntModalidad(Integer intModalidad) {
		this.intModalidad = intModalidad;
	}
	public String getStrNroCuenta() {
		return strNroCuenta;
	}
	public void setStrNroCuenta(String strNroCuenta) {
		this.strNroCuenta = strNroCuenta;
	}
	public String getStrNombreSocio() {
		return strNombreSocio;
	}
	public void setStrNombreSocio(String strNombreSocio) {
		this.strNombreSocio = strNombreSocio;
	}
	public String getStrFechaEgreso() {
		return strFechaEgreso;
	}
	public void setStrFechaEgreso(String strFechaEgreso) {
		this.strFechaEgreso = strFechaEgreso;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public BigDecimal getBdMontoFdoRetiro() {
		return bdMontoFdoRetiro;
	}
	public void setBdMontoFdoRetiro(BigDecimal bdMontoFdoRetiro) {
		this.bdMontoFdoRetiro = bdMontoFdoRetiro;
	}
	public String getStrUnidadEjecutora() {
		return strUnidadEjecutora;
	}
	public void setStrUnidadEjecutora(String strUnidadEjecutora) {
		this.strUnidadEjecutora = strUnidadEjecutora;
	}
	//Agregado por cdelosrios, 28/11/2013
	public Integer getIntNroSolicitud() {
		return intNroSolicitud;
	}
	public void setIntNroSolicitud(Integer intNroSolicitud) {
		this.intNroSolicitud = intNroSolicitud;
	}
	public String getStrCuenta() {
		return strCuenta;
	}
	public void setStrCuenta(String strCuenta) {
		this.strCuenta = strCuenta;
	}
	public String getStrTipo() {
		return strTipo;
	}
	public void setStrTipo(String strTipo) {
		this.strTipo = strTipo;
	}
	public BigDecimal getBdMontoBruto() {
		return bdMontoBruto;
	}
	public void setBdMontoBruto(BigDecimal bdMontoBruto) {
		this.bdMontoBruto = bdMontoBruto;
	}
	public BigDecimal getBdMontoGastos() {
		return bdMontoGastos;
	}
	public void setBdMontoGastos(BigDecimal bdMontoGastos) {
		this.bdMontoGastos = bdMontoGastos;
	}
	public BigDecimal getBdMontoNeto() {
		return bdMontoNeto;
	}
	public void setBdMontoNeto(BigDecimal bdMontoNeto) {
		this.bdMontoNeto = bdMontoNeto;
	}
	public String getStrFechaFallecimiento() {
		return strFechaFallecimiento;
	}
	public void setStrFechaFallecimiento(String strFechaFallecimiento) {
		this.strFechaFallecimiento = strFechaFallecimiento;
	}
	public String getStrFechaSolicitud() {
		return strFechaSolicitud;
	}
	public void setStrFechaSolicitud(String strFechaSolicitud) {
		this.strFechaSolicitud = strFechaSolicitud;
	}
	public String getStrEvaluacionEstado() {
		return strEvaluacionEstado;
	}
	public void setStrEvaluacionEstado(String strEvaluacionEstado) {
		this.strEvaluacionEstado = strEvaluacionEstado;
	}
	public String getStrEvaluacionFecha() {
		return strEvaluacionFecha;
	}
	public void setStrEvaluacionFecha(String strEvaluacionFecha) {
		this.strEvaluacionFecha = strEvaluacionFecha;
	}
	public String getStrEgresoFecha() {
		return strEgresoFecha;
	}
	public void setStrEgresoFecha(String strEgresoFecha) {
		this.strEgresoFecha = strEgresoFecha;
	}
	//Fin agregado por cdelosrios, 28/11/2013
	public String getStrCuentaSaldo() {
		return strCuentaSaldo;
	}
	public void setStrCuentaSaldo(String strCuentaSaldo) {
		this.strCuentaSaldo = strCuentaSaldo;
	}
	public String getStrFechaSolicitudSaldo() {
		return strFechaSolicitudSaldo;
	}
	public void setStrFechaSolicitudSaldo(String strFechaSolicitudSaldo) {
		this.strFechaSolicitudSaldo = strFechaSolicitudSaldo;
	}
	public String getStrTipoSaldo() {
		return strTipoSaldo;
	}
	public void setStrTipoSaldo(String strTipoSaldo) {
		this.strTipoSaldo = strTipoSaldo;
	}
	public String getStrNumeroSolicitudServicio() {
		return strNumeroSolicitudServicio;
	}
	public void setStrNumeroSolicitudServicio(String strNumeroSolicitudServicio) {
		this.strNumeroSolicitudServicio = strNumeroSolicitudServicio;
	}
	public BigDecimal getBdMontoSolicitud() {
		return bdMontoSolicitud;
	}
	public void setBdMontoSolicitud(BigDecimal bdMontoSolicitud) {
		this.bdMontoSolicitud = bdMontoSolicitud;
	}
	public BigDecimal getBdMontoCancelado() {
		return bdMontoCancelado;
	}
	public void setBdMontoCancelado(BigDecimal bdMontoCancelado) {
		this.bdMontoCancelado = bdMontoCancelado;
	}
	public BigDecimal getBdMontoSaldo() {
		return bdMontoSaldo;
	}
	public void setBdMontoSaldo(BigDecimal bdMontoSaldo) {
		this.bdMontoSaldo = bdMontoSaldo;
	}
	public Integer getIntNroCuotasTotales() {
		return intNroCuotasTotales;
	}
	public void setIntNroCuotasTotales(Integer intNroCuotasTotales) {
		this.intNroCuotasTotales = intNroCuotasTotales;
	}
	public Integer getIntCuotasFaltantes() {
		return intCuotasFaltantes;
	}
	public void setIntCuotasFaltantes(Integer intCuotasFaltantes) {
		this.intCuotasFaltantes = intCuotasFaltantes;
	}
	//Agregado por cdelosrios, 27/01/2014
	public String getStrCuentaPrimerDscto() {
		return strCuentaPrimerDscto;
	}
	public void setStrCuentaPrimerDscto(String strCuentaPrimerDscto) {
		this.strCuentaPrimerDscto = strCuentaPrimerDscto;
	}
	public String getStrTipoSocio() {
		return strTipoSocio;
	}
	public void setStrTipoSocio(String strTipoSocio) {
		this.strTipoSocio = strTipoSocio;
	}
	public Integer getIntIdSucursalSocio() {
		return intIdSucursalSocio;
	}
	public void setIntIdSucursalSocio(Integer intIdSucursalSocio) {
		this.intIdSucursalSocio = intIdSucursalSocio;
	}
	public Integer getIntIdSucursalAtencion() {
		return intIdSucursalAtencion;
	}
	public void setIntIdSucursalAtencion(Integer intIdSucursalAtencion) {
		this.intIdSucursalAtencion = intIdSucursalAtencion;
	}
	public String getStrTipoSolicitud() {
		return strTipoSolicitud;
	}
	public void setStrTipoSolicitud(String strTipoSolicitud) {
		this.strTipoSolicitud = strTipoSolicitud;
	}
	public String getStrNroSolicitud() {
		return strNroSolicitud;
	}
	public void setStrNroSolicitud(String strNroSolicitud) {
		this.strNroSolicitud = strNroSolicitud;
	}
	public String getStrReprestamo() {
		return strReprestamo;
	}
	public void setStrReprestamo(String strReprestamo) {
		this.strReprestamo = strReprestamo;
	}
	public String getStrFechaSolicitudPrimerDscto() {
		return strFechaSolicitudPrimerDscto;
	}
	public void setStrFechaSolicitudPrimerDscto(String strFechaSolicitudPrimerDscto) {
		this.strFechaSolicitudPrimerDscto = strFechaSolicitudPrimerDscto;
	}
	public String getStrPrimerMes() {
		return strPrimerMes;
	}
	public void setStrPrimerMes(String strPrimerMes) {
		this.strPrimerMes = strPrimerMes;
	}
	public BigDecimal getBdMontoSolicitudPrimerDscto() {
		return bdMontoSolicitudPrimerDscto;
	}
	public void setBdMontoSolicitudPrimerDscto(
			BigDecimal bdMontoSolicitudPrimerDscto) {
		this.bdMontoSolicitudPrimerDscto = bdMontoSolicitudPrimerDscto;
	}
	public BigDecimal getBdCapacidadSolicitudPrimerDscto() {
		return bdCapacidadSolicitudPrimerDscto;
	}
	public void setBdCapacidadSolicitudPrimerDscto(
			BigDecimal bdCapacidadSolicitudPrimerDscto) {
		this.bdCapacidadSolicitudPrimerDscto = bdCapacidadSolicitudPrimerDscto;
	}
	public BigDecimal getBdCuotaFijaSolicitudPrimerDscto() {
		return bdCuotaFijaSolicitudPrimerDscto;
	}
	public void setBdCuotaFijaSolicitudPrimerDscto(
			BigDecimal bdCuotaFijaSolicitudPrimerDscto) {
		this.bdCuotaFijaSolicitudPrimerDscto = bdCuotaFijaSolicitudPrimerDscto;
	}
	public BigDecimal getBdDsctoEnviado() {
		return bdDsctoEnviado;
	}
	public void setBdDsctoEnviado(BigDecimal bdDsctoEnviado) {
		this.bdDsctoEnviado = bdDsctoEnviado;
	}
	public BigDecimal getBdDsctoEfectuado() {
		return bdDsctoEfectuado;
	}
	public void setBdDsctoEfectuado(BigDecimal bdDsctoEfectuado) {
		this.bdDsctoEfectuado = bdDsctoEfectuado;
	}
	public BigDecimal getBdDiferenciaDscto() {
		return bdDiferenciaDscto;
	}
	public void setBdDiferenciaDscto(BigDecimal bdDiferenciaDscto) {
		this.bdDiferenciaDscto = bdDiferenciaDscto;
	}
	public BigDecimal getBdIngresoCaja() {
		return bdIngresoCaja;
	}
	public void setBdIngresoCaja(BigDecimal bdIngresoCaja) {
		this.bdIngresoCaja = bdIngresoCaja;
	}
	public BigDecimal getBdDiferenciaCaja() {
		return bdDiferenciaCaja;
	}
	public void setBdDiferenciaCaja(BigDecimal bdDiferenciaCaja) {
		this.bdDiferenciaCaja = bdDiferenciaCaja;
	}
	//Fin agregado por cdelosrios, 27/01/2014
	//Inicio agregado por cdelosrios, 05/02/2014
	public String getStrSucursalSocio() {
		return strSucursalSocio;
	}
	public void setStrSucursalSocio(String strSucursalSocio) {
		this.strSucursalSocio = strSucursalSocio;
	}
	public String getStrSucursalAtencion() {
		return strSucursalAtencion;
	}
	public void setStrSucursalAtencion(String strSucursalAtencion) {
		this.strSucursalAtencion = strSucursalAtencion;
	}
	//Fin agregado por cdelosrios, 05/02/2014
}
