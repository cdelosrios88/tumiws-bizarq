package pe.com.tumi.seguridad.empresa.dao.impl;

import java.util.List;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalCodigo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.seguridad.empresa.dao.SucursalCodigoDao;

public class SucursalCodigoDaoIbatis extends TumiDaoIbatis implements SucursalCodigoDao{
	
	public List<SucursalCodigo> getListaSucursalCodigoPorIdSucursal(Object o) throws DAOException{
		List<SucursalCodigo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<SucursalCodigo> getListaSucursalCodigoPorPK(Object o) throws DAOException{
		List<SucursalCodigo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public SucursalCodigo modificar(SucursalCodigo o) throws DAOException {
		SucursalCodigo dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public SucursalCodigo grabar(SucursalCodigo o) throws DAOException {
		SucursalCodigo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

}
