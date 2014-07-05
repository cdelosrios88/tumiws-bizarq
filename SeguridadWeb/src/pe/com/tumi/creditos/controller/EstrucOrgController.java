package pe.com.tumi.creditos.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import pe.com.tumi.adminCuenta.controller.PerJuridicaController;
import pe.com.tumi.adminCuenta.domain.PersonaJuridica;
import pe.com.tumi.adminCuenta.service.impl.AdminCuentaServiceImpl;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.common.util.ParametersController;
import pe.com.tumi.creditos.domain.EstructuraOrganica;
import pe.com.tumi.creditos.service.impl.EstrucOrgServiceImpl;
import pe.com.tumi.popup.service.impl.ComunicacionServiceImpl;
import pe.com.tumi.popup.service.impl.CtaBancariaServiceImpl;
import pe.com.tumi.popup.service.impl.DomicilioServiceImpl;
import pe.com.tumi.popup.service.impl.RepLegalServiceImpl;
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

public class EstrucOrgController extends GenericController {
	
	private 	EstrucOrgServiceImpl 	estrucOrgService;
	private 	List					beanListEstructuraOrg;
	private 	List 					beanListInstiPlanilla = new ArrayList();
	private 	List 					beanListInstiAdministra = new ArrayList();
	private 	List 					beanListInstiCobra = new ArrayList();
	private 	List 					beanListUniEjecuPlanilla = new ArrayList();
	private 	List 					beanListUniEjecuAdministra = new ArrayList();
	private 	List 					beanListUniEjecuCobra = new ArrayList();
	private 	List 					beanListDepenPlanilla = new ArrayList();;
	private 	List 					beanListDepenAdministra = new ArrayList();
	private 	List 					beanListDepenCobra;
	private 	EstructuraOrganica		beanInsti = new EstructuraOrganica();
	private 	EstructuraOrganica		beanInstiPlanilla = new EstructuraOrganica();
	private 	EstructuraOrganica		beanInstiAdministra = new EstructuraOrganica();
	private 	EstructuraOrganica		beanInstiCobra = new EstructuraOrganica();
	private 	EstructuraOrganica		beanUniEjecu = new EstructuraOrganica();
	private 	EstructuraOrganica		beanUniEjecuPlanilla = new EstructuraOrganica();
	private 	EstructuraOrganica		beanUniEjecuAdministra = new EstructuraOrganica();
	private 	EstructuraOrganica		beanUniEjecuCobra = new EstructuraOrganica();
	private 	EstructuraOrganica		beanDepen = new EstructuraOrganica();
	private 	EstructuraOrganica		beanDepenPlanilla = new EstructuraOrganica();
	private 	EstructuraOrganica		beanDepenAdministra = new EstructuraOrganica();
	private 	EstructuraOrganica		beanDepenCobra = new EstructuraOrganica();
	private 	Boolean 				renderConfigN1 = false;
	private 	Boolean 				renderConfigN2 = false;
	private 	Boolean 				renderConfigN3 = false;
	private 	Boolean 				renderProcesPlanillaN1 = false;
	private 	Boolean 				renderAdministraN1 = false;
	private 	Boolean 				renderCobChequesN1 = false;
	private 	Boolean 				renderProcesPlanillaN2 = false;
	private 	Boolean 				renderAdministraN2 = false;
	private 	Boolean 				renderCobChequesN2 = false;
	private 	Boolean 				renderProcesPlanillaN3 = false;
	private 	Boolean 				renderAdministraN3 = false;
	private 	Boolean 				renderCobChequesN3 = false;
	private 	Boolean 				chkProcesPlanillaN1 = false;
	private 	Boolean 				chkAdministraN1 = false;
	private 	Boolean 				chkCobChequesN1 = false;
	private 	Boolean 				chkProcesPlanillaN2 = false;
	private 	Boolean 				chkAdministraN2 = false;
	private 	Boolean 				chkCobChequesN2 = false;
	private 	Boolean 				chkProcesPlanillaN3 = false;
	private 	Boolean 				chkAdministraN3 = false;
	private 	Boolean 				chkCobChequesN3 = false;
	private 	String					strBtnAgregarN1 = "Agregar Configuración";
	private 	String					strBtnAgregarN2 = "Agregar Configuración";
	private 	String					strBtnAgregarN3 = "Agregar Configuración";
	// Variables para obtener valores de los selects
	private 	Integer 				intCboTipoEntidad;
	private 	Integer 				intCboNivelEntidad;
	private 	Integer					intCboConfigEstructura;
	private 	Integer 				intCboTipoSocio;
	private 	Integer 				intCboModalidadEstructura;
	private 	Integer 				intCboSucursales;
	private 	Integer 				intCboSubsucursales;
	private 	Integer					intCboFechaEnvio;
	private 	Integer					intCboFechaCobro;
	private 	Integer 				intCboEstadoPlanilla;
	private 	Integer 				intRdoTipoEntidad;
	private 	Integer 				intCboEstadoDocumento;
	private 	Boolean 				blnCheckNombreEntidad;
	private 	String 					strNombreEntidad;
	private 	Boolean 				blnCheckFechas;
	private 	Date 					dtFechaDesde;
	private 	Date 					dtFechaHasta;
	private 	Boolean 				blnCheckCodigo;
	private 	String 					strCodigoExterno;
	private 	Integer 				intCboTipoEntidadTerceros;
	private 	Boolean					blnNuevoRegEstruc;
	private 	Boolean					blnShowFormEstructura = false;
	private 	BeanSesion 				beanSesion = new BeanSesion();
	private 	PerJuridicaController	perJuriController = (PerJuridicaController) getSpringBean("perJuridicaController");
	private 	ParametersController 	paramController = (ParametersController) getSpringBean("parametersController");
	private 	AdminCuentaServiceImpl 	perJuridicaService;
	private 	CtaBancariaServiceImpl	ctaBancariaService;
	private 	DomicilioServiceImpl	domicilioService;
	private 	ComunicacionServiceImpl comunicacionService;
	private 	RepLegalServiceImpl		repLegalService;

	/**************************************************************/
	/*  Nombre :  listarEstructuraOrg           		      	 */
	/*  Parametros. :       	 							 */
	/*  Objetivo: 											 */
	/*  Retorno : 						                     */
	/**************************************************************/
	
	//------------------------------------------------------------------------------------------------------------
	//Mantenimiento de Estructura Organica
	//------------------------------------------------------------------------------------------------------------
	
