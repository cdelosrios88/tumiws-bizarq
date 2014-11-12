package pe.com.tumi.tesoreria.logistica.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuenta;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuentaDetalle;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraDetalleBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraDocumentoBO;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalleId;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraId;


public class OrdenCompraService {

	protected static Logger log = Logger.getLogger(OrdenCompraService.class);
	
	OrdenCompraBO boOrdenCompra = (OrdenCompraBO)TumiFactory.get(OrdenCompraBO.class);
	OrdenCompraDetalleBO boOrdenCompraDetalle = (OrdenCompraDetalleBO)TumiFactory.get(OrdenCompraDetalleBO.class);
	OrdenCompraDocumentoBO boOrdenCompraDocumento = (OrdenCompraDocumentoBO)TumiFactory.get(OrdenCompraDocumentoBO.class);
	
	public OrdenCompra grabarOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
		try{			
			log.info(ordenCompra);			
			ordenCompra = boOrdenCompra.grabar(ordenCompra);
			
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				ordenCompraDetalle.getId().setIntPersEmpresa(ordenCompra.getId().getIntPersEmpresa());
				ordenCompraDetalle.getId().setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
				log.info(ordenCompraDetalle);		
				boOrdenCompraDetalle.grabar(ordenCompraDetalle);
			}
			
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				ordenCompraDocumento.getId().setIntPersEmpresa(ordenCompra.getId().getIntPersEmpresa());
				ordenCompraDocumento.getId().setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
				log.info(ordenCompraDocumento);
				boOrdenCompraDocumento.grabar(ordenCompraDocumento);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ordenCompra;
	}

	
	public List<OrdenCompra> buscarOrdenCompra(OrdenCompra ordenCompraFiltro, List<Persona> listaPersonaFiltro) throws BusinessException{
		List<OrdenCompra> listaOrdenCompra = null;
		try{			
			log.info(ordenCompraFiltro);
			listaOrdenCompra = boOrdenCompra.getPorBuscar(ordenCompraFiltro);
			for(OrdenCompra ordenCompra : listaOrdenCompra){
				ordenCompra.setListaOrdenCompraDetalle(boOrdenCompraDetalle.getPorOrdenCompra(ordenCompra));
				ordenCompra.setListaOrdenCompraDocumento(boOrdenCompraDocumento.getPorOrdenCompra(ordenCompra));
				ordenCompra.setBdMontoTotalDetalle(new BigDecimal(0));
				for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle())
					ordenCompra.setBdMontoTotalDetalle(ordenCompra.getBdMontoTotalDetalle().add(ordenCompraDetalle.getBdPrecioTotal()));				
			}
			
			if(listaPersonaFiltro==null || listaPersonaFiltro.isEmpty())
				return listaOrdenCompra;
			
			List<OrdenCompra> listaOrdenCompraTemp = new ArrayList<OrdenCompra>();
			for(Persona persona : listaPersonaFiltro){
				for(OrdenCompra ordenCompra : listaOrdenCompra)
					if(ordenCompra.getIntPersPersonaProveedor().equals(persona.getIntIdPersona()))
						listaOrdenCompraTemp.add(ordenCompra);				
			}
			listaOrdenCompra = listaOrdenCompraTemp;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaOrdenCompra;
	}
	
	private void manejarModificarOrdenCompraDetalle(OrdenCompra ordenCompra)throws Exception{
		List<OrdenCompraDetalle> listaIU = new ArrayList<OrdenCompraDetalle>();
		List<OrdenCompraDetalle> listaBD = boOrdenCompraDetalle.getPorOrdenCompra(ordenCompra);
		
		for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
			if(ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle()==null){
				ordenCompraDetalle.getId().setIntPersEmpresa(ordenCompra.getId().getIntPersEmpresa());
				ordenCompraDetalle.getId().setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
				log.info(ordenCompraDetalle);
				boOrdenCompraDetalle.grabar(ordenCompraDetalle);
			}else{
				listaIU.add(ordenCompraDetalle);
			}
		}
		
		for(OrdenCompraDetalle ordenCompraDetalleBD : listaBD){
			boolean seEncuentraEnIU = Boolean.FALSE;
			for(OrdenCompraDetalle ordenCompraDetalleIU : listaIU){
				if(ordenCompraDetalleIU.getId().getIntItemOrdenCompraDetalle().equals(ordenCompraDetalleBD.getId().getIntItemOrdenCompraDetalle())){
					seEncuentraEnIU = Boolean.TRUE;
					break;
				}
			}
			if(!seEncuentraEnIU){
				ordenCompraDetalleBD.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boOrdenCompraDetalle.modificar(ordenCompraDetalleBD);
			}
		}
	}
	
	private void manejarModificarOrdenCompraDocumento(OrdenCompra ordenCompra)throws Exception{
		List<OrdenCompraDocumento> listaIU = new ArrayList<OrdenCompraDocumento>();
		List<OrdenCompraDocumento> listaBD = boOrdenCompraDocumento.getPorOrdenCompra(ordenCompra);
		
		for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
			if(ordenCompraDocumento.getId().getIntItemOrdenCompraDocumento()==null){
				ordenCompraDocumento.getId().setIntPersEmpresa(ordenCompra.getId().getIntPersEmpresa());
				ordenCompraDocumento.getId().setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
				log.info(ordenCompraDocumento);
				boOrdenCompraDocumento.grabar(ordenCompraDocumento);
			}else{
				listaIU.add(ordenCompraDocumento);
			}
		}
		
		for(OrdenCompraDocumento ordenCompraDocumentoBD : listaBD){
			boolean seEncuentraEnIU = Boolean.FALSE;
			for(OrdenCompraDocumento ordenCompraDocumentoIU : listaIU){
				if(ordenCompraDocumentoIU.getId().getIntItemOrdenCompraDocumento().equals(
						ordenCompraDocumentoBD.getId().getIntItemOrdenCompraDocumento())){
					seEncuentraEnIU = Boolean.TRUE;
					break;
				}
			}
			if(!seEncuentraEnIU){
				ordenCompraDocumentoBD.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boOrdenCompraDocumento.modificar(ordenCompraDocumentoBD);
			}
		}
	}	
	
	public OrdenCompra modificarOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
		try{
			log.info(ordenCompra);
			ordenCompra = boOrdenCompra.modificar(ordenCompra);
			
			manejarModificarOrdenCompraDetalle(ordenCompra);
			manejarModificarOrdenCompraDocumento(ordenCompra);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ordenCompra;
	}
	
	public void verificarEstadoOrdenCompra(OrdenCompra ordenCompra)throws BusinessException{
		try{
			ordenCompra = boOrdenCompra.modificar(ordenCompra);
			
			boolean todoOrdenCompraDetalleSaldoCero = Boolean.TRUE;
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				if(!ordenCompraDetalle.getBdMontoSaldo().equals(new BigDecimal(0))){
					todoOrdenCompraDetalleSaldoCero = Boolean.FALSE;
					break;
				}
			}
			
			boolean todoOrdenCompraDocumentoSaldoCero = Boolean.TRUE;
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				if(!ordenCompraDocumento.getBdMontoPagado().equals(ordenCompraDocumento.getBdMontoIngresado())){
					todoOrdenCompraDocumentoSaldoCero = Boolean.FALSE;
					break;
				}
			}
			
			if(todoOrdenCompraDetalleSaldoCero && todoOrdenCompraDocumentoSaldoCero){
				ordenCompra.setIntParaEstadoOrden(Constante.PARAM_T_ESTADOORDEN_CERRADO);
				boOrdenCompra.modificar(ordenCompra);
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 02.10.2014
	 * Se recupera OrdenCompra y OrdenCompraDetalle
	 * @author jchavez
	 * @param planCuenta
	 * @param bdMontoValidar
	 * @return bdMontoAcumulado - Booleano
	 * @throws BusinessException
	 */
	public boolean validarMontoOrdenCompraDetalle(OrdenCompraDetalle ocdNuevo, BigDecimal bdMontoValidar) 
	throws BusinessException{
		Boolean montoValido = Boolean.TRUE;
		try{
			OrdenCompraDetalle ordenCompraDetalle = new OrdenCompraDetalle();
			ordenCompraDetalle.setId(new OrdenCompraDetalleId());
			
			ordenCompraDetalle.setIntPersEmpresaCuenta(ocdNuevo.getPlanCuenta().getId().getIntEmpresaCuentaPk());
			ordenCompraDetalle.setIntContPeriodoCuenta(ocdNuevo.getPlanCuenta().getId().getIntPeriodoCuenta());
			ordenCompraDetalle.setStrContNumeroCuenta(ocdNuevo.getPlanCuenta().getId().getStrNumeroCuenta());
			ordenCompraDetalle.setIntSucuIdSucursal(ocdNuevo.getIntSucuIdSucursal());
			ordenCompraDetalle.setIntSudeIdSubsucursal(ocdNuevo.getIntSudeIdSubsucursal());
			ordenCompraDetalle.setIntIdPerfil(ocdNuevo.getIntIdPerfil());
			
			ordenCompraDetalle = boOrdenCompraDetalle.getSumPrecioTotalXCta(ordenCompraDetalle);
			BigDecimal bdMontoAcumulado = BigDecimal.ZERO;
			if (ordenCompraDetalle!=null) {
				if (ordenCompraDetalle.getBdPrecioTotal()!=null) {
					bdMontoAcumulado = ordenCompraDetalle.getBdPrecioTotal().add(bdMontoValidar);
				}else{
					bdMontoAcumulado = bdMontoValidar;
				}
			}

			AccesoPlanCuentaDetalle accesoPlanCuentaDetalleUsar = obtenerAccesoPlanCuentaDetalleUsar(ocdNuevo.getPlanCuenta().getAccesoPlanCuentaUsar());
			log.info("Monto Maximo: "+accesoPlanCuentaDetalleUsar.getBdMontoMaximo());

			//Validamos que este dentro del rango de montos fijados
			if((accesoPlanCuentaDetalleUsar.getBdMontoMaximo()!=null
			&& accesoPlanCuentaDetalleUsar.getBdMontoMaximo().compareTo(bdMontoAcumulado)<0)){
				montoValido = Boolean.FALSE;
			}
			
			if((accesoPlanCuentaDetalleUsar.getBdMontoMinimo()!=null
			&& accesoPlanCuentaDetalleUsar.getBdMontoMinimo().compareTo(bdMontoAcumulado)>0)){
				montoValido = Boolean.FALSE;
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return montoValido;
	}
	
	private AccesoPlanCuentaDetalle obtenerAccesoPlanCuentaDetalleUsar(AccesoPlanCuenta accesoPlanCuenta)throws Exception{
		for(AccesoPlanCuentaDetalle accesoPlanCuentaDetalle : accesoPlanCuenta.getListaAccesoPlanCuentaDetalle()){
			if(accesoPlanCuentaDetalle.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO))
				return accesoPlanCuentaDetalle;
		}
		return null;
	}
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 06.10.2014
	 * @author jchavez
	 * @param ordenCompra
	 * @return lista de orden de compra
	 * @throws BusinessException
	 */
	public List<OrdenCompra> buscarDocumentoAdelantoGarantiaParaGiroPorTesoreria(Integer intIdPersona, Integer intIdEmpresa, Integer intParaTipoDocumento) throws BusinessException{
		List<OrdenCompra> listaTemp = null;
		List<OrdenCompra> lista = new ArrayList<OrdenCompra>();
		try {
			OrdenCompra ordCmp = new OrdenCompra();
			ordCmp.getId().setIntPersEmpresa(intIdEmpresa);
			ordCmp.setIntPersEmpresaProveedor(intIdEmpresa);
			ordCmp.setIntPersPersonaProveedor(intIdPersona);
			listaTemp = boOrdenCompra.getPorBuscar(ordCmp);
			if (listaTemp!=null && !listaTemp.isEmpty()) {
				//Orden Compra Detalle
				for (OrdenCompra o : listaTemp) {
					o.setListaOrdenCompraDetalle(boOrdenCompraDetalle.getPorOrdenCompra(o));
				}
				//Orden Compra Documento
				for (OrdenCompra o : listaTemp) {
					List<OrdenCompraDocumento> lstOrdCmpDoc = boOrdenCompraDocumento.getPorOrdenCompra(o);
					o.setListaOrdenCompraDocumento(new ArrayList<OrdenCompraDocumento>());
					//Autor: jchavez / Tarea: nueva validacion / Fecha: 07.10.2014
					if (lstOrdCmpDoc!=null && !lstOrdCmpDoc.isEmpty()) {
						for (OrdenCompraDocumento ordenCompraDocumento : lstOrdCmpDoc) {
							//Solo se mostraran las ordenes de compra documento que su tipo sea ADELANTO o GARANTIA
							if (ordenCompraDocumento.getIntParaDocumentoGeneral().equals(intParaTipoDocumento) && ordenCompraDocumento.getIntItemEgresoGeneral()==null) {
								o.getListaOrdenCompraDocumento().add(ordenCompraDocumento);
							}
						}
						//Solo se adjuntan las ordenes de compra cuya orden de compra documento sea ADELANTO o GARANTIA
						if (o.getListaOrdenCompraDocumento()!=null && !o.getListaOrdenCompraDocumento().isEmpty()) {
							lista.add(o);
						}						
					}//Fin jchavez - 07.10.2014
				}				
			}

			
		} catch (Exception e) {
			log.info("Error en buscarDocumentoAdelantoParaGiroDesdeTesoreria() ---> "+e.getMessage());
		}
		return lista;
	}
	
	/**
	 * Autor: jchavez / Tarea: Creacion / Fecha: 22.10.2014
	 * Funcionalidad: Recupera la lista de orden de compra, sus detalle y sus documentos por egreso
	 * @author jchavez
	 * @param egreso
	 * @return
	 * @throws BusinessException
	 */
	public List<OrdenCompra> obtenerOrdenCompraPorEgresoPk(Egreso egreso) throws BusinessException{
		List<OrdenCompra> lstOrdenCompra = new ArrayList<OrdenCompra>();
		try {
			OrdenCompra ordComp = new OrdenCompra();
			List<OrdenCompraDocumento> lstOrdComDoc = boOrdenCompraDocumento.getListaPorEgreso(egreso);
			
			if (lstOrdComDoc!=null && !lstOrdComDoc.isEmpty()) {
				ordComp.setId(new OrdenCompraId());
				ordComp.getId().setIntPersEmpresa(lstOrdComDoc.get(0).getId().getIntPersEmpresa());
				ordComp.getId().setIntItemOrdenCompra(lstOrdComDoc.get(0).getId().getIntItemOrdenCompra());
				ordComp = boOrdenCompra.getPorPk(ordComp.getId());
				ordComp.setListaOrdenCompraDetalle(boOrdenCompraDetalle.getPorOrdenCompra(ordComp));
				ordComp.setListaOrdenCompraDocumento(new ArrayList<OrdenCompraDocumento>());
				ordComp.getListaOrdenCompraDocumento().addAll(lstOrdComDoc);
			}
			lstOrdenCompra.add(ordComp);
		} catch (Exception e) {
			log.info("Error en obtenerOrdenCompraPorEgresoPk() ---> "+e.getMessage());
		}
		return lstOrdenCompra;
	}
	
}