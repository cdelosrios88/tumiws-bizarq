package pe.com.tumi.contabilidad.cierre.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.AnexoDetalleDao;
import pe.com.tumi.contabilidad.cierre.dao.AnexoDetalleOperadorDao;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleOperador;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class AnexoDetalleOperadorDaoIbatis extends TumiDaoIbatis implements AnexoDetalleOperadorDao{

	public AnexoDetalleOperador grabar(AnexoDetalleOperador o) throws DAOException {
		AnexoDetalleOperador dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AnexoDetalleOperador modificar(AnexoDetalleOperador o) throws DAOException{
		AnexoDetalleOperador dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<AnexoDetalleOperador> getListaPorPk(Object o) throws DAOException{
		List<AnexoDetalleOperador> lista = null;
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
	
	public List<AnexoDetalleOperador> getListaPorAnexoDetalle(Object o) throws DAOException{
		List<AnexoDetalleOperador> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorAnexoDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
