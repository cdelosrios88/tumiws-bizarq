package pe.com.tumi.contabilidad.operaciones.dao;

import java.util.List;

import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalle;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalleId;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface HojaManualDetalleDao extends TumiDao{
	public HojaManualDetalle grabar(HojaManualDetalle o) throws DAOException;
	public HojaManualDetalle modificar(HojaManualDetalle o) throws DAOException;
	public List<HojaManualDetalle> getListaPorPk(Object o) throws DAOException;
	public List<HojaManualDetalle> getBusqueda(Object o) throws DAOException;
	public HojaManualDetalle eliminar(HojaManualDetalleId o) throws DAOException;
}	
