package pe.com.tumi.estadoCuenta.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.estadoCuenta.dao.EstadoCuentaDao;
import pe.com.tumi.estadoCuenta.dao.impl.EstadoCuentaDaoIbatis;
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
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class EstadoCuentaBO {
	
	protected  static Logger log = Logger.getLogger(EstadoCuentaBO.class);
	private EstadoCuentaDao dao = (EstadoCuentaDao)TumiFactory.get(EstadoCuentaDaoIbatis.class);
	
	public List<DataBeanEstadoCuentaResumenPrestamos> getResumenPrestamos(Integer intEmpresa, Integer intCuenta) throws BusinessException{
		List<DataBeanEstadoCuentaResumenPrestamos> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("intCuenta",	intCuenta);
			
			lista = dao.getResumenPrestamos(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	
	public List<DataBeanEstadoCuentaMontosBeneficios> getCabMtosBeneficios(Integer intEmpresa, Integer intCuenta) throws BusinessException{
		List<DataBeanEstadoCuentaMontosBeneficios> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("intCuenta",	intCuenta);
			
			lista = dao.getCabMtosBeneficios(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}	
	
	public List<DataBeanEstadoCuentaSocioEstructura> getCabSocioEstructura(Integer intEmpresa, Integer intPersona) throws BusinessException{
		List<DataBeanEstadoCuentaSocioEstructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("intPersona",	intPersona);
			
			lista = dao.getCabSocioEstructura(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}	
	
	public List<DataBeanEstadoCuentaSocioCuenta> getCabCuentasSocio(Integer intEmpresa, Integer intPersona, Integer intTipoCuenta) throws BusinessException{
		List<DataBeanEstadoCuentaSocioCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("intPersona",	intPersona);
			mapa.put("intTipoCuenta",	intTipoCuenta);
						
			lista = dao.getCabCuentasSocio(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	
	public List<DataBeanEstadoCuentaGestiones> getListaGestionCobranza(Integer intEmpresa, Integer intCuenta, String strFecha) throws BusinessException{
		List<DataBeanEstadoCuentaGestiones> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("intCuenta",	intCuenta);
			mapa.put("strFecha",	strFecha);
			
			lista = dao.getListaGestionCobranza(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	//Terceros - Filas
	public List<DataBeanEstadoCuentaDsctoTerceros> getListaFilasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strNroDocIden) throws BusinessException{
		List<DataBeanEstadoCuentaDsctoTerceros> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPeriodo", 			intPeriodo);
			mapa.put("intParaModalidadCod", intParaModalidadCod);
			mapa.put("strNroDocIden", 		strNroDocIden);
			lista = dao.getListaFilasPorPeriodoModalidadYDni(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	//Terceros - Columnas
	public List<DataBeanEstadoCuentaDsctoTerceros> getListaColumnasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strNroDocIden) throws BusinessException{
		List<DataBeanEstadoCuentaDsctoTerceros> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPeriodo", 			intPeriodo);
			mapa.put("intParaModalidadCod", intParaModalidadCod);
			mapa.put("strNroDocIden", 		strNroDocIden);
			lista = dao.getListaColumnasPorPeriodoModalidadYDni(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	//Terceros - Montos
	public List<DataBeanEstadoCuentaDsctoTerceros> getMontoTotalPorNomCptoYPeriodo(String strDsteCpto,String strNomCpto,Integer intAnio, Integer intMes, Integer intParaModalidadCod, String strNroDocIden) throws BusinessException{
		List<DataBeanEstadoCuentaDsctoTerceros> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("strDsteCpto", 		strDsteCpto);
			mapa.put("strNomCpto", 			strNomCpto);
			mapa.put("intAnio", 			intAnio);
			mapa.put("intMes", 				intMes);
			mapa.put("intParaModalidadCod", intParaModalidadCod);
			mapa.put("strNroDocIden", 		strNroDocIden);
			lista = dao.getMontoTotalPorNomCptoYPeriodo(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}	
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPlanillas> getResumenPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException{
		List<DataBeanEstadoCuentaPlanillas> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 		intEmpresa);
			mapa.put("intCuenta", 		intCuenta);
			mapa.put("intPeriodo", 		intPeriodo);
			lista = dao.getResumenPlanilla(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}	
		return lista;
	}	
	
	public List<DataBeanEstadoCuentaPlanillas> getFilasDifPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException {
		List<DataBeanEstadoCuentaPlanillas> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 		intEmpresa);
			mapa.put("intCuenta", 		intCuenta);
			mapa.put("intPeriodo", 		intPeriodo);
			lista = dao.getFilasDifPlanilla(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}	
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPlanillas> getColumnasDifPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException {
		List<DataBeanEstadoCuentaPlanillas> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 		intEmpresa);
			mapa.put("intCuenta", 		intCuenta);
			mapa.put("intPeriodo", 		intPeriodo);
			lista = dao.getColumnasDifPlanilla(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}	
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPlanillas> getDiferenciaPlanilla(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException {
		List<DataBeanEstadoCuentaPlanillas> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 		intEmpresa);
			mapa.put("intCuenta", 		intCuenta);
			mapa.put("intPeriodo", 		intPeriodo);
			lista = dao.getDiferenciaPlanilla(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}	
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoAprobado(Integer intEmpresa, Integer intCuenta, Integer intTipoCredito, Integer intEstadoCredito) throws BusinessException {
		List<DataBeanEstadoCuentaPrestamos> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 			intEmpresa);
			mapa.put("intCuenta", 			intCuenta);
			mapa.put("intTipoCredito", 		intTipoCredito);
			mapa.put("intEstadoCredito",	intEstadoCredito);
			lista = dao.getPrestamoAprobado(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}	
		return lista;
	}	
	
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoRechazado(Integer intEmpresa, Integer intCuenta, Integer intTipoCredito) throws BusinessException {
		List<DataBeanEstadoCuentaPrestamos> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 			intEmpresa);
			mapa.put("intCuenta", 			intCuenta);
			mapa.put("intTipoCredito", 		intTipoCredito);
			lista = dao.getPrestamoRechazado(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}	
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrestamos> getPrestamoGarantizado(Integer intEmpresa, Integer intPersona, Integer intCuenta) throws BusinessException{
		List<DataBeanEstadoCuentaPrestamos> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("intPersona",	intPersona);
			mapa.put("intCuenta",   intCuenta);
			lista = dao.getPrestamoGarantizado(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getBenefOtorgados(Integer intEmpresa, Integer intPersona, Integer intCuenta) throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("intPersona",	intPersona);
			mapa.put("intCuenta",   intCuenta);
			lista = dao.getBenefOtorgados(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getPeriodoCtaCto(Integer intEmpresa, Integer intCuenta, Integer intTipoConcepto) throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 		intEmpresa);
			mapa.put("intCuenta",   	intCuenta);
			mapa.put("intTipoConcepto", intTipoConcepto);
			lista = dao.getPeriodoCtaCto(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	
	public DataBeanEstadoCuentaPrevisionSocial getSumMontoPago(Integer intEmpresa, Integer intCuenta, Integer intItemCtaCto, Integer intItemCtaCtoDet, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		DataBeanEstadoCuentaPrevisionSocial domain = null;
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 			intEmpresa);
			mapa.put("intCuenta",			intCuenta);
			mapa.put("intItemCtaCto",   	intItemCtaCto);
			mapa.put("intItemCtaCtoDet", 	intItemCtaCtoDet);
			mapa.put("intPeriodoInicio",	intPeriodoInicio);
			mapa.put("intPeriodoFin",   	intPeriodoFin);
			lista = dao.getSumMontoPago(mapa);	
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return domain;
	}
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getConceptoPago(Integer intEmpresa, Integer intCuenta, Integer intItemCtaCto, Integer intItemCtaCtoDet, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 			intEmpresa);
			mapa.put("intCuenta",			intCuenta);
			mapa.put("intItemCtaCto",   	intItemCtaCto);
			mapa.put("intItemCtaCtoDet", 	intItemCtaCtoDet);
			mapa.put("intPeriodoInicio",	intPeriodoInicio);
			mapa.put("intPeriodoFin",   	intPeriodoFin);
			lista = dao.getConceptoPago(mapa);	
	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}	

	public List<DataBeanEstadoCuentaPrevisionSocial> getMovimientoFdoSepelio(Integer intEmpresa, Integer intCuenta, Integer intItemCtaCto, Integer intItemCtaCtoDet, Integer intItemCptoPago) throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 			intEmpresa);
			mapa.put("intCuenta",			intCuenta);
			mapa.put("intItemCtaCto",   	intItemCtaCto);
			mapa.put("intItemCtaCtoDet", 	intItemCtaCtoDet);
			mapa.put("intItemCptoPago",		intItemCptoPago);
			lista = dao.getMovimientoFdoSepelio(mapa);	
	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}	
	
	public List<DataBeanEstadoCuentaPrevisionSocial> getMovFdoRetiroInteres(Integer intEmpresa, Integer intCuenta) throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("intCuenta",	intCuenta);
			
			lista = dao.getMovFdoRetiroInteres(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}

	public DataBeanEstadoCuentaSocioEstructura getSocioPorDocumento(Integer intTipoDocumento, String strNumeroDocIdent) throws BusinessException{
		DataBeanEstadoCuentaSocioEstructura domain = null;
		List<DataBeanEstadoCuentaSocioEstructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intTipoDocumento", 	intTipoDocumento);
			mapa.put("strNumeroDocumento",	strNumeroDocIdent);
			
			lista = dao.getSocioPorDocumento(mapa);	
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}	
		return domain;
	}

	public List<DataBeanEstadoCuentaSocioCuenta> getSocioPorNombres(Integer intEmpresa, String strNombre) throws BusinessException{
		List<DataBeanEstadoCuentaSocioCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("strNombre",	strNombre);
			
			lista = dao.getSocioPorNombres(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	
	public List<DataBeanEstadoCuentaDetalleMovimiento> getDetalleMovimiento(Integer intEmpresa, Integer intPersona, Integer intCuenta) throws BusinessException{
		List<DataBeanEstadoCuentaDetalleMovimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("intPersona",	intPersona);
			mapa.put("intCuenta",	intCuenta);			
			
			lista = dao.getDetalleMovimiento(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}	
	
	public DataBeanEstadoCuentaSocioEstructura getSocioPorNumeroCuenta(Integer intEmpresa, Integer intCuenta) throws BusinessException{
		DataBeanEstadoCuentaSocioEstructura domain = null;
		List<DataBeanEstadoCuentaSocioEstructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 	intEmpresa);
			mapa.put("intCuenta",	intCuenta);
			
			lista = dao.getSocioPorNumeroCuenta(mapa);	
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}	
		return domain;
	}	
}
