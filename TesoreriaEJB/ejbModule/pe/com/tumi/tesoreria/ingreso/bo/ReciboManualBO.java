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
	
	/**
	 * jbermudez 19.09.2014
	 * Procedimiento que válida la serie y numero de recibo manual para una agencia
	 * Se agrego el parametro nroSerie  
	 * @param intEmpresa
	 * @param sucursal
	 * @param subsuc
	 * @param nroSerie
	 * @param nroRecibo
	 * @return
	 * @throws BusinessException
	 */
	public Integer validarNroReciboPorSuc(Integer idEmpresa, Integer sucursal, Integer subsuc, Integer nroSerie, Integer nroRecibo) throws BusinessException{
		
		Integer vResult = null;
		HashMap<String,Object> mapa = new HashMap<String,Object>();
		mapa.put("intPersEmpresa", idEmpresa);
		mapa.put("intSucuIdSucursal", sucursal);
		mapa.put("intSudeIdSubsucursal", subsuc);
		mapa.put("intNroSerie", nroSerie);
		mapa.put("intNroRecibo", nroRecibo);
		try{
		  vResult = dao.validarNroReciboPorSuc(mapa);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return vResult;
	}
	/**
	 * jchavez 02.07.2014
	 * Procedimiento que retorna la serie y el ultimo numero de recibo del gestor de ingreso 
	 * @param intEmpresa
	 * @param intIdGestor
	 * @param intIdSucursal
	 * @param intIdSubsucursal
	 * @return
	 * @throws BusinessException
	 */
	public ReciboManual getReciboPorGestorYSucursal(Integer intEmpresa, Integer intIdGestor, Integer intIdSucursal, Integer intIdSubsucursal) throws BusinessException{
		ReciboManual domain = null;
		List<ReciboManual> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", 			intEmpresa);
			mapa.put("intPersPersonaGestor", 	intIdGestor);
			mapa.put("intSucuIdSucursal", 		intIdSucursal);
			mapa.put("intSudeIdSubsucursal", 	intIdSubsucursal);
			lista = dao.getReciboPorGestorYSucursal(mapa);
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
}