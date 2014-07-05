package pe.com.tumi.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Domicilio  extends TumiDomain{    

	// Tabla PER_PERSONA
	private	 int		intIdpersona;
	private  int		intIdTipoDomicilio;
	private  int		intIdTipoDireccion;
	private  int		intIdTipoVivienda;
	private  int		intIdTipoVia;
	private  String		strNombreVia;
	private  int		intNroVia;
	private  int		intInterior;
	private  int		intIdTipoZona;
	private  String		strNombreZona;
	private	 String		strReferencia;
	private  int		intIdDepartamento;
	private	 int		intIdDistrito;
	private  int		intIdProvincia;
	private  boolean	fgCroquis;
	private  boolean	fgArchivo;
	private	 boolean	fgCorrespondencia;
	private  String		strObservacion;
	private  String		strDireccion;
	private  int		intIdDirUrl;
	private  String		strDirUrl;
	private  Boolean	chkDir;
	
	public int getIntIdpersona() {
		return intIdpersona;
	}
	public void setIntIdpersona(int intIdpersona) {
		this.intIdpersona = intIdpersona;
	}
	public int getIntIdTipoDomicilio() {
		return intIdTipoDomicilio;
	}
	public void setIntIdTipoDomicilio(int intIdTipoDomicilio) {
		this.intIdTipoDomicilio = intIdTipoDomicilio;
	}
	public int getIntIdTipoDireccion() {
		return intIdTipoDireccion;
	}
	public void setIntIdTipoDireccion(int intIdTipoDireccion) {
		this.intIdTipoDireccion = intIdTipoDireccion;
	}
	public int getIntIdTipoVivienda() {
		return intIdTipoVivienda;
	}
	public void setIntIdTipoVivienda(int intIdTipoVivienda) {
		this.intIdTipoVivienda = intIdTipoVivienda;
	}
	public int getIntIdTipoVia() {
		return intIdTipoVia;
	}
	public void setIntIdTipoVia(int intIdTipoVia) {
		this.intIdTipoVia = intIdTipoVia;
	}
	public String getStrNombreVia() {
		return strNombreVia;
	}
	public void setStrNombreVia(String strNombreVia) {
		this.strNombreVia = strNombreVia;
	}
	public int getIntNroVia() {
		return intNroVia;
	}
	public void setIntNroVia(int intNroVia) {
		this.intNroVia = intNroVia;
	}
	public int getIntInterior() {
		return intInterior;
	}
	public void setIntInterior(int intInterior) {
		this.intInterior = intInterior;
	}
	public int getIntIdTipoZona() {
		return intIdTipoZona;
	}
	public void setIntIdTipoZona(int intIdTipoZona) {
		this.intIdTipoZona = intIdTipoZona;
	}
	public String getStrNombreZona() {
		return strNombreZona;
	}
	public void setStrNombreZona(String strNombreZona) {
		this.strNombreZona = strNombreZona;
	}
	public String getStrReferencia() {
		return strReferencia;
	}
	public void setStrReferencia(String strReferencia) {
		this.strReferencia = strReferencia;
	}
	public int getIntIdDepartamento() {
		return intIdDepartamento;
	}
	public void setIntIdDepartamento(int intIdDepartamento) {
		this.intIdDepartamento = intIdDepartamento;
	}
	public int getIntIdDistrito() {
		return intIdDistrito;
	}
	public void setIntIdDistrito(int intIdDistrito) {
		this.intIdDistrito = intIdDistrito;
	}
	public int getIntIdProvincia() {
		return intIdProvincia;
	}
	public void setIntIdProvincia(int intIdProvincia) {
		this.intIdProvincia = intIdProvincia;
	}
	public boolean isFgCroquis() {
		return fgCroquis;
	}
	public void setFgCroquis(boolean fgCroquis) {
		this.fgCroquis = fgCroquis;
	}
	public boolean isFgArchivo() {
		return fgArchivo;
	}
	public void setFgArchivo(boolean fgArchivo) {
		this.fgArchivo = fgArchivo;
	}
	public boolean isFgCorrespondencia() {
		return fgCorrespondencia;
	}
	public void setFgCorrespondencia(boolean fgCorrespondencia) {
		this.fgCorrespondencia = fgCorrespondencia;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public String getStrDireccion() {
		return strDireccion;
	}
	public void setStrDireccion(String strDireccion) {
		this.strDireccion = strDireccion;
	}
	public int getIntIdDirUrl() {
		return intIdDirUrl;
	}
	public void setIntIdDirUrl(int intIdDirUrl) {
		this.intIdDirUrl = intIdDirUrl;
	}
	public String getStrDirUrl() {
		return strDirUrl;
	}
	public void setStrDirUrl(String strDirUrl) {
		this.strDirUrl = strDirUrl;
	}
	public Boolean getChkDir() {
		return chkDir;
	}
	public void setChkDir(Boolean chkDir) {
		this.chkDir = chkDir;
	}
	
}
