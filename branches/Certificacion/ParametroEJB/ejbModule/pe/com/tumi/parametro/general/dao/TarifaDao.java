package pe.com.tumi.parametro.general.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.Tarifa;
import pe.com.tumi.parametro.general.domain.TipoCambio;

public interface TarifaDao extends TumiDao{
	public Tarifa grabar(Tarifa o) throws DAOException;
	public Tarifa modificar(Tarifa o) throws DAOException;
	public List<Tarifa> getListaPorPK(Object o) throws DAOException;
	public List<Tarifa> getBusqueda(Object o) throws DAOException;
}
