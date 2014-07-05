package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PerfilDao;
import pe.com.tumi.credito.socio.convenio.dao.RetencionPlanillaDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.PerfilDaoIbatis;
import pe.com.tumi.credito.socio.convenio.dao.impl.RetencionPlanillaDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Perfil;
import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalleId;
import pe.com.tumi.credito.socio.convenio.domain.PerfilId;
import pe.com.tumi.credito.socio.convenio.domain.RetencionPlanilla;
import pe.com.tumi.credito.socio.convenio.domain.RetencionPlanillaId;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaControlComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class RetencionPlanillalBO {
	
	private RetencionPlanillaDao dao = (RetencionPlanillaDao)TumiFactory.get(RetencionPlanillaDaoIbatis.class);
	
	public RetencionPlanilla grabarRetencionPlla(RetencionPlanilla o) throws BusinessException{
		RetencionPlanilla dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public RetencionPlanilla modificarPerfil(RetencionPlanilla o) throws BusinessException{
		RetencionPlanilla dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public RetencionPlanilla getRetencionPlanillaPorPK(RetencionPlanillaId pPK) throws BusinessException{
		RetencionPlanilla domain = null;
		List<RetencionPlanilla> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemConvenio", 	pPK.getIntConvenio());
			mapa.put("intItemItemConvenio", pPK.getIntItemConvenio());
			mapa.put("intItemRetPlanilla", 	pPK.getIntItemRetPlla());
			lista = dao.getListaRetencPllaPorPK(mapa);
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
	
	public List<RetencionPlanilla> getListaRetencionPlanillaPorPKAdenda(AdendaId pPK) throws BusinessException{
		List<RetencionPlanilla> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdConvenio", 		pPK.getIntConvenio());
			mapa.put("intIdItemConvenio", 	pPK.getIntItemConvenio());
			lista = dao.getListaRetenPllaPorPKAdenda(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}