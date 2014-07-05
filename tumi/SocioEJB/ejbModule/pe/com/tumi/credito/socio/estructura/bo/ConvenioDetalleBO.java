package pe.com.tumi.credito.socio.estructura.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.estructura.dao.ConvenioDetalleDao;
import pe.com.tumi.credito.socio.estructura.dao.impl.ConvenioDetalleDaoIbatis;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class ConvenioDetalleBO {
	
	private ConvenioDetalleDao dao = (ConvenioDetalleDao)TumiFactory.get(ConvenioDetalleDaoIbatis.class);
	
	public ConvenioEstructuraDetalle grabarConvenioDetalle(ConvenioEstructuraDetalle o) throws BusinessException{
		ConvenioEstructuraDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConvenioEstructuraDetalle modificarConvenioDetalle(ConvenioEstructuraDetalle o) throws BusinessException{
		ConvenioEstructuraDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConvenioEstructuraDetalle getConvenioDetallePorPK(ConvenioEstructuraDetalleId pPK) throws BusinessException{
		ConvenioEstructuraDetalle domain = null;
		List<ConvenioEstructuraDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intConvenio", 	pPK.getIntConvenio());
			mapa.put("intItemConvenio", pPK.getIntItemConvenio());
			mapa.put("intNivel", 		pPK.getIntNivel());
			mapa.put("intCodigo", 		pPK.getIntCodigo());
			mapa.put("intCaso", 		pPK.getIntCaso());
			mapa.put("intItemCaso", 	pPK.getIntItemCaso());
			lista = dao.getListaConvenioDetallePorPK(mapa);
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
	
	public List<ConvenioEstructuraDetalleComp> getListaConvenioEstructuraDetallePorPKConvenio(AdendaId pPK) throws BusinessException{
		List<ConvenioEstructuraDetalleComp> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intConvenio", 		pPK.getIntConvenio());
			mapa.put("intItemConvenio", 	pPK.getIntItemConvenio());
			
			lista = dao.getListaConvenioDetallePorPKConvenio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ConvenioEstructuraDetalleComp> getConvenioEstructuraDetallePorEstructuraDet(ConvenioEstructuraDetalleComp pId) throws BusinessException{
		List<ConvenioEstructuraDetalleComp> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intNivel", 	pId.getEstructura().getId().getIntNivel());
			mapa.put("intCodigo", 	pId.getEstructura().getId().getIntCodigo());
			mapa.put("intCaso", 	pId.getEstructuraDetalle().getId().getIntCaso());
			//mapa.put("intItemCaso", pId.getEstructuraDetalle().getId().getIntItemCaso());
			lista = dao.getConvenioDetallePorPKEstructuraDet(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public ConvenioEstructuraDetalle getConvenioDetallePorPKEstructuraDetalle(EstructuraDetalleId pPK) throws BusinessException{
		ConvenioEstructuraDetalle domain = null;
		List<ConvenioEstructuraDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intNivel", 		pPK.getIntNivel());
			mapa.put("intCodigo", 		pPK.getIntCodigo());
			mapa.put("intCaso", 		pPK.getIntCaso());
			mapa.put("intItemCaso", 	pPK.getIntItemCaso());
			lista = dao.getConvenioDetallePorPKEstructuraDetalle(mapa);
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
	
	public List<ConvenioEstructuraDetalleComp> getListaConvenioEstructuraDetallePorEstructuraDetCompleto(EstructuraDetalleId pId) throws BusinessException{
		List<ConvenioEstructuraDetalleComp> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intNivel", 	pId.getIntNivel());
			mapa.put("intCodigo", 	pId.getIntCodigo());
			mapa.put("intCaso", 	pId.getIntCaso());
			lista = dao.getListaConvenioEstructuraDetallePorEstructuraDetCompleto(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
