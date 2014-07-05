package pe.com.tumi.seguridad.login.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;

public interface EmpresaUsuarioDao extends TumiDao {
	public EmpresaUsuario grabar(EmpresaUsuario o) throws DAOException;
	public EmpresaUsuario modificar(EmpresaUsuario o) throws DAOException;
	public List<EmpresaUsuario> getListaEmpresaUsuarioPorPk(Object o) throws DAOException;
	public List<EmpresaUsuario> getListaEmpresaUsuarioPorIdEmpresa(Object o) throws DAOException;
	public List<EmpresaUsuario> getListaPorIdPersona(Object o) throws DAOException;
	public List<EmpresaUsuario> getListaPorIdPersonaYFechaEliminacion(Object o) throws DAOException;
}
