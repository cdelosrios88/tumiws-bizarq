package pe.com.tumi.cobranza.cobro.controller;


import org.apache.log4j.Logger;

import pe.com.tumi.common.PermisoUtil;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class CobroController  extends GenericController {
	
	protected static Logger log = Logger.getLogger(CobroController.class);
	
	private Usuario usuario;
	private Integer PERSONA_USUARIO;
	private Integer EMPRESA_USUARIO;
	private boolean poseePermiso;
	
	public CobroController()
	{
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_COBRO);
		if(usuario!= null && poseePermiso)
		{
			cargarValoresIniciales();
		}
		else
		{
			log.error("usuario obtenido es null o no posee permiso");
		}
	}
	private void cargarUsuario()
	{
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
	}
	public void cargarValoresIniciales()
	{
		try
		{
			
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public String getLimpiarCobro()
	{
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_COBRO);
		if(usuario != null && poseePermiso)
		{
			cargarValoresIniciales();
		}
		else
		{
			log.error("Usuario obtenido es NULL O no posee permiso");
		}
		return "";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Integer getPERSONA_USUARIO() {
		return PERSONA_USUARIO;
	}
	public void setPERSONA_USUARIO(Integer pERSONA_USUARIO) {
		PERSONA_USUARIO = pERSONA_USUARIO;
	}
	public Integer getEMPRESA_USUARIO() {
		return EMPRESA_USUARIO;
	}
	public void setEMPRESA_USUARIO(Integer eMPRESA_USUARIO) {
		EMPRESA_USUARIO = eMPRESA_USUARIO;
	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	
}
