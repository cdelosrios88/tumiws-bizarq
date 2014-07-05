package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadDetalle;

public interface MovilidadDetalleDao extends TumiDao{
	public MovilidadDetalle grabar(MovilidadDetalle pDto) throws DAOException;
	public MovilidadDetalle modificar(MovilidadDetalle o) throws DAOException;
	public List<MovilidadDetalle> getListaPorPk(Object o) throws DAOException;
	public List<MovilidadDetalle> getListaPorMovilidad(Object o) throws DAOException;
}	
