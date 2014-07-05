package pe.com.tumi.servicio.solicitudPrestamo.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.domain.TipoCambioId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CancelacionCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.EstadoCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.ExpedienteCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
//import pe.com.tumi.tesoreria.egreso.domain.EgresoId;

public class EgresoPrestamoService {
	
	protected static Logger log = Logger.getLogger(EgresoPrestamoService.class);
	
	/**Valores para los tipo de egreso**/
	private final Integer	EGRESO_MONTOTOTAL = 1;
	private final Integer	EGRESO_GRAVAMEN = 2;
	private final Integer	EGRESO_APORTE = 3;
	private final Integer	EGRESO_CANCELADO = 4;
	private final Integer	EGRESO_FONDOFIJO = 5;
	
	private ExpedienteCreditoBO boExpedienteCredito = (ExpedienteCreditoBO)TumiFactory.get(ExpedienteCreditoBO.class);
	private EstadoCreditoBO boEstadoCredito = (EstadoCreditoBO)TumiFactory.get(EstadoCreditoBO.class);
	private CancelacionCreditoBO boCancelacionCredito = (CancelacionCreditoBO)TumiFactory.get(CancelacionCreditoBO.class);

	/**
	 * Recupera el Expediente Crédito por el código de la persona.
	 * @param intIdPersona
	 * @param intIdEmpresa
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCredito> obtenerExpedientePorIdPersonayIdEmpresa(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		List<ExpedienteCredito> listaExpedienteCredito = null;
		try{
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			listaExpedienteCredito = new ArrayList<ExpedienteCredito>();
			List<ExpedienteCredito> listaTemp = new ArrayList<ExpedienteCredito>();
			List<Cuenta> listaCuenta = new ArrayList<Cuenta>();	
			//Recupera Cuenta Integrante por Id persona
			List<CuentaIntegrante> listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(intIdPersona, intIdEmpresa);
			
			
			HashSet<Integer> hashSetIntCuenta = new HashSet<Integer>();
			for(CuentaIntegrante cuentaIntegrante : listaCuentaIntegrante){
				log.info("Cuenta Integrante Persona: "+cuentaIntegrante.getId().getIntPersonaIntegrante());
				hashSetIntCuenta.add(cuentaIntegrante.getId().getIntCuenta());
			}
			//Obetenemos las cuentas de tipo SOCIO
			for(Integer intCuenta : hashSetIntCuenta){
				log.info("Número de Cuenta: "+intCuenta);
				CuentaId cuentaId = new CuentaId();
				cuentaId.setIntPersEmpresaPk(intIdEmpresa);
				cuentaId.setIntCuenta(intCuenta);
				Cuenta cuenta = cuentaFacade.getCuentaPorId(cuentaId);
				if(cuenta!=null && cuenta.getIntParaTipoCuentaCod().equals(Constante.PARAM_T_TIPOCUENTAREQUISITOS_SOCIO))
					listaCuenta.add(cuenta);
			}
			//Obtenemos los expediente credito de dicha cuentas
			for(Cuenta cuenta : listaCuenta){
				List<ExpedienteCredito> listaExpedienteCreditoTemp = boExpedienteCredito.getListaPorCuenta(cuenta);
				for(ExpedienteCredito expedienteCredito : listaExpedienteCreditoTemp){
					expedienteCredito.setCuenta(cuenta);
					listaExpedienteCredito.add(expedienteCredito);
				}
			}
			
//			List<EstadoCredito> listaEstadoCredito = null;
//			boolean poseeEstadoAprobado;
			for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
				log.info("Expediente Crédito a consultar: "+expedienteCredito);
				//solo obtenemos los docs tipo prestamos
				if(!expedienteCredito.getIntParaDocumentoGeneralCod().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
					continue;
				}
				//obtenemos el ultimo estado para el expediente consultado
				EstadoCredito estadoCreditoUltimo = obtenerUltimoEstadoCredito(expedienteCredito);
				log.info("Ultimo Estado Crédito del expediente ["+expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente()+"]: "+estadoCreditoUltimo);
				
				Integer intParaEstadoCreditoFiltro = Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO;
				boolean pasaFiltroEstado = Boolean.FALSE;
				if(estadoCreditoUltimo!=null && estadoCreditoUltimo.getIntParaEstadoCreditoCod().equals(intParaEstadoCreditoFiltro)){
					pasaFiltroEstado = Boolean.TRUE;						
				}else if(estadoCreditoUltimo==null){
					pasaFiltroEstado = Boolean.TRUE;
				}
					
				if(pasaFiltroEstado){
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntIdSucursal(estadoCreditoUltimo.getIntIdUsuSucursalPk());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					log.info("Sucursal: "+sucursal);
					estadoCreditoUltimo.setSucursal(sucursal);
					estadoCreditoUltimo.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(estadoCreditoUltimo.getIntIdUsuSubSucursalPk()));
					expedienteCredito.setListaEstadoCredito(new ArrayList<EstadoCredito>());
					expedienteCredito.getListaEstadoCredito().add(estadoCreditoUltimo);
					listaTemp.add(expedienteCredito);
				}
			}
			listaExpedienteCredito = listaTemp;			
			
			for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
				log.info("doc:"+expedienteCredito);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaExpedienteCredito;
	}
	
	private EstadoCredito obtenerUltimoEstadoCredito(ExpedienteCredito expedienteCredito)throws BusinessException{
		EstadoCredito estadoCreditoUltima = new EstadoCredito();
		try{
			List<EstadoCredito> listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
			
			estadoCreditoUltima.setId(new EstadoCreditoId());
			estadoCreditoUltima.getId().setIntItemEstado(0);
			for(EstadoCredito estadoCredito : listaEstadoCredito){
				if(estadoCredito.getId().getIntItemEstado().compareTo(estadoCreditoUltima.getId().getIntItemEstado())>0){
					estadoCreditoUltima = estadoCredito;
				}
			}
			
			expedienteCredito.setListaEstadoCredito(listaEstadoCredito);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estadoCreditoUltima;
	}
	
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(ExpedienteCredito expedienteCredito) throws BusinessException{
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = new ArrayList<EgresoDetalleInterfaz>();
		try{
			BigDecimal bdSubTotal = expedienteCredito.getBdMontoTotal();
			
			EgresoDetalleInterfaz egresoDetalleInterfazComun = new EgresoDetalleInterfaz();
			egresoDetalleInterfazComun.setIntParaConcepto(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
			egresoDetalleInterfazComun.setStrNroSolicitud(""+expedienteCredito.getId().getIntItemExpediente());
			egresoDetalleInterfazComun.setBdCapital(expedienteCredito.getBdMontoTotal());		
			if(expedienteCredito.getBdMontoGravamen() != null){
				egresoDetalleInterfazComun.setBdGravamen(expedienteCredito.getBdMontoGravamen().negate());
				bdSubTotal = bdSubTotal.add(egresoDetalleInterfazComun.getBdGravamen());
			}
			if(expedienteCredito.getBdMontoAporte() != null){
				egresoDetalleInterfazComun.setBdAporte(expedienteCredito.getBdMontoAporte().negate());
				bdSubTotal = bdSubTotal.add(egresoDetalleInterfazComun.getBdAporte());
			}
			egresoDetalleInterfazComun.setBdSubTotal(bdSubTotal);
			listaEgresoDetalleInterfaz.add(egresoDetalleInterfazComun);
			
			
			if(expedienteCredito.getListaCancelacionCredito() != null){
				for(CancelacionCredito cancelacionCredito : expedienteCredito.getListaCancelacionCredito()){
					EgresoDetalleInterfaz egresoDetalleInterfazCancelacion = new EgresoDetalleInterfaz();
					egresoDetalleInterfazCancelacion.setIntParaConcepto(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
					egresoDetalleInterfazCancelacion.setStrNroSolicitud(""+cancelacionCredito.getIntItemExpediente());
					egresoDetalleInterfazCancelacion.setBdCapital(cancelacionCredito.getBdMontoCancelado().negate());
					egresoDetalleInterfazCancelacion.setBdInteres(expedienteCredito.getBdMontoInteresAtrasado().negate());
					egresoDetalleInterfazCancelacion.setBdSubTotal(egresoDetalleInterfazCancelacion.getBdCapital().add(egresoDetalleInterfazCancelacion.getBdInteres()));
					
					listaEgresoDetalleInterfaz.add(egresoDetalleInterfazCancelacion);
				}
			}			
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaEgresoDetalleInterfaz;
	}
	
	private SocioEstructura obtenerSocioEstructura(Persona persona, Integer intIdEmpresa) throws Exception{
		SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);		
		List<SocioEstructura> lista = socioFacade.getListaSocioEstrucuraPorIdPersona(persona.getIntIdPersona(), intIdEmpresa);		
		return lista.get(0);
	}
	
	private Credito obtenerCreditoPorExpediente(ExpedienteCredito expedienteCredito) throws Exception{
		CreditoFacadeRemote creditoFacade = (CreditoFacadeRemote) EJBFactory.getRemote(CreditoFacadeRemote.class);
		
		CreditoId creditoId = new CreditoId();
		creditoId.setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());
		creditoId.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
		creditoId.setIntItemCredito(expedienteCredito.getIntItemCredito());
		
		return creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
	}
	
	private void diferenciarModeloDeCancelado(List<Modelo> listaModelo) throws Exception{
		
		for(Modelo modeloTemp : listaModelo){
			modeloTemp.setEsModeloDeCancelado(Boolean.FALSE);
			List<ModeloDetalle> listaModelodeDetalle = modeloTemp.getListModeloDetalle();
			for(ModeloDetalle modeloDetalle : listaModelodeDetalle){
				for(ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel()){
					if(modeloDetalleNivel.getStrDescripcion().equals("CaCr_MontoCancelado_N")){
						modeloTemp.setEsModeloDeCancelado(Boolean.TRUE);
					}
					break;
				}
				break;
			}
		}
	}
	
	private ModeloDetalle obtenerModeloDetalleGiro(Modelo modelo, Credito credito, Integer intTipoEgreso){
		ModeloDetalle modeloDetalleTemp = new ModeloDetalle();
		modeloDetalleTemp.setId(new ModeloDetalleId());		
		
		ModeloDetalleNivel modeloDetalleNivel;
		if(intTipoEgreso.equals(EGRESO_MONTOTOTAL)){
			for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
				modeloDetalleNivel = modeloDetalle.getListModeloDetalleNivel().get(0);	
				if(modeloDetalleNivel.getIntDatoTablas() != null
				&& modeloDetalleNivel.getStrDescripcion() != null		
				&& modeloDetalleNivel.getIntDatoTablas().equals(credito.getIntParaTipoCreditoEmpresa())
				&& modeloDetalleNivel.getStrDescripcion().equals("ExCr_MontoTotal_N")){
					return modeloDetalle;
				}
			}			
		}else if(intTipoEgreso.equals(EGRESO_GRAVAMEN)){
			for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
				modeloDetalleNivel = modeloDetalle.getListModeloDetalleNivel().get(0);
				if(modeloDetalleNivel.getStrDescripcion() != null
				&& modeloDetalleNivel.getStrDescripcion().equals("ExCr_MontoGravamen_N")){
					return modeloDetalle;
				}
			}			
		}else if(intTipoEgreso.equals(EGRESO_APORTE)){
			for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
				modeloDetalleNivel = modeloDetalle.getListModeloDetalleNivel().get(0);
				if(modeloDetalleNivel.getStrDescripcion() != null
				&& modeloDetalleNivel.getStrDescripcion().equals("ExCr_MontoAporte_N")){
					return modeloDetalle;
				}
			}			
		}else if(intTipoEgreso.equals(EGRESO_CANCELADO)){
			for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){
				modeloDetalleNivel = modeloDetalle.getListModeloDetalleNivel().get(0);
				if(modeloDetalleNivel.getIntDatoTablas() != null
				&& modeloDetalleNivel.getStrDescripcion() != null		
				&& modeloDetalleNivel.getIntDatoTablas().equals(credito.getIntParaTipoCreditoEmpresa())
				&& modeloDetalleNivel.getStrDescripcion().equals("CaCr_MontoCancelado_N")){
					return modeloDetalle;
				}
			}
		}		
		return modeloDetalleTemp;
	}
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	 
	private TipoCambio obtenerTipoCambioActual(Integer intParaTipoMoneda, Integer IntIdEmpresa)throws Exception{
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		
		TipoCambioId tipoCambioId = new TipoCambioId();
		tipoCambioId.setIntPersEmpresa(IntIdEmpresa);
		tipoCambioId.setIntParaMoneda(intParaTipoMoneda);
		tipoCambioId.setDtParaFecha(obtenerFechaActual());
		tipoCambioId.setIntParaClaseTipoCambio(Constante.PARAM_T_TIPOCAMBIO_BANCARIO);
		
		return generalFacade.getTipoCambioPorPK(tipoCambioId);
	}
	
	private TipoCambio obtenerTipoCambioSolicitud(ExpedienteCredito expedienteCredito, Integer intParaTipoMoneda, Integer IntIdEmpresa) 
		throws Exception{
		GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		EstadoCredito estadoCreditoSolicitud = expedienteCredito.getEstadoCreditoUltimo();
		
		TipoCambioId tipoCambioId = new TipoCambioId();
		tipoCambioId.setIntPersEmpresa(IntIdEmpresa);
		tipoCambioId.setIntParaMoneda(intParaTipoMoneda);
		tipoCambioId.setDtParaFecha(estadoCreditoSolicitud.getTsFechaEstado());
		tipoCambioId.setIntParaClaseTipoCambio(Constante.PARAM_T_TIPOCAMBIO_BANCARIO);
		
		return generalFacade.getTipoCambioPorPK(tipoCambioId);
	}
	
	private Integer	obtenerPeriodoActual(){
		String strPeriodo = "";
		Calendar cal = Calendar.getInstance();
		int año = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		mes = mes + 1; 
		if(mes<10){
			strPeriodo = año + "0" + mes;
		}else{
			strPeriodo  = año + "" + mes;
		}		
		return Integer.parseInt(strPeriodo);		
	}
	
	private BigDecimal obtenerMontoDiferencial(BigDecimal bdMontoMonedaExtranjera, TipoCambio tipoCambioActual, TipoCambio tipoCambioSolicitud){
		BigDecimal bdMontoSolesSolicitud = bdMontoMonedaExtranjera.multiply(tipoCambioSolicitud.getBdPromedio());
		BigDecimal bdMontoSolesActual = bdMontoMonedaExtranjera.multiply(tipoCambioActual.getBdPromedio());
		
		return bdMontoSolesActual.subtract(bdMontoSolesSolicitud);
	}
	
	private EgresoDetalle generarEgresoDetalle(Integer intTipoEgreso, CancelacionCredito cancelacionCredito, 
			ModeloDetalle modeloDetalle, SocioEstructura socioEstructura, ExpedienteCredito expedienteCredito, Usuario usuario,
			TipoCambio tipoCambioActual, TipoCambio tipoCambioSolicitud, ControlFondosFijos controlFondosFijos)
		throws Exception{
		Credito credito = expedienteCredito.getCuenta().getCredito();
		boolean esMonedaExtranjera;
		
		Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();
		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		egresoDetalle.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
		egresoDetalle.setStrDescripcionEgreso(modeloDetalle.getPlanCuenta().getStrDescripcion());
		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalle.setIntPersonaGirado(expedienteCredito.getPersonaGirar().getIntIdPersona());
		egresoDetalle.setIntCuentaGirado(expedienteCredito.getCuenta().getId().getIntCuenta());
		egresoDetalle.setIntSucuIdSucursalEgreso(socioEstructura.getIntIdSucursalAdministra());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(socioEstructura.getIntIdSubsucurAdministra());
		egresoDetalle.setIntParaTipoMoneda(credito.getIntParaMonedaCod());
		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			esMonedaExtranjera = Boolean.FALSE;
		}else{
			esMonedaExtranjera = Boolean.TRUE;
		}
		
		if(intTipoEgreso.equals(EGRESO_MONTOTOTAL)){
			if(expedienteCredito.getBdMontoTotal()==null) throw new Exception();
			egresoDetalle.setBdMontoCargo(expedienteCredito.getBdMontoTotal());
			if(esMonedaExtranjera)
			egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
		
		}else if(intTipoEgreso.equals(EGRESO_GRAVAMEN)){
			if(expedienteCredito.getBdMontoGravamen()==null) return null;
			egresoDetalle.setBdMontoAbono(expedienteCredito.getBdMontoGravamen());
			if(esMonedaExtranjera) 
			egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono(), tipoCambioActual, tipoCambioSolicitud));
			
		}else if(intTipoEgreso.equals(EGRESO_APORTE)){
			if(expedienteCredito.getBdMontoAporte()==null) return null;
			egresoDetalle.setBdMontoAbono(expedienteCredito.getBdMontoAporte());
			if(esMonedaExtranjera) 
			egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono(), tipoCambioActual, tipoCambioSolicitud));
			
		}else if(intTipoEgreso.equals(EGRESO_CANCELADO)){
			if(cancelacionCredito.getBdMontoCancelado()==null) return null;
			egresoDetalle.setBdMontoAbono(cancelacionCredito.getBdMontoCancelado());
			if(esMonedaExtranjera) 
			egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono(), tipoCambioActual, tipoCambioSolicitud));
		}
		
		egresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egresoDetalle.setTsFechaRegistro(obtenerFechaActual());
		egresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
		egresoDetalle.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
		egresoDetalle.setIntPersEmpresaLibroDestino(null);
		egresoDetalle.setIntContPeriodoLibroDestino(null);
		egresoDetalle.setIntContCodigoLibroDestino(null);
		egresoDetalle.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
		egresoDetalle.setIntContPeriodoCuenta(modeloDetalle.getId().getIntContPeriodoCuenta());
		egresoDetalle.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
		if(controlFondosFijos!=null){
			egresoDetalle.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			egresoDetalle.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			egresoDetalle.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
			egresoDetalle.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());			
		}		
		return egresoDetalle;
	}
	
	private PlanCuenta obtenerPlanCuentaOrigenCFF(ControlFondosFijos controlFondosFijos) throws Exception{		
		
		LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
		
		Egreso egresoCFF = controlFondosFijos.getEgreso();
		PlanCuenta planCuenta = new PlanCuenta();
		planCuenta.setId(new PlanCuentaId());
		LibroDiarioId libroDiarioId = new LibroDiarioId();
		libroDiarioId.setIntPersEmpresaLibro(egresoCFF.getIntPersEmpresaLibro());
		libroDiarioId.setIntContPeriodoLibro(egresoCFF.getIntContPeriodoLibro());
		libroDiarioId.setIntContCodigoLibro(egresoCFF.getIntContCodigoLibro());
		
		LibroDiario libroDiario = libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);
		List<LibroDiarioDetalle> listaLibroDiarioDetalle = libroDiarioFacade.getListaLibroDiarioDetallePorLibroDiario(libroDiario);
		
		for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle){
			if(libroDiarioDetalle.getBdDebeSoles()!=null || libroDiarioDetalle.getBdDebeExtranjero()!=null){
				planCuenta.getId().setIntEmpresaCuentaPk(libroDiarioDetalle.getIntPersEmpresaCuenta());
				planCuenta.getId().setIntPeriodoCuenta(libroDiarioDetalle.getIntContPeriodo());
				planCuenta.getId().setStrNumeroCuenta(libroDiarioDetalle.getStrContNumeroCuenta());
			}
		}
		
		return planCuenta;
	}
	

	
	private LibroDiarioDetalle generarLibroDiarioDetalleCheque(Integer intTipoEgreso, CancelacionCredito cancelacionCredito, ModeloDetalle modeloDetalle, 
			SocioEstructura socioEstructura, ExpedienteCredito expedienteCredito, Bancocuenta bancoCuenta, TipoCambio tipoCambioActual)
		throws Exception{
		
		Credito credito = expedienteCredito.getCuenta().getCredito();
		Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
		
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		//CGD-12.10.2013
		libroDiarioDetalle.setId(new LibroDiarioDetalleId());
		libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		
		libroDiarioDetalle.setIntPersPersona(expedienteCredito.getPersonaGirar().getIntIdPersona());
		if(intTipoEgreso.equals(EGRESO_MONTOTOTAL)
		|| intTipoEgreso.equals(EGRESO_GRAVAMEN)
		|| intTipoEgreso.equals(EGRESO_APORTE)
		|| intTipoEgreso.equals(EGRESO_CANCELADO)){
			libroDiarioDetalle.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
			libroDiarioDetalle.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
		}else if(intTipoEgreso.equals(EGRESO_FONDOFIJO)){
			libroDiarioDetalle.setIntPersEmpresaCuenta(bancoCuenta.getId().getIntEmpresaPk());
			libroDiarioDetalle.setIntContPeriodo(bancoCuenta.getIntPeriodocuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(bancoCuenta.getStrNumerocuenta());			
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
		}
		
		libroDiarioDetalle.setStrSerieDocumento(null);
		if(intTipoEgreso.equals(EGRESO_MONTOTOTAL)
			|| intTipoEgreso.equals(EGRESO_GRAVAMEN)
			|| intTipoEgreso.equals(EGRESO_APORTE) 
			|| intTipoEgreso.equals(EGRESO_CANCELADO)){

			libroDiarioDetalle.setStrNumeroDocumento(""+expedienteCredito.getId().getIntItemExpediente());
			libroDiarioDetalle.setIntPersEmpresaSucursal(socioEstructura.getIntEmpresaSucUsuario());
			libroDiarioDetalle.setIntSucuIdSucursal(socioEstructura.getIntIdSucursalUsuario());
			libroDiarioDetalle.setIntSudeIdSubSucursal(socioEstructura.getIntIdSubSucursalUsuario());
			
		}else if(intTipoEgreso.equals(EGRESO_FONDOFIJO)){
			libroDiarioDetalle.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
			libroDiarioDetalle.setIntSucuIdSucursal(Constante.PARAM_SUCURSALSESION);
			libroDiarioDetalle.setIntSudeIdSubSucursal(Constante.PARAM_SUBSUCURSALSESION);			
		}
		
		libroDiarioDetalle.setIntParaMonedaDocumento(credito.getIntParaMonedaCod());
		if(!libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
		}
		
		if(libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			if(intTipoEgreso.equals(EGRESO_MONTOTOTAL)){
				libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoTotal());
			
			}else if(intTipoEgreso.equals(EGRESO_GRAVAMEN)){
				if(expedienteCredito.getBdMontoGravamen()==null) return null;
				libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoGravamen());
			
			}else if(intTipoEgreso.equals(EGRESO_APORTE)){
				if(expedienteCredito.getBdMontoAporte()==null) return null;
				libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoAporte());
			
			}else if(intTipoEgreso.equals(EGRESO_CANCELADO)){
				if(cancelacionCredito.getBdMontoCancelado()==null) return null;
				libroDiarioDetalle.setBdHaberSoles(cancelacionCredito.getBdMontoCancelado());			
			
			}else if(intTipoEgreso.equals(EGRESO_FONDOFIJO)){
				libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoSolicitado());
			}
			
		}else{
			/******cambio en soles ******/
			if(intTipoEgreso.equals(EGRESO_MONTOTOTAL)){
				libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoTotal().multiply(tipoCambioActual.getBdCompra()));
				libroDiarioDetalle.setBdDebeExtranjero(expedienteCredito.getBdMontoTotal());
			
			}else if(intTipoEgreso.equals(EGRESO_GRAVAMEN)){
				if(expedienteCredito.getBdMontoGravamen()==null) return null;
				libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoGravamen().multiply(tipoCambioActual.getBdCompra()));
				libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoGravamen());
			
			}else if(intTipoEgreso.equals(EGRESO_APORTE)){
				if(expedienteCredito.getBdMontoAporte()==null) return null;
				libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoAporte().multiply(tipoCambioActual.getBdCompra()));
				libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoAporte());
			
			}else if(intTipoEgreso.equals(EGRESO_CANCELADO)){
				if(cancelacionCredito.getBdMontoCancelado()==null) return null;
				libroDiarioDetalle.setBdHaberSoles(cancelacionCredito.getBdMontoCancelado().multiply(tipoCambioActual.getBdCompra()));
				libroDiarioDetalle.setBdHaberExtranjero(cancelacionCredito.getBdMontoCancelado());
			
			}else if(intTipoEgreso.equals(EGRESO_FONDOFIJO)){
				libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoSolicitado().multiply(tipoCambioActual.getBdCompra()));
				libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoSolicitado());
			}
		}
		return libroDiarioDetalle;
	}
	

	
