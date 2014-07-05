package pe.com.tumi.reporte.operativo.credito.asociativo.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Asociativo extends TumiDomain{
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
	
	private Integer intCantProyectadas;
	private Integer intCantEjecutadas;
	private Integer intDiferencia;
	
	//Captaciones	
	private Integer intCantCondAltas;
	private Integer intTotalCaptaciones;
	
	//Renuncias
	private Integer intCantCondBajas;
	private Integer intTotalRenuncias;
	private String strFechaLiquidacion;
	
	//Detalle Captaciones/Renuncias
	private String strNroCuenta;
	private String strNombreSocio;
	private String strFechaApertura;
	private BigDecimal bdMontoAporte;
	private BigDecimal bdMontoFdoRetiro;
	private String strUnidadEjecutora;

	//Convenios
	private String strEntidadDesc;
	private String strNroRuc;
	private String strModalidadHaberes;
	private String strModalidadIncentivos;
	private String strModalidadCas;
	private String strTiempoVigencia;
	private String strFechaInicio;
	private String strFechaCese;
	private Integer intTipoRetencion;
	private String strRetencionPorcentaje;
	private String strRetencionMonto;
	private String strClausulaCobranza;
	private String strDocumentoFisico;
	private String strAlertaPorVencimiento;
	private String strTipoSocio;
	
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
	private String strEgresoEstado;
	private String strEgresoFecha;
	//Fin agregado por cdelosrios, 28/11/2013
	
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
	public Integer getIntCantProyectadas() {
		return intCantProyectadas;
	}
	public void setIntCantProyectadas(Integer intCantProyectadas) {
		this.intCantProyectadas = intCantProyectadas;
	}
	public Integer getIntCantEjecutadas() {
		return intCantEjecutadas;
	}
	public void setIntCantEjecutadas(Integer intCantEjecutadas) {
		this.intCantEjecutadas = intCantEjecutadas;
	}
	public Integer getIntDiferencia() {
		return intDiferencia;
	}
	public void setIntDiferencia(Integer intDiferencia) {
		this.intDiferencia = intDiferencia;
	}
	public Integer getIntCantCondAltas() {
		return intCantCondAltas;
	}
	public void setIntCantCondAltas(Integer intCantCondAltas) {
		this.intCantCondAltas = intCantCondAltas;
	}
	public Integer getIntTotalCaptaciones() {
		return intTotalCaptaciones;
	}
	public void setIntTotalCaptaciones(Integer intTotalCaptaciones) {
		this.intTotalCaptaciones = intTotalCaptaciones;
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
	public String getStrFechaApertura() {
		return strFechaApertura;
	}
	public void setStrFechaApertura(String strFechaApertura) {
		this.strFechaApertura = strFechaApertura;
	}
	public BigDecimal getBdMontoAporte() {
		return bdMontoAporte;
	}
	public void setBdMontoAporte(BigDecimal bdMontoAporte) {
		this.bdMontoAporte = bdMontoAporte;
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
	public Integer getIntCantCondBajas() {
		return intCantCondBajas;
	}
	public void setIntCantCondBajas(Integer intCantCondBajas) {
		this.intCantCondBajas = intCantCondBajas;
	}
	public Integer getIntTotalRenuncias() {
		return intTotalRenuncias;
	}
	public void setIntTotalRenuncias(Integer intTotalRenuncias) {
		this.intTotalRenuncias = intTotalRenuncias;
	}
	public String getStrFechaLiquidacion() {
		return strFechaLiquidacion;
	}
	public void setStrFechaLiquidacion(String strFechaLiquidacion) {
		this.strFechaLiquidacion = strFechaLiquidacion;
	}
	public String getStrEntidadDesc() {
		return strEntidadDesc;
	}
	public void setStrEntidadDesc(String strEntidadDesc) {
		this.strEntidadDesc = strEntidadDesc;
	}
	public String getStrNroRuc() {
		return strNroRuc;
	}
	public void setStrNroRuc(String strNroRuc) {
		this.strNroRuc = strNroRuc;
	}
	public String getStrModalidadHaberes() {
		return strModalidadHaberes;
	}
	public void setStrModalidadHaberes(String strModalidadHaberes) {
		this.strModalidadHaberes = strModalidadHaberes;
	}
	public String getStrModalidadIncentivos() {
		return strModalidadIncentivos;
	}
	public void setStrModalidadIncentivos(String strModalidadIncentivos) {
		this.strModalidadIncentivos = strModalidadIncentivos;
	}
	public String getStrModalidadCas() {
		return strModalidadCas;
	}
	public void setStrModalidadCas(String strModalidadCas) {
		this.strModalidadCas = strModalidadCas;
	}
	public String getStrTiempoVigencia() {
		return strTiempoVigencia;
	}
	public void setStrTiempoVigencia(String strTiempoVigencia) {
		this.strTiempoVigencia = strTiempoVigencia;
	}
	public String getStrFechaInicio() {
		return strFechaInicio;
	}
	public void setStrFechaInicio(String strFechaInicio) {
		this.strFechaInicio = strFechaInicio;
	}
	public String getStrFechaCese() {
		return strFechaCese;
	}
	public void setStrFechaCese(String strFechaCese) {
		this.strFechaCese = strFechaCese;
	}
	public Integer getIntTipoRetencion() {
		return intTipoRetencion;
	}
	public void setIntTipoRetencion(Integer intTipoRetencion) {
		this.intTipoRetencion = intTipoRetencion;
	}
	public String getStrRetencionPorcentaje() {
		return strRetencionPorcentaje;
	}
	public void setStrRetencionPorcentaje(String strRetencionPorcentaje) {
		this.strRetencionPorcentaje = strRetencionPorcentaje;
	}
	public String getStrRetencionMonto() {
		return strRetencionMonto;
	}
	public void setStrRetencionMonto(String strRetencionMonto) {
		this.strRetencionMonto = strRetencionMonto;
	}
	public String getStrClausulaCobranza() {
		return strClausulaCobranza;
	}
	public void setStrClausulaCobranza(String strClausulaCobranza) {
		this.strClausulaCobranza = strClausulaCobranza;
	}
	public String getStrAlertaPorVencimiento() {
		return strAlertaPorVencimiento;
	}
	public void setStrAlertaPorVencimiento(String strAlertaPorVencimiento) {
		this.strAlertaPorVencimiento = strAlertaPorVencimiento;
	}	
	public String getStrDocumentoFisico() {
		return strDocumentoFisico;
	}
	public void setStrDocumentoFisico(String strDocumentoFisico) {
		this.strDocumentoFisico = strDocumentoFisico;
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
	public String getStrEgresoEstado() {
		return strEgresoEstado;
	}
	public void setStrEgresoEstado(String strEgresoEstado) {
		this.strEgresoEstado = strEgresoEstado;
	}
	public String getStrEgresoFecha() {
		return strEgresoFecha;
	}
	public void setStrEgresoFecha(String strEgresoFecha) {
		this.strEgresoFecha = strEgresoFecha;
	}
	//Fin agregado por cdelosrios, 28/11/2013
	
	//jchavez
	public String getStrTipoSocio() {
		return strTipoSocio;
	}
	public void setStrTipoSocio(String strTipoSocio) {
		this.strTipoSocio = strTipoSocio;
	}
}
