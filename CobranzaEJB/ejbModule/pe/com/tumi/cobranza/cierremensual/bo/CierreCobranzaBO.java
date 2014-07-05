package pe.com.tumi.cobranza.cierremensual.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.cierremensual.dao.CierreCobranzaDao;
import pe.com.tumi.cobranza.cierremensual.dao.impl.CierreCobranzaDaoIbatis;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.composite.CierreCobranzaComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CierreCobranzaBO {
	protected  static Logger log = Logger.getLogger(CierreCobranzaBO.class);
	private CierreCobranzaDao dao = (CierreCobranzaDao)TumiFactory.get(CierreCobranzaDaoIbatis.class);
	
	public List<CierreCobranzaComp> getListaCierreCobranza(CierreCobranzaComp o) throws BusinessException{
		List<CierreCobranzaComp> lista = null;
		HashMap map = new HashMap();
		map.put("intAnio", 				o.getCierreCobranza().getIntParaPeriodoCierre().toString().substring(0, 4));
		map.put("intMes", 				o.getCierreCobranza().getIntParaPeriodoCierre().toString().substring(4));
		map.put("intParaTipoOperacion", o.getCierreCobranza().getIntParaTipoRegistro());
		map.put("intParaEstadoCierre", 	o.getIntParaEstado());
		
		try{
			lista = dao.getListaCierreCobranza(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CierreCobranza getCierreCobranzaPorPK(CierreCobranza o) throws BusinessException {
		List<CierreCobranza> lista = null;
		CierreCobranza cierreCobranza = null;
		
		HashMap map = new HashMap();
		map.put("intIdEmpresaCierre", 	o.getIntEmpresaCierreCobranza());
		map.put("intIdPeriodoCierre", 	o.getIntParaPeriodoCierre());
		map.put("intIdTipoRegistro", 	o.getIntParaTipoRegistro());
		try{
			lista = dao.getCierreCobranzaPorPK(map);
			if(lista!=null){
				if(lista.size()==1){
					cierreCobranza = lista.get(0);
				}else if(lista.size()==0){
					cierreCobranza = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return cierreCobranza;
	}
	
	public CierreCobranza grabarCierreCobranza(CierreCobranza o) throws BusinessException {
		CierreCobranza dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CierreCobranza modificarCierreCobranza(CierreCobranza o) throws BusinessException {
		CierreCobranza dto = null;
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
