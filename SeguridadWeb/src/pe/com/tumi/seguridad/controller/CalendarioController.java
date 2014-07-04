/*package pe.com.telefonica.seguridad.controller;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import pe.com.telefonica.administracion.controller.GenericMaintenanceController;
import pe.com.telefonica.common.controller.GenericController;
import pe.com.telefonica.common.domain.Calendario;
import pe.com.telefonica.common.util.Constante;
import pe.com.telefonica.common.util.ItemHelper;
import pe.com.telefonica.common.util.SistemaHelper;
import pe.com.telefonica.common.util.StringHelper;
import pe.com.telefonica.seguridad.domain.Rol;
import pe.com.telefonica.seguridad.domain.Sistema;

public class CalendarioController extends GenericController{

	private Long sistemaElegido;
	private List<SelectItem> listaSistema;
	
	@Override
	public void init() throws Exception{
		setBeanList(getService().findByObject(new Calendario()));
		DiaSemanaController ds = new DiaSemanaController();
		ds.init();
		SistemaHelper sistemaHelper = (SistemaHelper)getSpringBean("sistemaHelper");
		listaSistema = sistemaHelper.getListaSistema();
	}
	
	@Override
	public void beforeSave(ActionEvent event) throws Exception{
		Calendario cal = (Calendario) getBean();
		cal.setIdSis(getSistemaElegido());
		Sistema sis = (Sistema) getService().findById(Sistema.class, cal.getIdSis());
		setBean(cal);
	}
	
	@Override
	public boolean validate() throws Exception{
		boolean success = true;
		
		if(!super.validate()){
			success = false;
		}
		
		Calendario cal = (Calendario)getBean();
		
		if(cal.getNombre()==null || cal.getNombre().trim().equals("")){
			setMessageError(new Exception("Se debe ingresar el nombre."));
			success = false;
		}
		
		if (cal.getEstado().equals("O")) {
			setMessageError(new Exception("Debe seleccionar un estado."));
			success = false;
		}

		if (cal.getIdSis()<1 || cal.getIdSis()==null) {
			setMessageError(new Exception("El calendario debe pertenecer a un sistema."));
			success = false;
		}
		
		return success;
	}
	
	public void setSistemaElegido(Long sistemaElegido) {
		this.sistemaElegido = sistemaElegido;
	}

	public Long getSistemaElegido() {
		return sistemaElegido;
	}

	public void setListaSistema(List<SelectItem> listaSistema) {
		this.listaSistema = listaSistema;
	}

	public List<SelectItem> getListaSistema() {
		return listaSistema;
	}
	
}
*/