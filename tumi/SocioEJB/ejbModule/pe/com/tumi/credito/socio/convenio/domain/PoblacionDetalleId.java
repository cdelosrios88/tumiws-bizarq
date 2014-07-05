package pe.com.tumi.credito.socio.convenio.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PoblacionDetalleId extends TumiDomain {

	private Integer intItemPoblacion;
	private Integer intTipoTrabajador;
	private Integer intTipoSocio;
	
	public PoblacionDetalleId(){
		
	}
	
	public PoblacionDetalleId(Integer intItemPoblacion,
			Integer intTipoTrabajador, Integer intTipoSocio) {
		super();
		this.intItemPoblacion = intItemPoblacion;
		this.intTipoTrabajador = intTipoTrabajador;
		this.intTipoSocio = intTipoSocio;
	}
	
	public Integer getIntItemPoblacion() {
		return intItemPoblacion;
	}
	public void setIntItemPoblacion(Integer intItemPoblacion) {
		this.intItemPoblacion = intItemPoblacion;
	}
	public Integer getIntTipoTrabajador() {
		return intTipoTrabajador;
	}
	public void setIntTipoTrabajador(Integer intTipoTrabajador) {
		this.intTipoTrabajador = intTipoTrabajador;
	}
	public Integer getIntTipoSocio() {
		return intTipoSocio;
	}
	public void setIntTipoSocio(Integer intTipoSocio) {
		this.intTipoSocio = intTipoSocio;
	}

}
