package pe.com.tumi.credito.socio.convenio.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.convenio.bo.AdendaBO;
import pe.com.tumi.credito.socio.convenio.bo.PerfilBO;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.Perfil;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaControlComp;
import pe.com.tumi.credito.socio.convenio.service.HojaControlService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

/**
 * Session Bean implementation class HojaControlFacade
 */
@Stateless
public class HojaControlFacade extends TumiFacade implements HojaControlFacadeRemote, HojaControlFacadeLocal {
    
	private static Logger log = Logger.getLogger(HojaControlFacade.class);
	private HojaControlService hojaControlService = (HojaControlService)TumiFactory.get(HojaControlService.class);
	private PerfilBO boAdendaPerfil = (PerfilBO)TumiFactory.get(PerfilBO.class);
	ConvenioFacadeLocal convenioFacade = null;
	
	public List<HojaControlComp> getListaAreaEncargadaDeHojaDeControl(HojaControlComp o) throws BusinessException{
		List<HojaControlComp> lista = null;
		try{
			lista = hojaControlService.getListaHojaControlComp(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Perfil grabarAdendaPerfil(Perfil pPerfil)throws BusinessException{
		Perfil dto = null;
		try{
			dto = hojaControlService.grabarAdendaPerfil(pPerfil);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			log.error("error al grabar", e);
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			log.error("error al grabar", e);
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Perfil getPerfilPorPKPerfil(Perfil o) throws BusinessException{
		Perfil perfil = null;
		try{
			perfil = boAdendaPerfil.getPerfilPorPKAdenda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return perfil;
	}

}
