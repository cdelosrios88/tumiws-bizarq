package pe.com.tumi.persona.core.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.ComunicacionPK;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.bo.PersonaBO;
import pe.com.tumi.persona.core.bo.PersonaEmpresaBO;
import pe.com.tumi.persona.core.bo.PersonaRolBO;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.empresa.bo.ActividadEconomicaBO;
import pe.com.tumi.persona.empresa.bo.JuridicaBO;
import pe.com.tumi.persona.empresa.bo.TipoComprobanteBO;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.ActividadEconomicaPK;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.domain.TipoComprobante;
import pe.com.tumi.persona.empresa.domain.TipoComprobantePK;
import pe.com.tumi.persona.empresa.service.EmpresaService;
import pe.com.tumi.persona.vinculo.bo.VinculoBO;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

public class PersonaJuridicaService {
	
	protected  static Logger log = Logger.getLogger(PersonaJuridicaService.class);
	private PersonaBO boPersona = (PersonaBO)TumiFactory.get(PersonaBO.class);
	private JuridicaBO boJuridica = (JuridicaBO)TumiFactory.get(JuridicaBO.class);
	private ActividadEconomicaBO boActividadEconomica = (ActividadEconomicaBO)TumiFactory.get(ActividadEconomicaBO.class);
	private TipoComprobanteBO boTipoComprobante = (TipoComprobanteBO)TumiFactory.get(TipoComprobanteBO.class);
	private PersonaDetalleService detallePersonaService = (PersonaDetalleService)TumiFactory.get(PersonaDetalleService.class);
	private PersonaEmpresaService personaEmpresaService = (PersonaEmpresaService)TumiFactory.get(PersonaEmpresaService.class);
	private PersonaRolService personaRolService = (PersonaRolService)TumiFactory.get(PersonaRolService.class);
	private VinculoBO boVinculo = (VinculoBO)TumiFactory.get(VinculoBO.class);
	
