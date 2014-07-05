package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.ContratoDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.ContratoDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;
import pe.com.tumi.tesoreria.logistica.domain.ContratoId;


public class ContratoBO{

	private ContratoDao dao = (ContratoDao)TumiFactory.get(ContratoDaoIbatis.class);

	public Contrato grabar(Contrato o) throws BusinessException{
		Contrato dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Contrato modificar(Contrato o) throws BusinessException{
  		Contrato dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Contrato getPorPk(ContratoId pId) throws BusinessException{
		Contrato domain = null;
		List<Contrato> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemContrato", pId.getIntItemContrato());
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
	
	public List<Contrato> getPorBuscar(Contrato contrato) throws BusinessException{
		List<Contrato> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", contrato.getId().getIntPersEmpresa());
			mapa.put("intParaTipoContrato", contrato.getIntParaTipoContrato());
			mapa.put("intPersPersonaServicio", contrato.getIntPersPersonaServicio());
			mapa.put("intPersPersonaSolicita", contrato.getIntPersPersonaSolicita());
			mapa.put("intSucuIdSucursal", contrato.getIntSucuIdSucursal());
			mapa.put("intParaEstado", contrato.getIntParaEstado());
			mapa.put("dtFiltroInicioDesde", contrato.getDtFiltroInicioDesde());
			mapa.put("dtFiltroInicioHasta", contrato.getDtFiltroInicioHasta());
			mapa.put("dtFiltroFinDesde", contrato.getDtFiltroFinDesde());
			mapa.put("dtFiltroFinHasta", contrato.getDtFiltroFinHasta());
			mapa.put("intPersEmpresaRequisicion", contrato.getIntPersEmpresaRequisicion());
			mapa.put("intItemRequisicion", contrato.getIntItemRequisicion());
			//Agregado por cdelosrios, 29/09/2013
			mapa.put("intItemContrato", contrato.getId().getIntItemContrato());
			//Fin agregado por cdelosrios, 29/09/2013
			lista = dao.getListaPorBuscar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}