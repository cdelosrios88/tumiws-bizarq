package pe.com.tumi.tesoreria.apertura.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.ConvertirLetras;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
//import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalle;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalleRes;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.BancocuentaId;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;
import pe.com.tumi.tesoreria.banco.domain.BancocuentachequeId;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.BancofondoId;
import pe.com.tumi.tesoreria.banco.domain.Fondodetalle;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
//import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;


public class AperturaController {

	protected static Logger log = Logger.getLogger(AperturaController.class);
	
	private EmpresaFacadeRemote 	empresaFacade;
	private PersonaFacadeRemote 	personaFacade;
	private TablaFacadeRemote 		tablaFacade;
	private PlanCuentaFacadeRemote 	planCuentaFacade;
	private BancoFacadeLocal 		bancoFacade;
	private EgresoFacadeLocal 		egresoFacade;
	private LibroDiarioFacadeRemote libroDiarioFacade;
	private CierreDiarioArqueoFacadeLocal 	cierreDiarioArqueoFacade;
	
	private List<ControlFondosFijos>	listaControlFondosFijos;
	private List<ControlFondosFijos>	listaControlFondosFijosRendicion;
	private	List<Sucursal>				listaSucursal;
	private	List<Tabla>					listaSucursalBus;
	private	List<Subsucursal>			listaSubsucursal;
	private	List<Tabla>					listaTipoDocumento;
	private	List<Bancocuenta>			listaBancoCuenta;
	private	List<Bancocuentacheque>		listaBancoCuentaCheque;
	private	List<Tabla>					listaMoneda;
	private List<AccesoDetalleRes>		listaAccesoDetalleRes;
	private	List<Tabla>					listaTipoFondoFijo;
	private	List<Tabla>					listaTipoFondoFijoRendicion;
	
	private	Acceso				accesoValidar;
	private	ControlFondosFijos	controlFondosFijosNuevo;
	private	ControlFondosFijos	controlFondosFijosFiltro;
	private	ControlFondosFijos	controlFondosFijosRendicion;
	private Sucursal			sucursalSedeCentral;
	private	Subsucursal			subsucursalSedeCentral;
	private	Bancofondo			bancoFondoHermes;
	private	Bancocuenta			bancoCuentaHermes;
	private Bancofondo			fondoTelecredito;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private String 		mensajePopUp;
	private	Integer		intTipoFondoFijoValidar;
	private	Integer		intTipoDocumentoValidar;
	private	Integer		intTipoMonedaValidar;
	private	Integer		intBancoSeleccionado;
	private	Integer		intBancoCuentaSeleccionado;
	private	Integer		intChequeSeleccionado;
	private	String		strObservacion;
	private	Integer		intNumeroCheque;
	private	Date		dtFechaActual;
	private	Integer		intTipoFondoFijoRendicion;
	private Integer		intControlSeleccionado;
	private Integer		intTipoMoneda;
	
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	private Integer		SUCURSAL_USUARIO;
	private Integer		SUBSUCURSAL_USUARIO;
	private Integer		ID_SUCURSAL_SEDECENTRAL = 59;
	private	Integer		ID_SUBSUCURSAL_SEDECENTRAL = 68;
	private	String		HERMES_RUC = "20100077044";
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean	mostrarMensajePopUp;
	private boolean habilitarEditarPopUp;
	private boolean datosValidados;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 23.08.2014 /
//	private	CuentaBancaria	cuentaBancariaSeleccionada;
//	private	Persona 		personaSeleccionada;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 25.08.2014 /
//	private List<Tabla> listaTablaTipoMoneda;
//	private List<Tabla> listaTablaTipoCuentaBancaria;
	
	
	public AperturaController(){
		cargarUsuario();
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public String getInicioPage() {
		cargarUsuario();
		if(usuario!=null){
			limpiarFormulario();
			listaControlFondosFijos.clear();
			deshabilitarPanelInferior(null);
		}else log.error("--Usuario obtenido es NULL.");
		return "";
	}
	
	private void limpiarFormulario(){
		controlFondosFijosFiltro.getId().setIntSucuIdSucursal(0);
		controlFondosFijosFiltro.getId().setIntParaTipoFondoFijo(0);
//		cuentaBancariaSeleccionada = null;
		ocultarMensaje();
	}

	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
		SUCURSAL_USUARIO = usuario.getSucursal().getId().getIntIdSucursal();
		SUBSUCURSAL_USUARIO = usuario.getSubSucursal().getId().getIntIdSubSucursal();
	}
	
	private void cargarValoresIniciales(){
		try{
//			PERSONA_USUARIO = usuario.getIntPersPersonaPk();
//			EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
//			SUCURSAL_USUARIO = usuario.getSucursal().getId().getIntIdSucursal();
//			SUBSUCURSAL_USUARIO = usuario.getSubSucursal().getId().getIntIdSubSucursal();
			
			listaControlFondosFijos = new ArrayList<ControlFondosFijos>();
			listaAccesoDetalleRes = new ArrayList<AccesoDetalleRes>();
			controlFondosFijosFiltro = new ControlFondosFijos();
			controlFondosFijosFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			controlFondosFijosFiltro.getId().setIntParaTipoFondoFijo(Constante.PARAM_T_TIPOFONDOFIJO_CAJA); 
			listaControlFondosFijosRendicion = new ArrayList<ControlFondosFijos>();
			listaBancoCuentaCheque = new ArrayList<Bancocuentacheque>();
			
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			cierreDiarioArqueoFacade = (CierreDiarioArqueoFacadeLocal) EJBFactory.getLocal(CierreDiarioArqueoFacadeLocal.class);
			
			listaTipoDocumento = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "A");
			listaMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			listaTipoFondoFijo = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOFONDOFIJO), "A");
			listaTipoFondoFijoRendicion = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOFONDOFIJO), "B");			
			//Autor: jchavez / Tarea: Creación / Fecha: 25.08.2014 /
