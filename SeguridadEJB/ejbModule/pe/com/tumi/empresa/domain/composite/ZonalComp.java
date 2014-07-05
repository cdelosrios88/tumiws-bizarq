package pe.com.tumi.empresa.domain.composite;

import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class ZonalComp extends TumiDomain {

	private Zonal zonal;
	private Integer intCantidadSucursal;
	private Juridica empresa;
	
	public Zonal getZonal() {
		return zonal;
	}
	public void setZonal(Zonal zonal) {
		this.zonal = zonal;
	}
	public Integer getIntCantidadSucursal() {
		return intCantidadSucursal;
	}
	public void setIntCantidadSucursal(Integer intCantidadSucursal) {
		this.intCantidadSucursal = intCantidadSucursal;
	}
	public Juridica getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Juridica empresa) {
		this.empresa = empresa;
	}
}
