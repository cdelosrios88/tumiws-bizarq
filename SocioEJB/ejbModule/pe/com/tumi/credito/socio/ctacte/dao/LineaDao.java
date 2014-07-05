package pe.com.tumi.credito.socio.ctacte.dao;

import java.util.List;

import pe.com.tumi.credito.socio.ctacte.domain.Linea;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface LineaDao extends TumiDao{
	public Linea grabar(Linea o) throws DAOException;
	public Linea modificar(Linea o) throws DAOException;
	public List<Linea> getListaLineaPorPK(Object o) throws DAOException;
}
