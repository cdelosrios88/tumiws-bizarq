package pe.com.tumi.contabilidad.cierre.controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.seguridad.login.domain.Usuario;

public class MayorizacionController {

	protected static Logger log;
	private Usuario usuario;
	
	public MayorizacionController(){
		log = Logger.getLogger(this.getClass());
		cargarValoresIniciales();
	}
	
	public void cargarValoresIniciales(){
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				
			}else{
				log.error("--Usuario obtenido es NULL.");
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	private String autenticar(){
		String outcome = null;
		
		
		return outcome;
	}
}