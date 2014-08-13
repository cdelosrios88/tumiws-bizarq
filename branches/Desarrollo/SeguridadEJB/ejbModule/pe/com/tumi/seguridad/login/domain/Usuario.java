package pe.com.tumi.seguridad.login.domain;

import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.empresa.domain.Empresa;

public class Usuario extends TumiDomain {

	private Integer intPersPersonaPk;
	private Integer intTipoUsuario;
	private String strUsuario;
	private String strContrasena;
	private Timestamp dtFechaRegistro;
	private Integer intIdEstado;
	//private String strArchivo;
	private Timestamp tsFechaEliminacion;
	private List<EmpresaUsuario> listaEmpresaUsuario;
	
	private Persona persona;
	private String strContrasenaValidacion;
	private Sucursal sucursal;
	private Subsucursal subSucursal;
	private Perfil perfil;
	private EmpresaUsuario empresaUsuario;
	private Empresa empresa;
	
	//Inicio: REQ14-003 - bizarq - 11/08/2014
	private Session objSession;
	//Inicio: REQ14-003 - bizarq - 11/08/2014
	
	public Integer getIntPersPersonaPk() {
		return intPersPersonaPk;
	}
	public void setIntPersPersonaPk(Integer intPersPersonaPk) {
		this.intPersPersonaPk = intPersPersonaPk;
	}
	public Integer getIntTipoUsuario() {
		return intTipoUsuario;
	}
	public void setIntTipoUsuario(Integer intTipoUsuario) {
		this.intTipoUsuario = intTipoUsuario;
	}
	public String getStrUsuario() {
		return strUsuario;
	}
	public void setStrUsuario(String strUsuario) {
		this.strUsuario = strUsuario;
	}
	public String getStrContrasena() {
		return strContrasena;
	}
	public void setStrContrasena(String strContrasena) {
		this.strContrasena = strContrasena;
	}
	public Timestamp getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Timestamp dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	/*public String getStrArchivo() {
		return strArchivo;
	}
	public void setStrArchivo(String strArchivo) {
		this.strArchivo = strArchivo;
	}*/
	public List<EmpresaUsuario> getListaEmpresaUsuario() {
		return listaEmpresaUsuario;
	}
	public void setListaEmpresaUsuario(List<EmpresaUsuario> listaEmpresaUsuario) {
		this.listaEmpresaUsuario = listaEmpresaUsuario;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getStrContrasenaValidacion() {
		return strContrasenaValidacion;
	}
	public void setStrContrasenaValidacion(String strContrasenaValidacion) {
		this.strContrasenaValidacion = strContrasenaValidacion;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Subsucursal getSubSucursal() {
		return subSucursal;
	}
	public void setSubSucursal(Subsucursal subSucursal) {
		this.subSucursal = subSucursal;
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
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	//Inicio: REQ14-003 - bizarq - 11/08/2014
	/**
	 * @return the objSession
	 */
	public Session getObjSession() {
		return objSession;
	}
	/**
	 * @param objSession the objSession to set
	 */
	public void setObjSession(Session objSession) {
		this.objSession = objSession;
	}
	//Fin: REQ14-003 - bizarq - 11/08/2014
	
}
