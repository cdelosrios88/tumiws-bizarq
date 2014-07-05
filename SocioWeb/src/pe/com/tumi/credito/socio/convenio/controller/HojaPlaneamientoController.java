package pe.com.tumi.credito.socio.convenio.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Adjunto;
import pe.com.tumi.credito.socio.convenio.domain.AdjuntoId;
import pe.com.tumi.credito.socio.convenio.domain.Competencia;
import pe.com.tumi.credito.socio.convenio.domain.CompetenciaId;
import pe.com.tumi.credito.socio.convenio.domain.Poblacion;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionDetalle;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionDetalleId;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionId;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaPlaneamientoComp;
import pe.com.tumi.credito.socio.convenio.facade.ConvenioFacadeLocal;
import pe.com.tumi.credito.socio.convenio.facade.HojaPlaneamientoFacadeLocal;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeLocal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

/************************************************************************/
/* Nombre de la clase: HojaPlaneamientoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de la Hoja de Planeamiento*/
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 28/12/2011 */
/* ********************************************************************* */

public class HojaPlaneamientoController{
	protected  static Logger 	log 			= Logger.getLogger(HojaPlaneamientoController.class);
	private int 				rows = 5;
	private Adenda				beanAdenda = null;
	private Archivo				archivo = null;
	private TipoArchivo			tipoArchivo = null;
	private Adjunto				adjunto = null;
	//Modificado por cdelosrios, 04/12/2013
	/*private	Integer				intTipoConvenio;
	private	Integer				intNivelEntidad;
	private	Integer				intEstadoConv;
	private	Integer				intSucursalConv;
	private	Integer				intModalidad;
	private	Integer				intTipoSocio;
	private	Boolean				chkDonacion;
	private	Boolean				chkRangoFec;
	private	Integer				intRanFecha;
	private Date				daFecIni;
	private Date				daFecFin;
	private Boolean				chkIndeterminado;
	private Boolean				chkDocAdjuntos;
	private String				strEntidad;
	private String				strNombreEntidad;
	private Boolean				chkCartaAutorizacion;*/
	private Integer				intCboNivelEntidad;
	//Fin modificado por cdelosrios, 04/12/2013
	private String				strNombreEstructura;
	private String				strNroRuc;
	//Agregado por cdelosrios, 04/12/2013
	private Integer				intNroHoja;
	private	Integer				intSucursalConv;
	private String				strNombreEntidad;
	private	Integer				intEstadoConv;
	private List<Sucursal> 		listaSucursal;
	private Usuario				usuario;
	private boolean 			poseePermiso;
	Integer MENU_HOJAPLANEAMIENTO = 103;
	Integer MENU_HOJACONTROL = 104;
	Integer MENU_CONVENIO = 105;
	//Fin agregado por cdelosrios, 04/12/2013
	private List<HojaPlaneamientoComp> 		listaEstructHojaPlaneamientoComp;
	private List<ConvenioEstructuraDetalleComp>	listaConvEstructuraDetComp = new ArrayList<ConvenioEstructuraDetalleComp>();
	private List<Poblacion>					listaPoblacion = new ArrayList<Poblacion>();
	private List<PoblacionDetalle>			listaPoblacionDet = new ArrayList<PoblacionDetalle>();
	private List<PoblacionDetalle>			listaResumenPoblacionDet = new ArrayList<PoblacionDetalle>();
	private List<Competencia>				listaCompetencia = new ArrayList<Competencia>();
	private List<Estructura>				listaEstructura;
	private List<EstructuraDetalle>			listaEstructuraDet;
	private List<EstructuraComp>			listaEstructuraComp;
	private List<Adjunto>					listaAdendaAdjunto;
	private List<Archivo>					listaArchivo;
	private GeneralFacadeRemote 			generalFacade = null;
	//Agregado por cdelosrios, 05/12/2013
	private PersonaFacadeRemote 			personaFacade = null;
	//Fin agregado por cdelosrios, 05/12/2013
	
	private String							strCartaPresentacion;
	private String							strConvenioSugerido;
	private String							strAdendaSugerida;
	
	private String							strNombreCartaPresentacion;
	private String							strNombreConvenioSugerido;
	private String							strNombreAdendaSugerida;
	//Carga de Sucursal
	private List<Sucursal> 					listJuridicaSucursal = null;
	
	//Mensajes de Error
	private String				msgTipoConvenio;
	private String				msgEstadoConvenio;
	private String				msgFecDuracion;
	private String				msgFecInicio;
	private String				msgOpcionFiltroCred;
	private String				msgFecSuscripcion;
	private String				msgConvSugerido;
	//Modificado por cdelosrios, 04/12/2013
	//private String				msgTxtRangoFec;
	//Fin modificado por cdelosrios, 04/12/2013
	private String				msgTxtNuevaAdenda;
	private String				msgTxtConvEstructura;
	
	private Boolean 			formHojaPlaneamientoRendered = false;
	private Boolean				formTipoConvEnabled = false;
	private Boolean 			btnBuscarEnabled = true;
	private Boolean				formFecFinDurac = true;
	private Boolean 			formCartaPresent = false;
	private Boolean 			formConvSugerido = false;
	private Boolean 			formAdendaSugerida = false;
	private Boolean 			upldCartaPresent = true;
	private Boolean 			upldConvSugerido = true;
	private Boolean 			upldAdendaSugerida = true;
	private Boolean				chkCartaPresent;
	private Boolean 			chkConvSugerido;
	private Boolean 			chkAdendaSugerida;
	private String				strHojaPlaneamiento;
	private String				rbTiempoDurac;
	private String				strTipoConvenio;
	private Integer 			intCartaPresentacion = null;
	private Integer 			intConvenioSugerido = null;
	private Integer 			intAdendaSugerida = null;
	
	//Propiedades tipo Boolean
	private Boolean				formRangoFechaEnabled = true;
	private Integer 			sesionIntIdEmpresa;
	private Boolean 			blnHojaPlaneamiento;
	private Boolean 			blnHojaControl;
	private Boolean 			blnConvenio;
	
	private ControlProcesoController	controlProcesoController;
	
	private Integer	intCantFilasActivas;
	// CGD -17.12.2013
	private HojaPlaneamientoComp hojaPlanCompBusqeda;
	private Integer intIdConvenioHP;
	private Integer intIdItemConvenioHP;
	
	//Agregado por cdelosrios, 11/12/2013
	public HojaPlaneamientoController() {
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(MENU_HOJAPLANEAMIENTO);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}
	
	public String getLimpiarHojaPlaneamiento(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(MENU_HOJAPLANEAMIENTO);
		log.info("POSEE PERMISO" + poseePermiso);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
			cancelarGrabarHojaPlaneamiento(null);
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		
		return "";
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		this.sesionIntIdEmpresa = usuario.getPerfil().getId().getIntPersEmpresaPk();	
	}
	
