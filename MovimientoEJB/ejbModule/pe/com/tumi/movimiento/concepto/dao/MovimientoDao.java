package pe.com.tumi.movimiento.concepto.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;

public interface MovimientoDao extends TumiDao{
	public Movimiento grabar(Movimiento o) throws DAOException;
	public Movimiento modificar(Movimiento o) throws DAOException;
	public List<Movimiento> getListaPorPK(Object o) throws DAOException;
	public List<Movimiento> getListaMovimientoPorCuentaEmpresaConcepto(Object o) throws DAOException;
	public List<Movimiento> getListPeriodoMaxCuentaEmpresa(Object o) throws DAOException;
	public List<Movimiento> getListXCtaYPersYCptoGeneral(Object o) throws DAOException;
	public List<Movimiento> getListXCtaExpedienteYTipoMov(Object o) throws DAOException;
	public List<Movimiento> getListXCtaExpediente(Object o) throws DAOException;
	public List<Movimiento> getListaMaximoMovimiento(Object o) throws DAOException;
	public List<Movimiento> getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(Object o) throws DAOException;
	public List<Movimiento> getListaMaximoMovimientoPorCuentaConcepto(Object o) throws DAOException;
	/** CREADO 15-08-2013 **/
	public List<Movimiento> getListaMovimientoPorExpedienteCredito(Object o) throws DAOException;
	/** CREADO 20-08-2013 **/
	public List<Movimiento> getListXCuentaYEmpresa(Object o) throws DAOException;
	public List<Movimiento> getMaxMovXCtaEmpresaTipoMov(Object o) throws DAOException;
	public List<Movimiento> getMaxXExpYCtoGral(Object o) throws DAOException;
	//jchavez 19.05.2014
	public List<Movimiento> getListaMovVtaCtePorPagar(Object o) throws DAOException;
	//rVillarreal
	public List<Movimiento> getMaxMovCtaCteXFecha(Object o) throws DAOException;
	//jchavez 05.06.2014
	public List<Movimiento> getListaMovCtaCtePorPagarLiq(Object o) throws DAOException;
}
