package pe.com.tumi.parametro.general.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.dao.ArchivoDao;
import pe.com.tumi.parametro.general.dao.TipoCambioDao;
import pe.com.tumi.parametro.general.dao.impl.ArchivoDaoIbatis;
import pe.com.tumi.parametro.general.dao.impl.TipoCambioDaoIbatis;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.domain.TipoCambioId;

public class TipoCambioBO {
	
	private TipoCambioDao dao = (TipoCambioDao)TumiFactory.get(TipoCambioDaoIbatis.class);
	
	public TipoCambio grabarTipoCambio(TipoCambio o) throws BusinessException{
		TipoCambio dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public TipoCambio modificarTipoCambio(TipoCambio o) throws BusinessException{
		TipoCambio dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public TipoCambio getTipoCambioPorPK(TipoCambioId pId) throws BusinessException{
		TipoCambio domain = null;
		List<TipoCambio> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("pDtParaFecha", pId.getDtParaFecha());
			mapa.put("pIntParaClaseTipoCambio", pId.getIntParaClaseTipoCambio());
			mapa.put("pIntParaMoneda", pId.getIntParaMoneda());
			//mapa.put("pIntParaMoneda", 2);
			lista = dao.getListaPorPK(mapa);
			if(lista!=null && !lista.isEmpty()){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}else{
				lista = null;
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
	
	public List<TipoCambio> getTipoCambioBusqueda(TipoCambio pTipoCambio) throws BusinessException{
		List<TipoCambio> lista = null;
		try{
			System.out.println("pTipoCambio.getDtFechaTCDesde(): "+pTipoCambio.getDtFechaTCDesde());
			System.out.println("pTipoCambio.getDtFechaTCHasta(): "+pTipoCambio.getDtFechaTCHasta());
			System.out.println("pTipoCambio.getId().getIntParaClaseTipoCambio(): "+pTipoCambio.getId().getIntParaClaseTipoCambio());
			System.out.println("pTipoCambio.getId().getIntParaMoneda(): "+pTipoCambio.getId().getIntParaMoneda());
			HashMap mapa = new HashMap();
			mapa.put("pDtFechaTCDesde", pTipoCambio.getDtFechaTCDesde());
			mapa.put("pDtFechaTCHasta", pTipoCambio.getDtFechaTCHasta());
			mapa.put("pIntParaClaseTipoCambio", pTipoCambio.getId().getIntParaClaseTipoCambio());
			mapa.put("pIntParaMoneda", pTipoCambio.getId().getIntParaMoneda());
			lista = dao.getBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public TipoCambio getTipoCambioActualPorClaseYMoneda(Integer pIntPersEmpresa,Integer pIntParaClaseTipoCambio,Integer pIntParaMoneda) throws BusinessException{
		TipoCambio domain = null;
		List<TipoCambio> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntPersEmpresa", pIntPersEmpresa);
			mapa.put("pIntParaClaseTipoCambio", pIntParaClaseTipoCambio);
			mapa.put("pIntParaMoneda", pIntParaMoneda);
			lista = dao.getTipoCambioActual(mapa);
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
