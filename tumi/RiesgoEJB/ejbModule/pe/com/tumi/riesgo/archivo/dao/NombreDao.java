package pe.com.tumi.riesgo.archivo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.archivo.domain.Nombre;

public interface NombreDao extends TumiDao{
	public Nombre grabar(Nombre o) throws DAOException;
	public Nombre modificar(Nombre o) throws DAOException;
	public List<Nombre> getListaPorPk(Object o) throws DAOException;
	public List<Nombre> getListaPorIntItemConfiguracion(Object o) throws DAOException;
}
