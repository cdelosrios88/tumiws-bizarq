package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PerfilValidacionDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.PerfilValidacionDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.PerfilValidacion;
import pe.com.tumi.credito.socio.convenio.domain.PerfilValidacionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class PerfilValidacionBO {
	
	private PerfilValidacionDao dao = (PerfilValidacionDao)TumiFactory.get(PerfilValidacionDaoIbatis.class);
	
	public PerfilValidacion grabarPerfilValidacion(PerfilValidacion o) throws BusinessException{
		PerfilValidacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PerfilValidacion modificarPerfilValidacion(PerfilValidacion o) throws BusinessException{
		PerfilValidacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PerfilValidacion getPerfilValidacionPorPK(PerfilValidacionId pPK) throws BusinessException{
		PerfilValidacion domain = null;
		List<PerfilValidacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intSeguPerfilPk", 	pPK.getIntSeguPerfilPk());
			mapa.put("intParaValidacionCod",pPK.getIntParaValidacionCod());
			lista = dao.getListaPerfilValidacionPorPK(mapa);
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
	
	public List<PerfilValidacion> getListaPerfilValidacionPorEmpresaYPerfil(PerfilValidacionId pPK) throws BusinessException{
		List<PerfilValidacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intSeguPerfilPk", 	pPK.getIntSeguPerfilPk());
			lista = dao.getListaPerfilValidacionPorEmpresaYPerfil(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
