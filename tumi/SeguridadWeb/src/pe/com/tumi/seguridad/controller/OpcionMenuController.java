package pe.com.tumi.seguridad.controller;

import javax.faces.event.ActionEvent;

import pe.com.tumi.administracion.controller.GenericMaintenanceController;
import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.common.util.ExpressionsValidator;
import pe.com.tumi.common.util.ItemHelper;
import pe.com.tumi.common.util.ParametroHelper;
import pe.com.tumi.seguridad.domain.OpcionMenu;

public class OpcionMenuController extends GenericMaintenanceController {
	
	private GenericService tipoArbolService;
	private int rows;
	
	public void init(){
		setBean(new OpcionMenu());
		setService(tipoArbolService);
		try {
			super.init();
			ParametroHelper parametroHelper = (ParametroHelper)getSpringBean("parametroHelper");
			setRows(Integer.parseInt(parametroHelper.getParametro(Constante.NUM_FILAS_PAG, Constante.ID_SISTEMA)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setMessageError("Error al ejecutar la operación");
		} catch (DaoException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			setCatchError(e);
		}
	}
	
	public void beforeSave(ActionEvent event){
		if(((OpcionMenu)getBean()).getSuperior()!= null){
			if(ExpressionsValidator.validarSoloNumeros(String.valueOf(((OpcionMenu)getBean()).getSuperior()))){
				((OpcionMenu)getBean()).setTipoNodo(Constante.TIPO_MENU_NODO);
			}
		}else if (((OpcionMenu)getBean()).getSuperior()==null){
			((OpcionMenu)getBean()).setTipoNodo(Constante.TIPO_MENU_PADRE);
		}((OpcionMenu)getBean()).setInitMethod(Constante.INIT_METHOD);
	}
	
	public boolean validate(){
		boolean success = true;
		try{
			OpcionMenu opcionMenu = (OpcionMenu)getBean();
			
			if (opcionMenu.getCodigo() != null && !opcionMenu.getCodigo().trim().equals("")) {
				if (!super.validate()) {
					success = false;
				}else{			
					if (opcionMenu.getCodigo().trim().length()<4){
						setMessageError("Código: Mínimo 4 caracteres.");
						success = false;				
					}
				}
			}else{
				setMessageError("Ingrese el  código.");
				success = false;
			}
			
			if(opcionMenu.getNombre()==null || opcionMenu.getNombre().trim().equals("")){
				setMessageError("Ingrese el nombre.");
				success = false;
			}
			if(opcionMenu.getEstado()==null || opcionMenu.getEstado().trim().equals(Constante.OPTION_SELECT_VALUE)){
				setMessageError("Debe seleccionar un estado.");
				success = false;
			}
			if(((OpcionMenu)getBean()).getSuperior()!= null){
				OpcionMenu tmp = (OpcionMenu)getService().findById(((OpcionMenu)getBean()).getSuperior());
				if(tmp==null){
					setMessageError("El Id. Superior no existe. Ingrese un código válido.");
					success = false;
				}
			}
			return success;
		}catch(NumberFormatException e){
			setMessageError("El Id. Superior debe ser un número válido y relacionado con un registro existente.");
			return false;
		}catch(Exception e){
			log.error(ItemHelper.getStackTrace(e));
			setMessageError("Ocurrió un error al momento de validar los datos.");
			return false;
		}
	}
	
	public void afterSave(ActionEvent event) throws DaoException{
		super.afterSave(event);
		clean(event);
	}
	
	public void setTipoArbolService(GenericService tipoArbolService) {
		this.tipoArbolService = tipoArbolService;
	}

	public GenericService getTipoArbolService() {
		return tipoArbolService;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return rows;
	}
}
