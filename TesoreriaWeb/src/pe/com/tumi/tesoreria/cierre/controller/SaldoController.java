package pe.com.tumi.tesoreria.cierre.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
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
				egresoFacade.anularSaldo(EMPRESA_USUARIO, dtFechaInicioSaldo);
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
		if(saldoUltimaFechaRegistro!=null)dtUltimaFechaCierreGeneral = saldoUltimaFechaRegistro.getTsFechaRegistro();
		
		dtUltimaFechaGenerada = egresoFacade.obtenerUltimaFechaSaldo(EMPRESA_USUARIO);	
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
}