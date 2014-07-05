package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CondicionHabilDao extends TumiDao{
	public CondicionHabil grabar(CondicionHabil o) throws DAOException;
	public CondicionHabil modificar(CondicionHabil o) throws DAOException;
	public List<CondicionHabil> getListaCondicionHabilPorPK(Object o) throws DAOException;
	public List<CondicionHabil> getListaCondicionHabilPorPKCredito(Object o) throws DAOException;
}
