package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;

public interface InformeGerenciaDao extends TumiDao{
	public InformeGerencia grabar(InformeGerencia pDto) throws DAOException;
	public InformeGerencia modificar(InformeGerencia o) throws DAOException;
	public List<InformeGerencia> getListaPorPk(Object o) throws DAOException;
	public List<InformeGerencia> getListaPorBuscar(Object o) throws DAOException;
}