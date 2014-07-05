package pe.com.tumi.servicio.configuracion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServGrupoCta extends TumiDomain{

	private ConfServGrupoCtaId id;
	private ConfServSolicitud solicitud;
	private Integer intParaSubTipoCtaCod;
	private Integer intParaTipoConformacionCod;
	private Integer intParaTipoMonedaCod;
	private Integer intValor;
	
	public ConfServGrupoCta(){
		id = new ConfServGrupoCtaId();
	}
	
	public ConfServGrupoCtaId getId() {
		return id;
	}
	public void setId(ConfServGrupoCtaId id) {
		this.id = id;
	}
	public ConfServSolicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(ConfServSolicitud solicitud) {
		this.solicitud = solicitud;
	}
	public Integer getIntParaSubTipoCtaCod() {
		return intParaSubTipoCtaCod;
	}
	public void setIntParaSubTipoCtaCod(Integer intParaSubTipoCtaCod) {
		this.intParaSubTipoCtaCod = intParaSubTipoCtaCod;
	}
	public Integer getIntParaTipoConformacionCod() {
		return intParaTipoConformacionCod;
	}
	public void setIntParaTipoConformacionCod(Integer intParaTipoConformacionCod) {
		this.intParaTipoConformacionCod = intParaTipoConformacionCod;
	}
	public Integer getIntParaTipoMonedaCod() {
		return intParaTipoMonedaCod;
	}
	public void setIntParaTipoMonedaCod(Integer intParaTipoMonedaCod) {
		this.intParaTipoMonedaCod = intParaTipoMonedaCod;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
	@Override
	public String toString() {
		return "ConfServGrupoCta [id=" + id + ", solicitud=" + solicitud
				+ ", intParaSubTipoCtaCod=" + intParaSubTipoCtaCod
				+ ", intParaTipoConformacionCod=" + intParaTipoConformacionCod
				+ ", intParaTipoMonedaCod=" + intParaTipoMonedaCod
				+ ", intValor=" + intValor + "]";
	}

}
