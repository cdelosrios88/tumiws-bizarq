package pe.com.tumi.riesgo.cartera.domain;

import java.math.BigDecimal;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CarteraCreditoDetalle extends TumiDomain{

   private CarteraCreditoDetalleId id;
    
  
   private Integer intPersPersona;
   private Integer intCacdRelacionlaboral;
   private Integer intCacdTipodocumento;
   private Integer intCacrRuc;
   private Integer intParaTiposbs;
   private Date    tsCacrFechacredito;
   private Integer intCacrMontoinicial;
   private Integer intCacrSaldocredito;
   private Integer intParaTipocategoriariesgo;
   private BigDecimal bdProvTmontoprovision;
   private BigDecimal bdCacrMontoprovisionrequerida;
   private BigDecimal bdCacrMontoprovisionconstituida;
   private Integer intCacrCuotasvencidas;
   private Integer intCacrMontorecuperación;
   private Integer intCacrMontoprovisión;
   private Integer intCacrInteressuspenso;
   private Integer intPersEmpresa;
   private Integer intCsocCuenta;
   private Integer intCserItemexpediente;
   private Integer intCserItemdetexpediente;
   private Integer intCmovItemcuentaconcepto;
   private Integer intSucuIdsucursal;
   private Integer intSudeIdsubsucursal;
   
public CarteraCreditoDetalleId getId() {
	return id;
}
public void setId(CarteraCreditoDetalleId id) {
	this.id = id;
}
public Integer getIntPersPersona() {
	return intPersPersona;
}
public void setIntPersPersona(Integer intPersPersona) {
	this.intPersPersona = intPersPersona;
}
public Integer getIntCacdRelacionlaboral() {
	return intCacdRelacionlaboral;
}
public void setIntCacdRelacionlaboral(Integer intCacdRelacionlaboral) {
	this.intCacdRelacionlaboral = intCacdRelacionlaboral;
}
public Integer getIntCacdTipodocumento() {
	return intCacdTipodocumento;
}
public void setIntCacdTipodocumento(Integer intCacdTipodocumento) {
	this.intCacdTipodocumento = intCacdTipodocumento;
}
public Integer getIntCacrRuc() {
	return intCacrRuc;
}
public void setIntCacrRuc(Integer intCacrRuc) {
	this.intCacrRuc = intCacrRuc;
}
public Integer getIntParaTiposbs() {
	return intParaTiposbs;
}
public void setIntParaTiposbs(Integer intParaTiposbs) {
	this.intParaTiposbs = intParaTiposbs;
}
public Date getTsCacrFechacredito() {
	return tsCacrFechacredito;
}
public void setTsCacrFechacredito(Date tsCacrFechacredito) {
	this.tsCacrFechacredito = tsCacrFechacredito;
}
public Integer getIntCacrMontoinicial() {
	return intCacrMontoinicial;
}
public void setIntCacrMontoinicial(Integer intCacrMontoinicial) {
	this.intCacrMontoinicial = intCacrMontoinicial;
}
public Integer getIntCacrSaldocredito() {
	return intCacrSaldocredito;
}
public void setIntCacrSaldocredito(Integer intCacrSaldocredito) {
	this.intCacrSaldocredito = intCacrSaldocredito;
}
public Integer getIntParaTipocategoriariesgo() {
	return intParaTipocategoriariesgo;
}
public void setIntParaTipocategoriariesgo(Integer intParaTipocategoriariesgo) {
	this.intParaTipocategoriariesgo = intParaTipocategoriariesgo;
}
public BigDecimal getBdProvTmontoprovision() {
	return bdProvTmontoprovision;
}
public void setBdProvTmontoprovision(BigDecimal bdProvTmontoprovision) {
	this.bdProvTmontoprovision = bdProvTmontoprovision;
}
public BigDecimal getBdCacrMontoprovisionrequerida() {
	return bdCacrMontoprovisionrequerida;
}
public void setBdCacrMontoprovisionrequerida(
		BigDecimal bdCacrMontoprovisionrequerida) {
	this.bdCacrMontoprovisionrequerida = bdCacrMontoprovisionrequerida;
}
public BigDecimal getBdCacrMontoprovisionconstituida() {
	return bdCacrMontoprovisionconstituida;
}
public void setBdCacrMontoprovisionconstituida(
		BigDecimal bdCacrMontoprovisionconstituida) {
	this.bdCacrMontoprovisionconstituida = bdCacrMontoprovisionconstituida;
}
public Integer getIntCacrCuotasvencidas() {
	return intCacrCuotasvencidas;
}
public void setIntCacrCuotasvencidas(Integer intCacrCuotasvencidas) {
	this.intCacrCuotasvencidas = intCacrCuotasvencidas;
}
public Integer getIntCacrMontorecuperación() {
	return intCacrMontorecuperación;
}
public void setIntCacrMontorecuperación(Integer intCacrMontorecuperación) {
	this.intCacrMontorecuperación = intCacrMontorecuperación;
}
public Integer getIntCacrMontoprovisión() {
	return intCacrMontoprovisión;
}
public void setIntCacrMontoprovisión(Integer intCacrMontoprovisión) {
	this.intCacrMontoprovisión = intCacrMontoprovisión;
}
public Integer getIntCacrInteressuspenso() {
	return intCacrInteressuspenso;
}
public void setIntCacrInteressuspenso(Integer intCacrInteressuspenso) {
	this.intCacrInteressuspenso = intCacrInteressuspenso;
}
public Integer getIntPersEmpresa() {
	return intPersEmpresa;
}
public void setIntPersEmpresa(Integer intPersEmpresa) {
	this.intPersEmpresa = intPersEmpresa;
}
public Integer getIntCsocCuenta() {
	return intCsocCuenta;
}
public void setIntCsocCuenta(Integer intCsocCuenta) {
	this.intCsocCuenta = intCsocCuenta;
}
public Integer getIntCserItemexpediente() {
	return intCserItemexpediente;
}
public void setIntCserItemexpediente(Integer intCserItemexpediente) {
	this.intCserItemexpediente = intCserItemexpediente;
}
public Integer getIntCserItemdetexpediente() {
	return intCserItemdetexpediente;
}
public void setIntCserItemdetexpediente(Integer intCserItemdetexpediente) {
	this.intCserItemdetexpediente = intCserItemdetexpediente;
}
public Integer getIntCmovItemcuentaconcepto() {
	return intCmovItemcuentaconcepto;
}
public void setIntCmovItemcuentaconcepto(Integer intCmovItemcuentaconcepto) {
	this.intCmovItemcuentaconcepto = intCmovItemcuentaconcepto;
}
public Integer getIntSucuIdsucursal() {
	return intSucuIdsucursal;
}
public void setIntSucuIdsucursal(Integer intSucuIdsucursal) {
	this.intSucuIdsucursal = intSucuIdsucursal;
}
public Integer getIntSudeIdsubsucursal() {
	return intSudeIdsubsucursal;
}
public void setIntSudeIdsubsucursal(Integer intSudeIdsubsucursal) {
	this.intSudeIdsubsucursal = intSudeIdsubsucursal;
}

   
}
