package pe.com.tumi.credito.socio.estructura.service;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.estructura.bo.ConvenioDetalleBO;
import pe.com.tumi.credito.socio.estructura.bo.EstructuraDetalleBO;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;


public class EstructuraDetalleService {
	protected  	static Logger log = Logger.getLogger(EstructuraService.class);
	private EstructuraDetalleBO boEstructuraDetalle = (EstructuraDetalleBO)TumiFactory.get(EstructuraDetalleBO.class);
	private ConvenioDetalleBO boConvenioDetalle = (ConvenioDetalleBO)TumiFactory.get(ConvenioDetalleBO.class);
	
	public List<EstructuraDetalle> grabarListaDinamicaDetalle(List<EstructuraDetalle> lista, EstructuraId pk, Integer pIntCaso) throws BusinessException{
		EstructuraDetalle dto = null;
		EstructuraDetalle dtoTemp = null;
		List<EstructuraDetalle> listaAux = null;
		String strIds = "";
		
		try{
			//Obtiene la EstructuraDetalle ya registra
			listaAux = boEstructuraDetalle.getListaEstructuraDetallePorEstructuraPK(pk);
			
			//Registra o actualiza la EstructuraDetalle desde el formulario
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				if(dto.getId().getIntItemCaso() == null){
					dto.getId().setIntCodigo(pk.getIntCodigo());
					dto = boEstructuraDetalle.grabarEstructuraDetalle(dto);
				}else{
					dtoTemp = boEstructuraDetalle.getEstructuraDetallePorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntCodigo(pk.getIntCodigo());
						dto = boEstructuraDetalle.grabarEstructuraDetalle(dto);
					}else{
						dto = boEstructuraDetalle.modificarEstructuraDetalle(dto);
						strIds = lista.get(i).getId().getIntItemCaso()+"/"+strIds;
					}
				}
				log.info("Se grabó estructuraDetalle(itemCaso): "+dto.getId().getIntItemCaso());
				log.info("dto.getIntParaEstadoCod(): "+dto.getIntParaEstadoCod());
			}
			
			//Anula la EstructuraDetalle que se obtuvo si se quitó desde el formulario
			log.info("listaAux.size(): "+listaAux.size());
			log.info("strIds: "+strIds);
			for(int i=0; i<listaAux.size(); i++){
				dto = listaAux.get(i);
				if(listaAux.get(i).getId().getIntCaso().equals(pIntCaso) && 
						!strIds.contains(listaAux.get(i).getId().getIntItemCaso().toString()+"/")){
					dto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					dto = boEstructuraDetalle.modificarEstructuraDetalle(dto);
				}
				log.info("Se grabó estructuraDetalle(itemCaso): "+dto.getId().getIntItemCaso());
				log.info("dto.getIntParaEstadoCod(): "+dto.getIntParaEstadoCod());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ConvenioEstructuraDetalleComp> getConvenioDetallePorEstructuraDet(ConvenioEstructuraDetalleComp pId) throws BusinessException{
		List<ConvenioEstructuraDetalleComp> lista = null;
		Juridica juridica = null;
		try{
			lista = boConvenioDetalle.getConvenioEstructuraDetallePorEstructuraDet(pId);
			if(lista!=null){
				PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				for(ConvenioEstructuraDetalleComp domain : lista){
					juridica = facade.getJuridicaPorPK(domain.getEstructura().getIntPersPersonaPk());
					log.info("juridica.intIdPersona: "+juridica.getIntIdPersona());
					domain.getEstructura().setJuridica(juridica);
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