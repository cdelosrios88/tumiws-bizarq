package pe.com.tumi.persona.core.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaFin;
import pe.com.tumi.persona.core.domain.CuentaBancariaFinId;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

@Local
public interface PersonaFacadeLocal {
	
	public Persona modificarPersona(Persona persona) throws BusinessException;
	public Persona getPersonaPorPK(Integer pIntPK) throws BusinessException;
	public Natural getNaturalPorPK(Integer pIntPK) throws BusinessException;
	public Persona getPersonaNaturalPorIdPersona(Integer pIntIdPersona) throws BusinessException;
	public Persona getPersonaNaturalPorPersonaEmpresaPKYTipoVinculo(PersonaEmpresaPK pk, Integer intTipoVinculo) throws BusinessException;
	public Persona grabarPersonaNaturalTotal(Persona persona) throws BusinessException;
	public Persona grabarPersonaNatural(Persona o)throws BusinessException;
	public Persona modificarPersonaNaturalTotal(Persona persona) throws BusinessException;
	public Persona modificarPersonaNatural(Persona o) throws BusinessException;
	//public void eliminarPersonaNaturalPorIdPersona(Integer pIntIdPersona)throws BusinessException;
	public List<Natural> getListaNaturalPorInPk(String pStrPK) throws BusinessException;
	public List<Persona> getFullListPerNaturalBusqueda(Persona o) throws BusinessException;
	public List<Persona> getListPerNaturalBusqueda(Persona o) throws BusinessException;
	public Empresa grabarEmpresa(Empresa o) throws BusinessException;
	public Empresa eliminarEmpresa(Empresa o) throws BusinessException;
	public Empresa getEmpresaPorPk(Integer intPk) throws BusinessException;
	public List<Empresa> getListaEmpresa(Empresa o) throws BusinessException;
	public Persona getPersonaJuridicaPorIdPersonaYIdEmpresa(Integer pIntIdPersona, Integer intIdEmpresaSistema) throws BusinessException;
	public Persona getPersonaJuridicaPorIdPersona(Integer pIntIdPersona) throws BusinessException;
	public Persona grabarPersonaJuridica(Persona juri) throws BusinessException;
	public Persona modificarPersonaJuridica(Persona juri) throws BusinessException;
	public Persona grabarPersonaJuridicaTotal(Persona juri) throws BusinessException;
	public Persona modificarPersonaJuridicaTotal(Persona juri) throws BusinessException;
	//public void eliminarPersonaJuridicaPorIdPersona(Integer pIntIdPersona)throws BusinessException;
	public Juridica modificarJuridica(Juridica juridica) throws BusinessException;
	public Empresa getEmpresaJuridicaPorPK(Integer pIntPK) throws BusinessException;
	public Juridica getJuridicaPorPK(Integer pIntPK) throws BusinessException;
	public Persona getPersonaJuridicaPorRuc(String strRuc) throws BusinessException;
	public List<Persona> getListPerJuridicaBusqueda(Persona o) throws BusinessException;
	public List<Persona> getBusquedaPerJuridicaSinSucursales(Persona o) throws BusinessException;
	public Persona getPersonaJuridicaYListaPersonaPorRucYIdEmpresa(String strRuc,Integer intIdEmpresaSistema) throws BusinessException;
	public Persona getPersonaJuridicaYListaPersonaPorRucYIdEmpresa2(String strRuc,Integer intIdEmpresaSistema) throws BusinessException;
	public List<Persona> getListaPersonaNaturalPorPKPersonaEmpresaYTipoVinculo(Integer intIdPersona,Integer intTipoVinculo,Integer intIdEmpresaSistema) throws BusinessException;
	public List<Juridica> getListaJuridicaPorInPk(String pStrPK) throws BusinessException;
	public List<Juridica> getListaJuridicaPorInPkLikeRazon(String pCsvIdPersona,String pStrRazonSocial) throws BusinessException;
	public List<Juridica> getListaJuridicaDeEmpresa() throws BusinessException;
	public CuentaBancaria getCuentaBancariaPorPK(CuentaBancariaPK id) throws BusinessException;
	public PersonaRol grabarPersonaRol(PersonaRol personaRol) throws BusinessException;
	public PersonaRol modificarPersonaRolPorPerEmpYRol(PersonaRol personaRol) throws BusinessException;
	public List<PersonaRol> grabarListaDinamicaPersonaRol(List<PersonaRol> lista, Integer intIdPersona) throws BusinessException;
	public Persona getPersonaNaturalPorDocIdentidadYIdEmpresa(Integer intTipoIdentidad ,String strNroIdentidad, Integer intIdEmpresaSistema) throws BusinessException;
	public Persona getPersonaNaturalPorDocIdentidadYIdEmpresaYTipoVinculo(Integer intTipoIdentidad ,String strNroIdentidad, Integer intIdEmpresaSistema, Integer intTipoVinculo) throws BusinessException;
	public Vinculo getVinculoPorPk(Integer intItemVinculo)throws BusinessException;
	public Vinculo getVinculoPersona(Vinculo o)throws BusinessException;
	public Vinculo grabarVinculoPersona(Vinculo o) throws BusinessException;
	public Vinculo modificarVinculoPersona(Vinculo o) throws BusinessException;
	public CuentaBancaria grabarCuentaBancaria(CuentaBancaria o) throws BusinessException;
	public CuentaBancaria modificarCuentaBancaria(CuentaBancaria o) throws BusinessException;
	
