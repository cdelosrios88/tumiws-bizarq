package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.CuentaCierre;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CuentaCierreDao extends TumiDao{

	public CuentaCierre grabar(CuentaCierre o) throws DAOException;
	public CuentaCierre modificar(CuentaCierre o) throws DAOException;
	public List<CuentaCierre> getListaPorPk(Object o) throws DAOException;
	public List<CuentaCierre> getListaPorBusqueda(Object o) throws DAOException;
}
