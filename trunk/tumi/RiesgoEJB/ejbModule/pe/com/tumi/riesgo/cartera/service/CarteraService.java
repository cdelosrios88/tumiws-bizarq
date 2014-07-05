package pe.com.tumi.riesgo.cartera.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.riesgo.cartera.bo.CarteraBO;
import pe.com.tumi.riesgo.cartera.bo.CarteraCreditoBO;
import pe.com.tumi.riesgo.cartera.bo.CarteraCreditoDetalleBO;
import pe.com.tumi.riesgo.cartera.bo.EspecificacionBO;
import pe.com.tumi.riesgo.cartera.bo.ProciclicoBO;
import pe.com.tumi.riesgo.cartera.bo.ProductoBO;
import pe.com.tumi.riesgo.cartera.bo.ProvisionBO;
import pe.com.tumi.riesgo.cartera.bo.TiempoBO;
import pe.com.tumi.riesgo.cartera.domain.Cartera;
import pe.com.tumi.riesgo.cartera.domain.CarteraCredito;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;
import pe.com.tumi.riesgo.cartera.domain.Especificacion;
import pe.com.tumi.riesgo.cartera.domain.EspecificacionId;
import pe.com.tumi.riesgo.cartera.domain.Prociclico;
import pe.com.tumi.riesgo.cartera.domain.Producto;
import pe.com.tumi.riesgo.cartera.domain.Provision;
import pe.com.tumi.riesgo.cartera.domain.Tiempo;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManual;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManualGen;

public class CarteraService {

	protected static Logger log = Logger.getLogger(CarteraService.class);

	CarteraBO boCartera = (CarteraBO) TumiFactory.get(CarteraBO.class);
	ProductoBO boProducto = (ProductoBO) TumiFactory.get(ProductoBO.class);
	EspecificacionBO boEspecificacion = (EspecificacionBO) TumiFactory
			.get(EspecificacionBO.class);
	ProvisionBO boProvision = (ProvisionBO) TumiFactory.get(ProvisionBO.class);
	ProciclicoBO boProciclico = (ProciclicoBO) TumiFactory
			.get(ProciclicoBO.class);
	TiempoBO boTiempo = (TiempoBO) TumiFactory.get(TiempoBO.class);
	CarteraCreditoBO boCarteroCredito = (CarteraCreditoBO) TumiFactory
			.get(CarteraCreditoBO.class);
	CarteraCreditoDetalleBO boCarteraCreditoDetalle = (CarteraCreditoDetalleBO) TumiFactory
			.get(CarteraCreditoDetalleBO.class);

