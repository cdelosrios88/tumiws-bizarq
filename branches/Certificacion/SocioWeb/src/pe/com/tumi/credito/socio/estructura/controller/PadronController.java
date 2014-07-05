package pe.com.tumi.credito.socio.estructura.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Table;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPago;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPagoDetalle;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.Password;
import pe.com.tumi.seguridad.permiso.domain.PasswordId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;


public class PadronController{

	protected static Logger log = Logger.getLogger(PadronController.class);
	private boolean ingresarPadronRendered;
	private boolean nuevaSolicitudPagoRendered;
	private boolean seleccionarArchivoRendered;
	private boolean ingresarContraseña;
	private boolean panelInferiorRendered;
	private boolean panelPadronesSinSolicitud;
	private boolean deshabilitarSolicitud;
	private boolean deshabilitarPadron;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarPadronSocio;
	private boolean mostrarComboDocumento;
	private boolean mostrarGrillaPadrones;
	private boolean permitirEliminarRegistro;
	private boolean subioArchivo;
	private boolean habilitarComboTipoArchivo;
	private boolean habilitarProcesar;
	
	private Integer intTipoBusqueda;
	private String target;
	private String mensajeOperacion;
	private List listaRegistros;
	private List listaEstados;
	private List listaEstructuras;
	private List<AdminPadron> listaAdminPadronesParaSolicitud;
	private List<AdminPadron> listaAdminPadronesParaSolicitudSelec;
	private AdminPadron adminPadronNuevo;
	private AdminPadron adminPadronFiltro;
	private AdminPadron adminPadronFiltroSolicitud;
	private AdminPadron registroEliminar;
	private SolicitudPago solicitudPagoNuevo;
	private Estructura estructuraFiltro;
	private Password passwordIngresado;
	private TablaFacadeRemote tablaFacade;
	private EstructuraFacadeLocal estructuraFacade;
	private PermisoFacadeRemote permisoFacade;
	private GeneralFacadeRemote generalFacade;
	private TipoArchivo tipoArchivo;
	private Archivo archivo;
	private List listaAnios;
	private final int cantidadAñosLista = 4;
	private Usuario usuario;
	
	public PadronController() {
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
		
	}
	
