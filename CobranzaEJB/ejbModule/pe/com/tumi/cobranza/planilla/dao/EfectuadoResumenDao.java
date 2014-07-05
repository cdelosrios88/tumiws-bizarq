package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;


public interface EfectuadoResumenDao extends TumiDao{
	public EfectuadoResumen grabar(EfectuadoResumen pDto) throws DAOException;
	public EfectuadoResumen modificar(EfectuadoResumen o) throws DAOException;
	public List<EfectuadoResumen> getListaPorPk(Object o) throws DAOException;
	public List<EfectuadoResumen> getListaFaltaCancelar(Object o) throws DAOException;
	public List<EfectuadoResumen> getListaPorEntidadyPeriodo(Object o)  throws DAOException;
	public List<EfectuadoResumen> getListaEfectuadoResumen(Object o) throws DAOException;	
	
	public String getNumeroCuenta(Object o) throws DAOException;
	public List<EfectuadoResumen> getMaximoPeriodo(Object o)throws DAOException;
	//jchavez 19.06.2014
	public List<EfectuadoResumen> getLstPendientesPorEnitdad(Object o) throws DAOException;	
}