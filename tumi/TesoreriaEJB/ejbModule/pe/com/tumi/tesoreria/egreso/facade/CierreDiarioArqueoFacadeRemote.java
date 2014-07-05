package pe.com.tumi.tesoreria.egreso.facade;

import java.util.Date;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;

import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;



@Remote
public interface CierreDiarioArqueoFacadeRemote {
	public boolean existeCierreDiaAnterior(ControlFondosFijos controlFondosFijos)throws BusinessException;
	public boolean existeCierreDiaActual(ControlFondosFijos controlFondosFijos)throws BusinessException;
	public boolean validarMovimientoCaja(ControlFondosFijos controlFondosFijos)throws BusinessException;
	public boolean validarMovimientoCaja(Ingreso ingreso)throws BusinessException;
	public boolean existeCierreDiaAnterior(Integer intIdEmpresa, Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException;
	public boolean existeCierreDiaActual(Integer intIdEmpresa, Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException;
	public Date obtenerFechaACerrar(CierreDiarioArqueo cierreDiarioArqueo)throws BusinessException;
	public CierreDiarioArqueoDetalle calcularCierreDiarioArqueoDetalleIngresos(CierreDiarioArqueo cierreDiarioArqueo)throws BusinessException;
	public CierreDiarioArqueo modificarCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo) throws BusinessException;
	//Agregado 11.12.2013 JCHAVEZ
	public boolean existeCierreDiarioArqueo(Integer intIdEmpresa, Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException;
	//Agregado 13.12.2013 JCHAVEZ
	public CierreDiarioArqueoDetalle obtenerCierreDiarioArqueoDetalleIngresos(CierreDiarioArqueo cierreDiarioArqueo)throws BusinessException;
}
