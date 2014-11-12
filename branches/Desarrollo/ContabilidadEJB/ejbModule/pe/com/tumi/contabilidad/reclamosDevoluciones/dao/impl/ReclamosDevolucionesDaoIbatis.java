package pe.com.tumi.contabilidad.reclamosDevoluciones.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.reclamosDevoluciones.dao.ReclamosDevolucionesDao;
import pe.com.tumi.contabilidad.reclamosDevoluciones.domain.ReclamosDevoluciones;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 08.08.2014 /
public class ReclamosDevolucionesDaoIbatis extends TumiDaoIbatis implements ReclamosDevolucionesDao{
	
	public ReclamosDevoluciones grabar(ReclamosDevoluciones o) throws DAOException{
		ReclamosDevoluciones dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ReclamosDevoluciones modificar(ReclamosDevoluciones o) throws DAOException{
		ReclamosDevoluciones dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}
	public List<ReclamosDevoluciones> getListaParametro(Object o) throws DAOException {
		List<ReclamosDevoluciones> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParametro", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<ReclamosDevoluciones> getBuscar(Object o) throws DAOException {
		List<ReclamosDevoluciones> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
