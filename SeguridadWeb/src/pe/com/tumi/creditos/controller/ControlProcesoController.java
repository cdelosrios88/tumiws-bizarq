package pe.com.tumi.creditos.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.creditos.domain.ControlProceso;
import pe.com.tumi.creditos.domain.PerfilConvenio;
import pe.com.tumi.creditos.service.impl.ControlProcesoServiceImpl;
import pe.com.tumi.creditos.service.impl.HojaPlaneamientoServiceImpl;
import pe.com.tumi.seguridad.domain.BeanSesion;

/************************************************************************/
/* Nombre de la clase: ControlProcesoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de Control de Proceso */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 17/01/2012 */
/* ********************************************************************* */

public class ControlProcesoController extends GenericController {
	private ControlProcesoServiceImpl controlProcesoService;
	private HojaPlaneamientoServiceImpl hojaPlaneamientoService;
	private int 				rows = 5;
	private List 				beanListControlProceso;
	private List 				beanListEncargConvenio;
	private List 				beanListJefatCreditos;
	private List 				beanListJefatCobranza;
	private List 				beanListAsesorLegal;
	private List 				beanListAsistGerencia;
	private BeanSesion 			beanSesion = new BeanSesion();
	private String				stPerfil;
	private String				stNombUsuario;
	private String				stEstConvenio;
	
	private String				stObsEncargConv;
	private String				stObsJefatCred;
	private String				stObsJefatCobr;
	private String				stObsAsesLegal;
	private String				stObsGerencGral;
	private String				msgApruebaRechaza;
	private String				msgObservacion;
	
	//private PerfilConvenio  	beanPerfilConvenio = new PerfilConvenio();
	private Integer 			nTipoConvenio;
	private Integer				inIdConvenio;
	private Integer				nIdSucursal;
	private Integer				nIdEstado;
	private String				sNombEntidad;
	private Boolean				enableDisableFormControl = false;
	private Boolean				enableDisableEncConv = true;
	private Boolean				enableDisableJefCred = true;
	private Boolean				enableDisableJefCobr = true;
	private Boolean				enableDisableAsesLeg = true;
	private Boolean				enableDisableAsistGer = true;
	
	private Boolean				enableDisableBtnValidar = true;
	private Boolean				enableDisableBtnAprobar = true;
	private Boolean				enableDisableBtnRechazar = true;
	private Boolean				enableDisableBtnCancelar = false;
	
