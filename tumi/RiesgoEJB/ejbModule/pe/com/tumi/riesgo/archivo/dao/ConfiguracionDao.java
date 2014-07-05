package pe.com.tumi.riesgo.archivo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;

public interface ConfiguracionDao extends TumiDao{
	public Configuracion grabar(Configuracion o) throws DAOException;
	public Configuracion modificar(Configuracion o) throws DAOException;
	public List<Configuracion> getListaPorPk(Object o) throws DAOException;
	public List<Configuracion> getListaPorBusqueda(Object o) throws DAOException;
	public List<Configuracion> getListaPorBusquedaConEstructura(Object o) throws DAOException;
	public List<Configuracion> getListaPorBusquedaSinEstructura(Object o) throws DAOException;
}