	public void listarEstructuraOrg(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging listarEstructuraOrg-----------------------------");
		setCreditosService(estrucOrgService);
		log.info("getIntCboTipoEntidad(): "+getIntCboTipoEntidad());
		log.info("getIntCboNivelEntidad(): "+getIntCboNivelEntidad());
		log.info("getBlnCheckNombreEntidad(): "+getBlnCheckNombreEntidad());
		log.info("getStrNombreEntidad(): "+getStrNombreEntidad());
		log.info("getIntCboConfigEstructura(): "+getIntCboConfigEstructura());
		log.info("getIntCboTipoSocio(): "+getIntCboTipoSocio());
		log.info("getIntCboModalidadEstructura(): "+getIntCboModalidadEstructura());
		log.info("getBlnCheckFechas(): "+getBlnCheckFechas());
		log.info("getIntCboEstadoPlanilla(): "+getIntCboEstadoPlanilla());
		log.info("getIntCboSucursales(): "+getIntCboSucursales());
		log.info("getIntCboSubsucursales(): "+getIntCboSubsucursales());
		log.info("getBlnCheckCodigo(): "+getBlnCheckCodigo());
		log.info("getIntCodigoEstructura(): "+getStrCodigoExterno());
		log.info("getDtFechaDesde(): "+getDtFechaDesde());
		log.info("getDtFechaHasta(): "+getDtFechaHasta());
		
		//Obteniendo Labels de los SelectOneMenu
		UIComponent cboConfigEstruc = getUIComponent("frmEstrucOrg:cboConfigEstruc");
		String lblCboConfigEstruc = getSelectOneLabel(cboConfigEstruc);
		log.info("lblCboConfigEstruc: "+lblCboConfigEstruc);
		lblCboConfigEstruc = lblCboConfigEstruc.substring(0, 1);
		log.info("lblCboConfigEstruc: "+lblCboConfigEstruc);
		
		UIComponent cboTipoSocio = getUIComponent("frmEstrucOrg:cboTipoSocioEstruc");
		String lblCboTipoSocio = getSelectOneLabel(cboTipoSocio);
		log.info("lblCboTipoSocio: "+lblCboTipoSocio);
		
		UIComponent cboModalidad = getUIComponent("frmEstrucOrg:cboModalidadEstruc");
		String lblCboModalidad = getSelectOneLabel(cboModalidad);
		log.info("lblCboModalidad: "+lblCboModalidad);
		
		EstructuraOrganica prmEstructura = new EstructuraOrganica();
		prmEstructura.setIntTipoEntidad(getIntCboTipoEntidad().equals(0)?null:getIntCboTipoEntidad());
		prmEstructura.setIntNivel(getIntCboNivelEntidad().equals(0)?null:getIntCboNivelEntidad());
		prmEstructura.setStrNombreEntidad(getStrNombreEntidad());
		prmEstructura.setStrCasoConfig(getIntCboConfigEstructura().equals(0)?null:lblCboConfigEstruc);
		prmEstructura.setStrTipoSocio(getIntCboTipoSocio().equals(0)?null:lblCboTipoSocio);
		prmEstructura.setStrModalidad(getIntCboModalidadEstructura().equals(0)?null:lblCboModalidad);
		prmEstructura.setIntSucursal(getIntCboSucursales().equals(0)?null:getIntCboSucursales());
		prmEstructura.setIntSubsucursal(getIntCboSubsucursales()==null||getIntCboSubsucursales().equals(0)?null:getIntCboSubsucursales());
		prmEstructura.setStrCodigoExterno(getStrCodigoExterno());
		prmEstructura.setStrFechaDesde(getDtFechaDesde()!=null?sdf.format(getDtFechaDesde()):null);
		prmEstructura.setStrFechaHasta(getDtFechaHasta()!=null?sdf.format(getDtFechaHasta()):null);
		
		ArrayList listaEstrucOrg = new ArrayList();
		listaEstrucOrg = getCreditosService().buscarEstrucOrg(prmEstructura);
		log.info("listaEstrucOrg.size(): "+listaEstrucOrg.size());
		
		setBeanListEstructuraOrg(listaEstrucOrg);
	}
	
	public void agregarPlanillaInsti(ActionEvent event) throws DaoException {
		log.info("-------------------------------------Debugging agregarPlanillaInsti-------------------------------------");		
		EstructuraOrganica instiPlanilla = new EstructuraOrganica();
		instiPlanilla = getBeanInstiPlanilla();
		instiPlanilla.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
		instiPlanilla.setIntNivel(1);
		instiPlanilla.setIntCasoConfig(1);
		instiPlanilla.setIntIdUsuario(beanSesion.getIntIdUsuario());
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(instiPlanilla,beanListInstiPlanilla,
																"frmEstrucOrg:cboTipoSocioPlanillaN1",
																"frmEstrucOrg:cboModalidadPlanillaN1",
																"frmEstrucOrg:cboSucursalPlanillaN1",
																"frmEstrucOrg:cboSubSucurPlanillaN1");
		setBeanListInstiPlanilla(listEstrucDet);
	}
	
	public void agregarAdministraInsti(ActionEvent event) throws DaoException {
		log.info("-------------------------------------Debugging agregarAdministraInsti-------------------------------------");
		EstructuraOrganica instiAdmin = new EstructuraOrganica();
		instiAdmin = getBeanInstiAdministra();
		instiAdmin.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
		instiAdmin.setIntNivel(1);
		instiAdmin.setIntCasoConfig(2);
		instiAdmin.setIntIdUsuario(beanSesion.getIntIdUsuario());
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(instiAdmin,beanListInstiAdministra,
																"frmEstrucOrg:cboTipoSocioAdministraN1",
																"frmEstrucOrg:cboModalidadAdministraN1",
																"frmEstrucOrg:cboSucursalAdministraN1",
																"frmEstrucOrg:cboSubSucurAdministraN1");
		setBeanListInstiAdministra(listEstrucDet);
	}
	
	public void agregarCobraInsti(ActionEvent event) throws DaoException {
		log.info("-------------------------------------Debugging agregarCobraInsti-------------------------------------");
		EstructuraOrganica instiCobra = new EstructuraOrganica();
		instiCobra = getBeanInstiCobra();
		instiCobra.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
		instiCobra.setIntNivel(1);
		instiCobra.setIntCasoConfig(3);
		instiCobra.setIntIdUsuario(beanSesion.getIntIdUsuario());
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(instiCobra,beanListInstiCobra,
																"frmEstrucOrg:cboTipoSocioCobraN1",
																"frmEstrucOrg:cboModalidadCobraN1",
																"frmEstrucOrg:cboSucursalCobraN1",
																"frmEstrucOrg:cboSubSucurCobraN1");
		setBeanListInstiCobra(listEstrucDet);
	}
	
	public void agregarPlanillaUniEjecu(ActionEvent event) throws DaoException {
		log.info("-------------------------------------Debugging agregarPlanillaUniEjecu-------------------------------------");
		EstructuraOrganica uniejePlanilla = new EstructuraOrganica();
		uniejePlanilla = getBeanUniEjecuPlanilla();
		uniejePlanilla.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
		uniejePlanilla.setIntNivel(2);
		uniejePlanilla.setIntCasoConfig(1);
		uniejePlanilla.setIntIdUsuario(beanSesion.getIntIdUsuario());
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(uniejePlanilla,beanListUniEjecuPlanilla,
																"frmEstrucOrg:cboTipoSocioPlanillaN2",
																"frmEstrucOrg:cboModalidadPlanillaN2",
																"frmEstrucOrg:cboSucursalPlanillaN2",
																"frmEstrucOrg:cboSubSucurPlanillaN2");
		setBeanListUniEjecuPlanilla(listEstrucDet);
	}
	
	public void agregarAdministraUniEjecu(ActionEvent event) throws DaoException {
		log.info("-------------------------------------Debugging agregarAdministraUniEjecu-------------------------------------");
		EstructuraOrganica uniejeAdmin = new EstructuraOrganica();
		uniejeAdmin = getBeanUniEjecuAdministra();
		uniejeAdmin.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
		uniejeAdmin.setIntNivel(2);
		uniejeAdmin.setIntCasoConfig(2);
		uniejeAdmin.setIntIdUsuario(beanSesion.getIntIdUsuario());
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(uniejeAdmin,beanListUniEjecuAdministra,
																"frmEstrucOrg:cboTipoSocioAdministraN2",
																"frmEstrucOrg:cboModalidadAdministraN2",
																"frmEstrucOrg:cboSucursalAdministraN2",
																"frmEstrucOrg:cboSubSucurAdministraN2");
		setBeanListUniEjecuAdministra(listEstrucDet);
	}
	
	public void agregarCobraUniEjecu(ActionEvent event) throws DaoException {
		log.info("-------------------------------------Debugging agregarCobraUniEjecu-------------------------------------");
		EstructuraOrganica uniejeCobra = new EstructuraOrganica();
		uniejeCobra = getBeanUniEjecuCobra();
		uniejeCobra.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
		uniejeCobra.setIntNivel(2);
		uniejeCobra.setIntCasoConfig(3);
		uniejeCobra.setIntIdUsuario(beanSesion.getIntIdUsuario());
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(uniejeCobra,beanListUniEjecuCobra,
																"frmEstrucOrg:cboTipoSocioCobraN2",
																"frmEstrucOrg:cboModalidadCobraN2",
																"frmEstrucOrg:cboSucursalCobraN2",
																"frmEstrucOrg:cboSubSucurCobraN2");
		setBeanListUniEjecuCobra(listEstrucDet);
	}
	
