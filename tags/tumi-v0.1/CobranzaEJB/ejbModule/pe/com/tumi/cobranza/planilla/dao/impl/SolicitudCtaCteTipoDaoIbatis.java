package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;


import pe.com.tumi.cobranza.planilla.dao.SolicitudCtaCteTipoDao;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class SolicitudCtaCteTipoDaoIbatis extends TumiDaoIbatis implements SolicitudCtaCteTipoDao{

	public SolicitudCtaCteTipo grabar(SolicitudCtaCteTipo o) throws DAOException{
		SolicitudCtaCteTipo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public SolicitudCtaCteTipo modificar(SolicitudCtaCteTipo o) throws DAOException{
		SolicitudCtaCteTipo dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<SolicitudCtaCteTipo> getListaPorPk(Object o) throws DAOException{
		List<SolicitudCtaCteTipo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SolicitudCtaCteTipo> getListaPorSolCtacteSinEstado(Object o) throws DAOException {
		List<SolicitudCtaCteTipo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorSolCtacteSinEstado",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	public List<SolicitudCtaCteTipo> getListaPorSolicitudCtacte(Object o) throws DAOException {
		List<SolicitudCtaCteTipo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorSolicitudCtacte",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	
	public void eliminarPorSolicitudCtacte(Object o) throws DAOException {
		try{
			   getSqlMapClientTemplate().update(getNameSpace() + ".eliminarPorSolicitudCtacte",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
	
	}
	
	
}	
