
package pe.com.tumi.cobranza.planilla.efectuado.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import pe.com.tumi.cobranza.planilla.bean.EnvioMsg;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoResumenBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioconceptoBO;
import pe.com.tumi.cobranza.planilla.bo.EnviomontoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioresumenBO;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.EnvioconceptoId;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.EnviomontoId;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;
import pe.com.tumi.cobranza.planilla.domain.EnvioresumenId;
import pe.com.tumi.cobranza.planilla.domain.composite.EfectuadoConceptoComp;
import pe.com.tumi.cobranza.planilla.domain.composite.EnvioConceptoComp;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeLocal;
import pe.com.tumi.cobranza.planilla.validador.EnvioValidador;
import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;
import pe.com.tumi.cobranza.prioridad.facade.PrioridadDescuentoFacadeRemote;
import pe.com.tumi.common.FileUtil;
import pe.com.tumi.common.PermisoUtil;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.controller.UtilCobranza;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadronId;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.Terceros;
import pe.com.tumi.credito.socio.estructura.domain.TercerosId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionado;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionadoId;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.DocumentoPK;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;


public class EfectuadoController extends GenericController{
	
	protected static Logger log = Logger.getLogger(EfectuadoController.class);
	
	private Usuario usuario;
	private Integer PERSONA_USUARIO;
	private Integer EMPRESA_USUARIO;
	private boolean poseePermiso;
	private EfectuadoConceptoComp dtoFiltroDeEfectuado2;
	private List<Tabla> listaTablaDeSucursal;
	private List<EfectuadoResumen> listaBusquedaEfectuadoResumen;
	private List<Tabla> listaModalidadPlanilla;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private String 		mensajeOperacion;
	private boolean btnGrabarDisabled;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;	
	private boolean habilitarGrabar;
	private boolean datosValidados;
	private EfectuadoConceptoComp dtoFiltroDeEfectuado;
	private List<Tabla> listaModalidadPlanillaTemporal;
	private EnvioConceptoComp dtoDeEfectuado;
	private EnvioMsg msgMantDeEfectuado;
	private List<Juridica> listaJuridicaSucursalDePPEjecutoraEfectuado;
	private List<Estructura> listaBusquedaDePPEjecutoraEfectuado;
	private EnvioConceptoComp dtoFiltroDePPEjecutoraEfectuado;
	private Integer intTipoBusquedaDePPEjecutoraEfectuado;
	
	private Integer intEfectuado;
	private List<EfectuadoResumen> planillaEfectuadaResumen;
	private List<Efectuado> planillaEfectuada;
	List<EfectuadoConceptoComp> listaEfectuadoConceptoComp;
	private	String strTipoMantValidarDeEfectuado;
	private Integer intEfectuado2;
	
	private String strDNI;
	private Integer intNombreoDNI;
	private Boolean blnFuera;
	private String strNombre;
	private EfectuadoConceptoComp efectuadoAgregarSocio;
	List<EfectuadoConceptoComp> listaEfectuadoConceptoCompTemp;
	
	private Boolean blnEstaEnSocio;
	private BigDecimal bdMontoNoSocio;
	private Boolean blnAgregarNosocio;
	private Boolean blnMontoNoSocio;
	private List<Persona> listandoPersona;
	private Efectuado efectuadoNoSocio;
	private Boolean  blnPopupModalidadDistinta;
	private Juridica juridicaUE;
	private Boolean blnDependenciaOrigen;
	private Juridica juridicaSuc;
	private Boolean blnMontoMayorAE;
	private Boolean blnTieneEnEDAE;
	private Boolean blnNoTienePlanillaAE;
	private Boolean blnModalidadAE;
	private Boolean blnEnOtrUE;
	private Integer intModalidadDistinta;
	
	private EfectuadoConceptoComp seleccionado;
	private List<Juridica> listaJuridicaSucursalDePPEjecutoraEnvio;
	private List<Estructura> listaBusquedaDePPEjecutoraEnvio;
	private List<Tabla> listaModalidadTipoPlanilla;
	private Boolean blnActivarSegundaModalidad;
	private EfectuadoConceptoComp registroSeleccionadoEnAE;
	private Boolean blnAgregarPlanilla;
	private Boolean blnCuentaPorPagar;
	private List<Envioconcepto> listaTemporal = new ArrayList<Envioconcepto>();
	private List<Expediente> listaExpediente = new ArrayList<Expediente>();
	private BigDecimal bdCuentaPorPagar;
	private BigDecimal bdAporte;
	private BigDecimal bdSepelio;
	private BigDecimal bdMant;
	private BigDecimal bdFdoRetiro;
	private BigDecimal bdAmortizacion;
	private BigDecimal bdInteres;
	
	private Boolean blnGrabarNuevaPersona;
	private Boolean blnNoEstaEnSocio;
	private Boolean blnEstaEnSocioEstructura;
	private Boolean blnNoEstaEnSocioEstructura;
	private Socio socioSelecionado;
	private Boolean blnNewPersona;
	private Documento documentoSeleccionado;
	private Persona registroPersona;
	
	private boolean mostrarPanelInferiorConArchivo;
	private boolean datosValidadosConArchivo;
	private boolean datosValidados2ConArchivo;
	
	private String selAdjDocumento; 
	private Archivo		archivoDocSolicitud;
	private Archivo		archivoDocSolicitud1;
	private Archivo		archivoDocSolicitud2;	
	private String target;
	private TipoArchivo tipoArchivo;
	private Archivo archivo;
	private static UploadItem uploadItem = null;
	private EnvioConceptoComp dtoDeEfectuadoConArchivo;
	private Boolean examinar;
	private String strExtensionConArchivo;
	private String strTipoSocioConArchivo;
	//para con archivo txt
	private Estructura estructura; 
	private Integer intPeriodoConArchivoTXT;	
	private List<EnvioMsg> listaMensajes;
	private Boolean blnPerteneceAlaPlanilla;
	private Juridica perJuridicaSucursal;
	private Juridica perJuridicaUnidadEjecutora;
	List<Juridica> listaSucursal;
	List<Juridica> listaUEjecutora;
	private String mensajeConArchivo;
	private Boolean blnErrorConArchivo;
	private Boolean blnErrorWithFileDeOneSucursalUE; //mas de una sucursal o mas de una unidad Ejecutora
	private List<EnvioMsg> listaMensajesSinSucursal; //son los socios que no van a encontrar su sucursal o Unidad Ejecutora en el texto.
	private List<EnvioMsg> listaMensajesConSucursaleUE; //son los socios que van a encontrar su sucursal y Unidad Ejecutora
														//pero que posbiblemente hayga mas de una sucursal o UE en todo el archivo texto.

	private List<Juridica> listaJuridicaSucursal;
	private List<Juridica> listaJuridicaUnidadEjecutora;
	private Integer intNroSociosConArchivo;
	private BigDecimal bdMontoTotalConArchivo;
	
