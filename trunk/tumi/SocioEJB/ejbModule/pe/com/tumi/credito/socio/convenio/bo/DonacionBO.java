package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.DonacionDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.DonacionDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.DonacionRegalia;
import pe.com.tumi.credito.socio.convenio.domain.DonacionRegaliaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class DonacionBO {
	
	private DonacionDao dao = (DonacionDao)TumiFactory.get(DonacionDaoIbatis.class);
	
	public DonacionRegalia grabarDonacionRegalia(DonacionRegalia o) throws BusinessException{
		DonacionRegalia dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public DonacionRegalia modificarDonacion(DonacionRegalia o) throws BusinessException{
		DonacionRegalia dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public DonacionRegalia getDonacionPorPK(DonacionRegaliaId pPK) throws BusinessException{
		DonacionRegalia domain = null;
		List<DonacionRegalia> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemConvenio", 	pPK.getIntConvenio());
			mapa.put("intItemItemConvenio", pPK.getIntItemConvenio());
			mapa.put("intItemDonacion", 	pPK.getIntItemDonacion());
			lista = dao.getListaDonacionRegaliaPorPK(mapa);
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
	
	public List<DonacionRegalia> getListaDonacionPorPKAdenda(AdendaId pPK) throws BusinessException{
		List<DonacionRegalia> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdConvenio", 		pPK.getIntConvenio());
			mapa.put("intIdItemConvenio", 	pPK.getIntItemConvenio());
			lista = dao.getListaDonacionRegaliaPorPKAdenda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}