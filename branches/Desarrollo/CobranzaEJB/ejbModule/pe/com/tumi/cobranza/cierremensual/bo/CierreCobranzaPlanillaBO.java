package pe.com.tumi.cobranza.cierremensual.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.cierremensual.dao.CierreCobranzaPlanillaDao;
import pe.com.tumi.cobranza.cierremensual.dao.impl.CierreCobranzaPlanillaDaoIbatis;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CierreCobranzaPlanillaBO {
	
	protected  static Logger log = Logger.getLogger(CierreCobranzaPlanillaBO.class);
	private CierreCobranzaPlanillaDao dao = (CierreCobranzaPlanillaDao)TumiFactory.get(CierreCobranzaPlanillaDaoIbatis.class);
	
	public List<CierreCobranzaPlanilla> getListaCierrePlanillaPorPkCierreCobranza(CierreCobranzaPlanilla o) throws BusinessException{
		List<CierreCobranzaPlanilla> lista = null;
		
		try{
			lista = dao.getListaCierrePlanillaPorPkCierreCobranza(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CierreCobranzaPlanilla getCierreCobranzaPlanillaPorPK(CierreCobranzaPlanilla o) throws BusinessException {
		List<CierreCobranzaPlanilla> lista = null;
		CierreCobranzaPlanilla cierreCobranzaPlanilla = null;
		
		try{
			lista = dao.getCierreCobranzaPlanillaPorPK(o);
			if(lista!=null){
				if(lista.size()==1){
					cierreCobranzaPlanilla = lista.get(0);
				}else if(lista.size()==0){
					cierreCobranzaPlanilla = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return cierreCobranzaPlanilla;
	}
	
	public List<CierreCobranzaPlanilla> getListaCierrePlanillaPorCobranza(CierreCobranzaPlanilla o) throws BusinessException{
		List<CierreCobranzaPlanilla> lista = null;
		
		try{
			lista = dao.getListaCierreCobranzaPlanillaPorCobranza(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CierreCobranzaPlanilla grabarCierreCobranzaPlanilla(CierreCobranzaPlanilla o) throws BusinessException {
		CierreCobranzaPlanilla dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CierreCobranzaPlanilla modificarCierreCobranzaPlanilla(CierreCobranzaPlanilla o) throws BusinessException {
		CierreCobranzaPlanilla dto = null;
		try{
			dto = dao.modificar(o);
		   }catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	public CierreCobranzaPlanilla getCierreCobranzaPlanillaValidarEnvio(CierreCobranzaPlanilla o) throws BusinessException {
		log.debug("getCierreCobranzaPlanillaValidarEnvio INICIO v0");
		List<CierreCobranzaPlanilla> lista = null;
		CierreCobranzaPlanilla cierreCobranzaPlanilla = null;
		
		try{
			lista = dao.getCierreCobranzaPlanillaValidarEnvio(o);			
			if(lista!=null){
				if(lista.size()==1){
					cierreCobranzaPlanilla = lista.get(0);
				}else if(lista.size()==0){
					cierreCobranzaPlanilla = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		log.debug("getCierreCobranzaPlanillaValidarEnvio FIN v0");
		
		return cierreCobranzaPlanilla;
	}
	
}
