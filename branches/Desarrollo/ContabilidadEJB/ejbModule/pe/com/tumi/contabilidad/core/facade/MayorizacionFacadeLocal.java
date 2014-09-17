/**
 * @author Bizarq
 * Cod. Req: REQ14-004: Proceso Mayorización
 * Created: 16/09/2014
 * */

package pe.com.tumi.contabilidad.core.facade;

import pe.com.tumi.framework.negocio.exception.BusinessException;

public interface MayorizacionFacadeLocal {
	public Integer processMayorizacion(Integer intPeriodo)throws BusinessException;
}
