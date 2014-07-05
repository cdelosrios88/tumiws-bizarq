package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;

public interface CronogramaDao extends TumiDao{
	public Cronograma grabar(Cronograma o) throws DAOException;
	public Cronograma modificar(Cronograma o) throws DAOException;
	public List<Cronograma> getListaPorPK(Object o) throws DAOException;
	public List<Cronograma> getListaPorPkExpediente(Object o) throws DAOException;
	public List<Cronograma> getListaDeVencidoPorPkExpedienteYPeriodoYPago(Object o) throws DAOException;
}
