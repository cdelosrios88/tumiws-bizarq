package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadDescuento;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadDescuentoId;

public interface CapacidadDescuentoDao extends TumiDao{
	public CapacidadDescuento grabar(CapacidadDescuento o) throws DAOException;
	public CapacidadDescuento modificar(CapacidadDescuento o) throws DAOException;
	public List<CapacidadDescuento> getListaPorPk(Object o) throws DAOException;
	public List<CapacidadDescuento> getListaPorCapacidadCreditoPk(Object o) throws DAOException;
	public void deletePorPk(Object o)throws DAOException;
}