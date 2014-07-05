package pe.com.tumi.credito.socio.core.domain;


import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Socio extends TumiDomain {

	private SocioPK id;
	private String strApePatSoc;
	private String strApeMatSoc;
	private String strNombreSoc;
	private List<SocioEstructura> listSocioEstructura;
	private SocioEstructura socioEstructura;
	
	// CGD - 25.10.2013
	private String strNroDocumento;
	private Integer intTipoSocio;
	private Integer intCondicionLab;
	private Integer intParaModalidad;
	private Integer intSucuAdministra;
	private Integer intParaTipoCta;
	private String strNumeroCta;
	private Integer intParaSituacionCta;
	private String  strFechaRegistroCta;
	private Timestamp tsFechaRegistroCta;
	    
	
	//Getters & Setters
	public String getStrApePatSoc() {
		return strApePatSoc;
	}
	public void setStrApePatSoc(String strApePatSoc) {
		this.strApePatSoc = strApePatSoc;
	}
	public String getStrApeMatSoc() {
		return strApeMatSoc;
	}
	public void setStrApeMatSoc(String strApeMatSoc) {
		this.strApeMatSoc = strApeMatSoc;
	}
	public String getStrNombreSoc() {
		return strNombreSoc;
	}
	public void setStrNombreSoc(String strNombreSoc) {
		this.strNombreSoc = strNombreSoc;
	}
	public List<SocioEstructura> getListSocioEstructura() {
		return listSocioEstructura;
	}
	public void setListSocioEstructura(List<SocioEstructura> listSocioEstructura) {
		this.listSocioEstructura = listSocioEstructura;
	}
	public SocioEstructura getSocioEstructura() {
		return socioEstructura;
	}
	public void setSocioEstructura(SocioEstructura socioEstructura) {
		this.socioEstructura = socioEstructura;
	}
	public SocioPK getId() {
		return id;
	}
	public void setId(SocioPK id) {
		this.id = id;
	}
	
		
	public String getStrNroDocumento() {
		return strNroDocumento;
	}
	public void setStrNroDocumento(String strNroDocumento) {
		this.strNroDocumento = strNroDocumento;
	}
	public Integer getIntTipoSocio() {
		return intTipoSocio;
	}
	public void setIntTipoSocio(Integer intTipoSocio) {
		this.intTipoSocio = intTipoSocio;
	}
	public Integer getIntCondicionLab() {
		return intCondicionLab;
	}
	public void setIntCondicionLab(Integer intCondicionLab) {
		this.intCondicionLab = intCondicionLab;
	}
	public Integer getIntParaModalidad() {
		return intParaModalidad;
	}
	public void setIntParaModalidad(Integer intParaModalidad) {
		this.intParaModalidad = intParaModalidad;
	}
	public Integer getIntSucuAdministra() {
		return intSucuAdministra;
	}
	public void setIntSucuAdministra(Integer intSucuAdministra) {
		this.intSucuAdministra = intSucuAdministra;
	}
	public Integer getIntParaTipoCta() {
		return intParaTipoCta;
	}
	public void setIntParaTipoCta(Integer intParaTipoCta) {
		this.intParaTipoCta = intParaTipoCta;
	}
	public String getStrNumeroCta() {
		return strNumeroCta;
	}
	public void setStrNumeroCta(String strNumeroCta) {
		this.strNumeroCta = strNumeroCta;
	}
	public Integer getIntParaSituacionCta() {
		return intParaSituacionCta;
	}
	public void setIntParaSituacionCta(Integer intParaSituacionCta) {
		this.intParaSituacionCta = intParaSituacionCta;
	}
	public String getStrFechaRegistroCta() {
		return strFechaRegistroCta;
	}
	public void setStrFechaRegistroCta(String strFechaRegistroCta) {
		this.strFechaRegistroCta = strFechaRegistroCta;
	}
	@Override
	public String toString() {
		return "Socio [id=" + id + ", strApePatSoc=" + strApePatSoc
				+ ", strApeMatSoc=" + strApeMatSoc + ", strNombreSoc="
				+ strNombreSoc + ", listSocioEstructura=" + listSocioEstructura
				+ ", socioEstructura=" + socioEstructura + "]";
	}
	public Timestamp getTsFechaRegistroCta() {
		return tsFechaRegistroCta;
	}
	public void setTsFechaRegistroCta(Timestamp tsFechaRegistroCta) {
		this.tsFechaRegistroCta = tsFechaRegistroCta;
	}
	
}
