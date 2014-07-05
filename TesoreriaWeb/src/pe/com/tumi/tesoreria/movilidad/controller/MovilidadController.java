package pe.com.tumi.tesoreria.movilidad.controller;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.AreaId;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadDetalle;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;


public class MovilidadController {

	protected static Logger log = Logger.getLogger(MovilidadController.class);
	
	PersonaFacadeRemote 	personaFacade;
	ContactoFacadeRemote	contactoFacade;
	BancoFacadeLocal 		bancoFacade;
	EgresoFacadeLocal		egresoFacade;
	EmpresaFacadeRemote		empresaFacade;
	
	private List<Movilidad>			listaMovilidad;
	private List<MovilidadDetalle>	listaMovilidadDetalle;
	private List<Tabla> 			listaAnios;
	private	List<Sucursal>			listaSucursal;
	private List<Subsucursal>		listaSubsucursal;
	private List<Area>				listaArea;
	
	private	Movilidad			movilidadNuevo;
	private	Movilidad			movilidadFiltro;
	private	MovilidadDetalle	movilidadDetalleAgregar;
	private	Area				area;
	private	Movilidad			registroSeleccionado;	
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private String 		mensajePopUp;	
	private final int 	cantidadAñosLista = 4;
	private String		strFiltroDNI;
	private	String 		strEtiquetaEmpresa;
	private	Date		dtDesdeFiltro;
	private	Date		dtHastaFiltro;
	BigDecimal 			montoAcumulado;
	
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
	
