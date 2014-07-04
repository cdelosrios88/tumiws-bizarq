package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PerfilDetalleDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.PerfilDetalleDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalle;
import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalleId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class PerfilDetalleBO {
	
	private PerfilDetalleDao dao = (PerfilDetalleDao)TumiFactory.get(PerfilDetalleDaoIbatis.class);
	
	public PerfilDetalle grabarPerfilDetalle(PerfilDetalle o) throws BusinessException{
		PerfilDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PerfilDetalle modificarPerfilDetalle(PerfilDetalle o) throws BusinessException{
		PerfilDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PerfilDetalle getPerfilDetallePorPK(PerfilDetalleId pPK) throws BusinessException{
		PerfilDetalle domain = null;
		List<PerfilDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemAdendaPerfil", pPK.getIntItemAdendaPerfil());
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intSeguPerfilPk", 	pPK.getIntSeguPerfilPk());
			mapa.put("intParaValidacionCod",pPK.getIntParaValidacionCod());
			lista = dao.getListaPerfilDetallePorPK(mapa);
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
	
	public List<PerfilDetalle> getListaPerfilDetallePorPKPerfil(Integer intItemAdendaPerfil) throws BusinessException{
		List<PerfilDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemAdendaPerfil", intItemAdendaPerfil);
			lista = dao.getListaPerfilDetallePorPKPerfil(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
