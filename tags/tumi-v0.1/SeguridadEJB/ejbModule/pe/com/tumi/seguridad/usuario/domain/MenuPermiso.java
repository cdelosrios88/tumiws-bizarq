package pe.com.tumi.seguridad.usuario.domain;

public class MenuPermiso {
	private		int		intIdEmpresa;
	private		int		intIdPersona;	
	private		int		intIdPerfil;
	private		String	strIdTransaccion;
	private		String	strIdTransaccionPadre;
	private		String	strTransaccion;
	private		int		intNivel;
	private 	String 	strNombreM1;
	private 	String 	strNombreM2;
	private 	String 	strNombreM3;
	private 	String 	strNombreM4;
	private 	String 	daFecIni;
	private 	String 	daFecFin;
	private		Boolean chkPerfil;
	
	
	public int getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(int intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public int getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(int intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public String getStrIdTransaccion() {
		return strIdTransaccion;
	}
	public void setStrIdTransaccion(String strIdTransaccion) {
		this.strIdTransaccion = strIdTransaccion;
	}
	public String getStrIdTransaccionPadre() {
		return strIdTransaccionPadre;
	}
	public void setStrIdTransaccionPadre(String strIdTransaccionPadre) {
		this.strIdTransaccionPadre = strIdTransaccionPadre;
	}
	public String getStrTransaccion() {
		return strTransaccion;
	}
	public void setStrTransaccion(String strTransaccion) {
		this.strTransaccion = strTransaccion;
	}
	public int getIntNivel() {
		return intNivel;
	}
	public void setIntNivel(int intNivel) {
		this.intNivel = intNivel;
	}
	public String getStrNombreM1() {
		return strNombreM1;
	}
	public void setStrNombreM1(String strNombreM1) {
		this.strNombreM1 = strNombreM1;
	}
	public String getStrNombreM2() {
		return strNombreM2;
	}
	public void setStrNombreM2(String strNombreM2) {
		this.strNombreM2 = strNombreM2;
	}
	public String getStrNombreM3() {
		return strNombreM3;
	}
	public void setStrNombreM3(String strNombreM3) {
		this.strNombreM3 = strNombreM3;
	}
	public String getStrNombreM4() {
		return strNombreM4;
	}
	public void setStrNombreM4(String strNombreM4) {
		this.strNombreM4 = strNombreM4;
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
	public Boolean getChkPerfil() {
		return chkPerfil;
	}
	public void setChkPerfil(Boolean chkPerfil) {
		this.chkPerfil = chkPerfil;
	}
	public int getIntIdPerfil() {
		return intIdPerfil;
	}
	public void setIntIdPerfil(int intIdPerfil) {
		this.intIdPerfil = intIdPerfil;
	}
}
