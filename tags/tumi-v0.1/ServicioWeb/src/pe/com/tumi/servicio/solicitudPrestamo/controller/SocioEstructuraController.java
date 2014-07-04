package pe.com.tumi.servicio.solicitudPrestamo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.refinanciamiento.controller.SolicitudRefinanController;
import pe.com.tumi.servicio.solicitudEspecial.controller.SolicitudEspecialController;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CapacidadCreditoComp;

/************************************************************************/
/* Nombre de la clase: HojaPlaneamientoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* Ref. : */
/* Autor : Christian De los Ríos */
/* Versión : 0.1 */
/* Fecha creación : 17/08/2012 */
/* ********************************************************************* */

public class SocioEstructuraController {
	
	protected   static Logger 				log = Logger.getLogger(SocioEstructuraController.class);
	private 	Estructura 					estrucBusq;
	private 	List<EstructuraDetalle> 	listaEstructuraDetalle;
	private 	List<Estructura>			listEstructura;
	private 	List<Sucursal>				listSucursal;
	private 	EstructuraDetalle 			entidadSeleccionada;
	private 	SolicitudPrestamoController solicitudPrestamoController = null;
	private 	SolicitudRefinanController  solicitudRefinanController = null;
	private 	SolicitudEspecialController	solicitudEspecialCOntroller = null;
	private 	String 						strCallingFormId;
	private 	String 						strFormIdSocioNatu;
	private 	String 						strIdModalPanel = null;
	private 	String 						pgListSocioEstructura = null;
	//Mensajes de Error
	private 	String						msgTxtTipoSocio;
	private 	String						msgTxtModalidad;
	//atributos de sesión
	private 	Integer 					SESION_IDEMPRESA;
	private 	Integer 					SESION_IDUSUARIO;
	private 	Integer 					SESION_IDSUCURSAL;
	private 	Integer 					SESION_IDSUBSUCURSAL;
	
	//------------------------------------------------------------------------------------------------------------
	//Mantenimiento de Estructura Organica
	//------------------------------------------------------------------------------------------------------------
	public SocioEstructuraController(){
		log.info("-------------------------------------Debugging Constructor de SocioEstructuraController-------------------------------------");
		initSocioEstructura();
		initSocioEstructuraRef();
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
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
	}
	
