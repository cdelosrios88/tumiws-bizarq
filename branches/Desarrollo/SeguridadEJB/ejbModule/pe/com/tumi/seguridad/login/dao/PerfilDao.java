package pe.com.tumi.seguridad.login.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.login.domain.Perfil;

public interface PerfilDao extends TumiDao {
	public Perfil grabar(Perfil o) throws DAOException;
	public Perfil modificar(Perfil o) throws DAOException;
	public List<Perfil> getListaPorPk(Object o) throws DAOException;
	public List<Perfil> getListaPorPkEmpresaUsuario(Object o) throws DAOException;
	public List<Perfil> getListaPorPkEmpresaUsuarioYEstado(Object o) throws DAOException;
	public List<Perfil> getListaPorIdEmpresa(Object o) throws DAOException;
	public List<Perfil> getListaBusqueda(Object o) throws DAOException;
}
