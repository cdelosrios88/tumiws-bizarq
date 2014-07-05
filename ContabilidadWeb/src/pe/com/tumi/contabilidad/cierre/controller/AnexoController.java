package pe.com.tumi.contabilidad.cierre.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.AnexoDetalleException;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.Anexo;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleOperador;
import pe.com.tumi.contabilidad.cierre.facade.CierreFacadeLocal;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class AnexoController {

	protected static Logger log = Logger.getLogger(AnexoController.class);	
	
	CierreFacadeLocal cierreFacade;
	PlanCuentaFacadeLocal planCuentaFacade;
	TablaFacadeRemote tablaFacade;
	
	private List listaAnios;
	private List listaAnexo;
	private List<AnexoDetalle> listaAnexoDetalle;
	private List<AnexoDetalleOperador> listaAnexoDetalleOperador;
	private List<AnexoDetalleOperador> listaAnexoDetalleOperadorPersiste;
	private List listaTipoAnexo;
	
	private Anexo anexoNuevo;
	private Anexo anexoFiltro;
	private Anexo registroSeleccionado;
	private AnexoDetalle anexoDetalleSeleccionado;
	
	private String 	mensajeOperacion;
	private Integer	intOrdenCorrelativo;
	
	private final int cantidadAñosLista = 4;
	
	private Usuario usuario;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	
	
	public AnexoController(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}	
	
	
	public void cargarValoresIniciales(){
		try{
			listaAnexo = new ArrayList<Anexo>();
			anexoFiltro = new Anexo();
			
			cierreFacade = (CierreFacadeLocal) EJBFactory.getLocal(CierreFacadeLocal.class);
			planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			
			listaTipoAnexo  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSFINANCIEROS), "B");			
			cargarListaAnios();
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
	
	private void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
	
	public void grabar(){
		String mensaje = "";
		boolean exito = Boolean.FALSE;
		try{
			
			anexoNuevo.setIntParaTipoLibroAnexo(Constante.TIPO_LIBRO);
			//Inicia validaciones
			if(anexoNuevo.getStrDescripcion()==null || anexoNuevo.getStrDescripcion().isEmpty()){
				mensaje = "Ocurrio un error durante el registró del esquema. Debe ingresar una descripción del esquema.";
				return;
			}
			boolean existeTextoEnBlanco = Boolean.FALSE;
			AnexoDetalle anexoDetalleEnBlanco = null;
			for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
				if(anexoDetalle.getStrTexto()==null || anexoDetalle.getStrTexto().isEmpty()){
					existeTextoEnBlanco = Boolean.TRUE;
					anexoDetalleEnBlanco = anexoDetalle;
					break;
				}
			}
			if(existeTextoEnBlanco){
				mensaje = "Ocurrió un error durante el registro del esquema. Debe ingresar la " +
						"descripción del elemento "+anexoDetalleEnBlanco.getStrNumeracion()+" .";
				return;
			}
			//Fin validaciones
			
			//calcular campo intHabilitarCuenta en AnexoDetalle
			for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
				anexoDetalle.setIntHabilitarCuenta(Constante.HABILITAR_CUENTA_NO);
				if(buscarListaHijos(anexoDetalle).isEmpty()){
					if(anexoDetalle.getListaAnexoDetalleOperador()== null || anexoDetalle.getListaAnexoDetalleOperador().isEmpty()){
						anexoDetalle.setIntHabilitarCuenta(Constante.HABILITAR_CUENTA_SI);
					}
				}
			}
			
			anexoNuevo.setListaAnexoDetalle(listaAnexoDetalle);
			if(registrarNuevo){
				anexoNuevo.getId().setIntPersEmpresaAnexo(usuario.getPerfil().getId().getIntPersEmpresaPk());
				anexoNuevo.setTsFechaRegistro(new Timestamp (new Date().getTime()));
				anexoNuevo.setIntPersEmpresaUsuario(usuario.getPerfil().getId().getIntPersEmpresaPk());
				anexoNuevo.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());				
				if(!cierreFacade.buscarAnexo(anexoNuevo).isEmpty()){
					mensaje = "Ya existe un anexo creado con ese periodo y tipo.";
					return;
				}
				cierreFacade.grabarAnexo(anexoNuevo);
				mensaje = "El esquema se registró correctamente.";				
			}else{
				cierreFacade.modificarAnexo(anexoNuevo);
				mensaje = "El esquema se modificó correctamente.";
			}
			exito = Boolean.TRUE;
			
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			//buscar();
		}catch(AnexoDetalleException e){
			mensaje = e.getMessage();
			log.error(e.getMessage(),e);			
		}catch (Exception e) {
			mensaje= "Ocurrió un error mientras se registraba el Esquema de estados financieros.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito, mensaje);
		}
	}
	
	
	public void buscar(){
		try{
			anexoFiltro.getId().setIntPersEmpresaAnexo(usuario.getPerfil().getId().getIntPersEmpresaPk());
			anexoFiltro.setIntParaTipoLibroAnexo(Constante.TIPO_LIBRO);
			listaAnexo = cierreFacade.buscarAnexo(anexoFiltro);
			
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (Anexo)event.getComponent().getAttributes().get("item");

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
			deshabilitarNuevo = Boolean.FALSE;
			ocultarMensaje();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarRegistro(){
		String mensaje = "";
		boolean exito = Boolean.FALSE;
		try{
			cierreFacade.eliminarAnexo(registroSeleccionado);
			buscar();
			mensaje = "Se eliminó correctamente el esquema.";
			exito = Boolean.TRUE;
		}catch(AnexoDetalleException e){
			mensaje = e.getMessage();
			log.error(e.getMessage(),e);
		}catch(Exception e){
			mensaje = "Ocurrió un error durante el proceso de eliminación.";
			log.error(e.getMessage(),e);			
		}finally{
			mostrarMensaje(exito, mensaje);
		}
	}
	
	private AnexoDetalle buscarAnexoDetalle(Integer intNivel, Integer intPosicion, Integer intItem){
		for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
			if(anexoDetalle.getIntNivel().equals(intNivel) 
				&& anexoDetalle.getIntPosicion().equals(intPosicion)
				&& anexoDetalle.getIntItem().equals(intItem)){
				return anexoDetalle;
			}
		}
		return null;
	}
	
	private AnexoDetalle buscarAnexoDetallePorItemAnexoDetalle(Integer intAnexoDetalle){
		for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
			if(anexoDetalle.getId().getIntItemAnexoDetalle().equals(intAnexoDetalle)){
				return anexoDetalle;
			}
		}
		return null;
	}
	
	private void cargarRegistro(){
		try{
			log.info("--cargarRegistro");
			anexoNuevo = registroSeleccionado;
			listaAnexoDetalle = cierreFacade.getListaAnexoDetallePorAnexo(anexoNuevo);
			List<AnexoDetalleOperador> listaAnexoDetalleOperador;
			List<AnexoDetalleCuenta> listaAnexoDetalleCuenta;
			
			for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
				listaAnexoDetalleCuenta = cierreFacade.getListaAnexoDetalleCuentaPorAnexoDetalle(anexoDetalle);
				anexoDetalle.setListaAnexoDetalleCuenta(listaAnexoDetalleCuenta);
				
				listaAnexoDetalleOperador = cierreFacade.getListaAnexoDetalleOperadorPorAnexoDetalle(anexoDetalle);				
				if(listaAnexoDetalleOperador!=null && !listaAnexoDetalleOperador.isEmpty()){
					for(AnexoDetalleOperador anexoDetalleOperador : listaAnexoDetalleOperador){
						anexoDetalleOperador.setAnexoDetalleReferencia(
							buscarAnexoDetallePorItemAnexoDetalle(
								anexoDetalleOperador.getIntItemAnexoDetalleOperador()));
					}
				}
				anexoDetalle.setListaAnexoDetalleOperador(listaAnexoDetalleOperador);
				
			}
			iniciarOrdenarListaAnexoDetalle();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			anexoNuevo = new Anexo();
				
			listaAnexoDetalle = new ArrayList<AnexoDetalle>();
			AnexoDetalle anexoTemp = new AnexoDetalle();
			anexoTemp.setIntNivel(1);
			anexoTemp.setIntPosicion(1);
			anexoTemp.setIntItem(1);
			anexoTemp.setIntNivelPadre(0);
			anexoTemp.setIntPosicionPadre(0);
			anexoTemp.setIntItemPadre(0);
			anexoTemp.setStrNumeracion("1");
			anexoTemp.setIntTipoOperacion(Constante.PARAM_T_TIPOOPERACIONCONTABLE_SUMA);
			anexoTemp.setHabilitarConfigurar(Boolean.FALSE);
			
			listaAnexoDetalle.add(anexoTemp);
			
			habilitarGrabar = Boolean.TRUE;
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;			
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	private List<AnexoDetalle> buscarListaHijos(AnexoDetalle anexoDetallePadre){
		Integer intNivelPadre 	= anexoDetallePadre.getIntNivel();
		Integer intPosicionPadre= anexoDetallePadre.getIntPosicion();
		Integer intItemPadre 	= anexoDetallePadre.getIntItem();
		
		List <AnexoDetalle> listaHijos = new ArrayList<AnexoDetalle>();
		
		for(AnexoDetalle aTemp : listaAnexoDetalle){
			if(aTemp.getIntNivelPadre().equals(intNivelPadre) 
				&& aTemp.getIntPosicionPadre().equals(intPosicionPadre)
				&& aTemp.getIntItemPadre().equals(intItemPadre)){				
				listaHijos.add(aTemp);
			}
		}
		
		return listaHijos;
	}
	
	
	private AnexoDetalle buscarPadre(AnexoDetalle anexoDetalleHijo){
		Integer intNivelPadre 	= anexoDetalleHijo.getIntNivelPadre();
		Integer intPosicionPadre= anexoDetalleHijo.getIntPosicionPadre();
		Integer intItemPadre 	= anexoDetalleHijo.getIntItemPadre();
		
		for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
			if(anexoDetalle.getIntNivel().equals(intNivelPadre) 
				&& anexoDetalle.getIntPosicion().equals(intPosicionPadre)
				&& anexoDetalle.getIntItem().equals(intItemPadre)){				
				return anexoDetalle;
			}
		}		
		return null;
	}
	
	
	private List<AnexoDetalle> buscarListaHermanos(AnexoDetalle anexoDetalle){
		Integer intNivelPadre 	= anexoDetalle.getIntNivelPadre();
		Integer intPosicionPadre= anexoDetalle.getIntPosicionPadre();
		Integer intItemPadre 	= anexoDetalle.getIntItemPadre();
		
		List <AnexoDetalle> listaHermanos = new ArrayList<AnexoDetalle>();
		
		for(AnexoDetalle aTemp : listaAnexoDetalle){
			if(aTemp.getIntNivelPadre().equals(intNivelPadre) 
				&& aTemp.getIntPosicionPadre().equals(intPosicionPadre)
				&& aTemp.getIntItemPadre().equals(intItemPadre)){				
				listaHermanos.add(aTemp);
			}
		}		
		return listaHermanos;
	}
	
	private Integer buscarMayorPosicionHermanos(AnexoDetalle anexoTemp){
		List<AnexoDetalle> listaHermanos = buscarListaHermanos(anexoTemp);
		Integer intMayorPosicionHermano = new Integer(0);
		for(AnexoDetalle anexoT : listaHermanos){
			if(anexoT.getIntPosicion().compareTo(intMayorPosicionHermano)>0){
				intMayorPosicionHermano = anexoT.getIntPosicion();
			}
		}
		return intMayorPosicionHermano;
	}
	
	private void asignarItem(AnexoDetalle anexoTemp){
		Integer intItemMayor = new Integer(0); 
		for(AnexoDetalle aTemp : listaAnexoDetalle){
			if(aTemp.getIntNivel().equals(anexoTemp.getIntNivel()) 
				&& aTemp.getIntPosicion().equals(anexoTemp.getIntPosicion())){
				if(aTemp.getIntItem().compareTo(intItemMayor)>0){
					intItemMayor = aTemp.getIntItem();
				}
			}
		}
		intItemMayor = intItemMayor + 1;
		anexoTemp.setIntItem(intItemMayor);
	}
	
	public void addAnexoDetalleHijo(ActionEvent event){
		try{
			AnexoDetalle anexoDetallePadre = (AnexoDetalle)event.getComponent().getAttributes().get("item");
			if(anexoDetallePadre.getListaAnexoDetalleOperador()!=null && !anexoDetallePadre.getListaAnexoDetalleOperador().isEmpty()){
				mostrarMensaje(Boolean.FALSE, "Al elemento '"+anexoDetallePadre.getStrTexto()+"' no se le puede agregar " +
						"subelementos porque tiene una configuración asociada." );
				return;
			}else{
				ocultarMensaje();
			}
			AnexoDetalle anexoDetalleHijo = new AnexoDetalle();
			anexoDetalleHijo.setIntPosicionPadre(anexoDetallePadre.getIntPosicion());
			anexoDetalleHijo.setIntNivelPadre(anexoDetallePadre.getIntNivel());
			anexoDetalleHijo.setIntItemPadre(anexoDetallePadre.getIntItem());
			anexoDetalleHijo.setIntNivel(anexoDetallePadre.getIntNivel()+1);
			anexoDetalleHijo.setIntTipoOperacion(Constante.PARAM_T_TIPOOPERACIONCONTABLE_SUMA);
			
			Integer intMayorPosicionHermano = buscarMayorPosicionHermanos(anexoDetalleHijo);
			
			anexoDetalleHijo.setIntPosicion(intMayorPosicionHermano+1);
			asignarItem(anexoDetalleHijo);
			listaAnexoDetalle.add(anexoDetalleHijo);
			
			iniciarOrdenarListaAnexoDetalle();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void addAnexoDetalleHermano(ActionEvent event){
		try{
			AnexoDetalle anexoTemp = (AnexoDetalle)event.getComponent().getAttributes().get("item");
			AnexoDetalle anexoTempHermano = new AnexoDetalle();
			anexoTempHermano.setIntPosicionPadre(anexoTemp.getIntPosicionPadre());
			anexoTempHermano.setIntNivelPadre(anexoTemp.getIntNivelPadre());
			anexoTempHermano.setIntItemPadre(anexoTemp.getIntItemPadre());
			anexoTempHermano.setIntNivel(anexoTemp.getIntNivel());
			anexoTempHermano.setIntTipoOperacion(Constante.PARAM_T_TIPOOPERACIONCONTABLE_SUMA);
			
			Integer intMayorPosicionHermano = buscarMayorPosicionHermanos(anexoTempHermano);
			
			anexoTempHermano.setIntPosicion(intMayorPosicionHermano+1);
			asignarItem(anexoTempHermano);
			listaAnexoDetalle.add(anexoTempHermano);
			
			iniciarOrdenarListaAnexoDetalle();
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean poseeHijos(AnexoDetalle anexoDetalle){
		List<AnexoDetalle> listaHijos = buscarListaHijos(anexoDetalle);
		if(!listaHijos.isEmpty()){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public void iniciarOrdenarListaAnexoDetalle(){
		try{
			intOrdenCorrelativo = new Integer(0);
			
			AnexoDetalle anexoTemp = new AnexoDetalle();
			anexoTemp.setIntNivel(1);
			anexoTemp.setIntPosicion(1);
			anexoTemp.setIntItem(1);
			anexoTemp.setIntNivelPadre(0);
			anexoTemp.setIntPosicionPadre(0);
			anexoTemp.setIntItemPadre(0);
			
			for(AnexoDetalle aTemp : listaAnexoDetalle){
				aTemp.setRevisado(Boolean.FALSE);
			}
			log.info("--iniciarOrdenarAnexoTemp");
			ordenarAnexoDetalle(anexoTemp);
			log.info("--finOrdenarAnexoTemp");
			revisarHabilitaciones();

			//ordenamos por intOrden (sort por defecto)
			Collections.sort(listaAnexoDetalle);
			
			/*for(AnexoTemp aTemp : listaAnexoTemp){
				log.info(aTemp);
			}*/
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void ordenarAnexoDetalle(AnexoDetalle anexoDetalle) throws Exception{
		try{
			//log.info("---------"+anexoTemp);
			List<AnexoDetalle> listaHermanos = buscarListaHermanos(anexoDetalle);
			//Ordenamos por POSICION
			Collections.sort(listaHermanos, new Comparator<AnexoDetalle>(){
				public int compare(AnexoDetalle uno, AnexoDetalle otro) {
					return uno.getIntPosicion().compareTo(otro.getIntPosicion());
				}
			});
			
			List<AnexoDetalle> listaHijos;
			/*for(AnexoTemp hermano : listaHermanos){
				log.info("--hermanos:"+hermano);
			}*/
			for(AnexoDetalle hermano : listaHermanos){
				if(!hermano.isRevisado()){
					hermano.setRevisado(Boolean.TRUE);
					listaHijos = buscarListaHijos(hermano);
					aplicarNumeracion(hermano);
					//si no posee hijos
					if(listaHijos.isEmpty()){
						intOrdenCorrelativo = intOrdenCorrelativo +1;
						hermano.setIntOrden(intOrdenCorrelativo);
						//log.info("*hermano:"+hermano);
					}else{
						intOrdenCorrelativo = intOrdenCorrelativo +1;
						hermano.setIntOrden(intOrdenCorrelativo);
						//log.info("-hermano:"+hermano);
						ordenarAnexoDetalle(listaHijos.get(0));
					}
				}				
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	
	public void iniciarEliminarAnexoDetalle(ActionEvent event){
		try{
			AnexoDetalle anexoTemp = (AnexoDetalle)event.getComponent().getAttributes().get("item");			
			//Si es el unico elemento padre (raiz) restante, no se podra eliminar
			if(anexoTemp.getIntNivel().equals(new Integer(1)) && anexoTemp.getIntPosicion().equals(new Integer(1))){
				List<AnexoDetalle> listaHermanos = buscarListaHermanos(anexoTemp);
				if(listaHermanos.size()==1){
					return;
				}
			}
			eliminarAnexoDetalle(anexoTemp);			
			iniciarOrdenarListaAnexoDetalle();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void actualizarAnexoDetalleOperador(AnexoDetalle anexoDetalleCambia, Integer intNuevaPosicion){
		AnexoDetalle anexoDetRef;
		for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
			if(anexoDetalle.getListaAnexoDetalleOperador()!=null && !anexoDetalle.getListaAnexoDetalleOperador().isEmpty()){
				for(AnexoDetalleOperador anexoDetalleOperador : anexoDetalle.getListaAnexoDetalleOperador()){
					anexoDetRef = anexoDetalleOperador.getAnexoDetalleReferencia();
					if(anexoDetRef.getIntNivel().equals(anexoDetalleCambia.getIntNivel()) 
						&& anexoDetRef.getIntPosicion().equals(anexoDetalleCambia.getIntPosicion())
						&& anexoDetRef.getIntItem().equals(anexoDetalleCambia.getIntItem())){
						anexoDetRef.setIntPosicion(intNuevaPosicion);
					}
				}
			}
		}
	}
	
	
	private void eliminarAnexoDetalle(AnexoDetalle anexoTempPadre){
		List<AnexoDetalle> listaHijos = buscarListaHijos(anexoTempPadre);
		if(listaHijos.isEmpty()){
			subirOrdenSiguiente(anexoTempPadre);
			listaAnexoDetalle.remove(anexoTempPadre);
		}else{
			for(AnexoDetalle hijo : listaHijos){
				eliminarAnexoDetalle(hijo);
			}
			subirOrdenSiguiente(anexoTempPadre);
			listaAnexoDetalle.remove(anexoTempPadre);
		}
	}
	
	
	private void subirOrdenSiguiente(AnexoDetalle anexoTemp){
		try{
			log.info("anexoDetalle:"+anexoTemp);
			Integer intUltimaPosicion = obtenerUltimaPosicionHermano(anexoTemp);
			if(anexoTemp.getIntPosicion().equals(intUltimaPosicion)){
				return;
			}
			
			AnexoDetalle anexoDetalleSiguiente = obtenerSiguienteAnexoTemp(anexoTemp);
			if(anexoDetalleSiguiente==null){
				return;
			}
			List<AnexoDetalle> listaHijosActualSiguiente = buscarListaHijos(anexoDetalleSiguiente);
			Integer intOrdenNuevo = anexoDetalleSiguiente.getIntPosicion() - 1;
			
			int indice;
			for(AnexoDetalle hijo : listaHijosActualSiguiente){
				//listaAnexoDetalle.remove(hijo);
				indice = listaAnexoDetalle.indexOf(hijo);
				hijo.setIntPosicionPadre(intOrdenNuevo);
				listaAnexoDetalle.set(indice, hijo);
			}
			indice = listaAnexoDetalle.indexOf(anexoDetalleSiguiente);
			
			actualizarAnexoDetalleOperador(anexoDetalleSiguiente,intOrdenNuevo);
			anexoDetalleSiguiente.setIntPosicion(intOrdenNuevo);
			listaAnexoDetalle.set(indice, anexoDetalleSiguiente);
			
			//iniciarOrdenarAnexoTemp();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void subirOrden(ActionEvent event){
		try{
			AnexoDetalle anexoTemp = (AnexoDetalle)event.getComponent().getAttributes().get("item");			
			log.info("anexoTemp:"+anexoTemp);
			if(anexoTemp.getIntPosicion().equals(new Integer(1))){
				return;
			}
			//subirOrden(event, listaAnexoDetalle, anexoTemp);
			Integer intOrdenNuevo = anexoTemp.getIntPosicion() - 1;
			AnexoDetalle anexoTempAnterior = obtenerAnteriorAnexoTemp(anexoTemp);
			log.info("anexoTempAnterior:"+anexoTempAnterior);
			List<AnexoDetalle> listaHijosActual = buscarListaHijos(anexoTemp);
			List<AnexoDetalle> listaHijosAnterior = buscarListaHijos(anexoTempAnterior);
			
			for(AnexoDetalle hijo : listaHijosActual){
				listaAnexoDetalle.remove(hijo);
				hijo.setIntPosicionPadre(intOrdenNuevo);
				listaAnexoDetalle.add(hijo);
			}
			for(AnexoDetalle hijo : listaHijosAnterior){
				listaAnexoDetalle.remove(hijo);
				hijo.setIntPosicionPadre(intOrdenNuevo+1);
				listaAnexoDetalle.add(hijo);
			}
			listaAnexoDetalle.remove(anexoTemp);
			listaAnexoDetalle.remove(anexoTempAnterior);
			
			actualizarAnexoDetalleOperador(anexoTemp, intOrdenNuevo);
			actualizarAnexoDetalleOperador(anexoTempAnterior, intOrdenNuevo+1);
			
			anexoTemp.setIntPosicion(intOrdenNuevo);
			anexoTempAnterior.setIntPosicion(intOrdenNuevo+1);
			listaAnexoDetalle.add(anexoTemp);
			listaAnexoDetalle.add(anexoTempAnterior);
			
			iniciarOrdenarListaAnexoDetalle();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private Integer obtenerUltimaPosicionHermano(AnexoDetalle anexoTemp){
		List<AnexoDetalle> listaHermanos = buscarListaHermanos(anexoTemp);
		Collections.sort(listaHermanos, new Comparator<AnexoDetalle>(){
			public int compare(AnexoDetalle uno, AnexoDetalle otro) {
				return uno.getIntPosicion().compareTo(otro.getIntPosicion());
			}
		});
		AnexoDetalle ultimoHermano = listaHermanos.get(listaHermanos.size()-1);
		return ultimoHermano.getIntPosicion();
	}
	
	public void bajarOrden(ActionEvent event){
		try{
			AnexoDetalle anexoTemp = (AnexoDetalle)event.getComponent().getAttributes().get("item");			
			log.info("anexoTemp:"+anexoTemp);
			Integer intUltimaPosicion = obtenerUltimaPosicionHermano(anexoTemp);
			if(anexoTemp.getIntPosicion().equals(intUltimaPosicion)){
				return;
			}
			//bajarOrden(event, listaAnexoDetalle, anexoTemp);
			Integer intOrdenNuevo = anexoTemp.getIntPosicion() + 1;
			AnexoDetalle anexoTempSiguiente = obtenerSiguienteAnexoTemp(anexoTemp);
			log.info("anexoTempSiguiente:"+anexoTempSiguiente);
			List<AnexoDetalle> listaHijosActual = buscarListaHijos(anexoTemp);
			List<AnexoDetalle> listaHijosSiguiente = buscarListaHijos(anexoTempSiguiente);
			
			for(AnexoDetalle hijo : listaHijosActual){
				listaAnexoDetalle.remove(hijo);
				hijo.setIntPosicionPadre(intOrdenNuevo);
				listaAnexoDetalle.add(hijo);
			}
			for(AnexoDetalle hijo : listaHijosSiguiente){
				listaAnexoDetalle.remove(hijo);
				hijo.setIntPosicionPadre(intOrdenNuevo-1);
				listaAnexoDetalle.add(hijo);
			}
			listaAnexoDetalle.remove(anexoTemp);
			listaAnexoDetalle.remove(anexoTempSiguiente);			

			actualizarAnexoDetalleOperador(anexoTemp, intOrdenNuevo);
			actualizarAnexoDetalleOperador(anexoTempSiguiente, intOrdenNuevo+1);
			
			anexoTemp.setIntPosicion(intOrdenNuevo);
			anexoTempSiguiente.setIntPosicion(intOrdenNuevo-1);
			listaAnexoDetalle.add(anexoTemp);
			listaAnexoDetalle.add(anexoTempSiguiente);
			
			iniciarOrdenarListaAnexoDetalle();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private AnexoDetalle obtenerSiguienteAnexoTemp(AnexoDetalle anexoDetalle){
		Integer intPosicionAnterior = anexoDetalle.getIntPosicion()+1;
		for(AnexoDetalle aTemp : listaAnexoDetalle){
			if(aTemp.getIntNivel().equals(anexoDetalle.getIntNivel()) 
				&& aTemp.getIntPosicion().equals(intPosicionAnterior)
				&& aTemp.getIntNivelPadre().equals(anexoDetalle.getIntNivelPadre()) 
				&& aTemp.getIntPosicionPadre().equals(anexoDetalle.getIntPosicionPadre())
				&& aTemp.getIntItemPadre().equals(anexoDetalle.getIntItemPadre())){
				return aTemp;
			}
		}
		return null;
	}
	
	private AnexoDetalle obtenerAnteriorAnexoTemp(AnexoDetalle anexoDetalle){
		Integer intPosicionAnterior = anexoDetalle.getIntPosicion()-1;
		for(AnexoDetalle aTemp : listaAnexoDetalle){
			if(aTemp.getIntNivel().equals(anexoDetalle.getIntNivel()) 
				&& aTemp.getIntPosicion().equals(intPosicionAnterior)
				&& aTemp.getIntNivelPadre().equals(anexoDetalle.getIntNivelPadre()) 
				&& aTemp.getIntPosicionPadre().equals(anexoDetalle.getIntPosicionPadre())
				&& aTemp.getIntItemPadre().equals(anexoDetalle.getIntItemPadre())){
				return aTemp;
			}
		}
		return null;
	}
	
	private void aplicarNumeracion(AnexoDetalle anexoDetalle){
		if(anexoDetalle.getIntNivelPadre().equals(new Integer(0))){
			anexoDetalle.setStrNumeracion(anexoDetalle.getIntPosicion()+"");
		}else{
			AnexoDetalle anexoDetallePadre = buscarPadre(anexoDetalle);
			String numeracion = "";
			for(int i=0;i<anexoDetalle.getIntNivel();i++){
				numeracion = numeracion + " ";
			}
			numeracion = numeracion + anexoDetallePadre.getStrNumeracion()+"."+anexoDetalle.getIntPosicion();
			anexoDetalle.setStrNumeracion(numeracion);
		}
	}
	
	public void aceptarConfigurar(){
		try{
			log.info("--aceptarConfigurar");
			anexoDetalleSeleccionado.setListaAnexoDetalleOperador(listaAnexoDetalleOperador);
			
			AnexoDetalle anexoDet = buscarAnexoDetalle(anexoDetalleSeleccionado.getIntNivel(), 
				anexoDetalleSeleccionado.getIntPosicion(), anexoDetalleSeleccionado.getIntItem());
			
			Integer intIndice = listaAnexoDetalle.indexOf(anexoDet);
			listaAnexoDetalle.set(intIndice, anexoDetalleSeleccionado);			
			
			revisarHabilitaciones();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void cancelarConfigurar(){
		try{		
			log.info("--cancelarConfigurar");
			for(AnexoDetalleOperador aDO : listaAnexoDetalleOperadorPersiste){
				log.info(aDO);
			}
			anexoDetalleSeleccionado.setListaAnexoDetalleOperador(listaAnexoDetalleOperadorPersiste);
			
			AnexoDetalle anexoDet = buscarAnexoDetalle(anexoDetalleSeleccionado.getIntNivel(), 
					anexoDetalleSeleccionado.getIntPosicion(), anexoDetalleSeleccionado.getIntItem());
				
			Integer intIndice = listaAnexoDetalle.indexOf(anexoDet);
			listaAnexoDetalle.set(intIndice, anexoDetalleSeleccionado);			
			
			revisarHabilitaciones();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean validarDuplicidadDetalleOperador(AnexoDetalle anexoDetalle){
		for(AnexoDetalleOperador anexoDetalleOperador : anexoDetalleSeleccionado.getListaAnexoDetalleOperador()){
			if(anexoDetalleOperador.getAnexoDetalleReferencia().getIntNivel().equals(anexoDetalle.getIntNivel()) 
				&& anexoDetalleOperador.getAnexoDetalleReferencia().getIntPosicion().equals(anexoDetalle.getIntPosicion())
				&& anexoDetalleOperador.getAnexoDetalleReferencia().getIntItem().equals(anexoDetalle.getIntItem())){
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	public void agregarAnexoDetalleOperacion(ActionEvent event){
		try{
			AnexoDetalle anexoDetalle = (AnexoDetalle)event.getComponent().getAttributes().get("item");			
			if(anexoDetalle.getIntNivel().equals(anexoDetalleSeleccionado.getIntNivel()) 
				&& anexoDetalle.getIntPosicion().equals(anexoDetalleSeleccionado.getIntPosicion())
				&& anexoDetalle.getIntItem().equals(anexoDetalleSeleccionado.getIntItem())){				
				return;
			}
			if(!validarDuplicidadDetalleOperador(anexoDetalle)){
				return;
			}
			
			AnexoDetalleOperador anexoDetalleOperador = new AnexoDetalleOperador();			
			anexoDetalleOperador.setAnexoDetalleReferencia(anexoDetalle);
			anexoDetalleOperador.setIntTipoOperacion(Constante.PARAM_T_TIPOOPERACIONCONTABLE_SUMA);
			
			//int posicionSeleccionado = listaAnexoDetalle.indexOf(anexoDetalleSeleccionado);
			//log.info("posicionSeleccionado:"+posicionSeleccionado);
			//anexoDetalleSeleccionado.getListaAnexoDetalleOperador().add(anexoDetalleOperador);
			
			listaAnexoDetalleOperador.add(anexoDetalleOperador);
			
			Collections.sort(listaAnexoDetalleOperador, new Comparator<AnexoDetalleOperador>(){
				public int compare(AnexoDetalleOperador uno, AnexoDetalleOperador otro) {
					return uno.getAnexoDetalleReferencia().getIntPosicion().compareTo(otro.getAnexoDetalleReferencia().getIntPosicion());
				}
			});
			
			//listaAnexoDetalle.set(posicionSeleccionado, anexoDetalleSeleccionado);
			
			//revisarHabilitaciones();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void revisarHabilitaciones(){
		for(AnexoDetalle aTemp : listaAnexoDetalle){
			aTemp.setHabilitarVerCuentas(Boolean.FALSE);
			aTemp.setHabilitarConfigurar(Boolean.FALSE);
			if(!poseeHijos(aTemp)){
				if(aTemp.getListaAnexoDetalleOperador()==null || aTemp.getListaAnexoDetalleOperador().isEmpty()){
					aTemp.setHabilitarVerCuentas(Boolean.TRUE);
				}					
				if(aTemp.getListaAnexoDetalleCuenta()==null || aTemp.getListaAnexoDetalleCuenta().isEmpty()){
					aTemp.setHabilitarConfigurar(Boolean.TRUE);
				}
			}
		}
	}
	
	public void desagregarAnexoDetalleOperacion(ActionEvent event){
		try{
			AnexoDetalleOperador anexoDetalleOperador = (AnexoDetalleOperador)event.getComponent().getAttributes().get("item");			
			
			//int posicionSeleccionado = listaAnexoDetalle.indexOf(anexoDetalleSeleccionado);
			//log.info("posicionSeleccionado:"+posicionSeleccionado);
			//anexoDetalleSeleccionado.getListaAnexoDetalleOperador().remove(anexoDetalleOperador);
			//listaAnexoDetalle.set(posicionSeleccionado, anexoDetalleSeleccionado);
			
			listaAnexoDetalleOperador.remove(anexoDetalleOperador);
			
			//revisarHabilitaciones();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpConfiguracion(ActionEvent event){
		try{
			anexoDetalleSeleccionado = (AnexoDetalle)event.getComponent().getAttributes().get("item");
			
			listaAnexoDetalleOperador = anexoDetalleSeleccionado.getListaAnexoDetalleOperador();
			
			listaAnexoDetalleOperadorPersiste = new ArrayList<AnexoDetalleOperador>();
			for(AnexoDetalleOperador aDO : anexoDetalleSeleccionado.getListaAnexoDetalleOperador()){
				listaAnexoDetalleOperadorPersiste.add(aDO);
			}
			
			Collections.sort(listaAnexoDetalleOperador, new Comparator<AnexoDetalleOperador>(){
				public int compare(AnexoDetalleOperador uno, AnexoDetalleOperador otro) {
					return uno.getAnexoDetalleReferencia().getIntPosicion().compareTo(otro.getAnexoDetalleReferencia().getIntPosicion());
				}
			});
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpVerCuentas(ActionEvent event){
		try{
			anexoDetalleSeleccionado = (AnexoDetalle)event.getComponent().getAttributes().get("item");
			if(anexoDetalleSeleccionado.getListaAnexoDetalleCuenta()!=null){
				PlanCuentaId planCuentaId;
				for(AnexoDetalleCuenta anexoDetalleCuenta : anexoDetalleSeleccionado.getListaAnexoDetalleCuenta()){
					planCuentaId = new PlanCuentaId();
					planCuentaId.setIntEmpresaCuentaPk(anexoDetalleCuenta.getId().getIntPersEmpresaCuenta());
					planCuentaId.setIntPeriodoCuenta(anexoDetalleCuenta.getId().getIntContPeriodoCuenta());
					planCuentaId.setStrNumeroCuenta(anexoDetalleCuenta.getId().getStrContNumeroCuenta());
					anexoDetalleCuenta.setPlanCuenta(planCuentaFacade.getPlanCuentaPorPk(planCuentaId));
				}
			}
			
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
		ocultarMensaje();
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}


	public List getListaAnexo() {
		return listaAnexo;
	}
	public void setListaAnexo(List listaAnexo) {
		this.listaAnexo = listaAnexo;
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
	public List getListaAnios() {
		return listaAnios;
	}
	public void setListaAnios(List listaAnios) {
		this.listaAnios = listaAnios;
	}
	public Anexo getAnexoNuevo() {
		return anexoNuevo;
	}
	public void setAnexoNuevo(Anexo anexoNuevo) {
		this.anexoNuevo = anexoNuevo;
	}
	public List<AnexoDetalle> getListaAnexoDetalle() {
		return listaAnexoDetalle;
	}
	public void setListaAnexoDetalle(List<AnexoDetalle> listaAnexoDetalle) {
		this.listaAnexoDetalle = listaAnexoDetalle;
	}
	public AnexoDetalle getAnexoDetalleSeleccionado() {
		return anexoDetalleSeleccionado;
	}
	public void setAnexoDetalleSeleccionado(AnexoDetalle anexoDetalleSeleccionado) {
		this.anexoDetalleSeleccionado = anexoDetalleSeleccionado;
	}
	public Anexo getAnexoFiltro() {
		return anexoFiltro;
	}
	public void setAnexoFiltro(Anexo anexoFiltro) {
		this.anexoFiltro = anexoFiltro;
	}
	public List<AnexoDetalleOperador> getListaAnexoDetalleOperador() {
		return listaAnexoDetalleOperador;
	}
	public void setListaAnexoDetalleOperador(
			List<AnexoDetalleOperador> listaAnexoDetalleOperador) {
		this.listaAnexoDetalleOperador = listaAnexoDetalleOperador;
	}
	public List getListaTipoAnexo() {
		return listaTipoAnexo;
	}
	public void setListaTipoAnexo(List listaTipoAnexo) {
		this.listaTipoAnexo = listaTipoAnexo;
	}
}