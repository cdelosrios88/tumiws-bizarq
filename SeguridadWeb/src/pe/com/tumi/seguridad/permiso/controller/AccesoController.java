package pe.com.tumi.seguridad.permiso.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeLocal;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;
import pe.com.tumi.seguridad.login.facade.LoginFacadeLocal;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecial;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecialDetalle;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeLocal;


public class AccesoController {

	protected static Logger log = Logger.getLogger(AccesoController.class);	
	
	private PersonaFacadeRemote personaFacade;
	private EmpresaFacadeLocal empresaFacade;
	private PermisoFacadeLocal permisoFacade;
	private TablaFacadeRemote tablaFacade;
	private LoginFacadeLocal loginFacade;
	
	private List listaEmpresas;
	private List listaUsuarios;
	private List listaAccesosEspeciales;
	private List <AccesoEspecialDetalle> listaAccesosEspecialesDetalle;
	private List listaDias;
	private List listaSucursales;
	private List listaMotivos;
	private List listaMotivosNuevo;
	
	private AccesoEspecial accesoEspecialNuevo;
	private AccesoEspecial accesoEspecialFiltro;
	private AccesoEspecial registroSeleccionado;
	private Date fechaInicioFiltro;
	private Date fechaFinFiltro;
	private Date fechaInicioNuevo;
	private Date fechaFinNuevo;
	private Usuario usuario;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean seleccionaFeriados;
	private boolean habilitarDias;
	private boolean habilitarCajaUsuario;
	
	private int intTipoUsuario;
	
	private int intIdNatural;
	private String cajaUsuario;
	private String mensajeOperacion;
	private Integer intTipoAcceso;
	
