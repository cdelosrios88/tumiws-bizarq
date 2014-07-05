package pe.com.tumi.cobranza.planilla.facade;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.cobranza.planilla.domain.CobroPlanillas;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoId;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumenId;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.EnvioconceptoId;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.EnviomontoId;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.composite.EfectuadoConceptoComp;
import pe.com.tumi.cobranza.planilla.domain.composite.EnvioConceptoComp;
import pe.com.tumi.cobranza.planilla.domain.composite.ItemPlanilla;
import pe.com.tumi.cobranza.planilla.domain.composite.PlanillaAdministra;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;

@Local
public interface PlanillaFacadeLocal {
	public Envioconcepto grabarEnvioconcepto(Envioconcepto o) throws BusinessException;
	public Envioconcepto getEnvioconceptoPorPk(EnvioconceptoId pId) throws BusinessException;
	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioYModalidad(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad) throws BusinessException;
	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocio(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio) throws BusinessException;
	public List<PlanillaAdministra> getPlanillaPorIdEstructuraYTipoSocioYPeriodo(EstructuraId pk,Integer intTipoSocio,Integer intPeriodo, List<Socio> socios) throws BusinessException;
	public void grabarEnvio(List<ItemPlanilla> listaItemPlanilla,Usuario pUsuario) throws BusinessException;	
	public List<ItemPlanilla> getPlanillaDeEfectuadoPorIdEstructuraYTipoSocioYPeriodo(EstructuraId pk,Integer intTipoSocio,Integer intPeriodo) throws BusinessException;
	public Envioconcepto getEnvioconceptoPorPkMaxPeriodo(EnvioconceptoId pPK) throws BusinessException;
	public List<EnvioConceptoComp> getListaEnviomontoDeBuscar(EnvioConceptoComp dtoFiltroDeEnvio) throws BusinessException;
	
	public Integer getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidad(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad) throws BusinessException;
	public List<EfectuadoResumen> getListaEfectuadoResumenParaIngreso(Persona persona, Usuario usuario) throws BusinessException;
	public EfectuadoResumen modificarEfectuadoResumen(EfectuadoResumen efectuadoResumen) throws BusinessException;
	public CobroPlanillas grabarCobroPlanillas(CobroPlanillas cobroPlanillas) throws BusinessException;
	public EfectuadoResumen getEfectuadoResumenPorId(EfectuadoResumenId efectuadoResumenId) throws BusinessException;
	
