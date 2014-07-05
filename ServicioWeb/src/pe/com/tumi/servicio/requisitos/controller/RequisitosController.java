package pe.com.tumi.servicio.requisitos.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Concepto;
import pe.com.tumi.credito.socio.captacion.domain.Requisito;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeRemote;
import pe.com.tumi.credito.socio.captacion.facade.VinculoFacadeRemote;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCreditoId;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServCaptacion;
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServGrupoCta;
import pe.com.tumi.servicio.configuracion.domain.ConfServRol;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;

public class RequisitosController {

	protected static Logger log = Logger.getLogger(RequisitosController.class);	
	
	private TablaFacadeRemote tablaFacade;
	private EstructuraFacadeRemote estructuraFacade;
	private ConfSolicitudFacadeLocal confSolicitudFacade;
	private CreditoFacadeRemote creditoFacade;
	private CaptacionFacadeRemote captacionFacade;
	private VinculoFacadeRemote vinculoFacade;
	
	private ConfServSolicitud confServSolicitudNuevo;
	private ConfServSolicitud confServSolicitudFiltro;
	private ConfServSolicitud registroSeleccionado;
	private ConfServDetalle confServDetalle;
	private Estructura estructuraFiltro;
	private Credito creditoFiltro;
	
	private List listaConfServSolicitud;
	private List listaTipoRelacion;
	private List listaTipoCuenta;
	private List listaConfServDetalle;
	private List listaEstructura;
	private List listaConfServEstructuraDetalle;
	private List <Tabla> listaSuboperacion;
	private List listaRequisito;
	private List listaConfServCredito;
	private List <CreditoComp>	listaCreditoComp;
	private List <CreditoComp>	listaCreditoCompPersiste;
	//private List listaEstructuraComp;
	//private List listaEstructuraCompPersiste;
	private List listaTipoCreditoEmpresa;
	private List listaConfServCaptacion;
	private List listaCaptacion;
	private List listaCaptacionPersiste;
	private List<Tabla> listaLabelBeneficio;
	private List<Tabla> listaLabelMotivo;
	private List<Tabla> listaLabelConcepto;
	private List listaEstructuraDetalle;
	private List <EstructuraDetalle> listaEstructuraDetallePersiste;
	
	private Date fechaFiltroInicio;
	private Date fechaFiltroFin;
	private Integer tipoCuentaFiltro;
	private Integer radioEstructura;
	private Integer radioEstructuraTodos = 1;
	private Integer radioEstructuraEntidad = 2;
	private Integer selecciona = 1;
	private Integer noSelecciona = 0;
	private String mensajeOperacion;
	private String nombrePanelEstructura;
	private String nombrePanelProducto;
	private Integer idTipoSucursal;
	private Integer intFiltroTipoCaptacion;
	private Integer intFiltroTipoBeneficio;
	private Integer intFiltroTipoMotivo;
	private Integer intFiltroTipoConcepto;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean habilitarArchivoAdjunto;
	private boolean habilitarComboEstado;
	private boolean habilitarAgregarEstructura;
	private boolean seleccionaIndeterminado;
	private boolean habilitarFechaFin;
	private boolean habilitarTipoOperacion;
	Usuario usuario;	
	
	public RequisitosController(){
		cargarValoresIniciales();
	}
	