	public void agregarPlanillaDepen(ActionEvent event) throws DaoException {
		log.info("-------------------------------------Debugging agregarPlanillaDepen-------------------------------------");
		EstructuraOrganica depenPlanilla = new EstructuraOrganica();
		depenPlanilla = getBeanDepenPlanilla();
		depenPlanilla.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
		depenPlanilla.setIntNivel(3);
		depenPlanilla.setIntCasoConfig(1);
		depenPlanilla.setIntIdUsuario(beanSesion.getIntIdUsuario());
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(depenPlanilla,beanListDepenPlanilla,
																"frmEstrucOrg:cboTipoSocioPlanillaN3",
																"frmEstrucOrg:cboModalidadPlanillaN3",
																"frmEstrucOrg:cboSucursalPlanillaN3",
																"frmEstrucOrg:cboSubSucurPlanillaN3");
		setBeanListDepenPlanilla(listEstrucDet);
	}
	
	public void agregarAdministraDepen(ActionEvent event) throws DaoException {
		log.info("-------------------------------------Debugging agregarAdministraDepen-------------------------------------");
		EstructuraOrganica depenAdmin = new EstructuraOrganica();
		depenAdmin = getBeanDepenAdministra();
		depenAdmin.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
		depenAdmin.setIntNivel(3);
		depenAdmin.setIntCasoConfig(2);
		depenAdmin.setIntIdUsuario(beanSesion.getIntIdUsuario());
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(depenAdmin,beanListDepenAdministra,
																"frmEstrucOrg:cboTipoSocioAdministraN3",
																"frmEstrucOrg:cboModalidadAdministraN3",
																"frmEstrucOrg:cboSucursalAdministraN3",
																"frmEstrucOrg:cboSubSucurAdministraN3");
		setBeanListDepenAdministra(listEstrucDet);
	}
	
	public void agregarCobraDepen(ActionEvent event) throws DaoException {
		log.info("-------------------------------------Debugging agregarCobraDepen-------------------------------------");
		EstructuraOrganica depenCobra = new EstructuraOrganica();
		depenCobra = getBeanDepenCobra();
		depenCobra.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
		depenCobra.setIntNivel(3);
		depenCobra.setIntCasoConfig(3);
		depenCobra.setIntIdUsuario(beanSesion.getIntIdUsuario());
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(depenCobra,beanListDepenCobra,
																"frmEstrucOrg:cboTipoSocioCobraN3",
																"frmEstrucOrg:cboModalidadCobraN3",
																"frmEstrucOrg:cboSucursalCobraN3",
																"frmEstrucOrg:cboSubSucurCobraN3");
		setBeanListDepenCobra(listEstrucDet);
	}
	
	public List agregarEstructura(EstructuraOrganica beanEstructura,List beanListEstrucDet,
								  String uiCboTipoSocio, String uiCboModalidad, String uiCboSucursal, String uiCboSubSucursal) throws DaoException {
		log.info("-------------------------------------Debugging agregarEstructura-------------------------------------");
		
		ArrayList listInstitucion = new ArrayList();
		if(beanListEstrucDet!=null){
			for(int i=0; i<beanListEstrucDet.size(); i++){
				EstructuraOrganica insti = (EstructuraOrganica) beanListEstrucDet.get(i);
				listInstitucion.add(insti);
			}
		}
		
		log.info("intIdEmpresa: "+beanEstructura.getIntIdEmpresa());
		log.info("intIdNivel: "+beanEstructura.getIntNivel());
		log.info("intCodigo: "+beanEstructura.getIntCodigoEstructura());
		log.info("intCaso: "+beanEstructura.getIntCasoConfig());
		log.info("intIdUsuario: "+beanEstructura.getIntIdUsuario());
		
		log.info("intTipoSocio: "+beanEstructura.getIntTipoSocio());
		log.info("intModalidad: "+beanEstructura.getIntModalidad());
		log.info("intSucursal: "+beanEstructura.getIntSucursal());
		log.info("blnCheckSubsucursal: "+beanEstructura.getBlnCheckSubsucursal());
		log.info("intSubsucursal: "+beanEstructura.getIntSubsucursal());
		log.info("intDiaEnviado: "+beanEstructura.getIntDiaEnviado());
		log.info("intFechaEnvioInsti: "+beanEstructura.getIntFechaEnviado());
		log.info("intDiaCheque: "+beanEstructura.getIntDiaCheque());
		log.info("intFechaChequeInsti: "+beanEstructura.getIntFechaCheque());
		log.info("intDiaEfectuado: "+beanEstructura.getIntDiaEfectuado());
		log.info("intFechaEfectuadoInsti: "+beanEstructura.getIntFechaEfectuado());
		log.info("blnCheckCodigo: "+beanEstructura.getBlnCheckCodigo());
		log.info("strCodigoExterno: "+beanEstructura.getStrCodigoExterno());
		
		//Seteando propiedades del bean estructura
		EstructuraOrganica institucion = new EstructuraOrganica();
		institucion.setIntDiaEnviado(beanEstructura.getIntDiaEnviado());
		institucion.setIntFechaEnviado(beanEstructura.getIntFechaEnviado());
		institucion.setIntDiaEfectuado(beanEstructura.getIntDiaEfectuado());
		institucion.setIntFechaEfectuado(beanEstructura.getIntFechaEfectuado());
		institucion.setIntDiaCheque(beanEstructura.getIntDiaCheque());
		institucion.setIntFechaCheque(beanEstructura.getIntFechaCheque());
		
		institucion.setIntIdEmpresa(beanEstructura.getIntIdEmpresa());
		institucion.setIntNivel(beanEstructura.getIntNivel());
		institucion.setIntCasoConfig(beanEstructura.getIntCasoConfig());
		institucion.setIntTipoSocio(beanEstructura.getIntTipoSocio());
		institucion.setIntModalidad(beanEstructura.getIntModalidad());
		institucion.setIntSucursal(beanEstructura.getIntSucursal());
		institucion.setIntSubsucursal(beanEstructura.getIntSubsucursal());
		institucion.setStrCodigoExterno(beanEstructura.getStrCodigoExterno());
		institucion.setIntIdUsuario(beanEstructura.getIntIdUsuario());
		
		//Obteniendo mes enviado, efectuado y cheque
		String strFechaEnviado = "";
		String strFechaEfectuado = "";
		String strFechaCheque = "";
		ArrayList<SelectItem> cboFecha = paramController.getCboFechaEnvioCobro();
		for(int i=0; i<cboFecha.size(); i++){
			SelectItem item = cboFecha.get(i);
			if(item.getValue().equals(institucion.getIntFechaEnviado())){
				strFechaEnviado = item.getLabel();
			}
			if(item.getValue().equals(institucion.getIntFechaEfectuado())){
				strFechaEfectuado = item.getLabel();
			}
			if(item.getValue().equals(institucion.getIntFechaCheque())){
				strFechaCheque = item.getLabel();
			}
		}
		log.info("strFechaEnviado: "+strFechaEnviado);
		log.info("strFechaEfectuado: "+strFechaEfectuado);
		log.info("strFechaCheque: "+strFechaCheque);
		
		//Obteniendo Labels de los SelectOneMenu
		UIComponent cboTipoSocio = getUIComponent(uiCboTipoSocio);
		String lblCboTipoSocio = getSelectOneLabel(cboTipoSocio);
		
		UIComponent cboModalidad = getUIComponent(uiCboModalidad);;
		String lblCboModalidad = getSelectOneLabel(cboModalidad);
		
		UIComponent cboSucursal = getUIComponent(uiCboSucursal);
		String lblCboSucursal = getSelectOneLabel(cboSucursal);
		
		UIComponent cboSubSucursal = getUIComponent(uiCboSubSucursal);
		String lblCboSubsucursal = getSelectOneLabel(cboSubSucursal);
		
		//Seteando labels en el bean
		institucion.setStrTipoSocio(lblCboTipoSocio);
		institucion.setStrModalidad(lblCboModalidad);
		institucion.setStrSucursal(lblCboSucursal);
		institucion.setStrSubsucursal(lblCboSubsucursal);
		institucion.setStrFechaEnviado("Día "+institucion.getIntDiaEnviado()+" / "+strFechaEnviado);
		institucion.setStrFechaEfectuado("Día "+institucion.getIntDiaEfectuado()+" / "+strFechaEfectuado);
		institucion.setStrFechaCheque("Día "+institucion.getIntDiaCheque()+" / "+strFechaCheque);
		
		listInstitucion.add(institucion);
		return listInstitucion;
	}
	
