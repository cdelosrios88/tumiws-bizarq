package pe.com.tumi.credito.socio.core.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.bo.SocioEstructuraBO;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.fecha.JFecha;

public class SocioEstructuraService {
	
	private SocioEstructuraBO boSocioEstructura= (SocioEstructuraBO)TumiFactory.get(SocioEstructuraBO.class); 
	protected  static Logger log = Logger.getLogger(SocioEstructuraService.class);
	
	public List<SocioEstructura> grabarListaDinamicaSocioEstructura(List<SocioEstructura> lista,SocioPK socioPK) throws BusinessException{
		log.info("-----------------------Debugging SocioBO.grabarListaDinamicaSocioEstructura-----------------------------");
		SocioEstructura dto = null;
		SocioEstructura dtoTemp = null;
		Timestamp lTsFechaEliminacion = null;
		try{
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				
				if(dto.getId() == null || dto.getId().getIntItemSocioEstructura() == null){
					if(dto.getId() == null)dto.setId(new SocioEstructuraPK());
					dto.getId().setIntIdPersona(socioPK.getIntIdPersona());
					dto.getId().setIntIdEmpresa(socioPK.getIntIdEmpresa());
					dto = boSocioEstructura.grabarSocioEstructura(dto);
				}else{
					dtoTemp = boSocioEstructura.getSocioEstructuraPorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPersona(socioPK.getIntIdPersona());
						dto.getId().setIntIdEmpresa(socioPK.getIntIdEmpresa());
						dto = boSocioEstructura.grabarSocioEstructura(dto);
					}else{
						lTsFechaEliminacion = JFecha.obtenerTimestampDeFechayHoraActual();
						if(dto.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)==0){
							dto.setTsFechaEliminacion(lTsFechaEliminacion);
						}
						dto = boSocioEstructura.modificarSocioEstructura(dto);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
