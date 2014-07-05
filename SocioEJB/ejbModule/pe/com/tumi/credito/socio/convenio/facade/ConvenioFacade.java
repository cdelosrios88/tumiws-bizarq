package pe.com.tumi.credito.socio.convenio.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.convenio.bo.AdendaBO;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Adjunto;
import pe.com.tumi.credito.socio.convenio.service.AdendaService;
import pe.com.tumi.credito.socio.convenio.service.ConvenioDetalleService;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;

@Stateless
public class ConvenioFacade extends TumiFacade implements ConvenioFacadeRemote, ConvenioFacadeLocal {
    
	private static Logger log = Logger.getLogger(ConvenioFacade.class);
	private AdendaService adendaService = (AdendaService)TumiFactory.get(AdendaService.class);
	private AdendaBO boAdenda = (AdendaBO)TumiFactory.get(AdendaBO.class);
	private ConvenioDetalleService convenioDetalleService = (ConvenioDetalleService)TumiFactory.get(ConvenioDetalleService.class);
	
	public Adenda grabarAdenda(Adenda pAdenda)throws BusinessException{
		Adenda dto = null;
		try{
			dto = adendaService.grabarAdenda(pAdenda);
			
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
	
	public Adenda modificarAdenda(Adenda pAdenda)throws BusinessException{
		Adenda dto = null;
		try{
			dto = adendaService.modificarAdenda(pAdenda);
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
	
	public Adenda getAdendaPorIdAdenda(AdendaId pk) throws BusinessException{
		Adenda adenda = null;
		try{
			adenda = adendaService.getAdendaPorIdAdenda(pk);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adenda;
	}

	@Override
	/*public List<Adenda> listarAdendaPorPK(AdendaId pPK) throws BusinessException {
		List<Adenda> lista = new ArrayList<Adenda>();;
		
		try{
			//ConvenioBO CBO=new ConvenioBO();
			lista = CBO.getListaConvenioPorPK(id);
			System.out.println("CBO.getConvenioPorPK(id) = "+CBO.getConvenioPorPK(id));
			System.out.println("CBO.getConvenioPorPK(id).size() = "+CBO.getListaConvenioPorPK(id).size());
		}catch(Exception e) {
		}
		return lista;
	}*/
	
	/*public List<Archivo> grabarListaArchivoDeAdjunto(List<Adjunto> lista, String strCartaPresentacion, String strConvenioSugerido, String strAdendaSugerida) throws BusinessException{
		List<Archivo> listaArchivo = null;
		List<Adjunto> listaAdjunto = null;
		try{
			listaArchivo = adendaService.grabarListaArchivoDeAdjunto(lista, strCartaPresentacion, strConvenioSugerido, strAdendaSugerida);
			listaAdjunto = new ArrayList<Adjunto>();
			for(Adjunto adjunto : lista){
				for(Archivo archivo :listaArchivo){
					if(adjunto.getIntParaTipoArchivoCod().equals(archivo.getId().getIntParaTipoCod())){
						adjunto.setIntItemArchivo(archivo.getId().getIntItemArchivo());
						adjunto.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
						listaAdjunto.add(adjunto);
					}
				}
			}
			//CGDWTF- reemplazar nulo x id de captacion
			listaAdjunto = adendaService.grabarListaDinamicaAdendaAdjunta(listaAdjunto, null);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			log.error("error al grabar", e);
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			log.error("error al grabar", e);
			throw new BusinessException(e);
		}
		return listaArchivo;
	}*/
	
	public List<Archivo> grabarListaArchivoDeAdjunto(List<Adjunto> lista, String strCartaPresentacion, String strConvenioSugerido, String strAdendaSugerida, AdendaId Pk) throws BusinessException{
		List<Archivo> listaArchivo = null;
		List<Adjunto> listaAdjunto = null;
		try{
			listaArchivo = adendaService.grabarListaArchivoDeAdjunto(lista, strCartaPresentacion, strConvenioSugerido, strAdendaSugerida);
			listaAdjunto = new ArrayList<Adjunto>();
			for(Adjunto adjunto : lista){
				for(Archivo archivo :listaArchivo){
					if(adjunto.getIntParaTipoArchivoCod().equals(archivo.getId().getIntParaTipoCod())){
						adjunto.setIntItemArchivo(archivo.getId().getIntItemArchivo());
						adjunto.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
						listaAdjunto.add(adjunto);
					}
				}
			}
			//CGDWTF- reemplazar nulo x id de captacion
			listaAdjunto = adendaService.grabarListaDinamicaAdendaAdjunta(listaAdjunto, Pk);
			
		}catch(BusinessException e){
			context.setRollbackOnly();
			log.error("error al grabar", e);
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			log.error("error al grabar", e);
			throw new BusinessException(e);
		}
		return listaArchivo;
	}
	
	public Adenda eliminarAdenda(Adenda o) throws BusinessException{
		Adenda adenda = null;
		try{
			adenda = boAdenda.eliminarAdenda(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return adenda;
	}
	
	public Adenda aprobarRechazarAdenda(Adenda o) throws BusinessException{
		Adenda adenda = null;
		try{
			adenda = boAdenda.aprobarRechazarAdenda(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return adenda;
	}
	
	public List<ConvenioEstructuraDetalleComp> getListConvenioEstructuraDetalle(AdendaId o) throws BusinessException{
		List<ConvenioEstructuraDetalleComp> lista = null;
		try{
			lista = convenioDetalleService.getListaConvenioEstructuraDetalleCompPorPkConvenio(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Persona> getListaRepLegalPorIdPerNaturalYIdPerJuridica(Integer intIdPersona,Integer intTipoVinculo,Integer intIdEmpresaSistema) throws BusinessException{
		List<Persona> lista = null;
		try{
			lista = adendaService.getListaRepLegalPorIdPerNaturalYIdPerJuridica(intIdPersona, intTipoVinculo, intIdEmpresaSistema);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Adenda grabarConvenio(Adenda pAdenda)throws BusinessException{
		Adenda dto = null;
		try{
			dto = adendaService.grabarConvenio(pAdenda);
			
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
	
	public Adenda modificarConvenio(Adenda pAdenda)throws BusinessException{
		Adenda dto = null;
		try{
			dto = adendaService.modificarConvenio(pAdenda);
			
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
	
	public Adenda getConvenioPorIdConvenio(AdendaId pk) throws BusinessException{
		Adenda adenda = null;
		try{
			adenda = adendaService.getConvenioPorIdConvenio(pk);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adenda;
	}
}
