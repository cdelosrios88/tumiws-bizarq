package pe.com.tumi.tesoreria.egreso.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;
import pe.com.tumi.tesoreria.banco.domain.BancocuentachequeId;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.banco.service.AccesoService;
import pe.com.tumi.tesoreria.cierre.fdoFijo.domain.ControlFondoFijoAnula;
import pe.com.tumi.tesoreria.cierre.fdoFijo.domain.ControlFondoFijoAnulaId;
import pe.com.tumi.tesoreria.egreso.bo.ControlFondoFijoBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoDetalleBO;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.EgresoId;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraDocumentoBO;
import pe.com.tumi.tesoreria.logistica.bo.RequisicionBO;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionId;

public class EgresoService {

	protected static Logger log = Logger.getLogger(EgresoService.class);
	
	ControlFondoFijoBO boControlFondosFijos = (ControlFondoFijoBO)TumiFactory.get(ControlFondoFijoBO.class);
	EgresoBO boEgreso = (EgresoBO)TumiFactory.get(EgresoBO.class);
	EgresoDetalleBO boEgresoDetalle = (EgresoDetalleBO)TumiFactory.get(EgresoDetalleBO.class);
	AccesoService accesoService = (AccesoService)TumiFactory.get(AccesoService.class);
	RequisicionBO boRequisicion = (RequisicionBO)TumiFactory.get(RequisicionBO.class);
	OrdenCompraDocumentoBO boOrdenCompraDocumento = (OrdenCompraDocumentoBO)TumiFactory.get(OrdenCompraDocumentoBO.class);
	
