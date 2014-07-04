package pe.com.tumi.credito.socio.estructura.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.dao.TercerosDao;
import pe.com.tumi.credito.socio.estructura.domain.Terceros;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class TercerosDaoIbatis extends TumiDaoIbatis implements TercerosDao{
	
	public List<Terceros> getListaFilaTercerosPorDNI(Object o) throws DAOException {
		List <Terceros> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFilaTercerosPorDNI", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Terceros> getListaColumnaTercerosPorDNI(Object o) throws DAOException {
		List <Terceros> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaColumnaTercerosPorDNI", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Terceros> getPorItemDNI(Object o) throws DAOException {
		List <Terceros> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPorItemDNI", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
}