	public void grabarEstructura(ActionEvent event) throws DaoException{
		log.info("-------------------------------------Debugging grabarEstructura-------------------------------------");
		setCreditosService(estrucOrgService);
		
		log.info("intIdEmpresa: "+beanSesion.getIntIdEmpresa());
		log.info("intRdoTipoEntida: "+getIntRdoTipoEntidad());
		log.info("intCboEstadoDocumento: "+getIntCboEstadoDocumento());
		log.info("intIdUsuario: "+beanSesion.getIntIdUsuario());

		//-------------------------------------------------------------------
		// Guardando Estructura: Institución, Unidad Ejecutora y Dependencia 
		//-------------------------------------------------------------------
		//Grabar Institución
		PersonaJuridica perJuridicaN1 = perJuriController.getBeanPerJuridicaN1();
		PersonaJuridica perJuridicaN2 = perJuriController.getBeanPerJuridicaN2();
		PersonaJuridica perJuridicaN3 = perJuriController.getBeanPerJuridicaN3();
		
		if(perJuridicaN1.getIntIdPersona()!=null){
			log.info("intIdPersona N1: "+perJuridicaN1.getIntIdPersona());
			log.info("strRazonSocial N1: "+perJuridicaN1.getStrRazonSocial());
			beanInsti.setIntIdPersona(perJuridicaN1.getIntIdPersona());
			beanInsti.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
			beanInsti.setIntNivel(1); 
			beanInsti.setIntTipoEntidad(getIntRdoTipoEntidad());
			beanInsti.setIntEstadoDocumento(getIntCboEstadoDocumento());
			beanInsti.setIntIdUsuario(beanSesion.getIntIdUsuario());
			beanInsti.setIntNivelRel(null);
			beanInsti.setIntCodigoRel(null);
			getCreditosService().grabarEstructuraOrg(beanInsti);
			log.info("intCodigoRelOut N1: "+beanInsti.getIntCodigoRelOut());
		}

		//Grabar Unidad Ejecutora
		if(perJuridicaN2.getIntIdPersona()!=null){
			log.info("intIdPersona N2: "+perJuridicaN2.getIntIdPersona());
			log.info("strRazonSocial N2: "+perJuridicaN2.getStrRazonSocial());
			beanUniEjecu.setIntIdPersona(perJuridicaN2.getIntIdPersona());
			beanUniEjecu.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
			beanUniEjecu.setIntNivel(2); 
			beanUniEjecu.setIntTipoEntidad(getIntRdoTipoEntidad());
			beanUniEjecu.setIntEstadoDocumento(getIntCboEstadoDocumento());
			beanUniEjecu.setIntIdUsuario(beanSesion.getIntIdUsuario());
			if(beanInsti.getIntCodigoRelOut()!=null){
				beanUniEjecu.setIntNivelRel(1);
				beanUniEjecu.setIntCodigoRel(beanInsti.getIntCodigoRelOut());
			}else{
				beanUniEjecu.setIntNivelRel(null);
				beanUniEjecu.setIntCodigoRel(null);
			}
			getCreditosService().grabarEstructuraOrg(beanUniEjecu);
			log.info("IntCodigoRelOut N2: "+beanUniEjecu.getIntCodigoRelOut());
		}
		
		//Grabar Dependencia
		if(perJuridicaN3.getIntIdPersona()!=null){
			log.info("intIdPersona N3: "+perJuridicaN3.getIntIdPersona());
			log.info("strRazonSocial N3: "+perJuridicaN3.getStrRazonSocial());
			beanDepen.setIntIdPersona(perJuridicaN3.getIntIdPersona());
			beanDepen.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
			beanDepen.setIntNivel(3); 
			beanDepen.setIntTipoEntidad(getIntRdoTipoEntidad());
			beanDepen.setIntEstadoDocumento(getIntCboEstadoDocumento());
			beanDepen.setIntIdUsuario(beanSesion.getIntIdUsuario());
			if(beanUniEjecu.getIntCodigoRelOut()!=null){
				beanDepen.setIntNivelRel(2);
				beanDepen.setIntCodigoRel(beanUniEjecu.getIntCodigoRelOut());
			}else{
				beanDepen.setIntNivelRel(null);
				beanDepen.setIntCodigoRel(null);
			}
			getCreditosService().grabarEstructuraOrg(beanDepen);
			log.info("IntCodigoRelOut N3: "+beanDepen.getIntCodigoRelOut());
		}
		
		//-------------------------------------------------------------------
		// Guardando Detalles: Procesar Planilla, Administra y Cobra Cheques 
		//-------------------------------------------------------------------
		ArrayList listInstiPlanilla = (ArrayList) (getBeanListInstiPlanilla() != null ? getBeanListInstiPlanilla() : new ArrayList());
		ArrayList listInstiAdministra = (ArrayList) (getBeanListInstiAdministra() != null ? getBeanListInstiAdministra() : new ArrayList());
		ArrayList listInstiCobra = (ArrayList) (getBeanListInstiCobra() != null ? getBeanListInstiCobra() : new ArrayList());
		
		if(beanInsti.getIntCodigoRelOut()!=null){
			log.info("listInstiPlanilla.size(): "+listInstiPlanilla.size());
			for(int i=0; i<listInstiPlanilla.size(); i++){
				EstructuraOrganica insti = new EstructuraOrganica();
				insti = (EstructuraOrganica)listInstiPlanilla.get(i);
				insti.setIntCodigoEstructura(beanInsti.getIntCodigoRelOut());
				getCreditosService().grabarEstructuraDetalle(insti);
			}
			log.info("listInstiAdministra.size(): "+listInstiAdministra.size());
			for(int i=0; i<listInstiAdministra.size(); i++){
				EstructuraOrganica insti = new EstructuraOrganica();
				insti = (EstructuraOrganica)listInstiAdministra.get(i);
				insti.setIntCodigoEstructura(beanInsti.getIntCodigoRelOut());
				getCreditosService().grabarEstructuraDetalle(insti);
			}
			log.info("listInstiCobra.size(): "+listInstiCobra.size());
			for(int i=0; i<listInstiCobra.size(); i++){
				EstructuraOrganica insti = new EstructuraOrganica();
				insti = (EstructuraOrganica)listInstiCobra.get(i);
				insti.setIntCodigoEstructura(beanInsti.getIntCodigoRelOut());
				getCreditosService().grabarEstructuraDetalle(insti);
			}
		}
		
		ArrayList listUniEjecuPlanilla = (ArrayList) (getBeanListUniEjecuPlanilla() != null ? getBeanListUniEjecuPlanilla() : new ArrayList());
		ArrayList listUniEjecuAdministra = (ArrayList) (getBeanListUniEjecuAdministra() != null ? getBeanListUniEjecuAdministra() : new ArrayList());
		ArrayList listUniEjecuCobra = (ArrayList) (getBeanListUniEjecuCobra() != null ? getBeanListUniEjecuCobra() : new ArrayList());
		
		if(beanUniEjecu.getIntCodigoRelOut()!=null){
			log.info("listUniEjecuPlanilla.size(): "+listUniEjecuPlanilla.size());
			for(int i=0; i<listUniEjecuPlanilla.size(); i++){
				EstructuraOrganica unieje = new EstructuraOrganica();
				unieje = (EstructuraOrganica)listUniEjecuPlanilla.get(i);
				unieje.setIntCodigoEstructura(beanUniEjecu.getIntCodigoRelOut());
				getCreditosService().grabarEstructuraDetalle(unieje);
			}
			log.info("listUniEjecuAdministra.size(): "+listUniEjecuAdministra.size());
			for(int i=0; i<listUniEjecuAdministra.size(); i++){
				EstructuraOrganica unieje = new EstructuraOrganica();
				unieje = (EstructuraOrganica)listUniEjecuAdministra.get(i);
				unieje.setIntCodigoEstructura(beanUniEjecu.getIntCodigoRelOut());
				getCreditosService().grabarEstructuraDetalle(unieje);
			}
			log.info("listUniEjecuCobra.size(): "+listUniEjecuCobra.size());
			for(int i=0; i<listUniEjecuCobra.size(); i++){
				EstructuraOrganica unieje = new EstructuraOrganica();
				unieje = (EstructuraOrganica)listUniEjecuCobra.get(i);
				unieje.setIntCodigoEstructura(beanUniEjecu.getIntCodigoRelOut());
				getCreditosService().grabarEstructuraDetalle(unieje);
			}
		}
		
		ArrayList listDepenPlanilla = (ArrayList) (getBeanListDepenPlanilla() != null ? getBeanListDepenPlanilla() : new ArrayList());
		ArrayList listDepenAdministra = (ArrayList) (getBeanListDepenAdministra() != null ? getBeanListDepenAdministra() : new ArrayList());
		ArrayList listDepenCobra = (ArrayList) (getBeanListDepenCobra() != null ? getBeanListDepenCobra() : new ArrayList());
		
		if(beanDepen.getIntCodigoRelOut()!=null){
			log.info("listDepenPlanilla.size(): "+listDepenPlanilla.size());
			for(int i=0; i<listDepenPlanilla.size(); i++){
				EstructuraOrganica depen = new EstructuraOrganica();
				depen = (EstructuraOrganica)listDepenPlanilla.get(i);
				depen.setIntCodigoEstructura(beanDepen.getIntCodigoRelOut());
				getCreditosService().grabarEstructuraDetalle(depen);
			}
			log.info("listDepenAdministra.size(): "+listDepenAdministra.size());
			for(int i=0; i<listDepenAdministra.size(); i++){
				EstructuraOrganica depen = new EstructuraOrganica();
				depen = (EstructuraOrganica)listDepenAdministra.get(i);
				depen.setIntCodigoEstructura(beanDepen.getIntCodigoRelOut());
				getCreditosService().grabarEstructuraDetalle(depen);
			}
			log.info("listDepenCobra.size(): "+listDepenCobra.size());
			for(int i=0; i<listDepenCobra.size(); i++){
				EstructuraOrganica depen = new EstructuraOrganica();
				depen = (EstructuraOrganica)listDepenCobra.get(i);
				depen.setIntCodigoEstructura(beanDepen.getIntCodigoRelOut());
				getCreditosService().grabarEstructuraDetalle(depen);
			}
		}
		
		//Listar Estructura Organica
		listarEstructuraOrg(event);
	}
	
