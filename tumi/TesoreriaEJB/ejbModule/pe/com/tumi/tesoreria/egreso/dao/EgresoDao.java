package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

public interface EgresoDao extends TumiDao{
	public Egreso grabar(Egreso pDto) throws DAOException;
	public Egreso modificar(Egreso o) throws DAOException;
	public List<Egreso> getListaPorPk(Object o) throws DAOException;
	public List<Egreso> getListaParaItem(Object o) throws DAOException;
	public List<Egreso> getBuscarTransferencia(Object o) throws DAOException;
	public List<Egreso> getListaPorCFF(Object o) throws DAOException;
	public List<Egreso> getListaPorBuscar(Object o) throws DAOException;
	public List<Egreso> getListaParaTelecredito(Object o) throws DAOException;
	public List<Egreso> getListaPorLibroDiario(Object o) throws DAOException;
}