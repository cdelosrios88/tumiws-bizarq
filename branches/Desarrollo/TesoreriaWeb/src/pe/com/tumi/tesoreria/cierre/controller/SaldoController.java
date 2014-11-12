/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-005       			19/10/2014     Christian De los Ríos        Se modificó la lógica interna relacionada al formulario de saldos diarios         
*/
package pe.com.tumi.tesoreria.cierre.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.Password;
import pe.com.tumi.seguridad.permiso.domain.PasswordId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.domain.Saldo;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalleInterfaz;


public class SaldoController {

	protected static Logger log = Logger.getLogger(SaldoController.class);
	
	private EmpresaFacadeRemote empresaFacade;
	private TablaFacadeRemote 	tablaFacade;
	private EgresoFacadeLocal 	egresoFacade;
	private BancoFacadeLocal 	bancoFacade;
	
	private List<Saldo>	listaSaldo;
	private List<IngresoDetalleInterfaz>listaIngresoDetalleInterfaz;
	private List<Tabla> listaTablaSucursal;	
	private List<Bancofondo>	listaBanco;
	private List<Bancofondo>	listaFondo;
	
	private Saldo		saldoFiltro;

	private Integer		intIdBancoFiltro;
	private Integer 	intIdFondoFiltro;
	private Date		dtUltimaFechaGenerada;
	private Date		dtUltimaFechaCierreGeneral;
	private Date		dtFechaInicioSaldo;
	private Date		dtFechaFinSaldo;
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private Integer		intTipoCierre;

	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;

	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferiorNuevo;
	private boolean mostrarPanelInferiorAnular;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean poseePermiso;
	
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	private String strPassword;
	private String strPasswordAnula;
	private String strAnulReason;
	private PermisoFacadeRemote permisoFacade;
	//Fin: REQ14-005 - bizarq - 19/10/2014
	
