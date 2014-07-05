package pe.com.tumi.riesgo.cartera.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.cartera.domain.Plantilla;

public interface PlantillaDao extends TumiDao{
	public Plantilla grabar(Plantilla o) throws DAOException;
	public Plantilla modificar(Plantilla o) throws DAOException;
	public List<Plantilla> getListaPorPk(Object o) throws DAOException;
	public List<Plantilla> getListaTodos() throws DAOException;
}