//	public Egreso generarEgresoPrestamoCheque(ExpedienteCredito expedienteCredito, Bancocuenta bancoCuenta, Usuario usuario, 
//		Integer intNumeroCheque, Integer intTipoDocumentoValidar) throws BusinessException{
//		Egreso egreso = new Egreso();
//		try{
//			
//			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);			
//			
//			Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
//			Persona personaGirar = expedienteCredito.getPersonaGirar();		
//			Persona personaApoderado = expedienteCredito.getPersonaApoderado();
//			Archivo archivoCartaPoder = expedienteCredito.getArchivoCartaPoder();
//			String strGlosaEgreso = expedienteCredito.getStrGlosaEgreso();
//			
//			log.info(personaGirar);
//			log.info(personaApoderado);
//			log.info(archivoCartaPoder);
//			
//			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
//			Credito creditoAsociado = obtenerCreditoPorExpediente(expedienteCredito);
//			expedienteCredito.getCuenta().setCredito(creditoAsociado);
//			if(expedienteCredito.getListaCancelacionCredito()==null)expedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());
//			
//			
//			List<Modelo> listaModeloGiro = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CREDITO, intIdEmpresa);
//			diferenciarModeloDeCancelado(listaModeloGiro);
//			Modelo modeloCancelado = null;
//			Modelo modelo = null;
//			for(Modelo modeloTemp : listaModeloGiro){
//				if(modeloTemp.isEsModeloDeCancelado()) modeloCancelado = modeloTemp;
//				else	modelo = modeloTemp;
//			}
//			log.info(modelo);
//			log.info(modeloCancelado);
//			ModeloDetalle modeloDetalleCancelado = null;
//			ModeloDetalle modeloDetalleMontoTotal = obtenerModeloDetalleGiro(modelo, creditoAsociado, EGRESO_MONTOTOTAL);
//			ModeloDetalle modeloDetalleGravamen = obtenerModeloDetalleGiro(modelo, creditoAsociado, EGRESO_GRAVAMEN);
//			ModeloDetalle modeloDetalleAporte = obtenerModeloDetalleGiro(modelo, creditoAsociado, EGRESO_APORTE);
//			
//			if (modeloCancelado!=null) {
//				modeloDetalleCancelado = obtenerModeloDetalleGiro(modeloCancelado, creditoAsociado, EGRESO_CANCELADO);
//				
//			}			
//			
//			log.info("modeloDetalleMontoTotal:"+modeloDetalleMontoTotal);
//			log.info("modeloDetalleGravamen:"+modeloDetalleGravamen);
//			log.info("modeloDetalleAporte:"+modeloDetalleAporte);
//			log.info("modeloDetalleCancelado:"+modeloDetalleCancelado);		
//			
//			TipoCambio tipoCambioActual = null;			
//			TipoCambio tipoCambioSolicitud = null;			
//			if(!bancoCuenta.getCuentaBancaria().getIntMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
//				tipoCambioActual = obtenerTipoCambioActual(creditoAsociado.getIntParaMonedaCod(),intIdEmpresa);			
//				tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteCredito, creditoAsociado.getIntParaMonedaCod(), intIdEmpresa);
//				
//			}
//			
//			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
//			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
//			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
//			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
//			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
//			egreso.setIntItemEgreso(null);
//			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
//			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
//			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
//			egreso.setTsFechaProceso(obtenerFechaActual());
//			egreso.setDtFechaEgreso(obtenerFechaActual());
//			egreso.setIntParaTipoFondoFijo(null);
//			egreso.setIntItemPeriodoFondo(null);
//			egreso.setIntItemFondoFijo(null);
//			egreso.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
//			egreso.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
//			egreso.setIntItemBancoCuentaCheque(null);
//			egreso.setIntNumeroPlanilla(null);
//			egreso.setIntNumeroCheque(intNumeroCheque);
//			egreso.setIntNumeroTransferencia(null);
//			egreso.setTsFechaPagoDiferido(null);
//			egreso.setIntPersEmpresaGirado(intIdEmpresa);
//			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
//			egreso.setIntCuentaGirado(expedienteCredito.getCuenta().getId().getIntCuenta());
//			//20.05.2014 jchavez cambio de tipo de dato
//			egreso.setStrPersCuentaBancariaGirado(null);	
////			egreso.setIntPersCuentaBancariaGirado(null);
//			egreso.setIntPersEmpresaBeneficiario(null);
//			egreso.setIntPersPersonaBeneficiario(null);
//			egreso.setIntPersCuentaBancariaBeneficiario(null);
//			if(personaApoderado != null){
//				egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
//				egreso.setIntPersEmpresaApoderado(intIdEmpresa);
//			}
//			egreso.setBdMontoTotal(expedienteCredito.getBdMontoSolicitado());
//			egreso.setStrObservacion(strGlosaEgreso);
//			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//			egreso.setTsFechaRegistro(obtenerFechaActual());
//			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
//			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
//			egreso.setIntPersEmpresaEgresoAnula(null);
//			egreso.setIntPersPersonaEgresoAnula(null);
//			if(archivoCartaPoder != null){
//				egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
//				egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
//				egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
//			}
//			egreso.setIntParaTipoGiro(null);
//			egreso.setIntItemArchivoGiro(null);
//			egreso.setIntItemHistoricoGiro(null);
//			
//			
//			EgresoDetalle egresoDetalleMontoTotal = generarEgresoDetalle(EGRESO_MONTOTOTAL, null, modeloDetalleMontoTotal, 
//					socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
//			
//			EgresoDetalle egresoDetalleGravamen = generarEgresoDetalle(EGRESO_GRAVAMEN, null, modeloDetalleGravamen,
//					socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null); 
//			
//			EgresoDetalle egresoDetalleAporte = generarEgresoDetalle(EGRESO_APORTE, null, modeloDetalleAporte, 
//					socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
//			
//			for(CancelacionCredito cancelacionCredito : expedienteCredito.getListaCancelacionCredito()){
//				EgresoDetalle egresoDetalleCancelado = generarEgresoDetalle(EGRESO_CANCELADO, cancelacionCredito, modeloDetalleCancelado,
//					socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
//				
//				if(egresoDetalleCancelado != null) egreso.getListaEgresoDetalle().add(egresoDetalleCancelado);
//			}
//				
//			
//			if(egresoDetalleMontoTotal != null) egreso.getListaEgresoDetalle().add(egresoDetalleMontoTotal);
//			if(egresoDetalleGravamen != null) egreso.getListaEgresoDetalle().add(egresoDetalleGravamen);
//			if(egresoDetalleAporte != null) egreso.getListaEgresoDetalle().add(egresoDetalleAporte);			
//			
//			
//			LibroDiario libroDiario = new LibroDiario();
//			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
//			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
//			libroDiario.setStrGlosa(strGlosaEgreso);
//			libroDiario.setTsFechaRegistro(obtenerFechaActual());
//			libroDiario.setTsFechaDocumento(obtenerFechaActual());
//			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
//			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
//			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
//			
//			
//			LibroDiarioDetalle libroDiarioDetalleMontoTotal = generarLibroDiarioDetalleCheque(EGRESO_MONTOTOTAL, null, modeloDetalleMontoTotal,
//					socioEstructura, expedienteCredito, bancoCuenta, tipoCambioActual); 
//			LibroDiarioDetalle libroDiarioDetalleGravamen = generarLibroDiarioDetalleCheque(EGRESO_GRAVAMEN, null, modeloDetalleGravamen,
//					socioEstructura, expedienteCredito, bancoCuenta, tipoCambioActual); 
//			LibroDiarioDetalle libroDiarioDetalleAporte = generarLibroDiarioDetalleCheque(EGRESO_APORTE, null, modeloDetalleAporte,
//					socioEstructura, expedienteCredito, bancoCuenta, tipoCambioActual); 
//			for(CancelacionCredito cancelacionCredito : expedienteCredito.getListaCancelacionCredito()){
//				LibroDiarioDetalle libroDiarioDetalleCancelado = generarLibroDiarioDetalleCheque(EGRESO_CANCELADO, cancelacionCredito, modeloDetalleCancelado,
//						socioEstructura, expedienteCredito, bancoCuenta, tipoCambioActual); 
//				if(libroDiarioDetalleCancelado != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleCancelado);
//			}
//			LibroDiarioDetalle libroDiarioDetalleFondoFijo = generarLibroDiarioDetalleCheque(EGRESO_FONDOFIJO, null,null,
//					socioEstructura, expedienteCredito, bancoCuenta, tipoCambioActual); 
//			
//			
//			if(libroDiarioDetalleMontoTotal != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleMontoTotal);
//			if(libroDiarioDetalleGravamen != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleGravamen);
//			if(libroDiarioDetalleAporte != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleAporte);			
//			if(libroDiarioDetalleFondoFijo != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleFondoFijo);
//			
//			egreso.setLibroDiario(libroDiario);
//			egreso.setControlFondosFijos(null);
//		}catch(Exception e){
//			throw new BusinessException(e);
//		}
//		return egreso;
//	}	
	
