package pe.com.tumi.servicio.configuracion.domain;

import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServEstructuraDetalle extends TumiDomain{

	private ConfServEstructuraDetalleId id;
	private ConfServSolicitud solicitud;
	private Integer intCodigoPk;
	private Integer intNivelPk;
	private Integer intParaEstadoCod;
	private Integer intCaso;
	private Integer intItemCaso;
	private Estructura estructura;
	
	//para interfaz
	private Integer intTipoModalidad;
	private Integer intTipoSocio;
	private String strRazonSocial;
	
	public ConfServEstructuraDetalle(){
		id = new ConfServEstructuraDetalleId();
		estructura = new Estructura();
	}
	
	public ConfServEstructuraDetalleId getId() {
		return id;
	}
	public void setId(ConfServEstructuraDetalleId id) {
		this.id = id;
	}
	public ConfServSolicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(ConfServSolicitud solicitud) {
		this.solicitud = solicitud;
	}
	public Integer getIntCodigoPk() {
		return intCodigoPk;
	}
	public void setIntCodigoPk(Integer intCodigoPk) {
		this.intCodigoPk = intCodigoPk;
	}
	public Integer getIntNivelPk() {
		return intNivelPk;
	}
	public void setIntNivelPk(Integer intNivelPk) {
		this.intNivelPk = intNivelPk;
	}
	public Estructura getEstructura() {
		return estructura;
	}

	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}

	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntCaso() {
		return intCaso;
	}
	public void setIntCaso(Integer intCaso) {
		this.intCaso = intCaso;
	}
	public Integer getIntItemCaso() {
		return intItemCaso;
	}
	public void setIntItemCaso(Integer intItemCaso) {
		this.intItemCaso = intItemCaso;
	}
	public Integer getIntTipoModalidad() {
		return intTipoModalidad;
	}
	public void setIntTipoModalidad(Integer intTipoModalidad) {
		this.intTipoModalidad = intTipoModalidad;
	}
	public Integer getIntTipoSocio() {
		return intTipoSocio;
	}
	public void setIntTipoSocio(Integer intTipoSocio) {
		this.intTipoSocio = intTipoSocio;
	}
	public String getStrRazonSocial() {
		return strRazonSocial;
	}
	public void setStrRazonSocial(String strRazonSocial) {
		this.strRazonSocial = strRazonSocial;
	}

	@Override
	public String toString() {
		return "ConfServEstructuraDetalle [id=" + id + ", solicitud="
				+ solicitud + ", intCodigoPk=" + intCodigoPk + ", intNivelPk="
				+ intNivelPk + ", intParaEstadoCod=" + intParaEstadoCod
				+ ", intCaso=" + intCaso + ", intItemCaso=" + intItemCaso
				+ ", estructura=" + estructura + "]";
	}

}