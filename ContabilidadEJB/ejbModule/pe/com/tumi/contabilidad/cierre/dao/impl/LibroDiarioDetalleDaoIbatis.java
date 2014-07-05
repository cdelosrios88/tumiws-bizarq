package pe.com.tumi.contabilidad.cierre.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.LibroDiarioDetalleDao;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class LibroDiarioDetalleDaoIbatis extends TumiDaoIbatis implements LibroDiarioDetalleDao{

	public LibroDiarioDetalle grabar(LibroDiarioDetalle o) throws DAOException {
		LibroDiarioDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public LibroDiarioDetalle modificar(LibroDiarioDetalle o) throws DAOException{
		LibroDiarioDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	@SuppressWarnings("unchecked")
	public List<LibroDiarioDetalle> getListaPorPk(Object o) throws DAOException{
		List<LibroDiarioDetalle> lista = null;
		try{
			lista = (List<LibroDiarioDetalle>) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<LibroDiarioDetalle> getListaPorLibroMayorYPlanCuenta(Object o) throws DAOException{
		List<LibroDiarioDetalle> lista = null;
		try{
			lista = (List<LibroDiarioDetalle>) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorLibroMayorYPlanContable", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<LibroDiarioDetalle> getListaPorLibroDiario(Object o) throws DAOException{
		List<LibroDiarioDetalle> lista = null;
		try{
			lista = (List<LibroDiarioDetalle>) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorLibroDiario", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<LibroDiarioDetalle> getListaPorBuscar(Object o) throws DAOException{
		List<LibroDiarioDetalle> lista = null;
		try{
			lista = (List<LibroDiarioDetalle>) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	/* Inicio - GTorresBroussetP - 05.abr.2014 */
	/* Buscar Libro Diario Detalle por Periodo y Documento */
	@SuppressWarnings("unchecked")
	public List<LibroDiarioDetalle> getListaPorPeriodoDocumento(Object o) throws DAOException{
		List<LibroDiarioDetalle> lista = null;
		try{
			lista = (List<LibroDiarioDetalle>) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPeriodoDocumento", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/* Fin - GTorresBroussetP - 05.abr.2014 */
}
