package pe.com.tumi.contabilidad.core.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleBO;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleCuentaBO;
import pe.com.tumi.contabilidad.cierre.bo.LibroDiarioDetalleBO;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.core.bo.AccesoPlanCuentaBO;
import pe.com.tumi.contabilidad.core.bo.AccesoPlanCuentaDetalleBO;
import pe.com.tumi.contabilidad.core.bo.PlanCuentaBO;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuenta;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuentaDetalle;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class PlanCuentaService {
	protected static Logger log = Logger.getLogger(PlanCuentaService.class);
	PlanCuentaBO boPlanCuenta = (PlanCuentaBO)TumiFactory.get(PlanCuentaBO.class);
	AccesoPlanCuentaBO boAccesoPlanCuenta = (AccesoPlanCuentaBO)TumiFactory.get(AccesoPlanCuentaBO.class);
	AccesoPlanCuentaDetalleBO boAccesoPlanCuentaDetalle = (AccesoPlanCuentaDetalleBO)TumiFactory.get(AccesoPlanCuentaDetalleBO.class);
	LibroDiarioDetalleBO boLibroDiarioDetalle = (LibroDiarioDetalleBO)TumiFactory.get(LibroDiarioDetalleBO.class);
	//Agregado por cdelosrios, 16/09/2013
	AnexoDetalleBO boAnexoDetalle = (AnexoDetalleBO)TumiFactory.get(AnexoDetalleBO.class);
	AnexoDetalleCuentaBO boAnexoDetalleCuenta = (AnexoDetalleCuentaBO)TumiFactory.get(AnexoDetalleCuentaBO.class);
	//Fin agregado por cdelosrios, 16/09/2013
	
	public List<PlanCuenta> buscarListaPlanCuenta(PlanCuenta planCuentaFiltro, Usuario usuario, Integer intIdTransaccion) 
	throws BusinessException{
		List<PlanCuenta> listaPlanCuenta = null;
		try{
			if(planCuentaFiltro.getId().getIntPeriodoCuenta().equals(new Integer(0))
			|| planCuentaFiltro.getId().getIntPeriodoCuenta().equals(new Integer(-1))){
				planCuentaFiltro.getId().setIntPeriodoCuenta(null);
			}
			
			if(planCuentaFiltro.getIntTipoBusqueda().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION)){
				planCuentaFiltro.getId().setStrNumeroCuenta(null);
				planCuentaFiltro.setStrDescripcion(planCuentaFiltro.getStrComentario());
				listaPlanCuenta = boPlanCuenta.getPlanCuentaBusqueda(planCuentaFiltro);
			}else if(planCuentaFiltro.getIntTipoBusqueda().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_CUENTACONTABLE)){
				planCuentaFiltro.getId().setStrNumeroCuenta(planCuentaFiltro.getStrComentario());
				planCuentaFiltro.setStrDescripcion(null);
				listaPlanCuenta = boPlanCuenta.getPlanCuentaBusqueda(planCuentaFiltro);
			}
			
			if(listaPlanCuenta != null){
				List<PlanCuenta> listaTemp = new ArrayList<PlanCuenta>();
				for(PlanCuenta planCuenta : listaPlanCuenta)
					if(validarPlanCuenta(planCuenta, usuario, intIdTransaccion))
						listaTemp.add(planCuenta);				
				listaPlanCuenta = listaTemp;
			}
			
			//Ordenamos por nombre			
			Collections.sort(listaPlanCuenta, new Comparator<PlanCuenta>(){
				public int compare(PlanCuenta uno, PlanCuenta otro) {
					return uno.getId().getStrNumeroCuenta().compareTo(otro.getId().getStrNumeroCuenta());
				}
			});
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaPlanCuenta;
	}
	
	private boolean validarPlanCuenta(PlanCuenta planCuenta, Usuario usuario, Integer intIdTransaccion)throws Exception{
		planCuenta.setListaAccesoPlanCuenta(boAccesoPlanCuenta.getPorPlanCuenta(planCuenta, intIdTransaccion));
		if(planCuenta.getListaAccesoPlanCuenta()==null	|| planCuenta.getListaAccesoPlanCuenta().isEmpty())
			return Boolean.FALSE;
		
		log.info(planCuenta);
		log.info(intIdTransaccion);
		log.info(usuario.getSubSucursal().getId().getIntIdSucursal());
		log.info(usuario.getSubSucursal().getId().getIntIdSubSucursal());
		log.info(usuario.getPerfil().getId().getIntIdPerfil());
		for(AccesoPlanCuenta accesoPlanCuenta : planCuenta.getListaAccesoPlanCuenta()){
			log.info(accesoPlanCuenta);
			if(accesoPlanCuenta.getId().getIntIdTransaccion().equals(intIdTransaccion)
			&& accesoPlanCuenta.getIntSucuIdSucursal().equals(usuario.getSucursal().getId().getIntIdSucursal())
			&& accesoPlanCuenta.getIntSudeIdSubsucursal().equals(usuario.getSubSucursal().getId().getIntIdSubSucursal())
			&& accesoPlanCuenta.getIntIdPerfil().equals(usuario.getPerfil().getId().getIntIdPerfil())
			&& accesoPlanCuenta.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				accesoPlanCuenta.setListaAccesoPlanCuentaDetalle(boAccesoPlanCuentaDetalle.getPorAccesoPlanCuenta(accesoPlanCuenta));
				if(accesoPlanCuenta.getListaAccesoPlanCuentaDetalle()!=null && !accesoPlanCuenta.getListaAccesoPlanCuentaDetalle().isEmpty()){
					planCuenta.setAccesoPlanCuentaUsar(accesoPlanCuenta);
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
	
	private AccesoPlanCuentaDetalle obtenerAccesoPlanCuentaDetalleUsar(AccesoPlanCuenta accesoPlanCuenta)throws Exception{
		//accesoPlanCuenta.setListaAccesoPlanCuentaDetalle(boAccesoPlanCuentaDetalle.getPorAccesoPlanCuenta(accesoPlanCuenta));
		for(AccesoPlanCuentaDetalle accesoPlanCuentaDetalle : accesoPlanCuenta.getListaAccesoPlanCuentaDetalle()){
			if(accesoPlanCuentaDetalle.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO))
				return accesoPlanCuentaDetalle;
		}
		return null;
	}
	
	private List<LibroDiarioDetalle> obtenerLibroDiarioDetalle(PlanCuenta planCuenta, Integer intMes)throws Exception{
		LibroDiarioDetalle libroDiarioDetalleFiltro = new LibroDiarioDetalle();
		//Agregado por cdelosrios, 07/11/2013
		if(libroDiarioDetalleFiltro.getId()==null)libroDiarioDetalleFiltro.setId(new LibroDiarioDetalleId());
		//Fin agregado por cdelosrios, 07/11/2013
		libroDiarioDetalleFiltro.getId().setIntPersEmpresaLibro(planCuenta.getId().getIntEmpresaCuentaPk());
		libroDiarioDetalleFiltro.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual(intMes));
		libroDiarioDetalleFiltro.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
		libroDiarioDetalleFiltro.setIntContPeriodo(planCuenta.getId().getIntPeriodoCuenta());
		libroDiarioDetalleFiltro.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());		
		log.info(libroDiarioDetalleFiltro);
		return boLibroDiarioDetalle.getListaPorBuscar(libroDiarioDetalleFiltro);		
	}
	
	public boolean validarMontoPlanCuenta(PlanCuenta planCuenta, BigDecimal bdMontoValidar) 
	throws BusinessException{
		Boolean montoValido = Boolean.TRUE;
		try{
			AccesoPlanCuentaDetalle accesoPlanCuentaDetalleUsar = obtenerAccesoPlanCuentaDetalleUsar(planCuenta.getAccesoPlanCuentaUsar());
			List<LibroDiarioDetalle> listaLibroDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
			
			if(accesoPlanCuentaDetalleUsar.getIntParaPeriodoRestriccion().equals(Constante.PARAM_T_FRECUENCPAGOINT_MENSUAL)){
				listaLibroDiarioDetalle = obtenerLibroDiarioDetalle(planCuenta, MyUtil.obtenerMesActual());
				
			}else if(accesoPlanCuentaDetalleUsar.getIntParaPeriodoRestriccion().equals(Constante.PARAM_T_FRECUENCPAGOINT_ANUAL)){
				int intMesActual = MyUtil.obtenerMesActual();
				for(int intMes=1; intMes<=intMesActual; intMes++)
					listaLibroDiarioDetalle.addAll(obtenerLibroDiarioDetalle(planCuenta, intMes));
			}
			
			BigDecimal bdMontoTotal = new BigDecimal(0);
			if(listaLibroDiarioDetalle!=null 
			&& accesoPlanCuentaDetalleUsar.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE))
				for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle)
					if(libroDiarioDetalle.getBdDebeSoles()!=null) 
						bdMontoTotal = bdMontoTotal.add(libroDiarioDetalle.getBdDebeSoles());
			
			
			if(listaLibroDiarioDetalle!=null 
			&& accesoPlanCuentaDetalleUsar.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER))
				for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle)
					if(libroDiarioDetalle.getBdHaberSoles()!=null)
						bdMontoTotal = bdMontoTotal.add(libroDiarioDetalle.getBdHaberSoles());
			
				
			
			bdMontoTotal = bdMontoTotal.add(bdMontoValidar);
			
			if((accesoPlanCuentaDetalleUsar.getBdMontoMaximo()!=null
			&& accesoPlanCuentaDetalleUsar.getBdMontoMaximo().compareTo(bdMontoTotal)<0)){
				montoValido = Boolean.FALSE;
			}
			
			if((accesoPlanCuentaDetalleUsar.getBdMontoMinimo()!=null
			&& accesoPlanCuentaDetalleUsar.getBdMontoMinimo().compareTo(bdMontoTotal)>0)){
				montoValido = Boolean.FALSE;
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return montoValido;
	}
	
	//Agregado por cdelosrios, 16/09/2013
	public PlanCuenta grabarPlanCuenta(PlanCuenta o) throws BusinessException {
		PlanCuenta planCuenta = null;
		
		try{
			planCuenta = boPlanCuenta.grabarPlanCuenta(o);
			if(o.getListaAnexoDetalleCuenta()!=null && o.getListaAnexoDetalleCuenta().size()>0){
				grabarListaDinamicaAnexoDetalleCuenta(o.getListaAnexoDetalleCuenta());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return planCuenta;
	}
	
	public PlanCuenta modificarPlanCuenta(PlanCuenta o) throws BusinessException {
		PlanCuenta planCuenta = null;
		
		try{
			planCuenta = boPlanCuenta.modificarPlanCuenta(o);
			if(o.getListaAnexoDetalleCuenta()!=null && o.getListaAnexoDetalleCuenta().size()>0){
				grabarListaDinamicaAnexoDetalleCuenta(o.getListaAnexoDetalleCuenta());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return planCuenta;
	}
	
	public List<AnexoDetalleCuenta> grabarListaDinamicaAnexoDetalleCuenta(List<AnexoDetalleCuenta> lstAnexoDetalleCuenta) throws BusinessException{
		AnexoDetalleCuenta anexoDetalleCuentaTemp = null;
		
		try{
			for(AnexoDetalleCuenta anexoDetalleCuenta : lstAnexoDetalleCuenta){
				if(anexoDetalleCuenta.getId()==null || anexoDetalleCuenta.getId().getIntItemAnexoDetalleCuenta()==null){
					anexoDetalleCuenta = boAnexoDetalleCuenta.grabar(anexoDetalleCuenta);
				} else {
					anexoDetalleCuentaTemp = boAnexoDetalleCuenta.getPorPk(anexoDetalleCuenta.getId());
					if(anexoDetalleCuentaTemp == null){
						boAnexoDetalleCuenta.grabar(anexoDetalleCuenta);
					}else{
						boAnexoDetalleCuenta.modificar(anexoDetalleCuenta);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstAnexoDetalleCuenta;
	}
	
	public List<AnexoDetalleCuenta> getAnexoDetalleCuentaPorPlanCuenta(PlanCuenta o)throws Exception{
		List<AnexoDetalleCuenta> lstAnexoDetalleCuenta = null;
		AnexoDetalle anexoDetalle = null;
		TablaFacadeRemote tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
		try {
			lstAnexoDetalleCuenta = boAnexoDetalleCuenta.getPorPlanCuenta(o);
			if(lstAnexoDetalleCuenta!=null && !lstAnexoDetalleCuenta.isEmpty()){
				for(AnexoDetalleCuenta anexoDetalleCuenta : lstAnexoDetalleCuenta){
					anexoDetalle = new AnexoDetalle();
					anexoDetalle.setId(new AnexoDetalleId());
					anexoDetalle.getId().setIntPersEmpresaAnexo(anexoDetalleCuenta.getId().getIntPersEmpresaAnexo());
					anexoDetalle.getId().setIntContPeriodoAnexo(anexoDetalleCuenta.getId().getIntContPeriodoAnexo());
					anexoDetalle.getId().setIntParaTipoAnexo(anexoDetalleCuenta.getId().getIntParaTipoAnexo());
					anexoDetalle.getId().setIntItemAnexoDetalle(anexoDetalleCuenta.getId().getIntItemAnexoDetalle());
					anexoDetalle = boAnexoDetalle.getPorPk(anexoDetalle.getId());
					
					Tabla tablaEstadoFinanciero = tablaFacade.getTablaPorIdMaestroYIdDetalle(new Integer(Constante.PARAM_T_ESTADOSFINANCIEROS), 
							anexoDetalleCuenta.getId().getIntParaTipoAnexo());
					anexoDetalleCuenta.setStrTexto(
							anexoDetalleCuenta.getId().getIntContPeriodoAnexo() + " - " +
							tablaEstadoFinanciero.getStrDescripcion() + " - " +
							anexoDetalle.getStrTexto());
					anexoDetalleCuenta.setAnexoDetalle(anexoDetalle);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return lstAnexoDetalleCuenta;		
	}
	//Fin agregado por cdelosrios, 16/09/2013
}