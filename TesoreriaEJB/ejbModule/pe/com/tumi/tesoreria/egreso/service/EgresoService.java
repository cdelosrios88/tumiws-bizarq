package pe.com.tumi.tesoreria.egreso.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;
import pe.com.tumi.tesoreria.banco.domain.BancocuentachequeId;
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
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeLocal;

public class EgresoService {

	protected static Logger log = Logger.getLogger(EgresoService.class);
	
	ControlFondoFijoBO boControlFondosFijos = (ControlFondoFijoBO)TumiFactory.get(ControlFondoFijoBO.class);
	EgresoBO boEgreso = (EgresoBO)TumiFactory.get(EgresoBO.class);
	EgresoDetalleBO boEgresoDetalle = (EgresoDetalleBO)TumiFactory.get(EgresoDetalleBO.class);
	AccesoService accesoService = (AccesoService)TumiFactory.get(AccesoService.class);
	
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
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
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
			if(controlFondosFijosGirar != null){
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
}