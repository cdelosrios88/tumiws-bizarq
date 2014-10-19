package pe.com.tumi.tesoreria.logistica.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.DocumentoPK;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorDetalle;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorId;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;
import pe.com.tumi.tesoreria.popup.JuridicaController;

public class ProveedorController {
	
	
	protected static Logger log = Logger.getLogger(ProveedorController.class);
	
	TablaFacadeRemote 		tablaFacade;
	LogisticaFacadeLocal 	logisticaFacade;
	PersonaFacadeRemote		personaFacade;
	ContactoFacadeRemote	contactoFacade;
	
	private	List<Proveedor>			listaProveedor;
	private	List<ProveedorDetalle>	listaProveedorDetalle;
	private List<Tabla>				listaTablaTipoProveedor;
	private	List<Persona>			listaPersonaEmpresa;
	
	private	Proveedor	proveedorNuevo;
	private	Proveedor	proveedorFiltro;
	private	Proveedor	registroSeleccionado;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private	Integer		intTipoPersona;
	private	Integer		intTipoDocumento;
	private	String		strTextoValidar;
	private	Integer		EMPRESA_USUARIO;
	private Integer		intTipoProveedorFiltro;
	private Integer		intTipoBusquedaFiltro;
	private String		strTextoFiltro;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean datosValidados;
	private boolean	registrarNuevoProveedor;	

