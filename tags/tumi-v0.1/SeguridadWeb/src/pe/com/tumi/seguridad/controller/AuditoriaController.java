package pe.com.tumi.seguridad.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.domain.Auditoria;
import pe.com.tumi.seguridad.domain.DataObjects;
import pe.com.tumi.seguridad.domain.FechasAuditoria;
import pe.com.tumi.seguridad.domain.FormReport;
import pe.com.tumi.seguridad.service.impl.AdminMenuServiceImpl;
import pe.com.tumi.seguridad.service.impl.AuditoriaServiceImpl;

public class AuditoriaController extends GenericController {
	private AuditoriaServiceImpl 			auditoriaService;
	private AdminMenuServiceImpl 			adminMenuService;
	private Integer							intCboEmpresa;
	private Integer							intCboTipoAplicacion;
	private Integer							intCboTipoSucursal;
	private Integer							intCboTipoReporte;
	private Integer							intCboTipoUsuario;
	private Integer							intCboTipoIngreso;
	private Date							daFechaIni;
	private Date							daFechaFin;
	private Integer							intCboUsuario;
	private String							strMenu01;
	private String							strMenu02;
	private String							strMenu03;
	private String							strMenu04;
	private List							beanListAplicacion = new  ArrayList();
	private List<FechasAuditoria> 			beanListColumns = new ArrayList<FechasAuditoria>();
	private String							msgTxtError;
	private String							msgTxtTipoReporte;
	private Boolean							chkRanFec;
	private Boolean							chkUsuario;
	private Boolean							formRanFecEnabled = true;
	private Boolean							formUsuarioEnabled = true;
	
	//Parámetros - Formulario y Reporte
	private String							strMenuFormApp01;
	private String							strMenuFormApp02;
	private String							strMenuFormApp03;
	private String							strMenuFormApp04;
	private List							beanListFormReport = new  ArrayList();
	private FormReport						beanFormReport = new FormReport();
	private Boolean							chkMenuFormApp;
	private Boolean							formRepMenuEnabled = true;	
	private Boolean							chkAplicaciones;
	private Boolean							formRepAplicacionesEnabled = true;
	private Boolean							formRepAppEnabled = true;
	private Boolean							chkTablas;
	private Boolean							formRepTablasEnabled = true;
	private Boolean							chkVistas;
	private Boolean							formRepVistasEnabled = true;
	private Boolean							chkTriggers;
	private Boolean							formRepTriggersEnabled = true;
	private Boolean							chkRanFecFormRep;
	private Boolean							formRepRanFecEnabled = true;
	
	private List							beanListDataObjects = new ArrayList();
	private List							beanListAplicaciones;	
	private List							beanListTablas;
	private List							beanListVistas;
	private List							beanListTriggers;
	private	ArrayList						arrayDataObjects = new ArrayList();
	private	String							strAdjuntarObjeto;
	private	String							strIdAplicacionesMenu;	
	private	String							strIdTablasMenu;
	private	String							strIdVistasMenu;
	private	String							strIdTriggersMenu;
	private	String							strAplicacionesMenu;
	private	String							strTablasMenu;
	private	String							strVistasMenu;
	private	String							strTriggersMenu;
	
	private    ArrayList<SelectItem> 		cboMenu1 = new ArrayList<SelectItem>();
	
