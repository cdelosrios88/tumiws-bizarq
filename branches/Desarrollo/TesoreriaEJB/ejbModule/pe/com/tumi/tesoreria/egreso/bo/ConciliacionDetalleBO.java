package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.ConciliacionDetalleDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.ConciliacionDetalleDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalleId;


public class ConciliacionDetalleBO{

	private ConciliacionDetalleDao dao = (ConciliacionDetalleDao)TumiFactory.get(ConciliacionDetalleDaoIbatis.class);

	public ConciliacionDetalle grabar(ConciliacionDetalle o) throws BusinessException{
		ConciliacionDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public ConciliacionDetalle modificar(ConciliacionDetalle o) throws BusinessException{
  		ConciliacionDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public ConciliacionDetalle getPorPk(ConciliacionDetalleId conciliacionDetalleId) throws BusinessException{
		ConciliacionDetalle domain = null;
		List<ConciliacionDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", conciliacionDetalleId.getIntPersEmpresa());
			mapa.put("intItemConciliacion", conciliacionDetalleId.getIntItemConciliacion());
			mapa.put("intItemConciliacionDetalle", conciliacionDetalleId.getIntItemConciliacionDetalle());
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
	
	
	/*
	public List<ConciliacionDetalle> getListConcilDet(ConciliacionDetalle pConcilDet) throws BusinessException{
		List<ConciliacionDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", 					pConcilDet.getId().getIntPersEmpresa());
			mapa.put("intItemConciliacion", 			pConcilDet.getId().getIntItemConciliacion());
			mapa.put("intItemConciliacionDetalle", 		pConcilDet.getId().getIntItemConciliacionDetalle());
			mapa.put("intPersEmpresaEgreso", 			pConcilDet.getIntPersEmpresaEgreso());
			mapa.put("intItemEgresoGeneral", 			pConcilDet.getIntItemEgresoGeneral());
			mapa.put("intPersEmpresaIngreso", 			pConcilDet.getIntPersEmpresaIngreso());
			mapa.put("intItemIngresoGeneral", 			pConcilDet.getIntItemIngresoGeneral());
			mapa.put("intIndicadorCheck", 				pConcilDet.getIntIndicadorCheck());
			mapa.put("intIndicadorConci", 				pConcilDet.getIntIndicadorConci());
			mapa.put("bdSaldoInicial", 					pConcilDet.getBdSaldoInicial());
			mapa.put("bdMontoDebe", 					pConcilDet.getBdMontoDebe());
			mapa.put("bdMontoHaber", 					pConcilDet.getBdMontoHaber());
			mapa.put("intPersEmpresaCheckConciliacion", pConcilDet.getIntPersEmpresaCheckConciliacion());
			mapa.put("intPersPersonaCheckConciliacion", pConcilDet.getIntPersPersonaCheckConciliacion());
			mapa.put("tsFechaCheck", 					pConcilDet.getTsFechaCheck());
			mapa.put("intNumeroOperacion", 				pConcilDet.getIntNumeroOperacion());
			
			lista = dao.getListConcilDet(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	*/
	
}