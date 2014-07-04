package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;

public interface ContratoDao extends TumiDao{
	public Contrato grabar(Contrato pDto) throws DAOException;
	public Contrato modificar(Contrato o) throws DAOException;
	public List<Contrato> getListaPorPk(Object o) throws DAOException;
	public List<Contrato> getListaPorBuscar(Object o) throws DAOException;
}