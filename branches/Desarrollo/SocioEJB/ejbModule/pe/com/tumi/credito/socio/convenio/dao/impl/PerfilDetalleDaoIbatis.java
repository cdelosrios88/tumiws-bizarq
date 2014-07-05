package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PerfilDetalleDao;
import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class PerfilDetalleDaoIbatis extends TumiDaoIbatis implements PerfilDetalleDao{
	
	public PerfilDetalle grabar(PerfilDetalle o) throws DAOException {
		PerfilDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public PerfilDetalle modificar(PerfilDetalle o) throws DAOException {
		PerfilDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<PerfilDetalle> getListaPerfilDetallePorPK(Object o) throws DAOException{
		List<PerfilDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PerfilDetalle> getListaPerfilDetallePorPKPerfil(Object o) throws DAOException{
		List<PerfilDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkPerfil", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}