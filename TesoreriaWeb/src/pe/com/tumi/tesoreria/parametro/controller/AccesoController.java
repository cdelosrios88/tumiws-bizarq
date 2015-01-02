package pe.com.tumi.tesoreria.parametro.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalle;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalleRes;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.BancocuentaId;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.BancofondoId;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;

public class AccesoController {

	protected static Logger log = Logger.getLogger(AccesoController.class);	
	

	EmpresaFacadeRemote empresaFacade;
	BancoFacadeLocal bancoFacade;
	TablaFacadeRemote tablaFacade;
	PersonaFacadeRemote personaFacade;
	
	private List 	listaAcceso;
	private List 	listaSucursal;
	private List 	listaSubSucursal;
	private List 	listaAccesoDetalleCuentaBancaria;
	private List 	listaAccesoDetalleFondoFijo;
	private List	listaBancoCuenta;
	private List<Tabla>	listaMoneda;
	
	private Acceso	accesoNuevo;
	private	Acceso	registroSeleccionado;
	private AccesoDetalle accesoDetalleAgregar;
	private Acceso	accesoFiltro;
	private AccesoDetalleRes accesoDetalleRes;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	//private Integer	intTipoFondoFijoSeleccionar;
	private String 		mensajePopUp;	
	private int 		intAccionRegistro;
	private int 		intAccionPersona;
	private final int 	AGREGAR_REGISTRO = 1;
	private final int 	EDITAR_REGISTRO = 2;	
	private final int 	AGREGAR_PERSONA = 1;
	private final int 	EDITAR_PERSONA = 2;
	private Integer		intOrden;
	private	Integer		EMPRESA_USUARIO;
	
	//PopUp de buscqueda Persona
	private	String		strTextoFiltroPersona;
	private Integer		intTipoFiltroPersona;
	private	List		listaPersona;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean	mostrarMensajePopUp;
	private boolean habilitarEditarPopUp;
	
	public AccesoController(){
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
			
			listaBancoCuenta = new ArrayList<Bancocuenta>();
			accesoFiltro = new Acceso();
			
			listaAccesoDetalleCuentaBancaria = new ArrayList<AccesoDetalle>();
			listaAccesoDetalleFondoFijo = new ArrayList<AccesoDetalle>();
			
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);		
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(usuario.getPerfil().getId().getIntPersEmpresaPk());
			listaMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			
			List<Sucursal> listaSucursalTemp = listaSucursal;
			Collections.sort(listaSucursalTemp, new Comparator<Sucursal>(){
				public int compare(Sucursal uno, Sucursal otro) {
					return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
				}
			});		
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			accesoNuevo = new Acceso();
			habilitarGrabar = Boolean.TRUE;
			
			listaAccesoDetalleCuentaBancaria = new ArrayList<AccesoDetalle>();
			listaAccesoDetalleFondoFijo = new ArrayList<AccesoDetalle>();
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;			
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
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
	
	private Sucursal buscarListaSucursalPorId(SucursalId id){
		Sucursal sucursal = null;
		for(Object o : listaSucursal){
			sucursal = (Sucursal)o;
			if(sucursal.getId().getIntPersEmpresaPk().equals(id.getIntPersEmpresaPk()) 
				&& sucursal.getId().getIntIdSucursal().equals(id.getIntIdSucursal())){
				return sucursal;
			}
		}
		return sucursal;
	}
	
	private Acceso aplicarEtiquetaSucursal(Acceso acceso){
		SucursalId sucursalId = new SucursalId();
		sucursalId.setIntPersEmpresaPk(acceso.getIntPersEmpresaSucursal());
		sucursalId.setIntIdSucursal(acceso.getIntSucuIdSucursal());
		Sucursal sucursal = buscarListaSucursalPorId(sucursalId);
		if(sucursal!=null){
			acceso.setStrEtiquetaSucursal(sucursal.getJuridica().getStrSiglas());
		}
		return acceso;
	}
	
	private Acceso aplicarEtiquetaSubSucursal(Acceso acceso) throws BusinessException{
		SubSucursalPK subcursalPK = new SubSucursalPK();
		subcursalPK.setIntPersEmpresaPk(acceso.getIntPersEmpresaSucursal());
		subcursalPK.setIntIdSucursal(acceso.getIntSucuIdSucursal());
		subcursalPK.setIntIdSubSucursal(acceso.getIntSudeIdSubsucursal());
		Subsucursal subsucursal = empresaFacade.getSubSucursalPorPk(subcursalPK);
		acceso.setStrEtiquetaSubsucursal(subsucursal.getStrDescripcion());
		return acceso;
	}
	
