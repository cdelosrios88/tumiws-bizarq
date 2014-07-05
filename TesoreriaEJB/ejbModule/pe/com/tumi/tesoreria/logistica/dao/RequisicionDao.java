package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;

public interface RequisicionDao extends TumiDao{
	public Requisicion grabar(Requisicion pDto) throws DAOException;
	public Requisicion modificar(Requisicion o) throws DAOException;
	public List<Requisicion> getListaPorPk(Object o) throws DAOException;
	public List<Requisicion> getListaPorBuscar(Object o) throws DAOException;
	public List<Requisicion> getListaParaReferencia(Object o) throws DAOException;
	public List<Requisicion> getListaParaOrdenCompra(Object o) throws DAOException;
}