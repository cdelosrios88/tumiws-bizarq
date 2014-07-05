package pe.com.tumi.credito.socio.estructura.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.estructura.bo.EstructuraBO;
import pe.com.tumi.credito.socio.estructura.bo.EstructuraDetalleBO;
import pe.com.tumi.credito.socio.estructura.bo.TercerosBO;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.Terceros;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;

public class EstructuraService {
	
	protected  	static Logger log = Logger.getLogger(EstructuraService.class);
	protected 	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private EstructuraBO boEstructura = (EstructuraBO)TumiFactory.get(EstructuraBO.class);
	private EstructuraDetalleBO boEstructuraDetalle = (EstructuraDetalleBO)TumiFactory.get(EstructuraDetalleBO.class);
	private EstructuraDetalleService estructuraDetalleService = (EstructuraDetalleService)TumiFactory.get(EstructuraDetalleService.class);
	private TercerosBO boTerceros = (TercerosBO)TumiFactory.get(TercerosBO.class);
	
	
	
	public List<EstructuraComp> getListaEstructuraComp(EstructuraComp o) throws BusinessException{
		log.info("-----------------------Debugging EstructuraService.getListaEmpresa-----------------------------");
		List<EstructuraComp> listaEstructuraComp = null;
		List<Estructura> listaEstructura = null;
		List<EstructuraDetalle> listaEstructuraDetalle = null;
		EstructuraComp estructuraComp = null;
		//Juridica juridica = null;
		try{
			
		if(o.getOpcionBusquedaCobranza()!=null && o.getOpcionBusquedaCobranza().equals(new Integer(1))){
			listaEstructuraComp = getListaFiltraEstructuraComp(o);
		}else{	
					
			log.info("Parametros de busqueda...");
			log.info("estructura.id.intNivel: "+o.getEstructura().getId().getIntNivel());
			log.info("estructura.id.intCodigo: "+o.getEstructura().getId().getIntCodigo());
			log.info("estructura.intPersEmpresaPk: "+o.getEstructura().getIntPersEmpresaPk());
			log.info("estructura.intIdGrupo: "+o.getEstructura().getIntIdGrupo());
			log.info("estructura.juridica.strRazonSocial: "+o.getEstructura().getJuridica().getStrRazonSocial());
			log.info("estructura.intIdCodigoRel: "+o.getEstructura().getIntIdCodigoRel());
			log.info("estructuraDetalle.id.intCaso: "+o.getEstructuraDetalle().getId().getIntCaso());
			log.info("estructuraDetalle.intParaTipoSocioCod: "+o.getEstructuraDetalle().getIntParaTipoSocioCod());
			log.info("estructuraDetalle.intParaModalidadCod: "+o.getEstructuraDetalle().getIntParaModalidadCod());
			log.info("estructuraDetalle.intDiaEnviado: "+o.getEstructuraDetalle().getIntDiaEnviado());
			log.info("estructuraDetalle.intDiaEfectuado: "+o.getEstructuraDetalle().getIntDiaEfectuado());
			log.info("estructuraDetalle.intDiaCheque: "+o.getEstructuraDetalle().getIntDiaCheque());
			log.info("estructuraDetalle.intSeguSucursalPk: "+o.getEstructuraDetalle().getIntSeguSucursalPk());
			log.info("estructuraDetalle.intSeguSubSucursalPk: "+o.getEstructuraDetalle().getIntSeguSubSucursalPk());
			log.info("intFechaEnviadoDesde: "+o.getIntFechaEnviadoDesde());
			log.info("intFechaEnviadoHasta: "+o.getIntFechaEnviadoHasta());
			log.info("intFechaEfectuadoDesde: "+o.getIntFechaEfectuadoDesde());
			log.info("intFechaEfectuadoHasta: "+o.getIntFechaEfectuadoHasta());
			log.info("intFechaChequeDesde: "+o.getIntFechaChequeDesde());
			log.info("intFechaChequeHasta: "+o.getIntFechaChequeHasta());
			
			listaEstructura = boEstructura.getListaEstructuraBusqueda(o.getEstructura());
			log.info("Se obtuvo listaEstructura.size: "+listaEstructura.size());
			
			//Validar por la Razón Social
			ArrayList<Estructura> arrayEstructura = new ArrayList<Estructura>();
			for(int i=0; i<listaEstructura.size(); i++){
				//Obteniendo la nombre de Juridica para Estructura
				PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				Juridica juridica = null;
				juridica = personaFacade.getJuridicaPorPK(listaEstructura.get(i).getIntPersPersonaPk());
				Persona persona = null;
				persona = personaFacade.getPersonaPorPK(juridica.getIntIdPersona());
				
				if(o.getEstructura().getJuridica().getStrRazonSocial().equals("") || 
					juridica.getStrRazonSocial().contains(o.getEstructura().getJuridica().getStrRazonSocial())){
					juridica.setPersona(persona);
					listaEstructura.get(i).setJuridica(juridica);
					arrayEstructura.add(listaEstructura.get(i));
				}
				
			}
			
			listaEstructuraComp = new ArrayList<EstructuraComp>();
			for(int i=0; i<arrayEstructura.size(); i++){
				estructuraComp = new EstructuraComp();
				estructuraComp.setEstructura(arrayEstructura.get(i));
				estructuraComp.setEstructuraDetalle(o.getEstructuraDetalle());
				estructuraComp.getEstructura().setStrFechaRegistro(sdf.format(estructuraComp.getEstructura().getDtFechaRegistro()));
				
				//Setear las cadenas concatenadas
				estructuraComp.getEstructuraDetalle().getId().setIntCodigo(arrayEstructura.get(i).getId().getIntCodigo());
				estructuraComp.getEstructuraDetalle().getId().setIntNivel(arrayEstructura.get(i).getId().getIntNivel());
				estructuraComp.getEstructuraDetalle().setIntPersEmpresaPk(arrayEstructura.get(i).getIntPersEmpresaPk());
				estructuraComp.setIntFechaEnviadoDesde(o.getIntFechaEnviadoDesde());
				estructuraComp.setIntFechaEnviadoHasta(o.getIntFechaEnviadoHasta());
				estructuraComp.setIntFechaEfectuadoDesde(o.getIntFechaEfectuadoDesde());
				estructuraComp.setIntFechaEfectuadoHasta(o.getIntFechaEfectuadoHasta());
				estructuraComp.setIntFechaChequeDesde(o.getIntFechaChequeDesde());
				estructuraComp.setIntFechaChequeHasta(o.getIntFechaChequeHasta());
				
				listaEstructuraDetalle = boEstructuraDetalle.getListaEstructuraDetalleBusqueda(estructuraComp);
				log.info("Se obtuvo listaEstructuraDetalle.size: "+listaEstructuraDetalle.size());
				
				if(o.getEstructuraDetalle().getIntSeguSucursalPk()!=null && o.getEstructuraDetalle().getIntSeguSucursalPk()<0){
					listaEstructuraDetalle = getListaEstructuraDetallePorSucursal(listaEstructuraDetalle,o.getEstructuraDetalle().getIntSeguSucursalPk());
				}
				
				estructuraComp.getEstructura().setListaEstructuraDetalle(listaEstructuraDetalle);				
				
				if(listaEstructuraDetalle.size()>0){	
					String strTipoSocioConcatenado = "";
					String strIdTipoSocio = "";
					String strConfiguracionConcatenado = "";
					String strIdConfiguracion = "";
					String strModalidadConcatenado = "";
					String strIdModalidad = "";
					
					
					for(int j=0; j<listaEstructuraDetalle.size(); j++){
						EstructuraDetalle ed = new EstructuraDetalle();
						ed = listaEstructuraDetalle.get(j);
						
						if(estructuraComp.getEstructura().getId().getIntCodigo().equals(ed.getId().getIntCodigo())){
							if(ed.getIntParaTipoSocioCod()!=null && !strIdTipoSocio.contains(ed.getIntParaTipoSocioCod()+"")){
								strTipoSocioConcatenado = strTipoSocioConcatenado+"/"+getLabelCboPorIdMaestro(Constante.PARAM_T_TIPOSOCIO, ed.getIntParaTipoSocioCod());
								strIdTipoSocio = strIdTipoSocio+"/"+ed.getIntParaTipoSocioCod();
							}
							if(ed.getId().getIntCaso()!=null && !strIdConfiguracion.contains(ed.getId().getIntCaso()+"")){
								strConfiguracionConcatenado = strConfiguracionConcatenado+"/"+getLabelCboPorIdMaestro(Constante.PARAM_T_CASOESTRUCTURA, ed.getId().getIntCaso());
								strIdConfiguracion = strIdConfiguracion+"/"+ed.getId().getIntCaso();
							}
							if(ed.getIntParaModalidadCod()!=null && !strIdModalidad.contains(ed.getIntParaModalidadCod()+"")){
								strModalidadConcatenado = strModalidadConcatenado+"/"+getLabelCboPorIdMaestro(Constante.PARAM_T_MODALIDADPLANILLA, ed.getIntParaModalidadCod());
								strIdModalidad = strIdModalidad+"/"+ed.getIntParaModalidadCod();
							}
							
						}
					}
					estructuraComp.setStrTipoSocioConcatenado(!strTipoSocioConcatenado.equals("")?strTipoSocioConcatenado.substring(1, strTipoSocioConcatenado.length()):null);
					estructuraComp.setStrConfiguracionConcatenado(!strConfiguracionConcatenado.equals("")?strConfiguracionConcatenado.substring(1, strConfiguracionConcatenado.length()):null);
					estructuraComp.setStrModalidadConcatenado(!strModalidadConcatenado.equals("")?strModalidadConcatenado.substring(1,strModalidadConcatenado.length()):null);

					
					log.info("Se obtuvo estructura.id.intCodigo: "+estructuraComp.getEstructura().getId().getIntCodigo());
					log.info("strTipoSocioConcatenado: "+estructuraComp.getStrTipoSocioConcatenado());
					log.info("strConfiguracionConcatenado: "+estructuraComp.getStrConfiguracionConcatenado());
					log.info("strModalidadConcatenado: "+estructuraComp.getStrModalidadConcatenado());
				}
				listaEstructuraComp.add(estructuraComp);
			}
		
		}
		
		}catch(BusinessException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		
		return listaEstructuraComp;
	}
	
	public List<EstructuraComp> getListaFiltraEstructuraComp(EstructuraComp o) throws BusinessException{
		log.info("-----------------------Debugging EstructuraService.getListaEmpresa-----------------------------");
		List<EstructuraComp> listaEstructuraComp = null;
		List<Estructura> listaEstructura = null;
		List<EstructuraDetalle> listaEstructuraDetalle = null;
		EstructuraComp estructuraComp = null;
		Juridica juridica = null;
		
		try{			
			log.info("Parametros de busqueda...");
			log.info("estructura.id.intNivel: "+o.getEstructura().getId().getIntNivel());
			log.info("estructura.id.intCodigo: "+o.getEstructura().getId().getIntCodigo());
			log.info("estructura.intPersEmpresaPk: "+o.getEstructura().getIntPersEmpresaPk());
			log.info("estructura.intIdGrupo: "+o.getEstructura().getIntIdGrupo());
			log.info("estructura.juridica.strRazonSocial: "+o.getEstructura().getJuridica().getStrRazonSocial());
			log.info("estructura.intIdCodigoRel: "+o.getEstructura().getIntIdCodigoRel());
			log.info("estructuraDetalle.id.intCaso: "+o.getEstructuraDetalle().getId().getIntCaso());
			log.info("estructuraDetalle.intParaTipoSocioCod: "+o.getEstructuraDetalle().getIntParaTipoSocioCod());
			log.info("estructuraDetalle.intParaModalidadCod: "+o.getEstructuraDetalle().getIntParaModalidadCod());
			log.info("estructuraDetalle.intDiaEnviado: "+o.getEstructuraDetalle().getIntDiaEnviado());
			log.info("estructuraDetalle.intDiaEfectuado: "+o.getEstructuraDetalle().getIntDiaEfectuado());
			log.info("estructuraDetalle.intDiaCheque: "+o.getEstructuraDetalle().getIntDiaCheque());
			log.info("estructuraDetalle.intSeguSucursalPk: "+o.getEstructuraDetalle().getIntSeguSucursalPk());
			log.info("estructuraDetalle.intSeguSubSucursalPk: "+o.getEstructuraDetalle().getIntSeguSubSucursalPk());
			log.info("intFechaEnviadoDesde: "+o.getIntFechaEnviadoDesde());
			log.info("intFechaEnviadoHasta: "+o.getIntFechaEnviadoHasta());
			log.info("intFechaEfectuadoDesde: "+o.getIntFechaEfectuadoDesde());
			log.info("intFechaEfectuadoHasta: "+o.getIntFechaEfectuadoHasta());
			log.info("intFechaChequeDesde: "+o.getIntFechaChequeDesde());
			log.info("intFechaChequeHasta: "+o.getIntFechaChequeHasta());
			
			listaEstructura = boEstructura.getListaEstructuraBusqueda(o.getEstructura());
			log.info("Se obtuvo listaEstructura.size: "+listaEstructura.size());
			
			//Validar por la Razón Social
			ArrayList<Estructura> arrayEstructura = new ArrayList<Estructura>();
			for(int i=0; i<listaEstructura.size(); i++){
				//Obteniendo la nombre de Juridica para Estructura
				log.info("listaEstructura.get(i).getIntPersPersonaPk: "+listaEstructura.get(i).getIntPersPersonaPk());
				PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				juridica = personaFacade.getJuridicaPorPK(listaEstructura.get(i).getIntPersPersonaPk());
				Persona persona = personaFacade.getPersonaPorPK(juridica.getIntIdPersona());
				
				log.info("juridica.strRazonSocial: "+juridica.getStrRazonSocial());
				log.info("juridica.strRucSocial: "+persona.getStrRuc());
				
				
				if(listaEstructura.get(i).getId().getIntNivel().equals(1) || listaEstructura.get(i).getId().getIntNivel().equals(2))
				if(o.getEstructura().getJuridica().getStrRazonSocial().equals("") || 
					juridica.getStrRazonSocial().contains(o.getEstructura().getJuridica().getStrRazonSocial())){
					juridica.setPersona(persona);
					listaEstructura.get(i).setJuridica(juridica);
					arrayEstructura.add(listaEstructura.get(i));
				}
				
			}
			
			listaEstructuraComp = new ArrayList<EstructuraComp>();
			log.info("Se obtuvo listaEstructuraComp.size: "+listaEstructuraComp.size());
			
			for(int i=0; i<arrayEstructura.size(); i++){
				estructuraComp = new EstructuraComp();
				estructuraComp.setEstructura(arrayEstructura.get(i));
				estructuraComp.setEstructuraDetalle(o.getEstructuraDetalle());
				estructuraComp.getEstructura().setStrFechaRegistro(sdf.format(estructuraComp.getEstructura().getDtFechaRegistro()));
				
				//Setear las cadenas concatenadas
				estructuraComp.getEstructuraDetalle().getId().setIntCodigo(arrayEstructura.get(i).getId().getIntCodigo());
				estructuraComp.getEstructuraDetalle().getId().setIntNivel(arrayEstructura.get(i).getId().getIntNivel());
				estructuraComp.getEstructuraDetalle().setIntPersEmpresaPk(arrayEstructura.get(i).getIntPersEmpresaPk());
				estructuraComp.setIntFechaEnviadoDesde(o.getIntFechaEnviadoDesde());
				estructuraComp.setIntFechaEnviadoHasta(o.getIntFechaEnviadoHasta());
				estructuraComp.setIntFechaEfectuadoDesde(o.getIntFechaEfectuadoDesde());
				estructuraComp.setIntFechaEfectuadoHasta(o.getIntFechaEfectuadoHasta());
				estructuraComp.setIntFechaChequeDesde(o.getIntFechaChequeDesde());
				estructuraComp.setIntFechaChequeHasta(o.getIntFechaChequeHasta());
				
				listaEstructuraDetalle = boEstructuraDetalle.getListaEstructuraDetalleBusqueda(estructuraComp);
				log.info("Se obtuvo listaEstructuraDetalle.size: "+listaEstructuraDetalle.size());
				
				if (listaEstructuraDetalle.size() > 0){
					listaEstructuraComp.add(estructuraComp);
				}
				
			}
		}catch(BusinessException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return listaEstructuraComp;
	}
	
	public List<EstructuraComp> getListaEstructuraCompConSucursal(EstructuraComp o) throws BusinessException{
		List<EstructuraComp> listaEstructuraComp = null;
		List<EstructuraDetalle> listaEstructuraDetalle = null;
		Sucursal sucursal = null;
		SucursalId sucursalId = null;
		//Para sucursales
		
		try{
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			listaEstructuraComp =	getListaEstructuraComp(o);
			String strSucursalConcatenado = null;
			for(EstructuraComp estructuraComp : listaEstructuraComp){
				listaEstructuraDetalle = estructuraComp.getEstructura().getListaEstructuraDetalle();
				for(EstructuraDetalle ed : listaEstructuraDetalle){
					//Concatena sucursales 
					if(ed.getIntSeguSucursalPk()!=null){
						sucursalId = new SucursalId();
						sucursal = new Sucursal();
						sucursalId.setIntIdSucursal(ed.getIntSeguSucursalPk());
						sucursal.setId(sucursalId);
						sucursal = empresaFacade.getSucursalPorPK(sucursal);
						ed.setSucursal(sucursal);
						if(strSucursalConcatenado == null){
							strSucursalConcatenado = sucursal.getJuridica().getStrRazonSocial();
						}else{
							if(!strSucursalConcatenado.contains(sucursal.getJuridica().getStrRazonSocial())){
								strSucursalConcatenado = strSucursalConcatenado+"/"+sucursal.getJuridica().getStrRazonSocial();
							}									
						}
						
					}
				}
				estructuraComp.setStrSucursalConcatenado(strSucursalConcatenado);
			}
		}catch(BusinessException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return listaEstructuraComp;
	}
	public List<EstructuraDetalle> getListaEstructuraDetallePorSucursal(List<EstructuraDetalle> lista, Integer idSucursalBusq){
		log.info("-----------------------Debugging EstructuraService.getListaEstructuraDetallePorSucursal-----------------------------");
		List<EstructuraDetalle> listaEstructuraDetalle = new ArrayList<EstructuraDetalle>();
		log.info("idSucursalBusq: "+idSucursalBusq);
		for(int i=0; i<lista.size(); i++){
			EstructuraDetalle ed = lista.get(i);
			log.info("ed.intSeguSucursalPk: "+ed.getIntSeguSucursalPk());
			if(ed.getIntSeguSucursalPk().equals(idSucursalBusq)){
				listaEstructuraDetalle.add(ed);
			}else{
				Sucursal sucursal = null;
				EmpresaFacadeRemote empresaFacade;
				try {
					empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
					if(idSucursalBusq.equals(Constante.PARAM_T_TOTALESSUCURSALES_AGENCIAS)){
						sucursal = empresaFacade.getPorPkYIdTipoSucursal(ed.getIntSeguSucursalPk(),Constante.PARAM_T_TIPOSUCURSAL_AGENCIA);
					}else if(idSucursalBusq.equals(Constante.PARAM_T_TOTALESSUCURSALES_FILIALES)){
						sucursal = empresaFacade.getPorPkYIdTipoSucursal(ed.getIntSeguSucursalPk(),Constante.PARAM_T_TIPOSUCURSAL_FILIAL);
					}else if(idSucursalBusq.equals(Constante.PARAM_T_TOTALESSUCURSALES_OFICINAPRINCIPAL)){
						sucursal = empresaFacade.getPorPkYIdTipoSucursal(ed.getIntSeguSucursalPk(),Constante.PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL);
					}
				} catch (EJBFactoryException e) {
					e.printStackTrace();
				} catch (BusinessException e) {
					e.printStackTrace();
				}
				if(sucursal!=null){
					log.info("sucursal.intPersPersonaPk: "+sucursal.getIntPersPersonaPk());
					listaEstructuraDetalle.add(ed);
				}
			}
		}
		log.info("listaEstructuraDetalle: "+listaEstructuraDetalle.size());
		return listaEstructuraDetalle;
	}
	
	public String getLabelCboPorIdMaestro(String id, Integer idItem){
		log.info("-------------------------------------Debugging getLabelCboPorIdMaestro-------------------------------------");
		List<Tabla> listaTabla = null;
		String label = null;
		try {
			TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = facade.getListaTablaPorIdMaestro(Integer.parseInt(id));
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("listaTala.size: "+listaTabla.size());
		log.info("idItem: "+idItem);
		for(int i=0; i<listaTabla.size(); i++){
			Tabla item = listaTabla.get(i);
			log.info("item.intIdDetalle: "+item.getIntIdDetalle());
			if(idItem.equals(item.getIntIdDetalle())){
				label = item.getStrDescripcion();
			} 
		}
		return label;
	}

	public Estructura getEstructuraPorPK(EstructuraId o) throws BusinessException{
		log.info("-----------------------Debugging EstructuraService.getEstructuraPorPK-----------------------------");
		Estructura estructura = null;
		List<EstructuraDetalle> listEstructuraDetalle = null;
		Persona juridica = null;
		
		try{
			estructura = boEstructura.getEstructuraPorPK(o);
			//Obteniendo la Persona Juridica
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			juridica = facade.getPersonaJuridicaPorIdPersona(estructura.getIntPersPersonaPk());
			estructura.setJuridica(juridica.getJuridica());
			//Obteniendo EstructuraDetalle
			EstructuraId estructuraId = new EstructuraId();
			estructuraId.setIntCodigo(o.getIntCodigo());
			estructuraId.setIntNivel(o.getIntNivel());
			listEstructuraDetalle = boEstructuraDetalle.getListaEstructuraDetallePorEstructuraPK(estructuraId);
			log.info("listEstructuraDetalle.size: "+listEstructuraDetalle.size());
			estructura.setListaEstructuraDetalle(listEstructuraDetalle);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estructura;
	}

	public Estructura grabarEstructura(Estructura o) throws BusinessException{
		log.info("-----------------------Debugging EstructuraService.grabarEstructura-----------------------------");
		Estructura estructura = null; 
		EstructuraId pk = null;
		
		try{
			log.info("estructura.intPersPersonaPk: "+o.getIntPersPersonaPk());
			estructura = boEstructura.grabarEstructura(o);
			log.info("Se grabó estructura(Codigo): "+estructura.getId().getIntCodigo());
			pk = new EstructuraId();
			pk.setIntNivel(estructura.getId().getIntNivel());
			pk.setIntCodigo(estructura.getId().getIntCodigo());
			
			if(o.getListaEstructuraDetallePlanilla()!=null){
				log.info("listaEstructuraDetallePlanilla.size: "+o.getListaEstructuraDetallePlanilla().size());
				estructuraDetalleService.grabarListaDinamicaDetalle(o.getListaEstructuraDetallePlanilla(), pk, Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA);
				log.info("Se grabó estructuraDetallePlanilla...");
			}
			
			if(o.getListaEstructuraDetalleAdministra()!=null){
				log.info("listaEstructuraDetalleAdministra.size: "+o.getListaEstructuraDetalleAdministra().size());
				estructuraDetalleService.grabarListaDinamicaDetalle(o.getListaEstructuraDetalleAdministra(), pk, Constante.PARAM_T_CASOESTRUCTURA_ADMINISTRA);
				log.info("Se grabó estructuraDetalleAdministra...");
			}
			
			if(o.getListaEstructuraDetalleCobranza()!=null){
				log.info("listaEstructuraDetalleCobranza.size: "+o.getListaEstructuraDetalleCobranza().size());
				estructuraDetalleService.grabarListaDinamicaDetalle(o.getListaEstructuraDetalleCobranza(), pk, Constante.PARAM_T_CASOESTRUCTURA_COBRA);
				log.info("Se grabó estructuraDetalleCobranza...");
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estructura;
	}
	
	public Estructura modificarEstructura(Estructura o) throws BusinessException{
		log.info("-----------------------Debugging EstructuraService.modificarEstructura-----------------------------");
		Estructura estructura = null; 
		EstructuraId pk = null;
		
		try{
			estructura = boEstructura.modificarEstructura(o);
			log.info("Se actualizó estructura(Codigo): "+estructura.getId().getIntCodigo());
			pk = new EstructuraId();
			pk.setIntNivel(estructura.getId().getIntNivel());
			pk.setIntCodigo(estructura.getId().getIntCodigo());
			
			if(o.getListaEstructuraDetallePlanilla()!=null){
				log.info("listaEstructuraDetallePlanilla.size: "+o.getListaEstructuraDetallePlanilla().size());
				estructuraDetalleService.grabarListaDinamicaDetalle(o.getListaEstructuraDetallePlanilla(), pk, Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA);
				log.info("Se actualizó estructuraDetallePlanilla...");
			}
			
			if(o.getListaEstructuraDetalleAdministra()!=null){
				log.info("listaEstructuraDetalleAdministra.size: "+o.getListaEstructuraDetalleAdministra().size());
				estructuraDetalleService.grabarListaDinamicaDetalle(o.getListaEstructuraDetalleAdministra(), pk, Constante.PARAM_T_CASOESTRUCTURA_ADMINISTRA);
				log.info("Se actualizó estructuraDetalleAdministra...");
			}
			
			if(o.getListaEstructuraDetalleCobranza()!=null){
				log.info("listaEstructuraDetalleCobranza.size: "+o.getListaEstructuraDetalleCobranza().size());
				estructuraDetalleService.grabarListaDinamicaDetalle(o.getListaEstructuraDetalleCobranza(), pk, Constante.PARAM_T_CASOESTRUCTURA_COBRA);
				log.info("Se actualizó estructuraDetalleCobranza...");
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estructura;
	}
	
	public Estructura eliminarEstructura(EstructuraId id) throws BusinessException{
		log.info("-----------------------Debugging EstructuraService.eliminarEstructura-----------------------------");
		Estructura estructura = null;
		List<Estructura> listaEstructura = null;
		List<EstructuraDetalle> listaEstrucDet = null;
		Estructura estructuraAux = null; 
		
		try{
			estructura = getEstructuraPorPK(id);
			
			//Verificar que no tenga otra Estructura relacionada
			estructuraAux = new Estructura();
			estructuraAux.setId(new EstructuraId());
			estructuraAux.setIntNivelRel(estructura.getId().getIntNivel());
			estructuraAux.setIntIdCodigoRel(estructura.getId().getIntCodigo());
			listaEstructura = boEstructura.getListaEstructuraBusqueda(estructuraAux);
			log.info("No se puede anular. Estructuras asociadas: "+listaEstructura.size());
			if(listaEstructura!=null && listaEstructura.size()>0){
				return estructura;
			}
			
			//Verificar si tiene alguna EstructuraDetalle Activo
			listaEstrucDet = boEstructuraDetalle.getListaEstructuraDetallePorEstructuraPK(estructura.getId());
			log.info("No se puede anular. EstructuraDetalle asociadas: "+listaEstrucDet.size());
			if(listaEstrucDet!=null && listaEstrucDet.size()>0){
				return estructura;
			}
			
			//Si cumple se anula
			estructura.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			estructura = boEstructura.modificarEstructura(estructura);
			log.info("Se anuló estructura(Codigo): "+estructura.getId().getIntCodigo());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estructura;
	}
	
	public List<Estructura> getListaEstructuraPorNivelYCodigoRel(Integer intNivel, Integer intCodigoRel)throws BusinessException{
		log.info("--------------------Debugging EstructuraService.getListaEstructuraPorNivelYCodigoRel--------------------------");
		List<Estructura> lista = null;
		Estructura estructura = null;
		Juridica juridica = null;
		try{
			lista = boEstructura.getListaEstructuraPorNivelYCodigoRel(intNivel, intCodigoRel);
			if(lista!=null){
				log.info("listaEstructura.size: "+lista.size());
				PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				for(int i=0;i<lista.size();i++){
					estructura = lista.get(i);
					log.info("estructura.intPersPersonaPk: "+estructura.getIntPersPersonaPk());
					juridica = facade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
					log.info("juridica.intIdPersona: "+juridica.getIntIdPersona());
					estructura.setJuridica(juridica);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EstructuraComp> getListaCompDetallePorTipoConvenio(Integer intIdNivel,Integer intTipoConvenio, String strRazonSocial, String strNroRuc)throws BusinessException{
		List<EstructuraComp> listaTemp = null;
		List<EstructuraComp> listaTemp2 = null;
		List<EstructuraComp> lista = null;
		List<Juridica> listaJuridica = null;
		Persona persona = null;
		String csvPkPersona = null;
		try{
			listaTemp = boEstructuraDetalle.getListaEstructuraCompPorTipoConvenio(intIdNivel, intTipoConvenio);
			
			if(listaTemp!=null){
				for(int i=0;i<listaTemp.size();i++){
					if(csvPkPersona == null)
						csvPkPersona = String.valueOf(listaTemp.get(i).getEstructura().getIntPersPersonaPk());
					else	
						csvPkPersona = csvPkPersona + "," +listaTemp.get(i).getEstructura().getIntPersPersonaPk();
				}
				PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				if(strRazonSocial!= null && strRazonSocial.trim().equals("")){
					listaJuridica = facade.getListaJuridicaPorInPk(csvPkPersona);
				}else{
					listaJuridica = facade.getListaJuridicaPorInPkLikeRazon(csvPkPersona,strRazonSocial);
				}
				if(listaTemp!=null && listaJuridica != null && listaJuridica.size()>0){
					listaTemp2 = new  ArrayList<EstructuraComp>();
					for(EstructuraComp $estructuraComp : listaTemp){
						for(Juridica $juridica : listaJuridica){
							if($estructuraComp.getEstructura().getIntPersPersonaPk().equals($juridica.getIntIdPersona())){
								$estructuraComp.getEstructura().setJuridica($juridica);
								listaTemp2.add($estructuraComp);
							}
						}
					}
				}
				/*if(listaJuridica != null && listaJuridica.size()>0){
					listaTemp2 = new  ArrayList<EstructuraComp>();
					for(int i=0; i<listaJuridica.size(); i++){
						juridica = listaJuridica.get(i);
						if(juridica != null){
							estructuraComp = listaTemp.get(i);
							estructuraComp.getEstructura().setJuridica(juridica);
						}else
							estructuraComp = new EstructuraComp();
						listaTemp2.add(estructuraComp);
					}
				}*/
				
				if(strNroRuc!=null && !strNroRuc.trim().equals("")){
					lista = new ArrayList<EstructuraComp>();
					EstructuraComp estructuraCompTemp = null;
					persona = facade.getPersonaJuridicaPorRuc(strNroRuc);
					if(persona!=null){
						for(int i=0; i<listaTemp.size(); i++){
							estructuraCompTemp = listaTemp.get(i);
							if(persona.getIntIdPersona().equals(estructuraCompTemp.getEstructura().getIntPersPersonaPk())){
								estructuraCompTemp.getEstructura().setJuridica(persona.getJuridica());
								lista.add(estructuraCompTemp);
							}
						}
					}
				}else{
					lista = listaTemp2;
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Estructura> getListaEstructuraPorNivelYCodigo(Integer intNivel, Integer intCodigoRel)throws BusinessException{
		List<Estructura> lista = null;
		Estructura estructura = null;
		Juridica juridica = null;
		try{
			lista = boEstructura.getListaEstructuraPorNivelYCodigo(intNivel, intCodigoRel);
			if(lista!=null){
				log.info("listaEstructura.size: "+lista.size());
				PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				for(int i=0;i<lista.size();i++){
					estructura = lista.get(i);
					log.info("estructura.intPersPersonaPk: "+estructura.getIntPersPersonaPk());
					juridica = facade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
					log.info("juridica.intIdPersona: "+juridica.getIntIdPersona());
					estructura.setJuridica(juridica);
				}
			}
		}catch(BusinessException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Terceros> getListaFilaTercerosPorDNI(String strDocIdentidad) throws BusinessException {
		List<Terceros> lista = null;
		List<Tabla> listTabla = null;
		
		try{
			lista = boTerceros.getListaFilaTercerosPorDNI(strDocIdentidad);
			
			TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_MES_CALENDARIO));
			
			for(Terceros tercero : lista){
				for(Tabla tabla : listTabla){
					if(tercero.getId().getIntMes().equals(tabla.getIntIdDetalle())){
						tercero.setStrMes(tabla.getStrDescripcion());
					}
				}
			}
		}catch(BusinessException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Terceros> getListaColumnaTercerosPorDNI(String strDocIdentidad) throws BusinessException {
		List<Terceros> lista = null;
		//List<Tabla> listTabla = null;
		
		try{
			lista = boTerceros.getListaColumnaTercerosPorDNI(strDocIdentidad);
		}catch(BusinessException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getConveEstrucDetAdministra(EstructuraComp o) throws BusinessException{
		List<EstructuraDetalle> lista = null;
		
		try{
			lista = boEstructuraDetalle.getConveEstrucDetAdministra(o);
			log.info("listaConveEstrucDetAdministra: "+lista);
			log.info("listaConveEstrucDetAdministra.size: "+lista.size());
			
			//setear lista de Subsucursales de EstructuraDetalle
			for(EstructuraDetalle ed : lista){
				EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				List<Subsucursal> listSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(ed.getIntSeguSucursalPk());
				
				if(listSubsucursal!=null){
					System.out.println("estructuraDetalle.listSubsucursal.size: "+listSubsucursal.size());
					ed.setListaSubsucursal(listSubsucursal);
				}
			}
			
			//filtrar por Razon Social de la Estructura
			if(o.getEstructura()!=null && o.getEstructura().getJuridica()!=null && 
					o.getEstructura().getJuridica().getStrRazonSocial()!=null && !o.getEstructura().getJuridica().getStrRazonSocial().equals("")){
				System.out.println("o.getEstructura().getJuridica().getStrRazonSocial(): "+o.getEstructura().getJuridica().getStrRazonSocial());

				ArrayList<EstructuraDetalle> array = new ArrayList<EstructuraDetalle>();
				for(EstructuraDetalle ed : lista){
					EstructuraId eid = new EstructuraId();
					eid.setIntNivel(ed.getId().getIntNivel());
					eid.setIntCodigo(ed.getId().getIntCodigo());
					Estructura estruc = boEstructura.getEstructuraPorPK(eid);
					
					PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
					Juridica juridica = personaFacade.getJuridicaPorPK(estruc.getIntPersPersonaPk());
					
					System.out.println("juridica.getStrRazonSocial(): "+juridica.getStrRazonSocial());
					if(juridica.getStrRazonSocial().contains(o.getEstructura().getJuridica().getStrRazonSocial())){
						array.add(ed);
					}
				}
				lista = array;
			}
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getConveEstrucDetPlanilla(EstructuraComp o) throws BusinessException{
		log.info("-----------------------Debugging EstructuraService.getConveEstrucDetAdministra-----------------------------");
		List<EstructuraDetalle> lista = null;
		
		try{
			lista = boEstructuraDetalle.getConveEstrucDetPlanilla(o);
			
			//setear lista de Subsucursales de EstructuraDetalle
			for(EstructuraDetalle ed : lista){
				EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				List<Subsucursal> listSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(ed.getIntSeguSucursalPk());
				
				if(listSubsucursal!=null){
					System.out.println("estructuraDetalle.listSubsucursal.size: "+listSubsucursal.size());
					ed.setListaSubsucursal(listSubsucursal);
				}
			}
			
			//filtrar por Razon Social de la Estructura
			if(o.getEstructura()!=null && o.getEstructura().getJuridica()!=null && 
					o.getEstructura().getJuridica().getStrRazonSocial()!=null && !o.getEstructura().getJuridica().getStrRazonSocial().equals("")){
				System.out.println("o.getEstructura().getJuridica().getStrRazonSocial(): "+o.getEstructura().getJuridica().getStrRazonSocial());

				ArrayList<EstructuraDetalle> array = new ArrayList<EstructuraDetalle>();
				for(EstructuraDetalle ed : lista){
					EstructuraId eid = new EstructuraId();
					eid.setIntNivel(ed.getId().getIntNivel());
					eid.setIntCodigo(ed.getId().getIntCodigo());
					Estructura estruc = boEstructura.getEstructuraPorPK(eid);
					
					PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
					Juridica juridica = personaFacade.getJuridicaPorPK(estruc.getIntPersPersonaPk());
					
					System.out.println("juridica.getStrRazonSocial(): "+juridica.getStrRazonSocial());
					if(juridica.getStrRazonSocial().contains(o.getEstructura().getJuridica().getStrRazonSocial())){
						array.add(ed);
					}
				}
				lista = array;
			}
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EstructuraDetalle> getListaEstructuraDetalleIngresos(Integer intIdSucursal, Integer intIdSubSucursal) throws BusinessException{
//		List<EstructuraComp> listaEstructuraComp = null;
		List<EstructuraDetalle> listaEstructuraDetalle = null;
//		List<Estructura> listaEstructura = null;
		Estructura estructura = null;
//		Juridica juridica = null; 
		try {
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			//1. Ir a estructura detalle (CSO_ESTRUCTURADETALLE) y filtrar por caso 2 y sucursal de login
			EstructuraComp estrucComp = new EstructuraComp();
			estrucComp.setEstructuraDetalle(new EstructuraDetalle());
			estrucComp.getEstructuraDetalle().setId(new EstructuraDetalleId());
			estrucComp.getEstructuraDetalle().getId().setIntCaso(Constante.PARAM_T_CASOESTRUCTURA_COBRA);
			estrucComp.getEstructuraDetalle().setIntSeguSucursalPk(intIdSucursal);
			estrucComp.getEstructuraDetalle().setIntSeguSubSucursalPk(intIdSubSucursal);
			listaEstructuraDetalle = boEstructuraDetalle.getListaEstructuraDetalleBusqueda(estrucComp);
			if (listaEstructuraDetalle!=null && !listaEstructuraDetalle.isEmpty()) {
				//2. obtenemos la estructura por la pk
				for (EstructuraDetalle estructuraDetalle : listaEstructuraDetalle) {
					EstructuraId pPK = new EstructuraId();
					pPK.setIntNivel(estructuraDetalle.getId().getIntNivel());
					pPK.setIntCodigo(estructuraDetalle.getId().getIntCodigo());
					estructura = boEstructura.getEstructuraPorPK(pPK);
					//3. una vez hecho esto obtenemos la persona juridica por idPersona
					estructura.setJuridica(personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk()));
					estructuraDetalle.setEstructura(estructura);
				}
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaEstructuraDetalle;
	}
}