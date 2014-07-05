package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.Password;

public interface PasswordDao extends TumiDao {
	public List<Password> getListaPorPk(Object o) throws DAOException;
	public List<Password> getListaPorPkYPass(Object o) throws DAOException;
}
