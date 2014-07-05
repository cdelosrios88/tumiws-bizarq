package pe.com.tumi.parametro.general.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.parametro.general.domain.Detraccion;

public interface DetraccionDao extends TumiDao{
	public List<Detraccion> getListaTodos() throws DAOException;
}
