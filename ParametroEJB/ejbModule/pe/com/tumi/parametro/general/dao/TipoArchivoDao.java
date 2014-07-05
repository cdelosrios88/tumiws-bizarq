package pe.com.tumi.parametro.general.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.parametro.general.domain.TipoArchivo;

public interface TipoArchivoDao extends TumiDao{
	public TipoArchivo grabar(TipoArchivo o) throws DAOException;
	public TipoArchivo modificar(TipoArchivo o) throws DAOException;
	public List<TipoArchivo> getListaPorPk(Object o) throws DAOException;
}
