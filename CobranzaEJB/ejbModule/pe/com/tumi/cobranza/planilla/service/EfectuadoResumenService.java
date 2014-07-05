package pe.com.tumi.cobranza.planilla.service;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.planilla.bo.CobroPlanillasBO;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoResumenBO;
import pe.com.tumi.cobranza.planilla.domain.CobroPlanillas;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalleInterfaz;


public class EfectuadoResumenService {

	protected static Logger log = Logger.getLogger(EfectuadoResumenService.class);
	
	EfectuadoResumenBO boEfectuadoResumen = (EfectuadoResumenBO)TumiFactory.get(EfectuadoResumenBO.class);
	CobroPlanillasBO boCobroPlanillas = (CobroPlanillasBO)TumiFactory.get(CobroPlanillasBO.class);

	public List<EfectuadoResumen> getListaEfectuadoResumenParaIngreso(Persona persona, Usuario usuario)throws BusinessException{
		List<EfectuadoResumen> listaEfectuadoResumen = null;
		try{
			Integer intIdEmpresa = usuario.getSucursal().getId().getIntPersEmpresaPk();
			Sucursal sucursalUsuario = usuario.getSucursal();
			Subsucursal subsucursalUsuario = usuario.getSubSucursal();
			
			List<EfectuadoResumen> listaEfectuadoResumenTemp = new ArrayList<EfectuadoResumen>();
			listaEfectuadoResumen = boEfectuadoResumen.getListaFaltaCancelar(intIdEmpresa);
			
			boolean pasaFiltroDetalle;
			for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
				pasaFiltroDetalle = Boolean.FALSE;
				Estructura estructura = getEstructuraPorEfectuadoResumen(efectuadoResumen);
				for(EstructuraDetalle estructuraDetalle : estructura.getListaEstructuraDetalle()){
					
					if(estructura.getIntPersPersonaPk().equals(persona.getIntIdPersona())
					&& estructuraDetalle.getId().getIntCaso().equals(Constante.PARAM_T_CASOESTRUCTURA_COBRA)
					&& estructuraDetalle.getIntParaTipoSocioCod().equals(efectuadoResumen.getIntTiposocioCod())
					&& estructuraDetalle.getIntParaModalidadCod().equals(efectuadoResumen.getIntModalidadCod())
					&& estructuraDetalle.getIntSeguSucursalPk().equals(sucursalUsuario.getId().getIntIdSucursal())
					&& estructuraDetalle.getIntSeguSubSucursalPk().equals(subsucursalUsuario.getId().getIntIdSubSucursal())){
						pasaFiltroDetalle = Boolean.TRUE;
					}
				}
				if(pasaFiltroDetalle)
					listaEfectuadoResumenTemp.add(efectuadoResumen);				
			}
			listaEfectuadoResumen = listaEfectuadoResumenTemp;
			
			//Obtenemos el monto cancelado hasta el momento de cada EfectuadoResumen
			for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
				efectuadoResumen.setListaCobroPlanillas(boCobroPlanillas.getPorEfectuadoResumen(efectuadoResumen));
				efectuadoResumen.setBdMontoDisponibelIngresar(efectuadoResumen.getBdMontoTotal());
				if(efectuadoResumen.getListaCobroPlanillas()==null) continue;
				BigDecimal bdMontoCanceladoHastaElMomento = new BigDecimal(0);
				for(CobroPlanillas cobroPlanillas : efectuadoResumen.getListaCobroPlanillas()){
					bdMontoCanceladoHastaElMomento = bdMontoCanceladoHastaElMomento.add(cobroPlanillas.getBdMontoPago());
				}
				efectuadoResumen.setBdMontoDisponibelIngresar(efectuadoResumen.getBdMontoTotal().subtract(bdMontoCanceladoHastaElMomento));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return listaEfectuadoResumen;
	}
	
	private Estructura getEstructuraPorEfectuadoResumen(EfectuadoResumen efectuadoResumen)throws Exception{
		EstructuraFacadeRemote estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
		
		EstructuraId estructuraId = new EstructuraId();
		estructuraId.setIntCodigo(efectuadoResumen.getIntCodigo());
		estructuraId.setIntNivel(efectuadoResumen.getIntNivel());
		return estructuraFacade.getEstructuraPorPk(estructuraId);
	}
	
