package pe.com.tumi.persona.core.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.dao.CuentaBancariaDao;
import pe.com.tumi.persona.core.dao.impl.CuentaBancariaDaoIbatis;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.facade.PersonaFacade;

public class CuentaBancariaBO {
	protected  static Logger log = Logger.getLogger(CuentaBancariaBO.class);
	private CuentaBancariaDao dao = (CuentaBancariaDao)TumiFactory.get(CuentaBancariaDaoIbatis.class);
	
	public CuentaBancaria grabarCuentaBancaria(CuentaBancaria o) throws BusinessException{
		CuentaBancaria dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaBancaria modificarCuentaBancaria(CuentaBancaria o) throws BusinessException{
		CuentaBancaria dto = null;
		try{
			
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaBancaria getCuentaBancariaPorPK(CuentaBancariaPK pPK) throws BusinessException{
		CuentaBancaria domain = null;
		List<CuentaBancaria> lista = null;
		try{
			log.info("intIdPersona:"+pPK.getIntIdPersona());
			log.info("intIdCuentaBancaria:"+pPK.getIntIdCuentaBancaria());
			
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			mapa.put("intIdCuentaBancaria", pPK.getIntIdCuentaBancaria());
			lista = dao.getListaCuentaBancariaPorPK(mapa);
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
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}catch(BusinessException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e) {
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<CuentaBancaria> getListaCuentaBancariaPorIdPersona(Integer pId) throws BusinessException{
		List<CuentaBancaria> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pId);
			lista = dao.getListaCuentaBancariaPorIdPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CuentaBancaria> getListaPorStrNroCuentaBancaria(String strNroCuentaBancaria) throws BusinessException{
		List<CuentaBancaria> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("strNroCuentaBancaria", strNroCuentaBancaria);
			lista = dao.getListaPorStrNroCuentaBancaria(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
