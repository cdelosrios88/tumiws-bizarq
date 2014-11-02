/**
* Resumen.
* Objeto: ConciliacionFacade
* Descripción:  Facade principal del proceso de conciliacion bancaria.
* Fecha de Creación: 18/10/2014.
* Requerimiento de Creación: REQ14-006
* Autor: Bizarq
*/
package pe.com.tumi.tesoreria.conciliacion.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteActividad;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CapacidadCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.BancocuentaId;
import pe.com.tumi.tesoreria.egreso.bo.ConciliacionBO;
import pe.com.tumi.tesoreria.egreso.bo.ConciliacionDetalleBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoBO;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalleId;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionId;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.EgresoId;
import pe.com.tumi.tesoreria.egreso.domain.comp.ConciliacionComp;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;


public class ConciliacionService {

	protected static Logger log = Logger.getLogger(pe.com.tumi.tesoreria.egreso.service.ConciliacionService.class);

	private ConciliacionBO boConciliacion = (ConciliacionBO)TumiFactory.get(ConciliacionBO.class);
	private IngresoBO boIngreso = (IngresoBO)TumiFactory.get(IngresoBO.class);
	private EgresoBO boEgreso = (EgresoBO)TumiFactory.get(EgresoBO.class);
	private ConciliacionDetalleBO boConciliacionDet = (ConciliacionDetalleBO)TumiFactory.get(ConciliacionDetalleBO.class);
	

	/**
	 * Recupera las conciliaciones segun filtros de busqueda.
	 * Asociado a boton BUSCAR
	 * @param conciliacionCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<Conciliacion> getListFilter(ConciliacionComp conciliacionCompBusq)throws BusinessException{
		List<Conciliacion> lstConciliacion = null;
		try {
			lstConciliacion = boConciliacion.getListFilter(conciliacionCompBusq);
		
		} catch (Exception e) {
			log.error("Error en getListaBusqConciliacionFiltros --> "+e);
		}
		return lstConciliacion;
	}
	
	/**
	 * Asociado al boton MOSTRAR DATOS
	 * @param conciliacion
	 * @return
	 * @throws BusinessException
	 */
	public List<ConciliacionDetalle> buscarRegistrosConciliacion(Conciliacion conciliacion)throws BusinessException{
		List<ConciliacionDetalle> listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalleFin = new ArrayList<ConciliacionDetalle>();
		try{
			Ingreso ingresoFiltro = new Ingreso();
			// SOLO PRUEBAS RSIS14-006 
			//conciliacion.setBancoCuenta(new Bancocuenta());
			//conciliacion.getBancoCuenta().setId(new BancocuentaId());
			//conciliacion.getBancoCuenta().getId().setIntEmpresaPk(2); // tumi
			//conciliacion.setIntParaDocumentoGeneralFiltro(new Integer("302"));// transfer a tercerso
			//conciliacion.getBancoCuenta().getId().setIntItembancocuenta(6);
			//conciliacion.getBancoCuenta().getId().setIntItembancofondo(2);
			// ---->

			ingresoFiltro.getId().setIntIdEmpresa((conciliacion.getBancoCuenta().getId().getIntEmpresaPk()));
			ingresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			ingresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			ingresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			List<Ingreso> listaIngreso = boIngreso.getListaParaBuscar(ingresoFiltro);
			
			if(listaIngreso != null && listaIngreso.size() >0){
				for(Ingreso ingreso : listaIngreso){
					ConciliacionDetalle conciliacionDet = new ConciliacionDetalle();
					//conciliacionDet.setId(new ConciliacionDetalleId());
					conciliacionDet.setIngreso(ingreso);
					listaConciliacionDetalle.add(conciliacionDet);
				}
			}
			
		   // solo para pruebas
			//conciliacion.setBancoCuenta(new Bancocuenta());
			//conciliacion.getBancoCuenta().setId(new BancocuentaId());
			//conciliacion.getBancoCuenta().getId().setIntEmpresaPk(2); // tumi
			//conciliacion.setIntParaDocumentoGeneralFiltro(new Integer("301"));// transfer a tercerso
			//conciliacion.getBancoCuenta().getId().setIntItembancocuenta(16);
			//conciliacion.getBancoCuenta().getId().setIntItembancofondo(16); 
		      
			
			Egreso egresoFiltro = new Egreso();
			egresoFiltro.getId().setIntPersEmpresaEgreso(conciliacion.getBancoCuenta().getId().getIntEmpresaPk());
			egresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			egresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			egresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			List<Egreso> listaEgreso = boEgreso.getListaPorBuscar(egresoFiltro, null, null);
			
			if(listaEgreso != null && listaEgreso.size() >0){
				for(Egreso egreso : listaEgreso){
					ConciliacionDetalle conciliacionDet = new ConciliacionDetalle();
					//conciliacionDet.setId(new ConciliacionDetalleId());
					conciliacionDet.setEgreso(egreso);
					listaConciliacionDetalle.add(conciliacionDet);
				}
			}
			
			for( ConciliacionDetalle conciliacionDet : listaConciliacionDetalle){
				conciliacionDet = convertEgresoIngresoAConcilDet(conciliacionDet);
				listaConciliacionDetalleFin.add(conciliacionDet);
			}	
			
		}catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaConciliacionDetalleFin;
	}
	
