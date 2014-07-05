package pe.com.tumi.credito.socio.estructura.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.PadronId;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPago;
import pe.com.tumi.credito.socio.estructura.domain.Terceros;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.core.domain.PersonaRol;

@Remote
public interface EstructuraFacadeRemote {
	
	public List<EstructuraComp> getListaEstructuraComp(EstructuraComp o) throws BusinessException;
	public List<EstructuraComp> getListaFiltraEstructuraComp(EstructuraComp o) throws BusinessException;
	public Estructura grabarEstructura(Estructura o) throws BusinessException;
	public Estructura modificarEstructura(Estructura o) throws BusinessException;
	public Estructura getEstructuraPorPk(EstructuraId o) throws BusinessException;
	public List<Estructura> getListaEstructuraPorNivelYCodigoRel(Integer intNivel, Integer intCodigoRel)throws BusinessException;
	public List<Estructura> getListaEstructuraPorIdEmpresaYIdNivelYIdCasoIdSucursal(Integer intIdEmpresa,Integer intIdNivel,Integer intIdCaso,Integer intIdSucursal) throws BusinessException;
	public List<Estructura> getListaEstructuraPorIdEmpresaYIdCodigoYIdNivelYIdCasoIdSucursal(Integer intIdEmpresa,Integer intIdCodigo,Integer intIdNivel,Integer intIdCaso, Integer intIdSucursal) throws BusinessException;
	public Estructura eliminarEstructura(Estructura o) throws BusinessException;
	public Estructura grabarEstructuraYPersonaRol(Estructura o, PersonaRol personaRol) throws BusinessException;
	public Estructura getEstructuraPorIdEmpresaYIdPersona(Integer intIdEmpresa,Integer intIdPersona) throws BusinessException;
	public Padron getPadronPorPK(PadronId o)throws BusinessException;
	public List<Padron> getPadronBusqueda(PadronId o)throws BusinessException;
	public AdminPadron grabarAdminPadron(AdminPadron o) throws BusinessException;
	public Descuento grabarDescuentoTerceros(Descuento o) throws BusinessException;
	public boolean grabarDescuentos(AdminPadron o, String s,List<String>l) throws BusinessException;
	public boolean grabarPadrones(AdminPadron o, String s, List<String>l) throws BusinessException;
	public List<AdminPadron> getAdminPadronBusqueda(AdminPadron o) throws BusinessException;
	public boolean grabarSolicitudPago(SolicitudPago s, List<AdminPadron> l) throws BusinessException;
	public List<AdminPadron> getAdminPadronSinSolicitud(AdminPadron adminPadron) throws BusinessException;
	public List<Estructura> getListaEstructuraPorNivel(Integer nivel) throws BusinessException;
	public List<AdminPadron> getSolicitudBusqueda(AdminPadron o) throws BusinessException;
	public AdminPadron modificarAdminPadron(AdminPadron o) throws BusinessException;
	public SolicitudPago modificarSolicitud(SolicitudPago o) throws BusinessException;
	public SolicitudPago getSolicitudPorPk(Integer o) throws BusinessException;
	public List<AdminPadron> actualizarListaAdminPadron(List<AdminPadron> l) throws BusinessException;
	public List<EstructuraDetalle> getListaEstructuraDetallePorEstructuraPK(EstructuraId id) throws BusinessException;
	public List<EstructuraDetalle> getListaEstructuraDetallePorIdEmpresaYIdNivelYIdSucursal(Integer intIdEmpresa,Integer intIdNivel,Integer intIdSucursal) throws BusinessException;
	public List<EstructuraDetalle> getConveEstrucDetAdministra(EstructuraComp o) throws BusinessException;
	public List<EstructuraDetalle> getConveEstrucDetPlanilla(EstructuraComp o) throws BusinessException;
	public List<EstructuraComp> getListaEstructuraCompPorTipoConvenio(Integer intIdNivel, Integer intTipoConvenio, String strRazonSocial, String strNroRuc)throws BusinessException;
	public EstructuraDetalle getEstructuraDetallePorPk(EstructuraDetalleId o) throws BusinessException;
	public List<ConvenioEstructuraDetalleComp> getListaConvenioEstructuraDetPorEstructuraDet(ConvenioEstructuraDetalleComp pId) throws BusinessException;
	public List<Estructura> getListaEstructuraPorNivelYCodigo(Integer intNivel, Integer intCodigoRel)throws BusinessException;
	public List<EstructuraComp> getListaEstructuraCompConSucursal(EstructuraComp o) throws BusinessException;
	public List<Terceros> getListaFilaTercerosPorDNI(String strDocIdentidad) throws BusinessException;
	public List<Terceros> getListaColumnaTercerosPorDNI(String strDocIdentidad) throws BusinessException;
	public SocioComp getSocioNatuPorLibElectoral(String strLibEle) throws BusinessException;
	public EstructuraDetalle getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(EstructuraId o,Integer intCaso,Integer intParaTipoSocioCod,Integer intParaModalidadCod)throws BusinessException;
	public EstructuraDetalle getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(EstructuraDetalleId o,Integer intParaTipoSocioCod,Integer intParaModalidadCod)throws BusinessException;
	public List<EstructuraDetalle> getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(EstructuraDetalleId o,Integer intParaTipoSocioCod,Integer intParaModalidadCod)throws BusinessException;
	public ConvenioEstructuraDetalle getConvenioEstructuraDetallePorPkEstructuraDetalle(EstructuraDetalleId o)throws BusinessException;
	public List<EstructuraDetalle> getListaEstructuraDetallePorCodExterno(String strCodExterno)throws BusinessException;
	public EstructuraDetalle getEstructuraDetallePorCodSocioYTipoSocYModalidad(Integer intCodSocio, Integer intParaTipoSocioCod,Integer intParaModalidadCod)throws BusinessException;
	public List<ConvenioEstructuraDetalleComp> getListaConvenioEstructuraDetallePorEstructuraDetCompleto(EstructuraDetalleId o)throws BusinessException;
	public List<Estructura> getListaEstructuraPorIdEmpresaYIdCasoIdSucursal(Integer intIdEmpresa,Integer intIdCaso,Integer intIdSucursal) throws BusinessException;
	
	public List<AdminPadron> getTipSocioModPeriodoMes(AdminPadron o) throws BusinessException;
	
	public Padron getPadronSOLOPorLibElectoral(String strLibEle, Integer item) throws BusinessException;
	
	public Terceros getPorItemDNI(Terceros o) throws BusinessException;
	public AdminPadron getMaximoAdminPadronPorAdminPadron ( AdminPadron adminPadronFiltro) throws BusinessException;
	public List<Descuento> getListaPorAdminPadron ( AdminPadron administraPadron, String strDni) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 30.09.2013 
	public Estructura getEstructuraPorPK(EstructuraId pPK) throws BusinessException;
	public EstructuraDetalle getEstructuraDetallePorSucuSubsucuYCodigo(SocioEstructura o) throws BusinessException;
	//autor rVillarreal
	public List<Estructura> getListaEstructuraPorNivelCodigo(Integer nivel, Integer codigo) throws BusinessException;
	//jchavez 18.06.2014
	public List<EstructuraDetalle> getListaEstructuraDetalleIngresos(Integer intIdSucursal, Integer intIdSubSucursal) throws BusinessException;
}