//	public Egreso generarEgresoPrestamoTransferencia(ExpedienteCredito expedienteCredito, Bancocuenta bancoCuentaOrigen, Usuario usuario, 
//		Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino) throws BusinessException{
//		Egreso egreso = new Egreso();
//		try{
//			//CGD-12.102013
//			egreso.setId(new EgresoId());
//			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);			
//			
//			Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
//			Persona personaGirar = expedienteCredito.getPersonaGirar();		
//			Persona personaApoderado = expedienteCredito.getPersonaApoderado();
//			Archivo archivoCartaPoder = expedienteCredito.getArchivoCartaPoder();
//			String strGlosaEgreso = expedienteCredito.getStrGlosaEgreso();
//			
//			log.info(personaGirar);
//			log.info(personaApoderado);
//			log.info(archivoCartaPoder);
//			
//			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
//			Credito creditoAsociado = obtenerCreditoPorExpediente(expedienteCredito);
//			expedienteCredito.getCuenta().setCredito(creditoAsociado);
//			if(expedienteCredito.getListaCancelacionCredito()==null)expedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());
//			
//			
//			List<Modelo> listaModeloGiro = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CREDITO, intIdEmpresa);
//			diferenciarModeloDeCancelado(listaModeloGiro);
//			Modelo modeloCancelado = null;
//			Modelo modelo = null;
//			for(Modelo modeloTemp : listaModeloGiro){
//				if(modeloTemp.isEsModeloDeCancelado()) modeloCancelado = modeloTemp;
//				else	modelo = modeloTemp;
//			}
//			log.info(modelo);
//			log.info(modeloCancelado);
//			ModeloDetalle modeloDetalleMontoTotal = obtenerModeloDetalleGiro(modelo, creditoAsociado, EGRESO_MONTOTOTAL);
//			ModeloDetalle modeloDetalleGravamen = obtenerModeloDetalleGiro(modelo, creditoAsociado, EGRESO_GRAVAMEN);
//			ModeloDetalle modeloDetalleAporte = obtenerModeloDetalleGiro(modelo, creditoAsociado, EGRESO_APORTE);
//			ModeloDetalle modeloDetalleCancelado = obtenerModeloDetalleGiro(modeloCancelado, creditoAsociado, EGRESO_CANCELADO);
//			
//			log.info("modeloDetalleMontoTotal:"+modeloDetalleMontoTotal);
//			log.info("modeloDetalleGravamen:"+modeloDetalleGravamen);
//			log.info("modeloDetalleAporte:"+modeloDetalleAporte);
//			log.info("modeloDetalleCancelado:"+modeloDetalleCancelado);			
//			
//			TipoCambio tipoCambioActual = null;			
//			TipoCambio tipoCambioSolicitud = null;			
//			if(!bancoCuentaOrigen.getCuentaBancaria().getIntMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
//				tipoCambioActual = obtenerTipoCambioActual(creditoAsociado.getIntParaMonedaCod(),intIdEmpresa);			
//				tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteCredito, creditoAsociado.getIntParaMonedaCod(), intIdEmpresa);
//				
//			}
//			
//			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
//			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
//			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
//			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
//			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
//			egreso.setIntItemEgreso(null);
//			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
//			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
//			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
//			egreso.setTsFechaProceso(obtenerFechaActual());
//			egreso.setDtFechaEgreso(obtenerFechaActual());
//			egreso.setIntParaTipoFondoFijo(null);
//			egreso.setIntItemPeriodoFondo(null);
//			egreso.setIntItemFondoFijo(null);
//			egreso.setIntItemBancoFondo(bancoCuentaOrigen.getId().getIntItembancofondo());
//			egreso.setIntItemBancoCuenta(bancoCuentaOrigen.getId().getIntItembancocuenta());
//			egreso.setIntItemBancoCuentaCheque(null);
//			egreso.setIntNumeroPlanilla(null);
//			egreso.setIntNumeroCheque(null);
//			egreso.setIntNumeroTransferencia(intNumeroTransferencia);
//			egreso.setTsFechaPagoDiferido(null);
//			egreso.setIntPersEmpresaGirado(intIdEmpresa);
//			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
//			egreso.setIntCuentaGirado(expedienteCredito.getCuenta().getId().getIntCuenta());
//			egreso.setIntPersCuentaBancariaGirado(cuentaBancariaDestino.getId().getIntIdCuentaBancaria());
//			egreso.setIntPersEmpresaBeneficiario(null);
//			egreso.setIntPersPersonaBeneficiario(null);
//			egreso.setIntPersCuentaBancariaBeneficiario(null);
//			if(personaApoderado != null){
//				egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
//				egreso.setIntPersEmpresaApoderado(intIdEmpresa);
//			}
//			egreso.setBdMontoTotal(expedienteCredito.getBdMontoSolicitado());
//			egreso.setStrObservacion(strGlosaEgreso);
//			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//			egreso.setTsFechaRegistro(obtenerFechaActual());
//			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
//			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
//			egreso.setIntPersEmpresaEgresoAnula(null);
//			egreso.setIntPersPersonaEgresoAnula(null);
//			if(archivoCartaPoder != null){
//				egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
//				egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
//				egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
//			}
//			egreso.setIntParaTipoGiro(null);
//			egreso.setIntItemArchivoGiro(null);
//			egreso.setIntItemHistoricoGiro(null);
//			
//			
//			EgresoDetalle egresoDetalleMontoTotal = generarEgresoDetalle(EGRESO_MONTOTOTAL, null, modeloDetalleMontoTotal, 
//					socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
//			
//			EgresoDetalle egresoDetalleGravamen = generarEgresoDetalle(EGRESO_GRAVAMEN, null, modeloDetalleGravamen,
//					socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null); 
//			
//			EgresoDetalle egresoDetalleAporte = generarEgresoDetalle(EGRESO_APORTE, null, modeloDetalleAporte, 
//					socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
//			
//			for(CancelacionCredito cancelacionCredito : expedienteCredito.getListaCancelacionCredito()){
//				EgresoDetalle egresoDetalleCancelado = generarEgresoDetalle(EGRESO_CANCELADO, cancelacionCredito, modeloDetalleCancelado,
//					socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
//				
//				if(egresoDetalleCancelado != null) egreso.getListaEgresoDetalle().add(egresoDetalleCancelado);
//			}
//				
//			
//			if(egresoDetalleMontoTotal != null) egreso.getListaEgresoDetalle().add(egresoDetalleMontoTotal);
//			if(egresoDetalleGravamen != null) egreso.getListaEgresoDetalle().add(egresoDetalleGravamen);
//			if(egresoDetalleAporte != null) egreso.getListaEgresoDetalle().add(egresoDetalleAporte);			
//			
//			
//			LibroDiario libroDiario = new LibroDiario();
//			//CGD-12.10.2013
//			libroDiario.setId(new LibroDiarioId());
//			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
//			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
//			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
//			libroDiario.setStrGlosa(strGlosaEgreso);
//			libroDiario.setTsFechaRegistro(obtenerFechaActual());
//			libroDiario.setTsFechaDocumento(obtenerFechaActual());
//			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
//			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
//			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
//			
//			
//			LibroDiarioDetalle libroDiarioDetalleMontoTotal = generarLibroDiarioDetalleCheque(EGRESO_MONTOTOTAL, null, modeloDetalleMontoTotal,
//					socioEstructura, expedienteCredito, bancoCuentaOrigen, tipoCambioActual); 
//			LibroDiarioDetalle libroDiarioDetalleGravamen = generarLibroDiarioDetalleCheque(EGRESO_GRAVAMEN, null, modeloDetalleGravamen,
//					socioEstructura, expedienteCredito, bancoCuentaOrigen, tipoCambioActual); 
//			LibroDiarioDetalle libroDiarioDetalleAporte = generarLibroDiarioDetalleCheque(EGRESO_APORTE, null, modeloDetalleAporte,
//					socioEstructura, expedienteCredito, bancoCuentaOrigen, tipoCambioActual); 
//			for(CancelacionCredito cancelacionCredito : expedienteCredito.getListaCancelacionCredito()){
//				LibroDiarioDetalle libroDiarioDetalleCancelado = generarLibroDiarioDetalleCheque(EGRESO_CANCELADO, cancelacionCredito, modeloDetalleCancelado,
//						socioEstructura, expedienteCredito, bancoCuentaOrigen, tipoCambioActual); 
//				if(libroDiarioDetalleCancelado != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleCancelado);
//			}
//			LibroDiarioDetalle libroDiarioDetalleFondoFijo = generarLibroDiarioDetalleCheque(EGRESO_FONDOFIJO, null,null,
//					socioEstructura, expedienteCredito, bancoCuentaOrigen, tipoCambioActual); 
//			
//			
//			if(libroDiarioDetalleMontoTotal != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleMontoTotal);
//			if(libroDiarioDetalleGravamen != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleGravamen);
//			if(libroDiarioDetalleAporte != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleAporte);			
//			if(libroDiarioDetalleFondoFijo != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleFondoFijo);
//			
//			egreso.setLibroDiario(libroDiario);
//			egreso.setControlFondosFijos(null);
//		}catch(Exception e){
//			throw new BusinessException(e);
//		}
//		return egreso;
//	}
//	
	
	private LibroDiarioDetalle generarLibroDiarioDetalle(ModeloDetalleNivelComp o, SocioEstructura socioEstructura, 
			ExpedienteCredito expedienteCredito, ControlFondosFijos controlFondosFijos, TipoCambio tipoCambioActual)
		throws Exception{
		
		LibroDiarioDetalle libroDiarioDetalle = null;
		Credito credito = expedienteCredito.getCuenta().getCredito();
		Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
		PlanCuentaId pId = new PlanCuentaId();
		try {
			PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			libroDiarioDetalle = new LibroDiarioDetalle();
			libroDiarioDetalle.setId(new LibroDiarioDetalleId());
			libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			
			libroDiarioDetalle.setIntPersPersona(expedienteCredito.getPersonaGirar().getIntIdPersona());
			libroDiarioDetalle.setIntPersEmpresaCuenta(o.getIntEmpresaCuenta());
			libroDiarioDetalle.setIntContPeriodo(o.getIntPeriodoCuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(o.getStrNumeroCuenta());
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
			
			//Descripción del Plan de Cuenta
			pId.setIntEmpresaCuentaPk(o.getIntEmpresaCuenta());
			pId.setStrNumeroCuenta(o.getStrNumeroCuenta());
			pId.setIntPeriodoCuenta(o.getIntPeriodoCuenta());
			libroDiarioDetalle.setStrComentario(planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().length()<20?planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion():planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().substring(0, 20));
			
			libroDiarioDetalle.setIntPersEmpresaSucursal(socioEstructura.getIntEmpresaSucUsuario());
			libroDiarioDetalle.setIntSucuIdSucursal(socioEstructura.getIntIdSucursalUsuario());
			libroDiarioDetalle.setIntSudeIdSubSucursal(socioEstructura.getIntIdSubSucursalUsuario());

			if(credito.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOL)==0){
				libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOL);
			}else{
				libroDiarioDetalle.setIntParaMonedaDocumento(credito.getIntParaMonedaCod());
			}
			if (tipoCambioActual.getId().getIntParaClaseTipoCambio()==null) tipoCambioActual.getId().setIntParaClaseTipoCambio(1);
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			
			if(libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
				if (o.getStrCampoConsumir().equalsIgnoreCase("ExCr_MontoTotal_N")) {
					if(expedienteCredito.getBdMontoTotal()==null) return null;
					if (o.getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoTotal());
					if (o.getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoTotal());
					libroDiarioDetalle.setStrNumeroDocumento(""+expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
				}
				if (o.getStrCampoConsumir().equalsIgnoreCase("ExCr_MontoGravamen_N")) {
					if(expedienteCredito.getBdMontoGravamen()==null) return null;
					if (o.getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoGravamen());
					if (o.getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoGravamen());
					libroDiarioDetalle.setStrNumeroDocumento(""+expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
				}
				if (o.getStrCampoConsumir().equalsIgnoreCase("ExCr_MontoAporte_N")) {
					if(expedienteCredito.getBdMontoAporte()==null) return null;
					if (o.getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoAporte());
					if (o.getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoAporte());
					libroDiarioDetalle.setStrNumeroDocumento(""+expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
				}
				if (o.getStrCampoConsumir().equalsIgnoreCase("CaCr_MontoCancelado_N")) {
					for (CancelacionCredito expCredCancelar : expedienteCredito.getListaCancelacionCredito()) {
						if(expCredCancelar.getBdMontoCancelado()==null) return null;
						if (o.getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeSoles(expCredCancelar.getBdMontoCancelado());
						if (o.getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberSoles(expCredCancelar.getBdMontoCancelado());
						libroDiarioDetalle.setStrNumeroDocumento(""+expCredCancelar.getIntItemExpediente()+"-"+expCredCancelar.getIntItemDetExpediente());
					}
				}
				if (o.getStrCampoConsumir().equalsIgnoreCase("Excr_MontoInteresAtrasado_N")) {
					if(expedienteCredito.getBdMontoInteresAtrasado()==null) return null;
					if (o.getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoInteresAtrasado());
					if (o.getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoInteresAtrasado());
					libroDiarioDetalle.setStrNumeroDocumento(""+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()+"-"+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
				}	
			}else{
				/******cambio en soles ******/
				if (o.getStrCampoConsumir().equalsIgnoreCase("ExCr_MontoTotal_N")) {
					if (o.getIntParamDebeHaber().equals(1)){
						libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoTotal().multiply(tipoCambioActual.getBdCompra()));
						libroDiarioDetalle.setBdDebeExtranjero(expedienteCredito.getBdMontoTotal());
					}
					if (o.getIntParamDebeHaber().equals(2)){
						libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoTotal().multiply(tipoCambioActual.getBdCompra()));
						libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoTotal());
					}		
					libroDiarioDetalle.setStrNumeroDocumento(""+expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
				}
				if(o.getStrCampoConsumir().equalsIgnoreCase("ExCr_MontoGravamen_N")) {
					if(expedienteCredito.getBdMontoGravamen()==null) return null;
					if (o.getIntParamDebeHaber().equals(1)){
						libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoGravamen().multiply(tipoCambioActual.getBdCompra()));
						libroDiarioDetalle.setBdDebeExtranjero(expedienteCredito.getBdMontoGravamen());
					}
					if (o.getIntParamDebeHaber().equals(2)){
						libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoGravamen().multiply(tipoCambioActual.getBdCompra()));
						libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoGravamen());
					}
					libroDiarioDetalle.setStrNumeroDocumento(""+expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
				}
				if(o.getStrCampoConsumir().equalsIgnoreCase("ExCr_MontoAporte_N")) {
					if(expedienteCredito.getBdMontoAporte()==null) return null;
					if (o.getIntParamDebeHaber().equals(1)){
						libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoAporte().multiply(tipoCambioActual.getBdCompra()));
						libroDiarioDetalle.setBdDebeExtranjero(expedienteCredito.getBdMontoAporte());
					}
					if (o.getIntParamDebeHaber().equals(2)){
						libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoAporte().multiply(tipoCambioActual.getBdCompra()));
						libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoAporte());
					}
					libroDiarioDetalle.setStrNumeroDocumento(""+expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
				}
				if(o.getStrCampoConsumir().equalsIgnoreCase("CaCr_MontoCancelado_N")){
					for (CancelacionCredito expCredCancelar : expedienteCredito.getListaCancelacionCredito()) {
						if(expCredCancelar.getBdMontoCancelado()==null) return null;
						if (o.getIntParamDebeHaber().equals(1)){
							libroDiarioDetalle.setBdDebeSoles(expCredCancelar.getBdMontoCancelado().multiply(tipoCambioActual.getBdCompra()));
							libroDiarioDetalle.setBdDebeExtranjero(expCredCancelar.getBdMontoCancelado());
						}
						if (o.getIntParamDebeHaber().equals(2)){
							libroDiarioDetalle.setBdHaberSoles(expCredCancelar.getBdMontoCancelado().multiply(tipoCambioActual.getBdCompra()));
							libroDiarioDetalle.setBdHaberExtranjero(expCredCancelar.getBdMontoCancelado());
						}
						libroDiarioDetalle.setStrNumeroDocumento(""+expCredCancelar.getIntItemExpediente()+"-"+expCredCancelar.getIntItemDetExpediente());
					}
				}
				if (o.getStrCampoConsumir().equalsIgnoreCase("Excr_MontoInteresAtrasado_N")) {
					if(expedienteCredito.getBdMontoInteresAtrasado()==null) return null;
					if (o.getIntParamDebeHaber().equals(1)){
						libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoInteresAtrasado().multiply(tipoCambioActual.getBdCompra()));
						libroDiarioDetalle.setBdDebeExtranjero(expedienteCredito.getBdMontoInteresAtrasado());
					}
					if (o.getIntParamDebeHaber().equals(2)){
						libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoInteresAtrasado().multiply(tipoCambioActual.getBdCompra()));
						libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoInteresAtrasado());
					}
					libroDiarioDetalle.setStrNumeroDocumento(""+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()+"-"+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
				}	
			}
		} catch (Exception e) {
			log.error("Error en generarLibroDiarioDetalle --> "+e);
		}
		
		return libroDiarioDetalle;
	}


	/**
	 * Genera el egreso cuando se realiza el giro por la Sede Central
	 * Usado para giros por cheques
	 * @param expedienteCredito
	 * @param bancoCuenta
	 * @param usuario
	 * @param intNumeroCheque
	 * @param intTipoDocumentoValidar
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoPrestamoGiroPorSedeCentral(ExpedienteCredito expedienteCredito, Bancocuenta bancoCuenta, Usuario usuario,
			Integer intNumeroCheque, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino)	throws BusinessException{ //ControlFondosFijos controlFondosFijos,
		Egreso egreso = new Egreso();
		Calendar cal = Calendar.getInstance();
		Integer anioActual = cal.get(Calendar.YEAR);
		List<CancelacionCredito> lstExpCreditoACancelar = null;
		ExpedienteCredito expCreditoACancelar = new ExpedienteCredito();
		expCreditoACancelar.setId(new ExpedienteCreditoId());
		Integer intCatergoriaRiesgo = null;
		List<ExpedienteCredito> lstCategoriaRiesgo = null;
		List<ModeloDetalleNivelId> lstModeloReprestamo = null;
		List<ModeloDetalleNivelComp> listaModeloGiroAux = null;
		List<ModeloDetalleNivelComp> listaModeloGiroCancelar = new ArrayList<ModeloDetalleNivelComp>();
		
		try{			
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			
			Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
			Persona personaGirar = expedienteCredito.getPersonaGirar();		
			Persona personaApoderado = expedienteCredito.getPersonaApoderado();
			Archivo archivoCartaPoder = expedienteCredito.getArchivoCartaPoder();
			String strGlosaEgreso = expedienteCredito.getStrGlosaEgreso();
			
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Persona a Girar: "+personaGirar);
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Apoderado: "+personaApoderado);
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Carta Poder: "+archivoCartaPoder);
			
			// Se obtiene SocioEstructura por persona a girar prestamo
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			if (socioEstructura==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Socio Estructura retorna nulo.");
				return egreso;
			}
			// Se obtiene Credtio Asociado por ParaTipoCredito e ItemCredito
			Credito creditoAsociado = obtenerCreditoPorExpediente(expedienteCredito);
			if (creditoAsociado==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Credito Asociado retorna nulo.");
				return egreso;
			}
			expedienteCredito.getCuenta().setCredito(creditoAsociado);
			// Se valida Cancelacion Credito si el subtipo operacion es REPRESTAMO(2)
			if(expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) expedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());
			
			//Se recupera Modelo Detalle Nivel, tanto para un préstamo nuevo como un represtamo (Parametro 151)
			ModeloDetalleNivel modeloFiltro = new ModeloDetalleNivel();
			modeloFiltro.setId(new ModeloDetalleNivelId());
			modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
			//modeloFiltro.getId().setIntCodigoModelo(Constante.PARAM_CODIGOMODELO_GIROCREDITOS);
			
			modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
			modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
			modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_CREDITO);
			modeloFiltro.setIntDatoTablas(expedienteCredito.getIntParaTipoCreditoCod());
			modeloFiltro.setIntDatoArgumento(expedienteCredito.getIntItemCredito());
			List<ModeloDetalleNivelComp> listaModeloGiro = modeloFacade.getModeloGiroPrestamo(modeloFiltro);
			if (listaModeloGiro==null || listaModeloGiro.isEmpty()) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel retorna nulo.");
				return egreso;
			}
			expedienteCredito.setListaModeloDetalleNivelComp(listaModeloGiro);
			
			/* Si el subtipo operación del préstamo es REPRESTAMO, se realiza los mismos pasos que para un prestamo nuevo, adicionalmente
			se generarán 2 movimiento más, uno correspondiente al SALDO DEL PRESTAMO A CANCELAR y otro al INTERES del mismo. El primer 
			campo se obtinene de la tabla CSE_CANCELACIONCREDITO y el segundo de la tabla CSE_EXPEDIENTE_CREDITO (tener en cuenta que 
			este campo está en el EXCR_MONTOINTERESATRASADO_N del credito nuevo que se está dando, no del que se está cancelando).
			*/
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
				// 1. Recuperamos la llave del Expediente Crédito que se irá a cancelar, esta se encuentra en Cancelacion Credito
				lstExpCreditoACancelar = boCancelacionCredito.getListaPorExpedienteCredito(expedienteCredito);
				if (lstExpCreditoACancelar!=null && !lstExpCreditoACancelar.isEmpty()) {
					for (CancelacionCredito expCredACancelar : lstExpCreditoACancelar) {
						expCreditoACancelar.getId().setIntPersEmpresaPk(expCredACancelar.getId().getIntPersEmpresaPk());
						expCreditoACancelar.getId().setIntCuentaPk(expCredACancelar.getId().getIntCuentaPk());
						expCreditoACancelar.getId().setIntItemExpediente(expCredACancelar.getIntItemExpediente());
						expCreditoACancelar.getId().setIntItemDetExpediente(expCredACancelar.getIntItemDetExpediente());
						expedienteCredito.getListaCancelacionCredito().add(expCredACancelar);
						break;
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. REPRESTAMO no cuenta con préstamo a cancelar.");
					return egreso;
				}
				
				// 2. Recuperamos los datos del Expediente Crédito que se irá a cancelar
				expCreditoACancelar = boExpedienteCredito.getPorPk(expCreditoACancelar.getId());
				// 3. Vamos al esquema de RIESGO para recuperar la categoría de riesgo (PARA_TIPOCATEGORIARIESGO_N_COD) de la 
				// tabla CRI_CARTERACREDITODETALLE, tener en cuenta que se obtendra el ultimo registro activo que se encuentre. 
				// (CRI_CARTERACREDITO campos: CACR_FECHAREGISTRO_D y PARA_ESTADO_N_COD)
				lstCategoriaRiesgo = boExpedienteCredito.getRiesgoExpCredACancelar(expCreditoACancelar.getId());
				if (lstCategoriaRiesgo!=null && !lstCategoriaRiesgo.isEmpty()) {
					for (ExpedienteCredito categoriaRiesgo : lstCategoriaRiesgo) {
						intCatergoriaRiesgo = categoriaRiesgo.getIntParaTipoCategoriaRiesgo();
						break;
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. REPRESTAMO no cuenta con Categoría de Riesgo.");
					return egreso;
				}
				
				// 4. Vamos a Modelo Detalle Nivel y se realiza un cruce: obtener registros con la categoria de riesgo (PARA_TIPOCATEGORIARIESGO_N_COD) 
				// y con el tipo credito(CSER_ITEMEXPEDIENTE_N Y CSER_ITEMDETEXPEDIENTE) del préstamo a cancelar, de la intersección de dichas búsquedas
				// se obtendrá las cuentas a registrar en el Libro Diario.
				ModeloDetalleNivel modeloFiltro2 = new ModeloDetalleNivel();
				modeloFiltro2.setId(new ModeloDetalleNivelId());
				modeloFiltro2.getId().setIntEmpresaPk(intIdEmpresa);
				modeloFiltro2.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_REPRESTAMO);
				modeloFiltro2.getId().setIntPersEmpresaCuenta(intIdEmpresa);
				modeloFiltro2.getId().setIntContPeriodoCuenta(anioActual);
				lstModeloReprestamo = modeloFacade.getPkModeloXReprestamo(modeloFiltro2, expCreditoACancelar.getIntParaTipoCreditoCod(), expCreditoACancelar.getIntItemCredito(), intCatergoriaRiesgo);
				// 5. Un vez obtenido los modelos, recuperamos el resitro que nos dará el campo a usar para la grabación del Libro Diario
				if (lstModeloReprestamo!=null && !lstModeloReprestamo.isEmpty()) {
					for (ModeloDetalleNivelId modReprestamo : lstModeloReprestamo) {
						modeloFiltro2.getId().setStrContNumeroCuenta(modReprestamo.getStrContNumeroCuenta());
						listaModeloGiroAux = modeloFacade.getCampoXPkModelo(modeloFiltro2);
						for (ModeloDetalleNivelComp o : listaModeloGiroAux) {
							listaModeloGiroCancelar.add(o);
							listaModeloGiroAux = new ArrayList<ModeloDetalleNivelComp>();
						}
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel para Represtamo retorna nulo.");
					return egreso;
				}
				
				expCreditoACancelar.setListaModeloDetalleNivelComp(listaModeloGiroCancelar);				
				expedienteCredito.setExpedienteCreditoCancelacion(expCreditoACancelar);
			}
	
			TipoCambio tipoCambioActual = null;
			TipoCambio tipoCambioSolicitud = null;
			if(creditoAsociado.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)==0){
				tipoCambioActual = new TipoCambio();
				tipoCambioActual.setId(new TipoCambioId());
				tipoCambioActual.getId().setIntParaClaseTipoCambio(new Integer(1));
				tipoCambioActual.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
				tipoCambioActual.getId().setIntPersEmpresa(2);
				tipoCambioActual.setBdCompra(BigDecimal.ONE);
				tipoCambioActual.setBdPromedio(BigDecimal.ONE);
				tipoCambioActual.setBdVenta(BigDecimal.ONE);
				
				tipoCambioSolicitud = new TipoCambio();
				tipoCambioSolicitud.setId(new TipoCambioId());
				tipoCambioSolicitud.getId().setIntParaClaseTipoCambio(new Integer(1));
				tipoCambioSolicitud.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
				tipoCambioSolicitud.getId().setIntPersEmpresa(2);
				tipoCambioSolicitud.setBdCompra(BigDecimal.ONE);
				tipoCambioSolicitud.setBdPromedio(BigDecimal.ONE);
				tipoCambioSolicitud.setBdVenta(BigDecimal.ONE);			
			}else{
				tipoCambioActual = obtenerTipoCambioActual(creditoAsociado.getIntParaMonedaCod(),intIdEmpresa);			
				tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteCredito, creditoAsociado.getIntParaMonedaCod(), intIdEmpresa);
			}
			
			//Generación Cabecera Egreso
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
//			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
//			egreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_CHEQUE);
			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			egreso.setTsFechaProceso(obtenerFechaActual());
			egreso.setDtFechaEgreso(obtenerFechaActual());
			egreso.setIntParaTipoFondoFijo(null);
			egreso.setIntItemPeriodoFondo(null);
			egreso.setIntItemFondoFijo(null);
			egreso.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
			egreso.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
			egreso.setIntItemBancoCuentaCheque(null);
			/*
			egreso.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			egreso.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			egreso.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
			egreso.setIntItemBancoFondo(null);
			egreso.setIntItemBancoCuenta(null);
			egreso.setIntItemBancoCuentaCheque(null);
			*/
			egreso.setIntNumeroPlanilla(null);
			//egreso.setIntNumeroCheque(null);
			egreso.setIntNumeroCheque(intNumeroCheque);
			egreso.setIntNumeroTransferencia(null);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
			egreso.setIntCuentaGirado(expedienteCredito.getCuenta().getId().getIntCuenta());
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(cuentaBancariaDestino!=null?cuentaBancariaDestino.getStrNroCuentaBancaria():null);	
//			egreso.setIntPersCuentaBancariaGirado(cuentaBancariaDestino!=null?cuentaBancariaDestino.getId().getIntIdCuentaBancaria():null);
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			if(personaApoderado != null){
				egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
				egreso.setIntPersEmpresaApoderado(intIdEmpresa);
			}
			egreso.setBdMontoTotal(expedienteCredito.getBdMontoSolicitado());
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(obtenerFechaActual());
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			if(archivoCartaPoder != null){
				egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
				egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
				egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
			}
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			//Generación del Egreso Detalle del nuevo préstamo
			if (expedienteCredito.getListaModeloDetalleNivelComp()!=null && !expedienteCredito.getListaModeloDetalleNivelComp().isEmpty()) {
				for (ModeloDetalleNivelComp o : expedienteCredito.getListaModeloDetalleNivelComp()) {
					EgresoDetalle egresoDetalleGiro = generarEgresoDetallePrestamo(o, socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
					if (egresoDetalleGiro!=null) {
						egreso.getListaEgresoDetalle().add(egresoDetalleGiro);
					}else{
						egreso.setIntErrorGeneracionEgreso(1);
						egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Egreso Detalle retorna nulo.");
						return egreso;
					}				
				}
			}
			//Generación del Egreso Detalle del préstamo a cancelar
			if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
				for (ModeloDetalleNivelComp o : expedienteCredito.getExpedienteCreditoCancelacion().getListaModeloDetalleNivelComp()) {
					EgresoDetalle egresoDetalleGiro = generarEgresoDetallePrestamo(o, socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
					if (egresoDetalleGiro!=null) {
						egreso.getListaEgresoDetalle().add(egresoDetalleGiro);
					}else{
						egreso.setIntErrorGeneracionEgreso(1);
						egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Egreso Detalle retorna nulo.");
						return egreso;
					}
				}
			}
			
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa(strGlosaEgreso);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			libroDiario.setIntParaTipoDocumentoGeneral(intTipoDocumentoValidar);			
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			
			//Generación del Libro Diario Detalle del nuevo préstamo
			if (expedienteCredito.getListaModeloDetalleNivelComp()!=null && !expedienteCredito.getListaModeloDetalleNivelComp().isEmpty()) {
				for (ModeloDetalleNivelComp o : expedienteCredito.getListaModeloDetalleNivelComp()) {
					LibroDiarioDetalle libroDiarioDetalleGiro = generarLibroDiarioDetalle(o, socioEstructura, expedienteCredito, null, tipoCambioActual);
					if (libroDiarioDetalleGiro!=null) {
						libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleGiro);
					}else{
						egreso.setIntErrorGeneracionEgreso(1);
						egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Libro Diario Detalle retorna nulo.");
						return egreso;
					}
				}				
			}
			//Generación del Libro Diario Detalle del préstamo a cancelar
			if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
				for (ModeloDetalleNivelComp o : expedienteCredito.getExpedienteCreditoCancelacion().getListaModeloDetalleNivelComp()) {
					LibroDiarioDetalle libroDiarioDetalleGiro = generarLibroDiarioDetalle(o, socioEstructura, expedienteCredito, null, tipoCambioActual);
					if (libroDiarioDetalleGiro!=null) {
						libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleGiro);
					}else{
						egreso.setIntErrorGeneracionEgreso(1);
						egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Libro Diario Detalle retorna nulo.");
						return egreso;
					}
				}
			}
			//Generación del Libro Diario Detalle BANCO
			LibroDiarioDetalle libroDiarioDetalleBancoPorCheque = generarBancoLibroDiarioDetalle(expedienteCredito, bancoCuenta, tipoCambioActual, intTipoDocumentoValidar); 			
			if(libroDiarioDetalleBancoPorCheque != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBancoPorCheque);
			
			egreso.setLibroDiario(libroDiario);
			//egreso.setControlFondosFijos(controlFondosFijos);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		egreso.setIntErrorGeneracionEgreso(0);
		egreso.setStrMsgErrorGeneracionEgreso("Generacion satisfactoria de Egreso, Egreso Detalle, Libro Diario, Libro Diario Detalle.");
		return egreso;
	}
	
	/**
	 * Genera el egreso detalle cuando el giro se realiza por la Sede Central.
	 * @param o
	 * @param socioEstructura
	 * @param expedienteCredito
	 * @param usuario
	 * @param tipoCambioActual
	 * @param tipoCambioSolicitud
	 * @return
	 * @throws Exception
	 */
