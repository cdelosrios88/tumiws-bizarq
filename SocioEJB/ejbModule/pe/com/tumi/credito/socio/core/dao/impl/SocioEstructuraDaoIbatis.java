package pe.com.tumi.credito.socio.core.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.core.dao.SocioDao;
import pe.com.tumi.credito.socio.core.dao.SocioEstructuraDao;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.dao.EstructuraDao;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.contacto.domain.Comunicacion;

public class SocioEstructuraDaoIbatis extends TumiDaoIbatis implements SocioEstructuraDao{
	
	public List<SocioEstructura> getListaSocioEstructuraBusq(Object o) throws DAOException {
		List <SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSocioEstructuraBusq", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public SocioEstructura grabar(SocioEstructura o) throws DAOException {
		SocioEstructura dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public SocioEstructura modificar(SocioEstructura o) throws DAOException {
		SocioEstructura dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<SocioEstructura> getListaSocioEstructuraPorPK(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaPorPkSocioYPkEstructuraYTipoEstructura(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorPkSocioYPkEstYTipoEst", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaSocioEstructuraPorSocioPK(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorSocioPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<SocioEstructura> getListaSocioEstructuraPorPkSocioYTipoEstructura(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorPkSocioYTipoEstruct", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaPorPkSocioYPkEstructura(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPorPkSocioYPkEst", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera Socio Estructura por persona_n_pk, pers_empresa_n_pk y para_estado_n_cod.
	 */
	public List<SocioEstructura> getListaSocioEstructuraPorSocioPKyActivo(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorSocioPKyActivo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaSocioEstructuraPorPKTipoSocio(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPKTipoSocio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/*public List<SocioEstructura> getListaSocEstPorSocioPKActivoTipoSocio(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaXSocioPKActivoTipo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}*/
	
	public List<SocioEstructura> getListaXSocioPKActivoTipoSocio(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaXSocioPKActivoTipoSocio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaXNivelCodigoNoCas(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaXNivelCodigoNoCas", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<SocioEstructura> getListaXNivelCodigosoloCas(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaXNivelCodigosoloCas", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaSocioEstructuraAgregarSocioEfectuado(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAgregarSocioEfectuado", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getLstBySocioAndEst(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstBySocioAndEst", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaXAdminySubAdminHABERINCENT(Object o)throws DAOException
	{
		List<SocioEstructura> lista = null;
		try{
			lista = (List)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaHABERINCENT",o);
		}catch(Exception e){
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaXNivelCodigosoloHaberIncentivo(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaXNivelCodigosoloHaberIncentivo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaPorCodPersonaOfEnviado(Object o) throws DAOException{
		List<SocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCodPersonaOfEnviado", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaXAdminySubAdminSOLOCAS(Object o)throws DAOException
	{
		List<SocioEstructura> lista = null;
		try{
			lista = (List)getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaXAdminySubAdminSOLOCAS",o);
		}catch(Exception e){
			throw new DAOException(e);
		}
		return lista;
	}
}