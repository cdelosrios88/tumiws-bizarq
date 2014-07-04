package pe.com.tumi.credito.socio.estructura.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.dao.EstructuraDetalleDao;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class EstructuraDetalleDaoIbatis extends TumiDaoIbatis implements EstructuraDetalleDao{
	
	public EstructuraDetalle grabar(EstructuraDetalle o) throws DAOException {
		EstructuraDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public EstructuraDetalle modificar(EstructuraDetalle o) throws DAOException {
		EstructuraDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetallePorPK(Object o) throws DAOException{
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetalle(Object o) throws DAOException {
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetallePorIdEmpresaYIdNivelYIdSucursal(Object o) throws DAOException {
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorEmpresaYNivelYSucurs", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getConveEstrucDetAdministra(Object o) throws DAOException {
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getConveEstrucDetAdministra", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getConveEstrucDetPlanilla(Object o) throws DAOException {
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getConveEstrucDetPlanilla", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraComp> getListaEstructuraCompPorTipoCovenio(Object o) throws DAOException {
		List<EstructuraComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTipoConvenio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getListaPorPkEstructuraPorTipoSocioYModalidad(Object o) throws DAOException {
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEstructuraYTipoSocioYModalidad", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(Object o) throws DAOException {
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEstructuraDetallePorEstructuraYTipoSocioYModalidad", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getListaPorPkEstructuraYCasoYTipoSocioYModalidad(Object o) throws DAOException {
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorPkEstYCasoYTipSocYMod", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getListaPorCodExterno(Object o) throws DAOException {
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorCodExterno", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getListaPorEstructuraDetallePorCodSocioYAdministraYTipoSocYMod(Object o) throws DAOException {
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorCodSocYAdministraYTipoSocioYModalidad", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<EstructuraDetalle> getEstructuraDetallePorSucuSubsucuYCodigo(Object o) throws DAOException{
		List<EstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getEstructuraDetallePorSucuSubsucuYCodigo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}