	/**
	Busca registros segun Tipo Doc, Fecha
	*/
	public List<ConciliacionDetalle> buscar(Conciliacion conciliacion)throws BusinessException{
		List<ConciliacionDetalle> listaConciliacionDetalleTemp = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
		try{
			Ingreso ingresoFiltro = new Ingreso();
			// SOLO PRUEBAS RSIS14-006 
			conciliacion.setBancoCuenta(new Bancocuenta());
			conciliacion.getBancoCuenta().setId(new BancocuentaId());
			conciliacion.getBancoCuenta().getId().setIntEmpresaPk(2); // tumi
			conciliacion.setIntParaDocumentoGeneralFiltro(new Integer("302"));// transfer a tercerso
			conciliacion.getBancoCuenta().getId().setIntItembancocuenta(6);
			conciliacion.getBancoCuenta().getId().setIntItembancofondo(2);
			// ---->

			ingresoFiltro.getId().setIntIdEmpresa((conciliacion.getBancoCuenta().getId().getIntEmpresaPk()));
			ingresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			ingresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			ingresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			List<Ingreso> listaIngreso = boIngreso.getListaParaBuscar(ingresoFiltro);
			
			if(listaIngreso != null && listaIngreso.size() >0){
				for(Ingreso ingreso : listaIngreso){
					IngresoDetalle ingDet = null;
					ingDet = recuperarIngresoDetConcil(ingreso);
					if(ingDet != null){
						ingreso.setIngresoDetConciliacion(ingDet);
					}
					ConciliacionDetalle conciliacionDet = new ConciliacionDetalle();
					//conciliacionDet.setId(new ConciliacionDetalleId());
					conciliacionDet.setIngreso(ingreso);
					listaConciliacionDetalleTemp.add(conciliacionDet);
				}				
			}
			
		   // solo para pruebas
			conciliacion.setBancoCuenta(new Bancocuenta());
			conciliacion.getBancoCuenta().setId(new BancocuentaId());
			conciliacion.getBancoCuenta().getId().setIntEmpresaPk(2); // tumi
			conciliacion.setIntParaDocumentoGeneralFiltro(new Integer("301"));// transfer a tercerso
			conciliacion.getBancoCuenta().getId().setIntItembancocuenta(16);
			conciliacion.getBancoCuenta().getId().setIntItembancofondo(16); 
		      
			
			Egreso egresoFiltro = new Egreso();
			egresoFiltro.getId().setIntPersEmpresaEgreso(conciliacion.getBancoCuenta().getId().getIntEmpresaPk());
			egresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			egresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			egresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			List<Egreso> listaEgreso = boEgreso.getListaPorBuscar(egresoFiltro, null, null);
			
			if(listaEgreso != null && listaEgreso.size() >0){
				for(Egreso egreso : listaEgreso){
					EgresoDetalle egrDet = null;
					egrDet = recuperarEgresoDetConcil(egreso);
					if(egrDet != null){
						egreso.setEgresoDetConciliacion(egrDet);
					}
					ConciliacionDetalle conciliacionDet = new ConciliacionDetalle();
					//conciliacionDet.setId(new ConciliacionDetalleId());
					conciliacionDet.setEgreso(egreso);
					listaConciliacionDetalleTemp.add(conciliacionDet);
				}
			}

			for( ConciliacionDetalle conciliacionDet : listaConciliacionDetalleTemp){
				conciliacionDet = convertEgresoIngresoAConcilDet(conciliacionDet);
				listaConciliacionDetalle.add(conciliacionDet);
			}	
			
			
		}catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaConciliacionDetalle;
	}
	
	
	/**
	 * 
	 * @param pConciliacion
	 * @return
	 * @throws BusinessException
	 */
	public Conciliacion grabarConciliacion(Conciliacion pConciliacion) throws BusinessException{
		Conciliacion conciliacion = null;
		List<ConciliacionDetalle> listaConciliacionDetalleTemp = null;
		List<ConciliacionDetalle> listaConciliacionDetalle = null;
		
		try{
			conciliacion = boConciliacion.grabar(pConciliacion);
			
			listaConciliacionDetalleTemp = pConciliacion.getListaConciliacionDetalle();
			
			if(listaConciliacionDetalleTemp!=null && !listaConciliacionDetalleTemp.isEmpty()){
				listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
				for( ConciliacionDetalle conciliacionDet : listaConciliacionDetalleTemp){
					conciliacionDet = convertEgresoIngresoAConcilDet(conciliacionDet);
					listaConciliacionDetalle.add(conciliacionDet);
				}	
			}
			if(listaConciliacionDetalle!=null && !listaConciliacionDetalle.isEmpty()){
				grabarListaDinamicaConciliacionDetalle(listaConciliacionDetalle, conciliacion.getId());
			}
			
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarConciliacion ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarConciliacion ---> "+e);
			throw new BusinessException(e);
		}
		return conciliacion;
	}
	
	
	/**
	 * 
	 * @param lstConciliacionDetalle
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<ConciliacionDetalle> grabarListaDinamicaConciliacionDetalle(List<ConciliacionDetalle> lstConciliacionDetalle, ConciliacionId pPK) throws BusinessException{
		ConciliacionDetalle conciliacionDetalle = null;
		ConciliacionDetalleId pk = null;
		ConciliacionDetalle conciliacionDetalleTemp = null;
		try{
			for(int i=0; i<lstConciliacionDetalle.size(); i++){
				conciliacionDetalle = (ConciliacionDetalle) lstConciliacionDetalle.get(i);
				if(conciliacionDetalle.getId()== null || conciliacionDetalle.getId().getIntItemConciliacionDetalle()==null){
					pk = new ConciliacionDetalleId();
					pk.setIntPersEmpresa(pPK.getIntPersEmpresa());
					pk.setIntItemConciliacion(pPK.getIntItemConciliacion());
					conciliacionDetalle.setId(pk);
					conciliacionDetalle = boConciliacionDet.grabar(conciliacionDetalle);
				}else{
					conciliacionDetalleTemp = boConciliacionDet.getPorPk(conciliacionDetalle.getId());
					if(conciliacionDetalleTemp == null){
						conciliacionDetalle = boConciliacionDet.grabar(conciliacionDetalle);
					}else{
						conciliacionDetalle = boConciliacionDet.modificar(conciliacionDetalle);
					}
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaConciliacionDetalle ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaConciliacionDetalle ---> "+e);
			throw new BusinessException(e);
		}
		return lstConciliacionDetalle;
	}
	
	/**
	
	**/
	public ConciliacionDetalle convertEgresoIngresoAConcilDet(ConciliacionDetalle conciliacionDet){
		try {
			if(conciliacionDet.getEgreso() != null){
				conciliacionDet.setIntPersEmpresaEgreso(conciliacionDet.getEgreso().getId().getIntPersEmpresaEgreso());
				conciliacionDet.setIntItemEgresoGeneral(conciliacionDet.getEgreso().getId().getIntItemEgresoGeneral());
				conciliacionDet.setIntIndicadorCheck(0);
				conciliacionDet.setIntIndicadorConci(0);

				conciliacionDet.setBdMontoDebe(null);
				conciliacionDet.setBdMontoHaber(conciliacionDet.getEgreso().getBdMontoTotal());
				
				conciliacionDet.setIntNumeroOperacion((conciliacionDet.getEgreso().getIntNumeroPlanilla() == null) ? ((conciliacionDet.getEgreso().getIntNumeroCheque() == null) ? ((conciliacionDet.getEgreso().getIntNumeroTransferencia() == null) ? conciliacionDet.getEgreso().getIntNumeroTransferencia(): 0 ): conciliacionDet.getEgreso().getIntNumeroCheque()) : conciliacionDet.getEgreso().getIntNumeroPlanilla());
			
			} else if (conciliacionDet.getIngreso() != null){	
				conciliacionDet.setIntPersEmpresaIngreso(conciliacionDet.getIngreso().getId().getIntIdEmpresa());
				conciliacionDet.setIntItemIngresoGeneral(conciliacionDet.getIngreso().getId().getIntIdIngresoGeneral());
				conciliacionDet.setIntIndicadorCheck(0);
				conciliacionDet.setIntIndicadorConci(0);
				
				conciliacionDet.setBdMontoDebe(conciliacionDet.getIngreso().getBdMontoTotal());
				conciliacionDet.setBdMontoHaber(null); 
				
				conciliacionDet.setIntNumeroOperacion( new Integer(conciliacionDet.getIngreso().getStrNumeroOperacion()));
			}
			//conciliacionDet.setIntIndicadorCheck();
			//conciliacionDet.setIntIndicadorConci();
			//conciliacionDet.setBdSaldoInicial();
			//conciliacionDet.setIntPersEmpresaCheckConciliacion();
			//conciliacionDet.setIntPersPersonaCheckConciliacion();
			//conciliacionDet.setTsFechaCheck(conciliacionDet.();
		
		} catch (Exception e) {
			log.error("Error en convertEgresoIngresoAConcilDet --> "+e);
		}
		return conciliacionDet;
	}
	
	
	
