package pe.com.tumi.contabilidad.perdidasSiniestro.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.contabilidad.impuesto.facade.ImpuestoFacadeLocal;
import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestroId;
import pe.com.tumi.contabilidad.perdidasSiniestro.facade.PerdidasSiniestroFacadeLocal;
import pe.com.tumi.contabilidad.tributacion.controller.TributacionController;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class PerdidasSiniestroController {
	protected static Logger log = Logger.getLogger(TributacionController.class);
	private PerdidasSiniestro perdidasSini;
	private PerdidasSiniestro perdidasSiniBusq;
	private PerdidasSiniestro seleccionPerdido;
	
	private Impuesto impuesto;
	
	//propiedades que capturan atributos de sesión
	private Integer IDUSUARIO_SESION;
	private Integer IDEMPRESA_SESION;
	private Usuario usuarioSesion;
	
	private List<Tabla> listaPersonaJuridica;
	private List<Tabla> listActivo;
	private List<Tabla> listTipo;
	private Integer intCboTipoJuridicaBusq;
	private Integer intCboFiltroJuridicaBusq;
	private List<SelectItem> cboPersonaJuridica = null;
	private String strTxtJuridticaBusq;
	private Boolean isDisabledTxtJuridicaBusq;
	//Valores del combo persona
	private List<Tabla> listTabla;
	private List<Impuesto> listaPersona;
	private List<SelectItem> cboFiltroPersona = null;
	private Integer intCboTipoPersonaBusq;
	private Integer intCboFiltroPersonaBusq;
	private String strTxtPersonaBusq;
	private Boolean isDisabledTxtPersonaBusq;
	
	private String strNombreJuridico;
	
	private List<PerdidasSiniestro> listaJuridica;
	private List<PerdidasSiniestro> listaBuscarPrincipal;
	//combos
	private List<Tabla> listTipoSiniestro;
	private List<Tabla> listMoneda;
	private List<Tabla> listActivoCab;
	private List<Tabla> listPago;
	
	//Fecha de Registro
	private String daFechaRegistro;
	private String strEstado;
	
	//Mostrar Panel
	private Boolean blnShowPanelInferior;
	
	private String strConcatenado;
	private Integer intIdPersona;
	
	//Valores para la busqueda general
	private List<SelectItem> cboFiltroPerdidaCab;
	private Integer intCboTipoPerdidoBusq;
	private Integer intCboFiltroPerdidoBusq;
	private String strTxtPerdidoBusq;
	private Boolean isDisabledTxtPerdidoBusq;
	private Boolean blnUpdating;
	
	//Validando botones
	private boolean btnVer;
	private boolean btnModificar;
	private boolean btnEliminar;
	private boolean blnVisible;
	
	
	private TablaFacadeRemote tablaFacade;
	private PerdidasSiniestroFacadeLocal perdidasFacade;
	
	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 14.08.2014 / 
	public PerdidasSiniestroController(){
		cargarUsuario();
		if(usuarioSesion!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 14.08.2014 /
	public String getInicioPage() {
		cargarUsuario();
		if(usuarioSesion!=null){
			limpiarFomularioPerdidas();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}		
		return "";
	}
	
	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 14.08.2014 /
	private void cargarUsuario(){
		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		
		IDUSUARIO_SESION = usuarioSesion.getIntPersPersonaPk();
		IDEMPRESA_SESION = usuarioSesion.getPerfil().getId().getIntPersEmpresaPk();
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Carga los valores iniciales del formulario fechas y combos
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return retorna los combos llenos.
	 */	
	public void cargarValoresIniciales(){
		try {
			perdidasFacade = (PerdidasSiniestroFacadeLocal) EJBFactory.getLocal(PerdidasSiniestroFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			
			perdidasSini = new PerdidasSiniestro();
			perdidasSini.setId(new PerdidasSiniestroId());
			perdidasSiniBusq = new PerdidasSiniestro();
			seleccionPerdido = new PerdidasSiniestro();
			
			btnVer = false;
			btnModificar = false;
			btnEliminar = false;
			blnVisible = false;
			
			listActivo = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO), "2");
			setStrEstado(listActivo.get(1).getStrDescripcion());
			listTipoSiniestro = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD));
			listMoneda = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOMONEDA), "0");
			listActivoCab = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO), "-1");
			listPago = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPO_PAGO), "-1");
			listTipo = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_TIPO_ASEGURADORA_PERSONAL_AFECTADO));
			
			/*Inicio Fecha*/
			java.util.Date date= new java.util.Date();
			perdidasSini.setTsSiniFechaRegistro(new Timestamp(date.getTime()));
			String fechaRegistro = Constante.sdf.format(perdidasSini.getTsSiniFechaRegistro());
			setDaFechaRegistro(fechaRegistro);
			/*Fin Fecha */
			listaBuscarPrincipal = new ArrayList<PerdidasSiniestro>();
			blnUpdating = false;
			blnShowPanelInferior = false;
			recargarCboPersonaJuridica();
			recargarCboFiltroPersona();
			recargarCboFiltroPerdidoCab();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Limpia todos los formularios
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Retorna los formularios en blanco
	 */	
	public void limpiarFomularioPerdidas(){
		blnShowPanelInferior = false;
		blnUpdating = false;
		perdidasSini = new PerdidasSiniestro();
		seleccionPerdido = new PerdidasSiniestro();
		listaBuscarPrincipal = new ArrayList<PerdidasSiniestro>();
		cboFiltroPerdidaCab = new ArrayList<SelectItem>();
		setStrTxtPerdidoBusq("");
		intCboTipoPerdidoBusq=0;
		perdidasSiniBusq.setDtSiniFechaSiniestro(null);
		perdidasSiniBusq.setIntParaEstadoCod(0);
		perdidasSiniBusq.setIntParaEstadoCobroCod(0);
		setStrConcatenado("");
	}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Muestra y oculta el formulario inferior.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 */	
	public void motrarPanel(){
		blnShowPanelInferior = true;
		blnVisible = false;
		/*Inicio Fecha*/
		java.util.Date date= new java.util.Date();
		perdidasSini.setTsSiniFechaRegistro(new Timestamp(date.getTime()));
		String fechaRegistro = Constante.sdf.format(perdidasSini.getTsSiniFechaRegistro());
		setDaFechaRegistro(fechaRegistro);
	}

	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Carga el combo de Persona Juridica.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Retorna los combos cocn los valores persona juridica y RUC;
	 * @throws BusinessException, NumberFormatException, EJBFactoryException
	 */	
	public void reloadCboPersonaJuridica(ActionEvent event) throws EJBFactoryException, NumberFormatException, BusinessException{
		recargarCboPersonaJuridica();
	}
	
	public void recargarCboPersonaJuridica() throws BusinessException{
		String pIntPersonaJuridica = FacesContextUtil.getRequestParameter("pPersonaJuridica");
		System.out.println("pIntTipoPersona: "+pIntPersonaJuridica);
		TablaFacadeRemote tablaFacade= null;
		cboPersonaJuridica = new ArrayList<SelectItem>();
		if(pIntPersonaJuridica==null){
			return;
		}
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);	
			Integer intTipoPersona = Integer.valueOf(pIntPersonaJuridica);
			
			listaPersonaJuridica = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_OPCIONPERSONABUSQUEDA));
			
			if(listaPersonaJuridica!=null){
				for(Tabla tabla : listaPersonaJuridica){
					if(intTipoPersona!=null && intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
						if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE) ||
								tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
							cboPersonaJuridica.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else if(intTipoPersona!=null && intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
						if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE) ||
								tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
							cboPersonaJuridica.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else{
						cboPersonaJuridica.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
					}
				}
			}
			log.info("liostacombo"+cboPersonaJuridica);
		} catch (Exception e) {
			log.info("debug combo");
		}
	}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Habilita el campo de texto del buscar Persona Juridica
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Se puede buscar por el nombre y el RUC de la persona Juridica
	 */	
	public void disableTxtJuridicaBusq(ActionEvent event){
		log.info("pFiltroPersonaBusq: "+FacesContextUtil.getRequestParameter("pFiltroPersonaBusq"));
		String pFiltroCombo = FacesContextUtil.getRequestParameter("pFiltroPersonaBusq");
		if(pFiltroCombo.equals("0")){
			setIsDisabledTxtJuridicaBusq(true);
		}else{
			setIsDisabledTxtJuridicaBusq(false);
		}
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Busca a la persona juridica en la base de datos
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Retorna una lista de la Persona Juridica.
	 * @throws BusinessException, EJBFactoryException
	 */	
	public List<PerdidasSiniestro> buscarPerJurdica() throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging HojaManualController.buscarPersona-------------------------------------");
		PerdidasSiniestro beanJuridicaBusq = new PerdidasSiniestro();
		if(getIntCboTipoJuridicaBusq()!=null && !getIntCboTipoJuridicaBusq().equals(0)){
			if(getIntCboTipoJuridicaBusq().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				if(getIntCboFiltroJuridicaBusq()!=null && !getIntCboFiltroJuridicaBusq().equals(0)){
					if(getIntCboFiltroJuridicaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
						beanJuridicaBusq.setIntCodigoBuscar(getIntCboFiltroJuridicaBusq());
						beanJuridicaBusq.setStrNombreBuscar(getStrTxtJuridticaBusq());
					}else if(getIntCboFiltroJuridicaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
						beanJuridicaBusq.setIntCodigoBuscar(getIntCboFiltroJuridicaBusq());
						beanJuridicaBusq.setStrNombreBuscar(getStrTxtJuridticaBusq());
					}
				}
				PerdidasSiniestroFacadeLocal perdidasFacade = (PerdidasSiniestroFacadeLocal)EJBFactory.getLocal(PerdidasSiniestroFacadeLocal.class);
				listaJuridica = perdidasFacade.getListaJuridicaRuc(beanJuridicaBusq);
			}
		}
		return listaJuridica;
	}
	
	public void limpiarAsegurador(){
		intCboTipoJuridicaBusq=0;
		listaJuridica= new ArrayList<PerdidasSiniestro>();
		cboPersonaJuridica = new ArrayList<SelectItem>();
		setStrTxtJuridticaBusq("");
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Carga el combo de Persona.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Retorna los combos cocn los valores nombre y RUC.
	 * @throws BusinessException, NumberFormatException, EJBFactoryException
	 */	
	public void reloadCboFiltroPersona(ActionEvent event) throws EJBFactoryException, NumberFormatException, BusinessException{
		recargarCboFiltroPersona();
	}
	
	public void recargarCboFiltroPersona() throws BusinessException{
		String pIntTipoPersona = FacesContextUtil.getRequestParameter("pTipoPersona");
		System.out.println("pIntTipoPersona: "+pIntTipoPersona);
		TablaFacadeRemote tablaFacade= null;
		cboFiltroPersona = new ArrayList<SelectItem>();
		if(pIntTipoPersona==null){
			return;
		}
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);	
			Integer intTipoPersona = Integer.valueOf(pIntTipoPersona);
			
			listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_OPCIONPERSONABUSQUEDA));
			
			if(listTabla!=null){
				for(Tabla tabla : listTabla){
					if(intTipoPersona!=null && intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
						if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE) ||
								tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
							cboFiltroPersona.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else if(intTipoPersona!=null && intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
						if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE) ||
								tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
							cboFiltroPersona.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else{
						cboFiltroPersona.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
					}
				}
			}
			log.info("liostacombo"+cboFiltroPersona);
		} catch (Exception e) {
			log.info("debug combo");
		}
	}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Activa el campo de texto del buscar persona.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 */	
	public void disableTxtPersonaBusq(ActionEvent event){
		log.info("pFiltroPersonaPerdi: "+FacesContextUtil.getRequestParameter("pFiltroPersonaPerdi"));
		String pFiltroCombo = FacesContextUtil.getRequestParameter("pFiltroPersonaPerdi");
		if(pFiltroCombo.equals("0")){
			setIsDisabledTxtPersonaBusq(true);
		}else{
			setIsDisabledTxtPersonaBusq(false);
		}
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Busca a la persona con rol 5 en la base de datos 
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Retorna una lista de todas las personas con rol 5.
	 * @throws BusinessException, EJBFactoryException
	 */	
	public List<Impuesto> buscarPersona() throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging HojaManualController.buscarPersona-------------------------------------");
		Impuesto beanPersonaBusq = new Impuesto();
		if(getIntCboTipoPersonaBusq()!=null && !getIntCboTipoPersonaBusq().equals(0)){
			if(getIntCboTipoPersonaBusq().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				if(getIntCboFiltroPersonaBusq()!=null && !getIntCboFiltroPersonaBusq().equals(0)){
					if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
						beanPersonaBusq.setIntImpuPeriodo(getIntCboFiltroPersonaBusq());
						beanPersonaBusq.setStrNombreCompleto(getStrTxtPersonaBusq());
					}else if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
						beanPersonaBusq.setIntImpuPeriodo(getIntCboFiltroPersonaBusq());
						beanPersonaBusq.setStrNombreCompleto(getStrTxtPersonaBusq());
					}
				}
				ImpuestoFacadeLocal personaFacade = (ImpuestoFacadeLocal)EJBFactory.getLocal(ImpuestoFacadeLocal.class);
				
				listaPersona = personaFacade.getListaNombreDniRol(beanPersonaBusq);
				
			}
		}
		return listaPersona;
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Metodo que se activa al momento que seleccionamos a una persona Juridica del popup
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Muestra el nombre completo de la persona juridica en el formulario.
	 */	
	public void seleccionarJuridica(){
		log.info("-----------------------Debugging HojaManualController.seleccionarCuenta()-----------------------------");
		String strRect = (String) FacesContextUtil.getRequestParameter("pRowRect");
		log.info("strCuenta: "+strRect);
//		setPerdidasSini(listaJuridica.get(Integer.valueOf(strRect)));
		setSeleccionPerdido(listaJuridica.get(Integer.valueOf(strRect)));
		setStrNombreJuridico(seleccionPerdido.getStrNombreJuridica());
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Metodo que se activa al momento que seleccionamos a una persona con rol 5 del popup.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Retorna El nombre completo de la persona.
	 */	
	public void seleccionarPersPerdido(){
		log.info("-----------------------Debugging HojaManualController.seleccionarCuenta()-----------------------------");
		String strPerdida = (String) FacesContextUtil.getRequestParameter("pRowPerdida");
		log.info("strCuenta: "+strPerdida);
		setImpuesto(listaPersona.get(Integer.valueOf(strPerdida)));
		setStrConcatenado(getImpuesto().getStrNombreCompleto());//+" -  DNI : "+getImpuesto().getStrNumeroDocumento());
		setIntIdPersona(getImpuesto().getIntPersPerson());
	}
	
	//limpia el popup de la persona con rol 3
	public void limpiar(){
		intCboTipoPersonaBusq = 0;
		cboFiltroPersona = new ArrayList<SelectItem>();
		listaPersona = new ArrayList<Impuesto>();
		strTxtPersonaBusq = "";
	}
	
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Metodo que permite grabar un registro en la base de datos. Permite modificar el registro seleccionad de la base de datos.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Ingresao y/o modifica valores en la base de datos.
	 * @throws BusinessException.
	 */	
	public void guardarPerdidas() throws BusinessException{
		PerdidasSiniestro peridas =null;
		//Validaciones
	if(perdidasSini.getId()==null || perdidasSini.getId().getIntContItemSiniestro()==null){
		if(!validarPerdido()){
			return;
		}
	}else{
		if(!validarModificacionPerdido()){
			return;
		}
	}
		if(perdidasSini.getId()==null || perdidasSini.getId().getIntContItemSiniestro()==null){
			perdidasSini.setId(new PerdidasSiniestroId());
			perdidasSini.getId().setIntPersEmpresa(IDEMPRESA_SESION);
			perdidasSini.setIntParaDocumentoGeneral(Constante.PARAM_T_TIPO_SOLICITUD_DE_SINIESTRO);
			perdidasSini.setIntPersEmpresaAseguraPk(IDEMPRESA_SESION);
			perdidasSini.setIntPersEmpresaAfectadoPk(IDEMPRESA_SESION);
			java.util.Date date= new java.util.Date();
			perdidasSini.setTsSiniFechaRegistro(new Timestamp(date.getTime()));
			perdidasSini.setIntPersEmpresaUsuarioPk(IDEMPRESA_SESION);
			perdidasSini.setIntPersPersonaUsuarioPk(IDUSUARIO_SESION);
			perdidasSini.setIntParaEstadoCod(1);
			perdidasSini.setIntParaEstadoCobroCod(1);
			perdidasSini.setIntPersonaAfectadoPk(getIntIdPersona());
			perdidasSini.setIntPersPersonaAseguraPk(seleccionPerdido.getIntIdPersJuridica());
			
			//facade que graga en la base de datos
			perdidasFacade.grabarPerdidasSiniestro(perdidasSini);
			if(impuesto!=null){
				blnVisible = true;
				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
			}
		}else{
			if(seleccionPerdido.getIntIdPersJuridica()!=null){
			if(perdidasSini.getIntPersPersonaAseguraPk()!=seleccionPerdido.getIntIdPersJuridica()){
				perdidasSini.setIntPersPersonaAseguraPk(seleccionPerdido.getIntIdPersJuridica());
			}
		}else{
			perdidasSini.getIntPersPersonaAseguraPk();
		}
			///////////
			if(getIntIdPersona()!=null){
				if(perdidasSini.getIntPersonaAfectadoPk()!=getIntIdPersona()){
					perdidasSini.setIntPersonaAfectadoPk(getIntIdPersona());
				}
				}else{
					perdidasSini.getIntPersonaAfectadoPk();
			}
			peridas = perdidasFacade.modificarPerdidasSiniestro(perdidasSini);
			
			if(peridas!=null){
				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_MODIFICAR);
//				listaBusqImpuesto = new ArrayList<Impuesto>();
			}
			blnShowPanelInferior = false;
		}
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Metodo que perimite validar los datos al momento de registrar a una nueva persona.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return En caso se tenga valores  nulos o vacios nos retorna el mensaje segun lo que el formulario requiera.
	 */	
	public boolean validarPerdido(){
		boolean isValidPerdido = true;
		FacesContextUtil.setMessageError("");
		if(perdidasSini!=null){
			if (seleccionPerdido.getIntIdPersJuridica()==null){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de seleccionar una Aseguradora es un Campo Obligatorio");
			}
			if (perdidasSini.getIntParaTipoSiniestro().equals(0)){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de seleccionar el Tipo de Siniestro es un Campo Obligatorio");
			}
			if (perdidasSini.getDtSiniFechaSiniestro()==null){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de seleccionar una fecha el Campo Obligatorio");
			}
			if (getIntIdPersona()==null){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de seleccionar al Personal Afectado el Campo Obligatorio");
			}
			if (perdidasSini.getBdSiniMontoDado()==null){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de ingresar el Importe de Daños es Obligatorio");
			}
			if (perdidasSini.getIntParaTipoMonedaCod().equals(0)){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de seleccionar el tipo de moneda de Importe de Daños es Obligatorio");
			}
			if (perdidasSini.getBdSiniMontoAsegurado()==null){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de ingresar el Monto Asegurado es un Campo Obligatorio");
			}
			if (perdidasSini.getIntParaTipoMonedaAsegCod().equals(0)){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de Seleccionar el tipo de moneda del Monto Asegurado");
			}
			if (perdidasSini.getStrSiniGlosa().isEmpty()){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de ingresar una Descripción en la Glosa es Obligatorio");
			}
			
		}
		return isValidPerdido;
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Metodo que perimite validar los datos al momento de realizar una modificaion en la base de datos.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return En caso se tenga valores  nulos o vacios nos retorna el mensaje segun lo que el formulario requiera.
	 */	
	public boolean validarModificacionPerdido(){
		boolean isValidPerdido = true;
		FacesContextUtil.setMessageError("");
		if(perdidasSini!=null){
			if (perdidasSini.getIntIdPersJuridica()==null){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de seleccionar una Aseguradora es un Campo Obligatorio");
			}
			if (perdidasSini.getIntParaTipoSiniestro().equals(0)){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de seleccionar el Tipo de Siniestro es un Campo Obligatorio");
			}
			if (perdidasSini.getDtSiniFechaSiniestro()==null){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de seleccionar una fecha el Campo Obligatorio");
			}
			if (perdidasSini.getIntPersonaAfectadoPk()==null){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de seleccionar al Personal Afectado el Campo Obligatorio");
			}
			if (perdidasSini.getBdSiniMontoDado()==null){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de ingresar el Importe de Daños es Obligatorio");
			}
			if (perdidasSini.getIntParaTipoMonedaCod().equals(0)){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de seleccionar el tipo de moneda de Importe de Daños es Obligatorio");
			}
			if (perdidasSini.getBdSiniMontoAsegurado()==null){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de ingresar el Monto Asegurado es un Campo Obligatorio");
			}
			if (perdidasSini.getIntParaTipoMonedaAsegCod().equals(0)){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de Seleccionar el tipo de moneda del Monto Asegurado");
			}
			if (perdidasSini.getStrSiniGlosa().isEmpty()){
				isValidPerdido = false;
				FacesContextUtil.setMessageError("Debe de ingresar una Descripción en la Glosa es Obligatorio");
			}
			
		}
		return isValidPerdido;
	}

	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Metodo que nos permite realizar la busqueda principal del formulario
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Retorna una lista con todos los datos de la base de datos.
	 * @throws BusinessException.
	 */	
	public void buscarPerdido() throws BusinessException{
		
		if(getIntCboFiltroPerdidoBusq()!=0){
			perdidasSiniBusq.setIntCodigoBuscar(getIntCboFiltroPerdidoBusq());
		}
		if(!getStrTxtPerdidoBusq().isEmpty()){
			perdidasSiniBusq.setStrNombreBuscar(getStrTxtPerdidoBusq());
		}
		listaBuscarPrincipal = perdidasFacade.getListaBuscar(perdidasSiniBusq);
		System.out.println("lista Impuesto: "+listaBuscarPrincipal);
		if(listaBuscarPrincipal!=null)System.out.println("Lista Impuesto: "+listaBuscarPrincipal.size());
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Este metodo recarga el combo del buscar general.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Retorna los valores en el combo segun la seleccion del usuario.
	 * @throws BusinessException.
	 */	
	public void recargarCboFiltroPerdidoCab() throws BusinessException{
		String pIntTipoPerdido = FacesContextUtil.getRequestParameter("pTipoPerdidoCab");
		System.out.println("pIntTipoPersona: "+pIntTipoPerdido);
		TablaFacadeRemote tablaFacade= null;
		cboFiltroPerdidaCab = new ArrayList<SelectItem>();
		if(pIntTipoPerdido==null){
			return;
		}
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);	
			Integer intTipoPersona = Integer.valueOf(pIntTipoPerdido);
			
			listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_OPCIONPERSONABUSQUEDA));
			
			if(listTabla!=null){
				for(Tabla tabla : listTabla){
					if(intTipoPersona!=null && intTipoPersona.equals(Constante.PARAM_T_ASEGURADORA)){
						if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_ASEGURADORA_RAZON_SOCIAL) ||
								tabla.getIntIdDetalle().equals(Constante.PARAM_T_ASEGURADORA_RUC)){
							cboFiltroPerdidaCab.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else if(intTipoPersona!=null && intTipoPersona.equals(Constante.PARAM_T_PERSONAL_AFECTADO)){
						if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_PERSONAL_AFECTADO_NOMBRE) ||
								tabla.getIntIdDetalle().equals(Constante.PARAM_T_PERSONAL_AFECTADO_DNI)){
							cboFiltroPerdidaCab.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else{
						cboFiltroPerdidaCab.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
					}
				}
			}
			log.info("liostacombo"+cboFiltroPerdidaCab);
		} catch (Exception e) {
			log.info("debug combo");
		}
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Metodo que activa el campo de texto de la busqueda.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return Retorna los datos segun lo buscado en el formulario.
	 * @throws BusinessException.
	 */	
	public void disableTxtPerdidoBusqCab(){
		log.info("pFiltroPerdidoBusq: "+FacesContextUtil.getRequestParameter("pFiltroPerdidoBusq"));
		String pFiltroCombo = FacesContextUtil.getRequestParameter("pFiltroPerdidoBusq");
		if(pFiltroCombo.equals("0")){
			setIsDisabledTxtPerdidoBusq(true);
		}else{
			setIsDisabledTxtPerdidoBusq(false);
		}
	}
	//metodo que me permite seleccionar en la lista buscar
	public void seleccionarPerdido(ActionEvent event){
		btnVer = false;
		btnModificar = false;
		btnEliminar = false;
		blnVisible = false;
		perdidasSini = (PerdidasSiniestro)event.getComponent().getAttributes().get("itemPerdidoBusq");
		
		if(perdidasSini.getIntParaEstadoCod()==1 && perdidasSini.getIntParaEstadoCobroCod()==1){
			btnVer = false;
			btnModificar = true;
			btnEliminar = true;
			blnVisible = false;
		}else if(perdidasSini.getIntParaEstadoCod()==1 && perdidasSini.getIntParaEstadoCobroCod()==2){
			btnVer = true;
			btnModificar = false;
			btnEliminar = false;
			blnVisible = true;
		}else if(perdidasSini.getIntParaEstadoCod()==3 && (perdidasSini.getIntParaEstadoCobroCod()==1 || perdidasSini.getIntParaEstadoCobroCod()==2)){
			btnVer = true;
			btnModificar = false;
			btnEliminar = false;
			blnVisible = true;
		}
		
		System.out.println("Que hay aca " + perdidasSini);
	}
	
	//metodo que me permite modificar
	public void modificarValores(){
		blnShowPanelInferior = true;
		setStrConcatenado(perdidasSini.getStrNombreCompletoPerdido());
//		String fecha = Constante.sdf.format(perdidasSini.getDtSiniFechaSiniestro());
//		setDaFechaRegistro(fecha);
		perdidasSini.setIntParaDocumentoGeneral(400);
		perdidasSini.setIntPersEmpresaAseguraPk(IDEMPRESA_SESION);
		perdidasSini.setIntPersEmpresaAfectadoPk(IDEMPRESA_SESION);
		perdidasSini.setIntPersEmpresaUsuarioPk(IDEMPRESA_SESION);
		perdidasSini.setIntPersPersonaAseguraPk(perdidasSini.getIntIdPersJuridica());
		seleccionPerdido.setStrNombreJuridica(perdidasSini.getStrNombreJuridica());
		/*Inicio Fecha*/
//		java.util.Date date= new java.util.Date();
//		perdidasSini.setTsSiniFechaRegistro(new Timestamp(date.getTime()));
		String fechaRegistro = Constante.sdf.format(perdidasSini.getTsSiniFechaRegistro());
		setDaFechaRegistro(fechaRegistro);
	}
	
	//Metodo que me permite visualizar el formulario inferior
	public void verValores(){
		blnShowPanelInferior = true;
		setStrConcatenado(perdidasSini.getStrNombreCompletoPerdido());
//		String fecha = Constante.sdf.format(perdidasSini.getDtSiniFechaSiniestro());
//		setDaFechaRegistro(fecha);
		seleccionPerdido.setStrNombreJuridica(perdidasSini.getStrNombreJuridica());
		String fechaRegistro = Constante.sdf.format(perdidasSini.getTsSiniFechaRegistro());
		setDaFechaRegistro(fechaRegistro);
	}
	
	//Metodo que cambia de estado 1 a 3 
	public void eliminarRegistro(){
		PerdidasSiniestro perdi = new PerdidasSiniestro();
		perdidasSini.setIntParaDocumentoGeneral(400);
		perdidasSini.setIntPersEmpresaAseguraPk(IDEMPRESA_SESION);
		perdidasSini.setIntPersEmpresaAfectadoPk(IDEMPRESA_SESION);
		perdidasSini.setIntPersEmpresaUsuarioPk(IDEMPRESA_SESION);
		perdidasSini.setIntPersPersonaAseguraPk(perdidasSini.getIntIdPersJuridica());
		if(perdidasSini.getIntParaEstadoCod()==1){
			perdidasSini.setIntParaEstadoCod(3);
		}
		try {
			perdi = perdidasFacade.modificarPerdidasSiniestro(perdidasSini);
			if(perdi!=null){
				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ELIMINAR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Getters y Setters
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		return sesion.getAttribute(beanName);
	}
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	public Integer getIDUSUARIO_SESION() {
		return IDUSUARIO_SESION;
	}

	public void setIDUSUARIO_SESION(Integer iDUSUARIO_SESION) {
		IDUSUARIO_SESION = iDUSUARIO_SESION;
	}

	public Integer getIDEMPRESA_SESION() {
		return IDEMPRESA_SESION;
	}

	public void setIDEMPRESA_SESION(Integer iDEMPRESA_SESION) {
		IDEMPRESA_SESION = iDEMPRESA_SESION;
	}

	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		PerdidasSiniestroController.log = log;
	}

	public Boolean getBlnShowPanelInferior() {
		return blnShowPanelInferior;
	}

	public void setBlnShowPanelInferior(Boolean blnShowPanelInferior) {
		this.blnShowPanelInferior = blnShowPanelInferior;
	}

	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}

	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}

	public List<Tabla> getListaPersonaJuridica() {
		return listaPersonaJuridica;
	}

	public void setListaPersonaJuridica(List<Tabla> listaPersonaJuridica) {
		this.listaPersonaJuridica = listaPersonaJuridica;
	}

	public List<SelectItem> getCboPersonaJuridica() {
		return cboPersonaJuridica;
	}

	public void setCboPersonaJuridica(List<SelectItem> cboPersonaJuridica) {
		this.cboPersonaJuridica = cboPersonaJuridica;
	}

	public String getStrTxtJuridticaBusq() {
		return strTxtJuridticaBusq;
	}

	public void setStrTxtJuridticaBusq(String strTxtJuridticaBusq) {
		this.strTxtJuridticaBusq = strTxtJuridticaBusq;
	}

	public Boolean getIsDisabledTxtJuridicaBusq() {
		return isDisabledTxtJuridicaBusq;
	}

	public void setIsDisabledTxtJuridicaBusq(Boolean isDisabledTxtJuridicaBusq) {
		this.isDisabledTxtJuridicaBusq = isDisabledTxtJuridicaBusq;
	}

	public Integer getIntCboTipoJuridicaBusq() {
		return intCboTipoJuridicaBusq;
	}

	public void setIntCboTipoJuridicaBusq(Integer intCboTipoJuridicaBusq) {
		this.intCboTipoJuridicaBusq = intCboTipoJuridicaBusq;
	}

	public Integer getIntCboFiltroJuridicaBusq() {
		return intCboFiltroJuridicaBusq;
	}

	public void setIntCboFiltroJuridicaBusq(Integer intCboFiltroJuridicaBusq) {
		this.intCboFiltroJuridicaBusq = intCboFiltroJuridicaBusq;
	}

	public List<PerdidasSiniestro> getListaJuridica() {
		return listaJuridica;
	}

	public void setListaJuridica(List<PerdidasSiniestro> listaJuridica) {
		this.listaJuridica = listaJuridica;
	}

	public List<Tabla> getListTabla() {
		return listTabla;
	}

	public void setListTabla(List<Tabla> listTabla) {
		this.listTabla = listTabla;
	}

	public Integer getIntCboTipoPersonaBusq() {
		return intCboTipoPersonaBusq;
	}

	public void setIntCboTipoPersonaBusq(Integer intCboTipoPersonaBusq) {
		this.intCboTipoPersonaBusq = intCboTipoPersonaBusq;
	}

	public Integer getIntCboFiltroPersonaBusq() {
		return intCboFiltroPersonaBusq;
	}

	public void setIntCboFiltroPersonaBusq(Integer intCboFiltroPersonaBusq) {
		this.intCboFiltroPersonaBusq = intCboFiltroPersonaBusq;
	}

	public String getStrTxtPersonaBusq() {
		return strTxtPersonaBusq;
	}

	public void setStrTxtPersonaBusq(String strTxtPersonaBusq) {
		this.strTxtPersonaBusq = strTxtPersonaBusq;
	}

	public Boolean getIsDisabledTxtPersonaBusq() {
		return isDisabledTxtPersonaBusq;
	}

	public void setIsDisabledTxtPersonaBusq(Boolean isDisabledTxtPersonaBusq) {
		this.isDisabledTxtPersonaBusq = isDisabledTxtPersonaBusq;
	}

	public List<SelectItem> getCboFiltroPersona() {
		return cboFiltroPersona;
	}

	public void setCboFiltroPersona(List<SelectItem> cboFiltroPersona) {
		this.cboFiltroPersona = cboFiltroPersona;
	}

	public List<Impuesto> getListaPersona() {
		return listaPersona;
	}

	public void setListaPersona(List<Impuesto> listaPersona) {
		this.listaPersona = listaPersona;
	}

	public PerdidasSiniestro getPerdidasSini() {
		return perdidasSini;
	}

	public void setPerdidasSini(PerdidasSiniestro perdidasSini) {
		this.perdidasSini = perdidasSini;
	}

	public Impuesto getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}

	public String getStrConcatenado() {
		return strConcatenado;
	}

	public void setStrConcatenado(String strConcatenado) {
		this.strConcatenado = strConcatenado;
	}

	public Integer getIntIdPersona() {
		return intIdPersona;
	}

	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}

	public List<Tabla> getListTipoSiniestro() {
		return listTipoSiniestro;
	}

	public void setListTipoSiniestro(List<Tabla> listTipoSiniestro) {
		this.listTipoSiniestro = listTipoSiniestro;
	}

	public List<Tabla> getListMoneda() {
		return listMoneda;
	}

	public void setListMoneda(List<Tabla> listMoneda) {
		this.listMoneda = listMoneda;
	}

	public String getDaFechaRegistro() {
		return daFechaRegistro;
	}

	public void setDaFechaRegistro(String daFechaRegistro) {
		this.daFechaRegistro = daFechaRegistro;
	}

	public List<Tabla> getListActivo() {
		return listActivo;
	}

	public void setListActivo(List<Tabla> listActivo) {
		this.listActivo = listActivo;
	}

	public String getStrEstado() {
		return strEstado;
	}

	public void setStrEstado(String strEstado) {
		this.strEstado = strEstado;
	}

	public PerdidasSiniestroFacadeLocal getPerdidasFacade() {
		return perdidasFacade;
	}

	public void setPerdidasFacade(PerdidasSiniestroFacadeLocal perdidasFacade) {
		this.perdidasFacade = perdidasFacade;
	}

	public List<Tabla> getListActivoCab() {
		return listActivoCab;
	}

	public void setListActivoCab(List<Tabla> listActivoCab) {
		this.listActivoCab = listActivoCab;
	}

	public List<Tabla> getListPago() {
		return listPago;
	}

	public void setListPago(List<Tabla> listPago) {
		this.listPago = listPago;
	}

	public PerdidasSiniestro getPerdidasSiniBusq() {
		return perdidasSiniBusq;
	}

	public void setPerdidasSiniBusq(PerdidasSiniestro perdidasSiniBusq) {
		this.perdidasSiniBusq = perdidasSiniBusq;
	}

	public List<Tabla> getListTipo() {
		return listTipo;
	}

	public void setListTipo(List<Tabla> listTipo) {
		this.listTipo = listTipo;
	}

	public List<SelectItem> getCboFiltroPerdidaCab() {
		return cboFiltroPerdidaCab;
	}

	public void setCboFiltroPerdidaCab(List<SelectItem> cboFiltroPerdidaCab) {
		this.cboFiltroPerdidaCab = cboFiltroPerdidaCab;
	}

	public Integer getIntCboTipoPerdidoBusq() {
		return intCboTipoPerdidoBusq;
	}

	public void setIntCboTipoPerdidoBusq(Integer intCboTipoPerdidoBusq) {
		this.intCboTipoPerdidoBusq = intCboTipoPerdidoBusq;
	}

	public Integer getIntCboFiltroPerdidoBusq() {
		return intCboFiltroPerdidoBusq;
	}

	public void setIntCboFiltroPerdidoBusq(Integer intCboFiltroPerdidoBusq) {
		this.intCboFiltroPerdidoBusq = intCboFiltroPerdidoBusq;
	}

	public String getStrTxtPerdidoBusq() {
		return strTxtPerdidoBusq;
	}

	public void setStrTxtPerdidoBusq(String strTxtPerdidoBusq) {
		this.strTxtPerdidoBusq = strTxtPerdidoBusq;
	}

	public Boolean getIsDisabledTxtPerdidoBusq() {
		return isDisabledTxtPerdidoBusq;
	}

	public void setIsDisabledTxtPerdidoBusq(Boolean isDisabledTxtPerdidoBusq) {
		this.isDisabledTxtPerdidoBusq = isDisabledTxtPerdidoBusq;
	}

	public List<PerdidasSiniestro> getListaBuscarPrincipal() {
		return listaBuscarPrincipal;
	}

	public void setListaBuscarPrincipal(List<PerdidasSiniestro> listaBuscarPrincipal) {
		this.listaBuscarPrincipal = listaBuscarPrincipal;
	}

	public Boolean getBlnUpdating() {
		return blnUpdating;
	}

	public void setBlnUpdating(Boolean blnUpdating) {
		this.blnUpdating = blnUpdating;
	}

	public boolean isBtnVer() {
		return btnVer;
	}

	public void setBtnVer(boolean btnVer) {
		this.btnVer = btnVer;
	}

	public boolean isBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(boolean btnModificar) {
		this.btnModificar = btnModificar;
	}

	public boolean isBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(boolean btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public boolean isBlnVisible() {
		return blnVisible;
	}

	public void setBlnVisible(boolean blnVisible) {
		this.blnVisible = blnVisible;
	}

	public PerdidasSiniestro getSeleccionPerdido() {
		return seleccionPerdido;
	}

	public void setSeleccionPerdido(PerdidasSiniestro seleccionPerdido) {
		this.seleccionPerdido = seleccionPerdido;
	}

	public String getStrNombreJuridico() {
		return strNombreJuridico;
	}

	public void setStrNombreJuridico(String strNombreJuridico) {
		this.strNombreJuridico = strNombreJuridico;
	}

}
