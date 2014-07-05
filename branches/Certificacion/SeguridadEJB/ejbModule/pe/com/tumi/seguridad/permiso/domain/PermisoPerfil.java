package pe.com.tumi.seguridad.permiso.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.seguridad.login.domain.Perfil;

public class PermisoPerfil extends TumiDomain {

	private PermisoPerfilId id;
	private Perfil perfil;
	private Transaccion transaccion;
	private Integer intIdEstado;
	private List<Documento> listaDocumento;
	
	public PermisoPerfilId getId() {
		return id;
	}
	public void setId(PermisoPerfilId id) {
		this.id = id;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Transaccion getTransaccion() {
		return transaccion;
	}
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}
	public List<Documento> getListaDocumento() {
		return listaDocumento;
	}
	public void setListaDocumento(List<Documento> listaDocumento) {
		this.listaDocumento = listaDocumento;
	}

}
