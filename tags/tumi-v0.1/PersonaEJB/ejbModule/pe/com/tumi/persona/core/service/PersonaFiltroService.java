package pe.com.tumi.persona.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.bo.NaturalBO;
import pe.com.tumi.persona.core.bo.PersonaBO;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.empresa.bo.JuridicaBO;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class PersonaFiltroService {
	
	protected  static Logger log = Logger.getLogger(PersonaFiltroService.class);
	private PersonaBO boPersona = (PersonaBO)TumiFactory.get(PersonaBO.class);
	private NaturalBO boNatural = (NaturalBO)TumiFactory.get(NaturalBO.class);
	private JuridicaBO boJuridica = (JuridicaBO)TumiFactory.get(JuridicaBO.class);
	
	
	public List<Persona> buscarListaPersonaParaFiltro(Integer intTipoBusqueda, String strFiltro) throws BusinessException {
		List<Persona> listaPersona = new ArrayList<Persona>();
		try{
			log.info("intTipoBusqueda:"+intTipoBusqueda);
			log.info("strFiltro:"+strFiltro);
			log.info(1);
			
			if(intTipoBusqueda.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
				log.info(2);
				Natural natural = new Natural();
				natural.setStrNombres(strFiltro);
				List<Natural> listaNatural = boNatural.getListaNaturalBusqueda(natural);
				for(Natural natu : listaNatural)
					listaPersona.add(boPersona.getPersonaPorPK(natu.getIntIdPersona()));				
			
				List<Juridica>listaJuridica = boJuridica.getListaPorNombreComercial(strFiltro);
				for(Juridica juridica : listaJuridica)
					listaPersona.add(boPersona.getPersonaPorPK(juridica.getIntIdPersona()));
			
			}else if(intTipoBusqueda.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
				log.info(3);
				Natural natural = boNatural.getNaturalPorTipoIdentidadYNroIdentidad(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, strFiltro);
				if(natural!=null)
					listaPersona.add(boPersona.getPersonaPorPK(natural.getIntIdPersona()));
			
			}else if(intTipoBusqueda.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
				log.info(4);
				log.info(strFiltro);
				Persona persona = boPersona.getPersonaPorRuc(strFiltro);
				log.info(persona);
				if(persona!=null)
					listaPersona.add(persona);				
			
			}else if(intTipoBusqueda.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RAZONSOCIAL)){
				log.info(5);
				List<Juridica>listaJuridica = boJuridica.getListaPorRazonSocial(strFiltro);
				for(Juridica juridica : listaJuridica)
					listaPersona.add(boPersona.getPersonaPorPK(juridica.getIntIdPersona()));
			
			}
			log.info(6);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaPersona;
	}	
}