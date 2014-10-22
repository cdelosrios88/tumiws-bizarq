/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-005       			19/10/2014     Christian De los Ríos        Se modificó el método de anulación de saldos         
*/
package pe.com.tumi.tesoreria.egreso.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.Fondodetalle;
import pe.com.tumi.tesoreria.egreso.bo.CierreDiarioArqueoBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoDetalleBO;
import pe.com.tumi.tesoreria.egreso.bo.SaldoBO;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.Saldo;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.facade.IngresoFacadeLocal;

public class SaldoService {

	protected static Logger log = Logger.getLogger(SaldoService.class);
	
	EgresoBO boEgreso = (EgresoBO)TumiFactory.get(EgresoBO.class);
	EgresoDetalleBO boEgresoDetalle = (EgresoDetalleBO)TumiFactory.get(EgresoDetalleBO.class);
	SaldoBO boSaldo = (SaldoBO)TumiFactory.get(SaldoBO.class);
	CierreDiarioArqueoBO boCierreDiarioArqueo = (CierreDiarioArqueoBO)TumiFactory.get(CierreDiarioArqueoBO.class);
	
	
	public List<Saldo> buscarSaldo(Saldo saldoFiltro)throws BusinessException{
		List<Saldo> listaSaldo = null;
		try{
			Integer intIdEmpresa = saldoFiltro.getId().getIntPersEmpresa();
			Integer intIdSucursal = saldoFiltro.getId().getIntSucuIdSucursal();
			
			listaSaldo = boSaldo.getListaPorBuscar(saldoFiltro);
			
			if(intIdSucursal!=null && intIdSucursal.intValue()>0){
				List<Saldo> listaSaldoTemp = new ArrayList<Saldo>();
				for(Saldo saldo : listaSaldo){
					if(saldo.getId().getIntSucuIdSucursal().equals(intIdSucursal)){
						listaSaldoTemp.add(saldo);
					}
				}
				listaSaldo = listaSaldoTemp;
			}
			if(intIdSucursal!=null && intIdSucursal.intValue()<0){
				Integer intIdTotalSucursal = intIdSucursal;
				EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
				
				List<Saldo> listaSaldoTemp = new ArrayList<Saldo>();
				for(Saldo saldo : listaSaldo){
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
					sucursal.getId().setIntIdSucursal(saldo.getId().getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					if(empresaFacade.validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intIdTotalSucursal)){
						listaSaldoTemp.add(saldo);
					}
				}
				listaSaldo = listaSaldoTemp;
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaSaldo;
	}
	
	public void procesarSaldo(Date dtFechaInicio, Date dtFechaFin, Usuario usuario, List<Bancofondo> listaBanco, List<Bancofondo> listaFondo) 
	throws BusinessException{
		try{
			Integer intIdEmpresa = usuario.getPerfil().getId().getIntPersEmpresaPk();
			
			List<Sucursal> listaSucursal = cargarSucursal(intIdEmpresa);
			List<Date> listaDias = obtenerFechasDiariasIntervalo(dtFechaInicio, dtFechaFin);
			List<Saldo> listaSaldoContabilidad = new ArrayList<Saldo>();
			List<Saldo> listaSaldoTesoreria = new ArrayList<Saldo>();
			List<LibroDiarioDetalle> listaLibroDiarioDetalle = null;
			List<Ingreso> listaIngreso = null;
			List<Egreso> listaEgreso = null;
			
			
			for(Date dtFecha : listaDias){
				log.info(dtFecha);
				listaLibroDiarioDetalle = obtenerListaLibroDiarioDetallePorFecha(intIdEmpresa, dtFecha);				
				listaSaldoContabilidad.addAll(procesarSaldoContabilidadBancos(dtFecha, listaBanco, listaSucursal, listaLibroDiarioDetalle, usuario));
				listaSaldoContabilidad.addAll(procesarSaldoContabilidadFondos(dtFecha, listaFondo, listaSucursal, listaLibroDiarioDetalle, usuario));
				
				listaIngreso = obtenerListaIngresoPorFecha(intIdEmpresa, dtFecha);
				listaEgreso = obtenerListaEgresoPorFecha(intIdEmpresa, dtFecha);
				listaSaldoTesoreria.addAll(procesarSaldoTesoreriaBancos(dtFecha, listaIngreso, listaEgreso, listaSucursal, listaBanco, usuario));
				listaSaldoTesoreria.addAll(procesarSaldoTesoreriaFondos(dtFecha, listaIngreso, listaEgreso, listaSucursal, listaFondo, usuario));
				
				log.info("listaSaldoContabilidad.size:"+listaSaldoContabilidad.size());
			}
			
			log.info("--contabilidad");
			for(Saldo saldo : listaSaldoContabilidad){
				/*if(saldo.getListaLibroDiarioDetalle()!=null && !saldo.getListaLibroDiarioDetalle().isEmpty()){
					log.info(saldo);
					for(LibroDiarioDetalle ldd : saldo.getListaLibroDiarioDetalle()){
						log.info(ldd);
					}
				}*/
				log.info(saldo);
				boSaldo.grabar(saldo);
			}
			
			log.info("--tesoreria");
			for(Saldo saldo : listaSaldoTesoreria){
				log.info(saldo);
				/*if((saldo.getListaIngreso()!=null && !saldo.getListaIngreso().isEmpty()) 
				|| (saldo.getListaEgreso()!=null && !saldo.getListaEgreso().isEmpty())){
					log.info(saldo);
					for(Ingreso ingreso : saldo.getListaIngreso()){
						log.info(ingreso);
					}
					for(Egreso engreso : saldo.getListaEgreso()){
						log.info(engreso);
					}
				}*/
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	private List<Saldo> procesarSaldoContabilidadBancos(Date dtFecha, List<Bancofondo> listaBanco, List<Sucursal> listaSucursal,
		List<LibroDiarioDetalle> listaLibroDiarioDetalle, Usuario usuario)throws Exception{
			
		List<Saldo> listaSaldo = new ArrayList<Saldo>();
		List<LibroDiarioDetalle> listaLDDGenerarSaldo = new ArrayList<LibroDiarioDetalle>();
			
		for(Sucursal sucursal : listaSucursal){
			if(!sucursal.getId().getIntIdSucursal().equals(Constante.SUCURSAL_SEDECENTRAL)) continue;
			
			for(Subsucursal subsucursal : sucursal.getListaSubSucursal()){
				if(subsucursal.getId().getIntIdSubSucursal().equals(Constante.SUBSUCURSAL_SEDE1))continue;
				
				for(Bancofondo banco : listaBanco){
					for(Bancocuenta bancoCuenta : banco.getListaBancocuenta()){
						listaLDDGenerarSaldo = new ArrayList<LibroDiarioDetalle>();
						for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle){
							if(libroDiarioDetalle.getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())
							&& libroDiarioDetalle.getIntSudeIdSubSucursal().equals(subsucursal.getId().getIntIdSubSucursal())
							&& libroDiarioDetalle.getIntPersEmpresaCuenta().equals(bancoCuenta.getIntEmpresacuentaPk())
							&& libroDiarioDetalle.getIntContPeriodo().equals(bancoCuenta.getIntPeriodocuenta())
							&& libroDiarioDetalle.getStrContNumeroCuenta().equals(bancoCuenta.getStrNumerocuenta())){
								listaLDDGenerarSaldo.add(libroDiarioDetalle);	
							}
						}
						listaSaldo.add(generarSaldoContabilidad(listaLDDGenerarSaldo, sucursal, subsucursal, null, bancoCuenta, usuario, dtFecha));
					}
				}
			}
		}
		return listaSaldo;
	}
	
	private List<Saldo> procesarSaldoContabilidadFondos(Date dtFecha, List<Bancofondo> listaFondo, List<Sucursal> listaSucursal,
		List<LibroDiarioDetalle> listaLibroDiarioDetalle, Usuario usuario)throws Exception{
			
		List<Saldo> listaSaldo = new ArrayList<Saldo>();
		List<LibroDiarioDetalle> listaLDDGenerarSaldo = new ArrayList<LibroDiarioDetalle>();
			
		for(Sucursal sucursal : listaSucursal){
			for(Subsucursal subsucursal : sucursal.getListaSubSucursal()){
				for(Bancofondo fondo : listaFondo){
					for(Fondodetalle fondoDetalle : fondo.getListaFondodetalle()){
						if(!fondoDetalle.getIntCodigodetalle().equals(Constante.CODIGODETALLE_CUENTACONTABLE)){
							continue;
						}
						listaLDDGenerarSaldo = new ArrayList<LibroDiarioDetalle>();
						for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle){
							if(libroDiarioDetalle.getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())
							&& libroDiarioDetalle.getIntSudeIdSubSucursal().equals(subsucursal.getId().getIntIdSubSucursal())
							&& libroDiarioDetalle.getIntPersEmpresaCuenta().equals(fondoDetalle.getIntEmpresacuentaPk())
							&& libroDiarioDetalle.getIntContPeriodo().equals(fondoDetalle.getIntPeriodocuenta())
							&& libroDiarioDetalle.getStrContNumeroCuenta().equals(fondoDetalle.getStrNumerocuenta())){
								listaLDDGenerarSaldo.add(libroDiarioDetalle);
							}
						}						
					}
					listaSaldo.add(generarSaldoContabilidad(listaLDDGenerarSaldo, sucursal, subsucursal, fondo, null, usuario, dtFecha));
				}
			}
		}
		return listaSaldo;
	}
	
	private List<Saldo> procesarSaldoTesoreriaBancos(Date dtFecha, List<Ingreso> listaIngreso, List<Egreso> listaEgreso, 
			List<Sucursal> listaSucursal, List<Bancofondo> listaBanco, Usuario usuario)throws Exception{
			
			List<Saldo> listaSaldo = new ArrayList<Saldo>();
			List<Ingreso> listaIngresoGenerarSaldo = new ArrayList<Ingreso>();
			List<Egreso> listaEgresoGenerarSaldo = new ArrayList<Egreso>();
			for(Sucursal sucursal : listaSucursal){
				if(!sucursal.getId().getIntIdSucursal().equals(Constante.SUCURSAL_SEDECENTRAL)) continue;
				
				for(Subsucursal subsucursal : sucursal.getListaSubSucursal()){
					if(subsucursal.getId().getIntIdSubSucursal().equals(Constante.SUBSUCURSAL_SEDE1))continue;
					
					for(Bancofondo banco : listaBanco){
						for(Bancocuenta bancoCuenta : banco.getListaBancocuenta()){
							listaIngresoGenerarSaldo = new ArrayList<Ingreso>();
							listaEgresoGenerarSaldo = new ArrayList<Egreso>();
							for(Ingreso ingreso : listaIngreso){
								if(ingreso.getIntItemBancoCuenta()!=null
								&& ingreso.getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())
								&& ingreso.getIntSudeIdSubsucursal().equals(subsucursal.getId().getIntIdSubSucursal())
								&& ingreso.getIntItemBancoFondo().equals(bancoCuenta.getId().getIntItembancofondo())
								&& ingreso.getIntItemBancoCuenta().equals(bancoCuenta.getId().getIntItembancocuenta())){
									listaIngresoGenerarSaldo.add(ingreso);
								}
							}
							for(Egreso egreso : listaEgreso){
								if(egreso.getIntItemBancoCuenta()!=null
								&& egreso.getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())
								&& egreso.getIntSudeIdSubsucursal().equals(subsucursal.getId().getIntIdSubSucursal())
								&& egreso.getIntItemBancoFondo().equals(bancoCuenta.getId().getIntItembancofondo())
								&& egreso.getIntItemBancoCuenta().equals(bancoCuenta.getId().getIntItembancocuenta())){
									listaEgresoGenerarSaldo.add(egreso);
								}
							}
							listaSaldo.add(generarSaldoTesoreria(listaIngresoGenerarSaldo, listaEgresoGenerarSaldo, usuario, sucursal, subsucursal, 
									null, bancoCuenta, dtFecha));
						}
					}
				}
			}
			return listaSaldo;
		}
	
	private List<Saldo> procesarSaldoTesoreriaFondos(Date dtFecha, List<Ingreso> listaIngreso, List<Egreso> listaEgreso, 
		List<Sucursal> listaSucursal, List<Bancofondo> listaFondo, Usuario usuario)throws Exception{
		
		List<Saldo> listaSaldo = new ArrayList<Saldo>();
		List<Ingreso> listaIngresoGenerarSaldo = new ArrayList<Ingreso>();
		List<Egreso> listaEgresoGenerarSaldo = new ArrayList<Egreso>();
		for(Sucursal sucursal : listaSucursal){
			for(Subsucursal subsucursal : sucursal.getListaSubSucursal()){
				for(Bancofondo fondo : listaFondo){
					listaIngresoGenerarSaldo = new ArrayList<Ingreso>();
					listaEgresoGenerarSaldo = new ArrayList<Egreso>();
					for(Ingreso ingreso : listaIngreso){
						if(ingreso.getIntParaFondoFijo()!=null
						&& ingreso.getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())
						&& ingreso.getIntSudeIdSubsucursal().equals(subsucursal.getId().getIntIdSubSucursal())
						&& ingreso.getIntParaFondoFijo().equals(fondo.getIntTipoFondoFijo())){
							listaIngresoGenerarSaldo.add(ingreso);
						}
					}
					for(Egreso egreso : listaEgreso){
						if(egreso.getIntParaTipoFondoFijo()!=null
						&& egreso.getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())
						&& egreso.getIntSudeIdSubsucursal().equals(subsucursal.getId().getIntIdSubSucursal())
						&& egreso.getIntParaTipoFondoFijo().equals(fondo.getIntTipoFondoFijo())){
							listaEgresoGenerarSaldo.add(egreso);
						}
					}
					listaSaldo.add(generarSaldoTesoreria(listaIngresoGenerarSaldo, listaEgresoGenerarSaldo, usuario, sucursal, subsucursal, 
							fondo, null, dtFecha));
				}
			}
		}
		return listaSaldo;
	}

	private Saldo generarSaldoContabilidad(List<LibroDiarioDetalle> listaLibroDiarioDetalle, Sucursal sucursal, Subsucursal subsucursal, 
		Bancofondo fondo, Bancocuenta bancoCuenta, Usuario usuario, Date dtFecha)throws BusinessException{
		Saldo saldo = new Saldo();
		try{
			BigDecimal bdDebeAcumulado = new BigDecimal(0);
			BigDecimal bdHaberAcumulado = new BigDecimal(0);
			for(LibroDiarioDetalle libroDiarioDetalle : listaLibroDiarioDetalle){
				if(libroDiarioDetalle.getBdDebeSoles()!=null)bdDebeAcumulado = bdDebeAcumulado.add(libroDiarioDetalle.getBdDebeSoles());
				if(libroDiarioDetalle.getBdHaberSoles()!=null)bdHaberAcumulado = bdHaberAcumulado.add(libroDiarioDetalle.getBdHaberSoles());
			}
			BigDecimal bdSaldo = bdDebeAcumulado.subtract(bdHaberAcumulado);
			
			saldo = generarSaldo(dtFecha, sucursal, subsucursal, bdSaldo, fondo, bancoCuenta, usuario);
			
			saldo.setListaLibroDiarioDetalle(listaLibroDiarioDetalle);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return saldo;
	}
	
	private Saldo generarSaldoTesoreria(List<Ingreso> listaIngreso, List<Egreso> listaEgreso, Usuario usuario, Sucursal sucursal, 
		Subsucursal subsucursal, Bancofondo fondo, Bancocuenta bancoCuenta, Date dtFecha)throws Exception{
		
		Saldo saldo = null;
		BigDecimal bdMontoSumar = new BigDecimal(0);
		BigDecimal bdMontoRestar = new BigDecimal(0);
		
		if(listaIngreso!=null && !listaIngreso.isEmpty()){
			for(Ingreso ingreso : listaIngreso){
				log.info(ingreso);
			}
		}
		
		if(listaEgreso!=null && !listaEgreso.isEmpty()){
			for(Egreso egreso : listaEgreso){
				log.info(egreso);
			}
		}
		
		if(fondo!=null && !fondo.getIntTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_CAJA)){
			//Se suman todas las aperturas de fondos
			for(Egreso egreso : listaEgreso){
				if(egreso.getIntParaSubTipoOperacion().equals(Constante.PARAM_T_TIPODESUBOPERACION_APERTURA)){
					EgresoDetalle egresoDetalle = egreso.getListaEgresoDetalle().get(0);
					bdMontoSumar = bdMontoSumar.add(egresoDetalle.getBdMontoCargo());
				}
			}
			
			//Se restan todos los egresos productos de operaciones de rendicion
			for(Egreso egreso : listaEgreso){
				if(egreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)){
					bdMontoRestar = bdMontoRestar.add(egreso.getBdMontoTotal());
				}
			}
			
			//Se restan todas las liquidaciones de fondos de cierre (generan ingresos)
			for(Ingreso ingreso : listaIngreso){
				IngresoDetalle ingresoDetalle = ingreso.getListaIngresoDetalle().get(0);
				if(ingreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA) 
				&& ingresoDetalle.getIntItemFondoFijo()!=null){
					bdMontoRestar = bdMontoRestar.add(ingreso.getBdMontoTotal());
				}
			}
		}
		
		if(fondo!=null && fondo.getIntTipoFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_CAJA)){
			//Se suman los ingresos con tipo de fondo fijo caja
			for(Ingreso ingreso : listaIngreso){
				if(ingreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA)
				&& ingreso.getIntParaFondoFijo().equals(Constante.PARAM_T_TIPOFONDOFIJO_CAJA)){
					bdMontoSumar = bdMontoSumar.add(ingreso.getBdMontoTotal());
				}
			}
			
			//Se restan los depositos
			for(Ingreso deposito : listaIngreso){
				if(deposito.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO)){
					for(IngresoDetalle depositoDetalle : deposito.getListaIngresoDetalle()){
						if(depositoDetalle.getBdAjusteDeposito()==null 
						&& depositoDetalle.getIntPersPersonaGirado().equals(deposito.getIntPersPersonaGirado())){
							bdMontoRestar = bdMontoRestar.add(depositoDetalle.getBdMontoAbono());
						}
					}
				}
			}
		}
		
		if(bancoCuenta!=null){
			for(Egreso egreso : listaEgreso){
				bdMontoRestar = bdMontoRestar.add(egreso.getBdMontoTotal());
			}
			for(Ingreso ingreso : listaIngreso){
				if(ingreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA))
					bdMontoSumar = bdMontoSumar.add(ingreso.getBdMontoTotal());
			}
		}
		
		BigDecimal bdSaldo = bdMontoSumar.subtract(bdMontoRestar);
		
		saldo = generarSaldo(dtFecha, sucursal, subsucursal, bdSaldo, fondo, bancoCuenta, usuario);
		
		saldo.setListaIngreso(listaIngreso);
		saldo.setListaEgreso(listaEgreso);
		
		return saldo;
	}	
	
	private Saldo generarSaldo(Date dtFecha, Sucursal sucursal, Subsucursal subsucursal, BigDecimal bdSaldo, Bancofondo fondo, 
		Bancocuenta bancoCuenta, Usuario usuario)throws Exception{
		Saldo saldo = new Saldo();
		
		Integer intIdEmpresa = usuario.getEmpresa().getIntIdEmpresa();
		saldo.getId().setIntPersEmpresa(intIdEmpresa);
		saldo.getId().setIntSucuIdSucursal(sucursal.getId().getIntIdSucursal());
		saldo.getId().setIntSudeIdSucursal(subsucursal.getId().getIntIdSubSucursal());
		saldo.getId().setDtFechaSaldo(dtFecha);
		saldo.setIntEmpresaPk(intIdEmpresa);
		if(bancoCuenta!=null){
			saldo.setIntItemBancoCuenta(bancoCuenta.getId().getIntItembancocuenta());
			saldo.setIntItemBancoFondo(bancoCuenta.getId().getIntItembancofondo());
			saldo.setIntParaTipoFondoFijo(null);
		}
		if(fondo!=null){
			saldo.setIntItemBancoCuenta(null);
			saldo.setIntItemBancoFondo(fondo.getId().getIntItembancofondo());
			saldo.setIntParaTipoFondoFijo(fondo.getIntTipoFondoFijo());
		}
		saldo.setBdSaldoInicial(null);
		saldo.setBdMovimientos(bdSaldo);
		saldo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		saldo.setIntPersEmpresaUsuario(intIdEmpresa);
		saldo.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
		saldo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		Saldo saldoAnterior = boSaldo.getSaldoAnterior(saldo);
		log.info("saldoAnterior "+saldoAnterior);
		if(saldoAnterior != null){
			saldo.setBdSaldoInicial(saldoAnterior.getBdMovimientos());
		}else{
			saldo.setBdSaldoInicial(new BigDecimal(0));
		}
		
		return saldo;
	}
	
	private List<Ingreso> obtenerListaIngresoPorFecha(Integer intIdEmpresa, Date dtFecha)throws Exception{
		IngresoFacadeLocal ingresoFacade = (IngresoFacadeLocal) EJBFactory.getLocal(IngresoFacadeLocal.class);
		
		Ingreso ingresoFiltro = new Ingreso();
		ingresoFiltro.getId().setIntIdEmpresa(intIdEmpresa);
		ingresoFiltro.setDtDechaDesde(dtFecha);
		ingresoFiltro.setDtDechaHasta(dtFecha);
		ingresoFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		List<Ingreso> listaIngreso = ingresoFacade.getListaIngresoParaBuscar(ingresoFiltro);
		for(Ingreso ingreso : listaIngreso){
			ingreso.setListaIngresoDetalle(ingresoFacade.getListaIngresoDetallePorIngreso(ingresoFiltro));
			log.info(ingreso);
		}
		return listaIngreso;
	}
	
	private List<Egreso> obtenerListaEgresoPorFecha(Integer intIdEmpresa, Date dtFecha)throws Exception{
		Egreso egresoFiltro = new Egreso();
		egresoFiltro.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoFiltro.setTsFechaRegistro(new Timestamp(dtFecha.getTime()));
		egresoFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		List<Egreso> listaEgreso = boEgreso.getListaPorBuscar(egresoFiltro, null, null);
		for(Egreso egreso : listaEgreso){
			egreso.setListaEgresoDetalle(boEgresoDetalle.getPorEgreso(egreso));
			log.info(egreso);
		}
		return listaEgreso;
	}

	
	//obtiene la lista de libroDiarioDetalle registrados en dtFecha
	private List<LibroDiarioDetalle> obtenerListaLibroDiarioDetallePorFecha(Integer intIdEmpresa, Date dtFecha)throws Exception{
		LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
		List<LibroDiarioDetalle> listaLibroDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
		
		LibroDiario libroDiarioFiltro = new LibroDiario();
		libroDiarioFiltro.setId(new LibroDiarioId());
		libroDiarioFiltro.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioFiltro.setTsFechaRegistro(new Timestamp(dtFecha.getTime()));
		
		List<LibroDiario> listaLibroDiario = libroDiarioFacade.buscarLibroDiario(libroDiarioFiltro);
		for(LibroDiario libroDiario : listaLibroDiario){
			listaLibroDiarioDetalle.addAll(libroDiarioFacade.getListaLibroDiarioDetallePorLibroDiario(libroDiario));			
		}
		/*for(LibroDiarioDetalle ldd : listaLibroDiarioDetalle){
			log.info(ldd);
		}*/
		return listaLibroDiarioDetalle;
	}
	
	private List<Sucursal> cargarSucursal(Integer intIdEmpresa)throws Exception{
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
		
		List<Sucursal>listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(intIdEmpresa);
		List<Subsucursal> listaSubsucursal = null;
		for(Sucursal sucursal : listaSucursal){
			listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(sucursal.getId().getIntIdSucursal());
			sucursal.setListaSubSucursal(listaSubsucursal);
		}
		return listaSucursal;
	}
	
	public List<Date> obtenerFechasDiariasIntervalo(Date dtFechaInicio, Date dtFechaFin) throws Exception{
		try{
			List<Date> listaFechas = new ArrayList<Date>();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dtFechaInicio);
			
			while (calendar.getTime().before(dtFechaFin) || calendar.getTime().equals(dtFechaFin)){
				Date resultado = calendar.getTime();
				listaFechas.add(resultado);
				calendar.add(Calendar.DATE, 1);
			}
		    return listaFechas;
		}catch(Exception e){
			throw e;
		}
	}
	
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	//public void anularSaldo(Integer intIdEmpresa, Date dtFechaInicio) throws BusinessException{
	/**
	 * Método encargado de realizar la anulación de saldos diarios
	 * 
     * @author Bizarq
     * @param objUsuario 	: Objeto usuario de sesión
     * @param dtFechaInicio : Fecha de inicio hasta donde llegará la anulación
     * 
     * @throws BusinessException
     * */
	public void anularSaldo(Usuario objUsuario, Saldo objSaldo) throws BusinessException{
	//Fin: REQ14-005 - bizarq - 19/10/2014
		try{			
			Saldo saldoFiltro = new Saldo();
			//Inicio: REQ14-005 - bizarq - 19/10/2014
			//saldoFiltro.getId().setIntPersEmpresa(intIdEmpresa);
			saldoFiltro.getId().setIntPersEmpresa(objUsuario.getEmpresa().getIntIdEmpresa());
			//saldoFiltro.setDtFechaDesde(dtFechaInicio);
			saldoFiltro.setDtFechaDesde(objSaldo.getDtFechaDesde());
			//Fin: REQ14-005 - bizarq - 19/10/2014
			
			List<Saldo> listaSaldo = boSaldo.getListaPorBuscar(saldoFiltro);
			
			for(Saldo saldo : listaSaldo){
				//Inicio: REQ14-005 - bizarq - 19/10/2014
				saldo.setIntPersEmpresaAnula(objUsuario.getEmpresa().getIntIdEmpresa());
				saldo.setIntPersPersonaAnula(objUsuario.getIntPersPersonaPk());
				saldo.setTsFechaAnula(new Timestamp(new Date().getTime()));
				saldo.setStrMotivoAnula(objSaldo.getStrMotivoAnula());
				//Fin: REQ14-005 - bizarq - 19/10/2014
				saldo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boSaldo.modificar(saldo);
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	/*public Date obtenerUltimaFechaSaldo(Integer intIdEmpresa) throws BusinessException{
		Date dtUltimaFecha = null;
		try{
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			List<Sucursal>listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(intIdEmpresa);
			List<Subsucursal> listaSubsucursal = new ArrayList<Subsucursal>();
			for(Sucursal sucursal : listaSucursal){
				listaSubsucursal.addAll(empresaFacade.getListaSubSucursalPorIdSucursal(sucursal.getId().getIntIdSucursal()));
			}
			
			List<HashMap> listaFechas = boCierreDiarioArqueo.getListaFechas(intIdEmpresa);
			
			List<Integer> listaIdSubsucursalFechas = new ArrayList<Integer>();
			for(HashMap<String,String> mapa : listaFechas){
				log.info(mapa);
				//listaIdSubsucursalFechas.add(Integer.parseInt((String)mapa.get("idSubsucursal")));
			}
			
			boolean estanPresentesSubsucursal = Boolean.FALSE;
			for(Subsucursal subsucursal : listaSubsucursal){
				estanPresentesSubsucursal = Boolean.FALSE;
				for(Integer intIdSubsucursalFechas : listaIdSubsucursalFechas){
					if(subsucursal.getId().getIntIdSubSucursal().equals(intIdSubsucursalFechas)){
						estanPresentesSubsucursal = Boolean.TRUE;
					}
				}
				if(!estanPresentesSubsucursal)	break;
				
			}
			
			if(estanPresentesSubsucursal){
				dtUltimaFecha = new Date();
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dtUltimaFecha;
	}*/
	public Date obtenerUltimaFechaSaldo(Integer intIdEmpresa) throws BusinessException{
		Date dtUltimaFecha = null;
		try {
			List<HashMap> listaFechas = boCierreDiarioArqueo.getListaFechas(intIdEmpresa);
			for(HashMap<String,Object> mapa : listaFechas){
				dtUltimaFecha = (Timestamp)mapa.get("fechaMaxima");
				break;
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return dtUltimaFecha;
	}
	//Fin: REQ14-005 - bizarq - 19/10/2014
}