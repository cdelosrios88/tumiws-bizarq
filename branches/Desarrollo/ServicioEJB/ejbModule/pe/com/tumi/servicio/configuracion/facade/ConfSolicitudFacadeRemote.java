package pe.com.tumi.servicio.configuracion.facade;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.servicio.configuracion.domain.ConfServCancelado;
import pe.com.tumi.servicio.configuracion.domain.ConfServCaptacion;
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;
import pe.com.tumi.servicio.configuracion.domain.ConfServCreditoEmpresa;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServGrupoCta;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServRol;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitudId;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;

@Remote
public interface ConfSolicitudFacadeRemote {
	public ConfServSolicitud grabarConfiguracion(ConfServSolicitud o)throws BusinessException;
	public ConfServSolicitud getConfiguracionPorPk(ConfServSolicitudId pId) throws BusinessException;
	public ConfServSolicitud grabarLiquidacionCuenta(ConfServSolicitud o)throws BusinessException;
	public List<ConfServSolicitud> buscarConfSolicitudRequisito(ConfServSolicitud o, Date fechaFiltroInicio, Date fechaFiltroFin,Integer tipoCuentaFiltro)throws BusinessException;
	public List<ConfServSolicitud> buscarConfSolicitudAutorizacion(ConfServSolicitud o, Date fechaFiltroInicio, Date fechaFiltroFin,Integer tipoCuentaFiltro)throws BusinessException;
	public ConfServSolicitud modificarConfiguracion(ConfServSolicitud o)throws BusinessException;
	public List<ConfServDetalle> getListaConfServDetallePorCabecera(ConfServSolicitud o) throws BusinessException;
	public ConfServDetalle getConfServDetallePorPk(ConfServDetalleId o)throws BusinessException;
	public List<ConfServRol> getListaConfServRolPorCabecera(ConfServSolicitud o)throws BusinessException;
	public List<ConfServGrupoCta> getListaConfServGrupoCtaPorCabecera(ConfServSolicitud o)throws BusinessException;
	public List<ConfServEstructuraDetalle> getListaConfServEstructuraPorCabecera(ConfServSolicitud o)throws BusinessException;
	public ConfServSolicitud modificarLiquidacionCuenta(ConfServSolicitud o)throws BusinessException;
	public ConfServSolicitud grabarCredito(ConfServSolicitud o)throws BusinessException;
	public ConfServSolicitud modificarCredito(ConfServSolicitud o)throws BusinessException;
	public List<ConfServCredito> getListaConfServCreditoPorCabecera(ConfServSolicitud o)throws BusinessException;
	public ConfServSolicitud grabarCaptacion(ConfServSolicitud o)throws BusinessException;
	public ConfServSolicitud modificarCaptacion(ConfServSolicitud o)throws BusinessException;
	public ConfServSolicitud grabarAutorizacion(ConfServSolicitud o)throws BusinessException;
	public List<ConfServCreditoEmpresa> getListaConfServCreditoEmpresaPorCabecera(ConfServSolicitud o)throws BusinessException;
	public List<ConfServCancelado> getListaConfServCanceladoPorCabecera(ConfServSolicitud o)throws BusinessException;
	public List<ConfServPerfil> getListaConfServPerfilPorCabecera(ConfServSolicitud o)throws BusinessException;
	public List<ConfServUsuario> getListaConfServUsuarioPorCabecera(ConfServSolicitud o)throws BusinessException;
	public ConfServSolicitud modificarAutorizacion(ConfServSolicitud o)throws BusinessException;
	public List<ConfServCaptacion> getListaConfServCaptacionPorCabecera(ConfServSolicitud o)throws BusinessException;
	public List<ConfServSolicitud> buscarConfSolicitudRequisitoOptimizado(ConfServSolicitud o, Integer tipoCuentaFiltro, Credito credito)throws BusinessException;
}
