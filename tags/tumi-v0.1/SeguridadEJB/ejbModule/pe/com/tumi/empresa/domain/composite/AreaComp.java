package pe.com.tumi.empresa.domain.composite;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class AreaComp extends TumiDomain{
	private Area area;
	private Empresa empresa;
	private Sucursal sucursal;
	private Integer	 intCantidadSubSucursal;
	
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Integer getIntCantidadSubSucursal() {
		return intCantidadSubSucursal;
	}
	public void setIntCantidadSubSucursal(Integer intCantidadSubSucursal) {
		this.intCantidadSubSucursal = intCantidadSubSucursal;
	}
}
