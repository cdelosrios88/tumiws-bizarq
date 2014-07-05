package pe.com.tumi.contabilidad.legalizacion.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.legalizacion.dao.PagoLegalizacionDao;
import pe.com.tumi.contabilidad.legalizacion.domain.PagoLegalizacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class PagoLegalizacionDaoIbatis extends TumiDaoIbatis implements PagoLegalizacionDao{

	public PagoLegalizacion grabar(PagoLegalizacion o) throws DAOException {
		PagoLegalizacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public PagoLegalizacion modificar(PagoLegalizacion o) throws DAOException{
		PagoLegalizacion dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<PagoLegalizacion> getListaPorPk(Object o) throws DAOException{
		List<PagoLegalizacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PagoLegalizacion> getListaPorLibroLegalizacion(Object o) throws DAOException{
		List<PagoLegalizacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorLibroLegalizacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}
