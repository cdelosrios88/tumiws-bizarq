package pe.com.tumi.credito.socio.estructura.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.bo.AdminPadronBO;
import pe.com.tumi.credito.socio.estructura.bo.DescuentoBO;
import pe.com.tumi.credito.socio.estructura.bo.PadronBO;
import pe.com.tumi.credito.socio.estructura.bo.SolicitudPagoBO;
import pe.com.tumi.credito.socio.estructura.bo.SolicitudPagoDetalleBO;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPago;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPagoDetalle;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.domain.PerLaboral;

public class AdminPadronService {
	
	protected  	static Logger log = Logger.getLogger(AdminPadronService.class);
	protected 	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private AdminPadronBO boAdminPadron = (AdminPadronBO)TumiFactory.get(AdminPadronBO.class);
	private DescuentoBO boDescuento = (DescuentoBO)TumiFactory.get(DescuentoBO.class);
	private PadronBO boPadron = (PadronBO)TumiFactory.get(PadronBO.class);
	private SolicitudPagoBO boSolicitudPago = (SolicitudPagoBO)TumiFactory.get(SolicitudPagoBO.class);
	private SolicitudPagoDetalleBO boSolicitudPagoDetalle = (SolicitudPagoDetalleBO)TumiFactory.get(SolicitudPagoDetalleBO.class);
	private EstructuraService estructuraService = (EstructuraService)TumiFactory.get(EstructuraService.class);
	
