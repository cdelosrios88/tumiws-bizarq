package pe.com.tumi.tesoreria.conciliacion.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.conciliacion.facade.ConciliacionFacadeLocal;
import pe.com.tumi.tesoreria.conciliacion.service.ConciliacionService;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;
import pe.com.tumi.tesoreria.egreso.domain.TelecreditoDetailFile;
import pe.com.tumi.tesoreria.egreso.domain.comp.ConciliacionComp;
import pe.com.tumi.tesoreria.egreso.domain.comp.TelecreditoFileComp;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;


public class ConciliacionController{

	protected static Logger log = Logger.getLogger(ConciliacionController.class);
	
	PersonaFacadeRemote 	personaFacade;
	EmpresaFacadeRemote		empresaFacade;
	LogisticaFacadeLocal	logisticaFacade;
	GeneralFacadeRemote		generalFacade;
	ContactoFacadeRemote	contactoFacade;
	TablaFacadeRemote		tablaFacade;
	PlanCuentaFacadeRemote	planCuentaFacade;
	EgresoFacadeLocal		egresoFacade;
	BancoFacadeLocal		bancoFacade;
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	ConciliacionFacadeLocal conciliacionFacade;
	private ConciliacionService conciliacionService;
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	private	Conciliacion	conciliacionNuevo;
	private Conciliacion	conciliacionFiltro;
	private Conciliacion	registroSeleccionado;
	private Bancocuenta		bancoCuentaFiltro;
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	//private List<Conciliacion>	listaConciliacion;
	private List<Conciliacion>	listaConciliacionBusq;
	private ConciliacionComp conciliacionCompBusq;
	private List<Bancofondo>	listaBanco;
	private List<Tabla> listaTablaTipoDoc;
	private List<Sucursal> listSucursal;
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	private List<Bancocuenta>	listaBancoCuenta;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean datosValidados;
	
	public ConciliacionController(){
		cargarUsuario();
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();	
	}
	
	public void cargarValoresIniciales(){
		try{
			personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			empresaFacade =  (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaFacade  = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			planCuentaFacade  = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			egresoFacade  = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);			
			bancoFacade  = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
			conciliacionFacade = (ConciliacionFacadeLocal) EJBFactory.getLocal(ConciliacionFacadeLocal.class);
			conciliacionCompBusq = new ConciliacionComp();
			//listaBanco = bancoFacade.obtenerListaBancoExistente(EMPRESA_USUARIO);
			//cargarListaBanco();
			cargarListaTipoDocumento();
			conciliacionService 	= (ConciliacionService)TumiFactory.get(ConciliacionService.class);

			/* Fin: REQ14-006 Bizarq - 26/10/2014 */
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	/**
	 * 
	 * @throws Exception
	 */
	private void cargarListaBanco()throws Exception{

		List<Tabla> listaTablaTipoBanco = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_BANCOS));
		for(Bancofondo banco : listaBanco){
			for(Tabla tabla : listaTablaTipoBanco){
				if(banco.getIntBancoCod().equals(tabla.getIntIdDetalle())){
					banco.setStrEtiqueta(tabla.getStrDescripcion());
				}
			}
		}
		
	}
	
