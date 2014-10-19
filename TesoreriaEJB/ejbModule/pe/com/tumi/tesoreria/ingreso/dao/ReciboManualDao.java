package pe.com.tumi.tesoreria.ingreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;

public interface ReciboManualDao extends TumiDao{
	public ReciboManual grabar(ReciboManual o) throws DAOException;
	public ReciboManual modificar(ReciboManual o) throws DAOException;
	public List<ReciboManual> getListaPorPk(Object o) throws DAOException;
	public List<ReciboManual> getListaPorBuscar(Object o) throws DAOException;
	public Integer validarNroReciboPorSuc(Object o) throws DAOException;
	//jchavez 02.07.2014
	public List<ReciboManual> getReciboPorGestorYSucursal(Object o) throws DAOException;
}