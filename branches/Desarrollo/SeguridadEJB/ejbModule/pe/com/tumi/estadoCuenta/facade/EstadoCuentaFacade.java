package pe.com.tumi.estadoCuenta.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.estadoCuenta.bo.EstadoCuentaBO;
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
import pe.com.tumi.framework.negocio.factory.TumiFactory;

@Stateless
public class EstadoCuentaFacade implements EstadoCuentaFacadeRemote, EstadoCuentaFacadeLocal {
	protected  static Logger log = Logger.getLogger(EstadoCuentaFacade.class);
	private EstadoCuentaBO estadoCuentaBO = (EstadoCuentaBO)TumiFactory.get(EstadoCuentaBO.class);

	public List<DataBeanEstadoCuentaResumenPrestamos> getResumenPrestamos(Integer intEmpresa, Integer intCuenta) throws BusinessException {
		List<DataBeanEstadoCuentaResumenPrestamos> lista = null;
    	try{
    		lista = estadoCuentaBO.getResumenPrestamos(intEmpresa, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaMontosBeneficios> getCabMtosBeneficios(Integer intEmpresa, Integer intCuenta) throws BusinessException {
		List<DataBeanEstadoCuentaMontosBeneficios> lista = null;
    	try{
    		lista = estadoCuentaBO.getCabMtosBeneficios(intEmpresa, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaSocioEstructura> getCabSocioEstructura(Integer intEmpresa, Integer intPersona) throws BusinessException {
		List<DataBeanEstadoCuentaSocioEstructura> lista = null;
    	try{
    		lista = estadoCuentaBO.getCabSocioEstructura(intEmpresa, intPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	//MODIFICADO 08.03.2014
	public List<DataBeanEstadoCuentaSocioCuenta> getCabCuentasSocio(Integer intEmpresa, Integer intPersona,Integer intTipoCuenta) throws BusinessException {
		List<DataBeanEstadoCuentaSocioCuenta> lista = null;
    	try{
    		lista = estadoCuentaBO.getCabCuentasSocio(intEmpresa, intPersona, intTipoCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaGestiones> getListaGestionCobranza(Integer intEmpresa, Integer intCuenta, String strFecha) throws BusinessException {
		List<DataBeanEstadoCuentaGestiones> lista = null;
    	try{
    		lista = estadoCuentaBO.getListaGestionCobranza(intEmpresa, intCuenta, strFecha);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<DataBeanEstadoCuentaDsctoTerceros> getListaColumnasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strNroDocIden) throws BusinessException {
		List<DataBeanEstadoCuentaDsctoTerceros> lista = null;
    	try{
    		lista = estadoCuentaBO.getListaColumnasPorPeriodoModalidadYDni(intPeriodo, intParaModalidadCod, strNroDocIden);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<DataBeanEstadoCuentaDsctoTerceros> getListaFilasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strNroDocIden) throws BusinessException {
		List<DataBeanEstadoCuentaDsctoTerceros> lista = null;
    	try{
    		lista = estadoCuentaBO.getListaFilasPorPeriodoModalidadYDni(intPeriodo, intParaModalidadCod, strNroDocIden);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}	

	public List<DataBeanEstadoCuentaDsctoTerceros> getMontoTotalPorNomCptoYPeriodo(String strDsteCpto, String strNomCpto, Integer intAnio, Integer intMes, Integer intParaModalidadCod, String strNroDocIden) throws BusinessException {
		List<DataBeanEstadoCuentaDsctoTerceros> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getMontoTotalPorNomCptoYPeriodo(strDsteCpto, strNomCpto, intAnio, intMes, intParaModalidadCod, strNroDocIden);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;
	}
	
	public List<DataBeanEstadoCuentaPlanillas> getResumenPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException {
		List<DataBeanEstadoCuentaPlanillas> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getResumenPlanilla(intEmpresa, intCuenta, intPeriodo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;
	}

	public List<DataBeanEstadoCuentaPlanillas> getDiferenciaPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException {
		List<DataBeanEstadoCuentaPlanillas> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getDiferenciaPlanilla(intEmpresa, intCuenta, intPeriodo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;
	}
	public List<DataBeanEstadoCuentaPlanillas> getFilasDifPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException {
		List<DataBeanEstadoCuentaPlanillas> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getFilasDifPlanilla(intEmpresa, intCuenta, intPeriodo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;
	}
	
	public List<DataBeanEstadoCuentaPlanillas> getColumnasDifPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException {
		List<DataBeanEstadoCuentaPlanillas> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getColumnasDifPlanilla(intEmpresa, intCuenta, intPeriodo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;
	}
	
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoAprobado(Integer intEmpresa, Integer intCuenta, Integer intTipoCredito, Integer intEstadoCredito) throws BusinessException {
		List<DataBeanEstadoCuentaPrestamos> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getPrestamoAprobado(intEmpresa, intCuenta, intTipoCredito, intEstadoCredito);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;
	}

	public List<DataBeanEstadoCuentaPrestamos> getPrestamoRechazado(Integer intEmpresa, Integer intCuenta, Integer intTipoCredito) throws BusinessException {
		List<DataBeanEstadoCuentaPrestamos> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getPrestamoRechazado(intEmpresa, intCuenta, intTipoCredito);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;
	}
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoGarantizado(Integer intEmpresa, Integer intPersona, Integer intCuenta) throws BusinessException{
		List<DataBeanEstadoCuentaPrestamos> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getPrestamoGarantizado(intEmpresa, intPersona, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;		
	}

	public List<DataBeanEstadoCuentaPrevisionSocial> getBenefOtorgados(Integer intEmpresa, Integer intPersona, Integer intCuenta) throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getBenefOtorgados(intEmpresa, intPersona, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;		
	}
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getPeriodoCtaCto(Integer intEmpresa, Integer intCuenta, Integer intTipoConcepto) throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getPeriodoCtaCto(intEmpresa, intCuenta, intTipoConcepto);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;		
	}
	
    public DataBeanEstadoCuentaPrevisionSocial getSumMontoPago(Integer intEmpresa, Integer intCuenta, Integer intItemCtaCto, Integer intItemCtaCtoDet, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
    	DataBeanEstadoCuentaPrevisionSocial dto = null;
    	try{
    		dto = estadoCuentaBO.getSumMontoPago(intEmpresa, intCuenta, intItemCtaCto, intItemCtaCtoDet, intPeriodoInicio, intPeriodoFin);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
    }
    
	public List<DataBeanEstadoCuentaPrevisionSocial> getConceptoPago(Integer intEmpresa, Integer intCuenta, Integer intItemCtaCto, Integer intItemCtaCtoDet, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getConceptoPago(intEmpresa, intCuenta, intItemCtaCto, intItemCtaCtoDet, intPeriodoInicio, intPeriodoFin);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;		
	}    

	public List<DataBeanEstadoCuentaPrevisionSocial> getMovimientoFdoSepelio(Integer intEmpresa, Integer intCuenta, Integer intItemCtaCto, Integer intItemCtaCtoDet, Integer intItemCptoPago) throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> dscto = null;
    	try{
    		dscto = estadoCuentaBO.getMovimientoFdoSepelio(intEmpresa, intCuenta, intItemCtaCto, intItemCtaCtoDet, intItemCptoPago);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;		
	}
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getMovFdoRetiroInteres(Integer intEmpresa, Integer intCuenta) throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
    	try{
    		lista = estadoCuentaBO.getMovFdoRetiroInteres(intEmpresa, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;	
	}
	
	public DataBeanEstadoCuentaSocioEstructura getSocioPorDocumento(Integer intTipoDocumento, String strNumeroDocIdent) throws BusinessException {
		DataBeanEstadoCuentaSocioEstructura dto = null;
    	try{
    		dto = estadoCuentaBO.getSocioPorDocumento(intTipoDocumento, strNumeroDocIdent);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}

	public List<DataBeanEstadoCuentaSocioCuenta> getSocioPorNombres(Integer intEmpresa, String strNombre) throws BusinessException {
	   	List<DataBeanEstadoCuentaSocioCuenta> lista = null;
	   	try{	
			lista = estadoCuentaBO.getSocioPorNombres(intEmpresa, strNombre);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<DataBeanEstadoCuentaDetalleMovimiento> getDetalleMovimiento(Integer intEmpresa, Integer intPersona, Integer intCuenta) throws BusinessException {
	   	List<DataBeanEstadoCuentaDetalleMovimiento> lista = null;
	   	try{	
			lista = estadoCuentaBO.getDetalleMovimiento(intEmpresa, intPersona, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}	
	
	public DataBeanEstadoCuentaSocioEstructura getSocioPorNumeroCuenta(Integer intEmpresa, Integer intCuenta) throws BusinessException {
		DataBeanEstadoCuentaSocioEstructura dto = null;
    	try{
    		dto = estadoCuentaBO.getSocioPorNumeroCuenta(intEmpresa, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
}
