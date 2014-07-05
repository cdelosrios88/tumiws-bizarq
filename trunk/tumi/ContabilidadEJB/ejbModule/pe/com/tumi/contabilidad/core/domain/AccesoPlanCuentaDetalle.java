package pe.com.tumi.contabilidad.core.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AccesoPlanCuentaDetalle extends TumiDomain{

	private AccesoPlanCuentaDetalleId	id;
	private BigDecimal bdMontoMaximo;
	private BigDecimal bdMontoMinimo;
	private Integer intParaPeriodoRestriccion;
	private Integer intParaOpcionDebeHaber;
	private Integer intParaEstado;
	
	public AccesoPlanCuentaDetalle(){
		id = new AccesoPlanCuentaDetalleId();
	}

	public AccesoPlanCuentaDetalleId getId() {
		return id;
	}
	public void setId(AccesoPlanCuentaDetalleId id) {
		this.id = id;
	}
	public BigDecimal getBdMontoMaximo() {
		return bdMontoMaximo;
	}
	public void setBdMontoMaximo(BigDecimal bdMontoMaximo) {
		this.bdMontoMaximo = bdMontoMaximo;
	}
	public BigDecimal getBdMontoMinimo() {
		return bdMontoMinimo;
	}
	public void setBdMontoMinimo(BigDecimal bdMontoMinimo) {
		this.bdMontoMinimo = bdMontoMinimo;
	}
	public Integer getIntParaPeriodoRestriccion() {
		return intParaPeriodoRestriccion;
	}
	public void setIntParaPeriodoRestriccion(Integer intParaPeriodoRestriccion) {
		this.intParaPeriodoRestriccion = intParaPeriodoRestriccion;
	}
	public Integer getIntParaOpcionDebeHaber() {
		return intParaOpcionDebeHaber;
	}
	public void setIntParaOpcionDebeHaber(Integer intParaOpcionDebeHaber) {
		this.intParaOpcionDebeHaber = intParaOpcionDebeHaber;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}	
}