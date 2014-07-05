package pe.com.tumi.tesoreria.banco.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalleRes;

public interface AccesoDetalleResDao extends TumiDao{
	public AccesoDetalleRes grabar(AccesoDetalleRes pDto) throws DAOException;
	public AccesoDetalleRes modificar(AccesoDetalleRes o) throws DAOException;
	public List<AccesoDetalleRes> getListaPorPk(Object o) throws DAOException;
	public List<AccesoDetalleRes> getListaPorAccesoDetalle(Object o) throws DAOException;
}	
