package pe.com.tumi.cobranza.gestion.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.domain.DocumentoCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaCierre;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaId;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranzaId;
import pe.com.tumi.cobranza.gestion.facade.DocumentoCobranzaFacadeLocal;
import pe.com.tumi.cobranza.gestion.facade.GestionCobranzaFacadeLocal;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.controller.UtilCobranza;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadController;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;

/************************************************************************/
/* Nombre de la clase: CobranzaController                               */
/* Funcionalidad : Registra Gestion de Cobranza                         */
/* y validaciones de los Certificados                                   */
/* Ref. : 																*/
/* Autor : FRAMIREZ 													*/
/* Versión : V1 														*/
/* Fecha creación : 01/01/2013 											*/
/* **********************************************************************/

public class CobranzaController extends GenericController {
	
	//Variables locales
	protected  static Logger 		log 			= Logger.getLogger(CobranzaController.class);
	//private    GestionCobranzaServiceImpl	GestionCobranzaService;	
	private    int                	rows = 5;
	private	   GestionCobranza		 beanGestionCobranza;
	 private   GestionCobranzaCierre beanGestCobCierre;
	   
	private    List<GestionCobranza>					beanListCobranzas;
	private    List<GestionCobranza>					beanListCobranzasCierre;
	
	private    GestionCobranza     gestionCobranzaSelected;
	private  List<Expediente> listaExpCredito;
	private  String strNombreApellidosGestor;
	private  String strDescSucursal;
	
	// Parámetros de busqueda Cobranza
	private	   Integer intCboTipoGestionCobranza;
	private	   Integer intCboSucursal;
	private    Integer cboEstadoCobranza;
	private	   String strTxtNumeroIdentidad;
	private	   Date dtTxtFechaIni;
	private	   Date dtTxtFechaFin;
	private    Integer intHoraInicio;
	private    Integer intHoraFin;
	private    Integer intMinInicio;
	private    Integer intMinFin;
	
	///////////////////////////////////////////////
	private  String strTipoMantCobranza;
    private  boolean formCobranzaDisabled;
    private  boolean cobranzaRendered;
    private  boolean formCobranzaEnabled;
    private  boolean bolGestionCobranza;
    private  boolean bolRecibosEnlaces;
    private  boolean habilitarBotones = true;
    
    private  boolean formPromocionEnabled;
    
    private  boolean formTramiteEnabled;
	//Propiedades para los combos de subsucursales
	private 	List<Sucursal> 			listJuridicaSucursal;
		//atributos de sesión
	private 	Integer 				SESION_IDEMPRESA;
	private 	Integer 				SESION_IDUSUARIO;
	
	private 	Integer 				SESION_IDSUCURSAL;
	private 	Integer 				SESION_IDSUBCURSAL;
	
	
	
	//Paramaetros de Filtro de Busqueda Entidad
	private String strRuc;
	private String strDni;
	
	private Integer intTipoBusqueda;
	private Integer TIPO_BUSQ_DNI = 1;
	private Integer TIPO_BUSQ_DESCRIPCION = 2;
	
	
	private Integer TIPO_BUSQ_ENTIDAD = 1;
	private Integer TIPO_BUSQ_SOCIO   = 2;
	private Integer intTipoSocio;
	//Lista Entidades Popup
	 
	private List<EstructuraComp> listaEntidad ; 
	private List<SocioComp> listaSocio ; 
	
	private boolean botonGrabarDisabled;
	private boolean formTipoDisabled;
	private boolean botonEliminarDisabled;
	private boolean botonValidarDisabled;
	
	//Atributos para deshabilitar:
    //Formulario Promocion
    
    private boolean formPromFechaDisabled;
    private boolean formPromHoraInicioDisabled;
    private boolean formPromHoraFinDisabled;
    private boolean formPromMinInicioDisabled;	
    private boolean formPromMinFinDisabled;
    private boolean formPromContactoDisabled;
    private boolean formPromObservacionDisabled;
    private boolean formPromBotonAgregarDisabled;
    private boolean formPromBotonEliminarDisabled;
    
    
  //Atributos para deshabilitar:
    //Formulario Tramite
    
    private boolean formTramFechaDisabled;
    private boolean formTramHoraInicioDisabled;
    private boolean formTramHoraFinDisabled;
    private boolean formTramMinInicioDisabled;	
    private boolean formTramMinFinDisabled;
    private boolean formTramContactoDisabled;
    private boolean formTramObservacionDisabled;
    private boolean formTramBotonAgregarDisabled;
    private boolean formTramBotonEliminarDisabled;
    private boolean formTramSubTipoGestionCobCod;
    private boolean formTramOpcSocioEntidad;
    
    
    //Atributos para deshabilitar:
    //Formulario Cierre de Gestion Diaria
    
    private boolean habilitarCierre;
    private boolean dtFechaCierreDisabled;
    private boolean habilitarGestion;
  
    
    
    private boolean habilitarFormCierre;
    
    
  //Atributos para deshabilitar:
    //Formulario Cobrzanza
    
 

	private boolean formCobrFechaDisabled;
    private boolean formCobrHoraInicioDisabled;
    private boolean formCobrHoraFinDisabled;
    private boolean formCobrMinInicioDisabled;	
    private boolean formCobrMinFinDisabled;
    private boolean formCobrContactoDisabled;
    private boolean formCobrObservacionDisabled;
    private boolean formCobrBotonAgregarDisabled;
    private boolean formCobrBotonEliminarDisabled;
    private boolean formCobrTipoSocioDisabled;
    private boolean formCobrParaLugarGestionDisabled;
    private boolean formCobrItemExpedienteDisabled;
    private boolean formCobrParaTipoResultadoDisabled;
    private boolean formCobrParaTipoSubResultadoDisabled;
    private boolean formCobrItemDocCobDisabled;
    private boolean formCobrBotonQuitarArchDisabled;
    private boolean formCobrBotonAdjuntarArchDisabled;
    
    private boolean formCobrBotonGenIngresoDisabled;
    private boolean formCobrBotonGenDocuDisabled;
    private boolean formGenerarDocumentoDisabled;
    private boolean formCobrBotonGenDocuDisabled2;
    private boolean habilitarDescarga;
    
  // Cobranza Cheque
    
    private boolean habilitarBotonGeneararIngreso;
    private boolean formBotonBusqCobrChDisabled;
    private boolean formBotonGenIngCobrChDisabled;
    private boolean formCobrChTipoResDisabled;
    
    private List<Tabla> listaSubResultado; 
    private List<DocumentoCobranza> listaDocCobranza; 
    
    private Integer intItemExpediente;
    private Integer intItemDocCob;
    
    //Archivo 
    
    private Archivo		archivoDocCobranza;
    private Archivo		archivoDocCobranzaTemp;
    
	
	public Archivo getArchivoDocCobranzaTemp() {
		return archivoDocCobranzaTemp;
	}

	public void setArchivoDocCobranzaTemp(Archivo archivoDocCobranzaTemp) {
		this.archivoDocCobranzaTemp = archivoDocCobranzaTemp;
	}

