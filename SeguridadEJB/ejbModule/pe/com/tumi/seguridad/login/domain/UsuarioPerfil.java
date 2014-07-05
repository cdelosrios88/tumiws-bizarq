package pe.com.tumi.seguridad.login.domain;

import java.sql.Timestamp;
import java.util.Date;

import java.util.List;

import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoUsuario;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class UsuarioPerfil extends TumiDomain {

	private UsuarioPerfilId id;
	private Perfil perfil;
	private EmpresaUsuario empresaUsuario;
	private Date dtHasta;
	private Timestamp dtFechaRegistro;
	private Integer intIdEstado;
	private Timestamp tsFechaEliminacion;
	private Integer intPersPersonaUsuarioPk;
	private Integer intPersPersonaEliminaPk;
	private List<PermisoUsuario> listaPermiso;
	
	private Boolean blnIndeterminado;
	
	public UsuarioPerfilId getId() {
		return id;
	}
	public void setId(UsuarioPerfilId id) {
		this.id = id;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public EmpresaUsuario getEmpresaUsuario() {
		return empresaUsuario;
	}
	public void setEmpresaUsuario(EmpresaUsuario empresaUsuario) {
		this.empresaUsuario = empresaUsuario;
	}
	public Date getDtHasta() {
		return dtHasta;
	}
	public void setDtHasta(Date dtHasta) {
		this.dtHasta = dtHasta;
	}
	public Timestamp getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Timestamp dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public List<PermisoUsuario> getListaPermiso() {
		return listaPermiso;
	}
	public void setListaPermiso(List<PermisoUsuario> listaPermiso) {
		this.listaPermiso = listaPermiso;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Boolean getBlnIndeterminado() {
		return blnIndeterminado;
	}
	public void setBlnIndeterminado(Boolean blnIndeterminado) {
		this.blnIndeterminado = blnIndeterminado;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}
}
