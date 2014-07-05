package pe.com.tumi.seguridad.usuario.domain;

public class Permiso {
	private		int		intIdEmpresa;
	private 	int		intIdPersona;
	private		String	strUsuario;
	private		String	strNombreCompleto;
	private		String	strDescPerfil;
	private		String	strIdTransaccion;
	private		String	daFecRegistro;
	private		String  daFecIni;
	private		String  daFecFin;
	private		String  strEstado;
	private  	int		intIdEstado;
	private  	Boolean	chkRanFecha;
	private  	Boolean	chkPerfil;
	private 	int		intPermisos;
	private	    String	strRanFecha;
	private	    String	strEmpresa;
	private 	int		intIdPerfil;
	
	public int getIntIdPerfil() {
		return intIdPerfil;
	}
	public void setIntIdPerfil(int intIdPerfil) {
		this.intIdPerfil = intIdPerfil;
	}
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
	public String getStrUsuario() {
		return strUsuario;
	}
	public void setStrUsuario(String strUsuario) {
		this.strUsuario = strUsuario;
	}
	public String getStrNombreCompleto() {
		return strNombreCompleto;
	}
	public void setStrNombreCompleto(String strNombreCompleto) {
		this.strNombreCompleto = strNombreCompleto;
	}
	public String getStrDescPerfil() {
		return strDescPerfil;
	}
	public void setStrDescPerfil(String strDescPerfil) {
		this.strDescPerfil = strDescPerfil;
	}
	public String getStrIdTransaccion() {
		return strIdTransaccion;
	}
	public void setStrIdTransaccion(String strIdTransaccion) {
		this.strIdTransaccion = strIdTransaccion;
	}
	public String getDaFecRegistro() {
		return daFecRegistro;
	}
	public void setDaFecRegistro(String daFecRegistro) {
		this.daFecRegistro = daFecRegistro;
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
	public String getStrEstado() {
		return strEstado;
	}
	public void setStrEstado(String strEstado) {
		this.strEstado = strEstado;
	}
	public int getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(int intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Boolean getChkRanFecha() {
		return chkRanFecha;
	}
	public void setChkRanFecha(Boolean chkRanFecha) {
		this.chkRanFecha = chkRanFecha;
	}
	public Boolean getChkPerfil() {
		return chkPerfil;
	}
	public void setChkPerfil(Boolean chkPerfil) {
		this.chkPerfil = chkPerfil;
	}
	public int getIntPermisos() {
		return intPermisos;
	}
	public void setIntPermisos(int intPermisos) {
		this.intPermisos = intPermisos;
	}
	public String getStrRanFecha() {
		return strRanFecha;
	}
	public void setStrRanFecha(String strRanFecha) {
		this.strRanFecha = strRanFecha;
	}
	public String getStrEmpresa() {
		return strEmpresa;
	}
	public void setStrEmpresa(String strEmpresa) {
		this.strEmpresa = strEmpresa;
	}
}