	public Egreso grabarTransferencia(Egreso egreso) throws BusinessException{
//		ControlFondosFijos controlFondosFijos = null;
		try{
			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			
			
			LibroDiario libroDiario = egreso.getLibroDiario();
			
			for(LibroDiarioDetalle libroDiarioDetalle : libroDiario.getListaLibroDiarioDetalle()){
				log.info(libroDiarioDetalle);
			}
			libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
			
			egreso.setIntPersEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
			egreso.setIntContPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
			egreso.setIntContCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
			
			egreso = grabarEgreso(egreso);
			
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				egresoDetalle.getId().setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
				//Egde_NumeroDocumento_V : colocar el número del fondo nuevo, concatenando los campos : egre_itemperiodoegreso_n – egre_itemegreso_n de la tabla TES_EGRESOS
				egresoDetalle.setStrNumeroDocumento(egresoDetalle.getStrNumeroDocumento()+""+egreso.getIntItemEgreso());
//				egresoDetalle.setStrNumeroDocumento(egresoDetalle.getStrNumeroDocumento()+""+egreso.getId().getIntItemEgresoGeneral());
				boEgresoDetalle.grabar(egresoDetalle);
			}			
			
			egreso.setLibroDiario(libroDiario);
			procesarItems(egreso);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	public Egreso grabarTransferenciaTelecredito(Egreso egreso, List<Egreso> listaEgresoTelecredito) throws BusinessException{
		try{
			grabarTransferencia(egreso);
			egreso.getControlFondosFijos().setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
			egreso.getControlFondosFijos().setIntEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
			ControlFondosFijos controlFondosFijos = grabarControlFondosFijos(egreso.getControlFondosFijos());
			for(Egreso egresoTelecredito : listaEgresoTelecredito){
				egresoTelecredito.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
				egresoTelecredito.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
				egresoTelecredito.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
				egresoTelecredito.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
				//Autor: jchavez / Tarea: Se agrega valor al campo / Fecha: 22.09.2014
				egresoTelecredito.setIntNumeroPlanilla(egreso.getIntNumeroPlanilla());
				//Fin jchavez - 22.09.2014
				log.info(egresoTelecredito);
				boEgreso.modificar(egresoTelecredito);
			}
			EgresoDetalle egresoDetalle = egreso.getListaEgresoDetalle().get(0); 
			egresoDetalle.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
			boEgresoDetalle.modificar(egresoDetalle);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	public ControlFondosFijos grabarAperturaRendicion(Egreso egreso, ControlFondosFijos controlFondosFijosRendicion) throws BusinessException{
		ControlFondosFijos controlFondosFijosNuevo = null;
		try{
			 //grabamos como un CFF normal
			controlFondosFijosNuevo = grabarAperturaCheque(egreso);
			
			if(controlFondosFijosRendicion!=null){
				//actualizamos los montos del CFF de rendicion referenciado
				controlFondosFijosRendicion.setBdMontoUtilizado(
						controlFondosFijosRendicion.getBdMontoUtilizado().add(controlFondosFijosNuevo.getBdMontoGirado()));
				
				controlFondosFijosRendicion.setBdMontoSaldo(
						controlFondosFijosRendicion.getBdMontoSaldo().subtract(controlFondosFijosNuevo.getBdMontoGirado()));
				
				boControlFondosFijos.modificar(controlFondosFijosRendicion);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return controlFondosFijosNuevo;
	}
	
	public ControlFondosFijos grabarAperturaCheque(Egreso egreso) throws BusinessException{
		ControlFondosFijos controlFondosFijos = null;
		try{
			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);			
			
			controlFondosFijos = egreso.getControlFondosFijos();
			LibroDiario libroDiario = egreso.getLibroDiario();
			
			for(LibroDiarioDetalle libroDiarioDetalle : libroDiario.getListaLibroDiarioDetalle()){
				log.info(libroDiarioDetalle);
			}
			libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
			
			egreso.setIntPersEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
			egreso.setIntContPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
			egreso.setIntContCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
			
			egreso = grabarEgreso(egreso);
			
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				egresoDetalle.getId().setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
				//Egde_NumeroDocumento_V : colocar el número del fondo nuevo, concatenando los campos : egre_itemperiodoegreso_n – egre_itemegreso_n de la tabla TES_EGRESOS
				egresoDetalle.setStrNumeroDocumento(egresoDetalle.getStrNumeroDocumento()+""+egreso.getIntItemEgreso());
//				egresoDetalle.setStrNumeroDocumento(egresoDetalle.getStrNumeroDocumento()+""+egreso.getId().getIntItemEgresoGeneral());
				boEgresoDetalle.grabar(egresoDetalle);
			}
			
			egreso.setLibroDiario(libroDiario);
			
			if(controlFondosFijos != null){
				controlFondosFijos.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
				controlFondosFijos.setIntEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());				
				
				controlFondosFijos = grabarControlFondosFijos(controlFondosFijos);
				
				if(egreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
					controlFondosFijos.setEgreso(egreso);
					actualizarCheque(controlFondosFijos.getEgreso());
				}
				
				controlFondosFijos.setEgreso(egreso);				
				actualizarNumeroDocumentoLibroDiarioDetalle(controlFondosFijos);
			}else{
				procesarItems(egreso);
			}			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return controlFondosFijos;
	}
	
	private void actualizarNumeroDocumentoLibroDiarioDetalle(ControlFondosFijos controlFondosFijos) throws Exception{
		LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
		
		procesarItems(controlFondosFijos);
		for(LibroDiarioDetalle libroDiarioDetalle : controlFondosFijos.getEgreso().getLibroDiario().getListaLibroDiarioDetalle()){
			if(libroDiarioDetalle.getBdDebeSoles()!=null){
				libroDiarioDetalle.setStrNumeroDocumento(controlFondosFijos.getEgreso().getStrNumeroEgreso());
				libroDiarioFacade.modificarLibroDiarioDetalle(libroDiarioDetalle);
			}else if(libroDiarioDetalle.getBdHaberSoles()!=null){
				libroDiarioDetalle.setStrNumeroDocumento(controlFondosFijos.getStrNumeroApertura());
				libroDiarioFacade.modificarLibroDiarioDetalle(libroDiarioDetalle);
			}
		}
	}
	
	/**
	 * 
	 * @param controlFondosFijos
	 */
	private void procesarItems(ControlFondosFijos controlFondosFijos){
		try {
			controlFondosFijos.setStrNumeroApertura(
					obtenerPeriodoItem(	controlFondosFijos.getId().getIntItemPeriodoFondo(),
										controlFondosFijos.getId().getIntItemFondoFijo(),
										"000000"));
			if(controlFondosFijos.getEgreso().getId()!=null && controlFondosFijos.getEgreso().getId().getIntItemEgresoGeneral()!=null){
				controlFondosFijos.getEgreso().setStrNumeroEgreso(
						obtenerPeriodoItem(	controlFondosFijos.getEgreso().getIntItemPeriodoEgreso(), 
											controlFondosFijos.getEgreso().getIntItemEgreso(), 
											"000000"));
			}		
			if(controlFondosFijos.getEgreso().getLibroDiario()!=null){
				controlFondosFijos.getEgreso().getLibroDiario().setStrNumeroAsiento(
						obtenerPeriodoItem(	controlFondosFijos.getEgreso().getLibroDiario().getId().getIntContPeriodoLibro(),
											controlFondosFijos.getEgreso().getLibroDiario().getId().getIntContCodigoLibro(), 
											"000000"));
			}	
		} catch (Exception e) {
			log.error("Error en procesarItems(ControlFondosFijos controlFondosFijos ---> "+e);
		}
			
	}
	
	private void procesarItems(Egreso egreso){
		try {
			egreso.setStrNumeroEgreso(
					obtenerPeriodoItem(	egreso.getIntItemPeriodoEgreso(), 
										egreso.getIntItemEgreso(), 
										"000000"));
				
			if(egreso.getLibroDiario()!=null){
				egreso.getLibroDiario().setStrNumeroAsiento(
						obtenerPeriodoItem(	egreso.getLibroDiario().getId().getIntContPeriodoLibro(),
											egreso.getLibroDiario().getId().getIntContCodigoLibro(), 
											"000000"));
			}	
		} catch (Exception e) {
			log.error("Error en procesarItems(Egreso egreso) ---> "+e);
		}
			
	}
	
	/**
	 * CGD-11.10.2013
	 * @param intPeriodo
	 * @param item
	 * @param patron
	 * @return
	 */
	private String obtenerPeriodoItem(Integer intPeriodo, Integer item, String patron){
		String strSalida = "";
		try{
			DecimalFormat formato = new DecimalFormat(patron);
			if(item != null){
				strSalida = intPeriodo+"-"+formato.format(Double.parseDouble(""+item));
			}else{
				strSalida = intPeriodo+"-0";
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
			//return intPeriodo+"-"+item;
		}
		return strSalida;
	}
	private void actualizarCheque(Egreso egreso)throws Exception{
		BancoFacadeLocal bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
		
		BancocuentachequeId bancoCuentaChequeId = new BancocuentachequeId();
		bancoCuentaChequeId.setIntEmpresaPk(egreso.getId().getIntPersEmpresaEgreso());
		bancoCuentaChequeId.setIntItembancofondo(egreso.getIntItemBancoFondo());
		bancoCuentaChequeId.setIntItembancocuenta(egreso.getIntItemBancoCuenta());
		bancoCuentaChequeId.setIntItembancuencheque(egreso.getIntItemBancoCuentaCheque());
		
		Bancocuentacheque bancocuentacheque = bancoFacade.getBancoCuentaChequePorId(bancoCuentaChequeId);
		Integer intNumeroActual = bancocuentacheque.getIntNumeroinicio() + 1;
		bancocuentacheque.setIntNumeroinicio(intNumeroActual);
		bancoFacade.modificarBancoCuentaCheque(bancocuentacheque);
	}
	
	public Egreso grabarEgreso(Egreso egreso)throws BusinessException{
		try{
			List<Egreso> listaEgreso = boEgreso.getListaParaItem(egreso);
			//Se busca el ultimo item egreso registrado para asignarlo al nuevo egreso generado
			int intMayorItemEgreso = 0;
			if(listaEgreso != null && !listaEgreso.isEmpty()){
				for(Egreso egresoTemp : listaEgreso){
					if(egresoTemp.getIntItemEgreso().intValue() > intMayorItemEgreso){
						intMayorItemEgreso = egresoTemp.getIntItemEgreso().intValue();
					}
				}
			}
			intMayorItemEgreso = intMayorItemEgreso + 1;
			egreso.setIntItemEgreso(intMayorItemEgreso);
			log.info("---- EgresoService.grabarEgreso ----");
			log.info("Egreso final generado para grabación ---> "+egreso);
			boEgreso.grabar(egreso);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}

	public ControlFondosFijos grabarControlFondosFijos(ControlFondosFijos controlFondosFijos)throws BusinessException{
		try{
			List<ControlFondosFijos> listaControlFondosFijos = boControlFondosFijos.getParaItem(controlFondosFijos);
			
			int intMayorItemControlFondosFijos = 0;
			if(listaControlFondosFijos != null && !listaControlFondosFijos.isEmpty()){
				for(ControlFondosFijos controlFondosFijosTemp : listaControlFondosFijos){
					if(controlFondosFijosTemp.getId().getIntItemFondoFijo().intValue() > intMayorItemControlFondosFijos){
						intMayorItemControlFondosFijos = controlFondosFijosTemp.getId().getIntItemFondoFijo().intValue();
					}
				}
			}			
			intMayorItemControlFondosFijos = intMayorItemControlFondosFijos + 1;
			controlFondosFijos.getId().setIntItemFondoFijo(intMayorItemControlFondosFijos);
			
			log.info(controlFondosFijos);
			boControlFondosFijos.grabar(controlFondosFijos);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return controlFondosFijos;
	}
	
	public List<ControlFondosFijos> buscarControlFondosFijos(ControlFondosFijos controlFondosFijos) throws BusinessException{
		List<ControlFondosFijos> lista;
		try{
			lista = boControlFondosFijos.getPorBusqueda(controlFondosFijos);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	private Sucursal obtenerSucursal(Integer intIdSucursal, List<Sucursal> listaSucursal)throws Exception{
		for(Sucursal sucursal : listaSucursal){
			if(sucursal.getId().getIntIdSucursal().equals(intIdSucursal)){
				return sucursal;
			}
		}
		return null;
	}
	
	private List<ControlFondosFijos> buscarControlFondosFijosConAgrupacionSucursal(Integer intTipoAgrupacion, 
			ControlFondosFijos controlFondosFijosFiltro, List<Sucursal> listaSucursal) throws Exception{
		
		List<ControlFondosFijos> lista = new ArrayList<ControlFondosFijos>();
		controlFondosFijosFiltro.getId().setIntSucuIdSucursal(null);
		lista = buscarControlFondosFijos(controlFondosFijosFiltro);
		
		List<ControlFondosFijos> listaTemp = new ArrayList<ControlFondosFijos>();
		
		Sucursal sucursal;
		for(ControlFondosFijos controlFondosFijos : lista){
			sucursal = obtenerSucursal(controlFondosFijos.getId().getIntSucuIdSucursal(), listaSucursal); 
			
			if(intTipoAgrupacion.equals(Constante.PARAM_T_TOTALESSUCURSALES_SUCURSALES)){
				listaTemp.add(controlFondosFijos);
			}else if(intTipoAgrupacion.equals(Constante.PARAM_T_TOTALESSUCURSALES_AGENCIAS)){
				if(sucursal.getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_AGENCIA)){
					listaTemp.add(controlFondosFijos);
				}
			}else if(intTipoAgrupacion.equals(Constante.PARAM_T_TOTALESSUCURSALES_FILIALES)){
				if(sucursal.getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_FILIAL)){
					listaTemp.add(controlFondosFijos);
				}
			}else if(intTipoAgrupacion.equals(Constante.PARAM_T_TOTALESSUCURSALES_OFICINAPRINCIPAL)){
				if(sucursal.getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL)){
					listaTemp.add(controlFondosFijos);
				}
			}else if(intTipoAgrupacion.equals(Constante.PARAM_T_TOTALESSUCURSALES_SEDE)){
				if(sucursal.getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL)){
					listaTemp.add(controlFondosFijos);
				}
			}			
		}		
		
		return listaTemp;
	}
	
	public List<ControlFondosFijos> buscarApertura(ControlFondosFijos controlFondosFijosFiltro, List<Sucursal> listaSucursal) throws BusinessException{
		List<ControlFondosFijos> lista = new ArrayList<ControlFondosFijos>();;
		try{
			log.info("--buscarApertura");
			List<ControlFondosFijos> listaTemp = null;
			
			//Si el filtro de sucursal se refiere a una sucursal especifica o a una agrupacion de sucursales
			Integer intTipoSucursal = controlFondosFijosFiltro.getId().getIntSucuIdSucursal();
			if(intTipoSucursal.compareTo(new Integer(0))>0){
				listaTemp = buscarControlFondosFijos(controlFondosFijosFiltro);
			}else{
				listaTemp = buscarControlFondosFijosConAgrupacionSucursal(intTipoSucursal,controlFondosFijosFiltro,listaSucursal);
			}
			
			
			//Obtenemos el grupo de ids de subsucursales que necesitamos
			HashSet<Integer> hashsetIdSubSucursales = new HashSet<Integer>();
			for(ControlFondosFijos controlFondosFijosTemp : listaTemp){
				if(!hashsetIdSubSucursales.contains(controlFondosFijosTemp.getIntSudeIdSubsucursal())){
					hashsetIdSubSucursales.add(controlFondosFijosTemp.getIntSudeIdSubsucursal());
				}
			}
			
			//Agrupamos la lista inicial en varias listas de acuerdo a su idsubsucursal
			HashSet<List<ControlFondosFijos>> hashsetListaCFFsPorSubsucursal = new HashSet<List<ControlFondosFijos>>();
			for(Integer intIdSubSucursal : hashsetIdSubSucursales){
				log.info("intIdSubSucursal:"+intIdSubSucursal);
				List<ControlFondosFijos> listaHash = new ArrayList<ControlFondosFijos>();
				for(ControlFondosFijos controlFondosFijosTemp : listaTemp){
					if(controlFondosFijosTemp.getIntSudeIdSubsucursal().equals(intIdSubSucursal)){
						log.info("CFF:"+controlFondosFijosTemp);
						listaHash.add(controlFondosFijosTemp);
					}
				}
				hashsetListaCFFsPorSubsucursal.add(listaHash);
			}
			
			if(controlFondosFijosFiltro.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){
				//Encontramos el ultimo CFF registrado en cada lista por subsucursal
				for(List<ControlFondosFijos> listaHash : hashsetListaCFFsPorSubsucursal){
					for(ControlFondosFijos cFF : listaHash){
						if(!cFF.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_ANULADO)){
							cFF.setSucursal(obtenerSucursal(cFF.getId().getIntSucuIdSucursal(), listaSucursal));
							procesarItems(cFF);						
							lista.add(cFF);
						}						
					}
				}
			}else{
				//Encontramos el ultimo CFF registrado en cada lista por subsucursal
				for(List<ControlFondosFijos> listaHash : hashsetListaCFFsPorSubsucursal){
					ControlFondosFijos controlUltimo = obtenerControlFondosFijosUltimo(listaHash);
					
					controlUltimo.setAnterior(obtenerControlFondosFijosPrevio(controlUltimo));
					controlUltimo.setSucursal(obtenerSucursal(controlUltimo.getId().getIntSucuIdSucursal(), listaSucursal));
					procesarItems(controlUltimo);
					
					lista.add(controlUltimo);
				}
			}			
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	private ControlFondosFijos obtenerControlFondosFijosPrevio(ControlFondosFijos controlFondosFijos) throws Exception{
		log.info("--buscarControlFondosFijosPrevio");
		Integer intPeriodo = controlFondosFijos.getId().getIntItemPeriodoFondo();
		Integer intEstadoFondo = controlFondosFijos.getIntEstadoFondo();
//		ControlFondosFijos controlFondosFijosTemp = null;
//		controlFondosFijosTemp = controlFondosFijos;
		controlFondosFijos.getId().setIntItemPeriodoFondo(null);
		controlFondosFijos.setIntEstadoFondo(null);
		List<ControlFondosFijos> listaTemp = buscarControlFondosFijos(controlFondosFijos);
		List<ControlFondosFijos> lista = new ArrayList<ControlFondosFijos>();
		
		//Obtenemos los ControlFondosFijos anteriores cuyos items sean menores al del actual CFF
		for(ControlFondosFijos controlFondosFijosAnterior : listaTemp){
			log.info("anterior:"+controlFondosFijosAnterior);
			if (controlFondosFijosAnterior.getId().getIntItemPeriodoFondo().equals(intPeriodo)) {
				if(controlFondosFijosAnterior.getId().getIntItemFondoFijo().compareTo(controlFondosFijos.getId().getIntItemFondoFijo())<0){
					lista.add(controlFondosFijosAnterior);
				}
			}else if (!controlFondosFijosAnterior.getId().getIntItemPeriodoFondo().equals(intPeriodo)) {
				lista.add(controlFondosFijosAnterior);
			}
		}
		
		if(lista.isEmpty()){
			controlFondosFijos.getId().setIntItemPeriodoFondo(intPeriodo);
			controlFondosFijos.setIntEstadoFondo(intEstadoFondo);
			return null;
		}

		//obtenemos el ControlFondosFijos con item mas reciente 
		Collections.sort(lista, new Comparator<ControlFondosFijos>(){
			public int compare(ControlFondosFijos uno, ControlFondosFijos otro) {
				return uno.getId().getIntItemFondoFijo().compareTo(otro.getId().getIntItemFondoFijo());
			}
		});
		
		ControlFondosFijos controlFondosFijosPrevio = lista.get(lista.size()-1);
		procesarItems(controlFondosFijosPrevio);
		log.info("previo:"+controlFondosFijosPrevio);
		controlFondosFijos.getId().setIntItemPeriodoFondo(intPeriodo);
		controlFondosFijos.setIntEstadoFondo(intEstadoFondo);
		return controlFondosFijosPrevio;
	}
	
	public ControlFondosFijos obtenerControlFondosFijosUltimo(List<ControlFondosFijos> lista) throws Exception{
		log.info("--buscarControlFondosFijosUltimo");
		
		//Obtenemos el mayor periodo del grupo
		int intMayorPeriodo = 0;
		for(ControlFondosFijos controlFondosFijos : lista){
			if(controlFondosFijos.getId().getIntItemPeriodoFondo().intValue()>intMayorPeriodo){
				intMayorPeriodo = controlFondosFijos.getId().getIntItemPeriodoFondo();
			}
		}
		log.info("intMayorPeriodo:"+intMayorPeriodo);
		
		//Filtramos solo los CFF que tienen el periodo igual a intMayorPeriodo
		List<ControlFondosFijos> listaTemp = new ArrayList<ControlFondosFijos>();
		for(ControlFondosFijos controlFondosFijos : lista){
			if(controlFondosFijos.getId().getIntItemPeriodoFondo().intValue()==intMayorPeriodo){
				listaTemp.add(controlFondosFijos);
			}
		}
		
		if(listaTemp.isEmpty()){
			return null;
		}
		
		//obtenemos el ControlFondosFijos con item mas reciente 
		Collections.sort(listaTemp, new Comparator<ControlFondosFijos>(){
			public int compare(ControlFondosFijos uno, ControlFondosFijos otro) {
				return uno.getId().getIntItemFondoFijo().compareTo(otro.getId().getIntItemFondoFijo());
			}
		});		
		
		ControlFondosFijos controlFondosFijosUltimo = listaTemp.get(listaTemp.size()-1);
		procesarItems(controlFondosFijosUltimo);
		log.info("ultimo:"+controlFondosFijosUltimo);
		
		return controlFondosFijosUltimo;
	}
	
	private Sucursal buscarSucursalPorId(Integer intIdSucursal, List<Sucursal> lista){
		for(Sucursal sucursal : lista){
			if(sucursal.getId().getIntIdSucursal().equals(intIdSucursal)){
				return sucursal;
			}
		}
		return null;
	}
	
	public List<ControlFondosFijos> buscarCierre(ControlFondosFijos controlFondosFijosFiltro, List<Sucursal> listaSucursal) throws BusinessException{
		List<ControlFondosFijos> lista;
		try{
			lista = buscarControlFondosFijos(controlFondosFijosFiltro);
			for(ControlFondosFijos controlFondosFijos : lista){
				controlFondosFijos.setSucursal(buscarSucursalPorId(controlFondosFijos.getId().getIntSucuIdSucursal(), listaSucursal));
				procesarItems(controlFondosFijos);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Egreso> buscarTransferencia(Egreso egreso, Date dtFiltroDesde, Date dtFiltroHasta, List<Persona> listaPersona) throws BusinessException{
		List<Egreso> lista;
		try{
			lista = boEgreso.buscarTransferencia(egreso, dtFiltroDesde, dtFiltroHasta);
			for(Egreso egresoTemp : lista){
				log.info(egresoTemp);
			}
			if(listaPersona!=null && !listaPersona.isEmpty()){
				List<Egreso>listaEgresoTemp = new ArrayList<Egreso>();
				for(Egreso egresoTemp : lista){
					boolean egresoPoseePersona = Boolean.FALSE;
					for(Persona persona : listaPersona){
						if(egresoTemp.getIntPersPersonaGirado().equals(persona.getIntIdPersona())){
							egresoPoseePersona = Boolean.TRUE;
							break;
						}
					}
					if(egresoPoseePersona){
						listaEgresoTemp.add(egresoTemp);
					}
				}
				lista = listaEgresoTemp;
			}
			
			for(Egreso egresoTemp : lista){
				log.info(egresoTemp);
				procesarItems(egresoTemp);
				egresoTemp.setListaEgresoDetalle(boEgresoDetalle.getPorEgreso(egresoTemp));
			}
			//Autor: jchavez / Tarea: Creación / Fecha: 19.08.2014 /
			if(lista!=null && !lista.isEmpty()){
				TablaFacadeRemote tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				List<Tabla> listaTablaBanco = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_BANCOS));
				List<Bancofondo> listaBanco = cargarListaBanco();
				for(Egreso egr : lista){
					for(Bancofondo banco : listaBanco){
						for(Bancocuenta bancoCuenta : banco.getListaBancocuenta()){
							if (egr.getIntItemBancoFondo()!=null && egr.getIntItemBancoCuenta()!=null) {
								if(egr.getIntItemBancoFondo().equals(bancoCuenta.getId().getIntItembancofondo()) && 
									egr.getIntItemBancoCuenta().equals(bancoCuenta.getId().getIntItembancocuenta())){
									bancoCuenta.setBancofondo(banco);
									log.info("numero cuenta bancaria: "+bancoCuenta.getCuentaBancaria().getStrNroCuentaBancaria());
									bancoCuenta.setStrEtiqueta(obtenerEtiquetaBanco(banco.getIntBancoCod(),listaTablaBanco));
									egr.setBancoCuenta(bancoCuenta);
								}
							}							
						}
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//Autor: jchavez / Tarea: Creación / Fecha: 19.08.2014 /
	private List<Bancofondo> cargarListaBanco()throws Exception{
		BancoFacadeLocal bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
		Bancofondo bancoFondoFiltro = new Bancofondo();
		bancoFondoFiltro.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
		bancoFondoFiltro.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		return bancoFacade.buscarBancoFondo(bancoFondoFiltro);
	}
	
	//Autor: jchavez / Tarea: Creación / Fecha: 19.08.2014 /
	private String obtenerEtiquetaBanco(Integer intBancoCod, List<Tabla> listaTablaBanco) throws Exception{		
		for(Tabla tabla : listaTablaBanco){
			if(tabla.getIntIdDetalle().equals(intBancoCod)){
				return tabla.getStrDescripcion();
			}
		}
		return null;
	}
	
	public List<ControlFondosFijos> buscarControlFondosParaGiro(Integer intIdSucursal, Integer intIdSubSucursal, Integer intIdEmpresa) throws BusinessException{
		List<ControlFondosFijos> listaControlFondosFijos = new ArrayList<ControlFondosFijos>();
		try{
			ControlFondosFijos controlFiltro = new ControlFondosFijos();
			controlFiltro.setId(new ControlFondosFijosId());
			controlFiltro.getId().setIntPersEmpresa(intIdEmpresa);
			controlFiltro.getId().setIntSucuIdSucursal(intIdSucursal);
			controlFiltro.setIntSudeIdSubsucursal(intIdSubSucursal);
			
			//Para el caso de Giro, solo tendremos en cuenta los CFFs de con TipoFondoFijo: FondoCmabio y EntregaARendir, 
			//y con TipoDocumento:RendicionDeFondosFijos. Pueden existir varios CFFs aperturados para una sucursal dependiendo del TipoFondoFijo
			//por lo que se retornana una lista de CFFs
			List<ControlFondosFijos> listaControlTemp = boControlFondosFijos.getPorBusqueda(controlFiltro);
			List<ControlFondosFijos> listaControlFondoCambio = new ArrayList<ControlFondosFijos>();
			List<ControlFondosFijos> listaControlEntregaARendir = new ArrayList<ControlFondosFijos>();
			
			for(ControlFondosFijos controlFondosFijosTemp : listaControlTemp){
				if(!controlFondosFijosTemp.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_ABIERTO)){
					continue;
				}
				log.info(controlFondosFijosTemp);
				if(controlFondosFijosTemp.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_FONDOCAMBIO)){					
					listaControlFondoCambio.add(controlFondosFijosTemp);
				}
				else if(controlFondosFijosTemp.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){
					listaControlEntregaARendir.add(controlFondosFijosTemp);
				}
			}
			
			ControlFondosFijos ultimoCFFFondoCambio = obtenerControlFondosFijosUltimo(listaControlFondoCambio);
			//Pueden existir varios cffs de entrega a rendir
			//ControlFondosFijos ultimoCFFEntregaARendir = obtenerControlFondosFijosUltimo(listaControlEntregaARendir);
			
			log.info("ultimoCFFFondoCambio:"+ultimoCFFFondoCambio);
			//log.info("ultimoCFFEntregaARendir:"+ultimoCFFEntregaARendir);
			
			if(ultimoCFFFondoCambio != null){
				Egreso egresoFondoCambio = boEgreso.getPorControlFondosFijos(ultimoCFFFondoCambio);
				log.info("egresoFondoCambio:"+egresoFondoCambio);
				if(ultimoCFFFondoCambio.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_ABIERTO)){
					procesarItems(ultimoCFFFondoCambio);
					ultimoCFFFondoCambio.setEgreso(egresoFondoCambio);
					listaControlFondosFijos.add(ultimoCFFFondoCambio);
				}
			}
			
			for(ControlFondosFijos cFF : listaControlEntregaARendir){
				Egreso egresoEntregaARendir = boEgreso.getPorControlFondosFijos(cFF);
				log.info("egresoEntregaARendir:"+egresoEntregaARendir);
				if(cFF.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_ABIERTO)){
					procesarItems(cFF);
					cFF.setEgreso(egresoEntregaARendir);
					listaControlFondosFijos.add(cFF);
				}
			}
			
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaControlFondosFijos;
	}
	
	
	public Egreso grabarEgresoParaGiroPrestamo(Egreso egreso) throws BusinessException{
		try{
			ControlFondosFijos controlFondosFijosGirar = egreso.getControlFondosFijos();
			// Se actualiza el monto saldo y el monto utilizado del Fondo Fijo usado, si el préstamo es girado por la Sede Central
			if(controlFondosFijosGirar != null ){
				//Autor: jchavez / Tarea: Modificación / Fecha: 21.08.2014 /
				if (!controlFondosFijosGirar.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)) {
					log.info("--grabarEgresoParaGiroPrestamo");
					CierreDiarioArqueoFacadeLocal cierreDiarioArqueoFacadeLocal = (CierreDiarioArqueoFacadeLocal) EJBFactory.getLocal(CierreDiarioArqueoFacadeLocal.class);			
					if(!cierreDiarioArqueoFacadeLocal.validarMovimientoCaja(controlFondosFijosGirar)){
						egreso.setStrMsgErrorGeneracionEgreso("El fondo fijo tiene un problema de cierre y arqueo");
						throw new Exception("El fondo fijo tiene un problema de cierre y arqueo");
					}
					
					//actualizamos controlFondosFijosGirar por si los montos se han modificado durante el proceso de egreso
					controlFondosFijosGirar = boControlFondosFijos.getPorPk(controlFondosFijosGirar.getId());
					Acceso acceso = accesoService.obtenerAccesoPorControlFondosFijos(controlFondosFijosGirar);
					if(acceso.getAccesoDetalleUsar()==null){
						egreso.setStrMsgErrorGeneracionEgreso("No existe un acceso detalle configurado para la sucursal");
						throw new Exception("No existe un acceso detalle configurado para la sucursal");
					}				
					if (!egreso.getBlnEsGiroPorSedeCentral()) {
						if(acceso.getAccesoDetalleUsar().getBdMontoMaximo()!=null
							&& acceso.getAccesoDetalleUsar().getBdMontoMaximo().compareTo(egreso.getBdMontoTotal())<0){
								egreso.setStrMsgErrorGeneracionEgreso("El monto del egreso es mayor al monto permitido ["+acceso.getAccesoDetalleUsar().getBdMontoMaximo()+"]");
								throw new Exception("El monto del egreso es mayor al monto permitido ["+acceso.getAccesoDetalleUsar().getBdMontoMaximo()+"]");
						}
					}
									
					if(controlFondosFijosGirar.getBdMontoSaldo().compareTo(egreso.getBdMontoTotal())<0){
						egreso.setStrMsgErrorGeneracionEgreso("El monto del egreso es mayor al saldo disponible ["+controlFondosFijosGirar.getBdMontoSaldo()+"]");
						throw new Exception("El monto del egreso es mayor al saldo disponible ["+controlFondosFijosGirar.getBdMontoSaldo()+"]");
					}
					
					controlFondosFijosGirar.setBdMontoSaldo(controlFondosFijosGirar.getBdMontoSaldo().subtract(egreso.getBdMontoTotal()));
					controlFondosFijosGirar.setBdMontoUtilizado(controlFondosFijosGirar.getBdMontoUtilizado().add(egreso.getBdMontoTotal()));
					log.info(controlFondosFijosGirar);
					boControlFondosFijos.modificar(controlFondosFijosGirar);
				}				
			}

			
			// Grabamos el Libro Diario
			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			LibroDiario libroDiario = libroDiarioFacade.grabarLibroDiario(egreso.getLibroDiario());
			
			// Seteamos datos del libro diario en egreso para su grabación
			egreso.setIntPersEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
			egreso.setIntContPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
			egreso.setIntContCodigoLibro(libroDiario.getId().getIntContCodigoLibro());	
			
			// Grabamos Egreso
			egreso = grabarEgreso(egreso);
			
			// Grabamos Egreso Detalle
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				egresoDetalle.getId().setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
				boEgresoDetalle.grabar(egresoDetalle);
			}
			
			// Se da formato y se setea el Número de Egreso y el Número de Asiento
			procesarItems(egreso);
			
			for(LibroDiarioDetalle libroDiarioDetalle : libroDiario.getListaLibroDiarioDetalle()){
				//Para el LDD asociado al giro del FF se le coloca el numero de egreso en strNumeroDocumento
				if (libroDiarioDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)){
//				if(libroDiarioDetalle.getStrNumeroDocumento() == null){
					libroDiarioDetalle.setStrNumeroDocumento(egreso.getStrNumeroEgreso());
					libroDiarioFacade.modificarLibroDiarioDetalle(libroDiarioDetalle);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	public ControlFondoFijoAnula grabarAnulaCierre(ControlFondosFijos o, Integer intEmpresa, Integer intPersona, String strObservacion, Integer intParaTipoAnulaFondo) throws BusinessException {
		ControlFondoFijoAnula anula = new ControlFondoFijoAnula(); 
		anula.setId(new ControlFondoFijoAnulaId());
		ControlFondosFijos controlFdoFijo = null;
		try {
			//Modificando Movimiento cerrado a abierto
			o.setTsFechaCierre(null);
			o.setIntPersEmpresaCierre(null);
			o.setIntPersPersonaCierre(null);
			o.setIntEstadoFondo(Constante.PARAM_T_ESTADOFONDO_ABIERTO);
			
			//Datos a grabar en TES_CONTROLFDOFIJOANULA
			anula.getId().setIntPersEmpresa(o.getId().getIntPersEmpresa());
			anula.getId().setIntParaTipoFondoFijo(o.getId().getIntParaTipoFondoFijo());
			anula.getId().setIntItemPeriodoFondo(o.getId().getIntItemPeriodoFondo());
			anula.getId().setIntSucuIdSucursal(o.getId().getIntSucuIdSucursal());
			anula.getId().setIntItemFondoFijo(o.getId().getIntItemFondoFijo());
			
			anula.setTsFechaAnulacion(new Timestamp(new Date().getTime()));
			anula.setIntParaTipoAnulaFondo(intParaTipoAnulaFondo);
			anula.setStrObservacion(strObservacion);
			anula.setIntIdEmpresaUsuario(intEmpresa);
			anula.setIntIdPersonaUsuario(intPersona);
			
			controlFdoFijo = boControlFondosFijos.modificar(o);
			if (controlFdoFijo!=null) {
				anula = boControlFondosFijos.grabarAnulaCierre(anula);
			}			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return anula;
	}
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 06.10.2014
	 * Funcionalidad: Recupera lista egreso detalle interfaz a mostrar en el giro por tesoreria
	 * @author jchavez
	 * @param ordenCompra
	 * @return lista - lista de Orden de Compra
	 * @throws Exception
	 */
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfazOrdenCompra(OrdenCompra ordenCompra, Integer intTipoDocumento)throws BusinessException{
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = new ArrayList<EgresoDetalleInterfaz>();
		try {
			
			EmpresaFacadeRemote empresaFacade =  (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);

			if (ordenCompra.getListaOrdenCompraDocumento()!=null && !ordenCompra.getListaOrdenCompraDocumento().isEmpty()) {
				for (OrdenCompraDocumento ordCmpDoc : ordenCompra.getListaOrdenCompraDocumento()) {
					EgresoDetalleInterfaz eDI = new EgresoDetalleInterfaz();
					eDI.setIntParaDocumentoGeneral(intTipoDocumento);//Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO);
					eDI.setStrNroDocumento(""+ordCmpDoc.getId().getIntItemOrdenCompraDocumento());
					BigDecimal bdAcumulado = BigDecimal.ZERO;
					for (OrdenCompraDetalle ordCmpDet : ordenCompra.getListaOrdenCompraDetalle()) {
						if (ordCmpDet.getId().getIntPersEmpresa().equals(ordCmpDoc.getId().getIntPersEmpresa())
							&& ordCmpDet.getId().getIntItemOrdenCompra().equals(ordCmpDoc.getId().getIntItemOrdenCompra())) {
							bdAcumulado = bdAcumulado.add(ordCmpDet.getBdPrecioTotal());
							eDI.setIntTipoMoneda(ordCmpDet.getIntParaTipoMoneda());
						}
					}
					eDI.setBdMonto(bdAcumulado);
					eDI.setBdSubTotal(ordCmpDoc.getBdMontoDocumento());
					eDI.setSucursal(empresaFacade.getSucursalPorId(ordCmpDoc.getIntSucuIdSucursal()));
					for(Subsucursal subsucursal : eDI.getSucursal().getListaSubSucursal()){
						if(subsucursal.getId().getIntIdSubSucursal().equals(ordCmpDoc.getIntSudeIdSubsucursal()))
							eDI.setSubsucursal(subsucursal);
						break;
					}
					listaEgresoDetalleInterfaz.add(eDI);
				}
			}
		}catch(BusinessException e){
			throw e;
		} catch (Exception e) {
			log.info("Error en cargarListaEgresoDetalleInterfazOrdenCompra() --> "+e.getMessage());
		}
		return listaEgresoDetalleInterfaz;
	}
	
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 06.10.2014
	 * Funcionalidad: Proceso de grabacion del adelanto-garantia TESORERIA / ORDEN DE COMPRA
	 * @author jchavez
	 * @param listaEgresoDetalleInterfaz
	 * @param controlFondosFijos
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public Egreso grabarGiroOrdenCompraDocumento(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, ControlFondosFijos controlFondosFijos, Usuario usuario, Integer intParaTipoDocumento)throws BusinessException{
		try{
			EgresoFacadeLocal egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			Egreso egreso = generarEgresoOrdenCompraDocumento(listaEgresoDetalleInterfaz, controlFondosFijos, usuario, intParaTipoDocumento);
			
			log.info(egreso);
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info(egresoDetalle);
			}
			log.info(egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info(libroDiarioDetalle);
			}

			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);

			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					if(!eDI.isEsDiferencial()){
						OrdenCompra ordCmp = eDI.getOrdenCompra();
						for (OrdenCompraDocumento o : ordCmp.getListaOrdenCompraDocumento()) {
							o.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
							o.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
							boOrdenCompraDocumento.modificar(o);
						}						
					}
				}
			}
			return egreso;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 10.10.2014
	 * Funcionalidad: Proceso de generacion de egreso, egreso detalle, libro y libro diario detalle para Adelanto-Garantia TESORERIA / ORDEN DE COMPRA
	 * @param listaEgresoDetalleInterfaz
	 * @param controlFondosFijos
	 * @param usuario
	 * @param intParaTipoDocumento
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoOrdenCompraDocumento(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, ControlFondosFijos controlFondosFijos, Usuario usuario, Integer intParaTipoDocumento)throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			
			Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
			Persona proveedor = listaEgresoDetalleInterfaz.get(0).getPersona();

			BigDecimal bdMontoTotal = new BigDecimal(0);
			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					bdMontoTotal = bdMontoTotal.add(eDI.getBdSubTotal());
				}
			}				
			String strGlosaEgreso = listaEgresoDetalleInterfaz.get(0).getOrdenCompra().getStrGlosaEgreso();

			egreso.setId(new EgresoId());
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			
			egreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			egreso.setTsFechaProceso(new Timestamp(new Date().getTime()));
			egreso.setDtFechaEgreso(new Timestamp(new Date().getTime()));
			
			//PERS_CUENTABANCARIAGIRADO_N: la cuenta bancaria del proveedor, solo para cheque, transferencia, Telecrédito.
			egreso.setIntPersCuentaBancariaGirado(null);
			
			if (controlFondosFijos.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)) {
				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA);
				egreso.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
				egreso.setIntItemPeriodoFondo(null);
				egreso.setIntItemFondoFijo(null);
			}else{
				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
				egreso.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
				egreso.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
				egreso.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
			}

			egreso.setIntItemBancoFondo(null);
			egreso.setIntItemBancoCuenta(null);
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(null);
			egreso.setIntNumeroTransferencia(null);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(proveedor.getIntIdPersona());
			
			egreso.setIntCuentaGirado(null); //CSOC_CUENTAGIRADO_N: en blanco solo se utiliza para socios.
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			egreso.setIntPersPersonaApoderado(null);
			egreso.setIntPersEmpresaApoderado(null);
			egreso.setBdMontoTotal(bdMontoTotal);
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			egreso.setIntParaTipoApoderado(null);
			egreso.setIntItemArchivoApoderado(null);
			egreso.setIntItemHistoricoApoderado(null);			
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			egreso.setListaEgresoDetalle(new ArrayList<EgresoDetalle>());
			
			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					ModeloDetalle modeloDetalleUsar = obtenerModeloDetalle(eDI.getOrdenCompra(), intParaTipoDocumento);
					EgresoDetalle egresoDetalleDebe = new EgresoDetalle();		
					egresoDetalleDebe.getId().setIntPersEmpresaEgreso(intIdEmpresa);
					egresoDetalleDebe.setIntParaDocumentoGeneral(intParaTipoDocumento);//Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO);
					egresoDetalleDebe.setIntParaTipoComprobante(null);
					egresoDetalleDebe.setStrSerieDocumento(null);
					egresoDetalleDebe.setStrNumeroDocumento(eDI.getStrNroDocumento());
					egresoDetalleDebe.setStrDescripcionEgreso(modeloDetalleUsar.getPlanCuenta().getStrDescripcion());
					egresoDetalleDebe.setIntPersEmpresaGirado(intIdEmpresa);
					egresoDetalleDebe.setIntPersonaGirado(proveedor.getIntIdPersona());
					egresoDetalleDebe.setIntCuentaGirado(null);
					egresoDetalleDebe.setIntSucuIdSucursalEgreso(eDI.getSucursal().getId().getIntIdSucursal());
					egresoDetalleDebe.setIntSudeIdSubsucursalEgreso(eDI.getSubsucursal().getId().getIntIdSubSucursal());
					egresoDetalleDebe.setIntParaTipoMoneda(eDI.getIntTipoMoneda());

					egresoDetalleDebe.setBdMontoCargo(eDI.getBdSubTotal());
					egresoDetalleDebe.setBdMontoAbono(null);
					
					egresoDetalleDebe.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					egresoDetalleDebe.setTsFechaRegistro(MyUtil.obtenerFechaActual());
					egresoDetalleDebe.setIntPersEmpresaUsuario(intIdEmpresa);
					egresoDetalleDebe.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
					egresoDetalleDebe.setIntPersEmpresaLibroDestino(null);
					egresoDetalleDebe.setIntContPeriodoLibroDestino(null);
					egresoDetalleDebe.setIntContCodigoLibroDestino(null);
					egresoDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleUsar.getPlanCuenta().getId().getIntEmpresaCuentaPk());
					egresoDetalleDebe.setIntContPeriodoCuenta(modeloDetalleUsar.getPlanCuenta().getId().getIntPeriodoCuenta());
					egresoDetalleDebe.setStrContNumeroCuenta(modeloDetalleUsar.getPlanCuenta().getId().getStrNumeroCuenta());
					egresoDetalleDebe.setIntParaTipoFondoFijo(null);
					egresoDetalleDebe.setIntItemPeriodoFondo(null);
					egresoDetalleDebe.setIntSucuIdSucursal(null);
					egresoDetalleDebe.setIntItemFondoFijo(null);
					egreso.getListaEgresoDetalle().add(egresoDetalleDebe);					
				}
			}

			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());
			libroDiario.setStrGlosa(strGlosaEgreso);
			libroDiario.setTsFechaRegistro(MyUtil.obtenerFechaActual());
			libroDiario.setTsFechaDocumento(MyUtil.obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			BigDecimal bdLDMontoTotal = BigDecimal.ZERO;
			
			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){	
					Integer intMoneda = controlFondosFijos.getIntParaMoneda();
					TipoCambio tipoCambioActual = null;
					Boolean esMonedaExtranjera = false;
					ModeloDetalle modeloDetalleUsar = obtenerModeloDetalle(eDI.getOrdenCompra(),intParaTipoDocumento);
					bdLDMontoTotal = bdLDMontoTotal.add(eDI.getBdSubTotal());
					LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
					libroDiarioDetalleDebe.setId(new LibroDiarioDetalleId());
					libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
					libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());				
					libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleUsar.getId().getIntPersEmpresaCuenta());
					libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalleUsar.getId().getIntContPeriodoCuenta());
					libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleUsar.getId().getStrContNumeroCuenta());
					libroDiarioDetalleDebe.setIntPersPersona(proveedor.getIntIdPersona());//PROVEEDOR
					libroDiarioDetalleDebe.setIntParaDocumentoGeneral(intParaTipoDocumento);//ADELANTO O GARANTIA
					libroDiarioDetalleDebe.setStrNumeroDocumento(eDI.getStrNroDocumento());
					libroDiarioDetalleDebe.setIntParaMonedaDocumento(intMoneda);
					
					if(!(libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES))){
						tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
						libroDiarioDetalleDebe.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
						esMonedaExtranjera = true;
					}
					
					libroDiarioDetalleDebe.setIntPersEmpresaSucursal(intIdEmpresa);
					libroDiarioDetalleDebe.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
					libroDiarioDetalleDebe.setIntSudeIdSubSucursal(controlFondosFijos.getIntSudeIdSubsucursal());		
					
					if(!esMonedaExtranjera){
						libroDiarioDetalleDebe.setBdDebeSoles(eDI.getBdSubTotal().abs());
					}else{
						libroDiarioDetalleDebe.setBdDebeExtranjero(eDI.getBdSubTotal().abs());
						libroDiarioDetalleDebe.setBdDebeSoles(libroDiarioDetalleDebe.getBdDebeExtranjero().multiply(tipoCambioActual.getBdPromedio()));
					}
					PlanCuentaId pId = new PlanCuentaId();
					pId.setIntEmpresaCuentaPk(modeloDetalleUsar.getId().getIntPersEmpresaCuenta());
					pId.setStrNumeroCuenta(modeloDetalleUsar.getId().getStrContNumeroCuenta());
					pId.setIntPeriodoCuenta(modeloDetalleUsar.getId().getIntContPeriodoCuenta());
					libroDiarioDetalleDebe.setStrComentario(planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().length()<20?planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion():planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().substring(0, 20));

					libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
				}
			}
			
			LibroDiarioDetalle libroDiarioDetalleBanco = generarLibroDiarioDetalleHaber(controlFondosFijos, bdLDMontoTotal);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBanco);
			egreso.setBlnEsGiroPorSedeCentral(true);
			egreso.setLibroDiario(libroDiario);
			egreso.setControlFondosFijos(controlFondosFijos);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 10.10.2014
	 * Funcionalidad: Proceso de generacion de Libro Diadio Detalle - Caja (HABER)
	 * @param controlFondosFijos
	 * @param bdMontoAGirar
	 * @return
	 * @throws Exception
	 */
	private LibroDiarioDetalle generarLibroDiarioDetalleHaber(ControlFondosFijos controlFondosFijos, BigDecimal bdMontoAGirar) throws Exception{
		BancoFacadeLocal bancoFacade =  (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
		Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
		TipoCambio tipoCambioActual = null;
		Bancofondo bancoFondo = bancoFacade.obtenerBancoFondoParaIngresoDesdeControl(controlFondosFijos);	
		Integer intMoneda = controlFondosFijos.getIntParaMoneda();
			
		boolean esMonedaExtranjera;
		LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
		libroDiarioDetalleHaber.setId(new LibroDiarioDetalleId());
		libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());				
		libroDiarioDetalleHaber.setIntPersEmpresaCuenta(bancoFondo.getFondoDetalleUsar().getPlanCuenta().getId().getIntEmpresaCuentaPk());
		libroDiarioDetalleHaber.setIntContPeriodo(bancoFondo.getFondoDetalleUsar().getPlanCuenta().getId().getIntPeriodoCuenta());
		libroDiarioDetalleHaber.setStrContNumeroCuenta(bancoFondo.getFondoDetalleUsar().getPlanCuenta().getId().getStrNumeroCuenta());
		libroDiarioDetalleHaber.setIntPersPersona(controlFondosFijos.getSucursal().getIntPersPersonaPk());
		libroDiarioDetalleHaber.setIntParaDocumentoGeneral(controlFondosFijos.getId().getIntParaTipoFondoFijo());
		libroDiarioDetalleHaber.setStrSerieDocumento(null);
		libroDiarioDetalleHaber.setStrNumeroDocumento(null);
		libroDiarioDetalleHaber.setIntParaMonedaDocumento(intMoneda);
		
		if(!libroDiarioDetalleHaber.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
			libroDiarioDetalleHaber.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}else{
			esMonedaExtranjera = Boolean.FALSE;
		}
			
		libroDiarioDetalleHaber.setIntPersEmpresaSucursal(intIdEmpresa);
		libroDiarioDetalleHaber.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
		libroDiarioDetalleHaber.setIntSudeIdSubSucursal(controlFondosFijos.getIntSudeIdSubsucursal());		
			
		if(!esMonedaExtranjera){
			libroDiarioDetalleHaber.setBdHaberSoles(bdMontoAGirar.abs());
		}else{
			libroDiarioDetalleHaber.setBdHaberExtranjero(bdMontoAGirar.abs());
			libroDiarioDetalleHaber.setBdHaberSoles(libroDiarioDetalleHaber.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
		}
		
		libroDiarioDetalleHaber.setStrComentario(bancoFondo.getFondoDetalleUsar().getPlanCuenta().getStrDescripcion());
				
		return libroDiarioDetalleHaber;
	}
	
	private Integer	obtenerPeriodoActual(){
		String strPeriodo = "";
		Calendar cal = Calendar.getInstance();
		int año = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		mes = mes + 1; 
		if(mes<10){
			strPeriodo = año + "0" + mes;
		}else{
			strPeriodo  = año + "" + mes;
		}		
		return Integer.parseInt(strPeriodo);		
	}
	
	private TipoCambio obtenerTipoCambioActual(Integer intParaMoneda, Integer intIdEmpresa)throws Exception{
		GeneralFacadeRemote generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		return generalFacade.getTipoCambioPorMonedaYFecha(intParaMoneda, new Date(), intIdEmpresa);		
	}

	private ModeloDetalle obtenerModeloDetalle(OrdenCompra ordCmp, Integer intParaTipoDocumento)throws Exception{
		ModeloFacadeRemote modeloFacade =  (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		PlanCuentaFacadeRemote planCuentaFacade =  (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
		Integer intIdEmpresa = ordCmp.getId().getIntPersEmpresa();
		ModeloDetalle modeloDetalleUsar = null;
		Modelo modelo = null;
		Requisicion requisicion = null;
		if (intParaTipoDocumento.equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)) {
			modelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CANCELACIONADELANTOORDENCOMPRA, intIdEmpresa).get(0);
			RequisicionId rId = new RequisicionId();  
			rId.setIntPersEmpresa(ordCmp.getIntPersEmpresaRequisicion());
			rId.setIntItemRequisicion(ordCmp.getIntItemRequisicion());
			requisicion = boRequisicion.getPorPk(rId);
		}else if (intParaTipoDocumento.equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA)) {
			modelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CANCELACIONGARANTIAORDENCOMPRA, intIdEmpresa).get(0);
		}

		//log de modelo recuperado...
		log.info("Modelo: "+modelo);
		for (ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()) {
			log.info("ModeloDetalle: "+modeloDetalle);
			if (intParaTipoDocumento.equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)) {
				for (ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel()) {
					log.info("ModeloDetalleNivel: "+modeloDetalleNivel);
					if (modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase("Para_TipoRequisicion_n_cod") && modeloDetalleNivel.getIntValor().equals(requisicion.getIntParaTipoRequisicion())) {
						PlanCuentaId pId = new PlanCuentaId();
						pId.setIntEmpresaCuentaPk(modeloDetalle.getId().getIntPersEmpresaCuenta());
						pId.setStrNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
						pId.setIntPeriodoCuenta(modeloDetalle.getId().getIntContPeriodoCuenta());
						modeloDetalle.setPlanCuenta(planCuentaFacade.getPlanCuentaPorPk(pId));
						modeloDetalleUsar = modeloDetalle;
						break;
					}
				}
			}else if (intParaTipoDocumento.equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA)) {
				for (ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel()) {
					log.info("ModeloDetalleNivel: "+modeloDetalleNivel);
					PlanCuentaId pId = new PlanCuentaId();
					pId.setIntEmpresaCuentaPk(modeloDetalle.getId().getIntPersEmpresaCuenta());
					pId.setStrNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
					pId.setIntPeriodoCuenta(modeloDetalle.getId().getIntContPeriodoCuenta());
					modeloDetalle.setPlanCuenta(planCuentaFacade.getPlanCuentaPorPk(pId));
					modeloDetalleUsar = modeloDetalle;
					break;
				}
			}			
		}
		
		return modeloDetalleUsar;
	}
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 13.10.2014
	 * Funcionalidad: Proceso de grabacion del adelanto-garantia TESORERIA / ORDEN DE COMPRA
	 * @param listaEgresoDetalleInterfaz
	 * @param bancoCuenta
	 * @param usuario
	 * @param intParaTipoDocumento
	 * @return
	 * @throws BusinessException
	 */
	public Egreso grabarGiroOrdenCompraDocumentoPorTesoreria(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, Bancocuenta bancoCuenta, Usuario usuario, Integer intParaTipoDocumento, Integer intTipoDocumentoValidar)throws BusinessException{
		try{
			EgresoFacadeLocal egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			Egreso egreso = generarEgresoOrdenCompraDocumentoPorTesoreria(listaEgresoDetalleInterfaz, bancoCuenta, usuario, intParaTipoDocumento, intTipoDocumentoValidar);
			
			log.info(egreso);
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info(egresoDetalle);
			}
			log.info(egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info(libroDiarioDetalle);
			}

			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);

			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					if(!eDI.isEsDiferencial()){
						OrdenCompra ordCmp = eDI.getOrdenCompra();
						for (OrdenCompraDocumento o : ordCmp.getListaOrdenCompraDocumento()) {
							o.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
							o.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
							boOrdenCompraDocumento.modificar(o);
						}						
					}
				}
			}
			return egreso;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 13.10.2014
	 * Funcionalidad: Método de generacion del egreso, egreso detalle y asiento contable
	 * @author jchavez
	 * @param listaEgresoDetalleInterfaz
	 * @param controlFondosFijos
	 * @param usuario
	 * @param intParaTipoDocumento
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoOrdenCompraDocumentoPorTesoreria(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, Bancocuenta bancoCuenta, Usuario usuario, Integer intParaTipoDocumento, Integer intTipoDocumentoValidar)throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			
			Integer intIdEmpresa = bancoCuenta.getId().getIntEmpresaPk();
			Persona proveedor = listaEgresoDetalleInterfaz.get(0).getPersona();

			BigDecimal bdMontoTotal = new BigDecimal(0);
			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					bdMontoTotal = bdMontoTotal.add(eDI.getBdSubTotal());
				}
			}				
			String strGlosaEgreso = listaEgresoDetalleInterfaz.get(0).getOrdenCompra().getStrGlosaEgreso();

			egreso.setId(new EgresoId());
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			
			egreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			egreso.setTsFechaProceso(new Timestamp(new Date().getTime()));
			egreso.setDtFechaEgreso(new Timestamp(new Date().getTime()));
			
			//PERS_CUENTABANCARIAGIRADO_N: la cuenta bancaria del proveedor, solo para cheque, transferencia, Telecrédito.
			egreso.setIntPersCuentaBancariaGirado(null);
			
			egreso.setIntParaTipoFondoFijo(null);
			egreso.setIntItemPeriodoFondo(null);
			egreso.setIntItemFondoFijo(null);
			
			//COMO SE SI ES EFECTIVO O TRANSFERENCIA
