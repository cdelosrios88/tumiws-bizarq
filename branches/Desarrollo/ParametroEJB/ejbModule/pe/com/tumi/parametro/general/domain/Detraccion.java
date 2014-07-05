package pe.com.tumi.parametro.general.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Detraccion extends TumiDomain{

	private Integer		intParaTipoDetraccion;
	private String		strDescripcion;
	private BigDecimal	bdPorcentaje;
	
	public Integer getIntParaTipoDetraccion() {
		return intParaTipoDetraccion;
	}
	public void setIntParaTipoDetraccion(Integer intParaTipoDetraccion) {
		this.intParaTipoDetraccion = intParaTipoDetraccion;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public BigDecimal getBdPorcentaje() {
		return bdPorcentaje;
	}
	public void setBdPorcentaje(BigDecimal bdPorcentaje) {
		this.bdPorcentaje = bdPorcentaje;
	}
	
	@Override
	public String toString() {
		return "Detraccion [intParaTipoDetraccion=" + intParaTipoDetraccion
				+ ", strDescripcion=" + strDescripcion + ", bdPorcentaje="
				+ bdPorcentaje + "]";
	}
}
