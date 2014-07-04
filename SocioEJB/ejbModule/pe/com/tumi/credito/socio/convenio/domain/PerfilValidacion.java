package pe.com.tumi.credito.socio.convenio.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;


public class PerfilValidacion extends TumiDomain {

	private PerfilValidacionId id;
	private Integer intParaEstadoCod;
	private List<PerfilDetalle> listaPerfilDetalle;
	
	public PerfilValidacionId getId() {
		return id;
	}
	public void setId(PerfilValidacionId id) {
		this.id = id;
	}
	public List<PerfilDetalle> getListaPerfilDetalle() {
		return listaPerfilDetalle;
	}
	public void setListaPerfilDetalle(List<PerfilDetalle> listaPerfilDetalle) {
		this.listaPerfilDetalle = listaPerfilDetalle;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
}
