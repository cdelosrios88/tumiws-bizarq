package pe.com.tumi.seguridad.permiso.controller;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
//import pe.com.tumi.common.util.ControllerFiller;
import pe.com.tumi.common.util.DaoException;
//import pe.com.tumi.empresa.domain.Empresa;
//import pe.com.tumi.empresa.domain.PerNatural;
//import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.domain.DataObjects;
import pe.com.tumi.seguridad.domain.SolicitudCambio;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.bean.MenuMsg;
import pe.com.tumi.seguridad.permiso.bean.SubMenuMsg;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;
import pe.com.tumi.seguridad.permiso.domain.TransaccionId;
import pe.com.tumi.seguridad.permiso.domain.composite.MenuComp;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeLocal;
import pe.com.tumi.seguridad.permiso.validador.MenuValidador;
import pe.com.tumi.seguridad.service.impl.AdminMenuServiceImpl;

public class AdminMenuController extends GenericController {
	private    AdminMenuServiceImpl adminMenuService;
	private	   	Integer				intCboEmpresaSoli;
	private	   	Integer				intCboDesarrolladorSoli;
	private	   	Integer				intCboSolicitanteSoli;
	private 	Integer				intTipoCambioSoli;
	private 	Integer				intEstadoSoli;
	private 	Integer				intClaseSoli;
	private 	Integer				intRangoFechSoli;
	private 	Date				dtFechRango1Soli;
	private 	Date				dtFechRango2Soli;
	private 	Boolean				blnAnexosSoli;
	private    	List				beanListDataObjects;
	private    	List				beanListSolicitudes;
	private    	List				beanListSolAgregadas = new ArrayList();
	private    	ArrayList			beanListAplicaciones;	
	private    	ArrayList			beanListTablas;
	private    	ArrayList			beanListVistas;
	private    	ArrayList			beanListTriggers;
	private		ArrayList			arrayDataObjects = new ArrayList();
	private	    ArrayList 			ArraySolicitudes   = new ArrayList();	
	private    	ArrayList<SelectItem> cboUsuarioSoli   = new ArrayList<SelectItem>();	
	private    	AdminMenu			beanMenu = new AdminMenu();
	private 	SolicitudCambio		beanSolicitud;
	private    	String 				strCboEmpresaMenu;
	private    	String				strCboSolMenu1;
	private    	String				strCboSolMenu2;
	private    	String				strCboSolMenu3;
	private    	String				strCboSolMenu4;
	private    	String 				strCboDesarrollador;
	private    	String 				strCboSolicitante;
	private    	String 				strCboCIO;
	private		String				strAplicacionesMenu;	
	private		String				strTablasMenu;
	private		String				strVistasMenu;
	private		String				strTriggersMenu;
	private 	String				strAdjuntarObjeto;
	private 	Boolean				blnAdminMenuRendered = false;
	private 	Boolean				blnSolicitudRendered = false;
	private    	StringUtils 		langValidator 	= new StringUtils();
	private 	Date				dtFechSolicitud;
	private 	Date				dtFechPrueba;
	private 	Date				dtFechEntrega;
	private 	SimpleDateFormat 	sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static Boolean 			blnAgregadas;
	private    	String				hiddenIdEmpresa;
	private    	ArrayList<SelectItem> 	cboMenu1 = new ArrayList<SelectItem>();	
	private    	ArrayList<SelectItem> 	cboMenu2 = new ArrayList<SelectItem>();
	private    	ArrayList<SelectItem> 	cboMenu3 = new ArrayList<SelectItem>();
	private    	ArrayList<SelectItem> 	cboMenu4 = new ArrayList<SelectItem>();
	private    	ArrayList<SelectItem> 	cboUsuario = new ArrayList<SelectItem>();
	private    	String				codTransaccion;
	private    	String				msgTxtPadre;

	public final static Integer MENU_NIVEL_UNO = new Integer(1);
	
	private Boolean esPopPupAccesible;
	private Boolean esPopPupValido;
	private List<Juridica> listaJuridicaEmpresa;
	private String strTipoMantMenu;
	
	private Transaccion menu;
	private MenuComp filtroMenu;
	private List<MenuComp> listaMenuComp;
	private List<List<Transaccion>> listaMenu;
	private Transaccion subMenu;
	private List<String> listaSelectRadioMenu;
	private List<Integer> listaSubMenuActivo;
	private Integer indiceMenuBusqueda;
	private MenuMsg msgMenu;
	private List<Tabla> listaTablaOrden;
	private Boolean bolSolicitudCambio;
	private Boolean bolAdministracionMenu;
	//--------------------------------------------------------------------------------------------------
	//Metodos Administracion de Menu
	//--------------------------------------------------------------------------------------------------
	
	public AdminMenuController() {
		inicio(null);
	}

	public void inicioMenu(ActionEvent event){
		strTipoMantMenu = Constante.MANTENIMIENTO_NINGUNO;
		menu = null;
		listaMenu = null;
		try{
			filtroMenu = new MenuComp();
			if(listaJuridicaEmpresa == null){
				PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				this.listaJuridicaEmpresa = facade.getListaJuridicaDeEmpresa();
			}
			filtroMenu.setListaStrIdTransaccion(new ArrayList<String>());
			filtroMenu.getListaStrIdTransaccion().add("0");
			filtroMenu.getListaStrIdTransaccion().add("0");
			filtroMenu.getListaStrIdTransaccion().add("0");
			if(listaMenuComp != null ){
				listaMenuComp.clear();
			}
			listaMenuComp = null;
		}catch(Exception e){
			log.error(e);
		}
	}
	
