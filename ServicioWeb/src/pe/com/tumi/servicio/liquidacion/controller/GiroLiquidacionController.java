package pe.com.tumi.servicio.liquidacion.controller;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadController;
import pe.com.tumi.fileupload.FileUploadControllerServicio;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;
import pe.com.tumi.servicio.prevision.facade.LiquidacionFacadeLocal;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeLocal;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeRemote;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeRemote;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeRemote;

public class GiroLiquidacionController {

	protected static Logger log = Logger.getLogger(GiroLiquidacionController.class);
	
	private PersonaFacadeRemote personaFacade;
	private TablaFacadeRemote 	tablaFacade;
	private EmpresaFacadeRemote empresaFacade;
	private EgresoFacadeRemote egresoFacade;
	private LiquidacionFacadeLocal liquidacionFacade;
	private CierreDiarioArqueoFacadeRemote cierreDiarioArqueoFacade;
	private BancoFacadeRemote	bancoFacade;
	
	private List<ExpedienteLiquidacion>		listaExpedienteLiquidacion;
	private	List<Tabla>		listaTablaEstadoPago;
	private	List<Persona>		listaPersonaApoderado;
	private List<EgresoDetalleInterfaz>		listaEgresoDetalleInterfaz;	
	private List<Tabla>			listaTablaSucursal;
	private List<Subsucursal>			listaSubsucursal;
	private List<Tabla>		listaTipoBusquedaSucursal;
	private List<Tabla>		listaTablaTipoDocumento;
//	private List		listaCuentaBancaria;
	
	
	private List<ControlFondosFijos>listaControl;
	private List<Persona>			listaPersonaGirar;

	private List<Tabla> listaTipoVinculo;
	private List<Tabla> listaTipoCuenta;
	
	private ExpedienteLiquidacion	expedienteLiquidacionGirar;
	private EstadoLiquidacion		estadoLiquidacionFiltro;
	private ControlFondosFijos 	controlFondosFijosGirar;
	private	Egreso		egreso;
	private Persona		personaGirar;
	
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
	private Integer		intIdPersonaBeneficiarioSeleccionar;
	private Integer		intItemCuentaBancariaSeleccionar;
	private Integer		intTipoBusquedaFechaFiltro;
	/**Valores par ala manera en q se va a mostrar la interfaz**/
	private Integer		MOSTRAR_FONDOCAMBIO_SINGULAR = 1;
	private Integer		MOSTRAR_FONDOCAMBIO_PLURAL = 2;
	private Integer		MOSTRAR_SOCIO_SINGULAR = 1;
	private Integer		MOSTRAR_SOCIO_PLURAL = 2;
	
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private String 		mensajePopUp;
	/**Valores de sesion**/
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
	private boolean esUltimoBeneficiarioAGirar;
	
	//JCHAVEZ 28.01.2014
	private Persona		personaApoderado;	
	private Archivo		archivoCartaPoder;
	private BeneficiarioLiquidacion beneficiarioSeleccionado;
	private Integer intCuenta;
	//JCHAVEZ 08.02.2014
	private boolean deshabilitarNuevoBeneficiario;
	private boolean mostrarPanelAdjuntoGiro;
	private Archivo archivoAdjuntoGiro;
	private Boolean mostrarMensajeAdjuntarRequisito;
	private boolean habilitarGrabarRequisito;
	private List<RequisitoLiquidacion> lstRequisitoLiquidacion;
	private List<RequisitoLiquidacionComp> lstRequisitoLiquidacionComp;
	private String	mensajeAdjuntarRequisito;
	
	//jchavez
	private Boolean visualizarGrabarRequisito;
	private Boolean visualizarGrabarAdjunto;
	private Boolean deshabilitarDescargaAdjuntoGiro;
	//jchavez 19.05.2014
	private ConceptoFacadeRemote conceptoFacade;
	public GiroLiquidacionController(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario != null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
//		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
		SUCURSAL_USUARIO_ID = usuario.getSucursal().getId().getIntIdSucursal();
		SUBSUCURSAL_USUARIO_ID = usuario.getSubSucursal().getId().getIntIdSubSucursal();		
	}
	
