package pe.com.tumi.contabilidad.cierre.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.Password;
import pe.com.tumi.seguridad.permiso.domain.PasswordId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;

public class MayorizacionController {

	protected static Logger log;
	private Usuario usuario;
	private String strPassword;
	private String strMsgError;
	private List<Tabla> listaAnios;
	
	private PermisoFacadeRemote permisoFacade;
	
	public MayorizacionController(){
		log = Logger.getLogger(this.getClass());
		cargarValoresIniciales();
	}
	
	public void cargarValoresIniciales(){
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				permisoFacade = (PermisoFacadeRemote) EJBFactory.getRemote(PermisoFacadeRemote.class);
				strMsgError = null;
				cargarListaAnios();
			}else{
				log.error("--Usuario obtenido es NULL.");
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaAnios(){
		listaAnios = new ArrayList<Tabla>();
		Calendar cal=Calendar.getInstance();
		Tabla tabla = null;
		for(int i=0;i<4;i++){
			tabla = new Tabla();
			int year = cal.get(Calendar.YEAR);
			cal.add(Calendar.YEAR, 1);
			tabla.setIntIdDetalle(year);
			tabla.setStrDescripcion(""+year);
			listaAnios.add(tabla);
		}		
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public String autenticar(){
		String outcome = null;
		Password password = null;
		strMsgError = null;
		try {
			password = new Password();
			password.setId(new PasswordId());
			password.getId().setIntEmpresaPk(usuario.getEmpresa().getIntIdEmpresa());
			password.getId().setIntIdTransaccion(Constante.INT_IDTRANSACCION_MAYORIZACION);
			password.setStrContrasena(strPassword);
			password = permisoFacade.getPasswordPorPkYPass(password);
			log.info("password: " + password);
			if(password!=null){
				outcome = "mayorizacion.formulario";
			}else {
				strMsgError = "Clave incorrecta. Favor verificar";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return outcome;
	}

	public String getStrPassword() {
		return strPassword;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}

	public String getStrMsgError() {
		return strMsgError;
	}

	public void setStrMsgError(String strMsgError) {
		this.strMsgError = strMsgError;
	}
}