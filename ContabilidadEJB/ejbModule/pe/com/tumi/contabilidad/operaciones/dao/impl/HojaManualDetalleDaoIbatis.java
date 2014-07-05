package pe.com.tumi.contabilidad.operaciones.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;
import pe.com.tumi.contabilidad.operaciones.dao.HojaManualDao;
import pe.com.tumi.contabilidad.operaciones.dao.HojaManualDetalleDao;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalle;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalleId;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class HojaManualDetalleDaoIbatis extends TumiDaoIbatis implements HojaManualDetalleDao{
	
	public HojaManualDetalle grabar(HojaManualDetalle o) throws DAOException{
		HojaManualDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public HojaManualDetalle modificar(HojaManualDetalle o) throws DAOException{
		HojaManualDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<HojaManualDetalle> getListaPorPk(Object o) throws DAOException{
		List<HojaManualDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<HojaManualDetalle> getBusqueda(Object o) throws DAOException{
		List<HojaManualDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public HojaManualDetalle eliminar(HojaManualDetalleId o) throws DAOException{
		HojaManualDetalle dto = null;
		try{
			dto = new HojaManualDetalle();
			dto.setId(o);
			getSqlMapClientTemplate().delete(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
}
