package pe.com.tumi.servicio.prevision.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;

@Remote
public interface AutorizacionPrevisionFacadeRemote {

	public ExpedientePrevision grabarAutorizacionPrevision(ExpedientePrevision o) throws BusinessException;
	public List<AutorizaPrevision> getListaAutorizaPrevisionPorPkExpediente(ExpedientePrevisionId pId) throws BusinessException;
	public List<AutorizaVerificaPrevision> getListaVerificaPrevisionPorPkExpediente(ExpedientePrevisionId pId) throws BusinessException;
	public LibroDiario aprobarPrevision(SocioComp socioComp, Integer intPeriodo, RequisitoPrevision requisitoPrev, ExpedientePrevision expedientePrevisionSeleccionado,Usuario usuario,
				ExpedientePrevision expedientePrevision, Integer intEstadoAprobado )throws BusinessException;
	
	public SolicitudCtaCte grabarSolicitudCtaCteParaPrevision(SocioComp socioComp,Integer intPeriodo, RequisitoPrevision requisitoPrev, Usuario usuario,
	ExpedientePrevision expedientePrevision)  throws BusinessException;
	public LibroDiario generarAsientoContablePrevisionRetiroYTransferencia(SolicitudCtaCte solicitudCtaCte, SocioComp socioComp, Usuario usuario, ExpedientePrevision expedientePrevision) 
	throws BusinessException;
	public SolicitudCtaCte grabarSolicitudCtaCteParaPrevisionDevolucionRetiro(SocioComp socioComp,Integer intPeriodo, RequisitoPrevision requisitoPrev, Usuario usuario,
			ExpedientePrevision expedientePrevision, LibroDiario libroDiario)  throws BusinessException;
	public SolicitudCtaCte grabarSolicitudCtaCteParaPrevisionDevolucionSepelio(SocioComp socioComp,Integer intPeriodo, RequisitoPrevision requisitoPrev, Usuario usuario,
			ExpedientePrevision expedientePrevision, LibroDiario libroDiario)  throws BusinessException;
}
