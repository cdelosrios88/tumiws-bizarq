package pe.com.tumi.estadoCuenta.dao.impl;


import java.util.List;

import pe.com.tumi.estadoCuenta.dao.EstadoCuentaDao;
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
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class EstadoCuentaDaoIbatis  extends TumiDaoIbatis implements EstadoCuentaDao {
	public List<DataBeanEstadoCuentaResumenPrestamos> getResumenPrestamos(Object o) throws DAOException {
		List<DataBeanEstadoCuentaResumenPrestamos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getResumenPrestamos", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaMontosBeneficios> getCabMtosBeneficios(Object o) throws DAOException {
		List<DataBeanEstadoCuentaMontosBeneficios> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCabMtosBeneficios", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaSocioEstructura> getCabSocioEstructura(Object o) throws DAOException {
		List<DataBeanEstadoCuentaSocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCabSocioEstructura", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaSocioCuenta> getCabCuentasSocio(Object o) throws DAOException {
		List<DataBeanEstadoCuentaSocioCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCabCuentasSocio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaGestiones> getListaGestionCobranza(Object o) throws DAOException {
		List<DataBeanEstadoCuentaGestiones> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaGestionCobranza", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaDsctoTerceros> getListaFilasPorPeriodoModalidadYDni(Object o) throws DAOException {
		List<DataBeanEstadoCuentaDsctoTerceros> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFilasPorPeriodoModalidadYDni", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaDsctoTerceros> getListaColumnasPorPeriodoModalidadYDni(Object o) throws DAOException {
		List<DataBeanEstadoCuentaDsctoTerceros> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaColumnasPorPeriodoModalidadYDni", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaDsctoTerceros> getMontoTotalPorNomCptoYPeriodo(Object o) throws DAOException {
		List<DataBeanEstadoCuentaDsctoTerceros> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMontoTotalPorNomCptoYPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPlanillas> getResumenPlanilla(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPlanillas> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getResumenPlanilla", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPlanillas> getDiferenciaPlanilla(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPlanillas> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getDiferenciaPlanilla", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPlanillas> getFilasDifPlanilla(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPlanillas> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getFilasDifPlanilla", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPlanillas> getColumnasDifPlanilla(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPlanillas> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getColumnasDifPlanilla", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoAprobado(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPrestamos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPrestamoAprobado", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoRechazado(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPrestamos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPrestamoRechazado", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoGarantizado(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPrestamos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPrestamoGarantizado", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getBenefOtorgados(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBenefOtorgados", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getPeriodoCtaCto(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPeriodoCtaCto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getSumMontoPago(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getSumMontoPago", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getConceptoPago(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getConceptoPago", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getMovimientoFdoSepelio(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMovimientoFdoSepelio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getMovFdoRetiroInteres(Object o) throws DAOException {
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMovFdoRetiroInteres", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaSocioEstructura> getSocioPorDocumento(Object o) throws DAOException {
		List<DataBeanEstadoCuentaSocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getSocioPorDocumento", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaSocioCuenta> getSocioPorNombres(Object o) throws DAOException {
		List<DataBeanEstadoCuentaSocioCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getSocioPorNombres", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaDetalleMovimiento> getDetalleMovimiento(Object o) throws DAOException {
		List<DataBeanEstadoCuentaDetalleMovimiento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getDetalleMovimiento", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaSocioEstructura> getSocioPorNumeroCuenta(Object o) throws DAOException {
		List<DataBeanEstadoCuentaSocioEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getSocioPorNumeroCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
