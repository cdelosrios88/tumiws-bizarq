package pe.com.tumi.tesoreria.egreso.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.tesoreria.egreso.bo.CierreDiarioArqueoBO;
import pe.com.tumi.tesoreria.egreso.bo.CierreDiarioArqueoBilleteBO;
import pe.com.tumi.tesoreria.egreso.bo.CierreDiarioArqueoDetalleBO;
import pe.com.tumi.tesoreria.egreso.bo.ControlFondoFijoBO;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoBillete;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoDetalleBO;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.service.IngresoService;

public class CierreDiarioArqueoService {
	
	protected static Logger log = Logger.getLogger(CierreDiarioArqueoService.class);
	
	ControlFondoFijoBO boControlFondosFijos = (ControlFondoFijoBO)TumiFactory.get(ControlFondoFijoBO.class);
	CierreDiarioArqueoBO boCierreDiarioArqueo = (CierreDiarioArqueoBO)TumiFactory.get(CierreDiarioArqueoBO.class);
	CierreDiarioArqueoDetalleBO boCierreDiarioArqueoDetalle = (CierreDiarioArqueoDetalleBO)TumiFactory.get(CierreDiarioArqueoDetalleBO.class);
	CierreDiarioArqueoBilleteBO boCierreDiarioArqueoBillete = (CierreDiarioArqueoBilleteBO)TumiFactory.get(CierreDiarioArqueoBilleteBO.class);
	IngresoService ingresoService = (IngresoService)TumiFactory.get(IngresoService.class);
	IngresoDetalleBO boIngresoDetalle = (IngresoDetalleBO)TumiFactory.get(IngresoDetalleBO.class);
	
