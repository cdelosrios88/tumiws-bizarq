package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface LibroDiarioDao extends TumiDao{

	public LibroDiario grabar(LibroDiario o) throws DAOException;
	public LibroDiario modificar(LibroDiario o) throws DAOException;
	public List<LibroDiario> getListaPorPk(Object o) throws DAOException;
	public List<LibroDiario> buscarParaCodigo(Object o) throws DAOException;
	public List<LibroDiario> buscarUltimoParaCodigo(Object o) throws DAOException;
	public List<LibroDiario> getListaPorBuscar(Object o) throws DAOException;
}
