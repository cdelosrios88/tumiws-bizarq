package pe.com.tumi.servicio.prevision.domain.composite;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;

public class ExpedientePrevisionComp extends TumiDomain{

	private ExpedientePrevision expedientePrevision;
	private List<ExpedientePrevision> listaExpedientePrevision;
	private EstadoPrevision estadoPrevision;
	private EstadoPrevision primerEstado;
	private SocioComp socioComp;
	private Persona persona;
	private Integer intIdTipoPersona;
	private Integer intBusqSolicPtmo;
	private String strNroSolicitud;
	private Integer intTipoSolicitud; 
	private Integer intSubTipoSolicitud;
	
	private Integer intTipoSucursalBusq;
	private Integer intTotalSucursales;
	private Integer intIdEstadoSolicitud;
	//private Integer intIdDependencia;
	//private Integer intIdTipoPrestamo;
	//private Integer intIdEstadoPrestamo;
	private Date dtFechaInicio;
	private Date dtFechaFin;
	
	private String strUserRegistro;
	private String strFechaUserRegistro;
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 23-09-2013
	private List<FallecidoPrevision> fallecidoPrevision;
	private List<BeneficiarioPrevision> beneficiarioPrevision;
	private String strDescripcionTipoBeneficio;
	private BigDecimal bdMontoNeto;
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 24-09-2013
	private Integer intTipoVinculoFallecido;
	private String  strNomApeFallecido;
	private Integer intTipoVinculoBeneficiario;
	private String strNomApeBeneficiario;

	
	// CGD 01.10.2013
	private Integer intBusquedaTipo; 	
	private String strBusqCadena;		    
	private String strBusqNroSol;		   
	private Integer intBusqSucursal;
	private Integer intBusqSubSucursal;
	private Integer intBusqEstado;	
	private Date dtBusqFechaEstadoDesde;  
	private Date dtBusqFechaEstadoHasta;
	private Integer intBusqTipoPrevision;
	private Integer intBusqSubTipoPrevision;
	
	private String strFechaRequisito;
	private String strFechaSolicitud;
	private String strFechaAutorizacion;
	
	
	