//			if (controlFondosFijos.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)) {
//				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA);
//			}else{
				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
//			}

			egreso.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
			egreso.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
			
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(null);
			egreso.setIntNumeroTransferencia(null);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(proveedor.getIntIdPersona());
			
			egreso.setIntCuentaGirado(null); //CSOC_CUENTAGIRADO_N: en blanco solo se utiliza para socios.
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			egreso.setIntPersPersonaApoderado(null);
			egreso.setIntPersEmpresaApoderado(null);
			egreso.setBdMontoTotal(bdMontoTotal);
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			egreso.setIntParaTipoApoderado(null);
			egreso.setIntItemArchivoApoderado(null);
			egreso.setIntItemHistoricoApoderado(null);			
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			egreso.setListaEgresoDetalle(new ArrayList<EgresoDetalle>());
			
			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					ModeloDetalle modeloDetalleUsar = obtenerModeloDetalle(eDI.getOrdenCompra(), intParaTipoDocumento);
					EgresoDetalle egresoDetalleDebe = new EgresoDetalle();		
					egresoDetalleDebe.getId().setIntPersEmpresaEgreso(intIdEmpresa);
					egresoDetalleDebe.setIntParaDocumentoGeneral(intParaTipoDocumento);//Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO);
					egresoDetalleDebe.setIntParaTipoComprobante(null);
					egresoDetalleDebe.setStrSerieDocumento(null);
					egresoDetalleDebe.setStrNumeroDocumento(eDI.getStrNroDocumento());
					egresoDetalleDebe.setStrDescripcionEgreso(modeloDetalleUsar.getPlanCuenta().getStrDescripcion());
					egresoDetalleDebe.setIntPersEmpresaGirado(intIdEmpresa);
					egresoDetalleDebe.setIntPersonaGirado(proveedor.getIntIdPersona());
					egresoDetalleDebe.setIntCuentaGirado(null);
					egresoDetalleDebe.setIntSucuIdSucursalEgreso(eDI.getSucursal().getId().getIntIdSucursal());
					egresoDetalleDebe.setIntSudeIdSubsucursalEgreso(eDI.getSubsucursal().getId().getIntIdSubSucursal());
					egresoDetalleDebe.setIntParaTipoMoneda(eDI.getIntTipoMoneda());

					egresoDetalleDebe.setBdMontoCargo(eDI.getBdSubTotal());
					egresoDetalleDebe.setBdMontoAbono(null);
					
					egresoDetalleDebe.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					egresoDetalleDebe.setTsFechaRegistro(MyUtil.obtenerFechaActual());
					egresoDetalleDebe.setIntPersEmpresaUsuario(intIdEmpresa);
					egresoDetalleDebe.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
					egresoDetalleDebe.setIntPersEmpresaLibroDestino(null);
					egresoDetalleDebe.setIntContPeriodoLibroDestino(null);
					egresoDetalleDebe.setIntContCodigoLibroDestino(null);
					egresoDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleUsar.getPlanCuenta().getId().getIntEmpresaCuentaPk());
					egresoDetalleDebe.setIntContPeriodoCuenta(modeloDetalleUsar.getPlanCuenta().getId().getIntPeriodoCuenta());
					egresoDetalleDebe.setStrContNumeroCuenta(modeloDetalleUsar.getPlanCuenta().getId().getStrNumeroCuenta());
					egresoDetalleDebe.setIntParaTipoFondoFijo(null);
					egresoDetalleDebe.setIntItemPeriodoFondo(null);
					egresoDetalleDebe.setIntSucuIdSucursal(null);
					egresoDetalleDebe.setIntItemFondoFijo(null);
					egreso.getListaEgresoDetalle().add(egresoDetalleDebe);					
				}
			}

			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());
			libroDiario.setStrGlosa(strGlosaEgreso);
			libroDiario.setTsFechaRegistro(MyUtil.obtenerFechaActual());
			libroDiario.setTsFechaDocumento(MyUtil.obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			BigDecimal bdLDMontoTotal = BigDecimal.ZERO;
			//Obtenemos el tipo de moneda de la cuenta bancaria
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			CuentaBancariaPK id = new CuentaBancariaPK();
			id.setIntIdPersona(bancoCuenta.getIntPersona());
			id.setIntIdCuentaBancaria(bancoCuenta.getIntCuentabancaria());
			CuentaBancaria ctaBco = personaFacade.getCuentaBancariaPorPK(id);
			Integer intMoneda = ctaBco!=null?ctaBco.getIntMonedaCod():Constante.PARAM_T_TIPOMONEDA_SOLES;
			//
			
			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					
					TipoCambio tipoCambioActual = null;
					Boolean esMonedaExtranjera = false;
					ModeloDetalle modeloDetalleUsar = obtenerModeloDetalle(eDI.getOrdenCompra(),intParaTipoDocumento);
					bdLDMontoTotal = bdLDMontoTotal.add(eDI.getBdSubTotal());
					LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
					libroDiarioDetalleDebe.setId(new LibroDiarioDetalleId());
					libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
					libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());				
					libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleUsar.getId().getIntPersEmpresaCuenta());
					libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalleUsar.getId().getIntContPeriodoCuenta());
					libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleUsar.getId().getStrContNumeroCuenta());
					libroDiarioDetalleDebe.setIntPersPersona(proveedor.getIntIdPersona());//PROVEEDOR
					libroDiarioDetalleDebe.setIntParaDocumentoGeneral(intParaTipoDocumento);//ADELANTO O GARANTIA
					libroDiarioDetalleDebe.setStrNumeroDocumento(eDI.getStrNroDocumento());
					libroDiarioDetalleDebe.setIntParaMonedaDocumento(intMoneda);
					
					if(!(libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES))){
						tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
						libroDiarioDetalleDebe.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
						esMonedaExtranjera = true;
					}
					
					libroDiarioDetalleDebe.setIntPersEmpresaSucursal(intIdEmpresa);
					libroDiarioDetalleDebe.setIntSucuIdSucursal(eDI.getSucursal().getId().getIntIdSucursal());
					libroDiarioDetalleDebe.setIntSudeIdSubSucursal(eDI.getSubsucursal().getId().getIntIdSubSucursal());		
					
					if(!esMonedaExtranjera){
						libroDiarioDetalleDebe.setBdDebeSoles(eDI.getBdSubTotal().abs());
					}else{
						libroDiarioDetalleDebe.setBdDebeExtranjero(eDI.getBdSubTotal().abs());
						libroDiarioDetalleDebe.setBdDebeSoles(libroDiarioDetalleDebe.getBdDebeExtranjero().multiply(tipoCambioActual.getBdPromedio()));
					}
					PlanCuentaId pId = new PlanCuentaId();
					pId.setIntEmpresaCuentaPk(modeloDetalleUsar.getId().getIntPersEmpresaCuenta());
					pId.setStrNumeroCuenta(modeloDetalleUsar.getId().getStrContNumeroCuenta());
					pId.setIntPeriodoCuenta(modeloDetalleUsar.getId().getIntContPeriodoCuenta());
					libroDiarioDetalleDebe.setStrComentario(planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().length()<20?planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion():planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().substring(0, 20));

					libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
				}
			}
			
			LibroDiarioDetalle libroDiarioDetalleBanco = generarLibroDiarioDetalleHaberPorTesoreria(bancoCuenta, bdLDMontoTotal, intMoneda, intTipoDocumentoValidar, usuario);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBanco);
			egreso.setBlnEsGiroPorSedeCentral(true);
			egreso.setLibroDiario(libroDiario);
