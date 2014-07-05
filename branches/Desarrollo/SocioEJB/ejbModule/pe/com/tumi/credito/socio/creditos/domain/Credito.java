package pe.com.tumi.credito.socio.creditos.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Credito extends TumiDomain {
	private CreditoId id;
	private CondicionCredito condicionCredito;
	private Integer intParaTipoCreditoEmpresa;
	private String strDescripcion;
	private Date dtFechaIni;
	private Date dtFechaFin;
	private String strDtFechaIni;
	private String strDtFechaFin;
	private Integer intParaTipoPersonaCod;
	private Integer intParaRolPk;
	private Integer intParaCondicionLaboralCod;
	private Integer intParaTipoSbsCod;
	private Integer intParaMonedaCod;
	private BigDecimal bdMontoMinimo;
	private BigDecimal bdPorcMinimo;
	private Integer intParaTipoCaptacionMinimoCod;
	private BigDecimal bdMontoMaximo;
	private BigDecimal bdPorcMaximo;
	private Integer intParaTipoCaptacionMaximoCod;
	private Integer intParaTipoCalculoInteresCod;
	private BigDecimal bdTasaAnual;
	private BigDecimal bdFactor;
	private BigDecimal bdTasaMoratoriaAnual;
	private BigDecimal bdTasaMoratoriaMensual;
	private BigDecimal bdTasaSeguroDesgravamen;
	private Integer intParaAplicacionSeguroDesgrav;
	private Date dtFechaRegistro;
	private String strDtFechaRegistro;
	private Integer intParaEstadoSolicitudCod;
	private Integer intParaEstadoCod;
	
	//Por Definir
	private Integer intTipoFecha;
	private Integer	intGarantia;
	private Integer	intDescuento;
	private Integer	intExcepcion;
	private Integer	intActivo;
	private Integer	intCaduco;
	
	private List<CondicionCredito> listaCondicion;
	private List<CondicionComp> listaCondicionComp;
	private List<CondicionHabil> listaCondicionHabil;
	private List<CreditoTopeCaptacion> listaRangoMontoMin;
	private List<CreditoTopeCaptacion> listaRangoMontoMax;
	private List<CreditoInteres> listaCreditoInteres;
	private List<Finalidad> listaFinalidad;
	
	private List<CreditoDescuento> listaDescuento;
	private List<CreditoExcepcion> listaExcepcion;
	private List<CreditoGarantia> listaGarantiaPersonal;
	private List<CreditoGarantia> listaGarantiaReal;
	private List<CreditoGarantia> listaGarantiaRapidaRealizacion;
	private List<CreditoGarantia> listaGarantiaAutoliquidable;
	
	// 10.09.2013 - CGD
	// Se agrego para Refinanciamiento
	private CreditoGarantia creditoGarantiaPersonal;
	
	//Getters y Setters
	public CreditoId getId() {
		return id;
	}
	public void setId(CreditoId id) {
		this.id = id;
	}
	public CondicionCredito getCondicionCredito() {
		return condicionCredito;
	}
	public void setCondicionCredito(CondicionCredito condicionCredito) {
		this.condicionCredito = condicionCredito;
	}
	public Integer getIntParaTipoCreditoEmpresa() {
		return intParaTipoCreditoEmpresa;
	}
	public void setIntParaTipoCreditoEmpresa(Integer intParaTipoCreditoEmpresa) {
		this.intParaTipoCreditoEmpresa = intParaTipoCreditoEmpresa;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Date getDtFechaIni() {
		return dtFechaIni;
	}
	public void setDtFechaIni(Date dtFechaIni) {
		this.dtFechaIni = dtFechaIni;
	}
	public Date getDtFechaFin() {
		return dtFechaFin;
	}
	public void setDtFechaFin(Date dtFechaFin) {
		this.dtFechaFin = dtFechaFin;
	}
	public String getStrDtFechaIni() {
		return strDtFechaIni;
	}
	public void setStrDtFechaIni(String strDtFechaIni) {
		this.strDtFechaIni = strDtFechaIni;
	}
	public String getStrDtFechaFin() {
		return strDtFechaFin;
	}
	public void setStrDtFechaFin(String strDtFechaFin) {
		this.strDtFechaFin = strDtFechaFin;
	}
	public Integer getIntParaTipoPersonaCod() {
		return intParaTipoPersonaCod;
	}
	public void setIntParaTipoPersonaCod(Integer intParaTipoPersonaCod) {
		this.intParaTipoPersonaCod = intParaTipoPersonaCod;
	}
	public Integer getIntParaRolPk() {
		return intParaRolPk;
	}
	public void setIntParaRolPk(Integer intParaRolPk) {
		this.intParaRolPk = intParaRolPk;
	}
	public Integer getIntParaCondicionLaboralCod() {
		return intParaCondicionLaboralCod;
	}
	public void setIntParaCondicionLaboralCod(Integer intParaCondicionLaboralCod) {
		this.intParaCondicionLaboralCod = intParaCondicionLaboralCod;
	}
	public Integer getIntParaTipoSbsCod() {
		return intParaTipoSbsCod;
	}
	public void setIntParaTipoSbsCod(Integer intParaTipoSbsCod) {
		this.intParaTipoSbsCod = intParaTipoSbsCod;
	}
	public Integer getIntParaMonedaCod() {
		return intParaMonedaCod;
	}
	public void setIntParaMonedaCod(Integer intParaMonedaCod) {
		this.intParaMonedaCod = intParaMonedaCod;
	}
	public BigDecimal getBdMontoMinimo() {
		return bdMontoMinimo;
	}
	public void setBdMontoMinimo(BigDecimal bdMontoMinimo) {
		this.bdMontoMinimo = bdMontoMinimo;
	}
	public BigDecimal getBdPorcMinimo() {
		return bdPorcMinimo;
	}
	public void setBdPorcMinimo(BigDecimal bdPorcMinimo) {
		this.bdPorcMinimo = bdPorcMinimo;
	}
	public Integer getIntParaTipoCaptacionMinimoCod() {
		return intParaTipoCaptacionMinimoCod;
	}
	public void setIntParaTipoCaptacionMinimoCod(
			Integer intParaTipoCaptacionMinimoCod) {
		this.intParaTipoCaptacionMinimoCod = intParaTipoCaptacionMinimoCod;
	}
	public BigDecimal getBdMontoMaximo() {
		return bdMontoMaximo;
	}
	public void setBdMontoMaximo(BigDecimal bdMontoMaximo) {
		this.bdMontoMaximo = bdMontoMaximo;
	}
	public BigDecimal getBdPorcMaximo() {
		return bdPorcMaximo;
	}
	public void setBdPorcMaximo(BigDecimal bdPorcMaximo) {
		this.bdPorcMaximo = bdPorcMaximo;
	}
	public Integer getIntParaTipoCaptacionMaximoCod() {
		return intParaTipoCaptacionMaximoCod;
	}
	public void setIntParaTipoCaptacionMaximoCod(
			Integer intParaTipoCaptacionMaximoCod) {
		this.intParaTipoCaptacionMaximoCod = intParaTipoCaptacionMaximoCod;
	}
	public Integer getIntParaTipoCalculoInteresCod() {
		return intParaTipoCalculoInteresCod;
	}
	public void setIntParaTipoCalculoInteresCod(Integer intParaTipoCalculoInteresCod) {
		this.intParaTipoCalculoInteresCod = intParaTipoCalculoInteresCod;
	}
	public BigDecimal getBdTasaAnual() {
		return bdTasaAnual;
	}
	public void setBdTasaAnual(BigDecimal bdTasaAnual) {
		this.bdTasaAnual = bdTasaAnual;
	}
	public BigDecimal getBdFactor() {
		return bdFactor;
	}
	public void setBdFactor(BigDecimal bdFactor) {
		this.bdFactor = bdFactor;
	}
	public BigDecimal getBdTasaMoratoriaAnual() {
		return bdTasaMoratoriaAnual;
	}
	public void setBdTasaMoratoriaAnual(BigDecimal bdTasaMoratoriaAnual) {
		this.bdTasaMoratoriaAnual = bdTasaMoratoriaAnual;
	}
	public BigDecimal getBdTasaMoratoriaMensual() {
		return bdTasaMoratoriaMensual;
	}
	public void setBdTasaMoratoriaMensual(BigDecimal bdTasaMoratoriaMensual) {
		this.bdTasaMoratoriaMensual = bdTasaMoratoriaMensual;
	}
	public BigDecimal getBdTasaSeguroDesgravamen() {
		return bdTasaSeguroDesgravamen;
	}
	public void setBdTasaSeguroDesgravamen(BigDecimal bdTasaSeguroDesgravamen) {
		this.bdTasaSeguroDesgravamen = bdTasaSeguroDesgravamen;
	}
	public Integer getIntParaAplicacionSeguroDesgrav() {
		return intParaAplicacionSeguroDesgrav;
	}
	public void setIntParaAplicacionSeguroDesgrav(
			Integer intParaAplicacionSeguroDesgrav) {
		this.intParaAplicacionSeguroDesgrav = intParaAplicacionSeguroDesgrav;
	}
	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public String getStrDtFechaRegistro() {
		return strDtFechaRegistro;
	}
	public void setStrDtFechaRegistro(String strDtFechaRegistro) {
		this.strDtFechaRegistro = strDtFechaRegistro;
	}
	public Integer getIntParaEstadoSolicitudCod() {
		return intParaEstadoSolicitudCod;
	}
	public void setIntParaEstadoSolicitudCod(Integer intParaEstadoSolicitudCod) {
		this.intParaEstadoSolicitudCod = intParaEstadoSolicitudCod;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntTipoFecha() {
		return intTipoFecha;
	}
	public void setIntTipoFecha(Integer intTipoFecha) {
		this.intTipoFecha = intTipoFecha;
	}
	public Integer getIntGarantia() {
		return intGarantia;
	}
	public void setIntGarantia(Integer intGarantia) {
		this.intGarantia = intGarantia;
	}
	public Integer getIntDescuento() {
		return intDescuento;
	}
	public void setIntDescuento(Integer intDescuento) {
		this.intDescuento = intDescuento;
	}
	public Integer getIntExcepcion() {
		return intExcepcion;
	}
	public void setIntExcepcion(Integer intExcepcion) {
		this.intExcepcion = intExcepcion;
	}
	public Integer getIntActivo() {
		return intActivo;
	}
	public void setIntActivo(Integer intActivo) {
		this.intActivo = intActivo;
	}
	public Integer getIntCaduco() {
		return intCaduco;
	}
	public void setIntCaduco(Integer intCaduco) {
		this.intCaduco = intCaduco;
	}
	public List<CondicionCredito> getListaCondicion() {
		return listaCondicion;
	}
	public void setListaCondicion(List<CondicionCredito> listaCondicion) {
		this.listaCondicion = listaCondicion;
	}
	public List<CondicionComp> getListaCondicionComp() {
		return listaCondicionComp;
	}
	public void setListaCondicionComp(List<CondicionComp> listaCondicionComp) {
		this.listaCondicionComp = listaCondicionComp;
	}
	public List<CondicionHabil> getListaCondicionHabil() {
		return listaCondicionHabil;
	}
	public void setListaCondicionHabil(List<CondicionHabil> listaCondicionHabil) {
		this.listaCondicionHabil = listaCondicionHabil;
	}
	public List<CreditoTopeCaptacion> getListaRangoMontoMin() {
		return listaRangoMontoMin;
	}
	public void setListaRangoMontoMin(List<CreditoTopeCaptacion> listaRangoMontoMin) {
		this.listaRangoMontoMin = listaRangoMontoMin;
	}
	public List<CreditoTopeCaptacion> getListaRangoMontoMax() {
		return listaRangoMontoMax;
	}
	public void setListaRangoMontoMax(List<CreditoTopeCaptacion> listaRangoMontoMax) {
		this.listaRangoMontoMax = listaRangoMontoMax;
	}
	public List<CreditoInteres> getListaCreditoInteres() {
		return listaCreditoInteres;
	}
	public void setListaCreditoInteres(List<CreditoInteres> listaCreditoInteres) {
		this.listaCreditoInteres = listaCreditoInteres;
	}
	public List<Finalidad> getListaFinalidad() {
		return listaFinalidad;
	}
	public void setListaFinalidad(List<Finalidad> listaFinalidad) {
		this.listaFinalidad = listaFinalidad;
	}
	public List<CreditoDescuento> getListaDescuento() {
		return listaDescuento;
	}
	public void setListaDescuento(List<CreditoDescuento> listaDescuento) {
		this.listaDescuento = listaDescuento;
	}
	public List<CreditoExcepcion> getListaExcepcion() {
		return listaExcepcion;
	}
	public void setListaExcepcion(List<CreditoExcepcion> listaExcepcion) {
		this.listaExcepcion = listaExcepcion;
	}
	public List<CreditoGarantia> getListaGarantiaPersonal() {
		return listaGarantiaPersonal;
	}
	public void setListaGarantiaPersonal(List<CreditoGarantia> listaGarantiaPersonal) {
		this.listaGarantiaPersonal = listaGarantiaPersonal;
	}
	public List<CreditoGarantia> getListaGarantiaReal() {
		return listaGarantiaReal;
	}
	public void setListaGarantiaReal(List<CreditoGarantia> listaGarantiaReal) {
		this.listaGarantiaReal = listaGarantiaReal;
	}
	public List<CreditoGarantia> getListaGarantiaRapidaRealizacion() {
		return listaGarantiaRapidaRealizacion;
	}
	public void setListaGarantiaRapidaRealizacion(
			List<CreditoGarantia> listaGarantiaRapidaRealizacion) {
		this.listaGarantiaRapidaRealizacion = listaGarantiaRapidaRealizacion;
	}
	public List<CreditoGarantia> getListaGarantiaAutoliquidable() {
		return listaGarantiaAutoliquidable;
	}
	public void setListaGarantiaAutoliquidable(
			List<CreditoGarantia> listaGarantiaAutoliquidable) {
		this.listaGarantiaAutoliquidable = listaGarantiaAutoliquidable;
	}
	public CreditoGarantia getCreditoGarantiaPersonal() {
		return creditoGarantiaPersonal;
	}
	public void setCreditoGarantiaPersonal(CreditoGarantia creditoGarantiaPersonal) {
		this.creditoGarantiaPersonal = creditoGarantiaPersonal;
	}
	
}