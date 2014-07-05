package pe.com.tumi.seguridad.empresa.dao.impl;

import java.util.List;

import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.empresa.dao.ZonalSucursalDao;

public class ZonalSucursalDaoIbatis extends TumiDaoIbatis implements ZonalSucursalDao{
	
	public List<Zonal> getListaZonalSucursalPorIdZonal(Object o) throws DAOException{
		List<Zonal> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdZonal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}
