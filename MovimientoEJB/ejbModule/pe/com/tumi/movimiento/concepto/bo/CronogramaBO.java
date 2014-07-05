package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.CronogramaDao;
import pe.com.tumi.movimiento.concepto.dao.impl.CronogramaDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CronogramaId;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;

public class CronogramaBO {
	
	private CronogramaDao dao = (CronogramaDao)TumiFactory.get(CronogramaDaoIbatis.class);
	
	public Cronograma grabarCronograma(Cronograma o) throws BusinessException{
		Cronograma dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Cronograma modificarCronograma(Cronograma o) throws BusinessException{
		Cronograma dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Cronograma getCronogramaPorPK(CronogramaId pId) throws BusinessException{
		Cronograma domain = null;
		List<Cronograma> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemCronograma", pId.getIntItemCronograma());
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
    /** CREADO 06/08/2013 
     * SE OBTIENE CRONOGRAMA POR PK DEL EXPEDIENTE(MOVIMIENTO)
     * **/		
	public List<Cronograma> getListaCronogramaPorPkExpediente(ExpedienteId pId) throws BusinessException{
		List<Cronograma> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemExpedienteDetalle",pId.getIntItemExpedienteDetalle());
			lista = dao.getListaPorPkExpediente(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Cronograma> getListaCronogramaDeVencidoPorPkExpedienteYPeriodoYPago(ExpedienteId pId,Integer intPeriodoPlanilla,Integer intParaFormaPagoCod) throws BusinessException{
		List<Cronograma> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemExpedienteDetalle",pId.getIntItemExpedienteDetalle());
			mapa.put("intPeriodoPlanilla",		intPeriodoPlanilla);
			mapa.put("intParaFormaPagoCod",		intParaFormaPagoCod);
			lista = dao.getListaDeVencidoPorPkExpedienteYPeriodoYPago(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}