	public List<Juridica> getListaJuridicaPorRazonSocial(String strRazonSocial) throws BusinessException;
	public List<Juridica> getListaJuridicaPorNombreComercial(String strNombreComercial) throws BusinessException;
	public Persona getPersonaPorRuc(String strRuc) throws BusinessException;
	public Persona getPersona(String strRuc) throws BusinessException;
	public List<Natural> getListaNaturalPorBusqueda(Natural o) throws BusinessException;
	public List<PersonaEmpresa> getListaPersonaEmpresaPorIdPersona(Integer pId) throws BusinessException;
	public Natural grabarNatural(Natural o) throws BusinessException;
	public Documento grabarDocumento(Documento o) throws BusinessException;
	public Natural modificarNatural(Natural o) throws BusinessException;
	public List<PersonaRol> getListaPersonaRolPorPKPersonaEmpresa(PersonaEmpresaPK pk) throws BusinessException;
	public CuentaBancariaFin grabarCuentaBancariaFin(CuentaBancariaFin o) throws BusinessException;
	public List<CuentaBancariaFin> getListaCuentaBancariaFinPorCuentaBancaria(CuentaBancaria o) throws BusinessException;
	public void eliminarCuentaBancariaFin(CuentaBancariaFinId o) throws BusinessException;
	public List<CuentaBancaria> getListaCuentaBancariaPorStrNumero(String strNroCuentaBancaria) throws BusinessException;
	public Vinculo getVinculoPorId(Integer intIdVinculo) throws BusinessException;
	
	public List<Persona> getListaBuscarPersonaNatural(Integer pIntIdEmpresa,String pStrNombres,Integer pIntTipoIdentidadCod, String pStrNumeroIdentidad,Integer pIntParaRolPk) throws BusinessException;
	public List<Vinculo> getVinculoPorPk(PersonaEmpresaPK pPK)throws BusinessException;
	public Persona devolverPersonaCargada(Integer intIdPersona) throws BusinessException;
	public void agregarNombreCompleto(Persona persona)throws BusinessException;
	public void agregarDocumentoDNI(Persona persona)throws BusinessException;
	
	public Persona getPersonaActiva(Integer intIdPersona) throws BusinessException;
	public List<Persona> buscarListaPersonaParaFiltro(Integer intTipoBusqueda, String strFiltro) throws BusinessException;
	public Persona getPersonaDetalladaPorIdPersonaYEmpresa(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
	public Juridica getJuridicaDetalladaPorIdPersona(Integer intIdPersona) throws BusinessException;
	public Natural getNaturalDetalladaPorIdPersona(Integer intIdPersona) throws BusinessException;
	
	// CGD-14.06.2013
	public Natural getSoloPersonaNaturalPorIdPersona(Integer intIdPersona) throws BusinessException;
	public Juridica getSoloPersonaJuridicaPorPersona(Integer intIdPersona) throws BusinessException;
	public Persona getSoloPersonaPorPK(Integer pIntPK) throws BusinessException;
	public List<Documento> getLstDocumentoPorPKPersona(Integer pIntPK) throws BusinessException;
	
	//FYC
	public List<Juridica> listaJuridicaWithFile(String strProg,
			  									String strCodEst) throws BusinessException;
	//FYC
	public List<PersonaRol> listaPersonaRolOfCobranza(PersonaEmpresaPK pPK) throws BusinessException;
}