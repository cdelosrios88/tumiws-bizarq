package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.InteresCanceladoDao;
import pe.com.tumi.movimiento.concepto.dao.impl.InteresCanceladoDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.InteresCanceladoId;

public class InteresCanceladoBO {
	private InteresCanceladoDao dao = (InteresCanceladoDao)TumiFactory.get(InteresCanceladoDaoIbatis.class);
	
	public InteresCancelado grabar(InteresCancelado o) throws BusinessException{
		InteresCancelado dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public InteresCancelado modificar(InteresCancelado o) throws BusinessException{
		InteresCancelado dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public InteresCancelado getInteresCanceladoPorPK(InteresCanceladoId pId) throws BusinessException{
		InteresCancelado domain = null;
		List<InteresCancelado> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemExpediente", pId.getIntItemExpediente());
			mapa.put("intItemExpedienteDetalle", pId.getIntItemExpedienteDetalle());
			mapa.put("intItemMovCtaCte", pId.getIntItemMovCtaCte());
			mapa.put("intItemCancelaInteres", pId.getIntItemCancelaInteres());

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
	
	public List<InteresCancelado> getListaPorPkExpedienteCredito(ExpedienteId pId) throws BusinessException{
		List<InteresCancelado> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 				pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemExpedienteDetalle());
			lista = dao.getListaPorExpediente(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera lista del ultimo Interes Cancelado en estado activo.
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public InteresCancelado getMaxInteresCanceladoPorExpediente(ExpedienteId pId) throws BusinessException{
		List<InteresCancelado> lista = null;
		InteresCancelado interesCancelado = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 				pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemExpedienteDetalle());
			lista = dao.getListMaxPorExpediente(mapa);
			if(lista != null && !lista.isEmpty()){
				interesCancelado = new InteresCancelado();
				interesCancelado = lista.get(0);
			}
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return interesCancelado;
	}
	
}
	
