package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;

public interface CuadroComparativoDao extends TumiDao{
	public CuadroComparativo grabar(CuadroComparativo pDto) throws DAOException;
	public CuadroComparativo modificar(CuadroComparativo o) throws DAOException;
	public List<CuadroComparativo> getListaPorPk(Object o) throws DAOException;
	public List<CuadroComparativo> getListaPorBuscar(Object o) throws DAOException;
}