	public EgresoDetalle recuperarEgresoDetConcil (Egreso egreso){
	List<EgresoDetalle> lstEgresoDet = null;
	EgresoDetalle egresoDet = null;	
		try {
			//lstEgresoDet = boEgreso.getPorEgreso(egreso);
			if(lstEgresoDet != null && lstEgresoDet.size() >0){
					egresoDet = new EgresoDetalle();
					egresoDet = lstEgresoDet.get(0);
			}
		} catch (Exception e) {
			log.error("Error en convertEgresoIngresoAConcilDet --> "+e);
		}
		return egresoDet;
	}
	
	public IngresoDetalle recuperarIngresoDetConcil (Ingreso ingreso){
	List<IngresoDetalle> lstIngresoDet = null;
	IngresoDetalle ingresoDet = null;	
		try {
			//lstIngresoDet = boIngreso.getPorIngreso(ingreso);
			if(lstIngresoDet != null && lstIngresoDet.size() >0){
					ingresoDet = new IngresoDetalle();
					ingresoDet = lstIngresoDet.get(0);
			}
		} catch (Exception e) {
			log.error("Error en convertEgresoIngresoAConcilDet --> "+e);
		}
		return ingresoDet;
	}
	

	public void anularConciliacion(ConciliacionComp pConciliacionCompAnul) throws BusinessException{
		List<Conciliacion> lstConciliacion = null;
		try{
			  lstConciliacion = boConciliacion.getListFilter(pConciliacionCompAnul);
			  
			  if(lstConciliacion != null && lstConciliacion.size() > 0){
				  for(Conciliacion concil : lstConciliacion){
						List<ConciliacionDetalle> lstConcilDetalle = null;
						Integer intNroConDet = 0;
						
						// 1. actualizar checks a 1 y concil a 0					
						lstConcilDetalle =  boConciliacionDet.getPorConciliacion(concil.getId());
						if(lstConcilDetalle != null && lstConcilDetalle.size() > 0){
							List<ConciliacionDetalle> lstTemp = new ArrayList<ConciliacionDetalle>();
							intNroConDet = lstConcilDetalle.size();
							
							for (ConciliacionDetalle concilDet :lstConcilDetalle){
								concilDet.setIntIndicadorCheck(new Integer("1"));
								concilDet.setIntIndicadorConci(new Integer("0"));	
								lstTemp.add(concilDet);
							}
							concil.setListaConciliacionDetalle(new ArrayList<ConciliacionDetalle>());
							concil.getListaConciliacionDetalle().addAll(lstTemp);
							
						}
						
						// 2. Actualizar cabecera
						
						concil.setIntRegistrosConciliados(new Integer("0"));
						concil.setIntRegistrosNoConciliados(intNroConDet);							
						//concil.setIntPersEmpresaAnula(pConciliacionCompAnul.getUsuario().getId().getIntPersEmpresa());	
						//concil.setIntPersPersonaAnula(pConciliacionCompAnul.getUsuario().getPersona().getId());								
						concil.setTsFechaAnula(MyUtil.obtenerFechaActual());
						//concil.setStrObservaciónAnula(pConciliacionCompAnul.getStrObservacionAnul());
						concil.setIntParaEstado(new Integer ("3")); // estado anulado

						// 3. Grabar concilicacion
						grabarConciliacion(concil);
				  }
			  }
		}catch(BusinessException e){
			log.error("Error - BusinessException - en anularConciliacion ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en anularConciliacion ---> "+e);
			throw new BusinessException(e);
		}
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Conciliacion getConciliacionEdit(ConciliacionId id){
		Conciliacion conciliacion = null;
		try {
			conciliacion = boConciliacion.getPorPk(id);
			if(conciliacion != null){
				List<ConciliacionDetalle> lstDetalle = null;
				lstDetalle = boConciliacionDet.getPorConciliacion(id);
				if(lstDetalle == null && lstDetalle.size()>0){
					conciliacion.setListaConciliacionDetalle(lstDetalle);
					//for (ConciliacionDetalle conciliacionDetalle : lstDetalle) {
						
						
						
						//convertEgresoIngresoAConcilDet(conciliacionDet)
					//}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return conciliacion;
		
		
	}
	
	/*
		public void calcularTablaResumen(){
		BigDecimal bdTotalConciliacion;
		BigDecimal bdResumenPorConciliar;
		
		try{
			bdTotalConciliacion= BigDecimal.ZERO;
			bdResumenPorConciliar= BigDecimal.ZERO;
			
			limpiarTablaResumen();
			
			concilResumen.setBdResumenSaldoAnterior(conciliacionNuevo.getBdMontoSaldoInicial() == null ? BigDecimal.ZERO :conciliacionNuevo.getBdMontoSaldoInicial());
		
			if(conciliacionNuevo.getListaConciliacionDetalle() != null || conciliacionNuevo.getListaConciliacionDetalle().size() == 0){
				concilResumen.setIntResumenNroMov(conciliacionNuevo.getListaConciliacionDetalle().size());
				
				//bdTotalConciliacion
				for(ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalle()){
					bdTotalConciliacion = bdTotalConciliacion.add((detalle.getBdMontoDebe() == null ? ( detalle.getBdMontoHaber() == null ? BigDecimal.ZERO : detalle.getBdMontoHaber() ): detalle.getBdMontoDebe()));					
				}

				// bdResumenPorConciliar
				for(ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalle()){
					if(detalle.getIntIndicadorCheck() == null || detalle.getIntIndicadorCheck() == 0){
						if(detalle.getIngreso() == null){
							bdResumenPorConciliar = bdResumenPorConciliar.add(detalle.getBdMontoDebe()==null?BigDecimal.ZERO:detalle.getBdMontoDebe());
						}else{
							bdResumenPorConciliar = bdResumenPorConciliar.add(detalle.getBdMontoHaber()==null?BigDecimal.ZERO:detalle.getBdMontoHaber());
						}
					}
				}
				
				// bdResumenSaldoConciliacion
				concilResumen.setBdResumenSaldoConciliacion(bdTotalConciliacion.subtract(bdResumenPorConciliar).setScale(2, RoundingMode.HALF_UP));
				
				// bdResumenDebe / bdResumenHaber
				BigDecimal bdResumenDebe = BigDecimal.ZERO;
				BigDecimal bdResumenHaber = BigDecimal.ZERO;
				for(ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalle()){
					bdResumenDebe  = bdResumenDebe.add(detalle.getBdMontoDebe()!= null ? detalle.getBdMontoDebe() : BigDecimal.ZERO);
					bdResumenHaber = bdResumenHaber.add(detalle.getBdMontoHaber()!= null ? detalle.getBdMontoHaber() : BigDecimal.ZERO);
				}

				//bdResumenSaldoCaja
				concilResumen.setBdResumenSaldoCaja(concilResumen.getBdResumenSaldoAnterior().add(bdResumenDebe).subtract(bdResumenHaber).setScale(2, RoundingMode.HALF_UP));
				lstResumen.add(concilResumen);
			
			}		
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	
	
	*/
	
	
	/*	
	public List<ConciliacionDetalle> getConciliacionConDetalleValidado(Conciliacion conciliacion, List<ConciliacionDetalle> lstDetalleOriginal) throws BusinessException{
		List<ConciliacionDetalle> lstConcilDetEgreTemp = null;
		List<ConciliacionDetalle> lstConcilDetIngreTemp = null;
		List<ConciliacionDetalle> listaConciliacionDetalle = null;
		Ingreso ingresoFiltro = null;
		Egreso egresoFiltro = null;

		try{	
			listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
			listaConciliacionDetalle.addAll(lstDetalleOriginal);
			
			// Metodos que recupera
			ingresoFiltro = new Ingreso();
			ingresoFiltro.setId(new IngresoId());
			egresoFiltro = new Egreso();
			egresoFiltro.setId(new EgresoId());
			// por terminar
			ingresoFiltro.set(conciliacion);
			egresoFiltro.set(conciliacion)
			
			List<Ingreso> listaIngreso = boIngreso.getListaBuscarAnteriores(ingresoFiltro);
			List<Egreso> listaEgreso = boEgreso.getListaBuscarAnteriores(egresoFiltro);

			if(listaIngreso != null && listaIngreso.size() >0){
			lstConcilDetIngreTemp = new ArrayList<ConciliacionDetalle>;
				for(ConciliacionDetalle concilDet :lstDetalleOriginal){
					for(Ingreso ingreso : listaIngreso){
						
						if(!(concilDet.getIntPersEmpresaIngreso().equals(ingreso.getId().getIntIdEmpresa()) 
							&& concilDet.getIntItemIngresoGeneral()equals(ingreso.getId().getIntIdIngresoGeneral()))){
							
								ConciliacionDetalle conciliacionDet = new ConciliacionDetalle();
								conciliacionDet.setIngreso(ingreso);
								lstConcilDetIngreTemp.add(conciliacionDet);
						}
					}
				}
				listaConciliacionDetalle.addAll(lstConcilDetIngreTemp);
			}
			
			if(listaEgreso != null && listaEgreso.size() >0){
			lstConcilDetEgreTemp = new ArrayList<ConciliacionDetalle>;
				for(ConciliacionDetalle concilDet :lstDetalleOriginal){
					for(Egreso egreso : listaEgreso){
						if(!(concilDet.getIntPersEmpresaEgreso().equals(egreso.getId().getIntPersEmpresaEgreso()) 
							&& concilDet.getIntItemEgresoGeneral().equals(egreso.getId().getIntItemEgresoGeneral()))){
								ConciliacionDetalle conciliacionDet = new ConciliacionDetalle();
								conciliacionDet.setEgreso(egreso);
								lstConcilDetEgreTemp.add(conciliacionDet);
						}
					}
				}
				listaConciliacionDetalle.addAll(lstConcilDetEgreTemp);
			}
			
			
		}catch(BusinessException e){
			log.error("Error - BusinessException - en getConciliacionConDetalleValidado ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getConciliacionConDetalleValidado ---> "+e);
			throw new BusinessException(e);
		}
		return listaConciliacionDetalle;
	}
	
	

	public Conciliacion cerrarConciliacion(Conciliacion pConciliacion) throws BusinessException{
		Conciliacion conciliacion = null;
		List<ConciliacionDetalle> listaConciliacionDetalleTemp = null;
		List<ConciliacionDetalle> listaConciliacionDetalle = null;
		
		try{
			// SETERAR ESTADOD E COCNILIADO
			//pConciliacion.setEstado(ESTADOCONCILIADO 2);		
			conciliacion = boConciliacion.grabar(pConciliacion);
			
			listaConciliacionDetalleTemp = pConciliacion.getListaConciliacionDetalle();
			
			if(listaConciliacionDetalleTemp!=null && !listaConciliacionDetalleTemp.isEmpty()){
				listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
				for( ConciliacionDetalle conciliacionDet : listaConciliacionDetalleTemp){
					// Igualamos el check concil segun check
					if(conciliacionDet.getIntIndicadorCheck() == null || conciliacionDet.getIntIndicadorCheck() == 0){
						conciliacionDet.setIntIndicadorConci(0);
					}else{
						conciliacionDet.setIntIndicadorConci(1);
					}
					conciliacionDet.setIntPersEmpresaCheckConciliacion(2);
					conciliacionDet.setIntPersPersonaCheckConciliacion(2);
					conciliacionDet.setTsFechaCheck(MyUtil.obtenerFechaActual());
					conciliacionDet = convertEgresoIngresoAConcilDet(conciliacionDet);
					listaConciliacionDetalle.add(conciliacionDet);
				}	
			}
			if(listaConciliacionDetalle!=null && !listaConciliacionDetalle.isEmpty()){
				grabarListaDinamicaConciliacionDetalle(listaConciliacionDetalle, conciliacion.getId());
			}
			
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarConciliacion ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarConciliacion ---> "+e);
			throw new BusinessException(e);
		}
		return conciliacion;
	}
	*/
	
	


	
}
