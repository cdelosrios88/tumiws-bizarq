package pe.com.tumi.credito.socio.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.bo.CuentaIntegranteBO;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeLocal;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.bo.SocioBO;
import pe.com.tumi.credito.socio.core.bo.SocioEstructuraBO;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeLocal;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.domain.PerLaboral;

public class SocioService {
	
	protected static Logger log = Logger.getLogger(SocioService.class);
	private SocioBO boSocio = (SocioBO)TumiFactory.get(SocioBO.class);
	private SocioEstructuraBO boSocioEstructuraBO = (SocioEstructuraBO)TumiFactory.get(SocioEstructuraBO.class);
	private SocioEstructuraBO boSocioEstructura = (SocioEstructuraBO)TumiFactory.get(SocioEstructuraBO.class);
	private SocioEstructuraService socioEstructuraService = (SocioEstructuraService)TumiFactory.get(SocioEstructuraService.class);
	private CuentaIntegranteBO boCuentaIntegrante = (CuentaIntegranteBO)TumiFactory.get(CuentaIntegranteBO.class);
	
	public List<SocioComp> getListaSocioComp(SocioComp o) throws BusinessException{
		log.info("-----------------------Debugging SocioService.getListaSocioComp-----------------------------");
		List<SocioComp> listaSocioComp = null;
		List<SocioEstructura> listaSocioEstructura = null;
		Cuenta cuenta = null;
		
		try{
			//Buscar por tipo de persona
			List<Persona> listPersonaNatu = null;
			Persona persona = new Persona();
			if(o.getPersona().getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL) || 
					o.getPersona().getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_AMBOS)){
				persona.setNatural(new Natural());
				persona.setDocumento(new Documento());
				
				persona.getNatural().setIntIdPersona(o.getPersona().getIntIdPersona());
				persona.getNatural().setStrNombres(o.getStrNombrePersona().trim());
				persona.getDocumento().setStrNumeroIdentidad(o.getStrDocIdentidad());
				
				if(o.getIntFechaBusq().equals(Constante.PARAM_T_TIPOFILTROFECHAS_NACIMIENTO)){
					persona.getNatural().setDtFechaNacDesde(o.getDtFechaDesde());
					persona.getNatural().setDtFechaNacHasta(o.getDtFechaHasta());
				}else if(o.getIntFechaBusq().equals(Constante.PARAM_T_TIPOFILTROFECHAS_INICIOSERVICIO)){
					persona.getNatural().setPerLaboral(new PerLaboral());
					persona.getNatural().getPerLaboral().setDtInicioServicioDesde(o.getDtFechaDesde());
					persona.getNatural().getPerLaboral().setDtInicioServicioHasta(o.getDtFechaHasta());
				}else if(o.getIntFechaBusq().equals(Constante.PARAM_T_TIPOFILTROFECHAS_INICIOCONTRATO)){
					persona.getNatural().setPerLaboral(new PerLaboral());
					persona.getNatural().getPerLaboral().setDtInicioContratoDesde(o.getDtFechaDesde());
					persona.getNatural().getPerLaboral().setDtInicioContratoHasta(o.getDtFechaHasta());
				}else if(o.getIntFechaBusq().equals(Constante.PARAM_T_TIPOFILTROFECHAS_FINCONTRATO)){
					persona.getNatural().setPerLaboral(new PerLaboral());
					persona.getNatural().getPerLaboral().setDtFinContratoDesde(o.getDtFechaDesde());
					persona.getNatural().getPerLaboral().setDtFinContratoHasta(o.getDtFechaHasta());
				}
				PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
	    		listPersonaNatu = personaFacade.getFullListPerNaturalBusqueda(persona);
	    		if(listPersonaNatu!=null)log.info("listPersona.size: "+listPersonaNatu.size());
			}else if(o.getPersona().getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA) ||
					o.getPersona().getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_AMBOS)){
				
			}
			
			if(listPersonaNatu!=null){
				//Relacionar SocioEstructura con Persona
				listaSocioComp = new ArrayList<SocioComp>();
				
				for(Persona perNatural : listPersonaNatu){
					SocioPK socioPk = new SocioPK();
					socioPk.setIntIdEmpresa(o.getSocio().getId().getIntIdEmpresa());
					socioPk.setIntIdPersona(perNatural.getIntIdPersona());
					//Obteniendo SocioEstructura
					listaSocioEstructura = boSocioEstructura.getListaSocioEstructuraPorSocioPK(socioPk);	
					SocioComp socioComp = null;
					String strTipoSocio = "";
					String strModalidad = "";
					
					for(int j=0; j<listaSocioEstructura.size(); j++){
						Boolean blnValidSocio = true;
						//valida el TipoSocio
						if(!o.getSocio().getSocioEstructura().getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_TODOS) &&
								!listaSocioEstructura.get(j).getIntTipoSocio().equals(o.getSocio().getSocioEstructura().getIntTipoSocio())){
							blnValidSocio = false;
						}
						//valida la Modalidad
						if(!o.getSocio().getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_TODOS) &&
								!listaSocioEstructura.get(j).getIntModalidad().equals(o.getSocio().getSocioEstructura().getIntModalidad())){
							blnValidSocio = false;
						}
						//valida la Sucursal y Subsucursal
						if(o.getIntTipoSucBusq().equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_SOCIO)){
							if(!o.getIntSucursalBusq().equals(0) &&
									!listaSocioEstructura.get(j).getIntIdSucursalAdministra().equals(o.getIntSucursalBusq())){
								blnValidSocio = false;
							}
							if(!o.getIntSubsucursalBusq().equals(0) &&
									!listaSocioEstructura.get(j).getIntIdSubsucurAdministra().equals(o.getIntSubsucursalBusq())){
								blnValidSocio = false;
							}
						}else if(o.getIntTipoSucBusq().equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_USUARIO)){
							if(!o.getIntSucursalBusq().equals(0) &&
									!listaSocioEstructura.get(j).getIntIdSucursalUsuario().equals(o.getIntSucursalBusq())){
								blnValidSocio = false;
							}
							if(!o.getIntSubsucursalBusq().equals(0) &&
									!listaSocioEstructura.get(j).getIntIdSubSucursalUsuario().equals(o.getIntSubsucursalBusq())){
								blnValidSocio = false;
							}
						}else if(o.getIntTipoSucBusq().equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_PLANILLA)){
							/*if(!o.getIntSucursalBusq().equals(0) &&
									!listaSocioEstructura.get(j).getIntIdSucursalUsuario().equals(o.getIntSucursalBusq())){
								blnValidSocio = false;
							}
							if(!o.getIntSubsucursalBusq().equals(0) &&
									!listaSocioEstructura.get(j).getIntIdSubSucursalUsuario().equals(o.getIntSubsucursalBusq())){
								blnValidSocio = false;
							}*/
						}
						log.info("listPersonaNatu.get(i).getIntIdPersona(): "+perNatural.getIntIdPersona());
						if(blnValidSocio){
							List<Tabla> listTabla = null;
							//Obteniendo el socio
							if(socioComp==null){
								socioComp = new SocioComp();
								socioComp.setSocio(boSocio.getSocioPorPK(socioPk));
								socioComp.setPersona(perNatural);
								socioComp.getSocio().setSocioEstructura(listaSocioEstructura.get(j));
								//Agregado 17/11/2012 20:13
								CuentaFacadeLocal cuentaFacade = (CuentaFacadeLocal)EJBFactory.getLocal(CuentaFacadeLocal.class);
								List<CuentaIntegrante> listaCuentaIntegrante= new ArrayList<CuentaIntegrante>();
								listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(o.getPersona().getIntIdPersona(), 2);
								if(listaCuentaIntegrante!=null && listaCuentaIntegrante.size()>0){
									for(CuentaIntegrante integrante : listaCuentaIntegrante){
										cuenta = new Cuenta();
										cuenta.setId(new CuentaId());
										cuenta.getId().setIntPersEmpresaPk(integrante.getId().getIntPersEmpresaPk());
										cuenta.getId().setIntCuenta(integrante.getId().getIntCuenta());
										cuenta.setIntParaSituacionCuentaCod(o.getIntCtasActivInactiv());
										/**
										 * @author Christian De los Ríos
										 * Fecha : 13/01/2013
										 * Descripción: Se agrego el filtro de cuentas activas e inactivas de 
										 * 				acuerdo al check del formulario principal. Si no se elige
										 * 				nada, por defecto traerá todas las cuentas.
										 * 
										 * */
										cuenta = cuentaFacade.getCuentaPorPkYSituacion(cuenta);
										/************************************************************************/
										//cuenta = cuentaFacade.getCuentaPorId(cuenta.getId());
										socioComp.setCuenta(cuenta);
									}
								}
								////////////////////////////////////////////
								listaSocioComp.add(socioComp);
							}
							
							TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
							listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOSOCIO));
							
							for(int k=0; k<listTabla.size(); k++){
								if(listTabla.get(k).getIntIdDetalle().equals(socioComp.getSocio().getSocioEstructura().getIntTipoSocio())){
									log.info("tabla.strDescripcion: "+listTabla.get(k).getStrDescripcion());
									if(!strTipoSocio.contains(listTabla.get(k).getStrDescripcion())){
										strTipoSocio = strTipoSocio + "/" + listTabla.get(k).getStrDescripcion();
									}
									break;
								}
							}
							
							listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_MODALIDADPLANILLA));
							
							for(int k=0; k<listTabla.size(); k++){
								if(listTabla.get(k).getIntIdDetalle().equals(socioComp.getSocio().getSocioEstructura().getIntModalidad())){
									log.info("tabla.strDescripcion: "+listTabla.get(k).getStrDescripcion());
									if(!strModalidad.contains(listTabla.get(k).getStrDescripcion())){
										strModalidad = strModalidad + "/" + listTabla.get(k).getStrDescripcion();
									}
									break;
								}
							}
						}
						if(strTipoSocio.length()>0)socioComp.setStrTipoSocio(strTipoSocio.substring(1, strTipoSocio.length()));
						if(strModalidad.length()>0)socioComp.setStrModalidad(strModalidad.substring(1, strModalidad.length()));
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSocioComp;
	}

	public SocioComp grabarSocioNatural(SocioComp o) throws BusinessException{
		Persona personaNat = null;
		SocioComp socioComp = null; 
		List<SocioEstructura> listSocioEstructura = null;
		
		try{
			//Parametros de PersonaEmpresa
			o.getPersona().setPersonaEmpresa(new PersonaEmpresa());
			o.getPersona().getPersonaEmpresa().setId(new PersonaEmpresaPK());
			o.getPersona().getPersonaEmpresa().getId().setIntIdEmpresa(o.getSocio().getId().getIntIdEmpresa());
			
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			if(o.getPersona().getIntIdPersona()==null){
				personaNat = facade.grabarPersonaNaturalTotal(o.getPersona());
			}else{
				personaNat = facade.modificarPersonaNaturalTotal(o.getPersona());
			}
			o.setPersona(personaNat);
			
			//grabando Socio
			o.getSocio().getId().setIntIdPersona(personaNat.getIntIdPersona());
			o.setSocio(boSocio.grabarSocio(o.getSocio()));
			
			if(o.getSocio().getListSocioEstructura()!=null){
				listSocioEstructura = socioEstructuraService.grabarListaDinamicaSocioEstructura(o.getSocio().getListSocioEstructura(),o.getSocio().getId());
				o.getSocio().setListSocioEstructura(listSocioEstructura);
			}
			
			socioComp = o;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
		return socioComp;
	}
	
	public SocioComp modificarSocioNatural(SocioComp o) throws BusinessException{
		Persona personaNat = null;
		SocioComp socioComp = null;
		List<SocioEstructura> listSocioEstructura = null;
//		Socio socio = null;
		
		try{
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			personaNat = facade.modificarPersonaNaturalTotal(o.getPersona());
			o.setPersona(personaNat);
			log.info("personaNat.intIdPersona: "+personaNat.getIntIdPersona());
			
			o.setSocio(boSocio.modificarSocio(o.getSocio()));
			log.info("socio.intIdPersona: "+o.getSocio().getId().getIntIdPersona());
			log.info("socio.intIdEmpresa: "+o.getSocio().getId().getIntIdEmpresa());
			
			listSocioEstructura = socioEstructuraService.grabarListaDinamicaSocioEstructura(o.getSocio().getListSocioEstructura(),o.getSocio().getId());
			o.getSocio().setListSocioEstructura(listSocioEstructura);
			
			socioComp = o;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
		return socioComp;
	}

	public SocioComp getSocioNatural(SocioPK o) throws BusinessException{
		log.info("-----------------------Debugging SocioService.getSocioNatural-----------------------------");
		List<SocioEstructura> listaSocioEstruc = null;
		Persona persona = null;
		SocioComp socioComp = null;
		Socio socio = null;
		Archivo archivo = null;
		
		try{
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			
			log.info("o.intIdEmpresa: "+o.getIntIdEmpresa());
			log.info("o.intIdPersona: "+o.getIntIdPersona());
			
			socio = boSocio.getSocioPorPK(o);
			socioComp = new SocioComp();
			if(socio!=null){
				log.info("socio.intIdPersona: "+socio.getId().getIntIdPersona());
				
				socioComp.setSocio(socio);
				
				//Buscar SocioEstructura
				listaSocioEstruc = boSocioEstructura.getListaSocioEstructuraPorSocioPK(o);
				log.info("Se obtuvo listaSocioEstruc.size: "+listaSocioEstruc.size());
				if(listaSocioEstruc!=null){
					socioComp.getSocio().setListSocioEstructura(listaSocioEstruc);
				}
			}
			
			//RODO
			int intCantidadHijos = boSocio.getCantidadHijos(socio.getId().getIntIdPersona());
			socioComp.setIntCantidadHijos(intCantidadHijos);
			
			Date dtFechaIngreso = boSocio.getFechaIngreso(socio.getId().getIntIdPersona());
			socioComp.setDtFechaIngreso(dtFechaIngreso);

			//sOCIO 
			SocioEstructuraPK socioEstructuraPK = new SocioEstructuraPK();
			socioEstructuraPK.setIntIdPersona(o.getIntIdPersona());
			socioEstructuraPK.setIntIdEmpresa(o.getIntIdEmpresa());
			
			//boSocioEstructura.getSocioEstructuraPorPK(socioEstructuraPK);
			
			//Buscar persona
			PersonaEmpresaPK pk = new PersonaEmpresaPK();
			pk.setIntIdEmpresa(o.getIntIdEmpresa());
			pk.setIntIdPersona(o.getIntIdPersona());
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			persona = personaFacade.getPersonaNaturalPorPersonaEmpresaPKYTipoVinculo(pk,Constante.PARAM_T_TIPOVINCULO_TIENEEMPRESA);
    		log.info("persona.intIdPersona: "+persona.getIntIdPersona());
    		//mostrar el Documento DNI aparte
    		if(persona.getListaDocumento()!=null){
    			for(int i=0; i<persona.getListaDocumento().size(); i++){
        			if(persona.getListaDocumento().get(i).getIntTipoIdentidadCod().toString().equals(Constante.PARAM_T_TIPODOCUMENTO_DNI)){
        				log.info("documento.strNumeroIdentidad: "+persona.getListaDocumento().get(i).getStrNumeroIdentidad());
        				persona.setDocumento(persona.getListaDocumento().get(i));
        			}
        		}
    		}else{
    			persona.setDocumento(new Documento());
    		}
    		
    		/**
			 * Agregado mostrar direccion 23-01-2013
			 * */
			if(persona.getListaDomicilio()!=null && persona.getListaDomicilio().size()>0){
				for (Domicilio domicilio : persona.getListaDomicilio()) {
					if(domicilio.getIntParaItemArchivo()!=null){
						archivo = new Archivo();
    					archivo.setId(new ArchivoId());
    					archivo.getId().setIntItemArchivo(domicilio.getIntParaItemArchivo());
    					archivo.getId().setIntItemHistorico(domicilio.getIntParaItemHistorico());
    					archivo.getId().setIntParaTipoCod(domicilio.getIntParaTipoArchivo());
    					archivo = generalFacade.getArchivoPorPK(archivo.getId());
    					
    					domicilio.setCroquis(archivo);
					}
				}
			}
			/* Fin 23-01-2013 */
			
    		if(persona!=null){
    			socioComp.setPersona(persona);
    		}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socioComp;
	}

	public SocioComp getSocioNatuPorDocIdentidadYIdEmpresaYTipoVinculo(Integer intTipoDoc,String strNumIdentidad,Integer intEmpresa,Integer intTipoVinculo) throws BusinessException{
		log.info("-----------------------Debugging SocioService.getSocioNatuPorDocuIdentidad-----------------------------");
		List<SocioEstructura> listaSocioEstruc = null;
		Persona persona = null;
		SocioComp socioComp = null;
		Socio socio = null;
		Archivo archivo = null;
		
		try{
			socioComp = new SocioComp();
			
			//Buscar persona
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresaYTipoVinculo(intTipoDoc,strNumIdentidad,intEmpresa,intTipoVinculo);
    		
    		if(persona!=null){
    			log.info("persona.intIdPersona: "+persona.getIntIdPersona());
    			//mostrar el dni separado de los otros documentos
    			if(persona.getListaDocumento()!=null && persona.getListaDocumento().size()>0){
    				for(int i=0; i<persona.getListaDocumento().size(); i++){
    					Documento doc = persona.getListaDocumento().get(i);
    					if(doc.getIntTipoIdentidadCod().toString().equals(Constante.PARAM_T_TIPODOCUMENTO_DNI)){
    						persona.setDocumento(doc);
    						persona.getListaDocumento().remove(i);
    						break;
    					}
    				}
    			}
    			/**
    			 * Agregado mostrar direccion 23-01-2013
    			 * */
    			if(persona.getListaDomicilio()!=null && persona.getListaDomicilio().size()>0){
    				for (Domicilio domicilio : persona.getListaDomicilio()) {
    					if(domicilio.getIntParaItemArchivo()!=null){
    						archivo = new Archivo();
        					archivo.setId(new ArchivoId());
        					archivo.getId().setIntItemArchivo(domicilio.getIntParaItemArchivo());
        					archivo.getId().setIntItemHistorico(domicilio.getIntParaItemHistorico());
        					archivo.getId().setIntParaTipoCod(domicilio.getIntParaTipoArchivo());
        					archivo = generalFacade.getArchivoPorPK(archivo.getId());
        					
        					domicilio.setCroquis(archivo);
    					}
					}
    			}
    			/* Fin 23-01-2013 */
    			
    			//mostrar los datos de Persona
    			socioComp.setPersona(persona);
    			//validar PersonaRol
    			if(persona.getPersonaEmpresa()!=null && persona.getPersonaEmpresa().getListaPersonaRol()!=null){
    				
    			}else{
    				socioComp.setPersonaRol(new PersonaRol());
    				socioComp.getPersonaRol().setId(new PersonaRolPK());
    			}
    			//validar PerLaboral
    			if(persona.getNatural().getListaPerLaboral()!=null && persona.getNatural().getListaPerLaboral().size()>0){
    				persona.getNatural().setPerLaboral(persona.getNatural().getListaPerLaboral().get(0));
    			}else{
    				persona.getNatural().setPerLaboral(new PerLaboral());
    			}
    			
    			//Buscar Socio
    			SocioPK socioPK = new SocioPK();
    			socioPK.setIntIdEmpresa(intEmpresa);
    			socioPK.setIntIdPersona(persona.getIntIdPersona());
    			socio = boSocio.getSocioPorPK(socioPK);
    			if(socio!=null){
    				log.info("socio.intIdPersona: "+socio.getId().getIntIdPersona());
    				socioComp.setSocio(socio);
    				//Buscar SocioEstructura
    				listaSocioEstruc = boSocioEstructura.getListaSocioEstructuraPorSocioPK(socio.getId());
    				if(listaSocioEstruc!=null){
    					log.info("Se obtuvo listaSocioEstruc.size: "+listaSocioEstruc.size());
    					socioComp.getSocio().setListSocioEstructura(listaSocioEstruc);
    				}
    			}else{
    				socioComp.setSocio(new Socio());
    				socioComp.getSocio().setStrApePatSoc(persona.getNatural().getStrApellidoPaterno());
    				socioComp.getSocio().setStrApeMatSoc(persona.getNatural().getStrApellidoMaterno());
    				socioComp.getSocio().setStrNombreSoc(persona.getNatural().getStrNombres());
    			}
    		}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socioComp;
	}
	
	/**
	 * Recupera sociocomp
	 * @param intTipoDoc
	 * @param strNumIdentidad
	 * @param intEmpresa
	 * @return
	 * @throws BusinessException
	 */
	public SocioComp getSocioNatuPorDocIdentidadYIdEmpresa(Integer intTipoDoc,String strNumIdentidad,Integer intEmpresa) throws BusinessException{
		log.info("-----------------------Debugging SocioService.getSocioNatuPorDocuIdentidad-----------------------------");
		List<SocioEstructura> listaSocioEstruc = null;
		Persona persona = null;
		SocioComp socioComp = null;
		Socio socio = null;
		CuentaIntegrante cuentaIntegrante = null;
		Cuenta cuenta = null;
		List<CuentaConcepto> listaCuentaConcepto = null;
		
		try{
			socioComp = new SocioComp();
			
			//Buscar persona
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			ConceptoFacadeRemote cuentaConceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(intTipoDoc,strNumIdentidad,intEmpresa);
    		
    		if(persona!=null){
    			log.info("persona.intIdPersona: "+persona.getIntIdPersona());
    			//mostrar el dni separado de los otros documentos
    			if(persona.getListaDocumento()!=null && persona.getListaDocumento().size()>0){
    				for(int i=0; i<persona.getListaDocumento().size(); i++){
    					Documento doc = persona.getListaDocumento().get(i);
    					if(doc.getIntTipoIdentidadCod().toString().equals(Constante.PARAM_T_TIPODOCUMENTO_DNI)){
    						persona.setDocumento(doc);
    						persona.getListaDocumento().remove(i);
    						break;
    					}
    				}
    			}
    			//mostrar los datos de Persona
    			socioComp.setPersona(persona);
    			//validar PersonaRol
    			if(persona.getPersonaEmpresa()!=null && persona.getPersonaEmpresa().getListaPersonaRol()!=null){
    				
    				
    				
    			}else{
    				socioComp.setPersonaRol(new PersonaRol());
    				socioComp.getPersonaRol().setId(new PersonaRolPK());
    			}
    			//validar PerLaboral
    			if(persona.getNatural().getListaPerLaboral()!=null && persona.getNatural().getListaPerLaboral().size()>0){
    				persona.getNatural().setPerLaboral(persona.getNatural().getListaPerLaboral().get(0));
    			}else{
    				persona.getNatural().setPerLaboral(new PerLaboral());
    			}
    			
    			//Buscar Socio
    			SocioPK socioPK = new SocioPK();
    			socioPK.setIntIdEmpresa(intEmpresa);
    			socioPK.setIntIdPersona(persona.getIntIdPersona());
    			socio = boSocio.getSocioPorPK(socioPK);
    			if(socio!=null){
    				socioComp.setSocio(socio);
    				//Buscar SocioEstructura
    				listaSocioEstruc = boSocioEstructura.getListaSocioEstructuraPorSocioPK(socio.getId());
    				if(listaSocioEstruc!=null){
    					log.info("Se obtuvo listaSocioEstruc.size: "+listaSocioEstruc.size());
    					socioComp.getSocio().setListSocioEstructura(listaSocioEstruc);
    				}
    			}else{
    				socioComp.setSocio(new Socio());
    				socioComp.getSocio().setStrApePatSoc(persona.getNatural().getStrApellidoPaterno());
    				socioComp.getSocio().setStrApeMatSoc(persona.getNatural().getStrApellidoMaterno());
    				socioComp.getSocio().setStrNombreSoc(persona.getNatural().getStrNombres());
    			}
    			
    			cuentaIntegrante = new CuentaIntegrante();
        		cuentaIntegrante.setId(new CuentaIntegranteId());
        		cuentaIntegrante.getId().setIntPersEmpresaPk(intEmpresa);
        		cuentaIntegrante.getId().setIntPersonaIntegrante(persona.getIntIdPersona());
        		cuentaIntegrante = cuentaFacade.getCuentaIntegrantePorPkSocio(cuentaIntegrante);
        		
        		if(cuentaIntegrante!=null){
        			cuenta = new Cuenta();
            		cuenta.setId(new CuentaId());
            		cuenta.getId().setIntPersEmpresaPk(intEmpresa);
            		cuenta.getId().setIntCuenta(cuentaIntegrante.getId().getIntCuenta());
            		cuenta = cuentaFacade.getCuentaPorIdCuenta(cuenta);
            		
            		if(cuenta != null && cuenta.getId() != null){
            			if(cuenta.getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
            				listaCuentaConcepto = cuentaConceptoFacade.getListaCuentaConceptoPorPkCuenta(cuenta.getId());
                    		if(listaCuentaConcepto!=null){
                    			cuenta.setListaConcepto(listaCuentaConcepto);
                    		}
                    		socioComp.setCuenta(cuenta);
            			}
            			
            		}
            		
            		
            		
        		}
    		}
		}catch(BusinessException e){
			log.error("Error BusinessException en getSocioNatuPorDocIdentidadYIdEmpresa ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error Exception en getSocioNatuPorDocIdentidadYIdEmpresa ---> "+e);
			throw new BusinessException(e);
		}
		return socioComp;
	}
	
	public List<Socio> getListaSocioPorEmpresaYTipoIntegranteYINCuenta(Integer intEmpresa,Integer intTipoIntegrante,String strINCuenta) throws BusinessException{
		List<Socio> lista = null;
		String strCsv = null;
		try{
			strCsv = boCuentaIntegrante.getCsvPersonaPorEmpresaYTipoIntegranteYINCuenta(intEmpresa, intTipoIntegrante, strINCuenta);
			lista = boSocio.getListaSocioPorEmpresaYINPersona(intEmpresa, strCsv);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}	
	
	
	/**
	 * Recupera Socio comp con cuenta en situacion activa
	 * @param intEmpresa
	 * @param intTipoRol
	 * @param nombre
	 * @param dni
	 * @return
	 * @throws BusinessException
	 */
	public List<SocioComp> getListaBuscarSocioConCuentaVigente(Integer intEmpresa,Integer intTipoRol,String nombre,String dni) throws BusinessException{
		
		log.info("-----------------------Debugging SocioService.getListaBuscarSocioConCuentaVigente-----------------------------");
		List<SocioEstructura> listaSocioEstruc = null;
		SocioComp socioComp = null;
		List<SocioComp> listaSocioComp = null;
		
		Socio socio = null;
		CuentaIntegrante cuentaIntegrante = null;
		Cuenta cuenta = null;
		List<CuentaConcepto> listaCuentaConcepto = null;
		
		try{
			
			//Buscar persona
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			List<Persona> listaPersona = personaFacade.getListaBuscarPersonaNatural(intEmpresa, nombre,Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI), dni, intTipoRol);
			CuentaFacadeRemote   cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
	    	ConceptoFacadeRemote cuentaConceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
	    	
	    	
	    	if (listaPersona != null){	
	    	siguienteSocio:
			for (Persona persona : listaPersona) {
				socioComp = new SocioComp();
				socioComp.setPersona(persona);
				
				//Buscar Socio
    			SocioPK socioPK = new SocioPK();
    			socioPK.setIntIdEmpresa(intEmpresa);
    			socioPK.setIntIdPersona(persona.getIntIdPersona());
    			socio = boSocio.getSocioPorPK(socioPK);
    			if(socio != null){
    				socioComp.setSocio(socio);
    				listaSocioEstruc = boSocioEstructura.getListaSocioEstructuraPorSocioPK(socio.getId());
    				if(listaSocioEstruc!=null){
    					log.info("Se obtuvo listaSocioEstruc.size: "+listaSocioEstruc.size());
    					socioComp.getSocio().setListSocioEstructura(listaSocioEstruc);
    				}
    			}else{
    				continue siguienteSocio;
    			}
    			
    			cuentaIntegrante = new CuentaIntegrante();
        		cuentaIntegrante.setId(new CuentaIntegranteId());
        		cuentaIntegrante.getId().setIntPersEmpresaPk(intEmpresa);
        		cuentaIntegrante.getId().setIntPersonaIntegrante(persona.getIntIdPersona());
        		cuentaIntegrante = cuentaFacade.getCuentaIntegrantePorPkSocio(cuentaIntegrante);
        		
        		
        		if(cuentaIntegrante != null){
        			Cuenta cuentaResult = new Cuenta();
        			cuenta = new Cuenta();
            		cuenta.setId(new CuentaId());
            		cuenta.getId().setIntPersEmpresaPk(intEmpresa);
            		cuenta.getId().setIntCuenta(cuentaIntegrante.getId().getIntCuenta());
            		//cuenta = cuentaFacade.getCuentaPorIdCuenta(cuenta);
            		
            		cuentaResult = cuentaFacade.getCuentaPorIdCuenta(cuenta);
            		if(cuentaResult != null && cuentaResult.getId() != null){
            			if(cuentaResult.getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
            				listaCuentaConcepto = cuentaConceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaResult.getId());
                    		if(listaCuentaConcepto!=null){
                    			cuentaResult.setListaConcepto(listaCuentaConcepto);
                    		}
                    		
                    		socioComp.setCuenta(cuentaResult);
                    		
                    		if (socioComp.getCuenta().getIntParaSituacionCuentaCod().equals(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)){
                    			if (listaSocioComp == null) listaSocioComp = new ArrayList<SocioComp>();
                        		listaSocioComp.add(socioComp);
                    		}
            				
            			}
            			
            		}

        		}else{
        			continue siguienteSocio;
        		}
				
            }
	      }	
    		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
		return listaSocioComp;
		
	}
	
	/**
	 * Recupera el Socio COmp en base a una cuenta definida, que puede ser un expediente de credito, etc
	 * @param intTipoDoc
	 * @param strNumIdentidad
	 * @param intEmpresa
	 * @param cuentaExpediente
	 * @return
	 * @throws BusinessException
	 */
	public SocioComp getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(Integer intTipoDoc,String strNumIdentidad,Integer intEmpresa, Cuenta cuentaExpediente) throws BusinessException{
		log.info("-----------------------Debugging SocioService.getSocioNatuPorDocuIdentidad-----------------------------");
		List<SocioEstructura> listaSocioEstruc = null;
		Persona persona = null;
		SocioComp socioComp = null;
		Socio socio = null;
//		CuentaIntegrante cuentaIntegrante = null;
		Cuenta cuentaCargada = null;
		List<CuentaConcepto> listaCuentaConcepto = null;
		
		try{
			socioComp = new SocioComp();
			
			//Buscar persona
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			ConceptoFacadeRemote cuentaConceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(intTipoDoc,strNumIdentidad,intEmpresa);
    		
    		if(persona!=null){
    			log.info("persona.intIdPersona: "+persona.getIntIdPersona());
    			//mostrar el dni separado de los otros documentos
    			if(persona.getListaDocumento()!=null && persona.getListaDocumento().size()>0){
    				for(int i=0; i<persona.getListaDocumento().size(); i++){
    					Documento doc = persona.getListaDocumento().get(i);
    					if(doc.getIntTipoIdentidadCod().toString().equals(Constante.PARAM_T_TIPODOCUMENTO_DNI)){
    						persona.setDocumento(doc);
    						persona.getListaDocumento().remove(i);
    						break;
    					}
    				}
    			}
    			//mostrar los datos de Persona
    			socioComp.setPersona(persona);
    			//validar PersonaRol
    			if(persona.getPersonaEmpresa()!=null && persona.getPersonaEmpresa().getListaPersonaRol()!=null){
    				
    				
    				
    			}else{
    				socioComp.setPersonaRol(new PersonaRol());
    				socioComp.getPersonaRol().setId(new PersonaRolPK());
    			}
    			//validar PerLaboral
    			if(persona.getNatural().getListaPerLaboral()!=null && persona.getNatural().getListaPerLaboral().size()>0){
    				persona.getNatural().setPerLaboral(persona.getNatural().getListaPerLaboral().get(0));
    			}else{
    				persona.getNatural().setPerLaboral(new PerLaboral());
    			}
    			
    			//Buscar Socio
    			SocioPK socioPK = new SocioPK();
    			socioPK.setIntIdEmpresa(intEmpresa);
    			socioPK.setIntIdPersona(persona.getIntIdPersona());
    			socio = boSocio.getSocioPorPK(socioPK);
    			if(socio!=null){
    				socioComp.setSocio(socio);
    				//Buscar SocioEstructura
    				listaSocioEstruc = boSocioEstructura.getListaSocioEstructuraPorSocioPK(socio.getId());
    				if(listaSocioEstruc!=null && !listaSocioEstruc.isEmpty()){
    					log.info("Se obtuvo listaSocioEstruc.size: "+listaSocioEstruc.size());
    					socioComp.getSocio().setListSocioEstructura(listaSocioEstruc);
    				}
    			}else{
    				socioComp.setSocio(new Socio());
    				socioComp.getSocio().setStrApePatSoc(persona.getNatural().getStrApellidoPaterno());
    				socioComp.getSocio().setStrApeMatSoc(persona.getNatural().getStrApellidoMaterno());
    				socioComp.getSocio().setStrNombreSoc(persona.getNatural().getStrNombres());
    			}

    			cuentaCargada = cuentaFacade.getCuentaPorIdCuenta(cuentaExpediente);
    			if(cuentaCargada != null){
    				listaCuentaConcepto = cuentaConceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaCargada.getId());
            		if(listaCuentaConcepto!=null){
            			cuentaCargada.setListaConcepto(listaCuentaConcepto);
            		}
            		socioComp.setCuenta(cuentaCargada);
    			}
        		
        		
        		//}
    		}
		}catch(BusinessException e){
			log.error("Error BusinessException en getSocioNatuPorDocIdentidadYIdEmpresa ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error Exception en getSocioNatuPorDocIdentidadYIdEmpresa ---> "+e);
			throw new BusinessException(e);
		}
		return socioComp;
	}
	
	
	
	/**
	 * Recupera sociocomp
	 * @param intTipoDoc
	 * @param strNumIdentidad
	 * @param intEmpresa
	 * @return
	 * @throws BusinessException
	 */
	public SocioComp getSocioCompXDocEmpCuenta(Integer intTipoDoc,String strNumIdentidad,Integer intEmpresa, Integer intCuenta) throws BusinessException{
		List<SocioEstructura> listaSocioEstruc = null;
		Persona persona = null;
		SocioComp socioComp = null;
		Socio socio = null;
		CuentaIntegrante cuentaIntegrante = null;
		Cuenta cuenta = null;
		//List<CuentaConcepto> listaCuentaConcepto = null;
		
		try{
			socioComp = new SocioComp();
			
			//Buscar persona
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			//ConceptoFacadeRemote cuentaConceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(intTipoDoc,strNumIdentidad,intEmpresa);
    		
    		if(persona!=null){
    			if(persona.getListaDocumento()!=null && persona.getListaDocumento().size()>0){
    				for(int i=0; i<persona.getListaDocumento().size(); i++){
    					Documento doc = persona.getListaDocumento().get(i);
    					if(doc.getIntTipoIdentidadCod().toString().equals(Constante.PARAM_T_TIPODOCUMENTO_DNI)){
    						persona.setDocumento(doc);
    						persona.getListaDocumento().remove(i);
    						break;
    					}
    				}
    			}
    			//mostrar los datos de Persona
    			socioComp.setPersona(persona);
    			
    			//validar PerLaboral
    			if(persona.getNatural().getListaPerLaboral()!=null && persona.getNatural().getListaPerLaboral().size()>0){
    				persona.getNatural().setPerLaboral(persona.getNatural().getListaPerLaboral().get(0));
    			}else{
    				persona.getNatural().setPerLaboral(new PerLaboral());
    			}
    			
    			//Buscar Socio
    			SocioPK socioPK = new SocioPK();
    			socioPK.setIntIdEmpresa(intEmpresa);
    			socioPK.setIntIdPersona(persona.getIntIdPersona());
    			socio = boSocio.getSocioPorPK(socioPK);
    			if(socio!=null){
    				socioComp.setSocio(socio);
    				//Buscar SocioEstructura
    				listaSocioEstruc = boSocioEstructura.getListaSocioEstructuraPorSocioPK(socio.getId());
    				if(listaSocioEstruc!=null){
    					socioComp.getSocio().setListSocioEstructura(listaSocioEstruc);
    				}
    			}else{
    				socioComp.setSocio(new Socio());
    				socioComp.getSocio().setStrApePatSoc(persona.getNatural().getStrApellidoPaterno());
    				socioComp.getSocio().setStrApeMatSoc(persona.getNatural().getStrApellidoMaterno());
    				socioComp.getSocio().setStrNombreSoc(persona.getNatural().getStrNombres());
    			}
    			
    			cuentaIntegrante = new CuentaIntegrante();
        		cuentaIntegrante.setId(new CuentaIntegranteId());
        		cuentaIntegrante.getId().setIntPersEmpresaPk(intEmpresa);
        		cuentaIntegrante.getId().setIntPersonaIntegrante(persona.getIntIdPersona());
        		cuentaIntegrante.getId().setIntCuenta(intCuenta);
        		
        		cuentaIntegrante = boCuentaIntegrante.getCuentaIntegrantePorPK(cuentaIntegrante.getId());
        		        		
        		if(cuentaIntegrante!=null){
        			cuenta = new Cuenta();
            		cuenta.setId(new CuentaId());
            		cuenta.getId().setIntPersEmpresaPk(intEmpresa);
            		cuenta.getId().setIntCuenta(cuentaIntegrante.getId().getIntCuenta());
            		cuenta = cuentaFacade.getCuentaPorIdCuenta(cuenta);
            		if(cuenta != null){
            			socioComp.setCuenta(cuenta);
            		}	
        		}
    		}
		}catch(BusinessException e){
			log.error("Error BusinessException en getSocioCompXDocEmpCuenta ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error Exception en getSocioCompXDocEmpCuenta ---> "+e);
			throw new BusinessException(e);
		}
		return socioComp;
	}
	
	
	/**
	 * Recupera los sociossegun filtros de busqueda
	 * @param socioCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<SocioComp> getListaSocioPorFiltrosBusq (SocioComp socioCompBusq)throws BusinessException{
		List<SocioComp> lstSocioComp = null;
		List<Socio> lstSocio = null;
		
		try {	
			lstSocio = boSocio.getListaSocioPorFiltrosBusq(socioCompBusq);
			if(lstSocio != null && !lstSocio.isEmpty()){
				lstSocioComp = new ArrayList<SocioComp>();
				for (Socio socioRes : lstSocio) {
					SocioComp socioComp =  new SocioComp();  //null; //new SocioComp();
					socioComp.setSocio(socioRes);
					lstSocioComp.add(socioComp);
					/*socioComp = getListaSocioCompParaBusquedaFiltros(socioRes.getId().getIntIdPersona());
					if(socioComp != null){
						
						socioComp.getSocio().setStrNroDocumento(socioRes.getStrNroDocumento());
						socioComp.getSocio().setIntTipoSocio(socioRes.getIntTipoSocio());
						socioComp.getSocio().setIntCondicionLab(socioRes.getIntCondicionLab());
						socioComp.getSocio().setIntParaModalidad(socioRes.getIntParaModalidad());
						socioComp.getSocio().setIntSucuAdministra(socioRes.getIntSucuAdministra());
						socioComp.getSocio().setIntParaTipoCta(socioRes.getIntParaTipoCta());
						socioComp.getSocio().setStrNumeroCta(socioRes.getStrNumeroCta());
						socioComp.getSocio().setIntParaSituacionCta(socioRes.getIntParaSituacionCta());
						socioComp.getSocio().setTsFechaRegistroCta(socioRes.getTsFechaRegistroCta());
						socioComp.getSocio().setStrFechaRegistroCta(Constante.sdf.format(socioRes.getTsFechaRegistroCta()));
						lstSocioComp.add(socioComp);
					}*/
				}
			}
			
		} catch (Exception e) {
			log.error("Error en getListaSocioPorFiltrosBusq --> "+e);		}
		return lstSocioComp;
		
	}
	
	
	/**
	 * Completa el objeto socio comp para la busqueda.
	 * Se agrega Persona, Natural, Domicilio, COmunicacion, Contacto, Cta Bancaria, Documentos,
	 * Juridica, Lista Actividad economica, Lista tipo comprobante, 
	 * Socio, Socio estructra, lsiat socio estrucutra, cuenta y lista cuenta integrante.
	 * @param intPersonaBusq
	 * @return
	 * @throws BusinessException
	 */
	public SocioComp getListaSocioCompParaBusquedaFiltros (Integer intPersonaBusq)throws BusinessException{
		SocioComp socioComp = null;
		Persona persona = null;
		Juridica juridica = null;
		List<Documento> lstDocumentos = null;
		Socio socio = null;
		List<SocioEstructura> lstSocioEstructura = null;
		Cuenta cuenta = null;
		List<CuentaIntegrante> lstCtaIntegrante = null;
		
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeLocal socioFacade = null;
		CuentaFacadeLocal cuentaFacade = null;
		try {
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			socioFacade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
			cuentaFacade = (CuentaFacadeLocal)EJBFactory.getLocal(CuentaFacadeLocal.class);
			
			
			socioComp = new SocioComp();
			
			//1. Recupera Persona, Natural,Domicilio,Comunicacion, Contacto, CtaBancaria  
			persona = personaFacade.getPersonaNaturalPorIdPersona(intPersonaBusq);
			if(persona != null){
				
				// 2. Recuperar Documentos
				lstDocumentos = personaFacade.getLstDocumentoPorPKPersona(intPersonaBusq);
				if(lstDocumentos != null && !lstDocumentos.isEmpty()){
					persona.setListaDocumento(lstDocumentos);	
				}
				
				// 3.Recupera Juridica, Lista Actividad Economica y Lista Tipo COmprbante
				juridica = personaFacade.getJuridicaDetalladaPorIdPersona(intPersonaBusq);
				if(juridica != null){
					persona.setJuridica(juridica);
				}
				
				socioComp.setPersona(persona);
			}
			
			
			// 4. Se recupera Socio
			SocioPK socioId = new SocioPK();
			socioId.setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
			socioId.setIntIdPersona(intPersonaBusq);
			
			socio = socioFacade.getSocioPorPK(socioId);
			
			if(socio != null){
				// 5. se recupera socio Estrucutra y la de origen
				lstSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(intPersonaBusq, Constante.PARAM_EMPRESASESION);
				if(lstSocioEstructura != null && !lstSocioEstructura.isEmpty()){
					socio.setListSocioEstructura(lstSocioEstructura);
					// Agregamos socio estrucutra de origern
					for (SocioEstructura socioEstOrig : lstSocioEstructura) {
						if(socioEstOrig.getIntTipoEstructura().compareTo(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)==0){
							socio.setSocioEstructura(socioEstOrig);
							break;
						}
					}
				}
				socioComp.setSocio(socio);
			}
			
			// 6. Recuperar cuenta
			lstCtaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(intPersonaBusq, Constante.PARAM_EMPRESASESION);
			cuenta = cuentaFacade.getCuentaActualPorIdPersona(intPersonaBusq, Constante.PARAM_EMPRESASESION);
			if(cuenta != null){
				lstCtaIntegrante= cuentaFacade.getListaCuentaIntegrantePorCuenta(cuenta.getId());
				if(lstCtaIntegrante != null && !lstCtaIntegrante.isEmpty()){
					cuenta.setListaIntegrante(lstCtaIntegrante);
				}
				socioComp.setCuenta(cuenta);
			}
			
			
		} catch (Exception e) {
			log.error(""+e);
		}
		return socioComp;

	}
	
	/**
	 * 
	 * @param socioCompBusq
	 * @return
	 */
	public SocioComp completarSocioCompBusqueda(Socio socioBusq){
		SocioComp socioCompResult = null;
		
		try {
			
			socioCompResult = getListaSocioCompParaBusquedaFiltros(socioBusq.getId().getIntIdPersona());
			if(socioCompResult != null){
				
				socioCompResult.getSocio().setStrNroDocumento(socioBusq.getStrNroDocumento());
				socioCompResult.getSocio().setIntTipoSocio(socioBusq.getIntTipoSocio());
				socioCompResult.getSocio().setIntCondicionLab(socioBusq.getIntCondicionLab());
				socioCompResult.getSocio().setIntParaModalidad(socioBusq.getIntParaModalidad());
				socioCompResult.getSocio().setIntSucuAdministra(socioBusq.getIntSucuAdministra());
				socioCompResult.getSocio().setIntParaTipoCta(socioBusq.getIntParaTipoCta());
				socioCompResult.getSocio().setStrNumeroCta(socioBusq.getStrNumeroCta());
				socioCompResult.getSocio().setIntParaSituacionCta(socioBusq.getIntParaSituacionCta());
				socioCompResult.getSocio().setTsFechaRegistroCta(socioBusq.getTsFechaRegistroCta());
				socioCompResult.getSocio().setStrFechaRegistroCta(Constante.sdf.format(socioBusq.getTsFechaRegistroCta()));				
			}
			
			
		} catch (Exception e) {
			
		}
		return socioCompResult;
		
	}
	
}
