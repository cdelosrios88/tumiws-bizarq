package pe.com.tumi.riesgo.cartera.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.cartera.dao.ProciclicoDao;
import pe.com.tumi.riesgo.cartera.dao.impl.ProciclicoDaoIbatis;
import pe.com.tumi.riesgo.cartera.domain.Especificacion;
import pe.com.tumi.riesgo.cartera.domain.Prociclico;
import pe.com.tumi.riesgo.cartera.domain.ProciclicoId;

public class ProciclicoBO {
	
	private ProciclicoDao dao = (ProciclicoDao)TumiFactory.get(ProciclicoDaoIbatis.class);
	
	public Prociclico grabar(Prociclico o) throws BusinessException{
		Prociclico dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Prociclico modificar(Prociclico o) throws BusinessException{
		Prociclico dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Prociclico getPorPk(ProciclicoId pId) throws BusinessException{
		Prociclico domain = null;
		List<Prociclico> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemProciclico", pId.getIntItemProciclico());
			mapa.put("intItemEspecificacion", pId.getIntItemEspecificacion());
			mapa.put("intItemCartera", pId.getIntItemCartera());
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
	
	public Prociclico getPorEspecificacion(Especificacion espeficicacion) throws BusinessException{
		Prociclico domain = null;
		List<Prociclico> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemEspecificacion", espeficicacion.getId().getIntItemEspecificacion());
			mapa.put("intItemCartera", espeficicacion.getId().getIntItemCartera());
			lista = dao.getListaPorEspecificacion(mapa);
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