	public void modificarEstructura(ActionEvent event) throws DaoException, ParseException{
		log.info("-------------------------------------Debugging modificarEstructura-------------------------------------");
		setCreditosService(estrucOrgService);
		String strEstrucKeys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelEstrucOrg:hiddenIdEstrucOrg");
		log.info("strEstrucKeys : "+strEstrucKeys);
		
		long intCodigoEstruc = 0;
		int intIdEmpresa = 0;
		int intNivel = 0;
		int intCaso = 0;
		
		if(strEstrucKeys != null){
			String[] strKeys = strEstrucKeys.split(",");
			intCodigoEstruc = Long.valueOf(strKeys[0]);
			intIdEmpresa = Integer.parseInt(strKeys[1]);
			intNivel = Integer.parseInt(strKeys[2]);
		}else{
			return;
		}
		
		EstructuraOrganica prmEstructura = new EstructuraOrganica();
		prmEstructura.setIntCodigoEstructura(intCodigoEstruc != 0 ? intCodigoEstruc : null);
		prmEstructura.setIntIdEmpresa(intIdEmpresa != 0 ? intIdEmpresa : null);
		prmEstructura.setIntNivel(intNivel != 0 ? intNivel : null);
		
		ArrayList listaEstruc = new ArrayList();
		listaEstruc = getCreditosService().buscarEstrucOrg(prmEstructura);
		log.info("listaEstruc.size(): "+listaEstruc.size());
		if(listaEstruc.size()==0){
			return;
		}
		
		EstructuraOrganica estruc = new EstructuraOrganica();
		estruc = (EstructuraOrganica)listaEstruc.get(0);
		log.info("strNombreEntidad: "+estruc.getStrNombreEntidad());
		log.info("IntTipoEntidadTerceros: "+estruc.getIntTipoEntidadTerceros());
		
		//Obtener el detalle de Planilla, Administra y Cobra
		ArrayList arrayDet = new ArrayList();
		arrayDet = getCreditosService().listarEstrucDet(prmEstructura);
		log.info("arrayDet.size(): "+arrayDet.size());
		ArrayList listPlanilla = new ArrayList();
		ArrayList listAdministra = new ArrayList();
		ArrayList listCobra = new ArrayList();
		
		if(intNivel == 1){
			PersonaJuridica perJuridicaN1 = perJuriController.getBeanPerJuridicaN1();
			perJuridicaN1.setStrRazonSocial(estruc.getStrNombreEntidad());
			getBeanInsti().setIntTipoEntidadTerceros(estruc.getIntTipoEntidadTerceros());
			
			for(int i=0; i<arrayDet.size(); i++){
				EstructuraOrganica ed = (EstructuraOrganica)arrayDet.get(i);
				log.info("intCasoConfig: "+ed.getIntCasoConfig());
				
				ed.setStrFechaEnviado(ed.getIntDiaEnviado()+" / "+ed.getStrFechaEnviado());
				ed.setStrFechaEfectuado(ed.getIntDiaEfectuado()+" / "+ed.getStrFechaEfectuado());
				ed.setStrFechaCheque(ed.getIntDiaCheque()+" / "+ed.getStrFechaCheque());
				
				if(ed.getIntCasoConfig() == 1){
					listPlanilla.add(ed);
				}else if(ed.getIntCasoConfig() == 2){
					listAdministra.add(ed);
				}else if(ed.getIntCasoConfig() == 3){
					listCobra.add(ed);
				}
			}
			setBeanListInstiPlanilla(listPlanilla);
			setBeanListInstiAdministra(listAdministra);
			setBeanListInstiCobra(listCobra);
		}if(intNivel == 2){
			PersonaJuridica perJuridicaN2 = perJuriController.getBeanPerJuridicaN2();
			perJuridicaN2.setStrRazonSocial(estruc.getStrNombreEntidad());
			
			for(int i=0; i<arrayDet.size(); i++){
				EstructuraOrganica ed = (EstructuraOrganica)arrayDet.get(i);
				log.info("intCasoConfig: "+ed.getIntCasoConfig());
				
				ed.setStrFechaEnviado(ed.getIntDiaEnviado()+" / "+ed.getStrFechaEnviado());
				ed.setStrFechaEfectuado(ed.getIntDiaEfectuado()+" / "+ed.getStrFechaEfectuado());
				ed.setStrFechaCheque(ed.getIntDiaCheque()+" / "+ed.getStrFechaCheque());
				
				if(ed.getIntCasoConfig() == 1){
					listPlanilla.add(ed);
				}else if(ed.getIntCasoConfig() == 2){
					listAdministra.add(ed);
				}else if(ed.getIntCasoConfig() == 3){
					listCobra.add(ed);
				}
			}
			setBeanListUniEjecuPlanilla(listPlanilla);
			setBeanListUniEjecuAdministra(listAdministra);
			setBeanListUniEjecuCobra(listCobra);
		}if(intNivel == 3){
			PersonaJuridica perJuridicaN3 = perJuriController.getBeanPerJuridicaN3();
			perJuridicaN3.setStrRazonSocial(estruc.getStrNombreEntidad());
			
			for(int i=0; i<arrayDet.size(); i++){
				EstructuraOrganica ed = (EstructuraOrganica)arrayDet.get(i);
				log.info("intCasoConfig: "+ed.getIntCasoConfig());
				
				ed.setStrFechaEnviado(ed.getIntDiaEnviado()+" / "+ed.getStrFechaEnviado());
				ed.setStrFechaEfectuado(ed.getIntDiaEfectuado()+" / "+ed.getStrFechaEfectuado());
				ed.setStrFechaCheque(ed.getIntDiaCheque()+" / "+ed.getStrFechaCheque());
				
				if(ed.getIntCasoConfig() == 1){
					listPlanilla.add(ed);
				}else if(ed.getIntCasoConfig() == 2){
					listAdministra.add(ed);
				}else if(ed.getIntCasoConfig() == 3){
					listCobra.add(ed);
				}
			}
			setBeanListDepenPlanilla(listPlanilla);
			setBeanListDepenAdministra(listAdministra);
			setBeanListInstiCobra(listCobra);
		}
	}
	
