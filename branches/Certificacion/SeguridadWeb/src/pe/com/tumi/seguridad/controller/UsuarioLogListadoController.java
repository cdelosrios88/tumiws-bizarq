package pe.com.tumi.seguridad.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.common.util.ItemHelper;
import pe.com.tumi.common.util.ParametroHelper;
import pe.com.tumi.common.util.SistemaHelper;

public class UsuarioLogListadoController extends GenericController{

	private GenericService listadosService;
	private List listaUsuarioLog;
	private List listaUsuarioPerfil;
	private List listaUsuarioOpcionPerfil; 
	private List listaUsuarioInactivo;
	private String sistemaElegido;
	private List listaSistema;
	private Date fechaInicio;
	private Date fechaFin;
	private HtmlSelectOneMenu selectMenuBound;
	private int rows;

	public void init(){
		setSistemaElegido(String.valueOf(Constante.NO_SELECTED));
		setListaUsuarioPerfil(null);
		setListaUsuarioLog(null);
		setListaUsuarioOpcionPerfil(null);
		setListaUsuarioInactivo(null);
		setFechaInicio(null);
		setFechaFin(null);
		try {
			loadSys();
		} catch (DaoException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setCatchError(e);
		}
	}

	public void loadSys() throws DaoException{
		SistemaHelper sistemaHelper = (SistemaHelper)getSpringBean("sistemaHelper");
		try {
			setListaSistema(sistemaHelper.getListaSistema());
			//SelectItem opcionSeleccionar = new SelectItem(Constante.NO_OPTION_SELECTED,"Todos los Sistemas");
			//getListaSistema().add(opcionSeleccionar);
			setListaSistema(ItemHelper.sortSelectItemListByName(getListaSistema()));
		} catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	} 
	
	public void clean() {
		setListaUsuarioPerfil(null);
		setListaUsuarioLog(null);
		setListaUsuarioOpcionPerfil(null);
		setListaUsuarioInactivo(null);
		setSistemaElegido(String.valueOf(Constante.NO_SELECTED));
	}

	/**
	 * @author TaT!
	 * Metodo Carga opciones de menu
	 * por usuario segun perfil 
	 * y agrupados por sistema
	 */

	public void listarOpcionUsuarioPerfil(ActionEvent event){
		//clean();
		if (validate()){
			try{
				if (!sistemaElegido.equals(Constante.NO_OPTION_SELECTED)){
					this.listaUsuarioOpcionPerfil = getListadosService().listarOpcionesxUsuario( new Long(sistemaElegido) );
				}else{
					this.listaUsuarioOpcionPerfil = getListadosService().listarOpcionesxUsuario( null );	
				}
				if(listaUsuarioOpcionPerfil.isEmpty() || listaUsuarioOpcionPerfil==null || listaUsuarioOpcionPerfil.size()==0) setMessageError("No se encontraron registros coincidentes con la busqueda.");
			}catch(DaoException exception){
				exception.printStackTrace();
				log.error(exception.getMessage());
				setCatchError(exception);
				setMessageError("Ocurrió un error al realizar la búsqueda de las opciones de menú para el usuario.");
			}
		}
	}
	
	/**
	 * @author TaT!
	 * Metodo Carga Usuarios Inactivos
	 * en el sistema por mas de 90 días
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */

	public void listarUsuariosInactivos(ActionEvent event) {
		//clean();
		if (validate()){
			ParametroHelper parametroHelper = (ParametroHelper) getSpringBean("parametroHelper");
			try{
				Long maximoTiempoInactividad = Long.valueOf(parametroHelper.getParametro("MAXIMO_TIEMPO_INACTIVIDAD", Constante.ID_SISTEMA));
				if (!sistemaElegido.equalsIgnoreCase(Constante.NO_OPTION_SELECTED)){
					this.listaUsuarioInactivo = getListadosService().listarUsuariosInactivos( new Long(sistemaElegido), maximoTiempoInactividad );
				}else{
					this.listaUsuarioInactivo = getListadosService().listarUsuariosInactivos( null, maximoTiempoInactividad );	
				}
				if(listaUsuarioInactivo.isEmpty() || listaUsuarioInactivo==null || listaUsuarioInactivo.size()==0) setMessageError("No se encontraron registros coincidentes con la busqueda.");
			}catch(Exception exception){
				exception.printStackTrace();
				log.error(exception.getMessage());
				setCatchError(exception);
				setMessageError("Ocurrió un error al realizar la búsqueda de usuarios.");
			}
		}
	}
	
