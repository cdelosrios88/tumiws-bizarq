package pe.com.tumi.cobranza.planilla.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.adminCuenta.domain.PersonaJuridica;
import pe.com.tumi.cobranza.planilla.bo.CobroPlanillasBO;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoBO;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoConceptoBO;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoResumenBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioconceptoBO;
import pe.com.tumi.cobranza.planilla.bo.EnviomontoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioresumenBO;
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
import pe.com.tumi.cobranza.planilla.service.EfectuadoResumenService;
import pe.com.tumi.cobranza.planilla.service.EfectuadoService;
import pe.com.tumi.cobranza.planilla.service.EnvioService;
import pe.com.tumi.cobranza.planilla.service.PlanillaService;
import pe.com.tumi.cobranza.planilla.service.SolicitudCtaCteService;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

@Stateless
public class PlanillaFacade extends TumiFacade implements PlanillaFacadeRemote, PlanillaFacadeLocal {
       
	protected static Logger log = Logger.getLogger(PlanillaFacade.class);
	
	
	EnvioconceptoBO boEnvioconcepto = (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);
	PlanillaService planillaService = (PlanillaService)TumiFactory.get(PlanillaService.class);
	EnvioService envioService = (EnvioService)TumiFactory.get(EnvioService.class);
	EfectuadoBO boEfectuado = (EfectuadoBO)TumiFactory.get(EfectuadoBO.class);
	EfectuadoResumenService efectuadoResumenService = (EfectuadoResumenService)TumiFactory.get(EfectuadoResumenService.class);
	EfectuadoResumenBO boEfectuadoResumen = (EfectuadoResumenBO)TumiFactory.get(EfectuadoResumenBO.class);
	CobroPlanillasBO boCobroPlanillas = (CobroPlanillasBO)TumiFactory.get(CobroPlanillasBO.class);
	
