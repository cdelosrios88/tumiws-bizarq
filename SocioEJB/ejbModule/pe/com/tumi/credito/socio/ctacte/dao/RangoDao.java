package pe.com.tumi.credito.socio.ctacte.dao;

import java.util.List;

import pe.com.tumi.credito.socio.ctacte.domain.Rango;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface RangoDao extends TumiDao{
	public Rango grabar(Rango o) throws DAOException;
	public Rango modificar(Rango o) throws DAOException;
	public List<Rango> getListaRangoPorPK(Object o) throws DAOException;
}
