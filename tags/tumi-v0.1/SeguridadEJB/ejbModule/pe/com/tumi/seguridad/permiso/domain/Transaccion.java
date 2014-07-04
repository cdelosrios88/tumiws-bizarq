package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Transaccion extends TumiDomain {

	private TransaccionId id;
	private Transaccion transaccion;
	private String strDescripcion;
	private String strNombre;
	private Integer intNivel;
	private Integer intOrden;
	private Integer intTipoMenu;
	private Integer intCrecimiento;
	private Integer intTipoTransaccion;
	private Integer intFinal;
	private Integer intIdEstado;
	private Timestamp tsFechaEliminacion;
	private Integer intPersPersonaEliminaPk;
	private String strModulo;
	private String strPagina;
	private PermisoPerfil permisoPerfil;
	
	private List<Transaccion> listaTransaccion;
	private List<PermisoUsuario> listaPermisoUsuario;
	private List<SolicitudCambio> listaSolicitudCambio;
	private List<PermisoPerfil> listaPermisoPerfil;
	//private List<SessionDetalle> listaSessionDetalle;
	private List<Contenido> listaContenido;
	private List<Password> listaPassword;
	
	public TransaccionId getId() {
		return id;
	}
	public void setId(TransaccionId id) {
		this.id = id;
	}
	public Transaccion getTransaccion() {
		return transaccion;
	}
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public String getStrNombre() {
		return strNombre;
	}
	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}
	public Integer getIntNivel() {
		return intNivel;
	}
	public void setIntNivel(Integer intNivel) {
		this.intNivel = intNivel;
	}
	public Integer getIntOrden() {
		return intOrden;
	}
	public void setIntOrden(Integer intOrden) {
		this.intOrden = intOrden;
	}
	public Integer getIntTipoMenu() {
		return intTipoMenu;
	}
	public void setIntTipoMenu(Integer intTipoMenu) {
		this.intTipoMenu = intTipoMenu;
	}
	public Integer getIntCrecimiento() {
		return intCrecimiento;
	}
	public void setIntCrecimiento(Integer intCrecimiento) {
		this.intCrecimiento = intCrecimiento;
	}
	public Integer getIntTipoTransaccion() {
		return intTipoTransaccion;
	}
	public void setIntTipoTransaccion(Integer intTipoTransaccion) {
		this.intTipoTransaccion = intTipoTransaccion;
	}
	public Integer getIntFinal() {
		return intFinal;
	}
	public void setIntFinal(Integer intFinal) {
		this.intFinal = intFinal;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public List<Transaccion> getListaTransaccion() {
		return listaTransaccion;
	}
	public void setListaTransaccion(List<Transaccion> listaTransaccion) {
		this.listaTransaccion = listaTransaccion;
	}
	public List<PermisoUsuario> getListaPermisoUsuario() {
		return listaPermisoUsuario;
	}
	public void setListaPermisoUsuario(List<PermisoUsuario> listaPermisoUsuario) {
		this.listaPermisoUsuario = listaPermisoUsuario;
	}
	public List<SolicitudCambio> getListaSolicitudCambio() {
		return listaSolicitudCambio;
	}
	public void setListaSolicitudCambio(List<SolicitudCambio> listaSolicitudCambio) {
		this.listaSolicitudCambio = listaSolicitudCambio;
	}
	public List<PermisoPerfil> getListaPermisoPerfil() {
		return listaPermisoPerfil;
	}
	public void setListaPermisoPerfil(List<PermisoPerfil> listaPermisoPerfil) {
		this.listaPermisoPerfil = listaPermisoPerfil;
	}
	public List<Contenido> getListaContenido() {
		return listaContenido;
	}
	public void setListaContenido(List<Contenido> listaContenido) {
		this.listaContenido = listaContenido;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public List<Password> getListaPassword() {
		return listaPassword;
	}
	public void setListaPassword(List<Password> listaPassword) {
		this.listaPassword = listaPassword;
	}
	public PermisoPerfil getPermisoPerfil() {
		return permisoPerfil;
	}
	public void setPermisoPerfil(PermisoPerfil permisoPerfil) {
		this.permisoPerfil = permisoPerfil;
	}
	public String getStrModulo() {
		return strModulo;
	}
	public void setStrModulo(String strModulo) {
		this.strModulo = strModulo;
	}
	public String getStrPagina() {
		return strPagina;
	}
	public void setStrPagina(String strPagina) {
		this.strPagina = strPagina;
	}
	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}
	
}
