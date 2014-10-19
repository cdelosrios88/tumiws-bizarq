package pe.com.tumi.tesoreria.ingreso.facade;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.common.util.DocumentoGeneral;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.egreso.service.DocumentoGeneralService;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoDetalleBO;
import pe.com.tumi.tesoreria.ingreso.bo.ReciboManualBO;
import pe.com.tumi.tesoreria.ingreso.bo.ReciboManualDetalleBO;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;
import pe.com.tumi.tesoreria.ingreso.service.DepositoService;
import pe.com.tumi.tesoreria.ingreso.service.IngresoControlFondosFijosService;
import pe.com.tumi.tesoreria.ingreso.service.IngresoEfectuadoResumenService;
import pe.com.tumi.tesoreria.ingreso.service.IngresoService;
import pe.com.tumi.tesoreria.ingreso.service.ReciboManualService;

@Stateless
public class IngresosFacade extends TumiFacade implements IngresosFacadeRemote, IngresosFacadeLocal {
	ReciboManualService reciboManualService = (ReciboManualService)TumiFactory.get(ReciboManualService.class);
	DocumentoGeneralService documentoGeneralService = (DocumentoGeneralService)TumiFactory.get(DocumentoGeneralService.class);
	IngresoEfectuadoResumenService ingresoEfectuadoResumenService = (IngresoEfectuadoResumenService)TumiFactory.get(IngresoEfectuadoResumenService.class);
	IngresoService ingresoService = (IngresoService)TumiFactory.get(IngresoService.class);
	DepositoService depositoService = (DepositoService)TumiFactory.get(DepositoService.class);
	IngresoControlFondosFijosService ingresoControlFondosFijosService = (IngresoControlFondosFijosService)TumiFactory.get(IngresoControlFondosFijosService.class);
	ReciboManualBO boReciboDetalle = (ReciboManualBO)TumiFactory.get(ReciboManualBO.class);
	ReciboManualDetalleBO boReciboManualDetalle = (ReciboManualDetalleBO)TumiFactory.get(ReciboManualDetalleBO.class);
	IngresoDetalleBO boIngresoDetalle = (IngresoDetalleBO)TumiFactory.get(IngresoDetalleBO.class);
	IngresoBO boIngreso = (IngresoBO)TumiFactory.get(IngresoBO.class);
	
	
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ReciboManual getReciboManualPorSubsucursal(Subsucursal subsucursal) throws BusinessException{
    	ReciboManual dto = null;
		try{
			dto = reciboManualService.getReciboManualPorSubsucursal(subsucursal);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentoGeneral> filtrarDuplicidadDocumentoGeneralParaIngreso(
    	List<DocumentoGeneral> listaDocumentoPorAgregar, Integer intTipoDocumentoAgregar, List<DocumentoGeneral>listaDocumentoAgregados)
    	throws BusinessException{
    	List<DocumentoGeneral> lista = null;
		try{
			lista = documentoGeneralService.filtrarDuplicidadDocumentoGeneralParaEgreso(listaDocumentoPorAgregar, 
					intTipoDocumentoAgregar, listaDocumentoAgregados);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    //metodo usado para generar el ingreso de una planilla efectuada
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EfectuadoResumen generarIngresoEfectuadoResumen(List<EfectuadoResumen> listaEfectuadoResumen, Bancofondo bancoFondo, Usuario usuario) 
		throws BusinessException{
    	EfectuadoResumen efectuadoResumen = listaEfectuadoResumen.get(0);
		try{
			efectuadoResumen = ingresoEfectuadoResumenService.generarIngresoEfectuadoResumen(efectuadoResumen, bancoFondo, usuario);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return efectuadoResumen;
	}
    
    public ReciboManualDetalle grabarReciboManualDetalle(ReciboManualDetalle reciboManualDetalle) throws BusinessException{
    	ReciboManualDetalle dto = null;
		try{
			dto = boReciboManualDetalle.grabar(reciboManualDetalle);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Ingreso grabarIngresoGeneral(Ingreso ingreso) throws BusinessException{
    	Ingreso dto = null;
		try{
			dto = ingresoService.grabarIngresoGeneral(ingreso);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Ingreso grabarIngresoEfectuadoResumen(Ingreso ingreso, List<EfectuadoResumen> listaEfectuadoResumen) throws BusinessException{
    	Ingreso dto = null;
		try{
			dto = ingresoEfectuadoResumenService.grabarIngresoEfectuadoResumen(ingreso, listaEfectuadoResumen);
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
    public List<Ingreso> buscarIngresoParaCaja(Ingreso ingresoFiltro, List<Persona> listaPersonaFiltro)throws BusinessException{
    	List<Ingreso> lista = null;
		try{
			lista = ingresoService.buscarIngresoParaCaja(ingresoFiltro, listaPersonaFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<IngresoDetalle> getListaIngresoDetallePorIngreso(Ingreso ingreso)throws BusinessException{
    	List<IngresoDetalle> lista = null;
		try{
			lista = boIngresoDetalle.getPorIngreso(ingreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ReciboManual obtenerReciboManualPorIngreso(Ingreso ingreso)throws BusinessException{
    	ReciboManual dto = null;
		try{
			dto = reciboManualService.obtenerReciboManualPorIngreso(ingreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EfectuadoResumen> obtenerListaEfectuadoResumenPorIngreso(Ingreso ingreso) throws BusinessException{
    	List<EfectuadoResumen> lista = null;
		try{
			lista = ingresoEfectuadoResumenService.obtenerListaEfectuadoResumenPorIngreso(ingreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public LibroDiario obtenerLibroDiarioPorIngreso(Ingreso ingreso)throws BusinessException{
    	LibroDiario dto = null;
		try{
			dto = ingresoService.obtenerLibroDiarioPorIngreso(ingreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Ingreso> obtenerListaIngresoParaDepositar(Integer intIdEmpresa, Integer intMoneda, Integer intFormaPago, Usuario usuario)throws BusinessException{
    	List<Ingreso> lista = null;
		try{
			lista = depositoService.obtenerListaIngresoParaDepositar(intIdEmpresa, intMoneda, intFormaPago, usuario);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public Ingreso grabarDeposito(List<Ingreso> listaIngreso, Usuario usuario, Bancofondo bancoFondoDepositar, String strObservacion, 
			Archivo archivo, BigDecimal bdOtrosIngresos, String strNumeroOperacion, Bancofondo bancoFondoIngresar)throws BusinessException{
    	Ingreso dto = null;
		try{
			dto = depositoService.grabarDeposito(listaIngreso, usuario, bancoFondoDepositar, strObservacion, archivo, 
					bdOtrosIngresos, strNumeroOperacion, bancoFondoIngresar);
			
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
    public Integer validarNroReciboPorSuc(Integer idEmpresa, Integer sucursal, Integer subsuc, Integer nroSerie, Integer nroRecibo) throws BusinessException{
    	Integer vResult  = null;
		try{
			//Autor : jbermudez / Tarea : modificación, se comento el código para llamar directo al BO / Fecha : 19.09.2014
			//vResult = reciboManualService.validarNroReciboPorSuc(idEmpresa, sucursal, subsuc, nroSerie, nroRecibo);
			vResult = boReciboDetalle.validarNroReciboPorSuc(idEmpresa, sucursal, subsuc, nroSerie, nroRecibo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return vResult;
	}
    
  //Autor : jbermudez / tarea : modificacion, se agrego el parametro nroSerie / Fecha : 19.09.2014
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String existeNroReciboEnlazado(Integer idEmpresa,Integer idSucursal,Integer idSubSuc,Integer nroSerie, Integer nroRecibo) throws BusinessException{
    	String vResult  = null;
		try{
			vResult = boReciboManualDetalle.existeNroReciboEnlazado(idEmpresa, idSucursal, idSubSuc, nroSerie, nroRecibo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return vResult;
	}
    
    //jbermudez / 22.09.2014 / Tarea: modificacion, tipo de parametro que ingresa de IngresoId ahora recibe un Ingreso, para recibir el codigo de sucursal y subsucursal
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Ingreso> getListaIngNoEnlazados(Ingreso ing)throws BusinessException{
    	List<Ingreso> lista = null;
		try{
			lista = boIngreso.getListaIngNoEnlazados(ing);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ReciboManualDetalle> buscarRecibosEnlazados(Integer idEmpresa,Integer idSucursal,Integer idSubSuc,Integer idEstadoRecibo,Integer nroSerie, Integer nroRecibo)throws BusinessException{
    	List<ReciboManualDetalle> lista = null;
    	try{
			//lista = reciboManualService.buscarRecibosEnlazados(idEmpresa, idSucursal, idSubSuc, idEstadoRecibo, nroSerie, nroRecibo);
			lista = boReciboManualDetalle.getListaPorFiltros(idEmpresa, idSucursal, idSubSuc, idEstadoRecibo, nroSerie, nroRecibo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
    }
    	
    public ReciboManualDetalle modificarReciboManualDetalle(ReciboManualDetalle reciboManualDetalle) throws BusinessException{
    	ReciboManualDetalle dto = null;
		try{
			dto = boReciboManualDetalle.modificar(reciboManualDetalle);
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
    public Ingreso grabarIngresoCierreFondo(ControlFondosFijos controlFondosFijos, Bancofondo bancoFondo, Usuario usuario, String strObservacion)
    	throws BusinessException{
    	Ingreso dto = null;
		try{
//			dto = ingresoControlFondosFijosService.grabarIngresoCierreFondos(controlFondosFijos, bancoFondo, usuario, strObservacion);
//   		}catch(BusinessException e){
//   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
 
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Ingreso> getListaIngresoParaBuscar(Ingreso ingreso) throws BusinessException{
    	List<Ingreso> lista = null;
		try{
			lista = boIngreso.getListaParaBuscar(ingreso);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
//    public List<IngresoDetalle> getPorControlFondosFijos(ControlFondosFijosId pId) throws BusinessException{
//    	List<IngresoDetalle> lista = null;
//		try{
//			lista = boIngresoDetalle.getPorControlFondosFijos(pId);
//   		}catch(BusinessException e){
//   			throw e;
//   		}catch(Exception e){
//   			throw new BusinessException(e);
//   		}
//		return lista;
//	}
//    /**
//     * Autor: jchavez / Tarea: Creación / Fecha: 02.07.2014 /
//	 * Funcionalidad: Método que retorna la serie y el ultimo numero de recibo del gestor de ingreso 
//     * @author jchavez
//   	 * @version 1.0
//     * @param intEmpresa
//     * @param intIdGestor
//     * @param intIdSucursal
//     * @param intIdSubsucursal
//     * @return dto - Objeto de tipo ReciboManual
//     * @throws BusinessException
//     */
//    public ReciboManual getReciboPorGestorYSucursal(Integer intEmpresa, Integer intIdGestor, Integer intIdSucursal, Integer intIdSubsucursal) throws BusinessException{
//    	ReciboManual dto = null;
//		try{
//			dto = boReciboDetalle.getReciboPorGestorYSucursal(intEmpresa, intIdGestor, intIdSucursal, intIdSubsucursal);
//   		}catch(BusinessException e){
//   			throw e;
//   		}catch(Exception e){
//   			throw new BusinessException(e);
//   		}
//		return dto;
//	}
//
//    /**
//     * Autor: jchavez / Tarea: Creación / Fecha: 11.07.2014 / 
//   	 * Funcionalidad: Método que genera Ingreso, Ingreso Detalle, Libro Diario y Libro Diario Detalle del Ingreso Caja - Socio
//   	 * @author jchavez
//   	 * @version 1.0
//   	 * @param listaIngresoSocio
//   	 * @param documentoGeneral
//   	 * @param bancoFondo
//   	 * @param usuario
//   	 * @param intModalidadC
//   	 * @param intPersonaRolC
//   	 * @return documentoGeneral - Objeto que contiene los objetos generados.
//   	 * @throws BusinessException
//     */
//    public DocumentoGeneral generarIngresoSocio(List<ExpedienteComp> listaIngresoSocio, DocumentoGeneral documentoGeneral, Bancofondo bancoFondo, Usuario usuario, Integer intModalidadC, Integer intPersonaRolC) throws BusinessException{
//		try{
//			documentoGeneral = ingresoService.generarIngresoSocio(listaIngresoSocio, documentoGeneral, bancoFondo, usuario, intModalidadC, intPersonaRolC);
//		}catch(BusinessException e){
//			throw e;
//		}catch(Exception e){
//			throw new BusinessException(e);
//		}
//		return documentoGeneral;
//    }
//    
//    /**
//     * Autor: jchavez / Tarea: Creación / Fecha: 13.07.2014 / 
//   	 * Funcionalidad: Método que realiza las grabaciones a las diferentes tablas vinculadas al Ingreso Caja - Socio
//   	 * @author jchavez
//   	 * @version 1.0
//   	 * @param listaIngresosSocio
//   	 * @param documentoGeneral
//   	 * @param usuario
//   	 * @param intModalidadC
//   	 * @return ingreso - ingreso grabado.
//   	 * @throws BusinessException
//     */
//    public Ingreso grabarIngresoSocio(List<ExpedienteComp> listaIngresosSocio, DocumentoGeneral documentoGeneral, Usuario usuario, Integer intModalidadC) throws BusinessException{
//    	Ingreso ingreso = null;
//		try{
//			ingreso = ingresoService.grabarIngresoSocio(listaIngresosSocio, documentoGeneral, usuario,intModalidadC);
//		}catch(BusinessException e){
//			context.setRollbackOnly();
//			throw e;
//		}catch(Exception e){
//			context.setRollbackOnly();
//			throw new BusinessException(e);
//		}
//		return ingreso;
//    }
}
