package pe.com.tumi.servicio.configuracion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServCreditoEmpresa extends TumiDomain{

	private ConfServCreditoEmpresaId id;
	private ConfServSolicitud solicitud;
	private Integer intValor;
	
	public ConfServCreditoEmpresa(){
		id = new ConfServCreditoEmpresaId();
	}
	
	public ConfServCreditoEmpresaId getId() {
		return id;
	}
	public void setId(ConfServCreditoEmpresaId id) {
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
		return "ConfServCreditoEmpresa [id=" + id + ", solicitud=" + solicitud
				+ ", intValor=" + intValor + "]";
	}

}