	public String getStrIdAplicacionesMenu() {
		return strIdAplicacionesMenu;
	}
	public void setStrIdAplicacionesMenu(String strIdAplicacionesMenu) {
		this.strIdAplicacionesMenu = strIdAplicacionesMenu;
	}
	public Boolean getFormRepAplicacionesEnabled() {
		return formRepAplicacionesEnabled;
	}
	public void setFormRepAplicacionesEnabled(Boolean formRepAplicacionesEnabled) {
		this.formRepAplicacionesEnabled = formRepAplicacionesEnabled;
	}
	public List getBeanListAplicaciones() {
		return beanListAplicaciones;
	}
	public void setBeanListAplicaciones(List beanListAplicaciones) {
		this.beanListAplicaciones = beanListAplicaciones;
	}
	public String getStrAplicacionesMenu() {
		return strAplicacionesMenu;
	}
	public void setStrAplicacionesMenu(String strAplicacionesMenu) {
		this.strAplicacionesMenu = strAplicacionesMenu;
	}
	public ArrayList<SelectItem> getCboMenu1() {
		return cboMenu1;
	}
	public void setCboMenu1(ArrayList<SelectItem> cboMenu1) {
		this.cboMenu1 = cboMenu1;
	}
	//Getters y Setters
	public AuditoriaServiceImpl getAuditoriaService() {
		return auditoriaService;
	}
	public void setAuditoriaService(AuditoriaServiceImpl auditoriaService) {
		this.auditoriaService = auditoriaService;
	}
	public AdminMenuServiceImpl getAdminMenuService() {
		return adminMenuService;
	}
	public void setAdminMenuService(AdminMenuServiceImpl adminMenuService) {
		this.adminMenuService = adminMenuService;
	}
	public Integer getIntCboEmpresa() {
		return intCboEmpresa;
	}
	public void setIntCboEmpresa(Integer intCboEmpresa) {
		this.intCboEmpresa = intCboEmpresa;
	}
	public Integer getIntCboTipoAplicacion() {
		return intCboTipoAplicacion;
	}
	public void setIntCboTipoAplicacion(Integer intCboTipoAplicacion) {
		this.intCboTipoAplicacion = intCboTipoAplicacion;
	}
	public Integer getIntCboTipoSucursal() {
		return intCboTipoSucursal;
	}
	public void setIntCboTipoSucursal(Integer intCboTipoSucursal) {
		this.intCboTipoSucursal = intCboTipoSucursal;
	}
	public Integer getIntCboTipoReporte() {
		return intCboTipoReporte;
	}
	public void setIntCboTipoReporte(Integer intCboTipoReporte) {
		this.intCboTipoReporte = intCboTipoReporte;
	}
	public Integer getIntCboTipoUsuario() {
		return intCboTipoUsuario;
	}
	public void setIntCboTipoUsuario(Integer intCboTipoUsuario) {
		this.intCboTipoUsuario = intCboTipoUsuario;
	}
	public Integer getIntCboTipoIngreso() {
		return intCboTipoIngreso;
	}
	public void setIntCboTipoIngreso(Integer intCboTipoIngreso) {
		this.intCboTipoIngreso = intCboTipoIngreso;
	}
	public Date getDaFechaIni() {
		return daFechaIni;
	}
	public void setDaFechaIni(Date daFechaIni) {
		this.daFechaIni = daFechaIni;
	}
	public Date getDaFechaFin() {
		return daFechaFin;
	}
	public void setDaFechaFin(Date daFechaFin) {
		this.daFechaFin = daFechaFin;
	}
	public Integer getIntCboUsuario() {
		return intCboUsuario;
	}
	public void setIntCboUsuario(Integer intCboUsuario) {
		this.intCboUsuario = intCboUsuario;
	}
	public String getStrMenu01() {
		return strMenu01;
	}
	public void setStrMenu01(String strMenu01) {
		this.strMenu01 = strMenu01;
	}
	public String getStrMenu02() {
		return strMenu02;
	}
	public void setStrMenu02(String strMenu02) {
		this.strMenu02 = strMenu02;
	}
	public String getStrMenu03() {
		return strMenu03;
	}
	public void setStrMenu03(String strMenu03) {
		this.strMenu03 = strMenu03;
	}
	public String getStrMenu04() {
		return strMenu04;
	}
	public void setStrMenu04(String strMenu04) {
		this.strMenu04 = strMenu04;
	}
	public List getBeanListAplicacion() {
		return beanListAplicacion;
	}
	public void setBeanListAplicacion(List beanListAplicacion) {
		this.beanListAplicacion = beanListAplicacion;
	}
	public List getBeanListColumns() {
		return beanListColumns;
	}
	public void setBeanListColumns(List beanListColumns) {
		this.beanListColumns = beanListColumns;
	}
	public String getMsgTxtError() {
		return msgTxtError;
	}
	public void setMsgTxtError(String msgTxtError) {
		this.msgTxtError = msgTxtError;
	}
	public String getMsgTxtTipoReporte() {
		return msgTxtTipoReporte;
	}
	public void setMsgTxtTipoReporte(String msgTxtTipoReporte) {
		this.msgTxtTipoReporte = msgTxtTipoReporte;
	}
	public Boolean getChkRanFec() {
		return chkRanFec;
	}
	public void setChkRanFec(Boolean chkRanFec) {
		this.chkRanFec = chkRanFec;
	}
	public Boolean getChkUsuario() {
		return chkUsuario;
	}
	public void setChkUsuario(Boolean chkUsuario) {
		this.chkUsuario = chkUsuario;
	}
	public Boolean getFormRanFecEnabled() {
		return formRanFecEnabled;
	}
	public void setFormRanFecEnabled(Boolean formRanFecEnabled) {
		this.formRanFecEnabled = formRanFecEnabled;
	}
	public Boolean getFormUsuarioEnabled() {
		return formUsuarioEnabled;
	}
	public void setFormUsuarioEnabled(Boolean formUsuarioEnabled) {
		this.formUsuarioEnabled = formUsuarioEnabled;
	}
	//Parámetros - Formulario y Reporte
	public String getStrMenuFormApp01() {
		return strMenuFormApp01;
	}
	public void setStrMenuFormApp01(String strMenuFormApp01) {
		this.strMenuFormApp01 = strMenuFormApp01;
	}
	public String getStrMenuFormApp02() {
		return strMenuFormApp02;
	}
	public void setStrMenuFormApp02(String strMenuFormApp02) {
		this.strMenuFormApp02 = strMenuFormApp02;
	}
	public String getStrMenuFormApp03() {
		return strMenuFormApp03;
	}
	public void setStrMenuFormApp03(String strMenuFormApp03) {
		this.strMenuFormApp03 = strMenuFormApp03;
	}
	public String getStrMenuFormApp04() {
		return strMenuFormApp04;
	}
	public void setStrMenuFormApp04(String strMenuFormApp04) {
		this.strMenuFormApp04 = strMenuFormApp04;
	}
	public List getBeanListFormReport() {
		return beanListFormReport;
	}
	public void setBeanListFormReport(List beanListFormReport) {
		this.beanListFormReport = beanListFormReport;
	}
	public FormReport getBeanFormReport() {
		return beanFormReport;
	}
	public void setBeanFormReport(FormReport beanFormReport) {
		this.beanFormReport = beanFormReport;
	}
	public Boolean getChkMenuFormApp() {
		return chkMenuFormApp;
	}
	public void setChkMenuFormApp(Boolean chkMenuFormApp) {
		this.chkMenuFormApp = chkMenuFormApp;
	}
	public Boolean getFormRepMenuEnabled() {
		return formRepMenuEnabled;
	}
	public void setFormRepMenuEnabled(Boolean formRepMenuEnabled) {
		this.formRepMenuEnabled = formRepMenuEnabled;
	}
	public Boolean getChkAplicaciones() {
		return chkAplicaciones;
	}
	public void setChkAplicaciones(Boolean chkAplicaciones) {
		this.chkAplicaciones = chkAplicaciones;
	}
	public Boolean getFormRepAppEnabled() {
		return formRepAppEnabled;
	}
	public void setFormRepAppEnabled(Boolean formRepAppEnabled) {
		this.formRepAppEnabled = formRepAppEnabled;
	}
	public Boolean getChkTablas() {
		return chkTablas;
	}
	public void setChkTablas(Boolean chkTablas) {
		this.chkTablas = chkTablas;
	}
	public Boolean getFormRepTablasEnabled() {
		return formRepTablasEnabled;
	}
	public void setFormRepTablasEnabled(Boolean formRepTablasEnabled) {
		this.formRepTablasEnabled = formRepTablasEnabled;
	}
	public Boolean getChkVistas() {
		return chkVistas;
	}
	public void setChkVistas(Boolean chkVistas) {
		this.chkVistas = chkVistas;
	}
	public Boolean getFormRepVistasEnabled() {
		return formRepVistasEnabled;
	}
	public void setFormRepVistasEnabled(Boolean formRepVistasEnabled) {
		this.formRepVistasEnabled = formRepVistasEnabled;
	}
	public Boolean getChkTriggers() {
		return chkTriggers;
	}
	public void setChkTriggers(Boolean chkTriggers) {
		this.chkTriggers = chkTriggers;
	}
	public Boolean getFormRepTriggersEnabled() {
		return formRepTriggersEnabled;
	}
	public void setFormRepTriggersEnabled(Boolean formRepTriggersEnabled) {
		this.formRepTriggersEnabled = formRepTriggersEnabled;
	}
	public Boolean getChkRanFecFormRep() {
		return chkRanFecFormRep;
	}
	public void setChkRanFecFormRep(Boolean chkRanFecFormRep) {
		this.chkRanFecFormRep = chkRanFecFormRep;
	}
	public List getBeanListDataObjects() {
		return beanListDataObjects;
	}
	public void setBeanListDataObjects(List beanListDataObjects) {
		this.beanListDataObjects = beanListDataObjects;
	}
	public Boolean getFormRepRanFecEnabled() {
		return formRepRanFecEnabled;
	}
	public void setFormRepRanFecEnabled(Boolean formRepRanFecEnabled) {
		this.formRepRanFecEnabled = formRepRanFecEnabled;
	}
	public List getBeanListTablas() {
		return beanListTablas;
	}
	public void setBeanListTablas(List beanListTablas) {
		this.beanListTablas = beanListTablas;
	}
	public List getBeanListVistas() {
		return beanListVistas;
	}
	public void setBeanListVistas(List beanListVistas) {
		this.beanListVistas = beanListVistas;
	}
	public List getBeanListTriggers() {
		return beanListTriggers;
	}
	public void setBeanListTriggers(List beanListTriggers) {
		this.beanListTriggers = beanListTriggers;
	}
	public ArrayList getArrayDataObjects() {
		return arrayDataObjects;
	}
	public void setArrayDataObjects(ArrayList arrayDataObjects) {
		this.arrayDataObjects = arrayDataObjects;
	}
	public String getStrAdjuntarObjeto() {
		return strAdjuntarObjeto;
	}
	public void setStrAdjuntarObjeto(String strAdjuntarObjeto) {
		this.strAdjuntarObjeto = strAdjuntarObjeto;
	}
	public String getStrIdTablasMenu() {
		return strIdTablasMenu;
	}
	public void setStrIdTablasMenu(String strIdTablasMenu) {
		this.strIdTablasMenu = strIdTablasMenu;
	}
	public String getStrIdVistasMenu() {
		return strIdVistasMenu;
	}
	public void setStrIdVistasMenu(String strIdVistasMenu) {
		this.strIdVistasMenu = strIdVistasMenu;
	}
	public String getStrIdTriggersMenu() {
		return strIdTriggersMenu;
	}
	public void setStrIdTriggersMenu(String strIdTriggersMenu) {
		this.strIdTriggersMenu = strIdTriggersMenu;
	}
	public String getStrTablasMenu() {
		return strTablasMenu;
	}
	public void setStrTablasMenu(String strTablasMenu) {
		this.strTablasMenu = strTablasMenu;
	}
	public String getStrVistasMenu() {
		return strVistasMenu;
	}
	public void setStrVistasMenu(String strVistasMenu) {
		this.strVistasMenu = strVistasMenu;
	}
	public String getStrTriggersMenu() {
		return strTriggersMenu;
	}
	public void setStrTriggersMenu(String strTriggersMenu) {
		this.strTriggersMenu = strTriggersMenu;
	}
	//---------------------------------------------------
	//Métodos de AuditoriaController
	//---------------------------------------------------
	public void listarAplicacion(ActionEvent event) throws ParseException{
		log.info("--------------------Debugging AuditoriaController.listarAplicacion-----------------------");
	    setService(auditoriaService);
	    log.info("Se ha seteado el Service");
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		Date daFecIni = getDaFechaIni();
	    String daFechaIni = (daFecIni == null ? "" : sdf2.format(daFecIni));
	    Date daFecFin = getDaFechaFin();
	    String daFechaFin = (daFecFin == null ? "" : sdf2.format(daFecFin));
		
	    Boolean bValidar = true;
	    log.info("daFechaIni: " + daFechaIni);
	    log.info("daFechaFin: " + daFechaFin);
	    
	    if(daFechaIni.equals("") || daFechaFin.equals("")){
	    	setMsgTxtError("Debe un rango de fechas para realizar la consulta.");
	    	bValidar = false;
	    }else{
	    	setMsgTxtError("");
	    }
	    if(getIntCboTipoReporte()==0){
	    	setMsgTxtTipoReporte("Debe elegir un tipo de Reporte.");
	    	bValidar = false;
	    }else{
	    	setMsgTxtTipoReporte("");
	    }
	    
	    Date fecIniL = (!daFechaIni.equals("")?sdf2.parse(daFechaIni):null);
		Date fecFinL = (!daFechaFin.equals("")?sdf2.parse(daFechaFin):null);
		
	    if (fecIniL.compareTo(fecFinL) > 0) {
			setMsgTxtError("La Fecha Inicial es mayor a la Final");
			bValidar = false;
		} else if (fecIniL.equals(fecFinL)) {
			setMsgTxtError("Las Fechas son iguales");
			bValidar = false;
		} else if(fecFinL==null){
			setMsgTxtError("Debe ingresar una Fecha de Fin");
			bValidar = false;
		}else{
			setMsgTxtError("");
		}
	    
	    String strIdTransacciones = "";
	    strIdTransacciones = (getStrMenu01()==null||getStrMenu01().equals("")?"":(getStrMenu01()+ ","))+
	    					 (getStrMenu02()==null||getStrMenu02().equals("")?"":(getStrMenu02()+ ","))+
	    					 (getStrMenu03()==null||getStrMenu03().equals("")?"":(getStrMenu03()+ ","))+
	    					 (getStrMenu04()==null||getStrMenu04().equals("")?"":getStrMenu04());
	    
	    log.info("strIdTransacciones: "+strIdTransacciones);
	    
	    HashMap prmtBusq = new HashMap();
	    prmtBusq.put("pIntIdEmpresa", 			getIntCboEmpresa());
	    prmtBusq.put("pIntIdTipoAplicacion", 	getIntCboTipoAplicacion());
	    prmtBusq.put("pStrIdTransacciones", 	strIdTransacciones);
	    prmtBusq.put("pIntIdPersona", 			getIntCboUsuario());
	    prmtBusq.put("pIntIdTipoReporte",		getIntCboTipoReporte());
		prmtBusq.put("pDaFecIni", 				daFechaIni);
		prmtBusq.put("pDaFecFin", 				daFechaFin);
	    
	    ArrayList arrayAplicacion = new ArrayList();
	    ArrayList listaAplicacion = new ArrayList();
	    
	    ArrayList arrayCols = new ArrayList();
	    ArrayList listaCols = new ArrayList();
	    
	    if(bValidar == true){
	    	try {
		    	arrayAplicacion = getService().listarAplicacion(prmtBusq);//List of Rows
		    	arrayCols 		= getService().listarCols(prmtBusq);//List of Columns
			} catch (DaoException e) {
				log.info("ERROR  getService().listarAplicacion() " + e.getMessage());
				log.info("ERROR  getService().listarCols() " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<String[]> arrayTransacciones = new ArrayList<String[]>();
			String strIds = "";
			log.info("arrayAplicacion.size(): "+arrayAplicacion.size());
			for(int i=0; i<arrayAplicacion.size(); i++){
				Auditoria aud = new Auditoria();
				aud = (Auditoria)arrayAplicacion.get(i);
				String[] strTran = new String[2];
				strTran[0] = aud.getStrIdTransaccion();
				strTran[1] = aud.getStrTransaccion();
				log.info("arrayTransacciones.size(): "+arrayTransacciones.size());
				for(int j=0; j<arrayTransacciones.size(); j++){
					String[] strAux = new String[2];
					strAux = (String[]) arrayTransacciones.get(j);
					strIds = strIds + strAux[0];
				}
				if(!strIds.contains(aud.getStrIdTransaccion())){
					arrayTransacciones.add(strTran);
				}
			}
			log.info("arrayTransacciones.size(): "+arrayTransacciones.size());
			
			ArrayList<Auditoria> lsCruce = new ArrayList<Auditoria>();
			for(int i=0; i<arrayTransacciones.size(); i++){
				String[] strTran = new String[2];
				strTran = arrayTransacciones.get(i);
				String[] arrayFechas = new String[arrayCols.size()];
				
				for(int k=0; k<arrayCols.size(); k++){
					FechasAuditoria fecha = new FechasAuditoria();
					fecha = (FechasAuditoria) arrayCols.get(k);
					arrayFechas[k]="";
					log.info("arrayFechas.length: "+arrayFechas.length);
					for(int j=0; j<arrayAplicacion.size(); j++){
						Auditoria aud = new Auditoria();
						aud = (Auditoria) arrayAplicacion.get(j);
						if(strTran[0].equals(aud.getStrIdTransaccion())){
							if(aud.getDaFecRegistro().equals(fecha.getStrCabecera())){
								arrayFechas[k] = ""+aud.getIntCntAcceso();
							}
						}
					}
					log.info("arrayFechas["+k+"]: "+arrayFechas[k]);
				}
				
				log.info("arrayFechas.length: "+arrayFechas.length);
				Auditoria audi = new Auditoria();
				audi.setStrTransaccion(strTran[1]);
				audi.setLstFechas(arrayFechas);
				int sum=0;
				for(int m=0;m<arrayFechas.length; m++){
					log.info("arrayFechas[m]:"+arrayFechas[m]);
					if(!arrayFechas[m].equals("")){
						sum += Integer.parseInt(arrayFechas[m]);
					}
				}
				log.info("sum: "+sum);
				audi.setIntSumCntAcceso(sum);
				lsCruce.add(audi);
			}
			setBeanListAplicacion(lsCruce);
			setBeanListColumns(arrayCols);
	    }
	}
	
	public void enableDisable(ActionEvent event) throws DaoException{
		log.info("----------------Debugging AuditoriaController.enableDisable-------------------");
		setFormRanFecEnabled(getChkRanFec()==false);
		setFormUsuarioEnabled(getChkUsuario()==false);
	}
	
	public void enableDisableFormRep(ActionEvent event) throws DaoException{
		log.info("----------------Debugging AuditoriaController.enableDisableFormRep-------------------");
		setFormRepMenuEnabled(getChkMenuFormApp()==false);
		setFormRepAppEnabled(getChkAplicaciones()==false);
		setFormRepTablasEnabled(getChkTablas()==false);
		setFormRepVistasEnabled(getChkVistas()==false);
		setFormRepTriggersEnabled(getChkTriggers()==false);
		setFormRepRanFecEnabled(getChkRanFecFormRep()==false);
		
		if(getChkMenuFormApp()==false){
			log.info("paso");
			setStrMenuFormApp01("0");
			setStrMenuFormApp02("0");
			setStrMenuFormApp03("0");
			setStrMenuFormApp04("0");
		}
		
		ValueChangeEvent event1 = null; 
		ArrayList listaMenu = new ArrayList();
		listaMenu = getListaCboMenu(event1);
		this.cboMenu1 = listaMenu;		
	}
	
	public void listarFormReporte(ActionEvent event){
		log.info("-----------------------Debugging AuditoriaController.listarFormReporte-----------------------------");
	    setService(auditoriaService);
	    log.info("Se ha seteado el Service");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		Date daFecIni = getDaFechaIni();
	    String daFechaIni = (daFecIni == null ? "" : sdf2.format(daFecIni));
	    Date daFecFin = getDaFechaFin();
	    String daFechaFin = (daFecFin == null ? "" : sdf2.format(daFecFin));
	    
	    String strIdTransacciones = "";
	    strIdTransacciones = (getStrMenuFormApp01()==null||getStrMenuFormApp01().equals("")?"":(getStrMenuFormApp01()+ ","))+
	    					 (getStrMenuFormApp02()==null||getStrMenuFormApp02().equals("")?"":(getStrMenuFormApp02()+ ","))+
	    					 (getStrMenuFormApp03()==null||getStrMenuFormApp03().equals("")?"":(getStrMenuFormApp03()+ ","))+
	    					 (getStrMenuFormApp04()==null||getStrMenuFormApp04().equals("")?"":getStrMenuFormApp04());
		
	    //strIdTransacciones = strIdTransacciones.substring(2,strIdTransacciones.length());
	    
	    HashMap prmtBusq = new HashMap();
	    prmtBusq.put("pDaFecIni", 				daFechaIni);
		prmtBusq.put("pDaFecFin", 				daFechaFin);
		prmtBusq.put("pIdTransacciones",		(getChkMenuFormApp()==true?strIdTransacciones:""));
		prmtBusq.put("pIdDataObjects", 			((getStrIdTablasMenu()==null||getStrIdTablasMenu().equals("")?"":getStrIdTablasMenu())+
												 (getStrIdVistasMenu()==null||getStrIdVistasMenu().equals("")?"":getStrIdVistasMenu())+
												 (getStrIdTriggersMenu()==null||getStrIdTriggersMenu().equals("")?"":getStrIdTriggersMenu())));
	    
	    ArrayList arrayFormReport = new ArrayList();
	    ArrayList listaFormReport = new ArrayList();
	    try {
	    	arrayFormReport = getService().listarFormReport(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarRegPol() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("arrayFormReport.size(): "+arrayFormReport.size());
		for(int i=0; i<arrayFormReport.size(); i++){
			FormReport formrep = (FormReport) arrayFormReport.get(i);
			log.info("ID_EMPRESA = "	+formrep.getIntIdEmpresa());
			log.info("ID_TIPOCAMBIO = "	+formrep.getIntIdTipoCambio());
			log.info("TIPO_CAMBIO_V = "	+formrep.getStrTipoCambio());
			log.info("ID_TRANSACCION =" +formrep.getStrIdTransaccion());
			log.info("ID_ITEM = " 		+formrep.getIntIdItem());
			log.info("V_DESCRIPCION = "	+formrep.getStrIdTransaccion());
			log.info("V_FINALIDAD = "	+formrep.getStrFinalidad());
			log.info("ID_ANEXOS"		+formrep.getIntIdAnexos());
			log.info("ID_CLASE = "		+formrep.getIntIdClase());
			log.info("V_CLASE = "		+formrep.getStrClase());
			log.info("D_FECSOLICITUD = "+formrep.getStrFecSolicitud());
			log.info("ID_SOLICITANTE = "+formrep.getIntIdSolicitante());
			log.info("V_OBSERVACION = "	+formrep.getStrObservacion());
			log.info("ID_ESTADO = "		+formrep.getIntIdEstado());
			log.info("V_DESCRIPCION = "	+formrep.getStrDescripcion());
			
			listaFormReport.add(formrep);
		}
		log.info("listaFormReport.size(): "+listaFormReport.size());
		setBeanListFormReport(listaFormReport);
	}
	
	public void editFormReport(ActionEvent event){
		log.info("-----------------------Debugging AuditoriaController.editFormReport-----------------------------");
	    setService(auditoriaService);
	    log.info("Se ha seteado el Service");
	    String strDescripcion 	= getRequestParameter("strDescripcion");
	    String strDesarrollador = getRequestParameter("strDesarrollador");
	    String strSolicitante 	= getRequestParameter("strSolicitante");
	    
	    FormReport formrep = new FormReport();
	    formrep.setStrDescripcion(strDescripcion);
	    formrep.setStrDesarrollador(strDesarrollador);
	    formrep.setStrSolicitante(strSolicitante);
	    setBeanFormReport(formrep);
	}
	
	public void listarAplicaciones(ActionEvent event) throws DaoException{
		listarDataObjects("aplicaciones");
	}
	
	public void listarTablas(ActionEvent event) throws DaoException{
		listarDataObjects("tablas");
	}
	
	public void listarVistas(ActionEvent event) throws DaoException{
		listarDataObjects("vistas");
	}
	
	public void listarTriggers(ActionEvent event) throws DaoException{
		listarDataObjects("triggers");
	}
	
	public void listarDataObjects(String tipoObjeto) throws DaoException{
		log.info("-----------------------Debugging AdminMenuController.listarTablas-----------------------------");
		setService(adminMenuService);
		HashMap prmtTablas = new HashMap();
		prmtTablas.put("pStrIdTransaccion", null);
		
		ArrayList arrayDataObjects = new ArrayList();
		ArrayList arrayAplicaciones = new ArrayList();
		ArrayList arrayTablas = new ArrayList();
		ArrayList arrayVistas = new ArrayList();
		ArrayList arrayTriggers = new ArrayList();
		arrayDataObjects = getService().listarDataObjects(prmtTablas);
		log.info("arrayDataObjects.size(): "+arrayDataObjects.size());
		
		for(int i=0; i<arrayDataObjects.size();i++){
			DataObjects tb = new DataObjects();
			tb = (DataObjects) arrayDataObjects.get(i);
			log.info("DICC_CODIGO_N:"+tb.getIntCodigo());
			log.info("DICC_TIPO_N:"+tb.getIntTipoObjecto());
			Integer intTipo = tb.getIntTipoObjecto();
			if(intTipo==null)tb.setIntTipoObjecto(0);
			log.info("DICC_DESCRIPCION_V: "+tb.getStrNombreObjeto());
			if(tb.getIntTipoObjecto()==1){
				arrayTablas.add(tb);
			}else if(tb.getIntTipoObjecto()==2){
				arrayVistas.add(tb);
			}else if(tb.getIntTipoObjecto()==3){
				arrayTriggers.add(tb);
			}else if(tb.getIntTipoObjecto()==4){
				arrayAplicaciones.add(tb);
			}
		}
		log.info("arrayAplicaciones.size(): "+arrayAplicaciones.size());
		log.info("arrayTablas.size(): "+arrayTablas.size());
		log.info("arrayVistas.size(): "+arrayVistas.size());
		log.info("arrayTriggers.size(): "+arrayTriggers.size());
		
		if(beanListAplicaciones==null || beanListAplicaciones.size()==0){
			log.info("Inicializando beanListAplicaciones...");
			beanListAplicaciones = new ArrayList();
			setBeanListAplicaciones(arrayAplicaciones);
		}
		
		if(beanListTablas==null || beanListTablas.size()==0){
			log.info("Inicializando beanListTablas...");
			beanListTablas = new ArrayList();
			setBeanListTablas(arrayTablas);
		}
		
		if(beanListVistas==null || beanListVistas.size()==0){
			log.info("Inicializando beanListVistas...");
			beanListVistas = new ArrayList();
			setBeanListVistas(arrayVistas);
		}
		
		if(beanListTriggers==null || beanListTriggers.size()==0){
			log.info("Inicializando beanListTriggers...");
			beanListTriggers = new ArrayList();
			setBeanListTriggers(arrayTriggers);
		}
		
		log.info("tipoObjeto: "+tipoObjeto);
		if(tipoObjeto.equals("tablas")){
			setStrAdjuntarObjeto("adjuntarTablas");
			log.info("beanListTablas.size(): "+beanListTablas.size());
			setBeanListDataObjects(beanListTablas);
		}else if(tipoObjeto.equals("vistas")){
			setStrAdjuntarObjeto("adjuntarVistas");
			log.info("beanListVistas.size(): "+beanListVistas.size());
			setBeanListDataObjects(beanListVistas);
		}else if(tipoObjeto.equals("triggers")){
			setStrAdjuntarObjeto("adjuntarTriggers");
			log.info("beanListTriggers.size(): "+beanListTriggers.size());
			setBeanListDataObjects(beanListTriggers);
		}else if(tipoObjeto.equals("aplicaciones")){
			setStrAdjuntarObjeto("adjuntarAplicaciones");
			log.info("beanListAplicaciones.size(): "+beanListAplicaciones.size());
			setBeanListDataObjects(beanListAplicaciones);
		}
		log.info("beanListDataObjects.size(): "+beanListDataObjects.size());
	}
	
	public void adjuntarDataObjects(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging AdminMenuController.adjuntarDataObjects-----------------------------");
		ArrayList arrayDBObjects = new ArrayList();
		arrayDBObjects = (ArrayList) getBeanListDataObjects();
		
		String strIdDataObjects = "";
		String strDataObjects = "";
		setStrIdAplicacionesMenu("");
		setStrAplicacionesMenu("");
		setStrIdTablasMenu("");
		setStrTablasMenu("");
		setStrIdVistasMenu("");
		setStrVistasMenu("");
		setStrIdTriggersMenu("");
		setStrTriggersMenu("");
		
		log.info("arrayDBObjects.size(): "+arrayDBObjects.size());
		for(int i=0; i<arrayDBObjects.size(); i++){
			DataObjects tb = new DataObjects();
			tb = (DataObjects)arrayDBObjects.get(i);
			log.info("tb.getStrNombreTabla(): "+tb.getStrNombreObjeto());
			log.info("tb.getBlnSelectedTable(): "+tb.getBlnSelectedObjecto());
			if(tb.getBlnSelectedObjecto()==true){
				strIdDataObjects = strIdDataObjects + ", " + tb.getIntCodigo();
				strDataObjects = strDataObjects +", "+tb.getStrNombreObjeto();
			}
		}
		
		if(!strDataObjects.equals("")){
			//strIdDataObjects = strIdDataObjects.substring(2,strIdDataObjects.length());
			strDataObjects = strDataObjects.substring(2,strDataObjects.length());
			log.info("getStrAdjuntarObjeto(): "+getStrAdjuntarObjeto());
			if(getStrAdjuntarObjeto().equals("adjuntarTablas")){
				setStrIdTablasMenu(strIdDataObjects);
				setStrTablasMenu(strDataObjects);
			}else if(getStrAdjuntarObjeto().equals("adjuntarVistas")){
				setStrIdVistasMenu(strIdDataObjects);
				setStrVistasMenu(strDataObjects);
			}else if(getStrAdjuntarObjeto().equals("adjuntarTriggers")){
				setStrIdTriggersMenu(strIdDataObjects);
				setStrTriggersMenu(strDataObjects);
			}else if(getStrAdjuntarObjeto().equals("adjuntarAplicaciones")){
				setStrIdAplicacionesMenu(strIdDataObjects);
				setStrAplicacionesMenu(strDataObjects);
			}
			log.info("strDataObjects: "+strDataObjects);
		}
	}
	
	public void selectAll(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging AdminMenuController.selectAll-----------------------------");
		setService(auditoriaService);
		log.info("se ha seteado el Service.");
		String strSelectAll 	= getRequestParameter("selectAll");
		log.info("strSelectAll: "+ strSelectAll);
		ArrayList arrayDBObjects = new ArrayList();
		arrayDBObjects = (ArrayList) getBeanListDataObjects();
		
		for(int i=0;i<arrayDBObjects.size(); i++){
			DataObjects tb = new DataObjects();
			tb = (DataObjects)arrayDBObjects.get(i);
			tb.setBlnSelectedObjecto(strSelectAll!=null && strSelectAll.equals("selectAll"));
		}
	}		
	
	public ArrayList getListaCboMenu(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboMenu()-----------------------------");
		//UIComponent uiComponent = event.getComponent();
		//log.info("uiComponent = "+uiComponent.getId());
		//String cboId=uiComponent.getId();
		setService(adminMenuService);
		//String idCboMenu = (String)event.getNewValue();
		
		//log.info("idCboMenu() = "+idCboMenu);
		int idEmpresa = 163;
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", null);
		prmtBusq.put("pIntIdEmpresa", idEmpresa);
		prmtBusq.put("pIntIdPerfil", null);
		prmtBusq.put("pStrNivel", "1");
		
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarMenuPerfil(prmtBusq);
	    log.info("arrayMenu: "+arrayMenu.size());
	    
	    ArrayList<SelectItem> arrayCbo = new ArrayList<SelectItem>();
	    
	    for(int i=0; i<arrayMenu.size() ; i++){
	    	HashMap hash = (HashMap) arrayMenu.get(i);
			AdminMenu menu = new AdminMenu();
			log.info("TRAN_IDTRANSACCION_C = "+hash.get("TRAN_IDTRANSACCION_C"));
			String strIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			menu.setStrIdTransaccion(strIdTransaccion);
			log.info("TRAN_NOMBRE_V = "+hash.get("TRAN_NOMBRE_V"));
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			menu.setStrNombre(strNombre);
			arrayCbo.add(new SelectItem(menu.getStrIdTransaccion(),menu.getStrNombre()));
        }
        SelectItem lblSelect = new SelectItem("0","Seleccionar..");
        arrayCbo.add(0, lblSelect);
        log.info("arrayCbo.size(): "+arrayCbo.size());
        
    	this.cboMenu1.clear();    
		return arrayCbo;
	}
}