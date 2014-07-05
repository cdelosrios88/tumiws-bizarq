package pe.com.tumi.credito.socio.ctacte.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.ctacte.dao.RangoDao;
import pe.com.tumi.credito.socio.ctacte.dao.impl.RangoDaoIbatis;
import pe.com.tumi.credito.socio.ctacte.domain.Rango;
import pe.com.tumi.credito.socio.ctacte.domain.RangoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class RangoBO {
	
	private RangoDao dao = (RangoDao)TumiFactory.get(RangoDaoIbatis.class);
	
	public Rango grabarRango(Rango o) throws BusinessException{
		Rango dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Rango modificarRango(Rango o) throws BusinessException{
		Rango dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Rango getRangoPorPK(RangoId pPK) throws BusinessException{
		Rango domain = null;
		List<Rango> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCtacteCod", pPK.getIntParaTipoCtacteCod());
			mapa.put("intItemCtacte", pPK.getIntItemCtacte());
			mapa.put("intItem", pPK.getIntItem());
			lista = dao.getListaRangoPorPK(mapa);
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
