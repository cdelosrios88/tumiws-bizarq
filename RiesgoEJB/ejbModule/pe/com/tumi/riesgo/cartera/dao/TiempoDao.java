package pe.com.tumi.riesgo.cartera.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.cartera.domain.Tiempo;

public interface TiempoDao extends TumiDao{
	public Tiempo grabar(Tiempo o) throws DAOException;
	public Tiempo modificar(Tiempo o) throws DAOException;
	public List<Tiempo> getListaPorPk(Object o) throws DAOException;	
	public List<Tiempo> getListaPorIntItemCartera(Object o) throws DAOException;
}
