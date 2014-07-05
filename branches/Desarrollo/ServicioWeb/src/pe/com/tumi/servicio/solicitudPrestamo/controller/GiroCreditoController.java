package pe.com.tumi.servicio.solicitudPrestamo.controller;

/*****************************************************************************
 * NOMBRE DE LA CLASE: GiroCreditoController 
 * FUNCIONALIDAD : Clase que tiene parámetros de búsqueda y validaciones.
 * REF. : ----------------
 * AUTOR : ----------------
 * VERSIÓN : ----------------
 * FECHA CREACIÓN : ---------------- 
 * AUTOR Y FECHA MODIFICACION : JCHAVEZ 23.12.2013
 *****************************************************************************/

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadController;
import pe.com.tumi.fileupload.FileUploadControllerServicio;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.PrestamoFacadeLocal;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeRemote;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeRemote;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeRemote;

public class GiroCreditoController {

	protected static Logger log = Logger.getLogger(GiroCreditoController.class);
	
	private PersonaFacadeRemote 			personaFacade;
	private TablaFacadeRemote 				tablaFacade;
	private EmpresaFacadeRemote 			empresaFacade;
	private EgresoFacadeRemote 				egresoFacade;
	private CierreDiarioArqueoFacadeRemote 	cierreDiarioArqueoFacade;
	private PrestamoFacadeLocal 			prestamoFacade;
	private BancoFacadeRemote				bancoFacade;
	
	private List<ExpedienteCredito>			listaExpedienteCredito;
	private	List<Tabla>						listaTablaEstadoPago;
	private	List<Persona>					listaPersonaApoderado;
	private List<EgresoDetalleInterfaz>		listaEgresoDetalleInterfaz;	
	private List<Tabla>						listaTablaSucursal;
	private List<Subsucursal>				listaSubsucursal;
	private List<Tabla>						listaTipoBusquedaSucursal;
	private List<ControlFondosFijos>		listaControl;
	private List<Persona>					listaPersonaGirar;
	
	private ExpedienteCredito	expedienteCreditoGirar;
	private EstadoCredito		estadoCreditoFiltro;
	private	ExpedienteCredito 	registroSeleccionado;
	private ControlFondosFijos 	controlFondosFijosGirar;
	private	Egreso		egreso;
	private Persona		personaGirar;
	private Persona		personaApoderado;	
	private Archivo		archivoCartaPoder;
	
	private String 		glosaDelEgreso;
	private Integer		intTipoPersonaFiltro;
	private Integer		intTipoBusquedaPersonaFiltro;
	private String		strTextoPersonaFiltro;
	private Integer		intTipoCreditoFiltro;
	private	String		strDNIApoderadoFiltro;
	private Integer		intItemExpedienteFiltro; 
	private Integer		intTipoBusquedaSucursal;
	private Integer		intIdSucursalFiltro;
	private Integer		intIdSubsucursalFiltro;
	private Date		dtFechaActual;
	private Integer		intTipoMostrarFondoCambio;
	private Integer		intTipoMostrarSocio;	
	private String		strGlosaEgreso;
	private BigDecimal	bdTotalEgresoDetalleInterfaz;
	private Integer		intItemControlFondoSeleccionar;
	private Integer		intIdPersonaGirarSeleccionar;
	//Valores para la manera en que se va a mostrar la interfaz
	private Integer		MOSTRAR_FONDOCAMBIO_SINGULAR = 1;
	private Integer		MOSTRAR_FONDOCAMBIO_PLURAL = 2;
	private Integer		MOSTRAR_SOCIO_SINGULAR = 1;
	private Integer		MOSTRAR_SOCIO_PLURAL = 2;
	
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private String 		mensajePopUp;

	//Valores de sesión
	private	Integer		EMPRESA_USUARIO;
//	private	Integer		PERSONA_USUARIO;
	private Integer		SUCURSAL_USUARIO_ID;
	private Integer		SUBSUCURSAL_USUARIO_ID;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean datosValidados;
	
	//JCHAVEZ 31.01.2014 Para la generacion de Requisito Préstamo
	private boolean deshabilitarRequisito;
	private boolean habilitarGrabarRequisito;
	private Archivo	archivoAdjuntoGiro;
	private List<RequisitoCreditoComp> lstRequisitoCreditoComp;
	private boolean mostrarPanelAdjuntoGiro;
	private List<RequisitoCredito> lstRequisitoCredito;
	
	public GiroCreditoController(){
		cargarUsuario();
		if(usuario != null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public String getInicioPage() {
		cargarUsuario();
		if(usuario!=null){
			limpiarFormulario();
			listaExpedienteCredito.clear();
			deshabilitarPanelInferior(null);
		}else log.error("--Usuario obtenido es NULL.");
		return "";
	}
	
	private void limpiarFormulario(){
		intTipoPersonaFiltro = 0; //Tipo de Persona
		strTextoPersonaFiltro = "";
		intItemExpedienteFiltro = null; //Nro. Solicitud
		intTipoBusquedaSucursal = 0; //Sucursal
		intIdSucursalFiltro = 0;
		intIdSubsucursalFiltro = 0;
		intTipoCreditoFiltro = 0;
		estadoCreditoFiltro.setIntParaEstadoCreditoCod(0);
		estadoCreditoFiltro.setDtFechaEstadoDesde(null);
		estadoCreditoFiltro.setDtFechaEstadoHasta(null);
		ocultarMensaje();
		archivoAdjuntoGiro = null;
		habilitarGrabarRequisito = false;
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
//		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
		SUCURSAL_USUARIO_ID = usuario.getSucursal().getId().getIntIdSucursal();
		SUBSUCURSAL_USUARIO_ID = usuario.getSubSucursal().getId().getIntIdSubSucursal();		
	}
	
	private void cargarValoresIniciales(){
		try{
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			estadoCreditoFiltro = new EstadoCredito();
			estadoCreditoFiltro.setId(new EstadoCreditoId());
			estadoCreditoFiltro.getId().setIntPersEmpresaPk(EMPRESA_USUARIO);
			
			listaExpedienteCredito = new ArrayList<ExpedienteCredito>();
			
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			egresoFacade = (EgresoFacadeRemote) EJBFactory.getRemote(EgresoFacadeRemote.class);
			cierreDiarioArqueoFacade = (CierreDiarioArqueoFacadeRemote) EJBFactory.getRemote(CierreDiarioArqueoFacadeRemote.class);
			prestamoFacade = (PrestamoFacadeLocal) EJBFactory.getLocal(PrestamoFacadeLocal.class);
			bancoFacade = (BancoFacadeRemote) EJBFactory.getRemote(BancoFacadeRemote.class);
			
			listaTablaEstadoPago = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSOLICPRESTAMO), "A");
			listaTipoBusquedaSucursal = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA), "A");
			
