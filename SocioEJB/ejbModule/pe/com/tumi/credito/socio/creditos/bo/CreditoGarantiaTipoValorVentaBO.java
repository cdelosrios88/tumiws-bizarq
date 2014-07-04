package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoGarantiaTipoValorVentaDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoGarantiaTipoValorVentaDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaTipoValorVenta;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaTipoValorVentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoGarantiaTipoValorVentaBO {
	
	private CreditoGarantiaTipoValorVentaDao dao = (CreditoGarantiaTipoValorVentaDao)TumiFactory.get(CreditoGarantiaTipoValorVentaDaoIbatis.class);
	
	public CreditoGarantiaTipoValorVenta grabarTipoValorVenta(CreditoGarantiaTipoValorVenta o) throws BusinessException{
		CreditoGarantiaTipoValorVenta dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoGarantiaTipoValorVenta modificarTipoValorVenta(CreditoGarantiaTipoValorVenta o) throws BusinessException{
		CreditoGarantiaTipoValorVenta dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoGarantiaTipoValorVenta getFinalidadPorPK(CreditoGarantiaTipoValorVentaId pPK) throws BusinessException{
		CreditoGarantiaTipoValorVenta domain = null;
		List<CreditoGarantiaTipoValorVenta> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod", 	pPK.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia",	pPK.getIntItemCreditoGarantia());
			mapa.put("intParaTipoValorVentaCod",pPK.getIntParaTipoValorVentaCod());
			lista = dao.getListaTipoValorVentaPorPK(mapa);
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
	
	public List<CreditoGarantiaTipoValorVenta> getListaTipoValorVentaPorPKCreditoGarantia(CreditoGarantiaId pPK) throws BusinessException{
		List<CreditoGarantiaTipoValorVenta> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod",	pPK.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia", 	pPK.getIntItemCreditoGarantia());
			
			lista = dao.getListaTipoValorVentaPorPKCreditoGarantia(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