	public Persona grabarPersonaJuridica(Persona pPersona) throws BusinessException{
		Persona persona = null;
		try{
			persona = boPersona.grabarPersona(pPersona);
			pPersona.getJuridica().setIntIdPersona(persona.getIntIdPersona());
			boJuridica.grabarJuridica(pPersona.getJuridica());
			
			if(pPersona.getPersonaEmpresa()!=null){
					personaEmpresaService.grabarDinamicaPersonaEmpresa(pPersona.getPersonaEmpresa(), persona.getIntIdPersona());
					//if(pPersona.getListaPersona()!=null){
						//vinculadaService.grabarListaDinamicaPersonaVinculada(pPersona.getListaPersona(), persona.getIntIdPersona());
					//}
			}else if(pPersona.getListaPersonaEmpresa()!=null && pPersona.getListaPersonaEmpresa().size()!=0){
				personaEmpresaService.grabarListaDinamicaPersonaEmpresa(pPersona.getListaPersonaEmpresa(), persona.getIntIdPersona());
			}else{
				throw new BusinessException("La Persona Debe Asociarse a una Empresa");
			}
			detallePersonaService.grabarDinamicoDetalleDePersonaPorIdPersona(pPersona,persona.getIntIdPersona());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return pPersona;
	}
	
	public Persona modificarPersonaJuridica(Persona pPersona) throws BusinessException{
		try{
			boJuridica.modificarJuridica(pPersona.getJuridica());
			detallePersonaService.grabarDinamicoDetalleDePersonaPorIdPersona(pPersona,pPersona.getJuridica().getIntIdPersona());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return pPersona;
	}
	
	public Persona grabarPersonaJuridicaTotal(Persona pPersona) throws BusinessException{
		Persona persona = null;
		try{
			persona = grabarPersonaJuridica(pPersona);
			grabarDinamicoDetalleDePersonaJuridica(persona,persona.getIntIdPersona());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return pPersona;
	}
	
	public Persona modificarPersonaJuridicaTotal(Persona pPersona) throws BusinessException{
		Persona persona = null;
		try{
			persona = modificarPersonaJuridica(pPersona);
			grabarDinamicoDetalleDePersonaJuridica(persona,persona.getIntIdPersona());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return pPersona;
	}
	
	/*public void eliminarPersonaJuridicaPorIdPersona(Integer pIntIdPersona)throws BusinessException {
		Persona persona = null;
		Juridica juridica = null;
		try{
				persona = boPersona.getPersonaPorPK(pIntIdPersona);
				persona.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boPersona.modificarPersona(persona);
				juridica = boJuridica.getJuridicaPorPK(pIntIdPersona);
				juridica.setIntIdPersona(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boJuridica.modificarJuridica(juridica);
				detallePersonaService.eliminarDetalleDePersonaPorIdPersona(pIntIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}*/
	
	/*public Persona getPersonaJuridicaYListaPersonaPorRucYTipoVinculoYIdEmpresa(String strRuc,Integer intTipoVinculo,Integer intIdEmpresaSistema) throws BusinessException {
		Persona persona = null;
		try{
			persona = getPersonaJuridicaPorRuc(strRuc);
			//PersonaEmpresaPK pk = new PersonaEmpresaPK();
			//pk.setIntIdEmpresa(intIdEmpresaSistema);
			//pk.setIntIdPersona(persona.getIntIdPersona());
			//persona.setListaPersona(vinculadaService.getListaPersonaNaturalPorPKPersonaEmpresaYTipoVinculo(pk,
			//																							 intTipoVinculo));
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return persona;
	}*/
	
	public Persona getPersonaJuridicaPorRuc(String strRuc) throws BusinessException {
		log.info("--------------------------PersonaJuridicaService.getPersonaJuridicaPorRuc--------------------------");
		Persona persona = null;
		try{
			persona = boPersona.getPersonaPorRuc(strRuc);
			if(persona!=null){
				persona.setJuridica(boJuridica.getJuridicaPorPK(persona.getIntIdPersona()));
				detallePersonaService.getDetalleDePersonaPorIdPersona(persona,persona.getIntIdPersona());
				log.info("boActEconomica.listaActividadEconomicaPorIdPersona"+boActividadEconomica.getListaActividadEconomicaPorIdPersona(persona.getIntIdPersona()));
				persona.getJuridica().setListaActividadEconomica(getListaActividadEconomicaPorIdPersona(persona.getIntIdPersona()));
				log.info("boActEconomica.listaActividadEconomicaPorIdPersona"+boTipoComprobante.getListaTipoComprobantePorIdPersona(persona.getIntIdPersona()));
				persona.getJuridica().setListaTipoComprobante(getListaTipoComprobantePorIdPersona(persona.getIntIdPersona()));
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	public Persona getPersonaJuridicaPorRucYIdEmpresa(String strRuc, Integer intIdEmpresaSistema) throws BusinessException {
		log.info("--------------------------PersonaJuridicaService.getPersonaJuridicaPorRucYIdEmpresa--------------------------");
		Persona persona = null;
		Juridica juridica = null;
		try{
			persona = boPersona.getPersonaPorRuc(strRuc);
			log.info("persona.intIdPersona: "+persona.getIntIdPersona());
			juridica = boJuridica.getJuridicaPorPK(persona.getIntIdPersona());
			log.info("juridica.strRazonSocial: "+juridica.getStrRazonSocial());
			persona.setJuridica(juridica);
			detallePersonaService.getDetalleDePersonaPorIdPersona(persona,persona.getIntIdPersona());
			log.info("boActEconomica.listaActividadEconomicaPorIdPersona: "+boActividadEconomica.getListaActividadEconomicaPorIdPersona(persona.getIntIdPersona()));
			persona.getJuridica().setListaActividadEconomica(getListaActividadEconomicaPorIdPersona(persona.getIntIdPersona()));
			log.info("boTipoComprobante.listaTipoComprobantePorIdPersona: "+boTipoComprobante.getListaTipoComprobantePorIdPersona(persona.getIntIdPersona()));
			persona.getJuridica().setListaTipoComprobante(getListaTipoComprobantePorIdPersona(persona.getIntIdPersona()));
			
			//Obteniendo la lista de PersonaRol
			PersonaEmpresaPK personaEmpresaPk = new PersonaEmpresaPK();
			personaEmpresaPk.setIntIdEmpresa(intIdEmpresaSistema);
			personaEmpresaPk.setIntIdPersona(persona.getIntIdPersona());
			List<PersonaRol> listaPersonaRol = personaRolService.getListaPersonaRolPorPKPersonaEmpresa(personaEmpresaPk);
			PersonaEmpresa personaEmpresa = new PersonaEmpresa();
			personaEmpresa.setId(personaEmpresaPk);
			personaEmpresa.setListaPersonaRol(listaPersonaRol);
			persona.setPersonaEmpresa(personaEmpresa);
			
			log.info("persona.juridica.strRazonSocial: "+persona.getJuridica().getStrRazonSocial());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	public Persona getPersonaJuridicaPorIdPersonaYIdEmpresa(Integer intIdPersona, Integer intIdEmpresaSistema) throws BusinessException {
		log.info("--------------------------PersonaJuridicaService.getPersonaJuridicaPorIdPersonaYIdEmpresa--------------------------");
		Persona persona = null;
		Juridica juridica = null;
		try{
			System.out.println("intIdPersona: "+intIdPersona);
			persona = boPersona.getPersonaPorPK(intIdPersona);
			System.out.println("persona: "+persona);
			juridica = boJuridica.getJuridicaPorPK(persona.getIntIdPersona());
			persona.setJuridica(juridica);
			detallePersonaService.getDetalleDePersonaPorIdPersona(persona,persona.getIntIdPersona());
			if(getListaActividadEconomicaPorIdPersona(persona.getIntIdPersona()).size()>0)
			persona.getJuridica().setListaActividadEconomica(getListaActividadEconomicaPorIdPersona(persona.getIntIdPersona()));
			if(getListaTipoComprobantePorIdPersona(persona.getIntIdPersona()).size()>0)
			persona.getJuridica().setListaTipoComprobante(getListaTipoComprobantePorIdPersona(persona.getIntIdPersona()));
			
			//Obteniendo la lista de PersonaRol
			PersonaEmpresaPK personaEmpresaPk = new PersonaEmpresaPK();
			personaEmpresaPk.setIntIdEmpresa(intIdEmpresaSistema);
			personaEmpresaPk.setIntIdPersona(persona.getIntIdPersona());
			List<PersonaRol> listaPersonaRol = personaRolService.getListaPersonaRolPorPKPersonaEmpresa(personaEmpresaPk);
			PersonaEmpresa personaEmpresa = new PersonaEmpresa();
			personaEmpresa.setId(personaEmpresaPk);
			personaEmpresa.setListaPersonaRol(listaPersonaRol);
			persona.setPersonaEmpresa(personaEmpresa);
			
			List<Vinculo> listaVinculo = boVinculo.getVinculoPorPersonaEmpresaPK(personaEmpresaPk);
			persona.getPersonaEmpresa().setListaVinculo(listaVinculo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}

	public Persona getPersonaJuridicaPorIdPersona(Integer pIntIdPersona)throws BusinessException {
		Persona persona = null;
		try{
				persona = boPersona.getPersonaPorPK(pIntIdPersona);
				persona.setJuridica(boJuridica.getJuridicaPorPK(pIntIdPersona));
				detallePersonaService.getDetalleDePersonaPorIdPersona(persona,pIntIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	public void grabarDinamicoDetalleDePersonaJuridica(Persona persona,Integer intIdPersona) throws BusinessException {
		Timestamp lTsFechaEliminacion = null;
		try{
			lTsFechaEliminacion = JFecha.obtenerTimestampDeFechayHoraActual();
			if(persona.getJuridica().getListaActividadEconomica()!=null){
				grabarListaDinamicaActividadEconomica(persona.getJuridica().getListaActividadEconomica(),intIdPersona);
			}
			if(persona.getJuridica().getListaTipoComprobante()!=null){
				grabarListaDinamicaTipoComprobante(persona.getJuridica().getListaTipoComprobante(),intIdPersona);
			}		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}		
	}
	
	public List<ActividadEconomica> grabarListaDinamicaActividadEconomica(List<ActividadEconomica> lista, Integer intIdPersona) throws BusinessException{
		try{
			System.out.println("lista: "+lista);
			if(lista==null) return lista;
			System.out.println("lista.size(): "+lista.size());
			for(ActividadEconomica actividadEconomica : lista){
				if(actividadEconomica.getId() == null || actividadEconomica.getId().getIntIdPersona() == null){
					if(actividadEconomica.getId() == null)actividadEconomica.setId(new ActividadEconomicaPK());
					actividadEconomica.getId().setIntIdPersona(intIdPersona);
					actividadEconomica = boActividadEconomica.grabarActividadEconomica(actividadEconomica);
				}else{
					if(boActividadEconomica.getActividadEconomicaPorPK(actividadEconomica.getId()) == null){
						actividadEconomica.getId().setIntIdPersona(intIdPersona);
						actividadEconomica = boActividadEconomica.grabarActividadEconomica(actividadEconomica);
					}else{
						if(actividadEconomica.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO))
							actividadEconomica.setTsFechaEliminacion(JFecha.obtenerTimestampDeFechayHoraActual());						
						actividadEconomica = boActividadEconomica.modificarActividadEconomica(actividadEconomica);
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
	
	public List<TipoComprobante> grabarListaDinamicaTipoComprobante(List<TipoComprobante> lista, Integer intIdPersona) throws BusinessException{
		TipoComprobante dto = null;
		TipoComprobante dtoTemp = null;
		Timestamp lTsFechaEliminacion = null;
		try{
			lTsFechaEliminacion = JFecha.obtenerTimestampDeFechayHoraActual();
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				
				if(dto.getId() == null || dto.getId().getIntIdPersona() == null){
					if(dto.getId() == null)dto.setId(new TipoComprobantePK());
					dto.getId().setIntIdPersona(intIdPersona);
					dto = boTipoComprobante.grabarTipoComprobante(dto);
				}else{
					dtoTemp = boTipoComprobante.getTipoComprobantePorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPersona(intIdPersona);
						dto = boTipoComprobante.grabarTipoComprobante(dto);
					}else{
						if(dto.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)==0){
							dto.setTsFechaEliminacion(lTsFechaEliminacion);
						}
						dto = boTipoComprobante.modificarTipoComprobante(dto);
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
	
	public List<ActividadEconomica> getListaActividadEconomicaPorIdPersona(Integer idPersona) throws BusinessException{
		List<ActividadEconomica> lista = null;
		ActividadEconomica actividad = null;
		List<Tabla> listTabla = null;
		Tabla tabla = null;
		try{
			TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
    		listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_ACTIVIDAD_ECONOMICA));
    		log.info("listTabla.size: "+listTabla.size());
			
			lista = boActividadEconomica.getListaActividadEconomicaPorIdPersona(idPersona);
			
			for(int i=0; i<lista.size(); i++){
				actividad = lista.get(i);
				for(int j=0; j<listTabla.size(); j++){
					tabla = listTabla.get(j);
					if(tabla.getIntIdDetalle().equals(actividad.getIntActividadEconomicaCod())){
						actividad.setTabla(tabla);
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
	
	public List<TipoComprobante> getListaTipoComprobantePorIdPersona(Integer idPersona) throws BusinessException{
		List<TipoComprobante> lista = null;
		TipoComprobante comprobante = null;
		List<Tabla> listTabla = null;
		Tabla tabla = null;
		try{
			TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
    		listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCOMPROBANTE));
    		
			lista = boTipoComprobante.getListaTipoComprobantePorIdPersona(idPersona);
			int contador = 0;
			
			for(int i=0; i<lista.size(); i++){
				comprobante = lista.get(i);
				for(int j=0; j<listTabla.size(); j++){
					tabla = listTabla.get(j);
					if(tabla.getIntIdDetalle().equals(comprobante.getIntTipoComprobanteCod())){
						comprobante.setTabla(tabla);
						break;
					}
					contador++;
					if(contador == 100){
						return lista;
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
	
	public List<Persona> getListPerJuridicaBusqueda(Persona o)throws BusinessException {
		log.info("-------------------------------------Debugging PersonaNaturalService.getListPerJuridicaBusqueda-------------------------------------");
		List<Juridica> listaJuridica = null;
		List<Persona> lista = null;
		Persona persona = null;
		try{
			listaJuridica = boJuridica.getListJuridicaBusqueda(o.getJuridica());
			log.info("listaJuridica.size: "+listaJuridica.size());
			
			persona = new Persona();
			for(Juridica juridica : listaJuridica){
				if(lista==null)lista = new ArrayList<Persona>();
				persona = boPersona.getPersonaPorPK(juridica.getIntIdPersona());
				persona.setJuridica(juridica);
				if(o.getStrRuc()!=null){
					if(persona.getStrRuc().equals(o.getStrRuc())){
						lista.add(persona);
					}
				}else{
					lista.add(persona);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Persona> getBusquedaPerJuridicaSinSucursales(Persona o)throws BusinessException {
		//Las Sucursales se registran sin Ruc, se filtran las que no tengan Ruc
		log.info("-------------------------------------Debugging PersonaNaturalService.getBusquedaPerJuridicaSinSucursales-------------------------------------");
		List<Juridica> listaJuridica = null;
		List<Persona> lista = null;
		Persona persona = null;
		try{
			listaJuridica = boJuridica.getListJuridicaBusqueda(o.getJuridica());
			log.info("listaJuridica.size: "+listaJuridica.size());
			
			persona = new Persona();
			for(Juridica juridica : listaJuridica){
				if(lista==null)lista = new ArrayList<Persona>();
				persona = boPersona.getPersonaPorPK(juridica.getIntIdPersona());
				persona.setJuridica(juridica);
				if(persona.getStrRuc()!=null){
					if(o.getStrRuc()!=null){
						if(persona.getStrRuc().equals(o.getStrRuc())){
							lista.add(persona);
						}
					}else{
						lista.add(persona);
					}
				}
			}
			System.out.println("listaPersonaJuridica: "+lista);
			if(lista!=null);
			System.out.println("listaPersonaJuridica.size: "+lista.size());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Persona getPersonaJuridicaPorRucYIdEmpresa2(String strRuc, Integer intIdEmpresaSistema) throws BusinessException {
		log.info("--------------------------PersonaJuridicaService.getPersonaJuridicaPorRucYIdEmpresa--------------------------");
		Persona persona = null;
		Juridica juridica = null;
		try{
			persona = boPersona.getPersonaPorRuc(strRuc);
			if(persona==null){
				return persona;
			}
			juridica = boJuridica.getJuridicaPorPK(persona.getIntIdPersona());
			if(juridica!=null){
				persona.setJuridica(juridica);
				persona.getJuridica().setListaActividadEconomica(getListaActividadEconomicaPorIdPersona(persona.getIntIdPersona()));
				persona.getJuridica().setListaTipoComprobante(getListaTipoComprobantePorIdPersona(persona.getIntIdPersona()));				
				log.info("persona.juridica.strRazonSocial: "+persona.getJuridica().getStrRazonSocial());
			}
			
			detallePersonaService.getDetalleDePersonaPorIdPersona(persona,persona.getIntIdPersona());
			
			//Obteniendo la lista de PersonaRol
			PersonaEmpresaPK personaEmpresaPk = new PersonaEmpresaPK();
			personaEmpresaPk.setIntIdEmpresa(intIdEmpresaSistema);
			personaEmpresaPk.setIntIdPersona(persona.getIntIdPersona());
			List<PersonaRol> listaPersonaRol = personaRolService.getListaPersonaRolPorPKPersonaEmpresa(personaEmpresaPk);
			PersonaEmpresa personaEmpresa = new PersonaEmpresa();
			personaEmpresa.setId(personaEmpresaPk);
			personaEmpresa.setListaPersonaRol(listaPersonaRol);
			persona.setPersonaEmpresa(personaEmpresa);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	/**
	 * Recupera Juridica, Lista Actividad Economica y Lista Tipo COmprbante
	 * @param intIdPersona
	 * @return
	 * @throws BusinessException
	 */
	public Juridica getJuridicaDetalladaPorIdPersona(Integer intIdPersona) throws BusinessException {
		Juridica juridica = null;
		try{
			juridica = boJuridica.getJuridicaPorPK(intIdPersona);
			
			if(juridica==null)	return null;
			
			juridica.setListaActividadEconomica(getListaActividadEconomicaPorIdPersona(intIdPersona));
			juridica.setListaTipoComprobante(getListaTipoComprobantePorIdPersona(intIdPersona));			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return juridica;
	}
	
	
	/**
	 * Recupera SOLO la persona Juridica de una persona en base a su ID persona
	 * @param strRuc
	 * @return
	 * @throws BusinessException
	 */
	public Juridica getSoloPersonaJuridicaPorPersona(Integer idPersona) throws BusinessException {
		log.info("--------------------------PersonaJuridicaService.getPersonaJuridicaPorRuc--------------------------");
		Juridica juridica = null;
		try{
			juridica = boJuridica.getJuridicaPorPK(idPersona);

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return juridica;
	}

}