	//------------------------------------------------------------------------------------------------------------
	//Mostrar y ocultar controles para el Nivel 1: Institucion
	//------------------------------------------------------------------------------------------------------------
	
	public void showFormEstructura(ActionEvent event) throws DaoException{
		log.info("-------------------------------------Debugging showFormEstructura-------------------------------------");
		log.info("blnShowFormEstructura: "+getBlnShowFormEstructura());
		setBlnShowFormEstructura(true);
		//Diferenciar Nuevo de Modificar
		setBlnNuevoRegEstruc(true);
	}
	
	public void hideFormEstructura(ActionEvent event) throws DaoException{
		log.info("-------------------------------------Debugging hideFormEstructura-------------------------------------");
		log.info("blnShowFormEstructura: "+getBlnShowFormEstructura());
		setBlnShowFormEstructura(false);
		//Diferenciar Nuevo de Modificar
		setBlnNuevoRegEstruc(false);
	}
	
	public void showConfigN1(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging EstrucOrgController.showConfigN1-----------------------------");
		log.info("renderConfigN1: "+getRenderConfigN1());
		if(getRenderConfigN1()==false){
			setRenderConfigN1(true);
			setStrBtnAgregarN1("Ocultar Configuración");
		}else{
			setRenderConfigN1(false);
			setStrBtnAgregarN1("Agregar Configuración");
		}
	}
	
	public void showProcesPlanillaN1(ActionEvent event){
		log.info("-----------------------Debugging EstrucOrgController.showProcesPlanillaN1-----------------------------");
		log.info("chkProcesPlanillaN1: "+getChkProcesPlanillaN1());
		log.info("renderProcesPlanillaN1: "+getRenderProcesPlanillaN1());
		if(chkProcesPlanillaN1==true){
			setRenderProcesPlanillaN1(true);
		}else{
			setRenderProcesPlanillaN1(false);
		}
	}
	
	public void showAdministraN1(ActionEvent event){
		log.info("-----------------------Debugging EstrucOrgController.showAdministraN1-----------------------------");
		log.info("renderAdministraN1: "+getRenderAdministraN1());
		if(chkAdministraN1==true){
			setRenderAdministraN1(true);
		}else{
			setRenderAdministraN1(false);
		}
	}
	
	public void showCobChequesN1(ActionEvent event){
		log.info("-----------------------Debugging EstrucOrgController.showCobChequesN1-----------------------------");
		log.info("renderCobChequesN1: "+getRenderCobChequesN1());
		if(chkCobChequesN1==true){
			setRenderCobChequesN1(true);
		}else{
			setRenderCobChequesN1(false);
		}
	}
	
	//------------------------------------------------------------------------------------------------------------
	//Mostrar y ocultar controles para el Nivel 2: Unidad Ejecutora
	//------------------------------------------------------------------------------------------------------------
	
	public void showConfigN2(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging EstrucOrgController.showConfigN2-----------------------------");
		log.info("renderConfigN2: "+getRenderConfigN2());
		if(getRenderConfigN2()==false){
			setRenderConfigN2(true);
			setStrBtnAgregarN2("Ocultar Configuración");
		}else{
			setRenderConfigN2(false);
			setStrBtnAgregarN2("Agregar Configuración");
		}
	}
	
	public void showProcesPlanillaN2(ActionEvent event){
		log.info("-----------------------Debugging EstrucOrgController.showProcesPlanillaN2-----------------------------");
		log.info("chkProcesPlanillaN2: "+getChkProcesPlanillaN2());
		log.info("renderProcesPlanillaN2: "+getRenderProcesPlanillaN2());
		if(chkProcesPlanillaN2==true){
			setRenderProcesPlanillaN2(true);
		}else{
			setRenderProcesPlanillaN2(false);
		}
	}
	
	public void showAdministraN2(ActionEvent event){
		log.info("-----------------------Debugging EstrucOrgController.showAdministraN2-----------------------------");
		log.info("renderAdministraN2: "+getRenderAdministraN2());
		if(chkAdministraN2==true){
			setRenderAdministraN2(true);
		}else{
			setRenderAdministraN2(false);
		}
	}
	
	public void showCobChequesN2(ActionEvent event){
		log.info("-----------------------Debugging EstrucOrgController.showCobChequesN2-----------------------------");
		log.info("renderCobChequesN2: "+getRenderCobChequesN2());
		if(chkCobChequesN2==true){
			setRenderCobChequesN2(true);
		}else{
			setRenderCobChequesN2(false);
		}
	}
	
	//------------------------------------------------------------------------------------------------------------
	//Mostrar y ocultar controles para el Nivel 3: Dependencia
	//------------------------------------------------------------------------------------------------------------
	
	public void showConfigN3(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging EstrucOrgController.showConfigN3-----------------------------");
		log.info("renderConfigN3: "+getRenderConfigN3());
		if(getRenderConfigN3()==false){
			setRenderConfigN3(true);
			setStrBtnAgregarN3("Ocultar Configuración");
		}else{
			setRenderConfigN3(false);
			setStrBtnAgregarN3("Agregar Configuración");
		}
	}
	
	public void showProcesPlanillaN3(ActionEvent event){
		log.info("-----------------------Debugging EstrucOrgController.showProcesPlanillaN3-----------------------------");
		log.info("chkProcesPlanillaN3: "+getChkProcesPlanillaN3());
		log.info("renderProcesPlanillaN3: "+getRenderProcesPlanillaN3());
		if(chkProcesPlanillaN3==true){
			setRenderProcesPlanillaN3(true);
		}else{
			setRenderProcesPlanillaN3(false);
		}
	}
	
	public void showAdministraN3(ActionEvent event){
		log.info("-----------------------Debugging EstrucOrgController.showAdministraN3-----------------------------");
		log.info("renderAdministraN1: "+getRenderAdministraN3());
		if(chkAdministraN3==true){
			setRenderAdministraN3(true);
		}else{
			setRenderAdministraN3(false);
		}
	}
	
	public void showCobChequesN3(ActionEvent event){
		log.info("-----------------------Debugging EstrucOrgController.showCobChequesN3-----------------------------");
		log.info("renderCobChequesN3: "+getRenderCobChequesN3());
		if(chkCobChequesN3==true){
			setRenderCobChequesN3(true);
		}else{
			setRenderCobChequesN3(false);
		}
	}
	