	//Agregado 16.12.2013 JCHAVEZ
	private List<Sucursal> listJuridicaSucursal;

	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
	
	
	public MovilidadController(){
		cargarUsuario();
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	

	public void cargarValoresIniciales(){
		try{
			movilidadFiltro = new Movilidad();
			listaMovilidad = new ArrayList<Movilidad>();
			movilidadDetalleAgregar = new MovilidadDetalle();
			//dtDesdeFiltro = new Date();
			//dtHastaFiltro = new Date();
			area = new Area();
			area.setId(new AreaId());
			area.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
			
			personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			contactoFacade = (ContactoFacadeRemote) EJBFactory.getRemote(ContactoFacadeRemote.class);
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			empresaFacade =  (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			cargarListaAnios();
			getListSucursales();
			generarEtiquetaEmpresa();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaAnios(){
		listaAnios = new ArrayList<Tabla>();
		Calendar cal=Calendar.getInstance();
		Tabla tabla = null;
		for(int i=0;i<cantidadAñosLista;i++){
			tabla = new Tabla();
			int year = cal.get(Calendar.YEAR);
			cal.add(Calendar.YEAR, 1);
			tabla.setIntIdDetalle(year);
			tabla.setStrDescripcion(""+year);
			listaAnios.add(tabla);
		}		
	}
	
	private void generarEtiquetaEmpresa() throws BusinessException{
		Integer intIdEmpresa = usuario.getPerfil().getId().getIntPersEmpresaPk();
		Persona persona = personaFacade.getPersonaPorPK(intIdEmpresa);
		Juridica juridica = personaFacade.getJuridicaPorPK(intIdEmpresa);
		strEtiquetaEmpresa = intIdEmpresa + " - " + juridica.getStrRazonSocial() + " - RUC - "+persona.getStrRuc();
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			datosValidados = Boolean.FALSE;
			strFiltroDNI = "";
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}	
		
	public void grabar(){
		log.info("--grabar");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			//validaciones
			if(listaMovilidadDetalle.isEmpty()){
				mensaje = "Debe agregar al menos un detalle de movilidad.";
				return;
			}
			if(movilidadNuevo.getIntSucuIdSucursal()==null || movilidadNuevo.getIntSucuIdSucursal().equals(new Integer(0))){
				mensaje = "Debe seleccionar una sucursal.";
				return;
			}
			if(movilidadNuevo.getIntSudeIdSubsucursal()==null || movilidadNuevo.getIntSudeIdSubsucursal().equals(new Integer(0))){
				mensaje = "Debe seleccionar una subsucursal.";
				return;
			}
			if(movilidadNuevo.getIntIdArea()==null || movilidadNuevo.getIntIdArea().equals(new Integer(0))){
				mensaje = "Debe seleccionar un área.";
				return;
			}
			//fin validaciones
			movilidadNuevo.setListaMovilidadDetalle(listaMovilidadDetalle);
			
			if(registrarNuevo){
				log.info("--registrar");
				movilidadNuevo.getId().setIntPersEmpresaMovilidad(usuario.getPerfil().getId().getIntPersEmpresaPk());
				movilidadNuevo.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD);
				movilidadNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
				
				movilidadNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				movilidadNuevo.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);
				
				egresoFacade.grabarMovilidad(movilidadNuevo);
				mensaje = "Se registró correctamente la planilla de movimientos.";
			}else{
				log.info("--modificar");
				movilidadNuevo.setIntPersPersonaEdita(usuario.getIntPersPersonaPk());
				movilidadNuevo.setIntPersEmpresaEdita(usuario.getPerfil().getId().getIntPersEmpresaPk());				
				
				egresoFacade.modificarMovilidad(movilidadNuevo);
				mensaje = "Se modificó correctamente la planilla de movimientos.";				
			}
			buscar();
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el proceso de registro de planillda de movilidad. "+e.getMessage();
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	//Modificado 16.12.2013 JCHAVEZ
	public void buscar(){
		try{
			//Agregado 16.12.2013 JCHAVEZ
			movilidadFiltro.getId().setIntPersEmpresaMovilidad(SESION_IDEMPRESA);
			if (movilidadFiltro.getStrTextoFiltro().trim().equals("")) {
				movilidadFiltro.setStrTextoFiltro(null);
			}
			if (movilidadFiltro.getIntSucuIdSucursal().equals(0)) {
				movilidadFiltro.setIntSucuIdSucursal(null);
			}
			//
			if(movilidadFiltro.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
				movilidadFiltro.setIntParaEstado(null);
			}
			
			String strPeriodo="";
			if (movilidadFiltro.getIntMes().equals(-1)) {
				strPeriodo = movilidadFiltro.getIntAño()+"00";
			}else{
				if(movilidadFiltro.getIntMes().intValue()<10){
					strPeriodo = movilidadFiltro.getIntAño()+"0"+movilidadFiltro.getIntMes();
				}else{
					strPeriodo = movilidadFiltro.getIntAño()+""+movilidadFiltro.getIntMes();
				}
			}
			
			movilidadFiltro.setIntPeriodo(Integer.parseInt(strPeriodo));
			
			if(dtDesdeFiltro!=null){
				movilidadFiltro.setTsFechaDesde(new Timestamp(dtDesdeFiltro.getTime()));
			}else{
				movilidadFiltro.setTsFechaDesde(null);
			}
			
			if(dtHastaFiltro!=null){
				//Se añade un dia extra en fecha fin porque orginalmente el calendar da la fecha con 00h 00m 00s y ocasiona
				//seleccion de rangos erroneos con fechas registradas en la base de datos que si poseen horas.
				movilidadFiltro.setTsFechaHasta(new Timestamp(dtHastaFiltro.getTime()+1 * 24 * 60 * 60 * 1000));
			}else{
				movilidadFiltro.setTsFechaHasta(null);
			}
								
			
			listaMovilidad = egresoFacade.buscarMovilidad(movilidadFiltro);
			
//			Movilidad movilidad;
//			for(Object o : listaMovilidad){
//				movilidad = (Movilidad)o;
//				generarEtiquetaUsuarioBusqueda(movilidad);
//				generarEtiquetaSucursalBusqueda(movilidad);
//			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
//	private Movilidad generarEtiquetaSucursalBusqueda(Movilidad movilidad) throws BusinessException{
//		String strEtiqueta = "";
//		
//		Subsucursal subSucursal = empresaFacade.getSubSucursalPorIdSubSucursal(movilidad.getIntSudeIdSubsucursal());
//		
//		strEtiqueta = movilidad.getSucursal().getJuridica().getStrSiglas() + " - " +subSucursal.getStrDescripcion();
//		
//		movilidad.setStrEtiquetaSucursalBusqueda(strEtiqueta);
//		return movilidad;
//	}
//	
//	private Movilidad generarEtiquetaUsuarioBusqueda(Movilidad movilidad) throws BusinessException{
//		Persona persona = movilidad.getPersona();
//		Documento documento = contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(persona.getIntIdPersona(), Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI));
//		String strEtiqueta = "";
//		
//		strEtiqueta = persona.getIntIdPersona()+" - "+persona.getNatural().getStrNombres()+" "+persona.getNatural().getStrApellidoPaterno()+" "+persona.getNatural().getStrApellidoMaterno();
//		strEtiqueta = strEtiqueta + " - "+documento.getStrNumeroIdentidad();
//		
//		movilidad.setStrEtiquetaUsuarioBusqueda(strEtiqueta);
//		return movilidad;	
//	}
	
	private Movilidad generarEtiquetaUsuario(Movilidad movilidad) throws BusinessException{
		Persona persona = movilidad.getPersona();
		Documento documento = contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(persona.getIntIdPersona(), Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI));
		String strEtiqueta = "";
		
		strEtiqueta = persona.getIntIdPersona()+" - "+persona.getNatural().getStrNombres()+" "+persona.getNatural().getStrApellidoPaterno()+" "+persona.getNatural().getStrApellidoMaterno();
		strEtiqueta = strEtiqueta + " - DNI : "+documento.getStrNumeroIdentidad();		
		
		movilidad.setStrEtiquetaUsuario(strEtiqueta);
		return movilidad;
	}
	
	private Movilidad generarPeriodo(Movilidad movilidad){
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
		movilidad.setIntPeriodo(Integer.parseInt(strPeriodo));
		
		return movilidad;
	}

	private void cargarListaSucursal(Movilidad movilidad) throws BusinessException{
		listaSucursal = new ArrayList<Sucursal>();
		List<EmpresaUsuario> listaEmpresaUsuario = empresaFacade.getListaEmpresaUsuarioPorIdPersona(movilidad.getIntPersPersonaUsuario());
		for(EmpresaUsuario empresaUsuario : listaEmpresaUsuario){
			listaSucursal.addAll(empresaFacade.getListaSucursalPorPkEmpresaUsuario(empresaUsuario.getId()));
		}
		log.info("listaSucursal:"+listaSucursal.size());
		
		//cargamos la juridica de cada sucursal;
		for(Object o : listaSucursal){
			Sucursal sucursal = (Sucursal)o;
			sucursal.setJuridica(personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk()));
			log.info("sucursal: "+sucursal.getId().getIntIdSucursal()+" "+sucursal.getIntPersPersonaPk());
		}
			
	}
	
	public void seleccionarSucursal(){
		try{
			if(movilidadNuevo.getIntSucuIdSucursal().equals(new Integer(0))){
				listaSubsucursal = new ArrayList<Subsucursal>();
				listaArea = new ArrayList<Area>();
				return;
			}
			listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(movilidadNuevo.getIntSucuIdSucursal());		
			
			
			Sucursal sucursalSeleccionada = null;
			for(Object o : listaSucursal){
				Sucursal sucursal = (Sucursal)o;
				if(sucursal.getId().getIntIdSucursal().equals(movilidadNuevo.getIntSucuIdSucursal())){
					sucursalSeleccionada = sucursal;
				}
			}
			listaArea = empresaFacade.getListaAreaPorSucursal(sucursalSeleccionada);
			
			if(movilidadNuevo.getId().getIntItemMovilidad()==null){
				movilidadNuevo.setIntSudeIdSubsucursal(0);
				movilidadNuevo.setIntIdArea(0);
			}
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}

	
	public void validarDatos(){
		try{
			Persona persona = buscarPersona();
			if(persona == null){
				return;
			}
			datosValidados = Boolean.TRUE;
			movilidadNuevo = new Movilidad();
			movilidadNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			movilidadNuevo.setPersona(persona);
			movilidadNuevo.setIntPersEmpresaUsuario(usuario.getPerfil().getId().getIntPersEmpresaPk());
			movilidadNuevo.setIntPersPersonaUsuario(persona.getIntIdPersona());
			
			generarPeriodo(movilidadNuevo);
			generarEtiquetaUsuario(movilidadNuevo);
			cargarListaSucursal(movilidadNuevo);
			
			listaSubsucursal = new ArrayList<Subsucursal>();
			
			if(persona.getIntIdPersona().equals(usuario.getIntPersPersonaPk())){
				movilidadNuevo.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				seleccionarSucursal();
				movilidadNuevo.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			}
			
			listaMovilidadDetalle = new ArrayList<MovilidadDetalle>();			
			
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public Persona buscarPersona() throws BusinessException{
		
		Integer intEmpresa = usuario.getPerfil().getId().getIntPersEmpresaPk();
		Persona personaBus = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI), strFiltroDNI, intEmpresa);
		if(personaBus==null){
			log.info("personaBus:null");
			return null;
		}
		
		//Solo para Personas que poseen rol 'Personal' y 'Usuario' 
		Boolean poseeRolPersonal = Boolean.FALSE;
		//Boolean poseeRolUsuario = Boolean.FALSE;
		for(PersonaRol personaRol : personaBus.getPersonaEmpresa().getListaPersonaRol()){
			//log.info("rol:"+personaRol.getId().getIntParaRolPk());
			if(personaRol.getId().getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_PERSONAL)){
				poseeRolPersonal = Boolean.TRUE;
				break;
			}/*
			if(personaRol.getId().getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_USUARIO)){
				poseeRolUsuario = Boolean.TRUE;
			}*/
		}
		
		if(poseeRolPersonal){
			return personaBus;
		}
		return null;
	}
	
	public void abrirPopUpMovilidadDetalle(){
		try{
			movilidadDetalleAgregar = new MovilidadDetalle();
			
			mostrarMensajePopUp = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarMovilidadDetalle(ActionEvent event){
		try{
			MovilidadDetalle movilidadDetalle = (MovilidadDetalle)event.getComponent().getAttributes().get("item");
			listaMovilidadDetalle.remove(movilidadDetalle);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean validarMontoAcumulado(MovilidadDetalle movilidadDetalleAgregar) throws BusinessException{
		montoAcumulado = new BigDecimal(0);
		for(Object o : listaMovilidadDetalle){
			MovilidadDetalle movilidadDetalle = (MovilidadDetalle)o;
			if(movilidadDetalle.getDtFechaMovilidad().equals(movilidadDetalleAgregar.getDtFechaMovilidad())){
				montoAcumulado = montoAcumulado.add(movilidadDetalle.getBdMonto());
			}			
		}
		
		List<MovilidadDetalle> listaMovilidadDetalleBD = egresoFacade.getListaMovilidadDetalleValidar(movilidadNuevo, movilidadDetalleAgregar);
		for(MovilidadDetalle movilidadDetalleBD : listaMovilidadDetalleBD){
			log.info(movilidadDetalleBD);
			if(registrarNuevo || !(movilidadDetalleBD.getId().getIntItemMovilidad().equals(movilidadNuevo.getId().getIntItemMovilidad()))){
				montoAcumulado = montoAcumulado.add(movilidadDetalleBD.getBdMonto());
			}
		}
		
		BigDecimal montoValidar = montoAcumulado.add(movilidadDetalleAgregar.getBdMonto());
		log.info("montoValidar:"+montoValidar);
		if(montoValidar.compareTo(Constante.MOVIMIENTO_MONTOMAXIMO)>0){
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (Movilidad)event.getComponent().getAttributes().get("item");
			if(registroSeleccionado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) 
			|| registroSeleccionado.getEgreso()!=null){
				mostrarBtnEliminar= Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
				registrarNuevo = Boolean.FALSE;
				mostrarPanelInferior = Boolean.TRUE;				
				deshabilitarNuevo = Boolean.TRUE;
				datosValidados = Boolean.TRUE;
				ocultarMensaje();
				cargarRegistro();
			}else{
				mostrarBtnEliminar = Boolean.TRUE;
			}
			log.info("reg selec:"+registroSeleccionado);						
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarRegistro(){
		try{
						
			registroSeleccionado.setIntPersPersonaEdita(usuario.getIntPersPersonaPk());
			registroSeleccionado.setIntPersEmpresaEdita(usuario.getPerfil().getId().getIntPersEmpresaPk());
			
			egresoFacade.anularMovilidad(registroSeleccionado);
			listaMovilidad.remove(registroSeleccionado);
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarRegistro() throws BusinessException{
		movilidadNuevo = registroSeleccionado;
		listaMovilidadDetalle = movilidadNuevo.getListaMovilidadDetalle();
		
//		generarEtiquetaUsuario(movilidadNuevo);
		
		cargarListaSucursal(movilidadNuevo);		
		seleccionarSucursal();
	}
	
	public void modificarRegistro(){
		try{
			log.info("--modificarRegistro");	
			cargarRegistro();
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
	
	
	public void aceptarAgregarMovilidadDetalle(){
		try{
			mostrarMensajePopUp = Boolean.TRUE;
			if(movilidadDetalleAgregar.getDtFechaMovilidad()==null){
				mensajePopUp = "Debe seleccionar una fecha.";
				return;
			}
			if(movilidadDetalleAgregar.getDtFechaMovilidad().compareTo(new Date())>0){
				mensajePopUp = "Debe seleccionar una fecha pasada o presente.";
				return;
			}
			if(movilidadDetalleAgregar.getBdMonto()==null || movilidadDetalleAgregar.getBdMonto().equals(new Integer(0))){
				mensajePopUp = "Debe ingresar un monto correcto.";
				return;
			}
			//Modificado 13.12.2013 JCHAVEZ
			if(!validarMontoAcumulado(movilidadDetalleAgregar)){
				mensajePopUp = "Para la fecha dada se sobrepasa el monto acumulado máximo permitido de S/"+Constante.MOVIMIENTO_MONTOMAXIMO+".00. Actualmente acumula un total de de S/"+montoAcumulado+".";
				return;
			}
			if(movilidadDetalleAgregar.getStrMotivo()== null || movilidadDetalleAgregar.getStrMotivo().isEmpty()){
				mensajePopUp = "Debe ingresar un motivo.";
				return;
			}
			if(movilidadDetalleAgregar.getStrDestino()== null || movilidadDetalleAgregar.getStrDestino().isEmpty()){
				mensajePopUp = "Debe ingresar un destino.";
				return;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(movilidadDetalleAgregar.getDtFechaMovilidad());
			String strPeriodo = calendar.get(Calendar.YEAR)+""+((calendar.get(Calendar.MONTH)+1)<10?"0"+(calendar.get(Calendar.MONTH)+1):(calendar.get(Calendar.MONTH)+1));
			if (Integer.parseInt(strPeriodo) != movilidadNuevo.getIntPeriodo()) {
				mensajePopUp = "La fecha dada no puede ser de un periodo inferior.";
				return;
			}
			mostrarMensajePopUp = Boolean.FALSE;
			
			movilidadDetalleAgregar.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			movilidadDetalleAgregar.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			movilidadDetalleAgregar.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			movilidadDetalleAgregar.setIntPersEmpresaUsuario(usuario.getPerfil().getId().getIntPersEmpresaPk());
			
			listaMovilidadDetalle.add(movilidadDetalleAgregar);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
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
	
	
	public void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	//Agregado por JCHAVEZ 16.12.2013
	public void getListSucursales() {
		log.info("-------------------------------------Debugging AsociativoController.getListSucursales-------------------------------------");
		try {
			if(listJuridicaSucursal == null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuario.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuario.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuario.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuario.getSubSucursal().getId().getIntIdSubSucursal();
	}
	
	public String getInicioPage() {
		cargarUsuario();
		if(usuario!=null){
			limpiarFormulario();
			listaMovilidad.clear();
			deshabilitarPanelInferior();
		}else log.error("--Usuario obtenido es NULL.");
		return "";
	}
	
	public void limpiarFormulario(){
		movilidadFiltro.setIntTipoBusqueda(Constante.FILTROMOVIMIENTO_DNI);
		movilidadFiltro.setStrTextoFiltro("");
		movilidadFiltro.setIntSucuIdSucursal(0);
		movilidadFiltro.setIntParaEstado(-1);
		dtDesdeFiltro = null;
		dtHastaFiltro = null;
		movilidadFiltro.setIntAño(Calendar.getInstance().get(Calendar.YEAR));
		movilidadFiltro.setIntMes(-1);
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
	public Movilidad getMovilidadNuevo() {
		return movilidadNuevo;
	}
	public void setMovilidadNuevo(Movilidad movilidadNuevo) {
		this.movilidadNuevo = movilidadNuevo;
	}
	public Movilidad getMovilidadFiltro() {
		return movilidadFiltro;
	}
	public void setMovilidadFiltro(Movilidad movilidadFiltro) {
		this.movilidadFiltro = movilidadFiltro;
	}
	public boolean isDatosValidados() {
		return datosValidados;
	}
	public void setDatosValidados(boolean datosValidados) {
		this.datosValidados = datosValidados;
	}
	public MovilidadDetalle getMovilidadDetalleAgregar() {
		return movilidadDetalleAgregar;
	}
	public void setMovilidadDetalleAgregar(MovilidadDetalle movilidadDetalleAgregar) {
		this.movilidadDetalleAgregar = movilidadDetalleAgregar;
	}
	public String getStrFiltroDNI() {
		return strFiltroDNI;
	}
	public void setStrFiltroDNI(String strFiltroDNI) {
		this.strFiltroDNI = strFiltroDNI;
	}
	public String getStrEtiquetaEmpresa() {
		return strEtiquetaEmpresa;
	}
	public void setStrEtiquetaEmpresa(String strEtiquetaEmpresa) {
		this.strEtiquetaEmpresa = strEtiquetaEmpresa;
	}
	public Date getDtDesdeFiltro() {
		return dtDesdeFiltro;
	}
	public void setDtDesdeFiltro(Date dtDesdeFiltro) {
		this.dtDesdeFiltro = dtDesdeFiltro;
	}
	public Date getDtHastaFiltro() {
		return dtHastaFiltro;
	}
	public void setDtHastaFiltro(Date dtHastaFiltro) {
		this.dtHastaFiltro = dtHastaFiltro;
	}

	//Agregado 16.12.2013 JCHAVEZ
	public static Logger getLog() {
		return log;
	}


	public static void setLog(Logger log) {
		MovilidadController.log = log;
	}


	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}


	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}


	public ContactoFacadeRemote getContactoFacade() {
		return contactoFacade;
	}


	public void setContactoFacade(ContactoFacadeRemote contactoFacade) {
		this.contactoFacade = contactoFacade;
	}


	public BancoFacadeLocal getBancoFacade() {
		return bancoFacade;
	}


	public void setBancoFacade(BancoFacadeLocal bancoFacade) {
		this.bancoFacade = bancoFacade;
	}


	public EgresoFacadeLocal getEgresoFacade() {
		return egresoFacade;
	}


	public void setEgresoFacade(EgresoFacadeLocal egresoFacade) {
		this.egresoFacade = egresoFacade;
	}


	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}


	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}


	public List<Movilidad> getListaMovilidad() {
		return listaMovilidad;
	}


	public void setListaMovilidad(List<Movilidad> listaMovilidad) {
		this.listaMovilidad = listaMovilidad;
	}


	public List<MovilidadDetalle> getListaMovilidadDetalle() {
		return listaMovilidadDetalle;
	}


	public void setListaMovilidadDetalle(
			List<MovilidadDetalle> listaMovilidadDetalle) {
		this.listaMovilidadDetalle = listaMovilidadDetalle;
	}


	public List<Tabla> getListaAnios() {
		return listaAnios;
	}


	public void setListaAnios(List<Tabla> listaAnios) {
		this.listaAnios = listaAnios;
	}


	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}


	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}


	public List<Subsucursal> getListaSubsucursal() {
		return listaSubsucursal;
	}


	public void setListaSubsucursal(List<Subsucursal> listaSubsucursal) {
		this.listaSubsucursal = listaSubsucursal;
	}


	public List<Area> getListaArea() {
		return listaArea;
	}


	public void setListaArea(List<Area> listaArea) {
		this.listaArea = listaArea;
	}


	public Area getArea() {
		return area;
	}


	public void setArea(Area area) {
		this.area = area;
	}


	public Movilidad getRegistroSeleccionado() {
		return registroSeleccionado;
	}


	public void setRegistroSeleccionado(Movilidad registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public BigDecimal getMontoAcumulado() {
		return montoAcumulado;
	}


	public void setMontoAcumulado(BigDecimal montoAcumulado) {
		this.montoAcumulado = montoAcumulado;
	}


	public List<Sucursal> getListJuridicaSucursal() {
		return listJuridicaSucursal;
	}


	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
	}


	public Integer getSESION_IDEMPRESA() {
		return SESION_IDEMPRESA;
	}


	public void setSESION_IDEMPRESA(Integer sESION_IDEMPRESA) {
		SESION_IDEMPRESA = sESION_IDEMPRESA;
	}


	public Integer getSESION_IDUSUARIO() {
		return SESION_IDUSUARIO;
	}


	public void setSESION_IDUSUARIO(Integer sESION_IDUSUARIO) {
		SESION_IDUSUARIO = sESION_IDUSUARIO;
	}


	public Integer getSESION_IDSUCURSAL() {
		return SESION_IDSUCURSAL;
	}


	public void setSESION_IDSUCURSAL(Integer sESION_IDSUCURSAL) {
		SESION_IDSUCURSAL = sESION_IDSUCURSAL;
	}


	public Integer getSESION_IDSUBSUCURSAL() {
		return SESION_IDSUBSUCURSAL;
	}


	public void setSESION_IDSUBSUCURSAL(Integer sESION_IDSUBSUCURSAL) {
		SESION_IDSUBSUCURSAL = sESION_IDSUBSUCURSAL;
	}


	public int getCantidadAñosLista() {
		return cantidadAñosLista;
	}
}