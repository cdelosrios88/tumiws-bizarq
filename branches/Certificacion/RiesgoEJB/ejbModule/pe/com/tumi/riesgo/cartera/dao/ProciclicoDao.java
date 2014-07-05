package pe.com.tumi.riesgo.cartera.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.cartera.domain.Prociclico;

public interface ProciclicoDao extends TumiDao{
	public Prociclico grabar(Prociclico o) throws DAOException;
	public Prociclico modificar(Prociclico o) throws DAOException;
	public List<Prociclico> getListaPorPk(Object o) throws DAOException;
	public List<Prociclico> getListaPorEspecificacion(Object o) throws DAOException;
}
