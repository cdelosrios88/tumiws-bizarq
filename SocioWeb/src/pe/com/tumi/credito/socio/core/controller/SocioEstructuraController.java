package pe.com.tumi.credito.socio.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
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
import pe.com.tumi.message.MessageController;
import pe.com.tumi.persona.empresa.domain.Juridica;

import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

/************************************************************************/
/* Nombre de la clase: HojaPlaneamientoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* Ref. : */
/* Autor : Jhonathan Morán */
/* Versión : 0.1 */
/* Fecha creación : 28/12/2011 */
/* ********************************************************************* */

public class SocioEstructuraController {
	
	protected   static Logger 				log = Logger.getLogger(SocioEstructuraController.class);
	private 	Estructura 					estrucBusq;
	private 	List<EstructuraDetalle> 	listaEstructuraDetalle;
	private 	List<Estructura>			listEstructura;
	private 	List<Sucursal>				listSucursal;
	private 	String 						strIdModalPanel = null;
	private 	String 						pgListSocioEstructura = null;
	private 	EstructuraDetalle 			entidadSeleccionada;
	private 	SocioController 			socioController = null;
	private 	String 						strCallingFormId;
	private 	String 						strFormIdSocioNatu;
	//atributos de sesión
	private 	Integer 					SESION_IDEMPRESA;
	private 	Integer 					SESION_IDUSUARIO;
	private 	Integer 					SESION_IDSUCURSAL;
	private 	Integer 					SESION_IDSUBSUCURSAL;
	
	
	public SocioEstructuraController(){
		log.info("-------------------------------------Debugging Constructor de SocioEstructuraController-------------------------------------");
		initSocioEstructura();
	}
	
	public void initSocioEstructura(){
		log.info("-------------------------------------Debugging SocioEstructuraController.initSocioEstructura-------------------------------------");
		listaEstructuraDetalle = new ArrayList<EstructuraDetalle>();
		
		strIdModalPanel = "mpSocioEstructura";
		pgListSocioEstructura = "pgSocioEstructura,tblEntidad";
		
		estrucBusq = new Estructura();
		estrucBusq.setJuridica(new Juridica());
		
		strFormIdSocioNatu = "frmSocioNatural";
		
		entidadSeleccionada = null;
		
		//atributos de sesión
		Usuario usuarioSesion = (Usuario)FacesContextUtil.getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
	}
	