//	private EgresoDetalle generarEgresoDetalleGiroPorSedeCentral(ModeloDetalleNivelComp o, SocioEstructura socioEstructura, ExpedienteCredito expedienteCredito, Usuario usuario,
//			TipoCambio tipoCambioActual, TipoCambio tipoCambioSolicitud)
//		throws Exception{
//		expedienteCredito.setGeneraAporte(false);
//		Credito credito = expedienteCredito.getCuenta().getCredito();
//		boolean esMonedaExtranjera;
//		
//		Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
//		
//		EgresoDetalle egresoDetalle = new EgresoDetalle();
//		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
//		egresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
//		egresoDetalle.setIntParaTipoComprobante(null);
//		egresoDetalle.setStrSerieDocumento(null);
//		egresoDetalle.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
//		egresoDetalle.setStrDescripcionEgreso(o.getStrDescCuenta());
//		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
//		egresoDetalle.setIntPersonaGirado(expedienteCredito.getPersonaGirar().getIntIdPersona());
//		egresoDetalle.setIntCuentaGirado(expedienteCredito.getCuenta().getId().getIntCuenta());
//		egresoDetalle.setIntSucuIdSucursalEgreso(socioEstructura.getIntIdSucursalAdministra());
//		egresoDetalle.setIntSudeIdSubsucursalEgreso(socioEstructura.getIntIdSubsucurAdministra());
//		egresoDetalle.setIntParaTipoMoneda(credito.getIntParaMonedaCod());
//		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
//			esMonedaExtranjera = Boolean.FALSE;
//		}else{
//			esMonedaExtranjera = Boolean.TRUE;
//		}
//		if (o.getStrCampoConsumir().equals("ExCr_MontoTotal_N")) {
//			if(expedienteCredito.getBdMontoTotal()==null) return null;
//			if (o.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(expedienteCredito.getBdMontoTotal());
//			if (o.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(expedienteCredito.getBdMontoTotal());	
//			if(esMonedaExtranjera)
//			egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
//		}
//		if (o.getStrCampoConsumir().equals("ExCr_MontoAporte_N")) {
//			if(expedienteCredito.getBdMontoAporte()==null) return null;
//			if (o.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(expedienteCredito.getBdMontoAporte());
//			if (o.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(expedienteCredito.getBdMontoAporte());
//			if(esMonedaExtranjera) 
//			egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
//			expedienteCredito.setGeneraAporte(true);
//		}
//		if (o.getStrCampoConsumir().equals("ExCr_MontoGravamen_N")) {
//			if(expedienteCredito.getBdMontoGravamen()==null) return null;
//			if (o.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(expedienteCredito.getBdMontoGravamen());
//			if (o.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(expedienteCredito.getBdMontoGravamen());
//			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
//		}
//		if (o.getStrCampoConsumir().equals("CaCr_MontoCancelado_N")) {
//			for (CancelacionCredito expCredCancelar : expedienteCredito.getListaCancelacionCredito()) {
//				if(expCredCancelar.getBdMontoCancelado()==null) return null;
//				if (o.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(expCredCancelar.getBdMontoCancelado());
//				if (o.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(expCredCancelar.getBdMontoCancelado());
//				if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
//			}
//		}
//		if (o.getStrCampoConsumir().equals("Excr_MontoInteresAtrasado_N")) {
//			if(expedienteCredito.getBdMontoInteresAtrasado()==null) return null;
//			if (o.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(expedienteCredito.getBdMontoInteresAtrasado());
//			if (o.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(expedienteCredito.getBdMontoInteresAtrasado());	
//			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
//		}		
//		
//		egresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//		egresoDetalle.setTsFechaRegistro(obtenerFechaActual());
//		egresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
//		egresoDetalle.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
//		egresoDetalle.setIntPersEmpresaLibroDestino(null);
//		egresoDetalle.setIntContPeriodoLibroDestino(null);
//		egresoDetalle.setIntContCodigoLibroDestino(null);
//		egresoDetalle.setIntPersEmpresaCuenta(o.getIntEmpresaCuenta());
//		egresoDetalle.setIntContPeriodoCuenta(o.getIntPeriodoCuenta());
//		egresoDetalle.setStrContNumeroCuenta(o.getStrNumeroCuenta());
//		
//		egresoDetalle.setIntParaTipoFondoFijo(null);
//		egresoDetalle.setIntItemPeriodoFondo(null);
//		egresoDetalle.setIntSucuIdSucursal(null);
//		egresoDetalle.setIntItemFondoFijo(null);			
//
//		return egresoDetalle;
//	}
	
	/*******************************************************************
	 * MODIFICACIONES PARA REALIZAR GIRO POR :
	 * SERVICIO - GIRO NORMAL
	 * TESORERIA - FONDO FIJO Y BANCO (CHEQUES Y TRANSFERENCIA TERCEROS)
	 * AUTOR: JUNIOR CHAVEZ
	 * FECHA: 15.05.2014
	 *******************************************************************/
	
	/**
	 * GIRO PRESTAMO - REPRESTAMO POR SERVICIO. TAMBIEN APLICA EN GIRO POR FONDO FIJO 
	 */
	
	/**
	 * MODIFICACION: JCHAVEZ 02.01.2014
	 * Generación del egreso del prestamo, usado en GiroCreditoController, proceso de grabación
	 * @param expedienteCredito
	 * @param controlFondosFijos
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoPrestamo(ExpedienteCredito expedienteCredito, ControlFondosFijos controlFondosFijos, Usuario usuario) throws BusinessException{
		Egreso egreso = new Egreso();
		Calendar cal = Calendar.getInstance();
		Integer anioActual = cal.get(Calendar.YEAR);
		List<CancelacionCredito> lstExpCreditoACancelar = null;
		ExpedienteCredito expCreditoACancelar = new ExpedienteCredito();
		expCreditoACancelar.setId(new ExpedienteCreditoId());
		Integer intCatergoriaRiesgoCredACancelar = null;
		List<ExpedienteCredito> lstCategoriaRiesgo = null;
		List<ModeloDetalleNivelId> lstModeloReprestamo = null;
		List<ModeloDetalleNivelId> lstModeloReprestamo2 = null;
		List<ModeloDetalleNivelComp> listaModeloGiroAux = null;
		List<ModeloDetalleNivelComp> listaModeloGiroAux2 = null;
		List<ModeloDetalleNivelComp> listaModeloGiroReprestamo = new ArrayList<ModeloDetalleNivelComp>();

		try{			
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			
			Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
			Persona personaGirar = expedienteCredito.getPersonaGirar();		
			Persona personaApoderado = expedienteCredito.getPersonaApoderado();
			Archivo archivoCartaPoder = expedienteCredito.getArchivoCartaPoder();
			String strGlosaEgreso = expedienteCredito.getStrGlosaEgreso();
			
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Persona a Girar: "+personaGirar);
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Apoderado: "+personaApoderado);
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Carta Poder: "+archivoCartaPoder);
			
			// Se obtiene SocioEstructura por persona a girar prestamo
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			if (socioEstructura==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Socio Estructura retorna nulo.");
				return egreso;
			}
			// Se obtiene Credtio Asociado por ParaTipoCredito e ItemCredito
			Credito creditoAsociado = obtenerCreditoPorExpediente(expedienteCredito);
			if (creditoAsociado==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Credito Asociado retorna nulo.");
				return egreso;
			}
			expedienteCredito.getCuenta().setCredito(creditoAsociado);
			// Se valida Cancelacion Credito si el subtipo operacion es REPRESTAMO(2)
			if(expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) expedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());

			//Se recupera Modelo Detalle Nivel, tanto para un préstamo nuevo como un represtamo (Parametro 151)
			ModeloDetalleNivel modeloFiltro = new ModeloDetalleNivel();
			modeloFiltro.setId(new ModeloDetalleNivelId());
			modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
			modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
			modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
			/*
			  Modificacion 11.04.2014 JCHAVEZ
			  Lo primero que debemos obtener es el modelo para el represtamo, para esto la busqueda se realiza como un prestamo 
			  cualquiera, pero variando el tipo de modelo contable por el que se filtrará.
			*/
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO)) modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_CREDITO);
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_REPRESTAMO);
			//FIN MODIFICACION 11.04.2014			
			
			modeloFiltro.setIntDatoTablas(expedienteCredito.getIntParaTipoCreditoCod());
			modeloFiltro.setIntDatoArgumento(expedienteCredito.getIntItemCredito());
			List<ModeloDetalleNivelComp> listaModeloGiro = modeloFacade.getModeloGiroPrestamo(modeloFiltro);
			if (listaModeloGiro==null || listaModeloGiro.isEmpty()) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel retorna nulo.");
				return egreso;
			}
			expedienteCredito.setListaModeloDetalleNivelComp(listaModeloGiro);

			/* Si el subtipo operación del préstamo es REPRESTAMO, se realiza los mismos pasos que para un prestamo nuevo, adicionalmente
			   se generarán 2 movimiento más, uno correspondiente al SALDO DEL PRESTAMO A CANCELAR y otro al INTERES del mismo. El primer 
			   campo se obtinene de la tabla CSE_CANCELACIONCREDITO y el segundo de la tabla CSE_EXPEDIENTE_CREDITO (tener en cuenta que 
			   este campo está en el EXCR_MONTOINTERESATRASADO_N del credito nuevo que se está dando, no del que se está cancelando).
			 */
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
				// 1. Recuperamos la llave del Expediente Crédito que se irá a cancelar, esta se encuentra en Cancelacion Credito
				lstExpCreditoACancelar = boCancelacionCredito.getListaPorExpedienteCredito(expedienteCredito);
				if (lstExpCreditoACancelar!=null && !lstExpCreditoACancelar.isEmpty()) {
					for (CancelacionCredito expCredACancelar : lstExpCreditoACancelar) {
						expCreditoACancelar.getId().setIntPersEmpresaPk(expCredACancelar.getId().getIntPersEmpresaPk());
						expCreditoACancelar.getId().setIntCuentaPk(expCredACancelar.getId().getIntCuentaPk());
						expCreditoACancelar.getId().setIntItemExpediente(expCredACancelar.getIntItemExpediente());
						expCreditoACancelar.getId().setIntItemDetExpediente(expCredACancelar.getIntItemDetExpediente());
						expedienteCredito.getListaCancelacionCredito().add(expCredACancelar);
						break;
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. REPRESTAMO no cuenta con préstamo a cancelar.");
					return egreso;
				}
				
				// 2. Recuperamos los datos del Expediente Crédito que se irá a cancelar
				expCreditoACancelar = boExpedienteCredito.getPorPk(expCreditoACancelar.getId());
				// 3. Vamos al esquema de RIESGO para recuperar la categoría de riesgo (PARA_TIPOCATEGORIARIESGO_N_COD) de la 
				// tabla CRI_CARTERACREDITODETALLE, tener en cuenta que se obtendra el ultimo registro activo que se encuentre. 
				// (CRI_CARTERACREDITO campos: CACR_FECHAREGISTRO_D y PARA_ESTADO_N_COD)
				lstCategoriaRiesgo = boExpedienteCredito.getRiesgoExpCredACancelar(expCreditoACancelar.getId());
				if (lstCategoriaRiesgo!=null && !lstCategoriaRiesgo.isEmpty()) {
					for (ExpedienteCredito categoriaRiesgo : lstCategoriaRiesgo) {
						intCatergoriaRiesgoCredACancelar = categoriaRiesgo.getIntParaTipoCategoriaRiesgo();
						break;
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. REPRESTAMO no cuenta con Categoría de Riesgo.");
					return egreso;
				}
				
				// 4. Vamos a Modelo Detalle Nivel y se realiza un cruce: obtener registros con la categoria de riesgo (PARA_TIPOCATEGORIARIESGO_N_COD) 
				// y con el tipo credito(CSER_ITEMEXPEDIENTE_N Y CSER_ITEMDETEXPEDIENTE) del préstamo a cancelar, de la intersección de dichas búsquedas
				// se obtendrá las cuentas a registrar en el Libro Diario. (EN TEORIA, SEGUN REUNIONES, DEBERIA DAR UNA SOLA CUENTA)
				ModeloDetalleNivel modeloFiltro2 = new ModeloDetalleNivel();
				modeloFiltro2.setId(new ModeloDetalleNivelId());
				modeloFiltro2.getId().setIntEmpresaPk(intIdEmpresa);
				modeloFiltro2.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_REPRESTAMO);
				modeloFiltro2.getId().setIntPersEmpresaCuenta(intIdEmpresa);
				modeloFiltro2.getId().setIntContPeriodoCuenta(anioActual);
				lstModeloReprestamo = modeloFacade.getPkModeloXReprestamo(modeloFiltro2, expCreditoACancelar.getIntParaTipoCreditoCod(), expCreditoACancelar.getIntItemCredito(), intCatergoriaRiesgoCredACancelar);
				// 5. Un vez obtenido los modelos, recuperamos el resitro que nos dará el campo a usar para la grabación del Libro Diario
				//Limpiamos la lista de arriba...
				for (ModeloDetalleNivelComp list : listaModeloGiro) {
					if (!(list.getStrCampoConsumir().equalsIgnoreCase("CaCr_MontoCancelado_N"))) listaModeloGiroReprestamo.add(list);
				}
				if (lstModeloReprestamo!=null && !lstModeloReprestamo.isEmpty()) {
					for (ModeloDetalleNivelId modReprestamo : lstModeloReprestamo) {
						modeloFiltro2.getId().setStrContNumeroCuenta(modReprestamo.getStrContNumeroCuenta());
						listaModeloGiroAux = modeloFacade.getCampoXPkModelo(modeloFiltro2);
						for (ModeloDetalleNivelComp o : listaModeloGiroAux) {
							listaModeloGiroReprestamo.add(o);
						}
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel para Represtamo retorna nulo.");
					return egreso;
				}
				//Hasta este punto, tengo la cuenta del aporte, gravamen y monto, cancelado. El monto solicitado y el
				//monto total usaran la misma cuenta q el cancelado, si su riesgo es vigente, caso contrario  el que se
				//irá a cancelar toma una cuenta de vencido y el represtamo toma la cuenta de vigente.
				ModeloDetalleNivel modeloFiltro3 = new ModeloDetalleNivel();
				modeloFiltro3.setId(new ModeloDetalleNivelId());
				modeloFiltro3.getId().setIntEmpresaPk(intIdEmpresa);
				modeloFiltro3.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_REPRESTAMO);
				modeloFiltro3.getId().setIntPersEmpresaCuenta(intIdEmpresa);
				modeloFiltro3.getId().setIntContPeriodoCuenta(anioActual);
				lstModeloReprestamo2 = modeloFacade.getPkModeloXReprestamo(modeloFiltro3, expedienteCredito.getIntParaTipoCreditoCod(), expedienteCredito.getIntItemCredito(), Constante.PARAM_T_TIPOCATEGORIADERIESGO_NORMAL);
				for (ModeloDetalleNivelId modGiroReprestamo : lstModeloReprestamo2) {
					modeloFiltro3.getId().setStrContNumeroCuenta(modGiroReprestamo.getStrContNumeroCuenta());
					listaModeloGiroAux2 = modeloFacade.getCampoXPkModelo(modeloFiltro3);
					for (ModeloDetalleNivelComp o : listaModeloGiroAux2) {						
						ModeloDetalleNivelComp modeloEnDuroMntoTotal = new ModeloDetalleNivelComp();
						modeloEnDuroMntoTotal.setIntEmpresa(o.getIntEmpresa());
						modeloEnDuroMntoTotal.setIntCodigoModelo(o.getIntCodigoModelo());
						modeloEnDuroMntoTotal.setIntEmpresaCuenta(o.getIntEmpresaCuenta());
						modeloEnDuroMntoTotal.setIntPeriodoCuenta(o.getIntPeriodoCuenta());
						modeloEnDuroMntoTotal.setStrNumeroCuenta(o.getStrNumeroCuenta());
						modeloEnDuroMntoTotal.setStrDescCuenta(o.getStrDescCuenta());
						modeloEnDuroMntoTotal.setIntItem(null);
					 	modeloEnDuroMntoTotal.setStrDescripcion(null);
					 	modeloEnDuroMntoTotal.setIntDatoTablas(null);
						modeloEnDuroMntoTotal.setIntDatoArgumento(null);
						modeloEnDuroMntoTotal.setIntValor(null);
						modeloEnDuroMntoTotal.setStrCampoConsumir("ExCr_MontoTotal_N");
						modeloEnDuroMntoTotal.setIntParamDebeHaber(1);
						modeloEnDuroMntoTotal.setStrDebe("X");
						modeloEnDuroMntoTotal.setStrHaber(null);	
						listaModeloGiroReprestamo.add(modeloEnDuroMntoTotal);
					}
				}
				expCreditoACancelar.setListaModeloDetalleNivelComp(listaModeloGiroReprestamo);				
				expedienteCredito.setExpedienteCreditoCancelacion(expCreditoACancelar);
			}
			

			// CGD - 10.10.2013
			TipoCambio tipoCambioActual = null;
			TipoCambio tipoCambioSolicitud = null;
			if(creditoAsociado.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)==0){
				tipoCambioActual = new TipoCambio();
				tipoCambioActual.setId(new TipoCambioId());
				tipoCambioActual.getId().setIntParaClaseTipoCambio(new Integer(1));
				tipoCambioActual.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
				tipoCambioActual.getId().setIntPersEmpresa(2);
				tipoCambioActual.setBdCompra(BigDecimal.ONE);
				tipoCambioActual.setBdPromedio(BigDecimal.ONE);
				tipoCambioActual.setBdVenta(BigDecimal.ONE);

				tipoCambioSolicitud = new TipoCambio();
				tipoCambioSolicitud.setId(new TipoCambioId());
				tipoCambioSolicitud.getId().setIntParaClaseTipoCambio(new Integer(1));
				tipoCambioSolicitud.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
				tipoCambioSolicitud.getId().setIntPersEmpresa(2);
				tipoCambioSolicitud.setBdCompra(BigDecimal.ONE);
				tipoCambioSolicitud.setBdPromedio(BigDecimal.ONE);
				tipoCambioSolicitud.setBdVenta(BigDecimal.ONE);
				
			}else{
				tipoCambioActual = obtenerTipoCambioActual(creditoAsociado.getIntParaMonedaCod(),intIdEmpresa);			
				tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteCredito, creditoAsociado.getIntParaMonedaCod(), intIdEmpresa);
			}
			
			
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			egreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			egreso.setTsFechaProceso(obtenerFechaActual());
			egreso.setDtFechaEgreso(obtenerFechaActual());
			egreso.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			egreso.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			egreso.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
			egreso.setIntItemBancoFondo(null);
			egreso.setIntItemBancoCuenta(null);
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(null);
			egreso.setIntNumeroTransferencia(null);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
			egreso.setIntCuentaGirado(expedienteCredito.getCuenta().getId().getIntCuenta());
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(null);	
//			egreso.setIntPersCuentaBancariaGirado(null);
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			if(personaApoderado != null){
				egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
				egreso.setIntPersEmpresaApoderado(intIdEmpresa);
			}
			egreso.setBdMontoTotal(expedienteCredito.getBdMontoSolicitado());
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(obtenerFechaActual());
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			if(archivoCartaPoder != null){
				egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
				egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
				egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
			}
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
				//Generación del Egreso Detalle del préstamo a cancelar
				if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getExpedienteCreditoCancelacion().getListaModeloDetalleNivelComp()) {
						EgresoDetalle egresoDetalleGiro = generarEgresoDetallePrestamo(o, socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, controlFondosFijos);
						if (egresoDetalleGiro!=null) {
							egreso.getListaEgresoDetalle().add(egresoDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Egreso Detalle retorna nulo.");
							return egreso;
						}
					}
				}
			}else {
				//Generación del Egreso Detalle del nuevo préstamo
				if (expedienteCredito.getListaModeloDetalleNivelComp()!=null && !expedienteCredito.getListaModeloDetalleNivelComp().isEmpty()) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getListaModeloDetalleNivelComp()) {
						EgresoDetalle egresoDetalleGiro = generarEgresoDetallePrestamo(o, socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, controlFondosFijos);
						if (egresoDetalleGiro!=null) {
							egreso.getListaEgresoDetalle().add(egresoDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Egreso Detalle retorna nulo.");
							return egreso;
						}					
					}
				}
			}			
		
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa(strGlosaEgreso);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			
			
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
				//Generación del Libro Diario Detalle del préstamo a cancelar
				if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getExpedienteCreditoCancelacion().getListaModeloDetalleNivelComp()) {
						LibroDiarioDetalle libroDiarioDetalleGiro = generarLibroDiarioDetalle(o, socioEstructura, expedienteCredito, controlFondosFijos, tipoCambioActual);
						if (libroDiarioDetalleGiro!=null) {
							libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Libro Diario Detalle retorna nulo.");
							return egreso;
						}
					}
				}
			}else{
				//Generación del Libro Diario Detalle del nuevo préstamo
				if (expedienteCredito.getListaModeloDetalleNivelComp()!=null && !expedienteCredito.getListaModeloDetalleNivelComp().isEmpty()) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getListaModeloDetalleNivelComp()) {
						LibroDiarioDetalle libroDiarioDetalleGiro = generarLibroDiarioDetalle(o, socioEstructura, expedienteCredito, controlFondosFijos, tipoCambioActual);
						if (libroDiarioDetalleGiro!=null) {
							libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Libro Diario Detalle retorna nulo.");
							return egreso;
						}
					}				
				}
			}
			
			
			//Generación del Libro Diario Detalle FONDO FIJO
			LibroDiarioDetalle libroDiarioDetalleFondoFijo = generarCFFLibroDiarioDetalle(EGRESO_FONDOFIJO, null, null,
					socioEstructura, expedienteCredito, controlFondosFijos, tipoCambioActual); 
			
			
			if(libroDiarioDetalleFondoFijo != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleFondoFijo);
			
			egreso.setLibroDiario(libroDiario);
			//jchavez 24.06.2014
			egreso.setExpedienteCredito(expedienteCredito);
			egreso.setControlFondosFijos(controlFondosFijos);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		egreso.setIntErrorGeneracionEgreso(0);
		egreso.setStrMsgErrorGeneracionEgreso("Generacion satisfactoria de Egreso, Egreso Detalle, Libro Diario, Libro Diario Detalle.");
		return egreso;
	}
	
	/**
	 * GIRO PRESTAMO - REPRESTAMO POR BANCO(CHEQUE). 
	 */
	
	/**
	 * Genera el egreso cuando se realiza el giro de prestamo por Cheques
	 * Usado para giros por cheques
	 * @param expedienteCredito
	 * @param bancoCuenta
	 * @param usuario
	 * @param intNumeroCheque
	 * @param intTipoDocumentoValidar
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoPrestamoCheque(ExpedienteCredito expedienteCredito, Bancocuenta bancoCuenta, Usuario usuario, Integer intNumeroCheque, Integer intTipoDocumentoValidar) throws BusinessException{
		Egreso egreso = new Egreso();
		Calendar cal = Calendar.getInstance();
		Integer anioActual = cal.get(Calendar.YEAR);
		List<CancelacionCredito> lstExpCreditoACancelar = null;
		ExpedienteCredito expCreditoACancelar = new ExpedienteCredito();
		expCreditoACancelar.setId(new ExpedienteCreditoId());
		Integer intCatergoriaRiesgoCredACancelar = null;
		List<ExpedienteCredito> lstCategoriaRiesgo = null;
		List<ModeloDetalleNivelId> lstModeloReprestamo = null;
		List<ModeloDetalleNivelId> lstModeloReprestamo2 = null;
		List<ModeloDetalleNivelComp> listaModeloGiroAux = null;
		List<ModeloDetalleNivelComp> listaModeloGiroAux2 = null;
		List<ModeloDetalleNivelComp> listaModeloGiroReprestamo = new ArrayList<ModeloDetalleNivelComp>();

		try{			
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			
			Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
			Persona personaGirar = expedienteCredito.getPersonaGirar();		
			Persona personaApoderado = expedienteCredito.getPersonaApoderado();
			Archivo archivoCartaPoder = expedienteCredito.getArchivoCartaPoder();
			String strGlosaEgreso = expedienteCredito.getStrGlosaEgreso();
			
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Persona a Girar: "+personaGirar);
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Apoderado: "+personaApoderado);
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Carta Poder: "+archivoCartaPoder);
			
			// Se obtiene SocioEstructura por persona a girar prestamo
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			if (socioEstructura==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Socio Estructura retorna nulo.");
				return egreso;
			}
			// Se obtiene Credtio Asociado por ParaTipoCredito e ItemCredito
			Credito creditoAsociado = obtenerCreditoPorExpediente(expedienteCredito);
			if (creditoAsociado==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Credito Asociado retorna nulo.");
				return egreso;
			}
			expedienteCredito.getCuenta().setCredito(creditoAsociado);
			// Se valida Cancelacion Credito si el subtipo operacion es REPRESTAMO(2)
			if(expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) expedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());

			//Se recupera Modelo Detalle Nivel, tanto para un préstamo nuevo como un represtamo (Parametro 151)
			ModeloDetalleNivel modeloFiltro = new ModeloDetalleNivel();
			modeloFiltro.setId(new ModeloDetalleNivelId());
			modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
			modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
			modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
			/*
			  Modificacion 11.04.2014 JCHAVEZ
			  Lo primero que debemos obtener es el modelo para el represtamo, para esto la busqueda se realiza como un prestamo 
			  cualquiera, pero variando el tipo de modelo contable por el que se filtrará.
			*/
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO)) modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_CREDITO);
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_REPRESTAMO);
			//FIN MODIFICACION 11.04.2014			
			
			modeloFiltro.setIntDatoTablas(expedienteCredito.getIntParaTipoCreditoCod());
			modeloFiltro.setIntDatoArgumento(expedienteCredito.getIntItemCredito());
			List<ModeloDetalleNivelComp> listaModeloGiro = modeloFacade.getModeloGiroPrestamo(modeloFiltro);
			if (listaModeloGiro==null || listaModeloGiro.isEmpty()) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel retorna nulo.");
				return egreso;
			}
			expedienteCredito.setListaModeloDetalleNivelComp(listaModeloGiro);

			/* Si el subtipo operación del préstamo es REPRESTAMO, se realiza los mismos pasos que para un prestamo nuevo, adicionalmente
			   se generarán 2 movimiento más, uno correspondiente al SALDO DEL PRESTAMO A CANCELAR y otro al INTERES del mismo. El primer 
			   campo se obtinene de la tabla CSE_CANCELACIONCREDITO y el segundo de la tabla CSE_EXPEDIENTE_CREDITO (tener en cuenta que 
			   este campo está en el EXCR_MONTOINTERESATRASADO_N del credito nuevo que se está dando, no del que se está cancelando).
			 */
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
				// 1. Recuperamos la llave del Expediente Crédito que se irá a cancelar, esta se encuentra en Cancelacion Credito
				lstExpCreditoACancelar = boCancelacionCredito.getListaPorExpedienteCredito(expedienteCredito);
				if (lstExpCreditoACancelar!=null && !lstExpCreditoACancelar.isEmpty()) {
					for (CancelacionCredito expCredACancelar : lstExpCreditoACancelar) {
						expCreditoACancelar.getId().setIntPersEmpresaPk(expCredACancelar.getId().getIntPersEmpresaPk());
						expCreditoACancelar.getId().setIntCuentaPk(expCredACancelar.getId().getIntCuentaPk());
						expCreditoACancelar.getId().setIntItemExpediente(expCredACancelar.getIntItemExpediente());
						expCreditoACancelar.getId().setIntItemDetExpediente(expCredACancelar.getIntItemDetExpediente());
						expedienteCredito.getListaCancelacionCredito().add(expCredACancelar);
						break;
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. REPRESTAMO no cuenta con préstamo a cancelar.");
					return egreso;
				}
				
				// 2. Recuperamos los datos del Expediente Crédito que se irá a cancelar
				expCreditoACancelar = boExpedienteCredito.getPorPk(expCreditoACancelar.getId());
				// 3. Vamos al esquema de RIESGO para recuperar la categoría de riesgo (PARA_TIPOCATEGORIARIESGO_N_COD) de la 
				// tabla CRI_CARTERACREDITODETALLE, tener en cuenta que se obtendra el ultimo registro activo que se encuentre. 
				// (CRI_CARTERACREDITO campos: CACR_FECHAREGISTRO_D y PARA_ESTADO_N_COD)
				lstCategoriaRiesgo = boExpedienteCredito.getRiesgoExpCredACancelar(expCreditoACancelar.getId());
				if (lstCategoriaRiesgo!=null && !lstCategoriaRiesgo.isEmpty()) {
					for (ExpedienteCredito categoriaRiesgo : lstCategoriaRiesgo) {
						intCatergoriaRiesgoCredACancelar = categoriaRiesgo.getIntParaTipoCategoriaRiesgo();
						break;
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. REPRESTAMO no cuenta con Categoría de Riesgo.");
					return egreso;
				}
				
				// 4. Vamos a Modelo Detalle Nivel y se realiza un cruce: obtener registros con la categoria de riesgo (PARA_TIPOCATEGORIARIESGO_N_COD) 
				// y con el tipo credito(CSER_ITEMEXPEDIENTE_N Y CSER_ITEMDETEXPEDIENTE) del préstamo a cancelar, de la intersección de dichas búsquedas
				// se obtendrá las cuentas a registrar en el Libro Diario. (EN TEORIA, SEGUN REUNIONES, DEBERIA DAR UNA SOLA CUENTA)
				ModeloDetalleNivel modeloFiltro2 = new ModeloDetalleNivel();
				modeloFiltro2.setId(new ModeloDetalleNivelId());
				modeloFiltro2.getId().setIntEmpresaPk(intIdEmpresa);
				modeloFiltro2.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_REPRESTAMO);
				modeloFiltro2.getId().setIntPersEmpresaCuenta(intIdEmpresa);
				modeloFiltro2.getId().setIntContPeriodoCuenta(anioActual);
				lstModeloReprestamo = modeloFacade.getPkModeloXReprestamo(modeloFiltro2, expCreditoACancelar.getIntParaTipoCreditoCod(), expCreditoACancelar.getIntItemCredito(), intCatergoriaRiesgoCredACancelar);
				// 5. Un vez obtenido los modelos, recuperamos el resitro que nos dará el campo a usar para la grabación del Libro Diario
				//Limpiamos la lista de arriba...
				for (ModeloDetalleNivelComp list : listaModeloGiro) {
					if (!(list.getStrCampoConsumir().equalsIgnoreCase("CaCr_MontoCancelado_N"))) listaModeloGiroReprestamo.add(list);
				}
				if (lstModeloReprestamo!=null && !lstModeloReprestamo.isEmpty()) {
					for (ModeloDetalleNivelId modReprestamo : lstModeloReprestamo) {
						modeloFiltro2.getId().setStrContNumeroCuenta(modReprestamo.getStrContNumeroCuenta());
						listaModeloGiroAux = modeloFacade.getCampoXPkModelo(modeloFiltro2);
						for (ModeloDetalleNivelComp o : listaModeloGiroAux) {
							listaModeloGiroReprestamo.add(o);
						}
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel para Represtamo retorna nulo.");
					return egreso;
				}
				//Hasta este punto, tengo la cuenta del aporte, gravamen y monto, cancelado. El monto solicitado y el
				//monto total usaran la misma cuenta q el cancelado, si su riesgo es vigente, caso contrario  el que se
				//irá a cancelar toma una cuenta de vencido y el represtamo toma la cuenta de vigente.
				ModeloDetalleNivel modeloFiltro3 = new ModeloDetalleNivel();
				modeloFiltro3.setId(new ModeloDetalleNivelId());
				modeloFiltro3.getId().setIntEmpresaPk(intIdEmpresa);
				modeloFiltro3.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_REPRESTAMO);
				modeloFiltro3.getId().setIntPersEmpresaCuenta(intIdEmpresa);
				modeloFiltro3.getId().setIntContPeriodoCuenta(anioActual);
				lstModeloReprestamo2 = modeloFacade.getPkModeloXReprestamo(modeloFiltro3, expedienteCredito.getIntParaTipoCreditoCod(), expedienteCredito.getIntItemCredito(), Constante.PARAM_T_TIPOCATEGORIADERIESGO_NORMAL);
				for (ModeloDetalleNivelId modGiroReprestamo : lstModeloReprestamo2) {
					modeloFiltro3.getId().setStrContNumeroCuenta(modGiroReprestamo.getStrContNumeroCuenta());
					listaModeloGiroAux2 = modeloFacade.getCampoXPkModelo(modeloFiltro3);
					for (ModeloDetalleNivelComp o : listaModeloGiroAux2) {						
						ModeloDetalleNivelComp modeloEnDuroMntoTotal = new ModeloDetalleNivelComp();
						modeloEnDuroMntoTotal.setIntEmpresa(o.getIntEmpresa());
						modeloEnDuroMntoTotal.setIntCodigoModelo(o.getIntCodigoModelo());
						modeloEnDuroMntoTotal.setIntEmpresaCuenta(o.getIntEmpresaCuenta());
						modeloEnDuroMntoTotal.setIntPeriodoCuenta(o.getIntPeriodoCuenta());
						modeloEnDuroMntoTotal.setStrNumeroCuenta(o.getStrNumeroCuenta());
						modeloEnDuroMntoTotal.setStrDescCuenta(o.getStrDescCuenta());
						modeloEnDuroMntoTotal.setIntItem(null);
					 	modeloEnDuroMntoTotal.setStrDescripcion(null);
					 	modeloEnDuroMntoTotal.setIntDatoTablas(null);
						modeloEnDuroMntoTotal.setIntDatoArgumento(null);
						modeloEnDuroMntoTotal.setIntValor(null);
						modeloEnDuroMntoTotal.setStrCampoConsumir("ExCr_MontoTotal_N");
						modeloEnDuroMntoTotal.setIntParamDebeHaber(1);
						modeloEnDuroMntoTotal.setStrDebe("X");
						modeloEnDuroMntoTotal.setStrHaber(null);	
						listaModeloGiroReprestamo.add(modeloEnDuroMntoTotal);
					}
				}
				expCreditoACancelar.setListaModeloDetalleNivelComp(listaModeloGiroReprestamo);				
				expedienteCredito.setExpedienteCreditoCancelacion(expCreditoACancelar);
			}
			
			TipoCambio tipoCambioActual = null;
			TipoCambio tipoCambioSolicitud = null;
			if(creditoAsociado.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)==0){
				tipoCambioActual = new TipoCambio();
				tipoCambioActual.setId(new TipoCambioId());
				tipoCambioActual.getId().setIntParaClaseTipoCambio(new Integer(1));
				tipoCambioActual.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
				tipoCambioActual.getId().setIntPersEmpresa(2);
				tipoCambioActual.setBdCompra(BigDecimal.ONE);
				tipoCambioActual.setBdPromedio(BigDecimal.ONE);
				tipoCambioActual.setBdVenta(BigDecimal.ONE);
				
				tipoCambioSolicitud = new TipoCambio();
				tipoCambioSolicitud.setId(new TipoCambioId());
				tipoCambioSolicitud.getId().setIntParaClaseTipoCambio(new Integer(1));
				tipoCambioSolicitud.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
				tipoCambioSolicitud.getId().setIntPersEmpresa(2);
				tipoCambioSolicitud.setBdCompra(BigDecimal.ONE);
				tipoCambioSolicitud.setBdPromedio(BigDecimal.ONE);
				tipoCambioSolicitud.setBdVenta(BigDecimal.ONE);			
			}else{
				tipoCambioActual = obtenerTipoCambioActual(creditoAsociado.getIntParaMonedaCod(),intIdEmpresa);			
				tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteCredito, creditoAsociado.getIntParaMonedaCod(), intIdEmpresa);
			}
			
			//Generación Cabecera Egreso
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_CHEQUE);
			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			egreso.setTsFechaProceso(obtenerFechaActual());
			egreso.setDtFechaEgreso(obtenerFechaActual());
			egreso.setIntParaTipoFondoFijo(null);
			egreso.setIntItemPeriodoFondo(null);
			egreso.setIntItemFondoFijo(null);
			egreso.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
			egreso.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(intNumeroCheque);
			egreso.setIntNumeroTransferencia(null);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
			egreso.setIntCuentaGirado(expedienteCredito.getCuenta().getId().getIntCuenta());
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(null); //cuentaBancariaDestino!=null?cuentaBancariaDestino.getStrNroCuentaBancaria():null);	
//			egreso.setIntPersCuentaBancariaGirado(cuentaBancariaDestino!=null?cuentaBancariaDestino.getId().getIntIdCuentaBancaria():null);
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			if(personaApoderado != null){
				egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
				egreso.setIntPersEmpresaApoderado(intIdEmpresa);
			}
			egreso.setBdMontoTotal(expedienteCredito.getBdMontoSolicitado());
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(obtenerFechaActual());
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			if(archivoCartaPoder != null){
				egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
				egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
				egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
			}
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
				//Generación del Egreso Detalle del préstamo a cancelar
				if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getExpedienteCreditoCancelacion().getListaModeloDetalleNivelComp()) {
						EgresoDetalle egresoDetalleGiro = generarEgresoDetallePrestamo(o, socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
						if (egresoDetalleGiro!=null) {
							egreso.getListaEgresoDetalle().add(egresoDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Egreso Detalle retorna nulo.");
							return egreso;
						}
					}
				}
			}else {
				//Generación del Egreso Detalle del nuevo préstamo
				if (expedienteCredito.getListaModeloDetalleNivelComp()!=null && !expedienteCredito.getListaModeloDetalleNivelComp().isEmpty()) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getListaModeloDetalleNivelComp()) {
						EgresoDetalle egresoDetalleGiro = generarEgresoDetallePrestamo(o, socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
						if (egresoDetalleGiro!=null) {
							egreso.getListaEgresoDetalle().add(egresoDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Egreso Detalle retorna nulo.");
							return egreso;
						}					
					}
				}
			}			
		
			
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa(strGlosaEgreso);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(intTipoDocumentoValidar);			
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
				//Generación del Libro Diario Detalle del préstamo a cancelar
				if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getExpedienteCreditoCancelacion().getListaModeloDetalleNivelComp()) {
						LibroDiarioDetalle libroDiarioDetalleGiro = generarLibroDiarioDetalle(o, socioEstructura, expedienteCredito, null, tipoCambioActual);
						if (libroDiarioDetalleGiro!=null) {
							libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Libro Diario Detalle retorna nulo.");
							return egreso;
						}
					}
				}
			}else{
				//Generación del Libro Diario Detalle del nuevo préstamo
				if (expedienteCredito.getListaModeloDetalleNivelComp()!=null && !expedienteCredito.getListaModeloDetalleNivelComp().isEmpty()) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getListaModeloDetalleNivelComp()) {
						LibroDiarioDetalle libroDiarioDetalleGiro = generarLibroDiarioDetalle(o, socioEstructura, expedienteCredito, null, tipoCambioActual);
						if (libroDiarioDetalleGiro!=null) {
							libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Libro Diario Detalle retorna nulo.");
							return egreso;
						}
					}				
				}
			}
			//Generación del Libro Diario Detalle BANCO
			LibroDiarioDetalle libroDiarioDetalleBancoPorCheque = generarBancoLibroDiarioDetalle(expedienteCredito, bancoCuenta, tipoCambioActual, intTipoDocumentoValidar); 			
			if(libroDiarioDetalleBancoPorCheque != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBancoPorCheque);
			
			egreso.setLibroDiario(libroDiario);
			//jchavez 24.06.2014
			egreso.setExpedienteCredito(expedienteCredito);
			//egreso.setControlFondosFijos(controlFondosFijos);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		egreso.setIntErrorGeneracionEgreso(0);
		egreso.setStrMsgErrorGeneracionEgreso("Generacion satisfactoria de Egreso, Egreso Detalle, Libro Diario, Libro Diario Detalle.");
		return egreso;
	}
	
	/**
	 * GIRO PRESTAMO - REPRESTAMO POR BANCO(TRANSFERENCIA A TERCEROS). 
	 */
	
	/**
	 * Genera el egreso cuando se realiza el giro de prestamo por Transferencia
	 * Usado para giros por cheques
	 * @param expedienteCredito
	 * @param bancoCuenta
	 * @param usuario
	 * @param intNumeroTransferencia
	 * @param intTipoDocumentoValidar
	 * @return
	 * @throws BusinessException
	 */
	public Egreso generarEgresoPrestamoTransferenciaTerceros(ExpedienteCredito expedienteCredito, Bancocuenta bancoCuenta, Usuario usuario, Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino) throws BusinessException{
		Egreso egreso = new Egreso();
		Calendar cal = Calendar.getInstance();
		Integer anioActual = cal.get(Calendar.YEAR);
		List<CancelacionCredito> lstExpCreditoACancelar = null;
		ExpedienteCredito expCreditoACancelar = new ExpedienteCredito();
		expCreditoACancelar.setId(new ExpedienteCreditoId());
		Integer intCatergoriaRiesgoCredACancelar = null;
		List<ExpedienteCredito> lstCategoriaRiesgo = null;
		List<ModeloDetalleNivelId> lstModeloReprestamo = null;
		List<ModeloDetalleNivelId> lstModeloReprestamo2 = null;
		List<ModeloDetalleNivelComp> listaModeloGiroAux = null;
		List<ModeloDetalleNivelComp> listaModeloGiroAux2 = null;
		List<ModeloDetalleNivelComp> listaModeloGiroReprestamo = new ArrayList<ModeloDetalleNivelComp>();

		try{			
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			
			Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
			Persona personaGirar = expedienteCredito.getPersonaGirar();		
			Persona personaApoderado = expedienteCredito.getPersonaApoderado();
			Archivo archivoCartaPoder = expedienteCredito.getArchivoCartaPoder();
			String strGlosaEgreso = expedienteCredito.getStrGlosaEgreso();
			
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Persona a Girar: "+personaGirar);
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Apoderado: "+personaApoderado);
			log.info("EgresoPrestamoService.generarEgresoPrestamo ---> Carta Poder: "+archivoCartaPoder);
			
			// Se obtiene SocioEstructura por persona a girar prestamo
			SocioEstructura socioEstructura = obtenerSocioEstructura(personaGirar, intIdEmpresa);
			if (socioEstructura==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Socio Estructura retorna nulo.");
				return egreso;
			}
			// Se obtiene Credtio Asociado por ParaTipoCredito e ItemCredito
			Credito creditoAsociado = obtenerCreditoPorExpediente(expedienteCredito);
			if (creditoAsociado==null) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Credito Asociado retorna nulo.");
				return egreso;
			}
			expedienteCredito.getCuenta().setCredito(creditoAsociado);
			// Se valida Cancelacion Credito si el subtipo operacion es REPRESTAMO(2)
			if(expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) expedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());

			//Se recupera Modelo Detalle Nivel, tanto para un préstamo nuevo como un represtamo (Parametro 151)
			ModeloDetalleNivel modeloFiltro = new ModeloDetalleNivel();
			modeloFiltro.setId(new ModeloDetalleNivelId());
			modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
			modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
			modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
			/*
			  Modificacion 11.04.2014 JCHAVEZ
			  Lo primero que debemos obtener es el modelo para el represtamo, para esto la busqueda se realiza como un prestamo 
			  cualquiera, pero variando el tipo de modelo contable por el que se filtrará.
			*/
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO)) modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_CREDITO);
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) modeloFiltro.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_REPRESTAMO);
			//FIN MODIFICACION 11.04.2014			
			
			modeloFiltro.setIntDatoTablas(expedienteCredito.getIntParaTipoCreditoCod());
			modeloFiltro.setIntDatoArgumento(expedienteCredito.getIntItemCredito());
			List<ModeloDetalleNivelComp> listaModeloGiro = modeloFacade.getModeloGiroPrestamo(modeloFiltro);
			if (listaModeloGiro==null || listaModeloGiro.isEmpty()) {
				egreso.setIntErrorGeneracionEgreso(1);
				egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel retorna nulo.");
				return egreso;
			}
			expedienteCredito.setListaModeloDetalleNivelComp(listaModeloGiro);

			/* Si el subtipo operación del préstamo es REPRESTAMO, se realiza los mismos pasos que para un prestamo nuevo, adicionalmente
			   se generarán 2 movimiento más, uno correspondiente al SALDO DEL PRESTAMO A CANCELAR y otro al INTERES del mismo. El primer 
			   campo se obtinene de la tabla CSE_CANCELACIONCREDITO y el segundo de la tabla CSE_EXPEDIENTE_CREDITO (tener en cuenta que 
			   este campo está en el EXCR_MONTOINTERESATRASADO_N del credito nuevo que se está dando, no del que se está cancelando).
			 */
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
				// 1. Recuperamos la llave del Expediente Crédito que se irá a cancelar, esta se encuentra en Cancelacion Credito
				lstExpCreditoACancelar = boCancelacionCredito.getListaPorExpedienteCredito(expedienteCredito);
				if (lstExpCreditoACancelar!=null && !lstExpCreditoACancelar.isEmpty()) {
					for (CancelacionCredito expCredACancelar : lstExpCreditoACancelar) {
						expCreditoACancelar.getId().setIntPersEmpresaPk(expCredACancelar.getId().getIntPersEmpresaPk());
						expCreditoACancelar.getId().setIntCuentaPk(expCredACancelar.getId().getIntCuentaPk());
						expCreditoACancelar.getId().setIntItemExpediente(expCredACancelar.getIntItemExpediente());
						expCreditoACancelar.getId().setIntItemDetExpediente(expCredACancelar.getIntItemDetExpediente());
						expedienteCredito.getListaCancelacionCredito().add(expCredACancelar);
						break;
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. REPRESTAMO no cuenta con préstamo a cancelar.");
					return egreso;
				}
				
				// 2. Recuperamos los datos del Expediente Crédito que se irá a cancelar
				expCreditoACancelar = boExpedienteCredito.getPorPk(expCreditoACancelar.getId());
				// 3. Vamos al esquema de RIESGO para recuperar la categoría de riesgo (PARA_TIPOCATEGORIARIESGO_N_COD) de la 
				// tabla CRI_CARTERACREDITODETALLE, tener en cuenta que se obtendra el ultimo registro activo que se encuentre. 
				// (CRI_CARTERACREDITO campos: CACR_FECHAREGISTRO_D y PARA_ESTADO_N_COD)
				lstCategoriaRiesgo = boExpedienteCredito.getRiesgoExpCredACancelar(expCreditoACancelar.getId());
				if (lstCategoriaRiesgo!=null && !lstCategoriaRiesgo.isEmpty()) {
					for (ExpedienteCredito categoriaRiesgo : lstCategoriaRiesgo) {
						intCatergoriaRiesgoCredACancelar = categoriaRiesgo.getIntParaTipoCategoriaRiesgo();
						break;
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. REPRESTAMO no cuenta con Categoría de Riesgo.");
					return egreso;
				}
				
				// 4. Vamos a Modelo Detalle Nivel y se realiza un cruce: obtener registros con la categoria de riesgo (PARA_TIPOCATEGORIARIESGO_N_COD) 
				// y con el tipo credito(CSER_ITEMEXPEDIENTE_N Y CSER_ITEMDETEXPEDIENTE) del préstamo a cancelar, de la intersección de dichas búsquedas
				// se obtendrá las cuentas a registrar en el Libro Diario. (EN TEORIA, SEGUN REUNIONES, DEBERIA DAR UNA SOLA CUENTA)
				ModeloDetalleNivel modeloFiltro2 = new ModeloDetalleNivel();
				modeloFiltro2.setId(new ModeloDetalleNivelId());
				modeloFiltro2.getId().setIntEmpresaPk(intIdEmpresa);
				modeloFiltro2.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_REPRESTAMO);
				modeloFiltro2.getId().setIntPersEmpresaCuenta(intIdEmpresa);
				modeloFiltro2.getId().setIntContPeriodoCuenta(anioActual);
				lstModeloReprestamo = modeloFacade.getPkModeloXReprestamo(modeloFiltro2, expCreditoACancelar.getIntParaTipoCreditoCod(), expCreditoACancelar.getIntItemCredito(), intCatergoriaRiesgoCredACancelar);
				// 5. Un vez obtenido los modelos, recuperamos el resitro que nos dará el campo a usar para la grabación del Libro Diario
				//Limpiamos la lista de arriba...
				for (ModeloDetalleNivelComp list : listaModeloGiro) {
					if (!(list.getStrCampoConsumir().equalsIgnoreCase("CaCr_MontoCancelado_N"))) listaModeloGiroReprestamo.add(list);
				}
				if (lstModeloReprestamo!=null && !lstModeloReprestamo.isEmpty()) {
					for (ModeloDetalleNivelId modReprestamo : lstModeloReprestamo) {
						modeloFiltro2.getId().setStrContNumeroCuenta(modReprestamo.getStrContNumeroCuenta());
						listaModeloGiroAux = modeloFacade.getCampoXPkModelo(modeloFiltro2);
						for (ModeloDetalleNivelComp o : listaModeloGiroAux) {
							listaModeloGiroReprestamo.add(o);
						}
					}
				}else{
					egreso.setIntErrorGeneracionEgreso(1);
					egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Modelo Detalle Nivel para Represtamo retorna nulo.");
					return egreso;
				}
				//Hasta este punto, tengo la cuenta del aporte, gravamen y monto, cancelado. El monto solicitado y el
				//monto total usaran la misma cuenta q el cancelado, si su riesgo es vigente, caso contrario  el que se
				//irá a cancelar toma una cuenta de vencido y el represtamo toma la cuenta de vigente.
				ModeloDetalleNivel modeloFiltro3 = new ModeloDetalleNivel();
				modeloFiltro3.setId(new ModeloDetalleNivelId());
				modeloFiltro3.getId().setIntEmpresaPk(intIdEmpresa);
				modeloFiltro3.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_REPRESTAMO);
				modeloFiltro3.getId().setIntPersEmpresaCuenta(intIdEmpresa);
				modeloFiltro3.getId().setIntContPeriodoCuenta(anioActual);
				lstModeloReprestamo2 = modeloFacade.getPkModeloXReprestamo(modeloFiltro3, expedienteCredito.getIntParaTipoCreditoCod(), expedienteCredito.getIntItemCredito(), Constante.PARAM_T_TIPOCATEGORIADERIESGO_NORMAL);
				for (ModeloDetalleNivelId modGiroReprestamo : lstModeloReprestamo2) {
					modeloFiltro3.getId().setStrContNumeroCuenta(modGiroReprestamo.getStrContNumeroCuenta());
					listaModeloGiroAux2 = modeloFacade.getCampoXPkModelo(modeloFiltro3);
					for (ModeloDetalleNivelComp o : listaModeloGiroAux2) {						
						ModeloDetalleNivelComp modeloEnDuroMntoTotal = new ModeloDetalleNivelComp();
						modeloEnDuroMntoTotal.setIntEmpresa(o.getIntEmpresa());
						modeloEnDuroMntoTotal.setIntCodigoModelo(o.getIntCodigoModelo());
						modeloEnDuroMntoTotal.setIntEmpresaCuenta(o.getIntEmpresaCuenta());
						modeloEnDuroMntoTotal.setIntPeriodoCuenta(o.getIntPeriodoCuenta());
						modeloEnDuroMntoTotal.setStrNumeroCuenta(o.getStrNumeroCuenta());
						modeloEnDuroMntoTotal.setStrDescCuenta(o.getStrDescCuenta());
						modeloEnDuroMntoTotal.setIntItem(null);
					 	modeloEnDuroMntoTotal.setStrDescripcion(null);
					 	modeloEnDuroMntoTotal.setIntDatoTablas(null);
						modeloEnDuroMntoTotal.setIntDatoArgumento(null);
						modeloEnDuroMntoTotal.setIntValor(null);
						modeloEnDuroMntoTotal.setStrCampoConsumir("ExCr_MontoTotal_N");
						modeloEnDuroMntoTotal.setIntParamDebeHaber(1);
						modeloEnDuroMntoTotal.setStrDebe("X");
						modeloEnDuroMntoTotal.setStrHaber(null);	
						listaModeloGiroReprestamo.add(modeloEnDuroMntoTotal);
					}
				}
				expCreditoACancelar.setListaModeloDetalleNivelComp(listaModeloGiroReprestamo);				
				expedienteCredito.setExpedienteCreditoCancelacion(expCreditoACancelar);
			}
			
			TipoCambio tipoCambioActual = null;
			TipoCambio tipoCambioSolicitud = null;
			if(creditoAsociado.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)==0){
				tipoCambioActual = new TipoCambio();
				tipoCambioActual.setId(new TipoCambioId());
				tipoCambioActual.getId().setIntParaClaseTipoCambio(new Integer(1));
				tipoCambioActual.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
				tipoCambioActual.getId().setIntPersEmpresa(2);
				tipoCambioActual.setBdCompra(BigDecimal.ONE);
				tipoCambioActual.setBdPromedio(BigDecimal.ONE);
				tipoCambioActual.setBdVenta(BigDecimal.ONE);
				
				tipoCambioSolicitud = new TipoCambio();
				tipoCambioSolicitud.setId(new TipoCambioId());
				tipoCambioSolicitud.getId().setIntParaClaseTipoCambio(new Integer(1));
				tipoCambioSolicitud.getId().setIntParaMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
				tipoCambioSolicitud.getId().setIntPersEmpresa(2);
				tipoCambioSolicitud.setBdCompra(BigDecimal.ONE);
				tipoCambioSolicitud.setBdPromedio(BigDecimal.ONE);
				tipoCambioSolicitud.setBdVenta(BigDecimal.ONE);			
			}else{
				tipoCambioActual = obtenerTipoCambioActual(creditoAsociado.getIntParaMonedaCod(),intIdEmpresa);			
				tipoCambioSolicitud = obtenerTipoCambioSolicitud(expedienteCredito, creditoAsociado.getIntParaMonedaCod(), intIdEmpresa);
			}
			
			//Generación Cabecera Egreso
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA);
			egreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egreso.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			egreso.setTsFechaProceso(obtenerFechaActual());
			egreso.setDtFechaEgreso(obtenerFechaActual());
			egreso.setIntParaTipoFondoFijo(null);
			egreso.setIntItemPeriodoFondo(null);
			egreso.setIntItemFondoFijo(null);
			egreso.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
			egreso.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(null);
			egreso.setIntNumeroTransferencia(intNumeroTransferencia);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
			egreso.setIntCuentaGirado(expedienteCredito.getCuenta().getId().getIntCuenta());
			//20.05.2014 jchavez cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(cuentaBancariaDestino!=null?cuentaBancariaDestino.getStrNroCuentaBancaria():null);	
