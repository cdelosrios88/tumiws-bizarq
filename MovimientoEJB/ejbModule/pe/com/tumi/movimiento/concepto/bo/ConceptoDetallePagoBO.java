package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.ConceptoDetallePagoDao;
import pe.com.tumi.movimiento.concepto.dao.impl.ConceptoDetallePagoDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePagoId;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPagoId;

public class ConceptoDetallePagoBO {
private ConceptoDetallePagoDao dao = (ConceptoDetallePagoDao)TumiFactory.get(ConceptoDetallePagoDaoIbatis.class);
	
	public ConceptoDetallePago grabar(ConceptoDetallePago o) throws BusinessException{
		ConceptoDetallePago dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConceptoDetallePago modificar(ConceptoDetallePago o) throws BusinessException{
		ConceptoDetallePago dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	

	public ConceptoDetallePago getConceptoDetallePagoPorPK(ConceptoDetallePagoId pId) throws BusinessException{
		ConceptoDetallePago domain = null;
		List<ConceptoDetallePago> lista = null;
		try{

			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intItemCtaCptoDet", pId.getIntItemCtaCptoDet());
			mapa.put("intItemConceptoPago", pId.getIntItemConceptoPago());
			mapa.put("intItemConceptoDetPago", pId.getIntItemConceptoDetPago());
			mapa.put("intItemMovCtaCte", pId.getIntItemMovCtaCte());
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
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 27-09-2013 
	 * OBTENER CONCEPTO DETALLE PAGO POR CONCEPTO PAGO PK 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<ConceptoDetallePago> getCptoDetPagoPorCptoPagoPK(ConceptoPagoId pId) throws BusinessException{
		List<ConceptoDetallePago> lista = null;
		try{

			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intItemCtaCptoDet", pId.getIntItemCtaCptoDet());
			mapa.put("intItemConceptoPago", pId.getIntItemConceptoPago());
			lista = dao.getListaPorCptoPagoPK(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
