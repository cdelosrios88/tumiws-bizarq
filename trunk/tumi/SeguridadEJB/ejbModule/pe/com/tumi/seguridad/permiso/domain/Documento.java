package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;

public class Documento extends TumiDomain {

	private DocumentoId id;
	private EmpresaUsuario empresaUsuario;
	private PermisoPerfil permisoPerfil;
	//private String strArchivo;
	private Timestamp tsFechaRegistro;
	private Integer intIdEstado;
	private Timestamp tsFechaEliminacion;
	public DocumentoId getId() {
		return id;
	}
	public void setId(DocumentoId id) {
		this.id = id;
	}
	public EmpresaUsuario getEmpresaUsuario() {
		return empresaUsuario;
	}
	public void setEmpresaUsuario(EmpresaUsuario empresaUsuario) {
		this.empresaUsuario = empresaUsuario;
	}
	public PermisoPerfil getPermisoPerfil() {
		return permisoPerfil;
	}
	public void setPermisoPerfil(PermisoPerfil permisoPerfil) {
		this.permisoPerfil = permisoPerfil;
	}
	/*public String getStrArchivo() {
		return strArchivo;
	}
	public void setStrArchivo(String strArchivo) {
		this.strArchivo = strArchivo;
	}*/
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}

}
