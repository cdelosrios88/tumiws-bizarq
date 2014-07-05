package pe.com.tumi.seguridad.login.service;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.login.bo.EmpresaUsuarioBO;
import pe.com.tumi.seguridad.login.bo.UsuarioBO;
import pe.com.tumi.seguridad.login.bo.UsuarioPerfilBO;
import pe.com.tumi.seguridad.login.bo.UsuarioSubSucursalBO;
import pe.com.tumi.seguridad.login.bo.UsuarioSucursalBO;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfil;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursalId;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursal;

public class LoginService {

	private UsuarioBO boUsuario = (UsuarioBO)TumiFactory.get(UsuarioBO.class);
	private EmpresaUsuarioBO boEmpresaUsuario = (EmpresaUsuarioBO)TumiFactory.get(EmpresaUsuarioBO.class);
	private UsuarioPerfilBO boUsuarioPerfil = (UsuarioPerfilBO)TumiFactory.get(UsuarioPerfilBO.class);
	private UsuarioSucursalBO boUsuarioSucursal = (UsuarioSucursalBO)TumiFactory.get(UsuarioSucursalBO.class);
	private UsuarioSubSucursalBO boUsuarioSubSucursal = (UsuarioSubSucursalBO)TumiFactory.get(UsuarioSubSucursalBO.class);
	
