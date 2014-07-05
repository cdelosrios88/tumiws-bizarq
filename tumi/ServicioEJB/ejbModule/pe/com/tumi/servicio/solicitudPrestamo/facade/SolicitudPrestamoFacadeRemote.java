package pe.com.tumi.servicio.solicitudPrestamo.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacion;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;

@Remote
public interface SolicitudPrestamoFacadeRemote {
	public List<ExpedienteCreditoComp> getListaExpedienteCreditoCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException;
	public List<ExpedienteCreditoComp> getListaAutorizacionCreditoCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException;
	public ExpedienteCredito grabarExpedienteCredito(ExpedienteCredito o) throws BusinessException;
	public ExpedienteCredito modificarExpedienteCredito(ExpedienteCredito o) throws BusinessException;
	public ExpedienteCredito grabarAutorizacionPrestamo(ExpedienteCredito o) throws BusinessException;
	public ExpedienteCredito getSolicitudPrestamoPorIdExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException;
	public Integer getCantidadPersonasAseguradasPorPkPersona(Integer idPersona) throws BusinessException;
	public List<CapacidadCredito> getListaCapacidadCreditoPorPkExpedienteYSocioEstructura(ExpedienteCreditoId pId,SocioEstructuraPK pIdSocio) throws BusinessException;
	public List<AutorizaCredito> getListaAutorizaCreditoPorPkExpediente(ExpedienteCreditoId pId) throws BusinessException;
	public List<GarantiaCredito> getListaGarantiasPorPkPersona(Integer intPersEmpresaPk, Integer idPersona) throws BusinessException;
	public LibroDiario generarLibroDiarioPrestamo(ExpedienteCredito expedienteCredito) throws BusinessException;
	public LibroDiario generarLibroDiarioAnulacion(ExpedienteCredito expedienteCredito) throws BusinessException;
	public List<AutorizaVerificacion> getListaVerifificacionesCreditoPorPkExpediente(ExpedienteCreditoId pId) throws BusinessException;	
	public EstadoCredito ultimoEstadoCredito(ExpedienteCredito expedienteCredito) throws BusinessException;
	public List<ExpedienteCredito> getListaExpedienteCreditoPorCuenta(Cuenta o) throws BusinessException;
	public List<GarantiaCredito> getListaGarantiaCreditoPorId(ExpedienteCreditoId o) throws BusinessException;
	public ExpedienteCredito grabarExpedienteRefinanciamiento(ExpedienteCredito o) throws BusinessException;
	public List<ExpedienteCreditoComp> getListaExpedienteRefinanciamientoCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException;
	public List<ExpedienteCredito> getListaPorExpediente(ExpedienteCredito o) throws BusinessException;
	
