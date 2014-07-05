package pe.com.tumi.seguridad.empresa.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.composite.AreaComp;
import pe.com.tumi.empresa.domain.composite.SucursalComp;
import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.empresa.domain.composite.ZonalComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.seguridad.empresa.bo.SubSucursalBO;
import pe.com.tumi.seguridad.empresa.bo.SucursalBO;
import pe.com.tumi.seguridad.empresa.service.AreaService;
import pe.com.tumi.seguridad.empresa.service.EmpresaService;
import pe.com.tumi.seguridad.empresa.service.SucursalService;
import pe.com.tumi.seguridad.empresa.service.ZonalService;
import pe.com.tumi.seguridad.login.bo.EmpresaUsuarioBO;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;

@Stateless
public class EmpresaFacade extends TumiFacade implements EmpresaFacadeRemote, EmpresaFacadeLocal {
    
	protected  static Logger log = Logger.getLogger(EmpresaFacade.class);
	private EmpresaUsuarioBO boEmpresaUsuario = (EmpresaUsuarioBO)TumiFactory.get(EmpresaUsuarioBO.class);
	private EmpresaService empresaService = (EmpresaService)TumiFactory.get(EmpresaService.class);
	private SucursalService sucursalService = (SucursalService)TumiFactory.get(SucursalService.class);
	private AreaService areaService = (AreaService)TumiFactory.get(AreaService.class);
	private SucursalBO boSucursal = (SucursalBO)TumiFactory.get(SucursalBO.class);
	private SubSucursalBO boSubsucursal = (SubSucursalBO)TumiFactory.get(SubSucursalBO.class);
	private ZonalService serviceZonal = (ZonalService)TumiFactory.get(ZonalService.class);
	
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Empresa> getListaEmpresa(Empresa o) throws BusinessException{
    	//log.info("-----------------------Debugging EmpresaFacade.getListaEmpresa-----------------------------");
		List<Empresa> lista = null;
		try{
			lista = empresaService.getListaEmpresa(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EmpresaUsuario getEmpresaUsuarioPorPk(EmpresaUsuarioId pPk) throws BusinessException{
		EmpresaUsuario dto = null;
		try{
			dto = boEmpresaUsuario.getEmpresaUsuarioPorPk(pPk);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmpresaUsuario> getListaEmpresaUsuarioPorIdPersona(Integer pPk) throws BusinessException{
		List<EmpresaUsuario> lista = null;
		try{
			lista = boEmpresaUsuario.getListaEmpresaUsuarioPorIdPersona(pPk);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalPorPkEmpresa(Integer pIntPK) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = sucursalService.getListaSucursalPorPkEmpresa(pIntPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalSinZonalPorPkEmpresa(Integer pIntPK) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = sucursalService.getListaSucursalSinZonalPorPkEmpresa(pIntPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Sucursal getSucursalPorPK(Sucursal o) throws BusinessException{
    	log.info("-----------------------Debugging EmpresaFacade.getSucursalPorPK-----------------------------");
    	Sucursal suc = null;
		try{
			suc = sucursalService.getSucursalPorPk(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return suc;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Subsucursal getSubSucursalPorPk(SubSucursalPK o) throws BusinessException {
		//log.info("-----------------------Debugging AreaService.eliminarArea-----------------------------");
		Subsucursal subsucursal = null;
		try{
			subsucursal = boSubsucursal.getSubSucursalPorPK(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return subsucursal;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Sucursal getPorPkYIdTipoSucursal(Integer idSucursal, Integer idTipoSucursal) throws BusinessException{
    	//log.info("-----------------------Debugging EmpresaFacade.getSucursalPorIdTipoSucursal-----------------------------");
    	Sucursal suc = null;
		try{
			suc = boSucursal.getPorPkYIdTipoSucursal(idSucursal,idTipoSucursal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return suc;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Sucursal getSucursalPorIdSucursal(Integer pId) throws BusinessException{
    	Sucursal sucursal = null;
		try{
			sucursal = sucursalService.getSucursalPorIdSucursal(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return sucursal;
	}    

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Sucursal getSucursalPorIdPersona(Integer pId) throws BusinessException{
    	Sucursal sucursal = null;
		try{
			sucursal = boSucursal.getSucursalPorIdPersona(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return sucursal;
	} 
    
    public Sucursal grabarSucursal(Sucursal o) throws BusinessException {
    	Sucursal suc = null;
		try{
			suc = sucursalService.grabarSucursal(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return suc;
	}
    
    public Sucursal modificarSucursal(Sucursal o) throws BusinessException {
    	Sucursal suc = null;
		try{
			suc = sucursalService.modificarSucursal(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return suc;
	}
    
    public List<SucursalComp> getListaSucursalCompDeBusquedaSucursal(Sucursal o) throws BusinessException{
		List<SucursalComp> lista = null;
		try{
			lista = sucursalService.getListaSucursalCompDeBusquedaSucursal(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public Sucursal eliminarSucursal(Sucursal o) throws BusinessException {
		//log.info("-----------------------Debugging EmpresaFacade.eliminarSucursal-----------------------------");
		Sucursal sucursal = null;
		try{
			sucursal = sucursalService.eliminarSucursal(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return sucursal;
	}

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ZonalComp> getListaZonalCompDeBusquedaZonal(Zonal o) throws BusinessException{
		List<ZonalComp> lista = null;
		try{
			lista = empresaService.getListaZonalCompDeBusquedaZonal(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Integer getCantidadSucursalPorPkZonal(Integer pkZonal) throws BusinessException{
    	Integer escalar = null;
		try{
			escalar = boSucursal.getCantidadSucursalPorPkZonal(pkZonal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return escalar;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalPorPkZonal(Integer pIdZonal) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = sucursalService.getListaSucursalPorPkZonal(pIdZonal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalZonalPorPkEmpresa(Integer pId) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = sucursalService.getListaSucursalZonalPorPkEmpresa(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalPorPkEmpresaUsuario(EmpresaUsuarioId pPk) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = boSucursal.getListaSucursalPorPkEmpresaUsuario(pPk);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalPorPkEmpresaUsuarioYEstado(EmpresaUsuarioId pPk,Integer intEstado) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = boSucursal.getListaSucursalPorPkEmpresaUsuarioYEstado(pPk,intEstado);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalPorEmpresaYTipoSucursal(Integer pIntPK,Integer pTipo) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = boSucursal.getListaSucursalPorEmpresaYTipoSucursal(pIntPK, pTipo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalPorEmpresaYTodoTipoSucursal(Integer pIntPK) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = boSucursal.getListaSucursalPorEmpresaYTodoTipoSucursal(pIntPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalZonalPorPkEmpresaYTipo(Integer pIntPK,Integer pIntTipo) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = sucursalService.getListaSucursalZonalPorPkEmpresaYTipo(pIntPK, pIntTipo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalZonalPorPkEmpresaYTipoDeAne(Integer pIntPK,Integer pIntTipo) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = sucursalService.getListaSucursalZonalPorPkEmpresaYTipoDeAne(pIntPK, pIntTipo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalZonalPorPkEmpresaYTipoDeLib(Integer pIntPK,Integer pIntTipo) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = sucursalService.getListaSucursalZonalPorPkEmpresaYTipoDeLib(pIntPK, pIntTipo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalZonalPorPkEmpresaYIdZonalYTipo(Integer intIdEmpresa,Integer intIdZonal,Integer pIntTipo) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = sucursalService.getListaSucursalZonaPorPkEmpresaYIdZonalYTipo(intIdEmpresa,intIdZonal,pIntTipo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalZonalPorPkEmpresaYIdZonalYTipoDeAne(Integer intIdEmpresa,Integer intIdZonal,Integer pIntTipo) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = sucursalService.getListaSucursalZonalPorPkEmpresaYIdZonalYTipoDeAne(intIdEmpresa, intIdZonal, pIntTipo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sucursal> getListaSucursalZonalPorPkEmpresaYIdZonalYTipoDeLib(Integer pIntPK,Integer intIdZonal,Integer pIntTipo) throws BusinessException{
    	List<Sucursal> lista = null;
		try{
			lista = sucursalService.getListaSucursalZonalPorPkEmpresaYIdZonalYTipoDeLib(pIntPK,intIdZonal,pIntTipo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public Zonal grabarZonalYListaSucursal(Zonal zonal,List<Sucursal> listaSucursal) throws BusinessException {
    	Zonal lZonal = null;
		try{
			lZonal = empresaService.grabarZonalYListaSucursal(zonal,listaSucursal);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lZonal;
	}
    
    public Zonal modificarZonalYListaSucursal(Zonal zonal,List<Sucursal> listaSucursal) throws BusinessException {
    	Zonal lZonal = null;
		try{
			lZonal = empresaService.modificarZonalYListaSucursal(zonal,listaSucursal);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lZonal;
	}
    
    public Zonal eliminarZonalPorIdZonal(Integer pIntIdZonal) throws BusinessException {
    	Zonal lZonal = null;
		try{
			lZonal = empresaService.eliminarZonalPorIdZonal(pIntIdZonal);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lZonal;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Zonal getZonalSucursalPorIdZonal(Integer pId) throws BusinessException{
		Zonal zonal = null;
		try{
			zonal = serviceZonal.getZonalSucursalPorIdZonal(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return zonal;
	}    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Subsucursal getSubSucursalPorIdSubSucursal(Integer pId) throws BusinessException{
		Subsucursal subsucursal = null;
		try{
			subsucursal = boSubsucursal.getSubSucursalPorIdSubSucursal(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return subsucursal;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Subsucursal> getListaSubSucursalPorIdSucursal(Integer pId) throws BusinessException{
		List<Subsucursal> subsucursal = null;
		try{
			subsucursal = boSubsucursal.getListaSubSucursalPorIdSucursal(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return subsucursal;
	}    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Subsucursal> getListaSubSucursalPorIdSucursalYestado(Integer pId,Integer intEstado) throws BusinessException{
		List<Subsucursal> subsucursal = null;
		try{
			subsucursal = boSubsucursal.getListaSubSucursalPorIdSucursalYestado(pId,intEstado);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return subsucursal;
	} 
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Subsucursal> getListaSubSucursalPorPkEmpresaUsuarioYIdSucursalYEstado(EmpresaUsuarioId pId,Integer intIdSucursal,Integer intEstado) throws BusinessException{
    	List<Subsucursal> subsucursal = null;
		try{
			subsucursal = boSubsucursal.getListaSubSucursalPorPkEmpresaUsuarioYIdSucursalYEstado(pId,intIdSucursal,intEstado);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return subsucursal;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Natural> getListaNaturalDeUsuarioPorIdEmpresa(Integer pIdEmpresa) throws BusinessException{
    	List<Natural> lista = null;
		try{
			lista = empresaService.getListaNaturalDeUsuarioPorIdEmpresa(pIdEmpresa);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	} 
    
    public Area grabarArea(Area o) throws BusinessException {
    	Area area = null;
		try{
			area = areaService.grabarArea(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return area;
	}
    
    public Area modificarArea(Area o) throws BusinessException {
    	Area area = null;
		try{
			area = areaService.modificarArea(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return area;
	}
    
    public List<AreaComp> getListaArea(Area o) throws BusinessException{
		//log.info("-----------------------Debugging EmpresaFacade.getListaArea-----------------------------");
		List<AreaComp> lista = null;
		try{
			lista = areaService.getListaArea(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Area getAreaPorPK(Area o) throws BusinessException{
    	//log.info("-----------------------Debugging EmpresaFacade.getAreaPorPK-----------------------------");
		Area area = null;
		try{
			area = areaService.getAreaPorPK(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return area;
	}
    
	public Area eliminarArea(Area o) throws BusinessException {
		log.info("-----------------------Debugging EmpresaFacade.eliminarArea-----------------------------");
		Area area = null;
		try{
			area = areaService.eliminarArea(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return area;
	}
	
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Area> getListaAreaPorSucursal(Sucursal o) throws BusinessException{
    	List<Area> lista = null;
		try{
			lista = areaService.getListaAreaPorSucursal(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean validarTotalSucursal(Integer intTipoSucursalValidar, Integer intTotalSucursalReferencia) throws BusinessException{
    	boolean exito = Boolean.FALSE;
		try{
			if(intTipoSucursalValidar.equals(Constante.PARAM_T_TIPOSUCURSAL_AGENCIA)
			&& intTotalSucursalReferencia.equals(Constante.PARAM_T_TOTALESSUCURSALES_AGENCIAS)){
				exito = Boolean.TRUE;
				
			}else if(intTipoSucursalValidar.equals(Constante.PARAM_T_TIPOSUCURSAL_FILIAL)
			&& intTotalSucursalReferencia.equals(Constante.PARAM_T_TOTALESSUCURSALES_FILIALES)){
				exito = Boolean.TRUE;
				
			}else if(intTipoSucursalValidar.equals(Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL)
			&& intTotalSucursalReferencia.equals(Constante.PARAM_T_TOTALESSUCURSALES_SEDE)){
				exito = Boolean.TRUE;
				
			}else if(intTipoSucursalValidar.equals(Constante.PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL)
			&& intTotalSucursalReferencia.equals(Constante.PARAM_T_TOTALESSUCURSALES_OFICINAPRINCIPAL)){
				exito = Boolean.TRUE;
					
			}else if(intTotalSucursalReferencia.equals(Constante.PARAM_T_TOTALESSUCURSALES_SUCURSALES)){
				exito = Boolean.TRUE;
			}		
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return exito;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Sucursal getSucursalPorId(Integer intIdSucursal) throws BusinessException{
    	Sucursal suc = null;
		try{
			suc = sucursalService.getSucursalPorPk(intIdSucursal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return suc;
	} 

    
    
}