//			listaTablaTipoCuentaBancaria = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCUENTABANCARIA));
//			listaTablaTipoMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			//fin jchavez - 25.08.2014
			listaSubsucursal = new ArrayList<Subsucursal>();
			cargarListaSucursal();
			cargarListaSucursalBus();
			cargarSedeCentral();
			cargarDatosHermes();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	private void cargarListaSucursalBus() throws NumberFormatException, BusinessException{
		listaSucursalBus = new ArrayList<Tabla>();
		listaSucursalBus = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
		Sucursal sucursal;
		Tabla tabla;
		for(Object o : listaSucursal){
			 sucursal = (Sucursal)o;
			 tabla = new Tabla();
			 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
			 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
			 listaSucursalBus.add(tabla);
		}
	}
	
	private void cargarListaSucursal() throws Exception{
		listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
		List<Sucursal> listaSucursalTemp = listaSucursal;
		//Ordenamos por nombre
		Collections.sort(listaSucursalTemp, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});		
	}
	
	private void cargarSedeCentral() throws Exception{
		sucursalSedeCentral = obtenerSucursal(ID_SUCURSAL_SEDECENTRAL);
		listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(ID_SUCURSAL_SEDECENTRAL);
		subsucursalSedeCentral = obtenerSubsucursal(ID_SUBSUCURSAL_SEDECENTRAL);		
	}
	
	
	public void seleccionarSucursal(){
		try{
			if(accesoValidar.getIntSucuIdSucursal().equals(new Integer(0))){
				return;
			}
			listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(accesoValidar.getIntSucuIdSucursal());
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void aperturar(ActionEvent event){
		try{
			ControlFondosFijos controlFondosFijos = (ControlFondosFijos)event.getComponent().getAttributes().get("item");
			
			if(controlFondosFijos.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_CERRADO)){
				habilitarPanelInferior(event);
				
				Egreso egreso = egresoFacade.getEgresoPorControlFondosFijos(controlFondosFijos);
				EgresoDetalle egresoDetalle = egresoFacade.getListaEgresoDetallePorEgreso(egreso).get(0);

//				intTipoFondoFijoValidar = controlFondosFijos.getId().getIntItemFondoFijo();
				//Modificado 17.12.2013 Se debe mostrar el tipo de Fondo Fijo obtenido del registro seleccionado
				intTipoFondoFijoValidar = controlFondosFijos.getId().getIntParaTipoFondoFijo();
				accesoValidar.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
				accesoValidar.setIntSudeIdSubsucursal(controlFondosFijos.getIntSudeIdSubsucursal());
				intTipoMonedaValidar = egresoDetalle.getIntParaTipoMoneda();
				intTipoDocumentoValidar = 0;
				
				seleccionarSucursal();
			}			
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			dtFechaActual = new Date();
			accesoValidar = new Acceso();
			datosValidados = Boolean.FALSE;
			listaSubsucursal.clear();
			intTipoMonedaValidar = Constante.PARAM_T_TIPOMONEDA_SOLES;
			intTipoFondoFijoValidar = null;
			intTipoDocumentoValidar = null;
			intNumeroCheque = null;
			fondoTelecredito = null;
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			
//			cuentaBancariaSeleccionada = null;
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}
	
	public void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
	public void mostrarMensaje(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExito = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mensajeOperacion = mensaje;
		}else{
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.TRUE;
			mensajeOperacion = mensaje;
		}
	}
	
//	public void seleccionarRegistro(ActionEvent event){
//		try{
//					
//		}catch (Exception e) {
//			log.error(e.getMessage(),e);
//		}
//	}
	
//	private void cargarRegistro() throws BusinessException{
//
//	}
	
	public void modificarRegistro(){
		try{
			log.info("--modificarRegistro");	
//			cargarRegistro();
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			ocultarMensaje();
			datosValidados = Boolean.TRUE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void buscar(){
		try{
			if(controlFondosFijosFiltro.getId().getIntSucuIdSucursal().equals(new Integer(0))){
				//mostrarMensaje(Boolean.FALSE, "Debe seleccionar una sucursal");
				return;
			}else{
				ocultarMensaje();
			}
			
			listaControlFondosFijos = egresoFacade.buscarApertura(controlFondosFijosFiltro, listaSucursal);
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}


	public void cargarDatosHermes(){
		try{
			Persona personaHermes = personaFacade.getPersona(HERMES_RUC);
			bancoFondoHermes = (bancoFacade.getBancoFondoPorEmpresayPersonaBanco(EMPRESA_USUARIO, personaHermes.getIntIdPersona())).get(0);
			
			bancoCuentaHermes = (bancoFacade.getBancoCuentaPorBancoFondo(bancoFondoHermes)).get(0);
			
			String strEtiqueta = bancoCuentaHermes.getStrNombrecuenta()+" - "
			+bancoCuentaHermes.getCuentaBancaria().getStrNroCuentaBancaria()+" - "
			+obtenerEtiquetaMoneda(bancoCuentaHermes.getCuentaBancaria().getIntMonedaCod());
			
			bancoCuentaHermes.setStrEtiqueta(strEtiqueta);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private Bancocuentacheque obtenerBancoCuentaChequeSeleccionado(){
		for(Bancocuentacheque bancoCuentaCheque : listaBancoCuentaCheque){
//			Bancocuentacheque bancoCuentaCheque = (Bancocuentacheque)bancoCuentaCheque;
			if(bancoCuentaCheque.getId().getIntItembancuencheque().equals(intChequeSeleccionado)){
				return bancoCuentaCheque;
			}
		}
		return null;
	}
	
	private boolean validarNumeroCheque(){
		Bancocuentacheque bancoCuentaCheque = obtenerBancoCuentaChequeSeleccionado();
		if(bancoCuentaCheque.getIntNumerofin().equals(bancoCuentaCheque.getIntNumeroinicio())){
			return Boolean.FALSE; 
		}
		
		return Boolean.TRUE;
	}
	
	public void grabar(){
		log.info("--grabar");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;	
		try {
			if(cierreDiarioArqueoFacade.existeCierreDiarioArqueo(controlFondosFijosNuevo.getId().getIntPersEmpresa(), controlFondosFijosNuevo.getId().getIntSucuIdSucursal(), null)){
				//valida si la caja asociada a la sucursal se encuentra cerrada
				if(!cierreDiarioArqueoFacade.existeCierreDiaAnterior(controlFondosFijosNuevo)){
					mensaje = "La caja de la sucursal no se ha cerrado el dia anterior.";
					return;
				}
				if(cierreDiarioArqueoFacade.existeCierreDiaActual(controlFondosFijosNuevo)){
					mensaje = "La caja de la sucursal ya se ha cerrado para el dia de hoy.";
					return;
				}
			}				
			
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA) 
			|| intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
				if(intBancoSeleccionado.equals(new Integer(0))){
					mensaje = "Debe seleccionar un banco.";
					return;
				}
				if(intBancoCuentaSeleccionado.equals(new Integer(0))){
					mensaje = "Debe seleccionar una cuenta bancaria.";
					return;
				}
			}
			
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
				if(intChequeSeleccionado.equals(new Integer(0))){
					mensaje = "Debe seleccionar un cheque.";
					return;
				}
				if(!validarNumeroCheque()){
					mensaje = "El cheque que ha seleccionado ha superado el número máximo de usos.";
					return;
				}
			}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA)){
				if(intNumeroCheque==null  || intNumeroCheque.equals(new Integer(0))){
					mensaje = "Debe ingresar un numero de cheque.";
					return;
				}
			}
			
			if (controlFondosFijosNuevo.getBdMontoGirado().compareTo(BigDecimal.ZERO)<0) {
				mensaje = "Error en el proceso de apertura de fondos. El Monto Girado no debe ser negativo.";
				return;
			}
			
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)){
				if(intTipoFondoFijoRendicion.equals(new Integer(0))){
					mensaje = "Debe seleccionar un tipo de fondo fijo.";return;	
				}
				if(intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){
					controlFondosFijosRendicion = obtenerControlSeleccionado();
					if(intControlSeleccionado.equals(new Integer(0))){
						mensaje = "Debe seleccionar un control de fondo fijo.";	return;	
					}
					//Autor: jchavez / Tarea: Se quita el ! de la validacion if / Fecha: 30.09.2014
					if(controlFondosFijosRendicion.getBdMontoSaldo().compareTo(controlFondosFijosNuevo.getBdMontoGirado())<0){
						mensaje = "El control de fondo fijo seleccionado no posee suficiente saldo para girar.";return;
					}//Fin jchavez - 30.09.2014
				}else if(intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)){
					fondoTelecredito = obtenerFondoTelecredito();
					if(fondoTelecredito == null){
						mensaje = "Existe un problema con el fondo de telecrédito, puede que no este configurado correctamente.";return;
					}
					if(intNumeroCheque==null  || intNumeroCheque.equals(new Integer(0))){
						mensaje = "Debe ingresar un numero de transferencia.";
						return;
					}
//					if (cuentaBancariaSeleccionada == null) {
//						mensaje = "Debe de seleccionar la cuenta bancaria del responsable.";return;
//					}
				}
				
			}
			//Autor: jchavez / Tarea: Se agrea validacion en transferencia a terceros / Fecha: 01.10.2014
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
				if(intNumeroCheque==null  || intNumeroCheque.equals(new Integer(0))){
					mensaje = "Debe ingresar un numero de transferencia.";
					return;
				}
			}
			
			if(strObservacion==null  || strObservacion.isEmpty()){
				mensaje = "Debe ingresar una observación (ira a la glosa del asiento).";
				return;
			}
			//fin validaciones
			
			if(registrarNuevo){
				log.info("--registrar");
				
				Egreso egresoApertura = procesarApertura(controlFondosFijosNuevo);
				log.info(controlFondosFijosNuevo);
				
				if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA) 
				|| intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)
				|| intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
					controlFondosFijosNuevo = egresoFacade.grabarAperturaFondoCheque(egresoApertura);
				
				}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)
					&& intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){
					controlFondosFijosNuevo = egresoFacade.grabarAperturaFondoRendicion(egresoApertura, controlFondosFijosRendicion);				
				
				}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)
					&& intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)){
					
					egresoFacade.grabarAperturaFondoRendicion(egresoApertura, null);
					controlFondosFijosNuevo.setEgreso(egresoApertura);
					/*Egreso egreso = controlFondosFijosNuevo.getEgreso();
					log.info(egreso);
					for(EgresoDetalle egd : egreso.getListaEgresoDetalle()){
						log.info(egd);
					}
					LibroDiario lb = egreso.getLibroDiario();
					log.info(lb);
					for(LibroDiarioDetalle lbd : lb.getListaLibroDiarioDetalle()){
						log.info(lbd);
					}*/
				}
				
				mensaje = "Se registró correctamente la apertura de fondos.";
			}
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			controlFondosFijosNuevo.setStrMontoLetras(ConvertirLetras.convertirMontoALetras(controlFondosFijosNuevo.getBdMontoGirado(),intTipoMoneda));
			buscar();
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el proceso de apertura de fondos.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	private Bancofondo obtenerFondoTelecredito(){
		try{
			fondoTelecredito = new Bancofondo();
			fondoTelecredito.getId().setIntEmpresaPk(EMPRESA_USUARIO);
			fondoTelecredito.setIntTipoFondoFijo(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO);
			fondoTelecredito.setIntMonedaCod(intTipoMonedaValidar);
			fondoTelecredito = bancoFacade.getBancoFondoPorTipoFondoFijoYMoneda(fondoTelecredito);
			fondoTelecredito.setListaFondodetalle(bancoFacade.getListaFondoDetallePorBancoFondo(fondoTelecredito));
			fondoTelecredito.setFondoDetalleUsar(bancoFacade.obtenerFondoDetalleContable(fondoTelecredito, 
												usuario.getSucursal(), usuario.getSubSucursal()));
			
			
			return fondoTelecredito;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	
	private PlanCuenta obtenerPlanCuentaOrigen() throws Exception{
		PlanCuenta planCuenta = new PlanCuenta();
		planCuenta.setId(new PlanCuentaId());
		
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA)){
			Bancocuenta bancoCuenta = obtenerBancoCuentaSeleccionado();
			planCuenta.getId().setIntEmpresaCuentaPk(bancoCuenta.getIntEmpresacuentaPk());
			planCuenta.getId().setIntPeriodoCuenta(bancoCuenta.getIntPeriodocuenta());
			planCuenta.getId().setStrNumeroCuenta(bancoCuenta.getStrNumerocuenta());
		
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
			Bancocuentacheque bancocuentacheque = obtenerBancoCuentaChequeSeleccionado();
			planCuenta.getId().setIntEmpresaCuentaPk(bancocuentacheque.getIntEmpresacuentaPk());
			planCuenta.getId().setIntPeriodoCuenta(bancocuentacheque.getIntPeriodocuenta());
			planCuenta.getId().setStrNumeroCuenta(bancocuentacheque.getBancoCuenta().getStrNumerocuenta());
		
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)
			&& intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){
			Egreso egreso = controlFondosFijosRendicion.getEgreso();
			if(egreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA)){
				Bancocuenta bancoCuenta = obtenerBancoCuentaPorEgreso(egreso);			
				planCuenta.getId().setIntEmpresaCuentaPk(bancoCuenta.getIntEmpresacuentaPk());
				planCuenta.getId().setIntPeriodoCuenta(bancoCuenta.getIntPeriodocuenta());
				planCuenta.getId().setStrNumeroCuenta(bancoCuenta.getStrNumerocuenta());
			}else if(egreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
				Bancocuentacheque bancocuentacheque = obtenerBancoCuentaChequePorEgreso(egreso);			
				planCuenta.getId().setIntEmpresaCuentaPk(bancocuentacheque.getIntEmpresacuentaPk());
				planCuenta.getId().setIntPeriodoCuenta(bancocuentacheque.getIntPeriodocuenta());
				planCuenta.getId().setStrNumeroCuenta(bancocuentacheque.getStrNumerocuenta());
			}else if(egreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)){
				LibroDiarioId libroDiarioId = new LibroDiarioId();
				libroDiarioId.setIntPersEmpresaLibro(egreso.getIntPersEmpresaLibro());
				libroDiarioId.setIntContPeriodoLibro(egreso.getIntContPeriodoLibro());
				libroDiarioId.setIntContCodigoLibro(egreso.getIntContCodigoLibro());
				LibroDiario libroDiario = libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);
				List<LibroDiarioDetalle> listaLibroDiarioDetalle = libroDiarioFacade.getListaLibroDiarioDetallePorLibroDiario(libroDiario);
				for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle){
					if(libroDiarioDetalle.getBdDebeSoles()!=null || libroDiarioDetalle.getBdDebeExtranjero()!=null){
						planCuenta.getId().setIntEmpresaCuentaPk(libroDiarioDetalle.getIntPersEmpresaCuenta());
						planCuenta.getId().setIntPeriodoCuenta(libroDiarioDetalle.getIntContPeriodo());
						planCuenta.getId().setStrNumeroCuenta(libroDiarioDetalle.getStrContNumeroCuenta());
					}
				}
			}		
		
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)
			&& intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)){
			planCuenta = fondoTelecredito.getFondoDetalleUsar().getPlanCuenta();			
		
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
			planCuenta.getId().setIntEmpresaCuentaPk(bancoCuentaHermes.getIntEmpresacuentaPk());
			planCuenta.getId().setIntPeriodoCuenta(bancoCuentaHermes.getIntPeriodocuenta());
			planCuenta.getId().setStrNumeroCuenta(bancoCuentaHermes.getStrNumerocuenta());		
		}
		
		planCuenta = planCuentaFacade.getPlanCuentaPorPk(planCuenta.getId());
		
		return planCuenta;
	}	
	
	private Bancocuentacheque obtenerBancoCuentaChequePorEgreso(Egreso egreso) throws Exception{
		BancocuentachequeId bancoCuentaChequeId = new BancocuentachequeId();
		bancoCuentaChequeId.setIntEmpresaPk(egreso.getId().getIntPersEmpresaEgreso());
		bancoCuentaChequeId.setIntItembancofondo(egreso.getIntItemBancoFondo());
		bancoCuentaChequeId.setIntItembancocuenta(egreso.getIntItemBancoCuenta());
		bancoCuentaChequeId.setIntItembancuencheque(egreso.getIntItemBancoCuentaCheque());
		
		Bancocuentacheque bancoCuentaCheque = bancoFacade.getBancoCuentaChequePorId(bancoCuentaChequeId);
		
		return bancoCuentaCheque;
	}
	
	
	private Bancocuenta obtenerBancoCuentaPorEgreso(Egreso egreso) throws Exception{
		BancocuentaId bancoCuentaId = new BancocuentaId();
		bancoCuentaId.setIntEmpresaPk(egreso.getId().getIntPersEmpresaEgreso());
		bancoCuentaId.setIntItembancofondo(egreso.getIntItemBancoFondo());
		bancoCuentaId.setIntItembancocuenta(egreso.getIntItemBancoCuenta());
		
		Bancocuenta bancoCuenta = bancoFacade.getBancoCuentaPorId(bancoCuentaId);
		
		return bancoCuenta;
	}
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	private Bancofondo obtenerBancoFondoOrigen() throws Exception{		
		Bancofondo bancoFondo = null;
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA) 
		|| intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
			Bancocuenta bancoCuentaSeleccionado = obtenerBancoCuentaSeleccionado();
			BancofondoId bancoFondoId = new BancofondoId();
			bancoFondoId.setIntEmpresaPk(bancoCuentaSeleccionado.getId().getIntEmpresaPk());
			bancoFondoId.setIntItembancofondo(bancoCuentaSeleccionado.getId().getIntItembancofondo());			
			bancoFondo = bancoFacade.getBancoFondoPorId(bancoFondoId);
			
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)
			&& intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){			
			Egreso egreso = controlFondosFijosRendicion.getEgreso();
			if(egreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA)){
				Bancocuenta bancoCuenta = obtenerBancoCuentaPorEgreso(egreso);
				bancoFondo = bancoFacade.getBancoFondoPorBancoCuenta(bancoCuenta);
			}else if(egreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
				Bancocuentacheque bancoCuentaCheque = obtenerBancoCuentaChequePorEgreso(egreso);
				bancoFondo = bancoFacade.getBancoFondoPorBancoCuentaCheque(bancoCuentaCheque);
			}				
						
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)
			&& intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)){
			bancoFondo = fondoTelecredito;
		
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
			bancoFondo = bancoFondoHermes;
		}
		
		log.info(bancoFondo);
		return bancoFondo;
	}
	
	private Sucursal obtenerSucursalOrigen() throws Exception{
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA) 
		|| intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
			return sucursalSedeCentral;
		
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)){
			return obtenerSucursal(SUCURSAL_USUARIO);
		
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
			return sucursalSedeCentral;
		}
		return null;
	}
	
	private Subsucursal obtenerSubsucursalOrigen() throws Exception{
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA) 
		|| intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
			return subsucursalSedeCentral;
		
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)){
			listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(SUCURSAL_USUARIO);
			return obtenerSubsucursal(SUBSUCURSAL_USUARIO);
		
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
			return subsucursalSedeCentral;
		}
		return null;
	}	
	
	private Egreso procesarApertura(ControlFondosFijos controlFondosFijos) throws Exception{
		Bancocuenta bancoCuentaSeleccionado = null;
		controlFondosFijosRendicion = null;
		log.info("intTipoDocumentoValidar:"+intTipoDocumentoValidar);
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA) 
		|| intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
			bancoCuentaSeleccionado = obtenerBancoCuentaSeleccionado();
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION) 
			&& intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){
			controlFondosFijosRendicion = obtenerControlSeleccionado();
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
			bancoCuentaSeleccionado = bancoCuentaHermes;
		}
		
		AccesoDetalle accesoDetalle = obtenerAccesoDetallePorTipoFondoFijo(controlFondosFijos.getAcceso());		
		Bancofondo fondoFijo = accesoDetalle.getFondoFijo();
		Fondodetalle fondoDetalle = obtenerFondoDetalle(accesoDetalle.getFondoFijo(), accesoValidar);
		PlanCuenta planCuentaDestino = obtenerPlanCuentaDestino(fondoDetalle);
		PlanCuenta planCuentaOrigen = obtenerPlanCuentaOrigen();
		Sucursal sucursalDestino = accesoValidar.getSucursal();
		Subsucursal subsucursalDestino = accesoValidar.getSubsucursal();
		Sucursal sucursalOrigen = obtenerSucursalOrigen();
		Subsucursal subsucursalOrigen = obtenerSubsucursalOrigen();
		Bancofondo bancoFondoOrigen = obtenerBancoFondoOrigen();
		
		
		Egreso egreso = new Egreso();
		egreso.getId().setIntPersEmpresaEgreso(EMPRESA_USUARIO);
		egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
		
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA) 
				|| intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_CHEQUE);
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA);
		}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)){
			if (intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)) {
				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			}else if (intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)) {
				egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA);
			}			
		}
				
		egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
