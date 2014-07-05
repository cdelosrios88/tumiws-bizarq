package pe.com.tumi.seguridad.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import pe.com.tumi.administracion.controller.GenericMaintenanceController;
import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.common.util.DateHelper;
import pe.com.tumi.common.util.Encriptador;
import pe.com.tumi.common.util.ExpressionsValidator;
import pe.com.tumi.common.util.ItemHelper;
import pe.com.tumi.common.util.ParametroHelper;
import pe.com.tumi.common.util.StringHelper;
import pe.com.tumi.seguridad.domain.Rol;
import pe.com.tumi.seguridad.domain.Sistema;
import pe.com.tumi.seguridad.domain.Usuario;
import pe.com.tumi.seguridad.service.RolService;

public class UsuarioController extends GenericMaintenanceController {

	protected static Logger log = Logger.getLogger(UsuarioController.class);
	private GenericService usuarioService;
	private RolService rolService;
	private String verifyPassword;
	private String newPassword;
	private List listaRol;
	private List listaRolUsuario;
	private List listaRolDisponible;
	private List rolAgregar;
	private List rolEliminar;
	private String sid;
	private int rows;
	/****Modificado Jessica Tadeo 24/06/2011***/
	private String rolElegido;
	/****Fin Modificado Jessica Tadeo 24/06/2011***/
	
	public void init(){
		setBean(new Usuario());
		setListaRolUsuario(new ArrayList<Rol>());
		setService(usuarioService);
		ParametroHelper parametroHelper = (ParametroHelper)getSpringBean("parametroHelper");
		try {
			setRows(Integer.parseInt(parametroHelper.getParametro(Constante.NUM_FILAS_PAG, Constante.ID_SISTEMA)));
			/****Modificado Jessica Tadeo 24/06/2011***/
			//Obteniendo la lista de roles
			listaRol = getRolService().findAll();
			if(listaRol!=null && !listaRol.isEmpty()){
				listaRol = ItemHelper.listToListOfSelectItems(listaRol, "id", "nombre");
				SelectItem opcionSeleccionar = new SelectItem(Constante.NO_SELECTED.toString(),Constante.OPTION_SELECT);
				listaRol.add(opcionSeleccionar);
				listaRol = ItemHelper.sortSelectItemListByName(listaRol);
			}
			/****Fin Modificado Jessica Tadeo 24/06/2011***/
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setMessageError("Error al ejecutar la operación");
		} catch (DaoException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setCatchError(e);
		}		
		super.init();
	}	

	public boolean validate(){
		boolean success = true;
		Usuario usuario = (Usuario)getBean();
		
		if (usuario.getCodigo() != null && !usuario.getCodigo().trim().equals("")) {
			if (!super.validate()) {
				success = false;
			}else{			
				if (usuario.getCodigo().trim().length()<4){
					setMessageError("Código de Usuario: Mínimo 4 caracteres.");
					success = false;				
				}
			}
		}else{
			setMessageError("Ingrese el  código de usuario.");
			success = false;
		}

		if(usuario.getNombre()==null || usuario.getNombre().trim().equals("")){	
			setMessageError("Ingrese el nombre.");	
			success = false;	 
		}
		if(usuario.getApePa()==null || usuario.getApePa().trim().equals("")){	
			setMessageError("Ingrese el Apellido Paterno.");	
			success = false;	
		}
		if(usuario.getApeMa()==null || usuario.getApeMa().trim().equals("")){	
			setMessageError("Ingrese el Apellido Materno.");	
			success = false;	
		}
		if(usuario.getCorreoElectronico()!=null && !usuario.getCorreoElectronico().equals("")){
			if( !ExpressionsValidator.validarEmail( usuario.getCorreoElectronico() ) ){
				setMessageError("Ingrese un formato válido para el correo electrónico");	
				success = false;
			}	
		}
		/****Modificado Jessica Tadeo 24/06/2011***/
		if(rolElegido.equals(Constante.NO_SELECTED.toString())){
			setMessageError("Debe seleccionar un rol.");
			success = false;
		}
		/****Fin Modificado Jessica Tadeo 24/06/2011***/
		if(usuario.getEstado().trim().equals(Constante.OPTION_SELECT_VALUE)){
			setMessageError("Debe seleccionar un estado.");
			success = false;
		}
		return success;
	}
	
