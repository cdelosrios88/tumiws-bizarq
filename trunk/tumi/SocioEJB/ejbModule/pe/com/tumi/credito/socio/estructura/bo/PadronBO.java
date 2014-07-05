package pe.com.tumi.credito.socio.estructura.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.estructura.dao.PadronDao;
import pe.com.tumi.credito.socio.estructura.dao.impl.PadronDaoIbatis;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.PadronId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class PadronBO {
	
	private PadronDao dao = (PadronDao)TumiFactory.get(PadronDaoIbatis.class);
	protected static Logger log = Logger.getLogger(PadronBO.class);
	
	public Padron grabarPadron(Padron o) throws BusinessException{
		Padron dto = null;
		//log.info(o);
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Padron modificarPadron(Padron o) throws BusinessException{
		Padron dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Padron getPadronPorPK(PadronId pPK) throws BusinessException{
		Padron domain = null;
		List<Padron> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPeriodo", pPK.getIntPeriodo());
			mapa.put("intMes", pPK.getIntMes());
			mapa.put("intNivel", pPK.getIntNivel());
			mapa.put("intCodigo", pPK.getIntCodigo());
			mapa.put("intParaTipoArchivoCod", pPK.getIntParaTipoArchivoPadronCod());
			mapa.put("intParaModalidadCod", pPK.getIntParaModalidadCod());
			mapa.put("intParaTipoSocioCod", pPK.getIntParaTipoSocioCod());
			mapa.put("intItemAdministraPadron", pPK.getIntItemAdministraPadron());
			mapa.put("intItem", pPK.getIntItem());
			lista = dao.getListaPadronPorPK(mapa);
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
	
	public List<Padron> getPadronBusqueda(PadronId pPK) throws BusinessException{
		List<Padron> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPeriodo", pPK.getIntPeriodo());
			mapa.put("intMes", pPK.getIntMes());
			lista = dao.getListaBusqueda(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Padron getPadronPorLibElectoral(String strLibEle) throws BusinessException{
		Padron domain = null;
		List<Padron> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("strLibEle", strLibEle);
			lista = dao.getPadronPorLibElectoral(mapa);
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
	
	
	public Padron getPadronSOLOPorLibElectoral(String strLibEle, Integer item) throws BusinessException{
		Padron domain = null;
		List<Padron> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("strLibEle", strLibEle);
			mapa.put("pItem", item);
			lista = dao.getPadronSOLOPorLibElectoral(mapa);
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
