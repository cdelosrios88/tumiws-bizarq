package pe.com.tumi.credito.socio.convenio.domain.composite;

import pe.com.tumi.credito.socio.convenio.domain.Perfil;
import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalle;
import pe.com.tumi.credito.socio.convenio.domain.PerfilValidacion;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class HojaControlComp extends TumiDomain {
	private Perfil perfil;
	private PerfilDetalle perfilDetalle;
	private PerfilValidacion perfilValidacion;
	private Boolean chkValor;
	
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public PerfilDetalle getPerfilDetalle() {
		return perfilDetalle;
	}
	public void setPerfilDetalle(PerfilDetalle perfilDetalle) {
		this.perfilDetalle = perfilDetalle;
	}
	public PerfilValidacion getPerfilValidacion() {
		return perfilValidacion;
	}
	public void setPerfilValidacion(PerfilValidacion perfilValidacion) {
		this.perfilValidacion = perfilValidacion;
	}
	public Boolean getChkValor() {
		return chkValor;
	}
	public void setChkValor(Boolean chkValor) {
		this.chkValor = chkValor;
	}
}