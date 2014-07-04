package pe.com.tumi.credito.socio.estructura.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.bo.AdminPadronBO;
import pe.com.tumi.credito.socio.estructura.bo.ConvenioDetalleBO;
import pe.com.tumi.credito.socio.estructura.bo.DescuentoBO;
import pe.com.tumi.credito.socio.estructura.bo.EstructuraBO;
import pe.com.tumi.credito.socio.estructura.bo.PadronBO;
import pe.com.tumi.credito.socio.estructura.bo.SolicitudPagoBO;
import pe.com.tumi.credito.socio.estructura.bo.TercerosBO;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.credito.socio.estructura.bo.EstructuraDetalleBO;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.PadronId;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPago;
import pe.com.tumi.credito.socio.estructura.domain.Terceros;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.service.AdminPadronService;
import pe.com.tumi.credito.socio.estructura.service.EstructuraDetalleService;
import pe.com.tumi.credito.socio.estructura.service.EstructuraService;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;

@Stateless
public class EstructuraFacade extends TumiFacade implements EstructuraFacadeRemote, EstructuraFacadeLocal {

	protected  static Logger log = Logger.getLogger(EstructuraFacade.class);
	private EstructuraBO boEstructura = (EstructuraBO)TumiFactory.get(EstructuraBO.class);
	private EstructuraService estructuraService = (EstructuraService)TumiFactory.get(EstructuraService.class);
	private AdminPadronService adminPadronService = (AdminPadronService)TumiFactory.get(AdminPadronService.class);
	private PadronBO boPadron = (PadronBO)TumiFactory.get(PadronBO.class);
	private DescuentoBO boDescuento = (DescuentoBO)TumiFactory.get(DescuentoBO.class);
	private AdminPadronBO boAdminPadron = (AdminPadronBO)TumiFactory.get(AdminPadronBO.class);
	private SolicitudPagoBO boSolicitudPago = (SolicitudPagoBO)TumiFactory.get(SolicitudPagoBO.class);
	private EstructuraDetalleBO boEstructuraDetalle = (EstructuraDetalleBO)TumiFactory.get(EstructuraDetalleBO.class);
	private EstructuraDetalleService estructuraDetalleService = (EstructuraDetalleService)TumiFactory.get(EstructuraDetalleService.class);
	private ConvenioDetalleBO boConvenioDetalle = (ConvenioDetalleBO)TumiFactory.get(ConvenioDetalleBO.class);
	