//			egreso.setControlFondosFijos(controlFondosFijos);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	
	private LibroDiarioDetalle generarLibroDiarioDetalleHaberPorTesoreria(Bancocuenta bancoCuenta, BigDecimal bdMontoAGirar, Integer intMoneda, Integer intTipoDocumentoValidar, Usuario usuario) throws Exception{
//		BancoFacadeLocal bancoFacade =  (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
		Integer intIdEmpresa = bancoCuenta.getId().getIntEmpresaPk();
		TipoCambio tipoCambioActual = null;
//		Bancofondo bancoFondo = bancoFacade.getBancoFondoPorBancoCuenta(bancoCuenta);	
			
		boolean esMonedaExtranjera;
		LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
		libroDiarioDetalleHaber.setId(new LibroDiarioDetalleId());
		libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());				
		libroDiarioDetalleHaber.setIntPersEmpresaCuenta(bancoCuenta.getId().getIntEmpresaPk());
		libroDiarioDetalleHaber.setIntContPeriodo(bancoCuenta.getIntPeriodocuenta());
		libroDiarioDetalleHaber.setStrContNumeroCuenta(bancoCuenta.getStrNumerocuenta());
		libroDiarioDetalleHaber.setIntPersPersona(bancoCuenta.getIntPersona());
		libroDiarioDetalleHaber.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
		libroDiarioDetalleHaber.setStrSerieDocumento(null);
		libroDiarioDetalleHaber.setStrNumeroDocumento(null);
		libroDiarioDetalleHaber.setIntParaMonedaDocumento(intMoneda);
		
		if(!libroDiarioDetalleHaber.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
			libroDiarioDetalleHaber.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}else{
			esMonedaExtranjera = Boolean.FALSE;
		}
			
		libroDiarioDetalleHaber.setIntPersEmpresaSucursal(intIdEmpresa);
		libroDiarioDetalleHaber.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
		libroDiarioDetalleHaber.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());		

		if(!esMonedaExtranjera){
			libroDiarioDetalleHaber.setBdHaberSoles(bdMontoAGirar.abs());
		}else{
			libroDiarioDetalleHaber.setBdHaberExtranjero(bdMontoAGirar.abs());
			libroDiarioDetalleHaber.setBdHaberSoles(libroDiarioDetalleHaber.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
		}
		
		libroDiarioDetalleHaber.setStrComentario(bancoCuenta.getStrNombrecuenta());
				
		return libroDiarioDetalleHaber;
	}
	
	public Egreso grabarGiroOrdenCompraDocumentoPorCheque(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, Bancocuenta bancoCuenta, Usuario usuario, Integer intNroCheque, Integer intParaTipoDocumento, Integer intTipoDocumentoValidar)throws BusinessException{
		try{
			EgresoFacadeLocal egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			Egreso egreso = generarEgresoOrdenCompraDocumentoPorCheque(listaEgresoDetalleInterfaz, bancoCuenta, usuario, intNroCheque, intParaTipoDocumento, intTipoDocumentoValidar);
			
			log.info(egreso);
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info(egresoDetalle);
			}
			log.info(egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info(libroDiarioDetalle);
			}

			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);

			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					if(!eDI.isEsDiferencial()){
						OrdenCompra ordCmp = eDI.getOrdenCompra();
						for (OrdenCompraDocumento o : ordCmp.getListaOrdenCompraDocumento()) {
							o.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
							o.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
							boOrdenCompraDocumento.modificar(o);
						}						
					}
				}
			}
			return egreso;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}

	public Egreso generarEgresoOrdenCompraDocumentoPorCheque(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, Bancocuenta bancoCuenta, Usuario usuario, Integer intNroCheque, Integer intParaTipoDocumento, Integer intTipoDocumentoValidar)throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			
			Integer intIdEmpresa = bancoCuenta.getId().getIntEmpresaPk();
			Persona proveedor = listaEgresoDetalleInterfaz.get(0).getPersona();

			BigDecimal bdMontoTotal = new BigDecimal(0);
			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					bdMontoTotal = bdMontoTotal.add(eDI.getBdSubTotal());
				}
			}				
			String strGlosaEgreso = listaEgresoDetalleInterfaz.get(0).getOrdenCompra().getStrGlosaEgreso();

			egreso.setId(new EgresoId());
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			
			egreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			egreso.setTsFechaProceso(new Timestamp(new Date().getTime()));
			egreso.setDtFechaEgreso(new Timestamp(new Date().getTime()));
			
			//PERS_CUENTABANCARIAGIRADO_N: la cuenta bancaria del proveedor, solo para cheque, transferencia, Telecrédito.
			egreso.setIntPersCuentaBancariaGirado(null);
			
			egreso.setIntParaTipoFondoFijo(null);
			egreso.setIntItemPeriodoFondo(null);
			egreso.setIntItemFondoFijo(null);
			
			//COMO SE SI ES EFECTIVO O TRANSFERENCIA
