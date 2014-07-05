package pe.com.tumi.seguridad.empresa.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.AreaCodigo;
import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.empresa.domain.composite.AreaComp;
import pe.com.tumi.empresa.domain.composite.SucursalComp;
import pe.com.tumi.empresa.domain.composite.ZonalComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;

@Remote
public interface EmpresaFacadeRemote {

	public List<Empresa> getListaEmpresa(Empresa o) throws BusinessException;
	public EmpresaUsuario getEmpresaUsuarioPorPk(EmpresaUsuarioId pPk) throws BusinessException;
	public List<EmpresaUsuario> getListaEmpresaUsuarioPorIdPersona(Integer pPk) throws BusinessException;
	public Sucursal getSucursalPorPK(Sucursal o) throws BusinessException;
	public Subsucursal getSubSucursalPorPk(SubSucursalPK o) throws BusinessException;
	public Sucursal getSucursalPorIdPersona(Integer pId) throws BusinessException;
	public Sucursal getPorPkYIdTipoSucursal(Integer idSucursal, Integer idTipoSucursal) throws BusinessException;
	public Sucursal getSucursalPorIdSucursal(Integer pId) throws BusinessException;
	public Sucursal grabarSucursal(Sucursal o) throws BusinessException;
	public Sucursal modificarSucursal(Sucursal o) throws BusinessException;
	public Sucursal eliminarSucursal(Sucursal o) throws BusinessException;
	public List<SucursalComp> getListaSucursalCompDeBusquedaSucursal(Sucursal o) throws BusinessException;
	public Integer getCantidadSucursalPorPkZonal(Integer pkZonal) throws BusinessException;
	public Subsucursal getSubSucursalPorIdSubSucursal(Integer pId) throws BusinessException;
	public List<Subsucursal> getListaSubSucursalPorIdSucursal(Integer pId) throws BusinessException;
	public List<Subsucursal> getListaSubSucursalPorIdSucursalYestado(Integer pId,Integer intEstado) throws BusinessException;
	public List<Subsucursal> getListaSubSucursalPorPkEmpresaUsuarioYIdSucursalYEstado(EmpresaUsuarioId pId,Integer intIdSucursal,Integer intEstado) throws BusinessException;
	
	public List<Sucursal> getListaSucursalPorPkZonal(Integer pIdZonal) throws BusinessException;
	public List<Sucursal> getListaSucursalPorPkEmpresa(Integer pIntPK) throws BusinessException;
	public List<Sucursal> getListaSucursalPorPkEmpresaUsuario(EmpresaUsuarioId pPk) throws BusinessException;
	public List<Sucursal> getListaSucursalPorPkEmpresaUsuarioYEstado(EmpresaUsuarioId pPk,Integer intEstado) throws BusinessException;
	public List<Sucursal> getListaSucursalSinZonalPorPkEmpresa(Integer pIntPK) throws BusinessException;
	public List<Sucursal> getListaSucursalZonalPorPkEmpresa(Integer pId) throws BusinessException;
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYTipo(Integer pIntPK,Integer pIntTipo) throws BusinessException;
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYTipoDeAne(Integer pIntPK,Integer pIntTipo) throws BusinessException;
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYTipoDeLib(Integer pIntPK,Integer pIntTipo) throws BusinessException;
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYIdZonalYTipo(Integer intIdEmpresa,Integer intIdZonal,Integer pIntTipo) throws BusinessException;
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYIdZonalYTipoDeAne(Integer intIdEmpresa,Integer intIdZonal,Integer pIntTipo) throws BusinessException;
	public List<Sucursal> getListaSucursalZonalPorPkEmpresaYIdZonalYTipoDeLib(Integer pIntPK,Integer intIdZonal,Integer pIntTipo) throws BusinessException;
	public List<Sucursal> getListaSucursalPorEmpresaYTipoSucursal(Integer pIntPK,Integer pTipo) throws BusinessException;
	public List<Sucursal> getListaSucursalPorEmpresaYTodoTipoSucursal(Integer pIntPK) throws BusinessException;
	public List<ZonalComp> getListaZonalCompDeBusquedaZonal(Zonal o) throws BusinessException;
	public Zonal grabarZonalYListaSucursal(Zonal zonal,List<Sucursal> listaSucursal) throws BusinessException;
	public Zonal modificarZonalYListaSucursal(Zonal zonal,List<Sucursal> listaSucursal) throws BusinessException;
	public Zonal eliminarZonalPorIdZonal(Integer pIntIdZonal) throws BusinessException;
	public Zonal getZonalSucursalPorIdZonal(Integer pId) throws BusinessException;
	public List<Natural> getListaNaturalDeUsuarioPorIdEmpresa(Integer pIdEmpresa) throws BusinessException;

	public List<AreaComp> getListaArea(Area busqArea) throws BusinessException;
	public Area grabarArea(Area o) throws BusinessException;
	public Area modificarArea(Area o) throws BusinessException;
	public Area getAreaPorPK(Area o) throws BusinessException;
	public Area eliminarArea(Area o) throws BusinessException;	
	public List<Area> getListaAreaPorSucursal(Sucursal o) throws BusinessException;
	public boolean validarTotalSucursal(Integer intTipoSucursalValidar, Integer intTotalSucursalReferencia) throws BusinessException;
	public Sucursal getSucursalPorId(Integer intIdSucursal) throws BusinessException;
}
