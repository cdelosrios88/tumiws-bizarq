package pe.com.tumi.tesoreria.egreso.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.tesoreria.egreso.bo.SolicitudPersonalBO;
import pe.com.tumi.tesoreria.egreso.bo.SolicitudPersonalDetalleBO;
import pe.com.tumi.tesoreria.egreso.bo.SolicitudPersonalPagoBO;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalDetalle;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalPago;

public class SolicitudPersonalService {
	
	protected static Logger log = Logger.getLogger(SaldoService.class);
	SolicitudPersonalBO boSolicitudPersonal = (SolicitudPersonalBO)TumiFactory.get(SolicitudPersonalBO.class);
	SolicitudPersonalDetalleBO boSolicitudPersonalDetalle = (SolicitudPersonalDetalleBO)TumiFactory.get(SolicitudPersonalDetalleBO.class);
	SolicitudPersonalPagoBO boSolicitudPersonalPago = (SolicitudPersonalPagoBO)TumiFactory.get(SolicitudPersonalPagoBO.class);
	
	public SolicitudPersonal grabarSolicitudPersonal(SolicitudPersonal solicitudPersonal) throws BusinessException{
		try{
			log.info(solicitudPersonal);
			solicitudPersonal = boSolicitudPersonal.grabar(solicitudPersonal);
			
			for(SolicitudPersonalDetalle solicitudPersonalDetalle : solicitudPersonal.getListaSolicitudPersonalDetalle()){
				grabarSolicitudPersonalDetalle(solicitudPersonalDetalle, solicitudPersonal);
			}
			
			for(SolicitudPersonalPago solicitudPersonalPago : solicitudPersonal.getListaSolicitudPersonalPago()){
				grabarSolicitudPersonalPago(solicitudPersonalPago, solicitudPersonal);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return solicitudPersonal;
	}
	
	public List<SolicitudPersonal> buscarSolicitudPersonal(SolicitudPersonal solicitudPersonalFiltro, List<Persona> listaPersonaFiltro) 
	throws BusinessException{
		List<SolicitudPersonal> listaSolicitudPersonal = new ArrayList<SolicitudPersonal>();
		try{
			log.info(solicitudPersonalFiltro);
			listaSolicitudPersonal = boSolicitudPersonal.getListaParaBuscar(solicitudPersonalFiltro);
			for(SolicitudPersonal solicitudPersonal : listaSolicitudPersonal)
				solicitudPersonal.setListaSolicitudPersonalDetalle(boSolicitudPersonalDetalle.getPorSolicitudPersonal(solicitudPersonal));
			
			
			if(listaPersonaFiltro!=null && !listaPersonaFiltro.isEmpty()){
				List<SolicitudPersonal> listaTemp = new ArrayList<SolicitudPersonal>();
				if(solicitudPersonalFiltro.getIntTipoBusqueda().equals(Constante.BUSCAR_SOLICITUDPP)){
					for(SolicitudPersonal solicitudPersonal : listaSolicitudPersonal)
						for(Persona persona : listaPersonaFiltro)
							if(solicitudPersonal.getIntPersPersonaGiro().equals(persona.getIntIdPersona())){
								listaTemp.add(solicitudPersonal);
								break;
							}
				
				}else if(solicitudPersonalFiltro.getIntTipoBusqueda().equals(Constante.BUSCAR_DETALLESOLICITUDPP)){
					for(SolicitudPersonal solicitudPersonal : listaSolicitudPersonal)
						for(SolicitudPersonalDetalle solicitudPersonalDetalle : solicitudPersonal.getListaSolicitudPersonalDetalle()){
							for(Persona persona : listaPersonaFiltro){
								if(solicitudPersonalDetalle.getIntPersPersonaAbonado().equals(persona.getIntIdPersona())){
									listaTemp.add(solicitudPersonal);
									break;
								}
							}
							if(listaTemp.contains(solicitudPersonal))
								break;
						}
				}
				listaSolicitudPersonal = listaTemp;
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSolicitudPersonal;
	}
	
	private void grabarSolicitudPersonalDetalle(SolicitudPersonalDetalle solicitudPersonalDetalle, SolicitudPersonal solicitudPersonal) 
	throws Exception{
		solicitudPersonalDetalle.getId().setIntPersEmpresa(solicitudPersonal.getId().getIntPersEmpresa());
		solicitudPersonalDetalle.getId().setIntItemSolicitudPersonal(solicitudPersonal.getId().getIntItemSolicitudPersonal());
		solicitudPersonalDetalle.setIntParaDocumentoGeneral(solicitudPersonal.getIntParaDocumentoGeneral());
		log.info(solicitudPersonalDetalle);
		boSolicitudPersonalDetalle.grabar(solicitudPersonalDetalle);
	}
	
	private void grabarSolicitudPersonalPago(SolicitudPersonalPago solicitudPersonalPago, SolicitudPersonal solicitudPersonal) 
	throws Exception{
		solicitudPersonalPago.getId().setIntPersEmpresa(solicitudPersonal.getId().getIntPersEmpresa());
		solicitudPersonalPago.getId().setIntItemSolicitudPersonal(solicitudPersonal.getId().getIntItemSolicitudPersonal());
		log.info(solicitudPersonalPago);
		boSolicitudPersonalPago.grabar(solicitudPersonalPago);
	}
	
	public SolicitudPersonal modificarSolicitudPersonal(SolicitudPersonal solicitudPersonal) throws BusinessException{
		try{
			log.info(solicitudPersonal);
			boSolicitudPersonal.modificar(solicitudPersonal);
			
			List<SolicitudPersonalDetalle> listaBD = boSolicitudPersonalDetalle.getPorSolicitudPersonal(solicitudPersonal);
			List<SolicitudPersonalDetalle> listaIU = new ArrayList<SolicitudPersonalDetalle>();
			for(SolicitudPersonalDetalle solicitudPersonalDetalle : solicitudPersonal.getListaSolicitudPersonalDetalle()){
				if(solicitudPersonalDetalle.getId().getIntItemSolicitudPersonalDetalle()==null)					
					grabarSolicitudPersonalDetalle(solicitudPersonalDetalle, solicitudPersonal);
				else
					listaIU.add(solicitudPersonalDetalle);
			}
			
			for(SolicitudPersonalDetalle solicitudPersonalDetalleBD : listaBD){
				boolean seEncuentraEnIU = Boolean.FALSE;
				for(SolicitudPersonalDetalle solicitudPersonalDetalleIU : listaIU){
					if(solicitudPersonalDetalleBD.getId().getIntItemSolicitudPersonalDetalle().equals(
						solicitudPersonalDetalleIU.getId().getIntItemSolicitudPersonalDetalle())){
						seEncuentraEnIU = Boolean.TRUE;
						break;
					}
				}
				if(!seEncuentraEnIU){
					log.info(solicitudPersonalDetalleBD);
					boSolicitudPersonalDetalle.eliminar(solicitudPersonalDetalleBD);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return solicitudPersonal;
	}	
}