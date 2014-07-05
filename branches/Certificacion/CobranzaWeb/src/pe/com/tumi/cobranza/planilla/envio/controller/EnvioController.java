package pe.com.tumi.cobranza.planilla.envio.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;

import pe.com.tumi.cobranza.cierremensual.bo.CierreCobranzaPlanillaBO;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanillaId;
import pe.com.tumi.cobranza.planilla.bean.EnvioMsg;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoBO;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoResumenBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioconceptoBO;
import pe.com.tumi.cobranza.planilla.bo.EnviomontoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioresumenBO;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.EnvioconceptoId;
import pe.com.tumi.cobranza.planilla.domain.Envioinflada;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;
import pe.com.tumi.cobranza.planilla.domain.composite.EfectuadoConceptoComp;
import pe.com.tumi.cobranza.planilla.domain.composite.EnvioConceptoComp;
import pe.com.tumi.cobranza.planilla.domain.composite.ItemPlanilla;
import pe.com.tumi.cobranza.planilla.domain.composite.PlanillaAdministra;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeLocal;
import pe.com.tumi.cobranza.planilla.validador.EnvioValidador;
import pe.com.tumi.common.PermisoUtil;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.riesgo.archivo.domain.ConfDetalle;
import pe.com.tumi.riesgo.archivo.domain.ConfEstructura;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;
import pe.com.tumi.riesgo.archivo.domain.ConfiguracionId;
import pe.com.tumi.riesgo.archivo.domain.Nombre;
import pe.com.tumi.riesgo.archivo.facade.ArchivoRiesgoFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class EnvioController extends GenericController{
	
	protected static Logger log = Logger.getLogger(EnvioController.class);
	
	private Usuario usuario;
	private EnvioConceptoComp dtoFiltroDeEnvio;
	private String strMsgDeEnvio;
	private EnvioMsg msgMantDeEnvio;
	private Integer EMPRESA_USUARIO;
	private Integer PERSONA_USUARIO;
	private String strTipoMantDeEnvio;
	private boolean poseePermiso;
	private String strIndexFilaDeEnvio;
	private String strEstadoFilaDeEnvio;
	private	String strTipoMantValidarDeEnvio;
	private EnvioConceptoComp dtoDeEnvio;
	private Envioresumen registroSeleccionadoEnvioResumen;
	private	List<ItemPlanilla> planillaCAS;	
	private	List<ItemPlanilla> planilla;
	private List<Tabla> listaTablaDeSucursal;
	private List<Envioresumen> listaBusquedaDeEnvioresumen;
	private List<Envioresumen> listaEnvioResumenTemp;
	private List<EnvioConceptoComp> listaBusquedaDeEnvio;
	private List<Estructura> listaBusquedaDePPEjecutoraEnvio;
	private List<Efectuado> listaEfectuado;
	private EnvioConceptoComp dtoFiltroDePPEjecutoraEnvio;
	private String strTipoSociOcas;
	private List<Juridica> listaJuridicaSucursalDePPEjecutoraEnvio;
	private Integer intTipoBusquedaDePPEjecutoraEnvio;
	private String strIndexFilaDePPEjecutoraEnvio;
	private Boolean esValidoDePPEjecutoraEnvio;
	private boolean blnEnviadaHaber;
	private boolean blnEnviadaIncentivo;
	private boolean blnEnviadaCas;
	private Integer intNroHaberesOf;
	private Integer intNroIncentivoOf;
	private Integer intNroCasOf;
	private Integer intNroHaberes;
	private Integer intNroIncentivo;
	private Integer intNroCas;
	private Boolean blnCheckHaber;
	private Boolean blnCheckIncentivo;
	private Boolean blnCheckCAS;
	private Boolean habilitarInflada;
	private BigDecimal bdSumatoriaHaberes;
	private BigDecimal bdSumatoriaIncentivos;
	private BigDecimal bdSumatoriaCas;
	private BigDecimal bdSumatoriaTotal;
	private BigDecimal bdSumatoriaHaberesI;
	private BigDecimal bdSumatoriaIncentivosI;
	private BigDecimal bdSumatoriaCasI;
	private Boolean blnEstructuraDetalle;
	private Boolean blnCAS;
	private List<Tabla> listaModalidadTipoPlanilla;
	private Integer intCodigoPlanilla;
	private Boolean blnhabilitarAgregar;
	private Boolean destacado;
	private BigDecimal bdMontoTipoPlanilla;
	private	ItemPlanilla registroSeleccionado;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private String 		mensajeOperacion;
	private List<Tabla> listaModalidadPlanilla;
	private boolean habilitarGrabarMontoEnvio;
	private boolean habilitarModalidadHaberMontoEnvio;
	private boolean habilitarModalidadIncentivoMontoEnvio;
	private boolean habilitarCalcularMontoEnvio;
	private BigDecimal bdHaberTemporal;	
	private BigDecimal bdIncentivoTemporal;
	private BigDecimal bdMontoHaberIncicial;
	private BigDecimal bdMontoIncentivoInicial;
	private String strHaberIncentivo;	
	private Boolean habilitarGrabarInflada;
	private List<Envioinflada> listaEnvioinflada;
	private HtmlSelectOneRadio radio;
	private boolean  habilitarAgregarEnvioinflada;
	private String tipoSocio;
	private List<Tabla> listaModalidadPlanillaTemporal;
	private Envioinflada envioInfladaK;
	private Envioinflada envioinfladaEliminar;
	private List<Envioinflada> listaEnvInfEliminada;
	private List<Envioinflada> listaInflada;
	private Integer intPerfil;
	private List<Tabla> listaTipoInflada;
	
	private boolean envioFormRendered;
	private boolean btnGrabarDisabled;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;	
	private boolean habilitarGrabar;
	private boolean datosValidados;
	private boolean blnPlanillaEnviadaHaber;
	private boolean blnPlanillaEnviadaIncentivo;
	List<EfectuadoConceptoComp> listaEfectuadoConceptoComp;
	private boolean blnMostrarTxth;
	private boolean blnMostrarExcelh;
	private boolean blnMostrarDBFh;
	private boolean blnMostrarTxta4j;
	private boolean blnMostrarExcela4j;
	private boolean blnMostrarDBFa4j;
	private String mensajeTxt;
	private String mensajeExcel;
	private String mensajeDBF;
	private List<PlanillaAdministra> planillaAdministraCAS;
	private List<PlanillaAdministra> planillaAdministra;
	
	private List<EnvioMsg> listaMensajes;
	
	public EnvioController()
	{
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_ENVIO);
		if(usuario!= null && poseePermiso)
		{
			cargarValoresIniciales();			
		}
		else
		{
			log.error("usuario obtenido es null o no posee permiso");
		}
	}
	private void cargarUsuario()
	{
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		PERSONA_USUARIO =usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
	}
	private void cargarValoresIniciales()
	{
		cargarUsuario();
		TablaFacadeRemote remoteTabla = null;
		registroSeleccionadoEnvioResumen = null;
		EmpresaFacadeRemote remoteEmpresa;
		msgMantDeEnvio = new EnvioMsg();		
		strMsgDeEnvio = null;
		strTipoMantDeEnvio = Constante.MANTENIMIENTO_NINGUNO;	
		dtoFiltroDeEnvio = null;
		dtoFiltroDeEnvio = new EnvioConceptoComp();
		dtoFiltroDeEnvio.setEnvioConcepto(new Envioconcepto());
		dtoFiltroDeEnvio.setEstructuraDetalle(new EstructuraDetalle());
		dtoFiltroDeEnvio.setEstructura(new Estructura());
		dtoFiltroDeEnvio.getEstructura().setIntPersEmpresaPk(EMPRESA_USUARIO);
		dtoFiltroDeEnvio.getEstructura().setJuridica(new Juridica());
		dtoDeEnvio = null;
		dtoDeEnvio = new EnvioConceptoComp();
		dtoDeEnvio.setEstructura(new Estructura());
		dtoDeEnvio.getEstructura().setJuridica(new Juridica());
		envioFormRendered =false;
		btnGrabarDisabled = true;
		envioInfladaK = new Envioinflada();
		listaEnvInfEliminada = new ArrayList<Envioinflada>();
		if(listaBusquedaDeEnvioresumen != null && ! listaBusquedaDeEnvioresumen.isEmpty())
		{
			getListaBusquedaDeEnvioresumen().clear();
		}
		
		if(planilla != null && ! planilla.isEmpty())
		{
			getPlanilla().clear();
		}
		if(planillaCAS != null && ! planillaCAS.isEmpty())
		{
			getPlanillaCAS().clear();
		}
		mostrarPanelInferior = false;
		try
		{
			remoteTabla = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			remoteEmpresa = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);				
			listaTablaDeSucursal 		= remoteTabla.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
			listaTipoInflada 			= remoteTabla.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOINFLADA));
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
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public String getLimpiarEnvio()
	{
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_ENVIO);
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
	
	public void buscarDeEnvio()
	{
		log.info("planillaController.BuscarDeEnvioo");
		PlanillaFacadeLocal planillaLocal = null;
		registroSeleccionadoEnvioResumen = null;
		listaEnvioResumenTemp = new ArrayList<Envioresumen>();
		try{								
					//Unidad Ejecutora
					if(dtoFiltroDeEnvio.getEstructura().getJuridica().getStrRazonSocial() != null 
					   && !dtoFiltroDeEnvio.getEstructura().getJuridica().getStrRazonSocial().isEmpty())
					{
						dtoFiltroDeEnvio.getEstructura().getJuridica().getStrRazonSocial().trim();
					}
					//sucursal
					if(dtoFiltroDeEnvio.getEstructuraDetalle().getIntSeguSucursalPk().equals(Constante.PARAM_T_SELECCIONAR))
						dtoFiltroDeEnvio.getEstructuraDetalle().setIntSeguSucursalPk(null);
					//tiposocio
					if(dtoFiltroDeEnvio.getEstructuraDetalle().getIntParaTipoSocioCod().equals(Constante.PARAM_T_SELECCIONAR)
						|| dtoFiltroDeEnvio.getEstructuraDetalle().getIntParaTipoSocioCod().equals(Constante.PARAM_T_TODOS)	)
						dtoFiltroDeEnvio.getEstructuraDetalle().setIntParaTipoSocioCod(null);
					//tipodeplanilla
					if(dtoFiltroDeEnvio.getIntParaModalidadPlanilla().equals(Constante.PARAM_T_SELECCIONAR)
					    || dtoFiltroDeEnvio.getIntParaModalidadPlanilla().equals(Constante.PARAM_T_TODOS))
						dtoFiltroDeEnvio.setIntParaModalidadPlanilla(null);
					//estado
					if(dtoFiltroDeEnvio.getEnvioConcepto().getIntParaEstadoCod().equals(Constante.PARAM_T_SELECCIONAR)
					   || dtoFiltroDeEnvio.getEnvioConcepto().getIntParaEstadoCod().equals(Constante.PARAM_T_TODOS))
						dtoFiltroDeEnvio.getEnvioConcepto().setIntParaEstadoCod(null);
					///periodo
					if(dtoFiltroDeEnvio.getIntPeriodoAnio().equals(Constante.PARAM_T_SELECCIONAR))
					{
						dtoFiltroDeEnvio.setIntPeriodoAnio(null);
						dtoFiltroDeEnvio.setIntPeriodoMes(null);
					}
					else
					{
						if(dtoFiltroDeEnvio.getIntPeriodoMes().intValue()<10)
							dtoFiltroDeEnvio.setIntPeriodo(Integer.parseInt(dtoFiltroDeEnvio.getIntPeriodoAnio()+"0"+dtoFiltroDeEnvio.getIntPeriodoMes()));
						else
							dtoFiltroDeEnvio.setIntPeriodo(Integer.parseInt(dtoFiltroDeEnvio.getIntPeriodoAnio()+""+dtoFiltroDeEnvio.getIntPeriodoMes()));
					}					
										
					strMsgDeEnvio = null;
					
					planillaLocal = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);

					listaBusquedaDeEnvioresumen = planillaLocal.getListaEnvioresumenBuscar(dtoFiltroDeEnvio);
					log.info("cantidad de lista en envioresumen"+listaBusquedaDeEnvioresumen.size());
									
					
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		
	}
	
	public void limpiarDeEnvio()
	{
		if(listaBusquedaDeEnvioresumen != null && !listaBusquedaDeEnvioresumen.isEmpty())
		{
			listaBusquedaDeEnvioresumen.clear();
		}
		dtoFiltroDeEnvio = null;
		dtoFiltroDeEnvio = new EnvioConceptoComp();
		dtoFiltroDeEnvio.setEnvioConcepto(new Envioconcepto());
		dtoFiltroDeEnvio.setEstructuraDetalle(new EstructuraDetalle());
		dtoFiltroDeEnvio.setEstructura(new Estructura());
		dtoFiltroDeEnvio.getEstructura().setIntPersEmpresaPk(EMPRESA_USUARIO);
		dtoFiltroDeEnvio.getEstructura().setJuridica(new Juridica());
	}
	
	public void habilitarPanelInferior()
	{
		try{
			cargarUsuario();
			datosValidados = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			dtoDeEnvio = new EnvioConceptoComp();
			dtoDeEnvio.setEstructura(new Estructura());
			dtoDeEnvio.getEstructura().setJuridica(new Juridica());
			habilitarGrabar = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			strMsgDeEnvio = null;
			if(listaMensajes != null && !listaMensajes.isEmpty())
			{
				listaMensajes.clear();
			}
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopupBuscarUnidadEjecutora()
	{
		EmpresaFacadeRemote remoteEmpresa = null;
		PersonaFacadeRemote remotePersona = null;
		List<Sucursal> lListaSucursal = null;
		EmpresaUsuarioId id = null;
		String csvPkPersona = null;
		Usuario usuario = null;
		ocultarMensaje();
		listaBusquedaDePPEjecutoraEnvio = null;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			dtoFiltroDePPEjecutoraEnvio = new EnvioConceptoComp();
			dtoFiltroDePPEjecutoraEnvio.setEstructura(new Estructura());
			dtoFiltroDePPEjecutoraEnvio.setJuridicaSucursal(new Juridica());
			if(usuario != null){
				remoteEmpresa = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				id = new EmpresaUsuarioId();
				id.setIntPersPersonaPk(usuario.getIntPersPersonaPk());
				id.setIntPersEmpresaPk(usuario.getEmpresa().getIntIdEmpresa());
				lListaSucursal = remoteEmpresa.getListaSucursalPorPkEmpresaUsuarioYEstado(id,Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				for(int i=0;i<lListaSucursal.size();i++){
					if(csvPkPersona==null)
						csvPkPersona = String.valueOf(lListaSucursal.get(i).getIntPersPersonaPk());
					else
						csvPkPersona = csvPkPersona + "," + lListaSucursal.get(i).getIntPersPersonaPk();
				}
				if(csvPkPersona != null){
					remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
					listaJuridicaSucursalDePPEjecutoraEnvio = remotePersona.getListaJuridicaPorInPk(csvPkPersona);
					strIndexFilaDePPEjecutoraEnvio = null;
					esValidoDePPEjecutoraEnvio = new Boolean(false);
				}	
			}
			log.debug("irBuscarDePPEjecutoraEnvio FIN");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public void buscarUnidadEjecutora(ActionEvent event){
		log.debug("buscarDePPEjecutoraEnvio INICIO");
		EstructuraFacadeRemote remoteEstructura = null;
		EmpresaFacadeRemote remoteEmpresa = null;
		PersonaFacadeRemote remotePersona = null;
		Usuario usuario = null;
		Sucursal lSucursal = null;
		Estructura estructura = null;
		List<Estructura> listaEstructura = null;
		String strRazonSocialDeFiltro = null;
		String strRazonSocialDeJuridica = null;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!= null)
			{				
				remoteEmpresa = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
				lSucursal = remoteEmpresa.getSucursalPorIdPersona(dtoFiltroDePPEjecutoraEnvio.getJuridicaSucursal().getIntIdPersona());
				log.debug("idSucursal: "+lSucursal.getId().getIntIdSucursal());
				log.debug("empresa: "+usuario.getEmpresa().getIntIdEmpresa());
				intTipoBusquedaDePPEjecutoraEnvio =0;
				if(intTipoBusquedaDePPEjecutoraEnvio == 0)
				{
					listaEstructura = remoteEstructura.getListaEstructuraPorIdEmpresaYIdCasoIdSucursal(usuario.getEmpresa().getIntIdEmpresa(), 							 
																			   						   Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA,
																			   						   lSucursal.getId().getIntIdSucursal());
					if(listaEstructura!=null && listaEstructura.size()>0)
					{
						remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
						for(int i=0;i<listaEstructura.size();i++)
						{
							estructura = listaEstructura.get(i);
							log.debug("nivel: "+estructura.getId().getIntNivel()+" codigo: "+estructura.getId().getIntCodigo());
							estructura.setJuridica(remotePersona.getJuridicaPorPK(estructura.getIntPersPersonaPk()));
							log.debug(estructura.getJuridica().getStrRazonSocial());
						}
					}	
				}
				else
				{	
					if(intTipoBusquedaDePPEjecutoraEnvio == 1)
					{
						log.debug("1");
						listaEstructura = remoteEstructura.getListaEstructuraPorIdEmpresaYIdNivelYIdCasoIdSucursal(usuario.getEmpresa().getIntIdEmpresa(), 
																												   Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA, 
																												   Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA,
																												   lSucursal.getId().getIntIdSucursal());
						if(listaEstructura!=null && listaEstructura.size()>0)
						{
							remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
							for(int i=0;i<listaEstructura.size();i++)
							{
								estructura = listaEstructura.get(i);
								estructura.setJuridica(remotePersona.getJuridicaPorPK(estructura.getIntPersPersonaPk()));
								if(dtoFiltroDePPEjecutoraEnvio.getJuridicaSucursal().getStrRazonSocial()!=null)
								{
								  strRazonSocialDeFiltro = dtoFiltroDePPEjecutoraEnvio.getJuridicaSucursal().getStrRazonSocial().trim();
								  strRazonSocialDeJuridica = estructura.getJuridica().getStrRazonSocial();
								  if(strRazonSocialDeFiltro!=null && strRazonSocialDeJuridica != null)
								  {
									  strRazonSocialDeFiltro = strRazonSocialDeFiltro.trim().toLowerCase();
									  strRazonSocialDeJuridica = strRazonSocialDeJuridica.trim().toLowerCase(); 
									  if(!strRazonSocialDeJuridica.contains(strRazonSocialDeFiltro))
									  {
										  listaEstructura.remove(i);
										  i--;
									  }
								  }
								}
							}
						}
					}else{
						log.debug("a");
						listaEstructura = remoteEstructura.getListaEstructuraPorIdEmpresaYIdCodigoYIdNivelYIdCasoIdSucursal(usuario.getEmpresa().getIntIdEmpresa(), 
																														   dtoFiltroDePPEjecutoraEnvio.getEstructura().getIntIdCodigoRel(),
																														   Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA, 
																														   Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA,
																														   lSucursal.getId().getIntIdSucursal());
						if(listaEstructura!=null && listaEstructura.size()>0)
						{
							remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
							for(int i=0;i<listaEstructura.size();i++)
							{
								estructura = listaEstructura.get(i);
								estructura.setJuridica(remotePersona.getJuridicaPorPK(estructura.getIntPersPersonaPk()));
							}
						}	
					}
				}
			}
			listaBusquedaDePPEjecutoraEnvio = listaEstructura;
			//ordenando lista
			Collections.sort(listaBusquedaDePPEjecutoraEnvio, new Comparator<Estructura>()
				{
					public int compare(Estructura estUno, Estructura estDos)
					{
						return estUno.getJuridica().getStrRazonSocial().compareTo(estDos.getJuridica().getStrRazonSocial());
					}
				});
			log.debug("buscarDePPEjecutoraEnvio FIN");
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
			Juridica juridica = remotePersona.getJuridicaPorPK(dtoFiltroDePPEjecutoraEnvio.getJuridicaSucursal().getIntIdPersona());
			
			dtoDeEnvio.setJuridicaSucursal(juridica);			
			dtoDeEnvio.setEstructura(unidadEjecutoraSeleccionada);
			dtoDeEnvio.getEstructura().setJuridica(unidadEjecutoraSeleccionada.getJuridica());
		} catch(Exception e)
		{
			log.error(e.getMessage(), e);
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
	
	public void deshabilitarPanelInferior()
	{
		habilitarGrabar = Boolean.FALSE;
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		planilla 	= null;
		planillaCAS = null;
	}
	
	public void grabarDeEnvio()
	{
		List<ItemPlanilla> planillaTemporal = new ArrayList<ItemPlanilla>();
		try
		{ 
			PlanillaFacadeLocal localPlanilla = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			Usuario lUsuario 				  = (Usuario)getRequest().getSession().getAttribute("usuario");
			
			if(planillaAdministra != null && !planillaAdministra.isEmpty())
    		{		    		
    		  if(intNroHaberesOf > 0)
    		  {
    			if(!blnEnviadaHaber)
    			{//noestabloqueado
    			  if(intNroIncentivoOf >0)
    			  {		
    				if(!blnEnviadaIncentivo)
    				{//noestabloqueado
    					//ver si ambas estan seleccionadas para mandar
    					if(blnCheckHaber)
    					{
    						if(blnCheckIncentivo)
    						{			    							
    							//las dos modalidades se pueden grabar
    							for(PlanillaAdministra administra:planillaAdministra)
    							{
    								log.debug("");
    								localPlanilla.grabarEnvio(administra.getListaPlanilla(),lUsuario);
    							}    							
    						}
    						else
    						{			    							
    							for(PlanillaAdministra itemPlanillaAdministra :planillaAdministra)
    							{    								
        							for(ItemPlanilla ila: itemPlanillaAdministra.getListaPlanilla())
        							{
        								for(Iterator<Enviomonto> iter = ila.getListaEnviomonto().listIterator(); iter.hasNext();)
        								{
        									Enviomonto eo = iter.next();
        									if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(eo.getIntModalidadCod())==0)
        									{
        										iter.remove();
        									}
        								}			    								
        							}
        							localPlanilla.grabarEnvio(itemPlanillaAdministra.getListaPlanilla(),lUsuario);
    							}    							
    						}
    					}
    					else
    					{
    						if(blnCheckIncentivo)
    						{
    							for(PlanillaAdministra itemAdministra :planillaAdministra)
    							{    								
        							for(ItemPlanilla ila: itemAdministra.getListaPlanilla())
        							{
        								for(Iterator<Enviomonto> iter= ila.getListaEnviomonto().listIterator(); iter.hasNext();)
        								{	Enviomonto eo = iter.next();
        									if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(eo.getIntModalidadCod())==0)
        									{
        										iter.remove();
        									}
        								}			    								
        							}        							
        							localPlanilla.grabarEnvio(itemAdministra.getListaPlanilla(),lUsuario);        							
    							}    							
    						}
    						else
    						{
    							strMsgDeEnvio = "No hay ninguna modalidad seleccionada.";
    							mostrarMensaje(Boolean.FALSE, "No hay ninguna modalidad seleccionada.");
		    					return;
    						}
    					}
    				}
    				else
    				{//sta bloqueado
    					//ver si esta seleccionado este haber sino mandar mensajito
    					if(blnCheckHaber)
    					{
    						//solo haber grabo    						
							//quitar lo de incentivo bloqueado
    						for(PlanillaAdministra itemPlanillaAdministra:planillaAdministra)
    						{
    							for(ItemPlanilla ila: itemPlanillaAdministra.getListaPlanilla())
    							{
    								for(Iterator<Enviomonto> iter =  ila.getListaEnviomonto().listIterator(); iter.hasNext();)
    								{	Enviomonto eo = iter.next();
    									if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(eo.getIntModalidadCod())==0)
    									{
    										iter.remove();
    									}
    								}			    								
    							}
    							localPlanilla.grabarEnvio(itemPlanillaAdministra.getListaPlanilla(),lUsuario);
    						}							
    					}
    					else
    					{			    						
    						mostrarMensaje(Boolean.FALSE, "La modalidad de haber no esta seleccionada.");
	    					return;
    					}
    				}
    			  }
    			  else
    			  {	// no hay incentivos
    				//preguntar si esta seleccinado sino decir selecciona haberes
    				  if(blnCheckHaber)
    					{						
							for(PlanillaAdministra itemAdministra:planillaAdministra)
							{
								localPlanilla.grabarEnvio(itemAdministra.getListaPlanilla(),lUsuario);
							}
    					}
    				  else
    				  {
    					  mostrarMensaje(Boolean.FALSE, "La modalidad de haber no esta seleccionada.");
    					  return; 
    				  }
    			  }
    			}
    			else
    			{//esta bloqueado haber
    			  if(intNroIncentivoOf >0)
    			  {
    				if(!blnEnviadaIncentivo)
    				{//noestabloqueado
    					//solo mando incentivo
    						//ver si esta seleccionado este incentivo sino mandar mensajito
    					if(blnCheckIncentivo)
    					{
    						//solo se manda incentivo
    						for(PlanillaAdministra itemPlanillaAdministra:planillaAdministra)
    						{
    							for(ItemPlanilla ila: itemPlanillaAdministra.getListaPlanilla())
    							{
        							if(ila.getListaEnviomonto() != null)
        							{
        								for(Iterator<Enviomonto> iter = ila.getListaEnviomonto().listIterator(); iter.hasNext();)
    	    							{	Enviomonto eo = iter.next();				    								
    	    								if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(eo.getIntModalidadCod())==0)
    	    								{
    	    									iter.remove();
    	    								}
    	    							}
        							}			    							
    							}			    						
    							localPlanilla.grabarEnvio(itemPlanillaAdministra.getListaPlanilla(),lUsuario);
    						}    						
    					}
    					else
    					{			    						
    						mostrarMensaje(Boolean.FALSE, " La modalidad de incentivo no esta seleccionada");
    						return;
    					}
    				}
    				else
    				{//esta bloqueado incentivo			    					
    					mostrarMensaje(Boolean.FALSE, " Ambas modalidades estan bloqueadas");
    					return;
    				}
    			  }	
    			}
    		  }
    		  else
    		  {//no tengo haberes
    			//preguntar si incentivo 
    			  if(intNroIncentivoOf > 0)
    			  {
    				  //preguntarte si esta bloqueado
    				  if(!blnEnviadaIncentivo)
    				  {//no esta bloqueado
    					  if(blnCheckIncentivo)
	    					{    							
    							for(PlanillaAdministra planAdministra:planillaAdministra)
    							{
    								localPlanilla.grabarEnvio(planAdministra.getListaPlanilla() ,lUsuario);
    							}
	    					}
	    					else
	    					{				    						
	    						mostrarMensaje(Boolean.FALSE, "La modalidad de incentivo no esta seleccionada");
	    						return;
	    					}			    					  
    				  }
    			  }
    			  else
    			  {
    				  	mostrarMensaje(Boolean.FALSE, "No hay Socios.");
    				  	return; 
    			  }
    		  }
    		}
			
			else if(planillaAdministraCAS != null && !planillaAdministraCAS.isEmpty())
			{
				if(blnCheckCAS)
				{
					for(PlanillaAdministra casAdministra: planillaAdministraCAS)
					{
						localPlanilla.grabarEnvio(casAdministra.getListaPlanilla(), lUsuario);
					}
				}else
				{
					mostrarMensaje(Boolean.FALSE, " La modalidad CAS no esta seleccionada");
					return;
				}
			}
	    	mostrarMensaje(Boolean.FALSE, "Los datos se grabaron");
	    	mostrarPanelInferior 	= Boolean.FALSE;
	    	habilitarGrabar 		= Boolean.FALSE;
	    	deshabilitarNuevo 		= Boolean.TRUE;
			
		}catch(Exception e)
		{
			mostrarMensaje(Boolean.FALSE, "Ocurrio un error durante el proceso del enviado de planilla.");
			log.error(e.getMessage(), e);
		}
	}
		
	public void irValidarDeEnvio()
	{
		log.info("irValidarDeEnvio INICIO V6");
		cargarUsuario();				
		EstructuraId pk   = null;					
		BigDecimal bdCero = new BigDecimal(0);
		
		if(planilla != null){
			planilla.clear();
		}
		if(planillaCAS != null){
			planillaCAS.clear();
		}
		
		blnCAS				= Boolean.FALSE;
		blnCheckHaber 		= Boolean.FALSE;
		blnCheckIncentivo 	= Boolean.FALSE;		
		intNroHaberesOf 	= 0;
		intNroIncentivoOf 	= 0;
		intNroCasOf 		= 0; 
		try{									
			pk = dtoDeEnvio.getEstructura().getId();	//nivel y codigo
			
			if(strTipoSociOcas != null)
			{
				log.info("strTipoSociOcas="+strTipoSociOcas);
				intNroHaberes   = 0;
				intNroIncentivo = 0;
				intNroCas       = 0;
				
				bdSumatoriaHaberes    = bdCero;
				bdSumatoriaIncentivos = bdCero;
				bdSumatoriaCas        = bdCero;
				bdSumatoriaHaberesI    = bdCero;
				bdSumatoriaIncentivosI = bdCero;
				bdSumatoriaCasI        = bdCero;
				bdSumatoriaTotal      = bdCero;
				
				if(strTipoSociOcas.equals("Activo")|| strTipoSociOcas.equals("Cesante"))
				{
					activoCesanteEnviado(pk);
				}
				else if(strTipoSociOcas.equals("Cas"))
				{
					casenEnviado(pk);		
				}								
			}	
			
			log.info("irValidarDeEnvio FIN V7");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}		
	}
	
	private void activoCesanteEnviado(EstructuraId pk)
	{		
		Integer intTipoSocio = 0;		
		Integer intMaxPeriodoEnviado = 0;
		Integer intMaxPeriodoEfectuado   = 0;
		Boolean blnEstructuraHaber  = Boolean.FALSE;
		Boolean blnEstructuraIncentivo = Boolean.FALSE;
		Boolean envioResumenHaber         = Boolean.FALSE;
		Boolean envioResumenIncentivo     = Boolean.FALSE;
		EstructuraDetalle lEstructuraDetalle     = null;
		EstructuraDetalleId lEstructuraDetalleId = null;
		
		try
		{	
			EstructuraFacadeRemote remoteEstrucutra = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			PlanillaFacadeLocal localPlanilla 		= (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			EnvioresumenBO boEnvioresumen         	= (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
			EfectuadoResumenBO boEfectuadoresumen 	= (EfectuadoResumenBO)TumiFactory.get(EfectuadoResumenBO.class);		
			EfectuadoBO boEfectuado					= (EfectuadoBO)TumiFactory.get(EfectuadoBO.class);			
			CierreCobranzaPlanillaBO boCierre		=  (CierreCobranzaPlanillaBO)TumiFactory.get(CierreCobranzaPlanillaBO.class);
			
			if(strTipoSociOcas.equals("Activo"))
			{
				intTipoSocio = Constante.PARAM_T_TIPOSOCIO_ACTIVO;
				dtoDeEnvio.setBlnActivo(true);
			}
			else if(strTipoSociOcas.equals("Cesante"))
			{
				intTipoSocio = Constante.PARAM_T_TIPOSOCIO_CESANTE;
				dtoDeEnvio.setBlnActivo(false);
			}		
								 
			lEstructuraDetalleId = new EstructuraDetalleId();
			lEstructuraDetalleId.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
			lEstructuraDetalleId.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
			
			EstructuraId id22 = new EstructuraId();
			id22.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
			id22.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
			
			List<EstructuraDetalle> listaEstructuraDetall = remoteEstrucutra.getListaEstructuraDetallePorEstructuraPK(id22);
			
			if(listaEstructuraDetall != null && !listaEstructuraDetall.isEmpty() )
			{
				for(EstructuraDetalle ee: listaEstructuraDetall)
				{
					if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(ee.getIntParaModalidadCod()) == 0)
					{
						blnEstructuraHaber = Boolean.TRUE;
						break;
					}
				}
				for(EstructuraDetalle ee: listaEstructuraDetall)
				{
					if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(ee.getIntParaModalidadCod()) == 0)
					{
						blnEstructuraIncentivo = Boolean.TRUE;
						break;
					}
				}
				if( blnEstructuraHaber && blnEstructuraIncentivo)
				{
					blnEstructuraDetalle =  Boolean.TRUE;
				}
				else
				{
					blnEstructuraDetalle =  Boolean.FALSE;
				}
			}
			
			lEstructuraDetalle = remoteEstrucutra.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(lEstructuraDetalleId,
																										 intTipoSocio,														
																										 Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA);
			if(lEstructuraDetalle != null)
			{
				dtoDeEnvio.setEstructuraDetalle(lEstructuraDetalle);				
				
				intMaxPeriodoEnviado = localPlanilla.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocio(EMPRESA_USUARIO,
																									     pk,
																									     intTipoSocio);						
										
				if(intMaxPeriodoEnviado != null){
					log.info("intMaxPeriodoEnviado="+intMaxPeriodoEnviado);
					//tanto en haberes, incentivo comparo en envioresumen haber, incentivo
					
					intMaxPeriodoEfectuado = localPlanilla.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocio(EMPRESA_USUARIO,
																												pk,
																												intTipoSocio);
					
					List<Socio> socios = localPlanilla.getListaSocio(pk, intTipoSocio);						
					
					if(socios != null && !socios.isEmpty())
					{	
						log.info("linea  socios cant="+socios.size());
						dtoDeEnvio.setBlnHaberes(Boolean.FALSE);
						dtoDeEnvio.setBlnIncentivo(Boolean.FALSE);								
						
						for (Socio socio : socios)
						{
							for(SocioEstructura socioEs: socio.getListSocioEstructura())
							{										
								if(socioEs.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0)
								{
									dtoDeEnvio.setBlnHaberes(Boolean.TRUE);
									intNroHaberesOf++;
									List<Envioresumen> listaEnvResumenHaber = boEnvioresumen.getListaPorEnitdadyPeriodo(EMPRESA_USUARIO, 
																														intMaxPeriodoEnviado,
																														intTipoSocio,
																														Constante.PARAM_T_MODALIDADPLANILLA_HABERES,
																														pk.getIntNivel(),
																														socioEs.getIntCodigo());
//									
									if(listaEnvResumenHaber != null && !listaEnvResumenHaber.isEmpty())
									{
										envioResumenHaber = true;										
									}else
									{
										log.debug("listaEnvResumenHaber is null");
									}
									break;
								}
							}
						}
						for (Socio socio : socios)
						{
							for(SocioEstructura socioEs:socio.getListSocioEstructura())
							{	
								 if(socioEs.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)==0)
								{
									dtoDeEnvio.setBlnIncentivo(Boolean.TRUE);
									intNroIncentivoOf++;
									List<Envioresumen> listaEnvResumenIncentivo = boEnvioresumen.getListaPorEnitdadyPeriodo(EMPRESA_USUARIO, 
																															intMaxPeriodoEnviado,
																															intTipoSocio,
																															Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS,
																															pk.getIntNivel(),
																															socioEs.getIntCodigo());
									
									if(listaEnvResumenIncentivo != null && !listaEnvResumenIncentivo.isEmpty())
									{
										envioResumenIncentivo = true;
									}else
									{
										log.debug("listaEnvResumenIncentivo is null");
									}
			
									break;
								}																		
							}																				
						}
					}
					else
					{
						mostrarMensaje(Boolean.FALSE, " La unidad seleccionada no tiene registrado socios.");
						return;										
					}
						
					log.debug("blnHaberes: "+dtoDeEnvio.getBlnHaberes()+", blnIncentivo: "+dtoDeEnvio.getBlnIncentivo()+
							  ", envioResumenHaber: "+envioResumenHaber+", envioResumenIncentivo: "+envioResumenIncentivo);
					if(dtoDeEnvio.getBlnHaberes().equals(envioResumenHaber) && 
					   dtoDeEnvio.getBlnIncentivo().equals(envioResumenIncentivo))								   
					{
					   periodoDelMesSiguiente(intTipoSocio,
											  intMaxPeriodoEfectuado,
											  intMaxPeriodoEnviado,
											  pk,
											  socios);
					}else
						{	
							completandoLoQueFaltaEnviar(intTipoSocio,
														pk,envioResumenHaber,
														envioResumenIncentivo,
														intMaxPeriodoEnviado);							
						}									
								
					}
					 else{
						 	fechasNuevas(intTipoSocio, pk);
					 	 }				
			}
			else
			{
				mostrarMensaje(Boolean.FALSE,
							  "La unidad seleccionada no tiene registrado socios para "
							   +strTipoSociOcas);
				return;
			}					
		
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	private void completandoLoQueFaltaEnviar(Integer intTipoSocio,
			  								 EstructuraId pk,
			  								 Boolean envioResumenHaber,
			  								 Boolean envioResumenIncentivo,
			  								 Integer intMaxPeriodoEnviado) {
		List<Socio> socios = null;
		try
		{
			log.debug("completando lo que falta y agregando lo ya hecho ");
			PlanillaFacadeLocal localPlanilla 		= (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);  
			   if(dtoDeEnvio.getBlnHaberes().equals(envioResumenHaber))
			   {
				   //falta
				   intNroHaberesOf++;
				   blnEnviadaHaber 	= Boolean.TRUE;
				   blnCheckHaber 	= Boolean.TRUE;
			   }
			   else if(dtoDeEnvio.getBlnIncentivo().equals(envioResumenIncentivo))
			   {
				   //bloquear y dar check
				   intNroIncentivoOf++;
				   blnEnviadaIncentivo 	= Boolean.TRUE; 
				   blnCheckIncentivo 	= Boolean.TRUE;
			   }
			habilitarInflada = Boolean.FALSE;									
			
			dtoDeEnvio.setEnvioConcepto(new Envioconcepto());
			setPeriodoPlanillaIgualAlmismosEnvio(dtoDeEnvio.getEnvioConcepto(),
												 intMaxPeriodoEnviado);
			
			planillaAdministra = localPlanilla.getPlanillaPorIdEstructuraYTipoSocioYPeriodo(pk, 
																						    intTipoSocio,
																						    dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla(),
																						    socios);
			
			
			 if(planillaAdministra != null && !planillaAdministra.isEmpty())
			  {
				 planilla = new ArrayList<ItemPlanilla>();
				 for(PlanillaAdministra global:planillaAdministra)
				 {
					 log.debug("admi:"+global.getSubSucursalPK().getIntIdSucursal()+" subAdm: "+global.getSubSucursalPK().getIntIdSubSucursal());
					 for(ItemPlanilla planillaSinAdministra: global.getListaPlanilla())
					 {														 
						 planilla.add(planillaSinAdministra);
					 }
				 }

				 
					if(planilla != null && !planilla.isEmpty())
					{									
							for(ItemPlanilla illa: planilla)
							{
								for(SocioEstructura  sora: illa.getSocio().getListSocioEstructura())
								{
									if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(sora.getIntModalidad()) == 0)
									{
										intNroHaberes++;															
									}
									if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(sora.getIntModalidad()) == 0)
									{
										intNroIncentivo++;													
									}
								}
							}
							
							//Ordenamos por nombre
							Collections.sort(planilla, new Comparator<ItemPlanilla>(){
								public int compare(ItemPlanilla uno, ItemPlanilla otro) {															
									return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
								}
							});
							
							if(intNroHaberes>0)
							{
								dtoDeEnvio.setBlnHaberes(Boolean.TRUE);
							}
							else
							{
								dtoDeEnvio.setBlnHaberes(Boolean.FALSE);
							}
							if(intNroIncentivo>0)
							{
								dtoDeEnvio.setBlnIncentivo(Boolean.TRUE);
							}
							else
							{
								dtoDeEnvio.setBlnIncentivo(Boolean.FALSE);
							}
							strTipoMantValidarDeEnvio = Constante.MANTENIMIENTO_NINGUNO;
							msgMantDeEnvio.setStrValidarEnvio(null);
							
							datosValidados = Boolean.TRUE;
							habilitarGrabar = Boolean.TRUE;
							deshabilitarNuevo = Boolean.FALSE;			
							ocultarMensaje();												
							log.info("completando lo que falta y agregando lo ya hecho ");					
						
					}
					else
					{
						mostrarMensaje(Boolean.FALSE, "  La unidad seleccionada no tiene registrado socios ");
						return;
					}				 
			  }else{
				  mostrarMensaje(Boolean.FALSE, "Error en listar el enviado de planilla.");
				  return;
			  }
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		
	}
	
	
	private void periodoDelMesSiguiente(Integer intTipoSocio,
										Integer intMaxPeriodoEfectuado,
										Integer intMaxPeriodoEnviado,
										EstructuraId pk,
										List<Socio> socios) {
		log.debug("periodoDelMesSiguiente INICIO");
		Integer idGrupo = 0;
		String haberes       = null;
		String incentivo     = null;
		listaMensajes = null;
		listaMensajes = new ArrayList<EnvioMsg>();
		CierreCobranzaPlanilla cierreCobranzaPlanilla = null;
		try
		{
			EfectuadoResumenBO boEfectuadoresumen 	= (EfectuadoResumenBO)TumiFactory.get(EfectuadoResumenBO.class);
			EstructuraFacadeRemote remoteEstrucutra = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			PlanillaFacadeLocal localPlanilla 		= (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			CierreCobranzaPlanillaBO boCierre		=  (CierreCobranzaPlanillaBO)TumiFactory.get(CierreCobranzaPlanillaBO.class);
			EfectuadoBO boEfectuado					= (EfectuadoBO)TumiFactory.get(EfectuadoBO.class);		
			log.debug("cumple lo de envioresumen");
					if(intMaxPeriodoEfectuado != null)
					{	
						 log.debug("intMaxPeriodoEfectuado="+intMaxPeriodoEfectuado);
						 
						 if(intMaxPeriodoEnviado.compareTo(intMaxPeriodoEfectuado) == 0)
						 {	
							 log.debug("son iguales los periodo enviado con efectuado");
							 //tanto en haberes, incentivo comparo en efectuadoresumen haber, incentivo
								dtoDeEnvio.setBlnHaberes(Boolean.FALSE);
								dtoDeEnvio.setBlnIncentivo(Boolean.FALSE);
								
								Boolean efectuadoResumenHaber     = Boolean.FALSE;
								Boolean efectuadoResumenIncentivo = Boolean.FALSE;												
								
									
								efectuadoH:	for (Socio socio : socios)
										{
											for(SocioEstructura socioEs:socio.getListSocioEstructura())
											{
												if(socioEs.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0)
												{
													dtoDeEnvio.setBlnHaberes(Boolean.TRUE);
													List<EfectuadoResumen> listaEfectuadoResumenHaber = boEfectuadoresumen.getListaPorEntidadyPeriodo(EMPRESA_USUARIO, 
																																					intMaxPeriodoEnviado,
																																					intTipoSocio,
																																					Constante.PARAM_T_MODALIDADPLANILLA_HABERES,
																																					pk.getIntNivel(),
																																					socioEs.getIntCodigo());													
													if(listaEfectuadoResumenHaber != null && !listaEfectuadoResumenHaber.isEmpty())
													{
														efectuadoResumenHaber=true;
													}
													break efectuadoH;
												}
											}
										}
										
										efectuadoI: for (Socio socio : socios)
										{
											for(SocioEstructura socioEs:socio.getListSocioEstructura())
											{
											 if(socioEs.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)==0)
												{
													dtoDeEnvio.setBlnIncentivo(Boolean.TRUE);
													List<EfectuadoResumen> listaEfectuadoResumenIncentivo = boEfectuadoresumen.getListaPorEntidadyPeriodo(EMPRESA_USUARIO, 
																																						intMaxPeriodoEnviado,
																																						intTipoSocio,
																																						Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS,
																																						pk.getIntNivel(),
																																						socioEs.getIntCodigo());													
													if(listaEfectuadoResumenIncentivo != null && !listaEfectuadoResumenIncentivo.isEmpty())
													{
														efectuadoResumenIncentivo = true;
													}
													break efectuadoI;
												}
											}
										}
							log.debug("blnHaberes: "+dtoDeEnvio.getBlnHaberes()+
									  ", efectuadoResumenHaber: "+efectuadoResumenHaber+
									  ", dtoDeEnvio.getBlnIncentivo(): "+dtoDeEnvio.getBlnIncentivo()+
									  ", efectuadoResumenIncentivo: "+efectuadoResumenIncentivo);			
							if(dtoDeEnvio.getBlnHaberes().equals(efectuadoResumenHaber)&&											
								dtoDeEnvio.getBlnIncentivo().equals(efectuadoResumenIncentivo))															
							{																												
								//si son iguales que haga lo del mes siguiente en el periodo	
								log.info("haciendo el periodo del mes siguiente ya que tambien cumplio lo de efectuado");
								dtoDeEnvio.setEnvioConcepto(new Envioconcepto());
								setPeriodoPlanillaAlUltimoEnvio(dtoDeEnvio.getEnvioConcepto(),  intMaxPeriodoEnviado);
											
								if(dtoDeEnvio.getEstructura().getId().getIntNivel().compareTo(1) == 0)
								{
									EstructuraId id = new EstructuraId();
									id.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
									id.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
									Estructura estructuraT = remoteEstrucutra.getEstructuraPorPk(id);
									if(estructuraT != null)
									{
										idGrupo = estructuraT.getIntIdGrupo();
									}												
								}else if(dtoDeEnvio.getEstructura().getId().getIntNivel().compareTo(2) == 0)
									{
										EstructuraId id = new EstructuraId();
										id.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
										id.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
										Estructura estructuraTemporal = remoteEstrucutra.getEstructuraPorPk(id);
										if(estructuraTemporal != null)
										{
											EstructuraId id2 = new EstructuraId();
											id2.setIntCodigo(estructuraTemporal.getIntIdCodigoRel());
											id2.setIntNivel(estructuraTemporal.getIntNivelRel());
											Estructura estructuraT = remoteEstrucutra.getEstructuraPorPk(id2);
											if(estructuraT != null)
											{
												idGrupo = estructuraT.getIntIdGrupo();
											}													
										}
									}
									socios = null;
									planilla = null;
									planillaAdministra = localPlanilla.getPlanillaPorIdEstructuraYTipoSocioYPeriodo(pk,
																												  	intTipoSocio,
																												  	dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla(),
																												  	socios);
									if(planillaAdministra != null && !planillaAdministra.isEmpty())
									  {
											blnEnviadaHaber = Boolean.FALSE;
											blnEnviadaIncentivo = Boolean.FALSE;											
											planilla = new ArrayList<ItemPlanilla>();
											
												for(PlanillaAdministra global:planillaAdministra)
												 { 													 
													if(global.getListaPlanilla() != null && !global.getListaPlanilla().isEmpty())															 
													{
														for(ItemPlanilla planillaSinAdministra: global.getListaPlanilla())
														{														 
														 planilla.add(planillaSinAdministra);
														} 
													}else 
													{
														mostrarMensaje(Boolean.FALSE, " Error no controlado al momento de obtener los registros de validación.");
														return;
													}
												 }
										
									  
									 // } flyalico
												if(planilla != null && !planilla.isEmpty())
												{
													//planillaTemp = new ArrayList<ItemPlanilla>();
													for(ItemPlanilla itemp: planilla)
													{																				
															log.debug("cant de socioestructura="+itemp.getSocio().getListSocioEstructura().size());
															
															for(SocioEstructura socioEstructura: itemp.getSocio().getListSocioEstructura())
															{
																EstructuraId pId = new EstructuraId();
																pId.setIntCodigo(socioEstructura.getIntCodigo());
																pId.setIntNivel(pk.getIntNivel());
																																						
																if(socioEstructura.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0)
																{
																	log.debug("haberalo v1");
																	CierreCobranzaPlanilla o = new CierreCobranzaPlanilla();
																	o.setId(new CierreCobranzaPlanillaId());												
																	o.getId().setIntPersEmpresaCierreCob(EMPRESA_USUARIO);												
																	o.getId().setIntPeriodoCierre(dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());											
																	o.getId().setIntEstrucGrupo(idGrupo);
																	o.getId().setIntParaTipoSocio(intTipoSocio);
																	o.getId().setIntParaModalidad(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
																	cierreCobranzaPlanilla = boCierre.getCierreCobranzaPlanillaValidarEnvio(o);
																	
																	if(cierreCobranzaPlanilla != null )
																	{													
																		 if(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO.
																				compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0)
																		{
																			 blnEnviadaHaber = Boolean.TRUE;
																		}
																		 else if(Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO.compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0
																					|| Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO0.compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0)
																		 {
																			 	intNroHaberes++;
																																																																
																				List<Efectuado> listaEfectuadHaber = boEfectuado.getListaEfectuadoPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(EMPRESA_USUARIO,
																																																	pId,
																																																	Constante.PARAM_T_MODALIDADPLANILLA_HABERES,
																																																	intMaxPeriodoEfectuado);

																				if(listaEfectuadHaber != null && !listaEfectuadHaber.isEmpty())
																				{	
																					for(Efectuado efectuad: listaEfectuadHaber)
																					{	
																						if(efectuad.getIntCuentaPk().compareTo(itemp.getCuentaIntegrante().getId().getIntCuenta()) == 0)
																						{																											
																							itemp.setBdHaberesEfectuado(efectuad.getBdMontoEfectuado());
																							break;
																						}																						
																					}
																				}																								
																				break;
																		 }
																	}
																	else
																	{
																		intNroHaberes++;
																																																														
																		List<Efectuado> listaEfectuadHaber = boEfectuado.getListaEfectuadoPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(EMPRESA_USUARIO,
																																														  pId,
																																														  Constante.PARAM_T_MODALIDADPLANILLA_HABERES,
																																														  intMaxPeriodoEfectuado);

																		if(listaEfectuadHaber != null && !listaEfectuadHaber.isEmpty())
																		{																								
																			for(Efectuado efectuad: listaEfectuadHaber)
																			{	
																				if(efectuad.getIntCuentaPk().compareTo(itemp.getCuentaIntegrante().getId().getIntCuenta()) == 0)
																				{																											
																					itemp.setBdHaberesEfectuado(efectuad.getBdMontoEfectuado());
																					break;
																				}																								
																			}
																		}																						
																		break;
																	}																					
																}
															}
																for(SocioEstructura socioEstructura2: itemp.getSocio().getListSocioEstructura())
																{
																	EstructuraId pId2 = new EstructuraId();
																	pId2.setIntCodigo(socioEstructura2.getIntCodigo());
																	pId2.setIntNivel(pk.getIntNivel());
																 if(socioEstructura2.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)==0)
																{
																	log.debug("incentivos v1");
																	CierreCobranzaPlanilla o = new CierreCobranzaPlanilla();
																	o.setId(new CierreCobranzaPlanillaId());												
																	o.getId().setIntPersEmpresaCierreCob(EMPRESA_USUARIO);												
																	o.getId().setIntPeriodoCierre(dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());											
																	o.getId().setIntEstrucGrupo(idGrupo);
																	o.getId().setIntParaTipoSocio(intTipoSocio);
																	o.getId().setIntParaModalidad(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS);
																	cierreCobranzaPlanilla = boCierre.getCierreCobranzaPlanillaValidarEnvio(o);
																	
																	if(cierreCobranzaPlanilla != null )
																	{													
																		log.debug("tiene cierre"); 
																		if(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO.
																				compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0)
																		{
																			 blnEnviadaIncentivo = Boolean.TRUE;
																		}
																		 else if(Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO.compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0
																					|| Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO0.compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0)
																		 {
																				intNroIncentivo++;
																																														
																				List<Efectuado> listaEfectuadIncentivo = boEfectuado.getListaEfectuadoPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(EMPRESA_USUARIO,
																																																		pId2,
																																																		Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS,
																																																		intMaxPeriodoEfectuado);																																															
																				
																				if(listaEfectuadIncentivo != null && !listaEfectuadIncentivo.isEmpty())
																				{
																					for(Efectuado efectua: listaEfectuadIncentivo)
																					{	
																						if(efectua.getIntCuentaPk().compareTo(itemp.getCuentaIntegrante().getId().getIntCuenta()) == 0)
																						{																											
																							itemp.setBdIncentivosEfectuado(efectua.getBdMontoEfectuado());
																							break;
																						}																						
																					}
																				}																				
																				break;
																		 }
																	}
																	else 
																	{
																		intNroIncentivo++;
																		log.debug("no tiene cierre = null");																								
																		List<Efectuado> listaEfectuadIncentivo = boEfectuado.getListaEfectuadoPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(EMPRESA_USUARIO,
																																															pId2,
																																															Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS,
																																															intMaxPeriodoEfectuado);																																									
																		
																		if(listaEfectuadIncentivo != null && !listaEfectuadIncentivo.isEmpty())
																		{
																			log.debug("aqui="+listaEfectuadIncentivo.size());
																			for(Efectuado efectua: listaEfectuadIncentivo)
																			{	
																				log.debug(efectua);
																				if(efectua.getIntCuentaPk().compareTo(itemp.getCuentaIntegrante().getId().getIntCuenta()) == 0)
																				{																											
																					itemp.setBdIncentivosEfectuado(efectua.getBdMontoEfectuado());
																					break;
																				}																								
																			}
																		}																		
																		break;
																	}																				
																}																			  
															}																	
													}
													
													if(planilla != null && !planilla.isEmpty())
													{
														datosValidados = Boolean.TRUE;
														habilitarGrabar = Boolean.TRUE;
														deshabilitarNuevo = Boolean.FALSE;			
														ocultarMensaje();
														
														//Ordenamos por nombre
														Collections.sort(planilla, new Comparator<ItemPlanilla>(){
															public int compare(ItemPlanilla uno, ItemPlanilla otro) {															
																return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
															}
														});
														
														if(intNroHaberes>0)
														{
															dtoDeEnvio.setBlnHaberes(Boolean.TRUE);
														}else
														{
															dtoDeEnvio.setBlnHaberes(Boolean.FALSE);
														}
														if(intNroIncentivo>0)
														{
															dtoDeEnvio.setBlnIncentivo(Boolean.TRUE);
														}else
														{
															dtoDeEnvio.setBlnIncentivo(Boolean.FALSE);
														}
														intNroCasOf 		= 0; 																	
														strTipoMantValidarDeEnvio = Constante.MANTENIMIENTO_NINGUNO;
														msgMantDeEnvio.setStrValidarEnvio(null);
													}
													else
													{	
														mostrarMensaje(Boolean.FALSE, " No hay modalidades haberes e incentivos para esta unidad.");
														return;
													}																	
												}												
												else
												{																	
													mostrarMensaje(Boolean.FALSE, "Error en listar el enviado de planillaA.");
													return;
												}
									  }else{
										  mostrarMensaje(Boolean.FALSE, "Error en listar el enviado de planillab.");
										  return;
									  }
							}
							else {									
									if(dtoDeEnvio.getBlnHaberes())
									{													
											if(!efectuadoResumenHaber)
											{
												haberes = "Haber ";
											}
											else
											{
												haberes = "";
											}														
									}else
									{
										if(!efectuadoResumenHaber)
										{
											haberes = "";
										}
									}
									
									if(dtoDeEnvio.getBlnIncentivo())
									{
										if(!efectuadoResumenIncentivo)
										{
												incentivo = "incentivo ";
										}else
										{
												incentivo = "";
										}
									}else
									{
										if(!efectuadoResumenIncentivo)
										{
											incentivo = "";
										}
									}																											
							
									mostrarMensaje(Boolean.FALSE, " Pendiente de realizar efectuado de la entidad "+
													dtoDeEnvio.getEstructura().getJuridica().getStrRazonSocial()+
											        " planilla "+haberes+incentivo+" para el periodo " +intMaxPeriodoEnviado+".");
									return;	
							}
						 }
						 else
						 {
							mostrarMensaje(Boolean.FALSE, " Aún no se realiza el Efectuado del periodo " + intMaxPeriodoEnviado);											
							return;
						}
					}
					else
					{
						mostrarMensaje(Boolean.FALSE, " Aún no se realiza el Efectuado, periodo " + intMaxPeriodoEnviado);										
						return;
					}						
		log.debug("periodoDelMesSiguiente FIN");
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		
	}
	
	private void fechasNuevas(Integer intTipoSocio,
							  EstructuraId pk) {
		log.info("fechasNuevas INICIO");
		Integer idGrupo 	 = 0;
		CierreCobranzaPlanilla cierreCobranzaPlanilla = null;
		try
		{	
			EstructuraFacadeRemote remoteEstrucutra = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			PlanillaFacadeLocal localPlanilla 		= (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			CierreCobranzaPlanillaBO boCierre		=  (CierreCobranzaPlanillaBO)TumiFactory.get(CierreCobranzaPlanillaBO.class);
			
			List<Socio> socios= null;
			log.info("fechas nuevas del 05 y 20 ya que intMaxPeriodoEnviado es null");
			log.debug("saltoEnviado="+dtoDeEnvio.getEstructuraDetalle().getIntSaltoEnviado());
			log.debug("diaEnviado="+dtoDeEnvio.getEstructuraDetalle().getIntDiaEnviado());
			dtoDeEnvio.setEnvioConcepto(new Envioconcepto());
			setPeriodoPlanilla(dtoDeEnvio.getEnvioConcepto(),
							   dtoDeEnvio.getEstructuraDetalle().getIntSaltoEnviado(),								
							   dtoDeEnvio.getEstructuraDetalle().getIntDiaEnviado());
					
			blnEnviadaHaber = Boolean.FALSE;
			blnEnviadaIncentivo = Boolean.FALSE;
			if(dtoDeEnvio.getEstructura().getId().getIntNivel().compareTo(1) == 0)
				{
					EstructuraId id = new EstructuraId();
					id.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
					id.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
					Estructura estructuraT = remoteEstrucutra.getEstructuraPorPk(id);
					idGrupo = estructuraT.getIntIdGrupo();
				}
			else if(dtoDeEnvio.getEstructura().getId().getIntNivel().compareTo(2) == 0)
				{
					EstructuraId id = new EstructuraId();
					id.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
					id.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
					Estructura estructuraTemporal = remoteEstrucutra.getEstructuraPorPk(id);
					if(estructuraTemporal != null)
					{
						EstructuraId id2 = new EstructuraId();
						id2.setIntCodigo(estructuraTemporal.getIntIdCodigoRel());
						id2.setIntNivel(estructuraTemporal.getIntNivelRel());
						Estructura estructuraT = remoteEstrucutra.getEstructuraPorPk(id2);
						idGrupo = estructuraT.getIntIdGrupo();
					}
				}			
					
				planillaAdministra = localPlanilla.getPlanillaPorIdEstructuraYTipoSocioYPeriodo(pk, 
																						    	intTipoSocio,
																						    	dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla(),
																						    	socios);
				planilla = new ArrayList<ItemPlanilla>();
				 for(PlanillaAdministra global:planillaAdministra)
					{
						log.debug("admi:"+global.getSubSucursalPK().getIntIdSucursal()+
								  " subAdm: "+global.getSubSucursalPK().getIntIdSubSucursal());
						for(ItemPlanilla planillaSinAdministra: global.getListaPlanilla())
						{														 
							planilla.add(planillaSinAdministra);
						}
					 }
					
					if(planilla!=null && !planilla.isEmpty())
					{
						log.debug("tiene data");
						log.debug("planilla:"+planilla.size());
						
						CIERREHABER:for(ItemPlanilla ia: planilla)
						{
							for(SocioEstructura sa: ia.getSocio().getListSocioEstructura())
							{
								if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(sa.getIntModalidad()) == 0)
								{
									CierreCobranzaPlanilla o = new CierreCobranzaPlanilla();
									o.setId(new CierreCobranzaPlanillaId());												
									o.getId().setIntPersEmpresaCierreCob(EMPRESA_USUARIO);												
									o.getId().setIntPeriodoCierre(dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());											
									o.getId().setIntEstrucGrupo(idGrupo);
									o.getId().setIntParaTipoSocio(intTipoSocio);
									o.getId().setIntParaModalidad(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
									cierreCobranzaPlanilla = boCierre.getCierreCobranzaPlanillaValidarEnvio(o);
									
									if(cierreCobranzaPlanilla != null )
									{	//log.debug("cierre != null en haberes"); 
										if(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO.
												compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0)
										{
											 blnEnviadaHaber = Boolean.TRUE;														
										}
									}					
									
									break CIERREHABER;
								}
							}
						}
						CIERREINCENTIVO:	for(ItemPlanilla ia: planilla)
						{	
							for(SocioEstructura sa2: ia.getSocio().getListSocioEstructura())
							{
								if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(sa2.getIntModalidad()) == 0)
								{
									CierreCobranzaPlanilla o = new CierreCobranzaPlanilla();
									o.setId(new CierreCobranzaPlanillaId());												
									o.getId().setIntPersEmpresaCierreCob(EMPRESA_USUARIO);												
									o.getId().setIntPeriodoCierre(dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());											
									o.getId().setIntEstrucGrupo(idGrupo);
									o.getId().setIntParaTipoSocio(intTipoSocio);
									o.getId().setIntParaModalidad(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS);
									cierreCobranzaPlanilla = boCierre.getCierreCobranzaPlanillaValidarEnvio(o);
									
									if(cierreCobranzaPlanilla != null )
									{	 
										if(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO.
												compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0)
										{
											 blnEnviadaIncentivo = Boolean.TRUE;														
										}
									}	
									break CIERREINCENTIVO;
								}
							}						
						}
						intNroHaberes = 0;
						intNroIncentivo =0;
						for(ItemPlanilla illa: planilla)
						{
							for(SocioEstructura  sora: illa.getSocio().getListSocioEstructura())
							{
								if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(sora.getIntModalidad()) == 0)
								{
									intNroHaberesOf++;
									intNroHaberes++;														
								}
							}
							for(SocioEstructura  sora1: illa.getSocio().getListSocioEstructura())
							{
								if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(sora1.getIntModalidad()) == 0)
								{
									intNroIncentivo++;
									intNroIncentivoOf++;
								}
							}
						}
						if(intNroHaberes>0)
						{
							dtoDeEnvio.setBlnHaberes(Boolean.TRUE);
						}
						else
						{
							dtoDeEnvio.setBlnHaberes(Boolean.FALSE);
						}
						if(intNroIncentivo>0)
						{
							dtoDeEnvio.setBlnIncentivo(Boolean.TRUE);
						}
						else
						{
							dtoDeEnvio.setBlnIncentivo(Boolean.FALSE);
						}
						strTipoMantValidarDeEnvio = Constante.MANTENIMIENTO_NINGUNO;
						msgMantDeEnvio.setStrValidarEnvio(null);							
						intNroCasOf 		= 0;								
						
						//Ordenamos por apellido
						Collections.sort(planilla, new Comparator<ItemPlanilla>(){
							public int compare(ItemPlanilla uno, ItemPlanilla otro) {															
								return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
							}
						});
						datosValidados = Boolean.TRUE;
						habilitarGrabar = Boolean.TRUE;
						deshabilitarNuevo = Boolean.FALSE;			
						ocultarMensaje();
						log.info("acabando lo de las fechas 05 12 ");
					}
					else 
					{
						mostrarMensaje(Boolean.FALSE,
										"La unidad seleccionada no tiene registrado socios para "
									    +strTipoSociOcas);
						return;
					}											
		
			log.info("fechasNuevas FIN");	
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}		
	}
	
	private void casenEnviado(EstructuraId pk)
	{
		List<ItemPlanilla> planillaTemp           = new ArrayList<ItemPlanilla>();
		EstructuraDetalleId lEstructuraDetalleId = null;
		EstructuraDetalle lEstructuraDetalle     = null;
		CierreCobranzaPlanilla cierreCobranzaPlanilla = null;
		Integer intMaxPeriodoEnviado 	= 0;
		Integer intMaxPeriodoEfectuado   = 0;
		Integer idGrupo =0;
		try
		{	log.info("CAS SOLO CAS CAS CAS");
			EstructuraFacadeRemote remoteEstrucutra = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);			
			PlanillaFacadeLocal localPlanilla 		= (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			CierreCobranzaPlanillaBO boCierre		=  (CierreCobranzaPlanillaBO)TumiFactory.get(CierreCobranzaPlanillaBO.class);
			 EfectuadoBO boEfectuado				= (EfectuadoBO)TumiFactory.get(EfectuadoBO.class);
			 
			lEstructuraDetalleId = new EstructuraDetalleId();
			lEstructuraDetalleId.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
			lEstructuraDetalleId.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
			
			lEstructuraDetalle = remoteEstrucutra.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(lEstructuraDetalleId,
																											Constante.PARAM_T_TIPOSOCIO_ACTIVO,														
																											Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA);
			if(lEstructuraDetalle != null)
			{						
				dtoDeEnvio.setEstructuraDetalle(lEstructuraDetalle);
				dtoDeEnvio.setBlnActivo(Boolean.TRUE);
				
				intMaxPeriodoEnviado = localPlanilla.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioCAS(new Integer(EMPRESA_USUARIO),
																											 pk,
																											 new Integer(Constante.PARAM_T_TIPOSOCIO_ACTIVO));
				
				log.debug("maxperidoenviadoCAS"+intMaxPeriodoEnviado);
										
				if(intMaxPeriodoEnviado != null)
				{
					log.info("intMaxPeriodoEnviado="+intMaxPeriodoEnviado);							
				
						dtoDeEnvio.setBlnCas(Boolean.FALSE);							
						
						intMaxPeriodoEfectuado = localPlanilla.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidad(EMPRESA_USUARIO,
																																pk,
																																Constante.PARAM_T_TIPOSOCIO_ACTIVO, 
																																Constante.PARAM_T_MODALIDADPLANILLA_CAS);
						
							if(intMaxPeriodoEfectuado != null)
							{
								log.info("intMaxPeriodoEfectuado="+intMaxPeriodoEfectuado);
								 
								 if(intMaxPeriodoEnviado.compareTo(intMaxPeriodoEfectuado) == 0)
								 {											 
									 	log.debug("Hacer el siguiente periodo de CASHacer el siguiente periodo de CAS");
									 	dtoDeEnvio.setEnvioConcepto(new Envioconcepto());
										setPeriodoPlanillaAlUltimoEnvio(dtoDeEnvio.getEnvioConcepto(),  intMaxPeriodoEnviado);
										
										if(dtoDeEnvio.getEstructura().getId().getIntNivel().compareTo(1) == 0)
										{
											EstructuraId id = new EstructuraId();
											id.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
											id.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
											Estructura estructuraT = remoteEstrucutra.getEstructuraPorPk(id);
											idGrupo = estructuraT.getIntIdGrupo();
										}
										else if(dtoDeEnvio.getEstructura().getId().getIntNivel().compareTo(2) == 0)
										{
											EstructuraId id = new EstructuraId();
											id.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
											id.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
											Estructura estructuraTemporal = remoteEstrucutra.getEstructuraPorPk(id);
											if(estructuraTemporal != null)
											{
												EstructuraId id2 = new EstructuraId();
												id2.setIntCodigo(estructuraTemporal.getIntIdCodigoRel());
												id2.setIntNivel(estructuraTemporal.getIntNivelRel());
												Estructura estructuraT = remoteEstrucutra.getEstructuraPorPk(id2);
												idGrupo = estructuraT.getIntIdGrupo();
											}
										}			
										
										CierreCobranzaPlanilla o = new CierreCobranzaPlanilla();
										o.setId(new CierreCobranzaPlanillaId());												
										o.getId().setIntPersEmpresaCierreCob(EMPRESA_USUARIO);												
										o.getId().setIntPeriodoCierre(dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());											
										o.getId().setIntEstrucGrupo(idGrupo);
										o.getId().setIntParaTipoSocio(Constante.PARAM_T_TIPOSOCIO_ACTIVO);
										o.getId().setIntParaModalidad(Constante.PARAM_T_MODALIDADPLANILLA_CAS);
										cierreCobranzaPlanilla = boCierre.getCierreCobranzaPlanillaValidarEnvio(o);
										
										if(cierreCobranzaPlanilla != null )
										{													
											 if(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO.
													compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0)
											{	
												 mostrarMensaje(Boolean.FALSE, " Este periodo se encuentra cerrado y " +
											 					"restringido de procesar planillas enviadas");
												 return;
											}
											 
											 else if(Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO.compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0
														|| Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO0.compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0)
											 {
												 planillaAdministraCAS = localPlanilla.getPlanillaPorIdEstructuraYTipoSocioYPeriodoCAS(pk,
																																	   Constante.PARAM_T_TIPOSOCIO_ACTIVO,
																																	   dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());
												 planillaCAS = new ArrayList<ItemPlanilla>();
												 for(PlanillaAdministra global:planillaAdministraCAS)
												 {
													 log.debug("admi:"+global.getSubSucursalPK().getIntIdSucursal()+" subAdm: "+global.getSubSucursalPK().getIntIdSubSucursal());
													 for(ItemPlanilla planillaSinAdministra: global.getListaPlanilla())
													 {														 
														 planillaCAS.add(planillaSinAdministra);
													 }
												 }
												
												 if(planillaCAS != null && !planillaCAS.isEmpty())
												{
													planillaTemp = new ArrayList<ItemPlanilla>();
													//que solo me quede con los CAS
													
													for(ItemPlanilla itemp:planillaCAS)
													{													
															if(itemp.getSocio().getListSocioEstructura() != null)
															{
																for(SocioEstructura socEst: itemp.getSocio().getListSocioEstructura())
																{														
																	if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(socEst.getIntModalidad())== 0)
																	{																		
																			EstructuraId pId = new EstructuraId();
																			pId.setIntCodigo(socEst.getIntCodigo());
																			pId.setIntNivel(pk.getIntNivel());	
																			intNroCas++;
																			
																			List<Efectuado> listaEfectuadCAS = boEfectuado.getListaEfectuadoPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(EMPRESA_USUARIO,
																																							 								pId,
																																							 								Constante.PARAM_T_MODALIDADPLANILLA_CAS,
																																							 								intMaxPeriodoEfectuado);
																			if(listaEfectuadCAS != null && !listaEfectuadCAS.isEmpty())
																			{	
																				for(Efectuado  efectuad: listaEfectuadCAS)
																				{																					
																					if(efectuad.getIntCuentaPk().compareTo(itemp.getCuentaIntegrante().getId().getIntCuenta()) == 0)
																					{
																						itemp.setEfectuado(efectuad);
																						itemp.setBdCasEfectuado(efectuad.getBdMontoEfectuado());
																						break;
																					}																					
																				}
																			}	
																			planillaTemp.add(itemp);
																	}																	
																}																
															}													
													}
													planillaCAS = planillaTemp;
													if(planillaCAS != null && !planillaCAS.isEmpty())
													{														
														//Ordenamos por nombre
														Collections.sort(planillaCAS, new Comparator<ItemPlanilla>(){
															public int compare(ItemPlanilla uno, ItemPlanilla otro) {															
																return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
															}
														});
														
														datosValidados = Boolean.TRUE;
														habilitarGrabar = Boolean.TRUE;
														deshabilitarNuevo = Boolean.FALSE;			
														ocultarMensaje();
														
														if(intNroCas > 0)
														{
															dtoDeEnvio.setBlnCas(Boolean.TRUE);																
														}
														else
														{
															dtoDeEnvio.setBlnCas(Boolean.FALSE);																
															mostrarMensaje(Boolean.FALSE, "No hay socios con la modalidad CAS");
															return;
														}							
														strTipoMantValidarDeEnvio = Constante.MANTENIMIENTO_NINGUNO;
														msgMantDeEnvio.setStrValidarEnvio(null);	
													}																										
												}								
											 }
										}
										else
										{
											planillaAdministraCAS = localPlanilla.getPlanillaPorIdEstructuraYTipoSocioYPeriodoCAS(pk,
																																  Constante.PARAM_T_TIPOSOCIO_ACTIVO ,
																																  dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());
											planillaCAS = new ArrayList<ItemPlanilla>();
											for(PlanillaAdministra global:planillaAdministraCAS)
											 {
												log.debug("admi:"+global.getSubSucursalPK().getIntIdSucursal()+" subAdm: "+global.getSubSucursalPK().getIntIdSubSucursal()); 
												for(ItemPlanilla planillaSinAdministra: global.getListaPlanilla())
												 {													 
													 planillaCAS.add(planillaSinAdministra);
												 }
											 }
											
											if(planillaCAS != null && !planillaCAS.isEmpty())
											{
												planillaTemp = new ArrayList<ItemPlanilla>();
												//que solo me quede con los CAS												
												for(ItemPlanilla itemp:planillaCAS)
												{													
														if(itemp.getSocio().getListSocioEstructura() != null)
														{
															for(SocioEstructura socEst: itemp.getSocio().getListSocioEstructura())
															{
																if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(socEst.getIntModalidad())== 0)
																{																		
																		EstructuraId pId = new EstructuraId();
																		pId.setIntCodigo(socEst.getIntCodigo());
																		pId.setIntNivel(pk.getIntNivel());	
																		intNroCas++;
																		
																		List<Efectuado> listaEfectuadCAS = boEfectuado.getListaEfectuadoPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(EMPRESA_USUARIO,
																																						 								pId,
																																						 								Constante.PARAM_T_MODALIDADPLANILLA_CAS,
																																						 								intMaxPeriodoEfectuado);
																		if(listaEfectuadCAS != null && !listaEfectuadCAS.isEmpty())
																		{
																			for(Efectuado  efectuad: listaEfectuadCAS)
																			{																				
																				if(efectuad.getIntCuentaPk().compareTo(itemp.getCuentaIntegrante().getId().getIntCuenta()) == 0)
																				{
																					itemp.setEfectuado(efectuad);
																					itemp.setBdCasEfectuado(efectuad.getBdMontoEfectuado());
																					break;
																				}																				
																			}
																		}	
																		planillaTemp.add(itemp);
																}																
															}															
														}													
												}
												planillaCAS = planillaTemp;
												if(planillaCAS != null && !planillaCAS.isEmpty())
												{														
													//Ordenamos por nombre
													Collections.sort(planillaCAS, new Comparator<ItemPlanilla>(){
														public int compare(ItemPlanilla uno, ItemPlanilla otro) {															
															return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
														}
													});
													
													if(intNroCas > 0)
													{
														dtoDeEnvio.setBlnCas(Boolean.TRUE);
														datosValidados = Boolean.TRUE;
														habilitarGrabar = Boolean.TRUE;
														deshabilitarNuevo = Boolean.FALSE;			
														ocultarMensaje();
													}
													else
													{
														dtoDeEnvio.setBlnCas(Boolean.FALSE);														
														mostrarMensaje(Boolean.FALSE, "No hay socios con la modalidad CAS");
														return;
													}							
													strTipoMantValidarDeEnvio = Constante.MANTENIMIENTO_NINGUNO;
													msgMantDeEnvio.setStrValidarEnvio(null);	
												}																										
											}								
										}																								
								 }								 	
								 else
								 {											
									 log.debug("Aún no se realiza el Efectuado del periodo");
									 mostrarMensaje(Boolean.FALSE, " Aún no se realiza el Efectuado del periodo " + intMaxPeriodoEnviado);
									return;			
								 }
							}
							else
							{
								mostrarMensaje(Boolean.FALSE, " No hay maximo Efectuado del periodo " + intMaxPeriodoEnviado);
								return;	
							}
							intNroCasOf 		= 0;									
							intNroCasOf 		= intNroCas;
					}
				
				else
				{
					log.info("intMaxPeriodoEnviado es null " +
							"lo de las fechas nuevas del 05 y 20");
					
					dtoDeEnvio.setEnvioConcepto(new Envioconcepto());
					
					setPeriodoPlanilla(dtoDeEnvio.getEnvioConcepto(),
										dtoDeEnvio.getEstructuraDetalle().getIntSaltoEnviado(),								
								   		dtoDeEnvio.getEstructuraDetalle().getIntDiaEnviado());
					
					log.info("perido="+dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());
					
					if(dtoDeEnvio.getEstructura().getId().getIntNivel().compareTo(1) == 0)
					{
						EstructuraId id = new EstructuraId();
						id.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
						id.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
						Estructura estructuraT = remoteEstrucutra.getEstructuraPorPk(id);
						idGrupo = estructuraT.getIntIdGrupo();
					}
					else if(dtoDeEnvio.getEstructura().getId().getIntNivel().compareTo(2) == 0)
					{
						EstructuraId id = new EstructuraId();
						id.setIntCodigo(dtoDeEnvio.getEstructura().getId().getIntCodigo());
						id.setIntNivel(dtoDeEnvio.getEstructura().getId().getIntNivel());
						Estructura estructuraTemporal = remoteEstrucutra.getEstructuraPorPk(id);
						if(estructuraTemporal != null)
						{
							EstructuraId id2 = new EstructuraId();
							id2.setIntCodigo(estructuraTemporal.getIntIdCodigoRel());
							id2.setIntNivel(estructuraTemporal.getIntNivelRel());
							Estructura estructuraT = remoteEstrucutra.getEstructuraPorPk(id2);
							idGrupo = estructuraT.getIntIdGrupo();
						}
					}
					
						CierreCobranzaPlanilla o = new CierreCobranzaPlanilla();
						o.setId(new CierreCobranzaPlanillaId());												
						o.getId().setIntPersEmpresaCierreCob(EMPRESA_USUARIO);												
						o.getId().setIntPeriodoCierre(dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());
						o.getId().setIntEstrucGrupo(idGrupo);
						log.debug("idGrupo="+idGrupo);
						log.debug("periodo"+dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());
						o.getId().setIntParaTipoSocio(Constante.PARAM_T_TIPOSOCIO_ACTIVO);
						o.getId().setIntParaModalidad(Constante.PARAM_T_MODALIDADPLANILLA_CAS);
						cierreCobranzaPlanilla = boCierre.getCierreCobranzaPlanillaValidarEnvio(o);
						
						if(cierreCobranzaPlanilla != null )
						{
							log.debug("existe cierreCobranzaplanilla");
							if(Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO.compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0
								|| Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO0.compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0)
							{
								log.debug("abierto");
								planillaCAS = null;
								planillaAdministraCAS = localPlanilla.getPlanillaPorIdEstructuraYTipoSocioYPeriodoCAS(pk,
																													Constante.PARAM_T_TIPOSOCIO_ACTIVO, 
																													dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());
						
								planillaCAS = new ArrayList<ItemPlanilla>();	
								for(PlanillaAdministra global:planillaAdministraCAS)
									 {
										log.debug("admi:"+global.getSubSucursalPK().getIntIdSucursal()+" subAdm: "+global.getSubSucursalPK().getIntIdSubSucursal()); 
										for(ItemPlanilla planillaSinAdministra: global.getListaPlanilla())
										 {											 
											 planillaCAS.add(planillaSinAdministra);
										 }
									 }
									if(planillaCAS != null && !planillaCAS.isEmpty())
									{	
										log.info("filtrando solo CAS");
										planillaTemp = new ArrayList<ItemPlanilla>();
										for(ItemPlanilla itemPlanilla: planillaCAS)
										{									
											if(itemPlanilla.getSocio().getListSocioEstructura()!=null 
												&& !itemPlanilla.getSocio().getListSocioEstructura().isEmpty())
											{
												for(SocioEstructura socioEstructura: itemPlanilla.getSocio().getListSocioEstructura())
												{																							
													 if(socioEstructura.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_CAS) == 0)
													{
														intNroCas++;
														planillaTemp.add(itemPlanilla);
													}
												}
												
											}											
																			
										}
										planillaCAS = planillaTemp;
										
										//Ordenamos por nombre
										Collections.sort(planillaCAS, new Comparator<ItemPlanilla>(){
											public int compare(ItemPlanilla uno, ItemPlanilla otro) {															
												return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
											}
										});
										
										log.info("planillaCAS cant"+planillaCAS.size());
										if( planillaTemp == null || planillaTemp.size() == 0)
										{											
											mostrarMensaje(Boolean.FALSE, "  No se encuentran socios con el tipo de socio solicitado para la unidad ejecutora.");
											return;										
										}
																		
										
										if(intNroCas > 0)
										{
											dtoDeEnvio.setBlnCas(Boolean.TRUE);
											datosValidados = Boolean.TRUE;
											habilitarGrabar = Boolean.TRUE;
											deshabilitarNuevo = Boolean.FALSE;			
											ocultarMensaje();
										}
										else
										{
											dtoDeEnvio.setBlnCas(Boolean.FALSE);												
											mostrarMensaje(Boolean.FALSE, "No hay socios con la modalidad CAS");
											return;
										}							
										strTipoMantValidarDeEnvio = Constante.MANTENIMIENTO_NINGUNO;
										msgMantDeEnvio.setStrValidarEnvio(null);									
																	
								}
								else
								{
									mostrarMensaje(Boolean.FALSE, "  La unidad seleccionada no tiene registrado socios " +
																  "para envío de planilla del período correspondiente "+ 
																  dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());
									return;
								}
							}
							else if(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO.
									compareTo(cierreCobranzaPlanilla.getIntParaEstadoCierre()) == 0)
							{									
								mostrarMensaje(Boolean.FALSE, " Este periodo se encuentra cerrado y restringido de procesar planillas enviadas");
								return;
							}
						}
						else 
						{
							planillaAdministraCAS = localPlanilla.getPlanillaPorIdEstructuraYTipoSocioYPeriodoCAS(pk,
									    					        											  Constante.PARAM_T_TIPOSOCIO_ACTIVO,
									    					        											  dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());
							
							planillaCAS = new ArrayList<ItemPlanilla>();
							for(PlanillaAdministra global:planillaAdministraCAS)
							 {
								log.debug("admi:"+global.getSubSucursalPK().getIntIdSucursal()+" subAdm: "+global.getSubSucursalPK().getIntIdSubSucursal()); 
								for(ItemPlanilla planillaSinAdministra: global.getListaPlanilla())
								 {									 
									 planillaCAS.add(planillaSinAdministra);
								 }
							 }
							
							if(planillaCAS != null && !planillaCAS.isEmpty())
							{								
								for(ItemPlanilla itemPlanilla: planillaCAS)
								{									
									if(itemPlanilla.getSocio().getListSocioEstructura()!=null &&
											!itemPlanilla.getSocio().getListSocioEstructura().isEmpty())
									{
										for(SocioEstructura socioEstructura: itemPlanilla.getSocio().getListSocioEstructura())
										{																					
											 if(socioEstructura.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_CAS) == 0)
											{
												intNroCas++;												
											}
										}									
									}																
								}							
								
								//Ordenamos por nombre
								Collections.sort(planillaCAS, new Comparator<ItemPlanilla>(){
									public int compare(ItemPlanilla uno, ItemPlanilla otro) {															
										return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
									}
								});							
													
								
								if(intNroCas > 0)
								{
									dtoDeEnvio.setBlnCas(Boolean.TRUE);
									datosValidados = Boolean.TRUE;
									habilitarGrabar = Boolean.TRUE;
									deshabilitarNuevo = Boolean.FALSE;			
									ocultarMensaje();
								}
								else
								{
									dtoDeEnvio.setBlnCas(Boolean.FALSE);										
									mostrarMensaje(Boolean.FALSE, "No hay socios con la modalidad CAS");
									return;
								}							
								strTipoMantValidarDeEnvio = Constante.MANTENIMIENTO_NINGUNO;
								msgMantDeEnvio.setStrValidarEnvio(null);															
							}
							else
							{
								mostrarMensaje(Boolean.FALSE, "  La unidad seleccionada no tiene registrado socios " +
															  "para envío de planilla del período correspondiente "+ 
															  dtoDeEnvio.getEnvioConcepto().getIntPeriodoplanilla());
								return;
							}
					}					
					//fin de cieerre																		
				}
				intNroHaberesOf 	= 0;
				intNroIncentivoOf	= 0;
				intNroCasOf 		= 0; 						
				intNroCasOf 		= intNroCas;							
			}
			else
			{
				mostrarMensaje(Boolean.FALSE, "La unidad seleccionada no tiene registrado socios CAS.");
				return;
			}					
		
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	private void setPeriodoPlanilla(Envioconcepto envioconcepto,
								    Integer intSaltoEnviado,
								    Integer intDiaEnviado){
		log.info("setPeriodoPlanilla INICIO V0");
		Integer intAnioActual = null;
		Integer intMesActual = null;
		Integer intDiaActual = null;
		Integer intFechaActual = null;
		Integer intFechaEnvio = null;
		String strPeriodoActual = null;
		Integer temporalEfectuado=null;
		String strPeriodoEfectuado=null;	
		
		intAnioActual = new Integer(JFecha.obtenerAnioActual());
		intMesActual = new Integer(JFecha.obtenerMesActual());
		intDiaActual = new Integer(JFecha.obtenerDiaActual());
		envioconcepto.setIntTempAnioPlanilla(intAnioActual);
		intFechaActual = new Integer(String.valueOf(intAnioActual)+
						    String.valueOf(intMesActual)+
						    String.valueOf(intDiaActual)); 
		
		if(intSaltoEnviado.compareTo(Constante.PARAM_T_FECHAENVIOCOBRO_ESTEMES)==0)
		{
			intFechaEnvio = new Integer(String.valueOf(intAnioActual)+
							String.valueOf(intMesActual)+
							String.valueOf(intDiaEnviado));			
		}else{			
			intFechaEnvio = new Integer(String.valueOf(intAnioActual)+
							String.valueOf(intMesActual+1)+
							String.valueOf(intDiaEnviado));					
		}
		if(intFechaActual.compareTo(intFechaEnvio) == -1 ||
			intFechaActual.compareTo(intFechaEnvio) == 0)
		{
			envioconcepto.setIntTempMesPlanilla(intMesActual);
			temporalEfectuado = intMesActual-1;
			log.debug("temporalEfectuado: "+temporalEfectuado);		
		}
		else if (intFechaActual.compareTo(intFechaEnvio)== 1)
		{
			
			if(intMesActual.compareTo(12) == 0)
			{
//				envioconcepto.setIntTempMesPlanilla(1);FLYALICO 05062014
				envioconcepto.setIntTempMesPlanilla(1);
				envioconcepto.setIntTempAnioPlanilla(intAnioActual+1);
			}
			else
			{
//				envioconcepto.setIntTempMesPlanilla(intMesActual+1); FLYALICO 05062014
				envioconcepto.setIntTempMesPlanilla(intMesActual+1);
			}
			temporalEfectuado = intMesActual;
			log.debug("temporalEfectuado: "+temporalEfectuado);		
		}
		strPeriodoActual = envioconcepto.getIntTempAnioPlanilla()+
							""+String.format("%02d",envioconcepto.getIntTempMesPlanilla());			
		strPeriodoEfectuado = envioconcepto.getIntTempAnioPlanilla()+
								""+String.format("%02d",temporalEfectuado);
		envioconcepto.setIntPeriodoplanilla(new Integer(strPeriodoActual));
		envioconcepto.setIntTempEfectuado(new Integer(strPeriodoEfectuado));
		log.info("strPeriodoActual = "+strPeriodoActual);
		log.info("setPeriodoPlanilla FIN V0");
	}
	
	private void setPeriodoPlanillaAlUltimoEnvio(Envioconcepto envioconcepto, Integer intMaxPeriodoEnviado){
		log.debug("setPeriodoPlanillaAlUltimoEnvio INICIO");
		String fString = Integer.toString(intMaxPeriodoEnviado);
		Integer año = Integer.parseInt(fString.substring(0, 4));		
		Integer mes = Integer.parseInt(fString.substring(4));
		if(mes.equals(12)){
			mes=1;
			año=año+1;
		}else{
			mes = mes+1;
		}
		String strPeriodoActual = null;
	
		envioconcepto.setIntTempAnioPlanilla(año);
		envioconcepto.setIntTempMesPlanilla(mes);
		dtoDeEnvio.getEnvioConcepto().setIntTempAnioPlanilla(año);
		dtoDeEnvio.getEnvioConcepto().setIntTempMesPlanilla(mes);
		strPeriodoActual = envioconcepto.getIntTempAnioPlanilla()+
							""+String.format("%02d",envioconcepto.getIntTempMesPlanilla());
		log.debug(" strPeriodo: "+strPeriodoActual);
		
		envioconcepto.setIntPeriodoplanilla(new Integer(strPeriodoActual));
		log.debug("setPeriodoPlanillaAlUltimoEnvio FIN");
	}
	
	private void setPeriodoPlanillaIgualAlmismosEnvio(Envioconcepto envioconcepto, Integer intMaxPeriodoEnviado){
		log.info("setPeriodoPlanillaIgualAlmismosEnvio INICIO");
		String fString = Integer.toString(intMaxPeriodoEnviado);
		Integer año = Integer.parseInt(fString.substring(0, 4));
		Integer mes = Integer.parseInt(fString.substring(4));
		String strPeriodoActual = null;
		envioconcepto.setIntTempAnioPlanilla(new Integer(año));		
		envioconcepto.setIntTempMesPlanilla(mes);
		strPeriodoActual = envioconcepto.getIntTempAnioPlanilla()+
							""+String.format("%02d",envioconcepto.getIntTempMesPlanilla());
		envioconcepto.setIntPeriodoplanilla(new Integer(strPeriodoActual));
		log.info("setPeriodoPlanillaIgualAlmismosEnvio INICIO");
	}
	
	public void seleccionarRegistro(ActionEvent event)
	{
		log.debug("seleccionarRegistro INICIO");
		registroSeleccionadoEnvioResumen = null;
		try{
			registroSeleccionadoEnvioResumen = (Envioresumen)event.getComponent().getAttributes().get("item");
		log.debug("seleccionarRegistro FIN");	
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	//sobre lo que he seleccionado que tiene y si lo tiene activar el boton adecuado
	//ya que todos van a tener un h y a4j
	public void ValidarOpciones()
	{
		log.debug("ValidarOpciones INICIO");		
		try{			
			ocultarMensaje();
			previoExportarEnvioresumenDBF();
			previoExportarEnvioresumenEXCEL();
			previoExportarEnvioresumenTXT();
			log.debug("ValidarOpciones FIN");	
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public void exportarTXTa4j()
	{
		log.debug("ValidarOpciones INICIO");
		
		try{
			ocultarMensaje();
			mostrarMensaje(Boolean.FALSE, mensajeTxt);			
		log.debug("ValidarOpciones FIN");	
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	public void exportarEXCELa4j()
	{
		log.debug("ValidarOpciones INICIO");
		
		try{
			ocultarMensaje();
			mostrarMensaje(Boolean.FALSE, mensajeExcel);			
		log.debug("ValidarOpciones FIN");	
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	public void exportarDBFa4j()
	{
		log.debug("ValidarOpciones INICIO");
		
		try{
			ocultarMensaje();
			mostrarMensaje(Boolean.FALSE, mensajeDBF);
			//return;			
		log.debug("ValidarOpciones FIN");	
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	//------------------------  INICIO EXPORTAR  -------------------------------------------------------------------
	
	public void previoExportarEnvioresumenDBF() 
	{
		log.debug("ExportarEnvioresumenDBF INICIO V1");
		
		Estructura estructura = null;
		EstructuraId eId = null;	
		List<ConfEstructura> listaConfEst = null;			
		Configuracion configuracion = null;		
		ocultarMensaje();
		blnMostrarDBFa4j   = Boolean.FALSE;
	    blnMostrarDBFh	   = Boolean.FALSE;
		try{
			ArchivoRiesgoFacadeRemote archivoRemote = (ArchivoRiesgoFacadeRemote)EJBFactory.getRemote(ArchivoRiesgoFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
						
			listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
																registroSeleccionadoEnvioResumen.getIntTiposocioCod(),
																registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
																registroSeleccionadoEnvioResumen.getIntNivel(), 
																registroSeleccionadoEnvioResumen.getIntCodigo(),
																Constante.PARAM_T_FORMATOARCHIVOS_DBF);
			
			if(listaConfEst != null && !listaConfEst.isEmpty())
			{
				log.debug("confEst: "+listaConfEst.size());
				for(ConfEstructura a: listaConfEst)
				{
					log.debug("confEst: "+a.getId().getIntItemConfiguracion());
					ConfiguracionId confId = new ConfiguracionId();
					confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
					confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
					configuracion = archivoRemote.getConfiguracionPorPk(confId);
					if(configuracion != null)
					{
						log.debug("configuracion: "+configuracion);
						
						if(Constante.PARAM_T_FORMATOARCHIVOS_DBF.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
						{
							log.debug("es del mismo tipo seleccionado en este caso dbf");
							List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaConfDetalle != null)
							{
								log.debug("tiene lista detalle");
								
								Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
									public int compare(ConfDetalle uno, ConfDetalle otro) {												
										return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
									}
								});
								
								configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
								configuracion.setListaConfDetalle(listaConfDetalle);	
								configuracion.setConfEstructura(new ConfEstructura());
								configuracion.setConfEstructura(a);
							}
							List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaNombre != null && !listaNombre.isEmpty())
							{								
								configuracion.setListaNombre(new ArrayList<Nombre>());
								configuracion.setListaNombre(listaNombre);
							}
						}
						else
						{
							log.debug("NO es del mismo tipo seleccionado en este caso dbf");
							blnMostrarDBFa4j   = Boolean.TRUE;
							mensajeDBF = "NO es del mismo tipo seleccionado en este caso dbf";
							return;
						}						
					}
					break;
				}
				blnMostrarDBFh = Boolean.TRUE;
			}
			else
			{			
				eId = new EstructuraId();				
				eId.setIntNivel(registroSeleccionadoEnvioResumen.getIntNivel());
				eId.setIntCodigo(registroSeleccionadoEnvioResumen.getIntCodigo());
				estructura = remoteEstructura.getEstructuraPorPK(eId);
				if(estructura != null)
				{
					log.debug("estructura es distinto que null");
					
					if(estructura.getIntNivelRel() != null && estructura.getIntIdCodigoRel() != null)
					{
						listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
																			registroSeleccionadoEnvioResumen.getIntTiposocioCod(), 
																			registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
																			estructura.getIntNivelRel(), estructura.getIntIdCodigoRel(),
																			Constante.PARAM_T_FORMATOARCHIVOS_DBF);
						if(listaConfEst != null && !listaConfEst.isEmpty())
						{
							log.debug("confEst: "+listaConfEst.size());
														
							
							for(ConfEstructura a: listaConfEst)
							{
								log.debug("confEst: "+a.getId().getIntItemConfiguracion());
								ConfiguracionId confId = new ConfiguracionId();
								confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
								confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
								configuracion = archivoRemote.getConfiguracionPorPk(confId);
								if(configuracion != null)
								{
									log.debug("configuracion: "+configuracion);
									if(Constante.PARAM_T_FORMATOARCHIVOS_DBF.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
									{
										log.debug("es del mismo tipo seleccionado en este caso DBF");
										List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaConfDetalle != null)
										{
											log.debug("tiene lista detalle");
											
											Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
												public int compare(ConfDetalle uno, ConfDetalle otro) {												
													return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
												}
											});
											
											configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
											configuracion.setListaConfDetalle(listaConfDetalle);	
											configuracion.setConfEstructura(new ConfEstructura());
											configuracion.setConfEstructura(a);
										}
										List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaNombre != null && !listaNombre.isEmpty())
										{												
											configuracion.setListaNombre(new ArrayList<Nombre>());
											configuracion.setListaNombre(listaNombre);
										}
									}
									else
									{
										log.debug("NO es del mismo tipo seleccionado en este caso DBF");
										blnMostrarDBFa4j   = Boolean.TRUE;
										mensajeDBF = "NO es del mismo tipo seleccionado en este caso dbf";
										return;
									}									
								}else
								{
									blnMostrarDBFa4j   = Boolean.TRUE;
									mensajeDBF = "Revisar configuracion.";
									return;
								}
								break;
							}
						}else
						{
							blnMostrarDBFa4j   = Boolean.TRUE;
							mensajeDBF = "Revisar configuracion.";
							log.debug("null");
							return;
						}
					}					
					else
					{
						blnMostrarDBFa4j   = Boolean.TRUE;
						mensajeDBF = "No tiene codigo ni nivel relacional";
						log.debug("No tiene codigo ni nivel relacional");
						return;
					}
				}
				blnMostrarDBFh = Boolean.TRUE;
			} 		
			
		log.debug("exportarEnvioresumenTXT FIN v1");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}		
	}
	
	public void exportarEnvioresumenDBF() 
	{
		log.debug("ExportarEnvioresumenDBF INICIO V1");
		
		Envioresumen envioresumen = registroSeleccionadoEnvioResumen;		
		List<Enviomonto> listamontos = null;
		Estructura estructura = null;
		EstructuraId eId = null;	
		List<ConfEstructura> listaConfEst = null;
		String filename = null;			
		Configuracion configuracion = null;		
		ocultarMensaje();		
		try{
			ArchivoRiesgoFacadeRemote archivoRemote = (ArchivoRiesgoFacadeRemote)EJBFactory.getRemote(ArchivoRiesgoFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			SocioFacadeRemote remoteSocio 			= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			CuentaFacadeRemote remoteCuenta 		= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);			
			EnviomontoBO boEnviomonto				= (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
			EnvioconceptoBO	boEnvioConcepto         = (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);			
			ContactoFacadeRemote remoteContacto		= (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			
			listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
																registroSeleccionadoEnvioResumen.getIntTiposocioCod(),
																registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
																registroSeleccionadoEnvioResumen.getIntNivel(), 
																registroSeleccionadoEnvioResumen.getIntCodigo(),
																Constante.PARAM_T_FORMATOARCHIVOS_DBF);
			
			if(listaConfEst != null && !listaConfEst.isEmpty())
			{
				log.debug("confEst: "+listaConfEst.size());
				for(ConfEstructura a: listaConfEst)
				{
					log.debug("confEst: "+a.getId().getIntItemConfiguracion());
					ConfiguracionId confId = new ConfiguracionId();
					confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
					confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
					configuracion = archivoRemote.getConfiguracionPorPk(confId);
					if(configuracion != null)
					{
						log.debug("configuracion: "+configuracion);
						
						if(Constante.PARAM_T_FORMATOARCHIVOS_DBF.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
						{
							log.debug("es del mismo tipo seleccionado en este caso dbf");
							List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaConfDetalle != null)
							{
								
								Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
									public int compare(ConfDetalle uno, ConfDetalle otro) {												
										return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
									}
								});
								
								configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
								configuracion.setListaConfDetalle(listaConfDetalle);	
								configuracion.setConfEstructura(new ConfEstructura());
								configuracion.setConfEstructura(a);
							}
							List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaNombre != null && !listaNombre.isEmpty())
							{								
								configuracion.setListaNombre(new ArrayList<Nombre>());
								configuracion.setListaNombre(listaNombre);
							}
						}
												
					}
					break;
				}
			}
			else
			{			
				eId = new EstructuraId();				
				eId.setIntNivel(registroSeleccionadoEnvioResumen.getIntNivel());
				eId.setIntCodigo(registroSeleccionadoEnvioResumen.getIntCodigo());
				estructura = remoteEstructura.getEstructuraPorPK(eId);
				if(estructura != null)
				{
					if(estructura.getIntNivelRel() != null && estructura.getIntIdCodigoRel() != null)
					{
						listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
																			registroSeleccionadoEnvioResumen.getIntTiposocioCod(), 
																			registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
																			estructura.getIntNivelRel(), estructura.getIntIdCodigoRel(),
																			Constante.PARAM_T_FORMATOARCHIVOS_DBF);
						if(listaConfEst != null && !listaConfEst.isEmpty())
						{
							for(ConfEstructura a: listaConfEst)
							{
								log.debug("confEst: "+a.getId().getIntItemConfiguracion());
								ConfiguracionId confId = new ConfiguracionId();
								confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
								confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
								configuracion = archivoRemote.getConfiguracionPorPk(confId);
								if(configuracion != null)
								{
									log.debug("configuracion: "+configuracion);
									if(Constante.PARAM_T_FORMATOARCHIVOS_DBF.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
									{
										log.debug("es del mismo tipo seleccionado en este caso DBF");
										List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaConfDetalle != null)
										{
											Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
												public int compare(ConfDetalle uno, ConfDetalle otro) {												
													return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
												}
											});
											
											configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
											configuracion.setListaConfDetalle(listaConfDetalle);	
											configuracion.setConfEstructura(new ConfEstructura());
											configuracion.setConfEstructura(a);
										}
										List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaNombre != null && !listaNombre.isEmpty())
										{												
											configuracion.setListaNombre(new ArrayList<Nombre>());
											configuracion.setListaNombre(listaNombre);
										}
									}									
								}
								break;
							}
						}
					}
				}
			}  
	       
	         ///DATA ENVIO	         
	        listamontos = boEnviomonto.getListaEnviomontoXItemEnvioresumen(envioresumen.getId());
			if(listamontos!=null && !listamontos.isEmpty())
			{
				for(Enviomonto monto:listamontos)
				{						
					monto.setEnvioresumen(envioresumen);
					List<Envioconcepto> listaconceptos = null;
					EnvioconceptoId pId = new  EnvioconceptoId();
					pId.setIntEmpresacuentaPk(monto.getId().getIntEmpresacuentaPk());
					pId.setIntItemenvioconcepto(monto.getId().getIntItemenvioconcepto());
					listaconceptos = boEnvioConcepto.getEnvioconceptoPorItemEnvioConcepto(pId);
					if(listaconceptos != null && !listaconceptos.isEmpty())
					{
						monto.setListaEnvioConcepto(new ArrayList<Envioconcepto>());
						monto.setListaEnvioConcepto(listaconceptos);
						for(Envioconcepto encon:listaconceptos)
						{							
							CuentaId cuentaId = new CuentaId();
							cuentaId.setIntPersEmpresaPk(encon.getId().getIntEmpresacuentaPk());
							cuentaId.setIntCuenta(encon.getIntCuentaPk());
							List<CuentaIntegrante> listaCI = remoteCuenta.getListaCuentaIntegrantePorPKCuenta(cuentaId);
							if(listaCI != null && !listaCI.isEmpty())
							{
								SocioPK socioPK = new SocioPK();
								socioPK.setIntIdEmpresa(encon.getId().getIntEmpresacuentaPk());
								socioPK.setIntIdPersona(listaCI.get(0).getId().getIntPersonaIntegrante());
								Socio socio = remoteSocio.getSocioPorPK(socioPK);
								if(socio != null)
								{
									monto.setSocio(socio);
									Integer intPersona = socio.getId().getIntIdPersona();
									Documento documento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(intPersona, Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI));
									if(documento != null)
									{ 										
										monto.setDocumento(documento); 
									}									
								}
							}
							
						}
					}					
				}
			} 
			
			for(Nombre nombr:configuracion.getListaNombre())
			{
				if(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO.compareTo(nombr.getIntParaTipoValorCod()) == 0)
				{
					if(filename == null)
					{
						filename="";
					}
					filename =  new StringBuilder(filename).append(nombr.getStrValorFijo()).toString();
				}else if(Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE.compareTo(nombr.getIntParaTipoValorCod()) == 0)
				{
					if(filename == null)
					{
						filename="";
					}
					filename = new StringBuilder(filename).append(iendoParametro(nombr.getIntParaTipoVariableCod(),
																  listamontos.get(0),
																  null) ).toString();
				}
			}			
						
			filename = new StringBuilder(filename).append(".DBF").toString();
			log.debug("filenameFINAL: "+filename);	
			FacesContext fc = FacesContext.getCurrentInstance();
		    HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		    log.debug("fc response");
		    response.reset();
		    response.setContentType("text/plain");
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		    log.debug("setHeader");
		   
			DBFWriter writer = new DBFWriter();

			
			DBFField []fields = new DBFField[configuracion.getListaConfDetalle().size()];
			log.debug("configuracion.getListaConfDetalle().size():"+configuracion.getListaConfDetalle().size());
			for(int intStart=0; intStart < configuracion.getListaConfDetalle().size(); intStart++)
			{
				ConfDetalle confDetalle = configuracion.getListaConfDetalle().get(intStart);
				
				fields[intStart] = new DBFField();
				fields[intStart].setName(confDetalle.getStrValorCampo());
				fields[intStart].setDataType(DBFField.FIELD_TYPE_C);
				fields[intStart].setFieldLength(confDetalle.getIntTamano());				
			}
			writer.setFields( fields);
			
			for(Enviomonto env: listamontos)
			{
				log.debug("env: "+env);
				int a=0;
				Object []record = null;
				for(ConfDetalle confDetalle0: configuracion.getListaConfDetalle())
				{					
					if(a == 0)
					{
						record = new Object[configuracion.getListaConfDetalle().size()];
						if(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO.compareTo(confDetalle0.getIntParaTipoValorCod()) == 0)
						{
							record[a] = confDetalle0.getStrDatoFijo().toString();
							log.debug("Frecord["+a+"]="+confDetalle0.getStrDatoFijo().toString());
						}
						else if(Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE.compareTo(confDetalle0.getIntParaTipoValorCod()) == 0)
						{
							record[a] = iendoParametro(confDetalle0.getIntParaTipoDatoVariableCod(),env, confDetalle0);
							log.debug("Vrecord["+a+"]=");	
						}
						a++;
					}
					else if(a>0 && a<configuracion.getListaConfDetalle().size())
					{
						if(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO.compareTo(confDetalle0.getIntParaTipoValorCod()) == 0)
						{
							record[a] = confDetalle0.getStrDatoFijo().toString();
							log.debug("Frecord["+a+"]="+confDetalle0.getStrDatoFijo().toString());
						}
						else if(Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE.compareTo(confDetalle0.getIntParaTipoValorCod()) == 0)
						{
							record[a] = iendoParametro(confDetalle0.getIntParaTipoDatoVariableCod(),env, confDetalle0);
							log.debug("Vrecord["+a+"]=");	
						}
						a++;
					}					
				}	
				writer.addRecord( record);				
			}			
			OutputStream output = response.getOutputStream();
			writer.write(output);
			output.flush();
			output.close();
			fc.responseComplete();
		log.debug("exportarEnvioresumenTXT FIN v1");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}		
	}
	
	public void  previoExportarEnvioresumenTXT()
	{
		log.debug("exportarEnvioresumenTXT INICIO");
		
		Estructura estructura = null;
		EstructuraId eId = null;		
		List<ConfEstructura> listaConfEst = null;		
		Configuracion configuracion = null;	       
        blnMostrarTxta4j   = Boolean.FALSE;        
        blnMostrarTxth	   = Boolean.FALSE;
        mensajeTxt 			= null;
        ocultarMensaje();
        try{
			ArchivoRiesgoFacadeRemote archivoRemote = (ArchivoRiesgoFacadeRemote)EJBFactory.getRemote(ArchivoRiesgoFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
						
			listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
																registroSeleccionadoEnvioResumen.getIntTiposocioCod(),
																registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
																registroSeleccionadoEnvioResumen.getIntNivel(), 
																registroSeleccionadoEnvioResumen.getIntCodigo(),
																Constante.PARAM_T_FORMATOARCHIVOS_TEXTO);			
			
			if(listaConfEst != null && !listaConfEst.isEmpty())
			{
				log.debug("confEst: "+listaConfEst.size());
				for(ConfEstructura a: listaConfEst)
				{
					log.debug("confEst: "+a.getId().getIntItemConfiguracion());
					ConfiguracionId confId = new ConfiguracionId();
					confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
					confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
					configuracion = archivoRemote.getConfiguracionPorPk(confId);
					if(configuracion != null)
					{
						log.debug("configuracion: "+configuracion);
						
						if(Constante.PARAM_T_FORMATOARCHIVOS_TEXTO.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
						{
							log.debug("es del mismo tipo seleccionado en este caso texto");
							List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaConfDetalle != null)
							{
								log.debug("tiene lista detalle");
								
								Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
									public int compare(ConfDetalle uno, ConfDetalle otro) {												
										return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
									}
								});
								
								configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
								configuracion.setListaConfDetalle(listaConfDetalle);	
								configuracion.setConfEstructura(new ConfEstructura());
								configuracion.setConfEstructura(a);
							}
							List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaNombre != null && !listaNombre.isEmpty())
							{								
								configuracion.setListaNombre(new ArrayList<Nombre>());
								configuracion.setListaNombre(listaNombre);
							}
						}
						else
						{
							log.debug("NO es del mismo tipo seleccionado en este caso texto");							
							blnMostrarTxta4j   = Boolean.TRUE;
							mensajeTxt ="NO es del mismo tipo seleccionado en este caso texto";	
							return;
						}						
					}
					break;
				}				
				blnMostrarTxth = Boolean.TRUE;
			}
			else
			{			
				eId = new EstructuraId();
				
				eId.setIntNivel(registroSeleccionadoEnvioResumen.getIntNivel());
				eId.setIntCodigo(registroSeleccionadoEnvioResumen.getIntCodigo());
				estructura = remoteEstructura.getEstructuraPorPK(eId);
				if(estructura != null )
				{
					log.debug("estructura es distinto que null");
					
					if(estructura.getIntNivelRel() != null && estructura.getIntIdCodigoRel() != null)
					{						
						log.debug("Tiene codigo Y nivel relacional");
						listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
																		registroSeleccionadoEnvioResumen.getIntTiposocioCod(), 
																		registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
																		estructura.getIntNivelRel(), estructura.getIntIdCodigoRel(),
																		Constante.PARAM_T_FORMATOARCHIVOS_TEXTO);
						if(listaConfEst != null && !listaConfEst.isEmpty())
						{
							log.debug("confEst: "+listaConfEst.size());
							for(ConfEstructura a: listaConfEst)
							{
								log.debug("confEst: "+a.getId().getIntItemConfiguracion());
								ConfiguracionId confId = new ConfiguracionId();
								confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
								confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
								configuracion = archivoRemote.getConfiguracionPorPk(confId);
								if(configuracion != null)
								{
									log.debug("configuracion: "+configuracion);
									if(Constante.PARAM_T_FORMATOARCHIVOS_TEXTO.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
									{
										log.debug("es del mismo tipo seleccionado en este caso texto");
										List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaConfDetalle != null)
										{
											log.debug("tiene lista detalle");
											
											Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
												public int compare(ConfDetalle uno, ConfDetalle otro) {												
													return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
												}
											});
											
											configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
											configuracion.setListaConfDetalle(listaConfDetalle);	
											configuracion.setConfEstructura(new ConfEstructura());
											configuracion.setConfEstructura(a);
										}
										List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaNombre != null && !listaNombre.isEmpty())
										{												
											configuracion.setListaNombre(new ArrayList<Nombre>());
											configuracion.setListaNombre(listaNombre);
										}
									}
									else
									{
										log.debug("NO es del mismo tipo seleccionado en este caso texto");
										blnMostrarTxta4j   = Boolean.TRUE;
										mensajeTxt ="NO es del mismo tipo seleccionado en este caso texto";	
										return;
									}									
								}else
								{									
									blnMostrarTxta4j   = Boolean.TRUE;
									mensajeTxt ="No tiene configuracion.";	
									return;
								}
								break;
							}
						}else
						{
							log.debug("No tiene configuracion.");														
							blnMostrarTxta4j   = Boolean.TRUE;
							mensajeTxt ="No tiene configuracion.";
							return;
						}
					}					
					else
					{
						log.debug("no tiene codigo ni nivel relacional");
						blnMostrarTxta4j   = Boolean.TRUE;
						mensajeTxt ="no tiene codigo ni nivel relacional";
						return;
					}
				}
				blnMostrarTxth = Boolean.TRUE;	
			}			
					
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		
	}
	
	public String  exportarEnvioresumenTXT()
	{
		log.debug("exportarEnvioresumenTXT INICIO");
		
		Envioresumen envioresumen = registroSeleccionadoEnvioResumen;		
		List<Enviomonto> listamontos = null;
		Estructura estructura = null;
		EstructuraId eId = null;		
		List<ConfEstructura> listaConfEst = null;
		String filename = null;		
		Configuracion configuracion = null;		
        List<String> strings = new ArrayList<String>();       
        ocultarMensaje();
        try{
			ArchivoRiesgoFacadeRemote archivoRemote = (ArchivoRiesgoFacadeRemote)EJBFactory.getRemote(ArchivoRiesgoFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			SocioFacadeRemote remoteSocio 			= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			CuentaFacadeRemote remoteCuenta 		= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);			
			EnviomontoBO boEnviomonto				= (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
			EnvioconceptoBO	boEnvioConcepto         = (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);			
			ContactoFacadeRemote remoteContacto		= (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
						
			listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
															registroSeleccionadoEnvioResumen.getIntTiposocioCod(),
															registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
															registroSeleccionadoEnvioResumen.getIntNivel(), 
															registroSeleccionadoEnvioResumen.getIntCodigo(),
															Constante.PARAM_T_FORMATOARCHIVOS_TEXTO);			
			
			if(listaConfEst != null && !listaConfEst.isEmpty())
			{
				log.debug("confEst: "+listaConfEst.size());
				for(ConfEstructura a: listaConfEst)
				{
					log.debug("confEst: "+a.getId().getIntItemConfiguracion());
					ConfiguracionId confId = new ConfiguracionId();
					confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
					confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
					configuracion = archivoRemote.getConfiguracionPorPk(confId);
					if(configuracion != null)
					{
						log.debug("configuracion: "+configuracion);
						
						if(Constante.PARAM_T_FORMATOARCHIVOS_TEXTO.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
						{
							log.debug("es del mismo tipo seleccionado en este caso texto");
							List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaConfDetalle != null)
							{
								log.debug("tiene lista detalle");
								
								Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
									public int compare(ConfDetalle uno, ConfDetalle otro) {												
										return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
									}
								});
								
								configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
								configuracion.setListaConfDetalle(listaConfDetalle);	
								configuracion.setConfEstructura(new ConfEstructura());
								configuracion.setConfEstructura(a);
							}
							List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaNombre != null && !listaNombre.isEmpty())
							{								
								configuracion.setListaNombre(new ArrayList<Nombre>());
								configuracion.setListaNombre(listaNombre);
							}
						}
						else
						{
							log.debug("NO es del mismo tipo seleccionado en este caso texto");												
						}						
					}
					break;
				}
			}
			else
			{			
				eId = new EstructuraId();
				
				eId.setIntNivel(registroSeleccionadoEnvioResumen.getIntNivel());
				eId.setIntCodigo(registroSeleccionadoEnvioResumen.getIntCodigo());
				estructura = remoteEstructura.getEstructuraPorPK(eId);
				if(estructura != null )
				{
					log.debug("estructura es distinto que null");
					
					if(estructura.getIntNivelRel() != null && estructura.getIntIdCodigoRel() != null)
					{						
						log.debug("Tiene codigo Y nivel relacional");
						listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
																		registroSeleccionadoEnvioResumen.getIntTiposocioCod(), 
																		registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
																		estructura.getIntNivelRel(), estructura.getIntIdCodigoRel(),
																		Constante.PARAM_T_FORMATOARCHIVOS_TEXTO);
						if(listaConfEst != null && !listaConfEst.isEmpty())
						{
							log.debug("confEst: "+listaConfEst.size());
							for(ConfEstructura a: listaConfEst)
							{
								log.debug("confEst: "+a.getId().getIntItemConfiguracion());
								ConfiguracionId confId = new ConfiguracionId();
								confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
								confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
								configuracion = archivoRemote.getConfiguracionPorPk(confId);
								if(configuracion != null)
								{
									log.debug("configuracion: "+configuracion);
									if(Constante.PARAM_T_FORMATOARCHIVOS_TEXTO.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
									{
										log.debug("es del mismo tipo seleccionado en este caso texto");
										List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaConfDetalle != null)
										{
											log.debug("tiene lista detalle");
											
											Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
												public int compare(ConfDetalle uno, ConfDetalle otro) {												
													return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
												}
											});
											
											configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
											configuracion.setListaConfDetalle(listaConfDetalle);	
											configuracion.setConfEstructura(new ConfEstructura());
											configuracion.setConfEstructura(a);
										}
										List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaNombre != null && !listaNombre.isEmpty())
										{												
											configuracion.setListaNombre(new ArrayList<Nombre>());
											configuracion.setListaNombre(listaNombre);
										}
									}																	
								}
								break;
							}
						}
					}
				}
			}	        
	        
	        listamontos = boEnviomonto.getListaEnviomontoXItemEnvioresumen(envioresumen.getId());
			if(listamontos!=null && !listamontos.isEmpty())
			{
				for(Enviomonto monto:listamontos)
				{	
					String string = new String();
					monto.setEnvioresumen(envioresumen);
					List<Envioconcepto> listaconceptos = null;
					EnvioconceptoId pId = new  EnvioconceptoId();
					pId.setIntEmpresacuentaPk(monto.getId().getIntEmpresacuentaPk());
					pId.setIntItemenvioconcepto(monto.getId().getIntItemenvioconcepto());
					listaconceptos = boEnvioConcepto.getEnvioconceptoPorItemEnvioConcepto(pId);
					if(listaconceptos != null && !listaconceptos.isEmpty())
					{
						monto.setListaEnvioConcepto(new ArrayList<Envioconcepto>());
						monto.setListaEnvioConcepto(listaconceptos);
						for(Envioconcepto encon:listaconceptos)
						{							
							CuentaId cuentaId = new CuentaId();
							cuentaId.setIntPersEmpresaPk(encon.getId().getIntEmpresacuentaPk());
							cuentaId.setIntCuenta(encon.getIntCuentaPk());
							List<CuentaIntegrante> listaCI = remoteCuenta.getListaCuentaIntegrantePorPKCuenta(cuentaId);
							if(listaCI != null && !listaCI.isEmpty())
							{
								SocioPK socioPK = new SocioPK();
								socioPK.setIntIdEmpresa(encon.getId().getIntEmpresacuentaPk());
								socioPK.setIntIdPersona(listaCI.get(0).getId().getIntPersonaIntegrante());
								Socio socio = remoteSocio.getSocioPorPK(socioPK);
								if(socio != null)
								{
									monto.setSocio(socio);
									Integer intPersona = socio.getId().getIntIdPersona();
									Documento documento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(intPersona,
																											    Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI));
									if(documento != null)
									{										
										monto.setDocumento(documento); 
									}									
								}
							}
							
						}
					}
					if(configuracion.getListaConfDetalle() != null && !configuracion.getListaConfDetalle().isEmpty())
					{
						for(ConfDetalle confDetalle:configuracion.getListaConfDetalle())
						 {						
							if(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO.compareTo(confDetalle.getIntParaTipoValorCod()) == 0)
							{							
								if(string==null)
								{string="";}
								string =  new StringBuilder(string).append(confDetalle.getStrDatoFijo()).toString() ;
							}else if(Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE.compareTo(confDetalle.getIntParaTipoValorCod()) == 0)
							{							
								if(string==null)
								{string="";	}
								string = new StringBuilder(string).append(iendoParametro(confDetalle.getIntParaTipoDatoVariableCod(),monto, confDetalle)).toString();
							}
							
						 }
						 string = new StringBuilder(string).append("\r\n").toString();
						 strings.add(string);
					}					 
				}
			} 
			
			for(Nombre nombr:configuracion.getListaNombre())
			{
				if(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO.compareTo(nombr.getIntParaTipoValorCod()) == 0)
				{
					filename =  new StringBuilder(nombr.getStrValorFijo()).toString();
				}else if(Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE.compareTo(nombr.getIntParaTipoValorCod()) == 0)
				{
					filename = new StringBuilder(filename).append(iendoParametro(nombr.getIntParaTipoVariableCod(),
																  listamontos.get(0),
																  null) ).toString();
				}
			}			
			
			filename = new StringBuilder(filename).append(".txt").toString();			
			log.debug("filenameFINAL: "+filename);			
	        FacesContext fc = FacesContext.getCurrentInstance();
	        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	        log.debug("fc response");
	        response.reset();
	        response.setContentType("text/plain");
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	        log.debug("setHeader");
	        OutputStream output = response.getOutputStream();	        
	        for (String s : strings) {
	            output.write(s.getBytes());
	            log.debug("out");
	        }
	        output.flush();
	        log.debug("flush");
	        output.close();	 	
	        log.debug("close");
	        fc.responseComplete();
					
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		 return strMsgDeEnvio; 
	}
	
