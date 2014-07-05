package pe.com.tumi.riesgo.archivo.bo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.archivo.dao.ConfiguracionDao;
import pe.com.tumi.riesgo.archivo.dao.impl.ConfiguracionDaoIbatis;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;
import pe.com.tumi.riesgo.archivo.domain.ConfiguracionId;

public class ConfiguracionBO {
	
	private ConfiguracionDao dao = (ConfiguracionDao)TumiFactory.get(ConfiguracionDaoIbatis.class);
	
	public Configuracion grabar(Configuracion o) throws BusinessException{
		Configuracion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Configuracion modificar(Configuracion o) throws BusinessException{
		Configuracion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Configuracion getPorPk(ConfiguracionId pId) throws BusinessException{
		Configuracion domain = null;
		List<Configuracion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemConfiguracion", pId.getIntItemConfiguracion());
			lista = dao.getListaPorPk(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<Configuracion> buscar(Configuracion c, Timestamp busquedaInicio, Timestamp busquedaFin) throws BusinessException{
		List<Configuracion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemConfiguracion", c.getId().getIntItemConfiguracion());
			mapa.put("intParaEstadoCod", c.getIntParaEstadoCod());
			mapa.put("tsBusquedaInicio", busquedaInicio);
			mapa.put("tsBusquedaFin", busquedaFin);
			lista = dao.getListaPorBusqueda(mapa);
				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Configuracion> buscarConEstructura(Configuracion c, Timestamp busquedaInicio, Timestamp busquedaFin) throws BusinessException{
		List<Configuracion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemConfiguracion", c.getId().getIntItemConfiguracion());
			mapa.put("intParaEstadoCod", c.getIntParaEstadoCod());
			mapa.put("tsBusquedaInicio", busquedaInicio);
			mapa.put("tsBusquedaFin", busquedaFin);
			
			mapa.put("intSociNivelPk", c.getConfEstructura().getIntSociNivelPk());
			mapa.put("intParaModalidadCod", c.getConfEstructura().getIntParaModalidadCod());
			mapa.put("intParaTipoSocioCod", c.getConfEstructura().getIntParaTipoSocioCod());
			
			lista = dao.getListaPorBusquedaConEstructura(mapa);
				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Configuracion> buscarSinEstructura(Configuracion c, Timestamp busquedaInicio, Timestamp busquedaFin) throws BusinessException{
		List<Configuracion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemConfiguracion", c.getId().getIntItemConfiguracion());
			mapa.put("intParaEstadoCod", c.getIntParaEstadoCod());
			mapa.put("tsBusquedaInicio", busquedaInicio);
			mapa.put("tsBusquedaFin", busquedaFin);
			lista = dao.getListaPorBusquedaSinEstructura(mapa);
				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
