package pe.com.tumi.credito.socio.estructura.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ibm.ws.odc.Info;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.popup.CuentaBancariaController;
import pe.com.tumi.credito.popup.JuridicaController;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraDetalleComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeLocal;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.message.MessageController;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;

/************************************************************************/
/* Nombre de la clase: HojaPlaneamientoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* Ref. : */
/* Autor : Jhonathan Morán */
/* Versión : V1 */
/* Fecha creación : 28/12/2011 */
/* ********************************************************************* */

public class EstructuraOrganicaController {
	
	protected   static Logger 		    log = Logger.getLogger(EstructuraOrganicaController.class);
	protected 	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private 	List<EstructuraComp>	beanListInstitucion;
	private 	List<EstructuraComp>	beanListUnidadEjecutora;
	private 	List<EstructuraComp>	beanListDependencia;
	private 	List<Estructura>		listInstitucion;
	private 	List<Estructura>		listUnidadEjecutora;
	private 	List<Estructura>		listDependencia;
	private 	Estructura				beanInsti = null;
	private 	EstructuraDetalle		beanInstiPlanilla = new EstructuraDetalle();
	private 	EstructuraDetalle		beanInstiAdministra = new EstructuraDetalle();
	private 	EstructuraDetalle		beanInstiCobra = new EstructuraDetalle();
	private 	Estructura				beanUniEjecu = null;
	private 	EstructuraDetalle		beanUniEjecuPlanilla = new EstructuraDetalle();
	private 	EstructuraDetalle		beanUniEjecuAdministra = new EstructuraDetalle();
	private 	EstructuraDetalle		beanUniEjecuCobra = new EstructuraDetalle();
	private 	Estructura				beanDepen = null;
	private 	EstructuraDetalle		beanDepenPlanilla = new EstructuraDetalle();
	private 	EstructuraDetalle		beanDepenAdministra = new EstructuraDetalle();
	private 	EstructuraDetalle		beanDepenCobra = new EstructuraDetalle();
	private 	Boolean 				renderConfigN1 = false;
	//private 	Boolean 				renderConfigN2 = false;
	private 	Boolean 				renderConfigN3 = false;
	private 	Boolean 				renderConfigPlanillaN2 = false;
	private 	Boolean 				renderConfigAdministraN2 = false;
	private 	Boolean 				renderConfigCobranzaN2 = false;
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
	private 	String					strBtnVerConfigN1 = "Ver Configuración";
	private 	String					strBtnVerConfigN2 = "Ver Configuración";
	private 	String					strBtnVerConfigN3 = "Ver Configuración";
	private 	String 					strRenderedTableDetalle;
	private 	Boolean 				onNewDisabledInsti;
	private 	Boolean 				onNewDisabledUniEjecu;
	private 	Boolean 				onNewDisabledDepen;
	
	// Variables para obtener valores de los selects
	private 	Integer 				intCboInstitucion;
	private 	Integer					intCboUnidadEjecutora;
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
	private 	Integer					intFechaDesde;
	private 	Integer					intFechaHasta;
	private 	Boolean 				blnCheckCodigo;
	private 	String 					strCodigoExterno;
	private 	Integer 				intCboTipoEntidadTerceros;
	private 	Boolean					blnShowFormInstitucion = false;
	private 	Boolean					blnShowFormUnidadEjecutora = false;
	private 	Boolean					blnShowFormDependencia = false;
	private 	Integer					intNivelEstructura;
	private 	Boolean					blnCboInstitucionDisabled = false;
	
	//Propiedades para los combos de subsucursales
	private 	List<Sucursal> 			listJuridicaSucursal;
	private 	List<Subsucursal> 		listJuridicaSubsucursal;
	private 	List<Subsucursal> 		listSubsucursalPlanilla;
	private 	List<Subsucursal> 		listSubsucursalAdministra;
	private 	List<Subsucursal> 		listSubsucursalCobra;
	
	//Capturar el valor de los checks de Subsucursal para EstructuraDetalle
	private 	Boolean 				blnCheckSubsucursalInstiPlanilla;
	private 	Boolean 				blnCheckSubsucursalInstiAdministra;
	private 	Boolean 				blnCheckSubsucursalInstiCobra;
	private 	Boolean 				blnCheckSubsucursalUniEjecuPlanilla;
	private 	Boolean 				blnCheckSubsucursalUniEjecuAdministra;
	private 	Boolean 				blnCheckSubsucursalUniEjecuCobra;
	private 	Boolean 				blnCheckSubsucursalDepenPlanilla;
	private 	Boolean 				blnCheckSubsucursalDepenAdministra;
	private 	Boolean 				blnCheckSubsucursalDepenCobra;
	
	//Capturar el valor de los checks de Codigo externo para EstructuraDetalle
	private 	Boolean 				blnCheckCodigoInstiPlanilla;
	private 	Boolean 				blnCheckCodigoInstiCobra;
	private 	Boolean 				blnCheckCodigoUniEjecuPlanilla;
	private 	Boolean 				blnCheckCodigoUniEjecuCobra;
	private 	Boolean 				blnCheckCodigoDepenPlanilla;
	private 	Boolean 				blnCheckCodigoDepenCobra;
	
	private 	JuridicaController 		perJuridicaController;
	
	//atributos de sesión
	private 	Integer 				SESION_IDEMPRESA;
	private 	Integer 				SESION_IDUSUARIO;
	
	private static final Integer IDMENU_INSTI_DESC_INSTITU = 166;
	private static final Integer IDMENU_INSTI_DESC_UNIEJECU = 167;
	private static final Integer IDMENU_INSTI_DESC_DEPEN = 168;
	private 	Boolean 				blnInstitucionPermisoMenu;
	private 	Boolean 				blnUniEjePermisoMenu;
	private 	Boolean 				blnDependenciaPermisoMenu;
	
	//------------------------------------------------------------------------------------------------------------
	//Mantenimiento de Estructura Organica
	//------------------------------------------------------------------------------------------------------------
	public EstructuraOrganicaController(){
		loadDefault();
	}
	
	public void loadDefault(){
		log.info("-----------------------EstructuraOrganicaController.loadDefault-----------------------------");
		beanInsti = new Estructura();
		beanInsti.setId(new EstructuraId());
		beanInsti.setJuridica(new Juridica());
		
		beanUniEjecu = new Estructura();
		beanUniEjecu.setId(new EstructuraId());
		beanUniEjecu.setJuridica(new Juridica());
		
		beanDepen = new Estructura();
		beanDepen.setId(new EstructuraId());
		beanDepen.setJuridica(new Juridica());
		
		//atributos de sesión
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		
		getPermisoPerfil(usuarioSesion);
		loadListInstitucion();
	}
	
	public void getPermisoPerfil(Usuario usuarioSesion){
		PermisoPerfil permiso = null;
		try{
			PermisoPerfilId id = new PermisoPerfilId();
			id.setIntPersEmpresaPk(usuarioSesion.getPerfil().getId().getIntPersEmpresaPk());
			id.setIntIdPerfil(usuarioSesion.getPerfil().getId().getIntIdPerfil());
			
			id.setIntIdTransaccion(IDMENU_INSTI_DESC_INSTITU);
			PermisoFacadeRemote localPermiso = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnInstitucionPermisoMenu = (permiso == null)?true:false;
			
			id.setIntIdTransaccion(IDMENU_INSTI_DESC_UNIEJECU);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnUniEjePermisoMenu = (permiso == null)?true:false;
			
			id.setIntIdTransaccion(IDMENU_INSTI_DESC_DEPEN);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnDependenciaPermisoMenu = (permiso == null)?true:false;
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}
	}
	
	public List<EstructuraComp> listarEstructuraOrg(EstructuraComp estructuraComp) {
		log.info("-----------------------Debugging listarEstructuraOrg-----------------------------");
		log.info("estructuraComp.estructura.id.intNivel: "+estructuraComp.getEstructura().getId().getIntNivel());
		log.info("estructuraComp.estructura.intIdGrupo: "+estructuraComp.getEstructura().getIntIdGrupo());
		log.info("getBlnCheckNombreEntidad(): "+getBlnCheckNombreEntidad());
		log.info("getStrNombreEntidad(): "+getStrNombreEntidad());
		log.info("estructuraComp.estructura.intIdCodigoRel: "+estructuraComp.getEstructura().getIntIdCodigoRel());
		log.info("getIntCboConfigEstructura(): "+getIntCboConfigEstructura());
		log.info("getIntCboTipoSocio(): "+getIntCboTipoSocio());
		log.info("getIntCboModalidadEstructura(): "+getIntCboModalidadEstructura());
		//Filtro fechas
		log.info("getBlnCheckFechas(): "+getBlnCheckFechas());
		log.info("getIntCboEstadoPlanilla(): "+getIntCboEstadoPlanilla());
		log.info("getDtFechaDesde(): "+getIntFechaDesde());
		log.info("getDtFechaHasta(): "+getIntFechaHasta());
		log.info("getIntCboSucursales(): "+getIntCboSucursales());
		log.info("getIntCboSubsucursales(): "+getIntCboSubsucursales());
		log.info("getBlnCheckCodigo(): "+getBlnCheckCodigo());
		log.info("getStrCodigoExterno(): "+getStrCodigoExterno());
		
		estructuraComp.getEstructura().setIntPersEmpresaPk(SESION_IDEMPRESA);
		estructuraComp.getEstructura().getJuridica().setStrRazonSocial(getStrNombreEntidad());
		estructuraComp.getEstructuraDetalle().getId().setIntCaso(getIntCboConfigEstructura()<=0?null:getIntCboConfigEstructura());
		estructuraComp.getEstructuraDetalle().setIntParaTipoSocioCod(getIntCboTipoSocio()<=0?null:getIntCboTipoSocio());
		estructuraComp.getEstructuraDetalle().setIntParaModalidadCod(getIntCboModalidadEstructura()<=0?null:getIntCboModalidadEstructura());
		estructuraComp.getEstructuraDetalle().setIntSeguSucursalPk(getIntCboSucursales().equals(0)||getIntCboSucursales().equals(-1)?null:getIntCboSucursales());
		estructuraComp.getEstructuraDetalle().setIntSeguSubSucursalPk(getIntCboSubsucursales()==null||getIntCboSubsucursales()<=0?null:getIntCboSubsucursales());
		
		if(getIntCboEstadoPlanilla().equals(Constante.PARAM_T_ESTADOPLANILLA_ENVIADO)){
			estructuraComp.setIntFechaEnviadoDesde(getIntFechaDesde()!=null?getIntFechaDesde():null);
			estructuraComp.setIntFechaEnviadoHasta(getIntFechaHasta()!=null?getIntFechaHasta():null);
		}else if(getIntCboEstadoPlanilla().equals(Constante.PARAM_T_ESTADOPLANILLA_EFECTUADO)){
			estructuraComp.setIntFechaEfectuadoDesde(getIntFechaDesde()!=null?getIntFechaDesde():null);
			estructuraComp.setIntFechaEfectuadoHasta(getIntFechaHasta()!=null?getIntFechaHasta():null);
		}else if(getIntCboEstadoPlanilla().equals(Constante.PARAM_T_ESTADOPLANILLA_COBRO)){
			estructuraComp.setIntFechaChequeDesde(getIntFechaDesde()!=null?getIntFechaDesde():null);
			estructuraComp.setIntFechaChequeHasta(getIntFechaHasta()!=null?getIntFechaHasta():null);
		}
		estructuraComp.getEstructuraDetalle().setStrCodigoExterno(getStrCodigoExterno());
		
		List<EstructuraComp> listaEstructura = null;
		try {
			EstructuraFacadeLocal facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			listaEstructura = facade.getListaEstructuraComp(estructuraComp);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return listaEstructura;
	}
	
	public void buscarInstitucion(ActionEvent event) {
		log.info("-----------------------Debugging buscarInstitucion-----------------------------");
		EstructuraComp estructuraComp = new EstructuraComp();
		estructuraComp.setEstructura(new Estructura());
		estructuraComp.getEstructura().setId(new EstructuraId());
		estructuraComp.getEstructura().setJuridica(new Juridica());
		estructuraComp.setEstructuraDetalle(new EstructuraDetalle());
		estructuraComp.getEstructuraDetalle().setId(new EstructuraDetalleId());
		
		estructuraComp.getEstructura().getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_INSTITUCION);
		estructuraComp.getEstructura().setIntIdGrupo(getIntCboTipoEntidad()<=0?null:getIntCboTipoEntidad());
		
		List<EstructuraComp> listaEstructuraComp = null;
		listaEstructuraComp = listarEstructuraOrg(estructuraComp);
		
		if(listaEstructuraComp!=null){
			log.info("listaEstructura.size(): "+listaEstructuraComp.size());
		}
		
		setBeanListInstitucion(listaEstructuraComp);
	}
	
