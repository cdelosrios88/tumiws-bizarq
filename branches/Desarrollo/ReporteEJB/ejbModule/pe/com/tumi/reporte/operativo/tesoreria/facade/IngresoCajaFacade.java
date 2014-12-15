package pe.com.tumi.reporte.operativo.tesoreria.facade;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.tesoreria.bo.IngresoCajaBO;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

/**
 * Session Bean implementation class IngresoCajaFacade
 */
@Stateless
@Local(IngresoCajaFacadeLocal.class)
@Remote(IngresoCajaFacadeRemote.class)
public class IngresoCajaFacade extends TumiFacade implements IngresoCajaFacadeRemote, IngresoCajaFacadeLocal {
	protected  static Logger log = Logger.getLogger(IngresoCajaFacade.class);
	private IngresoCajaBO boIngresoCaja = (IngresoCajaBO)TumiFactory.get(IngresoCajaBO.class);
	
    /**
     * @see TumiFacade#TumiFacade()
     */
    public IngresoCajaFacade() {
        super();
    }
    
    public List<IngresoCaja> getListaIngresosByTipoIngreso(IngresoCaja o) throws BusinessException{
		List<IngresoCaja> lista = null;
		try {
			lista = boIngresoCaja.getListaIngresosByTipoIngreso(o);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

}
