package pe.com.tumi.servicio.solicitudPrestamo.domain.composite;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteActividad;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;

public class ExpedienteCreditoComp extends TumiDomain {
	private ExpedienteCredito expedienteCredito;
	private List<ExpedienteCredito> listaExpedienteCredito;
	private EstadoCredito estadoCredito;
	private SocioComp socioComp;
	private Integer intIdTipoPersona;
	private Integer intBusqSolicPtmo;
	private String strNroSolicitud;
	private Integer intTipoSucursalBusq;
	private Integer intTotalSucursales;
	private Integer intIdEstadoSolicitud;
	private Integer intIdDependencia;
	private Integer intIdTipoPrestamo;
	private Integer intIdEstadoPrestamo;
	private Date dtFechaInicio;
	private Date dtFechaFin;
	
	private String strFechaRequisito;
	private String strFechaSolicitud;
	private String strFechaAutorizacion;
	
	private BigDecimal bdCuotaFija;
	
	private Expediente expedientePorRefinanciar;
	private Credito credito;
	private String strDescripcionExpedienteCredito;
	
	private String strDescripcionActividad;
	private String strDescripcionSubActividad;
	
	private ExpedienteActividad actividad;
	private String strNroSolicitudBusqueda; // 21-1
	private Integer intParaCreditoEmpresa;
	
	
	// Se utilizo para mostrar el nro de cuenta de algun prestamo o solicutd, ya que strNumeroCuenta se muestra la cuenta actual.
	private String strNumeroCuentaSegunCaso;
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 17-09-2013
	private String strCuotaMensual;
	private BigDecimal bdCuotaMensual;
	
	// CGD-27.09.2013
	//Utilizado como filtro de la grilla de busqueda de sol de credito.
	private Integer intBusqTipo;
	private String strBusqCadena;
	private String strBusqNroSol;
	private Integer intBusqSucursal;
	private Integer intBusqEstado;
	private Date dtBusqFechaEstadoDesde;
	private Date dtBusqFechaEstadoHasta;
	private Integer intBusqTipoCreditoEmpresa;
	private Integer intBusqItemCredito;
	
	// CGD - 03.10.2013
	private Boolean blnLinkDetalle;
	
