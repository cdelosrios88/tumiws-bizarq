package pe.com.tumi.credito.socio.captacion.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.CaptacionDao;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CaptacionDaoIbatis extends TumiDaoIbatis implements CaptacionDao{
	
	public Captacion grabar(Captacion o) throws DAOException {
		Captacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Captacion> getListaParaFiltro(Object o) throws DAOException{
		List<Captacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaFiltro", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	//legal
	public List<Captacion> getListaCaptacionPorPKCaptacion(Object o) throws DAOException{
		List<Captacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCaptacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public CaptacionId eliminarCaptacion(CaptacionId o) throws DAOException {
		CaptacionId dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".eliminar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	public Captacion modificarCaptacion(Captacion o) throws DAOException {
		Captacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Captacion> getListaCaptacionPorPKCaptacionOpcional(Object o) throws DAOException{
		List<Captacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCaptacionOpcional", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}