package pe.com.tumi.riesgo.archivo.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.riesgo.archivo.bo.ConfDetalleBO;
import pe.com.tumi.riesgo.archivo.bo.ConfEstructuraBO;
import pe.com.tumi.riesgo.archivo.bo.ConfiguracionBO;
import pe.com.tumi.riesgo.archivo.bo.NombreBO;
import pe.com.tumi.riesgo.archivo.domain.ConfDetalle;
import pe.com.tumi.riesgo.archivo.domain.ConfEstructura;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;
import pe.com.tumi.riesgo.archivo.domain.Nombre;

public class ConfiguracionService {

	protected static Logger log = Logger.getLogger(ConfiguracionService.class);	

	ConfiguracionBO boConfiguracion = (ConfiguracionBO)TumiFactory.get(ConfiguracionBO.class);
	ConfDetalleBO boConfDetalle = (ConfDetalleBO)TumiFactory.get(ConfDetalleBO.class);
	NombreBO boNombre = (NombreBO)TumiFactory.get(NombreBO.class);
	ConfEstructuraBO boConfEstructura = (ConfEstructuraBO)TumiFactory.get(ConfEstructuraBO.class);
	
	public Configuracion grabarConfiguracion(Configuracion configuracion, List <ConfDetalle> listaConfDetalle, List<Nombre> listaNombre) 
		throws BusinessException{
		try{
			log.info(configuracion);
			boConfiguracion.grabar(configuracion);
			for(ConfDetalle confDetalle : listaConfDetalle ){
				confDetalle.getId().setIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
				boConfDetalle.grabar(confDetalle);
			}
			for(Nombre nombre : listaNombre ){
				nombre.getId().setIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
				boNombre.grabar(nombre);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return configuracion;
	}
	
	public Configuracion grabarConfiguracion(Configuracion configuracion, List <ConfDetalle> listaConfDetalle, 
			List<Nombre> listaNombre,ConfEstructura confEstructura) 
		throws BusinessException{
		try{
			grabarConfiguracion(configuracion, listaConfDetalle, listaNombre);
			confEstructura.getId().setIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
			boConfEstructura.grabar(confEstructura);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return configuracion;
	}
	
	public List<Configuracion> buscarConfiguracion(Configuracion configuracion, Timestamp busquedaInicio, Timestamp busquedaFin) 
		throws BusinessException{
		List<Configuracion> listaConfiguracion = null;
		try{
			listaConfiguracion = boConfiguracion.buscar(configuracion, busquedaInicio, busquedaFin);
			listaConfiguracion = cargarListaConfiguracion(listaConfiguracion);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfiguracion;
	}
	
	public List<Configuracion> buscarConfiguracionConEstructura(Configuracion configuracion, Timestamp busquedaInicio, Timestamp busquedaFin) 
		throws BusinessException{
		List<Configuracion> listaConfiguracion = null;
		try{
			log.info("bus:"+configuracion);
			listaConfiguracion = boConfiguracion.buscarConEstructura(configuracion, busquedaInicio, busquedaFin);
			
			//Filtro de intParaFormatoArchivoCod
			List<Configuracion> listaConfiguracionAux = new ArrayList<Configuracion>();
			for(Configuracion conf : listaConfiguracion){
				log.info("enc:"+configuracion);
				if(configuracion.getIntParaFormatoArchivoCod().equals(conf.getIntParaFormatoArchivoCod())){
					listaConfiguracionAux.add(conf);
				}
			}
			listaConfiguracion = listaConfiguracionAux;
			
			listaConfiguracion = cargarListaConfiguracion(listaConfiguracion);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfiguracion;
	}
	
	public List<Configuracion> buscarConfiguracionSinEstructura(Configuracion configuracion, Timestamp busquedaInicio, Timestamp busquedaFin) 
		throws BusinessException{
		List<Configuracion> listaConfiguracion = null;
		try{
			listaConfiguracion = boConfiguracion.buscarSinEstructura(configuracion, busquedaInicio, busquedaFin);
			listaConfiguracion = cargarListaConfiguracion(listaConfiguracion);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaConfiguracion;
	}
	
	private List<Configuracion> cargarListaConfiguracion(List<Configuracion> listaConfiguracion) throws EJBFactoryException, BusinessException{
		List<Configuracion> listaAux = new ArrayList<Configuracion>();
		PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
		for(Configuracion configura : listaConfiguracion){
			log.info(configura);
			configura.setNatural(personaFacade.getNaturalPorPK(configura.getIntPersPersonaUsuarioPk()));
			listaAux.add(configura);
		}
		listaConfiguracion = listaAux;
		return listaConfiguracion;
	}
	
	public Configuracion modificarConfiguracion(Configuracion configuracion, List <ConfDetalle> listaConfDetalle, List<Nombre> listaNombre) 
		throws BusinessException{
		try{
			log.info(configuracion);
			boConfiguracion.modificar(configuracion);
			//**Proceso de ConfDetalles**			
			//Obtenemos la lista de confDetalle de la bd
			List<ConfDetalle> listaConfDetalleBD = boConfDetalle.getPorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
			//Los nuevos confDetalle que han ingresado (no tienen id) se graban
			List <ConfDetalle> listaConfDetalleAux = new ArrayList<ConfDetalle>();
			for(ConfDetalle confDetalle : listaConfDetalle){
				ConfDetalle confDetalleAux = confDetalle;
				if(confDetalleAux.getId().getIntItemConfiguracionDetalle()==null){					
					confDetalleAux.getId().setIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
					boConfDetalle.grabar(confDetalleAux);					
				}else{
					listaConfDetalleAux.add(confDetalle);					
				}
			}
			listaConfDetalle = listaConfDetalleAux;//Obtenemos listaConfDetalle libre de confDetalles procesados
			//Los confDetalle que se han eliminado mediante interfaz pasan a estado Inactivo
			boolean existe = Boolean.FALSE;
			for(ConfDetalle confDetalleBD : listaConfDetalleBD){
				//Tomamos en cuenta solo los que se encuentran en estado Activo
				if(confDetalleBD.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					existe = Boolean.FALSE;
					for(ConfDetalle confDetalle : listaConfDetalle){
						if(confDetalleBD.getId().getIntItemConfiguracionDetalle().equals(confDetalle.getId().getIntItemConfiguracionDetalle())){
							existe = Boolean.TRUE;
						}
					}
					if(!existe){
						confDetalleBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
						boConfDetalle.modificar(confDetalleBD);
					}
				}
			}
			
			//**Proceso de Nombres**
			//Obtenemos la lista de nombre de la bd
			List<Nombre> listaNombreBD = boNombre.getPorIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
			//Los nuevos nombre que han ingresado (no tienen id) se graban
			List <Nombre> listaNombreAux = new ArrayList<Nombre>();
			for(Nombre nombre : listaNombre){
				if(nombre.getId().getIntItemNombre()==null){
					nombre.getId().setIntItemConfiguracion(configuracion.getId().getIntItemConfiguracion());
					boNombre.grabar(nombre);
				}else{
					listaNombreAux.add(nombre);
				}
			}
			listaNombre = listaNombreAux;//Obtenemos listaNombre libre de nombres procesados
			//Los nombre que se han eliminado mediante interfaz pasan a estado Inactivo
			existe = Boolean.FALSE;
			for(Nombre nombreBD : listaNombreBD){
				//Tomamos en cuenta solo los que se encuentran en estado Activo
				if(nombreBD.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					existe = Boolean.FALSE;
					for(Nombre nombre : listaNombreAux){
						if(nombreBD.getId().getIntItemNombre().equals(nombre.getId().getIntItemNombre())){
							existe = Boolean.TRUE;
						}
					}
					if(!existe){
						nombreBD.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
						boNombre.modificar(nombreBD);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return configuracion;
	}
	
	public Configuracion modificarConfiguracion(Configuracion configuracion, List <ConfDetalle> listaConfDetalle, List<Nombre> listaNombre,ConfEstructura confEstructura) 
		throws BusinessException{
		try{
			modificarConfiguracion(configuracion, listaConfDetalle, listaNombre); 
			boConfEstructura.modificar(confEstructura);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return configuracion;
	}
}
