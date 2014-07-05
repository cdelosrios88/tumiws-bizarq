package pe.com.tumi.credito.socio.core.domain;

import java.util.Date;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import java.util.List;

public class SocioComp extends TumiDomain {

	private Persona persona;
	private Socio socio;
	private Padron padron;
	private PersonaRol personaRol;
	private Cuenta cuenta;
	private CuentaComp cuentaComp;
	private String strNombrePersona;
	private Integer intTipoDocIdentidad;
	private String strDocIdentidad;
	private String strTipoSocio;
	private String strSucursal;
	private String strModalidad;
	private Integer intFechaBusq;
	private Date dtFechaDesde;
	private Date dtFechaHasta;
	private Integer intTipoSucBusq;
	private Integer intSucursalBusq;
	private Integer intSubsucursalBusq;
	//private Boolean blnCtasActivasInact;
	private Integer intCtasActivInactiv;
	private String strTipoCuentas;
	private String strRoles;
	private List<CuentaComp> listaCuentaComp;
	
	// 09.09.2013-CGD
	// atributos 
	private String strDescValidaRefinan;
	private Boolean blnEstadoValidaRefinan;
	
	//12.09.2013 - CGD
	//private Integer intItemCreditoGarantia; // usado x motivos de validacion
	//13.09.2013 - CGD
	private CreditoTipoGarantia creditoTipoGarantiaAsignado; //
	//AUTOR Y FECHA CREACION: JCHAVEZ / 02-10-2013
	private Estructura estructura;
	
	
	// CGD - 25.10.2013
	private Integer intBusquedaTipo; 	
	private String strBusqCadena;		    
	private Date dtBusqFechaRegDesde;  
	private Date dtBusqFechaRegHasta;
	private Integer intBusqSituacionCta;
	
	//RODO
	private Integer intCantidadHijos;
	private Date dtFechaIngreso;
	
