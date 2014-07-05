package pe.com.tumi.contabilidad.cierre.facade;

import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.common.util.AnexoDetalleException;
import pe.com.tumi.common.util.MayorizacionException;
import pe.com.tumi.contabilidad.cierre.domain.Anexo;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleOperador;
import pe.com.tumi.contabilidad.cierre.domain.AnexoId;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierre;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierreDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorId;
import pe.com.tumi.contabilidad.cierre.domain.Ratio;
import pe.com.tumi.contabilidad.cierre.domain.RatioDetalle;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface CierreFacadeLocal {

	public LibroMayor grabarLibroMayor(LibroMayor o)throws BusinessException;
	public LibroMayor modificarLibroMayor(LibroMayor o)throws BusinessException;
	public LibroMayor getLibroMayorPorPk(LibroMayorId pId) throws BusinessException;
	public List<LibroMayor> buscarLibroMayor(LibroMayor o) throws BusinessException;
	public List<LibroMayor> getLibroMayorTodos() throws BusinessException;
	public List<LibroMayorDetalle> getListaLibroMayorDetallePorLibroMayor(LibroMayor libroMayor) throws BusinessException;
	public LibroMayor mayorizar(LibroMayor o)throws BusinessException, MayorizacionException;
	public boolean eliminarMayorizacion(LibroMayor o)throws BusinessException;
	public CuentaCierre grabarCuentaCierre(CuentaCierre o) throws BusinessException;
	public List<CuentaCierre> getListaCuentaCierrePorBusqueda(CuentaCierre o) throws BusinessException;
	public CuentaCierre eliminarCuentaCierre(CuentaCierre o)throws BusinessException;
	public List<CuentaCierreDetalle> getListaCuentaCierreDetallePorCuentaCierre(CuentaCierre cuentaCierre) throws BusinessException;
	public CuentaCierre modificarCuentaCierre(CuentaCierre o) throws BusinessException;
	public Anexo grabarAnexo(Anexo o) throws BusinessException;
	public Anexo getAnexoPorPK(AnexoId o) throws BusinessException;
	public List<Anexo> buscarAnexo(Anexo o) throws BusinessException;
	public void eliminarAnexo(Anexo o) throws BusinessException, AnexoDetalleException;
	public List<AnexoDetalle> getListaAnexoDetallePorAnexo(Anexo o) throws BusinessException;
	public List<AnexoDetalleOperador> getListaAnexoDetalleOperadorPorAnexoDetalle(AnexoDetalle o) throws BusinessException;
	public Anexo modificarAnexo(Anexo o) throws BusinessException, AnexoDetalleException;
	public List<AnexoDetalleCuenta> getListaAnexoDetalleCuentaPorAnexoDetalle(AnexoDetalle o) throws BusinessException;
	public Ratio grabarRatio(Ratio o) throws BusinessException;
	public List<Ratio> buscarRatio(Ratio o) throws BusinessException;
	public List<RatioDetalle> getListaRatioDetallePorRatio(Ratio o) throws BusinessException;
	public AnexoDetalle getAnexoDetallePorPK(AnexoDetalleId o) throws BusinessException;
	public Ratio eliminarRatio(Ratio o) throws BusinessException;
	public Ratio modificarRatio(Ratio o) throws BusinessException;
}
