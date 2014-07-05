package pe.com.tumi.contabilidad.cierre.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.AnexoDetalleOperadorDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.AnexoDetalleOperadorDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleOperador;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleOperadorId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class AnexoDetalleOperadorBO {
	private AnexoDetalleOperadorDao dao = (AnexoDetalleOperadorDao)TumiFactory.get(AnexoDetalleOperadorDaoIbatis.class);

	public AnexoDetalleOperador grabar(AnexoDetalleOperador o) throws BusinessException{
		AnexoDetalleOperador dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public AnexoDetalleOperador modificar(AnexoDetalleOperador o) throws BusinessException{
  		AnexoDetalleOperador dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public AnexoDetalleOperador getPorPk(AnexoDetalleOperadorId pId) throws BusinessException{
		AnexoDetalleOperador domain = null;
		List<AnexoDetalleOperador> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",	pId.getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",	pId.getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 	pId.getIntParaTipoAnexo());
			mapa.put("intItemAnexoDetalle",	pId.getIntItemAnexoDetalle());
			mapa.put("intItemOperador",		pId.getIntItemOperador());
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
	
	public List<AnexoDetalleOperador> getPorAnexoDetalle(AnexoDetalle o) throws BusinessException{
		List<AnexoDetalleOperador> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",	o.getId().getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",	o.getId().getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 	o.getId().getIntParaTipoAnexo());
			mapa.put("intItemAnexoDetalle", o.getId().getIntItemAnexoDetalle());
			lista = dao.getListaPorAnexoDetalle(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void eliminar(AnexoDetalleOperador o) throws BusinessException{
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",	o.getId().getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",	o.getId().getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 	o.getId().getIntParaTipoAnexo());
			mapa.put("intItemAnexoDetalle", o.getId().getIntItemAnexoDetalle());
			mapa.put("intItemOperador",		o.getId().getIntItemOperador());
			dao.eliminar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
}