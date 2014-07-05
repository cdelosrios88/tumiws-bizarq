package pe.com.tumi.tesoreria.egreso.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.MovilidadDao;
import pe.com.tumi.tesoreria.egreso.dao.MovilidadDetalleDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.MovilidadDaoIbatis;
import pe.com.tumi.tesoreria.egreso.dao.impl.MovilidadDetalleDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadDetalle;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadDetalleId;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadId;


public class MovilidadDetalleBO{

	private MovilidadDetalleDao dao = (MovilidadDetalleDao)TumiFactory.get(MovilidadDetalleDaoIbatis.class);

	public MovilidadDetalle grabar(MovilidadDetalle o) throws BusinessException{
		MovilidadDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public MovilidadDetalle modificar(MovilidadDetalle o) throws BusinessException{
  		MovilidadDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public MovilidadDetalle getPorPk(MovilidadDetalleId pId) throws BusinessException{
		MovilidadDetalle domain = null;
		List<MovilidadDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaMovilidad", pId.getIntPersEmpresaMovilidad());
			mapa.put("intItemMovilidad", pId.getIntItemMovilidad());
			mapa.put("intItemMovilidadDetalle", pId.getIntItemMovilidadDetalle());
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

	public List<MovilidadDetalle> getPorMovilidad(Movilidad o) throws BusinessException{
		List<MovilidadDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaMovilidad", o.getId().getIntPersEmpresaMovilidad());
			mapa.put("intItemMovilidad", o.getId().getIntItemMovilidad());
			List<MovilidadDetalle> listaTemp = dao.getListaPorMovilidad(mapa);
			lista = new ArrayList<MovilidadDetalle>();
			for(MovilidadDetalle movilidadDetalle : listaTemp){
				if(movilidadDetalle.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO))					
					lista.add(movilidadDetalle);
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}