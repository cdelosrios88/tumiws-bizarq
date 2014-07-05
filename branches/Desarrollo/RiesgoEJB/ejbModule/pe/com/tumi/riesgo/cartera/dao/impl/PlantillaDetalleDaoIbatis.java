package pe.com.tumi.riesgo.cartera.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.cartera.dao.PlantillaDetalleDao;
import pe.com.tumi.riesgo.cartera.domain.PlantillaDetalle;

public class PlantillaDetalleDaoIbatis extends TumiDaoIbatis implements PlantillaDetalleDao{
	
	public PlantillaDetalle grabar(PlantillaDetalle o) throws DAOException {
		PlantillaDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public PlantillaDetalle modificar(PlantillaDetalle o) throws DAOException {
		PlantillaDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<PlantillaDetalle> getListaPorPk(Object o) throws DAOException{
		List<PlantillaDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PlantillaDetalle> getListaTodos(Object o) throws DAOException{
		List<PlantillaDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaTodos", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}