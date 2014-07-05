package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.Diccionario;

public interface DiccionarioDao extends TumiDao {
	public Diccionario grabar(Diccionario o) throws DAOException;
	public Diccionario modificar(Diccionario o) throws DAOException;
	public List<Diccionario> getListaPorPk(Object o) throws DAOException;
}
