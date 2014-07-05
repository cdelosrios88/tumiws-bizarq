package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.cierre.fdoFijo.domain.ControlFondoFijoAnula;
import pe.com.tumi.tesoreria.egreso.dao.ControlFondosFijosDao;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;

public class ControlFondosFijosDaoIbatis extends TumiDaoIbatis implements ControlFondosFijosDao{

	public ControlFondosFijos grabar(ControlFondosFijos o) throws DAOException{
		ControlFondosFijos dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public ControlFondosFijos modificar(ControlFondosFijos o) throws DAOException{
		ControlFondosFijos dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<ControlFondosFijos> getListaPorPk(Object o) throws DAOException{
		List<ControlFondosFijos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ControlFondosFijos> getListaParaItem(Object o) throws DAOException{
		List<ControlFondosFijos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaItem", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ControlFondosFijos> getListaPorBusqueda(Object o) throws DAOException{
		List<ControlFondosFijos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public ControlFondoFijoAnula grabarAnulaCierre(ControlFondoFijoAnula o) throws DAOException{
		ControlFondoFijoAnula dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarAnulaCierre", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ControlFondosFijos> getControlFondoFijo(Object o) throws DAOException{
		List<ControlFondosFijos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getControlFondoFijo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}