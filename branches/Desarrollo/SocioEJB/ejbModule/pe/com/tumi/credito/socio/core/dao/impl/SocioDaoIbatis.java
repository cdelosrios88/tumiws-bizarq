package pe.com.tumi.credito.socio.core.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.dao.SocioDao;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class SocioDaoIbatis extends TumiDaoIbatis implements SocioDao{
	
	public List<Socio> getListaSocioBusqueda(Object o) throws DAOException {
		List <Socio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSocioBusqueda", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	//Rodo
	public Integer getCantidadHijos(int idPersona) {
		
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("pIntTipoEstado", Constante.PARAM_T_ESTADO_COD);
		parms.put("pIntTipoVinculo", Constante.PARAM_T_TIPOVINCULO_HIJO);
		parms.put("pIntIdPersona", idPersona);
		
		Integer cantidad = (Integer)getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getCantidadHijos", parms);
		
		return cantidad;

	}
	
	public Date getFechaIngreso(int idPersona) {
		
		Map<String, Object> parms = new HashMap<String, Object>();
		int uno = 1;
		parms.put("pIntConstante", uno);
		parms.put("pIntIdPersona", idPersona);
		Date fechaIngreso = (Date)getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getFechaIngreso", parms);
		return fechaIngreso;
	}
//	

	
	
	public Socio grabar(Socio o) throws DAOException {
		Socio dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Socio modificar(Socio o) throws DAOException {
		Socio dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<Socio> getListaSocioPorPK(Object o) throws DAOException {
		List <Socio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}

	public List<Socio> getListaSocioPorIdEstructuraYTipoSocio(Object o) throws DAOException {
		List <Socio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorIdEstructuraYTipoSoc", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Socio> getListaDeTitularCuentaPorPkEstructuraYTipoSocio(Object o) throws DAOException {
		List <Socio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLsDeTituCtaPorPkEstYTipoSoc", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Socio> getListaPorEmpresaYINPersona(Object o) throws DAOException {
		List <Socio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpresaYINPersona", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	/**
	 * Cobranza envioPlanilla listadodeSocios
	 * Lista por TipoSocio Modalidad solo haberes e incentivos, estado activos, empresa 
	 */
	public List<Socio> getLPorIdEstructuraTSMAE(Object o) throws DAOException {
		List <Socio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLPorIdEstructuraTSMAE", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera los socios segun filtros de busqeda
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Socio> getListaSocioPorFiltrosBusq(Object o) throws DAOException {
		List <Socio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSocioPorFiltrosBusq", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Socio> getListaSocioEnEfectuado(Object o) throws DAOException {
		List <Socio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSocioEnEfectuado", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<Socio> getListSocioPorNombres(Object o) throws DAOException {
		List <Socio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListSocioPorNombres", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	@Override
	public String getUbigeoPorId(Integer idUbigeo) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
}