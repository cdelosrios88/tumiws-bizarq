package pe.com.tumi.empresa.domain.composite;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class SucursalComp extends TumiDomain{
	private Sucursal sucursal;
	private Integer	 intCantidadSubSucursal;
	private Empresa  empresa;
	
	public Integer getIntCantidadSubSucursal() {
		return intCantidadSubSucursal;
	}
	public void setIntCantidadSubSucursal(Integer intCantidadSubSucursal) {
		this.intCantidadSubSucursal = intCantidadSubSucursal;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}