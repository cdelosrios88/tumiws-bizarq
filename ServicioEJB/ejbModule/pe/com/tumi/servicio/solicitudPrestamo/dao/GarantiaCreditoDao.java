package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;

public interface GarantiaCreditoDao extends TumiDao{
	public GarantiaCredito grabar(GarantiaCredito o) throws DAOException;
	public GarantiaCredito modificar(GarantiaCredito o) throws DAOException;
	public List<GarantiaCredito> getListaPorPk(Object o) throws DAOException;
	public List<GarantiaCredito> getListaPorExpedienteCredito(Object o) throws DAOException;
	public Integer getCantidadPersonasGarantizadasPorPkPersona(Object o) throws DAOException;
	public List<GarantiaCredito> getListaGarantiasPorPkPersona(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 04-09-2013 
	public List<GarantiaCredito> getListaGarantiasPorEmpPersCta(Object o) throws DAOException;
}