			lstRequisitoCredito = new ArrayList<RequisitoCredito>();
			cargarListaTablaSucursal();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
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
	
	public void buscar(){
		CreditoId cId = new CreditoId();
		try{
			CreditoFacadeRemote creditoFacade = (CreditoFacadeRemote) EJBFactory.getRemote(CreditoFacadeRemote.class);
			
			List<Persona> listaPersonaFiltro = buscarPersona();
			
			if(intTipoCreditoFiltro.equals(new Integer(0))){
				intTipoCreditoFiltro = null;
			}
			if(estadoCreditoFiltro.getIntParaEstadoCreditoCod().equals(new Integer(0))){
				estadoCreditoFiltro.setIntParaEstadoCreditoCod(null);
			}			
			if(estadoCreditoFiltro.getDtFechaEstadoDesde() != null){
				estadoCreditoFiltro.setDtFechaEstadoDesde(new Timestamp(estadoCreditoFiltro.getDtFechaEstadoDesde().getTime()));
			}			
			if(estadoCreditoFiltro.getDtFechaEstadoHasta() != null){
				//Se añade un dia extra en fecha fin porque orginalmente el calendar da la fecha con 00h 00m 00s y ocasiona
				//seleccion de rangos erroneos con fechas registradas en la base de datos que si poseen horas.
				estadoCreditoFiltro.setDtFechaEstadoHasta(new Timestamp(estadoCreditoFiltro.getDtFechaEstadoHasta().getTime()+1 * 24 * 60 * 60 * 1000));
			}
			if(intTipoBusquedaSucursal.equals(new Integer(0))){
				intTipoBusquedaSucursal = null;
			}
			if(intIdSucursalFiltro.equals(new Integer(0))){
				intIdSucursalFiltro = null;
			}
			if(intIdSubsucursalFiltro.equals(new Integer(0))){
				intIdSubsucursalFiltro = null;
			}
			
			//Si no se ha encontrado ninguna persona en base a strTextoPersonaFiltro
			if(listaPersonaFiltro!=null && listaPersonaFiltro.isEmpty()){
				return;
			}
			
			if(listaPersonaFiltro!=null){
				for(Persona persona : listaPersonaFiltro){
					log.info(persona);
				}
			}
			
			listaExpedienteCredito = prestamoFacade.buscarExpedienteParaGiro(listaPersonaFiltro, intTipoCreditoFiltro, 
					estadoCreditoFiltro, intItemExpedienteFiltro, intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro);
			//Recuperamos el Tipo de Credito Empresa
			for (ExpedienteCredito expCred : listaExpedienteCredito) {
				cId.setIntPersEmpresaPk(expCred.getId().getIntPersEmpresaPk());
				cId.setIntParaTipoCreditoCod(expCred.getIntParaTipoCreditoCod());
				cId.setIntItemCredito(expCred.getIntItemCredito());
				Credito getCredito = creditoFacade.getCreditoPorIdCreditoDirecto(cId);
//				expCred.setIntParaTipoCreditoEmpresa(creditoFacade.getCreditoPorIdCreditoDirecto(cId).getIntParaTipoCreditoEmpresa());
				//JCHAVEZ 16.01.2014
				expCred.setIntParaTipoCreditoEmpresa(getCredito!=null?getCredito.getIntParaTipoCreditoEmpresa():null);
			}
			
			
			//Se añade persona administra la cuenta del expediente
			for(Object o : listaExpedienteCredito){
				ExpedienteCredito expedienteCredito = (ExpedienteCredito)o;
				for(CuentaIntegrante cuentaIntegrante : expedienteCredito.getCuenta().getListaIntegrante()){
				//Parametro integrante administra = 1
					if(cuentaIntegrante.getIntParaTipoIntegranteCod().equals(Constante.TIPOINTEGRANTE_ADMINISTRADOR)){						
						expedienteCredito.setPersonaAdministra(devolverPersonaCargada(cuentaIntegrante.getId().getIntPersonaIntegrante()));
					}
				}
			}
			ocultarMensaje();
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la búsqueda.");
			log.error(e.getMessage(),e);
		}
	}
	
