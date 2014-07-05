package pe.com.tumi.credito.socio.estructura.domain;

import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class Estructura extends TumiDomain {

	private EstructuraId id;
	private Integer intPersEmpresaPk;
	private Integer intPersPersonaPk;
	private Date dtFechaRegistro;
	private String strFechaRegistro;
	private Integer intIdGrupo;
	private Integer intParaTipoTerceroCod;
	private Integer intParaEstadoCod;
	private Integer intPersPersonaUsuarioPk;
	private Integer intNivelRel;
	private Integer intIdCodigoRel;
	private Date dtFechaEliminacion;
	private List<AdminPadron> listaAdminPadron;
	private List<EstructuraDetalle> listaEstructuraDetalle;
	private List<Estructura> listaEstructura;
	
	private Juridica juridica;
	private List<EstructuraDetalle> listaEstructuraDetallePlanilla;
	private List<EstructuraDetalle> listaEstructuraDetalleAdministra;
	private List<EstructuraDetalle> listaEstructuraDetalleCobranza;
	
	//cobranza
	private Integer intPeriodoAnio;
	private Integer intPeriodoMes;
	
	public EstructuraId getId() {
		return id;
	}
	public void setId(EstructuraId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntPersPersonaPk() {
		return intPersPersonaPk;
	}
	public void setIntPersPersonaPk(Integer intPersPersonaPk) {
		this.intPersPersonaPk = intPersPersonaPk;
	}
	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public Integer getIntIdGrupo() {
		return intIdGrupo;
	}
	public void setIntIdGrupo(Integer intIdGrupo) {
		this.intIdGrupo = intIdGrupo;
	}
	public Integer getIntParaTipoTerceroCod() {
		return intParaTipoTerceroCod;
	}
	public void setIntParaTipoTerceroCod(Integer intParaTipoTerceroCod) {
		this.intParaTipoTerceroCod = intParaTipoTerceroCod;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public Integer getIntNivelRel() {
		return intNivelRel;
	}
	public void setIntNivelRel(Integer intNivelRel) {
		this.intNivelRel = intNivelRel;
	}
	public Integer getIntIdCodigoRel() {
		return intIdCodigoRel;
	}
	public void setIntIdCodigoRel(Integer intIdCodigoRel) {
		this.intIdCodigoRel = intIdCodigoRel;
	}
	public List<AdminPadron> getListaAdminPadron() {
		return listaAdminPadron;
	}
	public void setListaAdminPadron(List<AdminPadron> listaAdminPadron) {
		this.listaAdminPadron = listaAdminPadron;
	}
	public List<EstructuraDetalle> getListaEstructuraDetalle() {
		return listaEstructuraDetalle;
	}
	public void setListaEstructuraDetalle(
			List<EstructuraDetalle> listaEstructuraDetalle) {
		this.listaEstructuraDetalle = listaEstructuraDetalle;
	}
	public List<Estructura> getListaEstructura() {
		return listaEstructura;
	}
	public void setListaEstructura(List<Estructura> listaEstructura) {
		this.listaEstructura = listaEstructura;
	}
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}
	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}
	public Juridica getJuridica() {
		return juridica;
	}
	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}
	public List<EstructuraDetalle> getListaEstructuraDetallePlanilla() {
		return listaEstructuraDetallePlanilla;
	}
	public void setListaEstructuraDetallePlanilla(
			List<EstructuraDetalle> listaEstructuraDetallePlanilla) {
		this.listaEstructuraDetallePlanilla = listaEstructuraDetallePlanilla;
	}
	public List<EstructuraDetalle> getListaEstructuraDetalleAdministra() {
		return listaEstructuraDetalleAdministra;
	}
	public void setListaEstructuraDetalleAdministra(
			List<EstructuraDetalle> listaEstructuraDetalleAdministra) {
		this.listaEstructuraDetalleAdministra = listaEstructuraDetalleAdministra;
	}
	public List<EstructuraDetalle> getListaEstructuraDetalleCobranza() {
		return listaEstructuraDetalleCobranza;
	}
	public void setListaEstructuraDetalleCobranza(
			List<EstructuraDetalle> listaEstructuraDetalleCobranza) {
		this.listaEstructuraDetalleCobranza = listaEstructuraDetalleCobranza;
	}
	public Date getDtFechaEliminacion() {
		return dtFechaEliminacion;
	}
	public void setDtFechaEliminacion(Date dtFechaEliminacion) {
		this.dtFechaEliminacion = dtFechaEliminacion;
	}
	public Integer getIntPeriodoAnio() {
		return intPeriodoAnio;
	}
	public void setIntPeriodoAnio(Integer intPeriodoAnio) {
		this.intPeriodoAnio = intPeriodoAnio;
	}
	public Integer getIntPeriodoMes() {
		return intPeriodoMes;
	}
	public void setIntPeriodoMes(Integer intPeriodoMes) {
		this.intPeriodoMes = intPeriodoMes;
	}
	
}