  	public List<SolicitudCtaCte> buscarSolicitudCtaCte(Integer intEmpresasolctacte, Integer intSucuIdsucursalsocio, Integer intTipoSolicitud, Integer intEstadoSolicitud, String nroDni) throws BusinessException;
  	public SolicitudCtaCte grabarSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException;
  	public SolicitudCtaCte anularSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException;
	public SolicitudCtaCte obtenerSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException;
	public SolicitudCtaCte modificarSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException;
   	public List<SolicitudCtaCte> buscarMovimientoCtaCte(Integer intEmpresasolctacte, Integer intSucuIdsucursalsocio, Integer intTipoSolicitud, Integer intEstadoSolicitud, String nroDni,Integer intCboParaTipoEstado,
            Date    dtFechaInicio,Date dtFechaFin) throws BusinessException;
   	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad,Integer intTipoEstructura) throws BusinessException;
 	public Integer getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstr(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad,Integer intTipoEstructura) throws BusinessException;
	public List<Envioconcepto> getListaEnvioconceptoPorEmprPeriodoNroCta(Integer intEmpresa, Integer intPeriodo, Integer nroCta) throws BusinessException;
	public List<Envioconcepto> getListaEnvioconceptoPorEmprNroCta(Integer intEmpresa, Integer nroCta) throws BusinessException;	
	public List<Envioconcepto> getListaXPerCtaItemCto(Integer intPeriodo, Integer nroCta, Integer intItemenvioconcepto, Integer intEstado ) throws BusinessException;
	
	public Integer getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocio(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio) throws BusinessException;	
	
	public List<Envioresumen> getListaEnvioresumenBuscar(EnvioConceptoComp dtoFiltroDeEnvio) throws BusinessException;
	public List<EfectuadoResumen> getListaEfectuadoResumenBuscar(EfectuadoConceptoComp dtoFiltroDeEfectuado) throws BusinessException;

	public List<Enviomonto> getPlanillaDeEfectuada(Enviomonto o, Integer intMaxEnviado) throws BusinessException;
	public List<Enviomonto> getListaPlanillaEfectuada(SucursalId pk,Integer intTipoSocio,Integer pIntModalidadCod, Integer pIntEstadoCod) throws BusinessException;
		
	/** CREADO 05-08-2013 **/
 	public List<Envioconcepto> getListaEnvioconceptoPorPkExpedienteCredito(ExpedienteId pId) throws BusinessException;
 	public List<Enviomonto> getListaPorEnvioConcepto(Envioconcepto pId) throws BusinessException;
   	public List<Efectuado> getListaEfectuadoPorPkEnviomontoYPeriodo(EnviomontoId pId, Envioconcepto envioConcepto) throws BusinessException;
   	public List<EfectuadoConcepto> getListaPorEfectuadoYExpediente(EfectuadoId pId, Expediente expediente) throws BusinessException;
   	/** CREADO 08-08-2013 **/
   	public List<Envioconcepto> getListaEnvioconceptoPorCtaCptoDetYPer(CuentaConceptoDetalleId pId, Integer intPeriodo) throws BusinessException;
   	/** CREADO 19-08-2013 **/
   	public Efectuado getEfectuadoPorPk(EfectuadoId pId) throws BusinessException;
   	
   	public List<Efectuado> getListaEfectuadoXNiveCodigoModaliPeriodoTipoSocio(Integer intIdEmpresa,EstructuraId pId,
			   Integer intTipoModalidad,Integer intPeriodo, Integer intTipoSocio) throws BusinessException;
   	
   	public List<PlanillaAdministra> getPlanillaPorIdEstructuraYTipoSocioYPeriodoCAS(EstructuraId pk,
   																				    Integer intTipoSocio,
   																				    Integer intPeriodo) throws BusinessException;
   	   	
  //AUTOR Y FECHA CREACION: JCHAVEZ / 09-09-2013 
   	public List<Envioconcepto> getListaEnvioconceptoPorCtaYPer(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException;
	public List<EfectuadoResumen> getListaPorEntidadyPeriodo(Integer pIntEmpresaPk, Integer pIntPeriodoplanilla, 
			 Integer pIntTiposocioCod, Integer pIntModalidadCod,
			 Integer pIntNivel, Integer pIntCodigo) throws BusinessException;
	public List<CobroPlanillas> getPorEfectuadoResumen(EfectuadoResumen efectuadoResumen) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 10-09-2013
	public List<Envioconcepto> getListaPorCuentaYPeriodo(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException;
	public Envioconcepto getEnvioConceptoPorEmpPerCta(Integer intEmpresa, Integer intPeriodo, Integer nroCta) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 14-09-2013
	public List<EfectuadoConcepto> getListaPorEfectuadoPKEnvioConcepto (EfectuadoId pid, Envioconcepto envioConcepto) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 16-09-2013
	public List<EfectuadoConcepto> getListaPorEfectuado(EfectuadoId pid) throws BusinessException;
	
	public List<Socio> getListaSocio(EstructuraId pk,Integer intTipoSocio) throws BusinessException;
	
	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioCAS(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio) throws BusinessException;
	
	public List<Enviomonto> getListaEnvioMontoPlanillaEfectuada(Enviomonto o,Integer intMaxEnviado) throws BusinessException;
	
		
	public List<EfectuadoResumen> getPlanillaEfectuadaResumen(Enviomonto o, Integer periodo) throws BusinessException;
	
	public void grabarPlanillaEfectuada(List<EfectuadoResumen> listaEfectuadoResumen,Usuario pUsuario) throws BusinessException;
	
	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioM(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio, Integer intModalidad) throws BusinessException;
	
	public Integer getOtraModalidad(Integer intSegundaModalidad, SocioEstructura o) throws BusinessException;
	
	public List<EfectuadoConceptoComp> getEmpezandoEfectuado(Enviomonto o, Integer intMaxEnviado, Integer intSegundaModalidad)throws BusinessException;
	
	public List<EfectuadoResumen> aEfectuar(List<EfectuadoConceptoComp> listaEfectuadoConceptoComp,Enviomonto eo,Integer intMaxEnviado) throws BusinessException;
	 
	public List<EfectuadoConceptoComp> getCompletandoEfectuado(Enviomonto o, Integer intMaxEnviado, Integer intSegundaModalidad)throws BusinessException; 
	
	public List<EfectuadoResumen> aCompletarEfectuado(List<EfectuadoConceptoComp> listaEfectuadoConceptoComp,Enviomonto eo,Integer intMaxEnviado) throws BusinessException;
	
	public List<Efectuado> getListaPorEmpCtaPeriodo(Integer intEmpresacuentaPk, Integer intCuentaPk, Integer intPeriodoPlanilla) throws BusinessException;
	public List<Envioconcepto> getListaEnvioMinimoPorEmpCtaYEstado(Integer intEmpresa, Integer intCuenta) throws BusinessException;
	public List<Enviomonto> getListaXItemEnvioCtoGral(Enviomonto o) throws BusinessException;
	//jchavez 19.06.2014
	public List<EfectuadoResumen> getListaEfectuadoResumenParaIngreso2(EstructuraComp estructuraComp, Usuario usuario) throws BusinessException;
}