//		egreso.setIntParaDocumentoGeneral(obtenerDocumentoGeneralTipoFondo());
		egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
		egreso.setIntItemEgreso(null);
		egreso.setIntSucuIdSucursal(sucursalOrigen.getId().getIntIdSucursal());
		egreso.setIntSudeIdSubsucursal(subsucursalOrigen.getId().getIntIdSubSucursal());
		egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_APERTURA);
		egreso.setTsFechaProceso(obtenerFechaActual());
		egreso.setDtFechaEgreso(obtenerFechaActual());
		
		
		//JCHAVEZ 23.12.2013
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION) 
		&& intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){
			egreso.setIntParaTipoFondoFijo(controlFondosFijosRendicion.getId().getIntParaTipoFondoFijo());
			egreso.setIntItemPeriodoFondo(controlFondosFijosRendicion.getId().getIntItemPeriodoFondo());
			egreso.setIntItemFondoFijo(controlFondosFijosRendicion.getId().getIntItemFondoFijo());
		}else{
			egreso.setIntParaTipoFondoFijo(null);
			egreso.setIntItemPeriodoFondo(null);
			egreso.setIntItemFondoFijo(null);
		}
		
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA) 
		|| intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)
		|| intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
			egreso.setIntItemBancoFondo(bancoCuentaSeleccionado.getId().getIntItembancofondo());
			egreso.setIntItemBancoCuenta(bancoCuentaSeleccionado.getId().getIntItembancocuenta());
		}else{
			egreso.setIntItemBancoFondo(null);
			egreso.setIntItemBancoCuenta(null);
		}
		
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
			egreso.setIntItemBancoCuentaCheque(intChequeSeleccionado);
		}
		egreso.setIntNumeroPlanilla(null);
		egreso.setIntNumeroCheque(intNumeroCheque);
		egreso.setIntNumeroTransferencia(null);
		egreso.setTsFechaPagoDiferido(null);
		egreso.setIntPersEmpresaGirado(sucursalDestino.getId().getIntPersEmpresaPk());
		egreso.setIntPersPersonaGirado(sucursalDestino.getIntPersPersonaPk());
		egreso.setIntCuentaGirado(null);

		//Autor: jchavez / Tarea: Creación / Fecha: 25.08.2014 /
		//Autor: jchavez / Tarea: Modificación / Fecha: 25.08.2014 / Se vuelve a su estado original - OBS. TESORERIA
//		if(intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)){
//			//Autor: jchavez / Tarea: Modificación / Fecha: 20.05.2014 / Cambio de tipo de dato.
//			egreso.setStrPersCuentaBancariaGirado(cuentaBancariaSeleccionada.getStrNroCuentaBancaria());
//		}else {
//			//Autor: jchavez / Tarea: Modificación / Fecha: 20.05.2014 / Cambio de tipo de dato.
//			egreso.setStrPersCuentaBancariaGirado(null);
//		}
		
		//Autor jchavez / Tarea: Se regresa al tipo de dato integer y se graba la llave de la cuenta / Fecha: 19.09.2014
