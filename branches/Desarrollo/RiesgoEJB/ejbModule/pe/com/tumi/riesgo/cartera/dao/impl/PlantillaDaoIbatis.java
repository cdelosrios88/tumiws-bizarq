package pe.com.tumi.riesgo.cartera.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.cartera.dao.PlantillaDao;
import pe.com.tumi.riesgo.cartera.domain.Plantilla;

public class PlantillaDaoIbatis extends TumiDaoIbatis implements PlantillaDao{
	
	public Plantilla grabar(Plantilla o) throws DAOException {
		Plantilla dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Plantilla modificar(Plantilla o) throws DAOException {
		Plantilla dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Plantilla> getListaPorPk(Object o) throws DAOException{
		List<Plantilla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Plantilla> getListaTodos() throws DAOException{
		List<Plantilla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTodos");
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}