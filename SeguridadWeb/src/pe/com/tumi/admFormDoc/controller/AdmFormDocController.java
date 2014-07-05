package pe.com.tumi.admFormDoc.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import pe.com.tumi.admFormDoc.domain.AdmFormDoc;
import pe.com.tumi.admFormDoc.service.impl.AdmFormDocServiceImpl;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.ConstanteReporte;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.usuario.service.impl.UsuarioServiceImpl;

/************************************************************************/
/* Nombre de la clase: AdmFormDocController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de la Relación entre Formulario y Documentación */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 14/11/2011 */
/* ********************************************************************* */

public class AdmFormDocController extends GenericController {
	private AdmFormDocServiceImpl 	admFormDocService;
	private UsuarioServiceImpl 		usuarioPerfilService;
	private int 					rows = 5;
	private	int						scrollerPage;
	private List					beanListFormDoc;
	private List					beanListAdmDoc;
	private AdmFormDoc 				beanFormDoc = new AdmFormDoc();
	private Boolean 				admFormDocRendered = false;
	private Boolean 				strAdjuntoDoc = false;
	private Boolean 				strAdjuntoDemo = false;
	private Boolean 				formAdmDocEnabled = true;
	private Boolean 				chkTodosEnabled = true;
	private String					txtEmpresa;
	private String					strCboEmpresaFormDoc;
	private Integer					intCboPerfilFormDoc;
	private int						cboEstado;
	private Integer					cboTipoUsuario;
	private	Integer					intCboEmpresa;
	private	Integer					intCboPerfilEmpresa;
	private Boolean					chkPerfil;
	private String					strCboMenu01;
	private String					strCboMenu02;
	private String					strCboMenu03;
	private String					strCboMenu04;
	private List					beanListMenues;
	private String					msgTxtEmpresa;
	private String					msgTxtEstado;
	private String					msgTxtPerfil;
	private String					msgTxtDocum;
	private String					msgTxtDemo;
	private String					msgTxtTransaccion;
	private String					hiddenStrIdTransaccion;
	private Boolean 				cboPerfilRendered = true;
	//Parámetros de Administración de Documentación
	private Integer					cboEmpresaAdmDoc;
	private Integer					cboPerfilAdmDoc;
	private String					txtEmpresaAdmDoc;
	private String					txtPerfilAdmDoc;
	private int						cboEstadoAdmDoc;
	private String					strArcDoc;
	private String					strArcDemo;
	private int						intVerDoc;
	private int						intVerDemo;
	private String					strCboTransaccion;
	private String					strCboMenuAdmDoc01;
	private String					strCboMenuAdmDoc02;
	private String					strCboMenuAdmDoc03;
	private String					strCboMenuAdmDoc04;
	private String				 	hiddenIdPerfilFormDoc;
	private String				 	hiddenIdEmpresaFormDoc;
	private ArrayList<SelectItem> 	cboMenuPerfil1 = new ArrayList<SelectItem>();
	private String				 	strRadioMenu1;	
	private String				 	strRadioMenu2;
	private String				 	strRadioMenu3;
	private ArrayList<SelectItem> 	cboMenu2 = new ArrayList<SelectItem>();	
	private ArrayList<SelectItem> 	cboMenu3 = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> 	cboMenu4 = new ArrayList<SelectItem>();
	