	public void buscar(){
		try{
			if(accesoFiltro.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
				accesoFiltro.setIntParaEstado(null);
			}
			accesoFiltro.getId().setIntPersEmpresaAcceso(usuario.getPerfil().getId().getIntPersEmpresaPk());			
			accesoFiltro.setIntPersEmpresaSucursal(usuario.getPerfil().getId().getIntPersEmpresaPk());
			
			listaAcceso = bancoFacade.buscarAcceso(accesoFiltro);
			for(Object o : listaAcceso){
				Acceso acceso = (Acceso)o;
				acceso = aplicarEtiquetaSucursal(acceso);
				acceso = aplicarEtiquetaSubSucursal(acceso);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void grabar(){
		log.info("--grabar");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try{
			
			
			if(accesoNuevo.getIntSucuIdSucursal()==null || accesoNuevo.getIntSucuIdSucursal().equals(new Integer(0))){
				mensaje = "Hubo un error durante el registro del acceso. Debe seleccionar una sucursal.";
				return;
			}
			if(accesoNuevo.getIntSudeIdSubsucursal()==null || accesoNuevo.getIntSudeIdSubsucursal().equals(new Integer(0))){
				mensaje = "Hubo un error durante el registro del acceso. Debe seleccionar una subsucursal.";
				return;
			}
			
			accesoNuevo.setListaAccesoDetalle(new ArrayList<AccesoDetalle>());
			accesoNuevo.getListaAccesoDetalle().addAll(listaAccesoDetalleCuentaBancaria);
			accesoNuevo.getListaAccesoDetalle().addAll(listaAccesoDetalleFondoFijo);
			if(accesoNuevo.getListaAccesoDetalle().isEmpty()){
				mensaje = "Hubo un error durante el registro del acceso. Debe ingresar al menos una cuenta bancaria o fondo fijo.";
				return;
			}
			
			if(registrarNuevo){
				log.info("--registrar");
				accesoNuevo.getId().setIntPersEmpresaAcceso(usuario.getPerfil().getId().getIntPersEmpresaPk());
				accesoNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				for(AccesoDetalle accesoDetalle : accesoNuevo.getListaAccesoDetalle()){
					accesoDetalle.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					accesoDetalle.setIntPersEmpresaUsuario(usuario.getPerfil().getId().getIntPersEmpresaPk());
					accesoDetalle.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				}
				
				bancoFacade.grabarAcceso(accesoNuevo);
				mensaje = "Se registró correctamente el acceso.";
			}else{
				log.info("--modificar");
				accesoNuevo.setIntPersEmpresaModifica(usuario.getPerfil().getId().getIntPersEmpresaPk());
				accesoNuevo.setIntPersPersonaModifica(usuario.getIntPersPersonaPk());
				
				bancoFacade.modificarAcceso(accesoNuevo);
				mensaje = "Se modificó correctamente el acceso.";				
			}
			buscar();
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de registro del acceso.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (Acceso)event.getComponent().getAttributes().get("item");
			if(registroSeleccionado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				cargarRegistro();
				mostrarBtnEliminar= Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
				registrarNuevo = Boolean.FALSE;
				mostrarPanelInferior = Boolean.TRUE;
				ocultarMensaje();
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
	
	public void modificarRegistro(){
		try{
			log.info("--modificarRegistro");	
			cargarRegistro();
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			ocultarMensaje();			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarEstado(){
		try{
			if(accesoDetalleAgregar.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				habilitarEditarPopUp = Boolean.TRUE;
			}else{
				habilitarEditarPopUp = Boolean.FALSE;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarRegistro() throws BusinessException{
		accesoNuevo = registroSeleccionado;
		listaAccesoDetalleCuentaBancaria.clear();
		listaAccesoDetalleFondoFijo.clear();
		
		seleccionarSucursal();
		
		CuentaBancariaPK cuentaBancariaPK;
		Bancofondo bancoFondo;
		BancofondoId bancoFondoId;
		BancocuentaId bancoCuentaId;
		Bancocuenta bancoCuenta;
		CuentaBancaria cuentaBancaria;
		List<AccesoDetalleRes> listaAccesoDetalleRes;
		
		for(AccesoDetalle accesoDetalle : accesoNuevo.getListaAccesoDetalle()){
			
			listaAccesoDetalleRes = bancoFacade.getListaAccesoDetalleResPorAccesoDetalle(accesoDetalle);
			if(listaAccesoDetalleRes!=null){
				for(AccesoDetalleRes accesoDetalleRes : listaAccesoDetalleRes){
					accesoDetalleRes.setPersona(personaFacade.getPersonaNaturalPorIdPersona(accesoDetalleRes.getIntPersPersonaResponsable()));
				}
				accesoDetalle.setListaAccesoDetalleRes(listaAccesoDetalleRes);
			}			
			
			bancoFondoId = new BancofondoId();
			bancoFondoId.setIntEmpresaPk(accesoDetalle.getIntPersEmpresa());
			bancoFondoId.setIntItembancofondo(accesoDetalle.getIntItemBancoFondo());
			bancoFondo = bancoFacade.getBancoFondoPorId(bancoFondoId);
			
			if(accesoDetalle.getIntTipoAccesoDetalle().equals(Constante.TIPOACCESODETALLE_CUENTABANCARIA)){
				
				bancoCuentaId = new BancocuentaId();
				bancoCuentaId.setIntEmpresaPk(accesoDetalle.getIntPersEmpresa());
				bancoCuentaId.setIntItembancofondo(accesoDetalle.getIntItemBancoFondo());
				bancoCuentaId.setIntItembancocuenta(accesoDetalle.getIntItemBancoCuenta());
				bancoCuenta = bancoFacade.getBancoCuentaPorId(bancoCuentaId);
				
				cuentaBancariaPK = new CuentaBancariaPK();
				/* Autor: jchavez / Tarea: Modificación / Fecha: 10.12.2014
				 * La persona a la que le pertenece las cuentas es la Cooperativa El Tumi, 
				 * en este caso sería Pers_Empresa. */
				cuentaBancariaPK.setIntIdPersona(bancoCuenta.getId().getIntEmpresaPk());
				//Fin jchavez - 10.12.2014
				cuentaBancariaPK.setIntIdCuentaBancaria(bancoCuenta.getIntCuentabancaria());
				cuentaBancaria = personaFacade.getCuentaBancariaPorPK(cuentaBancariaPK);
				
				bancoCuenta.setCuentaBancaria(cuentaBancaria);
				accesoDetalle.setBancoCuenta(bancoCuenta);
				accesoDetalle.setIntBancoCod(bancoFondo.getIntBancoCod());
				
				listaAccesoDetalleCuentaBancaria.add(accesoDetalle);
			}else{
				
				accesoDetalle.setFondoFijo(bancoFondo);
				listaAccesoDetalleFondoFijo.add(accesoDetalle);
			}
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
	
	public void seleccionarSucursal(){
		try{
			if(accesoNuevo.getIntSucuIdSucursal().equals(new Integer(0))){
				return;
			}
			accesoNuevo.setIntPersEmpresaSucursal(usuario.getPerfil().getId().getIntPersEmpresaPk());
			listaSubSucursal = empresaFacade.getListaSubSucursalPorIdSucursal(accesoNuevo.getIntSucuIdSucursal());
			
			//listaAccesoDetalleFondoFijo.clear();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private String obtenerEtiquetaMoneda(Integer intTipoMoneda){
		for(Tabla tabla : listaMoneda){
			if(tabla.getIntIdDetalle().equals(intTipoMoneda)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	public void seleccionarBanco(){
		try{
			log.info("--seleccionarBanco");
			if(accesoDetalleAgregar.getIntBancoCod().equals(new Integer(0))){
				return;
			}
			Bancofondo bancoFondoTemp = new Bancofondo();
			bancoFondoTemp.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
			bancoFondoTemp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			List<Bancofondo> listaBancoFondoTemp = bancoFacade.buscarBancoFondo(bancoFondoTemp);
			listaBancoCuenta = new ArrayList<Bancocuenta>();
			String strEtiqueta = "";
			for(Bancofondo bancoFondo : listaBancoFondoTemp){
				log.info(bancoFondo);
				if(bancoFondo.getIntBancoCod().equals(accesoDetalleAgregar.getIntBancoCod())){
					for(Bancocuenta bancoCuenta : bancoFondo.getListaBancocuenta()){
						log.info(bancoCuenta);
						strEtiqueta = bancoCuenta.getStrNombrecuenta()+" - "
										+bancoCuenta.getCuentaBancaria().getStrNroCuentaBancaria()+" - "
										+obtenerEtiquetaMoneda(bancoCuenta.getCuentaBancaria().getIntMonedaCod());
						bancoCuenta.setStrEtiqueta(strEtiqueta);
						listaBancoCuenta.add(bancoCuenta);
					}
				}				
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	//Popup cuenta bancaria
	public void abrirPopUpCuentaBancaria(){
		try{
			intAccionRegistro = AGREGAR_REGISTRO;
			accesoDetalleAgregar = new AccesoDetalle();
			accesoDetalleAgregar.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			accesoDetalleAgregar.setIntBancoCod(new Integer(0));
			listaBancoCuenta = new ArrayList<Bancocuenta>();
			
			mostrarMensajePopUp = Boolean.FALSE;
			habilitarEditarPopUp = Boolean.TRUE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private Bancocuenta buscarListaBancoCuentaPorItem(Integer intItembancocuenta){
		for(Object o : listaBancoCuenta){
			Bancocuenta bancoCuenta = (Bancocuenta)o;
			if(intItembancocuenta.equals(bancoCuenta.getId().getIntItembancocuenta())){
				return bancoCuenta;
			}
		}
		return null;
	}
	
	public void aceptarCuentaBancaria(){
		try{
			mostrarMensajePopUp = Boolean.TRUE;
			if(accesoDetalleAgregar.getIntBancoCod().equals(new Integer(0))){
				mensajePopUp = "Debe seleccionar un Banco.";
				return;
			}
			if(accesoDetalleAgregar.getIntItemBancoCuenta().equals(new Integer(0))){
				mensajePopUp = "Debe ingresar una Cuenta Bancaria.";
				return;
			}
			if(accesoDetalleAgregar.getListaAccesoDetalleRes()==null || accesoDetalleAgregar.getListaAccesoDetalleRes().isEmpty()){
				mensajePopUp = "Debe agregar al menos un responsable";
				return;
			}
			if(!validarOrdenListaResponsable(accesoDetalleAgregar)){
				mensajePopUp = "Debe ingresar un Orden adecuado a todos los Responsables.";
				return;
			}
			/*if(accesoDetalleAgregar.getBdMontoAlerta()==null || accesoDetalleAgregar.getBdMontoAlerta().equals(new Integer(0))){
				mensajePopUp = "Debe ingresar un monto de alerta.";
				return;
			}
			if(accesoDetalleAgregar.getBdMontoSobregiro()==null || accesoDetalleAgregar.getBdMontoSobregiro().equals(new Integer(0))){
				mensajePopUp = "Debe ingresar un monto de sobregiro.";
				return;
			}*/
			mostrarMensajePopUp = Boolean.FALSE;
			
			Bancocuenta bancoCuenta = buscarListaBancoCuentaPorItem(accesoDetalleAgregar.getIntItemBancoCuenta());
			accesoDetalleAgregar.setIntPersEmpresa(bancoCuenta.getId().getIntEmpresaPk());
			accesoDetalleAgregar.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
			accesoDetalleAgregar.setBancoCuenta(bancoCuenta);
			accesoDetalleAgregar.setIntTipoAccesoDetalle(Constante.TIPOACCESODETALLE_CUENTABANCARIA);
			
			if(intAccionRegistro==AGREGAR_REGISTRO){
				accesoDetalleAgregar.setTsFechaRegistro(new Timestamp(new Date().getTime()));
				accesoDetalleAgregar.setIntPersEmpresaUsuario(usuario.getPerfil().getId().getIntPersEmpresaPk());
				accesoDetalleAgregar.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				listaAccesoDetalleCuentaBancaria.add(accesoDetalleAgregar);
			}
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void editarPopUpCuentaBancaria(ActionEvent event){
		try{
			intAccionRegistro = EDITAR_REGISTRO;
			accesoDetalleAgregar = (AccesoDetalle)event.getComponent().getAttributes().get("item");
			
			if(accesoDetalleAgregar.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				habilitarEditarPopUp = Boolean.TRUE;				
			}else{
				habilitarEditarPopUp = Boolean.FALSE;
			}
			
			Collections.sort(accesoDetalleAgregar.getListaAccesoDetalleRes());
			seleccionarBanco();
			mostrarMensajePopUp = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarCuentaBancaria(ActionEvent event){
		try{
			accesoDetalleAgregar = (AccesoDetalle)event.getComponent().getAttributes().get("item");			
			
			listaAccesoDetalleCuentaBancaria.remove(accesoDetalleAgregar);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarRegistro(){
		String mensaje = "";
		boolean exito = Boolean.FALSE;
		try{
			listaAcceso.remove(registroSeleccionado);
			bancoFacade.anularAcceso(registroSeleccionado);
			mensaje = "Se eliminó correctamente el registro.";
			exito = Boolean.TRUE;
		}catch(Exception e){
			mensaje = "Ocurrió un error eliminando el registro.";			
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito, mensaje);
		}
	}
	
	//Popup fondofijos
	public void abrirPopUpFondoFijo(){
		try{
			intAccionRegistro = AGREGAR_REGISTRO;
			accesoDetalleAgregar = new AccesoDetalle();
			accesoDetalleAgregar.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			//accesoDetalleAgregar.setIntParaTipoFondoFijo(0);
			
			mostrarMensajePopUp = Boolean.FALSE;
			habilitarEditarPopUp = Boolean.TRUE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private Bancofondo validarExistenciaReferenciaFondoFijo(AccesoDetalle accesoDetalle) throws BusinessException{
		accesoDetalle.getFondoFijo().setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO);
		accesoDetalle.getFondoFijo().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		List<Bancofondo> listaFondoFijo = bancoFacade.buscarBancoFondo(accesoDetalle.getFondoFijo());
		for(Bancofondo bancoFondo : listaFondoFijo){
			if(bancoFondo.getIntTipoFondoFijo().equals(accesoDetalle.getFondoFijo().getIntTipoFondoFijo())){
				log.info(bancoFondo);
				return bancoFondo;
			}
		}
		return null;
	}
	
	private boolean validarUnicidadReferenciaFondoFijo(AccesoDetalle accesoDetalle) throws BusinessException{
		try{
			//Se valida que no el fondofijo referenciado no se encuentre asociado ya a otro accesoDetalle en la interfaz
			for(Object o : listaAccesoDetalleFondoFijo){
				AccesoDetalle accesoDetalleTemp = (AccesoDetalle)o;
				log.info(accesoDetalle);
				if(accesoDetalleTemp.getIntTipoAccesoDetalle().equals(Constante.TIPOACCESODETALLE_FONDOFIJO)){
					if(accesoDetalleTemp.getIntPersEmpresa().equals(accesoDetalle.getIntPersEmpresa())
						&& accesoDetalleTemp.getIntItemBancoFondo().equals(accesoDetalle.getIntItemBancoFondo())){
						log.info("--false");
						return Boolean.FALSE;
					}
				}
			}
			
			//Se valida que no el fondofijo referenciado no se encuentre asociado ya a otro accesoDetalle en la BD
			Acceso accesoTempFiltro = new Acceso();
			accesoTempFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			List<Acceso> listaAccesoTemp = bancoFacade.buscarAcceso(accesoTempFiltro);
			
			for(Acceso accesoTemp : listaAccesoTemp){
				for(AccesoDetalle accesoDetalleTemp : accesoTemp.getListaAccesoDetalle()){
					log.info(accesoDetalle);
					if(accesoDetalleTemp.getIntTipoAccesoDetalle().equals(Constante.TIPOACCESODETALLE_FONDOFIJO)){
						if(accesoDetalleTemp.getIntPersEmpresa().equals(accesoDetalle.getIntPersEmpresa())
							&& accesoDetalleTemp.getIntItemBancoFondo().equals(accesoDetalle.getIntItemBancoFondo())){
							log.info("--false");
							return Boolean.FALSE;
						}
					}
				}				
			}
			
			log.info("--true");
			return Boolean.TRUE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			log.info("--false");
			return Boolean.FALSE;			
		}
	}
	
	private boolean validarOrdenListaResponsable(AccesoDetalle accesoDetalle) throws BusinessException{
		for(AccesoDetalleRes accesoDetalleRes : accesoDetalle.getListaAccesoDetalleRes()){
			if(accesoDetalleRes.getIntOrden()==null || accesoDetalleRes.getIntOrden().compareTo(new Integer(1))<0){
				return Boolean.FALSE;
			}
		}
		
		boolean existeOrden1 = Boolean.FALSE;
		for(AccesoDetalleRes accesoDetalleRes : accesoDetalle.getListaAccesoDetalleRes()){
			if(accesoDetalleRes.getIntOrden().equals(new Integer(1))){
				existeOrden1 = Boolean.TRUE;
				break;
			}
		}
		if(!existeOrden1){
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}
	
	private boolean validarUnicidadFondoFijo(AccesoDetalle accesoDetalleAgregar, Integer intAccion){
		boolean esNuevo = Boolean.TRUE;
		
		if(intAccion == AGREGAR_REGISTRO){
			for(AccesoDetalle accesoDetalle : accesoNuevo.getListaAccesoDetalle()){
				if(accesoDetalle.getIntPersEmpresa().equals(accesoDetalleAgregar.getIntPersEmpresa())
				&& accesoDetalle.getIntItemBancoFondo().equals(accesoDetalleAgregar.getIntItemBancoFondo())
				&& accesoDetalle.getIntItemBancoCuenta()==null){
					esNuevo = Boolean.FALSE;
					break;
				}
			}
		}else if(intAccion == EDITAR_REGISTRO){
			
		}
		
		return esNuevo;
	}
	
	public void aceptarFondoFijo(){
		try{
			mostrarMensajePopUp = Boolean.TRUE;
			
			//Se le asocia el banco fondo referenciado en el popup por la combinacion de tipofondofijo y moneda
			accesoDetalleAgregar.getFondoFijo().getId().setIntEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
			Bancofondo bancoFondoTemp = validarExistenciaReferenciaFondoFijo(accesoDetalleAgregar);			
			log.info(bancoFondoTemp);
			
			if(bancoFondoTemp==null){
				mensajePopUp = "No existe un fondo fijo registrado con el nombre y moneda seleccionados.";
				return;
			}
			accesoDetalleAgregar.setIntPersEmpresa(bancoFondoTemp.getId().getIntEmpresaPk());
			accesoDetalleAgregar.setIntItemBancoFondo(bancoFondoTemp.getId().getIntItembancofondo());
			accesoDetalleAgregar.setFondoFijo(bancoFondoTemp);
			
			/*if(!validarUnicidadReferenciaFondoFijo(accesoDetalleAgregar)){
				mensajePopUp = "El fondo fijo referenciado ya se encuentra asociado a otro acceso.";
				return;
			}*/
			if(!validarUnicidadFondoFijo(accesoDetalleAgregar,intAccionRegistro)){
				mensajePopUp = "Ya se ha seleccionado ese fondo fijo para el fondo actual.";
				return;
			}
			if(accesoDetalleAgregar.getListaAccesoDetalleRes()==null || accesoDetalleAgregar.getListaAccesoDetalleRes().isEmpty()){
				mensajePopUp = "Debe agregar al menos un responsable";
				return;
			}
			if(!validarOrdenListaResponsable(accesoDetalleAgregar)){
				mensajePopUp = "Debe ingresar un Orden adecuado a todos los Responsables.";
				return;
			}
			/*if(accesoDetalleAgregar.getBdMontoFondo()==null || accesoDetalleAgregar.getBdMontoFondo().equals(new Integer(0))){
				mensajePopUp = "Debe ingresar un monto del fondo.";
				return;
			}
			if(accesoDetalleAgregar.getBdMontoMaximo()==null || accesoDetalleAgregar.getBdMontoMaximo().equals(new Integer(0))){
				mensajePopUp = "Debe ingresar el monto máximo del fondo.";
				return;
			}
			if(accesoDetalleAgregar.getBdMontoAlerta()==null || accesoDetalleAgregar.getBdMontoAlerta().equals(new Integer(0))){
				mensajePopUp = "Debe ingresar el monto de alerta de reintegro.";
				return;
			}
			if(accesoDetalleAgregar.getIntCantidadTiempo()==null || accesoDetalleAgregar.getIntCantidadTiempo().equals(new Integer(0))){
				mensajePopUp = "Debe ingresar la cantidad de tiempo de vencimiento.";
				return;
			}
			*/
			mostrarMensajePopUp = Boolean.FALSE;

			accesoDetalleAgregar.setIntTipoAccesoDetalle(Constante.TIPOACCESODETALLE_FONDOFIJO);
			
			if(intAccionRegistro==AGREGAR_REGISTRO){
				accesoDetalleAgregar.setTsFechaRegistro(new Timestamp(new Date().getTime()));
				accesoDetalleAgregar.setIntPersEmpresaUsuario(usuario.getPerfil().getId().getIntPersEmpresaPk());
				accesoDetalleAgregar.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				listaAccesoDetalleFondoFijo.add(accesoDetalleAgregar);				
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}

	
	public void editarPopUpFondoFijo(ActionEvent event){
		try{
			intAccionRegistro = EDITAR_REGISTRO;
			accesoDetalleAgregar = (AccesoDetalle)event.getComponent().getAttributes().get("item");
			
			if(accesoDetalleAgregar.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				habilitarEditarPopUp = Boolean.TRUE;				
			}else{
				habilitarEditarPopUp = Boolean.FALSE;
			}
			
			Collections.sort(accesoDetalleAgregar.getListaAccesoDetalleRes());
			mostrarMensajePopUp = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarFondoFijo(ActionEvent event){
		try{
			accesoDetalleAgregar = (AccesoDetalle)event.getComponent().getAttributes().get("item");
			listaAccesoDetalleFondoFijo.remove(accesoDetalleAgregar);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	//PopUp Buscar Persona
	
	public void abrirPopUpPersona(){
		listaPersona = new ArrayList<Persona>();
		strTextoFiltroPersona = "";
		intTipoFiltroPersona = new Integer(0);
		intOrden = null;
		intAccionPersona = AGREGAR_PERSONA;
		accesoDetalleRes = new AccesoDetalleRes();
	}
	
	public void editarPopUpPersona(ActionEvent event){		
		try{
			accesoDetalleRes = (AccesoDetalleRes)event.getComponent().getAttributes().get("item");
			listaPersona = new ArrayList<Persona>();
			listaPersona.add(accesoDetalleRes.getPersona());
			
			strTextoFiltroPersona = "";
			intTipoFiltroPersona = new Integer(0);
			intOrden = accesoDetalleRes.getIntOrden();
			intAccionPersona = EDITAR_PERSONA;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarPersona(){
		try{
			//log.info("--buscarPersona");
			Persona personaFiltro = new Persona();
			personaFiltro.setNatural(new Natural());
			personaFiltro.setDocumento(new Documento());
			personaFiltro.setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
			
			//log.info("intTipoFiltroPersona:"+intTipoFiltroPersona);
			//log.info("strTextoFiltroPersona:"+strTextoFiltroPersona);
			
			if(intTipoFiltroPersona.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
				personaFiltro.getNatural().setStrNombres(strTextoFiltroPersona);
			
			}else if(intTipoFiltroPersona.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
				personaFiltro.getDocumento().setIntTipoIdentidadCod(Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI));
				personaFiltro.getDocumento().setStrNumeroIdentidad(strTextoFiltroPersona);
			}			
			
			listaPersona = personaFacade.getListPerNaturalBusqueda(personaFiltro);
			
			/*if(listaPersona!=null){
				log.info("listaPersona:"+listaPersona.size());
			}else{
				log.info("listaPersona:null");
			}*/
			

			
			//listaPersona = listaPersonaTemp;
			//SOLO SE PUEDEN BUSCAR PERSONAS QUE POSEAN EL ROL 'PERSONAL' Y PERTENEZCAN A LA MISMA EMPRESA DEL USUARIO
			List<Persona> listaPersonaTemp = new ArrayList<Persona>();
			boolean poseeRolPersonal;
			//listaPersona.clear();
			for(Object o : listaPersona){
				Persona persona = (Persona)o;
				persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
						persona.getDocumento().getStrNumeroIdentidad(), 
						EMPRESA_USUARIO);
				poseeRolPersonal = Boolean.FALSE;
				for(PersonaRol personaRol : persona.getPersonaEmpresa().getListaPersonaRol()){
					log.info(personaRol);
					if(personaRol.getId().getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_PERSONAL)){
						poseeRolPersonal = Boolean.TRUE;
					}
				}
				if(poseeRolPersonal){
					persona.setDocumento(obtenerDocumentoDNI(persona));
					listaPersonaTemp.add(persona);
				}
			}
			
			listaPersona = listaPersonaTemp;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private Documento obtenerDocumentoDNI(Persona persona)throws Exception{		
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				return documento;
			}
		}
		return null;
	}
	
	public void seleccionarPersona(ActionEvent event){
		try{
			log.info("--seleccionarPersona");
			Persona persona =(Persona)event.getComponent().getAttributes().get("item");
			accesoDetalleRes.setIntPersEmpresaResponsable(usuario.getPerfil().getId().getIntPersEmpresaPk());
			accesoDetalleRes.setIntPersPersonaResponsable(persona.getIntIdPersona());
			accesoDetalleRes.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			accesoDetalleRes.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			accesoDetalleRes.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			accesoDetalleRes.setIntPersEmpresaUsuario(usuario.getPerfil().getId().getIntPersEmpresaPk());
			accesoDetalleRes.setIntOrden(intOrden);			
			accesoDetalleRes.setPersona(persona);
			
			if(intAccionPersona==AGREGAR_PERSONA){
				accesoDetalleAgregar.getListaAccesoDetalleRes().add(accesoDetalleRes);
			}
			//ordenamos por intOrden (sort por defecto)
			Collections.sort(accesoDetalleAgregar.getListaAccesoDetalleRes());
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	
	public void eliminarAccesoDetalleResCuenta(ActionEvent event){
		try{
			AccesoDetalleRes accesoDetalleResEliminar = (AccesoDetalleRes)event.getComponent().getAttributes().get("item");			
			accesoDetalleAgregar.getListaAccesoDetalleRes().remove(accesoDetalleResEliminar);
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public Acceso getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(Acceso registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	public List getListaAcceso() {
		return listaAcceso;
	}
	public void setListaAcceso(List listaAcceso) {
		this.listaAcceso = listaAcceso;
	}
	public Acceso getAccesoNuevo() {
		return accesoNuevo;
	}
	public void setAccesoNuevo(Acceso accesoNuevo) {
		this.accesoNuevo = accesoNuevo;
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
	public List getListaAccesoDetalleCuentaBancaria() {
		return listaAccesoDetalleCuentaBancaria;
	}
	public void setListaAccesoDetalleCuentaBancaria(List listaAccesoDetalleCuentaBancaria) {
		this.listaAccesoDetalleCuentaBancaria = listaAccesoDetalleCuentaBancaria;
	}
	public List getListaAccesoDetalleFondoFijo() {
		return listaAccesoDetalleFondoFijo;
	}
	public void setListaAccesoDetalleFondoFijo(List listaAccesoDetalleFondoFijo) {
		this.listaAccesoDetalleFondoFijo = listaAccesoDetalleFondoFijo;
	}
	public List getListaBancoCuenta() {
		return listaBancoCuenta;
	}
	public void setListaBancoCuenta(List listaBancoCuenta) {
		this.listaBancoCuenta = listaBancoCuenta;
	}
	public int getIntAccionRegistro() {
		return intAccionRegistro;
	}
	public void setIntAccionRegistro(int intAccionRegistro) {
		this.intAccionRegistro = intAccionRegistro;
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
	public boolean isHabilitarEditarPopUp() {
		return habilitarEditarPopUp;
	}
	public void setHabilitarEditarPopUp(boolean habilitarEditarPopUp) {
		this.habilitarEditarPopUp = habilitarEditarPopUp;
	}
	public AccesoDetalle getAccesoDetalleAgregar() {
		return accesoDetalleAgregar;
	}
	public void setAccesoDetalleAgregar(AccesoDetalle accesoDetalleAgregar) {
		this.accesoDetalleAgregar = accesoDetalleAgregar;
	}
	public Acceso getAccesoFiltro() {
		return accesoFiltro;
	}
	public void setAccesoFiltro(Acceso accesoFiltro) {
		this.accesoFiltro = accesoFiltro;
	}
	public String getStrTextoFiltroPersona() {
		return strTextoFiltroPersona;
	}
	public void setStrTextoFiltroPersona(String strTextoFiltroPersona) {
		this.strTextoFiltroPersona = strTextoFiltroPersona;
	}
	public Integer getIntTipoFiltroPersona() {
		return intTipoFiltroPersona;
	}
	public void setIntTipoFiltroPersona(Integer intTipoFiltroPersona) {
		this.intTipoFiltroPersona = intTipoFiltroPersona;
	}
	public List getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List listaPersona) {
		this.listaPersona = listaPersona;
	}
	public Integer getIntOrden() {
		return intOrden;
	}
	public void setIntOrden(Integer intOrden) {
		this.intOrden = intOrden;
	}
}