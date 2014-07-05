package pe.com.tumi.persona.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaBancariaFin extends TumiDomain{

	private	CuentaBancariaFinId id;
	
	public CuentaBancariaFin(){
		id = new CuentaBancariaFinId();
	}
	
	public CuentaBancariaFinId getId() {
		return id;
	}

	public void setId(CuentaBancariaFinId id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "CuentaBancariaFin [id=" + id + "]";
	}
}
