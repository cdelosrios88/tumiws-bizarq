package pe.com.tumi.seguridad.permiso.bean;

public class PerfilMsg {
	private String idEmpresa;
	private String nombre;
	private String idTipoPerfil;
	private String dtDesde;
	private String dtHasta;
	private String idEstado;
	private String listaPerfil;
	private PermisoMsg msgPermiso;
	public String getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdTipoPerfil() {
		return idTipoPerfil;
	}
	public void setIdTipoPerfil(String idTipoPerfil) {
		this.idTipoPerfil = idTipoPerfil;
	}
	public String getDtDesde() {
		return dtDesde;
	}
	public void setDtDesde(String dtDesde) {
		this.dtDesde = dtDesde;
	}
	public String getDtHasta() {
		return dtHasta;
	}
	public void setDtHasta(String dtHasta) {
		this.dtHasta = dtHasta;
	}
	public String getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	public String getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(String listaPerfil) {
		this.listaPerfil = listaPerfil;
	}
	public PermisoMsg getMsgPermiso() {
		return msgPermiso;
	}
	public void setMsgPermiso(PermisoMsg msgPermiso) {
		this.msgPermiso = msgPermiso;
	}
}
