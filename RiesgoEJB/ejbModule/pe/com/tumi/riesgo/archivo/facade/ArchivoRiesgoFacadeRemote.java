package pe.com.tumi.riesgo.archivo.facade;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.riesgo.archivo.domain.ConfDetalle;
import pe.com.tumi.riesgo.archivo.domain.ConfEstructura;
import pe.com.tumi.riesgo.archivo.domain.ConfEstructuraId;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;
import pe.com.tumi.riesgo.archivo.domain.ConfiguracionId;
import pe.com.tumi.riesgo.archivo.domain.Nombre;

@Remote
public interface ArchivoRiesgoFacadeRemote {
	public Configuracion grabarConfiguracion(Configuracion o, List<ConfDetalle> listaConfDetalle, List<Nombre> listaNombre)throws BusinessException;
	public Configuracion getConfiguracionPorPk(ConfiguracionId pId) throws BusinessException;
	public List<Configuracion> buscarConfiguracion(Configuracion c, Timestamp busquedaInicio, Timestamp busquedaFin) throws BusinessException;
	public List<Configuracion> buscarConfiguracionConEstructura(Configuracion c, Timestamp busquedaInicio, Timestamp busquedaFin)throws BusinessException;
	public List<Configuracion> buscarConfiguracionSinEstructura(Configuracion c, Timestamp busquedaInicio, Timestamp busquedaFin)throws BusinessException;
	public Configuracion eliminarConfiguracion(Configuracion o)throws BusinessException;
	public List<ConfDetalle> getConfDetallePorIntItemConfiguracion(Integer intItemConfiguracion)throws BusinessException;
	public List<Nombre> getNombrePorIntItemConfiguracion(Integer intItemConfiguracion)throws BusinessException;
	public Configuracion modificarConfiguracion(Configuracion o, List<ConfDetalle> listaConfDetalle, List<Nombre> listaNombre)throws BusinessException;
	public Configuracion grabarConfiguracion(Configuracion o, List<ConfDetalle> listaConfDetalle, List<Nombre> listaNombre, ConfEstructura confEstructura)throws BusinessException;
	public Configuracion modificarConfiguracion(Configuracion o, List<ConfDetalle> listaConfDetalle, List<Nombre> listaNombre, ConfEstructura confEstructura)throws BusinessException;
	public ConfEstructura getConfEstructuraPorPK(ConfEstructuraId confEstructuraId) throws BusinessException;
	public List<ConfEstructura> getListaModTipoSoEmp(Integer intModalidad, Integer  intTipoSocio, 
			Integer intEmpresa, Integer intNivel, Integer intCodigo, Integer intFormato) throws BusinessException;
}
