package pe.com.tumi.seguridad.permiso.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.permiso.bo.DiasAccesosBO;
import pe.com.tumi.seguridad.permiso.bo.DiasAccesosDetalleBO;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalle;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalleId;

public class HorarioService {

	private DiasAccesosBO boDiasAccesos = (DiasAccesosBO)TumiFactory.get(DiasAccesosBO.class);
	private DiasAccesosDetalleBO boDiasAccesosDetalle = (DiasAccesosDetalleBO)TumiFactory.get(DiasAccesosDetalleBO.class);
	protected static Logger log = Logger.getLogger(HorarioService.class);	
	
	public DiasAccesos grabarDiasAccesos(DiasAccesos diasAccesos) 
		throws BusinessException{
		
		DiasAccesos da = null;
		try {
			da = boDiasAccesos.grabar(diasAccesos);
		} catch (BusinessException e) {
			throw e;
		}
		return da;
	}
	
	public DiasAccesos grabarDiasAccesosYDetalle(DiasAccesos diasAccesos, List<DiasAccesosDetalle> listaDiasAccesosDetalle) 
		throws BusinessException{
		
		DiasAccesos da = null;
		try {
			da = boDiasAccesos.grabar(diasAccesos);
			for(DiasAccesosDetalle dad : listaDiasAccesosDetalle){
				dad.getId().setIntItemDiasAccesos(da.getId().getIntItemDiaAccesos());
				log.info(dad);
				boDiasAccesosDetalle.grabar(dad);
			}
		} catch (BusinessException e) {
			throw e;
		}
		return da;
	}
	
	public List<DiasAccesos> buscarDiasAccesosPorTipoSucursalYEstadoYEmpresa(DiasAccesos diasAccesosFiltro) 
		throws BusinessException{
	
		List<DiasAccesos> lista = null;
		try {
			lista = boDiasAccesos.getListaPorTipoSucursalYEstado(diasAccesosFiltro);
			for(DiasAccesos da : lista){
				log.info("encontro:"+da);
			}
			lista = cargarJuridica(lista);
			/*if(diasAccesosFiltro.getJuridica().getStrSiglas()!=null && diasAccesosFiltro.getJuridica().getStrSiglas().length()>0){
				List<DiasAccesos> listaAux = new ArrayList<DiasAccesos>();
				for(DiasAccesos da : lista){
					if(da.getJuridica().getStrSiglas().toUpperCase().contains(diasAccesosFiltro.getJuridica().getStrSiglas().toUpperCase())){
						listaAux.add(da);
					}
				}
				lista= listaAux;
			}*/
			
		} catch (BusinessException e) {
			throw e;
		} catch (EJBFactoryException e){
			throw new BusinessException(e);
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	public DiasAccesos eliminarDiasAccesos(DiasAccesos diasAccesos) 
		throws BusinessException{
	
		List<DiasAccesosDetalle> lista = null;
		try {
			diasAccesos.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			log.info("antes:"+diasAccesos);
			diasAccesos = boDiasAccesos.modificar(diasAccesos);
			log.info("despu:"+diasAccesos);
			/*lista = boDiasAccesosDetalle.getListaPorCabecera(diasAccesos);
			for(DiasAccesosDetalle dad : lista){
				log.info("antes:"+dad);
				dad.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				dad = boDiasAccesosDetalle.modificar(dad);
				log.info("despu:"+dad);
			}*/			
		} catch (BusinessException e) {
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return diasAccesos;
	}

	public List<DiasAccesos> cargarJuridica(List<DiasAccesos> listaDiasAccesos) 
		throws BusinessException, EJBFactoryException{
		
		try {
			int i = 0;
			PersonaFacadeRemote facadePersona = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			while(i<listaDiasAccesos.size()){
				Juridica juridica = facadePersona.getJuridicaPorPK(listaDiasAccesos.get(i).getId().getIntPersEmpresa());
				listaDiasAccesos.get(i).setJuridica(juridica);
				i++;
			}
		} catch (BusinessException e) {
			throw e;
		} catch (EJBFactoryException e) {
			throw e;
		}
		return listaDiasAccesos;
	}
	
	public DiasAccesos modificarDiasAccesosYDetalle(DiasAccesos diasAccesos, List<DiasAccesosDetalle> listaDiasAccesosDetalle) 
		throws BusinessException{
	
		List<DiasAccesosDetalle> listaBD = null;
		try {
			log.info("da antes:"+diasAccesos);
			diasAccesos = boDiasAccesos.modificar(diasAccesos);		
			log.info("da despu:"+diasAccesos);
			listaBD = boDiasAccesosDetalle.getListaPorCabecera(diasAccesos);
			//Obtenemos un set con los ids de los dias de los detalles que existen en la bd
			Set<Integer> conjuntoDiasExistentes = new HashSet<Integer>();
			for(DiasAccesosDetalle dad : listaBD){
				conjuntoDiasExistentes.add(dad.getId().getIntIdDiaSemana());
			}
			//Comparamos si lo seleccionado en la interfaz corresponde con lo q hay en la bd.
			//Si existe en la bd se modifica, sino se graba.
			for(DiasAccesosDetalle dad : listaDiasAccesosDetalle){
				if(conjuntoDiasExistentes.contains(dad.getId().getIntIdDiaSemana())){
					log.info("modificar "+dad);
					boDiasAccesosDetalle.modificar(dad);
					conjuntoDiasExistentes.remove(dad.getId().getIntIdDiaSemana());
				}else{
					dad.getId().setIntItemDiasAccesos(diasAccesos.getId().getIntItemDiaAccesos());
					log.info("grabar    "+dad);
					boDiasAccesosDetalle.grabar(dad);
				}
			}
			//Los detalles que han sido deseleccionados en la interfaz pasan a estado anulado.
			Iterator<Integer> iter = conjuntoDiasExistentes.iterator();
			DiasAccesosDetalleId dadId = new DiasAccesosDetalleId();
			DiasAccesosDetalle dad = null;
			while (iter.hasNext()) {
				//log.info("conjuntoDiasExistentes:"+iter.next());
				dadId.setIntIdDiaSemana(iter.next());
				dadId.setIntIdTipoSucursal(diasAccesos.getId().getIntIdTipoSucursal());
				dadId.setIntItemDiasAccesos(diasAccesos.getId().getIntItemDiaAccesos());
				dadId.setIntPersEmpresa(diasAccesos.getId().getIntPersEmpresa());
				dad = boDiasAccesosDetalle.getListaPorPk(dadId);
				log.info("dad noselec antes:"+dad);				
				dad.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				dad = boDiasAccesosDetalle.modificar(dad);
				log.info("dad noselec despu:"+dad);
			}
		} catch (BusinessException e) {
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return diasAccesos;
	}	
		
	public boolean validarAccesoPorEmpresaYSucursal(DiasAccesos diasAccesos) throws BusinessException{
			
		boolean esPermitido = false;
		
		try{
			List<DiasAccesos> lista = null;
			lista = boDiasAccesos.getAccesoPorEmpresaYSucursal(diasAccesos);
			
			log.info("ingresa");
			if(!lista.isEmpty() && lista.size() > 0){
				log.info("tiene Informacion");
				esPermitido = true;
			}
			
		} catch (BusinessException e) {
			throw e;
		
		} catch(Exception e){
			throw new BusinessException(e);
		}
		
		return esPermitido;
	}
}

