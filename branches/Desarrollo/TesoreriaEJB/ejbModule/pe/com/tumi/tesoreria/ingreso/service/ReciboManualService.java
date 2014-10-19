package pe.com.tumi.tesoreria.ingreso.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.bo.ReciboManualBO;
import pe.com.tumi.tesoreria.ingreso.bo.ReciboManualDetalleBO;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualId;

public class ReciboManualService {

	protected static Logger log = Logger.getLogger(ReciboManualService.class);
	
	ReciboManualBO boReciboManual = (ReciboManualBO)TumiFactory.get(ReciboManualBO.class);
	ReciboManualDetalleBO boReciboManualDetalle = (ReciboManualDetalleBO)TumiFactory.get(ReciboManualDetalleBO.class);
	IngresoBO boIngreso = (IngresoBO)TumiFactory.get(IngresoBO.class);
	
	
	public ReciboManual getReciboManualPorSubsucursal(Subsucursal subsucursal)throws BusinessException{
		ReciboManual reciboManual = null;
		try{
			ReciboManual reciboManualFiltro = new ReciboManual();
			reciboManualFiltro.getId().setIntPersEmpresa(subsucursal.getId().getIntPersEmpresaPk());
			reciboManualFiltro.setIntSucuIdSucursal(subsucursal.getId().getIntIdSucursal());
			reciboManualFiltro.setIntSudeIdSubsucursal(subsucursal.getId().getIntIdSubSucursal());
			reciboManualFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			reciboManualFiltro.setIntParaEstadoCierre(Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO);
			
			List<ReciboManual> listaReciboManual = boReciboManual.getPorBuscar(reciboManualFiltro);
			
			if(listaReciboManual==null || listaReciboManual.isEmpty()){
				return null;
			}else if(listaReciboManual.size()>1){
				throw new Exception("Existe mas de un recibo manual activo");
			}
			
			reciboManual = listaReciboManual.get(0);
			reciboManual.setListaReciboManualDetalle(boReciboManualDetalle.getPorReciboManual(reciboManual));
			//filtrar lista por gestor seleccionado...
			reciboManual = obtenerReciboManualDetalleUltimo(reciboManual);
			if(reciboManual.getReciboManualDetalleUltimo()!=null){
				reciboManual.setIntNumeroActual(reciboManual.getReciboManualDetalleUltimo().getIntNumeroRecibo()+1);
			}else{
				reciboManual.setIntNumeroActual(reciboManual.getIntNumeroInicio());
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return reciboManual;
	}
	
	private ReciboManual obtenerReciboManualDetalleUltimo(ReciboManual reciboManual)throws Exception{
		List<ReciboManualDetalle> listaReciboManualDetalleTemp = reciboManual.getListaReciboManualDetalle();
		if(listaReciboManualDetalleTemp == null || listaReciboManualDetalleTemp.isEmpty()){
			return reciboManual;
		}
		
		List<ReciboManualDetalle> listaReciboManualDetalle = new ArrayList<ReciboManualDetalle>();
		for(ReciboManualDetalle reciboManualDetalle : listaReciboManualDetalleTemp){
			if(reciboManualDetalle.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				listaReciboManualDetalle.add(reciboManualDetalle);
			}			
		}
		//Ordena los reciboManualDetalle por numeroRecibo descendentemente
		Collections.sort(listaReciboManualDetalle, new Comparator<ReciboManualDetalle>(){
			public int compare(ReciboManualDetalle uno, ReciboManualDetalle otro) {
				return otro.getIntNumeroRecibo().compareTo(uno.getIntNumeroRecibo());
			}
		});
		
		reciboManual.setReciboManualDetalleUltimo(listaReciboManualDetalle.get(0));		
		return reciboManual;
	}
	
	
	public ReciboManual obtenerReciboManualPorIngreso(Ingreso ingreso)throws Exception{
		ReciboManual reciboManual = null;
		try{
			ReciboManualDetalle reciboManualDetalle = boReciboManualDetalle.getPorIngreso(ingreso);
			if(reciboManualDetalle==null) return reciboManual;
			
			reciboManual = boReciboManual.getPorReciboManualDetalle(reciboManualDetalle);
			reciboManual.setIntNumeroActual(reciboManualDetalle.getIntNumeroRecibo());
			
			reciboManual.getListaReciboManualDetalle().add(reciboManualDetalle);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return reciboManual;
	}
	
	//Autor : jbermudez / tarea : desactivado, ya no se invoca desde el FACADE / Fecha : 19.09.2014
	public Integer validarNroReciboPorSuc(Integer idEmpresa, Integer sucursal, Integer subsuc, Integer nroSerie, Integer nroRecibo) throws Exception{
			Integer  vResult = null;
		try{
			vResult = boReciboManual.validarNroReciboPorSuc(idEmpresa, sucursal, subsuc, nroSerie, nroRecibo);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return vResult;
	}
	
	public List<ReciboManualDetalle> buscarRecibosEnlazados(Integer idEmpresa,Integer idSucursal,Integer idSubSuc,Integer idEstadoRecibo,
			Integer nroSerie, Integer nroRecibo)throws Exception{
		
		//PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
		//EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
		
		
		//List<ReciboManualDetalle> listaReciboManualDetalleTemp = new ArrayList<ReciboManualDetalle>();
		
		List<ReciboManualDetalle> listaReciboManualDetalle = boReciboManualDetalle.getListaPorFiltros(idEmpresa, idSucursal, idSubSuc, idEstadoRecibo, nroSerie, nroRecibo);
		
//		for (ReciboManualDetalle reciboManualDetalle : listaReciboManualDetalle) {
//			IngresoId ingresoId = new IngresoId();
//			ingresoId.setIntIdEmpresa(idEmpresa);
//			ingresoId.setIntIdIngresoGeneral(reciboManualDetalle.getIntItemIngresoGeneral());
//			Ingreso ingreso = boIngreso.getPorId(ingresoId);
//			reciboManualDetalle.setIngreso(ingreso); //DATOS DEL INGRESO
//			ReciboManualId reciboManualId = new ReciboManualId();
//			reciboManualId.setIntPersEmpresa(idEmpresa);
//			reciboManualId.setIntItemReciboManual(reciboManualDetalle.getId().getIntItemReciboManual());
//			ReciboManual reciboManual = boReciboManual.getPorId(reciboManualId);
//			reciboManualDetalle.setReciboManual(reciboManual); //DATOS DEL RECIBO MANUAL
//			Natural gestor = personaFacade.getNaturalPorPK(reciboManualDetalle.getIntPersPersonaGestor());
//			reciboManualDetalle.setGestor(gestor); //DATOS DEL GESTOR
//			Sucursal sucursal = new Sucursal();
//			sucursal.getId().setIntIdSucursal(reciboManual.getIntSucuIdSucursal());
//			sucursal = empresaFacade.getSucursalPorPK(sucursal);
//			reciboManualDetalle.setStrDesSucursal(sucursal.getJuridica().getStrRazonSocial());
//			
//		    listaReciboManualDetalleTemp.add(reciboManualDetalle);
//		}
		
		return listaReciboManualDetalle;
	}
	
}