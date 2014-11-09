package pe.com.tumi.tesoreria.conciliacion.controller;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.seguridad.login.domain.Usuario;


public class Conciliacionvalidate {

	
	protected static Logger log;
	
	public Conciliacionvalidate(){
		log = Logger.getLogger(this.getClass());
		
	}
	
	/**
	 * Solo el Perfil Jefe Tesoreria puede Grabar Concilaicion Diaria
	 * @param usuario
	 * @return
	 */
	public boolean isValidGrabarConcil(Usuario usuario){
		boolean isValid = false;
		if(usuario.getPerfil().getId().getIntIdPerfil().compareTo(Constante.INT_PERFIL_JEFE_TESORERIA)==0){
			isValid=true;
		}
		return isValid;
	}
	
	/**
	 * Solo el Perfil Jefe de Contabilidad puede Anular Conciliacion
	 * @param usuario
	 * @return
	 */
	public boolean isValidAnulConcil(Usuario usuario){
		boolean isValid = false;

		if(usuario.getPerfil().getId().getIntIdPerfil().compareTo(Constante.INT_PERFIL_JEFE_CONTABILIDAD)==0){
			isValid=true;
		}
		return isValid;
	}
}
