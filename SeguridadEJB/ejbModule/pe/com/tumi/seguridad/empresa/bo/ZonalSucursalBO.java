package pe.com.tumi.seguridad.empresa.bo;

import java.util.HashMap;

import java.util.List;

import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.dao.ZonalSucursalDao;
import pe.com.tumi.seguridad.empresa.dao.impl.ZonalSucursalDaoIbatis;

public class ZonalSucursalBO {
	
	private ZonalSucursalDao dao = (ZonalSucursalDao)TumiFactory.get(ZonalSucursalDaoIbatis.class);
	
	public Zonal getZonalSucursalPorIdZonal(Integer pPk) throws BusinessException{
		List<Zonal> lista = null;
		Zonal domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdzonal", pPk);
			lista = dao.getListaZonalSucursalPorIdZonal(mapa);
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
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
}
