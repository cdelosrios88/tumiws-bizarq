package pe.com.tumi.credito.socio.convenio.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PerfilDetalle extends TumiDomain {

	private PerfilDetalleId id;
	private Perfil perfil;
	private PerfilValidacion perfilValidacion;
	private Integer intValor;
	
	public PerfilDetalleId getId() {
		return id;
	}
	public void setId(PerfilDetalleId id) {
		this.id = id;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public PerfilValidacion getPerfilValidacion() {
		return perfilValidacion;
	}
	public void setPerfilValidacion(PerfilValidacion perfilValidacion) {
		this.perfilValidacion = perfilValidacion;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}

}
