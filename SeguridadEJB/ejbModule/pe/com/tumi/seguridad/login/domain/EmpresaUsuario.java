package pe.com.tumi.seguridad.login.domain;

import java.sql.Timestamp;
import java.util.List;

/*import pe.com.tumi.empresa.domain.AccesoEspecial;
import pe.com.tumi.empresa.domain.Documento;
import pe.com.tumi.empresa.domain.Mof;
import pe.com.tumi.empresa.domain.Session;*/
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class EmpresaUsuario extends TumiDomain {

	private EmpresaUsuarioId id;
	private Usuario usuario;
	private Integer intControlHoraIngreso;
	private Integer intCambioClave;
	private Integer intControlVigenciaClave;
	private Integer intDiasVigencia;
	private Integer intIdEstado;
	private Timestamp tsFechaEliminacion;
	private Integer intPersPersonaEliminaPk;
	private List<UsuarioSubSucursal> listaUsuarioSubSucursal;
	private List<UsuarioSucursal> listaUsuarioSucursal;
	/*private List<Documento> listaDocumento;
	private List<Mof> listaMof;
	private List<AccesoEspecial> listaAccesoEspecial;
	private List<AccesoEspecial> listaAccesoEspecialAutoriza;
	private List<Session> listaSession;*/
	private List<UsuarioPerfil> listaUsuarioPerfil;
	private List<Sucursal> listaSucursal;
	private List<Perfil> listaPerfil;

	private Boolean blnControlHoraIngreso;
	private Boolean blnCambioClave;
	private Boolean blnControlVigenciaClave;
	private Integer intRadioControlVigenciaClave;
	private Juridica juridica;
	
	public EmpresaUsuarioId getId() {
		return id;
	}
	public void setId(EmpresaUsuarioId id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<UsuarioSubSucursal> getListaUsuarioSubSucursal() {
		return listaUsuarioSubSucursal;
	}
	public void setListaUsuarioSubSucursal(
			List<UsuarioSubSucursal> listaUsuarioSubSucursal) {
		this.listaUsuarioSubSucursal = listaUsuarioSubSucursal;
	}
	public List<UsuarioSucursal> getListaUsuarioSucursal() {
		return listaUsuarioSucursal;
	}
	public void setListaUsuarioSucursal(List<UsuarioSucursal> listaUsuarioSucursal) {
		this.listaUsuarioSucursal = listaUsuarioSucursal;
	}
	public List<UsuarioPerfil> getListaUsuarioPerfil() {
		return listaUsuarioPerfil;
	}
	public void setListaUsuarioPerfil(List<UsuarioPerfil> listaUsuarioPerfil) {
		this.listaUsuarioPerfil = listaUsuarioPerfil;
	}
	public Juridica getJuridica() {
		return juridica;
	}
	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Integer getIntControlHoraIngreso() {
		return intControlHoraIngreso;
	}
	public void setIntControlHoraIngreso(Integer intControlHoraIngreso) {
		this.intControlHoraIngreso = intControlHoraIngreso;
	}
	public Integer getIntCambioClave() {
		return intCambioClave;
	}
	public void setIntCambioClave(Integer intCambioClave) {
		this.intCambioClave = intCambioClave;
	}
	public Integer getIntControlVigenciaClave() {
		return intControlVigenciaClave;
	}
	public void setIntControlVigenciaClave(Integer intControlVigenciaClave) {
		this.intControlVigenciaClave = intControlVigenciaClave;
	}
	public Integer getIntDiasVigencia() {
		return intDiasVigencia;
	}
	public void setIntDiasVigencia(Integer intDiasVigencia) {
		this.intDiasVigencia = intDiasVigencia;
	}
	public Boolean getBlnControlHoraIngreso() {
		return blnControlHoraIngreso;
	}
	public void setBlnControlHoraIngreso(Boolean blnControlHoraIngreso) {
		this.blnControlHoraIngreso = blnControlHoraIngreso;
	}
	public Boolean getBlnCambioClave() {
		return blnCambioClave;
	}
	public void setBlnCambioClave(Boolean blnCambioClave) {
		this.blnCambioClave = blnCambioClave;
	}
	public Boolean getBlnControlVigenciaClave() {
		return blnControlVigenciaClave;
	}
	public void setBlnControlVigenciaClave(Boolean blnControlVigenciaClave) {
		this.blnControlVigenciaClave = blnControlVigenciaClave;
	}
	public Integer getIntRadioControlVigenciaClave() {
		return intRadioControlVigenciaClave;
	}
	public void setIntRadioControlVigenciaClave(Integer intRadioControlVigenciaClave) {
		this.intRadioControlVigenciaClave = intRadioControlVigenciaClave;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}
	
}