	public void listarSocioEstructura(EstructuraComp estrucComp){
		log.info("-------------------------------------Debugging SocioEstructuraController.listarSocioEstructura-------------------------------------");
		if(estrucComp==null){
			estrucComp = new EstructuraComp();
		}
		
		/**
		 * @author Christian De los Ríos - 09/04/2013
		 * Para el perfil Asociativo (11) debe permitir ver todas las Estructuras
		 * */
		Usuario usuarioSesion = (Usuario)FacesContextUtil.getRequest().getSession().getAttribute("usuario");
		Integer intIdPerfil = usuarioSesion.getPerfil().getId().getIntIdPerfil();
		
		log.info("socioEstrucBusq.strEntidad: "+getEstrucBusq().getJuridica().getStrRazonSocial());
		estrucComp.setEstructura(estrucBusq);
		estrucComp.setEstructuraDetalle(new EstructuraDetalle());
		//estrucComp.getEstructuraDetalle().setIntSeguSucursalPk(SESION_IDSUCURSAL);
		//estrucComp.getEstructuraDetalle().setIntSeguSubSucursalPk(SESION_IDSUBSUCURSAL);
		estrucComp.getEstructuraDetalle().setIntSeguSucursalPk(intIdPerfil.equals(11)?null:SESION_IDSUCURSAL);
		estrucComp.getEstructuraDetalle().setIntSeguSubSucursalPk(intIdPerfil.equals(11)?null:SESION_IDSUBSUCURSAL);
		
		//FILTRA SOLO LAS ESTRUCTURAS QUE TENGAN CONVENIOS 05.05.2014
//		List<EstructuraDetalle> listaEstructuraDetalleAdministraTemp = new ArrayList<EstructuraDetalle>();
		List<EstructuraDetalle> listaEstructuraDetallePlanillaTemp = new ArrayList<EstructuraDetalle>();
		List<ConvenioEstructuraDetalleComp> lista = new ArrayList<ConvenioEstructuraDetalleComp>();
		try {
			EstructuraFacadeLocal facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			this.listaEstructuraDetalle.clear();
//			listaEstructuraDetalleAdministraTemp = facade.getConveEstrucDetAdministra(estrucComp);
			listaEstructuraDetallePlanillaTemp = facade.getConveEstrucDetPlanilla(estrucComp);
			Integer intCodigoAnt = 0;
			Integer cont = 0;
			if (listaEstructuraDetallePlanillaTemp!=null && !listaEstructuraDetallePlanillaTemp.isEmpty()) {
				for (EstructuraDetalle o : listaEstructuraDetallePlanillaTemp) {
					if (cont>0) {
						if (!(o.getId().getIntCodigo().equals(intCodigoAnt))) {
							EstructuraDetalle x = new EstructuraDetalle();
							x.setId(new EstructuraDetalleId());
							x.getId().setIntCodigo(o.getId().getIntCodigo());
							x.getId().setIntCaso(1);//CASO PLANILLA
							x.getId().setIntNivel(o.getId().getIntNivel());
							lista = facade.getListaConvenioEstructuraDetallePorEstructuraDetCompleto(x.getId());
							if (lista.size()>0) {
								for (ConvenioEstructuraDetalleComp lst : lista) {
									if (lst.getEstructuraDetalle().getId().getIntItemCaso().equals(o.getId().getIntItemCaso())) {
										listaEstructuraDetalle.add(o);
										break;
									}
								}								
							}
							intCodigoAnt = o.getId().getIntCodigo();
						}
					}else {
						EstructuraDetalle x = new EstructuraDetalle();
						x.setId(new EstructuraDetalleId());
						x.getId().setIntCodigo(o.getId().getIntCodigo());
						x.getId().setIntCaso(1);//CASO PLANILLA
						x.getId().setIntNivel(o.getId().getIntNivel());
						
						lista = facade.getListaConvenioEstructuraDetallePorEstructuraDetCompleto(x.getId());
						if (lista.size()>0) {
							for (ConvenioEstructuraDetalleComp lst : lista) {
								if (lst.getEstructuraDetalle().getId().getIntItemCaso().equals(o.getId().getIntItemCaso())) {
									listaEstructuraDetalle.add(o);
									break;
								}
							}								
						}
						intCodigoAnt = o.getId().getIntCodigo();
					}
				}
			}
//			this.listaEstructuraDetalle = facade.getConveEstrucDetAdministra(estrucComp);
			
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void buscarSocioEstructura(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.buscarSocioEstructura-------------------------------------");
		EstructuraComp estrucComp = new EstructuraComp();
		
		listarSocioEstructura(estrucComp);
		
		if(listaEstructuraDetalle!=null){
			log.info("listaEstructuraDetalle.size: "+listaEstructuraDetalle.size());
		}
	}
	
	public void seleccionarEntidad(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.seleccionarEntidad-------------------------------------");
		log.info("strCallingFormId: "+strCallingFormId);
		log.info("strFormIdSocioNatu: "+strFormIdSocioNatu);
		
		if(getEntidadSeleccionada()==null  || getEntidadSeleccionada().getId()==null
				|| getEntidadSeleccionada().getId().getIntCodigo()==null){
			MessageController message = (MessageController)getSessionBean("messageController");
			message.setWarningMessage("Debe seleccionar una Entidad. Haga click en la fila que desea seleccionar.");
			return;
		}
		
		if(strCallingFormId.equals(strFormIdSocioNatu)){
			socioController = (SocioController)getSessionBean("socioController");
			SocioEstructura socioEstruc = socioController.getSocioEstructura();
			
			EstructuraDetalle ed = getEntidadSeleccionada();
			log.info("ed.intSeguSucursalPk: "+ed.getIntSeguSucursalPk());
			log.info("ed.intParaModalidadCod: "+ed.getIntParaModalidadCod());
			log.info("ed.id.intCodigo: "+ed.getId().getIntCodigo());
			log.info("ed.getIntSeguSucursalPk(): "+ed.getIntSeguSucursalPk());
			log.info("ed.getIntSeguSubSucursalPk(): "+ed.getIntSeguSubSucursalPk());
			
			socioEstruc.setIntEmpresaSucUsuario(SESION_IDEMPRESA);
			socioEstruc.setIntIdSucursalUsuario(SESION_IDSUCURSAL);
			socioEstruc.setIntIdSubSucursalUsuario(SESION_IDSUBSUCURSAL);
			socioEstruc.setIntEmpresaSucAdministra(ed.getIntPersEmpresaPk());
			socioEstruc.setIntIdSucursalAdministra(ed.getIntSeguSucursalPk());
			socioEstruc.setIntIdSubsucurAdministra(ed.getIntSeguSubSucursalPk());
			socioEstruc.setIntTipoSocio(ed.getIntParaTipoSocioCod());
			socioEstruc.setIntModalidad(ed.getIntParaModalidadCod());
			socioEstruc.setIntNivel(ed.getId().getIntNivel());
			socioEstruc.setIntCodigo(ed.getId().getIntCodigo());
			socioEstruc.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			socioEstruc.setIntEmpresaUsuario(SESION_IDEMPRESA);
			socioEstruc.setIntPersonaUsuario(SESION_IDUSUARIO);
		}
		
	}
	
	public void setSelectedEntidad(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.setSelectedEntidad-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowEntidad"));
		String selectedRow = getRequestParameter("rowEntidad");
		EstructuraDetalle ed = null;
		for(int i=0; i<listaEstructuraDetalle.size(); i++){
			ed = listaEstructuraDetalle.get(i);
			if(i == Integer.parseInt(selectedRow)){
				setEntidadSeleccionada(ed);
				break;
			}
		}
		log.info("ed.id.intCodigo: "+ed.getId().getIntCodigo());
	}
	
	public void addEntidadSocio(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.addEntidadSocio-------------------------------------");
		initSocioEstructura();
		log.info("strFormIdSocioNatu: "+strFormIdSocioNatu);
		setStrCallingFormId(strFormIdSocioNatu);
	}
	
	public void limpiarArrayEntidad(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.limpiarArrayEntidad-------------------------------------");
	}
	
	//Metodos Utilitarios
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		return sesion.getAttribute(beanName);
	}

	//Getters & Setters
	public Estructura getEstrucBusq() {
		return estrucBusq;
	}
	public void setEstrucBusq(Estructura estrucBusq) {
		this.estrucBusq = estrucBusq;
	}
	public List<EstructuraDetalle> getListaEstructuraDetalle() {
		return listaEstructuraDetalle;
	}
	public void setListaEstructuraDetalle(
			List<EstructuraDetalle> listaEstructuraDetalle) {
		this.listaEstructuraDetalle = listaEstructuraDetalle;
	}
	public List<Estructura> getListEstructura() {
		log.info("-------------------------------------Debugging SocioEstructuraController.getListEstructura-------------------------------------");
		try {
			if(listEstructura == null){
				EstructuraFacadeLocal facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
				this.listEstructura = facade.getListaEstructuraPorNivelYCodigoRel(null,null);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(listEstructura!=null){
			log.info("beanListInstitucion.size: "+listEstructura.size());
		}
		return listEstructura;
	}
	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
	}
	public String getStrIdModalPanel() {
		return strIdModalPanel;
	}
	public void setStrIdModalPanel(String strIdModalPanel) {
		this.strIdModalPanel = strIdModalPanel;
	}
	public String getPgListSocioEstructura() {
		return pgListSocioEstructura;
	}
	public void setPgListSocioEstructura(String pgListSocioEstructura) {
		this.pgListSocioEstructura = pgListSocioEstructura;
	}
	public EstructuraDetalle getEntidadSeleccionada() {
		return entidadSeleccionada;
	}
	public void setEntidadSeleccionada(EstructuraDetalle entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}
	public String getStrCallingFormId() {
		return strCallingFormId;
	}
	public void setStrCallingFormId(String strCallingFormId) {
		this.strCallingFormId = strCallingFormId;
	}
	public String getStrFormIdSocioNatu() {
		return strFormIdSocioNatu;
	}
	public void setStrFormIdSocioNatu(String strFormIdSocioNatu) {
		this.strFormIdSocioNatu = strFormIdSocioNatu;
	}
	public List<Sucursal> getListSucursal() {
		log.info("-------------------------------------Debugging SocioEstructuraController.getListEstructura-------------------------------------");
		try {
			if(listSucursal == null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listSucursal = facade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(listSucursal!=null){
			log.info("listSucursal.size: "+listSucursal.size());
		}
		return listSucursal;
	}
	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
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
	public Integer getSESION_IDSUCURSAL() {
		return SESION_IDSUCURSAL;
	}
	public void setSESION_IDSUCURSAL(Integer sESION_IDSUCURSAL) {
		SESION_IDSUCURSAL = sESION_IDSUCURSAL;
	}
	public Integer getSESION_IDSUBSUCURSAL() {
		return SESION_IDSUBSUCURSAL;
	}
	public void setSESION_IDSUBSUCURSAL(Integer sESION_IDSUBSUCURSAL) {
		SESION_IDSUBSUCURSAL = sESION_IDSUBSUCURSAL;
	}
	
}