public void previoExportarEnvioresumenEXCEL() throws IOException {
		
		log.debug("exportarEnvioresumenEXCEL INICIO");	
				
		Estructura estructura = null;
		EstructuraId eId = null;		
		List<ConfEstructura> listaConfEst = null;		
		ocultarMensaje(); 
		Configuracion configuracion = null;
		 blnMostrarExcela4j   = Boolean.FALSE;
	     blnMostrarExcelh	   = Boolean.FALSE;
		try
		{
			ArchivoRiesgoFacadeRemote archivoRemote = (ArchivoRiesgoFacadeRemote)EJBFactory.getRemote(ArchivoRiesgoFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
									
			listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
																registroSeleccionadoEnvioResumen.getIntTiposocioCod(),
																registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
																registroSeleccionadoEnvioResumen.getIntNivel(), 
																registroSeleccionadoEnvioResumen.getIntCodigo(),
																Constante.PARAM_T_FORMATOARCHIVOS_EXCEL);	
			
			if(listaConfEst != null && !listaConfEst.isEmpty())
			{
				log.debug("confEst: "+listaConfEst.size());
				for(ConfEstructura a: listaConfEst)
				{
					log.debug("confEst: "+a.getId().getIntItemConfiguracion());
					ConfiguracionId confId = new ConfiguracionId();
					confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
					confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
					configuracion = archivoRemote.getConfiguracionPorPk(confId);
					if(configuracion != null)
					{
						log.debug("configuracion: "+configuracion);
						
						if(Constante.PARAM_T_FORMATOARCHIVOS_EXCEL.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
						{
							log.debug("es del mismo tipo seleccionado en este caso excel");
							List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaConfDetalle != null)
							{
								log.debug("tiene lista detalle");
								
								Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
									public int compare(ConfDetalle uno, ConfDetalle otro) {												
										return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
									}
								});
								
								configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
								configuracion.setListaConfDetalle(listaConfDetalle);	
								configuracion.setConfEstructura(new ConfEstructura());
								configuracion.setConfEstructura(a);								
							}
							List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaNombre != null && !listaNombre.isEmpty())
							{								
								configuracion.setListaNombre(new ArrayList<Nombre>());
								configuracion.setListaNombre(listaNombre);
							}
						}
						else
						{
							log.debug("NO es del mismo tipo seleccionado en este caso excel");
							blnMostrarExcela4j   = Boolean.TRUE;
							mensajeExcel ="Revisar configuracion.";
							return;
						}
					}
					break;
				}
				  blnMostrarExcelh  = Boolean.TRUE;
			}
			else
			{			
				eId = new EstructuraId();				
				eId.setIntNivel(registroSeleccionadoEnvioResumen.getIntNivel());
				eId.setIntCodigo(registroSeleccionadoEnvioResumen.getIntCodigo());
				estructura = remoteEstructura.getEstructuraPorPK(eId);
				if(estructura != null )
				{
					log.debug("estructura es distinto que null");					
					if(estructura.getIntNivelRel() != null && estructura.getIntIdCodigoRel() != null)
					{
						listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
																			registroSeleccionadoEnvioResumen.getIntTiposocioCod(), 
																			registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
																			estructura.getIntNivelRel(),
																			estructura.getIntIdCodigoRel(),
																			Constante.PARAM_T_FORMATOARCHIVOS_EXCEL);
						if(listaConfEst != null && !listaConfEst.isEmpty())
						{
							log.debug("confEst: "+listaConfEst.size());
							for(ConfEstructura a: listaConfEst)
							{
								log.debug("confEst: "+a.getId().getIntItemConfiguracion());
								ConfiguracionId confId = new ConfiguracionId();
								confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
								confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
								configuracion = archivoRemote.getConfiguracionPorPk(confId);
								if(configuracion != null)
								{
									log.debug("configuracion: "+configuracion);
									
									if(Constante.PARAM_T_FORMATOARCHIVOS_EXCEL.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
									{
										log.debug("es del mismo tipo seleccionado en este caso excel");
										List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaConfDetalle != null)
										{
											log.debug("tiene lista detalle");
											
											Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
												public int compare(ConfDetalle uno, ConfDetalle otro) {												
													return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
												}
											});
											
											configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
											configuracion.setListaConfDetalle(listaConfDetalle);	
											configuracion.setConfEstructura(new ConfEstructura());
											configuracion.setConfEstructura(a);//											
										}
										List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaNombre != null && !listaNombre.isEmpty())
										{
												
											configuracion.setListaNombre(new ArrayList<Nombre>());
											configuracion.setListaNombre(listaNombre);
										}
									}
									else
									{
										log.debug("NO es del mismo tipo seleccionado en este caso excel");
										blnMostrarExcela4j   = Boolean.TRUE;
										mensajeExcel ="Revisar configuracion.";
										return;
									}
								}
								else
								{
									blnMostrarExcela4j   = Boolean.TRUE;
									mensajeExcel ="Revisar configuracion.";
									return;
								}
								break;
							}
						}else
						{
							log.debug("null recontra no encuentra nada");
							blnMostrarExcela4j   = Boolean.TRUE;
							mensajeExcel ="Revisar configuracion.";
							return;
						}
					}					
					else
					{
						log.debug("no tiene codigo ni nivel relacional");
						 blnMostrarExcela4j   = Boolean.TRUE;
						 mensajeExcel ="no tiene codigo ni nivel relacional";
						 return;
					}
				}
				  blnMostrarExcelh  = Boolean.TRUE;
			}					  
			log.debug("exportarEnvioresumenEXCEL FIN");	
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}  
	}
	
	public void exportarEnvioresumenEXCEL() throws IOException {
		
		log.debug("exportarEnvioresumenEXCEL INICIO");	
		
		Envioresumen envioresumen = registroSeleccionadoEnvioResumen;		
		List<Enviomonto> listamontos = null;
		Estructura estructura = null;
		EstructuraId eId = null;		
		List<ConfEstructura> listaConfEst = null;
		String filename = null;
		ocultarMensaje(); 
		Configuracion configuracion = null;		
		try
		{
			ArchivoRiesgoFacadeRemote archivoRemote = (ArchivoRiesgoFacadeRemote)EJBFactory.getRemote(ArchivoRiesgoFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			SocioFacadeRemote remoteSocio 			= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			CuentaFacadeRemote remoteCuenta 		= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);			
			EnviomontoBO boEnviomonto				= (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
			EnvioconceptoBO	boEnvioConcepto         = (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);			
			ContactoFacadeRemote remoteContacto		= (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
						
			listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
																registroSeleccionadoEnvioResumen.getIntTiposocioCod(),
																registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
																registroSeleccionadoEnvioResumen.getIntNivel(), 
																registroSeleccionadoEnvioResumen.getIntCodigo(),
																Constante.PARAM_T_FORMATOARCHIVOS_EXCEL);	
			
			if(listaConfEst != null && !listaConfEst.isEmpty())
			{
				log.debug("confEst: "+listaConfEst.size());
				for(ConfEstructura a: listaConfEst)
				{
					log.debug("confEst: "+a.getId().getIntItemConfiguracion());
					ConfiguracionId confId = new ConfiguracionId();
					confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
					confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
					configuracion = archivoRemote.getConfiguracionPorPk(confId);
					if(configuracion != null)
					{
						log.debug("configuracion: "+configuracion);
						
						if(Constante.PARAM_T_FORMATOARCHIVOS_EXCEL.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
						{
							log.debug("es del mismo tipo seleccionado en este caso excel");
							List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaConfDetalle != null)
							{
								Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
									public int compare(ConfDetalle uno, ConfDetalle otro) {												
										return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
									}
								});
								
								configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
								configuracion.setListaConfDetalle(listaConfDetalle);	
								configuracion.setConfEstructura(new ConfEstructura());
								configuracion.setConfEstructura(a);								
							}
							List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
							if(listaNombre != null && !listaNombre.isEmpty())
							{	
								configuracion.setListaNombre(new ArrayList<Nombre>());
								configuracion.setListaNombre(listaNombre);
							}
						}
					}
					break;
				}
			}
			else
			{			
				eId = new EstructuraId();				
				eId.setIntNivel(registroSeleccionadoEnvioResumen.getIntNivel());
				eId.setIntCodigo(registroSeleccionadoEnvioResumen.getIntCodigo());
				estructura = remoteEstructura.getEstructuraPorPK(eId);
				if(estructura != null )
				{
					log.debug("estructura es distinto que null");					
					if(estructura.getIntNivelRel() != null && estructura.getIntIdCodigoRel() != null)
					{
						listaConfEst = archivoRemote.getListaModTipoSoEmp(registroSeleccionadoEnvioResumen.getIntModalidadCod(),
													registroSeleccionadoEnvioResumen.getIntTiposocioCod(), 
													registroSeleccionadoEnvioResumen.getId().getIntEmpresaPk(),
													estructura.getIntNivelRel(), estructura.getIntIdCodigoRel(),
													Constante.PARAM_T_FORMATOARCHIVOS_EXCEL);
						if(listaConfEst != null && !listaConfEst.isEmpty())
						{
							for(ConfEstructura a: listaConfEst)
							{
								ConfiguracionId confId = new ConfiguracionId();
								confId.setIntPersEmpresaPk(a.getId().getIntPersEmpresaPk());
								confId.setIntItemConfiguracion(a.getId().getIntItemConfiguracion());
								configuracion = archivoRemote.getConfiguracionPorPk(confId);
								if(configuracion != null)
								{
									if(Constante.PARAM_T_FORMATOARCHIVOS_EXCEL.compareTo(configuracion.getIntParaFormatoArchivoCod())== 0)
									{										
										List<ConfDetalle> listaConfDetalle = archivoRemote.getConfDetallePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaConfDetalle != null)
										{
											Collections.sort(listaConfDetalle, new Comparator<ConfDetalle>(){
												public int compare(ConfDetalle uno, ConfDetalle otro) {												
													return uno.getId().getIntItemConfiguracionDetalle().compareTo(otro.getId().getIntItemConfiguracionDetalle());
												}
											});
											
											configuracion.setListaConfDetalle(new ArrayList<ConfDetalle>());
											configuracion.setListaConfDetalle(listaConfDetalle);	
											configuracion.setConfEstructura(new ConfEstructura());
											configuracion.setConfEstructura(a);//											
										}
										List<Nombre> listaNombre = 	archivoRemote.getNombrePorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
										if(listaNombre != null && !listaNombre.isEmpty())
										{
												
											configuracion.setListaNombre(new ArrayList<Nombre>());
											configuracion.setListaNombre(listaNombre);
										}
									}
								}
								
								break;
							}
						}
					}
				}
			}
				
			 
	        listamontos = boEnviomonto.getListaEnviomontoXItemEnvioresumen(envioresumen.getId());
			if(listamontos!=null && !listamontos.isEmpty())
			{
				for(Enviomonto monto:listamontos)
				{						
					monto.setEnvioresumen(envioresumen);
					List<Envioconcepto> listaconceptos = null;
					EnvioconceptoId pId = new  EnvioconceptoId();
					pId.setIntEmpresacuentaPk(monto.getId().getIntEmpresacuentaPk());
					pId.setIntItemenvioconcepto(monto.getId().getIntItemenvioconcepto());
					listaconceptos = boEnvioConcepto.getEnvioconceptoPorItemEnvioConcepto(pId);
					if(listaconceptos != null && !listaconceptos.isEmpty())
					{
						monto.setListaEnvioConcepto(new ArrayList<Envioconcepto>());
						monto.setListaEnvioConcepto(listaconceptos);
						for(Envioconcepto encon:listaconceptos)
						{							
							CuentaId cuentaId = new CuentaId();
							cuentaId.setIntPersEmpresaPk(encon.getId().getIntEmpresacuentaPk());
							cuentaId.setIntCuenta(encon.getIntCuentaPk());
							List<CuentaIntegrante> listaCI = remoteCuenta.getListaCuentaIntegrantePorPKCuenta(cuentaId);
							if(listaCI != null && !listaCI.isEmpty())
							{
								SocioPK socioPK = new SocioPK();
								socioPK.setIntIdEmpresa(encon.getId().getIntEmpresacuentaPk());
								socioPK.setIntIdPersona(listaCI.get(0).getId().getIntPersonaIntegrante());
								Socio socio = remoteSocio.getSocioPorPK(socioPK);
								if(socio != null)
								{
									monto.setSocio(socio);
									Integer intPersona = socio.getId().getIntIdPersona();
									Documento documento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(intPersona, Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI));
									if(documento != null)
									{										
										monto.setDocumento(documento); 
									}
									
								}
							}
							
						}
					}
					
				}
			} 
			
			for(Nombre nombr:configuracion.getListaNombre())
			{
				if(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO.compareTo(nombr.getIntParaTipoValorCod()) == 0)
				{
					filename =  new StringBuilder(nombr.getStrValorFijo()).toString() ;
					
				}else if(Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE.compareTo(nombr.getIntParaTipoValorCod()) == 0)
				{
					filename = new StringBuilder(filename).append(iendoParametro(nombr.getIntParaTipoVariableCod(),listamontos.get(0), null) ).toString();
					
				}
			}
			
			  filename = new StringBuilder(filename).append(".xls").toString();	
			  log.debug("rutaFINAL: "+filename);			  			
			  
			  Workbook workbook = new HSSFWorkbook();
			  Sheet sheet = workbook.createSheet();
			  Integer f = listamontos.size();
			 		  
			  log.debug("f: "+f);
			 
			  Integer b = 0;
			  for(Enviomonto emon: listamontos)
			  {				  
					  if(b.compareTo(f) == -1)
					  {
						  //creando una fila
						  Row row = sheet.createRow(b);				 
						  Integer y = configuracion.getListaConfDetalle().size();
						  Integer a =0;
						  for(ConfDetalle confDetalle: configuracion.getListaConfDetalle())
						  {						  
								//creando una celda
							  if(a.compareTo(y)==-1)
							  {
								  	Cell id = row.createCell(a);
								  	sheet.autoSizeColumn(a);
									if(Constante.PARAM_T_TIPOVALORARCHIVO_FIJO.compareTo(confDetalle.getIntParaTipoValorCod()) == 0)
									{
										id.setCellValue(confDetalle.getStrDatoFijo());
									}else if(Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE.compareTo(confDetalle.getIntParaTipoValorCod()) == 0)
									{
										id.setCellValue(iendoParametro(confDetalle.getIntParaTipoDatoVariableCod(),emon, confDetalle));
									}
								  a++;
							  }							 						  					  
						  }
						  b++;
					  }				  	 					  			  
			  }			  
			  FacesContext facesContext = FacesContext.getCurrentInstance();
			  HttpServletResponse externalContext = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			  externalContext.setContentType("application/vnd.ms-excel");
			  externalContext.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			  OutputStream output = externalContext.getOutputStream();
			  workbook.write(output);
			  facesContext.responseComplete();
			  
			  log.debug("exportarEnvioresumenEXCEL FIN");	
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}  
	}
	
	public String iendoParametro(Integer intParaTipoDatoVariableCod,Enviomonto monto,ConfDetalle confDetalle)
	{
	String string = null;
	try
	{		
		if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_NOMCOMPLETOSINCOMA.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			String nomCompletoSinComa = new StringBuilder(monto.getSocio().getStrApePatSoc()).append(" ")
										.append(monto.getSocio().getStrApeMatSoc()).append(" ")
										.append(monto.getSocio().getStrNombreSoc()).toString();
			if(Constante.PARAM_T_IZQUIERDA.compareTo(confDetalle.getIntParaTipoAlineacionCod()) == 0)
			{					
				string = leftPad(nomCompletoSinComa, confDetalle.getIntTamano());
			}
			else if(Constante.PARAM_T_DERECHA.compareTo(confDetalle.getIntParaTipoAlineacionCod()) == 0)
			{
				string = rightPad(nomCompletoSinComa, confDetalle.getIntTamano());
			}	
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_NOMCOMPLETOCONCOMA.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			String nomCompletoConComa = new StringBuilder(monto.getSocio().getStrApePatSoc()).append(" ")
										.append(monto.getSocio().getStrApeMatSoc()).append(", ")
										.append(monto.getSocio().getStrNombreSoc()).toString();
			if(Constante.PARAM_T_IZQUIERDA.compareTo(confDetalle.getIntParaTipoAlineacionCod()) == 0)
			{
				string = leftPad(nomCompletoConComa, confDetalle.getIntTamano());
			}
			else if(Constante.PARAM_T_DERECHA.compareTo(confDetalle.getIntParaTipoAlineacionCod()) == 0)
			{
				string = rightPad(nomCompletoConComa, confDetalle.getIntTamano());
			}	
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_APPATERNO.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			if(Constante.PARAM_T_IZQUIERDA.compareTo(confDetalle.getIntParaTipoAlineacionCod()) == 0)
			{
				string = leftPad(monto.getSocio().getStrApePatSoc(), confDetalle.getIntTamano());
			}
			else if(Constante.PARAM_T_DERECHA.compareTo(confDetalle.getIntParaTipoAlineacionCod()) == 0)
			{
				string = rightPad(monto.getSocio().getStrApePatSoc(), confDetalle.getIntTamano());
			}			
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_APMATERNO.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			
			if(Constante.PARAM_T_IZQUIERDA.compareTo(confDetalle.getIntParaTipoAlineacionCod()) == 0)
			{
				string = leftPad(monto.getSocio().getStrApeMatSoc(), confDetalle.getIntTamano());
			}
			else if(Constante.PARAM_T_DERECHA.compareTo(confDetalle.getIntParaTipoAlineacionCod()) == 0)
			{
				string = rightPad(monto.getSocio().getStrApeMatSoc(), confDetalle.getIntTamano());
			}
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_NOMBRES.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			if(Constante.PARAM_T_IZQUIERDA.compareTo(confDetalle.getIntParaTipoAlineacionCod()) == 0)
			{
				string = leftPad(monto.getSocio().getStrNombreSoc(), confDetalle.getIntTamano());
			}
			else if(Constante.PARAM_T_DERECHA.compareTo(confDetalle.getIntParaTipoAlineacionCod()) == 0)
			{
				string = rightPad(monto.getSocio().getStrNombreSoc(), confDetalle.getIntTamano());
			}
			
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_DNI.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			string = monto.getDocumento().getStrNumeroIdentidad();
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_MONTODCTOSINPTO.compareTo(intParaTipoDatoVariableCod) == 0)
		{	
			monto.setBdMontoenvio(monto.getBdMontoenvio().setScale(2,BigDecimal.ROUND_HALF_UP));
			string = monto.getBdMontoenvio().toString().replace(".", "");
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_MONTODCTOCONPTO.compareTo(intParaTipoDatoVariableCod) == 0)
		{								
			log.debug("monto con pto antes: "+monto.getBdMontoenvio());
			monto.setBdMontoenvio(monto.getBdMontoenvio().setScale(2,BigDecimal.ROUND_HALF_UP));
			log.debug("monto con pto despues: "+monto.getBdMontoenvio());
			string = monto.getBdMontoenvio().toString().replace(".", ",");
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_AÑO04DIGITOS.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			string = monto.getEnvioresumen().getIntPeriodoplanilla().toString().substring(0, 4);
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_AÑO02DIGITOS.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			string = monto.getEnvioresumen().getIntPeriodoplanilla().toString().substring(2,4);;
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_MES02DIGITOS.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			string = monto.getEnvioresumen().getIntPeriodoplanilla().toString().substring(4);;
		}
		/*else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_SALDODEPRESTAMO.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_SALDODEDEUDATOTAL.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_CARGO.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			
		}
		else if(Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO_SOLICITUDONP.compareTo(intParaTipoDatoVariableCod) == 0)
		{
			
		}*/		
		
	}catch(Exception e)
	{
		log.error(e.getMessage(), e);
	}
	return string;		
	}

	public static String leftPad(String sValue, int iMinLength) {
        StringBuilder sb = new StringBuilder(iMinLength);
        sb.append(sValue);
        while (sb.length() < iMinLength) {
            sb.append(" ");
        }
        return sb.toString();
    }

	public static String rightPad(String sValue, int iMinLength) {
        StringBuilder sb = new StringBuilder(iMinLength);
        sb.append(sValue);
        while (sb.length() < iMinLength) {
            sb.insert(0, " ");
        }
        return sb.toString();
    }
	//------------------------  FIN EXPORTAR  --------------------------------------------------------------------------
	
	
	//------------------------ INICIO TIPO PLANILLA  --------------------------------------------------------------------------
	/**
	 * Tipo de Planilla
	 * @param event
	 */
	public void verTipoPlanilla(ActionEvent event)
	{
		log.info("verTipoPlanilla v0 	INICIO");
		
		cargarUsuario();
		destacado = false;	
		blnhabilitarAgregar = Boolean.TRUE;
		String nombre;
		List<Tabla> listaTemporal		= new ArrayList<Tabla>();
		List<Estructura> listaUnidadEjecutora 	= new ArrayList<Estructura>();	
		registroSeleccionado = (ItemPlanilla)event.getComponent().getAttributes().get("item");		
		
		try{
			
			if(registroSeleccionado.getBdHaberes().compareTo(new BigDecimal(0))==1)
			{
				nombre="Haberes";
					
			} else if(registroSeleccionado.getBdIncentivos().compareTo(new BigDecimal(0))==1)
			{
				nombre="Incentivo";
			}
			else
			{
				nombre="CAS";
			}
			registroSeleccionado.setStrhaberIncentivoCas(nombre);
			
			if(listaModalidadPlanilla != null && !listaModalidadPlanilla.isEmpty())
			{
				for(Tabla tabla:listaModalidadPlanilla)
				{
					if(nombre.equals("Incentivo"))
					{
						if(tabla.getStrDescripcion().equals("Haberes"))
						{
							listaTemporal.add(tabla);
						}
					}
					else if(nombre.equals("Haberes"))
					{
						if(tabla.getStrDescripcion().equals("Incentivos"))
						{
							listaTemporal.add(tabla);
						}
					}
				}
				listaModalidadTipoPlanilla =listaTemporal;
			}
			for(Estructura est: listaBusquedaDePPEjecutoraEnvio)
			{
				if(dtoDeEnvio.getEstructura().getJuridica().getIntIdPersona().compareTo(est.getJuridica().getIntIdPersona())==0)
				{
					listaUnidadEjecutora.add(est);
				}
			}
			listaBusquedaDePPEjecutoraEnvio = listaUnidadEjecutora;
			
			log.info("verTipoPlanilla v0 FIN");
						
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			
		}
	}
	
	
	public void validarTipoPlanilla(ActionEvent event)
	{
		log.debug("validarTipoPlanilla v1 INICIO");
		
		if(intCodigoPlanilla == null)
		{			
			mostrarMensaje(Boolean.FALSE, "Ingrese el codigo de Planilla.");
			return;
		}
		if(bdMontoTipoPlanilla != null)
		{
			if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(listaModalidadTipoPlanilla.get(0).getIntIdDetalle()) == 0)
			{
				
				if(bdMontoTipoPlanilla.compareTo(registroSeleccionado.getBdIncentivos()) == 1){
					mostrarMensaje(Boolean.FALSE, "Monto debe ser menor que "+registroSeleccionado.getBdIncentivos()); return;
				}
				else
				{
					blnhabilitarAgregar = Boolean.FALSE;
				}
			}
			else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(listaModalidadTipoPlanilla.get(0).getIntIdDetalle()) == 0)
			{
				if(bdMontoTipoPlanilla.compareTo(registroSeleccionado.getBdHaberes()) == 1){
					mostrarMensaje(Boolean.FALSE, "Monto debe ser menor que "+registroSeleccionado.getBdHaberes()); return;
				}
				else
				{
					blnhabilitarAgregar = Boolean.FALSE;
				}
			}
		}
		else
		{
			mostrarMensaje(Boolean.FALSE, "Ingrese un monto."); return;
		}
		
		deshabilitarPanelInferior2();
		
	}
	
	public void addTipoPlanilla(ActionEvent event)
	{
		log.debug("addTipoPlanilla INICIO");		
		Enviomonto envio = registroSeleccionado.getListaEnviomonto().get(0);
		SocioEstructura cira = registroSeleccionado.getSocio().getListSocioEstructura().get(0);
		log.info("addTipoPlanilla INICIO V1");	
		Timestamp tsFechaHoraActual 	= null;	
		tsFechaHoraActual = JFecha.obtenerTimestampDeFechayHoraActual();
		java.sql.Date      date      = new           java.sql.Date(tsFechaHoraActual.getTime()); 
		SocioEstructura se = new SocioEstructura();
		
		se.setId(new SocioEstructuraPK());
		se.getId().setIntIdEmpresa(registroSeleccionado.getSocio().getId().getIntIdEmpresa());
		se.getId().setIntIdPersona(registroSeleccionado.getSocio().getId().getIntIdPersona());	
		se.setIntEmpresaSucUsuario(cira.getIntEmpresaSucUsuario());
		se.setIntIdSucursalUsuario(cira.getIntIdSucursalUsuario());
		se.setIntIdSubSucursalUsuario(cira.getIntIdSubSucursalUsuario());
		se.setIntEmpresaSucAdministra(cira.getIntEmpresaSucAdministra());
		se.setIntIdSucursalAdministra(cira.getIntIdSucursalAdministra());
		se.setIntIdSubsucurAdministra(cira.getIntIdSubsucurAdministra());
		se.setIntTipoSocio(Constante.PARAM_T_TIPOSOCIO_ACTIVO);
		se.setIntModalidad(new Integer(listaModalidadTipoPlanilla.get(0).getIntIdDetalle()));	
		se.setIntNivel(cira.getIntNivel());
		se.setIntCodigo(cira.getIntCodigo());
		se.setStrCodigoPlanilla(intCodigoPlanilla.toString());		
		se.setIntTipoEstructura(new Integer(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO));
		se.setIntEmpresaUsuario(cira.getIntEmpresaUsuario());
		se.setIntPersonaUsuario(cira.getIntPersonaUsuario());
		se.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		se.setDtFechaRegistro(date);
		 
		log.debug("date="+date);
		registroSeleccionado.getSocio().getListSocioEstructura().add(se);
		registroSeleccionado.getSocio().setSocioEstructura(new SocioEstructura());
		registroSeleccionado.getSocio().setSocioEstructura(se);
		
		Enviomonto ento = new Enviomonto();
		ento.getId().setIntEmpresacuentaPk(envio.getId().getIntEmpresacuentaPk());
		ento.setIntModalidadCod(listaModalidadTipoPlanilla.get(0).getIntIdDetalle());
		ento.setIntTiposocioCod(Constante.PARAM_T_TIPOSOCIO_ACTIVO);
		ento.setBdMontoenvio(bdMontoTipoPlanilla);
		ento.setIntNivel(envio.getIntNivel());
		ento.setIntCodigo(envio.getIntCodigo());
		ento.setIntTipoestructuraCod(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO);
		ento.setIntEmpresasucprocesaPk(envio.getIntEmpresasucprocesaPk());
		ento.setIntIdsucursalprocesaPk(envio.getIntIdsucursalprocesaPk());
		ento.setIntIdsubsucursalprocesaPk(envio.getIntIdsubsucursalprocesaPk());
		ento.setIntEmpresasucadministraPk(envio.getIntEmpresasucadministraPk());
		ento.setIntIdsucursaladministraPk(envio.getIntIdsucursaladministraPk());
		ento.setIntIdsubsucursaladministra(envio.getIntIdsubsucursaladministra());
		ento.setIntEstadoCod(envio.getIntEstadoCod());
		
		registroSeleccionado.getListaEnviomonto().add(ento);
		registroSeleccionado.setIntCantModalidades(new Integer(2));	
		registroSeleccionado.setBlnAgregoTipoPlanilla(Boolean.TRUE);
		//si se va a agregar una modalidad haber
		if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(listaModalidadTipoPlanilla.get(0).getIntIdDetalle()) == 0)
		{				
			log.debug("haber ");			
			registroSeleccionado.setBdHaberes(bdMontoTipoPlanilla);
			registroSeleccionado.setBdIncentivos(registroSeleccionado.getBdIncentivos().subtract(bdMontoTipoPlanilla));
			intNroHaberesOf++;
			intNroHaberes++;
								
			//en incentivos
			for(Enviomonto eka : registroSeleccionado.getListaEnviomonto())
			{	
				log.debug("antes:"+eka);
				if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(eka.getIntModalidadCod()) == 0)
				{
					eka.setBdMontoenvio(eka.getBdMontoenvio().subtract(bdMontoTipoPlanilla));
					break;
				}		
				
			}
		}
		//si se va a agregar una modalidad incentivo
		else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(listaModalidadTipoPlanilla.get(0).getIntIdDetalle()) == 0)
		{
			log.debug("incentivo ");			
			registroSeleccionado.setBdIncentivos(bdMontoTipoPlanilla);
			registroSeleccionado.setBdHaberes(registroSeleccionado.getBdHaberes().subtract(bdMontoTipoPlanilla));			
			intNroIncentivoOf++;
			intNroIncentivo++;
		
			//en haberes
			for(Enviomonto eka : registroSeleccionado.getListaEnviomonto())
			{
				log.debug("antes:"+eka);
				if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(eka.getIntModalidadCod()) == 0)
				{
					eka.setBdMontoenvio(eka.getBdMontoenvio().subtract(bdMontoTipoPlanilla));
					log.debug("despues:"+eka);
					break;
				}			
				
			}
			
		}
		
			for(ItemPlanilla it: planilla)
			{
				if(it.getDocumento().getStrNumeroIdentidad().equals(registroSeleccionado.getDocumento().getStrNumeroIdentidad()))
				{					
					planilla.remove(it);					
					planilla.add(registroSeleccionado);
					break;
				}
			}			
			
		//Ordenamos por nombre
		Collections.sort(planilla, new Comparator<ItemPlanilla>(){
			public int compare(ItemPlanilla uno, ItemPlanilla otro) {															
				return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
			}
		});	
		
		
		log.info("addTipoPlanilla FIN  V1");	
	}
	
	
	public void reloadCboJuridicaSucursal(ValueChangeEvent event) throws EJBFactoryException, BusinessException
	{
		log.info("reloadCboJuridicaSucursal INICIO v4");		
		Sucursal lSucursal = null;		
		Integer intTipoSucursal = Integer.parseInt("" + event.getNewValue());
		log.info("intTipoSucursal="+intTipoSucursal);	
		List<Estructura> listaEstructura = null;
		PersonaFacadeRemote remotePersona = null;
		Estructura estructura = null;
		EmpresaFacadeRemote remoteEmpresa = null;
		EstructuraFacadeRemote remoteEstructura = null;
		remoteEmpresa = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);		
		remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
		remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
		lSucursal = remoteEmpresa.getSucursalPorIdPersona(intTipoSucursal);	
		if(lSucursal != null)
		{
			listaEstructura = remoteEstructura.getListaEstructuraPorIdEmpresaYIdCasoIdSucursal(usuario.getEmpresa().getIntIdEmpresa(), 							 
																								Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA,
																								lSucursal.getId().getIntIdSucursal());		
			
			if(listaEstructura!=null && listaEstructura.size()>0)
			{
				remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				for(int i=0;i<listaEstructura.size();i++)
				{
					estructura = listaEstructura.get(i);
					estructura.setJuridica(remotePersona.getJuridicaPorPK(estructura.getIntPersPersonaPk()));
				}
			}
		}
		
		log.info("reloadCboJuridicaSucursal FIN v4");
	}
	
	public void onclickCheckDestacado(ActionEvent event){
		log.info("onclickCheckDestacado INICIO v0");
	try{
		destacado = true;
		
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	//------------------------  FIN TIPO PLANILLA  --------------------------------------------------------------------------
	//------------------------  INICIO ENVIOMONTO  --------------------------------------------------------------------------
	public void verEnvioMonto(ActionEvent event)
	{
		log.info("verEnvioMonto INICIO V2");		
		deshabilitarPanelInferior2();
		cargarUsuario();		
		registroSeleccionado = (ItemPlanilla)event.getComponent().getAttributes().get("item");	
		registroSeleccionado.setBdHaberes(registroSeleccionado.getBdHaberes().setScale(2,BigDecimal.ROUND_HALF_UP));
		registroSeleccionado.setBdIncentivos(registroSeleccionado.getBdIncentivos().setScale(2,BigDecimal.ROUND_HALF_UP));
		log.debug(registroSeleccionado);
		bdHaberTemporal 	=  getRegistroSeleccionado().getBdHaberes();
		bdIncentivoTemporal =  getRegistroSeleccionado().getBdIncentivos();
		if(registroSeleccionado != null)
		{
			registroSeleccionado.setBdtotalHaberIncentivo(registroSeleccionado.getBdIncentivos().add(registroSeleccionado.getBdHaberes()));
			bdMontoIncentivoInicial = registroSeleccionado.getBdHaberes();
			bdMontoHaberIncicial 	= registroSeleccionado.getBdIncentivos();		
		}		
		log.info("verEnvioMonto FIN V2");
	}
	
	public void seleccionarHaber(ValueChangeEvent event)throws EJBFactoryException, BusinessException
	{
		log.info("SeleccionarHaber INICIO  v1");
		strHaberIncentivo = null;
		radio = null;
		//HtmlSelectOneRadio radio = (HtmlSelectOneRadio)event.getComponent();  
		radio = (HtmlSelectOneRadio)event.getComponent();
		strHaberIncentivo = (String)radio.getValue(); 
		if ("0".equals(strHaberIncentivo))
		{  
			log.debug("dentro de haber ");
			habilitarCalcularMontoEnvio = Boolean.TRUE;
			habilitarModalidadHaberMontoEnvio = Boolean.TRUE;
			habilitarModalidadIncentivoMontoEnvio = Boolean.FALSE;
		}
		else if("1".equals(strHaberIncentivo))
		{			
			log.debug("dentro de incentivos");
			habilitarModalidadIncentivoMontoEnvio = Boolean.TRUE;
			habilitarModalidadHaberMontoEnvio = Boolean.FALSE;
			habilitarCalcularMontoEnvio = Boolean.TRUE;
		}
		log.info("SeleccionarHaber FIN v1");
	}
	
	public void calcularMontoEnvio(ActionEvent event)
	{
		log.info("calcularMontoEnvio INICIO v0");		
						
		BigDecimal nuevoMontoHaber 		= new BigDecimal(0);
		BigDecimal nuevoMontoIncentivo 	= new BigDecimal(0);
		BigDecimal nuevoMontoTotal 		= new BigDecimal(0);
		
		nuevoMontoHaber 	= registroSeleccionado.getBdHaberes();
		nuevoMontoIncentivo = registroSeleccionado.getBdIncentivos();
		nuevoMontoTotal 	= registroSeleccionado.getBdtotalHaberIncentivo();
		
		if(habilitarModalidadHaberMontoEnvio)
		{
			if(nuevoMontoTotal.compareTo(nuevoMontoHaber) == 0 || nuevoMontoTotal.compareTo(nuevoMontoHaber) == 1)
			{
				nuevoMontoIncentivo = nuevoMontoTotal.subtract(nuevoMontoHaber);
				getRegistroSeleccionado().setBdIncentivos(nuevoMontoIncentivo);				
			}
		}
		if(habilitarModalidadIncentivoMontoEnvio)
		{
			if(nuevoMontoTotal.compareTo(nuevoMontoIncentivo) == 0 ||  nuevoMontoTotal.compareTo(nuevoMontoIncentivo) == 1)
			{
				nuevoMontoHaber = nuevoMontoTotal.subtract(nuevoMontoIncentivo);
				getRegistroSeleccionado().setBdHaberes(nuevoMontoHaber);
			}
		}
		
		habilitarModalidadIncentivoMontoEnvio 	= Boolean.FALSE;
		habilitarModalidadHaberMontoEnvio 		= Boolean.FALSE;
		habilitarCalcularMontoEnvio 			= Boolean.FALSE;
		habilitarGrabarMontoEnvio				= Boolean.TRUE;		
		
		log.info("calcularMontoEnvio FIN v0");
	}
	
	public void grabarMontoEnvio(ActionEvent event)
	{		
		log.info("grabarMontoEnvio	INICIO v4");		
		log.debug(registroSeleccionado);
		if(registroSeleccionado.getListaEnviomonto().size() == 1)
		{
			Enviomonto o = registroSeleccionado.getListaEnviomonto().get(0);
			
			Enviomonto ento = new Enviomonto();
			ento.getId().setIntEmpresacuentaPk(o.getId().getIntEmpresacuentaPk());
			if(o.getIntModalidadCod().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0)
			{
				ento.setIntModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS);
			}
			else
			{
				ento.setIntModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
			}
			ento.setIntTiposocioCod(Constante.PARAM_T_TIPOSOCIO_ACTIVO);
			ento.setBdMontoenvio(bdMontoTipoPlanilla);
			ento.setIntNivel(o.getIntNivel());
			ento.setIntCodigo(o.getIntCodigo());
			ento.setIntTipoestructuraCod(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO);
			ento.setIntEmpresasucprocesaPk(o.getIntEmpresasucprocesaPk());
			ento.setIntIdsucursalprocesaPk(o.getIntIdsucursalprocesaPk());
			ento.setIntIdsubsucursalprocesaPk(o.getIntIdsubsucursalprocesaPk());
			ento.setIntEmpresasucadministraPk(o.getIntEmpresasucadministraPk());
			ento.setIntIdsucursaladministraPk(o.getIntIdsucursaladministraPk());
			ento.setIntIdsubsucursaladministra(o.getIntIdsubsucursaladministra());
			ento.setIntEstadoCod(o.getIntEstadoCod());
			
			registroSeleccionado.getListaEnviomonto().add(ento);
		}
		
		for(Enviomonto ento:registroSeleccionado.getListaEnviomonto())
		{
			if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(ento.getIntModalidadCod()) == 0)
			{
				ento.setBdMontoenvio(registroSeleccionado.getBdHaberes());
			}
			else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(ento.getIntModalidadCod()) == 0)
			{
				ento.setBdMontoenvio(registroSeleccionado.getBdIncentivos());
			}
		}
		
		strHaberIncentivo = null;
		radio = null;
		//Ordenamos por apellido
		Collections.sort(planilla, new Comparator<ItemPlanilla>(){
			public int compare(ItemPlanilla uno, ItemPlanilla otro) {															
				return uno.getSocio().getStrApePatSoc().compareTo(otro.getSocio().getStrApePatSoc());
			}
		});	
		
		
		log.info("grabarMontoEnvio 	FIN v4");
	}
	
	//------------------------  FIN ENVIOMONTO --------------------------------------------------------------------------
