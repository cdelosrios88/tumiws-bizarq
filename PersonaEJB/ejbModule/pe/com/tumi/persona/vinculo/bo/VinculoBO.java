package pe.com.tumi.persona.vinculo.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.vinculo.dao.VinculoDao;
import pe.com.tumi.persona.vinculo.dao.impl.VinculoDaoIbatis;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

public class VinculoBO {
	
	protected   static Logger 	log = Logger.getLogger(VinculoBO.class);
	
	private VinculoDao dao = (VinculoDao)TumiFactory.get(VinculoDaoIbatis.class);
	
	public Vinculo grabarVinculo(Vinculo o) throws BusinessException{
		Vinculo dto = null;
		try{
			o.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			log.info(o);
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Vinculo modificarVinculo(Vinculo o) throws BusinessException{
		Vinculo dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Vinculo getVinculoPorPK(Integer pIntItemVinculo) throws BusinessException{
		Vinculo domain = null;
		List<Vinculo> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("pIntItemVinculo", pIntItemVinculo);
			lista = dao.getListaVinculoPorPK(mapa);
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
	
	public List<Vinculo> getVinculoPorPK(PersonaEmpresaPK pPK) throws BusinessException{
		List<Vinculo> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pPK.getIntIdEmpresa());
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			lista = dao.getListaVinculoPorPKPersonaEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Vinculo> getVinculoPorPersonaEmpresaPK(PersonaEmpresaPK pPK) throws BusinessException{
		List<Vinculo> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pPK.getIntIdEmpresa());
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			lista = dao.getListaVinculoPorPKPersonaEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Vinculo> getListaVinculoPorPKPersonaEmpresaYTipoVinculo(PersonaEmpresaPK id,Integer intTipoVinculo) throws BusinessException{
		List<Vinculo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntIdEmpresa", id.getIntIdEmpresa());
			mapa.put("pIntIdPersona", id.getIntIdPersona());
			mapa.put("pIntTipoVinculoCod", intTipoVinculo);
			lista = dao.getListaPorPKPersonaEmpresaYTipoVinculo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Vinculo getVinculoPorPKPersEmpresaYPkPersona(Vinculo o) throws BusinessException{
		Vinculo domain = null;
		List<Vinculo> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", 		o.getIntIdEmpresa());
			mapa.put("intIdPersona", 		o.getIntIdPersona());
			mapa.put("intEmpresaVinculo", 	o.getIntEmpresaVinculo());
			mapa.put("intPersonaVinculo", 	o.getIntPersonaVinculo());
			lista = dao.getListaVinculoPorPKPersEmpresaYPkPersona(mapa);
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
	
	public List<Vinculo> getVinculoPorPKPersona(PersonaEmpresaPK pPK) throws BusinessException{
		List<Vinculo> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pPK.getIntIdEmpresa());
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			lista = dao.getListaVinculoPorPKPersonaEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