	public AccesoController(){
		
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				accesoEspecialFiltro = new AccesoEspecial();
				personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
				empresaFacade = (EmpresaFacadeLocal) EJBFactory.getLocal(EmpresaFacadeLocal.class);
				permisoFacade = (PermisoFacadeLocal) EJBFactory.getLocal(PermisoFacadeLocal.class);
				tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				loginFacade = (LoginFacadeLocal) EJBFactory.getLocal(LoginFacadeLocal.class);
				listaEmpresas = personaFacade.getListaJuridicaDeEmpresa();
				//listaUsuarios = empresaFacade.getListaNaturalDeUsuarioPorIdEmpresa(((Juridica)(listaEmpresas.get(0))).getIntIdPersona());
				listaDias = tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_DIASSEMANA_INT);	
				intTipoAcceso = Constante.PARAM_T_ACCESOESPECIAL_FUERAHORA;
				cargarValoresIniciales();
			}else{
				log.error("--Usuario obtenido es NULL.");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarUsuarios(Integer intIdEmpresa) throws BusinessException, Exception{
		UsuarioComp usuarioComp = new UsuarioComp();
		usuarioComp.setEmpresaUsuario(new EmpresaUsuario());
		usuarioComp.getEmpresaUsuario().setId(new EmpresaUsuarioId());
		usuarioComp.setUsuario(new Usuario());
		usuarioComp.getEmpresaUsuario().getId().setIntPersEmpresaPk(intIdEmpresa);
		log.info("intIdEmpresa"+intIdEmpresa);
		listaUsuarios = loginFacade.getListaUsuarioCompDeBusqueda(usuarioComp);
		if(listaUsuarios == null){
			log.info("listaUsuarios null");
		}else{
			log.info("listaUsuarios size "+listaUsuarios.size());
		}
		for(Object o : listaUsuarios){
			UsuarioComp uc = (UsuarioComp)o;
			log.info(uc);
		}
		/*listaUsuarios = empresaFacade.getListaNaturalDeUsuarioPorIdEmpresa(((Juridica)(listaEmpresas.get(0))).getIntIdPersona());
		EmpresaUsuarioId empresaUsuarioId = null;
		for(Natural natural : listaUsuarios){
			empresaUsuarioId = new EmpresaUsuarioId();
			empresaFacade.getListaSucursalPorPkEmpresaUsuario(empresaUsuarioId);
		}*/
	}
	
	
	private void cargarValoresIniciales(){
		try{
			listaAccesosEspeciales = new ArrayList<AccesoEspecial>();
			listaAccesosEspecialesDetalle = new ArrayList<AccesoEspecialDetalle>();
			listaMotivos = new ArrayList<Tabla>();
			listaMotivosNuevo = new ArrayList<Tabla>();
			mensajeOperacion = null;
			cajaUsuario = null;
			intTipoUsuario = 0;
			fechaInicioFiltro = null;
			fechaFinFiltro = null;
			accesoEspecialFiltro = new AccesoEspecial();
			mostrarBtnEliminar = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.FALSE;
			registrarNuevo = Boolean.FALSE;
			habilitarGrabar = Boolean.FALSE;
			habilitarCajaUsuario = Boolean.FALSE;
			
			if(intTipoAcceso.intValue()==Constante.PARAM_T_ACCESOESPECIAL_FUERAHORA.intValue()){
				cargarListaMotivos(Constante.PARAM_T_TIPOSMOTIVOACCESO_A);
			}else if(intTipoAcceso.intValue()==Constante.PARAM_T_ACCESOESPECIAL_SUCURSAL.intValue()){
				cargarListaMotivos(Constante.PARAM_T_TIPOSMOTIVOACCESO_B);
			}else if(intTipoAcceso.intValue()==Constante.PARAM_T_ACCESOESPECIAL_CABINA.intValue()){
				cargarListaMotivos(Constante.PARAM_T_TIPOSMOTIVOACCESO_C);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void cargarListaMotivos(String agrupamiento) throws BusinessException{
		List<Tabla> listaAux;
		listaAux = tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_TIPOSMOTIVOACCESO);
		for(Tabla tabla : listaAux){
			if(tabla.getIntIdDetalle().intValue()==Constante.PARAM_T_TIPOSMOTIVOACCESO_TODOS.intValue()){
				listaMotivos.add(tabla);
			}else{
				if(tabla.getStrIdAgrupamientoA().equalsIgnoreCase(agrupamiento)){
					listaMotivos.add(tabla);
					listaMotivosNuevo.add(tabla);
				}
			}
		}
		
	}
	
	public void seleccionaTipoUsuario(){
		if(intTipoUsuario==Constante.PARAM_T_TIPOUSUARIOACCESOESPECIAL_NINGUNO.intValue()){
			habilitarCajaUsuario = Boolean.FALSE;
			accesoEspecialFiltro.setNaturalOpera(null);
		}else{
			habilitarCajaUsuario = Boolean.TRUE;
		}
	}
	
	public void mostrarFueraHora(ActionEvent event){
		intTipoAcceso = Constante.PARAM_T_ACCESOESPECIAL_FUERAHORA;
		cargarValoresIniciales();
	}
	
	public void mostrarSucursal(ActionEvent event){
		intTipoAcceso = Constante.PARAM_T_ACCESOESPECIAL_SUCURSAL;
		cargarValoresIniciales();
	}
	
	public void mostrarCabina(ActionEvent event){
		intTipoAcceso = Constante.PARAM_T_ACCESOESPECIAL_CABINA;
		cargarValoresIniciales();
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			
			//En caso de que venga de tabSucursal, se carga una lista de sucursales.
			if(intTipoAcceso.intValue() == Constante.PARAM_T_ACCESOESPECIAL_SUCURSAL.intValue()){
				int idEmprea = ((Juridica)listaEmpresas.get(0)).getIntIdPersona();
				listaSucursales = empresaFacade.getListaSucursalPorPkEmpresa(idEmprea);				
			}
			cargarListaAccesosEspecialesDetalle();
			habilitarDias = Boolean.FALSE;
			fechaInicioNuevo = null;
			fechaFinNuevo = null;
			accesoEspecialNuevo = new AccesoEspecial();
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			seleccionaFeriados = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscar(){
		
		try {
			
			//Cuando se selecciona "Todos" en estados
			if(accesoEspecialFiltro.getIntIdEstado() == -1){
				accesoEspecialFiltro.setIntIdEstado(null);
			}
			//Para la seleccion de fecha de inicio
			if(fechaInicioFiltro!=null){
				accesoEspecialFiltro.setTsFechaInicio(new Timestamp(fechaInicioFiltro.getTime()));
			}else{
				accesoEspecialFiltro.setTsFechaInicio(null);
			}
			//Para la seleccion de fecha de fin
			if(fechaFinFiltro!=null){
				//Se añade un dia extra en fecha fin porque orginalmente el calendar da la fecha con 00h 00m 00s y ocasiona
				//seleccion de rangos erroneos con fechas registradas en la base de datos que si poseen horas.
				accesoEspecialFiltro.setTsFechaFin(new Timestamp(fechaFinFiltro.getTime()+1 * 24 * 60 * 60 * 1000));
			}else{
				accesoEspecialFiltro.setTsFechaFin(null);
			}
			//Para la seleccion de motivo
			if(accesoEspecialFiltro.getIntParaTipoMotivo().intValue()==Constante.PARAM_T_TIPOSMOTIVOACCESO_TODOS.intValue()){
				accesoEspecialFiltro.setIntParaTipoMotivo(null);
			}
			//Tipo de usuario que se ha elegido
			if(intTipoUsuario==Constante.PARAM_T_TIPOUSUARIOACCESOESPECIAL_TODOS.intValue()){
				//Tipo Usuario : Todos
				accesoEspecialFiltro.setIntPersPersonaOpera(intIdNatural);
				accesoEspecialFiltro.setIntPersPersonaAutoriza(intIdNatural);
			}else if(intTipoUsuario==Constante.PARAM_T_TIPOUSUARIOACCESOESPECIAL_NINGUNO.intValue()){
				//Tipo Usuario : Ninguno
				accesoEspecialFiltro.setIntPersPersonaOpera(null);
				accesoEspecialFiltro.setIntPersPersonaAutoriza(null);				
			}else if(intTipoUsuario==Constante.PARAM_T_TIPOUSUARIOACCESOESPECIAL_USUARIO.intValue()){
				//Tipo Usuario : usuario
				accesoEspecialFiltro.setIntPersPersonaOpera(intIdNatural);
				accesoEspecialFiltro.setIntPersPersonaAutoriza(null);				
			}else if(intTipoUsuario==Constante.PARAM_T_TIPOUSUARIOACCESOESPECIAL_AUTORIZACION.intValue()){
				//Tipo Usuario : autoriza
				accesoEspecialFiltro.setIntPersPersonaOpera(null);
				accesoEspecialFiltro.setIntPersPersonaAutoriza(intIdNatural);				
			} 
			//Tipo de acceso
			accesoEspecialFiltro.setIntParaTipoAcceso(intTipoAcceso);
			log.info(accesoEspecialFiltro);
			listaAccesosEspeciales = permisoFacade.buscarAccesosEspeciales(accesoEspecialFiltro);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void grabar(){
		String mensaje = null;
		boolean exito = Boolean.FALSE;
		try {
			//Validaciones
			if(accesoEspecialNuevo.getIntPersPersonaOpera() == null){
				mensaje = "Ocurrió un error durante el proceso. Debe ingresar un usuario operario.";
				return;
			}
			if(fechaInicioNuevo == null){
				mensaje = "Ocurrió un error durante el proceso. Debe ingresar una fecha de inicio.";
				return;
			}
			if(fechaFinNuevo == null){
				mensaje = "Ocurrió un error durante el proceso. Debe ingresar una fecha de fin.";
				return;
			}
			if(fechaInicioNuevo.compareTo(fechaFinNuevo)>0){
				mensaje = "Ocurrió un error durante el proceso. La fecha de inicio es mayor a la fecha de fin.";
				return;
			}
			if(accesoEspecialNuevo.getIntPersPersonaAutoriza() == null){
				mensaje = "Ocurrió un error durante el proceso. Debe ingresar un usuario que autorice la operación.";
				return;
			}			
			if(accesoEspecialNuevo.getStrObservacion() == null || accesoEspecialNuevo.getStrObservacion().isEmpty()){
				mensaje = "Ocurrió un error durante el proceso. Debe ingresar una observación.";
				return;
			}
			
			accesoEspecialNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			accesoEspecialNuevo.setTsFechaInicio(new Timestamp(fechaInicioNuevo.getTime()));
			accesoEspecialNuevo.setTsFechaFin(new Timestamp(fechaFinNuevo.getTime()));			
			accesoEspecialNuevo.setIntAccesoRemoto(0);
			//CAMBIAR !
			//usuario 93 en duro por problemas con QC
			//en DEV 50
			accesoEspecialNuevo.setIntPersPersonaRegistra(93);
			
			if(seleccionaFeriados){
				accesoEspecialNuevo.setIntFeriados(1);
			}else{
				accesoEspecialNuevo.setIntFeriados(0);
			}
			//Se procesa los dias seleccionados
			log.info("habilitarDias:"+habilitarDias);
			ArrayList<AccesoEspecialDetalle> listaAccesosEspecialesDetalleSeleccionados = new ArrayList<AccesoEspecialDetalle>();
			if(habilitarDias){
				for(AccesoEspecialDetalle aed : listaAccesosEspecialesDetalle){					
					if(aed.getChecked()){
						aed.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						listaAccesosEspecialesDetalleSeleccionados.add(aed);
					}
				}
			}
			log.info(registrarNuevo);
			//Se graba
			if(registrarNuevo){
				//Si se trata de una nueva insercion
				accesoEspecialNuevo.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				accesoEspecialNuevo.setIntParaTipoAcceso(intTipoAcceso);
				log.info("registrarNuevo:"+registrarNuevo);
				//Procede a grabar
				permisoFacade.grabarAccesosEspeciales(accesoEspecialNuevo, listaAccesosEspecialesDetalleSeleccionados);
				mensaje = "Se registró correctamente el acceso especial.";
				exito = Boolean.TRUE;
			}else{
				//Si se trata de una modificación
				permisoFacade.modificarAccesoEspecialYDetalle(accesoEspecialNuevo, listaAccesosEspecialesDetalleSeleccionados);
				mensaje = "Se modificó correctamente el acceso especial.";
				exito = Boolean.TRUE;
			}
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			log.info(accesoEspecialNuevo);
		} catch (BusinessException e) {
			mensaje = "Ocurrio un error durante el proceso.";
			e.printStackTrace();
		} catch (Exception e){
			mensaje = "Ocurrio un error durante el proceso.";
			e.printStackTrace();
		} finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	public void eliminarRegistro(){
		boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {			
			listaAccesosEspeciales.remove(registroSeleccionado);
			registroSeleccionado.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
			registroSeleccionado = permisoFacade.eliminarAccesoEspecial(registroSeleccionado);
			if(registroSeleccionado.getIntIdEstado().intValue()==Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO){
				exito = Boolean.TRUE;
				mensaje = "El proceso de eliminación se realizo correctamente";			
			}else{
				mensaje = "Ocurrio un error durante el proceso de eliminación";			
			}
		} catch (BusinessException e) {
			mensaje = "Ocurrio un error durante el proceso de eliminación";
			e.printStackTrace();
		} catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de eliminación";
			e.printStackTrace();
		} finally{
			mostrarMensaje(exito, mensaje);
			deshabilitarPanelInferior(null);	
		}
	}
	
	public void seleccionarUsuario(ActionEvent event){
		UsuarioComp usuarioComp = null;
		if(cajaUsuario.equalsIgnoreCase("cajaUsuarioFiltroFH")  || 
			cajaUsuario.equalsIgnoreCase("cajaUsuarioFiltroSucursal") ||
			cajaUsuario.equalsIgnoreCase("cajaUsuarioFiltroCabina") ){			
			usuarioComp = (UsuarioComp)event.getComponent().getAttributes().get("item");
			accesoEspecialFiltro.setNaturalOpera(usuarioComp.getUsuario().getPersona().getNatural());
			intIdNatural = usuarioComp.getUsuario().getIntPersPersonaPk();
			
		}else if(cajaUsuario.equalsIgnoreCase("cajaUsuarioOperaNuevoFH") ||
				cajaUsuario.equalsIgnoreCase("cajaUsuarioOperaNuevoSucursal") ||
				cajaUsuario.equalsIgnoreCase("cajaUsuarioOperaNuevoCabina")){
			usuarioComp = (UsuarioComp)event.getComponent().getAttributes().get("item");
			accesoEspecialNuevo.setNaturalOpera(usuarioComp.getUsuario().getPersona().getNatural());
			accesoEspecialNuevo.setIntPersPersonaOpera(usuarioComp.getUsuario().getIntPersPersonaPk());
			
		}else if(cajaUsuario.equalsIgnoreCase("cajaUsuarioAutorizaNuevoFH") ||
				cajaUsuario.equalsIgnoreCase("cajaUsuarioAutorizaNuevoSucursal") ||
				cajaUsuario.equalsIgnoreCase("cajaUsuarioAutorizaNuevoCabina")){
			usuarioComp = (UsuarioComp)event.getComponent().getAttributes().get("item");
			accesoEspecialNuevo.setNaturalAutoriza(usuarioComp.getUsuario().getPersona().getNatural());
			accesoEspecialNuevo.setIntPersPersonaAutoriza(usuarioComp.getUsuario().getIntPersPersonaPk());
		}
	}
	
	public void buscarUsuarios(ActionEvent event){
		try {
			cajaUsuario = (String)event.getComponent().getAttributes().get("caja");
			log.info("cajaUsuario:"+cajaUsuario);
			if(cajaUsuario.equalsIgnoreCase("cajaUsuarioFiltroFH") ||
				cajaUsuario.equalsIgnoreCase("cajaUsuarioFiltroSucursal") ||
				cajaUsuario.equalsIgnoreCase("cajaUsuarioFiltroCabina") ){
				//listaUsuarios = empresaFacade.getListaNaturalDeUsuarioPorIdEmpresa(accesoEspecialFiltro.getIntPersEmpresa());
				cargarUsuarios(accesoEspecialFiltro.getIntPersEmpresa());
				
			}else if(cajaUsuario.equalsIgnoreCase("cajaUsuarioOperaNuevoFH")||
				cajaUsuario.equalsIgnoreCase("cajaUsuarioOperaNuevoSucursal") ||
				cajaUsuario.equalsIgnoreCase("cajaUsuarioOperaNuevoCabina")){				
				//listaUsuarios = empresaFacade.getListaNaturalDeUsuarioPorIdEmpresa(accesoEspecialNuevo.getIntPersEmpresa());
				cargarUsuarios(accesoEspecialNuevo.getIntPersEmpresa());
			
			}else if(cajaUsuario.equalsIgnoreCase("cajaUsuarioAutorizaNuevoFH") ||
				cajaUsuario.equalsIgnoreCase("cajaUsuarioAutorizaNuevoSucursal") ||
				cajaUsuario.equalsIgnoreCase("cajaUsuarioAutorizaNuevoCabina")){
				//listaUsuarios = empresaFacade.getListaNaturalDeUsuarioPorIdEmpresa(accesoEspecialNuevo.getIntPersEmpresa());
				cargarUsuarios(accesoEspecialNuevo.getIntPersEmpresa());
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void mostrarMensaje(boolean exito, String mensaje){
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
	
	public void seleccionarRegistro(ActionEvent event){
		try {
			registroSeleccionado = (AccesoEspecial)event.getComponent().getAttributes().get("item");
			if(registroSeleccionado.getIntIdEstado().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO){
				mostrarBtnEliminar= Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
				deshabilitarNuevo = Boolean.TRUE;
				registrarNuevo = Boolean.FALSE;
				mostrarPanelInferior = Boolean.TRUE;
				cargarRegistro();
			}else{
				mostrarBtnEliminar = Boolean.TRUE;
				habilitarGrabar = Boolean.TRUE;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}	

	public void modificarRegistro(){
		try {
			cargarRegistro();
						
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cargarRegistro() throws BusinessException{
		cargarListaAccesosEspecialesDetalle();
		List<AccesoEspecialDetalle> listaBD = permisoFacade.getListaAccesoEspecialDetallePorCabecera(registroSeleccionado);
		List<AccesoEspecialDetalle> listaAux = new ArrayList<AccesoEspecialDetalle>();
		//Solo tomamos en cuenta los detalles no anulados
		for(AccesoEspecialDetalle aed : listaBD){
			if(aed.getIntIdEstado().intValue()!=Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO){
				listaAux.add(aed);
				log.info("dad:"+aed);
			}
		}
		listaBD = listaAux;
		int i=0;
		while(i<listaAccesosEspecialesDetalle.size()){
			AccesoEspecialDetalle aed = listaAccesosEspecialesDetalle.get(i);
			for(AccesoEspecialDetalle aedBD : listaBD){
				if(aed.getId().getIntIdDiaSemana().intValue()==aedBD.getId().getIntIdDiaSemana().intValue()){
					aedBD.setChecked(Boolean.TRUE);	
					aedBD.setDia(listaAccesosEspecialesDetalle.get(i).getDia());
					listaAccesosEspecialesDetalle.set(i, aedBD);						
				}
			}
			i++;
		}
		accesoEspecialNuevo = registroSeleccionado;
		if(accesoEspecialNuevo.getIntFeriados().intValue()==1){
			seleccionaFeriados = Boolean.TRUE;
		}else{
			seleccionaFeriados = Boolean.FALSE;
		}
		if(!listaBD.isEmpty()){
			habilitarDias = Boolean.TRUE;
		}else{
			habilitarDias = Boolean.FALSE;
		}
		if(intTipoAcceso.intValue() == Constante.PARAM_T_ACCESOESPECIAL_SUCURSAL){
			listaSucursales = empresaFacade.getListaSucursalPorPkEmpresa(accesoEspecialNuevo.getIntPersEmpresa());
		}
		fechaInicioNuevo = accesoEspecialNuevo.getTsFechaInicio();
		fechaFinNuevo = accesoEspecialNuevo.getTsFechaFin();
	}

	private void cargarListaAccesosEspecialesDetalle(){
		listaAccesosEspecialesDetalle.clear();
		AccesoEspecialDetalle accesosEspecialDetalle = null;
		for(Object o : listaDias){
			accesosEspecialDetalle = new AccesoEspecialDetalle();
			accesosEspecialDetalle.setDia(((Tabla)o));
			accesosEspecialDetalle.getId().setIntIdDiaSemana(((Tabla)o).getIntIdDetalle());
			listaAccesosEspecialesDetalle.add(accesosEspecialDetalle);
		}
	}
	
	public void seleccionarEmpresa(){		
		try {
			//Carga la lista de Sucursales dependiendo de la empresa seleccionada
			listaSucursales = empresaFacade.getListaSucursalPorPkEmpresa(accesoEspecialNuevo.getIntPersEmpresa());
		} catch (BusinessException e) {			
			e.printStackTrace();
		}
	}
	
	public void deshabilitarPanelInferior(ActionEvent event ){
		log.info("deshabilitarPanelInferior");
		mostrarPanelInferior = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
	}
	
	
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
	public AccesoEspecial getAccesoEspecialNuevo() {
		return accesoEspecialNuevo;
	}
	public void setAccesoEspecialNuevo(AccesoEspecial accesoEspecialNuevo) {
		this.accesoEspecialNuevo = accesoEspecialNuevo;
	}
	public AccesoEspecial getAccesoEspecialFiltro() {
		return accesoEspecialFiltro;
	}
	public void setAccesoEspecialFiltro(AccesoEspecial accesoEspecialFiltro) {
		this.accesoEspecialFiltro = accesoEspecialFiltro;
	}
	public List getListaEmpresas() {
		return listaEmpresas;
	}
	public void setListaEmpresas(List listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}
	public int getIntTipoUsuario() {
		return intTipoUsuario;
	}
	public void setIntTipoUsuario(int intTipoUsuario) {
		this.intTipoUsuario = intTipoUsuario;
	}
	public List getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	public String getCajaUsuario() {
		return cajaUsuario;
	}
	public void setCajaUsuario(String cajaUsuario) {
		this.cajaUsuario = cajaUsuario;
	}
	public Date getFechaInicioFiltro() {
		return fechaInicioFiltro;
	}
	public void setFechaInicioFiltro(Date fechaInicioFiltro) {
		this.fechaInicioFiltro = fechaInicioFiltro;
	}
	public Date getFechaFinFiltro() {
		return fechaFinFiltro;
	}
	public void setFechaFinFiltro(Date fechaFinFiltro) {
		this.fechaFinFiltro = fechaFinFiltro;
	}
	public Date getFechaInicioNuevo() {
		return fechaInicioNuevo;
	}
	public void setFechaInicioNuevo(Date fechaInicioNuevo) {
		this.fechaInicioNuevo = fechaInicioNuevo;
	}
	public Date getFechaFinNuevo() {
		return fechaFinNuevo;
	}
	public void setFechaFinNuevo(Date fechaFinNuevo) {
		this.fechaFinNuevo = fechaFinNuevo;
	}
	public boolean isSeleccionaFeriados() {
		return seleccionaFeriados;
	}
	public void setSeleccionaFeriados(boolean seleccionaFeriados) {
		this.seleccionaFeriados = seleccionaFeriados;
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public boolean isHabilitarDias() {
		return habilitarDias;
	}
	public void setHabilitarDias(boolean habilitarDias) {
		this.habilitarDias = habilitarDias;
	}
	public List getListaAccesosEspeciales() {
		return listaAccesosEspeciales;
	}
	public void setListaAccesosEspeciales(List listaAccesosEspeciales) {
		this.listaAccesosEspeciales = listaAccesosEspeciales;
	}
	public List getListaDias() {
		return listaDias;
	}
	public void setListaDias(List listaDias) {
		this.listaDias = listaDias;
	}
	public List<AccesoEspecialDetalle> getListaAccesosEspecialesDetalle() {
		return listaAccesosEspecialesDetalle;
	}
	public void setListaAccesosEspecialesDetalle(
			List<AccesoEspecialDetalle> listaAccesosEspecialesDetalle) {
		this.listaAccesosEspecialesDetalle = listaAccesosEspecialesDetalle;
	}
	public Integer getIntTipoAcceso() {
		return intTipoAcceso;
	}
	public void setIntTipoAcceso(Integer intTipoAcceso) {
		this.intTipoAcceso = intTipoAcceso;
	}
	public List getListaSucursales() {
		return listaSucursales;
	}
	public void setListaSucursales(List listaSucursales) {
		this.listaSucursales = listaSucursales;
	}
	public List getListaMotivos() {
		return listaMotivos;
	}
	public void setListaMotivos(List listaMotivos) {
		this.listaMotivos = listaMotivos;
	}
	public boolean isHabilitarCajaUsuario() {
		return habilitarCajaUsuario;
	}
	public void setHabilitarCajaUsuario(boolean habilitarCajaUsuario) {
		this.habilitarCajaUsuario = habilitarCajaUsuario;
	}
	public List getListaMotivosNuevo() {
		return listaMotivosNuevo;
	}
	public void setListaMotivosNuevo(List listaMotivosNuevo) {
		this.listaMotivosNuevo = listaMotivosNuevo;
	}	
}