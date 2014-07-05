package pe.com.tumi.parametro.general.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.parametro.general.dao.DetraccionDao;
import pe.com.tumi.parametro.general.domain.Detraccion;

public class DetraccionDaoIbatis extends TumiDaoIbatis implements DetraccionDao{

	
	public List<Detraccion> getListaTodos() throws DAOException{
		List<Detraccion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaTodos");
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}