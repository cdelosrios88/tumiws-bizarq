/**
* Resumen.
* Objeto: MayorizacionFacade
* Descripción:  Facade principal del proceso de mayorización.
* Fecha de Creación: 17/09/2014.
* Requerimiento de Creación: REQ14-004
* Autor: Bizarq
*/
package pe.com.tumi.contabilidad.core.facade;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import pe.com.tumi.contabilidad.cierre.bo.LibroMayorBO;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
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
    
    public Integer processMayorizacion(LibroMayor o)throws BusinessException{
    	Integer intIdLibroMayor = null;
		try{
			intIdLibroMayor = boLibroMayor.processMayorizacion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return intIdLibroMayor;
	}
    
	//Inicio: REQ14-004 - bizarq - 16/09/2014
	public List<LibroMayor> buscarLibroMayoreHistorico(LibroMayor o) throws BusinessException{
		List<LibroMayor> lista = null;
   		try{
   			lista = boLibroMayor.getListMayorHist(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	//Fin: REQ14-004 - bizarq - 16/09/2014

}