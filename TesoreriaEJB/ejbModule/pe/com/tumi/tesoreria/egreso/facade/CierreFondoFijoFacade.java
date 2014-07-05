package pe.com.tumi.tesoreria.egreso.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.bo.ControlFondoFijoBO;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;


@Stateless
public class CierreFondoFijoFacade implements CierreFondoFijoFacadeRemote, CierreFondoFijoFacadeLocal {

	protected  static Logger log = Logger.getLogger(CierreFondoFijoFacade.class);
	private ControlFondoFijoBO boControlFondoFijo = (ControlFondoFijoBO)TumiFactory.get(ControlFondoFijoBO.class);
	
	public List<ControlFondosFijos> getControlFondoFijo(Integer intEmpresa, Integer intTipoFondo, Integer intIdSucursal, Integer intIdSubSucursal) throws BusinessException {
		List<ControlFondosFijos> lista = null;
    	try{
    		lista = boControlFondoFijo.getControlFondoFijo(intEmpresa, intTipoFondo, intIdSucursal, intIdSubSucursal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
