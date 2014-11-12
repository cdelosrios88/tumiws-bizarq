/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-004       			27/09/2014     Christian De los Ríos        Se agregan nuevos métodos para regularizar la mayorización         
*/
package pe.com.tumi.contabilidad.cierre.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.LibroMayorDao;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class LibroMayorDaoIbatis extends TumiDaoIbatis implements LibroMayorDao{

	public LibroMayor grabar(LibroMayor o) throws DAOException {
		LibroMayor dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public LibroMayor modificar(LibroMayor o) throws DAOException{
		LibroMayor dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<LibroMayor> getListaPorPk(Object o) throws DAOException{
		List<LibroMayor> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<LibroMayor> getListaPorBuscar(Object o) throws DAOException{
		List<LibroMayor> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	//Inicio: REQ14-004 - bizarq - 16/09/2014
	public List<LibroMayor> getListMayorHist(Object o) throws DAOException{
		List<LibroMayor> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListMayorHist", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Integer processMayorizacion(Object o) throws DAOException{
		Integer escalar = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".processMayorizacion",o);
			m = (HashMap<String, Object>)o;
			escalar = (Integer)m.get("intResult");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return escalar;
	}
	
	public List<LibroMayor> getListAfterProcessedMayorizado(Object o) throws DAOException{
		List<LibroMayor> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListAfterProcessedMayorizado", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public LibroMayor eliminarMayorizado(LibroMayor o) throws DAOException{
		LibroMayor dto = null;
		try {
			//getSqlMapClientTemplate().update(getNameSpace() + ".eliminarMayorizado", o);
			getSqlMapClientTemplate().delete(getNameSpace() + ".eliminarMayorizado", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}
	//Inicio: REQ14-004 - bizarq - 16/09/2014
}