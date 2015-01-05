package pe.com.tumi.servicio.configuracion.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

//import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfServSolicitud extends TumiDomain{

	private ConfServSolicitudId id;
	private Integer intParaTipoRequertoAutorizaCod;
	private Integer intParaTipoOperacionCod;
	private Integer intParaSubtipoOperacionCod;
	private Date dtDesde;
	private Date dtHasta;
	private BigDecimal bdMontoDesde;
	private BigDecimal bdMontoHasta;
	private BigDecimal bdCuotaDesde;
	private BigDecimal bdCuotaHasta;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaRegistro;
	private Integer intPersPersonaUsuarioPk;
	private Timestamp tsFechaEliminacion;
	private Integer intPersPersonaEliminaPk;
	private List<ConfServGrupoCta> listaGrupoCta;
	private List<ConfServCreditoEmpresa> listaCreditoEmpresa;
	private List<ConfServCancelado> listaCancelado;
	private List<ConfServEstructuraDetalle> listaEstructuraDetalle;
	private List<ConfServCaptacion> listaCaptacion;
	private List<ConfServUsuario> listaUsuario;
	private List<ConfServPerfil> listaPerfil;
	private List<ConfServDetalle> listaDetalle;
	private List<ConfServCredito> listaCredito;
	private List<ConfServRol> listaRol;
	//private Estructura estructura;
	//para la interfaz
	private List<String> listaRazonSocialEstructura;
	
	// para la validacion de autorizacion de credito.
	private Boolean blnConfigurado;
	private Boolean blnUsuario;
	private Boolean blnPerfil;
	//Autor: Rodolfo Villarreal Acuña
	private String strDescripcionSubTipo;
	
	
	public ConfServSolicitud(){
		super();
		id = new ConfServSolicitudId();
	}
	
	public ConfServSolicitudId getId() {
		return id;
	}
	public void setId(ConfServSolicitudId id) {
		this.id = id;
	}
	public Integer getIntParaTipoRequertoAutorizaCod() {
		return intParaTipoRequertoAutorizaCod;
	}
	public void setIntParaTipoRequertoAutorizaCod(
			Integer intParaTipoRequertoAutorizaCod) {
		this.intParaTipoRequertoAutorizaCod = intParaTipoRequertoAutorizaCod;
	}
	public Integer getIntParaTipoOperacionCod() {
		return intParaTipoOperacionCod;
	}
	public void setIntParaTipoOperacionCod(Integer intParaTipoOperacionCod) {
		this.intParaTipoOperacionCod = intParaTipoOperacionCod;
	}
	public Integer getIntParaSubtipoOperacionCod() {
		return intParaSubtipoOperacionCod;
	}
	public void setIntParaSubtipoOperacionCod(Integer intParaSubtipoOperacionCod) {
		this.intParaSubtipoOperacionCod = intParaSubtipoOperacionCod;
	}
	public Date getDtDesde() {
		return dtDesde;
	}
	public void setDtDesde(Date dtDesde) {
		this.dtDesde = dtDesde;
	}
	public Date getDtHasta() {
		return dtHasta;
	}
	public void setDtHasta(Date dtHasta) {
		this.dtHasta = dtHasta;
	}
	public BigDecimal getBdMontoDesde() {
		return bdMontoDesde;
	}
	public void setBdMontoDesde(BigDecimal bdMontoDesde) {
		this.bdMontoDesde = bdMontoDesde;
	}
	public BigDecimal getBdMontoHasta() {
		return bdMontoHasta;
	}
	public void setBdMontoHasta(BigDecimal bdMontoHasta) {
		this.bdMontoHasta = bdMontoHasta;
	}
	public BigDecimal getBdCuotaDesde() {
		return bdCuotaDesde;
	}
	public void setBdCuotaDesde(BigDecimal bdCuotaDesde) {
		this.bdCuotaDesde = bdCuotaDesde;
	}
	public BigDecimal getBdCuotaHasta() {
		return bdCuotaHasta;
	}
	public void setBdCuotaHasta(BigDecimal bdCuotaHasta) {
		this.bdCuotaHasta = bdCuotaHasta;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}
	public List<ConfServGrupoCta> getListaGrupoCta() {
		return listaGrupoCta;
	}
	public void setListaGrupoCta(List<ConfServGrupoCta> listaGrupoCta) {
		this.listaGrupoCta = listaGrupoCta;
	}
	public List<ConfServCreditoEmpresa> getListaCreditoEmpresa() {
		return listaCreditoEmpresa;
	}
	public void setListaCreditoEmpresa(
			List<ConfServCreditoEmpresa> listaCreditoEmpresa) {
		this.listaCreditoEmpresa = listaCreditoEmpresa;
	}
	public List<ConfServCancelado> getListaCancelado() {
		return listaCancelado;
	}
	public void setListaCancelado(List<ConfServCancelado> listaCancelado) {
		this.listaCancelado = listaCancelado;
	}
	public List<ConfServCaptacion> getListaCaptacion() {
		return listaCaptacion;
	}
	public void setListaCaptacion(List<ConfServCaptacion> listaCaptacion) {
		this.listaCaptacion = listaCaptacion;
	}
	public List<ConfServUsuario> getListaUsuario() {
		return listaUsuario;
	}
	public void setListaUsuario(List<ConfServUsuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	public List<ConfServPerfil> getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(List<ConfServPerfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}
	public List<ConfServDetalle> getListaDetalle() {
		return listaDetalle;
	}
	public void setListaDetalle(List<ConfServDetalle> listaDetalle) {
		this.listaDetalle = listaDetalle;
	}
	public List<ConfServCredito> getListaCredito() {
		return listaCredito;
	}
	public void setListaCredito(List<ConfServCredito> listaCredito) {
		this.listaCredito = listaCredito;
	}
	public List<ConfServRol> getListaRol() {
		return listaRol;
	}
	public void setListaRol(List<ConfServRol> listaRol) {
		this.listaRol = listaRol;
	}
	public List<ConfServEstructuraDetalle> getListaEstructuraDetalle() {
		return listaEstructuraDetalle;
	}
	public void setListaEstructuraDetalle(
			List<ConfServEstructuraDetalle> listaEstructuraDetalle) {
		this.listaEstructuraDetalle = listaEstructuraDetalle;
	}
	public List<String> getListaRazonSocialEstructura() {
		return listaRazonSocialEstructura;
	}
	public void setListaRazonSocialEstructura(
			List<String> listaRazonSocialEstructura) {
		this.listaRazonSocialEstructura = listaRazonSocialEstructura;
	}
	
	
	public Boolean getBlnConfigurado() {
		return blnConfigurado;
	}

	public void setBlnConfigurado(Boolean blnConfigurado) {
		this.blnConfigurado = blnConfigurado;
	}

	public Boolean getBlnUsuario() {
		return blnUsuario;
	}

	public void setBlnUsuario(Boolean blnUsuario) {
		this.blnUsuario = blnUsuario;
	}

	public Boolean getBlnPerfil() {
		return blnPerfil;
	}

	public void setBlnPerfil(Boolean blnPerfil) {
		this.blnPerfil = blnPerfil;
	}

	@Override
	public String toString() {
		return "ConfServSolicitud [id=" + id
				+ ", intParaTipoRequertoAutorizaCod="
				+ intParaTipoRequertoAutorizaCod + ", intParaTipoOperacionCod="
				+ intParaTipoOperacionCod + ", intParaSubtipoOperacionCod="
				+ intParaSubtipoOperacionCod + ", dtDesde=" + dtDesde
				+ ", dtHasta=" + dtHasta + ", bdMontoDesde=" + bdMontoDesde
				+ ", bdMontoHasta=" + bdMontoHasta + ", bdCuotaDesde="
				+ bdCuotaDesde + ", bdCuotaHasta=" + bdCuotaHasta
				+ ", intParaEstadoCod=" + intParaEstadoCod
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersPersonaUsuarioPk=" + intPersPersonaUsuarioPk
				+ ", tsFechaEliminacion=" + tsFechaEliminacion
				+ ", intPersPersonaEliminaPk=" + intPersPersonaEliminaPk
				+ ", listaGrupoCta=" + listaGrupoCta + ", listaCreditoEmpresa="
				+ listaCreditoEmpresa + ", listaCancelado=" + listaCancelado
				+ ", listaEstructuraDetalle=" + listaEstructuraDetalle
				+ ", listaCaptacion=" + listaCaptacion + ", listaUsuario="
				+ listaUsuario + ", listaPerfil=" + listaPerfil
				+ ", listaDetalle=" + listaDetalle + ", listaCredito="
				+ listaCredito + ", listaRol=" + listaRol + "]";
	}

	public String getStrDescripcionSubTipo() {
		return strDescripcionSubTipo;
	}

	public void setStrDescripcionSubTipo(String strDescripcionSubTipo) {
		this.strDescripcionSubTipo = strDescripcionSubTipo;
	}


}
