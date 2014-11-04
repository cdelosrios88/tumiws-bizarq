/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-006       			28/10/2014     Christian De los Ríos        Se agregó el método processExcelFile para leer un archivo xls         
*/
package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.ConciliacionDetalleDao;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;

public class ConciliacionDetalleDaoIbatis extends TumiDaoIbatis implements ConciliacionDetalleDao{

	public ConciliacionDetalle grabar(ConciliacionDetalle o) throws DAOException{
		ConciliacionDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public ConciliacionDetalle modificar(ConciliacionDetalle o) throws DAOException{
		ConciliacionDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<ConciliacionDetalle> getListaPorPk(Object o) throws DAOException{
		List<ConciliacionDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/*
	public List<ConciliacionDetalle> getListConcilDet(Object o) throws DAOException{
		List<ConciliacionDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListConcilDet", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	*/
	
}