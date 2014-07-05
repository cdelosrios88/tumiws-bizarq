package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.CuentaDetalleBeneficioDao;
import pe.com.tumi.movimiento.concepto.dao.impl.CuentaDetalleBeneficioDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficioId;

public class CuentaDetalleBeneficioBO {
	
	private CuentaDetalleBeneficioDao dao = (CuentaDetalleBeneficioDao)TumiFactory.get(CuentaDetalleBeneficioDaoIbatis.class);
	
	public CuentaDetalleBeneficio grabarCuentaDetalleBeneficio(CuentaDetalleBeneficio o) throws BusinessException{
		CuentaDetalleBeneficio dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaDetalleBeneficio modificarCuentaDetalleBeneficio(CuentaDetalleBeneficio o) throws BusinessException{
		CuentaDetalleBeneficio dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaDetalleBeneficio getCuentaDetalleBeneficioPorPK(CuentaDetalleBeneficioId pId) throws BusinessException{
		CuentaDetalleBeneficio domain = null;
		List<CuentaDetalleBeneficio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intItemBeneficio", pId.getIntItemBeneficio());
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
	
	public List<CuentaDetalleBeneficio> getListaCuentaDetalleBeneficioPorPKCuentaConcepto(CuentaConceptoId pId) throws BusinessException{
		List<CuentaDetalleBeneficio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", 	pId.getIntItemCuentaConcepto());
			lista = dao.getListaPorPKConcepto(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CuentaDetalleBeneficio> getListaCuentaDetalleBeneficioPorPKCuenta(Integer intIdEmpresa, Integer intIdCuenta) throws BusinessException{
		List<CuentaDetalleBeneficio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		intIdEmpresa);
			mapa.put("intCuentaPk", 			intIdCuenta);
			lista = dao.getListaPorPKCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public void modificarPorBeneficiario(CuentaDetalleBeneficio o) throws BusinessException{
		try{
			dao.modificarPorBeneficiario(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
	
	
}