//			if (controlFondosFijos.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)) {
//				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA);
//			}else{
				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_CHEQUE);
//			}

			egreso.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
			egreso.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
			
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(intNroCheque);
			egreso.setIntNumeroTransferencia(null);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(proveedor.getIntIdPersona());
			
			egreso.setIntCuentaGirado(null); //CSOC_CUENTAGIRADO_N: en blanco solo se utiliza para socios.
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			egreso.setIntPersPersonaApoderado(null);
			egreso.setIntPersEmpresaApoderado(null);
			egreso.setBdMontoTotal(bdMontoTotal);
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			egreso.setIntParaTipoApoderado(null);
			egreso.setIntItemArchivoApoderado(null);
			egreso.setIntItemHistoricoApoderado(null);			
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			egreso.setListaEgresoDetalle(new ArrayList<EgresoDetalle>());
			
			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					ModeloDetalle modeloDetalleUsar = obtenerModeloDetalle(eDI.getOrdenCompra(), intParaTipoDocumento);
					EgresoDetalle egresoDetalleDebe = new EgresoDetalle();		
					egresoDetalleDebe.getId().setIntPersEmpresaEgreso(intIdEmpresa);
					egresoDetalleDebe.setIntParaDocumentoGeneral(intParaTipoDocumento);//Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO);
					egresoDetalleDebe.setIntParaTipoComprobante(null);
					egresoDetalleDebe.setStrSerieDocumento(null);
					egresoDetalleDebe.setStrNumeroDocumento(eDI.getStrNroDocumento());
					egresoDetalleDebe.setStrDescripcionEgreso(modeloDetalleUsar.getPlanCuenta().getStrDescripcion());
					egresoDetalleDebe.setIntPersEmpresaGirado(intIdEmpresa);
					egresoDetalleDebe.setIntPersonaGirado(proveedor.getIntIdPersona());
					egresoDetalleDebe.setIntCuentaGirado(null);
					egresoDetalleDebe.setIntSucuIdSucursalEgreso(eDI.getSucursal().getId().getIntIdSucursal());
					egresoDetalleDebe.setIntSudeIdSubsucursalEgreso(eDI.getSubsucursal().getId().getIntIdSubSucursal());
					egresoDetalleDebe.setIntParaTipoMoneda(eDI.getIntTipoMoneda());

					egresoDetalleDebe.setBdMontoCargo(eDI.getBdSubTotal());
					egresoDetalleDebe.setBdMontoAbono(null);
					
					egresoDetalleDebe.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					egresoDetalleDebe.setTsFechaRegistro(MyUtil.obtenerFechaActual());
					egresoDetalleDebe.setIntPersEmpresaUsuario(intIdEmpresa);
					egresoDetalleDebe.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
					egresoDetalleDebe.setIntPersEmpresaLibroDestino(null);
					egresoDetalleDebe.setIntContPeriodoLibroDestino(null);
					egresoDetalleDebe.setIntContCodigoLibroDestino(null);
					egresoDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleUsar.getPlanCuenta().getId().getIntEmpresaCuentaPk());
					egresoDetalleDebe.setIntContPeriodoCuenta(modeloDetalleUsar.getPlanCuenta().getId().getIntPeriodoCuenta());
					egresoDetalleDebe.setStrContNumeroCuenta(modeloDetalleUsar.getPlanCuenta().getId().getStrNumeroCuenta());
					egresoDetalleDebe.setIntParaTipoFondoFijo(null);
					egresoDetalleDebe.setIntItemPeriodoFondo(null);
					egresoDetalleDebe.setIntSucuIdSucursal(null);
					egresoDetalleDebe.setIntItemFondoFijo(null);
					egreso.getListaEgresoDetalle().add(egresoDetalleDebe);					
				}
			}

			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());
			libroDiario.setStrGlosa(strGlosaEgreso);
			libroDiario.setTsFechaRegistro(MyUtil.obtenerFechaActual());
			libroDiario.setTsFechaDocumento(MyUtil.obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			BigDecimal bdLDMontoTotal = BigDecimal.ZERO;
			//Obtenemos el tipo de moneda de la cuenta bancaria
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			CuentaBancariaPK id = new CuentaBancariaPK();
			id.setIntIdPersona(bancoCuenta.getIntPersona());
			id.setIntIdCuentaBancaria(bancoCuenta.getIntCuentabancaria());
			CuentaBancaria ctaBco = personaFacade.getCuentaBancariaPorPK(id);
			Integer intMoneda = ctaBco!=null?ctaBco.getIntMonedaCod():Constante.PARAM_T_TIPOMONEDA_SOLES;
			//
			
			if (listaEgresoDetalleInterfaz!=null && !listaEgresoDetalleInterfaz.isEmpty()) {
				for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
					
					TipoCambio tipoCambioActual = null;
					Boolean esMonedaExtranjera = false;
					ModeloDetalle modeloDetalleUsar = obtenerModeloDetalle(eDI.getOrdenCompra(),intParaTipoDocumento);
					bdLDMontoTotal = bdLDMontoTotal.add(eDI.getBdSubTotal());
					LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
					libroDiarioDetalleDebe.setId(new LibroDiarioDetalleId());
					libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
					libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());				
					libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleUsar.getId().getIntPersEmpresaCuenta());
					libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalleUsar.getId().getIntContPeriodoCuenta());
					libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleUsar.getId().getStrContNumeroCuenta());
					libroDiarioDetalleDebe.setIntPersPersona(proveedor.getIntIdPersona());//PROVEEDOR
					libroDiarioDetalleDebe.setIntParaDocumentoGeneral(intParaTipoDocumento);//ADELANTO O GARANTIA
					libroDiarioDetalleDebe.setStrNumeroDocumento(eDI.getStrNroDocumento());
					libroDiarioDetalleDebe.setIntParaMonedaDocumento(intMoneda);
					
					if(!(libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES))){
						tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
						libroDiarioDetalleDebe.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
						esMonedaExtranjera = true;
					}
					
					libroDiarioDetalleDebe.setIntPersEmpresaSucursal(intIdEmpresa);
					libroDiarioDetalleDebe.setIntSucuIdSucursal(eDI.getSucursal().getId().getIntIdSucursal());
					libroDiarioDetalleDebe.setIntSudeIdSubSucursal(eDI.getSubsucursal().getId().getIntIdSubSucursal());		
					
					if(!esMonedaExtranjera){
						libroDiarioDetalleDebe.setBdDebeSoles(eDI.getBdSubTotal().abs());
					}else{
						libroDiarioDetalleDebe.setBdDebeExtranjero(eDI.getBdSubTotal().abs());
						libroDiarioDetalleDebe.setBdDebeSoles(libroDiarioDetalleDebe.getBdDebeExtranjero().multiply(tipoCambioActual.getBdPromedio()));
					}
					PlanCuentaId pId = new PlanCuentaId();
					pId.setIntEmpresaCuentaPk(modeloDetalleUsar.getId().getIntPersEmpresaCuenta());
					pId.setStrNumeroCuenta(modeloDetalleUsar.getId().getStrContNumeroCuenta());
					pId.setIntPeriodoCuenta(modeloDetalleUsar.getId().getIntContPeriodoCuenta());
					libroDiarioDetalleDebe.setStrComentario(planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().length()<20?planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion():planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().substring(0, 20));

					libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
				}
			}
			
			LibroDiarioDetalle libroDiarioDetalleBanco = generarLibroDiarioDetalleHaberPorTesoreria(bancoCuenta, bdLDMontoTotal, intMoneda, intTipoDocumentoValidar, usuario);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBanco);
			egreso.setBlnEsGiroPorSedeCentral(true);
			egreso.setLibroDiario(libroDiario);
//			egreso.setControlFondosFijos(controlFondosFijos);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}

}