	private void cargarValoresIniciales(){
		habilitarProcesar = Boolean.FALSE;
		listaRegistros = new ArrayList<AdminPadron>();
		listaAdminPadronesParaSolicitud = new ArrayList<AdminPadron>();
		listaAdminPadronesParaSolicitudSelec = new ArrayList<AdminPadron>();
		adminPadronFiltro = new AdminPadron();
		adminPadronFiltro.getId().setIntParaModalidadCod(Constante.PARAM_T_NIVELENTIDAD_TODOS);
		adminPadronFiltro.setIntParaEstadoCod(Constante.PARAM_T_NIVELENTIDAD_TODOS);
		adminPadronFiltro.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_TODOS);
		adminPadronNuevo = new AdminPadron();
		target = new String();
		solicitudPagoNuevo = new SolicitudPago();
		adminPadronFiltroSolicitud = new AdminPadron();
		panelPadronesSinSolicitud = Boolean.TRUE;
		listaEstructuras = new ArrayList<Estructura>();
		estructuraFiltro = new Estructura();
		Juridica juridica = new Juridica();
		estructuraFiltro.setJuridica(juridica);
		passwordIngresado = new Password();
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		intTipoBusqueda = Constante.PARAM_T_TIPOREGISTROPADRON_PADRON;
		mostrarComboDocumento = Boolean.FALSE;
		mostrarGrillaPadrones = Boolean.TRUE;
		permitirEliminarRegistro = Boolean.FALSE;
		mensajeOperacion = "";
		archivo = new Archivo();
		archivo.setId(new ArchivoId());
		try {
			cargarListaAnios();
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeLocal) EJBFactory.getLocal(EstructuraFacadeLocal.class);
			permisoFacade = (PermisoFacadeRemote) EJBFactory.getRemote(PermisoFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		} catch (EJBFactoryException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaAnios(){
		listaAnios = new ArrayList<Tabla>();
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		for(int i=0;i<cantidadAñosLista;i++){
			Tabla tabla = new Tabla();
			int year = cal.get(Calendar.YEAR);
			cal.add(Calendar.YEAR, 1);
			tabla.setIntIdDetalle(year);
			tabla.setStrDescripcion(""+year);
			listaAnios.add(tabla);
		}
	}
	
	public void validarContraseña(ActionEvent event) {
		try {
			if (passwordIngresado.getStrContrasena()!= null && 
				passwordIngresado.getStrContrasena().length()>0) {
				log.info("ingreso: " + passwordIngresado.getStrContrasena());
				PasswordId passwordId = new PasswordId();
				passwordId.setIntEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				passwordId.setIntIdTransaccion(Constante.PARAM_TRANSACCION_CARGAPADRON);
				passwordId.setIntIdPassword(Constante.PARAM_TRANSACCION_IDPASSWORD);
				Password passwordObtenido =	permisoFacade.getPasswordPorPk(passwordId);
				log.info("passwordObtenido:"+passwordObtenido.getStrContrasena());
				if (passwordIngresado.getStrContrasena().equalsIgnoreCase(passwordObtenido.getStrContrasena())) {
					habilitarSeleccionarArchivo(event);
				}
			}
		} catch (BusinessException e) {
			//e.printStackTrace();
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		//habilitarSeleccionarArchivo(event);
	}

	public void buscarEntidades(ActionEvent event){
		try {
			log.info("nivel:"+adminPadronNuevo.getId().getIntNivel());
			listaEstructuras = estructuraFacade.getListaEstructuraPorNivel(adminPadronNuevo.getId().getIntNivel());
			if(listaEstructuras!=null){
				log.info("listaEstructuras:"+listaEstructuras.size());
			}else{
				log.info("listaEstructuras:null");
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarEntidad(ActionEvent event){
		for(Object o : listaEstructuras){
			Estructura estructura = (Estructura)o;
			if(estructura.getChecked()){
				//log.info("Estructura selec:"+estructura);
				adminPadronNuevo.setEstructura(estructura);
			}
		}
	}
	
	public void seleccionarEntidad2(ActionEvent event){
		Estructura estructura = (Estructura)event.getComponent().getAttributes().get("item");
		adminPadronNuevo.setEstructura(estructura);
	}
/*	
	public boolean procesarSolicitudPago(ActionEvent event) throws EJBFactoryException, BusinessException, Exception  {
		boolean exito = false;
		solicitudPagoNuevo.setIntPersEmpresaPK(1);
		solicitudPagoNuevo.setIntPersPersonsaPK(1);
		if(solicitudPagoNuevo.getBdMonto()==null){
			throw new Exception("no se especifico el monto de la solicitud de pago");
		}
		solicitudPagoNuevo.getBdMonto().setScale(12, BigDecimal.ROUND_HALF_UP);
		exito = estructuraFacade.grabarSolicitudPago(solicitudPagoNuevo, listaAdminPadronesParaSolicitudSelec);
		return exito;
	}
*/	
	private String validarTipoArchivoPadron(){
		Table table = null;
		Boolean exito = Boolean.TRUE;
		String mensaje = null;
		try{			
			table = new Table(new java.io.File(target));
			table.open(IfNonExistent.ERROR);
			List<Field> fields = table.getFields();
			HashSet<String> setColumnas = new HashSet<String>();
			for (final Field field : fields){
			    setColumnas.add(field.getName().toUpperCase());
			}
			if(!setColumnas.contains("CODNIV")){
				mensaje = "Error en el archivo subido no es de tipo Padron. No se encuentra columna CODNIV.";
				exito = Boolean.FALSE;
			}
			if(!setColumnas.contains("LIBELE")){
				mensaje = "Error en el archivo DBF. No existe la columna LIBELE";
				exito = Boolean.FALSE;
			}
			if(exito){
				mensaje = "correcto";
			}			
		}catch(Exception e){
			//e.printStackTrace();
			log.error(e.getMessage(),e);
			mensaje = "Error en el archivo DBF. No se ha podido abrir el archivo";
		}
		return mensaje;
	}
	
	private String validarTipoArchivoDescuento(){
		Table table = null;
		Boolean exito = Boolean.TRUE;
		String mensaje = null;
		try{			
			table = new Table(new java.io.File(target));
			table.open(IfNonExistent.ERROR);
			List<Field> fields = table.getFields();
			HashSet<String> setColumnas = new HashSet<String>();
			for (final Field field : fields){
			    setColumnas.add(field.getName().toUpperCase());
			}
			if(!setColumnas.contains("CPTO")){
				mensaje = "Error en el archivo subido no es de tipo Descuento de Terceros. No se encuentra columna CPTO";
				exito = Boolean.FALSE;
			}
			if(!setColumnas.contains("MMAA")){
				mensaje = "Error en el archivo subido no es de tipo Descuento de Terceros. No se encuentra columna MMAA";
				exito = Boolean.FALSE;
			}
			if(!setColumnas.contains("LIBELE")){
				mensaje = "Error en el archivo DBF. No existe la columna LIBELE";
				exito = Boolean.FALSE;
			}
			if(exito){
				mensaje = "correcto";
			}			
		}catch(Exception e){
			//e.printStackTrace();
			log.error(e.getMessage(),e);
			mensaje = "Error en el archivo DBF. No se ha podido abrir el archivo";
		}
		return mensaje;
	}
	
	public void procesar(ActionEvent event) {
		boolean exito = false;
		String mensaje = null;
		List<String> listaNoProcesados = new ArrayList<String>();
		try{
			//Procesar Padrones
			if(ingresarPadronRendered){
				
				if(!subioArchivo){
					mensaje = "Hubo un error durante el proceso. Debe de adjuntar un archivo y subirlo.";
					return;
				}
				
				//Terminamos de cargar el adminpadron
				adminPadronNuevo.getId().setIntNivel(adminPadronNuevo.getEstructura().getId().getIntNivel());
				adminPadronNuevo.getId().setIntCodigo(adminPadronNuevo.getEstructura().getId().getIntCodigo());
				adminPadronNuevo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				adminPadronNuevo.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				adminPadronNuevo.setAdPaFechaRegistro(new Date());
				adminPadronNuevo.setIntPersUsuarioRegistroPk(usuario.getIntPersPersonaPk());
				
				//Comprobamos que no haya otro adminpadron identico
				List<AdminPadron> lista = estructuraFacade.getAdminPadronBusqueda(adminPadronNuevo);
				if(!lista.isEmpty()){
					mensaje = "Hubo un error durante el proceso. Ya exite un registro padron con esos datos";
					return;
				}
				
				//Se procesa los padrones					
				log.info(adminPadronNuevo.toString());		
				if(adminPadronNuevo.getId().getIntParaTipoArchivoPadronCod().equals(Constante.PARAM_T_TIPOARCHIVOPADRON_PADRON)){
					mensaje = validarTipoArchivoPadron();
					if(mensaje.contains("correcto")){
						
						exito = estructuraFacade.grabarPadrones(adminPadronNuevo,target,listaNoProcesados);
					}else{
						habilitarComboTipoArchivo = Boolean.TRUE;
						return;
					}
					
				}else if(adminPadronNuevo.getId().getIntParaTipoArchivoPadronCod().equals(Constante.PARAM_T_TIPOARCHIVOPADRON_DESCUENTO)){
					mensaje = validarTipoArchivoDescuento();
					if(mensaje.contains("correcto")){
						exito = estructuraFacade.grabarDescuentos(adminPadronNuevo,target, listaNoProcesados);
					}else{
						habilitarComboTipoArchivo = Boolean.TRUE;
						return;
					}
					
				}
				
				//Manejo del archivo adjunto en caso de exito
				if(exito){
					archivo.setTipoarchivo(tipoArchivo);
					archivo.getId().setIntParaTipoCod(tipoArchivo.getIntParaTipoCod());
					archivo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					log.info(archivo);
					archivo = generalFacade.grabarArchivo(archivo);
					adminPadronNuevo.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
					adminPadronNuevo.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
					adminPadronNuevo.setIntParaTipoArchivo(archivo.getId().getIntParaTipoCod());
					estructuraFacade.modificarAdminPadron(adminPadronNuevo);
					FileUtil.renombrarArchivo(target, tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());					
				}
				//mensaje = "Se ingresó correctamente el registro de Padrón";
				
				if(exito){
					mensaje = "Se realizo correctamente el registro.";
					deshabilitarPadron = Boolean.TRUE;
					deshabilitarPadronSocio = Boolean.TRUE;
					habilitarProcesar = Boolean.FALSE;
					//Para la muestra de filas no procesadas
					if(!listaNoProcesados.isEmpty()){
						mensaje = mensaje + " No se pudieron procesar a las personas con las siguientes libretas electorales : ";						
						for(String noProcesado : listaNoProcesados){
							log.info(noProcesado);
							mensaje = mensaje + noProcesado + " ";
						}
					}
				}else{
					mensaje = "Hubo un error durante el proceso";
				}
			
			
			//Procesar Solicitud de pagos
			}else if(nuevaSolicitudPagoRendered){
				//Procesar Solicitud de Pago
				solicitudPagoNuevo.setIntPersEmpresaPK(usuario.getPerfil().getId().getIntPersEmpresaPk());
				solicitudPagoNuevo.setIntPersPersonsaPK(usuario.getIntPersPersonaPk());
				if(listaAdminPadronesParaSolicitudSelec.isEmpty()){
					mensaje = "Debe de seleccionar al menos 1 padrón.";
					deshabilitarSolicitud = Boolean.TRUE;
					return;
				}
				if(solicitudPagoNuevo.getBdMonto()==null){
					mensaje = "No ha ingresado un monto correcto.";
					deshabilitarSolicitud = Boolean.TRUE;
					return;
				}
				solicitudPagoNuevo.getBdMonto().setScale(12, BigDecimal.ROUND_HALF_UP);
				
				exito = estructuraFacade.grabarSolicitudPago(solicitudPagoNuevo, listaAdminPadronesParaSolicitudSelec);
				//mensaje = "Se ingresó correctamente el registro de Solicitud de Pago";
				manejarActualizarGrilla();
				
				if(exito){
					mensaje = "Se realizo correctamente el registro";
					deshabilitarSolicitud = Boolean.TRUE;
					habilitarProcesar = Boolean.FALSE;
				}else{
					mensaje = "Hubo un error durante el proceso";
				}
			
			}else{
				log.info("no se selecciono ninguna opcion para procesar");
			}			
			
		
		}catch(EJBFactoryException e){
			mensaje = "Hubo un error durante el proceso";
			//e.printStackTrace();
			log.error(e.getMessage(),e);
		}catch(BusinessException e){
			mensaje = "Hubo un error durante el proceso";
			//e.printStackTrace();
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			mensaje = "Hubo un error durante el proceso";
			//e.printStackTrace();
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}				
	}
	
	public void manejarActualizarGrilla() throws EJBFactoryException, BusinessException{
		if(mostrarGrillaPadrones){
			listaRegistros = estructuraFacade.actualizarListaAdminPadron(listaRegistros);
		}
	}

	public void manejarSubirArchivo(UploadEvent event){
		log.info("--manejarSubirArchivo");
		try {
			int idTipoArchivo = 0;
			if(adminPadronNuevo.getId().getIntParaTipoArchivoPadronCod().intValue()==Constante.PARAM_T_TIPOARCHIVOPADRON_PADRON){
				idTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_PADRON;
			}else{
				if(adminPadronNuevo.getId().getIntParaTipoArchivoPadronCod().intValue()==Constante.PARAM_T_TIPOARCHIVOPADRON_DESCUENTO){
					idTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_DESCUENTO;
				}
			}
			tipoArchivo = generalFacade.getTipoArchivoPorPk(idTipoArchivo);
			log.info(tipoArchivo);
			target = tipoArchivo.getStrRuta() + "\\" + FileUtil.subirArchivo(event, tipoArchivo.getIntParaTipoCod());
			archivo.setStrNombrearchivo(target);
			habilitarComboTipoArchivo = Boolean.FALSE;
			subioArchivo = Boolean.TRUE;
		}catch(PatternSyntaxException e){
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	

	public void habilitarIngresarPadron(ActionEvent event) {
		setPanelInferiorRendered(true);
		setIngresarPadronRendered(true);
		setNuevaSolicitudPagoRendered(false);
		habilitarIngresarContraseña(event);
		deshabilitarPadron = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		limpiarPanelInferior();
		adminPadronNuevo.getId().setIntParaModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
		deshabilitarPadronSocio = Boolean.FALSE;
		habilitarComboTipoArchivo = Boolean.TRUE;
		subioArchivo = Boolean.FALSE;
	}

	public void habilitarNuevaSolicitudPago(ActionEvent event) {
		setPanelInferiorRendered(true);
		setIngresarPadronRendered(false);
		setNuevaSolicitudPagoRendered(true);
		setPanelPadronesSinSolicitud(Boolean.FALSE);
		deshabilitarSolicitud = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		habilitarProcesar = Boolean.TRUE;
		limpiarPanelInferior();
	}

	public void habilitarSeleccionarArchivo(ActionEvent event) {
		setSeleccionarArchivoRendered(true);
		setIngresarContraseña(false);
		habilitarProcesar = Boolean.TRUE;
	}

	public void habilitarIngresarContraseña(ActionEvent event) {
		setSeleccionarArchivoRendered(false);
		setIngresarContraseña(true);
	}

	public void cancelar(ActionEvent event) {
		setPanelInferiorRendered(false);
		setIngresarPadronRendered(false);
		setNuevaSolicitudPagoRendered(false);
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		limpiarPanelInferior();
		habilitarProcesar = Boolean.FALSE;
	}
	
	public void limpiarPanelInferior(){
		adminPadronFiltro = new AdminPadron();
		adminPadronFiltroSolicitud = new AdminPadron();
		listaAdminPadronesParaSolicitud = new ArrayList<AdminPadron>();
		listaAdminPadronesParaSolicitudSelec = new ArrayList<AdminPadron>();
		solicitudPagoNuevo = new SolicitudPago();
		adminPadronNuevo = new AdminPadron();
		//estructuraSelec = new Estructura();
	}

	public void buscarPadronesSinSolicitud(ActionEvent event){
		try {
			listaAdminPadronesParaSolicitud = new ArrayList<AdminPadron>();
			List<AdminPadron> listaAdminPadronesSinSolicitud = estructuraFacade.getAdminPadronSinSolicitud(adminPadronFiltroSolicitud);
			for(AdminPadron adminPadron : listaAdminPadronesSinSolicitud){
				if(adminPadron.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					adminPadron.setChecked(Boolean.FALSE);
					listaAdminPadronesParaSolicitud.add(adminPadron);
				}				
			}			
		} catch (BusinessException e) {
			//e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarPadrones(ActionEvent event){
		List<AdminPadron> listaAux = new ArrayList<AdminPadron>();
		for(AdminPadron adminPadron : listaAdminPadronesParaSolicitud){
			listaAux.add(adminPadron);
			if(adminPadron.getChecked()){
				listaAdminPadronesParaSolicitudSelec.add(adminPadron);
				listaAux.remove(adminPadron);
			}
		}
		listaAdminPadronesParaSolicitud = listaAux;
	}
	
	public void buscarPadrones(ActionEvent event) {
		try {
			log.info("bus:"+adminPadronFiltro);
			listaRegistros = estructuraFacade.getAdminPadronBusqueda(adminPadronFiltro);
		} catch (BusinessException e) {
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscar(ActionEvent event){
		if(adminPadronFiltro.getId().getIntNivel().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
			adminPadronFiltro.getId().setIntNivel(null);
		}
		if(adminPadronFiltro.getId().getIntParaModalidadCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
			adminPadronFiltro.getId().setIntParaModalidadCod(null);
		}
		if(adminPadronFiltro.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
			adminPadronFiltro.setIntParaEstadoCod(null);
		}
		
		if(intTipoBusqueda == Constante.PARAM_T_TIPOREGISTROPADRON_PADRON){
			buscarPadrones(event);
			mostrarGrillaPadrones = Boolean.TRUE;
		}else{
			buscarSolicitudes(event);
			mostrarGrillaPadrones = Boolean.FALSE;
		}
	}
	
	public void buscarSolicitudes(ActionEvent event){
		try {			
			
			SolicitudPagoDetalle solPagoDet = new SolicitudPagoDetalle();
			solPagoDet.setIntPeriodo(adminPadronFiltro.getId().getIntPeriodo());
			solPagoDet.setIntMes(adminPadronFiltro.getId().getIntMes());
			solPagoDet.setIntNivel(adminPadronFiltro.getId().getIntNivel());
			solPagoDet.setIntParaModalidadCod(adminPadronFiltro.getId().getIntParaModalidadCod());
			solPagoDet.getSolicitudPago().setIntParaEstadoCod(adminPadronFiltro.getIntParaEstadoCod());
			solPagoDet.getSolicitudPago().setIntParaEstadoPagoCod(adminPadronFiltro.getSolicitudPagoDetalle().getSolicitudPago().getIntParaEstadoPagoCod());
			
			listaRegistros = estructuraFacade.getSolicitudBusqueda(adminPadronFiltro);
		} catch (BusinessException e) {
			//e.printStackTrace();
			log.error(e.getMessage(),e);
		}		
	}
	
	public void eliminarFilaPadron(ActionEvent event){
		AdminPadron item = (AdminPadron)event.getComponent().getAttributes().get("item");
		listaAdminPadronesParaSolicitud.add(item);
		listaAdminPadronesParaSolicitudSelec.remove(item);
	}	

	public void buscarEstructuraPorNombre(ActionEvent event){
		String nombre = estructuraFiltro.getJuridica().getStrRazonSocial();
		if(nombre != null && nombre.length()>0){
			List<Estructura> listaAux = new ArrayList<Estructura>();
			for(Object o : listaEstructuras){
				Estructura estructura = (Estructura)o;
				if(estructura.getJuridica().getStrRazonSocial().toUpperCase().contains(nombre.toUpperCase())){
					listaAux.add(estructura);
				}
			}			
			listaEstructuras = listaAux;
		}else{
			buscarEntidades(event);
		}
	}	
	
	public void buscarAdminPadronPorNombre(ActionEvent event){
		String nombre = adminPadronFiltroSolicitud.getEstructura().getJuridica().getStrRazonSocial();
		if(nombre != null && nombre.length()>0){
			List<AdminPadron> listaAux = new ArrayList<AdminPadron>();
			for(AdminPadron adminPadron : listaAdminPadronesParaSolicitud){
				if(adminPadron.getEstructura().getJuridica().getStrRazonSocial().toUpperCase().contains(nombre.toUpperCase())){
					listaAux.add(adminPadron);
				}
			}
			listaAdminPadronesParaSolicitud = listaAux;
		}else{
			buscarPadronesSinSolicitud(event);
		}
	}
	
	public void buscarAdminPadronConNombre(ActionEvent event){
		String nombre = adminPadronFiltro.getEstructura().getJuridica().getStrRazonSocial();
		if(nombre != null && nombre.length()>0){
			List<AdminPadron> listaAux = new ArrayList<AdminPadron>();
			List<AdminPadron> listaAdminPadronesAux = listaRegistros;
			for(AdminPadron adminPadron : listaAdminPadronesAux){
				if(adminPadron.getEstructura().getJuridica().getStrRazonSocial().toUpperCase().contains(nombre.toUpperCase())){
					listaAux.add(adminPadron);
				}
			}
			listaRegistros = listaAux;
		}else{
			buscarPadrones(event);
		}
	}
	
	public void cambioTipoRegistro(){
		switch(intTipoBusqueda.intValue()){
			case 1 :
				mostrarComboDocumento = Boolean.FALSE;
				break;
			case 2 :
				mostrarComboDocumento = Boolean.TRUE;
				break;
		}
	}
	
	public void cambioNivelEntidadPanelInferior(){
		adminPadronNuevo.getEstructura().setJuridica(new Juridica());
	}
	
	public void cambioTipoModalidadPanelInf(){
		if(adminPadronNuevo.getId().getIntParaModalidadCod().intValue() == Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS
				 || adminPadronNuevo.getId().getIntParaModalidadCod().intValue() == Constante.PARAM_T_MODALIDADPLANILLA_CAS
		){
			adminPadronNuevo.getId().setIntParaTipoSocioCod(Constante.PARAM_T_TIPOSOCIO_ACTIVO);
			deshabilitarPadronSocio = Boolean.TRUE;
		}else{
			deshabilitarPadronSocio = Boolean.FALSE;
		}		
	}
	
	public void preEliminarRegistro(ActionEvent event){
		registroEliminar = (AdminPadron)event.getComponent().getAttributes().get("item");
		permitirEliminarRegistro = Boolean.FALSE;
		//Si se trata de un registro de adminPadron
		log.info("mostrarGrillaPadrones:"+mostrarGrillaPadrones);
		if(mostrarGrillaPadrones==Boolean.TRUE){
			if(registroEliminar.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				log.info("registroEliminar.getSolicitudPagoDetalle().getId().getIntNumero():"+registroEliminar.getSolicitudPagoDetalle().getId().getIntNumero());
				//Si el adminPadron tiene una solicitud asociada
				if(registroEliminar.getSolicitudPagoDetalle().getId().getIntNumero()!=null){
					log.info("registroEliminar.getSolicitudPagoDetalle().getSolicitudPago().getIntParaEstadoCod():"+registroEliminar.getSolicitudPagoDetalle().getSolicitudPago().getIntParaEstadoCod());
					//Si la solicitud asociada esta anulada
					if(registroEliminar.getSolicitudPagoDetalle().getSolicitudPago().getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
						permitirEliminarRegistro = Boolean.TRUE;
					}
				}else{
					permitirEliminarRegistro = Boolean.TRUE;
				}
			}
			
		}
		//Si se trata de un registro de solicitud
		else{
			permitirEliminarRegistro = Boolean.FALSE;
		}
		log.info("permitirEliminarRegistro:"+permitirEliminarRegistro);
		log.info("a preEliminar:"+registroEliminar);
	}
	
	public void eliminarRegistro(ActionEvent event){
		log.info("eliminarRegistro");
		boolean exito = false;
		String mensaje = null;
		try {
			listaRegistros.remove(registroEliminar);
			if(registroEliminar.getIntParaTipoPadronCod() != null){				
				log.info("Eliminar Padron");
				exito = eliminarPadron(registroEliminar,estructuraFacade);
				mensaje = "Padron eliminado correctamente";
			}else{
				log.info("Eliminar Solicitud");
				exito = eliminarSolicitud(registroEliminar,estructuraFacade);
				mensaje = "Solicitud eliminada correctamente";
			}
		} catch (EJBFactoryException e) {
			mensaje = "Ocurrio un error realizando la operaciòn de eliminación";
			//e.printStackTrace();
			log.error(e.getMessage(),e);
		} catch (BusinessException e) {
			mensaje = "Ocurrio un error realizando la operaciòn de eliminación";
			//e.printStackTrace();
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			mensaje = "Ocurrio un error realizando la operaciòn de eliminación";
			//e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		
		mostrarMensaje(exito,mensaje);
	}
	
	private boolean eliminarPadron(AdminPadron adminPadron, EstructuraFacadeLocal facade) throws BusinessException, Exception{
		boolean exito = false;
		adminPadron.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		adminPadron.setAdPaFechaEliminacion(new Date());
		adminPadron = facade.modificarAdminPadron(adminPadron);
		exito = true;
		return exito;
	}
	
	private boolean eliminarSolicitud(AdminPadron adminPadron, EstructuraFacadeLocal facade) throws BusinessException, Exception{
		boolean exito = false;
		SolicitudPago solicitudPago = facade.getSolicitudPorPk(adminPadron.getSolicitudPagoDetalle().getId().getIntNumero());
		solicitudPago.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		solicitudPago.setDtFechaEliminacion(new Date());
		solicitudPago.setIntPersPersonaEliminar(usuario.getIntPersPersonaPk());
		solicitudPago.setIntPersEmpresaEliminar(usuario.getPerfil().getId().getIntPersEmpresaPk());
		facade.modificarSolicitud(solicitudPago);
		exito = true;
		return exito;
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
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public boolean isIngresarPadronRendered() {
		return ingresarPadronRendered;
	}
	public void setIngresarPadronRendered(boolean ingresarPadronRendered) {
		this.ingresarPadronRendered = ingresarPadronRendered;
	}
	public boolean isNuevaSolicitudPagoRendered() {
		return nuevaSolicitudPagoRendered;
	}
	public void setNuevaSolicitudPagoRendered(boolean nuevaSolicitudPagoRendered) {
		this.nuevaSolicitudPagoRendered = nuevaSolicitudPagoRendered;
	}
	public boolean isSeleccionarArchivoRendered() {
		return seleccionarArchivoRendered;
	}
	public void setSeleccionarArchivoRendered(boolean seleccionarArchivoRendered) {
		this.seleccionarArchivoRendered = seleccionarArchivoRendered;
	}
	public boolean isIngresarContraseña() {
		return ingresarContraseña;
	}
	public void setIngresarContraseña(boolean ingresarContraseña) {
		this.ingresarContraseña = ingresarContraseña;
	}
	public boolean isPanelInferiorRendered() {
		return panelInferiorRendered;
	}
	public void setPanelInferiorRendered(boolean panelInferiorRendered) {
		this.panelInferiorRendered = panelInferiorRendered;
	}
	public AdminPadron getAdminPadronNuevo() {
		return adminPadronNuevo;
	}
	public void setAdminPadronNuevo(AdminPadron adminPadronNuevo) {
		this.adminPadronNuevo = adminPadronNuevo;
	}
	public AdminPadron getPadronFiltro() {
		return adminPadronFiltro;
	}
	public void setPadronFiltro(AdminPadron padronFiltro) {
		this.adminPadronFiltro = padronFiltro;
	}
	public AdminPadron getAdminPadronFiltro() {
		return adminPadronFiltro;
	}
	public void setAdminPadronFiltro(AdminPadron adminPadronFiltro) {
		this.adminPadronFiltro = adminPadronFiltro;
	}
	public AdminPadron getAdminPadronFiltroSolicitud() {
		return adminPadronFiltroSolicitud;
	}
	public void setAdminPadronFiltroSolicitud(AdminPadron adminPadronFiltroSolicitud) {
		this.adminPadronFiltroSolicitud = adminPadronFiltroSolicitud;
	}
	public SolicitudPago getSolicitudPagoNuevo() {
		return solicitudPagoNuevo;
	}
	public void setSolicitudPagoNuevo(SolicitudPago solicitudPagoNuevo) {
		this.solicitudPagoNuevo = solicitudPagoNuevo;
	}
	public boolean isPanelPadronesSinSolicitud() {
		return panelPadronesSinSolicitud;
	}
	public void setPanelPadronesSinSolicitud(boolean panelPadronesSinSolicitud) {
		this.panelPadronesSinSolicitud = panelPadronesSinSolicitud;
	}
	public List<Estructura> getListaEstructuras() {
		return listaEstructuras;
	}
	public void setListaEstructuras(List<Estructura> listaEstructuras) {
		this.listaEstructuras = listaEstructuras;
	}
	public Estructura getEstructuraFiltro() {
		return estructuraFiltro;
	}
	public void setEstructuraFiltro(Estructura estructuraFiltro) {
		this.estructuraFiltro = estructuraFiltro;
	}
	public boolean isDeshabilitarSolicitud() {
		return deshabilitarSolicitud;
	}
	public void setDeshabilitarSolicitud(boolean deshabilitarSolicitud) {
		this.deshabilitarSolicitud = deshabilitarSolicitud;
	}
	public boolean isDeshabilitarPadron() {
		return deshabilitarPadron;
	}
	public void setDeshabilitarPadron(boolean deshabilitarPadron) {
		this.deshabilitarPadron = deshabilitarPadron;
	}
	public Integer getIntTipoBusqueda() {
		return intTipoBusqueda;
	}
	public void setIntTipoBusqueda(Integer intTipoBusqueda) {
		this.intTipoBusqueda = intTipoBusqueda;
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
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
	public boolean isDeshabilitarPadronSocio() {
		return deshabilitarPadronSocio;
	}
	public void setDeshabilitarPadronSocio(boolean deshabilitarPadronSocio) {
		this.deshabilitarPadronSocio = deshabilitarPadronSocio;
	}
	public boolean isMostrarComboDocumento() {
		return mostrarComboDocumento;
	}
	public void setMostrarComboDocumento(boolean mostrarComboDocumento) {
		this.mostrarComboDocumento = mostrarComboDocumento;
	}
	public List getListaAdminPadrones() {
		return listaRegistros;
	}
	public void setListaAdminPadrones(List listaAdminPadrones) {
		this.listaRegistros = listaAdminPadrones;
	}
	public List getListaRegistros() {
		return listaRegistros;
	}
	public void setListaRegistros(List listaRegistros) {
		this.listaRegistros = listaRegistros;
	}
	public boolean isMostrarGrillaPadrones() {
		return mostrarGrillaPadrones;
	}
	public void setMostrarGrillaPadrones(boolean mostrarGrillaPadrones) {
		this.mostrarGrillaPadrones = mostrarGrillaPadrones;
	}
	public List getListaEstados() {
		return listaEstados;
	}
	public void setListaEstados(List listaEstados) {
		this.listaEstados = listaEstados;
	}
	public boolean isPermitirEliminarRegistro() {
		return permitirEliminarRegistro;
	}
	public void setPermitirEliminarRegistro(boolean permitirEliminarRegistro) {
		this.permitirEliminarRegistro = permitirEliminarRegistro;
	}
	public List<AdminPadron> getListaAdminPadronesParaSolicitud() {
		return listaAdminPadronesParaSolicitud;
	}
	public void setListaAdminPadronesParaSolicitud(
			List<AdminPadron> listaAdminPadronesParaSolicitud) {
		this.listaAdminPadronesParaSolicitud = listaAdminPadronesParaSolicitud;
	}
	public List<AdminPadron> getListaAdminPadronesParaSolicitudSelec() {
		return listaAdminPadronesParaSolicitudSelec;
	}
	public void setListaAdminPadronesParaSolicitudSelec(
			List<AdminPadron> listaAdminPadronesParaSolicitudSelec) {
		this.listaAdminPadronesParaSolicitudSelec = listaAdminPadronesParaSolicitudSelec;
	}
	public Password getPasswordIngresado() {
		return passwordIngresado;
	}
	public void setPasswordIngresado(Password passwordIngresado) {
		this.passwordIngresado = passwordIngresado;
	}
	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}
	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}
	public boolean isHabilitarComboTipoArchivo() {
		return habilitarComboTipoArchivo;
	}
	public void setHabilitarComboTipoArchivo(boolean habilitarComboTipoArchivo) {
		this.habilitarComboTipoArchivo = habilitarComboTipoArchivo;
	}
	public boolean isHabilitarProcesar() {
		return habilitarProcesar;
	}
	public void setHabilitarProcesar(boolean habilitarProcesar) {
		this.habilitarProcesar = habilitarProcesar;
	}
	public List getListaAnios() {
		return listaAnios;
	}
	public void setListaAnios(List listaAnios) {
		this.listaAnios = listaAnios;
	}
}