	public ExpedienteCredito getExpedienteCredito() {
		return expedienteCredito;
	}
	public void setExpedienteCredito(ExpedienteCredito expedienteCredito) {
		this.expedienteCredito = expedienteCredito;
	}
	public List<ExpedienteCredito> getListaExpedienteCredito() {
		return listaExpedienteCredito;
	}
	public void setListaExpedienteCredito(
			List<ExpedienteCredito> listaExpedienteCredito) {
		this.listaExpedienteCredito = listaExpedienteCredito;
	}
	public EstadoCredito getEstadoCredito() {
		return estadoCredito;
	}
	public void setEstadoCredito(EstadoCredito estadoCredito) {
		this.estadoCredito = estadoCredito;
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
	public Integer getIntIdDependencia() {
		return intIdDependencia;
	}
	public void setIntIdDependencia(Integer intIdDependencia) {
		this.intIdDependencia = intIdDependencia;
	}
	public Integer getIntIdTipoPrestamo() {
		return intIdTipoPrestamo;
	}
	public void setIntIdTipoPrestamo(Integer intIdTipoPrestamo) {
		this.intIdTipoPrestamo = intIdTipoPrestamo;
	}
	public Integer getIntIdEstadoPrestamo() {
		return intIdEstadoPrestamo;
	}
	public void setIntIdEstadoPrestamo(Integer intIdEstadoPrestamo) {
		this.intIdEstadoPrestamo = intIdEstadoPrestamo;
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
	public String getStrFechaRequisito() {
		return strFechaRequisito;
	}
	public void setStrFechaRequisito(String strFechaRequisito) {
		this.strFechaRequisito = strFechaRequisito;
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
	public BigDecimal getBdCuotaFija() {
		return bdCuotaFija;
	}
	public void setBdCuotaFija(BigDecimal bdCuotaFija) {
		this.bdCuotaFija = bdCuotaFija;
	}
	public Expediente getExpedientePorRefinanciar() {
		return expedientePorRefinanciar;
	}
	public void setExpedientePorRefinanciar(Expediente expedientePorRefinanciar) {
		this.expedientePorRefinanciar = expedientePorRefinanciar;
	}
	public Credito getCredito() {
		return credito;
	}
	public void setCredito(Credito credito) {
		this.credito = credito;
	}
	public String getStrDescripcionExpedienteCredito() {
		return strDescripcionExpedienteCredito;
	}
	public void setStrDescripcionExpedienteCredito(
			String strDescripcionExpedienteCredito) {
		this.strDescripcionExpedienteCredito = strDescripcionExpedienteCredito;
	}
	public String getStrDescripcionActividad() {
		return strDescripcionActividad;
	}
	public void setStrDescripcionActividad(String strDescripcionActividad) {
		this.strDescripcionActividad = strDescripcionActividad;
	}
	public String getStrDescripcionSubActividad() {
		return strDescripcionSubActividad;
	}
	public void setStrDescripcionSubActividad(String strDescripcionSubActividad) {
		this.strDescripcionSubActividad = strDescripcionSubActividad;
	}
	public ExpedienteActividad getActividad() {
		return actividad;
	}
	public void setActividad(ExpedienteActividad actividad) {
		this.actividad = actividad;
	}
	public String getStrNroSolicitudBusqueda() {
		return strNroSolicitudBusqueda;
	}
	public void setStrNroSolicitudBusqueda(String strNroSolicitudBusqueda) {
		this.strNroSolicitudBusqueda = strNroSolicitudBusqueda;
	}
	public Integer getIntParaCreditoEmpresa() {
		return intParaCreditoEmpresa;
	}
	public void setIntParaCreditoEmpresa(Integer intParaCreditoEmpresa) {
		this.intParaCreditoEmpresa = intParaCreditoEmpresa;
	}
	public String getStrNumeroCuentaSegunCaso() {
		return strNumeroCuentaSegunCaso;
	}
	public void setStrNumeroCuentaSegunCaso(String strNumeroCuentaSegunCaso) {
		this.strNumeroCuentaSegunCaso = strNumeroCuentaSegunCaso;
	}
	public String getStrCuotaMensual() {
		return strCuotaMensual;
	}
	public void setStrCuotaMensual(String strCuotaMensual) {
		this.strCuotaMensual = strCuotaMensual;
	}
	public Integer getIntBusqTipo() {
		return intBusqTipo;
	}
	public void setIntBusqTipo(Integer intBusqTipo) {
		this.intBusqTipo = intBusqTipo;
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
	public BigDecimal getBdCuotaMensual() {
		return bdCuotaMensual;
	}
	public void setBdCuotaMensual(BigDecimal bdCuotaMensual) {
		this.bdCuotaMensual = bdCuotaMensual;
	}
	public Boolean getBlnLinkDetalle() {
		return blnLinkDetalle;
	}
	public void setBlnLinkDetalle(Boolean blnLinkDetalle) {
		this.blnLinkDetalle = blnLinkDetalle;
	}
	public Integer getIntBusqTipoCreditoEmpresa() {
		return intBusqTipoCreditoEmpresa;
	}
	public void setIntBusqTipoCreditoEmpresa(Integer intBusqTipoCreditoEmpresa) {
		this.intBusqTipoCreditoEmpresa = intBusqTipoCreditoEmpresa;
	}
	public Integer getIntBusqItemCredito() {
		return intBusqItemCredito;
	}
	public void setIntBusqItemCredito(Integer intBusqItemCredito) {
		this.intBusqItemCredito = intBusqItemCredito;
	}
}