//------------------------  ENVIO INFLADA  --------------------------------------------------------------------------
	
	public void verInfladaCAS(ActionEvent event)
	{
		log.info("verInfladaCAS INICIO v0");
		Tabla lmodalidad 	= null;
		Tabla lmodalidadC 	= null;
		cargarUsuario();		
		deshabilitarPanelInferior2();
		habilitarAgregarEnvioinflada = true;
		blnCAS = Boolean.TRUE;
		try
		{			
			//traendo lo seleccionado
			registroSeleccionado = (ItemPlanilla)event.getComponent().getAttributes().get("item");
			log.debug(registroSeleccionado);						
			listaEnvioinflada = new ArrayList<Envioinflada>();
			tipoSocio = "Activo";
			
			//Modalidad 
			if(listaModalidadPlanilla != null && !listaModalidadPlanilla.isEmpty())
			{
				for(int k = 0; k < listaModalidadPlanilla.size(); k++)
				{
					lmodalidad = listaModalidadPlanilla.get(k);
					
					if(lmodalidad.getStrDescripcion().compareTo("CAS")==0)
					{
					lmodalidadC	= lmodalidad;
					}
				}
			}
			listaModalidadPlanillaTemporal = new ArrayList<Tabla>();
						
			for(SocioEstructura sa: registroSeleccionado.getSocio().getListSocioEstructura())
			{
				 if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(sa.getIntModalidad()) == 0)
				{
					listaModalidadPlanillaTemporal.add(lmodalidadC);
				}
			}
		}
		catch (Exception e)
		{
		  log.error(e.getMessage(),e);
		}	
	}
	
	public void verInflada(ActionEvent event)
	{
		log.info("verInflada INICIO v0");
		Tabla lmodalidad 	= null;
		Tabla lmodalidadH 	= null;
		Tabla lmodalidadI 	= null;		
		cargarUsuario();		
		deshabilitarPanelInferior2();
		habilitarAgregarEnvioinflada = true;
		blnCAS = Boolean.FALSE;
		try
		{			
			//traendo lo seleccionado
			registroSeleccionado = (ItemPlanilla)event.getComponent().getAttributes().get("item");									
			listaEnvioinflada = new ArrayList<Envioinflada>();
			
			//tipo de socio
			if(strTipoSociOcas.equals("Activo")|| strTipoSociOcas.equals("Cesante"))
			{
				if(strTipoSociOcas.equals("Activo"))
				{
					tipoSocio = "Activo";
				}
				else if(strTipoSociOcas.equals("Cesante"))
				{
					tipoSocio = "Cesante";
				}
			}
			
			//Modalidad 
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
				}
			}
			
			listaModalidadPlanillaTemporal = new ArrayList<Tabla>();
			
			 if(!blnEnviadaIncentivo)
			{
				 if(intNroIncentivoOf >0)
					{
						listaModalidadPlanillaTemporal.add(lmodalidadI);
					} 
			}
				
			if(!blnEnviadaHaber)
			{
				if(intNroHaberesOf > 0)
				{
					listaModalidadPlanillaTemporal.add(lmodalidadH);
				}
			}
			log.debug("verinflada FIN");				
		}
		
		catch (Exception e)
		{
		  log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void editEnvioInflada(ActionEvent event)
	{
		log.debug("editEnvioInflada INICIO");
		Tabla lmodalidad 	= null;
		Tabla lmodalidadH 	= null;
		Tabla lmodalidadI 	= null;
		Tabla lmodalidadC 	= null;
		habilitarGrabarInflada = Boolean.TRUE;
		
		
		listaEnvioinflada = new ArrayList<Envioinflada>();
		registroSeleccionado = (ItemPlanilla)event.getComponent().getAttributes().get("item");
		for(Enviomonto ento: registroSeleccionado.getListaEnviomonto())
		{				
			if(ento.getListaEnvioinflada()!= null && !ento.getListaEnvioinflada().isEmpty())
			{
				for(Envioinflada enda: ento.getListaEnvioinflada())
				{
					listaEnvioinflada.add(enda);
				}
			}
			
		}
		if(listaEnvioinflada.size()< registroSeleccionado.getListaEnviomonto().size())
		{
			log.debug("permiteme eliminar o agregar lo que falta.");
			habilitarAgregarEnvioinflada = Boolean.TRUE;
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
					lmodalidadC	= lmodalidad;
					}
				}
				listaModalidadPlanillaTemporal = new ArrayList<Tabla>();
				
				for(SocioEstructura sa: registroSeleccionado.getSocio().getListSocioEstructura())
				{
					if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(sa.getIntModalidad()) == 0)
					{
						listaModalidadPlanillaTemporal.add(lmodalidadH);
					}
					else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(sa.getIntModalidad()) == 0)
					{
						listaModalidadPlanillaTemporal.add(lmodalidadI);
					}
					else if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(sa.getIntModalidad()) == 0)
					{
						listaModalidadPlanillaTemporal.add(lmodalidadC);
					}
				}	
			}
			
		}
		
		log.debug("editEnvioInflada FIN");
	}
	

	
	public void addEnvioInflada()
	{
		log.debug("addEnvioInflada INICIO");
		
		Timestamp tsFechaHoraActual = null;
		try
		{
			//validaciones
			if(envioInfladaK.getIntTipoinfladaCod() == 0)
			{
				mostrarMensaje(Boolean.FALSE, "Seleccione tipo de inflada."); return;
			}			
			if(envioInfladaK.getBdMonto()== null)
			{
				mostrarMensaje(Boolean.FALSE, "Ingresar monto."); return;
			}
			if(envioInfladaK.getBdMonto().compareTo(new BigDecimal(0)) == 0)
			{
				mostrarMensaje(Boolean.FALSE, "Ingresar monto mayor a 0."); return;
			}
			
			if(envioInfladaK.getStrObservacion().length() == 0)
			{
				mostrarMensaje(Boolean.FALSE, "Ingresar observacion."); return;			
			}
			//fin de validaciones
			
			//agregando a la grilla de envioinflada
			deshabilitarPanelInferior2();
			tsFechaHoraActual = JFecha.obtenerTimestampDeFechayHoraActual();
			
			Envioinflada envioI = new Envioinflada();
			envioI.setBdMonto(getEnvioInfladaK().getBdMonto());
			envioI.setStrObservacion(getEnvioInfladaK().getStrObservacion());
			envioI.setIntTipoinfladaCod(getEnvioInfladaK().getIntTipoinfladaCod());
			envioI.setIntModalidad(getEnvioInfladaK().getIntModalidad());
			envioI.setTsFecharegistro(tsFechaHoraActual);
			envioI.setIntEmpresausuarioPk(EMPRESA_USUARIO);		
			envioI.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			if(listaEnvioinflada == null)
			{
				log.debug("listaEnvioinflada es null");
				setListaEnvioinflada(new ArrayList<Envioinflada>());
				getListaEnvioinflada().add(envioI);
				habilitarGrabarInflada = Boolean.TRUE;				
			}else
			{
				log.debug("listaEnvioinflada cant="+listaEnvioinflada.size());
				getListaEnvioinflada().add(envioI);
				habilitarGrabarInflada = Boolean.TRUE;
				
			}
			//fin del boton agregar a la grilla inflada
			
			
			if(listaModalidadPlanillaTemporal.size() == listaEnvioinflada.size())
			{
				habilitarAgregarEnvioinflada=false;
			}
			else if(listaModalidadPlanillaTemporal.size() > listaEnvioinflada.size())
			{
				habilitarAgregarEnvioinflada=true;
			}			
			
		}
		catch (Exception e)
		{
		  log.error(e.getMessage(),e);
		}
		
	}
	
	public void eliminarEnvioinflada(ActionEvent event)
	{
		log.debug("eliminarEnvioinflada INICIO");
		try
		{ 
			envioinfladaEliminar =(Envioinflada)event.getComponent().getAttributes().get("item");	
			getListaEnvioinflada().remove(envioinfladaEliminar);
			log.debug("lista inflada cant="+listaEnvioinflada.size());
			listaEnvInfEliminada.add(envioinfladaEliminar);
			
		}
		catch (Exception e)
		{
		  log.error(e.getMessage(),e);
		}
	}
	public void grabarInfladaPlanillaCAS(ActionEvent event)
	{
		log.debug("grabarInfladaPlanillaCAS INICIO ");
		try
		{
			if(listaEnvioinflada.size()>0)			  
			{
				for(Enviomonto eo: registroSeleccionado.getListaEnviomonto())
				{
					if(eo.getListaEnvioinflada() == null || eo.getListaEnvioinflada().isEmpty())
					{					
						eo.setListaEnvioinflada(new ArrayList<Envioinflada>());
						for(Envioinflada enada: listaEnvioinflada)
						{
							if(eo.getIntModalidadCod().compareTo(enada.getIntModalidad()) == 0)
							{
								eo.getListaEnvioinflada().add(enada);
																
								if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(eo.getIntModalidadCod())==0)
								{
									registroSeleccionado.setBdCasI(enada.getBdMonto());
								}
							}	
						}					
					}								
				}
			}
			else if(listaEnvioinflada.size() == 0 
					&&(registroSeleccionado.getBdCasI().compareTo(new BigDecimal(0)) == 1 ))					
			{
				for(Enviomonto ento :registroSeleccionado.getListaEnviomonto())
				{
					for(Envioinflada enda :ento.getListaEnvioinflada())
					{
						for(Envioinflada envio: listaEnvInfEliminada)
						{
							if(enda.getIntModalidad().compareTo(envio.getIntModalidad()) == 0)
							{
								ento.getListaEnvioinflada().remove(enda);
								if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(envio.getIntModalidad())==0)
								{
									registroSeleccionado.setBdCasI(new BigDecimal(0));
								}
							}
						}						
					}
				}
			}
			
			//Limpiar
			setEnvioInfladaK(new Envioinflada());	
			habilitarGrabarInflada = false;
			
		log.debug("grabarInfladaPlanillaCAS FIN ");	
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	public void grabarInfladaPlanilla(ActionEvent event){
		log.debug("grabarInfladaPlanilla INICIO ");
		
		try
		{	
			/**
			 * Grabar bien para agregar o para eliminar
			 */
			for(ItemPlanilla illa: planilla)
			{
				log.debug("inicio="+illa);
			}
			
			if(listaEnvioinflada.size()>0)			  
			{
				/**
				 * Agrega una inflada a un enviomonto
				 */
				
				for(Enviomonto eo: registroSeleccionado.getListaEnviomonto())
				{
					
					if(eo.getListaEnvioinflada() == null || eo.getListaEnvioinflada().isEmpty())
					{						
						eo.setListaEnvioinflada(new ArrayList<Envioinflada>());
						for(Envioinflada enada: listaEnvioinflada)
						{
							if(eo.getIntModalidadCod().compareTo(enada.getIntModalidad()) == 0)
							{
								eo.getListaEnvioinflada().add(enada);
								
								if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(eo.getIntModalidadCod())==0)
								{
									registroSeleccionado.setBdHaberesI(enada.getBdMonto());
								}
								else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(eo.getIntModalidadCod())==0)
								{
									registroSeleccionado.setBdIncentivosI(enada.getBdMonto());
								}
								else if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(eo.getIntModalidadCod())==0)
								{
									registroSeleccionado.setBdCasI(enada.getBdMonto());
								}
							}	
						}					
					}								
				}		
				
			}
			else if(listaEnvioinflada.size() == 0 
					&&(registroSeleccionado.getBdHaberesI().compareTo(new BigDecimal(0)) == 1 
					|| registroSeleccionado.getBdIncentivosI().compareTo(new BigDecimal(0)) == 1))
			{
				/**
				 * Elimina una inflada de algun enviomonto
				 */
				for(Enviomonto ento :registroSeleccionado.getListaEnviomonto())
				{
					for(Envioinflada enda :ento.getListaEnvioinflada())
					{
						for(Envioinflada envio: listaEnvInfEliminada)
						{
							if(enda.getIntModalidad().compareTo(envio.getIntModalidad()) == 0)
							{
								ento.getListaEnvioinflada().remove(enda);
								if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(envio.getIntModalidad())==0)
								{
									registroSeleccionado.setBdHaberesI(new BigDecimal(0));
								}
								else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(envio.getIntModalidad())==0)
								{
									registroSeleccionado.setBdIncentivosI(new BigDecimal(0));
								}
								else if(Constante.PARAM_T_MODALIDADPLANILLA_CAS.compareTo(envio.getIntModalidad())==0)
								{
									registroSeleccionado.setBdCasI(new BigDecimal(0));
								}
							}
						}						
					}
				}				
			}
			
			registroSeleccionado.setBdEnvioTotal(getRegistroSeleccionado().getBdHaberes()
					.add(getRegistroSeleccionado().getBdIncentivos()).add(getRegistroSeleccionado().getBdCas())
					.add(getRegistroSeleccionado().getBdIncentivosI()).add(getRegistroSeleccionado().getBdHaberesI())
					.add(getRegistroSeleccionado().getBdCasI()));			
			//Limpiar
			setEnvioInfladaK(new Envioinflada());	
			habilitarGrabarInflada = false;
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void cancelarInfladaPlanilla(ActionEvent event)
	{
		try{
			setEnvioInfladaK(new Envioinflada());
			registroSeleccionado = new ItemPlanilla();
			registroSeleccionado = (ItemPlanilla)event.getComponent().getAttributes().get("item");
			deshabilitarPanelInferior2();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void deshabilitarPanelInferior2(){
		log.info("deshabilitarPanelInferior");		
		mostrarMensajeError 				= Boolean.FALSE;
		mostrarMensajeExito 				= Boolean.FALSE;	
		habilitarGrabarMontoEnvio			= Boolean.FALSE;
		habilitarCalcularMontoEnvio 		= Boolean.FALSE;
		habilitarModalidadHaberMontoEnvio	= Boolean.FALSE;
		habilitarModalidadIncentivoMontoEnvio=Boolean.FALSE;
		
	}
	
	//------------------------ FIN  ENVIO INFLADA  --------------------------------------------------------------------------
	public Integer getNroHaberes()
	{
		Integer ret = 0;		
		for(ItemPlanilla itemPlanilla :  planilla)
		{
			Iterator<SocioEstructura> it = itemPlanilla.getSocio().getListSocioEstructura().iterator();
			while(it.hasNext())
			{
				SocioEstructura item = (SocioEstructura)it.next();			
					
				if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(item.getIntModalidad()) == 0)
				{
					ret++;
				}			
			}
		}		
		return ret;
	}
	
	public Integer getNroIncentivos()
	{
		Integer ret = 0;
		for(ItemPlanilla itemPlanilla: planilla)
		{
			Iterator<SocioEstructura> it = itemPlanilla.getSocio().getListSocioEstructura().iterator();
			while(it.hasNext())
			{
				SocioEstructura item = (SocioEstructura)it.next();
								
				if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(item.getIntModalidad()) == 0)
				{
					ret++;
				}				
			}			
		}		
		return ret;
	}
	
	public Integer getNroHaberesEnviado()
	{
		Integer ret = 0;
		Iterator<ItemPlanilla> it = planilla.iterator();
		while(it.hasNext())
		{
			ItemPlanilla item = (ItemPlanilla)it.next();
			if(item.getTieneHaber())
			{
				ret++;
			}
		}		
		return ret;
	}
	
	public Integer getNroIncentivosEnviado()
	{
		Integer ret = 0;
		Iterator<ItemPlanilla> it = planilla.iterator();
		while(it.hasNext())
		{
			ItemPlanilla item = (ItemPlanilla)it.next();
			if(item.getTieneIncentivo())
			{
				ret++;
			}
		}		
		return ret;
	}
	
	public BigDecimal getTotalHaberes()
	{		
		BigDecimal ret = new BigDecimal(0);
		Iterator<ItemPlanilla> it = planilla.iterator();
		while(it.hasNext())
		{
			ItemPlanilla item = (ItemPlanilla)it.next();
			ret = ret.add(item.getBdHaberes());
		}		
		return ret;
	}
	
	
	public BigDecimal getTotalIncentivos()
	{		
		BigDecimal ret = new BigDecimal(0);
		Iterator<ItemPlanilla> it = planilla.iterator();
		while(it.hasNext())
		{
			ItemPlanilla item = (ItemPlanilla)it.next();
			ret = ret.add(item.getBdIncentivos());
		}		
		return ret;
	}
	public BigDecimal getTotalCas()
	{		
		BigDecimal ret = new BigDecimal(0);
		Iterator<ItemPlanilla> it = planillaCAS.iterator();
		while(it.hasNext())
		{
			ItemPlanilla item = (ItemPlanilla)it.next();
			ret = ret.add(item.getBdCas());
		}		
		return ret;
	}
	public BigDecimal getTotalHaberI()
	{		
		BigDecimal ret = new BigDecimal(0);
		Iterator<ItemPlanilla> it = planilla.iterator();
		while(it.hasNext())
		{
			ItemPlanilla item = (ItemPlanilla)it.next();
			ret = ret.add(item.getBdHaberesI());
		}		
		return ret;
	}
	public BigDecimal getTotalIncentivoI()
	{		
		BigDecimal ret = new BigDecimal(0);
		Iterator <ItemPlanilla> it = planilla.iterator();
		while(it.hasNext())
		{
			ItemPlanilla item = (ItemPlanilla)it.next();
			ret = ret.add(item.getBdIncentivosI());
		}		
		return ret;
	}
	
	
	public BigDecimal getTotalCasI()
	{		
		BigDecimal ret = new BigDecimal(0);
		Iterator<ItemPlanilla> it = planillaCAS.iterator();
		while(it.hasNext())
		{
			ItemPlanilla item = (ItemPlanilla)it.next();
			ret = ret.add(item.getBdCasI());
		}		
		return ret;
	}
	
	public BigDecimal getTotalTotal()
	{		
		return 
		getTotalHaberes().add(getTotalHaberI())
		.add(getTotalIncentivos().add(getTotalIncentivoI()));
		
	}
	
	public BigDecimal getTotalTotalCasTotalCasI()
	{		
		return getTotalCas().add(getTotalCasI());
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
	
	protected HttpServletRequest getRequest()
	{
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
	public EnvioConceptoComp getDtoFiltroDeEnvio() {
		return dtoFiltroDeEnvio;
	}
	public void setDtoFiltroDeEnvio(EnvioConceptoComp dtoFiltroDeEnvio) {
		this.dtoFiltroDeEnvio = dtoFiltroDeEnvio;
	}
	public String getStrMsgDeEnvio() {
		return strMsgDeEnvio;
	}
	public void setStrMsgDeEnvio(String strMsgDeEnvio) {
		this.strMsgDeEnvio = strMsgDeEnvio;
	}
	public EnvioMsg getMsgMantDeEnvio() {
		return msgMantDeEnvio;
	}
	public void setMsgMantDeEnvio(EnvioMsg msgMantDeEnvio) {
		this.msgMantDeEnvio = msgMantDeEnvio;
	}
	public String getStrTipoMantDeEnvio() {
		return strTipoMantDeEnvio;
	}
	public void setStrTipoMantDeEnvio(String strTipoMantDeEnvio) {
		this.strTipoMantDeEnvio = strTipoMantDeEnvio;
	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	public String getStrIndexFilaDeEnvio() {
		return strIndexFilaDeEnvio;
	}
	public void setStrIndexFilaDeEnvio(String strIndexFilaDeEnvio) {
		this.strIndexFilaDeEnvio = strIndexFilaDeEnvio;
	}
	public String getStrEstadoFilaDeEnvio() {
		return strEstadoFilaDeEnvio;
	}
	public void setStrEstadoFilaDeEnvio(String strEstadoFilaDeEnvio) {
		this.strEstadoFilaDeEnvio = strEstadoFilaDeEnvio;
	}
	public String getStrTipoMantValidarDeEnvio() {
		return strTipoMantValidarDeEnvio;
	}
	public void setStrTipoMantValidarDeEnvio(String strTipoMantValidarDeEnvio) {
		this.strTipoMantValidarDeEnvio = strTipoMantValidarDeEnvio;
	}
	public EnvioConceptoComp getDtoDeEnvio() {
		return dtoDeEnvio;
	}
	public void setDtoDeEnvio(EnvioConceptoComp dtoDeEnvio) {
		this.dtoDeEnvio = dtoDeEnvio;
	}
	public Envioresumen getRegistroSeleccionadoEnvioResumen() {
		return registroSeleccionadoEnvioResumen;
	}
	public void setRegistroSeleccionadoEnvioResumen(
			Envioresumen registroSeleccionadoEnvioResumen) {
		this.registroSeleccionadoEnvioResumen = registroSeleccionadoEnvioResumen;
	}
	public List<ItemPlanilla> getPlanillaCAS() {
		return planillaCAS;
	}
	public void setPlanillaCAS(List<ItemPlanilla> planillaCAS) {
		this.planillaCAS = planillaCAS;
	}
	public List<ItemPlanilla> getPlanilla() {
		return planilla;
	}
	public void setPlanilla(List<ItemPlanilla> planilla) {
		this.planilla = planilla;
	}
	public List<Tabla> getListaTablaDeSucursal() {
		return listaTablaDeSucursal;
	}
	public void setListaTablaDeSucursal(List<Tabla> listaTablaDeSucursal) {
		this.listaTablaDeSucursal = listaTablaDeSucursal;
	}
	public List<Envioresumen> getListaBusquedaDeEnvioresumen() {
		return listaBusquedaDeEnvioresumen;
	}
	public void setListaBusquedaDeEnvioresumen(
			List<Envioresumen> listaBusquedaDeEnvioresumen) {
		this.listaBusquedaDeEnvioresumen = listaBusquedaDeEnvioresumen;
	}
	public List<Envioresumen> getListaEnvioResumenTemp() {
		return listaEnvioResumenTemp;
	}
	public void setListaEnvioResumenTemp(List<Envioresumen> listaEnvioResumenTemp) {
		this.listaEnvioResumenTemp = listaEnvioResumenTemp;
	}
	public List<EnvioConceptoComp> getListaBusquedaDeEnvio() {
		return listaBusquedaDeEnvio;
	}
	public void setListaBusquedaDeEnvio(List<EnvioConceptoComp> listaBusquedaDeEnvio) {
		this.listaBusquedaDeEnvio = listaBusquedaDeEnvio;
	}
	public List<Estructura> getListaBusquedaDePPEjecutoraEnvio() {
		return listaBusquedaDePPEjecutoraEnvio;
	}
	public void setListaBusquedaDePPEjecutoraEnvio(
			List<Estructura> listaBusquedaDePPEjecutoraEnvio) {
		this.listaBusquedaDePPEjecutoraEnvio = listaBusquedaDePPEjecutoraEnvio;
	}
	public List<Efectuado> getListaEfectuado() {
		return listaEfectuado;
	}
	public void setListaEfectuado(List<Efectuado> listaEfectuado) {
		this.listaEfectuado = listaEfectuado;
	}
	public EnvioConceptoComp getDtoFiltroDePPEjecutoraEnvio() {
		return dtoFiltroDePPEjecutoraEnvio;
	}
	public void setDtoFiltroDePPEjecutoraEnvio(
			EnvioConceptoComp dtoFiltroDePPEjecutoraEnvio) {
		this.dtoFiltroDePPEjecutoraEnvio = dtoFiltroDePPEjecutoraEnvio;
	}
	public String getStrTipoSociOcas() {
		return strTipoSociOcas;
	}
	public void setStrTipoSociOcas(String strTipoSociOcas) {
		this.strTipoSociOcas = strTipoSociOcas;
	}
	public List<Juridica> getListaJuridicaSucursalDePPEjecutoraEnvio() {
		return listaJuridicaSucursalDePPEjecutoraEnvio;
	}
	public void setListaJuridicaSucursalDePPEjecutoraEnvio(
			List<Juridica> listaJuridicaSucursalDePPEjecutoraEnvio) {
		this.listaJuridicaSucursalDePPEjecutoraEnvio = listaJuridicaSucursalDePPEjecutoraEnvio;
	}
	public Integer getIntTipoBusquedaDePPEjecutoraEnvio() {
		return intTipoBusquedaDePPEjecutoraEnvio;
	}
	public void setIntTipoBusquedaDePPEjecutoraEnvio(
			Integer intTipoBusquedaDePPEjecutoraEnvio) {
		this.intTipoBusquedaDePPEjecutoraEnvio = intTipoBusquedaDePPEjecutoraEnvio;
	}
	public String getStrIndexFilaDePPEjecutoraEnvio() {
		return strIndexFilaDePPEjecutoraEnvio;
	}
	public void setStrIndexFilaDePPEjecutoraEnvio(
			String strIndexFilaDePPEjecutoraEnvio) {
		this.strIndexFilaDePPEjecutoraEnvio = strIndexFilaDePPEjecutoraEnvio;
	}
	public Boolean getEsValidoDePPEjecutoraEnvio() {
		return esValidoDePPEjecutoraEnvio;
	}
	public void setEsValidoDePPEjecutoraEnvio(Boolean esValidoDePPEjecutoraEnvio) {
		this.esValidoDePPEjecutoraEnvio = esValidoDePPEjecutoraEnvio;
	}
	public boolean isBlnEnviadaHaber() {
		return blnEnviadaHaber;
	}
	public void setBlnEnviadaHaber(boolean blnEnviadaHaber) {
		this.blnEnviadaHaber = blnEnviadaHaber;
	}
	public boolean isBlnEnviadaIncentivo() {
		return blnEnviadaIncentivo;
	}
	public void setBlnEnviadaIncentivo(boolean blnEnviadaIncentivo) {
		this.blnEnviadaIncentivo = blnEnviadaIncentivo;
	}
	public boolean isBlnEnviadaCas() {
		return blnEnviadaCas;
	}
	public void setBlnEnviadaCas(boolean blnEnviadaCas) {
		this.blnEnviadaCas = blnEnviadaCas;
	}
	public Integer getIntNroHaberesOf() {
		return intNroHaberesOf;
	}
	public void setIntNroHaberesOf(Integer intNroHaberesOf) {
		this.intNroHaberesOf = intNroHaberesOf;
	}
	public Integer getIntNroIncentivoOf() {
		return intNroIncentivoOf;
	}
	public void setIntNroIncentivoOf(Integer intNroIncentivoOf) {
		this.intNroIncentivoOf = intNroIncentivoOf;
	}
	public Integer getIntNroCasOf() {
		return intNroCasOf;
	}
	public void setIntNroCasOf(Integer intNroCasOf) {
		this.intNroCasOf = intNroCasOf;
	}
	public Integer getIntNroHaberes() {
		return intNroHaberes;
	}
	public void setIntNroHaberes(Integer intNroHaberes) {
		this.intNroHaberes = intNroHaberes;
	}
	public Integer getIntNroIncentivo() {
		return intNroIncentivo;
	}
	public void setIntNroIncentivo(Integer intNroIncentivo) {
		this.intNroIncentivo = intNroIncentivo;
	}
	public Integer getIntNroCas() {
		return intNroCas;
	}
	public void setIntNroCas(Integer intNroCas) {
		this.intNroCas = intNroCas;
	}
	public Boolean getBlnCheckHaber() {
		return blnCheckHaber;
	}
	public void setBlnCheckHaber(Boolean blnCheckHaber) {
		this.blnCheckHaber = blnCheckHaber;
	}
	public Boolean getBlnCheckIncentivo() {
		return blnCheckIncentivo;
	}
	public void setBlnCheckIncentivo(Boolean blnCheckIncentivo) {
		this.blnCheckIncentivo = blnCheckIncentivo;
	}
	public Boolean getBlnCheckCAS() {
		return blnCheckCAS;
	}
	public void setBlnCheckCAS(Boolean blnCheckCAS) {
		this.blnCheckCAS = blnCheckCAS;
	}
	public Boolean getHabilitarInflada() {
		return habilitarInflada;
	}
	public void setHabilitarInflada(Boolean habilitarInflada) {
		this.habilitarInflada = habilitarInflada;
	}
	public BigDecimal getBdSumatoriaHaberes() {
		return bdSumatoriaHaberes;
	}
	public void setBdSumatoriaHaberes(BigDecimal bdSumatoriaHaberes) {
		this.bdSumatoriaHaberes = bdSumatoriaHaberes;
	}
	public BigDecimal getBdSumatoriaIncentivos() {
		return bdSumatoriaIncentivos;
	}
	public void setBdSumatoriaIncentivos(BigDecimal bdSumatoriaIncentivos) {
		this.bdSumatoriaIncentivos = bdSumatoriaIncentivos;
	}
	public BigDecimal getBdSumatoriaCas() {
		return bdSumatoriaCas;
	}
	public void setBdSumatoriaCas(BigDecimal bdSumatoriaCas) {
		this.bdSumatoriaCas = bdSumatoriaCas;
	}
	public BigDecimal getBdSumatoriaTotal() {
		return bdSumatoriaTotal;
	}
	public void setBdSumatoriaTotal(BigDecimal bdSumatoriaTotal) {
		this.bdSumatoriaTotal = bdSumatoriaTotal;
	}
	public BigDecimal getBdSumatoriaHaberesI() {
		return bdSumatoriaHaberesI;
	}
	public void setBdSumatoriaHaberesI(BigDecimal bdSumatoriaHaberesI) {
		this.bdSumatoriaHaberesI = bdSumatoriaHaberesI;
	}
	public BigDecimal getBdSumatoriaIncentivosI() {
		return bdSumatoriaIncentivosI;
	}
	public void setBdSumatoriaIncentivosI(BigDecimal bdSumatoriaIncentivosI) {
		this.bdSumatoriaIncentivosI = bdSumatoriaIncentivosI;
	}
	public BigDecimal getBdSumatoriaCasI() {
		return bdSumatoriaCasI;
	}
	public void setBdSumatoriaCasI(BigDecimal bdSumatoriaCasI) {
		this.bdSumatoriaCasI = bdSumatoriaCasI;
	}
	public Boolean getBlnEstructuraDetalle() {
		return blnEstructuraDetalle;
	}
	public void setBlnEstructuraDetalle(Boolean blnEstructuraDetalle) {
		this.blnEstructuraDetalle = blnEstructuraDetalle;
	}
	public Boolean getBlnCAS() {
		return blnCAS;
	}
	public void setBlnCAS(Boolean blnCAS) {
		this.blnCAS = blnCAS;
	}
	public List<Tabla> getListaModalidadTipoPlanilla() {
		return listaModalidadTipoPlanilla;
	}
	public void setListaModalidadTipoPlanilla(List<Tabla> listaModalidadTipoPlanilla) {
		this.listaModalidadTipoPlanilla = listaModalidadTipoPlanilla;
	}
	public Integer getIntCodigoPlanilla() {
		return intCodigoPlanilla;
	}
	public void setIntCodigoPlanilla(Integer intCodigoPlanilla) {
		this.intCodigoPlanilla = intCodigoPlanilla;
	}
	public Boolean getBlnhabilitarAgregar() {
		return blnhabilitarAgregar;
	}
	public void setBlnhabilitarAgregar(Boolean blnhabilitarAgregar) {
		this.blnhabilitarAgregar = blnhabilitarAgregar;
	}
	public Boolean getDestacado() {
		return destacado;
	}
	public void setDestacado(Boolean destacado) {
		this.destacado = destacado;
	}
	public BigDecimal getBdMontoTipoPlanilla() {
		return bdMontoTipoPlanilla;
	}
	public void setBdMontoTipoPlanilla(BigDecimal bdMontoTipoPlanilla) {
		this.bdMontoTipoPlanilla = bdMontoTipoPlanilla;
	}
	public ItemPlanilla getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(ItemPlanilla registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
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
	public List<Tabla> getListaModalidadPlanilla() {
		return listaModalidadPlanilla;
	}
	public void setListaModalidadPlanilla(List<Tabla> listaModalidadPlanilla) {
		this.listaModalidadPlanilla = listaModalidadPlanilla;
	}
	public boolean isHabilitarGrabarMontoEnvio() {
		return habilitarGrabarMontoEnvio;
	}
	public void setHabilitarGrabarMontoEnvio(boolean habilitarGrabarMontoEnvio) {
		this.habilitarGrabarMontoEnvio = habilitarGrabarMontoEnvio;
	}
	public boolean isHabilitarModalidadHaberMontoEnvio() {
		return habilitarModalidadHaberMontoEnvio;
	}
	public void setHabilitarModalidadHaberMontoEnvio(
			boolean habilitarModalidadHaberMontoEnvio) {
		this.habilitarModalidadHaberMontoEnvio = habilitarModalidadHaberMontoEnvio;
	}
	public boolean isHabilitarModalidadIncentivoMontoEnvio() {
		return habilitarModalidadIncentivoMontoEnvio;
	}
	public void setHabilitarModalidadIncentivoMontoEnvio(
			boolean habilitarModalidadIncentivoMontoEnvio) {
		this.habilitarModalidadIncentivoMontoEnvio = habilitarModalidadIncentivoMontoEnvio;
	}
	public boolean isHabilitarCalcularMontoEnvio() {
		return habilitarCalcularMontoEnvio;
	}
	public void setHabilitarCalcularMontoEnvio(boolean habilitarCalcularMontoEnvio) {
		this.habilitarCalcularMontoEnvio = habilitarCalcularMontoEnvio;
	}
	public BigDecimal getBdHaberTemporal() {
		return bdHaberTemporal;
	}
	public void setBdHaberTemporal(BigDecimal bdHaberTemporal) {
		this.bdHaberTemporal = bdHaberTemporal;
	}
	public BigDecimal getBdIncentivoTemporal() {
		return bdIncentivoTemporal;
	}
	public void setBdIncentivoTemporal(BigDecimal bdIncentivoTemporal) {
		this.bdIncentivoTemporal = bdIncentivoTemporal;
	}
	public BigDecimal getBdMontoHaberIncicial() {
		return bdMontoHaberIncicial;
	}
	public void setBdMontoHaberIncicial(BigDecimal bdMontoHaberIncicial) {
		this.bdMontoHaberIncicial = bdMontoHaberIncicial;
	}
	public BigDecimal getBdMontoIncentivoInicial() {
		return bdMontoIncentivoInicial;
	}
	public void setBdMontoIncentivoInicial(BigDecimal bdMontoIncentivoInicial) {
		this.bdMontoIncentivoInicial = bdMontoIncentivoInicial;
	}
	public String getStrHaberIncentivo() {
		return strHaberIncentivo;
	}
	public void setStrHaberIncentivo(String strHaberIncentivo) {
		this.strHaberIncentivo = strHaberIncentivo;
	}
	public Boolean getHabilitarGrabarInflada() {
		return habilitarGrabarInflada;
	}
	public void setHabilitarGrabarInflada(Boolean habilitarGrabarInflada) {
		this.habilitarGrabarInflada = habilitarGrabarInflada;
	}
	public List<Envioinflada> getListaEnvioinflada() {
		return listaEnvioinflada;
	}
	public void setListaEnvioinflada(List<Envioinflada> listaEnvioinflada) {
		this.listaEnvioinflada = listaEnvioinflada;
	}
	public HtmlSelectOneRadio getRadio() {
		return radio;
	}
	public void setRadio(HtmlSelectOneRadio radio) {
		this.radio = radio;
	}
	public boolean isHabilitarAgregarEnvioinflada() {
		return habilitarAgregarEnvioinflada;
	}
	public void setHabilitarAgregarEnvioinflada(boolean habilitarAgregarEnvioinflada) {
		this.habilitarAgregarEnvioinflada = habilitarAgregarEnvioinflada;
	}
	public String getTipoSocio() {
		return tipoSocio;
	}
	public void setTipoSocio(String tipoSocio) {
		this.tipoSocio = tipoSocio;
	}
	public List<Tabla> getListaModalidadPlanillaTemporal() {
		return listaModalidadPlanillaTemporal;
	}
	public void setListaModalidadPlanillaTemporal(
			List<Tabla> listaModalidadPlanillaTemporal) {
		this.listaModalidadPlanillaTemporal = listaModalidadPlanillaTemporal;
	}
	public Envioinflada getEnvioInfladaK() {
		return envioInfladaK;
	}
	public void setEnvioInfladaK(Envioinflada envioInfladaK) {
		this.envioInfladaK = envioInfladaK;
	}
	public Envioinflada getEnvioinfladaEliminar() {
		return envioinfladaEliminar;
	}
	public void setEnvioinfladaEliminar(Envioinflada envioinfladaEliminar) {
		this.envioinfladaEliminar = envioinfladaEliminar;
	}
	public List<Envioinflada> getListaEnvInfEliminada() {
		return listaEnvInfEliminada;
	}
	public void setListaEnvInfEliminada(List<Envioinflada> listaEnvInfEliminada) {
		this.listaEnvInfEliminada = listaEnvInfEliminada;
	}
	public List<Envioinflada> getListaInflada() {
		return listaInflada;
	}
	public void setListaInflada(List<Envioinflada> listaInflada) {
		this.listaInflada = listaInflada;
	}
	public Integer getIntPerfil() {
		return intPerfil;
	}
	public void setIntPerfil(Integer intPerfil) {
		this.intPerfil = intPerfil;
	}
	public List<Tabla> getListaTipoInflada() {
		return listaTipoInflada;
	}
	public void setListaTipoInflada(List<Tabla> listaTipoInflada) {
		this.listaTipoInflada = listaTipoInflada;
	}
	public boolean isEnvioFormRendered() {
		return envioFormRendered;
	}
	public void setEnvioFormRendered(boolean envioFormRendered) {
		this.envioFormRendered = envioFormRendered;
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
	public boolean isBlnPlanillaEnviadaHaber() {
		return blnPlanillaEnviadaHaber;
	}
	public void setBlnPlanillaEnviadaHaber(boolean blnPlanillaEnviadaHaber) {
		this.blnPlanillaEnviadaHaber = blnPlanillaEnviadaHaber;
	}
	public boolean isBlnPlanillaEnviadaIncentivo() {
		return blnPlanillaEnviadaIncentivo;
	}
	public void setBlnPlanillaEnviadaIncentivo(boolean blnPlanillaEnviadaIncentivo) {
		this.blnPlanillaEnviadaIncentivo = blnPlanillaEnviadaIncentivo;
	}
	public List<EfectuadoConceptoComp> getListaEfectuadoConceptoComp() {
		return listaEfectuadoConceptoComp;
	}
	public void setListaEfectuadoConceptoComp(
			List<EfectuadoConceptoComp> listaEfectuadoConceptoComp) {
		this.listaEfectuadoConceptoComp = listaEfectuadoConceptoComp;
	}
	public boolean isBlnMostrarTxth() {
		return blnMostrarTxth;
	}
	public void setBlnMostrarTxth(boolean blnMostrarTxth) {
		this.blnMostrarTxth = blnMostrarTxth;
	}
	public boolean isBlnMostrarExcelh() {
		return blnMostrarExcelh;
	}
	public void setBlnMostrarExcelh(boolean blnMostrarExcelh) {
		this.blnMostrarExcelh = blnMostrarExcelh;
	}
	public boolean isBlnMostrarDBFh() {
		return blnMostrarDBFh;
	}
	public void setBlnMostrarDBFh(boolean blnMostrarDBFh) {
		this.blnMostrarDBFh = blnMostrarDBFh;
	}
	public boolean isBlnMostrarTxta4j() {
		return blnMostrarTxta4j;
	}
	public void setBlnMostrarTxta4j(boolean blnMostrarTxta4j) {
		this.blnMostrarTxta4j = blnMostrarTxta4j;
	}
	public boolean isBlnMostrarExcela4j() {
		return blnMostrarExcela4j;
	}
	public void setBlnMostrarExcela4j(boolean blnMostrarExcela4j) {
		this.blnMostrarExcela4j = blnMostrarExcela4j;
	}
	public boolean isBlnMostrarDBFa4j() {
		return blnMostrarDBFa4j;
	}
	public void setBlnMostrarDBFa4j(boolean blnMostrarDBFa4j) {
		this.blnMostrarDBFa4j = blnMostrarDBFa4j;
	}
	public String getMensajeTxt() {
		return mensajeTxt;
	}
	public void setMensajeTxt(String mensajeTxt) {
		this.mensajeTxt = mensajeTxt;
	}
	public String getMensajeExcel() {
		return mensajeExcel;
	}
	public void setMensajeExcel(String mensajeExcel) {
		this.mensajeExcel = mensajeExcel;
	}
	public String getMensajeDBF() {
		return mensajeDBF;
	}
	public void setMensajeDBF(String mensajeDBF) {
		this.mensajeDBF = mensajeDBF;
	}
	public List<PlanillaAdministra> getPlanillaAdministraCAS() {
		return planillaAdministraCAS;
	}
	public void setPlanillaAdministraCAS(
			List<PlanillaAdministra> planillaAdministraCAS) {
		this.planillaAdministraCAS = planillaAdministraCAS;
	}
	public List<PlanillaAdministra> getPlanillaAdministra() {
		return planillaAdministra;
	}
	public void setPlanillaAdministra(List<PlanillaAdministra> planillaAdministra) {
		this.planillaAdministra = planillaAdministra;
	}
	public List<EnvioMsg> getListaMensajes() {
		return listaMensajes;
	}
	public void setListaMensajes(List<EnvioMsg> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}		
	
}
