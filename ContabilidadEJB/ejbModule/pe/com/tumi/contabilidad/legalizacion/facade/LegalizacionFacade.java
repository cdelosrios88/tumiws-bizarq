package pe.com.tumi.contabilidad.legalizacion.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.contabilidad.legalizacion.bo.LibroContableDetalleBO;
import pe.com.tumi.contabilidad.legalizacion.bo.LibroLegalizacionBO;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacionComp;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalle;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalleComp;
import pe.com.tumi.contabilidad.legalizacion.service.LibroLegalizacionService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

@Stateless
public class LegalizacionFacade extends TumiFacade implements LegalizacionFacadeRemote, LegalizacionFacadeLocal{
	
	LibroLegalizacionService libroLegalizacion = (LibroLegalizacionService)TumiFactory.get(LibroLegalizacionService.class);
	
	LibroLegalizacionBO boLibroLegalizacionBO = (LibroLegalizacionBO)TumiFactory.get(LibroLegalizacionBO.class);
	LibroContableDetalleBO boLibroContableDetalleBO = (LibroContableDetalleBO)TumiFactory.get(LibroContableDetalleBO.class);

    /**
     * Autor : jbermudez / Tarea : Creación / Fecha : 03.09.2014
     * Funcionalidad : Procedimiento que graba las legalizaciones del control de libros contables
     * @author jbermudez
     * @version 1.0
     * @param o Clase LibroLegalizacion 
     * @return 
     * @throws BusinessException
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public LibroLegalizacion grabarLibroLegalizacion(LibroLegalizacionComp o)throws BusinessException{
		LibroLegalizacion dto = null;
		try{
			dto = libroLegalizacion.grabarLibroLegalizacion(o);
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
     * Autor : jbermudez / Tarea : Creación / Fecha : 03.09.2014
     * Funcionalidad : Procedimiento que modifica las legalizaciones del control de libros contables
     * @author jbermudez
     * @version 1.0
     * @param o Clase LibroLegalizacion 
     * @return 
     * @throws BusinessException
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public LibroLegalizacion modificarLibroLegalizacion(LibroLegalizacion o)throws BusinessException{
		LibroLegalizacion dto = null;
		try{
			dto = boLibroLegalizacionBO.modificar(o);
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
     * Autor : jbermudez / Tarea : Creación / Fecha : 08.09.2014
     * Funcionalidad : Procedimiento que elimina las legalizaciones del control de libros contables
     * @author jbermudez
     * @version 1.0
     * @param o Clase LibroLegalizacionComp 
     * @return 
     * @throws BusinessException
     */
	public void eliminar(LibroLegalizacionComp o) throws BusinessException{	
		try{
			boLibroLegalizacionBO.eliminar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}

    /**
     * Autor : jbermudez / Tarea : Creación / Fecha : 08.09.2014
     * Funcionalidad : Procedimiento que elimina los Libros Contables Detalles
     * @author jbermudez
     * @version 1.0
     * @param o Clase LibroContableDetalleComp 
     * @return 
     * @throws BusinessException
     */
	public void eliminar(LibroContableDetalleComp o) throws BusinessException{	
		try{
			boLibroContableDetalleBO.eliminar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}

    /**
     * Autor : jbermudez / Tarea : Creación / Fecha : 03.09.2014
     * Funcionalidad : Procedimiento que retorna las personas juridicas para el registro de legalizaciones
     * @author jbermudez
     * @version 1.0
     * @param strRazonSocial	Nombre de la razon social de la persona juridica
     * @param strRuc			Número de RUC 
     * @param intIdEmpresa		Código de la empresa
     * @return lista			Lista de personas juridicas 
     * @throws BusinessException
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<LibroLegalizacionComp> getListaPersonaJuridica(String strRazonSocial, String strRuc, Integer intIdEmpresa) throws BusinessException{
    	List<LibroLegalizacionComp> lista = null;
   		try{
   			lista = boLibroLegalizacionBO.getListaPersonaJuridica(strRazonSocial, strRuc, intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}

    /**
     * Autor : jbermudez / Tarea : Creación / Fecha : 03.09.2014
     * Funcionalidad : Procedimiento que graba las legalizaciones detalle del control de libros contables
     * @author jbermudez
     * @version 1.0
     * @param o Clase LibroContableDetalle 
     * @return 
     * @throws BusinessException
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public LibroContableDetalle grabarLibroContableDetalle(LibroContableDetalle o)throws BusinessException{
		LibroContableDetalle dto = null;
		try{
			dto = boLibroContableDetalleBO.grabar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

	public LibroContableDetalle modificarLibroContableDetalle (LibroContableDetalle o)throws BusinessException{
		LibroContableDetalle dto = null;
		try{
			dto = boLibroContableDetalleBO.modificar(o);
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
     * Autor : jbermudez / Tarea : Creación / Fecha : 03.09.2014
     * Funcionalidad : Procedimiento que retorna las Legalizaciones registradas
     * @author jbermudez
     * @version 1.0
     * @param intIdEmpresa			Código de la empresa
     * @param intParaLibroContable	Código del Tipo de Libro Contable
     * @return lista				Lista de legalizaciones
     * @throws BusinessException
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<LibroLegalizacionComp> getListaLegalizaciones(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException{
    	List<LibroLegalizacionComp> lista = null;
   		try{
   			lista = boLibroLegalizacionBO.getListaLegalizaciones(intIdEmpresa, intParaLibroContable);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
	
    /**
     * Autor : jbermudez / Tarea : Creación / Fecha : 03.09.2014
     * Funcionalidad : Procedimiento que retorna las Legalizaciones Detalle registradas
     * @author jbermudez
     * @version 1.0
     * @param intIdEmpresa			Código de la empresa
     * @param intParaLibroContable	Código del Tipo de Libro Contable
     * @return lista				Lista de legalizaciones detalle
     * @throws BusinessException
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<LibroContableDetalleComp> getListaLibroContableDetalle(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException{
    	List<LibroContableDetalleComp> lista = null;
   		try{
   			lista = boLibroContableDetalleBO.getListaLibroContableDetalle(intIdEmpresa, intParaLibroContable);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}

    /**
     * Autor : jbermudez / Tarea : Creación / Fecha : 03.09.2014
     * Funcionalidad : Procedimiento que retorna el último número de folios registrados por un Tipo de Libro Contable
     * @author jbermudez
     * @version 1.0
     * @param intIdEmpresa			Código de la empresa
     * @param intParaLibroContable	Código del Tipo de Libro Contable
     * @return ultimoFolio			Retorna el ultimo numero de folio de legalizaciones
     * @throws BusinessException
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Integer getUltimoFolio(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException{
    	Integer ultimoFolio = null;
   		try{
   			ultimoFolio = boLibroLegalizacionBO.getUltimoFolio(intIdEmpresa, intParaLibroContable);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return ultimoFolio;
   	}

    /**
     * Autor : jbermudez / Tarea : Creación / Fecha : 03.09.2014
     * Funcionalidad : Procedimiento que retorna un resumen de todas las legalizaciones registradas, agrupado por tipo de libro contable y el último periodo
     * @author jbermudez
     * @version 1.0
     * @param intIdEmpresa			Código de la empresa
     * @param intParaLibroContable	Código del Tipo de Libro Contable
     * @return lista				Retorna lista del resumen de legalizaciones
     * @throws BusinessException
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<LibroLegalizacionComp> getListaLibrosLegalizaciones(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException{
    	List<LibroLegalizacionComp> lista = null;
   		try{
   			lista = boLibroLegalizacionBO.getListaLibrosLegalizaciones(intIdEmpresa, intParaLibroContable);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}

    /**
     * Autor : jbermudez / Tarea : Creación / Fecha : 04.09.2014
     * Funcionalidad : Procedimiento que retorna un registro con los rangos de inicio y fin para registrar en el Libro Contable Detalle
     * @author jbermudez
     * @version 1.0
     * @param intIdEmpresa			Código de la empresa
     * @param intParaLibroContable	Código del Tipo de Libro Contable
     * @return lista				Retorna lista del resumen de legalizaciones
     * @throws BusinessException
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<LibroContableDetalleComp> getListaUltimoFolioDetalle(Integer intIdEmpresa, Integer intParaLibroContable, Integer intItemLibroLegalizacion) throws BusinessException{
    	List<LibroContableDetalleComp> lista = null;
   		try{
   			lista = boLibroContableDetalleBO.getListaUltimoFolioDetalle(intIdEmpresa, intParaLibroContable, intItemLibroLegalizacion);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}

    /**
     * Autor : jbermudez / Tarea : Creación / Fecha : 06.09.2014
     * Funcionalidad : Procedimiento que retorna las Libros Contable Detalle registradas en NULO
     * @author jbermudez
     * @version 1.0
     * @param intIdEmpresa			Código de la empresa
     * @param intParaLibroContable	Código del Tipo de Libro Contable
     * @return lista				Lista de legalizaciones detalle
     * @throws BusinessException
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<LibroContableDetalleComp> getListaLibroContaDetaNulo(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException{
    	List<LibroContableDetalleComp> lista = null;
   		try{
   			lista = boLibroContableDetalleBO.getListaLibroContaDetaNulo(intIdEmpresa, intParaLibroContable);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
}