package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.CuadroComparativoDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.CuadroComparativoDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoId;


public class CuadroComparativoBO{

	private CuadroComparativoDao dao = (CuadroComparativoDao)TumiFactory.get(CuadroComparativoDaoIbatis.class);

	public CuadroComparativo grabar(CuadroComparativo o) throws BusinessException{
		CuadroComparativo dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public CuadroComparativo modificar(CuadroComparativo o) throws BusinessException{
  		CuadroComparativo dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public CuadroComparativo getPorPk(CuadroComparativoId pId) throws BusinessException{
		CuadroComparativo domain = null;
		List<CuadroComparativo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemCuadroComparativo", pId.getIntItemCuadroComparativo());
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
	
	public List<CuadroComparativo> getPorBuscar(CuadroComparativo cuadroComparativo) throws BusinessException{
		List<CuadroComparativo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", cuadroComparativo.getId().getIntPersEmpresa());
			mapa.put("intParaTipoPropuesta", cuadroComparativo.getIntParaTipoPropuesta());
			mapa.put("intParaEstado", cuadroComparativo.getIntParaEstado());
			mapa.put("dtFiltroDesde", cuadroComparativo.getDtFiltroDesde());
			mapa.put("dtFiltroHasta", cuadroComparativo.getDtFiltroHasta());
			mapa.put("intPersEmpresaRequisicion", cuadroComparativo.getIntPersEmpresaRequisicion());
			mapa.put("intItemRequisicion", cuadroComparativo.getIntItemRequisicion());
			//Agregado por cdelosrios, 08/10/2013
			mapa.put("intItemCuadroComparativo", cuadroComparativo.getId().getIntItemCuadroComparativo());
			//Fin agregado por cdelosrios, 08/10/2013
			
			lista = dao.getListaPorBuscar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}