package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.FallecidoPrevisionDao;
import pe.com.tumi.servicio.prevision.dao.impl.FallecidoPrevisionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevisionId;

public class FallecidoPrevisionBO {

	
	private FallecidoPrevisionDao dao = (FallecidoPrevisionDao)TumiFactory.get(FallecidoPrevisionDaoIbatis.class);
	
	public FallecidoPrevision grabar(FallecidoPrevision o) throws BusinessException{
		FallecidoPrevision dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			System.out.println("DAOException DAOException --> "+e);
			throw new BusinessException(e);
			
		}catch(Exception e) {
			System.out.println("Exception Exception --> "+e);
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public FallecidoPrevision modificar(FallecidoPrevision o) throws BusinessException{
		FallecidoPrevision dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public FallecidoPrevision getPorPk(FallecidoPrevisionId pId) throws BusinessException{
		FallecidoPrevision domain = null;
		List<FallecidoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevision", pId.getIntPersEmpresaPrevision());
			mapa.put("intCuenta", pId.getIntCuenta());
			mapa.put("intItemExpediente", pId.getIntItemExpediente());
			mapa.put("intItemFallecido", pId.getIntItemFallecido());
			lista = dao.getListaPorPk(mapa);
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
	
	public List<FallecidoPrevision> getPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException{
		List<FallecidoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevision", expedientePrevision.getId().getIntPersEmpresaPk());
			mapa.put("intCuenta", expedientePrevision.getId().getIntCuentaPk());
			mapa.put("intItemExpediente", expedientePrevision.getId().getIntItemExpediente());
			lista = dao.getListaPorExpediente(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<FallecidoPrevision> getListaNombreCompletoAes(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException{
		List<FallecidoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevision", intPersEmpresaPk);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intItemExpediente", intExpediente);
			lista = dao.getListaNombreCompletoAes(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<FallecidoPrevision> getListaVinculoAes(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException{
		List<FallecidoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevision", intPersEmpresaPk);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intExpediente", intExpediente);
			lista = dao.getListaVinculoAes(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
