package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.Poblacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface PoblacionDao extends TumiDao{
	public Poblacion grabar(Poblacion o) throws DAOException;
	public Poblacion modificar(Poblacion o) throws DAOException;
	public List<Poblacion> getListaPoblacionPorPK(Object o) throws DAOException;
	public List<Poblacion> getListaPoblacionPorPKConvenio(Object o) throws DAOException;
}