	public void clean(ActionEvent event){
		setListaRolUsuario(new ArrayList<Rol>());
		setBeanBusqueda(new Usuario());
		setBean(new Usuario()); 
		/****Modificado Jessica Tadeo 24/06/2011***/
		setRolElegido("");
		/****Fin Modificado Jessica Tadeo 24/06/2011***/
		super.clean(event);
	}	

/**
 * 	@author TaTi
 * 	Genera una contraseña aleatoria de 15 carateres
 * 	alfanumericos y establece el estado del usuario a
 * 	nuevo "N" 
 
*/
	/*public void reiniciarContrasenha(ActionEvent event){
		Encriptador objCripto = new Encriptador();
		Usuario usuarioEnSesion = ((Usuario)getSpringBean(Constante.SESSION_USER));
		Usuario beanT;
		try {
			beanT = (Usuario)getUsuarioService().findById(Long.valueOf(getRequestParameter("sid")));
			ParametroHelper parametroHelper = (ParametroHelper)getSpringBean("parametroHelper");
			int maximoPassAlmacenados = Integer.valueOf(parametroHelper.getParametro(Constante.MAXIMOS_PASSWORDS_ALMACENADOS, Constante.ID_SISTEMA)).intValue();
	
			if (beanT != null) {
					setBean(beanT);
					StringHelper pg = new StringHelper();
					String clave = pg.getPassword(15);
					String enc_clave = objCripto.encryptBlowfish(clave, Constante.KEY );
					String miPassword = enc_clave + ","+ beanT.getContrasenha();
					beanT.setContrasenha(StringHelper.listaPassword(miPassword, maximoPassAlmacenados));
					beanT.setEstado(Constante.ESTADO_NUEVO);
					beanT.setFechaCambioPass(DateHelper.getFechaActual());
					save(event);
					setBean(new Usuario());
					log.info("El usuario: " + usuarioEnSesion.getId() + " ha reiniciado la contraseña para el usuario '" + beanT.getCodigo() + "'.");
					setMessageSuccess("La nueva contraseña para " + beanT.getNombre() + " " + beanT.getApePa() + " es: " + clave);
				} else {
					setMessageError("No se encontró el usuario en el sistema.");
					setBean(new Usuario());
				}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setMessageError("Error al ejecutar la operacion");
		} catch (DaoException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setCatchError(e);
		}  
	}*/
	
	public void reiniciarContrasenha(ActionEvent event){
		Encriptador objCripto = new Encriptador();
		Usuario usuarioEnSesion = ((Usuario)getSpringBean(Constante.SESSION_USER));
		Usuario beanT;
		try {
			//beanT = (Usuario)getUsuarioService().findById(Long.valueOf(getRequestParameter("sid")));
			load(event);
			ParametroHelper parametroHelper = (ParametroHelper)getSpringBean("parametroHelper");
			int maximoPassAlmacenados = Integer.valueOf(parametroHelper.getParametro(Constante.MAXIMOS_PASSWORDS_ALMACENADOS, Constante.ID_SISTEMA)).intValue();
			
			beanT=((Usuario)getBean());
			StringHelper pg = new StringHelper();
			String clave = pg.getPassword(15);
			String enc_clave = objCripto.encryptBlowfish(clave, Constante.KEY );
			String miPassword = enc_clave + ","+ beanT.getContrasenha();
			beanT.setContrasenha(StringHelper.listaPassword(miPassword, maximoPassAlmacenados));
			beanT.setEstado(Constante.ESTADO_NUEVO);
			beanT.setFechaCambioPass(DateHelper.getFechaActual());
			save(event);
			//setBean(new Usuario());
			clean(event);
			log.info("El usuario: " + usuarioEnSesion.getId() + " ha reiniciado la contraseña para el usuario '" + beanT.getCodigo() + "'.");
			setMessageSuccess("La nueva contraseña para " + beanT.getNombre() + " " + beanT.getApePa() + " es: " + clave);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setMessageError("Error al ejecutar la operacion");
		} catch (DaoException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setCatchError(e);
		}  
	}

