package pe.com.tumi.riesgo.cartera.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.cartera.domain.Cartera;

public interface CarteraDao extends TumiDao{
	public Cartera grabar(Cartera o) throws DAOException;
	public Cartera modificar(Cartera o) throws DAOException;
	public List<Cartera> getListaPorPk(Object o) throws DAOException;
	public List<Cartera> getListaPorBusqueda(Object o) throws DAOException;
}
