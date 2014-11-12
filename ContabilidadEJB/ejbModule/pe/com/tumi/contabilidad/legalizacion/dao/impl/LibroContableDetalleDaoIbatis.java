package pe.com.tumi.contabilidad.legalizacion.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.legalizacion.dao.LibroContableDetalleDao;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalle;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalleComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class LibroContableDetalleDaoIbatis extends TumiDaoIbatis implements LibroContableDetalleDao{

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	@Override
	public LibroContableDetalle grabar(LibroContableDetalle o) throws DAOException {
		LibroContableDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	@Override
	public LibroContableDetalle modificar(LibroContableDetalle o) throws DAOException {
		LibroContableDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 09.09.2014
	public void eliminar(Object o) throws DAOException{
		try{
			getSqlMapClientTemplate().queryForList(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		};
	}
	
	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<LibroContableDetalleComp> getListaLibroContableDetalle(Object o) throws DAOException{
		List<LibroContableDetalleComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaLibroContableDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 04.09.2014
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<LibroContableDetalleComp> getListaUltimoFolioDetalle(Object o) throws DAOException{
		List<LibroContableDetalleComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaUltimoFolioDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 06.09.2014
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<LibroContableDetalleComp> getListaLibroContaDetaNulo(Object o) throws DAOException{
		List<LibroContableDetalleComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaLibroContaDetaNulo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}