	private Boolean blnTexto;
	private Boolean blnExcel;
	

	
	public EfectuadoController()
	{
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_EFECTUADO);
		if(usuario!= null && poseePermiso)
		{
			cargarValoresIniciales();			
		}
		else
		{
			log.error("usuario obtenido es null o no posee permiso");
		}
	}
	
	public String getLimpiarEfectuado()
	{
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_EFECTUADO);
		if(usuario != null && poseePermiso)
		{
			cargarValoresIniciales();
		}
		else
		{
			log.error("Usuario obtenido es NULL O no posee permiso");
		}
		return "";
	}
	
	public String getRenderConArchivo()
	{
		return "";
	}
	
	public void cargarValoresIniciales()
	{
		try
		{
			cargarUsuario();
			mostrarPanelInferior = Boolean.FALSE;
			mostrarPanelInferiorConArchivo  	= Boolean.FALSE;
			TablaFacadeRemote remoteTabla = null;
			EmpresaFacadeRemote remoteEmpresa;
			dtoFiltroDeEfectuado2 = null;
			dtoFiltroDeEfectuado2 = new EfectuadoConceptoComp();
			dtoFiltroDeEfectuado2.setEfectuadoConcepto(new EfectuadoConcepto());
			dtoFiltroDeEfectuado2.setEstructuraDetalle(new EstructuraDetalle());
			dtoFiltroDeEfectuado2.setEstructura(new Estructura());
			dtoFiltroDeEfectuado2.getEstructura().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
			dtoFiltroDeEfectuado2.getEstructura().setJuridica(new Juridica());
			dtoFiltroDeEfectuado = null;
			dtoFiltroDeEfectuado = new EfectuadoConceptoComp();
			dtoFiltroDeEfectuado.setEfectuadoConcepto(new EfectuadoConcepto());
			dtoFiltroDeEfectuado.setEstructuraDetalle(new EstructuraDetalle());
			dtoFiltroDeEfectuado.setEstructura(new Estructura());
			dtoFiltroDeEfectuado.getEstructura().setIntPersEmpresaPk(EMPRESA_USUARIO);
			dtoFiltroDeEfectuado.getEstructura().setJuridica(new Juridica());
			
			archivo = new Archivo();
			archivo.setId(new ArchivoId());
			
			if(listaBusquedaEfectuadoResumen !=null && !listaBusquedaEfectuadoResumen.isEmpty())
			{
				getListaBusquedaEfectuadoResumen().clear();
			}
			
			if(listaEfectuadoConceptoComp != null && !listaEfectuadoConceptoComp.isEmpty())
			{
				getListaEfectuadoConceptoComp().clear();
			}
			
			
			dtoDeEfectuado = new EnvioConceptoComp();
			dtoDeEfectuado.setEstructura(new Estructura());
			dtoDeEfectuado.getEstructura().setJuridica(new Juridica());
			msgMantDeEfectuado = new EnvioMsg();
			remoteTabla = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			remoteEmpresa = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);				
			listaTablaDeSucursal 		= remoteTabla.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
			listaModalidadPlanilla      = remoteTabla.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_MODALIDADPLANILLA));
			
			List<Sucursal> listaSucursal = remoteEmpresa.getListaSucursalPorPkEmpresa(usuario.getPerfil().getId().getIntPersEmpresaPk());
			//Ordenamos por nombre
			Collections.sort(listaSucursal, new Comparator<Sucursal>(){
				public int compare(Sucursal sucUno, Sucursal sucDos) {
					return sucUno.getJuridica().getStrSiglas().compareTo(sucDos.getJuridica().getStrSiglas());
				}
			});
			Sucursal sucursal = null;
			Tabla tabla = null;
			for(int i=0;i<listaSucursal.size();i++){
				 sucursal = listaSucursal.get(i);
				 tabla = new Tabla();
				 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
				 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
				 listaTablaDeSucursal.add(tabla);
			}
			ocultarMensaje();
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public void reloadCboTipoPlanilla(ValueChangeEvent event) throws EJBFactoryException, BusinessException
	{
		Tabla lmodalidadH 	= null;
		Tabla lmodalidadI 	= null;
		Tabla lmodalidadC 	= null;	
		Tabla lmodalidad 	= null;
		
		Integer intTipoSocio = Integer.parseInt(""+ event.getNewValue());
		log.debug("intTipoSocio="+intTipoSocio);
		if(listaModalidadPlanilla != null && !listaModalidadPlanilla.isEmpty())
		{
			for(int k = 0; k < listaModalidadPlanilla.size(); k++)
			{
				lmodalidad = listaModalidadPlanilla.get(k);
				if(lmodalidad.getStrDescripcion().compareTo("Haberes")==0)
				{
					lmodalidadH = lmodalidad;
				}
				if(lmodalidad.getStrDescripcion().compareTo("Incentivos")==0)
				{
					lmodalidadI = lmodalidad;
				}
				if(lmodalidad.getStrDescripcion().compareTo("CAS")==0)
				{
					lmodalidadC = lmodalidad;
				}
				
			}
			listaModalidadPlanillaTemporal = new ArrayList<Tabla>();
			if(Constante.PARAM_T_TIPOSOCIO_ACTIVO.compareTo(intTipoSocio) == 0)
			{
				listaModalidadPlanillaTemporal.add(lmodalidadH);
				listaModalidadPlanillaTemporal.add(lmodalidadI);
				listaModalidadPlanillaTemporal.add(lmodalidadC);
			}
			else if(Constante.PARAM_T_TIPOSOCIO_CESANTE.compareTo(intTipoSocio) == 0)
			{
				listaModalidadPlanillaTemporal.add(lmodalidadH);
				
			}
			ocultarMensaje();
		}	
	}
	
	public void limpiandoDeEfectuado()
	{
		if(listaBusquedaEfectuadoResumen != null && !listaBusquedaEfectuadoResumen.isEmpty())
		{
			listaBusquedaEfectuadoResumen.clear();
		}
		dtoFiltroDeEfectuado2 = null;
		dtoFiltroDeEfectuado2 = new EfectuadoConceptoComp();
		dtoFiltroDeEfectuado2.setEfectuadoConcepto(new EfectuadoConcepto());
		dtoFiltroDeEfectuado2.setEstructuraDetalle(new EstructuraDetalle());
		dtoFiltroDeEfectuado2.setEstructura(new Estructura());
		dtoFiltroDeEfectuado2.getEstructura().setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
		dtoFiltroDeEfectuado2.getEstructura().setJuridica(new Juridica());
	}
	
	public void buscarDeEfectuado (){
		log.debug("planillaController.buscarDeEfectuado() INICIO");		
		listaBusquedaEfectuadoResumen = null;
		try{
			PlanillaFacadeLocal planillaLocal = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			cargarUsuario();
			dtoFiltroDeEfectuado2.getEstructura().setIntPersEmpresaPk(EMPRESA_USUARIO);
			//Unidad Ejecutora
			if(dtoFiltroDeEfectuado2.getEstructura().getJuridica().getStrRazonSocial() != null 
			   && !dtoFiltroDeEfectuado2.getEstructura().getJuridica().getStrRazonSocial().isEmpty())
			{
				dtoFiltroDeEfectuado2.getEstructura().getJuridica().getStrRazonSocial().trim();
			}
			//sucursal
			if(dtoFiltroDeEfectuado2.getEstructuraDetalle().getIntSeguSucursalPk().equals(Constante.PARAM_T_SELECCIONAR))
				dtoFiltroDeEfectuado2.getEstructuraDetalle().setIntSeguSucursalPk(null);
			//tiposocio
			if(dtoFiltroDeEfectuado2.getEstructuraDetalle().getIntParaTipoSocioCod().equals(Constante.PARAM_T_SELECCIONAR)
				|| dtoFiltroDeEfectuado2.getEstructuraDetalle().getIntParaTipoSocioCod().equals(Constante.PARAM_T_TODOS)	)
				dtoFiltroDeEfectuado2.getEstructuraDetalle().setIntParaTipoSocioCod(null);
			//tipodeplanilla
			if(dtoFiltroDeEfectuado2.getIntParaModalidadPlanilla().equals(Constante.PARAM_T_SELECCIONAR)
			   || dtoFiltroDeEfectuado2.getIntParaModalidadPlanilla().equals(Constante.PARAM_T_TODOS))
				dtoFiltroDeEfectuado2.setIntParaModalidadPlanilla(null);
			//estado
			if(dtoFiltroDeEfectuado2.getEfectuadoConcepto().getIntEstadoCod().equals(Constante.PARAM_T_SELECCIONAR)
				|| dtoFiltroDeEfectuado2.getEfectuadoConcepto().getIntEstadoCod().equals(Constante.PARAM_T_TODOS))
				dtoFiltroDeEfectuado2.getEfectuadoConcepto().setIntEstadoCod(null);
			///periodo
			if(dtoFiltroDeEfectuado2.getIntPeriodoAnio().equals(Constante.PARAM_T_SELECCIONAR))
			{
				dtoFiltroDeEfectuado2.setIntPeriodoAnio(null);
				dtoFiltroDeEfectuado2.setIntPeriodoMes(null);
			}
			else
			{
				if(dtoFiltroDeEfectuado2.getIntPeriodoMes().intValue()<10)
					dtoFiltroDeEfectuado2.setIntPeriodo(Integer.parseInt(dtoFiltroDeEfectuado2.getIntPeriodoAnio()+
										"0" +dtoFiltroDeEfectuado2.getIntPeriodoMes()));
				else
					dtoFiltroDeEfectuado2.setIntPeriodo(Integer.parseInt(dtoFiltroDeEfectuado2.getIntPeriodoAnio()+
										""+dtoFiltroDeEfectuado2.getIntPeriodoMes()));
			}
			
			listaBusquedaEfectuadoResumen = planillaLocal.getListaEfectuadoResumenBuscar(dtoFiltroDeEfectuado2);
			log.info("cant de listaefectuadoResumen="+listaBusquedaEfectuadoResumen.size());
			
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	
	private void cargarUsuario()
	{
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		PERSONA_USUARIO =usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
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
	
	public void habilitarPanelInferiorConArchivo()
	{
		try
		{
			cargarUsuario();
			habilitarGrabar = Boolean.TRUE;
			datosValidadosConArchivo = Boolean.FALSE;
			examinar 				 = Boolean.TRUE;
			datosValidados2ConArchivo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.FALSE;
			datosValidados = Boolean.TRUE;
			mostrarPanelInferiorConArchivo = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			dtoDeEfectuadoConArchivo = null;
			dtoDeEfectuadoConArchivo = new EnvioConceptoComp();
			dtoDeEfectuadoConArchivo.setEstructura(new Estructura());
			dtoDeEfectuadoConArchivo.getEstructura().setJuridica(new Juridica());
			dtoDeEfectuadoConArchivo.setJuridicaSucursal(new Juridica());
			archivo = new Archivo();
			archivo.setId(new ArchivoId());
			if(listaMensajes != null && !listaMensajes.isEmpty())
			{
				listaMensajes.clear();
			}
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	public void habilitarPanelInferior()
	{
		try{
			cargarUsuario();
			datosValidados = Boolean.FALSE;			
			datosValidadosConArchivo = Boolean.TRUE;
			datosValidados2ConArchivo = Boolean.FALSE;
			mostrarPanelInferiorConArchivo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			dtoFiltroDeEfectuado = new EfectuadoConceptoComp();
			dtoFiltroDeEfectuado.setEfectuadoConcepto(new EfectuadoConcepto());
			dtoFiltroDeEfectuado.setEstructuraDetalle(new EstructuraDetalle());
			dtoFiltroDeEfectuado.getEstructuraDetalle().setIntParaTipoSocioCod(Constante.OPCION_SELECCIONAR);
			dtoFiltroDeEfectuado.setIntParaModalidadPlanilla(Constante.OPCION_SELECCIONAR);
			dtoFiltroDeEfectuado.setEstructura(new Estructura());
			dtoFiltroDeEfectuado.getEstructura().setIntPersEmpresaPk(EMPRESA_USUARIO);
			dtoFiltroDeEfectuado.getEstructura().setJuridica(new Juridica());
			dtoDeEfectuado = null;
			dtoDeEfectuado = new EnvioConceptoComp();
			dtoDeEfectuado.setEstructura(new Estructura());
			dtoDeEfectuado.getEstructura().setJuridica(new Juridica());			
			dtoDeEfectuado.setJuridicaSucursal(new Juridica());
			
			habilitarGrabar = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
		
	public void abrirPopupBuscarUnidadEjecutora()
	{
		cargarUsuario();
		Integer inttipoSocio = null;
		Integer intmodalidad = null;
		ocultarMensaje();
		List<Envioresumen> listaE = null;
		listaBusquedaDePPEjecutoraEfectuado = null;
		listaJuridicaSucursalDePPEjecutoraEfectuado = new ArrayList<Juridica>();
		try
		{			
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			EnvioresumenBO boEnvioresumen 	  = (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			inttipoSocio = dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod();
			intmodalidad = dtoFiltroDeEfectuado.getIntParaModalidadPlanilla();
			
			listaE = boEnvioresumen.getListaSucursal(EMPRESA_USUARIO, inttipoSocio,intmodalidad);				
			if(listaE != null && !listaE.isEmpty())
			{
				for(Envioresumen envioresumen: listaE)
				{
					log.info("sucursal="+envioresumen.getIntIdsucursalprocesaPk());
					if(envioresumen.getIntIdsucursalprocesaPk()!= null)
					{
						Sucursal sucursal = new Sucursal();
						sucursal.getId().setIntIdSucursal(envioresumen.getIntIdsucursalprocesaPk());	
						sucursal = empresaFacade.getSucursalPorPK(sucursal);
						Juridica juridicaT = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
						listaJuridicaSucursalDePPEjecutoraEfectuado.add(juridicaT);	
					}					
				}
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public void buscarUnidadEjecutora()
	{
		log.debug("buscarUnidadEjecutora INICIO v1");
		cargarUsuario();		
		Usuario usuario = null;		 
		List<Estructura> listaEstructuraTemporal = new ArrayList<Estructura>();		 
		Integer intTipoSocio = null;
		Integer intModalidad = null;
		Integer intSucursalProcesa = null;
		Integer intEmpresa= null;
		List<Envioresumen> listaResumenEnvio = null;		
		Sucursal sucursalTem = null;
		Juridica juridicaTem = null;
		Integer intEstado = null;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!= null){				
				EmpresaFacadeRemote remoteEmpresa = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				EnvioresumenBO boEnvioresumen 			= (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);				
				EstructuraFacadeRemote estructuraFacadeRemote = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
				PersonaFacadeRemote  personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			
				intTipoBusquedaDePPEjecutoraEfectuado =0;			
				//hasta aca debo saber que empresa, q tipo de socio, que modalidad que sucursalprocesa tengo
				intEmpresa = EMPRESA_USUARIO;
				intTipoSocio = dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod();
				intModalidad = dtoFiltroDeEfectuado.getIntParaModalidadPlanilla();
				intSucursalProcesa = dtoFiltroDeEfectuado.getEstructuraDetalle().getIntSeguSucursalPk();
				intEstado = dtoFiltroDeEfectuado.getEfectuadoConcepto().getIntEstadoCod();
				juridicaTem = personaFacade.getJuridicaPorPK(intSucursalProcesa);
				if(juridicaTem != null)
				{
					dtoDeEfectuado.setJuridicaSucursal(juridicaTem);
				}				
				sucursalTem = remoteEmpresa.getSucursalPorIdPersona(intSucursalProcesa);
				if(sucursalTem != null)
				{
					intSucursalProcesa = sucursalTem.getId().getIntIdSucursal();
					dtoDeEfectuado.setJuridicaSucursal(juridicaTem);
					log.info("nuevo intSucursalProcesa="+intSucursalProcesa);
				}
				
				log.info("intEmpresa= "+intEmpresa+", intTipoSocio= "+intTipoSocio+",intModalidad= "+intModalidad+", intSucursalProcesa= "+intSucursalProcesa);
				
				if(intTipoBusquedaDePPEjecutoraEfectuado == 0){
					//listo buscando maximos periodos con tiposocio, modalidad, sucursal seleccionada
					listaResumenEnvio = boEnvioresumen.getListaEnvioResumenUE(intEmpresa, intTipoSocio, intModalidad, intSucursalProcesa);
					log.info("cant= "+listaResumenEnvio.size());
					if(listaResumenEnvio != null && !listaResumenEnvio.isEmpty())
					{
						for(Envioresumen envioresumen: listaResumenEnvio)
						{	//comparo uno a uno: si encuentro uno es que ya existe un efectuado pero si no entonces me falta						
																						
									//obtengo la unidad ejecutora
									log.info("añadiendo la unidad ejecutora");
									EstructuraId id = new EstructuraId();
									id.setIntCodigo(envioresumen.getIntCodigo());
									id.setIntNivel(envioresumen.getIntNivel());
									Estructura estructuraT = estructuraFacadeRemote.getEstructuraPorPk(id);
									if(estructuraT != null)
									{
										Juridica juridi= personaFacade.getJuridicaPorPK(estructuraT.getIntPersPersonaPk());
										estructuraT.setJuridica(juridi);
										estructuraT.setIntPeriodoAnio(Integer.parseInt(envioresumen.getIntPeriodoplanilla().toString().substring(0,4)));
										estructuraT.setIntPeriodoMes(Integer.parseInt(envioresumen.getIntPeriodoplanilla().toString().substring(4,envioresumen.getIntPeriodoplanilla().toString().length())));		
										listaEstructuraTemporal.add(estructuraT);
										
										if(intTipoSocio.compareTo(1) == 0)
										{
											dtoDeEfectuado.setStrTipoSocio("Activo");
										}
										else
										{
											dtoDeEfectuado.setStrTipoSocio("Cesante");
										}
										
										if(intModalidad.compareTo(1) == 0)
										{
											dtoDeEfectuado.setStrModalidad("Haberes");
										}else if(intModalidad.compareTo(2) == 0)
										{
											dtoDeEfectuado.setStrModalidad("Incentivo");
										}
										else  
										{
											dtoDeEfectuado.setStrModalidad("Cas");
										}									
										
										log.info("anio="+dtoDeEfectuado.getIntPeriodoAnio()+",mes= "+dtoDeEfectuado.getIntPeriodoMes());
									}
																
						}
					}	
				}				
			}

			listaBusquedaDePPEjecutoraEfectuado = listaEstructuraTemporal;
			//ordenando lista
			Collections.sort(listaBusquedaDePPEjecutoraEfectuado, new Comparator<Estructura>()
					{
					public int compare(Estructura estUno, Estructura estDos)
					{
						return estUno.getJuridica().getStrRazonSocial().compareTo(estDos.getJuridica().getStrRazonSocial());
					}
					});
			log.debug("listaBusquedaDePPEjecutoraEfectuado"+listaBusquedaDePPEjecutoraEfectuado.size());
			log.debug("buscarUnidadEjecutora FIN v1");
		} catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public void seleccionarUnidadEjecutora(ActionEvent event)
	{
		Estructura unidadEjecutoraSeleccionada = (Estructura)event.getComponent().getAttributes().get("item");
		try
		{
			PersonaFacadeRemote	remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			Juridica juridica = remotePersona.getJuridicaPorPK(dtoFiltroDeEfectuado.getEstructuraDetalle().getIntSeguSucursalPk());
			dtoDeEfectuado.setJuridicaSucursal(juridica);
			dtoDeEfectuado.setEstructura(unidadEjecutoraSeleccionada);
			dtoDeEfectuado.getEstructura().setJuridica(unidadEjecutoraSeleccionada.getJuridica());
			
			dtoDeEfectuado.setIntPeriodoMes(unidadEjecutoraSeleccionada.getIntPeriodoMes());
			dtoDeEfectuado.setIntPeriodoAnio(unidadEjecutoraSeleccionada.getIntPeriodoAnio());
			
			log.debug("Sucursal: "+dtoDeEfectuado.getJuridicaSucursal().getStrRazonSocial());
			log.debug("Unidad Ejecutora: "+dtoDeEfectuado.getEstructura().getJuridica().getStrRazonSocial());
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public void validarDatos()
	{
		log.debug("validarDatos INICIO");
		try
		{			 
			datosValidados = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;		
			ocultarMensaje();
						
			log.debug("validarDatos FIN");
		}
		catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la validación.");
			log.error(e.getMessage(),e);
		}		
	}
	
	public void irValidarDeEfectuado(ActionEvent event)
	{
		log.info("irValidarDeEfectuado INICIO");
		cargarUsuario();		
		Integer intMaxEnviado = null;
		EfectuadoConceptoComp efeConcepto = null;
		List<EfectuadoResumen> listaEfectuadoResumen = null;
		EstructuraId estructuraId                    = null;
		Integer intTipoSocio = dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod();
		Integer pIntModalidadCod = dtoFiltroDeEfectuado.getIntParaModalidadPlanilla();
		log.debug("tipoSocio: "+intTipoSocio+", modalidad: "+pIntModalidadCod);
		
		intEfectuado = Constante.intCesanteCAS;
		planillaEfectuadaResumen = null;
		List<EfectuadoResumen> planillaEfectuadaResumenTemp = null;
		
		planillaEfectuadaResumen = new ArrayList<EfectuadoResumen>();
		planillaEfectuada = null;
		planillaEfectuada = new ArrayList<Efectuado>();		
		listaEfectuadoConceptoComp =null;
		List<Envioresumen> listaEnvioresumen = null;
		listaEfectuadoConceptoComp = new ArrayList<EfectuadoConceptoComp>();
		
		try{
				PlanillaFacadeLocal localPlanilla = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);			
				EfectuadoResumenBO boEfectuadoResumen 	= (EfectuadoResumenBO)TumiFactory.get(EfectuadoResumenBO.class);
				EnvioresumenBO boEnvioresumen 	= (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
				
				Enviomonto eo = new Enviomonto();
				eo.setId(new EnviomontoId());
				eo.getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
				eo.setIntNivel(dtoDeEfectuado.getEstructura().getId().getIntNivel());
				eo.setIntCodigo(dtoDeEfectuado.getEstructura().getId().getIntCodigo());
				eo.setIntTiposocioCod(intTipoSocio);
				eo.setIntModalidadCod(pIntModalidadCod);

				Envioresumen er = new Envioresumen();
				er.setId(new EnvioresumenId());
				er.getId().setIntEmpresaPk(EMPRESA_USUARIO);
				
				estructuraId = new EstructuraId();
				estructuraId.setIntNivel(dtoDeEfectuado.getEstructura().getId().getIntNivel());
				estructuraId.setIntCodigo(dtoDeEfectuado.getEstructura().getId().getIntCodigo());
				
				er.setIntTiposocioCod(intTipoSocio);
				er.setIntModalidadCod(pIntModalidadCod);
				
				if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(pIntModalidadCod) == 0
					|| Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(pIntModalidadCod) == 0)
				{
					intMaxEnviado = localPlanilla.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioM(EMPRESA_USUARIO,
																										estructuraId,
																										intTipoSocio,
																										pIntModalidadCod);
				}
				else if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(pIntModalidadCod) == 0)
				{
					intMaxEnviado = localPlanilla.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioCAS(new Integer(EMPRESA_USUARIO),
																										estructuraId,
																										new Integer(Constante.PARAM_T_TIPOSOCIO_ACTIVO));
				}
								
				if(intMaxEnviado != null)
				{
					log.debug("maxperiodoenviado="+intMaxEnviado);
					listaEfectuadoResumen =	boEfectuadoResumen.getListaPorEntidadyPeriodo(EMPRESA_USUARIO,
																							intMaxEnviado, 
																			 				intTipoSocio,
																			 				pIntModalidadCod,
																			 				estructuraId.getIntNivel(),
																			 				estructuraId.getIntCodigo());
					
					if(listaEfectuadoResumen != null && !listaEfectuadoResumen.isEmpty())
					{
												
						mostrarMensaje(Boolean.FALSE, " No se ha realizado un enviado aun.");
						return;
					}
					else
					{						
						listaEnvioresumen = boEnvioresumen.getListaEnvioREfectuadoConArchivo(eo.getId().getIntEmpresacuentaPk(),
																							 eo.getIntNivel(),
																							 eo.getIntCodigo(),
																							 eo.getIntTiposocioCod(),
																					         eo.getIntModalidadCod());
						if(listaEnvioresumen != null && !listaEnvioresumen.isEmpty())
						{
							for(Envioresumen enr: listaEnvioresumen)
							{
								eo.setIntIdsucursaladministraPk(enr.getIntIdsucursaladministraPk());
								eo.setIntIdsubsucursaladministra(enr.getIntIdsubsucursaladministra());
								planillaEfectuadaResumenTemp  = localPlanilla.getPlanillaEfectuadaResumen(eo,
																										  intMaxEnviado);
								if(planillaEfectuadaResumenTemp != null && !planillaEfectuadaResumenTemp.isEmpty())
								{
									for(EfectuadoResumen err :planillaEfectuadaResumenTemp)
									{
										planillaEfectuadaResumen.add(err);
									}
								}
							}
						}	
						
						if(planillaEfectuadaResumen != null && !planillaEfectuadaResumen.isEmpty())
						{
							for(EfectuadoResumen efectuadoResumen :planillaEfectuadaResumen)
							{
								if(efectuadoResumen.getListaEfectuado()!= null && !efectuadoResumen.getListaEfectuado().isEmpty())
								{
									for(Efectuado efe: efectuadoResumen.getListaEfectuado())
									{
										log.debug(efe);	
										efeConcepto = new EfectuadoConceptoComp();
										efeConcepto.setBlnAgregarSocio(Boolean.FALSE);
										efeConcepto.setBlnAgregarNoSocio(Boolean.FALSE);
										efeConcepto.setDocumento(efe.getDocumento());
										efeConcepto.setCuentaIntegrante(efe.getCuentaIntegrante());
										efeConcepto.setSocio(efe.getSocio());
										efeConcepto.setEfectuado(efe);										
										listaEfectuadoConceptoComp.add(efeConcepto);										
									}
								}
								
							}
							datosValidados = Boolean.TRUE;
							habilitarGrabar = Boolean.TRUE;
							deshabilitarNuevo = Boolean.FALSE;		
							ocultarMensaje();
						}						
						
						//Ordenamos por nombre
						Collections.sort(listaEfectuadoConceptoComp, new Comparator<EfectuadoConceptoComp>(){
							public int compare(EfectuadoConceptoComp uno, EfectuadoConceptoComp otro) {															
								return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
							}
						});						
						strTipoMantValidarDeEfectuado = Constante.MANTENIMIENTO_NINGUNO;						
					}				
				}else{
					mostrarMensaje(Boolean.FALSE, " No existe un enviado.");
					return;
				}
		log.debug("irValidarDeEfectuado FIN");		
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public void irValidarDeEfectuadoHaberIncentivo(ActionEvent event) throws EJBFactoryException
	{
		log.info("irValidarDeEfectuadoHaberIncentivo v1");
		cargarUsuario();
		PlanillaFacadeLocal localPlanilla = null;
		Integer intMaxEnviado = null;  //PERIODO
		List<EfectuadoResumen> listaEfectuadoResumen1 = null;
		List<EfectuadoResumen> listaEfectuadoResumen2 = null;
		EstructuraId estructuraId                    = null;
		Integer intTipoSocio = dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod();
		Integer pIntModalidadCod = dtoFiltroDeEfectuado.getIntParaModalidadPlanilla();
		log.debug("tipoSocio: "+intTipoSocio+", modalidad: "+pIntModalidadCod);
		Integer intSegundaModalidad =0;
		Integer intCantSegundaModalidad = 0;
		intEfectuado = Constante.intHaberIncentivo;
		List<Envioresumen> listaEnvioResumen = null;
		 Boolean blntieneUnaSegundaModaliad = Boolean.FALSE;
		 planillaEfectuadaResumen = null;
		 planillaEfectuadaResumen = new ArrayList<EfectuadoResumen>();
		 planillaEfectuada 		  = new ArrayList<Efectuado>();
		 listaEfectuadoConceptoComp = new ArrayList<EfectuadoConceptoComp>();
		 EfectuadoConceptoComp efeConcepto = null;
		 List<Envioresumen> listaEnvioresumen = null;
		 List<EfectuadoResumen> planillaEfectuadaResumenTemp = null;
		 
		 
		if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(pIntModalidadCod) == 0)
		{
			intSegundaModalidad =Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS;
		}
		else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(pIntModalidadCod) == 0)
		{
			intSegundaModalidad = Constante.PARAM_T_MODALIDADPLANILLA_HABERES;
		}
		
		if(dtoDeEfectuado.getStrModalidad().equals("Haberes")) 
		{
			dtoDeEfectuado.setStrModalidad2("Incentivo");
		}
		else if(dtoDeEfectuado.getStrModalidad().equals("Incentivo"))
		{
			dtoDeEfectuado.setStrModalidad2("Haberes");
		}
		
		try{
			localPlanilla 							= (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);			
			EfectuadoResumenBO boEfectuadoResumen 	= (EfectuadoResumenBO)TumiFactory.get(EfectuadoResumenBO.class);
			EnvioresumenBO boEnvioresumen         	= (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
				Enviomonto eo = new Enviomonto();
				eo.setId(new EnviomontoId());
				eo.getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
				eo.setIntNivel(dtoDeEfectuado.getEstructura().getId().getIntNivel());
				eo.setIntCodigo(dtoDeEfectuado.getEstructura().getId().getIntCodigo());
				eo.setIntTiposocioCod(intTipoSocio);
				eo.setIntModalidadCod(pIntModalidadCod);

				Envioresumen er = new Envioresumen();
				er.setId(new EnvioresumenId());
				er.getId().setIntEmpresaPk(EMPRESA_USUARIO);
				
				estructuraId = new EstructuraId();
				estructuraId.setIntNivel(dtoDeEfectuado.getEstructura().getId().getIntNivel());
				estructuraId.setIntCodigo(dtoDeEfectuado.getEstructura().getId().getIntCodigo());
				
				List<Socio> socios = localPlanilla.getListaSocio(estructuraId, intTipoSocio);	
				if(socios != null && !socios.isEmpty())
				{
					for(Socio so:socios)
					{
						for(SocioEstructura s: so.getListSocioEstructura())
						{
							if(intSegundaModalidad.compareTo(s.getIntModalidad()) == 0)
							{
								blntieneUnaSegundaModaliad = Boolean.TRUE;
								break;
							}
						}
					}
				}else
				{					
					log.debug("Lista de socios vacia.");
					mostrarMensaje(Boolean.FALSE, " Error no controlado al momento de obtener los registros de validación.");
					return;
				}
				
				er.setIntTiposocioCod(intTipoSocio);
				er.setIntModalidadCod(pIntModalidadCod);
				
				if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(pIntModalidadCod) == 0
					|| Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(pIntModalidadCod) == 0)
				{
					intMaxEnviado = localPlanilla.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioM(EMPRESA_USUARIO,
																									estructuraId,
																									intTipoSocio,
																									pIntModalidadCod);
					if(intMaxEnviado != null)
					{
						log.debug("maxperiodoenviado="+intMaxEnviado);
						
						listaEfectuadoResumen1 =	boEfectuadoResumen.getListaPorEntidadyPeriodo(EMPRESA_USUARIO,
																								 intMaxEnviado, 
								 																 intTipoSocio,
								 																 pIntModalidadCod, 
								 																 estructuraId.getIntNivel(),
								 																 estructuraId.getIntCodigo());
						
						if(listaEfectuadoResumen1 != null && !listaEfectuadoResumen1.isEmpty())
						{
							log.debug("no se realiza enviado");
							if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(pIntModalidadCod)==0)
							{
								mostrarMensaje(Boolean.FALSE, " El Periodo "+intMaxEnviado+
								  				" ya se efectuo en la modalidad haberes.");
								
							}else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(pIntModalidadCod) == 0)
							{
								mostrarMensaje(Boolean.FALSE, " El Periodo "+intMaxEnviado+
								  			 " ya se efectuo en la modalidad incentivos.");
								
							}else if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(pIntModalidadCod) == 0)
							{
								mostrarMensaje(Boolean.FALSE, " El Periodo "+intMaxEnviado+
								  					" ya se efectuo en la modalidad cas.");
							}							
							return;
						}
						else
						{							
							if(blntieneUnaSegundaModaliad)
							{
								log.debug("intCantSegundaModalidad="+intCantSegundaModalidad);
								//tiene otra modalidad
								SocioEstructura  o = new SocioEstructura();
								o.setId(new SocioEstructuraPK());
								o.getId().setIntIdEmpresa(EMPRESA_USUARIO);
								o.setIntNivel(dtoDeEfectuado.getEstructura().getId().getIntNivel());
								o.setIntCodigo(dtoDeEfectuado.getEstructura().getId().getIntCodigo());
								o.setIntEstadoCod(new Integer(Constante.PARAM_T_ESTADOUNIVERSAL));
								o.setIntTipoSocio(intTipoSocio);
								
								listaEnvioResumen =	boEnvioresumen.getListaPorEnitdadyPeriodo(EMPRESA_USUARIO,
																							intMaxEnviado,
																	                        intTipoSocio,
																	                        intSegundaModalidad,
																	                        o.getIntNivel(),
																	                        o.getIntCodigo());
								
								if(listaEnvioResumen != null && !listaEnvioResumen.isEmpty())
								{
									listaEfectuadoResumen2 =	boEfectuadoResumen.getListaPorEntidadyPeriodo(EMPRESA_USUARIO,
																											intMaxEnviado, 
							 									                                            intTipoSocio,
							 									                                            intSegundaModalidad, 
							 									                                            estructuraId.getIntNivel(),
							 									                                            estructuraId.getIntCodigo());
									
									if(listaEfectuadoResumen2 != null && !listaEfectuadoResumen2.isEmpty())
									{									
										log.debug("completar efectuado pendiente");
										completarEfectuado(eo, intMaxEnviado, intSegundaModalidad);
									}
									else
									{
										log.debug("a listar lo que he seleccionado y mostrar mi otra modalidad");
										empezandoEfectuado(eo,intMaxEnviado, intSegundaModalidad); //ENVIOMONTO PERIODO SEGUNDA MODALIDAD
									}
								}
								else
								{
									mostrarMensaje(Boolean.FALSE, " Falta completar el enviado del periodo "+intMaxEnviado+".");
									return;
								}
							}
							else
							{							
								intEfectuado = Constante.intCesanteCAS;
								
								listaEnvioresumen = boEnvioresumen.getListaEnvioREfectuadoConArchivo(eo.getId().getIntEmpresacuentaPk(),
																									 eo.getIntNivel(),
																									 eo.getIntCodigo(),
																									 eo.getIntTiposocioCod(),
																							         eo.getIntModalidadCod());
								if(listaEnvioresumen != null && !listaEnvioresumen.isEmpty())
								{
									for(Envioresumen enr: listaEnvioresumen)
									{
										eo.setIntIdsucursaladministraPk(enr.getIntIdsucursaladministraPk());
										eo.setIntIdsubsucursaladministra(enr.getIntIdsubsucursaladministra());
										planillaEfectuadaResumenTemp  = localPlanilla.getPlanillaEfectuadaResumen(eo,
																												  intMaxEnviado);
										if(planillaEfectuadaResumenTemp != null && !planillaEfectuadaResumenTemp.isEmpty())
										{
											for(EfectuadoResumen err :planillaEfectuadaResumenTemp)
											{												
												planillaEfectuada = err.getListaEfectuado();
												for(Efectuado ef: planillaEfectuada)
												{
													log.debug(ef);
													
													efeConcepto = new EfectuadoConceptoComp();
													efeConcepto.setDocumento(ef.getDocumento());
													efeConcepto.setCuentaIntegrante(ef.getCuentaIntegrante());
													efeConcepto.setSocio(ef.getSocio());
													efeConcepto.setEfectuado(ef);
													listaEfectuadoConceptoComp.add(efeConcepto);
												}
												planillaEfectuadaResumen.add(err);
												datosValidados = Boolean.TRUE;
												habilitarGrabar = Boolean.TRUE;
												deshabilitarNuevo = Boolean.FALSE;		
												ocultarMensaje();												
											}
											
										}
									}
									//Ordenamos por nombre
									Collections.sort(listaEfectuadoConceptoComp, new Comparator<EfectuadoConceptoComp>(){
										public int compare(EfectuadoConceptoComp uno, EfectuadoConceptoComp otro) {															
											return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
										}
									});							
									strTipoMantValidarDeEfectuado = Constante.MANTENIMIENTO_NINGUNO;
								}else
								{
									mostrarMensaje(Boolean.FALSE, " Error no controlado al momento de obtener los registros de validación.");
									return;
								}								 															
							}						
						}				
					}else
					{						
						log.debug("Maximo periodo enviado es null.");
						mostrarMensaje(Boolean.FALSE, " No se encontro un enviado.");
						return;
					}
				}							
				log.info("irValidarDeEfectuadoHaberIncentivo fin v1");
		}
	     catch (BusinessException e) {
				FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONVALIDATION);			
			}  
		    catch (EJBFactoryException e) {
				FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONVALIDATION);
			    e.printStackTrace();		    
			}catch (Exception e) {
				FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONVALIDATION);
			    e.printStackTrace();		    
			}
	}
	
	public void empezandoEfectuado(Enviomonto eo,
								   Integer intMaxEnviado,
								   Integer intSegundaModalidad)//ENVIOMONTO PERIODO SEGUNDA MODALIDAD
	{
		log.info("empezandoEfectuado");
		cargarUsuario();
		planillaEfectuadaResumen = null;
		List<EfectuadoResumen> planillaEfectuadaResumenTemp = null;
		planillaEfectuadaResumen = new ArrayList<EfectuadoResumen>();
		//EfectuadoResumen efectuadoR = null;
		List<Envioresumen> listaEnvioresumen = null;
		List<EfectuadoConceptoComp> listaEfectuadoConceptoCompTemp = null;
		intEfectuado2 =  Constante.intHaberIncentivoNotienEfectuado;		
		try
		{
			EnvioresumenBO boEnvioresumen 	= (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);			
			PlanillaFacadeLocal localPlanilla = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);			

			listaEnvioresumen = boEnvioresumen.getListaEnvioREfectuadoConArchivo(eo.getId().getIntEmpresacuentaPk(),
																				 eo.getIntNivel(),
																				 eo.getIntCodigo(),
																				 eo.getIntTiposocioCod(),
																		         eo.getIntModalidadCod());
			if(listaEnvioresumen != null && !listaEnvioresumen.isEmpty())
			{
				for(Envioresumen er: listaEnvioresumen)
				{															
					eo.setIntIdsucursaladministraPk(er.getIntIdsucursaladministraPk());
					eo.setIntIdsubsucursaladministra(er.getIntIdsubsucursaladministra());
					listaEfectuadoConceptoCompTemp = localPlanilla.getEmpezandoEfectuado(eo,
																					 	 intMaxEnviado,
																					 	 intSegundaModalidad);
					if(listaEfectuadoConceptoCompTemp != null && !listaEfectuadoConceptoCompTemp.isEmpty())
					{
						planillaEfectuadaResumenTemp = localPlanilla.aEfectuar(listaEfectuadoConceptoCompTemp,
																		   		eo,
																		   		intMaxEnviado);
						if(planillaEfectuadaResumenTemp != null && !planillaEfectuadaResumenTemp.isEmpty())
						{
							planillaEfectuadaResumen.add(planillaEfectuadaResumenTemp.get(0));
						}
						
						for(EfectuadoConceptoComp ec: listaEfectuadoConceptoCompTemp)
						{
							listaEfectuadoConceptoComp.add(ec);
						}
					}else
					{
						log.debug("listaEfectuadoConceptoCompTemp is null");
					}				
				}	
			}else
			{
				log.debug("listaEnvioresumen is null");
			}
			if(listaEfectuadoConceptoComp != null && !listaEfectuadoConceptoComp.isEmpty())
			{
				datosValidados = Boolean.TRUE;
				habilitarGrabar = Boolean.TRUE;
				deshabilitarNuevo = Boolean.FALSE;		
				ocultarMensaje();			
				
				Collections.sort(listaEfectuadoConceptoComp, new Comparator<EfectuadoConceptoComp>(){
					public int compare(EfectuadoConceptoComp uno, EfectuadoConceptoComp otro) 
					{				
						return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
					}
				});			
				
				strTipoMantValidarDeEfectuado = Constante.MANTENIMIENTO_NINGUNO;
			}
			
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}		
	}
	
	public void completarEfectuado(Enviomonto eo,
								   Integer intMaxEnviado,
								   Integer intSegundaModalidad)
	{
		log.info("completarEfectuado INICIO");
		planillaEfectuadaResumen = null;
		listaEfectuadoConceptoComp = null;
		List<Envioresumen> listaEnvioresumen = null;
		List<EfectuadoResumen> planillaEfectuadaResumenTemp = null;
		planillaEfectuadaResumen = new ArrayList<EfectuadoResumen>();
		listaEfectuadoConceptoComp = new ArrayList<EfectuadoConceptoComp>();
		intEfectuado2 = Constante.intHaberIncentivoTieneEfectuado;
		
		try
		{
			PlanillaFacadeLocal localPlanilla = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			EnvioresumenBO boEnvioresumen 	= (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
			
			listaEnvioresumen = boEnvioresumen.getListaEnvioREfectuadoConArchivo(eo.getId().getIntEmpresacuentaPk(),
																				 eo.getIntNivel(),
																				 eo.getIntCodigo(),
																				 eo.getIntTiposocioCod(),
																		         eo.getIntModalidadCod());
			if(listaEnvioresumen != null && !listaEnvioresumen.isEmpty())
			{
				for(Envioresumen er: listaEnvioresumen)
				{	
					eo.setIntIdsucursaladministraPk(er.getIntIdsucursaladministraPk());
					eo.setIntIdsubsucursaladministra(er.getIntIdsubsucursaladministra());
					listaEfectuadoConceptoCompTemp = localPlanilla.getCompletandoEfectuado(eo,
																					   	   intMaxEnviado,
																					   	   intSegundaModalidad);
					if(listaEfectuadoConceptoCompTemp != null && !listaEfectuadoConceptoCompTemp.isEmpty())
					{
						planillaEfectuadaResumenTemp = localPlanilla.aCompletarEfectuado(listaEfectuadoConceptoCompTemp,
																					 	 eo,
																					 	 intMaxEnviado);
						if(planillaEfectuadaResumenTemp != null && !planillaEfectuadaResumenTemp.isEmpty())
						{
							planillaEfectuadaResumen.add(planillaEfectuadaResumenTemp.get(0));
						}
						for(EfectuadoConceptoComp ec: listaEfectuadoConceptoCompTemp)
						{
							listaEfectuadoConceptoComp.add(ec);
						}
					}else
					{
						log.debug("listaEfectuadoConceptoCompTemp is null");
					}						
				}
			}else
			{
				log.debug("listaEnvioresumen is null");
			}
			
			
			
			if(listaEfectuadoConceptoComp != null && !listaEfectuadoConceptoComp.isEmpty())
			{
				datosValidados = Boolean.TRUE;
				habilitarGrabar = Boolean.TRUE;
				deshabilitarNuevo = Boolean.FALSE;		
				ocultarMensaje();
				
				Collections.sort(listaEfectuadoConceptoComp, new Comparator<EfectuadoConceptoComp>(){
					public int compare(EfectuadoConceptoComp uno, EfectuadoConceptoComp otro) 
					{				
						return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
					}
				}); 
			}
			
			strTipoMantValidarDeEfectuado = Constante.MANTENIMIENTO_NINGUNO;
			
			log.info("completarEfectuado FIN");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}		
	}
	/**
	 * Si es de otra modalidad debe mostrar el mensaje 
	 * @param event
	 */
	
	public void irAgregarSocio(ActionEvent event)
	{	
		log.debug("irAgregarSocio");
		try
		{	intNombreoDNI = 1;	
			strNombre = new String("");
			blnFuera = Boolean.FALSE;
			strDNI = new String("");			
			efectuadoAgregarSocio = null;			
			listaEfectuadoConceptoCompTemp = null;			
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;		
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	//--------Agregar socioenefectuado INICIO --------------------------------
	public void onclickCheckFueraDelEnviado(ActionEvent event)
	{
		try
		{
			log.debug(blnFuera);
		
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	

	public void buscarEnTodasDNI()
	{
		log.debug("buscarEnTodasDNI INICIO v2");		
		cargarUsuario();
		Socio socio = null;
		Documento documento = null;
		CuentaIntegrante cuentaIntegranteRspta = null;
		List<Persona> listaPersonaBuscar = null;	
		List<SocioEstructura> listaSocioEstructura = null;
		EstructuraDetalle lEstructuraDetalle = null;
		listaEfectuadoConceptoCompTemp = new ArrayList<EfectuadoConceptoComp>();
		EfectuadoConceptoComp efConComp= null;
		blnPopupModalidadDistinta = Boolean.TRUE;
		EstructuraId eId = new EstructuraId();
		Estructura era = null;
		Sucursal sucursal = null;
		Integer intIdSucursal = 0;
		SocioEstructura socioOrigen = null;
		log.debug("tipoSocio:"+dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod());
		try
		{
			ContactoFacadeRemote remoteContacto 	= (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			SocioFacadeRemote remoteSocio 			= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			PersonaFacadeRemote personaFacade 		=  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			CuentaFacadeRemote remoteCuenta 		= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			EmpresaFacadeRemote remoteEmpresa 		= (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			log.debug("strDNI:"+strDNI);
			listaPersonaBuscar = personaFacade.buscarListaPersonaParaFiltro(2, strDNI);
			
			if(listaPersonaBuscar != null && !listaPersonaBuscar.isEmpty())
			{
				log.debug(listaPersonaBuscar.size());
				for(Persona persona : listaPersonaBuscar)
				{
					SocioPK o = new SocioPK();
					o.setIntIdEmpresa(EMPRESA_USUARIO);
					o.setIntIdPersona(persona.getIntIdPersona());
					
					socio = remoteSocio.getSocioPorPK(o);
					
					if(socio != null)
					{						
						listaSocioEstructura = remoteSocio.getListaSocioEstructuraAgregarSocioEfectuado(socio.getId().getIntIdPersona(),
																       								    socio.getId().getIntIdEmpresa(),
																       								    Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO,
																       								    dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod());
	
					if(listaSocioEstructura != null && !listaSocioEstructura.isEmpty())
					{
						log.debug("listasocioestructuracantidad:"+listaSocioEstructura.size());
						socio.setListSocioEstructura(listaSocioEstructura);
						for(SocioEstructura sosies:listaSocioEstructura)
						{						
							if(dtoFiltroDeEfectuado.getIntParaModalidadPlanilla().compareTo(sosies.getIntModalidad())==0)
							{
								blnPopupModalidadDistinta = Boolean.FALSE;
								break;
							}							
						}
						for(SocioEstructura sosie:listaSocioEstructura)
						{
							eId.setIntNivel(sosie.getIntNivel());
							eId.setIntCodigo(sosie.getIntCodigo());
							intIdSucursal = sosie.getIntIdSucursalAdministra();
							break;
						}
						for(SocioEstructura socie: listaSocioEstructura){
							if(socie.getIntTipoEstructura().compareTo(1)==0){
								socioOrigen = socie;
							}
						}
						lEstructuraDetalle = remoteEstructura.getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(eId,
																															Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA,							
																															socioOrigen.getIntTipoSocio(),
																															socioOrigen.getIntModalidad());		
						
						era = remoteEstructura.getEstructuraPorPK(eId);
						if(era != null)
						{							
							juridicaUE = personaFacade.getJuridicaPorPK(era.getIntPersPersonaPk());
							if(juridicaUE != null)
							{
								if(juridicaUE.getStrRazonSocial().compareTo(dtoDeEfectuado.getEstructura().getJuridica().getStrRazonSocial())==0)
								{
									blnDependenciaOrigen = Boolean.FALSE;
								}
								else
								{
									blnDependenciaOrigen = Boolean.TRUE;
								}
							}
						}
						
						sucursal= remoteEmpresa.getSucursalPorId(intIdSucursal);
						if(sucursal != null)
						{
							juridicaSuc = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
						}
					}					
						cuentaIntegranteRspta = remoteCuenta.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(socio.getId(),
																													 Constante.PARAM_T_TIPOCUENTA_ACTIVA,																
																													 Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
						if(cuentaIntegranteRspta != null)
						{
							documento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(socio.getId().getIntIdPersona(), 
																	         				  new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
							if(documento != null)
							{
							efConComp = new EfectuadoConceptoComp();
							efConComp.setSocio(socio);
							efConComp.setDocumento(documento);
							efConComp.setCuentaIntegrante(cuentaIntegranteRspta);
							efConComp.getEfectuado().getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);							
							efConComp.getEfectuado().setIntCuentaPk(cuentaIntegranteRspta.getId().getIntCuenta());								
							efConComp.getEfectuado().setIntNivel(dtoDeEfectuado.getEstructura().getId().getIntNivel());
							efConComp.getEfectuado().setIntCodigo(dtoDeEfectuado.getEstructura().getId().getIntCodigo() );
							efConComp.getEfectuado().setIntModalidadCod(dtoFiltroDeEfectuado.getIntParaModalidadPlanilla());
							efConComp.getEfectuado().setIntTiposocioCod(dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod());							
							efConComp.getEfectuado().setBdMontoEfectuado(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
							efConComp.getEfectuado().setIntPeriodoPlanilla(listaEfectuadoConceptoComp.get(0).getEfectuado().getIntPeriodoPlanilla());
							efConComp.getEfectuado().setIntTipoestructuraCod(socioOrigen.getIntTipoEstructura());
							efConComp.getEfectuado().setIntEmpresasucadministraPk(socioOrigen.getIntEmpresaSucAdministra());
							efConComp.getEfectuado().setIntIdsucursaladministraPk(socioOrigen.getIntIdSucursalAdministra());
							efConComp.getEfectuado().setIntIdsubsucursaladministra(socioOrigen.getIntIdSubsucurAdministra());
							efConComp.getEfectuado().setIntPersonausuarioPk(listaSocioEstructura.get(0).getIntPersonaUsuario());
							efConComp.getEfectuado().setEnvioMonto(new Enviomonto());
							efConComp.getEfectuado().getEnvioMonto().setIntTiposocioCod(dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod());
							efConComp.getEfectuado().getEnvioMonto().setIntModalidadCod(dtoFiltroDeEfectuado.getIntParaModalidadPlanilla());
							efConComp.getEfectuado().getEnvioMonto().setIntNivel(dtoDeEfectuado.getEstructura().getId().getIntNivel());
							efConComp.getEfectuado().getEnvioMonto().setIntCodigo(dtoDeEfectuado.getEstructura().getId().getIntCodigo());
							efConComp.getEfectuado().getEnvioMonto().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							efConComp.getEfectuado().getEnvioMonto().setBdMontoenvio(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
							efConComp.getEfectuado().getEnvioMonto().setIntEmpresasucadministraPk(socioOrigen.getIntEmpresaSucAdministra());
							efConComp.getEfectuado().getEnvioMonto().setIntIdsucursaladministraPk(socioOrigen.getIntIdSucursalAdministra());
							efConComp.getEfectuado().getEnvioMonto().setIntIdsubsucursaladministra(socioOrigen.getIntIdSubsucurAdministra());
							efConComp.getEfectuado().setBdMontoEnviadoDeEnviomonto(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
							if(lEstructuraDetalle != null){
								efConComp.getEfectuado().getEnvioMonto().setIntEmpresasucprocesaPk(lEstructuraDetalle.getIntPersEmpresaPk());
								efConComp.getEfectuado().getEnvioMonto().setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
								efConComp.getEfectuado().getEnvioMonto().setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
								efConComp.getEfectuado().setIntEmpresasucprocesaPk(lEstructuraDetalle.getIntPersEmpresaPk());
								efConComp.getEfectuado().setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
								efConComp.getEfectuado().setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
							}
							listaEfectuadoConceptoCompTemp.add(efConComp);
							}
						}											
					}
				}
			}
			irAgregarEfectuado();
			log.debug("buscarEnTodasDNI FIN v2");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	

	public void irAgregarEfectuado()
	{
		log.debug("irAgregarEfectuado inicio V4");
		Integer intIdPersona = null;
		blnTieneEnEDAE = Boolean.FALSE;
		blnMontoMayorAE = Boolean.FALSE;
		blnModalidadAE = Boolean.FALSE;
		blnNoTienePlanillaAE = Boolean.FALSE;
		blnEnOtrUE = Boolean.FALSE;
		try
		{
			EstructuraFacadeRemote remoteEstrucutra = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			if(listaEfectuadoConceptoCompTemp != null && !listaEfectuadoConceptoCompTemp.isEmpty())
			{
				for(EfectuadoConceptoComp efc: listaEfectuadoConceptoCompTemp)
				{
					intIdPersona = new Integer(efc.getSocio().getId().getIntIdPersona());					
					break;
				}	
			}
			if(blnFuera)
			{
				for(EfectuadoConceptoComp efc:  listaEfectuadoConceptoCompTemp)
				{
					if(efc.getSocio().getListSocioEstructura() == null)
					{
						blnEnOtrUE = Boolean.TRUE;
					}
				}
			}
			
			if(listaEfectuadoConceptoComp != null && !listaEfectuadoConceptoComp.isEmpty())
			{
				for(EfectuadoConceptoComp efc: listaEfectuadoConceptoComp)
				{
					if(intIdPersona != null)
					{
						if(efc.getSocio().getId().getIntIdPersona().compareTo(intIdPersona)==0)
						{							
							if(efc.getEfectuado().getEnvioMonto() != null )
							{
								blnModalidadAE = Boolean.TRUE;
								log.debug("blnModalidadAE:"+blnModalidadAE);
								if(efc.getEfectuado().getBdMontoEnviadoDeEnviomonto().compareTo(new BigDecimal(0)) == 0)
								{								
									blnMontoMayorAE = Boolean.FALSE;								
								}
								else if(efc.getEfectuado().getBdMontoEnviadoDeEnviomonto().compareTo(new BigDecimal(0)) == 1)
								{								
									blnMontoMayorAE = Boolean.TRUE;								
								}
							}else
							{																	
								blnModalidadAE = Boolean.FALSE;
								if(dtoDeEfectuado!= null)
								{
									EstructuraId estId = new EstructuraId();							
									estId.setIntNivel(dtoDeEfectuado.getEstructura().getId().getIntNivel());
									estId.setIntCodigo(dtoDeEfectuado.getEstructura().getId().getIntCodigo());
									
									//ver si sta modalidad esta en estructuraDetalle
									List<EstructuraDetalle> listaEstructuraDetall = remoteEstrucutra.getListaEstructuraDetallePorEstructuraPK(estId);
									if(listaEstructuraDetall!= null && !listaEstructuraDetall.isEmpty())
									{
										for(EstructuraDetalle eee:listaEstructuraDetall)
										{									
											if(eee.getIntParaModalidadCod().compareTo(dtoFiltroDeEfectuado.getIntParaModalidadPlanilla()) == 0
											&& eee.getIntParaTipoSocioCod().compareTo(dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod())==0)
											{
												blnTieneEnEDAE = Boolean.TRUE;												
												break;												
											}
										}
										if(!blnTieneEnEDAE)
										{
											blnNoTienePlanillaAE = Boolean.TRUE;
										}										
									}									
								}								
							}							
						}
					}
				}	
			}			
 			log.debug("irAgregarEfectuado fin V4");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	

	public void buscarEnTodasNombre()
	{
		log.debug("buscarEnTodasNombre");		
		
		Documento documento = null;
		List<Socio> listaSocio = null;		
		CuentaIntegrante cuentaIntegranteRspta = null;
		List<SocioEstructura> listaSocioEstructura = null;
		listaEfectuadoConceptoCompTemp = new ArrayList<EfectuadoConceptoComp>();
		EfectuadoConceptoComp efConComp= null;
		blnPopupModalidadDistinta = Boolean.FALSE;
		EstructuraId eId = new EstructuraId();
		Estructura era = null;
		Sucursal sucursal = null;
		Integer intIdSucursal = 0;
		blnDependenciaOrigen = Boolean.FALSE;
		
		log.debug("tipoSocio:"+dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod());
		try{			
			ContactoFacadeRemote remoteContacto 	= (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			SocioFacadeRemote remoteSocio 			= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			PersonaFacadeRemote personaFacade 		=  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			CuentaFacadeRemote remoteCuenta 		= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			EmpresaFacadeRemote remoteEmpresa 		= (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			Socio o = new Socio();
			o.setId(new SocioPK());
			o.getId().setIntIdEmpresa(EMPRESA_USUARIO);
			o.setStrApePatSoc(strNombre);
			
			listaSocio = remoteSocio.getListaSocioEnEfectuado(o);
											
				if(listaSocio != null && !listaSocio.isEmpty())
				{
					for(Socio socio : listaSocio)
					{	
						
						listaSocioEstructura = remoteSocio.getListaSocioEstructuraAgregarSocioEfectuado(socio.getId().getIntIdPersona(),
																	 								    socio.getId().getIntIdEmpresa(),
																	 								    Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO,
																	 								    dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod());
												
						if(listaSocioEstructura != null && !listaSocioEstructura.isEmpty())
						{
							log.debug("listasocioestructuracantidad:"+listaSocioEstructura.size());
							socio.setListSocioEstructura(listaSocioEstructura);
							for(SocioEstructura sosies:listaSocioEstructura)
							{
								if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(dtoFiltroDeEfectuado.getIntParaModalidadPlanilla()) == 0)
								{
									if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(sosies.getIntModalidad()) == 0 
											|| Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(sosies.getIntModalidad()) == 0)
									{
										blnPopupModalidadDistinta = Boolean.TRUE;
										intModalidadDistinta = Constante.PARAM_T_MODALIDADPLANILLA_HABERES;
										
									}
								}
								else if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(dtoFiltroDeEfectuado.getIntParaModalidadPlanilla()) == 0
										|| Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(dtoFiltroDeEfectuado.getIntParaModalidadPlanilla())==0)
								{
									if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(sosies.getIntModalidad())==0)
									{
										blnPopupModalidadDistinta = Boolean.TRUE;
										intModalidadDistinta = Constante.PARAM_T_MODALIDADPLANILLA_CAS;										
									}
								}
								
							}
							for(SocioEstructura sosie:listaSocioEstructura)
							{
								eId.setIntNivel(sosie.getIntNivel());
								eId.setIntCodigo(sosie.getIntCodigo());
								 intIdSucursal = sosie.getIntIdSucursalAdministra();
								 break;
							}
							era = remoteEstructura.getEstructuraPorPK(eId);
							if(era != null)
							{								
								juridicaUE = personaFacade.getJuridicaPorPK(era.getIntPersPersonaPk());
								if(juridicaUE != null)
								{
									if(juridicaUE.getStrRazonSocial().compareTo(dtoDeEfectuado.getEstructura().getJuridica().getStrRazonSocial())==0)
									{
										blnDependenciaOrigen = Boolean.FALSE;
									}
									else
									{
										blnDependenciaOrigen = Boolean.TRUE;
									}
								}
							}
							
							sucursal= remoteEmpresa.getSucursalPorId(intIdSucursal);
							if(sucursal != null)
							{
								juridicaSuc = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
							}
						}
						
							cuentaIntegranteRspta = remoteCuenta.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(socio.getId(),
																														 Constante.PARAM_T_TIPOCUENTA_ACTIVA,																
																														 Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
							if(cuentaIntegranteRspta != null)
							{
								log.debug(socio);
								documento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(socio.getId().getIntIdPersona(), 
																				 			      new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
								if(documento != null)
								{
									efConComp = new EfectuadoConceptoComp();
									efConComp.setSocio(socio);
									efConComp.setDocumento(documento);
									efConComp.setCuentaIntegrante(cuentaIntegranteRspta);//.setScale(2,BigDecimal.ROUND_HALF_UP)
									efConComp.getEfectuado().getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);							
									efConComp.getEfectuado().setIntCuentaPk(cuentaIntegranteRspta.getId().getIntCuenta());
									efConComp.getEfectuado().setIntTiposocioCod(dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod());
									efConComp.getEfectuado().setBdMontoEfectuado(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
									efConComp.getEfectuado().setIntPeriodoPlanilla(listaEfectuadoConceptoComp.get(0).getEfectuado().getIntPeriodoPlanilla());
									efConComp.getEfectuado().setEnvioMonto(new Enviomonto());
									efConComp.getEfectuado().getEnvioMonto().setIntTiposocioCod(dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod());
									efConComp.getEfectuado().getEnvioMonto().setIntNivel(dtoDeEfectuado.getEstructura().getId().getIntNivel());
									efConComp.getEfectuado().getEnvioMonto().setIntCodigo(dtoDeEfectuado.getEstructura().getId().getIntCodigo());
									efConComp.getEfectuado().getEnvioMonto().setIntModalidadCod(dtoFiltroDeEfectuado.getIntParaModalidadPlanilla());
									efConComp.getEfectuado().getEnvioMonto().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
									efConComp.getEfectuado().getEnvioMonto().setBdMontoenvio(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
									efConComp.getEfectuado().setBdMontoEnviadoDeEnviomonto(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
									listaEfectuadoConceptoCompTemp.add(efConComp);
									
								}
								
							}				
					}
				}				
			
				irAgregarEfectuado();
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public void buscarEnEnviadoxDNI()
	{
		
		EfectuadoConceptoComp efConComp= null;
		List<EfectuadoConceptoComp> lista = new ArrayList<EfectuadoConceptoComp>();
		listaEfectuadoConceptoCompTemp = new ArrayList<EfectuadoConceptoComp>();
		try
		{
			for(EfectuadoConceptoComp e :listaEfectuadoConceptoComp)
			{
				efConComp = new EfectuadoConceptoComp();
				efConComp.setSocio(e.getSocio());
				efConComp.setDocumento(e.getDocumento());			
				efConComp.setEfectuado(e.getEfectuado());
				efConComp.setYaEfectuado(e.getYaEfectuado());
				
				if(e.getYaEfectuado().getEnvioMonto() != null)
				{
					efConComp.getYaEfectuado().setBdMontoEnviadoDeEnviomonto(e.getYaEfectuado().getEnvioMonto().getBdMontoenvio());	
				}
				if(e.getEfectuado().getEnvioMonto() != null)
				{
					efConComp.getEfectuado().setBdMontoEnviadoDeEnviomonto(e.getEfectuado().getEnvioMonto().getBdMontoenvio());	
				}
				
				
				listaEfectuadoConceptoCompTemp.add(efConComp);
			}
			
			
			if(listaEfectuadoConceptoCompTemp !=  null && !listaEfectuadoConceptoCompTemp.isEmpty())
			{
				for(EfectuadoConceptoComp e: listaEfectuadoConceptoCompTemp)
				{
					if(e.getDocumento().getStrNumeroIdentidad().equals(strDNI))
					{					
						lista.add(e);
						break;
					}
				}
				listaEfectuadoConceptoCompTemp = lista;
			}
			irAgregarEfectuado();
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}		
	}
	
	public void buscarEnEnviadoxNombre () 
	{
		List<Socio> listaSocio = null;			
		
		EfectuadoConceptoComp efConComp   = null;
		listaEfectuadoConceptoCompTemp    = new ArrayList<EfectuadoConceptoComp>();		
		List<EfectuadoConceptoComp> lista = new ArrayList<EfectuadoConceptoComp>();
		cargarUsuario();
		log.debug("buscarEnEnviadoxNombre");
		log.debug(strNombre);
		try
		{
			SocioFacadeRemote remoteSocio = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			Socio o = new Socio();
			o.setId(new SocioPK());
			o.getId().setIntIdEmpresa(EMPRESA_USUARIO);
			o.setStrApePatSoc(strNombre);
			
			listaSocio = remoteSocio.getListaSocioEnEfectuado(o);
			
			for(EfectuadoConceptoComp e :listaEfectuadoConceptoComp)
			{
				efConComp = new EfectuadoConceptoComp();
				efConComp.setSocio(e.getSocio());
				efConComp.setDocumento(e.getDocumento());			
				efConComp.setEfectuado(e.getEfectuado());
				efConComp.setYaEfectuado(e.getYaEfectuado());
				
				if(e.getYaEfectuado().getEnvioMonto() != null)
				{
					efConComp.getYaEfectuado().setBdMontoEnviadoDeEnviomonto(e.getYaEfectuado().getEnvioMonto().getBdMontoenvio());	
				}
				if(e.getEfectuado().getEnvioMonto() != null)
				{
					efConComp.getEfectuado().setBdMontoEnviadoDeEnviomonto(e.getEfectuado().getEnvioMonto().getBdMontoenvio());	
				}
				
				
				listaEfectuadoConceptoCompTemp.add(efConComp);
			}
			if(listaSocio != null && !listaSocio.isEmpty())
			{
				log.debug(listaSocio.size());
				SOCIO:for(Socio socio: listaSocio)
				{
					if(listaEfectuadoConceptoCompTemp !=  null && !listaEfectuadoConceptoCompTemp.isEmpty())
					{
						for(EfectuadoConceptoComp e: listaEfectuadoConceptoCompTemp)
						{
							if(socio.getId().getIntIdPersona().compareTo(e.getSocio().getId().getIntIdPersona()) == 0)
							{	
								lista.add(e);
															
								break SOCIO;
							}
						}
					}
				}
				listaEfectuadoConceptoCompTemp  = lista;
				irAgregarEfectuado();
				log.debug("lista = "+listaEfectuadoConceptoCompTemp.size());
			}
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public void verAgregarEfectuado(ActionEvent event)
	{
		log.debug("irAgregarEnviado INICIO V3");		
		List<Tabla> listaTemporal		= new ArrayList<Tabla>();	
		
		try
		{			
			seleccionado = (EfectuadoConceptoComp)event.getComponent().getAttributes().get("item");
			if(seleccionado.getEfectuado().getBdMontoEfectuado() == null && seleccionado.getEfectuado().getBdMontoEnviadoDeEnviomonto() == null)
			{
				seleccionado.getEfectuado().setBdMontoEfectuado(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
				seleccionado.getEfectuado().setBdMontoEnviadoDeEnviomonto(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
								
				listaJuridicaSucursalDePPEjecutoraEnvio = new ArrayList<Juridica>();
				listaJuridicaSucursalDePPEjecutoraEnvio.add(dtoDeEfectuado.getJuridicaSucursal());
				
				listaBusquedaDePPEjecutoraEnvio = new ArrayList<Estructura>();				
				listaBusquedaDePPEjecutoraEnvio.add(dtoDeEfectuado.getEstructura());
				log.debug(listaBusquedaDePPEjecutoraEnvio.size());
				
				if(listaModalidadPlanilla != null && !listaModalidadPlanilla.isEmpty())
				{
					for(Tabla tabla:listaModalidadPlanilla)
					{						
						if(tabla.getIntIdDetalle().compareTo(dtoFiltroDeEfectuado.getIntParaModalidadPlanilla())==0)
						{
							listaTemporal.add(tabla);
						}						
					}
					listaModalidadTipoPlanilla =listaTemporal;
				}
			}
			seleccionado.getEfectuado().setBdMontoEnviadoDeEnviomonto(seleccionado.getEfectuado().getBdMontoEnviadoDeEnviomonto().setScale(2,BigDecimal.ROUND_HALF_UP));
			seleccionado.getEfectuado().setBdMontoEfectuado(seleccionado.getEfectuado().getBdMontoEfectuado().setScale(2, BigDecimal.ROUND_HALF_UP));
			seleccionado.getYaEfectuado().setBdMontoEfectuado(seleccionado.getYaEfectuado().getBdMontoEfectuado().setScale(2,BigDecimal.ROUND_HALF_UP));
			seleccionado.getYaEfectuado().setBdMontoEnviadoDeEnviomonto(seleccionado.getYaEfectuado().getBdMontoEnviadoDeEnviomonto().setScale(2,BigDecimal.ROUND_HALF_UP));
			
			if(seleccionado.getEfectuado().getBdMontoEnviadoDeEnviomonto() != null
					&& seleccionado.getEfectuado().getBdMontoEfectuado() != null)
			{
				blnActivarSegundaModalidad = Boolean.FALSE;
			}else if(seleccionado.getEfectuado().getBdMontoEnviadoDeEnviomonto() == null
					&& seleccionado.getEfectuado().getBdMontoEfectuado() == null)
			{
				blnActivarSegundaModalidad = Boolean.TRUE;
			}
			
			log.debug("irAgregarEnviado FIN V3");
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * recalcular agregar afectuado
	 */
	
	public void recalcularAE()
	{
		log.debug("recalcularAE");
		BigDecimal bdDif = new BigDecimal(0);
		try
		{
			if(seleccionado.getYaEfectuado().getBdMontoEnviadoDeEnviomonto() != null
					&& seleccionado.getYaEfectuado().getBdMontoEfectuado() != null)
			{
				bdDif = seleccionado.getYaEfectuado().getBdMontoEnviadoDeEnviomonto()
							.subtract(seleccionado.getYaEfectuado().getBdMontoEfectuado());
			}else if(seleccionado.getYaEfectuado().getBdMontoEfectuado() == null)
			{
				bdDif = seleccionado.getYaEfectuado().getBdMontoEnviadoDeEnviomonto();
			}
			
			if(seleccionado.getEfectuado().getBdMontoEnviadoDeEnviomonto() != null
					&& seleccionado.getEfectuado().getBdMontoEfectuado() != null)
			{
				if(seleccionado.getEfectuado().getBdMontoEfectuado().compareTo(bdDif) == 0)					
				{
					seleccionado.getEfectuado().setBdMontoEnviadoDeEnviomonto(seleccionado.getEfectuado().getBdMontoEfectuado());
					seleccionado.getEfectuado().getEnvioMonto().setBdMontoenvio(seleccionado.getEfectuado().getBdMontoEfectuado());
				}
				else if(seleccionado.getEfectuado().getBdMontoEfectuado().compareTo(bdDif) == 1)						
				{
					seleccionado.getEfectuado().setBdMontoEnviadoDeEnviomonto(bdDif);
					seleccionado.getEfectuado().getEnvioMonto().setBdMontoenvio(bdDif);
					
				}else if(seleccionado.getEfectuado().getBdMontoEfectuado()
						.compareTo(bdDif)==-1)
				{
					seleccionado.getEfectuado().setBdMontoEnviadoDeEnviomonto(seleccionado.getEfectuado().getBdMontoEfectuado());
					seleccionado.getEfectuado().getEnvioMonto().setBdMontoenvio(seleccionado.getEfectuado().getBdMontoEfectuado());
				}
			}else if(seleccionado.getEfectuado().getBdMontoEnviadoDeEnviomonto() == null
					&& seleccionado.getEfectuado().getBdMontoEfectuado() == null)
			{
				seleccionado.getEfectuado().setBdMontoEnviadoDeEnviomonto(seleccionado.getEfectuado().getBdMontoEfectuado());
				seleccionado.getEfectuado().setBdMontoEnviadoDeEnviomonto(new BigDecimal(0));
				seleccionado.getEfectuado().setBdMontoEfectuado(new BigDecimal(0));
			}		
		
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * grabar agregar efectuado
	 * @throws EJBFactoryException
	 * @throws BusinessException
	 */
	public void grabarAE()throws EJBFactoryException, BusinessException
	{
		log.debug("grabarAE");
		try
		{
			if(seleccionado.getEfectuado().getEnvioMonto() == null)
			{
				seleccionado.getEfectuado().setEnvioMonto(new Enviomonto());
				seleccionado.getEfectuado().getEnvioMonto().setBdMontoenvio(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
			}
			
			if(listaEfectuadoConceptoComp !=  null && !listaEfectuadoConceptoComp.isEmpty())
			{
				for(EfectuadoConceptoComp e: listaEfectuadoConceptoComp)
				{
					if(seleccionado.getSocio().getId().getIntIdPersona()
							.compareTo(e.getSocio().getId().getIntIdPersona())==0)
					{
						listaEfectuadoConceptoComp.remove(e);
						listaEfectuadoConceptoComp.add(seleccionado);
						break;
					}
				}
				Collections.sort(listaEfectuadoConceptoComp, new Comparator<EfectuadoConceptoComp>(){
					public int compare(EfectuadoConceptoComp uno, EfectuadoConceptoComp otro) 
					{				
						return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
					}
				});
			}	
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}				
	}
	

	public void irAgregarEnviado(ActionEvent event)
	{
		log.debug("irAgregarEnviado INICIO");
		blnAgregarPlanilla = Boolean.FALSE;
		blnCuentaPorPagar = Boolean.FALSE;
		bdAmortizacion = null;
		bdInteres = null;
		bdAporte = null;
		bdFdoRetiro = null;
		bdSepelio = null;
		bdMant = null;
		try
		{
			registroSeleccionadoEnAE = (EfectuadoConceptoComp)event.getComponent().getAttributes().get("item");			
			registroSeleccionadoEnAE.getEfectuado().setBlnAgregarNoSocio(Boolean.FALSE);
			registroSeleccionadoEnAE.getEfectuado().setBdMontoEfectuado(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
			registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setBdMontoenvio(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));			
			log.debug("irAgregarEnviado FIN");
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	
	public void planillaresumen(Enviomonto em,Integer intMaxEnviado)
	{
		log.debug("planillaresumen");
		try
		{
			PlanillaFacadeLocal localPlanilla = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);			
			planillaEfectuadaResumen = localPlanilla.aCompletarEfectuado(listaEfectuadoConceptoComp, em,intMaxEnviado);
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	public void calcularAgregarEnviado()
	{
		log.debug("calcularAgregarEnviado inicio v4");
		cargarUsuario();
		Boolean blnSeguir = Boolean.FALSE;
		Boolean blnSeguirA = Boolean.FALSE;
		Boolean blnSeguirI = Boolean.FALSE;
		Timestamp tsFechaFin = null;
		Timestamp tsFechaInicio = null; 
		BigDecimal bdMontoInteres = new BigDecimal(0);
		List<InteresCancelado> listaInteresCancelado = null;
		BigDecimal bdMontoMora  = new BigDecimal(0);  
		BigDecimal totalMontoConcepto   = new BigDecimal(0);
		BigDecimal bdTotalMontoMora  = new BigDecimal(0);		
		InteresProvisionado interesProvisionado = null;
		Envioconcepto envioconceptoA = null;
		Envioconcepto envioconceptoI = null;
		Expediente eexpediente = null;
		BigDecimal bdTotalPrimeraCuota = new BigDecimal(0);
		BigDecimal bdMontoEfectuado = new BigDecimal(0);
		listaTemporal = new ArrayList<Envioconcepto>();
		List<EstadoCredito>  listaEstadoCredito = null;
		listaExpediente = new ArrayList<Expediente>();
		blnDependenciaOrigen = Boolean.FALSE;
		blnPerteneceAlaPlanilla = Boolean.FALSE;
		Boolean blnEncontro = Boolean.FALSE;		
		Boolean blnHaciendoPrioridad = Boolean.FALSE;
		Boolean blnEncontroAcumulativo = Boolean.FALSE;
		 
		try
		{
			ConceptoFacadeRemote remoteConcepto 	= (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			SolicitudPrestamoFacadeRemote facadeSolPres = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			PrioridadDescuentoFacadeRemote   prioriadadFacade   = (PrioridadDescuentoFacadeRemote)EJBFactory.getRemote(PrioridadDescuentoFacadeRemote.class);
			if(registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado().compareTo(new BigDecimal(0)) == 0)
			{
				mostrarMensaje(Boolean.FALSE, "El monto de efectuado a ingresar debe ser mayor a cero."); 
				return;
			}
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			blnAgregarPlanilla  = Boolean.TRUE;
						
			registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setBdMontoenvio(registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado());
			registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setListaEnvioConcepto(new ArrayList<Envioconcepto>());
			//traerme la lista de cuenta concepto  //getListaCuentaConceptoPorPkCuenta
			CuentaId o = new CuentaId();
			o.setIntPersEmpresaPk(registroSeleccionadoEnAE.getCuentaIntegrante().getId().getIntPersEmpresaPk());
			o.setIntCuenta(registroSeleccionadoEnAE.getCuentaIntegrante().getId().getIntCuenta());
			
			List<CuentaConcepto> listaCuentaConcepto =  remoteConcepto.getListaCuentaConceptoPorPkCuenta(o);
			
			if(listaCuentaConcepto!= null && !listaCuentaConcepto.isEmpty())
			{				
				for(CuentaConcepto cu :listaCuentaConcepto)
				{	
					blnHaciendoPrioridad = Boolean.FALSE;					 
					blnSeguir = Boolean.FALSE;
					List<BloqueoCuenta> listaBloqueo = remoteConcepto.getListaBloqueoCuentaPorNroCuenta(registroSeleccionadoEnAE.getCuentaIntegrante().getId().getIntPersEmpresaPk(),
																		 								registroSeleccionadoEnAE.getCuentaIntegrante().getId().getIntCuenta());
					if(listaBloqueo != null && !listaBloqueo.isEmpty())
					{
						for(BloqueoCuenta bl:listaBloqueo)
						{
							if(bl.getIntItemCuentaConcepto() != null)
							{
								if(cu.getId().getIntItemCuentaConcepto().compareTo(bl.getIntItemCuentaConcepto()) == 0)
								{
									Calendar calendario	 	 = GregorianCalendar.getInstance();
									Date fechaActual		 = calendario.getTime();
									if(bl.getTsFechaInicio() != null && bl.getTsFechaFin() != null)
									{									
										Date dInicio = new Date(bl.getTsFechaInicio().getTime());
										Date dFin = new Date(bl.getTsFechaFin().getTime());
										
										if (UtilCobranza.esDentroRangoFechas(dInicio, dFin,	fechaActual))
										{
											blnSeguir = Boolean.TRUE;
										}	
									}
									else if(bl.getTsFechaInicio() != null)
									{
										Date dInicio = new Date(bl.getTsFechaInicio().getTime());
										
											if(UtilCobranza.esMayorRangoFechas(dInicio, fechaActual))
											{
												blnSeguir = Boolean.TRUE;
											}										
									}
									else if(bl.getTsFechaFin() != null)
									{
										Date dFin = new Date(bl.getTsFechaFin().getTime());
										if(UtilCobranza.esMenorRangoFechas(dFin, fechaActual))
										{
											blnSeguir = Boolean.TRUE;
										}
									}							
								}
							}							
						}
					}
					if(!blnSeguir)
					{						
						List<CuentaConceptoDetalle> listadetalle = remoteConcepto.getCuentaConceptoDetofCobranza(cu.getId());
						
						if(listadetalle!= null && !listadetalle.isEmpty())
						{
							totalMontoConcepto = new BigDecimal(0);
							for(CuentaConceptoDetalle det: listadetalle)
							{
								blnEncontro = Boolean.FALSE;
								blnEncontroAcumulativo = Boolean.FALSE;
								Integer intConceptoGeneral = 0;
								log.debug("conceptogeneral:"+det.getIntParaTipoConceptoCod());
								Envioconcepto envioconcepto = new Envioconcepto();
								envioconcepto.setId(new EnvioconceptoId());
								envioconcepto.getId().setIntEmpresacuentaPk(registroSeleccionadoEnAE.getCuentaIntegrante().getId().getIntPersEmpresaPk());
								envioconcepto.setIntPeriodoplanilla(registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla());
								envioconcepto.setIntCuentaPk(registroSeleccionadoEnAE.getCuentaIntegrante().getId().getIntCuenta());
								envioconcepto.setIntItemcuentaconcepto(det.getId().getIntItemCuentaConcepto());												
								envioconcepto.setBlnFiltrado(Boolean.FALSE);
								envioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);											
								envioconcepto.setIntIndicienpor(0);
								envioconcepto.setIntIndidescjudi(0);
								envioconcepto.setIntIndilicencia(0);							 
								
								if(det.getTsInicio() != null)
								{	 if(det.getTsInicio().compareTo(UtilCobranza.obtieneFechaActualEnTimesTamp()) == -1){
										if(det.getTsFin() != null){
											if(det.getTsFin().compareTo(UtilCobranza.obtieneFechaActualEnTimesTamp()) == 1){
												blnHaciendoPrioridad = Boolean.TRUE;
											}else{  
												log.debug("fecha fin es menor a hoy dia");
												blnHaciendoPrioridad = Boolean.TRUE;
												break;
											}
										}else{
											log.debug("fecha fin es null");
											blnHaciendoPrioridad = Boolean.TRUE;
										}	
									}else{
										log.debug("fecha inicio es mayor a hoy dia");
										break;
									}
										if(blnHaciendoPrioridad){
											
											CaptacionId captacionId = new CaptacionId();
					    					captacionId.setIntItem(det.getIntItemConcepto());
					    					captacionId.setIntParaTipoCaptacionCod(det.getIntParaTipoConceptoCod());
					    					captacionId.setIntPersEmpresaPk(det.getId().getIntPersEmpresaPk());
					    					
					    					   if (Constante.PARA_TIPO_CONCEPTO_MANTENIMIENTO.compareTo(det.getIntParaTipoConceptoCod()) == 0){
					    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO;
					    					   }
					    					   else
					    					   if (Constante.PARA_TIPO_CONCEPTO_RETIRO.compareTo(det.getIntParaTipoConceptoCod()) == 0){
					    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO;	 
					    					   }
					    					   else
					    					   if (Constante.PARA_TIPO_CONCEPTO_SEPELIO.compareTo(det.getIntParaTipoConceptoCod()) == 0){
					    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO;	 
					    					   }
					    					   else if(Constante.PARA_TIPO_CONCEPTO_APORTE.compareTo(det.getIntParaTipoConceptoCod()) == 0)
					    					   {
					    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION;
					    					   }
					    					   
					    					   envioconcepto.setIntTipoconceptogeneralCod(intConceptoGeneral);
					    					    
					    						PrioridadDescuento prioriadDscto =  prioriadadFacade.obtenerOrdenPrioridadDescuento
					    					 		(registroSeleccionadoEnAE.getCuentaIntegrante().getId().getIntPersEmpresaPk(),
					    					 																	intConceptoGeneral,
					    					 																			captacionId,
					    					 																				  null);
					    					   if (prioriadDscto != null)
					    					   {
					    						   log.debug("bien");
					    						   envioconcepto.setIntOrdenPrioridad(prioriadDscto.getIntPrdeOrdenprioridad());				    						  
					    					   }	
										
					    					   if(Constante.PARAM_T_TIPODSCTOAPORT_CANCELATORIO.compareTo(det.getIntParaTipoDescuentoCod()) == 0)
												{									
													
													 List<ConceptoPago> listaConceptoPago = remoteConcepto.getListaConceptoPagoPorCuentaConceptoDet(det.getId());
														if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
														{
															for(ConceptoPago x:listaConceptoPago)
															{
																if(x.getIntPeriodo().compareTo(envioconcepto.getIntPeriodoplanilla()) == 0)
																{
																	totalMontoConcepto = x.getBdMontoSaldo();
																	blnEncontro = Boolean.TRUE;
																	break;
																}
															}
															if(!blnEncontro)
															{
																totalMontoConcepto = det.getBdMontoConcepto().setScale(2,BigDecimal.ROUND_HALF_UP);
															}
															
														}else
														{
															totalMontoConcepto = det.getBdMontoConcepto().setScale(2,BigDecimal.ROUND_HALF_UP);
														}
													 
													envioconcepto.setBdMontoconcepto(totalMontoConcepto);													 
												}
												else if(Constante.PARAM_T_TIPODSCTOAPORT_ACUMULATIVO.compareTo(det.getIntParaTipoDescuentoCod()) == 0)
												{	 
														 
														List<ConceptoPago> listaConceptoPago = remoteConcepto.getListaConceptoPagoPorCuentaConceptoDet(det.getId());
														
														if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
														{
															BigDecimal bdSumaPorPeriodo = BigDecimal.ZERO;
															for (ConceptoPago conceptoPago : listaConceptoPago)
															{
																if(conceptoPago.getIntPeriodo().compareTo(envioconcepto.getIntPeriodoplanilla()) == -1)
																{
																	bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());	
																}else if(conceptoPago.getIntPeriodo().compareTo(envioconcepto.getIntPeriodoplanilla()) == 0)
																{
																	bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());
																	blnEncontroAcumulativo = Boolean.TRUE;
																}
															}
															if(!blnEncontroAcumulativo)
															{
																bdSumaPorPeriodo = bdSumaPorPeriodo.add(det.getBdMontoConcepto());
															}
															totalMontoConcepto=	bdSumaPorPeriodo.setScale(2,BigDecimal.ROUND_HALF_UP) ;										
														}
														else
														{
															totalMontoConcepto = det.getBdMontoConcepto().setScale(2,BigDecimal.ROUND_HALF_UP) ;
														}
													 
													envioconcepto.setBdMontoconcepto(totalMontoConcepto);												 
												}								
												registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getListaEnvioConcepto().add(envioconcepto);												 
										}								
									}									
							}
						}
					}
				}
			}			
			
			/**
			 * Expediente
			 * 
			 */
			List<Expediente> listaExpedientes = remoteConcepto.getListaExpedienteConSaldoPorEmpresaYcuenta(registroSeleccionadoEnAE.getSocio().getId().getIntIdEmpresa(),
											 															   registroSeleccionadoEnAE.getCuentaIntegrante().getId().getIntCuenta());
													
			if(listaExpedientes != null && !listaExpedientes.isEmpty())
			{
				listaExpediente = new ArrayList<Expediente>();
				for(Expediente ex: listaExpedientes)
				{				
					bdTotalPrimeraCuota = new BigDecimal(0);
					List<BloqueoCuenta> listaBloqueoCuenta = remoteConcepto.getListaBloqueoCuentaPorNroCuenta(ex.getId().getIntPersEmpresaPk(), 
					 						   									 							  ex.getId().getIntCuentaPk());
					if(listaBloqueoCuenta!= null && !listaBloqueoCuenta.isEmpty())
					{
						for(BloqueoCuenta b: listaBloqueoCuenta)
						{
							if(b.getIntParaTipoConceptoCre()!= null)
							{
								if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION
										.compareTo(b.getIntParaTipoConceptoCre()) == 0)
								{
									Calendar calendario	 	 = GregorianCalendar.getInstance();
									Date fechaActual		 = calendario.getTime();
									if(b.getTsFechaInicio() != null && b.getTsFechaFin() != null)
									{									
										Date dInicio = new Date(b.getTsFechaInicio().getTime());
										Date dFin = new Date(b.getTsFechaFin().getTime());
										
										if (UtilCobranza.esDentroRangoFechas(dInicio, dFin,	fechaActual))
										{
											blnSeguirA = Boolean.TRUE;
										}	
									}
									else if(b.getTsFechaInicio() != null)
									{
										Date dInicio = new Date(b.getTsFechaInicio().getTime());
										
											if(UtilCobranza.esMayorRangoFechas(dInicio, fechaActual))
											{
												blnSeguirA = Boolean.TRUE;
											}										
									}
									else if(b.getTsFechaFin() != null)
									{
										Date dFin = new Date(b.getTsFechaFin().getTime());
										if(UtilCobranza.esMenorRangoFechas(dFin, fechaActual))
										{
											blnSeguirA = Boolean.TRUE;
										}
									}									
								}
								else if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES
										.compareTo(b.getIntParaTipoConceptoCre()) == 0)
								{
									Calendar calendario	 	 = GregorianCalendar.getInstance();
									Date fechaActual		 = calendario.getTime();
									if(b.getTsFechaInicio() != null && b.getTsFechaFin() != null)
									{									
										Date dInicio = new Date(b.getTsFechaInicio().getTime());
										Date dFin = new Date(b.getTsFechaFin().getTime());
										
										if (UtilCobranza.esDentroRangoFechas(dInicio, dFin,	fechaActual))
										{
											blnSeguirI = Boolean.TRUE;
										}	
									}
									else if(b.getTsFechaInicio() != null)
									{
										Date dInicio = new Date(b.getTsFechaInicio().getTime());
										
											if(UtilCobranza.esMayorRangoFechas(dInicio, fechaActual))
											{
												blnSeguirI = Boolean.TRUE;
											}
										
									}
									else if(b.getTsFechaFin() != null)
									{
										Date dFin = new Date(b.getTsFechaFin().getTime());
										if(UtilCobranza.esMenorRangoFechas(dFin, fechaActual))
										{
											blnSeguirI = Boolean.TRUE;
										}
									}
								}
							}
						}
						
					}
						log.debug("idExp:"+ex.getId());
						List<Cronograma> listaCronograma = remoteConcepto.getListaCronogramaPorPkExpediente(ex.getId());
					
						if(!blnSeguirA)
						{							
							if(listaCronograma!=null && !listaCronograma.isEmpty())
							{
								for(Cronograma c:listaCronograma)
								{
									if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION
											.compareTo(c.getIntParaTipoConceptoCreditoCod()) == 0)
									{
										if(registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla() >= c.getIntPeriodoPlanilla())
										{
											if(!blnSeguirA)
											{
												bdMontoMora = bdMontoMora.add(c.getBdSaldoDetalleCredito());												
											}						
										}								
										if(registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla().compareTo(c.getIntPeriodoPlanilla()) == 0)
										{	
											 tsFechaFin = c.getTsFechaVencimiento();
										}
										
										if(c.getIntNumeroCuota().compareTo(1) == 0)
										{
											if(c.getIntPeriodoPlanilla().compareTo(registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla()) == 1)
											{
												bdTotalPrimeraCuota = bdTotalPrimeraCuota.add(c.getBdMontoConcepto());	
												ex.setIntMes(Integer.parseInt(c.getIntPeriodoPlanilla().toString().substring(4,c.getIntPeriodoPlanilla().toString().length())));
												ex.setIntAnio(Integer.parseInt(c.getIntPeriodoPlanilla().toString().substring(0,4)));												
												break;
											}
										}
									}
								}
								
								log.debug("total bdMontoMora:"+bdMontoMora);
								ex.setBdTotalAmortizacion(bdMontoMora);
								bdTotalMontoMora = bdTotalMontoMora.add(bdMontoMora);
								if(tsFechaFin == null)
								{																	
									tsFechaFin = UtilCobranza.obtenerUltimoDiaDelMesAnioPeriodo(registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla());										
								}
							}
							if(bdTotalPrimeraCuota.compareTo(new BigDecimal(0)) == 1)
							{
								envioconceptoA = new Envioconcepto();
								envioconceptoA.setId(new EnvioconceptoId());
								envioconceptoA.getId().setIntEmpresacuentaPk(ex.getId().getIntPersEmpresaPk());
								envioconceptoA.setIntPeriodoplanilla(registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla());
								envioconceptoA.setIntCuentaPk(ex.getId().getIntCuentaPk());									
								envioconceptoA.setIntItemexpediente(ex.getId().getIntItemExpediente());
								envioconceptoA.setIntItemdetexpediente(ex.getId().getIntItemExpedienteDetalle());
								envioconceptoA.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);	
								envioconceptoA.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								envioconceptoA.setBdMontoconcepto(bdMontoMora);
								envioconceptoA.getBdMontoconcepto().setScale(2,BigDecimal.ROUND_HALF_UP);
								envioconceptoA.setBlnFiltrado(Boolean.FALSE);
								envioconceptoA.setIntIndicienpor(0);
								envioconceptoA.setIntIndidescjudi(0);
								envioconceptoA.setIntIndilicencia(0);
								envioconceptoA.setIntParaTipoCreditoCod(ex.getIntParaTipoCreditoCod());
								
								CreditoId creditoId = new CreditoId();
				     			creditoId.setIntItemCredito(ex.getIntItemCredito());
				     			creditoId.setIntParaTipoCreditoCod(ex.getIntParaTipoCreditoCod());
				     			creditoId.setIntPersEmpresaPk(ex.getIntPersEmpresaCreditoPk());
				     			 
				     			PrioridadDescuento prioriadDscto =  prioriadadFacade.obtenerOrdenPrioridadDescuento(ex.getIntPersEmpresaCreditoPk(),
				     														   					 Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION,
																		     														          null,
																		     														    creditoId);
				     			if (prioriadDscto != null){
				     				envioconceptoA.setIntOrdenPrioridad(prioriadDscto.getIntPrdeOrdenprioridad());			     				
				     			}
				     			registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getListaEnvioConcepto().add(envioconceptoA);
				     			 
							}						
						}
					
						if(!blnSeguirI)
						{
							if(listaCronograma!=null && !listaCronograma.isEmpty())
							{
								for(Cronograma c2:listaCronograma)
								{								
									if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES
											.compareTo(c2.getIntParaTipoConceptoCreditoCod()) == 0)
									{										
										if(c2.getIntNumeroCuota().compareTo(1)==0)
										{											
											if(c2.getIntPeriodoPlanilla().compareTo(registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla()) == 1)
											{
												bdTotalPrimeraCuota = bdTotalPrimeraCuota.add(c2.getBdMontoConcepto());	
											}												
										}
									}
								}								
							}
							if(bdTotalPrimeraCuota.compareTo(new BigDecimal(0)) == 1)
							{
								if(ex.getBdPorcentajeInteres()!= null)
								{
									if(ex.getBdPorcentajeInteres().compareTo(new BigDecimal(0))==1)
									{										
										ExpedienteId icId = new ExpedienteId();
										icId.setIntCuentaPk(ex.getId().getIntCuentaPk());
										icId.setIntPersEmpresaPk(ex.getId().getIntPersEmpresaPk());
										icId.setIntItemExpediente(ex.getId().getIntItemExpediente());
										icId.setIntItemExpedienteDetalle(ex.getId().getIntItemExpedienteDetalle());
										
										listaInteresCancelado =  remoteConcepto.getListaInteresCanceladoPorExpedienteCredito(icId);
										
										if(listaInteresCancelado != null && !listaInteresCancelado.isEmpty())
										{
											for(InteresCancelado interesCancelado: listaInteresCancelado)
											{
												tsFechaInicio = interesCancelado.getTsFechaMovimiento();
												//sumo un dia
												tsFechaInicio = UtilCobranza.sumarUnDiaAFecha(tsFechaInicio);
												log.debug("fechaINicio="+tsFechaInicio);
												break;
											}											
										}
										else
										{
											ExpedienteCreditoId pId = new ExpedienteCreditoId();
											pId.setIntPersEmpresaPk(ex.getId().getIntPersEmpresaPk());
											pId.setIntCuentaPk(ex.getId().getIntCuentaPk());
											pId.setIntItemExpediente(ex.getId().getIntItemExpediente());
											pId.setIntItemDetExpediente(ex.getId().getIntItemExpedienteDetalle());
											
											listaEstadoCredito = facadeSolPres.getListaEstadosPorExpedienteCreditoId(pId);
											if(listaEstadoCredito != null && !listaEstadoCredito.isEmpty())
											{												
												for(EstadoCredito estadoCredito:listaEstadoCredito)
												{
													if(estadoCredito.getIntParaEstadoCreditoCod()
															.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO) == 0)
													{
														tsFechaInicio =  estadoCredito.getTsFechaEstado();														
														break;
													}
												}
											}
										}
										java.sql.Date      dateFechaInicio      = new           java.sql.Date(tsFechaInicio.getTime()); 
										java.sql.Date      dateFechaFin         = new           java.sql.Date(tsFechaFin.getTime());
										int intNumerosDias = UtilCobranza.obtenerDiasEntreFechas(dateFechaInicio, dateFechaFin);
								       
										interesProvisionado = new InteresProvisionado();
										interesProvisionado.setId(new InteresProvisionadoId());
										interesProvisionado.getId().setIntPersEmpresaPk(ex.getId().getIntPersEmpresaPk());
										interesProvisionado.getId().setIntCuentaPk(ex.getId().getIntCuentaPk());
										interesProvisionado.getId().setIntItemExpediente(ex.getId().getIntItemExpediente());
										interesProvisionado.getId().setIntItemExpedienteDetalle(ex.getId().getIntItemExpedienteDetalle());										
										interesProvisionado.setIntParaTipoMovInt(Constante.PARAM_T_ENVIADO_POR_PLANILLA);
										interesProvisionado.setTsFechaInicio(tsFechaInicio);
										interesProvisionado.setTsFechaFin(tsFechaFin); 					
										interesProvisionado.setIntNumeroDias(intNumerosDias);
										interesProvisionado.setBdSaldoPrestamo(ex.getBdSaldoCredito());
										interesProvisionado.setBdTasaInteres(ex.getBdPorcentajeInteres());
										
										bdMontoInteres = new BigDecimal(0);										
										bdMontoInteres = ex.getBdPorcentajeInteres()
														 .multiply(ex.getBdSaldoCredito())
														 .multiply(new BigDecimal(intNumerosDias));
										bdMontoInteres = bdMontoInteres.divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
										
										 
										ex.setBdTotalInteres(bdMontoInteres);
										interesProvisionado.setBdMontoInteres(bdMontoInteres); 
										interesProvisionado.setBdMontoAtrasadoInteres(null);
										interesProvisionado.setBdMontoTotalInteres(null);
										interesProvisionado.setBdMontoSaldo(null);			
										interesProvisionado.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
																				
										envioconceptoI = new Envioconcepto();
										envioconceptoI.setId(new EnvioconceptoId());					
										envioconceptoI.getId().setIntEmpresacuentaPk(ex.getId().getIntPersEmpresaPk());
										envioconceptoI.setIntPeriodoplanilla(registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla());
										envioconceptoI.setIntCuentaPk(ex.getId().getIntCuentaPk());										
										envioconceptoI.setIntItemexpediente(ex.getId().getIntItemExpediente());
										envioconceptoI.setIntItemdetexpediente(ex.getId().getIntItemExpedienteDetalle());
										envioconceptoI.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES);
										envioconceptoI.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);	
										envioconceptoI.setBdMontoconcepto(bdMontoInteres);
										envioconceptoI.getBdMontoconcepto().setScale(2,BigDecimal.ROUND_HALF_UP);
										envioconceptoI.setBlnFiltrado(Boolean.FALSE);
										envioconceptoI.setIntIndicienpor(0);
										envioconceptoI.setIntIndidescjudi(0);
										envioconceptoI.setIntIndilicencia(0);
										envioconceptoI.setIntParaTipoCreditoCod(ex.getIntParaTipoCreditoCod());
										
										CreditoId creditoId = new CreditoId();
						     			 creditoId.setIntItemCredito(ex.getIntItemCredito());
						     			 creditoId.setIntParaTipoCreditoCod(ex.getIntParaTipoCreditoCod());
						     			 creditoId.setIntPersEmpresaPk(ex.getIntPersEmpresaCreditoPk());
						     			 
						     			PrioridadDescuento prioriadDscto =  prioriadadFacade.obtenerOrdenPrioridadDescuento(ex.getIntPersEmpresaCreditoPk(),
												   														      Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES,
												   														      										   null,
												   														      									  creditoId);
						     			if (prioriadDscto != null)
						     				envioconceptoI.setIntOrdenPrioridad(prioriadDscto.getIntPrdeOrdenprioridad()); 
										
										registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getListaEnvioConcepto().add(envioconceptoI);
									}
										if(bdTotalPrimeraCuota.compareTo(new BigDecimal(0)) == 1)
										{
											eexpediente = new Expediente();
											eexpediente.setId(ex.getId());
											eexpediente.getListaInteresProvisionado().add(interesProvisionado);
											eexpediente.setIntParaTipoCreditoCod(ex.getIntParaTipoCreditoCod());
											eexpediente.setBdSaldoCredito(ex.getBdSaldoCredito().setScale(2,BigDecimal.ROUND_HALF_UP));				
											eexpediente.setBdprimeraCuota(bdTotalPrimeraCuota.setScale(2,BigDecimal.ROUND_HALF_UP));
											if(ex.getBdTotalAmortizacion() != null)
											{
												eexpediente.setBdTotalAmortizacion(ex.getBdTotalAmortizacion());	
											}
											if(ex.getBdTotalInteres() != null)
											{
												eexpediente.setBdTotalInteres(ex.getBdTotalInteres());
											}
											eexpediente.setIntMes(ex.getIntMes());
											eexpediente.setIntAnio(ex.getIntAnio());
											listaExpediente.add(eexpediente);																				
										}																				
							  }
						  }						
					   }						
					}
			}
			
			Collections.sort(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getListaEnvioConcepto(),
					new Comparator() {  
	            public int compare(Object o1, Object o2) {  
	            	Envioconcepto e1 = (Envioconcepto) o1;  
	            	Envioconcepto e2 = (Envioconcepto) o2;  
	                if (e1.getIntOrdenPrioridad() == null)
	                {	
	                	e1.setIntOrdenPrioridad(88);
	                }
	                
	                if (e2.getIntOrdenPrioridad() == null)
	                {	
	                	e2.setIntOrdenPrioridad(88);
	                }
	                return e1.getIntOrdenPrioridad().compareTo(e2.getIntOrdenPrioridad());
	            }  
	        });
			
			
			bdMontoEfectuado = registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado();
			log.debug("bdMontoEfectuado:"+bdMontoEfectuado);
			for(Envioconcepto ec: registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getListaEnvioConcepto())
			{
				log.debug(ec);
				if(ec.getIntOrdenPrioridad() != null)
				{					
					if((ec.getBdMontoconcepto().compareTo(new BigDecimal(0))==1 ||
							ec.getBdMontoconcepto().compareTo(new BigDecimal(0))== 0 ) 
							&& bdMontoEfectuado.compareTo(ec.getBdMontoconcepto()) == 1)
					{
						bdMontoEfectuado = bdMontoEfectuado.subtract(ec.getBdMontoconcepto());						
						ec.setBlnFiltrado(Boolean.TRUE);
						listaTemporal.add(ec);
					}
					else if((ec.getBdMontoconcepto().compareTo(new BigDecimal(0))==1 ||
							ec.getBdMontoconcepto().compareTo(new BigDecimal(0))== 0 ) 
							&& bdMontoEfectuado.compareTo(ec.getBdMontoconcepto()) == -1)
					{						
						ec.setBdMontoconcepto(bdMontoEfectuado);
						ec.setBlnFiltrado(Boolean.TRUE);
						bdMontoEfectuado = new BigDecimal(0);
						listaTemporal.add(ec);
						break;
					}
					else if((ec.getBdMontoconcepto().compareTo(new BigDecimal(0))==1 ||
							ec.getBdMontoconcepto().compareTo(new BigDecimal(0))== 0 ) 
							&& bdMontoEfectuado.compareTo(ec.getBdMontoconcepto()) == 0)
					{
						bdMontoEfectuado = bdMontoEfectuado.subtract(ec.getBdMontoconcepto());
						ec.setBlnFiltrado(Boolean.TRUE);
						listaTemporal.add(ec);
						break;
					}
				}
			}
			BigDecimal sumaEnvioConcepto = new BigDecimal(0);
			if(listaTemporal.size() > 0){
				for(Envioconcepto en: listaTemporal)
				{
					sumaEnvioConcepto = sumaEnvioConcepto.add(en.getBdMontoconcepto());
				}
				//Cuenta x Pagar
				bdCuentaPorPagar = new BigDecimal(0);
				log.debug("montoefectuado:"+registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado());
				log.debug("sumaenvioconcepto:"+sumaEnvioConcepto);
				if(registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado().compareTo(sumaEnvioConcepto)== 1)
				{
					bdCuentaPorPagar = registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado().subtract(sumaEnvioConcepto);
					blnCuentaPorPagar = Boolean.TRUE;					 
					
					Envioconcepto eco = new Envioconcepto();
					eco.setId(new EnvioconceptoId());
					eco.getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
					eco.setIntPeriodoplanilla(listaTemporal.get(0).getIntPeriodoplanilla());
					eco.setIntCuentaPk(listaTemporal.get(0).getIntCuentaPk());
					eco.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR); ;
					eco.setBlnFiltrado(Boolean.FALSE);
					eco.setIntIndicienpor(0);
					eco.setIntIndidescjudi(0);
					eco.setIntIndilicencia(0);
					eco.setBdMontoconcepto(bdCuentaPorPagar);
					eco.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaTemporal.add(eco);
				}				
			}else{
				log.debug("listaTemporal is null");
			}
			
			
			
			if(listaEfectuadoConceptoComp != null && !listaEfectuadoConceptoComp.isEmpty())
			{
				for(EfectuadoConceptoComp e2: listaEfectuadoConceptoComp)
				{
					if(e2.getSocio().getId().getIntIdPersona().compareTo(registroSeleccionadoEnAE.getSocio().getId().getIntIdPersona()) == 0)
					{
						blnPerteneceAlaPlanilla = Boolean.TRUE;
						break;
					}
				}
			}	
			
			AgregarLICDJUD(registroSeleccionadoEnAE.getSocio(),
						   registroSeleccionadoEnAE.getEfectuado(),						   
						   registroSeleccionadoEnAE.getDocumento(),
						   dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod());
			
			registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getListaEnvioConcepto().clear();
			registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setListaEnvioConcepto(listaTemporal);
			registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setBdMontoEfectuado(sumaEnvioConcepto);
			log.debug(registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado());
			
			for(Envioconcepto eoo:registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getListaEnvioConcepto())
			{
				if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION.compareTo(eoo.getIntTipoconceptogeneralCod()) == 0 
						&& eoo.getIntItemcuentaconcepto()!= null)
				{
					bdAporte = eoo.getBdMontoconcepto();					
				}
				else if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO.compareTo(eoo.getIntTipoconceptogeneralCod()) == 0)
				{
					bdSepelio = eoo.getBdMontoconcepto();					
				}
				else if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO.compareTo(eoo.getIntTipoconceptogeneralCod()) == 0)
				{
					bdFdoRetiro = eoo.getBdMontoconcepto();
					log.debug("bdFdoRetiro:"+bdFdoRetiro);
				}
				else if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO.compareTo(eoo.getIntTipoconceptogeneralCod()) == 0)
				{
					bdMant = eoo.getBdMontoconcepto();					
				}
				else if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES.compareTo(eoo.getIntTipoconceptogeneralCod()) == 0
						&& eoo.getIntItemexpediente() != null)
				{
					bdInteres = eoo.getBdMontoconcepto();
				}
				else if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION.compareTo(eoo.getIntTipoconceptogeneralCod()) == 0
						&& eoo.getIntItemexpediente() != null)
				{
					bdAmortizacion = eoo.getBdMontoconcepto();					
				}
			}			
			
		log.debug("calcularAgregarEnviado fin v5");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public void  AgregarLICDJUD(Socio lSocio,  Efectuado ex,Documento documento , Integer intTipoSocio) throws BusinessException
	{
		List<AdminPadron> listaAdminPadron = null;
		EstructuraFacadeRemote remoteEstructura = null;
		Padron padron = null;
		Terceros tercer = null;
		try
		{
			remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			for(SocioEstructura socioE: lSocio.getListSocioEstructura())
			{													
				if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(socioE.getIntModalidad()) == 0)
				{					
					AdminPadron adminP= new AdminPadron();
					adminP.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					adminP.setId(new AdminPadronId());											
					adminP.getId().setIntParaModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
					adminP.getId().setIntParaTipoSocioCod(intTipoSocio);					
					
					listaAdminPadron = remoteEstructura.getTipSocioModPeriodoMes(adminP);
					
					if(listaAdminPadron != null && !listaAdminPadron.isEmpty())
					{						
						for(AdminPadron aPatron : listaAdminPadron)
						{
							if(Constante.PARAM_T_TIPOARCHIVOS_PADRON.compareTo(aPatron.getId().getIntParaTipoArchivoPadronCod()) == 0)
							{								
								padron = remoteEstructura.getPadronSOLOPorLibElectoral(documento.getStrNumeroIdentidad(),
																						aPatron.getId().getIntItemAdministraPadron());
								if(padron != null)
								{																	
									ex.setBlnLIC(Boolean.TRUE);
									for(Envioconcepto xx:ex.getEnvioMonto().getListaEnvioConcepto())
									{
										xx.setIntIndilicencia(1);										
									}
								}else
								{
									for(Envioconcepto xx:ex.getEnvioMonto().getListaEnvioConcepto())
									{
										xx.setIntIndilicencia(0);										
									}
								}
							}
							else if(Constante.PARAM_T_TIPOARCHIVOS_DSCTOTERCEROS.compareTo(aPatron.getId().getIntParaTipoArchivoPadronCod()) == 0)
							{								
								Terceros ter = new Terceros();
								ter.setId(new TercerosId());
								ter.getId().setIntItemAdministraPadron(aPatron.getId().getIntItemAdministraPadron());
								ter.setStrLibeje(documento.getStrNumeroIdentidad());
								
								tercer = remoteEstructura.getPorItemDNI(ter);
								
								if(tercer != null)
								{								
									ex.setBlnDJUD(Boolean.TRUE);
									for(Envioconcepto xx: ex.getEnvioMonto().getListaEnvioConcepto())
									{
										xx.setIntIndidescjudi(1);
									}
								}else
								{
									for(Envioconcepto xx: ex.getEnvioMonto().getListaEnvioConcepto())
									{
										xx.setIntIndidescjudi(0);
									}
								}
							}
						}
						
					}
				}
				else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(socioE.getIntModalidad()) == 0)
				{					
					AdminPadron adminP= new AdminPadron();
					adminP.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					adminP.setId(new AdminPadronId());
					adminP.getId().setIntParaModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS);
					adminP.getId().setIntParaTipoSocioCod(intTipoSocio);											
					
					listaAdminPadron = remoteEstructura.getTipSocioModPeriodoMes(adminP);
					if(listaAdminPadron != null && !listaAdminPadron.isEmpty())
					{															
						for(AdminPadron aPatron1:listaAdminPadron)
						{
							if(Constante.PARAM_T_TIPOARCHIVOS_PADRON.compareTo(aPatron1.getId().getIntParaTipoArchivoPadronCod()) == 0)
							{								
								padron = remoteEstructura.getPadronSOLOPorLibElectoral(documento.getStrNumeroIdentidad(), 
								   		 											   aPatron1.getId().getIntItemAdministraPadron());
								if(padron != null)
								{																	
									ex.setBlnLIC(Boolean.TRUE);
									for(Envioconcepto xx:ex.getEnvioMonto().getListaEnvioConcepto())
									{
										xx.setIntIndilicencia(1);										
									}
								}else
								{
									for(Envioconcepto xx:ex.getEnvioMonto().getListaEnvioConcepto())
									{
										xx.setIntIndilicencia(0);										
									}
								}								
							}
							else if(Constante.PARAM_T_TIPOARCHIVOS_DSCTOTERCEROS.compareTo(aPatron1.getId().getIntParaTipoArchivoPadronCod()) == 0)
							{							
								Terceros ter = new Terceros();
								ter.setId(new TercerosId());
								ter.getId().setIntItemAdministraPadron(listaAdminPadron.get(0).getId().getIntItemAdministraPadron());
								ter.setStrLibeje(documento.getStrNumeroIdentidad());							
								tercer = remoteEstructura.getPorItemDNI(ter);								
								if(tercer != null)
								{								
									ex.setBlnDJUD(Boolean.TRUE);
									for(Envioconcepto xx: ex.getEnvioMonto().getListaEnvioConcepto())
									{
										xx.setIntIndidescjudi(1);
									}
								}else
								{
									for(Envioconcepto xx: ex.getEnvioMonto().getListaEnvioConcepto())
									{
										xx.setIntIndidescjudi(0);
									}
								}								
							}
						}						
					}
				}
			}
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public void agregarALaPlanilla()
	{
		cargarUsuario();		
		log.debug("agregarALaPlanilla inicio v2");
		Integer numeroDeAfectados = 0;
		Boolean blnEstaEnlaMismaSucursalAdministra = Boolean.FALSE;
		List<Envioresumen> listaEnvioresumen = null;
		try
		{
			EnvioresumenBO boEnvioresumen    = (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
						
			listaEnvioresumen = boEnvioresumen.getListEnvRes(EMPRESA_USUARIO
															,registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla()
															,registroSeleccionadoEnAE.getEfectuado().getIntTiposocioCod()
															,registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntModalidadCod()
															,registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntNivel()
															,registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntCodigo());
			
			
			//Aqui tengo que completar tanto  enviomonto y el efectuado
			registroSeleccionadoEnAE.setBlnAgregarSocio(Boolean.TRUE);
			registroSeleccionadoEnAE.getEfectuado().setBlnEfectuadoConcepto0(Boolean.FALSE);
			registroSeleccionadoEnAE.getEfectuado().setBlnAgregarNoSocio(Boolean.FALSE);
			log.debug(registroSeleccionadoEnAE.getEfectuado());
			/*
			if(listaEfectuadoConceptoComp != null && !listaEfectuadoConceptoComp.isEmpty())
			{*/
				registroSeleccionadoEnAE.getEfectuado().setTsFecharegistro(UtilCobranza.obtieneFechaActualEnTimesTamp());
				registroSeleccionadoEnAE.getEfectuado().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				registroSeleccionadoEnAE.getEfectuado().setIntEmpresausuarioPk(EMPRESA_USUARIO);
				registroSeleccionadoEnAE.getEfectuado().setIntPersonausuarioPk(PERSONA_USUARIO);
				registroSeleccionadoEnAE.getEfectuado().setIntParaTipoIngresoDatoCod(Constante.PARAM_ParaTipoIngresoDatoSocio);
				registroSeleccionadoEnAE.getEfectuado().setBlnListaEnvioConcepto(Boolean.TRUE);				 
				registroSeleccionadoEnAE.getEfectuado().setIntPersonaIntegrante(registroSeleccionadoEnAE.getSocio().getId().getIntIdPersona());
				
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntEmpresausuarioPk(EMPRESA_USUARIO);
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntPersonausuarioPk(PERSONA_USUARIO);
				
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntModalidadCod(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntModalidadCod());
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntTiposocioCod(registroSeleccionadoEnAE.getEfectuado().getIntTiposocioCod());
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntNivel(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntNivel());
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntCodigo(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntCodigo());
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setTsFecharegistro(UtilCobranza.obtieneFechaActualEnTimesTamp());
			/*	
			}else{
				log.debug("listaEfectuadoconceptocomp is null.");
			}*/			
			if(planillaEfectuadaResumen != null && planillaEfectuadaResumen.size() > 0){
				for(EfectuadoResumen efectuadoResumen: planillaEfectuadaResumen)
				{
					if(efectuadoResumen.getIntIdsucursaladministraPk().compareTo(registroSeleccionadoEnAE.getEfectuado().getIntIdsucursaladministraPk()) == 0
						&& efectuadoResumen.getIntIdsubsucursaladministra().compareTo(registroSeleccionadoEnAE.getEfectuado().getIntIdsubsucursaladministra()) == 0)
					{
						if(registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado() != null)
						{
							efectuadoResumen.getBdMontoTotal().add(registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado());	
						}
						if(listaEnvioresumen != null && !listaEnvioresumen.isEmpty())
						{						
							for(Envioresumen er: listaEnvioresumen){
								if(er.getIntIdsucursaladministraPk().compareTo(registroSeleccionadoEnAE.getEfectuado().getIntIdsucursaladministraPk()) == 0 && 
								   er.getIntIdsubsucursaladministra().compareTo(registroSeleccionadoEnAE.getEfectuado().getIntIdsubsucursaladministra()) == 0){
									registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setEnvioresumen(er);
								}
							}							
						}
						registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setBlnTieneNuevoEnvioresumen(Boolean.FALSE);
						efectuadoResumen.getListaEfectuado().add(registroSeleccionadoEnAE.getEfectuado());
						numeroDeAfectados = efectuadoResumen.getIntNumeroAfectados()+1;
						efectuadoResumen.setIntNumeroAfectados(numeroDeAfectados);
						blnEstaEnlaMismaSucursalAdministra = Boolean.TRUE;
						break;
					} 
				}	
			}else{
				log.debug("planillaEfectuadaResumen is null");
				
			}
			
			if(blnEstaEnlaMismaSucursalAdministra){
				listaEfectuadoConceptoComp.add(registroSeleccionadoEnAE);	
			}else{
				Envioresumen enre = new Envioresumen();
				enre.setId(new EnvioresumenId());
				enre.getId().setIntEmpresaPk(EMPRESA_USUARIO);
				enre.setIntDocumentogeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAENVIADA);
				enre.setIntPeriodoplanilla(registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla());
				enre.setBdMontototal(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getBdMontoenvio());							
				enre.setIntNumeroafectados(1);
				enre.setIntTiposocioCod(registroSeleccionadoEnAE.getEfectuado().getIntTiposocioCod());
				enre.setIntModalidadCod(registroSeleccionadoEnAE.getEfectuado().getIntModalidadCod());
				enre.setIntNivel(registroSeleccionadoEnAE.getEfectuado().getIntNivel());
				enre.setIntCodigo(registroSeleccionadoEnAE.getEfectuado().getIntCodigo());
				enre.setIntIdsucursalprocesaPk(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntIdsucursalprocesaPk());
				enre.setIntIdsubsucursalprocesaPk(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntIdsubsucursalprocesaPk());
				enre.setIntIdsucursaladministraPk(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntIdsucursaladministraPk());
				enre.setIntIdsubsucursaladministra(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntIdsubsucursaladministra());
				enre.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setBlnTieneNuevoEnvioresumen(Boolean.TRUE);
				registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setEnvioresumen(enre);
				
				EfectuadoResumen erAddSocio = new EfectuadoResumen();
				erAddSocio.getId().setIntEmpresa(EMPRESA_USUARIO);
				erAddSocio.setIntPeriodoPlanilla(registroSeleccionadoEnAE.getEfectuado().getIntPeriodoPlanilla());
				erAddSocio.setIntTiposocioCod(registroSeleccionadoEnAE.getEfectuado().getIntTiposocioCod());
				erAddSocio.setIntModalidadCod(registroSeleccionadoEnAE.getEfectuado().getIntModalidadCod());
				erAddSocio.setIntNivel(registroSeleccionadoEnAE.getEfectuado().getIntNivel());
				erAddSocio.setIntCodigo(registroSeleccionadoEnAE.getEfectuado().getIntCodigo());
				erAddSocio.setIntIdsucursaladministraPk(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntIdsucursaladministraPk());
				erAddSocio.setIntIdsubsucursaladministra(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntIdsubsucursaladministra());
				erAddSocio.setIntIdsucursalprocesaPk(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntIdsucursalprocesaPk());
				erAddSocio.setIntIdsubsucursalprocesaPk(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getIntIdsubsucursalprocesaPk());
				erAddSocio.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				erAddSocio.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
				erAddSocio.setIntPersonausuarioPk(PERSONA_USUARIO);
				erAddSocio.setBdMontoTotal(registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado());
				erAddSocio.setIntNumeroAfectados(1);
				registroSeleccionadoEnAE.getEfectuado().setIntParaTipoIngresoDatoCod(1);
				registroSeleccionadoEnAE.getEfectuado().setIntPersonausuarioPk(PERSONA_USUARIO);				
				erAddSocio.getListaEfectuado().add(registroSeleccionadoEnAE.getEfectuado()) ;
				erAddSocio.setIntParaEstadoPago(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				planillaEfectuadaResumen.add(erAddSocio);				
				listaEfectuadoConceptoComp.add(registroSeleccionadoEnAE); 
			}
			
			
			log.debug("despues:"+listaEfectuadoConceptoComp.size());
			Collections.sort(listaEfectuadoConceptoComp, new Comparator<EfectuadoConceptoComp>(){
				public int compare(EfectuadoConceptoComp uno, EfectuadoConceptoComp otro) 
				{				
					return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
				}
			});			
			 
			log.debug("agregarALaPlanilla fin v1");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	

	//-------- Agregar socioenefectuado FIN --------------------------------
	
	public void exportarEfectuadoAExcel()
	{
		log.debug("exportarEfectuadoAExcel inicio");
		String filename = null;
		String strTipoSocio = null;	
		String strNivel = null;	
		List<EfectuadoConceptoComp> listaTemporal = new ArrayList<EfectuadoConceptoComp>();		
		try
		{				
			if(listaEfectuadoConceptoComp != null && !listaEfectuadoConceptoComp.isEmpty())
			{
				for(EfectuadoConceptoComp efctuado:listaEfectuadoConceptoComp)
				{
					if(efctuado.getEfectuado().getBdMontoEfectuado() != null)
					{
						listaTemporal.add(efctuado);
					}
				}
				listaEfectuadoConceptoComp = listaTemporal;
			}
			
			
			if(dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod().compareTo(1) == 0)
			{
				strTipoSocio ="A";
			}
			else
			{
				strTipoSocio ="C";
			}
			
			 Workbook workbook = new HSSFWorkbook();			
			 
			 HSSFFont font = (HSSFFont) workbook.createFont();			
			 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);		 
			 font.setColor(HSSFFont.COLOR_RED);
			 
			 CellStyle unlockedCellStyle = workbook.createCellStyle();
			 unlockedCellStyle.setVerticalAlignment(CellStyle.ALIGN_RIGHT);
			 unlockedCellStyle.setLocked(false); //montos.
			 
			 CellStyle lockedCellStyle = workbook.createCellStyle();
			 lockedCellStyle.setLocked(true);
			 
			 CellStyle lockedCellStyle2 = workbook.createCellStyle();
			 lockedCellStyle2.setLocked(true);
			 lockedCellStyle2.setFont(font);
			
			 
			 Sheet sheet = workbook.createSheet();
			 sheet.protectSheet("password");
			 
			 Row row = sheet.createRow(0);
			 Cell id00 = row.createCell(0);
			 id00.setCellValue("SUCURSAL:");			
			 id00.setCellStyle(lockedCellStyle2);
			 
			 Cell id01 = row.createCell(1);
			 id01.setCellValue(dtoDeEfectuado.getJuridicaSucursal().getStrRazonSocial());
			 id01.setCellStyle(lockedCellStyle);
			 
			 row = sheet.createRow(1);
			 Cell id10 = row.createCell(0);
			 id10.setCellValue("UNI. EJE.:");			
			 id10.setCellStyle(lockedCellStyle2);
			 
			 
			 Cell id11 = row.createCell(1);
			 id11.setCellValue(dtoDeEfectuado.getEstructura().getJuridica().getStrRazonSocial());
			 id11.setCellStyle(lockedCellStyle);
			 
			 Cell id12 = row.createCell(2);
			 id12.setCellValue("MODALIDAD:");
			 id12.setCellStyle(lockedCellStyle2);
			 
			 Cell id13 = row.createCell(3);
			 id13.setCellValue(dtoDeEfectuado.getStrModalidad());
			 id13.setCellStyle(lockedCellStyle);
			 
			 row = sheet.createRow(2);
			 
			 Cell id20 = row.createCell(0);
			 id20.setCellValue("IDE EJE:");
			 id20.setCellStyle(lockedCellStyle2);
			 
			 Cell id21 = row.createCell(1);
			 if(dtoDeEfectuado.getEstructura().getId().getIntNivel().compareTo(10)==-1)
			 {
				 strNivel = new StringBuilder("0").append(dtoDeEfectuado.getEstructura().getId().getIntNivel().toString())
		 						.append(dtoDeEfectuado.getEstructura().getId().getIntCodigo()).toString();
				
			 }
			 else if(dtoDeEfectuado.getEstructura().getId().getIntNivel().compareTo(10) == 1 ||
					 dtoDeEfectuado.getEstructura().getId().getIntNivel().compareTo(10) == 0)
			 {
				 strNivel = new StringBuilder(dtoDeEfectuado.getEstructura().getId().getIntNivel().toString())
								.append(dtoDeEfectuado.getEstructura().getId().getIntCodigo()).toString();
				 
			 }
			 id21.setCellValue(strNivel); 
			 id21.setCellStyle(lockedCellStyle);
			 
			 Cell id22 = row.createCell(2);
			 id22.setCellValue("PERIODO:");
			 id22.setCellStyle(lockedCellStyle2);
			 
			 Cell id23 = row.createCell(3);			
			 id23.setCellValue(listaEfectuadoConceptoComp.get(0).getEfectuado().getIntPeriodoPlanilla().toString());
						 
			 row = sheet.createRow(3);
			 
			 Cell id30 = row.createCell(0);
			 id30.setCellValue("COD");
			 id30.setCellStyle(lockedCellStyle2);
			 
			 Cell id31 = row.createCell(1);
			 id31.setCellValue("DNI");
			 id31.setCellStyle(lockedCellStyle2);
			 
			 Cell id32 = row.createCell(2);
			 id32.setCellValue("NOMBRES");
			 id32.setCellStyle(lockedCellStyle2);
			 
			 Cell id33 = row.createCell(3);
			 id33.setCellValue("MONTO      ");
			 id33.setCellStyle(lockedCellStyle2);
			 
			 Cell id34 = row.createCell(4);
			 id34.setCellValue("TIPO");
			 id34.setCellStyle(lockedCellStyle2);
			 
			 Integer f = listaEfectuadoConceptoComp.size()+4;
			 
			 Integer b = 4;
			 
			 DataFormat format = workbook.createDataFormat();
			 unlockedCellStyle.setDataFormat(format.getFormat("#,##0.00"));
			 
			for(EfectuadoConceptoComp eee:listaEfectuadoConceptoComp)
			{
				log.debug("montoEfectuado: "+eee.getEfectuado().getBdMontoEfectuado());
				if(b.compareTo(f) == -1)
				  {
					row = sheet.createRow(b);	
					Cell id0 = row.createCell(0);
					id0.setCellStyle(lockedCellStyle);
					id0.setCellValue(eee.getSocio().getId().getIntIdPersona());
					
					Cell id1 = row.createCell(1);
					id1.setCellValue(eee.getDocumento().getStrNumeroIdentidad());
					id1.setCellStyle(lockedCellStyle) ;
					
					Cell id2 = row.createCell(2);
					id2.setCellValue(new StringBuilder(eee.getSocio().getStrApePatSoc())
									.append(" ").append(eee.getSocio().getStrApeMatSoc())
									.append(" ").append(eee.getSocio().getStrNombreSoc()).toString());
					id2.setCellStyle(lockedCellStyle);
					
					Cell id3 = row.createCell(3);
					Float numero = eee.getEfectuado().getBdMontoEfectuado().floatValue();
					id3.setCellValue(numero);
					
					id3.setCellStyle(unlockedCellStyle);
					
					Cell id4 = row.createCell(4);
					id4.setCellValue(strTipoSocio);
					
					id4.setCellStyle(lockedCellStyle);
					
					b++;
					sheet.autoSizeColumn(0);
					sheet.autoSizeColumn(1);
					sheet.autoSizeColumn(2);
					sheet.autoSizeColumn(3);
					sheet.autoSizeColumn(4);
				  }
			}
			
			row = sheet.createRow(b+1);	
			
			Cell id52 = row.createCell(2);
			id52.setCellValue("TOTAL");
			id52.setCellStyle(lockedCellStyle2);
			
			Cell id53 = row.createCell(3);
			String strcell = new StringBuilder("SUM(D4:D").append(b).append(")").toString();
			log.debug("strcell: "+strcell);
			id53.setCellType(Cell.CELL_TYPE_FORMULA);
			id53.setCellFormula(strcell);			
			id53.setCellStyle(unlockedCellStyle);
			
			 filename = new StringBuilder(strNivel)			 							
			 							.append("_")
			 							.append(listaEfectuadoConceptoComp.get(0).getEfectuado().getIntPeriodoPlanilla().toString())			 							
			 							.append(".xls").toString();	
			 sheet.autoSizeColumn(0);
			 sheet.autoSizeColumn(1);
			 sheet.autoSizeColumn(2);
			 sheet.autoSizeColumn(3);
			 sheet.autoSizeColumn(4);
			 
			 FacesContext facesContext = FacesContext.getCurrentInstance();
			 HttpServletResponse externalContext = (HttpServletResponse)facesContext.getExternalContext().getResponse();
			 externalContext.setContentType("application/vnd.ms-excel");
			 externalContext.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			 OutputStream output = externalContext.getOutputStream();
			 workbook.write(output);
			 facesContext.responseComplete();
			  
		log.debug("exportarEfectuadoAExcel fin");	
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	//--------RELACIONAR NO SOCIO INICIO --------------------------------
	public void irArelacionar(ActionEvent event)
	{
		log.debug("irArelacionar start v1");
		try
		{
			intNombreoDNI = 1;	
			strNombre = null;					
			strDNI = null;			
			blnFuera =  Boolean.FALSE;
			blnMontoNoSocio = Boolean.FALSE;
			blnAgregarNosocio = Boolean.FALSE;
			blnEstaEnSocio = Boolean.FALSE;
			bdMontoNoSocio = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP) ;			
			listandoPersona  = null;
			efectuadoNoSocio = null;
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			//deshabilitarPanelInferior();
			log.debug("irArelacionar end v1");
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarRelacionarNoSocioxN()
	{
		log.debug("buscarRelacionarNoSocioxN INICIO");
		log.debug("strNombre: "+strNombre);
		blnGrabarNuevaPersona = Boolean.FALSE;
		listandoPersona = null;
		blnEstaEnSocio = Boolean.FALSE;
		blnNoEstaEnSocio = Boolean.FALSE;
		blnEstaEnSocioEstructura = Boolean.FALSE;
		blnNoEstaEnSocioEstructura = Boolean.FALSE;
		List<SocioEstructura> listaSocioEstructura = null;
		
		Integer intTipoSocio = null;
		Integer intNivel 	= null;
		Integer intCodigo  = null;
		Socio socio = null;
		cargarUsuario();
		try
		{
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			SocioFacadeRemote socioFacade 	  = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			SocioFacadeRemote remoteSocio 	  = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			intTipoSocio = dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod();
			intNivel = dtoDeEfectuado.getEstructura().getId().getIntNivel();
			intCodigo = dtoDeEfectuado.getEstructura().getId().getIntCodigo();
			
			listandoPersona = personaFacade.getListaBuscarPersonaNatural(EMPRESA_USUARIO, strNombre,1, null, null);
						
			if(listandoPersona != null && !listandoPersona.isEmpty())
			{
				efectuadoNoSocio = new Efectuado();				
				efectuadoNoSocio.setPersona(new Persona());
				efectuadoNoSocio.setDocumento(new Documento());
				efectuadoNoSocio.setBlnNoTieneSocio(Boolean.FALSE);
				efectuadoNoSocio.setBlnTieneSocio(Boolean.FALSE);
				efectuadoNoSocio.setBlnEfectuadoConcepto0(Boolean.FALSE);
				
				for(Persona persona:listandoPersona)
				{					
					efectuadoNoSocio.setPersona(persona);
					efectuadoNoSocio.setDocumento(persona.getDocumento());
					SocioPK socioPK = new SocioPK();
	    			socioPK.setIntIdEmpresa(EMPRESA_USUARIO);
	    			socioPK.setIntIdPersona(persona.getIntIdPersona());
	    			
	    			socio = socioFacade.getSocioPorPK(socioPK);
	    			if(socio != null)
	    			{	    				
	    				efectuadoNoSocio.setSocio(new Socio());
	    				efectuadoNoSocio.setBlnTieneSocio(Boolean.TRUE);
	    				blnEstaEnSocio = Boolean.TRUE;
	    				listaSocioEstructura = remoteSocio.getListaXSocioPKActivoTipoSocio
											  				(persona.getIntIdPersona(),
											  				 EMPRESA_USUARIO,
											  				 Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO,
											  				 intTipoSocio,
											  				 intNivel,
											  				 intCodigo);
	    				
						if(listaSocioEstructura != null && !listaSocioEstructura.isEmpty())
						{							
							blnEstaEnSocioEstructura = Boolean.TRUE;
							socio.setListSocioEstructura(new ArrayList<SocioEstructura>());
							socio.setListSocioEstructura(listaSocioEstructura);
							efectuadoNoSocio.setSocio(socio);
						}
						else
						{							
							efectuadoNoSocio.setSocio(socio);
							blnNoEstaEnSocioEstructura = Boolean.TRUE;
						}
	    			}
	    			else
	    			{	    				
	    				efectuadoNoSocio.setBlnNoTieneSocio(Boolean.TRUE);
	    				blnNoEstaEnSocio = Boolean.TRUE;
	    			}
				}				
			}
			
			log.debug("buscarRelacionarNoSocioxN FIN");	
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarRelacionarNoSocioxDNI() 
	{
		log.debug("buscarRelacionarNoSocioxDNI INICIO");
		
		blnGrabarNuevaPersona = Boolean.FALSE;
		blnEstaEnSocio = Boolean.FALSE;
		blnNoEstaEnSocio = Boolean.FALSE;
		blnEstaEnSocioEstructura = Boolean.FALSE;
		blnNoEstaEnSocioEstructura = Boolean.FALSE;
		listandoPersona = null;
		
		List<SocioEstructura> listaSocioEstructura = null;
		
		Integer intTipoSocio = null;
		Integer intNivel 	= null;
		Integer intCodigo  = null;
		Socio socio = null;
		cargarUsuario();
				
		try
		{
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			SocioFacadeRemote socioFacade 	  = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			SocioFacadeRemote remoteSocio 	  = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			intTipoSocio = dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod();
			intNivel 	 = dtoDeEfectuado.getEstructura().getId().getIntNivel();
			intCodigo	 = dtoDeEfectuado.getEstructura().getId().getIntCodigo();
			listandoPersona = personaFacade.getListaBuscarPersonaNatural(EMPRESA_USUARIO, null,1, strDNI, null);
						
			if(listandoPersona != null && !listandoPersona.isEmpty())
			{
				efectuadoNoSocio = new Efectuado();				
				efectuadoNoSocio.setPersona(new Persona());
				efectuadoNoSocio.setDocumento(new Documento());
				efectuadoNoSocio.setBlnNoTieneSocio(Boolean.FALSE);
				efectuadoNoSocio.setBlnTieneSocio(Boolean.FALSE);
				efectuadoNoSocio.setBlnEfectuadoConcepto0(Boolean.FALSE);
				
				for(Persona persona:listandoPersona)
				{
					efectuadoNoSocio.setPersona(persona);
					efectuadoNoSocio.setDocumento(persona.getDocumento());
					SocioPK socioPK = new SocioPK();
	    			socioPK.setIntIdEmpresa(EMPRESA_USUARIO);
	    			socioPK.setIntIdPersona(persona.getIntIdPersona());
	    			
	    			socio = socioFacade.getSocioPorPK(socioPK);
	    			if(socio != null)
	    			{
	    				log.debug("si esta en socio");
	    				efectuadoNoSocio.setSocio(new Socio());
	    				efectuadoNoSocio.setBlnTieneSocio(Boolean.TRUE);
	    				blnEstaEnSocio = Boolean.TRUE;
	    				listaSocioEstructura = remoteSocio.getListaXSocioPKActivoTipoSocio(persona.getIntIdPersona(),
	    																					EMPRESA_USUARIO,
	    																					Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO,
																					        intTipoSocio,
																					        intNivel,
																					        intCodigo);
	    				
						if(listaSocioEstructura != null && !listaSocioEstructura.isEmpty())
						{
							log.debug("tiene listaSocioEstructura");
							blnEstaEnSocioEstructura = Boolean.TRUE;
							socio.setListSocioEstructura(new ArrayList<SocioEstructura>());
							socio.setListSocioEstructura(listaSocioEstructura);
							efectuadoNoSocio.setSocio(socio);
						}
						else
						{
							log.debug("NO tiene listaSocioEstructura");
							efectuadoNoSocio.setSocio(socio);
							blnNoEstaEnSocioEstructura = Boolean.TRUE;
						}
	    			}
	    			else
	    			{
	    				log.debug("no esta en el esquema socio");
	    				efectuadoNoSocio.setBlnNoTieneSocio(Boolean.TRUE);
	    				blnNoEstaEnSocio = Boolean.TRUE;
	    			}
				}
				log.debug("canti listasocio:"+listandoPersona.size());	
			}
						
			log.debug("buscarRelacionarNoSocioxDNI FIN");
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	public void validarRelacionarNoSocio()
	{
		log.debug("validarRelacionarNoSocio start");
		try{
			
			if(bdMontoNoSocio.compareTo(new BigDecimal(0)) == 0)
			{
				mostrarMensaje(Boolean.FALSE, "Ingresar monto mayor a 0");
				return;
			}else
			{				
				mostrarMensajeExito = Boolean.FALSE;
				mostrarMensajeError = Boolean.FALSE;
				blnAgregarNosocio = Boolean.TRUE;
			}
			log.debug("validarRelacionarNoSocio end");
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void agregarNoSocio()
	{
		log.debug("agregarNoSocio INICIO v1");
		cargarUsuario();
		Boolean blnEstaEnLaMismaSucursalAdministra = Boolean.FALSE;		
		EstructuraDetalle lEstructuraDetalle = null;
		EfectuadoConcepto efectuadoConcepto = null;
		CuentaIntegrante cuentaIntegrante = null;		
		EstructuraId lEstructuraId 		= null;		
		Envioconcepto envioconcepto = null;
		List<Envioresumen> listaO = null;
		Efectuado efectuadoTemp = null;
		SocioEstructura socioE = null;
		Enviomonto enviomonto = null;
		
		List<Envioconcepto> listaEnvioconcepto = new ArrayList<Envioconcepto>();
		
		Integer pIntModalidadCod = dtoFiltroDeEfectuado.getIntParaModalidadPlanilla();
		Integer pIntNivel 		 = dtoDeEfectuado.getEstructura().getId().getIntNivel();
		Integer pIntCodigo 		 = dtoDeEfectuado.getEstructura().getId().getIntCodigo();
		Integer intTipoSocio 	 = dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod();
		
		try{			
			CuentaFacadeRemote   cuentaFacade 		= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			EnvioresumenBO boEnvioresumen   		= (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
			
			if(efectuadoNoSocio.getSocio() != null)
			{
				if(efectuadoNoSocio.getSocio().getListSocioEstructura()!=null)
				{
					for(SocioEstructura so: efectuadoNoSocio.getSocio().getListSocioEstructura())
					{
						if(so.getIntTipoEstructura().compareTo(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)==0)
						{
							socioE = so;
						}
					}
				}				
			}
			lEstructuraId 		= new EstructuraId();
			lEstructuraId.setIntCodigo(pIntCodigo);
			lEstructuraId.setIntNivel(pIntNivel);		
			
			lEstructuraDetalle = remoteEstructura.getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(lEstructuraId,
																												Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA,							
																												intTipoSocio,
																												pIntModalidadCod);			
			
				if(listaEfectuadoConceptoComp != null && !listaEfectuadoConceptoComp.isEmpty())
				{	log.debug("cant listaEfectuadoConceptoComp: "+listaEfectuadoConceptoComp.size());
					efectuadoTemp = listaEfectuadoConceptoComp.get(0).getEfectuado(); 
					envioconcepto = new Envioconcepto();
					envioconcepto.setId(new EnvioconceptoId());					
					envioconcepto.getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
					envioconcepto.setIntPeriodoplanilla(efectuadoTemp.getIntPeriodoPlanilla());																		
					envioconcepto.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
					envioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);	
					envioconcepto.setBdMontoconcepto(bdMontoNoSocio);
					envioconcepto.setIntIndicienpor(0);
					envioconcepto.setIntIndidescjudi(0);
					envioconcepto.setIntIndilicencia(0);
					listaEnvioconcepto.add(envioconcepto);
					
					efectuadoConcepto = new EfectuadoConcepto();
					efectuadoConcepto.getId().setIntEmpresaCuentaEnvioPk(EMPRESA_USUARIO);
					efectuadoConcepto.setIntTipoConceptoGeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
					efectuadoConcepto.setBdMontoConcepto(bdMontoNoSocio);
					efectuadoConcepto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					
					enviomonto = new Enviomonto();
					enviomonto.setBdMontoenvio(bdMontoNoSocio);
					enviomonto.setId(new EnviomontoId());
			    	enviomonto.getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);			    	
			    	enviomonto.setIntTiposocioCod(efectuadoTemp.getIntTiposocioCod());
			    	enviomonto.setIntModalidadCod(efectuadoTemp.getIntModalidadCod());
			    	enviomonto.setIntNivel(efectuadoTemp.getIntNivel());
			    	enviomonto.setIntCodigo(efectuadoTemp.getIntCodigo());
			    	if(socioE != null)
			    	{
			    	 enviomonto.setIntTipoestructuraCod(socioE.getIntTipoEstructura());				    	
				     enviomonto.setIntEmpresasucadministraPk(socioE.getIntEmpresaSucAdministra());
				     enviomonto.setIntIdsucursaladministraPk(socioE.getIntIdSucursalAdministra());
				     enviomonto.setIntIdsubsucursaladministra(socioE.getIntIdSubsucurAdministra());	
				     enviomonto.setIntEmpresausuarioPk(socioE.getIntEmpresaUsuario());
				     enviomonto.setIntPersonausuarioPk(socioE.getIntPersonaUsuario());
			    	}
			    	if(lEstructuraDetalle != null)
			    	{
			    	 enviomonto.setIntEmpresasucprocesaPk(lEstructuraDetalle.getIntPersEmpresaPk());
				     enviomonto.setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
				     enviomonto.setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
			    	}
			    	enviomonto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			    	enviomonto.setListaEnvioConcepto(new ArrayList<Envioconcepto>());
			    	enviomonto.setListaEnvioConcepto(listaEnvioconcepto);
					
			    	
			    	efectuadoNoSocio.getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
					efectuadoNoSocio.setBdMontoEfectuado(bdMontoNoSocio);			
					efectuadoNoSocio.setEnvioMonto(new Enviomonto());
					efectuadoNoSocio.setListaEfectuadoConcepto(new ArrayList<EfectuadoConcepto>());
					efectuadoNoSocio.getListaEfectuadoConcepto().add(efectuadoConcepto);
					
					EfectuadoConceptoComp ec = new EfectuadoConceptoComp();
					ec.getSocio().setId(new SocioPK());
					ec.setBlnAgregarNoSocio(Boolean.TRUE);
					ec.setBlnAgregarSocio(Boolean.FALSE);
					if(efectuadoNoSocio.getPersona() != null)	
					{
						ec.getSocio().getId().setIntIdPersona(registroPersona.getIntIdPersona());		 	        	
		 	        	cuentaIntegrante = cuentaFacade.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(efectuadoNoSocio.getSocio().getId() ,
		 	        																							Constante.PARAM_T_TIPOCUENTA_ACTIVA,																
		 	        																							Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);		 	        	
		 	        	if(cuentaIntegrante != null)
		 	        	{
		 	        		//esta vigente		 	        		 
		 	        		efectuadoNoSocio.setCuentaIntegrante(new CuentaIntegrante());
		 	        		efectuadoNoSocio.setCuentaIntegrante(cuentaIntegrante);		 	        		
		 	        	}
		 	        	ec.setDocumento(efectuadoNoSocio.getDocumento());
						if(efectuadoNoSocio.getSocio() != null &&
						   efectuadoNoSocio.getSocio().getId().getIntIdPersona() != null)
						{
							ec.setSocio(efectuadoNoSocio.getSocio());
							ec.getSocio().setId(efectuadoNoSocio.getSocio().getId());
						}
						else 
						{
							ec.setSocio(socioSelecionado);
							efectuadoNoSocio.setSocio(socioSelecionado);
						}

						if(planillaEfectuadaResumen != null && !planillaEfectuadaResumen.isEmpty())
						{
							for(EfectuadoResumen eumen: planillaEfectuadaResumen)
					    	{											
								if(eumen.getIntIdsucursaladministraPk() != null && 
								   eumen.getIntIdsubsucursaladministra() != null ){
									if(eumen.getIntIdsucursaladministraPk().compareTo(socioE.getIntIdSucursalAdministra()) == 0 &&
										eumen.getIntIdsubsucursaladministra().compareTo(socioE.getIntIdSubsucurAdministra()) == 0){
										
										listaO = boEnvioresumen.getListEnvRes(EMPRESA_USUARIO,
								    										  efectuadoTemp.getIntPeriodoPlanilla(),
								    										  intTipoSocio,
								    										  pIntModalidadCod,
								    										  pIntNivel,
								    										  pIntCodigo);
								    	if(listaO != null && !listaO.isEmpty())	
								    	{
								    		for(Envioresumen eo:listaO)
								    		{
								    			enviomonto.setEnvioresumen(new Envioresumen());
								    			enviomonto.setEnvioresumen(eo);
								    			enviomonto.setBlnTieneNuevoEnvioresumen(Boolean.FALSE);
								    			break;
								    		}
								    	}
								    	enviomonto.setBlnTieneNuevoEnvioresumen(Boolean.FALSE);
										efectuadoNoSocio.setEnvioMonto(enviomonto);
										efectuadoNoSocio.getEnvioMonto().getListaEnvioConcepto().get(0).setIntCuentaPk(cuentaIntegrante.getId().getIntCuenta());
										eumen.getListaEfectuado().add(efectuadoNoSocio);
										blnEstaEnLaMismaSucursalAdministra = Boolean.TRUE;
										break;
									}
								}							
					    	}
						}
						if(blnEstaEnLaMismaSucursalAdministra){
							efectuadoNoSocio.setBlnAgregarNoSocio(Boolean.TRUE);
							ec.setEfectuado(efectuadoNoSocio);
							ec.setYaEfectuado(new Efectuado());
							listaEfectuadoConceptoComp.add(ec); 
						}else{
							Envioresumen enre = new Envioresumen();
							enre.setId(new EnvioresumenId());
							enre.getId().setIntEmpresaPk(EMPRESA_USUARIO);
							enre.setIntDocumentogeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAENVIADA);
							enre.setIntPeriodoplanilla(efectuadoTemp.getIntPeriodoPlanilla());
							enre.setBdMontototal(enviomonto.getBdMontoenvio());							
							enre.setIntNumeroafectados(1);
							enre.setIntTiposocioCod(efectuadoTemp.getIntTiposocioCod());
							enre.setIntModalidadCod(efectuadoTemp.getIntModalidadCod());
							enre.setIntNivel(efectuadoTemp.getIntNivel());
							enre.setIntCodigo(efectuadoTemp.getIntCodigo());
							enre.setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
							enre.setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
							enre.setIntIdsucursaladministraPk(socioE.getIntIdSucursalAdministra());
							enre.setIntIdsubsucursaladministra(socioE.getIntIdSubsucurAdministra());
							enre.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							enviomonto.setBlnTieneNuevoEnvioresumen(Boolean.TRUE);
							enviomonto.setEnvioresumen(enre);
							efectuadoNoSocio.setEnvioMonto(enviomonto);
							
							EfectuadoResumen erNoSocio = new EfectuadoResumen();
							erNoSocio.getId().setIntEmpresa(EMPRESA_USUARIO);
							erNoSocio.setIntPeriodoPlanilla(efectuadoTemp.getIntPeriodoPlanilla());
							erNoSocio.setIntTiposocioCod(efectuadoTemp.getIntTiposocioCod());
							erNoSocio.setIntModalidadCod(efectuadoTemp.getIntModalidadCod());
							erNoSocio.setIntNivel(efectuadoTemp.getIntNivel());
							erNoSocio.setIntCodigo(efectuadoTemp.getIntCodigo());
							erNoSocio.setIntIdsucursaladministraPk(socioE.getIntIdSucursalAdministra());
							erNoSocio.setIntIdsubsucursaladministra(socioE.getIntIdSubsucurAdministra());
							erNoSocio.setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
							erNoSocio.setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
							erNoSocio.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							erNoSocio.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
							erNoSocio.setIntPersonausuarioPk(PERSONA_USUARIO);
							erNoSocio.setBdMontoTotal(efectuadoNoSocio.getBdMontoEfectuado());
							erNoSocio.setIntNumeroAfectados(1);
							erNoSocio.getListaEfectuado().add(efectuadoNoSocio);
							erNoSocio.setIntParaEstadoPago(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							planillaEfectuadaResumen.add(erNoSocio);
							
							efectuadoNoSocio.setBlnAgregarNoSocio(Boolean.TRUE);
							ec.setEfectuado(efectuadoNoSocio);
							ec.setYaEfectuado(new Efectuado());
							listaEfectuadoConceptoComp.add(ec); 
						}
					}										
				}				
								
				log.debug("agregarNoSocio FIN v1");
			}catch (Exception e) {
				log.error(e.getMessage(),e);
			}
	}
	
	public void newPersona(ActionEvent event)
	{	
		cargarUsuario();
		blnNewPersona = Boolean.FALSE;
		log.debug("newPersona INICIO");
		blnGrabarNuevaPersona = Boolean.FALSE;		
		
		socioSelecionado = new Socio();
		socioSelecionado.setId(new SocioPK());
		documentoSeleccionado = new Documento();	
		documentoSeleccionado.setId(new DocumentoPK());
		
		try
		{							
			registroPersona = (Persona)event.getComponent().getAttributes().get("item");			
			socioSelecionado.getId().setIntIdEmpresa(EMPRESA_USUARIO);
			socioSelecionado.getId().setIntIdPersona(efectuadoNoSocio.getPersona().getIntIdPersona());
			socioSelecionado.setStrApeMatSoc(registroPersona.getNatural().getStrApellidoMaterno());
			socioSelecionado.setStrApePatSoc(registroPersona.getNatural().getStrApellidoPaterno());
			socioSelecionado.setStrNombreSoc(registroPersona.getNatural().getStrNombres());
			documentoSeleccionado.setStrNumeroIdentidad(registroPersona.getDocumento().getStrNumeroIdentidad());
		
			log.debug("newPersona FIN");	
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void validarNuevaPersona()
	{
		log.debug("validarNuevaPersona INICIO");
		blnNewPersona = Boolean.FALSE;
		try{
			if(socioSelecionado.getStrApePatSoc().length() == 0)
			{
				mostrarMensaje(Boolean.FALSE, "Ingresar apellido paterno."); return;			
			}
			else if(socioSelecionado.getStrApeMatSoc().length() == 0)
			{
				mostrarMensaje(Boolean.FALSE, "Ingresar apellido materno."); return;			
			}
			else if(socioSelecionado.getStrNombreSoc().length() == 0)
			{
				mostrarMensaje(Boolean.FALSE, "Ingresar nombres."); return;			
			}
			else if(documentoSeleccionado.getStrNumeroIdentidad().length() == 0)
			{
				mostrarMensaje(Boolean.FALSE, "Ingresar DNI."); return;			
			}
			else
			{
				deshabilitarPanelInferior();
				blnNewPersona = Boolean.TRUE;
			}
			log.debug("validarNuevaPersona FIN");
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void grabarNuevaPersona()
	{
		listandoPersona = null;	
		Persona persona = null;
		try{
			listandoPersona = new ArrayList<Persona>();
									
			if(efectuadoNoSocio == null)
			{	
				log.debug("no hay nada");
				efectuadoNoSocio = new Efectuado();
				efectuadoNoSocio.setPersona(new Persona());
				efectuadoNoSocio.setDocumento(new Documento());
				efectuadoNoSocio.setBlnEfectuadoConcepto0(Boolean.FALSE);
				
				persona = new Persona();
				persona.setDocumento(new Documento());
				persona.getDocumento().setStrNumeroIdentidad(documentoSeleccionado.getStrNumeroIdentidad());
				persona.setNatural(new Natural());			
				persona.getNatural().setStrNombres(socioSelecionado.getStrNombreSoc());
				persona.getNatural().setStrApellidoPaterno(socioSelecionado.getStrApePatSoc());
				persona.getNatural().setStrApellidoMaterno(socioSelecionado.getStrApeMatSoc());
				listandoPersona.add(persona);				
				efectuadoNoSocio.setPersona(persona);
				efectuadoNoSocio.setDocumento(persona.getDocumento());
			}else
			{
				log.debug("hay algo en persona");
				
				if(efectuadoNoSocio.getPersona() != null)
				{
					efectuadoNoSocio.getPersona().getDocumento().setStrNumeroIdentidad(documentoSeleccionado.getStrNumeroIdentidad());
					efectuadoNoSocio.getPersona().getNatural().setStrNombres(socioSelecionado.getStrNombreSoc());
					efectuadoNoSocio.getPersona().getNatural().setStrApellidoPaterno(socioSelecionado.getStrApePatSoc());
					efectuadoNoSocio.getPersona().getNatural().setStrApellidoMaterno(socioSelecionado.getStrApeMatSoc());
					listandoPersona.add(efectuadoNoSocio.getPersona());
				}				
				log.debug("documento: "+efectuadoNoSocio.getDocumento());
			}
			
			blnGrabarNuevaPersona = Boolean.TRUE;
			blnNoEstaEnSocio = Boolean.FALSE;
			}catch (Exception e) {
				log.error(e.getMessage(),e);
			}
	}
	
	//--------NUEVA PERSONA FIN --------------------------------
	
	//--------RELACIONAR NO SOCIO FIN --------------------------------
	
	public void deshabilitarPanelInferior(){
		log.info("deshabilitarPanelInferior");
		habilitarGrabar 					= Boolean.FALSE;
		mostrarPanelInferior 				= Boolean.FALSE;
		mostrarPanelInferiorConArchivo  	= Boolean.FALSE;
		mostrarMensajeError 				= Boolean.FALSE;
		mostrarMensajeExito 				= Boolean.FALSE;
		datosValidadosConArchivo			= Boolean.FALSE;
		datosValidados2ConArchivo			= Boolean.FALSE;
		//listaEfectuadoConceptoComp = null;
		strExtensionConArchivo = null;
		archivo = null;
		strTipoSocioConArchivo = null;
	}
	
	//------- verAgregarPersona INICIO   -------------/
	public void verAgregarPersona(ActionEvent event)
	{
		log.debug("verAgregarPersona INICIO");
		//Si esta en persona y tambien en socio
		blnMontoNoSocio = Boolean.FALSE;
		Socio socio =null;
		socioSelecionado = new Socio();
		socioSelecionado.setId(new SocioPK());
		documentoSeleccionado = new Documento();
		try{			
			SocioFacadeRemote socioFacade 	  = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			registroPersona = (Persona)event.getComponent().getAttributes().get("item");
			
			SocioPK socioPK = new SocioPK();
			socioPK.setIntIdEmpresa(EMPRESA_USUARIO);
			socioPK.setIntIdPersona(registroPersona.getIntIdPersona());
			
			socio = socioFacade.getSocioPorPK(socioPK);
			
			if(socio != null)
			{
				socioSelecionado.setStrApePatSoc(socio.getStrApePatSoc());
				socioSelecionado.setStrApeMatSoc(socio.getStrApeMatSoc());
				socioSelecionado.setStrNombreSoc(socio.getStrNombreSoc());
				documentoSeleccionado.setStrNumeroIdentidad(registroPersona.getDocumento().getStrNumeroIdentidad());
			}
			log.debug("verAgregarPersona FIN");
			}catch (Exception e) {
				log.error(e.getMessage(),e);
			}
	}
	
	public void grabarAgregarPersona()
	{
		try{
			for(Persona persona: listandoPersona)
			{
				if(persona.getDocumento().getStrNumeroIdentidad().compareTo(registroPersona.getDocumento().getStrNumeroIdentidad())==0)
				{
					log.debug("entroooo");					
					persona.getDocumento().setStrNumeroIdentidad(documentoSeleccionado.getStrNumeroIdentidad());					
					persona.getNatural().setStrApellidoMaterno(socioSelecionado.getStrApeMatSoc());
					persona.getNatural().setStrApellidoPaterno(socioSelecionado.getStrApePatSoc());
					persona.getNatural().setStrNombres(socioSelecionado.getStrNombreSoc());				
					efectuadoNoSocio.getSocio().setStrApeMatSoc(socioSelecionado.getStrApeMatSoc());
					efectuadoNoSocio.getSocio().setStrApePatSoc(socioSelecionado.getStrApePatSoc());
					efectuadoNoSocio.getSocio().setStrNombreSoc(socioSelecionado.getStrNombreSoc());					
					blnGrabarNuevaPersona = Boolean.TRUE;
					blnEstaEnSocio = Boolean.TRUE;
					break;
				}
								
			}
			}catch (Exception e) {
				log.error(e.getMessage(),e);
			}
	}
	
	public void selecionarNuevaPersona(ActionEvent event)
	{
		registroPersona = null;
		try{
			blnMontoNoSocio = Boolean.TRUE;			
			registroPersona = (Persona)event.getComponent().getAttributes().get("item");
			}catch (Exception e) {
				log.error(e.getMessage(),e);
			}
	}
	
	//------- verAgregarPersona FIN   -------------/
	
	public Integer getNroEnviadoEmpezandoEfectuada()
	{
		Integer ret = 0;
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			
				if(item.getEfectuado().getEnvioMonto()!= null)
				{
					ret++;
				}			
		}
		return ret;
	}
	
	public Integer getNroEfectuadoEmpezandoEfectuada()
	{
		Integer ret = 0;
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			if(item.getEfectuado().getBdMontoEfectuado() != null)
			{
				ret++;
			}
		}
		return ret;
	}
	
	public BigDecimal getTotalEnviadoEmpezandoEfectuada()
	{
		 
		BigDecimal ret= new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();	
			if(item.getEfectuado() != null)
			{
				if(item.getEfectuado().getEnvioMonto() != null)
				{
					if(item.getEfectuado().getEnvioMonto().getBdMontoenvio() != null)
					{
						ret = ret.add(item.getEfectuado().getEnvioMonto().getBdMontoenvio());	
					}					
				}				
			}			
		}
		 
		return ret;
	}
	public BigDecimal getTotalEfectuadoEmpezandoEfectuada()
	{
		 
		BigDecimal ret= new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			if(item.getEfectuado() != null)
			{
				if(item.getEfectuado().getBdMontoEfectuado() != null)
				{
					ret = ret.add(item.getEfectuado().getBdMontoEfectuado());	
				}					
			}			
		}
	 
		return ret;
	}
	
	public BigDecimal getTotalDiferenciaEmpezandoEfectuada()
	{
		 
		BigDecimal ret= new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			if(item.getEfectuado() != null)
			{
				if(item.getEfectuado().getEnvioMonto() != null)
				{
					if(item.getEfectuado().getEnvioMonto().getBdMontoenvio() != null && item.getEfectuado().getBdMontoEfectuado() != null)
					{
						ret = ret.add(item.getEfectuado().getEnvioMonto().getBdMontoenvio().subtract(item.getEfectuado().getBdMontoEfectuado()));
					}					
				}				
			}			
		}
		 
		return ret;
	}
	
	public BigDecimal getTotalesAEnviarEnEmpezandoEfectuada()
	{
		BigDecimal ret = new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();	
			if(item.getEfectuado() != null)
			{
				if(item.getEfectuado().getEnvioMonto() != null )
				{
					if(item.getEfectuado().getEnvioMonto().getBdMontoenvio() != null)
					{
						ret = ret.add(item.getEfectuado().getEnvioMonto().getBdMontoenvio());	
						if(item.getYaEfectuado().getEnvioMonto() != null)
						{
							if(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio() != null)
							{
								ret = ret.add(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio());
							}							
						}
					}					
				}
				else
				{
					if(item.getYaEfectuado().getEnvioMonto() != null)
					{
						if(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio() != null)
						{
							ret = ret.add(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio());
						}						
					}
				}				
			}			
		}
		 
		return ret;
	}
	
	public BigDecimal getTotalesAEfectuarEnEmpezandoEfectuada()
	{
		BigDecimal ret = new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			if(item.getEfectuado() != null)
			{
				if(item.getEfectuado().getBdMontoEfectuado() != null)
				{
					ret = ret.add(item.getEfectuado().getBdMontoEfectuado());
				}				
			}			
		}
		 
		return ret;
	}
	
	/**
	 * grabar efectuado tanto con/sin archivo
	 */	
	
	public void grabar(){
		log.debug("grabarDeEfectuado INICIO V2");		
	    Boolean bValidar = true;	 
	    try
	    {	    	
	    	bValidar = EnvioValidador.validarMatenimiento(msgMantDeEfectuado,dtoDeEfectuado);
		    if(bValidar==true)
		    {
		    	Usuario lUsuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		    	PlanillaFacadeLocal localPlanilla = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
		   		    	
		    	if(planillaEfectuadaResumen != null && !planillaEfectuadaResumen.isEmpty())
		    	{	    		
		    		for(EfectuadoResumen eumen: planillaEfectuadaResumen)
			    	{			    		
			    		if(eumen.getListaEfectuado() != null && !eumen.getListaEfectuado().isEmpty())
			    		{		    			
			    			for(Efectuado ee: eumen.getListaEfectuado())
				    		{	    				
			    				if(!ee.getBlnEfectuadoConcepto0())
			    				{
			    					if(!ee.getBlnAgregarNoSocio())
						    		{					    				
						    			agregarListaEfectuadoConcepto(ee);				    								    			
						    		}
			    				}		    								    			
				    		}
			    			revisandoTotalEfectuado(eumen);
			    		}		    		
			    	}
		    	}		    	
		      	localPlanilla.grabarPlanillaEfectuada(planillaEfectuadaResumen, lUsuario);
		    	
		    	mostrarMensaje(Boolean.FALSE, "Los datos se grabaron");
		    	mostrarPanelInferior 			= Boolean.FALSE;
		    	mostrarPanelInferiorConArchivo	= Boolean.FALSE;
		    	habilitarGrabar 				= Boolean.FALSE;
		    	deshabilitarNuevo 				= Boolean.TRUE;
		    }
		    log.debug("grabarDeEfectuado FIN v2");  
		}    
	     catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);			
		}     
	    catch (EJBFactoryException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
		    e.printStackTrace();		    
		}catch (Exception e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
		    e.printStackTrace();		    
		}	    
	}
	private void revisandoTotalEfectuado(EfectuadoResumen eumen) {
		// TODO Auto-generated method stub
		Integer num = 0;
		eumen.setBdMontoTotal(new BigDecimal(0));
		eumen.setIntNumeroAfectados(0);
		if(eumen.getListaEfectuado() != null && !eumen.getListaEfectuado().isEmpty())
		{
			for (Efectuado efectuado : eumen.getListaEfectuado()) {
				eumen.setBdMontoTotal(eumen.getBdMontoTotal().add(efectuado.getBdMontoEfectuado()));
				num++;
			}
			eumen.setIntNumeroAfectados(num);
		}
	}

	/**
	 * Vuelvo a calcular los montosconceptos a la fecha de hoy.
	 * @param efectuado
	 */
	public void agregarListaEfectuadoConcepto(Efectuado efectuado)
	{
		log.debug("agregarListaEfectuadoConcepto INICIO");
		Boolean blnSeguir  = Boolean.FALSE;
		Boolean blnSeguirA = Boolean.FALSE;		
		Boolean blnSeguirI = Boolean.FALSE;	
		Boolean blnEncontro = Boolean.FALSE;
		Boolean blnEncontroAcumulativo = Boolean.FALSE;
		BigDecimal bdMontoMora  = new BigDecimal(0);
		BigDecimal bdMontoInteres = new BigDecimal(0);
		BigDecimal bdTotalMontoMora  = new BigDecimal(0);
		BigDecimal totalMontoConcepto = new BigDecimal(0);				
		BigDecimal bdMontoEfectuado = new BigDecimal(0);
		List<InteresCancelado> listaInteresCancelado = null;
		List<EstadoCredito> listaEstadoCredito = null; 		
		Timestamp tsFechaFin = null;
		Timestamp tsFechaInicio = null; 
		
		List<EfectuadoConcepto> listaEfectuadoConcepto = new ArrayList<EfectuadoConcepto>();
		try
		{			
			ConceptoFacadeRemote remoteConcepto 	= (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			SolicitudPrestamoFacadeRemote facadeSolPres = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			PrioridadDescuentoFacadeRemote   prioriadadFacade   = (PrioridadDescuentoFacadeRemote)EJBFactory.getRemote(PrioridadDescuentoFacadeRemote.class);
			
			CuentaId o = new CuentaId();
			o.setIntPersEmpresaPk(efectuado.getId().getIntEmpresacuentaPk());
			o.setIntCuenta(efectuado.getIntCuentaPk());			
			List<CuentaConcepto> listaCuentaConcepto =  remoteConcepto.getListaCuentaConceptoPorPkCuenta(o);
			if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty())
			{				
				log.debug("TIENE LISTA CUENTACONCEPTO");
				for(CuentaConcepto cu :listaCuentaConcepto)
				{
					blnSeguir = Boolean.FALSE;
					List<BloqueoCuenta> listaBloqueo = remoteConcepto.getListaBloqueoCuentaPorNroCuenta(efectuado.getId().getIntEmpresacuentaPk(),
																	 								    efectuado.getIntCuentaPk());
					if(listaBloqueo != null && !listaBloqueo.isEmpty())
					{
						for(BloqueoCuenta bl:listaBloqueo)
						{
							if(bl.getIntItemCuentaConcepto() != null)
							{
								if(cu.getId().getIntItemCuentaConcepto().compareTo(bl.getIntItemCuentaConcepto())==0)
								{
									Calendar calendario	 	 = GregorianCalendar.getInstance();
									Date fechaActual		 = calendario.getTime();
									if(bl.getTsFechaInicio() != null && bl.getTsFechaFin() != null)
									{									
										Date dInicio = new Date(bl.getTsFechaInicio().getTime());
										Date dFin = new Date(bl.getTsFechaFin().getTime());
										
										if (UtilCobranza.esDentroRangoFechas(dInicio, dFin,	fechaActual))
										{
											blnSeguir = Boolean.TRUE;
										}	
									}
									else if(bl.getTsFechaInicio() != null)
									{
										Date dInicio = new Date(bl.getTsFechaInicio().getTime());
										
											if(UtilCobranza.esMayorRangoFechas(dInicio, fechaActual))
											{
												blnSeguir = Boolean.TRUE;
											}										
									}
									else if(bl.getTsFechaFin() != null)
									{
										Date dFin = new Date(bl.getTsFechaFin().getTime());
										if(UtilCobranza.esMenorRangoFechas(dFin, fechaActual))
										{
											blnSeguir = Boolean.TRUE;
										}
									}							
								}
							}							
						}
					}
					log.debug("blnSeguir: "+blnSeguir);
					//if(!blnSeguir)
					//{						
						List<CuentaConceptoDetalle> listadetalle = remoteConcepto.getCuentaConceptoDetofCobranza(cu.getId());
						
						if(listadetalle!= null && !listadetalle.isEmpty())
						{
							log.debug("tiene lista cuentaconceptodetalle");
							totalMontoConcepto = new BigDecimal(0);
							for(CuentaConceptoDetalle det: listadetalle)
							{							
								log.debug("--cuentaconceptoDetalle: "+det);
								Integer intConceptoGeneral = 0;
								blnEncontro = Boolean.FALSE;
							    blnEncontroAcumulativo = Boolean.FALSE;
								EfectuadoConcepto efectuadoConcepto = new EfectuadoConcepto();
								efectuadoConcepto.getId().setIntEmpresaCuentaEnvioPk(efectuado.getId().getIntEmpresacuentaPk());
								efectuadoConcepto.setIntItemCuentaConcepto(det.getId().getIntItemCuentaConcepto());								
								efectuadoConcepto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
															
									CaptacionId captacionId = new CaptacionId();
			    					captacionId.setIntItem(det.getIntItemConcepto());
			    					captacionId.setIntParaTipoCaptacionCod(det.getIntParaTipoConceptoCod());
			    					captacionId.setIntPersEmpresaPk(det.getId().getIntPersEmpresaPk());
			    					
			    					   if (Constante.PARA_TIPO_CONCEPTO_MANTENIMIENTO.compareTo(det.getIntParaTipoConceptoCod()) == 0){
			    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO;
			    					   }
			    					   else
			    					   if (Constante.PARA_TIPO_CONCEPTO_RETIRO.compareTo(det.getIntParaTipoConceptoCod()) == 0){
			    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO;	 
			    					   }
			    					   else
			    					   if (Constante.PARA_TIPO_CONCEPTO_SEPELIO.compareTo(det.getIntParaTipoConceptoCod()) == 0){
			    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO;	 
			    					   }
			    					   else if(Constante.PARA_TIPO_CONCEPTO_APORTE.compareTo(det.getIntParaTipoConceptoCod())== 0)
			    					   {
			    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION;
			    					   }
			    					   efectuadoConcepto.setIntTipoConceptoGeneralCod(intConceptoGeneral);
			    					   
			    						PrioridadDescuento prioriadDscto =  prioriadadFacade.obtenerOrdenPrioridadDescuento(efectuado.getId().getIntEmpresacuentaPk(),
			    					 																						intConceptoGeneral,
			    					 																						captacionId,
			    					 																						null);
			    					   if (prioriadDscto != null)
			    					   {			    								    						   
			    						 efectuadoConcepto.setIntOrdenPrioridad(prioriadDscto.getIntPrdeOrdenprioridad());
			    					   }    					   
								
			    					   if(Constante.PARAM_T_TIPODSCTOAPORT_CANCELATORIO.compareTo(det.getIntParaTipoDescuentoCod()) == 0)
										{																	
											List<ConceptoPago> listaConceptoPago = remoteConcepto.getListaConceptoPagoPorCuentaConceptoDet(det.getId());
											if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
											{
												log.debug("tiene listaConceptoPago");
												for(ConceptoPago oo: listaConceptoPago)
												{
													if(oo.getIntPeriodo().compareTo(efectuado.getIntPeriodoPlanilla()) == 0)
													{												
														totalMontoConcepto =   oo.getBdMontoSaldo();
														blnEncontro = Boolean.TRUE;
														break;
													}
												}
												if(!blnEncontro)
												{
													totalMontoConcepto =  det.getBdMontoConcepto(); 
												}
											}else
											{
												totalMontoConcepto =  det.getBdMontoConcepto(); 
											}									
											if(!blnSeguir)
											{
												if(det.getTsInicio() != null && det.getTsInicio().compareTo(UtilCobranza.obtieneFechaActualEnTimesTamp())== -1){
													if(det.getTsFin() != null){
														if(det.getTsFin().compareTo(UtilCobranza.obtieneFechaActualEnTimesTamp()) == 1){
															efectuadoConcepto.setBdMontoConcepto(totalMontoConcepto);
														}else{
															//efectuadoConcepto.setBdMontoConcepto(new BigDecimal(0));
															break;
														}														
													}else{
														efectuadoConcepto.setBdMontoConcepto(totalMontoConcepto);
													}
												}
												
											}else{
												efectuadoConcepto.setBdMontoConcepto(new BigDecimal(0));
											}
											
										}
										else if(Constante.PARAM_T_TIPODSCTOAPORT_ACUMULATIVO.compareTo(det.getIntParaTipoDescuentoCod()) == 0)
										{									
											List<ConceptoPago> listaConceptoPago = remoteConcepto.getListaConceptoPagoPorCuentaConceptoDet(det.getId());
											
											if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
											{
												BigDecimal bdSumaPorPeriodo = BigDecimal.ZERO;
												for (ConceptoPago conceptoPago : listaConceptoPago)
												{												
													if(conceptoPago.getIntPeriodo().compareTo(efectuado.getIntPeriodoPlanilla()) == -1)
													{
														bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());
													}else if(conceptoPago.getIntPeriodo().compareTo(efectuado.getIntPeriodoPlanilla()) == 0)
													{
														bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());
														blnEncontroAcumulativo = Boolean.TRUE;
													}
												}
												if(!blnEncontroAcumulativo)
												{
													bdSumaPorPeriodo = bdSumaPorPeriodo.add(det.getBdMontoConcepto());
												}
												totalMontoConcepto =	bdSumaPorPeriodo;									
											}
											else
											{										
												totalMontoConcepto =  det.getBdMontoConcepto(); 
											}
											if(!blnSeguir)
											{	if(det.getTsInicio() != null){
													if(det.getTsInicio().compareTo(UtilCobranza.obtieneFechaActualEnTimesTamp()) == -1){
														if(det.getTsFin() != null){
															if(det.getTsFin().compareTo(UtilCobranza.obtieneFechaActualEnTimesTamp())== 1){
																efectuadoConcepto.setBdMontoConcepto(totalMontoConcepto);
															}else{
																//efectuadoConcepto.setBdMontoConcepto(new BigDecimal(0));
																break;
															}
														}else{
															efectuadoConcepto.setBdMontoConcepto(totalMontoConcepto);	
														}
															
													}else{
														//efectuadoConcepto.setBdMontoConcepto(new BigDecimal(0));
														break;
													}
												}							
												
											}else{
												efectuadoConcepto.setBdMontoConcepto(new BigDecimal(0));
											}											
										}
			    					   listaEfectuadoConcepto.add(efectuadoConcepto);			    					   
							}
							log.debug("canti de lista efectuadoconcepto: "+listaEfectuadoConcepto.size());
						}else
						{
							log.debug("cuentaconceptodetalle null"+cu.getId().getIntItemCuentaConcepto());
						}									
				}
			}
			/**
			 * Expediente			 
			 */
			List<Expediente> listaExpedientes = remoteConcepto.getListaExpedienteConSaldoPorEmpresaYcuenta(efectuado.getId().getIntEmpresacuentaPk(),
											    			    			   							   efectuado.getIntCuentaPk());
													
			if(listaExpedientes != null && !listaExpedientes.isEmpty())
			{
				log.debug("TIENE LISTA");
				listaExpediente = new ArrayList<Expediente>();
				for(Expediente ex: listaExpedientes)
				{					
					List<BloqueoCuenta> listaBloqueoCuenta = remoteConcepto.getListaBloqueoCuentaPorNroCuenta(ex.getId().getIntPersEmpresaPk(), 
					 						   									 							  ex.getId().getIntCuentaPk());
					if(listaBloqueoCuenta!= null && !listaBloqueoCuenta.isEmpty())
					{
						for(BloqueoCuenta b: listaBloqueoCuenta)
						{
							if(b.getIntParaTipoConceptoCre() != null)
							{
								if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION
										.compareTo(b.getIntParaTipoConceptoCre()) == 0)
								{
									Calendar calendario	 	 = GregorianCalendar.getInstance();
									Date fechaActual		 = calendario.getTime();
									if(b.getTsFechaInicio() != null && b.getTsFechaFin() != null)
									{									
										Date dInicio = new Date(b.getTsFechaInicio().getTime());
										Date dFin = new Date(b.getTsFechaFin().getTime());
										
										if (UtilCobranza.esDentroRangoFechas(dInicio, dFin,	fechaActual))
										{
											blnSeguirA = Boolean.TRUE;
										}	
									}
									else if(b.getTsFechaInicio() != null)
									{
										Date dInicio = new Date(b.getTsFechaInicio().getTime());
										
											if(UtilCobranza.esMayorRangoFechas(dInicio, fechaActual))
											{
												blnSeguirA = Boolean.TRUE;
											}										
									}
									else if(b.getTsFechaFin() != null)
									{
										Date dFin = new Date(b.getTsFechaFin().getTime());
										if(UtilCobranza.esMenorRangoFechas(dFin, fechaActual))
										{
											blnSeguirA = Boolean.TRUE;
										}
									}									
								}
								else if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES
										.compareTo(b.getIntParaTipoConceptoCre()) == 0)
								{
									Calendar calendario	 	 = GregorianCalendar.getInstance();
									Date fechaActual		 = calendario.getTime();
									if(b.getTsFechaInicio() != null && b.getTsFechaFin() != null)
									{									
										Date dInicio = new Date(b.getTsFechaInicio().getTime());
										Date dFin = new Date(b.getTsFechaFin().getTime());
										
										if (UtilCobranza.esDentroRangoFechas(dInicio, dFin,	fechaActual))
										{
											blnSeguirI = Boolean.TRUE;
										}	
									}
									else if(b.getTsFechaInicio() != null)
									{
										Date dInicio = new Date(b.getTsFechaInicio().getTime());
										
											if(UtilCobranza.esMayorRangoFechas(dInicio, fechaActual))
											{
												blnSeguirI = Boolean.TRUE;
											}										
									}
									else if(b.getTsFechaFin() != null)
									{
										Date dFin = new Date(b.getTsFechaFin().getTime());
										if(UtilCobranza.esMenorRangoFechas(dFin, fechaActual))
										{
											blnSeguirI = Boolean.TRUE;
										}
									}
								}
							}
						}
						
					}
						
						List<Cronograma> listaCronograma = remoteConcepto.getListaCronogramaPorPkExpediente(ex.getId());
					 						
							bdMontoMora 	 = new BigDecimal(0);
							bdTotalMontoMora = new BigDecimal(0);
							if(listaCronograma != null && !listaCronograma.isEmpty())
							{
								log.debug("TIENE LISTA");
								for(Cronograma c:listaCronograma)
								{									
									if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION
											.compareTo(c.getIntParaTipoConceptoCreditoCod()) == 0)
									{										
										if(efectuado.getIntPeriodoPlanilla() >= c.getIntPeriodoPlanilla())
										{
											log.debug("periodo: "+c.getIntPeriodoPlanilla());
											if(!blnSeguirA)
											{
												bdMontoMora = bdMontoMora.add(c.getBdSaldoDetalleCredito());												
											}						
										}																				
									}
								}								
								log.debug("bdMontoMora: "+bdMontoMora);
								ex.setBdTotalAmortizacion(bdMontoMora);
								bdTotalMontoMora = bdTotalMontoMora.add(bdMontoMora);
								
								if(tsFechaFin == null)
								{																	
									tsFechaFin = UtilCobranza.obtieneFechaActualEnTimesTamp();									
								}
							}
								EfectuadoConcepto efectuadoConceptA = new EfectuadoConcepto();
								efectuadoConceptA.getId().setIntEmpresaCuentaEnvioPk(efectuado.getId().getIntEmpresacuentaPk());								
								efectuadoConceptA.setIntItemExpediente(ex.getId().getIntItemExpediente());
								efectuadoConceptA.setIntItemDetExpediente(ex.getId().getIntItemExpedienteDetalle());
								efectuadoConceptA.setIntTipoConceptoGeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);
								if(!blnSeguirA)
								{
									efectuadoConceptA.setBdMontoConcepto(bdMontoMora);
								}else {
									efectuadoConceptA.setBdMontoConcepto(new BigDecimal(0));
								}
								
								efectuadoConceptA.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
																								
								CreditoId creditoIdA = new CreditoId();
				     			creditoIdA.setIntItemCredito(ex.getIntItemCredito());
				     			creditoIdA.setIntParaTipoCreditoCod(ex.getIntParaTipoCreditoCod());
				     			creditoIdA.setIntPersEmpresaPk(ex.getIntPersEmpresaCreditoPk());
				     			 
				     			PrioridadDescuento prioriadDsctoA =  prioriadadFacade.obtenerOrdenPrioridadDescuento(ex.getIntPersEmpresaCreditoPk(),
													     														   Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION,
													     														   null,
													     														   creditoIdA);
				     			if (prioriadDsctoA != null){				     			 
				     				efectuadoConceptA.setIntOrdenPrioridad(prioriadDsctoA.getIntPrdeOrdenprioridad());
				     			}
								listaEfectuadoConcepto.add(efectuadoConceptA);
						 
						if(!blnSeguirI)
						{						
								if(ex.getBdPorcentajeInteres()!= null)
								{
									if(ex.getBdPorcentajeInteres().compareTo(new BigDecimal(0))==1)
									{									
										ExpedienteId icId = new ExpedienteId();
										icId.setIntCuentaPk(ex.getId().getIntCuentaPk());
										icId.setIntPersEmpresaPk(ex.getId().getIntPersEmpresaPk());
										icId.setIntItemExpediente(ex.getId().getIntItemExpediente());
										icId.setIntItemExpedienteDetalle(ex.getId().getIntItemExpedienteDetalle());
										
										listaInteresCancelado =  remoteConcepto.getListaInteresCanceladoPorExpedienteCredito(icId);
										
										if(listaInteresCancelado != null && !listaInteresCancelado.isEmpty())
										{
											for(InteresCancelado interesCancelado: listaInteresCancelado)
											{
												tsFechaInicio = interesCancelado.getTsFechaMovimiento();
												//sumo un dia
												tsFechaInicio = UtilCobranza.sumarUnDiaAFecha(tsFechaInicio);
											//	log.debug("fechaINicio="+tsFechaInicio);
												break;
											}											
										}
										else
										{
											ExpedienteCreditoId pId = new ExpedienteCreditoId();
											pId.setIntPersEmpresaPk(ex.getId().getIntPersEmpresaPk());
											pId.setIntCuentaPk(ex.getId().getIntCuentaPk());
											pId.setIntItemExpediente(ex.getId().getIntItemExpediente());
											pId.setIntItemDetExpediente(ex.getId().getIntItemExpedienteDetalle());
											
											listaEstadoCredito = facadeSolPres.getListaEstadosPorExpedienteCreditoId(pId);
											if(listaEstadoCredito != null && !listaEstadoCredito.isEmpty())
											{												
												for(EstadoCredito estadoCredito:listaEstadoCredito)
												{
													if(estadoCredito.getIntParaEstadoCreditoCod()
															.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO) == 0)
													{
														tsFechaInicio =  estadoCredito.getTsFechaEstado();													
														break;
													}
												}
											}
										}
										
										if(tsFechaInicio != null && tsFechaFin != null)
										{											
											java.sql.Date      dateFechaInicio      = new           java.sql.Date(tsFechaInicio.getTime()); 
											java.sql.Date      dateFechaFin         = new           java.sql.Date(tsFechaFin.getTime());
											int intNumerosDias = UtilCobranza.obtenerDiasEntreFechas(dateFechaInicio, dateFechaFin);
									        log.debug("intNumerosDias: "+intNumerosDias);
											
											bdMontoInteres = new BigDecimal(0);											
											bdMontoInteres = ex.getBdPorcentajeInteres()
															 .multiply(ex.getBdSaldoCredito())
															 .multiply(new BigDecimal(intNumerosDias));
											bdMontoInteres = bdMontoInteres.divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
																						
											EfectuadoConcepto efectuadoConceptI = new EfectuadoConcepto();
											efectuadoConceptI.getId().setIntEmpresaCuentaEnvioPk(efectuado.getId().getIntEmpresacuentaPk());								
											efectuadoConceptI.setIntItemExpediente(ex.getId().getIntItemExpediente());
											efectuadoConceptI.setIntItemDetExpediente(ex.getId().getIntItemExpedienteDetalle());
											efectuadoConceptI.setIntTipoConceptoGeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES);
											if(!blnSeguirI)
											{
												efectuadoConceptI.setBdMontoConcepto(bdMontoInteres);
											}else{
												efectuadoConceptI.setBdMontoConcepto(new BigDecimal(0));
											}											
											efectuadoConceptI.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);											
											
											CreditoId creditoId = new CreditoId();
							     			creditoId.setIntItemCredito(ex.getIntItemCredito());
							     			creditoId.setIntParaTipoCreditoCod(ex.getIntParaTipoCreditoCod());
							     			creditoId.setIntPersEmpresaPk(ex.getIntPersEmpresaCreditoPk());
							     			 
							     			PrioridadDescuento prioriadDscto =  prioriadadFacade.obtenerOrdenPrioridadDescuento(ex.getIntPersEmpresaCreditoPk(),
																					   										   Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES,
																					   										   null,
																					   										   creditoId);
							     			if (prioriadDscto != null)						     				 
							     			{
							     				efectuadoConceptI.setIntOrdenPrioridad(prioriadDscto.getIntPrdeOrdenprioridad());
							     			} 						     			
							     			//efectuado.getListaEfectuadoConcepto().add(efectuadoConceptI);
							     			listaEfectuadoConcepto.add(efectuadoConceptI);
										}										
									}																		
							  }						
						}						
					}
			}
			//log.debug("cantidad en listaefectuadoConcepto antes de ordenar:"+listaEfectuadoConcepto.size());
			Collections.sort(listaEfectuadoConcepto,
					new Comparator() {  
	            public int compare(Object o1, Object o2) {  
	            	
	            	EfectuadoConcepto e1 = (EfectuadoConcepto)o1;
	            	EfectuadoConcepto e2 = (EfectuadoConcepto)o2;
	            	
	                if (e1.getIntOrdenPrioridad() == null)
	                {	
	                	e1.setIntOrdenPrioridad(88);
	                }
	                
	                if (e2.getIntOrdenPrioridad() == null)
	                {	
	                	e2.setIntOrdenPrioridad(88);
	                }
	                return e1.getIntOrdenPrioridad().compareTo(e2.getIntOrdenPrioridad());
	            }  
	        });
			//log.debug("cantidad en listaefectuadoConcepto despues de ordenar:"+listaEfectuadoConcepto.size());
			bdMontoEfectuado = efectuado.getBdMontoEfectuado();
			log.debug("*****bdMontoEfectuado:"+bdMontoEfectuado);
			efectuado.setListaEfectuadoConcepto(new ArrayList<EfectuadoConcepto>());
			for(EfectuadoConcepto ec:listaEfectuadoConcepto)
			{
				log.debug("EFECTUADO: "+ec);
				if(ec.getIntOrdenPrioridad() != null)
				{					
					if((ec.getBdMontoConcepto().compareTo(new BigDecimal(0))== 1
						|| ec.getBdMontoConcepto().compareTo(new BigDecimal(0))== 0)	
					   && bdMontoEfectuado.compareTo(ec.getBdMontoConcepto()) == 1)
					{
						bdMontoEfectuado = bdMontoEfectuado.subtract(ec.getBdMontoConcepto());						
					    efectuado.getListaEfectuadoConcepto().add(ec);					
					}
					else if((ec.getBdMontoConcepto().compareTo(new BigDecimal(0))== 1
							|| ec.getBdMontoConcepto().compareTo(new BigDecimal(0))== 0) 
							&& bdMontoEfectuado.compareTo(ec.getBdMontoConcepto()) == -1)
					{						
						ec.setBdMontoConcepto(bdMontoEfectuado);						
						bdMontoEfectuado = new BigDecimal(0);
						efectuado.getListaEfectuadoConcepto().add(ec);
						efectuado.setBlnListaEnvioConcepto(Boolean.TRUE);						
					}
					else if((ec.getBdMontoConcepto().compareTo(new BigDecimal(0))== 1
							|| ec.getBdMontoConcepto().compareTo(new BigDecimal(0))== 0) 
							&& bdMontoEfectuado.compareTo(ec.getBdMontoConcepto()) == 0)
					{
						bdMontoEfectuado = bdMontoEfectuado.subtract(ec.getBdMontoConcepto());
						efectuado.getListaEfectuadoConcepto().add(ec);
						efectuado.setBlnListaEnvioConcepto(Boolean.TRUE);						
					}else if(ec.getBdMontoConcepto().compareTo(new BigDecimal(0)) == 0)
					{
						efectuado.getListaEfectuadoConcepto().add(ec);
						efectuado.setBlnListaEnvioConcepto(Boolean.TRUE);
					}
				}
			}			
			log.debug("agregarListaEfectuadoConcepto FIN");
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	public void manejarSubirArchivo(UploadEvent event){
		log.debug("-------  --manejarSubirArchivo---------");
		ocultarMensaje();
		blnExcel = Boolean.FALSE;
		blnTexto = Boolean.FALSE;
		if(listaMensajes != null && !listaMensajes.isEmpty())
		{
			listaMensajes.clear();
		}
		try {
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			uploadItem = event.getUploadItem();
			String nombreArchivo = event.getUploadItem().getFileName();
			int idTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_EFECTUADO;	
			tipoArchivo = generalFacade.getTipoArchivoPorPk(idTipoArchivo);			
			target = tipoArchivo.getStrRuta() + "\\" + FileUtil.subirArchivo(event, tipoArchivo.getIntParaTipoCod());
			archivo.setStrNombrearchivo(nombreArchivo);
			
			//iendonos a la lectura bien si es txt o si es xls
			strExtensionConArchivo = archivo.getStrNombrearchivo().substring(archivo.getStrNombrearchivo().length()-3 ,archivo.getStrNombrearchivo().length());
						
			if(strExtensionConArchivo != null)
			{
				if(strExtensionConArchivo.compareTo("txt") == 0)
				{
					leendoAlgoTXT(target);
					blnTexto = Boolean.TRUE;
				}
				else if(strExtensionConArchivo.compareTo("xls") == 0)
				{
					leendoAlgoDelXLS(target);
					blnExcel = Boolean.TRUE;
				}			
			}
			
		}catch(PatternSyntaxException e){
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}		
	}
	
	public void leendoAlgoDelXLS(String target)
	{
		Integer intTipoSocio = 0;
		Integer intModalidad = 0;
		Integer intNivelUE 	 = 0;
		Integer intCodigoUE  = 0;
		Integer intCuenta	 = 0;
		Integer intPeriodo	 = 0;
		listaEfectuadoConceptoComp = null;
		listaEfectuadoConceptoComp = new ArrayList<EfectuadoConceptoComp>();		
		EfectuadoConceptoComp efectuadoConArchivo = null;
		try
		{
			FileInputStream file = new FileInputStream(new File(target));			
			HSSFWorkbook workbook = new HSSFWorkbook(file);			
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			//sucursal
			HSSFRow row1 = sheet.getRow(0);
			HSSFCell cellb1 = row1.getCell(1);
			String strSucursal = cellb1.getStringCellValue();
			dtoDeEfectuadoConArchivo.setJuridicaSucursal(new Juridica());
			dtoDeEfectuadoConArchivo.getJuridicaSucursal().setStrRazonSocial(strSucursal);
			log.debug("strSucursal: "+strSucursal);
			
			//U.E
			HSSFRow row2 = sheet.getRow(1);
			HSSFCell cellb2 = row2.getCell(1);
			String strUE = cellb2.getStringCellValue();
			dtoDeEfectuadoConArchivo.getEstructura().getJuridica().setStrRazonSocial(strUE);
			log.debug("strUE: "+strUE);
			
			//Modalidad
			HSSFCell celld4 = row2.getCell(3);
			String strModalidad = celld4.getStringCellValue();
			dtoDeEfectuadoConArchivo.setStrModalidad(strModalidad);
			log.debug("strModalidad: "+strModalidad);
			dtoDeEfectuado.setStrModalidad(strModalidad);
			if(strModalidad.compareTo("Haberes")==0)
			{
				intModalidad = 1;
			}
			else if(strModalidad.compareTo("Incentivo")==0)
			{
				intModalidad = 2;
			}
			else if(strModalidad.compareTo("Cas")==0)
			{
				intEfectuado = Constante.intCesanteCAS;
				intModalidad = 3;
			}
			
			//Periodo
			HSSFRow row3 = sheet.getRow(2);
			HSSFCell cell1b2 = row3.getCell(1);
			String strNivelCodigo = cell1b2.getStringCellValue();			
			
			intNivelUE = Integer.parseInt(strNivelCodigo.substring(0, 2));
			
			
			intCodigoUE = Integer.parseInt(strNivelCodigo.substring(2, strNivelCodigo.length()));
			
			
			HSSFCell cell2b4= row3.getCell(3);
			cell2b4.setCellType(Cell.CELL_TYPE_STRING);
			String strPeriodo = cell2b4.getStringCellValue();
			intPeriodo = Integer.parseInt(strPeriodo);
			
			//dtoDeEfectuado.intPeriodoMes
			Integer intmmes= Integer.parseInt(strPeriodo.substring(4,6));
			
			Integer intanio = Integer.parseInt(strPeriodo.substring(0,4));
			
			dtoDeEfectuadoConArchivo.setIntPeriodoMes(intmmes);
			dtoDeEfectuadoConArchivo.setIntPeriodoAnio(intanio);
			
			//Tipo Socio
			HSSFRow row5 = sheet.getRow(4);
			HSSFCell cellE5= row5.getCell(4);
			
			String strTipoSocio = cellE5.getStringCellValue();
			if(strTipoSocio.compareTo("A")==0)
			{
				strTipoSocio = "ACTIVO";
				intTipoSocio = 1;
				strTipoSocioConArchivo="Activo";				
			}
			else if(strTipoSocio.compareTo("C")==0)
			{
				intTipoSocio = 2;
				strTipoSocio = "CESANTE";
				intEfectuado = Constante.intCesanteCAS;
				strTipoSocioConArchivo = "Cesante";
			}
			dtoDeEfectuadoConArchivo.setStrTipoSocio(strTipoSocio);
			log.debug("strtiposocio: "+strTipoSocio);			
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	

	public void validarConArchivo()
	{		
		ocultarMensaje();
		try
		{
			log.debug("que es: "+strExtensionConArchivo);
			if(strExtensionConArchivo != null)
			{
				if(strExtensionConArchivo.compareTo("txt") == 0)
				{
					leendoTXT(target);
				}
				else if(strExtensionConArchivo.compareTo("xls") == 0)
				{
					leendoXLS(target);
				}				
			}			
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	/**
	 * Validaciones lista socios que no tenga sucursal o que no tengan unidad ejecutora
	 * 				periodo mes a realizarse
	 * @param target
	 */
	
	public void leendoAlgoTXT(String target)
	{
		log.debug("leendoAlgoTXT INICIO v3");
		cargarUsuario();
		Integer intMes = 0;
		Integer intAnio = 0;
		estructura = null;
		perJuridicaSucursal = null;
		perJuridicaUnidadEjecutora = null;
		listaMensajes = null;
		listaMensajes = new ArrayList<EnvioMsg>();
		EnvioMsg envioMsg = null;
		listaSucursal = null;
		listaUEjecutora = null;
		listaSucursal = new ArrayList<Juridica>();		
		listaUEjecutora = new ArrayList<Juridica>();
		blnErrorConArchivo = Boolean.FALSE;
		listaMensajesSinSucursal = null;
		listaMensajesConSucursaleUE = null;
		listaJuridicaSucursal = null;
		listaJuridicaUnidadEjecutora = null;
		listaJuridicaSucursal = new ArrayList<Juridica>();
		listaJuridicaUnidadEjecutora = new ArrayList<Juridica>();
		listaMensajesSinSucursal = new ArrayList<EnvioMsg>();
		listaMensajesConSucursaleUE = new ArrayList<EnvioMsg>();
		intNroSociosConArchivo = 0;
		bdMontoTotalConArchivo = new BigDecimal(0);
		try
		{	
			PersonaFacadeRemote remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			File file = new File(target);
			File fileConSucursalAndUE = new File(target); 
			FileReader frSinSucursalNiUE = new FileReader(file); //no tienen sucursal ni unidad ejecutora	
			FileReader frConSucursalAndUE = new FileReader(fileConSucursalAndUE);
			BufferedReader brSinSucursalNiUE = new BufferedReader(frSinSucursalNiUE);
			BufferedReader brConSucursalAndUE = new BufferedReader(frConSucursalAndUE);
			String dataConSucursalAndUE = null;
			String dataSinSucursalNiUE = null;
			dtoDeEfectuadoConArchivo.setStrModalidad("Haberes");			
			//Scanner in = new Scanner(frSinSucursalNiUE);
			//while que retorna un listado en caso tenga socios sin sucursal o sin UnidadEjecutora 
			while ((dataSinSucursalNiUE = brSinSucursalNiUE.readLine()) != null)
			{
				if(dataSinSucursalNiUE != null && dataSinSucursalNiUE.length() > 80)
				{
					String strProg     = dataSinSucursalNiUE.substring(70, 75);					
					 String strCodEst   = dataSinSucursalNiUE.substring(75,84);				 
					 String nombreSocio = dataSinSucursalNiUE.substring(16,61).trim();
					 List<Juridica> listaJuridica = remotePersona.listaJuridicaWithFile(strProg,
																						strCodEst); 
					if(listaJuridica.size() < 2)						
					{
						log.debug( "menor a 2 "+nombreSocio+ ", strProg: "+strProg+
						", strCodEst: "+strCodEst+ "NO ENCUENTRA SUCURSAL.");
						
						mensajeConArchivo = nombreSocio+ ", strProg: "+
											strProg+ ", strCodEst: "+
											strCodEst+ " NO ENCUENTRA SUCURSAL.";
						blnErrorConArchivo = Boolean.TRUE;
						
						envioMsg = new EnvioMsg();
						envioMsg.setStrMsg(mensajeConArchivo);
						listaMensajesSinSucursal.add(envioMsg);
						continue; 
						
					}else if(listaJuridica.size() == 2)
					{
						mensajeConArchivo = null;
						mensajeConArchivo = nombreSocio+ 
											", Sucursal: "+listaJuridica.get(0).getStrRazonSocial()+
											", Unidad Ejecutora: "+listaJuridica.get(1).getStrRazonSocial()+".";
						envioMsg = new EnvioMsg();
						envioMsg.setStrMsg(mensajeConArchivo);
						listaMensajesConSucursaleUE.add(envioMsg);
						//listaIntSucursal.add(listaJuridica.get(0).getIntIdPersona());
						Juridica juSucursal  = new Juridica();
						juSucursal.setIntIdPersona(listaJuridica.get(0).getIntIdPersona());
						juSucursal.setStrRazonSocial(listaJuridica.get(0).getStrRazonSocial());
						listaJuridicaSucursal.add(juSucursal);
						
						Juridica juUE  = new Juridica();
						juUE.setIntIdPersona(listaJuridica.get(1).getIntIdPersona());
						juUE.setStrRazonSocial(listaJuridica.get(1).getStrRazonSocial());
						listaJuridicaUnidadEjecutora.add(juUE);
						//listaIntUnidadEjecutora.add(listaJuridica.get(1).getIntIdPersona());
						continue; 
					}
				}				 
			}				
			
			//while que pinta periodomes y periodoAnio
			while((dataConSucursalAndUE = brConSucursalAndUE.readLine())!= null)
			{
				log.debug("leendo");
				if(dataConSucursalAndUE != null && dataConSucursalAndUE.length() >80)
				{
					String strMes = dataConSucursalAndUE.substring(4,6);
					String strAnio = dataConSucursalAndUE.substring(6,8);
					intMes = Integer.parseInt(strMes);
					intAnio = Integer.parseInt("20"+strAnio);
					dtoDeEfectuadoConArchivo.setIntPeriodoMes(intMes);
					dtoDeEfectuadoConArchivo.setIntPeriodoAnio(intAnio);
					String strPeriodo = "20"+strAnio+strMes;
					intPeriodoConArchivoTXT = Integer.parseInt(strPeriodo);	
					log.debug("intPeriodoConArchivoTXT: "+intPeriodoConArchivoTXT);
					break;
				}				
			}
			brSinSucursalNiUE.close();
			brConSucursalAndUE.close();			
			
			log.debug("leendoAlgoTXT FIN v3");
		}catch(NumberFormatException e){			
			log.debug("NumberFormatException");			
			mensajeConArchivo= "ERROR: Archivo texto incorrecto.";			
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
			mensajeConArchivo = "Hubo un error al leer el strProg.";						
			return;
		}
	}
	
	public void aceptarUpload()
	{	
		log.debug("aceptarUpload: INICIO");	
		Integer intSucursal = 0;
		Integer intUnidadEjecutora = 0;
		blnErrorWithFileDeOneSucursalUE = Boolean.FALSE;		
		try
		{
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			if(blnTexto)
			{
				if(blnErrorConArchivo)			
				{			
					if(listaMensajesSinSucursal != null && !listaMensajesSinSucursal.isEmpty())
					{
						listaMensajes.clear();
						for(EnvioMsg em: listaMensajesSinSucursal)
						{
							EnvioMsg envioMsg = new EnvioMsg();
							envioMsg.setStrMsg(em.getStrMsg());
							listaMensajes.add(envioMsg);
						}
					}
					archivo = new Archivo();
					archivo.setId(new ArchivoId());
				}else 
				{
					intSucursal = listaJuridicaSucursal.get(0).getIntIdPersona();
					intUnidadEjecutora = listaJuridicaUnidadEjecutora.get(0).getIntIdPersona() ;
					
					if (listaJuridicaSucursal != null && !listaJuridicaSucursal.isEmpty())
					{
						for (Juridica sucursal: listaJuridicaSucursal) {
							if(intSucursal.compareTo(sucursal.getIntIdPersona())==-1 ||
							   intSucursal.compareTo(sucursal.getIntIdPersona())==1)
							{
								blnErrorWithFileDeOneSucursalUE = Boolean.TRUE;
								break;
							}
						}
					}
					if(!blnErrorWithFileDeOneSucursalUE)
					{
						if(listaJuridicaUnidadEjecutora != null && !listaJuridicaUnidadEjecutora.isEmpty())
						{
							for(Juridica unidadEjecutora : listaJuridicaUnidadEjecutora)
							{
								if(intUnidadEjecutora.compareTo(unidadEjecutora.getIntIdPersona())==-1 ||
								   intUnidadEjecutora.compareTo(unidadEjecutora.getIntIdPersona()) == 1)
								{
									blnErrorWithFileDeOneSucursalUE = Boolean.TRUE;
									break;
								}
							}
						}
					}
					
					if(blnErrorWithFileDeOneSucursalUE)
					{						
						if(listaMensajesConSucursaleUE != null && !listaMensajesConSucursaleUE.isEmpty())
						{
							if(listaMensajes != null && !listaMensajes.isEmpty())
							{
								listaMensajes.clear();
							}
							for(EnvioMsg em: listaMensajesConSucursaleUE)
							{								
								EnvioMsg envioMsg = new EnvioMsg();
								envioMsg.setStrMsg(em.getStrMsg());
								listaMensajes.add(envioMsg);
							}
						}
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
					}else
					{
						//todo esta bien entonces poner la sucursal e Unidad Ejecutora 
						dtoDeEfectuadoConArchivo.setJuridicaSucursal(new Juridica());
						dtoDeEfectuadoConArchivo.getJuridicaSucursal().setStrRazonSocial(listaJuridicaSucursal.get(0).getStrRazonSocial());
						dtoDeEfectuadoConArchivo.setEstructura(new Estructura());
						dtoDeEfectuadoConArchivo.getEstructura().setJuridica(new Juridica());
						dtoDeEfectuadoConArchivo.getEstructura().getJuridica().setStrRazonSocial(listaJuridicaUnidadEjecutora.get(0).getStrRazonSocial());
						datosValidadosConArchivo = Boolean.TRUE;
						examinar 				 = Boolean.FALSE;
						if(listaMensajes != null && !listaMensajes.isEmpty())
						{
							listaMensajes.clear();
						}
						if(listaJuridicaUnidadEjecutora!= null && !listaJuridicaUnidadEjecutora.isEmpty())
						{						
							estructura = remoteEstructura.getEstructuraPorIdEmpresaYIdPersona(EMPRESA_USUARIO,
									  														  listaJuridicaUnidadEjecutora.get(0).getIntIdPersona());					
							if(estructura != null)
							{
								log.debug("nivel: "+estructura.getId().getIntNivel() +
										  "codigo: "+estructura.getId().getIntCodigo());
							}
						}
					}				
				}	
			}
			if(blnExcel)
			{
				datosValidadosConArchivo = Boolean.TRUE;
				examinar 				 = Boolean.FALSE;
				if(listaMensajes != null && !listaMensajes.isEmpty())
				{
					listaMensajes.clear();
				}		
			}
			log.debug("aceptarUpload: FIN");
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			mensajeConArchivo = "Hubo un error al leer el strProg.";						
			return;
		}			
	}
	 
	
	/*
	 * 
	 */
	public void leendoTXT(String target)
	{
		log.debug("leendoTXT INICIO");
		cargarUsuario();		
		Integer intTipoSocio = 0;
		String nombres= "";
		Integer peridoMaximoEnvioResumen = 0;
		Integer intNumeroDeAfectados = 0;		
		listaMensajes = null;
		listaMensajes = new ArrayList<EnvioMsg>();
		EnvioMsg envioMsg = null;
		Boolean blnAgregarEfectuado = Boolean.FALSE;
		List<Envioresumen> listaEnvioresumen = null;
		try
		{			
			SocioFacadeRemote remoteSocio    		= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);			
			EnvioresumenBO boEnvioresumen	 		= (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
			EnvioconceptoBO boEnvioconcepto  		= (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);
			EfectuadoResumenBO boEfectuadoResumen	= (EfectuadoResumenBO)TumiFactory.get(EfectuadoResumenBO.class);
			
			File filePeriodo = new File(target);
			File fileSocio = new File(target);
			File fileSocioEstructura = new File(target);
			File fileNumeroSocios = new File(target);
			FileReader frPeriodo = new FileReader(filePeriodo);
			FileReader frSocio = new FileReader(fileSocio);
			FileReader frSocioEstructura = new FileReader(fileSocioEstructura);
			FileReader frNumeroSociosPorTipoSocio = new FileReader(fileNumeroSocios);
			BufferedReader brPeriodo = new BufferedReader(frPeriodo);
			BufferedReader brSocio = new BufferedReader(frSocio);
			BufferedReader brSocioEstructura = new BufferedReader(frSocioEstructura);
			BufferedReader brNumeroSociosPorTipoSocio = new BufferedReader(frNumeroSociosPorTipoSocio);
			dtoDeEfectuadoConArchivo.setStrModalidad("Haberes");	
			dtoDeEfectuado.setStrModalidad(dtoDeEfectuadoConArchivo.getStrModalidad());
			String dataPeriodo = null;
			String dataSocio = null;
			String dataSocioEstructura = null;
			String dataNumeroSociosPorTipoSocio = null;
			
			if(strTipoSocioConArchivo != null)
			{
				if(strTipoSocioConArchivo.compareTo("Activo") == 0)
				{
					intTipoSocio = Constante.PARAM_T_TIPOSOCIO_ACTIVO;
				}
				else if(strTipoSocioConArchivo.compareTo("Cesante") == 0)
				{
					intTipoSocio = Constante.PARAM_T_TIPOSOCIO_CESANTE;
				}
				
				//number of lines in a file of tipoSocio select
				while((dataNumeroSociosPorTipoSocio = brNumeroSociosPorTipoSocio.readLine()) != null)
				{
					if(dataNumeroSociosPorTipoSocio != null && dataNumeroSociosPorTipoSocio.length() >80 )
					{
						Integer intTip = Integer.parseInt(dataNumeroSociosPorTipoSocio.substring(84,85));
						if(Constante.PARAM_T_TIPOSOCIO_ACTIVO.compareTo(intTipoSocio) == 0)
						{
							if(intTip.compareTo(1)== 0 || intTip.compareTo(2) == 0 || 
							   intTip.compareTo(3) == 0 || intTip.compareTo(4)==0)
							{
								intNroSociosConArchivo++;
							}
						}else if(Constante.PARAM_T_TIPOSOCIO_CESANTE.compareTo(intTipoSocio) == 0)
						{
							if(intTip.compareTo(5)==0 || intTip.compareTo(6) == 0)
							{
								intNroSociosConArchivo++;
							}
						}
					}
				}
				log.debug("intNroSociosConArchivo: "+intNroSociosConArchivo);
				if(estructura != null)
				{					 		  
					listaEnvioresumen = boEnvioresumen.getListaEnvioREfectuadoConArchivo(EMPRESA_USUARIO,
																						 estructura.getId().getIntNivel(),
																						 estructura.getId().getIntCodigo(),
																						 intTipoSocio,
																				         Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
					if(listaEnvioresumen != null && !listaEnvioresumen.isEmpty())
					{
						EfectuadoResumen periodoEfectuadoResumen = boEfectuadoResumen.getMaximoPeriodo(EMPRESA_USUARIO,
																							  			estructura.getId().getIntNivel(),
																							  			estructura.getId().getIntCodigo(),
																							  			intTipoSocio,
																							  			Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
						Envioresumen envioResumenConArchivo = listaEnvioresumen.get(0);
						peridoMaximoEnvioResumen = envioResumenConArchivo.getIntPeriodoplanilla();
						
						if(periodoEfectuadoResumen != null)
						{
							log.debug("periodoEfectuadoResumen: "+periodoEfectuadoResumen.getIntPeriodoPlanilla());
							if(peridoMaximoEnvioResumen.compareTo(periodoEfectuadoResumen.getIntPeriodoPlanilla()) == 1
								|| periodoEfectuadoResumen == null)
							{
								//que siga
								log.debug("periodoEfectuadoResumen: "+periodoEfectuadoResumen.getIntPeriodoPlanilla()+
										 ", listaEnvioresumen: "+listaEnvioresumen.size()+
										  ", periodo: "+listaEnvioresumen.get(0).getIntPeriodoplanilla()+
										  ", nroAfectados: "+	listaEnvioresumen.get(0).getIntNumeroafectados()+
										  ", intNroSociosConArchivo: "+intNroSociosConArchivo);
								
								for(Envioresumen erca :listaEnvioresumen)
								{	log.debug(erca);
									intNumeroDeAfectados = intNumeroDeAfectados + erca.getIntNumeroafectados();
								}						 
								log.debug("intNumeroDeAfectados: "+intNumeroDeAfectados);
								
								if(intNroSociosConArchivo.compareTo(intNumeroDeAfectados) == 1)
								{
									log.debug("Nro de socios en el texto es mayor al numero de enviado");
									//a mostrar estos socios que no se encuentran en el enviado
									while((dataSocio = brSocio.readLine()) != null)
									{																	
										if(dataSocio != null && dataSocio.length() > 80)
										{		
											nombres= dataSocio.substring(16,61).trim();									
											Envioconcepto ec = boEnvioconcepto.getCuentaOfConArchivo(nombres,
																							 		 EMPRESA_USUARIO,
																							 		 estructura.getId().getIntNivel(),
																							 		 estructura.getId().getIntCodigo(),
																							 		 intTipoSocio,
																							 		 Constante.PARAM_T_MODALIDADPLANILLA_HABERES,
																							 		 peridoMaximoEnvioResumen);
											if(ec != null)
											{									
											}else
											{										
												String string =  new String();
												string = nombres+" no tiene un enviado para el periodo "+peridoMaximoEnvioResumen;
												envioMsg = new EnvioMsg();
												envioMsg.setStrMsg(string);
												listaMensajes.add(envioMsg);
												continue;
											}
										}							
									}
									
								}else 
								{
									log.debug("numero de socios en el archivo es menor al numero en el enviado resumen");
									//si es menor mi numero de socios del archivo que el enviado, se efectua grabando estos pocos socios normal
									//y los socios restantes se graba su efectuado con monto 0 y sus conceptos en 0							
									//LISTA LOS SOCIOS QUE NO CORRESPONDEN AL PERIODO A EFECTUARSE						
									while((dataPeriodo = brPeriodo.readLine()) != null)
									{
										log.debug("1");
										if(dataPeriodo != null && dataPeriodo.length() > 40)
										{								
											String strMes = dataPeriodo.substring(4,6);
											String strAnio = dataPeriodo.substring(6,8);											 									
											String strPeriodo = "20"+strAnio+strMes;
											intPeriodoConArchivoTXT = Integer.parseInt(strPeriodo);	
											
											log.debug("intPeriodoConArchivoTXT: "+ intPeriodoConArchivoTXT+
													 "peridoMaximoEnvioResumen: "+peridoMaximoEnvioResumen);
											if(intPeriodoConArchivoTXT.compareTo(peridoMaximoEnvioResumen)== -1
											|| intPeriodoConArchivoTXT.compareTo(peridoMaximoEnvioResumen)== 1)
											{									
												log.debug("13");
												nombres= dataPeriodo.substring(16,61).trim();								
												String string =  new String();
												string = peridoMaximoEnvioResumen+" "+nombres+" no corresponde al periodo a efectuar.";
												envioMsg = new EnvioMsg();
												envioMsg.setStrMsg(string);
												listaMensajes.add(envioMsg);									
											}								
										}							
									}
									//LISTA SOCIOS QUE NO EXISTAN EN LA TABLA SOCIO
									while((dataSocio = brSocio.readLine()) != null)
									{
										if(dataSocio != null && dataSocio.length() > 80)
										{								
										    nombres= dataSocio.substring(16,61).trim();									
											Socio socio = 	remoteSocio.getSocioPorNombres(nombres);
											if(socio != null)
											{									
											}else
											{
												log.debug("NO ES UN SOCIO");
												String string =  new String();
												string = nombres + " no es un  SOCIO.";
												envioMsg = new EnvioMsg();
												envioMsg.setStrMsg(string);
												listaMensajes.add(envioMsg);
											}
										}							
									}							
									//VERIFICA Y LISTA SI EN CASO EL SOCIO NO EXISTE EN SOCIOESTRUCTURA
									while((dataSocioEstructura = brSocioEstructura.readLine()) != null)
									{								
										if(dataSocioEstructura != null && dataSocioEstructura.length() > 80)
										{
										    nombres= dataSocioEstructura.substring(16,61).trim();									
											Socio socio = 	remoteSocio.getSocioPorNombres(nombres);
											if(socio!= null)
											{									
												SocioPK socioPK = new SocioPK();
												socioPK.setIntIdEmpresa(socio.getId().getIntIdEmpresa());
												socioPK.setIntIdPersona(socio.getId().getIntIdPersona());
												EstructuraId eId = new EstructuraId();
												eId.setIntNivel(estructura.getId().getIntNivel());
												eId.setIntCodigo(estructura.getId().getIntCodigo());  
												List<SocioEstructura> listaSocioEstructura = remoteSocio.getListaXSocioPKActivoTipoSocio(socio.getId().getIntIdPersona(),
																																		socio.getId().getIntIdEmpresa(), 
																																		Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO, 
																																		Constante.PARAM_T_TIPOSOCIO_ACTIVO,
																																		estructura.getId().getIntNivel(),
																																		estructura.getId().getIntCodigo());
												if(listaSocioEstructura != null && !listaSocioEstructura.isEmpty())
												{										
												}else
												{										
													String string =  new String();
													string = nombres+" no se encuentra en la Unidad Ejecutora a efectuar.";
													envioMsg = new EnvioMsg();
													envioMsg.setStrMsg(string);
													listaMensajes.add(envioMsg);
												}									
											}
										}
									}
									
									if(listaMensajes != null && !listaMensajes.isEmpty())
									{							
										log.debug("listaMensajes cant"+listaMensajes.size());
										brPeriodo.close();
										brSocioEstructura.close();
										brSocio.close();
										brNumeroSociosPorTipoSocio.close();
										if(filePeriodo.delete())
										if(fileSocio.delete())
										if(fileSocioEstructura.delete())	
										if(fileNumeroSocios.delete())
										return;
									}else
									{
										log.debug("listaMensajes cant"+listaMensajes.size());
										if(intNroSociosConArchivo.compareTo(intNumeroDeAfectados) == -1)
										{
											log.debug("AGREGAR FALTANTES");
											
											blnAgregarEfectuado = Boolean.TRUE;
											siguiendoleendoenTxt(target, 
															 	 intTipoSocio, 
																 peridoMaximoEnvioResumen, 
																 strTipoSocioConArchivo,
																 brSocioEstructura,
																 brSocio,
																 brPeriodo,
																 fileSocioEstructura,
																 fileSocio,
																 filePeriodo,
																 blnAgregarEfectuado,
																 listaEnvioresumen);
											
										}else if(intNroSociosConArchivo.compareTo(intNumeroDeAfectados) == 0)
										{
											log.debug("IGUALESS");
											siguiendoleendoenTxt(target, 
															 	 intTipoSocio, 
																 peridoMaximoEnvioResumen, 
																 strTipoSocioConArchivo,
																 brSocioEstructura,
																 brSocio,
																 brPeriodo,
																 fileSocioEstructura,
																 fileSocio,
																 filePeriodo,
																 blnAgregarEfectuado,
																 listaEnvioresumen);
											
										}								
									}
								}							
							}else if(peridoMaximoEnvioResumen.compareTo(periodoEfectuadoResumen.getIntPeriodoPlanilla()) == 0)
							{
								mostrarMensaje(Boolean.FALSE, " Ya se realizó un efectuado con la modalidad y tipo seleccionado," +
														      " Verifique que exista un enviado.");
								brPeriodo.close();
								brSocioEstructura.close();
								brSocio.close();
								brNumeroSociosPorTipoSocio.close();
								if(filePeriodo.delete()){}
								if(fileSocio.delete()){}
								if(fileSocioEstructura.delete()){}
								if(fileNumeroSocios.delete()){}
							}
						}else
						{
							log.debug("periodo en efectuadoresumen is null");
						}
					}else 
					{
						log.debug("no hay nada en el enviadoresumen");
						mostrarMensaje(Boolean.FALSE, "  No hay un enviado,  Revisar el archivo, seleccionar el archivo nuevamente.");
						brPeriodo.close();
						brSocioEstructura.close();
						brSocio.close();
						brNumeroSociosPorTipoSocio.close();
						if(filePeriodo.delete()){}
						if(fileSocio.delete()){}
						if(fileSocioEstructura.delete()){}
						if(fileNumeroSocios.delete()){}
					}				
				}else
				{
					log.debug("Estructura es null.");
				}
			}else if(strTipoSocioConArchivo == null)
			{
				mostrarMensaje(Boolean.FALSE, "  Seleccione Tipo de Socio");
				return;
			}		
			brPeriodo.close();
			brSocioEstructura.close();
			brSocio.close();
			brNumeroSociosPorTipoSocio.close();
			if(filePeriodo.delete()){}
			if(fileSocio.delete()){}
			if(fileSocioEstructura.delete()){}
			if(fileNumeroSocios.delete()){}
			log.debug("leendoTXT FIN");	
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
			mostrarMensaje(Boolean.FALSE, "  Vuelva a cargar and revise el archivo.");
			return;
		}
	}	
	/**
	 * un texto es de unica modalidad haber, tengo anio, mes, hago una comparacion completa de todos sus nombre con
	 * sus apellidos en la tabla SOCIO la sucursal y unidad ejecutora lo saco con  DEPE_PROGSUB_V  y DEPE_CODEST_V
	 * BUSCAR ESOS DATOS EN LA TABLA: CSO_PADRON
	   EN LOS CAMPOS: DEPE_PROGSUB_V  y DEPE_CODEST_V respectivamente
	   Encontrar el dato del campo EJEC_PRO_V = resultado N
	   Estos datos refieren al último padrón cargado ( validar con periodo (año) y mes.

	 * @param target viene a ser la ruta donde se ha copiado temporal cosa que luego se borra el archivo copiado.
	 * @throws FileNotFoundException 
	 */	
	public void siguiendoleendoenTxt(String target,
									 Integer intTipoSocio,
									 Integer peridoMaximoEnvioResumen,
									 String strTipoSocioConArchivo,
									 BufferedReader brSocio,
									 BufferedReader brSocioEstructura,
									 BufferedReader brPeriodo,
									 File filePeriodo,
									 File fileSocio,
									 File fileSocioEstructura,
									 Boolean blnAgregarEfectuado,
									 List<Envioresumen> listaEnvioresumen) throws FileNotFoundException
	{
		log.debug("siguiendoleendoenTxt INICIO");
		String data = null;
		EfectuadoResumen efectuadoR = null;		
		List<Efectuado> listaEfectuado = new ArrayList<Efectuado>();
		EfectuadoConceptoComp efectuadoConArchivo = null;
		File filea = new File(target);
		FileReader fr = new FileReader(filea);
		BufferedReader brr = new BufferedReader(fr);		
		planillaEfectuadaResumen = new ArrayList<EfectuadoResumen>();
		listaEfectuadoConceptoComp = new ArrayList<EfectuadoConceptoComp>();	
		List<EfectuadoResumen> listaEfectuadoResumenPorAdministra = null;
		List<EfectuadoConcepto> listaEfectuadoConcepto = null;
		listaEfectuadoResumenPorAdministra = new ArrayList<EfectuadoResumen>();
		cargarUsuario();
		try
		{			
			EnvioconceptoBO boEnvioconcepto  		= (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);
			EnviomontoBO boEnviomonto		 		= (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
			CuentaFacadeRemote remoteCuenta 		= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			for(Envioresumen envres: listaEnvioresumen)
			{
				efectuadoR = new EfectuadoResumen();
				efectuadoR.getId().setIntEmpresa(EMPRESA_USUARIO);
				efectuadoR.setIntPersonausuarioPk(PERSONA_USUARIO);
				efectuadoR.setIntTiposocioCod(intTipoSocio);
				efectuadoR.setIntPeriodoPlanilla(peridoMaximoEnvioResumen);
				efectuadoR.setIntModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
				efectuadoR.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				efectuadoR.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
				efectuadoR.setIntNivel(estructura.getId().getIntNivel());
				efectuadoR.setIntCodigo(estructura.getId().getIntCodigo());
				efectuadoR.setIntIdsucursaladministraPk(envres.getIntIdsucursaladministraPk());
				efectuadoR.setIntIdsubsucursaladministra(envres.getIntIdsubsucursaladministra());
				efectuadoR.setIntIdsucursalprocesaPk(envres.getIntIdsucursalprocesaPk());
				efectuadoR.setIntIdsubsucursalprocesaPk(envres.getIntIdsubsucursalprocesaPk());
				efectuadoR.setBdMontoTotal(new BigDecimal(0));
				efectuadoR.setIntNumeroAfectados(0);
				efectuadoR.setIntParaEstadoPago(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				listaEfectuadoResumenPorAdministra.add(efectuadoR);
			}			
			dtoDeEfectuadoConArchivo.setStrTipoSocio(strTipoSocioConArchivo);
			while((data = brr.readLine()) != null)
			{
				if(data != null && data.length() > 80)
				{
					String strTipoSocio = data.substring(84,85);
					
					Integer intTip = Integer.parseInt(strTipoSocio);
					log.debug("intTip: "+intTip);
					efectuadoConArchivo = new EfectuadoConceptoComp();					
					efectuadoConArchivo.setBlnAgregarSocio(Boolean.FALSE);
					efectuadoConArchivo.setBlnAgregarNoSocio(Boolean.FALSE);
					efectuadoConArchivo.getEfectuado().setBlnLIC(Boolean.FALSE);					
					efectuadoConArchivo.getEfectuado().setBlnDJUD(Boolean.FALSE);
					efectuadoConArchivo.getEfectuado().setBlnCartaAutorizacion(Boolean.FALSE);
					efectuadoConArchivo.getEfectuado().setBlnListaEnvioConcepto(Boolean.TRUE);
					efectuadoConArchivo.getEfectuado().setBlnAgregarNoSocio(Boolean.FALSE);
					efectuadoConArchivo.getEfectuado().setBlnEfectuadoConcepto0(Boolean.FALSE);
					if(Constante.PARAM_T_TIPOSOCIO_ACTIVO.compareTo(intTipoSocio)==0)
					{
						if(intTip.compareTo(1) ==0 ||intTip.compareTo(2) ==0 ||
								intTip.compareTo(3) ==0 || intTip.compareTo(4) ==0)
						{
							rellenando(data, efectuadoConArchivo,
									   intTipoSocio, listaEfectuadoConceptoComp,
									   peridoMaximoEnvioResumen, listaEfectuadoResumenPorAdministra,
									   listaEfectuado);
						}
					}else if(Constante.PARAM_T_TIPOSOCIO_CESANTE.compareTo(intTipoSocio)==0)
					{
						if(intTip.compareTo(5) ==0 ||intTip.compareTo(6) ==0)
						{
							rellenando(data, efectuadoConArchivo,
									   intTipoSocio, listaEfectuadoConceptoComp,
									   peridoMaximoEnvioResumen, listaEfectuadoResumenPorAdministra,
									   listaEfectuado);
						}
					}					 
				}else
				{
					break;
				}
			}
			if(blnAgregarEfectuado)
			{
				//me quedo solo con las cuentas faltantes 
				//log.debug("itemEnvioResumen"+intItemEnvioResumen);
				List<Envioconcepto> listaCuentas = boEnvioconcepto.getListaCuentaEnConArchivo(EMPRESA_USUARIO,
																							  estructura.getId().getIntNivel(),
																							  estructura.getId().getIntCodigo(),
																							  intTipoSocio,
																							  Constante.PARAM_T_MODALIDADPLANILLA_HABERES,
																							  peridoMaximoEnvioResumen);
				if(listaCuentas != null && !listaCuentas.isEmpty())
				{
					log.debug("cant en listaCuentas before: "+listaCuentas.size());
					
					for(Iterator<Envioconcepto> iter = listaCuentas.listIterator(); iter.hasNext();)
					{
						Envioconcepto cuenta = iter.next();
						for(EfectuadoConceptoComp ecc :listaEfectuadoConceptoComp)
						{						
							if(cuenta.getIntCuentaPk().compareTo(ecc.getEfectuado().getIntCuentaPk())==0)
							{
								iter.remove();
							}
						}
					}
					log.debug("cant en listaCuentas after: "+listaCuentas.size());
					/**
					 * con las cuentas faltantes formaria efectuados y lo asignaria
					 * con montos 0  a su efectuadoResumen correspondiente
					 * con su listado de efectuadoconceptos todos con montos 0
					 */					
					for(Envioconcepto ecuentas:listaCuentas)
					{
						List<Enviomonto> listaEnviomonto = boEnviomonto.getEnviomontoPorInt(EMPRESA_USUARIO,
																						    peridoMaximoEnvioResumen,
																						    ecuentas.getIntCuentaPk(),
																						    intTipoSocio,
																						    Constante.PARAM_T_MODALIDADPLANILLA_HABERES,
																						    estructura.getId().getIntNivel(),
																						    estructura.getId().getIntCodigo());

					if(listaEnviomonto != null && !listaEnviomonto.isEmpty())
					 {					
						CuentaIntegrante cuInt = remoteCuenta.getCodPersonaOfCobranza(EMPRESA_USUARIO,
																					  ecuentas.getIntCuentaPk());
						if(cuInt != null)
						{
							log.debug("si tiene listaEnviomonto "+listaEnviomonto.size());
							Enviomonto enviom = listaEnviomonto.get(0);
							
							efectuadoConArchivo = new EfectuadoConceptoComp();					
							efectuadoConArchivo.setBlnAgregarSocio(Boolean.FALSE);
							efectuadoConArchivo.setBlnAgregarNoSocio(Boolean.FALSE);						
							efectuadoConArchivo.getEfectuado().setIntPersonaIntegrante(cuInt.getId().getIntPersonaIntegrante());						
							efectuadoConArchivo.getEfectuado().getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
							efectuadoConArchivo.getEfectuado().setIntPeriodoPlanilla(peridoMaximoEnvioResumen);
							efectuadoConArchivo.getEfectuado().setIntTiposocioCod(intTipoSocio);
							efectuadoConArchivo.getEfectuado().setIntModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
							efectuadoConArchivo.getEfectuado().setIntNivel(estructura.getId().getIntNivel());
							efectuadoConArchivo.getEfectuado().setIntCodigo(estructura.getId().getIntCodigo());
							efectuadoConArchivo.getEfectuado().setIntTipoestructuraCod(enviom.getIntTipoestructuraCod());
							efectuadoConArchivo.getEfectuado().setIntEmpresasucprocesaPk(enviom.getIntEmpresasucprocesaPk());
							efectuadoConArchivo.getEfectuado().setIntIdsucursalprocesaPk(enviom.getIntIdsucursalprocesaPk());
							efectuadoConArchivo.getEfectuado().setIntIdsubsucursalprocesaPk(enviom.getIntIdsubsucursalprocesaPk());
							efectuadoConArchivo.getEfectuado().setIntIdsucursaladministraPk(enviom.getIntIdsucursaladministraPk());
							efectuadoConArchivo.getEfectuado().setIntIdsubsucursaladministra(enviom.getIntIdsubsucursaladministra());
							efectuadoConArchivo.getEfectuado().setIntEmpresasucadministraPk(enviom.getIntEmpresasucadministraPk());
							efectuadoConArchivo.getEfectuado().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							efectuadoConArchivo.getEfectuado().setIntEmpresausuarioPk(enviom.getIntEmpresausuarioPk());
							efectuadoConArchivo.getEfectuado().setTsFecharegistro(enviom.getTsFecharegistro());
							efectuadoConArchivo.getEfectuado().setIntPersonausuarioPk(enviom.getIntPersonausuarioPk());
							efectuadoConArchivo.getEfectuado().setIntCuentaPk(ecuentas.getIntCuentaPk());
							efectuadoConArchivo.getEfectuado().setIntParaTipoIngresoDatoCod(1);
							efectuadoConArchivo.getEfectuado().setEnvioMonto(enviom);
							efectuadoConArchivo.getEfectuado().setBlnDJUD(Boolean.FALSE);
							efectuadoConArchivo.getEfectuado().setBlnCartaAutorizacion(Boolean.FALSE);
							efectuadoConArchivo.getEfectuado().setBlnLIC(Boolean.FALSE);
							efectuadoConArchivo.getEfectuado().setBlnListaEnvioConcepto(Boolean.TRUE);
							efectuadoConArchivo.getEfectuado().setBdMontoEfectuado(new BigDecimal(0));
							efectuadoConArchivo.getEfectuado().setBlnAgregarNoSocio(Boolean.FALSE);						
							efectuadoConArchivo.getEfectuado().setBlnEfectuadoConcepto0(Boolean.TRUE);
							
							EnvioconceptoId encoId = new EnvioconceptoId();
							encoId.setIntEmpresacuentaPk(EMPRESA_USUARIO);
							encoId.setIntItemenvioconcepto(enviom.getId().getIntItemenvioconcepto());
							listaEfectuadoConcepto = new ArrayList<EfectuadoConcepto>();
							List<Envioconcepto> listaEnvioconcepto = boEnvioconcepto.getEnvioconceptoPorItemEnvioConcepto(encoId);
							
							if(listaEnvioconcepto != null && !listaEnvioconcepto.isEmpty())
							{
								for(Envioconcepto ec: listaEnvioconcepto)
								{
									EfectuadoConcepto efectuadoConcepto = new EfectuadoConcepto();
									efectuadoConcepto.getId().setIntEmpresaCuentaEnvioPk(EMPRESA_USUARIO);
									if(ec.getIntItemcuentaconcepto() != null)
									{
										efectuadoConcepto.setIntItemCuentaConcepto(ec.getIntItemcuentaconcepto());
									}else if(ec.getIntItemexpediente() != null && ec.getIntItemdetexpediente() != null)
									{
										efectuadoConcepto.setIntItemExpediente(ec.getIntItemexpediente());
										efectuadoConcepto.setIntItemDetExpediente(ec.getIntItemdetexpediente());
									}																
									efectuadoConcepto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
									efectuadoConcepto.setIntTipoConceptoGeneralCod(ec.getIntTipoconceptogeneralCod());
									efectuadoConcepto.setBdMontoConcepto(new BigDecimal(0));
									listaEfectuadoConcepto.add(efectuadoConcepto);
								}
								
								efectuadoConArchivo.getEfectuado().setListaEfectuadoConcepto(listaEfectuadoConcepto);
							}
							for(EfectuadoResumen er:listaEfectuadoResumenPorAdministra)
							{
								if(er.getIntIdsucursaladministraPk().compareTo(efectuadoConArchivo.getEfectuado().getIntIdsucursaladministraPk()) == 0 &&
								   er.getIntIdsubsucursaladministra().compareTo(efectuadoConArchivo.getEfectuado().getIntIdsubsucursaladministra()) == 0)
								{
									er.getListaEfectuado().add(efectuadoConArchivo.getEfectuado());								
									break;
								}
							}
						}else
						{
						log.debug("codPersona is nulll");
						}					
												
					   }
					}
				}
				
			}
			if(listaEfectuadoConceptoComp != null && !listaEfectuadoConceptoComp.isEmpty())
			{
				brr.close();
				brPeriodo.close();
				brSocio.close();
				brSocioEstructura.close();				
				datosValidados2ConArchivo = Boolean.TRUE;
				datosValidadosConArchivo  = Boolean.FALSE;			
				if(filea.delete()){}
				if(filePeriodo.delete()){}
				if(fileSocio.delete()){}
				if(fileSocioEstructura.delete()){}				
				setPlanillaEfectuadaResumen(listaEfectuadoResumenPorAdministra);
				 
			}else 
			{
				mostrarMensaje(Boolean.FALSE, "  No hay Socios en el archivo seleccionado con la modalidad y tipo socio elegido.");
				return;
			}
			brr.close();
			brPeriodo.close();
			brSocio.close();
			brSocioEstructura.close();
			if(filea.delete()){}
			if(filePeriodo.delete()){}
			if(fileSocio.delete()){}
			if(fileSocioEstructura.delete()){}
			log.debug("siguiendoleendoenTxt FIN");	
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void rellenando(String data,
						   EfectuadoConceptoComp efectuadoConArchivo,
						   Integer intTipoSocio,
						   List<EfectuadoConceptoComp> listaEfectuadoConceptoComp,
						   Integer peridoMaximoEnvioResumen,						  
						   List<EfectuadoResumen> listaEfectuadoResumenPorAdministra,
						   List<Efectuado> listaEfectuado)
	{
		String strLIC = null;			
		CuentaIntegrante cuentaIntegranteRspta = null;				
		List<Envioconcepto> listaEnvioConcepto = null;		 
		try
		{
			SocioFacadeRemote remoteSocio     		= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			CuentaFacadeRemote remoteCuenta 		= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			ContactoFacadeRemote remoteContacto 	= (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);		
			EnviomontoBO boEnviomonto		 		= (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
			EnvioconceptoBO boEnvioconcepto			= (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);
			
			//COMPARAR NOMBRES COMPLETOS
			String nombres= data.substring(16,61).trim();
			log.debug("nombres: "+nombres);
			 Socio socio = 	remoteSocio.getSocioPorNombres(nombres);
			 if(socio != null)
			 {
				 log.debug("si es igual en socio tiene socio");
				 log.debug("persona: " +socio.getId().getIntIdPersona()+
						   ", empresa: "+socio.getId().getIntIdEmpresa());
				 cuentaIntegranteRspta = remoteCuenta.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(socio.getId(), 
															 				 								  Constante.PARAM_T_TIPOCUENTA_ACTIVA,
															 				 								  Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
				 if(cuentaIntegranteRspta != null)
				  {	 
				 	efectuadoConArchivo.setSocio(socio);
				 	Documento lDocumento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(socio.getId().getIntIdPersona(), 
																								 new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));												 	
				 	
				 	if(lDocumento != null)
					{						
						efectuadoConArchivo.setDocumento(lDocumento);
						
						List<Enviomonto> listaEnviomonto = boEnviomonto.getEnviomontoPorInt(EMPRESA_USUARIO,
																							peridoMaximoEnvioResumen,
																							cuentaIntegranteRspta.getId().getIntCuenta(),
							 									 							intTipoSocio,
							 									 							Constante.PARAM_T_MODALIDADPLANILLA_HABERES,
							 									 							estructura.getId().getIntNivel(),
							 									 							estructura.getId().getIntCodigo());
						
						if(listaEnviomonto != null && !listaEnviomonto.isEmpty())
						{							
							log.debug("si tiene listaEnviomonto "+listaEnviomonto.size());
							Enviomonto enviom = listaEnviomonto.get(0);
							
							efectuadoConArchivo.getEfectuado().getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
							efectuadoConArchivo.getEfectuado().setIntPersonaIntegrante(socio.getId().getIntIdPersona());
							efectuadoConArchivo.getEfectuado().setIntPeriodoPlanilla(peridoMaximoEnvioResumen);
							efectuadoConArchivo.getEfectuado().setIntTiposocioCod(intTipoSocio);
							efectuadoConArchivo.getEfectuado().setIntModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
							efectuadoConArchivo.getEfectuado().setIntNivel(estructura.getId().getIntNivel());
							efectuadoConArchivo.getEfectuado().setIntCodigo(estructura.getId().getIntCodigo());
							efectuadoConArchivo.getEfectuado().setIntTipoestructuraCod(enviom.getIntTipoestructuraCod());
							efectuadoConArchivo.getEfectuado().setIntEmpresasucprocesaPk(enviom.getIntEmpresasucprocesaPk());
							efectuadoConArchivo.getEfectuado().setIntIdsucursalprocesaPk(enviom.getIntIdsucursalprocesaPk());
							efectuadoConArchivo.getEfectuado().setIntIdsubsucursalprocesaPk(enviom.getIntIdsubsucursalprocesaPk());
							efectuadoConArchivo.getEfectuado().setIntIdsucursaladministraPk(enviom.getIntIdsucursaladministraPk());
							efectuadoConArchivo.getEfectuado().setIntIdsubsucursaladministra(enviom.getIntIdsubsucursaladministra());
							efectuadoConArchivo.getEfectuado().setIntEmpresasucadministraPk(enviom.getIntEmpresasucadministraPk());
							efectuadoConArchivo.getEfectuado().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							efectuadoConArchivo.getEfectuado().setIntEmpresausuarioPk(enviom.getIntEmpresausuarioPk());
							efectuadoConArchivo.getEfectuado().setTsFecharegistro(enviom.getTsFecharegistro());
							efectuadoConArchivo.getEfectuado().setIntPersonausuarioPk(enviom.getIntPersonausuarioPk());
							efectuadoConArchivo.getEfectuado().setIntCuentaPk(cuentaIntegranteRspta.getId().getIntCuenta());
							efectuadoConArchivo.getEfectuado().setIntParaTipoIngresoDatoCod(Constante.PARAM_ParaTipoIngresoDatoConArchivo);
							efectuadoConArchivo.getEfectuado().setEnvioMonto(enviom);
							 
							
							EnvioconceptoId pId = new EnvioconceptoId();
							pId.setIntEmpresacuentaPk(enviom.getId().getIntEmpresacuentaPk());
							pId.setIntItemenvioconcepto(enviom.getId().getIntItemenvioconcepto());
							
							listaEnvioConcepto = boEnvioconcepto.getEnvioconceptoPorItemEnvioConcepto(pId);
							
							if(listaEnvioConcepto != null && !listaEnvioConcepto.isEmpty())
							{
								log.debug("listaEnvioConcepto:"+listaEnvioConcepto.size());
								Envioconcepto enconcepto = listaEnvioConcepto.get(0);
								if(enconcepto.getIntIndidescjudi().compareTo(1) == 0)
								{
									efectuadoConArchivo.getEfectuado().setBlnDJUD(Boolean.TRUE);
								}
								if(enconcepto.getIntIndicienpor().compareTo(1)==0)
								{
									efectuadoConArchivo.getEfectuado().setBlnCartaAutorizacion(Boolean.TRUE);
								}														
							}
							
							strLIC = data.substring(85, 86);
							if(strLIC != null && strLIC.compareTo("S") == 0)
							{
								log.debug("TIENE S EN DATA:::::::::"+data.substring(85, 86).trim());
								efectuadoConArchivo.getEfectuado().setBlnLIC(Boolean.TRUE);	
							}else
							{
								log.debug("no TIENE S EN DATA:::::::::");
								efectuadoConArchivo.getEfectuado().setBlnLIC(Boolean.FALSE);	
							}
							
							String strMonto = data.substring(61,70).trim();
							Integer intMonto = Integer.parseInt(strMonto);
							BigDecimal bdMonto = new BigDecimal(intMonto).divide(new BigDecimal(100))
																.setScale(2,BigDecimal.ROUND_HALF_UP);
							bdMontoTotalConArchivo = bdMontoTotalConArchivo.add(bdMonto);
							efectuadoConArchivo.getEfectuado().setBdMontoEfectuado(bdMonto);
							efectuadoConArchivo.getEfectuado().setBlnAgregarNoSocio(Boolean.FALSE);
							
							for(EfectuadoResumen er:listaEfectuadoResumenPorAdministra)
							{
								if(er.getIntIdsucursaladministraPk().compareTo(efectuadoConArchivo.getEfectuado().getIntIdsucursaladministraPk())==0)
								{
									er.getListaEfectuado().add(efectuadoConArchivo.getEfectuado());
									er.setIntNumeroAfectados(er.getIntNumeroAfectados()+1);
									er.setBdMontoTotal(er.getBdMontoTotal().add(efectuadoConArchivo.getEfectuado().getBdMontoEfectuado()));
									break;
								}
							}							
							listaEfectuadoConceptoComp.add(efectuadoConArchivo);							
						}
					}else
					{
						log.debug(" no tiene documento.");
					}
			     }else
			     {
			    	 log.debug("no encuentra cuentaintegrante, "+nombres); 
			    	 log.debug("persona: " +socio.getId().getIntIdPersona()+
							   ", empresa: "+socio.getId().getIntIdEmpresa());			    	 
			     }
			 }else{
				 log.debug("no encuentra al socio, "+nombres);
			 }
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
		
	
	/**	
	 * @param target es la ruta de la copia del archivo seleccionado luego es eliminado
	 * en tumi ArchivosAdjuntosP efectuadoConArchivo
	 */	
	public void leendoXLS(String target)
	{
		Integer intTipoSocio = 0;
		Integer intModalidad = 0;
		Integer intNivelUE 	 = 0;
		Integer intCodigoUE  = 0;
		Integer intCuenta	 = 0;
		Integer intPeriodo	 = 0;
		planillaEfectuadaResumen = null;
		listaEfectuadoConceptoComp = null;
		BigDecimal bdMontoTotal = new BigDecimal(0);
		List<Envioconcepto> listaEnvioConcepto = null;
		List<SocioEstructura> listaSocioEstructura = null;
		List<Efectuado> listaEfectuado = new ArrayList<Efectuado>();
		planillaEfectuadaResumen = new ArrayList<EfectuadoResumen>();
		listaEfectuadoConceptoComp = new ArrayList<EfectuadoConceptoComp>();
		EfectuadoResumen efectuadoResumen = null;
		CuentaIntegrante cuentaIntegranteRspta = null;
		EfectuadoConceptoComp efectuadoConArchivo = null;
		List<EfectuadoResumen> listaEfectuadoResumenPorAdministra = null;		
		listaEfectuadoResumenPorAdministra = new ArrayList<EfectuadoResumen>();
		List<Envioconcepto> listaEnvioconcepto = new ArrayList<Envioconcepto>();
		EstructuraId lEstructuraId 	= null;
		Envioconcepto envioconcepto = null;
		Enviomonto enviomonto = null;
		registroSeleccionadoEnAE = null;
		Envioresumen envioresumen = null;
		Envioresumen envResNoSoc = null; 
		Envioresumen envResSocAgregado = null;
		SocioEstructura socioEstructura = null;
		SocioEstructura socioEstructuraOrigen = null;
		EfectuadoConcepto efectuadoConcepto = null;
		EstructuraDetalle lEstructuraDetalle = null;
		Boolean blnAgregarSocio = Boolean.FALSE;
		Boolean blnAgregarNoSocio = Boolean.FALSE;		
		cargarUsuario();
		try
		{	
			SocioFacadeRemote remoteSocio 		    = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			CuentaFacadeRemote remoteCuenta 	    = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			EnviomontoBO boEnviomonto			    = (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
			EnvioresumenBO boEnvioresumen		    = (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);			
			EnvioconceptoBO boEnvioconcepto 	    = (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);
			PersonaFacadeRemote remotePersona       = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
			FileInputStream file = new FileInputStream(new File(target));			
			HSSFWorkbook workbook = new HSSFWorkbook(file);			
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			//sucursal
			HSSFRow row1 = sheet.getRow(0);
			HSSFCell cellb1 = row1.getCell(1);
			String strSucursal = cellb1.getStringCellValue();
			dtoDeEfectuadoConArchivo.setJuridicaSucursal(new Juridica());
			dtoDeEfectuadoConArchivo.getJuridicaSucursal().setStrRazonSocial(strSucursal);
			log.debug("strSucursal: "+strSucursal);
			
			//U.E
			HSSFRow row2 = sheet.getRow(1);
			HSSFCell cellb2 = row2.getCell(1);
			String strUE = cellb2.getStringCellValue();
			dtoDeEfectuadoConArchivo.getEstructura().getJuridica().setStrRazonSocial(strUE);
			log.debug("strUE: "+strUE);
			
			//Modalidad
			HSSFCell celld4 = row2.getCell(3);
			String strModalidad = celld4.getStringCellValue();
			dtoDeEfectuadoConArchivo.setStrModalidad(strModalidad);
			log.debug("strModalidad: "+strModalidad);
			dtoDeEfectuado.setStrModalidad(strModalidad);
			if(strModalidad.compareTo("Haberes")==0)
			{
				intModalidad = 1;
			}
			else if(strModalidad.compareTo("Incentivo")==0)
			{
				intModalidad = 2;
			}
			else if(strModalidad.compareTo("Cas")==0)
			{
				intEfectuado = Constante.intCesanteCAS;
				intModalidad = 3;
			}
			
			//Periodo
			HSSFRow row3 = sheet.getRow(2);
			HSSFCell cell1b2 = row3.getCell(1);
			String strNivelCodigo = cell1b2.getStringCellValue();
			log.debug("strNivelCodigo: "+strNivelCodigo);
			
			intNivelUE = Integer.parseInt(strNivelCodigo.substring(0, 2));
			log.debug("strNivel: "+intNivelUE);
			
			intCodigoUE = Integer.parseInt(strNivelCodigo.substring(2, strNivelCodigo.length()));
			log.debug("intCodigoUE: "+intCodigoUE);
			
			HSSFCell cell2b4= row3.getCell(3);
			cell2b4.setCellType(Cell.CELL_TYPE_STRING);
			String strPeriodo = cell2b4.getStringCellValue();
			intPeriodo = Integer.parseInt(strPeriodo);
			log.debug("strPeriodo: "+strPeriodo);
			//dtoDeEfectuado.intPeriodoMes
			Integer intmmes= Integer.parseInt(strPeriodo.substring(4,6));
			log.debug("mes:"+intmmes);
			Integer intanio = Integer.parseInt(strPeriodo.substring(0,4));
			log.debug("intanio:"+intanio);
			dtoDeEfectuadoConArchivo.setIntPeriodoMes(intmmes);
			dtoDeEfectuadoConArchivo.setIntPeriodoAnio(intanio);
			
			//Tipo Socio
			HSSFRow row5 = sheet.getRow(4);
			HSSFCell cellE5= row5.getCell(4);
			
			String strTipoSocio = cellE5.getStringCellValue();
			if(strTipoSocio.compareTo("A")==0)
			{
				strTipoSocio = "Activo";
				intTipoSocio = 1;
			}
			else if(strTipoSocio.compareTo("C")==0)
			{
				intTipoSocio = 2;
				strTipoSocio = "Cesante";
				intEfectuado = Constante.intCesanteCAS;
			}
			dtoDeEfectuadoConArchivo.setStrTipoSocio(strTipoSocio);
			log.debug("strtiposocio: "+strTipoSocio);			
			
			if(strTipoSocioConArchivo != null)
			{
				log.debug("strTipoSocioConArchivo: "+strTipoSocioConArchivo);
				
			if(strTipoSocioConArchivo.compareTo(strTipoSocio) == 0)
			{				
			
			List<Envioresumen> listaEnvioresumen = boEnvioresumen.getListaEnvioREfectuadoConArchivo(EMPRESA_USUARIO,
																									intNivelUE,
																									intCodigoUE,
																									intTipoSocio,
																									intModalidad);
			if(listaEnvioresumen != null && !listaEnvioresumen.isEmpty())
			{					
					envioresumen = listaEnvioresumen.get(0);
																				 
					if(intPeriodo.compareTo(envioresumen.getIntPeriodoplanilla())==0)
					{
						log.debug("PERIODO MAXIMO ES IGUAL AL EXCEL");
						
						for(Envioresumen enre :listaEnvioresumen)
						{
							efectuadoResumen = new  EfectuadoResumen();
							efectuadoResumen.getId().setIntEmpresa(EMPRESA_USUARIO);
							efectuadoResumen.setIntPeriodoPlanilla(enre.getIntPeriodoplanilla());
							efectuadoResumen.setIntTiposocioCod(intTipoSocio);
							efectuadoResumen.setIntModalidadCod(intModalidad);
							efectuadoResumen.setIntNivel(intNivelUE);
							efectuadoResumen.setIntCodigo(intCodigoUE);
							efectuadoResumen.setIntIdsucursaladministraPk(enre.getIntIdsucursaladministraPk());
							efectuadoResumen.setIntIdsubsucursaladministra(enre.getIntIdsubsucursaladministra());
							efectuadoResumen.setIntIdsucursalprocesaPk(enre.getIntIdsucursalprocesaPk());
							efectuadoResumen.setIntIdsubsucursalprocesaPk(enre.getIntIdsubsucursalprocesaPk());
							efectuadoResumen.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							efectuadoResumen.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
							efectuadoResumen.setIntPersonausuarioPk(PERSONA_USUARIO);
							efectuadoResumen.setBdMontoTotal(new BigDecimal(0));
							efectuadoResumen.setIntNumeroAfectados(0);
							efectuadoResumen.setIntParaEstadoPago(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							listaEfectuadoResumenPorAdministra.add(efectuadoResumen);
						}														
							Iterator<Row> rowIterator = sheet.iterator();
							while(rowIterator.hasNext())
							{
								Row row = rowIterator.next();
								if(new Integer(row.getRowNum()).compareTo(4)== 0 ||
								   new Integer(row.getRowNum()).compareTo(4)== 1)
								{
									blnAgregarSocio = Boolean.FALSE;
									if(row.getCell(1) != null)
									{
										efectuadoConArchivo = new EfectuadoConceptoComp();
										efectuadoConArchivo.setBlnAgregarSocio(Boolean.FALSE);
										efectuadoConArchivo.setBlnAgregarNoSocio(Boolean.FALSE);											
										efectuadoConArchivo.getEfectuado().setBlnListaEnvioConcepto(Boolean.FALSE);
										// CODIGO
										Cell cell0 = row.getCell(0);
										cell0.setCellType(Cell.CELL_TYPE_STRING);
										String strCodigo = cell0 .getStringCellValue();
										// DNI
										Cell cell1 = row.getCell(1);
										String strDNI = cell1.getStringCellValue();
										efectuadoConArchivo.getDocumento().setStrNumeroIdentidad(strDNI);
										log.debug("strDNI: "+strDNI);
										//MONTO
										Cell cell3 = row.getCell(3);
										BigDecimal original = new BigDecimal(cell3.getNumericCellValue());
										BigDecimal scaled = original.setScale(2,BigDecimal.ROUND_HALF_UP);
										bdMontoTotal = bdMontoTotal.add(scaled);
										
										efectuadoConArchivo.getSocio().setId(new SocioPK());
										efectuadoConArchivo.getSocio().getId().setIntIdPersona(Integer.parseInt(strCodigo));											
										SocioPK socioPK = new SocioPK();
										socioPK.setIntIdEmpresa(EMPRESA_USUARIO);
										socioPK.setIntIdPersona(efectuadoConArchivo.getSocio().getId().getIntIdPersona());
										Socio socio = remoteSocio.getSocioPorPK(socioPK);
										if(socio != null)
										{	
											efectuadoConArchivo.getEfectuado().setBlnTieneSocio(Boolean.TRUE);
											efectuadoConArchivo.getEfectuado().setBlnNoTieneSocio(Boolean.FALSE);
											efectuadoConArchivo.getEfectuado().setPersona(new Persona());
											efectuadoConArchivo.getEfectuado().getPersona().setIntIdPersona(socio.getId().getIntIdPersona());
											efectuadoConArchivo.getEfectuado().setIntPersonaIntegrante(socio.getId().getIntIdPersona());
											listaSocioEstructura = remoteSocio.getListaXSocioPKActivoTipoSocio(socio.getId().getIntIdPersona(),
																											  socio.getId().getIntIdEmpresa(),
																											  Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO,
																											  intTipoSocio,
																											  intNivelUE,
																											  intCodigoUE);
											if(listaSocioEstructura!= null && !listaSocioEstructura.isEmpty())
											{													
												socio.setListSocioEstructura(listaSocioEstructura);
												efectuadoConArchivo.setSocio(socio);
												efectuadoConArchivo.getEfectuado().setSocio(socio);
												
												lEstructuraId 		= new EstructuraId();
												lEstructuraId.setIntCodigo(intCodigoUE);
												lEstructuraId.setIntNivel(intNivelUE);
												
												lEstructuraDetalle = remoteEstructura.getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(lEstructuraId,
																																				   Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA,							
																																				   intTipoSocio,
																																				   intModalidad);
												
												//obteniendo su cuenta												
												cuentaIntegranteRspta = remoteCuenta.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(socio.getId(),
																																			Constante.PARAM_T_TIPOCUENTA_ACTIVA,																
																																			Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
												if(cuentaIntegranteRspta != null)
												{
													//traerme el montoenvio de Enviomonto
													intCuenta = cuentaIntegranteRspta.getId().getIntCuenta();
													efectuadoConArchivo.getEfectuado().setBlnEfectuadoConcepto0(Boolean.FALSE);
													efectuadoConArchivo.getEfectuado().getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
													efectuadoConArchivo.getEfectuado().setBlnListaEnvioConcepto(Boolean.FALSE);
													efectuadoConArchivo.getEfectuado().setIntPeriodoPlanilla(intPeriodo);													
													efectuadoConArchivo.getEfectuado().setIntTiposocioCod(intTipoSocio);
													efectuadoConArchivo.getEfectuado().setIntModalidadCod(intModalidad);
													efectuadoConArchivo.getEfectuado().setIntNivel(intNivelUE);
													efectuadoConArchivo.getEfectuado().setIntCodigo(intCodigoUE);													
													efectuadoConArchivo.getEfectuado().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
													efectuadoConArchivo.getEfectuado().setIntCuentaPk(intCuenta);
													
													List<Enviomonto> listaEnviomonto = boEnviomonto.getEnviomontoPorInt(EMPRESA_USUARIO,
																														intPeriodo,
																														intCuenta,
																														intTipoSocio,
																														intModalidad,
																														intNivelUE,
																														intCodigoUE);
													if(listaEnviomonto !=null && !listaEnviomonto.isEmpty())
													{													
														Enviomonto  enviommonto = listaEnviomonto.get(0);
														if(enviommonto != null)
														{
															if(efectuadoConArchivo.getEfectuado().getIntIdsucursaladministraPk() == null)
															{
																efectuadoConArchivo.getEfectuado().setIntTipoestructuraCod(enviommonto.getIntTipoestructuraCod());
																efectuadoConArchivo.getEfectuado().setIntEmpresasucprocesaPk(enviommonto.getIntEmpresasucprocesaPk());
																efectuadoConArchivo.getEfectuado().setIntEmpresasucadministraPk(enviommonto.getIntEmpresasucadministraPk());
																efectuadoConArchivo.getEfectuado().setIntIdsucursaladministraPk(enviommonto.getIntIdsucursaladministraPk());
																efectuadoConArchivo.getEfectuado().setIntIdsubsucursaladministra(enviommonto.getIntIdsubsucursaladministra());
																efectuadoConArchivo.getEfectuado().setIntIdsucursalprocesaPk(enviommonto.getIntIdsucursalprocesaPk());
																efectuadoConArchivo.getEfectuado().setIntIdsubsucursalprocesaPk(enviommonto.getIntIdsubsucursalprocesaPk());
																efectuadoConArchivo.getEfectuado().setIntEmpresausuarioPk(enviommonto.getIntEmpresausuarioPk());
																efectuadoConArchivo.getEfectuado().setTsFecharegistro(enviommonto.getTsFecharegistro());
																efectuadoConArchivo.getEfectuado().setIntPersonausuarioPk(enviommonto.getIntPersonausuarioPk());
																efectuadoConArchivo.getEfectuado().setIntParaTipoIngresoDatoCod(Constante.PARAM_ParaTipoIngresoDatoConArchivo);																
															}														
															efectuadoConArchivo.getEfectuado().setEnvioMonto(enviommonto);
														}														
														
														for(Enviomonto x:listaEnviomonto)
														{
															EnvioconceptoId pId = new EnvioconceptoId();
															pId.setIntEmpresacuentaPk(x.getId().getIntEmpresacuentaPk());
															pId.setIntItemenvioconcepto(x.getId().getIntItemenvioconcepto());
															listaEnvioConcepto = boEnvioconcepto.getEnvioconceptoPorItemEnvioConcepto(pId);
															if(listaEnvioConcepto != null && !listaEnvioConcepto.isEmpty())
															{
																Envioconcepto enconcepto = listaEnvioConcepto.get(0);
																efectuadoConArchivo.getEfectuado().setBlnListaEnvioConcepto(Boolean.TRUE);
																if(enconcepto.getIntIndilicencia().compareTo(1) == 0)
																{
																	efectuadoConArchivo.getEfectuado().setBlnLIC(Boolean.TRUE);	
																}else if(enconcepto.getIntIndilicencia().compareTo(0)==0)
																{
																	efectuadoConArchivo.getEfectuado().setBlnLIC(Boolean.FALSE);
																}
																if(enconcepto.getIntIndidescjudi().compareTo(1)==0)
																{
																	efectuadoConArchivo.getEfectuado().setBlnDJUD(Boolean.TRUE);	
																}else if(enconcepto.getIntIndidescjudi().compareTo(0)==0)
																{
																	efectuadoConArchivo.getEfectuado().setBlnDJUD(Boolean.FALSE);	
																}
																if(enconcepto.getIntIndicienpor().compareTo(1)==0)
																{
																	efectuadoConArchivo.getEfectuado().setBlnCartaAutorizacion(Boolean.TRUE);	
																}else if(enconcepto.getIntIndicienpor().compareTo(0)==0)
																{
																	efectuadoConArchivo.getEfectuado().setBlnCartaAutorizacion(Boolean.FALSE);
																}																
															}															
														}
														log.debug("strCodigo: "+strCodigo);
														
														
														efectuadoConArchivo.getEfectuado().setBlnAgregarNoSocio(Boolean.FALSE);
														efectuadoConArchivo.getEfectuado().setBdMontoEfectuado(scaled);
														efectuadoConArchivo.getEfectuado().setBlnEfectuadoConcepto0(Boolean.FALSE);													
														log.debug("dMonto: "+scaled);
														listaEfectuado.add(efectuadoConArchivo.getEfectuado());
														
														for(EfectuadoResumen eerr: listaEfectuadoResumenPorAdministra)
														{
															if(eerr.getIntIdsucursaladministraPk().compareTo(efectuadoConArchivo.getEfectuado().getIntIdsucursaladministraPk()) == 0 &&
															   eerr.getIntIdsubsucursaladministra().compareTo(efectuadoConArchivo.getEfectuado().getIntIdsubsucursaladministra()) == 0)
															{
																eerr.getListaEfectuado().add(efectuadoConArchivo.getEfectuado());
																eerr.setIntNumeroAfectados(eerr.getIntNumeroAfectados()+1);
																eerr.setBdMontoTotal(eerr.getBdMontoTotal().add(efectuadoConArchivo.getEfectuado().getBdMontoEfectuado()));																
																listaEfectuadoConceptoComp.add(efectuadoConArchivo);																
																break;
															}
														}													
													}else
													{														 
														//se a agregado un agregar socio que no tiene su enviado hay que comprobar
														log.debug("se a agregado un agregar socio que no tiene su enviado "
																  +efectuadoConArchivo.getSocio().getId().getIntIdPersona());
														efectuadoConArchivo.setBlnAgregarSocio(Boolean.TRUE);
														registroSeleccionadoEnAE = new EfectuadoConceptoComp();	
														registroSeleccionadoEnAE.getEfectuado().getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
														registroSeleccionadoEnAE.getEfectuado().setIntTiposocioCod(intTipoSocio);
														registroSeleccionadoEnAE.getEfectuado().setIntModalidadCod(intModalidad);
														registroSeleccionadoEnAE.getEfectuado().setIntNivel(intNivelUE);
														registroSeleccionadoEnAE.getEfectuado().setIntCodigo(intCodigoUE);
														registroSeleccionadoEnAE.getEfectuado().setEnvioMonto(new Enviomonto());
														registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
														registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntEmpresausuarioPk(EMPRESA_USUARIO);
														registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntModalidadCod(intModalidad);														
														registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntNivel(intNivelUE);
														registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntCodigo(intCodigoUE);
														registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setBlnTieneNuevoEnvioresumen(Boolean.FALSE);
														registroSeleccionadoEnAE.getEfectuado().setBlnAgregarNoSocio(Boolean.FALSE);
														registroSeleccionadoEnAE.getEfectuado().setIntPeriodoPlanilla(intPeriodo);
														registroSeleccionadoEnAE.getEfectuado().setIntCuentaPk(cuentaIntegranteRspta.getId().getIntCuenta());
														registroSeleccionadoEnAE.getEfectuado().setIntParaTipoIngresoDatoCod(Constante.PARAM_ParaTipoIngresoDatoSocio);
														registroSeleccionadoEnAE.getEfectuado().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
														registroSeleccionadoEnAE.getEfectuado().setTsFecharegistro(UtilCobranza.obtieneFechaActualEnTimesTamp());
														registroSeleccionadoEnAE.getEfectuado().setBdMontoEfectuado(scaled.setScale(2,BigDecimal.ROUND_HALF_UP));
														if(socio != null && socio.getListSocioEstructura() != null){
															for(SocioEstructura socioEst: socio.getListSocioEstructura()){
																if(socioEst.getIntTipoEstructura().compareTo(1) == 0){
																	socioEstructuraOrigen = socioEst;
																}
															}
															registroSeleccionadoEnAE.setSocio(socio);
															registroSeleccionadoEnAE.getEfectuado().setIntEmpresasucadministraPk(socioEstructuraOrigen.getIntEmpresaSucAdministra());
															registroSeleccionadoEnAE.getEfectuado().setIntIdsucursaladministraPk(socioEstructuraOrigen.getIntIdSucursalAdministra());
															registroSeleccionadoEnAE.getEfectuado().setIntIdsubsucursaladministra(socioEstructuraOrigen.getIntIdSubsucurAdministra());
															if(lEstructuraDetalle != null){
																registroSeleccionadoEnAE.getEfectuado().setIntEmpresasucprocesaPk(lEstructuraDetalle.getIntPersEmpresaPk());
																registroSeleccionadoEnAE.getEfectuado().setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
																registroSeleccionadoEnAE.getEfectuado().setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
																registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntEmpresasucprocesaPk(lEstructuraDetalle.getIntPersEmpresaPk());
																registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
																registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
															}	
															registroSeleccionadoEnAE.getEfectuado().setIntTipoestructuraCod(socioEstructuraOrigen.getIntTipoEstructura());
															registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntTipoestructuraCod(socioEstructuraOrigen.getIntTipoEstructura());
															registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntEmpresasucadministraPk(socioEstructuraOrigen.getIntEmpresaSucAdministra());
															registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntIdsucursaladministraPk(socioEstructuraOrigen.getIntIdSucursalAdministra());
															registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setIntIdsubsucursaladministra(socioEstructuraOrigen.getIntIdSubsucurAdministra());
														}													
														registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setBdMontoenvio(scaled.setScale(2,BigDecimal.ROUND_HALF_UP));
														registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setListaEnvioConcepto(new ArrayList<Envioconcepto>());
														registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setTsFecharegistro(UtilCobranza.obtieneFechaActualEnTimesTamp());
														registroSeleccionadoEnAE.setCuentaIntegrante(cuentaIntegranteRspta);
														
														registroSeleccionadoEnAE.getDocumento().setStrNumeroIdentidad(strDNI);
														calcularAgregarEnviado();
														agregarALaPlanilla();
														
														for(EfectuadoResumen eerr: listaEfectuadoResumenPorAdministra)
														{
															if(eerr.getIntIdsucursaladministraPk().compareTo(socio.getListSocioEstructura().get(0).getIntIdSucursalAdministra()) == 0 &&
															   eerr.getIntIdsubsucursaladministra().compareTo(socio.getListSocioEstructura().get(0).getIntIdSubsucurAdministra()) == 0)
															{
																registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setEnvioresumen(envioresumen);
																eerr.getListaEfectuado().add(registroSeleccionadoEnAE.getEfectuado());																
																eerr.setIntNumeroAfectados(eerr.getIntNumeroAfectados()+1);
																eerr.setBdMontoTotal(eerr.getBdMontoTotal().add(registroSeleccionadoEnAE.getEfectuado().getBdMontoEfectuado()));
																//listaEfectuadoConceptoComp.add(registroSeleccionadoEnAE);
																blnAgregarSocio = Boolean.TRUE;
																break;
															}
														}
														if(!blnAgregarSocio){ 
															//crear nuevo efectuadoResumen Y UN envioresumen
															envResSocAgregado = new Envioresumen();
															envResSocAgregado.setId(new EnvioresumenId());
															envResSocAgregado.getId().setIntEmpresaPk(EMPRESA_USUARIO);
															envResSocAgregado.setIntDocumentogeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAENVIADA);
															envResSocAgregado.setIntPeriodoplanilla(intPeriodo);
															envResSocAgregado.setBdMontototal(registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().getBdMontoenvio());							
															envResSocAgregado.setIntNumeroafectados(1);
															envResSocAgregado.setIntTiposocioCod(intTipoSocio);
															envResSocAgregado.setIntModalidadCod(intModalidad);
															envResSocAgregado.setIntNivel(intNivelUE);
															envResSocAgregado.setIntCodigo(intCodigoUE);
															envResSocAgregado.setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
															envResSocAgregado.setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
															envResSocAgregado.setIntIdsucursaladministraPk(registroSeleccionadoEnAE.getEfectuado().getIntIdsucursaladministraPk());
															envResSocAgregado.setIntIdsubsucursaladministra(registroSeleccionadoEnAE.getEfectuado().getIntIdsubsucursaladministra());
															envResSocAgregado.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
															registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setBlnTieneNuevoEnvioresumen(Boolean.TRUE);
															registroSeleccionadoEnAE.getEfectuado().getEnvioMonto().setEnvioresumen(envResSocAgregado);
															
															efectuadoResumen = new  EfectuadoResumen();
															efectuadoResumen.getId().setIntEmpresa(EMPRESA_USUARIO);
															efectuadoResumen.setIntPeriodoPlanilla(intPeriodo);
															efectuadoResumen.setIntTiposocioCod(intTipoSocio);
															efectuadoResumen.setIntModalidadCod(intModalidad);
															efectuadoResumen.setIntNivel(intNivelUE);
															efectuadoResumen.setIntCodigo(intCodigoUE);
															efectuadoResumen.setIntIdsucursaladministraPk(registroSeleccionadoEnAE.getEfectuado().getIntIdsucursaladministraPk());
															efectuadoResumen.setIntIdsubsucursaladministra(registroSeleccionadoEnAE.getEfectuado().getIntIdsubsucursaladministra());
															efectuadoResumen.setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
															efectuadoResumen.setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
															efectuadoResumen.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
															efectuadoResumen.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
															efectuadoResumen.setIntPersonausuarioPk(PERSONA_USUARIO);
															efectuadoResumen.setBdMontoTotal(new BigDecimal(0));
															efectuadoResumen.setIntNumeroAfectados(0);
															efectuadoResumen.setIntParaEstadoPago(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
															efectuadoResumen.getListaEfectuado().add(registroSeleccionadoEnAE.getEfectuado());
															listaEfectuadoResumenPorAdministra.add(efectuadoResumen);															 
														}
														continue;
													}
												}else
												{	
													log.debug("puede ser un no socio confirmarlo");
													log.debug("codPersona: "+efectuadoConArchivo.getSocio().getId().getIntIdPersona());
													log.debug("empresa: "+efectuadoConArchivo.getSocio().getId().getIntIdEmpresa());
													PersonaEmpresaPK p = new PersonaEmpresaPK();
													p.setIntIdEmpresa(efectuadoConArchivo.getSocio().getId().getIntIdEmpresa());
													p.setIntIdPersona(efectuadoConArchivo.getSocio().getId().getIntIdPersona());
													List<PersonaRol> listaPersonarol = remotePersona.listaPersonaRolOfCobranza(p);
													if(listaPersonarol !=null && !listaPersonarol.isEmpty())
													{
														//si es 8 es un no socio entonces sigo
														if(Constante.PARAM_T_PERSONA_ROL.compareTo(listaPersonarol.get(0).getId().getIntParaRolPk())==0){
															log.debug("es 8 es un no socio entonces sigo");
															efectuadoConArchivo.setBlnAgregarNoSocio(Boolean.TRUE);															
															efectuadoConArchivo.getEfectuado().setBlnAgregarNoSocio(Boolean.TRUE);
															
															efectuadoConArchivo.getDocumento().setStrNumeroIdentidad(strDNI);
															log.debug("strDNI: "+strDNI);	
															
															if(efectuadoConArchivo.getSocio().getListSocioEstructura() != null){
																for(SocioEstructura xx: efectuadoConArchivo.getSocio().getListSocioEstructura()){
																	if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(xx.getIntModalidad()) == 0){
																		socioEstructura = xx;
																	}																	
																}															
																	envioconcepto = new Envioconcepto();
																	envioconcepto.setId(new EnvioconceptoId());					
																	envioconcepto.getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
																	envioconcepto.setIntPeriodoplanilla(intPeriodo);																		
																	envioconcepto.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
																	envioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);	
																	envioconcepto.setBdMontoconcepto(scaled);
																	envioconcepto.setIntIndicienpor(0);
																	envioconcepto.setIntIndidescjudi(0);
																	envioconcepto.setIntIndilicencia(0);
																	listaEnvioconcepto.add(envioconcepto);
																	
																	efectuadoConcepto = new EfectuadoConcepto();
																	efectuadoConcepto.getId().setIntEmpresaCuentaEnvioPk(EMPRESA_USUARIO);
																	efectuadoConcepto.setIntTipoConceptoGeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
																	efectuadoConcepto.setBdMontoConcepto(scaled);
																	efectuadoConcepto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
																	
																	enviomonto = new Enviomonto();
																	enviomonto.setBdMontoenvio(scaled);
																	enviomonto.setId(new EnviomontoId());
															    	enviomonto.getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);			    	
															    	enviomonto.setIntTiposocioCod(intTipoSocio);
															    	enviomonto.setIntModalidadCod(intModalidad);
															    	enviomonto.setIntNivel(intNivelUE);
															    	enviomonto.setIntCodigo(intCodigoUE);
															    	enviomonto.setIntTipoestructuraCod(socioEstructura.getIntTipoEstructura());				    	
															    	enviomonto.setIntEmpresasucadministraPk(socioEstructura.getIntEmpresaSucAdministra());
															    	enviomonto.setIntIdsucursaladministraPk(socioEstructura.getIntIdSucursalAdministra());
															    	enviomonto.setIntIdsubsucursaladministra(socioEstructura.getIntIdSubsucurAdministra());	
															    	enviomonto.setIntEmpresausuarioPk(socioEstructura.getIntEmpresaUsuario());
															    	enviomonto.setIntPersonausuarioPk(socioEstructura.getIntPersonaUsuario());
															    	if(lEstructuraDetalle != null)
															    	{
															    		enviomonto.setIntEmpresasucprocesaPk(lEstructuraDetalle.getIntPersEmpresaPk());
																    	enviomonto.setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
																    	enviomonto.setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
															    	}
															    	enviomonto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
															    	enviomonto.setListaEnvioConcepto(new ArrayList<Envioconcepto>());
															    	enviomonto.setListaEnvioConcepto(listaEnvioconcepto);
															    	enviomonto.setEnvioresumen(new Envioresumen());
															    	enviomonto.setBlnTieneNuevoEnvioresumen(Boolean.FALSE);
															    //	enviomonto.setEnvioresumen(envioresumen);
															    	
															    	efectuadoConArchivo.getEfectuado().getId().setIntEmpresacuentaPk(EMPRESA_USUARIO);
															    	efectuadoConArchivo.getEfectuado().setBdMontoEfectuado(scaled);
															    	efectuadoConArchivo.getEfectuado().setBlnEfectuadoConcepto0(Boolean.FALSE);
															    	efectuadoConArchivo.getEfectuado().setBlnListaEnvioConcepto(Boolean.FALSE);
															    	efectuadoConArchivo.getEfectuado().setEnvioMonto(enviomonto);
															    	efectuadoConArchivo.getEfectuado().setIntParaTipoIngresoDatoCod(Constante.PARAM_ParaTipoIngresoDatoNoSocio);
															    	efectuadoConArchivo.getEfectuado().setListaEfectuadoConcepto(new ArrayList<EfectuadoConcepto>());
															    	efectuadoConArchivo.getEfectuado().getListaEfectuadoConcepto().add(efectuadoConcepto);
															    	
															    	for(EfectuadoResumen eerr: listaEfectuadoResumenPorAdministra)
																	{
															    		if(eerr.getIntIdsucursaladministraPk() != null && 
															    				eerr.getIntIdsubsucursaladministra() != null){
															    			if(eerr.getIntIdsucursaladministraPk().compareTo(socioEstructura.getIntIdSucursalAdministra()) == 0 &&
																					eerr.getIntIdsubsucursaladministra().compareTo(socioEstructura.getIntIdSubsucurAdministra()) == 0)
																				{																					
															    					efectuadoConArchivo.getEfectuado().getEnvioMonto().setEnvioresumen(envioresumen);
															    					eerr.getListaEfectuado().add(efectuadoConArchivo.getEfectuado());
																					eerr.setIntNumeroAfectados(eerr.getIntNumeroAfectados()+1);
																					eerr.setBdMontoTotal(eerr.getBdMontoTotal().add(efectuadoConArchivo.getEfectuado().getBdMontoEfectuado()));																			
																					listaEfectuadoConceptoComp.add(efectuadoConArchivo);
																					blnAgregarNoSocio = Boolean.TRUE;
																					break;
																				}
															    		}
																		
																	}
															    	if(!blnAgregarNoSocio){
															    		//crear nuevo efectuadoResumen crear nuevo envioresumen 
															    		envResNoSoc = new Envioresumen();
															    		envResNoSoc.setId(new EnvioresumenId());
															    		envResNoSoc.getId().setIntEmpresaPk(EMPRESA_USUARIO);
															    		envResNoSoc.setIntDocumentogeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAENVIADA);
															    		envResNoSoc.setIntPeriodoplanilla(intPeriodo);
															    		envResNoSoc.setBdMontototal(enviomonto.getBdMontoenvio());							
															    		envResNoSoc.setIntNumeroafectados(1);
															    		envResNoSoc.setIntTiposocioCod(intTipoSocio);
															    		envResNoSoc.setIntModalidadCod(intModalidad);
															    		envResNoSoc.setIntNivel(intNivelUE);
															    		envResNoSoc.setIntCodigo(intCodigoUE);
															    		envResNoSoc.setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
															    		envResNoSoc.setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
															    		envResNoSoc.setIntIdsucursaladministraPk(socioEstructura.getIntIdSucursalAdministra());
															    		envResNoSoc.setIntIdsubsucursaladministra(socioEstructura.getIntIdSubsucurAdministra());
															    		envResNoSoc.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);	
															    		efectuadoConArchivo.getEfectuado().getEnvioMonto().setBlnTieneNuevoEnvioresumen(Boolean.TRUE);
															    		efectuadoConArchivo.getEfectuado().getEnvioMonto().setEnvioresumen(envResNoSoc);
															    		
															    		efectuadoResumen = new  EfectuadoResumen();
																		efectuadoResumen.getId().setIntEmpresa(EMPRESA_USUARIO);
																		efectuadoResumen.setIntPeriodoPlanilla(intPeriodo);
																		efectuadoResumen.setIntTiposocioCod(intTipoSocio);
																		efectuadoResumen.setIntModalidadCod(intModalidad);
																		efectuadoResumen.setIntNivel(intNivelUE);
																		efectuadoResumen.setIntCodigo(intCodigoUE);
																		efectuadoResumen.setIntIdsucursaladministraPk(socioEstructura.getIntIdSucursalAdministra());
																		efectuadoResumen.setIntIdsubsucursaladministra(socioEstructura.getIntIdSubsucurAdministra());
																		efectuadoResumen.setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
																		efectuadoResumen.setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
																		efectuadoResumen.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
																		efectuadoResumen.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
																		efectuadoResumen.setIntPersonausuarioPk(PERSONA_USUARIO);
																		efectuadoResumen.setBdMontoTotal(new BigDecimal(0));
																		efectuadoResumen.setIntNumeroAfectados(0);
																		efectuadoResumen.getListaEfectuado().add(efectuadoConArchivo.getEfectuado());
																		efectuadoResumen.setIntParaEstadoPago(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
																		listaEfectuadoResumenPorAdministra.add(efectuadoResumen);
																		listaEfectuadoConceptoComp.add(efectuadoConArchivo);
															    	}
															}else{
																log.debug("no tiene socioescturctura");
															}
														}
													}
													continue;
												}
											}else
											{
												continue;
											}											
										}else
										{
											
											continue;
										}						
																					
									}					
								}
							}
							
						setPlanillaEfectuadaResumen(listaEfectuadoResumenPorAdministra);
						if(listaEfectuadoConceptoComp != null && !listaEfectuadoConceptoComp.isEmpty())
						{	
							datosValidados2ConArchivo = Boolean.TRUE;
							datosValidadosConArchivo  = Boolean.FALSE;									
						}
						
						file.close();
						FileOutputStream out = new FileOutputStream(new File(target));								
						workbook.write(out);
						out.close();
					    File  filea =new File(target);							    
					    if(filea.delete())
					    {
					    	log.debug("esta borrado");	
					    }					
					}
					else
					{
						file.close();
						FileOutputStream out = new FileOutputStream(new File(target));
						workbook.write(out);
						out.close();
						File  filea =new File(target);							    
						if(filea.delete()){}
						mostrarMensaje(Boolean.FALSE, "  El periodo es distinto al efectuarse.");
						return;
					}								
			    }
			}
			else 
			{
				mostrarMensaje(Boolean.FALSE, "  El tipo de socio es distinto.");
				return;
			}
		  }	
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
		
	public void quitarDocumento()
	{
		try
		{
			archivo = null;			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
		
	public BigDecimal getTotalEnviadoEnYaEfectuadaEnEmpezandoEfectuada()
	{
		BigDecimal ret= new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();	
			if(item.getYaEfectuado() != null)
			{
				if(item.getYaEfectuado().getEnvioMonto() != null )
				{
					if(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio() != null )
					{
						ret = ret.add(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio());													
					}					
				}				
			}			
		}
		 
		return ret;
	}
	
	public Integer getNroEfectuadoEnTieneEfectuada()
	{
		Integer ret = 0;
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			if(item.getEfectuado()!= null)
			{
				if(item.getEfectuado().getEnvioMonto() != null)
				{
					ret++;	
				}				
			}
		}
		 
		return ret;
	}
	
	public Integer getNroEnviadoEnTieneEfectuada()
	{
		Integer ret = 0;
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			if(item.getEfectuado() != null)
			{
				if(item.getEfectuado().getEnvioMonto()!= null)
				{
					ret++;
				}
			}
		}
		 
		return ret;
	}
	
	public BigDecimal getTotalEnviadoEnAEfectuar()
	{
		BigDecimal ret= new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();	
			
				if(item.getEfectuado().getEnvioMonto() != null)
				{
					if(item.getEfectuado().getEnvioMonto().getBdMontoenvio() != null)
					{						
						ret = ret.add(item.getEfectuado().getEnvioMonto().getBdMontoenvio());
					}					
				}		
		}
		 
		return ret;
	}
	
	public BigDecimal getTotalEfectuadoEnAEfectuar()
	{
		BigDecimal ret= new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			if(item.getEfectuado() != null)
			{
				if(item.getEfectuado().getBdMontoEfectuado() != null)
				{
					ret = ret.add(item.getEfectuado().getBdMontoEfectuado());	
				}
			}			
		}
		 
		return ret;
	}
	
	public BigDecimal getTotalEnviadoEnYaEfectuada()
	{
		BigDecimal ret= new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();	
			if(item.getYaEfectuado() != null)
			{
				if(item.getYaEfectuado().getEnvioMonto() != null)
				{
					if(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio() != null)
					{
						ret = ret.add(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio());
					}
				}
			}			
		}
		 
		return ret;
	}
	
	public BigDecimal getTotalEfectuadoEnYaEfectuada()
	{
		BigDecimal ret= new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();			
			if(item.getYaEfectuado() != null)
			{
				if(item.getYaEfectuado().getBdMontoEfectuado() != null)
				{
					ret = ret.add(item.getYaEfectuado().getBdMontoEfectuado());
				}
			}			
		}
		 
		return ret;
	}
	
	public BigDecimal getTotalDiferenciaEnYaEfectuada()
	{
		BigDecimal ret= new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			if(item.getYaEfectuado() != null)
			{
				if(item.getYaEfectuado().getBdDiferencia() != null)
				{
					ret = ret.add(item.getYaEfectuado().getBdDiferencia());	
				}
			}			
		}
		 
		return ret;
	}
	
	public BigDecimal getTotalesAEnviar()
	{
		BigDecimal ret = new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();	
			if(item.getEfectuado() != null)
			{
				if(item.getEfectuado().getEnvioMonto() != null)
				{
					if(item.getEfectuado().getEnvioMonto().getBdMontoenvio() != null)
					{
						ret = ret.add(item.getEfectuado().getEnvioMonto().getBdMontoenvio());
						if(item.getYaEfectuado() != null)
						{
							if(item.getYaEfectuado().getEnvioMonto() != null)
							{
								if(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio() != null)
								{
									ret = ret.add(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio());
								}								
							}							
						}
					}
						
				}else
				{
					if(item.getYaEfectuado().getEnvioMonto() != null)
					{
						if(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio() != null)
						{
							ret = ret.add(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio());
						}						
					}
				}
				
			}else
			{
				if(item.getYaEfectuado() != null)
				{
					if(item.getYaEfectuado().getEnvioMonto() != null)
					{
						if(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio() != null)
						{
							ret = ret.add(item.getYaEfectuado().getEnvioMonto().getBdMontoenvio());
						}						
					}					
				}
			}
		}
		 
		return ret;
	}
	
	public BigDecimal getTotalesAEfectuar()
	{
		BigDecimal ret = new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			if(item.getEfectuado() != null)
			{
				if(item.getEfectuado().getBdMontoEfectuado() != null)
				{
					ret = ret.add(item.getEfectuado().getBdMontoEfectuado());
					if(item.getYaEfectuado()!= null)
					{
						if(item.getYaEfectuado().getBdMontoEfectuado() != null)
						{
							ret = ret.add(item.getYaEfectuado().getBdMontoEfectuado());
						}						
					}
				}				
			}else
			{
				if(item.getYaEfectuado()!= null)
				{
					if(item.getYaEfectuado().getBdMontoEfectuado()!= null)
					{
						ret = ret.add(item.getYaEfectuado().getBdMontoEfectuado());
					}					
				}
			}			
		}
		 
		return ret;
	}
	
	public BigDecimal getTotalDiferenciaEnAEfectuar()
	{
		BigDecimal ret= new BigDecimal(0);
		Iterator<EfectuadoConceptoComp> it = listaEfectuadoConceptoComp.iterator();
		while(it.hasNext())
		{
			EfectuadoConceptoComp item = (EfectuadoConceptoComp)it.next();
			if(item.getEfectuado() != null)
			{
				if(item.getEfectuado().getEnvioMonto() != null && item.getEfectuado().getBdMontoEfectuado() != null)
				{
					if(item.getEfectuado().getEnvioMonto().getBdMontoenvio() != null)
					{
						ret = ret.add(item.getEfectuado().getEnvioMonto().getBdMontoenvio().subtract(item.getEfectuado().getBdMontoEfectuado()));	
					}					
				}
			}			
		}
		 
		return ret;
	}
	
	protected HttpServletRequest getRequest()
	{
		return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Integer getPERSONA_USUARIO() {
		return PERSONA_USUARIO;
	}
	public void setPERSONA_USUARIO(Integer pERSONA_USUARIO) {
		PERSONA_USUARIO = pERSONA_USUARIO;
	}
	public Integer getEMPRESA_USUARIO() {
		return EMPRESA_USUARIO;
	}
	public void setEMPRESA_USUARIO(Integer eMPRESA_USUARIO) {
		EMPRESA_USUARIO = eMPRESA_USUARIO;
	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}

	public EfectuadoConceptoComp getDtoFiltroDeEfectuado2() {
		return dtoFiltroDeEfectuado2;
	}

	public void setDtoFiltroDeEfectuado2(EfectuadoConceptoComp dtoFiltroDeEfectuado2) {
		this.dtoFiltroDeEfectuado2 = dtoFiltroDeEfectuado2;
	}

	public List<Tabla> getListaTablaDeSucursal() {
		return listaTablaDeSucursal;
	}

	public void setListaTablaDeSucursal(List<Tabla> listaTablaDeSucursal) {
		this.listaTablaDeSucursal = listaTablaDeSucursal;
	}

	public List<EfectuadoResumen> getListaBusquedaEfectuadoResumen() {
		return listaBusquedaEfectuadoResumen;
	}

	public void setListaBusquedaEfectuadoResumen(
			List<EfectuadoResumen> listaBusquedaEfectuadoResumen) {
		this.listaBusquedaEfectuadoResumen = listaBusquedaEfectuadoResumen;
	}

	public List<Tabla> getListaModalidadPlanilla() {
		return listaModalidadPlanilla;
	}

	public void setListaModalidadPlanilla(List<Tabla> listaModalidadPlanilla) {
		this.listaModalidadPlanilla = listaModalidadPlanilla;
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

	public String getMensajeOperacion() {
		return mensajeOperacion;
	}

	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}

	public boolean isBtnGrabarDisabled() {
		return btnGrabarDisabled;
	}

	public void setBtnGrabarDisabled(boolean btnGrabarDisabled) {
		this.btnGrabarDisabled = btnGrabarDisabled;
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

	public EfectuadoConceptoComp getDtoFiltroDeEfectuado() {
		return dtoFiltroDeEfectuado;
	}

	public void setDtoFiltroDeEfectuado(EfectuadoConceptoComp dtoFiltroDeEfectuado) {
		this.dtoFiltroDeEfectuado = dtoFiltroDeEfectuado;
	}

	public List<Tabla> getListaModalidadPlanillaTemporal() {
		return listaModalidadPlanillaTemporal;
	}

	public void setListaModalidadPlanillaTemporal(
			List<Tabla> listaModalidadPlanillaTemporal) {
		this.listaModalidadPlanillaTemporal = listaModalidadPlanillaTemporal;
	}

	public EnvioConceptoComp getDtoDeEfectuado() {
		return dtoDeEfectuado;
	}

	public void setDtoDeEfectuado(EnvioConceptoComp dtoDeEfectuado) {
		this.dtoDeEfectuado = dtoDeEfectuado;
	}

	public EnvioMsg getMsgMantDeEfectuado() {
		return msgMantDeEfectuado;
	}

	public void setMsgMantDeEfectuado(EnvioMsg msgMantDeEfectuado) {
		this.msgMantDeEfectuado = msgMantDeEfectuado;
	}

	public List<Juridica> getListaJuridicaSucursalDePPEjecutoraEfectuado() {
		return listaJuridicaSucursalDePPEjecutoraEfectuado;
	}

	public void setListaJuridicaSucursalDePPEjecutoraEfectuado(
			List<Juridica> listaJuridicaSucursalDePPEjecutoraEfectuado) {
		this.listaJuridicaSucursalDePPEjecutoraEfectuado = listaJuridicaSucursalDePPEjecutoraEfectuado;
	}

	public List<Estructura> getListaBusquedaDePPEjecutoraEfectuado() {
		return listaBusquedaDePPEjecutoraEfectuado;
	}

	public void setListaBusquedaDePPEjecutoraEfectuado(
			List<Estructura> listaBusquedaDePPEjecutoraEfectuado) {
		this.listaBusquedaDePPEjecutoraEfectuado = listaBusquedaDePPEjecutoraEfectuado;
	}

	public EnvioConceptoComp getDtoFiltroDePPEjecutoraEfectuado() {
		return dtoFiltroDePPEjecutoraEfectuado;
	}

	public void setDtoFiltroDePPEjecutoraEfectuado(
			EnvioConceptoComp dtoFiltroDePPEjecutoraEfectuado) {
		this.dtoFiltroDePPEjecutoraEfectuado = dtoFiltroDePPEjecutoraEfectuado;
	}

	public Integer getIntTipoBusquedaDePPEjecutoraEfectuado() {
		return intTipoBusquedaDePPEjecutoraEfectuado;
	}

	public void setIntTipoBusquedaDePPEjecutoraEfectuado(
			Integer intTipoBusquedaDePPEjecutoraEfectuado) {
		this.intTipoBusquedaDePPEjecutoraEfectuado = intTipoBusquedaDePPEjecutoraEfectuado;
	}

	public Integer getIntEfectuado() {
		return intEfectuado;
	}

	public void setIntEfectuado(Integer intEfectuado) {
		this.intEfectuado = intEfectuado;
	}

	public List<EfectuadoResumen> getPlanillaEfectuadaResumen() {
		return planillaEfectuadaResumen;
	}

	public void setPlanillaEfectuadaResumen(
			List<EfectuadoResumen> planillaEfectuadaResumen) {
		this.planillaEfectuadaResumen = planillaEfectuadaResumen;
	}

	public List<Efectuado> getPlanillaEfectuada() {
		return planillaEfectuada;
	}

	public void setPlanillaEfectuada(List<Efectuado> planillaEfectuada) {
		this.planillaEfectuada = planillaEfectuada;
	}

	public List<EfectuadoConceptoComp> getListaEfectuadoConceptoComp() {
		return listaEfectuadoConceptoComp;
	}

	public void setListaEfectuadoConceptoComp(
			List<EfectuadoConceptoComp> listaEfectuadoConceptoComp) {
		this.listaEfectuadoConceptoComp = listaEfectuadoConceptoComp;
	}

	public String getStrTipoMantValidarDeEfectuado() {
		return strTipoMantValidarDeEfectuado;
	}

	public void setStrTipoMantValidarDeEfectuado(
			String strTipoMantValidarDeEfectuado) {
		this.strTipoMantValidarDeEfectuado = strTipoMantValidarDeEfectuado;
	}

	public Integer getIntEfectuado2() {
		return intEfectuado2;
	}

	public void setIntEfectuado2(Integer intEfectuado2) {
		this.intEfectuado2 = intEfectuado2;
	}
	
	public Integer getIntNombreoDNI() {
		return intNombreoDNI;
	}

	public void setIntNombreoDNI(Integer intNombreoDNI) {
		this.intNombreoDNI = intNombreoDNI;
	}

	public Boolean getBlnFuera() {
		return blnFuera;
	}

	public void setBlnFuera(Boolean blnFuera) {
		this.blnFuera = blnFuera;
	}
	
	public String getStrDNI() {
		return strDNI;
	}

	public void setStrDNI(String strDNI) {
		this.strDNI = strDNI;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}

	public EfectuadoConceptoComp getEfectuadoAgregarSocio() {
		return efectuadoAgregarSocio;
	}

	public void setEfectuadoAgregarSocio(EfectuadoConceptoComp efectuadoAgregarSocio) {
		this.efectuadoAgregarSocio = efectuadoAgregarSocio;
	}

	public List<EfectuadoConceptoComp> getListaEfectuadoConceptoCompTemp() {
		return listaEfectuadoConceptoCompTemp;
	}

	public void setListaEfectuadoConceptoCompTemp(
			List<EfectuadoConceptoComp> listaEfectuadoConceptoCompTemp) {
		this.listaEfectuadoConceptoCompTemp = listaEfectuadoConceptoCompTemp;
	}

	public Boolean getBlnEstaEnSocio() {
		return blnEstaEnSocio;
	}

	public void setBlnEstaEnSocio(Boolean blnEstaEnSocio) {
		this.blnEstaEnSocio = blnEstaEnSocio;
	}

	public BigDecimal getBdMontoNoSocio() {
		return bdMontoNoSocio;
	}

	public void setBdMontoNoSocio(BigDecimal bdMontoNoSocio) {
		this.bdMontoNoSocio = bdMontoNoSocio;
	}

	public Boolean getBlnAgregarNosocio() {
		return blnAgregarNosocio;
	}

	public void setBlnAgregarNosocio(Boolean blnAgregarNosocio) {
		this.blnAgregarNosocio = blnAgregarNosocio;
	}

	public Boolean getBlnMontoNoSocio() {
		return blnMontoNoSocio;
	}

	public void setBlnMontoNoSocio(Boolean blnMontoNoSocio) {
		this.blnMontoNoSocio = blnMontoNoSocio;
	}

	public List<Persona> getListandoPersona() {
		return listandoPersona;
	}

	public void setListandoPersona(List<Persona> listandoPersona) {
		this.listandoPersona = listandoPersona;
	}

	public Efectuado getEfectuadoNoSocio() {
		return efectuadoNoSocio;
	}

	public void setEfectuadoNoSocio(Efectuado efectuadoNoSocio) {
		this.efectuadoNoSocio = efectuadoNoSocio;
	}

	public Boolean getBlnPopupModalidadDistinta() {
		return blnPopupModalidadDistinta;
	}

	public void setBlnPopupModalidadDistinta(Boolean blnPopupModalidadDistinta) {
		this.blnPopupModalidadDistinta = blnPopupModalidadDistinta;
	}

	public Juridica getJuridicaUE() {
		return juridicaUE;
	}

	public void setJuridicaUE(Juridica juridicaUE) {
		this.juridicaUE = juridicaUE;
	}

	public Boolean getBlnDependenciaOrigen() {
		return blnDependenciaOrigen;
	}

	public void setBlnDependenciaOrigen(Boolean blnDependenciaOrigen) {
		this.blnDependenciaOrigen = blnDependenciaOrigen;
	}

	public Juridica getJuridicaSuc() {
		return juridicaSuc;
	}

	public void setJuridicaSuc(Juridica juridicaSuc) {
		this.juridicaSuc = juridicaSuc;
	}

	public Boolean getBlnMontoMayorAE() {
		return blnMontoMayorAE;
	}

	public void setBlnMontoMayorAE(Boolean blnMontoMayorAE) {
		this.blnMontoMayorAE = blnMontoMayorAE;
	}

	public Boolean getBlnTieneEnEDAE() {
		return blnTieneEnEDAE;
	}

	public void setBlnTieneEnEDAE(Boolean blnTieneEnEDAE) {
		this.blnTieneEnEDAE = blnTieneEnEDAE;
	}

	public Boolean getBlnNoTienePlanillaAE() {
		return blnNoTienePlanillaAE;
	}

	public void setBlnNoTienePlanillaAE(Boolean blnNoTienePlanillaAE) {
		this.blnNoTienePlanillaAE = blnNoTienePlanillaAE;
	}

	public Boolean getBlnModalidadAE() {
		return blnModalidadAE;
	}

	public void setBlnModalidadAE(Boolean blnModalidadAE) {
		this.blnModalidadAE = blnModalidadAE;
	}

	public Boolean getBlnEnOtrUE() {
		return blnEnOtrUE;
	}

	public void setBlnEnOtrUE(Boolean blnEnOtrUE) {
		this.blnEnOtrUE = blnEnOtrUE;
	}

	public Integer getIntModalidadDistinta() {
		return intModalidadDistinta;
	}

	public void setIntModalidadDistinta(Integer intModalidadDistinta) {
		this.intModalidadDistinta = intModalidadDistinta;
	}

	public EfectuadoConceptoComp getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(EfectuadoConceptoComp seleccionado) {
		this.seleccionado = seleccionado;
	}

	public List<Juridica> getListaJuridicaSucursalDePPEjecutoraEnvio() {
		return listaJuridicaSucursalDePPEjecutoraEnvio;
	}

	public void setListaJuridicaSucursalDePPEjecutoraEnvio(
			List<Juridica> listaJuridicaSucursalDePPEjecutoraEnvio) {
		this.listaJuridicaSucursalDePPEjecutoraEnvio = listaJuridicaSucursalDePPEjecutoraEnvio;
	}

	public List<Estructura> getListaBusquedaDePPEjecutoraEnvio() {
		return listaBusquedaDePPEjecutoraEnvio;
	}

	public void setListaBusquedaDePPEjecutoraEnvio(
			List<Estructura> listaBusquedaDePPEjecutoraEnvio) {
		this.listaBusquedaDePPEjecutoraEnvio = listaBusquedaDePPEjecutoraEnvio;
	}

	public List<Tabla> getListaModalidadTipoPlanilla() {
		return listaModalidadTipoPlanilla;
	}

	public void setListaModalidadTipoPlanilla(List<Tabla> listaModalidadTipoPlanilla) {
		this.listaModalidadTipoPlanilla = listaModalidadTipoPlanilla;
	}

	public Boolean getBlnActivarSegundaModalidad() {
		return blnActivarSegundaModalidad;
	}

	public void setBlnActivarSegundaModalidad(Boolean blnActivarSegundaModalidad) {
		this.blnActivarSegundaModalidad = blnActivarSegundaModalidad;
	}

	public EfectuadoConceptoComp getRegistroSeleccionadoEnAE() {
		return registroSeleccionadoEnAE;
	}

	public void setRegistroSeleccionadoEnAE(
			EfectuadoConceptoComp registroSeleccionadoEnAE) {
		this.registroSeleccionadoEnAE = registroSeleccionadoEnAE;
	}

	public Boolean getBlnAgregarPlanilla() {
		return blnAgregarPlanilla;
	}

	public void setBlnAgregarPlanilla(Boolean blnAgregarPlanilla) {
		this.blnAgregarPlanilla = blnAgregarPlanilla;
	}

	public Boolean getBlnCuentaPorPagar() {
		return blnCuentaPorPagar;
	}

	public void setBlnCuentaPorPagar(Boolean blnCuentaPorPagar) {
		this.blnCuentaPorPagar = blnCuentaPorPagar;
	}

	public List<Envioconcepto> getListaTemporal() {
		return listaTemporal;
	}

	public void setListaTemporal(List<Envioconcepto> listaTemporal) {
		this.listaTemporal = listaTemporal;
	}

	public List<Expediente> getListaExpediente() {
		return listaExpediente;
	}

	public void setListaExpediente(List<Expediente> listaExpediente) {
		this.listaExpediente = listaExpediente;
	}

	public BigDecimal getBdCuentaPorPagar() {
		return bdCuentaPorPagar;
	}

	public void setBdCuentaPorPagar(BigDecimal bdCuentaPorPagar) {
		this.bdCuentaPorPagar = bdCuentaPorPagar;
	}

	public BigDecimal getBdAporte() {
		return bdAporte;
	}

	public void setBdAporte(BigDecimal bdAporte) {
		this.bdAporte = bdAporte;
	}

	public BigDecimal getBdSepelio() {
		return bdSepelio;
	}

	public void setBdSepelio(BigDecimal bdSepelio) {
		this.bdSepelio = bdSepelio;
	}

	public BigDecimal getBdMant() {
		return bdMant;
	}

	public void setBdMant(BigDecimal bdMant) {
		this.bdMant = bdMant;
	}

	public BigDecimal getBdFdoRetiro() {
		return bdFdoRetiro;
	}

	public void setBdFdoRetiro(BigDecimal bdFdoRetiro) {
		this.bdFdoRetiro = bdFdoRetiro;
	}

	public BigDecimal getBdAmortizacion() {
		return bdAmortizacion;
	}

	public void setBdAmortizacion(BigDecimal bdAmortizacion) {
		this.bdAmortizacion = bdAmortizacion;
	}

	public BigDecimal getBdInteres() {
		return bdInteres;
	}

	public void setBdInteres(BigDecimal bdInteres) {
		this.bdInteres = bdInteres;
	}

	public Boolean getBlnGrabarNuevaPersona() {
		return blnGrabarNuevaPersona;
	}

	public void setBlnGrabarNuevaPersona(Boolean blnGrabarNuevaPersona) {
		this.blnGrabarNuevaPersona = blnGrabarNuevaPersona;
	}

	public Boolean getBlnNoEstaEnSocio() {
		return blnNoEstaEnSocio;
	}

	public void setBlnNoEstaEnSocio(Boolean blnNoEstaEnSocio) {
		this.blnNoEstaEnSocio = blnNoEstaEnSocio;
	}

	public Boolean getBlnEstaEnSocioEstructura() {
		return blnEstaEnSocioEstructura;
	}

	public void setBlnEstaEnSocioEstructura(Boolean blnEstaEnSocioEstructura) {
		this.blnEstaEnSocioEstructura = blnEstaEnSocioEstructura;
	}

	public Boolean getBlnNoEstaEnSocioEstructura() {
		return blnNoEstaEnSocioEstructura;
	}

	public void setBlnNoEstaEnSocioEstructura(Boolean blnNoEstaEnSocioEstructura) {
		this.blnNoEstaEnSocioEstructura = blnNoEstaEnSocioEstructura;
	}

	public Socio getSocioSelecionado() {
		return socioSelecionado;
	}

	public void setSocioSelecionado(Socio socioSelecionado) {
		this.socioSelecionado = socioSelecionado;
	}

	public Boolean getBlnNewPersona() {
		return blnNewPersona;
	}

	public void setBlnNewPersona(Boolean blnNewPersona) {
		this.blnNewPersona = blnNewPersona;
	}

	public Documento getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(Documento documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public Persona getRegistroPersona() {
		return registroPersona;
	}

	public void setRegistroPersona(Persona registroPersona) {
		this.registroPersona = registroPersona;
	}

	public boolean isMostrarPanelInferiorConArchivo() {
		return mostrarPanelInferiorConArchivo;
	}

	public void setMostrarPanelInferiorConArchivo(
			boolean mostrarPanelInferiorConArchivo) {
		this.mostrarPanelInferiorConArchivo = mostrarPanelInferiorConArchivo;
	}

	public boolean isDatosValidadosConArchivo() {
		return datosValidadosConArchivo;
	}

	public void setDatosValidadosConArchivo(boolean datosValidadosConArchivo) {
		this.datosValidadosConArchivo = datosValidadosConArchivo;
	}

	public String getSelAdjDocumento() {
		return selAdjDocumento;
	}

	public void setSelAdjDocumento(String selAdjDocumento) {
		this.selAdjDocumento = selAdjDocumento;
	}
		
	public Archivo getArchivoDocSolicitud() {
		return archivoDocSolicitud;
	}

	public void setArchivoDocSolicitud(Archivo archivoDocSolicitud) {
		this.archivoDocSolicitud = archivoDocSolicitud;
	}

	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}

	public Archivo getArchivoDocSolicitud1() {
		return archivoDocSolicitud1;
	}

	public void setArchivoDocSolicitud1(Archivo archivoDocSolicitud1) {
		this.archivoDocSolicitud1 = archivoDocSolicitud1;
	}

	public Archivo getArchivoDocSolicitud2() {
		return archivoDocSolicitud2;
	}

	public void setArchivoDocSolicitud2(Archivo archivoDocSolicitud2) {
		this.archivoDocSolicitud2 = archivoDocSolicitud2;
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	public EnvioConceptoComp getDtoDeEfectuadoConArchivo() {
		return dtoDeEfectuadoConArchivo;
	}

	public void setDtoDeEfectuadoConArchivo(
			EnvioConceptoComp dtoDeEfectuadoConArchivo) {
		this.dtoDeEfectuadoConArchivo = dtoDeEfectuadoConArchivo;
	}

	public boolean isDatosValidados2ConArchivo() {
		return datosValidados2ConArchivo;
	}

	public void setDatosValidados2ConArchivo(boolean datosValidados2ConArchivo) {
		this.datosValidados2ConArchivo = datosValidados2ConArchivo;
	}

	public Boolean getExaminar() {
		return examinar;
	}

	public void setExaminar(Boolean examinar) {
		this.examinar = examinar;
	}

	public String getStrExtensionConArchivo() {
		return strExtensionConArchivo;
	}

	public void setStrExtensionConArchivo(String strExtensionConArchivo) {
		this.strExtensionConArchivo = strExtensionConArchivo;
	}

	public String getStrTipoSocioConArchivo() {
		return strTipoSocioConArchivo;
	}

	public void setStrTipoSocioConArchivo(String strTipoSocioConArchivo) {
		this.strTipoSocioConArchivo = strTipoSocioConArchivo;
	}

	public Estructura getEstructura() {
		return estructura;
	}

	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}

	public Integer getIntPeriodoConArchivoTXT() {
		return intPeriodoConArchivoTXT;
	}

	public void setIntPeriodoConArchivoTXT(Integer intPeriodoConArchivoTXT) {
		this.intPeriodoConArchivoTXT = intPeriodoConArchivoTXT;
	}

	public List<EnvioMsg> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<EnvioMsg> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public Boolean getBlnPerteneceAlaPlanilla() {
		return blnPerteneceAlaPlanilla;
	}

	public void setBlnPerteneceAlaPlanilla(Boolean blnPerteneceAlaPlanilla) {
		this.blnPerteneceAlaPlanilla = blnPerteneceAlaPlanilla;
	}

	public Juridica getPerJuridicaSucursal() {
		return perJuridicaSucursal;
	}

	public void setPerJuridicaSucursal(Juridica perJuridicaSucursal) {
		this.perJuridicaSucursal = perJuridicaSucursal;
	}

	public Juridica getPerJuridicaUnidadEjecutora() {
		return perJuridicaUnidadEjecutora;
	}

	public void setPerJuridicaUnidadEjecutora(Juridica perJuridicaUnidadEjecutora) {
		this.perJuridicaUnidadEjecutora = perJuridicaUnidadEjecutora;
	}

	public String getMensajeConArchivo() {
		return mensajeConArchivo;
	}

	public void setMensajeConArchivo(String mensajeConArchivo) {
		this.mensajeConArchivo = mensajeConArchivo;
	}

	public List<Juridica> getListaSucursal() {
		return listaSucursal;
	}

	public void setListaSucursal(List<Juridica> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public List<Juridica> getListaUEjecutora() {
		return listaUEjecutora;
	}

	public void setListaUEjecutora(List<Juridica> listaUEjecutora) {
		this.listaUEjecutora = listaUEjecutora;
	}

	public Boolean getBlnErrorConArchivo() {
		return blnErrorConArchivo;
	}

	public void setBlnErrorConArchivo(Boolean blnErrorConArchivo) {
		this.blnErrorConArchivo = blnErrorConArchivo;
	}

	public List<EnvioMsg> getListaMensajesSinSucursal() {
		return listaMensajesSinSucursal;
	}

	public void setListaMensajesSinSucursal(List<EnvioMsg> listaMensajesSinSucursal) {
		this.listaMensajesSinSucursal = listaMensajesSinSucursal;
	}

	public Integer getIntNroSociosConArchivo() {
		return intNroSociosConArchivo;
	}

	public void setIntNroSociosConArchivo(Integer intNroSociosConArchivo) {
		this.intNroSociosConArchivo = intNroSociosConArchivo;
	}

	public List<EnvioMsg> getListaMensajesConSucursaleUE() {
		return listaMensajesConSucursaleUE;
	}

	public void setListaMensajesConSucursaleUE(
			List<EnvioMsg> listaMensajesConSucursaleUE) {
		this.listaMensajesConSucursaleUE = listaMensajesConSucursaleUE;
	}

	public Boolean getBlnErrorWithFileDeOneSucursalUE() {
		return blnErrorWithFileDeOneSucursalUE;
	}

	public void setBlnErrorWithFileDeOneSucursalUE(
			Boolean blnErrorWithFileDeOneSucursalUE) {
		this.blnErrorWithFileDeOneSucursalUE = blnErrorWithFileDeOneSucursalUE;
	}

	public List<Juridica> getListaJuridicaSucursal() {
		return listaJuridicaSucursal;
	}

	public void setListaJuridicaSucursal(List<Juridica> listaJuridicaSucursal) {
		this.listaJuridicaSucursal = listaJuridicaSucursal;
	}

	public List<Juridica> getListaJuridicaUnidadEjecutora() {
		return listaJuridicaUnidadEjecutora;
	}

	public void setListaJuridicaUnidadEjecutora(
			List<Juridica> listaJuridicaUnidadEjecutora) {
		this.listaJuridicaUnidadEjecutora = listaJuridicaUnidadEjecutora;
	}

	public BigDecimal getBdMontoTotalConArchivo() {
		return bdMontoTotalConArchivo;
	}

	public void setBdMontoTotalConArchivo(BigDecimal bdMontoTotalConArchivo) {
		this.bdMontoTotalConArchivo = bdMontoTotalConArchivo;
	}

	public Boolean getBlnTexto() {
		return blnTexto;
	}

	public void setBlnTexto(Boolean blnTexto) {
		this.blnTexto = blnTexto;
	}

	public Boolean getBlnExcel() {
		return blnExcel;
	}

	public void setBlnExcel(Boolean blnExcel) {
		this.blnExcel = blnExcel;
	}


	
	/**
	 * si tengo por efectuar con ambas modalidades haber e incentivo con respecto a los conceptos 
	 * la parte del efectuado no es igual al enviado.... aqui si es cancelatorio  igual va a conceptoPago
	 * y solo verifica si el monto saldo esta cancelado o no del periodo a efectuar
	 * si es acumulativo igual va a conceptoPago y ve el periodo actual y los periodos anteriores si estan
	 * o no cancelados.....
	 * o acumulativo
	 */
}
