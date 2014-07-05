package pe.com.tumi.parametro.auditoria.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.domain.AuditoriaMotivo;

@Remote
public interface AuditoriaFacadeRemote {
	public Auditoria grabarAuditoria(Auditoria o)throws BusinessException;
	public Auditoria getAuditoriaDeMaximoPkPorTablaYColumnaYLlave1(String strTabla,String strColumna,String strLlave) throws BusinessException;
	public AuditoriaMotivo grabarAuditoriaMotivo(AuditoriaMotivo o)throws BusinessException;
	public Auditoria beanAuditoria(Integer sessionIdEmpresa, Integer sessionIdUsuario, Integer intTipoCambio, 
			List<String> llaves, String tabla) throws BusinessException;
}
