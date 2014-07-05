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
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleOperador;
import pe.com.tumi.contabilidad.cierre.domain.AnexoId;
import pe.com.tumi.contabilidad.cierre.domain.Ratio;
import pe.com.tumi.contabilidad.cierre.domain.RatioDetalle;
import pe.com.tumi.contabilidad.cierre.facade.CierreFacadeLocal;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class RatioController {

	protected static Logger log = Logger.getLogger(RatioController.class);	
	
	CierreFacadeLocal cierreFacade;
	TablaFacadeRemote tablaFacade;
	PlanCuentaFacadeLocal planCuentaFacade;
	
	private List listaRatio;
	private List listaAnios;
	private List listaRatioDetalleSuperior;
	private List listaRatioDetalleInferior;
	private List<AnexoDetalle> listaAnexoDetallePopUp;
	private List listaPeriodicidad;
	private List listaMoneda;
	private List<AnexoDetalle> listaAnexoDetalle;
	private List<AnexoDetalleOperador> listaAnexoDetalleOperador;
	private List<AnexoDetalleOperador> listaAnexoDetalleOperadorPersiste;
	private List listaPlanCuenta;
	private List listaTipoAnexo;
	private List listaTipoAnexoPopUp;
	private List listaAnexo;
	
	private	Ratio ratioNuevo;
	private	Anexo anexoNuevo;
	private	Ratio ratioFiltro;
	private	Anexo anexoFiltro;
	private	Ratio registroSeleccionado;
	private	Anexo registroSeleccionadoAnexo;
	private RatioDetalle 	ratioDetalleSeleccionado;
	private	PlanCuenta		planCuentaFiltro;
	private AnexoDetalle	anexoDetalleSeleccionado;
	
	private Usuario usuario;
	private String 	mensajeOperacion;
	private final int cantidadAñosLista = 4;
	private final int cantidadOperadoresRatio = 3;
	private Integer	intParaOperacionPrincipal;
	private Integer intIndicador;
	private	Integer	intTipoEstadoFinanciero;
	private	String	strTextoFiltrar;
	private	Integer intNumeroOperando;
	private Integer intCorrelativoOrdenSuperior;
	private Integer intCorrelativoOrdenInferior;
	private Integer	intIndicadorFiltro;
	private Integer intOrdenCorrelativo;
	private	Integer	intTipoBusquedaPlanCuenta;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean habilitarCabeceraNuevo;
	private boolean habilitarTextoPlanCuenta;
	
	
	public RatioController(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}	
	
	
	public void cargarValoresIniciales(){
		try{
			ratioFiltro = new Ratio();
			anexoFiltro = new Anexo();
			//Added by cdelosrios on 13/09/2013
			anexoNuevo = new Anexo();
			anexoNuevo.setId(new AnexoId());
			//End added by cdelosrios on 13/09/2013
			
			listaRatio = new ArrayList<Ratio>();
			listaAnexo = new ArrayList<Anexo>();
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			listaPlanCuenta = new ArrayList<PlanCuenta>();
			listaAnexoDetallePopUp = new ArrayList<AnexoDetalle>();	
			intTipoEstadoFinanciero = new Integer(0);
			intIndicadorFiltro = Constante.PARAM_T_GRUPORATIOANEXO_RATIO;
			
			cierreFacade = (CierreFacadeLocal) EJBFactory.getLocal(CierreFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
			
			listaPeriodicidad = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_FRECUENCPAGOINT), "C");
			listaMoneda = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA), "A");			
			listaTipoAnexo  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSFINANCIEROS), "A");
			listaTipoAnexoPopUp  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSFINANCIEROS), "B");
			
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
	
	private void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
	public void buscarPlanCuenta(){
		try{
			if(planCuentaFiltro.getId().getIntPeriodoCuenta().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
				planCuentaFiltro.getId().setIntPeriodoCuenta(null);
			}
			if(intTipoBusquedaPlanCuenta==0){
				
			}else if(intTipoBusquedaPlanCuenta.equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION)){
				log.info("Tipo:Descripcion");
				planCuentaFiltro.getId().setStrNumeroCuenta(null);
				planCuentaFiltro.setStrDescripcion(planCuentaFiltro.getStrComentario());
				listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);
			}else if(intTipoBusquedaPlanCuenta.equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_CUENTACONTABLE)){
				log.info("Tipo:NumeroCuenta");
				planCuentaFiltro.getId().setStrNumeroCuenta(planCuentaFiltro.getStrComentario());
				planCuentaFiltro.setStrDescripcion(null);
				listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);
			}
			/*for(Object o : listaPlanCuenta){
				log.info((PlanCuenta)o);
			}*/
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarPlanCuenta(ActionEvent event){
		try{
			PlanCuenta planCuenta = (PlanCuenta)event.getComponent().getAttributes().get("item");
			anexoDetalleSeleccionado.setStrTexto(planCuenta.getStrDescripcion());
			AnexoDetalleCuenta anexoDetalleCuenta = new AnexoDetalleCuenta();
			anexoDetalleCuenta.getId().setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
			anexoDetalleCuenta.getId().setIntContPeriodoCuenta(planCuenta.getId().getIntPeriodoCuenta());
			anexoDetalleCuenta.getId().setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());
			if(anexoDetalleSeleccionado.getListaAnexoDetalleCuenta().isEmpty()){
				anexoDetalleSeleccionado.getListaAnexoDetalleCuenta().add(anexoDetalleCuenta);
			}else{
				anexoDetalleSeleccionado.getListaAnexoDetalleCuenta().set(0,anexoDetalleCuenta);
			}			
			revisarHabilitaciones();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void deseleccionarPlanCuenta(){
		try{
			anexoDetalleSeleccionado.setStrTexto(null);
			anexoDetalleSeleccionado.getListaAnexoDetalleCuenta().clear();
			revisarHabilitaciones();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarTipoBusquedaPlanCuenta(){
		if(intTipoBusquedaPlanCuenta!=0){
			habilitarTextoPlanCuenta = Boolean.TRUE;
		}else{
			habilitarTextoPlanCuenta = Boolean.FALSE;
		}
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			ratioNuevo = new Ratio();
			habilitarGrabar = Boolean.FALSE;
			habilitarCabeceraNuevo = Boolean.TRUE;
			intIndicador = new Integer(0);
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
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
	
	
	public void buscar(){
		try{
			log.info("intIndicadorFiltro:"+intIndicadorFiltro);
			if(intIndicadorFiltro.equals(Constante.PARAM_T_GRUPORATIOANEXO_RATIO)){
				listaRatio = cierreFacade.buscarRatio(ratioFiltro);
				
			}else if(intIndicadorFiltro.equals(Constante.PARAM_T_GRUPORATIOANEXO_ANEXO)){
				anexoFiltro.getId().setIntPersEmpresaAnexo(usuario.getPerfil().getId().getIntPersEmpresaPk());
				anexoFiltro.getId().setIntContPeriodoAnexo(ratioFiltro.getId().getIntCodigoRatio());
				anexoFiltro.setIntParaTipoLibroAnexo(Constante.TIPO_ANEXO);
				listaAnexo = cierreFacade.buscarAnexo(anexoFiltro);
				
			}else{
				return;
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	

	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (Ratio)event.getComponent().getAttributes().get("item");
			if(registroSeleccionado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				cargarRegistroRatio();
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
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistroAnexo(ActionEvent event){
		try{
			registroSeleccionadoAnexo = (Anexo)event.getComponent().getAttributes().get("item");
			mostrarBtnEliminar = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;
			log.info("reg selec:"+registroSeleccionadoAnexo);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarRegistroRatio() throws BusinessException{
		log.info("cargarRegistroRatio");
		intIndicador = Constante.PARAM_T_GRUPORATIOANEXO_RATIO;	
		ratioNuevo = registroSeleccionado;
		List<RatioDetalle> listaRatioDetalle = cierreFacade.getListaRatioDetallePorRatio(ratioNuevo);
		listaRatioDetalleSuperior = new ArrayList<RatioDetalle>();
		listaRatioDetalleInferior = new ArrayList<RatioDetalle>();
		
		intCorrelativoOrdenSuperior = new Integer(0);
		intCorrelativoOrdenInferior = new Integer(0);
		
		AnexoDetalleId anexoDetalleId;
		for(RatioDetalle ratioDetalle : listaRatioDetalle){
			if(ratioDetalle.getIntAndeItemAnexoDetalle()!=null){
				anexoDetalleId = new AnexoDetalleId();
				anexoDetalleId.setIntPersEmpresaAnexo(ratioDetalle.getIntPersEmpresaAnexo());
				anexoDetalleId.setIntContPeriodoAnexo(ratioDetalle.getIntContPeriodoAnexo());
				anexoDetalleId.setIntParaTipoAnexo(ratioDetalle.getIntParaTipoAnexo());
				anexoDetalleId.setIntItemAnexoDetalle(ratioDetalle.getIntAndeItemAnexoDetalle());
				ratioDetalle.setOperando1(cierreFacade.getAnexoDetallePorPK(anexoDetalleId));
			}
			if(ratioDetalle.getIntAndeItemAnexoDetalle2()!=null){
				anexoDetalleId = new AnexoDetalleId();
				anexoDetalleId.setIntPersEmpresaAnexo(ratioDetalle.getIntPersEmpresaAnexo2());
				anexoDetalleId.setIntContPeriodoAnexo(ratioDetalle.getIntContPeriodoAnexo2());
				anexoDetalleId.setIntParaTipoAnexo(ratioDetalle.getIntParaTipoAnexo2());
				anexoDetalleId.setIntItemAnexoDetalle(ratioDetalle.getIntAndeItemAnexoDetalle2());
				ratioDetalle.setOperando2(cierreFacade.getAnexoDetallePorPK(anexoDetalleId));
			}
			if(ratioDetalle.getIntParte().equals(Constante.PARTE_SUPERIOR)){
				listaRatioDetalleSuperior.add(ratioDetalle);
			}else if(ratioDetalle.getIntParte().equals(Constante.PARTE_INFERIOR)){
				listaRatioDetalleInferior.add(ratioDetalle);
			}
		}
		
		//Actualiza intCorrelativoOrdenSuperior e intCorrelativoOrdenInferior
		List <RatioDetalle> listaRatioDetalleSuperiorTemp = listaRatioDetalleSuperior;
		Collections.sort(listaRatioDetalleSuperiorTemp);
		RatioDetalle ratioDetalle = listaRatioDetalleSuperiorTemp.get(listaRatioDetalleSuperiorTemp.size()-1);
		intCorrelativoOrdenSuperior = ratioDetalle.getIntParteGrupo();
		listaRatioDetalleSuperior = listaRatioDetalleSuperiorTemp;
		
		List <RatioDetalle> listaRatioDetalleInferiorTemp = listaRatioDetalleInferior;
		Collections.sort(listaRatioDetalleInferiorTemp);
		ratioDetalle = listaRatioDetalleInferiorTemp.get(listaRatioDetalleInferiorTemp.size()-1);
		intCorrelativoOrdenInferior = ratioDetalle.getIntParteGrupo();
		listaRatioDetalleInferior = listaRatioDetalleInferiorTemp;
	}
	
	private void cargarRegistroAnexo() throws BusinessException{
		log.info("cargarRegistroAnexo");
		intIndicador = Constante.PARAM_T_GRUPORATIOANEXO_ANEXO;	
		ratioNuevo = new Ratio();
		anexoNuevo = registroSeleccionadoAnexo;
		ratioNuevo.getId().setIntContPeriodoRatio(anexoNuevo.getId().getIntContPeriodoAnexo());
		
		listaAnexoDetalle = cierreFacade.getListaAnexoDetallePorAnexo(anexoNuevo);
		List<AnexoDetalleOperador> listaAnexoDetalleOperador;
		List<AnexoDetalleCuenta> listaAnexoDetalleCuenta;
		
		for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
			log.info(anexoDetalle);
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
			
			if(anexoDetalle.getIntAndeItemAnexoDetalleRef()!=null){
				AnexoDetalleId anexoDetalleId = new AnexoDetalleId();
				anexoDetalleId.setIntPersEmpresaAnexo(anexoDetalle.getIntPersEmpresaAnexoRef());
				anexoDetalleId.setIntContPeriodoAnexo(anexoDetalle.getIntContPeriodoAnexoRef());
				anexoDetalleId.setIntParaTipoAnexo(anexoDetalle.getIntParaTipoAnexoRef());
				anexoDetalleId.setIntItemAnexoDetalle(anexoDetalle.getIntAndeItemAnexoDetalleRef());
				anexoDetalle.setAnexoDetalleReferencia(cierreFacade.getAnexoDetallePorPK(anexoDetalleId));
			}
			
			anexoDetalle.setListaAnexoDetalleOperador(listaAnexoDetalleOperador);
			
		}
		iniciarOrdenarListaAnexoDetalle();
	}

	private AnexoDetalle buscarAnexoDetallePorItemAnexoDetalle(Integer intAnexoDetalle){
		for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
			if(anexoDetalle.getId().getIntItemAnexoDetalle().equals(intAnexoDetalle)){
				return anexoDetalle;
			}
		}
		return null;
	}
	
	public void grabar(){
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try{
			
			//Inicio validaciones
			if(intIndicador.equals(Constante.PARAM_T_GRUPORATIOANEXO_RATIO)){
				if(ratioNuevo.getStrNombreRatio()==null  || ratioNuevo.getStrNombreRatio().isEmpty()){
					mensaje = "Hubo un error durante el registro del ratio. Debe ingresar un Nombre.";
					return;
				}
				if(ratioNuevo.getStrDescripcionRatio()==null  || ratioNuevo.getStrDescripcionRatio().isEmpty()){
					mensaje = "Hubo un error durante el registro del ratio. Debe ingresar una Descripción.";
					return;
				}
				if(ratioNuevo.getIntParaUnidadMedida().equals(new Integer(0))){
					mensaje = "Hubo un error durante el registro del ratio. Debe seleccionar una unidad de Medida.";
					return;
				}
				RatioDetalle ratioDetalleSuperiorPrimero = buscarPorIntParteGrupo(listaRatioDetalleSuperior, 1);
				if(ratioDetalleSuperiorPrimero.getOperando1().getId().getIntItemAnexoDetalle()==null 
					&& ratioDetalleSuperiorPrimero.getOperando2().getId().getIntItemAnexoDetalle()==null){
					mensaje = "Hubo un error durante el registro del ratio. Debe ingresar un elemento en la parte superior de la fórmula";
					return;
				}
				RatioDetalle ratioDetalleInferiorPrimero = buscarPorIntParteGrupo(listaRatioDetalleInferior, 1);
				if(ratioDetalleInferiorPrimero.getOperando1().getId().getIntItemAnexoDetalle()==null 
					&& ratioDetalleInferiorPrimero.getOperando2().getId().getIntItemAnexoDetalle()==null){
					mensaje = "Hubo un error durante el registro del ratio. Debe ingresar un elemento en la parte inferior de la fórmula";
					return;
				}
				ratioNuevo.getListaRatioDetalle().addAll(listaRatioDetalleSuperior);
				ratioNuevo.getListaRatioDetalle().addAll(listaRatioDetalleInferior);
				
			}else if(intIndicador.equals(Constante.PARAM_T_GRUPORATIOANEXO_ANEXO)){
				if(anexoNuevo.getStrDescripcion()== null || anexoNuevo.getStrDescripcion().isEmpty()){
					mensaje = "Hubo un error durante el registro del anexo. Debe ingresar la Descripción.";
				}
				boolean existeTextoEnBlanco = Boolean.FALSE;
				for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
					if(anexoDetalle.getStrTexto()==null || anexoDetalle.getStrTexto().isEmpty()){
						existeTextoEnBlanco = Boolean.TRUE;
						break;
					}
				}
				if(existeTextoEnBlanco){
					mensaje = "Ocurrió un error durante el registro del anexo. Debe ingresar un texto para cada elemento .";
					return;
				}
				anexoNuevo.setListaAnexoDetalle(listaAnexoDetalle);
				for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
					anexoDetalle.getId().setIntPersEmpresaAnexo(anexoNuevo.getId().getIntPersEmpresaAnexo());
					anexoDetalle.getId().setIntContPeriodoAnexo(anexoNuevo.getId().getIntContPeriodoAnexo());
					anexoDetalle.getId().setIntParaTipoAnexo(anexoNuevo.getId().getIntParaTipoAnexo());
					anexoDetalle.setIntHabilitarCuenta(Constante.HABILITAR_CUENTA_NO);					
				}
			}else{
				return;
			}
			//Fin validaciones
			
			if(registrarNuevo){
				if(intIndicador.equals(Constante.PARAM_T_GRUPORATIOANEXO_RATIO)){
					ratioNuevo.getId().setIntPersEmpresaRatio(usuario.getPerfil().getId().getIntPersEmpresaPk());
					ratioNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					
					ratioNuevo = cierreFacade.grabarRatio(ratioNuevo);
					mensaje="Se registró correctamente el ratio '"+ratioNuevo.getStrNombreRatio()+"'.";
				
				}else if(intIndicador.equals(Constante.PARAM_T_GRUPORATIOANEXO_ANEXO)){
					anexoNuevo.getId().setIntContPeriodoAnexo(ratioNuevo.getId().getIntContPeriodoRatio());
					anexoNuevo.getId().setIntPersEmpresaAnexo(usuario.getPerfil().getId().getIntPersEmpresaPk());
					anexoNuevo.setIntParaTipoLibroAnexo(Constante.TIPO_ANEXO);
					anexoNuevo.setIntPersEmpresaUsuario(usuario.getPerfil().getId().getIntPersEmpresaPk());
					anexoNuevo.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
					anexoNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					
					if(!cierreFacade.buscarAnexo(anexoNuevo).isEmpty()){
						mensaje = "Ya existe un anexo creado con ese periodo y tipo.";
						return;
					}
					
					for(AnexoDetalle anexoDetalle : anexoNuevo.getListaAnexoDetalle()){
						anexoDetalle.getId().setIntPersEmpresaAnexo(anexoNuevo.getId().getIntPersEmpresaAnexo());
						anexoDetalle.getId().setIntContPeriodoAnexo(anexoNuevo.getId().getIntContPeriodoAnexo());
						anexoDetalle.getId().setIntParaTipoAnexo(anexoNuevo.getId().getIntParaTipoAnexo());
					}
					anexoNuevo = cierreFacade.grabarAnexo(anexoNuevo);
					mensaje="Se registró correctamente el anexo.";
					
				}else{
					return;
				}
			}else{
				if(intIndicador.equals(Constante.PARAM_T_GRUPORATIOANEXO_RATIO)){
					cierreFacade.modificarRatio(ratioNuevo);
					mensaje="Se modificó correctamente el ratio '"+ratioNuevo.getStrNombreRatio()+"'.";
					
				}else if(intIndicador.equals(Constante.PARAM_T_GRUPORATIOANEXO_ANEXO)){
					for(AnexoDetalle anexoDetalle : anexoNuevo.getListaAnexoDetalle()){
						anexoDetalle.getId().setIntPersEmpresaAnexo(anexoNuevo.getId().getIntPersEmpresaAnexo());
						anexoDetalle.getId().setIntContPeriodoAnexo(anexoNuevo.getId().getIntContPeriodoAnexo());
						anexoDetalle.getId().setIntParaTipoAnexo(anexoNuevo.getId().getIntParaTipoAnexo());
					}
					cierreFacade.modificarAnexo(anexoNuevo);
					mensaje="Se modificó correctamente el anexo.";
					
				}else{
					return;
				}
			}
			
			buscar();
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		}catch(AnexoDetalleException e){
			mensaje = e.getMessage();
			log.error(e.getMessage(),e);			
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de registro del cierre de cuenta.";
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

	
	public void modificarRegistro(){
		try{
			if(intIndicadorFiltro.equals(Constante.PARAM_T_GRUPORATIOANEXO_RATIO)){
				cargarRegistroRatio();
			}else if(intIndicadorFiltro.equals(Constante.PARAM_T_GRUPORATIOANEXO_ANEXO)){
				cargarRegistroAnexo();
			}
			
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			habilitarCabeceraNuevo = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void eliminarRegistro(){
		boolean exito = Boolean.FALSE;
		String mensaje = "";
		try{
			if(intIndicadorFiltro.equals(Constante.PARAM_T_GRUPORATIOANEXO_RATIO)){
				cierreFacade.eliminarRatio(registroSeleccionado);
				mensaje = "El ratio se anulo correctamente.";
			}else if(intIndicadorFiltro.equals(Constante.PARAM_T_GRUPORATIOANEXO_RATIO)){
				cierreFacade.eliminarAnexo(registroSeleccionadoAnexo);
				mensaje = "El anexo se eliminó correctamente.";
			}			
			buscar();
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
	
	
	/*private void revisarHabilitarAgregar(List<RatioDetalle> listaRatioDetalle, Integer intCorrelativoOrden){
		for(RatioDetalle ratioDetalle : listaRatioDetalle){
			if(!ratioDetalle.getIntParteGrupo().equals(intCorrelativoOrden)){
				ratioDetalle.setHabilitarAgregar(Boolean.FALSE);
			}else{
				ratioDetalle.setHabilitarAgregar(Boolean.TRUE);
			}
		}
	}*/
	
	public void analizar(){
		try{
			log.info("intIndicador:"+intIndicador);
			habilitarGrabar = Boolean.TRUE;
			habilitarCabeceraNuevo = Boolean.FALSE;
			
			if(intIndicador.equals(Constante.PARAM_T_GRUPORATIOANEXO_RATIO)){
				listaRatioDetalleSuperior = new ArrayList<RatioDetalle>();
				listaRatioDetalleInferior = new ArrayList<RatioDetalle>();
				intParaOperacionPrincipal = new Integer(1);
				ratioNuevo.setIntParaUnidadMedida(0);
				ratioNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				intCorrelativoOrdenSuperior = new Integer(0);
				intCorrelativoOrdenInferior = new Integer(0);
				agregarListaRatioDetalleSuperior();
				agregarListaRatioDetalleInferior();
				
			}else if(intIndicador.equals(Constante.PARAM_T_GRUPORATIOANEXO_ANEXO)){
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
				revisarHabilitaciones();
			}
			
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	public void agregarListaRatioDetalleSuperior(){
		try{
			if(listaRatioDetalleSuperior.size()>=cantidadOperadoresRatio){
				return;
			}
			RatioDetalle ratioDetalleSuperior = new RatioDetalle();
			intCorrelativoOrdenSuperior = intCorrelativoOrdenSuperior + 1;
			ratioDetalleSuperior.setIntParteGrupo(intCorrelativoOrdenSuperior);
			ratioDetalleSuperior.setIntParte(Constante.PARTE_SUPERIOR);
			listaRatioDetalleSuperior.add(ratioDetalleSuperior);
			//revisarHabilitarAgregar(listaRatioDetalleSuperior,intCorrelativoOrdenSuperior);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void agregarListaRatioDetalleInferior(){
		try{
			if(listaRatioDetalleInferior.size()>=cantidadOperadoresRatio){
				return;
			}
			RatioDetalle ratioDetalleInferior = new RatioDetalle();
			intCorrelativoOrdenInferior = intCorrelativoOrdenInferior + 1;
			ratioDetalleInferior.setIntParteGrupo(intCorrelativoOrdenInferior);
			ratioDetalleInferior.setIntParte(Constante.PARTE_INFERIOR);
			listaRatioDetalleInferior.add(ratioDetalleInferior);
			//revisarHabilitarAgregar(listaRatioDetalleInferior,intCorrelativoOrdenInferior);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private RatioDetalle buscarPorIntParteGrupo(List<RatioDetalle> listaRatioDetalle, Integer intParteGrupo){
		for(RatioDetalle ratioDetalle : listaRatioDetalle){
			if(ratioDetalle.getIntParteGrupo().equals(intParteGrupo)){
				return ratioDetalle;
			}
		}
		return null;
	}
	
	public void desagregarListaRatioDetalleSuperior(ActionEvent event){
		try{
			if(listaRatioDetalleSuperior.size()==0){
				return;
			}
			ratioDetalleSeleccionado = (RatioDetalle)event.getComponent().getAttributes().get("item");
			int intNumeroEliminado =  ratioDetalleSeleccionado.getIntParteGrupo();
			listaRatioDetalleSuperior.remove(ratioDetalleSeleccionado);
			
			if(intNumeroEliminado == 1){
				if(buscarPorIntParteGrupo(listaRatioDetalleSuperior,2)!=null)buscarPorIntParteGrupo(listaRatioDetalleSuperior,2).setIntParteGrupo(1);
				if(buscarPorIntParteGrupo(listaRatioDetalleSuperior,3)!=null)buscarPorIntParteGrupo(listaRatioDetalleSuperior,3).setIntParteGrupo(2);
			}else if(intNumeroEliminado == 2){
				if(buscarPorIntParteGrupo(listaRatioDetalleSuperior,3)!=null)buscarPorIntParteGrupo(listaRatioDetalleSuperior,3).setIntParteGrupo(2);
			}
			intCorrelativoOrdenSuperior = intCorrelativoOrdenSuperior - 1;
			//revisarHabilitarAgregar(listaRatioDetalleSuperior,intCorrelativoOrdenSuperior);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void desagregarListaRatioDetalleInferior(ActionEvent event){
		try{
			if(listaRatioDetalleInferior.size()==0){
				return;
			}
			ratioDetalleSeleccionado = (RatioDetalle)event.getComponent().getAttributes().get("item");
			int intNumeroEliminado =  ratioDetalleSeleccionado.getIntParteGrupo();
			listaRatioDetalleInferior.remove(ratioDetalleSeleccionado);
			
			if(intNumeroEliminado == 1){
				if(buscarPorIntParteGrupo(listaRatioDetalleInferior,2)!=null)buscarPorIntParteGrupo(listaRatioDetalleInferior,2).setIntParteGrupo(1);
				if(buscarPorIntParteGrupo(listaRatioDetalleInferior,2)!=null)buscarPorIntParteGrupo(listaRatioDetalleInferior,3).setIntParteGrupo(2);
			}else if(intNumeroEliminado == 2){
				if(buscarPorIntParteGrupo(listaRatioDetalleInferior,2)!=null)buscarPorIntParteGrupo(listaRatioDetalleInferior,3).setIntParteGrupo(2);
			}
			intCorrelativoOrdenInferior = intCorrelativoOrdenInferior - 1;
			//revisarHabilitarAgregar(listaRatioDetalleInferior,intCorrelativoOrdenInferior);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpElemento(ActionEvent event){
		try{
			intNumeroOperando = Integer.parseInt(((String)event.getComponent().getAttributes().get("operando")));
			if(intNumeroOperando.equals(new Integer(0))){
				anexoDetalleSeleccionado = (AnexoDetalle)event.getComponent().getAttributes().get("item");
			}else{
				ratioDetalleSeleccionado = (RatioDetalle)event.getComponent().getAttributes().get("item");
				strTextoFiltrar = "";
			}			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpCuenta(ActionEvent event){
		try{
			log.info("--abrirPopUpCuenta");
			anexoDetalleSeleccionado = (AnexoDetalle)event.getComponent().getAttributes().get("item");
			intTipoBusquedaPlanCuenta = 0;
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);
			for(Object o : listaPlanCuenta){
				log.info((PlanCuenta)o);
			}
			habilitarTextoPlanCuenta = Boolean.FALSE;
			planCuentaFiltro.getId().setIntPeriodoCuenta(-1);
			//intTipoAgregarPlanCuenta = (Integer)event.getComponent().getAttributes().get(Constante.TIPOAGREGARPLANCUENTA);
			
		}catch(Exception e){
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
	
	private AnexoDetalle buscarPadrePopUp(AnexoDetalle anexoDetalleHijo){
		Integer intNivelPadre 	= anexoDetalleHijo.getIntNivelPadre();
		Integer intPosicionPadre= anexoDetalleHijo.getIntPosicionPadre();
		Integer intItemPadre 	= anexoDetalleHijo.getIntItemPadre();
		
		for(Object o : listaAnexoDetallePopUp){
			AnexoDetalle anexoDetalle = (AnexoDetalle)o;
			if(anexoDetalle.getIntNivel().equals(intNivelPadre) 
				&& anexoDetalle.getIntPosicion().equals(intPosicionPadre)
				&& anexoDetalle.getIntItem().equals(intItemPadre)){				
				return anexoDetalle;
			}
		}		
		return null;
	}
	
	private void aplicarNumeracionPopUp(){
		for(Object o : listaAnexoDetallePopUp){
			AnexoDetalle anexoDetalle = (AnexoDetalle)o;
			if(anexoDetalle.getIntNivelPadre().equals(new Integer(0))){
				anexoDetalle.setStrNumeracion(anexoDetalle.getIntPosicion()+"");
			}else{
				AnexoDetalle anexoDetallePadre = buscarPadrePopUp(anexoDetalle);
				String numeracion = "";
				for(int i=0;i<anexoDetalle.getIntNivel();i++){
					numeracion = numeracion + " ";
				}
				numeracion = numeracion + anexoDetallePadre.getStrNumeracion()+"."+anexoDetalle.getIntPosicion();
				anexoDetalle.setStrNumeracion(numeracion);
			}
		}		
	}
	
	public void seleccionarEstadoFinanciero(){
		try{
			if(intTipoEstadoFinanciero.equals(new Integer(0))){
				return;
			}
			Anexo anexoFiltro = new Anexo();
			anexoFiltro.getId().setIntPersEmpresaAnexo(usuario.getPerfil().getId().getIntPersEmpresaPk());
			anexoFiltro.getId().setIntParaTipoAnexo(intTipoEstadoFinanciero);
			anexoFiltro.getId().setIntContPeriodoAnexo(ratioNuevo.getId().getIntContPeriodoRatio());
			listaAnexoDetallePopUp = cierreFacade.getListaAnexoDetallePorAnexo(anexoFiltro);
			if(listaAnexoDetallePopUp!=null && !listaAnexoDetallePopUp.isEmpty()){
				iniciarOrdenarListaAnexoDetallePopUp();
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public boolean filtrarElementos(Object actual){
		try{
			 AnexoDetalle anexoDetalleActual = (AnexoDetalle)actual;
			   if (strTextoFiltrar.length()==0) {
			      return true;
			   }
			   if (anexoDetalleActual.getStrTexto().toUpperCase().startsWith(strTextoFiltrar.toUpperCase())) {
			      return true;
			   }else {
				   return false; 
			   }
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return false;
		}
	}
	
	public void deseleccionarElemento(){
		try{
			
			log.info("intNumeroOperando:"+intNumeroOperando);
			
			if(intNumeroOperando.equals(new Integer(1))){
				ratioDetalleSeleccionado.setOperando1(new AnexoDetalle());
			}else if(intNumeroOperando.equals(new Integer(2))){
				ratioDetalleSeleccionado.setOperando2(new AnexoDetalle());
			}else if(intNumeroOperando.equals(new Integer(0))){
				anexoDetalleSeleccionado.setAnexoDetalleReferencia(null);
				anexoDetalleSeleccionado.setStrTexto(null);
				revisarHabilitaciones();
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarElemento(ActionEvent event){
		try{
			AnexoDetalle anexoDetalleSeleccionadoPopUp = (AnexoDetalle)event.getComponent().getAttributes().get("item");
			
			log.info("intNumeroOperando:"+intNumeroOperando);
			
			if(intNumeroOperando.equals(new Integer(1))){
				ratioDetalleSeleccionado.setOperando1(anexoDetalleSeleccionadoPopUp);
			}else if(intNumeroOperando.equals(new Integer(2))){
				ratioDetalleSeleccionado.setOperando2(anexoDetalleSeleccionadoPopUp);
			}else if(intNumeroOperando.equals(new Integer(0))){
				anexoDetalleSeleccionado.setStrTexto(anexoDetalleSeleccionadoPopUp.getStrTexto());
				anexoDetalleSeleccionado.setAnexoDetalleReferencia(anexoDetalleSeleccionadoPopUp);
				revisarHabilitaciones();
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void limpiarElemento(ActionEvent event){
		try{
			log.info("--limpiarElemento");
			ratioDetalleSeleccionado = (RatioDetalle)event.getComponent().getAttributes().get("item");
			//int intIndice = listaRatioDetalleSuperior.indexOf(anexoDetalleSeleccionado);
			
			intNumeroOperando = Integer.parseInt(((String)event.getComponent().getAttributes().get("operando")));
			log.info("intNumeroOperando:"+intNumeroOperando);
			if(intNumeroOperando.equals(new Integer(1))){
				ratioDetalleSeleccionado.setOperando1(new AnexoDetalle());
			}else if(intNumeroOperando.equals(new Integer(2))){
				ratioDetalleSeleccionado.setOperando2(new AnexoDetalle());
			}
			
			//log.info("intIndice:"+intIndice);
			//listaRatioDetalleSuperior.set(intIndice, anexoDetalleSeleccionado);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	//------------------------------------INICIO METODOS DE ANEXOCONTROLLER-----------------------------------------
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
	private List<AnexoDetalle> buscarListaHermanosPopUp(AnexoDetalle anexoDetalle){
		Integer intNivelPadre 	= anexoDetalle.getIntNivelPadre();
		Integer intPosicionPadre= anexoDetalle.getIntPosicionPadre();
		Integer intItemPadre 	= anexoDetalle.getIntItemPadre();
		
		List <AnexoDetalle> listaHermanos = new ArrayList<AnexoDetalle>();
		
		for(AnexoDetalle aTemp : listaAnexoDetallePopUp){
			if(aTemp.getIntNivelPadre().equals(intNivelPadre) 
				&& aTemp.getIntPosicionPadre().equals(intPosicionPadre)
				&& aTemp.getIntItemPadre().equals(intItemPadre)){				
				listaHermanos.add(aTemp);
			}
		}		
		return listaHermanos;
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
	
	public void iniciarOrdenarListaAnexoDetallePopUp(){
		try{
			intOrdenCorrelativo = new Integer(0);
			
			AnexoDetalle anexoTemp = new AnexoDetalle();
			anexoTemp.setIntNivel(1);
			anexoTemp.setIntPosicion(1);
			anexoTemp.setIntItem(1);
			anexoTemp.setIntNivelPadre(0);
			anexoTemp.setIntPosicionPadre(0);
			anexoTemp.setIntItemPadre(0);
			
			for(AnexoDetalle aTemp : listaAnexoDetallePopUp){
				aTemp.setRevisado(Boolean.FALSE);
			}
			log.info("--iniciarOrdenarAnexoTemp");
			ordenarAnexoDetallePopUp(anexoTemp);
			log.info("--finOrdenarAnexoTemp");
			//revisarHabilitaciones();

			//ordenamos por intOrden (sort por defecto)
			Collections.sort(listaAnexoDetallePopUp);
			
			/*for(AnexoTemp aTemp : listaAnexoTemp){
				log.info(aTemp);
			}*/
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
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
	
	private List<AnexoDetalle> buscarListaHijosPopUp(AnexoDetalle anexoDetallePadre){
		Integer intNivelPadre 	= anexoDetallePadre.getIntNivel();
		Integer intPosicionPadre= anexoDetallePadre.getIntPosicion();
		Integer intItemPadre 	= anexoDetallePadre.getIntItem();
		
		List <AnexoDetalle> listaHijos = new ArrayList<AnexoDetalle>();
		
		for(AnexoDetalle aTemp : listaAnexoDetallePopUp){
			if(aTemp.getIntNivelPadre().equals(intNivelPadre) 
				&& aTemp.getIntPosicionPadre().equals(intPosicionPadre)
				&& aTemp.getIntItemPadre().equals(intItemPadre)){				
				listaHijos.add(aTemp);
			}
		}
		
		return listaHijos;
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
	public void ordenarAnexoDetallePopUp(AnexoDetalle anexoDetalle) throws Exception{
		try{
			//log.info("---------"+anexoTemp);
			List<AnexoDetalle> listaHermanos = buscarListaHermanosPopUp(anexoDetalle);
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
					listaHijos = buscarListaHijosPopUp(hermano);
					aplicarNumeracionPopUp(hermano);
					//si no posee hijos
					if(listaHijos.isEmpty()){
						intOrdenCorrelativo = intOrdenCorrelativo +1;
						hermano.setIntOrden(intOrdenCorrelativo);
						//log.info("*hermano:"+hermano);
					}else{
						intOrdenCorrelativo = intOrdenCorrelativo +1;
						hermano.setIntOrden(intOrdenCorrelativo);
						//log.info("-hermano:"+hermano);
						ordenarAnexoDetallePopUp(listaHijos.get(0));
					}
				}				
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	private void revisarHabilitaciones(){
		for(AnexoDetalle aTemp : listaAnexoDetalle){
			aTemp.setHabilitarVerCuentas(Boolean.FALSE);
			aTemp.setHabilitarConfigurar(Boolean.FALSE);
			aTemp.setHabilitarVerReferencia(Boolean.FALSE);
			if(!poseeHijos(aTemp)){
				if(aTemp.getListaAnexoDetalleCuenta()!=null && !aTemp.getListaAnexoDetalleCuenta().isEmpty()){
					aTemp.setHabilitarVerCuentas(Boolean.TRUE);
				
				}else if(aTemp.getListaAnexoDetalleOperador()!=null && !aTemp.getListaAnexoDetalleOperador().isEmpty()){
					aTemp.setHabilitarConfigurar(Boolean.TRUE);
				
				}else if(aTemp.getAnexoDetalleReferencia()!=null){
					aTemp.setHabilitarVerReferencia(Boolean.TRUE);
				
				}else if((aTemp.getListaAnexoDetalleCuenta()==null 
					|| aTemp.getListaAnexoDetalleCuenta().isEmpty()) 
					&&(aTemp.getListaAnexoDetalleOperador()==null
					|| aTemp.getListaAnexoDetalleOperador().isEmpty())
					&& aTemp.getAnexoDetalleReferencia()==null){					
					aTemp.setHabilitarVerCuentas(Boolean.TRUE);
					aTemp.setHabilitarConfigurar(Boolean.TRUE);
					aTemp.setHabilitarVerReferencia(Boolean.TRUE);
				}
			}
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
	private void aplicarNumeracionPopUp(AnexoDetalle anexoDetalle){
		if(anexoDetalle.getIntNivelPadre().equals(new Integer(0))){
			anexoDetalle.setStrNumeracion(anexoDetalle.getIntPosicion()+"");
		}else{
			AnexoDetalle anexoDetallePadre = buscarPadrePopUp(anexoDetalle);
			String numeracion = "";
			for(int i=0;i<anexoDetalle.getIntNivel();i++){
				numeracion = numeracion + " ";
			}
			numeracion = numeracion + anexoDetallePadre.getStrNumeracion()+"."+anexoDetalle.getIntPosicion();
			anexoDetalle.setStrNumeracion(numeracion);
		}
	}	
	private boolean poseeHijos(AnexoDetalle anexoDetalle){
		List<AnexoDetalle> listaHijos = buscarListaHijos(anexoDetalle);
		if(!listaHijos.isEmpty()){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
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
	
	/*Otros metodos*/
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
	//------------------------------------FIN METODOS DE ANEXOCONTROLLER--------------------------------------------
	
	
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
	public List getListaRatio() {
		return listaRatio;
	}
	public void setListaRatio(List listaRatio) {
		this.listaRatio = listaRatio;
	}
	public Ratio getRatioNuevo() {
		return ratioNuevo;
	}
	public void setRatioNuevo(Ratio ratioNuevo) {
		this.ratioNuevo = ratioNuevo;
	}
	public Ratio getRatioFiltro() {
		return ratioFiltro;
	}
	public void setRatioFiltro(Ratio ratioFiltro) {
		this.ratioFiltro = ratioFiltro;
	}
	public Ratio getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(Ratio registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public boolean isHabilitarCabeceraNuevo() {
		return habilitarCabeceraNuevo;
	}
	public void setHabilitarCabeceraNuevo(boolean habilitarCabeceraNuevo) {
		this.habilitarCabeceraNuevo = habilitarCabeceraNuevo;
	}
	public List getListaRatioDetalleSuperior() {
		return listaRatioDetalleSuperior;
	}
	public void setListaRatioDetalleSuperior(List listaRatioDetalleSuperior) {
		this.listaRatioDetalleSuperior = listaRatioDetalleSuperior;
	}
	public List getListaRatioDetalleInferior() {
		return listaRatioDetalleInferior;
	}
	public void setListaRatioDetalleInferior(List listaRatioDetalleInferior) {
		this.listaRatioDetalleInferior = listaRatioDetalleInferior;
	}
	public Integer getIntParaOperacionPrincipal() {
		return intParaOperacionPrincipal;
	}
	public void setIntParaOperacionPrincipal(Integer intParaOperacionPrincipal) {
		this.intParaOperacionPrincipal = intParaOperacionPrincipal;
	}
	public Integer getIntIndicador() {
		return intIndicador;
	}
	public void setIntIndicador(Integer intIndicador) {
		this.intIndicador = intIndicador;
	}
	public RatioDetalle getRatioDetalleSeleccionado() {
		return ratioDetalleSeleccionado;
	}
	public void setRatioDetalleSeleccionado(RatioDetalle ratioDetalleSeleccionado) {
		this.ratioDetalleSeleccionado = ratioDetalleSeleccionado;
	}
	public Integer getIntTipoEstadoFinanciero() {
		return intTipoEstadoFinanciero;
	}
	public void setIntTipoEstadoFinanciero(Integer intTipoEstadoFinanciero) {
		this.intTipoEstadoFinanciero = intTipoEstadoFinanciero;
	}
	public String getStrTextoFiltrar() {
		return strTextoFiltrar;
	}
	public void setStrTextoFiltrar(String strTextoFiltrar) {
		this.strTextoFiltrar = strTextoFiltrar;
	}
	public List getListaPeriodicidad() {
		return listaPeriodicidad;
	}
	public void setListaPeriodicidad(List listaPeriodicidad) {
		this.listaPeriodicidad = listaPeriodicidad;
	}
	public List getListaMoneda() {
		return listaMoneda;
	}
	public void setListaMoneda(List listaMoneda) {
		this.listaMoneda = listaMoneda;
	}
	public Integer getIntIndicadorFiltro() {
		return intIndicadorFiltro;
	}
	public void setIntIndicadorFiltro(Integer intIndicadorFiltro) {
		this.intIndicadorFiltro = intIndicadorFiltro;
	}
	public Anexo getAnexoNuevo() {
		return anexoNuevo;
	}
	public void setAnexoNuevo(Anexo anexoNuevo) {
		this.anexoNuevo = anexoNuevo;
	}
	public List<AnexoDetalle> getListaAnexoDetallePopUp() {
		return listaAnexoDetallePopUp;
	}
	public void setListaAnexoDetallePopUp(List<AnexoDetalle> listaAnexoDetallePopUp) {
		this.listaAnexoDetallePopUp = listaAnexoDetallePopUp;
	}
	public List<AnexoDetalle> getListaAnexoDetalle() {
		return listaAnexoDetalle;
	}
	public void setListaAnexoDetalle(List<AnexoDetalle> listaAnexoDetalle) {
		this.listaAnexoDetalle = listaAnexoDetalle;
	}
	public List getListaPlanCuenta() {
		return listaPlanCuenta;
	}
	public void setListaPlanCuenta(List listaPlanCuenta) {
		this.listaPlanCuenta = listaPlanCuenta;
	}
	public PlanCuenta getPlanCuentaFiltro() {
		return planCuentaFiltro;
	}
	public void setPlanCuentaFiltro(PlanCuenta planCuentaFiltro) {
		this.planCuentaFiltro = planCuentaFiltro;
	}
	public Integer getIntTipoBusquedaPlanCuenta() {
		return intTipoBusquedaPlanCuenta;
	}
	public void setIntTipoBusquedaPlanCuenta(Integer intTipoBusquedaPlanCuenta) {
		this.intTipoBusquedaPlanCuenta = intTipoBusquedaPlanCuenta;
	}
	public boolean isHabilitarTextoPlanCuenta() {
		return habilitarTextoPlanCuenta;
	}
	public void setHabilitarTextoPlanCuenta(boolean habilitarTextoPlanCuenta) {
		this.habilitarTextoPlanCuenta = habilitarTextoPlanCuenta;
	}
	public List getListaTipoAnexo() {
		return listaTipoAnexo;
	}
	public void setListaTipoAnexo(List listaTipoAnexo) {
		this.listaTipoAnexo = listaTipoAnexo;
	}
	public List<AnexoDetalleOperador> getListaAnexoDetalleOperador() {
		return listaAnexoDetalleOperador;
	}
	public void setListaAnexoDetalleOperador(
			List<AnexoDetalleOperador> listaAnexoDetalleOperador) {
		this.listaAnexoDetalleOperador = listaAnexoDetalleOperador;
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
	public List getListaAnexo() {
		return listaAnexo;
	}
	public void setListaAnexo(List listaAnexo) {
		this.listaAnexo = listaAnexo;
	}
	public List getListaTipoAnexoPopUp() {
		return listaTipoAnexoPopUp;
	}
	public void setListaTipoAnexoPopUp(List listaTipoAnexoPopUp) {
		this.listaTipoAnexoPopUp = listaTipoAnexoPopUp;
	}
	public Integer getIntNumeroOperando() {
		return intNumeroOperando;
	}
	public void setIntNumeroOperando(Integer intNumeroOperando) {
		this.intNumeroOperando = intNumeroOperando;
	}
}