	SolicitudCtaCteService solicitudCtaCteService = (SolicitudCtaCteService)TumiFactory.get(SolicitudCtaCteService.class);
	EnviomontoBO  boEnviomonto = (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
	EfectuadoService efectuadoService = (EfectuadoService)TumiFactory.get(EfectuadoService.class);
	EfectuadoConceptoBO boEfectuadoConceptoBO = (EfectuadoConceptoBO)TumiFactory.get(EfectuadoConceptoBO.class);
	EnvioresumenBO boEnvioResumen =(EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
	
	public Envioconcepto grabarEnvioconcepto(Envioconcepto o) throws BusinessException{
    	Envioconcepto dto = null;
		try{
			dto = boEnvioconcepto.grabarEnvioconcepto(o);
		}catch(BusinessException e){			
			throw e;
		}catch(Exception e){			
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Envioconcepto getEnvioconceptoPorPk(EnvioconceptoId pId) throws BusinessException{
    	Envioconcepto dto = null;
   		try{
   			dto = boEnvioconcepto.getEnvioconceptoPorPk(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioYModalidad(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad) throws BusinessException{
    	Integer intEscalar = null;
   		try{
   			intEscalar = boEnvioconcepto.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioYModalidad(intEmpresa,pk,intTipoSocio, intModalidad);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return intEscalar;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocio(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio) throws BusinessException{
    	Integer intEscalar = null;
   		try{
   			
   			intEscalar = boEnvioResumen.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocio(intEmpresa,pk,intTipoSocio);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return intEscalar;
   	}
    
    public List<PlanillaAdministra> getPlanillaPorIdEstructuraYTipoSocioYPeriodo(EstructuraId pk,Integer intTipoSocio,Integer intPeriodo, List<Socio> socios) throws BusinessException{
		List<PlanillaAdministra> lista = null;
		try{
			lista = planillaService.getPlanillaPorIdEstructuraYTipoSocioYPeriodo(pk,intTipoSocio,intPeriodo, socios);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public void grabarEnvio(List<ItemPlanilla> listaItemPlanilla,Usuario pUsuario) throws BusinessException{
		try{
			envioService.grabarEnvio(listaItemPlanilla,pUsuario);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){			
			context.setRollbackOnly();
			throw new BusinessException(e);			
		}
	}
    

    
    public List<ItemPlanilla> getPlanillaDeEfectuadoPorIdEstructuraYTipoSocioYPeriodo(EstructuraId pk,Integer intTipoSocio,Integer intPeriodo) throws BusinessException{
		log.info("planillaFacade.getPlanillaDeEfectuadoPorIdEstructuraYTipoSocioYPeriodo()");
    	List<ItemPlanilla> lista = null;
		try{
			lista = planillaService.getPlanillaDeEfectuadoPorIdEstructuraYTipoSocioYPeriodo(pk,intTipoSocio, intPeriodo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public Envioconcepto getEnvioconceptoPorPkMaxPeriodo(EnvioconceptoId pPK) throws BusinessException{
    	Envioconcepto dto = null;
		try{
			dto =  boEnvioconcepto.getEnvioconceptoPorPkMaxPeriodo(pPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Integer getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidad(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad) throws BusinessException{
    	Integer intEscalar = null;
   		try{
   			intEscalar = boEfectuado.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidad(intEmpresa,pk, intTipoSocio, intModalidad);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return intEscalar;
   	}
    public List<Envioresumen> getListaEnvioresumenBuscar(EnvioConceptoComp dtoFiltroDeEnvio)throws BusinessException{
    	log.info("planillaFacade.getListaEnvioresumenBuscar()");
    	List<Envioresumen> lista = null;
    	try{
    		lista = envioService.getListaEnvioresumenBuscar(dtoFiltroDeEnvio);
    		if(lista!=null && !lista.isEmpty()){
    			log.info(" si hay lista  en planillaFacade.getListaEnvioresumenBuscar()");
//    			for(Envioresumen envioresumen:lista){    				
    				log.info("cant en listaEnvioResumen="+lista.size());
//        		}
    		}else{
    			log.info("lista null en planillaFacade.getListaEnvioresumenBuscar()");
    		} 		
    		
    	}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
    }
    
    public List<EnvioConceptoComp> getListaEnviomontoDeBuscar(EnvioConceptoComp dtoFiltroDeEnvio) throws BusinessException{
    	log.info("planillaFacade.getListaEnviomontoDeBuscar()");
    	List<EnvioConceptoComp> lista = null;
    	List<EnvioConceptoComp> listaTmp = null;
    	List<Sucursal> listaSucursal = null;
    	Integer intTipoSucursal = null;
    	EmpresaFacadeRemote remoteEmpresa = null;
    	intTipoSucursal = dtoFiltroDeEnvio.getEstructuraDetalle().getIntSeguSucursalPk();
    	Integer intSucursal = null;
    	Integer intSucursalTmp = null;
    	try{
	    	if(intTipoSucursal.compareTo(new Integer(0))>0){
	    		lista = envioService.getListaEnviomontoDeBuscar(dtoFiltroDeEnvio);
	    	}else{
	    		remoteEmpresa = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
	    		if(intTipoSucursal.compareTo(Constante.PARAM_T_TOTALESSUCURSALES_SEDES)==0){
	    			listaSucursal = remoteEmpresa.getListaSucursalPorEmpresaYTipoSucursal(
	    											dtoFiltroDeEnvio.getEstructura().getIntPersEmpresaPk(),
	    											Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL);
	    		}else if(intTipoSucursal.compareTo(Constante.PARAM_T_TOTALESSUCURSALES_SUCURSALES)==0){
	    			listaSucursal = remoteEmpresa.getListaSucursalPorEmpresaYTodoTipoSucursal(
	    											dtoFiltroDeEnvio.getEstructura().getIntPersEmpresaPk());
	    		}else if(intTipoSucursal.compareTo(Constante.PARAM_T_TOTALESSUCURSALES_FILIALES)==0){
	    			listaSucursal = remoteEmpresa.getListaSucursalPorEmpresaYTipoSucursal(
	    											dtoFiltroDeEnvio.getEstructura().getIntPersEmpresaPk(), 
	    											Constante.PARAM_T_TIPOSUCURSAL_FILIAL);
	    		}else if(intTipoSucursal.compareTo(Constante.PARAM_T_TOTALESSUCURSALES_AGENCIAS)==0){
	    			listaSucursal = remoteEmpresa.getListaSucursalPorEmpresaYTipoSucursal(
	    											dtoFiltroDeEnvio.getEstructura().getIntPersEmpresaPk(), 
	    											Constante.PARAM_T_TIPOSUCURSAL_AGENCIA);
	    		}else if(intTipoSucursal.compareTo(Constante.PARAM_T_TOTALESSUCURSALES_OFICINAPRINCIPAL)==0){
	    			listaSucursal = remoteEmpresa.getListaSucursalPorEmpresaYTipoSucursal(
	    											dtoFiltroDeEnvio.getEstructura().getIntPersEmpresaPk(), 
	    											Constante.PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL);
	    		}
	    		if(listaSucursal!= null && listaSucursal.size()>0){
	    			lista = new ArrayList<EnvioConceptoComp>();
	    			intSucursal = dtoFiltroDeEnvio.getEstructuraDetalle().getIntSeguSucursalPk();
	    			for(int i=0;i<listaSucursal.size();i++){
	    				intSucursalTmp = listaSucursal.get(i).getId().getIntIdSucursal() ;
	    				dtoFiltroDeEnvio.getEstructuraDetalle().setIntSeguSucursalPk(intSucursalTmp);
	    				listaTmp = envioService.getListaEnviomontoDeBuscar(dtoFiltroDeEnvio);
	    				if(listaTmp!=null && listaTmp.size()>0 ){
	    					lista.addAll(listaTmp);
	    				}
	    			}
	    			dtoFiltroDeEnvio.getEstructuraDetalle().setIntSeguSucursalPk(intSucursal);
	    		}
	    	}
    	}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
    	return lista;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<EfectuadoResumen> getListaEfectuadoResumenParaIngreso(Persona persona, Usuario usuario) throws BusinessException{
    	List<EfectuadoResumen> lista = null;
   		try{
   			lista = efectuadoResumenService.getListaEfectuadoResumenParaIngreso(persona, usuario);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    public EfectuadoResumen modificarEfectuadoResumen(EfectuadoResumen efectuadoResumen) throws BusinessException{
    	EfectuadoResumen dto = null;
		try{
			dto = boEfectuadoResumen.modificar(efectuadoResumen);
		}catch(BusinessException e){			
			throw e;
		}catch(Exception e){			
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public CobroPlanillas grabarCobroPlanillas(CobroPlanillas cobroPlanillas) throws BusinessException{
    	CobroPlanillas dto = null;
		try{
			dto = boCobroPlanillas.grabar(cobroPlanillas);
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
   	public EfectuadoResumen getEfectuadoResumenPorId(EfectuadoResumenId efectuadoResumenId) throws BusinessException{
    	EfectuadoResumen dto = null;
   		try{
   			dto = boEfectuadoResumen.getPorPk(efectuadoResumenId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<SolicitudCtaCte> buscarSolicitudCtaCte(Integer intEmpresasolctacte, Integer intSucuIdsucursalsocio, Integer intTipoSolicitud, Integer intEstadoSolicitud, String nroDni) throws BusinessException{
    	List<SolicitudCtaCte> listaSolicitudCtaCte = null;
   		try{
   			listaSolicitudCtaCte = solicitudCtaCteService.buscarSolicitudCtaCte(intEmpresasolctacte, intSucuIdsucursalsocio, intTipoSolicitud, intEstadoSolicitud, nroDni);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return listaSolicitudCtaCte;
   	}
	public SolicitudCtaCte grabarSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException{
		
		SolicitudCtaCte dato= null;
	  try{	
		  dato =  solicitudCtaCteService.grabarSolicitudCtaCte(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		
		return dato;
	 }
	
    public SolicitudCtaCte anularSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException{
		
		SolicitudCtaCte dato= null;
	 try{	
		  dato =  solicitudCtaCteService.anularSolicitudCtaCte(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		
		return dato;
   }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public SolicitudCtaCte obtenerSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException{
    	SolicitudCtaCte solicitudCtaCte = null;
   		try{
   			solicitudCtaCte = solicitudCtaCteService.obtenerSolicitudCtaCte(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return solicitudCtaCte;
   	}
    
    
    public SolicitudCtaCte modificarSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException{
		
		SolicitudCtaCte dato= null;
	  try{	
		  dato =  solicitudCtaCteService.modificarSolicitudCtaCte(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		
		return dato;
	 }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<SolicitudCtaCte> buscarMovimientoCtaCte(Integer intEmpresasolctacte, Integer intSucuIdsucursalsocio, Integer intTipoSolicitud, Integer intEstadoSolicitud, String nroDni,Integer intCboParaTipoEstado,
	                                                    Date    dtFechaInicio,Date dtFechaFin) throws BusinessException{
    	List<SolicitudCtaCte> listaSolicitudCtaCte = null;
   		try{
   			listaSolicitudCtaCte = solicitudCtaCteService.buscarMovimientoCtaCte(intEmpresasolctacte, intSucuIdsucursalsocio, intTipoSolicitud, intEstadoSolicitud, nroDni,intCboParaTipoEstado,dtFechaInicio,dtFechaFin);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return listaSolicitudCtaCte;
   	}
	
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad,Integer intTipoEstructura) throws BusinessException{
    	Integer intEscalar = null;
   		try{
   			intEscalar = boEnvioconcepto.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(intEmpresa,pk,intTipoSocio, intModalidad,intTipoEstructura);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return intEscalar;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Integer getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstr(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad,Integer intTipoEstructura) throws BusinessException{
    	Integer intEscalar = null;
   		try{
   			intEscalar = boEfectuado.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstrucura(intEmpresa,pk, intTipoSocio, intModalidad,intTipoEstructura);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return intEscalar;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Envioconcepto> getListaEnvioconceptoPorEmprPeriodoNroCta(Integer intEmpresa, Integer intPeriodo, Integer nroCta) throws BusinessException{
    	List<Envioconcepto> lista = null;
   		try{
   			lista = boEnvioconcepto.getListaEnvioconceptoPorEmprPeriodoNroCta(intEmpresa, intPeriodo, nroCta);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Envioconcepto> getListaEnvioconceptoPorEmprNroCta(Integer intEmpresa, Integer nroCta) throws BusinessException{
    	List<Envioconcepto> lista = null;
   		try{
   			lista = boEnvioconcepto.getListaEnvioconceptoPorEmprNroCta(intEmpresa, nroCta);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Envioconcepto> getListaXPerCtaItemCto(Integer intPeriodo, Integer nroCta, Integer intItemenvioconcepto, Integer intEstado ) throws BusinessException{
    	List<Envioconcepto> lista = null;
   		try{
   			lista = boEnvioconcepto.getListaXPerCtaItemCto(intPeriodo, nroCta, intItemenvioconcepto, intEstado);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Integer getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocio(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio) throws BusinessException{
    	Integer intEscalar = null;
   		try{
   			intEscalar = boEfectuado.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocio(intEmpresa,pk, intTipoSocio);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return intEscalar;
   	}
    
    public List<EfectuadoResumen> getListaEfectuadoResumenBuscar(EfectuadoConceptoComp dtoFiltroDeEfectuado)throws BusinessException{
    	log.info("planillaFacade.getListaEfectuadoResumenBuscar() INICIO");
    	List<EfectuadoResumen> lista = null;
    	try{
    		lista = envioService.getListaEfectuadoResumenBuscar(dtoFiltroDeEfectuado);
    		if(lista!=null && !lista.isEmpty()){
    			log.info(" si hay lista  en planillaFacade.getListaEfectuadoResumenBuscar()"+lista.size());
    			
    		}else{
    			log.info("lista null en planillaFacade.getListaEfectuadoResumenBuscar()");
    			} 		
    		log.info("planillaFacade.getListaEfectuadoResumenBuscar() FIN");	
    	}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
    }
    public List<Enviomonto> getPlanillaDeEfectuada(Enviomonto o, Integer intMaxEnviado) throws BusinessException{
    	log.info("planillafacade.getPlanillaDeEfectuada");
    	List<Enviomonto> lista= null;    	
    	try{
    		lista = planillaService.getPlanillaEfectuada(o, intMaxEnviado);
    		for(Enviomonto enviomonto: lista){
    			log.info("planillafacade enviomonto="+enviomonto);
    		}
    	}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
    	return lista;
    }
    
    public List<Enviomonto> getListaPlanillaEfectuada(SucursalId pk,Integer intTipoSocio,Integer pIntModalidadCod, Integer pIntEstadoCod) throws BusinessException{
        log.info("planillafacade.getListaPlanillaEfectuada");
    	List<Enviomonto> lista= null;    	
    	try{
    		lista = boEnviomonto.getListaEnviomonto(pk, intTipoSocio, pIntModalidadCod, pIntEstadoCod);    		
    	}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
    	return lista;
    }
    
   
    /**
     * AUTOR Y FECHA CREACION: JCHAVEZ / 05.08.2013 
	 * Recupera lista de Envio Concepto por Expediende Credito Pk
     */
   	public List<Envioconcepto> getListaEnvioconceptoPorPkExpedienteCredito(ExpedienteId pId) throws BusinessException{
    	List<Envioconcepto> lista = null;
   		try{
   			lista = boEnvioconcepto.getListaEnvioconceptoPorPkExpedienteCredito(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
   	/**
   	 * AUTOR Y FECHA CREACION: JCHAVEZ / 05.08.2013 
	 * Recupera lista de Envio Monto por Envio Concepto Pk
   	 */
   	public List<Enviomonto> getListaPorEnvioConcepto(Envioconcepto pId) throws BusinessException{
    	List<Enviomonto> lista = null;
   		try{
   			lista = boEnviomonto.getListaPorEnvioConcepto(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    /** CREADO 06/08/2013 
     * SE OBTINIE EFECTUADO POR PK DE ENVIOMONTO Y PERIODO
     * **/
   	public List<Efectuado> getListaEfectuadoPorPkEnviomontoYPeriodo(EnviomontoId pId, Envioconcepto envioConcepto) throws BusinessException{
    	List<Efectuado> lista = null;
   		try{
   			lista = boEfectuado.getListaEfectuadoPorPkEnviomontoYPeriodo(pId,envioConcepto);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
   	/** CREADO 06/08/2013 
     * SE OBTINIE EFECTUADOCONCEPTO POR PK DE EFECTUADO Y EXPEDIENTE
     * **/
   	public List<EfectuadoConcepto> getListaPorEfectuadoYExpediente(EfectuadoId pId, Expediente expediente) throws BusinessException{
    	List<EfectuadoConcepto> lista = null;
   		try{
   			lista = boEfectuadoConceptoBO.getListaPorEfectuadoYExpediente(pId,expediente);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    /** CREADO 08/08/2013 
     * SE OBTINIE ENVIOCONCEPTO POR PK DE CUENTACONCEPTODETALLE
     * **/
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Envioconcepto> getListaEnvioconceptoPorCtaCptoDetYPer(CuentaConceptoDetalleId pId, Integer intPeriodo) throws BusinessException{
    	List<Envioconcepto> lista = null;
   		try{
   			lista = boEnvioconcepto.getListaEnvioconceptoPorCtaCptoDetYPer(pId,intPeriodo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    
    /** CREADO 19/08/2013 
     * SE OBTINIE EFECTUADO POR PK
     * **/
	@Override
	public Efectuado getEfectuadoPorPk(EfectuadoId pId) throws BusinessException {
    	Efectuado dto = null;
   		try{
   			dto = boEfectuado.getEfectuadoPorPk(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
	}
	
    public List<Efectuado> getListaEfectuadoXNiveCodigoModaliPeriodoTipoSocio(Integer intIdEmpresa,EstructuraId pId,
			   Integer intTipoModalidad,Integer intPeriodo, Integer intTipoSocio) throws BusinessException{
		List<Efectuado> lista = null;
		try{
			lista = planillaService.getListaEfectuadoXNiveCodigoModaliPeriodoTipoSocio(intIdEmpresa, pId, intTipoModalidad,
					intPeriodo, intTipoSocio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

public List<PlanillaAdministra> getPlanillaPorIdEstructuraYTipoSocioYPeriodoCAS(EstructuraId pk,
																		  Integer intTipoSocio,
																		  Integer intPeriodo) throws BusinessException{
	List<PlanillaAdministra> lista = null;
		try{
			lista = planillaService.getPlanillaPorIdEstructuraYTipoSocioYPeriodoCAS(pk,intTipoSocio,intPeriodo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}    
    /**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 09-09-2013 
	 * OBTENER LISTA ENVIOCONCEPTO POR CUENTA Y PERIODO
	 */
    public List<Envioconcepto> getListaEnvioconceptoPorCtaYPer(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException{
		List<Envioconcepto> lista = null;
		try{
			lista = boEnvioconcepto.getListaEnvioconceptoPorCtaYPer(intEmpresa,intCuenta,intPeriodo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}    
    /**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 09-09-2013 
	 * OBTENER EFECTUADORESUMEN POR ENTIDAD Y PERIODO
	 */
	public List<EfectuadoResumen> getListaPorEntidadyPeriodo(Integer pIntEmpresaPk, Integer pIntPeriodoplanilla, 
			 Integer pIntTiposocioCod, Integer pIntModalidadCod,
			 Integer pIntNivel, Integer pIntCodigo) throws BusinessException{
		List<EfectuadoResumen> lista = null;
		try{
			lista = boEfectuadoResumen.getListaPorEntidadyPeriodo(pIntEmpresaPk,pIntPeriodoplanilla,pIntTiposocioCod,pIntModalidadCod,pIntNivel,pIntCodigo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    /**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 09-09-2013 
	 * OBTENER COBROPLANILLAS POR PK EFECTUADORESUMEN
	 */
	public List<CobroPlanillas> getPorEfectuadoResumen(EfectuadoResumen efectuadoResumen) throws BusinessException{
		List<CobroPlanillas> lista = null;
		try{
			lista = boCobroPlanillas.getPorEfectuadoResumen(efectuadoResumen);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 10-09-2013 
	 * OBTENER LISTA ENVIOCONCEPTO POR CUENTA, DEL PERIODO INGRESADO EN ADELANTE (DISTINCT POR PERIODO)
	 */
    public List<Envioconcepto> getListaPorCuentaYPeriodo(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException{
		List<Envioconcepto> lista = null;
		try{
			lista = boEnvioconcepto.getListaPorCuentaYPeriodo(intEmpresa,intCuenta,intPeriodo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
	
		/**
	 * Se recupera el envio en base  a la empresa, periodo y cuenta.
	 */
	public Envioconcepto getEnvioConceptoPorEmpPerCta(Integer intEmpresa, Integer intPeriodo, Integer nroCta) throws BusinessException{
		Envioconcepto envio = null;
		try{
			envio = boEnvioconcepto.getEnvioConceptoPorEmpPerCta(intEmpresa, intPeriodo, nroCta);
		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return envio;
	}

	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 14-09-2013
     * OBTENER EFECTUADOCONCEPTO POR PK DE EFECTUADO Y (EXPEDIENTE O CUENTACONCEPTO) O TIPOCONCEPTOGRAL
	 */
   	public List<EfectuadoConcepto> getListaPorEfectuadoPKEnvioConcepto(EfectuadoId pid, Envioconcepto envioConcepto) throws BusinessException{
    	List<EfectuadoConcepto> lista = null;
   		try{
   			lista = boEfectuadoConceptoBO.getListaPorEfectuadoPKEnvioConcepto(pid,envioConcepto);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
   	/**
   	 * AUTOR Y FECHA CREACION: JCHAVEZ / 16-09-2013
     * OBTENER EFECTUADOCONCEPTO POR PK EFECTUADO
   	 * @param pid
   	 * @return
   	 * @throws BusinessException
   	 */
   	public List<EfectuadoConcepto> getListaPorEfectuado(EfectuadoId pid) throws BusinessException{
    	List<EfectuadoConcepto> lista = null;
   		try{
   			lista = boEfectuadoConceptoBO.getListaPorEfectuado(pid);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
	   
   	
    public List<Socio> getListaSocio(EstructuraId pk,Integer intTipoSocio) throws BusinessException{
		List<Socio> lista = null;
		try{
			lista = planillaService.getListaSocio(pk,intTipoSocio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioCAS(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio) throws BusinessException{
    	Integer intEscalar = null;
   		try{
   			
   			intEscalar = boEnvioResumen.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioCAS(intEmpresa,pk,intTipoSocio);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return intEscalar;
   	}
    
    public List<Enviomonto> getListaEnvioMontoPlanillaEfectuada(Enviomonto o,Integer intMaxEnviado) throws BusinessException
    {
    	List<Enviomonto> lista = null;    	
    	
    	try{
   			lista = boEnviomonto.getListaEnvioMontoPlanillaEfectuada(o, intMaxEnviado);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
    }
    
       
    public List<EfectuadoResumen> getPlanillaEfectuadaResumen(Enviomonto o, Integer periodo) throws BusinessException
    {
    	List<EfectuadoResumen> lista = null;
    	try{
   			lista = planillaService.getPlanillaEfectuadaResumen(o, periodo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
    }
    
    public void grabarPlanillaEfectuada(List<EfectuadoResumen> listaEfectuadoResumen,
    									Usuario pUsuario) throws BusinessException{   	
    	
    	try{
    		 efectuadoService.grabarPlanillaEfectuadaResumen(listaEfectuadoResumen,pUsuario);
		}catch(BusinessException e){			
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){			
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		
	}
    
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioM(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio, Integer intModalidad) throws BusinessException{
    	Integer intEscalar = null;
   		try{
   			
   			intEscalar = boEnvioResumen.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioM(intEmpresa,pk,intTipoSocio,intModalidad);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return intEscalar;
   	}
     
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Integer getOtraModalidad(Integer intSegundaModalidad, SocioEstructura o) throws BusinessException{
     	Integer intEscalar = null;
    		try{
    			
    			intEscalar = planillaService.getOtraModalidad(intSegundaModalidad, o);
    		}catch(BusinessException e){
    			throw e;
    		}catch(Exception e){
    			throw new BusinessException(e);
    		}
    		return intEscalar;
    	}
     
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
     public List<EfectuadoConceptoComp> getEmpezandoEfectuado(Enviomonto o,
    		 													Integer intMaxEnviado,
    		 													Integer intSegundaModalidad)throws BusinessException{
    	 List<EfectuadoConceptoComp> lista = null;
    	 try{
    		lista =  planillaService.getEmpezandoEfectuado(o, intMaxEnviado, intSegundaModalidad);
    	 }
    	 catch(BusinessException e){
 			throw e;
 		}catch(Exception e){
 			throw new BusinessException(e);
 		}
    	return lista;
     }
     
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
     public List<EfectuadoResumen> aEfectuar(List<EfectuadoConceptoComp> listaEfectuadoConceptoComp,Enviomonto eo,Integer intMaxEnviado) throws BusinessException
     {    	 
    	 List<EfectuadoResumen> lista = null;
    	 try{
    		lista =  planillaService.aEfectuar(listaEfectuadoConceptoComp,eo,intMaxEnviado);
    	 }
    	 catch(BusinessException e){
 			throw e;
 		}catch(Exception e){
 			throw new BusinessException(e);
 		}
    	return lista;
     }
     
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
     public List<EfectuadoConceptoComp> getCompletandoEfectuado(Enviomonto o,
    		 													Integer intMaxEnviado,
    		 													Integer intSegundaModalidad)throws BusinessException{
    	 List<EfectuadoConceptoComp> lista = null;
    	 try{
    		lista =  planillaService.getCompletandoEfectuado(o, intMaxEnviado, intSegundaModalidad);
    	 }
    	 catch(BusinessException e){
 			throw e;
 		}catch(Exception e){
 			throw new BusinessException(e);
 		}
    	return lista;
     }
     
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
     public List<EfectuadoResumen> aCompletarEfectuado(List<EfectuadoConceptoComp> listaEfectuadoConceptoComp,Enviomonto eo,Integer intMaxEnviado) throws BusinessException
     {    	 
    	 List<EfectuadoResumen> lista = null;
    	 try{
    		lista =  planillaService.aCompletarEfectuado(listaEfectuadoConceptoComp,eo,intMaxEnviado);
    	 }
    	 catch(BusinessException e){
 			throw e;
 		}catch(Exception e){
 			throw new BusinessException(e);
 		}
    	return lista;
     }
     
     
     /**
      * Recupera los EFECTUADOS en base a la Empresa, Cuenta y peridodo en cualquier estado.
      * @param intEmpresacuentaPk
      * @param intCuentaPk
      * @param intPeriodoPlanilla
      * @return
      * @throws BusinessException
      */
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
     public List<Efectuado> getListaPorEmpCtaPeriodo(Integer intEmpresacuentaPk, Integer intCuentaPk, Integer intPeriodoPlanilla) throws BusinessException
     {    	 
    	 List<Efectuado> lista = null;
    	 try{
    		lista =  boEfectuado.getListaPorEmpCtaPeriodo(intEmpresacuentaPk, intCuentaPk, intPeriodoPlanilla);
    	 }
    	 catch(BusinessException e){
 			throw e;
 		}catch(Exception e){
 			throw new BusinessException(e);
 		}
    	return lista;
     }
     
     
     /**
      * 
      * @param intEmpresa
      * @param intCuenta
      * @return
      * @throws BusinessException
      */
     public List<Envioconcepto> getListaEnvioMinimoPorEmpCtaYEstado(Integer intEmpresa, Integer intCuenta) throws BusinessException
     {    	 
    	 List<Envioconcepto> lista = null;
    	 try{
    		lista =  boEnvioconcepto.getListaEnvioMinimoPorEmpCtaYEstado(intEmpresa, intCuenta);
    	 }
    	 catch(BusinessException e){
 			throw e;
 		}catch(Exception e){
 			throw new BusinessException(e);
 		}
    	return lista;
     }
     
     
     /**
      * 
      * @param o
      * @return
      * @throws BusinessException
      */
     public List<Enviomonto> getListaXItemEnvioCtoGral(Enviomonto o) throws BusinessException
     {    	 
    	 List<Enviomonto> lista = null;
    	 try{
    		lista =  boEnviomonto.getListaXItemEnvioCtoGral(o);
    	 }
    	 catch(BusinessException e){
 			throw e;
 		}catch(Exception e){
 			throw new BusinessException(e);
 		}
    	return lista;
     }
    /**
     * jchavez 19.06.2014
     */
	public List<EfectuadoResumen> getListaEfectuadoResumenParaIngreso2(EstructuraComp estructuraComp, Usuario usuario) throws BusinessException{
    	List<EfectuadoResumen> lista = null;
   		try{
   			lista = efectuadoResumenService.getListaEfectuadoResumenParaIngreso2(estructuraComp, usuario);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
}