	private JuridicaController juridicaController;
	
	
	public ProveedorController(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario!=null)	cargarValoresIniciales();
		else	log.error("--Usuario obtenido es NULL.");
		
	}
	
	public void cargarValoresIniciales(){
		try{
			EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
			listaProveedor = new ArrayList<Proveedor>();
			proveedorFiltro = new Proveedor();
			
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			logisticaFacade = (LogisticaFacadeLocal)EJBFactory.getLocal(LogisticaFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			contactoFacade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			
			listaTablaTipoProveedor = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOPROVEEDOR));
			listaProveedor = new ArrayList<Proveedor>();
			cargarEmpresas();
			
			for(Tabla tabla : listaTablaTipoProveedor)
				tabla.setChecked(Boolean.FALSE);			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	public void buscar(){
		try{
			log.info("--buscar");
			List<Persona> listaPersona = new ArrayList<Persona>();
			
			if(strTextoFiltro!=null & !strTextoFiltro.isEmpty())
				listaPersona = personaFacade.buscarListaPersonaParaFiltro(intTipoBusquedaFiltro, strTextoFiltro);
			
			//Revisa que las personas tengan un registro de proveedor asociado
			listaProveedor = new ArrayList<Proveedor>();
			for(Persona persona : listaPersona){
				ProveedorId proveedorId = new ProveedorId();
				proveedorId.setIntPersEmpresa(EMPRESA_USUARIO);
				proveedorId.setIntPersPersona(persona.getIntIdPersona());
				
				Proveedor proveedor = logisticaFacade.getProveedorPorPK(proveedorId);
				//Autor: jchavez / Tarea: Creación / Fecha: 22.09.2014
				if (proveedor!=null) {
					proveedor.setPersona(persona);
					listaProveedor.add(proveedor);
				}
				//Fin jchavez 22.09.2014
			}
			
			//Filtra de acuerdo a tipo de proveedor
			if(!intTipoProveedorFiltro.equals(Constante.PARAM_T_TIPOPROVEEDOR_TODOS)){
				boolean poseeTipoProveedor;
				List<Proveedor> listaProveedorTemp = new ArrayList<Proveedor>();
				for(Proveedor proveedor : listaProveedor){
					poseeTipoProveedor = Boolean.FALSE;
					for(ProveedorDetalle proveedorDetalle : proveedor.getListaProveedorDetalle()){
						if(proveedorDetalle.getIntParaTipoProveedor().equals(intTipoProveedorFiltro)){
							poseeTipoProveedor = Boolean.TRUE;
							break;
						}
					}
					if(poseeTipoProveedor)
						listaProveedorTemp.add(proveedor);
				}
				listaProveedor = listaProveedorTemp;
			}

			for(Proveedor proveedor : listaProveedor){
				log.info(proveedor);
				//proveedor.getPersona().setNatural(personaFacade.getNaturalPorPK(proveedor.getPersona().getIntIdPersona()));
				proveedor.setPersona(personaFacade.getPersonaDetalladaPorIdPersonaYEmpresa(proveedor.getId().getIntPersPersona(), EMPRESA_USUARIO));
				proveedor.getPersona().setNatural(personaFacade.getNaturalDetalladaPorIdPersona(proveedor.getPersona().getIntIdPersona()));
				proveedor.getPersona().setJuridica(personaFacade.getJuridicaPorPK(proveedor.getPersona().getIntIdPersona()));
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarPersona(){
		try{
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				intTipoDocumento = Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI);
				
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				intTipoDocumento = Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_RUC);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	private void grabarDocumento(Integer intIdPersona) throws BusinessException{
		Documento documento = new Documento();
		documento.setId(new DocumentoPK());
		documento.getId().setIntIdPersona(intIdPersona);
		documento.setIntTipoIdentidadCod(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI));
		documento.setStrNumeroIdentidad(strTextoValidar);
		documento.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		personaFacade.grabarDocumento(documento);
	}	
	
	private void registrarProveedor(Persona persona)throws Exception{
		if(registrarNuevoProveedor){
			log.info("--registrando nuevo proveedor");				
			proveedorNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			proveedorNuevo.getId().setIntPersPersona(persona.getIntIdPersona());
			proveedorNuevo.setListaProveedorDetalle(listaProveedorDetalle);				
			logisticaFacade.grabarProveedor(proveedorNuevo);				
		}else{
			log.info("--modificando proveedor");			
			proveedorNuevo.setListaProveedorDetalle(listaProveedorDetalle);
			logisticaFacade.modificarProveedor(proveedorNuevo);
		}
	}
	
	public void grabar(){
		log.info("--grabar");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {			
			//validaciones
			if(listaProveedorDetalle==null || listaProveedorDetalle.isEmpty()){
				mensaje = "Debe seleccionar al menos un tipo de proveedor.";
				return;
			}
			
			Persona persona = juridicaController.getPerJuridica();
			if(persona.getStrRuc()==null || persona.getStrRuc().isEmpty()){
				mensaje = "Debe ingresar el RUC de la persona.";return;
			}else if(persona.getStrRuc().trim().length()!=11){
				mensaje = "El RUC debe poseer 11 caracteres.";
				persona.setStrRuc(null);
				return;
			}
			
			if(persona.getJuridica().getStrRazonSocial()==null || persona.getJuridica().getStrRazonSocial().isEmpty()){
				mensaje = "Debe de ingresar una razon social para el proveedor.";return;
			}
			if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				Natural natural = persona.getNatural();
				if(natural.getStrApellidoPaterno()==null || natural.getStrApellidoPaterno().isEmpty()){
					mensaje = "Debe ingresar un apellido paterno para la persona.";return;
				}if(natural.getStrApellidoMaterno()==null || natural.getStrApellidoMaterno().isEmpty()){
					mensaje = "Debe ingresar un apellido materno para la persona.";return;
				}if(natural.getStrNombres()==null || natural.getStrNombres().isEmpty()){
					mensaje = "Debe ingresar un nombre para la persona.";return;
				}
			}
			if(registrarNuevo){
				persona = juridicaController.savePerJuridicaProveedor();
				log.info("idPersona:"+persona.getIntIdPersona());
				if(persona.getIntIdPersona()!=null){
					registrarProveedor(persona);
					if(registrarNuevoProveedor){						
						if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
							log.info("--registrando persona natural");
							persona.getNatural().setIntIdPersona(persona.getIntIdPersona());
							persona.getNatural().setIntTieneEmpresa(Constante.TIENEMPRESA_SI);
							personaFacade.grabarNatural(persona.getNatural());
							grabarDocumento(persona.getIntIdPersona());
							
						}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
							log.info("--registrando persona juridica");							
						}
						
					}else{
						if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
							log.info("--registrando persona natural");
							personaFacade.modificarNatural(persona.getNatural());
						}
					}
				}else{
					throw new Exception();
				}
				mensaje ="Se registró correctamente a el proveedor.";
			
			}else{
				persona = juridicaController.savePerJuridicaProveedor();
				log.info("idPersona:"+persona.getIntIdPersona());
				
				registrarProveedor(persona);
				log.info("intTipoPersona:"+intTipoPersona);
				if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
					log.info("--registrando persona natural");
					personaFacade.modificarNatural(persona.getNatural());
				}
				
				mensaje ="Se modificó correctamente a el proveedor.";
			}
			
			buscar();
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el proceso de registro de proveedor.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	public void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			strTextoValidar = "";
			datosValidados = Boolean.FALSE;
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
			intTipoDocumento = Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_RUC);
			
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			ocultarMensaje();
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
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}
	
	private Proveedor obtenerProveedor(Integer IdPersona) throws BusinessException{
		ProveedorId proveedorId = new ProveedorId();
		proveedorId.setIntPersEmpresa(EMPRESA_USUARIO);
		proveedorId.setIntPersPersona(IdPersona);
		return logisticaFacade.getProveedorPorPK(proveedorId);
		
	}
	
	private boolean esEmpresa(String strRuc)throws Exception{
		boolean esUnaEmpresa = Boolean.FALSE;
		for(Persona personaEmpresa : listaPersonaEmpresa){
			if(personaEmpresa.getStrRuc().equals(strRuc)){
				esUnaEmpresa = Boolean.TRUE;
			}
		}
		return esUnaEmpresa;
	}
	
	private void cargarEmpresas() throws Exception{
		//List<Empresa> listaEmpresa = personaFacade.getListaEmpresaTodos();
		List<Empresa> listaEmpresa = new ArrayList<Empresa>();
		Empresa emp = new Empresa();
		emp.setIntIdEmpresa(2);
		Empresa emp2 = new Empresa();
		emp2.setIntIdEmpresa(3);
		listaEmpresa.add(emp);
		listaEmpresa.add(emp2);
		listaPersonaEmpresa = new ArrayList<Persona>();
		for(Empresa empresa : listaEmpresa){
			listaPersonaEmpresa.add(personaFacade.getPersonaPorPK(empresa.getIntIdEmpresa()));			
		}
	}
	
	public void validarDatos(){
		try{
			datosValidados = Boolean.FALSE;			
			habilitarGrabar = Boolean.FALSE;
			
			//validamos que 'strTextoValidar' posea la longitud correcta
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL) 
				&& (strTextoValidar==null || strTextoValidar.isEmpty()  || !(strTextoValidar.length()== Constante.LONGITUD_DNI))){
				return;
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)
					&& (strTextoValidar==null || strTextoValidar.isEmpty()  || !(strTextoValidar.length()== Constante.LONGITUD_RUC))){
				return;
			}
			
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA) && esEmpresa(strTextoValidar))
				return;
			
				
			juridicaController = (JuridicaController)getSessionBean("perJuridicaController");
			Persona persona = juridicaController.validarProveedor(intTipoPersona, strTextoValidar);
			
			if(persona.getIntIdPersona()!=null){
				log.info("persona existe");
				proveedorNuevo = obtenerProveedor(persona.getIntIdPersona());
				if(proveedorNuevo!=null){
					log.info("persona es proveedora");
					listaProveedorDetalle = proveedorNuevo.getListaProveedorDetalle();
					registrarNuevoProveedor = Boolean.FALSE;
				}else{
					log.info("persona no es proveedora");
					proveedorNuevo = new Proveedor();
					listaProveedorDetalle = new ArrayList<ProveedorDetalle>();
					registrarNuevoProveedor = Boolean.TRUE;
				}
				
				if(persona.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					deshabilitarNuevo = Boolean.FALSE;
				}else{
					deshabilitarNuevo = Boolean.TRUE;
					habilitarGrabar = Boolean.FALSE;
				}
				registrarNuevo = Boolean.FALSE;				
				//log.info("deshabilitarNuevo:"+deshabilitarNuevo);
			}else{
				log.info("persona no existente");
				proveedorNuevo = new Proveedor();
				listaProveedorDetalle = new ArrayList<ProveedorDetalle>();
				registrarNuevoProveedor = Boolean.TRUE;
				registrarNuevo = Boolean.TRUE;				
			}

			datosValidados = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;
			
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Ha ocurrido un error durante el proceso de carga del proveedor.");
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (Proveedor)event.getComponent().getAttributes().get("item");
			//log.info("estado:"+registroSeleccionado.getPersona().getIntEstadoCod());
			if(registroSeleccionado.getPersona().getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				cargarRegistro();
				mostrarBtnEliminar= Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
				registrarNuevo = Boolean.FALSE;
				mostrarPanelInferior = Boolean.TRUE;
				ocultarMensaje();
				deshabilitarNuevo = Boolean.TRUE;
				datosValidados = Boolean.TRUE;				
			}else{
				mostrarBtnEliminar = Boolean.TRUE;
			}
			log.info("reg selec:"+registroSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarAgregarProveedorDetalle(){
		try{
			listaProveedorDetalle.clear();
			for(Tabla tabla : listaTablaTipoProveedor){
				//log.info("tabla:"+tabla.getIntIdDetalle());
				if(tabla.getChecked()){
					//log.info("se agrega");
					ProveedorDetalle proveedorDetalle = new ProveedorDetalle();
					proveedorDetalle.setIntParaTipoProveedor(tabla.getIntIdDetalle());
					listaProveedorDetalle.add(proveedorDetalle);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarProveedorDetalle(ActionEvent event){
		try{
			ProveedorDetalle proveedorDetalle = (ProveedorDetalle)event.getComponent().getAttributes().get("item");
			listaProveedorDetalle.remove(proveedorDetalle);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpTipoProveedor(){
		try{
			//marcamos los que ya han sido seleccionados
			for(ProveedorDetalle proveedorDetalle : listaProveedorDetalle)
				for(Tabla tabla : listaTablaTipoProveedor)
					if(proveedorDetalle.getIntParaTipoProveedor().equals(tabla.getIntIdDetalle()))
						tabla.setChecked(Boolean.TRUE);
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void modificarRegistro(){
		try{
			cargarRegistro();
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			datosValidados = Boolean.TRUE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarRegistro() throws BusinessException, Exception{
		juridicaController = (JuridicaController)getSessionBean("perJuridicaController");		
		proveedorNuevo = registroSeleccionado;
		listaProveedorDetalle = proveedorNuevo.getListaProveedorDetalle();
		
		Persona persona = proveedorNuevo.getPersona();		
		//intTipoPersona = persona.getIntTipoPersonaCod();
		
		if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
			Documento documento = contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(persona.getIntIdPersona(), Constante.PARAM_T_TIPOPERSONA_NATURAL);
			strTextoValidar = documento.getStrNumeroIdentidad();
		}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
			strTextoValidar = persona.getStrRuc();
		}
				
		juridicaController.validarProveedor(persona.getIntTipoPersonaCod(),strTextoValidar);
	}
	
	public void eliminarRegistro(){
		try{
			//listaProveedor.remove(registroSeleccionado);
			registroSeleccionado.getPersona().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			personaFacade.modificarPersona(registroSeleccionado.getPersona());
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public String getInicioPage() {
		deshabilitarPanelInferior(null);
		listaProveedor.clear();
		strTextoFiltro = "";
		return "";
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		return sesion.getAttribute(beanName);
	}
	
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
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
	public Proveedor getProveedorNuevo() {
		return proveedorNuevo;
	}
	public void setProveedorNuevo(Proveedor proveedorNuevo) {
		this.proveedorNuevo = proveedorNuevo;
	}
	public Proveedor getProveedorFiltro() {
		return proveedorFiltro;
	}
	public void setProveedorFiltro(Proveedor proveedorFiltro) {
		this.proveedorFiltro = proveedorFiltro;
	}
	public Proveedor getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(Proveedor registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public boolean isDatosValidados() {
		return datosValidados;
	}
	public void setDatosValidados(boolean datosValidados) {
		this.datosValidados = datosValidados;
	}
	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}
	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}
	public Integer getIntTipoDocumento() {
		return intTipoDocumento;
	}
	public void setIntTipoDocumento(Integer intTipoDocumento) {
		this.intTipoDocumento = intTipoDocumento;
	}
	public String getStrTextoFiltro() {
		return strTextoFiltro;
	}
	public void setStrTextoFiltro(String strTextoFiltro) {
		this.strTextoFiltro = strTextoFiltro;
	}
	public String getStrTextoValidar() {
		return strTextoValidar;
	}
	public void setStrTextoValidar(String strTextoValidar) {
		this.strTextoValidar = strTextoValidar;
	}
	public Integer getIntTipoProveedorFiltro() {
		return intTipoProveedorFiltro;
	}
	public void setIntTipoProveedorFiltro(Integer intTipoProveedorFiltro) {
		this.intTipoProveedorFiltro = intTipoProveedorFiltro;
	}
	public Integer getIntTipoBusquedaFiltro() {
		return intTipoBusquedaFiltro;
	}
	public void setIntTipoBusquedaFiltro(Integer intTipoBusquedaFiltro) {
		this.intTipoBusquedaFiltro = intTipoBusquedaFiltro;
	}
	public List<Proveedor> getListaProveedor() {
		return listaProveedor;
	}
	public void setListaProveedor(List<Proveedor> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}
	public List<Tabla> getListaTablaTipoProveedor() {
		return listaTablaTipoProveedor;
	}
	public void setListaTablaTipoProveedor(List<Tabla> listaTablaTipoProveedor) {
		this.listaTablaTipoProveedor = listaTablaTipoProveedor;
	}
	public List<ProveedorDetalle> getListaProveedorDetalle() {
		return listaProveedorDetalle;
	}
	public void setListaProveedorDetalle(List<ProveedorDetalle> listaProveedorDetalle) {
		this.listaProveedorDetalle = listaProveedorDetalle;
	}
}