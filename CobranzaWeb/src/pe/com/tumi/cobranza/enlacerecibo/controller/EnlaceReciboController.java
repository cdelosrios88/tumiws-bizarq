package pe.com.tumi.cobranza.enlacerecibo.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.facade.GestionCobranzaFacadeLocal;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursalId;
import pe.com.tumi.seguridad.login.facade.LoginFacadeRemote;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;
import pe.com.tumi.tesoreria.ingreso.facade.IngresoFacadeRemote;

/************************************************************************/
/* Nombre de la clase: EnlaceReciboController                           */
/* Funcionalidad : Clase que me permite enlazar recibos.                */
/*                                                                      */
/* Ref. : 																*/
/* Autor : FRAMIREZ 														*/
/* Versión : V1 														*/
/* Fecha creación : 01/01/2013 											*/
/* **********************************************************************/

public class EnlaceReciboController extends GenericController {
	
	//Variables locales
	protected  static Logger 		      log = Logger.getLogger(EnlaceReciboController.class);
	private	  ReciboManualDetalle		  beanRecManDet;
	private   List<ReciboManualDetalle>  beanListRecManDet;
	private   List<ReciboManualDetalle>   beanSelListRecManDet;
	
	// Parámetros de busqueda Enlace Recibo
	private Integer intCboSucursal;
	private Integer intCboEstadoRecibo;
	private Integer intInNroRecibo;
	
	//Propiedades de lista de combos.
	
	private 	List<Sucursal> 			listJuridicaSucursal;
	private 	List<Subsucursal> 		listJuridicaSubsucursal;
	private 	List<UsuarioSubSucursal> 		listGestor;
	
	private    String descIngreso;
	
   // Datos Generales del Usuario
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBCURSAL;
	
	
	private boolean bolEnlaceRecibo;
	
	//Proopiedades para deshabilitar.
	private boolean btnGrabarDisabled;
	private boolean cboSucursalDisabled;
	private boolean cboSubSucursalDisabled;
	private boolean texNumeroReciboDisabled;
	
	//Propiedades para renderizar
	private boolean enlaceReciboRendered;
	private boolean btnValidarRendered;
	private boolean texGestorRedered;

	//Popup
	
	private List<Ingreso> listIngreso;
	
	
	//Auxiliar
	
	private String strFechIngreso; 
	
	//-------------------------------------------------------------------------------------------------------------------------------------------
	//Métodos de GestionCobranzaController para Mantenimientos
	//-------------------------------------------------------------------------------------------------------------------------------------------
	public EnlaceReciboController() {
//		limpiarFormCobranza();
//		setFormCobranzaEnabled(true);
//		setCobranzaRendered(false);
		inicio(null);
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("CobranzaController");
	}
	
