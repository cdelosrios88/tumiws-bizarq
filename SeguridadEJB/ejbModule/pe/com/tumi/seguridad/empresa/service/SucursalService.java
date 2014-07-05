package pe.com.tumi.seguridad.empresa.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalCodigo;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.empresa.domain.composite.SucursalComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.bo.SubSucursalBO;
import pe.com.tumi.seguridad.empresa.bo.SucursalBO;
import pe.com.tumi.seguridad.empresa.bo.SucursalCodigoBO;
import pe.com.tumi.seguridad.empresa.bo.SucursalZonalBO;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacade;

public class SucursalService {
	
	protected  static Logger log = Logger.getLogger(EmpresaFacade.class);
	
	private SucursalBO boSucursal = (SucursalBO)TumiFactory.get(SucursalBO.class);
	private SucursalCodigoBO boSucursalCodigo = (SucursalCodigoBO)TumiFactory.get(SucursalCodigoBO.class);
	private SubSucursalBO boSubSucursal = (SubSucursalBO)TumiFactory.get(SubSucursalBO.class);
	private SucursalZonalBO boSucursalZonal = (SucursalZonalBO)TumiFactory.get(SucursalZonalBO.class);
	
	public List<Sucursal> getListaSucursalPorPkEmpresa(Integer pIntPK) throws BusinessException{
		//log.info("-----------------------Debugging EmpresaService.getListaSucursal-----------------------------");
		List<Sucursal> listaSucursal = null;
		Sucursal sucursal = null;
		Juridica juridica = null;
		try{
			listaSucursal = boSucursal.getListaSucursalPorPkEmpresa(pIntPK);
			if(listaSucursal!=null){
				//log.info("listaSucursal.size: "+listaSucursal.size());
				PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				for(int i=0;i<listaSucursal.size();i++){
					sucursal = listaSucursal.get(i);
					juridica = facade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
					sucursal.setJuridica(juridica);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSucursal;
	}
	
	public List<Sucursal> getListaSucursalSinZonalPorPkEmpresa(Integer pIntPK) throws BusinessException{
		//log.info("-----------------------Debugging EmpresaService.getListaSucursal-----------------------------");
		List<Sucursal> listaSucursal = null;
		Sucursal sucursal = null; 
		PersonaFacadeRemote facade = null;
		Juridica juridica = null;
		try{
			listaSucursal = boSucursal.getListaSucursalSinZonalPorPkEmpresa(pIntPK);
			facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			for(int i=0;i<listaSucursal.size();i++){
				sucursal = listaSucursal.get(i);
				juridica = facade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
				if(juridica!=null)
					sucursal.setJuridica(juridica);
				else
					sucursal.setJuridica(new Juridica());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSucursal;
	}

	public List<Sucursal> getListaSucursalPorPkZonal(Integer pIntPK) throws BusinessException{
		//log.info("-----------------------Debugging EmpresaService.getListaSucursal-----------------------------");
		List<Sucursal> listaSucursal = null;
		Sucursal sucursal = null; 
		PersonaFacadeRemote facade = null;
		Juridica juridica = null;
		try{
			listaSucursal = boSucursal.getListaSucursalPorPkZonal(pIntPK);
			facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			for(int i=0;i<listaSucursal.size();i++){
				sucursal = listaSucursal.get(i);
				juridica = facade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
				if(juridica!=null)
					sucursal.setJuridica(juridica);
				else
					sucursal.setJuridica(new Juridica());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSucursal;
	}
	
	public Sucursal grabarSucursal(Sucursal o)throws BusinessException{
		Persona per  = null;
		Sucursal suc = null;
		PersonaFacadeRemote personaFacade = null;
		PersonaEmpresa personaEmpresa = null;
		PersonaRol personaRol = null;
		List<SucursalCodigo> listaSucursalCodigo = null;
		List<Subsucursal> listaSubSucursal = null;
		try{
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			per = o.getJuridica().getPersona();
			per.setJuridica(o.getJuridica());
			personaEmpresa = new PersonaEmpresa();
			personaEmpresa.setId(new PersonaEmpresaPK());
			personaEmpresa.getId().setIntIdEmpresa(o.getId().getIntPersEmpresaPk());
			per.setPersonaEmpresa(personaEmpresa);
			
			personaRol = new PersonaRol();
			personaRol.setId(new PersonaRolPK());
			personaRol.getId().setIntIdEmpresa(o.getId().getIntPersEmpresaPk());
			personaRol.getId().setIntParaRolPk(Constante.PARAM_T_TIPOROL_SUCURSAL);
			personaRol.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			//per.setPersonaRol(personaRol);
			per.getPersonaEmpresa().setPersonaRol(personaRol);
			
			per = personaFacade.grabarPersonaJuridica(per);
			o.getJuridica().setIntIdPersona(per.getIntIdPersona());
			o.setIntPersPersonaPk(per.getIntIdPersona());
			suc = boSucursal.grabarSucursal(o);
			
			listaSucursalCodigo = o.getListaSucursalCodigo();
			//Grabar Lista Codigo de Terceros
			if(listaSucursalCodigo!=null){
				grabarListaDinamicaSucursalCodigo(listaSucursalCodigo, o);
			}
			
			listaSubSucursal = o.getListaSubSucursal();
			//Grabar Lista de SubSucursales
			if(listaSubSucursal!=null){
				grabarListaDinamicaSubSucursal(listaSubSucursal, o);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return suc;
	}
	
	public Sucursal modificarSucursal(Sucursal o)throws BusinessException{
		Persona per  = null;
		Sucursal suc = null;
		List<SucursalCodigo> listaSucursalCodigo = null;
		List<Subsucursal> listaSubSucursal = null;
		try{
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			per = o.getJuridica().getPersona();
			per.setJuridica(o.getJuridica());
			per = personaFacade.modificarPersonaJuridica(per);
			suc = boSucursal.modificarSucursal(o);
			
			listaSucursalCodigo = o.getListaSucursalCodigo();
			//Grabar Lista Codigo de Terceros
			if(listaSucursalCodigo!=null){
				grabarListaDinamicaSucursalCodigo(listaSucursalCodigo, o);
			}
			
			listaSubSucursal = o.getListaSubSucursal();
			//Grabar Lista de SubSucursales
			if(listaSubSucursal!=null){
				grabarListaDinamicaSubSucursal(listaSubSucursal, o);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return suc;
	}
	
	public List<SucursalComp> getListaSucursalCompDeBusquedaSucursal(Sucursal o) throws BusinessException{
		SucursalComp dto = null;
		Sucursal dtoSucursal = null;
		List<SucursalComp> lista = null;
		List<Juridica> listaJuridica = null;
		String csvPkPersona = null;
		PersonaFacadeRemote personaFacade = null;
		List<Sucursal> listaSucursal = null;
		Juridica juridica = null;
		try{
			listaSucursal = boSucursal.getListaSucursalDeBusqueda(o);
			//log.info("listaSucursal.size(): " + listaSucursal.size());
			for(int i=0;i<listaSucursal.size();i++){
				if(csvPkPersona == null)
					csvPkPersona = String.valueOf(listaSucursal.get(i).getIntPersPersonaPk());
				else
					csvPkPersona = csvPkPersona + "," +listaSucursal.get(i).getIntPersPersonaPk();
			}
			//log.info("csvPkPersona: "+csvPkPersona);
			juridica = o.getJuridica();
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			if(juridica.getStrRazonSocial()!= null && juridica.getStrRazonSocial().trim().equals("")){
				listaJuridica = personaFacade.getListaJuridicaPorInPk(csvPkPersona);
			}else{
				listaJuridica = personaFacade.getListaJuridicaPorInPkLikeRazon(csvPkPersona,juridica.getStrRazonSocial());
			}
			
			if(listaJuridica != null && listaJuridica.size()>0){
				lista = new ArrayList<SucursalComp>();
				for(int i=0;i<listaJuridica.size();i++){
					dto = new SucursalComp();
					dto.setEmpresa(new Empresa());
					dto.getEmpresa().setJuridica(new Juridica());
					juridica = listaJuridica.get(i);
					dtoSucursal = boSucursal.getSucursalPorIdPersona(juridica.getIntIdPersona());
					if(dtoSucursal!=null){
						dtoSucursal.setJuridica(juridica);
						dto.setSucursal(dtoSucursal);
						for(int j=0; j<listaSucursal.size();j++){
							if(juridica.getIntIdPersona().equals(listaSucursal.get(j).getIntPersPersonaPk())){
								dto.setIntCantidadSubSucursal(boSubSucursal.getCantidadSubSucursalPorPkSucursal(listaSucursal.get(j).getId().getIntIdSucursal()));
							}
						}
						dto.getEmpresa().setJuridica(personaFacade.getJuridicaPorPK(dtoSucursal.getId().getIntPersEmpresaPk()));
					}
					lista.add(dto);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SucursalCodigo> grabarListaDinamicaSucursalCodigo(List<SucursalCodigo> lstSucursalCodigo, Sucursal suc) throws BusinessException{
		SucursalCodigo succod = null;
		Sucursal pk = null;
		SucursalCodigo sucCodTemp = null;
		try{
			for(int i=0; i<lstSucursalCodigo.size(); i++){
				succod = (SucursalCodigo) lstSucursalCodigo.get(i);
				if(succod.getSucursal()==null){
					pk = new Sucursal();
					pk.setId(new SucursalId());
					pk.getId().setIntPersEmpresaPk(suc.getId().getIntPersEmpresaPk());
					pk.getId().setIntIdSucursal(suc.getId().getIntIdSucursal());
					succod.setSucursal(pk);
					succod = boSucursalCodigo.grabarSucursalCodigo(succod);
				}else{
					sucCodTemp = boSucursalCodigo.getSucursalCodigoPorPK(succod);
					if(sucCodTemp == null){
						succod = boSucursalCodigo.grabarSucursalCodigo(succod);
					}else{
						succod = boSucursalCodigo.modificarSucursalCodigo(succod);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstSucursalCodigo;
	}
	
	public List<Subsucursal> grabarListaDinamicaSubSucursal(List<Subsucursal> lstSubSucursal, Sucursal o) throws BusinessException{
		Subsucursal subs = null;
		SubSucursalPK pk = null;
		Subsucursal subsucTemp = null;
		try{
			for(int i=0; i<lstSubSucursal.size(); i++){
				subs = (Subsucursal) lstSubSucursal.get(i);
				if(subs.getId()==null){
					pk = new SubSucursalPK();
					pk.setIntPersEmpresaPk(o.getId().getIntPersEmpresaPk());
					pk.setIntIdSucursal(o.getId().getIntIdSucursal());
					subs.setId(pk);
					subs = boSubSucursal.grabarSubSucursal(subs);
				}else{
					subsucTemp = boSubSucursal.getSubSucursalPorPK(subs.getId());
					if(subsucTemp == null){
						subs = boSubSucursal.grabarSubSucursal(subs);
					}else{
						subs = boSubSucursal.modificarSubSucursal(subs);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstSubSucursal;
	}
	
	public List<Sucursal> getListaSucursalZonalPorPkEmpresa(Integer intIdEmpresa) throws BusinessException{
		List<Sucursal> listaSucursal = null;
		Sucursal sucursal = null;
		Zonal zonal = null;
		try{
			listaSucursal = boSucursalZonal.getListaSucursalPorPkEmpresa(intIdEmpresa);
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			for(int i=0;i<listaSucursal.size();i++){
				sucursal = listaSucursal.get(i);
				sucursal.setJuridica(facade.getJuridicaPorPK(sucursal.getId().getIntIdSucursal()));
				if(sucursal.getZonal()!=null){
					zonal = sucursal.getZonal(); 
					zonal.setJuridica(facade.getJuridicaPorPK(zonal.getId().getIntIdzonal()));
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSucursal;
	}
	
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYTipo(Integer intIdEmpresa,Integer pIntTipo) throws BusinessException{
		List<Sucursal> listaSucursal = null;
		Sucursal sucursal = null;
		Zonal zonal = null;
		try{
			listaSucursal = boSucursalZonal.getListaSucursalPorPkEmpresaYTipo(intIdEmpresa,pIntTipo);
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			for(int i=0;i<listaSucursal.size();i++){
				sucursal = listaSucursal.get(i);
				sucursal.setJuridica(facade.getJuridicaPorPK(sucursal.getId().getIntIdSucursal()));
				if(sucursal.getZonal()!=null){
					zonal = sucursal.getZonal();
					zonal.setJuridica(facade.getJuridicaPorPK(zonal.getId().getIntIdzonal()));
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSucursal;
	}
	
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYTipoDeAne(Integer intIdEmpresa,Integer pIntTipo) throws BusinessException{
		List<Sucursal> listaSucursal = null;
		Sucursal sucursal = null;
		Zonal zonal = null;
		try{
			listaSucursal = boSucursalZonal.getListaSucursalPorPkEmpresaYTipoDeAne(intIdEmpresa,pIntTipo);
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			for(int i=0;i<listaSucursal.size();i++){
				sucursal = listaSucursal.get(i);
				sucursal.setJuridica(facade.getJuridicaPorPK(sucursal.getId().getIntIdSucursal()));
				if(sucursal.getZonal()!=null){
					zonal = sucursal.getZonal(); 
					zonal.setJuridica(facade.getJuridicaPorPK(zonal.getId().getIntIdzonal()));
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSucursal;
	}
	
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYTipoDeLib(Integer intIdEmpresa,Integer pIntTipo) throws BusinessException{
		List<Sucursal> listaSucursal = null;
		Sucursal sucursal = null;
		Zonal zonal = null;
		try{
			listaSucursal = boSucursalZonal.getListaSucursalPorPkEmpresaYTipoDeLib(intIdEmpresa,pIntTipo);
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			for(int i=0;i<listaSucursal.size();i++){
				sucursal = listaSucursal.get(i);
				sucursal.setJuridica(facade.getJuridicaPorPK(sucursal.getId().getIntIdSucursal()));
				if(sucursal.getZonal()!=null){
					zonal = sucursal.getZonal();
					zonal.setJuridica(facade.getJuridicaPorPK(zonal.getId().getIntIdzonal()));
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSucursal;
	}
	
	public List<Sucursal> getListaSucursalZonaPorPkEmpresaYIdZonalYTipo(Integer intIdEmpresa,Integer intIdZonal,Integer pIntTipo) throws BusinessException{
		List<Sucursal> listaSucursal = null;
		Sucursal sucursal = null;
		Zonal zonal = null;
		try{
			listaSucursal = boSucursalZonal.getListaSucursalPorPkEmpresaYIdZonalYTipo(intIdEmpresa,intIdZonal,pIntTipo);
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			for(int i=0;i<listaSucursal.size();i++){
				sucursal = listaSucursal.get(i);
				sucursal.setJuridica(facade.getJuridicaPorPK(sucursal.getIntPersPersonaPk()));
				if(sucursal.getZonal()!=null){
					sucursal.setChecked(new Boolean(true));
					zonal = sucursal.getZonal(); 
					zonal.setJuridica(facade.getJuridicaPorPK(zonal.getIntPersPersonaPk()));
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSucursal;
	}
	
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYIdZonalYTipoDeAne(Integer intIdEmpresa,Integer intIdZonal,Integer pIntTipo) throws BusinessException{
		List<Sucursal> listaSucursal = null;
		Sucursal sucursal = null;
		Zonal zonal = null;
		try{
			listaSucursal = boSucursalZonal.getListaSucursalPorPkEmpresaYIdZonalYTipoDeAne(intIdEmpresa,intIdZonal,pIntTipo);
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			for(int i=0;i<listaSucursal.size();i++){
				sucursal = listaSucursal.get(i);
				sucursal.setJuridica(facade.getJuridicaPorPK(sucursal.getIntPersPersonaPk()));
				if(sucursal.getZonal()!=null){
					sucursal.setChecked(new Boolean(true));
					zonal = sucursal.getZonal(); 
					zonal.setJuridica(facade.getJuridicaPorPK(zonal.getIntPersPersonaPk()));
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSucursal;
	}
	
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYIdZonalYTipoDeLib(Integer intIdEmpresa,Integer intIdZonal,Integer pIntTipo) throws BusinessException{
		List<Sucursal> listaSucursal = null;
		Sucursal sucursal = null;
		Zonal zonal = null;
		try{
			listaSucursal = boSucursalZonal.getListaSucursalPorPkEmpresaYIdZonalYTipoDeLib(intIdEmpresa,intIdZonal,pIntTipo);
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			for(int i=0;i<listaSucursal.size();i++){
				sucursal = listaSucursal.get(i);
				sucursal.setJuridica(facade.getJuridicaPorPK(sucursal.getIntPersPersonaPk()));
				if(sucursal.getZonal()!=null){
					sucursal.setChecked(new Boolean(true));
					zonal = sucursal.getZonal(); 
					zonal.setJuridica(facade.getJuridicaPorPK(zonal.getIntPersPersonaPk()));
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSucursal;
	}
	
	public Sucursal getSucursalPorIdSucursal(Integer pId) throws BusinessException{
		Sucursal sucursal = null;
		try{
			sucursal = boSucursal.getSucursalPorIdPersona(pId);
			PersonaFacadeRemote facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			ContactoFacadeRemote facadeContacto = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			if(sucursal!=null){
				sucursal.setJuridica(facadePersona.getJuridicaPorPK(sucursal.getIntPersPersonaPk()));
				if(facadeContacto.getListaDomicilio(pId)!=null){
					if(sucursal.getJuridica()!=null){
						sucursal.getJuridica().setPersona(new Persona());
						sucursal.getJuridica().getPersona().setListaDomicilio(facadeContacto.getListaDomicilio(pId));
					}
				}
				if(boSucursalCodigo.getListaSucursalCodigoPorIdSucursal(sucursal.getId().getIntIdSucursal())!=null){
					sucursal.setListaSucursalCodigo(boSucursalCodigo.getListaSucursalCodigoPorIdSucursal(sucursal.getId().getIntIdSucursal()));
				}
				if(boSubSucursal.getListaSubSucursalPorIdSucursal(sucursal.getId().getIntIdSucursal())!=null){
					sucursal.setListaSubSucursal(boSubSucursal.getListaSubSucursalPorIdSucursal(sucursal.getId().getIntIdSucursal()));
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return sucursal;
	}
	
	public Sucursal eliminarSucursal(Sucursal o) throws BusinessException {
		//log.info("-----------------------Debugging AreaService.eliminarArea-----------------------------");
		Sucursal sucursal = null;
		try{
			o.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			sucursal = boSucursal.eliminarSucursal(o);
			log.info("area.id.intIdSucursal: "+o.getId().getIntIdSucursal());
			//areaCodigoService.eliminarListaAreaCodigo(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return sucursal;
	}
	
	public Sucursal getSucursalPorPk(Sucursal sucursal) throws BusinessException{
		log.info("SucursalService.getSucursalPorPk");
		try{
			sucursal = boSucursal.getSucursalPorPk(sucursal.getId().getIntIdSucursal());
			log.info(sucursal);
			PersonaFacadeRemote facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			if(sucursal!=null){
				sucursal.setJuridica(facadePersona.getJuridicaPorPK(sucursal.getIntPersPersonaPk()));				
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return sucursal;
	}

	public Sucursal getSucursalPorPk(Integer intIdSucursal) throws BusinessException{
		Sucursal sucursal = null;
		try{
			sucursal = boSucursal.getSucursalPorPk(intIdSucursal);
			PersonaFacadeRemote facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			if(sucursal!=null){
				sucursal.setJuridica(facadePersona.getJuridicaPorPK(sucursal.getIntPersPersonaPk()));
				sucursal.setListaSubSucursal(boSubSucursal.getListaSubSucursalPorIdSucursal(sucursal.getId().getIntIdSucursal()));
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return sucursal;
	}	
}