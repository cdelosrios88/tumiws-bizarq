package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.CuentaConceptoDetalleDao;
import pe.com.tumi.movimiento.concepto.dao.impl.CuentaConceptoDetalleDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;

public class CuentaConceptoDetalleBO {
	
	private CuentaConceptoDetalleDao dao = (CuentaConceptoDetalleDao)TumiFactory.get(CuentaConceptoDetalleDaoIbatis.class);
	
	public CuentaConceptoDetalle grabarCuentaConceptoDetalle(CuentaConceptoDetalle o) throws BusinessException{
		CuentaConceptoDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaConceptoDetalle modificarCuentaConceptoDetalle(CuentaConceptoDetalle o) throws BusinessException{
		CuentaConceptoDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaConceptoDetalle getCuentaConceptoDetallePorPK(CuentaConceptoDetalleId pId) throws BusinessException{
		CuentaConceptoDetalle domain = null;
		List<CuentaConceptoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intItemCtaCptoDet", pId.getIntItemCtaCptoDet());
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
	
	public List<CuentaConceptoDetalle> getListaCuentaConceptoPorPKCuenta(CuentaConceptoId pId) throws BusinessException{
		List<CuentaConceptoDetalle> lista = null;
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
	
	public CuentaConceptoDetalle getCuentaConceptoDetallePorPKYTipoConcepto(CuentaConceptoDetalle o) throws BusinessException{
		CuentaConceptoDetalle domain = null;
		List<CuentaConceptoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		o.getId().getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			o.getId().getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", 	o.getId().getIntItemCuentaConcepto());
			mapa.put("intParaTipoConceptoCod", 	o.getIntParaTipoConceptoCod());
			lista = dao.getListaPorPKYTipoConcepto(mapa);
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
	
	
	public void modificarDetallePorConcepto(CuentaConceptoDetalle o) throws BusinessException{
		try{
			dao.modificarDetallePorConcepto(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public List<CuentaConceptoDetalle> getCuentaConceptoDetallePorPKCuentaYTipoConcepto(CuentaConceptoDetalle o) throws BusinessException{
		
		List<CuentaConceptoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		o.getId().getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			o.getId().getIntCuentaPk());
			mapa.put("intParaTipoConceptoCod", 	o.getIntParaTipoConceptoCod());
			lista = dao.getListaPorPKCuentaYTipoConcepto(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public List<CuentaConceptoDetalle> getMaxCuentaConceptoDetPorPKCuentaConcepto(CuentaConceptoId pId) throws BusinessException{
		List<CuentaConceptoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", 	pId.getIntItemCuentaConcepto());

			lista = dao.getMaxCuentaConceptoDetPorPKCuentaConcepto(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * cobranza efectuadoController 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<CuentaConceptoDetalle> getCuentaConceptoDetofCobranza(CuentaConceptoId pId) throws BusinessException{
		List<CuentaConceptoDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", 	pId.getIntItemCuentaConcepto());

			lista = dao.getCuentaConceptoDetofCobranza(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
