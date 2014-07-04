package pe.com.tumi.reporte.operativo.cobranza.centralriesgo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.Fenacrep;

public interface FenacrepDao extends TumiDao {
	public List<Fenacrep> getListaFenacrep(Object o) throws DAOException;
}