	public AdminPadron grabarAdminPadron(AdminPadron o) throws BusinessException{
		//log.info("-----------------------Debugging AdminPadronService.grabarAdminPadron-----------------------------");
		AdminPadron adminPadron = null;		
		try{
			//log.info("fecha actual: "+dateFormat.format(fechaActual));
			o.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			o.setAdPaFechaRegistro(new Date());
			o.setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
			o.setIntPersUsuarioRegistroPk(Constante.PARAM_USUARIOSESION);
			adminPadron = boAdminPadron.grabarAdminPadron(o);			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adminPadron;
	}

	
	public boolean grabarAdminPadronyDescuentos(AdminPadron adminPadron, String ruta,List<String>listaNoProcesados)throws BusinessException, IOException{
		boolean exito = false;
		Table table = null;
		try{
			int contadorInCorrecto=0;
			int contadorCorrecto=0;
			adminPadron = boAdminPadron.grabarAdminPadron(adminPadron);			
			
			log.info("target[ruta a guardar]:"+ruta);
			table = new Table(new java.io.File(ruta));
			table.open(IfNonExistent.ERROR);			
			Record record = null;
			Number monto = null;
			Descuento descuento = new Descuento();
			Iterator<Record> iterator = table.recordIterator();
			while (iterator.hasNext()){
				record = iterator.next();
			    try{
			    	descuento.setStrDsteCpto(record.getStringValue("CPTO"));
			    	descuento.setStrDstePeriodo(record.getStringValue("MMAA"));
			    	descuento.setStrDsteNombre(record.getStringValue("NOMBRE").trim());
			    	monto = (Integer) record.getNumberValue("MONTO");
			    	try{
			    		descuento.setIntMonto(monto.intValue());			        
			    	}catch (Exception e) {
			    		descuento.setIntMonto(null);
					}
			    	descuento.setStrProgSub(record.getStringValue("PROGSUB"));
			    	descuento.setStrCodEst(record.getStringValue("CODEST"));
			    	descuento.setStrTipoPla(record.getStringValue("TIPOPLA"));
			    	descuento.setStrPro(record.getStringValue("PRO"));
			    	descuento.setStrCodEje(record.getStringValue("CODEJE"));
			    	descuento.setStrLibEle(record.getStringValue("LIBELE"));
			    	descuento.setStrNomCpto(record.getStringValue("ABRCON"));
			    	
			    	descuento.getId().setIntPeriodo(adminPadron.getId().getIntPeriodo());
			    	descuento.getId().setIntMes(adminPadron.getId().getIntMes());
			    	descuento.getId().setIntNivel(adminPadron.getId().getIntNivel());
			    	descuento.getId().setIntCodigo(adminPadron.getId().getIntCodigo());
			    	descuento.getId().setIntParaTipoArchivoPadronCod(adminPadron.getId().getIntParaTipoArchivoPadronCod());
			    	descuento.getId().setIntParaModalidadCod(adminPadron.getId().getIntParaModalidadCod());
			    	descuento.getId().setIntParaTipoSocio(adminPadron.getId().getIntParaTipoSocioCod());
			    	descuento.getId().setIntItemAdministraPadron(adminPadron.getId().getIntItemAdministraPadron());
						
			    	
			    	boDescuento.grabarDescuento(descuento);
			    	
			    	contadorCorrecto++;
			    }catch (Exception e) {
			    	try{
			    		listaNoProcesados.add(record.getStringValue("LIBELE"));
			    	}catch (Exception e1) {
						log.info("No se pudo leer la columna LIBELE",e1);
					}
			    	contadorInCorrecto++;
			    	/*if(contadorInCorrecto==1){
			    		log.error(e.getMessage(),e);
			    	}*/
			    	log.info(descuento);
			    	log.error(e.getMessage(),e);
			    }
			}
			/*
		    if(!exito){
		    	log.error("ocurrio un error leyendo la tabla DBF");
		    }*/
			log.info("contadorCorrecto:"+contadorCorrecto);
			log.info("contadorInCorrecto:"+contadorInCorrecto);
			exito = true;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}finally{
		    table.close();
		}
		return exito;
	}

	
	public boolean grabarAdminPadronyPadrones(AdminPadron adminPadron, String ruta, List<String>listaNoProcesados)
		throws BusinessException, IOException{
		boolean exito = false;
		 //int i = 0;
		 Record record = null;
		 Padron padron = new Padron();
		 Table table = null;
		 Number liquid = null;
		 Number porJud = null;
		 Number licenp = null;
		 Number licsub = null;
		 Number porguar = null;
		 Number	estado = null;
		 Number ipssat = null;		 

		 
		 int contadorCorrecto = 0;
		 int contadorInCorrecto = 0;
		 //log.info("inicio");
		 try{			
			 adminPadron = boAdminPadron.grabarAdminPadron(adminPadron);
			table = new Table(new java.io.File(ruta));
			table.open(IfNonExistent.ERROR);
			Iterator<Record> iterator = table.recordIterator();	   
			exito = true;
			int i = 0;			

			//while (iterator.hasNext() && i<10){	
			while (iterator.hasNext()){
			    record = iterator.next();
			    //padron = new Padron();
			    try{
			    	
				        //log.info(i);
				        padron.setStrPro(record.getStringValue("PRO").trim());
				        padron.setStrProgSub(record.getStringValue("PROGSUB").trim());
				        padron.setStrCodEst(record.getStringValue("CODEST").trim());
				        padron.setStrCodigoNivel(record.getStringValue("CODNIV").trim());
				        padron.setStrCodeje(record.getStringValue("CODEJE").trim());
				        padron.setStrTipoPla(record.getStringValue("TIPOPLA").trim());
				        padron.setStrNombre(record.getStringValue("NOMBRE").trim());
				        padron.setStrPlaza(record.getStringValue("PLAZA").trim());
				        padron.setStrLibEle(record.getStringValue("LIBELE").trim());
				        padron.setDtFecNac(record.getDateValue("FECNAC"));			        
				        padron.setStrCodCar(record.getStringValue("CODCAR").trim());
				        padron.setStrRegim(record.getStringValue("REGIM").trim());
				        
				        try{
				        	liquid = record.getNumberValue("LIQUID");
				        	padron.setBdLiquid((BigDecimal.valueOf(liquid.doubleValue())).setScale(2, BigDecimal.ROUND_HALF_UP));				       
				        }catch (Exception e) {
				        	padron.setBdLiquid(null);
						}
				        
				        padron.setStrCondic(record.getStringValue("CONDIC").trim());
				        
				        try{
					        porJud = record.getNumberValue("PORJUD");
					        padron.setBdPorJud((BigDecimal.valueOf(porJud.doubleValue())).setScale(2, BigDecimal.ROUND_HALF_UP));					       
				        }catch (Exception e) {
				        	padron.setBdPorJud(null);
						}
				        
					    try{
					        licenp = record.getNumberValue("LICENP");
					        padron.setIntLicenp(licenp.intValue());				        
					    }catch (Exception e) {
				        	padron.setIntLicenp(null);
						}
					    
				        try{
					        licsub = record.getNumberValue("LICSUB");
					        padron.setIntLicSub(licsub.intValue());
					    }catch (Exception e) {
				        	padron.setIntLicSub(null);
						}
					    
				        try{
					        porguar = record.getNumberValue("PORGUAR");
					        padron.setIntPorguar(porguar.intValue());
				        }catch (Exception e) {
				        	padron.setIntPorguar(null);
						}
				        
				        try{
				        	estado = record.getNumberValue("ESTADO");
				        	padron.setIntEstado(estado.intValue());
				        }catch (Exception e) {
				        	padron.setIntEstado(null);
						}				        
				        
				        
				        try{
				        	padron.setStrDL6(record.getStringValue("DL6").trim());
				        }catch (Exception e) {
				        	padron.setStrDL6(null);
						}
				        
				        try{
					        ipssat = record.getNumberValue("IPSSAT");
					        padron.setIntIPSSAT(ipssat.intValue());
				        }catch (Exception e) {
				        	padron.setIntIPSSAT(null);
						}
				        
				        
				        //log.info(padron.toString());
				        padron.getId().setIntPeriodo(adminPadron.getId().getIntPeriodo());
						padron.getId().setIntMes(adminPadron.getId().getIntMes());
						padron.getId().setIntNivel(adminPadron.getId().getIntNivel());
						padron.getId().setIntCodigo(adminPadron.getId().getIntCodigo());
						padron.getId().setIntParaTipoArchivoPadronCod(adminPadron.getId().getIntParaTipoArchivoPadronCod());
						padron.getId().setIntParaModalidadCod(adminPadron.getId().getIntParaModalidadCod());
						padron.getId().setIntParaTipoSocioCod(adminPadron.getId().getIntParaTipoSocioCod());
						padron.getId().setIntItemAdministraPadron(adminPadron.getId().getIntItemAdministraPadron());
						padron.getId().setIntItem(null);
						boPadron.grabarPadron(padron);
						contadorCorrecto++;
				    }catch(Exception e){
				    	try{
				    		log.info("--"+record.getStringValue("LIBELE").trim());
				    		listaNoProcesados.add(record.getStringValue("LIBELE").trim());
				    	}catch (Exception e1) {
							log.info("No se pudo leer la columna LIBELE",e1);
						}
				    	
				    	contadorInCorrecto++;
				    	/*if(contadorInCorrecto==1){
				    		log.error(e.getMessage(),e);
				    	}*/
				    	log.info(padron);
				    	log.error(e.getMessage(),e);
			        	//log.info("Hubo un error en una fila de la tabla DBF leida"+i);
			        }
				    //i++;
			}
			/*if(!exito){
				log.error("Ocurrio un error leyendo la tabla DBF");
			}*/
			log.info("contadorCorrecto:"+contadorCorrecto);
			log.info("contadorInCorrecto:"+contadorInCorrecto);
			//log.info("fin");
		}catch(BusinessException e){
			//System.out.println("Hubo un error en una fila de la tabla DBF leida BusinessException "+i);
			//log.info("Hubo un error en una fila de la tabla DBF leida BusinessException "+i);
			throw e;
		}catch(Exception e){
			//System.out.println("Hubo un error en una fila de la tabla DBF leida Exception "+i);
			//log.info("Hubo un error en una fila de la tabla DBF leida Exception "+i);
			throw new BusinessException(e);
		}finally{
			//System.out.println("finally"+i);
		    table.close();
		}
		return exito;
	}
	
	public boolean grabarSolicitudPago(SolicitudPago solicitudPago,	List<AdminPadron> listaPadrones)throws BusinessException{
		boolean exito = false;
		try{
			Date fechaActual = new Date();
			solicitudPago.setDtFechaRegistro(fechaActual);
			solicitudPago.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			solicitudPago.setIntParaEstadoPagoCod(Constante.PARAM_T_ESTADODOCUMENTO_PENDIENTE);
			solicitudPago.setIntPersPersonsaUsuarioPK(1);
			solicitudPago.setIntPersEmpresaPK(Constante.PARAM_EMPRESASESION);
			solicitudPago.setIntPersEmpresaUsuarioPK(Constante.PARAM_EMPRESASESION);
			solicitudPago = boSolicitudPago.grabar(solicitudPago);
			for(AdminPadron adminPadron : listaPadrones){
				SolicitudPagoDetalle solicitudPagoDetalle = new SolicitudPagoDetalle();
				solicitudPagoDetalle.getId().setIntNumero(solicitudPago.getIntNumero());
				solicitudPagoDetalle.setIntPeriodo(adminPadron.getId().getIntPeriodo());
				solicitudPagoDetalle.setIntMes(adminPadron.getId().getIntMes());
				solicitudPagoDetalle.setIntNivel(adminPadron.getId().getIntNivel());
				solicitudPagoDetalle.setIntCodigo(adminPadron.getId().getIntCodigo());
				solicitudPagoDetalle.setIntParaTipoArchivoPadronCod(adminPadron.getId().getIntParaTipoArchivoPadronCod());
				solicitudPagoDetalle.setIntParaModalidadCod(adminPadron.getId().getIntParaModalidadCod());
				solicitudPagoDetalle.setIntParaTipoSocioCod(adminPadron.getId().getIntParaTipoSocioCod());
				solicitudPagoDetalle.setIntItemAdministraPadron(adminPadron.getId().getIntItemAdministraPadron());
				
				boSolicitudPagoDetalle.grabar(solicitudPagoDetalle);
			}
			exito = true;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return exito;
	}
	
	public List<AdminPadron> getAdminPadronBusqueda(AdminPadron adminPadronFiltro) throws BusinessException, Exception{
		List<AdminPadron> lista;
		try {
			log.info("bus  :"+adminPadronFiltro);
			lista = boAdminPadron.getAdminPadronBusqueda(adminPadronFiltro);			
			cargarPersonas(lista);
			cargarSolicitudPagoDetalle(lista);
			for(AdminPadron adminPadron : lista){
				log.info("enc  :"+adminPadron);
			}
			List<AdminPadron> li = boAdminPadron.getListaAdminPadron();
			for(AdminPadron ad: li){
				log.info("hay  :"+ad);
			}
			if(adminPadronFiltro.getEstructura().getJuridica().getStrSiglas()!=null){
				if(!adminPadronFiltro.getEstructura().getJuridica().getStrSiglas().isEmpty()){
					String filtroRazonSocial = adminPadronFiltro.getEstructura().getJuridica().getStrSiglas().toUpperCase();
					lista = buscarRazonSocial(lista, filtroRazonSocial);
				}
			}			
		}catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return lista;
	}
	
	public List<AdminPadron> actualizarListaAdminPadron(List<AdminPadron> listaAdminPadron) throws BusinessException, Exception{
		try {	
			
			cargarSolicitudPagoDetalle(listaAdminPadron);

		}catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return listaAdminPadron;
	}
	
	private List<AdminPadron> buscarRazonSocial(List<AdminPadron> lista, String filtroRazonSocial){
		if(filtroRazonSocial != null && filtroRazonSocial.length()>0){
			List<AdminPadron> listaAux = new ArrayList<AdminPadron>();
			for(AdminPadron adminPadron : lista){
				String razonSocial = adminPadron.getEstructura().getJuridica().getStrSiglas().toUpperCase();
				if(razonSocial.contains(filtroRazonSocial)){
					listaAux.add(adminPadron);
				}
			}
			lista = listaAux;
		}
		return lista;
	}
	
	public List<AdminPadron> getAdminPadronSinSolicitud(AdminPadron adminPadronBus) throws BusinessException, Exception{
		List<AdminPadron> listaTodosAdminPadron = null;
		List<SolicitudPagoDetalle> listaSolicitudPagoDetalle = null;
		List<AdminPadron> listaAdminPadronSinSolicitud = null;
		try {
			//log.info("busca:"+adminPadronBus);
			listaTodosAdminPadron = boAdminPadron.getAdminPadronBusqueda(adminPadronBus);
			listaSolicitudPagoDetalle = boSolicitudPagoDetalle.getLista();
			listaAdminPadronSinSolicitud = new ArrayList<AdminPadron>();
			for(AdminPadron adminPadron : listaTodosAdminPadron){
				//log.info("encuentra:"+adminPadron);
				boolean sonIguales = Boolean.FALSE;
				int i = 0;
				while(i<listaSolicitudPagoDetalle.size()&& !sonIguales){
					SolicitudPagoDetalle solPagoDet = listaSolicitudPagoDetalle.get(i);
					sonIguales = compararAdminPadronYSolicitudPagoDetalle(adminPadron,solPagoDet);
					i++;
				}
				if(!sonIguales){
					listaAdminPadronSinSolicitud.add(adminPadron);
				}
			}
			cargarPersonas(listaAdminPadronSinSolicitud);
		}catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return listaAdminPadronSinSolicitud;
	}
	
	private void cargarSolicitudPagoDetalle(List<AdminPadron> listaAdminPadron) throws BusinessException,Exception{
		int i = 0;
		while(i<listaAdminPadron.size()){
			SolicitudPagoDetalle solicitudPagoDetalle = new SolicitudPagoDetalle();
			solicitudPagoDetalle.setIntPeriodo(listaAdminPadron.get(i).getId().getIntPeriodo());
			solicitudPagoDetalle.setIntMes(listaAdminPadron.get(i).getId().getIntMes());
			solicitudPagoDetalle.setIntNivel(listaAdminPadron.get(i).getId().getIntNivel());
			solicitudPagoDetalle.setIntCodigo(listaAdminPadron.get(i).getId().getIntCodigo());
			solicitudPagoDetalle.setIntParaTipoArchivoPadronCod(listaAdminPadron.get(i).getId().getIntParaTipoArchivoPadronCod());
			solicitudPagoDetalle.setIntParaModalidadCod(listaAdminPadron.get(i).getId().getIntParaModalidadCod());
			solicitudPagoDetalle.setIntParaTipoSocioCod(listaAdminPadron.get(i).getId().getIntParaTipoSocioCod());
			solicitudPagoDetalle.setIntItemAdministraPadron(listaAdminPadron.get(i).getId().getIntItemAdministraPadron());
			
			List<SolicitudPagoDetalle> lista = boSolicitudPagoDetalle.getBusqueda(solicitudPagoDetalle);
			if(lista!=null && lista.size()>0){
				listaAdminPadron.get(i).setSolicitudPagoDetalle(lista.get(0));				
			}
			i++;
		}
	}
	
	private void cargarPersonas(List<AdminPadron> listaAdminPadron) throws BusinessException, EJBFactoryException{
		int i = 0;
		PersonaFacadeRemote facadePersona = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
		while(i<listaAdminPadron.size()){
			int nivel = listaAdminPadron.get(i).getId().getIntNivel();
			int codigo = listaAdminPadron.get(i).getId().getIntCodigo();
			EstructuraId estructuraId = new EstructuraId();
			estructuraId.setIntNivel(nivel);
			estructuraId.setIntCodigo(codigo);
			Estructura estructura = estructuraService.getEstructuraPorPK(estructuraId);
			listaAdminPadron.get(i).setEstructura(estructura);
			//Persona persona = facadePersona.getPersonaPorPK(listaAdminPadron.get(i).getIntPersUsuarioRegistroPk());
			Persona persona = facadePersona.getPersonaPorPK(Constante.PARAM_USUARIOSESION);
			if(persona != null){
				if(persona.getIntTipoPersonaCod().intValue()==Constante.PARAM_T_TIPOPERSONA_NATURAL){
					Natural natural = facadePersona.getNaturalPorPK(persona.getIntIdPersona());
					persona.setNatural(natural);
					persona.setJuridica(new Juridica());
				}
				if(persona.getIntTipoPersonaCod().intValue()==Constante.PARAM_T_TIPOPERSONA_JURIDICA){
					Juridica juridica = facadePersona.getJuridicaPorPK(persona.getIntIdPersona());
					persona.setJuridica(juridica);
					persona.setNatural(new Natural());
				}
			}else{
				persona = new Persona();
				persona.setNatural(new Natural());
				persona.setJuridica(new Juridica());
			}
			listaAdminPadron.get(i).setPersona(persona);
			i++;
		}
	}
	
	private boolean compararAdminPadronYSolicitudPagoDetalle(AdminPadron adminPadron, SolicitudPagoDetalle solPagoDetalle){
		boolean sonIguales = false;
		try{
			if(	adminPadron.getId().getIntPeriodo().intValue() == solPagoDetalle.getIntPeriodo().intValue() 
				&& adminPadron.getId().getIntMes().intValue() == solPagoDetalle.getIntMes().intValue() 
				&& adminPadron.getId().getIntNivel().intValue() == solPagoDetalle.getIntNivel().intValue() 
				&& adminPadron.getId().getIntCodigo().intValue() == solPagoDetalle.getIntCodigo().intValue() 
				&& adminPadron.getId().getIntParaTipoArchivoPadronCod().intValue() == solPagoDetalle.getIntParaTipoArchivoPadronCod().intValue() 
				&& adminPadron.getId().getIntParaModalidadCod().intValue() == solPagoDetalle.getIntParaModalidadCod().intValue() 
				&& adminPadron.getId().getIntParaTipoSocioCod().intValue() == solPagoDetalle.getIntParaTipoSocioCod().intValue() 
				&& adminPadron.getId().getIntItemAdministraPadron().intValue() == solPagoDetalle.getIntItemAdministraPadron().intValue() 
				){				
				sonIguales = true;	
			}			

		}catch (Exception e) {
			e.printStackTrace();
		}
		return sonIguales;
	}
	
	public List<SolicitudPagoDetalle> getSolicitudDetalleBusqueda(SolicitudPagoDetalle solicitudPagoDetalle) throws BusinessException{
		List<SolicitudPagoDetalle> lista = null;
		try {
			lista = boSolicitudPagoDetalle.getBusqueda(solicitudPagoDetalle);
		} catch (BusinessException e) {
			throw e;
		}
		return lista;
	}
	
	public SolicitudPago getSolicitudPorPK(Integer solicitudPagoPK) throws BusinessException{
		SolicitudPago solicitudPago = null;
		try {
			log.info("PK solicitud Pago a buscar:"+solicitudPagoPK);
			solicitudPago = boSolicitudPago.getPorPK(solicitudPagoPK);
			log.info("solicitudPago encontrada:"+solicitudPago);
		} catch (BusinessException e) {
			throw e;
		}
		return solicitudPago;
	}
	
	public List<AdminPadron> getSolicitudBusqueda(AdminPadron adminPadron) throws Exception, BusinessException{
		List<SolicitudPagoDetalle> lista;
		List<AdminPadron> listaAdminPadron = new ArrayList<AdminPadron>();
		try {
			SolicitudPagoDetalle solicitudPagoDetalle = new SolicitudPagoDetalle();
			solicitudPagoDetalle.setIntPeriodo(adminPadron.getId().getIntPeriodo());
			solicitudPagoDetalle.setIntMes(adminPadron.getId().getIntMes());
			solicitudPagoDetalle.setIntParaModalidadCod(adminPadron.getId().getIntParaModalidadCod());
			solicitudPagoDetalle.setIntNivel(adminPadron.getId().getIntNivel());
			lista = getSolicitudDetalleBusqueda(solicitudPagoDetalle);
			for(SolicitudPagoDetalle solPagoDetalle : lista){
				log.info("solPagoDetalle "+solPagoDetalle);
				SolicitudPago solicitudPago = getSolicitudPorPK(solPagoDetalle.getId().getIntNumero());
				if(adminPadron.getIntParaEstadoCod()!=null){
					if((solicitudPago.getIntParaEstadoCod().intValue() == adminPadron.getIntParaEstadoCod().intValue()) &&
							solicitudPago.getIntParaEstadoPagoCod().intValue() == adminPadron.getSolicitudPagoDetalle().getSolicitudPago().getIntParaEstadoPagoCod().intValue()){
						AdminPadron adPadron = new AdminPadron();
						adPadron.getId().setIntPeriodo(solPagoDetalle.getIntPeriodo());
						adPadron.getId().setIntMes(solPagoDetalle.getIntMes());
						adPadron.getId().setIntNivel(solPagoDetalle.getIntNivel());
						adPadron.getId().setIntCodigo(solPagoDetalle.getIntCodigo());
						adPadron.getId().setIntParaTipoArchivoPadronCod(solPagoDetalle.getIntParaTipoArchivoPadronCod());
						adPadron.getId().setIntParaModalidadCod(solPagoDetalle.getIntParaModalidadCod());
						adPadron.getId().setIntParaTipoSocioCod(solPagoDetalle.getIntParaTipoSocioCod());
						adPadron.setIntParaEstadoCod(solicitudPago.getIntParaEstadoCod().intValue());
						adPadron.setAdPaFechaRegistro(solicitudPago.getDtFechaRegistro());
						adPadron.setIntPersUsuarioRegistroPk(solicitudPago.getIntPersPersonsaUsuarioPK());
						adPadron.setSolicitudPagoDetalle(solPagoDetalle);
						adPadron.getSolicitudPagoDetalle().getSolicitudPago().setIntParaEstadoPagoCod(solicitudPago.getIntParaEstadoPagoCod().intValue());
						listaAdminPadron.add(adPadron);
					}
				}else{
					if(solicitudPago.getIntParaEstadoPagoCod().intValue() == adminPadron.getSolicitudPagoDetalle().getSolicitudPago().getIntParaEstadoPagoCod().intValue()){
						AdminPadron adPadron = new AdminPadron();
						adPadron.getId().setIntPeriodo(solPagoDetalle.getIntPeriodo());
						adPadron.getId().setIntMes(solPagoDetalle.getIntMes());
						adPadron.getId().setIntNivel(solPagoDetalle.getIntNivel());
						adPadron.getId().setIntCodigo(solPagoDetalle.getIntCodigo());
						adPadron.getId().setIntParaTipoArchivoPadronCod(solPagoDetalle.getIntParaTipoArchivoPadronCod());
						adPadron.getId().setIntParaModalidadCod(solPagoDetalle.getIntParaModalidadCod());
						adPadron.getId().setIntParaTipoSocioCod(solPagoDetalle.getIntParaTipoSocioCod());
						adPadron.setIntParaEstadoCod(solicitudPago.getIntParaEstadoCod().intValue());
						adPadron.setAdPaFechaRegistro(solicitudPago.getDtFechaRegistro());
						adPadron.setIntPersUsuarioRegistroPk(solicitudPago.getIntPersPersonsaUsuarioPK());
						adPadron.setSolicitudPagoDetalle(solPagoDetalle);
						adPadron.getSolicitudPagoDetalle().getSolicitudPago().setIntParaEstadoPagoCod(solicitudPago.getIntParaEstadoPagoCod().intValue());
						listaAdminPadron.add(adPadron);
					}
				}
				
			}
			cargarPersonas(listaAdminPadron);
			for(AdminPadron adPadron : listaAdminPadron){
				log.info("encuentra:"+adPadron);
			}
			if(adminPadron.getEstructura().getJuridica().getStrSiglas()!=null){
				if(!adminPadron.getEstructura().getJuridica().getStrSiglas().isEmpty()){
					String filtroRazonSocial = adminPadron.getEstructura().getJuridica().getStrSiglas().toUpperCase();
					listaAdminPadron = buscarRazonSocial(listaAdminPadron, filtroRazonSocial);
				}
			}			
		}catch (BusinessException e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		return listaAdminPadron;
	}

	public SocioComp getSocioNatuPorLibElectoral(String strLibEle) throws BusinessException {
		SocioComp socioComp = null;
		Padron padron = null;
		
		padron = boPadron.getPadronPorLibElectoral(strLibEle);
		if(padron!=null){
			socioComp = new SocioComp();
			socioComp.setPadron(padron);
		}
		
		return socioComp;
	}
	
	

}