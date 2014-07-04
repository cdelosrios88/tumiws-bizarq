package pe.com.tumi.seguridad.usuario.domain;

public class SucursalUsuario {
	private int		intIdEmpresa;
	private String	strEmpresa;
	private int		intIdPersona;
	private int		intIdSucursal;
	private String  strSucursal;
	private String	daFecIni;
	private String  daFecFin;
	private String	strFechas;
	private Boolean chkSucUsu;
	
	public int getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(int intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public String getStrEmpresa() {
		return strEmpresa;
	}
	public void setStrEmpresa(String strEmpresa) {
		this.strEmpresa = strEmpresa;
	}
	public int getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(int intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public int getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(int intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public String getDaFecIni() {
		return daFecIni;
	}
	public void setDaFecIni(String daFecIni) {
		this.daFecIni = daFecIni;
	}
	public String getDaFecFin() {
		return daFecFin;
	}
	public void setDaFecFin(String daFecFin) {
		this.daFecFin = daFecFin;
	}
	public String getStrFechas() {
		return strFechas;
	}
	public void setStrFechas(String strFechas) {
		this.strFechas = strFechas;
	}
	public Boolean getChkSucUsu() {
		return chkSucUsu;
	}
	public void setChkSucUsu(Boolean chkSucUsu) {
		this.chkSucUsu = chkSucUsu;
	}
}