//		egreso.setStrPersCuentaBancariaGirado(null);
		egreso.setIntPersCuentaBancariaGirado(null);
		//Fin jchavez - 19.09.2014
		
		
		egreso.setIntPersEmpresaBeneficiario(null);
		egreso.setIntPersPersonaBeneficiario(null);
		egreso.setIntPersCuentaBancariaBeneficiario(null);
		egreso.setIntPersPersonaApoderado(null);
		egreso.setIntPersEmpresaApoderado(null);		
		egreso.setBdMontoTotal(controlFondosFijos.getBdMontoGirado());
		egreso.setStrObservacion(strObservacion);
		egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egreso.setTsFechaRegistro(obtenerFechaActual());
		egreso.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
		egreso.setIntPersPersonaUsuario(PERSONA_USUARIO);
		
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();
		egresoDetalle.getId().setIntPersEmpresaEgreso(EMPRESA_USUARIO);
		
		egresoDetalle.setIntParaDocumentoGeneral(obtenerDocumentoGeneralTipoFondo());
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		//
		egresoDetalle.setStrNumeroDocumento(""+obtenerPeriodoActual());
		egresoDetalle.setStrDescripcionEgreso(planCuentaDestino.getStrDescripcion());
		egresoDetalle.setIntPersEmpresaGirado(sucursalDestino.getId().getIntPersEmpresaPk());
		egresoDetalle.setIntPersonaGirado(sucursalDestino.getIntPersPersonaPk());
		egresoDetalle.setIntCuentaGirado(null);
		egresoDetalle.setIntSucuIdSucursalEgreso(sucursalDestino.getId().getIntIdSucursal());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(subsucursalDestino.getId().getIntIdSubSucursal());
		egresoDetalle.setIntParaTipoMoneda(fondoFijo.getIntMonedaCod());
		egresoDetalle.setBdMontoDiferencial(null);
		egresoDetalle.setBdMontoCargo(controlFondosFijos.getBdMontoGirado());
		egresoDetalle.setBdMontoAbono(null);
		egresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egresoDetalle.setTsFechaRegistro(obtenerFechaActual());
		egresoDetalle.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
		egresoDetalle.setIntPersPersonaUsuario(PERSONA_USUARIO);
		egresoDetalle.setIntPersEmpresaLibroDestino(null);
		egresoDetalle.setIntContPeriodoLibroDestino(null);
		egresoDetalle.setIntContCodigoLibroDestino(null);
		egresoDetalle.setIntPersEmpresaCuenta(planCuentaDestino.getId().getIntEmpresaCuentaPk());
		egresoDetalle.setIntContPeriodoCuenta(planCuentaDestino.getId().getIntPeriodoCuenta());
		egresoDetalle.setStrContNumeroCuenta(planCuentaDestino.getId().getStrNumeroCuenta());
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION) 
		&& intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){
			egresoDetalle.setIntParaTipoFondoFijo(controlFondosFijosRendicion.getId().getIntParaTipoFondoFijo());
			egresoDetalle.setIntItemPeriodoFondo(controlFondosFijosRendicion.getId().getIntItemPeriodoFondo());
			egresoDetalle.setIntSucuIdSucursal(controlFondosFijosRendicion.getId().getIntSucuIdSucursal());
			egresoDetalle.setIntItemFondoFijo(controlFondosFijosRendicion.getId().getIntItemFondoFijo());
		}else{
			egresoDetalle.setIntParaTipoFondoFijo(null);
			egresoDetalle.setIntItemPeriodoFondo(null);
			egresoDetalle.setIntSucuIdSucursal(null);
			egresoDetalle.setIntItemFondoFijo(null);
		}
		
		
		
		LibroDiario libroDiario = new LibroDiario();
		libroDiario.setId(new LibroDiarioId());
		libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
		libroDiario.getId().setIntPersEmpresaLibro(EMPRESA_USUARIO);
		libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		libroDiario.setIntParaTipoDocumentoGeneral(intTipoDocumentoValidar);
//		libroDiario.setIntParaTipoDocumentoGeneral(obtenerDocumentoGeneralTipoFondo());
		libroDiario.setStrGlosa(strObservacion);
		libroDiario.setTsFechaRegistro(obtenerFechaActual());
		libroDiario.setTsFechaDocumento(obtenerFechaActual());
		libroDiario.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
		libroDiario.setIntPersPersonaUsuario(PERSONA_USUARIO);
		libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);		
		
		
		LibroDiarioDetalle libroDiarioDetalleEgreso = new LibroDiarioDetalle();
		libroDiarioDetalleEgreso.setId(new LibroDiarioDetalleId());
		libroDiarioDetalleEgreso.getId().setIntPersEmpresaLibro(EMPRESA_USUARIO);
		libroDiarioDetalleEgreso.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		log.info("cadena real: "+planCuentaOrigen.getStrDescripcion());
		log.info("cadena partida: "+(planCuentaOrigen.getStrDescripcion().length()<=20?planCuentaOrigen.getStrDescripcion():planCuentaOrigen.getStrDescripcion().substring(0, 19)));
		libroDiarioDetalleEgreso.setStrComentario(planCuentaOrigen.getStrDescripcion().length()<=20?planCuentaOrigen.getStrDescripcion():planCuentaOrigen.getStrDescripcion().substring(0, 19));
		libroDiarioDetalleEgreso.setIntPersEmpresaCuenta(planCuentaOrigen.getId().getIntEmpresaCuentaPk());
		libroDiarioDetalleEgreso.setIntContPeriodo(planCuentaOrigen.getId().getIntPeriodoCuenta());
		libroDiarioDetalleEgreso.setStrContNumeroCuenta(planCuentaOrigen.getId().getStrNumeroCuenta());
		libroDiarioDetalleEgreso.setIntPersPersona(bancoFondoOrigen.getIntPersonabancoPk());
		libroDiarioDetalleEgreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
