package pe.com.tumi.servicio.prevision.facade;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionComp;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;
//import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp2;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

@Remote
public interface LiquidacionFacadeRemote {
	public List<ExpedienteLiquidacion> buscarExpedienteParaGiro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoLiquidacion estadoLiquidacionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException;
	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacionPorExpedienteLiquidacionDetalle(
			ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle) throws BusinessException;
	public ExpedienteLiquidacion grabarGiroLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException;
	public List<ExpedienteLiquidacion> buscarExpedienteParaGiroDesdeFondo(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle, Integer intIdBeneficiario) throws BusinessException;
	public Egreso generarEgresoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion, ControlFondosFijos controlFondosFijos, Usuario usuario)	throws BusinessException;
	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacionPorEgreso(Egreso egreso) throws BusinessException;
	public ExpedienteLiquidacion getExpedienteLiquidacionPorId(ExpedienteLiquidacionId expedienteLiquidacionId) throws BusinessException;
	public ExpedienteLiquidacion getExpedienteLiquidacionPorBeneficiario(BeneficiarioLiquidacion beneficiarioLiquidacion)throws BusinessException;
	public List<ExpedienteLiquidacionDetalle> getListaExpedienteLiquidacionDetallePorExpediente(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException;
	public Egreso generarEgresoLiquidacionCheque(ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuenta, Usuario usuario, 
			Integer intNumeroCheque, Integer intTipoDocumentoValidar)throws BusinessException;
	public Egreso generarEgresoLiquidacionTransferencia(ExpedienteLiquidacion expedienteLiquidacion, Bancocuenta bancoCuentaOrigen, Usuario usuario, 
			Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino)throws BusinessException;
	
	//public ExpedienteLiquidacion grabarExpedienteLiquidacion(ExpedienteLiquidacion o, CuentaConcepto c) throws BusinessException;
	//public ExpedienteLiquidacion modificarExpedienteLiquidacion(ExpedienteLiquidacion o, CuentaConcepto c) throws BusinessException;
	public ExpedienteLiquidacion getExpedientePrevisionCompletoPorIdExpedienteLiquidacion(ExpedienteLiquidacionId o) throws BusinessException;
	
	public List<ExpedienteLiquidacion> getListaExpedienteLiquidacionCompBusqueda() throws BusinessException;
	public ExpedienteLiquidacion grabarExpedienteLiquidacion(ExpedienteLiquidacion o) throws BusinessException;
	public ExpedienteLiquidacion modificarExpedienteLiquidacion(ExpedienteLiquidacion o) throws BusinessException;
	public List<ExpedienteLiquidacion> buscarExpedienteParaLiquidacion(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoLiquidacion estadoLiquidacionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException;
	public List<ExpedienteLiquidacionComp> getListaExpedienteLiquidacionCompDeBusquedaFiltro(	Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
 			String strNumeroSolicitudBusq,EstadoLiquidacion estadoCondicionFiltro, Integer intTipoCreditoFiltro, EstadoLiquidacion estadoLiquidacionFechas,
 			EstadoLiquidacion estadoLiquidacionSuc,	Integer intIdSubsucursalFiltro) throws BusinessException;
	public List<BeneficiarioLiquidacion> getListaBeneficiariosPorExpedienteLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException;
	public void deletePorExpediente(ExpedienteLiquidacionId expedienteLiquidacionId) throws BusinessException;
	public List<ExpedienteLiquidacionComp> getListaExpedienteAutorizacionLiquidacionCompDeBusquedaFiltro(	Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
 			String strNumeroSolicitudBusq,EstadoLiquidacion estadoCondicionFiltro, Integer intTipoCreditoFiltro,EstadoLiquidacion estadoLiquidacionFechas,
 			EstadoLiquidacion estadoLiquidacionSuc,Integer intIdSubsucursalFiltro) throws BusinessException;
	public ExpedienteLiquidacion modificarExpedienteLiquidacionParaAuditoria(ExpedienteLiquidacion pExpedienteLiquidacion, Integer intTipoCambio,
			Date dtNuevoFechaProgramacionPago, Integer intNuevoMotivoRenuncia) 
		throws BusinessException;
	public List<ExpedienteLiquidacion> getListaExpedienteLiquidacionYEstados(Integer intEmpresa,Integer intCuenta) throws BusinessException;
	public List<ExpedienteLiquidacionComp> getListaBusqExpLiqFiltros(ExpedienteLiquidacionComp expLiqComp) throws BusinessException;
	public List<ExpedienteLiquidacionComp> getListaBusqAutLiqFiltros(ExpedienteLiquidacionComp expLiqComp) throws BusinessException;
	public Egreso generarEgresoGiroLiquidacion(ExpedienteLiquidacion expedienteLiquidacion, ControlFondosFijos controlFondosFijos, Usuario usuario)	
	throws BusinessException;
	//JCHAVEZ 08.02.2014
	public Archivo getArchivoPorRequisitoLiquidacion(RequisitoLiquidacion reqLiq)throws BusinessException;
	public List<RequisitoLiquidacionComp> getRequisitoGiroLiquidacion(ExpedienteLiquidacion expLiq) throws BusinessException;
	//modificado 08.05.2014 jchavez - nuevo atributo
	public List<RequisitoLiquidacion> getListaPorPkExpedienteLiquidacionYRequisitoDetalle(ExpedienteLiquidacionId pId, ConfServDetalleId rId, Integer intBeneficiario) throws BusinessException;
	public RequisitoLiquidacion grabarRequisito(RequisitoLiquidacion reqLiq) throws BusinessException;
	public List<RequisitoLiquidacionComp> getRequisitoGiroLiquidacionBanco(ExpedienteLiquidacion expLiq) throws BusinessException;
	public ExpedienteLiquidacion grabarGiroLiquidacionPorTesoreria(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException;
	public void eliminarVerificaAutorizacionAdjuntosPorObservacion(ExpedienteLiquidacion pExpedienteLiquidacion) throws BusinessException;
	//Autor: jchavez / Tarea: Creación / Fecha: 09.12.2014
	public Integer getCorrespondePrevision(Integer intEmpresa, Integer intCuenta) throws BusinessException;
}