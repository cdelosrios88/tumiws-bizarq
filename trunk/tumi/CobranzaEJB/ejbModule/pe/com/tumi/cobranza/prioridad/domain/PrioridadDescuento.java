package pe.com.tumi.cobranza.prioridad.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.ws.dcs.vri.common.impl.TimeScaler;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class PrioridadDescuento extends TumiDomain{
	
	PrioridadDescuentoId id; 
	Integer	  intParaTipoconceptogeneral;
	Integer	  intPrdeOrdenprioridad;
	Integer	  intParaEstado;
	Integer	  intParaTipomovimiento;
	Integer	  intParaTipocaptacion;
	Integer	  intcsocItem;
	Integer	  intParaTipoCredito;
	Integer	  intCsocItemCredito;
	Integer	  intParaTipoCtacte;
	Integer	  intCsocItemCtacte;
	Timestamp tsPrdeFechainicio;
	Timestamp tsPrdeFechaFin;
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 13-09-2013 
	private String strDescripcion;
	private BigDecimal bdMontoDiferencia;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 16-09-2013 (GUARDO LA PRIORIDAD CON EL EXPEDIENTE O CUENTACONCEPTO ORIGEN) 
	private Integer intItemcuentaconcepto;
 	private Integer intItemexpediente;
 	private Integer intItemdetexpediente;
 	private Integer intTipoconceptogeneralCod;
	
	public PrioridadDescuentoId getId() {
		return id;
	}
	public void setId(PrioridadDescuentoId id) {
		this.id = id;
	}
	public Integer getIntParaTipoconceptogeneral() {
		return intParaTipoconceptogeneral;
	}
	public void setIntParaTipoconceptogeneral(Integer intParaTipoconceptogeneral) {
		this.intParaTipoconceptogeneral = intParaTipoconceptogeneral;
	}
	public Integer getIntPrdeOrdenprioridad() {
		return intPrdeOrdenprioridad;
	}
	public void setIntPrdeOrdenprioridad(Integer intPrdeOrdenprioridad) {
		this.intPrdeOrdenprioridad = intPrdeOrdenprioridad;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntParaTipomovimiento() {
		return intParaTipomovimiento;
	}
	public void setIntParaTipomovimiento(Integer intParaTipomovimiento) {
		this.intParaTipomovimiento = intParaTipomovimiento;
	}
	public Integer getIntParaTipocaptacion() {
		return intParaTipocaptacion;
	}
	public void setIntParaTipocaptacion(Integer intParaTipocaptacion) {
		this.intParaTipocaptacion = intParaTipocaptacion;
	}
	public Integer getIntcsocItem() {
		return intcsocItem;
	}
	public void setIntcsocItem(Integer intcsocItem) {
		this.intcsocItem = intcsocItem;
	}
	public Integer getIntParaTipoCredito() {
		return intParaTipoCredito;
	}
	public void setIntParaTipoCredito(Integer intParaTipoCredito) {
		this.intParaTipoCredito = intParaTipoCredito;
	}
	public Integer getIntCsocItemCredito() {
		return intCsocItemCredito;
	}
	public void setIntCsocItemCredito(Integer intCsocItemCredito) {
		this.intCsocItemCredito = intCsocItemCredito;
	}
	public Integer getIntParaTipoCtacte() {
		return intParaTipoCtacte;
	}
	public void setIntParaTipoCtacte(Integer intParaTipoCtacte) {
		this.intParaTipoCtacte = intParaTipoCtacte;
	}
	public Integer getIntCsocItemCtacte() {
		return intCsocItemCtacte;
	}
	public void setIntCsocItemCtacte(Integer intCsocItemCtacte) {
		this.intCsocItemCtacte = intCsocItemCtacte;
	}
	public Timestamp getTsPrdeFechainicio() {
		return tsPrdeFechainicio;
	}
	public void setTsPrdeFechainicio(Timestamp tsPrdeFechainicio) {
		this.tsPrdeFechainicio = tsPrdeFechainicio;
	}
	public Timestamp getTsPrdeFechaFin() {
		return tsPrdeFechaFin;
	}
	public void setTsPrdeFechaFin(Timestamp tsPrdeFechaFin) {
		this.tsPrdeFechaFin = tsPrdeFechaFin;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public BigDecimal getBdMontoDiferencia() {
		return bdMontoDiferencia;
	}
	public void setBdMontoDiferencia(BigDecimal bdMontoDiferencia) {
		this.bdMontoDiferencia = bdMontoDiferencia;
	}
	public Integer getIntItemcuentaconcepto() {
		return intItemcuentaconcepto;
	}
	public void setIntItemcuentaconcepto(Integer intItemcuentaconcepto) {
		this.intItemcuentaconcepto = intItemcuentaconcepto;
	}
	public Integer getIntItemexpediente() {
		return intItemexpediente;
	}
	public void setIntItemexpediente(Integer intItemexpediente) {
		this.intItemexpediente = intItemexpediente;
	}
	public Integer getIntItemdetexpediente() {
		return intItemdetexpediente;
	}
	public void setIntItemdetexpediente(Integer intItemdetexpediente) {
		this.intItemdetexpediente = intItemdetexpediente;
	}
	public Integer getIntTipoconceptogeneralCod() {
		return intTipoconceptogeneralCod;
	}
	public void setIntTipoconceptogeneralCod(Integer intTipoconceptogeneralCod) {
		this.intTipoconceptogeneralCod = intTipoconceptogeneralCod;
	}

}
