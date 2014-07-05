package pe.com.tumi.cobranza.planilla.service;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.planilla.bo.EfectuadoResumenBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioconceptoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioinfladaBO;
import pe.com.tumi.cobranza.planilla.bo.EnviomontoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioresumenBO;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Envioinflada;
import pe.com.tumi.cobranza.planilla.domain.EnvioinfladaId;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;
import pe.com.tumi.cobranza.planilla.domain.EnvioresumenId;
import pe.com.tumi.cobranza.planilla.domain.composite.EfectuadoConceptoComp;
import pe.com.tumi.cobranza.planilla.domain.composite.EnvioConceptoComp;
import pe.com.tumi.cobranza.planilla.domain.composite.ItemPlanilla;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionado;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class EnvioService {
	protected static Logger log = Logger.getLogger(EnvioService.class);
	
	private EnvioconceptoBO boEnvioconcepto 		= (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);
	private EnvioresumenBO boEnvioresumen 			= (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
	private EnviomontoBO boEnviomonto 				= (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
	private EnvioinfladaBO boEnvioinflada   		= (EnvioinfladaBO)TumiFactory.get(EnvioinfladaBO.class);
	private EfectuadoResumenBO boEfectuadoResumen 	= (EfectuadoResumenBO)TumiFactory.get(EfectuadoResumenBO.class);
	
public void grabarEnvio(List<ItemPlanilla> listaItemPlanilla,
						Usuario pUsuario) throws BusinessException
	{
	log.debug("grabarEnvio INICIO");
		Envioresumen lEnvioresumen 	= null;
		ItemPlanilla lItemPlanilla 	= null;
		Integer intCantidadSocio 	= null;
		Map<String,BigDecimal> mapaMontoResumen 	= null;
		Map<String,BigDecimal> mapaMontoInflada 	= null;	
		Map<String,List<Integer>> mapaEstructura 	= null;
		Map<String,List<Integer>> mapaAdministra 	= null;
		Map<String,List<Integer>> mapaProcesa 		= null;
		Map<String,Integer> mapaCantidadSocio 		= null;		
		Map<String,List<Enviomonto>> mapaListaEnvio = null;
		List<Integer> listaInteger 		= null;
		List<Enviomonto> listaEnvioMonto = null;
		String strKeyMapa 			= null;			
		Enviomonto lEnvioMonto 		= null;		
		BigDecimal bdMonto 			= null;
		BigDecimal bdMontoInflada 	= null;
		String []strArray 			= null;
		Envioconcepto lEnvioConcepto = null;
		List<Envioinflada>  listaEnvioinflada = null;
		Envioinflada lEnvioinflada 		= null;		
		Integer intItemEnvioConcepto 	= null;
		Timestamp tsFechaHoraActual 	= null;	
		bdMontoInflada = new BigDecimal(0);
		mapaMontoResumen = new HashMap<String,BigDecimal>();
		mapaMontoInflada = new HashMap<String,BigDecimal>();
		mapaEstructura = new HashMap<String,List<Integer>>();
		mapaAdministra = new HashMap<String,List<Integer>>();
		mapaProcesa = new HashMap<String,List<Integer>>();
		mapaCantidadSocio = new HashMap<String,Integer>();
		mapaListaEnvio = new HashMap<String,List<Enviomonto>>();
		tsFechaHoraActual = JFecha.obtenerTimestampDeFechayHoraActual();
		ConceptoFacadeRemote remoteConcepto = null;
		SocioFacadeRemote remoteSocio 				= null;
		try
		{
			
		remoteConcepto		 = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		remoteSocio 	 	= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
		
		for(int i=0;i<listaItemPlanilla.size();i++)
		{
			lItemPlanilla = listaItemPlanilla.get(i);
			
			if(lItemPlanilla.getSocio().getSocioEstructura() != null)
			{
				//graba un nuevo socioestructura
				log.debug("grabaSocioestructura");
				remoteSocio.grabarSocioEstructura(lItemPlanilla.getSocio().getSocioEstructura());
			}
			
			if(!lItemPlanilla.getBlnEnvioConcepto() )
			{
				log.debug("aun no graba en envioconcepto");
				if(lItemPlanilla.getListaEnvioConcepto() != null && !lItemPlanilla.getListaEnvioConcepto().isEmpty())
				{
					for(int j=0; j<lItemPlanilla.getListaEnvioConcepto().size(); j++)
					{
						lEnvioConcepto = lItemPlanilla.getListaEnvioConcepto().get(j);
						log.debug("lEnvioConcepto: "+lEnvioConcepto);
						if(j == 0)
						{
							boEnvioconcepto.grabarEnvioconcepto(lEnvioConcepto);
							intItemEnvioConcepto = lEnvioConcepto.getId().getIntItemenvioconcepto();
							log.debug("itemconcepto="+intItemEnvioConcepto);
						}
						else
						{
							lEnvioConcepto.getId().setIntItemenvioconcepto(intItemEnvioConcepto);
							boEnvioconcepto.grabarSubEnvioconcepto(lEnvioConcepto);
						}
					}
				}
			}
			else 
			{
				//ya mando incentivos con el codigo de incentivo grabo mi enviomonto pendiente
				log.debug("ya grabo en envioconcepto");
				intItemEnvioConcepto =  lItemPlanilla.getIntItemEnvioConcepto();
			}
			
			
			if(lItemPlanilla.getListaEnviomonto() != null && !lItemPlanilla.getListaEnviomonto().isEmpty())
			{
				for(int j=0; j < lItemPlanilla.getListaEnviomonto().size(); j++)
				{
					lEnvioMonto = lItemPlanilla.getListaEnviomonto().get(j);
					
					lEnvioMonto.getId().setIntItemenvioconcepto(intItemEnvioConcepto);
					strKeyMapa = String.valueOf(lEnvioMonto.getIntModalidadCod())
								+"-"+String.valueOf(lEnvioMonto.getIntTiposocioCod());
					log.debug("strKeyMapa="+strKeyMapa);
					if(mapaMontoResumen.containsKey(strKeyMapa))
					{
						bdMonto = mapaMontoResumen.get(strKeyMapa);
						bdMonto = bdMonto.add(lEnvioMonto.getBdMontoenvio());
						mapaMontoResumen.put(strKeyMapa,bdMonto);
						intCantidadSocio = mapaCantidadSocio.get(strKeyMapa);
						intCantidadSocio = intCantidadSocio + 1;
						mapaCantidadSocio.put(strKeyMapa,intCantidadSocio);
						listaEnvioMonto = mapaListaEnvio.get(strKeyMapa);
						listaEnvioMonto.add(lEnvioMonto);
					}
					else
					{
						mapaMontoResumen.put(strKeyMapa,lEnvioMonto.getBdMontoenvio());
						listaInteger = new ArrayList<Integer>();
						listaInteger.add(lEnvioMonto.getIntNivel());
						listaInteger.add(lEnvioMonto.getIntCodigo());
						
						mapaEstructura.put(strKeyMapa, listaInteger);
						
						listaInteger = new ArrayList<Integer>();
						listaInteger.add(lEnvioMonto.getIntEmpresasucprocesaPk());
						listaInteger.add(lEnvioMonto.getIntIdsucursalprocesaPk());
						listaInteger.add(lEnvioMonto.getIntIdsubsucursalprocesaPk());
						
						mapaProcesa.put(strKeyMapa, listaInteger);
						
						listaInteger = new ArrayList<Integer>();
						listaInteger.add(lEnvioMonto.getIntEmpresasucadministraPk());
						listaInteger.add(lEnvioMonto.getIntIdsucursaladministraPk());
						listaInteger.add(lEnvioMonto.getIntIdsubsucursaladministra());
						
						mapaAdministra.put(strKeyMapa, listaInteger);						
						mapaCantidadSocio.put(strKeyMapa,new Integer(1));
						
						listaEnvioMonto = new ArrayList<Enviomonto>();
						listaEnvioMonto.add(lEnvioMonto);
						mapaListaEnvio.put(strKeyMapa, listaEnvioMonto);						
					}
					
					if(lEnvioMonto.getListaEnvioinflada() != null && !lEnvioMonto.getListaEnvioinflada().isEmpty())
					{
						for(int k=0; k < lEnvioMonto.getListaEnvioinflada().size();k++)
						{
							lEnvioinflada = lEnvioMonto.getListaEnvioinflada().get(k);							
							if(mapaMontoInflada.containsKey(strKeyMapa))
							{								
								if(mapaMontoInflada.get(strKeyMapa) == null)
								{
									bdMontoInflada = new BigDecimal(0);
								}
								else
								{
									bdMontoInflada = mapaMontoInflada.get(strKeyMapa);
								}
								bdMontoInflada = bdMontoInflada.add(lEnvioinflada.getBdMonto());
								mapaMontoInflada.put(strKeyMapa, bdMontoInflada);
							}
							else
							{
								mapaMontoInflada.put(strKeyMapa, lEnvioinflada.getBdMonto());
							}
													
						}
					}
					else
					{
						if(mapaMontoInflada.containsKey(strKeyMapa))
						{
							continue;
						}
						else
						{
							mapaMontoInflada.put(strKeyMapa,null);
						}						
					}									
				}
			}	
			
		}
		
		log.debug("strKeyMapa="+strKeyMapa);
		
		Iterator iterator 	= mapaMontoResumen.entrySet().iterator();
		Iterator iteratorI 	= mapaMontoInflada.entrySet().iterator();			
		Map.Entry mapEntry 	= null;	
		Map.Entry mapEntryI = null;	
		
        while(iterator.hasNext())
        {
        	mapEntry 			= (Map.Entry)iterator.next();         	
        	mapEntryI 			= (Map.Entry)iteratorI.next();
        	lEnvioresumen = new Envioresumen();
    		lEnvioresumen.setId(new EnvioresumenId());
    		lEnvioresumen.getId().setIntEmpresaPk(listaItemPlanilla.get(0).getIntEmpresa());
    		lEnvioresumen.setIntDocumentogeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAENVIADA);
    		lEnvioresumen.setIntPeriodoplanilla(listaItemPlanilla.get(0).getIntPeriodo());
    		lEnvioresumen.setBdMontototal((BigDecimal)mapEntry.getValue());
    		lEnvioresumen.setBdMontototalinflada((BigDecimal)mapEntryI.getValue());
    		strKeyMapa = String.valueOf(mapEntry.getKey());
    		log.debug("strKeyMapa="+strKeyMapa);
    		intCantidadSocio = mapaCantidadSocio.get(strKeyMapa);
    		if(intCantidadSocio != null)
    		{
    			lEnvioresumen.setIntNumeroafectados(intCantidadSocio);
    		}
    		strArray = strKeyMapa.split("-");
    		lEnvioresumen.setIntTiposocioCod(Integer.parseInt(strArray[1]));
    		lEnvioresumen.setIntModalidadCod(Integer.parseInt(strArray[0]));
    		listaInteger = mapaEstructura.get(strKeyMapa);
    		
    		if(listaInteger != null && !listaInteger.isEmpty())
    		{
	    		lEnvioresumen.setIntNivel(listaInteger.get(0));
	    		lEnvioresumen.setIntCodigo(listaInteger.get(1));
    		}
    		
    		listaInteger = mapaProcesa.get(strKeyMapa);
    		
    		if(listaInteger != null && !listaInteger.isEmpty())
    		{
	    		lEnvioresumen.setIntIdsucursalprocesaPk(listaInteger.get(1));
	    		lEnvioresumen.setIntIdsubsucursalprocesaPk(listaInteger.get(2));
    		}
    		
    		listaInteger = mapaAdministra.get(strKeyMapa);
    		
    		if(listaInteger != null && !listaInteger.isEmpty())
    		{
	    		lEnvioresumen.setIntIdsucursaladministraPk(listaInteger.get(1));
	    		lEnvioresumen.setIntIdsubsucursaladministra(listaInteger.get(2));
    		}
    		
    		lEnvioresumen.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
    		lEnvioresumen = boEnvioresumen.grabarEnvioresumen(lEnvioresumen);
    		listaEnvioMonto = mapaListaEnvio.get(strKeyMapa);
    		
    		for(int i=0;i<listaEnvioMonto.size();i++)
    		{
    			lEnvioMonto = listaEnvioMonto.get(i);
    			lEnvioMonto.setEnvioresumen(lEnvioresumen);
    			lEnvioMonto.setTsFecharegistro(tsFechaHoraActual);
    			lEnvioMonto.setIntEmpresausuarioPk(pUsuario.getEmpresa().getIntIdEmpresa());
    			lEnvioMonto.setIntPersonausuarioPk(pUsuario.getIntPersPersonaPk());
    			boEnviomonto.grabarEnviomonto(lEnvioMonto);
    			
    			if(lEnvioMonto.getListaEnvioinflada() != null && !lEnvioMonto.getListaEnvioinflada().isEmpty())
    			{
	    			listaEnvioinflada = lEnvioMonto.getListaEnvioinflada();
	    			
	    			if(listaEnvioinflada != null && !listaEnvioinflada.isEmpty())
	    			{
		    			for(int y=0; y < listaEnvioinflada.size(); y++)
		    			{
		    				lEnvioinflada = listaEnvioinflada.get(y);    			
		    				lEnvioinflada.setId(new EnvioinfladaId());   //lEnvioMonto.getId().setIntItemenvioconcepto
		    				lEnvioinflada.getId().setIntItemenvioconcepto(lEnvioMonto.getId().getIntItemenvioconcepto());
		    				lEnvioinflada.getId().setIntEmpresacuentaPk(lEnvioMonto.getId().getIntEmpresacuentaPk());
		    				lEnvioinflada.getId().setIntItemenviomonto(lEnvioMonto.getId().getIntItemenviomonto());    			
		    				lEnvioinflada.setIntPersonausuarioPk(pUsuario.getIntPersPersonaPk());
		    				boEnvioinflada.grabarEnvioinflada(lEnvioinflada);
		    			}
	    			}
    			}
    			
    		}
        }
	    for(ItemPlanilla i:listaItemPlanilla)
	    {
	    	 if(i.getListaExpediente() !=null && !i.getListaExpediente().isEmpty())
	         {
	 			for(Expediente expediente: i.getListaExpediente())
	 			{
	 				if(expediente.getListaInteresProvisionado() != null && 
	 						!expediente.getListaInteresProvisionado().isEmpty())
	 				{
	 					for(InteresProvisionado interesProvisionado: expediente.getListaInteresProvisionado())
	 					{
	 						log.debug(" grabarinteresprovisionado");
	 						remoteConcepto.grabarInteresProvisionado(interesProvisionado);
	 					}
	 				}
	 			}
	 			
	         }
	    }
       
	    log.debug("grabarEnvio FIN");
		} catch (EJBFactoryException e) {
			throw new BusinessException(e);
		} catch(Exception e){
			throw new BusinessException(e);
		}
	}

	public List<Envioresumen> getListaEnvioresumenBuscar(EnvioConceptoComp dtoFiltroDeEnvio) throws BusinessException{
		log.info("envioservice.getListaEnvioresumenBuscar()");
		List<Envioresumen> lista = null;
		List<Envioresumen> listaTemporal = new ArrayList<Envioresumen>();
		Integer empresa = null;
		Integer tipoSocio = null;
		Integer modalidad = null;		
		Integer intPeriodo = null;
		Integer intEstado = null;
		Integer sucursalprocesa = null;
		String sucursalT = null;
		List<Juridica> listaJuridica= null;
		
		try{
			PersonaFacadeRemote  personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EmpresaFacadeRemote  empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacadeRemote = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
			empresa = dtoFiltroDeEnvio.getEstructura().getIntPersEmpresaPk();			
			tipoSocio = dtoFiltroDeEnvio.getEstructuraDetalle().getIntParaTipoSocioCod();			
			modalidad = dtoFiltroDeEnvio.getIntParaModalidadPlanilla();			
			sucursalprocesa = dtoFiltroDeEnvio.getEstructuraDetalle().getIntSeguSucursalPk();
			intPeriodo = dtoFiltroDeEnvio.getIntPeriodo();
			sucursalT = dtoFiltroDeEnvio.getEstructura().getJuridica().getStrRazonSocial();
			intEstado = dtoFiltroDeEnvio.getEnvioConcepto().getIntParaEstadoCod();
			
			lista = boEnvioresumen.getListaEnvioResumen(empresa, intPeriodo, tipoSocio, modalidad, sucursalprocesa, intEstado);
			if(lista != null && !lista.isEmpty()){				
					log.info("lista.size="+lista.size());				
					if(sucursalT != null && !sucursalT.isEmpty())
					//la caja si tiene algo
					{
						listaJuridica = personaFacade.getListaJuridicaPorRazonSocial(sucursalT);
						log.info("cantidad de listajuridica="+listaJuridica.size());
						
							for(Envioresumen envioresumen: lista)
							{
								EstructuraId id = new EstructuraId();
								id.setIntCodigo(envioresumen.getIntCodigo());
								id.setIntNivel(envioresumen.getIntNivel());
								Estructura estructura = estructuraFacadeRemote.getEstructuraPorPk(id);
								if(estructura != null)
								{
									Juridica juridi= personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
									if(juridi != null)
									{
										for(Juridica juridica: listaJuridica)
										{
											if(juridi.getIntIdPersona().compareTo(juridica.getIntIdPersona()) == 0)
											{
												envioresumen.setJuridicaUnidadEjecutora(juridi);
												log.info("persona="+estructura.getIntPersPersonaPk()+" Unidad ejecutora= "+juridi.getStrRazonSocial());
												//Sucursal
												Sucursal sucursal = new Sucursal();
												sucursal.getId().setIntIdSucursal(envioresumen.getIntIdsucursalprocesaPk());	
												sucursal = empresaFacade.getSucursalPorPK(sucursal);
												
												Juridica juridicaT = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());					
												envioresumen.setJuridicaSucursal(juridicaT);
												log.info("PERSONA= "+sucursal.getIntPersPersonaPk()+" SUCURSAL= "+sucursal.getJuridica().getStrRazonSocial());
												listaTemporal.add(envioresumen);
												break;
											}
										}
									}								
								}
								
							}
						
					}
					else
					{	
						for(Envioresumen envioresumen: lista)
						{		
							//no se ha escrito nada en la caja de unidad ejecutora
							//unidad ejecutora						
							EstructuraId id = new EstructuraId();
							id.setIntCodigo(envioresumen.getIntCodigo());
							id.setIntNivel(envioresumen.getIntNivel());
							Estructura estructura = estructuraFacadeRemote.getEstructuraPorPk(id);
							if(estructura != null)
							{
								Juridica juridi= personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());		
								if(juridi != null)
								{
									envioresumen.setJuridicaUnidadEjecutora(juridi);
									log.info("persona="+estructura.getIntPersPersonaPk()+" Unidad ejecutora= "+juridi.getStrRazonSocial());
									//Sucursal
									Sucursal sucursal = new Sucursal();
									sucursal.getId().setIntIdSucursal(envioresumen.getIntIdsucursalprocesaPk());	
									sucursal = empresaFacade.getSucursalPorPK(sucursal);
									
									Juridica juridica = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());					
									envioresumen.setJuridicaSucursal(juridica);
									log.info("PERSONA= "+sucursal.getIntPersPersonaPk()+" SUCURSAL= "+sucursal.getJuridica().getStrRazonSocial());
									listaTemporal.add(envioresumen);
								}								
							}						
						}					
					}
					lista = listaTemporal;
				
			}else{
				log.info("lista null en envioservice.getListaEnvioresumenBuscar()");
			}

		}
		catch(BusinessException e){
			throw e;
		}
		catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EnvioConceptoComp> getListaEnviomontoDeBuscar(EnvioConceptoComp dtoFiltroDeEnvio) throws BusinessException{
		log.info("envioservice.getListaEnviomontoDeBuscar()");
		List<EnvioConceptoComp> lista = null;
		EnvioConceptoComp dto = null;
		List<Enviomonto> listaEnvioMonto = null;
		Enviomonto dtoEnvioMonto = null;
		PersonaFacadeRemote remotePersona = null;
		EstructuraFacadeRemote remoteEstructura = null;
		EmpresaFacadeRemote remoteEmpresa = null;
		String strRazonSocial = null;
		List<Juridica> listaJuridica = null;
		Juridica lJuridica = null;
		Sucursal lSucursalTmp = null;
		Sucursal lSucursal = null;
		SucursalId lSucursalId = null;
		Estructura lEstructura = null;
		EstructuraId lEstructuraId = null;
		String strPeriodo = null;
		Integer intPeriodo = null;
		Integer intPeriodoAnio = null;
		Integer intPeriodoMes = null;
		try{
			intPeriodoMes = dtoFiltroDeEnvio.getIntPeriodoMes();
			intPeriodoAnio = dtoFiltroDeEnvio.getIntPeriodoAnio();
			if(intPeriodoMes!=null && intPeriodoAnio!= null){
				strPeriodo = intPeriodoAnio+""+String.format("%02d",intPeriodoMes);
				intPeriodo = new Integer(strPeriodo);
			}
			lSucursalId = new SucursalId();
			lSucursalId.setIntPersEmpresaPk(dtoFiltroDeEnvio.getEstructura().getIntPersEmpresaPk());
			lSucursalId.setIntIdSucursal(dtoFiltroDeEnvio.getEstructuraDetalle().getIntSeguSucursalPk());			
			listaEnvioMonto = boEnviomonto.getListaEnviomontoDeBuscar(
					lSucursalId,
					dtoFiltroDeEnvio.getEstructuraDetalle().getIntParaTipoSocioCod(),
					dtoFiltroDeEnvio.getIntParaModalidadPlanilla(),
					intPeriodo,
					dtoFiltroDeEnvio.getEnvioConcepto().getIntParaEstadoCod());
			if(listaEnvioMonto!=null && listaEnvioMonto.size()>0){
				strRazonSocial = dtoFiltroDeEnvio.getEstructura().getJuridica().getStrRazonSocial();
				lEstructuraId = new EstructuraId();
				remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
				remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				remoteEmpresa = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				lSucursalId = new SucursalId();
				lSucursalTmp = new Sucursal();
				if(strRazonSocial != null && !strRazonSocial.equals("")){
					listaJuridica = remotePersona.getListaJuridicaPorRazonSocial(strRazonSocial);
					if(listaJuridica != null && listaJuridica.size() > 0 ){
						lEstructura = new Estructura();
						
						lista = new ArrayList<EnvioConceptoComp>();  
						for(int i=0;i<listaJuridica.size();i++){
							lJuridica = listaJuridica.get(i);
							lEstructura = remoteEstructura.getEstructuraPorIdEmpresaYIdPersona(
														dtoFiltroDeEnvio.getEstructura().getIntPersEmpresaPk(), 
														lJuridica.getIntIdPersona());
							
							if(lEstructura!=null){
								for(int j=0;j<listaEnvioMonto.size();j++){
									dtoEnvioMonto = listaEnvioMonto.get(j);
									if(dtoEnvioMonto.getIntCodigo().compareTo(lEstructura.getId().getIntCodigo())==0 &&
									   dtoEnvioMonto.getIntNivel().compareTo(lEstructura.getId().getIntNivel())==0){
										dto = new EnvioConceptoComp();
										dto.setEnvioMonto(dtoEnvioMonto);
										
										lSucursalId.setIntPersEmpresaPk(dtoEnvioMonto.getIntEmpresasucprocesaPk());
										lSucursalId.setIntIdSucursal(dtoEnvioMonto.getIntIdsucursalprocesaPk());
										lSucursalTmp.setId(lSucursalId);
										lSucursal = remoteEmpresa.getSucursalPorPK(lSucursalTmp);
										dto.setJuridicaSucursal(remotePersona.getJuridicaPorPK(lSucursal.getIntPersPersonaPk()));
										dto.setJuridicaUnidadEjecutora(remotePersona.getJuridicaPorPK(lEstructura.getIntPersPersonaPk()));
										lista.add(dto);
									}
								}
							}
						}
					}
				}else{
					lista = new ArrayList<EnvioConceptoComp>();
					for(int j=0;j<listaEnvioMonto.size();j++){
						dtoEnvioMonto = listaEnvioMonto.get(j);
						dto = new EnvioConceptoComp();
						dto.setEnvioMonto(dtoEnvioMonto);
						
						lEstructuraId.setIntNivel(dtoEnvioMonto.getIntNivel());
						lEstructuraId.setIntCodigo(dtoEnvioMonto.getIntCodigo());
						lEstructura = remoteEstructura.getEstructuraPorPk(lEstructuraId);
						lSucursalId.setIntPersEmpresaPk(dtoEnvioMonto.getIntEmpresasucprocesaPk());
						lSucursalId.setIntIdSucursal(dtoEnvioMonto.getIntIdsucursalprocesaPk());
						lSucursalTmp.setId(lSucursalId);
						lSucursal = remoteEmpresa.getSucursalPorPK(lSucursalTmp);
						dto.setJuridicaSucursal(remotePersona.getJuridicaPorPK(lSucursal.getIntPersPersonaPk()));
						dto.setJuridicaUnidadEjecutora(remotePersona.getJuridicaPorPK(lEstructura.getIntPersPersonaPk()));
						lista.add(dto);
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
	
	public List<EfectuadoResumen> getListaEfectuadoResumenBuscar(EfectuadoConceptoComp dtoFiltroDeEfectuado) throws BusinessException{
		log.info("getListaEfectuadoResumenBuscar() INICIO");
		List<EfectuadoResumen> lista = null;	
		List<EfectuadoResumen> listaTemporal = new ArrayList<EfectuadoResumen>();
		Integer empresa = null;
		Integer tipoSocio = null;
		Integer modalidad = null;		
		Integer intPeriodo = null;
		Integer sucursalprocesa = null;
		String sucursalT = null;
		List<Juridica> listaJuridica = null;
		Integer intEstado = null;
		try{
			PersonaFacadeRemote  personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EmpresaFacadeRemote  empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacadeRemote = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
			empresa = dtoFiltroDeEfectuado.getEstructura().getIntPersEmpresaPk();			
			tipoSocio = dtoFiltroDeEfectuado.getEstructuraDetalle().getIntParaTipoSocioCod();			
			modalidad = dtoFiltroDeEfectuado.getIntParaModalidadPlanilla();			
			sucursalprocesa = dtoFiltroDeEfectuado.getEstructuraDetalle().getIntSeguSucursalPk();
			intPeriodo = dtoFiltroDeEfectuado.getIntPeriodo();
			sucursalT = dtoFiltroDeEfectuado.getEstructura().getJuridica().getStrRazonSocial();
			intEstado = dtoFiltroDeEfectuado.getEfectuadoConcepto().getIntEstadoCod();
			
			lista = boEfectuadoResumen.getListaEfectuadoResumen(empresa, intPeriodo, tipoSocio, modalidad, sucursalprocesa, intEstado);

			if(lista != null && !lista.isEmpty()){
				if(sucursalT != null && !sucursalT.isEmpty())
				{
					listaJuridica = personaFacade.getListaJuridicaPorRazonSocial(sucursalT);
					for(EfectuadoResumen efectuadoResumen: lista)
					{
						EstructuraId id = new EstructuraId();
						id.setIntCodigo(efectuadoResumen.getIntCodigo());
						id.setIntNivel(efectuadoResumen.getIntNivel());
						Estructura estructura = estructuraFacadeRemote.getEstructuraPorPk(id);					
						Juridica juridi= personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
						log.info("persona="+estructura.getIntPersPersonaPk()+" Unidad ejecutora= "+juridi.getStrRazonSocial());
						for(Juridica juridica: listaJuridica)
						{
							if(juridica.getIntIdPersona().compareTo(juridi.getIntIdPersona()) == 0)
							{
								efectuadoResumen.setJuridicaUnidadEjecutora(juridi);
								//Sucursal
								Sucursal sucursal = new Sucursal();
								sucursal.getId().setIntIdSucursal(efectuadoResumen.getIntIdsucursalprocesaPk());	
								sucursal = empresaFacade.getSucursalPorPK(sucursal);					
								Juridica juridicaT = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
								log.info("PERSONA= "+sucursal.getIntPersPersonaPk()+" SUCURSAL= "+sucursal.getJuridica().getStrRazonSocial());
								efectuadoResumen.setJuridicaSucursal(juridicaT);
								listaTemporal.add(efectuadoResumen);
							}
						}
					}
				}
				else
				{
					log.info(" si hay lista  en envioservice.getListaEfectuadoResumenBuscar()="+lista.size());
					for(EfectuadoResumen efectuadoResumen: lista){
						//unidad ejecutora					
						EstructuraId id = new EstructuraId();
						id.setIntCodigo(efectuadoResumen.getIntCodigo());
						id.setIntNivel(efectuadoResumen.getIntNivel());
						Estructura estructura = estructuraFacadeRemote.getEstructuraPorPk(id);
						if(estructura != null)
						{
							Juridica juridi= personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
							
							if(juridi != null && estructura != null)
							{
								log.info("persona="+estructura.getIntPersPersonaPk()+
										" Unidad ejecutora= "+juridi.getStrRazonSocial());
								efectuadoResumen.setJuridicaUnidadEjecutora(juridi);
							}
						}
						
						//Sucursal
						Sucursal sucursal = new Sucursal();
						sucursal.getId().setIntIdSucursal(efectuadoResumen.getIntIdsucursalprocesaPk());	
						sucursal = empresaFacade.getSucursalPorPK(sucursal);
						if(sucursal != null)
						{
							Juridica juridica = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
							if(juridica != null && sucursal != null)
							{
								log.info("PERSONA= "+sucursal.getIntPersPersonaPk()+
										 " SUCURSAL= "+sucursal.getJuridica().getStrRazonSocial());
								efectuadoResumen.setJuridicaSucursal(juridica);
							}
							
							listaTemporal.add(efectuadoResumen);
						}						
					}
				}
				
				lista = listaTemporal;
			}else{
				log.info("lista null en envioservice.getListaEfectuadoResumenBuscarr()");
			}
			log.info("getListaEfectuadoResumenBuscar() FIN");
		}
		catch(BusinessException e){
			throw e;
		}
		catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
