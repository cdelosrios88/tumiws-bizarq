package pe.com.tumi.seguridad.permiso.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.file.domain.File;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalle;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeLocal;

public class HorarioController {

	protected static Logger log = Logger.getLogger(HorarioController.class);	
	
	private boolean mostrarPanelInferior;
	private boolean seleccionaIndeterminado;
	private boolean habilitarFechaFin;
	private boolean seleccionaFeriados;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarBtnEliminar;
	private boolean registrarNuevo;
	private boolean subioArchivo;
	private boolean habilitarGrabar;
	private String mensajeOperacion;
	private String target;
	
	private Date fechaInicio;
	private Date fechaFin;
	private DiasAccesos diasAccesosFiltro;
	private DiasAccesos nuevoDiasAccesos;
	private DiasAccesos registroSeleccionado;
	private List listaDiasAccesos;	
	private List<DiasAccesosDetalle> listaDiasAccesosDetalle;
	private TablaFacadeRemote tablaFacade;
	private PermisoFacadeLocal permisoFacade;
	private PersonaFacadeRemote personaFacade;
	private GeneralFacadeRemote generalFacade;
	private List listaEmpresas;
	private TipoArchivo tipoArchivo;
	private Archivo archivo;
	private Usuario usuario;
	
	public HorarioController(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	private void cargarValoresIniciales(){
		try {
			habilitarGrabar = Boolean.FALSE;
			subioArchivo = Boolean.FALSE;
			registrarNuevo = Boolean.FALSE;
			mostrarBtnEliminar = Boolean.TRUE;
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			mostrarPanelInferior = Boolean.FALSE;
			habilitarFechaFin = Boolean.TRUE;
			seleccionaFeriados = Boolean.FALSE;
			seleccionaIndeterminado = Boolean.FALSE;
			diasAccesosFiltro = new DiasAccesos();
			nuevoDiasAccesos = new DiasAccesos();
			listaDiasAccesos = new ArrayList<DiasAccesos>();
			listaDiasAccesosDetalle = new ArrayList<DiasAccesosDetalle>();
			tipoArchivo = new TipoArchivo();
			archivo = new Archivo();
			archivo.setId(new ArchivoId());
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			permisoFacade = (PermisoFacadeLocal) EJBFactory.getLocal(PermisoFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			tipoArchivo = generalFacade.getTipoArchivoPorPk(Constante.PARAM_T_TIPOARCHIVO_HORARIO);
			listaEmpresas = personaFacade.getListaJuridicaDeEmpresa();
			log.info(tipoArchivo);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	
	private Timestamp convertirATimestamp(int hora, int minuto){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,hora);
		cal.set(Calendar.MINUTE,minuto);
		cal.set(Calendar.SECOND,0);
		return new Timestamp(cal.getTimeInMillis());
	}
	
	
	public void grabar(){
		boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			if(fechaInicio==null){
				mensaje = "Debe seleccionar una fecha de inicio";
				return;
			}
			
			if(seleccionaIndeterminado){
				nuevoDiasAccesos.setTsFechaInicio(new Timestamp(fechaInicio.getTime()));
				nuevoDiasAccesos.setTsFechaFin(null);
			}else{
				if(fechaFin==null){
					mensaje = "Debe seleccionar una fecha de fin";
					return;
				}
				//valida que la fecha final sea mayor que la inicial
				if(fechaInicio.compareTo(fechaFin)>0){
					mensaje = "Ocurrio un error de validación con las fechas";
					return;
				}
				nuevoDiasAccesos.setTsFechaInicio(new Timestamp(fechaInicio.getTime()));
				nuevoDiasAccesos.setTsFechaFin(new Timestamp(fechaFin.getTime()));
			}
			
			if(seleccionaFeriados){
				nuevoDiasAccesos.setIntFeriados(1);
			}else{
				nuevoDiasAccesos.setIntFeriados(0);
			}
			
			
			log.info(nuevoDiasAccesos);
			ArrayList<DiasAccesosDetalle> listaDiasAccesosDetalleSeleccionados = new ArrayList<DiasAccesosDetalle>();
			for(DiasAccesosDetalle d : listaDiasAccesosDetalle){
				if(d.getChecked()){
					d.getId().setIntPersEmpresa(nuevoDiasAccesos.getId().getIntPersEmpresa());
					d.getId().setIntIdTipoSucursal(nuevoDiasAccesos.getId().getIntIdTipoSucursal());
					d.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					d.setTsHoraFin(convertirATimestamp(d.getIntHoraFin(),d.getIntMinutoFin()));
					d.setTsHoraInicio(convertirATimestamp(d.getIntHoraInicio(),d.getIntMinutoInicio()));
					if(d.getTsHoraInicio().compareTo(d.getTsHoraFin())>0){
						mensaje = "Ocurrio un error de validación con las horas";
						return;
					}
					//log.info(d);
					listaDiasAccesosDetalleSeleccionados.add(d);
				}
			}
			
			if(listaDiasAccesosDetalleSeleccionados.isEmpty()){
				mensaje="Ocurrio un error. No ha seleccionado los dias relacionados al horario";
				return;
			}
			
			if(nuevoDiasAccesos.getStrMotivo()==null || nuevoDiasAccesos.getStrMotivo().isEmpty()){
				mensaje="Ocurrio un error. No ha ingresado un motivo";
				return;
			}			
			
			//log.info(nuevoDiasAccesos);
			
			if(registrarNuevo){
				//Se graba el nuevo horario
				if(!subioArchivo){
					mensaje="Ocurrio un error. No se ha adjuntado un archivo";
					return;
				}
				nuevoDiasAccesos.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				for(DiasAccesosDetalle d : listaDiasAccesosDetalleSeleccionados){
					d.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				}
				nuevoDiasAccesos = permisoFacade.grabarDiasAccesos(nuevoDiasAccesos,listaDiasAccesosDetalleSeleccionados);
				mensaje = "Se registró correctamente el horario";
			}else{
				//Se modifica el horario 
				for(DiasAccesosDetalle d : listaDiasAccesosDetalleSeleccionados){
					d.setIntIdEstado(nuevoDiasAccesos.getIntIdEstado());
				}
				nuevoDiasAccesos = permisoFacade.modificarDiasAccesosYDetalle(nuevoDiasAccesos,listaDiasAccesosDetalleSeleccionados);
				mensaje = "Se modificó correctamente el horario";				
			}
			//Grabar Archivo
			if(subioArchivo){
				subioArchivo = Boolean.FALSE;
				archivo.getId().setIntParaTipoCod(tipoArchivo.getIntParaTipoCod());
				archivo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				archivo = generalFacade.grabarArchivo(archivo);
				nuevoDiasAccesos.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
				nuevoDiasAccesos.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
				nuevoDiasAccesos.setIntParaTipoArchivo(archivo.getId().getIntParaTipoCod());
				nuevoDiasAccesos = permisoFacade.modificarDiasAccesos(nuevoDiasAccesos);
				if(target !=null && target.length()>0){
					FileUtil.renombrarArchivo(target, tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}else{
					mensaje = "Ocurrio un error. No se ha procesado correctamente el archivo";
					return;
				}
			}
			exito = Boolean.TRUE;
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			//habilitarFechaFin = Boolean.FALSE;
		} catch (BusinessException e) {
			mensaje = "Ocurrio un error durante el proceso de registro del horario";
			e.printStackTrace();
		} catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de registro del horario";
			e.printStackTrace();
		} finally{			
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
	
	public void buscar(ActionEvent event ){
		try {
			log.info("a buscar:"+diasAccesosFiltro);
			
			if(diasAccesosFiltro.getId().getIntIdTipoSucursal().equals(Constante.PARAM_COMBO_TODOS)){
				diasAccesosFiltro.getId().setIntIdTipoSucursal(null);
			}
			if(diasAccesosFiltro.getIntIdEstado().equals(Constante.PARAM_COMBO_TODOS)){
				diasAccesosFiltro.setIntIdEstado(null);
			}
			if(diasAccesosFiltro.getId().getIntPersEmpresa().equals(Constante.PARAM_COMBO_SELECCIONAR)){
				diasAccesosFiltro.getId().setIntPersEmpresa(null);
			}
			listaDiasAccesos = permisoFacade.buscarDiasAccesosPorTipoSucursalYEstadoYEmpresa(diasAccesosFiltro);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cargarAccesosDiasDetalle(){
		listaDiasAccesosDetalle.clear();
		DiasAccesosDetalle diasAccesosDetalle = null;
		for(int i=1;i<8;i++){
			diasAccesosDetalle = new DiasAccesosDetalle();
			diasAccesosDetalle.getId().setIntIdDiaSemana(i);
			listaDiasAccesosDetalle.add(diasAccesosDetalle);
		}
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.TRUE;
		registrarNuevo = Boolean.TRUE;
		mostrarPanelInferior = Boolean.TRUE;
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		deshabilitarNuevo = Boolean.FALSE;
		cargarAccesosDiasDetalle();
		nuevoDiasAccesos = new DiasAccesos();
		fechaInicio = null;
		fechaFin = null;
		seleccionaIndeterminado = Boolean.FALSE;
		seleccionaFeriados = Boolean.FALSE;
		habilitarFechaFin = Boolean.TRUE;
		
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
	}
	
	public void manejarIndeterminado(){
		if(seleccionaIndeterminado==Boolean.TRUE){
			habilitarFechaFin = Boolean.FALSE;
			fechaFin = null;
		}else{
			habilitarFechaFin = Boolean.TRUE;
		}
	}
	
	private void cargarRegistro() throws BusinessException{
		
		//Se carga los DiasAccesosDetalle iniciales y se superponen los DiasAccesosDetalle, 
		//que se encuentran en la bd, relacionados con el registro seleccionado
		cargarAccesosDiasDetalle();
		List<DiasAccesosDetalle> listaBD = permisoFacade.getListaDiasAccesosDetallePorCabecera(registroSeleccionado);
		List<DiasAccesosDetalle> listaAux = new ArrayList<DiasAccesosDetalle>();
		//Solo tomamos en cuenta los detalles no anulados
		for(DiasAccesosDetalle dad : listaBD){
			if(dad.getIntIdEstado().intValue()!=Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO){
				listaAux.add(dad);
			}
		}
		listaBD = listaAux;
		int i=0;
		while(i<listaDiasAccesosDetalle.size()){
			DiasAccesosDetalle dad = listaDiasAccesosDetalle.get(i);
			for(DiasAccesosDetalle dadBD : listaBD){
				if(dad.getId().getIntIdDiaSemana().intValue()==dadBD.getId().getIntIdDiaSemana().intValue()){
					dadBD.setChecked(Boolean.TRUE);
					Calendar cal = Calendar.getInstance();
					cal.setTime(dadBD.getTsHoraFin());
					dadBD.setIntHoraFin(cal.get(Calendar.HOUR_OF_DAY));
					dadBD.setIntMinutoFin(cal.get(Calendar.MINUTE));
					cal.setTime(dadBD.getTsHoraInicio());
					dadBD.setIntHoraInicio(cal.get(Calendar.HOUR_OF_DAY));
					dadBD.setIntMinutoInicio(cal.get(Calendar.MINUTE));
					listaDiasAccesosDetalle.set(i, dadBD);						
				}
			}
			i++;
		}
		registrarNuevo = Boolean.FALSE;
		nuevoDiasAccesos = registroSeleccionado;
		//Para mostrar en la interfaz los checks seleccionados
		if(nuevoDiasAccesos.getIntFeriados().intValue()==1){
			seleccionaFeriados = Boolean.TRUE;
		}else{
			seleccionaFeriados = Boolean.FALSE;
		}
		if(nuevoDiasAccesos.getTsFechaFin()==null){
			seleccionaIndeterminado = Boolean.TRUE;
			habilitarFechaFin = Boolean.FALSE;
		}else{
			seleccionaIndeterminado = Boolean.FALSE;
			habilitarFechaFin = Boolean.TRUE;
		}
		fechaInicio = nuevoDiasAccesos.getTsFechaInicio();
		fechaFin = nuevoDiasAccesos.getTsFechaFin();
		//Obtener archivo asociado a diasAccesos
		ArchivoId archivoId = new ArchivoId();
		archivoId.setIntItemHistorico(nuevoDiasAccesos.getIntParaItemHistorico());
		archivoId.setIntItemArchivo(nuevoDiasAccesos.getIntParaItemArchivo());
		archivoId.setIntParaTipoCod(nuevoDiasAccesos.getIntParaTipoArchivo());
		Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
		if(archivo!=null){
			nuevoDiasAccesos.setArchivo(archivo);
		}else{
			archivo = new Archivo();
			archivo.setStrNombrearchivo("No existe");
			nuevoDiasAccesos.setArchivo(archivo);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
		registroSeleccionado = (DiasAccesos)event.getComponent().getAttributes().get("item");
		if(registroSeleccionado.getIntIdEstado().intValue()==Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO){
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
		log.info("reg selec:"+registroSeleccionado);
		}catch (Exception e) {
			e.printStackTrace();
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
		} catch (BusinessException e) {			
			e.printStackTrace();
		}
	}
	
	public void eliminarRegistro(){
		boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {					
			listaDiasAccesos.remove(registroSeleccionado);			
			registroSeleccionado = permisoFacade.eliminarDiasAccesos(registroSeleccionado);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		if(registroSeleccionado.getIntIdEstado().intValue()==Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO.intValue()){
			exito = Boolean.TRUE;
			mensaje = "El proceso de eliminación se realizo correctamente";			
		}else{
			mensaje = "Ocurrio un error durante el proceso de eliminación";			
		}
		log.info(mensaje);
		mostrarMensaje(exito, mensaje);
		deshabilitarPanelInferior(null);
	}
	
	public void manejarSubirArchivo(UploadEvent event){
		log.info("--manejarSubirArchivo");
		
		UploadItem uploadItem = event.getUploadItem();
		String fileName = uploadItem.getFileName();		
		archivo.setStrNombrearchivo(fileName);
		java.io.File file = uploadItem.getFile();		 
		target = tipoArchivo.getStrRuta() + "\\" + fileName;
		File f = new File();
		f.setName(fileName);		 
		log.info("target: "+target);
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(target);
			byte[] buf = new byte[1024*1024*15];//Máximo 15MB
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			subioArchivo = Boolean.TRUE;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				in.close();
				out.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public List getListaDiasAccesos() {
		return listaDiasAccesos;
	}
	public void setListaDiasAccesos(List listaDiasAccesos) {
		this.listaDiasAccesos = listaDiasAccesos;
	}
	public DiasAccesos getDiasAccesosFiltro() {
		return diasAccesosFiltro;
	}
	public void setDiasAccesosFiltro(DiasAccesos diasAccesosFiltro) {
		this.diasAccesosFiltro = diasAccesosFiltro;
	}
	public DiasAccesos getNuevoDiasAccesos() {
		return nuevoDiasAccesos;
	}
	public void setNuevoDiasAccesos(DiasAccesos nuevoDiasAccesos) {
		this.nuevoDiasAccesos = nuevoDiasAccesos;
	}
	public boolean isMostrarPanelInferior() {
		return mostrarPanelInferior;
	}
	public void setMostrarPanelInferior(boolean mostrarPanelInferior) {
		this.mostrarPanelInferior = mostrarPanelInferior;
	}
	public boolean isSeleccionaIndeterminado() {
		return seleccionaIndeterminado;
	}
	public void setSeleccionaIndeterminado(boolean seleccionaIndeterminado) {
		this.seleccionaIndeterminado = seleccionaIndeterminado;
	}
	public boolean isHabilitarFechaFin() {
		return habilitarFechaFin;
	}
	public void setHabilitarFechaFin(boolean habilitarFechaFin) {
		this.habilitarFechaFin = habilitarFechaFin;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public List getListaEmpresas() {
		return listaEmpresas;
	}
	public void setListaEmpresas(List listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}
	public boolean isSeleccionaFeriados() {
		return seleccionaFeriados;
	}
	public void setSeleccionaFeriados(boolean seleccionaFeriados) {
		this.seleccionaFeriados = seleccionaFeriados;
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
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public List<DiasAccesosDetalle> getListaDiasAccesosDetalle() {
		return listaDiasAccesosDetalle;
	}
	public void setListaDiasAccesosDetalle(
			List<DiasAccesosDetalle> listaDiasAccesosDetalle) {
		this.listaDiasAccesosDetalle = listaDiasAccesosDetalle;
	}
	public boolean isDeshabilitarNuevo() {
		return deshabilitarNuevo;
	}
	public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
		this.deshabilitarNuevo = deshabilitarNuevo;
	}
	public boolean isMostrarBtnEliminar() {
		return mostrarBtnEliminar;
	}
	public void setMostrarBtnEliminar(boolean mostrarBtnEliminar) {
		this.mostrarBtnEliminar = mostrarBtnEliminar;
	}
	public boolean isSubioArchivo() {
		return subioArchivo;
	}
	public void setSubioArchivo(boolean subioArchivo) {
		this.subioArchivo = subioArchivo;
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
	
}
