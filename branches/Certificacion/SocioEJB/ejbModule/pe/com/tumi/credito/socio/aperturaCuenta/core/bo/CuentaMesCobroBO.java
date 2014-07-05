package pe.com.tumi.credito.socio.aperturaCuenta.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.CuentaMesCobroDao;
import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.impl.CuentaMesCobroDaoIbatis;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaMesCobro;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaMesCobroId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CuentaMesCobroBO {
	private CuentaMesCobroDao dao = (CuentaMesCobroDao)TumiFactory.get(CuentaMesCobroDaoIbatis.class);
	
	public CuentaMesCobro grabar(CuentaMesCobro o) throws BusinessException{
		CuentaMesCobro dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaMesCobro modificar(CuentaMesCobro o) throws BusinessException{
		CuentaMesCobro dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaMesCobro getCuentaPorPK(CuentaMesCobroId pPK) throws BusinessException{
		CuentaMesCobro domain = null;
		List<CuentaMesCobro> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intCuenta", 		 	pPK.getIntCuenta());
			mapa.put("intParaMesCod",		pPK.getIntParaMesCod());
			lista = dao.getListaCuentaMesCobroPorPK(mapa);
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
