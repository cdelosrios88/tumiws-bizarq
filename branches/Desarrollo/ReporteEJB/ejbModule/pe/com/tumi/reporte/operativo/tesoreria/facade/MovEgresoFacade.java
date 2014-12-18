package pe.com.tumi.reporte.operativo.tesoreria.facade;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.tesoreria.bo.MovEgresoBO;
import pe.com.tumi.reporte.operativo.tesoreria.domain.EgresoFondoFijo;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;

/**
 * Session Bean implementation class IngresoCajaFacade
 */
@Stateless
@Local(MovEgresoFacadeLocal.class)
@Remote(MovEgresoFacadeRemote.class)
public class MovEgresoFacade extends TumiFacade implements MovEgresoFacadeRemote, MovEgresoFacadeLocal {
	protected  static Logger log = Logger.getLogger(MovEgresoFacade.class);
	private MovEgresoBO boMovEgreso = (MovEgresoBO)TumiFactory.get(MovEgresoBO.class);
	
    /**
     * @see TumiFacade#TumiFacade()
     */
    public MovEgresoFacade() {
        super();
    }
    
    public List<MovEgreso> getListFondoFijo(int intSucursal,int intAnio, int intTipoFondoFijo) throws BusinessException{
		List<MovEgreso> lista = null;
		try {
			lista = boMovEgreso.getListFondoFijo(intSucursal,intAnio,intTipoFondoFijo);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public MovEgreso getListEgresoById(MovEgreso o) throws BusinessException{
		MovEgreso domain = null;
		try {
			domain = boMovEgreso.getListEgresoById(o);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
    
    public List<EgresoFondoFijo> getEgresos (MovEgreso objMovEgreso) throws BusinessException {
    	List<EgresoFondoFijo> lista  = null;
    	try {
			lista = boMovEgreso.getEgresos(objMovEgreso);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
    	return lista;
    }

}
