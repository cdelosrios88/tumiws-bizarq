package pe.com.tumi.credito.socio.ctacte.dao;

import java.util.List;

import pe.com.tumi.credito.socio.ctacte.domain.CtaCteCondicion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CtaCteCondicionDao extends TumiDao{
	public CtaCteCondicion grabar(CtaCteCondicion o) throws DAOException;
	public CtaCteCondicion modificar(CtaCteCondicion o) throws DAOException;
	public List<CtaCteCondicion> getListaCtaCteCondicionPorPK(Object o) throws DAOException;
}
