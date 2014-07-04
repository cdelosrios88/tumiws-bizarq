package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.FirmanteDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.FirmanteDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Firmante;
import pe.com.tumi.credito.socio.convenio.domain.FirmanteId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class FirmanteBO {
	
	private FirmanteDao dao = (FirmanteDao)TumiFactory.get(FirmanteDaoIbatis.class);
	
	public Firmante grabarFirmante(Firmante o) throws BusinessException{
		Firmante dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Firmante modificarFirmante(Firmante o) throws BusinessException{
		Firmante dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Firmante getFirmantePorPK(FirmanteId pPK) throws BusinessException{
		Firmante domain = null;
		List<Firmante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemConvenio", 		pPK.getIntConvenio());
			mapa.put("intItemItemConvenio", 	pPK.getIntItemConvenio());
			mapa.put("intItemAdendaFirmante", 	pPK.getIntItemAdendaFirmante());
			lista = dao.getListaFirmantePorPK(mapa);
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
	
	public List<Firmante> getListaFirmantePorPKAdenda(AdendaId pPK) throws BusinessException{
		List<Firmante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdConvenio", 		pPK.getIntConvenio());
			mapa.put("intIdItemConvenio", 	pPK.getIntItemConvenio());
			lista = dao.getListaFirmantePorPKAdenda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}