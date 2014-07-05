package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.Ratio;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface RatioDao extends TumiDao{

	public Ratio grabar(Ratio o) throws DAOException;
	public Ratio modificar(Ratio o) throws DAOException;
	public List<Ratio> getListaPorPk(Object o) throws DAOException;
	public List<Ratio> getListaPorBusqueda(Object o) throws DAOException;
}
