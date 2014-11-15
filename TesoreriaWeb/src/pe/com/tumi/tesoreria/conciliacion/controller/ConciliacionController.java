/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-006       			01/11/2014     Christian De los Ríos        Se modificó los métodos principales de búsqueda y acciones de Conciliación         
*/
package pe.com.tumi.tesoreria.conciliacion.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;

import pe.com.tumi.common.util.CommonUtils;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
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
import pe.com.tumi.tesoreria.egreso.domain.Saldo;
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
	private Conciliacion	conciliacionAnulacion;
	private Conciliacion	registroSeleccionado;
	private Bancocuenta		bancoCuentaFiltro;
	private Bancocuenta		bancoCuentaFiltroConciliacion;
	private Bancocuenta		bancoCuentaFiltroAnulacion;
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	private List<Conciliacion>	listaConciliacionBusq;
	private ConciliacionComp conciliacionCompBusq;
	private ConciliacionComp conciliacionCompAnul;
	private List<Bancofondo>	listaBanco;
	private List<Tabla> listaTablaTipoDoc;
	private List<Sucursal> listSucursal;
	private List<ConciliacionComp> lstResumen;
	private ConciliacionComp concilResumen;
	private boolean mostrarBotonGrabarConcil;
	private boolean mostrarBotonAnular;
	private TelecreditoFileComp telecreditoFileComp;
	private boolean blnMostrarPanelAnulacion;
	private boolean deshabilitarGrabarConciliacionDiaria;
	private boolean blDeshabilitarBuscar;
	private boolean blDeshabilitaValidarDatos;
	private boolean blDeshabilitarBuscarCuenta;
	private String strMsgErrorAnulaFecha;
	private String strMsgErrorAnulaCuenta;
	private String strMsgErrorAnulaObservacion;
	private String strMsgErrorAnulaPerfil;
	private boolean blDeshabilitarVerConc;
	private Integer intBancoSeleccionado;
	private	List<Tabla>	 		listaMoneda;
	private	List<Bancocuenta>	listaBancoCuentaFiltro;
	private Integer intBancoCuentaSeleccionado;
	private Integer intBancoNuevoSeleccionado;
	private Integer intBancoAnuladoSeleccionado;
	private List<Bancocuenta> listaBancoCuentaFiltroNuevaConc;
	private List<Bancocuenta> listaBancoCuentaFiltroAnulaConc;
	private Integer intBancoCuentaNuevaConcSeleccionado;
	private Integer intBancoCuentaAnuladoConcSeleccionado;
	private boolean deshabilitarBancoCuentaNuevoConc;
	private boolean deshabilitarBancoNuevoConc;
	
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
	private boolean mostrarBtnView;
	private boolean mostrarBtnActualizar;
	private boolean poseePermiso;

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
			conciliacionService = (ConciliacionService)TumiFactory.get(ConciliacionService.class);
			listaMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			conciliacionCompBusq = new ConciliacionComp();
			conciliacionCompBusq.setConciliacion(new Conciliacion());
			conciliacionCompBusq.getConciliacion().setBancoCuenta(new Bancocuenta());
			conciliacionCompAnul = new ConciliacionComp();
			//listaBanco = bancoFacade.obtenerListaBancoExistente(EMPRESA_USUARIO);
			//cargarListaBanco();
			cargarUsuario();
			cargarListaTipoDocumento();
			cargarValoresResumen();
			renderBotones();
			/* Fin: REQ14-006 Bizarq - 26/10/2014 */
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	public boolean isDeshabilitarBancoCuentaNuevoConc() {
		return deshabilitarBancoCuentaNuevoConc;
	}

	public void setDeshabilitarBancoCuentaNuevoConc(
			boolean deshabilitarBancoCuentaNuevoConc) {
		this.deshabilitarBancoCuentaNuevoConc = deshabilitarBancoCuentaNuevoConc;
	}

	public boolean isDeshabilitarBancoNuevoConc() {
		return deshabilitarBancoNuevoConc;
	}

	public void setDeshabilitarBancoNuevoConc(boolean deshabilitarBancoNuevoConc) {
		this.deshabilitarBancoNuevoConc = deshabilitarBancoNuevoConc;
	}

	public Integer getIntBancoNuevoSeleccionado() {
		return intBancoNuevoSeleccionado;
	}

	public void setIntBancoNuevoSeleccionado(Integer intBancoNuevoSeleccionado) {
		this.intBancoNuevoSeleccionado = intBancoNuevoSeleccionado;
	}

	public Integer getIntBancoAnuladoSeleccionado() {
		return intBancoAnuladoSeleccionado;
	}

	public void setIntBancoAnuladoSeleccionado(Integer intBancoAnuladoSeleccionado) {
		this.intBancoAnuladoSeleccionado = intBancoAnuladoSeleccionado;
	}

	public List<Bancocuenta> getListaBancoCuentaFiltroNuevaConc() {
		return listaBancoCuentaFiltroNuevaConc;
	}

	public void setListaBancoCuentaFiltroNuevaConc(
			List<Bancocuenta> listaBancoCuentaFiltroNuevaConc) {
		this.listaBancoCuentaFiltroNuevaConc = listaBancoCuentaFiltroNuevaConc;
	}

	public Integer getIntBancoCuentaNuevaConcSeleccionado() {
		return intBancoCuentaNuevaConcSeleccionado;
	}

	public void setIntBancoCuentaNuevaConcSeleccionado(
			Integer intBancoCuentaNuevaConcSeleccionado) {
		this.intBancoCuentaNuevaConcSeleccionado = intBancoCuentaNuevaConcSeleccionado;
	}

	public Integer getIntBancoCuentaSeleccionado() {
		return intBancoCuentaSeleccionado;
	}

	public void setIntBancoCuentaSeleccionado(Integer intBancoCuentaSeleccionado) {
		this.intBancoCuentaSeleccionado = intBancoCuentaSeleccionado;
	}
	public List<Bancocuenta> getListaBancoCuentaFiltro() {
		return listaBancoCuentaFiltro;
	}

	public void setListaBancoCuentaFiltro(List<Bancocuenta> listaBancoCuentaFiltro) {
		this.listaBancoCuentaFiltro = listaBancoCuentaFiltro;
	}
	public Integer getIntBancoSeleccionado() {
		return intBancoSeleccionado;
	}

	public void setIntBancoSeleccionado(Integer intBancoSeleccionado) {
		this.intBancoSeleccionado = intBancoSeleccionado;
	}
	public void renderBotones(){
		Conciliacionvalidate validate = new Conciliacionvalidate();
		mostrarBotonGrabarConcil = validate.isValidGrabarConcil(usuario);
		mostrarBotonAnular = validate.isValidAnulConcil(usuario);
		
	}
	
	/**
	 * 
	 */
	public void cargarValoresResumen(){
		try{	
			concilResumen = new ConciliacionComp();
			concilResumen.setBdResumenSaldoAnterior(BigDecimal.ZERO);
			concilResumen.setBdResumenDebe(BigDecimal.ZERO);	
			concilResumen.setBdResumenHaber(BigDecimal.ZERO);
			concilResumen.setBdResumenSaldoCaja(BigDecimal.ZERO);
			concilResumen.setBdResumenSaldoConciliacion(BigDecimal.ZERO);
			concilResumen.setIntResumenNroMov(new Integer("0"));
			concilResumen.setBdResumenPorConciliar(BigDecimal.ZERO);
			  
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void cargarListaBanco()throws Exception{
		
		try {
			List<Tabla> listaTablaTipoBanco = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_BANCOS));
			for(Bancofondo banco : listaBanco){
				for(Tabla tabla : listaTablaTipoBanco){
					if(banco.getIntBancoCod().equals(tabla.getIntIdDetalle())){
						banco.setStrEtiqueta(tabla.getStrDescripcion());
					}
				}
			}	
		} catch (Exception e) {
			log.error(e.getMessage(),e);
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
			listaTablaTipoDoc = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_DOCUMENTOGENERAL), "T");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}	
	}
	
	
	/**
	 * Recupera Ingresos y egresos para conciliacion, segun Filtros de Cuenta y Tipo de Documento
	 */
	public void buscarRegistrosConciliacion(){
		List<ConciliacionDetalle> lstConcilDetTotal = null;
		try {
			
			if(conciliacionNuevo.getIntParaDocumentoGeneralFiltro().compareTo(new Integer(0))== 0)
				conciliacionNuevo.setIntParaDocumentoGeneralFiltro(null);
			lstConcilDetTotal= conciliacionFacade.buscarRegistrosConciliacion(conciliacionNuevo);
			if(lstConcilDetTotal != null && lstConcilDetTotal.size() > 0){
				conciliacionNuevo.setListaConciliacionDetalle(new ArrayList<ConciliacionDetalle>());
				conciliacionNuevo.getListaConciliacionDetalle().addAll(lstConcilDetTotal);
				//
				getListaVisualSegunFiltros(lstConcilDetTotal);
				calcularResumen();
			}else{
				mostrarMensaje(Boolean.FALSE, "No se encontraron registros.");
			}
			
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 
	 */
	public void buscarRegistrosConciliacionEdicion(){
		List<ConciliacionDetalle> lstConcilDetTotal = null;
		try {
			
			if(conciliacionNuevo.getIntParaDocumentoGeneralFiltro().compareTo(new Integer(0))== 0)conciliacionNuevo.setIntParaDocumentoGeneralFiltro(null);
			lstConcilDetTotal= conciliacionFacade.buscarRegistrosConciliacionEdicion(conciliacionNuevo);
			if(lstConcilDetTotal != null && lstConcilDetTotal.size() > 0){
				conciliacionNuevo.setListaConciliacionDetalle(new ArrayList<ConciliacionDetalle>());
				conciliacionNuevo.getListaConciliacionDetalle().addAll(lstConcilDetTotal);
				//
				getListaVisualSegunFiltros(lstConcilDetTotal);
				calcularResumen();
			}else{
				mostrarMensaje(Boolean.FALSE, "No se encontraron registros.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 
	 * @param lstDetalleTotal
	 * @return
	 */
	public void getListaVisualSegunFiltros(List<ConciliacionDetalle> lstDetalleTotal){
		List<ConciliacionDetalle> lstVisual = null;
		try {
			lstVisual = new ArrayList<ConciliacionDetalle>();
			
			if(conciliacionNuevo.getIntParaDocumentoGeneralFiltro()== null 
				|| conciliacionNuevo.getIntParaDocumentoGeneralFiltro().toString().equalsIgnoreCase("0")){
				lstVisual.addAll(lstDetalleTotal);
			}else{
				for (ConciliacionDetalle detalleTotal : lstDetalleTotal) {
					
					if(detalleTotal.getIngreso() == null){
						if(detalleTotal.getEgreso().getIntParaDocumentoGeneral().
								compareTo(conciliacionNuevo.getIntParaDocumentoGeneralFiltro())==0){
							lstVisual.add(detalleTotal);
						}
					}
					if(detalleTotal.getEgreso() == null){
						if(detalleTotal.getIngreso().getIntParaDocumentoGeneral().
								compareTo(conciliacionNuevo.getIntParaDocumentoGeneralFiltro())==0){
							lstVisual.add(detalleTotal);
						}
					}
				}			
			}
			conciliacionNuevo.getListaConciliacionDetalleVisual().clear();
			conciliacionNuevo.getListaConciliacionDetalleVisual().addAll(lstVisual);	

		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 
	 */
	public void anularConciliacion(){
		boolean isProcedeAnulacion=false;
		try {
			conciliacionAnulacion.setUsuario(usuario);
			conciliacionCompAnul.setConciliacion(conciliacionAnulacion);
			isProcedeAnulacion = validarAnulacion();
			
			if(isProcedeAnulacion){
				conciliacionFacade.anularConciliacion(conciliacionCompAnul);
				mostrarMensaje(Boolean.TRUE, "Se realizo éxitosamente el Proceso de Anulación.");
				registrarNuevo = Boolean.FALSE; 
				mostrarPanelInferior = Boolean.FALSE;
				mostrarMensajeError = Boolean.TRUE;
				mostrarMensajeExito = Boolean.FALSE;
				blnMostrarPanelAnulacion = Boolean.FALSE;
				limpiarMensajesAnulacion();
			}else{
				mostrarMensaje(Boolean.FALSE, "No se pudo realizar el Proceso de Anulación.");
			}
			
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error en el proceso de Anulación.");
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean validarAnulacion(){
		boolean isError = true;
		limpiarMensajesAnulacion();
		ocultarMensaje();
		
		Conciliacionvalidate validate = new Conciliacionvalidate();
		
		if(conciliacionCompAnul.getDtFechaAnulDesde() == null){
			isError = false;
			strMsgErrorAnulaFecha="Ingresar Fecha de Anulación.";
		}
		
		if(conciliacionCompAnul.getStrObservacionAnula() == null
			|| conciliacionCompAnul.getStrObservacionAnula().isEmpty()){
			isError = false;
			strMsgErrorAnulaObservacion = "Ingresar Observación de Anulación.";
		}
		
		if(conciliacionCompAnul.getConciliacion().getBancoCuenta() == null){
			isError = false;
			strMsgErrorAnulaCuenta="Ingresar Cuenta Bancaria de Anulación.";
		}
		
		if(!validate.isValidAnulConcil(usuario)){
			isError = false;
			strMsgErrorAnulaPerfil="Solo el Perfil de Jefe de Contabilidad puede Anular Conciliación.";
		}
		
		return isError;
	}
	
	/**
	 * 
	 */
	public void limpiarMensajesAnulacion(){
		setStrMsgErrorAnulaFecha("");
		setStrMsgErrorAnulaObservacion("");
		setStrMsgErrorAnulaCuenta("");
		setStrMsgErrorAnulaPerfil("");
		intBancoAnuladoSeleccionado = null;
		intBancoCuentaAnuladoConcSeleccionado = null;
	}
	
	/**
	 * 
	 */
	public void grabarConciliacionDiaria(){
		try {
			if(!isValidConciliacion(conciliacionNuevo)){
				conciliacionNuevo.setUsuario(usuario);
				calcularCabecera();
				conciliacionFacade.grabarConciliacionDiaria(conciliacionNuevo);
				deshabilitarPanelInferior();
				mostrarMensaje(Boolean.TRUE, "Se guardó éxitosamente la conciliación diaria.");
			}
			
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro de la Conciliacion Diaria.");
			log.error(e.getMessage(),e);
		}
	}
	
	public List<Bancocuenta> seleccionarBanco(Integer intBancoSeleccionado) throws Exception{
		//log.info("--seleccionarBanco");
		if (intBancoSeleccionado==null) intBancoSeleccionado=0;
			
		List<Bancocuenta> listaBancoCuenta = new ArrayList<Bancocuenta>();
		if(intBancoSeleccionado.equals(new Integer(0))){
			return listaBancoCuenta;
		}
		Bancofondo bancoFondoTemp = new Bancofondo();
		bancoFondoTemp.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
		bancoFondoTemp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		List<Bancofondo> listaBancoFondoTemp = bancoFacade.buscarBancoFondo(bancoFondoTemp);
		String strEtiqueta = "";
		//log.info("--intBancoSeleccionado:"+intBancoSeleccionado);
		for(Bancofondo bancoFondo : listaBancoFondoTemp){
			//log.info(bancoFondo);
			if(bancoFondo.getIntBancoCod().equals(intBancoSeleccionado)){
				for(Bancocuenta bancoCuenta : bancoFondo.getListaBancocuenta()){
					//log.info(bancoCuenta);
					strEtiqueta = bancoCuenta.getStrNombrecuenta()+" - "
									+bancoCuenta.getCuentaBancaria().getStrNroCuentaBancaria()+" - "
									+obtenerEtiquetaTipoMoneda(bancoCuenta.getCuentaBancaria().getIntMonedaCod());
					bancoCuenta.setStrEtiqueta(strEtiqueta);
					listaBancoCuenta.add(bancoCuenta);
				}
			}
		}
		return listaBancoCuenta;
	}
	private String obtenerEtiquetaTipoMoneda(Integer intTipoMoneda){
		for(Tabla tabla : listaMoneda){
			if(tabla.getIntIdDetalle().equals(intTipoMoneda)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	public void seleccionarBancoFiltro(){
		try{
			log.info("--seleccionarBancoOrigen");
			listaBancoCuentaFiltro = seleccionarBanco(intBancoSeleccionado);
			//listaBancoCuentaDestino solo puede tener BancoCuenta's con moneda del bancocuenta seleccionado
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	public void seleccionarNuevoConcBancoFiltro(){
		try{
			log.info("--seleccionarBancoOrigen");
			listaBancoCuentaFiltroNuevaConc = seleccionarBanco(intBancoNuevoSeleccionado);
			//listaBancoCuentaDestino solo puede tener BancoCuenta's con moneda del bancocuenta seleccionado
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarAnularConcBancoFiltro(){
		try{
			log.info("--seleccionarBancoOrigen");
			listaBancoCuentaFiltroAnulaConc = seleccionarBanco(intBancoAnuladoSeleccionado);
			//listaBancoCuentaDestino solo puede tener BancoCuenta's con moneda del bancocuenta seleccionado
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarBancoCuentaFiltro(){
		try{
			Bancocuenta bancoCuentaSeleccionado = null;
			if(listaBancoCuentaFiltro != null){
				for(Bancocuenta bancocuenta : listaBancoCuentaFiltro){
					if(intBancoCuentaSeleccionado.equals(bancocuenta.getId().getIntItembancocuenta())){
						bancoCuentaSeleccionado = bancocuenta;
						break;
					}
				}
			}
			if(conciliacionCompBusq!=null && conciliacionCompBusq.getConciliacion()!=null && bancoCuentaSeleccionado != null){
				conciliacionCompBusq.getConciliacion().setBancoCuenta(bancoCuentaSeleccionado);
				log.info("IntItembancocuenta(): "+ bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				log.info("IntItembancofondo: "+bancoCuentaSeleccionado.getId().getIntItembancofondo());
				conciliacionCompBusq.setIntBusqItemBancoCuenta(bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				conciliacionCompBusq.setIntBusqItemBancoFondo(bancoCuentaSeleccionado.getId().getIntItembancofondo());
			}
			log.info(bancoCuentaSeleccionado);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	public void seleccionarBancoCuentaNuevoConc(){
		try{
			Bancocuenta bancoCuentaSeleccionado = null;
			if(listaBancoCuentaFiltroNuevaConc != null){
				for(Bancocuenta bancocuenta : listaBancoCuentaFiltroNuevaConc){
					if(intBancoCuentaNuevaConcSeleccionado.equals(bancocuenta.getId().getIntItembancocuenta())){
						bancoCuentaSeleccionado = bancocuenta;
						break;
					}
				}
			}
			if(conciliacionNuevo!=null && bancoCuentaSeleccionado!= null){
				conciliacionNuevo.setBancoCuenta(bancoCuentaSeleccionado);
				log.info("IntItembancocuenta(): "+ bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				log.info("IntItembancofondo: "+bancoCuentaSeleccionado.getId().getIntItembancofondo());
				conciliacionNuevo.setIntPersEmpresa(bancoCuentaSeleccionado.getId().getIntEmpresaPk());
				conciliacionNuevo.setIntItemBancoCuenta(bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				conciliacionNuevo.setIntItemBancoFondo(bancoCuentaSeleccionado.getId().getIntItembancofondo());
			}
			log.info(bancoCuentaSeleccionado);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarBancoCuentaAnulaConc(){
		try{
			Bancocuenta bancoCuentaSeleccionado = null;
			if(listaBancoCuentaFiltroAnulaConc != null){
				for(Bancocuenta bancocuenta : listaBancoCuentaFiltroAnulaConc){
					if(intBancoCuentaAnuladoConcSeleccionado.equals(bancocuenta.getId().getIntItembancocuenta())){
						bancoCuentaSeleccionado = bancocuenta;
						break;
					}
				}
			}
			if(conciliacionCompAnul!=null && bancoCuentaSeleccionado!= null){
				conciliacionCompAnul.getConciliacion().setBancoCuenta(bancoCuentaSeleccionado);
				log.info("IntItembancocuenta(): "+ bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				log.info("IntItembancofondo: "+bancoCuentaSeleccionado.getId().getIntItembancofondo());
				conciliacionCompAnul.getConciliacion().setIntPersEmpresa(bancoCuentaSeleccionado.getId().getIntEmpresaPk());
				conciliacionCompAnul.getConciliacion().setIntItemBancoCuenta(bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				conciliacionCompAnul.getConciliacion().setIntItemBancoFondo(bancoCuentaSeleccionado.getId().getIntItembancofondo());
			}
			log.info(bancoCuentaSeleccionado);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
		//habilitarGrabar = Boolean.FALSE;
		blnMostrarPanelAnulacion = Boolean.FALSE;
		ocultarMensaje();
		limpiarMensajesAnulacion();
		/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	}

	/**
	 * 
	 */
	public void grabar(){
		log.info("--grabar");
		boolean isCrear = false;
		try {
			Conciliacionvalidate validate = new Conciliacionvalidate();
			ocultarMensaje();
			cargarUsuario();
			
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.FALSE;
			/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
			calcularCabecera();
			if(conciliacionNuevo.getId().getIntItemConciliacion() == null){
				isCrear = validate.isValidCrearConciliacion(conciliacionNuevo);
				if(isCrear){
					//logging();
					
					conciliacionNuevo.setIntParaEstado(Constante.INT_EST_CONCILIACION_REGISTRADO); // Estado Registrado					
					conciliacionNuevo = conciliacionService.grabarConciliacion(conciliacionNuevo);
					mostrarMensaje(Boolean.TRUE, "Se guardó éxitosamente la Conciliación Bancaria.");
				}else{
					mostrarMensaje(Boolean.FALSE, "Ya existe Conciliación Bancaria con las caracteristicas ingresadas. Se cancela registro.");
				}

			}else{
				//logging();
				conciliacionNuevo = conciliacionService.grabarConciliacion(conciliacionNuevo);
				mostrarMensaje(Boolean.TRUE, "Se actualizó éxitosamente la Conciliación Bancaria.");
			}

			/* Fin: REQ14-006 Bizarq - 26/10/2014 */
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro de la Conciliacion Bancaria.");
			log.error(e.getMessage(),e);
		}
	}	
	

	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	
	
	public void logging(){
		if(conciliacionNuevo.getListaConciliacionDetalleVisual()!= null && conciliacionNuevo.getListaConciliacionDetalleVisual().size()>0 ){
			System.out.println("============================== xxx VISUAL xxx ======================================");
			for (ConciliacionDetalle visual: conciliacionNuevo.getListaConciliacionDetalleVisual()) {
				int cont = 0;
				if(visual.getEgreso() != null){
					System.out.println("Egreso "+ cont++ + ":");
					System.out.println("visual.getIntPersEmpresaEgreso() -- "+visual.getIntPersEmpresaEgreso());
					System.out.println("visual.getIntItemEgresoGeneral() -- "+visual.getIntItemEgresoGeneral());
				}
				if(visual.getIngreso() != null){
					System.out.println("Ingreso "+ cont++ + ":");
					System.out.println("visual.getIntPersEmpresaIngreso() -- "+visual.getIntPersEmpresaIngreso());
					System.out.println("visual.getIntItemIngresoGeneral() -- "+visual.getIntItemIngresoGeneral());
				}
				System.out.println("=============================================================================");

			}
		}
		
		if(conciliacionNuevo.getListaConciliacionDetalle()!= null && conciliacionNuevo.getListaConciliacionDetalle().size()>0 ){
			System.out.println("============================== xxx REAL xxx ======================================");
			for (ConciliacionDetalle real: conciliacionNuevo.getListaConciliacionDetalle()) {
				int cont = 0;
				if(real.getEgreso() != null){
					System.out.println("Egreso "+ cont++ + ":");
					System.out.println("real.getIntPersEmpresaEgreso() -- "+real.getIntPersEmpresaEgreso());
					System.out.println("real.getIntItemEgresoGeneral() -- "+real.getIntItemEgresoGeneral());
				}
				if(real.getIngreso() != null){
					System.out.println("Ingreso "+ cont++ + ":");
					System.out.println("real.getIntPersEmpresaIngreso() -- "+real.getIntPersEmpresaIngreso());
					System.out.println("real.getIntItemIngresoGeneral() -- "+real.getIntItemIngresoGeneral());
				}
				System.out.println("=============================================================================");
			}
		}
	}
	
	
	private boolean isValidConciliacion(Conciliacion conciliacion){
		boolean isValid = false;
		Date dtLastArqueo = null;
		Saldo dtoSaldo = null;
		Date dtLastPreviousUtilDay = null;
		try {
			//1. Verificar el arqueo del día anterior
			dtLastArqueo = egresoFacade.obtenerUltimaFechaSaldo(EMPRESA_USUARIO);
			dtLastPreviousUtilDay = CommonUtils.getPreviousUtilDay(conciliacion.getTsFechaConciliacion(), Constante.INT_ONE);//Día anterior
			if((dtLastArqueo!=null && dtLastPreviousUtilDay!=null)
					&& (dtLastArqueo.compareTo(dtLastPreviousUtilDay))!=0){
				mostrarMensaje(Boolean.FALSE, "No se ha realizado arqueo correspondiente al " + Constante.sdf.format(dtLastPreviousUtilDay));
				return true;
			}
			
			//2. Verificar los saldos del día anterior
			dtoSaldo = conciliacionFacade.obtenerSaldoUltimaFechaSaldo(EMPRESA_USUARIO);
			if((dtoSaldo!=null && dtoSaldo.getId().getDtFechaSaldo()!=null && dtLastPreviousUtilDay!=null)
					&& (dtoSaldo.getId().getDtFechaSaldo().compareTo(dtLastPreviousUtilDay))!=0){
				mostrarMensaje(Boolean.FALSE, "No se ha realizado la generación de saldos correspondiente a " + Constante.sdf.format(dtLastPreviousUtilDay));
				return true;
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return isValid;
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
	
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	/**
	 * 
	 */
	public void buscar(){
		try{
			/* Inicio: REQ14-006 Bizarq - 18/10/2014 */
			seleccionarBancoCuentaFiltro();
			listaConciliacionBusq = new ArrayList<Conciliacion>();
			listaConciliacionBusq = conciliacionFacade.getListFilter(conciliacionCompBusq);
			//conciliacionCompBusq = new ConciliacionComp();
			ocultarMensaje();
			/* Fin: REQ14-006 Bizarq - 18/10/2014 */
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	
	/**
	 * 
	 */
	public void limpiar(){
		try{
			conciliacionCompBusq = new ConciliacionComp();
			conciliacionCompBusq.setConciliacion(new Conciliacion());
			conciliacionCompBusq.getConciliacion().setBancoCuenta(new Bancocuenta());
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public String getLimpiarConciliacion(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_CONCILIACION_BANCARIA);
		log.info("POSEE PERMISO: " + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			limpiar();
			deshabilitarPanelInferior();
			listaConciliacionBusq = new ArrayList<Conciliacion>();
			blnMostrarPanelAnulacion = Boolean.FALSE;
			renderBotones();
			
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		return "";
	}
	
	/**
	 * 
	 * @param event
	 */
	/*public void seleccionarRegistro(ActionEvent event){
		try{
			cargarUsuario();
			ocultarMensaje();
			registroSeleccionado = (Conciliacion)event.getComponent().getAttributes().get("item");
			log.info(registroSeleccionado);
			if(registroSeleccionado.getIntParaEstado().compareTo(Constante.INT_EST_CONCILIACION_REGISTRADO)==0
					|| registroSeleccionado.getIntParaEstado().compareTo(Constante.INT_EST_CONCILIACION_ANULADO)==0 ){
				irModificarConciliacion();
			}else{
				mostrarMensaje(Boolean.TRUE, "Solo se poueden Modificar las Conciliaciones en estado Registrado y/o Anulado.");
			}
			
		
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}*/
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (Conciliacion)event.getComponent().getAttributes().get("item");
			log.info("reg selec:"+registroSeleccionado);
			/*mostrarBtnActualizar = true;
			if(registroSeleccionado.getIntParaEstado().compareTo(Constante.INT_EST_CONCILIACION_ANULADO)== 0
				|| registroSeleccionado.getIntParaEstado().compareTo(Constante.INT_EST_CONCILIACION_CONCILIADO)== 0){
				mostrarBtnActualizar = false;
			}*/
			mostrarBtnView = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Actualiza el campo check de la lista conciliacion detalle a guardar,
	 * segun la lista detalle vista 
	 * @param event
	 */
	public void onclickCheck(ActionEvent event){
		ConciliacionDetalle concilDetVisual;
		Boolean blIsEgreso = Boolean.FALSE;
		Boolean blIsIngreso =Boolean.FALSE;
		try{
			concilDetVisual = (ConciliacionDetalle)event.getComponent().getAttributes().get("item");
			log.info("reg selec:"+concilDetVisual);
			
			if(concilDetVisual != null){
				if(concilDetVisual.getEgreso() != null && concilDetVisual.getIngreso() == null)
					blIsEgreso = Boolean.TRUE;
				if(concilDetVisual.getIngreso() != null && concilDetVisual.getEgreso() == null)
					blIsIngreso = Boolean.TRUE;
				
				if(conciliacionNuevo.getListaConciliacionDetalle() != null && conciliacionNuevo.getListaConciliacionDetalle().size() >0){
					
					if(blIsEgreso){
						for (ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalle()) {
							if(detalle.getEgreso()!= null){
								if(concilDetVisual.getIntItemEgresoGeneral().compareTo(detalle.getIntItemEgresoGeneral())== 0
									&& concilDetVisual.getIntPersEmpresaEgreso().compareTo(detalle.getIntPersEmpresaEgreso())== 0){
									
									if(concilDetVisual.getBlIndicadorCheck()){
										detalle.setIntIndicadorCheck(new Integer(1));
									}else{
										detalle.setIntIndicadorCheck(new Integer(0));
									}
								}
							}
						}
					}
					if(blIsIngreso){
						for (ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalle()) {
							if(detalle.getIngreso()!= null){
								if(concilDetVisual.getIntItemIngresoGeneral().compareTo(detalle.getIntItemIngresoGeneral())== 0
									&& concilDetVisual.getIntPersEmpresaIngreso().compareTo(detalle.getIntPersEmpresaIngreso())== 0){
									if(concilDetVisual.getBlIndicadorCheck()){
										detalle.setIntIndicadorCheck(new Integer(1));
									}else{
										detalle.setIntIndicadorCheck(new Integer(0));
									}
								}
							}
						}
					}
					
				}
			}

			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	public void verRegistro(){
		try{
			
			blDeshabilitarBuscarCuenta = Boolean.TRUE;
			blDeshabilitarVerConc = Boolean.TRUE;
			
			if(registroSeleccionado.getIntParaEstado().equals(Constante.INT_EST_CONCILIACION_REGISTRADO)){
				deshabilitarNuevo = Boolean.FALSE;
				habilitarGrabar = Boolean.TRUE;
				conciliacionNuevo = conciliacionService.getConciliacionEdit(registroSeleccionado.getId());
								
			}else{
				deshabilitarNuevo = Boolean.TRUE;
				habilitarGrabar = Boolean.FALSE;
			}
			mostrarPanelInferior = Boolean.TRUE;
			//mostrarBotonGrabarConcil = Boolean.FALSE;
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void irModificarConciliacion(){
		try{
			blDeshabilitarBuscarCuenta = true;
			blDeshabilitarVerConc = Boolean.FALSE;
			
			if(registroSeleccionado.getIntParaEstado().compareTo(Constante.INT_EST_CONCILIACION_REGISTRADO)==0
					|| registroSeleccionado.getIntParaEstado().compareTo(Constante.INT_EST_CONCILIACION_ANULADO)==0 ){
				//habilitarGrabar = Boolean.TRUE;
				//mostrarBotonGrabarConcil = Boolean.TRUE;
				registrarNuevo = Boolean.FALSE;
				deshabilitarNuevo = Boolean.FALSE;
				mostrarPanelInferior = Boolean.TRUE;			
				datosValidados = Boolean.TRUE;
				blDeshabilitarBuscar = Boolean.TRUE;
				blDeshabilitaValidarDatos = Boolean.FALSE;
				blnMostrarPanelAnulacion = Boolean.FALSE;
				
				conciliacionNuevo = conciliacionFacade.getConciliacionEdit(registroSeleccionado.getId());
				calcularResumen();
			} else {
				mostrarMensaje(Boolean.TRUE, "Solo se poueden Modificar las Conciliaciones en estado Registrado y/o Anulado.");
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	/*
	public void eliminarRegistro(){
		try{
			Conciliacion conciliacionEliminar = registroSeleccionado;			
			
			buscar();
			//mostrarMensaje(Boolean.TRUE, "Se eliminó correctamente la Solicitud Personal.");
		}catch (Exception e){
			//mostrarMensaje(Boolean.FALSE, "Hubo un error durante la eliminación de la Solicitud Personal.");
			log.error(e.getMessage(),e);
		}
	}
	*/
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	public void habilitarPanelInferior(){
		try{
			//cargarUsuario();
			ocultarMensaje();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			datosValidados = Boolean.FALSE;
			
			conciliacionNuevo = new Conciliacion();
			conciliacionNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			conciliacionNuevo.setListaConciliacionDetalle(new ArrayList<ConciliacionDetalle>());
			conciliacionNuevo.setListaConciliacionDetalleVisual(new ArrayList<ConciliacionDetalle>());
			conciliacionNuevo.setTsFechaConciliacion(MyUtil.obtenerFechaActual());
			/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
			conciliacionCompAnul = new ConciliacionComp();
			conciliacionCompAnul.setConciliacion(new Conciliacion());
			conciliacionCompAnul.getConciliacion().getId().setIntPersEmpresa(EMPRESA_USUARIO);
			conciliacionCompAnul.setDtFechaAnulDesde(null);

			blDeshabilitarBuscar = Boolean.FALSE;
			blDeshabilitaValidarDatos = Boolean.TRUE;
			blnMostrarPanelAnulacion = Boolean.FALSE;
			blDeshabilitarBuscarCuenta = false;
			blDeshabilitarVerConc = Boolean.FALSE;
			deshabilitarBancoNuevoConc = Boolean.FALSE;
			intBancoNuevoSeleccionado = null;
			intBancoAnuladoSeleccionado = null;
			deshabilitarBancoCuentaNuevoConc = Boolean.FALSE;
			intBancoCuentaNuevaConcSeleccionado = null;
			listaBancoCuentaFiltroNuevaConc = null;
			/* Fin: REQ14-006 Bizarq - 26/10/2014 */
			
			//mostrarBotonGrabarConcil = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	public void habilitarPanelAnulacion(){
		try{
			cargarUsuario();
			ocultarMensaje();
			//registrarNuevo = Boolean.TRUE;
			blnMostrarPanelAnulacion = Boolean.TRUE;
			blDeshabilitarBuscar = Boolean.FALSE;
			blDeshabilitaValidarDatos = Boolean.FALSE;
			mostrarPanelInferior = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			datosValidados = Boolean.FALSE;
			blnMostrarPanelAnulacion = Boolean.TRUE;
			
			conciliacionAnulacion = new Conciliacion();
			conciliacionAnulacion.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			conciliacionAnulacion.setTsFechaConciliacion(MyUtil.obtenerFechaActual());
			
			conciliacionCompAnul = new ConciliacionComp();
			conciliacionCompAnul.setConciliacion(new Conciliacion());
			conciliacionCompAnul.getConciliacion().getId().setIntPersEmpresa(EMPRESA_USUARIO);
			conciliacionCompAnul.setDtFechaAnulDesde(null);
			
			intBancoAnuladoSeleccionado = null;
			intBancoCuentaAnuladoConcSeleccionado = null;
			
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/*public void mostrarMensaje(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExito = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mensajeOperacion = mensaje;
		}else{
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.TRUE;
			mensajeOperacion = mensaje;
		}
	}*/
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
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
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	public void abrirPopUpBuscarBancoCuentaConciliacion(){
		try{
			bancoCuentaFiltroConciliacion = new Bancocuenta();
			bancoCuentaFiltroConciliacion.getId().setIntEmpresaPk(EMPRESA_USUARIO);
			bancoCuentaFiltroConciliacion.setIntEmpresacuentaPk(EMPRESA_USUARIO);
			bancoCuentaFiltroConciliacion.setBancofondo(new Bancofondo());
			bancoCuentaFiltroConciliacion.setIntPeriodocuenta(MyUtil.obtenerAñoActual()-1);
			bancoCuentaFiltroConciliacion.getBancofondo().setIntBancoCod(Constante.PARAM_T_BANCOS_BANCOCREDITO);
			
			buscarBancoCuentaConciliacion();
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 */
	public void abrirPopUpBuscarBancoCuentaAnul(){
		try{
			bancoCuentaFiltroAnulacion = new Bancocuenta();
			bancoCuentaFiltroAnulacion.getId().setIntEmpresaPk(EMPRESA_USUARIO);
			bancoCuentaFiltroAnulacion.setIntEmpresacuentaPk(EMPRESA_USUARIO);
			bancoCuentaFiltroAnulacion.setBancofondo(new Bancofondo());
			bancoCuentaFiltroAnulacion.setIntPeriodocuenta(MyUtil.obtenerAñoActual()-1);
			bancoCuentaFiltroAnulacion.getBancofondo().setIntBancoCod(Constante.PARAM_T_BANCOS_BANCOCREDITO);
			
			buscarBancoCuentaAnulacion();
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	public void buscarBancoCuenta(){
		try{
			if(bancoCuentaFiltro.getStrNumerocuenta()!=null && bancoCuentaFiltro.getStrNumerocuenta().isEmpty())
				bancoCuentaFiltro.setStrNumerocuenta(null);
			
			listaBancoCuenta = bancoFacade.buscarListaBancoCuenta(bancoCuentaFiltro);
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	public void buscarBancoCuentaConciliacion(){
		try{
			if(bancoCuentaFiltroConciliacion.getStrNumerocuenta()!=null && bancoCuentaFiltroConciliacion.getStrNumerocuenta().isEmpty())
				bancoCuentaFiltroConciliacion.setStrNumerocuenta(null);
			
			listaBancoCuenta = bancoFacade.buscarListaBancoCuenta(bancoCuentaFiltroConciliacion);
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 */
	public void buscarBancoCuentaAnulacion(){
		try{
			if(bancoCuentaFiltroAnulacion.getStrNumerocuenta()!=null && bancoCuentaFiltroAnulacion.getStrNumerocuenta().isEmpty())
				bancoCuentaFiltroAnulacion.setStrNumerocuenta(null);
			
			listaBancoCuenta = bancoFacade.buscarListaBancoCuenta(bancoCuentaFiltroAnulacion);
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	public void seleccionarBancoCuenta(ActionEvent event){
		try{
			Bancocuenta bancoCuentaSeleccionado = (Bancocuenta)event.getComponent().getAttributes().get("item");
			/* Inicio: REQ14-006 Bizarq - 28/10/2014 */
			if(conciliacionCompBusq!=null && conciliacionCompBusq.getConciliacion()!=null){
				conciliacionCompBusq.getConciliacion().setBancoCuenta(bancoCuentaSeleccionado);
				log.info("IntItembancocuenta(): "+ bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				log.info("IntItembancofondo: "+bancoCuentaSeleccionado.getId().getIntItembancofondo());
				conciliacionCompBusq.setIntBusqItemBancoCuenta(bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				conciliacionCompBusq.setIntBusqItemBancoFondo(bancoCuentaSeleccionado.getId().getIntItembancofondo());
			} else {
				conciliacionCompBusq.getConciliacion().setBancoCuenta(bancoCuentaSeleccionado);
			}
			/* Fin: REQ14-006 Bizarq - 28/10/2014 */
			log.info(bancoCuentaSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	public void seleccionarBancoCuentaConciliacion(ActionEvent event){
		try{
			Bancocuenta bancoCuentaSeleccionado = (Bancocuenta)event.getComponent().getAttributes().get("item");
			if(conciliacionNuevo!=null){
				conciliacionNuevo.setBancoCuenta(bancoCuentaSeleccionado);
				log.info("IntItembancocuenta(): "+ bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				log.info("IntItembancofondo: "+bancoCuentaSeleccionado.getId().getIntItembancofondo());
				conciliacionNuevo.setIntPersEmpresa(bancoCuentaSeleccionado.getId().getIntEmpresaPk());
				conciliacionNuevo.setIntItemBancoCuenta(bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				conciliacionNuevo.setIntItemBancoFondo(bancoCuentaSeleccionado.getId().getIntItembancofondo());
			} else {
				conciliacionNuevo.setBancoCuenta(bancoCuentaSeleccionado);
			}
			log.info(bancoCuentaSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void seleccionarBancoCuentaAnulacion(ActionEvent event){
		try{
			Bancocuenta bancoCuentaSeleccionado = (Bancocuenta)event.getComponent().getAttributes().get("item");
			if(conciliacionAnulacion!=null){
				conciliacionAnulacion.setBancoCuenta(bancoCuentaSeleccionado);
				log.info("IntItembancocuenta(): "+ bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				log.info("IntItembancofondo: "+bancoCuentaSeleccionado.getId().getIntItembancofondo());
				conciliacionAnulacion.setIntPersEmpresa(bancoCuentaSeleccionado.getId().getIntEmpresaPk());
				conciliacionAnulacion.setIntItemBancoCuenta(bancoCuentaSeleccionado.getId().getIntItembancocuenta());
				conciliacionAnulacion.setIntItemBancoFondo(bancoCuentaSeleccionado.getId().getIntItembancofondo());
			} else {
				conciliacionAnulacion.setBancoCuenta(bancoCuentaSeleccionado);
			}
			log.info(bancoCuentaSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	public void validarDatos(){
		try{
			datosValidados = Boolean.TRUE;
			/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
			buscarRegistrosConciliacion();
			if(conciliacionNuevo.getListaConciliacionDetalle() != null && conciliacionNuevo.getListaConciliacionDetalle().size() >0){
				blDeshabilitaValidarDatos = Boolean.FALSE;
				blDeshabilitarBuscar = Boolean.TRUE;
				deshabilitarBancoNuevoConc = Boolean.TRUE;
				deshabilitarBancoCuentaNuevoConc = Boolean.TRUE;
			}
			/* Fin: REQ14-006 Bizarq - 26/10/2014 */

		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	public void limpiarTablaResumen(){
		concilResumen = new ConciliacionComp();
		concilResumen.setBdResumenSaldoAnterior(BigDecimal.ZERO);
		concilResumen.setBdResumenDebe(BigDecimal.ZERO);	
		concilResumen.setBdResumenHaber(BigDecimal.ZERO);
		concilResumen.setBdResumenSaldoCaja(BigDecimal.ZERO);
		concilResumen.setBdResumenSaldoConciliacion(BigDecimal.ZERO);
		concilResumen.setIntResumenNroMov(new Integer("0"));
		concilResumen.setBdResumenPorConciliar(BigDecimal.ZERO);
		lstResumen = new ArrayList<ConciliacionComp>();
	}
	
	/**
	*/
	public void calcularResumen(){
		BigDecimal bdTotalConciliacion;
		BigDecimal bdResumenPorConciliar;
		
		try{
			bdTotalConciliacion= BigDecimal.ZERO;
			bdResumenPorConciliar= BigDecimal.ZERO;
			
			limpiarTablaResumen();
			concilResumen.setBdResumenSaldoAnterior(conciliacionNuevo.getBdMontoSaldoInicial() == null ? BigDecimal.ZERO :conciliacionNuevo.getBdMontoSaldoInicial());
			// bdResumenDebe / bdResumenHaber
			BigDecimal bdResumenDebe = BigDecimal.ZERO;
			BigDecimal bdResumenHaber = BigDecimal.ZERO;
			Integer intRegNoConciliados = 0;

			if(conciliacionNuevo.getListaConciliacionDetalleVisual() != null || conciliacionNuevo.getListaConciliacionDetalleVisual().size() == 0){
				concilResumen.setIntResumenNroMov(conciliacionNuevo.getListaConciliacionDetalleVisual().size());
				//bdTotalConciliacion
				for(ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalleVisual()){
					bdTotalConciliacion = bdTotalConciliacion.add((detalle.getBdMontoDebe() == null ? ( detalle.getBdMontoHaber() == null ? BigDecimal.ZERO : detalle.getBdMontoHaber() ): detalle.getBdMontoDebe()));					
				}

				// bdResumenPorConciliar
				for(ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalleVisual()){
					if(detalle.getIntIndicadorCheck() == null || detalle.getIntIndicadorCheck() == 0){
						if(detalle.getEgreso() == null){
							bdResumenPorConciliar = bdResumenPorConciliar.add(detalle.getBdMontoDebe()==null?BigDecimal.ZERO:detalle.getBdMontoDebe());
						}else{
							bdResumenPorConciliar = bdResumenPorConciliar.add(detalle.getBdMontoHaber()==null?BigDecimal.ZERO:detalle.getBdMontoHaber());
						}
					}
				}
				
				// bdResumenSaldoConciliacion
				concilResumen.setBdResumenSaldoConciliacion(bdTotalConciliacion.subtract(bdResumenPorConciliar).setScale(2, RoundingMode.HALF_UP));
				concilResumen.setBdResumenPorConciliar(bdResumenPorConciliar);

				for(ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalleVisual()){
					bdResumenDebe  = bdResumenDebe.add(detalle.getIngreso()!= null ? detalle.getIngreso().getBdMontoTotal() : BigDecimal.ZERO);
					bdResumenHaber = bdResumenHaber.add(detalle.getEgreso()!= null ? detalle.getEgreso().getBdMontoTotal() : BigDecimal.ZERO);
					
					if(detalle.getBlIndicadorConci()== null || detalle.getBlIndicadorConci().equals(false)){
						intRegNoConciliados = intRegNoConciliados + 1;					
					}
				}
				concilResumen.setBdDebe(bdResumenDebe);
				concilResumen.setBdHaber(bdResumenHaber);
				concilResumen.setBdResumenDebe(bdResumenDebe);
				concilResumen.setBdResumenHaber(bdResumenHaber);
				concilResumen.setBdResumenSaldoCaja(concilResumen.getBdResumenSaldoAnterior().add(bdResumenDebe).subtract(bdResumenHaber).setScale(2, RoundingMode.HALF_UP));
				lstResumen.add(concilResumen);
			
			}		
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 
	 */
	public void calcularCabecera(){
		BigDecimal bdTotalConciliacion;
		BigDecimal bdResumenPorConciliar;
		
		try{
			bdTotalConciliacion= BigDecimal.ZERO;
			bdResumenPorConciliar= BigDecimal.ZERO;
						
			conciliacionNuevo.setBdMontoSaldoInicial(concilResumen.getBdResumenSaldoAnterior());

			// bdResumenDebe / bdResumenHaber
			BigDecimal bdResumenDebe = BigDecimal.ZERO;
			BigDecimal bdResumenHaber = BigDecimal.ZERO;
			Integer intRegConciliados = 0;
			Integer intRegNoConciliados = 0;

			if(conciliacionNuevo.getListaConciliacionDetalleVisual() != null || conciliacionNuevo.getListaConciliacionDetalleVisual().size() == 0){
				conciliacionNuevo.setIntNroMovimientos(conciliacionNuevo.getListaConciliacionDetalleVisual().size());
				//bdTotalConciliacion
				for(ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalleVisual()){
					bdTotalConciliacion = bdTotalConciliacion.add((detalle.getBdMontoDebe() == null ? ( detalle.getBdMontoHaber() == null ? BigDecimal.ZERO : detalle.getBdMontoHaber() ): detalle.getBdMontoDebe()));					
				}

				// bdResumenPorConciliar
				for(ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalleVisual()){
					if(detalle.getIntIndicadorCheck() == null || detalle.getIntIndicadorCheck() == 0){
						if(detalle.getEgreso() == null){
							bdResumenPorConciliar = bdResumenPorConciliar.add(detalle.getBdMontoDebe()==null?BigDecimal.ZERO:detalle.getBdMontoDebe());
						}else{
							bdResumenPorConciliar = bdResumenPorConciliar.add(detalle.getBdMontoHaber()==null?BigDecimal.ZERO:detalle.getBdMontoHaber());
						}
					}
				}
	
				conciliacionNuevo.setBdPorConciliar(bdResumenPorConciliar);

				for(ConciliacionDetalle detalle : conciliacionNuevo.getListaConciliacionDetalleVisual()){
					bdResumenDebe  = bdResumenDebe.add(detalle.getIngreso()!= null ? detalle.getIngreso().getBdMontoTotal() : BigDecimal.ZERO);
					bdResumenHaber = bdResumenHaber.add(detalle.getEgreso()!= null ? detalle.getEgreso().getBdMontoTotal() : BigDecimal.ZERO);
					
					if(detalle.getBlIndicadorConci()== null || detalle.getBlIndicadorConci().equals(false)){
						intRegNoConciliados = intRegNoConciliados + 1;					
					}
				}
				
				intRegConciliados = conciliacionNuevo.getIntNroMovimientos() - intRegNoConciliados;
				conciliacionNuevo.setIntRegistrosConciliados(intRegConciliados);
				conciliacionNuevo.setIntRegistrosNoConciliados(intRegNoConciliados);

				conciliacionNuevo.setBdDebe(bdResumenDebe);
				conciliacionNuevo.setBdHaber(bdResumenHaber);
				conciliacionNuevo.setBdMontoDebe(bdResumenDebe);
				conciliacionNuevo.setBdMontoHaber(bdResumenHaber);
				
				//bdResumenSaldoCaja
				conciliacionNuevo.setBdSaldoCaja(concilResumen.getBdSaldoCaja());
			
			}		
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */					
										
										
	/* Inicio: REQ14-006 Bizarq - 28/10/2014 */
	public void adjuntarDocTelecredito(UploadEvent event){
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
			/*
			List<Entry> entryList = new ArrayList<Entry>(mpTelecreditoFile.entrySet());
			for (Entry temp : entryList) {
				System.out.println(temp.getKey());
				System.out.println(temp.getValue());
				
				String rowIndex = temp.getKey().toString().substring(Constante.INT_ZERO,temp.getKey().toString().lastIndexOf(Constante.STR_COMMA));
				//String colIndex = temp.getKey().toString().substring(temp.getKey().toString().lastIndexOf(Constante.STR_COMMA)+1);
				
				int indexCol = 0;
				telecreditoDetail = new TelecreditoDetailFile();
				
				if(Integer.parseInt(rowIndex)>4){
					telecreditoDetail.setStrFecRegistro(mpTelecreditoFile.get(Constante.sdf.format(rowIndex+Constante.STR_COMMA+indexCol++)).toString());
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
			}
			matchTelecreditoFileAgainstLstConcDet(telecreditoFileComp);
			*/
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @author bizarq
	 * Método encargado de realizar el match entre el archivo telecrédito y la lista de conciliaciones actual.
	 * @param telecreditoFileComp
	 * 
	 * */
	public void matchTelecreditoFileAgainstLstConcDet(){
		//Verificando el Nº de cta. antes de iniciar las validaciones...
		if((telecreditoFileComp==null || (telecreditoFileComp.getLstTelecreditoFileDetail()==null || telecreditoFileComp.getLstTelecreditoFileDetail().isEmpty()) )){
			mostrarMensaje(Boolean.FALSE, "No se ha cargado ningún archivo Telecrédito");
			return;
		}
		if((conciliacionNuevo.getListaConciliacionDetalle()==null || conciliacionNuevo.getListaConciliacionDetalle().isEmpty())){
			mostrarMensaje(Boolean.FALSE, "No existen conciliaciones para comparar.");
			return;
		}
		
		if(telecreditoFileComp.getStrNroCuenta().equals(conciliacionNuevo.getBancoCuenta().getStrNumerocuenta())){
			if((telecreditoFileComp.getLstTelecreditoFileDetail()!=null && !telecreditoFileComp.getLstTelecreditoFileDetail().isEmpty())
					&& (conciliacionNuevo.getListaConciliacionDetalle()!=null && !conciliacionNuevo.getListaConciliacionDetalle().isEmpty())){
				lstDetailFile:
				for(TelecreditoDetailFile detailFile : telecreditoFileComp.getLstTelecreditoFileDetail()){
					//Verificando que la fecha de conciliación sea la misma 
					for(ConciliacionDetalle concDetalle : conciliacionNuevo.getListaConciliacionDetalle()){
						if(Constante.sdf.format(conciliacionNuevo.getTsFechaConciliacion()).equals(detailFile.getStrFecRegistro())){
							if(concDetalle.getIngreso()!=null){
								if(detailFile.getStrNroOperacion().trim().equals(concDetalle.getIngreso().getStrNumeroOperacion().trim())
										&& new BigDecimal(detailFile.getStrMonto()).compareTo(concDetalle.getIngreso().getBdMontoTotal())==Constante.INT_ZERO){
									concDetalle.setIntIndicadorCheck(Constante.INT_ONE);
									concDetalle.setBlIndicadorCheck(Boolean.TRUE);
								}
							} else if(concDetalle.getEgreso()!=null){
								String strNroOperacion = null;
								if(concDetalle.getEgreso().getIntNumeroPlanilla()!=null){
									strNroOperacion = concDetalle.getEgreso().getIntNumeroPlanilla().toString();
								}else if(concDetalle.getEgreso().getIntNumeroCheque()!=null){
									strNroOperacion = concDetalle.getEgreso().getIntNumeroCheque().toString();
								}else if(concDetalle.getEgreso().getIntNumeroTransferencia()!=null) {
									strNroOperacion = concDetalle.getEgreso().getIntNumeroTransferencia().toString();
								} else {
									//mostrarMensaje(Boolean.FALSE,"Inconsistencia de datos. No se encontró número de operación para el egreso: " + concDetalle.getEgreso().getId().getIntItemEgresoGeneral());
									break lstDetailFile;
								}
								if(detailFile.getStrNroOperacion().trim().equals(strNroOperacion.trim())
										&& new BigDecimal(detailFile.getStrMonto()).compareTo(concDetalle.getEgreso().getBdMontoTotal())==Constante.INT_ZERO){
									concDetalle.setIntIndicadorCheck(Constante.INT_ONE);
									concDetalle.setBlIndicadorCheck(Boolean.TRUE);
								}
							} else break;
						} else break;
					}
				}
			}
			
		} else {
			//mostrarMensaje(Boolean.FALSE,"El archivo seleccionado no muestra coincidencia con el número de cuenta.");
			return;
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

	public List<ConciliacionComp> getLstResumen() {
		return lstResumen;
	}

	public void setLstResumen(List<ConciliacionComp> lstResumen) {
		this.lstResumen = lstResumen;
	}

	public ConciliacionComp getConcilResumen() {
		return concilResumen;
	}

	public void setConcilResumen(ConciliacionComp concilResumen) {
		this.concilResumen = concilResumen;
	}
	
	public TelecreditoFileComp getTelecreditoFileComp() {
		return telecreditoFileComp;
	}

	public void setTelecreditoFileComp(TelecreditoFileComp telecreditoFileComp) {
		this.telecreditoFileComp = telecreditoFileComp;
	}
	

	public boolean isMostrarBotonGrabarConcil() {
		return mostrarBotonGrabarConcil;
	}

	public void setMostrarBotonGrabarConcil(boolean mostrarBotonGrabarConcil) {
		this.mostrarBotonGrabarConcil = mostrarBotonGrabarConcil;
	}

	public boolean isDeshabilitarGrabarConciliacionDiaria() {
		return deshabilitarGrabarConciliacionDiaria;
	}

	public void setDeshabilitarGrabarConciliacionDiaria(
			boolean deshabilitarGrabarConciliacionDiaria) {
		this.deshabilitarGrabarConciliacionDiaria = deshabilitarGrabarConciliacionDiaria;
	}

	public Bancocuenta getBancoCuentaFiltroConciliacion() {
		return bancoCuentaFiltroConciliacion;
	}

	public void setBancoCuentaFiltroConciliacion(
			Bancocuenta bancoCuentaFiltroConciliacion) {
		this.bancoCuentaFiltroConciliacion = bancoCuentaFiltroConciliacion;
	}

	public Conciliacion getConciliacionAnulacion() {
		return conciliacionAnulacion;
	}

	public void setConciliacionAnulacion(Conciliacion conciliacionAnulacion) {
		this.conciliacionAnulacion = conciliacionAnulacion;
	}

	public Bancocuenta getBancoCuentaFiltroAnulacion() {
		return bancoCuentaFiltroAnulacion;
	}

	public void setBancoCuentaFiltroAnulacion(Bancocuenta bancoCuentaFiltroAnulacion) {
		this.bancoCuentaFiltroAnulacion = bancoCuentaFiltroAnulacion;
	}

	public ConciliacionComp getConciliacionCompAnul() {
		return conciliacionCompAnul;
	}

	public void setConciliacionCompAnul(ConciliacionComp conciliacionCompAnul) {
		this.conciliacionCompAnul = conciliacionCompAnul;
	}

	public List<Bancofondo> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List<Bancofondo> listaBanco) {
		this.listaBanco = listaBanco;
	}

	public boolean isMostrarBotonAnular() {
		return mostrarBotonAnular;
	}

	public void setMostrarBotonAnular(boolean mostrarBotonAnular) {
		this.mostrarBotonAnular = mostrarBotonAnular;
	}

	public boolean isBlnMostrarPanelAnulacion() {
		return blnMostrarPanelAnulacion;
	}

	public void setBlnMostrarPanelAnulacion(boolean blnMostrarPanelAnulacion) {
		this.blnMostrarPanelAnulacion = blnMostrarPanelAnulacion;
	}

	/**
	 * @return the blDeshabilitarBuscar
	 */
	public boolean isBlDeshabilitarBuscar() {
		return blDeshabilitarBuscar;
	}

	/**
	 * @param blDeshabilitarBuscar the blDeshabilitarBuscar to set
	 */
	public void setBlDeshabilitarBuscar(boolean blDeshabilitarBuscar) {
		this.blDeshabilitarBuscar = blDeshabilitarBuscar;
	}

	/**
	 * @return the blDeshabilitaValidarDatos
	 */
	public boolean isBlDeshabilitaValidarDatos() {
		return blDeshabilitaValidarDatos;
	}

	/**
	 * @param blDeshabilitaValidarDatos the blDeshabilitaValidarDatos to set
	 */
	public void setBlDeshabilitaValidarDatos(boolean blDeshabilitaValidarDatos) {
		this.blDeshabilitaValidarDatos = blDeshabilitaValidarDatos;
	}

	/**
	 * @return the blDeshabilitarBuscarCuenta
	 */
	public boolean isBlDeshabilitarBuscarCuenta() {
		return blDeshabilitarBuscarCuenta;
	}

	/**
	 * @param blDeshabilitarBuscarCuenta the blDeshabilitarBuscarCuenta to set
	 */
	public void setBlDeshabilitarBuscarCuenta(boolean blDeshabilitarBuscarCuenta) {
		this.blDeshabilitarBuscarCuenta = blDeshabilitarBuscarCuenta;
	}

	/**
	 * @return the strMsgErrorAnulaFecha
	 */
	public String getStrMsgErrorAnulaFecha() {
		return strMsgErrorAnulaFecha;
	}

	/**
	 * @param strMsgErrorAnulaFecha the strMsgErrorAnulaFecha to set
	 */
	public void setStrMsgErrorAnulaFecha(String strMsgErrorAnulaFecha) {
		this.strMsgErrorAnulaFecha = strMsgErrorAnulaFecha;
	}

	/**
	 * @return the strMsgErrorAnulaCuenta
	 */
	public String getStrMsgErrorAnulaCuenta() {
		return strMsgErrorAnulaCuenta;
	}

	/**
	 * @param strMsgErrorAnulaCuenta the strMsgErrorAnulaCuenta to set
	 */
	public void setStrMsgErrorAnulaCuenta(String strMsgErrorAnulaCuenta) {
		this.strMsgErrorAnulaCuenta = strMsgErrorAnulaCuenta;
	}

	/**
	 * @return the strMsgErrorAnulaObservacion
	 */
	public String getStrMsgErrorAnulaObservacion() {
		return strMsgErrorAnulaObservacion;
	}

	/**
	 * @param strMsgErrorAnulaObservacion the strMsgErrorAnulaObservacion to set
	 */
	public void setStrMsgErrorAnulaObservacion(String strMsgErrorAnulaObservacion) {
		this.strMsgErrorAnulaObservacion = strMsgErrorAnulaObservacion;
	}

	/**
	 * @return the strMsgErrorAnulaPerfil
	 */
	public String getStrMsgErrorAnulaPerfil() {
		return strMsgErrorAnulaPerfil;
	}

	/**
	 * @param strMsgErrorAnulaPerfil the strMsgErrorAnulaPerfil to set
	 */
	public void setStrMsgErrorAnulaPerfil(String strMsgErrorAnulaPerfil) {
		this.strMsgErrorAnulaPerfil = strMsgErrorAnulaPerfil;
	}

	public boolean isMostrarBtnView() {
		return mostrarBtnView;
	}

	public void setMostrarBtnView(boolean mostrarBtnView) {
		this.mostrarBtnView = mostrarBtnView;
	}

	public boolean isBlDeshabilitarVerConc() {
		return blDeshabilitarVerConc;
	}

	public void setBlDeshabilitarVerConc(boolean blDeshabilitarVerConc) {
		this.blDeshabilitarVerConc = blDeshabilitarVerConc;
	}

	/**
	 * @return the mostrarBtnActualizar
	 */
	public boolean isMostrarBtnActualizar() {
		return mostrarBtnActualizar;
	}

	/**
	 * @param mostrarBtnActualizar the mostrarBtnActualizar to set
	 */
	public void setMostrarBtnActualizar(boolean mostrarBtnActualizar) {
		this.mostrarBtnActualizar = mostrarBtnActualizar;
	}
	
	public List<Bancocuenta> getListaBancoCuentaFiltroAnulaConc() {
		return listaBancoCuentaFiltroAnulaConc;
	}

	public void setListaBancoCuentaFiltroAnulaConc(
			List<Bancocuenta> listaBancoCuentaFiltroAnulaConc) {
		this.listaBancoCuentaFiltroAnulaConc = listaBancoCuentaFiltroAnulaConc;
	}

	public Integer getIntBancoCuentaAnuladoConcSeleccionado() {
		return intBancoCuentaAnuladoConcSeleccionado;
	}

	public void setIntBancoCuentaAnuladoConcSeleccionado(
			Integer intBancoCuentaAnuladoConcSeleccionado) {
		this.intBancoCuentaAnuladoConcSeleccionado = intBancoCuentaAnuladoConcSeleccionado;
	}
	/* Fin: REQ14-006 Bizarq - 18/10/2014 */
}