package pe.com.tumi.contabilidad.legalizacion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.legalizacion.dao.LibroContableDetalleDao;
import pe.com.tumi.contabilidad.legalizacion.dao.impl.LibroContableDetalleDaoIbatis;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalle;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalleComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroContableDetalleBO {

	private LibroContableDetalleDao dao = (LibroContableDetalleDao)TumiFactory.get(LibroContableDetalleDaoIbatis.class);

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	public LibroContableDetalle grabar(LibroContableDetalle o) throws BusinessException{
		LibroContableDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
  	public LibroContableDetalle modificar(LibroContableDetalle o) throws BusinessException{
  		LibroContableDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }

    //Autor : jbermudez	/ Tarea : Creación	/ Fecha : 09.09.2014
	public void eliminar(LibroContableDetalleComp o) throws BusinessException{
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", o.getLibroContableDetalle().getId().getIntEmpresaPk());
			mapa.put("intParaLibroContable", o.getLibroContableDetalle().getId().getIntLibroContable());
			mapa.put("intItemLibroContableDet", o.getLibroContableDetalle().getId().getIntItemLibroContableDet());
			dao.eliminar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
  	
	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	public List<LibroContableDetalleComp> getListaLibroContableDetalle(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException{
		List<LibroContableDetalleComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa",intIdEmpresa);
			mapa.put("intParaLibroContable",intParaLibroContable);
			lista = dao.getListaLibroContableDetalle(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}  	

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 04.09.2014
	public List<LibroContableDetalleComp> getListaUltimoFolioDetalle(Integer intIdEmpresa, Integer intParaLibroContable, Integer intItemLibroLegalizacion) throws BusinessException{
		List<LibroContableDetalleComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa",intIdEmpresa);
			mapa.put("intParaLibroContable",intParaLibroContable);
			mapa.put("intItemLibroLegalizacion",intItemLibroLegalizacion);
			lista = dao.getListaUltimoFolioDetalle(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}  	

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 06.09.2014
	public List<LibroContableDetalleComp> getListaLibroContaDetaNulo(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException{
		List<LibroContableDetalleComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa",intIdEmpresa);
			mapa.put("intParaLibroContable",intParaLibroContable);
			lista = dao.getListaLibroContaDetaNulo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}  	
}