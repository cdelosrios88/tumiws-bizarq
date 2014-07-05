package pe.com.tumi.seguridad.empresa.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.empresa.dao.SucursalDao;
import pe.com.tumi.seguridad.empresa.dao.SucursalEmpresaDao;

public class SucursalEmpresaDaoIbatis extends TumiDaoIbatis implements SucursalEmpresaDao{
	
	public Integer getCantidadSucursalPorIdEmpresa(Integer intIdEmpresa)
			throws BusinessException, DAOException {
		Integer cant = null;
		try{
			HashMap map = new HashMap();
			map.put("intIdEmpresa", intIdEmpresa);
			getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCantidadSucursalPorIdEmpresa", map);
			cant = (Integer) map.get("intCantidadSucursal");
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return cant;
	}

}
