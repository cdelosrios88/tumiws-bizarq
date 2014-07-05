package pe.com.tumi.contabilidad.cierre.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.AnexoDetalleDao;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class AnexoDetalleDaoIbatis extends TumiDaoIbatis implements AnexoDetalleDao{

	public AnexoDetalle grabar(AnexoDetalle o) throws DAOException {
		AnexoDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AnexoDetalle modificar(AnexoDetalle o) throws DAOException{
		AnexoDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<AnexoDetalle> getListaPorPk(Object o) throws DAOException{
		List<AnexoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public void eliminar(Object o) throws DAOException{
		try{
			getSqlMapClientTemplate().queryForList(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
	}
	
	public List<AnexoDetalle> getListaPorAnexo(Object o) throws DAOException{
		List<AnexoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorAnexo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