	public Cartera grabarCartera(Cartera cartera) throws BusinessException {
		try {

			log.info(cartera);

			List<Producto> listaProducto = cartera.getListaProducto();
			List<Especificacion> listaEspecificacionProvision = cartera.getListaEspecificacionProvision();
			List<Especificacion> listaEspecificacionProciclico = cartera.getListaEspecificacionProciclico();
			List<Tiempo> listaTiempo = cartera.getListaTiempo();
			Prociclico prociclico = null;

			cartera = boCartera.grabar(cartera);

			for (Producto producto : listaProducto) {
				producto.getId().setIntItemCartera(cartera.getIntItemCartera());
				log.info(producto);
				boProducto.grabar(producto);
			}
			/*
			Iterator<Especificacion> itProvision = listaEspecificacionProvision.iterator();
			Iterator<Especificacion> itProciclico = listaEspecificacionProciclico.iterator();
			
			while(itProvision.hasNext()) {
				Especificacion especificacion = new Especificacion();
				especificacion.setId(new EspecificacionId());
				especificacion.getId().setIntItemCartera(cartera.getIntItemCartera());
				boEspecificacion.grabar(especificacion);
				Especificacion espProvision = itProvision.next();
				while(itProciclico.hasNext()) {
					Especificacion espProciclico = itProciclico.next();
					if(espProvision.getListaProvision() != null) {
						for (Provision provision : espProvision.getListaProvision()) {
							provision.getId().setIntItemEspecificacion(
									espProvision.getId().getIntItemEspecificacion());
							provision.getId().setIntItemCartera(
									cartera.getIntItemCartera());
							log.info(provision);
							boProvision.grabar(provision);
						}
					}
					if(espProvision.getIntItemPlantillaDetalle() >= 0 && espProvision.getIntItemPlantillaDetalle() < 3 &&
							espProciclico.getIntItemPlantillaDetalle() == 64) {
						prociclico = espProciclico.getProciclico();
						prociclico.getId().setIntItemEspecificacion(
								espProciclico.getId().getIntItemEspecificacion());
						prociclico.getId().setIntItemCartera(
								cartera.getIntItemCartera());
						log.info(prociclico);
						boProciclico.grabar(prociclico);
					}
				}
			} */
			
			for (Especificacion especificacion : listaEspecificacionProvision) {
				especificacion.getId().setIntItemCartera(
						cartera.getIntItemCartera());
				log.info(especificacion);
				boEspecificacion.grabar(especificacion);
				for (Provision provision : especificacion.getListaProvision()) {
					provision.getId().setIntItemEspecificacion(
							especificacion.getId().getIntItemEspecificacion());
					provision.getId().setIntItemCartera(
							cartera.getIntItemCartera());
					log.info(provision);
					boProvision.grabar(provision);
				}

				for (Especificacion espProciclico : listaEspecificacionProciclico) {
					log.info(especificacion);
					prociclico = espProciclico.getProciclico();
					prociclico.getId().setIntItemEspecificacion(
							especificacion.getId().getIntItemEspecificacion());
					prociclico.getId().setIntItemCartera(
							cartera.getIntItemCartera());
					log.info(prociclico);
					boProciclico.grabar(prociclico);
				}
			}
			
			/*
			for (Especificacion especificacion : listaEspecificacionProciclico) {
				especificacion.getId().setIntItemCartera(
						cartera.getIntItemCartera());
				log.info(especificacion);
				boEspecificacion.grabar(especificacion);
				prociclico = especificacion.getProciclico();
				prociclico.getId().setIntItemEspecificacion(
						especificacion.getId().getIntItemEspecificacion());
				prociclico.getId().setIntItemCartera(
						cartera.getIntItemCartera());
				log.info(prociclico);
				boProciclico.grabar(prociclico);
			} */

			for (Tiempo tiempo : listaTiempo) {
				tiempo.setIntItemCartera(cartera.getIntItemCartera());
				log.info(tiempo);
				boTiempo.grabar(tiempo);
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return cartera;
	}

	public Cartera modificarCartera(Cartera cartera) throws BusinessException {
		try {

			log.info(cartera);

			List<Producto> listaProducto = cartera.getListaProducto();
			List<Especificacion> listaEspecificacionProvision = cartera
					.getListaEspecificacionProvision();
			List<Especificacion> listaEspecificacionProciclico = cartera
					.getListaEspecificacionProciclico();
			List<Tiempo> listaTiempo = cartera.getListaTiempo();

			cartera = boCartera.modificar(cartera);
			boProducto.getPorIntItemCartera(cartera.getIntItemCartera());

			for (Producto producto : listaProducto) {
				log.info(producto);
				boProducto.modificar(producto);
			}

			// Manejamos listaEspecificacionProvision
			// 1ero grabamos todos las Especificacion nuevas que hemos agregado
			// List<Especificacion> listaEspecificacionAux = new
			// ArrayList<Especificacion>();
			List<Provision> listaProvisionBD = null;
			List<Especificacion> listaEspecificacionBD = getListaEspecificacionPorIntItemCarteraYTipoProvision(
					cartera.getIntItemCartera(),
					Constante.PARAM_T_TIPOPROVISION_CREDITOS);
			boolean especificacionEsNueva = Boolean.FALSE;
			boolean provisionEsNueva = Boolean.FALSE;
			for (Especificacion especificacion : listaEspecificacionProvision) {
				Especificacion especificacionBDPersiste = null;
				especificacionEsNueva = Boolean.TRUE;

				for (Especificacion especificacionBD : listaEspecificacionBD) {
					if (especificacionBD.getIntItemPlantillaDetalle().equals(
							especificacion.getIntItemPlantillaDetalle())) {
						especificacionBDPersiste = especificacionBD;
						especificacionEsNueva = Boolean.FALSE;
						listaEspecificacionBD.remove(especificacionBD);
						break;
					}
				}
				if (especificacionEsNueva) {
					especificacion.getId().setIntItemCartera(
							cartera.getIntItemCartera());
					boEspecificacion.grabar(especificacion);
					for (Provision provision : especificacion
							.getListaProvision()) {
						provision.getId().setIntItemEspecificacion(
								especificacion.getId()
										.getIntItemEspecificacion());
						provision.getId().setIntItemCartera(
								cartera.getIntItemCartera());
						boProvision.grabar(provision);
					}
				} else {
					listaProvisionBD = getListaProvisionPorEspecificacion(especificacionBDPersiste);
					Provision provisionBDPersiste = null;
					for (Provision provision : especificacion
							.getListaProvision()) {
						provisionEsNueva = Boolean.TRUE;
						for (Provision provisionBD : listaProvisionBD) {
							if (provision
									.getIntParaTipoCategoriaRiesgoCod()
									.equals(provisionBD
											.getIntParaTipoCategoriaRiesgoCod())) {
								provisionBDPersiste = provisionBD;
								provisionEsNueva = Boolean.FALSE;
								break;
							}
						}
						if (provisionEsNueva) {
							provision.getId().setIntItemEspecificacion(
									especificacionBDPersiste.getId()
											.getIntItemEspecificacion());
							provision.getId().setIntItemCartera(
									cartera.getIntItemCartera());
							boProvision.grabar(provision);
						} else {
							// Se agrega para corregir la actualización de
							// Provisiones de Crédito.
							// GTorresBrousset 2014.02.11
							provision.getId().setIntItemProvision(
									provisionBDPersiste.getId()
											.getIntItemProvision());
							provision.getId().setIntItemEspecificacion(
									provisionBDPersiste.getId()
											.getIntItemEspecificacion());
							provision.getId().setIntItemCartera(
									cartera.getIntItemCartera());
							provision
									.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							boProvision.modificar(provision);
							listaProvisionBD.remove(provisionBDPersiste);
						}
					}
					for (Provision provisionBDNoSeleccionada : listaProvisionBD) {
						provisionBDNoSeleccionada
								.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
						boProvision.modificar(provisionBDNoSeleccionada);
					}
				}
			}
			// Se agrega para corregir la eliminación de Provisiones de Crédito.
			// GTorresBrousset 2014.02.12
			for (Especificacion especificacionBDNoPersiste : listaEspecificacionBD) {
				listaProvisionBD = getListaProvisionPorEspecificacion(especificacionBDNoPersiste);
				for (Provision provisionBDNoPersiste : listaProvisionBD) {
					provisionBDNoPersiste
							.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
					boProvision.modificar(provisionBDNoPersiste);
				}
			}

			// Manejamos listaEspecificacionProciclica
			// 1ero grabamos todos las Especificacion nuevas que hemos agregado
			List<Especificacion> listaEspecificacionProBD = getListaEspecificacionPorIntItemCarteraYTipoProvision(
					cartera.getIntItemCartera(),
					Constante.PARAM_T_TIPOPROVISION_PROCICLICA);
			Prociclico prociclico = null;
			Prociclico prociclicoBD = null;
			especificacionEsNueva = Boolean.FALSE;
			for (Especificacion especificacion : listaEspecificacionProciclico) {
				Especificacion especificacionBDPersiste = null;
				especificacionEsNueva = Boolean.TRUE;
				for (Especificacion especificacionBD : listaEspecificacionProBD) {
					if (especificacionBD.getIntItemPlantillaDetalle().equals(
							especificacion.getIntItemPlantillaDetalle())) {
						especificacionBDPersiste = especificacionBD;
						// Se agrega para corregir la actualización de
						// Procíclicos.
						// GTorresBrousset 2014.02.07
						especificacionBDPersiste
								.setProciclico(getProciclicoPorEspecificacion(especificacionBDPersiste));
						especificacionEsNueva = Boolean.FALSE;
						break;
					}
				}
				if (especificacionEsNueva) {
					especificacion.getId().setIntItemCartera(
							cartera.getIntItemCartera());
					boEspecificacion.grabar(especificacion);
					prociclico = especificacion.getProciclico();
					prociclico.getId().setIntItemCartera(
							cartera.getIntItemCartera());
					prociclico.getId().setIntItemEspecificacion(
							especificacion.getId().getIntItemEspecificacion());
					boProciclico.grabar(prociclico);
				} else {
					prociclico = especificacion.getProciclico();
					// Se agrega para corregir la actualización de Procíclicos.
					// GTorresBrousset 2014.02.07
					prociclico.getId().setIntItemProciclico(
							especificacionBDPersiste.getProciclico().getId()
									.getIntItemProciclico());
					prociclico.getId().setIntItemEspecificacion(
							especificacionBDPersiste.getProciclico().getId()
									.getIntItemEspecificacion());
					prociclico.getId().setIntItemCartera(
							cartera.getIntItemCartera());
					prociclico
							.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					boProciclico.modificar(prociclico);
					listaEspecificacionProBD.remove(especificacionBDPersiste);
				}
			}
			for (Especificacion espeficicacionNoSeleccionada : listaEspecificacionProBD) {
				prociclicoBD = getProciclicoPorEspecificacion(espeficicacionNoSeleccionada);
				prociclicoBD
						.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
				boProciclico.modificar(prociclicoBD);
			}

			// Manejamos listatTiempo
			List<Tiempo> listaTiempoBD = getListaTiempoPorCartera(cartera);
			boolean tiempoEsNuevo = Boolean.FALSE;
			Tiempo tiempoBDPersiste = null;
			for (Tiempo tiempo : listaTiempo) {
				tiempoEsNuevo = Boolean.TRUE;
				for (Tiempo tiempoBD : listaTiempoBD) {
					if (tiempoBD.getId().getIntParaTipoSbsCod()
							.equals(tiempo.getId().getIntParaTipoSbsCod())
							&& tiempoBD
									.getIntItemTipoCategoriaRiesgoCod()
									.equals(tiempo
											.getIntItemTipoCategoriaRiesgoCod())) {
						tiempoBDPersiste = tiempoBD;
						tiempoEsNuevo = Boolean.FALSE;
						break;
					}
				}
				if (tiempoEsNuevo) {
					tiempo.setIntItemCartera(cartera.getIntItemCartera());
					boTiempo.grabar(tiempo);
				} else {
					tiempo.setIntItemCartera(cartera.getIntItemCartera());
					tiempo.getId().setIntItemTiempo(
							tiempoBDPersiste.getId().getIntItemTiempo());
					tiempo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					boTiempo.modificar(tiempo);
					listaTiempoBD.remove(tiempoBDPersiste);
				}
			}
			for (Tiempo tiempoNoSelec : listaTiempoBD) {
				tiempoNoSelec
						.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
				boTiempo.modificar(tiempoNoSelec);
			}

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return cartera;
	}

	private Cartera cargarProductoConcatenado(Cartera cartera,
			List<Tabla> listaProductoTabla) {
		List<String> lista = new ArrayList<String>();
		for (Producto producto : cartera.getListaProducto()) {
			if (producto.getIntParaEstadoCod().equals(
					Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)) {
				// log.info("producto activo:"+producto);
				for (Tabla tabla : listaProductoTabla) {
					// log.info("tabla idM:"+tabla.getIntIdMaestro()+" idD:"+tabla.getIntIdDetalle());
					if (producto.getId().getIntParaTipoOperacionCod()
							.equals(Constante.PARAM_T_IDENTIFICACAPTACION)
							&& tabla.getIntIdMaestro()
									.equals(Integer
											.parseInt(Constante.PARAM_T_TIPOCUENTA))
							&& tabla.getIntIdDetalle().equals(
									producto.getId()
											.getIntParaTipoConceptoCod())) {
						lista.add(tabla.getStrDescripcion());
						break;
					}
					if (producto.getId().getIntParaTipoOperacionCod()
							.equals(Constante.PARAM_T_IDENTIFICACREDITO)
							&& tabla.getIntIdMaestro()
									.equals(Integer
											.parseInt(Constante.PARAM_T_TIPO_CREDITO))
							&& tabla.getIntIdDetalle().equals(
									producto.getId()
											.getIntParaTipoConceptoCod())) {
						lista.add(tabla.getStrDescripcion());
						break;
					}
				}
			}
		}
		cartera.setListaStrProductos(lista);
		return cartera;
	}

	private Date obtenerFechaActual() {
		Calendar cal = Calendar.getInstance();
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		return cal.getTime();
	}

	private List<Tabla> cargarListaProducto(List<Tabla> listaProducto,
			TablaFacadeRemote tablaFacade) throws Exception {
		try {
			listaProducto = tablaFacade.getListaTablaPorIdMaestro(Integer
					.parseInt(Constante.PARAM_T_TIPOCUENTA));
			List<Tabla> listaProductoAux = new ArrayList<Tabla>();
			for (Object o : listaProducto) {
				Tabla tabla = (Tabla) o;
				if (tabla.getStrIdAgrupamientoA() != null
						&& tabla.getStrIdAgrupamientoA().contains("E")) {
					listaProductoAux.add(tabla);
				}
			}
			listaProducto = listaProductoAux;
			listaProducto.addAll(tablaFacade.getListaTablaPorIdMaestro(Integer
					.parseInt(Constante.PARAM_T_TIPO_CREDITO)));
			log.info("listaProducto:" + listaProducto.size());
			return listaProducto;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	public List<Cartera> buscarCartera(Cartera carteraBus,
			Producto productoFiltro, boolean buscarVigente, boolean buscarCaduco)
			throws BusinessException, Exception {

		List<Cartera> lista = null;
		List<Cartera> listaAux = null;
		List<Producto> listaProducto = null;
		List<Tabla> listaProductoTabla = null;
		TablaFacadeRemote tablaFacade;
		try {
			listaProductoTabla = new ArrayList<Tabla>();
			tablaFacade = (TablaFacadeRemote) EJBFactory
					.getRemote(TablaFacadeRemote.class);
			listaProductoTabla = cargarListaProducto(listaProductoTabla,
					tablaFacade);
			log.info("listaProducto:" + listaProductoTabla.size());

			lista = boCartera.buscar(carteraBus);

			if (carteraBus.getIntParaEstadoCod() != null) {
				listaAux = new ArrayList<Cartera>();
				for (Cartera cartera : lista) {
					if (cartera.getIntParaEstadoCod().equals(
							carteraBus.getIntParaEstadoCod())) {
						listaAux.add(cartera);
					}
				}
				lista = listaAux;
			}

			if (carteraBus.getStrNombre() != null
					&& !carteraBus.getStrNombre().isEmpty()) {
				listaAux = new ArrayList<Cartera>();
				for (Cartera cartera : lista) {
					if (cartera.getStrNombre().toUpperCase()
							.contains(carteraBus.getStrNombre().toUpperCase())) {
						listaAux.add(cartera);
					}
				}
				lista = listaAux;
			}

			// fecha actual con 00:00:00
			Date fechaActual = obtenerFechaActual();
			if (buscarVigente) {
				listaAux = new ArrayList<Cartera>();
				for (Cartera cartera : lista) {
					if (cartera.getDtFechaFin() != null) {
						if (cartera.getDtFechaFin().compareTo(fechaActual) > 0
								|| cartera.getDtFechaFin().compareTo(
										fechaActual) == 0) {
							listaAux.add(cartera);
						}
					} else {
						listaAux.add(cartera);
					}
				}
				lista = listaAux;
			}

			if (buscarCaduco) {
				listaAux = new ArrayList<Cartera>();
				for (Cartera cartera : lista) {
					if (cartera.getDtFechaFin() != null) {
						if (cartera.getDtFechaFin().compareTo(fechaActual) < 0) {
							listaAux.add(cartera);
						}
					}
				}
				lista = listaAux;
			}

			// Filtra tipo de productos y agrega la cadena de productos
			// concantenados
			if (!productoFiltro.getId().getIntParaTipoOperacionCod()
					.equals(Constante.PARAM_T_TIPOCUENTA_TODOS)) {
				listaAux = new ArrayList<Cartera>();
				boolean carteraPoseeProducto;
				for (Cartera cartera : lista) {
					carteraPoseeProducto = Boolean.FALSE;
					listaProducto = boProducto.getPorIntItemCartera(cartera
							.getIntItemCartera());
					for (Producto producto : listaProducto) {
						if (producto.getIntParaEstadoCod().equals(
								Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
								&& producto
										.getId()
										.getIntParaTipoOperacionCod()
										.equals(productoFiltro.getId()
												.getIntParaTipoOperacionCod())
								&& producto
										.getId()
										.getIntParaTipoConceptoCod()
										.equals(productoFiltro.getId()
												.getIntParaTipoConceptoCod())) {
							carteraPoseeProducto = Boolean.TRUE;
							break;
						}
					}
					if (carteraPoseeProducto) {
						cartera.setListaProducto(listaProducto);
						cartera = cargarProductoConcatenado(cartera,
								listaProductoTabla);
						listaAux.add(cartera);
					}
				}
				lista = listaAux;
			} else {
				listaAux = new ArrayList<Cartera>();
				for (Cartera cartera : lista) {
					listaProducto = boProducto.getPorIntItemCartera(cartera
							.getIntItemCartera());
					cartera.setListaProducto(listaProducto);
					cartera = cargarProductoConcatenado(cartera,
							listaProductoTabla);
					listaAux.add(cartera);
				}
				lista = listaAux;
			}
			/*
			 * log.info("--BUS:"); for(Cartera car : lista){ log.info(car);
			 * for(Producto producto : car.getListaProducto()){
			 * log.info(producto); } }
			 */
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Especificacion> getListaEspecificacionPorIntItemCartera(
			Integer intItemCartera) throws BusinessException, Exception {
		List<Especificacion> lista = null;
		try {
			lista = boEspecificacion.getPorIntItemCartera(intItemCartera);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Especificacion> getListaEspecificacionPorIntItemCarteraYTipoProvision(
			Integer intItemCartera, Integer intTipoProvision)
			throws BusinessException, Exception {
		List<Especificacion> lista = null;
		try {
			List<Especificacion> listaAux = new ArrayList<Especificacion>();
			lista = getListaEspecificacionPorIntItemCartera(intItemCartera);
			for (Especificacion e : lista) {
				if (e.getIntParaTipoProvisionCod().equals(intTipoProvision)) {
					listaAux.add(e);
				}
			}
			lista = listaAux;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Provision> getListaProvisionPorEspecificacion(
			Especificacion especificacion) throws BusinessException, Exception {
		List<Provision> lista = null;
		try {
			lista = boProvision.getPorEspecificacion(especificacion);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public Prociclico getProciclicoPorEspecificacion(
			Especificacion especificacion) throws BusinessException, Exception {
		Prociclico domain = null;
		try {
			domain = boProciclico.getPorEspecificacion(especificacion);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}

	public List<Tiempo> getListaTiempoPorCartera(Cartera cartera)
			throws BusinessException, Exception {
		List<Tiempo> lista = null;
		try {
			lista = boTiempo.getPorCartera(cartera);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public CarteraCredito getCarteraCreditoPorMaxPeriodo(Integer intEmpresa)
			throws BusinessException, Exception {
		CarteraCredito carteraCredito = null;
		try {
			carteraCredito = boCarteroCredito.getPorMaxPeriodo(intEmpresa);
			List<CarteraCreditoDetalle> listaCarteraCreDetalle = boCarteraCreditoDetalle
					.getListaPorCarteraCredito(carteraCredito.getId());
			carteraCredito.setListaCarteraCreDetalle(listaCarteraCreDetalle);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCredito;
	}

	/**
	 * Recupera el ultimo cartera credito detalle en base al id del expediente
	 * ty el periodo
	 * 
	 * @param pId
	 * @param intPeriodo
	 * @return
	 * @throws BusinessException
	 */
	public CarteraCreditoDetalle getMaxPorExpediente(ExpedienteId pId,
			Integer intPeriodo) throws BusinessException {
		CarteraCreditoDetalle carteraCreditoDet = null;
		try {

			carteraCreditoDet = boCarteraCreditoDetalle.getMaxPorExpediente(
					pId, intPeriodo);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCreditoDet;
	}

	/**
	 * 
	 * @param mesFiltro
	 * @param anioFiltro
	 * @param tipoCarteraFiltro
	 * @param estadoFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<CarteraCreditoManual> buscaCarteraManual(
			Integer intPeriodoInicial, Integer intPeriodoFinal,
			Integer intEstadoMen, Integer intEstadoMay)
			throws BusinessException {
		List<CarteraCreditoManual> listaCarCreManual = null;
		try {
			listaCarCreManual = boCarteraCreditoDetalle.buscaCarteraManual(
					intPeriodoInicial, intPeriodoFinal, intEstadoMen,
					intEstadoMay);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaCarCreManual;
	}

	public CarteraCreditoManual obtenerCarteraManual() throws BusinessException {
		CarteraCreditoManual carteraCreditoManual = null;
		try {
			carteraCreditoManual = boCarteraCreditoDetalle
					.obtenerCarteraManual();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCreditoManual;
	}

	public CarteraCreditoManualGen generarCarteraManual(Date dtFechaCorte,
			Integer intPersEmpresa, Integer intPersPersona)
			throws BusinessException {
		CarteraCreditoManualGen carteraCreditoManualGen = null;
		try {
			carteraCreditoManualGen = boCarteraCreditoDetalle
					.generarCarteraManual(dtFechaCorte, intPersEmpresa,
							intPersPersona);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCreditoManualGen;
	}

	public CarteraCreditoManual cerrarCarteraManual(Integer intPeriodo)
			throws BusinessException {
		CarteraCreditoManual carteraCreditoManual = null;
		try {
			carteraCreditoManual = boCarteraCreditoDetalle
					.cerrarCarteraManual(intPeriodo);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return carteraCreditoManual;
	}

}
