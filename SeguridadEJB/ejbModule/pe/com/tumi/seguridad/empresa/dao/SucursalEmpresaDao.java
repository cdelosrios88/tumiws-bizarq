package pe.com.tumi.seguridad.empresa.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface SucursalEmpresaDao extends TumiDao {
	
	public Integer getCantidadSucursalPorIdEmpresa(Integer intIdEmpresa) throws BusinessException, DAOException;

}
