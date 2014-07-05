package pe.com.tumi.administracion.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.common.util.ExpressionsValidator;

public class GenericMaintenanceController extends GenericController {
	
	private String mainAttribute;

	public boolean validate(){
		if(!super.validate()){
			return false;
		}else if (!validateCodigo()) return false;
		return true;
	}
	
	public boolean validateCodigo(){
		String idTmp = null;
		String codigo = null;
		Long id = null;
		if(mainAttribute==null || mainAttribute.equals("")){
			setMainAttribute("codigo");
		}
		try{
			codigo = BeanUtils.getProperty(getBean(), mainAttribute);
			codigo = codigo.trim();
			if(codigo.equals("")){
				return false;
			}
			if(!ExpressionsValidator.validarSoloAlfanumerico(codigo)){
				setMessageError("El " + mainAttribute +" sólo puede tener caracteres alfanuméricos, y los símbolos punto(.), guión (-) y guión bajo (_).");
				return false;
			}
			idTmp = BeanUtils.getProperty(getBean(), Constante.PERSISTENCE_FIELD_ID);
			if(idTmp!=null && !idTmp.equals("")){
				id = new Long(idTmp);
			}
		}catch(Exception e){
			log.info("Ocurrió un error al validar id y " + mainAttribute + " de " + getBean().getClass().getName());
			e.printStackTrace();
			return false;
		}
		
		Object obj = getBeanInstance();
		Object obj2 = getBeanInstance();
		Object obj3 = getBeanInstance();
		try {
			BeanUtils.setProperty(obj, mainAttribute, codigo.toLowerCase());
			BeanUtils.setProperty(obj2, mainAttribute, codigo.toUpperCase());
			BeanUtils.setProperty(obj3, mainAttribute, codigo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if(id == null){
			List lstTmp;
			try {
				lstTmp = (List) getService().findByObject(obj);
				List lstTmp2 = (List) getService().findByObject(obj2);
				List lstTmp3 = (List) getService().findByObject(obj3);
				if(!lstTmp.isEmpty() || !lstTmp2.isEmpty() || !lstTmp3.isEmpty()){
					setMessageError("El " + mainAttribute + " ya existe.");
					return false;
				}
			} catch (DaoException e) {
				setMessageError("No se realizo conexion con BD");
				e.printStackTrace();
			}
		}else{
			obj = getBeanInstance();
			try {
				obj = getService().findById(id);
				String codigoTmp = BeanUtils.getProperty(obj, mainAttribute);
				if(!codigoTmp.equals(codigo)){
					obj = getBeanInstance();
					BeanUtils.setProperty(obj, mainAttribute, codigo.toLowerCase());
					obj2 = getBeanInstance();
					BeanUtils.setProperty(obj2, mainAttribute, codigo.toUpperCase());
					obj3 = getBeanInstance();
					BeanUtils.setProperty(obj3, mainAttribute, codigo);
					List lstTmp = (List) getService().findByObject(obj);
					List lstTmp2 = (List) getService().findByObject(obj2);
					List lstTmp3 = (List) getService().findByObject(obj3);
					if(!lstTmp.isEmpty() || !lstTmp2.isEmpty() || !lstTmp3.isEmpty()){
						setMessageError("El " + mainAttribute +" ya existe.");
						return false;
					}	
				}
			} catch (DaoException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		try {
			BeanUtils.setProperty(getBean(), mainAttribute, codigo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public String getMainAttribute() {
		return mainAttribute;
	}

	public void setMainAttribute(String mainAttribute) {
		this.mainAttribute = mainAttribute;
	}

}
