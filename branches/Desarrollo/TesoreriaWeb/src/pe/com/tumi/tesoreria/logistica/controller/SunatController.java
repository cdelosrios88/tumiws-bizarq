package pe.com.tumi.tesoreria.logistica.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.common.util.MyUtilFormatoFecha;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.Tarifa;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.domain.TipoComprobante;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoRequisicion;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDoc;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatOrdenComDoc;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatOrdenComDocId;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorId;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionId;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;

public class SunatController {

	protected static Logger log = Logger.getLogger(SunatController.class);
	
	PersonaFacadeRemote 	personaFacade;
	EmpresaFacadeRemote		empresaFacade;
	LogisticaFacadeLocal	logisticaFacade;
	GeneralFacadeRemote		generalFacade;
	TablaFacadeRemote		tablaFacade;	
	
	private	DocumentoSunat		documentoSunatNuevo;
	private DocumentoSunat		documentoSunatFiltro;
	private DocumentoSunat		registroSeleccionado;
	private	OrdenCompra			ordenCompra;
	private TipoCambio			tipoCambio;
	private DocumentoSunat		documentoSunatLetra;
	private TipoCambio			tipoCambioLetra;
	private Persona				personaFiltro;
	
	private List<Sucursal>		listaSucursal;
	private List<DocumentoSunat>listaDocumentoSunat;
	private List<DocumentoRequisicion>	listaDocumentoRequisicion;
	private List<OrdenCompra>	listaOrdenCompra;
	private List<Tabla>			listaTablaTipoComprobante;
	private List<Tabla>			listaTablaTipoComprobanteLetrasYNotas;
	private List<DocumentoSunat>listaDocumentoSunatGrabar;
	private List<Persona>		listaPersonaFiltro;
	//Agregado por cdelosrios, 18/11/2013
	private List<DocumentoSunat> listaDocumentoSunatLetra;
	//Fin agregado por cdelosrios, 18/11/2013
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private Integer		intTipoPersona;
	private String		strFiltroTextoPersona;
	private String		strMensajeDetalle;
	private String		strMensajeAgregar;
	private Date		dtDesdeFiltro;
	private Date		dtHastaFiltro;
//	private String		strMensajeLetra;
	
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	
	//Agregado por cdelosrios, 12/11/2013
	private	Integer		SUCURSAL_USUARIO;
	private	Integer		SUBSUCURSAL_USUARIO;
	private	Integer		PERFIL_USUARIO;
	//Fin agregado por cdelosrios, 12/11/2013
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean poseePermiso;
	private boolean mostrarMensajeDetalle;
	private boolean	poseePermisoDescuento;
	private boolean poseePermisoIGV;
	private boolean modificarRegistro;
	private boolean agregarLetras;
	private boolean mostrarMensajeLetra;
	
	//Agregado por cdelosrios, 25/10/2013
	private boolean mostrarPanelDatosSunat;
	private boolean habilitarVerRegistro;
	//Fin agregado por cdelosrios, 25/10/2013
	
	//Agregado por cdelosrios, 08/10/2013
	private List<Tabla> listaTipoComprobante;
	//Fin agregado por cdelosrios, 08/10/2013
	
	//Agregado por cdelosrios, 15/10/2013
	private OrdenCompra ordenCompraFiltro;
	//Fin agregado por cdelosrios, 15/10/2013
	
	// Agregado por cdelosrios, 26/09/2013
	private Integer ADMINISTRADOR_ZONAL_CENTRAL = 3;
	private Integer ADMINISTRADOR_ZONAL_FILIAL = 5;
	private Integer ADMINISTRADOR_ZONAL_LIMA = 6;
	// Fin agregado por cdelosrios, 26/09/2013
	
	//Agregado por cdelosrios, 17/10/2013
	private BigDecimal bdMontoAdelantoEntregadoTotal;
	//Fin agregado por cdelosrios, 17/10/2013
	
	//Agregado por cdelosrios, 01/11/2013
	private DocumentoSunat itemDocumentoSunat;
	private String strSunat;
	//Fin agregado por cdelosrios, 01/11/2013
	
	//Agregado por cdelosrios, 15/11/2013
	private OrdenCompraDocumento itemOrdenCompraDocumento;
	//Fin agregado por cdelosrios, 15/11/2013
	
	//Autor: jchavez / Tarea: Creacion / Fecha: 24.10.2014
//	private String strMsgErrorAgregarDetalleDocSunat;
	private Integer intValidaCierrePorFechaEmision;
	private Boolean blnBloqueoDetalle;
	//Fin jchavez - 24.10.2014
	
	//Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
	private BigDecimal bdMontoIngresadoTotal;
	private String strMsgErrorMontoOrdenCompraDetalle;
	private Boolean poseePermisoIGVContable;
	private Boolean poseePermisoAfectoInafecto;
	private String strMsgErrorDetalleOtros;
	private Boolean poseePermisoPercepcion;
	//Fin jchavez - 26.10.2014
	
	//Autor: jchavez / Tarea: Creacion / Fecha: 29.10.2014
//	private String strMsgErrorFechaEmisionLetra;
	private Integer intValidaCierrePorFechaEmisionLetra;
	private List<DocumentoSunat> listaDocSunatValidosParaLetraDeCambio;
	private String strMsgErrorMontoDocSunatL;
	//Fin jchavez - 29.10.2014
	//Autor: jchavez / Tarea: Creacion / Fecha: 30.10.2014
	private List<DocumentoSunatDoc> listaDocSunatDetraccionPercepcion;
	//Fin jchavez - 30.10.2014
	
	private Integer intTipoComprobante;
	private Boolean mostrarMensajeExitoDocSunatDetalle;
	private Boolean mostrarMensajeErrorDocSunatDetalle;
	private String 	mensajeOperacionDocSunatDetalle;
	
	private Boolean mostrarMensajeExitoDocSunatLetra;
	private Boolean mostrarMensajeErrorDocSunatLetra;
	private String 	mensajeOperacionDocSunatLetra;
	
	private Boolean mostrarMensajeExitoDocSunatNota;
	private Boolean mostrarMensajeErrorDocSunatNota;
	private String 	mensajeOperacionDocSunatNota;
	
	private String strMsgErrorMontoDocSunatNota;
	private List<DocumentoSunat> listaDocSunatValidosParaNota;
	
	private DocumentoSunat documentoSunatNota;
//	private Boolean blnNotaGeneraDetraccion;
	private Integer intValidaCierrePorFechaEmisionNota;
	private List<DocumentoSunatDetalle> listaDocSunatDetDeDocSunatRel; 
	private TipoCambio tipoCambioNota;
	private List<Tabla> listaTipoComprobantePorProveedor;
	private List<DocumentoSunat> listaDocumentoSunatNota;
	private DocumentoSunat docSunatRelacionado;
	
	public SunatController(){
		cargarUsuario();

		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_DOCUMENTOSUNAT);
		if(usuario!=null && poseePermiso)	cargarValoresIniciales();
		else log.error("--Usuario obtenido es NULL o no posee permiso.");
	}
	
	public String getLimpiarSunat(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_DOCUMENTOSUNAT);
		if(usuario != null && poseePermiso){
			cargarValoresIniciales();
			deshabilitarPanelInferior();
		}	
		else 
			log.error("--Usuario obtenido es NULL o no posee permiso.");		
		return "";
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
		SUCURSAL_USUARIO = usuario.getSucursal().getId().getIntIdSucursal();
		SUBSUCURSAL_USUARIO = usuario.getSubSucursal().getId().getIntIdSubSucursal();
		PERFIL_USUARIO = usuario.getPerfil().getId().getIntIdPerfil();
	}
	
	public void cargarValoresIniciales(){
		try{
			personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			empresaFacade =  (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			logisticaFacade =  (LogisticaFacadeLocal) EJBFactory.getLocal(LogisticaFacadeLocal.class);
			generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			
			listaTablaTipoComprobante = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOCOMPROBANTE), "A");
			listaTablaTipoComprobanteLetrasYNotas = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOCOMPROBANTE), "B");
			documentoSunatFiltro = new DocumentoSunat();
			documentoSunatFiltro.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);
			listaDocumentoSunat = new ArrayList<DocumentoSunat>();
			listaSucursal = MyUtil.cargarListaSucursalConSubsucursalArea(EMPRESA_USUARIO);
			personaFiltro = new Persona();
			//Agregado por cdelosrios, 08/10/2013
			//Autor: jchavez / Tarea: Modificacion / Fecha 24.10.2014
			listaTipoComprobante = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOCOMPROBANTE), "C");
			
			ordenCompraFiltro = new OrdenCompra();
			bdMontoAdelantoEntregadoTotal = BigDecimal.ZERO;
			//Fin agregado por cdelosrios, 15/10/2013
			//Agregado por cdelosrios, 08/11/2013
			strMensajeAgregar = "";
			//Fin agregado por cdelosrios, 08/11/2013
			//Agregado por cdelosrios, 18/11/2013
			listaDocumentoSunatLetra = new ArrayList<DocumentoSunat>();
			//Fin agregado por cdelosrios, 18/11/2013
			//Autor: jchavez / Tarea: Creacion / Fecha: 24.10.2014
//			strMsgErrorAgregarDetalleDocSunat = "";
//			strMsgErrorFechaEmisionLetra = "";
			blnBloqueoDetalle = false;
			
			listaDocSunatValidosParaLetraDeCambio = new ArrayList<DocumentoSunat>();
			listaDocSunatDetraccionPercepcion = new ArrayList<DocumentoSunatDoc>();
			listaDocSunatValidosParaNota = new ArrayList<DocumentoSunat>();
			listaDocSunatDetDeDocSunatRel = new ArrayList<DocumentoSunatDetalle>();
