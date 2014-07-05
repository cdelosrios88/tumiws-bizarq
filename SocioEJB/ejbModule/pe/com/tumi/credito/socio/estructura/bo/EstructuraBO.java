package pe.com.tumi.credito.socio.estructura.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.estructura.dao.EstructuraDao;
import pe.com.tumi.credito.socio.estructura.dao.impl.EstructuraDaoIbatis;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class EstructuraBO {
	
	protected  static Logger log = Logger.getLogger(EstructuraBO.class);
	private EstructuraDao dao = (EstructuraDao)TumiFactory.get(EstructuraDaoIbatis.class);
	
	public Estructura grabarEstructura(Estructura o) throws BusinessException{
		Estructura dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Estructura modificarEstructura(Estructura o) throws BusinessException{
		Estructura dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA DE CREACION: JCHAVEZ / 29.10.2013
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public Estructura getEstructuraPorPK(EstructuraId pPK) throws BusinessException{
		Estructura domain = null;
		List<Estructura> lista = null;
		try{
			//HashMap mapa = new HashMap();
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intNivel", pPK.getIntNivel());
			mapa.put("intCodigo", pPK.getIntCodigo());
			lista = dao.getListaEstructuraPorPK(mapa);
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
	
	public List<Estructura> getListaEstructuraBusqueda(Estructura o) throws BusinessException{
		log.info("-----------------------Debugging EstructuraBO.getListaEstructuraBusqueda-----------------------------");
		Estructura domain = null;
		List<Estructura> lista = null;
		try{
			log.info("intNivel: " + o.getId().getIntNivel());
			log.info("intCodigo: " + o.getId().getIntCodigo());
			log.info("intPersEmpresaPk: " + o.getIntPersEmpresaPk());
			log.info("intPersPersonaPk: " + o.getIntPersPersonaPk());
			log.info("dtFechaRegistro: " + o.getDtFechaRegistro());
			log.info("intIdGrupo: " + o.getIntIdGrupo());
			log.info("intParaTipoTerceroCod: " + o.getIntParaTipoTerceroCod());
			log.info("intParaEstadoCod: " + o.getIntParaEstadoCod());
			log.info("intPersPersonaUsuarioPk: " + o.getIntPersPersonaUsuarioPk());
			log.info("intNivelRel: " + o.getIntNivelRel());
			log.info("intIdCodigoRel: " + o.getIntIdCodigoRel());
			log.info("dtFechaEliminacion: " + o.getDtFechaEliminacion());
			
			HashMap mapa = new HashMap();
			mapa.put("pIntNivel", o.getId().getIntNivel());
			mapa.put("pIntCodigo", o.getId().getIntCodigo());
			mapa.put("pIntPersEmpresaPk", o.getIntPersEmpresaPk());
			mapa.put("pIntPersPersonaPk", o.getIntPersPersonaPk());
			mapa.put("pDtFechaRegistro", o.getDtFechaRegistro());
			mapa.put("pIntIdGrupo", o.getIntIdGrupo());
			mapa.put("pIntParaTipoTerceroCod", o.getIntParaTipoTerceroCod());
			mapa.put("pIntParaEstadoCod", o.getIntParaEstadoCod());
			mapa.put("pIntPersPersonaUsuarioPk", o.getIntPersPersonaUsuarioPk());
			mapa.put("pIntNivelRel", o.getIntNivelRel());
			mapa.put("pIntIdCodigoRel", o.getIntIdCodigoRel());
			mapa.put("pDtFechaEliminacion", o.getDtFechaEliminacion());
			lista = dao.getListaEstructuraBusqueda(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Estructura> getListaEstructuraPorNivelYCodigoRel(Integer intNivel, Integer intCodigoRel)throws BusinessException {
		List<Estructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intNivel", intNivel);
			mapa.put("intCodigoRel", intCodigoRel);
			lista = dao.getListaEstructuraPorNivelYCodigoRel(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Estructura> getListaEstructuraPorNivelYCodigo(Integer intNivel, Integer intCodigo)throws BusinessException {
		List<Estructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intNivel", intNivel);
			mapa.put("intCodigoRel", intCodigo);
			lista = dao.getListaEstructuraPorNivelYCodigo(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Estructura> getListaEstructuraPorIdEmpresaYIdNivelYIdCasoIdSucursal(Integer intIdEmpresa,Integer intIdNivel,Integer intIdCaso,Integer intIdSucursal)throws BusinessException {
		List<Estructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", intIdEmpresa);
			mapa.put("intIdNivel", intIdNivel);
			mapa.put("intIdCaso", intIdCaso);
			mapa.put("intIdSucursal", intIdSucursal);
			lista = dao.getListaPorIdEmpresaYIdNivelYIdCasoIdSucursal(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Estructura> getListaEstructuraPorIdEmpresaYIdCodigoYIdNivelYIdCasoIdSucursal(Integer intIdEmpresa,Integer intIdCodigo,Integer intIdNivel,Integer intIdCaso,Integer intIdSucursal)throws BusinessException {
		List<Estructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", intIdEmpresa);
			mapa.put("intIdCodigo", intIdCodigo);
			mapa.put("intIdNivel", intIdNivel);
			mapa.put("intIdCaso", intIdCaso);
			mapa.put("intIdSucursal", intIdSucursal);
			lista = dao.getListaPorIdEmpresaYIdCodigoYIdNivelYIdCasoIdSucursal(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Estructura getEstructuraPorIdEmpresaYIdPersona(Integer intIdEmpresa,Integer intIdPersona)throws BusinessException {
		List<Estructura> lista = null;
		Estructura dto = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", intIdEmpresa);
			mapa.put("intIdPersona", intIdPersona);
			lista = dao.getListaPorIdEmpresaYIdPersona(mapa);
			if(lista!=null){
				if(lista.size()==1){
					dto = lista.get(0);
				}else if(lista.size()==0){
					dto = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<Estructura> getListaEstructuraPorIdEmpresaYIdCasoIdSucursal(Integer intIdEmpresa,Integer intIdCaso,Integer intIdSucursal)throws BusinessException {
		List<Estructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", intIdEmpresa);			
			mapa.put("intIdCaso", intIdCaso);
			mapa.put("intIdSucursal", intIdSucursal);
			lista = dao.getListaPorIdEmpresaYIdCasoIdSucursal(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
