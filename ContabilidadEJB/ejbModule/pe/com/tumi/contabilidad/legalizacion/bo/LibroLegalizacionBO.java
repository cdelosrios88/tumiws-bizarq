package pe.com.tumi.contabilidad.legalizacion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.legalizacion.dao.LibroLegalizacionDao;
import pe.com.tumi.contabilidad.legalizacion.dao.impl.LibroLegalizacionDaoIbatis;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacionComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroLegalizacionBO {

	private LibroLegalizacionDao dao = (LibroLegalizacionDao)TumiFactory.get(LibroLegalizacionDaoIbatis.class);

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	public LibroLegalizacion grabar(LibroLegalizacion o) throws BusinessException{
		LibroLegalizacion dto = null;
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
  	public LibroLegalizacion modificar(LibroLegalizacion o) throws BusinessException{
  		LibroLegalizacion dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  	
  //Autor : jbermudez	/ Tarea : Creación	/ Fecha : 08.09.2014
	public void eliminar(LibroLegalizacionComp o) throws BusinessException{
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", o.getLibroLegalizacion().getId().getIntPersEmpresa());
			mapa.put("intParaLibroContable", o.getLibroLegalizacion().getId().getIntParaLibroContable());
			mapa.put("intItemLibroLegalizacion", o.getLibroLegalizacion().getId().getIntItemLibroLegalizacion());
			dao.eliminar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
  	
	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	public List<LibroLegalizacionComp> getListaPersonaJuridica(String strRazonSocial, String strRuc, Integer intIdEmpresa) throws BusinessException{
		List<LibroLegalizacionComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("strPersRuc",strRuc.trim());
			mapa.put("strJuriRazon",strRazonSocial);
			mapa.put("intPersEmpresa",intIdEmpresa);
			lista = dao.getListaPersonaJuridica(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	public List<LibroLegalizacionComp> getListaLegalizaciones(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException{
		List<LibroLegalizacionComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa",intIdEmpresa);
			mapa.put("intParaLibroContable",intParaLibroContable);
			lista = dao.getListaLegalizaciones(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	public Integer getUltimoFolio(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException{
		Integer ultimoFolio = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa",intIdEmpresa);
			mapa.put("intParaLibroContable",intParaLibroContable);
			ultimoFolio = dao.getUltimoFolio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return ultimoFolio;
	}

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	public List<LibroLegalizacionComp> getListaLibrosLegalizaciones(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException{
		List<LibroLegalizacionComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa",intIdEmpresa);
			mapa.put("intParaLibroContable",intParaLibroContable);
			lista = dao.getListaLibrosLegalizaciones(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}