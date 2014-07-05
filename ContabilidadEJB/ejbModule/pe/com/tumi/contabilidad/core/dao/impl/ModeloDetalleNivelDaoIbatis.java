package pe.com.tumi.contabilidad.core.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.contabilidad.core.dao.ModeloDetalleNivelDao;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;

public class ModeloDetalleNivelDaoIbatis extends TumiDaoIbatis implements ModeloDetalleNivelDao{

	public ModeloDetalleNivel grabar(ModeloDetalleNivel o) throws DAOException{
		ModeloDetalleNivel dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public ModeloDetalleNivel modificar(ModeloDetalleNivel o) throws DAOException{
		ModeloDetalleNivel dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<ModeloDetalleNivel> getListaPorPk(Object o) throws DAOException{
		List<ModeloDetalleNivel> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ModeloDetalleNivel> getListaPorModeloDetalleId(Object o) throws DAOException{
		List<ModeloDetalleNivel> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorModeloDetalleId", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public ModeloDetalleNivel eliminar(ModeloDetalleNivelId o) throws DAOException{
		ModeloDetalleNivel dto = null;
		try{
			dto = new ModeloDetalleNivel();
			dto.setId(o);
			getSqlMapClientTemplate().delete(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	/**
	 * JCHAVEZ 30.12.2013
	 * Recupera Modelo Detalle Nivel segun filtros
	 */
	public List<ModeloDetalleNivelComp> getModeloGiroPrestamo(Object o) throws DAOException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getModeloGiroPrestamo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ModeloDetalleNivelId> getPkModeloXReprestamo(Object o) throws DAOException{
		List<ModeloDetalleNivelId> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPkModeloXReprestamo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ModeloDetalleNivelComp> getCampoXPkModelo(Object o) throws DAOException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCampoXPkModelo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ModeloDetalleNivelComp> getModeloGiroPrevision(Object o) throws DAOException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getModeloGiroPrevision", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ModeloDetalleNivelComp> getModeloPlanillaMovilidad(Object o) throws DAOException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getModeloPlanillaMovilidad", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	@Override
	public String getCuentaPorPagar(Object o)
			throws DAOException {
		String escalar = null;
		try
		{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getCuentaPorPagar", o);
			m = (HashMap<String, Object>)o;
			escalar = (String)m.get("strEscalar");
		}catch(Exception e)
		{
			throw new DAOException(e);
		}
		return escalar;
	}

	public List<ModeloDetalleNivel> getNumeroCuentaPrestamo(Object o) throws DAOException{
		List<ModeloDetalleNivel> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getNumeroCuentaPrestamo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<ModeloDetalleNivel> getNroCtaPrestamoSinCategoria(Object o) throws DAOException{
		List<ModeloDetalleNivel> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getNroCtaPrestamoSinCategoria", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * jchavez 27.05.2014
	 * obtiene el modelo de provision de fdo. de retiro
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ModeloDetalleNivelComp> getModeloProvisionRetiro(Object o) throws DAOException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getModeloProvisionRetiro", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * jchavez 27.05.2014
	 * obtiene el modelo de provision de fdo. de retiro interes generado y capitalizado
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ModeloDetalleNivelComp> getModeloProvRetiroInteres(Object o) throws DAOException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getModeloProvRetiroInteres", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	
