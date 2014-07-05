package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.InformeGerenciaDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.InformeGerenciaDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerenciaId;


public class InformeGerenciaBO{

	private InformeGerenciaDao dao = (InformeGerenciaDao)TumiFactory.get(InformeGerenciaDaoIbatis.class);

	public InformeGerencia grabar(InformeGerencia o) throws BusinessException{
		InformeGerencia dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public InformeGerencia modificar(InformeGerencia o) throws BusinessException{
  		InformeGerencia dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public InformeGerencia getPorPk(InformeGerenciaId pId) throws BusinessException{
		InformeGerencia domain = null;
		List<InformeGerencia> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemInformeGerencia", pId.getIntItemInformeGerencia());
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

	public List<InformeGerencia> getPorBuscar(InformeGerencia informeGerencia) throws BusinessException{
		List<InformeGerencia> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", informeGerencia.getId().getIntPersEmpresa());
			mapa.put("intParaTipoInforme", informeGerencia.getIntParaTipoInforme());
			mapa.put("intSucuIdSucursal", informeGerencia.getIntSucuIdSucursal());
			mapa.put("intParaEstado", informeGerencia.getIntParaEstado());
			mapa.put("dtFiltroDesde", informeGerencia.getDtFiltroDesde());
			mapa.put("dtFiltroHasta", informeGerencia.getDtFiltroHasta());
			mapa.put("intPersEmpresaRequisicion", informeGerencia.getIntPersEmpresaRequisicion());
			mapa.put("intItemRequisicion", informeGerencia.getIntItemRequisicion());
			//Agregado por cdelosrios, 08/10/2013
			mapa.put("intItemInformeGerencia", informeGerencia.getId().getIntItemInformeGerencia());
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