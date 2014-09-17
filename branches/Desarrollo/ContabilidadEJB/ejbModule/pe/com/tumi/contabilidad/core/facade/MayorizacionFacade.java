/**
 * @author Bizarq
 * Cod. Req: REQ14-004: Proceso Mayorización
 * Created: 16/09/2014
 * */
package pe.com.tumi.contabilidad.core.facade;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import pe.com.tumi.contabilidad.cierre.bo.LibroMayorBO;
import pe.com.tumi.contabilidad.cierre.service.LibroMayorService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

/**
 * Session Bean implementation class MayorizacionFacade
 */
@Stateless
@Local(MayorizacionFacadeLocal.class)
@Remote(MayorizacionFacadeRemote.class)
public class MayorizacionFacade extends TumiFacade implements MayorizacionFacadeRemote, MayorizacionFacadeLocal {
    
	//LibroDiarioBO boLibroDiario = (LibroDiarioBO)TumiFactory.get(LibroDiarioBO.class);
	//LibroDiarioService libroDiarioService = (LibroDiarioService)TumiFactory.get(LibroDiarioService.class);
	LibroMayorBO boLibroMayor = (LibroMayorBO)TumiFactory.get(LibroMayorBO.class);
	LibroMayorService libroMayorService = (LibroMayorService)TumiFactory.get(LibroMayorService.class);
	
    /**
     * @see TumiFacade#TumiFacade()
     */
    public MayorizacionFacade() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public Integer processMayorizacion(Integer intPeriodo)throws BusinessException{
    	Integer intIdLibroMayor = null;
		try{
			intIdLibroMayor = boLibroMayor.processMayorizacion(intPeriodo);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return intIdLibroMayor;
	}
    
}