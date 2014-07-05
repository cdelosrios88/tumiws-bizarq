package pe.com.tumi.servicio.prevision.domain;

import java.util.Date;
import java.util.List;

import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ExpedienteLiquidacionComp extends TumiDomain{
	private ExpedienteLiquidacion expedienteLiquidacion;
	private List<ExpedienteLiquidacion> listaExpedienteLiquidacion;
	private EstadoLiquidacion estadoLiquidacion;
	private SocioComp socioComp;
	private Integer intIdTipoPersona;
	private Integer intBusqSolicLiqui;
	private String strNroSolicitud;
	private Integer intTipoSucursalBusq;
	private Integer intTotalSucursales;
	private Integer intIdEstadoSolicitud;
	private Integer intIdDependencia;
	private Integer intIdTipoPrestamo;
	private Integer intIdEstadoLiquidacion;
	private Date dtFechaInicio;
	private Date dtFechaFin;
	
	private String strFechaRequisito;
	private String strFechaSolicitud;
	private String strFechaAutorizacion;
	private String strFechaAprobado;
	private String strFechaGirado;

	private Credito credito;
	private String strDescripcionExpedienteLiquidacion;

	private String strNroSolicitudBusqueda; // 21-1
	private Integer intParaCreditoEmpresa;
	
	
	// CGD 24.10.2013
	private Integer intBusquedaTipo; 	
	private String strBusqCadena;		    
	private String strBusqNroSol;		   
	private Integer intBusqSucursal;
	private Integer intBusqEstado;	
	private Date dtBusqFechaEstadoDesde;  
	private Date dtBusqFechaEstadoHasta;
	private Integer intBusqTipoLiquidacion;
	
	
	public ExpedienteLiquidacion getExpedienteLiquidacion() {
		return expedienteLiquidacion;
	}
	public void setExpedienteLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) {
		this.expedienteLiquidacion = expedienteLiquidacion;
	}
	public List<ExpedienteLiquidacion> getListaExpedienteLiquidacion() {
		return listaExpedienteLiquidacion;
	}
	public void setListaExpedienteLiquidacion(
			List<ExpedienteLiquidacion> listaExpedienteLiquidacion) {
		this.listaExpedienteLiquidacion = listaExpedienteLiquidacion;
	}
	public EstadoLiquidacion getEstadoLiquidacion() {
		return estadoLiquidacion;
	}
	public void setEstadoLiquidacion(EstadoLiquidacion estadoLiquidacion) {
		this.estadoLiquidacion = estadoLiquidacion;
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
	public Integer getIntBusqSolicLiqui() {
		return intBusqSolicLiqui;
	}
	public void setIntBusqSolicLiqui(Integer intBusqSolicLiqui) {
		this.intBusqSolicLiqui = intBusqSolicLiqui;
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
	public Integer getIntIdEstadoLiquidacion() {
		return intIdEstadoLiquidacion;
	}
	public void setIntIdEstadoLiquidacion(Integer intIdEstadoLiquidacion) {
		this.intIdEstadoLiquidacion = intIdEstadoLiquidacion;
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
	public String getStrFechaAprobado() {
		return strFechaAprobado;
	}
	public void setStrFechaAprobado(String strFechaAprobado) {
		this.strFechaAprobado = strFechaAprobado;
	}
	public String getStrFechaGirado() {
		return strFechaGirado;
	}
	public void setStrFechaGirado(String strFechaGirado) {
		this.strFechaGirado = strFechaGirado;
	}
	public Credito getCredito() {
		return credito;
	}
	public void setCredito(Credito credito) {
		this.credito = credito;
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
	public String getStrDescripcionExpedienteLiquidacion() {
		return strDescripcionExpedienteLiquidacion;
	}
	public void setStrDescripcionExpedienteLiquidacion(
			String strDescripcionExpedienteLiquidacion) {
		this.strDescripcionExpedienteLiquidacion = strDescripcionExpedienteLiquidacion;
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
	public Integer getIntBusqTipoLiquidacion() {
		return intBusqTipoLiquidacion;
	}
	public void setIntBusqTipoLiquidacion(Integer intBusqTipoLiquidacion) {
		this.intBusqTipoLiquidacion = intBusqTipoLiquidacion;
	}
	

}
