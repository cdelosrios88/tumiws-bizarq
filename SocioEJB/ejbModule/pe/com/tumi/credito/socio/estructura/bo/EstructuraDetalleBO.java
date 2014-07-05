package pe.com.tumi.credito.socio.estructura.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.dao.EstructuraDetalleDao;
import pe.com.tumi.credito.socio.estructura.dao.impl.EstructuraDetalleDaoIbatis;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class EstructuraDetalleBO {
	
	protected  static Logger log = Logger.getLogger(EstructuraDetalleBO.class);
	private EstructuraDetalleDao dao = (EstructuraDetalleDao)TumiFactory.get(EstructuraDetalleDaoIbatis.class);
	
	public EstructuraDetalle grabarEstructuraDetalle(EstructuraDetalle o) throws BusinessException{
		EstructuraDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstructuraDetalle modificarEstructuraDetalle(EstructuraDetalle o) throws BusinessException{
		EstructuraDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstructuraDetalle getEstructuraDetallePorPK(EstructuraDetalleId pPK) throws BusinessException{
		EstructuraDetalle domain = null;
		List<EstructuraDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intNivel", pPK.getIntNivel());
			mapa.put("intCodigo", pPK.getIntCodigo());
			mapa.put("intCaso", pPK.getIntCaso());
			mapa.put("intItemCaso", pPK.getIntItemCaso());
			lista = dao.getListaEstructuraDetallePorPK(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}

	public List<EstructuraDetalle> getListaEstructuraDetallePorEstructuraPK(EstructuraId id) throws BusinessException{
		log.info("-----------------------Debugging EstructuraDetalleBO.getListaEstructuraDetalle-----------------------------");
		List<EstructuraDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntCodigo", id.getIntCodigo());
			mapa.put("pIntNivel", id.getIntNivel());
			lista = dao.getListaEstructuraDetalle(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetalleBusqueda(EstructuraComp o) throws BusinessException{
		log.info("-----------------------Debugging EstructuraDetalleBO.getListaEstructuraDetalleBusqueda-----------------------------");
		List<EstructuraDetalle> lista = null;
		
		try{
			log.info("pIntNivel: " + o.getEstructuraDetalle().getId().getIntNivel());
			log.info("pIntCodigo: " + o.getEstructuraDetalle().getId().getIntCodigo());
			log.info("pIntCaso: " + o.getEstructuraDetalle().getId().getIntCaso());
			log.info("pIntItemCaso: " + o.getEstructuraDetalle().getId().getIntItemCaso());
			log.info("pIntParaTipoSocioCod: " + o.getEstructuraDetalle().getIntParaTipoSocioCod());
			log.info("pIntParaModalidadCod: " + o.getEstructuraDetalle().getIntParaModalidadCod());
			log.info("pIntSeguSucursalPk: " + o.getEstructuraDetalle().getIntSeguSucursalPk());
			log.info("pIntSeguSubSucursalPk: " + o.getEstructuraDetalle().getIntSeguSubSucursalPk());
			log.info("pIntDiaEnviado: " + o.getEstructuraDetalle().getIntDiaEnviado());
			log.info("pIntSaltoEnviado: " + o.getEstructuraDetalle().getIntSaltoEnviado());
			log.info("pIntDiaEfectuado: " + o.getEstructuraDetalle().getIntDiaEfectuado());
			log.info("pIntSaltoEfectuado: " + o.getEstructuraDetalle().getIntSaltoEfectuado());
			log.info("pStrCodigoExterno: " + o.getEstructuraDetalle().getStrCodigoExterno());
			log.info("pIntDiaCheque: " + o.getEstructuraDetalle().getIntDiaCheque());
			log.info("pIntSaltoCheque: " + o.getEstructuraDetalle().getIntSaltoCheque());
			log.info("pIntPersEmpresaPk: " + o.getEstructuraDetalle().getIntPersEmpresaPk());
			log.info("pIntPersPersonaUsuarioPk: " + o.getEstructuraDetalle().getIntPersPersonaUsuarioPk());
			log.info("pIntParaEstadoCod: " + o.getEstructuraDetalle().getIntParaEstadoCod());
			log.info("pDtFechaEliminacion: " + o.getEstructuraDetalle().getDtFechaEliminacion());
			log.info("pIntFechaEnviadoDesde: " + o.getIntFechaEnviadoDesde());
			log.info("pIntFechaEnviadoHasta: " + o.getIntFechaEnviadoHasta());
			log.info("pIntFechaEfectuadoDesde: " + o.getIntFechaEfectuadoDesde());
			log.info("pIntFechaEfectuadoHasta: " + o.getIntFechaEfectuadoHasta());
			log.info("pIntFechaChequeDesde: " + o.getIntFechaChequeDesde());
			log.info("pIntFechaChequeHasta: " + o.getIntFechaChequeHasta());
			
			HashMap mapa = new HashMap();
			mapa.put("pIntCodigo", o.getEstructuraDetalle().getId().getIntCodigo());
			mapa.put("pIntNivel", o.getEstructuraDetalle().getId().getIntNivel());
			mapa.put("pIntCaso", o.getEstructuraDetalle().getId().getIntCaso());
			mapa.put("pIntItemCaso", o.getEstructuraDetalle().getId().getIntItemCaso());
			mapa.put("pIntParaTipoSocioCod", o.getEstructuraDetalle().getIntParaTipoSocioCod());
			mapa.put("pIntParaModalidadCod", o.getEstructuraDetalle().getIntParaModalidadCod());
			mapa.put("pIntSeguSucursalPk", o.getEstructuraDetalle().getIntSeguSucursalPk());
			mapa.put("pIntSeguSubSucursalPk", o.getEstructuraDetalle().getIntSeguSubSucursalPk());
			mapa.put("pIntDiaEnviado", o.getEstructuraDetalle().getIntDiaEnviado());
			mapa.put("pIntSaltoEnviado", o.getEstructuraDetalle().getIntSaltoEnviado());
			mapa.put("pIntDiaEfectuado", o.getEstructuraDetalle().getIntDiaEfectuado());
			mapa.put("pIntSaltoEfectuado", o.getEstructuraDetalle().getIntSaltoEfectuado());
			mapa.put("pStrCodigoExterno", o.getEstructuraDetalle().getStrCodigoExterno());
			mapa.put("pIntDiaCheque", o.getEstructuraDetalle().getIntDiaCheque());
			mapa.put("pIntSaltoCheque", o.getEstructuraDetalle().getIntSaltoCheque());
			mapa.put("pIntPersEmpresaPk", o.getEstructuraDetalle().getIntPersEmpresaPk());
			mapa.put("pIntPersPersonaUsuarioPk", o.getEstructuraDetalle().getIntPersPersonaUsuarioPk());
			mapa.put("pIntParaEstadoCod", o.getEstructuraDetalle().getIntParaEstadoCod());
			mapa.put("pDtFechaEliminacion", o.getEstructuraDetalle().getDtFechaEliminacion());
			mapa.put("pIntFechaEnviadoDesde", o.getIntFechaEnviadoDesde());
			mapa.put("pIntFechaEnviadoHasta", o.getIntFechaEnviadoHasta());
			mapa.put("pIntFechaEfectuadoDesde", o.getIntFechaEfectuadoDesde());
			mapa.put("pIntFechaEfectuadoHasta", o.getIntFechaEfectuadoDesde());
			mapa.put("pIntFechaChequeDesde", o.getIntFechaChequeDesde());
			mapa.put("pIntFechaChequeHasta", o.getIntFechaChequeDesde());
			lista = dao.getListaEstructuraDetalle(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetallePorIdEmpresaYIdNivelYIdSucursal(Integer intIdEmpresa,Integer intIdNivel,Integer intIdSucursal) throws BusinessException{
		List<EstructuraDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", intIdEmpresa);
			mapa.put("intIdNivel", intIdNivel);
			mapa.put("intIdSucursal", intIdSucursal);
			lista = dao.getListaEstructuraDetallePorIdEmpresaYIdNivelYIdSucursal(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getConveEstrucDetAdministra(EstructuraComp o) throws BusinessException{
		log.info("-----------------------Debugging EstructuraDetalleBO.getConveEstrucDetAdministra-----------------------------");
		List<EstructuraDetalle> lista = null;
		
		try{
			log.info("pIntSeguSucursalPk: "+o.getEstructuraDetalle().getIntSeguSucursalPk());
			log.info("IntSeguSubSucursalPk: "+o.getEstructuraDetalle().getIntSeguSubSucursalPk());
			HashMap mapa = new HashMap();
			mapa.put("pIntSeguSucursalPk", o.getEstructuraDetalle().getIntSeguSucursalPk());
			mapa.put("pIntSeguSubSucursalPk", o.getEstructuraDetalle().getIntSeguSubSucursalPk());
			lista = dao.getConveEstrucDetAdministra(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getConveEstrucDetPlanilla(EstructuraComp o) throws BusinessException{
		log.info("-----------------------Debugging EstructuraDetalleBO.getConveEstrucDetAdministra-----------------------------");
		List<EstructuraDetalle> lista = null;
		
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntSeguSucursalPk", o.getEstructuraDetalle().getIntSeguSucursalPk());
			mapa.put("pIntSeguSubSucursalPk", o.getEstructuraDetalle().getIntSeguSubSucursalPk());
			lista = dao.getConveEstrucDetPlanilla(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EstructuraComp> getListaEstructuraCompPorTipoConvenio(Integer intIdNivel, Integer intTipoConvenio) throws BusinessException{
		List<EstructuraComp> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntTipoConvenio", 	intTipoConvenio);
			mapa.put("pIntNivel", 			intIdNivel);
			lista = dao.getListaEstructuraCompPorTipoCovenio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public EstructuraDetalle getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(EstructuraDetalleId o,Integer intParaTipoSocioCod,Integer intParaModalidadCod) throws BusinessException{
		List<EstructuraDetalle> lista = null;
		EstructuraDetalle domain = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntNivel", 				o.getIntNivel());
			mapa.put("pIntCodigo", 				o.getIntCodigo());
			mapa.put("pIntParaTipoSocioCod", 	intParaTipoSocioCod);
			mapa.put("pIntParaModalidadCod", 	intParaModalidadCod);
			lista = dao.getListaPorPkEstructuraPorTipoSocioYModalidad(mapa);
			
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(EstructuraDetalleId o,Integer intParaTipoSocioCod,Integer intParaModalidadCod) throws BusinessException{
		List<EstructuraDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntNivel", 				o.getIntNivel());
			mapa.put("pIntCodigo", 				o.getIntCodigo());
			mapa.put("pIntParaTipoSocioCod", 	intParaTipoSocioCod);
			mapa.put("pIntParaModalidadCod", 	intParaModalidadCod);
			lista = dao.getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public EstructuraDetalle getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(EstructuraId o,Integer intCaso,Integer intParaTipoSocioCod,Integer intParaModalidadCod) throws BusinessException{
		List<EstructuraDetalle> lista = null;
		EstructuraDetalle domain = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntNivel", 				o.getIntNivel());
			mapa.put("pIntCodigo", 				o.getIntCodigo());
			mapa.put("pIntCaso", 				intCaso);
			mapa.put("pIntParaTipoSocioCod", 	intParaTipoSocioCod);
			mapa.put("pIntParaModalidadCod", 	intParaModalidadCod);
			lista = dao.getListaPorPkEstructuraYCasoYTipoSocioYModalidad(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetallePorCodExterno(String strCodExterno) throws BusinessException{
		List<EstructuraDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pStrCodExterno", 				strCodExterno);
			lista = dao.getListaPorCodExterno(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public EstructuraDetalle getEstructuraDetallePorCodSocioYTipoSocYMod(Integer intCodSocio, Integer intParaTipoSocioCod,Integer intParaModalidadCod) throws BusinessException{
		List<EstructuraDetalle> lista = null;
		EstructuraDetalle domain = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntCodigo", 				intCodSocio);
			mapa.put("pIntParaTipoSocioCod", 	intParaTipoSocioCod);
			mapa.put("pIntParaModalidadCod", 	intParaModalidadCod);
			lista = dao.getListaPorEstructuraDetallePorCodSocioYAdministraYTipoSocYMod(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	public EstructuraDetalle getEstructuraDetallePorSucuSubsucuYCodigo(SocioEstructura o) throws BusinessException{
		EstructuraDetalle domain = null;
		List<EstructuraDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intCodigo", o.getIntCodigo());
			mapa.put("intIdSucursal", o.getIntIdSucursalAdministra());
			mapa.put("intIdSubSucursal", o.getIntIdSubsucurAdministra());
			lista = dao.getEstructuraDetallePorSucuSubsucuYCodigo(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
}