	public String getInicioPage() {
		cargarUsuario();
		if(usuario!=null){
			limpiarFormulario();
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
		listaExpedienteLiquidacion.clear();
		estadoLiquidacionFiltro.setIntParaEstado(0);
		estadoLiquidacionFiltro.setDtFechaEstadoDesde(null);
		estadoLiquidacionFiltro.setDtFechaEstadoHasta(null);
		personaApoderado = null;
		archivoCartaPoder = null;
		ocultarMensaje();
		deshabilitarNuevo = Boolean.FALSE;
		archivoAdjuntoGiro = null;
		habilitarGrabarRequisito = false;
		
		mensajeAdjuntarRequisito = "";
		mostrarMensajeAdjuntarRequisito = false;
		mostrarPanelAdjuntoGiro = false;
		deshabilitarNuevoBeneficiario = false;
	}
	
	private void cargarValoresIniciales(){
		try{
			cargarUsuario();			
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			estadoLiquidacionFiltro = new EstadoLiquidacion();
			estadoLiquidacionFiltro.getId().setIntPersEmpresaPk(EMPRESA_USUARIO);
			
			listaExpedienteLiquidacion = new ArrayList<ExpedienteLiquidacion>();
			
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			liquidacionFacade = (LiquidacionFacadeLocal) EJBFactory.getLocal(LiquidacionFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			egresoFacade = (EgresoFacadeRemote) EJBFactory.getRemote(EgresoFacadeRemote.class);
			cierreDiarioArqueoFacade = (CierreDiarioArqueoFacadeRemote) EJBFactory.getRemote(CierreDiarioArqueoFacadeRemote.class);
			bancoFacade = (BancoFacadeRemote) EJBFactory.getRemote(BancoFacadeRemote.class); 
			conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			listaTablaEstadoPago = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSOLICPRESTAMO), "A");
			listaTipoBusquedaSucursal = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA), "A");
			//listaTablaTipoDocumento = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "F");
			listaTablaTipoDocumento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSUBOPERACION));
			listaTipoVinculo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOVINCULO));
			listaTipoCuenta = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA));
			deshabilitarNuevo = Boolean.FALSE;
			cargarListaTablaSucursal();
			
			lstRequisitoLiquidacion = new ArrayList<RequisitoLiquidacion>();
			//Adjuntar Giro Liquidación
			mensajeAdjuntarRequisito = "";
			mostrarMensajeAdjuntarRequisito = false;
			mostrarPanelAdjuntoGiro = false;
			
			deshabilitarDescargaAdjuntoGiro = true;
			deshabilitarNuevoBeneficiario = false;
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
		try{
			List<Persona> listaPersonaFiltro = buscarPersona();
			
			if(intTipoCreditoFiltro.equals(new Integer(0))){
				intTipoCreditoFiltro = null;
			}
			if(estadoLiquidacionFiltro.getIntParaEstado().equals(new Integer(0))){
				estadoLiquidacionFiltro.setIntParaEstado(null);
			}
			if(estadoLiquidacionFiltro.getDtFechaEstadoDesde() != null){
				estadoLiquidacionFiltro.setDtFechaEstadoDesde(new Timestamp(estadoLiquidacionFiltro.getDtFechaEstadoDesde().getTime()));
			}			
			if(estadoLiquidacionFiltro.getDtFechaEstadoHasta() != null){
				//Se añade un dia extra en fecha fin porque orginalmente el calendar da la fecha con 00h 00m 00s y ocasiona
				//seleccion de rangos erroneos con fechas registradas en la base de datos que si poseen horas.
				estadoLiquidacionFiltro.setDtFechaEstadoHasta(new Timestamp(estadoLiquidacionFiltro.getDtFechaEstadoHasta().getTime()+1 * 24 * 60 * 60 * 1000));
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
			//Error NullPointer SOLUCIONADO
			listaExpedienteLiquidacion = liquidacionFacade.buscarExpedienteParaGiro(listaPersonaFiltro, intTipoCreditoFiltro, 
					estadoLiquidacionFiltro, intItemExpedienteFiltro, intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro);
			
			//Se añade persona administra la cuenta del expediente
			for(Object o : listaExpedienteLiquidacion){
				ExpedienteLiquidacion expedienteLiquidacion = (ExpedienteLiquidacion)o;
				aplicarEtiquetaListaCuentas(expedienteLiquidacion);
				expedienteLiquidacion.setPersonaAdministra(devolverPersonaCargada(expedienteLiquidacion.getPersonaAdministra().getIntIdPersona()));
				log.info((ExpedienteLiquidacion)o);
			}
			ocultarMensaje();
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la búsqueda.");
			log.error(e.getMessage(),e);
		}
	}
	
	private void aplicarEtiquetaListaCuentas(ExpedienteLiquidacion expedienteLiquidacion)throws Exception{
		String strEtiqueta = ""; 
		HashSet<Integer> hashTipoCuenta = new HashSet<Integer>();
		for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
			hashTipoCuenta.add(expedienteLiquidacionDetalle.getCuenta().getIntParaTipoCuentaCod());			
		}
		for(Integer intTipoCuenta : hashTipoCuenta){
			strEtiqueta = strEtiqueta + "/"+ obtenerEtiquetaTipoCuenta(intTipoCuenta);
		}
		strEtiqueta = strEtiqueta.substring(1);
		expedienteLiquidacion.setStrEtiquetaListaCuentas(strEtiqueta);
	}
	
	private Persona devolverPersonaCargada(Integer intIdPersona) throws Exception{
		log.info(intIdPersona);
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
		beneficiarioSeleccionado = new BeneficiarioLiquidacion();
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
			// 2. Valida que se seleccione a un beneficiario 
			if(intIdPersonaBeneficiarioSeleccionar.equals(new Integer(0))){
				mensaje = "Debe seleccionar un beneficiario.";
				return;
			}
			// 3. Valida si se ha ingresado un apoderado, se debe adjuntar la Carta Poder
			if(personaApoderado != null && archivoCartaPoder==null){
				mensaje = "Debe asignar una Carta de Poder al apoderado.";
				return;
			}
			// 4. Valida que la glosa no sea nula ni vacía				
			if(strGlosaEgreso==null || strGlosaEgreso.isEmpty()){
				mensaje = "Debe ingresar una descripción de glosa de egreso.";
				return;
			}
			if(bdTotalEgresoDetalleInterfaz.compareTo(controlFondosFijosGirar.getBdMontoSaldo())>1){
				mensaje = "El monto solicitado en el préstamo es mayor al saldo disponible en el fondo.";
				return;
			}
			/******************/
			beneficiarioSeleccionado.setPersonaApoderado(personaApoderado);
			beneficiarioSeleccionado.setArchivoCartaPoder(archivoCartaPoder);
			/******************/
			expedienteLiquidacionGirar.setBeneficiarioLiquidacionGirar(beneficiarioSeleccionado);
			expedienteLiquidacionGirar.setPersonaGirar(personaGirar);
			expedienteLiquidacionGirar.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
			expedienteLiquidacionGirar.setEsUltimoBeneficiarioAGirar(esUltimoBeneficiarioAGirar);
			expedienteLiquidacionGirar.setIntIdPersonaBeneficiarioGirar(intIdPersonaBeneficiarioSeleccionar);
			expedienteLiquidacionGirar.setStrGlosaEgreso(strGlosaEgreso);
			/******************/
			
			egreso = liquidacionFacade.generarEgresoGiroLiquidacion(expedienteLiquidacionGirar, controlFondosFijosGirar, usuario);
			expedienteLiquidacionGirar.setEgreso(egreso);
			
			liquidacionFacade.grabarGiroLiquidacion(expedienteLiquidacionGirar);
			
			exito = Boolean.TRUE;			
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			mensaje = "Se giró correctamente la liquidación.";
			
			buscar();
		}catch (Exception e){
			mensaje = "Ocurrio un error durante el proceso de giro de liquidación."+e.getMessage();
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	private String obtenerEtiquetaVinculo(Integer intItemVinculo) throws Exception{
		Vinculo vinculo = personaFacade.getVinculoPorId(intItemVinculo);
		for(Tabla tabla : listaTipoVinculo){
			if(tabla.getIntIdDetalle().equals(vinculo.getIntTipoVinculoCod())){
				return tabla.getStrDescripcion();
			}
		}
		return null;
	}
	
	private String obtenerEtiquetaTipoCuenta(Integer intTipoCuenta) throws Exception{
		for(Tabla tabla : listaTipoCuenta){
			if(tabla.getIntIdDetalle().equals(intTipoCuenta)){
				return tabla.getStrDescripcion();
			}
		}
		return null;
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
	
	private void esUltimoBeneficiarioAGirar(){
		if(expedienteLiquidacionGirar.getListaPersona().size()==1){
			esUltimoBeneficiarioAGirar = Boolean.TRUE;
		}else{
			esUltimoBeneficiarioAGirar = Boolean.FALSE;
		}
	}
	
	private void cargarListaEgresoDetalleInterfaz(List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle) throws Exception{
		listaEgresoDetalleInterfaz = new ArrayList<EgresoDetalleInterfaz>();
		BigDecimal bdSubTotal = new BigDecimal(0); 
		
		log.info("intIdPersonaBeneficiarioSeleccionar:"+intIdPersonaBeneficiarioSeleccionar);
		listaEgresoDetalleInterfaz = liquidacionFacade.cargarListaEgresoDetalleInterfaz(listaExpedienteLiquidacionDetalle, intIdPersonaBeneficiarioSeleccionar);
		for(Object o : listaEgresoDetalleInterfaz){
			EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
			bdSubTotal = bdSubTotal.add(egresoDetalleInterfaz.getBdSubTotal());
		}
		bdTotalEgresoDetalleInterfaz = bdSubTotal;
	}	
	

	
	private ExpedienteLiquidacion cargarListaBeneficiarioLiquidacion(ExpedienteLiquidacion expedienteLiquidacion)throws Exception{
		List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacionTemp = new ArrayList<BeneficiarioLiquidacion>();
		for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
			List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacionTemp2 = liquidacionFacade.getListaBeneficiarioLiquidacionPorExpedienteLiquidacionDetalle(expedienteLiquidacionDetalle); 
			expedienteLiquidacionDetalle.setListaBeneficiarioLiquidacion(listaBeneficiarioLiquidacionTemp2);
			listaBeneficiarioLiquidacionTemp.addAll(listaBeneficiarioLiquidacionTemp2);
			intCuenta = expedienteLiquidacionDetalle.getId().getIntCuenta();
		}
		
		HashSet<Integer> hashIntIdPersona = new HashSet<Integer>();
		for(BeneficiarioLiquidacion beneficiarioLiquidacion : listaBeneficiarioLiquidacionTemp){
			//Solo listaremos los beneficiarios que aun no hayan girado
			if(beneficiarioLiquidacion.getIntItemEgresoGeneral()==null){
				hashIntIdPersona.add(beneficiarioLiquidacion.getIntPersPersonaBeneficiario());
			}			
		}
		
		List<Persona> listaPersona = new ArrayList<Persona>();
		for(Integer intIdPersona : hashIntIdPersona){
			Integer intItemVinculo = null;
			for(BeneficiarioLiquidacion beneficiarioLiquidacion : listaBeneficiarioLiquidacionTemp){
				if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(intIdPersona)){
					intItemVinculo = beneficiarioLiquidacion.getIntItemViculo();
					break;
				}
			}
			String strEtiqueta = "";
			Persona persona = devolverPersonaCargada(intIdPersona);
			strEtiqueta = (intItemVinculo!=null?intItemVinculo+ " - ":"") + persona.getNatural().getStrNombreCompleto();
			strEtiqueta = strEtiqueta + " - DNI : " + persona.getDocumento().getStrNumeroIdentidad();
			strEtiqueta = strEtiqueta + " - VINCULO : "+(intItemVinculo!=null?obtenerEtiquetaVinculo(intItemVinculo):"TITULAR");
			persona.setStrEtiqueta(strEtiqueta);
			
			listaPersona.add(persona);
		}
		expedienteLiquidacion.setListaPersona(listaPersona);
		
		return expedienteLiquidacion;
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
		try{
			PrevisionFacadeLocal previsionFacade = (PrevisionFacadeLocal) EJBFactory.getLocal(PrevisionFacadeLocal.class);
			
			strGlosaEgreso = "";
			habilitarGrabar = Boolean.FALSE;
			intIdPersonaBeneficiarioSeleccionar = null;
			intItemCuentaBancariaSeleccionar = null;
			listaEgresoDetalleInterfaz = new ArrayList<EgresoDetalleInterfaz>();
			bdTotalEgresoDetalleInterfaz = null;
			
			expedienteLiquidacionGirar = (ExpedienteLiquidacion)event.getComponent().getAttributes().get("item");
			
			cargarUsuario();
			dtFechaActual = obtenerFechaActual();
			controlFondosFijosGirar = new ControlFondosFijos();
			egreso = new Egreso();
			
			listaControl = egresoFacade.buscarControlFondosParaGiro(SUCURSAL_USUARIO_ID, SUBSUCURSAL_USUARIO_ID, EMPRESA_USUARIO);
			
			if(listaControl == null || listaControl.isEmpty()){
				mensaje = "No se ha aperturado un fondo para giros en la sucursal "+
					usuario.getSucursal().getJuridica().getStrSiglas()+" - "+usuario.getSubSucursal().getStrDescripcion();
				return;
			}
			/*
			 * Validación, No se muestra el expediente si el monto bruto a liquidar supera el establecido en Acceso
			 * JCHAVEZ 28.01.2014
			 */
//			List<ControlFondosFijos> listaControlValida = new ArrayList<ControlFondosFijos>();
//			for(ControlFondosFijos controlFondosFijos : listaControl){
//				Acceso acceso = bancoFacade.obtenerAccesoPorControlFondosFijos(controlFondosFijos);
//				log.info(controlFondosFijos);
//				log.info(acceso);				
//				if(acceso!=null
//				&& acceso.getAccesoDetalleUsar()!=null
//				&& acceso.getAccesoDetalleUsar().getBdMontoMaximo()!=null
//				&& acceso.getAccesoDetalleUsar().getBdMontoMaximo().compareTo(expedienteLiquidacionGirar.getBdMontoBrutoLiquidacion())>=0){
//					listaControlValida.add(controlFondosFijos);
//				}
//			}
//			
//			if(listaControlValida.isEmpty()){
//				mensaje = "No existe un fondo de cambio con un monto máximo configurado que soporte el monto de giro del expediente."; 
//				return;
//			}
//			
//			listaControl = listaControlValida;
			
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
			log.info("intTipoMostrarSocio:"+intTipoMostrarSocio);
			log.info("monto solicitado : "+expedienteLiquidacionGirar.getBdMontoBrutoLiquidacion());
			log.info("saldo : "+controlFondosFijosGirar.getBdMontoSaldo());

			controlFondosFijosGirar.setSucursal(usuario.getSucursal());
			controlFondosFijosGirar.setSubsucursal(usuario.getSubSucursal());
			personaGirar = expedienteLiquidacionGirar.getPersonaAdministra();
			expedienteLiquidacionGirar = cargarListaBeneficiarioLiquidacion(expedienteLiquidacionGirar);			
			esUltimoBeneficiarioAGirar();
			mostrarPanelInferior = Boolean.TRUE;			
			
			
			log.info(expedienteLiquidacionGirar.getEstadoLiquidacionUltimo());
			if(!expedienteLiquidacionGirar.getEstadoLiquidacionUltimo().getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
				mensaje = "El expediente de crédito no se encuentra en estado autorizado para girar.";
				return;
			}
			/*
			 * Validación, si para esta cuenta existe Expedientes en Prevision con estado APROBADO (4) no se puede realizar el giro.
			 * JCHAVEZ 28.01.2014
			 */
			CuentaId cId = new CuentaId();
			cId.setIntPersEmpresaPk(expedienteLiquidacionGirar.getId().getIntPersEmpresaPk());
			cId.setIntCuenta(intCuenta);
			List<ExpedientePrevision> lstExpPrevision = previsionFacade.getUltimoEstadoExpPrev(cId);
			if (lstExpPrevision!=null && !lstExpPrevision.isEmpty()) {
				for (ExpedientePrevision o : lstExpPrevision) {
					if (o.getIntEstadoCreditoUltimo().equals(4)) {
						mensaje = "NO se puede girar el expediente de liquidación. Existen solicitudes en expediente previsión";
						return;
					}
				}				
			}
			
			habilitarGrabar = Boolean.TRUE;
			deshabilitarNuevo  = false;
			deshabilitarNuevoBeneficiario = false;
			buscarMovCtaCtePorPagar(expedienteLiquidacionGirar);
			exito = Boolean.TRUE;
		}catch (Exception e) {
//			mensaje = "Ocurrio un error durante el proceso de giro.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	public void buscarMovCtaCtePorPagar(ExpedienteLiquidacion expLiq) throws Exception {
		List<Movimiento> lstMov = null;
//		Integer intParaTipoConcepto = null;
//		List<ExpedienteLiquidacionDetalle> lstExpLiqDet = new ArrayList<ExpedienteLiquidacionDetalle>();
		
		try {
//			lstExpLiqDet = liquidacionFacade.getListaExpedienteLiquidacionDetallePorExpediente(expLiq);
//			if (lstExpLiqDet!=null && !lstExpLiqDet.isEmpty()) {
//				for (ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : lstExpLiqDet) {
//					
//				}
//			}
//			switch (expLiq.getIntParaDocumentoGeneral()) {
//			case (102): //Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO
//				intParaTipoConcepto = Constante.PARAM_T_CUENTACONCEPTO_SEPELIO;
//				break;
//			case (103): //Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO
//				intParaTipoConcepto = Constante.PARAM_T_CUENTACONCEPTO_RETIRO;
//				break;
////			case Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO:
////				intParaTipoConcepto = Constante.PARAM_T_CUENTACONCEPTO_SEPELIO;
////				break;				
//			default:
//				break;
//			}
			lstMov = conceptoFacade.getListaMovCtaCtePorPagarLiq(expLiq.getId().getIntPersEmpresaPk(), intCuenta, expLiq.getId().getIntItemExpediente(), expLiq.getIntParaDocumentoGeneral());
			if (lstMov!=null && !lstMov.isEmpty()) {
				for (Movimiento movimiento : lstMov) {
					if (movimiento.getIntItemMovimientoRel()==null) {
						expedienteLiquidacionGirar.setMovimiento(movimiento);
						break;
					}					
				}
			}else expedienteLiquidacionGirar.setMovimiento(null);
		} catch (Exception e) {
			log.error("Error al buscarMovCtaCtePorPagar: "+e.getMessage(),e);
		}
		
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		expedienteLiquidacionGirar = null;
		
		registrarNuevo = Boolean.FALSE;
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
		
		mensajeAdjuntarRequisito = "";
		mostrarMensajeAdjuntarRequisito = false;
		mostrarPanelAdjuntoGiro = false;
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
	
	public void seleccionarTipoBusquedaSucursal(){
		try{
			if(intTipoBusquedaSucursal.intValue()>0){
				cargarListaTablaSucursal();
				listaSubsucursal = new ArrayList<Subsucursal>();
			}else{
				listaTablaSucursal  = new ArrayList<Tabla>();
				listaSubsucursal = new ArrayList<Subsucursal>();
			}
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
	
	public void seleccionarBeneficiario(){
		try{
			log.info("intIdPersonaBeneficiarioSeleccionar:"+intIdPersonaBeneficiarioSeleccionar);
			if(intIdPersonaBeneficiarioSeleccionar.equals(new Integer(0))){
				intIdPersonaBeneficiarioSeleccionar = 0;
				listaEgresoDetalleInterfaz = new ArrayList<EgresoDetalleInterfaz>();
				bdTotalEgresoDetalleInterfaz = null;
				//
				mostrarPanelAdjuntoGiro = false;
				deshabilitarNuevo = false;
				deshabilitarNuevoBeneficiario = false;
				return;
			}
			
			List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle = new ArrayList<ExpedienteLiquidacionDetalle>();
			for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacionGirar.getListaExpedienteLiquidacionDetalle()){
				for(BeneficiarioLiquidacion beneficiarioLiquidacion : expedienteLiquidacionDetalle.getListaBeneficiarioLiquidacion()){
					if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(intIdPersonaBeneficiarioSeleccionar)){
						beneficiarioSeleccionado = beneficiarioLiquidacion;
						listaExpedienteLiquidacionDetalle.add(expedienteLiquidacionDetalle);
						break;
					}
				}
			}
			
			cargarListaEgresoDetalleInterfaz(listaExpedienteLiquidacionDetalle);
			
			List<ControlFondosFijos> listaControlValida = new ArrayList<ControlFondosFijos>();
			for(ControlFondosFijos controlFondosFijos : listaControl){
				Acceso acceso = bancoFacade.obtenerAccesoPorControlFondosFijos(controlFondosFijos);
				log.info(controlFondosFijos);
				log.info(acceso);				
				if(acceso!=null
				&& acceso.getAccesoDetalleUsar()!=null
				&& acceso.getAccesoDetalleUsar().getBdMontoMaximo()!=null
				&& acceso.getAccesoDetalleUsar().getBdMontoMaximo().compareTo(bdTotalEgresoDetalleInterfaz)>=0){
					listaControlValida.add(controlFondosFijos);
				}
			}
			visualizarGrabarRequisito = true;
			deshabilitarNuevoBeneficiario = false;
			
			if(listaControlValida.isEmpty() && intIdPersonaBeneficiarioSeleccionar!=0){
				
				//Deshabilitamos todos los campos para detener el proceso de grabacion
				if(validarExisteRequisito()) {
					mensajeAdjuntarRequisito = "No existe un fondo de cambio con un monto máximo configurado que soporte el monto de giro del expediente."+
					  "Este giro será realizado por la Sede Central.";
					mostrarMensajeAdjuntarRequisito = true;
					mostrarPanelAdjuntoGiro = true;
					habilitarGrabarRequisito = true;	
					visualizarGrabarAdjunto = true;
					deshabilitarNuevo = true;
					deshabilitarDescargaAdjuntoGiro = false;
				}else{
					mensajeAdjuntarRequisito = "Ya existen requisitos registrados para este expediente.";
					archivoAdjuntoGiro = liquidacionFacade.getArchivoPorRequisitoLiquidacion(lstRequisitoLiquidacion.get(0));
					mostrarMensajeAdjuntarRequisito = true;
					mostrarPanelAdjuntoGiro = true;
					habilitarGrabarRequisito = false;
					visualizarGrabarAdjunto  =false;
					deshabilitarNuevo = true;
					deshabilitarDescargaAdjuntoGiro = true;
					return;
				}
			}else{
				visualizarGrabarRequisito = false;
				mostrarPanelAdjuntoGiro = false;
				deshabilitarNuevo = false;
				deshabilitarDescargaAdjuntoGiro = false;
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	/**
	 * JCHAVEZ 08.02.2014
	 * validacion de existencia de requisito en CSE_REQUISITOLIQUIDACION, en caso exista solo se permitirá
	 * descargar el adjunto guardado, sino se habilita el registro de un nuevo adjunto
	 * @return
	 */
	public boolean validarExisteRequisito(){
		log.info("--validarExisteRequisito()");
		lstRequisitoLiquidacion.clear();
//		String mensaje = null;
		Boolean exito = Boolean.TRUE;
		archivoAdjuntoGiro = null;
		try {
			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
			lstRequisitoLiquidacionComp = liquidacionFacade.getRequisitoGiroLiquidacion(expedienteLiquidacionGirar);
			//2. validamos si existe registro en requisito credito
			if (lstRequisitoLiquidacionComp!=null && !lstRequisitoLiquidacionComp.isEmpty()) {
				Integer requisitoSize = lstRequisitoLiquidacionComp.size();
				
				for (RequisitoLiquidacionComp o : lstRequisitoLiquidacionComp) {
					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
					lstRequisitoLiquidacion.addAll(liquidacionFacade.getListaPorPkExpedienteLiquidacionYRequisitoDetalle(expedienteLiquidacionGirar.getId(), reqAutDetalleId, beneficiarioSeleccionado.getIntPersPersonaBeneficiario()));
				}
				if (lstRequisitoLiquidacion!=null && !lstRequisitoLiquidacion.isEmpty()) {
					if (requisitoSize.equals(lstRequisitoLiquidacion.size())) {
						exito = Boolean.FALSE;	
						deshabilitarNuevo = Boolean.TRUE;
					}
				}
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}

		return exito;
	}
	
	public void aceptarAdjuntarGiroLiquidacion(){
		try{
			FileUploadControllerServicio fileUploadControllerServicio = (FileUploadControllerServicio)getSessionBean("fileUploadControllerServicio");
			archivoAdjuntoGiro = fileUploadControllerServicio.getArchivoGiroLiquidacion();
			log.info("Nombre del Archivo recuperado: "+archivoAdjuntoGiro.getStrNombrearchivo());
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}	
	
	public void quitarAdjuntoGiro(){
		try{
			archivoAdjuntoGiro = null;
			((FileUploadControllerServicio)getSessionBean("fileUploadControllerServicio")).setArchivoGiroLiquidacion(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * JCHAVEZ 08.02.2014
	 * Si el total solicitado es mayor al saldo disponible en el fondo, ya no se debe mostrar un mensaje de error, 
	 * se subirá un archivo, el cual sera usado como requisito para que la Sede Central realice el giro. 
	 */
	public void grabarRequisito(){
		log.info("--grabarRequisito()");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			//Se quita la validacion de cierre de caja para adjunto de giro
//			// 1. Valida Cierre Diario Arqueo, si existe un cierre diario para la sucursal que hace el giro, se procede a la siguiente validación
//			if(cierreDiarioArqueoFacade.existeCierreDiarioArqueo(controlFondosFijosGirar.getId().getIntPersEmpresa(), controlFondosFijosGirar.getId().getIntSucuIdSucursal(), null)){
//				// 1.1. Valida si la caja asociada a la sucursal se encuentra cerrada el dia anterior
//				if(!cierreDiarioArqueoFacade.existeCierreDiaAnterior(controlFondosFijosGirar)){
//					mensaje = "La caja de la sucursal no se ha cerrado el dia anterior.";
//					return;
//				}
//				// 1.2. Valida si la caja asociada a la sucursal se encuentra cerrada a la fecha actual
//				if(cierreDiarioArqueoFacade.existeCierreDiaActual(controlFondosFijosGirar)){
//					mensaje = "La caja de la sucursal ya se ha cerrado para el dia de hoy.";
//					return;
//				}
//			}	
			
			// 2. Valida adjunto Giro Prevision
			if(archivoAdjuntoGiro == null){
				mensaje = "Debe asignar un Archivo Adjunto para el giro.";
				return;
			}
			/*
			 * Vamos a la tabla CSE_REQAUT,para obtener el requisito luego vamos a la tabla CSE_REQAUTESTDETALLE 
			 * y filtramos todos los que su Tipo de Descripcion pertenescan al Grupo C "GIRO", asi se 
			 * obtendrán todos los requisitos necesarios para realizar el giro.
			 */
			
			RequisitoLiquidacion requisitoLiquidacion = new RequisitoLiquidacion();
			requisitoLiquidacion.setId(new RequisitoLiquidacionId());
			
			//Seteando valores...
			//Llave del expediente crédito
			requisitoLiquidacion.getId().setIntPersEmpresaLiquidacion(expedienteLiquidacionGirar.getId().getIntPersEmpresaPk());
			requisitoLiquidacion.getId().setIntItemExpediente(expedienteLiquidacionGirar.getId().getIntItemExpediente());
			//Llave del requisito
			requisitoLiquidacion.setIntPersEmpresaPk(lstRequisitoLiquidacionComp.get(0).getIntEmpresa());
			requisitoLiquidacion.setIntItemReqAut(lstRequisitoLiquidacionComp.get(0).getIntItemRequisito());
			requisitoLiquidacion.setIntItemReqAutEstDetalle(lstRequisitoLiquidacionComp.get(0).getIntItemRequisitoDetalle());
			//Archivo cargado
			requisitoLiquidacion.setIntParaTipoArchivo(archivoAdjuntoGiro.getId().getIntParaTipoCod());
			requisitoLiquidacion.setIntParaItemArchivo(archivoAdjuntoGiro.getId().getIntItemArchivo());
			requisitoLiquidacion.setIntParaItemHistorico(archivoAdjuntoGiro.getId().getIntItemHistorico());
			//Del registro
			requisitoLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			requisitoLiquidacion.setTsFechaRequisito(new Timestamp(new Date().getTime()));
			//Se agrega codigo de la persona beneficiaria... jchavez 08.05.2014
			requisitoLiquidacion.setIntPersonaBeneficiarioId(beneficiarioSeleccionado.getIntPersPersonaBeneficiario());
			
			requisitoLiquidacion = liquidacionFacade.grabarRequisito(requisitoLiquidacion);
			
			exito = Boolean.TRUE;			
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabarRequisito = Boolean.FALSE;
			deshabilitarNuevoBeneficiario = true;
			mensaje = "Se grabó el requisito satisfactoriamente.";
			
			buscar();
		}catch (Exception e){
			mensaje = "Error durante proceso de grabación de requisito: "+e.getMessage().toString();
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
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
			//fileUploadController.setArchivoCartaPoder(null);
		}catch(Exception e){
			log.error(e.getMessage(),e);
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
	public String getStrDNIApoderadoFiltro() {
		return strDNIApoderadoFiltro;
	}
	public void setStrDNIApoderadoFiltro(String strDNIApoderadoFiltro) {
		this.strDNIApoderadoFiltro = strDNIApoderadoFiltro;
	}
	public String getStrGlosaEgreso() {
		return strGlosaEgreso;
	}
	public void setStrGlosaEgreso(String strGlosaEgreso) {
		this.strGlosaEgreso = strGlosaEgreso;
	}
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
	public Persona getPersonaGirar() {
		return personaGirar;
	}
	public void setPersonaGirar(Persona personaGirar) {
		this.personaGirar = personaGirar;
	}
	public EstadoLiquidacion getEstadoLiquidacionFiltro() {
		return estadoLiquidacionFiltro;
	}

	public void setEstadoLiquidacionFiltro(EstadoLiquidacion estadoLiquidacionFiltro) {
		this.estadoLiquidacionFiltro = estadoLiquidacionFiltro;
	}

	public Integer getIntTipoBusquedaFechaFiltro() {
		return intTipoBusquedaFechaFiltro;
	}
	public void setIntTipoBusquedaFechaFiltro(Integer intTipoBusquedaFechaFiltro) {
		this.intTipoBusquedaFechaFiltro = intTipoBusquedaFechaFiltro;
	}
//	public List getListaCuentaBancaria() {
//		return listaCuentaBancaria;
//	}
//	public void setListaCuentaBancaria(List listaCuentaBancaria) {
//		this.listaCuentaBancaria = listaCuentaBancaria;
//	}
	public Integer getIntItemCuentaBancariaSeleccionar() {
		return intItemCuentaBancariaSeleccionar;
	}
	public void setIntItemCuentaBancariaSeleccionar(Integer intItemCuentaBancariaSeleccionar) {
		this.intItemCuentaBancariaSeleccionar = intItemCuentaBancariaSeleccionar;
	}
	public ExpedienteLiquidacion getExpedienteLiquidacionGirar() {
		return expedienteLiquidacionGirar;
	}
	public void setExpedienteLiquidacionGirar(ExpedienteLiquidacion expedienteLiquidacionGirar) {
		this.expedienteLiquidacionGirar = expedienteLiquidacionGirar;
	}
	public Integer getIntIdPersonaBeneficiarioSeleccionar() {
		return intIdPersonaBeneficiarioSeleccionar;
	}
	public void setIntIdPersonaBeneficiarioSeleccionar(Integer intIdPersonaBeneficiarioSeleccionar) {
		this.intIdPersonaBeneficiarioSeleccionar = intIdPersonaBeneficiarioSeleccionar;
	}
	//JCHAVEZ 23.01.2014
	public List<ExpedienteLiquidacion> getListaExpedienteLiquidacion() {
		return listaExpedienteLiquidacion;
	}
	public void setListaExpedienteLiquidacion(
			List<ExpedienteLiquidacion> listaExpedienteLiquidacion) {
		this.listaExpedienteLiquidacion = listaExpedienteLiquidacion;
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
	public List<Tabla> getListaTablaTipoDocumento() {
		return listaTablaTipoDocumento;
	}
	public void setListaTablaTipoDocumento(List<Tabla> listaTablaTipoDocumento) {
		this.listaTablaTipoDocumento = listaTablaTipoDocumento;
	}
	//JCHAVEZ 27.01.2014
	public Persona getPersonaApoderado() {
		return personaApoderado;
	}
	public void setPersonaApoderado(Persona personaApoderado) {
		this.personaApoderado = personaApoderado;
	}
	public Archivo getArchivoCartaPoder() {
		return archivoCartaPoder;
	}
	public void setArchivoCartaPoder(Archivo archivoCartaPoder) {
		this.archivoCartaPoder = archivoCartaPoder;
	}
	//JCHAVEZ 08.02.2014
	public boolean isDeshabilitarNuevoBeneficiario() {
		return deshabilitarNuevoBeneficiario;
	}
	public void setDeshabilitarNuevoBeneficiario(
			boolean deshabilitarNuevoBeneficiario) {
		this.deshabilitarNuevoBeneficiario = deshabilitarNuevoBeneficiario;
	}
	public boolean isMostrarPanelAdjuntoGiro() {
		return mostrarPanelAdjuntoGiro;
	}
	public void setMostrarPanelAdjuntoGiro(boolean mostrarPanelAdjuntoGiro) {
		this.mostrarPanelAdjuntoGiro = mostrarPanelAdjuntoGiro;
	}
	public Archivo getArchivoAdjuntoGiro() {
		return archivoAdjuntoGiro;
	}
	public void setArchivoAdjuntoGiro(Archivo archivoAdjuntoGiro) {
		this.archivoAdjuntoGiro = archivoAdjuntoGiro;
	}
	public Boolean getMostrarMensajeAdjuntarRequisito() {
		return mostrarMensajeAdjuntarRequisito;
	}
	public void setMostrarMensajeAdjuntarRequisito(
			Boolean mostrarMensajeAdjuntarRequisito) {
		this.mostrarMensajeAdjuntarRequisito = mostrarMensajeAdjuntarRequisito;
	}
	public boolean isHabilitarGrabarRequisito() {
		return habilitarGrabarRequisito;
	}
	public void setHabilitarGrabarRequisito(boolean habilitarGrabarRequisito) {
		this.habilitarGrabarRequisito = habilitarGrabarRequisito;
	}
	public List<RequisitoLiquidacion> getLstRequisitoLiquidacion() {
		return lstRequisitoLiquidacion;
	}
	public void setLstRequisitoLiquidacion(
			List<RequisitoLiquidacion> lstRequisitoLiquidacion) {
		this.lstRequisitoLiquidacion = lstRequisitoLiquidacion;
	}
	public List<RequisitoLiquidacionComp> getLstRequisitoLiquidacionComp() {
		return lstRequisitoLiquidacionComp;
	}
	public void setLstRequisitoLiquidacionComp(
			List<RequisitoLiquidacionComp> lstRequisitoLiquidacionComp) {
		this.lstRequisitoLiquidacionComp = lstRequisitoLiquidacionComp;
	}
	public String getMensajeAdjuntarRequisito() {
		return mensajeAdjuntarRequisito;
	}
	public void setMensajeAdjuntarRequisito(String mensajeAdjuntarRequisito) {
		this.mensajeAdjuntarRequisito = mensajeAdjuntarRequisito;
	}
	public Boolean getVisualizarGrabarRequisito() {
		return visualizarGrabarRequisito;
	}
	public void setVisualizarGrabarRequisito(Boolean visualizarGrabarRequisito) {
		this.visualizarGrabarRequisito = visualizarGrabarRequisito;
	}
	public Boolean getVisualizarGrabarAdjunto() {
		return visualizarGrabarAdjunto;
	}
	public void setVisualizarGrabarAdjunto(Boolean visualizarGrabarAdjunto) {
		this.visualizarGrabarAdjunto = visualizarGrabarAdjunto;
	}
	public Boolean getDeshabilitarDescargaAdjuntoGiro() {
		return deshabilitarDescargaAdjuntoGiro;
	}
	public void setDeshabilitarDescargaAdjuntoGiro(
			Boolean deshabilitarDescargaAdjuntoGiro) {
		this.deshabilitarDescargaAdjuntoGiro = deshabilitarDescargaAdjuntoGiro;
	}
}