package pe.com.tumi.tesoreria.parametro.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.Fondodetalle;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;


public class BancoFondoController {

	protected static Logger log = Logger.getLogger(BancoFondoController.class);
	
	
	PlanCuentaFacadeRemote planCuentaFacade;
	EmpresaFacadeRemote empresaFacade;
	GeneralFacadeRemote generalFacade;
	BancoFacadeLocal bancoFacade;
	TablaFacadeRemote tablaFacade;
	
	private List listaAnios;
	private List listaBancoFondo;
	private List listaBancoCuenta;
	private List listaBancoCuentaCheque;
	private	List listaPlanCuenta;
	private List listaSucursal;

	private List 	listaSucursalFondoFijo;
	private List listaSubSucursal;
	private List listaFondoDetalleCuenta;
	private List listaFondoDetalleDocumento;
	private List listaFondoDetalleSobregiro;
	private List<Bancofondo> listaBancoFondoBD;
	
	
	private Bancofondo 	bancoNuevo;
	private Bancofondo 	fondoNuevo;
	private Bancocuenta bancoCuentaAgregar;
	private Bancocuentacheque 	bancoCuentaChequeAgregar;
	private	PlanCuenta 	planCuentaFiltro;
	private Fondodetalle fondoDetalleAgregar;
	private Bancofondo 	bancoFondoFiltro;
	private Bancofondo 	registroSeleccionado;
	private TipoArchivo	tipoArchivo2;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private final int 	cantidadAñosLista = 4;
	private String 		mensajePopUp;
	
	private Integer		intTipoBanco;
	private Integer 	intTipoBusquedaPlanCuenta;
	private Integer 	intTipoPopUpPlanCuenta;
	private int 		intAccionRegistro;	
	private final int 	AGREGAR_REGISTRO = 1;
	private final int 	EDITAR_REGISTRO = 2;
	
	private	Integer 	intItemInterfazSeleccionado;
	private Integer		intItemInterfazCorrelativo;
	private	Integer		intIdSucursal;
	private	Integer		EMPRESA_USUARIO;
	
