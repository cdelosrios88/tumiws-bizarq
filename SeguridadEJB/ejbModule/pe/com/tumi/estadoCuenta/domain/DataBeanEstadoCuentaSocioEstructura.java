package pe.com.tumi.estadoCuenta.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;

public class DataBeanEstadoCuentaSocioEstructura extends TumiDomain {
	private Integer intIdSucursal;
	private String strSucursal;
	private Integer intIdPersona;
	private String strDocIdent;
	private String strNombreEstructura;
	private Integer intCondicionLaboral;
	private Integer intModalidad;
	private Integer intTipoSocio;
	//JCHAVEZ 13.01.2014 Recuperando foto y firma 
	private Integer intTipoArchivoFoto;
	private Integer intItemArchivoFoto;
	private Integer intItemHistoricoFoto;
	private Integer intTipoArchivoFirma;
	private Integer intItemArchivoFirma;
	private Integer intItemHistoricoFirma;		
	private Archivo foto;
	private Archivo firma;
	
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}	
	public String getStrDocIdent() {
		return strDocIdent;
	}
	public void setStrDocIdent(String strDocIdent) {
		this.strDocIdent = strDocIdent;
	}
	public String getStrNombreEstructura() {
		return strNombreEstructura;
	}
	public void setStrNombreEstructura(String strNombreEstructura) {
		this.strNombreEstructura = strNombreEstructura;
	}
	public Integer getIntCondicionLaboral() {
		return intCondicionLaboral;
	}
	public void setIntCondicionLaboral(Integer intCondicionLaboral) {
		this.intCondicionLaboral = intCondicionLaboral;
	}
	public Integer getIntModalidad() {
		return intModalidad;
	}
	public void setIntModalidad(Integer intModalidad) {
		this.intModalidad = intModalidad;
	}
	public Integer getIntTipoSocio() {
		return intTipoSocio;
	}
	public void setIntTipoSocio(Integer intTipoSocio) {
		this.intTipoSocio = intTipoSocio;
	}
	//JCHAVEZ 13.01.2014
	public Integer getIntTipoArchivoFoto() {
		return intTipoArchivoFoto;
	}
	public void setIntTipoArchivoFoto(Integer intTipoArchivoFoto) {
		this.intTipoArchivoFoto = intTipoArchivoFoto;
	}
	public Integer getIntItemArchivoFoto() {
		return intItemArchivoFoto;
	}
	public void setIntItemArchivoFoto(Integer intItemArchivoFoto) {
		this.intItemArchivoFoto = intItemArchivoFoto;
	}
	public Integer getIntItemHistoricoFoto() {
		return intItemHistoricoFoto;
	}
	public void setIntItemHistoricoFoto(Integer intItemHistoricoFoto) {
		this.intItemHistoricoFoto = intItemHistoricoFoto;
	}
	public Integer getIntTipoArchivoFirma() {
		return intTipoArchivoFirma;
	}
	public void setIntTipoArchivoFirma(Integer intTipoArchivoFirma) {
		this.intTipoArchivoFirma = intTipoArchivoFirma;
	}
	public Integer getIntItemArchivoFirma() {
		return intItemArchivoFirma;
	}
	public void setIntItemArchivoFirma(Integer intItemArchivoFirma) {
		this.intItemArchivoFirma = intItemArchivoFirma;
	}
	public Integer getIntItemHistoricoFirma() {
		return intItemHistoricoFirma;
	}
	public void setIntItemHistoricoFirma(Integer intItemHistoricoFirma) {
		this.intItemHistoricoFirma = intItemHistoricoFirma;
	}
	public Archivo getFoto() {
		return foto;
	}
	public void setFoto(Archivo foto) {
		this.foto = foto;
	}
	public Archivo getFirma() {
		return firma;
	}
	public void setFirma(Archivo firma) {
		this.firma = firma;
	}
}
