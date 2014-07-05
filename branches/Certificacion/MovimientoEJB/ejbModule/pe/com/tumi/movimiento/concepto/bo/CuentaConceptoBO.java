package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.CuentaConceptoDao;
import pe.com.tumi.movimiento.concepto.dao.impl.CuentaConceptoDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;

public class CuentaConceptoBO {
	
	private CuentaConceptoDao dao = (CuentaConceptoDao)TumiFactory.get(CuentaConceptoDaoIbatis.class);
	
	public CuentaConcepto grabarCuentaConcepto(CuentaConcepto o) throws BusinessException{
		CuentaConcepto dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaConcepto modificarCuentaConcepto(CuentaConcepto o) throws BusinessException{
		CuentaConcepto dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaConcepto getCuentaConceptoPorPK(CuentaConceptoId pId) throws BusinessException{
		CuentaConcepto domain = null;
		List<CuentaConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			lista = dao.getListaPorPK(mapa);
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
	
	public List<CuentaConcepto> getListaCuentaConceptoPorPKCuenta(CuentaId pId) throws BusinessException{
		List<CuentaConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 	pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 		pId.getIntCuenta());
			lista = dao.getListaPorPKCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}