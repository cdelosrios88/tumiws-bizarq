package pe.com.tumi.seguridad.login.domain.composite;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class UsuarioComp extends TumiDomain {
	
	private Usuario usuario;
	private EmpresaUsuario empresaUsuario;
	private Integer intIdPerfil;
	private Integer intIdTipoSucursal;
	private String strPerfil;
	private String strSucursal;
	private List<Perfil> listaPerfil;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public EmpresaUsuario getEmpresaUsuario() {
		return empresaUsuario;
	}
	public void setEmpresaUsuario(EmpresaUsuario empresaUsuario) {
		this.empresaUsuario = empresaUsuario;
	}
	public Integer getIntIdPerfil() {
		return intIdPerfil;
	}
	public void setIntIdPerfil(Integer intIdPerfil) {
		this.intIdPerfil = intIdPerfil;
	}
	public Integer getIntIdTipoSucursal() {
		return intIdTipoSucursal;
	}
	public void setIntIdTipoSucursal(Integer intIdTipoSucursal) {
		this.intIdTipoSucursal = intIdTipoSucursal;
	}
	public String getStrPerfil() {
		return strPerfil;
	}
	public void setStrPerfil(String strPerfil) {
		this.strPerfil = strPerfil;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}
	
}