	//Getters & Setters
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getStrTipoSocio() {
		return strTipoSocio;
	}
	public void setStrTipoSocio(String strTipoSocio) {
		this.strTipoSocio = strTipoSocio;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public String getStrModalidad() {
		return strModalidad;
	}
	public void setStrModalidad(String strModalidad) {
		this.strModalidad = strModalidad;
	}
	public String getStrTipoCuentas() {
		return strTipoCuentas;
	}
	public void setStrTipoCuentas(String strTipoCuentas) {
		this.strTipoCuentas = strTipoCuentas;
	}
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}	public Integer getIntFechaBusq() {
		return intFechaBusq;
	}
	public void setIntFechaBusq(Integer intFechaBusq) {
		this.intFechaBusq = intFechaBusq;
	}
	public Date getDtFechaDesde() {
		return dtFechaDesde;
	}
	public void setDtFechaDesde(Date dtFechaDesde) {
		this.dtFechaDesde = dtFechaDesde;
	}
	public Date getDtFechaHasta() {
		return dtFechaHasta;
	}
	public void setDtFechaHasta(Date dtFechaHasta) {
		this.dtFechaHasta = dtFechaHasta;
	}
	public Integer getIntSubsucursalBusq() {
		return intSubsucursalBusq;
	}
	public void setIntSubsucursalBusq(Integer intSubsucursalBusq) {
		this.intSubsucursalBusq = intSubsucursalBusq;
	}
	public Integer getIntCtasActivInactiv() {
		return intCtasActivInactiv;
	}
	public void setIntCtasActivInactiv(Integer intCtasActivInactiv) {
		this.intCtasActivInactiv = intCtasActivInactiv;
	}
	public Integer getIntSucursalBusq() {
		return intSucursalBusq;
	}
	public void setIntSucursalBusq(Integer intSucursalBusq) {
		this.intSucursalBusq = intSucursalBusq;
	}
	public Integer getIntTipoSucBusq() {
		return intTipoSucBusq;
	}
	public void setIntTipoSucBusq(Integer intTipoSucBusq) {
		this.intTipoSucBusq = intTipoSucBusq;
	}
	public String getStrNombrePersona() {
		return strNombrePersona;
	}
	public void setStrNombrePersona(String strNombrePersona) {
		this.strNombrePersona = strNombrePersona;
	}
	public String getStrDocIdentidad() {
		return strDocIdentidad;
	}
	public void setStrDocIdentidad(String strDocIdentidad) {
		this.strDocIdentidad = strDocIdentidad;
	}
	public Integer getIntTipoDocIdentidad() {
		return intTipoDocIdentidad;
	}
	public void setIntTipoDocIdentidad(Integer intTipoDocIdentidad) {
		this.intTipoDocIdentidad = intTipoDocIdentidad;
	}
	public String getStrRoles() {
		return strRoles;
	}
	public void setStrRoles(String strRoles) {
		this.strRoles = strRoles;
	}
	public PersonaRol getPersonaRol() {
		return personaRol;
	}
	public void setPersonaRol(PersonaRol personaRol) {
		this.personaRol = personaRol;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	public CuentaComp getCuentaComp() {
		return cuentaComp;
	}
	public void setCuentaComp(CuentaComp cuentaComp) {
		this.cuentaComp = cuentaComp;
	}
	public Padron getPadron() {
		return padron;
	}
	public void setPadron(Padron padron) {
		this.padron = padron;
	}
	public List<CuentaComp> getListaCuentaComp() {
		return listaCuentaComp;
	}
	public void setListaCuentaComp(List<CuentaComp> listaCuentaComp) {
		this.listaCuentaComp = listaCuentaComp;
	}
	public String getStrDescValidaRefinan() {
		return strDescValidaRefinan;
	}
	public void setStrDescValidaRefinan(String strDescValidaRefinan) {
		this.strDescValidaRefinan = strDescValidaRefinan;
	}
	public Boolean getBlnEstadoValidaRefinan() {
		return blnEstadoValidaRefinan;
	}
	public void setBlnEstadoValidaRefinan(Boolean blnEstadoValidaRefinan) {
		this.blnEstadoValidaRefinan = blnEstadoValidaRefinan;
	}
	public CreditoTipoGarantia getCreditoTipoGarantiaAsignado() {
		return creditoTipoGarantiaAsignado;
	}
	public void setCreditoTipoGarantiaAsignado(
			CreditoTipoGarantia creditoTipoGarantiaAsignado) {
		this.creditoTipoGarantiaAsignado = creditoTipoGarantiaAsignado;
	}
	public Estructura getEstructura() {
		return estructura;
	}
	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}
	public Integer getIntBusquedaTipo() {
		return intBusquedaTipo;
	}
	public void setIntBusquedaTipo(Integer intBusquedaTipo) {
		this.intBusquedaTipo = intBusquedaTipo;
	}
	public String getStrBusqCadena() {
		return strBusqCadena;
	}
	public void setStrBusqCadena(String strBusqCadena) {
		this.strBusqCadena = strBusqCadena;
	}

	public Integer getIntBusqSituacionCta() {
		return intBusqSituacionCta;
	}
	public void setIntBusqSituacionCta(Integer intBusqSituacionCta) {
		this.intBusqSituacionCta = intBusqSituacionCta;
	}
	public Date getDtBusqFechaRegDesde() {
		return dtBusqFechaRegDesde;
	}
	public void setDtBusqFechaRegDesde(Date dtBusqFechaRegDesde) {
		this.dtBusqFechaRegDesde = dtBusqFechaRegDesde;
	}
	public Date getDtBusqFechaRegHasta() {
		return dtBusqFechaRegHasta;
	}
	public void setDtBusqFechaRegHasta(Date dtBusqFechaRegHasta) {
		this.dtBusqFechaRegHasta = dtBusqFechaRegHasta;
	}
	//setter y getter agregados por rvillarreal
	public void setIntCantidadHijos(Integer intCantidadHijos) {
		this.intCantidadHijos = intCantidadHijos;
	}
	public Integer getIntCantidadHijos() {
		return intCantidadHijos;
	}
	public void setDtFechaIngreso(Date dtFechaIngreso) {
		this.dtFechaIngreso = dtFechaIngreso;
	}
	public Date getDtFechaIngreso() {
		return dtFechaIngreso;
	}
}
