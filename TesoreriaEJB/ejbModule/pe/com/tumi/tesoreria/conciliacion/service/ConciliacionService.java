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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.tesoreria.banco.bo.BancocuentaBO;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.service.BancoFondoService;
import pe.com.tumi.tesoreria.egreso.bo.ConciliacionBO;
import pe.com.tumi.tesoreria.egreso.bo.ConciliacionDetalleBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoDetalleBO;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalleId;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionId;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.EgresoId;
import pe.com.tumi.tesoreria.egreso.domain.comp.ConciliacionComp;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoDetalleBO;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;


public class ConciliacionService {

	protected static Logger log = Logger.getLogger(pe.com.tumi.tesoreria.egreso.service.ConciliacionService.class);

	private ConciliacionBO boConciliacion = (ConciliacionBO)TumiFactory.get(ConciliacionBO.class);
	private IngresoBO boIngreso = (IngresoBO)TumiFactory.get(IngresoBO.class);
	private EgresoBO boEgreso = (EgresoBO)TumiFactory.get(EgresoBO.class);
	private IngresoDetalleBO boIngresoDet = (IngresoDetalleBO)TumiFactory.get(IngresoDetalleBO.class);
	private EgresoDetalleBO boEgresoDet = (EgresoDetalleBO)TumiFactory.get(EgresoDetalleBO.class);
	private ConciliacionDetalleBO boConciliacionDet = (ConciliacionDetalleBO)TumiFactory.get(ConciliacionDetalleBO.class);
	private BancocuentaBO boBancoCuenta = (BancocuentaBO)TumiFactory.get(BancocuentaBO.class);
	private BancoFondoService bancoFondoService = (BancoFondoService)TumiFactory.get(BancoFondoService.class);
	

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
	 * Recupera las Conciliaciones Detalle. Asociado al boton MOSTRAR DATOS
	 * @param conciliacion
	 * @return
	 * @throws BusinessException
	 */
	public List<ConciliacionDetalle> buscarRegistrosConciliacion(Conciliacion conciliacion)throws BusinessException{
		List<ConciliacionDetalle> listaConciliacionDetallePlus = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
		
		List<ConciliacionDetalle> listaConciliacionDetalle_1 = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalle_2 = new ArrayList<ConciliacionDetalle>();
		
		List<ConciliacionDetalle> listaConciliacionDetalleTemp = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalleResult = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalleFinal = new ArrayList<ConciliacionDetalle>();
		
		Conciliacion concilLast = null;
		Date dtSince = null;
		Date dtSincePlusOne = null;
		try{
			
			// verificamos la ultima conciliacion
			concilLast = boConciliacion.getLastConciliacionByCuenta(conciliacion);
			if(concilLast != null){
				dtSince = new Date(concilLast.getTsFechaConciliacion().getTime());
				dtSincePlusOne = sumarFechasDias(dtSince, 1);

				// Recuperamos las concilaiciones desde el dia de hoy hast un dia posterior a la ultima cocnilaicion
				listaConciliacionDetallePlus = getConciliacionDetallePorFechas(
						conciliacion.getBancoCuenta().getId().getIntEmpresaPk(), 
						conciliacion.getIntParaDocumentoGeneralFiltro(), 
						conciliacion.getBancoCuenta().getId().getIntItembancofondo(), 
						conciliacion.getBancoCuenta().getId().getIntItembancocuenta(), 
						dtSincePlusOne, 
						new Date( conciliacion.getTsFechaConciliacion().getTime()));
				
				
				//recuperampos el detalle de la ultima concilaicion
				listaConciliacionDetalle_1 = getConciliacionDetallePorFechas(
						conciliacion.getBancoCuenta().getId().getIntEmpresaPk(), 
						conciliacion.getIntParaDocumentoGeneralFiltro(), 
						conciliacion.getBancoCuenta().getId().getIntItembancofondo(), 
						conciliacion.getBancoCuenta().getId().getIntItembancocuenta(), 
						dtSince, 
						dtSince);
				
				// detalle de la ultima concilaicion
				listaConciliacionDetalle_2 = boConciliacionDet.getPorConciliacion(concilLast.getId());
				
				if(listaConciliacionDetalle_2 != null && listaConciliacionDetalle_2.size() > 0){
					
				//Recoremos las listas a fin de solo tomar las que aun estan en estado No concilaido
					if(listaConciliacionDetalle_1 != null && listaConciliacionDetalle_1.size()>0){
						for (ConciliacionDetalle concilDet1 : listaConciliacionDetalle_1) {
							concilDet1.setBlValid(Boolean.TRUE);
							for (ConciliacionDetalle concilDet2 : listaConciliacionDetalle_2) {
								if(concilDet1.getIngreso() == null && concilDet2.getIngreso() == null){
									if(concilDet1.getEgreso().getId().getIntItemEgresoGeneral().compareTo(concilDet2.getIntItemEgresoGeneral())==0
									   && concilDet1.getEgreso().getId().getIntPersEmpresaEgreso().compareTo(concilDet2.getIntPersEmpresaEgreso())==0
									   //&& concilDet1.getIntIndicadorConci() != null){
										){
									   //if(concilDet1.getIntIndicadorConci().compareTo(Constante.INT_ONE)==0)
										concilDet1.setBlValid( Boolean.FALSE);
									}									
								}
								else if(concilDet1.getEgreso() == null && concilDet2.getEgreso() == null){
									if(concilDet1.getIngreso().getId().getIntIdIngresoGeneral().compareTo(concilDet2.getIntItemIngresoGeneral())==0
										&& concilDet1.getIngreso().getId().getIntIdEmpresa().compareTo(concilDet2.getIntPersEmpresaIngreso())==0
										//&& concilDet1.getIntIndicadorConci() != null){
										){
										//if(concilDet1.getIntIndicadorConci().compareTo(Constante.INT_ONE)==0)										
										concilDet1.setBlValid( Boolean.FALSE);
									}
								}
							}
							listaConciliacionDetalleTemp.add(concilDet1);
						}
					}
					
					
					if(listaConciliacionDetalleTemp != null && listaConciliacionDetalleTemp.size() >0){
						for (ConciliacionDetalle concilaicionDet : listaConciliacionDetalleTemp) {
							if(concilaicionDet.getBlValid()){
								listaConciliacionDetalle.add(concilaicionDet);
							}
						}
					}
					
					listaConciliacionDetalleResult.addAll(listaConciliacionDetallePlus);
					listaConciliacionDetalleResult.addAll(listaConciliacionDetalle);	
				}
				
			}else{
				// Recuperamos las concilaiciones desde el inicio del tiempo hast el dia de hoy
				listaConciliacionDetalleResult = getConciliacionDetallePorFechas(
						conciliacion.getBancoCuenta().getId().getIntEmpresaPk(), 
						conciliacion.getIntParaDocumentoGeneralFiltro(), 
						conciliacion.getBancoCuenta().getId().getIntItembancofondo(), 
						conciliacion.getBancoCuenta().getId().getIntItembancocuenta(), 
						null, 
						new Date( conciliacion.getTsFechaConciliacion().getTime()));		
			}
			
			
			if(listaConciliacionDetalleResult != null && listaConciliacionDetalleResult.size()>0){
				listaConciliacionDetalleFinal = new ArrayList<ConciliacionDetalle>();
				for (ConciliacionDetalle conciliacionDetalle : listaConciliacionDetalleResult) {
					conciliacionDetalle.setStrDescripcionSucursalPaga(getSucursalPaga(conciliacionDetalle));
					listaConciliacionDetalleFinal.add(conciliacionDetalle);
				}
			}
			
		}catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaConciliacionDetalleFinal;
	}
	
	
	/**
	 * Recupera las Conciliaciones Detalle. Asociado al boton MOSTRAR DATOS
	 * @param conciliacion
	 * @return
	 * @throws BusinessException
	 */
	public List<ConciliacionDetalle> buscarRegistrosConciliacionEdicion(Conciliacion conciliacion)throws BusinessException{
		List<ConciliacionDetalle> listaConciliacionDetallePlus = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalle2 = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalle_1 = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalle_2 = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalleTemp = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalleReal = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalleRec = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalleTemp2 = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalleResult = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalleFinal = new ArrayList<ConciliacionDetalle>();
		
		Conciliacion concilLast = null;
		Date dtSince = null;
		Date dtSincePlusOne = null;
		try{
			System.out.println("XXX - VERIFICAMOS SI HAY ULTIMA CONCILIACION");
			// verificamos la ultima conciliacion
			concilLast = boConciliacion.getLastConciliacionByCuenta(conciliacion);
			if(concilLast != null){
				System.out.println("XXX - SI HAY ULTIMA CONCILIACION");
				dtSince = new Date(concilLast.getTsFechaConciliacion().getTime());
				dtSincePlusOne = sumarFechasDias(dtSince, 1);

				// Recuperamos las concilaiciones desde el dia de hoy hast un dia posterior a la ultima cocnilaicion
				listaConciliacionDetallePlus = getConciliacionDetallePorFechas(
						conciliacion.getBancoCuenta().getId().getIntEmpresaPk(), 
						conciliacion.getIntParaDocumentoGeneralFiltro(), 
						conciliacion.getBancoCuenta().getId().getIntItembancofondo(), 
						conciliacion.getBancoCuenta().getId().getIntItembancocuenta(), 
						dtSincePlusOne, 
						new Date( conciliacion.getTsFechaConciliacion().getTime()));
				
				
				//recuperampos el detalle de la ultima concilaicion
				listaConciliacionDetalle_1 = getConciliacionDetallePorFechas(
						conciliacion.getBancoCuenta().getId().getIntEmpresaPk(), 
						conciliacion.getIntParaDocumentoGeneralFiltro(), 
						conciliacion.getBancoCuenta().getId().getIntItembancofondo(), 
						conciliacion.getBancoCuenta().getId().getIntItembancocuenta(), 
						dtSince, 
						dtSince);
				
				// detalle de la ultima concilaicion
				listaConciliacionDetalle_2 = boConciliacionDet.getPorConciliacion(concilLast.getId());
				
				if(listaConciliacionDetalle_2 != null && listaConciliacionDetalle_2.size() > 0){
					
				//Recoremos las listas a fin de solo tomar las que aun estan en estado No concilaido
					for (ConciliacionDetalle concilDet1 : listaConciliacionDetalle_1) {
						concilDet1.setBlValid(Boolean.TRUE);
						for (ConciliacionDetalle concilDet2 : listaConciliacionDetalle_2) {
							if(concilDet1.getIngreso() == null && concilDet2.getIngreso() == null){
								if(concilDet1.getEgreso().getId().getIntItemEgresoGeneral().compareTo(concilDet2.getIntItemEgresoGeneral())==0
								   && concilDet1.getEgreso().getId().getIntPersEmpresaEgreso().compareTo(concilDet2.getIntPersEmpresaEgreso())==0
								   //&& concilDet1.getIntIndicadorConci()!= null){
									){
								   //if(concilDet1.getIntIndicadorConci().compareTo(Constante.INT_ONE)==0)
									concilDet1.setBlValid( Boolean.FALSE);
								}									
							}
							else if(concilDet1.getEgreso() == null && concilDet2.getEgreso() == null){
								if(concilDet1.getIngreso().getId().getIntIdIngresoGeneral().compareTo(concilDet2.getIntItemIngresoGeneral())==0
									&& concilDet1.getIngreso().getId().getIntIdEmpresa().compareTo(concilDet2.getIntPersEmpresaIngreso())==0
									//&& concilDet1.getIntIndicadorConci() != null){
									){
									//if(concilDet1.getIntIndicadorConci().compareTo(Constante.INT_ONE)==0)
									concilDet1.setBlValid( Boolean.FALSE);
								}
							}
						}
						listaConciliacionDetalleTemp.add(concilDet1);
					}

					for (ConciliacionDetalle concilaicionDet : listaConciliacionDetalleTemp) {
						if(concilaicionDet.getBlValid()){
							listaConciliacionDetalle.add(concilaicionDet);
						}
					}
					listaConciliacionDetalleRec.addAll(listaConciliacionDetallePlus);
					listaConciliacionDetalleRec.addAll(listaConciliacionDetalle);	
				}
				
			}else{
				System.out.println("XXX - NO HAY ULTIMA CONCILIACION");
				// Recuperamos las concilaiciones desde el inicio del tiempo hast el dia de hoy
				System.out.println("XXX - recuperamos listaConciliacionDetalleRec");
				listaConciliacionDetalleRec = getConciliacionDetallePorFechas(
						conciliacion.getBancoCuenta().getId().getIntEmpresaPk(), 
						conciliacion.getIntParaDocumentoGeneralFiltro(), 
						conciliacion.getBancoCuenta().getId().getIntItembancofondo(), 
						conciliacion.getBancoCuenta().getId().getIntItembancocuenta(), 
						null, 
						new Date( conciliacion.getTsFechaConciliacion().getTime()));
				System.out.println("XXX - listaConciliacionDetalleRec "+listaConciliacionDetalleRec.size());
			}
			
			
			// Se valida la lista recuperada VS. la lista actual de la concilaicion visulaizada
			listaConciliacionDetalleReal = conciliacion.getListaConciliacionDetalle();
			System.out.println("XXX - listaConciliacionDetalleReal "+listaConciliacionDetalleReal.size());

			if(listaConciliacionDetalleReal != null && listaConciliacionDetalleReal.size() > 0){
				//Recoremos las listas a fin de solo tomar las que aun estan en estado No concilaido
				if(listaConciliacionDetalleRec != null && listaConciliacionDetalleRec.size()> 0){
					
					for (ConciliacionDetalle concilDet1 : listaConciliacionDetalleRec) {
						
						for (ConciliacionDetalle concilDet2 : listaConciliacionDetalleReal) {
							if(concilDet1.getIngreso() == null &&  concilDet2.getIngreso() == null){
								System.out.println("comparando egresos");
								if(concilDet1.getEgreso().getId().getIntItemEgresoGeneral().compareTo(concilDet2.getIntItemEgresoGeneral())==0
								   && concilDet1.getEgreso().getId().getIntPersEmpresaEgreso().compareTo(concilDet2.getIntPersEmpresaEgreso())==0
								   //&& concilDet1.getIntIndicadorConci()!=null){
								   ){
									
									System.out.println("egreso invalido "+concilDet1.getEgreso().getId().getIntPersEmpresaEgreso()+"/"+concilDet1.getEgreso().getId().getIntItemEgresoGeneral());
									//if(concilDet1.getIntIndicadorConci().compareTo(Constante.INT_ONE)==0)
									concilDet1.setBlValid( Boolean.FALSE);
								}									
							}
							
							else if(concilDet1.getEgreso() == null &&  concilDet2.getEgreso() == null){
								System.out.println("comparando ingresos");
								if(concilDet1.getIngreso().getId().getIntIdIngresoGeneral().compareTo(concilDet2.getIntItemIngresoGeneral())==0
									&& concilDet1.getIngreso().getId().getIntIdEmpresa().compareTo(concilDet2.getIntPersEmpresaIngreso())==0
									//&& (concilDet1.getIntIndicadorConci() != null)){
									){
									System.out.println("ingreso invalido "+concilDet1.getIngreso().getId().getIntIdEmpresa()+"/"+concilDet1.getIngreso().getId().getIntIdIngresoGeneral());

									//if(concilDet1.getIntIndicadorConci().compareTo(Constante.INT_ONE)==0)
									concilDet1.setBlValid( Boolean.FALSE);
								}
							}
						}
						listaConciliacionDetalleTemp2.add(concilDet1);
					}
				}
				
				
				if(listaConciliacionDetalleTemp2 != null && listaConciliacionDetalleTemp2.size() > 0){
					for (ConciliacionDetalle concilaicionDet : listaConciliacionDetalleTemp2) {
						if(concilaicionDet != null){
							if(concilaicionDet.getBlValid()==null){
								listaConciliacionDetalle2.add(concilaicionDet);
							}
						}							
					}
				}
			
				System.out.println("lista a agregarse listaConciliacionDetalle2 "+ listaConciliacionDetalle2.size());
				listaConciliacionDetalleResult.addAll(listaConciliacionDetalle2);
				listaConciliacionDetalleResult.addAll(listaConciliacionDetalleReal);
				
			}else{
				listaConciliacionDetalleResult.addAll(listaConciliacionDetalleRec);
			}

			if(listaConciliacionDetalleResult != null && listaConciliacionDetalleResult.size()>0){
				listaConciliacionDetalleFinal = new ArrayList<ConciliacionDetalle>();
				for (ConciliacionDetalle conciliacionDetalle : listaConciliacionDetalleResult) {
					conciliacionDetalle.setStrDescripcionSucursalPaga(getSucursalPaga(conciliacionDetalle));
					listaConciliacionDetalleFinal.add(conciliacionDetalle);
				}
			}

		}catch (Exception e) {
			throw new BusinessException(e);
		}
		System.out.println("lista listaConciliacionDetalleResult "+listaConciliacionDetalleResult.size());
		return listaConciliacionDetalleFinal;
	}
	
	
	/**
	 * 
	 * @param detalle
	 * @return
	 * @throws BusinessException
	 */
	public String getSucursalPaga(ConciliacionDetalle detalle) throws BusinessException{
		String strSucursalPagaConcat = "";
		EmpresaFacadeRemote empresaFacade = null;
		List<Sucursal> listaSucursal;

		try {
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			
			if(listaSucursal != null && listaSucursal.size()>0 ){
				if(detalle.getIngreso() == null && detalle.getEgreso() != null){
					List<EgresoDetalle> lstEgresoDet=null;
					lstEgresoDet = boEgresoDet.getPorEgreso(detalle.getEgreso());
					if(lstEgresoDet != null && lstEgresoDet.size()>0){
						for (EgresoDetalle egresoDetalle : lstEgresoDet) {
							for (Sucursal sucursal : listaSucursal) {
								if(sucursal.getId().getIntIdSucursal().compareTo(egresoDetalle.getIntSucuIdSucursalEgreso())==0){
									strSucursalPagaConcat = strSucursalPagaConcat + " " + sucursal.getJuridica().getStrRazonSocial()+".";
								}
							}	
						}
					}
					
				}else if(detalle.getEgreso() == null && detalle.getIngreso() != null){
					List<IngresoDetalle> lstIngresoDet=null;
					lstIngresoDet = boIngresoDet.getPorIngreso(detalle.getIngreso());
					if(lstIngresoDet != null && lstIngresoDet.size()>0){
						for (IngresoDetalle ingresoDetalle : lstIngresoDet) {
							for (Sucursal sucursal : listaSucursal) {
								if(sucursal.getId().getIntIdSucursal().compareTo(ingresoDetalle.getIntSucuIdSucursalIn())==0){
									strSucursalPagaConcat = strSucursalPagaConcat + " " + sucursal.getJuridica().getStrRazonSocial();
								}
							}	
						}
					}
				}
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return strSucursalPagaConcat;
	}
	
	
	/**
	 * Recupera Conciliacion Detalle, desde Egreso e Ingreso en base a fechas de Inicio ,Fin y Cuenta Bancaria
	 * @param intBcoCtaEmpresaPk
	 * @param intParaDocumentoGeneral
	 * @param intItemBancoFondo
	 * @param intItemBancoCuenta
	 * @param conciliacion
	 * @param dtInicio
	 * @param dtFin
	 * @return List<ConciliacionDetalle>
	 * @throws BusinessException
	 */
	public List<ConciliacionDetalle> getConciliacionDetallePorFechas(Integer intBcoCtaEmpresaPk, Integer intParaDocumentoGeneral,
			Integer intItemBancoFondo, Integer intItemBancoCuenta, Date dtInicio, Date dtFin)throws BusinessException{
		List<ConciliacionDetalle> listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
		List<ConciliacionDetalle> listaConciliacionDetalleFin = new ArrayList<ConciliacionDetalle>();
		//Conciliacion concilLast = null;
		//Date dtSince = null;
		//Date dtSincePlusOne = null;
		//Boolean blnExisteConcil = Boolean.FALSE;
		try{
		
			Ingreso ingresoFiltro = new Ingreso();

			ingresoFiltro.getId().setIntIdEmpresa(intBcoCtaEmpresaPk);
			ingresoFiltro.setIntParaDocumentoGeneral(intParaDocumentoGeneral);
			ingresoFiltro.setIntItemBancoFondo(intItemBancoFondo);
			ingresoFiltro.setIntItemBancoCuenta(intItemBancoCuenta);
			//PRUEBAS
			//ingresoFiltro.setIntParaDocumentoGeneral(302);
			//ingresoFiltro.setIntItemBancoFondo(2);
			//ingresoFiltro.setIntItemBancoCuenta(6);
			//
			ingresoFiltro.setDtDechaDesde(dtInicio);
			ingresoFiltro.setDtDechaHasta(dtFin);
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
			egresoFiltro.getId().setIntPersEmpresaEgreso(intBcoCtaEmpresaPk);
			egresoFiltro.setIntParaDocumentoGeneral(intParaDocumentoGeneral);
			egresoFiltro.setIntItemBancoFondo(intItemBancoFondo);
			egresoFiltro.setIntItemBancoCuenta(intItemBancoCuenta);
			//PRUEBAS
			//egresoFiltro.setIntParaDocumentoGeneral(301);
			//egresoFiltro.setIntItemBancoFondo(2);
			//egresoFiltro.setIntItemBancoCuenta(4);
			//
			List<Egreso> listaEgreso = boEgreso.getListaPorBuscar(egresoFiltro, dtInicio, dtFin);

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
	
	
	/*
	public List<ConciliacionDetalle> buscar(Conciliacion conciliacion)throws BusinessException{
		List<ConciliacionDetalle> listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
		try{

			
			
			
			
		}catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaConciliacionDetalle;
	}
	*/
	
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
			pConciliacion.setIntParaEstado(Constante.INT_EST_CONCILIACION_CONCILIADO);			
			pConciliacion.setIntPersPersonaConcilia(pConciliacion.getUsuario().getIntPersPersonaPk());
			pConciliacion.setIntPersEmpresaConcilia(pConciliacion.getUsuario().getPerfil().getId().getIntPersEmpresaPk());
			
			
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
					conciliacionDet.setIntPersEmpresaCheckConciliacion(pConciliacion.getUsuario().getPerfil().getId().getIntPersEmpresaPk());
					conciliacionDet.setIntPersPersonaCheckConciliacion(pConciliacion.getUsuario().getIntPersPersonaPk());	
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

				if(conciliacionDetalle.getIntIndicadorCheck() != null && conciliacionDetalle.getIntIndicadorCheck() == 1 ){
					conciliacionDetalle.setBlIndicadorCheck(Boolean.TRUE);			
										
				}else{
					conciliacionDetalle.setBlIndicadorCheck(Boolean.FALSE);						
				}
				
				if(conciliacionDetalle.getIntIndicadorConci() != null && conciliacionDetalle.getIntIndicadorConci() == 1){
					
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

			  lstConciliacion = boConciliacion.getListFilter(pConciliacionCompAnul);
			  
			  if(lstConciliacion != null && lstConciliacion.size() > 0){
				  for(Conciliacion concil : lstConciliacion){
					  if(concil.getIntParaEstado().compareTo(Constante.INT_EST_CONCILIACION_REGISTRADO)==0){
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
			pConciliacion.setIntPersEmpresaConcilia(pConciliacion.getUsuario().getPerfil().getId().getIntPersEmpresaPk());	
			pConciliacion.setIntPersPersonaConcilia(pConciliacion.getUsuario().getIntPersPersonaPk());	
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
				conciliacion.setListaConciliacionDetalle(new ArrayList<ConciliacionDetalle>());
				conciliacion.setListaConciliacionDetalleVisual(new ArrayList<ConciliacionDetalle>());
				
				lstConcildet = boConciliacionDet.getPorConciliacion(pId);
				if(lstConcildet != null && lstConcildet.size()>0){
					conciliacion.setListaConciliacionDetalle(new ArrayList<ConciliacionDetalle>());
					lstConcildetTemp = new ArrayList<ConciliacionDetalle>();
					
					for (ConciliacionDetalle detalle : lstConcildet) {
						if(detalle.getIntItemEgresoGeneral()!= null 
								&& detalle.getIntPersEmpresaEgreso() != null){
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
						detalle.setStrDescripcionSucursalPaga(getSucursalPaga(detalle));
						lstConcildetTemp.add(detalle);
					}
					
					conciliacion.getListaConciliacionDetalle().addAll(lstConcildetTemp) ;
					conciliacion.getListaConciliacionDetalleVisual().addAll(lstConcildetTemp);
					
				}
				conciliacion.setBancoCuenta(bancoFondoService.getBancoCuentaByConciliacion(conciliacion));
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return conciliacion;		
	}
	
/**
 * 	
 * @param dtFecha
 * @param dias
 * @return
 */
   public static Date sumarFechasDias(Date dtFecha, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(dtFecha.getTime());
        cal.add(Calendar.DATE, dias);
        return new Date(cal.getTimeInMillis());
    }
	   
	
}