	public void cargarValoresIniciales(){
		beanAdenda = new Adenda();
		beanAdenda.setId(new AdendaId());
		adjunto = new Adjunto();
		listJuridicaSucursal = new ArrayList<Sucursal>();
		try{
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			cargarListaSucursal();
		}catch(EJBFactoryException e){
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		controlProcesoController = (ControlProcesoController)getSessionBean("controlProcesoController");
	}
	//Fin agregado por cdelosrios, 11/12/2013
	
	/*public void inicio(ActionEvent event){
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		Usuario usuario = null;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			id = new PermisoPerfilId();
			id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
			id.setIntIdTransaccion(MENU_HOJAPLANEAMIENTO);
			id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
			PermisoFacadeRemote localPermiso = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnHojaPlaneamiento = (permiso == null)?true:false;
			id.setIntIdTransaccion(MENU_HOJACONTROL);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnHojaControl = (permiso == null)?true:false;
			id.setIntIdTransaccion(MENU_CONVENIO);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnConvenio = (permiso == null)?true:false;
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}
	}*/
	
	//Agregado por cdelosrios, 04/12/2013
	private void cargarListaSucursal() throws Exception{
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
		listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(sesionIntIdEmpresa);
		for(Sucursal sucursal : listaSucursal){
			sucursal.setListaArea(empresaFacade.getListaAreaPorSucursal(sucursal));			
		}
		//Ordenamos por nombre
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});
	}
	//Fin agregado por cdelosrios, 04/12/2013
	
	//Getters y Setters
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public Adenda getBeanAdenda() {
		return beanAdenda;
	}
	public void setBeanAdenda(Adenda beanAdenda) {
		this.beanAdenda = beanAdenda;
	}
	public Archivo getArchivo() {
		return archivo;
	}
	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}
	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}
	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}
	public Adjunto getAdjunto() {
		return adjunto;
	}
	public void setAdjunto(Adjunto adjunto) {
		this.adjunto = adjunto;
	}
	public GeneralFacadeRemote getGeneralFacade() {
		return generalFacade;
	}
	public void setGeneralFacade(GeneralFacadeRemote generalFacade) {
		this.generalFacade = generalFacade;
	}
	public String getStrCartaPresentacion() {
		return strCartaPresentacion;
	}
	public void setStrCartaPresentacion(String strCartaPresentacion) {
		this.strCartaPresentacion = strCartaPresentacion;
	}
	public String getStrConvenioSugerido() {
		return strConvenioSugerido;
	}
	public void setStrConvenioSugerido(String strConvenioSugerido) {
		this.strConvenioSugerido = strConvenioSugerido;
	}
	public String getStrAdendaSugerida() {
		return strAdendaSugerida;
	}
	public void setStrAdendaSugerida(String strAdendaSugerida) {
		this.strAdendaSugerida = strAdendaSugerida;
	}
	public String getStrNombreCartaPresentacion() {
		return strNombreCartaPresentacion;
	}
	public void setStrNombreCartaPresentacion(String strNombreCartaPresentacion) {
		this.strNombreCartaPresentacion = strNombreCartaPresentacion;
	}
	public String getStrNombreConvenioSugerido() {
		return strNombreConvenioSugerido;
	}
	public void setStrNombreConvenioSugerido(String strNombreConvenioSugerido) {
		this.strNombreConvenioSugerido = strNombreConvenioSugerido;
	}
	public String getStrNombreAdendaSugerida() {
		return strNombreAdendaSugerida;
	}
	public void setStrNombreAdendaSugerida(String strNombreAdendaSugerida) {
		this.strNombreAdendaSugerida = strNombreAdendaSugerida;
	}
	public Integer getIntEstadoConv() {
		return intEstadoConv;
	}
	public void setIntEstadoConv(Integer intEstadoConv) {
		this.intEstadoConv = intEstadoConv;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
	public Integer getIntSucursalConv() {
		return intSucursalConv;
	}
	public void setIntSucursalConv(Integer intSucursalConv) {
		this.intSucursalConv = intSucursalConv;
	}
	public String getStrNombreEntidad() {
		return strNombreEntidad;
	}
	public void setStrNombreEntidad(String strNombreEntidad) {
		this.strNombreEntidad = strNombreEntidad;
	}
	public String getStrNombreEstructura() {
		return strNombreEstructura;
	}
	public void setStrNombreEstructura(String strNombreEstructura) {
		this.strNombreEstructura = strNombreEstructura;
	}
	public Integer getIntCboNivelEntidad() {
		return intCboNivelEntidad;
	}
	public void setIntCboNivelEntidad(Integer intCboNivelEntidad) {
		this.intCboNivelEntidad = intCboNivelEntidad;
	}
	public String getStrNroRuc() {
		return strNroRuc;
	}
	public void setStrNroRuc(String strNroRuc) {
		this.strNroRuc = strNroRuc;
	}
	//Agregado por cdelosrios, 04/12/2013
	public Integer getIntNroHoja() {
		return intNroHoja;
	}
	public void setIntNroHoja(Integer intNroHoja) {
		this.intNroHoja = intNroHoja;
	}
	//Fin agregado por cdelosrios, 04/12/2013
	public List<HojaPlaneamientoComp> getListaEstructHojaPlaneamientoComp() {
		return listaEstructHojaPlaneamientoComp;
	}
	public void setListaEstructHojaPlaneamientoComp(
			List<HojaPlaneamientoComp> listaEstructHojaPlaneamientoComp) {
		this.listaEstructHojaPlaneamientoComp = listaEstructHojaPlaneamientoComp;
	}
	public List<ConvenioEstructuraDetalleComp> getListaConvEstructuraDetComp() {
		return listaConvEstructuraDetComp;
	}
	public void setListaConvEstructuraDetComp(
			List<ConvenioEstructuraDetalleComp> listaConvEstructuraDetComp) {
		this.listaConvEstructuraDetComp = listaConvEstructuraDetComp;
	}
	public List<EstructuraDetalle> getListaEstructuraDet() {
		return listaEstructuraDet;
	}
	public void setListaEstructuraDet(List<EstructuraDetalle> listaEstructuraDet) {
		this.listaEstructuraDet = listaEstructuraDet;
	}
	public List<EstructuraComp> getListaEstructuraComp() {
		return listaEstructuraComp;
	}
	public void setListaEstructuraComp(List<EstructuraComp> listaEstructuraComp) {
		this.listaEstructuraComp = listaEstructuraComp;
	}
	public List<Adjunto> getListaAdendaAdjunto() {
		return listaAdendaAdjunto;
	}
	public void setListaAdendaAdjunto(List<Adjunto> listaAdendaAdjunto) {
		this.listaAdendaAdjunto = listaAdendaAdjunto;
	}
	public List<Archivo> getListaArchivo() {
		return listaArchivo;
	}
	public void setListaArchivo(List<Archivo> listaArchivo) {
		this.listaArchivo = listaArchivo;
	}
	public List<Poblacion> getListaPoblacion() {
		return listaPoblacion;
	}
	public void setListaPoblacion(List<Poblacion> listaPoblacion) {
		this.listaPoblacion = listaPoblacion;
	}
	public List<PoblacionDetalle> getListaPoblacionDet() {
		return listaPoblacionDet;
	}
	public void setListaPoblacionDet(List<PoblacionDetalle> listaPoblacionDet) {
		this.listaPoblacionDet = listaPoblacionDet;
	}
	public List<PoblacionDetalle> getListaResumenPoblacionDet() {
		return listaResumenPoblacionDet;
	}
	public void setListaResumenPoblacionDet(
			List<PoblacionDetalle> listaResumenPoblacionDet) {
		this.listaResumenPoblacionDet = listaResumenPoblacionDet;
	}
	public List<Competencia> getListaCompetencia() {
		return listaCompetencia;
	}
	public void setListaCompetencia(List<Competencia> listaCompetencia) {
		this.listaCompetencia = listaCompetencia;
	}
	public List<Estructura> getListaEstructura() {
		return listaEstructura;
	}
	public void setListaEstructura(List<Estructura> listaEstructura) {
		this.listaEstructura = listaEstructura;
	}
	public List<Sucursal> getListJuridicaSucursal() {
		log.info("-------------------------------------Debugging EstructuraOrganicaController.getListJuridicaSucursal-------------------------------------");
		log.info("sesionIntIdEmpresa: "+sesionIntIdEmpresa);
		try {
			if(listJuridicaSucursal != null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(sesionIntIdEmpresa);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return this.listJuridicaSucursal;
	}
	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
	}
	public Boolean getFormHojaPlaneamientoRendered() {
		return formHojaPlaneamientoRendered;
	}
	public void setFormHojaPlaneamientoRendered(Boolean formHojaPlaneamientoRendered) {
		this.formHojaPlaneamientoRendered = formHojaPlaneamientoRendered;
	}
	public Boolean getFormTipoConvEnabled() {
		return formTipoConvEnabled;
	}
	public void setFormTipoConvEnabled(Boolean formTipoConvEnabled) {
		this.formTipoConvEnabled = formTipoConvEnabled;
	}
	public Boolean getBtnBuscarEnabled() {
		return btnBuscarEnabled;
	}
	public void setBtnBuscarEnabled(Boolean btnBuscarEnabled) {
		this.btnBuscarEnabled = btnBuscarEnabled;
	}
	public Boolean getFormFecFinDurac() {
		return formFecFinDurac;
	}
	public void setFormFecFinDurac(Boolean formFecFinDurac) {
		this.formFecFinDurac = formFecFinDurac;
	}
	public Boolean getFormCartaPresent() {
		return formCartaPresent;
	}
	public void setFormCartaPresent(Boolean formCartaPresent) {
		this.formCartaPresent = formCartaPresent;
	}
	public Boolean getFormConvSugerido() {
		return formConvSugerido;
	}
	public void setFormConvSugerido(Boolean formConvSugerido) {
		this.formConvSugerido = formConvSugerido;
	}
	public Boolean getFormAdendaSugerida() {
		return formAdendaSugerida;
	}
	public void setFormAdendaSugerida(Boolean formAdendaSugerida) {
		this.formAdendaSugerida = formAdendaSugerida;
	}
	public Boolean getUpldCartaPresent() {
		return upldCartaPresent;
	}
	public void setUpldCartaPresent(Boolean upldCartaPresent) {
		this.upldCartaPresent = upldCartaPresent;
	}
	public Boolean getUpldConvSugerido() {
		return upldConvSugerido;
	}
	public void setUpldConvSugerido(Boolean upldConvSugerido) {
		this.upldConvSugerido = upldConvSugerido;
	}
	public Boolean getUpldAdendaSugerida() {
		return upldAdendaSugerida;
	}
	public void setUpldAdendaSugerida(Boolean upldAdendaSugerida) {
		this.upldAdendaSugerida = upldAdendaSugerida;
	}
	public Boolean getChkCartaPresent() {
		return chkCartaPresent;
	}
	public void setChkCartaPresent(Boolean chkCartaPresent) {
		this.chkCartaPresent = chkCartaPresent;
	}
	public Boolean getChkConvSugerido() {
		return chkConvSugerido;
	}
	public void setChkConvSugerido(Boolean chkConvSugerido) {
		this.chkConvSugerido = chkConvSugerido;
	}
	public Boolean getChkAdendaSugerida() {
		return chkAdendaSugerida;
	}
	public void setChkAdendaSugerida(Boolean chkAdendaSugerida) {
		this.chkAdendaSugerida = chkAdendaSugerida;
	}
	public String getMsgTipoConvenio() {
		return msgTipoConvenio;
	}
	public void setMsgTipoConvenio(String msgTipoConvenio) {
		this.msgTipoConvenio = msgTipoConvenio;
	}
	public String getMsgEstadoConvenio() {
		return msgEstadoConvenio;
	}
	public void setMsgEstadoConvenio(String msgEstadoConvenio) {
		this.msgEstadoConvenio = msgEstadoConvenio;
	}
	public String getMsgFecDuracion() {
		return msgFecDuracion;
	}
	public void setMsgFecDuracion(String msgFecDuracion) {
		this.msgFecDuracion = msgFecDuracion;
	}
	public String getMsgFecInicio() {
		return msgFecInicio;
	}
	public void setMsgFecInicio(String msgFecInicio) {
		this.msgFecInicio = msgFecInicio;
	}
	public String getMsgOpcionFiltroCred() {
		return msgOpcionFiltroCred;
	}
	public void setMsgOpcionFiltroCred(String msgOpcionFiltroCred) {
		this.msgOpcionFiltroCred = msgOpcionFiltroCred;
	}
	public String getMsgFecSuscripcion() {
		return msgFecSuscripcion;
	}
	public void setMsgFecSuscripcion(String msgFecSuscripcion) {
		this.msgFecSuscripcion = msgFecSuscripcion;
	}
	public String getMsgConvSugerido() {
		return msgConvSugerido;
	}
	public void setMsgConvSugerido(String msgConvSugerido) {
		this.msgConvSugerido = msgConvSugerido;
	}
	//Modificado por cdelosrios, 04/12/2013
	/*public String getMsgTxtRangoFec() {
		return msgTxtRangoFec;
	}
	public void setMsgTxtRangoFec(String msgTxtRangoFec) {
		this.msgTxtRangoFec = msgTxtRangoFec;
	}*/
	//Fin Modificado por cdelosrios, 04/12/2013
	public String getMsgTxtNuevaAdenda() {
		return msgTxtNuevaAdenda;
	}
	public void setMsgTxtNuevaAdenda(String msgTxtNuevaAdenda) {
		this.msgTxtNuevaAdenda = msgTxtNuevaAdenda;
	}
	public String getMsgTxtConvEstructura() {
		return msgTxtConvEstructura;
	}
	public void setMsgTxtConvEstructura(String msgTxtConvEstructura) {
		this.msgTxtConvEstructura = msgTxtConvEstructura;
	}
	public String getStrHojaPlaneamiento() {
		return strHojaPlaneamiento;
	}
	public void setStrHojaPlaneamiento(String strHojaPlaneamiento) {
		this.strHojaPlaneamiento = strHojaPlaneamiento;
	}
	public String getRbTiempoDurac() {
		return rbTiempoDurac;
	}
	public void setRbTiempoDurac(String rbTiempoDurac) {
		this.rbTiempoDurac = rbTiempoDurac;
	}
	public String getStrTipoConvenio() {
		return strTipoConvenio;
	}
	public void setStrTipoConvenio(String strTipoConvenio) {
		this.strTipoConvenio = strTipoConvenio;
	}
	public Integer getIntCartaPresentacion() {
		return intCartaPresentacion;
	}
	public void setIntCartaPresentacion(Integer intCartaPresentacion) {
		this.intCartaPresentacion = intCartaPresentacion;
	}
	public Integer getIntConvenioSugerido() {
		return intConvenioSugerido;
	}
	public void setIntConvenioSugerido(Integer intConvenioSugerido) {
		this.intConvenioSugerido = intConvenioSugerido;
	}
	public Integer getIntAdendaSugerida() {
		return intAdendaSugerida;
	}
	public void setIntAdendaSugerida(Integer intAdendaSugerida) {
		this.intAdendaSugerida = intAdendaSugerida;
	}
	public Boolean getBlnHojaPlaneamiento() {
		return blnHojaPlaneamiento;
	}
	public void setBlnHojaPlaneamiento(Boolean blnHojaPlaneamiento) {
		this.blnHojaPlaneamiento = blnHojaPlaneamiento;
	}
	public Boolean getBlnHojaControl() {
		return blnHojaControl;
	}
	public void setBlnHojaControl(Boolean blnHojaControl) {
		this.blnHojaControl = blnHojaControl;
	}
	public Boolean getBlnConvenio() {
		return blnConvenio;
	}
	public void setBlnConvenio(Boolean blnConvenio) {
		this.blnConvenio = blnConvenio;
	}
	public Boolean getFormRangoFechaEnabled() {
		return formRangoFechaEnabled;
	}
	public void setFormRangoFechaEnabled(Boolean formRangoFechaEnabled) {
		this.formRangoFechaEnabled = formRangoFechaEnabled;
	}
	public Integer getSesionIntIdEmpresa() {
		return sesionIntIdEmpresa;
	}
	public void setSesionIntIdEmpresa(Integer sesionIntIdEmpresa) {
		this.sesionIntIdEmpresa = sesionIntIdEmpresa;
	}
	public ControlProcesoController getControlProcesoController() {
		return controlProcesoController;
	}
	public void setControlProcesoController(
			ControlProcesoController controlProcesoController) {
		this.controlProcesoController = controlProcesoController;
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public HojaPlaneamientoComp getHojaPlanCompBusqeda() {
		return hojaPlanCompBusqeda;
	}

	public void setHojaPlanCompBusqeda(HojaPlaneamientoComp hojaPlanCompBusqeda) {
		this.hojaPlanCompBusqeda = hojaPlanCompBusqeda;
	}

	/**************************************************************/
	/*  Nombre :  habilitarGrabarHojaPlan()           		 		*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                                                    	 		*/
	/*  Objetivo: Realizar el listado de la Hoja de Planeamiento 	*/
	/*                                           					*/
	/*  Retorno : Listado de Hoja de Planeamiento	    	 		*/
	/**************************************************************/
	public void habilitarGrabarHojaPlan(ActionEvent event) {
		setFormHojaPlaneamientoRendered(true);
		limpiarHojaPlaneamiento();
		strHojaPlaneamiento = Constante.MANTENIMIENTO_GRABAR;
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarHojaPlaneamiento()           		 		*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                                                    	 		*/
	/*  Objetivo: Realizar el listado de la Hoja de Planeamiento 	*/
	/*                                           					*/
	/*  Retorno : Listado de Hoja de Planeamiento	    	 		*/
	/**************************************************************/
	public void limpiarHojaPlaneamiento(){
		Adenda adenda = new Adenda();
		adenda.setId(new AdendaId());
		setBeanAdenda(adenda);
		archivo = new Archivo();
		tipoArchivo = new TipoArchivo();
		setStrTipoConvenio("");
		setFormTipoConvEnabled(false);
		setRbTiempoDurac("");
		setBtnBuscarEnabled(true);
		intCartaPresentacion = null;
		intConvenioSugerido = null;
		intAdendaSugerida = null;
		setChkCartaPresent(null);
		setChkConvSugerido(null);
		setChkAdendaSugerida(null);
		setUpldCartaPresent(true);
		setUpldConvSugerido(true);
		setUpldAdendaSugerida(true);
		strNombreCartaPresentacion=null;
		formCartaPresent = false;
		strNombreConvenioSugerido=null;
		formConvSugerido=false;
		strNombreAdendaSugerida=null;
		formAdendaSugerida=false;
		if(listaPoblacion!=null){
			listaPoblacion.clear();
		}
		if(listaPoblacionDet!=null){
			listaPoblacionDet.clear();
		}
		if(listaResumenPoblacionDet!=null){
			listaResumenPoblacionDet.clear();
		}
		if(listaCompetencia!=null){
			listaCompetencia.clear();
		}
		if(listaConvEstructuraDetComp!=null){
			listaConvEstructuraDetComp = new ArrayList<ConvenioEstructuraDetalleComp>();
		}
		//CGD-17.12.2013
		strHojaPlaneamiento = "";
		
	}
	
	public void cancelarGrabarHojaPlaneamiento(ActionEvent event){
		setFormHojaPlaneamientoRendered(false);
		limpiarHojaPlaneamiento();
	}
	
	/**************************************************************/
	/*  Nombre :  listarHojaPlaneamiento()           		 		*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                                                    	 		*/
	/*  Objetivo: Realizar el listado de la Hoja de Planeamiento 	*/
	/*                                           					*/
	/*  Retorno : Listado de Hoja de Planeamiento	    	 		*/
	/**************************************************************/
	public void listarHojaPlaneamiento(ActionEvent event) {
		log.info("--------------------Debugging HojaPlaneamientoController.listarHojaPlaneamiento------------------------");
		HojaPlaneamientoFacadeLocal facade = null;
		
		List<Juridica> listaJuridica = null;
		String csvPkPersona = null;
		Juridica juridica = null;
		//HojaPlaneamientoComp dto = null;
		List<HojaPlaneamientoComp> lista = null;
		
		try {
			facade = (HojaPlaneamientoFacadeLocal)EJBFactory.getLocal(HojaPlaneamientoFacadeLocal.class);
			HojaPlaneamientoComp o = new HojaPlaneamientoComp();
			o.setAdenda(new Adenda());
			o.getAdenda().setId(new AdendaId());
			o.setEstructuraDetalle(new EstructuraDetalle());
			o.getEstructuraDetalle().setId(new EstructuraDetalleId());
			o.getEstructuraDetalle().setEstructura(new Estructura());
			o.getEstructuraDetalle().getEstructura().setJuridica(new Juridica());
			//Modificado por cdelosrios, 04/12/2013
			/*if(intTipoConvenio!=null && intTipoConvenio!=0)
				o.getAdenda().getId().setIntItemConvenio(intTipoConvenio);
			if(intNivelEntidad!=null && intNivelEntidad!=0)
			o.getEstructuraDetalle().getId().setIntNivel(intNivelEntidad);*/
			if(intNroHoja!=null && intNroHoja!=0)
				o.getAdenda().getId().setIntConvenio(intNroHoja);
			if(intEstadoConv!=0)
				o.getAdenda().setIntParaEstadoHojaPlan(intEstadoConv);
			if(strNombreEntidad!=null){
				o.getEstructuraDetalle().getEstructura().getJuridica().setStrRazonSocial(strNombreEntidad);
			}
			if(intSucursalConv!=0)
				o.getAdenda().setIntSeguSucursalPk(intSucursalConv);
			/*if(intModalidad!=0)
				o.getEstructuraDetalle().setIntParaModalidadCod(intModalidad);
			if(intTipoSocio!=0)
				o.getEstructuraDetalle().setIntParaTipoSocioCod(intTipoSocio);
			o.getAdenda().setIntDonacion(chkDonacion==true?1:0);
			o.getAdenda().setDtInicio(getDaFecIni());
			o.getAdenda().setDtCese(getDaFecFin());
			o.setIntIndeterminado(chkIndeterminado==true?1:0);
			o.setIntDocAdjunto(chkDocAdjuntos==true?1:0);
			o.setIntCartaAutorizacion(chkCartaAutorizacion==true?1:0);*/
			//Fin modificado por cdelosrios, 04/12/2013
			listaEstructHojaPlaneamientoComp = facade.getListaHojaPlaneamientoCompDeBusquedaAdenda(o);
			//Agregado por cdelosrios, 05/12/2013
			if(strNombreEntidad!=null && !strNombreEntidad.trim().equals("")){
				if(listaEstructHojaPlaneamientoComp!=null && !listaEstructHojaPlaneamientoComp.isEmpty()){
					for (HojaPlaneamientoComp hojaPlanComp : listaEstructHojaPlaneamientoComp) {
						if(csvPkPersona == null)
							csvPkPersona = String.valueOf(hojaPlanComp.getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona()); 
						else	
							csvPkPersona = csvPkPersona + "," +hojaPlanComp.getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona();
					}
					juridica = o.getEstructuraDetalle().getEstructura().getJuridica();
					if(juridica.getStrRazonSocial()!= null && juridica.getStrRazonSocial().trim().equals("")){
						listaJuridica = personaFacade.getListaJuridicaPorInPk(csvPkPersona);
					}else{
						listaJuridica = personaFacade.getListaJuridicaPorInPkLikeRazon(csvPkPersona,juridica.getStrRazonSocial());
					}
					if(listaJuridica != null && listaJuridica.size()>0){
						lista = new ArrayList<HojaPlaneamientoComp>();
						for(Juridica jur : listaJuridica){
							for(HojaPlaneamientoComp dto : listaEstructHojaPlaneamientoComp){
								if(jur.getIntIdPersona().equals(dto.getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona())){
									dto.getEstructuraDetalle().getEstructura().setJuridica(jur);
									lista.add(dto);
								}
							}
						}
					}
					listaEstructHojaPlaneamientoComp = lista;
				}
			}
			//Fin agregado por cdelosrios, 05/12/2013
		} catch (BusinessException e) {
			log.error("Error BusinessException en listarHojaPlaneamiento ---> "+e);
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			log.error("Error EJBFactoryException en listarHojaPlaneamiento ---> "+e);
			e.printStackTrace();
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  enableDisableControls()           				*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Habilitar y desabilitar el panel de Fechas     	*/
	/*  Retorno : El panel de Rango de Fechas se habilita y 		*/
	/*            deshabilita  						     	 		*/
	/**************************************************************/
	//Modificado por cdelosrios, 04/12/2013
	public void enableDisableControls(ActionEvent event){
		log.info("----------------Debugging HojaPlaneamientoController.enableDisableControls-------------------");
		//setFormRangoFechaEnabled(getChkRangoFec()!=true);
		setUpldCartaPresent(getChkCartaPresent()!=true);
		setUpldConvSugerido(getChkConvSugerido()!=true);
		setUpldAdendaSugerida(getChkAdendaSugerida()!=true);
		if(getRbTiempoDurac()!=null){
			setFormFecFinDurac(getRbTiempoDurac().equals("1"));
			beanAdenda.setDtCese(getRbTiempoDurac().equals("2")?null:beanAdenda.getDtCese());
		}
		setBtnBuscarEnabled(getStrTipoConvenio()==null);
		/*if(getChkRangoFec()==false){
			setIntRanFecha(0);
			setDaFecIni(null);
			setDaFecFin(null);
		}*/
	}
	//Fin modificado por cdelosrios, 04/12/2013
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarEstructuras()           					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Habilitar y desabilitar el panel de Fechas     	*/
	/*                         						     	 		*/
	/**************************************************************/
	public void listarEstructuras(ActionEvent event){
		log.info("--------------------Debugging HojaPlaneamientoController.listarEstructuras------------------------");
		EstructuraFacadeLocal facade = null;
		try {
			Integer intTipoConvenio = new Integer(strTipoConvenio);
			log.info("intTipoConvenio: "+ intTipoConvenio);
			Integer intIdNivel = getIntCboNivelEntidad();
			facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			listaEstructuraComp = facade.getListaEstructuraCompPorTipoConvenio(intIdNivel, (intTipoConvenio==null?0:intTipoConvenio),strNombreEstructura, strNroRuc);
			System.out.println("tamaño lista ---> "+listaEstructuraComp.size());
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  isvalidaConvenioEstructDet()      				*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Habilitar y desabilitar el panel de Fechas     	*/
	/*                         						     	 		*/
	/**************************************************************/
	private Boolean isvalidaConvenioEstructDet(Integer intIdCodigo){
		Boolean valida = true;
		//String strIdNivel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmEstructHojaPlan:hiddenIdNivel");
		if(listaConvEstructuraDetComp!=null && listaConvEstructuraDetComp.size()>0){
			for(int i=0; i<listaConvEstructuraDetComp.size(); i++){
				ConvenioEstructuraDetalleComp domain = listaConvEstructuraDetComp.get(i);
				if(!domain.getEstructura().getId().getIntCodigo().equals(intIdCodigo)){
					valida = false;
					setMsgTxtConvEstructura("No puede ingresar una Estructura diferente a la ya ingresada anteriormente");
				}else{
					setMsgTxtConvEstructura("");
				}
			}
		}
		return valida;
	}
	
	public void listarEstructuraDet(ActionEvent event){
		//listaConvEstructuraDetComp = new ArrayList<ConvenioEstructuraDetalleComp>();
		ConvenioEstructuraDetalleComp convenioEstructuraDetalleComp = null;
		try{
			if(listaEstructuraComp!=null){
				for(EstructuraComp estructuraComp : listaEstructuraComp){
					if(estructuraComp.getChkEstructura()!=null && 
							estructuraComp.getChkEstructura()==true){
						convenioEstructuraDetalleComp = new ConvenioEstructuraDetalleComp();
						convenioEstructuraDetalleComp.setEstructura(new Estructura());
						convenioEstructuraDetalleComp.setEstructuraDetalle(new EstructuraDetalle());
						convenioEstructuraDetalleComp.setAdenda(new Adenda());
						convenioEstructuraDetalleComp.setEstructura(estructuraComp.getEstructura());
						convenioEstructuraDetalleComp.setEstructuraDetalle(estructuraComp.getEstructuraDetalle());
						listaConvEstructuraDetComp.add(convenioEstructuraDetalleComp);
					}
				}
			}
		}catch(Exception e){
			log.error("error: "+e);
			e.printStackTrace();
		}
	}
	/*public void listarEstructuraDet(ActionEvent event){
		log.info("--------------------Debugging HojaPlaneamientoController.listarEstructuraDet------------------------");
		EstructuraFacadeLocal facade = null;
		ConvenioEstructuraDetalleComp convenioEstructuraDetalleComp = null;
		//List<ConvenioEstructuraDetalleComp> listaConvenioEstrucDetComp = new ArrayList<ConvenioEstructuraDetalleComp>();
		String strIdNivel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmEstructHojaPlan:hiddenIdNivel");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmEstructHojaPlan:hiddenIdCodigo");
		String strIdCaso = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmEstructHojaPlan:hiddenIdCaso");
		//String strIdItemCaso = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmEstructHojaPlan:hiddenIdItemCaso");
		
		if(isvalidaConvenioEstructDet(Integer.parseInt(strIdCodigo))==false){
			log.info("Ha ocurrido un error");
			return;
		}
		try {
			convenioEstructuraDetalleComp = new ConvenioEstructuraDetalleComp();
			convenioEstructuraDetalleComp.setConvenioEstructuraDetalle(new ConvenioEstructuraDetalle());
			convenioEstructuraDetalleComp.setEstructura(new Estructura());
			convenioEstructuraDetalleComp.getEstructura().setId(new EstructuraId());
			convenioEstructuraDetalleComp.setEstructuraDetalle(new EstructuraDetalle());
			convenioEstructuraDetalleComp.getEstructuraDetalle().setId(new EstructuraDetalleId());
			convenioEstructuraDetalleComp.setAdenda(new Adenda());
			convenioEstructuraDetalleComp.getEstructura().getId().setIntNivel(new Integer(strIdNivel));
			convenioEstructuraDetalleComp.getEstructura().getId().setIntCodigo(new Integer(strIdCodigo));
			convenioEstructuraDetalleComp.getEstructuraDetalle().getId().setIntCaso(new Integer(strIdCaso));
			//convenioEstructuraDetalleComp.getEstructuraDetalle().getId().setIntItemCaso(new Integer(strIdItemCaso));
			facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			listaConvEstructuraDetComp = facade.getListaConvenioEstructuraDetPorEstructuraDet(convenioEstructuraDetalleComp);
			setListaConvEstructuraDetComp(listaConvEstructuraDetComp);
			
			System.out.println("getListaConvEstructuraDetComp().size(): "+ getListaConvEstructuraDetComp().size());
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}*/
	
	/**************************************************************/
	/*  Nombre :  agregarPoblacion()        		      	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                                                    	 */
	/*  Objetivo: Realizar el listado del Centro de Trabajo  */
	/*            Poblacional y el detalle del centro de 	 */
	/*            trabajo                                  	 */
	/*  Retorno : Listados de ambas poblaciones     	 	 */
	/**************************************************************/
	public void agregarPoblacion(ActionEvent event) {
		log.info("---------------------Debugging HojaPlaneamientoController.agregarPoblacion-----------------------------");
		listarPoblacion();
	}
	
	/**************************************************************/
	/*  Nombre :  listarPoblacion()           		      	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                         dato1      descripcion     	 */ 
	/*                                                    	 */
	/*  Objetivo: Realizar el listado del Estudio de Mercado */
	/*            Poblacional.                               */
	/*  Retorno : Listado de Poblaciones                     */
	/**************************************************************/
	public void listarPoblacion(){
		log.info("-----------------Debugging listarPoblacion()---------------------");
		Poblacion poblacion = null;
		//List<PoblacionDetalle> listaPoblacionDet = new ArrayList<PoblacionDetalle>();
		try{
			poblacion = new Poblacion();
			poblacion.setListaPoblacionDetalle(listarPoblacionDet());
			listaPoblacion.add(poblacion);
		}catch(Exception e){
			log.error(e);
		}
	}
	
	/**************************************************************/
	/*  Nombre :  listarPoblacionDet()         		      	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                                                    	 */
	/*  Objetivo: Realizar el listado del Centro de Trabajo  */
	/*            Poblacional.                               */
	/*  Retorno : Listado de Centro de Trabajo vacía    	 */
	/**************************************************************/
	public List<PoblacionDetalle> listarPoblacionDet(){
		log.info("-----------------Debugging listarPoblacion()---------------------");
		List<PoblacionDetalle> listaTemp = null;
		PoblacionDetalle poblacionDetalleN1 = null;
		PoblacionDetalle poblacionDetalleN2 = null;
		PoblacionDetalle poblacionDetalleC = null;
		PoblacionDetalleId poblacionDetalleIdN1 = null;
		PoblacionDetalleId poblacionDetalleIdN2 = null;
		PoblacionDetalleId poblacionDetalleIdC = null;
		try{
			poblacionDetalleIdN1 = new PoblacionDetalleId(null, 1, 1);
			poblacionDetalleIdN2 = new PoblacionDetalleId(null, 1, 2);
			poblacionDetalleIdC = new PoblacionDetalleId(null, 2, 0);
			poblacionDetalleN1 = new PoblacionDetalle(poblacionDetalleIdN1, null);
			poblacionDetalleN2 = new PoblacionDetalle(poblacionDetalleIdN2, null);
			poblacionDetalleC  = new PoblacionDetalle(poblacionDetalleIdC,  null);
			listaTemp = new ArrayList<PoblacionDetalle>();
			listaTemp.add(poblacionDetalleN1);
			listaTemp.add(poblacionDetalleN2);
			listaTemp.add(poblacionDetalleC);
		}catch(Exception e){
			log.error(e);
		}
		return listaTemp;
	}
	
	/**************************************************************/
	/*  Nombre :  removePoblacion()         		      	 	*/
	/*                                                    	 	*/
	/*  Parametros. :  event       descripcion            	 	*/
	/*                                                    	 	*/
	/*  Objetivo: Quitar un registro del listado de Poblacion	*/
	/*            				                               	*/
	/*  Retorno : Listado de Población sin el registro quitado 	*/
	/**************************************************************/
	public void removePoblacion(ActionEvent event){
		log.info("--------------------HojaPlaneamientoController.removePoblacion--------------------");
		String rowKey = getRequestParameter("rowKeyPoblacion");
		Poblacion poblacionTmp = null;
		try{
			if(listaPoblacion!=null){
				for(int i=0; i<listaPoblacion.size(); i++){
					if(Integer.parseInt(rowKey)==i){
						Poblacion poblacion = listaPoblacion.get(i);
						if(poblacion.getId()==null)poblacion.setId(new PoblacionId());
						if(poblacion.getId().getIntItemPoblacion() !=null){
							poblacionTmp = listaPoblacion.get(i);
							poblacionTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						}
						listaPoblacion.remove(i);
						break;
					}
				}
				if(poblacionTmp!=null){
					listaPoblacion.add(poblacionTmp);
				}
			}
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	public void calcularPoblacion(ActionEvent event) {
		log.info("---------------------Debugging HojaPlaneamientoController.calcularPoblacion-------------------------");
		ArrayList<PoblacionDetalle> lstPoblacionDetalle	= new ArrayList<PoblacionDetalle>();
		//PoblacionDetalle poblacionDetalle = null;
		int sumPadNombA=0;
		int sumPadNombC=0;
		int sumPadCont =0;
		
		log.info("listaPoblacionDet.size(): "+ listaPoblacion.size());
	    for(Poblacion poblacion : listaPoblacion){
	    	for(PoblacionDetalle poblacionDetalle : poblacion.getListaPoblacionDetalle()){
	    		if(poblacionDetalle.getId().getIntTipoSocio().equals(1)){
		    		sumPadNombA += (poblacionDetalle.getIntPadron()==null?0:poblacionDetalle.getIntPadron());
		    	}else if(poblacionDetalle.getId().getIntTipoSocio().equals(2)){
		    		sumPadNombC += (poblacionDetalle.getIntPadron()==null?0:poblacionDetalle.getIntPadron());
		    	}else{
		    		sumPadCont += (poblacionDetalle.getIntPadron()==null?0:poblacionDetalle.getIntPadron());
		    	}
	    	}
	    }
	    
	    PoblacionDetalle poblacionDetalle1 = new PoblacionDetalle();
	    poblacionDetalle1.setId(new PoblacionDetalleId());
	    poblacionDetalle1.getId().setIntTipoTrabajador(1);
	    poblacionDetalle1.getId().setIntTipoSocio(1);
	    poblacionDetalle1.setIntPadron(sumPadNombA);
	    PoblacionDetalle poblacionDetalle2 = new PoblacionDetalle();
	    poblacionDetalle2.setId(new PoblacionDetalleId());
	    poblacionDetalle2.getId().setIntTipoTrabajador(1);
	    poblacionDetalle2.getId().setIntTipoSocio(2);
	    poblacionDetalle2.setIntPadron(sumPadNombC);
	    PoblacionDetalle poblacionDetalle3 = new PoblacionDetalle();
	    poblacionDetalle3.setId(new PoblacionDetalleId());
	    poblacionDetalle3.getId().setIntTipoTrabajador(2);
	    poblacionDetalle3.getId().setIntTipoSocio(0);
	    poblacionDetalle3.setIntPadron(sumPadCont);
	    lstPoblacionDetalle.add(poblacionDetalle1);
	    lstPoblacionDetalle.add(poblacionDetalle2);
	    lstPoblacionDetalle.add(poblacionDetalle3);
	    
	    listaResumenPoblacionDet = lstPoblacionDetalle;
	}
	
	public void cancelarPoblacion(ActionEvent event) {
		log.info("---------------------Debugging HojaPlaneamientoController.cancelarPoblacion-----------------------------");
		if(listaPoblacion!=null && listaPoblacion.size()>0) listaPoblacion.clear();
		if(listaPoblacionDet!=null && listaPoblacionDet.size()>0) listaPoblacionDet.clear();
	}
	
	public void addCompetencia(ActionEvent event){
		log.info("-----------------Debugging agregarCompetencia()---------------------");
		Competencia competencia = null;
		try{
			competencia = new Competencia();
			listaCompetencia.add(competencia);
			log.info("listaPoblacion.size(): " + listaPoblacion.size());
		}catch(Exception e){
			log.error(e);
		}
	}
	
	public void removeCompetencia(ActionEvent event){
		log.info("-------------------------------------CreditoController.removeRangoInteres-------------------------------------");
		String rowKey = getRequestParameter("rowKeyCompetencia");
		Competencia competenciaTmp = null;
		if(listaCompetencia!=null){
			for(int i=0; i<listaCompetencia.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Competencia competencia = listaCompetencia.get(i);
					if(competencia.getId()==null)competencia.setId(new CompetenciaId());
					if(competencia.getId().getIntItemCompetencia()!=null){
						competenciaTmp = listaCompetencia.get(i);
						competenciaTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaCompetencia.remove(i);
					break;
				}
			}
			if(competenciaTmp!=null){
				listaCompetencia.add(competenciaTmp);
			}
		}
	}
	
	private Boolean isValidoHojaPlaneamiento(Adenda beanAdenda) throws ParseException{
		Boolean validAdenda = true;
		String today = Constante.sdf.format(new Date());
		if (strTipoConvenio.equals("2")) {
			if(listaConvEstructuraDetComp!=null && listaConvEstructuraDetComp.size()==1){
				for(ConvenioEstructuraDetalleComp convenioEstructuraDetalleComp:listaConvEstructuraDetComp){
					if(convenioEstructuraDetalleComp.getAdenda().getDtCese()==null){
						setMsgTxtNuevaAdenda("El Convenio seleccionado tiene Fecha Indeterminada, no se puede realizar una Nueva Adenda.");
					}
					else{
						if(today.equals(Constante.sdf.format(convenioEstructuraDetalleComp.getAdenda().getDtCese()))){
							validAdenda = true;
							setMsgTxtNuevaAdenda("");
						}
						else if(Constante.sdf.parse(today).after(convenioEstructuraDetalleComp.getAdenda().getDtCese())){
							setMsgTxtNuevaAdenda("La Fecha del Convenio seleccionado ya venció. Por favor, genere un Nuevo Convenio.");
							validAdenda = false;
						}
					}
				}
			}
		} else {
			setMsgTxtNuevaAdenda("");
		}
		
		if(beanAdenda.getDtInicio()==null){
			setMsgFecInicio("Debe ingresar una fecha de Inicio");
			validAdenda = false;
		}else{
			setMsgFecInicio("");
		}
		if(beanAdenda.getIntOpcionFiltroCredito()==null){
			setMsgOpcionFiltroCred("Debe elegir una Opción para el filtro del crédito.");
			validAdenda = false;
		}else{
			setMsgOpcionFiltroCred("");
		}
		if(beanAdenda.getDtSuscripcion()==null){
			setMsgFecSuscripcion("Debe Ingresar una Fecha de Suscripción");
			validAdenda = false;
		}else{
			setMsgFecSuscripcion("");
		}/*
		if(beanAdenda.getIntParaTipoRetencionCod()==null){
			setMsgTipoRetencion("Debe Ingresar un tipo de retención");
			validAdenda = false;
		}else{
			setMsgTipoRetencion("");
		}
		if(beanAdenda.getBdRetencion()==null){
			setMsgRetencPlanilla("Debe Ingresar un monto para la Retención de Planilla");
			validAdenda = false;
		}else{
			setMsgRetencPlanilla("");
		}*/
	    return validAdenda;
	}
	
	public void irModificarHojaPlaneamiento(ActionEvent event) throws ParseException{
    	log.info("-------------------Debugging HojaPlaneamientoController.irModificarHojaPlaneamiento--------------------");
    	ConvenioFacadeLocal convenioFacade = null;
    	controlProcesoController = (ControlProcesoController)getSessionBean("controlProcesoController");
    	//HojaPlaneamientoFacadeLocal adendaFacade = null;
    	//String strIdConvenio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmHPModalPanel:hiddenIdConvenio");
		//String strIdAmpliacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmHPModalPanel:hiddenIdItemConvenio");
		String strIdConvenio = intIdConvenioHP.toString();
		String strIdAmpliacion = intIdItemConvenioHP.toString();

		Poblacion poblacion = null;
		List<PoblacionDetalle> listaPoblacionDetalle = new ArrayList<PoblacionDetalle>();
		AdendaId adendaId = new AdendaId();
		adendaId.setIntConvenio(strIdConvenio!=null ? new Integer(strIdConvenio): controlProcesoController.getIntIdConvenio());
		adendaId.setIntItemConvenio(strIdAmpliacion!=null ? (new Integer(strIdAmpliacion)):controlProcesoController.getIntIdItemConvenio());
		Archivo archivo = null;
		
		log.info("controlProcesoController.getIntIdConvenio(): "+ controlProcesoController.getIntIdConvenio());
		log.info("strIdConvenio: "+ strIdConvenio);
		
		try {
    		//if(adendaId.getIntConvenio() != null && !adendaId.getIntConvenio().equals("")){
    			convenioFacade = (ConvenioFacadeLocal)EJBFactory.getLocal(ConvenioFacadeLocal.class);
				beanAdenda   = convenioFacade.getAdendaPorIdAdenda(adendaId);
				
				if(beanAdenda.getListaPoblacion()!=null && beanAdenda.getListaPoblacion().size()>0){
					listaPoblacion = beanAdenda.getListaPoblacion();
					for(int i=0;i<listaPoblacion.size(); i++){
						poblacion = listaPoblacion.get(i);
						listaPoblacionDetalle = poblacion.getListaPoblacionDetalle();
					}
					listaPoblacionDet = listaPoblacionDetalle;
				}
				else{
					listaPoblacion.clear();
				}
				if(beanAdenda.getListaConvenioDetalleComp()!=null && beanAdenda.getListaConvenioDetalleComp().size()>0){
					listaConvEstructuraDetComp = beanAdenda.getListaConvenioDetalleComp();
					int i=0;
					for(ConvenioEstructuraDetalleComp convenioEstructuraDetalleComp: listaConvEstructuraDetComp){
						log.info("ca:"+convenioEstructuraDetalleComp.getConvenioEstructuraDetalle().getId().getIntItemCaso());
						log.info("es:"+convenioEstructuraDetalleComp.getConvenioEstructuraDetalle().getIntParaEstadoCod());
						if(convenioEstructuraDetalleComp.getConvenioEstructuraDetalle().getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO))
							i++;					
					}
					intCantFilasActivas = i;
					
				}else{
					log.info("--listaConvEstructuraDetComp : null");
				}
				if(beanAdenda.getListaCompetencia()!=null && beanAdenda.getListaCompetencia().size()>0){
					listaCompetencia = beanAdenda.getListaCompetencia();
				}else{
					listaCompetencia.clear();
				}
				if(listaResumenPoblacionDet!=null && listaResumenPoblacionDet.size()>0){
					listaResumenPoblacionDet.clear();
				}
				/*adendaFacade = (HojaPlaneamientoFacadeLocal)EJBFactory.getLocal(HojaPlaneamientoFacadeLocal.class);
				beanAdenda = adendaFacade.getAdendaPorIdAdenda(adendaId);*/
				beanAdenda.setBoCartaAutorizacion(beanAdenda.getIntCartaAutorizacion()==1);
				beanAdenda.setBoDonacion(beanAdenda.getIntDonacion()==1);
				setStrTipoConvenio(beanAdenda.getId().getIntItemConvenio()==0?"1":"2");
				setRbTiempoDurac(beanAdenda.getDtCese()!=null?"1":"2");
				setFormFecFinDurac(beanAdenda.getDtCese()!=null);
				setBtnBuscarEnabled(beanAdenda.getId().getIntItemConvenio()==null);
				if(beanAdenda.getListaAdjunto()!=null && beanAdenda.getListaAdjunto().size()>0){
					for(Adjunto adjunto : beanAdenda.getListaAdjunto()){
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPRESENTACION)){
							archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
							archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							if(archivo!=null){
								setStrNombreCartaPresentacion(archivo.getStrNombrearchivo());
								setChkCartaPresent(true);
								setFormCartaPresent(true);
								beanAdenda.setArchivoDocumentoCartaPresent(archivo);
							}
						}
						if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO)){
							archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
							archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							if(archivo!=null){
								setStrNombreConvenioSugerido(archivo.getStrNombrearchivo());
								setChkConvSugerido(true);
								setFormConvSugerido(true);
								beanAdenda.setArchivoDocumentoConvSugerido(archivo);
							}
						}
						if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA)){
							archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
							archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							if(archivo!=null){
								setStrNombreAdendaSugerida(archivo.getStrNombrearchivo());
								setChkAdendaSugerida(true);
								setFormAdendaSugerida(true);
								beanAdenda.setArchivoDocumentoAdendaSugerida(archivo);
							}
						}
					}
					/*for(Adjunto adjunto : beanAdenda.getListaAdjunto()){
						setChkConvSugerido(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO));
					}
					for(Adjunto adjunto : beanAdenda.getListaAdjunto()){
						setChkAdendaSugerida(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA));
					}*/
				}else{
					setStrNombreCartaPresentacion("");
					setChkCartaPresent(false);
					setFormCartaPresent(false);
					
					setStrNombreConvenioSugerido("");
					setChkConvSugerido(false);
					setFormConvSugerido(false);
					
					setStrNombreAdendaSugerida("");
					setChkAdendaSugerida(false);
					setFormAdendaSugerida(false);
				}
				setStrHojaPlaneamiento(beanAdenda.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) || 
						beanAdenda.getIntParaEstadoHojaPlan().equals(Constante.PARAM_T_ESTADODOCUMENTO_CONCLUIDO)?
								Constante.MANTENIMIENTO_ELIMINAR:Constante.MANTENIMIENTO_MODIFICAR);
				setFormTipoConvEnabled(true);
				setFormHojaPlaneamientoRendered(true);
    		//}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void irModificarHojaPlaneamientoView(ActionEvent event) throws ParseException{
    	log.info("-------------------Debugging HojaPlaneamientoController.irModificarHojaPlaneamiento--------------------");
    	
		System.out.println("hojaPlanCompBusqeda ---> "+hojaPlanCompBusqeda);
		
    	ConvenioFacadeLocal convenioFacade = null;
    	controlProcesoController = (ControlProcesoController)getSessionBean("controlProcesoController");
    	
    	// CGD - 18.12.2013
    	String strIdConvenio = intIdConvenioHP.toString();
		String strIdAmpliacion = intIdItemConvenioHP.toString();	
		
		Poblacion poblacion = null;
		List<PoblacionDetalle> listaPoblacionDetalle = new ArrayList<PoblacionDetalle>();
		AdendaId adendaId = new AdendaId();
		adendaId.setIntConvenio(strIdConvenio!=null ? new Integer(strIdConvenio): controlProcesoController.getIntIdConvenio());
		adendaId.setIntItemConvenio(strIdAmpliacion!=null ? (new Integer(strIdAmpliacion)):controlProcesoController.getIntIdItemConvenio());
		Archivo archivo = null;
		
		log.info("controlProcesoController.getIntIdConvenio(): "+ controlProcesoController.getIntIdConvenio());
		log.info("strIdConvenio: "+ strIdConvenio);
		
		try {
    		//if(adendaId.getIntConvenio() != null && !adendaId.getIntConvenio().equals("")){
    			convenioFacade = (ConvenioFacadeLocal)EJBFactory.getLocal(ConvenioFacadeLocal.class);
				beanAdenda   = convenioFacade.getAdendaPorIdAdenda(adendaId);
				
				if(beanAdenda.getListaPoblacion()!=null && beanAdenda.getListaPoblacion().size()>0){
					listaPoblacion = beanAdenda.getListaPoblacion();
					for(int i=0;i<listaPoblacion.size(); i++){
						poblacion = listaPoblacion.get(i);
						listaPoblacionDetalle = poblacion.getListaPoblacionDetalle();
					}
					listaPoblacionDet = listaPoblacionDetalle;
				}
				else{
					listaPoblacion.clear();
				}
				if(beanAdenda.getListaConvenioDetalleComp()!=null && beanAdenda.getListaConvenioDetalleComp().size()>0){
					listaConvEstructuraDetComp = beanAdenda.getListaConvenioDetalleComp();
					int i=0;
					for(ConvenioEstructuraDetalleComp convenioEstructuraDetalleComp: listaConvEstructuraDetComp){
						log.info("ca:"+convenioEstructuraDetalleComp.getConvenioEstructuraDetalle().getId().getIntItemCaso());
						log.info("es:"+convenioEstructuraDetalleComp.getConvenioEstructuraDetalle().getIntParaEstadoCod());
						if(convenioEstructuraDetalleComp.getConvenioEstructuraDetalle().getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO))
							i++;					
					}
					intCantFilasActivas = i;
					
				}else{
					log.info("--listaConvEstructuraDetComp : null");
				}
				if(beanAdenda.getListaCompetencia()!=null && beanAdenda.getListaCompetencia().size()>0){
					listaCompetencia = beanAdenda.getListaCompetencia();
				}else{
					listaCompetencia.clear();
				}
				if(listaResumenPoblacionDet!=null && listaResumenPoblacionDet.size()>0){
					listaResumenPoblacionDet.clear();
				}
				/*adendaFacade = (HojaPlaneamientoFacadeLocal)EJBFactory.getLocal(HojaPlaneamientoFacadeLocal.class);
				beanAdenda = adendaFacade.getAdendaPorIdAdenda(adendaId);*/
				beanAdenda.setBoCartaAutorizacion(beanAdenda.getIntCartaAutorizacion()==1);
				beanAdenda.setBoDonacion(beanAdenda.getIntDonacion()==1);
				setStrTipoConvenio(beanAdenda.getId().getIntItemConvenio()==0?"1":"2");
				setRbTiempoDurac(beanAdenda.getDtCese()!=null?"1":"2");
				setFormFecFinDurac(beanAdenda.getDtCese()!=null);
				setBtnBuscarEnabled(beanAdenda.getId().getIntItemConvenio()==null);
				if(beanAdenda.getListaAdjunto()!=null && beanAdenda.getListaAdjunto().size()>0){
					for(Adjunto adjunto : beanAdenda.getListaAdjunto()){
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPRESENTACION)){
							archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
							archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							if(archivo!=null){
								setStrNombreCartaPresentacion(archivo.getStrNombrearchivo());
								setChkCartaPresent(true);
								setFormCartaPresent(true);
								beanAdenda.setArchivoDocumentoCartaPresent(archivo);
							}
						}
						if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO)){
							archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
							archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							if(archivo!=null){
								setStrNombreConvenioSugerido(archivo.getStrNombrearchivo());
								setChkConvSugerido(true);
								setFormConvSugerido(true);
								beanAdenda.setArchivoDocumentoConvSugerido(archivo);
							}
						}
						if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA)){
							archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
							archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							if(archivo!=null){
								setStrNombreAdendaSugerida(archivo.getStrNombrearchivo());
								setChkAdendaSugerida(true);
								setFormAdendaSugerida(true);
								beanAdenda.setArchivoDocumentoAdendaSugerida(archivo);
							}
						}
					}
					/*for(Adjunto adjunto : beanAdenda.getListaAdjunto()){
						setChkConvSugerido(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO));
					}
					for(Adjunto adjunto : beanAdenda.getListaAdjunto()){
						setChkAdendaSugerida(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA));
					}*/
				}else{
					setStrNombreCartaPresentacion("");
					setChkCartaPresent(false);
					setFormCartaPresent(false);
					
					setStrNombreConvenioSugerido("");
					setChkConvSugerido(false);
					setFormConvSugerido(false);
					
					setStrNombreAdendaSugerida("");
					setChkAdendaSugerida(false);
					setFormAdendaSugerida(false);
				}
				setStrHojaPlaneamiento(beanAdenda.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) || 
						beanAdenda.getIntParaEstadoHojaPlan().equals(Constante.PARAM_T_ESTADODOCUMENTO_CONCLUIDO)?
								Constante.MANTENIMIENTO_ELIMINAR:Constante.MANTENIMIENTO_MODIFICAR);
				setFormTipoConvEnabled(true);
				setFormHojaPlaneamientoRendered(true);
    		//}
		} catch (EJBFactoryException e) {
			log.error("Error EJBFactoryException en irModificarHojaPlaneamiento ---> "+e);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			log.error("Error NumberFormatException en irModificarHojaPlaneamiento ---> "+e);
			e.printStackTrace();
		} catch (BusinessException e) {
			log.error("Error BusinessException en irModificarHojaPlaneamiento ---> "+e);
			e.printStackTrace();
		}
	}
	
	public void grabarHojaPlaneamiento(ActionEvent event) throws ParseException{
		ConvenioFacadeLocal facadeConvenio = null;
		Adjunto adjuntoCartaPresent = null;
		Adjunto adjuntoConvenioSugerido = null;
		Adjunto adjuntoAdendaSugerida = null;
		List<Adjunto> listaAdjunto = new ArrayList<Adjunto>();
	    if(isValidoHojaPlaneamiento(beanAdenda) == false){
	    	log.info("Datos de Convenio no válidos. Se aborta el proceso de grabación de Convenio.");
	    	return;
	    }
	    beanAdenda.setId(new AdendaId());
	    
	    beanAdenda.setIntPersEmpresaPk(sesionIntIdEmpresa);//beanSesion.getIntIdEmpresa()
	    
		if(listaConvEstructuraDetComp!=null && listaConvEstructuraDetComp.size()>0){
			beanAdenda.setListaConvenioDetalleComp(listaConvEstructuraDetComp);
			if(strTipoConvenio.equals("2")){
				beanAdenda.getId().setIntConvenio(listaConvEstructuraDetComp.get(0).getConvenioEstructuraDetalle().getId().getIntConvenio());
			}
		}
		if(listaPoblacion!=null && listaPoblacion.size()>0){
			beanAdenda.setListaPoblacion(listaPoblacion);
		}
		if(listaCompetencia!=null && listaCompetencia.size()>0){
			beanAdenda.setListaCompetencia(listaCompetencia);
		}
		
		beanAdenda.setIntCartaAutorizacion(beanAdenda.getBoCartaAutorizacion()==true?1:0);
		beanAdenda.setIntDonacion(beanAdenda.getBoDonacion()==true?1:0);
		beanAdenda.getId().setIntItemConvenio(new Integer(strTipoConvenio));
		beanAdenda.setIntParaEstadoHojaPlan(Constante.PARAM_T_ESTADODOCUMENTO_PENDIENTE);
		
		if(intCartaPresentacion!=null){
			adjuntoCartaPresent = new Adjunto();
			adjuntoCartaPresent.setId(new AdjuntoId());
			adjuntoCartaPresent.setIntParaTipoArchivoCod(intCartaPresentacion!=null?intCartaPresentacion:null);
			listaAdjunto.add(adjuntoCartaPresent);
		}
		if(intConvenioSugerido!=null){
			adjuntoConvenioSugerido = new Adjunto();
			adjuntoConvenioSugerido.setId(new AdjuntoId());
			adjuntoConvenioSugerido.setIntParaTipoArchivoCod(intConvenioSugerido!=null?intConvenioSugerido:null);
			listaAdjunto.add(adjuntoConvenioSugerido);
		}
		if(intAdendaSugerida!=null){
			adjuntoAdendaSugerida = new Adjunto();
			adjuntoAdendaSugerida.setId(new AdjuntoId());
			adjuntoAdendaSugerida.setIntParaTipoArchivoCod(intAdendaSugerida!=null?intAdendaSugerida:null);
			listaAdjunto.add(adjuntoAdendaSugerida);
		}
		listaAdendaAdjunto = listaAdjunto;
		if(listaAdjunto!=null && listaAdjunto.size()>0){
			beanAdenda.setListaAdjunto(listaAdendaAdjunto);
		}
		
		try {
			facadeConvenio = (ConvenioFacadeLocal)EJBFactory.getLocal(ConvenioFacadeLocal.class);
			beanAdenda=facadeConvenio.grabarAdenda(beanAdenda);
			if(beanAdenda.getListaAdjunto()!=null && beanAdenda.getListaAdjunto().size()>0){
				listaArchivo = facadeConvenio.grabarListaArchivoDeAdjunto(beanAdenda.getListaAdjunto(), strCartaPresentacion, strConvenioSugerido, strAdendaSugerida,beanAdenda.getId());
				renombrarArchivo(listaArchivo);
			}
			limpiarHojaPlaneamiento();
			listarHojaPlaneamiento(event);
			formHojaPlaneamientoRendered = false;
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void modificarHojaPlaneamiento(ActionEvent event) throws ParseException{
		ConvenioFacadeLocal facadeConvenio = null;
		List<Adjunto> listaAdjunto = new ArrayList<Adjunto>();
		
	    if(isValidoHojaPlaneamiento(beanAdenda) == false){
	    	log.info("Datos de Convenio no válidos. Se aborta el proceso de grabación de Convenio.");
	    	return;
	    }
	    beanAdenda.setIntPersEmpresaPk(sesionIntIdEmpresa);//beanSesion.getIntIdEmpresa()
	    
		if(listaConvEstructuraDetComp!=null && listaConvEstructuraDetComp.size()>0){
			beanAdenda.setListaConvenioDetalleComp(listaConvEstructuraDetComp);
		}
		if(listaPoblacion!=null && listaPoblacion.size()>0){
			beanAdenda.setListaPoblacion(listaPoblacion);
		}
		if(listaCompetencia!=null && listaCompetencia.size()>0){
			beanAdenda.setListaCompetencia(listaCompetencia);
		}
		
		beanAdenda.setIntCartaAutorizacion(beanAdenda.getBoCartaAutorizacion()==true?1:0);
		beanAdenda.setIntDonacion(beanAdenda.getBoDonacion()==true?1:0);
		if(beanAdenda.getIntParaEstadoHojaPlan().equals(Constante.PARAM_T_ESTADODOCUMENTO_CONCLUIDO)){
			beanAdenda.setTsFechaHojaCulminado(new Timestamp(new Date().getTime()));
		}
		log.info("beanAdenda.getIntParaEstadoHojaPlan(): " + beanAdenda.getIntParaEstadoHojaPlan());
		log.info("beanAdenda.getTsFechaHojaCulminado():  " + beanAdenda.getTsFechaHojaCulminado());
		
		// CGD - 17.12.2013
		/*if(strCartaPresentacion!=null){
			Adjunto adjuntoCartaPresent = null;
			adjuntoCartaPresent = new Adjunto();
			adjuntoCartaPresent.setId(new AdjuntoId());
			adjuntoCartaPresent.setIntParaTipoArchivoCod(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPRESENTACION);
			listaAdjunto.add(adjuntoCartaPresent);
		}
		if(strConvenioSugerido!=null){
			Adjunto adjuntoConvenioSugerido = null;
			adjuntoConvenioSugerido = new Adjunto();
			adjuntoConvenioSugerido.setId(new AdjuntoId());
			adjuntoConvenioSugerido.setIntParaTipoArchivoCod(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO);
			listaAdjunto.add(adjuntoConvenioSugerido);
		}
		if(strAdendaSugerida!=null){
			Adjunto adjuntoAdendaSugerida = null;
			adjuntoAdendaSugerida = new Adjunto();
			adjuntoAdendaSugerida.setId(new AdjuntoId());
			adjuntoAdendaSugerida.setIntParaTipoArchivoCod(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA);
			listaAdjunto.add(adjuntoAdendaSugerida);
		}
		listaAdendaAdjunto = listaAdjunto;
		if(listaAdjunto!=null && listaAdjunto.size()>0){
			beanAdenda.setListaAdjunto(listaAdendaAdjunto);
		}*/

		try {
			facadeConvenio = (ConvenioFacadeLocal)EJBFactory.getLocal(ConvenioFacadeLocal.class);
			beanAdenda = facadeConvenio.modificarAdenda(beanAdenda);
			//if(beanAdenda.getListaAdjunto()!= null && beanAdenda.getListaAdjunto().size()>0){
			/*if(listaAdjunto != null && listaAdjunto.size()>0){
				log.error("strCartaPresentacion ----> "+strCartaPresentacion);
				log.error("strConvenioSugerido ----> "+strConvenioSugerido);
				log.error("strAdendaSugerida ----> "+strAdendaSugerida);
				//beanAdenda.getListaAdjunto()
				listaArchivo = facadeConvenio.grabarListaArchivoDeAdjunto(listaAdjunto, strCartaPresentacion, strConvenioSugerido, strAdendaSugerida,beanAdenda.getId());
				renombrarArchivo(listaArchivo);
			}*/
			limpiarHojaPlaneamiento();
			listarHojaPlaneamiento(event);
			formHojaPlaneamientoRendered = false;
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void adjuntarArchivo(UploadEvent event) throws Exception{
		UIComponent componente = event.getComponent();
		String uiName = componente.getId();
		
		if(uiName.equals("uploadCartaPresent")){
			strCartaPresentacion = FileUtil.subirArchivo(event, Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPRESENTACION);
			intCartaPresentacion = Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPRESENTACION;
		}else if(uiName.equals("uploadConvSug")){
			strConvenioSugerido = FileUtil.subirArchivo(event, Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO);
			intConvenioSugerido = Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO;
		}else if(uiName.equals("uploadAdenda")){
			strAdendaSugerida = FileUtil.subirArchivo(event, Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA);
			intAdendaSugerida = Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA;
		}
	}
	
	public void renombrarArchivo(List<Archivo> lista) throws BusinessException{
		TipoArchivo tipoArchivo = null;
		try{
			for(Archivo archivo: lista){
				tipoArchivo = new TipoArchivo();
				if(archivo.getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPRESENTACION)){
					tipoArchivo = generalFacade.getTipoArchivoPorPk(archivo.getId().getIntParaTipoCod());
					String ruta = tipoArchivo.getStrRuta() + "\\" + strCartaPresentacion;
					FileUtil.renombrarArchivo(ruta, tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}
				if(archivo.getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO)){
					tipoArchivo = generalFacade.getTipoArchivoPorPk(archivo.getId().getIntParaTipoCod());
					String ruta = tipoArchivo.getStrRuta() + "\\" + strConvenioSugerido;
					FileUtil.renombrarArchivo(ruta, tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}
				if(archivo.getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA)){
					tipoArchivo = generalFacade.getTipoArchivoPorPk(archivo.getId().getIntParaTipoCod());
					String ruta = tipoArchivo.getStrRuta() + "\\" + strAdendaSugerida;
					FileUtil.renombrarArchivo(ruta, tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}
			}
		}catch(BusinessException e){
			log.error("Error enrenombrarArchivo--->  "+e);
			log.error(e);
			throw e;
		}
	}
	
	public void removeConvenioEstructuraDet(ActionEvent event){
		log.info("------------------------HojaPlaneamientoController.removeConvenioEstructuraDet------------------------");
		String rowKey = getRequestParameter("rowKeyConvEstructDet");
		ConvenioEstructuraDetalleComp convEstructDetTmp = null;
		if(listaConvEstructuraDetComp!=null){
			for(int i=0; i<listaConvEstructuraDetComp.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					ConvenioEstructuraDetalleComp convEstructDetComp = listaConvEstructuraDetComp.get(i);
					if(convEstructDetComp.getEstructura().getId()!=null && convEstructDetComp.getEstructura().getId().getIntNivel()!=null){
						convEstructDetComp.setConvenioEstructuraDetalle(new ConvenioEstructuraDetalle());
						convEstructDetTmp = listaConvEstructuraDetComp.get(i);
						convEstructDetTmp.getConvenioEstructuraDetalle().setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaConvEstructuraDetComp.remove(i);
					break;
				}
			}
			if(convEstructDetTmp!=null){
				listaConvEstructuraDetComp.add(convEstructDetTmp);
			}
		}
	}
	
	public void eliminarHojaPlaneamiento(ActionEvent event){
		log.info("--------------Debugging HojaPlaneamientoController.eliminarHojaPlaneamiento--------------");
		String strIdConvenio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmHPModalPanel:hiddenIdConvenio");
		String strIdAmpliacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmHPModalPanel:hiddenIdItemConvenio");
		log.info("strIdConvenio 	: "+strIdConvenio);
		log.info("strIdAmpliacion 	: "+strIdAmpliacion);
		ConvenioFacadeLocal facade = null;
    	try {
    		beanAdenda.setId(new AdendaId());
    		beanAdenda.getId().setIntConvenio(new Integer(strIdConvenio));
    		beanAdenda.getId().setIntItemConvenio(new Integer(strIdAmpliacion));
			facade = (ConvenioFacadeLocal)EJBFactory.getLocal(ConvenioFacadeLocal.class);
			facade.eliminarAdenda(beanAdenda);
			limpiarHojaPlaneamiento();
			listarHojaPlaneamiento(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * CGD - 17.12.2013
	 */
		public void seleccionarRegistro(ActionEvent event){
			try {	
				hojaPlanCompBusqeda = new HojaPlaneamientoComp();
				hojaPlanCompBusqeda = (HojaPlaneamientoComp)event.getComponent().getAttributes().get("itemRegistro");
				
				
				intIdConvenioHP = hojaPlanCompBusqeda.getAdenda().getId().getIntConvenio();
				intIdItemConvenioHP = hojaPlanCompBusqeda.getAdenda().getId().getIntItemConvenio();
				
				System.out.println("hojaPlanCompBusqeda ---> "+hojaPlanCompBusqeda);
			} catch (Exception e) {
				log.error("Error en seleccionarRegistro ---> "+e);
			}
		}

		

	public Integer getIntCantFilasActivas() {
		return intCantFilasActivas;
	}
	public void setIntCantFilasActivas(Integer intCantFilasActivas) {
		this.intCantFilasActivas = intCantFilasActivas;
	}
	
	//Agregado por cdelosrios, 08/12/2013
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	//Fin agregado por cdelosrios, 08/12/2013

	public Integer getIntIdConvenioHP() {
		return intIdConvenioHP;
	}

	public void setIntIdConvenioHP(Integer intIdConvenioHP) {
		this.intIdConvenioHP = intIdConvenioHP;
	}

	public Integer getIntIdItemConvenioHP() {
		return intIdItemConvenioHP;
	}

	public void setIntIdItemConvenioHP(Integer intIdItemConvenioHP) {
		this.intIdItemConvenioHP = intIdItemConvenioHP;
	}
	
}