	public Usuario grabarUsuarioPersona(Usuario pUsuario,Integer intPersonaRegistra)throws BusinessException{
		PersonaFacadeRemote facade = null;
		Usuario usuario = null;
		Persona persona = null;
		EmpresaUsuario empresaUsuario = null;
		List<PersonaEmpresa> listaPersonaEmpresa = null;
		PersonaEmpresa lPersonaEmpresa = null;
		PersonaRol lPersonaRol = null;
		try{
			facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			if(pUsuario.getListaEmpresaUsuario()!=null){
				listaPersonaEmpresa = new ArrayList<PersonaEmpresa>();
				for(int i=0;i<pUsuario.getListaEmpresaUsuario().size();i++){
					empresaUsuario = pUsuario.getListaEmpresaUsuario().get(i);
					lPersonaEmpresa = new PersonaEmpresa();
					lPersonaEmpresa.setId(new PersonaEmpresaPK());
					lPersonaEmpresa.getId().setIntIdEmpresa(empresaUsuario.getId().getIntPersEmpresaPk());
					
					lPersonaRol = new PersonaRol();
					lPersonaRol.setId(new PersonaRolPK());
					lPersonaRol.getId().setIntIdEmpresa(empresaUsuario.getId().getIntPersEmpresaPk());
					lPersonaRol.getId().setIntParaRolPk(Constante.PARAM_T_TIPOROL_USUARIO);
					lPersonaRol.getId().setDtFechaInicio(new Date());
					lPersonaRol.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					lPersonaEmpresa.setPersonaRol(lPersonaRol);
					
					listaPersonaEmpresa.add(lPersonaEmpresa);
				}
				pUsuario.getPersona().setListaPersonaEmpresa(listaPersonaEmpresa);
			}
			if(pUsuario.getPersona().getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
				persona = facade.grabarPersonaNatural(pUsuario.getPersona());
			}else if(pUsuario.getPersona().getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_JURIDICA)==0){
				persona = facade.grabarPersonaJuridica(pUsuario.getPersona());
			}
			pUsuario.setIntPersPersonaPk(persona.getIntIdPersona());
			usuario = boUsuario.grabarUsuario(pUsuario);
			if(usuario.getListaEmpresaUsuario()!=null){
				grabarListaDinamicaEmpresaUsuario(usuario.getListaEmpresaUsuario(),usuario.getIntPersPersonaPk(),intPersonaRegistra);
				for(int i=0;i<usuario.getListaEmpresaUsuario().size();i++){
					empresaUsuario = usuario.getListaEmpresaUsuario().get(i);
					if(empresaUsuario.getListaUsuarioPerfil()!=null)
						grabarListaDinamicaUsuarioPerfil(empresaUsuario.getListaUsuarioPerfil(), usuario.getIntPersPersonaPk(),intPersonaRegistra);
					if(empresaUsuario.getListaUsuarioSucursal()!=null)
						grabarListaDinamicaUsuarioSucursal(empresaUsuario.getListaUsuarioSucursal(), usuario.getIntPersPersonaPk());
					if(empresaUsuario.getListaUsuarioSubSucursal()!=null)
						grabarListaDinamicaUsuarioSubSucursal(empresaUsuario.getListaUsuarioSubSucursal(), usuario.getIntPersPersonaPk());
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return usuario;
	}
	
	public Usuario modificarUsuarioPersona(Usuario pUsuario,Integer intIdPersonaModifica)throws BusinessException{
		PersonaFacadeRemote facade = null;
		Usuario usuario = null;
		Persona persona = null;
		EmpresaUsuario empresaUsuario = null;
		try{
			facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			if(pUsuario.getPersona().getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
				persona = facade.modificarPersonaNatural(pUsuario.getPersona());
			}else if(pUsuario.getPersona().getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_JURIDICA)==0){
				persona = facade.modificarPersonaJuridica(pUsuario.getPersona());
			}
			pUsuario.setIntPersPersonaPk(persona.getIntIdPersona());
			usuario = boUsuario.modificarUsuario(pUsuario);
			if(usuario.getListaEmpresaUsuario()!=null){
				grabarListaDinamicaEmpresaUsuario(usuario.getListaEmpresaUsuario(),usuario.getIntPersPersonaPk(),intIdPersonaModifica);
				for(int i=0;i<usuario.getListaEmpresaUsuario().size();i++){
					empresaUsuario = usuario.getListaEmpresaUsuario().get(i);
					if(empresaUsuario.getListaUsuarioPerfil()!=null)
						grabarListaDinamicaUsuarioPerfil(empresaUsuario.getListaUsuarioPerfil(), usuario.getIntPersPersonaPk(),intIdPersonaModifica);
					if(empresaUsuario.getListaUsuarioSucursal()!=null)
						grabarListaDinamicaUsuarioSucursal(empresaUsuario.getListaUsuarioSucursal(), usuario.getIntPersPersonaPk());
					if(empresaUsuario.getListaUsuarioSubSucursal()!=null)
						grabarListaDinamicaUsuarioSubSucursal(empresaUsuario.getListaUsuarioSubSucursal(), usuario.getIntPersPersonaPk());
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return usuario;
	}
	
	public void eliminarIntegralUsuarioPersonaPorIdPersona(Integer pIntIdPersona,Integer pIntIdPersonaElimina)throws BusinessException{
		Usuario usuario = null;
		EmpresaUsuario empresaUsuario = null;
		List<EmpresaUsuario> listaEmpresaUsuario = null;
		Timestamp ltsFechaEliminacion = null;
		try{
			usuario = boUsuario.getUsuarioPorPk(pIntIdPersona);
			ltsFechaEliminacion = JFecha.obtenerTimestampDeFechayHoraActual();
			usuario.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			usuario.setTsFechaEliminacion(ltsFechaEliminacion);
			boUsuario.modificarUsuario(usuario);
			listaEmpresaUsuario = boEmpresaUsuario.getListaEmpresaUsuarioPorIdPersona(pIntIdPersona);
			if(listaEmpresaUsuario!=null && listaEmpresaUsuario.size()>0){
				
				for(int i=0;i<listaEmpresaUsuario.size();i++){
					empresaUsuario = listaEmpresaUsuario.get(i);
					empresaUsuario.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					empresaUsuario.setTsFechaEliminacion(ltsFechaEliminacion);
					empresaUsuario.setIntPersPersonaEliminaPk(pIntIdPersonaElimina);
					boEmpresaUsuario.modificarEmpresaUsuario(empresaUsuario);
					eliminarIntegralUsuarioPerfilPorPkEmpresaUsuario(empresaUsuario.getId(),ltsFechaEliminacion,pIntIdPersonaElimina);
					eliminarIntegralUsuarioSucursalPorPkEmpresaUsuario(empresaUsuario.getId(),ltsFechaEliminacion);
				}	
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public List<EmpresaUsuario> grabarListaDinamicaEmpresaUsuario(List<EmpresaUsuario> pLista, Integer intIdPersona,Integer intIdPersonaModifica) throws BusinessException{
		EmpresaUsuario dto = null;
		EmpresaUsuario dtoTemp = null;
		try{
			for(int i=0; i<pLista.size(); i++){
				dto = pLista.get(i);
				if(dto.getId()==null || dto.getId().getIntPersPersonaPk() == null ){
					dto.getId().setIntPersPersonaPk(intIdPersona);
					dto = boEmpresaUsuario.grabarEmpresaUsuario(dto);
				}else{
					dtoTemp = boEmpresaUsuario.getEmpresaUsuarioPorPk(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntPersPersonaPk(intIdPersona);
						dto = boEmpresaUsuario.grabarEmpresaUsuario(dto);
					}else{
						dto = boEmpresaUsuario.modificarEmpresaUsuario(dto);
						if(dto.getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)==0){
							eliminarUsuarioPerfilPorPkEmpresaUsuario(dto.getId(),intIdPersonaModifica);
							eliminarUsuarioSucursalPorPkEmpresaUsuario(dto.getId());
						}
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return pLista;
	}
	
	public List<UsuarioPerfil> grabarListaDinamicaUsuarioPerfil(List<UsuarioPerfil> pLista, Integer intIdPersona,Integer intPersonaRegistra) throws BusinessException{
		UsuarioPerfil dto = null;
		UsuarioPerfil dtoTemp = null;
		try{
			for(int i=0; i<pLista.size(); i++){
				dto = pLista.get(i);
				if(dto.getId()==null || dto.getId().getIntPersPersonaPk() == null){
					dto.getId().setIntPersPersonaPk(intIdPersona);
					dto.setIntPersPersonaUsuarioPk(intPersonaRegistra);
					dto = boUsuarioPerfil.grabarUsuarioPerfilPersona(dto);
				}else{
					dtoTemp = boUsuarioPerfil.getUsuarioPerfilPorPk(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntPersPersonaPk(intIdPersona);
						dto.setIntPersPersonaUsuarioPk(intPersonaRegistra);
						dto = boUsuarioPerfil.grabarUsuarioPerfilPersona(dto);
					}else{
						dto = boUsuarioPerfil.modificarUsuarioPerfil(dto);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return pLista;
	}
	
	public List<UsuarioSucursal> grabarListaDinamicaUsuarioSucursal(List<UsuarioSucursal> pLista, Integer intIdPersona) throws BusinessException{
		UsuarioSucursal dto = null;
		UsuarioSucursal dtoTemp = null;
		EmpresaUsuarioId lEmpresaUsuarioId = null;
		try{
			for(int i=0; i<pLista.size(); i++){
				dto = pLista.get(i);
				if(dto.getId()==null || dto.getId().getIntPersPersonaPk() == null){
					dto.getId().setIntPersPersonaPk(intIdPersona);
					dto = boUsuarioSucursal.grabarUsuarioSucursal(dto);
				}else{
					dtoTemp = boUsuarioSucursal.getUsuarioSucursalPorPk(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntPersPersonaPk(intIdPersona);
						dto = boUsuarioSucursal.grabarUsuarioSucursal(dto);
					}else{
						dto = boUsuarioSucursal.modificarUsuarioSucursal(dto);
						if(dto.getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)==0){
							if(lEmpresaUsuarioId == null)
							lEmpresaUsuarioId = new EmpresaUsuarioId();
							lEmpresaUsuarioId.setIntPersEmpresaPk(dto.getId().getIntPersEmpresaPk());
							lEmpresaUsuarioId.setIntPersPersonaPk(dto.getId().getIntPersPersonaPk());
							eliminarUsuarioSubSucursalPorPkEmpresaUsuarioYIdSucursal(lEmpresaUsuarioId,dto.getId().getIntIdSucursal());
						}
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return pLista;
	}
	
	public List<UsuarioSubSucursal> grabarListaDinamicaUsuarioSubSucursal(List<UsuarioSubSucursal> pLista, Integer intIdPersona) throws BusinessException{
		UsuarioSubSucursal dto = null;
		UsuarioSubSucursal dtoTemp = null;
		try{
			for(int i=0; i<pLista.size(); i++){
				dto = pLista.get(i);
				if(dto.getId()==null || dto.getId().getIntPersPersonaPk() == null){
					dto.getId().setIntPersPersonaPk(intIdPersona);
					dto = boUsuarioSubSucursal.grabarUsuarioSubSucursal(dto);
				}else{
					dtoTemp = boUsuarioSubSucursal.getUsuarioSubSucursalPorPk(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntPersPersonaPk(intIdPersona);
						dto = boUsuarioSubSucursal.grabarUsuarioSubSucursal(dto);
					}else{
						dto = boUsuarioSubSucursal.modificarUsuarioSubSucursal(dto);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return pLista;
	}
	
	public Usuario getUsuarioPersonaPorIdPersona(Integer pIntIdPersona)throws BusinessException{
		PersonaFacadeRemote facade = null;
		Usuario lUsuario = null;
		EmpresaUsuario lEmpresaUsuario = null;
		Persona persona = null;
		try{
			lUsuario = boUsuario.getUsuarioPorPk(pIntIdPersona);
			if(lUsuario != null){
				if(lUsuario.getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)==0){
					lUsuario.setListaEmpresaUsuario(boEmpresaUsuario.getListaEmpresaUsuarioPorIdPersonaYFechaEliminacion(pIntIdPersona,lUsuario.getTsFechaEliminacion()));
					for(int i=0;i<lUsuario.getListaEmpresaUsuario().size();i++){
						lEmpresaUsuario = lUsuario.getListaEmpresaUsuario().get(i);
						lEmpresaUsuario.setListaUsuarioPerfil(boUsuarioPerfil.getListaUsuarioPerfilPorPkEmpresaUsuarioYFechaEliminacion(lEmpresaUsuario.getId(),lUsuario.getTsFechaEliminacion()));
						lEmpresaUsuario.setListaUsuarioSucursal(boUsuarioSucursal.getListaUsuarioSucursalPkEmpresaUsuarioYFechaEliminacion(lEmpresaUsuario.getId(),lUsuario.getTsFechaEliminacion()));
						lEmpresaUsuario.setListaUsuarioSubSucursal(boUsuarioSubSucursal.getUsuarioSubSucursalPorPkEmpresaUsuarioYFechaEliminacion(lEmpresaUsuario.getId(),lUsuario.getTsFechaEliminacion()));
					}
				}else{
					lUsuario.setListaEmpresaUsuario(boEmpresaUsuario.getListaEmpresaUsuarioPorIdPersona(pIntIdPersona));
					for(int i=0;i<lUsuario.getListaEmpresaUsuario().size();i++){
						lEmpresaUsuario = lUsuario.getListaEmpresaUsuario().get(i);
						lEmpresaUsuario.setListaUsuarioPerfil(boUsuarioPerfil.getListaUsuarioPerfilPorPkEmpresaUsuario(lEmpresaUsuario.getId()));
						lEmpresaUsuario.setListaUsuarioSucursal(boUsuarioSucursal.getListaUsuarioSucursalPorPkEmpresaUsuario(lEmpresaUsuario.getId()));
						lEmpresaUsuario.setListaUsuarioSubSucursal(boUsuarioSubSucursal.getUsuarioSubSucursalPorPkEmpresaUsuario(lEmpresaUsuario.getId()));
					}
				}
				facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				persona = facade.getPersonaPorPK(pIntIdPersona);
				if(persona != null){
					if(persona.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
						lUsuario.setPersona(facade.getPersonaNaturalPorIdPersona(pIntIdPersona));		
					}else if(persona.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_JURIDICA)==0){
						lUsuario.setPersona(facade.getPersonaJuridicaPorIdPersona(pIntIdPersona));
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lUsuario;
	}
	
	public void eliminarUsuarioPerfilPorPkEmpresaUsuario(EmpresaUsuarioId pId,Integer intIdPersonaModifica)throws BusinessException{
		UsuarioPerfil lUsuarioPerfilLoop = null;
		List<UsuarioPerfil> listaPerfil = null;
		listaPerfil = boUsuarioPerfil.getListaUsuarioPerfilPorPkEmpresaUsuario(pId);
		if(listaPerfil !=null && listaPerfil.size()>0){
			for(int j=0;j<listaPerfil.size();j++){
				lUsuarioPerfilLoop = listaPerfil.get(j);
				lUsuarioPerfilLoop.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				lUsuarioPerfilLoop.setIntPersPersonaEliminaPk(intIdPersonaModifica);
				boUsuarioPerfil.modificarUsuarioPerfil(lUsuarioPerfilLoop);
			}
		}
	}
	
	public void eliminarUsuarioSucursalPorPkEmpresaUsuario(EmpresaUsuarioId pId)throws BusinessException{
		UsuarioSucursal lUsuarioSucursalLoop = null;
		List<UsuarioSucursal> listaSucursal = null;
		listaSucursal = boUsuarioSucursal.getListaUsuarioSucursalPorPkEmpresaUsuario(pId);
		if(listaSucursal!=null && listaSucursal.size()>0){
			for(int j=0;j<listaSucursal.size();j++){
				lUsuarioSucursalLoop = listaSucursal.get(j);
				lUsuarioSucursalLoop.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boUsuarioSucursal.modificarUsuarioSucursal(lUsuarioSucursalLoop);
				eliminarUsuarioSubSucursalPorPkEmpresaUsuarioYIdSucursal(pId,lUsuarioSucursalLoop.getId().getIntIdSucursal());
			}
		}
	}
	
	public void eliminarUsuarioSubSucursalPorPkEmpresaUsuarioYIdSucursal(EmpresaUsuarioId pId,Integer pIntIdSucursal)throws BusinessException{
		UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
		List<UsuarioSubSucursal> listaSubSucursal = null;
		listaSubSucursal = boUsuarioSubSucursal.getUsuarioSubSucursalPorPkEmpresaUsuarioYIdSucursal(pId,pIntIdSucursal);
		if(listaSubSucursal!=null && listaSubSucursal.size()>0){
			for(int k=0;k<listaSubSucursal.size();k++){
				lUsuarioSubSucursalLoop = listaSubSucursal.get(k);
				lUsuarioSubSucursalLoop.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boUsuarioSubSucursal.modificarUsuarioSubSucursal(lUsuarioSubSucursalLoop);
			}
		}
	}
	
	public void eliminarIntegralUsuarioPerfilPorPkEmpresaUsuario(EmpresaUsuarioId pId,Timestamp tsFechaEliminacion,Integer pIntIdPersonaElimina)throws BusinessException{
		UsuarioPerfil lUsuarioPerfilLoop = null;
		List<UsuarioPerfil> listaPerfil = null;
		listaPerfil = boUsuarioPerfil.getListaUsuarioPerfilPorPkEmpresaUsuario(pId);
		if(listaPerfil !=null && listaPerfil.size()>0){
			for(int j=0;j<listaPerfil.size();j++){
				lUsuarioPerfilLoop = listaPerfil.get(j);
				lUsuarioPerfilLoop.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				lUsuarioPerfilLoop.setTsFechaEliminacion(tsFechaEliminacion);
				lUsuarioPerfilLoop.setIntPersPersonaEliminaPk(pIntIdPersonaElimina);
				boUsuarioPerfil.modificarUsuarioPerfil(lUsuarioPerfilLoop);
			}
		}
	}
	
	public void eliminarIntegralUsuarioSucursalPorPkEmpresaUsuario(EmpresaUsuarioId pId,Timestamp tsFechaEliminacion)throws BusinessException{
		UsuarioSucursal lUsuarioSucursalLoop = null;
		List<UsuarioSucursal> listaSucursal = null;
		listaSucursal = boUsuarioSucursal.getListaUsuarioSucursalPorPkEmpresaUsuario(pId);
		if(listaSucursal!=null && listaSucursal.size()>0){
			for(int j=0;j<listaSucursal.size();j++){
				lUsuarioSucursalLoop = listaSucursal.get(j);
				lUsuarioSucursalLoop.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				lUsuarioSucursalLoop.setTsFechaEliminacion(tsFechaEliminacion);
				boUsuarioSucursal.modificarUsuarioSucursal(lUsuarioSucursalLoop);
				eliminarIntegralUsuarioSubSucursalPorPkEmpresaUsuarioYIdSucursal(pId,lUsuarioSucursalLoop.getId().getIntIdSucursal(),tsFechaEliminacion);
			}
		}
	}
	
	public void eliminarIntegralUsuarioSubSucursalPorPkEmpresaUsuarioYIdSucursal(EmpresaUsuarioId pId,Integer pIntIdSucursal,Timestamp tsFechaEliminacion)throws BusinessException{
		UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
		List<UsuarioSubSucursal> listaSubSucursal = null;
		listaSubSucursal = boUsuarioSubSucursal.getUsuarioSubSucursalPorPkEmpresaUsuarioYIdSucursal(pId,pIntIdSucursal);
		if(listaSubSucursal!=null && listaSubSucursal.size()>0){
			for(int k=0;k<listaSubSucursal.size();k++){
				lUsuarioSubSucursalLoop = listaSubSucursal.get(k);
				lUsuarioSubSucursalLoop.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				lUsuarioSubSucursalLoop.setTsFechaEliminacion(tsFechaEliminacion);
				boUsuarioSubSucursal.modificarUsuarioSubSucursal(lUsuarioSubSucursalLoop);
			}
		}
	}
	
	public  List<UsuarioSubSucursal> getListaPorSucYSubSucursal(UsuarioSubSucursalId pId) throws BusinessException{
		
		PersonaFacadeRemote facade = null;
		
		List<UsuarioSubSucursal> listaSubSucursal = null;
		List<UsuarioSubSucursal> listaSubSucursalTemp = new ArrayList<UsuarioSubSucursal>();
		
		Persona persona = null;
	
		try{	
			listaSubSucursal = boUsuarioSubSucursal.getListaPorSucYSubSucursal(pId); 
			for (UsuarioSubSucursal usuarioSubSucursal : listaSubSucursal) {
				usuarioSubSucursal.setEmpresaUsuario(new EmpresaUsuario());
				usuarioSubSucursal.getEmpresaUsuario().setUsuario(new Usuario());
				facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				persona = facade.getPersonaPorPK(usuarioSubSucursal.getId().getIntPersPersonaPk());
				if (persona != null){
					
					if(persona.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
						persona = (facade.getPersonaNaturalPorIdPersona(usuarioSubSucursal.getId().getIntPersPersonaPk()));		
					}
				}
				
			   if (persona != null)	{
				   usuarioSubSucursal.getEmpresaUsuario().getUsuario().setPersona(persona);
				   listaSubSucursalTemp.add(usuarioSubSucursal);
			   }
				
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSubSucursalTemp;
	}
		
	
	
}

