package pe.com.tumi.credito.socio.convenio.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Perfil;
import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalle;
import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalleId;
import pe.com.tumi.credito.socio.convenio.domain.PerfilId;
import pe.com.tumi.credito.socio.convenio.domain.PerfilValidacion;
import pe.com.tumi.credito.socio.convenio.domain.PerfilValidacionId;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaControlComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaPlaneamientoComp;
import pe.com.tumi.credito.socio.convenio.facade.ConvenioFacadeLocal;
import pe.com.tumi.credito.socio.convenio.facade.HojaControlFacadeLocal;
import pe.com.tumi.credito.socio.convenio.facade.HojaPlaneamientoFacadeLocal;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class ControlProcesoController {
	protected  static Logger 	log 			= Logger.getLogger(ControlProcesoController.class);
	private int 						rows = 5;
	private Adenda						beanAdenda = null;
	private Perfil						beanPerfil = null;
	private Usuario						usuario;
	private Integer						intIdConvenio;
	private Integer						intIdTipoConvenio;
	private Integer						intSucursalConv;
	private Integer						intEstadoConvenio;
	private String						strEntidad;
	private List<HojaPlaneamientoComp>	listaEstructHojaPlaneamientoComp;
	private List<HojaControlComp>		listaEncargadoConvenio;
	private List<HojaControlComp>		listaJefaturaCredito;
	private List<HojaControlComp>		listaJefaturaCobranza;
	private List<HojaControlComp>		listaAsesorLegal;
	private List<HojaControlComp>		listaGerenciaGeneral;
	private Integer						intEstConvenio;
	private Boolean						enableDisableEncConv = true;
	private Boolean						enableDisableJefCred = true;
	private Boolean						enableDisableJefCobr = true;
	private Boolean						enableDisableAsesLeg = true;
	private Boolean						enableDisableAsistGer = true;
	private Integer						intIdFiltroConvenio;
	private Integer						intIdItemConvenio;
	
	private String						strObsEncargConv;
	private String						strObsJefatCred;
	private String						strObsJefatCobr;
	private String						strObsAsesLegal;
	private String						strObsGerencGral;
	
	private String						msgTxtEncargadoConvenio;
	private String						msgTxtJefaturaCredito;
	private String						msgTxtJefaturaCobranza;
	private String						msgTxtAsesorLegal;
	private String						msgTxtGerenciaGeneral;
	
	private Boolean						enableDisableBtnValidar = true;
	private Boolean						enableDisableBtnAprobar = true;
	private Boolean						enableDisableBtnRechazar = true;
	private Boolean						enableDisableBtnCancelar = false;
	private Boolean						formHojaControlRendered = true;
	
	HojaControlFacadeLocal 				facadeHojaControl = null;
	ConvenioFacadeLocal 				facadeConvenio = null;
	//Agregado por cdelosrios, 08/12/2013
	private boolean						poseePermiso;
	Integer MENU_HOJACONTROL = 104;
	Integer EMPRESA_USUARIO = null;
	Integer PERSONA_USUARIO = null;
	private List<Sucursal> 				listaSucursal;
	private PersonaFacadeRemote 		personaFacade = null;
	//Fin agregado por cdelosrios, 08/12/2013
	
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
	public Perfil getBeanPerfil() {
		return beanPerfil;
	}
	public void setBeanPerfil(Perfil beanPerfil) {
		this.beanPerfil = beanPerfil;
	}
	public Integer getIntIdTipoConvenio() {
		return intIdTipoConvenio;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public void setIntIdTipoConvenio(Integer intIdTipoConvenio) {
		this.intIdTipoConvenio = intIdTipoConvenio;
	}
	public Integer getIntSucursalConv() {
		return intSucursalConv;
	}
	public void setIntSucursalConv(Integer intSucursalConv) {
		this.intSucursalConv = intSucursalConv;
	}
	public Integer getIntEstadoConvenio() {
		return intEstadoConvenio;
	}
	public void setIntEstadoConvenio(Integer intEstadoConvenio) {
		this.intEstadoConvenio = intEstadoConvenio;
	}
	public String getStrEntidad() {
		return strEntidad;
	}
	public void setStrEntidad(String strEntidad) {
		this.strEntidad = strEntidad;
	}
	public List<HojaPlaneamientoComp> getListaEstructHojaPlaneamientoComp() {
		return listaEstructHojaPlaneamientoComp;
	}
	public void setListaEstructHojaPlaneamientoComp(
			List<HojaPlaneamientoComp> listaEstructHojaPlaneamientoComp) {
		this.listaEstructHojaPlaneamientoComp = listaEstructHojaPlaneamientoComp;
	}
	public List<HojaControlComp> getListaEncargadoConvenio() {
		return listaEncargadoConvenio;
	}
	public void setListaEncargadoConvenio(
			List<HojaControlComp> listaEncargadoConvenio) {
		this.listaEncargadoConvenio = listaEncargadoConvenio;
	}
	public List<HojaControlComp> getListaJefaturaCredito() {
		return listaJefaturaCredito;
	}
	public void setListaJefaturaCredito(List<HojaControlComp> listaJefaturaCredito) {
		this.listaJefaturaCredito = listaJefaturaCredito;
	}
	public List<HojaControlComp> getListaJefaturaCobranza() {
		return listaJefaturaCobranza;
	}
	public void setListaJefaturaCobranza(List<HojaControlComp> listaJefaturaCobranza) {
		this.listaJefaturaCobranza = listaJefaturaCobranza;
	}
	public List<HojaControlComp> getListaAsesorLegal() {
		return listaAsesorLegal;
	}
	public void setListaAsesorLegal(List<HojaControlComp> listaAsesorLegal) {
		this.listaAsesorLegal = listaAsesorLegal;
	}
	public List<HojaControlComp> getListaGerenciaGeneral() {
		return listaGerenciaGeneral;
	}
	public void setListaGerenciaGeneral(List<HojaControlComp> listaGerenciaGeneral) {
		this.listaGerenciaGeneral = listaGerenciaGeneral;
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	public Integer getIntEstConvenio() {
		return intEstConvenio;
	}
	public void setIntEstConvenio(Integer intEstConvenio) {
		this.intEstConvenio = intEstConvenio;
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
	public Integer getIntIdConvenio() {
		return intIdConvenio;
	}
	public void setIntIdConvenio(Integer intIdConvenio) {
		this.intIdConvenio = intIdConvenio;
	}
	public Integer getIntIdItemConvenio() {
		return intIdItemConvenio;
	}
	public void setIntIdItemConvenio(Integer intIdItemConvenio) {
		this.intIdItemConvenio = intIdItemConvenio;
	}
	public Integer getIntIdFiltroConvenio() {
		return intIdFiltroConvenio;
	}
	public void setIntIdFiltroConvenio(Integer intIdFiltroConvenio) {
		this.intIdFiltroConvenio = intIdFiltroConvenio;
	}
	public String getStrObsEncargConv() {
		return strObsEncargConv;
	}
	public void setStrObsEncargConv(String strObsEncargConv) {
		this.strObsEncargConv = strObsEncargConv;
	}
	public String getStrObsJefatCred() {
		return strObsJefatCred;
	}
	public void setStrObsJefatCred(String strObsJefatCred) {
		this.strObsJefatCred = strObsJefatCred;
	}
	public String getStrObsJefatCobr() {
		return strObsJefatCobr;
	}
	public void setStrObsJefatCobr(String strObsJefatCobr) {
		this.strObsJefatCobr = strObsJefatCobr;
	}
	public String getStrObsAsesLegal() {
		return strObsAsesLegal;
	}
	public void setStrObsAsesLegal(String strObsAsesLegal) {
		this.strObsAsesLegal = strObsAsesLegal;
	}
	public String getStrObsGerencGral() {
		return strObsGerencGral;
	}
	public void setStrObsGerencGral(String strObsGerencGral) {
		this.strObsGerencGral = strObsGerencGral;
	}
	public String getMsgTxtEncargadoConvenio() {
		return msgTxtEncargadoConvenio;
	}
	public void setMsgTxtEncargadoConvenio(String msgTxtEncargadoConvenio) {
		this.msgTxtEncargadoConvenio = msgTxtEncargadoConvenio;
	}
	public String getMsgTxtJefaturaCredito() {
		return msgTxtJefaturaCredito;
	}
	public void setMsgTxtJefaturaCredito(String msgTxtJefaturaCredito) {
		this.msgTxtJefaturaCredito = msgTxtJefaturaCredito;
	}
	public String getMsgTxtJefaturaCobranza() {
		return msgTxtJefaturaCobranza;
	}
	public void setMsgTxtJefaturaCobranza(String msgTxtJefaturaCobranza) {
		this.msgTxtJefaturaCobranza = msgTxtJefaturaCobranza;
	}
	public String getMsgTxtAsesorLegal() {
		return msgTxtAsesorLegal;
	}
	public void setMsgTxtAsesorLegal(String msgTxtAsesorLegal) {
		this.msgTxtAsesorLegal = msgTxtAsesorLegal;
	}
	public String getMsgTxtGerenciaGeneral() {
		return msgTxtGerenciaGeneral;
	}
	public void setMsgTxtGerenciaGeneral(String msgTxtGerenciaGeneral) {
		this.msgTxtGerenciaGeneral = msgTxtGerenciaGeneral;
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
	public Boolean getFormHojaControlRendered() {
		return formHojaControlRendered;
	}
	public void setFormHojaControlRendered(Boolean formHojaControlRendered) {
		this.formHojaControlRendered = formHojaControlRendered;
	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	public ControlProcesoController(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(MENU_HOJACONTROL);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}
	
	//Agregado por cdelosrios, 08/12/2013
	public void cargarValoresIniciales(){
		beanPerfil = new Perfil();
		try {
			facadeHojaControl = (HojaControlFacadeLocal)EJBFactory.getLocal(HojaControlFacadeLocal.class);
			facadeConvenio = (ConvenioFacadeLocal)EJBFactory.getLocal(ConvenioFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			cargarListaSucursal();
		} catch (EJBFactoryException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		listaEstructHojaPlaneamientoComp = new ArrayList<HojaPlaneamientoComp>();
		listaEncargadoConvenio = new ArrayList<HojaControlComp>();
		listaJefaturaCredito = new ArrayList<HojaControlComp>();
		listaJefaturaCobranza = new ArrayList<HojaControlComp>();
		listaAsesorLegal = new ArrayList<HojaControlComp>();
		listaGerenciaGeneral = new ArrayList<HojaControlComp>();
	}
	//Fin agregado por cdelosrios, 08/12/2013
	
	private void cargarListaSucursal() throws Exception{
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
		listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
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
	//Fin agregado por cdelosrios, 08/12/2013
	/*
	public void inicio(){
		Usuario usuario = null;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			setUsuario(usuario);
		} catch (Exception e) {
			log.error(e);
		}
	}*/
	
	/**************************************************************/
	/*  Nombre :  listarPerfilConvenio()      		      	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                                                    	 */
	/*  Objetivo: Listar los Convenios y verificar los que   */
	/*            están pendientes de validar y/o Aprobar.   */
	/*  Retorno : Listado de Convenios                       */
	/**************************************************************/
	public void cancelarConvenio(ActionEvent event){
		formHojaControlRendered = false;
		setEnableDisableBtnValidar(true);
		setEnableDisableBtnAprobar(true);
		setEnableDisableBtnRechazar(true);
		limpiarHojaControl();
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarHojaControl()      		      	 */
	/*                                                    	 */
	/*  Parametros. :  event       descripcion            	 */
	/*                                                    	 */
	/*  Objetivo: Limpiar todos los campos del formulario de */
	/*            Hoja de Control.                         	 */
	/*  Retorno : Formulario vacío de Hoja de Control        */
	/**************************************************************/
	public void limpiarHojaControl(){
		setStrEntidad("");
		setIntEstadoConvenio(null);
		setMsgTxtEncargadoConvenio("");
		setMsgTxtJefaturaCredito("");
		setMsgTxtJefaturaCobranza("");
		setMsgTxtAsesorLegal("");
		setMsgTxtGerenciaGeneral("");
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
	public void listarControlProceso(ActionEvent event) {
		log.info("--------------------Debugging ControlProcesoController.listarHojaPlaneamiento------------------------");
		HojaPlaneamientoFacadeLocal facade = null;
		List<Juridica> listaJuridica = null;
		String csvPkPersona = null;
		Juridica juridica = null;
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
			if(intIdFiltroConvenio!=null)
				o.getAdenda().getId().setIntConvenio(intIdFiltroConvenio);
			//Modificado por cdelosrios, 08/12/2013
			/*if(intIdTipoConvenio!=null && intIdTipoConvenio!=0)
				o.getAdenda().getId().setIntItemConvenio(intIdTipoConvenio);*/
			//Fin modificado por cdelosrios, 08/12/2013
			if(intSucursalConv!=null && intSucursalConv!=0)
				o.getAdenda().setIntSeguSucursalPk(intSucursalConv);
			if(strEntidad!=null){
				o.getEstructuraDetalle().getEstructura().getJuridica().setStrRazonSocial(strEntidad);
			}
			o.getAdenda().setIntParaEstadoHojaPlan(Constante.PARAM_T_ESTADODOCUMENTO_CONCLUIDO);
			
			listaEstructHojaPlaneamientoComp = facade.getListaHojaPlaneamientoCompDeBusquedaAdenda(o);
			//Agregado por cdelosrios, 08/12/2013
			if(strEntidad!=null && !strEntidad.trim().equals("")){
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
			//Fin agregado por cdelosrios, 08/12/2013
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	public void listarPerfilConvenio(ActionEvent event){
		setIntEstConvenio(new Integer(getRequestParameter("intIdEstadoConv")));
		
		Integer idConvenio = Integer.parseInt(getRequestParameter("intIdConvenio"));
		Integer idItemConvenio = Integer.parseInt(getRequestParameter("intIdItemConvenio"));
		setIntIdConvenio(idConvenio);
		setIntIdItemConvenio(idItemConvenio);
		Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		//this.usuario = usuario;
		//setUsuario(usuario);
		try{
			
			// CGD - 18.12.2013
			HojaPlaneamientoController hojaPlaneamientoController = (HojaPlaneamientoController)getSessionBean("hojaPlaneamientoController");
			hojaPlaneamientoController.setIntIdConvenioHP(idConvenio);
			hojaPlaneamientoController.setIntIdItemConvenioHP(idItemConvenio);
			
			//Integer idPerfil = Constante.GERENCIA_GENERAL;
			Integer idPerfil = usuario.getPerfil().getId().getIntIdPerfil();
			Integer intIdEmpresa = EMPRESA_USUARIO;
			log.info("idPerfil: " + idPerfil);
			log.info("intIdEmpresa: " + intIdEmpresa);
			setEnableDisableEncConv(!idPerfil.equals(Constante.ENCARGADO_CONVENIO));
			setEnableDisableJefCred(!idPerfil.equals(Constante.JEFATURA_CREDITOS));
			setEnableDisableJefCobr(!idPerfil.equals(Constante.JEFATURA_COBRANZAS));
			setEnableDisableAsesLeg(!idPerfil.equals(Constante.ASESOR_LEGAL));
			setEnableDisableAsistGer(!idPerfil.equals(Constante.GERENCIA_GENERAL));
			
			setEnableDisableBtnValidar((idPerfil.equals(Constante.ENCARGADO_CONVENIO) ||
										idPerfil.equals(Constante.JEFATURA_CREDITOS) ||
										idPerfil.equals(Constante.JEFATURA_COBRANZAS) ||
										idPerfil.equals(Constante.ASESOR_LEGAL)?false:true));
			log.info("getEnableDisableBtnValidar(): "+getEnableDisableBtnValidar());
			setEnableDisableBtnAprobar(!idPerfil.equals(Constante.GERENCIA_GENERAL));
			setEnableDisableBtnRechazar(!idPerfil.equals(Constante.GERENCIA_GENERAL));
			
			if(getRequestParameter("intIdEstadoConv").equals("2") || 
					getRequestParameter("intIdEstadoConv").equals("3")){
				setEnableDisableBtnAprobar(true);
				setEnableDisableBtnRechazar(true);
				setEnableDisableBtnValidar(true);
				setEnableDisableEncConv(true);
			}
			formHojaControlRendered = true;
			listaEncargadoConvenio = listarAreaEncargada(idConvenio, idItemConvenio, intIdEmpresa, Constante.ENCARGADO_CONVENIO);
			listaJefaturaCredito = listarAreaEncargada(idConvenio, idItemConvenio, intIdEmpresa, Constante.JEFATURA_CREDITOS);
			listaJefaturaCobranza = listarAreaEncargada(idConvenio, idItemConvenio, intIdEmpresa, Constante.JEFATURA_COBRANZAS);
			listaAsesorLegal = listarAreaEncargada(idConvenio, idItemConvenio, intIdEmpresa, Constante.ASESOR_LEGAL);
			listaGerenciaGeneral = listarAreaEncargada(idConvenio, idItemConvenio, intIdEmpresa, Constante.GERENCIA_GENERAL);
			
			if(listaEncargadoConvenio!=null && !listaEncargadoConvenio.isEmpty()){
				setStrObsEncargConv(listaEncargadoConvenio.get(0).getPerfil().getStrObservacion());
			}else{
				setStrObsEncargConv("");
			}
			if(listaJefaturaCredito!=null && !listaJefaturaCredito.isEmpty()){
				setStrObsJefatCred(listaJefaturaCredito.get(0).getPerfil().getStrObservacion());
			}else{
				setStrObsJefatCred("");
			}
			if(listaJefaturaCobranza!=null && !listaJefaturaCobranza.isEmpty()){
				setStrObsJefatCobr(listaJefaturaCobranza.get(0).getPerfil().getStrObservacion());
			}else{
				setStrObsJefatCobr("");
			}
			if(listaAsesorLegal!=null && !listaAsesorLegal.isEmpty()){
				setStrObsAsesLegal(listaAsesorLegal.get(0).getPerfil().getStrObservacion());
			}else{
				setStrObsAsesLegal("");
			}
			if(listaGerenciaGeneral!=null && !listaGerenciaGeneral.isEmpty()){
				setStrObsGerencGral(listaGerenciaGeneral.get(0).getPerfil().getStrObservacion());
			}else{
				setStrObsGerencGral("");
			}
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	public List<HojaControlComp> listarAreaEncargada(Integer idConvenio, Integer idItemConvenio, Integer intIdEmpresa, Integer intIdPerfil){
		log.info("----------------Debugging ControlProcesoController.listarEncargConvenio-------------------");
		//HojaControlFacadeLocal facade = null;
		List<HojaControlComp> lista = null;
		HojaControlComp hojaControl = new HojaControlComp();
		hojaControl.setPerfil(new Perfil());
		hojaControl.getPerfil().setId(new PerfilId());
		hojaControl.setPerfilValidacion(new PerfilValidacion());
		hojaControl.getPerfilValidacion().setId(new PerfilValidacionId());
		hojaControl.getPerfil().setIntConvenio(idConvenio);
		hojaControl.getPerfil().setIntItemConvenio(idItemConvenio);
		hojaControl.getPerfil().setIntPersEmpresaPk(intIdEmpresa);
		hojaControl.getPerfil().setIntSeguPerfilPk(intIdPerfil);
		
		hojaControl.setPerfilDetalle(new PerfilDetalle());
		hojaControl.getPerfilDetalle().setId(new PerfilDetalleId());
		hojaControl.getPerfilDetalle().getId().setIntPersEmpresaPk(intIdEmpresa);
		hojaControl.getPerfilDetalle().getId().setIntSeguPerfilPk(intIdPerfil);
		
		hojaControl.getPerfilValidacion().getId().setIntPersEmpresaPk(intIdEmpresa);
		hojaControl.getPerfilValidacion().getId().setIntSeguPerfilPk(intIdPerfil);
	    
	    try {
			//facade = (HojaControlFacadeLocal)EJBFactory.getLocal(HojaControlFacadeLocal.class);
			lista = facadeHojaControl.getListaAreaEncargadaDeHojaDeControl(hojaControl);
		} catch (BusinessException e) {
			log.error("error: " + e);
		}
		return lista;
	}
	
	private Boolean isValidoHojaControl(Perfil beanPerfil) throws BusinessException{
		Boolean validHojaControl = true;
		Perfil perfil = null;
		try {
			perfil = facadeHojaControl.getPerfilPorPKPerfil(beanPerfil);
		} catch (BusinessException e) {
			log.error("error: "+ e);
			throw e;
		}
		if(beanPerfil.getIntSeguPerfilPk().equals(Constante.ENCARGADO_CONVENIO) && perfil!=null){
			setMsgTxtEncargadoConvenio("Ya se ha validado el Convenio Seleccionado");
			validHojaControl = false;
		}else{
			setMsgTxtEncargadoConvenio("");
		}
		if(beanPerfil.getIntSeguPerfilPk().equals(Constante.JEFATURA_CREDITOS) && perfil!=null){
			setMsgTxtJefaturaCredito("Ya se ha validado el Convenio Seleccionado");
			validHojaControl = false;
		}else{
			setMsgTxtJefaturaCredito("");
		}
		if(beanPerfil.getIntSeguPerfilPk().equals(Constante.JEFATURA_COBRANZAS) && perfil!=null){
			setMsgTxtJefaturaCobranza("Ya se ha validado el Convenio Seleccionado");
			validHojaControl = false;
		}else{
			setMsgTxtJefaturaCobranza("");
		}
		if(beanPerfil.getIntSeguPerfilPk().equals(Constante.ASESOR_LEGAL) && perfil!=null){
			setMsgTxtAsesorLegal("Ya se ha validado el Convenio Seleccionado");
			validHojaControl = false;
		}else{
			setMsgTxtAsesorLegal("");
		}
		if(beanPerfil.getIntSeguPerfilPk().equals(Constante.GERENCIA_GENERAL) && perfil!=null){
			setMsgTxtGerenciaGeneral("Ya se ha validado el Convenio Seleccionado");
			validHojaControl = false;
		}else{
			setMsgTxtGerenciaGeneral("");
		}
		
		return validHojaControl;
	}
	
	public void validarConvenio(ActionEvent event) throws BusinessException{
		Usuario usuario = new Usuario();
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		//Integer intIdEmpresa = usuario.getPerfil().getId().getIntPersEmpresaPk();//Constante.PARAM_EMPRESASESION;
		Integer intIdEmpresa = EMPRESA_USUARIO;
		Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
		beanPerfil.setIntConvenio(getIntIdConvenio());
		beanPerfil.setIntItemConvenio(getIntIdItemConvenio());
		beanPerfil.setIntPersEmpresaPk(intIdEmpresa);
		beanPerfil.setIntSeguPerfilPk(intIdPerfil);
		//beanPerfil.setIntPersonaRegistraPk(Constante.PARAM_USUARIOSESION);
		beanPerfil.setIntPersonaRegistraPk(PERSONA_USUARIO);
		beanPerfil.setStrObservacion(intIdPerfil.equals(Constante.ENCARGADO_CONVENIO)?strObsEncargConv:
									 intIdPerfil.equals(Constante.JEFATURA_CREDITOS)?strObsJefatCred:
									 intIdPerfil.equals(Constante.JEFATURA_COBRANZAS)?strObsJefatCobr:
									 intIdPerfil.equals(Constante.ASESOR_LEGAL)?strObsAsesLegal:"");
		beanPerfil.setListaHojaControlComp(intIdPerfil.equals(Constante.ENCARGADO_CONVENIO)?listaEncargadoConvenio:
			 								intIdPerfil.equals(Constante.JEFATURA_CREDITOS)?listaJefaturaCredito:
			 									intIdPerfil.equals(Constante.JEFATURA_COBRANZAS)?listaJefaturaCobranza:
			 										intIdPerfil.equals(Constante.ASESOR_LEGAL)?listaAsesorLegal:listaGerenciaGeneral);
		
		if(isValidoHojaControl(beanPerfil) == false){
	    	log.info("Datos de Convenio no válidos. Se aborta el proceso de grabación de Convenio.");
	    	return;
	    }
		
		try {
			facadeHojaControl.grabarAdendaPerfil(beanPerfil);
			listarControlProceso(event);
			formHojaControlRendered = false;
			limpiarHojaControl();
		} catch (BusinessException e) {
			log.error("error: "+ e);
			throw e;
		}
	}
	
	public void aprobarRechazarConvenio(ActionEvent event) throws BusinessException{
		Adenda adenda = null;
		//Usuario usuario = new Usuario();
		//usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		//Integer intIdEmpresa = usuario.getPerfil().getId().getIntPersEmpresaPk();
		Integer intIdEmpresa = EMPRESA_USUARIO;
		//Integer intIdEmpresa = Constante.PARAM_EMPRESASESION;
		Integer intIdPerfil = Constante.GERENCIA_GENERAL;
		beanPerfil.setIntConvenio(getIntIdConvenio());
		beanPerfil.setIntItemConvenio(getIntIdItemConvenio());
		beanPerfil.setIntPersEmpresaPk(intIdEmpresa);
		beanPerfil.setIntSeguPerfilPk(intIdPerfil);
		//beanPerfil.setIntPersonaRegistraPk(Constante.PARAM_USUARIOSESION);
		beanPerfil.setIntPersonaRegistraPk(PERSONA_USUARIO);
		beanPerfil.setStrObservacion(strObsGerencGral);

		UIComponent uiComponent = event.getComponent();
		String btnApruebaRechaza = uiComponent.getId();
		
		if(isValidoHojaControl(beanPerfil) == false){
	    	log.info("Datos de Convenio no válidos. Se aborta el proceso de grabación de Convenio.");
	    	return;
	    }
		
		try {
			facadeHojaControl.grabarAdendaPerfil(beanPerfil);
			
			adenda = new Adenda();
			adenda.setId(new AdendaId());
			adenda.getId().setIntConvenio(getIntIdConvenio());
			adenda.getId().setIntItemConvenio(getIntIdItemConvenio());
			adenda.setIntParaEstadoValidacion(btnApruebaRechaza.equals("btnAprobar")?Constante.PARAM_T_ESTADODOCUMENTO_APROBADO:
				Constante.PARAM_T_ESTADODOCUMENTO_RECHAZADO);
			facadeConvenio.aprobarRechazarAdenda(adenda);
			listarControlProceso(event);
			formHojaControlRendered = false;
			limpiarHojaControl();
		} catch (BusinessException e) {
			log.error("error: "+ e);
			throw e;
		}
	}
	
	//Agregado por cdelosrios, 08/12/2013
	public String getLimpiarHojaControl(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(MENU_HOJACONTROL);
		log.info("POSEE PERMISO" + poseePermiso);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
			cancelarConvenio(null);
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		
		return "";
	}
	//Fin agregado por cdelosrios, 08/12/2013
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	
}