package pe.com.tumi.seguridad.usuario.domain;

public class SubSucursalUsuario {
	private int		intIdEmpresa;
	private String	strEmpresa;
	private int		intIdPersona;
	private int		intIdSucursal;
	private String	strSucursal;
	private int		intIdSubSucursal;
	private String	strSubSucursal;
	private	String	daFecIni;
	private	String	daFecFin;
	private String	strFechas;
	private Boolean chkSubSucUsu;
	
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
	public int getIntIdSubSucursal() {
		return intIdSubSucursal;
	}
	public void setIntIdSubSucursal(int intIdSubSucursal) {
		this.intIdSubSucursal = intIdSubSucursal;
	}
	public String getStrSubSucursal() {
		return strSubSucursal;
	}
	public void setStrSubSucursal(String strSubSucursal) {
		this.strSubSucursal = strSubSucursal;
	}
	public int getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(int intIdPersona) {
		this.intIdPersona = intIdPersona;
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
	public Boolean getChkSubSucUsu() {
		return chkSubSucUsu;
	}
	public void setChkSubSucUsu(Boolean chkSubSucUsu) {
		this.chkSubSucUsu = chkSubSucUsu;
	}
}
