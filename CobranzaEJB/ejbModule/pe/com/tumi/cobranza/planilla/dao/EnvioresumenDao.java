package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;

public interface EnvioresumenDao extends TumiDao{
	public Envioresumen grabar(Envioresumen pDto) throws DAOException;
	public Envioresumen modificar(Envioresumen o) throws DAOException;
	public List<Envioresumen> getListaPorPk(Object o) throws DAOException;
	public List<Envioresumen> getListaPorEnitdadyPeriodo(Object o) throws DAOException;
	public List<Envioresumen> getListaEnvioResumen(Object o) throws DAOException;
	public List<Envioresumen> getListaSucursal(Object o) throws DAOException;
	public List<Envioresumen> getListaEnvioResumenUE(Object o) throws DAOException;
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocio(Object o) throws DAOException;	
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioM(Object o) throws DAOException;	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioCAS(Object o) throws DAOException;
	
	public List<Envioresumen> getListaEnvioREfectuadoConArchivo(Object o) throws DAOException;
	
	public List<Envioresumen> getListEnvRes(Object o) throws DAOException;
}