	/**
	 * @author TaT!
	 * Metodo Carga Usuarios 
	 * logueados en un rango de fecha
	 * @throws Exception 
	 */

	public void listarUsuariosLogueados(ActionEvent event) {
		//clean();
		if (validate()){
			if(getFechaInicio()==null || getFechaInicio().equals("")){
				setMessageError("Debe ingresar la fecha Inicial");
				return;
			}
			if(getFechaFin()==null || getFechaFin().equals("")){
				setMessageError("Debe ingresar la fecha Final");
				return;
			}
			if(getFechaInicio()!=null && getFechaFin()!=null){
				if(getFechaInicio().getTime()>getFechaFin().getTime()){
					setMessageError("La fecha inicial debe ser menor que la fecha final");
					return;
				}else{
					try{
						if (!sistemaElegido.equals(Constante.NO_OPTION_SELECTED)){
							this.listaUsuarioLog = getListadosService().listarUsuariosLogueados( new Long(sistemaElegido), fechaInicio, fechaFin );
						}else{
							this.listaUsuarioLog = getListadosService().listarUsuariosLogueados( null, fechaInicio, fechaFin );
						}
						if(listaUsuarioLog.isEmpty() || listaUsuarioLog==null || listaUsuarioLog.size()==0) setMessageError("No se encontraron registros coincidentes con la busqueda.");
					}catch(DaoException exception){
						exception.printStackTrace();
						log.error(exception.getMessage());
						setCatchError(exception);
						setMessageError("Ocurrió un error al realizar la búsqueda de los Usuarios logueados en el rango proporcionado.");
					}
				}
			}
		}
	}

	/**
	 * Método para validar el ingreso de los filtros
	 */

	public boolean validate(){
		if (sistemaElegido==null || sistemaElegido.equals("")||sistemaElegido.equalsIgnoreCase(Constante.NO_SELECTED.toString())){ 
				setMessageError("Debe seleccionar un sistema");
				return false;}
		return true;
	}
	
	public void setListaUsuarioLog(List listaUsuarioLog) {
		this.listaUsuarioLog = listaUsuarioLog;
	}

	public List getListaUsuarioLog() {
		return listaUsuarioLog;
	}

	public void setListaUsuarioPerfil(List listaUsuarioPerfil) {
		this.listaUsuarioPerfil = listaUsuarioPerfil;
	}

	public List getListaUsuarioPerfil() {
		return listaUsuarioPerfil;
	}

	
	public void setListaUsuarioOpcionPerfil(List listaUsuarioOpcionPerfil) {
		this.listaUsuarioOpcionPerfil = listaUsuarioOpcionPerfil;
	}

	
	public List getListaUsuarioOpcionPerfil() {
		return listaUsuarioOpcionPerfil;
	}

	
	public void setListaUsuarioInactivo(ArrayList listaUsuarioInactivo) {
		this.listaUsuarioInactivo = listaUsuarioInactivo;
	}

	
	public List getListaUsuarioInactivo() {
		return listaUsuarioInactivo;
	}

	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setSistemaElegido(String sistemaElegido) {
		this.sistemaElegido = sistemaElegido;
	}

	public String getSistemaElegido() {
		return sistemaElegido;
	}

	public void setListaSistema(List listaSistema) {
		this.listaSistema = listaSistema;
	}

	public List getListaSistema() {
		return listaSistema;
	}

	public void setListadosService(GenericService listadosService) {
		this.listadosService = listadosService;
	}

	public GenericService getListadosService() {
		return listadosService;
	}

	public void setSelectMenuBound(HtmlSelectOneMenu selectMenuBound) {
		this.selectMenuBound = selectMenuBound;
	}

	public HtmlSelectOneMenu getSelectMenuBound() {
		return selectMenuBound;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		ParametroHelper parametroHelper = (ParametroHelper)getSpringBean("parametroHelper");
		try {
			rows = (Integer.parseInt(parametroHelper.getParametro(Constante.NUM_FILAS_PAG, Constante.ID_SISTEMA)));
		} catch (NumberFormatException e) {
			log.debug(e);
		} catch (DaoException e) {
			log.debug(e);
			setCatchError(e);
		}		
		return rows;
	}

}