	public void inicio(ActionEvent event){
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		Usuario usuario = null;
		Integer MENU_GESTION_COBRANZA = 172;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			SESION_IDUSUARIO = usuario.getIntPersPersonaPk();
			if(usuario != null){
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_GESTION_COBRANZA);
				id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
				
				 SESION_IDEMPRESA = usuario.getEmpresa().getIntIdEmpresa();
				 SESION_IDSUCURSAL = usuario.getSucursal().getId().getIntIdSucursal();
				 SESION_IDSUBCURSAL = usuario.getSubSucursal().getId().getIntIdSubSucursal();
			}else{
				 bolEnlaceRecibo = false;
		    }
		} catch (Exception e) {
			log.error(e);
		}
		
		inicializarFormEnlaceRecibo();
		beanListRecManDet = null;
		beanSelListRecManDet = null;
		btnGrabarDisabled = true;
		enlaceReciboRendered = false;
	}
	
	//Acciones
	public void buscar(ActionEvent envent){
	try {
			IngresoFacadeRemote ingresoFacade = (IngresoFacadeRemote)EJBFactory.getRemote(IngresoFacadeRemote.class);
			
			Integer idSucursal     = getIntCboSucursal();
			Integer idEstadoCierre = getIntCboEstadoRecibo();
			Integer nroRecibo      = getIntInNroRecibo();
			beanListRecManDet      = ingresoFacade.buscarRecibosEnlazados(SESION_IDEMPRESA, idSucursal, 0, idEstadoCierre, nroRecibo);
			log.info("beanListRecManDet.size: "+beanListRecManDet.size());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	public void seleccionar(ActionEvent event){
		
		ReciboManualDetalle  recManDet =  (ReciboManualDetalle)event.getComponent().getAttributes().get("item");
		setBeanRecManDet(recManDet);
		
		if (beanSelListRecManDet != null)  beanSelListRecManDet.clear();
	 	    
		    beanSelListRecManDet = new ArrayList<ReciboManualDetalle>();
		    beanSelListRecManDet.add(recManDet);
		
	}
	public void nuevo(ActionEvent event){
		inicializarFormEnlaceRecibo();
		enlaceReciboRendered = true;
		btnValidarRendered = true;
		cboSucursalDisabled = false;
		cboSubSucursalDisabled = false;
		texNumeroReciboDisabled = false;
		texGestorRedered = false;
		
	}
	public void grabar(ActionEvent event){
		IngresoFacadeRemote ingresFacade = null;
		ReciboManualDetalle reciboManDet = null;
		boolean esValido = true;
		
		
		
	
	   //Grabar	
		try{
			
			 
			if (getBeanRecManDet().getId().getIntItemReciboManualDetalle() == null &&
				getBeanRecManDet().getId().getIntPersEmpresa() == null){
				//Validar Datos
				esValido = validarFormEnlaceRecibo();
				if (!esValido){
					return;
				}
				
				ingresFacade = (IngresoFacadeRemote)EJBFactory.getRemote(IngresoFacadeRemote.class);
			    getBeanRecManDet().getId().setIntPersEmpresa(SESION_IDEMPRESA);
				getBeanRecManDet().setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				
				//Graba
				reciboManDet = ingresFacade.grabarReciboManualDetalle(getBeanRecManDet());
			}else{
				ingresFacade = (IngresoFacadeRemote)EJBFactory.getRemote(IngresoFacadeRemote.class);
				getBeanRecManDet().setIntPersPersonaAnula(SESION_IDEMPRESA);
				getBeanRecManDet().setIntPersEmpresaAnula(SESION_IDUSUARIO);
				getBeanRecManDet().setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				java.util.Date dtFechaAnula = new java.util.Date();
				getBeanRecManDet().setDtFechaAnula(dtFechaAnula);
				reciboManDet = ingresFacade.modificarReciboManualDetalle(getBeanRecManDet());
			}
			
			
			
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
			log.error(e);
		} catch (EJBFactoryException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
		    e.printStackTrace();
		    log.error(e);
		}
		
		if (reciboManDet.getDtFechaAnula() != null){
			FacesContextUtil.setMessageSuccess("Desenlace:"+FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
		}
		else
		if (reciboManDet != null){
			FacesContextUtil.setMessageSuccess("Enalce:"+FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
		}
		
		enlaceReciboRendered = false;
		beanSelListRecManDet = null;
		btnGrabarDisabled = true;
		buscar(event);
	}
	public void cancelar(ActionEvent event){
		enlaceReciboRendered = false;
		beanSelListRecManDet = null;
		btnGrabarDisabled = true;
	}
	
	public void desenlazar(ActionEvent event){
		
		if (getBeanRecManDet().getReciboManual().getIntParaEstadoCierre().equals(Constante.PARAM_T_TIPO_ESTADO_RECIBO_CERRADO)){
		    	beanSelListRecManDet = null;
				return;
		}
		
		enlaceReciboRendered = false;
		btnGrabarDisabled = false;
	}
	
	public void listarSubSucursalPorSuc(ValueChangeEvent event){
		log.info("-------------------------------------Debugging enlaceReciboController.listarSubSucursal-------------------------------------");
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
			log.error(e);
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		setListJuridicaSubsucursal(listaSubsucursal);
	
	}
	
	public boolean validarFormEnlaceRecibo(){
	 boolean esValido = true;	
	  
	    if (getBeanRecManDet().getIngreso() == null || getBeanRecManDet().getIngreso().getId().getIntIdIngresoGeneral() == null){
	    	FacesContextUtil.setMessageError("Debe completar el campo Ingreso.");
	    	return false;
	    }
	 
	   return esValido;
	}
	
	public void validar(ActionEvent event){
		
		String vResult;
		Integer intItemReciboManual;
		
		
		
		try{
			IngresoFacadeRemote ingreso = (IngresoFacadeRemote)EJBFactory.getRemote(IngresoFacadeRemote.class);
			
			Integer idSucursal    = getBeanRecManDet().getReciboManual().getIntSucuIdSucursal();
			Integer idSubSucursal = getBeanRecManDet().getReciboManual().getIntSudeIdSubsucursal();
			Integer nroRecibo = getBeanRecManDet().getIntNumeroRecibo();
			
			log.info("idEmpresa: "+SESION_IDEMPRESA);
			log.info("idSucursal: "+idSucursal);
			log.info("idSubSucursal: "+idSubSucursal);
			log.info("nroRecibo: "+nroRecibo);
			
			
			intItemReciboManual =	ingreso.validarNroReciboPorSuc(SESION_IDEMPRESA,idSucursal, idSubSucursal, nroRecibo);
			log.info("esValido?: "+intItemReciboManual);
			
		  	if (intItemReciboManual.equals(0)) {
				FacesContextUtil.setMessageError("El Nro de Recibo ingresado es Invalido.");
		  		return;
		  	}
		  	
            vResult =	ingreso.existeNroReciboEnlazado(SESION_IDEMPRESA,idSucursal, idSubSucursal, nroRecibo);
            log.info("existe?: "+vResult);
		  	if (vResult.equals("S")) {
				FacesContextUtil.setMessageError("El Nro de Recibo se encuentra  enlazado.");
		  		return;
		  	}
			
		  	
		  	LoginFacadeRemote loginFacade = (LoginFacadeRemote)EJBFactory.getRemote(LoginFacadeRemote.class);
			
		  	UsuarioSubSucursalId id = new UsuarioSubSucursalId();
		  	id.setIntPersEmpresaPk(SESION_IDEMPRESA);
		  	id.setIntIdSucursal(idSucursal);
		  	id.setIntIdSubSucursal(idSubSucursal);
		  	
		  	listGestor = loginFacade.getListaPorSucYSubSucursal(id);
		  	getBeanRecManDet().getId().setIntItemReciboManual(intItemReciboManual);
		  	getBeanRecManDet().setIntNumeroRecibo(nroRecibo);
		
	    } catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
			log.error(e);
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		btnValidarRendered = false;
		cboSucursalDisabled     = true;
		cboSubSucursalDisabled  =  true;
		texNumeroReciboDisabled = true;
		texGestorRedered = true;
		btnGrabarDisabled = false;
	}
	
	public void buscarIngresos(ActionEvent event){
		log.info("---------------------------------Debugging enlaceReciboController.buscarIngresos-----------------------------");
		try{
	   	     IngresoFacadeRemote ingresoFacade = (IngresoFacadeRemote)EJBFactory.getRemote(IngresoFacadeRemote.class);
	   	     IngresoId id = new IngresoId();
	   	               id.setIntIdEmpresa(SESION_IDEMPRESA);
	   	     listIngreso = ingresoFacade.getListaIngNoEnlazados(id);
		
		}catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
			log.error(e);
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void seleccionarIngreso(ValueChangeEvent event){
		log.info("---------------------------------Debugging enlaceReciboController.seleccionarIngreso-----------------------------");
		
		String nroIngreso = (String) event.getNewValue();
		log.info("nroIngreso: "+nroIngreso);
		Ingreso ingreso = (Ingreso)listIngreso.get(Integer.valueOf(nroIngreso));
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //El formato en el que obtenemos la fecha inicialmente
        strFechIngreso = formatter.format(ingreso.getDtFechaIngreso());
		getBeanRecManDet().setIngreso(ingreso);
		getBeanRecManDet().setIntItemIngresoGeneral(ingreso.getId().getIntIdIngresoGeneral());
		getBeanRecManDet().setIntPersEmpresaIngreso(ingreso.getId().getIntIdEmpresa());
	}
	
	public void inicializarFormEnlaceRecibo(){
		log.info("-----------------------Debugging enlaceReciboController.inicializarFormEnlaceRecibo-----------------------------");
		
		 setBeanRecManDet(new ReciboManualDetalle());
		 getBeanRecManDet().setReciboManual(new ReciboManual());
		 strFechIngreso ="";
	}
	
	//beans
	public ReciboManualDetalle getBeanRecManDet() {
		return beanRecManDet;
	}

	public void setBeanRecManDet(ReciboManualDetalle beanRecManDet) {
		this.beanRecManDet = beanRecManDet;
	}

	public List<ReciboManualDetalle> getBeanListRecManDet() {
		return beanListRecManDet;
	}

	public void setBeanListRecManDet(List<ReciboManualDetalle> beanListRecManDet) {
		this.beanListRecManDet = beanListRecManDet;
	}

	public Integer getIntCboSucursal() {
		return intCboSucursal;
	}

	public void setIntCboSucursal(Integer intCboSucursal) {
		this.intCboSucursal = intCboSucursal;
	}

	public Integer getIntCboEstadoRecibo() {
		return intCboEstadoRecibo;
	}

	public void setIntCboEstadoRecibo(Integer intCboEstadoRecibo) {
		this.intCboEstadoRecibo = intCboEstadoRecibo;
	}

	public Integer getIntInNroRecibo() {
		return intInNroRecibo;
	}

	public void setIntInNroRecibo(Integer intInNroRecibo) {
		this.intInNroRecibo = intInNroRecibo;
	}

		public boolean isBolEnlaceRecibo() {
		return bolEnlaceRecibo;
	}

	public void setBolEnlaceRecibo(boolean bolEnlaceRecibo) {
		this.bolEnlaceRecibo = bolEnlaceRecibo;
	}

	public boolean isBtnGrabarDisabled() {
		return btnGrabarDisabled;
	}

	public void setBtnGrabarDisabled(boolean btnGrabarDisabled) {
		this.btnGrabarDisabled = btnGrabarDisabled;
	}

	public List<Sucursal> getListJuridicaSucursal() {
		
		log.info("-------------------------------------Debugging enlaceReciboController.getListJuridicaSucursal-------------------------------------");
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
		
		return listJuridicaSucursal;
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

	public boolean isEnlaceReciboRendered() {
		return enlaceReciboRendered;
	}

	public void setEnlaceReciboRendered(boolean enlaceReciboRendered) {
		this.enlaceReciboRendered = enlaceReciboRendered;
	}

	public boolean isBtnValidarRendered() {
		return btnValidarRendered;
	}

	public void setBtnValidarRendered(boolean btnValidarRendered) {
		this.btnValidarRendered = btnValidarRendered;
	}

	public boolean isCboSucursalDisabled() {
		return cboSucursalDisabled;
	}

	public void setCboSucursalDisabled(boolean cboSucursalDisabled) {
		this.cboSucursalDisabled = cboSucursalDisabled;
	}

	public boolean isCboSubSucursalDisabled() {
		return cboSubSucursalDisabled;
	}

	public void setCboSubSucursalDisabled(boolean cboSubSucursalDisabled) {
		this.cboSubSucursalDisabled = cboSubSucursalDisabled;
	}

	public boolean isTexNumeroReciboDisabled() {
		return texNumeroReciboDisabled;
	}

	public void setTexNumeroReciboDisabled(boolean texNumeroReciboDisabled) {
		this.texNumeroReciboDisabled = texNumeroReciboDisabled;
	}

	public List<UsuarioSubSucursal> getListGestor() {
		return listGestor;
	}

	public void setListGestor(List<UsuarioSubSucursal> listGestor) {
		this.listGestor = listGestor;
	}

	public boolean isTexGestorRedered() {
		return texGestorRedered;
	}

	public void setTexGestorRedered(boolean texGestorRedered) {
		this.texGestorRedered = texGestorRedered;
	}

	public String getDescIngreso() {
		return descIngreso;
	}

	public void setDescIngreso(String descIngreso) {
		this.descIngreso = descIngreso;
	}

	public List<Ingreso> getListIngreso() {
		return listIngreso;
	}

	public void setListIngreso(List<Ingreso> listIngreso) {
		this.listIngreso = listIngreso;
	}

	public String getStrFechIngreso() {
		return strFechIngreso;
	}

	public void setStrFechIngreso(String strFechIngreso) {
		this.strFechIngreso = strFechIngreso;
	}

	public List<ReciboManualDetalle> getBeanSelListRecManDet() {
		return beanSelListRecManDet;
	}

	public void setBeanSelListRecManDet(
			List<ReciboManualDetalle> beanSelListRecManDet) {
		this.beanSelListRecManDet = beanSelListRecManDet;
	}
	
	
	
	
	
	
	
	
	
}