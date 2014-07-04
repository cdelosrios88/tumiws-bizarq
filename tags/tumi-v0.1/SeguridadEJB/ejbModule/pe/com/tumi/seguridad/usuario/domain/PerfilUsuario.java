package pe.com.tumi.seguridad.usuario.domain;

public class PerfilUsuario {
	private int		intIdEmpresa;
	private String	strEmpresa;
	private int		intIdPersona;
	private int		intIdPerfil;
	private String	strDescPerfil;
	private String	daFecIni;
	private String	daFecFin;
	private String  strFechas;
	private Boolean chkPerfUsu;
	
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
	public int getIntIdPerfil() {
		return intIdPerfil;
	}
	public void setIntIdPerfil(int intIdPerfil) {
		this.intIdPerfil = intIdPerfil;
	}
	public String getStrDescPerfil() {
		return strDescPerfil;
	}
	public void setStrDescPerfil(String strDescPerfil) {
		this.strDescPerfil = strDescPerfil;
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
	public Boolean getChkPerfUsu() {
		return chkPerfUsu;
	}
	public void setChkPerfUsu(Boolean chkPerfUsu) {
		this.chkPerfUsu = chkPerfUsu;
	}
}
