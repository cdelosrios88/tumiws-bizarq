package pe.com.tumi.seguridad.controller;

import java.util.List;

import javax.faces.event.ActionEvent;

import pe.com.tumi.administracion.controller.GenericMaintenanceController;
import pe.com.tumi.administracion.domain.Parametro;
import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.common.util.ItemHelper;
import pe.com.tumi.common.util.ParametroHelper;
import pe.com.tumi.common.util.SistemaHelper;
import pe.com.tumi.seguridad.domain.Sistema;

public class ParametroController extends GenericMaintenanceController {
	
	private GenericService parametroService;
	private GenericService sistemaService;
	private List listaSistema;
	private String sistemaElegido;
	private int rows;
	
	public boolean validateCodigo(){
		if(!super.validateCodigo()){
			Parametro parametro = (Parametro)getBean();
			Parametro parametroTemporal = new Parametro();
			parametroTemporal.setCodigo(parametro.getCodigo().toUpperCase());
			parametroTemporal.setIdSis(new Long(getSistemaElegido()));
			try {
				List listaTemporal = (List) parametroService.findByObject(parametroTemporal);
				if (listaTemporal!=null && listaTemporal.size()>0){
					setMessageError("El codigo existe para el sistema seleccionado");
					return false;
				}
			} catch (DaoException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				setCatchError(e);
			}
		}
		return true;
	}
	
	public boolean validate(){
		boolean success = true;
		
		if(!super.validate()){
			success = false;
		}
		
		Parametro parametro = (Parametro)getBean();
		
		if(parametro.getCodigo()==null || parametro.getCodigo().trim().equals("")){
			setMessageError("Se debe ingresar el código.");
			success = false;
		}
		
		if(parametro.getValor()==null || parametro.getValor().trim().equals("")){
			setMessageError("Se debe ingresar el valor del parámetro.");
			success = false;
		}
		
		if(parametro.getEstado()==null || parametro.getEstado().trim().equals(Constante.OPTION_SELECT_VALUE)){
			setMessageError("Debe seleccionar un estado.");
			success = false;
		}
		if (parametro.getIdSis().intValue()<1 || parametro.getIdSis()== null) {
			setMessageError("El parametro debe pertenecer a un sistema.");
			success = false;
		}
		return success;
	}

	public void init(){
		SistemaHelper sistemaHelper = (SistemaHelper)getSpringBean("sistemaHelper");
		try {
			listaSistema = sistemaHelper.getListaSistema();
			setBean(new Parametro());
			setService(parametroService);
			ParametroHelper parametroHelper = (ParametroHelper)getSpringBean("parametroHelper");
			setRows(Integer.parseInt(parametroHelper.getParametro(Constante.NUM_FILAS_PAG, Constante.ID_SISTEMA)));		
			setBeanList(parametroService.findByQueryWithDesc(" 1 = 1 "));
		} catch (DaoException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setCatchError(e);
		}
	}

	public void load(ActionEvent event){
		setService(parametroService);
		super.load(event);
		try{
			Parametro param = (Parametro)getBean();
			if(param.getIdSis()!= null){
				Sistema tmp = (Sistema)getSistemaService().findById(param.getIdSis());
				Long sistema = tmp.getId();
				sistemaElegido = String.valueOf(sistema);
			}
			setBean(param);
		}catch(DaoException exception){
			exception.printStackTrace();
			log.debug(exception.getMessage());
			setCatchError(exception);
			setMessageError("Ocurrió un error en la carga de datos del Parametro.");
		}
	}
	
	public void setParametroService(GenericService parametroService) {
		this.parametroService = parametroService;
	}

	public void beforeSave(ActionEvent event){
		Parametro param = (Parametro) getBean();
		param.setIdSis(new Long(getSistemaElegido()));
		super.beforeSave(event);

	}
	
	public void refreshBeanList(){
		try {
			setBeanList(parametroService.findByQueryWithDesc(" 1 = 1 "));
		} catch (DaoException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setCatchError(e);
		}
	}
	
	public void clean (ActionEvent event){
		setSistemaElegido(String.valueOf(Constante.NO_SELECTED));
		super.clean(event);
	}
	
	public void afterSave(ActionEvent event)throws DaoException{
		super.afterSave(event);
		clean(event);
		setSistemaElegido(null);
	}
	
	public void afterDelete(ActionEvent event)throws DaoException{
		super.afterDelete(event);
		setSistemaElegido(null);
	}
	
	public GenericService getParametroService() {
		return parametroService;
	}

	public List getListaSistema() {
		return listaSistema;
	}

	public void setListaSistema(List listaSistema) {
		this.listaSistema = listaSistema;
	}

	public String getSistemaElegido() {
		return sistemaElegido;
	}

	public void setSistemaElegido(String sistemaElegido) {
		this.sistemaElegido = sistemaElegido;
	}

	public void setSistemaService(GenericService sistemaService) {
		this.sistemaService = sistemaService;
	}

	public GenericService getSistemaService() {
		return sistemaService;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return rows;
	}

}