	/**
	 * Carga Combo de Cuentas Banacarias
	 * @throws Exception
	 */
	public void cargarListaCuentas()throws Exception{
		
		try {
			String strIdCuenta = null;
			Integer intIdCuenta = null;
			
			strIdCuenta = getRequestParameter("pIntIdCuenta");
			intIdCuenta = new Integer(strIdCuenta);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
	}
	
	/**
	 * carga Combo de Tipo de Documento
	 */
	public void cargarListaTipoDocumento()throws Exception{
		
		try {
			listaTablaTipoDoc = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_DOCUMENTOGENERAL), "B");

		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}	
	}
	
	
	/**
	 * Recupera Ingresos y egresos para conciliacion
	 */
	public void buscarRegistrosConciliacion(){
		List<ConciliacionDetalle> lstConcilDet = null;
		try {
			
			
			lstConcilDet= conciliacionFacade.buscarRegistrosConciliacion(conciliacionNuevo);
			if(lstConcilDet != null && lstConcilDet.size() > 0){
				conciliacionNuevo.setListaConciliacionDetalle(new ArrayList<ConciliacionDetalle>());
				conciliacionNuevo.getListaConciliacionDetalle().addAll(lstConcilDet);
				
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}

		
	}
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}

	public void grabar(){
		log.info("--grabar");
		try {
			cargarUsuario();
			
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro de la Conciliacion Bancaria.");
			log.error(e.getMessage(),e);
		}
	}	
	
	public void buscar(){
		/* Inicio: REQ14-006 Bizarq - 18/10/2014 */
		conciliacionCompBusq = new ConciliacionComp();
		listaConciliacionBusq = new ArrayList<Conciliacion>();		
		/* Fin: REQ14-006 Bizarq - 18/10/2014 */
	
		try{
			
			/* Inicio: REQ14-006 Bizarq - 18/10/2014 */
			listaConciliacionBusq = conciliacionFacade.getListFilter(conciliacionCompBusq);
			/* Fin: REQ14-006 Bizarq - 18/10/2014 */
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			cargarUsuario();
			registroSeleccionado = (Conciliacion)event.getComponent().getAttributes().get("item");
			log.info(registroSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistro(){
		try{
			if(registroSeleccionado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				deshabilitarNuevo = Boolean.FALSE;
				habilitarGrabar = Boolean.TRUE;
			}else{
				deshabilitarNuevo = Boolean.TRUE;
				habilitarGrabar = Boolean.FALSE;
			}
			mostrarPanelInferior = Boolean.TRUE;
			
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void modificarRegistro(){
		try{
			log.info("--modificarRegistro");			
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;			

			
			ocultarMensaje();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarRegistro(){
		try{
			Conciliacion conciliacionEliminar = registroSeleccionado;			
			
			buscar();
			mostrarMensaje(Boolean.TRUE, "Se eliminó correctamente la Solicitud Personal.");
		}catch (Exception e){
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la eliminación de la Solicitud Personal.");
			log.error(e.getMessage(),e);
		}
	}
	
	public void habilitarPanelInferior(){
		try{
			//cargarUsuario();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			datosValidados = Boolean.FALSE;
			
			conciliacionNuevo = new Conciliacion();
			conciliacionNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			conciliacionNuevo.setTsFechaConciliacion(MyUtil.obtenerFechaActual());
			
			habilitarGrabar = Boolean.TRUE;
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
	
	public void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
	public void abrirPopUpBuscarBancoCuenta(){
		try{
			bancoCuentaFiltro = new Bancocuenta();
			bancoCuentaFiltro.getId().setIntEmpresaPk(EMPRESA_USUARIO);
			bancoCuentaFiltro.setIntEmpresacuentaPk(EMPRESA_USUARIO);
			bancoCuentaFiltro.setBancofondo(new Bancofondo());
			bancoCuentaFiltro.setIntPeriodocuenta(MyUtil.obtenerAñoActual()-1);
			bancoCuentaFiltro.getBancofondo().setIntBancoCod(Constante.PARAM_T_BANCOS_BANCOCREDITO);
			
			buscarBancoCuenta();
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarBancoCuenta(){
		try{
			if(bancoCuentaFiltro.getStrNumerocuenta()!=null && bancoCuentaFiltro.getStrNumerocuenta().isEmpty())
				bancoCuentaFiltro.setStrNumerocuenta(null);
			
			listaBancoCuenta = bancoFacade.buscarListaBancoCuenta(bancoCuentaFiltro);
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}	
	
	public void seleccionarBancoCuenta(ActionEvent event){
		try{
			Bancocuenta bancoCuentaSeleccionado = (Bancocuenta)event.getComponent().getAttributes().get("item");
			conciliacionNuevo.setBancoCuenta(bancoCuentaSeleccionado);
			log.info(bancoCuentaSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void validarDatos(){
		try{
			datosValidados = Boolean.TRUE;
			
			
			
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/* Inicio: REQ14-006 Bizarq - 28/10/2014 */
	public void adjuntarDocTelecredito(UploadEvent event){
		TelecreditoFileComp telecreditoFileComp = null;
		TelecreditoDetailFile telecreditoDetail = null;
		List<TelecreditoDetailFile> lstDetailTelecreditoFile = new ArrayList<TelecreditoDetailFile>();
		try {
			telecreditoFileComp = new TelecreditoFileComp();
			Map<String, Object> mpTelecreditoFile = FileUploadController.processExcelFiles(event);

			telecreditoFileComp.setStrNroCuenta(mpTelecreditoFile.get("0,1").toString().replaceAll("[^\\.0123456789]",""));
			telecreditoFileComp.setStrMoneda(mpTelecreditoFile.get("1,1").toString());
			telecreditoFileComp.setStrTipoCuenta(mpTelecreditoFile.get("2,1").toString());
			//telecreditoFileComp.setLstTelecreditoFileDetail(new ArrayList<TelecreditoDetailFile>());
			Integer intCont = (Integer) mpTelecreditoFile.get("size");
			
			for(int i=5; i<intCont; i++){
				int indexCol=0;
				telecreditoDetail = new TelecreditoDetailFile();
				telecreditoDetail.setStrFecRegistro(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				telecreditoDetail.setStrFecValuta(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				telecreditoDetail.setStrDescOperacion(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				telecreditoDetail.setStrMonto(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				telecreditoDetail.setStrSaldo(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				telecreditoDetail.setStrSucursal(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				telecreditoDetail.setStrNroOperacion(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				telecreditoDetail.setStrHoraOperacion(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				telecreditoDetail.setStrUsuario(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				telecreditoDetail.setStrUTC(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				telecreditoDetail.setStrReferencia(mpTelecreditoFile.get(i+Constante.STR_COMMA+indexCol++).toString());
				lstDetailTelecreditoFile.add(telecreditoDetail);
			}
			telecreditoFileComp.setLstTelecreditoFileDetail(lstDetailTelecreditoFile);
			
			List<Entry> entryList = new ArrayList<Entry>(mpTelecreditoFile.entrySet());
			/*for (Entry temp : entryList) {
				System.out.println(temp.getKey());
				System.out.println(temp.getValue());
				
				String rowIndex = temp.getKey().toString().substring(Constante.INT_ZERO,temp.getKey().toString().lastIndexOf(Constante.STR_COMMA));
				//String colIndex = temp.getKey().toString().substring(temp.getKey().toString().lastIndexOf(Constante.STR_COMMA)+1);
				
				int indexCol = 0;
				telecreditoDetail = new TelecreditoDetailFile();
				
				if(Integer.parseInt(rowIndex)>4){
					telecreditoDetail.setStrFecRegistro(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
					telecreditoDetail.setStrFecValuta(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
					telecreditoDetail.setStrDescOperacion(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
					telecreditoDetail.setStrMonto(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
					telecreditoDetail.setStrSaldo(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
					telecreditoDetail.setStrSucursal(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
					telecreditoDetail.setStrNroOperacion(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
					telecreditoDetail.setStrHoraOperacion(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
					telecreditoDetail.setStrUsuario(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
					telecreditoDetail.setStrUTC(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
					telecreditoDetail.setStrReferencia(mpTelecreditoFile.get(rowIndex+Constante.STR_COMMA+indexCol++).toString());
				}
				telecreditoFileComp.getLstTelecreditoFileDetail().add(telecreditoDetail);
			}*/
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	/* Fin: REQ14-006 Bizarq - 28/10/2014 */
	
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
	public Conciliacion getConciliacionNuevo() {
		return conciliacionNuevo;
	}
	public void setConciliacionNuevo(Conciliacion conciliacionNuevo) {
		this.conciliacionNuevo = conciliacionNuevo;
	}
	public Conciliacion getConciliacionFiltro() {
		return conciliacionFiltro;
	}
	public void setConciliacionFiltro(Conciliacion conciliacionFiltro) {
		this.conciliacionFiltro = conciliacionFiltro;
	}
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	/*public List<Conciliacion> getListaConciliacion() {
		return listaConciliacion;
	}
	public void setListaConciliacion(List<Conciliacion> listaConciliacion) {
		this.listaConciliacion = listaConciliacion;
	}*/
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	public List<Bancocuenta> getListaBancoCuenta() {
		return listaBancoCuenta;
	}
	public void setListaBancoCuenta(List<Bancocuenta> listaBancoCuenta) {
		this.listaBancoCuenta = listaBancoCuenta;
	}
	public Bancocuenta getBancoCuentaFiltro() {
		return bancoCuentaFiltro;
	}
	public void setBancoCuentaFiltro(Bancocuenta bancoCuentaFiltro) {
		this.bancoCuentaFiltro = bancoCuentaFiltro;
	}
	public boolean isDatosValidados() {
		return datosValidados;
	}
	public void setDatosValidados(boolean datosValidados) {
		this.datosValidados = datosValidados;
	}

	
	/* Inicio: REQ14-006 Bizarq - 18/10/2014 */
	public List<Conciliacion> getListaConciliacionBusq() {
		return listaConciliacionBusq;
	}

	public void setListaConciliacionBusq(List<Conciliacion> listaConciliacionBusq) {
		this.listaConciliacionBusq = listaConciliacionBusq;
	}

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	public Conciliacion getRegistroSeleccionado() {
		return registroSeleccionado;
	}

	public void setRegistroSeleccionado(Conciliacion registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}

	public ConciliacionComp getConciliacionCompBusq() {
		return conciliacionCompBusq;
	}

	public void setConciliacionCompBusq(ConciliacionComp conciliacionCompBusq) {
		this.conciliacionCompBusq = conciliacionCompBusq;
	}

	public List<Tabla> getListaTablaTipoDoc() {
		return listaTablaTipoDoc;
	}

	public void setListaTablaTipoDoc(List<Tabla> listaTablaTipoDoc) {
		this.listaTablaTipoDoc = listaTablaTipoDoc;
	}

	public List<Sucursal> getListSucursal() {
		try {
			if (listSucursal == null) {
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listSucursal = facade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listSucursal;
	}


	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}

	
	
	/* Fin: REQ14-006 Bizarq - 18/10/2014 */
}