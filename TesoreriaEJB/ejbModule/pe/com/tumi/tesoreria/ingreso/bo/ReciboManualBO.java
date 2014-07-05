package pe.com.tumi.tesoreria.ingreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.ingreso.dao.ReciboManualDao;
import pe.com.tumi.tesoreria.ingreso.dao.impl.ReciboManualDaoIbatis;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualId;


public class ReciboManualBO{

	private ReciboManualDao dao = (ReciboManualDao)TumiFactory.get(ReciboManualDaoIbatis.class);

	public ReciboManual grabar(ReciboManual o) throws BusinessException{
		ReciboManual dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public ReciboManual modificar(ReciboManual o) throws BusinessException{
  		ReciboManual dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public ReciboManual getPorId(ReciboManualId reciboManualId) throws BusinessException{
		ReciboManual domain = null;
		List<ReciboManual> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", reciboManualId.getIntPersEmpresa());
			mapa.put("intItemReciboManual", reciboManualId.getIntItemReciboManual());
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
	
	public List<ReciboManual> getPorBuscar(ReciboManual reciboManual) throws BusinessException{
		List<ReciboManual> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", reciboManual.getId().getIntPersEmpresa());
			mapa.put("intParaTipoDocumentoGeneral", reciboManual.getIntParaTipoDocumentoGeneral());
			mapa.put("intSucuIdSucursal", reciboManual.getIntSucuIdSucursal());
			mapa.put("intSudeIdSubsucursal", reciboManual.getIntSudeIdSubsucursal());
			mapa.put("intSerieRecibo", reciboManual.getIntSerieRecibo());
			mapa.put("intNumeroInicio", reciboManual.getIntNumeroInicio());
			mapa.put("intNumeroFinal", reciboManual.getIntNumeroFinal());
			mapa.put("intParaEstado", reciboManual.getIntParaEstado());
			mapa.put("intParaEstadoCierre", reciboManual.getIntParaEstadoCierre());
			
			lista = dao.getListaPorBuscar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public ReciboManual getPorReciboManualDetalle(ReciboManualDetalle reciboManualDetalle) throws BusinessException{
		ReciboManual domain = null;
		try{
			ReciboManualId reciboManualId = new ReciboManualId();
			reciboManualId.setIntPersEmpresa(reciboManualDetalle.getId().getIntPersEmpresa());
			reciboManualId.setIntItemReciboManual(reciboManualDetalle.getId().getIntItemReciboManual());
			
			domain = getPorId(reciboManualId);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public Integer validarNroReciboPorSuc(Integer idEmpresa,Integer sucursal,Integer subsuc,Integer nroRecibo) throws BusinessException{
		
		Integer vResult = null;
		HashMap<String,Object> mapa = new HashMap<String,Object>();
		mapa.put("intPersEmpresa", idEmpresa);
		mapa.put("intSucuIdSucursal", sucursal);
		mapa.put("intSudeIdSubsucursal", subsuc);
		mapa.put("intNroRecibo", nroRecibo);
		
		try{
			
		  vResult = dao.validarNroReciboPorSuc(mapa);
		
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return vResult;
	}
}