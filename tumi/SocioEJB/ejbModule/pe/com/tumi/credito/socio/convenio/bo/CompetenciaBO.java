package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.CompetenciaDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.CompetenciaDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Competencia;
import pe.com.tumi.credito.socio.convenio.domain.CompetenciaId;
import pe.com.tumi.credito.socio.convenio.domain.Poblacion;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CompetenciaBO {
	
	private CompetenciaDao dao = (CompetenciaDao)TumiFactory.get(CompetenciaDaoIbatis.class);
	
	public Competencia grabarCompetencia(Competencia o) throws BusinessException{
		Competencia dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Competencia modificarCompetencia(Competencia o) throws BusinessException{
		Competencia dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Competencia getCompetenciaPorPK(CompetenciaId pPK) throws BusinessException{
		Competencia domain = null;
		List<Competencia> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemCompetencia", 	pPK.getIntItemCompetencia());
			
			lista = dao.getListaCompetenciaPorPK(mapa);
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
	
	public List<Competencia> getListaCompetenciaPorPKConvenio(AdendaId pPK) throws BusinessException{
		List<Competencia> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intConvenio", 		pPK.getIntConvenio());
			mapa.put("intItemConvenio", 	pPK.getIntItemConvenio());
			
			lista = dao.getListaCompetenciaPorPKConvenio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}