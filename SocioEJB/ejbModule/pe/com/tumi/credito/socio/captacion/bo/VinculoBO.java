package pe.com.tumi.credito.socio.captacion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.VinculoDao;
import pe.com.tumi.credito.socio.captacion.dao.impl.VinculoDaoIbatis;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.credito.socio.captacion.domain.VinculoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class VinculoBO {
	
	private VinculoDao dao = (VinculoDao)TumiFactory.get(VinculoDaoIbatis.class);
	
	public Vinculo grabarVinculo(Vinculo o) throws BusinessException{
		Vinculo dto = null;
		try{
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
	
	public Vinculo getVinculoPorPK(VinculoId pPK) throws BusinessException{
		Vinculo domain = null;
		List<Vinculo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intEmpresaPk", pPK.getIntEmpresaPk());
			mapa.put("paraTipoCaptacionCod", pPK.getParaTipoCaptacionCod());
			mapa.put("intItem", pPK.getIntItem());
			mapa.put("paraTipoVinculoCod", pPK.getParaTipoVinculoCod());
			mapa.put("intItemVinculo", pPK.getIntItemVinculo());
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

	public List<Vinculo> getListaVinculos(VinculoId pPK) throws BusinessException{
		List<Vinculo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intEmpresaPk", pPK.getIntEmpresaPk());
			mapa.put("paraTipoCaptacionCod", pPK.getParaTipoCaptacionCod());
			mapa.put("intItem", pPK.getIntItem());
			mapa.put("paraTipoVinculoCod", pPK.getParaTipoVinculoCod());
			mapa.put("intItemVinculo", pPK.getIntItemVinculo());
			lista = dao.getListaVinculoPorPK(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public List<Vinculo> getListaVinculos() throws BusinessException{
		List<Vinculo> lista = null;
		try{
			lista = dao.getListaVinculos();
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
	
	
	public List<Vinculo> getListaVinculoPorPKCaptacion(CaptacionId pPK) throws DAOException{
		List<Vinculo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("paraTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", pPK.getIntItem());
			lista = dao.getListaVinculoPorPKCaptacion(mapa);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Object eliminarVinculo(CaptacionId pPK, String strPkVinculo) throws BusinessException {
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", 			pPK.getIntItem());
			mapa.put("strPkVinculo", 		strPkVinculo);
			dao.eliminar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return strPkVinculo;
	}
}
