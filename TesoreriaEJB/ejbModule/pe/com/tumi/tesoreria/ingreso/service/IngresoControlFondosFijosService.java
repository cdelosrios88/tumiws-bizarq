package pe.com.tumi.tesoreria.ingreso.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.bo.ControlFondoFijoBO;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalleId;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;

public class IngresoControlFondosFijosService {

	protected static Logger log = Logger.getLogger(IngresoControlFondosFijosService.class);
	
	IngresoService ingresoService = (IngresoService)TumiFactory.get(IngresoService.class);
	ControlFondoFijoBO boControlFondosFijos = (ControlFondoFijoBO)TumiFactory.get(ControlFondoFijoBO.class);
	
	public Ingreso generarIngreso(ControlFondosFijos controlFondosFijos, Bancofondo bancoFondo, Usuario usuario, String strObservacion, Bancofondo bancoFondoIngreso) 
		throws BusinessException{
		Ingreso ingreso = new Ingreso();
		Integer intDocumentoGeneral = 0;
		try{
			BancoFacadeLocal bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			if (controlFondosFijos.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_FONDOCAMBIO)) { //(2)
				intDocumentoGeneral = Constante.PARAM_T_DOCUMENTOGENERAL_FONDOCAMBIO;
			}
			if (controlFondosFijos.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_CAJACHICA)) { //(3)
				intDocumentoGeneral = Constante.PARAM_T_DOCUMENTOGENERAL_CAJACHICA;
			}
			if (controlFondosFijos.getId().getIntParaTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR)) { //(4)
				intDocumentoGeneral = Constante.PARAM_T_DOCUMENTOGENERAL_ENTREGARENDIR;
			}
			
			Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
			Sucursal sucursal = usuario.getSucursal();
			Subsucursal	subsucursal = usuario.getSubSucursal();
			PlanCuenta planCuenta  = bancoFondo.getFondoDetalleUsar().getPlanCuenta();
			Bancofondo bancoFondoControl = bancoFacade.obtenerBancoFondoParaIngresoDesdeControl(controlFondosFijos);
			Sucursal getSucursal = empresaFacade.getSucursalPorId(controlFondosFijos.getId().getIntSucuIdSucursal());
			
			ingreso.setId(new IngresoId());
			ingreso.getId().setIntIdEmpresa(intIdEmpresa);
			ingreso.getId().setIntIdIngresoGeneral(null);
			ingreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_INGRESO);
			ingreso.setIntParaFormaPago(Constante.PARAM_T_PAGOINGRESO_EFECTIVO);
			ingreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			ingreso.setIntItemPeriodoIngreso(obtenerPeriodoActual());
			ingreso.setIntItemIngreso(null);
			ingreso.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			ingreso.setIntSudeIdSubsucursal(subsucursal.getId().getIntIdSubSucursal());
			ingreso.setIntParaTipoSuboperacion(Constante.PARAM_T_TIPODESUBOPERACION_CIERRE);
			ingreso.setTsFechaProceso(obtenerFechaActual());
			ingreso.setDtFechaIngreso(obtenerFechaActual());
			//Autor:jchavez / Tarea: modificacion / Fecha: 01.10.2014
			ingreso.setIntParaFondoFijo(bancoFondoIngreso.getIntTipoFondoFijo());
			ingreso.setIntItemBancoFondo(bancoFondoIngreso.getId().getIntItembancofondo());
			//Fin jchavez - 01.10.2014
			ingreso.setIntItemBancoCuenta(null);
			ingreso.setStrNumeroCheque(null);
			ingreso.setIntPersEmpresaGirado(intIdEmpresa);
			/*
			 * Modificado 11.12.2013 JCHAVEZ
			 * PERS_PERSONAGIRADO_N_PK : debe colocar el codigo de persona de la sucursal de quien se realiza el cierre
			 */			
			ingreso.setIntPersPersonaGirado(getSucursal.getIntPersPersonaPk());
			ingreso.setIntCuentaGirado(null);
			ingreso.setIntParaTipoMoneda(bancoFondo.getIntMonedaCod());
			ingreso.setBdTipoCambio(null);
			ingreso.setBdMontoTotal(controlFondosFijos.getBdMontoSaldo());
			ingreso.setStrObservacion(strObservacion);
			ingreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			ingreso.setTsFechaRegistro(obtenerFechaActual());
			ingreso.setIntPersEmpresaUsuario(intIdEmpresa);
			ingreso.setIntPersPersonaUsuario(usuario.getPersona().getIntIdPersona());
			ingreso.setIntParaEstadoDepositado(Constante.PARAM_T_ESTADODEPOSITADO_PENDIENTE);
			ingreso.setIntPersEmpresa(controlFondosFijos.getId().getIntPersEmpresa());
			ingreso.setIntParaTipoFondoFijoCan(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			ingreso.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			ingreso.setIntIdSucursalCan(controlFondosFijos.getId().getIntSucuIdSucursal());
			ingreso.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
			
			
			IngresoDetalle ingresoDetalle = new IngresoDetalle();
			ingresoDetalle.setId(new IngresoDetalleId());
			ingreso.setListaIngresoDetalle(new ArrayList<IngresoDetalle>());
			ingresoDetalle.getId().setIntPersEmpresa(intIdEmpresa);
			ingresoDetalle.getId().setIntIdIngresoGeneral(null);
			ingresoDetalle.getId().setIntIdIngresoDetalle(null);
			
			ingresoDetalle.setIntParaDocumentoGeneral(intDocumentoGeneral);
//			ingresoDetalle.setIntParaDocumentoGeneral(controlFondosFijos.getIntDocumentoGeneral());
			ingresoDetalle.setIntParaTipoComprobante(null);
			ingresoDetalle.setStrSerieDocumento(null);
			ingresoDetalle.setStrNumeroDocumento(controlFondosFijos.getStrNumeroApertura());
			ingresoDetalle.setStrDescripcionIngreso(planCuenta.getStrDescripcion());
			ingresoDetalle.setIntPersEmpresaGirado(intIdEmpresa);
			/*
			 * Modificado 11.12.2013 JCHAVEZ
			 * PERS_PERSONAGIRADO_N_PK : debe colocar el codigo de persona de la sucursal de quien se realiza el cierre
			 */			
			ingresoDetalle.setIntPersPersonaGirado(getSucursal.getIntPersPersonaPk());
			ingresoDetalle.setIntCuentaGirado(null);
			ingresoDetalle.setIntParaTipoPagoCuenta(null);
			ingresoDetalle.setIntSucuIdSucursalIn(controlFondosFijos.getId().getIntSucuIdSucursal());
			ingresoDetalle.setIntSucuIdSubsucursalIn(controlFondosFijos.getIntSudeIdSubsucursal());
			ingresoDetalle.setIntParaTipoMoneda(bancoFondo.getIntMonedaCod());
			ingresoDetalle.setBdAjusteDeposito(null);
			ingresoDetalle.setBdMontoCargo(null);
			ingresoDetalle.setBdMontoAbono(controlFondosFijos.getBdMontoSaldo());
			ingresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			ingresoDetalle.setTsFechaRegistro(obtenerFechaActual());
			ingresoDetalle.setIntPersEmpresaUsuario(intIdEmpresa);
			ingresoDetalle.setIntPersPersonaUsuario(usuario.getPersona().getIntIdPersona());
			ingresoDetalle.setIntPersEmpresa(controlFondosFijos.getId().getIntPersEmpresa());
			ingresoDetalle.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			ingresoDetalle.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			ingresoDetalle.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
			ingresoDetalle.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
			//Agregado 11.12.2013 JCHAVEZ  llave de Plan de Cuenta
			ingresoDetalle.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
			ingresoDetalle.setIntContPeriodoCuenta(planCuenta.getId().getIntPeriodoCuenta());
			ingresoDetalle.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());

			ingreso.getListaIngresoDetalle().add(ingresoDetalle);

			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa(strObservacion);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			
			
			LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
			libroDiarioDetalleHaber.setId(new LibroDiarioDetalleId());			
			libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioDetalleHaber.setIntPersEmpresaCuenta(bancoFondoControl.getFondoDetalleUsar().getPlanCuenta().getId().getIntEmpresaCuentaPk());
			libroDiarioDetalleHaber.setIntContPeriodo(bancoFondoControl.getFondoDetalleUsar().getPlanCuenta().getId().getIntPeriodoCuenta());
			libroDiarioDetalleHaber.setStrContNumeroCuenta(bancoFondoControl.getFondoDetalleUsar().getPlanCuenta().getId().getStrNumeroCuenta());
			libroDiarioDetalleHaber.setIntPersPersona(null);
			libroDiarioDetalleHaber.setIntParaDocumentoGeneral(intDocumentoGeneral);

			libroDiarioDetalleHaber.setStrSerieDocumento(null);
			libroDiarioDetalleHaber.setStrNumeroDocumento(controlFondosFijos.getStrNumeroApertura());
			libroDiarioDetalleHaber.setIntPersEmpresaSucursal(intIdEmpresa);
			libroDiarioDetalleHaber.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
			libroDiarioDetalleHaber.setIntSudeIdSubSucursal(controlFondosFijos.getIntSudeIdSubsucursal());
			libroDiarioDetalleHaber.setIntParaMonedaDocumento(bancoFondo.getIntMonedaCod());
			libroDiarioDetalleHaber.setBdHaberSoles(controlFondosFijos.getBdMontoSaldo());
			libroDiarioDetalleHaber.setBdDebeSoles(null);
			libroDiarioDetalleHaber.setBdHaberExtranjero(null);
			libroDiarioDetalleHaber.setBdDebeExtranjero(null);
			//Autor: jchavez / Tarea: se agrega descripcion de la cuenta / Fecha: 01.10.2014
			libroDiarioDetalleHaber.setStrComentario(bancoFondoControl.getFondoDetalleUsar().getPlanCuenta().getStrDescripcion());
			//Fin jchavez - 01.10.2014
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleHaber);
			
			
			LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
			libroDiarioDetalleDebe.setId(new LibroDiarioDetalleId());
			libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioDetalleDebe.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
			libroDiarioDetalleDebe.setIntContPeriodo(planCuenta.getId().getIntPeriodoCuenta());
			libroDiarioDetalleDebe.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());
			libroDiarioDetalleDebe.setIntPersPersona(null);
			libroDiarioDetalleDebe.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			libroDiarioDetalleDebe.setStrSerieDocumento(null);
			libroDiarioDetalleDebe.setStrNumeroDocumento(null);
			libroDiarioDetalleDebe.setIntPersEmpresaSucursal(intIdEmpresa);
			libroDiarioDetalleDebe.setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
			libroDiarioDetalleDebe.setIntSudeIdSubSucursal(subsucursal.getId().getIntIdSubSucursal());
			libroDiarioDetalleDebe.setIntParaMonedaDocumento(bancoFondo.getIntMonedaCod());
			libroDiarioDetalleDebe.setBdHaberSoles(null);
			libroDiarioDetalleDebe.setBdDebeSoles(controlFondosFijos.getBdMontoSaldo());			
			libroDiarioDetalleDebe.setBdHaberExtranjero(null);
			libroDiarioDetalleDebe.setBdDebeExtranjero(null);
			//Autor: jchavez / Tarea: se agrega descripcion de la cuenta / Fecha: 01.10.2014
			libroDiarioDetalleDebe.setStrComentario(planCuenta.getStrDescripcion());
			//Fin jchavez - 01.10.2014
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
			
			ingreso.setLibroDiario(libroDiario);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ingreso;
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
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}	
	
	public Ingreso grabarIngresoCierreFondos(ControlFondosFijos controlFondosFijos, Bancofondo bancoFondo, Usuario usuario, String strObservacion, Bancofondo bancoFondoIngreso) throws BusinessException{
		Ingreso ingreso = null;
		try{
			ingreso = generarIngreso(controlFondosFijos, bancoFondo, usuario, strObservacion, bancoFondoIngreso);			
			
			ingresoService.grabarIngresoGeneral(ingreso);
			
			boControlFondosFijos.modificar(controlFondosFijos);			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ingreso;
	}

}