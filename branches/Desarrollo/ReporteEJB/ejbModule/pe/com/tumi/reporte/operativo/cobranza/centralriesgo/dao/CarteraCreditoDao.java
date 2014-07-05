package pe.com.tumi.reporte.operativo.cobranza.centralriesgo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.CarteraCredito;

public interface CarteraCreditoDao extends TumiDao {
	public List<CarteraCredito> getListaCarteraCredito(Object o) throws DAOException;
}