//			egreso.setIntPersCuentaBancariaGirado(cuentaBancariaDestino!=null?Integer.parseInt(cuentaBancariaDestino.getStrNroCuentaBancaria()):null);
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			if(personaApoderado != null){
				egreso.setIntPersPersonaApoderado(personaApoderado.getIntIdPersona());
				egreso.setIntPersEmpresaApoderado(intIdEmpresa);
			}
			egreso.setBdMontoTotal(expedienteCredito.getBdMontoSolicitado());
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(obtenerFechaActual());
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			if(archivoCartaPoder != null){
				egreso.setIntParaTipoApoderado(archivoCartaPoder.getId().getIntParaTipoCod());
				egreso.setIntItemArchivoApoderado(archivoCartaPoder.getId().getIntItemArchivo());
				egreso.setIntItemHistoricoApoderado(archivoCartaPoder.getId().getIntItemHistorico());
			}
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
				//Generación del Egreso Detalle del préstamo a cancelar
				if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getExpedienteCreditoCancelacion().getListaModeloDetalleNivelComp()) {
						EgresoDetalle egresoDetalleGiro = generarEgresoDetallePrestamo(o, socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
						if (egresoDetalleGiro!=null) {
							egreso.getListaEgresoDetalle().add(egresoDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Egreso Detalle retorna nulo.");
							return egreso;
						}
					}
				}
			}else {
				//Generación del Egreso Detalle del nuevo préstamo
				if (expedienteCredito.getListaModeloDetalleNivelComp()!=null && !expedienteCredito.getListaModeloDetalleNivelComp().isEmpty()) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getListaModeloDetalleNivelComp()) {
						EgresoDetalle egresoDetalleGiro = generarEgresoDetallePrestamo(o, socioEstructura, expedienteCredito, usuario, tipoCambioActual, tipoCambioSolicitud, null);
						if (egresoDetalleGiro!=null) {
							egreso.getListaEgresoDetalle().add(egresoDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Egreso Detalle retorna nulo.");
							return egreso;
						}					
					}
				}
			}			
		
			
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa(strGlosaEgreso);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(intTipoDocumentoValidar);			
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			
			if (expedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
				//Generación del Libro Diario Detalle del préstamo a cancelar
				if (expedienteCredito.getExpedienteCreditoCancelacion()!=null) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getExpedienteCreditoCancelacion().getListaModeloDetalleNivelComp()) {
						LibroDiarioDetalle libroDiarioDetalleGiro = generarLibroDiarioDetalle(o, socioEstructura, expedienteCredito, null, tipoCambioActual);
						if (libroDiarioDetalleGiro!=null) {
							libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Libro Diario Detalle retorna nulo.");
							return egreso;
						}
					}
				}
			}else{
				//Generación del Libro Diario Detalle del nuevo préstamo
				if (expedienteCredito.getListaModeloDetalleNivelComp()!=null && !expedienteCredito.getListaModeloDetalleNivelComp().isEmpty()) {
					for (ModeloDetalleNivelComp o : expedienteCredito.getListaModeloDetalleNivelComp()) {
						LibroDiarioDetalle libroDiarioDetalleGiro = generarLibroDiarioDetalle(o, socioEstructura, expedienteCredito, null, tipoCambioActual);
						if (libroDiarioDetalleGiro!=null) {
							libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleGiro);
						}else{
							egreso.setIntErrorGeneracionEgreso(1);
							egreso.setStrMsgErrorGeneracionEgreso("Ocurrio un error durante el proceso de giro de préstamos. Generación de Libro Diario Detalle retorna nulo.");
							return egreso;
						}
					}				
				}
			}
			//Generación del Libro Diario Detalle BANCO
			LibroDiarioDetalle libroDiarioDetalleBancoPorCheque = generarBancoLibroDiarioDetalle(expedienteCredito, bancoCuenta, tipoCambioActual, intTipoDocumentoValidar); 			
			if(libroDiarioDetalleBancoPorCheque != null) libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBancoPorCheque);
			
			egreso.setLibroDiario(libroDiario);
			//jchavez 24.06.2014
			egreso.setExpedienteCredito(expedienteCredito);
			//egreso.setControlFondosFijos(controlFondosFijos);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		egreso.setIntErrorGeneracionEgreso(0);
		egreso.setStrMsgErrorGeneracionEgreso("Generacion satisfactoria de Egreso, Egreso Detalle, Libro Diario, Libro Diario Detalle.");
		return egreso;
	}
	
	/**
	 * JCHAVEZ 02.01.2014
	 * Genera el Egreso Detalle para Giro de Préstamos
	 * @param o
	 * @param socioEstructura
	 * @param expedienteCredito
	 * @param usuario
	 * @param tipoCambioActual
	 * @param tipoCambioSolicitud
	 * @param controlFondosFijos
	 * @return
	 * @throws Exception
	 */
	private EgresoDetalle generarEgresoDetallePrestamo(ModeloDetalleNivelComp o, SocioEstructura socioEstructura, ExpedienteCredito expedienteCredito, Usuario usuario,
			TipoCambio tipoCambioActual, TipoCambio tipoCambioSolicitud, ControlFondosFijos controlFondosFijos)
		throws Exception{
		expedienteCredito.setGeneraAporte(false);
		Credito credito = expedienteCredito.getCuenta().getCredito();
		boolean esMonedaExtranjera;
		
		Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
		
		EgresoDetalle egresoDetalle = new EgresoDetalle();
		egresoDetalle.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
		egresoDetalle.setIntParaTipoComprobante(null);
		egresoDetalle.setStrSerieDocumento(null);
		
		egresoDetalle.setStrDescripcionEgreso(o.getStrDescCuenta());
		egresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalle.setIntPersonaGirado(expedienteCredito.getPersonaGirar().getIntIdPersona());
		egresoDetalle.setIntCuentaGirado(expedienteCredito.getCuenta().getId().getIntCuenta());
		egresoDetalle.setIntSucuIdSucursalEgreso(socioEstructura.getIntIdSucursalAdministra());
		egresoDetalle.setIntSudeIdSubsucursalEgreso(socioEstructura.getIntIdSubsucurAdministra());
		egresoDetalle.setIntParaTipoMoneda(credito.getIntParaMonedaCod());
		if(egresoDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			esMonedaExtranjera = Boolean.FALSE;
		}else{
			esMonedaExtranjera = Boolean.TRUE;
		}
		if (o.getStrCampoConsumir().equalsIgnoreCase("ExCr_MontoTotal_N")) {
			if(expedienteCredito.getBdMontoTotal()==null) return null;
			if (o.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(expedienteCredito.getBdMontoTotal());
			if (o.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(expedienteCredito.getBdMontoTotal());	
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
			egresoDetalle.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
		}
		if (o.getStrCampoConsumir().equalsIgnoreCase("ExCr_MontoAporte_N")) {
			if(expedienteCredito.getBdMontoAporte()==null) return null;
			if (o.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(expedienteCredito.getBdMontoAporte());
			if (o.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(expedienteCredito.getBdMontoAporte());
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
			egresoDetalle.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
			expedienteCredito.setGeneraAporte(true);
		}
		if (o.getStrCampoConsumir().equalsIgnoreCase("ExCr_MontoGravamen_N")) {
			if(expedienteCredito.getBdMontoGravamen()==null) return null;
			if (o.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(expedienteCredito.getBdMontoGravamen());
			if (o.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(expedienteCredito.getBdMontoGravamen());
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
			egresoDetalle.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
		}
		if (o.getStrCampoConsumir().equalsIgnoreCase("CaCr_MontoCancelado_N")) {
			for (CancelacionCredito expCredCancelar : expedienteCredito.getListaCancelacionCredito()) {
				if(expCredCancelar.getBdMontoCancelado()==null) return null;
				if (o.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(expCredCancelar.getBdMontoCancelado());
				if (o.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(expCredCancelar.getBdMontoCancelado());
				if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
				egresoDetalle.setStrNumeroDocumento(expCredCancelar.getIntItemExpediente()+"-"+expCredCancelar.getIntItemDetExpediente());
			}
		}
		if (o.getStrCampoConsumir().equalsIgnoreCase("Excr_MontoInteresAtrasado_N")) {
			if(expedienteCredito.getBdMontoInteresAtrasado()==null) return null;
			if (o.getIntParamDebeHaber().equals(1)) egresoDetalle.setBdMontoCargo(expedienteCredito.getBdMontoInteresAtrasado());
			if (o.getIntParamDebeHaber().equals(2)) egresoDetalle.setBdMontoAbono(expedienteCredito.getBdMontoInteresAtrasado());	
			if(esMonedaExtranjera) egresoDetalle.setBdMontoDiferencial(obtenerMontoDiferencial(egresoDetalle.getBdMontoAbono()!=null?egresoDetalle.getBdMontoAbono():egresoDetalle.getBdMontoCargo(), tipoCambioActual, tipoCambioSolicitud));
			egresoDetalle.setStrNumeroDocumento(expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemExpediente()+"-"+expedienteCredito.getExpedienteCreditoCancelacion().getId().getIntItemDetExpediente());
		}		
		
		egresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egresoDetalle.setTsFechaRegistro(obtenerFechaActual());
		egresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
		egresoDetalle.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
		egresoDetalle.setIntPersEmpresaLibroDestino(null);
		egresoDetalle.setIntContPeriodoLibroDestino(null);
		egresoDetalle.setIntContCodigoLibroDestino(null);
		egresoDetalle.setIntPersEmpresaCuenta(o.getIntEmpresaCuenta());
		egresoDetalle.setIntContPeriodoCuenta(o.getIntPeriodoCuenta());
		egresoDetalle.setStrContNumeroCuenta(o.getStrNumeroCuenta());
		if(controlFondosFijos!=null){
			egresoDetalle.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			egresoDetalle.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			egresoDetalle.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
			egresoDetalle.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());			
		}
		return egresoDetalle;
	}
	
	/**
	 * Generacion del asiento contable por control de fondos fijos
	 * @param intTipoEgreso
	 * @param cancelacionCredito
	 * @param modeloDetalle
	 * @param socioEstructura
	 * @param expedienteCredito
	 * @param controlFondosFijos
	 * @param tipoCambioActual
	 * @return
	 * @throws Exception
	 */	
	private LibroDiarioDetalle generarCFFLibroDiarioDetalle(Integer intTipoEgreso, CancelacionCredito cancelacionCredito, ModeloDetalle modeloDetalle, 
			SocioEstructura socioEstructura, ExpedienteCredito expedienteCredito, ControlFondosFijos controlFondosFijos, TipoCambio tipoCambioActual)
		throws Exception{
		LibroDiarioDetalle libroDiarioDetalle = null;
		Credito credito = expedienteCredito.getCuenta().getCredito();
		Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
		PlanCuentaId pId = new PlanCuentaId();
		try {
			PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			libroDiarioDetalle = new LibroDiarioDetalle();
			libroDiarioDetalle.setId(new LibroDiarioDetalleId());
			libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			
			libroDiarioDetalle.setIntPersPersona(expedienteCredito.getPersonaGirar().getIntIdPersona());
			if(intTipoEgreso.equals(EGRESO_MONTOTOTAL)
			|| intTipoEgreso.equals(EGRESO_GRAVAMEN)
			|| intTipoEgreso.equals(EGRESO_APORTE)
			|| intTipoEgreso.equals(EGRESO_CANCELADO)){
				libroDiarioDetalle.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
				libroDiarioDetalle.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
				libroDiarioDetalle.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
				libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
			}else if(intTipoEgreso.equals(EGRESO_FONDOFIJO)){
				PlanCuenta planCuenta = obtenerPlanCuentaOrigenCFF(controlFondosFijos);
				libroDiarioDetalle.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
				libroDiarioDetalle.setIntContPeriodo(planCuenta.getId().getIntPeriodoCuenta());
				libroDiarioDetalle.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());			
				libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
				//Descripción del Plan de Cuenta
				pId.setIntEmpresaCuentaPk(planCuenta.getId().getIntEmpresaCuentaPk());
				pId.setStrNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());
				pId.setIntPeriodoCuenta(planCuenta.getId().getIntPeriodoCuenta());
				libroDiarioDetalle.setStrComentario(planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().length()<20?planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion():planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().substring(0, 20));
				
			}
			
			libroDiarioDetalle.setStrSerieDocumento(null);
			if(intTipoEgreso.equals(EGRESO_MONTOTOTAL)
				|| intTipoEgreso.equals(EGRESO_GRAVAMEN)
				|| intTipoEgreso.equals(EGRESO_APORTE) 
				|| intTipoEgreso.equals(EGRESO_CANCELADO)){

				libroDiarioDetalle.setStrNumeroDocumento(""+expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
				libroDiarioDetalle.setIntPersEmpresaSucursal(socioEstructura.getIntEmpresaSucUsuario());
				libroDiarioDetalle.setIntSucuIdSucursal(socioEstructura.getIntIdSucursalUsuario());
				libroDiarioDetalle.setIntSudeIdSubSucursal(socioEstructura.getIntIdSubSucursalUsuario());
				
			}else if(intTipoEgreso.equals(EGRESO_FONDOFIJO)){
				libroDiarioDetalle.setIntPersEmpresaSucursal(controlFondosFijos.getSucursal().getId().getIntPersEmpresaPk());
				libroDiarioDetalle.setIntSucuIdSucursal(controlFondosFijos.getSucursal().getId().getIntIdSucursal());
				libroDiarioDetalle.setIntSudeIdSubSucursal(controlFondosFijos.getSubsucursal().getId().getIntIdSubSucursal());			
			}		
			
			// CGD -10.10.2013
			if(credito.getIntParaMonedaCod().compareTo(Constante.PARAM_T_TIPOMONEDA_SOL)==0){
				libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOL);
			}else{
				libroDiarioDetalle.setIntParaMonedaDocumento(credito.getIntParaMonedaCod());
			}
			
			libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			
			if(libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
				if(intTipoEgreso.equals(EGRESO_MONTOTOTAL)){
					libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoTotal());
				
				}else if(intTipoEgreso.equals(EGRESO_GRAVAMEN)){
					if(expedienteCredito.getBdMontoGravamen()==null) return null;
					libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoGravamen());
				
				}else if(intTipoEgreso.equals(EGRESO_APORTE)){
					if(expedienteCredito.getBdMontoAporte()==null) return null;
					libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoAporte());
				
				}else if(intTipoEgreso.equals(EGRESO_CANCELADO)){
					if(cancelacionCredito.getBdMontoCancelado()==null) return null;
					libroDiarioDetalle.setBdHaberSoles(cancelacionCredito.getBdMontoCancelado());			
				
				}else if(intTipoEgreso.equals(EGRESO_FONDOFIJO)){
					if (expedienteCredito.getBdMontoSolicitado()==null) return null;
					libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoSolicitado());
				}
				
			}else{
				/******cambio en soles ******/
				if(intTipoEgreso.equals(EGRESO_MONTOTOTAL)){
					libroDiarioDetalle.setBdDebeSoles(expedienteCredito.getBdMontoTotal().multiply(tipoCambioActual.getBdCompra()));
					libroDiarioDetalle.setBdDebeExtranjero(expedienteCredito.getBdMontoTotal());
				
				}else if(intTipoEgreso.equals(EGRESO_GRAVAMEN)){
					if(expedienteCredito.getBdMontoGravamen()==null) return null;
					libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoGravamen().multiply(tipoCambioActual.getBdCompra()));
					libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoGravamen());
				
				}else if(intTipoEgreso.equals(EGRESO_APORTE)){
					if(expedienteCredito.getBdMontoAporte()==null) return null;
					libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoAporte().multiply(tipoCambioActual.getBdCompra()));
					libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoAporte());
				
				}else if(intTipoEgreso.equals(EGRESO_CANCELADO)){
					if(cancelacionCredito.getBdMontoCancelado()==null) return null;
					libroDiarioDetalle.setBdHaberSoles(cancelacionCredito.getBdMontoCancelado().multiply(tipoCambioActual.getBdCompra()));
					libroDiarioDetalle.setBdHaberExtranjero(cancelacionCredito.getBdMontoCancelado());
				
				}else if(intTipoEgreso.equals(EGRESO_FONDOFIJO)){
					if (expedienteCredito.getBdMontoSolicitado()==null) return null;
					libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoSolicitado().multiply(tipoCambioActual.getBdCompra()));
					libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoSolicitado());
				}
			}
		} catch (Exception e) {
			log.error("Error en generarLibroDiarioDetalle --> "+e);
		}
		
		return libroDiarioDetalle;
	}
	
	/**
	 * JCHAVEZ 17.02.2014
	 * Generacion del asiento contable por Banco.
	 * @param expedienteCredito
	 * @param bancoCuenta
	 * @param tipoCambioActual
	 * @return
	 * @throws Exception
	 */
	private LibroDiarioDetalle generarBancoLibroDiarioDetalle(ExpedienteCredito expedienteCredito, Bancocuenta bancoCuenta, TipoCambio tipoCambioActual, Integer intTipoDocumentoValidar) throws Exception{
		
		Credito credito = expedienteCredito.getCuenta().getCredito();
		Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
		
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		libroDiarioDetalle.setId(new LibroDiarioDetalleId());
		libroDiarioDetalle.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		
		libroDiarioDetalle.setIntPersPersona(expedienteCredito.getPersonaGirar().getIntIdPersona());
		
		libroDiarioDetalle.setIntPersEmpresaCuenta(bancoCuenta.getId().getIntEmpresaPk());
		libroDiarioDetalle.setIntContPeriodo(bancoCuenta.getIntPeriodocuenta());
		libroDiarioDetalle.setStrContNumeroCuenta(bancoCuenta.getStrNumerocuenta());			
		libroDiarioDetalle.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
		libroDiarioDetalle.setStrComentario(bancoCuenta.getStrNombrecuenta());
		libroDiarioDetalle.setStrSerieDocumento(null);
		libroDiarioDetalle.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
		libroDiarioDetalle.setIntSucuIdSucursal(Constante.PARAM_SUCURSALSESION);
		libroDiarioDetalle.setIntSudeIdSubSucursal(null);			
	
		libroDiarioDetalle.setIntParaMonedaDocumento(credito.getIntParaMonedaCod());
		libroDiarioDetalle.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
		
		if(libroDiarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOL)){
			libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoSolicitado());
		}else{
			/******cambio en soles ******/
			libroDiarioDetalle.setBdHaberSoles(expedienteCredito.getBdMontoSolicitado().multiply(tipoCambioActual.getBdCompra()));
			libroDiarioDetalle.setBdHaberExtranjero(expedienteCredito.getBdMontoSolicitado());
		}
		return libroDiarioDetalle;
	}
	
}