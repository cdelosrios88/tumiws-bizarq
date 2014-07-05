package pe.com.tumi.seguridad.login.bean;

public class UsuarioMsg {
	private String tipoUsuario;
	private String tipoPersonaCod;
	private String tipoIdentidad;
	//Validacion para Persona Natural
	private String nroIdentidad;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	//Validacion para Persona Juridica
	private String razonSocial;
	private String nombreComercial;
	private DomicilioMsg msgDomicilio;
	private ComunicacionMsg msgComunicacion;
	
	private String listaDomicilio;
	private String listaComunicacion;
	private String listaEmpresaUsuario;
	
	private String idPerfil;
	private String desdePerfil;
	private String hastaPerfil;
	private String listaUsuarioPerfil;
	private String idSucursal;
	private String desdeSucursal;
	private String hastaSucursal;
	private String listaUsuarioSucursal;
	private String idSubSucursal;
	private String desdeSubSucursal;
	private String hastaSubSucursal;
	private String listaUsuarioSubSucursal;

	private String usuario;
	private String contrasena;
	private String contrasenaValida;
	
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public String getTipoPersonaCod() {
		return tipoPersonaCod;
	}
	public void setTipoPersonaCod(String tipoPersonaCod) {
		this.tipoPersonaCod = tipoPersonaCod;
	}
	public String getNroIdentidad() {
		return nroIdentidad;
	}
	public void setNroIdentidad(String nroIdentidad) {
		this.nroIdentidad = nroIdentidad;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getListaDomicilio() {
		return listaDomicilio;
	}
	public void setListaDomicilio(String listaDomicilio) {
		this.listaDomicilio = listaDomicilio;
	}
	public String getListaComunicacion() {
		return listaComunicacion;
	}
	public void setListaComunicacion(String listaComunicacion) {
		this.listaComunicacion = listaComunicacion;
	}
	public String getListaEmpresaUsuario() {
		return listaEmpresaUsuario;
	}
	public void setListaEmpresaUsuario(String listaEmpresaUsuario) {
		this.listaEmpresaUsuario = listaEmpresaUsuario;
	}
	public String getListaUsuarioPerfil() {
		return listaUsuarioPerfil;
	}
	public void setListaUsuarioPerfil(String listaUsuarioPerfil) {
		this.listaUsuarioPerfil = listaUsuarioPerfil;
	}
	public String getListaUsuarioSucursal() {
		return listaUsuarioSucursal;
	}
	public void setListaUsuarioSucursal(String listaUsuarioSucursal) {
		this.listaUsuarioSucursal = listaUsuarioSucursal;
	}
	public String getListaUsuarioSubSucursal() {
		return listaUsuarioSubSucursal;
	}
	public void setListaUsuarioSubSucursal(String listaUsuarioSubSucursal) {
		this.listaUsuarioSubSucursal = listaUsuarioSubSucursal;
	}
	public String getContrasenaValida() {
		return contrasenaValida;
	}
	public void setContrasenaValida(String contrasenaValida) {
		this.contrasenaValida = contrasenaValida;
	}
	public DomicilioMsg getMsgDomicilio() {
		return msgDomicilio;
	}
	public void setMsgDomicilio(DomicilioMsg msgDomicilio) {
		this.msgDomicilio = msgDomicilio;
	}
	public ComunicacionMsg getMsgComunicacion() {
		return msgComunicacion;
	}
	public void setMsgComunicacion(ComunicacionMsg msgComunicacion) {
		this.msgComunicacion = msgComunicacion;
	}
	public String getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getDesdePerfil() {
		return desdePerfil;
	}
	public void setDesdePerfil(String desdePerfil) {
		this.desdePerfil = desdePerfil;
	}
	public String getIdSucursal() {
		return idSucursal;
	}
	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
	}
	public String getDesdeSucursal() {
		return desdeSucursal;
	}
	public void setDesdeSucursal(String desdeSucursal) {
		this.desdeSucursal = desdeSucursal;
	}
	public String getIdSubSucursal() {
		return idSubSucursal;
	}
	public void setIdSubSucursal(String idSubSucursal) {
		this.idSubSucursal = idSubSucursal;
	}
	public String getDesdeSubSucursal() {
		return desdeSubSucursal;
	}
	public void setDesdeSubSucursal(String desdeSubSucursal) {
		this.desdeSubSucursal = desdeSubSucursal;
	}
	public String getHastaPerfil() {
		return hastaPerfil;
	}
	public void setHastaPerfil(String hastaPerfil) {
		this.hastaPerfil = hastaPerfil;
	}
	public String getHastaSucursal() {
		return hastaSucursal;
	}
	public void setHastaSucursal(String hastaSucursal) {
		this.hastaSucursal = hastaSucursal;
	}
	public String getHastaSubSucursal() {
		return hastaSubSucursal;
	}
	public void setHastaSubSucursal(String hastaSubSucursal) {
		this.hastaSubSucursal = hastaSubSucursal;
	}
	public String getTipoIdentidad() {
		return tipoIdentidad;
	}
	public void setTipoIdentidad(String tipoIdentidad) {
		this.tipoIdentidad = tipoIdentidad;
	}
	
}
