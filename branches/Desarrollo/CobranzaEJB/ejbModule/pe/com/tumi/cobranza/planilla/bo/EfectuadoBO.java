package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

import pe.com.tumi.cobranza.planilla.dao.EfectuadoDao;
import pe.com.tumi.cobranza.planilla.dao.impl.EfectuadoDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoId;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.EnviomontoId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;

public class EfectuadoBO{

	private EfectuadoDao dao = (EfectuadoDao)TumiFactory.get(EfectuadoDaoIbatis.class);

	public Efectuado grabarEfectuado(Efectuado o) throws BusinessException{
		Efectuado dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Efectuado modificarEfectuado(Efectuado o) throws BusinessException{
     	Efectuado dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Efectuado getEfectuadoPorPk(EfectuadoId pId) throws BusinessException{
		Efectuado domain = null;
		List<Efectuado> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", pId.getIntEmpresacuentaPk());
			mapa.put("intItemefectuado", pId.getIntItemefectuado());
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
	
	public List<Efectuado> getListaEfectuadoPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(Integer intIdEmpresa,EstructuraId pId,Integer intTipoModalidad,Integer intPeriodo) throws BusinessException{
		List<Efectuado> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", 	intIdEmpresa);
			mapa.put("intNivel", 			pId.getIntNivel());
			mapa.put("intCodigo", 			pId.getIntCodigo());
			mapa.put("intModalidadCod", 	intTipoModalidad);
			mapa.put("intPeriodoPlanilla", 	intPeriodo);
			lista = dao.getListaPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Integer getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidad(Integer intEmpresa,EstructuraId pk, Integer intTipoSocio,Integer intModalidad) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intNivel", pk.getIntNivel());
			mapa.put("intCodigo", pk.getIntCodigo());
			mapa.put("intTiposocioCod", intTipoSocio);
			mapa.put("intModalidadCod", intModalidad);
			intEscalar = dao.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidad(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstrucura(Integer intEmpresa,EstructuraId pk, Integer intTipoSocio,Integer intModalidad,Integer intTipoEstruCod) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intNivel", pk.getIntNivel());
			mapa.put("intCodigo", pk.getIntCodigo());
			mapa.put("intTiposocioCod", intTipoSocio);
			mapa.put("intModalidadCod", intModalidad);
			mapa.put("intTipoEstruCod", intTipoEstruCod);
			intEscalar = dao.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidad(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	
	public Integer getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocio(Integer intEmpresa,EstructuraId pk, Integer intTipoSocio) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intNivel", pk.getIntNivel());
			mapa.put("intCodigo", pk.getIntCodigo());
			mapa.put("intTiposocioCod", intTipoSocio);			
			intEscalar = dao.getMaxPeriodoPorEmpresaYEstructuraYTipoSocio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	
    /** CREADO 06/08/2013 
     * SE OBTINIE EFECTUADO POR PK DE ENVIOMONTO
     * **/	
	public List<Efectuado> getListaEfectuadoPorPkEnviomontoYPeriodo(EnviomontoId pId, Envioconcepto envioConcepto) throws BusinessException{
		List<Efectuado> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemenvioconcepto", 	pId.getIntItemenvioconcepto());
			mapa.put("intItemenviomonto", 		pId.getIntItemenviomonto());
			mapa.put("intEmpresacuentaPk", 		pId.getIntEmpresacuentaPk());
			mapa.put("intPeriodoPlanilla", 		envioConcepto.getIntPeriodoplanilla());
			lista = dao.getListaEfectuadoPorPkEnviomontoYPeriodo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
	
	public List<Efectuado> getListaEfectuadoXNiveCodigoModaliPeriodoTipoSocio(Integer intIdEmpresa,EstructuraId pId,Integer intTipoModalidad,Integer intPeriodo, Integer intTipoSocio) throws BusinessException{
		List<Efectuado> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", 	intIdEmpresa);
			mapa.put("intNivel", 			pId.getIntNivel());
			mapa.put("intCodigo", 			pId.getIntCodigo());
			mapa.put("intModalidadCod", 	intTipoModalidad);
			mapa.put("intPeriodoPlanilla", 	intPeriodo);
			mapa.put("intTipoSocio", 	intTipoSocio);
			lista = dao.getListaEfectuadoXNiveCodigoModaliPeriodoTipoSocio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public Efectuado getEfectuadoPorItemEnvioConcepto(Enviomonto o) throws BusinessException{
		Efectuado domain = null;
		List<Efectuado> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", o.getId().getIntEmpresacuentaPk());
			mapa.put("intItemenvioconcepto", o.getId().getIntItemenvioconcepto());
			lista = dao.getEfectuadoPorItemEnvioConcepto(mapa);
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
	
	
	/**
	 * Recupera los EFECTUADOS en base a la Empresa, Cuenta y peridodo en cualquier estado.
	 * @param intEmpresacuentaPk
	 * @param intCuentaPk
	 * @param intPeriodoPlanilla
	 * @return
	 * @throws BusinessException
	 */
	public List<Efectuado> getListaPorEmpCtaPeriodo(Integer intEmpresacuentaPk, Integer intCuentaPk, Integer intPeriodoPlanilla) throws BusinessException{
		List<Efectuado> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", 	intEmpresacuentaPk);
			mapa.put("intCuentaPk", 		intCuentaPk);
			mapa.put("intPeriodoPlanilla", 	intPeriodoPlanilla);
			lista = dao.getListaPorEmpCtaPeriodo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Efectuado> getListaPorAdministra(Integer itemEfectuadoResumen) throws BusinessException
	{
		List<Efectuado> lista = null;
		try
		{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intItemEfectuadoResumen", itemEfectuadoResumen);		
			lista = dao.getListaPorAdministra(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	

	
	public Efectuado getMontoTotalPorConcepto(Integer itemEfectuadoResumen,
											  Integer sucursal,
											  Integer subSucursal,
											  Integer conceptoGeneral,
											  Integer intParaTipoConcepto,
											  Integer empresa,
											  Integer periodo,
											  Integer codigoModelo,
											  String numeroCuenta)throws BusinessException
	{
		Efectuado domain = null;
		List<Efectuado> lista = null;
		try
		{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intItemEfectuadoResumen",itemEfectuadoResumen);
			mapa.put("sucursal",sucursal);
			mapa.put("subSucursal",subSucursal);
			mapa.put("intConceptoGeneral",conceptoGeneral);
			mapa.put("intParaTipoConcepto",intParaTipoConcepto);
			mapa.put("intEmpresa",empresa);
			mapa.put("intPeriodo",periodo);
			mapa.put("intCodigoModelo",codigoModelo);
			mapa.put("strNumeroCuenta",numeroCuenta);
			lista = dao.getMontoTotalPorConcepto(mapa);
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
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public Integer getNumerodeRolesUsuarios(Integer itemEfectuadoResumen)throws BusinessException
	{
		Integer intEscalar = null;
		try
		{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intItemEfectuadoResumen",itemEfectuadoResumen);
			intEscalar = dao.getNumerodeRolesUsuarios(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
			
}