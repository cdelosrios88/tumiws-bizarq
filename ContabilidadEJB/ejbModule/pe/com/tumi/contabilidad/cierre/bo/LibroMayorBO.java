/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-004       			27/09/2014     Christian De los Ríos        Se agregan nuevos métodos para regularizar la mayorización         
*/
package pe.com.tumi.contabilidad.cierre.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.dao.LibroMayorDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.LibroMayorDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroMayorBO {
	
	private LibroMayorDao dao = (LibroMayorDao)TumiFactory.get(LibroMayorDaoIbatis.class);

	public LibroMayor grabar(LibroMayor o) throws BusinessException{
		LibroMayor dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public LibroMayor modificar(LibroMayor o) throws BusinessException{
  		LibroMayor dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public LibroMayor getPorPk(LibroMayorId pId) throws BusinessException{
		LibroMayor domain = null;
		List<LibroMayor> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaMayor", pId.getIntPersEmpresaMayor());
			mapa.put("intContPeriodoMayor", pId.getIntContPeriodoMayor());
			mapa.put("intContMesMayor", pId.getIntContMesMayor());
			lista = dao.getListaPorPk(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	
	public List<LibroMayor> buscar(LibroMayor libroMayor) throws BusinessException{
		List<LibroMayor> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intContMesMayor", libroMayor.getId().getIntContMesMayor());
			mapa.put("intContPeriodoMayor", libroMayor.getId().getIntContPeriodoMayor());
			mapa.put("intParaEstadoCierreCod", libroMayor.getIntParaEstadoCierreCod());
			lista = dao.getListaPorBuscar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//Inicio: REQ14-004 - bizarq - 16/09/2014
	public List<LibroMayor> getListMayorHist(LibroMayor libroMayor) throws BusinessException{
		List<LibroMayor> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intContMesMayor", (libroMayor.getId().getIntContMesMayor().equals(Constante.PARAM_T_MES_CALENDARIO_TODOS)?null:libroMayor.getId().getIntContMesMayor()));
			mapa.put("intContPeriodoMayor", libroMayor.getId().getIntContPeriodoMayor());
			mapa.put("intEstadoCod", libroMayor.getIntEstadoCod().equals(Constante.PARAM_T_TODOS)?null:libroMayor.getIntEstadoCod());
			mapa.put("intPersEmpresaMayor", libroMayor.getId().getIntPersEmpresaMayor());
			lista = dao.getListMayorHist(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	public Integer processMayorizacion(LibroMayor o, String strPeriodo) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intMesMayor", 	 o.getId().getIntContMesMayor());
			mapa.put("intAnioPeriodo",   o.getId().getIntContPeriodoMayor());
			mapa.put("intParaEmpresaMayor", o.getId().getIntPersEmpresaMayor());
			mapa.put("intParaEmpresaUsuario", o.getIntPersEmpresaUsuario());
			mapa.put("intPersonaUsuarioPk", o.getIntPersPersonaUsuario());
			mapa.put("intContPeriodoMayor", strPeriodo);
			intEscalar = dao.processMayorizacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	
	public List<LibroMayor> getListAfterProcessedMayorizado(LibroMayor libroMayor) throws BusinessException{
		List<LibroMayor> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intContMesMayor", libroMayor.getId().getIntContMesMayor());
			mapa.put("intContPeriodoMayor", libroMayor.getId().getIntContPeriodoMayor());
			mapa.put("intEstadoCod", libroMayor.getIntEstadoCod());
			lista = dao.getListAfterProcessedMayorizado(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public LibroMayor eliminarMayorizado(LibroMayor o) throws BusinessException{
  		LibroMayor dto = null;
     	try{
		     dto = dao.eliminarMayorizado(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
	//Fin: REQ14-004 - bizarq - 16/09/2014
}