package pe.com.tumi.servicio.solicitudPrestamo.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CancelacionCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CronogramaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.ExpedienteCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.RequisitoCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp2;
import pe.com.tumi.servicio.solicitudPrestamo.service.EgresoPrestamoService;
import pe.com.tumi.servicio.solicitudPrestamo.service.GiroPrestamoService;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;


/**
 * Session Bean implementation class SolicitudPrestamoFacade
 */
@Stateless
public class PrestamoFacade extends TumiFacade implements PrestamoFacadeRemote, PrestamoFacadeLocal {
	
	private GiroPrestamoService giroPrestamoService = (GiroPrestamoService)TumiFactory.get(GiroPrestamoService.class);
	private EgresoPrestamoService egresoPrestamoService = (EgresoPrestamoService)TumiFactory.get(EgresoPrestamoService.class);
	private CancelacionCreditoBO boCancelacionCredito = (CancelacionCreditoBO)TumiFactory.get(CancelacionCreditoBO.class);
	private CronogramaCreditoBO boCronogramaCredito = (CronogramaCreditoBO)TumiFactory.get(CronogramaCreditoBO.class);
	private ExpedienteCreditoBO boExpedienteCredito = (ExpedienteCreditoBO)TumiFactory.get(ExpedienteCreditoBO.class);
	private RequisitoCreditoBO boRequisitoCredito = (RequisitoCreditoBO)TumiFactory.get(RequisitoCreditoBO.class);
	

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedienteCredito> obtenerExpedientePorIdPersonayIdEmpresa(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		List<ExpedienteCredito> lista = null;
   		try{
   			lista = egresoPrestamoService.obtenerExpedientePorIdPersonayIdEmpresa(intIdPersona, intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExpedienteCredito> buscarExpedienteParaGiro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoCredito estadoCreditoFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
		List<ExpedienteCredito> lista = null;
   		try{
   			lista = giroPrestamoService.buscarExpedienteParaGiro(listaPersonaFiltro, intTipoCreditoFiltro, estadoCreditoFiltro,
   					intItemExpedienteFiltro, intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CancelacionCredito> getListaCancelacionCreditoPorExpedienteCredito(ExpedienteCredito expedienteCredito) throws BusinessException{
		List<CancelacionCredito> lista = null;
   		try{
   			lista = boCancelacionCredito.getListaPorExpedienteCredito(expedienteCredito);
   			if(lista==null){
   				lista = new ArrayList<CancelacionCredito>();
   			}
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
    public ExpedienteCredito grabarGiroPrestamo(ExpedienteCredito expedienteCredito) throws BusinessException{
    	ExpedienteCredito dto = null;
		try{
			dto = giroPrestamoService.grabarGiroPrestamo(expedienteCredito);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CronogramaCredito> getListaCronogramaCreditoPorExpedienteCredito(ExpedienteCredito expedienteCredito) throws BusinessException{
		List<CronogramaCredito> lista = null;
   		try{
   			lista = boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EstadoCredito obtenerUltimoEstadoCredito(ExpedienteCredito expedienteCredito, Integer intTipoEstado) throws BusinessException{
		EstadoCredito dto = null;
   		try{
   			dto = giroPrestamoService.obtenerUltimoEstadoCredito(expedienteCredito, intTipoEstado);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Cuenta getCuentaActualPorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
    	Cuenta dto = null;
		try{
			dto = giroPrestamoService.getCuentaActualPorIdPersona(intIdPersona,intIdEmpresa);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(ExpedienteCredito expedienteCredito) throws BusinessException{
		List<EgresoDetalleInterfaz> lista = null;
		try{
			lista = egresoPrestamoService.cargarListaEgresoDetalleInterfaz(expedienteCredito);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Metodo usado en la generacion del egreso de giro de prestamo.
	 * Abarca la generacion tanto del egreso como del asiento contable.
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso generarEgresoPrestamo(ExpedienteCredito expedienteCredito, ControlFondosFijos controlFondosFijos, Usuario usuario) 
	throws BusinessException{
		Egreso egreso = null;
		try{
			egreso = egresoPrestamoService.generarEgresoPrestamo(expedienteCredito, controlFondosFijos, usuario);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ExpedienteCredito getExpedienteCreditoPorId(ExpedienteCreditoId expedienteCreditoId) throws BusinessException{
		ExpedienteCredito expedienteCredito = null;
		try{
			expedienteCredito = boExpedienteCredito.getPorPk(expedienteCreditoId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return expedienteCredito;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ExpedienteCredito obtenerExpedientePorEgreso(Egreso egreso)throws BusinessException{
		ExpedienteCredito expedienteCredito = null;
		try{
			expedienteCredito = giroPrestamoService.obtenerExpedientePorEgreso(egreso);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return expedienteCredito;
	}
	/**
	 * Se modifico el procedimiento para generar el egreso segun las ultimas indicaciones, contemplando los campos a grabar y 
	 * el caso de represtamo
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso generarEgresoPrestamoCheque(ExpedienteCredito expedienteCredito, Bancocuenta bancoCuenta, Usuario usuario, 
    	Integer intNumeroCheque, Integer intTipoDocumentoValidar)throws BusinessException{
		Egreso egreso = null;
		try{
			egreso = egresoPrestamoService.generarEgresoPrestamoCheque(expedienteCredito, bancoCuenta, usuario, intNumeroCheque, intTipoDocumentoValidar);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Egreso generarEgresoPrestamoTransferencia(ExpedienteCredito expedienteCredito, Bancocuenta bancoCuentaOrigen, Usuario usuario, 
    	Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino)throws BusinessException{
		Egreso egreso = null;
		try{
			egreso = egresoPrestamoService.generarEgresoPrestamoTransferenciaTerceros(expedienteCredito, bancoCuentaOrigen, usuario, intNumeroTransferencia, intTipoDocumentoValidar, cuentaBancariaDestino);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	/**
	 * JCHAVEZ 31.01.2014
	 * Procedimiento que recupera los Requisitos necesarios para el giro en Giro REPRESTAMO
	 * @param expCred
	 * @return
	 * @throws BusinessException
	 */
	public List<RequisitoCreditoComp> getRequisitoGiroPrestamo(ExpedienteCredito expCred) throws BusinessException{
		List<RequisitoCreditoComp> lista = null;
		try{
			lista = boExpedienteCredito.getRequisitoGiroPrestamo(expCred);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * JCHAVEZ 03.02.2014
	 * Grabacion del requisito en giro de credito, aplican a creditos que superen el monto maximo asignado al fondo.
	 * @param reqCred
	 * @return
	 * @throws BusinessException
	 */
	public RequisitoCredito grabarRequisito(RequisitoCredito reqCred) throws BusinessException{
		RequisitoCredito dto = null;
		try{
			dto = boRequisitoCredito.grabar(reqCred);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * JCHAVEZ 03.02.2014
	 * Recupera lista de expediente credito por pk del expediente credito y requisito detalle
	 * @param pId
	 * @param rId
	 * @return
	 * @throws BusinessException
	 */
	public List<RequisitoCredito> getListaPorPkExpedienteCreditoYRequisitoDetalle(ExpedienteCreditoId pId, ConfServDetalleId rId) throws BusinessException{
		List<RequisitoCredito>  lista = null;
		try{
			lista = boRequisitoCredito.getListaPorPkExpedienteCreditoYRequisitoDetalle(pId, rId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * JCHAVEZ 04.02.2014
	 * Recupera el archivo del requisito credito registrado.
	 * @param reqCred
	 * @return
	 * @throws BusinessException
	 */
    public Archivo getArchivoPorRequisitoCredito(RequisitoCredito reqCred)throws BusinessException{
    	Archivo archivo = null;
		try{
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntParaTipoCod(reqCred.getIntParaTipoArchivoCod());
			archivoId.setIntItemArchivo(reqCred.getIntParaItemArchivo());
			archivoId.setIntItemHistorico(reqCred.getIntParaItemHistorico());
			archivo = generalFacade.getArchivoPorPK(archivoId);			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return archivo;
	}
    /**
     * Procedimiento usado en "Banco", para solucionar problema stub con RequisitoCreditoComp - atributo MyFile.
     */
	public List<RequisitoCreditoComp2> getRequisitoGiroPrestamoBanco(ExpedienteCredito expCred) throws BusinessException{
		List<RequisitoCreditoComp> lista = null;
		List<RequisitoCreditoComp2> lstReqCred = new ArrayList<RequisitoCreditoComp2>();
		RequisitoCreditoComp2 reqCred = new RequisitoCreditoComp2();
		try{
			lista = boExpedienteCredito.getRequisitoGiroPrestamo(expCred);
			for (RequisitoCreditoComp o : lista) {
				reqCred = new RequisitoCreditoComp2();
				reqCred.setIntEmpresa(o.getIntEmpresa());
				reqCred.setIntItemRequisito(o.getIntItemRequisito());
				reqCred.setIntItemRequisitoDetalle(o.getIntItemRequisitoDetalle());
				reqCred.setStrDescripcionRequisito(o.getStrDescripcionRequisito());
				reqCred.setIntParaTipoCredito(o.getIntParaTipoCredito());
				reqCred.setIntItemCredito(o.getIntItemCredito());
				lstReqCred.add(reqCred);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstReqCred;
	}
	
	/**
	 * Agregado jchavez 24.04.2014
	 * Grabacion del giro prestamo por sede central
	 * @param expedienteCredito
	 * @return
	 * @throws BusinessException
	 */
    public ExpedienteCredito grabarGiroPrestamoPorTesoreria(ExpedienteCredito expedienteCredito) throws BusinessException{
    	ExpedienteCredito dto = null;
		try{
			dto = giroPrestamoService.grabarGiroPrestamoPorTesoreria(expedienteCredito);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
}

