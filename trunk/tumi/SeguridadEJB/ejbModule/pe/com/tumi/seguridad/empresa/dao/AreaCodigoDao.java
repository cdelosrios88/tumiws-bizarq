package pe.com.tumi.seguridad.empresa.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.empresa.domain.AreaCodigo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AreaCodigoDao extends TumiDao {

	public AreaCodigo grabar(AreaCodigo o) throws DAOException;
	public AreaCodigo modificar(AreaCodigo o) throws DAOException;
	public List<AreaCodigo> getListaAreaCodigo(Object o) throws DAOException;
	public List<AreaCodigo> getAreaCodigoPorPK(Object o) throws DAOException;
}