	public void buscarUnidadEjecutora(ActionEvent event) {
		log.info("-----------------------Debugging buscarUnidadEjecutora-----------------------------");
		log.info("getIntNivelEstructura(): "+getIntNivelEstructura());
		
		EstructuraComp estructuraComp = new EstructuraComp();
		estructuraComp.setEstructura(new Estructura());
		estructuraComp.getEstructura().setId(new EstructuraId());
		estructuraComp.getEstructura().setJuridica(new Juridica());
		estructuraComp.setEstructuraDetalle(new EstructuraDetalle());
		estructuraComp.getEstructuraDetalle().setId(new EstructuraDetalleId());
		
		estructuraComp.getEstructura().setIntIdCodigoRel(getIntCboInstitucion()<=0?null:getIntCboInstitucion());
		estructuraComp.getEstructura().getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA);
		
		List<EstructuraComp> listaEstructuraComp = null;
		listaEstructuraComp = listarEstructuraOrg(estructuraComp);
		
		if(listaEstructuraComp!=null){
			log.info("listaEstructura.size(): "+listaEstructuraComp.size());
		}
		
		setBeanListUnidadEjecutora(listaEstructuraComp);
	}
	
	public void buscarDependencia(ActionEvent event) {
		log.info("-----------------------Debugging buscarDependencia-----------------------------");
		log.info("getIntNivelEstructura(): "+getIntNivelEstructura());
		
		EstructuraComp estructuraComp = new EstructuraComp();
		estructuraComp.setEstructura(new Estructura());
		estructuraComp.getEstructura().setId(new EstructuraId());
		estructuraComp.getEstructura().setJuridica(new Juridica());
		estructuraComp.setEstructuraDetalle(new EstructuraDetalle());
		estructuraComp.getEstructuraDetalle().setId(new EstructuraDetalleId());
		
		estructuraComp.getEstructura().getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_DEPENDENCIA);
		estructuraComp.getEstructura().setIntIdCodigoRel(getIntCboUnidadEjecutora()<=0?null:getIntCboUnidadEjecutora());
		
		List<EstructuraComp> listaEstructuraComp = null;
		listaEstructuraComp = listarEstructuraOrg(estructuraComp);
		
		if(listaEstructuraComp!=null){
			log.info("listaEstructura.size(): "+listaEstructuraComp.size());
		}
		
		setBeanListDependencia(listaEstructuraComp);
	}
	
	public void agregarPlanillaInsti(ActionEvent event)  {
		log.info("-------------------------------------Debugging agregarPlanillaInsti-------------------------------------");		
		EstructuraDetalle instiPlanilla = new EstructuraDetalle();
		instiPlanilla = getBeanInstiPlanilla();
		instiPlanilla.setId(new EstructuraDetalleId());
		instiPlanilla.setIntPersEmpresaPk(SESION_IDEMPRESA);
		instiPlanilla.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_INSTITUCION);
		instiPlanilla.getId().setIntCaso(Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA);
		instiPlanilla.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(instiPlanilla,beanInsti.getListaEstructuraDetallePlanilla(),event.getComponent().getId());
		beanInsti.setListaEstructuraDetallePlanilla(listEstrucDet);
	}
	
	public void agregarAdministraInsti(ActionEvent event)  {
		log.info("-------------------------------------Debugging agregarAdministraInsti-------------------------------------");
		EstructuraDetalle instiAdmin = new EstructuraDetalle();
		instiAdmin = getBeanInstiAdministra();
		instiAdmin.setId(new EstructuraDetalleId());
		instiAdmin.setIntPersEmpresaPk(SESION_IDEMPRESA);
		instiAdmin.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_INSTITUCION);
		instiAdmin.getId().setIntCaso(Constante.PARAM_T_CASOESTRUCTURA_ADMINISTRA);
		instiAdmin.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(instiAdmin,beanInsti.getListaEstructuraDetalleAdministra(),event.getComponent().getId());
		beanInsti.setListaEstructuraDetalleAdministra(listEstrucDet);
	}
	
	public void agregarCobraInsti(ActionEvent event)  {
		log.info("-------------------------------------Debugging agregarCobraInsti-------------------------------------");
		EstructuraDetalle instiCobra = new EstructuraDetalle();
		instiCobra = getBeanInstiCobra();
		instiCobra.setId(new EstructuraDetalleId());
		instiCobra.setIntPersEmpresaPk(SESION_IDEMPRESA);
		instiCobra.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_INSTITUCION);
		instiCobra.getId().setIntCaso(Constante.PARAM_T_CASOESTRUCTURA_COBRA);
		instiCobra.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(instiCobra,beanInsti.getListaEstructuraDetalleCobranza(),event.getComponent().getId());
		beanInsti.setListaEstructuraDetalleCobranza(listEstrucDet);
	}
	
	public void agregarPlanillaUniEjecu(ActionEvent event)  {
		log.info("-------------------------------------Debugging agregarPlanillaUniEjecu-------------------------------------");
		EstructuraDetalle uniejePlanilla = new EstructuraDetalle();
		uniejePlanilla = getBeanUniEjecuPlanilla();
		uniejePlanilla.setId(new EstructuraDetalleId());
		uniejePlanilla.setIntPersEmpresaPk(SESION_IDEMPRESA);
		uniejePlanilla.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA);
		uniejePlanilla.getId().setIntCaso(Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA);
		uniejePlanilla.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(uniejePlanilla,beanUniEjecu.getListaEstructuraDetallePlanilla(),event.getComponent().getId());
		beanUniEjecu.setListaEstructuraDetallePlanilla(listEstrucDet);
	}
	
	public void agregarAdministraUniEjecu(ActionEvent event)  {
		log.info("-------------------------------------Debugging agregarAdministraUniEjecu-------------------------------------");
		EstructuraDetalle uniejeAdmin = new EstructuraDetalle();
		uniejeAdmin = getBeanUniEjecuAdministra();
		uniejeAdmin.setId(new EstructuraDetalleId());
		uniejeAdmin.setIntPersEmpresaPk(SESION_IDEMPRESA);
		uniejeAdmin.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA);
		uniejeAdmin.getId().setIntCaso(Constante.PARAM_T_CASOESTRUCTURA_ADMINISTRA);
		uniejeAdmin.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(uniejeAdmin,beanUniEjecu.getListaEstructuraDetalleAdministra(),event.getComponent().getId());
		beanUniEjecu.setListaEstructuraDetalleAdministra(listEstrucDet);
	}
	
	public void agregarCobraUniEjecu(ActionEvent event)  {
		log.info("-------------------------------------Debugging agregarCobraUniEjecu-------------------------------------");
		EstructuraDetalle uniejeCobra = new EstructuraDetalle();
		uniejeCobra = getBeanUniEjecuCobra();
		uniejeCobra.setId(new EstructuraDetalleId());
		uniejeCobra.setIntPersEmpresaPk(SESION_IDEMPRESA);
		uniejeCobra.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA);
		uniejeCobra.getId().setIntCaso(Constante.PARAM_T_CASOESTRUCTURA_COBRA);
		uniejeCobra.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(uniejeCobra,beanUniEjecu.getListaEstructuraDetalleCobranza(),event.getComponent().getId());
		beanUniEjecu.setListaEstructuraDetalleCobranza(listEstrucDet);
	}
	
	public void agregarPlanillaDepen(ActionEvent event)  {
		log.info("-------------------------------------Debugging agregarPlanillaDepen-------------------------------------");
		EstructuraDetalle depenPlanilla = new EstructuraDetalle();
		depenPlanilla = getBeanDepenPlanilla();
		depenPlanilla.setId(new EstructuraDetalleId());
		depenPlanilla.setIntPersEmpresaPk(SESION_IDEMPRESA);
		depenPlanilla.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_DEPENDENCIA);
		depenPlanilla.getId().setIntCaso(Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA);
		depenPlanilla.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(depenPlanilla,beanDepen.getListaEstructuraDetallePlanilla(),event.getComponent().getId());
		beanDepen.setListaEstructuraDetallePlanilla(listEstrucDet);
	}
	
	public void agregarAdministraDepen(ActionEvent event)  {
		log.info("-------------------------------------Debugging agregarAdministraDepen-------------------------------------");
		EstructuraDetalle depenAdmin = new EstructuraDetalle();
		depenAdmin = getBeanDepenAdministra();
		depenAdmin.setId(new EstructuraDetalleId());
		depenAdmin.setIntPersEmpresaPk(SESION_IDEMPRESA);
		depenAdmin.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_DEPENDENCIA);
		depenAdmin.getId().setIntCaso(Constante.PARAM_T_CASOESTRUCTURA_ADMINISTRA);
		depenAdmin.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(depenAdmin,beanDepen.getListaEstructuraDetalleAdministra(),event.getComponent().getId());
		beanDepen.setListaEstructuraDetalleAdministra(listEstrucDet);
	}
	
	public void agregarCobraDepen(ActionEvent event)  {
		log.info("-------------------------------------Debugging agregarCobraDepen-------------------------------------");
		EstructuraDetalle depenCobra = new EstructuraDetalle();
		depenCobra = getBeanDepenCobra();
		depenCobra.setId(new EstructuraDetalleId());
		depenCobra.setIntPersEmpresaPk(SESION_IDEMPRESA);
		depenCobra.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_DEPENDENCIA);
		depenCobra.getId().setIntCaso(Constante.PARAM_T_CASOESTRUCTURA_COBRA);
		depenCobra.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		ArrayList listEstrucDet = (ArrayList) agregarEstructura(depenCobra,beanDepen.getListaEstructuraDetalleCobranza(),event.getComponent().getId());
		beanDepen.setListaEstructuraDetalleCobranza(listEstrucDet);
	}
	
	public String getLabelCboPorIdMaestro(String id, Integer idItem){
		log.info("-------------------------------------Debugging getLabelCboPorIdMaestro-------------------------------------");
		List<Tabla> listaTabla = null;
		String label = null;
		try {
			TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = facade.getListaTablaPorIdMaestro(Integer.parseInt(id));
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("listaTala.size: "+listaTabla.size());
		log.info("idItem: "+idItem);
		for(int i=0; i<listaTabla.size(); i++){
			Tabla item = listaTabla.get(i);
			log.info("item.intIdDetalle: "+item.getIntIdDetalle());
			if(idItem.equals(item.getIntIdDetalle())){
				label = item.getStrDescripcion();
			} 
		}
		return label;
	}
	
	public List agregarEstructura(EstructuraDetalle beanEstructura,List<EstructuraDetalle> beanListEstrucDet, String idBtnAgregar)  {
		log.info("-------------------------------------Debugging agregarEstructura-------------------------------------");
		
		ArrayList listInstitucion = new ArrayList();
		if(beanListEstrucDet!=null){
			for(int i=0; i<beanListEstrucDet.size(); i++){
				EstructuraDetalle insti = beanListEstrucDet.get(i);
				listInstitucion.add(insti);
			}
		}
		
		log.info("intIdEmpresa: "+beanEstructura.getIntPersEmpresaPk());
		log.info("intIdNivel: "+beanEstructura.getId().getIntNivel());
		log.info("intCodigo: "+beanEstructura.getId().getIntCodigo());
		log.info("intCaso: "+beanEstructura.getId().getIntCaso());
		log.info("intIdUsuario: "+beanEstructura.getIntPersPersonaUsuarioPk());
		
		log.info("intTipoSocio: "+beanEstructura.getIntParaTipoSocioCod());
		log.info("intModalidad: "+beanEstructura.getIntParaModalidadCod());
		log.info("intSucursal: "+beanEstructura.getIntSeguSucursalPk());
		log.info("blnCheckSubsucursal: "+getBlnCheckSubsucursalInstiPlanilla());
		log.info("intSubsucursal: "+beanEstructura.getIntSeguSubSucursalPk());
		log.info("intDiaEnviado: "+beanEstructura.getIntDiaEnviado());
		log.info("intSaltoEnviado: "+beanEstructura.getIntSaltoEnviado());
		log.info("intDiaCheque: "+beanEstructura.getIntDiaCheque());
		log.info("intSaltoCheque: "+beanEstructura.getIntSaltoCheque());
		log.info("intDiaEfectuado: "+beanEstructura.getIntDiaEfectuado());
		log.info("intSaltoEfectuado: "+beanEstructura.getIntSaltoEfectuado());
		log.info("blnCheckCodigoInstiPlanilla: "+getBlnCheckCodigoInstiPlanilla());
		log.info("strCodigoExterno: "+beanEstructura.getStrCodigoExterno());
		
		//Seteando propiedades del bean estructura
		EstructuraDetalle institucion = new EstructuraDetalle();
		institucion.setId(new EstructuraDetalleId());
		institucion.setIntDiaEnviado(beanEstructura.getIntDiaEnviado());
		institucion.setIntSaltoEnviado(beanEstructura.getIntSaltoEnviado());
		institucion.setIntDiaEfectuado(beanEstructura.getIntDiaEfectuado());
		institucion.setIntSaltoEfectuado(beanEstructura.getIntSaltoEfectuado());
		institucion.setIntDiaCheque(beanEstructura.getIntDiaCheque());
		institucion.setIntSaltoCheque(beanEstructura.getIntSaltoCheque());
		
		institucion.setIntPersEmpresaPk(beanEstructura.getIntPersEmpresaPk());
		institucion.getId().setIntNivel(beanEstructura.getId().getIntNivel());
		institucion.getId().setIntCaso(beanEstructura.getId().getIntCaso());
		institucion.setIntParaTipoSocioCod(beanEstructura.getIntParaTipoSocioCod());
		institucion.setIntParaModalidadCod(beanEstructura.getIntParaModalidadCod());
		institucion.setIntSeguSucursalPk(beanEstructura.getIntSeguSucursalPk());
		institucion.setIntSeguSubSucursalPk(beanEstructura.getIntSeguSubSucursalPk());
		institucion.setStrCodigoExterno(beanEstructura.getStrCodigoExterno());
		institucion.setIntPersPersonaUsuarioPk(beanEstructura.getIntPersPersonaUsuarioPk());
		institucion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO); //Estado Activo por default
		
		log.info("idBtnAgregar: "+idBtnAgregar);
		if(idBtnAgregar.equals("btnAgregarPlanillaN1")){
			institucion.setListaSubsucursal(listSubsucursalPlanilla);
		}else if(idBtnAgregar.equals("btnAgregarAdministraN1")){
			institucion.setListaSubsucursal(listSubsucursalAdministra);
		}else if(idBtnAgregar.equals("btnAgregarCobraN1")){
			institucion.setListaSubsucursal(listSubsucursalCobra);
		}else if(idBtnAgregar.equals("btnAgregarPlanillaN2")){
			institucion.setListaSubsucursal(listSubsucursalPlanilla);
		}else if(idBtnAgregar.equals("btnAgregarAdministraN2")){
			institucion.setListaSubsucursal(listSubsucursalAdministra);
		}else if(idBtnAgregar.equals("btnAgregarCobraN2")){
			institucion.setListaSubsucursal(listSubsucursalCobra);
		}else if(idBtnAgregar.equals("btnAgregarPlanillaN3")){
			institucion.setListaSubsucursal(listSubsucursalPlanilla);
		}else if(idBtnAgregar.equals("btnAgregarAdministraN3")){
			institucion.setListaSubsucursal(listSubsucursalAdministra);
		}else if(idBtnAgregar.equals("btnAgregarCobraN3")){
			institucion.setListaSubsucursal(listSubsucursalCobra);
		}
		
		//Obteniendo mes enviado, efectuado y cheque
		String strFechaEnviado = null;
		String strFechaEfectuado = null;
		String strFechaCheque = null;
		if(institucion.getIntSaltoEnviado()!=null){
			strFechaEnviado = getLabelCboPorIdMaestro(Constante.PARAM_T_FECHAENVIOCOBRO, institucion.getIntSaltoEnviado());
		}
		if(institucion.getIntSaltoEfectuado()!=null){
			strFechaEfectuado = getLabelCboPorIdMaestro(Constante.PARAM_T_FECHAENVIOCOBRO, institucion.getIntSaltoEfectuado());
		}
		if(institucion.getIntSaltoCheque()!=null){
			strFechaCheque = getLabelCboPorIdMaestro(Constante.PARAM_T_FECHAENVIOCOBRO, institucion.getIntSaltoCheque());
		}
		
		log.info("strFechaEnviado: "+strFechaEnviado);
		log.info("strFechaEfectuado: "+strFechaEfectuado);
		log.info("strFechaCheque: "+strFechaCheque);
		
		//Obteniendo Labels de los SelectOneMenu
		String lblCboTipoSocio = null;
		lblCboTipoSocio = getLabelCboPorIdMaestro(Constante.PARAM_T_TIPOSOCIO, beanEstructura.getIntParaTipoSocioCod());
		
		String lblCboModalidad = null;
		lblCboModalidad = getLabelCboPorIdMaestro(Constante.PARAM_T_MODALIDADPLANILLA, beanEstructura.getIntParaModalidadCod());
		
		listInstitucion.add(institucion);
		log.info("listInstitucion.size: "+listInstitucion.size());
		return listInstitucion;
	}
	
	public void grabarEstructuraYPersonaRol(Estructura estructura, PersonaRol personaRol) {
		log.info("-------------------------------------Debugging grabarEstructuraYPersonaRol-------------------------------------");
		
		log.info("intIdEmpresa: "+SESION_IDEMPRESA);
		log.info("intIdUsuario: "+SESION_IDUSUARIO);
		
		//Seteando propiedades a Estructura
		estructura.setIntPersEmpresaPk(SESION_IDEMPRESA);
		estructura.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		try {
    		EstructuraFacadeLocal estructuraFacade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
    		estructura = estructuraFacade.grabarEstructuraYPersonaRol(estructura, personaRol);
    		log.info("estructura.id.intCodigo: "+estructura.getId().getIntCodigo());
		} catch (EJBFactoryException e) {
			log.error(e);
			e.printStackTrace();
		} catch (BusinessException e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	public void modificarEstructura(Estructura estructura) {
		log.info("-------------------------------------Debugging modificarEstructura-------------------------------------");
		
		log.info("intIdEmpresa: "+SESION_IDEMPRESA);
		log.info("intIdUsuario: "+SESION_IDUSUARIO);
		
		//Seteando propiedades a Estructura
		estructura.setIntPersEmpresaPk(SESION_IDEMPRESA);
		estructura.setIntPersPersonaUsuarioPk(SESION_IDUSUARIO);
		
		try {
    		EstructuraFacadeLocal estructuraFacade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
    		estructura = estructuraFacade.modificarEstructura(estructura);
    		log.info("estructura.id.intCodigo: "+estructura.getId().getIntCodigo());
		} catch (EJBFactoryException e) {
			log.error(e);
			e.printStackTrace();
		} catch (BusinessException e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	public void grabarInstitucion(ActionEvent event) {
		log.info("-------------------------------------Debugging grabarInstitucion-------------------------------------");
		
		perJuridicaController = (JuridicaController)getSessionBean("perJuridicaController");
		log.info("beanPerJuridicaN1.juridica.intIdPersona: "+perJuridicaController.getBeanPerJuridicaN1().getJuridica().getIntIdPersona());
		log.info("beanPerJuridicaN1.juridica.strRazonSocial: "+perJuridicaController.getBeanPerJuridicaN1().getJuridica().getStrRazonSocial());
		
		//Seteando propiedades a Institucion
		log.info("beanInsti.intIdGrupo: "+beanInsti.getIntIdGrupo());
		log.info("beanInsti.intParaTipoTerceroCod: "+beanInsti.getIntParaTipoTerceroCod());
		log.info("beanInsti.intParaEstadoCod: "+beanInsti.getIntParaEstadoCod());
		
		beanInsti.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_INSTITUCION);
		if(beanInsti.getIntPersPersonaPk()==null){
			beanInsti.setIntPersPersonaPk(perJuridicaController.getBeanPerJuridicaN1().getIntIdPersona());
		}
		if(beanInsti.getId().getIntCodigo()==null){
			beanInsti.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		}
		
		if(beanInsti!=null){
			log.info("beanInsti.id.intCodigo: "+beanInsti.getId().getIntCodigo());
			if(beanInsti.getId().getIntCodigo()==null){
				//Seteando PersonaRol
				PersonaRol personaRol = new PersonaRol();
				PersonaRolPK pk = new PersonaRolPK();
				pk.setIntIdEmpresa(SESION_IDEMPRESA);
				pk.setIntIdPersona(beanInsti.getIntPersPersonaPk());
				pk.setIntParaRolPk(Constante.PARAM_T_TIPOCOMPROBANTE_ENTIDAD);
				personaRol.setId(pk);
				personaRol.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				//Se guarda Estructura y PersonaRol
				grabarEstructuraYPersonaRol(beanInsti, personaRol);
			}else{
				modificarEstructura(beanInsti);
			}
		}
	}
	
	public void grabarUnidadEjecutora(ActionEvent event) {
		log.info("-------------------------------------Debugging grabarUnidadEjecutora-------------------------------------");
		
		perJuridicaController = (JuridicaController)getSessionBean("perJuridicaController");
		log.info("beanPerJuridicaN2.juridica.intIdPersona: "+perJuridicaController.getBeanPerJuridicaN2().getIntIdPersona());
		log.info("beanPerJuridicaN2.juridica.strRazonSocial: "+perJuridicaController.getBeanPerJuridicaN2().getJuridica().getStrRazonSocial());
		
		//Seteando propiedades a Unidad Ejecutora
		log.info("beanUniEjecu.intIdCodigoRel: "+beanUniEjecu.getIntIdCodigoRel());
		log.info("beanUniEjecu.intPersPersonaPk: "+beanUniEjecu.getIntPersPersonaPk());
		log.info("beanUniEjecu.intParaEstadoCod: "+beanUniEjecu.getIntParaEstadoCod());
		
		beanUniEjecu.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA);
		if(beanUniEjecu.getIntPersPersonaPk()==null){
			beanUniEjecu.setIntPersPersonaPk(perJuridicaController.getBeanPerJuridicaN2().getIntIdPersona());
		}
		beanUniEjecu.setIntNivelRel(Constante.PARAM_T_NIVELENTIDAD_INSTITUCION);
		beanUniEjecu.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		if(beanUniEjecu.getId().getIntCodigo()==null){
			//Seteando PersonaRol
			PersonaRol personaRol = new PersonaRol();
			PersonaRolPK pk = new PersonaRolPK();
			pk.setIntIdEmpresa(SESION_IDEMPRESA);
			pk.setIntIdPersona(beanUniEjecu.getIntPersPersonaPk());
			pk.setIntParaRolPk(Constante.PARAM_T_TIPOCOMPROBANTE_ENTIDAD);
			personaRol.setId(pk);
			personaRol.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			//Se guarda Estructura y PersonaRol
			grabarEstructuraYPersonaRol(beanUniEjecu, personaRol);
		}else{
			modificarEstructura(beanUniEjecu);
		}
	}
	
	public void grabarDependencia(ActionEvent event) {
		log.info("-------------------------------------Debugging grabarDependencia-------------------------------------");
		
		perJuridicaController = (JuridicaController)getSessionBean("perJuridicaController");
		log.info("beanPerJuridicaN3.juridica.intIdPersona: "+perJuridicaController.getBeanPerJuridicaN3().getJuridica().getIntIdPersona());
		log.info("beanPerJuridicaN3.juridica.strRazonSocial: "+perJuridicaController.getBeanPerJuridicaN3().getJuridica().getStrRazonSocial());
		
		//Seteando propiedades a Dependencia
		log.info("beanDepen.intIdCodigoRel: "+beanDepen.getIntIdCodigoRel());
		log.info("beanDepen.intPersPersonaPk: "+beanDepen.getIntPersPersonaPk());
		log.info("beanDepen.intParaEstadoCod: "+beanDepen.getIntParaEstadoCod());
		
		beanDepen.getId().setIntNivel(Constante.PARAM_T_NIVELENTIDAD_DEPENDENCIA);
		if(beanDepen.getIntPersPersonaPk()==null){
			beanDepen.setIntPersPersonaPk(perJuridicaController.getBeanPerJuridicaN3().getIntIdPersona());
		}
		beanDepen.setIntNivelRel(Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA);
		beanDepen.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		if(beanDepen.getId().getIntCodigo()==null){
			//Seteando PersonaRol
			PersonaRol personaRol = new PersonaRol();
			PersonaRolPK pk = new PersonaRolPK();
			pk.setIntIdEmpresa(SESION_IDEMPRESA);
			pk.setIntIdPersona(beanUniEjecu.getIntPersPersonaPk());
			pk.setIntParaRolPk(Constante.PARAM_T_TIPOCOMPROBANTE_ENTIDAD);
			personaRol.setId(pk);
			personaRol.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			//Se guarda Estructura y PersonaRol
			grabarEstructuraYPersonaRol(beanDepen, personaRol);
		}else{
			modificarEstructura(beanDepen);
		}
	}
	
	public void obtenerEstructura(ActionEvent event) {
		log.info("-------------------------------------Debugging obtenerEstructura-------------------------------------");
		String strEstrucKeys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelEstrucOrg:hiddenIdEstrucOrg");
		log.info("strEstrucKeys : "+strEstrucKeys);
		
		Integer intCodigoEstruc = 0;
		int intNivel = 0;
		
		if(strEstrucKeys != null){
			String[] strKeys = strEstrucKeys.split(",");
			log.info("intCodigoEstruc: "+strKeys[0]);
			log.info("intNivel: "+strKeys[1]);
			intCodigoEstruc = Integer.parseInt(strKeys[0]);
			intNivel = Integer.parseInt(strKeys[1]);
		}else{
			return;
		}
		
		Estructura prmEstructura = new Estructura();
		prmEstructura.setId(new EstructuraId());
		prmEstructura.getId().setIntCodigo(intCodigoEstruc != 0 ? intCodigoEstruc : null);
		prmEstructura.getId().setIntNivel(intNivel != 0 ? intNivel : null);
		
		Estructura estructura = null;
		try {
			EstructuraFacadeLocal facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			estructura = facade.getEstructuraPorPk(prmEstructura.getId());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("Se obtuvo Estructura(Codigo): "+estructura.getId().getIntCodigo());
		log.info("strNombreEntidad: "+estructura.getJuridica().getStrRazonSocial());
		
		//Obtener el detalle de Planilla, Administra y Cobra
		List<EstructuraDetalle> listDet = null;
		listDet = estructura.getListaEstructuraDetalle();

		log.info("arrayDet.size(): "+listDet.size());
		ArrayList<EstructuraDetalle> listPlanilla = new ArrayList<EstructuraDetalle>();
		ArrayList<EstructuraDetalle> listAdministra = new ArrayList<EstructuraDetalle>();
		ArrayList<EstructuraDetalle> listCobra = new ArrayList<EstructuraDetalle>();
		
		perJuridicaController = (JuridicaController)getSessionBean("perJuridicaController");
		
		if(intNivel == Constante.PARAM_T_NIVELENTIDAD_INSTITUCION){	
			log.info("estructura.juridica.intIdPersona: "+estructura.getJuridica().getIntIdPersona());
			Persona perJuridicaN1 = new Persona();
			perJuridicaN1.setJuridica(estructura.getJuridica());
			perJuridicaController.setBeanPerJuridicaN1(perJuridicaN1);
			
			for(int i=0; i<listDet.size(); i++){
				EstructuraDetalle ed = new EstructuraDetalle();
				ed = listDet.get(i);
				log.info("intCasoConfig: "+ed.getId().getIntCaso());
				//Obtener lista de subsucursales por suscursal
				ed.setListaSubsucursal(getSubsucursalesPorIdSucursal(ed.getIntSeguSucursalPk()));
				
				if(ed.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA)){ 
					listPlanilla.add(ed);
				}else if(ed.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_ADMINISTRA)){
					listAdministra.add(ed);
				}else if(ed.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_COBRA)){
					listCobra.add(ed);
				}
			}
			
			setBeanInsti(estructura);
			System.out.println("listPlanilla.size(): "+listPlanilla.size());
			System.out.println("listAdministra.size(): "+listAdministra.size());
			System.out.println("listCobra.size(): "+listCobra.size());
			beanInsti.setListaEstructuraDetallePlanilla(listPlanilla);
			beanInsti.setListaEstructuraDetalleAdministra(listAdministra);
			beanInsti.setListaEstructuraDetalleCobranza(listCobra);
			
			//Desplegar el DetalleEstructura
			setBlnShowFormInstitucion(true);
			setOnNewDisabledInsti(false);
			setRenderConfigN1(true);
			setRenderProcesPlanillaN1(true);
			setRenderAdministraN1(true);
			setRenderCobChequesN1(true);
		}if(intNivel == Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA){
			log.info("estructura.juridica.intIdPersona: "+estructura.getJuridica().getIntIdPersona());
			Persona perJuridicaN2 = new Persona();
			perJuridicaN2.setJuridica(estructura.getJuridica());
			perJuridicaController.setBeanPerJuridicaN2(perJuridicaN2);
			
			for(int i=0; i<listDet.size(); i++){
				EstructuraDetalle ed = new EstructuraDetalle();
				ed = listDet.get(i);
				log.info("intCasoConfig: "+ed.getId().getIntCaso());
				//Obtener lista de subsucursales por suscursal
				ed.setListaSubsucursal(getSubsucursalesPorIdSucursal(ed.getIntSeguSucursalPk()));
				
				if(ed.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA)){
					listPlanilla.add(ed);
				}else if(ed.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_ADMINISTRA)){
					listAdministra.add(ed);
				}else if(ed.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_COBRA)){
					listCobra.add(ed);
				}
			}
			
			setBeanUniEjecu(estructura);
			beanUniEjecu.setListaEstructuraDetallePlanilla(listPlanilla);
			beanUniEjecu.setListaEstructuraDetalleAdministra(listAdministra);
			beanUniEjecu.setListaEstructuraDetalleCobranza(listCobra);
			
			//Desplegar el DetalleEstructura
			setBlnCboInstitucionDisabled(true);
			setBlnShowFormUnidadEjecutora(true);
			setOnNewDisabledUniEjecu(false);
			setRenderConfigPlanillaN2(true); // Muestra el check para ver la configuracion de Planilla
			setRenderConfigAdministraN2(true); // Muestra el check para ver la configuracion de Administra
			setRenderConfigCobranzaN2(true); // Muestra el check para ver la configuracion de Cobranza
			setRenderProcesPlanillaN2(true); // Muestra el detalle de configuracion de Planilla
			setRenderAdministraN2(true); // Muestra el detalle de configuracion de Administra
			setRenderCobChequesN2(true); // Muestra el detalle de configuracion de Cobranza
		}if(intNivel == Constante.PARAM_T_NIVELENTIDAD_DEPENDENCIA){
			log.info("estructura.juridica.intIdPersona: "+estructura.getJuridica().getIntIdPersona());
			Persona perJuridicaN3 = new Persona();
			perJuridicaN3.setJuridica(estructura.getJuridica());
			perJuridicaController.setBeanPerJuridicaN3(perJuridicaN3);
			
			for(int i=0; i<listDet.size(); i++){
				EstructuraDetalle ed = new EstructuraDetalle();
				ed = listDet.get(i);
				log.info("intCasoConfig: "+ed.getId().getIntCaso());
				//Obtener lista de subsucursales por suscursal
				ed.setListaSubsucursal(getSubsucursalesPorIdSucursal(ed.getIntSeguSucursalPk()));
				
				if(ed.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA)){
					listPlanilla.add(ed);
				}else if(ed.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_ADMINISTRA)){
					listAdministra.add(ed);
				}else if(ed.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_COBRA)){
					listCobra.add(ed);
				}
			}
			
			setBeanDepen(estructura);
			beanDepen.setListaEstructuraDetallePlanilla(listPlanilla);
			beanDepen.setListaEstructuraDetalleAdministra(listAdministra);
			beanDepen.setListaEstructuraDetalleCobranza(listCobra);
			
			//Desplegar el DetalleEstructura
			setBlnShowFormDependencia(true);
			setOnNewDisabledDepen(false);
			setRenderConfigN3(true);
			setRenderProcesPlanillaN3(true);
			setRenderAdministraN3(true);
			setRenderCobChequesN3(true);
		}
	}
	
	public void eliminarEstructura(ActionEvent event){
		log.info("-------------------------------------Debugging eliminarEstructura-------------------------------------");
		String strEstrucKeys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelEstrucOrg:hiddenIdEstrucOrg");
		log.info("strEstrucKeys : "+strEstrucKeys);
		
		Integer intCodigoEstruc = null;
		Integer intNivel = null;
		Integer intPersPersonaPk = null;
		Integer intPersEmpresaPk = null;
		
		if(strEstrucKeys != null){
			String[] strKeys = strEstrucKeys.split(",");
			log.info("intCodigoEstruc: "+strKeys[0]);
			log.info("intNivel: "+strKeys[1]);
			log.info("intPersPersonaPk: "+strKeys[2]);
			log.info("intPersEmpresaPk: "+strKeys[3]);
			intCodigoEstruc = Integer.parseInt(strKeys[0]);
			intNivel = Integer.parseInt(strKeys[1]);
			intPersPersonaPk = Integer.parseInt(strKeys[2]);
			intPersEmpresaPk = Integer.parseInt(strKeys[3]);
		}else{
			return;
		}
		
		Estructura prmEstructura = new Estructura();
		prmEstructura.setId(new EstructuraId());
		prmEstructura.getId().setIntCodigo(intCodigoEstruc != null ? intCodigoEstruc : null);
		prmEstructura.getId().setIntNivel(intNivel != null ? intNivel : null);
		prmEstructura.setIntPersEmpresaPk(intPersEmpresaPk);
		prmEstructura.setIntPersPersonaPk(intPersPersonaPk);
		
		Estructura estructura = null;
		try {
			EstructuraFacadeLocal facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			estructura = facade.eliminarEstructura(prmEstructura);
		} catch (EJBFactoryException e) {
			log.error(e);
		} catch (BusinessException e) {
			log.error(e);
		}
	}
	
	public void listarEstructuraDetalle(Integer nivel, Integer codigoEstructura) {
		log.info("-------------------------------------Debugging listarEstructuraDetalle-------------------------------------");
		
		EstructuraId estructuraId = new EstructuraId();
		estructuraId.setIntCodigo(codigoEstructura);
		estructuraId.setIntNivel(nivel);
		log.info("estructuraId.intNivel: "+estructuraId.getIntNivel());
		log.info("estructuraId.intCodigo: "+estructuraId.getIntCodigo());
		
		Estructura estructura = null;
		try {
			EstructuraFacadeLocal facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			estructura = facade.getEstructuraPorPk(estructuraId);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("Se obtuvo Estructura(Codigo): "+estructura.getId().getIntCodigo());
		log.info("strNombreEntidad: "+estructura.getJuridica().getStrRazonSocial());
		
		//Obtener el detalle de Planilla, Administra y Cobra
		List<EstructuraDetalle> listDet =  estructura.getListaEstructuraDetalle();
		
		if(listDet!=null){
			log.info("arrayDet.size(): "+listDet.size());
			ArrayList<EstructuraDetalle> listPlanilla = new ArrayList<EstructuraDetalle>();
			ArrayList<EstructuraDetalle> listAdministra = new ArrayList<EstructuraDetalle>();
			ArrayList<EstructuraDetalle> listCobra = new ArrayList<EstructuraDetalle>();
			
			perJuridicaController = (JuridicaController)getSessionBean("perJuridicaController");
			
			if(nivel == 1){	
				for(int i=0; i<listDet.size(); i++){
					EstructuraDetalle ed = new EstructuraDetalle();
					ed = listDet.get(i);
					log.info("intCasoConfig: "+ed.getId().getIntCaso());
					//Obtener lista de subsucursales por suscursal
					ed.setListaSubsucursal(getSubsucursalesPorIdSucursal(ed.getIntSeguSucursalPk()));
					
					if(ed.getId().getIntCaso() == 1){
						listPlanilla.add(ed);
					}else if(ed.getId().getIntCaso() == 2){
						listAdministra.add(ed);
					}else if(ed.getId().getIntCaso() == 3){
						listCobra.add(ed);
					}
				}
				
				setBeanInsti(estructura);
				beanInsti.setListaEstructuraDetallePlanilla(listPlanilla);
				beanInsti.setListaEstructuraDetalleAdministra(listAdministra);
				beanInsti.setListaEstructuraDetalleCobranza(listCobra);
				
				//Desplegar el DetalleEstructura
				setBlnShowFormInstitucion(true);
				setRenderConfigN1(true);
				setRenderProcesPlanillaN1(true);
				setRenderAdministraN1(true);
				setRenderCobChequesN1(true);
			}if(nivel == 2){
				
				for(int i=0; i<listDet.size(); i++){
					EstructuraDetalle ed = new EstructuraDetalle();
					ed = listDet.get(i);
					log.info("intCasoConfig: "+ed.getId().getIntCaso());
					//Obtener lista de subsucursales por suscursal
					ed.setListaSubsucursal(getSubsucursalesPorIdSucursal(ed.getIntSeguSucursalPk()));
					
					if(ed.getId().getIntCaso() == 1){
						listPlanilla.add(ed);
					}else if(ed.getId().getIntCaso() == 2){
						listAdministra.add(ed);
					}else if(ed.getId().getIntCaso() == 3){
						listCobra.add(ed);
					}
				}
				
				setBeanUniEjecu(estructura);
				beanUniEjecu.setListaEstructuraDetallePlanilla(listPlanilla);
				beanUniEjecu.setListaEstructuraDetalleAdministra(listAdministra);
				beanUniEjecu.setListaEstructuraDetalleCobranza(listCobra);
				
				//Desplegar el DetalleEstructura
				setBlnShowFormUnidadEjecutora(true);
				setRenderConfigPlanillaN2(true); // Muestra el check para ver la configuracion de Planilla
				setRenderConfigAdministraN2(true); // Muestra el check para ver la configuracion de Administra
				setRenderConfigCobranzaN2(true); // Muestra el check para ver la configuracion de Cobranza
				setRenderProcesPlanillaN2(true); // Muestra el detalle de configuracion de Planilla
				setRenderAdministraN2(true); // Muestra el detalle de configuracion de Administra
				setRenderCobChequesN2(true); // Muestra el detalle de configuracion de Cobranza
			}
		}
	}
	
	public void eliminarEstructuraDetalle(ActionEvent event){
		log.info("---------------------------Debugging EstructuraController.eliminarEstructuraDetalle---------------------------");
		String strRenderedTable = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelEstrucDet:hiddenIdRenderedTable");
		log.info("strRenderedTable: "+strRenderedTable);
		if(strRenderedTable!=null){
			setStrRenderedTableDetalle(strRenderedTable);
		}
		
		String strEstrucKeys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUpDelEstrucDet:hiddenIdEstrucDet");
		log.info("strEstrucKeys : "+strEstrucKeys);
		
		Integer rowKey = null;
		Integer intCasoConfig = null;
		Integer intNivel = null;
		
		if(strEstrucKeys != null){
			String[] strKeys = strEstrucKeys.split(",");
			log.info("rowKey: "+strKeys[0]);
			log.info("intCasoConfig: "+strKeys[1]);
			log.info("intNivel: "+strKeys[2]);
			rowKey = Integer.parseInt(strKeys[0]);
			intCasoConfig = Integer.parseInt(strKeys[1]);
			intNivel = Integer.parseInt(strKeys[2]);
		}else{
			return;
		}
		
		if(intNivel==1){
			if(intCasoConfig==1 && beanInsti!=null){
				if(beanInsti.getListaEstructuraDetallePlanilla()!=null){
					beanInsti.setListaEstructuraDetallePlanilla(eliminarEstructuraDetallePorItemCaso(beanInsti.getListaEstructuraDetallePlanilla(),rowKey));
				}
			}else if(intCasoConfig==2 && beanInsti!=null){
				if(beanInsti.getListaEstructuraDetalleAdministra()!=null){
					beanInsti.setListaEstructuraDetalleAdministra(eliminarEstructuraDetallePorItemCaso(beanInsti.getListaEstructuraDetalleAdministra(),rowKey));
				}
			}else if(intCasoConfig==3 && beanInsti!=null){
				if(beanInsti.getListaEstructuraDetalleCobranza()!=null){
					beanInsti.setListaEstructuraDetalleCobranza(eliminarEstructuraDetallePorItemCaso(beanInsti.getListaEstructuraDetalleCobranza(),rowKey));
				}
			}
		}else if(intNivel==2){
			if(intCasoConfig==1 && beanUniEjecu!=null){
				if(beanUniEjecu.getListaEstructuraDetallePlanilla()!=null){
					beanUniEjecu.setListaEstructuraDetallePlanilla(eliminarEstructuraDetallePorItemCaso(beanUniEjecu.getListaEstructuraDetallePlanilla(),rowKey));
				}
			}else if(intCasoConfig==2 && beanUniEjecu!=null){
				if(beanUniEjecu.getListaEstructuraDetalleAdministra()!=null){
					beanUniEjecu.setListaEstructuraDetalleAdministra(eliminarEstructuraDetallePorItemCaso(beanUniEjecu.getListaEstructuraDetalleAdministra(),rowKey));
				}
			}else if(intCasoConfig==3 && beanUniEjecu!=null){
				if(beanUniEjecu.getListaEstructuraDetalleCobranza()!=null){
					beanUniEjecu.setListaEstructuraDetalleCobranza(eliminarEstructuraDetallePorItemCaso(beanUniEjecu.getListaEstructuraDetalleCobranza(),rowKey));
				}
			}
		}else if(intNivel==3){
			if(intCasoConfig==1 && beanDepen!=null){
				if(beanDepen.getListaEstructuraDetallePlanilla()!=null){
					beanDepen.setListaEstructuraDetallePlanilla(eliminarEstructuraDetallePorItemCaso(beanDepen.getListaEstructuraDetallePlanilla(),rowKey));
				}
			}else if(intCasoConfig==2 && beanDepen!=null){
				if(beanDepen.getListaEstructuraDetalleAdministra()!=null){
					beanDepen.setListaEstructuraDetalleAdministra(eliminarEstructuraDetallePorItemCaso(beanDepen.getListaEstructuraDetalleAdministra(),rowKey));
				}
			}else if(intCasoConfig==3 && beanDepen!=null){
				if(beanDepen.getListaEstructuraDetalleCobranza()!=null){
					beanDepen.setListaEstructuraDetalleCobranza(eliminarEstructuraDetallePorItemCaso(beanDepen.getListaEstructuraDetalleCobranza(),rowKey));
				}
			}
		}
	}
	
	public List<EstructuraDetalle> eliminarEstructuraDetallePorItemCaso(List<EstructuraDetalle> lista, Integer rowKey){
		log.info("---------------------------Debugging EstructuraController.eliminarEstructuraDetallePorItemCaso---------------------------");
		ArrayList<EstructuraDetalle> listaEstructuraDetalle = new ArrayList<EstructuraDetalle>();
		EstructuraDetalle ed = null;
		for(int i=0; i<lista.size(); i++){
			ed = lista.get(i);
			if(i!=rowKey){
				listaEstructuraDetalle.add(ed);
			}
		}
		return listaEstructuraDetalle;
	}
	
	public List<Subsucursal> getSubsucursalesPorIdSucursal(Integer idSucursal){
		log.info("---------------------------Debugging EstructuraController.getSubsucursalesPorIdSucursal---------------------------");
		List<Subsucursal> listSubsucursal = null;
		EmpresaFacadeRemote facade = null; 
		
		try {
			log.info("isSucursal: "+idSucursal);
			if(idSucursal!=0){
				facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listSubsucursal = facade.getListaSubSucursalPorIdSucursal(idSucursal);
				log.info("listSubsucursal.size: "+listSubsucursal.size());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listSubsucursal;
	}
	
	public void getListSubsucursalDeSucursal(ValueChangeEvent event) {
		log.info("-------------------------------------Debugging EstructuraOrganicaController.getListSubsucursalDeSucursal-------------------------------------");
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = (Integer)event.getNewValue();
			if(intIdSucursal!=0){
				facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(intIdSucursal);
				log.info("listaSubsucursal.size: "+listaSubsucursal.size());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("event.getComponent.getId(): "+event.getComponent().getId());
		if(event.getComponent().getId().equals("cboSucursalBusqueda")){
			setListJuridicaSubsucursal(listaSubsucursal);
		}else if(event.getComponent().getId().equals("cboSucursalPlanillaN1")){
			setListSubsucursalPlanilla(listaSubsucursal);
		}else if(event.getComponent().getId().equals("cboSucursalAdministraN1")){
			setListSubsucursalAdministra(listaSubsucursal);
		}else if(event.getComponent().getId().equals("cboSucursalCobraN1")){
			setListSubsucursalCobra(listaSubsucursal);
		}else if(event.getComponent().getId().equals("cboSucursalPlanillaN2")){
			setListSubsucursalPlanilla(listaSubsucursal);
		}else if(event.getComponent().getId().equals("cboSucursalAdministraN2")){
			setListSubsucursalAdministra(listaSubsucursal);
		}else if(event.getComponent().getId().equals("cboSucursalCobraN2")){
			setListSubsucursalCobra(listaSubsucursal);
		}else if(event.getComponent().getId().equals("cboSucursalPlanillaN3")){
			setListSubsucursalPlanilla(listaSubsucursal);
		}else if(event.getComponent().getId().equals("cboSucursalAdministraN3")){
			setListSubsucursalAdministra(listaSubsucursal);
		}else if(event.getComponent().getId().equals("cboSucursalCobraN3")){
			setListSubsucursalCobra(listaSubsucursal);
		}
	}
	
	public void getListUnidadEjecutora(ValueChangeEvent event) {
		log.info("-------------------------------------Debugging EstructuraOrganicaController.getListUnidadEjecutora-------------------------------------");
		EstructuraFacadeLocal facade = null;
		Integer intIdCodigoRel = null;
		List<Estructura> lista = null;
		try {
			log.info("intIdCodigoRel: "+event.getNewValue());
			intIdCodigoRel = Integer.parseInt(event.getNewValue().toString());
			if(intIdCodigoRel!=0){
				facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
				lista = facade.getListaEstructuraPorNivelYCodigoRel(Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA,intIdCodigoRel);
				log.info("listaEstructura.size: "+lista.size());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListUnidadEjecutora(lista);
	}
	
	//------------------------------------------------------------------------------------------------------------
	//Mostrar y ocultar controles para el Nivel 1: Institucion
	//------------------------------------------------------------------------------------------------------------
	
	public void showFormInstitucion(ActionEvent event) {
		log.info("-------------------------------------Debugging showFormInstitucion-------------------------------------");
		log.info("blnShowFormInstitucion: "+getBlnShowFormInstitucion());
		setBlnShowFormInstitucion(true);
		setOnNewDisabledInsti(true); //Deshabilita los controles por defecto cuando se desea crear un nuevo registro.
		setRenderConfigN1(false);
		setStrBtnAgregarN1("Agregar Configuración");
		setRenderProcesPlanillaN1(false);
		setRenderAdministraN1(false);
		setRenderCobChequesN1(false);
		limpiarFormInstitucion();
	}
	
	public void showFormUnidadEjecutora(ActionEvent event) {
		log.info("-------------------------------------Debugging FormUnidadEjecutora-------------------------------------");
		log.info("blnShowFormEstructura: "+getBlnShowFormUnidadEjecutora());
		setBlnShowFormUnidadEjecutora(true);
		setOnNewDisabledUniEjecu(true);
		setBlnCboInstitucionDisabled(false);
		limpiarFormUnidadEjecutora();
	}
	
	public void showFormDependencia(ActionEvent event) {
		log.info("-------------------------------------Debugging showFormDependencia-------------------------------------");
		log.info("blnShowFormEstructura: "+getBlnShowFormDependencia());
		setBlnShowFormDependencia(true);
		setOnNewDisabledDepen(true);
		limpiarFormDependencia();
	}
	
	public void limpiarFormInstitucion(){
		beanInsti = new Estructura();
		beanInsti.setId(new EstructuraId());
		//Limpiando Persona Juridica
		perJuridicaController = (JuridicaController)getSessionBean("perJuridicaController");
		perJuridicaController.setBeanPerJuridicaN1(new Persona());
		perJuridicaController.getBeanPerJuridicaN1().setJuridica(new Juridica());
	}
	
	public void limpiarFormUnidadEjecutora(){
		beanUniEjecu = new Estructura();
		beanUniEjecu.setId(new EstructuraId());
		//Limpiando Persona Juridica
		perJuridicaController = (JuridicaController)getSessionBean("perJuridicaController");
		perJuridicaController.setBeanPerJuridicaN2(new Persona());
		perJuridicaController.getBeanPerJuridicaN2().setJuridica(new Juridica());
	}
	
	public void limpiarFormDependencia(){
		beanDepen = new Estructura();
		beanDepen.setId(new EstructuraId());
		//Limpiando Persona Juridica
		perJuridicaController = (JuridicaController)getSessionBean("perJuridicaController");
		perJuridicaController.setBeanPerJuridicaN3(new Persona());
		perJuridicaController.getBeanPerJuridicaN3().setJuridica(new Juridica());
	}
	
	public void hideFormInstitucion(ActionEvent event) {
		log.info("-------------------------------------Debugging hideFormInstitucion-------------------------------------");
		log.info("blnShowFormInstitucion: "+getBlnShowFormInstitucion());
		limpiarFormInstitucion();
		
		//Contraer el DetalleEstructura
		setBlnShowFormInstitucion(false);
		setRenderConfigN1(false);
		setRenderProcesPlanillaN1(false);
		setRenderAdministraN1(false);
		setRenderCobChequesN1(false);
	}
	
	public void hideFormUnidadEjecutora(ActionEvent event) {
		log.info("-------------------------------------Debugging hideFormUnidadEjecutora-------------------------------------");
		log.info("blnShowFormEstructura: "+getBlnShowFormUnidadEjecutora());
		limpiarFormUnidadEjecutora();
		
		//Contraer el DetalleEstructura
		setBlnShowFormUnidadEjecutora(false);
		setRenderConfigPlanillaN2(true); // Muestra el check para ver la configuracion de Planilla
		setRenderConfigAdministraN2(true); // Muestra el check para ver la configuracion de Administra
		setRenderConfigCobranzaN2(true); // Muestra el check para ver la configuracion de Cobranza
		setRenderProcesPlanillaN2(true); // Muestra el detalle de configuracion de Planilla
		setRenderAdministraN2(true); // Muestra el detalle de configuracion de Administra
		setRenderCobChequesN2(true); // Muestra el detalle de configuracion de Cobranza
	}
	
	public void hideFormDependencia(ActionEvent event) {
		log.info("-------------------------------------Debugging hideFormDependencia-------------------------------------");
		log.info("blnShowFormEstructura: "+getBlnShowFormDependencia());
		limpiarFormDependencia();
		
		//Contraer el DetalleEstructura
		setBlnShowFormDependencia(false);
		setRenderConfigN3(false);
		setRenderProcesPlanillaN3(false);
		setRenderAdministraN3(false);
		setRenderCobChequesN3(false);
	}
	
	public void showConfigN1(ActionEvent event) {
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
	
	public void verConfigN1(ActionEvent event) {
		log.info("-----------------------Debugging EstrucOrgController.verConfigN1-----------------------------");
		log.info("renderConfigN1: "+getRenderConfigN1());
		if(getRenderConfigN1()==false){
			setRenderConfigN1(true);
			setStrBtnVerConfigN1("Ocultar Configuración");
			listarEstructuraDetalle(Constante.PARAM_T_NIVELENTIDAD_INSTITUCION, beanUniEjecu.getIntIdCodigoRel());
		}else{
			setRenderConfigN1(false);
			setStrBtnVerConfigN1("Ver Configuración");
		}
	}
	
	//------------------------------------------------------------------------------------------------------------
	//Mostrar y ocultar controles para el Nivel 2: Unidad Ejecutora
	//------------------------------------------------------------------------------------------------------------
	
	public void showConfigN2(ActionEvent event) throws EJBFactoryException, BusinessException {
		log.info("-----------------------Debugging EstrucOrgController.showConfigN2-----------------------------");
		log.info("renderConfigN2: "+getRenderConfigPlanillaN2());
		log.info("renderConfigN2: "+getRenderConfigAdministraN2());
		log.info("renderConfigN2: "+getRenderConfigCobranzaN2());
		log.info("beanUniEjecu.intIdCodigoRel: "+beanUniEjecu.getIntIdCodigoRel());
		
		if(renderConfigPlanillaN2==true || renderConfigAdministraN2==true || renderConfigCobranzaN2==true){
			setRenderConfigPlanillaN2(false);
			setRenderConfigAdministraN2(false);
			setRenderConfigCobranzaN2(false);
			setStrBtnAgregarN2("Agregar Configuración");
		}else{
			//Valida que se haya seleccionado una Institución para esta Unidad Ejecutora
			if(beanUniEjecu.getIntIdCodigoRel()==null || beanUniEjecu.getIntIdCodigoRel().equals(0)){
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setWarningMessage("Primero debe seleccionar una Institución.");
				return;
			}
			
			//Busca los DetalleEstructura que tenga la Institución seleccionada 
			//ya que la Institución y la Unidad Ejecutora no deben tener los mismos casos de configuración
			List<EstructuraDetalle> lista = null;
			EstructuraId estructuraPK = new EstructuraId();
			estructuraPK.setIntCodigo(beanUniEjecu.getIntIdCodigoRel());
			estructuraPK.setIntNivel(Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA);
			EstructuraFacadeLocal facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			lista = facade.getListaEstructuraDetallePorEstructuraPK(estructuraPK);
			log.info("lista.size: "+((lista!=null)?lista.size():lista));
			
			///Valida que se muestren sólo las configuraciones de Planilla, Administra o Cobranza que permita a esta Unidad Ejecutora
			Boolean bPlanilla = null;
			Boolean bAdministra = null;
			Boolean bCobranza = null;
			Integer caso = null;
			if(lista!=null && lista.size()>0){
				for(int i=0; i<lista.size(); i++){
					caso = lista.get(i).getId().getIntCaso();
					if(caso.equals(Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA)){
						bPlanilla = true;
					}else if(caso.equals(Constante.PARAM_T_CASOESTRUCTURA_ADMINISTRA)){
						bAdministra = true;
					}else if(caso.equals(Constante.PARAM_T_CASOESTRUCTURA_COBRA)){
						bCobranza = true;
					}
				}
			}
			
			log.info("bPlanilla: "+bPlanilla);
			log.info("bAdministra: "+bAdministra);
			log.info("bCobranza: "+bCobranza);
			if(bPlanilla==null || bPlanilla==false){
				setRenderConfigPlanillaN2(true);
			}
			if(bAdministra==null || bAdministra==false){
				setRenderConfigAdministraN2(true);
			}
			if(bCobranza==null || bCobranza==false){
				setRenderConfigCobranzaN2(true);
			}
			setStrBtnAgregarN2("Ocultar Configuración");
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
	
	public void verConfigN2(ActionEvent event) {
		log.info("-----------------------Debugging EstrucOrgController.verConfigN2-----------------------------");
		log.info("renderConfigN2: "+getRenderConfigPlanillaN2());
		log.info("renderConfigN2: "+getRenderConfigAdministraN2());
		log.info("renderConfigN2: "+getRenderConfigCobranzaN2());
		if(getRenderConfigPlanillaN2()==false && getRenderConfigAdministraN2()==false && getRenderConfigCobranzaN2()==false){
			setRenderConfigPlanillaN2(true);
			setRenderConfigAdministraN2(true);
			setRenderConfigCobranzaN2(true);
			setStrBtnVerConfigN2("Ocultar Configuración");
			listarEstructuraDetalle(Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA, beanDepen.getIntIdCodigoRel());
		}else{
			setRenderConfigPlanillaN2(false);
			setRenderConfigAdministraN2(false);
			setRenderConfigCobranzaN2(false);
			setStrBtnVerConfigN2("Ver Configuración");
		}
	}
	
	//------------------------------------------------------------------------------------------------------------
	//Mostrar y ocultar controles para el Nivel 3: Dependencia
	//------------------------------------------------------------------------------------------------------------
	
	public void showConfigN3(ActionEvent event) {
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
	
	public List<Estructura> loadListInstitucion() {
		log.info("-------------------------------------Debugging EstructuraOrganicaController.getListInstitucion-------------------------------------");
		try {
			if(listInstitucion == null){
				EstructuraFacadeLocal facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
				this.listInstitucion = facade.getListaEstructuraPorNivelYCodigoRel(Constante.PARAM_T_NIVELENTIDAD_INSTITUCION,null);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(listInstitucion!=null){
			log.info("beanListInstitucion.size: "+listInstitucion.size());
		}
		return listInstitucion;
	}
	
	//Metodos Utilitarios
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		return sesion.getAttribute(beanName);
	}
	
	//-------------------------------------------------------------
	//Getters y Settters
	//-------------------------------------------------------------
	public Boolean getRenderConfigN1() {
		return renderConfigN1;
	}
	public void setRenderConfigN1(Boolean renderConfigN1) {
		this.renderConfigN1 = renderConfigN1;
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
	public Integer getIntFechaDesde() {
		return intFechaDesde;
	}
	public void setIntFechaDesde(Integer intFechaDesde) {
		this.intFechaDesde = intFechaDesde;
	}
	public Integer getIntFechaHasta() {
		return intFechaHasta;
	}
	public void setIntFechaHasta(Integer intFechaHasta) {
		this.intFechaHasta = intFechaHasta;
	}
	public Boolean getBlnCheckCodigo() {
		return blnCheckCodigo;
	}
	public void setBlnCheckCodigo(Boolean blnCheckCodigo) {
		this.blnCheckCodigo = blnCheckCodigo;
	}
	public EstructuraDetalle getBeanInstiPlanilla() {
		return beanInstiPlanilla;
	}
	public void setBeanInstiPlanilla(EstructuraDetalle beanInstiPlanilla) {
		this.beanInstiPlanilla = beanInstiPlanilla;
	}
	public EstructuraDetalle getBeanInstiAdministra() {
		return beanInstiAdministra;
	}
	public void setBeanInstiAdministra(EstructuraDetalle beanInstiAdministra) {
		this.beanInstiAdministra = beanInstiAdministra;
	}
	public EstructuraDetalle getBeanInstiCobra() {
		return beanInstiCobra;
	}
	public void setBeanInstiCobra(EstructuraDetalle beanInstiCobra) {
		this.beanInstiCobra = beanInstiCobra;
	}
	public EstructuraDetalle getBeanUniEjecuPlanilla() {
		return beanUniEjecuPlanilla;
	}
	public void setBeanUniEjecuPlanilla(EstructuraDetalle beanUniEjecuPlanilla) {
		this.beanUniEjecuPlanilla = beanUniEjecuPlanilla;
	}
	public EstructuraDetalle getBeanUniEjecuAdministra() {
		return beanUniEjecuAdministra;
	}
	public void setBeanUniEjecuAdministra(EstructuraDetalle beanUniEjecuAdministra) {
		this.beanUniEjecuAdministra = beanUniEjecuAdministra;
	}
	public EstructuraDetalle getBeanUniEjecuCobra() {
		return beanUniEjecuCobra;
	}
	public void setBeanUniEjecuCobra(EstructuraDetalle beanUniEjecuCobra) {
		this.beanUniEjecuCobra = beanUniEjecuCobra;
	}
	public EstructuraDetalle getBeanDepenPlanilla() {
		return beanDepenPlanilla;
	}
	public void setBeanDepenPlanilla(EstructuraDetalle beanDepenPlanilla) {
		this.beanDepenPlanilla = beanDepenPlanilla;
	}
	public EstructuraDetalle getBeanDepenAdministra() {
		return beanDepenAdministra;
	}
	public void setBeanDepenAdministra(EstructuraDetalle beanDepenAdministra) {
		this.beanDepenAdministra = beanDepenAdministra;
	}
	public EstructuraDetalle getBeanDepenCobra() {
		return beanDepenCobra;
	}
	public void setBeanDepenCobra(EstructuraDetalle beanDepenCobra) {
		this.beanDepenCobra = beanDepenCobra;
	}
	public String getStrCodigoExterno() {
		return strCodigoExterno;
	}
	public void setStrCodigoExterno(String strCodigoExterno) {
		this.strCodigoExterno = strCodigoExterno;
	}
	public Estructura getBeanInsti() {
		return beanInsti;
	}
	public void setBeanInsti(Estructura beanInsti) {
		this.beanInsti = beanInsti;
	}
	public Estructura getBeanUniEjecu() {
		return beanUniEjecu;
	}
	public void setBeanUniEjecu(Estructura beanUniEjecu) {
		this.beanUniEjecu = beanUniEjecu;
	}
	public Estructura getBeanDepen() {
		return beanDepen;
	}
	public void setBeanDepen(Estructura beanDepen) {
		this.beanDepen = beanDepen;
	}
	public List<Sucursal> getListJuridicaSucursal() {
		log.info("-------------------------------------Debugging EstructuraOrganicaController.getListJuridicaSucursal-------------------------------------");
		log.info("sesionIntIdEmpresa: "+SESION_IDEMPRESA);
		try {
			if(listJuridicaSucursal == null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("listJuridicaSucursal.size: "+listJuridicaSucursal.size());
		return this.listJuridicaSucursal;
	}
	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
	}
	public List<Subsucursal> getListJuridicaSubsucursal() {
		return listJuridicaSubsucursal;
	}
	public void setListJuridicaSubsucursal(List<Subsucursal> listJuridicaSubsucursal) {
		this.listJuridicaSubsucursal = listJuridicaSubsucursal;
	}
	public Boolean getBlnCheckCodigoInstiPlanilla() {
		return blnCheckCodigoInstiPlanilla;
	}
	public void setBlnCheckCodigoInstiPlanilla(Boolean blnCheckCodigoInstiPlanilla) {
		this.blnCheckCodigoInstiPlanilla = blnCheckCodigoInstiPlanilla;
	}
	public Boolean getBlnCheckCodigoInstiCobra() {
		return blnCheckCodigoInstiCobra;
	}
	public void setBlnCheckCodigoInstiCobra(Boolean blnCheckCodigoInstiCobra) {
		this.blnCheckCodigoInstiCobra = blnCheckCodigoInstiCobra;
	}
	public Boolean getBlnCheckCodigoUniEjecuPlanilla() {
		return blnCheckCodigoUniEjecuPlanilla;
	}
	public void setBlnCheckCodigoUniEjecuPlanilla(
			Boolean blnCheckCodigoUniEjecuPlanilla) {
		this.blnCheckCodigoUniEjecuPlanilla = blnCheckCodigoUniEjecuPlanilla;
	}
	public Boolean getBlnCheckCodigoUniEjecuCobra() {
		return blnCheckCodigoUniEjecuCobra;
	}
	public void setBlnCheckCodigoUniEjecuCobra(Boolean blnCheckCodigoUniEjecuCobra) {
		this.blnCheckCodigoUniEjecuCobra = blnCheckCodigoUniEjecuCobra;
	}
	public Boolean getBlnCheckCodigoDepenPlanilla() {
		return blnCheckCodigoDepenPlanilla;
	}
	public void setBlnCheckCodigoDepenPlanilla(Boolean blnCheckCodigoDepenPlanilla) {
		this.blnCheckCodigoDepenPlanilla = blnCheckCodigoDepenPlanilla;
	}
	public Boolean getBlnCheckCodigoDepenCobra() {
		return blnCheckCodigoDepenCobra;
	}
	public void setBlnCheckCodigoDepenCobra(Boolean blnCheckCodigoDepenCobra) {
		this.blnCheckCodigoDepenCobra = blnCheckCodigoDepenCobra;
	}
	public Boolean getBlnCheckSubsucursalInstiPlanilla() {
		return blnCheckSubsucursalInstiPlanilla;
	}
	public void setBlnCheckSubsucursalInstiPlanilla(
			Boolean blnCheckSubsucursalInstiPlanilla) {
		this.blnCheckSubsucursalInstiPlanilla = blnCheckSubsucursalInstiPlanilla;
	}
	public Boolean getBlnCheckSubsucursalUniEjecuPlanilla() {
		return blnCheckSubsucursalUniEjecuPlanilla;
	}
	public void setBlnCheckSubsucursalUniEjecuPlanilla(
			Boolean blnCheckSubsucursalUniEjecuPlanilla) {
		this.blnCheckSubsucursalUniEjecuPlanilla = blnCheckSubsucursalUniEjecuPlanilla;
	}
	public Boolean getBlnCheckSubsucursalDepenPlanilla() {
		return blnCheckSubsucursalDepenPlanilla;
	}
	public void setBlnCheckSubsucursalDepenPlanilla(
			Boolean blnCheckSubsucursalDepenPlanilla) {
		this.blnCheckSubsucursalDepenPlanilla = blnCheckSubsucursalDepenPlanilla;
	}
	public Boolean getBlnCheckSubsucursalInstiAdministra() {
		return blnCheckSubsucursalInstiAdministra;
	}
	public void setBlnCheckSubsucursalInstiAdministra(
			Boolean blnCheckSubsucursalInstiAdministra) {
		this.blnCheckSubsucursalInstiAdministra = blnCheckSubsucursalInstiAdministra;
	}
	public Boolean getBlnCheckSubsucursalInstiCobra() {
		return blnCheckSubsucursalInstiCobra;
	}
	public void setBlnCheckSubsucursalInstiCobra(
			Boolean blnCheckSubsucursalInstiCobra) {
		this.blnCheckSubsucursalInstiCobra = blnCheckSubsucursalInstiCobra;
	}
	public Boolean getBlnCheckSubsucursalUniEjecuAdministra() {
		return blnCheckSubsucursalUniEjecuAdministra;
	}
	public void setBlnCheckSubsucursalUniEjecuAdministra(
			Boolean blnCheckSubsucursalUniEjecuAdministra) {
		this.blnCheckSubsucursalUniEjecuAdministra = blnCheckSubsucursalUniEjecuAdministra;
	}
	public Boolean getBlnCheckSubsucursalUniEjecuCobra() {
		return blnCheckSubsucursalUniEjecuCobra;
	}
	public void setBlnCheckSubsucursalUniEjecuCobra(
			Boolean blnCheckSubsucursalUniEjecuCobra) {
		this.blnCheckSubsucursalUniEjecuCobra = blnCheckSubsucursalUniEjecuCobra;
	}
	public Boolean getBlnCheckSubsucursalDepenAdministra() {
		return blnCheckSubsucursalDepenAdministra;
	}
	public void setBlnCheckSubsucursalDepenAdministra(
			Boolean blnCheckSubsucursalDepenAdministra) {
		this.blnCheckSubsucursalDepenAdministra = blnCheckSubsucursalDepenAdministra;
	}
	public Boolean getBlnCheckSubsucursalDepenCobra() {
		return blnCheckSubsucursalDepenCobra;
	}
	public void setBlnCheckSubsucursalDepenCobra(
			Boolean blnCheckSubsucursalDepenCobra) {
		this.blnCheckSubsucursalDepenCobra = blnCheckSubsucursalDepenCobra;
	}
	public Integer getIntNivelEstructura() {
		return intNivelEstructura;
	}
	public void setIntNivelEstructura(Integer intNivelEstructura) {
		this.intNivelEstructura = intNivelEstructura;
	}
	public List<Subsucursal> getListSubsucursalPlanilla() {
		return listSubsucursalPlanilla;
	}
	public void setListSubsucursalPlanilla(List<Subsucursal> listSubsucursalPlanilla) {
		this.listSubsucursalPlanilla = listSubsucursalPlanilla;
	}
	public List<Subsucursal> getListSubsucursalAdministra() {
		return listSubsucursalAdministra;
	}
	public void setListSubsucursalAdministra(
			List<Subsucursal> listSubsucursalAdministra) {
		this.listSubsucursalAdministra = listSubsucursalAdministra;
	}
	public List<Subsucursal> getListSubsucursalCobra() {
		return listSubsucursalCobra;
	}
	public void setListSubsucursalCobra(List<Subsucursal> listSubsucursalCobra) {
		this.listSubsucursalCobra = listSubsucursalCobra;
	}
	public String getStrBtnVerConfigN1() {
		return strBtnVerConfigN1;
	}
	public void setStrBtnVerConfigN1(String strBtnVerConfigN1) {
		this.strBtnVerConfigN1 = strBtnVerConfigN1;
	}
	public String getStrBtnVerConfigN2() {
		return strBtnVerConfigN2;
	}
	public void setStrBtnVerConfigN2(String strBtnVerConfigN2) {
		this.strBtnVerConfigN2 = strBtnVerConfigN2;
	}
	public String getStrBtnVerConfigN3() {
		return strBtnVerConfigN3;
	}
	public void setStrBtnVerConfigN3(String strBtnVerConfigN3) {
		this.strBtnVerConfigN3 = strBtnVerConfigN3;
	}
	public JuridicaController getPerJuridicaController() {
		return perJuridicaController;
	}
	public void setPerJuridicaController(JuridicaController perJuridicaController) {
		this.perJuridicaController = perJuridicaController;
	}
	public List<Estructura> getListInstitucion() {
		return listInstitucion;
	}
	public void setListInstitucion(List<Estructura> listInstitucion) {
		this.listInstitucion = listInstitucion;
	}
	public List<Estructura> getListUnidadEjecutora() {
		return listUnidadEjecutora;
	}
	public void setListUnidadEjecutora(List<Estructura> listUnidadEjecutora) {
		this.listUnidadEjecutora = listUnidadEjecutora;
	}
	public List<Estructura> getListDependencia() {
		return listDependencia;
	}
	public void setListDependencia(List<Estructura> listDependencia) {
		this.listDependencia = listDependencia;
	}
	public List<EstructuraComp> getBeanListInstitucion() {
		return beanListInstitucion;
	}
	public void setBeanListInstitucion(List<EstructuraComp> beanListInstitucion) {
		this.beanListInstitucion = beanListInstitucion;
	}
	public List<EstructuraComp> getBeanListUnidadEjecutora() {
		return beanListUnidadEjecutora;
	}
	public void setBeanListUnidadEjecutora(
			List<EstructuraComp> beanListUnidadEjecutora) {
		this.beanListUnidadEjecutora = beanListUnidadEjecutora;
	}
	public List<EstructuraComp> getBeanListDependencia() {
		return beanListDependencia;
	}
	public void setBeanListDependencia(List<EstructuraComp> beanListDependencia) {
		this.beanListDependencia = beanListDependencia;
	}
	public Boolean getBlnShowFormInstitucion() {
		return blnShowFormInstitucion;
	}
	public void setBlnShowFormInstitucion(Boolean blnShowFormInstitucion) {
		this.blnShowFormInstitucion = blnShowFormInstitucion;
	}
	public Boolean getBlnShowFormUnidadEjecutora() {
		return blnShowFormUnidadEjecutora;
	}
	public void setBlnShowFormUnidadEjecutora(Boolean blnShowFormUnidadEjecutora) {
		this.blnShowFormUnidadEjecutora = blnShowFormUnidadEjecutora;
	}
	public Boolean getBlnShowFormDependencia() {
		return blnShowFormDependencia;
	}
	public void setBlnShowFormDependencia(Boolean blnShowFormDependencia) {
		this.blnShowFormDependencia = blnShowFormDependencia;
	}
	public Integer getIntCboInstitucion() {
		return intCboInstitucion;
	}
	public void setIntCboInstitucion(Integer intCboInstitucion) {
		this.intCboInstitucion = intCboInstitucion;
	}
	public Integer getIntCboUnidadEjecutora() {
		return intCboUnidadEjecutora;
	}
	public void setIntCboUnidadEjecutora(Integer intCboUnidadEjecutora) {
		this.intCboUnidadEjecutora = intCboUnidadEjecutora;
	}
	public String getStrRenderedTableDetalle() {
		return strRenderedTableDetalle;
	}
	public void setStrRenderedTableDetalle(String strRenderedTableDetalle) {
		this.strRenderedTableDetalle = strRenderedTableDetalle;
	}
	public Boolean getRenderConfigPlanillaN2() {
		return renderConfigPlanillaN2;
	}
	public void setRenderConfigPlanillaN2(Boolean renderConfigPlanillaN2) {
		this.renderConfigPlanillaN2 = renderConfigPlanillaN2;
	}
	public Boolean getRenderConfigAdministraN2() {
		return renderConfigAdministraN2;
	}
	public void setRenderConfigAdministraN2(Boolean renderConfigAdministraN2) {
		this.renderConfigAdministraN2 = renderConfigAdministraN2;
	}
	public Boolean getRenderConfigCobranzaN2() {
		return renderConfigCobranzaN2;
	}
	public void setRenderConfigCobranzaN2(Boolean renderConfigCobranzaN2) {
		this.renderConfigCobranzaN2 = renderConfigCobranzaN2;
	}
	public Boolean getOnNewDisabledInsti() {
		return onNewDisabledInsti;
	}
	public void setOnNewDisabledInsti(Boolean onNewDisabledInsti) {
		this.onNewDisabledInsti = onNewDisabledInsti;
	}
	public Boolean getOnNewDisabledUniEjecu() {
		return onNewDisabledUniEjecu;
	}
	public void setOnNewDisabledUniEjecu(Boolean onNewDisabledUniEjecu) {
		this.onNewDisabledUniEjecu = onNewDisabledUniEjecu;
	}
	public Boolean getOnNewDisabledDepen() {
		return onNewDisabledDepen;
	}
	public void setOnNewDisabledDepen(Boolean onNewDisabledDepen) {
		this.onNewDisabledDepen = onNewDisabledDepen;
	}
	public Boolean getBlnCboInstitucionDisabled() {
		return blnCboInstitucionDisabled;
	}
	public void setBlnCboInstitucionDisabled(Boolean blnCboInstitucionDisabled) {
		this.blnCboInstitucionDisabled = blnCboInstitucionDisabled;
	}
	public Integer getSESION_IDEMPRESA() {
		return SESION_IDEMPRESA;
	}
	public void setSESION_IDEMPRESA(Integer sESION_IDEMPRESA) {
		SESION_IDEMPRESA = sESION_IDEMPRESA;
	}
	public Integer getSESION_IDUSUARIO() {
		return SESION_IDUSUARIO;
	}
	public void setSESION_IDUSUARIO(Integer sESION_IDUSUARIO) {
		SESION_IDUSUARIO = sESION_IDUSUARIO;
	}
	public Boolean getBlnInstitucionPermisoMenu() {
		return blnInstitucionPermisoMenu;
	}
	public void setBlnInstitucionPermisoMenu(Boolean blnInstitucionPermisoMenu) {
		this.blnInstitucionPermisoMenu = blnInstitucionPermisoMenu;
	}
	public Boolean getBlnUniEjePermisoMenu() {
		return blnUniEjePermisoMenu;
	}
	public void setBlnUniEjePermisoMenu(Boolean blnUniEjePermisoMenu) {
		this.blnUniEjePermisoMenu = blnUniEjePermisoMenu;
	}
	public Boolean getBlnDependenciaPermisoMenu() {
		return blnDependenciaPermisoMenu;
	}
	public void setBlnDependenciaPermisoMenu(Boolean blnDependenciaPermisoMenu) {
		this.blnDependenciaPermisoMenu = blnDependenciaPermisoMenu;
	}
}