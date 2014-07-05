package pe.com.tumi.seguridad.controller;

import javax.faces.event.ActionEvent;

import pe.com.tumi.administracion.controller.GenericMaintenanceController;
import pe.com.tumi.administracion.domain.Parametro;
import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.common.util.ParametroHelper;
import pe.com.tumi.seguridad.domain.Sistema;

public class SistemaController extends GenericMaintenanceController{

	private GenericService sistemaService;
	private int rows;

	public void init(){
		setBean(new Sistema());
		setService(sistemaService);
		ParametroHelper parametroHelper = (ParametroHelper)getSpringBean("parametroHelper");
		try {
			setRows(Integer.parseInt(parametroHelper.getParametro(Constante.NUM_FILAS_PAG, Constante.ID_SISTEMA)));
			super.init();
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
			setMessageError("Error al ejecutar la operación");
		} catch (DaoException e) {
			setCatchError(e);
			log.error(e.getMessage());
		}		
	}	

	public boolean validate(){
		boolean success = true;
		Sistema sistema = (Sistema)getBean();

		if (sistema.getCodigo() != null && !sistema.getCodigo().trim().equals("")) {
			if (!super.validate()) {
				success = false;
			}else{			
				if (sistema.getCodigo().trim().length()<3){
					setMessageError("Código: Mínimo 3 caracteres.");
					success = false;				
				}
			}
		}else{
			setMessageError("Se debe de ingresar un código.");
			success = false;
		}

		if(sistema.getEstado().trim().equals(Constante.OPTION_SELECT_VALUE)){
			setMessageError("Debe seleccionar un estado.");
			success = false;
		}
		return success;
	}
	
	public void afterSave(ActionEvent event)throws DaoException{
		super.afterSave(event);
		clean(event);
	}
	
	public GenericService getSistemaService() {
		return sistemaService;
	}
	public void setSistemaService(GenericService sistemaService) {
		this.sistemaService = sistemaService;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

}