//		libroDiarioDetalleEgreso.setIntParaDocumentoGeneral(obtenerDocumentoGeneralTipoFondo());		
		libroDiarioDetalleEgreso.setStrSerieDocumento(null);
		libroDiarioDetalleEgreso.setStrNumeroDocumento(null);
		libroDiarioDetalleEgreso.setIntPersEmpresaSucursal(sucursalOrigen.getId().getIntPersEmpresaPk());
		libroDiarioDetalleEgreso.setIntSucuIdSucursal(sucursalOrigen.getId().getIntIdSucursal());
		libroDiarioDetalleEgreso.setIntSudeIdSubSucursal(subsucursalOrigen.getId().getIntIdSubSucursal());
		libroDiarioDetalleEgreso.setIntParaMonedaDocumento(fondoFijo.getIntMonedaCod());
		if(fondoFijo.getIntMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			libroDiarioDetalleEgreso.setBdHaberSoles(controlFondosFijos.getBdMontoGirado());
			libroDiarioDetalleEgreso.setBdDebeSoles(null);			
		}else{
			libroDiarioDetalleEgreso.setBdHaberExtranjero(controlFondosFijos.getBdMontoGirado());
			libroDiarioDetalleEgreso.setBdDebeExtranjero(null);
		}
		
		LibroDiarioDetalle libroDiarioDetalleIngreso = new LibroDiarioDetalle();
		libroDiarioDetalleIngreso.setId(new LibroDiarioDetalleId());
		libroDiarioDetalleIngreso.getId().setIntPersEmpresaLibro(EMPRESA_USUARIO);
		libroDiarioDetalleIngreso.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		log.info("cadena real: "+planCuentaDestino.getStrDescripcion());
		log.info("cadena partida: "+(planCuentaDestino.getStrDescripcion().length()<=20?planCuentaDestino.getStrDescripcion():planCuentaDestino.getStrDescripcion().substring(0, 19)));
		libroDiarioDetalleIngreso.setStrComentario(planCuentaDestino.getStrDescripcion().length()<=20?planCuentaDestino.getStrDescripcion():planCuentaDestino.getStrDescripcion().substring(0, 19));
		libroDiarioDetalleIngreso.setIntPersEmpresaCuenta(planCuentaDestino.getId().getIntEmpresaCuentaPk());
		libroDiarioDetalleIngreso.setIntContPeriodo(planCuentaDestino.getId().getIntPeriodoCuenta());
		libroDiarioDetalleIngreso.setStrContNumeroCuenta(planCuentaDestino.getId().getStrNumeroCuenta());
		//
		libroDiarioDetalleIngreso.setIntPersPersona(sucursalDestino.getIntPersPersonaPk());
		libroDiarioDetalleIngreso.setIntParaDocumentoGeneral(obtenerDocumentoGeneralTipoFondo());
		libroDiarioDetalleIngreso.setStrSerieDocumento(null);
		libroDiarioDetalleIngreso.setStrNumeroDocumento(null);
		libroDiarioDetalleIngreso.setIntPersEmpresaSucursal(sucursalDestino.getId().getIntPersEmpresaPk());
		libroDiarioDetalleIngreso.setIntSucuIdSucursal(sucursalDestino.getId().getIntIdSucursal());
		libroDiarioDetalleIngreso.setIntSudeIdSubSucursal(subsucursalDestino.getId().getIntIdSubSucursal());
		libroDiarioDetalleIngreso.setIntParaMonedaDocumento(fondoFijo.getIntMonedaCod());			
		if(fondoFijo.getIntMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			libroDiarioDetalleIngreso.setBdHaberSoles(null);
			libroDiarioDetalleIngreso.setBdDebeSoles(controlFondosFijos.getBdMontoGirado());			
		}else{
			libroDiarioDetalleIngreso.setBdHaberExtranjero(null);
			libroDiarioDetalleIngreso.setBdDebeExtranjero(controlFondosFijos.getBdMontoGirado());			
		}
		libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleEgreso);
		libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleIngreso);		
		
		egreso.setLibroDiario(libroDiario);
		egreso.getListaEgresoDetalle().add(egresoDetalle);		
		egreso.setControlFondosFijos(controlFondosFijos);
		
		if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION) 
		&& intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)){
			egreso.getControlFondosFijos().setIntEstadoFondo(Constante.PARAM_T_ESTADOFONDO_ABIERTO);
		}
		
		log.info(egreso.getControlFondosFijos());
		log.info(controlFondosFijos);
		log.info(egreso);
		
		intTipoMoneda = fondoFijo.getIntMonedaCod();
		return egreso;
	}
	
	private ControlFondosFijos crearControlFondosFijos(Acceso acceso) throws Exception{
		AccesoDetalle accesoDetalle = obtenerAccesoDetallePorTipoFondoFijo(acceso);
		AccesoDetalleRes accesoDetalleResPrimer = obtenerPrimerResponsable(accesoDetalle);
		Sucursal sucursalDestino = accesoValidar.getSucursal();
		Subsucursal subsucursalDestino = accesoValidar.getSubsucursal();
		listaAccesoDetalleRes = accesoDetalle.getListaAccesoDetalleRes();		
		
		controlFondosFijosNuevo = new ControlFondosFijos();
		
		controlFondosFijosNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
		controlFondosFijosNuevo.getId().setIntParaTipoFondoFijo(intTipoFondoFijoValidar);
		//el itemPeriodoFondo se deja en null para que al momento de buscar un CFF previo se busquen en todos los periodos (puede
		//darse el caso de un CFF registrado en un periodo anterior que no se ha cerrado). Se setea el periodo luego.
		//controlFondosFijosNuevo.getId().setIntItemPeriodoFondo(obtenerPeriodoActual());
		controlFondosFijosNuevo.getId().setIntItemPeriodoFondo(null);
		controlFondosFijosNuevo.getId().setIntSucuIdSucursal(accesoValidar.getIntSucuIdSucursal());				
		controlFondosFijosNuevo.setIntSudeIdSubsucursal(accesoValidar.getIntSudeIdSubsucursal());
		controlFondosFijosNuevo.setIntPersPersonaResponsable(accesoDetalleResPrimer.getIntPersPersonaResponsable());
		//Los montos se asignan luego de verifiicar si hay un CFF previo
		//controlFondosFijosNuevo = manejarFondos(controlFondosFijosNuevo, accesoDetalle);
		controlFondosFijosNuevo.setIntEstadoFondo(Constante.PARAM_T_ESTADOFONDO_ABIERTO);
		controlFondosFijosNuevo.setTsFechaRegistro(obtenerFechaActual());
		controlFondosFijosNuevo.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
		controlFondosFijosNuevo.setIntPersPersonaUsuario(PERSONA_USUARIO);		
		
		controlFondosFijosNuevo.setStrDescripcionPersona(obtenerDescripcionPersona(accesoDetalleResPrimer.getPersona()));		
		controlFondosFijosNuevo.setSucursal(sucursalDestino);
		controlFondosFijosNuevo.setSubsucursal(subsucursalDestino);
		controlFondosFijosNuevo.getEgreso().setIntParaDocumentoGeneral(intTipoDocumentoValidar);		
		controlFondosFijosNuevo.setAcceso(acceso);
		
		return controlFondosFijosNuevo;
	}
	
	private ControlFondosFijos obtenerControlSeleccionado(){
		//log.info("intControlSeleccionado:"+intControlSeleccionado);
		for(Object o : listaControlFondosFijosRendicion){
			ControlFondosFijos control = (ControlFondosFijos)o;
			//log.info(control);
			if(control.getId().getIntItemFondoFijo().equals(intControlSeleccionado)){
				//log.info("match");
				return control;
			}
		}
		return null;
	}
	
	private Integer obtenerDocumentoGeneralTipoFondo(){
		if(intTipoFondoFijoValidar.equals(Constante.PARAM_T_TIPOFONDOFIJO_CAJA)){
			return Constante.PARAM_T_DOCUMENTOGENERAL_CAJA;
		}else if(intTipoFondoFijoValidar.equals(Constante.PARAM_T_TIPOFONDOFIJO_FONDOCAMBIO)){
			return Constante.PARAM_T_DOCUMENTOGENERAL_FONDOCAMBIO;
		}else if(intTipoFondoFijoValidar.equals(Constante.PARAM_T_TIPOFONDOFIJO_CAJACHICA)){
			return Constante.PARAM_T_DOCUMENTOGENERAL_CAJACHICA;
		}else if(intTipoFondoFijoValidar.equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){
			return Constante.PARAM_T_DOCUMENTOGENERAL_ENTREGARENDIR;
		}else if(intTipoFondoFijoValidar.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)){
			return Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLATELECREDITO;
		}
		return 0;
	}
	
	private Integer	obtenerPeriodoActual(){
		String strPeriodo = "";
		Calendar cal=Calendar.getInstance();
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
	
	public void validarDatos(){
		try{
			log.info("intTipoFondoFijoValidar:"+intTipoFondoFijoValidar);
			log.info("intTipoDocumentoValidar:"+intTipoDocumentoValidar);
			
			datosValidados = Boolean.FALSE;
			habilitarGrabar = Boolean.FALSE;
			
			
			if(accesoValidar.getIntSucuIdSucursal().equals(0)){
				mostrarMensaje(Boolean.FALSE,"Debe seleccionar una sucursal correcta.");return;
			}
			if(accesoValidar.getIntSudeIdSubsucursal().equals(0)){
				mostrarMensaje(Boolean.FALSE,"Debe seleccionar una subsucursal correcta.");return;
			}
			if(intTipoDocumentoValidar.equals(0)){
				mostrarMensaje(Boolean.FALSE,"Debe seleccionar un documento correcto.");return;
			}
			
			log.info(accesoValidar);
			accesoValidar.getId().setIntPersEmpresaAcceso(EMPRESA_USUARIO);
			//accesoValidar.setIntPersEmpresaSucursal(EMPRESA_USUARIO);
			accesoValidar.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			accesoValidar.setSucursal(obtenerSucursal(accesoValidar.getIntSucuIdSucursal()));
			accesoValidar.setSubsucursal(obtenerSubsucursal(accesoValidar.getIntSudeIdSubsucursal()));
			strObservacion = "";
			
			Acceso acceso = bancoFacade.validarAccesoParaApertura(accesoValidar, intTipoFondoFijoValidar, intTipoMonedaValidar);
			log.info(acceso);
			if(acceso == null){
				mostrarMensaje(Boolean.FALSE,"No existe un acceso configurado para esa sucursal con ese fondo fijo y moneda.");return;				
			}			
			log.info(accesoValidar);
			controlFondosFijosNuevo = crearControlFondosFijos(acceso);
			
			controlFondosFijosNuevo.setIntEstadoFondo(null);//se pone nulo para que en el filtro obtenga todos los cFFs anteriores
			
			List<ControlFondosFijos> listaTemp = egresoFacade.buscarControlFondosFijos(controlFondosFijosNuevo);
			controlFondosFijosNuevo.setIntEstadoFondo(Constante.PARAM_T_ESTADOFONDO_ABIERTO);//vuelve al estado aperturado
			
			ControlFondosFijos controlFondosFijosPrevio = null;
			if(listaTemp!=null)	controlFondosFijosPrevio = egresoFacade.obtenerControlFondosFijosUltimo(listaTemp);
			
			if(controlFondosFijosPrevio!=null){
				//Solo para el caso de 'entrega a rendir' se pueden aperturar varios fondos a la vez
				if(controlFondosFijosPrevio.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_ABIERTO)){
					if(controlFondosFijosPrevio.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)){
						obtenerDescripcionControlFondosFijos(controlFondosFijosPrevio);
						controlFondosFijosNuevo.setAnterior(controlFondosFijosPrevio);
					}else{
						mostrarMensaje(Boolean.FALSE,"El control sobre el fondo fijo referenciado aun esta abierto.");return;
					}
				}else if(controlFondosFijosPrevio.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_CERRADO)){
					obtenerDescripcionControlFondosFijos(controlFondosFijosPrevio);
					controlFondosFijosNuevo.setAnterior(controlFondosFijosPrevio);
				}else if(controlFondosFijosPrevio.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_ANULADO)){
					controlFondosFijosPrevio = null;
				}
			}
			
			
			AccesoDetalle accesoDetalle = obtenerAccesoDetallePorTipoFondoFijo(acceso);
			controlFondosFijosNuevo.getId().setIntItemPeriodoFondo(obtenerPeriodoActual());
			controlFondosFijosNuevo = manejarMontosFondos(controlFondosFijosNuevo, accesoDetalle, controlFondosFijosPrevio);
			log.info(controlFondosFijosNuevo);
			
			
			intBancoSeleccionado = 0;
			intChequeSeleccionado = 0;
			listaBancoCuentaCheque.clear();
			listaBancoCuenta = new ArrayList<Bancocuenta>();
			datosValidados = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;
			ocultarMensaje();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	private String obtenerDescripcionControlFondosFijos(ControlFondosFijos controlFondosFijos){
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String descripcion = "";
		descripcion = controlFondosFijos.getStrNumeroApertura() +" - Monto Asignado: "+ controlFondosFijos.getBdMontoGirado()
			+" - Monto Otorgado: " + controlFondosFijos.getBdMontoUtilizado() + " - Saldo: " + controlFondosFijos.getBdMontoSaldo();
			
		if(controlFondosFijos.getTsFechaCierre()!=null){
			descripcion = descripcion +" / Fecha Cierre: "+formatter.format(controlFondosFijos.getTsFechaCierre());
		}
		controlFondosFijos.setStrDescripcion(descripcion);
		return descripcion;
	}
	
	private String obtenerDescripcionControlFondosFijos2(ControlFondosFijos controlFondosFijos, EgresoDetalle egresoDetalle){
		String descripcion = "";		
		
		descripcion = controlFondosFijos.getStrNumeroApertura() +" - Saldo: "+ controlFondosFijos.getBdMontoSaldo()
			+" - Moneda: " + obtenerEtiquetaMoneda(egresoDetalle.getIntParaTipoMoneda());
		
		controlFondosFijos.setStrDescripcion(descripcion);
		return descripcion;
	}
	
	
	private ControlFondosFijos manejarMontosFondos(ControlFondosFijos controlFondosFijosNuevo, AccesoDetalle accesoDetalle, 
			ControlFondosFijos controlFondosFijosAnterior)throws Exception{
		BigDecimal montoApertura = accesoDetalle.getBdMontoFondo();		
		
		log.info(controlFondosFijosAnterior);
		if(controlFondosFijosAnterior!=null && controlFondosFijosAnterior.getId().getIntItemFondoFijo()!=null){
			log.info("Existe controlFondosFijosAnterior");
			BigDecimal montoGiro = montoApertura.subtract(controlFondosFijosAnterior.getBdMontoSaldo());
			
			controlFondosFijosNuevo.setBdMontoGirado(montoGiro);
			controlFondosFijosNuevo.setBdMontoApertura(montoApertura);
			controlFondosFijosNuevo.setBdMontoUtilizado(new BigDecimal(0));
			controlFondosFijosNuevo.setBdMontoSaldo(montoApertura);
		}else{
			log.info("No Existe controlFondosFijosAnterior");
			controlFondosFijosNuevo.setBdMontoGirado(montoApertura);
			controlFondosFijosNuevo.setBdMontoApertura(montoApertura);
			controlFondosFijosNuevo.setBdMontoUtilizado(new BigDecimal(0));
			controlFondosFijosNuevo.setBdMontoSaldo(montoApertura);
		}
		
		return controlFondosFijosNuevo;
	}	

	
	private PlanCuenta obtenerPlanCuentaDestino(Fondodetalle fondoDetalle)throws Exception{
		PlanCuenta planCuenta = new PlanCuenta();
		planCuenta.setId(new PlanCuentaId());
		planCuenta.getId().setIntEmpresaCuentaPk(fondoDetalle.getIntEmpresacuentaPk());
		planCuenta.getId().setIntPeriodoCuenta(fondoDetalle.getIntPeriodocuenta());
		planCuenta.getId().setStrNumeroCuenta(fondoDetalle.getStrNumerocuenta());
		
		planCuenta = planCuentaFacade.getPlanCuentaPorPk(planCuenta.getId());
		return planCuenta;
	}
	
	private Fondodetalle obtenerFondoDetalle(Bancofondo bancoFondo, Acceso acceso) throws Exception{
		List<Fondodetalle> listaFondoDetalle = bancoFacade.getListaFondoDetallePorBancoFondo(bancoFondo);
		Sucursal sucursal = obtenerSucursal(acceso.getIntSucuIdSucursal());
		//log.info(acceso);
		//log.info("sucursal tipo:"+sucursal.getIntIdTipoSucursal());
		for(Fondodetalle fondoDetalle : listaFondoDetalle){
			if(fondoDetalle.getIntCodigodetalle().equals(Constante.CODIGODETALLE_CUENTACONTABLE)){
				//log.info(fondoDetalle);
				//si el fondoDetalle ha sido configurado para una sucursal especifica
				if(fondoDetalle.getIntIdsucursal()!=null){
					if(fondoDetalle.getIntIdsucursal().equals(acceso.getIntSucuIdSucursal())
					&& fondoDetalle.getIntIdsubsucursal().equals(acceso.getIntSudeIdSubsucursal())){
						return fondoDetalle;
					}				
				}
				//si el fondoDetalle ha sido configurado para un grupo de sucursales
				if(fondoDetalle.getIntTotalsucursalCod()!=null){
					if(fondoDetalle.getIntTotalsucursalCod().equals(Constante.PARAM_T_TOTALESSUCURSALES_SUCURSALES)){
						return fondoDetalle;
					}else if(fondoDetalle.getIntTotalsucursalCod().equals(Constante.PARAM_T_TOTALESSUCURSALES_AGENCIAS) && sucursal.getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_AGENCIA)){
						return fondoDetalle;
					}else if(fondoDetalle.getIntTotalsucursalCod().equals(Constante.PARAM_T_TOTALESSUCURSALES_FILIALES) && sucursal.getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_FILIAL)){
						return fondoDetalle;
					}else if(fondoDetalle.getIntTotalsucursalCod().equals(Constante.PARAM_T_TOTALESSUCURSALES_OFICINAPRINCIPAL) && sucursal.getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL)){
						return fondoDetalle;
					}else if(fondoDetalle.getIntTotalsucursalCod().equals(Constante.PARAM_T_TOTALESSUCURSALES_SEDE) && sucursal.getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL)){
						return fondoDetalle;
					}
				}
			}			
		}
		return null;
	}
	
	private Bancocuenta obtenerBancoCuentaSeleccionado(){
		for(Object o : listaBancoCuenta){
			Bancocuenta bancoCuenta = (Bancocuenta) o;
			if(bancoCuenta.getId().getIntItembancocuenta().equals(intBancoCuentaSeleccionado)){
				return bancoCuenta;
			}
		}
		return null;
	}
	
	private String obtenerDNI(Persona persona) throws Exception{
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				return documento.getStrNumeroIdentidad();
			}
		}
		return "";
	}
	
	private String obtenerDescripcionPersona(Persona persona)throws Exception{
		String strDes = "";
		strDes = persona.getIntIdPersona() + " - " + persona.getNatural().getStrNombreCompleto() + 
			" - DNI : " + obtenerDNI(persona); 
//		buscarPersona(obtenerDNI(persona));
		return strDes;
	}
	
	private AccesoDetalle obtenerAccesoDetallePorTipoFondoFijo(Acceso acceso) throws Exception{
		for(AccesoDetalle accesoDetalle : acceso.getListaAccesoDetalle()){
			if(accesoDetalle.isPoseeTipoFondoFijo()){
				//Carga datos de persona para su lista de accesoDetalleRes
				for(AccesoDetalleRes accesoDetalleRes : accesoDetalle.getListaAccesoDetalleRes()){
					Persona persona = new Persona();
					persona.setNatural(new Natural());
					persona.getNatural().setIntIdPersona(accesoDetalleRes.getIntPersPersonaResponsable());
					List<Persona> listaPersona = personaFacade.getListPerNaturalBusqueda(persona);
					if(listaPersona!=null && !listaPersona.isEmpty()){
						 persona = listaPersona.get(0);
						 persona.getNatural().setStrNombreCompleto(persona.getNatural().getStrNombres()+
								 " " + persona.getNatural().getStrApellidoPaterno()+
								 " " + persona.getNatural().getStrApellidoMaterno());
						 accesoDetalleRes.setPersona(persona);
					}
				}
				return accesoDetalle;
			}
		}
		return null;
	}
	
	private AccesoDetalleRes obtenerPrimerResponsable(AccesoDetalle accesoDetalle) throws Exception{
		//log.info("accesoDetalle.getListaAccesoDetalleRes:"+accesoDetalle.getListaAccesoDetalleRes().size());
		for(AccesoDetalleRes accesoDetalleRes : accesoDetalle.getListaAccesoDetalleRes()){
			//log.info(accesoDetalleRes);			
			if(accesoDetalleRes.getIntOrden().equals(new Integer(1))){
				return  accesoDetalleRes;
			}
		}
		return null;
	}
	
	private Sucursal obtenerSucursal(Integer intIdSucursal) throws Exception{
		for(Object o : listaSucursal){
			Sucursal sucursal = (Sucursal)o;
			if(sucursal.getId().getIntIdSucursal().equals(intIdSucursal)){
				return sucursal;
			}
		}
		return null;
	}

	
	private Subsucursal obtenerSubsucursal(Integer intIdSubsucursal) throws Exception{
		for(Object o : listaSubsucursal){
			Subsucursal subsucursal = (Subsucursal)o;
			if(subsucursal.getId().getIntIdSubSucursal().equals(intIdSubsucursal)){
				return subsucursal;
			}
		}
		return null;
	}
	
	private String obtenerEtiquetaMoneda(Integer intTipoMoneda){
		for(Tabla tabla : listaMoneda){
			if(tabla.getIntIdDetalle().equals(intTipoMoneda)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	public void seleccionarResponsable(ActionEvent event){
//		String strFiltro = "";
		try{
			AccesoDetalleRes accesoDetalleRes = (AccesoDetalleRes)event.getComponent().getAttributes().get("item");
			controlFondosFijosNuevo.setIntPersPersonaResponsable(accesoDetalleRes.getIntPersPersonaResponsable());
			controlFondosFijosNuevo.setStrDescripcionPersona(obtenerDescripcionPersona(accesoDetalleRes.getPersona()));
			//Autor: jchavez / Tarea: Creación / Fecha: 23.08.2014 /
//			strFiltro = obtenerDNI(accesoDetalleRes.getPersona());
//			buscarPersona(strFiltro);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarBanco(){
		try{
			//log.info("--seleccionarBanco");
			if(intBancoSeleccionado.equals(new Integer(0))){
				listaBancoCuenta = new ArrayList<Bancocuenta>();
				return;
			}
			Bancofondo bancoFondoTemp = new Bancofondo();
			bancoFondoTemp.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
			bancoFondoTemp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			List<Bancofondo> listaBancoFondoTemp = bancoFacade.buscarBancoFondo(bancoFondoTemp);
			listaBancoCuenta = new ArrayList<Bancocuenta>();
			String strEtiqueta = "";
			//log.info("--intBancoSeleccionado:"+intBancoSeleccionado);
			for(Bancofondo bancoFondo : listaBancoFondoTemp){
				//log.info(bancoFondo);
				if(bancoFondo.getIntBancoCod().equals(intBancoSeleccionado)){
					for(Bancocuenta bancoCuenta : bancoFondo.getListaBancocuenta()){
						//log.info(bancoCuenta);
						strEtiqueta = bancoCuenta.getStrNombrecuenta()+" - "
										+bancoCuenta.getCuentaBancaria().getStrNroCuentaBancaria()+" - "
										+obtenerEtiquetaMoneda(bancoCuenta.getCuentaBancaria().getIntMonedaCod());
						bancoCuenta.setStrEtiqueta(strEtiqueta);
						listaBancoCuenta.add(bancoCuenta);
					}
				}				
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}	
	
	
	public void seleccionarBancoCuenta(){
		try{
			if(intBancoCuentaSeleccionado.equals(new Integer(0))){
				return;
			}			
			Bancocuenta bancoCuentaSeleccionado = obtenerBancoCuentaSeleccionado();
			listaBancoCuentaCheque = bancoFacade.getListaBancoCuentaChequePorBancoCuenta(bancoCuentaSeleccionado);
			if(listaBancoCuentaCheque==null){
				return;				
			}
			
			for(Object o : listaBancoCuentaCheque){
				Bancocuentacheque bancoCuentaCheque = (Bancocuentacheque)o;
				bancoCuentaCheque.setStrEtiqueta("Chequera : "+bancoCuentaCheque.getStrSerie()
					+" - Número actual : "+bancoCuentaCheque.getIntNumeroinicio());				
//				intNumeroCheque = bancoCuentaCheque.getIntNumeroinicio();
			}			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void mostrarNumeroCheque(){
		if (intChequeSeleccionado.equals(0)) {
			intNumeroCheque = null;
		}else{
			for(Object o : listaBancoCuentaCheque){
				Bancocuentacheque bancoCuentaCheque = (Bancocuentacheque) o;
				if(bancoCuentaCheque.getId().getIntItembancuencheque().equals(intChequeSeleccionado)){
					intNumeroCheque = bancoCuentaCheque.getIntNumeroinicio();
					break;
				}
			}
		}
	}
	
	public void seleccionarFondoFijoRendicion(){
		try{
			listaControlFondosFijosRendicion = new ArrayList<ControlFondosFijos>();
			if(intTipoFondoFijoRendicion.equals(new Integer(0))){
				return;
			}
			if(intTipoFondoFijoRendicion.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)){
				return;
			}
			log.info("--seleccionarFondoFijoRendicion");
			
			ControlFondosFijos controlRendicion = new ControlFondosFijos();
			controlRendicion.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			controlRendicion.getId().setIntParaTipoFondoFijo(intTipoFondoFijoRendicion);
			controlRendicion.getId().setIntSucuIdSucursal(SUCURSAL_USUARIO);
			controlRendicion.setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
			
			List<ControlFondosFijos> lista = egresoFacade.buscarControlFondosFijos(controlRendicion);
			for(ControlFondosFijos controlFondosFijos : lista){
				log.info(controlFondosFijos);
			}
			
			ControlFondosFijos controlUltimo = egresoFacade.obtenerControlFondosFijosUltimo(lista);
			if(controlUltimo==null){
				return;
			}
			Egreso egreso = egresoFacade.getEgresoPorControlFondosFijos(controlUltimo);
			controlUltimo.setEgreso(egreso);
			List<EgresoDetalle> listaEgresoDetalle = egresoFacade.getListaEgresoDetallePorEgreso(egreso);
			EgresoDetalle egresoDetalle = listaEgresoDetalle.get(0);
			obtenerDescripcionControlFondosFijos2(controlUltimo, egresoDetalle);
			listaControlFondosFijosRendicion.add(controlUltimo);
			
			log.info("Tipo de fondo seleccionado: "+intTipoFondoFijoRendicion);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	//Autor: jchavez / Tarea: Creación / Fecha: 23.08.2014 /
//	public void buscarPersona(String strFiltroTextoPersona){
//		try{
//			personaSeleccionada = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
//				Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
//				strFiltroTextoPersona,
//				EMPRESA_USUARIO);
//		}catch(Exception e){
//			log.error(e.getMessage(), e);
//		}
//	}
	
//	public void seleccionarCuentaBancaria(ActionEvent event){
//		String strTipoCuenta = "";
//		String strTipoMoneda = "";
//		try{
//			cuentaBancariaSeleccionada = (CuentaBancaria)event.getComponent().getAttributes().get("item");
//			for (Tabla x : listaTablaTipoCuentaBancaria) {
//				if (x.getIntIdDetalle().equals(cuentaBancariaSeleccionada.getIntTipoCuentaCod())) {
//					strTipoCuenta = "Tipo Cuenta: "+x.getStrDescripcion();
//					break;
//				}
//			}
//			for (Tabla y : listaTablaTipoMoneda) {
//				if (y.getIntIdDetalle().equals(cuentaBancariaSeleccionada.getIntMonedaCod())) {
//					strTipoMoneda = " - Moneda: "+y.getStrDescripcion();
//					break;
//				}
//			}
//			cuentaBancariaSeleccionada.setStrEtiqueta(strTipoCuenta+strTipoMoneda+" - Nro. Cuenta: "+cuentaBancariaSeleccionada.getStrNroCuentaBancaria());
//			log.info(personaSeleccionada);
//		}catch(Exception e){
//			log.error(e.getMessage(), e);
//		}
//	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public String getMensajePopUp() {
		return mensajePopUp;
	}
	public void setMensajePopUp(String mensajePopUp) {
		this.mensajePopUp = mensajePopUp;
	}
	public boolean isMostrarBtnEliminar() {
		return mostrarBtnEliminar;
	}
	public void setMostrarBtnEliminar(boolean mostrarBtnEliminar) {
		this.mostrarBtnEliminar = mostrarBtnEliminar;
	}
	public boolean isMostrarMensajeExito() {
		return mostrarMensajeExito;
	}
	public void setMostrarMensajeExito(boolean mostrarMensajeExito) {
		this.mostrarMensajeExito = mostrarMensajeExito;
	}
	public boolean isMostrarMensajeError() {
		return mostrarMensajeError;
	}
	public void setMostrarMensajeError(boolean mostrarMensajeError) {
		this.mostrarMensajeError = mostrarMensajeError;
	}
	public boolean isDeshabilitarNuevo() {
		return deshabilitarNuevo;
	}
	public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
		this.deshabilitarNuevo = deshabilitarNuevo;
	}
	public boolean isMostrarPanelInferior() {
		return mostrarPanelInferior;
	}
	public void setMostrarPanelInferior(boolean mostrarPanelInferior) {
		this.mostrarPanelInferior = mostrarPanelInferior;
	}
	public boolean isRegistrarNuevo() {
		return registrarNuevo;
	}
	public void setRegistrarNuevo(boolean registrarNuevo) {
		this.registrarNuevo = registrarNuevo;
	}
	public boolean isHabilitarGrabar() {
		return habilitarGrabar;
	}
	public void setHabilitarGrabar(boolean habilitarGrabar) {
		this.habilitarGrabar = habilitarGrabar;
	}
	public boolean isMostrarMensajePopUp() {
		return mostrarMensajePopUp;
	}
	public void setMostrarMensajePopUp(boolean mostrarMensajePopUp) {
		this.mostrarMensajePopUp = mostrarMensajePopUp;
	}
	public boolean isHabilitarEditarPopUp() {
		return habilitarEditarPopUp;
	}
	public void setHabilitarEditarPopUp(boolean habilitarEditarPopUp) {
		this.habilitarEditarPopUp = habilitarEditarPopUp;
	}
	public boolean isDatosValidados() {
		return datosValidados;
	}
	public void setDatosValidados(boolean datosValidados) {
		this.datosValidados = datosValidados;
	}
//	public List getListaSucursal() {
//		return listaSucursal;
//	}
//	public void setListaSucursal(List listaSucursal) {
//		this.listaSucursal = listaSucursal;
//	}
//	public List getListaSubsucursal() {
//		return listaSubsucursal;
//	}
//	public void setListaSubsucursal(List listaSubsucursal) {
//		this.listaSubsucursal = listaSubsucursal;
//	}
	public Integer getIntTipoFondoFijoValidar() {
		return intTipoFondoFijoValidar;
	}
	public void setIntTipoFondoFijoValidar(Integer intTipoFondoFijoValidar) {
		this.intTipoFondoFijoValidar = intTipoFondoFijoValidar;
	}
	public Integer getIntTipoDocumentoValidar() {
		return intTipoDocumentoValidar;
	}
	public void setIntTipoDocumentoValidar(Integer intTipoDocumentoValidar) {
		this.intTipoDocumentoValidar = intTipoDocumentoValidar;
	}
	public Acceso getAccesoValidar() {
		return accesoValidar;
	}
	public void setAccesoValidar(Acceso accesoValidar) {
		this.accesoValidar = accesoValidar;
	}
//	public List getListaTipoDocumento() {
//		return listaTipoDocumento;
//	}
//	public void setListaTipoDocumento(List listaTipoDocumento) {
//		this.listaTipoDocumento = listaTipoDocumento;
//	}
	public Integer getIntBancoSeleccionado() {
		return intBancoSeleccionado;
	}
	public void setIntBancoSeleccionado(Integer intBancoSeleccionado) {
		this.intBancoSeleccionado = intBancoSeleccionado;
	}
	public ControlFondosFijos getControlFondosFijosNuevo() {
		return controlFondosFijosNuevo;
	}
	public void setControlFondosFijosNuevo(	ControlFondosFijos controlFondosFijosNuevo) {
		this.controlFondosFijosNuevo = controlFondosFijosNuevo;
	}
	public Sucursal getSucursalSedeCentral() {
		return sucursalSedeCentral;
	}
	public void setSucursalSedeCentral(Sucursal sucursalSedeCentral) {
		this.sucursalSedeCentral = sucursalSedeCentral;
	}
	public Subsucursal getSubsucursalSedeCentral() {
		return subsucursalSedeCentral;
	}
	public void setSubsucursalSedeCentral(Subsucursal subsucursalSedeCentral) {
		this.subsucursalSedeCentral = subsucursalSedeCentral;
	}
//	public List getListaBancoCuenta() {
//		return listaBancoCuenta;
//	}
//	public void setListaBancoCuenta(List listaBancoCuenta) {
//		this.listaBancoCuenta = listaBancoCuenta;
//	}
	public Integer getIntTipoMonedaValidar() {
		return intTipoMonedaValidar;
	}
	public void setIntTipoMonedaValidar(Integer intTipoMonedaValidar) {
		this.intTipoMonedaValidar = intTipoMonedaValidar;
	}
//	public List getListaAccesoDetalleRes() {
//		return listaAccesoDetalleRes;
//	}
//	public void setListaAccesoDetalleRes(List listaAccesoDetalleRes) {
//		this.listaAccesoDetalleRes = listaAccesoDetalleRes;
//	}
	public Integer getIntBancoCuentaSeleccionado() {
		return intBancoCuentaSeleccionado;
	}
	public void setIntBancoCuentaSeleccionado(Integer intBancoCuentaSeleccionado) {
		this.intBancoCuentaSeleccionado = intBancoCuentaSeleccionado;
	}
	public Integer getIntNumeroCheque() {
		return intNumeroCheque;
	}
	public void setIntNumeroCheque(Integer intNumeroCheque) {
		this.intNumeroCheque = intNumeroCheque;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Date getDtFechaActual() {
		return dtFechaActual;
	}
	public void setDtFechaActual(Date dtFechaActual) {
		this.dtFechaActual = dtFechaActual;
	}
	public Integer getIntChequeSeleccionado() {
		return intChequeSeleccionado;
	}
	public void setIntChequeSeleccionado(Integer intChequeSeleccionado) {
		this.intChequeSeleccionado = intChequeSeleccionado;
	}
//	public List getListaBancoCuentaCheque() {
//		return listaBancoCuentaCheque;
//	}
//	public void setListaBancoCuentaCheque(List listaBancoCuentaCheque) {
//		this.listaBancoCuentaCheque = listaBancoCuentaCheque;
//	}
//	public List getListaSucursalBus() {
//		return listaSucursalBus;
//	}
//	public void setListaSucursalBus(List listaSucursalBus) {
//		this.listaSucursalBus = listaSucursalBus;
//	}
//	public List getListaControlFondosFijos() {
//		return listaControlFondosFijos;
//	}
//	public void setListaControlFondosFijos(List listaControlFondosFijos) {
//		this.listaControlFondosFijos = listaControlFondosFijos;
//	}
	public ControlFondosFijos getControlFondosFijosFiltro() {
		return controlFondosFijosFiltro;
	}
	public void setControlFondosFijosFiltro(ControlFondosFijos controlFondosFijosFiltro) {
		this.controlFondosFijosFiltro = controlFondosFijosFiltro;
	}
//	public List getListaTipoFondoFijo() {
//		return listaTipoFondoFijo;
//	}
//	public void setListaTipoFondoFijo(List listaTipoFondoFijo) {
//		this.listaTipoFondoFijo = listaTipoFondoFijo;
//	}
//	public List getListaTipoFondoFijoRendicion() {
//		return listaTipoFondoFijoRendicion;
//	}
//	public void setListaTipoFondoFijoRendicion(List listaTipoFondoFijoRendicion) {
//		this.listaTipoFondoFijoRendicion = listaTipoFondoFijoRendicion;
//	}
	public Integer getIntTipoFondoFijoRendicion() {
		return intTipoFondoFijoRendicion;
	}
	public void setIntTipoFondoFijoRendicion(Integer intTipoFondoFijoRendicion) {
		this.intTipoFondoFijoRendicion = intTipoFondoFijoRendicion;
	}
	public Integer getIntControlSeleccionado() {
		return intControlSeleccionado;
	}
	public void setIntControlSeleccionado(Integer intControlSeleccionado) {
		this.intControlSeleccionado = intControlSeleccionado;
	}
//	public List getListaControlFondosFijosRendicion() {
//		return listaControlFondosFijosRendicion;
//	}
//	public void setListaControlFondosFijosRendicion(List listaControlFondosFijosRendicion) {
//		this.listaControlFondosFijosRendicion = listaControlFondosFijosRendicion;
//	}
	public Bancofondo getBancoFondoHermes() {
		return bancoFondoHermes;
	}
	public void setBancoFondoHermes(Bancofondo bancoFondoHermes) {
		this.bancoFondoHermes = bancoFondoHermes;
	}
	public Bancocuenta getBancoCuentaHermes() {
		return bancoCuentaHermes;
	}
	public void setBancoCuentaHermes(Bancocuenta bancoCuentaHermes) {
		this.bancoCuentaHermes = bancoCuentaHermes;
	}
	//Agregado 17.12.2013 JCHAVEZ
	public List<ControlFondosFijos> getListaControlFondosFijos() {
		return listaControlFondosFijos;
	}

	public void setListaControlFondosFijos(
			List<ControlFondosFijos> listaControlFondosFijos) {
		this.listaControlFondosFijos = listaControlFondosFijos;
	}

	public List<ControlFondosFijos> getListaControlFondosFijosRendicion() {
		return listaControlFondosFijosRendicion;
	}

	public void setListaControlFondosFijosRendicion(
			List<ControlFondosFijos> listaControlFondosFijosRendicion) {
		this.listaControlFondosFijosRendicion = listaControlFondosFijosRendicion;
	}

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public List<Tabla> getListaSucursalBus() {
		return listaSucursalBus;
	}

	public void setListaSucursalBus(List<Tabla> listaSucursalBus) {
		this.listaSucursalBus = listaSucursalBus;
	}

	public List<Subsucursal> getListaSubsucursal() {
		return listaSubsucursal;
	}

	public void setListaSubsucursal(List<Subsucursal> listaSubsucursal) {
		this.listaSubsucursal = listaSubsucursal;
	}

	public List<Tabla> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<Tabla> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public List<Bancocuenta> getListaBancoCuenta() {
		return listaBancoCuenta;
	}

	public void setListaBancoCuenta(List<Bancocuenta> listaBancoCuenta) {
		this.listaBancoCuenta = listaBancoCuenta;
	}

	public List<Bancocuentacheque> getListaBancoCuentaCheque() {
		return listaBancoCuentaCheque;
	}

	public void setListaBancoCuentaCheque(
			List<Bancocuentacheque> listaBancoCuentaCheque) {
		this.listaBancoCuentaCheque = listaBancoCuentaCheque;
	}

	public List<Tabla> getListaMoneda() {
		return listaMoneda;
	}

	public void setListaMoneda(List<Tabla> listaMoneda) {
		this.listaMoneda = listaMoneda;
	}

	public List<AccesoDetalleRes> getListaAccesoDetalleRes() {
		return listaAccesoDetalleRes;
	}

	public void setListaAccesoDetalleRes(
			List<AccesoDetalleRes> listaAccesoDetalleRes) {
		this.listaAccesoDetalleRes = listaAccesoDetalleRes;
	}

	public List<Tabla> getListaTipoFondoFijo() {
		return listaTipoFondoFijo;
	}

	public void setListaTipoFondoFijo(List<Tabla> listaTipoFondoFijo) {
		this.listaTipoFondoFijo = listaTipoFondoFijo;
	}

	public List<Tabla> getListaTipoFondoFijoRendicion() {
		return listaTipoFondoFijoRendicion;
	}

	public void setListaTipoFondoFijoRendicion(
			List<Tabla> listaTipoFondoFijoRendicion) {
		this.listaTipoFondoFijoRendicion = listaTipoFondoFijoRendicion;
	}

	public ControlFondosFijos getControlFondosFijosRendicion() {
		return controlFondosFijosRendicion;
	}

	public void setControlFondosFijosRendicion(
			ControlFondosFijos controlFondosFijosRendicion) {
		this.controlFondosFijosRendicion = controlFondosFijosRendicion;
	}

	public Bancofondo getFondoTelecredito() {
		return fondoTelecredito;
	}

	public void setFondoTelecredito(Bancofondo fondoTelecredito) {
		this.fondoTelecredito = fondoTelecredito;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getIntTipoMoneda() {
		return intTipoMoneda;
	}

	public void setIntTipoMoneda(Integer intTipoMoneda) {
		this.intTipoMoneda = intTipoMoneda;
	}

	public Integer getEMPRESA_USUARIO() {
		return EMPRESA_USUARIO;
	}

	public void setEMPRESA_USUARIO(Integer eMPRESA_USUARIO) {
		EMPRESA_USUARIO = eMPRESA_USUARIO;
	}

	public Integer getPERSONA_USUARIO() {
		return PERSONA_USUARIO;
	}

	public void setPERSONA_USUARIO(Integer pERSONA_USUARIO) {
		PERSONA_USUARIO = pERSONA_USUARIO;
	}

	public Integer getSUCURSAL_USUARIO() {
		return SUCURSAL_USUARIO;
	}

	public void setSUCURSAL_USUARIO(Integer sUCURSAL_USUARIO) {
		SUCURSAL_USUARIO = sUCURSAL_USUARIO;
	}

	public Integer getSUBSUCURSAL_USUARIO() {
		return SUBSUCURSAL_USUARIO;
	}

	public void setSUBSUCURSAL_USUARIO(Integer sUBSUCURSAL_USUARIO) {
		SUBSUCURSAL_USUARIO = sUBSUCURSAL_USUARIO;
	}

	public Integer getID_SUCURSAL_SEDECENTRAL() {
		return ID_SUCURSAL_SEDECENTRAL;
	}

	public void setID_SUCURSAL_SEDECENTRAL(Integer iD_SUCURSAL_SEDECENTRAL) {
		ID_SUCURSAL_SEDECENTRAL = iD_SUCURSAL_SEDECENTRAL;
	}

	public Integer getID_SUBSUCURSAL_SEDECENTRAL() {
		return ID_SUBSUCURSAL_SEDECENTRAL;
	}

	public void setID_SUBSUCURSAL_SEDECENTRAL(Integer iD_SUBSUCURSAL_SEDECENTRAL) {
		ID_SUBSUCURSAL_SEDECENTRAL = iD_SUBSUCURSAL_SEDECENTRAL;
	}

	public String getHERMES_RUC() {
		return HERMES_RUC;
	}

	public void setHERMES_RUC(String hERMES_RUC) {
		HERMES_RUC = hERMES_RUC;
	}
	
	//Autor: jchavez / Tarea: Creación / Fecha: 23.08.2014 /
//	public CuentaBancaria getCuentaBancariaSeleccionada() {
//		return cuentaBancariaSeleccionada;
//	}
//	public void setCuentaBancariaSeleccionada(
//			CuentaBancaria cuentaBancariaSeleccionada) {
//		this.cuentaBancariaSeleccionada = cuentaBancariaSeleccionada;
//	}
//	public Persona getPersonaSeleccionada() {
//		return personaSeleccionada;
//	}
//	public void setPersonaSeleccionada(Persona personaSeleccionada) {
//		this.personaSeleccionada = personaSeleccionada;
//	}
//	//Autor: jchavez / Tarea: Creación / Fecha: 25.08.2014 /
//	public List<Tabla> getListaTablaTipoMoneda() {
//		return listaTablaTipoMoneda;
//	}
//	public void setListaTablaTipoMoneda(List<Tabla> listaTablaTipoMoneda) {
//		this.listaTablaTipoMoneda = listaTablaTipoMoneda;
//	}
//	public List<Tabla> getListaTablaTipoCuentaBancaria() {
//		return listaTablaTipoCuentaBancaria;
//	}
//	public void setListaTablaTipoCuentaBancaria(
//			List<Tabla> listaTablaTipoCuentaBancaria) {
//		this.listaTablaTipoCuentaBancaria = listaTablaTipoCuentaBancaria;
//	}	
}

/*
            <h:panelGrid id="panelTablaResultados">
	        	<rich:dataTable id="tblResultado" 
	          		rendered="#{aperturaController.controlFondosFijosFiltro.id.intParaTipoFondoFijo!=applicationScope.Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR}"
	          		sortMode="single" 
                    var="item" 
                    value="#{aperturaController.listaControlFondosFijos}"  
					rowKeyVar="rowKey"
					rows="5"
					width="1110px"
					styleClass="dataTable1"
					align="center">

*/