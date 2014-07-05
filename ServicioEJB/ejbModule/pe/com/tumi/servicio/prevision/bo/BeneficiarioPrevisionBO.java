package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.BeneficiarioPrevisionDao;
import pe.com.tumi.servicio.prevision.dao.impl.BeneficiarioPrevisionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevisionId;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

public class BeneficiarioPrevisionBO {
	
	private BeneficiarioPrevisionDao dao = (BeneficiarioPrevisionDao)TumiFactory.get(BeneficiarioPrevisionDaoIbatis.class);
	
	public BeneficiarioPrevision grabar(BeneficiarioPrevision o) throws BusinessException{
		BeneficiarioPrevision dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public BeneficiarioPrevision modificar(BeneficiarioPrevision o) throws BusinessException{
		BeneficiarioPrevision dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public BeneficiarioPrevision getPorPk(BeneficiarioPrevisionId pId) throws BusinessException{
		BeneficiarioPrevision domain = null;
		List<BeneficiarioPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevision", pId.getIntPersEmpresaPrevision());
			mapa.put("intCuenta", pId.getIntCuenta());
			mapa.put("intItemExpediente", pId.getIntItemExpediente());
			mapa.put("intItemBeneficiario", pId.getIntItemBeneficiario());
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
	
	public List<BeneficiarioPrevision> getPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException{
		List<BeneficiarioPrevision> lista = null;
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
	
	public BeneficiarioPrevision getPorEgreso(Egreso egreso) throws BusinessException{
		BeneficiarioPrevision domain = null;
		List<BeneficiarioPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaEgreso", egreso.getId().getIntPersEmpresaEgreso());
			mapa.put("intItemEgresoGeneral", egreso.getId().getIntItemEgresoGeneral());
			lista = dao.getListaPorEgreso(mapa);
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
	//rVillarreal
	public List<BeneficiarioPrevision> getListaNombreCompletoBeneficiario(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException{
		List<BeneficiarioPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevision", intPersEmpresaPk);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intExpediente", intExpediente);
			lista = dao.getListaNombreCompletoBeneficiario(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<BeneficiarioPrevision> getListaVinculo(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException{
		List<BeneficiarioPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevision", intPersEmpresaPk);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intExpediente", intExpediente);
			lista = dao.getListaVinculo(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}