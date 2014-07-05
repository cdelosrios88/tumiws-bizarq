package pe.com.tumi.contabilidad.cierre.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.AnexoDetalleException;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.bo.AnexoBO;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleBO;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleCuentaBO;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleOperadorBO;
import pe.com.tumi.contabilidad.cierre.bo.RatioBO;
import pe.com.tumi.contabilidad.cierre.bo.RatioDetalleBO;
import pe.com.tumi.contabilidad.cierre.domain.Anexo;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleOperador;
import pe.com.tumi.contabilidad.cierre.domain.Ratio;
import pe.com.tumi.contabilidad.cierre.domain.RatioDetalle;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class AnexoService {

	protected static Logger log = Logger.getLogger(AnexoService.class);
	
	AnexoBO boAnexo = (AnexoBO)TumiFactory.get(AnexoBO.class);
	AnexoDetalleBO boAnexoDetalle = (AnexoDetalleBO)TumiFactory.get(AnexoDetalleBO.class);
	AnexoDetalleOperadorBO boAnexoDetalleOperador = (AnexoDetalleOperadorBO)TumiFactory.get(AnexoDetalleOperadorBO.class);
	AnexoDetalleCuentaBO boAnexoDetalleCuenta = (AnexoDetalleCuentaBO)TumiFactory.get(AnexoDetalleCuentaBO.class);
	RatioBO boRatio = (RatioBO)TumiFactory.get(RatioBO.class);
	RatioDetalleBO boRatioDetalle = (RatioDetalleBO)TumiFactory.get(RatioDetalleBO.class);
	
	
	public List<Anexo> buscarListaAnexo(Anexo anexoFiltro)throws BusinessException{
		List<Anexo> listaAnexo = null;
		try{
			listaAnexo = boAnexo.getPorBuscar(anexoFiltro);
			
			if(anexoFiltro.getStrAnexoDetalleBusqueda()==null || anexoFiltro.getStrAnexoDetalleBusqueda().isEmpty()){
				return listaAnexo;
			}
			
			List<AnexoDetalle> listaAnexoDetalle;
			List<Anexo> listaAnexoTemp = new ArrayList<Anexo>();
			boolean poseeMatch = Boolean.FALSE;
			String concatenadoTextoPadre = "";
			
			for(Anexo anexo : listaAnexo){
				poseeMatch = Boolean.FALSE;
				concatenadoTextoPadre = "";
				listaAnexoDetalle = boAnexoDetalle.getPorAnexo(anexo);
				for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
					if(anexoDetalle.getStrTexto().toUpperCase().contains(anexoFiltro.getStrAnexoDetalleBusqueda().toUpperCase())){
						poseeMatch = Boolean.TRUE;
						concatenadoTextoPadre = iniciarGenerarTextoConcatenadoPadre(anexoDetalle,listaAnexoDetalle);
						concatenadoTextoPadre = (concatenadoTextoPadre + " \\ " + anexoDetalle.getStrTexto()).substring(2);
						anexo.getListaDescripciones().add(concatenadoTextoPadre);
					}
				}
				if(poseeMatch){
					listaAnexoTemp.add(anexo);
				}				
			}
			
			//para evitar repeticiones en contenido en los textos seleccionados
			boolean seEncuentraContenida = Boolean.FALSE;
			List<String> listaDescripcionesTemp;
			for(Anexo anexo : listaAnexoTemp){
				listaDescripcionesTemp = new ArrayList<String>();
				for(String descripcion : anexo.getListaDescripciones()){
					seEncuentraContenida = Boolean.FALSE;
					for(String descripcion2 : anexo.getListaDescripciones()){
						if(descripcion2.contains(descripcion) && !descripcion2.equals(descripcion)){
							seEncuentraContenida = Boolean.TRUE;
							break;
						}
					}
					if(!seEncuentraContenida){
						listaDescripcionesTemp.add(descripcion);
					}
				}
				anexo.setListaDescripciones(listaDescripcionesTemp);
			}
			listaAnexo = listaAnexoTemp;			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaAnexo;
	}
	
	private String iniciarGenerarTextoConcatenadoPadre(AnexoDetalle anexoDetalle, List<AnexoDetalle> listaAnexoDetalle){
		return generarTextoConcatenadoPadre(anexoDetalle,listaAnexoDetalle);
	}
	
	private String generarTextoConcatenadoPadre(AnexoDetalle anexoDetalle, List<AnexoDetalle> listaAnexoDetalle){
		String jerarquiaTexto = "";
		AnexoDetalle anexoDetallePadre;
		
		anexoDetallePadre = obtenerPadre(anexoDetalle,listaAnexoDetalle); 
		
		if(anexoDetallePadre!=null){
			
			jerarquiaTexto = generarTextoConcatenadoPadre(anexoDetallePadre,listaAnexoDetalle);
			
			jerarquiaTexto = jerarquiaTexto + " \\ " + anexoDetallePadre.getStrTexto();
						
			return jerarquiaTexto;
		}else{
			return "";
		}		
		
	}
	
	private AnexoDetalle obtenerPadre(AnexoDetalle anexoDetalle, List<AnexoDetalle> listaAnexoDetalle){
		for(AnexoDetalle anexoDet : listaAnexoDetalle){
			if(anexoDet.getIntNivel().equals(anexoDetalle.getIntNivelPadre()) 
				&& anexoDet.getIntPosicion().equals(anexoDetalle.getIntPosicionPadre())
				&& anexoDet.getIntItem().equals(anexoDetalle.getIntItemPadre())){
				return anexoDet;
			}
		}
		return null;
	}
	
	public Anexo grabarAnexo(Anexo anexo) throws BusinessException{
		try{
			
			log.info(anexo);
			boAnexo.grabar(anexo);
			
			grabarAnexoDetalle(anexo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return anexo;
	}
	
	private Anexo grabarAnexoDetalle(Anexo anexo) throws BusinessException{
		try{
			List<AnexoDetalle> listaAnexoDetalle = anexo.getListaAnexoDetalle();
			List<AnexoDetalleOperador> listaAnexoDetalleOperador;
			List<AnexoDetalleCuenta> listaAnexoDetalleCuenta;
			
			for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
				anexoDetalle.getId().setIntPersEmpresaAnexo(anexo.getId().getIntPersEmpresaAnexo());
				anexoDetalle.getId().setIntContPeriodoAnexo(anexo.getId().getIntContPeriodoAnexo());
				anexoDetalle.getId().setIntParaTipoAnexo(anexo.getId().getIntParaTipoAnexo());
				
				if(anexoDetalle.getAnexoDetalleReferencia()!=null){
					AnexoDetalle referencia = anexoDetalle.getAnexoDetalleReferencia();
					anexoDetalle.setIntPersEmpresaAnexoRef(referencia.getId().getIntPersEmpresaAnexo());
					anexoDetalle.setIntContPeriodoAnexoRef(referencia.getId().getIntContPeriodoAnexo());
					anexoDetalle.setIntParaTipoAnexoRef(referencia.getId().getIntParaTipoAnexo());
					anexoDetalle.setIntAndeItemAnexoDetalleRef(referencia.getId().getIntItemAnexoDetalle());
				}
				
				log.info(anexoDetalle);
				boAnexoDetalle.grabar(anexoDetalle);
				
				listaAnexoDetalleCuenta = anexoDetalle.getListaAnexoDetalleCuenta();
				for(AnexoDetalleCuenta anexoDetalleCuenta : listaAnexoDetalleCuenta){
					anexoDetalleCuenta.getId().setIntPersEmpresaAnexo(anexoDetalle.getId().getIntPersEmpresaAnexo());
					anexoDetalleCuenta.getId().setIntContPeriodoAnexo(anexoDetalle.getId().getIntContPeriodoAnexo());
					anexoDetalleCuenta.getId().setIntParaTipoAnexo(anexoDetalle.getId().getIntParaTipoAnexo());
					anexoDetalleCuenta.getId().setIntItemAnexoDetalle(anexoDetalle.getId().getIntItemAnexoDetalle());					
					log.info(anexoDetalleCuenta);
					boAnexoDetalleCuenta.grabar(anexoDetalleCuenta);
				}
			}
			
			
			Integer intItemAnexoDetalleBD;
			
			for(AnexoDetalle anexoDetalle : listaAnexoDetalle){
				listaAnexoDetalleOperador = anexoDetalle.getListaAnexoDetalleOperador();
				
				for(AnexoDetalleOperador anexoDetalleOperador : listaAnexoDetalleOperador){
					anexoDetalleOperador.getId().setIntPersEmpresaAnexo(anexo.getId().getIntPersEmpresaAnexo());
					anexoDetalleOperador.getId().setIntContPeriodoAnexo(anexo.getId().getIntContPeriodoAnexo());
					anexoDetalleOperador.getId().setIntParaTipoAnexo(anexo.getId().getIntParaTipoAnexo());
					anexoDetalleOperador.getId().setIntItemAnexoDetalle(anexoDetalle.getId().getIntItemAnexoDetalle());	
					
					intItemAnexoDetalleBD = buscarAnexoDetalle(anexoDetalleOperador.getAnexoDetalleReferencia(),listaAnexoDetalle).getId().getIntItemAnexoDetalle(); 
					
					anexoDetalleOperador.setIntItemAnexoDetalleOperador(intItemAnexoDetalleBD);					
					log.info(anexoDetalleOperador);
					boAnexoDetalleOperador.grabar(anexoDetalleOperador);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return anexo;
	}
	
	private AnexoDetalle buscarAnexoDetalle(AnexoDetalle anexoDetalle, List<AnexoDetalle> listaAnexoDetalle){
		for(AnexoDetalle anexoDet : listaAnexoDetalle){
			if(anexoDet.getIntNivel().equals(anexoDetalle.getIntNivel()) 
				&& anexoDet.getIntPosicion().equals(anexoDetalle.getIntPosicion())
				&& anexoDet.getIntItem().equals(anexoDetalle.getIntItem())){
				return anexoDet;
			}
		}
		return null;
	}
	
	public Anexo eliminarAnexo(Anexo anexo) throws BusinessException, AnexoDetalleException{
		try{

			log.info("eliminar:"+anexo);
			anexo.setListaAnexoDetalle(boAnexoDetalle.getPorAnexo(anexo));
			
			for(AnexoDetalle anexoDetalle : anexo.getListaAnexoDetalle()){
				eliminarAnexoDetalle(anexoDetalle,anexo);
			}
			boAnexo.eliminar(anexo);
		}catch(AnexoDetalleException e){
			throw e;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return anexo;
	}
	
	
	private void eliminarAnexoDetalle(AnexoDetalle anexoDetalle, Anexo anexo) throws BusinessException, AnexoDetalleException{
		try{
			List<AnexoDetalleOperador> listaAnexoDetalleOperador;
			List<AnexoDetalleCuenta> listaAnexoDetalleCuenta;
			List<RatioDetalle> listaRatioDetalle;
			
			log.info("eliminar:"+anexoDetalle);
				
				listaAnexoDetalleCuenta = boAnexoDetalleCuenta.getPorAnexoDetalle(anexoDetalle);
				if(anexo.getIntParaTipoLibroAnexo().equals(Constante.TIPO_LIBRO)){
					//validación con AnexoDetalleCuenta
					if(listaAnexoDetalleCuenta!=null && !listaAnexoDetalleCuenta.isEmpty()){
						throw new AnexoDetalleException("No se puede realizar la operación. El elemento '"+anexoDetalle.getStrTexto()+"' " +
						"posee asociada una o más cuentas, por lo que no puede ser eliminado.");
					}
					
					//validación con RatioDetalle
					listaRatioDetalle = boRatioDetalle.getPorAnexoDetalle(anexoDetalle);
					if(listaRatioDetalle!=null && !listaRatioDetalle.isEmpty()){
						throw new AnexoDetalleException("No se puede realizar la operación. El elemento '"+anexoDetalle.getStrTexto()+"' " +
						"se encuentra asociado a un ratio, por lo que no puede ser eliminado.");
					}
				}
				
				
				listaAnexoDetalleOperador = boAnexoDetalleOperador.getPorAnexoDetalle(anexoDetalle);
				
				if(listaAnexoDetalleOperador!=null && !listaAnexoDetalleOperador.isEmpty()){
					for(AnexoDetalleOperador anexoDetalleOperador : listaAnexoDetalleOperador){					
						log.info("eliminar:"+anexoDetalleOperador);
						boAnexoDetalleOperador.eliminar(anexoDetalleOperador);
					}
				}
				
				if(listaAnexoDetalleCuenta!=null && !listaAnexoDetalleCuenta.isEmpty()){
					for(AnexoDetalleCuenta anexoDetalleCuenta : listaAnexoDetalleCuenta){					
						log.info("eliminar:"+anexoDetalleCuenta);
						boAnexoDetalleCuenta.eliminar(anexoDetalleCuenta);
					}
				}
				
				boAnexoDetalle.eliminar(anexoDetalle);
			
		}catch(AnexoDetalleException e){
			throw e;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	public Anexo modificarAnexo(Anexo anexo) throws BusinessException, AnexoDetalleException{
		try{
			
			log.info(anexo);
			boAnexo.modificar(anexo);			
			//eliminarAnexoDetalle(anexo);			
			//grabarAnexoDetalle(anexo);
			
			List<AnexoDetalle> listaAnexoDetalleBD = boAnexoDetalle.getPorAnexo(anexo);
			List<AnexoDetalle> listaAnexoDetalleTemp = new ArrayList<AnexoDetalle>();
			List<AnexoDetalle> listaAnexoDetalleGrabar = new ArrayList<AnexoDetalle>();
			
			for(AnexoDetalle anexoDetalle : anexo.getListaAnexoDetalle()){
				if(anexoDetalle.getId().getIntItemAnexoDetalle()==null){
					anexoDetalle.getId().setIntPersEmpresaAnexo(anexo.getId().getIntPersEmpresaAnexo());
					anexoDetalle.getId().setIntContPeriodoAnexo(anexo.getId().getIntContPeriodoAnexo());
					anexoDetalle.getId().setIntParaTipoAnexo(anexo.getId().getIntParaTipoAnexo());
					log.info(anexoDetalle);					
					boAnexoDetalle.grabar(anexoDetalle);
					listaAnexoDetalleGrabar.add(anexoDetalle);
				}else{
					listaAnexoDetalleTemp.add(anexoDetalle);
				}
			}
			
			for(AnexoDetalle anexoDetalle : listaAnexoDetalleGrabar){
				grabarAnexoDetalleOperador(anexoDetalle, anexo.getListaAnexoDetalle());
			}			
			
			boolean existe = Boolean.FALSE;
			for(AnexoDetalle anexoDetalleBD : listaAnexoDetalleBD){
				existe = Boolean.FALSE;
				for(AnexoDetalle anexoDetalle : listaAnexoDetalleTemp){
					if(anexoDetalle.getId().getIntItemAnexoDetalle().equals(anexoDetalleBD.getId().getIntItemAnexoDetalle())){
						existe = Boolean.TRUE;
						modificarAnexoDetalle(anexoDetalle,anexo.getListaAnexoDetalle());
						break;
					}
				}
				if(!existe){
					eliminarAnexoDetalle(anexoDetalleBD,anexo);
				}
			}
		}catch(AnexoDetalleException e){
			throw e;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return anexo;
	}
	
	
	private AnexoDetalle grabarAnexoDetalleOperador(AnexoDetalle anexoDetalle, List<AnexoDetalle>listaAnexoDetalle) throws BusinessException{		
		if(anexoDetalle.getListaAnexoDetalleOperador()!=null && !anexoDetalle.getListaAnexoDetalleOperador().isEmpty()){
			Integer intItemAnexoDetalleBD;
			for(AnexoDetalleOperador anexoDetalleOperador : anexoDetalle.getListaAnexoDetalleOperador()){
				anexoDetalleOperador.getId().setIntPersEmpresaAnexo(anexoDetalle.getId().getIntPersEmpresaAnexo());
				anexoDetalleOperador.getId().setIntContPeriodoAnexo(anexoDetalle.getId().getIntContPeriodoAnexo());
				anexoDetalleOperador.getId().setIntParaTipoAnexo(anexoDetalle.getId().getIntParaTipoAnexo());
				anexoDetalleOperador.getId().setIntItemAnexoDetalle(anexoDetalle.getId().getIntItemAnexoDetalle());	
					
				intItemAnexoDetalleBD = buscarAnexoDetalle(anexoDetalleOperador.getAnexoDetalleReferencia(),listaAnexoDetalle).getId().getIntItemAnexoDetalle(); 
					
				anexoDetalleOperador.setIntItemAnexoDetalleOperador(intItemAnexoDetalleBD);					
				log.info(anexoDetalleOperador);
				boAnexoDetalleOperador.grabar(anexoDetalleOperador);				
			}
		}		
		return anexoDetalle;
	}
	
	private AnexoDetalle modificarAnexoDetalle(AnexoDetalle anexoDetalle, List<AnexoDetalle>listaAnexoDetalle) throws BusinessException{
		log.info(anexoDetalle);
		boAnexoDetalle.modificar(anexoDetalle);
		List<AnexoDetalleOperador> listaAnexoDetalleOperadorBD = boAnexoDetalleOperador.getPorAnexoDetalle(anexoDetalle);
		for(AnexoDetalleOperador aDO : listaAnexoDetalleOperadorBD){
			log.info(aDO);
			boAnexoDetalleOperador.eliminar(aDO);
		}
		grabarAnexoDetalleOperador(anexoDetalle, listaAnexoDetalle);
		return anexoDetalle;
	}

	
	public Ratio grabarRatio(Ratio ratio) throws BusinessException{
		try{
			
			log.info(ratio);
			boRatio.grabar(ratio);
			
			for(RatioDetalle ratioDetalle : ratio.getListaRatioDetalle()){
				grabarRatioDetalle(ratio, ratioDetalle);
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ratio;
	}
	
	private RatioDetalle grabarRatioDetalle(Ratio ratio, RatioDetalle ratioDetalle) throws BusinessException{
		ratioDetalle.getId().setIntPersEmpresaRatio(ratio.getId().getIntPersEmpresaRatio());
		ratioDetalle.getId().setIntContPeriodoRatio(ratio.getId().getIntContPeriodoRatio());
		ratioDetalle.getId().setIntCodigoRatio(ratio.getId().getIntCodigoRatio());
		
		AnexoDetalle operando1 = ratioDetalle.getOperando1();
		if(operando1.getId().getIntItemAnexoDetalle()!=null){
			ratioDetalle.setIntPersEmpresaAnexo(operando1.getId().getIntPersEmpresaAnexo());
			ratioDetalle.setIntContPeriodoAnexo(operando1.getId().getIntContPeriodoAnexo());
			ratioDetalle.setIntParaTipoAnexo(operando1.getId().getIntParaTipoAnexo());
			ratioDetalle.setIntAndeItemAnexoDetalle(operando1.getId().getIntItemAnexoDetalle());
		}
		
		AnexoDetalle operando2 = ratioDetalle.getOperando2();
		if(operando2.getId().getIntItemAnexoDetalle()!=null){
			ratioDetalle.setIntPersEmpresaAnexo2(operando2.getId().getIntPersEmpresaAnexo());
			ratioDetalle.setIntContPeriodoAnexo2(operando2.getId().getIntContPeriodoAnexo());
			ratioDetalle.setIntParaTipoAnexo2(operando2.getId().getIntParaTipoAnexo());
			ratioDetalle.setIntAndeItemAnexoDetalle2(operando2.getId().getIntItemAnexoDetalle());
		}
		log.info(ratioDetalle);
		boRatioDetalle.grabar(ratioDetalle);
		
		return ratioDetalle;
	}
	
	private RatioDetalle modificarRatioDetalle(RatioDetalle ratioDetalle) throws BusinessException{		
		AnexoDetalle operando1 = ratioDetalle.getOperando1();
		if(operando1.getId().getIntItemAnexoDetalle()!=null){
			ratioDetalle.setIntPersEmpresaAnexo(operando1.getId().getIntPersEmpresaAnexo());
			ratioDetalle.setIntContPeriodoAnexo(operando1.getId().getIntContPeriodoAnexo());
			ratioDetalle.setIntParaTipoAnexo(operando1.getId().getIntParaTipoAnexo());
			ratioDetalle.setIntAndeItemAnexoDetalle(operando1.getId().getIntItemAnexoDetalle());
		}else{
			ratioDetalle.setIntPersEmpresaAnexo(null);
			ratioDetalle.setIntContPeriodoAnexo(null);
			ratioDetalle.setIntParaTipoAnexo(null);
			ratioDetalle.setIntAndeItemAnexoDetalle(null);
		}
		
		AnexoDetalle operando2 = ratioDetalle.getOperando2();
		if(operando2.getId().getIntItemAnexoDetalle()!=null){
			ratioDetalle.setIntPersEmpresaAnexo2(operando2.getId().getIntPersEmpresaAnexo());
			ratioDetalle.setIntContPeriodoAnexo2(operando2.getId().getIntContPeriodoAnexo());
			ratioDetalle.setIntParaTipoAnexo2(operando2.getId().getIntParaTipoAnexo());
			ratioDetalle.setIntAndeItemAnexoDetalle2(operando2.getId().getIntItemAnexoDetalle());
		}else{
			ratioDetalle.setIntPersEmpresaAnexo2(null);
			ratioDetalle.setIntContPeriodoAnexo2(null);
			ratioDetalle.setIntParaTipoAnexo2(null);
			ratioDetalle.setIntAndeItemAnexoDetalle2(null);
		}
		log.info(ratioDetalle);
		boRatioDetalle.modificar(ratioDetalle);		
		return ratioDetalle;
	}
	

	public Ratio modificarRatio(Ratio ratio) throws BusinessException{
		try{
			
			log.info(ratio);
			boRatio.modificar(ratio);
			
			List<RatioDetalle> listaRatioDetalle = ratio.getListaRatioDetalle();
			List<RatioDetalle> listaRatioDetalleBD = boRatioDetalle.getPorRatio(ratio);
			List<RatioDetalle> listaRatioDetalleTemp = new ArrayList<RatioDetalle>();
			
			for(RatioDetalle ratioDetalle : listaRatioDetalle){
				if(ratioDetalle.getId().getIntItemRatio()==null){
					grabarRatioDetalle(ratio,ratioDetalle);
				}else{
					listaRatioDetalleTemp.add(ratioDetalle);
				}				
			}
			
		
			boolean seEncuentraEnIU = Boolean.FALSE;
			for(RatioDetalle ratioDetalleBD : listaRatioDetalleBD){
				log.info("bd:"+ratioDetalleBD);
				seEncuentraEnIU = Boolean.FALSE;
				for(RatioDetalle ratioDetalle : listaRatioDetalleTemp){
					log.info("iu:"+ratioDetalle);
					if(ratioDetalle.getId().getIntItemRatio().equals(ratioDetalleBD.getId().getIntItemRatio())){
						log.info("mod:"+ratioDetalle);
						modificarRatioDetalle(ratioDetalle);
						seEncuentraEnIU = Boolean.TRUE;
						break;
					}
				}
				if(!seEncuentraEnIU){
					log.info("eli:"+ratioDetalleBD);
					boRatioDetalle.eliminar(ratioDetalleBD);
				}
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ratio;
	}
	
	
	public List<Ratio> buscarRatio(Ratio ratioFiltro) throws BusinessException{
		List<Ratio> listaRatio;
		try{
			log.info("--ratioFiltro");
			listaRatio = boRatio.buscar(ratioFiltro);
			for(Ratio ratio : listaRatio){
				log.info(ratio);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaRatio;
	}
}