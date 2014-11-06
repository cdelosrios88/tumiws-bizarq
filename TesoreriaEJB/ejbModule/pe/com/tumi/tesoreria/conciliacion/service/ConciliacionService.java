/**
* Resumen.
* Objeto: ConciliacionFacade
* Descripción:  Facade principal del proceso de conciliacion bancaria.
* Fecha de Creación: 18/10/2014.
* Requerimiento de Creación: REQ14-006
* Autor: Bizarq
*/
package pe.com.tumi.tesoreria.conciliacion.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
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

			ingresoFiltro.getId().setIntIdEmpresa((conciliacion.getBancoCuenta().getId().getIntEmpresaPk()));
			ingresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			ingresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			ingresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			//ingresoFiltro.setIntParaDocumentoGeneral(302);
			//ingresoFiltro.setIntItemBancoFondo(2);
			//ingresoFiltro.setIntItemBancoCuenta(6);			
			//ingresoFiltro.setDtDechaDesde(new Date(conciliacion.getTsFechaConciliacion().getTime()));
			ingresoFiltro.setDtDechaHasta(new Date(conciliacion.getTsFechaConciliacion().getTime()));
			List<Ingreso> listaIngreso = boIngreso.getListaParaBuscar(ingresoFiltro);
			
			if(listaIngreso != null && listaIngreso.size() >0){
				for(Ingreso ingreso : listaIngreso){
					ConciliacionDetalle conciliacionDet = new ConciliacionDetalle();
					//conciliacionDet.setId(new ConciliacionDetalleId());
					conciliacionDet.setIngreso(ingreso);
					listaConciliacionDetalle.add(conciliacionDet);
				}
			}
			
			Egreso egresoFiltro = new Egreso();
			egresoFiltro.getId().setIntPersEmpresaEgreso(conciliacion.getBancoCuenta().getId().getIntEmpresaPk());
			egresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			egresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			egresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			//egresoFiltro.setIntParaDocumentoGeneral(301);
			//egresoFiltro.setIntItemBancoFondo(2);
			//egresoFiltro.setIntItemBancoCuenta(4);
			List<Egreso> listaEgreso = boEgreso.getListaPorBuscar(egresoFiltro, null, (new Date(conciliacion.getTsFechaConciliacion().getTime())));
			//List<Egreso> listaEgreso = null;

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
			
			Egreso egresoFiltro = new Egreso();
			egresoFiltro.getId().setIntPersEmpresaEgreso(conciliacion.getBancoCuenta().getId().getIntEmpresaPk());
			egresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			egresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			egresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			List<Egreso> listaEgreso = boEgreso.getListaPorBuscar(egresoFiltro, null, null);
			//List<Egreso> listaEgreso = null;
			
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
			if(pConciliacion.getId()== null || pConciliacion.getId().getIntItemConciliacion()==null){
				conciliacion = boConciliacion.grabar(pConciliacion);
			}else{
				conciliacion = boConciliacion.modificar(pConciliacion);
			}
			
			
			listaConciliacionDetalleTemp = pConciliacion.getListaConciliacionDetalle();
			
			if(listaConciliacionDetalleTemp!=null && !listaConciliacionDetalleTemp.isEmpty()){
				grabarListaDinamicaConciliacionDetalle(listaConciliacionDetalleTemp, conciliacion.getId());
			}
			/*if(listaConciliacionDetalleTemp!=null && !listaConciliacionDetalleTemp.isEmpty()){
				listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
				for( ConciliacionDetalle conciliacionDet : listaConciliacionDetalleTemp){
					conciliacionDet = convertEgresoIngresoAConcilDet(conciliacionDet);
					listaConciliacionDetalle.add(conciliacionDet);
				}	
			}
			if(listaConciliacionDetalle!=null && !listaConciliacionDetalle.isEmpty()){
				grabarListaDinamicaConciliacionDetalle(listaConciliacionDetalle, conciliacion.getId());
			}
			*/
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
	 * @param pConciliacion
	 * @return
	 * @throws BusinessException
	 */
	public Conciliacion cerrarConciliacion(Conciliacion pConciliacion) throws BusinessException{
		Conciliacion conciliacion = null;
		List<ConciliacionDetalle> listaConciliacionDetalleTemp = null;
		List<ConciliacionDetalle> listaConciliacionDetalle = null;
		
		try{
			// SETERAR ESTADOD E COCNILIADO
			pConciliacion.setIntParaEstado(2);
			pConciliacion.setIntPersPersonaConcilia(2);
			pConciliacion.setIntPersEmpresaConcilia(2);
			
			
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
				conciliacionDetalle = checkConciliacionDetalle(conciliacionDetalle);
				//conciliacionDetalle = convertEgresoIngresoAConcilDet(conciliacionDetalle);
				
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
	 * 
	 * @param conciliacionDetalle
	 * @return
	 * @throws BusinessException
	 */
	public ConciliacionDetalle checkConciliacionDetalle(ConciliacionDetalle conciliacionDetalle) throws BusinessException{
		try {

				if(conciliacionDetalle.getBlIndicadorCheck() != null &&
					conciliacionDetalle.getBlIndicadorCheck().equals(Boolean.TRUE)){
					conciliacionDetalle.setIntIndicadorCheck(new Integer("1"));			
										
				}else{
					conciliacionDetalle.setIntIndicadorCheck(new Integer("0"));						
				}
				
				if(conciliacionDetalle.getBlIndicadorConci() != null &&
						conciliacionDetalle.getBlIndicadorConci().equals(Boolean.TRUE)){
					
						conciliacionDetalle.setIntIndicadorConci(new Integer("1"));	
					
				}else{
					conciliacionDetalle.setIntIndicadorConci(new Integer("0"));	
				}	
				
			
		} catch(Exception e){
			log.error("Error - Exception - en checkConciliacionDetalle ---> "+e);
			throw new BusinessException(e);
		}
		return conciliacionDetalle;
	}
	
	/**
	 * 
	 * @param conciliacionDetalle
	 * @return
	 * @throws BusinessException
	 */
	public ConciliacionDetalle checkDetalleConciliacion(ConciliacionDetalle conciliacionDetalle) throws BusinessException{
		try {

				if(conciliacionDetalle.getIntIndicadorCheck() == 1 ){
					conciliacionDetalle.setBlIndicadorCheck(Boolean.TRUE);			
										
				}else{
					conciliacionDetalle.setBlIndicadorCheck(Boolean.FALSE);						
				}
				
				if(conciliacionDetalle.getIntIndicadorConci() == 1){
					
						conciliacionDetalle.setBlIndicadorConci(Boolean.TRUE);	
					
				}else{
					conciliacionDetalle.setBlIndicadorConci(Boolean.FALSE);
				}	
				
			
		} catch(Exception e){
			log.error("Error - Exception - en checkConciliacionDetalle ---> "+e);
			throw new BusinessException(e);
		}
		return conciliacionDetalle;
	}
	
	/**
	 * 
	 * @param conciliacionDet
	 * @return
	 */
	public ConciliacionDetalle convertEgresoIngresoAConcilDet(ConciliacionDetalle conciliacionDet){
		try {
			if(conciliacionDet.getEgreso() != null){
				conciliacionDet.setIntPersEmpresaEgreso(conciliacionDet.getEgreso().getId().getIntPersEmpresaEgreso());
				conciliacionDet.setIntItemEgresoGeneral(conciliacionDet.getEgreso().getId().getIntItemEgresoGeneral());
				conciliacionDet.setBdMontoDebe(null);
				conciliacionDet.setBdMontoHaber(conciliacionDet.getEgreso().getBdMontoTotal());
				conciliacionDet.setIntNumeroOperacion((conciliacionDet.getEgreso().getIntNumeroPlanilla() == null) ? ((conciliacionDet.getEgreso().getIntNumeroCheque() == null) ? ((conciliacionDet.getEgreso().getIntNumeroTransferencia() == null) ? conciliacionDet.getEgreso().getIntNumeroTransferencia(): 0 ): conciliacionDet.getEgreso().getIntNumeroCheque()) : conciliacionDet.getEgreso().getIntNumeroPlanilla());
			
			} else if (conciliacionDet.getIngreso() != null){	
				conciliacionDet.setIntPersEmpresaIngreso(conciliacionDet.getIngreso().getId().getIntIdEmpresa());
				conciliacionDet.setIntItemIngresoGeneral(conciliacionDet.getIngreso().getId().getIntIdIngresoGeneral());
				conciliacionDet.setBdMontoDebe(conciliacionDet.getIngreso().getBdMontoTotal());
				conciliacionDet.setBdMontoHaber(null); 
				conciliacionDet.setIntNumeroOperacion( new Integer(conciliacionDet.getIngreso().getStrNumeroOperacion()));
			}

		
		} catch (Exception e) {
			log.error("Error en convertEgresoIngresoAConcilDet --> "+e);
		}
		return conciliacionDet;
	}
	
	
	/**
	 * 
	 * @param egreso
	 * @return
	 */
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
	
	/**
	 * 
	 * @param ingreso
	 * @return
	 */
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
	
	/**
	 * 
	 * @param pConciliacionCompAnul
	 * @throws BusinessException
	 */
	public void anularConciliacion(ConciliacionComp pConciliacionCompAnul) throws BusinessException{
		List<Conciliacion> lstConciliacion = null;
		try{

			//pConciliacionCompAnul.getConciliacion().getBancoCuenta().getId().setIntEmpresaPk(2);
			//pConciliacionCompAnul.getConciliacion().getBancoCuenta().getId().setIntItembancocuenta(6);
			//pConciliacionCompAnul.getConciliacion().getBancoCuenta().getId().setIntItembancofondo(2);
			
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
						concil.setIntPersEmpresaAnula(pConciliacionCompAnul.getConciliacion().getUsuario().getPerfil().getId().getIntPersEmpresaPk());	
						concil.setIntPersPersonaAnula(pConciliacionCompAnul.getConciliacion().getUsuario().getIntPersPersonaPk());								
						concil.setTsFechaAnula(MyUtil.obtenerFechaActual());
						concil.setStrObservaciónAnula(pConciliacionCompAnul.getStrObservacionAnula());
						concil.setIntParaEstado(Constante.INT_EST_CONCILIACION_ANULADO);
						
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
	 * @param pConciliacion
	 * @throws BusinessException
	 */
	public void grabarConciliacionDiaria(Conciliacion pConciliacion) throws BusinessException{
		List<ConciliacionDetalle> lstTemp = null;
		try{

			pConciliacion.setTsFechaConcilia(MyUtil.obtenerFechaActual());
			pConciliacion.setIntPersEmpresaAnula(pConciliacion.getUsuario().getPerfil().getId().getIntPersEmpresaPk());	
			pConciliacion.setIntPersPersonaAnula(pConciliacion.getUsuario().getIntPersPersonaPk());	
			pConciliacion.setIntParaEstado(Constante.INT_EST_CONCILIACION_CONCILIADO);
				  
			  if(pConciliacion.getListaConciliacionDetalle() != null 
				&& pConciliacion.getListaConciliacionDetalle().size() > 0){
				  lstTemp = new ArrayList<ConciliacionDetalle>();
				  for(ConciliacionDetalle detalle : pConciliacion.getListaConciliacionDetalle()){
						if(detalle.getBlIndicadorCheck().equals(Boolean.TRUE) || 
							detalle.getIntIndicadorCheck() == 1){
							detalle.setIntIndicadorConci(1);
							detalle.setBlIndicadorConci(Boolean.TRUE);
						}
						lstTemp.add(detalle);
					}
				  
				  pConciliacion.getListaConciliacionDetalle().clear();
				  pConciliacion.getListaConciliacionDetalle().addAll(lstTemp);
				  }

			// Grabar concilicacion
				grabarConciliacion(pConciliacion);
			  
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarConciliacionDiaria ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarConciliacionDiaria ---> "+e);
			throw new BusinessException(e);
		}
	}
	

	/**
	 * 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public Conciliacion getConciliacionEdit(ConciliacionId pId) throws BusinessException{
		Conciliacion conciliacion = null;
		List<ConciliacionDetalle> lstConcildet = null;
		List<ConciliacionDetalle> lstConcildetTemp = null;
		try {
			
			conciliacion = boConciliacion.getPorPk(pId);
			if(conciliacion != null){
				lstConcildet = boConciliacionDet.getPorConciliacion(pId);
				if(lstConcildet != null && lstConcildet.size()>0){
					conciliacion.setListaConciliacionDetalle(new ArrayList<ConciliacionDetalle>());
					lstConcildetTemp = new ArrayList<ConciliacionDetalle>();
					
					for (ConciliacionDetalle detalle : lstConcildet) {
						if(detalle.getIntItemEgresoGeneral()!= null 
								&& detalle.getIntPersEmpresaEgreso() != null){
						// 	private	Integer	intPersEmpresaEgreso;
						//	private	Integer	intItemEgresoGeneral;	
							EgresoId egresoId = new EgresoId();
							Egreso egreso = null;
							egresoId.setIntItemEgresoGeneral(detalle.getIntItemEgresoGeneral());
							egresoId.setIntPersEmpresaEgreso(detalle.getIntPersEmpresaEgreso());
							egreso = boEgreso.getPorPk(egresoId);
							if(egreso != null){
								detalle.setEgreso(egreso);
							}
						}
						if(detalle.getIntItemIngresoGeneral()!= null 
								&& detalle.getIntPersEmpresaIngreso() != null){
						// 	private	Integer	intPersEmpresaEgreso;
						//	private	Integer	intItemEgresoGeneral;	
							IngresoId ingresoId = new IngresoId();
							Ingreso ingreso = null;
							ingresoId.setIntIdIngresoGeneral(detalle.getIntItemIngresoGeneral());
							ingresoId.setIntIdEmpresa(detalle.getIntPersEmpresaIngreso());
							ingreso = boIngreso.getPorId(ingresoId);
							if(ingreso != null){
								detalle.setIngreso(ingreso);
							}
						}
						detalle = checkDetalleConciliacion(detalle);
						
						lstConcildetTemp.add(detalle);
					}
					
					conciliacion.getListaConciliacionDetalle().addAll(lstConcildetTemp) ;					
					
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return conciliacion;		
	}
		
	
	
	/*

	
	
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
	

	*/
	
	


	
}
