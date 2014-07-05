package pe.com.tumi.contabilidad.core.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.core.bo.ModeloBO;
import pe.com.tumi.contabilidad.core.bo.ModeloDetalleBO;
import pe.com.tumi.contabilidad.core.bo.ModeloDetalleNivelBO;
import pe.com.tumi.contabilidad.core.bo.PlanCuentaBO;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;
import pe.com.tumi.contabilidad.core.domain.ModeloId;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class ModeloService {
	protected static Logger log = Logger.getLogger(ModeloService.class);
	ModeloBO boModelo = (ModeloBO)TumiFactory.get(ModeloBO.class);
	ModeloDetalleBO boModeloDetalle = (ModeloDetalleBO)TumiFactory.get(ModeloDetalleBO.class);
	ModeloDetalleNivelBO boModeloDetalleNivel = (ModeloDetalleNivelBO)TumiFactory.get(ModeloDetalleNivelBO.class);
	PlanCuentaBO boPlanCuenta = (PlanCuentaBO)TumiFactory.get(PlanCuentaBO.class);
	
	public Modelo grabarModelo(Modelo o) throws BusinessException{
		Modelo domain = null;
		domain = boModelo.grabarModelo(o);
		
		if(domain!=null){
			for(ModeloDetalle modeloDetalle : o.getListModeloDetalle()){
				grabarModeloDetalleDinamico(modeloDetalle, domain.getId());
			}
		}
		return domain;
	}
	
	public Modelo modificarModelo(Modelo o) throws BusinessException{
		Modelo domain = null;
		try{		
			domain = boModelo.modificarModelo(o);
			
			if(domain!=null){
				for(ModeloDetalle modeloDetalle : o.getListModeloDetalle()){
					log.info(modeloDetalle.getIsDeleted()+" "+modeloDetalle);					
					grabarModeloDetalleDinamico(modeloDetalle, domain.getId());
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public void grabarModeloDetalleDinamico(ModeloDetalle modeloDetalle, ModeloId id) throws BusinessException{
		ModeloDetalle domain = null;
		
		//en caso cumpla con la condición se elimina físicamente de la base de datos 
		if(modeloDetalle.getIsDeleted()!=null && modeloDetalle.getIsDeleted()){
			System.out.println("o.getIsDeleted(): "+modeloDetalle.getIsDeleted());
			modeloDetalle.setListModeloDetalleNivel(boModeloDetalleNivel.getListaPorModeloDetalleId(modeloDetalle.getId()));
			for(ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel()){
				boModeloDetalleNivel.eliminarModeloDetNivel(modeloDetalleNivel.getId());
			}
			boModeloDetalle.eliminarModeloDetalle(modeloDetalle.getId());
			return ;
		}
		
		//para grabar o actualizar
		domain = boModeloDetalle.getModeloDetallePorPk(modeloDetalle.getId());
		if(domain!=null){
			domain = boModeloDetalle.modificarModeloDetalle(modeloDetalle);
		}else{
			modeloDetalle.getId().setIntEmpresaPk(id.getIntEmpresaPk());
			modeloDetalle.getId().setIntCodigoModelo(id.getIntCodigoModelo());
			domain = boModeloDetalle.grabarModeloDetalle(modeloDetalle);
		}
		
		if(domain!=null && modeloDetalle.getListModeloDetalleNivel()!=null){
			for(ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel()){
				grabarModeloDetNivelDinamico(modeloDetalleNivel, domain.getId());
			}
		}
	}
	
	public ModeloDetalleNivel grabarModeloDetNivelDinamico(ModeloDetalleNivel o, ModeloDetalleId id) throws BusinessException{
		ModeloDetalleNivel domain = null;
		
		//en caso cumpla con la condición se elimina físicamente de la base de datos 
		if(o.getIsDeleted()!=null && o.getIsDeleted()){
			System.out.println("o.getIsDeleted(): "+o.getIsDeleted());
			domain = boModeloDetalleNivel.getModeloDetalleNivelPorPk(o.getId());
			if(domain!=null){
				domain = boModeloDetalleNivel.eliminarModeloDetNivel(domain.getId());
				return domain;
			}
		}
		
		if(o.getId()!=null && o.getId().getIntItem()==null){
			if(o.getId()==null)o.setId(new ModeloDetalleNivelId());
			o.getId().setIntEmpresaPk(id.getIntEmpresaPk());
			o.getId().setIntCodigoModelo(id.getIntCodigoModelo());
			o.getId().setIntPersEmpresaCuenta(id.getIntPersEmpresaCuenta());
			o.getId().setIntContPeriodoCuenta(id.getIntContPeriodoCuenta());
			o.getId().setStrContNumeroCuenta(id.getStrContNumeroCuenta());
			domain = boModeloDetalleNivel.grabarModeloDetalleNivel(o);
		}else{
			domain = boModeloDetalleNivel.getModeloDetalleNivelPorPk(o.getId());
			if(domain!=null){
				domain = boModeloDetalleNivel.modificarModeloDetalleNivel(o);
			}else{
				if(o.getId()==null)o.setId(new ModeloDetalleNivelId());
				o.getId().setIntEmpresaPk(id.getIntEmpresaPk());
				o.getId().setIntCodigoModelo(id.getIntCodigoModelo());
				o.getId().setIntPersEmpresaCuenta(id.getIntPersEmpresaCuenta());
				o.getId().setIntContPeriodoCuenta(id.getIntContPeriodoCuenta());
				o.getId().setStrContNumeroCuenta(id.getStrContNumeroCuenta());
				domain = boModeloDetalleNivel.grabarModeloDetalleNivel(o);
			}
		}
		
		return domain;
	}
	
	public List<Modelo> getListaModeloBusqueda(Modelo o) throws BusinessException{
		List<Modelo> lista = null;
		List<ModeloDetalle> listaModeloDetalle = null;
		List<ModeloDetalleNivel> listaModeloDetalleNivel = null;
		List<Modelo> listaAux = null;
		
		lista = boModelo.getModeloBusqueda(o);
		//obtener ModeloDetalle y ModeloDetalleNivel
		if(lista!=null && lista.size()>0){
			for(Modelo modelo : lista){
				Integer intPeriodo = null;
				listaModeloDetalle = boModeloDetalle.getListaPorModeloId(modelo.getId());
				//Si el modelo tiene data en Modelo Detalle...
				if(listaModeloDetalle!=null && listaModeloDetalle.size()>0){
					intPeriodo = listaModeloDetalle.get(0).getId().getIntContPeriodoCuenta();					
					//el período se busca por separado ya que no es columna de la tabla Modelo
					modelo.setIntPeriodo(intPeriodo);
					modelo.setListModeloDetalle(listaModeloDetalle);
				//Sino...
				}else{
					modelo.setIntPeriodo(0);
				}
			}
			//filtrar por el período, en caso se este buscando el modelo actual (obtenerTipoModeloActual) el período será el año vigente 
			if(o.getIntPeriodo()!=null){
				listaAux = new ArrayList<Modelo>();
				for(Modelo modelo : lista){
					log.info(modelo);
					if(o.getIntPeriodo().equals(new Integer(0)) || modelo.getIntPeriodo().equals(o.getIntPeriodo())){
						for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
							//obtener la lista ModeloDetalleNivel
							listaModeloDetalleNivel = boModeloDetalleNivel.getListaPorModeloDetalleId(modeloDetalle.getId());
							if(listaModeloDetalleNivel!=null && listaModeloDetalleNivel.size()>0){
								modeloDetalle.setListModeloDetalleNivel(listaModeloDetalleNivel);
							}
							//obtener el PlanContable, por cada ModeloDetalle
							PlanCuentaId planCuentaPK = new PlanCuentaId(modeloDetalle.getId().getIntPersEmpresaCuenta(),
															modeloDetalle.getId().getIntContPeriodoCuenta(),
															modeloDetalle.getId().getStrContNumeroCuenta());
							modeloDetalle.setPlanCuenta(boPlanCuenta.getPlanCuentaPorPk(planCuentaPK));							
						}
						listaAux.add(modelo);
					}
				}
				lista = listaAux;
			}
		}
		return lista;
	}

	public List<Modelo> obtenerTipoModeloActual(Integer intTipoModelo, Integer intIdEmpresa) throws BusinessException{
		List<Modelo> lista = null;
		try{
			Calendar cal = Calendar.getInstance();
			Integer annoActual = cal.get(Calendar.YEAR);
			
			Modelo modeloFiltro = new Modelo();
			modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
			modeloFiltro.setIntTipoModeloContable(intTipoModelo);
			modeloFiltro.setIntEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			modeloFiltro.setIntPeriodo(annoActual);
			
			log.info(modeloFiltro);
			lista = getListaModeloBusqueda(modeloFiltro); 
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public List<Modelo> obtenerTipoModeloRefinanciamiento(Integer intTipoModelo, Integer intIdEmpresa) throws BusinessException{
		List<Modelo> lista = null;
		try{
			Calendar cal = Calendar.getInstance();
			Integer añoActual = cal.get(Calendar.YEAR);
			
			Modelo modeloFiltro = new Modelo();
			modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
			modeloFiltro.setIntTipoModeloContable(intTipoModelo);
			modeloFiltro.setIntEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			modeloFiltro.setIntPeriodo(añoActual);
			
			log.info(modeloFiltro);
			lista = getListaModeloBusqueda(modeloFiltro); 
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
