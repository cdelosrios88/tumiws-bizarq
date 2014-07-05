package pe.com.tumi.seguridad.empresa.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.empresa.domain.ZonalId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.dao.ZonalDao;
import pe.com.tumi.seguridad.empresa.dao.impl.ZonalDaoIbatis;

public class ZonalBO {
	
	private ZonalDao dao = (ZonalDao)TumiFactory.get(ZonalDaoIbatis.class);
	
	public Zonal grabarZonal(Zonal o) throws BusinessException {
		Zonal dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Zonal modificarZonal(Zonal o) throws BusinessException{
		Zonal dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Zonal getZonalPorPk(ZonalId pPk) throws BusinessException{
		List<Zonal> lista = null;
		Zonal domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pPk.getIntPersEmpresaPk());
			mapa.put("intIdzonal", pPk.getIntIdzonal());
			lista = dao.getListaZonalPorPk(mapa);
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
	
	public Zonal getZonalPorIdZonal(Integer pPk) throws BusinessException{
		List<Zonal> lista = null;
		Zonal domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdzonal", pPk);
			lista = dao.getListaZonalPorIdZonal(mapa);
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
	
	public Zonal getZonalPorIdPersona(Integer pPk) throws BusinessException{
		List<Zonal> lista = null;
		Zonal domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdPersona", pPk);
			lista = dao.getListaZonalPorIdPersona(mapa);
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
	
	public List<Zonal> getListaZonalDeBusqueda(Zonal pZonal) throws BusinessException{
		List<Zonal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pZonal.getId().getIntPersEmpresaPk());
			mapa.put("intIdzonal", pZonal.getId().getIntIdzonal());
			mapa.put("intPersPersonaPk", pZonal.getIntPersPersonaPk());
			mapa.put("intIdTipoZonal", pZonal.getIntIdTipoZonal());
			mapa.put("intPersPersonaResponsablePk", pZonal.getIntPersPersonaResponsablePk());
			if(pZonal.getSucursal()!= null)
				mapa.put("intIdSucursal", pZonal.getSucursal().getId().getIntIdSucursal());
			else
				mapa.put("intIdSucursal", null);
			mapa.put("intIdEstado", pZonal.getIntIdEstado());
			lista = dao.getListaZonalDeBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
