package pe.com.tumi.seguridad.permiso.bean;

public class MenuMsg {
	private String idEmpresa;
	private String tipoMenu;
	private String listaMenu;
	private String idEstado;
	private SubMenuMsg msgSubMenu;
	
	public String getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getTipoMenu() {
		return tipoMenu;
	}
	public void setTipoMenu(String tipoMenu) {
		this.tipoMenu = tipoMenu;
	}
	public String getListaMenu() {
		return listaMenu;
	}
	public void setListaMenu(String listaMenu) {
		this.listaMenu = listaMenu;
	}
	public SubMenuMsg getMsgSubMenu() {
		return msgSubMenu;
	}
	public void setMsgSubMenu(SubMenuMsg msgSubMenu) {
		this.msgSubMenu = msgSubMenu;
	}
	public String getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	
}
