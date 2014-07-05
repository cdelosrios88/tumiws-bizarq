package pe.com.tumi.riesgo.cartera.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.cartera.dao.ProvisionDao;
import pe.com.tumi.riesgo.cartera.dao.impl.ProvisionDaoIbatis;
import pe.com.tumi.riesgo.cartera.domain.Especificacion;
import pe.com.tumi.riesgo.cartera.domain.Provision;
import pe.com.tumi.riesgo.cartera.domain.ProvisionId;

public class ProvisionBO {
	
	private ProvisionDao dao = (ProvisionDao)TumiFactory.get(ProvisionDaoIbatis.class);
	
	public Provision grabar(Provision o) throws BusinessException{
		Provision dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Provision modificar(Provision o) throws BusinessException{
		Provision dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Provision getPorPk(ProvisionId pId) throws BusinessException{
		Provision domain = null;
		List<Provision> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemProvision", pId.getIntItemProvision());
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

	public List<Provision> getPorEspecificacion(Especificacion e) throws BusinessException{
		List<Provision> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemEspecificacion", e.getId().getIntItemEspecificacion());
			mapa.put("intItemCartera", e.getId().getIntItemCartera());
			lista = dao.getListaPorEspecificacion(mapa);				
		}catch(DAOException e2){
			throw new BusinessException(e2);
		}catch(Exception e1) {
			throw new BusinessException(e1);
		}
		return lista;
	}
}
