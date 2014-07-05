package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.creditos.dao.CreditoTopeCaptacionDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoTopeCaptacionDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCreditoId;

public class CreditoTopeCaptacionBO {
	
	private CreditoTopeCaptacionDao dao = (CreditoTopeCaptacionDao)TumiFactory.get(CreditoTopeCaptacionDaoIbatis.class);
	
	public CreditoTopeCaptacion grabarCreditoTopeCaptacion(CreditoTopeCaptacion o) throws BusinessException{
		CreditoTopeCaptacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoTopeCaptacion modificarCreditoTopeCaptacion(CreditoTopeCaptacion o) throws BusinessException{
		CreditoTopeCaptacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoTopeCaptacion getCreditoTopeCaptacionPorPK(CreditoTopeCaptacionId pPK) throws BusinessException{
		CreditoTopeCaptacion domain = null;
		List<CreditoTopeCaptacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoMinMaxCod",	pPK.getIntParaTipoMinMaxCod());
			mapa.put("intParaTipoCaptacion",	pPK.getIntParaTipoCaptacion());
			lista = dao.getListaCreditoTopeCaptacionPorPK(mapa);
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
	
	public List<CreditoTopeCaptacion> getListaPorPKCreditoTipoMin(CreditoId pPK) throws BusinessException{
		List<CreditoTopeCaptacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoMinMaxCod",	Constante.PARAM_T_MINIMO);
			lista = dao.getListaCreditoTopeCaptacionTipoMinMaxPorPK(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CreditoTopeCaptacion> getListaPorPKCreditoTipoMax(CreditoId pPK) throws BusinessException{
		List<CreditoTopeCaptacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoMinMaxCod",	Constante.PARAM_T_MAXIMO);
			lista = dao.getListaCreditoTopeCaptacionTipoMinMaxPorPK(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CreditoTopeCaptacion> getListaPorPKCredito(CreditoId pPK) throws BusinessException{
		List<CreditoTopeCaptacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			
			lista = dao.getListaCondicionHabilPorPKCredito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void deletePorPk(CreditoTopeCaptacionId pPK) throws BusinessException{
		
		try {
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoMinMaxCod",	pPK.getIntParaTipoMinMaxCod());
			mapa.put("intParaTipoCaptacion",	0);
	 
			dao.deletePorPk(mapa);
		} catch(Exception e) {
			System.out.println("deletePorPk"+e);
			throw new BusinessException(e);
		}

	}
}
