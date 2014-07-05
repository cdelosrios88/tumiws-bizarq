package pe.com.tumi.credito.socio.estructura.domain;


import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class AdminPadron extends TumiDomain {

	private AdminPadronId id;
	private Estructura estructura;
	//private Integer intParaTipoArchivoCod;
	//private Integer intMaeItemArchivo;
	private Integer intParaTipoPadronCod;
	private Integer intParaEstadoCod;
	private Date adPaFechaEliminacion;
	private Integer intPersEmpresaPk;
	private Integer intPersUsuarioRegistroPk;
	private Date adPaFechaRegistro;
	private Integer intParaTipoArchivo;
	private Integer intParaItemArchivo;
	private Integer intParaItemHistorico;
	private SolicitudPagoDetalle solicitudPagoDetalle;
	private Persona persona;
	
	public AdminPadron(){
		id = new AdminPadronId();
		solicitudPagoDetalle = new SolicitudPagoDetalle();
		estructura = new Estructura();
		EstructuraId estructuraId = new EstructuraId();
		estructura.setJuridica(new Juridica());
		estructura.setId(estructuraId);
		persona = new Persona();
		persona.setJuridica(new Juridica());
		persona.setNatural(new Natural());
	}
	
	public AdminPadronId getId() {
		return id;
	}
	public void setId(AdminPadronId id) {
		this.id = id;
	}
/*	public Integer getIntParaTipoArchivoCod() {
		return intParaTipoArchivoCod;
	}
	public void setIntParaTipoArchivoCod(Integer intParaTipoArchivoCod) {
		this.intParaTipoArchivoCod = intParaTipoArchivoCod;
	}*/
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
/*	public Integer getIntMaeItemArchivo() {
		return intMaeItemArchivo;
	}
	public void setIntMaeItemArchivo(Integer intMaeItemArchivo) {
		this.intMaeItemArchivo = intMaeItemArchivo;
	}*/
	public Date getAdPaFechaEliminacion() {
		return adPaFechaEliminacion;
	}
	public void setAdPaFechaEliminacion(Date adPaFechaEliminacion) {
		this.adPaFechaEliminacion = adPaFechaEliminacion;
	}
	public Date getAdPaFechaRegistro() {
		return adPaFechaRegistro;
	}
	public void setAdPaFechaRegistro(Date adPaFechaRegistro) {
		this.adPaFechaRegistro = adPaFechaRegistro;
	}

	public Integer getIntParaTipoPadronCod() {
		return intParaTipoPadronCod;
	}

	public void setIntParaTipoPadronCod(Integer intParaTipoPadronCod) {
		this.intParaTipoPadronCod = intParaTipoPadronCod;
	}

	public Integer getIntPersUsuarioRegistroPk() {
		return intPersUsuarioRegistroPk;
	}

	public void setIntPersUsuarioRegistroPk(Integer intPersUsuarioRegistroPk) {
		this.intPersUsuarioRegistroPk = intPersUsuarioRegistroPk;
	}	

	public Estructura getEstructura() {
		return estructura;
	}

	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}	

	public SolicitudPagoDetalle getSolicitudPagoDetalle() {
		return solicitudPagoDetalle;
	}

	public void setSolicitudPagoDetalle(SolicitudPagoDetalle solicitudPagoDetalle) {
		this.solicitudPagoDetalle = solicitudPagoDetalle;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Integer getIntParaTipoArchivo() {
		return intParaTipoArchivo;
	}

	public void setIntParaTipoArchivo(Integer intParaTipoArchivo) {
		this.intParaTipoArchivo = intParaTipoArchivo;
	}

	public Integer getIntParaItemArchivo() {
		return intParaItemArchivo;
	}

	public void setIntParaItemArchivo(Integer intParaItemArchivo) {
		this.intParaItemArchivo = intParaItemArchivo;
	}

	public Integer getIntParaItemHistorico() {
		return intParaItemHistorico;
	}

	public void setIntParaItemHistorico(Integer intParaItemHistorico) {
		this.intParaItemHistorico = intParaItemHistorico;
	}

	@Override
	public String toString() {
		return "AdminPadron [id=" + id + ", estructura=" + estructura
				+ ", intParaTipoPadronCod=" + intParaTipoPadronCod
				+ ", intParaEstadoCod=" + intParaEstadoCod
				+ ", adPaFechaEliminacion=" + adPaFechaEliminacion
				+ ", intPersEmpresaPk=" + intPersEmpresaPk
				+ ", intPersUsuarioRegistroPk=" + intPersUsuarioRegistroPk
				+ ", adPaFechaRegistro=" + adPaFechaRegistro
				+ ", intParaTipoArchivo=" + intParaTipoArchivo
				+ ", intParaItemArchivo=" + intParaItemArchivo
				+ ", intParaItemHistorico=" + intParaItemHistorico
				+ ", solicitudPagoDetalle=" + solicitudPagoDetalle
				+ ", persona=" + persona + "]";
	}

	
}