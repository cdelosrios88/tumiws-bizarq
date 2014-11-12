package pe.com.tumi.contabilidad.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

import pe.com.tumi.contabilidad.core.dao.ModeloDetalleNivelDao;
import pe.com.tumi.contabilidad.core.dao.impl.ModeloDetalleNivelDaoIbatis;

import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;


public class ModeloDetalleNivelBO{

	private ModeloDetalleNivelDao dao = (ModeloDetalleNivelDao)TumiFactory.get(ModeloDetalleNivelDaoIbatis.class);

	public ModeloDetalleNivel grabarModeloDetalleNivel(ModeloDetalleNivel o) throws BusinessException{
		ModeloDetalleNivel dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public ModeloDetalleNivel modificarModeloDetalleNivel(ModeloDetalleNivel o) throws BusinessException{
     	ModeloDetalleNivel dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public ModeloDetalleNivel getModeloDetalleNivelPorPk(ModeloDetalleNivelId pId) throws BusinessException{
		ModeloDetalleNivel domain = null;
		List<ModeloDetalleNivel> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntEmpresaPk", pId.getIntEmpresaPk());
			mapa.put("pIntCodigoModelo", pId.getIntCodigoModelo());
			mapa.put("pIntPersEmpresaCuenta", pId.getIntPersEmpresaCuenta());
			mapa.put("pIntContPeriodoCuenta", pId.getIntContPeriodoCuenta());
			mapa.put("pStrContNumeroCuenta", pId.getStrContNumeroCuenta());
			mapa.put("pIntItem", pId.getIntItem());
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
	
	public List<ModeloDetalleNivel> getListaPorModeloDetalleId(ModeloDetalleId pId) throws BusinessException{
		List<ModeloDetalleNivel> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntEmpresaPk", pId.getIntEmpresaPk());
			mapa.put("pIntCodigoModelo", pId.getIntCodigoModelo());
			mapa.put("pIntPersEmpresaCuenta", pId.getIntPersEmpresaCuenta());
			mapa.put("pIntContPeriodoCuenta", pId.getIntContPeriodoCuenta());
			mapa.put("pStrContNumeroCuenta", pId.getStrContNumeroCuenta());
			lista = dao.getListaPorModeloDetalleId(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public ModeloDetalleNivel eliminarModeloDetNivel(ModeloDetalleNivelId o) throws BusinessException{
		ModeloDetalleNivel dto = null;
		try{
			System.out.println("eliminando...");
			System.out.println("intEmpresaPk: "+o.getIntEmpresaPk());
			System.out.println("intCodigoModelo: "+o.getIntCodigoModelo());
			System.out.println("intPersEmpresaCuenta: "+o.getIntPersEmpresaCuenta());
			System.out.println("intContPeriodoCuenta: "+o.getIntContPeriodoCuenta());
			System.out.println("strContNumeroCuenta: "+o.getStrContNumeroCuenta());
		    dto = dao.eliminar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	/**
	 * JCHAVEZ 30.12.2013
	 * Recupera Modelo Detalle Nivel en Giro Prestamo segun filtros
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<ModeloDetalleNivelComp> getModeloGiroPrestamo(ModeloDetalleNivel modeloFiltro) throws BusinessException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			System.out.println("intEmpresa ---> "+modeloFiltro.getId().getIntEmpresaPk());
			System.out.println("intTipoModeloContable ---> "+modeloFiltro.getIntTipoModeloContable());
			System.out.println("intEmpresaCuenta ---> "+modeloFiltro.getId().getIntPersEmpresaCuenta());
			System.out.println("intPeriodoCuenta ---> "+modeloFiltro.getId().getIntContPeriodoCuenta());
			System.out.println("intDatoTablas ---> "+modeloFiltro.getIntDatoTablas());
			System.out.println("intDatoArgumento ---> "+modeloFiltro.getIntDatoArgumento());
			mapa.put("intEmpresa", modeloFiltro.getId().getIntEmpresaPk());
			mapa.put("intTipoModeloContable", modeloFiltro.getIntTipoModeloContable());
			mapa.put("intEmpresaCuenta", modeloFiltro.getId().getIntPersEmpresaCuenta());
			mapa.put("intPeriodoCuenta", modeloFiltro.getId().getIntContPeriodoCuenta());
			mapa.put("intDatoTablas", modeloFiltro.getIntDatoTablas());
			mapa.put("intDatoArgumento", modeloFiltro.getIntDatoArgumento());
			lista = dao.getModeloGiroPrestamo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * JCHAVEZ 02.01.2014
	 * Recupera la pk de Modelo Detalle Nivel del préstamo a cancelar, haciendo
     * un cruce entre las cuentas obtenidas por el tipo de crédito y las obtenidas por la categoría
     * de RIESGO
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<ModeloDetalleNivelId> getPkModeloXReprestamo(ModeloDetalleNivel modeloFiltro, Integer intTipoCredito, Integer intItemCredito, Integer intCategoriaRiesgo) throws BusinessException{
		List<ModeloDetalleNivelId> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", modeloFiltro.getId().getIntEmpresaPk());
			mapa.put("intTipoModeloContable", modeloFiltro.getIntTipoModeloContable());
			mapa.put("intEmpresaCuenta", modeloFiltro.getId().getIntPersEmpresaCuenta());
			mapa.put("intPeriodoCuenta", modeloFiltro.getId().getIntContPeriodoCuenta());
			mapa.put("intTipoCredito", intTipoCredito);
			mapa.put("intItemCredito", intItemCredito);
			mapa.put("intCategoriaRiesgo", intCategoriaRiesgo);
			lista = dao.getPkModeloXReprestamo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * JCHAVEZ 02.01.2014
	 * Recupera el campo que se va a guardar en la grabación del libro diario
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<ModeloDetalleNivelComp> getCampoXPkModelo(ModeloDetalleNivel modeloFiltro) throws BusinessException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntEmpresaPk", modeloFiltro.getId().getIntEmpresaPk());
			mapa.put("intTipoModeloContable", modeloFiltro.getIntTipoModeloContable());
			mapa.put("pIntPersEmpresaCuenta", modeloFiltro.getId().getIntPersEmpresaCuenta());
			mapa.put("pIntContPeriodoCuenta", modeloFiltro.getId().getIntContPeriodoCuenta());
			mapa.put("pStrContNumeroCuenta", modeloFiltro.getId().getStrContNumeroCuenta());
			lista = dao.getCampoXPkModelo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * JCHAVEZ 21.01.2014
	 * Recupera Modelo Detalle Nivel en Giro Prevision segun filtros
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<ModeloDetalleNivelComp> getModeloGiroPrevision(ModeloDetalleNivel modeloFiltro) throws BusinessException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			System.out.println("intEmpresa ---> "+modeloFiltro.getId().getIntEmpresaPk());
			System.out.println("intTipoModeloContable ---> "+modeloFiltro.getIntTipoModeloContable());
			System.out.println("intEmpresaCuenta ---> "+modeloFiltro.getId().getIntPersEmpresaCuenta());
			System.out.println("intPeriodoCuenta ---> "+modeloFiltro.getId().getIntContPeriodoCuenta());
			System.out.println("intDatoTablas ---> "+modeloFiltro.getIntDatoTablas());
			System.out.println("intDatoArgumento ---> "+modeloFiltro.getIntDatoArgumento());
			mapa.put("intEmpresa", modeloFiltro.getId().getIntEmpresaPk());
			mapa.put("intTipoModeloContable", modeloFiltro.getIntTipoModeloContable());
			mapa.put("intEmpresaCuenta", modeloFiltro.getId().getIntPersEmpresaCuenta());
			mapa.put("intPeriodoCuenta", modeloFiltro.getId().getIntContPeriodoCuenta());
			mapa.put("intDatoTablas", modeloFiltro.getIntDatoTablas());
			mapa.put("intDatoArgumento", modeloFiltro.getIntDatoArgumento());
			lista = dao.getModeloGiroPrevision(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * JCHAVEZ 21.01.2014
	 * Recupera Modelo Detalle Nivel en Planilla Movimiento segun filtros
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<ModeloDetalleNivelComp> getModeloPlanillaMovilidad(ModeloDetalleNivel modeloFiltro) throws BusinessException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			System.out.println("intEmpresa ---> "+modeloFiltro.getId().getIntEmpresaPk());
			System.out.println("intTipoModeloContable ---> "+modeloFiltro.getIntTipoModeloContable());
			System.out.println("intEmpresaCuenta ---> "+modeloFiltro.getId().getIntPersEmpresaCuenta());
			System.out.println("intPeriodoCuenta ---> "+modeloFiltro.getId().getIntContPeriodoCuenta());
			System.out.println("intValor ---> "+modeloFiltro.getIntValor());
			mapa.put("intEmpresa", modeloFiltro.getId().getIntEmpresaPk());
			mapa.put("intTipoModeloContable", modeloFiltro.getIntTipoModeloContable());
			mapa.put("intEmpresaCuenta", modeloFiltro.getId().getIntPersEmpresaCuenta());
			mapa.put("intPeriodoCuenta", modeloFiltro.getId().getIntContPeriodoCuenta());
			mapa.put("intValor", modeloFiltro.getIntValor());
			lista = dao.getModeloPlanillaMovilidad(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public String getCuentaPorPagar(Integer intEmpresa,								   
									Integer intPeriodo)throws BusinessException
	{
		String strEscalar = null;
		try
		{	
			HashMap<String,Object> mapa = new HashMap<String, Object>();			
			mapa.put("intEmpresa", intEmpresa);
			mapa.put("intPeriodo", intPeriodo);			
			strEscalar = dao.getCuentaPorPagar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return strEscalar;
	}
	
	
	public List<ModeloDetalleNivel> getNumeroCuentaPrestamo(Integer empresa,
															Integer periodo,
															Integer paraTipoRiesgo,
															Integer itemConcepto,
															Integer categoria,
															Integer conceptoGeneral,
									   						Integer intTipoModeloContable) throws BusinessException{
		List<ModeloDetalleNivel> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", empresa);
			mapa.put("intPeriodo", periodo);
			mapa.put("intParaTipoRiesgo", paraTipoRiesgo);
			mapa.put("intItemConcepto", itemConcepto);
			mapa.put("intCategoria", categoria);
			mapa.put("intConceptoGeneral", conceptoGeneral);
			mapa.put("intTipoModeloContable",intTipoModeloContable);
			lista = dao.getNumeroCuentaPrestamo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ModeloDetalleNivel> getNroCtaPrestamoSinCategoria(Integer empresa,
															Integer periodo,
															Integer paraTipoRiesgo,
															Integer itemConcepto,															
															Integer conceptoGeneral,
									   						Integer intTipoModeloContable) throws BusinessException{
		List<ModeloDetalleNivel> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", empresa);
			mapa.put("intPeriodo", periodo);
			mapa.put("intParaTipoRiesgo", paraTipoRiesgo);
			mapa.put("intItemConcepto", itemConcepto);			
			mapa.put("intConceptoGeneral", conceptoGeneral);
			mapa.put("intTipoModeloContable",intTipoModeloContable);
			lista = dao.getNroCtaPrestamoSinCategoria(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * jchavez 27.05.2014
	 * obtiene el modelo de provision de fdo. de retiro
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<ModeloDetalleNivelComp> getModeloProvisionRetiro(ModeloDetalleNivel modeloFiltro) throws BusinessException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", modeloFiltro.getId().getIntEmpresaPk());
			mapa.put("intEmpresaCuenta", modeloFiltro.getId().getIntPersEmpresaCuenta());
			mapa.put("intPeriodoCuenta", modeloFiltro.getId().getIntContPeriodoCuenta());
			lista = dao.getModeloProvisionRetiro(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * jchavez 27.05.2014
	 * obtiene el modelo de provision de fdo. de retiro interes generado y capitalizado
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<ModeloDetalleNivelComp> getModeloProvRetiroInteres(ModeloDetalleNivel modeloFiltro) throws BusinessException{
		List<ModeloDetalleNivelComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", modeloFiltro.getId().getIntEmpresaPk());
			mapa.put("intTipoModeloContable", modeloFiltro.getIntTipoModeloContable());
			mapa.put("intEmpresaCuenta", modeloFiltro.getId().getIntPersEmpresaCuenta());
			mapa.put("intPeriodoCuenta", modeloFiltro.getId().getIntContPeriodoCuenta());
			lista = dao.getModeloProvRetiroInteres(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	//Autor: fyalico / Tarea: Creación / Fecha: 11.09.2014 
	public String getCuentaPorCobrar(Integer intEmpresa,								   
									Integer intPeriodo)throws BusinessException
	{
		String strEscalar = null;
		try
		{	
			HashMap<String,Object> mapa = new HashMap<String, Object>();			
			mapa.put("intEmpresa", intEmpresa);
			mapa.put("intPeriodo", intPeriodo);			
			strEscalar = dao.getCuentaPorCobrar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return strEscalar;
	}
 
	 
}
