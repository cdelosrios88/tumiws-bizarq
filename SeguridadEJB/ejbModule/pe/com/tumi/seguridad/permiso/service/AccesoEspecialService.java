package pe.com.tumi.seguridad.permiso.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.permiso.bo.AccesoEspecialBO;
import pe.com.tumi.seguridad.permiso.bo.AccesoEspecialDetalleBO;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecial;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecialDetalle;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecialDetalleId;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalle;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalleId;

public class AccesoEspecialService {

	protected static Logger log = Logger.getLogger(AccesoEspecialService.class);	
	private AccesoEspecialBO boAccesoEspecial = (AccesoEspecialBO)TumiFactory.get(AccesoEspecialBO.class);	
	private AccesoEspecialDetalleBO boAccesoEspecialDetalle = (AccesoEspecialDetalleBO)TumiFactory.get(AccesoEspecialDetalleBO.class);	
	
	public AccesoEspecial grabarAccesosEspeciales(AccesoEspecial accesoEspecial, List <AccesoEspecialDetalle>listaAccesoEspecialDetalle) 
		throws BusinessException{
		try{
			log.info(accesoEspecial);
			boAccesoEspecial.grabar(accesoEspecial);
			log.info(accesoEspecial);
			for(AccesoEspecialDetalle accEspDet : listaAccesoEspecialDetalle ){
				accEspDet.getId().setIntItemAcceso(accesoEspecial.getIntItemAcceso());
				log.info(accEspDet);
				boAccesoEspecialDetalle.grabar(accEspDet);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return accesoEspecial;
	}
	
	public List<AccesoEspecial> buscarAccesosEspeciales(AccesoEspecial accesoEspecial) 
		throws BusinessException{

		List<AccesoEspecial> lista = null;
		try {
			PersonaFacadeRemote facadePersona = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			lista = boAccesoEspecial.getPorBusqueda(accesoEspecial);
			for(AccesoEspecial da : lista){
				log.info("encontro:"+da);				
			}
			int i = 0;
			while(i<lista.size()){
				lista.get(i).setJuridica(facadePersona.getJuridicaPorPK(lista.get(i).getIntPersEmpresa()));
				lista.get(i).setNaturalAutoriza(facadePersona.getNaturalPorPK(lista.get(i).getIntPersPersonaAutoriza()));
				lista.get(i).setNaturalOpera(facadePersona.getNaturalPorPK(lista.get(i).getIntPersPersonaOpera()));
				i++;
			}
			
		} catch (BusinessException e) {
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public AccesoEspecial eliminarAccesoEspecial(AccesoEspecial accesoEspecial) 
		throws BusinessException{
		List <AccesoEspecialDetalle> lista = null;
		try{
			accesoEspecial.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			boAccesoEspecial.modificar(accesoEspecial);
			/*lista = boAccesoEspecialDetalle.getPorCabecera(accesoEspecial);
			for(AccesoEspecialDetalle accEspDet : lista ){
				accEspDet.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boAccesoEspecialDetalle.modificar(accEspDet);
			}*/
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return accesoEspecial;
	}
	
	public AccesoEspecial modificarAccesoEspecialYDetalle(AccesoEspecial accesoEspecial, List<AccesoEspecialDetalle> listaAccesoEspecialDetalle) 
		throws BusinessException{
	
		List<AccesoEspecialDetalle> listaBD = null;
		try {
			log.info("accesoEspecial antes:"+accesoEspecial);
			accesoEspecial = boAccesoEspecial.modificar(accesoEspecial);		
			log.info("da despu:"+accesoEspecial);
			listaBD = boAccesoEspecialDetalle.getPorCabecera(accesoEspecial);
			//Obtenemos un set con los ids de los dias de los detalles que existen en la bd
			Set<Integer> conjuntoDiasExistentes = new HashSet<Integer>();
			for(AccesoEspecialDetalle dad : listaBD){
				conjuntoDiasExistentes.add(dad.getId().getIntIdDiaSemana());
			}
			//Comparamos si lo seleccionado en la interfaz corresponde con lo q hay en la bd.
			//Si existe en la bd se modifica, sino se graba.
			for(AccesoEspecialDetalle dad : listaAccesoEspecialDetalle){
				if(conjuntoDiasExistentes.contains(dad.getId().getIntIdDiaSemana())){
					log.info("modificar "+dad);
					boAccesoEspecialDetalle.modificar(dad);
					conjuntoDiasExistentes.remove(dad.getId().getIntIdDiaSemana());
				}else{
					dad.getId().setIntItemAcceso(accesoEspecial.getIntItemAcceso());
					log.info("grabar    "+dad);
					boAccesoEspecialDetalle.grabar(dad);
				}
			}
			//Los detalles que han sido deseleccionados en la interfaz pasan a estado anulado.
			Iterator<Integer> iter = conjuntoDiasExistentes.iterator();
			AccesoEspecialDetalleId dadId = new AccesoEspecialDetalleId();
			AccesoEspecialDetalle dad = null;
			while (iter.hasNext()) {
				//log.info("conjuntoDiasExistentes:"+iter.next());
				dadId.setIntIdDiaSemana(iter.next());
				dadId.setIntItemAcceso(accesoEspecial.getIntItemAcceso());
				dad = boAccesoEspecialDetalle.getPorPk(dadId);
				log.info("dad noselec antes:"+dad);				
				dad.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				dad = boAccesoEspecialDetalle.modificar(dad);
				log.info("dad noselec despu:"+dad);
			}
		} catch (BusinessException e) {
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return accesoEspecial;
	}
	
	public boolean validarAccesoPorEmpresaUsuario(AccesoEspecial o)	throws BusinessException{
			
		boolean esPermitido = false;
		
		try{
			List<AccesoEspecial> lista = null;
			lista = boAccesoEspecial.getAccesoPorEmpresaUsuario(o);
			
			if(!lista.isEmpty() && lista.size() > 0)
				esPermitido = true;				
			
		} catch (BusinessException e) {
			throw e;
		
		} catch(Exception e){
			throw new BusinessException(e);
		}
		
		return esPermitido;
	}
	
}