	public void initSocioEstructuraRef(){
		log.info("-------------------------------------Debugging SocioEstructuraController.initSocioEstructura-------------------------------------");
		listaEstructuraDetalle = new ArrayList<EstructuraDetalle>();
		
		strIdModalPanel = "mpSocioEstructuraRef";
		pgListSocioEstructura = "pgSocioEstructuraRef,tblEntidadRef";
		
		estrucBusq = new Estructura();
		estrucBusq.setJuridica(new Juridica());
		
		strFormIdSocioNatu = "frmSocioNatural";
		
		entidadSeleccionada = null;

		//atributos de sesión
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
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
		
		log.info("socioEstrucBusq.strEntidad: "+getEstrucBusq().getJuridica().getStrRazonSocial());
		estrucComp.setEstructura(estrucBusq);
		estrucComp.setEstructuraDetalle(new EstructuraDetalle());
		estrucComp.getEstructuraDetalle().setIntSeguSucursalPk(SESION_IDSUCURSAL);
		estrucComp.getEstructuraDetalle().setIntSeguSubSucursalPk(SESION_IDSUBSUCURSAL);
		
		try {
			EstructuraFacadeRemote facade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			this.listaEstructuraDetalle.clear();
			this.listaEstructuraDetalle = facade.getConveEstrucDetAdministra(estrucComp);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	/*public void listarSocioEstructura(EstructuraComp estrucComp){
		log.info("-------------------------------------Debugging SocioEstructuraController.listarSocioEstructura-------------------------------------");
		if(estrucComp==null){
			estrucComp = new EstructuraComp();
			estrucComp.setEstructura(new Estructura());
			estrucComp.getEstructura().setJuridica(new Juridica());
		}
		
		try {
			EstructuraFacadeRemote facade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			this.listaEstructuraDetalle.clear();
			this.listaEstructuraDetalle = facade.getConveEstrucDetAdministra(estrucComp);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}*/
	
	public void buscarSocioEstructura(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.buscarSocioEstructura-------------------------------------");
		log.info("socioEstrucBusq.strEntidad: "+getEstrucBusq().getJuridica().getStrRazonSocial());
		EstructuraComp estrucComp = new EstructuraComp();
		estrucComp.setEstructura(new Estructura());
		estrucComp.getEstructura().setJuridica(new Juridica());
		
		listarSocioEstructura(estrucComp);
		
		if(listaEstructuraDetalle!=null){
			log.info("listaEstructuraDetalle.size: "+listaEstructuraDetalle.size());
		}
	}
	
	
	/* private Boolean isValidoEstructuraEntidad(SocioEstructura socioEstruc){
		boolean validSocioEstructura = true;
		if(solicitudPrestamoController.getListaCapacidadCreditoComp()!=null 
				&& solicitudPrestamoController.getListaCapacidadCreditoComp().size()>0){
			log.info("socioEstruc.getIntNivel(): " + socioEstruc.getIntNivel());
			log.info("socioEstruc.getIntCodigo(): " + socioEstruc.getIntCodigo());
			
			
			
			for(CapacidadCreditoComp capacidadCreditoComp : solicitudPrestamoController.getListaCapacidadCreditoComp()){
				log.info("capacidadCreditoComp.getSocioEstructura().getIntNivel(): " + capacidadCreditoComp.getSocioEstructura().getIntNivel());
				log.info("capacidadCreditoComp.getSocioEstructura().getIntCodigo(): " + capacidadCreditoComp.getSocioEstructura().getIntCodigo());
				
				System.out.println("01-->"+socioEstruc.getIntNivel().equals(capacidadCreditoComp.getSocioEstructura().getIntNivel()));
				System.out.println("02-->"+!socioEstruc.getIntCodigo().equals(capacidadCreditoComp.getSocioEstructura().getIntCodigo()));
				System.out.println("03-->"+socioEstruc.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO));

				if(socioEstruc.getIntNivel().equals(capacidadCreditoComp.getSocioEstructura().getIntNivel())
					&&	!socioEstruc.getIntCodigo().equals(capacidadCreditoComp.getSocioEstructura().getIntCodigo())
						&&	socioEstruc.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO)){
					
					if(capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
						|| capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
						
						if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
							solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("La Estructura seleccionada no puede ser de tipo CAS.");
							validSocioEstructura = false;
							break;
						}else{
							solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("");
						}
					}
					
					if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
						if(capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
							|| capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
							solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("No puede ingresar un tipo CAS, ya existen modalidades distintas.");
							validSocioEstructura = false;
							break;
						}else{
							solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("");
						}
					}
					
				}else if(socioEstruc.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_CESANTE)){
					if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
						solicitudPrestamoController.setMsgTxtEstrucCesanteRepetido("El tipo de Socio 'CESANTE' no puede tener un tipo de Modalidad CAS.");
						validSocioEstructura = false;
						break;
					}else{
						solicitudPrestamoController.setMsgTxtEstrucCesanteRepetido("");
					}
				}
				
				//--------------------------->
				if(capacidadCreditoComp.getSocioEstructura().getIntNivel().equals(socioEstruc.getIntNivel())
					&& capacidadCreditoComp.getSocioEstructura().getIntCodigo().equals(socioEstruc.getIntCodigo())
						&& capacidadCreditoComp.getSocioEstructura().getIntTipoSocio().equals(socioEstruc.getIntTipoSocio())
							&& capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(socioEstruc.getIntModalidad())){
					solicitudPrestamoController.setMsgTxtEstructuraRepetida("Ya existe una estructura del mismo tipo");
					validSocioEstructura = false;
					break;
				}else{
					solicitudPrestamoController.setMsgTxtEstructuraRepetida("");
				}
			}
		}
		return validSocioEstructura;
	}*/
	
	/**
	 * Valida el registro de las U. ejecutoras dependiendo del TipoSocio y Modalidad
	 */
	private Boolean isValidoEstructuraEntidad(SocioEstructura socioEstruc){
		boolean validSocioEstructura = true;
		if(solicitudPrestamoController.getListaCapacidadCreditoComp()!=null 
			&& !solicitudPrestamoController.getListaCapacidadCreditoComp().isEmpty()){
			
			log.info("socioEstruc.getIntNivel(): " + socioEstruc.getIntNivel());
			log.info("socioEstruc.getIntCodigo(): " + socioEstruc.getIntCodigo());
			
			for(CapacidadCreditoComp capacidadCreditoComp : solicitudPrestamoController.getListaCapacidadCreditoComp()){
				log.info("capacidadCreditoComp.getSocioEstructura().getIntNivel(): " + capacidadCreditoComp.getSocioEstructura().getIntNivel());
				log.info("capacidadCreditoComp.getSocioEstructura().getIntCodigo(): " + capacidadCreditoComp.getSocioEstructura().getIntCodigo());
				
				System.out.println("01-->"+socioEstruc.getIntNivel().equals(capacidadCreditoComp.getSocioEstructura().getIntNivel()));
				System.out.println("02-->"+!socioEstruc.getIntCodigo().equals(capacidadCreditoComp.getSocioEstructura().getIntCodigo()));
				System.out.println("03-->"+socioEstruc.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO));
				
				// Es activo
				if(socioEstruc.getIntNivel().equals(capacidadCreditoComp.getSocioEstructura().getIntNivel())
					&&	!socioEstruc.getIntCodigo().equals(capacidadCreditoComp.getSocioEstructura().getIntCodigo())
					&&	socioEstruc.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO)){
					
					//si alguno modaliodad (hab o inc) ya existe
					if(capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
						|| capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
						
						// si modalidad es cas
						if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
							solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("La Estructura seleccionada no puede ser de tipo CAS.");
							validSocioEstructura = false;
							break;
						}else{
							solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("");
						}
					}
					
					// si es cas
					if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
						// si alguno modaliodad (hab o inc) ya existe
						if(capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
							|| capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
							solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("No puede ingresar un tipo CAS, ya existen modalidades distintas.");
							validSocioEstructura = false;
							break;
						}else{
							solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("");
						}
					}
				
				// Si no es activo - cesante
				}else if(socioEstruc.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_CESANTE)){
					// si la modalidad es cas
					if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
						solicitudPrestamoController.setMsgTxtEstrucCesanteRepetido("El tipo de Socio 'CESANTE' no puede tener un tipo de Modalidad CAS.");
						validSocioEstructura = false;
						break;
					}else{
						solicitudPrestamoController.setMsgTxtEstrucCesanteRepetido("");
					}
				}
				
				
				//-------------------------------------------------------->
				
				// si ya existe una capacidad con tipo y modalidad igual
				if(capacidadCreditoComp.getSocioEstructura().getIntNivel().equals(socioEstruc.getIntNivel())
					&& capacidadCreditoComp.getSocioEstructura().getIntCodigo().equals(socioEstruc.getIntCodigo())
						&& capacidadCreditoComp.getSocioEstructura().getIntTipoSocio().equals(socioEstruc.getIntTipoSocio())
							&& capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(socioEstruc.getIntModalidad())){
					solicitudPrestamoController.setMsgTxtEstructuraRepetida("Ya existe una estructura del mismo tipo");
					validSocioEstructura = false;
					break;
				}else{
					solicitudPrestamoController.setMsgTxtEstructuraRepetida("");
				}
			}
		}
		return validSocioEstructura;
	}
	
	
	/**
	 * Valida el registro de las U. ejecutoras dependiendo del TipoSocio y Modalidad:
	 * 
	 * Si la de Origen es:
	 *	A. Haberes-Cesante:  debe permitir agregar solo Haberes-Cesante pero no el mismo.
	 *	B. CAS - Activo: debe permitir agregar solo  Haberes-Cesante pero no el mismo.
	 *	C. Haberes – Activo debe permitir agregar solo Incentivos-Activo y   Haberes-Cesante.
	 *	D. Incentivos-Activo debe permitir agregar solo Haberes – Activo.
	 *
	 * @param socioEstruc
	 * @return
	 */
	private Boolean isValidoEstructuraEntidadSolicitudPrestamo(SocioEstructura socioEstruc){
		boolean validSocioEstructura = false;
		Integer intTipoMod = 0;
			
		if(solicitudPrestamoController.getListaCapacidadCreditoComp() != null 
		&& !solicitudPrestamoController.getListaCapacidadCreditoComp().isEmpty()){

			//socioEstructura.getIntTipoEstructura().intValue() == Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN
			forCapacidades:
			for (CapacidadCreditoComp capacidad : solicitudPrestamoController.getListaCapacidadCreditoComp()) {
				
				if(capacidad.getSocioEstructura().getIntTipoEstructura().compareTo(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)==0){
					
					if(capacidad.getSocioEstructura().getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0){
						intTipoMod = 1;
					}else if(capacidad.getSocioEstructura().getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_CAS)==0){	
						intTipoMod = 2;
					}else if(capacidad.getSocioEstructura().getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)==0){
						intTipoMod = 3;	
					}

					switch (intTipoMod) {
						//HABERES
						case 1:
						// Activo
						if(capacidad.getSocioEstructura().getIntTipoSocio().compareTo(Constante.PARAM_T_TIPOSOCIO_ACTIVO)==0){
							//debe permitir   agregar solo Incentivos-Activo y   Haberes-Cesante.
							if((socioEstruc.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)==0 
							&& socioEstruc.getIntTipoSocio().compareTo(Constante.PARAM_T_TIPOSOCIO_ACTIVO)==0)
							|| socioEstruc.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0 
							&& socioEstruc.getIntTipoSocio().compareTo(Constante.PARAM_T_TIPOSOCIO_CESANTE)==0){
								validSocioEstructura = Boolean.TRUE;
								solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("");
							}else{
								solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("No se puede agregar la U. Ejecutora.");
								validSocioEstructura = Boolean.FALSE;
								break forCapacidades;
							}

						// cesante
						}else if(capacidad.getSocioEstructura().getIntTipoSocio().compareTo(Constante.PARAM_T_TIPOSOCIO_CESANTE)==0){
							//debe permitir  agregar solo  Haberes-Cesante
							if(socioEstruc.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0 
							&& socioEstruc.getIntTipoSocio().compareTo(Constante.PARAM_T_TIPOSOCIO_CESANTE)==0){
								//10.05.2013 - CGD
								validSocioEstructura = Boolean.TRUE;
								solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("");
								//validSocioEstructura = validarExistencia(solicitudPrestamoController.getListaCapacidadCreditoComp(), socioEstruc);
								
							}else{
								solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("No se puede agregar la U. Ejecutora. Solo se permite Haberes-Cesante.");
								validSocioEstructura = Boolean.FALSE;
								break forCapacidades;
							}	
						}
						break;
						//CAS
						case 2:
						if(capacidad.getSocioEstructura().getIntTipoSocio().compareTo(Constante.PARAM_T_TIPOSOCIO_ACTIVO)==0){
							//no debe permitirgenerar otro tipo
							if(socioEstruc.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_CAS)==0 
							&& socioEstruc.getIntTipoSocio().compareTo(Constante.PARAM_T_TIPOSOCIO_ACTIVO)==0){
								//10.05.2013 - CGD
								validSocioEstructura = Boolean.TRUE;
								solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("");
								//validSocioEstructura = validarExistencia(solicitudPrestamoController.getListaCapacidadCreditoComp(), socioEstruc);
							}else{
								solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("No se puede agregar la U. Ejecutora. Solo se permite CAS-Activo.");
								validSocioEstructura = Boolean.FALSE;
								break forCapacidades;
							}
						} 
						break;
						//INCENTIVOS
						case 3:
						if(capacidad.getSocioEstructura().getIntTipoSocio().compareTo(Constante.PARAM_T_TIPOSOCIO_ACTIVO)==0){
							//debe permitir agregar solo Haberes – Activo
							if(socioEstruc.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0 
							&& socioEstruc.getIntTipoSocio().compareTo(Constante.PARAM_T_TIPOSOCIO_ACTIVO)==0){
								validSocioEstructura = Boolean.TRUE;
								solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("");
							}else{
								solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("No se puede agregar la U. Ejecutora. Solo se permite Haberes – Activo.");
								validSocioEstructura = Boolean.FALSE;
								break forCapacidades;
							}
						}
						break;
					}
				
					
					
				}
			}
		}

		return validSocioEstructura;
	}
	

	/**
	 * Valida si ya existe el socio esctructura en la lista de estrucutras de la solicitud de prestamo.
	 * @param lstCapacidades
	 * @param socioEstruc
	 * @return
	 */
	public boolean validarExistencia(List<CapacidadCreditoComp> lstCapacidades ,SocioEstructura socioEstruc){
		Boolean blnAgregar = Boolean.TRUE;
		//Boolean blnYaExiste = Boolean.FALSE;
		
		try {
			solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("");
			
			if(lstCapacidades != null && !lstCapacidades.isEmpty()){
				for (CapacidadCreditoComp capacidadCreditoComp : lstCapacidades) {
					if(capacidadCreditoComp.getSocioEstructura().getIntCodigo().compareTo(socioEstruc.getIntCodigo())==0
						&& capacidadCreditoComp.getSocioEstructura().getIntEmpresaSucAdministra().compareTo(socioEstruc.getIntEmpresaSucAdministra())==0
						//&& capacidadCreditoComp.getSocioEstructura().getIntIdSucursalAdministra().compareTo(socioEstruc.getIntIdSucursalAdministra())==0
						//&& capacidadCreditoComp.getSocioEstructura().getIntIdSubsucurAdministra().compareTo(socioEstruc.getIntIdSubsucurAdministra())==0
						&& capacidadCreditoComp.getSocioEstructura().getIntModalidad().compareTo(socioEstruc.getIntModalidad())==0
						&& capacidadCreditoComp.getSocioEstructura().getIntNivel().compareTo(socioEstruc.getIntNivel())==0
						&& capacidadCreditoComp.getSocioEstructura().getIntPersonaUsuario().compareTo(socioEstruc.getIntPersonaUsuario())==0
						//&& capacidadCreditoComp.getSocioEstructura().getIntTipoEstructura().compareTo(socioEstruc.getIntTipoEstructura())==0
						&& capacidadCreditoComp.getSocioEstructura().getIntTipoSocio().compareTo(socioEstruc.getIntTipoSocio())==0){
							blnAgregar = Boolean.FALSE;
							solicitudPrestamoController.setMsgTxtEstrucActivoRepetido("La U. Ejecutora ya está registrada.");
							break;
					}
				}
			}
			
			
		} catch (Exception e) {
			log.error("Error en validarExistencia --->  "+e);
		}
		
		return blnAgregar;
	}
	

	public SolicitudRefinanController getSolicitudRefinanController() {
		return solicitudRefinanController;
	}

	public void setSolicitudRefinanController(
			SolicitudRefinanController solicitudRefinanController) {
		this.solicitudRefinanController = solicitudRefinanController;
	}

	private Boolean isValidoEstructuraEntidadRef(SocioEstructura socioEstruc){
		boolean validSocioEstructura = true;
		if(solicitudRefinanController.getListaCapacidadCreditoComp()!=null 
				&& solicitudRefinanController.getListaCapacidadCreditoComp().size()>0){
			log.info("socioEstruc.getIntNivel(): " + socioEstruc.getIntNivel());
			log.info("socioEstruc.getIntCodigo(): " + socioEstruc.getIntCodigo());
			for(CapacidadCreditoComp capacidadCreditoComp : solicitudRefinanController.getListaCapacidadCreditoComp()){
				log.info("capacidadCreditoComp.getSocioEstructura().getIntNivel(): " + capacidadCreditoComp.getSocioEstructura().getIntNivel());
				log.info("capacidadCreditoComp.getSocioEstructura().getIntCodigo(): " + capacidadCreditoComp.getSocioEstructura().getIntCodigo());
				if(socioEstruc.getIntNivel().equals(capacidadCreditoComp.getSocioEstructura().getIntNivel())
					&&	!socioEstruc.getIntCodigo().equals(capacidadCreditoComp.getSocioEstructura().getIntCodigo())
						&&	socioEstruc.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO)){
					if(capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
						|| capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
						if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
							solicitudRefinanController.setMsgTxtEstrucActivoRepetido("La Estructura seleccionada no puede ser de tipo CAS.");
							validSocioEstructura = false;
							break;
						}else{
							solicitudRefinanController.setMsgTxtEstrucActivoRepetido("");
						}
					}
					
					if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
						if(capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
							|| capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
							solicitudRefinanController.setMsgTxtEstrucActivoRepetido("No puede ingresar un tipo CAS, ya existen modalidades distintas.");
							validSocioEstructura = false;
							break;
						}else{
							solicitudRefinanController.setMsgTxtEstrucActivoRepetido("");
						}
					}
					
				}else if(socioEstruc.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_CESANTE)){
					if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
						solicitudRefinanController.setMsgTxtEstrucCesanteRepetido("El tipo de Socio 'CESANTE' no puede tener un tipo de Modalidad CAS.");
						validSocioEstructura = false;
						break;
					}else{
						solicitudRefinanController.setMsgTxtEstrucCesanteRepetido("");
					}
				}
				
				if(capacidadCreditoComp.getSocioEstructura().getIntNivel().equals(socioEstruc.getIntNivel())
					&& capacidadCreditoComp.getSocioEstructura().getIntCodigo().equals(socioEstruc.getIntCodigo())
						&& capacidadCreditoComp.getSocioEstructura().getIntTipoSocio().equals(socioEstruc.getIntTipoSocio())
							&& capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(socioEstruc.getIntModalidad())){
					solicitudRefinanController.setMsgTxtEstructuraRepetida("Ya existe una extructura del mismo tipo");
					validSocioEstructura = false;
					break;
				}else{
					solicitudRefinanController.setMsgTxtEstructuraRepetida("");
				}
			}
		}
		return validSocioEstructura;
	}
	
	
	private Boolean isValidoEstructuraEntidadEsp(SocioEstructura socioEstruc){
		boolean validSocioEstructura = true;
		if(solicitudEspecialCOntroller.getListaCapacidadCreditoComp()!=null 
				&& solicitudEspecialCOntroller.getListaCapacidadCreditoComp().size()>0){
			log.info("socioEstruc.getIntNivel(): " + socioEstruc.getIntNivel());
			log.info("socioEstruc.getIntCodigo(): " + socioEstruc.getIntCodigo());
			for(CapacidadCreditoComp capacidadCreditoComp : solicitudEspecialCOntroller.getListaCapacidadCreditoComp()){
				log.info("capacidadCreditoComp.getSocioEstructura().getIntNivel(): " + capacidadCreditoComp.getSocioEstructura().getIntNivel());
				log.info("capacidadCreditoComp.getSocioEstructura().getIntCodigo(): " + capacidadCreditoComp.getSocioEstructura().getIntCodigo());
				if(socioEstruc.getIntNivel().equals(capacidadCreditoComp.getSocioEstructura().getIntNivel())
					&&	!socioEstruc.getIntCodigo().equals(capacidadCreditoComp.getSocioEstructura().getIntCodigo())
						&&	socioEstruc.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO)){
					if(capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
						|| capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
						if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
							solicitudEspecialCOntroller.setMsgTxtEstrucActivoRepetido("La Estructura seleccionada no puede ser de tipo CAS.");
							validSocioEstructura = false;
							break;
						}else{
							solicitudEspecialCOntroller.setMsgTxtEstrucActivoRepetido("");
						}
					}
					
					if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
						if(capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
							|| capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
							solicitudEspecialCOntroller.setMsgTxtEstrucActivoRepetido("No puede ingresar un tipo CAS, ya existen modalidades distintas.");
							validSocioEstructura = false;
							break;
						}else{
							solicitudEspecialCOntroller.setMsgTxtEstrucActivoRepetido("");
						}
					}
					
				}else if(socioEstruc.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_CESANTE)){
					if(socioEstruc.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
						solicitudEspecialCOntroller.setMsgTxtEstrucCesanteRepetido("El tipo de Socio 'CESANTE' no puede tener un tipo de Modalidad CAS.");
						validSocioEstructura = false;
						break;
					}else{
						solicitudEspecialCOntroller.setMsgTxtEstrucCesanteRepetido("");
					}
				}
				
				if(capacidadCreditoComp.getSocioEstructura().getIntNivel().equals(socioEstruc.getIntNivel())
					&& capacidadCreditoComp.getSocioEstructura().getIntCodigo().equals(socioEstruc.getIntCodigo())
						&& capacidadCreditoComp.getSocioEstructura().getIntTipoSocio().equals(socioEstruc.getIntTipoSocio())
							&& capacidadCreditoComp.getSocioEstructura().getIntModalidad().equals(socioEstruc.getIntModalidad())){
					solicitudEspecialCOntroller.setMsgTxtEstructuraRepetida("Ya existe una extructura del mismo tipo");
					validSocioEstructura = false;
					break;
				}else{
					solicitudEspecialCOntroller.setMsgTxtEstructuraRepetida("");
				}
			}
		}
		return validSocioEstructura;
	}
	
	public void seleccionarEntidad(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.seleccionarEntidad-------------------------------------");
		log.info("strCallingFormId: "+strCallingFormId);
		log.info("strFormIdSocioNatu: "+strFormIdSocioNatu);
		Usuario usuario;
		SocioComp socioComp= null;
		try {
				usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			//if(strCallingFormId.equals(strFormIdSocioNatu)){
				solicitudPrestamoController = (SolicitudPrestamoController)getSessionBean("solicitudPrestamoController");
				//SocioEstructura socioEstruc = solicitudPrestamoController.getBeanSocioComp().getSocio().getSocioEstructura();
				socioComp = solicitudPrestamoController.getBeanSocioComp();
				SocioEstructura socioEstruc = new SocioEstructura();
				CapacidadCreditoComp capacidadCreditoComp = new CapacidadCreditoComp();
				EstructuraDetalle ed = getEntidadSeleccionada();
				
				socioEstruc.setIntEmpresaSucUsuario(usuario.getSucursal().getId().getIntPersEmpresaPk());
				socioEstruc.setIntIdSucursalUsuario(usuario.getSucursal().getId().getIntIdSucursal());
				socioEstruc.setIntIdSubSucursalUsuario(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				socioEstruc.setIntEmpresaSucAdministra(ed.getIntPersEmpresaPk());
				socioEstruc.setIntIdSucursalAdministra(ed.getIntSeguSucursalPk());
				socioEstruc.setIntIdSubsucurAdministra(ed.getIntSeguSubSucursalPk());
				socioEstruc.setIntTipoSocio(ed.getIntParaTipoSocioCod());
				socioEstruc.setIntModalidad(ed.getIntParaModalidadCod());
				socioEstruc.setIntNivel(ed.getId().getIntNivel());
				socioEstruc.setIntCodigo(ed.getId().getIntCodigo());
				socioEstruc.setIntTipoEstructura(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO);
				socioEstruc.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				socioEstruc.setIntEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa());
				socioEstruc.setIntPersonaUsuario(usuario.getIntPersPersonaPk());
				
				//if(isValidoEstructuraEntidad(socioEstruc) == false){
				if(isValidoEstructuraEntidadSolicitudPrestamo(socioEstruc) == false){
					log.info("Se aborta el proceso de grabado.");
			    	return;
			    }else{
			    	//Agregamos la nueva estructura a la lista actual.
			    	socioComp.getSocio().getListSocioEstructura().add(socioEstruc);
			    	capacidadCreditoComp.setSocioEstructura(socioEstruc);
					solicitudPrestamoController.getListaCapacidadCreditoComp().add(capacidadCreditoComp);
					//Agregamos al bean esxpediente el nuevo sociCOmp
					solicitudPrestamoController.getBeanExpedienteCredito().setSocioComp(socioComp);
					log.info("solicitudPrestamoController.getBeanSocioComp().getSocio().getListSocioEstructura(): " + solicitudPrestamoController.getBeanSocioComp().getSocio().getListSocioEstructura().size());
			    }
				
			//}
		} catch (Exception e) {
			log.error("Error en seleccionarEntidad ---> "+e);
			e.printStackTrace();
		}
	}
	
	public void seleccionarEntidadRef(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.seleccionarEntidad-------------------------------------");
		log.info("strCallingFormId: "+strCallingFormId);
		log.info("strFormIdSocioNatu: "+strFormIdSocioNatu);
		Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		//if(strCallingFormId.equals(strFormIdSocioNatu)){
			solicitudRefinanController = (SolicitudRefinanController)getSessionBean("solicitudRefinanController");
			//SocioEstructura socioEstruc = solicitudPrestamoController.getBeanSocioComp().getSocio().getSocioEstructura();
			SocioEstructura socioEstruc = new SocioEstructura();
			CapacidadCreditoComp capacidadCreditoComp = new CapacidadCreditoComp();
			EstructuraDetalle ed = getEntidadSeleccionada();
			
			socioEstruc.setIntEmpresaSucUsuario(usuario.getSucursal().getId().getIntPersEmpresaPk());
			socioEstruc.setIntIdSucursalUsuario(usuario.getSucursal().getId().getIntIdSucursal());
			socioEstruc.setIntIdSubSucursalUsuario(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			socioEstruc.setIntEmpresaSucAdministra(ed.getIntPersEmpresaPk());
			socioEstruc.setIntIdSucursalAdministra(ed.getIntSeguSucursalPk());
			socioEstruc.setIntIdSubsucurAdministra(ed.getIntSeguSubSucursalPk());
			socioEstruc.setIntTipoSocio(ed.getIntParaTipoSocioCod());
			socioEstruc.setIntModalidad(ed.getIntParaModalidadCod());
			socioEstruc.setIntNivel(ed.getId().getIntNivel());
			socioEstruc.setIntCodigo(ed.getId().getIntCodigo());
			socioEstruc.setIntTipoEstructura(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO);
			socioEstruc.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			socioEstruc.setIntEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa());
			socioEstruc.setIntPersonaUsuario(usuario.getIntPersPersonaPk());
			if(isValidoEstructuraEntidadRef(socioEstruc) == false){
				log.info("Se aborta el proceso de grabado.");
		    	return;
		    }
			capacidadCreditoComp.setSocioEstructura(socioEstruc);
			solicitudRefinanController.getListaCapacidadCreditoComp().add(capacidadCreditoComp);
			//log.info("solicitudPrestamoController.getBeanSocioComp().getSocio().getListSocioEstructura(): " + solicitudPrestamoController.getBeanSocioComp().getSocio().getListSocioEstructura().size());
		//}
	}
	
	
	public void seleccionarEntidadEspecial(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.seleccionarEntidad-------------------------------------");
		log.info("strCallingFormId: "+strCallingFormId);
		log.info("strFormIdSocioNatu: "+strFormIdSocioNatu);
		Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		//if(strCallingFormId.equals(strFormIdSocioNatu)){
			solicitudEspecialCOntroller = (SolicitudEspecialController)getSessionBean("solicitudEspecialController");
			//SocioEstructura socioEstruc = solicitudPrestamoController.getBeanSocioComp().getSocio().getSocioEstructura();
			SocioEstructura socioEstruc = new SocioEstructura();
			CapacidadCreditoComp capacidadCreditoComp = new CapacidadCreditoComp();
			EstructuraDetalle ed = getEntidadSeleccionada();
			
			socioEstruc.setIntEmpresaSucUsuario(usuario.getSucursal().getId().getIntPersEmpresaPk());
			socioEstruc.setIntIdSucursalUsuario(usuario.getSucursal().getId().getIntIdSucursal());
			socioEstruc.setIntIdSubSucursalUsuario(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			socioEstruc.setIntEmpresaSucAdministra(ed.getIntPersEmpresaPk());
			socioEstruc.setIntIdSucursalAdministra(ed.getIntSeguSucursalPk());
			socioEstruc.setIntIdSubsucurAdministra(ed.getIntSeguSubSucursalPk());
			socioEstruc.setIntTipoSocio(ed.getIntParaTipoSocioCod());
			socioEstruc.setIntModalidad(ed.getIntParaModalidadCod());
			socioEstruc.setIntNivel(ed.getId().getIntNivel());
			socioEstruc.setIntCodigo(ed.getId().getIntCodigo());
			socioEstruc.setIntTipoEstructura(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO);
			socioEstruc.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			socioEstruc.setIntEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa());
			socioEstruc.setIntPersonaUsuario(usuario.getIntPersPersonaPk());
			if(isValidoEstructuraEntidadEsp(socioEstruc) == false){
				log.info("Se aborta el proceso de grabado.");
		    	return;
		    }
			capacidadCreditoComp.setSocioEstructura(socioEstruc);
			solicitudEspecialCOntroller.getListaCapacidadCreditoComp().add(capacidadCreditoComp);
			//log.info("solicitudPrestamoController.getBeanSocioComp().getSocio().getListSocioEstructura(): " + solicitudPrestamoController.getBeanSocioComp().getSocio().getListSocioEstructura().size());
		//}
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
	}
	
	public void addEntidadSocio(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.addEntidadSocio-------------------------------------");
		initSocioEstructura();
		log.info("strFormIdSocioNatu: "+strFormIdSocioNatu);
		setStrCallingFormId(strFormIdSocioNatu);
	}
	
	public void limpiarArrayEntidad(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.seleccionarEntidad-------------------------------------");
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
				EstructuraFacadeRemote facade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
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
	public String getMsgTxtTipoSocio() {
		return msgTxtTipoSocio;
	}
	public void setMsgTxtTipoSocio(String msgTxtTipoSocio) {
		this.msgTxtTipoSocio = msgTxtTipoSocio;
	}
	public String getMsgTxtModalidad() {
		return msgTxtModalidad;
	}
	public void setMsgTxtModalidad(String msgTxtModalidad) {
		this.msgTxtModalidad = msgTxtModalidad;
	}
	public SolicitudPrestamoController getSolicitudPrestamoController() {
		return solicitudPrestamoController;
	}
	public void setSolicitudPrestamoController(
			SolicitudPrestamoController solicitudPrestamoController) {
		this.solicitudPrestamoController = solicitudPrestamoController;
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
	public List<Sucursal> getListSucursal() {
		log.info("-------------------------------------Debugging SocioEstructuraController.getListEstructura-------------------------------------");
		try {
			if(listSucursal == null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listSucursal = facade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
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
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
}