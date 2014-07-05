package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecial;

public interface AccesoEspecialDao extends TumiDao {
	
	public AccesoEspecial grabar(AccesoEspecial o) throws DAOException;
	
	public AccesoEspecial modificar(AccesoEspecial o) throws DAOException;
	
	public List<AccesoEspecial> getListaPorPk(Object o) throws DAOException;
	
	public List<AccesoEspecial> getListaPorBusqueda(Object o) throws DAOException;
	
	public List<AccesoEspecial> getAccesoPorEmpresaUsuario(Object o) throws DAOException;
}
