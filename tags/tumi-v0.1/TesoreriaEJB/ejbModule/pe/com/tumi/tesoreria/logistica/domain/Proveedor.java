package pe.com.tumi.tesoreria.logistica.domain;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class Proveedor extends TumiDomain{

	private	ProveedorId	id;
	private	Integer		intRetencionCuarta;
	
	private List<ProveedorDetalle>	listaProveedorDetalle;
	private	Persona		persona;
	
	private String	strListaProveedorDetalle;
	
	public Proveedor(){
		id = new ProveedorId();
		listaProveedorDetalle = new ArrayList<ProveedorDetalle>();
	}
	
	public ProveedorId getId() {
		return id;
	}
	public void setId(ProveedorId id) {
		this.id = id;
	}
	public Integer getIntRetencionCuarta() {
		return intRetencionCuarta;
	}
	public void setIntRetencionCuarta(Integer intRetencionCuarta) {
		this.intRetencionCuarta = intRetencionCuarta;
	}
	public List<ProveedorDetalle> getListaProveedorDetalle() {
		return listaProveedorDetalle;
	}
	public void setListaProveedorDetalle(List<ProveedorDetalle> listaProveedorDetalle) {
		this.listaProveedorDetalle = listaProveedorDetalle;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getStrListaProveedorDetalle() {
		return strListaProveedorDetalle;
	}
	public void setStrListaProveedorDetalle(String strListaProveedorDetalle) {
		this.strListaProveedorDetalle = strListaProveedorDetalle;
	}
	

	@Override
	public String toString() {
		return "Proveedor [id=" + id + ", intRetencionCuarta="
				+ intRetencionCuarta + "]";
	}
	
}
