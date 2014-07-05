package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.ConceptoPagoDao;
import pe.com.tumi.movimiento.concepto.dao.impl.ConceptoPagoDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPagoId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;

public class ConceptoPagoBO {
	private ConceptoPagoDao dao = (ConceptoPagoDao)TumiFactory.get(ConceptoPagoDaoIbatis.class);
	
	public ConceptoPago grabar(ConceptoPago o) throws BusinessException{
		ConceptoPago dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConceptoPago modificar(ConceptoPago o) throws BusinessException{
		ConceptoPago dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	

	public ConceptoPago getConceptoPagoPorPK(ConceptoPagoId pId) throws BusinessException{
		ConceptoPago domain = null;
		List<ConceptoPago> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intItemCtaCptoDet", pId.getIntItemCtaCptoDet());
			mapa.put("intItemConceptoPago", pId.getIntItemConceptoPago());

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
	
	
	public List<ConceptoPago> getListaConceptoPagoPorCuentaConceptoDet(CuentaConceptoDetalleId pId) throws BusinessException{
		List<ConceptoPago> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intItemCtaCptoDet", pId.getIntItemCtaCptoDet());

			lista = dao.getListaConceptoPagoPorCuentaConceptoDet(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 25-09-2013 
	 * OBTENER CONCEPTO PAGO POR CUENTA CONCEPTO DETALLE Y PERIODO
	 * @param pId
	 * @param intPeriodoIni
	 * @param intPeriodoFin
	 * @return
	 * @throws BusinessException
	 */
	public List<ConceptoPago> getListaConceptoPagoPorCtaCptoDetYPeriodo(CuentaConceptoDetalleId pId, Integer intPeriodoIni, Integer intPeriodoFin) throws BusinessException{
		List<ConceptoPago> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intItemCtaCptoDet", pId.getIntItemCtaCptoDet());
			mapa.put("intPeriodoInicio", intPeriodoIni);
			mapa.put("intPeriodoFin", intPeriodoFin);
			lista = dao.getListaConceptoPagoPorCtaCptoDetYPeriodo(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * JCHAVEZ 10.01.2014
	 * Recupera el ultimo registro de Concepto Pago por Pk Cuenta Concepto Detalle
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<ConceptoPago> getUltimoCptoPagoPorCuentaConceptoDet(CuentaConceptoDetalleId pId) throws BusinessException{
		List<ConceptoPago> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intItemCtaCptoDet", pId.getIntItemCtaCptoDet());

			lista = dao.getUltimoCptoPagoPorCuentaConceptoDet(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ConceptoPago> getListaConceptoPagoToCobranza(CuentaConceptoDetalleId pId) throws BusinessException{
		List<ConceptoPago> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intItemCtaCptoDet", pId.getIntItemCtaCptoDet());

			lista = dao.getListaConceptoPagotoCobranza(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
