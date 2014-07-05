package pe.com.tumi.tesoreria.logistica.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DocumentoRequisicion;
import pe.com.tumi.common.util.MyUtil;
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
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.logistica.domain.AdelantoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;
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
	private List<Tabla>			listaTablaTipoComprobanteLetra;
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
	private String		strMensajeLetra;
	
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
	
	public SunatController(){
		cargarUsuario();
		//poseePermiso = Boolean.TRUE;
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_DOCUMENTOSUNAT);
		if(usuario!=null && poseePermiso)	cargarValoresIniciales();
		else log.error("--Usuario obtenido es NULL o no posee permiso.");
	}
	
	public String getLimpiarSunat(){
		cargarUsuario();
		//poseePermiso = Boolean.TRUE;
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
			listaTablaTipoComprobanteLetra = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOCOMPROBANTE), "B");
			documentoSunatFiltro = new DocumentoSunat();
			documentoSunatFiltro.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);
			listaDocumentoSunat = new ArrayList<DocumentoSunat>();
			listaSucursal = MyUtil.cargarListaSucursalConSubsucursalArea(EMPRESA_USUARIO);
			personaFiltro = new Persona();
			//Agregado por cdelosrios, 08/10/2013
			listaTipoComprobante = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCOMPROBANTE));
			if(listaTipoComprobante!=null && !listaTipoComprobante.isEmpty()){
				List<Tabla> lstTmpTabla = new ArrayList<Tabla>();
				for(int i=0; i<listaTipoComprobante.size(); i++){
					Tabla tipoComprobante = (Tabla) listaTipoComprobante.get(i);
					if(tipoComprobante.getIntIdDetalle().equals(new Integer(7)) 
						|| tipoComprobante.getIntIdDetalle().equals(new Integer(8))
						|| tipoComprobante.getIntIdDetalle().equals(new Integer(9)) ){
						lstTmpTabla.add(tipoComprobante);
					}
				}
				listaTipoComprobante.removeAll(lstTmpTabla);
			}
			//Fin agregado por cdelosrios, 08/10/2013
			//Agregado por cdelosrios, 15/10/2013
			ordenCompraFiltro = new OrdenCompra();
			bdMontoAdelantoEntregadoTotal = BigDecimal.ZERO;
			//Fin agregado por cdelosrios, 15/10/2013
			//Agregado por cdelosrios, 08/11/2013
			strMensajeAgregar = "";
			//Fin agregado por cdelosrios, 08/11/2013
			//Agregado por cdelosrios, 18/11/2013
			listaDocumentoSunatLetra = new ArrayList<DocumentoSunat>();
			//Fin agregado por cdelosrios, 18/11/2013
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
		ordenCompra = null;
		listaDocumentoSunatGrabar = null;
		documentoSunatNuevo = null;
		//Agregado por cdelosrios, 25/10/2013
		mostrarPanelDatosSunat = Boolean.FALSE;
		habilitarVerRegistro = Boolean.FALSE;
		strMensajeAgregar = "";
		//Fin agregado por cdelosrios, 25/10/2013
	}	
	
	public void grabar(){
		log.info("--grabar");
		try{
			if(listaDocumentoSunatGrabar!=null && !listaDocumentoSunatGrabar.isEmpty()){
				if(listaDocumentoSunatGrabar.isEmpty()){
					mostrarMensaje(Boolean.FALSE, "Debe agregar al menos un Documento Sunat."); return;
				}
				
				listaDocumentoSunatGrabar = logisticaFacade.grabarDocumentoSunat(listaDocumentoSunatGrabar,	ordenCompra);
				//Agregado por cdelosrios, 25/10/2013
				mostrarPanelDatosSunat = Boolean.FALSE;
				//Fin agregado por cdelosrios, 25/10/2013
				buscar();
				mostrarMensaje(Boolean.TRUE, "Se registró correctamente el Documento Sunat.");
			//Agregado por cdelosrios, 18/11/2013
			} else {
				DocumentoSunat docSunatLetra = null;
				if(listaDocumentoSunatLetra!=null && !listaDocumentoSunatLetra.isEmpty()){
					docSunatLetra = new DocumentoSunat();
					docSunatLetra.setOrdenCompra(ordenCompra);
					docSunatLetra.setListaDocumentoSunatLetra(listaDocumentoSunatLetra);
					logisticaFacade.agregarDocumentoSunatLetra(docSunatLetra);
					mostrarMensaje(Boolean.TRUE, "Se registro correctamente la Letra de cambio.");
				}
			}
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
	public void modificar(){
		log.info("--modificar");
		try{
			if(listaDocumentoSunatGrabar!=null){
				if(listaDocumentoSunatGrabar.isEmpty()){
					mostrarMensaje(Boolean.FALSE, "Debe agregar al menos un Documento Sunat."); return;
				}
				//Modificado por cdelosrios, 18/11/2013
				//listaDocumentoSunatGrabar = logisticaFacade.modificarDocumentoSunat(listaDocumentoSunatGrabar, ordenCompra);
				listaDocumentoSunatGrabar = logisticaFacade.modificarDocumentoSunat(listaDocumentoSunatGrabar, ordenCompra, listaDocumentoSunatLetra);
				//Fin modificado por cdelosrios, 18/11/2013
				//Agregado por cdelosrios, 18/11/2013
				/*if(documentoSunatNuevo.getListaDocumentoSunatLetra()!=null 
						&& !documentoSunatNuevo.getListaDocumentoSunatLetra().isEmpty()){
						documentoSunatNuevo.getId().setIntItemDocumentoSunat(
								registroSeleccionado.getId().getIntItemDocumentoSunat());
						logisticaFacade.agregarDocumentoSunatLetra(documentoSunatNuevo);
				}*/
				//Fin agregado por cdelosrios, 18/11/2013
				mostrarPanelDatosSunat = Boolean.TRUE;
				buscar();
				mostrarMensaje(Boolean.TRUE, "Se modificó correctamente el Documento Sunat.");
			}
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			modificarRegistro = Boolean.FALSE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro del Documento Sunat.");
			log.error(e.getMessage(),e);
		}
	}
	//Fin agregado por cdelosrios, 01/11/2013
	
	public void buscar(){
		try{
			listaDocumentoSunat.clear();
			
			cargarUsuario();
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
			
			//Agregado por cdelosrios, 22/10/2013
			if(documentoSunatFiltro.getIntParaTipoComprobante()!= null 
					&& documentoSunatFiltro.getIntParaTipoComprobante().equals(0)){
				documentoSunatFiltro.setIntParaTipoComprobante(null);
			}
			//Fin agregado por cdelosrios, 22/10/2013
			
			listaDocumentoSunat = logisticaFacade.buscarDocumentoSunat(documentoSunatFiltro, listaPersonaFiltro);
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			cargarUsuario();
			registroSeleccionado = (DocumentoSunat)event.getComponent().getAttributes().get("item");
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	//Modificado por cdelosrios, 29/10/2013
	/*public void verRegistro(){
		try{
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;			
			mostrarPanelInferior = Boolean.TRUE;

		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}*/
	//Fin modificado por cdelosrios, 29/10/2013
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
			documentoSunatLetra.setIntParaTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO);
			cargarListaDocumentoSunatLetra(documentoSunatLetra);
			//Fin agregado por cdelosrios, 18/11/2013
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	//Agregado por cdelosrios, 01/11/2013
	public void verRegistro(){
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
			mostrarPanelDatosSunat = Boolean.FALSE;
			habilitarVerRegistro = Boolean.TRUE;
			cargarDocumentoSunatPorOrdenCompra(registroSeleccionado);
			cargarDocumentoSunatNuevo();
			
			//Agregado por cdelosrios, 18/11/2013
			documentoSunatLetra = documentoSunatNuevo;
			documentoSunatLetra.setIntParaTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO);
			cargarListaDocumentoSunatLetra(documentoSunatLetra);
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
		documentoSunatNuevo = registroSeleccionado;
		
		ordenCompra = documentoSunatNuevo.getOrdenCompra();	
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
	
	public void abrirPopUpBuscarOrdenCompra(){
		//Modificado por cdelosrios, 15/10/2013
		buscarOrdenCompra();
		/*try{
			ordenCompraFiltro = new OrdenCompra();
			ordenCompraFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			//ordenCompraFiltro.setIntParaEstadoOrden(Constante.PARAM_T_ESTADOORDEN_ABIERTO);
			ordenCompraFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			listaOrdenCompra = logisticaFacade.buscarOrdenCompra(ordenCompraFiltro, null);
			for(OrdenCompra ordenCompra : listaOrdenCompra){
				cargarRequisicon(ordenCompra);
				Persona personaProveedor = personaFacade.devolverPersonaCargada(ordenCompra.getIntPersPersonaProveedor());
				ordenCompra.setPersonaProveedor(personaProveedor);
				ordenCompra.setIntParaTipoMoneda(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}*/
		//Fin modificado por cdelosrios, 15/10/2013
	}
	
	//Agregado por cdelosrios, 15/10/2013
	public void buscarOrdenCompra(){
		try{
			Integer intIdSucursal = usuario.getSucursal().getId().getIntIdSucursal();
			Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
			List<OrdenCompra> listaOrdenCompraTmp = new ArrayList<OrdenCompra>();
			ordenCompraFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			ordenCompraFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			listaOrdenCompra = logisticaFacade.buscarOrdenCompra(ordenCompraFiltro, null);
			for(OrdenCompra ordenCompra : listaOrdenCompra){
				cargarRequisicon(ordenCompra);
				Persona personaProveedor = personaFacade.devolverPersonaCargada(ordenCompra.getIntPersPersonaProveedor());
				ordenCompra.setPersonaProveedor(personaProveedor);
				ordenCompra.setIntParaTipoMoneda(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
				if (intIdPerfil.equals(ADMINISTRADOR_ZONAL_CENTRAL)
						|| intIdPerfil.equals(ADMINISTRADOR_ZONAL_FILIAL)
						|| intIdPerfil.equals(ADMINISTRADOR_ZONAL_LIMA)) {
					if(ordenCompra.getDocumentoRequisicion().getRequisicion().getIntSucuIdSucursal().equals(intIdSucursal)){
						listaOrdenCompraTmp.add(ordenCompra);
						listaOrdenCompra = listaOrdenCompraTmp;
					}else {
						listaOrdenCompra = new ArrayList<OrdenCompra>();
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	//Fin agregado por cdelosrios, 15/10/2013

	public void seleccionarOrdenCompra(ActionEvent event){
		DocumentoSunat documentoSunat = null;
		try{
			ordenCompra = (OrdenCompra)event.getComponent().getAttributes().get("item");
			cargarOrdenCompra(ordenCompra);
			//Agregado por cdelosrios, 04/11/2013
			documentoSunat = new DocumentoSunat();
			documentoSunat.getId().setIntPersEmpresa(ordenCompra.getId().getIntPersEmpresa());
			documentoSunat.setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
			documentoSunat.setOrdenCompra(ordenCompra);
			cargarDocumentoSunatPorOrdenCompra(documentoSunat);
			//Fin agregado por cdelosrios, 04/11/2013
			//Agregado por cdelosrios, 18/11/2013
			documentoSunat.setIntParaTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO);
			cargarListaDocumentoSunatLetra(documentoSunat);
			//Fin agregado por cdelosrios, 18/11/2013
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	//Agregado por cdelosrios, 18/11/2013
	private void cargarListaDocumentoSunatLetra(DocumentoSunat documentoSunat){
		List<DocumentoSunat> listDocSunatLetra = null;
		try {
			listDocSunatLetra = logisticaFacade.getListDocumentoSunatPorOrdenCompraYTipoDocumento(documentoSunat);
			listaDocumentoSunatLetra = listDocSunatLetra;
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
	
	private OrdenCompra cargarOrdenCompra(OrdenCompra ordenCompraCargar)throws Exception{
		Persona personaProveedor = ordenCompraCargar.getPersonaProveedor();
		if(personaProveedor.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
			String strEtiqueta = ordenCompraCargar.getId().getIntItemOrdenCompra()+ " - "+
				personaProveedor.getNatural().getStrNombreCompleto()+" - DNI :"+personaProveedor.getDocumento().getStrNumeroIdentidad();
			personaProveedor.setStrEtiqueta(strEtiqueta);
		}else{
			String strEtiqueta = ordenCompraCargar.getId().getIntItemOrdenCompra()+ " - "+
			personaProveedor.getJuridica().getStrRazonSocial()+" - RUC :"+personaProveedor.getStrRuc();
			personaProveedor.setStrEtiqueta(strEtiqueta);
		}
		
		ordenCompraCargar.setPersonaProveedor(personaProveedor);
		cargarEtiquetaOrdenCompra(ordenCompraCargar);
		if(ordenCompraCargar.getIntParaTipoDetraccion()!=null)
			ordenCompraCargar.setDetraccion(generalFacade.getDetraccionPorTipo(ordenCompraCargar.getIntParaTipoDetraccion()));
		
		//Agregado por cdelosrios, 17/10/2013
		bdMontoAdelantoEntregadoTotal = BigDecimal.ZERO;
		//Fin agregado por cdelosrios, 17/10/2013
		for(OrdenCompraDocumento ordenCompraDocumento : ordenCompraCargar.getListaOrdenCompraDocumento()){
			cargarSucursal(ordenCompraDocumento);
			ordenCompraDocumento.setBdMontoPagadoTemp(ordenCompraDocumento.getBdMontoPagado());
			ordenCompraDocumento.setBdMontoIngresadoTemp(ordenCompraDocumento.getBdMontoIngresado());
			ordenCompraDocumento.setBdMontoUsarTotal(new BigDecimal(0));
			ordenCompraDocumento.setBdMontoUsarDocumento(new BigDecimal(0));
			//Agregado por cdelosrios, 17/10/2013
			if(ordenCompraDocumento.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)){
				bdMontoAdelantoEntregadoTotal = bdMontoAdelantoEntregadoTotal.add(ordenCompraDocumento.getBdMontoPagado());
			}
			//Fin agregado por cdelosrios, 17/10/2013
		}
		
		for(OrdenCompraDetalle ordenCompraDetalle : ordenCompraCargar.getListaOrdenCompraDetalle()){
			cargarSucursal(ordenCompraDetalle);
			ordenCompraDetalle.setBdMontoSaldoTemp(ordenCompraDetalle.getBdMontoSaldo());
		}
		
		ProveedorId proveedorId = new ProveedorId();
		proveedorId.setIntPersEmpresa(EMPRESA_USUARIO);
		proveedorId.setIntPersPersona(ordenCompraCargar.getPersonaProveedor().getIntIdPersona());
		ordenCompraCargar.setProveedor(logisticaFacade.getProveedorPorPK(proveedorId));
		
		if(documentoSunatNuevo==null)	cargarDocumentoSunatNuevo();
		//Agregado por cdelosrios, 17/10/2013
		//documentoSunatNuevo.setDtFechaEmision(ordenCompraCargar.getDocumentoRequisicion().getRequisicion().get);
		//Fin agregado por cdelosrios, 17/10/2013
		
		return ordenCompraCargar;
	}
	
	private void cargarEtiquetaOrdenCompra(OrdenCompra ordenCompraCargar)throws Exception{
		//Modificado por cdelosrios, 15/10/2013
		String strEtiqueta = "";//ordenCompraCargar.getPersonaProveedor().getStrEtiqueta();
		strEtiqueta = strEtiqueta 
				//+ " - " + MyUtil.obtenerEtiquetaTabla(
				+ MyUtil.obtenerEtiquetaTabla(
		//Fin modificado por cdelosrios, 15/10/2013
						ordenCompraCargar.getIntParaEstadoOrden(), tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_ESTADOORDEN_INT));
		
		strEtiqueta = strEtiqueta 
		+ " - " + MyUtil.obtenerEtiquetaTabla(
				ordenCompraCargar.getDocumentoRequisicion().getRequisicion().getIntParaTipoAprobacion(), 
				tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_APROBACION_INT));
		
		strEtiqueta = strEtiqueta
				+ " - " + MyUtil.formatoMonto(ordenCompraCargar.getBdMontoTotalDetalle())
				+ " - " + MyUtil.obtenerEtiquetaTabla(
						ordenCompraCargar.getIntParaEstadoOrden(), tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_TIPOMONEDA_INT));
		
		ordenCompraCargar.setStrEtiqueta(strEtiqueta);
	}
		
	private void cargarDocumentoSunatNuevo()throws Exception{
		documentoSunatNuevo = new DocumentoSunat();
		
		documentoSunatNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
		documentoSunatNuevo.setIntPersEmpresaOrden(ordenCompra.getId().getIntPersEmpresa());
		documentoSunatNuevo.setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
		documentoSunatNuevo.setTsFechaProvision(MyUtil.obtenerFechaActual());
		documentoSunatNuevo.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
		documentoSunatNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		documentoSunatNuevo.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);
		documentoSunatNuevo.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
		documentoSunatNuevo.setIntPersPersonaUsuario(PERSONA_USUARIO);
		documentoSunatNuevo.setDtFechaEmision(new Date());
		documentoSunatNuevo.setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);			
		documentoSunatNuevo.setIntParaTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA);
		
		documentoSunatNuevo.setOrdenCompra(ordenCompra);
		
		documentoSunatNuevo.setDetalleSubTotal(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleDescuento(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleValorVenta(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleIGV(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleOtros(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleTotal(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetallePercepcion(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleDetraccion(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleRetencion(new DocumentoSunatDetalle());
		documentoSunatNuevo.setDetalleTotalGeneral(new DocumentoSunatDetalle());
		//Agregado por cdelosrios, 04/01/2014
		documentoSunatNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		//Fin Agregado por cdelosrios, 04/01/2014
		cargarTipoCambio();
		
		Integer intIdSucursalUsuario = usuario.getSucursal().getId().getIntIdSucursal();
		Integer intIdSubsucursalUsuario = usuario.getSubSucursal().getId().getIntIdSubSucursal();
		documentoSunatNuevo.getDetalleSubTotal().setIntSucuIdSucursal(intIdSucursalUsuario);
		documentoSunatNuevo.getDetalleSubTotal().setIntSudeIdSubsucursal(intIdSubsucursalUsuario);
		documentoSunatNuevo.getDetalleDescuento().setIntSucuIdSucursal(intIdSucursalUsuario);
		documentoSunatNuevo.getDetalleDescuento().setIntSudeIdSubsucursal(intIdSubsucursalUsuario);
		documentoSunatNuevo.getDetalleValorVenta().setIntSucuIdSucursal(intIdSucursalUsuario);
		documentoSunatNuevo.getDetalleValorVenta().setIntSudeIdSubsucursal(intIdSubsucursalUsuario);
		documentoSunatNuevo.getDetalleIGV().setIntSucuIdSucursal(intIdSucursalUsuario);
		documentoSunatNuevo.getDetalleIGV().setIntSudeIdSubsucursal(intIdSubsucursalUsuario);
		documentoSunatNuevo.getDetalleOtros().setIntSucuIdSucursal(intIdSucursalUsuario);
		documentoSunatNuevo.getDetalleOtros().setIntSudeIdSubsucursal(intIdSubsucursalUsuario);
		documentoSunatNuevo.getDetalleTotal().setIntSucuIdSucursal(intIdSucursalUsuario);
		documentoSunatNuevo.getDetalleTotal().setIntSudeIdSubsucursal(intIdSubsucursalUsuario);
		documentoSunatNuevo.getDetallePercepcion().setIntSucuIdSucursal(intIdSucursalUsuario);
		documentoSunatNuevo.getDetallePercepcion().setIntSudeIdSubsucursal(intIdSubsucursalUsuario);
		documentoSunatNuevo.getDetalleDetraccion().setIntSucuIdSucursal(intIdSucursalUsuario);
		documentoSunatNuevo.getDetalleDetraccion().setIntSudeIdSubsucursal(intIdSubsucursalUsuario);
		documentoSunatNuevo.getDetalleRetencion().setIntSucuIdSucursal(intIdSucursalUsuario);
		documentoSunatNuevo.getDetalleRetencion().setIntSudeIdSubsucursal(intIdSubsucursalUsuario);
		documentoSunatNuevo.getDetalleTotalGeneral().setIntSucuIdSucursal(intIdSucursalUsuario);
		documentoSunatNuevo.getDetalleTotalGeneral().setIntSudeIdSubsucursal(intIdSubsucursalUsuario);
		
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
				break;
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
	
	public void abrirPopUpAgregarDetalleSunat(){
		try{
			strMensajeDetalle = null;
			mostrarMensajeDetalle = Boolean.FALSE;
			List<OrdenCompraDocumento> listaTemp = new ArrayList<OrdenCompraDocumento>();
			
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
	
	public void abrirPopUpAgregarLetra(){
		try{
			log.info("--abrirPopUpAgregarLetra");
			documentoSunatLetra = new DocumentoSunat();
			documentoSunatLetra.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			documentoSunatLetra.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
			documentoSunatLetra.setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
			documentoSunatLetra.setTsFechaProvision(MyUtil.obtenerFechaActual());
			documentoSunatLetra.setDtFechaEmision(new Date());
			documentoSunatLetra.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			documentoSunatLetra.setIntPersPersonaUsuario(PERSONA_USUARIO);
			
			documentoSunatLetra.setDetalleLetra(new DocumentoSunatDetalle());			
			documentoSunatLetra.getDetalleLetra().setIntParaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
			documentoSunatLetra.getDetalleLetra().setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);			
			documentoSunatLetra.getDetalleLetra().setOrdenCompraDetalle(new OrdenCompraDetalle());
			documentoSunatLetra.getDetalleLetra().getOrdenCompraDetalle().setSucursal(usuario.getSucursal());
			documentoSunatLetra.getDetalleLetra().setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			seleccionarSucursalLetra();
			documentoSunatLetra.getDetalleLetra().setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());			
			
			cargarTipoCambioLetra();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void cargarTipoCambioLetra(){
		try{
			log.info("moneda:"+documentoSunatLetra.getDetalleLetra().getIntParaTipoMoneda());
			log.info("fecha:"+documentoSunatLetra.getDtFechaEmision());
			log.info("empresa:"+EMPRESA_USUARIO);
			tipoCambioLetra = generalFacade.getTipoCambioPorMonedaYFecha(
				documentoSunatLetra.getDetalleLetra().getIntParaTipoMoneda(), documentoSunatLetra.getDtFechaEmision(), EMPRESA_USUARIO);
			log.info(tipoCambioLetra);
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
	
	public void agregarDetalleSunat2(){
		try{			
			if(!validarDetalleSunat2()){
				mostrarMensajeDetalle = Boolean.TRUE;
				return;
			}
			mostrarMensajeDetalle = Boolean.FALSE;
			
			BigDecimal bdMontoAdelantarTotal = new BigDecimal(0);
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				if(ordenCompraDocumento.getBdMontoUsar()==null)	continue;
				//manejarAdelantoSunat(ordenCompraDocumento);
				ordenCompraDocumento.setBdMontoUsarDocumento(
						ordenCompraDocumento.getBdMontoUsarDocumento().add(ordenCompraDocumento.getBdMontoUsar()));
				
				ordenCompraDocumento.setBdMontoUsarTotal(
						ordenCompraDocumento.getBdMontoUsarTotal().add(ordenCompraDocumento.getBdMontoUsar()));
				
				ordenCompraDocumento.setBdMontoIngresadoTemp(
						ordenCompraDocumento.getBdMontoIngresadoTemp().add(ordenCompraDocumento.getBdMontoUsar()));
				
				bdMontoAdelantarTotal = bdMontoAdelantarTotal.add(ordenCompraDocumento.getBdMontoUsar());
			}
			
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				if(ordenCompraDetalle.getBdMontoUsar()==null) continue;
				ordenCompraDetalle.setBdMontoSaldoTemp(ordenCompraDetalle.getBdMontoSaldoTemp().subtract(ordenCompraDetalle.getBdMontoUsar()));
				//Modificado por cdelosrios, 24/11/2013
				/*if(ordenCompraDetalle.getBdMontoUsar().compareTo(bdMontoAdelantarTotal)>0){
					ordenCompraDetalle.setBdMontoUsar(ordenCompraDetalle.getBdMontoUsar().subtract(bdMontoAdelantarTotal));
					bdMontoAdelantarTotal = new BigDecimal(0);
				}else{
					bdMontoAdelantarTotal = bdMontoAdelantarTotal.subtract(ordenCompraDetalle.getBdMontoUsar());
					ordenCompraDetalle.setBdMontoUsar(new BigDecimal(0));
				}*/
				//Fin modificado por cdelosrios, 24/11/2013
				agregarDocumentoDetalleSunat(ordenCompraDetalle);
			}
			
			calcularMontos();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	public void calcularMontos(){
		try{
			cargarTipoCambio();
			documentoSunatNuevo = logisticaFacade.calcularMontosDocumentoSunat(documentoSunatNuevo, tipoCambio);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}		
	
	public void agregarDocumentoSunatLetra(){
		try{
			if(!validarDocumentoSunatLetra()){
				mostrarMensajeLetra = Boolean.TRUE;
				return;
			}
			mostrarMensajeLetra = Boolean.FALSE;
			log.info(documentoSunatLetra.getDetalleLetra().getBdMontoSinTipoCambio());
			log.info(tipoCambioLetra.getBdPromedio());
			documentoSunatLetra.getDetalleLetra().setBdMontoTotal(
					documentoSunatLetra.getDetalleLetra().getBdMontoSinTipoCambio().multiply(tipoCambioLetra.getBdPromedio()));
			
			for(Area area : documentoSunatLetra.getDetalleLetra().getOrdenCompraDetalle().getSucursal().getListaArea()){
				if(area.getId().getIntIdArea().equals(documentoSunatLetra.getDetalleLetra().getIntIdArea())){
					documentoSunatLetra.getDetalleLetra().getOrdenCompraDetalle().setArea(area);
				}
			}
			//Modificado por cdelosrios, 18/11/2013
			//documentoSunatNuevo.getListaDocumentoSunatLetra().add(documentoSunatLetra);
			listaDocumentoSunatLetra.add(documentoSunatLetra);
			//Fin modificado por cdelosrios, 18/11/2013
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean validarDocumentoSunatLetra(){
		strMensajeLetra="";
		documentoSunatLetra.getDetalleLetra().setBdMontoTotal(
				documentoSunatLetra.getDetalleLetra().getBdMontoSinTipoCambio().multiply(tipoCambioLetra.getBdPromedio()));
			
		
		if(documentoSunatLetra.getDtFechaEmision()==null){
			strMensajeLetra = "Debe de ingresar una fecha de emisión.";
		}else if(documentoSunatLetra.getDtFechaVencimiento()==null){
			strMensajeLetra = "Debe de ingresar una fecha de vencimiento.";
		}else if(documentoSunatLetra.getDtFechaVencimiento().compareTo(documentoSunatLetra.getDtFechaEmision())<0){
			strMensajeLetra = "La fecha de vencimiento debe ser mayor a la fecha de emisión.";		
		}else if(documentoSunatLetra.getStrNumeroDocumento()==null){
			strMensajeLetra = "Debe de ingresar un numero de documento.";
		}else if(documentoSunatLetra.getStrSerieDocumento()==null){
			strMensajeLetra = "Debe de ingresar una serie de documento.";
		}else if(documentoSunatLetra.getDetalleLetra().getBdMontoSinTipoCambio()==null){
			strMensajeLetra = "Debe de ingresar un monto.";
		}else if(documentoSunatLetra.getDetalleLetra().getIntSudeIdSubsucursal()==null){
			strMensajeLetra = "Debe de seleccionar una subsucursal valida.";
		}else if(documentoSunatLetra.getDetalleLetra().getIntIdArea()==null){
			strMensajeLetra = "Debe de seleccionar un área válida.";
		}
		
		BigDecimal bdMontoAcumulado = documentoSunatLetra.getDetalleLetra().getBdMontoTotal();
		//Modificado por celosrios, 18/11/2013
		//for(DocumentoSunat documentoSunatLetra : documentoSunatNuevo.getListaDocumentoSunatLetra()){
		for(DocumentoSunat documentoSunatLetra : listaDocumentoSunatLetra){
		//Fin modificado por celosrios, 18/11/2013
			bdMontoAcumulado = bdMontoAcumulado.add(documentoSunatLetra.getDetalleLetra().getBdMontoTotal());				
		}
		//if(documentoSunatNuevo.getDetalleTotalGeneral().getBdMontoTotal().compareTo(bdMontoAcumulado)<0){
		if(ordenCompra.getBdMontoTotalDetalle().compareTo(bdMontoAcumulado)<0){
		//Fin modificado por cdelosrios, 14/11/2013
			strMensajeLetra = "El monto ingresado supera el permitido.";
		}
		if(strMensajeLetra.isEmpty()){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private void agregarDocumentoDetalleSunat(OrdenCompraDetalle ordenCompraDetalle)throws Exception{
		boolean seEncuentraAgregado = Boolean.FALSE;
		for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunatNuevo.getListaDocumentoSunatDetalle()){
			if(documentoSunatDetalle.getIntItemOrdenCompraDetalle().equals(ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle())){
				seEncuentraAgregado = Boolean.TRUE;
				documentoSunatDetalle.setBdMontoSinTipoCambio(documentoSunatDetalle.getBdMontoSinTipoCambio().add(
						ordenCompraDetalle.getBdMontoUsar()));
				break;
			}
		}
		
		if(seEncuentraAgregado)	return;
		
		DocumentoSunatDetalle documentoSunatDetalle = new DocumentoSunatDetalle();
		documentoSunatDetalle.getId().setIntPersEmpresa(EMPRESA_USUARIO);
		documentoSunatDetalle.setIntParaTipoDocumentoSunat(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PRODUCTO);
		documentoSunatDetalle.setIntPersEmpresaOrdenCompra(ordenCompraDetalle.getId().getIntPersEmpresa());
		documentoSunatDetalle.setIntItemOrdenCompra(ordenCompraDetalle.getId().getIntItemOrdenCompra());
		documentoSunatDetalle.setIntItemOrdenCompraDetalle(ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle());
		documentoSunatDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		documentoSunatDetalle.setBdMontoSinTipoCambio(ordenCompraDetalle.getBdMontoUsar());
		//documentoSunatDetalle.setBdPrecioTotal(.multiply(tipoCambio.getBdPromedio()));
		documentoSunatDetalle.setOrdenCompraDetalle(ordenCompraDetalle);
		documentoSunatDetalle.setIntParaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
		documentoSunatDetalle.setIntSucuIdSucursal(ordenCompraDetalle.getIntSucuIdSucursal());
		documentoSunatDetalle.setIntSudeIdSubsucursal(ordenCompraDetalle.getIntSudeIdSubsucursal());
		documentoSunatDetalle.setIntIdArea(ordenCompraDetalle.getIntIdArea());
		//Agregado por cdelosrios, 07/11/2013
		documentoSunatDetalle.setStrDescripcion(ordenCompraDetalle.getStrDescripcion());
		documentoSunatDetalle.setIntPersEmpresaCuenta(ordenCompraDetalle.getIntPersEmpresaCuenta());
		documentoSunatDetalle.setIntContPeriodoCuenta(ordenCompraDetalle.getIntContPeriodoCuenta());
		documentoSunatDetalle.setStrContNumeroCuenta(ordenCompraDetalle.getStrContNumeroCuenta());
		//Fin agregado por cdelosrios, 07/11/2013
		documentoSunatNuevo.getListaDocumentoSunatDetalle().add(documentoSunatDetalle);
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
	
	private void cargarTipoCambio()throws Exception{
		Integer intMoneda = ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda();
		tipoCambio = generalFacade.getTipoCambioPorMonedaYFecha(intMoneda, documentoSunatNuevo.getDtFechaEmision(), EMPRESA_USUARIO);		
	}
	
	public void agregarDocumentoSunat(){
		try{
			strMensajeAgregar = "";
			
			if(documentoSunatNuevo.getStrNumeroDocumento()==null || documentoSunatNuevo.getStrNumeroDocumento().isEmpty()){
				strMensajeAgregar = "Debe de ingresar un numero de documento."; return;
			}
			if(documentoSunatNuevo.getStrSerieDocumento()==null || documentoSunatNuevo.getStrSerieDocumento().isEmpty()){
				strMensajeAgregar = "Debe de ingresar un numero de serie."; return;
			}
			if(documentoSunatNuevo.getDtFechaEmision()==null){
				strMensajeAgregar = "Debe de ingresar una fecha de emisión."; return;
			}
			if(documentoSunatNuevo.getDtFechaVencimiento()==null){
				strMensajeAgregar = "Debe de ingresar una fecha de vencimiento."; return;
			}
			if(documentoSunatNuevo.getDtFechaVencimiento().compareTo(documentoSunatNuevo.getDtFechaEmision())<0){
				strMensajeAgregar = "La fecha de vencimiento debe ser mayor a la fecha de emisión."; return;
			}
			if(documentoSunatNuevo.getDtFechaPago()==null){
				strMensajeAgregar = "Debe de ingresar una fecha de programación de pago."; return;
			}
			if(documentoSunatNuevo.getDtFechaPago().compareTo(documentoSunatNuevo.getDtFechaEmision())<0){
				strMensajeAgregar = "La fecha de pago debe ser mayor a la fecha de emisión."; return;
			}
			if(documentoSunatNuevo.getStrGlosa()==null || documentoSunatNuevo.getStrGlosa().isEmpty()){
				strMensajeAgregar = "Debe de ingresar una glosa."; return;
			}
			if(documentoSunatNuevo.getListaDocumentoSunatDetalle()==null || documentoSunatNuevo.getListaDocumentoSunatDetalle().isEmpty()){
				strMensajeAgregar = "Debe de agregar al menos un detalle."; return;
			}
			
			//Agregado por cdelosrios, 04/11/2013
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
			//Fin agregado por cdelosrios, 04/11/2013
			
			if(documentoSunatNuevo.isSeleccionaInafecto())	documentoSunatNuevo.setIntIndicadorInafecto(1);
			else documentoSunatNuevo.setIntIndicadorInafecto(0);
			
			if(documentoSunatNuevo.isSeleccionaIGV()) documentoSunatNuevo.setIntIndicadorIGV(1);
			else documentoSunatNuevo.setIntIndicadorIGV(0);
			
			if(documentoSunatNuevo.isSeleccionaPercepcion()) documentoSunatNuevo.setIntIndicadorPercepcion(1);
			else documentoSunatNuevo.setIntIndicadorPercepcion(0);
			
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				if(ordenCompraDocumento.getBdMontoUsarDocumento()!=null && ordenCompraDocumento.getBdMontoUsarDocumento().signum()>=0)
					documentoSunatNuevo.getListaAdelantoSunat().add(agregarAdelantoSunat(ordenCompraDocumento));
				
				ordenCompraDocumento.setBdMontoPagado(ordenCompraDocumento.getBdMontoPagadoTemp());
				ordenCompraDocumento.setBdMontoIngresado(ordenCompraDocumento.getBdMontoIngresadoTemp());				
				ordenCompraDocumento.setBdMontoUsarDocumento(new BigDecimal(0));
			}
			
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				ordenCompraDetalle.setBdMontoSaldo(ordenCompraDetalle.getBdMontoSaldoTemp());
			}
			//Agregado por cdelosrios, 04/11/2013
			documentoSunatNuevo.setIntPersEmpresaRequisicion(ordenCompra.getDocumentoRequisicion().getRequisicion().getId().getIntPersEmpresa());
			documentoSunatNuevo.setIntItemRequisicion(ordenCompra.getDocumentoRequisicion().getRequisicion().getId().getIntItemRequisicion());
			//Fin agregado por cdelosrios, 04/11/2013
			
			//Agregado por cdelosrios, 12/11/2013
			documentoSunatNuevo.setStrNumeroDocumento(documentoSunatNuevo.getStrNumeroDocumento().trim());
			documentoSunatNuevo.setStrSerieDocumento(documentoSunatNuevo.getStrSerieDocumento().trim());
			documentoSunatNuevo.setIntSucuIdSucursal(SUCURSAL_USUARIO);
			documentoSunatNuevo.setIntSubIdSubsucursal(SUBSUCURSAL_USUARIO);
			//Fin agregado por cdelosrios, 12/11/2013
			
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
			
			cargarDocumentoSunatNuevo();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private AdelantoSunat agregarAdelantoSunat(OrdenCompraDocumento ordenCompraDocumento){
		AdelantoSunat adelantoSunat = new AdelantoSunat();
		adelantoSunat.getId().setIntPersEmpresa(EMPRESA_USUARIO);
		adelantoSunat.setOrdenCompraDocumentoId(ordenCompraDocumento.getId());
		adelantoSunat.setBdMonto(ordenCompraDocumento.getBdMontoUsarDocumento());
		adelantoSunat.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		log.info(adelantoSunat);
		return adelantoSunat;
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
	
	//Agregado por cdelosrios, 08/11/2013
	public void onChangeTipoDocumento(){
		calcularMontos();
	}
	//Fin agregado por cdelosrios, 08/11/2013
	
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
			parametro.put("P_PERCEPCION", registroSeleccionado.getDetallePercepcion().getBdMontoTotal());
			parametro.put("P_DETRACCION", registroSeleccionado.getDetalleDetraccion().getBdMontoTotal());
			parametro.put("P_RETENCION", registroSeleccionado.getDetalleRetencion().getBdMontoTotal());
			parametro.put("P_TOTALGENERAL", registroSeleccionado.getDetalleTotalGeneral().getBdMontoTotal());
			
			strNombreReporte = "documentoSunat";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaDocumentoSunat), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
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
	public String getStrMensajeLetra() {
		return strMensajeLetra;
	}
	public void setStrMensajeLetra(String strMensajeLetra) {
		this.strMensajeLetra = strMensajeLetra;
	}
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
	public List<Tabla> getListaTablaTipoComprobanteLetra() {
		return listaTablaTipoComprobanteLetra;
	}
	public void setListaTablaTipoComprobanteLetra(List<Tabla> listaTablaTipoComprobanteLetra) {
		this.listaTablaTipoComprobanteLetra = listaTablaTipoComprobanteLetra;
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
}