package pe.com.tumi.parametro.general.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.parametro.general.domain.Archivo;

public interface ArchivoDao extends TumiDao{
	public Archivo grabar(Archivo o) throws DAOException;
	public Archivo grabarVersion(Archivo o) throws DAOException;
	public Archivo modificar(Archivo o) throws DAOException;
	public List<Archivo> getListaPorPK(Object o) throws DAOException;
	public List<Archivo> getListaVersionFinPorTipoYItem(Object o) throws DAOException;
}
