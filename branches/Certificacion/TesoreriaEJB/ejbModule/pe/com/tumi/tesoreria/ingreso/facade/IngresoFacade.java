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
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.egreso.service.DocumentoGeneralService;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoDetalleBO;
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
public class IngresoFacade extends TumiFacade implements IngresoFacadeRemote, IngresoFacadeLocal {
	
	ReciboManualService reciboManualService = (ReciboManualService)TumiFactory.get(ReciboManualService.class);
	DocumentoGeneralService documentoGeneralService = (DocumentoGeneralService)TumiFactory.get(DocumentoGeneralService.class);
	IngresoEfectuadoResumenService ingresoEfectuadoResumenService = (IngresoEfectuadoResumenService)TumiFactory.get(IngresoEfectuadoResumenService.class);
	IngresoService ingresoService = (IngresoService)TumiFactory.get(IngresoService.class);
	DepositoService depositoService = (DepositoService)TumiFactory.get(DepositoService.class);
	IngresoControlFondosFijosService ingresoControlFondosFijosService = (IngresoControlFondosFijosService)TumiFactory.get(IngresoControlFondosFijosService.class);
	
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
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Ingreso generarIngresoEfectuadoResumen(List<EfectuadoResumen> listaEfectuadoResumen, Bancofondo bancoFondo, Usuario usuario) 
		throws BusinessException{
    	Ingreso ingreso = null;
		try{
			ingreso = ingresoEfectuadoResumenService.generarIngresoEfectuadoResumen(listaEfectuadoResumen, bancoFondo, usuario);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return ingreso;
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
    public Integer validarNroReciboPorSuc(Integer idEmpresa,Integer sucursal,Integer subsuc,Integer nroRecibo) throws BusinessException{
    	Integer vResult  = null;
		try{
			vResult = reciboManualService.validarNroReciboPorSuc(idEmpresa,sucursal,subsuc,nroRecibo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return vResult;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String existeNroReciboEnlazado(Integer idEmpresa,Integer idSucursal,Integer idSubSuc,Integer nroRecibo) throws BusinessException{
    	String vResult  = null;
		try{
			vResult = boReciboManualDetalle.existeNroReciboEnlazado(idEmpresa, idSucursal,idSubSuc,nroRecibo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return vResult;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Ingreso> getListaIngNoEnlazados(IngresoId id)throws BusinessException{
    	List<Ingreso> lista = null;
		try{
			lista = boIngreso.getListaIngNoEnlazados(id);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ReciboManualDetalle> buscarRecibosEnlazados(Integer idEmpresa,Integer idSucursal,Integer idSubSuc,Integer idEstadoCierre,Integer nroRecibo)throws BusinessException{
    	List<ReciboManualDetalle> lista = null;
    	try{
			lista = reciboManualService.buscarRecibosEnlazados(idEmpresa, idSucursal, idSubSuc, idEstadoCierre, nroRecibo);
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
			dto = ingresoControlFondosFijosService.grabarIngresoCierreFondos(controlFondosFijos, bancoFondo, usuario, strObservacion);
   		}catch(BusinessException e){
   			throw e;
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
    
    public List<IngresoDetalle> getPorControlFondosFijos(ControlFondosFijosId pId) throws BusinessException{
    	List<IngresoDetalle> lista = null;
		try{
			lista = boIngresoDetalle.getPorControlFondosFijos(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
}