//			cargarListaSucursal();
			listaOrdenCompra = new ArrayList<OrdenCompra>();
			listaTipoComprobantePorProveedor = new ArrayList<Tabla>();
			listaDocumentoSunatNota = new ArrayList<DocumentoSunat>();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 23.10.2014
	 * Funcionalidad: Realiza la búsqueda de Documentos Sunat registrados segun filtros ingresados
	 */
	public void buscar(){
		try{
			listaDocumentoSunat.clear();

			documentoSunatFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);			
			listaPersonaFiltro = new ArrayList<Persona>();
			if(personaFiltro.getStrEtiqueta()!=null && !personaFiltro.getStrEtiqueta().isEmpty()){
				listaPersonaFiltro = 
					personaFacade.buscarListaPersonaParaFiltro(personaFiltro.getIntEstadoCod(),	personaFiltro.getStrEtiqueta());				
				if(listaPersonaFiltro.isEmpty())return;
			}
			
			documentoSunatFiltro.setDtFiltroEmisionDesde(null);
			documentoSunatFiltro.setDtFiltroEmisionHasta(null);
			documentoSunatFiltro.setDtFiltroVencimientoDesde(null);
			documentoSunatFiltro.setDtFiltroVencimientoHasta(null);
			documentoSunatFiltro.setDtFiltroProgramacionDesde(null);
			documentoSunatFiltro.setDtFiltroProgramacionHasta(null);
			
			if(documentoSunatFiltro.getIntTipoFiltroFecha().equals(1)){
				documentoSunatFiltro.setDtFiltroEmisionDesde(dtDesdeFiltro);
				documentoSunatFiltro.setDtFiltroEmisionHasta(dtHastaFiltro);
			}else if(documentoSunatFiltro.getIntTipoFiltroFecha().equals(2)){
				documentoSunatFiltro.setDtFiltroVencimientoDesde(dtDesdeFiltro);
				documentoSunatFiltro.setDtFiltroVencimientoHasta(dtHastaFiltro);
			}else if(documentoSunatFiltro.getIntTipoFiltroFecha().equals(3)){
				documentoSunatFiltro.setDtFiltroProgramacionDesde(dtDesdeFiltro);
				documentoSunatFiltro.setDtFiltroProgramacionHasta(dtHastaFiltro);
			}

			if(documentoSunatFiltro.getIntParaTipoComprobante()!= null 
					&& documentoSunatFiltro.getIntParaTipoComprobante().equals(0)){
				documentoSunatFiltro.setIntParaTipoComprobante(null);
			}

			listaDocumentoSunat = logisticaFacade.buscarDocumentoSunat(documentoSunatFiltro, listaPersonaFiltro);
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 23.10.2014
	 * Funcionalidad: Setea en la variable "registroSeleccionado" de tipo DocumentoSunat() el registro seleccionado
	 * 				  tras la búsqueda.
	 * @param event
	 */
	public void seleccionarRegistro(ActionEvent event){
		try{
			log.info("Perfil Usuario: "+PERFIL_USUARIO);
			registroSeleccionado = (DocumentoSunat)event.getComponent().getAttributes().get("item");
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarModalidad(ActionEvent event){
		intTipoComprobante = Integer.valueOf(getRequestParameter("pCboTipoComprobanteDS"));
	}
	
	private boolean documentoValido(DocumentoSunatDoc documentoSunatDoc){
		if (documentoSunatDoc != null
		&& documentoSunatDoc.getBdMontoDocumento()!=null 
		&& documentoSunatDoc.getBdMontoDocumento().compareTo(new BigDecimal(0))!=0){
			return true;
		}
		return false;
	}
	
	private boolean detalleValido(DocumentoSunatDetalle documentoSunatDetalle){
		if(documentoSunatDetalle != null
		&& documentoSunatDetalle.getBdMontoTotal()!=null 
		&& documentoSunatDetalle.getBdMontoTotal().compareTo(new BigDecimal(0))!=0){
			return Boolean.TRUE;
		}		
		return Boolean.FALSE;
	}
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 29.10.2014
	 * Funcionalidad: Setea la llave del Documento Sunat adjunto ingresado a la variable "documentoSunatNuevo"
	 * 				  para su posterior grabación.
	 */
	public void aceptarAdjuntarDocumento(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			Archivo archivoDocumento = fileUploadController.getArchivoDocumento();
			
			documentoSunatNuevo.setIntParaTipo(archivoDocumento.getId().getIntParaTipoCod());
			documentoSunatNuevo.setIntItemArchivo(archivoDocumento.getId().getIntItemArchivo());
			documentoSunatNuevo.setIntItemHistorico(archivoDocumento.getId().getIntItemHistorico());
			documentoSunatNuevo.setArchivoDocumento(archivoDocumento);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 29.10.2014
	 * Funcionalidad: Setea la llave de la Letra de Cambio adjunta ingresada a la variable "documentoSunatLetra"
	 * 				  para su posterior grabación.
	 */
	public void aceptarAdjuntarDocumentoSunatLetra(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			Archivo archivoDocumento = fileUploadController.getArchivoDocumento();
			
			documentoSunatLetra.setIntParaTipo(archivoDocumento.getId().getIntParaTipoCod());
			documentoSunatLetra.setIntItemArchivo(archivoDocumento.getId().getIntItemArchivo());
			documentoSunatLetra.setIntItemHistorico(archivoDocumento.getId().getIntItemHistorico());
			documentoSunatLetra.setArchivoDocumento(archivoDocumento);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 29.10.2014
	 * Funcionalidad: Setea la llave de la Nota de Crédito y Débito adjunta ingresada a la variable "documentoSunatNotaCreditoYDebito"
	 * 				  para su posterior grabación.
	 */
	public void aceptarAdjuntarDocumentoSunatNota(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			Archivo archivoDocumento = fileUploadController.getArchivoDocumento();
			
			documentoSunatNota.setIntParaTipo(archivoDocumento.getId().getIntParaTipoCod());
			documentoSunatNota.setIntItemArchivo(archivoDocumento.getId().getIntItemArchivo());
			documentoSunatNota.setIntItemHistorico(archivoDocumento.getId().getIntItemHistorico());
			documentoSunatNota.setArchivoDocumento(archivoDocumento);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 29.10.2014
	 * Funcionalidad: Setea los valores por defecto al objeto "documentoSunatLetra" al abrir el popup para el ingreso de Letras de Cambio.
	 */
	public void abrirPopUpAgregarLetra(){
		strMsgErrorMontoDocSunatL = "";
		bdMontoIngresadoTotal = BigDecimal.ZERO;
		try{
			log.info("----------- abrirPopUpAgregarLetra() -----------");
			
			documentoSunatLetra = new DocumentoSunat();
			documentoSunatLetra.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			//DATOS DE LA ORDEN DE COMPRA...
			documentoSunatLetra.setIntPersEmpresaOrden(ordenCompra.getId().getIntPersEmpresa());
			documentoSunatLetra.setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
			documentoSunatLetra.setIntPersEmpresaRequisicion(ordenCompra.getDocumentoRequisicion().getRequisicion().getId().getIntPersEmpresa());
			documentoSunatLetra.setIntItemRequisicion(ordenCompra.getDocumentoRequisicion().getRequisicion().getId().getIntItemRequisicion());
			documentoSunatLetra.setIntParaMoneda(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
			documentoSunatLetra.setOrdenCompra(ordenCompra);
			
			documentoSunatLetra.setDtFechaEmision(new Date());
//			documentoSunatLetra.setTsFechaProvision(null);
			documentoSunatLetra.setTsFechaProvision(MyUtil.obtenerFechaActual());
			documentoSunatLetra.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
			documentoSunatLetra.setIntParaTipoComprobante(intTipoComprobante);

			documentoSunatLetra.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			documentoSunatLetra.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);		
			
			documentoSunatLetra.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			documentoSunatLetra.setIntPersPersonaUsuario(PERSONA_USUARIO);
			documentoSunatLetra.setIntSubIdSubsucursal(SUCURSAL_USUARIO);
			documentoSunatLetra.setIntSucuIdSucursal(SUBSUCURSAL_USUARIO);
			documentoSunatLetra.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			
			//DETALLE LETRA DE CAMBIO...
			documentoSunatLetra.setDetalleLetra(new DocumentoSunatDetalle());	
			documentoSunatLetra.getDetalleLetra().setIntParaTipoMoneda(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());		
			documentoSunatLetra.getDetalleLetra().setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						
			documentoSunatLetra.getDetalleLetra().setOrdenCompraDetalle(new OrdenCompraDetalle());
//			documentoSunatLetra.getDetalleLetra().getOrdenCompraDetalle().setSucursal(usuario.getSucursal());
			documentoSunatLetra.getDetalleLetra().setIntSucuIdSucursal(SUCURSAL_USUARIO);
			documentoSunatLetra.getDetalleLetra().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
//			seleccionarSucursalLetra();
			documentoSunatLetra.getDetalleLetra().setIntIdArea(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntIdArea());
						
			cargarTipoCambioLetra();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarSucursalLetra(){
		try{
			for(Sucursal sucursal : listaSucursal)
				if(sucursal.getId().getIntIdSucursal().equals(documentoSunatLetra.getDetalleLetra().getIntSucuIdSucursal()))
					documentoSunatLetra.getDetalleLetra().getOrdenCompraDetalle().setSucursal(sucursal);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarTipoCambio()throws Exception{
		Integer intTipoMoneda = ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda();
		tipoCambio = generalFacade.getTipoCambioPorMonedaYFecha(intTipoMoneda, documentoSunatNuevo.getDtFechaEmision(), EMPRESA_USUARIO);		
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 24.10.2014
	 * Funcionalidad: Obtiene el tipo de cambio con respecto a la fecha de emisión y tipo de moneda de la Letra de Cambio.
	 */
	public void cargarTipoCambioLetra(){
		try{
//			log.info("----------- cargarTipoCambioLetra() ----------");
//			log.info("moneda: "+documentoSunatLetra.getDetalleLetra().getIntParaTipoMoneda());
//			log.info("fecha: "+documentoSunatLetra.getDtFechaEmision());
//			log.info("empresa: "+EMPRESA_USUARIO);
//			tipoCambioLetra = generalFacade.getTipoCambioPorMonedaYFecha(documentoSunatLetra.getDetalleLetra().getIntParaTipoMoneda(), documentoSunatLetra.getDtFechaEmision(), EMPRESA_USUARIO);
			tipoCambioLetra = generalFacade.getTipoCambioPorMonedaYFecha(documentoSunatLetra.getIntParaMoneda(), documentoSunatLetra.getDtFechaEmision(), EMPRESA_USUARIO);
//			log.info("Tipo Cambio: "+tipoCambioLetra);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 24.10.2014
	 * Funcionalidad: Obtiene el tipo de cambio con respecto a la fecha de emisión y tipo de moneda de la Nota de Crédito o Débito.
	 */
	public void cargarTipoCambioNota(){
		try{
			tipoCambioNota = generalFacade.getTipoCambioPorMonedaYFecha(documentoSunatNota.getIntParaMoneda(), documentoSunatNota.getDtFechaEmision(), EMPRESA_USUARIO);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpBuscarOrdenCompra(){
		ordenCompraFiltro.getId().setIntItemOrdenCompra(null);
		listaOrdenCompra.clear();
		buscarOrdenCompra();
	}
	
	public void buscarOrdenCompra(){
		try{
			List<OrdenCompra> listaOrdenCompraTmp = new ArrayList<OrdenCompra>();
			ordenCompraFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			ordenCompraFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			if (ordenCompraFiltro.getId().getIntItemOrdenCompra()!=null) {
				listaOrdenCompra = logisticaFacade.buscarOrdenCompra(ordenCompraFiltro, null);
				for(OrdenCompra ordenCompra : listaOrdenCompra){
					cargarRequisicon(ordenCompra);
					Persona personaProveedor = personaFacade.devolverPersonaCargada(ordenCompra.getIntPersPersonaProveedor());
					ordenCompra.setPersonaProveedor(personaProveedor);
					ordenCompra.setIntParaTipoMoneda(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
					if (PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_CENTRAL)
							|| PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_FILIAL)
							|| PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_LIMA)) {
						if(ordenCompra.getDocumentoRequisicion().getRequisicion().getIntSucuIdSucursal().equals(SUCURSAL_USUARIO)){
							listaOrdenCompraTmp.add(ordenCompra);
							listaOrdenCompra = listaOrdenCompraTmp;
						}else {
							listaOrdenCompra.clear();
						}
					}
				}
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 30.10.2014
	 * Funcionalidad: Obtiene los Documentos Sunat válidos a los que se les puede ingresar Letras de Cambio.
	 */
	public void obtenerDocSunatValidosParaLetraDeCambio(){
		List<DocumentoSunat> lstDocSunatRelTmp = null;
		listaDocSunatValidosParaLetraDeCambio.clear();
		Boolean blnDetraccion = false;
		Boolean blnPercepcion = false;
		try {
			// Solo debe permitirse agregar Letras cuando ...
			// 1. La orden contable NO se encuentra cerrada.
			if (!ordenCompra.getIntParaEstadoOrden().equals(Constante.PARAM_T_ESTADOORDEN_CERRADO)) {
				lstDocSunatRelTmp = logisticaFacade.getListaDocumentoSunatPorOrdenCompra(ordenCompra);
				if (lstDocSunatRelTmp!=null && !lstDocSunatRelTmp.isEmpty()) {
					for (DocumentoSunat documentoSunat : lstDocSunatRelTmp) {
						// 2. Los documentos asociados a la orden Contable y que NO están "canceladas" sean facturas o boletas de venta
						if (documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)
								|| documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_BOLETA)) {
							if (!documentoSunat.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)) {
								// 3. Si la factura está relacionada con una detracción o percepción y esta NO se encuentra cancelada.
								if (documentoSunat.getDocDetraccion()==null || (documentoValido(documentoSunat.getDocDetraccion()))) {
									blnDetraccion = true;
								}
								if (blnDetraccion) {
									if (documentoSunat.getDocPercepcion()==null || (documentoValido(documentoSunat.getDocPercepcion()))) {
										blnPercepcion = true;
									}
									if (blnPercepcion) {
										listaDocSunatValidosParaLetraDeCambio.add(documentoSunat);
									}
								}
							}
						}
					}
				}
			}
			// 4. Si la detracción del Documento se encuentra en estado: canjeada NO se debe mostrar en la columna detracción
			if (listaDocSunatValidosParaLetraDeCambio!=null && !listaDocSunatValidosParaLetraDeCambio.isEmpty()) {
				for (DocumentoSunat docSunatValidos : listaDocSunatValidosParaLetraDeCambio) {
					if (documentoValido(docSunatValidos.getDocDetraccion()) && !docSunatValidos.getDocDetraccion().getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANJEADO)) {
						docSunatValidos.setBdMontoTotalSinDetraccion(docSunatValidos.getDetalleTotalGeneral().getBdMontoTotal()
								.subtract(docSunatValidos.getDocDetraccion().getBdMontoDocumento()));
					}else {
						docSunatValidos.setBdMontoTotalSinDetraccion(docSunatValidos.getDetalleTotalGeneral().getBdMontoTotal());
					}
					docSunatValidos.setBdMontoSaldoTemp(docSunatValidos.getBdMontoTotalSinDetraccion());
					
					if (documentoValido(docSunatValidos.getDocPercepcion())) {
						docSunatValidos.getDocPercepcion().setBdMontoSaldoTemp(docSunatValidos.getDocPercepcion().getBdMontoDocumento());
					}
				}
			}
		} catch (Exception e) {
			log.info(""+e.getMessage());
		}		
	}
	
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
	 * Funcionalidad: Realiza el calculo del monto a ingresar en el Documento Sunat 
	 */
	public void getBaseCalculo(ActionEvent event){
		strMsgErrorMontoOrdenCompraDetalle = "";
		bdMontoIngresadoTotal = BigDecimal.ZERO;
		try{
			for (OrdenCompraDetalle x : ordenCompra.getListaOrdenCompraDetalle()) {
				if (x.getBdMontoUsar()!=null) {
					if (x.getBdMontoSaldoTemp().compareTo(x.getBdMontoUsar())==-1) {
						strMsgErrorMontoOrdenCompraDetalle = "El monto ingresado supera el máximo permitodo para el detalle "+x.getId().getIntItemOrdenCompraDetalle();
					}else {
						bdMontoIngresadoTotal = bdMontoIngresadoTotal.add(x.getBdMontoUsar()!=null?x.getBdMontoUsar():BigDecimal.ZERO);
					}
				}
			}
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
	 * Funcionalidad: Realiza el calculo del monto a ingresar en el Documento Sunat Letra de Cambio
	 */
	public void getBaseCalculoDocSunatLetra(ActionEvent event){
		strMsgErrorMontoDocSunatL = "";
		bdMontoIngresadoTotal = BigDecimal.ZERO;
		try{
			for (DocumentoSunat x : listaDocSunatValidosParaLetraDeCambio) {
				if (x.getBdMontoAplicar()!=null) {
					if (x.getBdMontoSaldoTemp().compareTo(x.getBdMontoAplicar())==-1) {
						strMsgErrorMontoDocSunatL = "El monto ingresado supera el máximo permitodo para el Documento Sunat "+x.getId().getIntItemDocumentoSunat();
					}else {
						bdMontoIngresadoTotal = bdMontoIngresadoTotal.add(x.getBdMontoAplicar()!=null?x.getBdMontoAplicar():BigDecimal.ZERO);
					}
				}
				if (documentoValido(x.getDocPercepcion())) {
					if (x.getDocPercepcion().getBdMontoAplicar()!=null) {
						if (x.getDocPercepcion().getBdMontoSaldoTemp().compareTo(x.getDocPercepcion().getBdMontoAplicar())==-1) {
							strMsgErrorMontoDocSunatL = "El monto ingresado supera el máximo permitodo para la Percepción "+x.getDocPercepcion().getId().getIntItemDocumentoSunatDoc();
						}else {
							bdMontoIngresadoTotal = bdMontoIngresadoTotal.add(x.getDocPercepcion().getBdMontoAplicar()!=null?x.getDocPercepcion().getBdMontoAplicar():BigDecimal.ZERO);
						}
//						bdMontoIngresadoTotal = bdMontoIngresadoTotal.add(x.getDocPercepcion().getBdMontoAplicar()!=null?x.getDocPercepcion().getBdMontoAplicar():BigDecimal.ZERO);
					}
				}
			}
			documentoSunatLetra.getDetalleLetra().setBdMontoSinTipoCambio(bdMontoIngresadoTotal);
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 04.11.2014
	 * Funcionalidad: Realiza el calculo del monto a ingresar en el Documento Sunat Nota de Credito o Debito
	 */			
	public void getBaseCalculoDocSunatNota(ActionEvent event){
		strMsgErrorMontoDocSunatNota = "";
		bdMontoIngresadoTotal = BigDecimal.ZERO;
		try{
			for (DocumentoSunatDetalle x : listaDocSunatDetDeDocSunatRel) {
				if (x.getBdMontoAplicar()!=null) {
					if (x.getBdMontoSaldoTemp().compareTo(x.getBdMontoAplicar())==-1) {
						strMsgErrorMontoDocSunatNota = "El monto ingresado supera el máximo permitodo para la orden detalle "+x.getOrdenCompraDetalle().getId().getIntItemOrdenCompraDetalle();
					}else {
						bdMontoIngresadoTotal = bdMontoIngresadoTotal.add(x.getBdMontoAplicar()!=null?x.getBdMontoAplicar():BigDecimal.ZERO);
					}
				}
			}
			
			if (documentoSunatNota.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO)) {
				if (docSunatRelacionado.getDetalleTotalGeneral().getBdMontoTotal().compareTo(bdMontoIngresadoTotal)==-1) {
					strMsgErrorMontoDocSunatNota = "El monto ingresado supera el monto del Documento Sunat Relacionado";
				}
				documentoSunatNota.getDetalleNotaCredito().setBdMontoSinTipoCambio(bdMontoIngresadoTotal);
			}else if (documentoSunatNota.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO)) {
				documentoSunatNota.getDetalleNotaDebito().setBdMontoSinTipoCambio(bdMontoIngresadoTotal);
			}
			
			log.info("mensaje tras ingreso monto: "+strMsgErrorMontoDocSunatNota);
			
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	private OrdenCompra cargarOrdenCompra(OrdenCompra ordenCompraCargar)throws Exception{
		Persona personaProveedor = ordenCompraCargar.getPersonaProveedor();
		if(personaProveedor.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
			String strEtiqueta = ordenCompraCargar.getId().getIntItemOrdenCompra()+ " - "+
				personaProveedor.getNatural().getStrNombreCompleto()+" - DNI :"+personaProveedor.getDocumento().getStrNumeroIdentidad();
			personaProveedor.setStrEtiqueta(strEtiqueta);
		}else{
			String strEtiqueta = ordenCompraCargar.getId().getIntItemOrdenCompra()+ " - "+
			personaProveedor.getJuridica().getStrRazonSocial()+" - RUC :"+personaProveedor.getStrRuc();
			Juridica juridica = personaFacade.getJuridicaDetalladaPorIdPersona(personaProveedor.getJuridica().getIntIdPersona());
			personaProveedor.getJuridica().setListaTipoComprobante(juridica.getListaTipoComprobante());
			personaProveedor.setStrEtiqueta(strEtiqueta);
		}
		
		
		//Autor: jchavez / Tarea: Modificacion / Fecha: 10.11.2014
		List<OrdenCompra> lstOrdCom = logisticaFacade.buscarOrdenCompra(ordenCompra, null);
		ordenCompra.setListaOrdenCompraDetalle(lstOrdCom.get(0).getListaOrdenCompraDetalle());
		ordenCompra.setListaOrdenCompraDocumento(lstOrdCom.get(0).getListaOrdenCompraDocumento());
		//Fin jchavez - 10.11.2014
		
		ordenCompraCargar.setPersonaProveedor(personaProveedor);
		cargarEtiquetaOrdenCompra(ordenCompraCargar);
		if(ordenCompraCargar.getIntParaTipoDetraccion()!=null)
			ordenCompraCargar.setDetraccion(generalFacade.getDetraccionPorTipo(ordenCompraCargar.getIntParaTipoDetraccion()));
		

		bdMontoAdelantoEntregadoTotal = BigDecimal.ZERO;
		List<OrdenCompraDocumento> listaOrdenCompraDocumentoTemp = new ArrayList<OrdenCompraDocumento>();
		if (ordenCompraCargar.getListaOrdenCompraDocumento()!=null && !ordenCompraCargar.getListaOrdenCompraDocumento().isEmpty()) {
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompraCargar.getListaOrdenCompraDocumento()){
				if(ordenCompraDocumento.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)){
					listaOrdenCompraDocumentoTemp.add(ordenCompraDocumento);
				}
			}
		}
		ordenCompraCargar.getListaOrdenCompraDocumento().clear();
		ordenCompraCargar.getListaOrdenCompraDocumento().addAll(listaOrdenCompraDocumentoTemp);
		for(OrdenCompraDocumento ordenCompraDocumento : ordenCompraCargar.getListaOrdenCompraDocumento()){
			cargarSucursal(ordenCompraDocumento);
			ordenCompraDocumento.setBdMontoPagadoTemp(ordenCompraDocumento.getBdMontoPagado());
			ordenCompraDocumento.setBdMontoIngresadoTemp(ordenCompraDocumento.getBdMontoIngresado());
			ordenCompraDocumento.setBdMontoUsarTotal(new BigDecimal(0));
			ordenCompraDocumento.setBdMontoUsarDocumento(new BigDecimal(0));
			if(ordenCompraDocumento.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO) && ordenCompraDocumento.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)){
				bdMontoAdelantoEntregadoTotal = bdMontoAdelantoEntregadoTotal.add(ordenCompraDocumento.getBdMontoPagado());
			}
		}
		
		for(OrdenCompraDetalle ordenCompraDetalle : ordenCompraCargar.getListaOrdenCompraDetalle()){
			cargarSucursal(ordenCompraDetalle);
			ordenCompraDetalle.setBdMontoSaldoTemp(ordenCompraDetalle.getBdMontoSaldo());
		}
		
		ProveedorId proveedorId = new ProveedorId();
		proveedorId.setIntPersEmpresa(EMPRESA_USUARIO);
		proveedorId.setIntPersPersona(ordenCompraCargar.getPersonaProveedor().getIntIdPersona());
		ordenCompraCargar.setProveedor(logisticaFacade.getProveedorPorPK(proveedorId));
		cargarTipoComprobantePorProveedor();
		
		if(documentoSunatNuevo==null)	cargarDocumentoSunatNuevo();
		return ordenCompraCargar;
	}
	
	private void cargarEtiquetaOrdenCompra(OrdenCompra ordenCompraCargar)throws Exception{
		String strEtiqueta = "";
		strEtiqueta = "Estado: "+MyUtil.obtenerEtiquetaTabla(ordenCompraCargar.getIntParaEstadoOrden(), tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_ESTADOORDEN_INT));
	
		strEtiqueta = strEtiqueta + " Nro. Orden: " +ordenCompraCargar.getId().getIntItemOrdenCompra()
		+ " - " + MyUtil.obtenerEtiquetaTabla(
				ordenCompraCargar.getDocumentoRequisicion().getRequisicion().getIntParaTipoAprobacion(), 
				tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_APROBACION_INT));
		
		strEtiqueta = strEtiqueta
				+ " - " + MyUtil.formatoMonto(ordenCompraCargar.getBdMontoTotalDetalle())
				+ " - " + MyUtil.obtenerEtiquetaTabla(
						ordenCompraCargar.getIntParaEstadoOrden(), tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_TIPOMONEDA_INT));
		
		ordenCompraCargar.setStrEtiqueta(strEtiqueta);
	}
	
	public void abrirPopUpAgregarDetalleSunat(){
		blnBloqueoDetalle = false;
		strMsgErrorMontoOrdenCompraDetalle = "";
		bdMontoIngresadoTotal = BigDecimal.ZERO;
		mensajeOperacionDocSunatDetalle = "";
		try{
			List<OrdenCompraDocumento> listaTemp = new ArrayList<OrdenCompraDocumento>();

			validaDocSunatDetalle();
			if (!mostrarMensajeExitoDocSunatDetalle) {
				return;
			}
			
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				if( ordenCompraDocumento.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)){
					ordenCompraDocumento.setBdMontoUsar(null);
					listaTemp.add(ordenCompraDocumento);
				}
				ordenCompra.setListaOrdenCompraDocumento(listaTemp);
			}
			
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				ordenCompraDetalle.setBdMontoUsar(null);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void validaDocSunatDetalle(){
		try {
			/* Cuando se registra la fecha de emisión, se debe validar que el periodo del documento (año – mes) 
			 * se encuentre abierto. En el proceso de cierre de logística y en el cierre de contabilidad, 
			 * ambos cierres se encuentran grabados en las tablas: TES_CIERRELOGISTICAOPER y CON_CIERRECONTABILIDAD
			 * */
			//Autor: jchavez / Tarea: Modificación / Fecha: 24.10.2014
			intValidaCierrePorFechaEmision = logisticaFacade.getValidarCierreDocumento(Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(new Timestamp(documentoSunatNuevo.getDtFechaEmision().getTime()))));
			if (intValidaCierrePorFechaEmision.equals(2)) {
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "El período del Documento Sunat que desea ingresar tiene Cierre Logístico.");
				return;
			}else if (intValidaCierrePorFechaEmision.equals(3)){
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "El período del Documento Sunat que desea ingresar tiene Cierre Contable.");
				return;
			}
			//VALIDACION SERIE...
			if (documentoSunatNuevo.getStrSerieDocumento()==null || documentoSunatNuevo.getStrSerieDocumento().trim().equals("")) {
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "Debe ingresar un número de Serie.");
				return;
			}else if (documentoSunatNuevo.getStrSerieDocumento().trim().length()>6) {
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "El número de Serie no puede superar los 6 caracteres.");
				return;
			}
			//VALIDACION NUMERO...
			if (documentoSunatNuevo.getStrNumeroDocumento()==null || documentoSunatNuevo.getStrNumeroDocumento().trim().equals("")) {
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "Debe ingresar un número de Documento.");
				return;
			}
			//VALIDACION FECHA VENCIMIENTO...
			if (documentoSunatNuevo.getDtFechaVencimiento().equals(null)) {
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "Debe seleccionar una Fecha de Vencimiento.");
				return;
			}
			//VALIDACION PROGRAMACION DE PAGO...
			if (documentoSunatNuevo.getDtFechaPago().equals(null)) {
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "Debe seleccionar una Programación de Pago.");
				return;
			}
			//VALIDACION FECHA DE PROVISION...
			if (documentoSunatNuevo.getTsFechaProvision().equals(null)) {
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "Debe seleccionar una Fecha de Provisión.");
				return;
			}
			//VALIDACION GLOSA...
			if (documentoSunatNuevo.getStrGlosa().equals(null) || documentoSunatNuevo.getStrGlosa().trim().isEmpty()) {
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "Debe ingresar una Glosa.");
				return;
			}
			//VALIDACION ARCHIVO ADJUNTO...
			if (documentoSunatNuevo.getArchivoDocumento()==null){
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "Debe ingresar un Archivo adjunto.");
				return;
			}
			//VALIDACION FECHA EMISION Y VENCIMIENTO... convertirTimestampAStringPeriodo
			Integer intPeriodoFechaVencimiento = Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(new Timestamp(documentoSunatNuevo.getDtFechaVencimiento().getTime())));
			Integer intPeriodoFechaEmision = Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(new Timestamp(documentoSunatNuevo.getDtFechaEmision().getTime())));
			if (!intPeriodoFechaVencimiento.equals(intPeriodoFechaEmision)) {
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "La fecha de vencimiento debe ser igual a la fecha de emisión o encontrarse en el mismo período.");
				return;
			}			
			//VALIDACION FECHA EMISION Y PAGO...
			if(documentoSunatNuevo.getDtFechaPago().compareTo(documentoSunatNuevo.getDtFechaEmision())<0){
				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "La fecha de pago debe ser mayor a la fecha de emisión.");
				return;
			}
			//VALIDACION EXITOSA
			mostrarMensajeAgregarDocSunatDetalle(Boolean.TRUE, "");
		} catch (Exception e) {
			log.info("");
		}
	}
	
	private void validarDocumentoSunatLetra(){
		try {
			/* Cuando se registra la fecha de emisión, se debe validar que el periodo del documento (año – mes) 
			 * se encuentre abierto. En el proceso de cierre de logística y en el cierre de contabilidad, 
			 * ambos cierres se encuentran grabados en las tablas: TES_CIERRELOGISTICAOPER y CON_CIERRECONTABILIDAD
			 * */
			//Autor: jchavez / Tarea: Modificación / Fecha: 24.10.2014
			intValidaCierrePorFechaEmisionLetra = logisticaFacade.getValidarCierreDocumento(Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(new Timestamp(documentoSunatLetra.getDtFechaEmision().getTime()))));
//			if (intValidaCierrePorFechaEmisionLetra.equals(2)) {
//				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "El período del Documento Sunat que desea ingresar tiene Cierre Logístico.");
//				return;
//			}else if (intValidaCierrePorFechaEmisionLetra.equals(3)){
//				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "El período del Documento Sunat que desea ingresar tiene Cierre Contable.");
//				return;
//			}
			//VALIDACION SERIE...
			if (documentoSunatLetra.getStrSerieDocumento()==null || documentoSunatLetra.getStrSerieDocumento().trim().equals("")) {
				mostrarMensajeAgregarDocSunatLetra(Boolean.FALSE, "Debe ingresar un número de Serie.");
				return;
			}else if (documentoSunatLetra.getStrSerieDocumento().trim().length()>6) {
				mostrarMensajeAgregarDocSunatLetra(Boolean.FALSE, "El número de Serie no puede superar los 6 caracteres.");
				return;
			}
			//VALIDACION NUMERO...
			if (documentoSunatLetra.getStrNumeroDocumento()==null || documentoSunatLetra.getStrNumeroDocumento().trim().equals("")) {
				mostrarMensajeAgregarDocSunatLetra(Boolean.FALSE, "Debe ingresar un número de Documento.");
				return;
			}
			//VALIDACION FECHA DE EMISION...
			if (intValidaCierrePorFechaEmisionLetra.equals(2)) {
				mostrarMensajeAgregarDocSunatLetra(Boolean.FALSE, "El período del Documento Sunat que desea ingresar tiene Cierre Logístico.");
				return;
			}else if (intValidaCierrePorFechaEmisionLetra.equals(3)){
				mostrarMensajeAgregarDocSunatLetra(Boolean.FALSE, "El período del Documento Sunat que desea ingresar tiene Cierre Contable.");
				return;
			}		
			//VALIDACION FECHA VENCIMIENTO...
			if (documentoSunatLetra.getDtFechaVencimiento().equals(null)) {
				mostrarMensajeAgregarDocSunatLetra(Boolean.FALSE, "Debe seleccionar una Fecha de Vencimiento.");
				return;
			}
			//VALIDACION GLOSA...
			if (documentoSunatLetra.getStrGlosa().equals(null) || documentoSunatLetra.getStrGlosa().trim().isEmpty()) {
				mostrarMensajeAgregarDocSunatLetra(Boolean.FALSE, "Debe ingresar una Glosa.");
				return;
			}
			//VALIDACION MONTO...
			if (documentoSunatLetra.getDetalleLetra().getBdMontoSinTipoCambio()==null || documentoSunatLetra.getDetalleLetra().getBdMontoSinTipoCambio().compareTo(BigDecimal.ZERO)==0) {
				mostrarMensajeAgregarDocSunatLetra(Boolean.FALSE, "Debe ingresar monto de la Letra.");
			}		
			//VALIDACION ARCHIVO ADJUNTO...
//			if (documentoSunatLetra.getArchivoDocumento()==null){
//				mostrarMensajeAgregarDocSunatLetra(Boolean.FALSE, "Debe ingresar un Archivo adjunto.");
//				return;
//			}
			//VALIDACION FECHA EMISION Y VENCIMIENTO... convertirTimestampAStringPeriodo
//			Integer intPeriodoFechaVencimiento = Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(new Timestamp(documentoSunatNuevo.getDtFechaVencimiento().getTime())));
//			Integer intPeriodoFechaEmision = Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(new Timestamp(documentoSunatNuevo.getDtFechaEmision().getTime())));
//			if (!intPeriodoFechaVencimiento.equals(intPeriodoFechaEmision)) {
//				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "La fecha de vencimiento debe ser igual a la fecha de emisión o encontrarse en el mismo período.");
//				return;
//			}			
			//VALIDACION EXITOSA
			mostrarMensajeAgregarDocSunatLetra(Boolean.TRUE, "");
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	
	private void poseePermisoDescuento(){
		if(usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_LOGISTICA)
		|| usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_CONTABILIDAD)
		|| usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_ASISTENTELOGISTICA)
		|| usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_SISTEMAS)){
			poseePermisoDescuento = Boolean.TRUE;
		}else{
			poseePermisoDescuento = Boolean.FALSE;
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 26.10.2014
	 * Funcionalidad: Validación de permisos para la selección de IGV
	 */
	private void poseePermisoIGVContable(){
		if(usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_LOGISTICA)
		|| usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_ASISTENTELOGISTICA)
		|| usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_SISTEMAS)){
			poseePermisoIGVContable = Boolean.TRUE;
		}else{
			poseePermisoIGVContable = Boolean.FALSE;
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 26.10.2014
	 * Funcionalidad: Validación de permisos para la selección de Afecto IGV
	 */
	private void poseePermisoAfectoInafecto(){
		if(usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_LOGISTICA)
		|| usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_ASISTENTELOGISTICA)
		|| usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_SISTEMAS)){
			poseePermisoAfectoInafecto = Boolean.TRUE;
		}else{
			poseePermisoAfectoInafecto = Boolean.FALSE;
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 26.10.2014
	 * Funcionalidad: Validación de permisos para la selección de Percepción
	 */
	private void poseePermisoPercepcion(){
		if(usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_LOGISTICA)
		|| usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_CONTABILIDAD)
		|| usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_ASISTENTELOGISTICA)
		|| usuario.getPerfil().getId().getIntIdPerfil().equals(Constante.ID_PERFIL_SISTEMAS)){
			poseePermisoPercepcion = Boolean.TRUE;
		}else{
			poseePermisoPercepcion = Boolean.FALSE;
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
	
	public void mostrarMensajeAgregarDocSunatDetalle(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExitoDocSunatDetalle = true;
			mostrarMensajeErrorDocSunatDetalle = false;
			mensajeOperacionDocSunatDetalle = mensaje;
		}else{
			mostrarMensajeExitoDocSunatDetalle = false;
			mostrarMensajeErrorDocSunatDetalle = true;
			mensajeOperacionDocSunatDetalle = mensaje;
		}
	}
	
	public void mostrarMensajeAgregarDocSunatLetra(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExitoDocSunatLetra = true;
			mostrarMensajeErrorDocSunatLetra = false;
			mensajeOperacionDocSunatLetra = mensaje;
		}else{
			mostrarMensajeExitoDocSunatLetra = false;
			mostrarMensajeErrorDocSunatLetra = true;
			mensajeOperacionDocSunatLetra = mensaje;
		}
	}
	
	public void mostrarMensajeAgregarDocSunatNota(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExitoDocSunatNota = true;
			mostrarMensajeErrorDocSunatNota = false;
			mensajeOperacionDocSunatNota = mensaje;
		}else{
			mostrarMensajeExitoDocSunatNota = false;
			mostrarMensajeErrorDocSunatNota = true;
			mensajeOperacionDocSunatNota = mensaje;
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 26.10.2014
	 * Funcionalidad: Se agrega el Documento Sunat a la lista "listaDocumentoSunatGrabar" y se setean los valores para el ingreso de uno nuevo si se requiere.
	 */
	public void agregarDocumentoSunat(){
		try{
			strMensajeAgregar = "";

			if(listaDocumentoSunatGrabar!=null && !listaDocumentoSunatGrabar.isEmpty()){
				for (DocumentoSunat docSunat : listaDocumentoSunatGrabar) {
					if((documentoSunatNuevo.getStrSerieDocumento().trim().equals(docSunat.getStrSerieDocumento().trim())
						&& documentoSunatNuevo.getStrNumeroDocumento().trim().equals(docSunat.getStrNumeroDocumento().trim()))
						//&& !docSunat.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) 
							){
						strMensajeAgregar = "Ya se tiene registrada la misma serie y Nro. Documento"; return;
					}
				}
			}

			if(documentoSunatNuevo.isSeleccionaInafecto())	documentoSunatNuevo.setIntIndicadorInafecto(1);
			else documentoSunatNuevo.setIntIndicadorInafecto(0);
			
			if(documentoSunatNuevo.isSeleccionaIGVContable()) documentoSunatNuevo.setIntIndicadorIGV(1);
			else documentoSunatNuevo.setIntIndicadorIGV(0);
			
			if(documentoSunatNuevo.isSeleccionaPercepcion()) documentoSunatNuevo.setIntIndicadorPercepcion(1);
			else documentoSunatNuevo.setIntIndicadorPercepcion(0);
			
			//Autor: jchavez / Tarea: Creacion / Fecha: 29.10.2014
			documentoSunatNuevo.setListaDocumentoSunatOrdenComDoc(new ArrayList<DocumentoSunatOrdenComDoc>());
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				if(ordenCompraDocumento.getBdMontoUsarDocumento()!=null && ordenCompraDocumento.getBdMontoUsarDocumento().signum()>=0)
					documentoSunatNuevo.getListaDocumentoSunatOrdenComDoc().add(agregarDocumentoSunatOrdenComDoc(ordenCompraDocumento));
				ordenCompraDocumento.setBdMontoPagado(ordenCompraDocumento.getBdMontoPagadoTemp());
				ordenCompraDocumento.setBdMontoIngresado(ordenCompraDocumento.getBdMontoIngresadoTemp());				
				ordenCompraDocumento.setBdMontoUsarDocumento(new BigDecimal(0));
			}//Fin jchavez - 29.10.2014
			
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				ordenCompraDetalle.setBdMontoSaldo(ordenCompraDetalle.getBdMontoSaldoTemp());
			}

//			documentoSunatNuevo.setIntPersEmpresaRequisicion(ordenCompra.getDocumentoRequisicion().getRequisicion().getId().getIntPersEmpresa());
//			documentoSunatNuevo.setIntItemRequisicion(ordenCompra.getDocumentoRequisicion().getRequisicion().getId().getIntItemRequisicion());
			documentoSunatNuevo.setStrSerieDocumento(documentoSunatNuevo.getStrSerieDocumento().trim());
			documentoSunatNuevo.setStrNumeroDocumento(documentoSunatNuevo.getStrNumeroDocumento().trim());
//			documentoSunatNuevo.setIntSucuIdSucursal(SUCURSAL_USUARIO);
//			documentoSunatNuevo.setIntSubIdSubsucursal(SUBSUCURSAL_USUARIO);
			documentoSunatNuevo.setStrGlosa(documentoSunatNuevo.getStrGlosa().trim());
			log.info("log documentoSunatNuevo: "+documentoSunatNuevo);
			
			//Agregado por cdelosrios, 20/11/2013
			if(listaDocumentoSunatLetra!=null && !listaDocumentoSunatLetra.isEmpty()){
				for(DocumentoSunat docSunatLetra : listaDocumentoSunatLetra){
					if(docSunatLetra.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO)){
						documentoSunatNuevo.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
						break;
					}
				}
			}
			//Fin agregado por cdelosrios, 20/11/2013		
			
			listaDocumentoSunatGrabar.add(documentoSunatNuevo);
			if (documentoValido(documentoSunatNuevo.getDocDetraccion())) {
				cargarDocumentoSunatDoc(documentoSunatNuevo.getDocDetraccion(), Constante.PARAM_T_DOCUMENTOGENERAL_DETRACCION);
			}
			if (documentoValido(documentoSunatNuevo.getDocPercepcion())) {
				cargarDocumentoSunatDoc(documentoSunatNuevo.getDocPercepcion(), Constante.PARAM_T_DOCUMENTOGENERAL_PERCEPCION);
			}
			
			cargarDocumentoSunatNuevo();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 05.11.2014
	 * Funcionalidad: Se carga el Documento Sunat Doc a grabar en la lista "listaDocSunatDetraccionPercepcion".
	 * @param docSunatDoc
	 * @param intTipoDocumentoGeneral
	 * @throws Exception
	 */
	private void cargarDocumentoSunatDoc(DocumentoSunatDoc docSunatDoc, Integer intTipoDocumentoGeneral)throws Exception{
		DocumentoSunatDoc documentoSunatDoc = new DocumentoSunatDoc();
		documentoSunatDoc.setBdMontoDocumento(docSunatDoc.getBdMontoDocumento());
		documentoSunatDoc.setIntParaTipoDocumentoGeneral(intTipoDocumentoGeneral);
		documentoSunatDoc.setTsFechaRegistro(MyUtil.obtenerFechaActual());	
		documentoSunatDoc.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		documentoSunatDoc.setIntParaEstadoPagoDocSunat(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);		
		documentoSunatDoc.setIntTipoMoneda(documentoSunatNuevo.getIntParaMoneda());
		documentoSunatDoc.setBdTipoCambio(tipoCambio.getBdPromedio());
		listaDocSunatDetraccionPercepcion.add(documentoSunatDoc);
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 22.10.2014
	 * Funcionalidad: Setea los valores por defecto al objeto "documentoSunatNuevo" para la grabacion de Documento Sunat.
	 * @throws Exception
	 */
	private void cargarDocumentoSunatNuevo()throws Exception{
		Integer intParaTipoMoneda = ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda();
		documentoSunatNuevo = new DocumentoSunat();
		
		documentoSunatNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
		//DATOS DE LA ORDEN CONTABLE...
		documentoSunatNuevo.setIntPersEmpresaOrden(ordenCompra.getId().getIntPersEmpresa());
		documentoSunatNuevo.setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
		documentoSunatNuevo.setIntPersEmpresaRequisicion(ordenCompra.getDocumentoRequisicion().getRequisicion().getId().getIntPersEmpresa());
		documentoSunatNuevo.setIntItemRequisicion(ordenCompra.getDocumentoRequisicion().getRequisicion().getId().getIntItemRequisicion());
		//
		documentoSunatNuevo.setTsFechaProvision(MyUtil.obtenerFechaActual());
		documentoSunatNuevo.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
		documentoSunatNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		documentoSunatNuevo.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);
		
		documentoSunatNuevo.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
		documentoSunatNuevo.setIntPersPersonaUsuario(PERSONA_USUARIO);
		documentoSunatNuevo.setIntSubIdSubsucursal(SUCURSAL_USUARIO);
		documentoSunatNuevo.setIntSucuIdSucursal(SUBSUCURSAL_USUARIO);
		documentoSunatNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		
		documentoSunatNuevo.setDtFechaEmision(new Date());
		documentoSunatNuevo.setDtFechaVencimiento(new Date());
		documentoSunatNuevo.setIntParaMoneda(intParaTipoMoneda);			
		documentoSunatNuevo.setIntParaTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA);
		
		documentoSunatNuevo.setOrdenCompra(ordenCompra);
		
		//INICIALIZAMOS DOCUMENTO SUNAT DETALLE...
		documentoSunatNuevo.setDetalleSubTotal(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleDescuento(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleValorVenta(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleIGV(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleOtros(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleTotal(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleRetencion(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleTotalGeneral(new DocumentoSunatDetalle());

		//INICIALIZAMOS DOCUMENTO SUNAT DOC...
		documentoSunatNuevo.setDocPercepcion(new DocumentoSunatDoc());
		documentoSunatNuevo.setDocDetraccion(new DocumentoSunatDoc());

		cargarTipoCambio();

		//Solo para el caso de Detalle SubTotal se usará la sucursal de la roden de compra detalle, para el resto se usará la de login.
//		documentoSunatNuevo.getDetalleSubTotal().setIntSucuIdSucursal(SUCURSAL_USUARIO);
//		documentoSunatNuevo.getDetalleSubTotal().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleSubTotal().setIntParaTipoMoneda(intParaTipoMoneda);
				
		documentoSunatNuevo.getDetalleDescuento().setIntSucuIdSucursal(SUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleDescuento().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleDescuento().setIntParaTipoMoneda(intParaTipoMoneda);
		
		documentoSunatNuevo.getDetalleValorVenta().setIntSucuIdSucursal(SUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleValorVenta().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleValorVenta().setIntParaTipoMoneda(intParaTipoMoneda);
		
		documentoSunatNuevo.getDetalleIGV().setIntSucuIdSucursal(SUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleIGV().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleIGV().setIntParaTipoMoneda(intParaTipoMoneda);
		
		documentoSunatNuevo.getDetalleOtros().setIntSucuIdSucursal(SUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleOtros().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleOtros().setIntParaTipoMoneda(intParaTipoMoneda);
		
		documentoSunatNuevo.getDetalleTotal().setIntSucuIdSucursal(SUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleTotal().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleTotal().setIntParaTipoMoneda(intParaTipoMoneda);
		
		documentoSunatNuevo.getDetalleRetencion().setIntSucuIdSucursal(SUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleRetencion().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleRetencion().setIntParaTipoMoneda(intParaTipoMoneda);
		
		documentoSunatNuevo.getDetalleTotalGeneral().setIntSucuIdSucursal(SUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleTotalGeneral().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
		documentoSunatNuevo.getDetalleTotalGeneral().setIntParaTipoMoneda(intParaTipoMoneda);
		
		blnBloqueoDetalle = false;
	}
	
	private void cargarSucursal(OrdenCompraDocumento ordenCompraDocumento)throws Exception{
		for(Sucursal sucursal : listaSucursal){
			if(ordenCompraDocumento.getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())){
				ordenCompraDocumento.setSucursal(sucursal);
				for(Area area : sucursal.getListaArea()){
					if(ordenCompraDocumento.getIntIdArea().equals(area.getId().getIntIdArea())){
						ordenCompraDocumento.setArea(area);
						break;
					}
				}
				break;
			}
		}
	}	

	public void agregarDetalleSunat2(){
		try{			
			if(!validarDetalleSunat2()){
				mostrarMensajeDetalle = Boolean.TRUE;
				return;
			}
			mostrarMensajeDetalle = Boolean.FALSE;
			
			BigDecimal bdMontoAdelantarTotal = new BigDecimal(0);
			//Carga Orden Compra Documento ADELANTO ingresada...
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				if(ordenCompraDocumento.getBdMontoUsar()==null)	continue;
				//manejarAdelantoSunat(ordenCompraDocumento);
				ordenCompraDocumento.setBdMontoUsarDocumento(ordenCompraDocumento.getBdMontoUsarDocumento().add(ordenCompraDocumento.getBdMontoUsar()));
				ordenCompraDocumento.setBdMontoUsarTotal(ordenCompraDocumento.getBdMontoUsarTotal().add(ordenCompraDocumento.getBdMontoUsar()));
				ordenCompraDocumento.setBdMontoIngresadoTemp(ordenCompraDocumento.getBdMontoIngresadoTemp().add(ordenCompraDocumento.getBdMontoUsar()));
				bdMontoAdelantarTotal = bdMontoAdelantarTotal.add(ordenCompraDocumento.getBdMontoUsar());
			}
			
			//Carga Orden Compra Detalle ingresadas...
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				if(ordenCompraDetalle.getBdMontoUsar()==null) continue;
				ordenCompraDetalle.setBdMontoSaldoTemp(ordenCompraDetalle.getBdMontoSaldoTemp().subtract(ordenCompraDetalle.getBdMontoUsar()));
				agregarDocumentoDetalleSunat(ordenCompraDetalle);
			}
			
			calcularMontos();
			if (documentoSunatNuevo.getListaDocumentoSunatDetalle()!=null && !documentoSunatNuevo.getListaDocumentoSunatDetalle().isEmpty()) {
				blnBloqueoDetalle = true;
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	private boolean validarDetalleSunat2(){
		strMensajeDetalle="";
		
		BigDecimal bdMontoAdelantarTotal = new BigDecimal(0);
		for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
			if(ordenCompraDocumento.getBdMontoUsar()==null)continue;
			if(ordenCompraDocumento.getBdMontoPagadoTemp().compareTo(
				ordenCompraDocumento.getBdMontoIngresadoTemp().add(ordenCompraDocumento.getBdMontoUsar()))<0){
				strMensajeDetalle = "El monto a usar del documento "+ordenCompraDocumento.getId().getIntItemOrdenCompraDocumento()
				+" de adelanto es mayor al permitido.";
				return Boolean.FALSE;
			}
			bdMontoAdelantarTotal = bdMontoAdelantarTotal.add(ordenCompraDocumento.getBdMontoUsar());
		}
		
		BigDecimal bdMontoUsarTotal = new BigDecimal(0);
		for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
			if(ordenCompraDetalle.getBdMontoUsar()==null)continue;
			if(ordenCompraDetalle.getBdMontoSaldoTemp().compareTo(ordenCompraDetalle.getBdMontoUsar())<0){
				strMensajeDetalle = "El monto a usar del detalle "+ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle()
				+" es mayor al permitido.";
				return Boolean.FALSE;
			}
			bdMontoUsarTotal = bdMontoUsarTotal.add(ordenCompraDetalle.getBdMontoUsar());
		}
		
		if(bdMontoAdelantarTotal.compareTo(bdMontoUsarTotal)>0){
			strMensajeDetalle = "El monto total de adelanto es mayor al monto total de detalle a usar.";
			return Boolean.FALSE;
		}		
		return  Boolean.TRUE;
	}
	
	private void agregarDocumentoDetalleSunat(OrdenCompraDetalle ordenCompraDetalle)throws Exception{
		boolean seEncuentraAgregado = Boolean.FALSE;
		//Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
		if (documentoSunatNuevo.getListaDocumentoSunatDetalle()!=null && !documentoSunatNuevo.getListaDocumentoSunatDetalle().isEmpty()) {
			for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunatNuevo.getListaDocumentoSunatDetalle()){
				if(documentoSunatDetalle.getIntItemOrdenCompraDetalle().equals(ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle())){
					seEncuentraAgregado = Boolean.TRUE;
					documentoSunatDetalle.setBdMontoSinTipoCambio(documentoSunatDetalle.getBdMontoSinTipoCambio().add(
							ordenCompraDetalle.getBdMontoUsar()));
					break;
				}
			}
		}else {
			documentoSunatNuevo.setListaDocumentoSunatDetalle(new ArrayList<DocumentoSunatDetalle>());
		}

		if(seEncuentraAgregado)	return;
		
		DocumentoSunatDetalle documentoSunatDetalle = new DocumentoSunatDetalle();
		documentoSunatDetalle.getId().setIntPersEmpresa(EMPRESA_USUARIO);
		documentoSunatDetalle.setIntParaTipoCptoDocumentoSunat(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PRODUCTO);
		documentoSunatDetalle.setIntPersEmpresaOrdenCompra(ordenCompraDetalle.getId().getIntPersEmpresa());
		documentoSunatDetalle.setIntItemOrdenCompra(ordenCompraDetalle.getId().getIntItemOrdenCompra());
		documentoSunatDetalle.setIntItemOrdenCompraDetalle(ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle());
		documentoSunatDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		documentoSunatDetalle.setBdMontoSinTipoCambio(ordenCompraDetalle.getBdMontoUsar());
		documentoSunatDetalle.setOrdenCompraDetalle(ordenCompraDetalle);
		documentoSunatDetalle.setIntParaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
		documentoSunatDetalle.setIntSucuIdSucursal(ordenCompraDetalle.getIntSucuIdSucursal());
		documentoSunatDetalle.setIntSudeIdSubsucursal(ordenCompraDetalle.getIntSudeIdSubsucursal());
		documentoSunatDetalle.setIntIdArea(ordenCompraDetalle.getIntIdArea());
		documentoSunatDetalle.setStrDescripcion(ordenCompraDetalle.getStrDescripcion());
		documentoSunatDetalle.setIntPersEmpresaCuenta(ordenCompraDetalle.getIntPersEmpresaCuenta());
		documentoSunatDetalle.setIntContPeriodoCuenta(ordenCompraDetalle.getIntContPeriodoCuenta());
		documentoSunatDetalle.setStrContNumeroCuenta(ordenCompraDetalle.getStrContNumeroCuenta());
		documentoSunatNuevo.getListaDocumentoSunatDetalle().add(documentoSunatDetalle);
	}
	
	public void calcularMontos(){
		strMsgErrorDetalleOtros = "";
		try{
			//Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
			//Validaciones adicionales...
			if (documentoSunatNuevo.getDetalleOtros().getBdMontoTotal()!=null 
					&& (documentoSunatNuevo.getDetalleOtros().getBdMontoTotal().compareTo(BigDecimal.ONE)==1 
					|| documentoSunatNuevo.getDetalleOtros().getBdMontoTotal().compareTo(new BigDecimal(-1))==-1)) {
				strMsgErrorDetalleOtros = "El monto máximo a ingresar es +/- 1.00";
				return;
			}
			cargarTipoCambio();
			documentoSunatNuevo = logisticaFacade.calcularMontosDocumentoSunat(documentoSunatNuevo, tipoCambio);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 06.11.2014
	 * Funcionalidad: Agrega las Letras de cambio generadas por Documento Sunat.
	 */
	public void agregarDocumentoSunatLetra(){
		mensajeOperacionDocSunatLetra = "";
		try{
			validarDocumentoSunatLetra();
			if (!mostrarMensajeExitoDocSunatLetra) {
				return;
			}

//			if(!validarDocumentoSunatLetra()){
//				mostrarMensajeLetra = Boolean.TRUE;
//				return;
//			}
			
//			mostrarMensajeLetra = Boolean.FALSE;
			log.info(documentoSunatLetra.getDetalleLetra().getBdMontoSinTipoCambio());
			log.info(tipoCambioLetra.getBdPromedio());
			documentoSunatLetra.getDetalleLetra().setBdMontoTotal(
					documentoSunatLetra.getDetalleLetra().getBdMontoSinTipoCambio().multiply(tipoCambioLetra.getBdPromedio()));
			
			for (DocumentoSunat docSunat : listaDocSunatValidosParaLetraDeCambio) {
				if (docSunat.getBdMontoAplicar()!=null) {
					docSunat.setBdMontoSaldoTemp(docSunat.getBdMontoSaldoTemp().subtract(docSunat.getBdMontoAplicar()));
					docSunat.setBdMontoAplicar(null);
					if (documentoValido(docSunat.getDocPercepcion())) {
						if (docSunat.getDocPercepcion().getBdMontoAplicar()!=null) {
							docSunat.getDocPercepcion().setBdMontoSaldoTemp(docSunat.getDocPercepcion().getBdMontoSaldoTemp().subtract(docSunat.getDocPercepcion().getBdMontoAplicar()));
							docSunat.getDocPercepcion().setBdMontoAplicar(null);
						}
					}
					documentoSunatLetra.getListaDocSunatRelacionadosConLetraDeCambio().add(docSunat);
				}
			}
			
//			for(Area area : documentoSunatLetra.getDetalleLetra().getOrdenCompraDetalle().getSucursal().getListaArea()){
//				if(area.getId().getIntIdArea().equals(documentoSunatLetra.getDetalleLetra().getIntIdArea())){
//					documentoSunatLetra.getDetalleLetra().getOrdenCompraDetalle().setArea(area);
//				}
//			}
			//Modificado por cdelosrios, 18/11/2013
			//documentoSunatNuevo.getListaDocumentoSunatLetra().add(documentoSunatLetra);
			listaDocumentoSunatLetra.add(documentoSunatLetra);
			//Fin modificado por cdelosrios, 18/11/2013
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void agregarDocumentoSunatNota(){
		mensajeOperacionDocSunatNota = "";
		try{
			validarDocumentoSunatNota();
			if (!mostrarMensajeExitoDocSunatNota) {
				return;
			}
			
			if (documentoSunatNota.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO)) {
				documentoSunatNota.getDetalleNotaCredito().setBdMontoTotal(
						documentoSunatNota.getDetalleNotaCredito().getBdMontoSinTipoCambio().multiply(tipoCambioNota.getBdPromedio()));				
			}else if (documentoSunatNota.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO)) {
				documentoSunatNota.getDetalleNotaDebito().setBdMontoTotal(
						documentoSunatNota.getDetalleNotaDebito().getBdMontoSinTipoCambio().multiply(tipoCambioNota.getBdPromedio()));
			}
			
			documentoSunatNota.setIntPersEmpresaDocSunatEnlazado(docSunatRelacionado.getId().getIntPersEmpresa());
			documentoSunatNota.setIntItemDocumentoSunatEnlazado(docSunatRelacionado.getId().getIntItemDocumentoSunat());
			documentoSunatNota.setIntIndicadorIGV(docSunatRelacionado.getIntIndicadorIGV());
			//AGREGAR PERO CON EL DETALLE
//			for (DocumentoSunat docSunat : listaDocSunatValidosParaNota) {
//				if (docSunat.getBdMontoAplicar()!=null) {
//					documentoSunatNota.getListaDocSunatRelacionadosConLetraDeCambio().add(docSunat);
//				}
//			}
			documentoSunatNota.setListaDocumentoSunatDetalle(new ArrayList<DocumentoSunatDetalle>());
			for (DocumentoSunatDetalle docSunDet : listaDocSunatDetDeDocSunatRel) {
				if (docSunDet.getBdMontoAplicar()!=null && docSunDet.getBdMontoAplicar().compareTo(BigDecimal.ZERO)!=0) {
					docSunDet.setBdMontoSinTipoCambio(docSunDet.getBdMontoAplicar());
					docSunDet.setBdMontoSaldoTemp(docSunDet.getBdMontoSaldoTemp().subtract(docSunDet.getBdMontoAplicar()));
					docSunDet.setBdMontoAplicar(null);
					documentoSunatNota.getListaDocumentoSunatDetalle().add(docSunDet);
				}
			}
			
			for (DocumentoSunat docSunat : listaDocSunatValidosParaNota) {
				if (docSunat.getRbDocSunatSelected()!=null) {
					if (documentoSunatNota.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO)) {
						docSunat.setBdMontoSaldoTemp(docSunat.getDetalleTotalGeneral().getBdMontoTotal().subtract(documentoSunatNota.getDetalleNotaCredito().getBdMontoSinTipoCambio()));
					}else if (documentoSunatNota.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO)) {
						docSunat.setBdMontoSaldoTemp(docSunat.getDetalleTotalGeneral().getBdMontoTotal().subtract(documentoSunatNota.getDetalleNotaDebito().getBdMontoSinTipoCambio()));
					}
					
					documentoSunatNota.setIntPersEmpresaDocSunatEnlazado(docSunat.getId().getIntPersEmpresa());
					documentoSunatNota.setIntItemDocumentoSunatEnlazado(docSunat.getId().getIntItemDocumentoSunat());
					break;
				}
			}
			
			listaDocumentoSunatNota.add(documentoSunatNota);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	private void validarDocumentoSunatNota(){
		try {
			/* Cuando se registra la fecha de emisión, se debe validar que el periodo del documento (año – mes) 
			 * se encuentre abierto. En el proceso de cierre de logística y en el cierre de contabilidad, 
			 * ambos cierres se encuentran grabados en las tablas: TES_CIERRELOGISTICAOPER y CON_CIERRECONTABILIDAD
			 * */
			//Autor: jchavez / Tarea: Modificación / Fecha: 24.10.2014
			intValidaCierrePorFechaEmisionNota = logisticaFacade.getValidarCierreDocumento(Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(new Timestamp(documentoSunatNota.getDtFechaEmision().getTime()))));
//			if (intValidaCierrePorFechaEmisionLetra.equals(2)) {
//				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "El período del Documento Sunat que desea ingresar tiene Cierre Logístico.");
//				return;
//			}else if (intValidaCierrePorFechaEmisionLetra.equals(3)){
//				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "El período del Documento Sunat que desea ingresar tiene Cierre Contable.");
//				return;
//			}
			//VALIDACION SERIE...
			if (documentoSunatNota.getStrSerieDocumento()==null || documentoSunatNota.getStrSerieDocumento().trim().equals("")) {
				mostrarMensajeAgregarDocSunatNota(Boolean.FALSE, "Debe ingresar un número de Serie.");
				return;
			}else if (documentoSunatNota.getStrSerieDocumento().trim().length()>6) {
				mostrarMensajeAgregarDocSunatNota(Boolean.FALSE, "El número de Serie no puede superar los 6 caracteres.");
				return;
			}
			//VALIDACION NUMERO...
			if (documentoSunatNota.getStrNumeroDocumento()==null || documentoSunatNota.getStrNumeroDocumento().trim().equals("")) {
				mostrarMensajeAgregarDocSunatNota(Boolean.FALSE, "Debe ingresar un número de Documento.");
				return;
			}
			//VALIDACION FECHA DE EMISION...
			if (intValidaCierrePorFechaEmisionNota.equals(2)) {
				mostrarMensajeAgregarDocSunatNota(Boolean.FALSE, "El período del Documento Sunat que desea ingresar tiene Cierre Logístico.");
				return;
			}else if (intValidaCierrePorFechaEmisionNota.equals(3)){
				mostrarMensajeAgregarDocSunatNota(Boolean.FALSE, "El período del Documento Sunat que desea ingresar tiene Cierre Contable.");
				return;
			}		
			//VALIDACION FECHA VENCIMIENTO...
			if (documentoSunatNota.getDtFechaVencimiento().equals(null)) {
				mostrarMensajeAgregarDocSunatNota(Boolean.FALSE, "Debe seleccionar una Fecha de Vencimiento.");
				return;
			}
			//VALIDACION GLOSA...
			if (documentoSunatNota.getStrGlosa().equals(null) || documentoSunatNota.getStrGlosa().trim().isEmpty()) {
				mostrarMensajeAgregarDocSunatNota(Boolean.FALSE, "Debe ingresar una Glosa.");
				return;
			}
			//VALIDACION MONTO...
			if (documentoSunatNota.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO)) {
				if (documentoSunatNota.getDetalleNotaCredito().getBdMontoSinTipoCambio()==null || documentoSunatNota.getDetalleNotaCredito().getBdMontoSinTipoCambio().compareTo(BigDecimal.ZERO)==0) {
					mostrarMensajeAgregarDocSunatNota(Boolean.FALSE, "Debe ingresar monto para la Nota de Crédito.");
					return;
				}
			}else if (documentoSunatNota.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO)) {
				if (documentoSunatNota.getDetalleNotaDebito().getBdMontoSinTipoCambio()==null || documentoSunatNota.getDetalleNotaDebito().getBdMontoSinTipoCambio().compareTo(BigDecimal.ZERO)==0) {
					mostrarMensajeAgregarDocSunatNota(Boolean.FALSE, "Debe ingresar monto para la Nota de Débito.");
					return;
				}
			}				
					
			//VALIDACION ARCHIVO ADJUNTO...
//			if (documentoSunatNota.getArchivoDocumento()==null){
//				mostrarMensajeAgregarDocSunatNota(Boolean.FALSE, "Debe ingresar un Archivo adjunto.");
//				return;
//			}
			//VALIDACION FECHA EMISION Y VENCIMIENTO... convertirTimestampAStringPeriodo
//			Integer intPeriodoFechaVencimiento = Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(new Timestamp(documentoSunatNuevo.getDtFechaVencimiento().getTime())));
//			Integer intPeriodoFechaEmision = Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(new Timestamp(documentoSunatNuevo.getDtFechaEmision().getTime())));
//			if (!intPeriodoFechaVencimiento.equals(intPeriodoFechaEmision)) {
//				mostrarMensajeAgregarDocSunatDetalle(Boolean.FALSE, "La fecha de vencimiento debe ser igual a la fecha de emisión o encontrarse en el mismo período.");
//				return;
//			}			
			//VALIDACION EXITOSA
			mostrarMensajeAgregarDocSunatNota(Boolean.TRUE, "");
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void imprimirDocumentoSunat() {
		String strNombreReporte = "";
		Sucursal sucursal = null;
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Tabla tabla = null;
		Tabla tablaMoneda = null;
		String strUsuarioRegistra = null;
		Natural natural = null;
		
		try {
			/*itemDocumentoSunat = (DocumentoSunat) event.getComponent()
					.getAttributes().get("item");*/
			log.info("itemDocumentoSunat :" + itemDocumentoSunat);
			
			sucursal = empresaFacade.getSucursalPorId(registroSeleccionado.getIntSucuIdSucursal()==null?0:registroSeleccionado.getIntSucuIdSucursal());
			tabla = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOCOMPROBANTE), registroSeleccionado.getIntParaTipoComprobante());
			tablaMoneda = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOMONEDA), registroSeleccionado.getIntParaMoneda());
			natural = personaFacade.getNaturalPorPK(registroSeleccionado.getIntPersPersonaUsuario());
			if(natural!=null){
				strUsuarioRegistra = natural.getStrApellidoPaterno() + " " + natural.getStrApellidoMaterno() + " " + natural.getStrNombres();
			}
			parametro.put("P_FECHAREGISTRO", Constante.sdf.format(registroSeleccionado.getTsFechaRegistro()));
			parametro.put("P_HORAREGISTRO", new SimpleDateFormat("HH:mm:ss").format(registroSeleccionado.getTsFechaRegistro()));
			parametro.put("P_USUARIOREGISTRO", strUsuarioRegistra);
			
			parametro.put("P_ORDENCOMPRA", registroSeleccionado.getOrdenCompra().getId().getIntItemOrdenCompra());
			parametro.put("P_NROCOMPRA", registroSeleccionado.getId().getIntItemDocumentoSunat());
			parametro.put("P_FECHAPROVISION", Constante.sdf.format(registroSeleccionado.getTsFechaProvision()));
			parametro.put("P_PROVEEDOR", registroSeleccionado.getOrdenCompra().getPersonaProveedor().getJuridica().getStrRazonSocial());
			parametro.put("P_DETALLE", registroSeleccionado.getStrGlosa());
			
			parametro.put("P_REQUISICION", registroSeleccionado.getOrdenCompra().getIntItemRequisicion());
			parametro.put("P_NROASIENTO", registroSeleccionado.getIntPeriodoLibro() + "-" + registroSeleccionado.getIntCodigoLibro());
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			parametro.put("P_RUC", registroSeleccionado.getOrdenCompra().getPersonaProveedor().getStrRuc());
			parametro.put("P_TIPODOC", tabla.getStrDescripcion());
			parametro.put("P_FECHAEMISION", Constante.sdf.format(registroSeleccionado.getDtFechaEmision()));
			parametro.put("P_FECHAVENC", Constante.sdf.format(registroSeleccionado.getDtFechaVencimiento()));
			parametro.put("P_NRODOC", registroSeleccionado.getStrSerieDocumento() + "-" + registroSeleccionado.getStrNumeroDocumento());
			parametro.put("P_MONEDA", tablaMoneda.getStrDescripcion());
			parametro.put("P_TIPOCAMBIO", tipoCambio.getBdPromedio());
			parametro.put("P_SUBTOTAL", registroSeleccionado.getDetalleSubTotal().getBdMontoTotal());
			parametro.put("P_DESCUENTO", registroSeleccionado.getDetalleDescuento().getBdMontoTotal());
			parametro.put("P_VALORVENTA", registroSeleccionado.getDetalleValorVenta().getBdMontoTotal());
			parametro.put("P_IGV", registroSeleccionado.getDetalleIGV().getBdMontoTotal());
			parametro.put("P_OTROS", registroSeleccionado.getDetalleOtros().getBdMontoTotal());
			parametro.put("P_TOTAL", registroSeleccionado.getDetalleTotal().getBdMontoTotal());
			parametro.put("P_PERCEPCION", registroSeleccionado.getDocPercepcion().getBdMontoDocumento());
			parametro.put("P_DETRACCION", registroSeleccionado.getDocDetraccion().getBdMontoDocumento());
			parametro.put("P_RETENCION", registroSeleccionado.getDetalleRetencion().getBdMontoTotal());
			parametro.put("P_TOTALGENERAL", registroSeleccionado.getDetalleTotalGeneral().getBdMontoTotal());
			
			strNombreReporte = "documentoSunat";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaDocumentoSunat), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	//Agregado por cdelosrios, 08/11/2013
	public void onChangeTipoDocumento(){
		calcularMontos();
	}
	//Fin agregado por cdelosrios, 08/11/2013
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Autor
	 */
	public void abrirPopUpAgregarNota(){
		strMsgErrorMontoDocSunatNota = "";
		bdMontoIngresadoTotal = BigDecimal.ZERO;
		try{
			log.info("----------- abrirPopUpAgregarNota() -----------");
			if (listaDocSunatValidosParaNota!=null && !listaDocSunatValidosParaNota.isEmpty()) {
				for (DocumentoSunat docSunat : listaDocSunatValidosParaNota) {
					if (docSunat.getBdMontoSaldoTemp()==null){
						docSunat.setBdMontoSaldoTemp(docSunat.getDetalleTotalGeneral().getBdMontoTotal());
					}
					docSunat.setRbDocSunatSelected(null);
				}
			}else {
				obtenerDocSunatValidosParaNota();
				if (listaDocSunatValidosParaNota==null || listaDocSunatValidosParaNota.isEmpty()) {
					if (intTipoComprobante.equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO)) {
						log.info("No existen Documentos Sunat que se le puedan asociar Nota de Crédito.");
					}else if (intTipoComprobante.equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO)) {
						log.info("No existen Documentos Sunat que se le puedan asociar Nota de Débito.");
					}
					return;
				}
			}
			
			//LIMPIEZA DE FORMULARIO
//			for (DocumentoSunat docSun : listaDocSunatValidosParaNota) {
//				docSun.setRbDocSunatSelected(null);
//			}
			listaDocSunatDetDeDocSunatRel.clear();
			
			documentoSunatNota = new DocumentoSunat();
			documentoSunatNota.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			//DATOS DE LA ORDEN DE COMPRA...
			documentoSunatNota.setIntPersEmpresaOrden(ordenCompra.getId().getIntPersEmpresa());
			documentoSunatNota.setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
			documentoSunatNota.setIntPersEmpresaRequisicion(ordenCompra.getDocumentoRequisicion().getRequisicion().getId().getIntPersEmpresa());
			documentoSunatNota.setIntItemRequisicion(ordenCompra.getDocumentoRequisicion().getRequisicion().getId().getIntItemRequisicion());
			documentoSunatNota.setIntParaMoneda(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
			documentoSunatNota.setOrdenCompra(ordenCompra);
			
			documentoSunatNota.setDtFechaEmision(new Date());
			documentoSunatNota.setTsFechaProvision(MyUtil.obtenerFechaActual());
			documentoSunatNota.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
			documentoSunatNota.setIntParaTipoComprobante(intTipoComprobante);
			
			documentoSunatNota.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);
			documentoSunatNota.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			documentoSunatNota.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			documentoSunatNota.setIntPersPersonaUsuario(PERSONA_USUARIO);
			documentoSunatNota.setIntSubIdSubsucursal(SUCURSAL_USUARIO);
			documentoSunatNota.setIntSucuIdSucursal(SUBSUCURSAL_USUARIO);
			documentoSunatNota.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			
			
			//DETALLE NOTA DE CREDITO O DEBITO SEGUN EL CASO...
			documentoSunatNota.setDetalleNotaCredito(new DocumentoSunatDetalle());
			documentoSunatNota.setDetalleNotaDebito(new DocumentoSunatDetalle());	
			
			if (intTipoComprobante.equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO)) {
				documentoSunatNota.getDetalleNotaCredito().setIntParaTipoMoneda(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
				documentoSunatNota.getDetalleNotaCredito().setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				documentoSunatNota.getDetalleNotaCredito().setIntSucuIdSucursal(SUCURSAL_USUARIO);
				documentoSunatNota.getDetalleNotaCredito().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
				documentoSunatNota.getDetalleNotaCredito().setOrdenCompraDetalle(new OrdenCompraDetalle());
				documentoSunatNota.getDetalleNotaCredito().setIntIdArea(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntIdArea());
			}else if (intTipoComprobante.equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO)) {
				documentoSunatNota.getDetalleNotaDebito().setIntParaTipoMoneda(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
				documentoSunatNota.getDetalleNotaDebito().setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				documentoSunatNota.getDetalleNotaDebito().setIntSucuIdSucursal(SUCURSAL_USUARIO);
				documentoSunatNota.getDetalleNotaDebito().setIntSudeIdSubsucursal(SUBSUCURSAL_USUARIO);
				documentoSunatNota.getDetalleNotaDebito().setOrdenCompraDetalle(new OrdenCompraDetalle());
				documentoSunatNota.getDetalleNotaDebito().setIntIdArea(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntIdArea());
			}
			
			cargarTipoCambioNota();
			documentoSunatNota.setTipoCambio(tipoCambioNota);
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void obtenerDocSunatValidosParaNota(){
		List<DocumentoSunat> lstDocSunatRelTmp = null;
		listaDocSunatValidosParaNota.clear();
//		Boolean blnAgregaNotaCredito = false;
		Boolean blnGeneraDetraccion = false;
//		Boolean blnDetraccion = false;
//		Boolean blnPercepcion = false;
		//listaDocSunatValidosParaNota
		try {
			// Solo debe permitirse agregar Notas cuando ...
			// 1. La orden contable NO se encuentra cerrada.
			if (!ordenCompra.getIntParaEstadoOrden().equals(Constante.PARAM_T_ESTADOORDEN_CERRADO)) {
				lstDocSunatRelTmp = logisticaFacade.getListaDocumentoSunatPorOrdenCompra(ordenCompra);
				if (lstDocSunatRelTmp!=null && !lstDocSunatRelTmp.isEmpty()) {
					// Agrega Notas de Débito en todos los casos
					if (intTipoComprobante.equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO)) {
						blnGeneraDetraccion = true;
//						listaDocSunatValidosParaNota.addAll(lstDocSunatRelTmp);
					}
					// Solo debe permitirse agregar Notas de Crédito cuando ...
					else if (intTipoComprobante.equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO)) {
						for (DocumentoSunat documentoSunat : lstDocSunatRelTmp) {
							// 1. Si los documentos asociados a la orden Contable son facturas o boletas de venta y NO tienen enlazadas Letras de Cambio
							if ((documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)
									|| documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_BOLETA))
									&& (documentoSunat.getIntIndicadorLetras()==null || documentoSunat.getIntIndicadorLetras().equals(Constante.PARAM_T_INDICADORLETRAS_SINLETRAS))) {
								// a. Si los Documentos Sunat están "pendientes" 
//								if (documentoSunat.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_PENDIENTE)) {
//									// a.1 Si NO tiene documentos detraccion asociados 
									if (!documentoValido(documentoSunat.getDocDetraccion()) && !documentoValido(documentoSunat.getDocPercepcion())) {
										blnGeneraDetraccion = false;
									}
									// a.2 Si tiene documentos detraccion asociados
									else if (documentoValido(documentoSunat.getDocDetraccion())) {
										// a.2.1 Si el estado pago de la Detraccion esta "pendiente"
										if (documentoSunat.getDocDetraccion().getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_PENDIENTE)) {
											blnGeneraDetraccion = true;
										}
										// a.2.2 Si el estado pago de la Detraccion esta "pendiente"
										else if (documentoSunat.getDocDetraccion().getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)) {
											blnGeneraDetraccion = false;
										}
									}
//								}
//								// b. Si los Documentos Sunat están "canceladas"
//								else if (documentoSunat.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)) {
//									// a.1 Si NO tiene documentos detraccion asociados 
//									if (!documentoValido(documentoSunat.getDocDetraccion()) && !documentoValido(documentoSunat.getDocPercepcion())) {
//										blnNotaGeneraDetraccion = false;
//									}
//									// a.2 Si tiene documentos detraccion asociados
//									else if (documentoValido(documentoSunat.getDocDetraccion())) {
//										// a.2.1 Si el estado pago de la Detraccion esta "pendiente"
//										if (documentoSunat.getDocDetraccion().getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_PENDIENTE)) {
//											blnNotaGeneraDetraccion = true;
//										}
//										// a.2.2 Si el estado pago de la Detraccion esta "pendiente"
//										else if (documentoSunat.getDocDetraccion().getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)) {
//											blnNotaGeneraDetraccion = false;
//										}
//									}
//								}
								documentoSunat.setBdMontoSaldoTemp(documentoSunat.getDetalleTotalGeneral().getBdMontoTotal());
								documentoSunat.setBlnGeneraDetraccionNota(blnGeneraDetraccion);
								listaDocSunatValidosParaNota.add(documentoSunat);
							}
						}
					}					
				}
			}
			// 4. Si la detracción del Documento se encuentra en estado: canjeada NO se debe mostrar en la columna detracción
//			if (listaDocSunatValidosParaLetraDeCambio!=null && !listaDocSunatValidosParaLetraDeCambio.isEmpty()) {
//				for (DocumentoSunat docSunatValidos : listaDocSunatValidosParaLetraDeCambio) {
//					if (documentoValido(docSunatValidos.getDocDetraccion()) && !docSunatValidos.getDocDetraccion().getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANJEADO)) {
//						docSunatValidos.setBdMontoTotalSinDetraccion(docSunatValidos.getDetalleTotalGeneral().getBdMontoTotal()
//								.subtract(docSunatValidos.getDocDetraccion().getBdMontoDocumento()));
//					}else {
//						docSunatValidos.setBdMontoTotalSinDetraccion(docSunatValidos.getDetalleTotalGeneral().getBdMontoTotal());
//					}
//				}
//			}
//			if (listaDocSunatValidosParaNota!=null && !listaDocSunatValidosParaNota.isEmpty()) {
//				for (DocumentoSunat docSunat : listaDocSunatValidosParaNota) {
//					docSunat.setBdMontoSaldoTemp(docSunat.getDetalleTotalGeneral().getBdMontoTotal());
//				}
//			}
			
			log.info("Genera Detraccion: "+blnGeneraDetraccion);
		} catch (Exception e) {
			log.info(""+e.getMessage());
		}		
	}
	
	public void verDetalleDocSunatRelacionado(ActionEvent event){
		log.info("-------------------------------------Debugging verDetalleDocSunatRelacionado()-------------------------------------");
		log.info("pRdoRegistroDocumento: "+getRequestParameter("pRdoRegistroDocumento"));
		String pRegistroDocumento = getRequestParameter("pRdoRegistroDocumento");
		listaDocSunatDetDeDocSunatRel.clear();

		try {
			for (DocumentoSunat docSunat : listaDocSunatValidosParaNota) {
				if (docSunat.getId().getIntItemDocumentoSunat().equals(new Integer(pRegistroDocumento))) {
					docSunatRelacionado = docSunat;
					docSunat.setRbDocSunatSelected(docSunat.getId().getIntItemDocumentoSunat());	
					if (docSunat.getDetalleSubTotal().getListaDetalleSubTotal()!=null && !docSunat.getDetalleSubTotal().getListaDetalleSubTotal().isEmpty()) {
						listaDocSunatDetDeDocSunatRel.addAll(docSunat.getDetalleSubTotal().getListaDetalleSubTotal());
					}
				}else {
					docSunat.setRbDocSunatSelected(null);
				}
			}
			
			for (DocumentoSunatDetalle o : listaDocSunatDetDeDocSunatRel) {
				OrdenCompraDetalle ordComDet = new OrdenCompraDetalle();
				ordComDet.getId().setIntPersEmpresa(o.getIntPersEmpresaOrdenCompra());
				ordComDet.getId().setIntItemOrdenCompra(o.getIntItemOrdenCompra());
				ordComDet.getId().setIntItemOrdenCompraDetalle(o.getIntItemOrdenCompraDetalle());
				o.setOrdenCompraDetalle(logisticaFacade.getOrdenCompraDetallePorPk(ordComDet.getId()));
				o.setBdMontoSaldoTemp(o.getOrdenCompraDetalle().getBdPrecioTotal());
				for (Sucursal sucursal : listaSucursal) {
					if (o.getOrdenCompraDetalle().getIntSucuIdSucursal().equals(
							sucursal.getId().getIntIdSucursal())) {
						o.getOrdenCompraDetalle().setSucursal(sucursal);
						for (Area area : sucursal.getListaArea()) {
							if (o.getOrdenCompraDetalle().getIntIdArea().equals(
									area.getId().getIntIdArea())) {
								o.getOrdenCompraDetalle().setArea(area);
								break;
							}
						}
					}
				}
			}
//			//deseleccionar otros registros
//			for (DocumentoSunat docSunat : listaDocSunatValidosParaNota) {
//				if (!docSunat.getId().getIntItemDocumentoSunat().equals(new Integer(pRegistroDocumento))) {
//					
//				}
//			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}	
	}
	
//	private void cargarListaSucursal() throws Exception {
//		listaSucursal = empresaFacade
//				.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
//		for (Sucursal sucursal : listaSucursal)
//			sucursal.setListaArea(empresaFacade
//					.getListaAreaPorSucursal(sucursal));
//
//		// Ordenamos por nombre
//		Collections.sort(listaSucursal, new Comparator<Sucursal>() {
//			public int compare(Sucursal uno, Sucursal otro) {
//				return uno.getJuridica().getStrSiglas()
//						.compareTo(otro.getJuridica().getStrSiglas());
//			}
//		});
//	}
	
	
	
	
	
	
	
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
		ordenCompra = null;
		listaDocumentoSunatGrabar = null;
		documentoSunatNuevo = null;
		//Agregado por cdelosrios, 25/10/2013
		mostrarPanelDatosSunat = Boolean.FALSE;
		habilitarVerRegistro = Boolean.FALSE;
		strMensajeAgregar = "";
		//Fin agregado por cdelosrios, 25/10/2013
//		strMsgErrorAgregarDetalleDocSunat = "";
//		strMsgErrorFechaEmisionLetra = "";
		blnBloqueoDetalle = false;
		bdMontoIngresadoTotal = null;
		strMsgErrorMontoOrdenCompraDetalle = "";
		strMsgErrorMontoDocSunatL = "";
		strMsgErrorDetalleOtros = "";
		listaDocSunatDetraccionPercepcion.clear();
		//Autor: jchavez / Tarea: Creación / Fecha: 04.11.2014
		mostrarMensajeExitoDocSunatDetalle = false;
		mostrarMensajeErrorDocSunatDetalle = false;
		
		mostrarMensajeExitoDocSunatLetra = false;
		mostrarMensajeErrorDocSunatLetra = false;
		
		mostrarMensajeExitoDocSunatNota = false;
		mostrarMensajeErrorDocSunatNota= false;
	}	
	
	public void grabar(){
		log.info("--grabar");
		try{
//			if(listaDocumentoSunatGrabar!=null && !listaDocumentoSunatGrabar.isEmpty()){
//				if(listaDocumentoSunatGrabar.isEmpty()){
//					mostrarMensaje(Boolean.FALSE, "Debe agregar al menos un Documento Sunat."); return;
//				}
//
//				listaDocumentoSunatGrabar = logisticaFacade.grabarDocumentoSunat(listaDocumentoSunatGrabar,	ordenCompra);
//				listaDocSunatDetraccionPercepcion.clear();
//				for (DocumentoSunat x : listaDocumentoSunatGrabar) {
//					if (documentoValido(x.getDocDetraccion())) {
//						x.getDocDetraccion().setIntTipoMoneda(x.getIntParaMoneda());
//						x.getDocDetraccion().setBdTipoCambio(tipoCambio.getBdPromedio());
//						listaDocSunatDetraccionPercepcion.add(x.getDocDetraccion());
//					}
//					if (documentoValido(x.getDocPercepcion())) {
//						x.getDocPercepcion().setIntTipoMoneda(x.getIntParaMoneda());
//						x.getDocPercepcion().setBdTipoCambio(tipoCambio.getBdPromedio());
//						listaDocSunatDetraccionPercepcion.add(x.getDocPercepcion());
//					}
//				}
//				//Agregado por cdelosrios, 25/10/2013
//				mostrarPanelDatosSunat = Boolean.FALSE;
//				//Fin agregado por cdelosrios, 25/10/2013
//				buscar();
//				mostrarMensaje(Boolean.TRUE, "Se registró correctamente el Documento Sunat.");
			//Agregado por cdelosrios, 18/11/2013
//			}else {
				DocumentoSunat docSunatLetra = null;
				if(listaDocumentoSunatLetra!=null && !listaDocumentoSunatLetra.isEmpty()){
					docSunatLetra = new DocumentoSunat();
					docSunatLetra.setOrdenCompra(ordenCompra);
					docSunatLetra.setListaDocumentoSunatLetra(listaDocumentoSunatLetra);
					logisticaFacade.agregarDocumentoSunatLetra(docSunatLetra);
					mostrarMensaje(Boolean.TRUE, "Se registro correctamente la Letra de cambio.");
				}
//			}
			//Fin agregado por cdelosrios, 18/11/2013
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			modificarRegistro = Boolean.FALSE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro del Documento Sunat.");
			log.error(e.getMessage(),e);
		}
	}	
	
	//Agregado por cdelosrios, 01/11/2013
//	public void modificar(){
//		log.info("--modificar");
//		try{
//			if(listaDocumentoSunatGrabar!=null){
//				if(listaDocumentoSunatGrabar.isEmpty()){
//					mostrarMensaje(Boolean.FALSE, "Debe agregar al menos un Documento Sunat."); return;
//				}
//				//Modificado por cdelosrios, 18/11/2013
//				//listaDocumentoSunatGrabar = logisticaFacade.modificarDocumentoSunat(listaDocumentoSunatGrabar, ordenCompra);
//				listaDocumentoSunatGrabar = logisticaFacade.modificarDocumentoSunat(listaDocumentoSunatGrabar, ordenCompra, listaDocumentoSunatLetra);
//				//Fin modificado por cdelosrios, 18/11/2013
//				//Agregado por cdelosrios, 18/11/2013
//				/*if(documentoSunatNuevo.getListaDocumentoSunatLetra()!=null 
//						&& !documentoSunatNuevo.getListaDocumentoSunatLetra().isEmpty()){
//						documentoSunatNuevo.getId().setIntItemDocumentoSunat(
//								registroSeleccionado.getId().getIntItemDocumentoSunat());
//						logisticaFacade.agregarDocumentoSunatLetra(documentoSunatNuevo);
//				}*/
//				//Fin agregado por cdelosrios, 18/11/2013
//				mostrarPanelDatosSunat = Boolean.TRUE;
//				buscar();
//				mostrarMensaje(Boolean.TRUE, "Se modificó correctamente el Documento Sunat.");
//			}
//			habilitarGrabar = Boolean.FALSE;
//			deshabilitarNuevo = Boolean.TRUE;
//			modificarRegistro = Boolean.FALSE;
//		} catch (Exception e) {
//			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro del Documento Sunat.");
//			log.error(e.getMessage(),e);
//		}
//	}
	//Fin agregado por cdelosrios, 01/11/2013
	
	public void modificar(){
		log.info("--modificar");
//		DocumentoSunat docSunatLetra = null;
		try{
			if(listaDocumentoSunatGrabar!=null){
//				if(listaDocumentoSunatGrabar.isEmpty()){
//					mostrarMensaje(Boolean.FALSE, "Debe agregar al menos un Documento Sunat."); return;
//				}
//
//				listaDocumentoSunatGrabar = logisticaFacade.modificarDocumentoSunat(listaDocumentoSunatGrabar, ordenCompra, listaDocumentoSunatLetra);
			}

			if(listaDocumentoSunatLetra!=null && !listaDocumentoSunatLetra.isEmpty()){
				for (DocumentoSunat docSunatLetra : listaDocumentoSunatLetra) {
					if (docSunatLetra.getId().getIntItemDocumentoSunat()==null) {
//						docSunatLetra.setOrdenCompra(ordenCompra);
						docSunatLetra.setListaDocumentoSunatLetra(listaDocumentoSunatLetra);
						logisticaFacade.agregarDocumentoSunatLetra(docSunatLetra);
//						mostrarMensaje(Boolean.TRUE, "Se registro correctamente la Letra de cambio.");
					}
				}				
			}
			
			if (listaDocumentoSunatNota!=null && !listaDocumentoSunatNota.isEmpty()) {
				for (DocumentoSunat docSunatNota : listaDocumentoSunatNota) {
					if (docSunatNota.getId().getIntItemDocumentoSunat()==null) {
						docSunatNota.setListaDocumentoSunatNota(listaDocumentoSunatNota);
						logisticaFacade.agregarDocumentoSunatNota(docSunatNota);
					}
				}
			}
			
			mostrarPanelDatosSunat = Boolean.TRUE;
			buscar();
			mostrarMensaje(Boolean.TRUE, "Se modificó correctamente el Documento Sunat.");
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			modificarRegistro = Boolean.FALSE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro del Documento Sunat.");
			log.error(e.getMessage(),e);
		}
	}
	


	public void agregarLetras(){
		try{
			log.info("--agregarLetras");			
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;

			cargarRegistro();
			
			agregarLetras = Boolean.TRUE;
			modificarRegistro = Boolean.FALSE;			
			ocultarMensaje();			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void modificarRegistro(){
		DocumentoSunat documentoSunatLetra = null;
		try{
			log.info("--modificarRegistro");
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			
			cargarRegistro();
			
			//Agregado por cdelosrios, 14/11/2013
			if(PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_CENTRAL) 
				|| PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_FILIAL) 
				|| PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_LIMA)){
				agregarLetras = Boolean.FALSE;
			}else
				agregarLetras = Boolean.TRUE;
			//Fin agregado por cdelosrios, 14/11/2013
			
			modificarRegistro = Boolean.TRUE;
			ocultarMensaje();
			if(documentoSunatNuevo.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)
			//Modificado por cdelosrios, 18/11/2013
			//||(documentoSunatNuevo.getListaDocumentoSunatLetra()!=null && !documentoSunatNuevo.getListaDocumentoSunatLetra().isEmpty())
			||(listaDocumentoSunatLetra!=null && !listaDocumentoSunatLetra.isEmpty())
			//Fin modificado por cdelosrios, 18/11/2013
			|| documentoSunatNuevo.getIntItemEgresoGeneral()!=null)
				modificarRegistro = Boolean.FALSE;
			//Agregado por cdelosrios, 29/10/2013
			mostrarPanelDatosSunat = Boolean.TRUE;
			cargarDocumentoSunatPorOrdenCompra(registroSeleccionado);
			
			//Fin agregado por cdelosrios, 29/10/2013
			//Agregado por cdelosrios, 01/11/2013
			strSunat = Constante.MANTENIMIENTO_MODIFICAR;
			cargarDocumentoSunatNuevo();
			//Fin agregado por cdelosrios, 01/11/2013
			
			//Agregado por cdelosrios, 18/11/2013
			documentoSunatLetra = documentoSunatNuevo;
			cargarListaDocumentoSunatLetrasYNotas(documentoSunatLetra);
			cargarListaDocumentoSunatDoc();
			//Fin agregado por cdelosrios, 18/11/2013
			
			obtenerDocSunatValidosParaLetraDeCambio();
//			obtenerDocSunatValidosParaNota();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	//Agregado por cdelosrios, 01/11/2013
	public void verRegistro(){
		DocumentoSunat documentoSunatLetra = null;
		try{
			log.info("----- ver registro -----");			
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;

			cargarRegistro();
			
			//Agregado por cdelosrios, 14/11/2013
			if(PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_CENTRAL) 
				|| PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_FILIAL) 
				|| PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_LIMA)){
				agregarLetras = Boolean.FALSE;
			}else
				agregarLetras = Boolean.TRUE;
			//Fin agregado por cdelosrios, 14/11/2013
			modificarRegistro = Boolean.TRUE;
			ocultarMensaje();
			if(documentoSunatNuevo.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)
			//Modificado por cdelosrios, 18/11/2013
			//||(documentoSunatNuevo.getListaDocumentoSunatLetra()!=null && !documentoSunatNuevo.getListaDocumentoSunatLetra().isEmpty())
			||(listaDocumentoSunatLetra!=null && !listaDocumentoSunatLetra.isEmpty())
			//Fin modificado por cdelosrios, 18/11/2013
			|| documentoSunatNuevo.getIntItemEgresoGeneral()!=null)
				modificarRegistro = Boolean.FALSE;
			mostrarPanelDatosSunat = Boolean.FALSE;
			habilitarVerRegistro = Boolean.TRUE;
			cargarDocumentoSunatPorOrdenCompra(registroSeleccionado);
			cargarDocumentoSunatNuevo();
			
			//Agregado por cdelosrios, 18/11/2013
			documentoSunatLetra = documentoSunatNuevo;
