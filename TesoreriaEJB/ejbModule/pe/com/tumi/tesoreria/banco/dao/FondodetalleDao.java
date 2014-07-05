package pe.com.tumi.tesoreria.banco.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.banco.domain.Fondodetalle;

public interface FondodetalleDao extends TumiDao{
	public Fondodetalle grabar(Fondodetalle pDto) throws DAOException;
	public Fondodetalle modificar(Fondodetalle o) throws DAOException;
	public List<Fondodetalle> getListaPorPk(Object o) throws DAOException;
	public List<Fondodetalle> getListaPorBancoFondo(Object o) throws DAOException;
	public List<Fondodetalle> getListaPorSubSucursalPK(Object o) throws DAOException;
}	
