package pe.com.tumi.creditos.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.creditos.domain.AdendaPerfil;
import pe.com.tumi.creditos.domain.Poblacion;
import pe.com.tumi.creditos.domain.Competencia;
import pe.com.tumi.creditos.domain.Estructura;
import pe.com.tumi.creditos.domain.EstructuraHojaPlan;
import pe.com.tumi.creditos.domain.HojaPlaneamiento;
import pe.com.tumi.creditos.domain.ConvenioEstructuraDet;
import pe.com.tumi.creditos.domain.PoblacionDet;
import pe.com.tumi.creditos.service.impl.HojaPlaneamientoServiceImpl;
import pe.com.tumi.file.controller.FileUploadBean;
import pe.com.tumi.seguridad.controller.LoginController;
import pe.com.tumi.seguridad.domain.BeanSesion;

/************************************************************************/
/* Nombre de la clase: HojaPlaneamientoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de la Hoja de Planeamiento*/
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 28/12/2011 */
/* ********************************************************************* */

public class HojaPlaneamientoController extends GenericController {
	private HojaPlaneamientoServiceImpl hojaPlaneamientoService;
	private int 						rows = 5;
	private List 						beanListPoblacion;
	private List 						beanListPoblacionDet;
	private List 						beanListConvenioEstructDet = new ArrayList();
	private List 						beanListCompetencia = new ArrayList();
	private List 						beanListConvenio  = new ArrayList();
	private List 						beanListEstructHojaPlan  = new ArrayList();
	private List 						beanListEstructDet = new ArrayList();
	private HojaPlaneamiento			beanHojaPlaneam = new HojaPlaneamiento();
	private BeanSesion 					beanSesion = new BeanSesion();
	private Boolean 					bFormHojaPlaneamientoRendered = false;
	private Boolean 					formTipoConvEnabled = false;
	private Boolean 					formRangoFechaEnabled = true;
	private Boolean 					btnBuscarEnabled = true;
	private Boolean 					upldCartaPresent = true;
	private Boolean 					upldConvSugerido = true;
	private Boolean 					upldAdendaSugerida = true;
	private Boolean 					formFecFinDurac = true;
	private Boolean 					formRetPlan = true;
	private Boolean 					formCartaPresent = false;
	private Boolean 					formConvSugerido = false;
	private Boolean 					formAdendaSugerida = false;
	private Integer						nTipoConvenio;
	private Integer						nNivelEntidad;
	private Integer						nEstadoConv;
	private Integer						nSucursalConv;
	private Integer						nModalidad;
	private String 						sNombreEntidad;
	private Integer						nTipoSocio;
	private Integer						nDonacionEnt;
	private Integer						nRanFecha;
	private Date 						dFecIni;
	private Date 						dFecFin;
	private Boolean						chkDonacion;
	private Boolean						chkRangoFec;
	private String						msgTxtRangoFec;
	private String						sAlturaMercPoblacion;
	private String						sAlturaCompetencia;
	private String						sAlturaEstructDet;
	private Boolean						chkCartaPresent;
	private Boolean						chkConvSugerido;
	private Boolean						chkAdendaSugerida;
	private Boolean						chkRetencion;
	private String						rbTiempoDurac;
	private String						sNombEntidad;
	private Integer						cboNivelEntidad;
	private Date 						dFecIniDurac;
	private Date 						dFecFinDurac;
	private Date 						daFecSuscripcion;
	private String						hiddenIdConvenio;
	private String						sTipoConvenio;
	private Boolean						chkDocAdjuntos;
	private Boolean						chkCartaAutorizacion;
	//
	private String						strIdEmpresa;
	private String						strIdNivel;
	private String						strIdCodigo;
	private String						strIdCaso;
	
