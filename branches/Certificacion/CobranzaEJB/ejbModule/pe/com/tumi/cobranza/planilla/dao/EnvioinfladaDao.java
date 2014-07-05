package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.Envioinflada;

public interface EnvioinfladaDao extends TumiDao{
	public Envioinflada grabar(Envioinflada pDto) throws DAOException;
	public Envioinflada modificar(Envioinflada o) throws DAOException;
	public List<Envioinflada> getListaPorPk(Object o) throws DAOException;
	public List<Envioinflada> getListaPorEnvioMonto(Object o) throws DAOException;
	
}	