//			documentoSunatLetra.setIntParaTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO);
			cargarListaDocumentoSunatLetrasYNotas(documentoSunatLetra);
			cargarListaDocumentoSunatDoc();
			//Fin agregado por cdelosrios, 18/11/2013
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void cargarDocumentoSunatPorOrdenCompra(DocumentoSunat docSunat){
		List<DocumentoSunat> listaDocSunat = null;
		try {
			listaDocSunat = logisticaFacade.getListaDocumentoSunatPorOrdenCompra(docSunat.getOrdenCompra());
			listaDocumentoSunatGrabar = listaDocSunat;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	
	
	

	
	public void verDocumentoSunat(ActionEvent event) {
		try {
			itemDocumentoSunat = (DocumentoSunat) event.getComponent()
					.getAttributes().get("item");
			log.info("itemDocumentoSunat :" + itemDocumentoSunat);
			
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntParaTipoCod(itemDocumentoSunat.getIntParaTipo());
			archivoId.setIntItemArchivo(itemDocumentoSunat.getIntItemArchivo());
			archivoId.setIntItemHistorico(itemDocumentoSunat.getIntItemHistorico());			
			itemDocumentoSunat.setArchivoDocumento(generalFacade.getArchivoPorPK(archivoId));
			
			//seleccionarSucursalDocumento();
			//strMensajeDocumento = "";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	//Fin agregado por cdelosrios, 01/11/2013
	
	//Agregado por cdelosrios, 15/11/2013
	public void verDocumentoAdelanto(ActionEvent event) {
		try {
			itemOrdenCompraDocumento = (OrdenCompraDocumento) event.getComponent()
					.getAttributes().get("item");
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	//Fin agregado por cdelosrios, 15/11/2013
	
	private void cargarRegistro()throws Exception{
		if (registroSeleccionado!=null) {
			documentoSunatNuevo = registroSeleccionado;
			ordenCompra = documentoSunatNuevo.getOrdenCompra();
		}else{
			listaDocumentoSunat.clear();
			listaPersonaFiltro = new ArrayList<Persona>();
			listaDocumentoSunat = logisticaFacade.buscarDocumentoSunat(documentoSunatFiltro, listaPersonaFiltro);
			if (listaDocumentoSunat!=null && !listaDocumentoSunat.isEmpty()) {
				for (DocumentoSunat o : listaDocumentoSunat) {
					if (o.getOrdenCompra().getId().getIntItemOrdenCompra().equals(ordenCompra.getId().getIntItemOrdenCompra())) {
						documentoSunatNuevo = o;
					}
				}
			}
		}		
			
		cargarRequisicon(ordenCompra);
		ordenCompra.setBdMontoTotalDetalle(new BigDecimal(0));
		for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle())
			ordenCompra.setBdMontoTotalDetalle(ordenCompra.getBdMontoTotalDetalle().add(ordenCompraDetalle.getBdPrecioTotal()));
		cargarOrdenCompra(ordenCompra);
		
		//Modificado por cdelosrios, 29/10/2013
		//listaDocumentoSunatGrabar = null;
		listaDocumentoSunatGrabar = new ArrayList<DocumentoSunat>();
		//Fin modificado por cdelosrios, 29/10/2013
		
		Tarifa tarifaIGV = logisticaFacade.cargarTarifaIGVDesdeDocumentoSunat(documentoSunatNuevo);
		cargarTipoCambio();
		
		for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunatNuevo.getListaDocumentoSunatDetalle()){
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				if(documentoSunatDetalle.getIntItemOrdenCompraDetalle().equals(ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle())){
					documentoSunatDetalle.setOrdenCompraDetalle(ordenCompraDetalle);
					break;
				}
			}
			documentoSunatDetalle = calcularIGV(documentoSunatDetalle, tarifaIGV);
		}
		//Modificado por cdelosrios, 18/11/2013
		//if(documentoSunatNuevo.getListaDocumentoSunatLetra()!=null){
		//	for(DocumentoSunat documentoSunatLetra : documentoSunatNuevo.getListaDocumentoSunatLetra())
		if(listaDocumentoSunatLetra!=null){
			for(DocumentoSunat documentoSunatLetra : listaDocumentoSunatLetra){
		//Fin modificado por cdelosrios, 18/11/2013
				cargarSucursal(documentoSunatLetra.getDetalleLetra());
			}
		}else{
			documentoSunatNuevo.setListaDocumentoSunatLetra(new ArrayList<DocumentoSunat>());
		}
	}

	private void cargarRequisicon(OrdenCompra ordenCompra)throws Exception{
		RequisicionId requisicionId = new RequisicionId();
		requisicionId.setIntPersEmpresa(ordenCompra.getIntPersEmpresaRequisicion());
		requisicionId.setIntItemRequisicion(ordenCompra.getIntItemRequisicion());
		ordenCompra.setDocumentoRequisicion(new DocumentoRequisicion());
		ordenCompra.getDocumentoRequisicion().setRequisicion(logisticaFacade.obtenerRequisicionPorId(requisicionId));		
	}
	
	
	
	public void seleccionarOrdenCompra(ActionEvent event){
		DocumentoSunat documentoSunat = null;
		try{
			ordenCompra = (OrdenCompra)event.getComponent().getAttributes().get("item");
			cargarOrdenCompra(ordenCompra);

			documentoSunat = new DocumentoSunat();
			documentoSunat.getId().setIntPersEmpresa(ordenCompra.getId().getIntPersEmpresa());
			documentoSunat.setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
			documentoSunat.setOrdenCompra(ordenCompra);
			cargarDocumentoSunatPorOrdenCompra(documentoSunat);
			if (listaDocumentoSunatGrabar!=null && !listaDocumentoSunatGrabar.isEmpty()) {
				modificarRegistro();
			}else{
				//Fin agregado por cdelosrios, 04/11/2013
				//Agregado por cdelosrios, 18/11/2013
				documentoSunat.setIntParaTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO);
				cargarListaDocumentoSunatLetrasYNotas(documentoSunat);
				//Fin agregado por cdelosrios, 18/11/2013
				
//				cargarListaDocumentoSunatDoc();
//				obtenerDocSunatValidosParaLetraDeCambio();
//				obtenerDocSunatValidosParaNota();
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarTipoComprobantePorProveedor(){
		listaTipoComprobantePorProveedor.clear();
		if (ordenCompra.getPersonaProveedor().getJuridica().getListaTipoComprobante()!=null && !ordenCompra.getPersonaProveedor().getJuridica().getListaTipoComprobante().isEmpty()) {
			for (TipoComprobante o : ordenCompra.getPersonaProveedor().getJuridica().getListaTipoComprobante()) {
				for (Tabla tipoComp : listaTipoComprobante) {
					if (o.getIntTipoComprobanteCod().equals(tipoComp.getIntIdDetalle())) {
						listaTipoComprobantePorProveedor.add(tipoComp);
						break;
					}
				}
			}
		}
		
	}
	
	private void cargarListaDocumentoSunatDoc(){
		try {
			for (DocumentoSunat docSunat : listaDocumentoSunatGrabar) {
				if (documentoValido(docSunat.getDocDetraccion())) {
					docSunat.getDocDetraccion().setIntTipoMoneda(documentoSunatNuevo.getIntParaMoneda());
					docSunat.getDocDetraccion().setBdTipoCambio(tipoCambio.getBdPromedio());
					listaDocSunatDetraccionPercepcion.add(docSunat.getDocDetraccion());
				}else if (documentoValido(docSunat.getDocPercepcion())) {
					docSunat.getDocPercepcion().setIntTipoMoneda(documentoSunatNuevo.getIntParaMoneda());
					docSunat.getDocPercepcion().setBdTipoCambio(tipoCambio.getBdPromedio());
					listaDocSunatDetraccionPercepcion.add(docSunat.getDocPercepcion());
				}
			}
			
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	
	
	//Agregado por cdelosrios, 18/11/2013
	private void cargarListaDocumentoSunatLetrasYNotas(DocumentoSunat documentoSunat){
		List<DocumentoSunat> listDocSunatLetra = null;
		List<DocumentoSunat> listDocSunatNotaDebito = null;
		List<DocumentoSunat> listDocSunatNotaCrebito = null;
		listaDocumentoSunatLetra = new ArrayList<DocumentoSunat>();
		try {
			documentoSunat.setIntParaTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO);
			listDocSunatLetra = logisticaFacade.getListDocumentoSunatPorOrdenCompraYTipoDocumento(documentoSunat);
			documentoSunat.setIntParaTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO);
			listDocSunatNotaDebito = logisticaFacade.getListDocumentoSunatPorOrdenCompraYTipoDocumento(documentoSunat);
			documentoSunat.setIntParaTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO);
			listDocSunatNotaCrebito = logisticaFacade.getListDocumentoSunatPorOrdenCompraYTipoDocumento(documentoSunat);
			
//			listaDocumentoSunatLetra.addAll(documentoSunat.getListaDocumentoSunatLetra());
			listaDocumentoSunatLetra.addAll(listDocSunatLetra);
			listaDocumentoSunatLetra.addAll(listDocSunatNotaDebito);
			listaDocumentoSunatLetra.addAll(listDocSunatNotaCrebito);
			
			if(listaDocumentoSunatLetra!=null){
				for(DocumentoSunat documentoSunatLetra : listaDocumentoSunatLetra){
					cargarSucursal(documentoSunatLetra.getDetalleLetra());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	//Fin agregado por cdelosrios, 18/11/2013
	
	
		

	
	
	
	
	private void cargarSucursal(OrdenCompraDetalle ordenCompraDetalle)throws Exception{
		for(Sucursal sucursal : listaSucursal){
			if(ordenCompraDetalle.getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())){
				ordenCompraDetalle.setSucursal(sucursal);
				for(Area area : sucursal.getListaArea()){
					if(ordenCompraDetalle.getIntIdArea().equals(area.getId().getIntIdArea())){
						ordenCompraDetalle.setArea(area);
						break;
					}
				}
				break;
			}
		}
	}
	
	private void cargarSucursal(DocumentoSunatDetalle documentoSunatDetalle)throws Exception{
		for(Sucursal sucursal : listaSucursal){
			if(documentoSunatDetalle.getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())){
				documentoSunatDetalle.setOrdenCompraDetalle(new OrdenCompraDetalle());
				documentoSunatDetalle.getOrdenCompraDetalle().setSucursal(sucursal);
				for(Area area : sucursal.getListaArea()){
					if(documentoSunatDetalle.getIntIdArea().equals(area.getId().getIntIdArea())){
						documentoSunatDetalle.getOrdenCompraDetalle().setArea(area);
						break;
					}
				}
			}
		}
	}
	
	public void eliminarRegistro(){
		try{
			cargarUsuario();
			DocumentoSunat documentoSunatEliminar = registroSeleccionado;
			
			if(documentoSunatEliminar.getIntItemEgresoGeneral()!=null){
				mostrarMensaje(Boolean.FALSE, "No se puede eliminar la orden de compra seleccionada, se encuentra enlazada a un egreso.");
				return;
			}
			
			documentoSunatEliminar.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			documentoSunatEliminar.setTsFechaAnula(MyUtil.obtenerFechaActual());
			documentoSunatEliminar.setIntPersEmpresaAnula(EMPRESA_USUARIO);
			documentoSunatEliminar.setIntPersPersonaAnula(PERSONA_USUARIO);
			
			logisticaFacade.modificarDocumentoSunatDirecto(documentoSunatEliminar);
			buscar();
			mostrarMensaje(Boolean.TRUE, "Se eliminó correctamente la orden de compra.");
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la eliminación de la orden de compra.");
			log.error(e.getMessage(),e);
		}
	}
	
	public void habilitarPanelInferior(){
		try{
			cargarUsuario();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			
			ordenCompra = null;
			modificarRegistro = Boolean.FALSE;
			//Agregado por cdelosrios, 14/11/2013
			if(PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_CENTRAL) 
				|| PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_FILIAL) 
				|| PERFIL_USUARIO.equals(ADMINISTRADOR_ZONAL_LIMA)){
				agregarLetras = Boolean.FALSE;
			}else
				agregarLetras = Boolean.TRUE;
			//Fin agregado por cdelosrios, 14/11/2013
			
			poseePermisoDescuento();
			poseePermisoIGVContable();
			poseePermisoAfectoInafecto();
			poseePermisoPercepcion();
			listaDocumentoSunatGrabar = new ArrayList<DocumentoSunat>();
			habilitarGrabar = Boolean.TRUE;
			//Agregado por cdelosrios, 26/10/2013
			mostrarPanelDatosSunat = Boolean.TRUE;
			habilitarVerRegistro = Boolean.FALSE;
			strSunat = Constante.MANTENIMIENTO_GRABAR;
			//Fin agregado por cdelosrios, 26/10/2013
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	

	


	

	

	

	
	
		
	

	

	

	
	private DocumentoSunatDetalle calcularIGV(DocumentoSunatDetalle documentoSunatDetalle, Tarifa tarifaIGV){
		OrdenCompraDetalle ordenCompraDetalle = documentoSunatDetalle.getOrdenCompraDetalle();
		log.info(documentoSunatDetalle.getBdMontoSinTipoCambio());
		log.info(tipoCambio.getBdPromedio());
		//Agregado por cdelosrios, 05/11/2013
		/*DocumentoSunat documentoSunat = new DocumentoSunat();
		documentoSunat.getId().setIntPersEmpresa(documentoSunatDetalle.getId().getIntPersEmpresa());
		documentoSunat.getId().setIntItemDocumentoSunat(documentoSunatDetalle.getId().getIntItemDocumentoSunat());
		try {
			documentoSunat = logisticaFacade.getDocumentoSunat(documentoSunat);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
		}*/
		if(documentoSunatNuevo.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)){
		//Fin agregado por cdelosrios, 05/11/2013
			
			if(documentoSunatDetalle.getId().getIntItemDocumentoSunat()==null)
				documentoSunatDetalle.setBdMontoTotal(documentoSunatDetalle.getBdMontoSinTipoCambio().multiply(tipoCambio.getBdPromedio()));				
			
			if(ordenCompraDetalle.getIntAfectoIGV().equals(new Integer(1))&& !documentoSunatNuevo.isSeleccionaInafecto()){
				documentoSunatDetalle.setBdMontoIGV(
						documentoSunatDetalle.getBdMontoTotal().multiply(tarifaIGV.getBdValor().divide(new BigDecimal(100))));
			}else{
				documentoSunatDetalle.setBdMontoIGV(new BigDecimal(0));
			}
			documentoSunatDetalle.setBdMontoSinIGV(documentoSunatDetalle.getBdMontoTotal().subtract(documentoSunatDetalle.getBdMontoIGV()));
		//Agregado por cdelosrios, 05/11/2013
		} else {
			documentoSunatDetalle.setBdMontoTotal(BigDecimal.ZERO);
			documentoSunatDetalle.setBdMontoIGV(BigDecimal.ZERO);
			documentoSunatDetalle.setBdMontoSinIGV(BigDecimal.ZERO);
		}
		//Fin agregado por cdelosrios, 05/11/2013
		return documentoSunatDetalle;
	}
	

	

	
	//Autor: jchavez / Tarea: Se comenta dado que la tabla AdelantoSunat SE ELIMINÓ / Fecha: 17.10.2014
//	private AdelantoSunat agregarAdelantoSunat(OrdenCompraDocumento ordenCompraDocumento){
//		AdelantoSunat adelantoSunat = new AdelantoSunat();
//		adelantoSunat.getId().setIntPersEmpresa(EMPRESA_USUARIO);
//		adelantoSunat.setOrdenCompraDocumentoId(ordenCompraDocumento.getId());
//		adelantoSunat.setBdMonto(ordenCompraDocumento.getBdMontoUsarDocumento());
//		adelantoSunat.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//		
//		log.info(adelantoSunat);
//		return adelantoSunat;
//	}
	
	private DocumentoSunatOrdenComDoc agregarDocumentoSunatOrdenComDoc(OrdenCompraDocumento ordenCompraDocumento){
		DocumentoSunatOrdenComDoc docSunatOrdenCompraDoc = new DocumentoSunatOrdenComDoc();
		docSunatOrdenCompraDoc.setId(new DocumentoSunatOrdenComDocId());
		
		docSunatOrdenCompraDoc.getId().setIntPersEmpresaDocSunatOrden(EMPRESA_USUARIO);		
		docSunatOrdenCompraDoc.setIntPersEmpresaOrden(ordenCompra.getId().getIntPersEmpresa());
		docSunatOrdenCompraDoc.setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
		docSunatOrdenCompraDoc.setIntItemOrdenCompraDoc(ordenCompraDocumento.getId().getIntItemOrdenCompraDocumento());
		docSunatOrdenCompraDoc.setBdMonto(ordenCompraDocumento.getBdMontoUsarDocumento());
		docSunatOrdenCompraDoc.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		docSunatOrdenCompraDoc.setIntItemOrdenCompraDetalle(null);
		log.info(docSunatOrdenCompraDoc);
		
		return docSunatOrdenCompraDoc;
	}
	
	
	
	//Modificado por cdelosrios, 20/10/2013
	/*public void seleccionarTipoComprobante(){
		try{
			if(documentoSunatNuevo.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_RECIBOHONORARIOS))
				documentoSunatNuevo.setStrSerieDocumento("001");
			
			if(!documentoSunatNuevo.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA))
				documentoSunatNuevo.setSeleccionaPercepcion(Boolean.FALSE);
			
			calcularMontos();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}*/
	//Fin modificado por cdelosrios, 20/10/2013
	
	//Agregado por cdelosrios, 04/11/2013
	public void eliminarDocumentoSunat(){
		String rowKey = getRequestParameter("rowKeyDocSunat");
		DocumentoSunat documentoSunatTmp = null;
		if(listaDocumentoSunatGrabar!=null){
			for(int i=0; i<listaDocumentoSunatGrabar.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					DocumentoSunat documentoSunat = listaDocumentoSunatGrabar.get(i);
					if(documentoSunat.getId()!=null && documentoSunat.getId().getIntItemDocumentoSunat()!=null){
						documentoSunatTmp = listaDocumentoSunatGrabar.get(i);
						documentoSunatTmp.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						try {
							logisticaFacade.eliminarDocumentoSunat(documentoSunatTmp);
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}
					}
					removeDocSunat(documentoSunat);
					listaDocumentoSunatGrabar.remove(i);
					break;
				}
			}
			if(documentoSunatTmp!=null){
				listaDocumentoSunatGrabar.add(documentoSunatTmp);
			}
		}
	}
	
	public void removeDocSunat(DocumentoSunat documentoSunat){
		for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
			if(ordenCompraDetalle.getId().getIntItemOrdenCompra().equals(
					documentoSunat.getOrdenCompra().getId().getIntItemOrdenCompra())){
				ordenCompraDetalle.setBdMontoSaldoTemp(ordenCompraDetalle.getBdMontoSaldoTemp().add(
						documentoSunat.getDetalleTotal().getBdMontoTotal()));
			}
			
			/*if(ordenCompraDetalle.getBdMontoUsar().compareTo(bdMontoAdelantarTotal)>0){
				ordenCompraDetalle.setBdMontoUsar(ordenCompraDetalle.getBdMontoUsar().subtract(bdMontoAdelantarTotal));
				bdMontoAdelantarTotal = new BigDecimal(0);
			}else{
				bdMontoAdelantarTotal = bdMontoAdelantarTotal.subtract(ordenCompraDetalle.getBdMontoUsar());
				ordenCompraDetalle.setBdMontoUsar(new BigDecimal(0));
			}*/
		}
	}
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	//Fin agregado por cdelosrios, 04/11/2013
	

	
	
	

	
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
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}
	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}
	public String getStrFiltroTextoPersona() {
		return strFiltroTextoPersona;
	}
	public void setStrFiltroTextoPersona(String strFiltroTextoPersona) {
		this.strFiltroTextoPersona = strFiltroTextoPersona;
	}
	public DocumentoSunat getDocumentoSunatNuevo() {
		return documentoSunatNuevo;
	}
	public void setDocumentoSunatNuevo(DocumentoSunat documentoSunatNuevo) {
		this.documentoSunatNuevo = documentoSunatNuevo;
	}
	public DocumentoSunat getDocumentoSunatFiltro() {
		return documentoSunatFiltro;
	}
	public void setDocumentoSunatFiltro(DocumentoSunat documentoSunatFiltro) {
		this.documentoSunatFiltro = documentoSunatFiltro;
	}
	public DocumentoSunat getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(DocumentoSunat registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public List<DocumentoSunat> getListaDocumentoSunat() {
		return listaDocumentoSunat;
	}
	public void setListaDocumentoSunat(List<DocumentoSunat> listaDocumentoSunat) {
		this.listaDocumentoSunat = listaDocumentoSunat;
	}
	public List<DocumentoRequisicion> getListaDocumentoRequisicion() {
		return listaDocumentoRequisicion;
	}
	public void setListaDocumentoRequisicion(List<DocumentoRequisicion> listaDocumentoRequisicion) {
		this.listaDocumentoRequisicion = listaDocumentoRequisicion;
	}
	public List<OrdenCompra> getListaOrdenCompra() {
		return listaOrdenCompra;
	}
	public void setListaOrdenCompra(List<OrdenCompra> listaOrdenCompra) {
		this.listaOrdenCompra = listaOrdenCompra;
	}
	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}
	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	public List<Tabla> getListaTablaTipoComprobante() {
		return listaTablaTipoComprobante;
	}
	public void setListaTablaTipoComprobante(List<Tabla> listaTablaTipoComprobante) {
		this.listaTablaTipoComprobante = listaTablaTipoComprobante;
	}
	public String getStrMensajeDetalle() {
		return strMensajeDetalle;
	}
	public void setStrMensajeDetalle(String strMensajeDetalle) {
		this.strMensajeDetalle = strMensajeDetalle;
	}
	public boolean isMostrarMensajeDetalle() {
		return mostrarMensajeDetalle;
	}
	public void setMostrarMensajeDetalle(boolean mostrarMensajeDetalle) {
		this.mostrarMensajeDetalle = mostrarMensajeDetalle;
	}
	public boolean isPoseePermisoDescuento() {
		return poseePermisoDescuento;
	}
	public void setPoseePermisoDescuento(boolean poseePermisoDescuento) {
		this.poseePermisoDescuento = poseePermisoDescuento;
	}
	public boolean isPoseePermisoIGV() {
		return poseePermisoIGV;
	}
	public void setPoseePermisoIGV(boolean poseePermisoIGV) {
		this.poseePermisoIGV = poseePermisoIGV;
	}
	public List<DocumentoSunat> getListaDocumentoSunatGrabar() {
		return listaDocumentoSunatGrabar;
	}
	public void setListaDocumentoSunatGrabar(List<DocumentoSunat> listaDocumentoSunatGrabar) {
		this.listaDocumentoSunatGrabar = listaDocumentoSunatGrabar;
	}
	public String getStrMensajeAgregar() {
		return strMensajeAgregar;
	}
	public void setStrMensajeAgregar(String strMensajeAgregar) {
		this.strMensajeAgregar = strMensajeAgregar;
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
	public TipoCambio getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(TipoCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	public boolean isModificarRegistro() {
		return modificarRegistro;
	}
	public void setModificarRegistro(boolean modificarRegistro) {
		this.modificarRegistro = modificarRegistro;
	}
	public boolean isAgregarLetras() {
		return agregarLetras;
	}
	public void setAgregarLetras(boolean agregarLetras) {
		this.agregarLetras = agregarLetras;
	}
	public DocumentoSunat getDocumentoSunatLetra() {
		return documentoSunatLetra;
	}
	public void setDocumentoSunatLetra(DocumentoSunat documentoSunatLetra) {
		this.documentoSunatLetra = documentoSunatLetra;
	}
//	public String getStrMensajeLetra() {
//		return strMensajeLetra;
//	}
//	public void setStrMensajeLetra(String strMensajeLetra) {
//		this.strMensajeLetra = strMensajeLetra;
//	}
	public boolean isMostrarMensajeLetra() {
		return mostrarMensajeLetra;
	}
	public void setMostrarMensajeLetra(boolean mostrarMensajeLetra) {
		this.mostrarMensajeLetra = mostrarMensajeLetra;
	}
	public TipoCambio getTipoCambioLetra() {
		return tipoCambioLetra;
	}
	public void setTipoCambioLetra(TipoCambio tipoCambioLetra) {
		this.tipoCambioLetra = tipoCambioLetra;
	}
	public Persona getPersonaFiltro() {
		return personaFiltro;
	}
	public void setPersonaFiltro(Persona personaFiltro) {
		this.personaFiltro = personaFiltro;
	}

	//Agregado por cdelosrios, 08/10/2013
	public List<Tabla> getListaTipoComprobante() {
		return listaTipoComprobante;
	}
	public void setListaTipoComprobante(List<Tabla> listaTipoComprobante) {
		this.listaTipoComprobante = listaTipoComprobante;
	}
	//Fin agregado por cdelosrios, 08/10/2013
	
	//Agregado por cdelosrios, 15/10/2013
	public OrdenCompra getOrdenCompraFiltro() {
		return ordenCompraFiltro;
	}
	public void setOrdenCompraFiltro(OrdenCompra ordenCompraFiltro) {
		this.ordenCompraFiltro = ordenCompraFiltro;
	}
	//Fin agregado por cdelosrios, 15/10/2013
	
	//Agregado por cdelosrios, 15/10/2013
	public BigDecimal getBdMontoAdelantoEntregadoTotal() {
		return bdMontoAdelantoEntregadoTotal;
	}
	public void setBdMontoAdelantoEntregadoTotal(
			BigDecimal bdMontoAdelantoEntregadoTotal) {
		this.bdMontoAdelantoEntregadoTotal = bdMontoAdelantoEntregadoTotal;
	}
	//Fin agregado por cdelosrios, 15/10/2013
	
	//Agregado por cdelosrios, 25/10/2013
	public boolean isMostrarPanelDatosSunat() {
		return mostrarPanelDatosSunat;
	}
	public void setMostrarPanelDatosSunat(boolean mostrarPanelDatosSunat) {
		this.mostrarPanelDatosSunat = mostrarPanelDatosSunat;
	}
	public boolean isHabilitarVerRegistro() {
		return habilitarVerRegistro;
	}
	public void setHabilitarVerRegistro(boolean habilitarVerRegistro) {
		this.habilitarVerRegistro = habilitarVerRegistro;
	}
	//Fin agregado por cdelosrios, 25/10/2013
	//Agregado por cdelosrios, 01/11/2013
	public DocumentoSunat getItemDocumentoSunat() {
		return itemDocumentoSunat;
	}
	public void setItemDocumentoSunat(DocumentoSunat itemDocumentoSunat) {
		this.itemDocumentoSunat = itemDocumentoSunat;
	}
	public String getStrSunat() {
		return strSunat;
	}
	public void setStrSunat(String strSunat) {
		this.strSunat = strSunat;
	}
	//Fin agregado por cdelosrios, 01/11/2013
	//Inicio agregado por cdelosrios, 15/11/2013
	public OrdenCompraDocumento getItemOrdenCompraDocumento() {
		return itemOrdenCompraDocumento;
	}
	public void setItemOrdenCompraDocumento(
			OrdenCompraDocumento itemOrdenCompraDocumento) {
		this.itemOrdenCompraDocumento = itemOrdenCompraDocumento;
	}
	//Fin agregado por cdelosrios, 15/11/2013
	//Agregado por cdelosrios, 18/11/2013
	public List<DocumentoSunat> getListaDocumentoSunatLetra() {
		return listaDocumentoSunatLetra;
	}
	public void setListaDocumentoSunatLetra(
			List<DocumentoSunat> listaDocumentoSunatLetra) {
		this.listaDocumentoSunatLetra = listaDocumentoSunatLetra;
	}
	//Fin agregado por cdelosrios, 18/11/2013
	
	//Autor: jchavez / Tarea: Creacion / Fecha: 24.10.2014
//	public String getStrMsgErrorAgregarDetalleDocSunat() {
//		return strMsgErrorAgregarDetalleDocSunat;
//	}
//	public void setStrMsgErrorAgregarDetalleDocSunat(String strMsgErrorAgregarDetalleDocSunat) {
//		this.strMsgErrorAgregarDetalleDocSunat = strMsgErrorAgregarDetalleDocSunat;
//	}
	public Integer getIntValidaCierrePorFechaEmision() {
		return intValidaCierrePorFechaEmision;
	}
	public void setIntValidaCierrePorFechaEmision(
			Integer intValidaCierrePorFechaEmision) {
		this.intValidaCierrePorFechaEmision = intValidaCierrePorFechaEmision;
	}
	public Boolean getBlnBloqueoDetalle() {
		return blnBloqueoDetalle;
	}
	public void setBlnBloqueoDetalle(Boolean blnBloqueoDetalle) {
		this.blnBloqueoDetalle = blnBloqueoDetalle;
	}
	//Fin jchavez - 24.10.2014
	//Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
	public BigDecimal getBdMontoIngresadoTotal() {
		return bdMontoIngresadoTotal;
	}
	public void setBdMontoIngresadoTotal(BigDecimal bdMontoIngresadoTotal) {
		this.bdMontoIngresadoTotal = bdMontoIngresadoTotal;
	}
	public String getStrMsgErrorMontoOrdenCompraDetalle() {
		return strMsgErrorMontoOrdenCompraDetalle;
	}
	public void setStrMsgErrorMontoOrdenCompraDetalle(
			String strMsgErrorMontoOrdenCompraDetalle) {
		this.strMsgErrorMontoOrdenCompraDetalle = strMsgErrorMontoOrdenCompraDetalle;
	}
	public Boolean getPoseePermisoIGVContable() {
		return poseePermisoIGVContable;
	}
	public void setPoseePermisoIGVContable(Boolean poseePermisoIGVContable) {
		this.poseePermisoIGVContable = poseePermisoIGVContable;
	}
	public Boolean getPoseePermisoAfectoInafecto() {
		return poseePermisoAfectoInafecto;
	}
	public void setPoseePermisoAfectoInafecto(Boolean poseePermisoAfectoInafecto) {
		this.poseePermisoAfectoInafecto = poseePermisoAfectoInafecto;
	}
	public String getStrMsgErrorDetalleOtros() {
		return strMsgErrorDetalleOtros;
	}
	public void setStrMsgErrorDetalleOtros(String strMsgErrorDetalleOtros) {
		this.strMsgErrorDetalleOtros = strMsgErrorDetalleOtros;
	}
	public Boolean getPoseePermisoPercepcion() {
		return poseePermisoPercepcion;
	}
	public void setPoseePermisoPercepcion(Boolean poseePermisoPercepcion) {
		this.poseePermisoPercepcion = poseePermisoPercepcion;
	}
	//Fin jchavez - 26.10.2014
	//Autor: jchavez / Tarea: Creacion / Fecha: 29.10.2014
	public Integer getIntValidaCierrePorFechaEmisionLetra() {
		return intValidaCierrePorFechaEmisionLetra;
	}
	public void setIntValidaCierrePorFechaEmisionLetra(
			Integer intValidaCierrePorFechaEmisionLetra) {
		this.intValidaCierrePorFechaEmisionLetra = intValidaCierrePorFechaEmisionLetra;
	}
	public String getStrMsgErrorMontoDocSunatL() {
		return strMsgErrorMontoDocSunatL;
	}
	public void setStrMsgErrorMontoDocSunatL(String strMsgErrorMontoDocSunatL) {
		this.strMsgErrorMontoDocSunatL = strMsgErrorMontoDocSunatL;
	}
	//Fin jchavez - 29.10.2014
	//Autor: jchavez / Tarea: Creacion / Fecha: 30.10.2014
	public List<DocumentoSunatDoc> getListaDocSunatDetraccionPercepcion() {
		return listaDocSunatDetraccionPercepcion;
	}
	public void setListaDocSunatDetraccionPercepcion(
			List<DocumentoSunatDoc> listaDocSunatDetraccionPercepcion) {
		this.listaDocSunatDetraccionPercepcion = listaDocSunatDetraccionPercepcion;
	}
	//Fin jchavez - 30.10.2014

	public List<Tabla> getListaTablaTipoComprobanteLetrasYNotas() {
		return listaTablaTipoComprobanteLetrasYNotas;
	}
	public void setListaTablaTipoComprobanteLetrasYNotas(
			List<Tabla> listaTablaTipoComprobanteLetrasYNotas) {
		this.listaTablaTipoComprobanteLetrasYNotas = listaTablaTipoComprobanteLetrasYNotas;
	}
	public Integer getIntTipoComprobante() {
		return intTipoComprobante;
	}
	public void setIntTipoComprobante(Integer intTipoComprobante) {
		this.intTipoComprobante = intTipoComprobante;
	}
	public List<DocumentoSunat> getListaDocSunatValidosParaLetraDeCambio() {
		return listaDocSunatValidosParaLetraDeCambio;
	}
	public void setListaDocSunatValidosParaLetraDeCambio(
			List<DocumentoSunat> listaDocSunatValidosParaLetraDeCambio) {
		this.listaDocSunatValidosParaLetraDeCambio = listaDocSunatValidosParaLetraDeCambio;
	}

	public Boolean getMostrarMensajeExitoDocSunatDetalle() {
		return mostrarMensajeExitoDocSunatDetalle;
	}
	public void setMostrarMensajeExitoDocSunatDetalle(
			Boolean mostrarMensajeExitoDocSunatDetalle) {
		this.mostrarMensajeExitoDocSunatDetalle = mostrarMensajeExitoDocSunatDetalle;
	}
	public Boolean getMostrarMensajeErrorDocSunatDetalle() {
		return mostrarMensajeErrorDocSunatDetalle;
	}
	public void setMostrarMensajeErrorDocSunatDetalle(
			Boolean mostrarMensajeErrorDocSunatDetalle) {
		this.mostrarMensajeErrorDocSunatDetalle = mostrarMensajeErrorDocSunatDetalle;
	}
	public String getMensajeOperacionDocSunatDetalle() {
		return mensajeOperacionDocSunatDetalle;
	}
	public void setMensajeOperacionDocSunatDetalle(
			String mensajeOperacionDocSunatDetalle) {
		this.mensajeOperacionDocSunatDetalle = mensajeOperacionDocSunatDetalle;
	}
	public Boolean getMostrarMensajeExitoDocSunatLetra() {
		return mostrarMensajeExitoDocSunatLetra;
	}
	public void setMostrarMensajeExitoDocSunatLetra(
			Boolean mostrarMensajeExitoDocSunatLetra) {
		this.mostrarMensajeExitoDocSunatLetra = mostrarMensajeExitoDocSunatLetra;
	}
	public Boolean getMostrarMensajeErrorDocSunatLetra() {
		return mostrarMensajeErrorDocSunatLetra;
	}
	public void setMostrarMensajeErrorDocSunatLetra(
			Boolean mostrarMensajeErrorDocSunatLetra) {
		this.mostrarMensajeErrorDocSunatLetra = mostrarMensajeErrorDocSunatLetra;
	}
	public String getMensajeOperacionDocSunatLetra() {
		return mensajeOperacionDocSunatLetra;
	}
	public void setMensajeOperacionDocSunatLetra(
			String mensajeOperacionDocSunatLetra) {
		this.mensajeOperacionDocSunatLetra = mensajeOperacionDocSunatLetra;
	}	

	public List<DocumentoSunat> getListaDocSunatValidosParaNota() {
		return listaDocSunatValidosParaNota;
	}
	public void setListaDocSunatValidosParaNota(
			List<DocumentoSunat> listaDocSunatValidosParaNota) {
		this.listaDocSunatValidosParaNota = listaDocSunatValidosParaNota;
	}
	public DocumentoSunat getDocumentoSunatNota() {
		return documentoSunatNota;
	}
	public void setDocumentoSunatNota(DocumentoSunat documentoSunatNota) {
		this.documentoSunatNota = documentoSunatNota;
	}
	public Boolean getMostrarMensajeExitoDocSunatNota() {
		return mostrarMensajeExitoDocSunatNota;
	}
	public void setMostrarMensajeExitoDocSunatNota(
			Boolean mostrarMensajeExitoDocSunatNota) {
		this.mostrarMensajeExitoDocSunatNota = mostrarMensajeExitoDocSunatNota;
	}
	public Boolean getMostrarMensajeErrorDocSunatNota() {
		return mostrarMensajeErrorDocSunatNota;
	}
	public void setMostrarMensajeErrorDocSunatNota(
			Boolean mostrarMensajeErrorDocSunatNota) {
		this.mostrarMensajeErrorDocSunatNota = mostrarMensajeErrorDocSunatNota;
	}
	public String getMensajeOperacionDocSunatNota() {
		return mensajeOperacionDocSunatNota;
	}
	public void setMensajeOperacionDocSunatNota(String mensajeOperacionDocSunatNota) {
		this.mensajeOperacionDocSunatNota = mensajeOperacionDocSunatNota;
	}
	public TipoCambio getTipoCambioNota() {
		return tipoCambioNota;
	}
	public void setTipoCambioNota(TipoCambio tipoCambioNota) {
		this.tipoCambioNota = tipoCambioNota;
	}
	public List<DocumentoSunatDetalle> getListaDocSunatDetDeDocSunatRel() {
		return listaDocSunatDetDeDocSunatRel;
	}
	public void setListaDocSunatDetDeDocSunatRel(
			List<DocumentoSunatDetalle> listaDocSunatDetDeDocSunatRel) {
		this.listaDocSunatDetDeDocSunatRel = listaDocSunatDetDeDocSunatRel;
	}
	public List<Tabla> getListaTipoComprobantePorProveedor() {
		return listaTipoComprobantePorProveedor;
	}
	public void setListaTipoComprobantePorProveedor(
			List<Tabla> listaTipoComprobantePorProveedor) {
		this.listaTipoComprobantePorProveedor = listaTipoComprobantePorProveedor;
	}
	public List<DocumentoSunat> getListaDocumentoSunatNota() {
		return listaDocumentoSunatNota;
	}
	public void setListaDocumentoSunatNota(
			List<DocumentoSunat> listaDocumentoSunatNota) {
		this.listaDocumentoSunatNota = listaDocumentoSunatNota;
	}
	public String getStrMsgErrorMontoDocSunatNota() {
		return strMsgErrorMontoDocSunatNota;
	}
	public void setStrMsgErrorMontoDocSunatNota(String strMsgErrorMontoDocSunatNota) {
		this.strMsgErrorMontoDocSunatNota = strMsgErrorMontoDocSunatNota;
	}
}