	private Date obtenerDate(Timestamp timestamp)throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		return cal.getTime();
	}
	
	public CierreDiarioArqueoDetalle calcularCierreDiarioArqueoDetalleIngresos(CierreDiarioArqueo cierreDiarioArqueo)throws BusinessException{
		CierreDiarioArqueoDetalle cierreDiarioArqueoDetalleIngresos = null;
		try{
			cierreDiarioArqueoDetalleIngresos = new CierreDiarioArqueoDetalle();
			Integer intIdEmpresa = cierreDiarioArqueo.getId().getIntPersEmpresa();
			Integer intIdSucursal = cierreDiarioArqueo.getId().getIntSucuIdSucursal();
			Integer intIdSubsucursal = cierreDiarioArqueo.getId().getIntSudeIdSubsucursal();
			Date dtFechaCierre = obtenerDate(cierreDiarioArqueo.getTsFechaCierreArqueo()); //Limpia las horas, minutos segundos y milisegundos de la fecha a cerrar
			
			
			Ingreso ingresoFiltro = new Ingreso();
			ingresoFiltro.getId().setIntIdEmpresa(intIdEmpresa);
			ingresoFiltro.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			ingresoFiltro.setDtDechaDesde(dtFechaCierre);
			ingresoFiltro.setDtDechaHasta(null);
			ingresoFiltro.setIntSucuIdSucursal(intIdSucursal);
			ingresoFiltro.setIntSudeIdSubsucursal(intIdSubsucursal);
			ingresoFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			List<Ingreso> listaIngreso = ingresoService.buscarIngresoParaCaja(ingresoFiltro, null);
			BigDecimal bdMontoIngresos = new BigDecimal(0);
			for(Ingreso ingreso : listaIngreso){
				log.info("Ingresos Recuperados: "+ingreso);
				bdMontoIngresos = bdMontoIngresos.add(ingreso.getBdMontoTotal());
			}

			cierreDiarioArqueoDetalleIngresos.setBdMontoIngreso(bdMontoIngresos);
			
			Ingreso depositoFiltro = new Ingreso();
			depositoFiltro.getId().setIntIdEmpresa(intIdEmpresa);
			depositoFiltro.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO);
			depositoFiltro.setDtDechaDesde(dtFechaCierre);
			depositoFiltro.setDtDechaHasta(null);
			depositoFiltro.setIntSucuIdSucursal(intIdSucursal);
			depositoFiltro.setIntSudeIdSubsucursal(intIdSubsucursal);
			depositoFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			List<Ingreso> listaDeposito = ingresoService.buscarIngresoParaCaja(depositoFiltro, null);
			BigDecimal bdMontoDepositos = new BigDecimal(0);
			List<IngresoDetalle> listaAjuste = new ArrayList<IngresoDetalle>();
			for(Ingreso deposito : listaDeposito){
				log.info("Despósitos Recuperados: "+deposito);
				bdMontoDepositos = bdMontoDepositos.add(deposito.getBdMontoTotal());
				//Autor: jchavez / Tarea: Creación / Fecha: 14.08.2014 / Se agrega suma del monto Ajuste
				List<IngresoDetalle> lstAjuste = boIngresoDetalle.getPorIngreso(deposito);
				if (lstAjuste!=null && !lstAjuste.isEmpty()) {
					for (IngresoDetalle ingresoDetalle : lstAjuste) {
						listaAjuste.add(ingresoDetalle);					}
				}
				
			}
			BigDecimal bdMontoAjustes = new BigDecimal(0);
			if (listaAjuste!=null && !listaAjuste.isEmpty()) {
				for (IngresoDetalle ingresoDetalle : listaAjuste) {
					if (ingresoDetalle.getBdAjusteDeposito()!=null) {
						bdMontoAjustes = bdMontoAjustes.add(ingresoDetalle.getBdAjusteDeposito());
					}
				}
			}
			
			cierreDiarioArqueoDetalleIngresos.setBdMontoAjuste(bdMontoAjustes);
			//Fin jchavez - 14.08.2014
			
			cierreDiarioArqueoDetalleIngresos.setBdMontoDeposito(bdMontoDepositos);			
			//Autor: jchavez / Tarea: Modificación / Fecha: 14.08.2014 /
			cierreDiarioArqueoDetalleIngresos.setBdMontoIngPendiente(bdMontoIngresos.add(bdMontoAjustes).subtract(bdMontoDepositos));	
			//Fin jchavez - 14.08.2014
			
			//Autor: jchavez / Tarea: Modificación / Fecha: 14.08.2014 / Se quita, segun REUNION MOD TESORERIA 13.08.2014 
//			BigDecimal bdMontoSaldoEfectivoAnterior = new BigDecimal(0);
//			CierreDiarioArqueo cierreDiarioArqueoUltimo = boCierreDiarioArqueo.getCierreDiarioArqueoAnterior(cierreDiarioArqueo);			
//			if(cierreDiarioArqueoUltimo!=null){
//				cierreDiarioArqueoUltimo.setListaCierreDiarioArqueoDetalle(boCierreDiarioArqueoDetalle.getListaPorCierreDiarioArqueo(cierreDiarioArqueoUltimo));
//				for(CierreDiarioArqueoDetalle cierreDiarioArqueoDetalle : cierreDiarioArqueoUltimo.getListaCierreDiarioArqueoDetalle()){
//					if(cierreDiarioArqueoDetalle.getBdMontoApertura()==null){
//						bdMontoSaldoEfectivoAnterior = cierreDiarioArqueoDetalle.getBdMontoSaldoEfectivo();
//					}
//				}
//			}
//			
//			cierreDiarioArqueoDetalleIngresos.setBdMontoSaldoEfectivo(bdMontoSaldoEfectivoAnterior.add(cierreDiarioArqueoDetalleIngresos.getBdMontoIngPendiente()));			

		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cierreDiarioArqueoDetalleIngresos;
	}
	
	public Date obtenerFechaACerrar(CierreDiarioArqueo cierreDiarioArqueo)throws BusinessException{
		try{
			log.info(cierreDiarioArqueo);
			CierreDiarioArqueo cierreDiarioArqueoUltimo = boCierreDiarioArqueo.getCierreDiarioArqueoAnterior(cierreDiarioArqueo);
			
			Calendar calendar = Calendar.getInstance();
			if(cierreDiarioArqueoUltimo==null){
				return null;
			}
			calendar.setTime(cierreDiarioArqueoUltimo.getTsFechaCierreArqueo());
			calendar.add(Calendar.DATE, 1);
			//Obtenemos el calendario con el dia siguiente. Si el dia siguiente es domingo se va hasta lunes.
			if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)	calendar.add(Calendar.DATE, 1);
			
			return calendar.getTime();
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	private boolean existeCierreCaja(Integer intIdEmpresa, Date fechaValidar, Integer intIdSucursal, Integer intIdSubsucursal)throws Exception{
		boolean existeCierreCaja = Boolean.FALSE;
		
		CierreDiarioArqueo cierreDiarioArqueoFiltro = new CierreDiarioArqueo();
		cierreDiarioArqueoFiltro.getId().setIntPersEmpresa(intIdEmpresa);
		cierreDiarioArqueoFiltro.getId().setIntParaTipoArqueo(Constante.PARAM_T_TIPOARQUEO_CIERRECAJA);
		cierreDiarioArqueoFiltro.getId().setIntSucuIdSucursal(intIdSucursal);
		cierreDiarioArqueoFiltro.getId().setIntSudeIdSubsucursal(intIdSubsucursal);
		cierreDiarioArqueoFiltro.setTsFechaCierreArqueo(new Timestamp(fechaValidar.getTime()));
		cierreDiarioArqueoFiltro.setIntParaEstadoCierre(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		List<CierreDiarioArqueo> listaCierreDiarioArqueo = boCierreDiarioArqueo.getListaBuscar(cierreDiarioArqueoFiltro);
		
		if(listaCierreDiarioArqueo!=null && !listaCierreDiarioArqueo.isEmpty()){
			log.info("listaCierreDiarioArqueo size:"+listaCierreDiarioArqueo.size());
			if(listaCierreDiarioArqueo.size()==1){
				existeCierreCaja = Boolean.TRUE;
			}
		}
		
		log.info(fechaValidar+" "+existeCierreCaja);
		return existeCierreCaja;
	}
	
	private Date obtenerDiaAnterior(Date diaActual)throws Exception{		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(diaActual);
		calendar.add(Calendar.DATE, -1);
		//Obtenemos el calendario con el dia anterior. Si el dia anterior es domingo se va hasta sabado.
		if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)	calendar.add(Calendar.DATE, -1);
		
		return calendar.getTime();	
	}
	
	public boolean existeCierreDiaAnterior(Integer intIdEmpresa, Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException{
		boolean existeCierreDiaAnterior = Boolean.FALSE;
		try{			
			Date dtFechaDiaAnterior = obtenerDiaAnterior(new Date());
			
			existeCierreDiaAnterior = existeCierreCaja(intIdEmpresa, dtFechaDiaAnterior, intIdSucursal,intIdSubsucursal);			
			
			//Si la sucursal es de sede central tiene un dia extra de tolerancia en los cierres de caja
			if(!existeCierreDiaAnterior){
				EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
				Sucursal sucursal = new Sucursal();
				sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
				sucursal.getId().setIntIdSucursal(intIdSucursal);
				sucursal = empresaFacade.getSucursalPorPK(sucursal);
				if(sucursal.getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL)){
					dtFechaDiaAnterior = obtenerDiaAnterior(dtFechaDiaAnterior);
					existeCierreDiaAnterior = existeCierreCaja(intIdEmpresa, dtFechaDiaAnterior, intIdSucursal, intIdSubsucursal);
				}
			}
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return existeCierreDiaAnterior;
	}
	
	public boolean existeCierreDiaActual(Integer intIdEmpresa, Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException{
		boolean existeCierreDiaActual = Boolean.FALSE;
		try{
			existeCierreDiaActual = existeCierreCaja(intIdEmpresa, new Date(), intIdSucursal,intIdSubsucursal);			
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return existeCierreDiaActual;
	}

	
	public List<ControlFondosFijos> obtenerControlFondosFijosACerrar(Integer intIdEmpresa,Integer intIdSucursal, Integer intIdSubsucursal)
		throws BusinessException{
		List<ControlFondosFijos> listaControlFondosFijos = null;
		try{
			
			ControlFondosFijos controlFondosFijosFiltro = new ControlFondosFijos();
			controlFondosFijosFiltro.getId().setIntPersEmpresa(intIdEmpresa);
			controlFondosFijosFiltro.getId().setIntSucuIdSucursal(intIdSucursal);
			controlFondosFijosFiltro.setIntSudeIdSubsucursal(intIdSubsucursal);
			controlFondosFijosFiltro.setIntEstadoFondo(Constante.PARAM_T_ESTADOFONDO_ABIERTO);
			
			listaControlFondosFijos = boControlFondosFijos.getPorBusqueda(controlFondosFijosFiltro);
			//Agregado 12.12.2013 JCHAVEZ
			if (listaControlFondosFijos!=null && !listaControlFondosFijos.isEmpty()) {
				for(ControlFondosFijos cFF : listaControlFondosFijos){
					procesarItems(cFF);
				}
			}
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaControlFondosFijos;
	}
	
	private void procesarItems(ControlFondosFijos controlFondosFijos){
		controlFondosFijos.setStrNumeroApertura(
				obtenerPeriodoItem(	controlFondosFijos.getId().getIntItemPeriodoFondo(),
									controlFondosFijos.getId().getIntItemFondoFijo(),
									"000000"));				
	}
	
	private String obtenerPeriodoItem(Integer intPeriodo, Integer item, String patron){
		try{
			DecimalFormat formato = new DecimalFormat(patron);
			return intPeriodo+"-"+formato.format(Double.parseDouble(""+item));
		}catch (Exception e) {
			log.error(e.getMessage());
			return intPeriodo+"-"+item;
		}
	}
	
	
	public CierreDiarioArqueo grabarCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo)throws BusinessException{
		try{
			
			log.info(cierreDiarioArqueo);
			cierreDiarioArqueo = boCierreDiarioArqueo.grabar(cierreDiarioArqueo);
			
			for(CierreDiarioArqueoDetalle cierreDiarioArqueoDetalle : cierreDiarioArqueo.getListaCierreDiarioArqueoDetalle()){
				cierreDiarioArqueoDetalle.getId().setIntPersEmpresa(cierreDiarioArqueo.getId().getIntPersEmpresa());
				cierreDiarioArqueoDetalle.getId().setIntParaTipoArqueo(cierreDiarioArqueo.getId().getIntParaTipoArqueo());
				cierreDiarioArqueoDetalle.getId().setIntSucuIdSucursal(cierreDiarioArqueo.getId().getIntSucuIdSucursal());
				cierreDiarioArqueoDetalle.getId().setIntSudeIdSubsucursal(cierreDiarioArqueo.getId().getIntSudeIdSubsucursal());
				cierreDiarioArqueoDetalle.getId().setIntItemCierreDiarioArqueo(cierreDiarioArqueo.getId().getIntItemCierreDiarioArqueo());
				log.info(cierreDiarioArqueoDetalle);
				boCierreDiarioArqueoDetalle.grabar(cierreDiarioArqueoDetalle);
			}
			
			for(CierreDiarioArqueoBillete cierreDiarioArqueoBillete : cierreDiarioArqueo.getListaCierreDiarioArqueoBillete()){
				cierreDiarioArqueoBillete.getId().setIntPersEmpresa(cierreDiarioArqueo.getId().getIntPersEmpresa());
				cierreDiarioArqueoBillete.getId().setIntParaTipoArqueo(cierreDiarioArqueo.getId().getIntParaTipoArqueo());
				cierreDiarioArqueoBillete.getId().setIntSucuIdSucursal(cierreDiarioArqueo.getId().getIntSucuIdSucursal());
				cierreDiarioArqueoBillete.getId().setIntSudeIdSubsucursal(cierreDiarioArqueo.getId().getIntSudeIdSubsucursal());
				cierreDiarioArqueoBillete.getId().setIntItemCierreDiarioArqueo(cierreDiarioArqueo.getId().getIntItemCierreDiarioArqueo());
				log.info(cierreDiarioArqueoBillete);
				boCierreDiarioArqueoBillete.grabar(cierreDiarioArqueoBillete);
			}
		}catch (Exception e) {
			throw new BusinessException(e);
		}
		return cierreDiarioArqueo;
	}
	
	private ControlFondosFijos obtenerControlFondosFijos(CierreDiarioArqueoDetalle cierreDiarioArqueoDetalle)
	throws Exception{
		ControlFondosFijos controlFondosFijos = null;
		ControlFondosFijosId controlFondosFijosId = new ControlFondosFijosId();
		controlFondosFijosId.setIntPersEmpresa(cierreDiarioArqueoDetalle.getIntPersEmpresa());
		controlFondosFijosId.setIntParaTipoFondoFijo(cierreDiarioArqueoDetalle.getIntParaTipoFondoFijo());
		controlFondosFijosId.setIntItemPeriodoFondo(cierreDiarioArqueoDetalle.getIntItemPeriodoFondo());
		controlFondosFijosId.setIntSucuIdSucursal(cierreDiarioArqueoDetalle.getId().getIntSucuIdSucursal());
		controlFondosFijosId.setIntItemFondoFijo(cierreDiarioArqueoDetalle.getIntItemFondoFijo());
		
		controlFondosFijos = boControlFondosFijos.getPorPk(controlFondosFijosId);
		if(controlFondosFijos!=null) procesarItems(controlFondosFijos);
		
		return controlFondosFijos;
	}
	
	public List<CierreDiarioArqueo> buscarCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo, List<Persona> listaPersona)throws BusinessException{
		List<CierreDiarioArqueo> listaCierreDiarioArqueo = new ArrayList<CierreDiarioArqueo>();
		try{
			log.info(cierreDiarioArqueo);
			listaCierreDiarioArqueo = boCierreDiarioArqueo.getListaBuscar(cierreDiarioArqueo);
			
			if(listaPersona!=null && !listaPersona.isEmpty()){
				List<CierreDiarioArqueo> listaCierreDiarioArqueoTemp = new ArrayList<CierreDiarioArqueo>();
				for(CierreDiarioArqueo cierreDiarioArqueoTemp : listaCierreDiarioArqueo){
					for(Persona persona : listaPersona){
						if(cierreDiarioArqueoTemp.getIntPersPersonaResponsable().equals(persona.getIntIdPersona())){
							listaCierreDiarioArqueoTemp.add(cierreDiarioArqueoTemp);
							break;
						}
					}					
				}
				listaCierreDiarioArqueo = listaCierreDiarioArqueoTemp;
			}
			
			for(CierreDiarioArqueo cierreDiarioArqueoTemp : listaCierreDiarioArqueo){
				cierreDiarioArqueoTemp.setListaCierreDiarioArqueoDetalle(boCierreDiarioArqueoDetalle.getListaPorCierreDiarioArqueo(cierreDiarioArqueoTemp));
				cierreDiarioArqueoTemp.setListaCierreDiarioArqueoBillete(boCierreDiarioArqueoBillete.getListaPorCierreDiarioArqueo(cierreDiarioArqueoTemp));
				
				for(CierreDiarioArqueoDetalle cierreDiarioArqueoDetalle : cierreDiarioArqueoTemp.getListaCierreDiarioArqueoDetalle()){
					ControlFondosFijos controlFondosFijos = obtenerControlFondosFijos(cierreDiarioArqueoDetalle);
					if(controlFondosFijos!=null)cierreDiarioArqueoDetalle.setStrNumeroApertura(controlFondosFijos.getStrNumeroApertura());
					if (cierreDiarioArqueoDetalle.getBdMontoIngPendiente()!=null) {
						cierreDiarioArqueoTemp.setBdMontoIngresoPendiente(cierreDiarioArqueoDetalle.getBdMontoIngPendiente());
					}
				}
			}
		}catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaCierreDiarioArqueo;
	}
	/**
	 * Agregado 11.12.2013 JCHAVEZ 
	 * Valida la existencia de al menos un registro en Cierre Diario Arqueo por 
	 * @param intIdEmpresa
	 * @param intIdSucursal
	 * @param intIdSubsucursal
	 * @return
	 * @throws Exception
	 */
	public boolean existeCierreDiarioArqueo(Integer intIdEmpresa, Integer intIdSucursal, Integer intIdSubsucursal) throws Exception{
		boolean existeCierre = Boolean.FALSE;
		try {
			CierreDiarioArqueo cierreDiarioArqueoFiltro = new CierreDiarioArqueo();
			cierreDiarioArqueoFiltro.getId().setIntPersEmpresa(intIdEmpresa);
			cierreDiarioArqueoFiltro.getId().setIntParaTipoArqueo(Constante.PARAM_T_TIPOARQUEO_CIERRECAJA);
			cierreDiarioArqueoFiltro.getId().setIntSucuIdSucursal(intIdSucursal);
			cierreDiarioArqueoFiltro.getId().setIntSudeIdSubsucursal(intIdSubsucursal);
			cierreDiarioArqueoFiltro.setTsFechaCierreArqueo(null);
			cierreDiarioArqueoFiltro.setIntParaEstadoCierre(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			List<CierreDiarioArqueo> listaCierreDiarioArqueo = boCierreDiarioArqueo.getListaBuscar(cierreDiarioArqueoFiltro);
			
			if(listaCierreDiarioArqueo!=null && !listaCierreDiarioArqueo.isEmpty()){
				existeCierre = Boolean.TRUE;
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}		
		log.info("Existe algun registro?? "+existeCierre);
		return existeCierre;
	}
	/**
	 * Agregado 13.12.2013 JCHAVEZ 
	 * Busca ingresos y depositos cuando se va a realizar un cierre diario y arqueo por primera vez.(no existe ningun cierre previo)
	 * @param cierreDiarioArqueo
	 * @return
	 * @throws BusinessException
	 */
	public CierreDiarioArqueoDetalle obtenerCierreDiarioArqueoDetalleIngresos(CierreDiarioArqueo cierreDiarioArqueo)throws BusinessException{
		CierreDiarioArqueoDetalle cierreDiarioArqueoDetalleIngresos = null;
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
		try{
			cierreDiarioArqueoDetalleIngresos = new CierreDiarioArqueoDetalle();
			Integer intIdEmpresa = cierreDiarioArqueo.getId().getIntPersEmpresa();
			Integer intIdSucursal = cierreDiarioArqueo.getId().getIntSucuIdSucursal();
			Integer intIdSubsucursal = cierreDiarioArqueo.getId().getIntSudeIdSubsucursal();
			
			Ingreso ingresoFiltro = new Ingreso();
			ingresoFiltro.getId().setIntIdEmpresa(intIdEmpresa);
			ingresoFiltro.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA);
			ingresoFiltro.setDtDechaDesde(null);
			ingresoFiltro.setDtDechaHasta(null);
			ingresoFiltro.setIntSucuIdSucursal(intIdSucursal);
			ingresoFiltro.setIntSudeIdSubsucursal(intIdSubsucursal);
			ingresoFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			List<Ingreso> listaIngreso = ingresoService.buscarIngresoParaCaja(ingresoFiltro, null);
			BigDecimal bdMontoIngresos = new BigDecimal(0);
			Integer cont = 0;
			String strFechaRegistro = null;
			
			for(Ingreso ingreso : listaIngreso){
				if (cont == 0) {
					strFechaRegistro = formatoDeFecha.format(ingreso.getDtFechaIngreso());
				}else{
					if (formatoDeFecha.parse(strFechaRegistro).compareTo(ingreso.getDtFechaIngreso())<0) {
						strFechaRegistro = ingreso.getDtFechaIngreso().toString();
					}
				}					
				log.info("Ingreso Recuperado: "+ingreso);
				bdMontoIngresos = bdMontoIngresos.add(ingreso.getBdMontoTotal());
			}
			cierreDiarioArqueoDetalleIngresos.setStrFechaRegistro(strFechaRegistro);
			cierreDiarioArqueoDetalleIngresos.setBdMontoIngreso(bdMontoIngresos);
			
			Ingreso depositoFiltro = new Ingreso();
			depositoFiltro.getId().setIntIdEmpresa(intIdEmpresa);
			depositoFiltro.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO);
			depositoFiltro.setDtDechaDesde(null);
			depositoFiltro.setDtDechaHasta(null);
			depositoFiltro.setIntSucuIdSucursal(intIdSucursal);
			depositoFiltro.setIntSudeIdSubsucursal(intIdSubsucursal);
			depositoFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			List<Ingreso> listaDeposito = ingresoService.buscarIngresoParaCaja(depositoFiltro, null);
			BigDecimal bdMontoDepositos = new BigDecimal(0);
			List<IngresoDetalle> listaAjuste = new ArrayList<IngresoDetalle>();
			for(Ingreso deposito : listaDeposito){
				log.info("Depósito Recuperado: "+deposito);
				bdMontoDepositos = bdMontoDepositos.add(deposito.getBdMontoTotal());
				//Autor: jchavez / Tarea: Creación / Fecha: 14.08.2014 / Se agrega suma del monto Ajuste
				List<IngresoDetalle> lstAjuste = boIngresoDetalle.getPorIngreso(deposito);
				if (lstAjuste!=null && !lstAjuste.isEmpty()) {
					for (IngresoDetalle ingresoDetalle : lstAjuste) {
						listaAjuste.add(ingresoDetalle);					}
				}
				
			}
			BigDecimal bdMontoAjustes = new BigDecimal(0);
			if (listaAjuste!=null && !listaAjuste.isEmpty()) {
				for (IngresoDetalle ingresoDetalle : listaAjuste) {
					if (ingresoDetalle.getBdAjusteDeposito()!=null) {
						bdMontoAjustes = bdMontoAjustes.add(ingresoDetalle.getBdAjusteDeposito());
					}
				}
			}
			
			cierreDiarioArqueoDetalleIngresos.setBdMontoAjuste(bdMontoAjustes);
			//Fin jchavez - 14.08.2014
			cierreDiarioArqueoDetalleIngresos.setBdMontoDeposito(bdMontoDepositos);			
			//Autor: jchavez / Tarea: Modificación / Fecha: 14.08.2014 / 
			cierreDiarioArqueoDetalleIngresos.setBdMontoIngPendiente(bdMontoIngresos.add(bdMontoAjustes).subtract(bdMontoDepositos));	
			//Fin jchavez - 14.08.2014
			
			//Autor: jchavez / Tarea: Modificación / Fecha: 14.08.2014 / Se quita, segun REUNION MOD TESORERIA 13.08.2014
//			BigDecimal bdMontoSaldoEfectivoAnterior = new BigDecimal(0);
//			CierreDiarioArqueo cierreDiarioArqueoUltimo = boCierreDiarioArqueo.getCierreDiarioArqueoAnterior(cierreDiarioArqueo);			
//			if(cierreDiarioArqueoUltimo!=null){
//				cierreDiarioArqueoUltimo.setListaCierreDiarioArqueoDetalle(boCierreDiarioArqueoDetalle.getListaPorCierreDiarioArqueo(cierreDiarioArqueoUltimo));
//				for(CierreDiarioArqueoDetalle cierreDiarioArqueoDetalle : cierreDiarioArqueoUltimo.getListaCierreDiarioArqueoDetalle()){
//					if(cierreDiarioArqueoDetalle.getBdMontoApertura()==null){
//						bdMontoSaldoEfectivoAnterior = cierreDiarioArqueoDetalle.getBdMontoSaldoEfectivo();
//					}
//				}
//			}
			
//			cierreDiarioArqueoDetalleIngresos.setBdMontoSaldoEfectivo(bdMontoSaldoEfectivoAnterior.add(cierreDiarioArqueoDetalleIngresos.getBdMontoIngPendiente()));			
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cierreDiarioArqueoDetalleIngresos;
	}
}