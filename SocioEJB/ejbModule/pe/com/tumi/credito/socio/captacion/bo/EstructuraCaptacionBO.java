package pe.com.tumi.credito.socio.captacion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.EstructuraCaptacionDao;
import pe.com.tumi.credito.socio.captacion.dao.impl.EstructuraCaptacionDaoIbatis;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.EstructuraCaptacion;
import pe.com.tumi.credito.socio.captacion.domain.EstructuraCaptacionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class EstructuraCaptacionBO {
	
	private EstructuraCaptacionDao dao = (EstructuraCaptacionDao)TumiFactory.get(EstructuraCaptacionDaoIbatis.class);
	
	public EstructuraCaptacion grabarEstructuraCaptacion(EstructuraCaptacion o) throws BusinessException{
		EstructuraCaptacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstructuraCaptacion modificarEstructuraCaptacion(EstructuraCaptacion o) throws BusinessException{
		EstructuraCaptacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstructuraCaptacion getEstructuraCaptacionPorPK(EstructuraCaptacionId pPK) throws BusinessException{
		EstructuraCaptacion domain = null;
		List<EstructuraCaptacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intConvenio", pPK.getIntConvenio());
			mapa.put("intNivel", pPK.getIntNivel());
			mapa.put("intCodigo", pPK.getIntCodigo());
			mapa.put("intCaso", pPK.getIntCaso());
			mapa.put("intItemCaso", pPK.getIntItemCaso());
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", pPK.getIntItem());
			lista = dao.getListaEstructuraCaptacionPorPK(mapa);
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
	
	public List<EstructuraCaptacion> getListaEstructuraCaptacionPorPKCaptacion(CaptacionId pPK) throws BusinessException{
		List<EstructuraCaptacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", pPK.getIntItem());
			lista = dao.getListaEstructuraCaptacionPorPKCaptacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
