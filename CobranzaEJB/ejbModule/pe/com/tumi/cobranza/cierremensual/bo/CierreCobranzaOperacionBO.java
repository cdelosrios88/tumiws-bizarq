package pe.com.tumi.cobranza.cierremensual.bo;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.cierremensual.dao.CierreCobranzaOperacionDao;
import pe.com.tumi.cobranza.cierremensual.dao.impl.CierreCobranzaOperacionDaoIbatis;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacion;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CierreCobranzaOperacionBO {
	
	protected  static Logger log = Logger.getLogger(CierreCobranzaOperacionBO.class);
	private CierreCobranzaOperacionDao dao = (CierreCobranzaOperacionDao)TumiFactory.get(CierreCobranzaOperacionDaoIbatis.class);
	
	public List<CierreCobranzaOperacion> getListaCierreOperacionPorPkCierreCobranza(CierreCobranza o) throws BusinessException{
		List<CierreCobranzaOperacion> lista = null;
		try{
			lista = dao.getListaCierreOperacionPorPkCierreCobranza(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CierreCobranzaOperacion getCierreCobranzaOperacionPorPK(CierreCobranzaOperacionId o) throws BusinessException {
		List<CierreCobranzaOperacion> lista = null;
		CierreCobranzaOperacion cierreCobranzaOperacion = null;
		
		try{
			lista = dao.getCierreCobranzaOperacionPorPK(o);
			if(lista!=null){
				if(lista.size()==1){
					cierreCobranzaOperacion = lista.get(0);
				}else if(lista.size()==0){
					cierreCobranzaOperacion = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return cierreCobranzaOperacion;
	}
	
	public CierreCobranzaOperacion grabarCierreCobranzaOperacion(CierreCobranzaOperacion o) throws BusinessException {
		CierreCobranzaOperacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CierreCobranzaOperacion modificarCierreCobranzaOperacion(CierreCobranzaOperacion o) throws BusinessException {
		CierreCobranzaOperacion dto = null;
		try{
			dto = dao.modificar(o);
		   }catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
}
