package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PerfilDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.PerfilDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Perfil;
import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalleId;
import pe.com.tumi.credito.socio.convenio.domain.PerfilId;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaControlComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class PerfilBO {
	
	private PerfilDao dao = (PerfilDao)TumiFactory.get(PerfilDaoIbatis.class);
	
	public Perfil grabarPerfil(Perfil o) throws BusinessException{
		Perfil dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Perfil modificarPerfil(Perfil o) throws BusinessException{
		Perfil dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Perfil getPerfilPorPK(PerfilId pPK) throws BusinessException{
		Perfil domain = null;
		List<Perfil> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemAdendaPerfil", 	pPK.getIntItemAdendaPerfil());
			lista = dao.getListaPerfilPorPK(mapa);
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
	
	public Perfil getPerfilPorPKAdenda(Perfil pPK) throws BusinessException{
		Perfil domain = null;
		List<Perfil> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdConvenio", 		pPK.getIntConvenio());
			mapa.put("intIdItemConvenio", 	pPK.getIntItemConvenio());
			mapa.put("intIdEmpresa", 		pPK.getIntPersEmpresaPk());
			mapa.put("intIdPerfil", 		pPK.getIntSeguPerfilPk());
			lista = dao.getListaPerfilPorPKAdenda(mapa);
			
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
}