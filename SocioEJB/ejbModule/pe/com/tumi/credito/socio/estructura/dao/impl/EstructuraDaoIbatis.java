package pe.com.tumi.credito.socio.estructura.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.dao.EstructuraDao;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class EstructuraDaoIbatis extends TumiDaoIbatis implements EstructuraDao{
	
	public Estructura grabar(Estructura o) throws DAOException {
		Estructura dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Estructura modificar(Estructura o) throws DAOException {
		Estructura dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Estructura> getListaEstructuraPorPK(Object o) throws DAOException{
		List<Estructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Estructura> getListaEstructuraBusqueda(Object o) throws DAOException {
		List <Estructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusquedaEstructura", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}

	public List<Estructura> getListaEstructuraPorNivelYCodigoRel(Object o) throws DAOException {
		List <Estructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorNivelYCodigoRel", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Estructura> getListaEstructuraPorNivelYCodigo(Object o) throws DAOException {
		List <Estructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorNivelYCodigo", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Estructura> getListaPorIdEmpresaYIdNivelYIdCasoIdSucursal(Object o) throws DAOException {
		List <Estructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorEmpresaYNiveYCasoYSuc", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Estructura> getListaPorIdEmpresaYIdCodigoYIdNivelYIdCasoIdSucursal(Object o) throws DAOException {
		List <Estructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorEmpYCodYNiveYCasoYSuc", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Estructura> getListaPorIdEmpresaYIdPersona(Object o) throws DAOException {
		List <Estructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorEmpresaYPersona", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Estructura> getListaPorIdEmpresaYIdCasoIdSucursal(Object o) throws DAOException {
		List <Estructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorEmpresaYCasoYSuc", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
}