	private boolean 	mostrarBtnEliminar;
	private boolean 	mostrarMensajeExito;
	private boolean 	mostrarMensajeError;
	private boolean 	deshabilitarNuevo;
	private boolean 	registrarNuevo;
	private boolean 	habilitarGrabar;
	private boolean 	habilitarBanco;
	private boolean 	habilitarFondoFijo;
	private boolean 	habilitarTextoPlanCuenta;
	private boolean 	habilitarBancoCuentaCheque;
	private	boolean		iniciaRegistrarNuevo;
	private boolean		habilitarBotonMostrarBanco;
	private boolean		habilitarBotonMostrarFondo;
	private boolean		habilitarPeriodoPlanCuenta;
	private boolean		mostrarMensajePopUp;
	
	
	public BancoFondoController(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public void cargarValoresIniciales(){
		try{
			EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
			listaBancoFondo = new ArrayList<Bancofondo>();			
			
			habilitarBanco = Boolean.FALSE;
			habilitarFondoFijo = Boolean.FALSE;
			iniciaRegistrarNuevo = Boolean.TRUE;
			habilitarBotonMostrarBanco = Boolean.TRUE;
			habilitarBotonMostrarFondo = Boolean.TRUE;
			registrarNuevo = Boolean.TRUE;
			bancoFondoFiltro = new Bancofondo();
			bancoFondoFiltro.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO);

			listaFondoDetalleCuenta = new ArrayList<Fondodetalle>();
			listaFondoDetalleDocumento = new ArrayList<Fondodetalle>();
			listaFondoDetalleSobregiro = new ArrayList<Fondodetalle>();
			fondoNuevo = new Bancofondo();

			listaBancoCuenta = new ArrayList<Bancocuenta>();
			listaBancoCuentaCheque = new ArrayList<Bancocuentacheque>();
			bancoNuevo = new Bancofondo();

			planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			
			tipoArchivo2 = generalFacade.getTipoArchivoPorPk(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BANCOCUENTACHEQUE);
			listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
			
			cargarListaAnios();
			cargarListaSucursalFondoFijo();
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
	
	
	private void cargarListaSucursalFondoFijo() throws NumberFormatException, BusinessException{
		listaSucursalFondoFijo = new ArrayList<Tabla>();
		listaSucursalFondoFijo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
		Sucursal sucursal;
		Tabla tabla;
		for(Object o : listaSucursal){
			 sucursal = (Sucursal)o;
			 tabla = new Tabla();
			 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
			 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
			 listaSucursalFondoFijo.add(tabla);
		}
	}
	
	public void buscar(){
		try{
			if(bancoFondoFiltro.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
				bancoFondoFiltro.setIntEstadoCod(null);
			}
			if(bancoFondoFiltro.getIntTipoBancoFondoFiltro().equals(Constante.PARAM_T_BANCOFONDOFIJO_BANCO)){
				bancoFondoFiltro.setIntMonedaCod(null);
			}
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			listaBancoFondo = bancoFacade.buscarBancoFondo(bancoFondoFiltro);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (Bancofondo)event.getComponent().getAttributes().get("item");
			if(registroSeleccionado.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				cargarRegistro();
				mostrarBtnEliminar= Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
				registrarNuevo = Boolean.FALSE;
				mostrarMensajeExito = Boolean.FALSE;
				mostrarMensajeError = Boolean.FALSE;
				deshabilitarNuevo = Boolean.TRUE;
				habilitarBotonMostrarBanco = Boolean.FALSE;
				habilitarBotonMostrarFondo = Boolean.FALSE;
			}else{
				mostrarBtnEliminar = Boolean.TRUE;
			}
			log.info("reg selec:"+registroSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	private void cargarRegistro() throws BusinessException, EJBFactoryException{
		log.info("cargarRegistro");
		
		if(registroSeleccionado.getIntTipoBancoFondoFiltro().equals(Constante.PARAM_T_BANCOFONDOFIJO_BANCO)){
			bancoNuevo = registroSeleccionado;
			listaBancoCuenta = new ArrayList<Bancocuenta>();
			listaBancoCuentaCheque = new ArrayList<Bancocuentacheque>();
			intItemInterfazCorrelativo = new Integer(0);
			
			List<Bancocuentacheque> listaBancoCuentaChequeBD;
			for(Bancocuenta bancoCuenta : bancoNuevo.getListaBancocuenta()){
				log.info(bancoCuenta);
				
				listaBancoCuentaChequeBD = bancoFacade.getListaBancoCuentaChequePorBancoCuenta(bancoCuenta);
				intItemInterfazCorrelativo = intItemInterfazCorrelativo + 1;
				bancoCuenta.setIntItemInterfaz(intItemInterfazCorrelativo);
				
				obtenerPlanCuenta(bancoCuenta);
				
				listaBancoCuenta.add(bancoCuenta);
				
				for(Bancocuentacheque bancoCuentaCheque : listaBancoCuentaChequeBD){
					log.info(bancoCuentaCheque);
										
					bancoCuentaCheque.setBancoCuenta(bancoCuenta);
					listaBancoCuentaCheque.add(bancoCuentaCheque);
				}
			}
			
			habilitarBanco = Boolean.TRUE;
			habilitarFondoFijo = Boolean.FALSE;
			habilitarBotonMostrarBanco = Boolean.TRUE;
			habilitarBotonMostrarFondo = Boolean.FALSE;
			
		}else if(registroSeleccionado.getIntTipoBancoFondoFiltro().equals(Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO)){
			fondoNuevo = registroSeleccionado;
			listaFondoDetalleCuenta = new ArrayList<Fondodetalle>();
			listaFondoDetalleDocumento = new ArrayList<Fondodetalle>();
			listaFondoDetalleSobregiro = new ArrayList<Fondodetalle>();
			
			for(Fondodetalle fondoDetalle : fondoNuevo.getListaFondodetalle()){
				log.info(fondoDetalle);
				
				if(fondoDetalle.getIntTotalsucursalCod()!=null){
					aplicarEtiquetaTotalSucursal(fondoDetalle);
				}else{
					obtenerSucursalySubsucursal(fondoDetalle);
				}
				
				
				if(fondoDetalle.getIntCodigodetalle().equals(Constante.CODIGODETALLE_CUENTACONTABLE)){
					obtenerPlanCuenta(fondoDetalle);
					listaFondoDetalleCuenta.add(fondoDetalle);				
				
				}else if(fondoDetalle.getIntCodigodetalle().equals(Constante.CODIGODETALLE_DOCUMENTO)){
					listaFondoDetalleDocumento.add(fondoDetalle);				
				
				}else if(fondoDetalle.getIntCodigodetalle().equals(Constante.CODIGODETALLE_SOBREGIRO)){
					obtenerPlanCuenta(fondoDetalle);
					listaFondoDetalleSobregiro.add(fondoDetalle);
				}
			}
			habilitarBanco = Boolean.FALSE;
			habilitarFondoFijo = Boolean.TRUE;
			habilitarBotonMostrarBanco = Boolean.FALSE;
			habilitarBotonMostrarFondo = Boolean.TRUE;
		}
	}
	
	private void obtenerSucursalySubsucursal(Fondodetalle fondoDetalle) throws BusinessException{
		SucursalId sucursalId = new SucursalId();
		sucursalId.setIntIdSucursal(fondoDetalle.getIntIdsucursal());
		Sucursal sucursal = new Sucursal();
		sucursal.setId(sucursalId);
		
		//fondoDetalle.setSucursal(empresaFacade.getSucursalPorPK(sucursal));
		
		for(Object o : listaSucursal){
			Sucursal sucursal2 = (Sucursal)o;
			if(sucursal2.getId().getIntIdSucursal().equals(fondoDetalle.getIntIdsucursal())){
				fondoDetalle.setSucursal(sucursal2);
				fondoDetalle.setStrEtiquetaSucursal(sucursal2.getJuridica().getStrSiglas());
				break;
			}
		}
		
		SubSucursalPK subSucursalId = new SubSucursalPK();
		subSucursalId.setIntPersEmpresaPk(fondoDetalle.getIntEmpresasucursalPk());
		subSucursalId.setIntIdSucursal(fondoDetalle.getIntIdsucursal());
		subSucursalId.setIntIdSubSucursal(fondoDetalle.getIntIdsubsucursal());
		
		fondoDetalle.setSubSucursal(empresaFacade.getSubSucursalPorPk(subSucursalId));
		fondoDetalle.setStrEtiquetaSubsucursal(fondoDetalle.getSubSucursal().getStrDescripcion());
	}
	
	private void obtenerPlanCuenta(Fondodetalle fondoDetalle) throws BusinessException{
		PlanCuentaId planCuentaId = new PlanCuentaId();
		planCuentaId.setIntEmpresaCuentaPk(fondoDetalle.getIntEmpresacuentaPk());
		planCuentaId.setIntPeriodoCuenta(fondoDetalle.getIntPeriodocuenta());
		planCuentaId.setStrNumeroCuenta(fondoDetalle.getStrNumerocuenta());
		fondoDetalle.setPlanCuenta(planCuentaFacade.getPlanCuentaPorPk(planCuentaId));
		
	}
	
	private void obtenerPlanCuenta(Bancocuenta bancoCuenta) throws BusinessException, EJBFactoryException{
		PlanCuentaId planCuentaId = new PlanCuentaId();
		planCuentaId.setIntEmpresaCuentaPk(bancoCuenta.getIntEmpresacuentaPk());
		planCuentaId.setIntPeriodoCuenta(bancoCuenta.getIntPeriodocuenta());
		planCuentaId.setStrNumeroCuenta(bancoCuenta.getStrNumerocuenta());
		planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
		bancoCuenta.setPlanCuenta(planCuentaFacade.getPlanCuentaPorPk(planCuentaId));
		
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
		log.info("--grabar");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		Boolean registrarBanco = Boolean.FALSE;
		Boolean registrarFondo = Boolean.FALSE;
		Bancofondo bancoFondoTemp = new Bancofondo();
		bancoFondoTemp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		List<Bancofondo> listaBancofondoTemp;
		try{
			
			for(Object o : listaBancoCuentaCheque){
				Bancocuentacheque bancoCuentaCheque = (Bancocuentacheque)o;
				Bancocuenta bancoCuenta = bancoCuentaCheque.getBancoCuenta();
				bancoCuenta.getListaBancocuentacheque().add(bancoCuentaCheque);
			}
			for(Object o : listaBancoCuenta){
				((Bancocuenta)o).getCuentaBancaria().setIntBancoCod(bancoNuevo.getIntBancoCod());
			}
			bancoNuevo.setListaBancocuenta(listaBancoCuenta);			
			
			fondoNuevo.getListaFondodetalle().clear();
			for(Object o : listaFondoDetalleCuenta){
				fondoNuevo.getListaFondodetalle().add((Fondodetalle)o);
			}
			for(Object o : listaFondoDetalleDocumento){
				fondoNuevo.getListaFondodetalle().add((Fondodetalle)o);
			}
			for(Object o : listaFondoDetalleSobregiro){
				fondoNuevo.getListaFondodetalle().add((Fondodetalle)o);
			}
			
			if(!bancoNuevo.getListaBancocuenta().isEmpty()){
				registrarBanco = Boolean.TRUE;
			}
			
			if(!fondoNuevo.getListaFondodetalle().isEmpty()){
				registrarFondo = Boolean.TRUE;
			}
			
			if(!registrarBanco && !registrarFondo){
				mensaje = "No se ha registrado. No se ha añadido un banco o fondo.";
			}
			log.info("registrarNuevo:"+registrarNuevo);
			if(registrarNuevo){
				log.info("registrarBanco:"+registrarBanco);
				log.info("registrarFondo:"+registrarFondo);
				if(registrarBanco){				
					
					bancoNuevo.getId().setIntEmpresaPk(EMPRESA_USUARIO);
					bancoNuevo.setTsFecharegistro(new Timestamp(new Date().getTime()));
					bancoNuevo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					bancoFacade.grabarBanco(bancoNuevo);
					mensaje = "Se registró el Banco correctamente.";
				}
				if(registrarFondo){			

					//validar combinacion unica de tipofondofijo y moneda
					bancoFondoTemp.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO);
					listaBancofondoTemp = bancoFacade.buscarBancoFondo(bancoFondoTemp);
					for(Bancofondo bancoFondo : listaBancofondoTemp){
						if(bancoFondo.getIntTipoFondoFijo().equals(fondoNuevo.getIntTipoFondoFijo())
							&& bancoFondo.getIntMonedaCod().equals(fondoNuevo.getIntMonedaCod())){
							mensaje = "Ya existe un fondo fijo registrado con ese nombre y moneda.";
							return;
						}
					}
					
					//validar  que al menos haya un fondo detalle tipo cuenta contable o sobregiro *yessica 19-09-12*
					//porque al momento de usar apertura necesita una cuenta contable con fondo detalle
					if(listaFondoDetalleCuenta.isEmpty() && listaFondoDetalleSobregiro.isEmpty()){
						mensaje = "Necesita ingresar al menos una Cuenta Contable o Sobregiro.";
						return;
					}
					
					fondoNuevo.getId().setIntEmpresaPk(EMPRESA_USUARIO);
					fondoNuevo.setTsFecharegistro(new Timestamp(new Date().getTime()));
					fondoNuevo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					bancoFacade.grabarFondo(fondoNuevo);
					mensaje = "Se registró el Fondo Fijo correctamente.";
				}
				
				if(registrarBanco && registrarFondo){
					mensaje = "Se registró el Banco y Fondo Fijo correctamente.";
				}
			}else{
				if(registrarBanco){
					bancoFacade.modificarBanco(bancoNuevo);
					mensaje = "Se modificó el Banco correctamente.";
				}
				
				if(registrarFondo){					
					bancoFacade.modificarFondo(fondoNuevo);
					mensaje = "Se modificó el Fondo Fijo correctamente.";
				}
				
				if(registrarBanco && registrarFondo){
					mensaje = "Se modificó el Banco y Fondo Fijo correctamente.";
				}
			}
			
			buscar();
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			habilitarBotonMostrarBanco = Boolean.FALSE;
			habilitarBotonMostrarFondo = Boolean.FALSE;
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de registro del cierre de cuenta.";
			log.error(e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	private void cargarListaBancoFondoBD() throws BusinessException{
		Bancofondo bancoFondoTemp = new Bancofondo();
		bancoFondoTemp.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO);
		listaBancoFondoBD = bancoFacade.buscarBancoFondo(bancoFondoTemp);
	}
	
	public void mostrarPanelBanco(ActionEvent event){
		try{
			habilitarBanco = Boolean.TRUE;
			habilitarFondoFijo = Boolean.FALSE;						
			
			if(registrarNuevo && iniciaRegistrarNuevo){
				bancoNuevo = new Bancofondo();
				fondoNuevo = new Bancofondo();
				bancoNuevo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				fondoNuevo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				
				intTipoBanco = new Integer(0);
				intItemInterfazCorrelativo = new Integer(0);

				listaBancoCuenta = new ArrayList<Bancocuenta>();
				listaBancoCuentaCheque = new ArrayList<Bancocuentacheque>();
				listaFondoDetalleCuenta = new ArrayList<Fondodetalle>();
				listaFondoDetalleDocumento = new ArrayList<Fondodetalle>();
				listaFondoDetalleSobregiro = new ArrayList<Fondodetalle>();
				
				cargarListaBancoFondoBD();
			}
			log.info("intItemInterfazCorrelativo:"+intItemInterfazCorrelativo);
			
			
			habilitarGrabar = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;	
			ocultarMensaje();
			iniciaRegistrarNuevo = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void mostrarPanelFondoFijo(ActionEvent event){
		try{
			habilitarBanco = Boolean.FALSE;
			habilitarFondoFijo = Boolean.TRUE;
			
			if(registrarNuevo && iniciaRegistrarNuevo){
				bancoNuevo = new Bancofondo();
				fondoNuevo = new Bancofondo();
				bancoNuevo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				fondoNuevo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				intTipoBanco = new Integer(0);
				intItemInterfazCorrelativo = new Integer(0);

				listaBancoCuenta = new ArrayList<Bancocuenta>();
				listaBancoCuentaCheque = new ArrayList<Bancocuentacheque>();
				listaFondoDetalleCuenta = new ArrayList<Fondodetalle>();
				listaFondoDetalleDocumento = new ArrayList<Fondodetalle>();
				listaFondoDetalleSobregiro = new ArrayList<Fondodetalle>();
				
				cargarListaBancoFondoBD();
			}	
			
			habilitarGrabar = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			ocultarMensaje();
			iniciaRegistrarNuevo = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void cancelar(ActionEvent event){
		habilitarBanco = Boolean.FALSE;
		habilitarFondoFijo = Boolean.FALSE;
		registrarNuevo = Boolean.FALSE; 
		habilitarGrabar = Boolean.FALSE;
		ocultarMensaje();
		iniciaRegistrarNuevo = Boolean.TRUE;
		habilitarBotonMostrarBanco = Boolean.TRUE;
		habilitarBotonMostrarFondo = Boolean.TRUE;
		registrarNuevo = Boolean.TRUE;
	}
	
	public void modificarRegistro(){
		try{
			cargarRegistro();
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			cargarListaBancoFondoBD();
			ocultarMensaje();
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void eliminarRegistro(){
		String mensaje = "";
		boolean exito = Boolean.FALSE;
		try{
			listaBancoFondo.remove(registroSeleccionado);
			bancoFacade.eliminarBancoFondo(registroSeleccionado);
			mensaje = "Se eliminó correctamente el registro.";
			exito = Boolean.TRUE;
		}catch(Exception e){
			mensaje = "Ocurrió un error eliminando el registro.";			
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito, mensaje);
		}
	}
	

	

	
	//Métodos para el popUP  de CUENTA BANCARIA
	
	public void abrirPopUpCuentaBancaria(){
		try{
			intAccionRegistro = AGREGAR_REGISTRO;
			bancoCuentaAgregar = new Bancocuenta();
			habilitarPeriodoPlanCuenta = Boolean.TRUE;
			
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			planCuentaFiltro.setIntMovimiento(Constante.MOVIMIENTO_OPERACIONAL);
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);					
			planCuentaFiltro.getId().setIntPeriodoCuenta(-1);
			
			mostrarMensajePopUp = Boolean.FALSE;
			//log.info("planCuentaFiltro:"+planCuentaFiltro);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpPlanCuenta(ActionEvent event){
		try{
			intTipoPopUpPlanCuenta = (Integer)event.getComponent().getAttributes().get("tipo");
			
			log.info("intTipoPopUpPlanCuenta:"+intTipoPopUpPlanCuenta);
			//log.info("--abrirPopUpPlanCuenta");
			intTipoBusquedaPlanCuenta = 0;
			
			log.info("planCuentaFiltro:"+planCuentaFiltro);
			/*planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);					
			planCuentaFiltro.getId().setIntPeriodoCuenta(-1);*/
			habilitarTextoPlanCuenta = Boolean.FALSE;	
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
	
	public void buscarPlanCuenta(){
		try{
			//log.info("--antes");
			/*for(Object o : listaPlanCuenta){
				log.info((PlanCuenta)o);
			}*/
			planCuentaFiltro.setIntMovimiento(Constante.MOVIMIENTO_OPERACIONAL);
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
				log.info(planCuentaFiltro);
				listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);
			}
			if(planCuentaFiltro.getId().getIntPeriodoCuenta()!=null){
				List<PlanCuenta> listaPlanCuentaTemp = new ArrayList<PlanCuenta>();
				for(Object o : listaPlanCuenta){
					PlanCuenta planCuenta = (PlanCuenta)o;
					if(planCuenta.getId().getIntPeriodoCuenta().equals(planCuentaFiltro.getId().getIntPeriodoCuenta())){
						listaPlanCuentaTemp.add(planCuenta);
					}
				}
				listaPlanCuenta = listaPlanCuentaTemp;
			}
			
			
			//Orden la lista de PlanCuenta
			List<PlanCuenta> listaPlanCuentaTemp = listaPlanCuenta;
			Collections.sort(listaPlanCuentaTemp, new Comparator<PlanCuenta>() {
			    public int compare(PlanCuenta o1, PlanCuenta o2) {
			        if (o1.getId().getStrNumeroCuenta().length() > o2.getId().getStrNumeroCuenta().length()) {
			            return 1;
			         } else if (o1.getId().getStrNumeroCuenta().length() < o2.getId().getStrNumeroCuenta().length()) {
			            return -1;
			         } else { 
			            return o1.getId().getStrNumeroCuenta().compareTo(o2.getId().getStrNumeroCuenta());
			         }
			    }
			});
			listaPlanCuenta = listaPlanCuentaTemp;
			for(Object o : listaPlanCuenta){
				log.info((PlanCuenta)o);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarPlanCuenta(ActionEvent event){
		try{
			log.info("intTipoPopUpPlanCuenta:"+intTipoPopUpPlanCuenta);
			PlanCuenta planCuenta = (PlanCuenta)event.getComponent().getAttributes().get("item");
			if(intTipoPopUpPlanCuenta.equals(Constante.BANCOCUENTA)){
				bancoCuentaAgregar.setIntEmpresacuentaPk(planCuenta.getId().getIntEmpresaCuentaPk());
				bancoCuentaAgregar.setIntPeriodocuenta(planCuenta.getId().getIntPeriodoCuenta());
				bancoCuentaAgregar.setStrNumerocuenta(planCuenta.getId().getStrNumeroCuenta());
			
			}else if(intTipoPopUpPlanCuenta.equals(Constante.BANCOCUENTACHEQUE)){
				bancoCuentaChequeAgregar.setIntEmpresacuentaPk(planCuenta.getId().getIntEmpresaCuentaPk());
				bancoCuentaChequeAgregar.setIntPeriodocuenta(planCuenta.getId().getIntPeriodoCuenta());
				bancoCuentaChequeAgregar.setStrNumerocuenta(planCuenta.getId().getStrNumeroCuenta());
			
			}else if(intTipoPopUpPlanCuenta.equals(Constante.FONDOFIJOCUENTA)){
				fondoDetalleAgregar.setIntEmpresacuentaPk(planCuenta.getId().getIntEmpresaCuentaPk());
				fondoDetalleAgregar.setIntPeriodocuenta(planCuenta.getId().getIntPeriodoCuenta());
				fondoDetalleAgregar.setStrNumerocuenta(planCuenta.getId().getStrNumeroCuenta());
				fondoDetalleAgregar.setPlanCuenta(planCuenta);
			
			}else if(intTipoPopUpPlanCuenta.equals(Constante.FONDOFIJOSOBREGIRO)){
				fondoDetalleAgregar.setIntEmpresacuentaPk(planCuenta.getId().getIntEmpresaCuentaPk());
				fondoDetalleAgregar.setIntPeriodocuenta(planCuenta.getId().getIntPeriodoCuenta());
				fondoDetalleAgregar.setStrNumerocuenta(planCuenta.getId().getStrNumeroCuenta());
				fondoDetalleAgregar.setPlanCuenta(planCuenta);
			}
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void deseleccionarPlanCuenta(){
		try{
			log.info("intTipoPopUpPlanCuenta:"+intTipoPopUpPlanCuenta);
			if(intTipoPopUpPlanCuenta.equals(Constante.BANCOCUENTA)){
				bancoCuentaAgregar.setIntEmpresacuentaPk(null);
				bancoCuentaAgregar.setIntPeriodocuenta(null);
				bancoCuentaAgregar.setStrNumerocuenta(null);
				
			}else if(intTipoPopUpPlanCuenta.equals(Constante.BANCOCUENTACHEQUE)){
				bancoCuentaChequeAgregar.setIntEmpresacuentaPk(null);
				bancoCuentaChequeAgregar.setIntPeriodocuenta(null);
				bancoCuentaChequeAgregar.setStrNumerocuenta(null);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarSeleccionBancoCuenta(){
		try{
			if(bancoCuentaAgregar.getStrNumerocuenta()==null
				|| bancoCuentaAgregar.getStrNumerocuenta().isEmpty()){
				mensajePopUp = "Debe seleccionar una cuenta contable.";
				mostrarMensajePopUp = Boolean.TRUE;
			}
			mostrarMensajePopUp = Boolean.FALSE;
			
			
			if(intAccionRegistro == AGREGAR_REGISTRO){
				intItemInterfazCorrelativo = intItemInterfazCorrelativo + 1;
				
				bancoCuentaAgregar.getCuentaBancaria().setId(new CuentaBancariaPK());
				//bancoCuentaAgregar.getCuentaBancaria().getId().setIntIdPersona(EMPRESA_USUARIO);
				bancoCuentaAgregar.setIntItemInterfaz(intItemInterfazCorrelativo);
				bancoCuentaAgregar.getCuentaBancaria().setIntDepositaCts(0);
				bancoCuentaAgregar.getCuentaBancaria().setIntMarcaAbono(0);
				bancoCuentaAgregar.getCuentaBancaria().setIntMarcaCargo(0);
				bancoCuentaAgregar.getCuentaBancaria().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				
				listaBancoCuenta.add(bancoCuentaAgregar);			
			}else if(intAccionRegistro == EDITAR_REGISTRO){
				
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void editarPopUpCuentaBancaria(ActionEvent event){
		try{
			intAccionRegistro = EDITAR_REGISTRO;
			bancoCuentaAgregar = (Bancocuenta)event.getComponent().getAttributes().get("item");
			habilitarPeriodoPlanCuenta = Boolean.TRUE;
			
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);					
			planCuentaFiltro.getId().setIntPeriodoCuenta(-1);
			
			mostrarMensajePopUp = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarCuentaBancaria(ActionEvent event){
		try{			
			Bancocuenta bancoCuentaSelec = (Bancocuenta)event.getComponent().getAttributes().get("item");
			listaBancoCuenta.remove(bancoCuentaSelec);
			log.info("busca:"+bancoCuentaSelec.getIntItemInterfaz());
			Bancocuentacheque bancoCuentaCheque;
			List<Bancocuentacheque> listaBancoCuentaChequeTemp = new ArrayList<Bancocuentacheque>();
			for(Object o : listaBancoCuentaCheque){
				bancoCuentaCheque = (Bancocuentacheque)o;
				log.info(bancoCuentaCheque);
				log.info("hay:"+bancoCuentaCheque.getBancoCuenta().getIntItemInterfaz());
				if(!bancoCuentaCheque.getBancoCuenta().getIntItemInterfaz().equals(bancoCuentaSelec.getIntItemInterfaz())){
					listaBancoCuentaChequeTemp.add(bancoCuentaCheque);
				}
			}
			listaBancoCuentaCheque = listaBancoCuentaChequeTemp;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	//Métodos para el popup de cuentabancariacheques
	
	public void abrirPopUpCuentaCheques(){
		try{
			intItemInterfazSeleccionado = new Integer(0);
			habilitarBancoCuentaCheque = Boolean.FALSE;
			intAccionRegistro = AGREGAR_REGISTRO;
			bancoCuentaChequeAgregar = new Bancocuentacheque();
			bancoCuentaChequeAgregar.getArchivo().setTipoarchivo(tipoArchivo2);
			habilitarPeriodoPlanCuenta = Boolean.FALSE;
			
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			planCuentaFiltro.setIntMovimiento(Constante.MOVIMIENTO_OPERACIONAL);
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);					
			planCuentaFiltro.getId().setIntPeriodoCuenta(-1);	
			
			mostrarMensajePopUp = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void editarPopUpCuentaBancariaCheques(ActionEvent event){
		try{
			bancoCuentaChequeAgregar = (Bancocuentacheque)event.getComponent().getAttributes().get("item");
			log.info(bancoCuentaChequeAgregar);
			habilitarBancoCuentaCheque = Boolean.TRUE;
			intAccionRegistro = EDITAR_REGISTRO;
			habilitarPeriodoPlanCuenta = Boolean.FALSE;			
			
			intItemInterfazSeleccionado = bancoCuentaChequeAgregar.getBancoCuenta().getIntItemInterfaz();
			
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);					
			planCuentaFiltro.getId().setIntPeriodoCuenta(bancoCuentaChequeAgregar.getBancoCuenta().getIntPeriodocuenta());
			
			mostrarMensajePopUp = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarCuentaBancaria(){
		try{
			if(intItemInterfazSeleccionado.equals(new Integer(0))){
				return;
			}
			Bancocuenta bancoCuenta = buscarBancoCuenta(intItemInterfazSeleccionado);			
			habilitarBancoCuentaCheque = Boolean.TRUE;			

			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);					
			planCuentaFiltro.getId().setIntPeriodoCuenta(bancoCuenta.getIntPeriodocuenta());			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private Bancocuenta buscarBancoCuenta(Integer intItemInterfaz){
		for(Object o : listaBancoCuenta){
			if(((Bancocuenta)o).getIntItemInterfaz().equals(intItemInterfaz)){
				return (Bancocuenta)o;
			}
		}
		return null;
	}
	
	public void aceptarSeleccionBancoCuentaCheque(){
		try{
			if(bancoCuentaChequeAgregar.getStrNumerocuenta()==null
				|| bancoCuentaChequeAgregar.getStrNumerocuenta().isEmpty()){
				mensajePopUp = "Debe seleccionar una cuenta contable.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}
			mostrarMensajePopUp = Boolean.FALSE;
			
			
			if(intAccionRegistro == AGREGAR_REGISTRO){
				Bancocuenta bancoCuenta = buscarBancoCuenta(intItemInterfazSeleccionado);
				bancoCuentaChequeAgregar.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				bancoCuentaChequeAgregar.setBancoCuenta(bancoCuenta);

				bancoCuentaChequeAgregar.getArchivo().getId().setIntParaTipoCod(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BANCOCUENTACHEQUE);
				bancoCuentaChequeAgregar.getArchivo().setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				
				listaBancoCuentaCheque.add(bancoCuentaChequeAgregar);				
			}else if(intAccionRegistro == EDITAR_REGISTRO){
				
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarCuentaBancariaCheque(ActionEvent event){
		try{			
			Bancocuentacheque bancoCuentaChequeSelec = (Bancocuentacheque)event.getComponent().getAttributes().get("item");
			listaBancoCuentaCheque.remove(bancoCuentaChequeSelec);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	
	//PopUps de fondo fijo
	public void abrirPopUpCuentaContable(){
		try{
			listaSubSucursal = new ArrayList<Object>();
			intIdSucursal = new Integer(0);
			fondoDetalleAgregar = new Fondodetalle();
			fondoDetalleAgregar.setIntIdsucursal(0);
			intAccionRegistro = AGREGAR_REGISTRO;
			habilitarPeriodoPlanCuenta = Boolean.TRUE;
			
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			planCuentaFiltro.setIntMovimiento(Constante.MOVIMIENTO_OPERACIONAL);
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);					
			planCuentaFiltro.getId().setIntPeriodoCuenta(-1);
			
			mostrarMensajePopUp = Boolean.FALSE;
			log.info("planCuentaFiltro:"+planCuentaFiltro);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean validarSucursalDocumento(Fondodetalle fondoDetalleAgregar){
		try{			
			for(Bancofondo bancoFondoBD : listaBancoFondoBD){
				if(bancoFondoBD.getIntTipoFondoFijo().equals(fondoNuevo.getIntTipoFondoFijo())){
					for(Fondodetalle fondoDetalleBD : bancoFondoBD.getListaFondodetalle()){
						if(fondoDetalleBD.getIntCodigodetalle().equals(Constante.CODIGODETALLE_DOCUMENTO)
							&& fondoDetalleBD.getIntEmpresasucursalPk().equals(fondoDetalleAgregar.getIntEmpresasucursalPk())
							&& fondoDetalleBD.getIntIdsucursal().equals(fondoDetalleAgregar.getIntIdsucursal())
							&& fondoDetalleBD.getIntIdsubsucursal().equals(fondoDetalleAgregar.getIntIdsubsucursal())
							&& fondoDetalleBD.getIntDocumentogeneralCod().equals(fondoDetalleAgregar.getIntDocumentogeneralCod())){
							return false;
						}
					}
				}
			}

			for(Object o : listaFondoDetalleDocumento){
				Fondodetalle fondoDetalleBD = (Fondodetalle)o;
				if(fondoDetalleBD.getIntEmpresasucursalPk().equals(fondoDetalleAgregar.getIntEmpresasucursalPk())
					&& fondoDetalleBD.getIntIdsucursal().equals(fondoDetalleAgregar.getIntIdsucursal())
					&& fondoDetalleBD.getIntIdsubsucursal().equals(fondoDetalleAgregar.getIntIdsubsucursal())
					&& fondoDetalleBD.getIntDocumentogeneralCod().equals(fondoDetalleAgregar.getIntDocumentogeneralCod())){
					return false;
				}
			}
			return true;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return false;
		}
	}
	
	private Fondodetalle aplicarEtiquetaTotalSucursal(Fondodetalle fondoDetalle){
		log.info("fondoDetalle:"+fondoDetalle.getIntTotalsucursalCod());
		for(Object o : listaSucursalFondoFijo){
			Tabla tabla = (Tabla)o;
			log.info("tabla id:"+tabla.getIntIdDetalle()+" desc:"+tabla.getStrDescripcion());
			if(tabla.getIntIdDetalle().equals(fondoDetalle.getIntTotalsucursalCod())){
				fondoDetalle.setStrEtiquetaSucursal(tabla.getStrDescripcion());
				break;
			}
		}
		fondoDetalle.setStrEtiquetaSubsucursal(null);
		fondoDetalle.setIntIdsucursal(null);
		fondoDetalle.setIntIdsubsucursal(null);
		return fondoDetalle;
	}
	
	private Fondodetalle aplicarEtiquetaSucursalYSubsucursal(Fondodetalle fondoDetalle){
		for(Object o : listaSucursal){
			Sucursal sucursal = (Sucursal)o;
			if(sucursal.getId().getIntIdSucursal().equals(fondoDetalle.getIntIdsucursal())){
				fondoDetalle.setSucursal(sucursal);
				fondoDetalle.setStrEtiquetaSucursal(sucursal.getJuridica().getStrSiglas());
				break;
			}
		}
		for(Object o : listaSubSucursal){
			Subsucursal subsucursal = (Subsucursal)o;
			if(subsucursal.getId().getIntIdSubSucursal().equals(fondoDetalle.getIntIdsubsucursal())){
				fondoDetalle.setSubSucursal(subsucursal);
				fondoDetalle.setStrEtiquetaSubsucursal(subsucursal.getStrDescripcion());
				break;
			}
		}
		return fondoDetalle;
	}
	
	public void aceptarSeleccionFondoCuenta(){
		try{
			if(fondoDetalleAgregar.getIntIdsucursal().equals(new Integer(0))){
				mensajePopUp = "Debe seleccionar una sucursal.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}
			if(fondoDetalleAgregar.getIntIdsubsucursal().equals(new Integer(0)) 
				&& fondoDetalleAgregar.getIntIdsucursal().compareTo(new Integer(0))>0){
				mensajePopUp = "Debe seleccionar una subsucursal.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}
			if(fondoDetalleAgregar.getStrNumerocuenta()==null
				|| fondoDetalleAgregar.getStrNumerocuenta().isEmpty()){
				mensajePopUp = "Debe seleccionar una cuenta contable.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}
			mostrarMensajePopUp = Boolean.FALSE;
			
			if(fondoDetalleAgregar.getIntIdsucursal().compareTo(new Integer(0))<0){
				fondoDetalleAgregar.setIntTotalsucursalCod(fondoDetalleAgregar.getIntIdsucursal());
				aplicarEtiquetaTotalSucursal(fondoDetalleAgregar);
			}else{
				fondoDetalleAgregar.setIntTotalsucursalCod(null);
				aplicarEtiquetaSucursalYSubsucursal(fondoDetalleAgregar);
			}
			
			if(intAccionRegistro == AGREGAR_REGISTRO){
				fondoDetalleAgregar.setIntCodigodetalle(Constante.CODIGODETALLE_CUENTACONTABLE);				
				listaFondoDetalleCuenta.add(fondoDetalleAgregar);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void editarPopUpCuentaContable(ActionEvent event){
		try{
			fondoDetalleAgregar = (Fondodetalle)event.getComponent().getAttributes().get("item");
			seleccionarSucursal();
			intAccionRegistro = EDITAR_REGISTRO;
			habilitarPeriodoPlanCuenta = Boolean.TRUE;
			//log.info(fondoDetalleAgregar);
			
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);					
			planCuentaFiltro.getId().setIntPeriodoCuenta(-1);
			
			if(fondoDetalleAgregar.getIntTotalsucursalCod()!=null){
				fondoDetalleAgregar.setIntIdsucursal(fondoDetalleAgregar.getIntTotalsucursalCod());
			}
			
			mostrarMensajePopUp = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarCuentaContable(ActionEvent event){
		try{
			Fondodetalle fondoDetalleSelec = (Fondodetalle)event.getComponent().getAttributes().get("item");
			listaFondoDetalleCuenta.remove(fondoDetalleSelec);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpDocumentoCancelar(){
		try{			
			listaSubSucursal = new ArrayList<Object>();
			intIdSucursal = new Integer(0);
			fondoDetalleAgregar = new Fondodetalle();
			fondoDetalleAgregar.setIntIdsucursal(0);
			fondoDetalleAgregar.setIntTipocomprobanteCod(0);
			intAccionRegistro = AGREGAR_REGISTRO;
			habilitarPeriodoPlanCuenta = Boolean.TRUE;
			
			mostrarMensajePopUp = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarSeleccionFondoDocumento(){
		try{
			if(fondoDetalleAgregar.getIntIdsucursal().equals(new Integer(0))){
				mensajePopUp = "Debe seleccionar una sucursal.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}
			if(fondoDetalleAgregar.getIntIdsubsucursal().equals(new Integer(0)) 
				&& fondoDetalleAgregar.getIntIdsucursal().compareTo(new Integer(0))>0){
				mensajePopUp = "Debe seleccionar una subsucursal.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}
			/*if(!validarSucursalDocumento(fondoDetalleAgregar)){
				mensajePopUp = "La sucursal y subsucursal seleccionadas ya han sido registradas o seleccionadas anteriormente con ese tipo de documento.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}*/
			if(fondoDetalleAgregar.getIntDocumentogeneralCod().equals(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS) 
				&& fondoDetalleAgregar.getIntTipocomprobanteCod().equals(new Integer(0))){
				mensajePopUp = "Debe seleccionar una tipo de comprobante.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}
			mostrarMensajePopUp = Boolean.FALSE;
			
			if(!fondoDetalleAgregar.getIntDocumentogeneralCod().equals(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS)){
				fondoDetalleAgregar.setIntTipocomprobanteCod(null);
			}
			
			if(fondoDetalleAgregar.getIntIdsucursal().compareTo(new Integer(0))<0){
				fondoDetalleAgregar.setIntTotalsucursalCod(fondoDetalleAgregar.getIntIdsucursal());
				aplicarEtiquetaTotalSucursal(fondoDetalleAgregar);
			}else{
				fondoDetalleAgregar.setIntTotalsucursalCod(null);
				aplicarEtiquetaSucursalYSubsucursal(fondoDetalleAgregar);
			}
			
			if(intAccionRegistro == AGREGAR_REGISTRO){
				fondoDetalleAgregar.setIntCodigodetalle(Constante.CODIGODETALLE_DOCUMENTO);			
				listaFondoDetalleDocumento.add(fondoDetalleAgregar);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void editarPopUpDocumentoCancelar(ActionEvent event){
		try{
			fondoDetalleAgregar = (Fondodetalle)event.getComponent().getAttributes().get("item");
			intAccionRegistro = EDITAR_REGISTRO;
			habilitarPeriodoPlanCuenta = Boolean.TRUE;
			
			if(fondoDetalleAgregar.getIntTotalsucursalCod()!=null){
				fondoDetalleAgregar.setIntIdsucursal(fondoDetalleAgregar.getIntTotalsucursalCod());
			}
			mostrarMensajePopUp = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarDocumentoCancelar(ActionEvent event){
		try{
			Fondodetalle fondoDetalleSelec = (Fondodetalle)event.getComponent().getAttributes().get("item");
			listaFondoDetalleDocumento.remove(fondoDetalleSelec);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpSobregiro(){
		try{
			listaSubSucursal = new ArrayList<Object>();
			intIdSucursal = new Integer(0);
			fondoDetalleAgregar = new Fondodetalle();
			fondoDetalleAgregar.setIntIdsucursal(0);
			intAccionRegistro = AGREGAR_REGISTRO;
			habilitarPeriodoPlanCuenta = Boolean.TRUE;
			
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			planCuentaFiltro.setIntMovimiento(Constante.MOVIMIENTO_OPERACIONAL);
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);					
			planCuentaFiltro.getId().setIntPeriodoCuenta(-1);
			
			mostrarMensajePopUp = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarSeleccionFondoSobregiro(){
		try{
			if(fondoDetalleAgregar.getIntIdsucursal().equals(new Integer(0))){
				mensajePopUp = "Debe seleccionar una sucursal.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}
			if(fondoDetalleAgregar.getIntIdsubsucursal().equals(new Integer(0)) 
				&& fondoDetalleAgregar.getIntIdsucursal().compareTo(new Integer(0))>0){
				mensajePopUp = "Debe seleccionar una subsucursal.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}
			if(fondoDetalleAgregar.getStrNumerocuenta()==null
				|| fondoDetalleAgregar.getStrNumerocuenta().isEmpty()){
				mensajePopUp = "Debe seleccionar una cuenta contable.";
				mostrarMensajePopUp = Boolean.TRUE;
				return;
			}
			mostrarMensajePopUp = Boolean.FALSE;
			
			
			if(fondoDetalleAgregar.getIntIdsucursal().compareTo(new Integer(0))<0){
				fondoDetalleAgregar.setIntTotalsucursalCod(fondoDetalleAgregar.getIntIdsucursal());
				aplicarEtiquetaTotalSucursal(fondoDetalleAgregar);
			}else{
				fondoDetalleAgregar.setIntTotalsucursalCod(null);
				aplicarEtiquetaSucursalYSubsucursal(fondoDetalleAgregar);
			}
			
			if(intAccionRegistro == AGREGAR_REGISTRO){
				fondoDetalleAgregar.setIntCodigodetalle(Constante.CODIGODETALLE_SOBREGIRO);				
				listaFondoDetalleSobregiro.add(fondoDetalleAgregar);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void editarPopUpSobregiro(ActionEvent event){
		try{
			fondoDetalleAgregar = (Fondodetalle)event.getComponent().getAttributes().get("item");
			intAccionRegistro = EDITAR_REGISTRO;
			habilitarPeriodoPlanCuenta = Boolean.TRUE;
			
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);					
			planCuentaFiltro.getId().setIntPeriodoCuenta(-1);
			
			if(fondoDetalleAgregar.getIntTotalsucursalCod()!=null){
				fondoDetalleAgregar.setIntIdsucursal(fondoDetalleAgregar.getIntTotalsucursalCod());
			}
			mostrarMensajePopUp = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarSobregiro(ActionEvent event){
		try{
			Fondodetalle fondoDetalleSelec = (Fondodetalle)event.getComponent().getAttributes().get("item");
			listaFondoDetalleSobregiro.remove(fondoDetalleSelec);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarSucursal(){
		try{
			if(fondoDetalleAgregar.getIntIdsucursal().equals(new Integer(0))){
				return;
			}
			fondoDetalleAgregar.setIntEmpresasucursalPk(EMPRESA_USUARIO);
			if(fondoDetalleAgregar.getIntIdsucursal().compareTo(new Integer(0))>0){
				listaSubSucursal = empresaFacade.getListaSubSucursalPorIdSucursal(fondoDetalleAgregar.getIntIdsucursal());
			}else{
				listaSubSucursal = new ArrayList<Subsucursal>();
			}
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	//Subir archivo
	
	public void manejarSubirArchivo(UploadEvent event){
		log.info("--manejarSubirArchivo");
		try {
			
			log.info("tipoArchivo.getIntParaTipoCod():"+tipoArchivo2.getIntParaTipoCod());
			
			String target = tipoArchivo2.getStrRuta() + "\\" + subirArchivo(event, Constante.PARAM_T_TIPOARCHIVOADJUNTO_BANCOCUENTACHEQUE);
			
			log.info("target:"+target);
			
			bancoCuentaChequeAgregar.getArchivo().setStrNombrearchivo(target);
			
		}catch(PatternSyntaxException e){
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public static String subirArchivo(UploadEvent event, Integer intTipoArchivo) throws BusinessException{
		TipoArchivo tipoArchivo = null;
		UploadItem uploadItem = null;
		uploadItem = event.getUploadItem();
		java.io.File file = uploadItem.getFile();
		
		try {
			log.info("intTipoArchivo: "+intTipoArchivo);
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			tipoArchivo = generalFacade.getTipoArchivoPorPk(intTipoArchivo);
			log.info("tipoArchivo: "+tipoArchivo);
			log.info("tipoArchivo.getStrDescripcion(): "+tipoArchivo.getStrDescripcion());
		} catch (BusinessException e) {
			System.out.println("error: "+ e);
			throw e;
		} catch (EJBFactoryException e) {
			System.out.println("error: "+ e);
			throw new BusinessException(e);
		}
		//**Parche local para IE
		String nombreArchivo = event.getUploadItem().getFileName();
		log.info("nombreArchivo:"+nombreArchivo);
		String nombreArchivoPartes[] = nombreArchivo.split("\\\\");			
		nombreArchivo = nombreArchivoPartes[nombreArchivoPartes.length-1];
		log.info("nombreArchivo:"+nombreArchivo);
		//**
		//String fileName = tipoArchivo.getStrPrefijo()+"-"+uploadItem.getFileName();
		String fileName = tipoArchivo.getStrPrefijo()+"-"+nombreArchivo;
		String target = tipoArchivo.getStrRuta() + "\\" +fileName;
		log.info("target:"+target);
		try {
			InputStream in = new FileInputStream(file);
			OutputStream out = new FileOutputStream(target);
			try {
			// Transfer bytes from in to out
			byte[] buf = new byte[1024*1024*50];//Máximo 50MB
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			 
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
			finally {
				in.close();
				out.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return fileName;
	}
	
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	
	
	
	public Fondodetalle getFondoDetalleAgregar() {
		return fondoDetalleAgregar;
	}
	public void setFondoDetalleAgregar(Fondodetalle fondoDetalleAgregar) {
		this.fondoDetalleAgregar = fondoDetalleAgregar;
	}
	public List getListaAnios() {
		return listaAnios;
	}
	public void setListaAnios(List listaAnios) {
		this.listaAnios = listaAnios;
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
	public List getListaBancoFondo() {
		return listaBancoFondo;
	}
	public void setListaBancoFondo(List listaBancoFondo) {
		this.listaBancoFondo = listaBancoFondo;
	}
	public Bancofondo getBancoNuevo() {
		return bancoNuevo;
	}
	public void setBancoNuevo(Bancofondo bancoNuevo) {
		this.bancoNuevo = bancoNuevo;
	}
	public Bancofondo getFondoNuevo() {
		return fondoNuevo;
	}
	public void setFondoNuevo(Bancofondo fondoNuevo) {
		this.fondoNuevo = fondoNuevo;
	}

	public Integer getIntTipoBanco() {
		return intTipoBanco;
	}
	public void setIntTipoBanco(Integer intTipoBanco) {
		this.intTipoBanco = intTipoBanco;
	}
	public boolean isHabilitarBanco() {
		return habilitarBanco;
	}
	public void setHabilitarBanco(boolean habilitarBanco) {
		this.habilitarBanco = habilitarBanco;
	}
	public boolean isHabilitarFondoFijo() {
		return habilitarFondoFijo;
	}
	public void setHabilitarFondoFijo(boolean habilitarFondoFijo) {
		this.habilitarFondoFijo = habilitarFondoFijo;
	}
	public List getListaBancoCuenta() {
		return listaBancoCuenta;
	}
	public void setListaBancoCuenta(List listaBancoCuenta) {
		this.listaBancoCuenta = listaBancoCuenta;
	}
	public Bancocuenta getBancoCuentaAgregar() {
		return bancoCuentaAgregar;
	}
	public void setBancoCuentaAgregar(Bancocuenta bancoCuentaAgregar) {
		this.bancoCuentaAgregar = bancoCuentaAgregar;
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
	public List getListaBancoCuentaCheque() {
		return listaBancoCuentaCheque;
	}
	public void setListaBancoCuentaCheque(List listaBancoCuentaCheque) {
		this.listaBancoCuentaCheque = listaBancoCuentaCheque;
	}
	public boolean isHabilitarBancoCuentaCheque() {
		return habilitarBancoCuentaCheque;
	}
	public void setHabilitarBancoCuentaCheque(boolean habilitarBancoCuentaCheque) {
		this.habilitarBancoCuentaCheque = habilitarBancoCuentaCheque;
	}
	public Bancocuentacheque getBancoCuentaChequeAgregar() {
		return bancoCuentaChequeAgregar;
	}
	public void setBancoCuentaChequeAgregar(Bancocuentacheque bancoCuentaChequeAgregar) {
		this.bancoCuentaChequeAgregar = bancoCuentaChequeAgregar;
	}
	public Integer getIntTipoPopUpPlanCuenta() {
		return intTipoPopUpPlanCuenta;
	}
	public void setIntTipoPopUpPlanCuenta(Integer intTipoPopUpPlanCuenta) {
		this.intTipoPopUpPlanCuenta = intTipoPopUpPlanCuenta;
	}
	public Integer getIntItemInterfazSeleccionado() {
		return intItemInterfazSeleccionado;
	}
	public void setIntItemInterfazSeleccionado(Integer intItemInterfazSeleccionado) {
		this.intItemInterfazSeleccionado = intItemInterfazSeleccionado;
	}
	public List getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
	public List getListaSubSucursal() {
		return listaSubSucursal;
	}
	public void setListaSubSucursal(List listaSubSucursal) {
		this.listaSubSucursal = listaSubSucursal;
	}
	public List getListaFondoDetalleCuenta() {
		return listaFondoDetalleCuenta;
	}
	public void setListaFondoDetalleCuenta(List listaFondoDetalleCuenta) {
		this.listaFondoDetalleCuenta = listaFondoDetalleCuenta;
	}
	public List getListaFondoDetalleDocumento() {
		return listaFondoDetalleDocumento;
	}
	public void setListaFondoDetalleDocumento(List listaFondoDetalleDocumento) {
		this.listaFondoDetalleDocumento = listaFondoDetalleDocumento;
	}
	public List getListaFondoDetalleSobregiro() {
		return listaFondoDetalleSobregiro;
	}
	public void setListaFondoDetalleSobregiro(List listaFondoDetalleSobregiro) {
		this.listaFondoDetalleSobregiro = listaFondoDetalleSobregiro;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public boolean isIniciaRegistrarNuevo() {
		return iniciaRegistrarNuevo;
	}
	public void setIniciaRegistrarNuevo(boolean iniciaRegistrarNuevo) {
		this.iniciaRegistrarNuevo = iniciaRegistrarNuevo;
	}
	public boolean isHabilitarBotonMostrarBanco() {
		return habilitarBotonMostrarBanco;
	}
	public void setHabilitarBotonMostrarBanco(boolean habilitarBotonMostrarBanco) {
		this.habilitarBotonMostrarBanco = habilitarBotonMostrarBanco;
	}
	public boolean isHabilitarBotonMostrarFondo() {
		return habilitarBotonMostrarFondo;
	}
	public void setHabilitarBotonMostrarFondo(boolean habilitarBotonMostrarFondo) {
		this.habilitarBotonMostrarFondo = habilitarBotonMostrarFondo;
	}
	public Bancofondo getBancoFondoFiltro() {
		return bancoFondoFiltro;
	}
	public void setBancoFondoFiltro(Bancofondo bancoFondoFiltro) {
		this.bancoFondoFiltro = bancoFondoFiltro;
	}
	public boolean isHabilitarPeriodoPlanCuenta() {
		return habilitarPeriodoPlanCuenta;
	}
	public void setHabilitarPeriodoPlanCuenta(boolean habilitarPeriodoPlanCuenta) {
		this.habilitarPeriodoPlanCuenta = habilitarPeriodoPlanCuenta;
	}
	public String getMensajePopUp() {
		return mensajePopUp;
	}
	public void setMensajePopUp(String mensajePopUp) {
		this.mensajePopUp = mensajePopUp;
	}
	public boolean isMostrarMensajePopUp() {
		return mostrarMensajePopUp;
	}
	public void setMostrarMensajePopUp(boolean mostrarMensajePopUp) {
		this.mostrarMensajePopUp = mostrarMensajePopUp;
	}

	public List getListaSucursalFondoFijo() {
		return listaSucursalFondoFijo;
	}

	public void setListaSucursalFondoFijo(List listaSucursalFondoFijo) {
		this.listaSucursalFondoFijo = listaSucursalFondoFijo;
	}
}