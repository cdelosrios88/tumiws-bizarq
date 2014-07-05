package pe.com.tumi.riesgo.archivo.facade;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.archivo.bo.ConfDetalleBO;
import pe.com.tumi.riesgo.archivo.bo.ConfEstructuraBO;
import pe.com.tumi.riesgo.archivo.bo.ConfiguracionBO;
import pe.com.tumi.riesgo.archivo.bo.NombreBO;
import pe.com.tumi.riesgo.archivo.domain.ConfDetalle;
import pe.com.tumi.riesgo.archivo.domain.ConfEstructura;
import pe.com.tumi.riesgo.archivo.domain.ConfEstructuraId;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;
import pe.com.tumi.riesgo.archivo.domain.ConfiguracionId;
import pe.com.tumi.riesgo.archivo.domain.Nombre;
import pe.com.tumi.riesgo.archivo.service.ConfiguracionService;

@Stateless
public class ArchivoRiesgoFacade extends TumiFacade implements ArchivoRiesgoFacadeRemote, ArchivoRiesgoFacadeLocal {
    ConfiguracionBO boConfiguracion = (ConfiguracionBO)TumiFactory.get(ConfiguracionBO.class); 
    ConfDetalleBO boConfDetalle = (ConfDetalleBO)TumiFactory.get(ConfDetalleBO.class);
    NombreBO boNombre = (NombreBO)TumiFactory.get(NombreBO.class);
    ConfEstructuraBO confEstructuraBO = (ConfEstructuraBO)TumiFactory.get(ConfEstructuraBO.class);
    ConfiguracionService configuracionService = (ConfiguracionService)TumiFactory.get(ConfiguracionService.class); 

    public Configuracion grabarConfiguracion(Configuracion o, List<ConfDetalle> listaConfDetalle, List<Nombre> listaNombre)
    	throws BusinessException{
    	Configuracion dto = null;
		try{
			dto = configuracionService.grabarConfiguracion(o,listaConfDetalle,listaNombre);
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
   	public Configuracion getConfiguracionPorPk(ConfiguracionId pId) throws BusinessException{
    	Configuracion dto = null;
   		try{
   			dto = boConfiguracion.getPorPk(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Configuracion> buscarConfiguracion(Configuracion c, Timestamp busquedaInicio, Timestamp busquedaFin) 
    	throws BusinessException{
    	List<Configuracion> lista = null;
   		try{
   			lista = configuracionService.buscarConfiguracion(c, busquedaInicio, busquedaFin);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Configuracion> buscarConfiguracionConEstructura(Configuracion c, Timestamp busquedaInicio, Timestamp busquedaFin) 
    	throws BusinessException{
    	List<Configuracion> lista = null;
   		try{
   			lista = configuracionService.buscarConfiguracionConEstructura(c, busquedaInicio, busquedaFin);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Configuracion> buscarConfiguracionSinEstructura(Configuracion c, Timestamp busquedaInicio, Timestamp busquedaFin) 
    	throws BusinessException{
    	List<Configuracion> lista = null;
   		try{
   			lista = configuracionService.buscarConfiguracionSinEstructura(c, busquedaInicio, busquedaFin);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    public Configuracion eliminarConfiguracion(Configuracion c) throws BusinessException{
    	Configuracion dto = null;
   		try{
   			c.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
   			dto = boConfiguracion.modificar(c);
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
   	public List<ConfDetalle> getConfDetallePorIntItemConfiguracion(Integer intItemConfiguracion) 
    	throws BusinessException{
    	List<ConfDetalle> lista = null;
   		try{
   			lista = boConfDetalle.getPorIntItemConfiguracion(intItemConfiguracion);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Nombre> getNombrePorIntItemConfiguracion(Integer intItemConfiguracion) 
    	throws BusinessException{
    	List<Nombre> lista = null;
   		try{
   			lista = boNombre.getPorIntItemConfiguracion(intItemConfiguracion);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    public Configuracion modificarConfiguracion(Configuracion o, List<ConfDetalle> listaConfDetalle, List<Nombre> listaNombre)
		throws BusinessException{
		Configuracion dto = null;
		try{
			dto = configuracionService.modificarConfiguracion(o,listaConfDetalle,listaNombre);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Configuracion grabarConfiguracion(Configuracion o, List<ConfDetalle> listaConfDetalle, List<Nombre> listaNombre, ConfEstructura confEstructura)
    	throws BusinessException{
    	Configuracion dto = null;
		try{
			dto = configuracionService.grabarConfiguracion(o,listaConfDetalle,listaNombre,confEstructura);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;    	
    }
    
    public Configuracion modificarConfiguracion(Configuracion o, List<ConfDetalle> listaConfDetalle, List<Nombre> listaNombre, ConfEstructura confEstructura)
		throws BusinessException{
		Configuracion dto = null;
		try{
			dto = configuracionService.modificarConfiguracion(o,listaConfDetalle,listaNombre,confEstructura);
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
   	public ConfEstructura getConfEstructuraPorPK(ConfEstructuraId confEstructuraId) 
    	throws BusinessException{
    	ConfEstructura confEstructura;
   		try{
   			confEstructura = confEstructuraBO.getPorPk(confEstructuraId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return confEstructura;
   	}
    /**
     * Franko
     * Listado de ConfirugacionEstructura
     * Por Modalidad, tipo de socio, empresa, nivel, codigo
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConfEstructura> getListaModTipoSoEmp(Integer intModalidad, Integer  intTipoSocio, 
			Integer intEmpresa, Integer intNivel, Integer intCodigo, Integer intFormato)throws BusinessException{
    	List<ConfEstructura> lista = null;
   		try{
   			lista = confEstructuraBO.getListaModTipoSoEmp(intModalidad, intTipoSocio, intEmpresa, intNivel,intCodigo,intFormato);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
}