	//-------------------------------------------------------------
	//Getters y Settters
	//-------------------------------------------------------------
	public EstrucOrgServiceImpl getEstrucOrgService() {
		return estrucOrgService;
	}
	public void setEstrucOrgService(EstrucOrgServiceImpl estrucOrgService) {
		this.estrucOrgService = estrucOrgService;
	}
	public Boolean getRenderConfigN1() {
		return renderConfigN1;
	}
	public void setRenderConfigN1(Boolean renderConfigN1) {
		this.renderConfigN1 = renderConfigN1;
	}
	public Boolean getRenderConfigN2() {
		return renderConfigN2;
	}
	public void setRenderConfigN2(Boolean renderConfigN2) {
		this.renderConfigN2 = renderConfigN2;
	}
	public Boolean getRenderConfigN3() {
		return renderConfigN3;
	}
	public void setRenderConfigN3(Boolean renderConfigN3) {
		this.renderConfigN3 = renderConfigN3;
	}
	public Boolean getRenderProcesPlanillaN1() {
		return renderProcesPlanillaN1;
	}
	public void setRenderProcesPlanillaN1(Boolean renderProcesPlanillaN1) {
		this.renderProcesPlanillaN1 = renderProcesPlanillaN1;
	}
	public Boolean getRenderAdministraN1() {
		return renderAdministraN1;
	}
	public void setRenderAdministraN1(Boolean renderAdministraN1) {
		this.renderAdministraN1 = renderAdministraN1;
	}
	public Boolean getRenderCobChequesN1() {
		return renderCobChequesN1;
	}
	public void setRenderCobChequesN1(Boolean renderCobChequesN1) {
		this.renderCobChequesN1 = renderCobChequesN1;
	}
	public Boolean getRenderProcesPlanillaN2() {
		return renderProcesPlanillaN2;
	}
	public void setRenderProcesPlanillaN2(Boolean renderProcesPlanillaN2) {
		this.renderProcesPlanillaN2 = renderProcesPlanillaN2;
	}
	public Boolean getRenderAdministraN2() {
		return renderAdministraN2;
	}
	public void setRenderAdministraN2(Boolean renderAdministraN2) {
		this.renderAdministraN2 = renderAdministraN2;
	}
	public Boolean getRenderCobChequesN2() {
		return renderCobChequesN2;
	}
	public void setRenderCobChequesN2(Boolean renderCobChequesN2) {
		this.renderCobChequesN2 = renderCobChequesN2;
	}
	public Boolean getRenderProcesPlanillaN3() {
		return renderProcesPlanillaN3;
	}
	public void setRenderProcesPlanillaN3(Boolean renderProcesPlanillaN3) {
		this.renderProcesPlanillaN3 = renderProcesPlanillaN3;
	}
	public Boolean getRenderAdministraN3() {
		return renderAdministraN3;
	}
	public void setRenderAdministraN3(Boolean renderAdministraN3) {
		this.renderAdministraN3 = renderAdministraN3;
	}
	public Boolean getRenderCobChequesN3() {
		return renderCobChequesN3;
	}
	public void setRenderCobChequesN3(Boolean renderCobChequesN3) {
		this.renderCobChequesN3 = renderCobChequesN3;
	}
	public Boolean getChkProcesPlanillaN1() {
		return chkProcesPlanillaN1;
	}
	public void setChkProcesPlanillaN1(Boolean chkProcesPlanillaN1) {
		this.chkProcesPlanillaN1 = chkProcesPlanillaN1;
	}
	public Boolean getChkAdministraN1() {
		return chkAdministraN1;
	}
	public void setChkAdministraN1(Boolean chkAdministraN1) {
		this.chkAdministraN1 = chkAdministraN1;
	}
	public Boolean getChkCobChequesN1() {
		return chkCobChequesN1;
	}
	public void setChkCobChequesN1(Boolean chkCobChequesN1) {
		this.chkCobChequesN1 = chkCobChequesN1;
	}
	public Boolean getChkProcesPlanillaN2() {
		return chkProcesPlanillaN2;
	}
	public void setChkProcesPlanillaN2(Boolean chkProcesPlanillaN2) {
		this.chkProcesPlanillaN2 = chkProcesPlanillaN2;
	}
	public Boolean getChkAdministraN2() {
		return chkAdministraN2;
	}
	public void setChkAdministraN2(Boolean chkAdministraN2) {
		this.chkAdministraN2 = chkAdministraN2;
	}
	public Boolean getChkCobChequesN2() {
		return chkCobChequesN2;
	}
	public void setChkCobChequesN2(Boolean chkCobChequesN2) {
		this.chkCobChequesN2 = chkCobChequesN2;
	}
	public Boolean getChkProcesPlanillaN3() {
		return chkProcesPlanillaN3;
	}
	public void setChkProcesPlanillaN3(Boolean chkProcesPlanillaN3) {
		this.chkProcesPlanillaN3 = chkProcesPlanillaN3;
	}
	public Boolean getChkAdministraN3() {
		return chkAdministraN3;
	}
	public void setChkAdministraN3(Boolean chkAdministraN3) {
		this.chkAdministraN3 = chkAdministraN3;
	}
	public Boolean getChkCobChequesN3() {
		return chkCobChequesN3;
	}
	public void setChkCobChequesN3(Boolean chkCobChequesN3) {
		this.chkCobChequesN3 = chkCobChequesN3;
	}
	public String getStrBtnAgregarN1() {
		return strBtnAgregarN1;
	}
	public void setStrBtnAgregarN1(String strBtnAgregarN1) {
		this.strBtnAgregarN1 = strBtnAgregarN1;
	}
	public String getStrBtnAgregarN2() {
		return strBtnAgregarN2;
	}
	public void setStrBtnAgregarN2(String strBtnAgregarN2) {
		this.strBtnAgregarN2 = strBtnAgregarN2;
	}
	public String getStrBtnAgregarN3() {
		return strBtnAgregarN3;
	}
	public void setStrBtnAgregarN3(String strBtnAgregarN3) {
		this.strBtnAgregarN3 = strBtnAgregarN3;
	}
	public Integer getIntCboTipoEntidad() {
		return intCboTipoEntidad;
	}
	public void setIntCboTipoEntidad(Integer intCboTipoEntidad) {
		this.intCboTipoEntidad = intCboTipoEntidad;
	}
	public Integer getIntCboNivelEntidad() {
		return intCboNivelEntidad;
	}
	public void setIntCboNivelEntidad(Integer intCboNivelEntidad) {
		this.intCboNivelEntidad = intCboNivelEntidad;
	}
	public Integer getIntCboTipoSocio() {
		return intCboTipoSocio;
	}
	public void setIntCboTipoSocio(Integer intCboTipoSocio) {
		this.intCboTipoSocio = intCboTipoSocio;
	}
	public Integer getIntCboModalidadEstructura() {
		return intCboModalidadEstructura;
	}
	public void setIntCboModalidadEstructura(Integer intCboModalidadEstructura) {
		this.intCboModalidadEstructura = intCboModalidadEstructura;
	}
	public Integer getIntCboSucursales() {
		return intCboSucursales;
	}
	public void setIntCboSucursales(Integer intCboSucursales) {
		this.intCboSucursales = intCboSucursales;
	}
	public Integer getIntCboSubsucursales() {
		return intCboSubsucursales;
	}
	public void setIntCboSubsucursales(Integer intCboSubsucursales) {
		this.intCboSubsucursales = intCboSubsucursales;
	}
	public Integer getIntCboFechaEnvio() {
		return intCboFechaEnvio;
	}
	public void setIntCboFechaEnvio(Integer intCboFechaEnvio) {
		this.intCboFechaEnvio = intCboFechaEnvio;
	}
	public Integer getIntCboFechaCobro() {
		return intCboFechaCobro;
	}
	public void setIntCboFechaCobro(Integer intCboFechaCobro) {
		this.intCboFechaCobro = intCboFechaCobro;
	}
	public List getBeanListEstructuraOrg() {
		return beanListEstructuraOrg;
	}
	public void setBeanListEstructuraOrg(List beanListEstructuraOrg) {
		this.beanListEstructuraOrg = beanListEstructuraOrg;
	}
	public Integer getIntCboConfigEstructura() {
		return intCboConfigEstructura;
	}
	public void setIntCboConfigEstructura(Integer intCboConfigEstructura) {
		this.intCboConfigEstructura = intCboConfigEstructura;
	}
	public Integer getIntCboEstadoPlanilla() {
		return intCboEstadoPlanilla;
	}
	public void setIntCboEstadoPlanilla(Integer intCboEstadoPlanilla) {
		this.intCboEstadoPlanilla = intCboEstadoPlanilla;
	}
	public Integer getIntRdoTipoEntidad() {
		return intRdoTipoEntidad;
	}
	public void setIntRdoTipoEntidad(Integer intRdoTipoEntidad) {
		this.intRdoTipoEntidad = intRdoTipoEntidad;
	}
	public Integer getIntCboEstadoDocumento() {
		return intCboEstadoDocumento;
	}
	public void setIntCboEstadoDocumento(Integer intCboEstadoDocumento) {
		this.intCboEstadoDocumento = intCboEstadoDocumento;
	}
	public Integer getIntCboTipoEntidadTerceros() {
		return intCboTipoEntidadTerceros;
	}
	public void setIntCboTipoEntidadTerceros(Integer intCboTipoEntidadTerceros) {
		this.intCboTipoEntidadTerceros = intCboTipoEntidadTerceros;
	}
	public Boolean getBlnCheckNombreEntidad() {
		return blnCheckNombreEntidad;
	}
	public void setBlnCheckNombreEntidad(Boolean blnCheckNombreEntidad) {
		this.blnCheckNombreEntidad = blnCheckNombreEntidad;
	}
	public String getStrNombreEntidad() {
		return strNombreEntidad;
	}
	public void setStrNombreEntidad(String strNombreEntidad) {
		this.strNombreEntidad = strNombreEntidad;
	}
	public Boolean getBlnCheckFechas() {
		return blnCheckFechas;
	}
	public void setBlnCheckFechas(Boolean blnCheckFechas) {
		this.blnCheckFechas = blnCheckFechas;
	}
	public Date getDtFechaDesde() {
		return dtFechaDesde;
	}
	public void setDtFechaDesde(Date dtFechaDesde) {
		this.dtFechaDesde = dtFechaDesde;
	}
	public Date getDtFechaHasta() {
		return dtFechaHasta;
	}
	public void setDtFechaHasta(Date dtFechaHasta) {
		this.dtFechaHasta = dtFechaHasta;
	}
	public Boolean getBlnCheckCodigo() {
		return blnCheckCodigo;
	}
	public void setBlnCheckCodigo(Boolean blnCheckCodigo) {
		this.blnCheckCodigo = blnCheckCodigo;
	}
	public EstructuraOrganica getBeanInstiPlanilla() {
		return beanInstiPlanilla;
	}
	public void setBeanInstiPlanilla(EstructuraOrganica beanInstiPlanilla) {
		this.beanInstiPlanilla = beanInstiPlanilla;
	}
	public EstructuraOrganica getBeanInstiAdministra() {
		return beanInstiAdministra;
	}
	public void setBeanInstiAdministra(EstructuraOrganica beanInstiAdministra) {
		this.beanInstiAdministra = beanInstiAdministra;
	}
	public EstructuraOrganica getBeanInstiCobra() {
		return beanInstiCobra;
	}
	public void setBeanInstiCobra(EstructuraOrganica beanInstiCobra) {
		this.beanInstiCobra = beanInstiCobra;
	}
	public EstructuraOrganica getBeanUniEjecuPlanilla() {
		return beanUniEjecuPlanilla;
	}
	public void setBeanUniEjecuPlanilla(EstructuraOrganica beanUniEjecuPlanilla) {
		this.beanUniEjecuPlanilla = beanUniEjecuPlanilla;
	}
	public EstructuraOrganica getBeanUniEjecuAdministra() {
		return beanUniEjecuAdministra;
	}
	public void setBeanUniEjecuAdministra(EstructuraOrganica beanUniEjecuAdministra) {
		this.beanUniEjecuAdministra = beanUniEjecuAdministra;
	}
	public EstructuraOrganica getBeanUniEjecuCobra() {
		return beanUniEjecuCobra;
	}
	public void setBeanUniEjecuCobra(EstructuraOrganica beanUniEjecuCobra) {
		this.beanUniEjecuCobra = beanUniEjecuCobra;
	}
	public EstructuraOrganica getBeanDepenPlanilla() {
		return beanDepenPlanilla;
	}
	public void setBeanDepenPlanilla(EstructuraOrganica beanDepenPlanilla) {
		this.beanDepenPlanilla = beanDepenPlanilla;
	}
	public EstructuraOrganica getBeanDepenAdministra() {
		return beanDepenAdministra;
	}
	public void setBeanDepenAdministra(EstructuraOrganica beanDepenAdministra) {
		this.beanDepenAdministra = beanDepenAdministra;
	}
	public EstructuraOrganica getBeanDepenCobra() {
		return beanDepenCobra;
	}
	public void setBeanDepenCobra(EstructuraOrganica beanDepenCobra) {
		this.beanDepenCobra = beanDepenCobra;
	}
	public List getBeanListInstiPlanilla() {
		return beanListInstiPlanilla;
	}
	public void setBeanListInstiPlanilla(List beanListInstiPlanilla) {
		this.beanListInstiPlanilla = beanListInstiPlanilla;
	}
	public List getBeanListInstiAdministra() {
		return beanListInstiAdministra;
	}
	public void setBeanListInstiAdministra(List beanListInstiAdministra) {
		this.beanListInstiAdministra = beanListInstiAdministra;
	}
	public List getBeanListInstiCobra() {
		return beanListInstiCobra;
	}
	public void setBeanListInstiCobra(List beanListInstiCobra) {
		this.beanListInstiCobra = beanListInstiCobra;
	}
	public List getBeanListUniEjecuPlanilla() {
		return beanListUniEjecuPlanilla;
	}
	public void setBeanListUniEjecuPlanilla(List beanListUniEjecuPlanilla) {
		this.beanListUniEjecuPlanilla = beanListUniEjecuPlanilla;
	}
	public List getBeanListUniEjecuAdministra() {
		return beanListUniEjecuAdministra;
	}
	public void setBeanListUniEjecuAdministra(List beanListUniEjecuAdministra) {
		this.beanListUniEjecuAdministra = beanListUniEjecuAdministra;
	}
	public List getBeanListUniEjecuCobra() {
		return beanListUniEjecuCobra;
	}
	public void setBeanListUniEjecuCobra(List beanListUniEjecuCobra) {
		this.beanListUniEjecuCobra = beanListUniEjecuCobra;
	}
	public List getBeanListDepenPlanilla() {
		return beanListDepenPlanilla;
	}
	public void setBeanListDepenPlanilla(List beanListDepenPlanilla) {
		this.beanListDepenPlanilla = beanListDepenPlanilla;
	}
	public List getBeanListDepenAdministra() {
		return beanListDepenAdministra;
	}
	public void setBeanListDepenAdministra(List beanListDepenAdministra) {
		this.beanListDepenAdministra = beanListDepenAdministra;
	}
	public List getBeanListDepenCobra() {
		return beanListDepenCobra;
	}
	public void setBeanListDepenCobra(List beanListDepenCobra) {
		this.beanListDepenCobra = beanListDepenCobra;
	}
	public String getStrCodigoExterno() {
		return strCodigoExterno;
	}
	public void setStrCodigoExterno(String strCodigoExterno) {
		this.strCodigoExterno = strCodigoExterno;
	}
	public EstructuraOrganica getBeanInsti() {
		return beanInsti;
	}
	public void setBeanInsti(EstructuraOrganica beanInsti) {
		this.beanInsti = beanInsti;
	}
	public EstructuraOrganica getBeanUniEjecu() {
		return beanUniEjecu;
	}
	public void setBeanUniEjecu(EstructuraOrganica beanUniEjecu) {
		this.beanUniEjecu = beanUniEjecu;
	}
	public EstructuraOrganica getBeanDepen() {
		return beanDepen;
	}
	public void setBeanDepen(EstructuraOrganica beanDepen) {
		this.beanDepen = beanDepen;
	}
	public Boolean getBlnNuevoRegEstruc() {
		return blnNuevoRegEstruc;
	}
	public void setBlnNuevoRegEstruc(Boolean blnNuevoRegEstruc) {
		this.blnNuevoRegEstruc = blnNuevoRegEstruc;
	}
	public Boolean getBlnShowFormEstructura() {
		return blnShowFormEstructura;
	}
	public void setBlnShowFormEstructura(Boolean blnShowFormEstructura) {
		this.blnShowFormEstructura = blnShowFormEstructura;
	}
	public PerJuridicaController getPerJuriController() {
		return perJuriController;
	}
	public void setPerJuriController(PerJuridicaController perJuriController) {
		this.perJuriController = perJuriController;
	}
	public ParametersController getParamController() {
		return paramController;
	}
	public void setParamController(ParametersController paramController) {
		this.paramController = paramController;
	}
	public AdminCuentaServiceImpl getPerJuridicaService() {
		return perJuridicaService;
	}
	public void setPerJuridicaService(AdminCuentaServiceImpl perJuridicaService) {
		this.perJuridicaService = perJuridicaService;
	}
	public CtaBancariaServiceImpl getCtaBancariaService() {
		return ctaBancariaService;
	}
	public void setCtaBancariaService(CtaBancariaServiceImpl ctaBancariaService) {
		this.ctaBancariaService = ctaBancariaService;
	}
	public DomicilioServiceImpl getDomicilioService() {
		return domicilioService;
	}
	public void setDomicilioService(DomicilioServiceImpl domicilioService) {
		this.domicilioService = domicilioService;
	}
	public ComunicacionServiceImpl getComunicacionService() {
		return comunicacionService;
	}
	public void setComunicacionService(ComunicacionServiceImpl comunicacionService) {
		this.comunicacionService = comunicacionService;
	}
	public RepLegalServiceImpl getRepLegalService() {
		return repLegalService;
	}
	public void setRepLegalService(RepLegalServiceImpl repLegalService) {
		this.repLegalService = repLegalService;
	}
}