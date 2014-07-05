package pe.com.tumi.contabilidad.operaciones.dao;

import java.util.List;

import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface HojaManualDao extends TumiDao{
	public HojaManual grabar(HojaManual o) throws DAOException;
	public HojaManual modificar(HojaManual o) throws DAOException;
	public List<HojaManual> getListaPorPk(Object o) throws DAOException;
	public List<HojaManual> getBusqueda(Object o) throws DAOException;
}	
