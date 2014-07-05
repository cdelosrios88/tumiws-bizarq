package pe.com.tumi.seguridad.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import pe.com.tumi.administracion.controller.GenericMaintenanceController;
import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.common.util.ItemHelper;
import pe.com.tumi.common.util.ParametroHelper;
import pe.com.tumi.common.util.SistemaHelper;
import pe.com.tumi.common.util.StringHelper;
import pe.com.tumi.seguridad.domain.OpcionMenu;
import pe.com.tumi.seguridad.domain.Rol;
import pe.com.tumi.seguridad.domain.Sistema;
import pe.com.tumi.seguridad.domain.Usuario;
import pe.com.tumi.seguridad.service.RolService;
import pe.com.tumi.seguridad.service.TipoArbolService;

public class RolController extends GenericMaintenanceController {
	
	private GenericService sistemaService;
	private RolService rolService;
	private TipoArbolService tipoArbolService;
	private List menuAgregar;
	private List menuEliminar;
	private List listaOpcion;
	private List listaOpcionRol;
	private List listaOpcionDisponible;
	private List listaSistema;
	private String sistemaElegido;
	private String sid;
	private int rows;
	
	public void init(){
		SistemaHelper sistemaHelper = (SistemaHelper)getSpringBean("sistemaHelper");
		try {
			listaSistema = sistemaHelper.getListaSistema();
			setBean(new Rol());
			setService(rolService);
			ParametroHelper parametroHelper = (ParametroHelper)getSpringBean("parametroHelper");
			setRows(Integer.parseInt(parametroHelper.getParametro(Constante.NUM_FILAS_PAG, Constante.ID_SISTEMA)));		
			setBeanList(rolService.findByQueryWithDesc(" 1 = 1 "));
		} catch (DaoException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setCatchError(e);
		}
	}
	
	public void refreshBeanList(){
		try {
			setBeanList(rolService.findByQueryWithDesc(" 1 = 1 "));
		} catch (DaoException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setCatchError(e);
		}
	}

	public void agregar(ActionEvent event) {
		if(menuAgregar==null || menuAgregar.isEmpty() || menuAgregar.size()==0) {
			setMessageError("Seleccione menús de la lista de Opciones de Menús Disponibles");
		}else{
			try{
				OpcionMenu opcn = new OpcionMenu();
				for (int j=0; j<menuAgregar.size(); j++){
					Long id = new Long((String)menuAgregar.get(j));
					opcn = (OpcionMenu)getTipoArbolService().findById(id);
					if (opcn!=null){
						this.listaOpcionRol.add(new SelectItem(String.valueOf(opcn.getId()), (String)opcn.getNombre().toUpperCase()));
						for (int i=0; i<listaOpcionDisponible.size(); i++){
							SelectItem item = (SelectItem)listaOpcionDisponible.get(i);
							if(new Long((item.getValue()).toString()).intValue() == opcn.getId().intValue()) {
								listaOpcionDisponible.remove(item);
								break;
							}
						}
					}
				}
			}catch (DaoException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				setCatchError(e);
			}
		}
	}
	
	public void eliminar(ActionEvent event) {
		if(menuEliminar==null || menuEliminar.isEmpty() || menuEliminar.size()==0) {
			setMessageError("Seleccione roles de la lista Roles Seleccionados");
		}else{
			try{
				OpcionMenu opcn = new OpcionMenu();
				for (int j=0; j<menuEliminar.size(); j++){
					opcn = (OpcionMenu)getTipoArbolService().findById(new Long((String)menuEliminar.get(j)));
					if (opcn!=null){
						this.listaOpcionDisponible.add(new SelectItem(String.valueOf(opcn.getId()), (String)opcn.getNombre().toUpperCase()));
						for (int i=0; i<listaOpcionRol.size(); i++){
							SelectItem item = (SelectItem)listaOpcionRol.get(i);
							if(new Long((item.getValue()).toString()).intValue() == opcn.getId().intValue()){
								listaOpcionRol.remove(item);
								break;
							}
						}
					}
				}
			}catch (DaoException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				setCatchError(e);
			}
		}
	}

	public void afterSave(ActionEvent event)throws DaoException{
		super.afterSave(event);
		clean(event);
	}
	
	public void afterDelete(ActionEvent event)throws DaoException{
		super.afterDelete(event);
		clean(event);
	}
	
	public RolService getRolService() {
		return rolService;
	}
	
	public void setRolService(RolService rolService) {
		this.rolService = rolService;
	}
	
	public void setListaSistema(List listaSistema) {
		this.listaSistema = listaSistema;
	}
	
	public List getListaSistema() {
		return listaSistema;
	}

	public void setSistemaElegido(String sistemaElegido) {
		this.sistemaElegido = sistemaElegido;
	}

	public String getSistemaElegido() {
		return sistemaElegido;
	}

	public void setSistemaService(GenericService sistemaService) {
		this.sistemaService = sistemaService;
	}

	public GenericService getSistemaService() {
		return sistemaService;
	}

	public TipoArbolService getTipoArbolService() {
		return tipoArbolService;
	}

	public void setTipoArbolService(TipoArbolService tipoArbolService) {
		this.tipoArbolService = tipoArbolService;
	}

	public List getListaOpcion() {
		return listaOpcion;
	}

	public void setListaOpcion(List listaOpcion) {
		this.listaOpcion = listaOpcion;
	}

	public List getListaOpcionRol() {
		return listaOpcionRol;
	}

	public void setListaOpcionRol(List listaOpcionRol) {
		this.listaOpcionRol = listaOpcionRol;
	}

	public List getListaOpcionDisponible() {
		return listaOpcionDisponible;
	}

	public void setListaOpcionDisponible(List listaOpcionDisponible) {
		this.listaOpcionDisponible = listaOpcionDisponible;
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
	public List getMenuAgregar() {
		return menuAgregar;
	}

	public void setMenuAgregar(List menuAgregar) {
		this.menuAgregar = menuAgregar;
	}

	public List getMenuEliminar() {
		return menuEliminar;
	}

	public void setMenuEliminar(List menuEliminar) {
		this.menuEliminar = menuEliminar;
	}


}