package pe.com.tumi.seguridad.domain;

import java.util.List;

public class AdminMenu {
	private 	int 	intIdEmpresa;
	private 	String 	strIdTransaccion;
	private 	String 	strIdTransaccionPadre;
	private 	String 	strNombre;
	private 	String 	strNombreM1;
	private 	String 	strNombreM2;
	private 	String 	strNombreM3;
	private 	String 	strNombreM4;
	private 	int 	intMenuOrden;
	private 	Boolean chkPerfil;
	private 	int 	intTipoMenu;
	private 	int 	intIdEstado;
	private 	int 	intCrecimiento;
	private 	int 	intFinalMenu;
	private 	Boolean blnFinalMenu;
	private 	int 	intNivelMenu;
	private 	List	cursorLista;
	private		int		intIdPersona;		
	private		int		intIdPerfil;
	private 	String 	daFecIni;	
	private 	String 	daFecFin;
	private 	String 	strAccion;
		
	public String getStrAccion() {
		return strAccion;
	}
	public void setStrAccion(String strAccion) {
		this.strAccion = strAccion;
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
	public int getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(int intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
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
	public int getIntMenuOrden() {
		return intMenuOrden;
	}
	public void setIntMenuOrden(int intMenuOrden) {
		this.intMenuOrden = intMenuOrden;
	}
	public String getStrNombre() {
		return strNombre;
	}
	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}
	public Boolean getChkPerfil() {
		return chkPerfil;
	}
	public void setChkPerfil(Boolean chkPerfil) {
		this.chkPerfil = chkPerfil;
	}
	public int getIntTipoMenu() {
		return intTipoMenu;
	}
	public void setIntTipoMenu(int intTipoMenu) {
		this.intTipoMenu = intTipoMenu;
	}
	public int getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(int intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public int getIntCrecimiento() {
		return intCrecimiento;
	}
	public void setIntCrecimiento(int intCrecimiento) {
		this.intCrecimiento = intCrecimiento;
	}
	public int getIntFinalMenu() {
		return intFinalMenu;
	}
	public void setIntFinalMenu(int intFinalMenu) {
		this.intFinalMenu = intFinalMenu;
	}
	public Boolean getBlnFinalMenu() {
		return blnFinalMenu;
	}
	public void setBlnFinalMenu(Boolean blnFinalMenu) {
		this.blnFinalMenu = blnFinalMenu;
	}
	public int getIntNivelMenu() {
		return intNivelMenu;
	}
	public void setIntNivelMenu(int intNivelMenu) {
		this.intNivelMenu = intNivelMenu;
	}
	public List getCursorLista() {
		return cursorLista;
	}
	public void setCursorLista(List cursorLista) {
		this.cursorLista = cursorLista;
	}
	
}
