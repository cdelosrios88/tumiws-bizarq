package pe.com.tumi.seguridad.empresa.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.empresa.domain.composite.ZonalComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.empresa.bo.SucursalBO;
import pe.com.tumi.seguridad.empresa.bo.SucursalEmpresaBO;
import pe.com.tumi.seguridad.empresa.bo.ZonalBO;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacade;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeLocal;
import pe.com.tumi.seguridad.login.bo.EmpresaUsuarioBO;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;

public class EmpresaService {
	
	protected  static Logger log = Logger.getLogger(EmpresaFacade.class);
	
	private SucursalEmpresaBO boSucursalEmpresa = (SucursalEmpresaBO)TumiFactory.get(SucursalEmpresaBO.class);
	private SucursalBO boSucursal = (SucursalBO)TumiFactory.get(SucursalBO.class);
	private ZonalBO boZonal = (ZonalBO)TumiFactory.get(ZonalBO.class);
	private EmpresaUsuarioBO boEmpresaUsuario = (EmpresaUsuarioBO)TumiFactory.get(EmpresaUsuarioBO.class);
	
	public List<Empresa> getListaEmpresa(Empresa o) throws BusinessException{
		log.info("-----------------------Debugging EmpresaService.getListaEmpresa-----------------------------");
		List<Empresa> listaEmpresa = null;			
		
		try{
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			listaEmpresa = personaFacade.getListaEmpresa(o);
			Empresa emp = null;
			Integer intCantSucur = null;
			for(int i=0; i<listaEmpresa.size(); i++){
				emp = (Empresa) listaEmpresa.get(i);
				intCantSucur = getCantidadSucursalesPorIdEmpresa(emp.getIntIdEmpresa()); 
				emp.setIntCantidadSucursal(intCantSucur);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaEmpresa;
	}
	
	public Integer getCantidadSucursalesPorIdEmpresa(Integer idEmpresa) throws BusinessException{
		log.info("-----------------------Debugging EmpresaService.getCantidadSucursalesPorIdEmpresa-----------------------------");
		Integer cant = null;
		try{
			cant = boSucursalEmpresa.getCantidadSucursalPorIdEmpresa(idEmpresa);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return cant;
	}
	
	public Zonal grabarZonalYListaSucursal(Zonal pZonal,List<Sucursal> listaSucrusal) throws BusinessException{
		Zonal zonal = null;
		Persona persona = null;
		PersonaEmpresa personaEmpresa = null; 
		PersonaFacadeRemote facade = null;
		Sucursal sucursal = null;
		SucursalId idSucursal = null;
		try{
			persona = new Persona();
			persona.setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_JURIDICA);
			persona.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			persona.setJuridica(pZonal.getJuridica());
			personaEmpresa = new PersonaEmpresa();
			personaEmpresa.setId(new PersonaEmpresaPK());
			personaEmpresa.getId().setIntIdEmpresa(pZonal.getId().getIntPersEmpresaPk());
			persona.setPersonaEmpresa(personaEmpresa);
			facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			persona = facade.grabarPersonaJuridica(persona);

			pZonal.setIntPersPersonaPk(persona.getIntIdPersona());
			zonal = boZonal.grabarZonal(pZonal);
			if(listaSucrusal != null && listaSucrusal.size()>0){
				for(int i=0;i<listaSucrusal.size();i++){
					sucursal = listaSucrusal.get(i);
					if(sucursal.getChecked() != null && sucursal.getChecked() == true){
						sucursal.setIntIdZonal(zonal.getId().getIntIdzonal());
					}else{
						sucursal.setIntIdZonal(null);
					}
					boSucursal.modificarSucursal(sucursal);
				}
			}
			//sucursal = boSucursal.getSucursalPorIdPersona(zonal.getSucursal().getId().getIntIdSucursal());
			sucursal = boSucursal.getSucursalPorPk(zonal.getSucursal().getId().getIntIdSucursal());
			sucursal.setIntIdZonal(zonal.getId().getIntIdzonal());
			boSucursal.modificarSucursal(sucursal);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return zonal;
	}
	
	public Zonal modificarZonalYListaSucursal(Zonal pZonal,List<Sucursal> listaSucrusal) throws BusinessException{
		Zonal zonal = null;
		Persona persona = null;
		PersonaEmpresa personaEmpresa = null; 
		PersonaFacadeRemote facade = null;
		Sucursal sucursal = null;
		try{
			facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			facade.modificarJuridica(pZonal.getJuridica());
			zonal = boZonal.modificarZonal(pZonal);
			if(listaSucrusal != null && listaSucrusal.size()>0){
				for(int i=0;i<listaSucrusal.size();i++){
					sucursal = listaSucrusal.get(i);
					if(sucursal.getChecked() != null && sucursal.getChecked() == true){
						sucursal.setIntIdZonal(zonal.getId().getIntIdzonal());
					}else{
						sucursal.setIntIdZonal(null);
					}
					boSucursal.modificarSucursal(sucursal);
				}
			}	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return zonal;
	}
	
	public Zonal eliminarZonalPorIdZonal(Integer pIntIdZonal) throws BusinessException{
		Zonal zonal = null;
		Persona persona = null;
		PersonaFacadeRemote facadePersona = null;
		EmpresaFacadeLocal facadeEmpresa = null;
		Sucursal sucursal = null;
		List<Sucursal> listaSucursal = null;
		try{
			zonal = boZonal.getZonalPorIdZonal(pIntIdZonal);
			zonal.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			boZonal.modificarZonal(zonal);
			facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			persona = facadePersona.getPersonaPorPK(zonal.getIntPersPersonaPk());
			persona.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			facadePersona.modificarPersona(persona);
			facadeEmpresa = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			listaSucursal = facadeEmpresa.getListaSucursalPorPkZonal(pIntIdZonal);
			if(listaSucursal != null && listaSucursal.size()>0){
				for(int i=0;i<listaSucursal.size();i++){
					sucursal = listaSucursal.get(i);
					sucursal.setIntIdZonal(null);
					boSucursal.modificarSucursal(sucursal);
				}
			}	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return zonal;
	}
	
	public List<ZonalComp> getListaZonalCompDeBusquedaZonal(Zonal o) throws BusinessException{
		ZonalComp dto = null;
		Zonal dtoZonal = null;
		List<ZonalComp> lista = null;
		List<Juridica> listaJuridica = null;
		String csvPkPersona = null;
		//EmpresaFacadeRemote facade = null;
		PersonaFacadeRemote facade = null;
		List<Zonal> listaZonal = null;
		Juridica juridica = null;
		try{
			listaZonal = boZonal.getListaZonalDeBusqueda(o);
			if(listaZonal != null && listaZonal.size()>0){
				for(int i=0;i<listaZonal.size();i++){
					if(csvPkPersona == null)
						csvPkPersona = String.valueOf(listaZonal.get(i).getIntPersPersonaPk()); 
					else	
						csvPkPersona = csvPkPersona + "," +listaZonal.get(i).getIntPersPersonaPk();
				}
				juridica = o.getJuridica();
				facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				if(juridica.getStrRazonSocial()!= null && juridica.getStrRazonSocial().trim().equals("")){
					listaJuridica = facade.getListaJuridicaPorInPk(csvPkPersona);
				}else{
					listaJuridica = facade.getListaJuridicaPorInPkLikeRazon(csvPkPersona,juridica.getStrRazonSocial());
				}
				if(listaJuridica != null && listaJuridica.size()>0){
					lista = new ArrayList<ZonalComp>();
					for(int i=0;i<listaJuridica.size();i++){
						dto = new ZonalComp();
						juridica = listaJuridica.get(i);
						if(juridica != null){
							dtoZonal = boZonal.getZonalPorIdPersona(juridica.getIntIdPersona());
							dtoZonal.setJuridica(juridica);
							dto.setZonal(dtoZonal);
							dto.setIntCantidadSucursal(boSucursal.getCantidadSucursalPorPkZonal(dtoZonal.getId().getIntIdzonal()));
							dto.setEmpresa(facade.getJuridicaPorPK(dtoZonal.getId().getIntPersEmpresaPk()));
						}else
							dto.setZonal(new Zonal());
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

	public List<Natural> getListaNaturalDeUsuarioPorIdEmpresa(Integer IdEmpresa) throws BusinessException{
		List<Natural> lista = null;
		List<EmpresaUsuario> listaEmpresaUsuario = null;
		String csvPkPersona = null;
		PersonaFacadeRemote facade = null;
		try{
			listaEmpresaUsuario = boEmpresaUsuario.getListaEmpresaUsuarioPorIdEmpresa(IdEmpresa);
			if(listaEmpresaUsuario != null && listaEmpresaUsuario.size()>0){
				for(int i=0;i<listaEmpresaUsuario.size();i++){
					if(csvPkPersona == null)
						csvPkPersona = String.valueOf(listaEmpresaUsuario.get(i).getId().getIntPersPersonaPk()); 
					else	
						csvPkPersona = csvPkPersona + "," +listaEmpresaUsuario.get(i).getId().getIntPersPersonaPk();
				}
				facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				lista = facade.getListaNaturalPorInPk(csvPkPersona);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
