package pe.com.tumi.contabilidad.impuesto.service;

import java.util.List;

import pe.com.tumi.contabilidad.impuesto.bo.ImpuestoBO;
import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class ImpuestoService {
	ImpuestoBO boImpuesto = (ImpuestoBO)TumiFactory.get(ImpuestoBO.class);

public Impuesto grabarImpuesto(Impuesto o) throws Exception{
	Impuesto domain = null;
	
	domain = boImpuesto.grabarImpuesto(o);
	
	
	return domain;
	}

//public Impuesto modificarImpuesto(Impuesto o) throws BusinessException{
//	Impuesto domain = null;
//	domain = boImpuesto.modificarImpuesto(o);
//	return domain;
//}

public List<Impuesto> getListaPersonaJuridica(Impuesto o)throws BusinessException {
	List<Impuesto> listaJuri = null;
	try{
		listaJuri = boImpuesto.getListaPersonaJuridica(o);
		
	}catch(BusinessException e){
		throw e;
	}catch(Exception e){
		throw new BusinessException(e);
	}
	return listaJuri;
	}

public List<Impuesto> getListaNombreDniRol(Impuesto o)throws BusinessException {
	List<Impuesto> listaJuri = null;
	try{
		listaJuri = boImpuesto.getListaNombreDniRol(o);
		
	}catch(BusinessException e){
		throw e;
	}catch(Exception e){
		throw new BusinessException(e);
	}
	return listaJuri;
	}
//public List<Impuesto> getBuscar(Impuesto o)throws BusinessException {
//	List<Impuesto> listaJuri = null;
//	try{
//		listaJuri = boImpuesto.getBuscar(o);
//		
//	}catch(BusinessException e){
//		throw e;
//	}catch(Exception e){
//		throw new BusinessException(e);
//	}
//	return listaJuri;
//	}
}
