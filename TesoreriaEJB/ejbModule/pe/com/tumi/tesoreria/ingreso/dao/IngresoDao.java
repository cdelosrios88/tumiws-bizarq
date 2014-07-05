package pe.com.tumi.tesoreria.ingreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;

public interface IngresoDao extends TumiDao{
	public Ingreso grabar(Ingreso o) throws DAOException;
	public Ingreso modificar(Ingreso o) throws DAOException;
	public List<Ingreso> getListaPorPk(Object o) throws DAOException;
	public List<Ingreso> getListaParaItem(Object o) throws DAOException;
	public List<Ingreso> getListaParaBuscar(Object o) throws DAOException;
	public List<Ingreso> getListaParaDepositar(Object o) throws DAOException;
	public List<Ingreso> getListaIngNoEnlazados(Object o) throws DAOException;

}