package pe.com.tumi.estadoCuenta.facade;
import java.util.List;

import javax.ejb.Local;

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
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface EstadoCuentaFacadeLocal {
	public List<DataBeanEstadoCuentaResumenPrestamos> getResumenPrestamos(Integer intEmpresa, Integer intCuenta) throws BusinessException;
	public List<DataBeanEstadoCuentaMontosBeneficios> getCabMtosBeneficios(Integer intEmpresa, Integer intCuenta) throws BusinessException;
	public List<DataBeanEstadoCuentaSocioEstructura> getCabSocioEstructura(Integer intEmpresa, Integer intPersona) throws BusinessException;
	//MODIFICADO 08.03.2014
	public List<DataBeanEstadoCuentaSocioCuenta> getCabCuentasSocio(Integer intEmpresa, Integer intPersona,Integer intTipoCuenta) throws BusinessException;
	public List<DataBeanEstadoCuentaGestiones> getListaGestionCobranza(Integer intEmpresa, Integer intCuenta, String strFecha) throws BusinessException;
	
	public List<DataBeanEstadoCuentaDsctoTerceros> getListaColumnasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strNroDocIden) throws BusinessException;
	public List<DataBeanEstadoCuentaDsctoTerceros> getListaFilasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strNroDocIden) throws BusinessException;
	public List<DataBeanEstadoCuentaDsctoTerceros> getMontoTotalPorNomCptoYPeriodo(String strDsteCpto, String strNomCpto, Integer intAnio, Integer intMes, Integer intParaModalidadCod, String strNroDocIden) throws BusinessException;
	
	public List<DataBeanEstadoCuentaPlanillas> getResumenPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException;
	public List<DataBeanEstadoCuentaPlanillas> getDiferenciaPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException;
	public List<DataBeanEstadoCuentaPlanillas> getFilasDifPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException;
	public List<DataBeanEstadoCuentaPlanillas> getColumnasDifPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException;
	
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoAprobado(Integer intEmpresa, Integer intCuenta, Integer intTipoCredito, Integer intEstadoCredito) throws BusinessException;
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoRechazado(Integer intEmpresa, Integer intCuenta, Integer intTipoCredito) throws BusinessException;
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoGarantizado(Integer intEmpresa, Integer intPersona, Integer intCuenta) throws BusinessException;
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getBenefOtorgados(Integer intEmpresa, Integer intPersona, Integer intCuenta) throws BusinessException;
	public List<DataBeanEstadoCuentaPrevisionSocial> getPeriodoCtaCto(Integer intEmpresa, Integer intCuenta, Integer intTipoConcepto) throws BusinessException;
	public DataBeanEstadoCuentaPrevisionSocial getSumMontoPago(Integer intEmpresa, Integer intCuenta, Integer intItemCtaCto, Integer intItemCtaCtoDet, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException;
	public List<DataBeanEstadoCuentaPrevisionSocial> getConceptoPago(Integer intEmpresa, Integer intCuenta, Integer intItemCtaCto, Integer intItemCtaCtoDet, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException;
	public List<DataBeanEstadoCuentaPrevisionSocial> getMovimientoFdoSepelio(Integer intEmpresa, Integer intCuenta, Integer intItemCtaCto, Integer intItemCtaCtoDet, Integer intItemCptoPago) throws BusinessException;
	public List<DataBeanEstadoCuentaPrevisionSocial> getMovFdoRetiroInteres(Integer intEmpresa, Integer intCuenta) throws BusinessException;
	
	//Búsqueda por DNI
	public DataBeanEstadoCuentaSocioEstructura getSocioPorDocumento(Integer intTipoDocumento, String strNumeroDocIdent) throws BusinessException;
	//Búsqueda por nombres y apellidos
	public List<DataBeanEstadoCuentaSocioCuenta> getSocioPorNombres(Integer intEmpresa, String strNombre) throws BusinessException;
	//JCHAVEZ 15.01.2014
	public List<DataBeanEstadoCuentaDetalleMovimiento> getDetalleMovimiento(Integer intEmpresa, Integer intPersona, Integer intCuenta) throws BusinessException;
	//JCHAVEZ 22.01.2014
	public DataBeanEstadoCuentaSocioEstructura getSocioPorNumeroCuenta(Integer intEmpresa, Integer intCuenta) throws BusinessException;
}
