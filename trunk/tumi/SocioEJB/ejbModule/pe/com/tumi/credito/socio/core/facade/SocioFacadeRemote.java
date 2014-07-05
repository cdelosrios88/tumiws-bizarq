package pe.com.tumi.credito.socio.core.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraDetalleComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.core.domain.PersonaRol;

@Remote
public interface SocioFacadeRemote {
	public List<SocioComp> getListaSocioComp(SocioComp o) throws BusinessException;
	public SocioComp grabarSocioNatural(SocioComp o, List<PersonaRol> listaRol) throws BusinessException;
	public SocioComp modificarSocioNatural(SocioComp o, List<PersonaRol> listaRol) throws BusinessException;
	public SocioComp getSocioNatural(SocioPK o) throws BusinessException;
	public SocioComp getSocioNatuPorDocIdentidadYIdEmpresaYTipoVinculo(Integer intTipoDoc,String strNumIdentidad,Integer pIntEmpresa,Integer pIntTipoVinculo) throws BusinessException;
	public SocioComp getSocioNatuPorDocIdentidadYIdEmpresa(Integer intTipoDoc,String strNumIdentidad,Integer pIntEmpresa) throws BusinessException;
	public SocioEstructura getSocioEstructuraPorPk(SocioEstructuraPK pk) throws BusinessException;
	public SocioEstructura getSocioEstructuraDeOrigenPorPkSocio(SocioPK pkSocio) throws BusinessException;
	public SocioEstructura getSocioEstructuraPorPkSocioYPkEstructuraYTipoEstructura(SocioPK pkSocio,EstructuraId pkEstructura,Integer intTipoEstructura) throws BusinessException;
	public List<Socio> getListaSocioPorIdEstructuraYTipoSocio(EstructuraId pk,Integer intTipoSocio) throws BusinessException;
	public List<Socio> getListaSocioDeTitularPorIdEstructuraYTipoSocio(EstructuraId pk,Integer intTipoSocio) throws BusinessException;
	public List<Socio> getListaSocioPorEmpresaYTipoIntegranteYINCuenta(Integer intEmpresa,Integer intTipoIntegrante,String strINCuenta) throws BusinessException;
	public List<SocioEstructura> getListaSocioEstrucuraPorIdPersona(Integer intIdPersona,Integer intIdEmpresa) throws BusinessException;

	public List<SocioComp> getListaBuscarSocioConCuentaVigente(Integer intEmpresa,Integer intTipoRol,String nombre,String dni) throws BusinessException;
	public SocioEstructura modificarSocioEstructura(SocioEstructura o) throws BusinessException ;
	public SocioEstructura grabarSocioEstructura(SocioEstructura o) throws BusinessException;

	public List<CuentaIntegrante> getListaCuentaIntegrantePorCuenta(Integer intEmpresa,Integer intCuenta) throws BusinessException;
	public SocioEstructura getSocioEstructuraPorPkSocioYPkEstructura(SocioPK pkSocio,EstructuraId pkEstructura) throws BusinessException;

	public Socio getSocioPorPK(SocioPK o) throws BusinessException;

	public SocioComp getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(Integer intTipoDoc,String strNumIdentidad,Integer pIntEmpresa, Cuenta cuentaExpediente) throws BusinessException;
	public List<SocioEstructura> getListaSocioEstructuraPorSocioPKyActivo(Integer intPersona,
			Integer intEmpresa, Integer intEstado) throws BusinessException;

	public SocioEstructura getSocioEstructuraPorPKTipoSocio(SocioEstructuraPK pk, Integer intTipoSocio) throws BusinessException;

	/*public List<SocioEstructura> getListaSocEstPorSocioPKActivoTipoSocio(Integer intPersona,
			Integer intEmpresa, Integer intEstado, Integer intTipoSocio) throws BusinessException;*/

	public List<SocioEstructura> getListaXSocioPKActivoTipoSocio(Integer intPersona,
			Integer intEmpresa, Integer intEstado, Integer intTipoSocio,Integer intNivel, Integer intCodigo)
			throws BusinessException;
	public SocioComp getSocioCompXDocEmpCuenta(Integer intTipoDoc,String strNumIdentidad,Integer pIntEmpresa, Integer intCuenta) throws BusinessException;
	
	public List<SocioEstructura> getListaXNivelCodigoNoCas(SocioEstructura o) throws BusinessException;
	public List<SocioEstructura> getListaXNivelCodigosoloCas(SocioEstructura o) throws BusinessException;
	public List<Socio> getLPorIdEstructuraTSMAE(SocioEstructura o) throws BusinessException;
	
	public List<SocioComp> getListaSocioPorFiltrosBusq (SocioComp socioCompBusq)throws BusinessException;
	
	public List<Socio> getListaSocioEnEfectuado(Socio o) throws BusinessException;
	
	public List<SocioEstructura> getListaSocioEstructuraAgregarSocioEfectuado(Integer intPersona,
			Integer intEmpresa, Integer intEstado, Integer intTipoSocio) throws BusinessException;
	
	public Socio modificarSocio(Socio o) throws BusinessException; 
	public Socio grabarSocio(Socio o) throws BusinessException;
	
	public Socio getSocioPorNombres(String nombres) throws BusinessException;
	
	public List<SocioEstructura> getListaSocioEstructuraPorPkSocioYPkEstructura(SocioPK pkSocio,
																		EstructuraId pkEstructura)
																		throws BusinessException;
	public List<SocioEstructura> getListaXAdminySubAdminHABERINCENT(SocioEstructura o) throws BusinessException;
	
	public List<SocioEstructura> getListaXNivelCodigosoloHaberIncentivo(SocioEstructura o) throws BusinessException;
	
	public List<SocioEstructura> getListaPorCodPersonaOfEnviado(SocioEstructura o) throws BusinessException;
	
	public List<SocioEstructura> getListaXAdminySubAdminSOLOCAS(SocioEstructura o) throws BusinessException;
}