	//Getters y Setters
	public ControlProcesoServiceImpl getControlProcesoService() {
		return controlProcesoService;
	}
	public void setControlProcesoService(
			ControlProcesoServiceImpl controlProcesoService) {
		this.controlProcesoService = controlProcesoService;
	}
	public HojaPlaneamientoServiceImpl getHojaPlaneamientoService() {
		return hojaPlaneamientoService;
	}
	public void setHojaPlaneamientoService(
			HojaPlaneamientoServiceImpl hojaPlaneamientoService) {
		this.hojaPlaneamientoService = hojaPlaneamientoService;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public List getBeanListControlProceso() {
		return beanListControlProceso;
	}
	public void setBeanListControlProceso(List beanListControlProceso) {
		this.beanListControlProceso = beanListControlProceso;
	}
	public List getBeanListEncargConvenio() {
		return beanListEncargConvenio;
	}
	public void setBeanListEncargConvenio(List beanListEncargConvenio) {
		this.beanListEncargConvenio = beanListEncargConvenio;
	}
	public List getBeanListJefatCreditos() {
		return beanListJefatCreditos;
	}
	public void setBeanListJefatCreditos(List beanListJefatCreditos) {
		this.beanListJefatCreditos = beanListJefatCreditos;
	}
	public List getBeanListJefatCobranza() {
		return beanListJefatCobranza;
	}
	public void setBeanListJefatCobranza(List beanListJefatCobranza) {
		this.beanListJefatCobranza = beanListJefatCobranza;
	}
	public List getBeanListAsesorLegal() {
		return beanListAsesorLegal;
	}
	public void setBeanListAsesorLegal(List beanListAsesorLegal) {
		this.beanListAsesorLegal = beanListAsesorLegal;
	}
	public List getBeanListAsistGerencia() {
		return beanListAsistGerencia;
	}
	public void setBeanListAsistGerencia(List beanListAsistGerencia) {
		this.beanListAsistGerencia = beanListAsistGerencia;
	}
	public BeanSesion getBeanSesion() {
		return beanSesion;
	}
	public void setBeanSesion(BeanSesion beanSesion) {
		this.beanSesion = beanSesion;
	}
	public String getStPerfil() {
		return stPerfil;
	}
	public void setStPerfil(String stPerfil) {
		this.stPerfil = stPerfil;
	}
	public String getStNombUsuario() {
		return stNombUsuario;
	}
	public void setStNombUsuario(String stNombUsuario) {
		this.stNombUsuario = stNombUsuario;
	}
	public String getStEstConvenio() {
		return stEstConvenio;
	}
	public void setStEstConvenio(String stEstConvenio) {
		this.stEstConvenio = stEstConvenio;
	}
	public String getStObsEncargConv() {
		return stObsEncargConv;
	}
	public void setStObsEncargConv(String stObsEncargConv) {
		this.stObsEncargConv = stObsEncargConv;
	}
	public String getStObsJefatCred() {
		return stObsJefatCred;
	}
	public void setStObsJefatCred(String stObsJefatCred) {
		this.stObsJefatCred = stObsJefatCred;
	}
	public String getStObsJefatCobr() {
		return stObsJefatCobr;
	}
	public void setStObsJefatCobr(String stObsJefatCobr) {
		this.stObsJefatCobr = stObsJefatCobr;
	}
	public String getStObsAsesLegal() {
		return stObsAsesLegal;
	}
	public void setStObsAsesLegal(String stObsAsesLegal) {
		this.stObsAsesLegal = stObsAsesLegal;
	}
	public String getStObsGerencGral() {
		return stObsGerencGral;
	}
	public void setStObsGerencGral(String stObsGerencGral) {
		this.stObsGerencGral = stObsGerencGral;
	}
	public String getMsgApruebaRechaza() {
		return msgApruebaRechaza;
	}
	public void setMsgApruebaRechaza(String msgApruebaRechaza) {
		this.msgApruebaRechaza = msgApruebaRechaza;
	}
	public String getMsgObservacion() {
		return msgObservacion;
	}
	public void setMsgObservacion(String msgObservacion) {
		this.msgObservacion = msgObservacion;
	}
	public Integer getnTipoConvenio() {
		return nTipoConvenio;
	}
	public void setnTipoConvenio(Integer nTipoConvenio) {
		this.nTipoConvenio = nTipoConvenio;
	}
	public Integer getInIdConvenio() {
		return inIdConvenio;
	}
	public void setInIdConvenio(Integer inIdConvenio) {
		this.inIdConvenio = inIdConvenio;
	}
	public Integer getnIdSucursal() {
		return nIdSucursal;
	}
	public void setnIdSucursal(Integer nIdSucursal) {
		this.nIdSucursal = nIdSucursal;
	}
	public Integer getnIdEstado() {
		return nIdEstado;
	}
	public void setnIdEstado(Integer nIdEstado) {
		this.nIdEstado = nIdEstado;
	}
	public String getsNombEntidad() {
		return sNombEntidad;
	}
	public void setsNombEntidad(String sNombEntidad) {
		this.sNombEntidad = sNombEntidad;
	}
	public Boolean getEnableDisableFormControl() {
		return enableDisableFormControl;
	}
	public void setEnableDisableFormControl(Boolean enableDisableFormControl) {
		this.enableDisableFormControl = enableDisableFormControl;
	}
	public Boolean getEnableDisableEncConv() {
		return enableDisableEncConv;
	}
	public void setEnableDisableEncConv(Boolean enableDisableEncConv) {
		this.enableDisableEncConv = enableDisableEncConv;
	}
	public Boolean getEnableDisableJefCred() {
		return enableDisableJefCred;
	}
	public void setEnableDisableJefCred(Boolean enableDisableJefCred) {
		this.enableDisableJefCred = enableDisableJefCred;
	}
	public Boolean getEnableDisableJefCobr() {
		return enableDisableJefCobr;
	}
	public void setEnableDisableJefCobr(Boolean enableDisableJefCobr) {
		this.enableDisableJefCobr = enableDisableJefCobr;
	}
	public Boolean getEnableDisableAsesLeg() {
		return enableDisableAsesLeg;
	}
	public void setEnableDisableAsesLeg(Boolean enableDisableAsesLeg) {
		this.enableDisableAsesLeg = enableDisableAsesLeg;
	}
	public Boolean getEnableDisableAsistGer() {
		return enableDisableAsistGer;
	}
	public void setEnableDisableAsistGer(Boolean enableDisableAsistGer) {
		this.enableDisableAsistGer = enableDisableAsistGer;
	}
	public Boolean getEnableDisableBtnValidar() {
		return enableDisableBtnValidar;
	}
	public void setEnableDisableBtnValidar(Boolean enableDisableBtnValidar) {
		this.enableDisableBtnValidar = enableDisableBtnValidar;
	}
	public Boolean getEnableDisableBtnAprobar() {
		return enableDisableBtnAprobar;
	}
	public void setEnableDisableBtnAprobar(Boolean enableDisableBtnAprobar) {
		this.enableDisableBtnAprobar = enableDisableBtnAprobar;
	}
	public Boolean getEnableDisableBtnRechazar() {
		return enableDisableBtnRechazar;
	}
	public void setEnableDisableBtnRechazar(Boolean enableDisableBtnRechazar) {
		this.enableDisableBtnRechazar = enableDisableBtnRechazar;
	}
	public Boolean getEnableDisableBtnCancelar() {
		return enableDisableBtnCancelar;
	}
	public void setEnableDisableBtnCancelar(Boolean enableDisableBtnCancelar) {
		this.enableDisableBtnCancelar = enableDisableBtnCancelar;
	}
	
	
	// Métodos a implementar
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarControlProceso()        					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	public void listarControlProceso(ActionEvent event) {
		log.info("---------------------Debugging ControlProcesoController.listarControlProceso---------------------------");
		setCreditosService(controlProcesoService);
		log.info("Se ha seteado el Service");
		
	    HashMap prmtBusq = new HashMap();
	    prmtBusq.put("pIntIdAmpliacion", 	getnTipoConvenio());
	    prmtBusq.put("pIntIdConvenio", 		getInIdConvenio());
	    prmtBusq.put("pIntIdSucursal", 		getnIdSucursal());
	    prmtBusq.put("pIntIdEstado", 		getnIdEstado());
	    prmtBusq.put("pStrEntidad", 		getsNombEntidad());
		
	    ArrayList arrayControlProceso = new ArrayList();
	    ArrayList listaControlProceso = new ArrayList();
	    
	    try {
	    	arrayControlProceso = getCreditosService().listarControlProceso(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR getCreditosService().listarControlProceso() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		log.info("arrayControlProceso.size(): "+arrayControlProceso.size());
		for(int i=0; i<arrayControlProceso.size(); i++){
			ControlProceso contproc = (ControlProceso) arrayControlProceso.get(i);
			log.info("CONV_IDCONVENIO_N  = " +contproc.getInIdConvenio());
			log.info("ADEM_AMPLIACION_N  = " +contproc.getInIdAmpliacion());
			log.info("EMPR_IDEMPRESA_N   = " +contproc.getInIdEmpresa());
			log.info("ESTR_NIVEL_N  	 = " +contproc.getInIdNivel());
			log.info("ESTR_IDCODIGO_N	 = " +contproc.getInIdCodigo());
			log.info("ESDE_CASO_N		 = " +contproc.getInIdCaso());
			log.info("V_TIPOCONVENIO	 = " +contproc.getStTipoConvenio());
			log.info("PERS_IDPERSONA_N	 = " +contproc.getInIdPersona());
			log.info("JURI_RAZONSOCIAL_V = " +contproc.getStNombreEntidad());
			log.info("ADEM_IDESTADO_N    = " +contproc.getInIdEstado());
			log.info("V_ESTCONV    		 = " +contproc.getStEstConvenio());
			log.info("SUCU_IDSUCURSAL_N  = " +contproc.getInIdSucursal());
			log.info("V_SUCURSAL   		 = " +contproc.getStSucursal());
			
			listaControlProceso.add(contproc);
		}
		
		log.info("listaControlProceso.size(): "+listaControlProceso.size());
		setBeanListControlProceso(listaControlProceso);
	}
	
	/**************************************************************/
	/*  Nombre :  listarPerfilConvenio()      		      	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                                                    	 */
	/*  Objetivo: Listar los Convenios y verificar los que   */
	/*            están pendientes de validar y/o Aprobar.   */
	/*  Retorno : Listado de Convenios                       */
	/**************************************************************/
	public void listarPerfilConvenio(ActionEvent event){
		log.info("----------------Debugging ControlProcesoController.listarPerfilConvenio-------------------");
		setCreditosService(controlProcesoService);
		log.info("Se ha seteado el Service");
		setEnableDisableFormControl(true);
		log.info("getEnableDisableFormControl(): "+getEnableDisableFormControl());
		
		setStPerfil(beanSesion.getStrPerfil());
		setStNombUsuario(beanSesion.getStrApepaUsu()+" "+ beanSesion.getStrApemaUsu()+ " "+beanSesion.getStrNombreUsu());
		setStEstConvenio(getRequestParameter("idEstadoConv"));
		
		Integer idConvenio = Integer.parseInt(getRequestParameter("idConvenio"));
		Integer idAmpliacion = Integer.parseInt(getRequestParameter("idAmpliacion"));
		
		Integer idPerfil = beanSesion.getIntIdPerfil();
		setEnableDisableEncConv(idPerfil!=Constante.ENCARGADO_CONVENIO);
		setEnableDisableJefCred(idPerfil!=Constante.JEFATURA_CREDITOS);
		setEnableDisableJefCobr(idPerfil!=Constante.JEFATURA_COBRANZAS);
		setEnableDisableAsesLeg(idPerfil!=Constante.ASESOR_LEGAL);
		setEnableDisableAsistGer(idPerfil!=Constante.GERENCIA_GENERAL);
		
		setEnableDisableBtnValidar((idPerfil==Constante.ENCARGADO_CONVENIO ||
									idPerfil==Constante.JEFATURA_CREDITOS ||
									idPerfil==Constante.JEFATURA_COBRANZAS ||
									idPerfil==Constante.ASESOR_LEGAL)?false:true);
		log.info("getEnableDisableBtnValidar(): "+getEnableDisableBtnValidar());
		setEnableDisableBtnAprobar(idPerfil!=Constante.GERENCIA_GENERAL);
		setEnableDisableBtnRechazar(idPerfil!=Constante.GERENCIA_GENERAL);
		
		if(getRequestParameter("idEstadoConv").equals("Aprobado")){
			setEnableDisableBtnRechazar(true);
			setEnableDisableBtnValidar(true);
			setEnableDisableEncConv(true);
		}
		
		listarEncargConvenio(idConvenio, idAmpliacion);
		listarJefaturaCreditos(idConvenio, idAmpliacion);
		listarJefaturaCobranza(idConvenio, idAmpliacion);
		listarAsesorLegal(idConvenio, idAmpliacion);
		listarAsistGerencia(idConvenio, idAmpliacion);
	}
	
	public void listarEncargConvenio(Integer idConvenio, Integer idAmpliacion){
		log.info("----------------Debugging ControlProcesoController.listarEncargConvenio-------------------");
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdConvenio",  	idConvenio);
		prmtBusq.put("pIntIdAmpliacion",  	idAmpliacion);
		prmtBusq.put("pIntIdEmpresa",  		beanSesion.getIntIdEmpresa());
	    prmtBusq.put("pIntIdPerfil",   		Constante.ENCARGADO_CONVENIO);
	    
	    ArrayList arrayPerfilConvenio = new ArrayList();
	    ArrayList listaPerfilConvenio = new ArrayList();
	    
	    log.info("prmtBusq: "+prmtBusq);
        try {
	    	arrayPerfilConvenio = getCreditosService().listarPerfilConvenio(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getCreditosService().listarPerfilConvenio() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("arrayPerfilConvenio.size(): "+arrayPerfilConvenio.size());
		for(int i=0; i<arrayPerfilConvenio.size(); i++){
			PerfilConvenio perfc = (PerfilConvenio) arrayPerfilConvenio.get(i);
			log.info("PEVA_IDVALIDACION_N 	= "	+perfc.getInIdValidacion());
			log.info("V_DESCRIPCION  		= "	+perfc.getStDescripcion());
			log.info("V_OBSERVACION 	   	= "	+perfc.getStObservacion());
			log.info("APDE_VALOR_N 	   		= "	+perfc.getInValorPerfDet());
			setStObsEncargConv(perfc.getStObservacion());
			perfc.setInEstadoPerf(perfc.getInValorPerfDet()==1);
			listaPerfilConvenio.add(perfc);
		}
		log.info("listaPerfilConvenio.size(): "+listaPerfilConvenio.size());
		setBeanListEncargConvenio(listaPerfilConvenio);
	}
	
	public void listarJefaturaCreditos(Integer idConvenio, Integer idAmpliacion){
		log.info("----------------Debugging ControlProcesoController.listarJefaturaCreditos-------------------");
		
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdConvenio",  	idConvenio);
		prmtBusq.put("pIntIdAmpliacion",  	idAmpliacion);
	    prmtBusq.put("pIntIdEmpresa",  		beanSesion.getIntIdEmpresa());
	    prmtBusq.put("pIntIdPerfil",   		Constante.JEFATURA_CREDITOS);
	    
	    ArrayList arrayPerfilConvenio = new ArrayList();
	    ArrayList listaPerfilConvenio = new ArrayList();
	    
	    log.info("prmtBusq: "+prmtBusq);
        try {
	    	arrayPerfilConvenio = getCreditosService().listarPerfilConvenio(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getCreditosService().listarPerfilConvenio() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("arrayPerfilConvenio.size(): "+arrayPerfilConvenio.size());
		for(int i=0; i<arrayPerfilConvenio.size(); i++){
			PerfilConvenio perfc = (PerfilConvenio) arrayPerfilConvenio.get(i);
			log.info("PEVA_IDVALIDACION_N 	= "	+perfc.getInIdValidacion());
			log.info("V_DESCRIPCION  		= "	+perfc.getStDescripcion());
			log.info("V_OBSERVACION 	   	= "	+perfc.getStObservacion());
			log.info("APDE_VALOR_N 	   		= "	+perfc.getInValorPerfDet());
			setStObsJefatCred(perfc.getStObservacion());
			perfc.setInEstadoPerf(perfc.getInValorPerfDet()==1);
			listaPerfilConvenio.add(perfc);
		}
		log.info("listaPerfilConvenio.size(): "+listaPerfilConvenio.size());
		setBeanListJefatCreditos(listaPerfilConvenio);
	}
	
	public void listarJefaturaCobranza(Integer idConvenio, Integer idAmpliacion){
		log.info("----------------Debugging ControlProcesoController.listarJefaturaCobranza-------------------");
		
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdConvenio",  	idConvenio);
		prmtBusq.put("pIntIdAmpliacion",  	idAmpliacion);
	    prmtBusq.put("pIntIdEmpresa",  		beanSesion.getIntIdEmpresa());
	    prmtBusq.put("pIntIdPerfil",   		Constante.JEFATURA_COBRANZAS);
	    
	    ArrayList arrayPerfilConvenio = new ArrayList();
	    ArrayList listaPerfilConvenio = new ArrayList();
	    
	    log.info("prmtBusq: "+prmtBusq);
        try {
	    	arrayPerfilConvenio = getCreditosService().listarPerfilConvenio(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getCreditosService().listarPerfilConvenio() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("arrayPerfilConvenio.size(): "+arrayPerfilConvenio.size());
		for(int i=0; i<arrayPerfilConvenio.size(); i++){
			PerfilConvenio perfc = (PerfilConvenio) arrayPerfilConvenio.get(i);
			log.info("PEVA_IDVALIDACION_N 	= "	+perfc.getInIdValidacion());
			log.info("V_DESCRIPCION  		= "	+perfc.getStDescripcion());
			log.info("V_OBSERVACION 	   	= "	+perfc.getStObservacion());
			log.info("APDE_VALOR_N 	   		= "	+perfc.getInValorPerfDet());
			setStObsJefatCobr(perfc.getStObservacion());
			perfc.setInEstadoPerf(perfc.getInValorPerfDet()==1);
			listaPerfilConvenio.add(perfc);
		}
		log.info("listaPerfilConvenio.size(): "+listaPerfilConvenio.size());
		setBeanListJefatCobranza(listaPerfilConvenio);
	}
	
	public void listarAsesorLegal(Integer idConvenio, Integer idAmpliacion){
		log.info("----------------Debugging ControlProcesoController.listarAsesorLegal-------------------");
		
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdConvenio",  	idConvenio);
		prmtBusq.put("pIntIdAmpliacion",  	idAmpliacion);
	    prmtBusq.put("pIntIdEmpresa",  		beanSesion.getIntIdEmpresa());
	    prmtBusq.put("pIntIdPerfil",   		Constante.ASESOR_LEGAL);
	    
	    ArrayList arrayPerfilConvenio = new ArrayList();
	    ArrayList listaPerfilConvenio = new ArrayList();
	    
	    log.info("prmtBusq: "+prmtBusq);
        try {
	    	arrayPerfilConvenio = getCreditosService().listarPerfilConvenio(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getCreditosService().listarPerfilConvenio() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("arrayPerfilConvenio.size(): "+arrayPerfilConvenio.size());
		for(int i=0; i<arrayPerfilConvenio.size(); i++){
			PerfilConvenio perfc = (PerfilConvenio) arrayPerfilConvenio.get(i);
			log.info("PEVA_IDVALIDACION_N 	= "	+perfc.getInIdValidacion());
			log.info("V_DESCRIPCION  		= "	+perfc.getStDescripcion());
			log.info("V_OBSERVACION 	   	= "	+perfc.getStObservacion());
			log.info("APDE_VALOR_N 	   		= "	+perfc.getInValorPerfDet());
			setStObsAsesLegal(perfc.getStObservacion());
			perfc.setInEstadoPerf(perfc.getInValorPerfDet()==1);
			listaPerfilConvenio.add(perfc);
		}
		log.info("listaPerfilConvenio.size(): "+listaPerfilConvenio.size());
		setBeanListAsesorLegal(listaPerfilConvenio);
	}
	
	public void listarAsistGerencia(Integer idConvenio, Integer idAmpliacion){
		log.info("----------------Debugging ControlProcesoController.listarAsistGerencia-------------------");
		
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdConvenio",  	idConvenio);
		prmtBusq.put("pIntIdAmpliacion",  	idAmpliacion);
	    prmtBusq.put("pIntIdEmpresa",  		beanSesion.getIntIdEmpresa());
	    prmtBusq.put("pIntIdPerfil",   		Constante.GERENCIA_GENERAL);
	    
	    ArrayList arrayPerfilConvenio = new ArrayList();
	    ArrayList listaPerfilConvenio = new ArrayList();
	    
	    log.info("prmtBusq: "+prmtBusq);
        try {
	    	arrayPerfilConvenio = getCreditosService().listarPerfilConvenio(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getCreditosService().listarPerfilConvenio() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("arrayPerfilConvenio.size(): "+arrayPerfilConvenio.size());
		for(int i=0; i<arrayPerfilConvenio.size(); i++){
			PerfilConvenio perfc = (PerfilConvenio) arrayPerfilConvenio.get(i);
			log.info("PEVA_IDVALIDACION_N 	= "	+perfc.getInIdValidacion());
			log.info("V_DESCRIPCION  		= "	+perfc.getStDescripcion());
			log.info("V_OBSERVACION 	   	= "	+perfc.getStObservacion());
			log.info("APDE_VALOR_N 	   		= "	+perfc.getInValorPerfDet());
			setStObsGerencGral(perfc.getStObservacion());
			perfc.setInEstadoPerf(perfc.getInValorPerfDet()==1);
			listaPerfilConvenio.add(perfc);
		}
		if(arrayPerfilConvenio.size()==0){
			setStObsGerencGral("");
		}
		log.info("listaPerfilConvenio.size(): "+listaPerfilConvenio.size());
		setBeanListAsistGerencia(listaPerfilConvenio);
	}
	
	/**************************************************************/
	/*  Nombre :  validarConvenio()		      		      	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                                                    	 */
	/*  Objetivo: Listar los Convenios y verificar los que   */
	/*            están pendientes de validar y/o Aprobar.   */
	/*  Retorno : Listado de Convenios                       */
	/**************************************************************/
	public void validarConvenio(ActionEvent event){
		log.info("----------------Debugging ControlProcesoController.validarConvenio-------------------");
		setCreditosService(controlProcesoService);
		log.info("Se ha seteado el Service");
		String strIdConvenio   = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:hiddenConvenioId");
		String strIdAmpliacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:hiddenAmpliacionId");
		
		Boolean bValidar = true;
		
		log.info("getEnableDisableEncConv(): "+getEnableDisableEncConv());
		if(getEnableDisableEncConv()==false){
			
			if(getStObsEncargConv().equals("")){
				bValidar = false;
				setMsgObservacion("Debe ingresar una observación para el Encargado de Convenio");
			}else{
				setMsgObservacion("");
			}
			
			if(bValidar == true){
				//PerfilConvenio perfconv = new PerfilConvenio();
				log.info("-----------------------Debugging HojaPlaneamientoController.eliminarPoblacion-----------------------------");
		    	HashMap prmtPob = new HashMap();
		    	prmtPob.put("pIntIdConvenio", 	Integer.parseInt(strIdConvenio));
		    	prmtPob.put("pIntIdAmpliacion", Integer.parseInt(strIdAmpliacion));
		    	prmtPob.put("pIntIdEmpresa", 	beanSesion.getIntIdEmpresa());
		    	prmtPob.put("pIntIdPerfil", 	beanSesion.getIntIdPerfil());
		    	
				try{
					getCreditosService().eliminarAdendaPerfil(prmtPob);
				}catch(DaoException e){
					log.info("ERROR  getCreditosService().grabarConvEstructDet(conv:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for(int i=0; i<getBeanListEncargConvenio().size(); i++){
					PerfilConvenio perf = (PerfilConvenio) getBeanListEncargConvenio().get(i);
					
					perf.setInIdConvenio(Integer.parseInt(strIdConvenio));
					perf.setInIdAmpliacion(Integer.parseInt(strIdAmpliacion));
					perf.setInIdEmpresa(beanSesion.getIntIdEmpresa());
					perf.setInIdPerfil(beanSesion.getIntIdPerfil());
					perf.setInIdValidacion(perf.getInIdValidacion());
					perf.setInValorPerfDet(perf.getInEstadoPerf()==true?1:0);
					perf.setStObservacion(getStObsEncargConv());
					
					try {
						getCreditosService().grabarAdendaPerfilDet(perf);
					} catch (DaoException e) {
						log.info("ERROR  getCreditosService().grabarConvEstructDet(conv:) " + e.getMessage());
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
		log.info("getEnableDisableJefCred(): "+getEnableDisableJefCred());
		if(getEnableDisableJefCred()==false){
			
			PerfilConvenio perfconv = new PerfilConvenio();
	    	HashMap prmtPob = new HashMap();
	    	prmtPob.put("pIntIdConvenio", 	Integer.parseInt(strIdConvenio));
	    	prmtPob.put("pIntIdAmpliacion", Integer.parseInt(strIdAmpliacion));
	    	prmtPob.put("pIntIdEmpresa", 	beanSesion.getIntIdEmpresa());
	    	prmtPob.put("pIntIdPerfil", 	beanSesion.getIntIdPerfil());
			try{
				getCreditosService().eliminarAdendaPerfil(prmtPob);
			}catch(DaoException e){
				log.info("ERROR  getCreditosService().grabarConvEstructDet(conv:) " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i=0; i<getBeanListJefatCreditos().size(); i++){
				PerfilConvenio perf = (PerfilConvenio) getBeanListJefatCreditos().get(i);
				
				perf.setInIdConvenio(Integer.parseInt(strIdConvenio));
				perf.setInIdAmpliacion(Integer.parseInt(strIdAmpliacion));
				perf.setInIdEmpresa(beanSesion.getIntIdEmpresa());
				perf.setInIdPerfil(beanSesion.getIntIdPerfil());
				perf.setInIdValidacion(perf.getInIdValidacion());
				perf.setInValorPerfDet(perf.getInEstadoPerf()==true?1:0);
				perf.setStObservacion(getStObsJefatCred());
				
				try {
					getCreditosService().grabarAdendaPerfilDet(perf);
				} catch (DaoException e) {
					log.info("ERROR  getCreditosService().grabarConvEstructDet(conv:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		log.info("getBeanListJefatCobranza(): "+getBeanListJefatCobranza());
		if(getEnableDisableJefCobr()==false){
			
			PerfilConvenio perfconv = new PerfilConvenio();
			log.info("-----------------------Debugging HojaPlaneamientoController.eliminarPoblacion-----------------------------");
	    	HashMap prmtPob = new HashMap();
	    	prmtPob.put("pIntIdConvenio", 	Integer.parseInt(strIdConvenio));
	    	prmtPob.put("pIntIdAmpliacion", Integer.parseInt(strIdAmpliacion));
	    	prmtPob.put("pIntIdEmpresa", 	beanSesion.getIntIdEmpresa());
	    	prmtPob.put("pIntIdPerfil", 	beanSesion.getIntIdPerfil());
			try{
				getCreditosService().eliminarAdendaPerfil(prmtPob);
			}catch(DaoException e){
				log.info("ERROR  getCreditosService().grabarConvEstructDet(conv:) " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i=0; i<getBeanListJefatCobranza().size(); i++){
				PerfilConvenio perf = (PerfilConvenio) getBeanListJefatCobranza().get(i);
				
				perf.setInIdConvenio(Integer.parseInt(strIdConvenio));
				perf.setInIdAmpliacion(Integer.parseInt(strIdAmpliacion));
				perf.setInIdEmpresa(beanSesion.getIntIdEmpresa());
				perf.setInIdPerfil(beanSesion.getIntIdPerfil());
				perf.setInIdValidacion(perf.getInIdValidacion());
				perf.setInValorPerfDet(perf.getInEstadoPerf()==true?1:0);
				perf.setStObservacion(getStObsJefatCobr());
				
				try {
					getCreditosService().grabarAdendaPerfilDet(perf);
				} catch (DaoException e) {
					log.info("ERROR  getCreditosService().grabarConvEstructDet(conv:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		log.info("getBeanListAsesorLegal(): "+getBeanListAsesorLegal());
		if(getEnableDisableAsesLeg()==false){
			
			PerfilConvenio perfconv = new PerfilConvenio();
			log.info("-----------------------Debugging HojaPlaneamientoController.eliminarPoblacion-----------------------------");
	    	HashMap prmtPob = new HashMap();
	    	prmtPob.put("pIntIdConvenio", 	Integer.parseInt(strIdConvenio));
	    	prmtPob.put("pIntIdAmpliacion", Integer.parseInt(strIdAmpliacion));
	    	prmtPob.put("pIntIdEmpresa", 	beanSesion.getIntIdEmpresa());
	    	prmtPob.put("pIntIdPerfil", 	beanSesion.getIntIdPerfil());
			try{
				getCreditosService().eliminarAdendaPerfil(prmtPob);
			}catch(DaoException e){
				log.info("ERROR  getCreditosService().grabarConvEstructDet(conv:) " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i=0; i<getBeanListAsesorLegal().size(); i++){
				PerfilConvenio perf = (PerfilConvenio) getBeanListAsesorLegal().get(i);
				
				perf.setInIdConvenio(Integer.parseInt(strIdConvenio));
				perf.setInIdAmpliacion(Integer.parseInt(strIdAmpliacion));
				perf.setInIdEmpresa(beanSesion.getIntIdEmpresa());
				perf.setInIdPerfil(beanSesion.getIntIdPerfil());
				perf.setInIdValidacion(perf.getInIdValidacion());
				perf.setInValorPerfDet(perf.getInEstadoPerf()==true?1:0);
				perf.setStObservacion(getStObsAsesLegal());
				
				try {
					getCreditosService().grabarAdendaPerfilDet(perf);
				} catch (DaoException e) {
					log.info("ERROR  getCreditosService().grabarConvEstructDet(conv:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**************************************************************/
	/*  Nombre :  aprobarRechazarConvenio()		      	   	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                                                    	 */
	/*  Objetivo: Aprobar o rechazar el Convenio 		     */
	/*                                                    	 */
	/*  Retorno : El listado de Convenios tendrá el estado   */
	/*            aprobado o rechazado                     	 */
	/**************************************************************/
	public void aprobarRechazarConvenio(ActionEvent event){
		log.info("----------------Debugging ControlProcesoController.aprobarRechazarConvenio-------------------");
		setCreditosService(controlProcesoService);
		
		log.info("Se ha seteado el Service");
		String strIdConvenio   = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:hiddenConvenioId");
		String strIdAmpliacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:hiddenAmpliacionId");
		
		UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String btnApruebaRechaza = uiComponent.getId();
		log.info("btnApruebaRechaza: "+btnApruebaRechaza);
		
		/*HojaPlaneamiento hoj = new HojaPlaneamiento();
		hoj.setnIdEstado(btnApruebaRechaza.equals("btnAprobar")?2:3);*/
		Boolean bValidar = true;
		
		log.info("getStObsEncargConv(): "+getStObsEncargConv());
		log.info("getStObsJefatCred(): "+getStObsJefatCred());
		log.info("getStObsJefatCobr(): "+getStObsJefatCobr());
		log.info("getStObsAsesLegal(): "+getStObsAsesLegal());
		
		if( getStObsEncargConv()==null || getStObsEncargConv().equals("") || 
			getStObsJefatCred()==null  || getStObsJefatCred().equals("")  || 
			getStObsJefatCobr()==null  || getStObsJefatCobr().equals("")  || 
			getStObsAsesLegal()==null  || getStObsAsesLegal().equals("")  ){
			bValidar = false;
			setMsgApruebaRechaza("No se puede " + 
					(btnApruebaRechaza.equals("btnAprobar")?"Aprobar":"Rechazar") +
					" el Convenio ya que aún no ha sido validado completamente.");
		}else{
			setMsgApruebaRechaza("");
		}
		if(getStObsGerencGral()==null || getStObsGerencGral().equals("")){
			setMsgObservacion("Debe ingresar una Observación");
			bValidar = false;
		}else{
			setMsgObservacion("");
		}
		
		HashMap prmtEst = new HashMap();
    	prmtEst.put("pIntIdConvenio", 	Integer.parseInt(strIdConvenio));
    	prmtEst.put("pIntIdAmpliacion", Integer.parseInt(strIdAmpliacion));
    	prmtEst.put("pIntIdEmpresa", 	beanSesion.getIntIdEmpresa());
    	prmtEst.put("pIntIdPerfil", 	beanSesion.getIntIdPerfil());
    	prmtEst.put("pStrObservacion", 	getStObsGerencGral());
    	prmtEst.put("pIntIdEstado", 	(btnApruebaRechaza.equals("btnAprobar")?2:3));
    	
    	if(bValidar == true){
			try{
				getCreditosService().aprobarRechazarConvenio(prmtEst);
			}catch(DaoException e){
				log.info("ERROR  getCreditosService().aprobarRechazarConvenio(conv:) " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				listarControlProceso(event);
			}
    	}
	}
	
	/**************************************************************/
	/*  Nombre :  cancelarConvenio()		   		      	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                                                    	 */
	/*  Objetivo: Cancelar el Convenio 		     			 */
	/*                                                    	 */
	/*  Retorno : El listado de Convenios tendrá el estado   */
	/*            aprobado o rechazado                     	 */
	/**************************************************************/
	public void cancelarConvenio(ActionEvent event){
		log.info("----------------Debugging ControlProcesoController.cancelarConvenio-------------------");
		setCreditosService(controlProcesoService);
		
		setEnableDisableFormControl(false);
		setEnableDisableBtnValidar(true);
		setEnableDisableBtnAprobar(true);
		setEnableDisableBtnRechazar(true);
	}
}