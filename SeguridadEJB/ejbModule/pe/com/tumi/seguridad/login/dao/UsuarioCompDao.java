package pe.com.tumi.seguridad.login.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;

public interface UsuarioCompDao extends TumiDao {
	public List<UsuarioComp> getListaDeBusqueda(Object o) throws DAOException;
}
