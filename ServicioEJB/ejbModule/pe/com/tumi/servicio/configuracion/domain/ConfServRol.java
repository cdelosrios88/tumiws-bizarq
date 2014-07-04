package pe.com.tumi.servicio.configuracion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServRol extends TumiDomain{

	private ConfServRolId id;
	private ConfServSolicitud solicitud;
	private Integer intValor;
	
	public ConfServRol(){
		id = new ConfServRolId();
	}
	public ConfServRolId getId() {
		return id;
	}
	public void setId(ConfServRolId id) {
		this.id = id;
	}
	public ConfServSolicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(ConfServSolicitud solicitud) {
		this.solicitud = solicitud;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
	@Override
	public String toString() {
		return "ConfServRol [id=" + id + ", solicitud=" + solicitud
				+ ", intValor=" + intValor + "]";
	}

}
