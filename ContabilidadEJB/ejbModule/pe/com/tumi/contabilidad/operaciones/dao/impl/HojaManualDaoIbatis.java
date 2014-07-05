package pe.com.tumi.contabilidad.operaciones.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.operaciones.dao.HojaManualDao;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class HojaManualDaoIbatis extends TumiDaoIbatis implements HojaManualDao{
	
	public HojaManual grabar(HojaManual o) throws DAOException{
		HojaManual dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public HojaManual modificar(HojaManual o) throws DAOException{
		HojaManual dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<HojaManual> getListaPorPk(Object o) throws DAOException{
		List<HojaManual> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<HojaManual> getBusqueda(Object o) throws DAOException{
		List<HojaManual> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
