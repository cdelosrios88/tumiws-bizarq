package pe.com.tumi.tesoreria.banco.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalle;

public interface AccesoDetalleDao extends TumiDao{
	public AccesoDetalle grabar(AccesoDetalle pDto) throws DAOException;
	public AccesoDetalle modificar(AccesoDetalle o) throws DAOException;
	public List<AccesoDetalle> getListaPorPk(Object o) throws DAOException;
	public List<AccesoDetalle> getListaPorAcceso(Object o) throws DAOException;
}	
