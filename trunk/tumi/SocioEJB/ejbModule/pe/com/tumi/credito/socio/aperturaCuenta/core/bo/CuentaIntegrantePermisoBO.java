package pe.com.tumi.credito.socio.aperturaCuenta.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.CuentaIntegrantePermisoDao;
import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.impl.CuentaIntegrantePermisoDaoIbatis;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrantePermiso;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrantePermisoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CuentaIntegrantePermisoBO {
	private CuentaIntegrantePermisoDao dao = (CuentaIntegrantePermisoDao)TumiFactory.get(CuentaIntegrantePermisoDaoIbatis.class);
	
	public CuentaIntegrantePermiso grabar(CuentaIntegrantePermiso o) throws BusinessException{
		CuentaIntegrantePermiso dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaIntegrantePermiso modificar(CuentaIntegrantePermiso o) throws BusinessException{
		CuentaIntegrantePermiso dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaIntegrantePermiso getCuentaPorPK(CuentaIntegrantePermisoId pPK) throws BusinessException{
		CuentaIntegrantePermiso domain = null;
		List<CuentaIntegrantePermiso> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intCuenta", 		 	pPK.getIntCuenta());
			mapa.put("intPersonaIntegrante",pPK.getIntPersonaIntegrante());
			mapa.put("intItem",				pPK.getIntItem());
			lista = dao.getListaCuentaIntegrantePermisoPorPK(mapa);
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
	
	public List<CuentaIntegrantePermiso> getListaCuentaIntegrantePermisoPorCuentaIntegrante(CuentaIntegranteId pPK) throws BusinessException{
		List<CuentaIntegrantePermiso> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intCuenta", 		 	pPK.getIntCuenta());
			mapa.put("intPersonaIntegrante",pPK.getIntPersonaIntegrante());
			lista = dao.getListaCuentaIntegrantePermisoPorCuentaIntegrante(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