	public void inicio(ActionEvent event){
		PermisoPerfilId id = null;
		PermisoPerfil permiso = null;
		Integer MENU_SOLCITUDCAMBIO = 12;
		Integer MENU_ADMINISTRACIONMENU = 13;
		try{
			Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario != null){
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_SOLCITUDCAMBIO);
				id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
				PermisoFacadeLocal localPermiso = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				bolSolicitudCambio = (permiso == null)?true:false;
				id.setIntIdTransaccion(MENU_ADMINISTRACIONMENU);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				bolAdministracionMenu = (permiso == null)?true:false;
			}else{
				bolSolicitudCambio = false;
				bolAdministracionMenu = false;
			}
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}
	}
	
	public void onchangeEmpresa(ActionEvent event){
		List<Transaccion> listaMenuPrincipal = null;
		PermisoFacadeLocal facade = null;
		try{
			if(filtroMenu.getIntPersEmpresaPk().compareTo(new Integer(0))!=0){
				facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
				listaMenuPrincipal = facade.getListaTransaccionDePrincipalPorIdEmpresa(filtroMenu.getIntPersEmpresaPk()); 
				filtroMenu.setListaMenu01(listaMenuPrincipal);
			}else{
				filtroMenu.setListaMenu01(null);
			}
			if(listaMenuComp != null ){
				listaMenuComp.clear();
			}
			listaMenuComp = null;
		}catch(Exception e){
			log.error(e);
		}
	}
	
	public void onchangeNivel01(ActionEvent event){
		PermisoFacadeLocal facade = null;
		TransaccionId lId = null;
		List<Transaccion> listaMenuDeNivel = null;
		try{
			if(!filtroMenu.getListaStrIdTransaccion().get(0).equals("0")){
				facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
				lId = new TransaccionId();
				lId.setIntPersEmpresaPk(filtroMenu.getIntPersEmpresaPk());
				lId.setIntIdTransaccion(new Integer(filtroMenu.getListaStrIdTransaccion().get(0)));
				listaMenuDeNivel = facade.getListaTransaccionPorPkPadre(lId);
				if(listaMenuDeNivel != null && listaMenuDeNivel.size()>0){ 
					filtroMenu.setListaMenu02(listaMenuDeNivel);
				}else{
					filtroMenu.setListaMenu02(null);
				}
				filtroMenu.setListaMenu03(null);
			}
			if(listaMenuComp != null ){
				listaMenuComp.clear();
			}
			listaMenuComp = null;
		}catch(Exception e){
			log.error(e);
		}
	}
	
	public void onchangeNivel02(ActionEvent event){
		PermisoFacadeLocal facade = null;
		TransaccionId lId = null;
		List<Transaccion> listaMenuDeNivel = null;
		try{
			if(!filtroMenu.getListaStrIdTransaccion().get(1).equals("0")){	
				facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
				lId = new TransaccionId();
				lId.setIntPersEmpresaPk(filtroMenu.getIntPersEmpresaPk());
				lId.setIntIdTransaccion(new Integer(filtroMenu.getListaStrIdTransaccion().get(1)));
				listaMenuDeNivel = facade.getListaTransaccionPorPkPadre(lId);
				if(listaMenuDeNivel != null && listaMenuDeNivel.size()>0){
					filtroMenu.setListaMenu03(listaMenuDeNivel);
				}else{
					filtroMenu.setListaMenu03(null);
				}
			}
			if(listaMenuComp != null ){
				listaMenuComp.clear();
			}
			listaMenuComp = null;
		}catch(Exception e){
			log.error(e);
		}
	}
	
	public void buscarMenu(ActionEvent event){
		PermisoFacadeLocal facade = null;
		try{
			for(int i=0;i<filtroMenu.getListaStrIdTransaccion().size();i++){
				if(filtroMenu.getListaStrIdTransaccion().get(i).equals("0")){
					filtroMenu.getListaStrIdTransaccion().set(i,null);
				}
			}
			if(filtroMenu.getIntTipoMenu().compareTo(new Integer(0))==0){
				filtroMenu.setIntTipoMenu(null);
			}
			if(filtroMenu.getIntIdEstado().compareTo(new Integer(0))==0){
				filtroMenu.setIntIdEstado(null);
			}	
			facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
			listaMenuComp = facade.getListaMenuCompDeBusqueda(filtroMenu);
		}catch(Exception e){
			log.error(e);
		}
		
	}
	
	public void irGrabarMenu(ActionEvent event){
		//setBlnAdminMenuRendered(true);
		//limpiarFormAdminMenu();
		menu = new Transaccion();
		menu.setId(new TransaccionId());
		menu.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		strTipoMantMenu = Constante.MANTENIMIENTO_GRABAR;
		listaMenu = null;
		listaSelectRadioMenu = new ArrayList<String>();
		listaSubMenuActivo = new ArrayList<Integer>();
		msgMenu = new MenuMsg();
	}
	
	public void validarAgregarSubMenu(ActionEvent event){
		String pStrIntNivel = null;
		Integer intNivel = null;
		String pStrValor = null;
		Transaccion subMenuPare = null;
		List<Transaccion> listaSubMenu = null;
		try{
			esPopPupAccesible = new Boolean(true);
			pStrIntNivel = getRequestParameter("pIntNivel");
			if(pStrIntNivel!= null && !pStrIntNivel.trim().equals("")){
				intNivel = new Integer(pStrIntNivel);
				menu.setIntNivel(intNivel);
				if(intNivel > 1){
					pStrValor = listaSelectRadioMenu.get(intNivel-2);
					if(pStrValor == null){
						throw new Exception("debe seleccionar un registro en el nivel Anterior");
					}
					
					listaSubMenu = listaMenu.get(intNivel-2);
					subMenuPare = listaSubMenu.get(Integer.parseInt(pStrValor));
					if(subMenuPare.getIntCrecimiento().compareTo(Constante.PARAM_T_CRECIMIENTOMENU_HORIZONTAL)==0){
						throw new Exception("El nivel Superior es de Crecimiento Horizontal");
					}
				}
			}
		}catch(Exception e){
			esPopPupAccesible = new Boolean(false);
			log.error(e);
			setMessageError(e.getMessage());
		}
	}
	
	public void irAgregarSubMenuInicial(ActionEvent event){
		try{
			if(listaMenu == null){
				listaMenu = new ArrayList<List<Transaccion>>();
				menu.setIntNivel(new Integer(1));
			}
			irAgregarSubMenu();
			esPopPupAccesible = null;
		}catch(Exception e){
			log.error(e);
			setMessageError(e.getMessage());
		}
	}
	
	public void irAgregarSubMenu(ActionEvent event){
		String pStrIntNivel = null;
		Integer intNivel = null;
		try{
			pStrIntNivel = getRequestParameter("pIntNivel");
			if(pStrIntNivel!= null && !pStrIntNivel.trim().equals("")){
				intNivel = new Integer(pStrIntNivel);
				menu.setIntNivel(intNivel);
				irAgregarSubMenu();
				esPopPupAccesible = null;
			}
		}catch(Exception e){
			log.error(e);
			setMessageError(e.getMessage());
		}
	}

	private void irAgregarSubMenu() throws Exception{
		TablaFacadeRemote facade = null;
		Integer intNivel = null;
		Transaccion subMenuPare = null;
		List<Transaccion> listaSubMenu = null;
		Transaccion subMenuLoop = null;
		String csvIdDetalle = null;
		try{
			facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			intNivel = menu.getIntNivel();
			if(intNivel ==1){
				subMenu = new Transaccion();
				subMenu.setId(new TransaccionId());
				subMenu.setIntCrecimiento(Constante.PARAM_T_CRECIMIENTOMENU_VERTICAL);
				if(listaMenu.size() > 0 && listaMenu.get(0)!=null && listaMenu.get(0).size()>0){
					for(int i=0;i<listaMenu.get(0).size();i++){
						subMenuLoop = listaMenu.get(0).get(i);
						if(subMenuLoop.getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)!=0){
							if(csvIdDetalle == null){
								csvIdDetalle = String.valueOf(subMenuLoop.getIntOrden());
							}else{
								csvIdDetalle = csvIdDetalle + "," +subMenuLoop.getIntOrden();
							}
						}
					}
					if(csvIdDetalle != null)
						listaTablaOrden = facade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_ORDENMENU), csvIdDetalle);
					else
						listaTablaOrden = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ORDENMENU));
				}else{
					listaTablaOrden = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ORDENMENU));
				}
			}else{
				listaSubMenu = listaMenu.get(intNivel-2);
				subMenuPare = listaSubMenu.get(Integer.parseInt(listaSelectRadioMenu.get(intNivel-2)));
				subMenu = new Transaccion();
				subMenu.setId(new TransaccionId());
				if(subMenuPare.getIntFinal()!=null && subMenuPare.getIntFinal().compareTo(new Integer(1))==0){
					subMenu.setIntCrecimiento(Constante.PARAM_T_CRECIMIENTOMENU_HORIZONTAL);
				}else{
					subMenu.setIntCrecimiento(Constante.PARAM_T_CRECIMIENTOMENU_VERTICAL);
				}
				
				if(subMenuPare.getListaTransaccion()!= null && subMenuPare.getListaTransaccion().size()>0){
					for(int i=0;i<subMenuPare.getListaTransaccion().size();i++){
						subMenuLoop = subMenuPare.getListaTransaccion().get(i);
						if(subMenuLoop.getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)!=0){
							if(csvIdDetalle == null){
								csvIdDetalle = String.valueOf(subMenuLoop.getIntOrden());
							}else{
								csvIdDetalle = csvIdDetalle + "," +subMenuLoop.getIntOrden();
							}
						}
					}
					if(csvIdDetalle != null)
						listaTablaOrden = facade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_ORDENMENU), csvIdDetalle);
					else
						listaTablaOrden = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ORDENMENU));
				}else{
					listaTablaOrden = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ORDENMENU));
				}
			}
			
			subMenu.setIntFinal(new Integer(0));
			subMenu.setPersiste(new Boolean(false));
			subMenu.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			subMenu.setIntNivel(intNivel);
			
			//TODO asignacion del tipo de orden que tiene y los posibles ordenes que podria modificar
			msgMenu.setMsgSubMenu(new SubMenuMsg());
		}catch(Exception e){
			throw e; 
		}
	}
	
	public void onclickRadioDeSubMenu(ActionEvent event){
		int lIndiceMenu = 0;
		int lIndiceSubMenu = 0;
		List<Transaccion> listaSubMenu = null;
		Transaccion lSubMenu = null;
		String strIndiceMenu = null;
		String strIndiceSubMenu = null;
		try{
			strIndiceMenu = getRequestParameter("pIndiceMenu");
			strIndiceSubMenu = getRequestParameter("pIndiceSubMenu");
			if(strIndiceMenu != null && strIndiceSubMenu != null){
				lIndiceMenu = Integer.parseInt(strIndiceMenu);
				lIndiceSubMenu = Integer.parseInt(strIndiceSubMenu);
				listaSubMenu = listaMenu.get(lIndiceMenu);
				lSubMenu = listaSubMenu.get(lIndiceSubMenu);
				limpiarListaSubMenuPorNivelActual(lIndiceMenu);
				if(lSubMenu.getListaTransaccion()!=null){
					if(contieneActivo(lSubMenu.getListaTransaccion())){
						agregarListaSubMenuPorNivel(lSubMenu.getListaTransaccion());
					}
				}
			}
		}catch(Exception e){
			log.error(e);
		}
	}
	
	private void limpiarListaSubMenuPorNivelActual(int pNivelActual) throws Exception{
		int i = 0;
		try{
			i=pNivelActual+1;
			if(i<listaMenu.size()){
				do{
					listaMenu.remove(i);
					listaSelectRadioMenu.remove(i);
					listaSubMenuActivo.remove(i);
				}while(i<listaMenu.size());
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	private void agregarListaSubMenuPorNivel(List<Transaccion> listaTransaccion){
		Transaccion lTransaccion = null;
		listaMenu.add(listaTransaccion);
		listaSelectRadioMenu.add("0");
		listaSubMenuActivo.add(getIndexActivo(listaTransaccion));
		if(listaTransaccion.size()>0){
			lTransaccion = listaTransaccion.get(0);
			if(lTransaccion.getListaTransaccion() !=null){
				if(contieneActivo(lTransaccion.getListaTransaccion())){
					agregarListaSubMenuPorNivel(lTransaccion.getListaTransaccion());
				}
			}	
		}
	}
	
	private Integer getIndexActivo(List<Transaccion> listaTransaccion){
		boolean tieneAnulado = false;
		Integer indice = null;
		Transaccion lSubMenu = null;
		for(int i=0;i<listaTransaccion.size();i++){
			lSubMenu = listaTransaccion.get(i);
			if(lSubMenu.getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)==0){
				tieneAnulado = true;
				if(i>0){
					indice = new Integer(i);
				}else{
					indice = new Integer(0);
				}
				break;
			}
		}
		if(!tieneAnulado){
			indice = new Integer(listaTransaccion.size());
		}
		return indice;
	}
	
	private boolean contieneActivo(List<Transaccion> listaTransaccion){
		boolean contieneActivo = false;
		Transaccion lSubMenu = null;
		for(int i=0;i<listaTransaccion.size();i++){
			lSubMenu = listaTransaccion.get(i);
			if(lSubMenu.getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
				contieneActivo = true;
				break;
			}
		}
		return contieneActivo;
	}
	
	public void irModificarSubMenu(ActionEvent event){
		TablaFacadeRemote facade = null;
		Transaccion subMenuPadre = null;
		Transaccion subMenuLoop = null;
		List<Transaccion> listaSubMenu = null;
		String csvIdDetalle = null;
		Integer intNivel = null;
		try{
			irSubMenu();
			facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			
			intNivel = subMenu.getIntNivel();
			if(intNivel ==1){
				if(listaMenu.get(0)!=null && listaMenu.get(0).size()>0){
					for(int i=0;i<listaMenu.get(0).size();i++){
						subMenuLoop = listaMenu.get(0).get(i);
						if(subMenuLoop.getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)!=0){
							if(subMenuLoop.getIntOrden().compareTo(subMenu.getIntOrden()) != 0){
								if(csvIdDetalle == null){
									csvIdDetalle = String.valueOf(subMenuLoop.getIntOrden());
								}else{
									csvIdDetalle = csvIdDetalle + "," +subMenuLoop.getIntOrden();
								}
							}
						}	
					}
					if(csvIdDetalle!=null)
						listaTablaOrden = facade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_ORDENMENU), csvIdDetalle);
					else
						listaTablaOrden = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ORDENMENU));
				}else{
					listaTablaOrden = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ORDENMENU));
				}
			}else{
				listaSubMenu = listaMenu.get(intNivel.intValue()-2);
				subMenuPadre = listaSubMenu.get(Integer.parseInt(listaSelectRadioMenu.get(intNivel.intValue()-2)));
				
				if(subMenuPadre.getListaTransaccion()!= null && subMenuPadre.getListaTransaccion().size()>0){
					for(int i=0;i<subMenuPadre.getListaTransaccion().size();i++){
						subMenuLoop = subMenuPadre.getListaTransaccion().get(i);
						if(subMenuLoop.getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)!=0){
							if(subMenuLoop.getIntOrden().compareTo(subMenu.getIntOrden()) != 0){
								if(csvIdDetalle == null){
									csvIdDetalle = String.valueOf(subMenuLoop.getIntOrden());
								}else{
									csvIdDetalle = csvIdDetalle + "," +subMenuLoop.getIntOrden();
								}
							}
						}	
					}
					if(csvIdDetalle!=null)
						listaTablaOrden = facade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_ORDENMENU), csvIdDetalle);
					else
						listaTablaOrden = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ORDENMENU));
				}else{
					listaTablaOrden = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ORDENMENU));
				}
			}
		}catch(Exception e){
			log.error(e);
		}
	}
	
	public void irConsultarSubMenu(ActionEvent event){
		TablaFacadeRemote facade = null;
		try{
			irSubMenu();
			facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTablaOrden = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ORDENMENU));
		}catch(Exception e){
			log.error(e);
		}
	}
	
	private void irSubMenu(){
		Integer lIndiceMenu = null;
		Integer lIndicesubMenu = null;
		String pStrValor = null;
		List<Transaccion> listaSubMenu = null;
		
		pStrValor = getRequestParameter("pIndiceMenu");
		lIndiceMenu = new Integer(pStrValor);
		pStrValor = listaSelectRadioMenu.get(lIndiceMenu.intValue());
		lIndicesubMenu = new Integer(pStrValor);
		
		listaSubMenu = listaMenu.get(lIndiceMenu.intValue());
		subMenu = listaSubMenu.get(lIndicesubMenu.intValue());
		
		msgMenu.setMsgSubMenu(new SubMenuMsg());
	}
	
	public void eliminarSubMenu(ActionEvent event){
		Integer lIndiceMenu = null;
		Integer lIndicesubMenu = null;
		Integer lIndiceMenuPadre = null;
		String pStrValor = null;
		Transaccion lSubMenu = null;
		Transaccion lSubMenuTemp = null;
		List<Transaccion> listaSubMenu = null;
		List<Transaccion> listaSubMenuPadre = null;
		PermisoFacadeLocal facade = null;
		try{
			pStrValor = getRequestParameter("pIndiceMenu");
			lIndiceMenu = new Integer(pStrValor);
			pStrValor = listaSelectRadioMenu.get(lIndiceMenu.intValue());
			lIndicesubMenu = new Integer(pStrValor);
			
			listaSubMenu = listaMenu.get(lIndiceMenu.intValue());
			lSubMenu = listaSubMenu.get(lIndicesubMenu.intValue());
			if(lSubMenu.getId().getIntPersEmpresaPk()!= null && lSubMenu.getId().getIntIdTransaccion()!=null){
				facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
				lSubMenuTemp = facade.getTransaccionPorPk(lSubMenu.getId());
				if(lSubMenuTemp != null){
					listaSubMenu.remove(lIndicesubMenu.intValue());
					lSubMenu.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					listaSubMenu.add(lSubMenu);
					if(lSubMenu.getListaTransaccion()!=null && lSubMenu.getListaTransaccion().size()>0){
						eliminarsubMenu(lSubMenu.getListaTransaccion());
					}
				}else{
					listaSubMenu.remove(lIndicesubMenu.intValue());
				}
			}else{
				listaSubMenu.remove(lIndicesubMenu.intValue());
			}
			
			if(listaSubMenu.size()>0){
				if(contieneActivo(listaSubMenu)){
					listaSelectRadioMenu.set(lIndiceMenu.intValue(),"0");
					listaSubMenuActivo.set(lIndiceMenu.intValue(),getIndexActivo(listaSubMenu));
					lSubMenu = listaSubMenu.get(0);  
					limpiarListaSubMenuPorNivelActual(lIndiceMenu.intValue());
					if(lSubMenu.getListaTransaccion()!=null){
						agregarListaSubMenuPorNivel(lSubMenu.getListaTransaccion());
					}
				}else{
					if(lIndiceMenu > 0){
						lIndiceMenuPadre = lIndiceMenu -1;
						listaSelectRadioMenu.set(lIndiceMenuPadre.intValue(),"0");
						listaSubMenuPadre = listaMenu.get(lIndiceMenuPadre);
						listaSubMenuActivo.set(lIndiceMenuPadre.intValue(),getIndexActivo(listaSubMenuPadre));
						lSubMenu = listaSubMenuPadre.get(0);
						limpiarListaSubMenuPorNivelActual(lIndiceMenuPadre.intValue());
						if(lSubMenu.getListaTransaccion()!=null){
							agregarListaSubMenuPorNivel(lSubMenu.getListaTransaccion());
						}
					}else{
						limpiarListaSubMenuPorNivelActual(0);
					}
				}
			}else{
				if(lIndiceMenu.intValue()>0){
					pStrValor = listaSelectRadioMenu.get(lIndiceMenu.intValue()-1);
					lIndicesubMenu = new Integer(pStrValor);
					listaSubMenu = listaMenu.get(lIndiceMenu.intValue()-1);
					lSubMenu = listaSubMenu.get(lIndicesubMenu.intValue());
					lSubMenu.setListaTransaccion(null);
				}
				listaMenu.remove(lIndiceMenu.intValue());
				listaSelectRadioMenu.remove(lIndiceMenu.intValue());
				listaSubMenuActivo.remove(lIndiceMenu.intValue());
			}
		}catch(Exception e){
			log.error(e);
		}
	}
	
	private void eliminarsubMenu(List<Transaccion> listaSubMenu){
		Transaccion lSubMenu = null;
		for(int i=0;i<listaSubMenu.size();i++){
			lSubMenu = listaSubMenu.get(i);
			lSubMenu.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			if(lSubMenu.getListaTransaccion()!=null && lSubMenu.getListaTransaccion().size()>0){
				eliminarsubMenu(lSubMenu.getListaTransaccion());
			}
		}	
	}
	
	public void checkSubMenuFinal(ActionEvent event){
		String pStrInFinal = null; 
		pStrInFinal = getRequestParameter("pIntFinal");
		if(pStrInFinal.equals("0")){
			subMenu.setIntFinal(new Integer(1));
		}else{
			subMenu.setIntFinal(new Integer(0));
		}
	}
	
	public void validarSubMenu(ActionEvent event){
		esPopPupValido = MenuValidador.validarSubMenu(msgMenu, subMenu);
	}

	public void agregarSubMenu(ActionEvent event){
		List<Transaccion> listaSubMenu = null;
		Integer lIntIdNivel = null;
		int lIndiceMenuPadre = 0;
		int lIndiceSubMenuPadre = 0;
		List<Transaccion> listaSubMenuPadre = null;
		Transaccion lSubMenuPadre = null;
		try{
			lIntIdNivel = subMenu.getIntNivel().intValue();
			if(listaMenu.size() == lIntIdNivel.intValue()-1){
				listaMenu.add(new ArrayList<Transaccion>());
			}
			if(listaMenu.size() > lIntIdNivel.intValue()-1){
				listaSubMenu = listaMenu.get(lIntIdNivel.intValue()-1);
				if(listaSelectRadioMenu.size() == lIntIdNivel.intValue()-1){
					listaSelectRadioMenu.add(String.valueOf(listaSubMenu.size()));
				}else{
					listaSelectRadioMenu.set(lIntIdNivel.intValue()-1,
							String.valueOf(listaSubMenuActivo.get(lIntIdNivel.intValue()-1)));
				}
				if(lIntIdNivel.intValue()>1){
					lIndiceMenuPadre = lIntIdNivel.intValue()-2;
					lIndiceSubMenuPadre = Integer.parseInt(listaSelectRadioMenu.get(lIndiceMenuPadre));
					listaSubMenuPadre = listaMenu.get(lIndiceMenuPadre);
					lSubMenuPadre = listaSubMenuPadre.get(lIndiceSubMenuPadre);
				}
				if(listaSubMenu.size() > 0 && 
				   listaSubMenu.get(listaSubMenu.size()-1).getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)==0){
					Integer indiceActivo = listaSubMenuActivo.get(lIntIdNivel.intValue()-1);
					if(!contieneActivo(listaSubMenu)){
						listaSubMenu.add(0,subMenu);
						listaSelectRadioMenu.set(0,"0");
					}else{
						listaSubMenu.add(indiceActivo.intValue(),subMenu);
					}
					listaSubMenuActivo.set(lIntIdNivel.intValue()-1,getIndexActivo(listaSubMenu));
				}else{
					listaSubMenu.add(subMenu);
					if(lSubMenuPadre!= null){
						if(lSubMenuPadre.getListaTransaccion()==null){
							lSubMenuPadre.setListaTransaccion(listaSubMenu);
							listaSubMenuActivo.add(getIndexActivo(listaSubMenu));
						}else{
							if(!contieneActivo(lSubMenuPadre.getListaTransaccion())){
								lSubMenuPadre.getListaTransaccion().add(0,subMenu);
								listaSubMenu = lSubMenuPadre.getListaTransaccion();
								listaMenu.set(lIntIdNivel.intValue()-1,listaSubMenu);
								listaSubMenuActivo.add(lIntIdNivel.intValue()-1,getIndexActivo(listaSubMenu));
							}else{
								listaSubMenuActivo.set(lIntIdNivel.intValue()-1,getIndexActivo(listaSubMenu));
							}
						}
					}else{
						if(listaSubMenuActivo.size()==0)
							listaSubMenuActivo.add(getIndexActivo(listaSubMenu));
						else	
							listaSubMenuActivo.set(lIntIdNivel.intValue()-1,getIndexActivo(listaSubMenu));
					}
				}
				limpiarListaSubMenuPorNivelActual(lIntIdNivel.intValue()-1);
				esPopPupValido = new Boolean(false);
				msgMenu.setMsgSubMenu(null);
			}
		}catch(Exception e){
			log.error(e);
		}
	}
	
	public void modificarSubMenu(ActionEvent event){
		cerrarSubMenu();
	}
	
	public void cancelarSubMenu(ActionEvent event){
		cerrarSubMenu();
	}
	
	private void cerrarSubMenu(){
		subMenu = null;
		msgMenu.setMsgSubMenu(null);
		esPopPupValido = new Boolean(false);
	}
	
	public void grabarMenu(ActionEvent event) throws DaoException{
		PermisoFacadeLocal facade = null;
		Boolean bValidar = true;
		try{
		    bValidar = MenuValidador.validarMenu(msgMenu,menu,listaMenu.get(0));
		    if(bValidar == true){
		    	facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
		    	asociaDatoMenuASubMenu(listaMenu.get(0));
		    	facade.grabarMenu(listaMenu.get(0));
		    	msgMenu = null;
		    	strTipoMantMenu = Constante.MANTENIMIENTO_NINGUNO;
		    	buscarMenu(event);
		    }
		}catch(Exception e){
			log.error(e);
		}
	}

	private void asociaDatoMenuASubMenu(List<Transaccion> listaSubMenu){
		Transaccion lTransaccion = null;
		for(int i=0;i<listaSubMenu.size();i++){
			lTransaccion = listaSubMenu.get(i);
			lTransaccion.getId().setIntPersEmpresaPk(menu.getId().getIntPersEmpresaPk());
			lTransaccion.setIntTipoMenu(menu.getIntTipoMenu());
			if(lTransaccion.getListaTransaccion()!=null && lTransaccion.getListaTransaccion().size()>0){
				asociaDatoMenuASubMenu(lTransaccion.getListaTransaccion());
			}
		}
	}
	
	public void cancelarMenu(ActionEvent event){
		//log.info("-----------------------Debugging adminMenuController.cancelarGrabarMenu-----------------------------");
		//limpiarFormAdminMenu();
		//setBlnAdminMenuRendered(false);
		menu = null;
		strTipoMantMenu = Constante.MANTENIMIENTO_NINGUNO;
		listaMenu = null;
		listaSelectRadioMenu = null;
		msgMenu = null;
	}
		
	public void irModificarMenu(ActionEvent event){
		PermisoFacadeLocal facade = null;
		Transaccion dto = null;
		List<Transaccion> listaSubMenu = null;
		try{
			System.out.println(indiceMenuBusqueda.intValue());
			MenuComp lMenuComp = listaMenuComp.get(indiceMenuBusqueda.intValue());
			facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
			dto = facade.getMenu(lMenuComp.getIntPersEmpresaPk(),lMenuComp.getListaStrIdTransaccion());
			
			menu = new Transaccion();
			menu.setId(new TransaccionId());
			menu.getId().setIntPersEmpresaPk(dto.getId().getIntPersEmpresaPk());
			menu.setIntTipoMenu(dto.getIntTipoMenu());
			menu.setIntIdEstado(dto.getIntIdEstado());
			
			listaMenu = new ArrayList<List<Transaccion>>();
			listaSelectRadioMenu = new ArrayList<String>();
			listaSubMenuActivo = new ArrayList<Integer>();
			listaSubMenu = new ArrayList<Transaccion>();
			listaSubMenu.add(dto);
			listaMenu.add(listaSubMenu);
			listaSelectRadioMenu.add("0");
			listaSubMenuActivo.add(new Integer(listaSubMenu.size()));
			if(dto.getListaTransaccion()!= null && dto.getListaTransaccion().size()>0){
				agregarListaSubMenuPorNivel(dto.getListaTransaccion());
			}
			strTipoMantMenu = Constante.MANTENIMIENTO_MODIFICAR;
			msgMenu = new MenuMsg();
		}catch(Exception e){
			log.error(e);
		}
	}
	
	public void modificarMenu(ActionEvent event) throws DaoException{
		PermisoFacadeLocal facade = null;
		Boolean bValidar = true;
		try{
		    bValidar = MenuValidador.validarMenu(msgMenu,menu,listaMenu.get(0));
		    if(bValidar == true){
		    	facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
		    	asociaDatoMenuASubMenu(listaMenu.get(0));
		    	facade.grabarMenu(listaMenu.get(0));
		    	msgMenu = null;
		    	strTipoMantMenu = Constante.MANTENIMIENTO_NINGUNO;
		    	buscarMenu(event);
		    }
		}catch(Exception e){
			log.error(e);
		}
	}
	
	/*public void eliminarMenu(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging AdminMenuController.eliminarEmpresa-----------------------------");
		setService(adminMenuService);
		String strMenuId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMenuPanel:hiddenIdMenu");
		log.info("strMenuId = "+strMenuId);

		HashMap prmtMenu = new HashMap();
		prmtMenu.put("prmIdMenu", strMenuId);
		getService().eliminarMenu(prmtMenu);
		log.info("Se ha eliminado el menu: "+strMenuId);
		//listarMenues(event);
		arrayDataObjects.clear();
	}*/
	
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
		
		if(beanListAplicaciones==null || beanListAplicaciones.size()==0){
			beanListAplicaciones = new ArrayList();
			setBeanListAplicaciones(arrayAplicaciones);
		}
		
		if(beanListTablas==null || beanListTablas.size()==0){
			beanListTablas = new ArrayList();
			setBeanListTablas(arrayTablas);
		}
		
		if(beanListVistas==null || beanListVistas.size()==0){
			beanListVistas = new ArrayList();
			setBeanListVistas(arrayVistas);
		}
		
		if(beanListTriggers==null || beanListTriggers.size()==0){
			beanListTriggers = new ArrayList();
			setBeanListTriggers(arrayTriggers);
		}
		
		if(tipoObjeto.equals("tablas")){
			setStrAdjuntarObjeto("adjuntarTablas");
			setBeanListDataObjects(beanListTablas);
		}else if(tipoObjeto.equals("vistas")){
			setStrAdjuntarObjeto("adjuntarVistas");
			setBeanListDataObjects(beanListVistas);
		}else if(tipoObjeto.equals("triggers")){
			setStrAdjuntarObjeto("adjuntarTriggers");
			setBeanListDataObjects(beanListTriggers);
		}else if(tipoObjeto.equals("aplicaciones")){
			setStrAdjuntarObjeto("adjuntarAplicaciones");
			setBeanListDataObjects(beanListAplicaciones);
		}
	}
	
	public void adjuntarDataObjects(ActionEvent event) throws DaoException{
		ArrayList arrayDBObjects = new ArrayList();
		arrayDBObjects = (ArrayList) getBeanListDataObjects();
		String strDataObjects = "";
		
		for(int i=0; i<arrayDBObjects.size(); i++){
			DataObjects tb = new DataObjects();
			tb = (DataObjects)arrayDBObjects.get(i);
			if(tb.getBlnSelectedObjecto()==true){
				strDataObjects = strDataObjects +", "+tb.getStrNombreObjeto();
			}
		}
		
		if(!strDataObjects.equals("")){
			strDataObjects = strDataObjects.substring(2,strDataObjects.length());
		}
		if(getStrAdjuntarObjeto().equals("adjuntarTablas")){
			setStrTablasMenu(strDataObjects);
		}else if(getStrAdjuntarObjeto().equals("adjuntarVistas")){
			setStrVistasMenu(strDataObjects);
		}else if(getStrAdjuntarObjeto().equals("adjuntarTriggers")){
			setStrTriggersMenu(strDataObjects);
		}else if(getStrAdjuntarObjeto().equals("adjuntarAplicaciones")){
			setStrAplicacionesMenu(strDataObjects);
		}
	}
	
	//-------------------------------------------------------------------------------------------------------------------------------------------
	// Metodos para los controles
	//-------------------------------------------------------------------------------------------------------------------------------------------
	
	public void limpiarFormAdminMenu(){
		AdminMenu menu = new AdminMenu();
		setBeanMenu(menu);
		limpiarTablaPopup();
	}
	
	public void limpiarTablaPopup(){
		if(beanListAplicaciones!=null)beanListAplicaciones.clear();
		if(beanListTablas!=null)beanListTablas.clear();
		if(beanListVistas!=null)beanListVistas.clear();
		if(beanListTriggers!=null)beanListTriggers.clear();
		setStrAplicacionesMenu("");
		setStrTablasMenu("");
		setStrVistasMenu("");
		setStrTriggersMenu("");
		this.cboMenu1.clear();
    	this.cboMenu2.clear();
    	this.cboMenu3.clear();
    	this.cboMenu4.clear();
	}
	
	
	//-------------------------------------------------------------------------------------------------------------------
	//SOLICITUD DE CAMBIO
	//-------------------------------------------------------------------------------------------------------------------
	public void agregarSolicitudes(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging adminMenuController.agregarSolicitudes-----------------------------");
		Boolean bValidar = true;
		//Registro de solicitud
	    SolicitudCambio sol = new SolicitudCambio();
	    
	    sol = (SolicitudCambio) getBeanSolicitud();	    	    
	    if(sol.getIntTipoCambio()==0){
	    	bValidar = false;
	    	setMessageError("Debe seleccionar el campo Tipo de Cambio.");
	    }else{
	    	bValidar = true;
	    }
	    if(sol.getIntIdEstado()==0){
	    	bValidar = false;
	    	setMessageError("Debe seleccionar el campo Estado.");
	    }else{
	    	bValidar = true;
	    }
	    if(sol.getIntClase()==0){
	    	bValidar = false;
	    	setMessageError("Debe seleccionar el campo Clase.");
	    }else{
	    	bValidar = true;
	    }
	    if(getDtFechPrueba()==null){
	    	bValidar = false;
	    	setMessageError("Debe seleccionar el campo Fecha Prueba.");
	    }else{
	    	bValidar = true;
	    }
	    if(getDtFechEntrega()==null){
	    	bValidar = false;
	    	setMessageError("Debe seleccionar el campo Fecha Entrega.");
	    }else{
	    	bValidar = true;
	    }
	    if(sol.getIntIdDesarrollador()==null){
	    	bValidar = false;
	    	setMessageError("Debe seleccionar el campo Desarrollador.");
	    }else{
	    	if(sol.getIntIdDesarrollador()==0){
		    	bValidar = false;
		    	setMessageError("Debe seleccionar el campo Desarrollador.");
		    }else{
		    	bValidar = true;
		    }	    		    	
	    }
	    if(sol.getIntIdSolicitante()==null){
	    	bValidar = false;
	    	setMessageError("Debe seleccionar el campo Solicitante.");
	    }else{
	    	if(sol.getIntIdSolicitante()==0){
		    	bValidar = false;
		    	setMessageError("Debe seleccionar el campo Solicitante.");
		    }else{
		    	bValidar = true;
		    }	 
	    }
	    if(sol.getIntIdCIO()==null){
	    	bValidar = false;
	    	setMessageError("Debe seleccionar el campo CIO.");
	    }else{
	    	if(sol.getIntIdCIO()==0){
		    	bValidar = false;
		    	setMessageError("Debe seleccionar el campo CIO.");
		    }else{
		    	bValidar = true;
		    }
	    }
	    	    
	    if(bValidar == true){
	    	sol.setIntIdSolicitud(null);
	    	sol.setStrFechEntrega(getDtFechPrueba().toString());
		    sol.setStrFechPrueba(getDtFechEntrega().toString());
	    	beanListSolAgregadas.add(sol);
	    	grabarSolicitud(event,sol);
	    	listarSolicitudesAgregadas(event);	    	
	    	setBlnAgregadas(true);
	    }
	    //if(beanListSolAgregadas.size()==0){
	    //	listarSolicitudesAgregadas(event);
	    //}
	    
	    //listarSolicitudesAgregadas(event);
	}
	
	public void buscarSolicitudes(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging adminMenuController.buscarSolicitudes-----------------------------");
		setService(adminMenuService);
		
		ArrayList listaSolicitudes = new ArrayList();
		listaSolicitudes = listarSolicitudes();
		setBeanListSolicitudes(listaSolicitudes);
	}
	
	public void listarSolicitudesAgregadas(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging adminMenuController.listarSolicitudesAgregada-----------------------------");
		setService(adminMenuService);
		
		ArrayList listaSolAgregadas = new ArrayList();
		listaSolAgregadas = listarSolicitudes();
		setBeanListSolAgregadas(listaSolAgregadas);
	}
	
	public ArrayList listarSolicitudes() throws DaoException{
		setService(adminMenuService);
		
		//Filtros de búsqueda
		Integer intIdEmpresa = (getIntCboEmpresaSoli()!=0)?getIntCboEmpresaSoli():null;
		Integer intIdDesarrollador = (getIntCboDesarrolladorSoli()==0||getIntCboDesarrolladorSoli()==null)?null:getIntCboDesarrolladorSoli();
		Integer intIdSolicitante = (getIntCboSolicitanteSoli()!=0)?getIntCboSolicitanteSoli():null;
		Integer intTipoCambio = (getIntTipoCambioSoli()!=0)?getIntTipoCambioSoli():null;
		Integer intEstado = (getIntEstadoSoli()!=0)?getIntEstadoSoli():null;
		Integer intClase = (getIntClaseSoli()!=0)?getIntClaseSoli():null;
		Integer intRangoFech = getIntRangoFechSoli();
		int intAnexos = (getBlnAnexosSoli()==true)?1:0;
		String strFechRango1 = (getDtFechRango1Soli()!=null)?sdf.format(getDtFechRango1Soli()):null;
		String strFechRango2 = (getDtFechRango2Soli()!=null)?sdf.format(getDtFechRango2Soli()):null;
		
		HashMap prmtSolicitudes = new HashMap();
		prmtSolicitudes.put("pIntIdSolicitud", null);
		prmtSolicitudes.put("pIntIdEmpresa", intIdEmpresa);
		prmtSolicitudes.put("pIntDesarrollador", intIdDesarrollador);
		prmtSolicitudes.put("pIntSolicitante", intIdSolicitante);
		prmtSolicitudes.put("pIntTipoCambio", intTipoCambio);
		prmtSolicitudes.put("pIntEstado", intEstado);
		prmtSolicitudes.put("pIntClase", intClase);
		prmtSolicitudes.put("pIntAnexos", intAnexos);
		prmtSolicitudes.put("pIntRangoFech", intRangoFech);
		prmtSolicitudes.put("pStrFechRango1", strFechRango1);
		prmtSolicitudes.put("pStrFechRango2", strFechRango2);
		
		ArrayList listaSolicitudes = new ArrayList();
		ArrayList arraySolicitudes = new ArrayList();
		arraySolicitudes = getService().listarSolicitudes(prmtSolicitudes);
		
		for(int i=0; i<arraySolicitudes.size();i++){
			SolicitudCambio sol = new SolicitudCambio();
			sol = (SolicitudCambio) arraySolicitudes.get(i);
			String ruta = sol.getStrRuta();
			
			for(int j=0; j<ruta.length(); j++){
				char c = ruta.charAt(j);
				if(!(""+c).equals("/") && j!=0){
					ruta=ruta.substring(j,ruta.length());
					break;
				}
			}
			sol.setStrRuta(ruta);
			listaSolicitudes.add(sol);
		}
		return listaSolicitudes;
		
	}
	
	public void habilitarGrabarSolicitud(ActionEvent event){
		setBlnSolicitudRendered(true);
		limpiarFormSolicitud();
		SolicitudCambio sol = new SolicitudCambio();
		setBeanSolicitud(sol);
	}
	
	public void limpiarFormSolicitud(){
		SolicitudCambio sol = new SolicitudCambio();
		setBeanSolicitud(sol);
		limpiarTablaPopup();
		beanListSolAgregadas.clear();
	}
	
	public void cancelarGrabarSolicitud(ActionEvent event){
		limpiarFormSolicitud();
		setBlnSolicitudRendered(false);
	}
	
	public void modificarSolicitudCambio(ActionEvent event) throws DaoException, ParseException{
		setService(adminMenuService);
		String strIdSolicitud = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmSolicitudPanel:hiddenIdSolicitud");
		setBlnSolicitudRendered(true);
		
		//El metodo devuelve una sola fila
		HashMap prmtSolicitudes = new HashMap();
		prmtSolicitudes.put("pIntIdSolicitud", Integer.parseInt(strIdSolicitud));
		
		ArrayList arraySolicitudes = new ArrayList();
		arraySolicitudes = getService().listarSolicitudes(prmtSolicitudes);

		SolicitudCambio sol = new SolicitudCambio();
		sol = (SolicitudCambio)arraySolicitudes.get(0);
		Date fechSolicitud = sdf.parse(sol.getStrFechSolicitud());		
		Date fechPrueba = sdf.parse(sol.getStrFechPrueba());
		Date fechEntrega = sdf.parse(sol.getStrFechEntrega());
		setDtFechSolicitud(fechSolicitud);
		setDtFechPrueba(fechPrueba);
		setDtFechEntrega(fechEntrega);
		
		setBeanSolicitud(sol);
		limpiarTablaPopup();
		
		arrayDataObjects.clear();
		HashMap prmtTablas = new HashMap();
		HashMap prmtTablas1 = new HashMap();

		prmtTablas.put("pStrIdTransaccion", null);
		prmtTablas.put("pIntIdEmpresa", 0);
		ArrayList listaObjetos = new ArrayList();
		listaObjetos = getService().listarDataObjects(prmtTablas);
		
		String strIdTransaccion = sol.getStrIdTransaccion();
		codTransaccion = sol.getStrIdTransaccion();
		int intIdEmpresa = sol.getIntIdEmpresa();
		prmtTablas.put("pStrIdTransaccion", strIdTransaccion);
		prmtTablas.put("pIntIdEmpresa", intIdEmpresa);
		ArrayList listaSeleccionados = getService().listarDataObjects(prmtTablas);
		
		for(int i=0; i<listaSeleccionados.size(); i++){
			DataObjects dob = new DataObjects();
			dob = (DataObjects)listaSeleccionados.get(i);
			int intCod = dob.getIntCodigo();
			for(int j=0; j<listaObjetos.size();j++){
				DataObjects obj = new DataObjects();
				obj = (DataObjects)listaObjetos.get(j);
				int intCodigo = obj.getIntCodigo();
				if(intCod == intCodigo){
					obj.setBlnSelectedObjecto(true);
				}
			}		
		}
		
		ArrayList listTablas   = new ArrayList();
		ArrayList listVistas   = new ArrayList();
		ArrayList listTriggers = new ArrayList();
		ArrayList listAplicaciones = new ArrayList();
		String    strTablas    = "";
		String    strVistas    = "";
		String    strTriggers  = "";
		String    strAplicaciones  = "";
		
		for(int i=0; i<listaObjetos.size(); i++){
			DataObjects tb = new DataObjects();
			tb = (DataObjects)listaObjetos.get(i);
			
			if(tb.getIntTipoObjecto()==null){
				log.info("Dato inconsistente ["+tb.getIntCodigo()+"]");
			}else if(tb.getIntTipoObjecto()==1){
				listTablas.add(tb);
				if(tb.getBlnSelectedObjecto()==true){
					strTablas = strTablas + ", " + tb.getStrNombreObjeto();
				}				
			}else if(tb.getIntTipoObjecto()==2){
				listVistas.add(tb);
				if(tb.getBlnSelectedObjecto()==true){
					strVistas = strVistas + ", " + tb.getStrNombreObjeto();
				}							
			}else if(tb.getIntTipoObjecto()==3){
				listTriggers.add(tb);
				if(tb.getBlnSelectedObjecto()==true){
					strTriggers = strTriggers + ", " + tb.getStrNombreObjeto();
				}								
			}else if(tb.getIntTipoObjecto()==4){
				listAplicaciones.add(tb);
				if(tb.getBlnSelectedObjecto()==true){
					strAplicaciones = strAplicaciones + ", " + tb.getStrNombreObjeto();
				}								
			}
		}
		
		if(strTablas.length()>2)strTablas=strTablas.substring(2);
		if(strTablas.length()>2)strTablas=strTablas.substring(2);
		if(strTablas.length()>2)strTablas=strTablas.substring(2);
		
		setBeanListTablas(listTablas);
		setBeanListVistas(listVistas);
		setBeanListTriggers(listTriggers);
		setBeanListAplicaciones(listAplicaciones);
		
		setStrTablasMenu(strTablas);
		setStrVistasMenu(strVistas);
		setStrTriggersMenu(strTriggers);
		setStrAplicacionesMenu(strAplicaciones);
		
		ValueChangeEvent event1 = null;
		reloadCboUsuarioMenu(event1);
		
	}
	
	public void eliminarSolicitudCambio(ActionEvent event) throws DaoException{
		setService(adminMenuService);
		String strIdSolicitud = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmSolicitudPanel:hiddenIdSolicitud");
		Integer intIdSolicitud = (strIdSolicitud!="")?Integer.parseInt(strIdSolicitud):null;
		
		HashMap prmtSolicitud = new HashMap();
		prmtSolicitud.put("pIntIdSolicitud", intIdSolicitud);
		getService().eliminarSolCambio(prmtSolicitud);
		arrayDataObjects.clear();
		buscarSolicitudes(event);
	}
	
	public void guardarSolicitudes(ActionEvent event) throws DaoException{
		//Registro de solicitud
	    SolicitudCambio sol = new SolicitudCambio();
	    sol = (SolicitudCambio) getBeanSolicitud();
	    grabarSolicitud(event, sol);
	    
	    if(blnAgregadas!=null && blnAgregadas==true){
	    	for(int i=0; i<beanListSolAgregadas.size(); i++){
	    		SolicitudCambio solCambio = new SolicitudCambio();
	    		grabarSolicitud(event, solCambio);
	    	}
	    	listarSolicitudesAgregadas(event);
	    }else{
	    	setBlnAgregadas(false);
	    }
	    
	    buscarSolicitudes(event);
	    setMessageSuccess("Registro de Solicitud exitoso.");
	}
	
	public void grabarSolicitud(ActionEvent event, SolicitudCambio solicitud) throws DaoException{
	    setService(adminMenuService);
	    
	    //Obteniendo el Id Transaccion
	    String idTransaccion = "";
	    
	    if(getStrCboSolMenu4()!=null && !getStrCboSolMenu4().equals("0")){
	    	idTransaccion = getStrCboSolMenu4();
	    }else if(getStrCboSolMenu3()!=null && !getStrCboSolMenu3().equals("0")){
	    	idTransaccion = getStrCboSolMenu3();
	    }else if(getStrCboSolMenu2()!=null && !getStrCboSolMenu2().equals("0")){
	    	idTransaccion = getStrCboSolMenu2();
	    }else if(getStrCboSolMenu1()!=null && !getStrCboSolMenu1().equals("0")){
	    	idTransaccion = getStrCboSolMenu1();
	    }
	    
	    //Obteniendo las fechas
	    if(!idTransaccion.equals("")){
	    	solicitud.setStrIdTransaccion(idTransaccion);
	    }
	    
	    solicitud.setStrFechSolicitud(sdf.format(getDtFechSolicitud()));
	    solicitud.setStrFechPrueba(sdf.format(getDtFechPrueba()));
	    solicitud.setStrFechEntrega(sdf.format(getDtFechEntrega()));
	    
	    //solicitud.setStrFechSolicitud(getDtFechSolicitud().toString());
	    //solicitud.setStrFechPrueba(getDtFechPrueba().toString());
	    //solicitud.setStrFechEntrega(getDtFechEntrega().toString());
	      
	    getService().grabarSolicitud(solicitud);
		
		int conta = 0;
		for(int i=0; i<beanListTablas.size(); i++){
			DataObjects obj = (DataObjects)beanListTablas.get(i);
			if(obj.getBlnSelectedObjecto()==true){
				obj.setIntIdEmpresa(solicitud.getIntIdEmpresa());
				obj.setStrIdTransaccion(solicitud.getStrIdTransaccion());
				obj.setIntIdSolicitud(solicitud.getIntIdSolicitudOut());
				obj.setIntConta(conta);
				conta=conta+1;
				getService().grabarSocaDetalle(obj);
			}
		}
		for(int i=0; i<beanListVistas.size(); i++){
			DataObjects obj = (DataObjects)beanListVistas.get(i);
			if(obj.getBlnSelectedObjecto()==true){
				obj.setIntIdEmpresa(solicitud.getIntIdEmpresa());
				obj.setStrIdTransaccion(solicitud.getStrIdTransaccion());
				obj.setIntIdSolicitud(solicitud.getIntIdSolicitudOut());
				obj.setIntConta(conta++);
				conta=conta+1;
				getService().grabarDataObjects(obj);
			}
		}
		for(int i=0; i<beanListTriggers.size(); i++){
			DataObjects obj = (DataObjects)beanListTriggers.get(i);
			if(obj.getBlnSelectedObjecto()==true){
				obj.setIntIdEmpresa(solicitud.getIntIdEmpresa());
				obj.setStrIdTransaccion(solicitud.getStrIdTransaccion());
				obj.setIntIdSolicitud(solicitud.getIntIdSolicitudOut());
				obj.setIntConta(conta++);
				conta=conta+1;
				getService().grabarDataObjects(obj);
			}
		}
		
		for(int i=0; i<beanListAplicaciones.size(); i++){
			DataObjects obj = (DataObjects)beanListAplicaciones.get(i);
			if(obj.getBlnSelectedObjecto()==true){
				obj.setIntIdEmpresa(solicitud.getIntIdEmpresa());
				obj.setStrIdTransaccion(solicitud.getStrIdTransaccion());
				obj.setIntIdSolicitud(solicitud.getIntIdSolicitudOut());
				obj.setIntConta(conta++);
				conta=conta+1;
				getService().grabarDataObjects(obj);
			}
		}
		
		//buscarSolicitudes(event);
		if(arrayDataObjects!=null){
			arrayDataObjects.clear();
		}
	}
	
	public void reloadCboUsuarioMenu(ValueChangeEvent event) throws DaoException{
		reloadCboUsuario(event);
		reloadCboMenuEmpresa(event);
		reloadCboMenuEmpresa1(event);
		reloadCboMenuEmpresa2(event);
		reloadCboMenuEmpresa3(event);
	}
	
	public void reloadCboUsuario(ValueChangeEvent event) throws DaoException {
		setService(adminMenuService);
		int idCboEmpresa = 0;
		if(event!=null){
			idCboEmpresa = Integer.parseInt(""+event.getNewValue());
		}			
		ArrayList<SelectItem> cboUsuariosEmp	 = new ArrayList<SelectItem>();
		HashMap prmtBusqUsuario = new HashMap();
		prmtBusqUsuario.put("pIntIdEmpresa", idCboEmpresa);
	    setHiddenIdEmpresa(""+idCboEmpresa);
	    
		ArrayList arrayUsuarios = new ArrayList();
	    arrayUsuarios = getService().listarUsuariosEmpresa1(prmtBusqUsuario);
	    
	    for(int i=0; i<arrayUsuarios.size() ; i++){
        	HashMap hash = (HashMap) arrayUsuarios.get(i);
			int idPersona = Integer.parseInt("" + hash.get("PERS_IDPERSONA_N"));
			String strNomUsuario = "" + hash.get("USUA_USUARIO_V");
			cboUsuariosEmp.add(new SelectItem(idPersona,strNomUsuario));
        }
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        cboUsuariosEmp.add(0, lblSelect);
	    
        String cboId = "";
        if(event!=null){
        	UIComponent uiComponent = event.getComponent();
    		cboId=uiComponent.getId();
        }
		
		if(cboId.equals("cboEmpSoliBusq")){
			this.cboUsuarioSoli.clear();
			setCboUsuarioSoli(cboUsuariosEmp);
		}else if(cboId.equals("cboEmpresasSoli")){
			this.cboUsuario.clear();
			setCboUsuario(cboUsuariosEmp);
		}else{
			this.cboUsuario.clear();
			setCboUsuario(cboUsuariosEmp);
		}
	}
	
	public void reloadCboMenuEmpresa(ValueChangeEvent event) throws DaoException {
		setService(adminMenuService);
		Integer	idEmpresa = 0;
		if(event!=null){
			UIComponent uiComponent = event.getComponent();
			String cboId=uiComponent.getId();
			idEmpresa = (Integer)event.getNewValue();
		}
				
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", null);
		prmtBusq.put("pIntIdEmpresa", idEmpresa);
		prmtBusq.put("pIntIdPerfil", null);
		prmtBusq.put("pStrNivel", "1");
		
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarMenuPerfil(prmtBusq);
	    
	    ArrayList<SelectItem> arrayCbo = new ArrayList<SelectItem>();
	    if(codTransaccion==null){
	    	codTransaccion = "";
	    }
	    for(int i=0; i<arrayMenu.size() ; i++){
	    	HashMap hash = (HashMap) arrayMenu.get(i);
			AdminMenu menu = new AdminMenu();
			String strIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			if(codTransaccion.equals(strIdTransaccion)){
				strCboSolMenu1 = codTransaccion; 
			}
			menu.setStrIdTransaccion(strIdTransaccion);
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			menu.setStrNombre(strNombre);
			arrayCbo.add(new SelectItem(menu.getStrIdTransaccion(),menu.getStrNombre()));
        }
        SelectItem lblSelect = new SelectItem("0","Seleccionar..");
        arrayCbo.add(0, lblSelect);
        
    	this.cboMenu1.clear();
    	this.cboMenu2.clear();
    	this.cboMenu3.clear();
    	this.cboMenu4.clear();
    	this.cboMenu1=arrayCbo;
	}
	
	public void reloadCboMenuEmpresa1(ValueChangeEvent event) throws DaoException {
		setService(adminMenuService);
		Integer	idEmpresa = 0;
		if(event!=null){
			UIComponent uiComponent = event.getComponent();
			log.info("uiComponent = "+uiComponent.getId());
			String cboId=uiComponent.getId();
			log.info("cboId: "+cboId);
			idEmpresa = (Integer)event.getNewValue();
		}
				
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", null);
		prmtBusq.put("pIntIdEmpresa", idEmpresa);
		prmtBusq.put("pIntIdPerfil", null);
		prmtBusq.put("pStrNivel", "2");
		
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarMenuPerfil(prmtBusq);
	    log.info("arrayMenu: "+arrayMenu.size());
	    
	    ArrayList<SelectItem> arrayCbo = new ArrayList<SelectItem>();
	    if(codTransaccion==null){
	    	codTransaccion = "";
	    }
	    for(int i=0; i<arrayMenu.size() ; i++){
	    	HashMap hash = (HashMap) arrayMenu.get(i);
			AdminMenu menu = new AdminMenu();
			log.info("TRAN_IDTRANSACCION_C = "+hash.get("TRAN_IDTRANSACCION_C"));
			String strIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			if(codTransaccion.equals(strIdTransaccion)){
				strCboSolMenu2 = codTransaccion; 
			}
			menu.setStrIdTransaccion(strIdTransaccion);
			log.info("TRAN_NOMBRE_V = "+hash.get("TRAN_NOMBRE_V"));
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			menu.setStrNombre(strNombre);
			arrayCbo.add(new SelectItem(menu.getStrIdTransaccion(),menu.getStrNombre()));
        }
        SelectItem lblSelect = new SelectItem("0","Seleccionar..");
        arrayCbo.add(0, lblSelect);
        log.info("arrayCbo.size(): "+arrayCbo.size());
        
    	this.cboMenu2=arrayCbo;
		log.info("Saliendo de ControllerFiller.reloadCboMenuPerfil()...");
	}
	
	public void reloadCboMenuEmpresa2(ValueChangeEvent event) throws DaoException {
		log.info("--------------------Debugging ControlsFiller.reloadCboMenuEmpresa()--------------------------");
		setService(adminMenuService);
		Integer	idEmpresa = 0;
		if(event!=null){
			UIComponent uiComponent = event.getComponent();
			log.info("uiComponent = "+uiComponent.getId());
			String cboId=uiComponent.getId();
			log.info("cboId: "+cboId);
			idEmpresa = (Integer)event.getNewValue();
		}
				
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", null);
		prmtBusq.put("pIntIdEmpresa", idEmpresa);
		prmtBusq.put("pIntIdPerfil", null);
		prmtBusq.put("pStrNivel", "3");
		
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarMenuPerfil(prmtBusq);
	    log.info("arrayMenu: "+arrayMenu.size());
	    
	    ArrayList<SelectItem> arrayCbo = new ArrayList<SelectItem>();
	    if(codTransaccion==null){
	    	codTransaccion = "";
	    }
	    for(int i=0; i<arrayMenu.size() ; i++){
	    	HashMap hash = (HashMap) arrayMenu.get(i);
			AdminMenu menu = new AdminMenu();
			log.info("TRAN_IDTRANSACCION_C = "+hash.get("TRAN_IDTRANSACCION_C"));
			String strIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			if(codTransaccion.equals(strIdTransaccion)){
				strCboSolMenu3 = codTransaccion; 
			}
			menu.setStrIdTransaccion(strIdTransaccion);
			log.info("TRAN_NOMBRE_V = "+hash.get("TRAN_NOMBRE_V"));
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			menu.setStrNombre(strNombre);
			arrayCbo.add(new SelectItem(menu.getStrIdTransaccion(),menu.getStrNombre()));
        }
        SelectItem lblSelect = new SelectItem("0","Seleccionar..");
        arrayCbo.add(0, lblSelect);
        log.info("arrayCbo.size(): "+arrayCbo.size());
        
    	this.cboMenu3=arrayCbo;
		log.info("Saliendo de ControllerFiller.reloadCboMenuPerfil()...");
	}
	
	public void reloadCboMenuEmpresa3(ValueChangeEvent event) throws DaoException {
		log.info("--------------------Debugging ControlsFiller.reloadCboMenuEmpresa()--------------------------");
		setService(adminMenuService);
		Integer	idEmpresa = 0;
		if(event!=null){
			UIComponent uiComponent = event.getComponent();
			log.info("uiComponent = "+uiComponent.getId());
			String cboId=uiComponent.getId();
			log.info("cboId: "+cboId);
			idEmpresa = (Integer)event.getNewValue();
		}
				
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", null);
		prmtBusq.put("pIntIdEmpresa", idEmpresa);
		prmtBusq.put("pIntIdPerfil", null);
		prmtBusq.put("pStrNivel", "4");
		
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarMenuPerfil(prmtBusq);
	    log.info("arrayMenu: "+arrayMenu.size());
	    
	    ArrayList<SelectItem> arrayCbo = new ArrayList<SelectItem>();
	    if(codTransaccion==null){
	    	codTransaccion = "";
	    }
	    for(int i=0; i<arrayMenu.size() ; i++){
	    	HashMap hash = (HashMap) arrayMenu.get(i);
			AdminMenu menu = new AdminMenu();
			log.info("TRAN_IDTRANSACCION_C = "+hash.get("TRAN_IDTRANSACCION_C"));
			String strIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			if(codTransaccion.equals(strIdTransaccion)){
				strCboSolMenu4 = codTransaccion; 
			}
			menu.setStrIdTransaccion(strIdTransaccion);
			log.info("TRAN_NOMBRE_V = "+hash.get("TRAN_NOMBRE_V"));
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			menu.setStrNombre(strNombre);
			arrayCbo.add(new SelectItem(menu.getStrIdTransaccion(),menu.getStrNombre()));
        }
        SelectItem lblSelect = new SelectItem("0","Seleccionar..");
        arrayCbo.add(0, lblSelect);
        log.info("arrayCbo.size(): "+arrayCbo.size());
        
    	this.cboMenu4=arrayCbo;
		log.info("Saliendo de ControllerFiller.reloadCboMenuPerfil()...");
	}
	
	
	public ArrayList getListaCboMenu(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboMenu()-----------------------------");
		UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String cboId=uiComponent.getId();
		setService(adminMenuService);
		String idCboMenu = (String)event.getNewValue();
		
		log.info("idCboMenu() = "+idCboMenu);
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", idCboMenu);
	    
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarAdminMenu(prmtBusq);
	    
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
        arrayCbo.add(0,new SelectItem("0","Seleccionar.."));
		log.info("Saliendo de ControllerFiller.reloadCboMenu()...");
		return arrayCbo;
	}
	
	//------------------------------------------------------------------------------------
	// Getters y Setters
	//------------------------------------------------------------------------------------
	
	public String getMsgTxtPadre() {
		return msgTxtPadre;
	}

	public void setMsgTxtPadre(String msgTxtPadre) {
		this.msgTxtPadre = msgTxtPadre;
	}

	public AdminMenuServiceImpl getAdminMenuService() {
		return adminMenuService;
	}

	public void setAdminMenuService(AdminMenuServiceImpl adminMenuService) {
		this.adminMenuService = adminMenuService;
	}
	
	public AdminMenu getBeanMenu() {
		return beanMenu;
	}

	public void setBeanMenu(AdminMenu beanMenu) {
		this.beanMenu = beanMenu;
	}

	public String getStrCboEmpresaMenu() {
		return strCboEmpresaMenu;
	}

	public void setStrCboEmpresaMenu(String strCboEmpresaMenu) {
		this.strCboEmpresaMenu = strCboEmpresaMenu;
	}


	public String getStrTablasMenu() {
		return strTablasMenu;
	}

	public void setStrTablasMenu(String strTablasMenu) {
		this.strTablasMenu = strTablasMenu;
	}

	public List getBeanListDataObjects() {
		return beanListDataObjects;
	}

	public void setBeanListDataObjects(List beanListDataObjects) {
		this.beanListDataObjects = beanListDataObjects;
	}

	public String getStrAdjuntarObjeto() {
		return strAdjuntarObjeto;
	}

	public void setStrAdjuntarObjeto(String strAdjuntarObjeto) {
		this.strAdjuntarObjeto = strAdjuntarObjeto;
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

	public ArrayList getArrayDataObjects() {
		return arrayDataObjects;
	}

	public void setArrayDataObjects(ArrayList arrayDataObjects) {
		this.arrayDataObjects = arrayDataObjects;
	}

	public ArrayList getBeanListTablas() {
		return beanListTablas;
	}

	public void setBeanListTablas(ArrayList beanListTablas) {
		this.beanListTablas = beanListTablas;
	}

	public ArrayList getBeanListVistas() {
		return beanListVistas;
	}

	public void setBeanListVistas(ArrayList beanListVistas) {
		this.beanListVistas = beanListVistas;
	}

	public ArrayList getBeanListTriggers() {
		return beanListTriggers;
	}

	public void setBeanListTriggers(ArrayList beanListTriggers) {
		this.beanListTriggers = beanListTriggers;
	}

	public Boolean getBlnAdminMenuRendered() {
		return blnAdminMenuRendered;
	}

	public void setBlnAdminMenuRendered(Boolean blnAdminMenuRendered) {
		this.blnAdminMenuRendered = blnAdminMenuRendered;
	}

	public List getBeanListSolicitudes() {
		return beanListSolicitudes;
	}

	public void setBeanListSolicitudes(List beanListSolicitudes) {
		this.beanListSolicitudes = beanListSolicitudes;
	}

	public String getStrCboSolMenu1() {
		return strCboSolMenu1;
	}

	public void setStrCboSolMenu1(String strCboSolMenu1) {
		this.strCboSolMenu1 = strCboSolMenu1;
	}

	public String getStrCboSolMenu2() {
		return strCboSolMenu2;
	}

	public void setStrCboSolMenu2(String strCboSolMenu2) {
		this.strCboSolMenu2 = strCboSolMenu2;
	}

	public String getStrCboSolMenu3() {
		return strCboSolMenu3;
	}

	public void setStrCboSolMenu3(String strCboSolMenu3) {
		this.strCboSolMenu3 = strCboSolMenu3;
	}

	public String getStrCboSolMenu4() {
		return strCboSolMenu4;
	}

	public void setStrCboSolMenu4(String strCboSolMenu4) {
		this.strCboSolMenu4 = strCboSolMenu4;
	}
	
	public String getStrCboDesarrollador() {
		return strCboDesarrollador;
	}

	public void setStrCboDesarrollador(String strCboDesarrollador) {
		this.strCboDesarrollador = strCboDesarrollador;
	}

	public String getStrCboSolicitante() {
		return strCboSolicitante;
	}

	public void setStrCboSolicitante(String strCboSolicitante) {
		this.strCboSolicitante = strCboSolicitante;
	}

	public String getStrCboCIO() {
		return strCboCIO;
	}

	public void setStrCboCIO(String strCboCIO) {
		this.strCboCIO = strCboCIO;
	}

	public SolicitudCambio getBeanSolicitud() {
		return beanSolicitud;
	}

	public void setBeanSolicitud(SolicitudCambio beanSolicitud) {
		this.beanSolicitud = beanSolicitud;
	}

	public Boolean getBlnSolicitudRendered() {
		return blnSolicitudRendered;
	}

	public void setBlnSolicitudRendered(Boolean blnSolicitudRendered) {
		this.blnSolicitudRendered = blnSolicitudRendered;
	}

	public Integer getIntCboEmpresaSoli() {
		return intCboEmpresaSoli;
	}

	public void setIntCboEmpresaSoli(Integer intCboEmpresaSoli) {
		this.intCboEmpresaSoli = intCboEmpresaSoli;
	}

	public Date getDtFechSolicitud() {
		return dtFechSolicitud;
	}

	public void setDtFechSolicitud(Date dtFechSolicitud) {
		this.dtFechSolicitud = dtFechSolicitud;
	}

	public Date getDtFechPrueba() {
		return dtFechPrueba;
	}

	public void setDtFechPrueba(Date dtFechPrueba) {
		this.dtFechPrueba = dtFechPrueba;
	}

	public Date getDtFechEntrega() {
		return dtFechEntrega;
	}

	public void setDtFechEntrega(Date dtFechEntrega) {
		this.dtFechEntrega = dtFechEntrega;
	}

	public Integer getIntCboDesarrolladorSoli() {
		return intCboDesarrolladorSoli;
	}

	public void setIntCboDesarrolladorSoli(Integer intCboDesarrolladorSoli) {
		this.intCboDesarrolladorSoli = intCboDesarrolladorSoli;
	}

	public Integer getIntCboSolicitanteSoli() {
		return intCboSolicitanteSoli;
	}

	public void setIntCboSolicitanteSoli(Integer intCboSolicitanteSoli) {
		this.intCboSolicitanteSoli = intCboSolicitanteSoli;
	}

	public Integer getIntTipoCambioSoli() {
		return intTipoCambioSoli;
	}

	public void setIntTipoCambioSoli(Integer intTipoCambioSoli) {
		this.intTipoCambioSoli = intTipoCambioSoli;
	}

	public Integer getIntEstadoSoli() {
		return intEstadoSoli;
	}

	public void setIntEstadoSoli(Integer intEstadoSoli) {
		this.intEstadoSoli = intEstadoSoli;
	}

	public Integer getIntClaseSoli() {
		return intClaseSoli;
	}

	public void setIntClaseSoli(Integer intClaseSoli) {
		this.intClaseSoli = intClaseSoli;
	}

	public Integer getIntRangoFechSoli() {
		return intRangoFechSoli;
	}

	public void setIntRangoFechSoli(Integer intRangoFechSoli) {
		this.intRangoFechSoli = intRangoFechSoli;
	}

	public Date getDtFechRango1Soli() {
		return dtFechRango1Soli;
	}

	public void setDtFechRango1Soli(Date dtFechRango1Soli) {
		this.dtFechRango1Soli = dtFechRango1Soli;
	}

	public Date getDtFechRango2Soli() {
		return dtFechRango2Soli;
	}

	public void setDtFechRango2Soli(Date dtFechRango2Soli) {
		this.dtFechRango2Soli = dtFechRango2Soli;
	}

	public Boolean getBlnAnexosSoli() {
		return blnAnexosSoli;
	}

	public void setBlnAnexosSoli(Boolean blnAnexosSoli) {
		this.blnAnexosSoli = blnAnexosSoli;
	}

	public List getBeanListSolAgregadas() {
		return beanListSolAgregadas;
	}

	public void setBeanListSolAgregadas(List beanListSolAgregadas) {
		this.beanListSolAgregadas = beanListSolAgregadas;
	}

	public static Boolean getBlnAgregadas() {
		return blnAgregadas;
	}

	public static void setBlnAgregadas(Boolean blnAgregadas) {
		AdminMenuController.blnAgregadas = blnAgregadas;
	}
	
	public ArrayList getBeanListAplicaciones() {
		return beanListAplicaciones;
	}

	public void setBeanListAplicaciones(ArrayList beanListAplicaciones) {
		this.beanListAplicaciones = beanListAplicaciones;
	}
	
	public String getStrAplicacionesMenu() {
		return strAplicacionesMenu;
	}

	public void setStrAplicacionesMenu(String strAplicacionesMenu) {
		this.strAplicacionesMenu = strAplicacionesMenu;
	}
	
	public ArrayList getArraySolicitudes() {
		return ArraySolicitudes;
	}

	public void setArraySolicitudes(ArrayList arraySolicitudes) {
		ArraySolicitudes = arraySolicitudes;
	}
	
	public ArrayList<SelectItem> getCboUsuarioSoli() {
		return cboUsuarioSoli;
	}

	public void setCboUsuarioSoli(ArrayList<SelectItem> cboUsuarioSoli) {
		this.cboUsuarioSoli = cboUsuarioSoli;
	}
	
	public String getHiddenIdEmpresa() {
		return hiddenIdEmpresa;
	}

	public void setHiddenIdEmpresa(String hiddenIdEmpresa) {
		this.hiddenIdEmpresa = hiddenIdEmpresa;
	}
	
	public ArrayList<SelectItem> getCboMenu1() {
		return cboMenu1;
	}

	public void setCboMenu1(ArrayList<SelectItem> cboMenu1) {
		this.cboMenu1 = cboMenu1;
	}

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
	
	public ArrayList<SelectItem> getCboUsuario() {
		return cboUsuario;
	}

	public void setCboUsuario(ArrayList<SelectItem> cboUsuario) {
		this.cboUsuario = cboUsuario;
	}
	
	public String getCodTransaccion() {
		return codTransaccion;
	}

	public void setCodTransaccion(String codTransaccion) {
		this.codTransaccion = codTransaccion;
	}

	public List<Juridica> getListaJuridicaEmpresa() throws ParseException, BusinessException{
		return this.listaJuridicaEmpresa;
	}

	public Boolean getEsPopPupValido() {
		return esPopPupValido;
	}
	public void setEsPopPupValido(Boolean esPopPupValido) {
		this.esPopPupValido = esPopPupValido;
	}
	
	public Boolean getEsPopPupAccesible() {
		return esPopPupAccesible;
	}
	public void setEsPopPupAccesible(Boolean esPopPupAccesible) {
		this.esPopPupAccesible = esPopPupAccesible;
	}
	public void setListaJuridicaEmpresa(List<Juridica> listaJuridicaEmpresa) {
		this.listaJuridicaEmpresa = listaJuridicaEmpresa;
	}

	public String getStrTipoMantMenu() {
		return strTipoMantMenu;
	}
	public void setStrTipoMantMenu(String strTipoMantMenu) {
		this.strTipoMantMenu = strTipoMantMenu;
	}

	public Transaccion getMenu() {
		return menu;
	}
	public void setMenu(Transaccion menu) {
		this.menu = menu;
	}

	public List<List<Transaccion>> getListaMenu() {
		return listaMenu;
	}
	public void setListaMenu(List<List<Transaccion>> listaMenu) {
		this.listaMenu = listaMenu;
	}

	public Transaccion getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(Transaccion subMenu) {
		this.subMenu = subMenu;
	}

	public List<String> getListaSelectRadioMenu() {
		return listaSelectRadioMenu;
	}

	public void setListaSelectRadioMenu(List<String> listaSelectRadioMenu) {
		this.listaSelectRadioMenu = listaSelectRadioMenu;
	}
	public List<Integer> getListaSubMenuActivo() {
		return listaSubMenuActivo;
	}
	public void setListaSubMenuActivo(List<Integer> listaSubMenuActivo) {
		this.listaSubMenuActivo = listaSubMenuActivo;
	}
	public MenuMsg getMsgMenu() {
		return msgMenu;
	}
	public void setMsgMenu(MenuMsg msgMenu) {
		this.msgMenu = msgMenu;
	}

	public MenuComp getFiltroMenu() {
		return filtroMenu;
	}

	public void setFiltroMenu(MenuComp filtroMenu) {
		this.filtroMenu = filtroMenu;
	}

	public List<MenuComp> getListaMenuComp() {
		return listaMenuComp;
	}

	public void setListaMenuComp(List<MenuComp> listaMenuComp) {
		this.listaMenuComp = listaMenuComp;
	}
	public Integer getIndiceMenuBusqueda() {
		return indiceMenuBusqueda;
	}
	public void setIndiceMenuBusqueda(Integer indiceMenuBusqueda) {
		this.indiceMenuBusqueda = indiceMenuBusqueda;
	}
	public List<Tabla> getListaTablaOrden() {
		return listaTablaOrden;
	}
	public void setListaTablaOrden(List<Tabla> listaTablaOrden) {
		this.listaTablaOrden = listaTablaOrden;
	}
	public Boolean getBolSolicitudCambio() {
		return bolSolicitudCambio;
	}
	public void setBolSolicitudCambio(Boolean bolSolicitudCambio) {
		this.bolSolicitudCambio = bolSolicitudCambio;
	}
	public Boolean getBolAdministracionMenu() {
		return bolAdministracionMenu;
	}
	public void setBolAdministracionMenu(Boolean bolAdministracionMenu) {
		this.bolAdministracionMenu = bolAdministracionMenu;
	}
	
}