	//Mensajes de Error
	private String						msgTipoConvenio;
	private String						msgEstadoConvenio;
	private String						msgEntidad;
	private String						msgSucursal;
	private String						msgFecDuracion;
	private String						msgOpcionFiltroCred;
	private String						msgFecSuscripcion;
	private String						msgRetencPlanilla;
	private String						msgCartaAutorizacion;
	private String						msgDonacion;
	private String						msgCartaPresent;
	private String						msgConvSugerido;
	private String						msgAdendaSugerida;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	//Getters y Setters
	public HojaPlaneamientoServiceImpl getCreditosService() {
		return hojaPlaneamientoService;
	}
	public void setHojaPlaneamientoService(HojaPlaneamientoServiceImpl hojaPlaneamientoService) {
		this.hojaPlaneamientoService = hojaPlaneamientoService;
	}
	public HojaPlaneamientoServiceImpl getHojaPlaneamientoService() {
		return hojaPlaneamientoService;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public List getBeanListPoblacion() {
		return beanListPoblacion;
	}
	public void setBeanListPoblacion(List beanListPoblacion) {
		this.beanListPoblacion = beanListPoblacion;
	}
	public List getBeanListPoblacionDet() {
		return beanListPoblacionDet;
	}
	public void setBeanListPoblacionDet(List beanListPoblacionDet) {
		this.beanListPoblacionDet = beanListPoblacionDet;
	}
	public List getBeanListConvenioEstructDet() {
		return beanListConvenioEstructDet;
	}
	public void setBeanListConvenioEstructDet(List beanListConvenioEstructDet) {
		this.beanListConvenioEstructDet = beanListConvenioEstructDet;
	}
	public List getBeanListCompetencia() {
		return beanListCompetencia;
	}
	public void setBeanListCompetencia(List beanListCompetencia) {
		this.beanListCompetencia = beanListCompetencia;
	}
	public List getBeanListConvenio() {
		return beanListConvenio;
	}
	public void setBeanListConvenio(List beanListConvenio) {
		this.beanListConvenio = beanListConvenio;
	}
	public List getBeanListEstructHojaPlan() {
		return beanListEstructHojaPlan;
	}
	public void setBeanListEstructHojaPlan(List beanListEstructHojaPlan) {
		this.beanListEstructHojaPlan = beanListEstructHojaPlan;
	}
	public List getBeanListEstructDet() {
		return beanListEstructDet;
	}
	public void setBeanListEstructDet(List beanListEstructDet) {
		this.beanListEstructDet = beanListEstructDet;
	}
	public HojaPlaneamiento getBeanHojaPlaneam() {
		return beanHojaPlaneam;
	}
	public void setBeanHojaPlaneam(HojaPlaneamiento beanHojaPlaneam) {
		this.beanHojaPlaneam = beanHojaPlaneam;
	}
	public BeanSesion getBeanSesion() {
		return beanSesion;
	}
	public void setBeanSesion(BeanSesion beanSesion) {
		this.beanSesion = beanSesion;
	}
	public Boolean getbFormHojaPlaneamientoRendered() {
		return bFormHojaPlaneamientoRendered;
	}
	public void setbFormHojaPlaneamientoRendered(
			Boolean bFormHojaPlaneamientoRendered) {
		this.bFormHojaPlaneamientoRendered = bFormHojaPlaneamientoRendered;
	}
	public Boolean getFormTipoConvEnabled() {
		return formTipoConvEnabled;
	}
	public void setFormTipoConvEnabled(Boolean formTipoConvEnabled) {
		this.formTipoConvEnabled = formTipoConvEnabled;
	}
	public Boolean getFormRangoFechaEnabled() {
		return formRangoFechaEnabled;
	}
	public void setFormRangoFechaEnabled(Boolean formRangoFechaEnabled) {
		this.formRangoFechaEnabled = formRangoFechaEnabled;
	}
	public Boolean getBtnBuscarEnabled() {
		return btnBuscarEnabled;
	}
	public void setBtnBuscarEnabled(Boolean btnBuscarEnabled) {
		this.btnBuscarEnabled = btnBuscarEnabled;
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
	public Boolean getFormFecFinDurac() {
		return formFecFinDurac;
	}
	public void setFormFecFinDurac(Boolean formFecFinDurac) {
		this.formFecFinDurac = formFecFinDurac;
	}
	public Boolean getFormRetPlan() {
		return formRetPlan;
	}
	public void setFormRetPlan(Boolean formRetPlan) {
		this.formRetPlan = formRetPlan;
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
	public Integer getnTipoConvenio() {
		return nTipoConvenio;
	}
	public void setnTipoConvenio(Integer nTipoConvenio) {
		this.nTipoConvenio = nTipoConvenio;
	}
	public Integer getnNivelEntidad() {
		return nNivelEntidad;
	}
	public void setnNivelEntidad(Integer nNivelEntidad) {
		this.nNivelEntidad = nNivelEntidad;
	}
	public Integer getnEstadoConv() {
		return nEstadoConv;
	}
	public void setnEstadoConv(Integer nEstadoConv) {
		this.nEstadoConv = nEstadoConv;
	}
	public Integer getnSucursalConv() {
		return nSucursalConv;
	}
	public void setnSucursalConv(Integer nSucursalConv) {
		this.nSucursalConv = nSucursalConv;
	}
	public Integer getnModalidad() {
		return nModalidad;
	}
	public void setnModalidad(Integer nModalidad) {
		this.nModalidad = nModalidad;
	}
	public Integer getnTipoSocio() {
		return nTipoSocio;
	}
	public void setnTipoSocio(Integer nTipoSocio) {
		this.nTipoSocio = nTipoSocio;
	}
	public Integer getnDonacionEnt() {
		return nDonacionEnt;
	}
	public void setnDonacionEnt(Integer nDonacionEnt) {
		this.nDonacionEnt = nDonacionEnt;
	}
	public Integer getnRanFecha() {
		return nRanFecha;
	}
	public void setnRanFecha(Integer nRanFecha) {
		this.nRanFecha = nRanFecha;
	}
	public String getsNombreEntidad() {
		return sNombreEntidad;
	}
	public void setsNombreEntidad(String sNombreEntidad) {
		this.sNombreEntidad = sNombreEntidad;
	}
	public Date getdFecIni() {
		return dFecIni;
	}
	public void setdFecIni(Date dFecIni) {
		this.dFecIni = dFecIni;
	}
	public Date getdFecFin() {
		return dFecFin;
	}
	public void setdFecFin(Date dFecFin) {
		this.dFecFin = dFecFin;
	}
	public Boolean getChkDonacion() {
		return chkDonacion;
	}
	public void setChkDonacion(Boolean chkDonacion) {
		this.chkDonacion = chkDonacion;
	}
	public Boolean getChkRangoFec() {
		return chkRangoFec;
	}
	public void setChkRangoFec(Boolean chkRangoFec) {
		this.chkRangoFec = chkRangoFec;
	}
	public String getMsgTxtRangoFec() {
		return msgTxtRangoFec;
	}
	public void setMsgTxtRangoFec(String msgTxtRangoFec) {
		this.msgTxtRangoFec = msgTxtRangoFec;
	}
	public String getsAlturaMercPoblacion() {
		return sAlturaMercPoblacion;
	}
	public void setsAlturaMercPoblacion(String sAlturaMercPoblacion) {
		this.sAlturaMercPoblacion = sAlturaMercPoblacion;
	}
	public String getsAlturaCompetencia() {
		return sAlturaCompetencia;
	}
	public void setsAlturaCompetencia(String sAlturaCompetencia) {
		this.sAlturaCompetencia = sAlturaCompetencia;
	}
	public String getsAlturaEstructDet() {
		return sAlturaEstructDet;
	}
	public void setsAlturaEstructDet(String sAlturaEstructDet) {
		this.sAlturaEstructDet = sAlturaEstructDet;
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
	public Boolean getChkRetencion() {
		return chkRetencion;
	}
	public void setChkRetencion(Boolean chkRetencion) {
		this.chkRetencion = chkRetencion;
	}
	public String getRbTiempoDurac() {
		return rbTiempoDurac;
	}
	public void setRbTiempoDurac(String rbTiempoDurac) {
		this.rbTiempoDurac = rbTiempoDurac;
	}
	public String getsNombEntidad() {
		return sNombEntidad;
	}
	public void setsNombEntidad(String sNombEntidad) {
		this.sNombEntidad = sNombEntidad;
	}
	public Integer getCboNivelEntidad() {
		return cboNivelEntidad;
	}
	public void setCboNivelEntidad(Integer cboNivelEntidad) {
		this.cboNivelEntidad = cboNivelEntidad;
	}
	public Date getdFecIniDurac() {
		return dFecIniDurac;
	}
	public void setdFecIniDurac(Date dFecIniDurac) {
		this.dFecIniDurac = dFecIniDurac;
	}
	public Date getdFecFinDurac() {
		return dFecFinDurac;
	}
	public void setdFecFinDurac(Date dFecFinDurac) {
		this.dFecFinDurac = dFecFinDurac;
	}
	public Date getDaFecSuscripcion() {
		return daFecSuscripcion;
	}
	public void setDaFecSuscripcion(Date daFecSuscripcion) {
		this.daFecSuscripcion = daFecSuscripcion;
	}
	public String getHiddenIdConvenio() {
		return hiddenIdConvenio;
	}
	public void setHiddenIdConvenio(String hiddenIdConvenio) {
		this.hiddenIdConvenio = hiddenIdConvenio;
	}
	public String getsTipoConvenio() {
		return sTipoConvenio;
	}
	public void setsTipoConvenio(String sTipoConvenio) {
		this.sTipoConvenio = sTipoConvenio;
	}
	public Boolean getChkDocAdjuntos() {
		return chkDocAdjuntos;
	}
	public void setChkDocAdjuntos(Boolean chkDocAdjuntos) {
		this.chkDocAdjuntos = chkDocAdjuntos;
	}
	public Boolean getChkCartaAutorizacion() {
		return chkCartaAutorizacion;
	}
	public void setChkCartaAutorizacion(Boolean chkCartaAutorizacion) {
		this.chkCartaAutorizacion = chkCartaAutorizacion;
	}
	public String getStrIdEmpresa() {
		return strIdEmpresa;
	}
	public void setStrIdEmpresa(String strIdEmpresa) {
		this.strIdEmpresa = strIdEmpresa;
	}
	public String getStrIdNivel() {
		return strIdNivel;
	}
	public void setStrIdNivel(String strIdNivel) {
		this.strIdNivel = strIdNivel;
	}
	public String getStrIdCodigo() {
		return strIdCodigo;
	}
	public void setStrIdCodigo(String strIdCodigo) {
		this.strIdCodigo = strIdCodigo;
	}
	public String getStrIdCaso() {
		return strIdCaso;
	}
	public void setStrIdCaso(String strIdCaso) {
		this.strIdCaso = strIdCaso;
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
	public String getMsgEntidad() {
		return msgEntidad;
	}
	public void setMsgEntidad(String msgEntidad) {
		this.msgEntidad = msgEntidad;
	}
	public String getMsgSucursal() {
		return msgSucursal;
	}
	public void setMsgSucursal(String msgSucursal) {
		this.msgSucursal = msgSucursal;
	}
	public String getMsgFecDuracion() {
		return msgFecDuracion;
	}
	public void setMsgFecDuracion(String msgFecDuracion) {
		this.msgFecDuracion = msgFecDuracion;
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
	public String getMsgRetencPlanilla() {
		return msgRetencPlanilla;
	}
	public void setMsgRetencPlanilla(String msgRetencPlanilla) {
		this.msgRetencPlanilla = msgRetencPlanilla;
	}
	public String getMsgCartaAutorizacion() {
		return msgCartaAutorizacion;
	}
	public void setMsgCartaAutorizacion(String msgCartaAutorizacion) {
		this.msgCartaAutorizacion = msgCartaAutorizacion;
	}
	public String getMsgDonacion() {
		return msgDonacion;
	}
	public void setMsgDonacion(String msgDonacion) {
		this.msgDonacion = msgDonacion;
	}
	public String getMsgCartaPresent() {
		return msgCartaPresent;
	}
	public void setMsgCartaPresent(String msgCartaPresent) {
		this.msgCartaPresent = msgCartaPresent;
	}
	public String getMsgConvSugerido() {
		return msgConvSugerido;
	}
	public void setMsgConvSugerido(String msgConvSugerido) {
		this.msgConvSugerido = msgConvSugerido;
	}
	public String getMsgAdendaSugerida() {
		return msgAdendaSugerida;
	}
	public void setMsgAdendaSugerida(String msgAdendaSugerida) {
		this.msgAdendaSugerida = msgAdendaSugerida;
	}
	/**************************************************************/
	/*  Nombre :  listarCentroTrabajo()           		      	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                                                    	 */
	/*  Objetivo: Realizar el listado del Centro de Trabajo  */
	/*            Poblacional.                               */
	/*  Retorno : Listado de Centro de Trabajo vacía    	 */
	/**************************************************************/
	public void listarCentroTrabajo(){
		ArrayList lstCentroTrabajo	= new ArrayList();
	    Poblacion cent = new Poblacion("", new Float(0.0));
	    if(beanListPoblacion!=null){
			log.info("beanListCentroTrabajo.size(): "+beanListPoblacion.size());
			lstCentroTrabajo = (ArrayList) getBeanListPoblacion();
		}
	    lstCentroTrabajo.add(cent);
		log.info("lstCentroTrabajo.size(): "+lstCentroTrabajo.size());
		setBeanListPoblacion(lstCentroTrabajo);
		if(lstCentroTrabajo.size()==1){
			setsAlturaMercPoblacion(112*lstCentroTrabajo.size()+"px");
		}else{
			setsAlturaMercPoblacion((112*lstCentroTrabajo.size()-25*(lstCentroTrabajo.size()-1)) + "px");
		}
		
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
		log.info("---------------------Debugging listarPoblacion()-----------------------------");
		ArrayList lstPoblacionDet	= new ArrayList();
		PoblacionDet pobN1 = new PoblacionDet(0, 1,"Nombrado", 	1,"A", 0);
		PoblacionDet pobN2 = new PoblacionDet(0, 1,"Nombrado", 	2,"C", 0);
		PoblacionDet pobC  = new PoblacionDet(0, 2,"Contratado",0,"",  0);
	    
	    if(beanListPoblacion!=null){
			log.info("beanListPoblacion.size(): "+beanListPoblacion.size());
			lstPoblacionDet = (ArrayList) getBeanListPoblacionDet();
		}
	    lstPoblacionDet.add(pobN1);
	    lstPoblacionDet.add(pobN2);
	    lstPoblacionDet.add(pobC);
		log.info("lstPoblacionDet.size(): "+lstPoblacionDet.size());
		setBeanListPoblacionDet(lstPoblacionDet);
	}
	
	/**************************************************************/
	/*  Nombre :  agregarPoblacion()        		      	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                                                    	 */
	/*  Objetivo: Realizar el listado del Centro de Trabajo  */
	/*            Poblacional y el detalle del centro de 	 */
	/*            trabajo                                  	 */
	/*  Retorno : Listados de ambas poblaciones     	 */
	/**************************************************************/
	public void agregarPoblacion(ActionEvent event) {
		log.info("---------------------Debugging HojaPlaneamientoController.agregarPoblacion-----------------------------");
		listarPoblacion();
		listarCentroTrabajo();
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarPoblacion()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Agregar una fila para su posterior llenado      	*/
	/*  Retorno : Una fila habilitada para ser llenada		 		*/
	/**************************************************************/
	public void listarPoblacion(ActionEvent event) throws DaoException{
		log.info("--------------Debugging HojaPlaneamientoController.listarPoblacion-----------------");
		setCreditosService(hojaPlaneamientoService);
		log.info("Se ha seteado el Service");
		
		HashMap prmtBusq = new HashMap();
	    prmtBusq.put("pIntIdConvenio", 			Integer.parseInt(getHiddenIdConvenio().equals("")?"0":getHiddenIdConvenio()));
	    
	    ArrayList arrayPoblacion = new ArrayList();
	    ArrayList listaPoblacion = new ArrayList();
	    
	    try {
	    	arrayPoblacion = getCreditosService().listarPoblacion(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarRegPol() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    log.info("arrayPoblacion.size(): "+arrayPoblacion.size());
		for(int i=0; i<arrayPoblacion.size(); i++){
			Poblacion pob = (Poblacion) arrayPoblacion.get(i);
			log.info("CONV_IDCONVENIO_N = "		+pob.getnIdConvenio());
			log.info("COPO_DESCRIPCION_V = "	+pob.getsCentroTrabajo());
			log.info("COPO_DISTANCIA_N   = 	"	+pob.getFlDistancia());
			
			listaPoblacion.add(pob);
		}
		log.info("listaPoblacion.size(): "+listaPoblacion.size());
		if(listaPoblacion.size()==1){
			setsAlturaMercPoblacion(112*listaPoblacion.size()+"px");
		}else{
			setsAlturaMercPoblacion((112*listaPoblacion.size()-25*(listaPoblacion.size()-1)) + "px");
		}
	    setBeanListPoblacion(listaPoblacion);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarPoblacionDet()           					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Agregar una fila para su posterior llenado      	*/
	/*  Retorno : Una fila habilitada para ser llenada		 		*/
	/**************************************************************/
	public void listarPoblacionDet(ActionEvent event) throws DaoException{
		log.info("--------------Debugging HojaPlaneamientoController.listarPoblacionDet-----------------");
		setCreditosService(hojaPlaneamientoService);
		log.info("Se ha seteado el Service");
		
		HashMap prmtBusq = new HashMap();
	    prmtBusq.put("pIntIdConvenio", 			Integer.parseInt(getHiddenIdConvenio().equals("")?"0":getHiddenIdConvenio()));
	    
	    ArrayList arrayPoblacionDet = new ArrayList();
	    ArrayList listaPoblacionDet = new ArrayList();
	    
	    try {
	    	arrayPoblacionDet = getCreditosService().listarPoblacionDet(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getCreditosService().listarPoblacionDet() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    log.info("arrayPoblacionDet.size(): "+arrayPoblacionDet.size());
		for(int i=0; i<arrayPoblacionDet.size(); i++){
			PoblacionDet pobd = (PoblacionDet) arrayPoblacionDet.get(i);
			log.info("CONV_IDCONVENIO_N 	= "	+pobd.getnIdConvenio());
			log.info("CPDE_PADRON_N 		= "	+pobd.getnPadron());
			log.info("CPDE_TIPOTRABAJADOR_N = "	+pobd.getnTipoTrabajador());
			log.info("V_TIPOTRABAJADOR 		= "	+pobd.getsTipoTrabajador());
			log.info("CPDE_TIPOSOCIO_N  	= "	+pobd.getnTipoSocio());
			log.info("V_TIPOSOCIO  			= "	+pobd.getsTipoSocio());
			
			listaPoblacionDet.add(pobd);
		}
		log.info("listaPoblacion.size(): "+listaPoblacionDet.size());
		if(listaPoblacionDet.size()==3){
			setsAlturaMercPoblacion(112+"px");
		}else{
			setsAlturaMercPoblacion((112*(listaPoblacionDet.size()-3)-25*(listaPoblacionDet.size()-1)) + "px");
		}
		setBeanListPoblacionDet(listaPoblacionDet);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarCompetencia()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Agregar una fila para su posterior llenado      	*/
	/*  Retorno : Una fila habilitada para ser llenada		 		*/
	/**************************************************************/
	public void listarCompetencia(ActionEvent event) throws DaoException{
		log.info("--------------Debugging HojaPlaneamientoController.listarCompetencia-----------------");
		setCreditosService(hojaPlaneamientoService);
		log.info("Se ha seteado el Service");
		
		HashMap prmtBusq = new HashMap();
	    prmtBusq.put("pIntIdConvenio", 			Integer.parseInt(getHiddenIdConvenio()));
	    
	    ArrayList arrayCompetencia = new ArrayList();
	    ArrayList listaCompetencia = new ArrayList();
	    
	    try {
	    	arrayCompetencia = getCreditosService().listarCompetencia(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarRegPol() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    log.info("arrayCompetencia.size(): "+arrayCompetencia.size());
		for(int i=0; i<arrayCompetencia.size(); i++){
			Competencia comp = (Competencia) arrayCompetencia.get(i);
			log.info("CONV_IDCONVENIO_N = "		+comp.getnIdConvenio());
			log.info("COCO_DESCRIPCIONENTIDAD_V = "	+comp.getsEntidadFinanc());
			log.info("COCO_PLAZOPRESTAMO_N = 	"	+comp.getFlPlazoPrestamo());
			log.info("COCO_TASASPRESTAMO_N ="   +comp.getFlInteres());
			log.info("COCO_APORTES_N = " 		+comp.getFlMontoAporte());
			log.info("COCO_SERVICIOS_V = "		+comp.getsServOfrec());
			log.info("COCO_CANTIDAD_N  = "		+comp.getnSocios());
			
			listaCompetencia.add(comp);
		}
		log.info("lstCompetencia.size(): 	"+listaCompetencia.size());
		if(listaCompetencia.size()==1){
			setsAlturaCompetencia(52*listaCompetencia.size()+"px");
		}else{
			setsAlturaCompetencia((52*listaCompetencia.size()-23*(listaCompetencia.size()-1)) + "px");
		}
	    setBeanListCompetencia(listaCompetencia);
		
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  addCompetencia()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Agregar una fila para su posterior llenado      	*/
	/*  Retorno : Una fila habilitada para ser llenada		 		*/
	/**************************************************************/
	public void addCompetencia(ActionEvent event) throws DaoException{
		log.info("--------------Debugging HojaPlaneamientoController.addCompetencia-----------------");
		ArrayList lstCompetencia	= new ArrayList();
	    Competencia com = new Competencia("", 0, new Float(0.0), new Float(0.0), new Float(0.0), "", false);
	    if(beanListCompetencia!=null){
			log.info("beanListCompetencia.size(): "+beanListCompetencia.size());
			lstCompetencia = (ArrayList) getBeanListCompetencia();
		}
	    lstCompetencia.add(com);
		log.info("lstCompetencia.size(): "+lstCompetencia.size());
		setBeanListCompetencia(lstCompetencia);
		if(lstCompetencia.size()==1){
			setsAlturaCompetencia(52*lstCompetencia.size()+"px");
		}else{
			setsAlturaCompetencia((52*lstCompetencia.size()-23*(lstCompetencia.size()-1)) + "px");
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  removeCompetencia()           					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Quitar una o varias filas 				      	*/
	/*  Retorno : Fila o filas quitadas del listado de Competencias	*/
	/**************************************************************/
	public void removeCompetencia(ActionEvent event){
		log.info("-------------------Debugging HojaPlaneamientoController.removeCompetencia-----------------------");
	    ArrayList arrayCompetencia = new ArrayList();
	    for(int i=0; i<getBeanListCompetencia().size(); i++){
	    	Competencia com = new Competencia();
	    	com = (Competencia) getBeanListCompetencia().get(i);
	    	if(com.getChkCompetencia() == false){
	    		arrayCompetencia.add(com);
	    	}
	    }
	    setBeanListCompetencia(arrayCompetencia);
	    if(arrayCompetencia.size()==1){
			setsAlturaCompetencia(52*arrayCompetencia.size()+"px");
		}else{
			setsAlturaCompetencia((52*arrayCompetencia.size()-23*(arrayCompetencia.size()-1)) + "px");
		}
	}
	
	/**************************************************************/
	/*  Nombre :  cancelarPoblacion()        		      	 	*/
	/*                                                    	 	*/
	/*  Parametros. :  event       descripcion            	 	*/
	/*                                                    	 	*/
	/*  Objetivo: Quitar el listado tanto de centros de trabajo */
	/*            como el de Población 	 						*/
	/*  Retorno : Listados de centros de trabajo y población  	*/
	/*            vacíos                                   	 	*/
	/**************************************************************/
	public void cancelarPoblacion(ActionEvent event) {
		log.info("---------------------Debugging HojaPlaneamientoController.cancelarPoblacion-----------------------------");
		if(beanListPoblacion!=null) beanListPoblacion.clear();
		if(beanListPoblacionDet!=null) beanListPoblacionDet.clear();
		//if(beanListConvenioEstructDet!=null) beanListConvenioEstructDet.clear();
	}
	
	/**************************************************************/
	/*  Nombre :  grabarPoblacion()        		      	 	*/
	/*                                                    	 	*/
	/*  Parametros. :  event       descripcion            	 	*/
	/*                                                    	 	*/
	/*  Objetivo: Quitar el listado tanto de centros de trabajo */
	/*            como el de Población 	 						*/
	/*  Retorno : Listados de centros de trabajo y población  	*/
	/*            vacíos                                   	 	*/
	/**************************************************************/
	/*public void grabarPoblacion(ActionEvent event) {
		log.info("-----------------Debugging HojaPlaneamientoController.grabarPoblacion---------------------");
		ArrayList lstPoblacion	= new ArrayList();
		
		int sumPadNombA=0;	int sumSocNombA=0;
		int sumPadNombC=0;	int sumSoctNombC=0;
		int sumPadCont =0;	int sumSocCont=0;
		
		log.info("getBeanListPoblacion().size(): "+ getBeanListPoblacion().size());
	    for(int i=0; i<getBeanListPoblacion().size(); i++){
	    	ConvenioEstructuraDet pob = new ConvenioEstructuraDet();
	    	pob = (ConvenioEstructuraDet) getBeanListPoblacion().get(i);
	    	log.info("pob.getsTipoNombrado(): "+ pob.getsTipoNombrado());
	    	if(pob.getsTipoNombrado().equals("A")){
	    		sumPadNombA += pob.getnPadron();
	    	}else if(pob.getsTipoNombrado().equals("C")){
	    		sumPadNombC += pob.getnPadron();
	    	}else{
	    		sumPadCont += pob.getnPadron();
	    	}
	    }
	    for(int j=0; j<getBeanListConvenioEstructDet().size(); j++){
	    	ConvenioEstructuraDet pobent = (ConvenioEstructuraDet) getBeanListConvenioEstructDet().get(j);
	    	if(pobent.getsTipoNombrado()==null || pobent.getsTipoNombrado().equals("")){
	    		sumSocCont = pobent.getnSocios();
	    	}else if(pobent.getsTipoNombrado().equals("A")){
	    		sumSocNombA = pobent.getnSocios();
	    	}else if(pobent.getsTipoNombrado().equals("C")){
	    		sumSoctNombC = pobent.getnSocios();
	    	}
	    }
	    
	    ConvenioEstructuraDet pob2 = new ConvenioEstructuraDet();
	    pob2.setsDescripcion("Nombrado");
	    pob2.setsTipoNombrado("A");
	    pob2.setnPadron(sumPadNombA);
	    pob2.setnSocios(sumSocNombA);
	    pob2.setDoPorcDcto(null);
	    pob2.setDoPorcMoros(null);
	    ConvenioEstructuraDet pob3 = new ConvenioEstructuraDet();
	    pob3.setsDescripcion("Nombrado");
	    pob3.setsTipoNombrado("C");
	    pob3.setnPadron(sumPadNombC);
	    pob3.setnSocios(sumSoctNombC);
	    pob3.setDoPorcDcto(null);
	    pob3.setDoPorcMoros(null);
	    ConvenioEstructuraDet pob4 = new ConvenioEstructuraDet();
	    pob4.setsDescripcion("Contratado");
	    pob4.setsTipoNombrado("");
	    pob4.setnPadron(sumPadCont);
	    pob4.setnSocios(sumSocCont);
	    pob4.setDoPorcDcto(null);
	    pob4.setDoPorcMoros(null);
	    lstPoblacion.add(pob2);
	    lstPoblacion.add(pob3);
	    lstPoblacion.add(pob4);
	    setBeanListConvenioEstructDet(lstPoblacion);
	    
	}*/
	
	/**************************************************************/
	/*  Nombre :  habilitarGrabarHojaPlan()     		      		*/
	/*                                                    	 		*/
	/*  Parametros. :  Ninguno					           	 		*/
	/*  Objetivo: Habilitar el Formulario para el llenado del mimso */
	/*  Retorno : El formulario habilitado para su respectivo llenado */
	/**************************************************************/
	public void habilitarGrabarHojaPlan(ActionEvent event) {
		log.info("---------------------Debugging HojaPlaneamientoController.habilitarGrabarHojaPlan-----------------------------");
		setbFormHojaPlaneamientoRendered(true);
		setFormTipoConvEnabled(false);
		setFormCartaPresent(false);
		setFormConvSugerido(false);
		setFormAdendaSugerida(false);
		limpiarHojaPlaneamiento();
	}
	
	/**************************************************************/
	/*  Nombre :  habilitarActualizarHojaPlaneamiento()     		*/
	/*                                                    	 		*/
	/*  Parametros. :  Ninguno					           	 		*/
	/*  Objetivo: Habilitar el formulario de Hoja de Planeamiento al*/
	/*            ser editado                              	 		*/
	/*  Retorno : El formulario de Hoja de Planeamiento aparece     */
	/*            habilitado para su posterior llenado     	 		*/
	/**************************************************************/
	public void habilitarActualizarHojaPlaneamiento(ActionEvent event){	
		log.info("----------------Debugging HojaPlaneamientoController.habilitarActualizarHojaPlaneamiento----------------------");
	    if(beanListPoblacion!=null) beanListPoblacion.clear();
	    if(beanListPoblacionDet!=null) beanListPoblacionDet.clear();
		setbFormHojaPlaneamientoRendered(true);
	    setFormTipoConvEnabled(true);
	    setFormRetPlan(false);
	    setChkRetencion(true);
	    setFormCartaPresent(true);
		setFormConvSugerido(true);
		setFormAdendaSugerida(true);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  cancelarGrabarHojaPlaneamiento()           		*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*                                                    	 		*/
	/*  Objetivo: Cancelar la nueva Hoja de Planeamiento     		*/
	/*            Poblacional.                               	  	*/
	/*  Retorno : Se oculta el Formulario de Hoja de Planeamiento 	*/
	/**************************************************************/
	public void cancelarGrabarHojaPlaneamiento(ActionEvent event) {
		log.info("---------------------Debugging AdmFormDocController.cancelarGrabarHojaPlaneamiento-----------------------------");
		setbFormHojaPlaneamientoRendered(false);
		limpiarHojaPlaneamiento();
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  limpiarHojaPlaneamiento()      		     		*/
	/*  Parametros. :  Ninguno				            	 		*/
	/*  Objetivo: Realizar una limpieza al formulario	     		*/
	/*  Retorno : Formulario limpio para realizar una nueva inserción */
	/**************************************************************/
	public void limpiarHojaPlaneamiento(){
		HojaPlaneamiento hojaplan = new HojaPlaneamiento();
		setBeanHojaPlaneam(hojaplan);
		setMsgTipoConvenio("");
		setMsgEstadoConvenio("");
		setMsgEntidad("");
		setMsgSucursal("");
		setMsgOpcionFiltroCred("");
		setMsgFecSuscripcion("");
		setMsgFecDuracion("");
		setBtnBuscarEnabled(true);
		setHiddenIdConvenio("");
		setdFecIniDurac(null);
		setdFecFinDurac(null);
		setDaFecSuscripcion(null);
		setsTipoConvenio("");
		if(beanListConvenioEstructDet!=null) beanListConvenioEstructDet.clear();
		if(beanListEstructDet!=null) beanListEstructDet.clear();
		if(beanListPoblacion!=null) beanListPoblacion.clear();
		if(beanListPoblacionDet!=null) beanListPoblacionDet.clear();
		if(beanListCompetencia!=null) beanListCompetencia.clear();
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarConvenio()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	public void listarConvenio(ActionEvent event) {
		log.info("---------------------Debugging HojaPlaneamientoController.listarConvenio---------------------------");
		setCreditosService(hojaPlaneamientoService);
		log.info("Se ha seteado el Service");
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		Date daFecIni = getdFecIni();
	    String daFechaIni = (daFecIni == null ? "" : sdf2.format(daFecIni));
	    Date daFecFin = getdFecFin();
	    String daFechaFin = (daFecFin == null ? "" : sdf2.format(daFecFin));
	    
	    Boolean bValidar = true;
	    
	    if(getChkRangoFec()!= null && getChkRangoFec()==true && getnRanFecha() == 0){
	    	bValidar = false;
	    	setMsgTxtRangoFec("Debe Seleccionar un Rango de Fecha");
	    }else{
	    	setMsgTxtRangoFec("");
	    }
	    
	    HashMap prmtBusq = new HashMap();
	    prmtBusq.put("pIntIdConvenio", 			null);
	    prmtBusq.put("pIntIdAmpliacion", 		getnTipoConvenio());
	    prmtBusq.put("pIntIdNivel", 			getnNivelEntidad());
	    prmtBusq.put("pIntIdEstado",	 		getnEstadoConv());
	    prmtBusq.put("pIntIdSucursal",	 		getnSucursalConv());
	    prmtBusq.put("pIntIdModalidad", 		getnModalidad());
	    prmtBusq.put("pStrEntidad",		 		getsNombreEntidad());
	    prmtBusq.put("pIntIdTipoSocio",			getnTipoSocio());
		prmtBusq.put("pIntIdDonacion",			(getChkDonacion()!=null&&getChkDonacion()==true?1:null));
		prmtBusq.put("pIntIdRanFec",			getnRanFecha());
		prmtBusq.put("pDaFecIni",				daFechaIni);
		prmtBusq.put("pDaFecFin",				daFechaFin);
		prmtBusq.put("pChkDocAdjuntos",			getChkDocAdjuntos()==true?1:null);
		prmtBusq.put("pChkCartaAutoriz",		getChkCartaAutorizacion()==true?1:null);
		
	    ArrayList arrayConvenio = new ArrayList();
	    ArrayList listaConvenio = new ArrayList();
	    
	    if(bValidar == true){
		    try {
		    	arrayConvenio = getCreditosService().listarConvenio(prmtBusq);
			} catch (DaoException e) {
				log.info("ERROR getCreditosService().listarConvenio() " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
		log.info("arrayFormReport.size(): "+arrayConvenio.size());
		for(int i=0; i<arrayConvenio.size(); i++){
			HojaPlaneamiento hojaPlan = (HojaPlaneamiento) arrayConvenio.get(i);
			log.info("EMPR_IDEMPRESA_N = "	+hojaPlan.getnIdEmpresa());
			log.info("ESTR_IDCODIGO_N = "	+hojaPlan.getnIdCodigo());
			log.info("ESTR_NIVEL_N = 	"	+hojaPlan.getnIdNivel());
			log.info("ADEM_AMPLIACION_N ="  +hojaPlan.getnIdAmpliacion());
			log.info("V_TIPOCONVENIO = " 	+hojaPlan.getsTipoConvenio());
			log.info("PERS_IDPERSONA_N = "	+hojaPlan.getnIdPersona());
			log.info("JURI_RAZONSOCIAL_V ="	+hojaPlan.getsRazonSocial());
			log.info("CONV_IDCONVENIO_N"	+hojaPlan.getnIdConvenio());
			log.info("ADEM_IDESTADO_N = "	+hojaPlan.getnIdEstado());
			log.info("SUCU_IDSUCURSAL_N = "	+hojaPlan.getnIdSucursal());
			log.info("ESDE_MODALIDAD_N =  " +hojaPlan.getnIdModalidad());
			log.info("ESDE_TIPOSOCIO_N  = " +hojaPlan.getnIdTipoSocio());
			log.info("ADEM_FECHAREGISTRO_D="+hojaPlan.getDaFecRegistro());
			
			listaConvenio.add(hojaPlan);
		}
		
		log.info("listaConvenio.size(): "+listaConvenio.size());
		setBeanListConvenio(listaConvenio);
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
	public void enableDisableControls(ActionEvent event) throws DaoException{
		log.info("----------------Debugging HojaPlaneamientoController.enableDisableControls-------------------");
		setFormRangoFechaEnabled(getChkRangoFec()!=true);
		setUpldCartaPresent(getChkCartaPresent()!=true);
		setUpldConvSugerido(getChkConvSugerido()!=true);
		setUpldAdendaSugerida(getChkAdendaSugerida()!=true);
		setFormRetPlan(getChkRetencion()!=true);
		if(getRbTiempoDurac()!=null){
			setFormFecFinDurac(getRbTiempoDurac().equals("1"));
		}
		setBtnBuscarEnabled(getsTipoConvenio()==null);
		if(getChkRangoFec()==false){
			setnRanFecha(0);
			setdFecIni(null);
			setdFecFin(null);
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarEstructuras()           					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Habilitar y desabilitar el panel de Fechas     	*/
	/*  Retorno : El panel de Rango de Fechas se habilita y 		*/
	/*            deshabilita  						     	 		*/
	/**************************************************************/
	public void listarEstructuras(ActionEvent event){
		log.info("-----------------------Debugging HojaPlaneamientoController.listarEstructuras-----------------------------");
		HojaPlaneamiento hojaPlan = (HojaPlaneamiento) getBeanHojaPlaneam();
	    log.info("hojaPlan.getnIdAmpliacion(): "+hojaPlan.getnIdAmpliacion());
	    log.info("getsTipoConvenio(): "+getsTipoConvenio());
	    Boolean bValidar = true;
	    /*if(hojaPlan.getnIdAmpliacion()==null||hojaPlan.getnIdAmpliacion().equals("")){
	    	setMsgEntidad("Debe seleccionar un Tipo de Convenio.");
	    	bValidar = false;
	    }else{
	    	setMsgEntidad("");
	    }*/
	    
		HashMap prmtBusq = new HashMap();
	    prmtBusq.put("pStrNombEnt",  getsNombEntidad());
	    prmtBusq.put("pIntIdNivel",  getCboNivelEntidad());
	    prmtBusq.put("pIntIdTipoConvenio",  Integer.parseInt(getsTipoConvenio()));
	    
	    ArrayList arrayEstructura = new ArrayList();
	    ArrayList listaEstructura = new ArrayList();
	    
	    if(bValidar==true){
		    try {
		    	arrayEstructura = getCreditosService().listarEstructura(prmtBusq);
			} catch (DaoException e) {
				log.info("ERROR  getService().listarRegPol() " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		
		log.info("arrayEstructHojaPlan.size(): "+arrayEstructura.size());
		for(int i=0; i<arrayEstructura.size(); i++){
			Estructura estruct = (Estructura) arrayEstructura.get(i);
			log.info("EMPR_IDEMPRESA_N = "	+estruct.getnIdEmpresa());
			log.info("ESTR_IDCODIGO_N  = "	+estruct.getnIdCodigo());
			log.info("ESTR_NIVEL_N 	   = "	+estruct.getnIdNivel());
			log.info("ESDE_CASO_N 	   = "	+estruct.getnIdCaso());
			log.info("JURI_RAZONSOCIAL_V="  +estruct.getsRazonSocial());
			
			listaEstructura.add(estruct);
		}
		log.info("listaFormReport.size(): "+listaEstructura.size());
		setBeanListEstructHojaPlan(listaEstructura);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarEstructuraDet()           					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Habilitar y desabilitar el panel de Fechas     	*/
	/*  Retorno : El panel de Rango de Fechas se habilita y 		*/
	/*            deshabilita  						     	 		*/
	/**************************************************************/
	public void listarEstructuraDet(ActionEvent event){
		log.info("-----------------------Debugging HojaPlaneamientoController.listarEstructuraDet-----------------------------");
		//String strCodigoId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmEstructHojaPlan:hiddenIdCodigo");
		String nIdEmpresa = getRequestParameter("nIdEmpresa");
		String strCodigoId = getRequestParameter("nIdCodigo");
	    String nIdNivel   = getRequestParameter("nIdNivel");
	    String nIdCaso    = getRequestParameter("nIdCaso");
	    log.info("nIdEmpresa: "	+nIdEmpresa);
	    log.info("strCodigoId: "+strCodigoId);
	    log.info("nIdNivel: "	+nIdNivel);
	    log.info("nIdCaso: "	+nIdCaso);
	    
	    HojaPlaneamiento hojaPlan = (HojaPlaneamiento) getBeanHojaPlaneam();
	    
	    log.info("hojaPlan.getnIdAmpliacion(): "+hojaPlan.getnIdAmpliacion());
	    
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdConvenio", 	Integer.parseInt((getHiddenIdConvenio()==null||getHiddenIdConvenio().equals(""))?"0":getHiddenIdConvenio()));
		prmtBusq.put("pIntIdEmpresa", 	Integer.parseInt(nIdEmpresa==null?"0":nIdEmpresa));
		//prmtBusq.put("pIntIdCodigo", 	new Long(strCodigoId==null?"0":strCodigoId));
		prmtBusq.put("pIntIdCodigo", 	Long.valueOf(strCodigoId==null?"0":strCodigoId));
	    prmtBusq.put("pIntIdNivel", 	Integer.parseInt(nIdNivel==null?"0":nIdNivel));
	    prmtBusq.put("pIntIdCaso", 		Integer.parseInt(nIdCaso==null?"0":nIdCaso));
	    
	    ArrayList arrayEstructHojaPlan = new ArrayList();
	    ArrayList listaEstructHojaPlan = new ArrayList();
	    
	    try {
	    	arrayEstructHojaPlan = getCreditosService().listarEstructuraDet(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getCreditosService().listarEstructuraDet() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("arrayEstructHojaPlan.size(): "+arrayEstructHojaPlan.size());
		for(int i=0; i<arrayEstructHojaPlan.size(); i++){
			EstructuraHojaPlan estruct = (EstructuraHojaPlan) arrayEstructHojaPlan.get(i);
			log.info("EMPR_IDEMPRESA_N = "	+estruct.getnIdEmpresa());
			log.info("ESTR_IDCODIGO_N  = "	+estruct.getnIdCodigo());
			log.info("ESTR_NIVEL_N 	   = "	+estruct.getnIdNivel());
			log.info("NIVEL_DESCRIPCION= "	+estruct.getsNivelDesc());
			log.info("PERS_IDPERSONA_N = "  +estruct.getnIdPersona());
			log.info("JURI_RAZONSOCIAL_V="  +estruct.getsNombrePersona());
			log.info("PERS_USUARIO_N	="  +estruct.getnIdUsuario());
			log.info("ESDE_MODALIDAD_N	="  +estruct.getnIdModalidad());
			log.info("MODALIDAD_DESC	="  +estruct.getsModalidadDesc());
			log.info("ESDE_TIPOSOCIO_N	="  +estruct.getnIdTipoSocio());
			log.info("TIPOSOCIO_DESC	="  +estruct.getsTipoSocioDesc());
			log.info("ESDE_CASO_N		="  +estruct.getnIdCaso());
			
			setStrIdEmpresa(nIdEmpresa==null?(""+estruct.getnIdEmpresa()):nIdEmpresa);
		    setStrIdCodigo(strCodigoId==null?(""+estruct.getnIdCodigo()):strCodigoId);
		    setStrIdNivel(nIdNivel==null?(""+estruct.getnIdNivel()):nIdNivel);
		    setStrIdCaso(nIdCaso==null?(""+estruct.getnIdCaso()):nIdCaso);
			
			listaEstructHojaPlan.add(estruct);
		}
		log.info("listaEstructHojaPlan.size(): "+listaEstructHojaPlan.size());
		if(listaEstructHojaPlan.size()==1){
			setsAlturaEstructDet(48*listaEstructHojaPlan.size()+"px");
			log.info("getsAlturaEstructDet(): "+getsAlturaEstructDet());
		}else{
			setsAlturaEstructDet((48*listaEstructHojaPlan.size()-25*(listaEstructHojaPlan.size()-1)) + "px");
			log.info("getsAlturaEstructDet(): "+getsAlturaEstructDet());
		}
		setBeanListEstructDet(listaEstructHojaPlan);
		
		if(getHiddenIdConvenio()==null||getHiddenIdConvenio().equals("")){
			listarResumenPoblacion(event);
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarResumenPoblacion()           					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Habilitar y desabilitar el panel de Fechas     	*/
	/*  Retorno : El panel de Rango de Fechas se habilita y 		*/
	/*            deshabilita  						     	 		*/
	/**************************************************************/
	public void listarResumenPoblacion(ActionEvent event){
		log.info("-----------------------Debugging HojaPlaneamientoController.listarResumenPoblacion-----------------------------");
		String sIdEmpresa 	= getRequestParameter("nIdEmpresa");
		String sIdNivel 	= getRequestParameter("nIdNivel");
		log.info("sIdEmpresa: "+ sIdEmpresa);
		log.info("sIdNivel:   "+ sIdNivel);
	    
	    Boolean bValidar = true;
	    
		HashMap prmtBusq = new HashMap();
	    prmtBusq.put("pIntIdPeriodo", 	1201);
	    prmtBusq.put("pIntIdMes",  		1);
	    prmtBusq.put("pIntIdEmpresa",  	Integer.parseInt(sIdEmpresa==null?"0":sIdEmpresa));
	    prmtBusq.put("pIntIdNivel",  	Integer.parseInt(sIdNivel==null?"0":sIdNivel));
	    
	    ArrayList arrayResumenPob = new ArrayList();
	    ArrayList listaResumenPob = new ArrayList();
	    
	    if(bValidar==true){
		    try {
		    	arrayResumenPob = getCreditosService().listarResumenPoblacion(prmtBusq);
			} catch (DaoException e) {
				log.info("ERROR  getCreditosService().listarResumenPoblacion(prmtBusq); " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		
		log.info("arrayResumenPob.size(): "+arrayResumenPob.size());
		for(int i=0; i<arrayResumenPob.size(); i++){
			ConvenioEstructuraDet pobent = (ConvenioEstructuraDet) arrayResumenPob.get(i);
			log.info("V_MODALIDAD 	   = "	+pobent.getsDescripcion());
			log.info("V_TIPOSOC 	   = "	+pobent.getsTipoNombrado());
			log.info("SUM_PADRON 	   = "	+pobent.getnPadron());
			log.info("CNT_SOCIOS 	   = "	+pobent.getnSocios());
			listaResumenPob.add(pobent);
		}
		log.info("listaResumenPob.size(): "+listaResumenPob.size());
		setBeanListConvenioEstructDet(listaResumenPob);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  modificarHojaPlaneamiento()           			*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Habilitar y desabilitar el panel de Fechas     	*/
	/*  Retorno : El panel de Rango de Fechas se habilita y 		*/
	/*            deshabilita  						     	 		*/
	/**
	 * @throws ParseException ************************************************************/
	public void modificarHojaPlaneamiento(ActionEvent event) throws DaoException, ParseException{
		log.info("-------------------Debugging HojaPlaneamientoController.modificarHojaPlaneamiento----------------------");
		String strConvenioId   = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmHPModalPanel:hiddenIdConvenio");
		String strAmpliacionId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmHPModalPanel:hiddenIdAmpliacion");
		log.info("strUsuarioId: 	"+strConvenioId);
		log.info("strAmpliacionId:  "+strAmpliacionId);
		ArrayList arrayHojaPlaneamiento = new ArrayList();
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdConvenio", 		Integer.parseInt(strConvenioId));
		prmtBusq.put("pIntIdAmpliacion", 	Integer.parseInt(strAmpliacionId));
		
		//El metodo devuelve una sola fila
		arrayHojaPlaneamiento = getCreditosService().listarConvenio(prmtBusq);
		HojaPlaneamiento hojaPlan = (HojaPlaneamiento) arrayHojaPlaneamiento.get(0);
		log.info("EMPR_IDEMPRESA_N = "	+hojaPlan.getnIdEmpresa());
		log.info("ESTR_IDCODIGO_N = "	+hojaPlan.getnIdCodigo());
		log.info("ESTR_NIVEL_N = 	"	+hojaPlan.getnIdNivel());
		log.info("ADEM_AMPLIACION_N ="  +hojaPlan.getnIdAmpliacion());
		log.info("V_TIPOCONVENIO = " 	+hojaPlan.getsTipoConvenio());
		log.info("PERS_IDPERSONA_N = "	+hojaPlan.getnIdPersona());
		log.info("JURI_RAZONSOCIAL_V ="	+hojaPlan.getsRazonSocial());
		log.info("CONV_IDCONVENIO_N"	+hojaPlan.getnIdConvenio());
		log.info("ADEM_IDESTADO_N = "	+hojaPlan.getnIdEstado());
		log.info("SUCU_IDSUCURSAL_N = "	+hojaPlan.getnIdSucursal());
		log.info("V_SUCURSAL 		= "	+hojaPlan.getsSucursal());
		log.info("ESDE_MODALIDAD_N =  " +hojaPlan.getnIdModalidad());
		log.info("V_MODALIDAD 		=  " +hojaPlan.getsModalidad());
		log.info("ESDE_TIPOSOCIO_N  = " +hojaPlan.getnIdTipoSocio());
		log.info("V_TIPOSOCIO  		= " +hojaPlan.getsTipoSocio());
		log.info("ADEM_FECHAREGISTRO_D="+hojaPlan.getDaFecRegistro());
		log.info("ADEM_INICIO_D		="	+hojaPlan.getDaFecIni());
		log.info("ADEM_CESE_D		="	+hojaPlan.getDaFecFin());
		log.info("ADEM_SUSCRIPCION_D="	+hojaPlan.getDaFecSuscripcion());
		log.info("ADEM_TIPORETENCION_N="	+hojaPlan.getnTipoRetencion());
		log.info("ADEM_RETENCION_N	="	+hojaPlan.getFlRetencion());
		log.info("ADEM_OPCIONFILTROCREDITO_N="	+hojaPlan.getnOpcionFiltroCredito());
		log.info("ADEM_CARTAAUTORIZACION_N	="	+(hojaPlan.getnAutorizacion()==1?true:false));
		log.info("ADEM_DONACION_N			="	+(hojaPlan.getnDonacion()==1?true:false));
		
		//setnTipoConvenio(hojaPlan.getnIdConvenio());
		setHiddenIdConvenio(""+hojaPlan.getnIdConvenio());
		setsTipoConvenio(hojaPlan.getnIdAmpliacion()==0?"1":"2");
		hojaPlan.setnIdAmpliacion(hojaPlan.getnIdAmpliacion()==0?1:2);
		
		String daFecIni = ""+ hojaPlan.getDaFecIni() == null ? "" : hojaPlan.getDaFecIni();
		Date fechaIni = (daFecIni.equals("") ? null : sdf.parse(daFecIni));
		setdFecIniDurac(fechaIni);
		
		String daFecFin = ""+ hojaPlan.getDaFecFin() == null ? "" : hojaPlan.getDaFecFin();
		Date fechaFin = (daFecFin==null||daFecFin.equals("") ? null : sdf.parse(daFecFin));
		setdFecFinDurac(fechaFin);
		
		String daFecSusc = ""+ hojaPlan.getDaFecSuscripcion() == null ? "" : hojaPlan.getDaFecSuscripcion();
		Date fechaSusc = (daFecSusc.equals("") ? null : sdf.parse(daFecSusc));
		setDaFecSuscripcion(fechaSusc);
		
		setRbTiempoDurac(daFecFin!=null && !daFecFin.equals("") ? "1" : "2");
		setRbTiempoDurac(daFecFin==null?"2":"1");
		setFormFecFinDurac(daFecFin==null?false:true);
		hojaPlan.setbAutorizacion(hojaPlan.getnAutorizacion()==1);
		hojaPlan.setbDonacion(hojaPlan.getnDonacion()==1);
		
		setBeanHojaPlaneam(hojaPlan);
		habilitarActualizarHojaPlaneamiento(event);
		
		if(!getHiddenIdConvenio().equals("")){
			if(beanListPoblacion!=null) beanListPoblacion.clear();
			if(beanListPoblacionDet!=null) beanListPoblacionDet.clear();
		}
		
		listarEstructuraDet(event);
		listarPoblacion(event);
		listarPoblacionDet(event);
		listarResumenPoblacion(event);
		listarCompetencia(event);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  grabarHojaPlaneamiento	()           			*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Grabar el Formulario de Hoja de Planeamiento     	*/
	/*  Retorno : En el listado de Hojas de Planeamiento, deberá	*/
	/*            aparecer el nuevo registro		     	 		*/
	/**************************************************************/
	public void grabarHojaPlaneamiento(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging UsuarioController.grabarHojaPlaneamiento-----------------------------");
		setCreditosService(hojaPlaneamientoService);
		log.info("se ha seteado el service");
		
	    HojaPlaneamiento hojaPlan =  (HojaPlaneamiento) getBeanHojaPlaneam();
	    log.info("FileUploadBean.getStrNombreCartaPresent():   "+FileUploadBean.getStrNombreCartaPresent());
		log.info("FileUploadBean.getStrNombreConvSugerido():   "+FileUploadBean.getStrNombreConvSugerido());
		log.info("FileUploadBean.getStrNombreAdendaSugerida(): "+FileUploadBean.getStrNombreAdendaSugerida());
		String strCartaPresent 	= FileUploadBean.getStrNombreCartaPresent();
		String strConvSugerido 	= FileUploadBean.getStrNombreConvSugerido();
		String strAdendaSugerida= FileUploadBean.getStrNombreAdendaSugerida();
		
		log.info("LoginController.getEmpresa():   "+beanSesion.getIntIdEmpresa());
		log.info("LoginController.getPerfil(): "+beanSesion.getIntIdPerfil());
		String strIdEmpresa = ""+beanSesion.getIntIdEmpresa();
		String strIdPerfil  = ""+beanSesion.getIntIdPerfil();
		
		hojaPlan.setsCartaPresent(strCartaPresent);
		hojaPlan.setsConvSugerido(getUpldConvSugerido()==true ? "" : strConvSugerido);
		hojaPlan.setsAdendaSugerida(getUpldAdendaSugerida()==true ? "" : strAdendaSugerida);
	    
	    Date daFecIni = getdFecIniDurac();
	    String daFechaIni = (daFecIni == null ? "" : sdf.format(daFecIni));
	    hojaPlan.setDaFecIni(daFechaIni);
	    
	    Date daFecFin = getdFecFinDurac();
	    String daFechaFin = (daFecFin == null ? "" : sdf.format(daFecFin));
	    hojaPlan.setDaFecFin(daFechaFin);
	    
	    Date daFecSusc = getDaFecSuscripcion();
	    String daFechaSusc = (daFecSusc == null ? "" : sdf.format(daFecSusc));
	    hojaPlan.setDaFecRegistro(daFechaSusc);
	    
	    Boolean bValidar = true;
	    //if(hojaPlan.getnIdAmpliacion()==null){
	    if(getsTipoConvenio()==null||getsTipoConvenio().equals("")){
	    	setMsgTipoConvenio("* Debe seleccionar un Tipo de Convenio.");
	    	bValidar = false;
	    }else{
	    	setMsgTipoConvenio("");
	    }
	    if(hojaPlan.getnIdEstado()==0){
	    	setMsgEstadoConvenio("* Debe completar el campo Estado de Convenio");
	    	bValidar = false;
	    }else{
	    	setMsgEstadoConvenio("");
	    }
	    if(getBeanListEstructDet().size()==0){
	    	setMsgEntidad("* Debe Ingresar una Entidad");
	    	bValidar = false;
	    }else{
	    	setMsgEntidad("");
	    }
	    if(hojaPlan.getnIdSucursal()==0){
	    	setMsgSucursal("* Debe completar el campo Sucursal");
	    	bValidar = false;
	    }else{
	    	setMsgSucursal("");
	    }
	    if(getdFecIniDurac()==null){
	    	setMsgFecDuracion("* Debe completar la Fecha Inicio");
	    	bValidar = false;
	    }else{
	    	setMsgFecDuracion("");
	    }
	    if(getRbTiempoDurac()==null || getRbTiempoDurac().equals("1")){
		    if(getdFecIniDurac()==null){
		    	setMsgFecDuracion("* Debe completar la Fecha de Cese.");
		    	bValidar = false;
		    }else{
		    	setMsgFecDuracion("");
		    }
	    }
	    if(getDaFecSuscripcion()==null){
	    	setMsgFecSuscripcion("* Debe completar la Fecha de Suscripción");
	    	bValidar = false;
	    }else{
	    	setMsgFecSuscripcion("");
	    }
	    if(hojaPlan.getnOpcionFiltroCredito()==null){
	    	setMsgOpcionFiltroCred("Debe seleccionar un tipo de Filtro de Crédito");
	    	bValidar = false;
	    }else{
	    	setMsgOpcionFiltroCred("");
	    }
	    
	    if(getChkRetencion()==true){
	    	if(hojaPlan.getnTipoRetencion()==null){
	    		setMsgRetencPlanilla("* Debe elegir un tipo de retención de planilla.");
		    	bValidar = false;
	    	}
	    	if(hojaPlan.getFlRetencion()==null){
	    		setMsgRetencPlanilla("* Debe ingresar un monto.");
		    	bValidar = false;
	    	}
	    }else{
	    	setMsgRetencPlanilla("");
	    }
	    
	    if(getChkConvSugerido()== true && getChkAdendaSugerida() == true){
	    	setMsgConvSugerido("* Solo puede adjuntar un Convenio o Adenda Sugerida.");
	    	bValidar = false;
	    }else{
	    	setMsgConvSugerido("");
	    }
	    
	    log.info("getStrIdEmpresa(): "+ getStrIdEmpresa());
	    log.info("getStrIdNivel(): 	 "+ getStrIdNivel());
	    log.info("getStrIdCodigo():	 "+ getStrIdCodigo());
	    log.info("getStrIdCaso():	 "+ getStrIdCaso());
	    
	    if(bValidar==true){
		    try {
		    	getCreditosService().grabarHojaPlaneamiento(hojaPlan);
		    	log.info("hojaPlan.getnIdConvenio(): "+hojaPlan.getnIdConvenio());
				setHiddenIdConvenio(""+hojaPlan.getnIdConvenio());
				
			} catch (DaoException e) {
				log.info("ERROR  getCreditosService().grabarHojaPlaneamiento(usu:) " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Grabando Adenda Perfil
			try{
				AdendaPerfil aden = new AdendaPerfil();
				log.info("hojaPlan.getnIdConvenio(): "+hojaPlan.getnIdConvenio());
				log.info("hojaPlan.getnIdAmpliacion(): "+hojaPlan.getnIdAmpliacion());
				log.info("strIdEmpresa: "+strIdEmpresa);
				aden.setnIdConvenio(hojaPlan.getnIdConvenio());
				aden.setnIdAmpliacion(hojaPlan.getnIdAmpliacion());
				aden.setnIdEmpresa(Integer.parseInt(strIdEmpresa));
				
				getCreditosService().grabarAdendaPerfil(aden);
			} catch(DaoException e){
				log.info("ERROR  getCreditosService().grabarAdendaPerfil(aden:) " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Guardando el Resumen de ConvenioEstructuraDet
			for(int i=0; i<getBeanListConvenioEstructDet().size(); i++){
				ConvenioEstructuraDet conv = (ConvenioEstructuraDet) getBeanListConvenioEstructDet().get(i);
				
				conv.setnIdConvenio(hojaPlan.getnIdConvenio());
				conv.setnIdEmpresa(Integer.parseInt(getStrIdEmpresa()));
				conv.setnIdNivel(Integer.parseInt(getStrIdNivel()));
				conv.setnIdCodigo(Long.valueOf(getStrIdCodigo()));
				conv.setnIdCaso(Integer.parseInt(getStrIdCaso()));
				try {
					getCreditosService().grabarConvEstructDet(conv);
				} catch (DaoException e) {
					log.info("ERROR  getCreditosService().grabarConvEstructDet(conv:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			eliminarPoblacion(event);
			//Guardando Poblacion
			log.info("getBeanListPoblacion().size(): "+getBeanListPoblacion().size());
			for(int i=0; i<getBeanListPoblacion().size(); i++){
				Poblacion pob = (Poblacion) getBeanListPoblacion().get(i);
				pob.setnIdConvenio(hojaPlan.getnIdConvenio());
				pob.setnIdCorr(i+1);
				try {
					getCreditosService().grabarPoblacion(pob);
					//Guardando PoblacionDet
				} catch (DaoException e) {
					log.info("ERROR  getCreditosService().grabarPoblacion(pob:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int n = 0;
			log.info("beanListPoblacionDet.size: "+beanListPoblacionDet.size());
			//Guardando Poblacion Detalle
			for(int j=0; j<getBeanListPoblacionDet().size(); j++){
				PoblacionDet pobd = (PoblacionDet) getBeanListPoblacionDet().get(j);
				pobd.setnIdConvenio(hojaPlan.getnIdConvenio());
				log.info("j: "+j);
				if(j%3 == 0){
					n++;
				}
				log.info("n: " + n);
				pobd.setnIdCorr(n);
				
				try {
					getCreditosService().grabarPoblacionDet(pobd);
				} catch (DaoException e) {
					log.info("ERROR  getCreditosService().grabarPoblacionDet(pobd:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Grabando Competencia
			for(int i=0; i<getBeanListCompetencia().size(); i++){
				Competencia comp = (Competencia) getBeanListCompetencia().get(i);
				comp.setnIdConvenio(hojaPlan.getnIdConvenio());
				
				try {
					getCreditosService().grabarCompetencia(comp);
				} catch (DaoException e) {
					log.info("ERROR  getCreditosService().grabarCompetencia(comp:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			listarConvenio(event);
			setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
	    }
	}
	
	public void eliminarPoblacion(ActionEvent event) throws DaoException{
		Poblacion pob = new Poblacion();
		log.info("-----------------------Debugging HojaPlaneamientoController.eliminarPoblacion-----------------------------");
    	HashMap prmtPob = new HashMap();
    	prmtPob.put("pIntIdConvenio", Integer.parseInt(getHiddenIdConvenio()));
		
		getCreditosService().eliminarPoblacion(prmtPob);
		log.info("Se ha eliminado la poblacion: "+getHiddenIdConvenio());
    }
}