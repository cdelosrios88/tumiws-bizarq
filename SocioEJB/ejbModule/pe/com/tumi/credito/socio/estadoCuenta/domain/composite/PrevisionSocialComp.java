package pe.com.tumi.credito.socio.estadoCuenta.domain.composite;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePagoId;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPagoId;

public class PrevisionSocialComp extends TumiDomain {
	private Integer intPeriodo;
	private String strPeriodo;
	private Integer intNumeroCuotas;
	private BigDecimal bdMontoCuotas;
	private String strMontoCuotas;
	private BigDecimal bdProvisionado;
	private BigDecimal bdCancelado;
	private BigDecimal bdPendiente;
	private BigDecimal bdAcumulado;
	/*
	private String strProvisionado;
	private String strCancelado;
	private String strPendiente;
	private String strAcumulado;*/
	private Integer intParaTipoConceptoCod;
	private List<ConceptoPagoId> listaConceptoPagoId;
	private List<ConceptoPago> listaConceptoPago;
	
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	public String getStrPeriodo() {
		return strPeriodo;
	}
	public void setStrPeriodo(String strPeriodo) {
		this.strPeriodo = strPeriodo;
	}
	public Integer getIntNumeroCuotas() {
		return intNumeroCuotas;
	}
	public void setIntNumeroCuotas(Integer intNumeroCuotas) {
		this.intNumeroCuotas = intNumeroCuotas;
	}
	public BigDecimal getBdMontoCuotas() {
		return bdMontoCuotas;
	}
	public void setBdMontoCuotas(BigDecimal bdMontoCuotas) {
		this.bdMontoCuotas = bdMontoCuotas;
	}
	public String getStrMontoCuotas() {
		return strMontoCuotas;
	}
	public void setStrMontoCuotas(String strMontoCuotas) {
		this.strMontoCuotas = strMontoCuotas;
	}
	public BigDecimal getBdProvisionado() {
		return bdProvisionado;
	}
	public void setBdProvisionado(BigDecimal bdProvisionado) {
		this.bdProvisionado = bdProvisionado;
	}
	public BigDecimal getBdCancelado() {
		return bdCancelado;
	}
	public void setBdCancelado(BigDecimal bdCancelado) {
		this.bdCancelado = bdCancelado;
	}
	public BigDecimal getBdPendiente() {
		return bdPendiente;
	}
	public void setBdPendiente(BigDecimal bdPendiente) {
		this.bdPendiente = bdPendiente;
	}
	public Integer getIntParaTipoConceptoCod() {
		return intParaTipoConceptoCod;
	}
	public void setIntParaTipoConceptoCod(Integer intParaTipoConceptoCod) {
		this.intParaTipoConceptoCod = intParaTipoConceptoCod;
	}
	public BigDecimal getBdAcumulado() {
		return bdAcumulado;
	}
	public void setBdAcumulado(BigDecimal bdAcumulado) {
		this.bdAcumulado = bdAcumulado;
	}
	public List<ConceptoPagoId> getListaConceptoPagoId() {
		return listaConceptoPagoId;
	}
	public void setListaConceptoPagoId(List<ConceptoPagoId> listaConceptoPagoId) {
		this.listaConceptoPagoId = listaConceptoPagoId;
	}
	public List<ConceptoPago> getListaConceptoPago() {
		return listaConceptoPago;
	}
	public void setListaConceptoPago(List<ConceptoPago> listaConceptoPago) {
		this.listaConceptoPago = listaConceptoPago;
	}
}