	public EstadoPrevision getPrimerEstado() {
		return primerEstado;
	}
	public void setPrimerEstado(EstadoPrevision primerEstado) {
		this.primerEstado = primerEstado;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public ExpedientePrevision getExpedientePrevision() {
		return expedientePrevision;
	}
	public void setExpedientePrevision(ExpedientePrevision expedientePrevision) {
		this.expedientePrevision = expedientePrevision;
	}
	public List<ExpedientePrevision> getListaExpedientePrevision() {
		return listaExpedientePrevision;
	}
	public void setListaExpedientePrevision(
			List<ExpedientePrevision> listaExpedientePrevision) {
		this.listaExpedientePrevision = listaExpedientePrevision;
	}
	public EstadoPrevision getEstadoPrevision() {
		return estadoPrevision;
	}
	public void setEstadoPrevision(EstadoPrevision estadoPrevision) {
		this.estadoPrevision = estadoPrevision;
	}
	public SocioComp getSocioComp() {
		return socioComp;
	}
	public void setSocioComp(SocioComp socioComp) {
		this.socioComp = socioComp;
	}
	public Integer getIntIdTipoPersona() {
		return intIdTipoPersona;
	}
	public void setIntIdTipoPersona(Integer intIdTipoPersona) {
		this.intIdTipoPersona = intIdTipoPersona;
	}
	public Integer getIntBusqSolicPtmo() {
		return intBusqSolicPtmo;
	}
	public void setIntBusqSolicPtmo(Integer intBusqSolicPtmo) {
		this.intBusqSolicPtmo = intBusqSolicPtmo;
	}
	public String getStrNroSolicitud() {
		return strNroSolicitud;
	}
	public void setStrNroSolicitud(String strNroSolicitud) {
		this.strNroSolicitud = strNroSolicitud;
	}
	public Integer getIntTipoSolicitud() {
		return intTipoSolicitud;
	}
	public void setIntTipoSolicitud(Integer intTipoSolicitud) {
		this.intTipoSolicitud = intTipoSolicitud;
	}
	public Integer getIntSubTipoSolicitud() {
		return intSubTipoSolicitud;
	}
	public void setIntSubTipoSolicitud(Integer intSubTipoSolicitud) {
		this.intSubTipoSolicitud = intSubTipoSolicitud;
	}
	public Integer getIntTipoSucursalBusq() {
		return intTipoSucursalBusq;
	}
	public void setIntTipoSucursalBusq(Integer intTipoSucursalBusq) {
		this.intTipoSucursalBusq = intTipoSucursalBusq;
	}
	public Integer getIntTotalSucursales() {
		return intTotalSucursales;
	}
	public void setIntTotalSucursales(Integer intTotalSucursales) {
		this.intTotalSucursales = intTotalSucursales;
	}
	public Integer getIntIdEstadoSolicitud() {
		return intIdEstadoSolicitud;
	}
	public void setIntIdEstadoSolicitud(Integer intIdEstadoSolicitud) {
		this.intIdEstadoSolicitud = intIdEstadoSolicitud;
	}
	public Date getDtFechaInicio() {
		return dtFechaInicio;
	}
	public void setDtFechaInicio(Date dtFechaInicio) {
		this.dtFechaInicio = dtFechaInicio;
	}
	public Date getDtFechaFin() {
		return dtFechaFin;
	}
	public void setDtFechaFin(Date dtFechaFin) {
		this.dtFechaFin = dtFechaFin;
	}
	public String getStrUserRegistro() {
		return strUserRegistro;
	}
	public void setStrUserRegistro(String strUserRegistro) {
		this.strUserRegistro = strUserRegistro;
	}
	public String getStrFechaUserRegistro() {
		return strFechaUserRegistro;
	}
	public void setStrFechaUserRegistro(String strFechaUserRegistro) {
		this.strFechaUserRegistro = strFechaUserRegistro;
	}
	public String getStrFechaSolicitud() {
		return strFechaSolicitud;
	}
	public void setStrFechaSolicitud(String strFechaSolicitud) {
		this.strFechaSolicitud = strFechaSolicitud;
	}
	public String getStrFechaAutorizacion() {
		return strFechaAutorizacion;
	}
	public void setStrFechaAutorizacion(String strFechaAutorizacion) {
		this.strFechaAutorizacion = strFechaAutorizacion;
	}
	public List<FallecidoPrevision> getFallecidoPrevision() {
		return fallecidoPrevision;
	}
	public void setFallecidoPrevision(List<FallecidoPrevision> fallecidoPrevision) {
		this.fallecidoPrevision = fallecidoPrevision;
	}
	public List<BeneficiarioPrevision> getBeneficiarioPrevision() {
		return beneficiarioPrevision;
	}
	public void setBeneficiarioPrevision(
			List<BeneficiarioPrevision> beneficiarioPrevision) {
		this.beneficiarioPrevision = beneficiarioPrevision;
	}
	public String getStrDescripcionTipoBeneficio() {
		return strDescripcionTipoBeneficio;
	}
	public void setStrDescripcionTipoBeneficio(String strDescripcionTipoBeneficio) {
		this.strDescripcionTipoBeneficio = strDescripcionTipoBeneficio;
	}
	public BigDecimal getBdMontoNeto() {
		return bdMontoNeto;
	}
	public void setBdMontoNeto(BigDecimal bdMontoNeto) {
		this.bdMontoNeto = bdMontoNeto;
	}
	public Integer getIntTipoVinculoFallecido() {
		return intTipoVinculoFallecido;
	}
	public void setIntTipoVinculoFallecido(Integer intTipoVinculoFallecido) {
		this.intTipoVinculoFallecido = intTipoVinculoFallecido;
	}
	public String getStrNomApeFallecido() {
		return strNomApeFallecido;
	}
	public void setStrNomApeFallecido(String strNomApeFallecido) {
		this.strNomApeFallecido = strNomApeFallecido;
	}
	public Integer getIntTipoVinculoBeneficiario() {
		return intTipoVinculoBeneficiario;
	}
	public void setIntTipoVinculoBeneficiario(Integer intTipoVinculoBeneficiario) {
		this.intTipoVinculoBeneficiario = intTipoVinculoBeneficiario;
	}
	public String getStrNomApeBeneficiario() {
		return strNomApeBeneficiario;
	}
	public void setStrNomApeBeneficiario(String strNomApeBeneficiario) {
		this.strNomApeBeneficiario = strNomApeBeneficiario;
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
	public String getStrBusqNroSol() {
		return strBusqNroSol;
	}
	public void setStrBusqNroSol(String strBusqNroSol) {
		this.strBusqNroSol = strBusqNroSol;
	}
	public Integer getIntBusqSucursal() {
		return intBusqSucursal;
	}
	public void setIntBusqSucursal(Integer intBusqSucursal) {
		this.intBusqSucursal = intBusqSucursal;
	}
	public Integer getIntBusqSubSucursal() {
		return intBusqSubSucursal;
	}
	public void setIntBusqSubSucursal(Integer intBusqSubSucursal) {
		this.intBusqSubSucursal = intBusqSubSucursal;
	}
	public Integer getIntBusqEstado() {
		return intBusqEstado;
	}
	public void setIntBusqEstado(Integer intBusqEstado) {
		this.intBusqEstado = intBusqEstado;
	}
	public Date getDtBusqFechaEstadoDesde() {
		return dtBusqFechaEstadoDesde;
	}
	public void setDtBusqFechaEstadoDesde(Date dtBusqFechaEstadoDesde) {
		this.dtBusqFechaEstadoDesde = dtBusqFechaEstadoDesde;
	}
	public Date getDtBusqFechaEstadoHasta() {
		return dtBusqFechaEstadoHasta;
	}
	public void setDtBusqFechaEstadoHasta(Date dtBusqFechaEstadoHasta) {
		this.dtBusqFechaEstadoHasta = dtBusqFechaEstadoHasta;
	}
	public Integer getIntBusqTipoPrevision() {
		return intBusqTipoPrevision;
	}
	public void setIntBusqTipoPrevision(Integer intBusqTipoPrevision) {
		this.intBusqTipoPrevision = intBusqTipoPrevision;
	}
	public Integer getIntBusqSubTipoPrevision() {
		return intBusqSubTipoPrevision;
	}
	public void setIntBusqSubTipoPrevision(Integer intBusqSubTipoPrevision) {
		this.intBusqSubTipoPrevision = intBusqSubTipoPrevision;
	}
	public String getStrFechaRequisito() {
		return strFechaRequisito;
	}
	public void setStrFechaRequisito(String strFechaRequisito) {
		this.strFechaRequisito = strFechaRequisito;
	}
	
}