	public ArrayList<SelectItem> getCboMenu2() {
		return cboMenu2;
	}
	public void setCboMenu2(ArrayList<SelectItem> cboMenu2) {
		this.cboMenu2 = cboMenu2;
	}
	public ArrayList<SelectItem> getCboMenu3() {
		return cboMenu3;
	}
	public void setCboMenu3(ArrayList<SelectItem> cboMenu3) {
		this.cboMenu3 = cboMenu3;
	}
	public ArrayList<SelectItem> getCboMenu4() {
		return cboMenu4;
	}
	public void setCboMenu4(ArrayList<SelectItem> cboMenu4) {
		this.cboMenu4 = cboMenu4;
	}
	public String getStrRadioMenu1() {
		return strRadioMenu1;
	}
	public void setStrRadioMenu1(String strRadioMenu1) {
		this.strRadioMenu1 = strRadioMenu1;
	}
	public String getStrRadioMenu2() {
		return strRadioMenu2;
	}
	public void setStrRadioMenu2(String strRadioMenu2) {
		this.strRadioMenu2 = strRadioMenu2;
	}
	public String getStrRadioMenu3() {
		return strRadioMenu3;
	}
	public void setStrRadioMenu3(String strRadioMenu3) {
		this.strRadioMenu3 = strRadioMenu3;
	}
	public ArrayList<SelectItem> getCboMenuPerfil1() {
		return cboMenuPerfil1;
	}
	public void setCboMenuPerfil1(ArrayList<SelectItem> cboMenuPerfil1) {
		this.cboMenuPerfil1 = cboMenuPerfil1;
	}
	public String getHiddenIdEmpresaFormDoc() {
		return hiddenIdEmpresaFormDoc;
	}
	public void setHiddenIdEmpresaFormDoc(String hiddenIdEmpresaFormDoc) {
		this.hiddenIdEmpresaFormDoc = hiddenIdEmpresaFormDoc;
	}
	public String getHiddenIdPerfilFormDoc() {
		return hiddenIdPerfilFormDoc;
	}
	public void setHiddenIdPerfilFormDoc(String hiddenIdPerfilFormDoc) {
		this.hiddenIdPerfilFormDoc = hiddenIdPerfilFormDoc;
	}
	public AdmFormDocServiceImpl getAdmFormDocService() {
		return admFormDocService;
	}
	public void setAdmFormDocService(AdmFormDocServiceImpl admFormDocService) {
		this.admFormDocService = admFormDocService;
	}
	public UsuarioServiceImpl getUsuarioPerfilService() {
		return usuarioPerfilService;
	}
	public void setUsuarioPerfilService(UsuarioServiceImpl usuarioPerfilService) {
		this.usuarioPerfilService = usuarioPerfilService;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getScrollerPage() {
		return scrollerPage;
	}
	public void setScrollerPage(int scrollerPage) {
		this.scrollerPage = scrollerPage;
	}
	public List getBeanListFormDoc() {
		return beanListFormDoc;
	}
	public void setBeanListFormDoc(List beanListFormDoc) {
		this.beanListFormDoc = beanListFormDoc;
	}
	public List getBeanListAdmDoc() {
		return beanListAdmDoc;
	}
	public void setBeanListAdmDoc(List beanListAdmDoc) {
		this.beanListAdmDoc = beanListAdmDoc;
	}
	public AdmFormDoc getBeanFormDoc() {
		return beanFormDoc;
	}
	public void setBeanFormDoc(AdmFormDoc beanFormDoc) {
		this.beanFormDoc = beanFormDoc;
	}
	public Boolean getAdmFormDocRendered() {
		return admFormDocRendered;
	}
	public void setAdmFormDocRendered(Boolean admFormDocRendered) {
		this.admFormDocRendered = admFormDocRendered;
	}
	public Boolean getStrAdjuntoDoc() {
		return strAdjuntoDoc;
	}
	public void setStrAdjuntoDoc(Boolean strAdjuntoDoc) {
		this.strAdjuntoDoc = strAdjuntoDoc;
	}
	public Boolean getStrAdjuntoDemo() {
		return strAdjuntoDemo;
	}
	public void setStrAdjuntoDemo(Boolean strAdjuntoDemo) {
		this.strAdjuntoDemo = strAdjuntoDemo;
	}
	public Boolean getFormAdmDocEnabled() {
		return formAdmDocEnabled;
	}
	public void setFormAdmDocEnabled(Boolean formAdmDocEnabled) {
		this.formAdmDocEnabled = formAdmDocEnabled;
	}
	public Boolean getChkTodosEnabled() {
		return chkTodosEnabled;
	}
	public void setChkTodosEnabled(Boolean chkTodosEnabled) {
		this.chkTodosEnabled = chkTodosEnabled;
	}
	public String getTxtEmpresa() {
		return txtEmpresa;
	}
	public void setTxtEmpresa(String txtEmpresa) {
		this.txtEmpresa = txtEmpresa;
	}
	public String getStrCboEmpresaFormDoc() {
		return strCboEmpresaFormDoc;
	}
	public void setStrCboEmpresaFormDoc(String strCboEmpresaFormDoc) {
		this.strCboEmpresaFormDoc = strCboEmpresaFormDoc;
	}
	public Integer getIntCboPerfilFormDoc() {
		return intCboPerfilFormDoc;
	}
	public void setIntCboPerfilFormDoc(Integer intCboPerfilFormDoc) {
		this.intCboPerfilFormDoc = intCboPerfilFormDoc;
	}
	public int getCboEstado() {
		return cboEstado;
	}
	public void setCboEstado(int cboEstado) {
		this.cboEstado = cboEstado;
	}
	public Integer getCboTipoUsuario() {
		return cboTipoUsuario;
	}
	public void setCboTipoUsuario(Integer cboTipoUsuario) {
		this.cboTipoUsuario = cboTipoUsuario;
	}
	public Integer getIntCboEmpresa() {
		return intCboEmpresa;
	}
	public void setIntCboEmpresa(Integer intCboEmpresa) {
		this.intCboEmpresa = intCboEmpresa;
	}
	public Integer getIntCboPerfilEmpresa() {
		return intCboPerfilEmpresa;
	}
	public void setIntCboPerfilEmpresa(Integer intCboPerfilEmpresa) {
		this.intCboPerfilEmpresa = intCboPerfilEmpresa;
	}
	public Boolean getChkPerfil() {
		return chkPerfil;
	}
	public void setChkPerfil(Boolean chkPerfil) {
		this.chkPerfil = chkPerfil;
	}
	public String getStrCboMenu01() {
		return strCboMenu01;
	}
	public void setStrCboMenu01(String strCboMenu01) {
		this.strCboMenu01 = strCboMenu01;
	}
	public String getStrCboMenu02() {
		return strCboMenu02;
	}
	public void setStrCboMenu02(String strCboMenu02) {
		this.strCboMenu02 = strCboMenu02;
	}
	public String getStrCboMenu03() {
		return strCboMenu03;
	}
	public void setStrCboMenu03(String strCboMenu03) {
		this.strCboMenu03 = strCboMenu03;
	}
	public String getStrCboMenu04() {
		return strCboMenu04;
	}
	public void setStrCboMenu04(String strCboMenu04) {
		this.strCboMenu04 = strCboMenu04;
	}
	public List getBeanListMenues() {
		return beanListMenues;
	}
	public void setBeanListMenues(List beanListMenues) {
		this.beanListMenues = beanListMenues;
	}
	public String getMsgTxtEmpresa() {
		return msgTxtEmpresa;
	}
	public void setMsgTxtEmpresa(String msgTxtEmpresa) {
		this.msgTxtEmpresa = msgTxtEmpresa;
	}
	public String getMsgTxtEstado() {
		return msgTxtEstado;
	}
	public void setMsgTxtEstado(String msgTxtEstado) {
		this.msgTxtEstado = msgTxtEstado;
	}
	public String getMsgTxtPerfil() {
		return msgTxtPerfil;
	}
	public void setMsgTxtPerfil(String msgTxtPerfil) {
		this.msgTxtPerfil = msgTxtPerfil;
	}
	public String getMsgTxtDocum() {
		return msgTxtDocum;
	}
	public void setMsgTxtDocum(String msgTxtDocum) {
		this.msgTxtDocum = msgTxtDocum;
	}
	public String getMsgTxtDemo() {
		return msgTxtDemo;
	}
	public void setMsgTxtDemo(String msgTxtDemo) {
		this.msgTxtDemo = msgTxtDemo;
	}
	public String getMsgTxtTransaccion() {
		return msgTxtTransaccion;
	}
	public void setMsgTxtTransaccion(String msgTxtTransaccion) {
		this.msgTxtTransaccion = msgTxtTransaccion;
	}
	public String getHiddenStrIdTransaccion() {
		return hiddenStrIdTransaccion;
	}
	public void setHiddenStrIdTransaccion(String hiddenStrIdTransaccion) {
		this.hiddenStrIdTransaccion = hiddenStrIdTransaccion;
	}
	public Boolean getCboPerfilRendered() {
		return cboPerfilRendered;
	}
	public void setCboPerfilRendered(Boolean cboPerfilRendered) {
		this.cboPerfilRendered = cboPerfilRendered;
	}
	public Integer getCboEmpresaAdmDoc() {
		return cboEmpresaAdmDoc;
	}
	public void setCboEmpresaAdmDoc(Integer cboEmpresaAdmDoc) {
		this.cboEmpresaAdmDoc = cboEmpresaAdmDoc;
	}
	public Integer getCboPerfilAdmDoc() {
		return cboPerfilAdmDoc;
	}
	public void setCboPerfilAdmDoc(Integer cboPerfilAdmDoc) {
		this.cboPerfilAdmDoc = cboPerfilAdmDoc;
	}
	public String getTxtEmpresaAdmDoc() {
		return txtEmpresaAdmDoc;
	}
	public void setTxtEmpresaAdmDoc(String txtEmpresaAdmDoc) {
		this.txtEmpresaAdmDoc = txtEmpresaAdmDoc;
	}
	public String getTxtPerfilAdmDoc() {
		return txtPerfilAdmDoc;
	}
	public void setTxtPerfilAdmDoc(String txtPerfilAdmDoc) {
		this.txtPerfilAdmDoc = txtPerfilAdmDoc;
	}
	public int getCboEstadoAdmDoc() {
		return cboEstadoAdmDoc;
	}
	public void setCboEstadoAdmDoc(int cboEstadoAdmDoc) {
		this.cboEstadoAdmDoc = cboEstadoAdmDoc;
	}
	public String getStrArcDoc() {
		return strArcDoc;
	}
	public void setStrArcDoc(String strArcDoc) {
		this.strArcDoc = strArcDoc;
	}
	public String getStrArcDemo() {
		return strArcDemo;
	}
	public void setStrArcDemo(String strArcDemo) {
		this.strArcDemo = strArcDemo;
	}
	public int getIntVerDoc() {
		return intVerDoc;
	}
	public void setIntVerDoc(int intVerDoc) {
		this.intVerDoc = intVerDoc;
	}
	public int getIntVerDemo() {
		return intVerDemo;
	}
	public void setIntVerDemo(int intVerDemo) {
		this.intVerDemo = intVerDemo;
	}
	public String getStrCboTransaccion() {
		return strCboTransaccion;
	}
	public void setStrCboTransaccion(String strCboTransaccion) {
		this.strCboTransaccion = strCboTransaccion;
	}
	public String getStrCboMenuAdmDoc01() {
		return strCboMenuAdmDoc01;
	}
	public void setStrCboMenuAdmDoc01(String strCboMenuAdmDoc01) {
		this.strCboMenuAdmDoc01 = strCboMenuAdmDoc01;
	}
	public String getStrCboMenuAdmDoc02() {
		return strCboMenuAdmDoc02;
	}
	public void setStrCboMenuAdmDoc02(String strCboMenuAdmDoc02) {
		this.strCboMenuAdmDoc02 = strCboMenuAdmDoc02;
	}
	public String getStrCboMenuAdmDoc03() {
		return strCboMenuAdmDoc03;
	}
	public void setStrCboMenuAdmDoc03(String strCboMenuAdmDoc03) {
		this.strCboMenuAdmDoc03 = strCboMenuAdmDoc03;
	}
	public String getStrCboMenuAdmDoc04() {
		return strCboMenuAdmDoc04;
	}
	public void setStrCboMenuAdmDoc04(String strCboMenuAdmDoc04) {
		this.strCboMenuAdmDoc04 = strCboMenuAdmDoc04;
	}
	//Métodos
	public void habilitarGrabarAdmFormDoc(ActionEvent event) {
		log.info("-----------------------Debugging AdmFormDocController.habilitarGrabarAdmFormDoc-----------------------------");
		setFormAdmDocEnabled(false);
		setChkTodosEnabled(false);
		setAdmFormDocRendered(true);
		setStrAdjuntoDoc(false);
		setStrAdjuntoDemo(false);
		limpiarFormAdmDoc();
		if (beanListMenues != null) {
			beanListMenues.clear();
		}
		listarMenues(event);
	}
	
	public void habilitarActualizarAdmFormDoc(ActionEvent event){	
		log.info("-----------------------Debugging AdmFormDocController.habilitarActualizarAdmFormDoc-----------------------------");
	    setFormAdmDocEnabled(false);
	    setChkTodosEnabled(true);
	    setCboPerfilRendered(true);
	    setAdmFormDocRendered(true);
	    setStrAdjuntoDoc(true);
		setStrAdjuntoDemo(true);
	}
	
	public void cancelarGrabarAdmFormDoc(ActionEvent event) {
		log.info("-----------------------Debugging AdmFormDocController.cancelarGrabarAdmFormDoc-----------------------------");
		setFormAdmDocEnabled(true);
		setAdmFormDocRendered(false);
		setStrAdjuntoDoc(false);
		setStrAdjuntoDemo(false);
		limpiarFormAdmDoc();
	}
	
	public void limpiarFormAdmDoc() {
		AdmFormDoc formdoc = new AdmFormDoc();
		formdoc.setIntIdEmpresa(0);
		formdoc.setIntIdPerfil(0);
		formdoc.setIntIdEstado(1);
		setIntCboEmpresa(0);
		setIntCboPerfilEmpresa(0);
		setChkPerfil(false);
		setCboPerfilRendered(true);
		
		setStrCboMenu01("");
		setStrCboMenu02("");
		setStrCboMenu03("");
		setStrCboMenu04("");
		setMsgTxtEmpresa("");
		setMsgTxtPerfil("");
		setMsgTxtEstado("");
		setBeanFormDoc(formdoc);
	}
	
	public void selectNode(ActionEvent event) throws DaoException{
		List perfDet = getBeanListMenues();
		ArrayList arr = new ArrayList();
		
		UIComponent uiComponent = event.getComponent();
		String btnCbo = uiComponent.getId();
		String cboVal="";
		if(btnCbo.equals("btnAgregaMenu1")){
			cboVal = getStrCboMenu01();
		}else if(btnCbo.equals("btnAgregaMenu2")){
			cboVal = getStrCboMenu02();
		}else if(btnCbo.equals("btnAgregaMenu3")){
			cboVal = getStrCboMenu03();
		}else{
			cboVal = getStrCboMenu04();
		}
		
		for(int i = 0; i< perfDet.size(); i++){
			AdminMenu adm = (AdminMenu)perfDet.get(i);
			if(adm.getStrIdTransaccion().equals(cboVal)){
				adm.setChkPerfil(true);
				setHiddenStrIdTransaccion(adm.getStrIdTransaccion());
			}else adm.setChkPerfil(false);
			arr.add(adm);
		}
		setBeanListMenues(arr);
	}
	
	public void listarMenues(ActionEvent event){
		log.info("-----------------------Debugging UsuarioPerfilController.listarMenues-----------------------------");
	    setService(usuarioPerfilService);
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de seg_m_transacciones,seg_v_menu: ");
		log.info("-----------------------------------------------------------------------");
		String strPerfilId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenIdPerfil");
		String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenIdEmpresa");
		HashMap prmtBusq = new HashMap();
		String idPerfil = (strPerfilId==null||strPerfilId.equals("")?"0":strPerfilId);
		String idEmpresa = (strEmpresaId==null||strEmpresaId.equals("")?"0":strEmpresaId);
		
		log.info("idPerfil: "  + idPerfil);
		log.info("idEmpresa: " + idEmpresa);
		prmtBusq.put("pIntIdPerfil", Integer.parseInt(idPerfil));
		prmtBusq.put("pStrIdPadre",  "");
		prmtBusq.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		
	    ArrayList arrayAdminMenu = new ArrayList();
	    ArrayList listaAdminMenu = new ArrayList();
	    ArrayList arrayMenu1 = new ArrayList();
	    ArrayList arrayMenu2 = new ArrayList();
	    ArrayList arrayMenu3 = new ArrayList();
	    ArrayList arrayMenu4 = new ArrayList();
	    try {
	    	arrayAdminMenu = getService().listarPerfilDetalle(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarPerfilDetalle() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<arrayAdminMenu.size(); i++){
			HashMap hash = (HashMap) arrayAdminMenu.get(i);
			AdminMenu menu = new AdminMenu();
			log.info("EMPR_IDEMPRESA_N = "+hash.get("EMPR_IDEMPRESA_N"));
			int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
			menu.setIntIdEmpresa(intIdEmpresa);
			log.info("TRAN_IDTRANSACCION_C = "+hash.get("TRAN_IDTRANSACCION_C"));
			String strIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			menu.setStrIdTransaccion(strIdTransaccion);
			log.info("TRAN_IDTRANSACCIONPADRE_C = "+hash.get("TRAN_IDTRANSACCIONPADRE_C"));
			if(hash.get("TRAN_IDTRANSACCIONPADRE_C")!=null){
				String strIdTransaccionPadre = "" + hash.get("TRAN_IDTRANSACCIONPADRE_C");
				menu.setStrIdTransaccionPadre(strIdTransaccionPadre);
				log.info("menu.getStrIdTransaccionPadre() = "+menu.getStrIdTransaccionPadre());
			}
			log.info("TRAN_NIVEL_N = "+hash.get("TRAN_NIVEL_N"));
			int intMenuOrden = 0;
			if(hash.get("TRAN_NIVEL_N")!=null){
				intMenuOrden = Integer.parseInt("" + hash.get("TRAN_NIVEL_N"));
				menu.setIntMenuOrden(intMenuOrden);
				log.info("intMenuOrden = "+intMenuOrden);
			}
			log.info("TRAN_NOMBRE_V = "+hash.get("TRAN_NOMBRE_V"));
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			if(intMenuOrden==1){
				menu.setStrNombreM1(strNombre);
				arrayMenu1.add(menu);
			}else if(intMenuOrden==2){
				menu.setStrNombreM2(strNombre);
				arrayMenu2.add(menu);
			}else if(intMenuOrden==3){
				menu.setStrNombreM3(strNombre);
				arrayMenu3.add(menu);
			}else if(intMenuOrden==4){
				menu.setStrNombreM4(strNombre);
				arrayMenu4.add(menu);
			}
			log.info("TRAN_IDESTADO_N = "+hash.get("TRAN_IDESTADO_N"));
			int intIdEstado = Integer.parseInt("" + hash.get("TRAN_IDESTADO_N"));
			Boolean chkEstado = (intIdEstado == 1 ? true : false);  
			menu.setChkPerfil(chkEstado);
		}
		
		log.info("arrayMenu1.size() = "+arrayMenu1.size());
		log.info("arrayMenu2.size() = "+arrayMenu2.size());
		log.info("arrayMenu3.size() = "+arrayMenu3.size());
		log.info("arrayMenu4.size() = "+arrayMenu4.size());
		
		for(int i=0; i<arrayMenu1.size(); i++){
			AdminMenu m1 = new AdminMenu();
			m1=(AdminMenu)arrayMenu1.get(i);
			log.info("m1.getStrNombreM1() = "+m1.getStrNombreM1());
			listaAdminMenu.add(m1);
			for(int j=0; j<arrayMenu2.size(); j++){
				AdminMenu m2 = new AdminMenu();
				m2=(AdminMenu)arrayMenu2.get(j);
				if(m2.getStrIdTransaccionPadre().equals(m1.getStrIdTransaccion())){
					log.info("m2.getStrNombreM2() = "+m2.getStrNombreM2());
					listaAdminMenu.add(m2);
					for(int k=0; k<arrayMenu3.size(); k++){
						AdminMenu m3 = new AdminMenu();
						m3=(AdminMenu)arrayMenu3.get(k);
						if(m3.getStrIdTransaccionPadre().equals(m2.getStrIdTransaccion())){
							log.info("m3.getStrNombreM3() = "+m3.getStrNombreM3());
							listaAdminMenu.add(m3);
							for(int l=0; l<arrayMenu4.size(); l++){
								AdminMenu m4 = new AdminMenu();
								m4=(AdminMenu)arrayMenu4.get(l);
								if(m4.getStrIdTransaccionPadre().equals(m3.getStrIdTransaccion())){
									log.info("m4.getStrNombreM4() = "+m4.getStrNombreM4());
									listaAdminMenu.add(m4);
								}
							}
						}
					}
				}
			}
		}
		setBeanListMenues(listaAdminMenu);
	}
	
	public void listarAdmFormDoc(ActionEvent event){
		log.info("-----------------Debugging AdmFormDocController.listarAdmFormDoc------------------");
	    setService(admFormDocService);
	    log.info("Se ha seteado el Service");
		
	    log.info("Seteando los parametros de busqueda de perfiles: ");
		log.info("-------------------------------------------------");
		//String cboEmpresa = (getStrCboEmpresaFormDoc()==null || getStrCboEmpresaFormDoc().equals("selectNone")?"0":getStrCboEmpresaFormDoc());
		Integer cboEmpresa = getIntCboEmpresa();
		Integer cboPerfil  = getIntCboPerfilFormDoc();
		log.info("cboEmpresa: " + cboEmpresa);
		log.info("cboPerfil:  " + cboPerfil);
		
		HashMap prmtBusqAdmForm = new HashMap();
		//prmtBusqAdmForm.put("pTxtEmpresa", 			(getTxtEmpresa().equals("")?null:getTxtEmpresa()));
		prmtBusqAdmForm.put("pIntIdEmpresa", 		cboEmpresa);
		prmtBusqAdmForm.put("pIntIdPerfil", 		cboPerfil);
		prmtBusqAdmForm.put("pCboEstado", 			(getCboEstado()==0?null:getCboEstado()));
		
	    ArrayList arrayAdmFormDoc = new ArrayList();
	    ArrayList listaAdmFormDoc = new ArrayList();
	    try {
	    	arrayAdmFormDoc = getService().listarFormAdmDoc(prmtBusqAdmForm);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarFormAdmDoc() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Listado de empresas ha sido satisfactorio");
		
		for(int i=0; i<arrayAdmFormDoc.size(); i++){
			HashMap hash = (HashMap) arrayAdmFormDoc.get(i);
			AdmFormDoc formdoc = new AdmFormDoc();
			log.info("EMPR_IDEMPRESA_N = "+hash.get("EMPR_IDEMPRESA_N"));
			int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
			formdoc.setIntIdEmpresa(intIdEmpresa);
			log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
			int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
			formdoc.setIntIdPerfil(intIdPerfil);
			log.info("TRAN_IDTRANSACCION_C = "+hash.get("TRAN_IDTRANSACCION_C"));
			String intIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			formdoc.setStrIdTransaccion(intIdTransaccion);
			log.info("PERF_DESCRIPCION_V = "+hash.get("PERF_DESCRIPCION_V"));
			String strPerfil = ""+hash.get("PERF_DESCRIPCION_V");
			formdoc.setStrPerfil(strPerfil);
			log.info("DOCU_VERSION_DOC = "+(hash.get("DOCU_VERSION_DOC")==null?"0":hash.get("DOCU_VERSION_DOC")));
			int intNroVersion = Integer.parseInt(""+(hash.get("DOCU_VERSION_DOC")==null?"0":hash.get("DOCU_VERSION_DOC")));
			formdoc.setIntVersionDoc(intNroVersion);
			log.info("DOCU_VERSION_DEMO = "+(hash.get("DOCU_VERSION_DEMO"))==null?"0":hash.get("DOCU_VERSION_DEMO"));
			int intNroVersionDemo = Integer.parseInt(""+(hash.get("DOCU_VERSION_DEMO")==null?"0":hash.get("DOCU_VERSION_DEMO")));
			formdoc.setIntVersionDemo(intNroVersionDemo);
			log.info("TRAN_NIVEL_N = "+hash.get("TRAN_NIVEL_N"));
			int intNivel = Integer.parseInt(""+hash.get("TRAN_NIVEL_N"));
			formdoc.setIntNivel(intNivel);
			log.info("MENU01 = "+(hash.get("MENU01")==null?"":hash.get("MENU01")));
			String strMenu01 = ""+(hash.get("MENU01")==null?"":""+hash.get("MENU01"));
			formdoc.setStrNombreM1(strMenu01==null?"":strMenu01);
			log.info("MENU02 = "+hash.get("MENU02"));
			String strMenu02 = ""+(hash.get("MENU02")==null?"":""+hash.get("MENU02"));
			formdoc.setStrNombreM2(strMenu02);
			log.info("MENU03 = "+hash.get("MENU03"));
			String strMenu03 = ""+(hash.get("MENU03")==null?"":""+hash.get("MENU03"));
			formdoc.setStrNombreM3(strMenu03);
			log.info("MENU04 = "+hash.get("MENU04"));
			String strMenu04 = ""+(hash.get("MENU04")==null?"":""+hash.get("MENU04"));
			formdoc.setStrNombreM4(strMenu04);
			log.info("V_PROCESO = "+hash.get("V_PROCESO"));
			String strDocProceso = ""+(hash.get("V_PROCESO")==null?"":""+hash.get("V_PROCESO"));
			formdoc.setStrArchDoc(strDocProceso);
			log.info("V_DEMO = "+hash.get("V_DEMO"));
			String strDemo = ""+(hash.get("V_DEMO")==null?"":""+hash.get("V_DEMO"));
			formdoc.setStrArchDemo(strDemo);
			log.info("DOCU_FECHAREGISTRO_D = "+(hash.get("DOCU_FECHAREGISTRO_D") == null ?"":hash.get("DOCU_FECHAREGISTRO_D")));
			String strFecRegDoc = ""+(hash.get("DOCU_FECHAREGISTRO_D") == null ?"":hash.get("DOCU_FECHAREGISTRO_D"));
			formdoc.setDaFecRegDoc(strFecRegDoc);
			/*log.info("DODE_FECHAREGISTRO_D = "+(hash.get("DODE_FECHAREGISTRO_D") == null ?"":hash.get("DODE_FECHAREGISTRO_D")));
			String strFecRegDemo = ""+(hash.get("DODE_FECHAREGISTRO_D") == null ?"":hash.get("DODE_FECHAREGISTRO_D"));
			formdoc.setDaFecRegDemo(strFecRegDemo);*/
			
			listaAdmFormDoc.add(formdoc);
		}
		setBeanListFormDoc(listaAdmFormDoc);
	}
	
	public void modificarAdmFormDoc(ActionEvent event) throws DaoException, ParseException{
		log.info("-----------------Debugging AdmFormDocController.modificarAdmFormDoc-----------------------");
		String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenIdEmpresa");
		String strPerfilId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenIdPerfil");
		String strTransaccionId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenIdTransaccion");
		String strIdVersionDoc = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenIdVersionDoc");
		String strIdVersionDemo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenIdVersionDemo");
		String strArchDoc  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenStrArchDoc");
		String strArchDemo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenStrArchDemo");
		setService(admFormDocService);
		log.info("Se ha seteado el service");
		log.info("strEmpresaId  = "+strEmpresaId);
		log.info("strPerfilId   = "+strPerfilId);
		log.info("strTransaccionId  = "+strTransaccionId);
		log.info("strIdVersionDoc  	= "+strIdVersionDoc);
		log.info("strIdVersionDemo	= "+strIdVersionDemo);
		log.info("strArchDoc  	= "+strArchDoc);
		log.info("strArchDemo  	= "+strArchDemo);
		AdmFormDoc formdoc = new AdmFormDoc();
		ArrayList arrayAdmFomDoc = new ArrayList();
		HashMap prmtBusqAdmFomDoc  = new HashMap();
		prmtBusqAdmFomDoc.put("pIntIdEmpresa", 		Integer.parseInt(strEmpresaId));
		prmtBusqAdmFomDoc.put("pIntIdPerfil", 		Integer.parseInt(strPerfilId));
		prmtBusqAdmFomDoc.put("pStrIdTransaccion", 	strTransaccionId);
		prmtBusqAdmFomDoc.put("pIntIdVersionDoc", 	Integer.parseInt(strIdVersionDoc));
		prmtBusqAdmFomDoc.put("pIntIdVersionDemo",	Integer.parseInt(strIdVersionDemo));
		
		prmtBusqAdmFomDoc.put("pStrArchDoc", 		strArchDoc);
		prmtBusqAdmFomDoc.put("pStrArchDemo", 		strArchDemo);
		
		//El metodo devuelve una sola fila
		arrayAdmFomDoc = getService().listarFormAdmDoc(prmtBusqAdmFomDoc);
		log.info("arrayAdmFomDoc.size(): "+arrayAdmFomDoc.size());
		HashMap hash = (HashMap) arrayAdmFomDoc.get(0);
		
		log.info("EMPR_IDEMPRESA_N = "+hash.get("EMPR_IDEMPRESA_N"));
		int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
		formdoc.setIntIdEmpresa(intIdEmpresa);
		setIntCboEmpresa(intIdEmpresa);
		log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
		int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
		formdoc.setIntIdPerfil(intIdPerfil);
		setIntCboPerfilEmpresa(intIdPerfil);
		log.info("PERF_DESCRIPCION_V = "+hash.get("PERF_DESCRIPCION_V"));
		String strPerfil = ""+hash.get("PERF_DESCRIPCION_V");
		formdoc.setStrPerfil(strPerfil);
		log.info("V_N_IDESTADO = "+hash.get("V_N_IDESTADO"));
		int intIdEstado = Integer.parseInt("" + hash.get("V_N_IDESTADO"));
		formdoc.setIntIdEstado(intIdEstado);
		
		log.info("DOCU_VERSION_DOC = "+hash.get("DOCU_VERSION_DOC"));
		int intVersionDoc = 0;
		if(hash.get("DOCU_VERSION_DOC")==null||hash.get("DOCU_VERSION_DOC")==""){
			intVersionDoc = 0;
		}else{
			intVersionDoc = Integer.parseInt(""+hash.get("DOCU_VERSION_DOC"));
		}		
		formdoc.setIntVersionDoc(intVersionDoc);
		log.info("DOCU_VERSION_DEMO = "+hash.get("DOCU_VERSION_DEMO"));
		int intVersionDemo = 0;
		if(hash.get("DOCU_VERSION_DEMO")==null||hash.get("DOCU_VERSION_DEMO")==""){
			intVersionDemo = 0;
		}else{
			intVersionDemo = Integer.parseInt(""+hash.get("DOCU_VERSION_DEMO"));
		}		
		formdoc.setIntVersionDemo(intVersionDemo);
		log.info("V_PROCESO = "+(hash.get("V_PROCESO")==null?"":hash.get("V_PROCESO")));
		String strArcDoc = "" + (hash.get("V_PROCESO")==null?"":hash.get("V_PROCESO"));
		formdoc.setStrArchDoc(strArcDoc);
		log.info("V_DEMO = "+(hash.get("V_DEMO")==null?"":hash.get("V_DEMO")));
		String strArcDemo = "" + (hash.get("V_DEMO")==null?"":hash.get("V_DEMO"));
		formdoc.setStrArchDemo(strArcDemo);
		
		log.info("TRAN_IDTRANSACCION_C = "+hash.get("TRAN_IDTRANSACCION_C"));
		String strIdTransaccion = ""+hash.get("TRAN_IDTRANSACCION_C");
		formdoc.setStrIdTransaccion(strIdTransaccion);
		setHiddenStrIdTransaccion(strIdTransaccion);
		
		setChkPerfil(false);
		
		ArrayList arrayMenu = new ArrayList();
		if(beanListMenues == null){
			listarMenues(event);
		}
		
		for(int i=0;i<getBeanListMenues().size();i++){
			AdminMenu menu = new AdminMenu();
			menu = (AdminMenu) getBeanListMenues().get(i);
			if(menu.getStrIdTransaccion().equals(strIdTransaccion))
				menu.setChkPerfil(true);
			else menu.setChkPerfil(false);
			arrayMenu.add(menu);
			
		}
		setBeanFormDoc(formdoc);
	    setAdmFormDocRendered(true);
	    setFormAdmDocEnabled(false);
	    habilitarActualizarAdmFormDoc(event);
		listarMenues(event);
		
		setBeanListMenues(arrayMenu);
		ValueChangeEvent event1 = null;		
		reloadCboMenuPerfil1(event1);
		//reloadCboMenuPerfil(event1);
		
		//log.info("Datos de perfil ("+hash.get("N_IDPERFIL_PERF")+") cargados exitosamente.");
	}
	
	public void grabarFormDoc(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging AdmFormDocController.grabarFormDoc-----------------------------");
	    setService(admFormDocService);
	    log.info("Se ha seteado el Service");
	    String strFileNameDoc = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:hiddenStrFileDocName");
	    String strFileNameDemo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:hiddenStrFileDemoName");
		
		log.info("strFileNameDoc:  				"+strFileNameDoc);
		log.info("strFileNameDemo: 				"+strFileNameDemo);
		log.info("getStrCboEmpresa(): 			"+getIntCboEmpresa());
		log.info("getStrCboPerfilEmpresa(): 	"+(getIntCboPerfilEmpresa().equals("selectNone")?0:getIntCboPerfilEmpresa()));
		log.info("getHiddenStrIdTransaccion():	"+(getHiddenStrIdTransaccion()==null||getHiddenStrIdTransaccion().equals("")?"":getHiddenStrIdTransaccion()));
		log.info("getChkPerfil(): 				"+(getChkPerfil()==true?1:0));
	    
		Integer idTipoDoc = 0, idTipoDemo = 0;
		if(!strFileNameDoc.equals("")){
			idTipoDoc  = 1; 
		}
		if(!strFileNameDemo.equals("")){
			idTipoDemo = 2;
		}
		
		AdmFormDoc formdoc = new AdmFormDoc();
	    formdoc = (AdmFormDoc) getBeanFormDoc();
	    formdoc.setIntIdEmpresa(getIntCboEmpresa());
	    formdoc.setIntIdPerfil(getIntCboPerfilEmpresa().equals("selectNone")?0:getIntCboPerfilEmpresa());
	    formdoc.setStrIdTransaccion(getHiddenStrIdTransaccion()==null||getHiddenStrIdTransaccion().equals("")?"":getHiddenStrIdTransaccion());
	    formdoc.setIntIdTipoDoc(idTipoDoc);
	    formdoc.setIntIdTipoDemo(idTipoDemo);
	    formdoc.setStrArchDoc(strFileNameDoc);
	    formdoc.setStrArchDemo(strFileNameDemo);
	    formdoc.setIntPerfil(getChkPerfil()==true?1:0);
	    
	    Boolean bValidar = true;
	    
	    int intIdEmpresa = formdoc.getIntIdEmpresa();
	    if(intIdEmpresa==0){
	    	setMsgTxtEmpresa("* Debe seleccionar una Empresa.");
	    	bValidar = false;
	    }else{
	        setMsgTxtEmpresa("");
	    }
	    int intIdEstado = formdoc.getIntIdEstado();
	    if(intIdEstado == 0){
	        setMsgTxtEstado("* Debe completar el campo Estado.");
	    	bValidar = false;
	    } else{
	        setMsgTxtEstado("");
	    }
	    int intIdPerfil = formdoc.getIntIdPerfil();
	    if(getChkPerfil()==false){
	    	if(intIdPerfil == 0){
	    		setMsgTxtPerfil("* Debe seleccionar un Tipo de Perfil.");
		    	bValidar = false;
		    } else{
		        setMsgTxtPerfil("");
		    }
	    }
	    if(strFileNameDoc.equals("") && strFileNameDemo.equals("")){
	    	setMsgTxtDocum("Debe elegir por lo menos un Tipo de Documento.");
	    	bValidar = false;
	    }else{
	    	setMsgTxtDocum("");
	    }
	    
	    if(getHiddenStrIdTransaccion()==null || getHiddenStrIdTransaccion().equals("")){
	        setMsgTxtTransaccion("* Debe elegir un tipo de Transacción");
	    	bValidar = false;
	    }else {
	        setMsgTxtTransaccion("");
	    }
	    
	    if(bValidar==true){
		  try {
			  if(getChkPerfil()==true){
				  setCboPerfilRendered(false);
			  }
			  if(!strFileNameDoc.equals("") && !strFileNameDemo.equals("")){
				  getService().grabarFormDoc(formdoc);
				  getService().grabarFormDemo(formdoc);
			  }else if(!strFileNameDoc.equals("") && strFileNameDemo.equals("")){
				  getService().grabarFormDoc(formdoc);
	  	      }else{ //if(strFileNameDoc.equals("") && !strFileNameDemo.equals("")){
	  	    	  getService().grabarFormDemo(formdoc);
	  	      }
		  } catch (DaoException e) {
			  log.info("ERROR  getService().grabarFormDoc(formdoc:) " + e.getMessage());
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  	listarAdmFormDoc(event);
			setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
	    }
	}
	
	public void eliminarFormDoc(ActionEvent event) throws DaoException{
    	log.info("-----------------------Debugging AdmFormDocController.eliminarFormDoc-----------------------------");
    	String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenIdEmpresa");
		String strPerfilId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenIdPerfil");
		String strTransaccionId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelFormDocModalPanel:hiddenIdTransaccion");
		log.info("strEmpresaId:" 		+ strEmpresaId);
		log.info("strPerfilId:" 		+ strPerfilId);
		log.info("strTransaccionId:" 	+ strTransaccionId);
		
		HashMap prmtAdmFormDoc = new HashMap();
		prmtAdmFormDoc.put("pIntIdEmpresa", 	Integer.parseInt(strEmpresaId));
		prmtAdmFormDoc.put("pIntIdPerfil",		Integer.parseInt(strPerfilId));
		prmtAdmFormDoc.put("pStrTransaccionId",strTransaccionId);
		getService().eliminarAdmFormDoc(prmtAdmFormDoc);
		log.info("Se ha eliminado el Documento seleccionado.");
		listarAdmFormDoc(event);
    }
	
	public void reloadCboMenuPerfil1(ValueChangeEvent event) throws DaoException {
		log.info("--------------------Metodo ejemplo--------------------------");
		setService(usuarioPerfilService);
		String idCboMenu = "";
		String cboId = "";
		if(event!=null){
			UIComponent uiComponent = event.getComponent();
			log.info("uiComponent = "+uiComponent.getId());
			cboId=uiComponent.getId();
			log.info("cboId: "+cboId);			
			PhaseId id = event.getPhaseId();
			id.toString();
			idCboMenu =(String)event.getNewValue();
			if(cboId.equals("idCboPerfilEmpresa")){
				setHiddenIdPerfilFormDoc(idCboMenu==null?"":idCboMenu);
				idCboMenu = "";
			}else {
				//setHiddenIdPerfilFormDoc("");
				idCboMenu = (String)event.getNewValue();
			}
		}					
				
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", idCboMenu==null||idCboMenu.equals("")?"":idCboMenu);
		prmtBusq.put("pIntIdEmpresa", Integer.parseInt(getHiddenIdEmpresaFormDoc()==null || getHiddenIdEmpresaFormDoc().equals("")?"0":getHiddenIdEmpresaFormDoc()));
		//String cboEmpresa = (getStrCboEmpresa()==null || getStrCboEmpresa().equals("selectNone")?null:getStrCboEmpresa());
		prmtBusq.put("pIntIdPerfil", Integer.parseInt(getHiddenIdPerfilFormDoc()==null||getHiddenIdPerfilFormDoc().equals("")||getHiddenIdPerfilFormDoc().equals("selectNone")?"1":getHiddenIdPerfilFormDoc()));
		prmtBusq.put("pStrNivel", 	 "1");
		
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarMenuPerfil1(prmtBusq);
	    
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
        
        log.info("Cargando cboMenuPerfil1...");
    	this.cboMenuPerfil1.clear();
		setStrRadioMenu1(idCboMenu);
    	this.cboMenuPerfil1=arrayCbo;
    	this.cboMenuPerfil1.add(0, lblSelect);
    	setCboMenuPerfil1(cboMenuPerfil1);
                
		log.info("Saliendo de ControlsFiller.reloadCboMenu()...");
	}
	
	public void reloadCboMenuPerfil(ValueChangeEvent event) throws DaoException {
		log.info("--------------------Debugging ControlsFiller.reloadCboMenuPerfil()--------------------------");
		UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String cboId=uiComponent.getId();
		log.info("cboId: "+cboId);
		setService(usuarioPerfilService);
		PhaseId id = event.getPhaseId();
		id.toString();
		String idCboMenu =(String)event.getNewValue();
		
		if(cboId.equals("idCboPerfilEmpresa")){
			setHiddenIdPerfilFormDoc(idCboMenu==null?"":idCboMenu);
			idCboMenu = "";
		}else {
			//setHiddenIdPerfilFormDoc("");
			idCboMenu = (String)event.getNewValue();
		}
		
		//log.info("idCboMenu(): "+idCboMenu==null||idCboMenu.equals("")?"":idCboMenu);
		//log.info("getHiddenIdEmpresaFormDoc(): "+getHiddenIdEmpresaFormDoc());
		//log.info("getHiddenIdPerfilFormDoc(): "+getHiddenIdPerfilFormDoc()==null||getHiddenIdPerfilFormDoc().equals("")?"1":getHiddenIdPerfilFormDoc());
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", idCboMenu==null||idCboMenu.equals("")?"":idCboMenu);
		prmtBusq.put("pIntIdEmpresa", Integer.parseInt(getHiddenIdEmpresaFormDoc()==null || getHiddenIdEmpresaFormDoc().equals("")?"0":getHiddenIdEmpresaFormDoc()));
		//String cboEmpresa = (getStrCboEmpresa()==null || getStrCboEmpresa().equals("selectNone")?null:getStrCboEmpresa());
		prmtBusq.put("pIntIdPerfil", Integer.parseInt(getHiddenIdPerfilFormDoc()==null||getHiddenIdPerfilFormDoc().equals("")||getHiddenIdPerfilFormDoc().equals("selectNone")?"1":getHiddenIdPerfilFormDoc()));
		prmtBusq.put("pStrNivel", 	 cboId==null||cboId.equals("idCboPerfilEmpresa")?"1":"");
		
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarMenuPerfil1(prmtBusq);
	    
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
        
        if(cboId.equals("idCboPerfilEmpresa")){
        	log.info("Cargando cboMenuPerfil1...");
        	this.cboMenuPerfil1.clear();
			setStrRadioMenu1(idCboMenu);
        	this.cboMenuPerfil1=arrayCbo;
        	this.cboMenuPerfil1.add(0, lblSelect);
        	setCboMenuPerfil1(cboMenuPerfil1);
		}
        log.info("reloadCboMenuPerfil...");
        if(cboId.equals("cboMenuPerfil1")){
        	log.info("Cargando cboMenuPerfil1...");
        	log.info("idCboMenu: "+idCboMenu);
        	this.cboMenu2.clear();
			setStrRadioMenu1(idCboMenu);
        	this.cboMenu2=arrayCbo;
        	this.cboMenu2.add(0, lblSelect);
        	setCboMenu2(cboMenu2);
		}else if(cboId.equals("cboMenuPerfil2")){
			log.info("Cargando cboMenu2...");
			this.cboMenu3.clear();
			setStrRadioMenu2(idCboMenu);
			this.cboMenu3=arrayCbo;
			this.cboMenu3.add(0, lblSelect);
			setCboMenu3(cboMenu3);
		}else if(cboId.equals("cboMenuPerfil3")){
			log.info("Cargando cboMenu3...");
			this.cboMenu4.clear();
			setStrRadioMenu3(idCboMenu);
			this.cboMenu4=arrayCbo;
			this.cboMenu4.add(0, lblSelect);
			setCboMenu4(cboMenu4);
		}
		log.info("Saliendo de ControlsFiller.reloadCboMenu()...");
	}
	
	public void downloadArc(ActionEvent event){
	    log.info("Ingreso en la descarga ");
	    String strDoc = getRequestParameter("paramDoc");
	    String strDemo = getRequestParameter("paramDemo");
	    log.info("paramDoc: "+strDoc);
	    log.info("paramDemo: "+strDemo);
	    
	    String strAdjunto = (strDoc==null||strDoc.equals("")? strDemo : strDoc);
	    log.info("strAdjunto: "+strAdjunto);
	    try{
	        setFileName(strAdjunto);
	        setPathToFile(getServletContext().getRealPath("") + ConstanteReporte.RUTA_UPLOADED + strAdjunto);
	        super.downloadFile(event);
	        
	    }catch(Exception e){
	        log.info("error  en los archivos " + e.getMessage());
	        setMessageError("No se pudo descargar el archivo, compruebe si existe '" + getFileName()+ "'.");
	    }
	}
	
	public void listarAdmDoc(ActionEvent event){
		log.info("-----------------Debugging AdmFormDocController.listarAdmDoc------------------");
	    setService(admFormDocService);
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de Administración de Documentación: ");
		log.info("-------------------------------------------------");
		String cboVal = "";
		
		HashMap prmtBusqAdmDoc = new HashMap();
		prmtBusqAdmDoc.put("pIntIdEmpresa", 		(getCboEmpresaAdmDoc()==0?null:getCboEmpresaAdmDoc()));
		prmtBusqAdmDoc.put("pIntIdPerfil", 			(getCboPerfilAdmDoc()==0?null:getCboPerfilAdmDoc()));
		prmtBusqAdmDoc.put("pCboEstado", 			(getCboEstadoAdmDoc()==0?null:getCboEstadoAdmDoc()));
		prmtBusqAdmDoc.put("pCboEstado", 			(getCboEstadoAdmDoc()==0?null:getCboEstadoAdmDoc()));
		prmtBusqAdmDoc.put("pCboEstado", 			(getCboEstadoAdmDoc()==0?null:getCboEstadoAdmDoc()));
		prmtBusqAdmDoc.put("pStrIdTransaccion", 	cboVal);
		prmtBusqAdmDoc.put("pStrArchDoc", 			getStrArcDoc());
		prmtBusqAdmDoc.put("pStrArchDemo", 			getStrArcDemo());
		prmtBusqAdmDoc.put("pIntVerDoc", 			getIntVerDoc()==0?null:getIntVerDoc());
		prmtBusqAdmDoc.put("pIntVerDemo", 			getIntVerDemo()==0?null:getIntVerDemo());
		
	    ArrayList arrayAdmDoc = new ArrayList();
	    ArrayList listaAdmDoc = new ArrayList();
	    try {
	    	arrayAdmDoc = getService().listarFormAdmDoc(prmtBusqAdmDoc);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarFormAdmDoc() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Listado de Documentación ha sido satisfactorio");
		
		for(int i=0; i<arrayAdmDoc.size(); i++){
			HashMap hash = (HashMap) arrayAdmDoc.get(i);
			AdmFormDoc formdoc = new AdmFormDoc();
			log.info("EMPR_IDEMPRESA_N = "+hash.get("EMPR_IDEMPRESA_N"));
			int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
			formdoc.setIntIdEmpresa(intIdEmpresa);
			log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
			int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
			formdoc.setIntIdPerfil(intIdPerfil);
			log.info("TRAN_IDTRANSACCION_C = "+hash.get("TRAN_IDTRANSACCION_C"));
			String intIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			formdoc.setStrIdTransaccion(intIdTransaccion);
			log.info("PERF_DESCRIPCION_V = "+hash.get("PERF_DESCRIPCION_V"));
			String strPerfil = ""+hash.get("PERF_DESCRIPCION_V");
			formdoc.setStrPerfil(strPerfil);
			log.info("DOCU_VERSION_DOC = "+(hash.get("DOCU_VERSION_DOC")==null?"0":hash.get("DOCU_VERSION_DOC")));
			int intNroVersion = Integer.parseInt(""+(hash.get("DOCU_VERSION_DOC")==null?"0":hash.get("DOCU_VERSION_DOC")));
			formdoc.setIntVersionDoc(intNroVersion);
			log.info("DOCU_VERSION_DEMO = "+(hash.get("DOCU_VERSION_DEMO"))==null?"0":hash.get("DOCU_VERSION_DEMO"));
			int intNroVersionDemo = Integer.parseInt(""+(hash.get("DOCU_VERSION_DEMO")==null?"0":hash.get("DOCU_VERSION_DEMO")));
			formdoc.setIntVersionDemo(intNroVersionDemo);
			log.info("TRAN_NIVEL_N = "+hash.get("TRAN_NIVEL_N"));
			int intNivel = Integer.parseInt(""+hash.get("TRAN_NIVEL_N"));
			formdoc.setIntNivel(intNivel);
			log.info("MENU01 = "+(hash.get("MENU01")==null?"":hash.get("MENU01")));
			String strMenu01 = ""+(hash.get("MENU01")==null?"":""+hash.get("MENU01"));
			formdoc.setStrNombreM1(strMenu01==null?"":strMenu01);
			log.info("MENU02 = "+hash.get("MENU02"));
			String strMenu02 = ""+(hash.get("MENU02")==null?"":""+hash.get("MENU02"));
			formdoc.setStrNombreM2(strMenu02);
			log.info("MENU03 = "+hash.get("MENU03"));
			String strMenu03 = ""+(hash.get("MENU03")==null?"":""+hash.get("MENU03"));
			formdoc.setStrNombreM3(strMenu03);
			log.info("MENU04 = "+hash.get("MENU04"));
			String strMenu04 = ""+(hash.get("MENU04")==null?"":""+hash.get("MENU04"));
			formdoc.setStrNombreM4(strMenu04);
			log.info("V_PROCESO = "+hash.get("V_PROCESO"));
			String strDocProceso = ""+(hash.get("V_PROCESO")==null?"":""+hash.get("V_PROCESO"));
			formdoc.setStrArchDoc(strDocProceso);
			log.info("V_DEMO = "+hash.get("V_DEMO"));
			String strDemo = ""+(hash.get("V_DEMO")==null?"":""+hash.get("V_DEMO"));
			formdoc.setStrArchDemo(strDemo);
			log.info("DOCU_FECHAREGISTRO_D = "+(hash.get("DOCU_FECHAREGISTRO_D") == null ?"":hash.get("DOCU_FECHAREGISTRO_D")));
			String strFecRegDoc = ""+(hash.get("DOCU_FECHAREGISTRO_D") == null ?"":hash.get("DOCU_FECHAREGISTRO_D"));
			formdoc.setDaFecRegDoc(strFecRegDoc);
			log.info("DOCU_FECHAREGISTRO_D = "+(hash.get("DOCU_FECHAREGISTRO_D") == null ?"":hash.get("DOCU_FECHAREGISTRO_D")));
			String strFecRegDemo = ""+(hash.get("DOCU_FECHAREGISTRO_D") == null ?"":hash.get("DOCU_FECHAREGISTRO_D"));
			formdoc.setDaFecRegDemo(strFecRegDemo);
			
			listaAdmDoc.add(formdoc);
		}
		setBeanListAdmDoc(listaAdmDoc);
	}
}