package pe.com.tumi.reporte.operativo.credito.asociativo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Prevision;

public interface PrevisionDao extends TumiDao {
	public List<Prevision> getListaFondoSepelio(Object o) throws DAOException;
	public List<Prevision> getListaFondoRetiro(Object o) throws DAOException;
	public List<Prevision> getListaAes(Object o) throws DAOException;
}
