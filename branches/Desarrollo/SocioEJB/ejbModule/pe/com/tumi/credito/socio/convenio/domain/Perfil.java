package pe.com.tumi.credito.socio.convenio.domain;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.composite.HojaControlComp;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Perfil extends TumiDomain {

	private PerfilId id;
	private Adenda adenda;
	private Integer intConvenio;
	private Integer intItemConvenio;
	private Integer intPersEmpresaPk;
	private Integer intSeguPerfilPk;
	private Integer intPersonaRegistraPk;
	private String strObservacion;
	private List<PerfilDetalle> listaPerfilDetalle;
	private List<HojaControlComp> listaHojaControlComp;
	
	public PerfilId getId() {
		return id;
	}
	public void setId(PerfilId id) {
		this.id = id;
	}
	public Adenda getAdenda() {
		return adenda;
	}
	public void setAdenda(Adenda adenda) {
		this.adenda = adenda;
	}
	public Integer getIntConvenio() {
		return intConvenio;
	}
	public void setIntConvenio(Integer intConvenio) {
		this.intConvenio = intConvenio;
	}
	public Integer getIntItemConvenio() {
		return intItemConvenio;
	}
	public void setIntItemConvenio(Integer intItemConvenio) {
		this.intItemConvenio = intItemConvenio;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntSeguPerfilPk() {
		return intSeguPerfilPk;
	}
	public void setIntSeguPerfilPk(Integer intSeguPerfilPk) {
		this.intSeguPerfilPk = intSeguPerfilPk;
	}
	public Integer getIntPersonaRegistraPk() {
		return intPersonaRegistraPk;
	}
	public void setIntPersonaRegistraPk(Integer intPersonaRegistraPk) {
		this.intPersonaRegistraPk = intPersonaRegistraPk;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public List<PerfilDetalle> getListaPerfilDetalle() {
		return listaPerfilDetalle;
	}
	public void setListaPerfilDetalle(List<PerfilDetalle> listaPerfilDetalle) {
		this.listaPerfilDetalle = listaPerfilDetalle;
	}
	public List<HojaControlComp> getListaHojaControlComp() {
		return listaHojaControlComp;
	}
	public void setListaHojaControlComp(List<HojaControlComp> listaHojaControlComp) {
		this.listaHojaControlComp = listaHojaControlComp;
	}
	
}
