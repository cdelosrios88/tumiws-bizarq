package pe.com.tumi.parametro.auditoria.facade;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.auditoria.bo.AuditoriaBO;
import pe.com.tumi.parametro.auditoria.bo.AuditoriaMotivoBO;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.domain.AuditoriaMotivo;

@Stateless
public class AuditoriaFacade extends TumiFacade implements AuditoriaFacadeRemote, AuditoriaFacadeLocal {
	
	protected static Logger log = Logger.getLogger(AuditoriaFacade.class);
       
	private AuditoriaBO boAuditoria = (AuditoriaBO)TumiFactory.get(AuditoriaBO.class);
	private AuditoriaMotivoBO boAuditoriaMotivo = (AuditoriaMotivoBO)TumiFactory.get(AuditoriaMotivoBO.class);
	
	public Auditoria grabarAuditoria(Auditoria o)throws BusinessException{
		Auditoria dto = null;
		try{
			dto = boAuditoria.grabarAuditoria(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Auditoria getAuditoriaDeMaximoPkPorTablaYColumnaYLlave1(String strTabla,String strColumna,String strLlave) throws BusinessException{
		Auditoria dto = null;
		try{
			dto = boAuditoria.getAuditoriaDeMaximoPkPorTablaYColumnaYLlave1(strTabla,strColumna,strLlave);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AuditoriaMotivo grabarAuditoriaMotivo(AuditoriaMotivo o)throws BusinessException{
		AuditoriaMotivo dto = null;
		try{
			dto = boAuditoriaMotivo.grabar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

    /* Inicio - Auditoria - GTorresBrousset 02.may.2014 */
	/* Método que carga los datos comunes de la estructura Auditoria */
	public Auditoria beanAuditoria(Integer sessionIdEmpresa, Integer sessionIdUsuario, Integer intTipoCambio, 
			List<String> llaves, String tabla) throws BusinessException {
		log.info("Inicio");
		Auditoria auditoria = new Auditoria();
		Calendar fecHoy = Calendar.getInstance();
		try {		
			auditoria.setListaAuditoriaMotivo(null);
			auditoria.setStrTabla(tabla);
			auditoria.setIntEmpresaPk(sessionIdEmpresa);
			for (int i = 0; i < llaves.size(); i ++) {
				if(i == 0) 
					auditoria.setStrLlave1(llaves.get(i));
				if(i == 1) 
					auditoria.setStrLlave2(llaves.get(i));
				if(i == 2) 
					auditoria.setStrLlave3(llaves.get(i));
				if(i == 3) 
					auditoria.setStrLlave4(llaves.get(i));
				if(i == 4) 
					auditoria.setStrLlave5(llaves.get(i));
				if(i == 5) 
					auditoria.setStrLlave6(llaves.get(i));
				if(i == 6) 
					auditoria.setStrLlave7(llaves.get(i));
				if(i == 7) 
					auditoria.setStrLlave8(llaves.get(i));
				if(i == 8) 
					auditoria.setStrLlave9(llaves.get(i));
				if(i == 9) 
					auditoria.setStrLlave10(llaves.get(i));
			}
			auditoria.setIntTipo(intTipoCambio);
			auditoria.setTsFecharegistro(new Timestamp(fecHoy.getTimeInMillis()));
			auditoria.setIntPersonaPk(sessionIdUsuario);
		} catch(Exception e) {
			throw new BusinessException(e);
		}
		log.info("Fin");
		return auditoria;
	}
    /* Fin - Auditoria - GTorresBrousset 02.may.2014 */
	
}