	public void save(ActionEvent event){
		Encriptador objCripto = new Encriptador();
		Usuario usuario = (Usuario)getBean();
		beforeSave(event);
		try{
			/****Modificado Jessica Tadeo 24/06/2011***/
			if (usuario.getId()==null){
				String clave = StringHelper.getPassword(15);
				String enc_clave = objCripto.encryptBlowfish(clave, Constante.KEY );
				usuario.setContrasenha(enc_clave);
				usuario.setCodigo(usuario.getCodigo().toUpperCase());
				usuario.setEstado(Constante.ESTADO_NUEVO);
				if(validate()){
					doSave(event);
					setMessageSuccess("La contraseña para "+ usuario.getNombre()+" "+usuario.getApePa()+" es: " + clave);
					//guardando el rol del usuario
					updateRolUsuario();
					log.info("Se registro un nuevo usuario: " + usuario.getCodigo());
					setBean(new Usuario());
					setListaRolUsuario(new ArrayList<Rol>());
					setRolElegido(""); 
				}
				
			}else{
				if (validate()){
					//actualizando el rol del usuario
					updateRolUsuario();	
					log.info("Se actualizo el usuario: " + usuario.getCodigo());
					doSave(event);
					setMessageSuccess("Se actualizaron los datos del usuario "+ usuario.getNombre()+" "+usuario.getApePa());
					setBean(new Usuario());
					setListaRolUsuario(new ArrayList<Rol>());
					setRolElegido("");
				}				
			}			
			afterSave(event);					
			/****Fin Modificado Jessica Tadeo 24/06/2011***/
		}catch(DaoException e){
			e.printStackTrace();
			log.debug(e.getMessage());
			setCatchError(e);
		}
	}
	
	public void update(ActionEvent event){
		super.beforeUpdate(event);
		Usuario usuarioEnSesion = (Usuario)getSpringBean(Constante.SESSION_USER);
		List roles = null;
		if(!getListaRol().isEmpty()){	
			roles = new ArrayList();
			Iterator rolesSeleccionadosIterator = getListaRolUsuario().iterator();
			while(rolesSeleccionadosIterator.hasNext()){
				SelectItem item = (SelectItem) rolesSeleccionadosIterator.next();
				try {
					Long id =  new Long((String)item.getValue());
					roles.add((Rol)getRolService().findById(id));
				} catch (DaoException e) {
					log.error(e.getMessage());
					setCatchError(e);
				}
			}
			try{
				getRolService().deleteRelationalTables(((Usuario)getBean()).getId(), listaRol);
				((Usuario)getBean()).setRoles(roles);
				getRolService().saveRelationalTables(((Usuario)getBean()).getId(), roles);
				log.info(roles.size()  + " roles asignados al usuario '" + ((Usuario)getBean()).getCodigo() + "'. Accion iniciada por: '" + usuarioEnSesion.getCodigo() + "'.");
				setMessageSuccess("Se asignaron " + roles.size() + " rol(es) al usuario: " + ((Usuario)getBean()).getCodigo() + ".");
				afterUpdate(event);
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
				setCatchError(e);
				setMessageError("No se pudo guardar los roles para el usuario");
			}
		}
	}
	