	private TercerosBO boTerceros = (TercerosBO)TumiFactory.get(TercerosBO.class);
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Terceros> getListaFilaTercerosPorDNI(String strDocIdentidad) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getListaFilaTercerosPorDNI-----------------------------");
		List<Terceros> lista = null;
		try{
			lista = estructuraService.getListaFilaTercerosPorDNI(strDocIdentidad);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Terceros> getListaColumnaTercerosPorDNI(String strDocIdentidad) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getListaColumnaTercerosPorDNI-----------------------------");
		List<Terceros> lista = null;
		try{
			lista = estructuraService.getListaColumnaTercerosPorDNI(strDocIdentidad);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstructuraComp> getListaEstructuraComp(EstructuraComp o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getListaEstructura-----------------------------");
		List<EstructuraComp> lista = null;
		try{
			lista = estructuraService.getListaEstructuraComp(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstructuraComp> getListaFiltraEstructuraComp(EstructuraComp o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getListaEstructura-----------------------------");
		List<EstructuraComp> lista = null;
		try{
			lista = estructuraService.getListaFiltraEstructuraComp(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstructuraComp> getListaEstructuraCompConSucursal(EstructuraComp o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getListaEstructura-----------------------------");
		List<EstructuraComp> lista = null;
		try{
			lista = estructuraService.getListaEstructuraCompConSucursal(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Estructura grabarEstructuraYPersonaRol(Estructura o, PersonaRol rol) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.grabarEstructuraYPersonaRol-----------------------------");
		Estructura estructura = null;
		PersonaRol personaRol = null;
		try{
			estructura = estructuraService.grabarEstructura(o);
			
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			personaRol = personaFacade.grabarPersonaRol(rol);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return estructura;
	}
	
	public Estructura grabarEstructura(Estructura o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.grabarEstructura-----------------------------");
		Estructura estructura = null;
		try{
			estructura = estructuraService.grabarEstructura(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return estructura;
	}
	
	public Estructura modificarEstructura(Estructura o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.modificarEstructura-----------------------------");
		Estructura estructura = null;
		try{
			estructura = estructuraService.modificarEstructura(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return estructura;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Estructura getEstructuraPorPk(EstructuraId o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getEstructuraPorPk-----------------------------");
		Estructura estructura = null;
		try{
			estructura = estructuraService.getEstructuraPorPK(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estructura;
	}
	
	public Estructura eliminarEstructura(Estructura estruc) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.eliminarEstructura-----------------------------");
		Estructura estructura = null;
		PersonaRol personaRol = null;
		PersonaRolPK personaRolPk = null;
		
		try{
			estructura = estructuraService.eliminarEstructura(estruc.getId());
			
			personaRolPk = new PersonaRolPK();
			personaRolPk.setIntIdEmpresa(estruc.getIntPersEmpresaPk());
			personaRolPk.setIntIdPersona(estruc.getIntPersPersonaPk());
			personaRolPk.setIntParaRolPk(Constante.PARAM_T_TIPOCOMPROBANTE_ENTIDAD);
			personaRol = new PersonaRol();
			personaRol.setId(personaRolPk);
			personaRol.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			personaRol.setDtFechaEliminacion(new Date());
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			personaRol = personaFacade.modificarPersonaRolPorPerEmpYRol(personaRol);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return estructura;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Estructura> getListaEstructuraPorNivelYCodigoRel(Integer intNivel, Integer intCodigoRel)throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getListaEstructuraPorNivelYCodigoRel-----------------------------");
		List<Estructura> lista = null;
		try{
			lista = estructuraService.getListaEstructuraPorNivelYCodigoRel(intNivel, intCodigoRel);
		// CGD - 31-10-2013
		/*}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}*/
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Estructura> getListaEstructuraPorIdEmpresaYIdNivelYIdCasoIdSucursal(Integer intIdEmpresa,Integer intIdNivel,Integer intIdCaso, Integer intIdSucursal) throws BusinessException {
		List<Estructura> lista = null;
		try{
			lista = boEstructura.getListaEstructuraPorIdEmpresaYIdNivelYIdCasoIdSucursal(intIdEmpresa,intIdNivel,intIdCaso,intIdSucursal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Estructura> getListaEstructuraPorIdEmpresaYIdCodigoYIdNivelYIdCasoIdSucursal(Integer intIdEmpresa,Integer intIdCodigo,Integer intIdNivel,Integer intIdCaso, Integer intIdSucursal) throws BusinessException {
		List<Estructura> lista = null;
		try{
			lista = boEstructura.getListaEstructuraPorIdEmpresaYIdCodigoYIdNivelYIdCasoIdSucursal(intIdEmpresa,intIdCodigo,intIdNivel,intIdCaso,intIdSucursal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Estructura getEstructuraPorIdEmpresaYIdPersona(Integer intIdEmpresa,Integer intIdPersona) throws BusinessException {
		Estructura dto = null;
		try{
			dto = boEstructura.getEstructuraPorIdEmpresaYIdPersona(intIdEmpresa, intIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstructuraDetalle> getListaEstructuraDetallePorEstructuraPK(EstructuraId id) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getListaEstructuraDetallePorEstructuraPK-----------------------------");
		List<EstructuraDetalle> lista = null;
		try{
			lista = boEstructuraDetalle.getListaEstructuraDetallePorEstructuraPK(id);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstructuraDetalle> getListaEstructuraDetallePorIdEmpresaYIdNivelYIdSucursal(Integer intIdEmpresa,Integer intIdNivel,Integer intIdSucursal) throws BusinessException {
		List<EstructuraDetalle> lista = null;
		try{
			lista = boEstructuraDetalle.getListaEstructuraDetallePorIdEmpresaYIdNivelYIdSucursal(intIdEmpresa,intIdNivel,intIdSucursal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstructuraDetalle> getConveEstrucDetAdministra(EstructuraComp o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getConveEstrucDetAdministra-----------------------------");
		List<EstructuraDetalle> listEstrucDet = null;
		try{
			listEstrucDet = estructuraService.getConveEstrucDetAdministra(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listEstrucDet;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstructuraDetalle> getConveEstrucDetPlanilla(EstructuraComp o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getConveEstrucDetAdministra-----------------------------");
		List<EstructuraDetalle> listEstrucDet = null;
		try{
			listEstrucDet = estructuraService.getConveEstrucDetPlanilla(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listEstrucDet;
	}
	
	public List<EstructuraComp> getListaEstructuraCompPorTipoConvenio(Integer intIdNivel, Integer intTipoConvenio, String strRazonSocial, String strNroRuc)throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getListaEstructuraCompPorTipoConvenio-----------------------------");
		List<EstructuraComp> lista = null;
		try{
			lista = estructuraService.getListaCompDetallePorTipoConvenio(intIdNivel, intTipoConvenio, strRazonSocial, strNroRuc);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public EstructuraDetalle getEstructuraDetallePorPk(EstructuraDetalleId o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getEstructuraPorPk-----------------------------");
		EstructuraDetalle estructuraDetalle = null;
		try{
			estructuraDetalle = boEstructuraDetalle.getEstructuraDetallePorPK(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estructuraDetalle;
	}

	@Override
	public Padron getPadronPorPK(PadronId o)throws BusinessException {
		Padron padron = null;
		try {
			padron = boPadron.getPadronPorPK(o);
		} catch (BusinessException e) {
			context.setRollbackOnly();
			throw e;
		} catch (Exception e) {
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return padron;
	}

	@Override
	public AdminPadron grabarAdminPadron(AdminPadron o)
			throws BusinessException {
		
		log.info("-----------------------Debugging EstructuraFacade.grabarAdminPadron-----------------------------");
		AdminPadron adminPadron = null;
		try{
			adminPadron = adminPadronService.grabarAdminPadron(o);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return adminPadron;
		
	}

	@Override
	public Descuento grabarDescuentoTerceros(Descuento o)
			throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.grabarDescuentoTerceros-----------------------------");
		Descuento descuento = null;
		try{
			descuento = boDescuento.grabarDescuento(o);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return descuento;
	}

	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean grabarDescuentos(AdminPadron o, String s, List<String>l)
			throws BusinessException {
		boolean exito = false;
		log.info("-----------------------Debugging EstructuraFacade.grabarDescuentos-----------------------------");
		try{
			exito = adminPadronService.grabarAdminPadronyDescuentos(o, s,l);			
		}catch(BusinessException e){
			//context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			//context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return exito;
	}

	@Override
	public List<AdminPadron> getAdminPadronBusqueda(AdminPadron o)
			throws BusinessException {
		//log.info("-----------------------Debugging EstructuraFacade.getAdminPadronBusqueda-----------------------------");
		List<AdminPadron> lista = null;
		try{
			log.info("bus  :"+o);
			lista = adminPadronService.getAdminPadronBusqueda(o);
		}catch(BusinessException e){
			log.info(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.info(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return lista;
	}

	@Override
	public boolean grabarSolicitudPago(SolicitudPago s,	List<AdminPadron> l) throws BusinessException {
		boolean exito = false;
		log.info("-----------------------Debugging EstructuraFacade.grabarSolicitudPago-----------------------------");
		try{
			exito = adminPadronService.grabarSolicitudPago(s, l);			
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return exito;
	}

	@Override
	public List<AdminPadron> getAdminPadronSinSolicitud(AdminPadron adminPadron) throws BusinessException {
		List<AdminPadron> lista = null;
		try{
			lista = adminPadronService.getAdminPadronSinSolicitud(adminPadron);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Estructura> getListaEstructuraPorNivel(Integer nivel) throws BusinessException {		
		//List<Estructura> listatemp;
		List<Estructura> listaEstructuras = new ArrayList<Estructura>();		
		try{
			if(nivel.equals(Constante.PARAM_T_NIVELENTIDAD_TODOS)){
				listaEstructuras = getListaEstructuraPorNivelYCodigo(null,null);
			}else{
				listaEstructuras = getListaEstructuraPorNivelYCodigo(nivel,null);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaEstructuras;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AdminPadron> getSolicitudBusqueda(AdminPadron o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getAdminPadronBusqueda-----------------------------");
		log.info("busqueda:"+o.toString());
		List<AdminPadron> lista = null;
		try{			
			lista = adminPadronService.getSolicitudBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	@Override
	public AdminPadron modificarAdminPadron(AdminPadron o) throws BusinessException {
		AdminPadron adminPadron = null;
		try{
			log.info("a modificar:"+o);
			adminPadron = boAdminPadron.modificarAdminPadron(o);
			log.info("modificado: "+o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return adminPadron;		
	}

	@Override
	public SolicitudPago modificarSolicitud(SolicitudPago o) throws BusinessException {
		SolicitudPago solicitudPago = null;
		try{
			log.info("a modificar:"+o);
			o = boSolicitudPago.modificar(o);
			log.info("modificado: "+o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return solicitudPago;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SolicitudPago getSolicitudPorPk(Integer o) throws BusinessException {
		SolicitudPago solicitudPago = null;
		try{
			solicitudPago = boSolicitudPago.getPorPK(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return solicitudPago;
	}

	@Override
	public List<AdminPadron> actualizarListaAdminPadron(List<AdminPadron> l)
			throws BusinessException {
		List<AdminPadron> lista = null;
		try{
			lista = adminPadronService.actualizarListaAdminPadron(l);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Padron> getPadronBusqueda(PadronId o) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getPadronBusqueda-----------------------------");
		List<Padron> lista = null;
		try{
			lista = boPadron.getPadronBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean grabarPadrones(AdminPadron o, String s, List<String>l)
			throws BusinessException {
		// TODO Auto-generated method stub
		boolean exito = false;
		try{
			exito = adminPadronService.grabarAdminPadronyPadrones(o, s, l);			
		}catch(BusinessException e){
			//context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			//context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return exito;
	}
	
	public SocioComp getSocioNatuPorLibElectoral(String strLibEle) throws BusinessException{
		SocioComp socioComp = null;
		try{
			socioComp = (SocioComp) adminPadronService.getSocioNatuPorLibElectoral(strLibEle);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socioComp;
	}
	
	public List<ConvenioEstructuraDetalleComp> getListaConvenioEstructuraDetPorEstructuraDet(ConvenioEstructuraDetalleComp pId) throws BusinessException {
		List<ConvenioEstructuraDetalleComp> lista = null;
		try{
			lista = estructuraDetalleService.getConvenioDetallePorEstructuraDet(pId);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Estructura> getListaEstructuraPorNivelYCodigo(Integer intNivel, Integer intCodigoRel)throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getListaEstructuraPorNivelYCodigoRel-----------------------------");
		List<Estructura> lista = null;
		try{
			lista = estructuraService.getListaEstructuraPorNivelYCodigo(intNivel, intCodigoRel);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public EstructuraDetalle getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(EstructuraId o,Integer intCaso,Integer intParaTipoSocioCod,Integer intParaModalidadCod)throws BusinessException{
		EstructuraDetalle domain = null;
		try{
			domain = boEstructuraDetalle.getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(o,intCaso,intParaTipoSocioCod,intParaModalidadCod);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public EstructuraDetalle getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(EstructuraDetalleId o,Integer intParaTipoSocioCod,Integer intParaModalidadCod)throws BusinessException{
		EstructuraDetalle domain = null;
		try{
			domain = boEstructuraDetalle.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(o, intParaTipoSocioCod, intParaModalidadCod);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(EstructuraDetalleId o,Integer intParaTipoSocioCod,Integer intParaModalidadCod)throws BusinessException{
		List<EstructuraDetalle> lista = null;
		try{
			lista = boEstructuraDetalle.getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(o, intParaTipoSocioCod, intParaModalidadCod);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public ConvenioEstructuraDetalle getConvenioEstructuraDetallePorPkEstructuraDetalle(EstructuraDetalleId o)throws BusinessException{
		ConvenioEstructuraDetalle domain = null;
		try{
			domain = boConvenioDetalle.getConvenioDetallePorPKEstructuraDetalle(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return domain;
	}

	public List<EstructuraDetalle> getListaEstructuraDetallePorCodExterno(String strCodExterno)throws BusinessException{
		List<EstructuraDetalle> lista = null;
		try{
			lista = boEstructuraDetalle.getListaEstructuraDetallePorCodExterno(strCodExterno);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public EstructuraDetalle getEstructuraDetallePorCodSocioYTipoSocYModalidad(Integer intCodSocio, Integer intParaTipoSocioCod,Integer intParaModalidadCod)throws BusinessException{
		EstructuraDetalle domain = null;
		try{
			domain = boEstructuraDetalle.getEstructuraDetallePorCodSocioYTipoSocYMod(intCodSocio, intParaTipoSocioCod, intParaModalidadCod);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<ConvenioEstructuraDetalleComp> getListaConvenioEstructuraDetallePorEstructuraDetCompleto(EstructuraDetalleId o)throws BusinessException{
		List<ConvenioEstructuraDetalleComp> lista = null;
		try{
			lista = boConvenioDetalle.getListaConvenioEstructuraDetallePorEstructuraDetCompleto(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Estructura> getListaEstructuraPorIdEmpresaYIdCasoIdSucursal(Integer intIdEmpresa,Integer intIdCaso, Integer intIdSucursal) throws BusinessException {
		List<Estructura> lista = null;
		try{
			lista = boEstructura.getListaEstructuraPorIdEmpresaYIdCasoIdSucursal(intIdEmpresa,intIdCaso,intIdSucursal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * 
	 */
	public List<AdminPadron> getTipSocioModPeriodoMes(AdminPadron o)
			throws BusinessException {
		//log.info("-----------------------Debugging EstructuraFacade.getAdminPadronBusqueda-----------------------------");
		List<AdminPadron> lista = null;
		try{
			log.info("bus  :"+o);
			lista = boAdminPadron.getTipSocioModPeriodoMes(o);
		}catch(BusinessException e){
			log.info(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.info(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return lista;
	}
	

	public Padron getPadronSOLOPorLibElectoral(String strLibEle, Integer item) throws BusinessException{
		Padron adron = null;
		try{
			adron = (Padron) boPadron.getPadronSOLOPorLibElectoral(strLibEle, item);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adron;

	}
	

	public Terceros getPorItemDNI(Terceros o) throws BusinessException
	{
		Terceros tercer = null;


		try
		{
			tercer = (Terceros)boTerceros.getPorItemDNI(o);
		}
		catch(BusinessException e)
		{
			throw e;
		}catch(Exception e)
		{
			throw new BusinessException(e);
		}
		return tercer;
	}
	
	
	/**
	 * Recupera el mas reciente registro en base a Administra Padron.
	 * Soporta nulos.
	 * @param adminPadronFiltro
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public AdminPadron getMaximoAdminPadronPorAdminPadron ( AdminPadron adminPadronFiltro) throws BusinessException {
		AdminPadron domain = null;
		 /* Campos utilizados, todos pueden ser nulos:
		  * CSOC_PERIODO_N , CSOC_MES_N , 
		  * CSOC_NIVEL_N , CSOC_CODIGO_N , 
		  * PARA_TIPOARCHIVOPADRON_N_COD,
		  * PARA_MODALIDAD_N_COD , PARA_TIPOSOCIO_N_COD ,  
		  * PARA_ESTADO_N_COD */
		try {
			domain = boAdminPadron.getMaximoAdminPadronPorAdminPadron(adminPadronFiltro);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	/**
	 * 
	 * @param descuentoId
	 * @return
	 * @throws BusinessException
	 */
	/*@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Descuento getDescuentoPorPK ( DescuentoId descuentoId) throws BusinessException {
		Descuento domain = null;

		try {
			domain = boDescuento.getDescuentoPorPK(descuentoId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}*/
	
	//public List<Descuento> getListaPorAdminPadron(AdminPadron administraPadron, String strDni) throws BusinessException
	/**
	 * Recupera Descuentos Terceros por AdminPadron y strDni
	 */
	public List<Descuento> getListaPorAdminPadron ( AdminPadron administraPadron, String strDni) throws BusinessException {
		 List<Descuento> lista = null;

		try {
			lista = boDescuento.getListaPorAdminPadron(administraPadron,strDni);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	public Estructura getEstructuraPorPK(EstructuraId pPK) throws BusinessException {
		log.info("-----------------------Debugging EstructuraFacade.getEstructuraPorPK-----------------------------");
		Estructura estructura = null;
		try{
			estructura = boEstructura.getEstructuraPorPK(pPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estructura;
	}
	
	public EstructuraDetalle getEstructuraDetallePorSucuSubsucuYCodigo(SocioEstructura o) throws BusinessException {
		EstructuraDetalle domain = null;
		try {
			domain =  boEstructuraDetalle.getEstructuraDetallePorSucuSubsucuYCodigo(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	//autor rVillarreal
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Estructura> getListaEstructuraPorNivelCodigo(Integer nivel, Integer codigo) throws BusinessException {		
		//List<Estructura> listatemp;
		List<Estructura> listaEstructuras = new ArrayList<Estructura>();		
		try{
			if(nivel.equals(Constante.PARAM_T_NIVELENTIDAD_TODOS)){
				listaEstructuras = getListaEstructuraPorNivelYCodigo(null,null);
			}else{
				listaEstructuras = getListaEstructuraPorNivelYCodigo(nivel,codigo);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaEstructuras;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetalleIngresos(Integer intIdSucursal, Integer intIdSubSucursal) throws BusinessException{
		List<EstructuraDetalle> lista = null;
		try {
			lista = estructuraService.getListaEstructuraDetalleIngresos(intIdSucursal,intIdSubSucursal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	
}