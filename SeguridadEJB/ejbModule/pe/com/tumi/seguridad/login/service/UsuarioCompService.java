package pe.com.tumi.seguridad.login.service;

import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.bo.SubSucursalBO;
import pe.com.tumi.seguridad.empresa.bo.SucursalBO;
import pe.com.tumi.seguridad.login.bo.PerfilBO;
import pe.com.tumi.seguridad.login.bo.UsuarioCompBO;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;

public class UsuarioCompService {

	private UsuarioCompBO boUsuarioComp = (UsuarioCompBO)TumiFactory.get(UsuarioCompBO.class);
	private PerfilBO boPerfil = (PerfilBO)TumiFactory.get(PerfilBO.class);
	private SucursalBO boSucursal = (SucursalBO)TumiFactory.get(SucursalBO.class);
	private SubSucursalBO boSubSucursal = (SubSucursalBO)TumiFactory.get(SubSucursalBO.class);
	
	public List<UsuarioComp> getListaUsuarioCompDeBusqueda(UsuarioComp pUsuario) throws BusinessException{
		List<UsuarioComp> lista = null;
		List<Perfil> listaPerfil = null;
		List<Sucursal> listaSucursal = null;
		List<Subsucursal> listaSubSucursal = null;
		UsuarioComp comp = null;
		Perfil perfil = null;
		Sucursal sucursal = null;
		Subsucursal subSucursal = null;
		PersonaFacadeRemote facade = null;
		Persona persona = null;
		Juridica juridica = null;
		Natural natural = null;
		StringBuilder sbSucursal = null;
		try{
			facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			lista = boUsuarioComp.getListaDeBusqueda(pUsuario);
			sbSucursal = new StringBuilder();
			for(int i=0;i<lista.size();i++){
				comp = lista.get(i);
				persona = facade.getPersonaPorPK(comp.getUsuario().getIntPersPersonaPk());
				if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
					natural = facade.getNaturalPorPK(comp.getUsuario().getIntPersPersonaPk()); 
					persona.setNatural(natural);
				} else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
					juridica = facade.getJuridicaPorPK(comp.getUsuario().getIntPersPersonaPk()); 
					persona.setJuridica(juridica);
				}
				comp.getUsuario().setPersona(persona);
				juridica = facade.getJuridicaPorPK(comp.getEmpresaUsuario().getId().getIntPersEmpresaPk());
				comp.getEmpresaUsuario().setJuridica(juridica);
				
				listaPerfil = boPerfil.getListaPerfilPorPkEmpresaUsuario(comp.getEmpresaUsuario().getId());
				comp.setListaPerfil(listaPerfil);
				for(int j=0;j<listaPerfil.size();j++){
					perfil = listaPerfil.get(j);
					if(comp.getStrPerfil() == null){
						comp.setStrPerfil(perfil.getStrDescripcion());
					}else{
						comp.setStrPerfil(comp.getStrPerfil()+"/"+perfil.getStrDescripcion());
					}
				}
				listaSucursal = boSucursal.getListaSucursalPorPkEmpresaUsuario(comp.getEmpresaUsuario().getId());
				for(int j=0;j<listaSucursal.size();j++){
					sucursal = listaSucursal.get(j);
					juridica = facade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
					sucursal.setJuridica(juridica);
				}
				listaSubSucursal = boSubSucursal.getListaSubSucursalPorPkEmpresaUsuario(comp.getEmpresaUsuario().getId());
				sbSucursal.delete(0, sbSucursal.length());
				for(int j=0;j<listaSucursal.size();j++){
					sucursal = listaSucursal.get(j);
					sbSucursal.append(sucursal.getJuridica().getStrRazonSocial());
					for(int k=0;k<listaSubSucursal.size();k++){
						subSucursal = listaSubSucursal.get(k);
						if(sucursal.getId().getIntPersEmpresaPk() == subSucursal.getId().getIntPersEmpresaPk() && 
						   sucursal.getId().getIntIdSucursal() == subSucursal.getId().getIntIdSucursal()){
							sbSucursal.append("-"+subSucursal.getStrDescripcion());
						}
					}
					sbSucursal.append("/");
				}	
				comp.setStrSucursal(sbSucursal.toString());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}

