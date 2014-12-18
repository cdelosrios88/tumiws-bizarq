package pe.com.tumi.reporte.operativo.tesoreria.facade;
/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-009       			15/12/2014     Christian De los Ríos        Creaciòn de componente         
*/

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.tesoreria.bo.MovEgresoBO;
import pe.com.tumi.reporte.operativo.tesoreria.domain.EgresoFondoFijo;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;

/**
 * Clase de acceso Facade con implementacion Local y Remote
 * 
 * @author Bizarq
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
    
    /**
	 * Metodo que recupera la lista de Egresos realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param intSucursal, Identificador de la sucursal.
	 * @param intAnio, Anio de consulta.
	 * @param intTipoFondoFijo, Indicador de tipo de fondo fijo.
	 * @return Lista de entidades del tipo EgresosCaja.
	 * 
	 * @throws BusinessException
	 */
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
    
    /**
	 * Metodo que recupera la lista de Egresos de monto fijo realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param objMovEgreso, Entidad de tipo Moviemiento Egreso.
	 * @return Lista de entidades del tipo EgresoFondoFijo.
	 * 
	 * @throws BusinessException
	 */
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