	private void cargarValoresIniciales(){
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				listaEstructuraDetalle = new ArrayList<EstructuraDetalle>();			
				listaEstructuraDetallePersiste = new ArrayList<EstructuraDetalle>();
				listaConfServCaptacion = new ArrayList<ConfServCaptacion>();
				idTipoSucursal = null;
				creditoFiltro = new Credito();
				creditoFiltro.setId(new CreditoId());
				listaCreditoComp = new ArrayList<CreditoComp>();
				listaCreditoCompPersiste = new ArrayList<CreditoComp>();
				listaConfServCredito = new ArrayList<ConfServCredito>();
				listaConfServEstructuraDetalle = new ArrayList<ConfServEstructuraDetalle>();
				mensajeOperacion = null;
				//confServEstructura = new ConfServEstructura();
				estructuraFiltro = new Estructura();
				estructuraFiltro.setJuridica(new Juridica());
				estructuraFiltro.setId(new EstructuraId());
				listaEstructura = new ArrayList<Estructura>();
				confServDetalle = new ConfServDetalle();
				listaConfServDetalle = new ArrayList<ConfServDetalle>();
				listaConfServSolicitud = new ArrayList<ConfServSolicitud>();
				listaTipoRelacion = new ArrayList<Tabla>();
				listaTipoCuenta = new ArrayList<Tabla>();
				confServSolicitudNuevo = new ConfServSolicitud();
				confServSolicitudFiltro = new ConfServSolicitud();
				fechaFiltroInicio = null;
				fechaFiltroFin = null;
				mostrarMensajeExito = Boolean.FALSE;
				mostrarMensajeError = Boolean.FALSE;
				mostrarPanelInferior = Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
				
				tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
				confSolicitudFacade = (ConfSolicitudFacadeLocal) EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
				creditoFacade = (CreditoFacadeRemote) EJBFactory.getRemote(CreditoFacadeRemote.class);
				captacionFacade = (CaptacionFacadeRemote) EJBFactory.getRemote(CaptacionFacadeRemote.class);
				vinculoFacade = (VinculoFacadeRemote) EJBFactory.getRemote(VinculoFacadeRemote.class);
				
				listaTipoRelacion = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL),"E");
				listaTipoCuenta = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCUENTAREQUISITOS));
				
				listaLabelBeneficio = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPO_BENEFICIARIO));
				listaLabelMotivo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPO_MOTFONRET));
				listaLabelConcepto = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCONCEPTO_AES));
				
				cargarListaEstructuraDetalle();
				//cargarListaCreditoComp();
			}else{
				log.error("--Usuario obtenido es NULL.");
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/*private void cargarListaCreditoComp() throws BusinessException{		
		Credito credito = new Credito();
		CondicionCredito condicionCredito = new CondicionCredito();
		condicionCredito.setId(new CondicionCreditoId());
		credito.setId(new CreditoId());
		credito.setCondicionCredito(condicionCredito);
		listaCreditoCompPersiste = creditoFacade.getListaCreditoCompDeBusquedaCredito(credito);
		//listaCreditoComp.clear();
		listaCreditoComp = listaCreditoCompPersiste;

	}*/
	
	private void cargarListaCreditoComp(Integer intTipoCredito) throws BusinessException{		
		Credito credito = new Credito();
		CreditoId creditoId = new CreditoId();
		CondicionCredito condicionCredito = new CondicionCredito();
		creditoId.setIntParaTipoCreditoCod(intTipoCredito);
		condicionCredito.setId(new CondicionCreditoId());
		credito.setId(creditoId);
		credito.setCondicionCredito(condicionCredito);
		listaCreditoCompPersiste = creditoFacade.getListaCreditoCompDeBusquedaCredito(credito);
		listaCreditoComp = listaCreditoCompPersiste;
	}

	
	private String aplicarLabelBeneficio(Integer idBeneficio){
		for(Tabla tabla : listaLabelBeneficio)
			if(tabla.getIntIdDetalle().equals(idBeneficio))
				return tabla.getStrDescripcion();
		return "";
	}
	
	private String aplicarLabelMotivo(Integer idMotivo){
		for(Tabla tabla : listaLabelMotivo)
			if(tabla.getIntIdDetalle().equals(idMotivo))
				return tabla.getStrDescripcion();
		return "";
	}
	
	private String aplicarLabelConcepto(Integer idConcepto){
		for(Tabla tabla : listaLabelConcepto)
			if(tabla.getIntIdDetalle().equals(idConcepto))
				return tabla.getStrDescripcion();		
		return "";
	}
	
	private String obtenerBeneficiosConcatenados(List<Requisito> listaRequisitos){
		String label = "";
		String beneficiosConcatenados = "";
		for(Requisito requisito : listaRequisitos){
			label = aplicarLabelBeneficio(requisito.getId().getIntParaTipoRequisitoBenef());
			if(beneficiosConcatenados.isEmpty()){
				beneficiosConcatenados = label;
			}else{
				if(!beneficiosConcatenados.contains(label)){
					beneficiosConcatenados = beneficiosConcatenados + "/" + label;
				}
			}			
		}
		return beneficiosConcatenados;
	}
	
	private String obtenerMotivosConcatenados(List<Vinculo> listaVinculos){
		String label = "";
		String motivosConcatenados = "";
		for(Vinculo vinculo : listaVinculos){
			label = aplicarLabelMotivo(vinculo.getIntParaMotivo());
			if(motivosConcatenados.isEmpty()){
				motivosConcatenados = label;
			}else{
				if(!motivosConcatenados.contains(label)){
					motivosConcatenados = motivosConcatenados + "/" + label;
				}
			}									 
		}
		return motivosConcatenados;
	}
	
	private String obtenerConceptosConcatenados(List<Concepto> listaConceptos){
		String label = "";
		String conceptosConcatenados = "";
		for(Concepto concepto : listaConceptos){
			label = aplicarLabelConcepto(concepto.getId().getIntParaTipoConcepto());
			if(conceptosConcatenados.isEmpty()){
				conceptosConcatenados = label;
			}else{
				if(!conceptosConcatenados.contains(label)){
					conceptosConcatenados = conceptosConcatenados + "/" + label;
				}
			}									 
		}
		return conceptosConcatenados;
	}
	
	private void cargarListaCaptacion(Integer intParaTipoCaptacionCod) throws Exception{		
		try{
			log.info("intParaTipoCaptacionCod:"+intParaTipoCaptacionCod);
			CaptacionId captacionId = new CaptacionId();
			List<Requisito> listaRequisitos;
			List<Vinculo> listaVinculos;
			List<Concepto> listaConceptos;
			captacionId.setIntParaTipoCaptacionCod(intParaTipoCaptacionCod);
			listaCaptacionPersiste = captacionFacade.getCaptacionPorPKOpcional(captacionId);
			List<Captacion> listaCaptacionAux = new ArrayList<Captacion>();
			
			if(intParaTipoCaptacionCod.equals(Constante.CAPTACION_FDO_SEPELIO)){
				Captacion captacion = null;
				for(int i=0;i<listaCaptacionPersiste.size();i++){
					captacion = (Captacion)listaCaptacionPersiste.get(i);
					listaRequisitos = captacionFacade.getListaRequisitoPorPKCaptacion(captacion.getId());
					captacion.setListaRequisito(listaRequisitos);
					captacion.setStrRequisitosConcatenados(obtenerBeneficiosConcatenados(listaRequisitos));
					listaCaptacionAux.add(captacion);
				}
				listaCaptacionPersiste = listaCaptacionAux;
			
			}else if(intParaTipoCaptacionCod.equals(Constante.CAPTACION_FDO_RETIRO)){
				Captacion captacion = null;
				for(int i=0;i<listaCaptacionPersiste.size();i++){
					captacion = (Captacion)listaCaptacionPersiste.get(i);
					listaRequisitos = captacionFacade.getListaRequisitoPorPKCaptacion(captacion.getId());
					captacion.setListaRequisito(listaRequisitos);
					captacion.setStrRequisitosConcatenados(obtenerBeneficiosConcatenados(listaRequisitos));
					listaCaptacionAux.add(captacion);
				}
				listaCaptacionPersiste = listaCaptacionAux;
				listaCaptacionAux = new ArrayList<Captacion>();
				
				for(int i=0;i<listaCaptacionPersiste.size();i++){
					captacion = (Captacion)listaCaptacionPersiste.get(i);
					listaVinculos = vinculoFacade.listarVinculoPorPKCaptacion(captacion.getId());
					captacion.setListaVinculo(listaVinculos);					
					captacion.setStrVinculosConcatenados(obtenerMotivosConcatenados(listaVinculos));
					listaCaptacionAux.add(captacion);
				}
				listaCaptacionPersiste = listaCaptacionAux;
			
			}else if(intParaTipoCaptacionCod.equals(Constante.CAPTACION_AES)){
				Captacion captacion = null;
				for(int i=0;i<listaCaptacionPersiste.size();i++){
					captacion = (Captacion)listaCaptacionPersiste.get(i);
					listaConceptos = captacionFacade.getListaConceptoPorPKCaptacion(captacion.getId());
					captacion.setListaConcepto(listaConceptos);					
					captacion.setStrConceptosConcatenados(obtenerConceptosConcatenados(listaConceptos));
					listaCaptacionAux.add(captacion);
				}
				listaCaptacionPersiste = listaCaptacionAux;
			}
			if(listaCaptacionPersiste!=null){
				log.info("listaCaptacionPersiste:"+listaCaptacionPersiste.size());
			}else{
				log.info("listaCaptacionPersiste:null");
			}
			listaCaptacion = listaCaptacionPersiste;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw e;
		}
	}

	private void cargarListaEstructuraDetalle() throws BusinessException{		
		listaEstructura = estructuraFacade.getListaEstructuraPorNivelYCodigo(null,null);
		List<EstructuraDetalle> listaEstructuraDetalleAux = new ArrayList<EstructuraDetalle>(); 		
		for(Object o : listaEstructura){
			Estructura estructura = (Estructura)o;
			listaEstructuraDetalleAux = estructuraFacade.getListaEstructuraDetallePorEstructuraPK(estructura.getId());
			for(EstructuraDetalle estructuraDetalle: listaEstructuraDetalleAux){
				//Para este proceso solo usamos las estructuraDetalle con Caso == 2
				if(estructuraDetalle.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_ADMINISTRA)){
					estructuraDetalle.setEstructura(estructura);
					listaEstructuraDetallePersiste.add(estructuraDetalle);
				}
			}
		}
		listaEstructuraDetalle = listaEstructuraDetallePersiste;		
	}
	
	private EstructuraDetalle buscarListaEstructuraDetallePersiste(Integer intItemCaso) throws BusinessException{		
		for(EstructuraDetalle estructuraDetalle : listaEstructuraDetallePersiste){
			if(estructuraDetalle.getId().getIntItemCaso().equals(intItemCaso)){
				return estructuraDetalle;
			}
		}
		return null;		
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			habilitarTipoOperacion = Boolean.TRUE;
			habilitarFechaFin = Boolean.TRUE;
			seleccionaIndeterminado = Boolean.FALSE;
			radioEstructura = radioEstructuraTodos;
			habilitarAgregarEstructura = Boolean.FALSE;
			habilitarComboEstado = Boolean.FALSE;
			habilitarArchivoAdjunto = Boolean.FALSE;
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;			
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			listaConfServDetalle.clear();
			listaConfServEstructuraDetalle.clear();
			limpiarChecks(listaTipoCuenta);
			limpiarChecks(listaTipoRelacion);
			confServSolicitudNuevo = new ConfServSolicitud();
			listaConfServDetalle.clear();
			listaConfServEstructuraDetalle.clear();
			listaConfServCredito.clear();
			listaConfServCaptacion.clear();
			mostrarMensaje(Boolean.TRUE,null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void limpiarChecks(List lista){
		for(int i=0;i<lista.size();i++){
			((Tabla)lista.get(i)).setChecked(Boolean.FALSE);
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
			if(confServSolicitudFiltro.getIntParaEstadoCod().equals(Constante.PARAM_COMBO_TODOS)){
				confServSolicitudFiltro.setIntParaEstadoCod(null);
			}
			listaConfServSolicitud = confSolicitudFacade.buscarConfSolicitudRequisito(confServSolicitudFiltro, fechaFiltroInicio, fechaFiltroFin, tipoCuentaFiltro);
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
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
	
	public void grabar(){
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try{
			
			//Validación general
			if(habilitarTipoOperacion){
				mensaje = "Ocurrió un error durante el registro. Debe seleccionar un tipo de operación.";
				return;
			}
			
			boolean seleccionoTipoRelacion = Boolean.FALSE;
			for(Object o : listaTipoRelacion){
				if(((Tabla)o).getChecked()){
					seleccionoTipoRelacion = Boolean.TRUE;
					break;
				}
			}
			if(!seleccionoTipoRelacion){
				mensaje = "Ocurrió un error durante el registro. Debe seleccionar al menos un tipo de relación.";
				return;
			}
			
			//Para los roles
			List<ConfServRol> listaConfServRol = new ArrayList<ConfServRol>();
			for(Object o : listaTipoRelacion){
				Tabla tabla = (Tabla)o;
				ConfServRol confServRol = new ConfServRol();
				confServRol.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				confServRol.getId().setIntParaRolCod(tabla.getIntIdDetalle());
				if(tabla.getChecked()){
					confServRol.setIntValor(selecciona);
				}else{
					confServRol.setIntValor(noSelecciona);
				}
				listaConfServRol.add(confServRol);					
			}
			confServSolicitudNuevo.setListaRol(listaConfServRol);
			confServSolicitudNuevo.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);
			
			if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_LIQUIDACIONDECUENTA)){
				mensaje = grabarLiquidacionCuenta();
				
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO)
				||	confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_ORDENCREDITO)
				||	confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD)
				||	confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO)){
				mensaje = grabarCredito();
				
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO)
				||	confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_FONDORETIRO)
				||	confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_AES)){
				mensaje = grabarCaptacion();
			}
			
			if(mensaje.contains("correctamente")){
				exito = Boolean.TRUE;
			}
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de registro de configuración de requisitos.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	private String grabarCaptacion() throws BusinessException{
		String mensaje = "";
		try{
			//Validaciones
			boolean seleccionoTipoCuenta = Boolean.FALSE;
			for(Object o : listaTipoCuenta){
				if(((Tabla)o).getChecked()){
					seleccionoTipoCuenta = Boolean.TRUE;
					break;
				}
			}
			if(!seleccionoTipoCuenta){
				return "Ocurrió un error durante el registro. Debe seleccionar al menos un tipo de cuenta.";
			}			
			if(confServSolicitudNuevo.getDtDesde()==null){
				return "Ocurrió un error durante el registro. Debe ingresar el inicio del Rango de Fecha.";
			}
			if(!seleccionaIndeterminado && confServSolicitudNuevo.getDtHasta()==null){
				return "Ocurrió un error durante el registro. Debe ingresar el fin del Rango de Fecha.";
			}
			if(!seleccionaIndeterminado && confServSolicitudNuevo.getDtDesde().compareTo(confServSolicitudNuevo.getDtHasta())>0){
				return "Ocurrió un error durante el proceso. La fecha de inicio es mayor a la fecha de fin.";				
			}
			if(listaConfServEstructuraDetalle.isEmpty() && radioEstructura.equals(radioEstructuraEntidad)){
				return "Ocurrió un error durante el registro. Debe seleccionar al menos una estructura orgánica.";
			}
			if(listaConfServDetalle.isEmpty()){
				return "Ocurrió un error durante el registro. Debe ingresar al menos un requisito.";
			}
			log.info("listaConfServCaptacion.isEmpty():"+listaConfServCaptacion.isEmpty());
			if(listaConfServCaptacion.isEmpty()){
				return "Ocurrió un error durante el registro. Debe ingresar al menos un producto.";
			}
			//Para tipo de cuentas
			List<ConfServGrupoCta> listaConfServGrupoCta = new ArrayList<ConfServGrupoCta>();
			for(Object o : listaTipoCuenta){
				Tabla tabla = (Tabla)o;
				ConfServGrupoCta confServGrupoCta = new ConfServGrupoCta();
				confServGrupoCta.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				confServGrupoCta.getId().setIntParaTipoCuentaCod(tabla.getIntIdDetalle());
				confServGrupoCta.setIntParaTipoMonedaCod(Constante.PARAM_T_TIPOMONEDA_SOL);
				if(tabla.getChecked()){
					confServGrupoCta.setIntValor(selecciona);
				}else{
					confServGrupoCta.setIntValor(noSelecciona);
				}
				listaConfServGrupoCta.add(confServGrupoCta);
			}
			confServSolicitudNuevo.setListaGrupoCta(listaConfServGrupoCta);
			//Si se elegio el radiobutton de Estructura
			if(radioEstructura.equals(radioEstructuraEntidad)){
				//Para seleccionar unas determinadas estructuras
				confServSolicitudNuevo.setListaEstructuraDetalle(listaConfServEstructuraDetalle);
			}else{
				//Para todas las estructuras
				listaConfServEstructuraDetalle.clear();
				for(EstructuraDetalle estructuraDetalle : listaEstructuraDetallePersiste){
					//EstructuraDetalle estructuraDetalle = (EstructuraDetalle)o;
					agregarListaConfServEstructuraDetalle(estructuraDetalle);					
				}
				confServSolicitudNuevo.setListaEstructuraDetalle(listaConfServEstructuraDetalle);
			}
			//Para los requisitos[confServDetalle]
			confServSolicitudNuevo.setListaDetalle(listaConfServDetalle);			
			//Para las captaciones[confServCaptacion]
			confServSolicitudNuevo.setListaCaptacion(listaConfServCaptacion);
			//Si es un nuevo registro a grabar
			if(registrarNuevo){
				confServSolicitudNuevo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				confServSolicitudNuevo.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				//Cambiar para pruebas en QC con 93
				//confServSolicitudNuevo.setIntPersPersonaUsuarioPk(Constante.PARAM_USUARIOSESION);
				confServSolicitudNuevo.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
				confServSolicitudNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));								
				confSolicitudFacade.grabarCaptacion(confServSolicitudNuevo);
				mensaje = "Se registraron correctamente los requisitos.";
			}else{
				confSolicitudFacade.modificarCaptacion(confServSolicitudNuevo);
				mensaje = "Se modificaron correctamente los requisitos.";
			}
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return mensaje;
	}
	
	private String grabarCredito() throws BusinessException{
		String mensaje = "";
		try{
			//Validaciones
			boolean seleccionoTipoCuenta = Boolean.FALSE;
			for(Object o : listaTipoCuenta){
				if(((Tabla)o).getChecked()){
					seleccionoTipoCuenta = Boolean.TRUE;
					break;
				}
			}
			if(!seleccionoTipoCuenta){
				return "Ocurrió un error durante el registro. Debe seleccionar al menos un tipo de cuenta.";
			}			
			if(confServSolicitudNuevo.getDtDesde()==null){
				return "Ocurrió un error durante el registro. Debe ingresar el inicio del Rango de Fecha.";
			}
			if(!seleccionaIndeterminado && confServSolicitudNuevo.getDtHasta()==null){
				return "Ocurrió un error durante el registro. Debe ingresar el fin del Rango de Fecha.";
			}
			if(!seleccionaIndeterminado && confServSolicitudNuevo.getDtDesde().compareTo(confServSolicitudNuevo.getDtHasta())>0){
				return "Ocurrió un error durante el proceso. La fecha de inicio es mayor a la fecha de fin.";				
			}
			if(listaConfServEstructuraDetalle.isEmpty() && radioEstructura.equals(radioEstructuraEntidad)){
				return "Ocurrió un error durante el registro. Debe seleccionar al menos una estructura orgánica.";
			}
			if(listaConfServDetalle.isEmpty()){
				return "Ocurrió un error durante el registro. Debe ingresar al menos un requisito.";
			}
			if(listaConfServCredito.isEmpty()){
				return "Ocurrió un error durante el registro. Debe ingresar al menos un producto.";
			}
			//Para tipo de cuentas
			List<ConfServGrupoCta> listaConfServGrupoCta = new ArrayList<ConfServGrupoCta>();
			for(Object o : listaTipoCuenta){
				Tabla tabla = (Tabla)o;
				ConfServGrupoCta confServGrupoCta = new ConfServGrupoCta();
				confServGrupoCta.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				confServGrupoCta.getId().setIntParaTipoCuentaCod(tabla.getIntIdDetalle());
				confServGrupoCta.setIntParaTipoMonedaCod(Constante.PARAM_T_TIPOMONEDA_SOL);
				if(tabla.getChecked()){
					confServGrupoCta.setIntValor(selecciona);
				}else{
					confServGrupoCta.setIntValor(noSelecciona);
				}
				listaConfServGrupoCta.add(confServGrupoCta);
			}
			confServSolicitudNuevo.setListaGrupoCta(listaConfServGrupoCta);			
			//Si se elegio el radiobutton de Estructura
			if(radioEstructura.equals(radioEstructuraEntidad)){
				//Para seleccionar unas determinadas estructuras
				confServSolicitudNuevo.setListaEstructuraDetalle(listaConfServEstructuraDetalle);
			}else{
				//Para todas las estructuras
				listaConfServEstructuraDetalle.clear();
				for(EstructuraDetalle estructuraDetalle : listaEstructuraDetallePersiste){
					//EstructuraDetalle estructuraDetalle = (EstructuraDetalle)o;
					agregarListaConfServEstructuraDetalle(estructuraDetalle);					
				}
				confServSolicitudNuevo.setListaEstructuraDetalle(listaConfServEstructuraDetalle);
			}
			//Para los requisitos[confServDetalle]
			confServSolicitudNuevo.setListaDetalle(listaConfServDetalle);			
			//Para los creditos[confServCredito]
			confServSolicitudNuevo.setListaCredito(listaConfServCredito);
			//Si es un nuevo registro a grabar
			if(registrarNuevo){
				confServSolicitudNuevo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				confServSolicitudNuevo.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				//Cambiar para pruebas en QC con 93
				//confServSolicitudNuevo.setIntPersPersonaUsuarioPk(Constante.PARAM_USUARIOSESION);
				confServSolicitudNuevo.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
				confServSolicitudNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));								
				confSolicitudFacade.grabarCredito(confServSolicitudNuevo);
				mensaje = "Se registraron correctamente los requisitos.";
			}else{
				confSolicitudFacade.modificarCredito(confServSolicitudNuevo);
				mensaje = "Se modificaron correctamente los requisitos.";
			}
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return mensaje;
	}
	
	
	private String grabarLiquidacionCuenta() throws BusinessException{
		String mensaje = "";
		try{
			//Validaciones de Liquidacion de Cuenta
			boolean seleccionoTipoCuenta = Boolean.FALSE;
			for(Object o : listaTipoCuenta){
				if(((Tabla)o).getChecked()){
					seleccionoTipoCuenta = Boolean.TRUE;
					break;
				}
			}
			if(!seleccionoTipoCuenta){
				return "Ocurrió un error durante el registro. Debe seleccionar al menos un tipo de cuenta.";
			}			
			if(confServSolicitudNuevo.getDtDesde()==null){
				return "Ocurrió un error durante el registro. Debe ingresar el inicio del Rango de Fecha.";
			}
			if(!seleccionaIndeterminado && confServSolicitudNuevo.getDtHasta()==null){
				return "Ocurrió un error durante el registro. Debe ingresar el fin del Rango de Fecha.";
			}
			if(!seleccionaIndeterminado && confServSolicitudNuevo.getDtDesde().compareTo(confServSolicitudNuevo.getDtHasta())>0){
				return "Ocurrió un error durante el proceso. La fecha de inicio es mayor a la fecha de fin.";				
			}
			if(listaConfServEstructuraDetalle.isEmpty() && radioEstructura.equals(radioEstructuraEntidad)){
				return "Ocurrió un error durante el registro. Debe seleccionar al menos una estructura orgánica.";
			}
			if(listaConfServDetalle.isEmpty()){
				return "Ocurrió un error durante el registro. Debe ingresar al menos un requisito.";
			}
			//Para tipo de cuentas
			List<ConfServGrupoCta> listaConfServGrupoCta = new ArrayList<ConfServGrupoCta>();
			for(Object o : listaTipoCuenta){
				Tabla tabla = (Tabla)o;
				ConfServGrupoCta confServGrupoCta = new ConfServGrupoCta();
				confServGrupoCta.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				confServGrupoCta.getId().setIntParaTipoCuentaCod(tabla.getIntIdDetalle());
				confServGrupoCta.setIntParaTipoMonedaCod(Constante.PARAM_T_TIPOMONEDA_SOL);
				if(tabla.getChecked()){
					confServGrupoCta.setIntValor(selecciona);
				}else{
					confServGrupoCta.setIntValor(noSelecciona);
				}
				listaConfServGrupoCta.add(confServGrupoCta);
			}
			confServSolicitudNuevo.setListaGrupoCta(listaConfServGrupoCta);			
			//Si se elegio el radiobutton de Estructura
			if(radioEstructura.equals(radioEstructuraEntidad)){
				//Para seleccionar unas determinadas estructuras
				confServSolicitudNuevo.setListaEstructuraDetalle(listaConfServEstructuraDetalle);
			}else{
				//Para todas las estructuras
				listaConfServEstructuraDetalle.clear();
				for(EstructuraDetalle estructuraDetalle : listaEstructuraDetallePersiste){
					//EstructuraDetalle estructuraDetalle = (EstructuraDetalle)o;
					agregarListaConfServEstructuraDetalle(estructuraDetalle);					
				}
				confServSolicitudNuevo.setListaEstructuraDetalle(listaConfServEstructuraDetalle);
			}
			//Para los requisitos[confServDetalle]
			confServSolicitudNuevo.setListaDetalle(listaConfServDetalle);			
			//Si es un nuevo registro a grabar
			if(registrarNuevo){
				confServSolicitudNuevo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				confServSolicitudNuevo.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				//Cambiar para pruebas en QC con 93
				//confServSolicitudNuevo.setIntPersPersonaUsuarioPk(Constante.PARAM_USUARIOSESION);
				confServSolicitudNuevo.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
				confServSolicitudNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));								
				confSolicitudFacade.grabarLiquidacionCuenta(confServSolicitudNuevo);
				mensaje = "Se registraron correctamente los requisitos.";
			}else{
				confSolicitudFacade.modificarLiquidacionCuenta(confServSolicitudNuevo);
				mensaje = "Se modificaron correctamente los requisitos.";
			}
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return mensaje;
	}
	
	public void agregarRequisito(){
		try{
			ConfServDetalle confServDetalleAux = new ConfServDetalle();
			confServDetalleAux.setIntParaTipoDescripcion(confServDetalle.getIntParaTipoDescripcion());
			confServDetalleAux.setIntParaTipoPersonaOperacionCod(confServDetalle.getIntParaTipoPersonaOperacionCod());
			confServDetalleAux.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
			confServDetalleAux.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			confServDetalleAux.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			//Para pruebas en QC se usa 93
			//confServDetalleAux.setIntPersPersonaUsuarioPk(Constante.PARAM_USUARIOSESION);
			confServDetalleAux.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
			if(habilitarArchivoAdjunto){
				confServDetalleAux.setIntOpcionAdjunta(selecciona);
			}else{
				confServDetalleAux.setIntOpcionAdjunta(noSelecciona);
			}
			log.info(confServSolicitudNuevo.getIntParaTipoOperacionCod());
			log.info(confServDetalleAux);
			listaConfServDetalle.add(confServDetalleAux);
			habilitarArchivoAdjunto = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	
	}
	
	public void eliminarRequisito(ActionEvent event){
		ConfServDetalle confServDetalleAux = (ConfServDetalle)event.getComponent().getAttributes().get("item");
		listaConfServDetalle.remove(confServDetalleAux);		
	}
	
	
	private void cargarListaTipoCreditoEmpresa(Integer agrupamientoB) throws BusinessException{	
		Integer idMaestro = Integer.parseInt(Constante.PARAM_T_TIPOCREDITOEMPRESA);
		List<Tabla> listaMaestra = tablaFacade.getListaTablaPorIdMaestro(idMaestro);
		List<Tabla> listaMaestraAux = new ArrayList<Tabla>();
		for(Tabla tabla : listaMaestra)
			if(tabla.getStrIdAgrupamientoB().contains(agrupamientoB.toString()))
				listaMaestraAux.add(tabla);
		listaTipoCreditoEmpresa = listaMaestraAux;
	}
	
	public void seleccionarRegistrarTipo(){
		try{
			estructuraFiltro.getId().setIntNivel(Constante.PARAM_COMBO_TODOS);
			estructuraFiltro.getJuridica().setStrRazonSocial(null);
			habilitarGrabar = Boolean.TRUE;
			habilitarTipoOperacion = Boolean.FALSE;
			habilitarComboEstado = Boolean.TRUE;
			
			//listaRequisito = llamarAgrupamientoB(Integer.parseInt(Constante.PARAM_T_REQUISITOSDESCRIPCION), confServSolicitudNuevo.getIntParaTipoOperacionCod());			
			mostrarMensaje(Boolean.TRUE,null);
			
			if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_LIQUIDACIONDECUENTA)){
				listaSuboperacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOSUBOPERACION));
				listaRequisito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION));
				
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO)){
				listaSuboperacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONPRESTAMO));
				creditoFiltro.getId().setIntParaTipoCreditoCod(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO);
				cargarListaCreditoComp(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO);
				cargarListaTipoCreditoEmpresa(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO);
				listaRequisito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO));
				
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_ORDENCREDITO)){
				listaSuboperacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONORDENDECREDITO));
				creditoFiltro.getId().setIntParaTipoCreditoCod(Constante.PARAM_T_TIPO_CREDITO_ORDENDECREDITO);
				cargarListaCreditoComp(Constante.PARAM_T_TIPO_CREDITO_ORDENDECREDITO);
				cargarListaTipoCreditoEmpresa(Constante.PARAM_T_TIPO_CREDITO_ORDENDECREDITO);
				listaRequisito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_REQUISITOSDESCRIPCION_ORDENCREDITO));
				
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD)){
				listaSuboperacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONACTIVIDADES));
				creditoFiltro.getId().setIntParaTipoCreditoCod(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD);
				cargarListaCreditoComp(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD);
				cargarListaTipoCreditoEmpresa(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD);
				listaRequisito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDADES));
				
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO)){
				listaSuboperacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONFONDOSEPELIO));
				intFiltroTipoCaptacion = Constante.CAPTACION_FDO_SEPELIO;
				cargarListaCaptacion(Constante.CAPTACION_FDO_SEPELIO);
				listaRequisito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDOSEPELIO));
				
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_FONDORETIRO)){
				listaSuboperacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONFONDORETIRO));
				intFiltroTipoCaptacion = Constante.CAPTACION_FDO_RETIRO;
				cargarListaCaptacion(Constante.CAPTACION_FDO_RETIRO);
				listaRequisito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDORETIRO));
				
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_AES)){
				listaSuboperacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONFONDOAES));
				intFiltroTipoCaptacion = Constante.CAPTACION_AES;
				cargarListaCaptacion(Constante.CAPTACION_AES);
				listaRequisito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_REQUISITOSDESCRIPCION_AES));
			
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO)){
				listaSuboperacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONREFINANCIAMIENTO));				
				creditoFiltro.getId().setIntParaTipoCreditoCod(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO);
				cargarListaCreditoComp(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO);
				cargarListaTipoCreditoEmpresa(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO);
				listaRequisito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO));
			}
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRadioEstructura(){
		if(radioEstructura.equals(radioEstructuraTodos)){
			habilitarAgregarEstructura = Boolean.FALSE;
		}else if(radioEstructura.equals(radioEstructuraEntidad)){
			habilitarAgregarEstructura = Boolean.TRUE;
		}
	}
	
	private List<EstructuraComp> filtrarPorTipoSucursal(List<EstructuraComp> listaEstructuraComp, Integer tipoSucursal){
		List<EstructuraComp> listaAux = new ArrayList<EstructuraComp>();
		List<EstructuraDetalle> listaEstructuraDetalle = null;
		Boolean coincideTipoSucursal = Boolean.FALSE;
		for(Object o : listaEstructuraComp){
			coincideTipoSucursal = Boolean.FALSE;
			EstructuraComp estructuraComp = (EstructuraComp)o;
			listaEstructuraDetalle = estructuraComp.getEstructura().getListaEstructuraDetalle();
			for(EstructuraDetalle estructuraDetalle : listaEstructuraDetalle){
				if(estructuraDetalle.getSucursal().getIntIdTipoSucursal().equals(tipoSucursal)){
					coincideTipoSucursal = Boolean.TRUE;
					break;
				}
			}
			if(coincideTipoSucursal){
				listaAux.add(estructuraComp);
			}
		}
		listaEstructuraComp = listaAux;
		return listaEstructuraComp;
	}
	
	public void buscarEstructuraDetalle(){
		try{
			listaEstructuraDetalle = listaEstructuraDetallePersiste;
			List<EstructuraDetalle> listaAux = new ArrayList<EstructuraDetalle>();
			if(!estructuraFiltro.getId().getIntNivel().equals(Constante.PARAM_COMBO_TODOS)){
				log.info("estructuraFiltro.getId().getIntNivel:"+estructuraFiltro.getId().getIntNivel());
				for(Object o : listaEstructuraDetalle){
					EstructuraDetalle estructuraDetalle = (EstructuraDetalle)o;
					log.info("estructuraDetalle.getEstructura().getId().getIntNivel:"+estructuraDetalle.getEstructura().getId().getIntNivel());
					if(estructuraDetalle.getEstructura().getId().getIntNivel().equals(estructuraFiltro.getId().getIntNivel())){
						log.info("cumple");
						listaAux.add(estructuraDetalle);
					}
				}
				listaEstructuraDetalle = listaAux;
			}
			/*
			if(!idTipoSucursal.equals(Constante.PARAM_COMBO_TODOS)){
				if(idTipoSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES2_AGENCIAS)){
					listaEstructuraComp = filtrarPorTipoSucursal(listaEstructuraComp,Constante.PARAM_T_TIPOSUCURSAL_AGENCIA);
					
				}else if(idTipoSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES2_FILIALES)){
					listaEstructuraComp = filtrarPorTipoSucursal(listaEstructuraComp,Constante.PARAM_T_TIPOSUCURSAL_FILIAL);
					
				}else if(idTipoSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES2_OFICINAPRINCIPAL)){
					listaEstructuraComp = filtrarPorTipoSucursal(listaEstructuraComp,Constante.PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL);
				}
			}
			*/
			String nombre = estructuraFiltro.getJuridica().getStrRazonSocial();
			if(nombre != null && nombre.length()>0){
				listaAux = new ArrayList<EstructuraDetalle>();
				for(Object o : listaEstructuraDetalle){
					EstructuraDetalle estructuraDetalle = (EstructuraDetalle)o;
					if(estructuraDetalle.getEstructura().getJuridica().getStrRazonSocial().toUpperCase().contains(nombre.toUpperCase())){
						listaAux.add(estructuraDetalle);
					}
				}
				listaEstructuraDetalle = listaAux;
			}
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarCreditoDetalle(){
		try{
			listaCreditoComp = listaCreditoCompPersiste;
			List<CreditoComp> listaAux = new ArrayList<CreditoComp>();
			
			if(!creditoFiltro.getId().getIntParaTipoCreditoCod().equals(Constante.PARAM_COMBO_TODOS)){
				listaAux.clear();
				for(CreditoComp creditoComp : listaCreditoComp)
					if(creditoComp.getCredito().getId().getIntParaTipoCreditoCod().equals(creditoFiltro.getId().getIntParaTipoCreditoCod()))
						listaAux.add(creditoComp);
				listaCreditoComp = listaAux;
			}

			if(!creditoFiltro.getIntParaTipoCreditoEmpresa().equals(Constante.PARAM_COMBO_TODOS)){
				listaAux = new ArrayList<CreditoComp>();
				for(CreditoComp creditoComp : listaCreditoComp)
					if(creditoComp.getCredito().getIntParaTipoCreditoEmpresa().equals(creditoFiltro.getIntParaTipoCreditoEmpresa()))
						listaAux.add(creditoComp);				
				listaCreditoComp = listaAux;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarCaptacion(){
		try{
			listaCaptacion = listaCaptacionPersiste;
			List<Captacion> listaAux = new ArrayList<Captacion>();
			
			if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO)){
				boolean poseeTipoBeneficio = Boolean.FALSE;
				for(Object o : listaCaptacion){
					Captacion captacion = (Captacion)o;
					for(Requisito requisito : captacion.getListaRequisito()){
						if(requisito.getId().getIntParaTipoRequisitoBenef().equals(intFiltroTipoBeneficio)){
							poseeTipoBeneficio = Boolean.TRUE;
							break;
						}
					}
					if(poseeTipoBeneficio){
						listaAux.add(captacion);
					}
				}
				listaCaptacion = listaAux;
				
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_FONDORETIRO)){
				boolean poseeTipoBeneficio = Boolean.FALSE;
				for(Object o : listaCaptacion){
					Captacion captacion = (Captacion)o;
					for(Requisito requisito : captacion.getListaRequisito()){
						if(requisito.getId().getIntParaTipoRequisitoBenef().equals(intFiltroTipoBeneficio)){
							poseeTipoBeneficio = Boolean.TRUE;
							break;
						}
					}
					if(poseeTipoBeneficio){
						listaAux.add(captacion);
					}
				}
				listaCaptacion = listaAux;
				
				listaAux = new ArrayList<Captacion>();
				boolean poseeTipoMotivo = Boolean.FALSE;
				for(Object o : listaCaptacion){
					Captacion captacion = (Captacion)o;
					for(Vinculo vinculo : captacion.getListaVinculo()){
						if(vinculo.getIntParaMotivo().equals(intFiltroTipoMotivo)){
							poseeTipoMotivo = Boolean.TRUE;
							break;
						}
					}
					if(poseeTipoMotivo){
						listaAux.add(captacion);
					}
				}
				listaCaptacion = listaAux;
				
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_AES)){
				boolean poseeTipoConcepto = Boolean.FALSE;
				for(Object o : listaCaptacion){
					Captacion captacion = (Captacion)o;
					for(Concepto concepto : captacion.getListaConcepto()){
						if(concepto.getId().getIntParaTipoConcepto().equals(intFiltroTipoConcepto)){
							poseeTipoConcepto = Boolean.TRUE;
							break;
						}
					}
					if(poseeTipoConcepto){
						listaAux.add(captacion);
					}
				}
				listaCaptacion = listaAux;
			}

		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	//Verifica que la estructura no se encuentra ya agregada en tblEstructuras
	private boolean verificarEstructuraDetalle(EstructuraDetalle estructuraDetalle){
		boolean verificado = Boolean.TRUE;
		for(Object o : listaConfServEstructuraDetalle){
			ConfServEstructuraDetalle confServEstructuraDetalle = (ConfServEstructuraDetalle)o;
			if(	confServEstructuraDetalle.getIntItemCaso().equals(estructuraDetalle.getId().getIntItemCaso())){
				verificado = Boolean.FALSE;
				break;
			}
		}
		return verificado;
	}
	
	private boolean verificarCredito(CreditoComp creditoComp){
		boolean verificado = Boolean.TRUE;
		for(Object o : listaConfServCredito){
			ConfServCredito confServCredito = (ConfServCredito)o;
			if(confServCredito.getIntSocioItemCredito().equals(creditoComp.getCredito().getId().getIntItemCredito())){
				verificado = Boolean.FALSE;
				break;
			}
		}
		return verificado;
	}
	
	private boolean verificarCaptacion(Captacion captacion){
		boolean verificado = Boolean.TRUE;
		for(Object o : listaConfServCaptacion){
			ConfServCaptacion confServCaptacion = (ConfServCaptacion)o;
			if(confServCaptacion.getIntSocioItem().equals(captacion.getId().getIntItem())){
				verificado = Boolean.FALSE;
				break;
			}
		}
		return verificado;
	}
	
	public void agregarConfServEstructuraDetalle(){		
		try{
			for(int i=0; i<listaEstructuraDetalle.size(); i++){
				EstructuraDetalle estructuraDetalle = (EstructuraDetalle)(listaEstructuraDetalle.get(i));
				//Si se selecciono en el popup se agrega a listaConfServEstructuraDetalle
				if(estructuraDetalle.getChecked() != null && estructuraDetalle.getChecked()==Boolean.TRUE){
					//Verifica que la estructura no se encuentra ya agregada en tblEstructuras
					if(verificarEstructuraDetalle(estructuraDetalle)){
						agregarListaConfServEstructuraDetalle(estructuraDetalle);
					}
				}
				((EstructuraDetalle)(listaEstructuraDetalle.get(i))).setChecked(Boolean.FALSE);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	

	
	public void agregarConfServCredito(){
		try{
			EstructuraDetalle estructuraDetalle = new EstructuraDetalle();
			estructuraDetalle.setId(new EstructuraDetalleId());
			
			for(int i=0; i<listaCreditoComp.size(); i++){
				CreditoComp creditoComp = (CreditoComp)listaCreditoComp.get(i);
				//Si se selecciono en el popup se agrega a listaConfServEstructura
				if(creditoComp.getChecked() != null && creditoComp.getChecked()==Boolean.TRUE){
					//Verifica que la estructura no se encuentra ya agregada en tblEstructuras
					if(verificarCredito(creditoComp)){
						agregarListaConfServCredito(creditoComp);
					}
				}
				((CreditoComp)(listaCreditoComp.get(i))).setChecked(Boolean.FALSE);
			}
			
			for(int i=0; i<listaCaptacion.size(); i++){
				Captacion captacion = (Captacion)listaCaptacion.get(i);
				//Si se selecciono en el popup se agrega a listaConfServCaptacion
				if(captacion.getChecked() != null && captacion.getChecked()==Boolean.TRUE){
					//Verifica que la captacion no se encuentra ya agregada en tblCaptacion
					if(verificarCaptacion(captacion)){
						agregarListaConfServCaptacion(captacion);
					}
				}
				//Limpia de checks
				((Captacion)(listaCaptacion.get(i))).setChecked(Boolean.FALSE);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private void agregarListaConfServEstructuraDetalle(EstructuraDetalle estructuraDetalle) throws BusinessException{
		ConfServEstructuraDetalle confServEstructuraDetalle = new ConfServEstructuraDetalle();
		confServEstructuraDetalle.setIntCodigoPk(estructuraDetalle.getId().getIntCodigo());
		confServEstructuraDetalle.setIntNivelPk(estructuraDetalle.getId().getIntNivel());
		confServEstructuraDetalle.setIntCaso(estructuraDetalle.getId().getIntCaso());
		confServEstructuraDetalle.setIntItemCaso(estructuraDetalle.getId().getIntItemCaso());
		confServEstructuraDetalle.setIntTipoModalidad(estructuraDetalle.getIntParaModalidadCod());
		confServEstructuraDetalle.setIntTipoSocio(estructuraDetalle.getIntParaTipoSocioCod());
		confServEstructuraDetalle.setStrRazonSocial(estructuraDetalle.getEstructura().getJuridica().getStrRazonSocial());
		confServEstructuraDetalle.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		confServEstructuraDetalle.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
		//Para obtener las sucursales y modalidades concatenadas
		//confServEstructuraDetalle.setEstructura(estructuraDetalle.getEstructura());		
		listaConfServEstructuraDetalle.add(confServEstructuraDetalle);
	}
	
	private void agregarListaConfServCredito(CreditoComp creditoComp) throws BusinessException{
		ConfServCredito confServCredito = new ConfServCredito();
		confServCredito.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
		confServCredito.setIntParaTipocreditoCod(creditoComp.getCredito().getId().getIntParaTipoCreditoCod());
		confServCredito.setIntSocioItemCredito(creditoComp.getCredito().getId().getIntItemCredito());
		confServCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		confServCredito.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		confServCredito.setStrDescripcion(creditoComp.getCredito().getStrDescripcion());
		confServCredito.setIntParaTipoCreditoEmpresa(creditoComp.getCredito().getIntParaTipoCreditoEmpresa());
		confServCredito.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
		listaConfServCredito.add(confServCredito);
	}
	
	private void agregarListaConfServCaptacion(Captacion captacion) throws BusinessException{
		ConfServCaptacion confServCaptacion = new ConfServCaptacion();
		confServCaptacion.getId().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
		confServCaptacion.setIntParaTipoCaptacionCod(captacion.getId().getIntParaTipoCaptacionCod());
		confServCaptacion.setIntSocioItem(captacion.getId().getIntItem());
		confServCaptacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		confServCaptacion.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		confServCaptacion.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
		confServCaptacion.setStrDescripcion(captacion.getStrDescripcion());
		listaConfServCaptacion.add(confServCaptacion);
	}	
	
	public void eliminarEstructuraDetalle(ActionEvent event){
		ConfServEstructuraDetalle confServEstructuraDetalle = (ConfServEstructuraDetalle)event.getComponent().getAttributes().get("item");
		listaConfServEstructuraDetalle.remove(confServEstructuraDetalle);
	}
	
	public void eliminarConfServEstructura(ActionEvent event){
		ConfServCredito confServCredito = (ConfServCredito)event.getComponent().getAttributes().get("item");
		listaConfServCredito.remove(confServCredito);
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (ConfServSolicitud)event.getComponent().getAttributes().get("item");
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
	
	public void eliminarRegistro(){
		boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			listaConfServSolicitud.remove(registroSeleccionado);
			//Para pruebas en QC ser usa el usuario 93
			//registroSeleccionado.setIntPersPersonaEliminaPk(Constante.PARAM_USUARIOSESION);
			registroSeleccionado.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
			registroSeleccionado.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
			registroSeleccionado.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			registroSeleccionado = confSolicitudFacade.modificarConfiguracion(registroSeleccionado);
		} catch (BusinessException e) {
			log.error(e.getMessage(),e);
		} catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(registroSeleccionado.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
			exito = Boolean.TRUE;
			mensaje = "El proceso de eliminación se realizo correctamente";			
		}else{
			mensaje = "Ocurrio un error durante el proceso de eliminación";			
		}
		mostrarMensaje(exito, mensaje);
		mostrarPanelInferior = Boolean.FALSE;
	}
	
	public void modificarRegistro(){
		try {
			cargarRegistro();	
			habilitarGrabar = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
		} catch (Exception e) {			
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarRegistro()throws BusinessException{
		try{
			confServSolicitudNuevo = registroSeleccionado;
			seleccionarRegistrarTipo();
			
			//Carga la tabla Tipo de relacion / listaConfServRol 
			List<ConfServRol> listaConfServRol = confSolicitudFacade.getListaConfServRolPorCabecera(confServSolicitudNuevo);
			List<Tabla> listaTipoRelacionAux = new ArrayList<Tabla>();
			for(Object o : listaTipoRelacion){
				Tabla tabla = (Tabla)o;
				for(ConfServRol confServRol : listaConfServRol){
					if(tabla.getIntIdDetalle().equals(confServRol.getId().getIntParaRolCod())){
						if(confServRol.getIntValor().equals(selecciona)){
							tabla.setChecked(Boolean.TRUE);
						}else{
							tabla.setChecked(Boolean.FALSE);
						}
						listaTipoRelacionAux.add(tabla);
						break;
					}
				}
			}
			listaTipoRelacion = listaTipoRelacionAux;
			
			//Carga la tabla de Tipo de Cuenta / listaConfServGrupoCta
			//List<ConfServGrupoCta> listaConfServGrupoCta = confSolicitudFacade.getListaConfServGrupoCtaPorCabecera(confServSolicitudNuevo);
			List<ConfServGrupoCta> listaConfServGrupoCta = confServSolicitudNuevo.getListaGrupoCta();
			List<Tabla> listaTipoCuentaAux = new ArrayList<Tabla>();				
			for(Object o : listaTipoCuenta){
				Tabla tabla = (Tabla)o;
				for(ConfServGrupoCta confServGrupoCta : listaConfServGrupoCta){
					if(tabla.getIntIdDetalle().equals(confServGrupoCta.getId().getIntParaTipoCuentaCod())){
						if(confServGrupoCta.getIntValor().equals(selecciona)){
							tabla.setChecked(Boolean.TRUE);
						}else{
							tabla.setChecked(Boolean.FALSE);
						}
						listaTipoCuentaAux.add(tabla);
					}
				}
			}
			listaTipoCuenta = listaTipoCuentaAux;
			
			//Carga la tabla de Estructuras Detalle / listaConfServEstructuraDetalle
			//ya viene cargado listaConfServEstructuraDetalle de la busqueda
			//listaConfServEstructura = confSolicitudFacade.getListaConfServEstructuraPorCabecera(confServSolicitudNuevo);
			listaConfServEstructuraDetalle = confServSolicitudNuevo.getListaEstructuraDetalle();
			List<ConfServEstructuraDetalle> listaConfServEstructuraDetalleAux = new ArrayList<ConfServEstructuraDetalle>();
			
			for(Object o : listaConfServEstructuraDetalle){
				EstructuraDetalle estructuraDetalle = null;
				ConfServEstructuraDetalle confServEstructuraDetalle = (ConfServEstructuraDetalle)o;
				estructuraDetalle = buscarListaEstructuraDetallePersiste(confServEstructuraDetalle.getIntItemCaso());
				if(estructuraDetalle != null){
					System.out.println("****************  PUERTA ********************");
					System.out.println("CODIGO --- "+estructuraDetalle.getId().getIntCodigo());
					System.out.println("NEVEL --- "+estructuraDetalle.getId().getIntNivel());
					System.out.println("CASO --- "+estructuraDetalle.getId().getIntCaso());
					
					confServEstructuraDetalle.setIntTipoModalidad(estructuraDetalle.getIntParaModalidadCod());
					confServEstructuraDetalle.setIntTipoSocio(estructuraDetalle.getIntParaTipoSocioCod());
					confServEstructuraDetalle.setStrRazonSocial(estructuraDetalle.getEstructura().getJuridica().getStrRazonSocial());
					listaConfServEstructuraDetalleAux.add(confServEstructuraDetalle);
				}
			}
			listaConfServEstructuraDetalle = listaConfServEstructuraDetalleAux;
			
			//Para el radiobutton de estructuras
			radioEstructura = radioEstructuraEntidad;
			habilitarAgregarEstructura = Boolean.TRUE;
			habilitarComboEstado = Boolean.TRUE;
			
			//Para el check de Indeterminado
			if(confServSolicitudNuevo.getDtHasta()==null){
				habilitarFechaFin = Boolean.FALSE;
				seleccionaIndeterminado = Boolean.TRUE;
			}else{
				habilitarFechaFin = Boolean.TRUE;
				seleccionaIndeterminado = Boolean.FALSE;
			}
			
			//Para el combo de suboperacion
			habilitarArchivoAdjunto = Boolean.FALSE;
			
			//Carga la tabla de requisitos / listaConofServDetalle
			//ya viene cargado listaConfServDetalle de la busqueda
			//listaConfServDetalle = confSolicitudFacade.getListaConfServDetallePorCabecera(confServSolicitudNuevo);				
			listaConfServDetalle = confServSolicitudNuevo.getListaDetalle();
			
			//Dependiendo del tipo de operacion del registro a cargar 			
			if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO)
			|| confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_ORDENCREDITO)
			|| confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD)
			|| confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO)){
				
				listaConfServCredito = confSolicitudFacade.getListaConfServCreditoPorCabecera(confServSolicitudNuevo);
				List<ConfServCredito> listaConfServCreditoAux = new ArrayList<ConfServCredito>();
				for(Object o : listaConfServCredito){
					ConfServCredito confServCredito = (ConfServCredito)o;
					for(CreditoComp creditoComp : listaCreditoCompPersiste){
						if(confServCredito.getIntSocioItemCredito().equals(creditoComp.getCredito().getId().getIntItemCredito())){
							confServCredito.setIntParaTipoCreditoEmpresa(creditoComp.getCredito().getIntParaTipoCreditoEmpresa());
							confServCredito.setStrDescripcion(creditoComp.getCredito().getStrDescripcion());
							listaConfServCreditoAux.add(confServCredito);
						}
					}
				}
				listaConfServCredito = listaConfServCreditoAux;
			
			}else if(confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO)
				||  confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_FONDORETIRO)
				||  confServSolicitudNuevo.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_AES)){
				
				listaConfServCaptacion = confSolicitudFacade.getListaConfServCaptacionPorCabecera(confServSolicitudNuevo);
				for(Object o : listaConfServCaptacion){
					ConfServCaptacion confServCaptacion = (ConfServCaptacion)o;
					CaptacionId captacionId = new CaptacionId();
					captacionId.setIntPersEmpresaPk(confServCaptacion.getId().getIntPersEmpresaPk());
					captacionId.setIntParaTipoCaptacionCod(confServCaptacion.getIntParaTipoCaptacionCod());
					captacionId.setIntItem(confServCaptacion.getIntSocioItem());
					confServCaptacion.setStrDescripcion(captacionFacade.listarCaptacionPorPK(captacionId).getStrDescripcion());
				}
			}
			
			
			seleccionarRegistrarTipo();
			registrarNuevo = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
	}
	
	
	public void manejarIndeterminado(){
		if(seleccionaIndeterminado==Boolean.TRUE){
			habilitarFechaFin = Boolean.FALSE;
			confServSolicitudNuevo.setDtHasta(null);
		}else{
			habilitarFechaFin = Boolean.TRUE;
		}
	}
	
	public void abrirConfServEstructuraDetalle(ActionEvent event){
		try {
			nombrePanelEstructura = (String)event.getComponent().getAttributes().get("nombrePanelEstructura");
			//Limpiar checks
			if(!listaEstructuraDetalle.isEmpty()){
				for(int i =0;i<listaEstructuraDetalle.size();i++){
					((EstructuraDetalle)(listaEstructuraDetalle.get(i))).setChecked(Boolean.FALSE);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirConfServCredito(ActionEvent event){
		try {
			nombrePanelProducto= (String)event.getComponent().getAttributes().get("nombrePanelProducto");
			//Limpiar checks
			if(listaCreditoComp!=null && !listaCreditoComp.isEmpty()){
				for(int i =0;i<listaCreditoComp.size();i++){
					((CreditoComp)(listaCreditoComp.get(i))).setChecked(Boolean.FALSE);
				}
			}			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}		
	}
	
	public void abrirConfServCaptacion(ActionEvent event){
		try {
			nombrePanelProducto= (String)event.getComponent().getAttributes().get("nombrePanelProducto");
			//Limpiar checks
			if(listaCaptacion!=null && !listaCaptacion.isEmpty()){
				for(int i =0;i<listaCaptacion.size();i++){
					((Captacion)(listaCaptacion.get(i))).setChecked(Boolean.FALSE);
				}
			}						
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}		
	}
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public ConfServSolicitud getConfServSolicitudNuevo() {
		return confServSolicitudNuevo;
	}
	public void setConfServSolicitudNuevo(ConfServSolicitud confServSolicitudNuevo) {
		this.confServSolicitudNuevo = confServSolicitudNuevo;
	}
	public ConfServSolicitud getConfServSolicitudFiltro() {
		return confServSolicitudFiltro;
	}
	public void setConfServSolicitudFiltro(ConfServSolicitud confServSolicitudFiltro) {
		this.confServSolicitudFiltro = confServSolicitudFiltro;
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
	public List getListaConfServSolicitud() {
		return listaConfServSolicitud;
	}
	public void setListaConfServSolicitud(List listaConfServSolicitud) {
		this.listaConfServSolicitud = listaConfServSolicitud;
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
	public List getListaTipoRelacion() {
		return listaTipoRelacion;
	}
	public void setListaTipoRelacion(List listaTipoRelacion) {
		this.listaTipoRelacion = listaTipoRelacion;
	}
	public List getListaTipoCuenta() {
		return listaTipoCuenta;
	}
	public void setListaTipoCuenta(List listaTipoCuenta) {
		this.listaTipoCuenta = listaTipoCuenta;
	}
	public boolean isHabilitarArchivoAdjunto() {
		return habilitarArchivoAdjunto;
	}
	public void setHabilitarArchivoAdjunto(boolean habilitarArchivoAdjunto) {
		this.habilitarArchivoAdjunto = habilitarArchivoAdjunto;
	}
	public List getListaConfServDetalle() {
		return listaConfServDetalle;
	}
	public void setListaConfServDetalle(List listaConfServDetalle) {
		this.listaConfServDetalle = listaConfServDetalle;
	}
	public ConfServDetalle getConfServDetalle() {
		return confServDetalle;
	}
	public void setConfServDetalle(ConfServDetalle confServDetalle) {
		this.confServDetalle = confServDetalle;
	}
	public boolean isHabilitarComboEstado() {
		return habilitarComboEstado;
	}
	public void setHabilitarComboEstado(boolean habilitarComboEstado) {
		this.habilitarComboEstado = habilitarComboEstado;
	}
	public Integer getRadioEstructura() {
		return radioEstructura;
	}
	public void setRadioEstructura(Integer radioEstructura) {
		this.radioEstructura = radioEstructura;
	}
	public boolean isHabilitarAgregarEstructura() {
		return habilitarAgregarEstructura;
	}
	public void setHabilitarAgregarEstructura(boolean habilitarAgregarEstructura) {
		this.habilitarAgregarEstructura = habilitarAgregarEstructura;
	}
	public Estructura getEstructuraFiltro() {
		return estructuraFiltro;
	}
	public void setEstructuraFiltro(Estructura estructuraFiltro) {
		this.estructuraFiltro = estructuraFiltro;
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public ConfServSolicitud getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(ConfServSolicitud registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public boolean isHabilitarFechaFin() {
		return habilitarFechaFin;
	}
	public void setHabilitarFechaFin(boolean habilitarFechaFin) {
		this.habilitarFechaFin = habilitarFechaFin;
	}
	public boolean isSeleccionaIndeterminado() {
		return seleccionaIndeterminado;
	}
	public void setSeleccionaIndeterminado(boolean seleccionaIndeterminado) {
		this.seleccionaIndeterminado = seleccionaIndeterminado;
	}
	public boolean isHabilitarTipoOperacion() {
		return habilitarTipoOperacion;
	}
	public void setHabilitarTipoOperacion(boolean habilitarTipoOperacion) {
		this.habilitarTipoOperacion = habilitarTipoOperacion;
	}
	public Integer getSelecciona() {
		return selecciona;
	}
	public void setSelecciona(Integer selecciona) {
		this.selecciona = selecciona;
	}
	public Integer getNoSelecciona() {
		return noSelecciona;
	}
	public void setNoSelecciona(Integer noSelecciona) {
		this.noSelecciona = noSelecciona;
	}
	public List<Tabla> getListaSuboperacion() {
		return listaSuboperacion;
	}
	public void setListaSuboperacion(List<Tabla> listaSuboperacion) {
		this.listaSuboperacion = listaSuboperacion;
	}
	public List getListaRequisito() {
		return listaRequisito;
	}
	public void setListaRequisito(List listaRequisito) {
		this.listaRequisito = listaRequisito;
	}
	public Integer getTipoCuentaFiltro() {
		return tipoCuentaFiltro;
	}
	public void setTipoCuentaFiltro(Integer tipoCuentaFiltro) {
		this.tipoCuentaFiltro = tipoCuentaFiltro;
	}
	public String getNombrePanelEstructura() {
		return nombrePanelEstructura;
	}
	public void setNombrePanelEstructura(String nombrePanelEstructura) {
		this.nombrePanelEstructura = nombrePanelEstructura;
	}
	public String getNombrePanelProducto() {
		return nombrePanelProducto;
	}
	public void setNombrePanelProducto(String nombrePanelProducto) {
		this.nombrePanelProducto = nombrePanelProducto;
	}
	public List getListaConfServCredito() {
		return listaConfServCredito;
	}
	public void setListaConfServCredito(List listaConfServCredito) {
		this.listaConfServCredito = listaConfServCredito;
	}
	public Credito getCreditoFiltro() {
		return creditoFiltro;
	}
	public void setCreditoFiltro(Credito creditoFiltro) {
		this.creditoFiltro = creditoFiltro;
	}
	public List<CreditoComp> getListaCreditoComp() {
		return listaCreditoComp;
	}
	public void setListaCreditoComp(List<CreditoComp> listaCreditoComp) {
		this.listaCreditoComp = listaCreditoComp;
	}
	public Integer getIdTipoSucursal() {
		return idTipoSucursal;
	}
	public void setIdTipoSucursal(Integer idTipoSucursal) {
		this.idTipoSucursal = idTipoSucursal;
	}
	public List getListaTipoCreditoEmpresa() {
		return listaTipoCreditoEmpresa;
	}
	public void setListaTipoCreditoEmpresa(List listaTipoCreditoEmpresa) {
		this.listaTipoCreditoEmpresa = listaTipoCreditoEmpresa;
	}
	public List getListaConfServCaptacion() {
		return listaConfServCaptacion;
	}
	public void setListaConfServCaptacion(List listaConfServCaptacion) {
		this.listaConfServCaptacion = listaConfServCaptacion;
	}
	public List getListaCaptacion() {
		return listaCaptacion;
	}
	public void setListaCaptacion(List listaCaptacion) {
		this.listaCaptacion = listaCaptacion;
	}
	public Integer getIntFiltroTipoBeneficio() {
		return intFiltroTipoBeneficio;
	}
	public void setIntFiltroTipoBeneficio(Integer intFiltroTipoBeneficio) {
		this.intFiltroTipoBeneficio = intFiltroTipoBeneficio;
	}
	public Integer getIntFiltroTipoMotivo() {
		return intFiltroTipoMotivo;
	}
	public void setIntFiltroTipoMotivo(Integer intFiltroTipoMotivo) {
		this.intFiltroTipoMotivo = intFiltroTipoMotivo;
	}
	public Integer getIntFiltroTipoConcepto() {
		return intFiltroTipoConcepto;
	}
	public void setIntFiltroTipoConcepto(Integer intFiltroTipoConcepto) {
		this.intFiltroTipoConcepto = intFiltroTipoConcepto;
	}
	public Integer getIntFiltroTipoCaptacion() {
		return intFiltroTipoCaptacion;
	}
	public void setIntFiltroTipoCaptacion(Integer intFiltroTipoCaptacion) {
		this.intFiltroTipoCaptacion = intFiltroTipoCaptacion;
	}
	public List getListaEstructuraDetalle() {
		return listaEstructuraDetalle;
	}
	public void setListaEstructuraDetalle(List listaEstructuraDetalle) {
		this.listaEstructuraDetalle = listaEstructuraDetalle;
	}
	public List getListaEstructuraDetallePersiste() {
		return listaEstructuraDetallePersiste;
	}
	public void setListaEstructuraDetallePersiste(
			List listaEstructuraDetallePersiste) {
		this.listaEstructuraDetallePersiste = listaEstructuraDetallePersiste;
	}
	public List getListaConfServEstructuraDetalle() {
		return listaConfServEstructuraDetalle;
	}
	public void setListaConfServEstructuraDetalle(
			List listaConfServEstructuraDetalle) {
		this.listaConfServEstructuraDetalle = listaConfServEstructuraDetalle;
	}	
}