	/**
	 * jchavez 19.06.2014
	 * Procedimiento que retorna lista de efectuados pendientes de pago, mostrando los montos totales despues
	 * de agruparlos por estructura detalle y sucursal procesa. Usado en Caja - Ingreso (planilla efectuada)
	 * @param estructuraComp
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public List<EfectuadoResumen> getListaEfectuadoResumenParaIngreso2(EstructuraComp estructuraComp, Usuario usuario)throws BusinessException{
		List<EfectuadoResumen> listaEfectuadoResumen = null;
		List<EfectuadoResumen> lstEfectuadoResumenTemp = new ArrayList<EfectuadoResumen>();
		try{
			Integer intIdEmpresa = usuario.getSucursal().getId().getIntPersEmpresaPk();
			Integer intTipoSocio = estructuraComp.getEstructuraDetalle().getIntParaTipoSocioCod();
			Integer intModalidad  = estructuraComp.getEstructuraDetalle().getIntParaModalidadCod();
			Integer intNivel = estructuraComp.getEstructuraDetalle().getId().getIntNivel();
			Integer intCodigo = estructuraComp.getEstructuraDetalle().getId().getIntCodigo();
			listaEfectuadoResumen = boEfectuadoResumen.getLstPendientesPorEnitdad(intIdEmpresa, intTipoSocio, intModalidad, intNivel, intCodigo);

			//Obtenemos el monto cancelado hasta el momento de cada EfectuadoResumen
			if (listaEfectuadoResumen!=null && !listaEfectuadoResumen.isEmpty()) {
				//obtenemos los pagos ya realizados para el efectuado
				for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
					efectuadoResumen.setListaCobroPlanillas(boCobroPlanillas.getPorEfectuadoResumen(efectuadoResumen));
					efectuadoResumen.setBdMontoDisponibelIngresar(efectuadoResumen.getBdMontoTotal());
					if(efectuadoResumen.getListaCobroPlanillas()==null) continue;
					BigDecimal bdMontoCanceladoHastaElMomento = new BigDecimal(0);
					for(CobroPlanillas cobroPlanillas : efectuadoResumen.getListaCobroPlanillas()){
						bdMontoCanceladoHastaElMomento = bdMontoCanceladoHastaElMomento.add(cobroPlanillas.getBdMontoPago());
					}
					efectuadoResumen.setBdMontoDisponibelIngresar(efectuadoResumen.getBdMontoTotal().subtract(bdMontoCanceladoHastaElMomento));
				}
				//agrupamos por periodo
				BigDecimal bdMontoTotalAcumulado = BigDecimal.ZERO;
				BigDecimal bdMontoIngresado = BigDecimal.ZERO;
				
				for (int i = 0; i < listaEfectuadoResumen.size(); i++) {
					EfectuadoResumen efectuadoResumenActual = null;
					EfectuadoResumen efectuadoResumenAnterior = null;
					if (i == 0) {
						efectuadoResumenAnterior = listaEfectuadoResumen.get(i);
					}else if (i > 0){
						efectuadoResumenAnterior = listaEfectuadoResumen.get(i-1);
						efectuadoResumenActual = listaEfectuadoResumen.get(i);
						
						if (efectuadoResumenActual.getIntPeriodoPlanilla().equals(efectuadoResumenAnterior.getIntPeriodoPlanilla())) {
							bdMontoTotalAcumulado = bdMontoTotalAcumulado.add(efectuadoResumenAnterior.getBdMontoTotal());
							
							if (efectuadoResumenAnterior.getListaCobroPlanillas()!=null && !efectuadoResumenAnterior.getListaCobroPlanillas().isEmpty()) {
								for (CobroPlanillas cobroPlanillas : efectuadoResumenAnterior.getListaCobroPlanillas()) {
									bdMontoIngresado = bdMontoIngresado.add(cobroPlanillas.getBdMontoPago());
								}
							}

						}else{
							bdMontoTotalAcumulado = bdMontoTotalAcumulado.add(efectuadoResumenAnterior.getBdMontoTotal());
							
							if (efectuadoResumenAnterior.getListaCobroPlanillas()!=null && !efectuadoResumenAnterior.getListaCobroPlanillas().isEmpty()) {
								for (CobroPlanillas cobroPlanillas : efectuadoResumenAnterior.getListaCobroPlanillas()) {
									bdMontoIngresado = bdMontoIngresado.add(cobroPlanillas.getBdMontoPago());
								}
							}

							efectuadoResumenAnterior.setBdMontoTotal(bdMontoTotalAcumulado);
							efectuadoResumenAnterior.setBdMontIngresar(bdMontoIngresado);
							lstEfectuadoResumenTemp.add(efectuadoResumenAnterior);
							bdMontoTotalAcumulado = BigDecimal.ZERO;
							bdMontoIngresado = BigDecimal.ZERO;
						}						
					}
					if (i == listaEfectuadoResumen.size()-1) {
						if (i == 0) {
							if (efectuadoResumenAnterior.getListaCobroPlanillas()!=null && !efectuadoResumenAnterior.getListaCobroPlanillas().isEmpty()) {
								for (CobroPlanillas cobroPlanillas : efectuadoResumenAnterior.getListaCobroPlanillas()) {
									bdMontoIngresado = bdMontoIngresado.add(cobroPlanillas.getBdMontoPago());
								}
							}
							efectuadoResumenAnterior.setBdMontIngresar(bdMontoIngresado);
							lstEfectuadoResumenTemp.add(efectuadoResumenAnterior);
							
						}else{
							if (efectuadoResumenActual.getIntPeriodoPlanilla().equals(efectuadoResumenAnterior.getIntPeriodoPlanilla())) {
								bdMontoTotalAcumulado = efectuadoResumenAnterior.getBdMontoTotal().add(efectuadoResumenActual.getBdMontoTotal());
								if ((efectuadoResumenActual.getListaCobroPlanillas()!=null && !efectuadoResumenActual.getListaCobroPlanillas().isEmpty()) 
										&& (efectuadoResumenAnterior.getListaCobroPlanillas()!=null && !efectuadoResumenAnterior.getListaCobroPlanillas().isEmpty())) {
									for (CobroPlanillas cobroPlanillas : efectuadoResumenAnterior.getListaCobroPlanillas()) {
										bdMontoIngresado = bdMontoIngresado.add(cobroPlanillas.getBdMontoPago());
									}
									for (CobroPlanillas cobroPlanillas : efectuadoResumenActual.getListaCobroPlanillas()) {
										bdMontoIngresado = bdMontoIngresado.add(cobroPlanillas.getBdMontoPago());
									}
								}
								efectuadoResumenAnterior.setBdMontIngresar(bdMontoIngresado);
								efectuadoResumenAnterior.setBdMontoTotal(bdMontoTotalAcumulado);
								lstEfectuadoResumenTemp.add(efectuadoResumenAnterior);
							}else{
								if (efectuadoResumenActual.getListaCobroPlanillas()!=null && !efectuadoResumenActual.getListaCobroPlanillas().isEmpty()){
									for (CobroPlanillas cobroPlanillas : efectuadoResumenActual.getListaCobroPlanillas()) {
										bdMontoIngresado = bdMontoIngresado.add(cobroPlanillas.getBdMontoPago());
									}
								}
								efectuadoResumenActual.setBdMontIngresar(bdMontoIngresado);
								lstEfectuadoResumenTemp.add(efectuadoResumenActual);
							}
						}						
					}
				}
			}
			
//			//Ordenamos por intOrden
//			Collections.sort(lstEfectuadoResumenTemp, new Comparator<EfectuadoResumen>(){
//				public int compare(EfectuadoResumen uno, EfectuadoResumen otro) {
//					return uno.getIntPeriodoPlanilla().compareTo(otro.getIntPeriodoPlanilla());
//				}
//			});

			log.info(lstEfectuadoResumenTemp);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return lstEfectuadoResumenTemp;
	}
}