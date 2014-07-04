package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;

public interface TransaccionDao extends TumiDao {
	public Transaccion grabar(Transaccion o) throws DAOException;
	public Transaccion modificar(Transaccion o) throws DAOException;
	public List<Transaccion> getListaPorPk(Object o) throws DAOException;
	public List<Transaccion> getListaPrincipalPorIdEmpresa(Object o) throws DAOException;
	public List<Transaccion> getListaPrincipalPorIdPerfil(Object o) throws DAOException;
	public List<Transaccion> getListaPorPkPadre(Object o) throws DAOException;
	public List<Transaccion> getListaPorPkPadreYIdPerfil(Object o) throws DAOException;
	public List<Transaccion> getListaDeBusquedaPrincipal(Object o) throws DAOException;
	public List<Transaccion> getListaDeBusqueda(Object o) throws DAOException;
	public List<Transaccion> getListaDeBusquedaPorPkPadre(Object o) throws DAOException;
}
