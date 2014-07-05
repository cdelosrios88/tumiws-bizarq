package pe.com.tumi.estadoCuenta.dao;

import java.util.List;

import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaDetalleMovimiento;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaDsctoTerceros;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaMontosBeneficios;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaPlanillas;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaPrestamos;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaPrevisionSocial;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaResumenPrestamos;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaSocioCuenta;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaSocioEstructura;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaGestiones;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface EstadoCuentaDao extends TumiDao {
	public List<DataBeanEstadoCuentaResumenPrestamos> getResumenPrestamos(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaMontosBeneficios> getCabMtosBeneficios(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaSocioEstructura> getCabSocioEstructura(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaSocioCuenta> getCabCuentasSocio(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaGestiones> getListaGestionCobranza(Object o) throws DAOException;
	
	public List<DataBeanEstadoCuentaDsctoTerceros> getListaFilasPorPeriodoModalidadYDni(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaDsctoTerceros> getListaColumnasPorPeriodoModalidadYDni(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaDsctoTerceros> getMontoTotalPorNomCptoYPeriodo(Object o) throws DAOException;
	
	public List<DataBeanEstadoCuentaPlanillas> getResumenPlanilla(Object o) throws DAOException ;
	public List<DataBeanEstadoCuentaPlanillas> getDiferenciaPlanilla(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaPlanillas> getFilasDifPlanilla(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaPlanillas> getColumnasDifPlanilla(Object o) throws DAOException;
	
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoAprobado(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoRechazado(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoGarantizado(Object o) throws DAOException;
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getBenefOtorgados(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaPrevisionSocial> getPeriodoCtaCto(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaPrevisionSocial> getSumMontoPago(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaPrevisionSocial> getConceptoPago(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaPrevisionSocial> getMovimientoFdoSepelio(Object o) throws DAOException;
	public List<DataBeanEstadoCuentaPrevisionSocial> getMovFdoRetiroInteres(Object o) throws DAOException;
	
	//Búsqueda por DNI
	public List<DataBeanEstadoCuentaSocioEstructura> getSocioPorDocumento(Object o) throws DAOException;
	//Búsqueda por nombres y apellidos
	public List<DataBeanEstadoCuentaSocioCuenta> getSocioPorNombres(Object o) throws DAOException;
	//JCHAVEZ 15.01.2014
	public List<DataBeanEstadoCuentaDetalleMovimiento> getDetalleMovimiento(Object o) throws DAOException;
	//JCHAVEZ 15.01.2014
	public List<DataBeanEstadoCuentaSocioEstructura> getSocioPorNumeroCuenta(Object o) throws DAOException;
}
