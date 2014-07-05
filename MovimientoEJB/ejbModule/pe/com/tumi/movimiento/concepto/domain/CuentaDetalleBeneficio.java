package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

public class CuentaDetalleBeneficio extends TumiDomain{

	private CuentaDetalleBeneficioId id;
	private CuentaConcepto cuentaConcepto;
	private Integer intItemVinculo;
	private Integer paraEstadoCod;
	private BigDecimal bdPorcentaje;
	private Integer intParaTipoConceptoCod;
	private Integer intItemConcepto;
	private Integer persPersonausuarioPk;
	private Timestamp tsFecharegistro;
	private Integer intPersPersonaEliminaPk;
	private Timestamp tsFechaEliminacion;
	
	private Vinculo vinculo;
	private Persona persona;
	
	public CuentaDetalleBeneficioId getId() {
		return id;
	}
	public void setId(CuentaDetalleBeneficioId id) {
		this.id = id;
	}
	public CuentaConcepto getCuentaConcepto() {
		return cuentaConcepto;
	}
	public void setCuentaConcepto(CuentaConcepto cuentaConcepto) {
		this.cuentaConcepto = cuentaConcepto;
	}
	public Integer getIntItemVinculo() {
		return intItemVinculo;
	}
	public void setIntItemVinculo(Integer intItemVinculo) {
		this.intItemVinculo = intItemVinculo;
	}
	public Integer getParaEstadoCod() {
		return paraEstadoCod;
	}
	public void setParaEstadoCod(Integer paraEstadoCod) {
		this.paraEstadoCod = paraEstadoCod;
	}
	public BigDecimal getBdPorcentaje() {
		return bdPorcentaje;
	}
	public void setBdPorcentaje(BigDecimal bdPorcentaje) {
		this.bdPorcentaje = bdPorcentaje;
	}
	public Integer getIntParaTipoConceptoCod() {
		return intParaTipoConceptoCod;
	}
	public void setIntParaTipoConceptoCod(Integer intParaTipoConceptoCod) {
		this.intParaTipoConceptoCod = intParaTipoConceptoCod;
	}
	public Integer getIntItemConcepto() {
		return intItemConcepto;
	}
	public void setIntItemConcepto(Integer intItemConcepto) {
		this.intItemConcepto = intItemConcepto;
	}
	public Integer getPersPersonausuarioPk() {
		return persPersonausuarioPk;
	}
	public void setPersPersonausuarioPk(Integer persPersonausuarioPk) {
		this.persPersonausuarioPk = persPersonausuarioPk;
	}
	public Timestamp getTsFecharegistro() {
		return tsFecharegistro;
	}
	public void setTsFecharegistro(Timestamp tsFecharegistro) {
		this.tsFecharegistro = tsFecharegistro;
	}
	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Vinculo getVinculo() {
		return vinculo;
	}
	public void setVinculo(Vinculo vinculo) {
		this.vinculo = vinculo;
	}
}