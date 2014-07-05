package pe.com.tumi.seguridad.empresa.dao;

import java.util.List;

import pe.com.tumi.empresa.domain.SucursalCodigo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.contacto.domain.Comunicacion;

public interface SucursalCodigoDao extends TumiDao {
	
	public List<SucursalCodigo> getListaSucursalCodigoPorPK(Object o) throws DAOException;
	public SucursalCodigo modificar(SucursalCodigo o) throws DAOException;
	public SucursalCodigo grabar(SucursalCodigo o) throws DAOException;
	public List<SucursalCodigo> getListaSucursalCodigoPorIdSucursal(Object o) throws DAOException;
}