	public SaldoController(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.TRANSACCION_CIERRE_SALDOS);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();	
	}
	
	public void cargarValoresIniciales(){
		try{
			cargarUsuario();
			listaSaldo = new ArrayList<Saldo>();
			intIdBancoFiltro = new Integer(0);
			intIdFondoFiltro = new Integer(0);
			saldoFiltro = new Saldo();
			saldoFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			//Inicio: REQ14-005 - bizarq - 19/10/2014
			permisoFacade = (PermisoFacadeRemote) EJBFactory.getRemote(PermisoFacadeRemote.class);
			//Fin: REQ14-005 - bizarq - 19/10/2014
			
			listaBanco = bancoFacade.obtenerListaBancoExistente(EMPRESA_USUARIO);
			listaFondo = bancoFacade.obtenerListaFondoExistente(EMPRESA_USUARIO);
			cargarListaBancoyFondo();
			
			cargarListaTablaSucursal();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaBancoyFondo()throws Exception{
		List<Tabla> listaTablaTipoFondo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOFONDOFIJO));
		for(Bancofondo fondo : listaFondo){
			for(Tabla tabla : listaTablaTipoFondo){
				if(fondo.getIntTipoFondoFijo().equals(tabla.getIntIdDetalle())){
					fondo.setStrEtiqueta(tabla.getStrDescripcion());
					break;
				}
			}
		}
		List<Tabla> listaTablaTipoBanco = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_BANCOS));
		for(Bancofondo banco : listaBanco){
			for(Tabla tabla : listaTablaTipoBanco){
				if(banco.getIntBancoCod().equals(tabla.getIntIdDetalle())){
					banco.setStrEtiqueta(tabla.getStrDescripcion());
				}
			}
		}
		
	}
	
	private void cargarListaTablaSucursal() throws Exception{
		List<Sucursal>listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
		//Ordena la sucursal alafabeticamente
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});
		
		listaTablaSucursal = new ArrayList<Tabla>();
		listaTablaSucursal = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
		Sucursal sucursal;
		Tabla tabla;
		for(Object o : listaSucursal){
			 sucursal = (Sucursal)o;
			 tabla = new Tabla();
			 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
			 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
			 listaTablaSucursal.add(tabla);
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
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferiorNuevo = Boolean.FALSE;
		mostrarPanelInferiorAnular = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
		ocultarMensaje();
	}	
	
	public void buscar(){
		try{
			if(saldoFiltro.getDtFechaDesde()!=null && saldoFiltro.getDtFechaHasta()!=null 
			&& saldoFiltro.getDtFechaDesde().compareTo(saldoFiltro.getDtFechaHasta())>0){
				mostrarMensaje(Boolean.FALSE, "La fecha de inicio no puede ser mayor a la fecha de fin dentro de la busqueda."); return;
			}
			if(saldoFiltro.getId().getIntSucuIdSucursal().equals(new Integer(0))){
				saldoFiltro.getId().setIntSucuIdSucursal(null);
			}
			if(intIdFondoFiltro.equals(new Integer(0))){
				saldoFiltro.setIntItemBancoFondo(intIdBancoFiltro);
			}
			if(intIdBancoFiltro.equals(new Integer(0))){
				saldoFiltro.setIntItemBancoFondo(intIdFondoFiltro);
			}
			if(saldoFiltro.getIntItemBancoFondo().equals(new Integer(0))){
				saldoFiltro.setIntItemBancoFondo(null);
			}
			
			listaSaldo = egresoFacade.buscarSaldo(saldoFiltro);
			
			for(Saldo saldo : listaSaldo){
				for(Bancofondo fondo : listaFondo){
					if(saldo.getIntItemBancoFondo().equals(fondo.getId().getIntItembancofondo())){
						saldo.setStrEtiqueta(fondo.getStrEtiqueta());
						break;
					}
				}
				for(Bancofondo banco : listaBanco){
					if(saldo.getIntItemBancoFondo().equals(banco.getId().getIntItembancofondo())){
						saldo.setStrEtiqueta(banco.getStrEtiqueta());
						break;
					}
				}
				for(Tabla tablaSucursal : listaTablaSucursal){
					if(saldo.getId().getIntSucuIdSucursal().equals(tablaSucursal.getIntIdDetalle())){
						Subsucursal subsucursal = empresaFacade.getSubSucursalPorIdSubSucursal(saldo.getId().getIntSudeIdSucursal());
						saldo.setStrEtiquetaSucursal(tablaSucursal.getStrDescripcion()+" - "+subsucursal.getStrDescripcion());
					}
				}
			}
			
			//Ordena la sucursal alafabeticamente
			Collections.sort(listaSaldo, new Comparator<Saldo>(){
				public int compare(Saldo uno, Saldo otro) {
					return uno.getId().getDtFechaSaldo().compareTo(otro.getId().getDtFechaSaldo());
				}
			});
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void grabar(){
		log.info("--grabar");
		try {
			
			if(mostrarPanelInferiorNuevo){
				if(dtFechaInicioSaldo==null){
					mostrarMensaje(Boolean.FALSE, "Debe ingresar una fecha de inicio a calcular el saldo."); return;			
				}
				if(dtFechaFinSaldo==null){
					mostrarMensaje(Boolean.FALSE, "Debe ingresar una fecha de fin a calcular el saldo."); return;			
				}			
				if(dtFechaInicioSaldo.compareTo(dtFechaFinSaldo)>0){
					mostrarMensaje(Boolean.FALSE, "La fecha de inicio no puede ser mayor a la fecha de fin."); return;
				}
				if(dtUltimaFechaGenerada!=null && dtFechaInicioSaldo.compareTo(dtUltimaFechaGenerada)<=0){
					mostrarMensaje(Boolean.FALSE, "La fecha de inicio debe ser mayor a la ultima fecha generada."); return;
				}
				
				egresoFacade.procesarSaldo(dtFechaInicioSaldo, dtFechaFinSaldo, usuario, listaBanco, listaFondo);
				mostrarMensaje(Boolean.TRUE, "Se registro correctamene el saldo para el rango de fechas indicadas.");
			}
			
			if(mostrarPanelInferiorAnular){
				if(dtFechaInicioSaldo==null){
					mostrarMensaje(Boolean.FALSE, "Debe ingresar una fecha de inicio de anulación de saldos."); return;			
				}
				//Inicio: REQ14-005 - bizarq - 19/10/2014
				//egresoFacade.anularSaldo(EMPRESA_USUARIO, dtFechaInicioSaldo);
				egresoFacade.anularSaldo(usuario, dtFechaInicioSaldo);
				//Fin: REQ14-005 - bizarq - 19/10/2014
				mostrarMensaje(Boolean.TRUE, "Se anularon correctamene todos los saldos desde la fecha "+dtFechaInicioSaldo+".");
			}
			
			buscar();
			
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Ocurrio un error durante el proceso de cierre de fondos.");
			log.error(e.getMessage(),e);
		}
	}
	
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	/**
	 * processDailyAmount:  Método encargado de realizar el proceso de saldos diarios
	 * 
	 * @author bizarq
	 * @throws EJBFactoryException
	 * 
	 * */
	public void processDailyAmount() throws EJBFactoryException {
		Integer intResult = null;
		try {
			if(!isValidDailyAmountProcess()){
				//egresoFacade.procesarSaldo(dtFechaInicioSaldo, dtFechaFinSaldo, usuario, listaBanco, listaFondo);
				if(intResult!=null && intResult.equals(Constante.ON_SUCCESS)){
					egresoFacade.processDailyAmount(dtFechaInicioSaldo, dtFechaFinSaldo, usuario);
					mostrarMensaje(Boolean.TRUE, "Se registro correctamene el saldo para el rango de fechas indicadas.");
				}else{
					mostrarMensaje(Boolean.FALSE, "Ocurrió un error en el proceso de Saldos Diarios.");
				}
				buscar();
			}
			
		} catch (BusinessException e) {
			mostrarMensaje(Boolean.FALSE, "Ocurrio un error durante el proceso de cierre de fondos.");
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * anulateDailyAmount:  Método encargado de realizar la anulación de algún saldo diario en un determinado
	 * 						periodo de fechas
	 * 
	 * @author bizarq
	 * @throws EJBFactoryException
	 * */
	public void anulateDailyAmount() throws EJBFactoryException {
		Saldo saldo = null;
		try {
			if(!isValidAmountAnulation()){
				saldo = new Saldo();
				saldo.setDtFechaDesde(dtFechaInicioSaldo);
				saldo.setStrMotivoAnula(strAnulReason);
				egresoFacade.anularSaldo(usuario, saldo);
				mostrarMensaje(Boolean.TRUE, "Se anularon correctamene todos los saldos desde la fecha "+ Constante.sdf.format(dtFechaInicioSaldo)+".");
			}
		} catch (BusinessException e) {
			mostrarMensaje(Boolean.FALSE, "Ocurrio un error durante el proceso de anulación de saldos diarios.");
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * isValidDailyAmountProcess: Método encargado de realizar validaciones al formulario de 
	 * generación de saldos diarios. 
	 * 
	 * @author bizarq
	 * @return <boolean> retorna true o false según la validación de los campos de generación de saldos </boolean>
	 * */
	private boolean isValidDailyAmountProcess(){
		boolean isValid = Boolean.FALSE;
		try {
			if(dtFechaInicioSaldo==null){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar una fecha de inicio a calcular el saldo.");
				return Boolean.TRUE;
			}
			if(dtFechaFinSaldo==null){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar una fecha de fin a calcular el saldo.");
				return Boolean.TRUE;
			}			
			if(dtFechaInicioSaldo.compareTo(dtFechaFinSaldo)>0){
				mostrarMensaje(Boolean.FALSE, "La fecha de inicio no puede ser mayor a la fecha de fin.");
				return Boolean.TRUE;
			}
			if(dtUltimaFechaGenerada!=null && dtFechaInicioSaldo.compareTo(dtUltimaFechaGenerada)<=0){
				mostrarMensaje(Boolean.FALSE, "La fecha de inicio debe ser mayor a la ultima fecha generada.");
				return Boolean.TRUE;
			}
			
			isValid = isValidPassword(this.strPassword);
			
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Ocurrio un error durante el proceso de anulación de saldos diarios.");
			log.error(e.getMessage(),e );
		}
		return isValid;
	}
	
	/**
	 * Descripción: Método encargado de realizar validaciones al formulario de anulación de saldos diarios
	 * 
	 * @author bizarq
	 * @return <boolean> retorna true o false según la validación de los campos de anulación </boolean>
	 * */
	private boolean isValidAmountAnulation(){
		boolean isValid = Boolean.FALSE;
		
		if(dtFechaInicioSaldo==null){
			mostrarMensaje(Boolean.FALSE, "Debe ingresar una fecha de inicio de anulación de saldos.");
			return Boolean.TRUE;
		}
		if(strAnulReason==null || strAnulReason.equals(Constante.STR_EMPTY)){
			mostrarMensaje(Boolean.FALSE, "Debe ingresar el motivo de anulación de saldos.");
			return Boolean.TRUE;
		}
		
		isValid = isValidPassword(this.strPasswordAnula);
		
		return isValid;
	}
	
	/**
	 * @author bizarq
	 * @param <String> strPassword </String>
	 * @return <boolean> retorna true o false según la validación del password </boolean>
	 * */
	private boolean isValidPassword(String strPassword){
		Password password = null;
		try {
			if(strPassword==null || strPassword.equals(Constante.STR_EMPTY)){
				mostrarMensaje(Boolean.FALSE, "Ingrese el Password por favor.");
				return Boolean.TRUE;
			}
			password = new Password();
			password.setId(new PasswordId());
			password.getId().setIntEmpresaPk(usuario.getEmpresa().getIntIdEmpresa());
			password.getId().setIntIdTransaccion(Constante.INT_IDTRANSACCION_CIERRE_SALDOCAJA);
			password.setStrContrasena(strPassword);
			password = permisoFacade.getPasswordPorPkYPass(password);
			if(password==null){
				mostrarMensaje(Boolean.FALSE, "Clave incorrecta. Por favor intente nuevamente.");
				return Boolean.TRUE;
			}
			
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
		}
		return Boolean.FALSE;
	}
	
	//Fin: REQ14-005 - bizarq - 19/10/2014
	
	public void habilitarPanelInferiorNuevo(){
		try{
			cargarUsuario();
			dtFechaInicioSaldo = null;
			dtFechaFinSaldo = null;
			dtUltimaFechaCierreGeneral = null;
			dtUltimaFechaGenerada = null;
			
			obtenerFechasSaldo();
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferiorNuevo = Boolean.TRUE;
			mostrarPanelInferiorAnular = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			habilitarGrabar = Boolean.TRUE;
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void obtenerFechasSaldo()throws Exception{
		Saldo saldoUltimaFechaRegistro = egresoFacade.obtenerSaldoUltimaFechaRegistro(EMPRESA_USUARIO);		
		log.info("saldoUltimaFechaRegistro:"+saldoUltimaFechaRegistro);		
		//Inicio: REQ14-005 - bizarq - 19/10/2014
		//if(saldoUltimaFechaRegistro!=null)dtUltimaFechaCierreGeneral = saldoUltimaFechaRegistro.getTsFechaRegistro();
		if(saldoUltimaFechaRegistro!=null)dtUltimaFechaGenerada = saldoUltimaFechaRegistro.getTsFechaRegistro();
		
		//dtUltimaFechaGenerada = egresoFacade.obtenerUltimaFechaSaldo(EMPRESA_USUARIO);
		dtUltimaFechaCierreGeneral = egresoFacade.obtenerUltimaFechaSaldo(EMPRESA_USUARIO);
		//Fin: REQ14-005 - bizarq - 19/10/2014
	}
	
	public void habilitarPanelInferiorAnular(){
		try{
			cargarUsuario();
			dtFechaInicioSaldo = null;
			dtFechaFinSaldo = null;
			dtUltimaFechaCierreGeneral = null;
			dtUltimaFechaGenerada = null;
			
			obtenerFechasSaldo();
				
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferiorNuevo = Boolean.FALSE;
			mostrarPanelInferiorAnular = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			habilitarGrabar = Boolean.TRUE;
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
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
	public Integer getIntTipoCierre() {
		return intTipoCierre;
	}
	public void setIntTipoCierre(Integer intTipoCierre) {
		this.intTipoCierre = intTipoCierre;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<IngresoDetalleInterfaz> getListaIngresoDetalleInterfaz() {
		return listaIngresoDetalleInterfaz;
	}
	public void setListaIngresoDetalleInterfaz(List<IngresoDetalleInterfaz> listaIngresoDetalleInterfaz) {
		this.listaIngresoDetalleInterfaz = listaIngresoDetalleInterfaz;
	}
	public Date getDtUltimaFechaGenerada() {
		return dtUltimaFechaGenerada;
	}
	public void setDtUltimaFechaGenerada(Date dtUltimaFechaGenerada) {
		this.dtUltimaFechaGenerada = dtUltimaFechaGenerada;
	}
	public Date getDtUltimaFechaCierreGeneral() {
		return dtUltimaFechaCierreGeneral;
	}
	public void setDtUltimaFechaCierreGeneral(Date dtUltimaFechaCierreGeneral) {
		this.dtUltimaFechaCierreGeneral = dtUltimaFechaCierreGeneral;
	}
	public Date getDtFechaInicioSaldo() {
		return dtFechaInicioSaldo;
	}
	public void setDtFechaInicioSaldo(Date dtFechaInicioSaldo) {
		this.dtFechaInicioSaldo = dtFechaInicioSaldo;
	}
	public Date getDtFechaFinSaldo() {
		return dtFechaFinSaldo;
	}
	public void setDtFechaFinSaldo(Date dtFechaFinSaldo) {
		this.dtFechaFinSaldo = dtFechaFinSaldo;
	}
	public List<Saldo> getListaSaldo() {
		return listaSaldo;
	}
	public void setListaSaldo(List<Saldo> listaSaldo) {
		this.listaSaldo = listaSaldo;
	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	public Saldo getSaldoFiltro() {
		return saldoFiltro;
	}
	public void setSaldoFiltro(Saldo saldoFiltro) {
		this.saldoFiltro = saldoFiltro;
	}
	public Integer getIntIdBancoFiltro() {
		return intIdBancoFiltro;
	}
	public void setIntIdBancoFiltro(Integer intIdBancoFiltro) {
		this.intIdBancoFiltro = intIdBancoFiltro;
	}
	public Integer getIntIdFondoFiltro() {
		return intIdFondoFiltro;
	}
	public void setIntIdFondoFiltro(Integer intIdFondoFiltro) {
		this.intIdFondoFiltro = intIdFondoFiltro;
	}
	public List<Bancofondo> getListaBanco() {
		return listaBanco;
	}
	public void setListaBanco(List<Bancofondo> listaBanco) {
		this.listaBanco = listaBanco;
	}
	public List<Bancofondo> getListaFondo() {
		return listaFondo;
	}
	public void setListaFondo(List<Bancofondo> listaFondo) {
		this.listaFondo = listaFondo;
	}
	public List<Tabla> getListaTablaSucursal() {
		return listaTablaSucursal;
	}
	public void setListaTablaSucursal(List<Tabla> listaTablaSucursal) {
		this.listaTablaSucursal = listaTablaSucursal;
	}
	public boolean isMostrarPanelInferiorNuevo() {
		return mostrarPanelInferiorNuevo;
	}
	public void setMostrarPanelInferiorNuevo(boolean mostrarPanelInferiorNuevo) {
		this.mostrarPanelInferiorNuevo = mostrarPanelInferiorNuevo;
	}
	public boolean isMostrarPanelInferiorAnular() {
		return mostrarPanelInferiorAnular;
	}
	public void setMostrarPanelInferiorAnular(boolean mostrarPanelInferiorAnular) {
		this.mostrarPanelInferiorAnular = mostrarPanelInferiorAnular;
	}

	//Inicio: REQ14-005 - bizarq - 19/10/2014
	public String getStrPassword() {
		return strPassword;
	}
	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}
	public String getStrPasswordAnula() {
		return strPasswordAnula;
	}
	public void setStrPasswordAnula(String strPasswordAnula) {
		this.strPasswordAnula = strPasswordAnula;
	}
	public String getStrAnulReason() {
		return strAnulReason;
	}
	public void setStrAnulReason(String strAnulReason) {
		this.strAnulReason = strAnulReason;
	}
	//Fin: REQ14-005 - bizarq - 19/10/2014
}