package pe.com.tumi.parametro.general.bo;

import java.util.HashMap;


import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.dao.UbigeoDao;
import pe.com.tumi.parametro.general.dao.impl.UbigeoDaoIbatis;
import pe.com.tumi.parametro.general.domain.Ubigeo;

public class UbigeoBO {
	
	private UbigeoDao dao = (UbigeoDao)TumiFactory.get(UbigeoDaoIbatis.class);
	
	public List<Ubigeo> getListaUbigeoDeDepartamento() throws BusinessException{
		List<Ubigeo> lista = null;
		try{
			lista = dao.getListaUbigeoDeDepartamento();
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Ubigeo> getListaUbigeoDeProvinciaPorIdUbigeo(Integer pIntId) throws BusinessException{
		List<Ubigeo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pId", pIntId);
			lista = dao.getListaUbigeoDeProvinciaPorIdUbigeo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Ubigeo> getListaUbigeoDeDistritoPorIdUbigeo(Integer pIntId) throws BusinessException{
		List<Ubigeo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pId", pIntId);
			lista = dao.getListaUbigeoDeDistritoPorIdUbigeo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Ubigeo> getListaPorIdUbigeo(Integer pIntId) throws BusinessException{
		List<Ubigeo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pId", pIntId);
			lista = dao.getListaPorIdUbigeo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
