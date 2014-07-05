package pe.com.tumi.riesgo.configuracion.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.riesgo.archivo.domain.ConfDetalle;
import pe.com.tumi.riesgo.archivo.domain.ConfDetalleId;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;
import pe.com.tumi.riesgo.archivo.domain.ConfiguracionId;
import pe.com.tumi.riesgo.archivo.domain.Nombre;
import pe.com.tumi.riesgo.archivo.domain.NombreId;
import pe.com.tumi.riesgo.archivo.facade.ArchivoRiesgoFacadeLocal;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class ConfiguracionArchivoController {
	protected static Logger log = Logger.getLogger(ConfiguracionArchivoController.class);	
	
	private TablaFacadeRemote tablaFacade;
	private ArchivoRiesgoFacadeLocal archivoRiesgoFacade;
	
	private List listaConfiguracion;
	private List listaNombre;
	private List listaNombreValor;
	private List listaConfDetalle;
	private List listaConfDetalleValor;
	private List listaMuestraPrevia;
	private List listaEjemplo;
	private List listaEjemploAux;
	private List listaEstructuras;
	
	private Configuracion configuracionFiltro;
	private Configuracion configuracionNuevo;
	private Configuracion registroSeleccionado;
	private ConfiguracionId configuracionId;
	private Nombre nombre;
	private NombreId nombreId;
	private ConfDetalle confDetalle;
	private ConfDetalle confDetalleAux;
	private ConfDetalleId confDetalleId;
	
	private Date fechaFiltroInicio;
	private Date fechaFiltroFin;
	private Date fechaActual;
	private String mensajeOperacion;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean habilitarCampo;
	private boolean habilitarTipoSocio;
	private boolean habilitarNombreFijo;
	private boolean habilitarNombreVariable;
	private boolean habilitarConfDetalleFijo;
	private boolean habilitarConfDetalleVariable;
	Usuario usuario;
	
	
	public ConfiguracionArchivoController(){
		cargarValoresIniciales();
	}
	
	private void cargarValoresIniciales(){
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				listaEjemplo = new ArrayList<String>();
				listaEjemploAux = new ArrayList();
				configuracionId = new ConfiguracionId();
				configuracionId.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				mensajeOperacion = null;
				listaMuestraPrevia = new ArrayList<String>();
				fechaActual = new Date();
				confDetalle = new ConfDetalle();
				confDetalleAux = new ConfDetalle();
				nombre = new Nombre();
				nombreId = new NombreId();
				nombreId.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				listaConfiguracion = new ArrayList<Configuracion>();
				listaNombre = new ArrayList<Nombre>();
				listaConfDetalle = new ArrayList<ConfDetalle>();
				configuracionFiltro = new Configuracion();
				configuracionNuevo = new Configuracion();
				fechaFiltroInicio = null;
				fechaFiltroFin = null;
				archivoRiesgoFacade = (ArchivoRiesgoFacadeLocal) EJBFactory.getLocal(ArchivoRiesgoFacadeLocal.class);
				tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				listaNombreValor = tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_TIPOVALORARCHIVO_INT);
				listaConfDetalleValor = tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_TIPOVALORARCHIVO_INT);
			}else{
				log.error("--Usuario obtenido es NULL.");
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			habilitarConfDetalleFijo = Boolean.FALSE;
			habilitarConfDetalleVariable = Boolean.FALSE;
			habilitarNombreFijo = Boolean.FALSE;
			habilitarNombreVariable = Boolean.FALSE;
			habilitarTipoSocio = Boolean.TRUE;
			configuracionNuevo = new Configuracion();
			configuracionNuevo.setIntParaFormatoArchivoCod(Constante.PARAM_T_FORMATOARCHIVOS_DBF);
			habilitarCampo = Boolean.TRUE;
			nombre = new Nombre();
			confDetalle = new ConfDetalle();
			listaNombre = new ArrayList<Nombre>();
			listaConfDetalle = new ArrayList<ConfDetalle>();
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;			
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			listaMuestraPrevia.clear();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void modificarRegistro(){
		try {
			cargarRegistro();	
			habilitarGrabar = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			if(configuracionNuevo.getIntParaFormatoArchivoCod().equals(Constante.PARAM_T_FORMATOARCHIVOS_DBF)){
				habilitarCampo = Boolean.TRUE;
			}else{
				habilitarCampo = Boolean.FALSE;
			}
		} catch (BusinessException e) {			
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarRegistro()throws BusinessException{
		try{
			configuracionNuevo = registroSeleccionado;
			listaNombre = archivoRiesgoFacade.getNombrePorIntItemConfiguracion(registroSeleccionado.getId().getIntItemConfiguracion());
			listaConfDetalle = archivoRiesgoFacade.getConfDetallePorIntItemConfiguracion(registroSeleccionado.getId().getIntItemConfiguracion());			
			cargarMuestraPrevia();
			listaTablaLimpiarChecked(listaNombreValor);
			listaTablaLimpiarChecked(listaConfDetalleValor);
			confDetalle = new ConfDetalle();
			nombre = new Nombre();
			registrarNuevo = Boolean.FALSE;
			habilitarConfDetalleFijo = Boolean.FALSE;
			habilitarConfDetalleVariable = Boolean.FALSE;
			habilitarNombreFijo = Boolean.FALSE;
			habilitarNombreVariable = Boolean.FALSE;			
			cargarListaEjemplo();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
	}
	
	private void listaTablaLimpiarChecked(List lista){
		List listaAux = new ArrayList<Tabla>();
		for(int i=0;i<lista.size();i++){
			Tabla tabla = (Tabla)lista.get(i);
			tabla.setChecked(Boolean.FALSE);
			listaAux.add(tabla);
		}
		lista = listaAux;
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
	}
	
	public void eliminarRegistro(){
		log.info("eliminar:"+registroSeleccionado);
		boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			listaConfiguracion.remove(registroSeleccionado);
			//Para pruebas en QC ser usa el usuario 93
			//registroSeleccionado.setIntPersPersonaEliminaPk(Constante.PARAM_USUARIOSESION);
			registroSeleccionado.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
			registroSeleccionado.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
			registroSeleccionado = archivoRiesgoFacade.eliminarConfiguracion(registroSeleccionado);
		} catch (BusinessException e) {
			log.error(e.getMessage(),e);
		} catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(registroSeleccionado.getIntParaEstadoCod().intValue()==Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO.intValue()){
			exito = Boolean.TRUE;
			mensaje = "El proceso de eliminación se realizo correctamente";			
		}else{
			mensaje = "Ocurrio un error durante el proceso de eliminación";			
		}
		mostrarMensaje(exito, mensaje);
		mostrarPanelInferior = Boolean.FALSE;
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (Configuracion)event.getComponent().getAttributes().get("item");
			log.info("reg selec:"+registroSeleccionado);
			if(registroSeleccionado.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				cargarRegistro();
				mostrarBtnEliminar= Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
				registrarNuevo = Boolean.FALSE;
				mostrarPanelInferior = Boolean.TRUE;
				mostrarMensajeExito = Boolean.FALSE;
				mostrarMensajeError = Boolean.FALSE;
				deshabilitarNuevo = Boolean.TRUE;
			}else{
				mostrarBtnEliminar = Boolean.TRUE;
				habilitarGrabar = Boolean.TRUE;
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void agregarConfDetalle(){
		ConfDetalle confDetalleAgregar = new ConfDetalle();
		for(Object o : listaConfDetalleValor){
			Tabla tabla = ((Tabla)o);
			if(tabla.getChecked()==true){
				//Valida que se haya ingresado Tamaño
				if(confDetalle.getIntTamano()==null){
					mostrarMensaje(Boolean.FALSE, "Debe ingresar el Tamaño");
					return;
				}				
				//Si se selecciono un valor fijo para el nombre de archivo
				if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO)){
					//Valida que Campo este lleno en caso que el formato sea DBF
					if(configuracionNuevo.getIntParaFormatoArchivoCod().equals(Constante.PARAM_T_FORMATOARCHIVOS_DBF)){
						if(confDetalle.getStrValorCampo()==null  || confDetalle.getStrValorCampo().trim().isEmpty()){
							mostrarMensaje(Boolean.FALSE, "Debe ingresar el nombre de Campo fijo.");
							return;
						}
					}
					//Valida que Dato este lleno
					if(confDetalle.getStrDatoFijo()==null  || confDetalle.getStrDatoFijo().trim().isEmpty()){
						mostrarMensaje(Boolean.FALSE, "Debe ingresar el Dato.");
						return;
					}					
					confDetalleAgregar.setIntParaTipoValorCod(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO);
					confDetalleAgregar.setStrDatoFijo(confDetalle.getStrDatoFijo());
					confDetalleAgregar.setStrValorCampo(confDetalle.getStrValorCampo());
				}else{
					//Si se selecciono un valor variable para el nombre de archivo
					//Valida que Campo este lleno en caso que el formato sea DBF
					if(configuracionNuevo.getIntParaFormatoArchivoCod().equals(Constante.PARAM_T_FORMATOARCHIVOS_DBF)){
						if(confDetalleAux.getStrValorCampo()==null  || confDetalleAux.getStrValorCampo().trim().isEmpty()){
							mostrarMensaje(Boolean.FALSE, "Debe ingresar el nombre de Campo variable.");
							return;
						}
					}
					confDetalleAgregar.setIntParaTipoValorCod(Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE);
					confDetalleAgregar.setIntParaTipoDatoVariableCod(confDetalle.getIntParaTipoDatoVariableCod());
					confDetalleAgregar.setStrValorCampo(confDetalleAux.getStrValorCampo());
				}
				confDetalleAgregar.setIntParaTipoDatoCod(confDetalle.getIntParaTipoDatoCod());
				confDetalleAgregar.setIntParaTipoCompletaCod(confDetalle.getIntParaTipoCompletaCod());
				confDetalleAgregar.setIntTamano(confDetalle.getIntTamano());
				confDetalleAgregar.setIntParaTipoAlineacionCod(confDetalle.getIntParaTipoAlineacionCod());
				confDetalleAgregar.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				confDetalleId = new ConfDetalleId();
				confDetalleId.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				confDetalleAgregar.setId(confDetalleId);				
			}
		}
		//quita el mensaje de error en caso lo haya
		mostrarMensaje(Boolean.TRUE, null);
		log.info("agregar:"+confDetalleAgregar);
		listaConfDetalle.add(confDetalleAgregar);
		cargarListaEjemplo();
	}
	
	private void cargarListaEjemplo(){
		ConfDetalle confDet = null;
		listaEjemplo.clear();
		for(Object ob : listaConfDetalle){
			confDet = (ConfDetalle)ob;
			if(confDet.getIntParaTipoValorCod().equals(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO)){
				listaEjemplo.add(confDet.getStrDatoFijo());
			}else{
				listaEjemplo.add(agregarListaEjemploVariable(confDet.getIntParaTipoDatoVariableCod()));
			}
			listaEjemploAux.clear();
			listaEjemploAux.add(listaEjemplo);
		}
	}
	
	private String agregarListaEjemploVariable(Integer tipo){
		String ejemplo = null;
		if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_FECHAREPORTE)){
			ejemplo = "31/01/12";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_CODIGOSOCIO)){
			ejemplo = "1";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_TIPODOCUMENTO)){
			ejemplo = "1";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_NRODOCUMENTO)){
			ejemplo = "44830812";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_TIPOPERSONA)){
			ejemplo = "1";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_TIPODEUDOR)){
			ejemplo = "1";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_APELLIDOSYNOMBRES)){
			ejemplo = "RUIZ SAAVEDRA EVA";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_DIRECCION)){
			ejemplo = "CALLE PAZ 140";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_NOMBREDISTRITO)){
			ejemplo = "LINCE";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_NOMBREDEPARTAMENTO)){
			ejemplo = "ICA";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_FECHAVENCIMIENTO)){
			ejemplo = "25/12/12";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_TIPOMONEDA)){
			ejemplo = "1";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_MONTOADEUDADO)){
			ejemplo = "2500";
		}else if(tipo.equals(Constante.PARAM_T_TIPODATOVARIABLE_CONDICIONMOROSO)){
			ejemplo = "1";
		}
		return ejemplo;
	}
	
	public void agregarNombre(){
		Nombre nombreAgregar = new Nombre();
		for(Object o : listaNombreValor){
			Tabla tabla = ((Tabla)o);
			if(tabla.getChecked()==true){
				//Si se selecciono un valor fijo para el nombre de archivo
				if(tabla.getIntIdDetalle().intValue()==Constante.PARAM_T_TIPOVALORARCHIVO_FIJO.intValue()){
					nombreAgregar.setIntParaTipoValorCod(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO);
					nombreAgregar.setStrValorFijo(nombre.getStrValorFijo());
					if(nombre.getStrValorFijo()==null  || nombre.getStrValorFijo().trim().isEmpty()){
						mostrarMensaje(Boolean.FALSE, "Debe escribir el nombre del valor fijo");
						return;
					}
				}else{
					//Si se selecciono un valor variable para el nombre de archivo
					if(tabla.getIntIdDetalle().intValue()==Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE.intValue()){
						nombreAgregar.setIntParaTipoValorCod(Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE);
						nombreAgregar.setIntParaTipoVariableCod(nombre.getIntParaTipoVariableCod());
						//Dependiendo el tipo de valor variable elegido se setea strValorVariable
						SimpleDateFormat simpleDateformat = null;
						if(nombre.getIntParaTipoVariableCod().intValue()==Constante.PARAM_T_TIPOVALORVARIABLEARCHIVO_MES2DIG){
							simpleDateformat = new SimpleDateFormat("MM");
						}else if(nombre.getIntParaTipoVariableCod().intValue()==Constante.PARAM_T_TIPOVALORVARIABLEARCHIVO_MESNOMBRE){
							simpleDateformat = new SimpleDateFormat("MMMM", new Locale("ES"));
						}else if(nombre.getIntParaTipoVariableCod().intValue()==Constante.PARAM_T_TIPOVALORVARIABLEARCHIVO_AÑO2DIG){
							simpleDateformat = new SimpleDateFormat("yy");
						}else if(nombre.getIntParaTipoVariableCod().intValue()==Constante.PARAM_T_TIPOVALORVARIABLEARCHIVO_AÑO4DIG){
							simpleDateformat = new SimpleDateFormat("yyyy");
						}
						nombreAgregar.setStrValorFijo(simpleDateformat.format(fechaActual));
					}
				}
				nombreAgregar.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				nombreId = new NombreId();
				nombreId.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				nombreAgregar.setId(nombreId);
			}
		}
		mostrarMensaje(Boolean.TRUE, null);
		log.info("agregar:"+nombreAgregar);
		listaNombre.add(nombreAgregar);
		cargarMuestraPrevia();
	}
	
	private void cargarMuestraPrevia(){
		String muestraPrevia = "";
		for(Object o : listaNombre){
			muestraPrevia = muestraPrevia + ((Nombre)o).getStrValorFijo();
		}
		//Dependiendo del tipo de archivo seleccionado
		if(configuracionNuevo.getIntParaFormatoArchivoCod().equals(Constante.PARAM_T_FORMATOARCHIVOS_EXCEL)){
			muestraPrevia = muestraPrevia + ".xls";
		}else if(configuracionNuevo.getIntParaFormatoArchivoCod().equals(Constante.PARAM_T_FORMATOARCHIVOS_WORD)){
			muestraPrevia = muestraPrevia + ".doc";
		}else if(configuracionNuevo.getIntParaFormatoArchivoCod().equals(Constante.PARAM_T_FORMATOARCHIVOS_DBF)){
			muestraPrevia = muestraPrevia + ".dbf";
		}else if(configuracionNuevo.getIntParaFormatoArchivoCod().equals(Constante.PARAM_T_FORMATOARCHIVOS_TEXTO)){
			muestraPrevia = muestraPrevia + ".txt";
		}
		listaMuestraPrevia.clear();
		listaMuestraPrevia.add(muestraPrevia);
	}
	
	public void eliminarNombre(ActionEvent event){
		try{
			Nombre nombre = (Nombre)event.getComponent().getAttributes().get("item");
			listaNombre.remove(nombre);
			cargarMuestraPrevia();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarConfDetalle(ActionEvent event){
		try{
			ConfDetalle confDetalleAux = (ConfDetalle)event.getComponent().getAttributes().get("item");
			listaConfDetalle.remove(confDetalleAux);
			cargarListaEjemplo();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscar(){
		try{
			Timestamp busquedaInicio = null;
			Timestamp busquedaFin = null;
			if(configuracionFiltro.getIntParaEstadoCod().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_TODOS.intValue()){
				configuracionFiltro.setIntParaEstadoCod(null);
			}
			if(fechaFiltroInicio != null){
				busquedaInicio =  new Timestamp(fechaFiltroInicio.getTime());
			}
			if(fechaFiltroFin !=null){
				//Se añade un dia extra en fecha fin porque orginalmente el calendar da la fecha con 00h 00m 00s y ocasiona
				//seleccion de rangos erroneos con fechas registradas en la base de datos que si poseen horas.
				busquedaFin = new Timestamp(fechaFiltroFin.getTime()+1 * 24 * 60 * 60 * 1000);
			}
			listaConfiguracion = archivoRiesgoFacade.buscarConfiguracionSinEstructura(configuracionFiltro, busquedaInicio, busquedaFin);
		} catch (BusinessException e) {
			log.error(e.getMessage(),e);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void grabar(){
		boolean exito = Boolean.FALSE;
		String mensaje = "";
		try{
			if(listaConfDetalle.isEmpty()){
				mensaje="Debe realizar al menos un ingreso en Contenido de Archivo.";
				return;
			}
			if(listaNombre.isEmpty()){
				mensaje="Debe realizar al menos un ingreso en Nombre de Archivo.";
				return;
			}
			//Si es un nuevo registro a grabar
			if(registrarNuevo){
				configuracionNuevo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				//Para pruebas en QC usuario 93
				//configuracionNuevo.setIntPersPersonaUsuarioPk(Constante.PARAM_USUARIOSESION);
				configuracionNuevo.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
				configuracionNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
				configuracionNuevo.setId(configuracionId);
				archivoRiesgoFacade.grabarConfiguracion(configuracionNuevo, listaConfDetalle, listaNombre);
				mensaje = "Se registró correctamente la configuración de archivo";
			}else{
				//Si se esta modificando un registro
				archivoRiesgoFacade.modificarConfiguracion(configuracionNuevo, listaConfDetalle, listaNombre);
				mensaje = "Se modificó correctamente la configuración de archivo";				
			}
			exito = Boolean.TRUE;
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
		} catch (BusinessException e) {
			mensaje = "Ocurrio un error durante el proceso de registro de configuración de archivo.";
			log.error(e.getMessage(),e);
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de registro de configuración de archivo.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
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
	
	public void seleccionarFormato(){
		if(configuracionNuevo.getIntParaFormatoArchivoCod().equals(Constante.PARAM_T_FORMATOARCHIVOS_DBF)){
			habilitarCampo = Boolean.TRUE;			
		}else{
			//Se deshabilita ambos "Campo"
			habilitarCampo = Boolean.FALSE;
			confDetalle.setStrValorCampo(null);
			confDetalleAux.setStrValorCampo(null);
			//Se quita de listaConfDetalle los detalles que sean de tipo archivo DBF
			List<ConfDetalle> listaAux = new ArrayList<ConfDetalle>();
			for(Object o : listaConfDetalle){
				ConfDetalle confDet= (ConfDetalle)o;
				if(confDet.getStrValorCampo()==null || confDet.getStrValorCampo().trim().isEmpty() ){
					listaAux.add(confDet);
				}
			}
			listaConfDetalle.clear();
			listaConfDetalle = listaAux;
			cargarListaEjemplo();
		}
		cargarMuestraPrevia();
	}
	
	//Dependiendo del tipo de Valor (Fijo o Variable) seleccionado en la interfaz, se habilitan ciertos controles de la interfaz
	public void seleccionarNombre(ActionEvent event){
		
		Tabla tablaSeleccionado = (Tabla)event.getComponent().getAttributes().get("item");
		for(int i=0;i<listaNombreValor.size();i++){
			if(((Tabla)listaNombreValor.get(i)).getIntIdDetalle().equals(tablaSeleccionado.getIntIdDetalle())){
				((Tabla)listaNombreValor.get(i)).setChecked(Boolean.TRUE);
			}else{
				((Tabla)listaNombreValor.get(i)).setChecked(Boolean.FALSE);
			}
		}
		if(tablaSeleccionado.getIntIdDetalle().equals(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO)){
			habilitarNombreFijo = Boolean.TRUE;
			habilitarNombreVariable = Boolean.FALSE;
			nombre.setIntParaTipoValorCod(null);
		}else{
			habilitarNombreFijo = Boolean.FALSE;
			habilitarNombreVariable = Boolean.TRUE;
			nombre.setStrValorFijo(null);
		}
	}
	
	//Dependiendo del tipo de Valor (Fijo o Variable) seleccionado en la interfaz, se habilitan ciertos controles de la interfaz
	public void seleccionarConfDetalle(ActionEvent event){
		Tabla tablaSeleccionado = (Tabla)event.getComponent().getAttributes().get("item");
		for(int i=0;i<listaConfDetalleValor.size();i++){
			if(((Tabla)listaConfDetalleValor.get(i)).getIntIdDetalle().equals(tablaSeleccionado.getIntIdDetalle())){
				((Tabla)listaConfDetalleValor.get(i)).setChecked(Boolean.TRUE);
			}else{
				((Tabla)listaConfDetalleValor.get(i)).setChecked(Boolean.FALSE);
			}
		}
		if(tablaSeleccionado.getIntIdDetalle().equals(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO)){
			habilitarConfDetalleFijo = Boolean.TRUE;
			habilitarConfDetalleVariable = Boolean.FALSE;
			confDetalleAux.setStrValorCampo(null);
			confDetalle.setIntParaTipoDatoCod(null);
		}else{
			habilitarConfDetalleFijo = Boolean.FALSE;
			habilitarConfDetalleVariable = Boolean.TRUE;
			confDetalle.setStrValorCampo(null);
			confDetalle.setStrDatoFijo(null);
		}
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
	public Configuracion getConfiguracionFiltro() {
		return configuracionFiltro;
	}
	public void setConfiguracionFiltro(Configuracion configuracionFiltro) {
		this.configuracionFiltro = configuracionFiltro;
	}
	public Configuracion getConfiguracionNuevo() {
		return configuracionNuevo;
	}
	public void setConfiguracionNuevo(Configuracion configuracionNuevo) {
		this.configuracionNuevo = configuracionNuevo;
	}
	public Date getFechaFiltroInicio() {
		return fechaFiltroInicio;
	}
	public void setFechaFiltroInicio(Date fechaFiltroInicio) {
		this.fechaFiltroInicio = fechaFiltroInicio;
	}
	public Date getFechaFiltroFin() {
		return fechaFiltroFin;
	}
	public void setFechaFiltroFin(Date fechaFiltroFin) {
		this.fechaFiltroFin = fechaFiltroFin;
	}
	public List getListaConfiguracion() {
		return listaConfiguracion;
	}
	public void setListaConfiguracion(List listaConfiguracion) {
		this.listaConfiguracion = listaConfiguracion;
	}
	public List getListaNombre() {
		return listaNombre;
	}
	public void setListaNombre(List listaNombre) {
		this.listaNombre = listaNombre;
	}
	public List getListaNombreValor() {
		return listaNombreValor;
	}
	public void setListaNombreValor(List listaNombreValor) {
		this.listaNombreValor = listaNombreValor;
	}
	public List getListaConfDetalle() {
		return listaConfDetalle;
	}
	public void setListaConfDetalle(List listaConfDetalle) {
		this.listaConfDetalle = listaConfDetalle;
	}
	public List getListaConfDetalleValor() {
		return listaConfDetalleValor;
	}
	public void setListaConfDetalleValor(List listaConfDetalleValor) {
		this.listaConfDetalleValor = listaConfDetalleValor;
	}
	public Nombre getNombre() {
		return nombre;
	}
	public void setNombre(Nombre nombre) {
		this.nombre = nombre;
	}
	public List getListaMuestraPrevia() {
		return listaMuestraPrevia;
	}
	public void setListaMuestraPrevia(List listaMuestraPrevia) {
		this.listaMuestraPrevia = listaMuestraPrevia;
	}
	public Date getFechaActual() {
		return fechaActual;
	}
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}
	public ConfDetalle getConfDetalle() {
		return confDetalle;
	}
	public void setConfDetalle(ConfDetalle confDetalle) {
		this.confDetalle = confDetalle;
	}
	public ConfDetalle getConfDetalleAux() {
		return confDetalleAux;
	}
	public void setConfDetalleAux(ConfDetalle confDetalleAux) {
		this.confDetalleAux = confDetalleAux;
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public boolean isHabilitarCampo() {
		return habilitarCampo;
	}
	public void setHabilitarCampo(boolean habilitarCampo) {
		this.habilitarCampo = habilitarCampo;
	}
	public List getListaEjemplo() {
		return listaEjemplo;
	}
	public void setListaEjemplo(List listaEjemplo) {
		this.listaEjemplo = listaEjemplo;
	}
	public List getListaEjemploAux() {
		return listaEjemploAux;
	}
	public void setListaEjemploAux(List listaEjemploAux) {
		this.listaEjemploAux = listaEjemploAux;
	}
	public List getListaEstructuras() {
		return listaEstructuras;
	}
	public void setListaEstructuras(List listaEstructuras) {
		this.listaEstructuras = listaEstructuras;
	}
	public boolean isHabilitarTipoSocio() {
		return habilitarTipoSocio;
	}
	public void setHabilitarTipoSocio(boolean habilitarTipoSocio) {
		this.habilitarTipoSocio = habilitarTipoSocio;
	}
	public boolean isHabilitarNombreFijo() {
		return habilitarNombreFijo;
	}
	public void setHabilitarNombreFijo(boolean habilitarNombreFijo) {
		this.habilitarNombreFijo = habilitarNombreFijo;
	}
	public boolean isHabilitarNombreVariable() {
		return habilitarNombreVariable;
	}
	public void setHabilitarNombreVariable(boolean habilitarNombreVariable) {
		this.habilitarNombreVariable = habilitarNombreVariable;
	}
	public boolean isHabilitarConfDetalleFijo() {
		return habilitarConfDetalleFijo;
	}
	public void setHabilitarConfDetalleFijo(boolean habilitarConfDetalleFijo) {
		this.habilitarConfDetalleFijo = habilitarConfDetalleFijo;
	}
	public boolean isHabilitarConfDetalleVariable() {
		return habilitarConfDetalleVariable;
	}
	public void setHabilitarConfDetalleVariable(boolean habilitarConfDetalleVariable) {
		this.habilitarConfDetalleVariable = habilitarConfDetalleVariable;
	}
}