	//-------------------------------------------------------------------------------------------------------------------------------------------
	//Métodos de GestionCobranzaController para Mantenimientos
	//-------------------------------------------------------------------------------------------------------------------------------------------
	public CobranzaController() {
		limpiarFormCobranza();
		setFormCobranzaEnabled(true);
		setCobranzaRendered(false);
		strTipoMantCobranza = "1";
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
				
//				PermisoFacadeLocal localPermiso = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
//				permiso = localPermiso.getPermisoPerfilPorPk(id);
//				bolGestionCobranza = (permiso == null)?true:false;
//				id.setIntIdTransaccion(MENU_SUCURSAL);
//				permiso = localPermiso.getPermisoPerfilPorPk(id);
//				bolSucursal = (permiso == null)?true:false;
//				id.setIntIdTransaccion(MENU_Cobranza);
//				permiso = localPermiso.getPermisoPerfilPorPk(id);
//				bolCobranza = (permiso == null)?true:false;
//				id.setIntIdTransaccion(MENU_ZONAL);
//				permiso = localPermiso.getPermisoPerfilPorPk(id);
//				bolZonal = (permiso == null)?true:false;
				 SESION_IDEMPRESA = usuario.getEmpresa().getIntIdEmpresa();
				 
				 String nombreUsuario = usuario.getPersona().getNatural().getStrNombres();	
				 String apePatUsuario = usuario.getPersona().getNatural().getStrApellidoPaterno();	
			     String apeMatUsuario = usuario.getPersona().getNatural().getStrApellidoMaterno();
			     
				 setStrNombreApellidosGestor(SESION_IDUSUARIO+"-"+nombreUsuario+" "+apePatUsuario+" "+apeMatUsuario);
				 
				 setStrDescSucursal(usuario.getSucursal().getJuridica().getStrRazonSocial());
				 
				 SESION_IDSUCURSAL = usuario.getSucursal().getId().getIntIdSucursal();
				 SESION_IDSUBCURSAL = usuario.getSubSucursal().getId().getIntIdSubSucursal();
				 
				 GestorCobranzaId ids = new GestorCobranzaId();
				 ids.setIntPersEmpresaPk(id.getIntPersEmpresaPk());
				 ids.setIntPersPersonaGestorPk(usuario.getPersona().getIntIdPersona());
				 getBeanGestionCobranza().setGestorCobranza(new GestorCobranza());
				 getBeanGestionCobranza().getGestorCobranza().setId(ids);
				 
			}else{
				  bolGestionCobranza = false;
				  
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	@Override
	public void init(){
		log.info("-----------------------Debugging GestionCobranzaController.init()-----------------------------");
		ArrayList lsHoras = new ArrayList();
		int intH = 0;
		for(int i=0; i<13; i++){
			i++;
		}
		//limpiarFormGestionCobranza();
		strTipoMantCobranza = "1";
	}
	
	public boolean esValidoFechaGestion(Date fechaGestion){
				Date d1 = new Date();
		Date d2 = sumarFechasDias(d1,1);
		
		String fechaGestionReg = UtilCobranza.convierteDateAString(fechaGestion);
		
		String fechaActual     = UtilCobranza.convierteDateAString(d1);
		String fechaSiguiente  = UtilCobranza.convierteDateAString(d2);
		
		
		if(fechaGestionReg.equals(fechaActual) || fechaGestionReg.equals(fechaSiguiente)){
			return true;
		}
		return false;
	}
	
	 public Date sumarFechasDias(Date fch, int dias) {
	     Calendar cal = new GregorianCalendar();
	     cal.setTimeInMillis(fch.getTime());
	     cal.add(Calendar.DATE, dias);
	     return new Date(cal.getTimeInMillis());
	 }
	
	
       
    public void listarCobranzas(ActionEvent event) throws EJBFactoryException,BusinessException{
    	 buscarEntidad(null);
		log.info("-----------------------Debugging GestionCobranzaController.listarCobranzas-----------------------------");
		
	    log.info("Seteando los parametros de busqueda de áreas: ");
		log.info("-------------------------------------------------");
		log.info("intCboTipoGestionCobranza = "+ getIntCboTipoGestionCobranza());
		log.info("IntCboSucursal            = "+ getIntCboSucursal());
		log.info("strTxtNumeroIdentidad     = "+ getStrTxtNumeroIdentidad());
		log.info("dtTxtFechaIni             = "+ getDtTxtFechaIni());
		log.info("dtTxtFechaFin             = "+ getDtTxtFechaFin());
		
		 
		
		    GestionCobranza busqGestionCobranza = new GestionCobranza();
		   
		    busqGestionCobranza.setId(new GestionCobranzaId());
		    busqGestionCobranza.getId().setIntPersEmpresaGestionPK(SESION_IDEMPRESA);
		    busqGestionCobranza.setIntTipoGestionCobCod(getIntCboTipoGestionCobranza());
		    
			busqGestionCobranza.setStrNroDocuIdentidad(getStrTxtNumeroIdentidad() == null?"":getStrTxtNumeroIdentidad().trim());
			busqGestionCobranza.setIntSucursal(getIntCboSucursal());
			busqGestionCobranza.setDtFechaGestionIni(getDtTxtFechaIni());
			busqGestionCobranza.setDtFechaGestionFin(getDtTxtFechaFin());
			busqGestionCobranza.setIntEstado(getCboEstadoCobranza());
			
		
		    ArrayList listaGestionCobranza = new ArrayList();
			try {
				GestionCobranzaFacadeLocal GestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
				listaGestionCobranza = (ArrayList) GestionCobranzaFacade.getListaGestionCobranza(busqGestionCobranza);
				
				log.info("listaGestionCobranza.size: "+listaGestionCobranza.size());
			} catch (EJBFactoryException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			
		
		setMessageSuccess("Listado de Gestion Cobranza ha sido satisfactorio");
		setBeanListCobranzas(listaGestionCobranzaDet(listaGestionCobranza));
		
		setFormCobranzaEnabled(true);
		setCobranzaRendered(false);
		limpiarFormCobranza();
		
	}
    
    public List<GestionCobranza> listaGestionCobranzaDet(List<GestionCobranza> lista) throws EJBFactoryException,BusinessException{
    	
    	if (getBeanListCobranzas() != null){
    		getBeanListCobranzas().clear();
    	}
    	List<GestionCobranza> listaGcob= new ArrayList<GestionCobranza>();
    	if (listaGcob != null){
    		listaGcob.clear();
    	}
    	
    	GestionCobranzaFacadeLocal	gestioCobranzaFacade= (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
    	
    	for (int i = 0; i < lista.size(); i++) {
			 GestionCobranza gestionCobranza = (GestionCobranza)lista.get(i);
			 
		     GestionCobranzaEnt gestionCobranzaEnt = new  GestionCobranzaEnt();
			 gestionCobranzaEnt.getId().setIntPersEmpresaGestion(gestionCobranza.getId().getIntPersEmpresaGestionPK());
			 gestionCobranzaEnt.getId().setIntItemGestionCobranza(gestionCobranza.getId().getIntItemGestionCobranza());
			 
			 List<GestionCobranzaEnt> listaGcobEnt =  gestioCobranzaFacade.getListaGestionCobranzaEnt(gestionCobranzaEnt);
			 
			 if (listaGcobEnt != null && listaGcobEnt.size() > 0){
			    for (GestionCobranzaEnt gesCobEnt : listaGcobEnt) {
			    	
			    
			    	GestionCobranza gesCob = new GestionCobranza();
					                gesCob.setId(gestionCobranza.getId());
					            	gesCob.setIntItemGestionCobranza(gestionCobranza.getIntItemGestionCobranza());
					                gesCob.setIntTipoGestionCobCod(gestionCobranza.getIntTipoGestionCobCod());
					                gesCob.setIntSubTipoGestionCobCod(gestionCobranza.getIntSubTipoGestionCobCod());
					                gesCob.setGestorCobranza(gestionCobranza.getGestorCobranza());
					                gesCob.setPersona(gestionCobranza.getPersona());
					            	
					            	//ID Gestor
					                gesCob.setIntIdPersonaGestor(gestionCobranza.getIntIdPersonaGestor());
					                gesCob.setIntIdEmpresaGestor(gestionCobranza.getIntIdEmpresaGestor());
					                gesCob.setIntItemGestorcobranzasuc(gestionCobranza.getIntItemGestorcobranzasuc());
					            	
					                gesCob.setDtFechaGestion(gestionCobranza.getDtFechaGestion());
					                gesCob.setDtHoraInicio(gestionCobranza.getDtHoraInicio());
					                gesCob.setDtHoraFin(gestionCobranza.getDtHoraFin());
					                gesCob.setStrContacto(gestionCobranza.getStrContacto());
					                gesCob.setStrObservacion(gestionCobranza.getStrObservacion());
					                gesCob.setIntIdSucursalGestion(gestionCobranza.getIntIdSucursalGestion());       
					                gesCob.setIntEstado(gestionCobranza.getIntEstado()); 
					                
					                gesCob.setDtFechaCierre(gestionCobranza.getDtFechaCierre());
					                gesCob.setIntParaLugarGestion(gestionCobranza.getIntParaLugarGestion());
					                gesCob.setIntParaTipoResultado(gestionCobranza.getIntParaTipoResultado());
					                gesCob.setIntParaTipoSubResultado(gestionCobranza.getIntParaTipoSubResultado());
					                gesCob.setIntItemDocCob(gestionCobranza.getIntItemDocCob());
					                gesCob.setIntItemArchivo(gestionCobranza.getIntItemArchivo());
					                gesCob.setIntItemHistorico(gestionCobranza.getIntItemHistorico());
					                gesCob.setDocCobranza(gestionCobranza.getDocCobranza());
					              
					                
					 if (gesCobEnt.getIntNivel().equals(1)){
						 gesCob.setStrTipoEnitidad("Institución");
					 }
					 else{
						 gesCob.setStrTipoEnitidad("Unidad Ejecutora");
					 }
					
					 gesCob.setStrDescEntidad(gesCobEnt.getEstructura().getJuridica().getStrRazonSocial()+"-"+gesCobEnt.getEstructura().getJuridica().getPersona().getStrRuc());
					 
					 
					 listaGcob.add(gesCob);
				}
			 }
			    
			 GestionCobranzaSoc gestionCobranzaSoc = new  GestionCobranzaSoc();
			 gestionCobranzaSoc.getId().setIntPersEmpresaGestion(gestionCobranza.getId().getIntPersEmpresaGestionPK());
			 gestionCobranzaSoc.getId().setIntItemGestionCobranza(gestionCobranza.getId().getIntItemGestionCobranza());
			 List<GestionCobranzaSoc> listaGcobSoc =  gestioCobranzaFacade.getListaGestionCobranzaSoc(gestionCobranzaSoc);
			 if (listaGcobSoc != null && listaGcobSoc.size() > 0){
				for (GestionCobranzaSoc gesCobSoc : listaGcobSoc) {
					
					
					   GestionCobranza gesCob = new GestionCobranza();
					    gesCob.setId(gestionCobranza.getId());
		            	gesCob.setIntItemGestionCobranza(gestionCobranza.getIntItemGestionCobranza());
		                gesCob.setIntTipoGestionCobCod(gestionCobranza.getIntTipoGestionCobCod());
		                gesCob.setIntSubTipoGestionCobCod(gestionCobranza.getIntSubTipoGestionCobCod());
		                gesCob.setGestorCobranza(gestionCobranza.getGestorCobranza());
		                gesCob.setPersona(gestionCobranza.getPersona());
		            	
		            	//ID Gestor
		                gesCob.setIntIdPersonaGestor(gestionCobranza.getIntIdPersonaGestor());
		                gesCob.setIntIdEmpresaGestor(gestionCobranza.getIntIdEmpresaGestor());
		                gesCob.setIntItemGestorcobranzasuc(gestionCobranza.getIntItemGestorcobranzasuc());
		            	
		                gesCob.setDtFechaGestion(gestionCobranza.getDtFechaGestion());
		                gesCob.setDtHoraInicio(gestionCobranza.getDtHoraInicio());
		                gesCob.setDtHoraFin(gestionCobranza.getDtHoraFin());
		                gesCob.setStrContacto(gestionCobranza.getStrContacto());
		                gesCob.setStrObservacion(gestionCobranza.getStrObservacion());
		                gesCob.setIntIdSucursalGestion(gestionCobranza.getIntIdSucursalGestion());   
		                gesCob.setIntEstado(gestionCobranza.getIntEstado());
	                    gesCob.setStrTipoSocio("Socio");
	                    gesCob.setStrDescSocio(gesCobSoc.getSocio().getStrNombreSoc()+" "+gesCobSoc.getSocio().getStrApePatSoc()+" "+gesCobSoc.getSocio().getStrApeMatSoc()+"-"+gesCobSoc.getSocioComp().getPersona().getDocumento().getStrNumeroIdentidad());
	                    
	                    gesCob.setDtFechaCierre(gestionCobranza.getDtFechaCierre());
		                gesCob.setIntParaLugarGestion(gestionCobranza.getIntParaLugarGestion());
		                gesCob.setIntParaTipoResultado(gestionCobranza.getIntParaTipoResultado());
		                gesCob.setIntParaTipoSubResultado(gestionCobranza.getIntParaTipoSubResultado());
		                gesCob.setIntItemDocCob(gestionCobranza.getIntItemDocCob());
		                gesCob.setIntItemArchivo(gestionCobranza.getIntItemArchivo());
		                gesCob.setIntItemHistorico(gestionCobranza.getIntItemHistorico());
		                gesCob.setDocCobranza(gestionCobranza.getDocCobranza());
		                
					  listaGcob.add(gesCob);
				} 
			 }
			 
			 if ((listaGcobSoc == null || listaGcobSoc.size() == 0)  && (listaGcobEnt == null || listaGcobEnt.size() == 0)){
				    GestionCobranza gesCob = new GestionCobranza();
				    gesCob.setId(gestionCobranza.getId());
	            	gesCob.setIntItemGestionCobranza(gestionCobranza.getIntItemGestionCobranza());
	                gesCob.setIntTipoGestionCobCod(gestionCobranza.getIntTipoGestionCobCod());
	                gesCob.setIntSubTipoGestionCobCod(gestionCobranza.getIntSubTipoGestionCobCod());
	                gesCob.setGestorCobranza(gestionCobranza.getGestorCobranza());
	                gesCob.setPersona(gestionCobranza.getPersona());
	            	
	            	//ID Gestor
	                gesCob.setIntIdPersonaGestor(gestionCobranza.getIntIdPersonaGestor());
	                gesCob.setIntIdEmpresaGestor(gestionCobranza.getIntIdEmpresaGestor());
	                gesCob.setIntItemGestorcobranzasuc(gestionCobranza.getIntItemGestorcobranzasuc());
	            	
	                gesCob.setDtFechaGestion(gestionCobranza.getDtFechaGestion());
	                gesCob.setDtHoraInicio(gestionCobranza.getDtHoraInicio());
	                gesCob.setDtHoraFin(gestionCobranza.getDtHoraFin());
	                gesCob.setStrContacto(gestionCobranza.getStrContacto());
	                gesCob.setStrObservacion(gestionCobranza.getStrObservacion());
	                gesCob.setIntIdSucursalGestion(gestionCobranza.getIntIdSucursalGestion());  
	                gesCob.setIntEstado(gestionCobranza.getIntEstado());
	                
	                gesCob.setDtFechaCierre(gestionCobranza.getDtFechaCierre());
	                gesCob.setIntParaLugarGestion(gestionCobranza.getIntParaLugarGestion());
	                gesCob.setIntParaTipoResultado(gestionCobranza.getIntParaTipoResultado());
	                gesCob.setIntParaTipoSubResultado(gestionCobranza.getIntParaTipoSubResultado());
	                gesCob.setIntItemDocCob(gestionCobranza.getIntItemDocCob());
	                gesCob.setIntItemArchivo(gestionCobranza.getIntItemArchivo());
	                gesCob.setIntItemHistorico(gestionCobranza.getIntItemHistorico());
	                gesCob.setDocCobranza(gestionCobranza.getDocCobranza());
	                
	             listaGcob.add(gesCob);
			 }
		}
    	
    	return listaGcob;
    }
    
    public void limpiarGestionCobranza(){
    	log.info("-------------------------------------Debugging CobranzaController.limpiarGestionCobranza-------------------------------------");
		setBeanGestionCobranza(new GestionCobranza());
		getBeanGestionCobranza().setId(new GestionCobranzaId());
		//setBlnUpdating(false);
    }
    
    public void grabarCobranza(ActionEvent event) throws EJBFactoryException{
    	 GestionCobranza gestionCobranza = null;
		  log.info("-----------------------Debugging CobranzaController.grabarCobranza-----------------------------");
	
	     //Validaciones
 	     if(!validarGestionCobranza()){
 			return;
 	     }
 	     
	     //Conversiones  
	      String strFechaGestion = UtilCobranza.convierteDateAString(getBeanGestionCobranza().getDtFechaGestion());
	      if (getIntHoraInicio() != null){
		      Timestamp timHoraInicio = UtilCobranza.convierteStringATimestamp(strFechaGestion+" "+getIntHoraInicio()+":"+getIntMinInicio());
		      getBeanGestionCobranza().setDtHoraInicio(timHoraInicio);
	      }
	      if (getIntHoraInicio() != null && getIntHoraFin() != null){
	         Timestamp timHoraFin    = UtilCobranza.convierteStringATimestamp(strFechaGestion+" "+getIntHoraFin()+":"+getIntMinFin());
	         getBeanGestionCobranza().setDtHoraFin(timHoraFin);
	      }
	   
	      //Grabaciones
	      if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PROMOCION) || getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_CHEQUE)) {
		      GestionCobranzaFacadeLocal gestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
		    
				if(getBeanGestionCobranza()!=null && getBeanGestionCobranza().getId()!=null){
					
					
					//validar los valores boolean de Movimiento e Id Extranjero
								
					if(getBeanGestionCobranza().getId().getIntPersEmpresaGestionPK()==null && getBeanGestionCobranza().getId().getIntItemGestionCobranza()==null){
				
						//Grabar GestionCobranza
						getBeanGestionCobranza().getId().setIntPersEmpresaGestionPK(SESION_IDEMPRESA);
						getBeanGestionCobranza().setIntIdEmpresaGestor(getBeanGestionCobranza().getGestorCobranza().getId().getIntPersEmpresaPk());
						getBeanGestionCobranza().setIntIdPersonaGestor(getBeanGestionCobranza().getGestorCobranza().getId().getIntPersPersonaGestorPk());
		                getBeanGestionCobranza().setIntItemGestorcobranzasuc(getBeanGestionCobranza().getGestorCobranza().getId().getIntItemGestorCobranzaSuc());
						
						getBeanGestionCobranza().setIntIdSucursalGestion(SESION_IDSUCURSAL);
						getBeanGestionCobranza().setIntSucursal(SESION_IDSUBCURSAL);
						getBeanGestionCobranza().setIntEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						
		
						try {
							gestionCobranza = gestionCobranzaFacade.grabarGestionCobranza(getBeanGestionCobranza());
							listarCobranzas(event);
							
							setFormPromocionEnabled(true);
							setFormCobranzaEnabled(false);
							setFormTramiteEnabled(false);
							setFormGenerarDocumentoDisabled(false);
							
						} catch (BusinessException e) {
							FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
							log.error(e);
						}
					}else if (getBeanGestionCobranza().getId().getIntPersEmpresaGestionPK()!=null && getBeanGestionCobranza().getId().getIntItemGestionCobranza()!=null){
						//Actualizar Tipo de Cambio
						try {
							gestionCobranza = gestionCobranzaFacade.modificarGestionCobranza(getBeanGestionCobranza());
							listarCobranzas(event);
							obtenerGestionCobranza(null);
							
							setFormPromocionEnabled(true);
							setFormCobranzaEnabled(false);
							setFormTramiteEnabled(false);
							setFormGenerarDocumentoDisabled(false);
							
							setBotonValidarDisabled(false);
							setFormTipoDisabled(true);	
							setFormCobranzaDisabled(false);
							setCobranzaRendered(true);
						} catch (BusinessException e) {
							FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
							log.error(e);
						}
					}
				}
			
				if(gestionCobranza != null){
					FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
						
				}
				
			
	     }else
	     if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_TRAMITEADMIN)){
	    	 grabarGestionCobranzaTramite(event);
	     }else{
	    	 grabarGestionCobranza(event);
	     }
	
    }
    public void grabarGestionCobranzaTramite(ActionEvent event) throws EJBFactoryException{
    	 GestionCobranza gestionCobranza = null;
    	 
    	      GestionCobranzaFacadeLocal gestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
    	   
			if(getBeanGestionCobranza()!=null && getBeanGestionCobranza().getId()!=null){
				
				
				//validar los valores boolean de Movimiento e Id Extranjero
							
				if(getBeanGestionCobranza().getId().getIntPersEmpresaGestionPK()==null && getBeanGestionCobranza().getId().getIntItemGestionCobranza()==null){
			
					//Grabar GestionCobranza
					getBeanGestionCobranza().getId().setIntPersEmpresaGestionPK(SESION_IDEMPRESA);
					getBeanGestionCobranza().setIntIdEmpresaGestor(getBeanGestionCobranza().getGestorCobranza().getId().getIntPersEmpresaPk());
					getBeanGestionCobranza().setIntIdPersonaGestor(getBeanGestionCobranza().getGestorCobranza().getId().getIntPersPersonaGestorPk());
	                getBeanGestionCobranza().setIntItemGestorcobranzasuc(getBeanGestionCobranza().getGestorCobranza().getId().getIntItemGestorCobranzaSuc());
					getBeanGestionCobranza().setIntIdSucursalGestion(SESION_IDSUCURSAL);
					getBeanGestionCobranza().setIntSucursal(SESION_IDSUBCURSAL);
					getBeanGestionCobranza().setIntEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					
	
					try {
						gestionCobranza = gestionCobranzaFacade.grabarGestionCobranza(getBeanGestionCobranza());
						listarCobranzas(event);
						
						setFormCobranzaEnabled(false);
						setFormPromocionEnabled(false);
						setFormTramiteEnabled(true);
						setFormGenerarDocumentoDisabled(false);
						
						setFormCobranzaDisabled(false);
						setCobranzaRendered(false);
					} catch (BusinessException e) {
						FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
						log.error(e);
					}
				}else if (getBeanGestionCobranza().getId().getIntPersEmpresaGestionPK()!=null && getBeanGestionCobranza().getId().getIntItemGestionCobranza()!=null){
					//Actualizar Tipo de Cambio
					try {
						
						gestionCobranza = gestionCobranzaFacade.modificarGestionCobranza(getBeanGestionCobranza());
						
						listarCobranzas(event);
						obtenerGestionCobranza(null);
						
						setFormCobranzaEnabled(false);
						setFormPromocionEnabled(false);
						setFormTramiteEnabled(true);
						setFormGenerarDocumentoDisabled(false);
						
						setBotonValidarDisabled(false);
						setFormTipoDisabled(true);	
						setFormCobranzaDisabled(false);
						setCobranzaRendered(true);
					} catch (BusinessException e) {
						FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
						log.error(e);
					}
				}
			}
		
			if(gestionCobranza != null){
				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
			}
	    	
    }
    
    public void grabarGestionCobranza(ActionEvent event) throws EJBFactoryException{
   	 GestionCobranza gestionCobranza = null;
   	 
   	      GestionCobranzaFacadeLocal gestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
   	   
			if(getBeanGestionCobranza()!=null && getBeanGestionCobranza().getId()!=null){
				
				//validar los valores boolean de Movimiento e Id Extranjero
							
				if(getBeanGestionCobranza().getId().getIntPersEmpresaGestionPK()==null && getBeanGestionCobranza().getId().getIntItemGestionCobranza()==null){
			
					//Grabar GestionCobranza
					getBeanGestionCobranza().getId().setIntPersEmpresaGestionPK(SESION_IDEMPRESA);
					getBeanGestionCobranza().setIntIdEmpresaGestor(getBeanGestionCobranza().getGestorCobranza().getId().getIntPersEmpresaPk());
					getBeanGestionCobranza().setIntIdPersonaGestor(getBeanGestionCobranza().getGestorCobranza().getId().getIntPersPersonaGestorPk());
	                getBeanGestionCobranza().setIntItemGestorcobranzasuc(getBeanGestionCobranza().getGestorCobranza().getId().getIntItemGestorCobranzaSuc());
					getBeanGestionCobranza().setIntIdSucursalGestion(SESION_IDSUCURSAL);
					getBeanGestionCobranza().setIntSucursal(SESION_IDSUBCURSAL);
					getBeanGestionCobranza().setIntEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					
					
					
					for (Expediente expediente: getListaExpCredito()) {
						
						if (expediente.getId().getIntItemExpediente().equals(getIntItemExpediente())){
							GestionCobranzaSoc gesCobSoc = (GestionCobranzaSoc)getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(0);
							gesCobSoc.setIntCserItemExp(expediente.getId().getIntItemExpediente());
							gesCobSoc.setIntCserdeteItemExp(expediente.getId().getIntItemExpedienteDetalle());
							gesCobSoc.setIntCsocCuenta(gesCobSoc.getSocioComp().getCuenta().getId().getIntCuenta());
							gesCobSoc.setIntPersEmpresa(gesCobSoc.getSocio().getId().getIntIdEmpresa());
							gesCobSoc.setIntPersPersona(gesCobSoc.getSocio().getId().getIntIdPersona());
							gesCobSoc.setIntTipoPersonaOpe(getIntTipoSocio());
							getBeanGestionCobranza().getListaGestionCobranzaDetalle().set(0, gesCobSoc);
							break;
						}
					}
					
					try {
						
						if (getArchivoDocCobranza() != null){
							Archivo archivo = grabarArchvioDocAdjunto(getArchivoDocCobranza());
							getBeanGestionCobranza().setIntItemArchivo(archivo.getId().getIntItemArchivo());
							getBeanGestionCobranza().setIntItemHistorico(archivo.getId().getIntItemHistorico());
						}
						else{
							getBeanGestionCobranza().setIntItemArchivo(null);
							getBeanGestionCobranza().setIntItemHistorico(null);
						}
						
						gestionCobranza = gestionCobranzaFacade.grabarGestionCobranza(getBeanGestionCobranza());
						listarCobranzas(event);
						
						setFormCobranzaEnabled(true);
						setFormPromocionEnabled(false);
						setFormTramiteEnabled(false);
						
						setFormCobranzaDisabled(false);
						setCobranzaRendered(false);
					} catch (BusinessException e) {
						FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
						log.error(e);
					}
				}else if (getBeanGestionCobranza().getId().getIntPersEmpresaGestionPK()!=null && getBeanGestionCobranza().getId().getIntItemGestionCobranza()!=null){
					try {
						
						if (getArchivoDocCobranza() != null && archivoDocCobranzaTemp !=  null){
							
							getArchivoDocCobranza().getId().setIntItemArchivo(archivoDocCobranzaTemp.getId().getIntItemArchivo());
							getArchivoDocCobranza().getId().setIntItemHistorico(archivoDocCobranzaTemp.getId().getIntItemHistorico());
							getArchivoDocCobranza().getId().setIntParaTipoCod(archivoDocCobranzaTemp.getId().getIntParaTipoCod());
							
							Archivo archivo = grabarArchvioDocAdjunto(getArchivoDocCobranza());
							getBeanGestionCobranza().setIntItemArchivo(archivo.getId().getIntItemArchivo());
							getBeanGestionCobranza().setIntItemHistorico(archivo.getId().getIntItemHistorico());
						}
						else{
								getBeanGestionCobranza().setIntItemArchivo(null);
								getBeanGestionCobranza().setIntItemHistorico(null);
						}
						
						if (getBeanGestionCobranza().getIntParaLugarGestion().equals(Constante.PARAM_T_TIPORESULTADO_PAGO_CAJA)){
							getBeanGestionCobranza().setIntItemDocCob(null);
							getBeanGestionCobranza().setIntItemArchivo(null);
							getBeanGestionCobranza().setIntItemHistorico(null);
						}
						
					
						
						for (Expediente expediente: getListaExpCredito()) {
							
							if (expediente.getId().getIntItemExpediente().equals(getIntItemExpediente())){
								GestionCobranzaSoc gesCobSoc = (GestionCobranzaSoc)getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(0);
								gesCobSoc.setIntCserItemExp(expediente.getId().getIntItemExpediente());
								gesCobSoc.setIntCserdeteItemExp(expediente.getId().getIntItemExpedienteDetalle());
								gesCobSoc.setIntCsocCuenta(gesCobSoc.getSocioComp().getCuenta().getId().getIntCuenta());
								gesCobSoc.setIntPersEmpresa(gesCobSoc.getSocio().getId().getIntIdEmpresa());
								gesCobSoc.setIntPersPersona(gesCobSoc.getSocio().getId().getIntIdPersona());
								gesCobSoc.setIntTipoPersonaOpe(getIntTipoSocio());
								getBeanGestionCobranza().getListaGestionCobranzaDetalle().set(0, gesCobSoc);
								break;
							}
						}
						
						gestionCobranza = gestionCobranzaFacade.modificarGestionCobranza(getBeanGestionCobranza());
						
						listarCobranzas(event);
						obtenerGestionCobranza(null);
						
						setFormCobranzaEnabled(true);
						setFormPromocionEnabled(false);
						setFormTramiteEnabled(false);
						
						
						setBotonValidarDisabled(false);
						setFormTipoDisabled(true);	
						setFormCobranzaDisabled(false);
						setCobranzaRendered(false);
					} catch (BusinessException e) {
						FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
						log.error(e);
					}
				}
			}
		
			if(gestionCobranza != null){
				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
			}
	    	
   }
    
    public void setSelectedGestionCobranza(ActionEvent event){
		log.info("-------------------------------------Debugging cobranzaController.setSelectedGestionCobranza-------------------------------------");
		inicio(null);
		if(getBeanListCobranzas() != null){
			GestionCobranza  gestionCobraSel =  (GestionCobranza)event.getComponent().getAttributes().get("item");
			if (!SESION_IDUSUARIO.equals(gestionCobraSel.getIntIdPersonaGestor())){
				setBotonEliminarDisabled(false);
			}
			else
			if (gestionCobraSel.getIntEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				setBotonEliminarDisabled(false);
			}
			else
			if (gestionCobraSel.getDtFechaCierre() != null){
				setBotonEliminarDisabled(false);
			}
			else
			{
				setBotonEliminarDisabled(true);
			}
			
			setGestionCobranzaSelected(gestionCobraSel);
				
		}
	}
	
    
    public void obtenerGestionCobranza(ActionEvent event)  throws EJBFactoryException, BusinessException{
    	GestionCobranza gestionCobranza = null;
    	log.info("------------------------------------Debugging CobranzaController.obtenerGestionCobranza------------------------------------");
		if(getGestionCobranzaSelected()!=null){
			
			setHabilitarDescarga(false);	         
			setHabilitarBotonGeneararIngreso(false); 
			setFormBotonBusqCobrChDisabled(false);
  	        setFormBotonGenIngCobrChDisabled(false);
  	        setFormCobrChTipoResDisabled(false);
				
				
				try	{
					GestionCobranzaFacadeLocal gestioCobranzaFacade = (GestionCobranzaFacadeLocal) EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
				     
					  //gestionCobranza = getGestionCobranzaSelected();
					  
					  gestionCobranza = gestioCobranzaFacade.getGestionCobranza(getGestionCobranzaSelected());
					
				     setCobranzaRendered(true);
				     if (getGestionCobranzaSelected().getDtHoraInicio() != null){
				    	    setIntHoraInicio(UtilCobranza.obtieneHora(getGestionCobranzaSelected().getDtHoraInicio()));
				    	    setIntMinInicio(UtilCobranza.obtieneMinuto(getGestionCobranzaSelected().getDtHoraInicio()));
				    	 }
				     if (getGestionCobranzaSelected().getDtHoraFin()   != null){
				    	    setIntHoraFin(UtilCobranza.obtieneHora(getGestionCobranzaSelected().getDtHoraFin()));
				    	    setIntMinFin(UtilCobranza.obtieneMinuto(getGestionCobranzaSelected().getDtHoraFin()));
				     }
				     
				     setBeanGestionCobranza(gestionCobranza);
				     
				     if (gestionCobranza.getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PROMOCION) || gestionCobranza.getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_CHEQUE)){
				    	 GestionCobranzaEnt gestionCobranzaEnt = new  GestionCobranzaEnt();
						 gestionCobranzaEnt.getId().setIntPersEmpresaGestion(gestionCobranza.getId().getIntPersEmpresaGestionPK());
						 gestionCobranzaEnt.getId().setIntItemGestionCobranza(gestionCobranza.getId().getIntItemGestionCobranza());
						   
						 if (gestionCobranza.getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_CHEQUE)){
							  gestionCobranza.setListaGestionCobranzaEnt(gestioCobranzaFacade.getListaGestionCobranzaEnt(gestionCobranzaEnt));
							  GestionCobranzaEnt gCobEnt2 = (GestionCobranzaEnt)gestionCobranza.getListaGestionCobranzaEnt().get(0);
			            	  Integer codigo         = gCobEnt2.getEstructura().getId().getIntCodigo();
			            	  String  desRazonSocial = gCobEnt2.getEstructura().getJuridica().getStrRazonSocial();
			            	  String  ruc            = gCobEnt2.getEstructura().getJuridica().getPersona().getStrRuc();
			            	  getBeanGestionCobranza().setStrDescEntidad(codigo+"-"+desRazonSocial+"-RUC:"+ruc);
			            	  
			            	  if (gestionCobranza.getIntParaTipoResultado().equals(Constante.PARAM_T_TIPORESULTADOCOBROCHEQUE)){
			          			  setHabilitarBotonGeneararIngreso(true);
			            	  }
			             }else{
							  getBeanGestionCobranza().setListaGestionCobranzaEnt(gestioCobranzaFacade.getListaGestionCobranzaEnt(gestionCobranzaEnt));
						 }
						 
				    	
				    	 setFormPromocionEnabled(true);
				    	 setFormTramiteEnabled(false);
				    	 setFormCobranzaEnabled(false);
				    	 setFormGenerarDocumentoDisabled(false);
				    	 
				         setFormCobranzaDisabled(false);
				 	     setCobranzaRendered(true);
				 	     
				 	     if (getBeanGestionCobranza().getIntEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				 	    	       deshabilitarFormularioPromocion();
				 	    	       setBotonGrabarDisabled(true);
				 	    	       
				 	    	       setFormBotonBusqCobrChDisabled(true);
				 	    	       setFormBotonGenIngCobrChDisabled(true);
				 	    	       setFormCobrChTipoResDisabled(true);
				 	    	     
				 	     }
				 	     else
				 	     {	 
					 	     if (!gestionCobranza.getIntIdPersonaGestor().equals(SESION_IDUSUARIO)){
									  deshabilitarFormularioPromocion();
									  setBotonGrabarDisabled(true);
									  setFormBotonBusqCobrChDisabled(true);
					 	    	      setFormBotonGenIngCobrChDisabled(true);
					 	    	      setFormCobrChTipoResDisabled(true);
					 	    	     
							 }
					 	     else
					 	     if(gestionCobranza.getDtFechaCierre() != null){
					 	    	  deshabilitarFormularioPromocion();
								  setBotonGrabarDisabled(true);
								  setFormBotonBusqCobrChDisabled(true);
				 	    	      setFormBotonGenIngCobrChDisabled(true);
				 	    	      setFormCobrChTipoResDisabled(true);
				 	    	      setBotonValidarDisabled(false);
					 	     }
					 	     else{
								  habilitarFormularioPromocion();
								  setBotonValidarDisabled(false);
								  setFormTipoDisabled(true);
								  setBotonGrabarDisabled(false);
							 }
				 	    }
				 	     
				    }
				    else{
				     
				    	if (gestionCobranza.getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_TRAMITEADMIN)){
					    	 GestionCobranzaEnt gestionCobranzaEnt = new  GestionCobranzaEnt();
							 gestionCobranzaEnt.getId().setIntPersEmpresaGestion(gestionCobranza.getId().getIntPersEmpresaGestionPK());
							 gestionCobranzaEnt.getId().setIntItemGestionCobranza(gestionCobranza.getId().getIntItemGestionCobranza());
							 getBeanGestionCobranza().setListaGestionCobranzaEnt(gestioCobranzaFacade.getListaGestionCobranzaEnt(gestionCobranzaEnt));
							    
							 GestionCobranzaSoc gestionCobranzaSoc = new  GestionCobranzaSoc();
							 gestionCobranzaSoc.getId().setIntPersEmpresaGestion(gestionCobranza.getId().getIntPersEmpresaGestionPK());
							 gestionCobranzaSoc.getId().setIntItemGestionCobranza(gestionCobranza.getId().getIntItemGestionCobranza());
							 getBeanGestionCobranza().setListaGestionCobranzaSoc(gestioCobranzaFacade.getListaGestionCobranzaSoc(gestionCobranzaSoc));
							 
					    	 getBeanGestionCobranza().setListaGestionCobranzaDetalle(listaGestionCobranzaDetalle());
					    	 
					    	 setFormPromocionEnabled(false);
					    	 setFormTramiteEnabled(true);
					    	 setFormCobranzaEnabled(false);
					    	 setFormGenerarDocumentoDisabled(false);
					    	 
					         setFormCobranzaDisabled(false);
					 	     setCobranzaRendered(true);
					 	     setFormTipoDisabled(true);
					 	     
					 	    if (getBeanGestionCobranza().getIntEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				 	    	       deshabilitarFormularioTramite();
				 	    	       setBotonGrabarDisabled(true);
					 	     }
					 	     else
					 	     {	 
						 	     if (!gestionCobranza.getIntIdPersonaGestor().equals(SESION_IDUSUARIO)){
										  deshabilitarFormularioTramite();
										  setBotonGrabarDisabled(true);
										  setBotonValidarDisabled(false);
								 }else
								 if(gestionCobranza.getDtFechaCierre() != null){
									      deshabilitarFormularioTramite();
										  setBotonGrabarDisabled(true);
										  setBotonValidarDisabled(false);
							 	 }
								 else
								 {
										  habilitarFormularioTramite();
										  setBotonGrabarDisabled(false);
										  setBotonValidarDisabled(false);
										  setFormTipoDisabled(true);
								 }
					 	    }
					 	     
				    	}else{
				    		 archivoDocCobranzaTemp = null;
				    		 getBeanGestionCobranza().setIntOpcSocioEntidad(TIPO_BUSQ_SOCIO);
				    		 GestionCobranzaSoc gestionCobranzaSoc = new  GestionCobranzaSoc();
							 gestionCobranzaSoc.getId().setIntPersEmpresaGestion(gestionCobranza.getId().getIntPersEmpresaGestionPK());
							 gestionCobranzaSoc.getId().setIntItemGestionCobranza(gestionCobranza.getId().getIntItemGestionCobranza());
							 getBeanGestionCobranza().setListaGestionCobranzaSoc(gestioCobranzaFacade.getListaGestionCobranzaSoc(gestionCobranzaSoc));
							 getBeanGestionCobranza().setListaGestionCobranzaDetalle(listaGestionCobranzaDetalle());
							 
							 if (getBeanGestionCobranza().getListaGestionCobranzaDetalle() != null && getBeanGestionCobranza().getListaGestionCobranzaDetalle().size() >0)
							 { 
						    	 gestionCobranzaSoc = (GestionCobranzaSoc)getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(0);
								 setIntTipoSocio(gestionCobranzaSoc.getIntTipoPersonaOpe());
								 getBeanGestionCobranza().setStrSocioNombre(gestionCobranzaSoc.getSocio().getStrNombreSoc()+ " "+
										                                    gestionCobranzaSoc.getSocio().getStrApePatSoc()+ " " +
										                                    gestionCobranzaSoc.getSocio().getStrApeMatSoc());
								 setIntItemExpediente(gestionCobranzaSoc.getIntCserItemExp());
								 
								 if (gestionCobranzaSoc.getIntTipoPersonaOpe().equals(Constante.PARAM_T_TIPO_SOCIO_TITTULAR)){
									 listaExpedienteCreditoPorTitular();
								 }
								 else{
									 listaExpedienteCreditoPorGarante();
								 }
								 
							 }
							 
							 getBeanGestionCobranza().setIntParaTipoResultado(gestionCobranza.getIntParaTipoResultado());
							 loadCboSubResultado(getBeanGestionCobranza().getIntParaTipoResultado());
							 
							 GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
						     
							 
							 ArchivoId idArchivo = new ArchivoId();
							 
							 idArchivo.setIntParaTipoCod(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DOCCOBRANZA);
							 idArchivo.setIntItemArchivo(gestionCobranza.getIntItemArchivo());
							 idArchivo.setIntItemHistorico(gestionCobranza.getIntItemHistorico());
							 setArchivoDocCobranza(facade.getArchivoPorPK(idArchivo));
							 
							 setFormPromocionEnabled(false);
					    	 setFormTramiteEnabled(false);
					    	 setFormCobranzaEnabled(true);
					    	 
					    	
					    	
					    	 
					    	 setFormCobranzaDisabled(false);
					 	     setCobranzaRendered(true);
					 	     setFormTipoDisabled(true);
					 	     
					 	     if (getBeanGestionCobranza().getIntItemDocCob() != null || getArchivoDocCobranza() != null){
					 	    	  setFormCobrBotonGenDocuDisabled(true);
					 	    	  setFormGenerarDocumentoDisabled(true);
					 	    	  setBotonGrabarDisabled(true);
					 	     }
					 	     
					 	     if (gestionCobranza.getIntParaTipoResultado().equals(Constante.PARAM_T_TIPORESULTADO_PAGO_CAJA))
					    	 {
					    		 setFormGenerarDocumentoDisabled(false);
					    		 setFormCobrBotonGenIngresoDisabled(true);
					    		 setFormCobrBotonGenDocuDisabled(false);
					    	 }else
					    	 if (gestionCobranza.getIntParaTipoResultado().equals(Constante.PARAM_T_TIPORESULTADO_NOTIFICACION)){
								 setFormGenerarDocumentoDisabled(true);
								 setFormCobrBotonGenDocuDisabled(true);
								 setFormCobrBotonGenIngresoDisabled(false);
							 }
							 else{
								 setFormGenerarDocumentoDisabled(false);
							 	 setFormCobrBotonGenDocuDisabled(false);
								 setFormCobrBotonGenIngresoDisabled(false);
									
							}
					    	 
					 	   					 	     
					 	    if (getBeanGestionCobranza().getIntEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				 	    	       deshabilitarFormularioCobranza();
				 	    	       setBotonGrabarDisabled(true);
					 	     }
					 	     else
					 	     {	 
						 	     if (!gestionCobranza.getIntIdPersonaGestor().equals(SESION_IDUSUARIO)){
										  deshabilitarFormularioCobranza();
										  setBotonGrabarDisabled(true);
										  setHabilitarDescarga(false);
										  setBotonValidarDisabled(false);
								 }else
								 if(gestionCobranza.getDtFechaCierre() != null){
									      deshabilitarFormularioCobranza();
										  setBotonGrabarDisabled(true);
										  setHabilitarDescarga(true);
										  setBotonValidarDisabled(false);
							 	 }
								 else
								 {
										  habilitarFormularioCobranza();
										  setBotonValidarDisabled(false);
										  setFormTipoDisabled(true);
										  setBotonGrabarDisabled(false);
										  setHabilitarDescarga(false);
								 }
					 	    }
					 	     
					 	     
				    	}
				    }
				    		 
				     
				 } catch (EJBFactoryException e) {
						e.printStackTrace();
			     } catch (BusinessException e) {
						e.printStackTrace();
				 }
			     
			     
			     setHabilitarCierre(false);
				 setHabilitarFormCierre(false);
			     setHabilitarGestion(true);
		}
    	
    }
    
    public List<Object> listaGestionCobranzaDetalle(){
    	List<Object> listaGesCobDet = new ArrayList<Object>();
      	
    	if (getBeanGestionCobranza().getListaGestionCobranzaEnt() !=null) 
      	for (GestionCobranzaEnt object : getBeanGestionCobranza().getListaGestionCobranzaEnt()) {
    		listaGesCobDet.add(object);
		}
    	
    	if (getBeanGestionCobranza().getListaGestionCobranzaSoc() !=null) 
     	for (GestionCobranzaSoc object : getBeanGestionCobranza().getListaGestionCobranzaSoc()) {
    		listaGesCobDet.add(object);
		} 
    	
    	return listaGesCobDet;
    }
    
    public void eliminarGestionCobranza(ActionEvent event) throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging CobranzaController.eliminarGestionCobranza-------------------------------------");
		GestionCobranza gestionCobranza = null;
		
		if(getBeanListCobranzas() != null && getGestionCobranzaSelected() != null){
			     gestionCobranza = getGestionCobranzaSelected();
			if (!gestionCobranza.getIntIdPersonaGestor().equals(SESION_IDUSUARIO)){
				FacesContextUtil.setMessageError("No puede eliminar las gestiones de otro Usuario.");
			}
			else{	
				
				log.info("getIntPersEmpresaGestionPK: "+getGestionCobranzaSelected().getId().getIntPersEmpresaGestionPK());
				log.info("getIntItemGestionCobranza: "+getGestionCobranzaSelected().getId().getIntItemGestionCobranza());
				
				gestionCobranza.setIntEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				GestionCobranzaFacadeLocal gestioCobranzaFacade = (GestionCobranzaFacadeLocal) EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
				gestionCobranza = gestioCobranzaFacade.elimnarGestionCobranza(gestionCobranza);
				System.out.println("Se actualizó el registro a estado anulado.");
				FacesContextUtil.setMessageSuccess("Se actualizó el registro a estado anulado");
				listarCobranzas(event);
				obtenerGestionCobranza(null);
				
			}
			
			if (getGestionCobranzaSelected().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PROMOCION)){
				if (gestionCobranza.getIntEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
					deshabilitarFormularioPromocion();
				} 
			}
		}
		
		
	}
    
    public void deshabilitarFormularioPromocion(){
    	 setFormTipoDisabled(true);
    	 setFormPromFechaDisabled(true);
		 setFormPromHoraInicioDisabled(true);
	     setFormPromHoraFinDisabled(true);
	     setFormPromMinInicioDisabled(true);
	     setFormPromMinFinDisabled(true);
	     setFormPromContactoDisabled(true);
	     setFormPromObservacionDisabled(true);
	     setFormPromBotonAgregarDisabled(false);
	     setFormPromBotonEliminarDisabled(false);
    }
    
    public void deshabilitarFormularioTramite(){
   	     setFormTramFechaDisabled(true);
		 setFormTramHoraInicioDisabled(true);
	     setFormTramHoraFinDisabled(true);
	     setFormTramMinInicioDisabled(true);
	     setFormTramMinFinDisabled(true);
	     setFormTramContactoDisabled(true);
	     setFormTramObservacionDisabled(true);
	     setFormTramBotonAgregarDisabled(true);
	     setFormTramBotonEliminarDisabled(false);
	     setFormTramSubTipoGestionCobCod(true);
	     setFormTramOpcSocioEntidad(true);
   }
    
    public void deshabilitarFormularioCobranza(){
  	     setFormCobrFechaDisabled(true);
		 setFormCobrHoraInicioDisabled(true);
	     setFormCobrHoraFinDisabled(true);
	     setFormCobrMinInicioDisabled(true);
	     setFormCobrMinFinDisabled(true);
	     setFormCobrContactoDisabled(true);
	     setFormCobrObservacionDisabled(true);
	     setFormCobrBotonAgregarDisabled(true);
	     setFormCobrBotonEliminarDisabled(true);
	     setFormCobrTipoSocioDisabled(true);
	     setFormCobrParaLugarGestionDisabled(true);
	     setFormCobrItemExpedienteDisabled(true);
	     setFormCobrParaTipoResultadoDisabled(true);
	     setFormCobrParaTipoSubResultadoDisabled(true);
	     setFormCobrItemDocCobDisabled(true);
	     setFormCobrBotonQuitarArchDisabled(true);
	     setFormCobrBotonAdjuntarArchDisabled(true);
	     setFormCobrBotonGenDocuDisabled2(true);
  }
    
    public void habilitarFormularioCobranza(){
 	     setFormCobrFechaDisabled(false);
		 setFormCobrHoraInicioDisabled(false);
	     setFormCobrHoraFinDisabled(false);
	     setFormCobrMinInicioDisabled(false);
	     setFormCobrMinFinDisabled(false);
	     setFormCobrContactoDisabled(false);
	     setFormCobrObservacionDisabled(false);
	     setFormCobrBotonAgregarDisabled(false);
	     setFormCobrBotonEliminarDisabled(false);
	     setFormCobrTipoSocioDisabled(false);
	     setFormCobrParaLugarGestionDisabled(false);
	     setFormCobrItemExpedienteDisabled(false);
	     setFormCobrParaTipoResultadoDisabled(false);
	     setFormCobrParaTipoSubResultadoDisabled(false);
	     setFormCobrItemDocCobDisabled(false);
	     setFormCobrBotonQuitarArchDisabled(false);
	     setFormCobrBotonAdjuntarArchDisabled(false);
	     setHabilitarDescarga(false);
	     setFormCobrBotonGenDocuDisabled2(false);
	  
 }
    
    public void habilitarFormularioTramite(){
  	     setFormTramFechaDisabled(false);
		 setFormTramHoraInicioDisabled(false);
	     setFormTramHoraFinDisabled(false);
	     setFormTramMinInicioDisabled(false);
	     setFormTramMinFinDisabled(false);
	     setFormTramContactoDisabled(false);
	     setFormTramObservacionDisabled(false);
	     setFormTramBotonAgregarDisabled(false);
	     setFormTramBotonEliminarDisabled(true);
	     setFormTramSubTipoGestionCobCod(false);
	     setFormTramOpcSocioEntidad(false);
  }
    
    public void habilitarFormularioPromocion(){
    	 setFormTipoDisabled(false);
    	 setFormPromFechaDisabled(false);
		 setFormPromHoraInicioDisabled(false);
	     setFormPromHoraFinDisabled(false);
	     setFormPromMinInicioDisabled(false);
	     setFormPromMinFinDisabled(false);
	     setFormPromContactoDisabled(false);
	     setFormPromObservacionDisabled(false);
	     setFormPromBotonAgregarDisabled(true);
	     setFormPromBotonEliminarDisabled(true);
    	
   }
    
    public boolean validarGestionCobranza(){
    	
    	boolean isValidCobranza = true;
		FacesContextUtil.setMessageError("");
		
		if(getBeanGestionCobranza() != null){
		
			if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(0)){
				isValidCobranza = false;
				FacesContextUtil.setMessageError("Debe seleccionar el campo Tipo de Gestion.");
			}
			if (getBeanGestionCobranza().getDtFechaGestion() == null){
				isValidCobranza = false;
				FacesContextUtil.setMessageError("Debe completar el campo Fecha de Gestion.");
			}
			
			if (getBeanGestionCobranza().getDtFechaGestion() != null && !esValidoFechaGestion(getBeanGestionCobranza().getDtFechaGestion())){
				isValidCobranza = false;
				FacesContextUtil.setMessageError("Solo permite el ingreso de la fecha actual o del día siguiente.");
			}
			if (getBeanGestionCobranza().getDtFechaGestion() != null && tieneGestionesPendientes()){
					isValidCobranza = false;
					FacesContextUtil.setMessageError("Tiene Gestiones Pendientes.");
			}

			if (getBeanGestionCobranza().getDtFechaGestion() != null && getIntHoraFin() != null &&  getIntHoraInicio() == null){
				isValidCobranza = false;
				FacesContextUtil.setMessageError("Debe completar el campo Hora Inicio.");
			}
			
			if (getIntHoraInicio() != null && getIntHoraFin() != null){
				   if (getIntHoraFin() < getIntHoraInicio()){
					   FacesContextUtil.setMessageError("La Hora de Inicio debe ser menor la Hora de Fin");
					   isValidCobranza = false;
				   }
				   
				   if (getIntHoraFin() == getIntHoraInicio()){
					   
					   if (getIntMinFin() == null)setIntMinFin(0);
					   if (getIntMinInicio() == null)setIntMinInicio(0);
					   
					   if (getIntMinFin() <= getIntMinInicio()){
						   FacesContextUtil.setMessageError("La Hora de Inicio debe ser menor la Hora de Fin");
						   isValidCobranza = false;
					   }   
				   }
			 }
			
			
			if (getBeanGestionCobranza().getStrObservacion().equals("")){
				isValidCobranza = false;
				FacesContextUtil.setMessageError("Debe completar el campo Observación.");
			}
			if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PROMOCION)){
				if (!existeListaGestionCobEnt()){
					isValidCobranza = false;
					FacesContextUtil.setMessageError("Debe seleccionar al menos una Entidad.");
				}
			}else{
				if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_TRAMITEADMIN)){		
					if (!existeListaGestionCobDet()){
						isValidCobranza = false;
						FacesContextUtil.setMessageError("Debe seleccionar al menos una Entidad/Socio.");
					}
				}
				else{
					if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_CHEQUE)){
						
						if (!existeListaGestionCobEnt()){
							isValidCobranza = false;
							FacesContextUtil.setMessageError("Debe seleccionar al menos una Entidad.");
						}
						
					}else{
					
						if (!existeListaGestionCobDet()){
							isValidCobranza = false;
							FacesContextUtil.setMessageError("Debe seleccionar un Socio.");
						}
						if (getIntItemExpediente().equals(0)){
							isValidCobranza = false;
							FacesContextUtil.setMessageError("Debe seleccionar el Tipo de Credito.");
						}
					}		
			   }	
			  
		    }		
		}	
		
    	return isValidCobranza;
    }
    
    
    public boolean tieneGestionesPendientes(){
    	boolean result = false;
    	
    	
    	GestionCobranza busqGestionCobranza = new GestionCobranza();
		   
	    busqGestionCobranza.setId(new GestionCobranzaId());
	    busqGestionCobranza.getId().setIntPersEmpresaGestionPK(SESION_IDEMPRESA);
	    busqGestionCobranza.setIntIdPersonaGestor(SESION_IDUSUARIO);
	    busqGestionCobranza.setIntTipoGestionCobCod(0);
	    busqGestionCobranza.setIntSucursal(-1);
	    busqGestionCobranza.setStrNroDocuIdentidad("");
	    Date fechaAtenrior = sumarFechasDias(getBeanGestionCobranza().getDtFechaGestion(),-1);
	    busqGestionCobranza.setDtFechaGestionFin(fechaAtenrior);
	    
	    ArrayList<GestionCobranza> listaGestionCobranza = new ArrayList();
	    ArrayList<GestionCobranza> listaGestionCobranzaFiltra = new ArrayList();
	    ArrayList<GestionCobranza> listaGestionPendientes = new ArrayList();
	    
	    
	    try {
			GestionCobranzaFacadeLocal GestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
			listaGestionCobranza = (ArrayList) GestionCobranzaFacade.getListaGestionCobranza(busqGestionCobranza);
			listaGestionCobranzaFiltra = (ArrayList) GestionCobranzaFacade.getListaGestionCobranza(busqGestionCobranza);
			
			for (GestionCobranza gcob : listaGestionCobranza) {
			     GestionCobranza cob =  obtenerGestionCobranzaPediente(gcob.getDtFechaGestion(),listaGestionCobranzaFiltra,listaGestionPendientes);
			     
			     if (cob != null)
			     listaGestionPendientes.add(cob);
			}
			
		 } catch (EJBFactoryException e) {
				e.printStackTrace();
	     } catch (BusinessException e) {
				e.printStackTrace();
		 }
	     
	     if (listaGestionPendientes.size() >= 2)
	     {
	    	 result = true;
	     }
	    
	   return  result; 
    }
 
    
    public GestionCobranza obtenerGestionCobranzaPediente(Date pfechaGestion ,List<GestionCobranza> lista,ArrayList<GestionCobranza> listaPend){
    	
    	GestionCobranza g = null;
    	for(GestionCobranza gcob:lista){
    		
    		String fechaGestion = UtilCobranza.convierteDateAString(gcob.getDtFechaGestion());
    		String parFchaGestion = UtilCobranza.convierteDateAString(pfechaGestion);
    		
    		if (gcob.getIntEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) && fechaGestion.equals(parFchaGestion) && gcob.getDtFechaCierre() == null){
    			 g = gcob;
    			lista.remove(gcob);
    			
    			if (existeGestionPendiente(listaPend, parFchaGestion))
    			    return null;
    			else
    				return g;
    			
    		}
    	}
    	return g;
    }
    
    public boolean existeGestionPendiente(ArrayList<GestionCobranza> listaGestionPendientes,String parFchaGestion){
    
    	boolean result = false;
    	
    	for (GestionCobranza gesPend : listaGestionPendientes) {
    		String fechaGestion = UtilCobranza.convierteDateAString(gesPend.getDtFechaGestion());
    		if (gesPend.getIntEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)&&
    				                 fechaGestion.equals(parFchaGestion)&& 
    				                  gesPend.getDtFechaCierre() == null ){
    			result = true;
    			break;
    		}
		}
    	
    	return result;
    }
    
    
    public boolean existeListaGestionCobEnt(){
     
    	List<GestionCobranzaEnt> liataGestCobEnt = new ArrayList<GestionCobranzaEnt>();
    	
    	if (getBeanGestionCobranza().getListaGestionCobranzaEnt().size() > 0){
    		
    		for (GestionCobranzaEnt gestCobEnt : getBeanGestionCobranza().getListaGestionCobranzaEnt()) {
    			
    			   if (gestCobEnt.getIsDeleted() == null || !gestCobEnt.getIsDeleted()){
    				   liataGestCobEnt.add(gestCobEnt);
    			   }
			}  
    	}
    	
    	if (liataGestCobEnt.size() > 0){
    		return true;
    	}
    		
    	return false;
    }
    
    public boolean existeListaGestionCobDet(){
        
    	List<Object> liataGestCobDet = new ArrayList<Object>();
    	
    	if (getBeanGestionCobranza().getListaGestionCobranzaDetalle().size() > 0){
    		
    		for (int i = 0; i < getBeanGestionCobranza().getListaGestionCobranzaDetalle().size(); i++) {
    			
    			if (getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(i) instanceof GestionCobranzaEnt){
    				GestionCobranzaEnt gestCobEnt = (GestionCobranzaEnt)getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(i);
    				if (gestCobEnt.getIsDeleted() == null || !gestCobEnt.getIsDeleted()){
    					liataGestCobDet.add(gestCobEnt);
     			    }
    			}else{
    				GestionCobranzaSoc gestCobSoc = (GestionCobranzaSoc)getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(i);
    				if (gestCobSoc.getIsDeleted() == null || !gestCobSoc.getIsDeleted()){
    					liataGestCobDet.add(gestCobSoc);
     			    }
    			}
			}
    	}
    	
    	if (liataGestCobDet.size() > 0){
    		return true;
    	}
    		
    	return false;
    }
    
    
    public boolean existeGestorCobranza(GestorCobranzaId id){
    	
    	boolean result = false;
		    try{
		    	GestionCobranzaFacadeLocal gestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
		    	GestorCobranza gestorCobranza = gestionCobranzaFacade.existeGestorCobranza(id);     
		    	
		    	if (gestorCobranza == null){
		    		result = false;
		    	}else
		    	{
		    		result = true;
		    		getBeanGestionCobranza().setGestorCobranza(gestorCobranza);
		    	}
		    	                      
		        } catch (EJBFactoryException e) {
				e.printStackTrace();
			    } catch (BusinessException e) {
				e.printStackTrace();
			}
    	return result;
    }
    
    
    public void cancelarGrabarCobranza(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging ModeloController.cancelarGrabarCobranza-------------------------------------");

		 setFormCobranzaEnabled(true);
		 setCobranzaRendered(false);
		 limpiarFormCobranza();
		    
	}
  
    public void habilitarGrabarCobranza(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.habilitarGrabarCobranza-----------------------------");
		inicio(null);
		habilitarFormularioPromocion();
		
		setFormTramiteEnabled(false);
		setFormPromocionEnabled(false);
	    setFormCobranzaEnabled(false);
	    setFormCobranzaDisabled(false);
	    setFormGenerarDocumentoDisabled(false);
	    
	    setCobranzaRendered(true);
	    limpiarFormCobranza();
	    setStrTipoMantCobranza(Constante.MANTENIMIENTO_GRABAR);
	    setBotonValidarDisabled(true);
	    setBotonGrabarDisabled(false);
	    setHabilitarGestion(true);
	    setHabilitarCierre(false);
	    setHabilitarFormCierre(false);
	    
	    //Cobranza Cheque
	    
	    setHabilitarBotonGeneararIngreso(false);
	    setFormBotonBusqCobrChDisabled(false);
	    setFormBotonGenIngCobrChDisabled(false);
	    setFormCobrChTipoResDisabled(false);
	     
	}
    public void seleccionarTipoGestionCobranza(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.seleccionarTipoGestionCobranza-----------------------------");
		     inicio(null);
	     	 boolean esGestorCobranza = true;
	     	 
	     	 //Deshabilitamos los formularios
		     setFormPromocionEnabled(false);
		     setFormTramiteEnabled(false);
		     setFormCobranzaEnabled(false);
		     
		     
		     GestorCobranzaId ids = new GestorCobranzaId();
			 ids.setIntPersEmpresaPk(SESION_IDEMPRESA);
			 ids.setIntPersPersonaGestorPk(SESION_IDUSUARIO);
			 getBeanGestionCobranza().setGestorCobranza(new GestorCobranza());
			 getBeanGestionCobranza().getGestorCobranza().setId(ids);
			 
			 if (existeGestorCobranza(ids)){
				    setHabilitarBotones(true); 
			 }
			 else{
				    setHabilitarBotones(false); 
//					MessageController message = (MessageController)getSessionBean("messageController");
//		 			message.setWarningMessage("Usuario no esta registrado como Gestor de Cobranza.");
		 			FacesContextUtil.setMessageError("Usuario no esta registrado como Gestor de Cobranza.");
		 			esGestorCobranza = false;
			}    
		     
			if (esGestorCobranza) {
					if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PROMOCION) || getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_CHEQUE)){
						setFormPromocionEnabled(true);
						setFormTramiteEnabled(false);
						setFormCobranzaEnabled(false);
						
						setBotonValidarDisabled(false);
						setFormTipoDisabled(true);
					}else{
						if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_TRAMITEADMIN)){
								setFormTramiteEnabled(true);
								setFormPromocionEnabled(false);
								setFormCobranzaEnabled(false);
								
								setBotonValidarDisabled(false);
								setFormTipoDisabled(true);
						}		
						else{
							if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_ACTIVA) || getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PASIVA)){
								
							    setFormGenerarDocumentoDisabled(false);
							    setFormCobrBotonGenIngresoDisabled(false);
							    setFormCobrBotonGenDocuDisabled(false);
								setFormTramiteEnabled(false);
								setFormPromocionEnabled(false);
								setFormCobranzaEnabled(true);
								getBeanGestionCobranza().setIntOpcSocioEntidad(TIPO_BUSQ_SOCIO);
								
								setBotonValidarDisabled(false);
								setFormTipoDisabled(true);
							}	
						}
					}
			}
		  
	}
    
    
    
    public void limpiarFormCobranza(){
		log.info("-----------------------Debugging SucursalController.limpiarFormGestionCobranza-----------------------------");
		GestionCobranza gestionCobranza = new GestionCobranza();
		gestionCobranza.setId(new GestionCobranzaId());
		gestionCobranza.setListaGestionCobranzaEnt(new ArrayList<GestionCobranzaEnt>());
		
		setBeanGestionCobranza(gestionCobranza);
		setArchivoDocCobranza(null);
		setIntHoraInicio(null);
		setIntMinInicio(00);
		setIntHoraFin(null);
		setIntMinFin(00);
		archivoDocCobranzaTemp = null;
		setIntItemExpediente(0);
		this.listaExpCredito = null;
		this.listaSubResultado = null;
		
		habilitarFormularioCobranza();
		habilitarFormularioTramite();
		habilitarFormularioPromocion();
		
	}
   
    //Popup
    public void buscarEntidad(ActionEvent event){
    	  log.info("-----------------------Debugging cobranzaController.buscarEntidad-----------------------------");
    	
    	  log.info("SESION_IDSUCURSAL  : "+SESION_IDSUCURSAL);
    	  log.info("SESION_IDSUBCURSAL : "+SESION_IDSUBCURSAL);
    	  log.info("strRuc:"+getStrRuc());
    	  log.info("strDesEntidad:"+getStrRuc());
      	  log.info("intTipoBusqueda:"+getIntTipoBusqueda());
    	
    	  EstructuraComp estructuraComp = new EstructuraComp();
    	  
    	  EstructuraDetalle ed = new EstructuraDetalle();
    	  EstructuraDetalleId detId = new EstructuraDetalleId();
    	                      detId.setIntCaso(3);
    	  ed.setId(detId);
    	  ed.setIntSeguSucursalPk(SESION_IDSUCURSAL);
    	  ed.setIntSeguSubSucursalPk(SESION_IDSUBCURSAL);
    	  
    	  estructuraComp.setEstructuraDetalle(ed);
    	  Estructura estructura = new Estructura();
    	  EstructuraId id = new EstructuraId();
    	 // id.setIntNivel(2);
    	  estructura.setId(id);
    	  Juridica   juridica = new Juridica();
    	  
    	  if (getIntTipoBusqueda() != null && !getIntTipoBusqueda().equals(TIPO_BUSQ_DESCRIPCION)){
    		  juridica.setStrRazonSocial("");
    	  }
    	  else
    	  {
    		  juridica.setStrRazonSocial(getStrRuc()!=null?getStrRuc().trim():""); 
    	  }
    	  
       	  pe.com.tumi.persona.core.domain.Persona    persona = new pe.com.tumi.persona.core.domain.Persona();
       	  
          if (getIntTipoBusqueda()!= null && !getIntTipoBusqueda().equals(TIPO_BUSQ_DNI)){
    	      persona.setStrRuc("");
          }
          else
          {
        	 persona.setStrRuc(getStrRuc()!=null?getStrRuc().trim():"");
          }
    	  
    	  juridica.setPersona(persona);
    	  estructura.setJuridica(juridica);
    	  estructuraComp.setEstructura(estructura);
    	  estructuraComp.setOpcionBusquedaCobranza(1);
    	
    	  try{
		    	EstructuraFacadeRemote estructuraFacade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
		    
		    	if (getIntTipoBusqueda() != null && getIntTipoBusqueda().equals(TIPO_BUSQ_DNI)){
		    		if(getStrRuc() != null && getStrRuc().trim().length() > 0){
		    		    this.listaEntidad = filtrarEntidadPorDNI(estructuraFacade.getListaEstructuraComp(estructuraComp));
		    		}
		    		else
		    		{
		    			this.listaEntidad = estructuraFacade.getListaEstructuraComp(estructuraComp);
		    		}
		    	}
		    	else
		    	{
		    		this.listaEntidad = estructuraFacade.getListaEstructuraComp(estructuraComp);
		    	}
		      } catch (EJBFactoryException e) {
			  	e.printStackTrace();
			  } catch (BusinessException e) {
				e.printStackTrace();
			  }
			    
    }
    
    
    
    
    public void buscarEntidadSocio(ActionEvent event){
    	
    	if (getBeanGestionCobranza().getIntOpcSocioEntidad().equals(TIPO_BUSQ_ENTIDAD)){
    		
    		buscarEntidad(event);
    	}
    	else
    	{
    		 try{
 		    	SocioFacadeRemote socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
 		    
 		    	
 		    	SocioComp socioComp = new SocioComp();
 		    	socioComp.setPersona( new Persona());
 		    	
 		    	socioComp.getPersona().setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
 		        socioComp.setIntTipoSucBusq(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_SOCIO);
 		    	socioComp.setIntSucursalBusq(SESION_IDSUCURSAL);
 		    	socioComp.setIntSubsucursalBusq(0);
 		    	socioComp.setStrNombrePersona(" ");
 		    	
 		    	if (getIntTipoBusqueda() != null && getIntTipoBusqueda().equals(TIPO_BUSQ_DNI)){
 		    		 socioComp.setStrDocIdentidad(getStrDni());
 		    	}
 		    	else{
 		    		 socioComp.setStrNombrePersona(getStrDni()!=null?getStrDni():" ");
 	 		 	}
 		    	
 		    	socioComp.setIntFechaBusq(0);
 		    	socioComp.setSocio(new Socio());
 		    	socioComp.getSocio().setId(new SocioPK());
 		    	socioComp.getSocio().getId().setIntIdEmpresa(SESION_IDEMPRESA);
 		    	socioComp.getSocio().setSocioEstructura(new SocioEstructura());
 		    	
 		    	if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_TRAMITEADMIN)){
 		    	   socioComp.getSocio().getSocioEstructura().setIntTipoSocio(Constante.PARAM_T_TIPOSOCIO_TODOS);
 		    	}
 		    	else{
 		    		socioComp.getSocio().getSocioEstructura().setIntTipoSocio(1);
 		    	}
 		    	
 		    	socioComp.getSocio().getSocioEstructura().setIntModalidad(Constante.PARAM_T_MODALIDADPLANILLA_TODOS);
 		    	
 		    	
 		    	if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_TRAMITEADMIN)){
 		    		
 		    		this.listaSocio = socioFacade.getListaSocioComp(socioComp);
 		    	}
 		    	else{
 		    	   if (getIntTipoSocio().equals(Constante.PARAM_T_TIPO_SOCIO_TITTULAR)){
 		    		  
 		    		  this.listaSocio = listaSociosMorosos(socioFacade.getListaSocioComp(socioComp));
 	 		       }else{
 	 		    	  this.listaSocio  = listaSociosGarantes(socioFacade.getListaSocioComp(socioComp));
 		    	   }
 		    	}
 		      } catch (EJBFactoryException e) {
 			  	e.printStackTrace();
 			  } catch (BusinessException e) {
 				e.printStackTrace();
 			  }
    		 
    	}
    
    }
    
    
    public List<SocioComp> listaSociosMorosos(List<SocioComp> listaSociosComp)throws EJBFactoryException,BusinessException{
    	
    	List<SocioComp> listaSociosMorosos = new ArrayList<SocioComp>();
    	CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
    	
    	for (SocioComp socioComp : listaSociosComp) {
			
    		List<CuentaIntegrante> listaCueInt = cuentaFacade.getCuentaIntegrantePorIdPersona(socioComp.getSocio().getId().getIntIdPersona(),socioComp.getSocio().getId().getIntIdEmpresa());
    		
    		if (listaCueInt != null && listaCueInt.size() > 0)
    		   {	
		    	for (CuentaIntegrante cuentaInt : listaCueInt) {
				   CuentaId cuentaId = new CuentaId();
		    		cuentaId.setIntCuenta(cuentaInt.getId().getIntCuenta());
		    		cuentaId.setIntPersEmpresaPk(cuentaInt.getId().getIntPersEmpresaPk());
		    		Cuenta cuenta= (Cuenta)cuentaFacade.getCuentaPorId(cuentaId);
		    		if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PASIVA)){
		    			
		    			if (cuenta != null && cuenta.getIntParaCondicionCuentaCod().equals(Constante.PARAMA_T_CTA_ESTADO_MORROSO)){
			    			socioComp.setCuenta(cuenta);
			    		    listaSociosMorosos.add(socioComp);
			    		    break;
			    		}
		    	    }	
		    		else{
		    	   		if (cuenta != null && cuenta.getIntParaSubCondicionCuentaCod().equals(Constante.PARAM_T_TIPO_CONDSOCIOREGULAR)){
			    			socioComp.setCuenta(cuenta);
			    		    listaSociosMorosos.add(socioComp);
			    		    break;
			    		}
		    		}	
		    	}	
    		  }
		}
    	                                                                  
    	return listaSociosMorosos;
    }
    
    public List<SocioComp> listaSociosGarantes(List<SocioComp> listaSocios) throws EJBFactoryException,BusinessException{
    	List<SocioComp> listaSociosGarantes = new ArrayList<SocioComp>();
    	
    	SolicitudPrestamoFacadeRemote facadeSolPres = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
    	CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
    	
    	
    	for (SocioComp socioComp : listaSocios) {
    		List<GarantiaCredito> listaGaratiaCredito =  facadeSolPres.getListaGarantiasPorPkPersona(socioComp.getSocio().getId().getIntIdEmpresa(), socioComp.getSocio().getId().getIntIdPersona());
    		   for (GarantiaCredito garantiaCredito : listaGaratiaCredito) {
    			    CuentaId cuentaId = new CuentaId();
		    		cuentaId.setIntCuenta(garantiaCredito.getId().getIntCuentaPk());
		    		cuentaId.setIntPersEmpresaPk(garantiaCredito.getId().getIntPersEmpresaPk());
		    		Cuenta cuenta= (Cuenta)cuentaFacade.getCuentaPorId(cuentaId);
		    		
		    		if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PASIVA)){
		    			 if (cuenta.getIntParaCondicionCuentaCod().equals(Constante.PARAMA_T_CTA_ESTADO_MORROSO)){
				    			socioComp.setCuenta(cuenta);
				    			listaSociosGarantes.add(socioComp);
				    			break;
				    	   }
		    		}else{
		    		   if (cuenta.getIntParaSubCondicionCuentaCod().equals(Constante.PARAM_T_TIPO_CONDSOCIOREGULAR)){
			    			socioComp.setCuenta(cuenta);
			    			listaSociosGarantes.add(socioComp);
			    			break;
			    	   }
		    		}
    		    }
    	}
			
    	return listaSociosGarantes;
    	
    }
    
    
    public void seleccionarEntidadSocio(ValueChangeEvent event){
    	log.info("-----------------------Debugging cobranzaController.seleccionarEntidadSocio------------------------");
 		String nroEntidadSocio = (String) event.getNewValue();
		log.info("nroEntidad: "+nroEntidadSocio);
		
		if (getBeanGestionCobranza().getIntOpcSocioEntidad().equals(TIPO_BUSQ_ENTIDAD)){
	  
			if(listaEntidad!=null){
				EstructuraComp estructuraComp = listaEntidad.get(Integer.valueOf(nroEntidadSocio));
				GestionCobranzaEnt gCobEnt = new  GestionCobranzaEnt();
				gCobEnt.setEstructura(estructuraComp.getEstructura());
				if (!existeEntidadDosSeleccionado(estructuraComp.getEstructura().getId())){
				    getBeanGestionCobranza().getListaGestionCobranzaDetalle().add(gCobEnt);
				}
			}
		}
		else{
			if(listaSocio!=null){
				SocioComp socioComp = listaSocio.get(Integer.valueOf(nroEntidadSocio));
				GestionCobranzaSoc gCobSoc = new  GestionCobranzaSoc();
				gCobSoc.setSocioComp(socioComp);
				gCobSoc.setSocio(socioComp.getSocio());
				if (!existeSocioSeleccionado(socioComp.getSocio().getId())){
					if(getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_TRAMITEADMIN)){
						getBeanGestionCobranza().getListaGestionCobranzaDetalle().add(gCobSoc);
					}else{
						
						if (getBeanGestionCobranza().getId() != null && getBeanGestionCobranza().getId().getIntItemGestionCobranza() != null){
							 if (getBeanGestionCobranza().getListaGestionCobranzaDetalleTemp() == null){
								 getBeanGestionCobranza().setListaGestionCobranzaDetalleTemp(getBeanGestionCobranza().getListaGestionCobranzaDetalle());
							 }
					 	}
						
						getBeanGestionCobranza().getListaGestionCobranzaDetalle().clear();
						getBeanGestionCobranza().getListaGestionCobranzaDetalle().add(gCobSoc);
						getBeanGestionCobranza().setStrSocioNombre(gCobSoc.getSocio().getStrNombreSoc()+" "+gCobSoc.getSocio().getStrApePatSoc()+" "+gCobSoc.getSocio().getStrApeMatSoc());
						
						if (getIntTipoSocio().equals(Constante.PARAM_T_TIPO_SOCIO_TITTULAR)){
							listaExpedienteCreditoPorTitular();
						}else{
							listaExpedienteCreditoPorGarante();
						}
						
					}
			   }
			}
		}
			
		setCobranzaRendered(true);
		setFormCobranzaEnabled(true);
    }
    
    
    
    
  public void eliminarGestionCobranzaEnt(ActionEvent event) {
	    log.info("-------------------------------------Debugging cobranzaController.eliminarGestionCobranzaEnt-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowGestionCobranzaEnt"));
		String selectedRow = getRequestParameter("rowGestionCobranzaEnt");
		if(beanGestionCobranza!=null && beanGestionCobranza.getListaGestionCobranzaEnt()!=null && beanGestionCobranza.getListaGestionCobranzaEnt().size()>0){
			
			GestionCobranzaEnt gesCobEnt = beanGestionCobranza.getListaGestionCobranzaEnt().get(Integer.valueOf(selectedRow));
			                   gesCobEnt.setIsDeleted(true);
			 try{
				  beanGestionCobranza.getListaGestionCobranzaEnt().remove(gesCobEnt);
				 //beanGestionCobranza.getListaGestionCobranzaEnt().set(Integer.valueOf(selectedRow),gesCobEnt);
				  
				 GestionCobranzaFacadeLocal gestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
			    	
				 gestionCobranzaFacade.eliminarListaGestionCobranzaEnt(gesCobEnt);
		     } catch (EJBFactoryException e) {
				  	e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
		    }
			                   
		             
			
		}
  }
  
  public void eliminarGestionCobranzaDet(ActionEvent event){

	  log.info("-------------------------------------Debugging cobranzaController.eliminarGestionCobranzaDet-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowGestionCobranzaEnt"));
		String selectedRow = getRequestParameter("rowGestionCobranzaEnt");
		
		if(beanGestionCobranza != null && 
		   beanGestionCobranza.getListaGestionCobranzaDetalle() != null && 
		   beanGestionCobranza.getListaGestionCobranzaDetalle().size() > 0){
		
			if (beanGestionCobranza.getListaGestionCobranzaDetalle().get(Integer.valueOf(selectedRow)) instanceof GestionCobranzaEnt){
				GestionCobranzaEnt gesCobEnt = (GestionCobranzaEnt)beanGestionCobranza.getListaGestionCobranzaDetalle().get(Integer.valueOf(selectedRow));
				 gesCobEnt.setIsDeleted(true);
				beanGestionCobranza.getListaGestionCobranzaDetalle().remove(gesCobEnt);
				try{
					 GestionCobranzaFacadeLocal gestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
					 gestionCobranzaFacade.eliminarListaGestionCobranzaEnt(gesCobEnt);
			    } catch (EJBFactoryException e) {
					  	e.printStackTrace();
				} catch (BusinessException e) {
					e.printStackTrace();
			    }
			}else{
				  GestionCobranzaSoc gesCobSoc = (GestionCobranzaSoc)beanGestionCobranza.getListaGestionCobranzaDetalle().get(Integer.valueOf(selectedRow));
				  gesCobSoc.setIsDeleted(true);
				  beanGestionCobranza.getListaGestionCobranzaDetalle().remove(gesCobSoc);
				 try{
					 GestionCobranzaFacadeLocal gestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
					 gestionCobranzaFacade.eliminarGestionCobranzaSoc(gesCobSoc);
			     } catch (EJBFactoryException e) {
					  	e.printStackTrace();
				} catch (BusinessException e) {
					e.printStackTrace();
			    }
			}
		}	
	  
  }
  
  
	
	
    
   public List<EstructuraComp> filtrarEntidadPorDNI(List<EstructuraComp> listaEstructuraComp){
    	
    	List<EstructuraComp> lista =new ArrayList<EstructuraComp>();
    	if (listaEstructuraComp != null)
    	{
    	   	for (EstructuraComp estructuraComp : listaEstructuraComp) {
    	   		
    	   		if(getStrRuc().equals(estructuraComp.getEstructura().getJuridica().getPersona().getStrRuc())){
    	   			lista.add(estructuraComp);
    	   			return lista;
    	   		}
			} 
    	}
    	return lista;
    }
    
    
    
    public void seleccionarEntidad(ValueChangeEvent event){
    	log.info("-----------------------Debugging cobranzaController.seleccionarEntidad------------------------");
    	
    	if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PROMOCION) || getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_CHEQUE)){
    	
	 		String nroEntidad = (String) event.getNewValue();
			log.info("nroEntidad: "+nroEntidad);
			if(listaEntidad!=null){
				EstructuraComp estructuraComp = listaEntidad.get(Integer.valueOf(nroEntidad));
				GestionCobranzaEnt gCobEnt = new  GestionCobranzaEnt();
				gCobEnt.setEstructura(estructuraComp.getEstructura());
				
				if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_CHEQUE)){
					  getBeanGestionCobranza().getListaGestionCobranzaEnt().clear();
					  getBeanGestionCobranza().getListaGestionCobranzaEnt().add(gCobEnt);
					  GestionCobranzaEnt gCobEnt2 = (GestionCobranzaEnt)getBeanGestionCobranza().getListaGestionCobranzaEnt().get(0);
	            	  Integer codigo         = gCobEnt2.getEstructura().getId().getIntCodigo();
	            	  String  desRazonSocial = gCobEnt2.getEstructura().getJuridica().getStrRazonSocial();
	            	  String  ruc            = gCobEnt2.getEstructura().getJuridica().getPersona().getStrRuc();
	            	  getBeanGestionCobranza().setStrDescEntidad(codigo+"-"+desRazonSocial+"-RUC:"+ruc);
	    		}
				else{
					if (!existeEntidadSeleccionado(estructuraComp.getEstructura().getId())){
					    getBeanGestionCobranza().getListaGestionCobranzaEnt().add(gCobEnt);
					}
				}
				 
			}
           
    		setCobranzaRendered(true);
    		setFormCobranzaEnabled(false);
    		setFormTramiteEnabled(false);
    		setFormPromocionEnabled(true);
    		
				
    	}else{
    		
    		if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_TRAMITEADMIN)){
    	    	
    			seleccionarEntidadSocio(event);
    			setCobranzaRendered(true);
    			setFormCobranzaEnabled(false);
    			setFormTramiteEnabled(true);
    			setFormPromocionEnabled(false);
        	}    		
    		else{
        		
        		seleccionarEntidadSocio(event);
        		setCobranzaRendered(true);
    			setFormCobranzaEnabled(true);
    			setFormTramiteEnabled(false);
    			setFormPromocionEnabled(false);
        	}
    		
    	}
    }
    
    public boolean existeEntidadSeleccionado(EstructuraId id){
    	
    	if (getBeanGestionCobranza().getListaGestionCobranzaEnt() != null){
    	
    		for (GestionCobranzaEnt gesCobEnt : getBeanGestionCobranza().getListaGestionCobranzaEnt()) {
				if(gesCobEnt.getEstructura().getId().getIntCodigo().equals(id.getIntCodigo()) && gesCobEnt.getEstructura().getId().getIntNivel().equals(id.getIntNivel()))
    			{
    				return true;
    			}
			}
    	}
    	
    	return false;
    }
    
    public boolean existeEntidadDosSeleccionado(EstructuraId id){
    	
    	if (getBeanGestionCobranza().getListaGestionCobranzaDetalle() != null && getBeanGestionCobranza().getListaGestionCobranzaDetalle().size() > 0){
    		for (int i = 0; i < getBeanGestionCobranza().getListaGestionCobranzaDetalle().size();i++){
    			if (getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(i) instanceof GestionCobranzaEnt){
   				   GestionCobranzaEnt gesCobEnt = (GestionCobranzaEnt)getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(i);
    				if(gesCobEnt.getEstructura().getId().getIntCodigo().equals(id.getIntCodigo()) && gesCobEnt.getEstructura().getId().getIntNivel().equals(id.getIntNivel()))
        			{
        				return true;
        			}
    			}
    		}	
    	}
    	
    	return false;
    }
    
   public boolean existeSocioSeleccionado(SocioPK id){
    	
    	if (getBeanGestionCobranza().getListaGestionCobranzaDetalle() != null && getBeanGestionCobranza().getListaGestionCobranzaDetalle().size() > 0){
    		for (int i = 0; i < getBeanGestionCobranza().getListaGestionCobranzaDetalle().size();i++){
    			if (getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(i) instanceof GestionCobranzaSoc){
   				   GestionCobranzaSoc gesCobSoc = (GestionCobranzaSoc)getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(i);
    				if(gesCobSoc.getSocio().getId().getIntIdEmpresa().equals(id.getIntIdEmpresa()) && 
    				   gesCobSoc.getSocio().getId().getIntIdPersona().equals(id.getIntIdPersona())){
        				return true;
        			}
    			}
    		}	
    	}
    	
    	return false;
    }
    
    
    /*
    public void onchangueSucursalSondeo(ActionEvent event){
    	//TODO se tiene idPersona de la sucursal falta traer el id de la sucursal cuyda idpersona corresponda
    	beanZonal.sucursal.id.intIdSucursal
    }
    */
    
    //Mensajes
    
    public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	
	protected void setMessageError(String error) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
	}
    
   
    //Parametros de busqueda
    
	public Integer getIntCboTipoGestionCobranza() {
		return intCboTipoGestionCobranza;
	}

	public GestionCobranza getBeanGestionCobranza() {
		return beanGestionCobranza;
	}

	public void setBeanGestionCobranza(GestionCobranza beanGestionCobranza) {
		this.beanGestionCobranza = beanGestionCobranza;
	}

	public boolean isFormCobranzaEnabled() {
		return formCobranzaEnabled;
	}

	public void setFormCobranzaEnabled(boolean formCobranzaEnabled) {
		this.formCobranzaEnabled = formCobranzaEnabled;
	}

	public boolean isBolGestionCobranza() {
		return bolGestionCobranza;
	}

	public void setBolGestionCobranza(boolean bolGestionCobranza) {
		this.bolGestionCobranza = bolGestionCobranza;
	}

	
	public String getStrTipoMantCobranza() {
		return strTipoMantCobranza;
	}

	public void setStrTipoMantCobranza(String strTipoMantCobranza) {
		this.strTipoMantCobranza = strTipoMantCobranza;
	}

	public boolean isFormCobranzaDisabled() {
		return formCobranzaDisabled;
	}

	public void setFormCobranzaDisabled(boolean formCobranzaDisabled) {
		this.formCobranzaDisabled = formCobranzaDisabled;
	}

	public boolean isCobranzaRendered() {
		return cobranzaRendered;
	}

	public void setCobranzaRendered(boolean cobranzaRendered) {
		this.cobranzaRendered = cobranzaRendered;
	}

	public void setIntCboTipoGestionCobranza(Integer intCboTipoGestionCobranza) {
		this.intCboTipoGestionCobranza = intCboTipoGestionCobranza;
	}

	public Integer getIntCboSucursal() {
		return intCboSucursal;
	}

	public void setIntCboSucursal(Integer intCboSucursal) {
		this.intCboSucursal = intCboSucursal;
	}

	

	public String getStrTxtNumeroIdentidad() {
		return strTxtNumeroIdentidad;
	}

	public void setStrTxtNumeroIdentidad(String strTxtNumeroIdentidad) {
		this.strTxtNumeroIdentidad = strTxtNumeroIdentidad;
	}

	public Date getDtTxtFechaIni() {
		return dtTxtFechaIni;
	}

	public void setDtTxtFechaIni(Date dtTxtFechaIni) {
		this.dtTxtFechaIni = dtTxtFechaIni;
	}

	public Date getDtTxtFechaFin() {
		return dtTxtFechaFin;
	}

	public void setDtTxtFechaFin(Date dtTxtFechaFin) {
		this.dtTxtFechaFin = dtTxtFechaFin;
	}

	public List getBeanListCobranzas() {
		return beanListCobranzas;
	}

	public void setBeanListCobranzas(List beanListCobranzas) {
		this.beanListCobranzas = beanListCobranzas;
	}

	public boolean isBolRecibosEnlaces() {
		return bolRecibosEnlaces;
	}

	public void setBolRecibosEnlaces(boolean bolRecibosEnlaces) {
		this.bolRecibosEnlaces = bolRecibosEnlaces;
	}
	
	

	
	public String getStrDni() {
		return strDni;
	}

	public void setStrDni(String strDni) {
		this.strDni = strDni;
	}

	public boolean isHabilitarBotones() {
		return habilitarBotones;
	}

	public void setHabilitarBotones(boolean habilitarBotones) {
		this.habilitarBotones = habilitarBotones;
	}

	public String getStrNombreApellidosGestor() {
		return strNombreApellidosGestor;
	}

	public void setStrNombreApellidosGestor(String strNombreApellidosGestor) {
		this.strNombreApellidosGestor = strNombreApellidosGestor;
	}

	public String getStrDescSucursal() {
		return strDescSucursal;
	}

	public void setStrDescSucursal(String strDescSucursal) {
		this.strDescSucursal = strDescSucursal;
	}
	
	
	//Combos
	
	public List<EstructuraComp> getListaEntidad() {
		return listaEntidad;
	}

	public void setListaEntidad(List<EstructuraComp> listaEntidad) {
		this.listaEntidad = listaEntidad;
	}

	public String getStrRuc() {
		return strRuc;
	}

	public void setStrRuc(String strRuc) {
		this.strRuc = strRuc;
	}
	

	public Integer getIntHoraInicio() {
		return intHoraInicio;
	}

	public void setIntHoraInicio(Integer intHoraInicio) {
		this.intHoraInicio = intHoraInicio;
	}

	public Integer getIntHoraFin() {
		return intHoraFin;
	}

	public void setIntHoraFin(Integer intHoraFin) {
		this.intHoraFin = intHoraFin;
	}

	public Integer getIntTipoBusqueda() {
		return intTipoBusqueda;
	}

	public void setIntTipoBusqueda(Integer intTipoBusqueda) {
		this.intTipoBusqueda = intTipoBusqueda;
	}
	
	

	public GestionCobranza getGestionCobranzaSelected() {
		return gestionCobranzaSelected;
	}

	public void setGestionCobranzaSelected(GestionCobranza gestionCobranzaSelected) {
		this.gestionCobranzaSelected = gestionCobranzaSelected;
	}
	
	

	public Integer getIntMinInicio() {
		return intMinInicio;
	}

	public void setIntMinInicio(Integer intMinInicio) {
		this.intMinInicio = intMinInicio;
	}

	public Integer getIntMinFin() {
		return intMinFin;
	}

	public void setIntMinFin(Integer intMinFin) {
		this.intMinFin = intMinFin;
	}
	
	

	
	public boolean isFormTipoDisabled() {
		return formTipoDisabled;
	}

	public void setFormTipoDisabled(boolean formTipoDisabled) {
		this.formTipoDisabled = formTipoDisabled;
	}

	public boolean isFormPromFechaDisabled() {
		return formPromFechaDisabled;
	}

	public void setFormPromFechaDisabled(boolean formPromFechaDisabled) {
		this.formPromFechaDisabled = formPromFechaDisabled;
	}

	public boolean isFormPromHoraInicioDisabled() {
		return formPromHoraInicioDisabled;
	}

	public void setFormPromHoraInicioDisabled(boolean formPromHoraInicioDisabled) {
		this.formPromHoraInicioDisabled = formPromHoraInicioDisabled;
	}

	public boolean isFormPromHoraFinDisabled() {
		return formPromHoraFinDisabled;
	}

	public void setFormPromHoraFinDisabled(boolean formPromHoraFinDisabled) {
		this.formPromHoraFinDisabled = formPromHoraFinDisabled;
	}

	public boolean isFormPromMinInicioDisabled() {
		return formPromMinInicioDisabled;
	}

	public void setFormPromMinInicioDisabled(boolean formPromMinInicioDisabled) {
		this.formPromMinInicioDisabled = formPromMinInicioDisabled;
	}

	public boolean isFormPromMinFinDisabled() {
		return formPromMinFinDisabled;
	}

	public void setFormPromMinFinDisabled(boolean formPromMinFinDisabled) {
		this.formPromMinFinDisabled = formPromMinFinDisabled;
	}

	public boolean isFormPromContactoDisabled() {
		return formPromContactoDisabled;
	}

	public void setFormPromContactoDisabled(boolean formPromContactoDisabled) {
		this.formPromContactoDisabled = formPromContactoDisabled;
	}

	public boolean isFormPromObservacionDisabled() {
		return formPromObservacionDisabled;
	}

	public void setFormPromObservacionDisabled(boolean formPromObservacionDisabled) {
		this.formPromObservacionDisabled = formPromObservacionDisabled;
	}

	public boolean isFormPromocionEnabled() {
		return formPromocionEnabled;
	}

	public void setFormPromocionEnabled(boolean formPromocionEnabled) {
		this.formPromocionEnabled = formPromocionEnabled;
	}
	
	
	

	public boolean isFormPromBotonAgregarDisabled() {
		return formPromBotonAgregarDisabled;
	}

	public void setFormPromBotonAgregarDisabled(boolean formPromBotonAgregarDisabled) {
		this.formPromBotonAgregarDisabled = formPromBotonAgregarDisabled;
	}

	public boolean isFormPromBotonEliminarDisabled() {
		return formPromBotonEliminarDisabled;
	}

	public void setFormPromBotonEliminarDisabled(
			boolean formPromBotonEliminarDisabled) {
		this.formPromBotonEliminarDisabled = formPromBotonEliminarDisabled;
	}

	public boolean isFormTramiteEnabled() {
		return formTramiteEnabled;
	}

	public void setFormTramiteEnabled(boolean formTramiteEnabled) {
		this.formTramiteEnabled = formTramiteEnabled;
	}

	
	
	public boolean isBotonEliminarDisabled() {
		return botonEliminarDisabled;
	}

	public void setBotonEliminarDisabled(boolean botonEliminarDisabled) {
		this.botonEliminarDisabled = botonEliminarDisabled;
	}

	
	public boolean isBotonValidarDisabled() {
		return botonValidarDisabled;
	}

	public void setBotonValidarDisabled(boolean botonValidarDisabled) {
		this.botonValidarDisabled = botonValidarDisabled;
	}
		
	public boolean isFormTramFechaDisabled() {
		return formTramFechaDisabled;
	}

	public void setFormTramFechaDisabled(boolean formTramFechaDisabled) {
		this.formTramFechaDisabled = formTramFechaDisabled;
	}

	public boolean isFormTramHoraInicioDisabled() {
		return formTramHoraInicioDisabled;
	}

	public void setFormTramHoraInicioDisabled(boolean formTramHoraInicioDisabled) {
		this.formTramHoraInicioDisabled = formTramHoraInicioDisabled;
	}

	public boolean isFormTramMinFinDisabled() {
		return formTramMinFinDisabled;
	}

	public void setFormTramMinFinDisabled(boolean formTramMinFinDisabled) {
		this.formTramMinFinDisabled = formTramMinFinDisabled;
	}

	public boolean isFormTramContactoDisabled() {
		return formTramContactoDisabled;
	}

	public void setFormTramContactoDisabled(boolean formTramContactoDisabled) {
		this.formTramContactoDisabled = formTramContactoDisabled;
	}

	public boolean isFormTramObservacionDisabled() {
		return formTramObservacionDisabled;
	}

	public void setFormTramObservacionDisabled(boolean formTramObservacionDisabled) {
		this.formTramObservacionDisabled = formTramObservacionDisabled;
	}

	public boolean isFormTramBotonAgregarDisabled() {
		return formTramBotonAgregarDisabled;
	}

	public void setFormTramBotonAgregarDisabled(boolean formTramBotonAgregarDisabled) {
		this.formTramBotonAgregarDisabled = formTramBotonAgregarDisabled;
	}

	public boolean isFormTramBotonEliminarDisabled() {
		return formTramBotonEliminarDisabled;
	}

	public void setFormTramBotonEliminarDisabled(
			boolean formTramBotonEliminarDisabled) {
		this.formTramBotonEliminarDisabled = formTramBotonEliminarDisabled;
	}

	
	public boolean isFormTramHoraFinDisabled() {
		return formTramHoraFinDisabled;
	}

	public void setFormTramHoraFinDisabled(boolean formTramHoraFinDisabled) {
		this.formTramHoraFinDisabled = formTramHoraFinDisabled;
	}

	public boolean isFormTramMinInicioDisabled() {
		return formTramMinInicioDisabled;
	}

	public void setFormTramMinInicioDisabled(boolean formTramMinInicioDisabled) {
		this.formTramMinInicioDisabled = formTramMinInicioDisabled;
	}
	
	

	public List<SocioComp> getListaSocio() {
		return listaSocio;
	}

	public void setListaSocio(List<SocioComp> listaSocio) {
		this.listaSocio = listaSocio;
	}
	

	public boolean isFormCobrFechaDisabled() {
		return formCobrFechaDisabled;
	}

	public void setFormCobrFechaDisabled(boolean formCobrFechaDisabled) {
		this.formCobrFechaDisabled = formCobrFechaDisabled;
	}

	public boolean isFormCobrHoraInicioDisabled() {
		return formCobrHoraInicioDisabled;
	}

	public void setFormCobrHoraInicioDisabled(boolean formCobrHoraInicioDisabled) {
		this.formCobrHoraInicioDisabled = formCobrHoraInicioDisabled;
	}

	public boolean isFormCobrHoraFinDisabled() {
		return formCobrHoraFinDisabled;
	}

	public void setFormCobrHoraFinDisabled(boolean formCobrHoraFinDisabled) {
		this.formCobrHoraFinDisabled = formCobrHoraFinDisabled;
	}

	public boolean isFormCobrMinInicioDisabled() {
		return formCobrMinInicioDisabled;
	}

	public void setFormCobrMinInicioDisabled(boolean formCobrMinInicioDisabled) {
		this.formCobrMinInicioDisabled = formCobrMinInicioDisabled;
	}

	public boolean isFormCobrMinFinDisabled() {
		return formCobrMinFinDisabled;
	}

	public void setFormCobrMinFinDisabled(boolean formCobrMinFinDisabled) {
		this.formCobrMinFinDisabled = formCobrMinFinDisabled;
	}

	public boolean isFormCobrContactoDisabled() {
		return formCobrContactoDisabled;
	}

	public void setFormCobrContactoDisabled(boolean formCobrContactoDisabled) {
		this.formCobrContactoDisabled = formCobrContactoDisabled;
	}

	public boolean isFormCobrObservacionDisabled() {
		return formCobrObservacionDisabled;
	}

	public void setFormCobrObservacionDisabled(boolean formCobrObservacionDisabled) {
		this.formCobrObservacionDisabled = formCobrObservacionDisabled;
	}

	public boolean isFormCobrBotonAgregarDisabled() {
		return formCobrBotonAgregarDisabled;
	}

	public void setFormCobrBotonAgregarDisabled(boolean formCobrBotonAgregarDisabled) {
		this.formCobrBotonAgregarDisabled = formCobrBotonAgregarDisabled;
	}

	public boolean isFormCobrBotonEliminarDisabled() {
		return formCobrBotonEliminarDisabled;
	}

	public void setFormCobrBotonEliminarDisabled(
			boolean formCobrBotonEliminarDisabled) {
		this.formCobrBotonEliminarDisabled = formCobrBotonEliminarDisabled;
	}

	public List<Sucursal> getListJuridicaSucursal() {
	 
		log.info("-------------------------------------Debugging CobranzaController.getListJuridicaSucursal-------------------------------------");
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
	
	public void reloadCboSubResultado(ValueChangeEvent event){
		log.info("-----------------------Debugging EmpresaController.reloadCboSucursales-----------------------------");

		
		Integer intIdResultado = Integer.parseInt(""+event.getNewValue());
		log.info("intIdEmpresa = "+intIdResultado);
		TablaFacadeRemote facade = null;
		try {
			facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			List<Tabla> listaTabla = facade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPORESULTADO));
			Tabla tabla = null;
			
			for (Tabla tablaa : listaTabla) {
				if (tablaa.getIntIdDetalle().equals(intIdResultado)){
					tabla = tablaa;
					break;
				}
			}
			
			
		    setArchivoDocCobranza(null);
		    getBeanGestionCobranza().setIntItemDocCob(null);
		    
			if (intIdResultado.equals(Constante.PARAM_T_TIPORESULTADO_PAGO_CAJA)){
					  setFormGenerarDocumentoDisabled(false);
				  setFormCobrBotonGenIngresoDisabled(true);
				  setFormCobrBotonGenDocuDisabled(false);
			}
			else
			if (intIdResultado.equals(Constante.PARAM_T_TIPORESULTADO_NOTIFICACION)){
					setFormGenerarDocumentoDisabled(false);
					setFormCobrBotonGenDocuDisabled(true);
					setFormCobrBotonGenIngresoDisabled(false);
				
			}
			else{
					setFormGenerarDocumentoDisabled(false);
					setFormCobrBotonGenDocuDisabled(false);
					setFormCobrBotonGenIngresoDisabled(false);
				
			}
			
		listaSubResultado =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOSUBRESULTADO),tabla.getStrIdAgrupamientoA());
			
		log.info("listaSubResultado.size(): " + listaSubResultado.size());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	
	public void loadCboSubResultado(Integer intIdResultado){
		log.info("-----------------------Debugging EmpresaController.reloadCboSucursales-----------------------------");

		
		log.info("intIdEmpresa = "+intIdResultado);
		TablaFacadeRemote facade = null;
		try {
			facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			List<Tabla> listaTabla = facade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPORESULTADO));
			Tabla tabla = null;
			
			for (Tabla tablaa : listaTabla) {
				if (tablaa.getIntIdDetalle().equals(intIdResultado)){
					tabla = tablaa;
					break;
				}
			}
			
			if (intIdResultado.equals(Constante.PARAM_T_TIPORESULTADO_PAGO_CAJA)){
				setFormGenerarDocumentoDisabled(false);
			    setFormCobrBotonGenIngresoDisabled(true);
			    setFormCobrBotonGenDocuDisabled(false);
			}
			else
			if (intIdResultado.equals(Constante.PARAM_T_TIPORESULTADO_NOTIFICACION)){
				setFormGenerarDocumentoDisabled(false);
				setFormCobrBotonGenDocuDisabled(true);
				setFormCobrBotonGenIngresoDisabled(false);
			}
			else{
				setFormGenerarDocumentoDisabled(false);
				setFormCobrBotonGenDocuDisabled(false);
				setFormCobrBotonGenIngresoDisabled(false);
			}
				
			listaSubResultado =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOSUBRESULTADO),tabla.getStrIdAgrupamientoA());
			log.info("listaSubResultado.size(): " + listaSubResultado.size());
			
			
		
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public List<Tabla> getListaSubResultado() {
		return listaSubResultado;
	}

	public void setListaSubResultado(List<Tabla> listaSubResultado) {
		this.listaSubResultado = listaSubResultado;
	}

	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
	}

	public Integer getIntTipoSocio() {
		return intTipoSocio;
	}

	public void setIntTipoSocio(Integer intTipoSocio) {
		this.intTipoSocio = intTipoSocio;
	}
	
	
	public void listaExpedienteCreditoPorGarante(){
		
	 	try {
	 		 SolicitudPrestamoFacadeRemote facadeSolPres = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
	 		 TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			 CreditoFacadeRemote creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
				
		 	if(getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(0) != null){
		 		   ConceptoFacadeRemote conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				   GestionCobranzaSoc socioCobranza = (GestionCobranzaSoc)getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(0);
		           List<GarantiaCredito> listaGaratiaCredito =  facadeSolPres.getListaGarantiasPorPkPersona(socioCobranza.getSocioComp().getSocio().getId().getIntIdEmpresa(), socioCobranza.getSocioComp().getSocio().getId().getIntIdPersona());
		           CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
		           PersonaFacadeRemote personaFacadeRemote = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				   
		           List<Expediente> listaExpTemp =new ArrayList<Expediente>();
		           for (GarantiaCredito garantiaCredito : listaGaratiaCredito) {
		        	   
		        	   Integer intEmpresa   = garantiaCredito.getId().getIntPersEmpresaPk();
		        	   Integer intNroCuenta = garantiaCredito.getId().getIntCuentaPk();
		        	   List<Expediente> listaExp = conceptoFacadeRemote.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa,intNroCuenta);
					   if(listaExp != null && !listaExp.isEmpty()){
						   for (Expediente expediente : listaExp) {
			        		   
			        		   CreditoId creditoId = new CreditoId();
							   
							   creditoId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
							   creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
							   creditoId.setIntItemCredito(expediente.getIntItemCredito());
							   
							   Credito credito = creditoFacade.getCreditoPorIdCredito(creditoId);
							   Tabla tabla =  tablaFacade.getTablaPorIdMaestroYIdDetalle(Constante.PARAM_T_TIPO_SOCIOCREDITO, credito.getIntParaTipoCreditoEmpresa());
							   
							   CuentaId cuentaId = new CuentaId();
							   cuentaId.setIntCuenta(expediente.getId().getIntCuentaPk());
							   cuentaId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
							   Cuenta cuenta = cuentaFacade.getCuentaPorId(cuentaId);
							   String nroCuenta  = cuenta.getStrNumeroCuenta();
							   CuentaIntegrante cuentaIntegrante =  cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId).get(0);
							   Natural natural = personaFacadeRemote.getNaturalPorPK(cuentaIntegrante.getId().getIntPersonaIntegrante());
							   String nombreTitular = natural.getStrNombres() + " " +natural.getStrApellidoPaterno()+ " "+natural.getStrApellidoMaterno();
							   expediente.setStrDescripcion(expediente.getId().getIntItemExpediente()+"-"+tabla.getStrDescripcion()+"-S/"+expediente.getBdSaldoCredito().doubleValue()+"-Titular:"+nombreTitular+"-Cuenta:"+nroCuenta);
							   
								if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PASIVA)){
									 if (cuenta.getIntParaCondicionCuentaCod().equals(Constante.PARAMA_T_CTA_ESTADO_MORROSO)){
									      listaExpTemp.add(expediente);
									   }
								}
								else{
							   
							   
								   if (cuenta.getIntParaSubCondicionCuentaCod().equals(Constante.PARAM_T_TIPO_CONDSOCIOREGULAR)){
								      listaExpTemp.add(expediente);
								   }
								}
						   }
					   }
		        	  
					
				   }
		           this.listaExpCredito = listaExpTemp;
		    }
		    
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
	}
	
	public void listaExpedienteCreditoPorTitular(){
		
		try{
		     ConceptoFacadeRemote conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		     CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
	         TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		     CreditoFacadeRemote creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
		     List<Expediente> listaExpTemp =new ArrayList<Expediente>();
		     if(getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(0) != null){
				   GestionCobranzaSoc socioCobranza = (GestionCobranzaSoc)getBeanGestionCobranza().getListaGestionCobranzaDetalle().get(0);
				
		       List<CuentaIntegrante> listaCueInt = cuentaFacade.getCuentaIntegrantePorIdPersona(socioCobranza.getSocioComp().getSocio().getId().getIntIdPersona(),socioCobranza.getSocioComp().getSocio().getId().getIntIdEmpresa());
	    		
		       for (CuentaIntegrante cuentaIntegrante : listaCueInt) {
		    	   
		    	   Integer intEmpresa   =  cuentaIntegrante.getId().getIntPersEmpresaPk();
		    	   Integer intNroCuenta =  cuentaIntegrante.getId().getIntCuenta();
		    	   List<Expediente> listaExp = conceptoFacadeRemote.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa,intNroCuenta);
		    	   for (Expediente expediente : listaExp) {
	        		   
	        		   CreditoId creditoId = new CreditoId();
					   
					   creditoId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
					   creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
					   creditoId.setIntItemCredito(expediente.getIntItemCredito());
					   
					   Credito credito = creditoFacade.getCreditoPorIdCredito(creditoId);
					   Tabla tabla =  tablaFacade.getTablaPorIdMaestroYIdDetalle(Constante.PARAM_T_TIPO_SOCIOCREDITO, credito.getIntParaTipoCreditoEmpresa());
					   
					   CuentaId cuentaId = new CuentaId();
					   cuentaId.setIntCuenta(expediente.getId().getIntCuentaPk());
					   cuentaId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
					   Cuenta cuenta = cuentaFacade.getCuentaPorId(cuentaId);
					   expediente.setStrDescripcion(expediente.getId().getIntItemExpediente()+"-"+tabla.getStrDescripcion()+"-S/"+expediente.getBdSaldoCredito().doubleValue());
					  if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_PASIVA)){
						    if (cuenta.getIntParaCondicionCuentaCod().equals(Constante.PARAMA_T_CTA_ESTADO_MORROSO)){
							      listaExpTemp.add(expediente);
							  } 
					  }
					  else{
						   if (cuenta.getIntParaSubCondicionCuentaCod().equals(Constante.PARAM_T_TIPO_CONDSOCIOREGULAR)){
						      listaExpTemp.add(expediente);
						   } 
					  }   
				   }
			 	   
			   }
		       this.listaExpCredito = listaExpTemp;
		    }
		     
		   
		 } catch (EJBFactoryException e) {
				e.printStackTrace();
		 } catch (BusinessException e) {
				e.printStackTrace();
		}
	}
	
	
	
   public void generarDocCobranza(ActionEvent event){
	   
	   setFormGenerarDocumentoDisabled(true);
	   setFormCobrBotonGenIngresoDisabled(false);
   }
   
   public void aceptarAdjuntarDocCobranza(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			archivoDocCobranza = fileUploadController.getArchivoDocCobranza();
			archivoDocCobranzaTemp = archivoDocCobranza;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
    public void quitarDocCobranza(){
		try{
			 archivoDocCobranzaTemp = archivoDocCobranza;
			 archivoDocCobranza = null;
			((FileUploadController)getSessionBean("fileUploadController")).setArchivoDocCobranza(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
    
    public void adjuntarDocCobranza(){
    	
    	FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
    	
    }
   
	
	public List<Expediente> getListaExpCredito() {
		
		
		
		return listaExpCredito;
	}

	public void setListaExpCredito(List<Expediente> listaExpCredito) {
		
		
		this.listaExpCredito = listaExpCredito;
	}

	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}

	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}

	public List<DocumentoCobranza> getListaDocCobranza() {
	   try{
		   DocumentoCobranzaFacadeLocal docCobranzaFacade= (DocumentoCobranzaFacadeLocal)EJBFactory.getLocal(DocumentoCobranzaFacadeLocal.class);
		   
		   if (getBeanGestionCobranza().getIntTipoGestionCobCod().equals(Constante.PARAM_T_TIPOCOBRANZA_ACTIVA)){
			   DocumentoCobranza doc =  new DocumentoCobranza();
			   doc.setIntParaTipoCobranza(Constante.PARAMA_T_TIPO_COBRANZA_ACTIVA);
		       listaDocCobranza = docCobranzaFacade.getListaDocumentoCobranza(doc);
		   } 
		   else{
			   DocumentoCobranza doc =  new DocumentoCobranza();
			   doc.setIntParaTipoCobranza(Constante.PARAMA_T_TIPO_COBRANZA_PASIVA);
		       listaDocCobranza = docCobranzaFacade.getListaDocumentoCobranza(doc);
		   }
	   } catch (EJBFactoryException e) {
			e.printStackTrace();
	   } catch (BusinessException e) {
			e.printStackTrace();
	   }
		
		return listaDocCobranza;
	}

	public void setListaDocCobranza(List<DocumentoCobranza> listaDocCobranza) {
	this.listaDocCobranza = listaDocCobranza;
	}

	public Integer getIntItemDocCob() {
		return intItemDocCob;
	}

	public void setIntItemDocCob(Integer intItemDocCob) {
		this.intItemDocCob = intItemDocCob;
	}

	public Archivo getArchivoDocCobranza() {
		return archivoDocCobranza;
	}

	public void setArchivoDocCobranza(Archivo archivoDocCobranza) {
		this.archivoDocCobranza = archivoDocCobranza;
	}

	public boolean isFormCobrBotonGenIngresoDisabled() {
		return formCobrBotonGenIngresoDisabled;
	}

	public void setFormCobrBotonGenIngresoDisabled(
			boolean formCobrBotonGenIngresoDisabled) {
		this.formCobrBotonGenIngresoDisabled = formCobrBotonGenIngresoDisabled;
	}

	public boolean isFormCobrBotonGenDocuDisabled() {
		return formCobrBotonGenDocuDisabled;
	}

	public void setFormCobrBotonGenDocuDisabled(boolean formCobrBotonGenDocuDisabled) {
		this.formCobrBotonGenDocuDisabled = formCobrBotonGenDocuDisabled;
	}

	public boolean isFormGenerarDocumentoDisabled() {
		return formGenerarDocumentoDisabled;
	}

	public void setFormGenerarDocumentoDisabled(boolean formGenerarDocumentoDisabled) {
		this.formGenerarDocumentoDisabled = formGenerarDocumentoDisabled;
	}

	public boolean isFormTramSubTipoGestionCobCod() {
		return formTramSubTipoGestionCobCod;
	}

	public void setFormTramSubTipoGestionCobCod(boolean formTramSubTipoGestionCobCod) {
		this.formTramSubTipoGestionCobCod = formTramSubTipoGestionCobCod;
	}

	public boolean isFormTramOpcSocioEntidad() {
		return formTramOpcSocioEntidad;
	}

	public void setFormTramOpcSocioEntidad(boolean formTramOpcSocioEntidad) {
		this.formTramOpcSocioEntidad = formTramOpcSocioEntidad;
	}

	public boolean isFormCobrTipoSocioDisabled() {
		return formCobrTipoSocioDisabled;
	}

	public void setFormCobrTipoSocioDisabled(boolean formCobrTipoSocioDisabled) {
		this.formCobrTipoSocioDisabled = formCobrTipoSocioDisabled;
	}

	public boolean isFormCobrParaLugarGestionDisabled() {
		return formCobrParaLugarGestionDisabled;
	}

	public void setFormCobrParaLugarGestionDisabled(
			boolean formCobrParaLugarGestionDisabled) {
		this.formCobrParaLugarGestionDisabled = formCobrParaLugarGestionDisabled;
	}

	public boolean isFormCobrItemExpedienteDisabled() {
		return formCobrItemExpedienteDisabled;
	}

	public void setFormCobrItemExpedienteDisabled(
			boolean formCobrItemExpedienteDisabled) {
		this.formCobrItemExpedienteDisabled = formCobrItemExpedienteDisabled;
	}

	public boolean isFormCobrParaTipoResultadoDisabled() {
		return formCobrParaTipoResultadoDisabled;
	}

	public void setFormCobrParaTipoResultadoDisabled(
			boolean formCobrParaTipoResultadoDisabled) {
		this.formCobrParaTipoResultadoDisabled = formCobrParaTipoResultadoDisabled;
	}

	public boolean isFormCobrParaTipoSubResultadoDisabled() {
		return formCobrParaTipoSubResultadoDisabled;
	}

	public void setFormCobrParaTipoSubResultadoDisabled(
			boolean formCobrParaTipoSubResultadoDisabled) {
		this.formCobrParaTipoSubResultadoDisabled = formCobrParaTipoSubResultadoDisabled;
	}

	public boolean isFormCobrItemDocCobDisabled() {
		return formCobrItemDocCobDisabled;
	}

	public void setFormCobrItemDocCobDisabled(boolean formCobrItemDocCobDisabled) {
		this.formCobrItemDocCobDisabled = formCobrItemDocCobDisabled;
	}

	public boolean isFormCobrBotonQuitarArchDisabled() {
		return formCobrBotonQuitarArchDisabled;
	}

	public void setFormCobrBotonQuitarArchDisabled(
			boolean formCobrBotonQuitarArchDisabled) {
		this.formCobrBotonQuitarArchDisabled = formCobrBotonQuitarArchDisabled;
	}

	public boolean isFormCobrBotonAdjuntarArchDisabled() {
		return formCobrBotonAdjuntarArchDisabled;
	}

	public void setFormCobrBotonAdjuntarArchDisabled(
			boolean formCobrBotonAdjuntarArchDisabled) {
		this.formCobrBotonAdjuntarArchDisabled = formCobrBotonAdjuntarArchDisabled;
	}
	
	
	public boolean isBotonGrabarDisabled() {
		return botonGrabarDisabled;
	}

	public void setBotonGrabarDisabled(boolean botonGrabarDisabled) {
		this.botonGrabarDisabled = botonGrabarDisabled;
	}

	public Archivo grabarArchvioDocAdjunto(Archivo archivo) throws BusinessException,EJBFactoryException{
		
		Archivo archivoResult= null;
		FileUploadController fileUploadController = (FileUploadController)getSessionBean("FileUploadController");
		
		if (fileUploadController.getFile() != null){
			try {
				  InputStream in = new FileInputStream(fileUploadController.getFile());
				  OutputStream out = new FileOutputStream(fileUploadController.getTarjet());
				try {
					// Transfer bytes from in to out
					byte[] buf = new byte[1024*1024*50];//Máximo 50MB
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
				}catch(Exception e){
					e.printStackTrace();
					log.error(e.getMessage(),e);
				}finally {
					in.close();
					out.close();
				}
			 }catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e);
			 }
		 
		 
		 
					try{
						   GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
						   archivoResult = facade.grabarArchivo(archivo);
					
					    } catch (BusinessException e) {
							System.out.println("error: "+ e);
							throw e;
						} catch (EJBFactoryException e) {
							System.out.println("error: "+ e);
							throw new BusinessException(e);
				 }
			
				 archivo.setRutaActual(archivo.getTipoarchivo().getStrRuta()+"\\"+archivoResult.getStrNombrearchivo());
			     String strRuta      = archivo.getRutaAntigua();
			     String nuevoNombre	 = archivo.getRutaActual();	  
				 try{
						java.io.File oldFile = new java.io.File(strRuta);
						java.io.File newFile = new java.io.File(nuevoNombre);
						oldFile.renameTo(newFile);
					}catch(Exception e){
						System.out.println("El renombrado no se ha podido realizar: " + e);
						log.error(e.getMessage(),e);
						throw new BusinessException(e);
				}
				 
		 }
		
			return archivoResult;
	}
	
	public void irCerrarGestiones(ActionEvent event){
	      try{
			    	GestionCobranzaFacadeLocal gestCobFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
			    	gestCobFacade.obtUltimaFechaGestion(new GestionCobranzaCierre());
			    	GestionCobranzaCierre gCcierre= new GestionCobranzaCierre();
			    	gCcierre.setDtUltFechaCierre(gestCobFacade.obtUltimaFechaGestion(new GestionCobranzaCierre()));
			    	setBeanGestCobCierre(gCcierre);
			    	
			      } catch (EJBFactoryException e) {
				  	e.printStackTrace();
				  } catch (BusinessException e) {
					e.printStackTrace();
				  }
				  
				setCobranzaRendered(true);
				setHabilitarGestion(false);
				setHabilitarCierre(true);
				setHabilitarFormCierre(false);
				setFormPromocionEnabled(false);
				setFormTramiteEnabled(false);
				setFormCobranzaEnabled(false);
				setFormGenerarDocumentoDisabled(false);
				setBotonValidarDisabled(true);
				setDtFechaCierreDisabled(false);
	}
	
	
	public void validar(ActionEvent event){
		try {
		 GestionCobranza busqGestionCobranza = new GestionCobranza();
			//Valida
			if (getBeanGestCobCierre().getId().getDtFechaCierre() == null){
			    FacesContextUtil.setMessageError("Debe Ingresar La fecha Cierre.");
			    return;
		    }
			
			if (getBeanGestCobCierre().getId().getDtFechaCierre() != null){
			    Date fechaActual = new Date();
			    if (getBeanGestCobCierre().getId().getDtFechaCierre().after(fechaActual)){
			    	 FacesContextUtil.setMessageError("La fecha de Cierre debe ser igual/menor a la fecha actual");
			    	 return;
			    }
			    
		    }
			
		    //Busca
		    busqGestionCobranza.setId(new GestionCobranzaId());
		    busqGestionCobranza.getId().setIntPersEmpresaGestionPK(SESION_IDEMPRESA);
		    busqGestionCobranza.setIntTipoGestionCobCod(0);
		    busqGestionCobranza.setStrNroDocuIdentidad("");
			busqGestionCobranza.setIntSucursal(SESION_IDSUCURSAL);
			busqGestionCobranza.setIntIdPersonaGestor(SESION_IDUSUARIO);
			busqGestionCobranza.setDtFechaGestionIni(getDtTxtFechaIni());
			busqGestionCobranza.setDtFechaGestionFin(getBeanGestCobCierre().getId().getDtFechaCierre());
			busqGestionCobranza.setIntEstado(-1);
		
		    ArrayList listaGestionCobranza = new ArrayList();
		    List<GestionCobranza> listaGestionCobranzaTemp = new ArrayList<GestionCobranza>();
		    List<GestionCobranza> listaGestionCobranzaTemp2 = new ArrayList<GestionCobranza>();
		    
		    
			
				GestionCobranzaFacadeLocal GestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
				listaGestionCobranza = (ArrayList) GestionCobranzaFacade.getListaGestionCobranza(busqGestionCobranza);
				
				log.info("listaGestionCobranza.size: "+listaGestionCobranza.size());
				
				
				listaGestionCobranzaTemp = listaGestionCobranzaDet(listaGestionCobranza);	
				
				for (GestionCobranza gcob: listaGestionCobranzaTemp){
					if (gcob.getIntEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					    if(gcob.getGestorCobranza().getSucursal().getId().getIntIdSucursal().equals(SESION_IDSUCURSAL)){
							if (gcob.getDtHoraInicio() != null){
								Integer horaInicio   = UtilCobranza.obtieneHora(gcob.getDtHoraInicio());
								gcob.setIntHoraInicio(horaInicio);
								Integer miniInicio   = UtilCobranza.obtieneMinuto(gcob.getDtHoraInicio());
								gcob.setIntMiniInicio(miniInicio);
							}
							
							if (gcob.getDtFechaCierre() == null){
								listaGestionCobranzaTemp2.add(gcob);
							}
					    }	
					}	
				}
				
				beanListCobranzasCierre   =	listaGestionCobranzaTemp2;
				
			} catch (EJBFactoryException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		
			
			setHabilitarFormCierre(true);
			setFormPromocionEnabled(false);
			setFormTramiteEnabled(false);
			setFormCobranzaEnabled(false);
			
			setBotonValidarDisabled(false);
			
			setDtFechaCierreDisabled(true);
	}
	
	
	public void grabarCierreCobranza(ActionEvent event){
		
	   GestionCobranzaCierre gestionCobranzaCie = null;
	   boolean esValido = true;
	   
	   //Valida
	   for(GestionCobranza cob: getBeanListCobranzasCierre()){
		   if (cob.getDtHoraFin() == null && (cob.getIntHoraInicio() == null || cob.getIntHoraFin() == null)){
			   esValido = false;
			   FacesContextUtil.setMessageError("Debe completar la Hora de Inicio y Fin.");
			   break;
		   }
		   if (cob.getDtHoraFin() == null && (cob.getIntHoraInicio() != null || cob.getIntHoraFin() != null)){
			   if (cob.getIntHoraFin() < cob.getIntHoraInicio()){
				   FacesContextUtil.setMessageError("La Hora de Inicio debe ser menor la Hora de Fin");
				   esValido = false;
				   break;
			   }
			   
			   if (cob.getIntHoraFin() == cob.getIntHoraInicio()){
				   
				   if (cob.getIntMiniFin() == null)cob.setIntMiniFin(0);
				   if (cob.getIntMiniInicio() == null)cob.setIntMiniInicio(0);
				   
				   if (cob.getIntMiniFin() <= cob.getIntMiniInicio()){
					   FacesContextUtil.setMessageError("La Hora de Inicio debe ser menor la Hora de Fin");
					   esValido = false;
					   break;
				   }   
			   }
		   }
	  }
	   
	   if(!esValido){
			return;
	   }
	   //Grabado
		try {
			
			 getBeanGestCobCierre().getId().setIntPersEmpresaCierreGestCob(SESION_IDEMPRESA);
			 getBeanGestCobCierre().setIntEmpresaGestor(SESION_IDEMPRESA);
			 getBeanGestCobCierre().setIntPersonaGestor(SESION_IDUSUARIO);
			 
			 GestionCobranzaFacadeLocal GestionCobranzaFacade = (GestionCobranzaFacadeLocal)EJBFactory.getLocal(GestionCobranzaFacadeLocal.class);
			 getBeanGestCobCierre().setListaGestionCobranza(getBeanListCobranzasCierre());
			 gestionCobranzaCie =  GestionCobranzaFacade.grabarGestionCobranzaCierre(getBeanGestCobCierre());
			 
	    } catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
			log.error(e);
		}
		
		if(gestionCobranzaCie != null){
			FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
			setCobranzaRendered(false);
			setHabilitarFormCierre(false);
		}
		
	}
	
	public void limpiarSocio(ValueChangeEvent event){
		log.info("limpiarSocio:");
		getBeanGestionCobranza().setStrSocioNombre("");
		setListaExpCredito(null);
	}
	
	public void mostrarBotonGenerarIngreso(ValueChangeEvent event){
		Integer intTipoResultado   = Integer.parseInt(""+event.getNewValue());
		log.info("intTipoResultado = "+intTipoResultado);
		
		if (intTipoResultado.equals(Constante.PARAM_T_TIPORESULTADOCOBROCHEQUE)){
			setHabilitarBotonGeneararIngreso(true);
		}else{
			setHabilitarBotonGeneararIngreso(false);
		}
	}

	public boolean isHabilitarCierre() {
		return habilitarCierre;
	}

	public void setHabilitarCierre(boolean habilitarCierre) {
		this.habilitarCierre = habilitarCierre;
	}

	public boolean isHabilitarFormCierre() {
		return habilitarFormCierre;
	}

	public void setHabilitarFormCierre(boolean habilitarFormCierre) {
		this.habilitarFormCierre = habilitarFormCierre;
	}

	public boolean isHabilitarGestion() {
		return habilitarGestion;
	}

	public void setHabilitarGestion(boolean habilitarGestion) {
		this.habilitarGestion = habilitarGestion;
	}

	public GestionCobranzaCierre getBeanGestCobCierre() {
		return beanGestCobCierre;
	}

	public void setBeanGestCobCierre(GestionCobranzaCierre beanGestCobCierre) {
		this.beanGestCobCierre = beanGestCobCierre;
	}

	public List<GestionCobranza> getBeanListCobranzasCierre() {
		return beanListCobranzasCierre;
	}

	public void setBeanListCobranzasCierre(
			List<GestionCobranza> beanListCobranzasCierre) {
		this.beanListCobranzasCierre = beanListCobranzasCierre;
	}

	public boolean isDtFechaCierreDisabled() {
		return dtFechaCierreDisabled;
	}

	public void setDtFechaCierreDisabled(boolean dtFechaCierreDisabled) {
		this.dtFechaCierreDisabled = dtFechaCierreDisabled;
	}

	public boolean isHabilitarDescarga() {
		return habilitarDescarga;
	}

	public void setHabilitarDescarga(boolean habilitarDescarga) {
		this.habilitarDescarga = habilitarDescarga;
	}

	public boolean isFormCobrBotonGenDocuDisabled2() {
		return formCobrBotonGenDocuDisabled2;
	}

	public void setFormCobrBotonGenDocuDisabled2(
			boolean formCobrBotonGenDocuDisabled2) {
		this.formCobrBotonGenDocuDisabled2 = formCobrBotonGenDocuDisabled2;
	}

	public boolean isHabilitarBotonGeneararIngreso() {
		return habilitarBotonGeneararIngreso;
	}

	public void setHabilitarBotonGeneararIngreso(
			boolean habilitarBotonGeneararIngreso) {
		this.habilitarBotonGeneararIngreso = habilitarBotonGeneararIngreso;
	}

	public boolean isFormBotonBusqCobrChDisabled() {
		return formBotonBusqCobrChDisabled;
	}

	public void setFormBotonBusqCobrChDisabled(boolean formBotonBusqCobrChDisabled) {
		this.formBotonBusqCobrChDisabled = formBotonBusqCobrChDisabled;
	}

	public boolean isFormBotonGenIngCobrChDisabled() {
		return formBotonGenIngCobrChDisabled;
	}

	public void setFormBotonGenIngCobrChDisabled(
			boolean formBotonGenIngCobrChDisabled) {
		this.formBotonGenIngCobrChDisabled = formBotonGenIngCobrChDisabled;
	}

	public boolean isFormCobrChTipoResDisabled() {
		return formCobrChTipoResDisabled;
	}

	public void setFormCobrChTipoResDisabled(boolean formCobrChTipoResDisabled) {
		this.formCobrChTipoResDisabled = formCobrChTipoResDisabled;
	}

	public Integer getCboEstadoCobranza() {
		return cboEstadoCobranza;
	}

	public void setCboEstadoCobranza(Integer cboEstadoCobranza) {
		this.cboEstadoCobranza = cboEstadoCobranza;
	}

	
	
	
	


 
	
	
}