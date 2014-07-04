package pe.com.tumi.parametro.general.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.TipoCambio;

public interface TipoCambioDao extends TumiDao{
	public TipoCambio grabar(TipoCambio o) throws DAOException;
	public TipoCambio modificar(TipoCambio o) throws DAOException;
	public List<TipoCambio> getListaPorPK(Object o) throws DAOException;
	public List<TipoCambio> getBusqueda(Object o) throws DAOException;
	public List<TipoCambio> getTipoCambioActual(Object o) throws DAOException;
}
