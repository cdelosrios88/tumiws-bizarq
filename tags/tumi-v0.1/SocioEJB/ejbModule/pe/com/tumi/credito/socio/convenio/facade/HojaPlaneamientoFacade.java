package pe.com.tumi.credito.socio.convenio.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.convenio.bo.AdendaBO;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.PerfilValidacion;
import pe.com.tumi.credito.socio.convenio.domain.composite.ConvenioComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaControlComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaPlaneamientoComp;
import pe.com.tumi.credito.socio.convenio.service.AdendaService;
import pe.com.tumi.credito.socio.convenio.service.HojaControlService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

/**
 * Session Bean implementation class HojaPlaneamientoFacade
 */
@Stateless
public class HojaPlaneamientoFacade extends TumiFacade implements HojaPlaneamientoFacadeRemote, HojaPlaneamientoFacadeLocal {
    
	private AdendaService adendaService = (AdendaService)TumiFactory.get(AdendaService.class);
	private AdendaBO boAdenda = (AdendaBO)TumiFactory.get(AdendaBO.class);
	
	public List<HojaPlaneamientoComp> getListaHojaPlaneamientoCompDeBusquedaAdenda(HojaPlaneamientoComp o) throws BusinessException{
		List<HojaPlaneamientoComp> lista = null;
		try{
			lista = adendaService.getListaHojaPlaneamientoCompDeBusquedaAdenda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Adenda getAdendaPorIdAdenda(AdendaId pPK) throws BusinessException{
		Adenda adenda = null;
		try{
			adenda = adendaService.getAdendaPorIdAdenda(pPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adenda;
	}
	
	public List<ConvenioComp> getListaConvenioCompDeBusqueda(ConvenioComp o) throws BusinessException{
		List<ConvenioComp> lista = null;
		try{
			lista = adendaService.getListaConvenioCompDeBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ConvenioComp> getListaConvenioDet(ConvenioComp o) throws BusinessException{
		List<ConvenioComp> lista = null;
		try{
			lista = boAdenda.getListaConvenioDet(o);
			
			for(ConvenioComp h : lista){
				h.getAdenda().setStrFechaRegistro(Constante.sdf2.format(h.getAdenda().getDtFechaHojaPendiente()));
				h.getAdenda().setStrDtInicio(Constante.sdf.format(h.getAdenda().getDtInicio()));
				h.getAdenda().setStrDtCese(Constante.sdf.format(h.getAdenda().getDtCese()));
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ConvenioComp> getListaConvenioPorTipoConvenio(ConvenioComp o) throws BusinessException{
		List<ConvenioComp> lista = null;
		try{
			lista = adendaService.getListaConvenioPorTipoConvenio(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}