	//public LibroDiario generarLibroDiarioRefinanciamiento(ExpedienteCredito expedienteCredito) throws BusinessException;
	//public void getDatosDeCartera(Integer empresa)throws BusinessException;
	//public void generarAsientoRefinanciamiento()throws BusinessException;
	 //public void generarSolicitudCtaCte(SocioComp socioComp,Usuario usuario, String strPeriodo, RequisitoCredito requisitoCred, Expediente expedientePorRefinanciar )throws BusinessException;
	 public RequisitoCredito recuperarRequisitoRefinanciamiento(ExpedienteCredito expediente) throws BusinessException;
	 /**
	  * Genera el coronograma de movimiento. 
	  * Recuperando el cronograma desde servicio.
	  * @param expedienteId
	  * @return
	  * @throws BusinessException
	  */
	 public List<Cronograma> generarCronogramaMovimiento(ExpedienteCreditoId expedienteId) throws BusinessException;
	/* public LibroDiario generarProcesoAutorizacionRefinanciamientoTotal(ExpedienteCredito expedienteCredito,Integer intParaEstadoAprobado, Integer intParaTipoOperacion,
				ActionEvent event, Usuario usuario, SocioComp beanSocioComp, ExpedienteCreditoComp expedienteCreditoCompSelected,
				String strPeriodo, RequisitoCredito requisitoCred, Expediente expedienteMov)throws BusinessException;*/
	 public EstadoCredito grabarEstadoAutorizadoRefinan(EstadoCredito estado)throws BusinessException;
	 public Integer getNumeroCreditosGarantizadosPorPersona(Integer intPersEmpresaPk, Integer idPersona)throws BusinessException;
	 public Expediente generarExpedienteMovimiento(ExpedienteCreditoComp expedienteCreditoCompSelected) throws BusinessException;
	 public List<EstadoCredito> getListaEstadosPorExpedienteCreditoId(ExpedienteCreditoId pId)throws BusinessException;
	 public List<ExpedienteCreditoComp> getListaExpedienteActividadCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException;
	 public ExpedienteCredito getExpedienteActividadCompletoPorIdExpedienteActividad(ExpedienteCreditoId pId) throws BusinessException;
	 public List<ExpedienteCreditoComp> getListaExpedienteActividadCompPorAutorizar(ExpedienteCreditoComp o) throws BusinessException;
	// public LibroDiario generarLibroDiarioActividad(ExpedienteCredito expedienteCredito, Usuario usuario) throws BusinessException;
	public List<ExpedienteCreditoComp> getListaExpedienteCreditoCompDeBusquedaFiltro(Integer intTipoConsultaBusqueda, String strConsultaBusqueda, 
				String strNumeroSolicitudBusq, EstadoCredito estadoCreditoBusqueda, ExpedienteCredito expCreditoBusqueda) throws BusinessException;
	public List<ExpedienteCredito> getListaExpedienteCreditoPorCuentaYUltimoEstado(Cuenta cuenta, Integer intEstadoSol) 
	throws BusinessException;
	public List<ExpedienteCreditoComp> getListaExpedienteCreditoAutorizacionCompDeBusquedaFiltro(Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
			String strNumeroSolicitudBusq,EstadoCredito estadoCondicionFiltro, Integer intTipoCreditoFiltro, EstadoCredito estadoLiquidacionFechas,	EstadoCredito estadoLiquidacionSuc,
			Integer intIdSubsucursalFiltro) throws BusinessException;
	public void eliminarVerificaAutorizacionAdjuntosPorObservacion(ExpedienteCredito pExpedienteCredito,Integer intTipoCredito, Integer intSubTipo) throws BusinessException;
	public List<AutorizaCredito> getListaVerificaCreditoPorPkExpediente(ExpedienteCreditoId pId) throws BusinessException;
	public LibroDiario generarProcesosAutorizacionActividad(ExpedienteCredito expedienteCredito, Usuario usuario) throws BusinessException;
	
	/**
	 * Cobranza enviodeplanilla capacidad de credito
	 * 
	 */
	public List<CapacidadCredito> getListaPorPkExpedienteCredito(ExpedienteCreditoId pId)throws BusinessException;
	
	//CGD-13.01.2014
	public String generarProcesosAutorizacionRefinanciamiento(ExpedienteCredito expedienteCredito, ExpedienteCredito expedienteCreditoAut, Usuario usuario, SocioComp beanSocioComp)throws BusinessException;
	//public String generarProcesosAutorizacionRefinanciamiento(ExpedienteCredito expedienteCredito, ExpedienteCredito expedienteCreditoAut, Usuario usuario)throws BusinessException;
	public List<ExpedienteComp> recuperarDetallesCreditoRefinanciado(ExpedienteCreditoComp registroSeleccionadoBusqueda) throws BusinessException;
	//public List<ExpedienteCreditoComp> getBuscarAutRefinanciamientoCompFiltros(Integer intBusqTipo,String strBusqCadena,String strBusqNroSol,Integer intBusqSucursal,
	//		Integer intBusqEstado,Date dtBusqFechaEstadoDesde,Date dtBusqFechaEstadoHasta) throws BusinessException;
	public List<ExpedienteCreditoComp> getListaBusqCreditoFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException;
	public List<ExpedienteCreditoComp> getListaBusqCreditosAutFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException;
	public List<ExpedienteCreditoComp> getListaBusqRefinanFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException;
	//public List<ExpedienteCreditoComp> getBuscarAutRefinanciamientoCompFiltros(ExpedienteCreditoComp expedienteCompBusq) throws BusinessException;
	public List<ExpedienteCreditoComp> getListaBusqAutRefFiltros(ExpedienteCreditoComp expedienteCompBusq) throws BusinessException;
	public List<ExpedienteCreditoComp> getListaBusqActividadFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException;
	public List<ExpedienteCreditoComp> getListaBusqActAutFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException;
	public List<ExpedienteCredito> getMaxExpedienteRefinan(Integer intPersEmpresaPk, Integer intCuenta, Integer intItemExpediente)throws BusinessException;
}