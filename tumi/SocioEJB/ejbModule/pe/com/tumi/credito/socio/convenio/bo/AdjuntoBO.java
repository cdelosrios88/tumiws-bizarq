package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.AdjuntoDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.AdjuntoDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Adjunto;
import pe.com.tumi.credito.socio.convenio.domain.AdjuntoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.Finalidad;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class AdjuntoBO {
	
	private AdjuntoDao dao = (AdjuntoDao)TumiFactory.get(AdjuntoDaoIbatis.class);
	
	public Adjunto grabarAdjunto(Adjunto o) throws BusinessException{
		Adjunto dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Adjunto modificarAdjunto(Adjunto o) throws BusinessException{
		Adjunto dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Adjunto getAdjuntoPorPK(AdjuntoId pPK) throws BusinessException{
		Adjunto domain = null;
		List<Adjunto> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intConvenio", 	pPK.getIntConvenio());
			mapa.put("intItemConvenio", pPK.getIntItemConvenio());
			mapa.put("intCodigo", 		pPK.getIntCodigo());
			lista = dao.getListaAdjuntoPorPK(mapa);
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
	
	public List<Adjunto> getListaAdjuntoPorPKAdenda(AdendaId pPK) throws BusinessException{
		List<Adjunto> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intConvenio", 		pPK.getIntConvenio());
			mapa.put("intItemConvenio", 	pPK.getIntItemConvenio());
			
			lista = dao.getListaAdjuntoPorPKAdenda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