	private Persona devolverPersonaCargada(Integer intIdPersona) throws Exception{
		Persona persona = personaFacade.getPersonaPorPK(intIdPersona);
		if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
			persona = personaFacade.getPersonaNaturalPorIdPersona(persona.getIntIdPersona());
			agregarDocumentoDNI(persona);
			agregarNombreCompleto(persona);							
		
		}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
			persona.setJuridica(personaFacade.getJuridicaPorPK(persona.getIntIdPersona()));			
		}
		
		return persona;
	}
	
	public void grabar(){
		log.info("--grabar");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			// 1. Valida Cierre Diario Arqueo, si existe un cierre diario para la sucursal que hace el giro, se procede a la siguiente validación
			if(cierreDiarioArqueoFacade.existeCierreDiarioArqueo(controlFondosFijosGirar.getId().getIntPersEmpresa(), controlFondosFijosGirar.getId().getIntSucuIdSucursal(), null)){
				// 1.1. Valida si la caja asociada a la sucursal se encuentra cerrada el dia anterior
				if(!cierreDiarioArqueoFacade.existeCierreDiaAnterior(controlFondosFijosGirar)){
					mensaje = "La caja de la sucursal no se ha cerrado el dia anterior.";
					return;
				}
				// 1.2. Valida si la caja asociada a la sucursal se encuentra cerrada a la fecha actual
				if(cierreDiarioArqueoFacade.existeCierreDiaActual(controlFondosFijosGirar)){
					mensaje = "La caja de la sucursal ya se ha cerrado para el dia de hoy.";
					return;
				}
			}			
			// 2. Valida que la glosa no sea nula ni vacía
			if(strGlosaEgreso==null || strGlosaEgreso.isEmpty()){
				mensaje = "Debe ingresar una descripción de glosa de egreso.";
				return;
			}
			// 3. Valida si se ha ingresado un apoderado, se debe adjuntar la Carta Poder
			if(personaApoderado != null && archivoCartaPoder==null){
				mensaje = "Debe asignar una Carta de Poder al apoderado.";
				return;
			}
			// 4. Valida Expediente Crédito a Girar (Ver método)
			if(!validarMontosExpediente(expedienteCreditoGirar)){
				mensaje = "Hay un error con los montos del expediente de crédito.";
				return;
			}
			// 5. Valida que el Total General a Girar no supere el monto del Fondo de Cambio a usar
			if(bdTotalEgresoDetalleInterfaz.compareTo(controlFondosFijosGirar.getBdMontoSaldo())>1){
				mensaje = "El monto solicitado en el préstamo es mayor al saldo disponible en el fondo.";
				return;
			}
			// 6. Setea la persona a la que se le gira el prestamo
			expedienteCreditoGirar.setPersonaGirar(obtenerPersonaGirar());
			// 7. Setea la persona apoderada
			expedienteCreditoGirar.setPersonaApoderado(personaApoderado);
			// 8. Setea la Carta Poder adjunta
			expedienteCreditoGirar.setArchivoCartaPoder(archivoCartaPoder);
			// 9. Setea la Glosa ingresada
			expedienteCreditoGirar.setStrGlosaEgreso(strGlosaEgreso);
			// 10. Generación del egreso del préstamo (Ver método)
			Egreso egreso = prestamoFacade.generarEgresoPrestamo(expedienteCreditoGirar, controlFondosFijosGirar, usuario);
			//jchavez 24.06.2014
			expedienteCreditoGirar = egreso.getExpedienteCredito();
			if (egreso.getIntErrorGeneracionEgreso().equals(1)) {
				mensaje = egreso.getStrMsgErrorGeneracionEgreso();
				return;
			}else{
				log.info("MENSAJE: "+egreso.getStrMsgErrorGeneracionEgreso());
			}
			// 11. Seteamos el Egreso (Egreso, Egreso Detalle, Libro Diario, Libro Diario Detalle)
			expedienteCreditoGirar.setEgreso(egreso);
			// 12. Grabación del Giro Préstamo
			prestamoFacade.grabarGiroPrestamo(expedienteCreditoGirar);
			// 13. Exito en la grabación
			exito = Boolean.TRUE;			
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			mensaje = "Se giró correctamente el préstamo.";
			
			buscar();
		}catch (Exception e){
			mensaje = "Error durante proceso de Giro de Préstamos: "+e.getMessage().toString();
			log.error(e.getLocalizedMessage());
			log.error(e.getMessage(),e);
			log.info(e.toString());
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	/**
	 * JCHAVEZ 31.01.2014
	 * Si el monto solicitado en el préstamo es mayor al saldo disponible en la configuracion del fondo, ya no se debe mostrar 
	 * un mensaje de error, se habilita la parte inferior para que se suba un archivo, el cual sera usado como requisito para 
	 * que la Sede Central realice el giro de dicho préstamo. 
	 */
	public void grabarRequisito(){
		log.info("--grabarRequisito()");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			// 1. Valida adjunto Giro Credito
			if(archivoAdjuntoGiro == null){
				mensaje = "Debe asignar un Archivo Adjunto para el giro.";
				return;
			}
			/*
			 * Vamos a la tabla CSE_REQAUTCREDITO,para obtener el requisito que maneja dicho credito, luego vamos a la tabla
			 * CSE_REQAUTESTDETALLE y filtramos todos los que su Tipo de Descripcion pertenescan al Grupo C "Grio", asi se 
			 * obtendrán todos los requisitos necesarios para realizar el giro.
			 */
//			lstRequisitoCreditoComp = prestamoFacade.getRequisitoGiroPrestamo(expedienteCreditoGirar);
			
			RequisitoCredito requisitoCredito = new RequisitoCredito();
			requisitoCredito.setId(new RequisitoCreditoId());
			
			//Seteando valores...
			//Llave del expediente crédito
			requisitoCredito.getId().setIntPersEmpresaPk(expedienteCreditoGirar.getId().getIntPersEmpresaPk());
			requisitoCredito.getId().setIntCuentaPk(expedienteCreditoGirar.getId().getIntCuentaPk());
			requisitoCredito.getId().setIntItemExpediente(expedienteCreditoGirar.getId().getIntItemExpediente());
			requisitoCredito.getId().setIntItemDetExpediente(expedienteCreditoGirar.getId().getIntItemDetExpediente());
			//Llave del requisito
			requisitoCredito.setIntPersEmpresaRequisitoPk(lstRequisitoCreditoComp.get(0).getIntEmpresa());
			requisitoCredito.setIntItemReqAut(lstRequisitoCreditoComp.get(0).getIntItemRequisito());
			requisitoCredito.setIntItemReqAutDet(lstRequisitoCreditoComp.get(0).getIntItemRequisitoDetalle());
			//Archivo cargado
			requisitoCredito.setIntParaTipoArchivoCod(archivoAdjuntoGiro.getId().getIntParaTipoCod());
			requisitoCredito.setIntParaItemArchivo(archivoAdjuntoGiro.getId().getIntItemArchivo());
			requisitoCredito.setIntParaItemHistorico(archivoAdjuntoGiro.getId().getIntItemHistorico());
			//Del registro
			requisitoCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			requisitoCredito.setTsFechaRequisito(new Timestamp(new Date().getTime()));
			
			requisitoCredito = prestamoFacade.grabarRequisito(requisitoCredito);
			
			exito = Boolean.TRUE;			
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabarRequisito = Boolean.FALSE;
			mensaje = "Se grabó el requisito satisfactoriamente.";
			
			buscar();
		}catch (Exception e){
			mensaje = "Error durante proceso de grabación de requisito: "+e.getMessage().toString();
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	/**
	 * Modificado 24.04.2014 jchavez
	 * Metodo que se encarga de la validacion de existencia de requisito para que se realice el giro por 
	 * medio de la sede.
	 * @return
	 */
	public boolean validarExisteRequisito(){
		log.info("--validarExisteRequisito()");
		lstRequisitoCredito.clear();
//		String mensaje = null;
		Boolean exito = true;
		deshabilitarNuevo = false;
		try {
			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
			lstRequisitoCreditoComp = prestamoFacade.getRequisitoGiroPrestamo(expedienteCreditoGirar);
			//2. validamos si existe registro en requisito credito
			if (lstRequisitoCreditoComp!=null && !lstRequisitoCreditoComp.isEmpty()) {
				Integer requisitoSize = lstRequisitoCreditoComp.size();

				for (RequisitoCreditoComp o : lstRequisitoCreditoComp) {
					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
					lstRequisitoCredito.addAll(prestamoFacade.getListaPorPkExpedienteCreditoYRequisitoDetalle(expedienteCreditoGirar.getId(), reqAutDetalleId));
			
				}
				if (lstRequisitoCredito!=null && !lstRequisitoCredito.isEmpty()) {
					if (requisitoSize.equals(lstRequisitoCredito.size())) {
						exito = false;	
						deshabilitarNuevo = true;
					}
				}
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return exito;
	}

	private void agregarDocumentoDNI(Persona persona){
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				persona.setDocumento(documento);
			}
		}
	}
	
	private void agregarNombreCompleto(Persona persona){
		persona.getNatural().setStrNombreCompleto(
				persona.getNatural().getStrNombres()+" "+
				persona.getNatural().getStrApellidoPaterno()+" "+
				persona.getNatural().getStrApellidoMaterno());
	}
	
	private List<Persona> buscarPersona() throws Exception{
		List<Persona> listaPersona = new ArrayList<Persona>();
		
		log.info("intTipoBusquedaPersonaFiltro:"+intTipoBusquedaPersonaFiltro);
		
		if(strTextoPersonaFiltro==null || strTextoPersonaFiltro.isEmpty()){
			//Si strTextoPersonaFiltro se deja vacio, entonces buscaremos todos los registros
			return null;
		}
		
		if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_BUSQSOLICPTMO_CODIGOPERSONA)){	
			Persona persona = personaFacade.getPersonaPorPK(Integer.parseInt(strTextoPersonaFiltro));
			if(persona!=null)listaPersona.add(persona);
		
		}else if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_BUSQSOLICPTMO_APELLIDOSNOMBRES)){
			Natural natural = new Natural();
			natural.setStrNombres(strTextoPersonaFiltro);
			List<Natural> listaNatural = personaFacade.getListaNaturalPorBusqueda(natural);
			if(listaNatural!=null && !listaNatural.isEmpty()){
				for(Natural naturalTemp : listaNatural){
					Persona persona = new Persona();
					persona.setIntIdPersona(naturalTemp.getIntIdPersona());
					listaPersona.add(persona);
				}
			}
			
		}else if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_BUSQSOLICPTMO_DOCUMENTO)){
			if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				Persona persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
						strTextoPersonaFiltro,
						EMPRESA_USUARIO);
				if(persona!=null) listaPersona.add(persona);
			}else if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				Persona persona = personaFacade.getPersonaPorRuc(strTextoPersonaFiltro);
				if(persona!=null) listaPersona.add(persona);
			}			
		}
		
		return listaPersona;
	}
	
	private void cargarListaEgresoDetalleInterfaz() throws Exception{
		listaEgresoDetalleInterfaz = prestamoFacade.cargarListaEgresoDetalleInterfaz(expedienteCreditoGirar);
		
		bdTotalEgresoDetalleInterfaz = new BigDecimal(0);
		for(Object o : listaEgresoDetalleInterfaz){
			EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
			bdTotalEgresoDetalleInterfaz = bdTotalEgresoDetalleInterfaz.add(egresoDetalleInterfaz.getBdSubTotal());
		}
	}

	private void añadirEtiquetaControl(List<ControlFondosFijos> listaCFF)throws Exception{
		for(ControlFondosFijos cFF : listaCFF){
			Tabla tablaTipoFondo = tablaFacade.getTablaPorIdMaestroYIdDetalle(
					Integer.parseInt(Constante.PARAM_T_TIPOFONDOFIJO), cFF.getId().getIntParaTipoFondoFijo());
			cFF.setStrNumeroApertura(tablaTipoFondo.getStrDescripcion()+"-"+cFF.getStrNumeroApertura());
		}
	}
	
	public void verRegistro(ActionEvent event){
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		archivoAdjuntoGiro = null;
		try{
			cargarUsuario();
			strGlosaEgreso = "";
			archivoCartaPoder = null;
			personaApoderado = null;
			glosaDelEgreso = null;
			habilitarGrabar = Boolean.FALSE;
			
			registroSeleccionado = (ExpedienteCredito)event.getComponent().getAttributes().get("item");
			
			dtFechaActual = obtenerFechaActual();			
			expedienteCreditoGirar = registroSeleccionado;
			controlFondosFijosGirar = new ControlFondosFijos();
			
			listaControl = egresoFacade.buscarControlFondosParaGiro(SUCURSAL_USUARIO_ID, SUBSUCURSAL_USUARIO_ID, EMPRESA_USUARIO);
			
			if(listaControl == null || listaControl.isEmpty()){
				mensaje = "No se ha aperturado un fondo para giros en la sucursal "+
					usuario.getSucursal().getJuridica().getStrSiglas()+" - "+usuario.getSubSucursal().getStrDescripcion();
				return;
			}
			
			List<ControlFondosFijos> listaControlValida = new ArrayList<ControlFondosFijos>();
			for(ControlFondosFijos controlFondosFijos : listaControl){
				Acceso acceso = bancoFacade.obtenerAccesoPorControlFondosFijos(controlFondosFijos);
				log.info(controlFondosFijos);
				log.info(acceso);				
				if(acceso!=null
				&& acceso.getAccesoDetalleUsar()!=null
				&& acceso.getAccesoDetalleUsar().getBdMontoMaximo()!=null
				//Se cambia el monto total a monto solicitado... JCHAVEZ 22.01.2014
				&& acceso.getAccesoDetalleUsar().getBdMontoMaximo().compareTo(expedienteCreditoGirar.getBdMontoSolicitado())>=0){
					listaControlValida.add(controlFondosFijos);
				}
			}
			
			if(listaControlValida.isEmpty()){
//				generarRequisito();
				if(validarExisteRequisito()) {
					mostrarPanelAdjuntoGiro = true;
					habilitarGrabarRequisito = true;
					mostrarPanelInferior = Boolean.FALSE;
					return;
				}else{
					mensaje = "Ya existen requisitos registrados para este expediente.";
					archivoAdjuntoGiro = prestamoFacade.getArchivoPorRequisitoCredito(lstRequisitoCredito.get(0));
					mostrarPanelAdjuntoGiro = true;
					habilitarGrabarRequisito = false;
					mostrarPanelInferior = Boolean.FALSE;
					return;
				}
//				mensaje = "No existe un fondo de cambio con un monto máximo configurado que soporte el monto de giro del expediente."; 
			}else{
				mostrarPanelAdjuntoGiro = false;
				habilitarGrabarRequisito = false;
			}
			
			listaControl = listaControlValida;
			
			añadirEtiquetaControl(listaControl);
			
			if(listaControl.size()==1){
				intTipoMostrarFondoCambio = MOSTRAR_FONDOCAMBIO_SINGULAR;
				controlFondosFijosGirar = listaControl.get(0);
			}
			if(listaControl.size()>1){
				intTipoMostrarFondoCambio = MOSTRAR_FONDOCAMBIO_PLURAL;
				//Ordena los controles por item
				Collections.sort(listaControl, new Comparator<ControlFondosFijos>(){
					public int compare(ControlFondosFijos uno, ControlFondosFijos otro) {
						return uno.getId().getIntItemFondoFijo().compareTo(otro.getId().getIntItemFondoFijo());
					}
				});
				controlFondosFijosGirar = listaControl.get(0);
			}
			log.info("intTipoMostrarFondoCambio:"+intTipoMostrarFondoCambio);			
			
			controlFondosFijosGirar.setSucursal(usuario.getSucursal());
			controlFondosFijosGirar.setSubsucursal(usuario.getSubSucursal());
			cargarListaEgresoDetalleInterfaz();
			
			if(expedienteCreditoGirar.getCuenta().getListaIntegrante().size()==1){
				intTipoMostrarSocio = MOSTRAR_SOCIO_SINGULAR;
				personaGirar = expedienteCreditoGirar.getPersonaAdministra();
			}else{
				intTipoMostrarSocio = MOSTRAR_SOCIO_PLURAL;				
				listaPersonaGirar = new ArrayList<Persona>();
				for(CuentaIntegrante cuentaIntegrante : expedienteCreditoGirar.getCuenta().getListaIntegrante()){
					Persona persona = devolverPersonaCargada(cuentaIntegrante.getId().getIntPersonaIntegrante());
					listaPersonaGirar.add(persona);
				}				
			}
			log.info("intTipoMostrarSocio:"+intTipoMostrarSocio);
			
			
			log.info("monto solicitado : "+expedienteCreditoGirar.getBdMontoSolicitado());
			log.info("saldo : "+controlFondosFijosGirar.getBdMontoSaldo());			

			mostrarPanelInferior = Boolean.TRUE;
			
			if(expedienteCreditoGirar.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
				deshabilitarNuevo = Boolean.FALSE;
			}else{
				//si el estado es girado
				deshabilitarNuevo = Boolean.TRUE;
				mensaje = "El expediente de crédito ya se encuentra girado.";
				Egreso egreso = egresoFacade.getEgresoPorExpedienteCredito(expedienteCreditoGirar);
				if(egreso.getIntPersPersonaApoderado()!=null){
					personaApoderado = devolverPersonaCargada(egreso.getIntPersPersonaApoderado());
					archivoCartaPoder = egresoFacade.getArchivoPorEgreso(egreso);
				}
				glosaDelEgreso = egreso.getStrObservacion();
				return;
			}
			log.info("deshabilitarNuevo:"+deshabilitarNuevo);
			
			log.info(expedienteCreditoGirar.getEstadoCreditoUltimo());
			if(expedienteCreditoGirar.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO)
			&& expedienteCreditoGirar.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)){
				mensaje = "No esta permitido girar para el subtipo de operación del crédito.";
				return;
			}
			if(expedienteCreditoGirar.getBdMontoSolicitado().compareTo(controlFondosFijosGirar.getBdMontoSaldo())>1){
				mensaje = "El monto solicitado en el préstamo es mayor al saldo disponible en el fondo.";
				return;
			}
			habilitarGrabar = Boolean.TRUE;
			exito = Boolean.TRUE;
		}catch (Exception e) {
			mensaje = "Ocurrio un error durante el proceso de giro.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		expedienteCreditoGirar = null;
		
		registrarNuevo = Boolean.FALSE;
		mostrarPanelInferior = Boolean.FALSE;
		mostrarPanelAdjuntoGiro = false;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;		
	}	
	
	public void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
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

	public void abrirPopUpBuscarApoderado(){
		try{
			strDNIApoderadoFiltro = "";
			listaPersonaApoderado = new ArrayList<Persona>();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarPersonaApoderado(ActionEvent event){
		try{
			personaApoderado = (Persona)event.getComponent().getAttributes().get("item");
			log.info("persona apoderado selec:"+personaApoderado);						
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	

	public void deseleccionarPersonaApoderado(){
		try{
			personaApoderado = null;
			quitarCartaPoder();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void quitarCartaPoder(){
		try{
			archivoCartaPoder = null;
			((FileUploadController)getSessionBean("fileUploadController")).setArchivoCartaPoder(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void quitarAdjuntoGiro(){
		try{
			archivoAdjuntoGiro = null;
			((FileUploadControllerServicio)getSessionBean("fileUploadControllerServicio")).setArchivoGiroCredito(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarSucursal(){
		try{
			if(intIdSucursalFiltro.intValue()>0){
				listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(intIdSucursalFiltro);
			}else{
				listaSubsucursal = new ArrayList<Subsucursal>();
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	public void seleccionarControlFondo(){
		try{
			for(ControlFondosFijos controlFondosFijos : listaControl){
				if(controlFondosFijos.getId().getIntItemFondoFijo().equals(intItemControlFondoSeleccionar)){
					controlFondosFijosGirar = controlFondosFijos; 
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarPersonaApoderado(){
		try{
			Persona persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
					Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
					strDNIApoderadoFiltro, 
					EMPRESA_USUARIO);
			if(persona != null){
				agregarDocumentoDNI(persona);
				agregarNombreCompleto(persona);
				listaPersonaApoderado.add(persona);
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarAdjuntarCartaPoder(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			archivoCartaPoder = fileUploadController.getArchivoCartaPoder();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}	
	
	public void aceptarAdjuntarGiroCredito(){
		try{
			FileUploadControllerServicio fileUploadControllerServicio = (FileUploadControllerServicio)getSessionBean("fileUploadControllerServicio");
			archivoAdjuntoGiro = fileUploadControllerServicio.getArchivoGiroCredito();
			log.info("Nombre del Archivo recuperado: "+archivoAdjuntoGiro.getStrNombrearchivo());
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}	

	private boolean validarMontosExpediente(ExpedienteCredito expedienteCredito) throws Exception{
		boolean exito = Boolean.FALSE;
		BigDecimal bdMontoCanceladoAcumulado = new BigDecimal(0);
		// En caso que el Subtipo de operacion del expediente credito sea REPRESTAMO (2), se carga el prestamo que se esta cancelando
		// este se encuentra en la tabla CSE_CANCELACIONCREDITO
		if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
			if (expedienteCredito.getListaCancelacionCredito()==null) {
				expedienteCredito.setListaCancelacionCredito(prestamoFacade.getListaCancelacionCreditoPorExpedienteCredito(expedienteCredito));
			}
			if(expedienteCredito.getListaCancelacionCredito()!=null){
				for(CancelacionCredito cancelacionCredito : expedienteCredito.getListaCancelacionCredito()){
					bdMontoCanceladoAcumulado = bdMontoCanceladoAcumulado.add(cancelacionCredito.getBdMontoCancelado());
				}
			}
		}
	
		// Valida que el expediente credito tenga monto total
		if(expedienteCredito.getBdMontoTotal()==null){
			return exito;//FALSE
		}
		// Sumatoria de Gravamen, Aporte, Interes, Mora, monto Solicitado y monto Cancelado 
		BigDecimal bdMontoValidar = new BigDecimal(0);
		bdMontoValidar = bdMontoValidar.add(bdMontoCanceladoAcumulado);
		if(expedienteCredito.getBdMontoGravamen()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoGravamen());
		}
		if(expedienteCredito.getBdMontoAporte()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoAporte());
		}
		if(expedienteCredito.getBdMontoSolicitado()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoSolicitado());
		}
		if (expedienteCredito.getBdMontoInteresAtrasado()!=null) {
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoInteresAtrasado());
		}
		if (expedienteCredito.getBdMontoMoraAtrasada()!=null) {
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoMoraAtrasada());
		}
//		else{
//			return exito;//FALSE
//		}		
		log.info("total : "+expedienteCredito.getBdMontoTotal());
		log.info("valid : "+bdMontoValidar);
		// Valida que la suma de Gravamen, Aporte, Interes, Mora, monto Solicitado y monto Cancelado sea igual al monto Total
		if(!(expedienteCredito.getBdMontoTotal().compareTo(bdMontoValidar)==0)){
			return exito;//FALSE
		}
		// De pasar todas las validaciones...
		exito = Boolean.TRUE;
		return exito;
	}
	
	private Persona obtenerPersonaGirar(){
		Persona personaSeleccionada = null;
		if(intTipoMostrarSocio.equals(MOSTRAR_SOCIO_SINGULAR)){
			personaSeleccionada = personaGirar;
		}else if(intTipoMostrarSocio.equals(MOSTRAR_SOCIO_PLURAL)){
			for(Persona persona : listaPersonaGirar){
				if(persona.getIntIdPersona().equals(intIdPersonaGirarSeleccionar)){
					personaSeleccionada = persona;
				}
			}
		}
		return personaSeleccionada;
	}
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
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
	public String getMensajePopUp() {
		return mensajePopUp;
	}
	public void setMensajePopUp(String mensajePopUp) {
		this.mensajePopUp = mensajePopUp;
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
	public boolean isDatosValidados() {
		return datosValidados;
	}
	public void setDatosValidados(boolean datosValidados) {
		this.datosValidados = datosValidados;
	}
//	public List getListaExpedienteCredito() {
//		return listaExpedienteCredito;
//	}
//	public void setListaExpedienteCredito(List listaExpedienteCredito) {
//		this.listaExpedienteCredito = listaExpedienteCredito;
//	}
	public Integer getIntTipoPersonaFiltro() {
		return intTipoPersonaFiltro;
	}
	public void setIntTipoPersonaFiltro(Integer intTipoPersonaFiltro) {
		this.intTipoPersonaFiltro = intTipoPersonaFiltro;
	}
	public Integer getIntTipoBusquedaPersonaFiltro() {
		return intTipoBusquedaPersonaFiltro;
	}
	public void setIntTipoBusquedaPersonaFiltro(Integer intTipoBusquedaPersonaFiltro) {
		this.intTipoBusquedaPersonaFiltro = intTipoBusquedaPersonaFiltro;
	}
	public String getStrTextoPersonaFiltro() {
		return strTextoPersonaFiltro;
	}
	public void setStrTextoPersonaFiltro(String strTextoPersonaFiltro) {
		this.strTextoPersonaFiltro = strTextoPersonaFiltro;
	}
	public Integer getIntTipoCreditoFiltro() {
		return intTipoCreditoFiltro;
	}
	public void setIntTipoCreditoFiltro(Integer intTipoCreditoFiltro) {
		this.intTipoCreditoFiltro = intTipoCreditoFiltro;
	}
	public EstadoCredito getEstadoCreditoFiltro() {
		return estadoCreditoFiltro;
	}
	public void setEstadoCreditoFiltro(EstadoCredito estadoCreditoFiltro) {
		this.estadoCreditoFiltro = estadoCreditoFiltro;
	}
//	public List getListaTablaEstadoPago() {
//		return listaTablaEstadoPago;
//	}
//	public void setListaTablaEstadoPago(List listaTablaEstadoPago) {
//		this.listaTablaEstadoPago = listaTablaEstadoPago;
//	}
	public Egreso getEgreso() {
		return egreso;
	}
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	public Date getDtFechaActual() {
		return dtFechaActual;
	}
	public void setDtFechaActual(Date dtFechaActual) {
		this.dtFechaActual = dtFechaActual;
	}
	public ExpedienteCredito getExpedienteCreditoGirar() {
		return expedienteCreditoGirar;
	}
	public void setExpedienteCreditoGirar(ExpedienteCredito expedienteCreditoGirar) {
		this.expedienteCreditoGirar = expedienteCreditoGirar;
	}
	public Integer getIntTipoMostrarFondoCambio() {
		return intTipoMostrarFondoCambio;
	}
	public void setIntTipoMostrarFondoCambio(Integer intTipoMostrarFondoCambio) {
		this.intTipoMostrarFondoCambio = intTipoMostrarFondoCambio;
	}
	public Integer getIntTipoMostrarSocio() {
		return intTipoMostrarSocio;
	}
	public void setIntTipoMostrarSocio(Integer intTipoMostrarSocio) {
		this.intTipoMostrarSocio = intTipoMostrarSocio;
	}
	public Integer getMOSTRAR_FONDOCAMBIO_SINGULAR() {
		return MOSTRAR_FONDOCAMBIO_SINGULAR;
	}
	public void setMOSTRAR_FONDOCAMBIO_SINGULAR(Integer mOSTRAR_FONDOCAMBIO_SINGULAR) {
		MOSTRAR_FONDOCAMBIO_SINGULAR = mOSTRAR_FONDOCAMBIO_SINGULAR;
	}
	public Integer getMOSTRAR_FONDOCAMBIO_PLURAL() {
		return MOSTRAR_FONDOCAMBIO_PLURAL;
	}
	public void setMOSTRAR_FONDOCAMBIO_PLURAL(Integer mOSTRAR_FONDOCAMBIO_PLURAL) {
		MOSTRAR_FONDOCAMBIO_PLURAL = mOSTRAR_FONDOCAMBIO_PLURAL;
	}
	public Integer getMOSTRAR_SOCIO_SINGULAR() {
		return MOSTRAR_SOCIO_SINGULAR;
	}
	public void setMOSTRAR_SOCIO_SINGULAR(Integer mOSTRAR_SOCIO_SINGULAR) {
		MOSTRAR_SOCIO_SINGULAR = mOSTRAR_SOCIO_SINGULAR;
	}
	public Integer getMOSTRAR_SOCIO_PLURAL() {
		return MOSTRAR_SOCIO_PLURAL;
	}
	public void setMOSTRAR_SOCIO_PLURAL(Integer mOSTRAR_SOCIO_PLURAL) {
		MOSTRAR_SOCIO_PLURAL = mOSTRAR_SOCIO_PLURAL;
	}
	public ControlFondosFijos getControlFondosFijosGirar() {
		return controlFondosFijosGirar;
	}
	public void setControlFondosFijosGirar(ControlFondosFijos controlFondosFijosGirar) {
		this.controlFondosFijosGirar = controlFondosFijosGirar;
	}
//	public List getListaPersonaApoderado() {
//		return listaPersonaApoderado;
//	}
//	public void setListaPersonaApoderado(List listaPersonaApoderado) {
//		this.listaPersonaApoderado = listaPersonaApoderado;
//	}
	public String getStrDNIApoderadoFiltro() {
		return strDNIApoderadoFiltro;
	}
	public void setStrDNIApoderadoFiltro(String strDNIApoderadoFiltro) {
		this.strDNIApoderadoFiltro = strDNIApoderadoFiltro;
	}
	public Persona getPersonaApoderado() {
		return personaApoderado;
	}
	public void setPersonaApoderado(Persona personaApoderado) {
		this.personaApoderado = personaApoderado;
	}
	public String getStrGlosaEgreso() {
		return strGlosaEgreso;
	}
	public void setStrGlosaEgreso(String strGlosaEgreso) {
		this.strGlosaEgreso = strGlosaEgreso;
	}
//	public List getListaEgresoDetalleInterfaz() {
//		return listaEgresoDetalleInterfaz;
//	}
//	public void setListaEgresoDetalleInterfaz(List listaEgresoDetalleInterfaz) {
//		this.listaEgresoDetalleInterfaz = listaEgresoDetalleInterfaz;
//	}
	public BigDecimal getBdTotalEgresoDetalleInterfaz() {
		return bdTotalEgresoDetalleInterfaz;
	}
	public void setBdTotalEgresoDetalleInterfaz(BigDecimal bdTotalEgresoDetalleInterfaz) {
		this.bdTotalEgresoDetalleInterfaz = bdTotalEgresoDetalleInterfaz;
	}
	public Integer getIntItemExpedienteFiltro() {
		return intItemExpedienteFiltro;
	}
	public void setIntItemExpedienteFiltro(Integer intItemExpedienteFiltro) {
		this.intItemExpedienteFiltro = intItemExpedienteFiltro;
	}
	public Integer getIntIdSucursalFiltro() {
		return intIdSucursalFiltro;
	}
	public void setIntIdSucursalFiltro(Integer intIdSucursalFiltro) {
		this.intIdSucursalFiltro = intIdSucursalFiltro;
	}
	public Integer getIntIdSubsucursalFiltro() {
		return intIdSubsucursalFiltro;
	}
	public void setIntIdSubsucursalFiltro(Integer intIdSubsucursalFiltro) {
		this.intIdSubsucursalFiltro = intIdSubsucursalFiltro;
	}
	public Integer getIntTipoBusquedaSucursal() {
		return intTipoBusquedaSucursal;
	}
	public void setIntTipoBusquedaSucursal(Integer intTipoBusquedaSucursal) {
		this.intTipoBusquedaSucursal = intTipoBusquedaSucursal;
	}
//	public List getListaTablaSucursal() {
//		return listaTablaSucursal;
//	}
//	public void setListaTablaSucursal(List listaTablaSucursal) {
//		this.listaTablaSucursal = listaTablaSucursal;
//	}
//	public List getListaSubsucursal() {
//		return listaSubsucursal;
//	}
//	public void setListaSubsucursal(List listaSubsucursal) {
//		this.listaSubsucursal = listaSubsucursal;
//	}
//	public List getListaTipoBusquedaSucursal() {
//		return listaTipoBusquedaSucursal;
//	}
//	public void setListaTipoBusquedaSucursal(List listaTipoBusquedaSucursal) {
//		this.listaTipoBusquedaSucursal = listaTipoBusquedaSucursal;
//	}
	public List<ControlFondosFijos> getListaControl() {
		return listaControl;
	}
	public void setListaControl(List<ControlFondosFijos> listaControl) {
		this.listaControl = listaControl;
	}
	public Integer getIntItemControlFondoSeleccionar() {
		return intItemControlFondoSeleccionar;
	}
	public void setIntItemControlFondoSeleccionar(Integer intItemControlFondoSeleccionar) {
		this.intItemControlFondoSeleccionar = intItemControlFondoSeleccionar;
	}
	public List<Persona> getListaPersonaGirar() {
		return listaPersonaGirar;
	}
	public void setListaPersonaGirar(List<Persona> listaPersonaGirar) {
		this.listaPersonaGirar = listaPersonaGirar;
	}
	public Integer getIntIdPersonaGirarSeleccionar() {
		return intIdPersonaGirarSeleccionar;
	}
	public void setIntIdPersonaGirarSeleccionar(Integer intIdPersonaGirarSeleccionar) {
		this.intIdPersonaGirarSeleccionar = intIdPersonaGirarSeleccionar;
	}
	public Persona getPersonaGirar() {
		return personaGirar;
	}
	public void setPersonaGirar(Persona personaGirar) {
		this.personaGirar = personaGirar;
	}
	public Archivo getArchivoCartaPoder() {
		return archivoCartaPoder;
	}
	public void setArchivoCartaPoder(Archivo archivoCartaPoder) {
		this.archivoCartaPoder = archivoCartaPoder;
	}
	//Agregado JHAVEZ 24.12.2013
	public List<ExpedienteCredito> getListaExpedienteCredito() {
		return listaExpedienteCredito;
	}

	public void setListaExpedienteCredito(
			List<ExpedienteCredito> listaExpedienteCredito) {
		this.listaExpedienteCredito = listaExpedienteCredito;
	}

	public List<Tabla> getListaTablaEstadoPago() {
		return listaTablaEstadoPago;
	}

	public void setListaTablaEstadoPago(List<Tabla> listaTablaEstadoPago) {
		this.listaTablaEstadoPago = listaTablaEstadoPago;
	}

	public List<Persona> getListaPersonaApoderado() {
		return listaPersonaApoderado;
	}

	public void setListaPersonaApoderado(List<Persona> listaPersonaApoderado) {
		this.listaPersonaApoderado = listaPersonaApoderado;
	}

	public List<EgresoDetalleInterfaz> getListaEgresoDetalleInterfaz() {
		return listaEgresoDetalleInterfaz;
	}

	public void setListaEgresoDetalleInterfaz(
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz) {
		this.listaEgresoDetalleInterfaz = listaEgresoDetalleInterfaz;
	}

	public List<Tabla> getListaTablaSucursal() {
		return listaTablaSucursal;
	}

	public void setListaTablaSucursal(List<Tabla> listaTablaSucursal) {
		this.listaTablaSucursal = listaTablaSucursal;
	}

	public List<Subsucursal> getListaSubsucursal() {
		return listaSubsucursal;
	}

	public void setListaSubsucursal(List<Subsucursal> listaSubsucursal) {
		this.listaSubsucursal = listaSubsucursal;
	}

	public List<Tabla> getListaTipoBusquedaSucursal() {
		return listaTipoBusquedaSucursal;
	}

	public void setListaTipoBusquedaSucursal(List<Tabla> listaTipoBusquedaSucursal) {
		this.listaTipoBusquedaSucursal = listaTipoBusquedaSucursal;
	}

	public String getGlosaDelEgreso() {
		return glosaDelEgreso;
	}

	public void setGlosaDelEgreso(String glosaDelEgreso) {
		this.glosaDelEgreso = glosaDelEgreso;
	}

	public boolean isDeshabilitarRequisito() {
		return deshabilitarRequisito;
	}

	public void setDeshabilitarRequisito(boolean deshabilitarRequisito) {
		this.deshabilitarRequisito = deshabilitarRequisito;
	}

	public boolean isHabilitarGrabarRequisito() {
		return habilitarGrabarRequisito;
	}

	public void setHabilitarGrabarRequisito(boolean habilitarGrabarRequisito) {
		this.habilitarGrabarRequisito = habilitarGrabarRequisito;
	}
	public Archivo getArchivoAdjuntoGiro() {
		return archivoAdjuntoGiro;
	}
	public void setArchivoAdjuntoGiro(Archivo archivoAdjuntoGiro) {
		this.archivoAdjuntoGiro = archivoAdjuntoGiro;
	}
	public List<RequisitoCreditoComp> getLstRequisitoCreditoComp() {
		return lstRequisitoCreditoComp;
	}
	public void setLstRequisitoCreditoComp(
			List<RequisitoCreditoComp> lstRequisitoCreditoComp) {
		this.lstRequisitoCreditoComp = lstRequisitoCreditoComp;
	}
	public boolean isMostrarPanelAdjuntoGiro() {
		return mostrarPanelAdjuntoGiro;
	}
	public void setMostrarPanelAdjuntoGiro(boolean mostrarPanelAdjuntoGiro) {
		this.mostrarPanelAdjuntoGiro = mostrarPanelAdjuntoGiro;
	}
}