	/****Modificado Jessica Tadeo 24/06/2011***/
	public void updateRolUsuario(){
		List roles = null;
		if(!getListaRol().isEmpty()){	
			try{
				listaRolUsuario.clear();
				Long lRolElegido=new Long(0);
				if(rolElegido!=null && !rolElegido.equals("")){//save o update
					lRolElegido=new Long(rolElegido.trim());
				}
				roles = new ArrayList();
				Rol rol;
					
				Iterator rolesSeleccionadosIterator = listaRol.iterator();
				while(rolesSeleccionadosIterator.hasNext()){
					SelectItem item = (SelectItem) rolesSeleccionadosIterator.next();
					try {
						Long id =  new Long((String)item.getValue());
						rol=(Rol)getRolService().findById(id);
						if(rol!=null) roles.add(rol);
						if(lRolElegido!=0 && id.equals(lRolElegido)){
							listaRolUsuario.add(rol);
						}										
					} catch (DaoException e) {
						log.error(e.getMessage());
						setCatchError(e);
					}
				}
				
				getRolService().deleteRelationalTables(((Usuario)getBean()).getId(), roles);
													
				if(listaRolUsuario.size()>0){ //save o update
					getRolService().saveRelationalTables(((Usuario)getBean()).getId(), listaRolUsuario);
					log.info(getListaRolUsuario().size()  + " roles asignados al usuario '" + ((Usuario)getBean()).getCodigo() + "'.");
					//setMessageSuccess("Se asignaron " + getListaRolUsuario().size() + " rol(es) al usuario: " + ((Usuario)getBean()).getCodigo() + ".");
				}else{ //delete
					log.info("Se eliminaron los roles del usuario '" + ((Usuario)getBean()).getCodigo() + "'.");
					log.info("Eliminacion de usuario"+ ((Usuario)getBean()).getCodigo() + "'.");
				}
				
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
				setCatchError(e);
				setMessageError("No se pudo guardar los roles para el usuario");
			}
		}		
	}
	
	public void delete(ActionEvent e){
		setListaRolUsuario(new ArrayList<Rol>());
		setRolElegido("");
		setBean(new Usuario());
		((Usuario)getBean()).setId(Long.valueOf(getRequestParameter("sid")));		
		updateRolUsuario();
		super.delete(e);
	}
	/****Fin Modificado Jessica Tadeo 24/06/2011***/
	
	public void afterUpdate(ActionEvent event)throws DaoException{
		super.afterUpdate(event);
		Object bean =  super.doLoad(getSid());
		setBean(bean);
	}
	
	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setUsuarioService(GenericService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public GenericService getUsuarioService() {
		return usuarioService;
	}

	public void setRolService(RolService rolService) {
		this.rolService = rolService;
	}

	public RolService getRolService() {
		return rolService;
	}

	public List getListaRol() {
		return listaRol;
	}

	public void setListaRol(List listaRol) {
		this.listaRol = listaRol;
	}

	public List getRolAgregar() {
		return rolAgregar;
	}

	public void setRolAgregar(List rolAgregar) {
		this.rolAgregar = rolAgregar;
	}

	public void setListaRolUsuario(List listaRolUsuario) {
		this.listaRolUsuario = listaRolUsuario;
	}

	public List getListaRolUsuario() {
		return listaRolUsuario;
	}

	public void setListaRolDisponible(List listaRolDisponible) {
		this.listaRolDisponible = listaRolDisponible;
	}

	public List getListaRolDisponible() {
		return listaRolDisponible;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSid() {
		return sid;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return rows;
	}

	public void setRolEliminar(List rolEliminar) {
		this.rolEliminar = rolEliminar;
	}

	public List getRolEliminar() {
		return rolEliminar;
	}
	/****Modificado Jessica Tadeo 24/06/2011***/
	public String getRolElegido() {
		return rolElegido;
	}

	public void setRolElegido(String rolElegido) {
		this.rolElegido = rolElegido;
	}
	/****Fin Modificado Jessica Tadeo 24/06/2011***/
	

}