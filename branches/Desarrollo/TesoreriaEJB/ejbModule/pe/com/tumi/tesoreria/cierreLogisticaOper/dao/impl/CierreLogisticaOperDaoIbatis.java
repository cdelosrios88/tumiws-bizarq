package pe.com.tumi.tesoreria.cierreLogisticaOper.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.tesoreria.cierreLogisticaOper.dao.CierreLogisticaOperDao;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.CierreLogisticaOper;

public class CierreLogisticaOperDaoIbatis extends TumiDaoIbatis implements CierreLogisticaOperDao{

	public CierreLogisticaOper grabarCierreLogistica(CierreLogisticaOper o) throws DAOException{
		CierreLogisticaOper dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarCierreLogistica", o);
			dto = o;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CierreLogisticaOper modificarCierreLogistica(CierreLogisticaOper o) throws DAOException{
		CierreLogisticaOper dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificarCierreLogistica", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CierreLogisticaOper> getListaCierreLogisticaVista(Object o) throws DAOException {
		List<CierreLogisticaOper> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCierreLogistica", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreLogisticaOper> getListaCierreLogisticaValidar(Object o) throws DAOException {
		List<CierreLogisticaOper> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaLogis", o);
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreLogisticaOper> getListaBuscarCierre(Object o) throws DAOException {
		List<CierreLogisticaOper> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBuscarCierre", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
