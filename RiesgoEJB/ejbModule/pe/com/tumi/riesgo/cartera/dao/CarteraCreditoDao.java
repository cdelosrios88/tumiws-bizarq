package pe.com.tumi.riesgo.cartera.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.cartera.domain.Cartera;
import pe.com.tumi.riesgo.cartera.domain.CarteraCredito;

public interface CarteraCreditoDao extends TumiDao{
 public List<CarteraCredito> getListaPorMaxPeriodo(Object o) throws DAOException;
}
