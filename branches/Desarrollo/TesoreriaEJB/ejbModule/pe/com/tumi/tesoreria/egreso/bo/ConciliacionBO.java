/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-006       			01/11/2014     		Bisarq        Nuevos Metodos        
*/
package pe.com.tumi.tesoreria.egreso.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.ConciliacionDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.ConciliacionDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionId;
import pe.com.tumi.tesoreria.egreso.domain.comp.ConciliacionComp;


public class ConciliacionBO{

	private ConciliacionDao dao = (ConciliacionDao)TumiFactory.get(ConciliacionDaoIbatis.class);

	/**
	 * 
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public Conciliacion grabar(Conciliacion o) throws BusinessException{
		Conciliacion dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

	/**
	 * 
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
  	public Conciliacion modificar(Conciliacion o) throws BusinessException{
  		Conciliacion dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
  	/**
  	 * 
  	 * @param conciliacionId
  	 * @return
  	 * @throws BusinessException
  	 */
	public Conciliacion getPorPk(ConciliacionId conciliacionId) throws BusinessException{
		Conciliacion domain = null;
		List<Conciliacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", conciliacionId.getIntPersEmpresa());
			mapa.put("intItemConciliacion", conciliacionId.getIntItemConciliacion());
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
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	/**
	 * 
	 * @param conciliacionCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<Conciliacion> getListFilter(ConciliacionComp conciliacionCompBusq) throws BusinessException{
		List<Conciliacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("dtBusqFechaDesde", 		conciliacionCompBusq.getDtBusqFechaDesde());
			mapa.put("dtBusqFechaHasta", 		conciliacionCompBusq.getDtBusqFechaHasta());
			mapa.put("intBusqPersEmpresa", 		conciliacionCompBusq.getIntBusqPersEmpresa());
			mapa.put("intBusqItemBancoFondo", 	conciliacionCompBusq.getIntBusqItemBancoFondo());
			mapa.put("intBusqItemBancoCuenta", 	conciliacionCompBusq.getIntBusqItemBancoCuenta());

			lista = dao.getListFilter(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/*
	public Conciliacion getUltimaConciliacion(Conciliacion pConciliacion) throws DAOException{
	List<Conciliacion> lista = null;
	Conciliacion domain = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", 		pConciliacion.getIntPersEmpresa());
			mapa.put("intItemBancoFondo", 	pConciliacion.getIntItemBancoFondo());
			mapa.put("intItemBancoCuenta", 	pConciliacion.getIntItemBancoCuenta());
			mapa.put("tsFechaConciliacion", pConciliacion.getTsFechaConciliacion());
			mapa.put("intParaEstado", 		pConciliacion.getIntParaEstado());

			lista = dao.getUltimaConciliacion(mapa);
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
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	*/
	
	/**
	 * 
	 * @param conciliacionCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public Conciliacion getLastConciliacionByIdEstadoYFecha(Integer intParaEstado, Date dtLastUtilDay) throws BusinessException, DAOException{
		Conciliacion domain = null;
		List<Conciliacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			/*mapa.put("dtBusqFechaDesde", 		conciliacionCompBusq.getDtBusqFechaDesde());
			mapa.put("dtBusqFechaHasta", 		conciliacionCompBusq.getDtBusqFechaHasta());
			mapa.put("intBusqPersEmpresa", 		conciliacionCompBusq.getIntBusqPersEmpresa());
			*/
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
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	
}