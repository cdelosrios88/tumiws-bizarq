package pe.com.tumi.persona.core.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.dao.CuentaBancariaFinDao;
import pe.com.tumi.persona.core.dao.impl.CuentaBancariaFinDaoIbatis;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaFin;
import pe.com.tumi.persona.core.domain.CuentaBancariaFinId;
import pe.com.tumi.persona.core.service.PersonaDetalleService;

public class CuentaBancariaFinBO {
	
	protected  static Logger log = Logger.getLogger(CuentaBancariaFinBO.class);
	
	private CuentaBancariaFinDao dao = (CuentaBancariaFinDao)TumiFactory.get(CuentaBancariaFinDaoIbatis.class);
	
	public CuentaBancariaFin grabar(CuentaBancariaFin o) throws BusinessException{
		CuentaBancariaFin dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}

	
	public CuentaBancariaFin getPorPK(CuentaBancariaFinId pId) throws BusinessException{
		CuentaBancariaFin domain = null;
		List<CuentaBancariaFin> lista = null;
		try{			
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pId.getIntIdPersona());
			mapa.put("intIdCuentaBancaria", pId.getIntIdCuentaBancaria());
			mapa.put("intParaTipoFinCuenta", pId.getIntParaTipoFinCuenta());
			lista = dao.getListaPorPK(mapa);
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
	
	public List<CuentaBancariaFin> getListaPorCuentaBancaria(CuentaBancaria o) throws BusinessException{
		List<CuentaBancariaFin> lista = null;
		try{
			
			log.info("intIdPersona:"+o.getId().getIntIdPersona());
			log.info("intIdCuentaBancaria:"+o.getId().getIntIdCuentaBancaria());
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", o.getId().getIntIdPersona());
			mapa.put("intIdCuentaBancaria", o.getId().getIntIdCuentaBancaria());
			lista = dao.getListaPorCuentaBancaria(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void eliminar(CuentaBancariaFinId pId) throws BusinessException{
		try{			
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pId.getIntIdPersona());
			mapa.put("intIdCuentaBancaria", pId.getIntIdCuentaBancaria());
			mapa.put("intParaTipoFinCuenta", pId.getIntParaTipoFinCuenta());
			dao.eliminar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
}