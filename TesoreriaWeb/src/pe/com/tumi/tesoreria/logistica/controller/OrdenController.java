package pe.com.tumi.tesoreria.logistica.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Detraccion;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaFin;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedor;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoRequisicion;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatOrdenComDoc;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorDetalle;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorId;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionId;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;

public class OrdenController {

	protected static Logger log = Logger.getLogger(OrdenController.class);

	PersonaFacadeRemote personaFacade;
	EmpresaFacadeRemote empresaFacade;
	LogisticaFacadeLocal logisticaFacade;
	GeneralFacadeRemote generalFacade;
	TablaFacadeRemote tablaFacade;
	PlanCuentaFacadeRemote planCuentaFacade;

	private OrdenCompra ordenCompraNuevo;
	private OrdenCompra ordenCompraFiltro;
	private OrdenCompra registroSeleccionado;
	private OrdenCompraDetalle ordenCompraDetalle;
	private OrdenCompraDocumento ordenCompraDocumento;
	private PlanCuenta planCuentaFiltro;

	private List<OrdenCompra> listaOrdenCompra;
	private List<Persona> listaPersona;
	private List<DocumentoRequisicion> listaDocumentoRequisicion;
	private List<Tabla> listaTablaAprobacion;
	private List<Sucursal> listaSucursal;
	private List<Tabla> listaAnios;
	private List<Tabla> listaTablaDocumentoGeneral;
	private List<PlanCuenta> listaPlanCuenta;
	private List<Tabla> listaTablaBancos;
	private List<Persona> listaPersonaFiltro;
	private List<Detraccion> listaDetraccion;
	private List<Proveedor> listaProveedor;
	private List<Tabla> listaTablaProveedor;

	private Usuario usuario;
	private String mensajeOperacion;
	private Integer intTipoPersona;
	private String strFiltroTextoPersona;
	private Integer intTipoAprobacion;
	private String strMensajeDetalle;
	private String strMensajeDocumento;
	private Integer intTipoCuentaBancaria;
	private String strTextoPersonaFiltro;
	private Integer intTipoPersonaFiltro;
	private Integer intTipoBusquedaPersona;
	// Agregado por cdelosrios, 29/09/2013
	private Integer intItemRequisicion;
	// Fin agregado por cdelosrios, 29/09/2013

	private Integer EMPRESA_USUARIO;
	private Integer PERSONA_USUARIO;

	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean poseePermiso;
	private boolean mostrarMensajeDetalle;
	private boolean mostrarMensajeDocumento;
	private boolean habilitarCuentaFormaPago;
	private boolean habilitarCuentaImpuesto;
	// Agregado por cdelosrios, 26/09/2013
	private Integer ADMINISTRADOR_ZONAL_CENTRAL = 3;
	private Integer ADMINISTRADOR_ZONAL_FILIAL = 5;
	private Integer ADMINISTRADOR_ZONAL_LIMA = 6;
	// Fin agregado por cdelosrios, 26/09/2013

	// Agregado por cdelosrios, 29/09/2013
	private Boolean blnHabilitarDetOrden;
	private Boolean blnHabilitarAdmin;

	// Fin agregado por cdelosrios, 29/09/2013
	
	//Autor: jchavez / Tarea: Creacion / Fecha: 01.10.2014
	private List<Tabla> lstTablaEstadoContribuyente;
	private List<Tabla> lstTablaCondicionContribuyente;
	private String		strMsgErrorBusquedaProveedor;
	private	List<Tabla> listaTablaTipoCtaBancaria;
	private	List<Tabla> listaTablaTipoMoneda;
	//Fin jchavez - 01.10.2014
	
	//Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
	private Boolean blnExisteCancelado;
	private Boolean blnExisteDocSunatRel;
	//Fin jchavez - 26.10.2014
	
	public OrdenController() {
		cargarUsuario();
		// poseePermiso = Boolean.TRUE;
		poseePermiso = PermisoUtil
				.poseePermiso(Constante.PARAM_TRANSACCION_ORDENCOMPRA);
		if (usuario != null && poseePermiso)
			cargarValoresIniciales();
		else
			log.error("--Usuario obtenido es NULL o no posee permiso.");
	}

	public String getLimpiarOrden(){
		cargarUsuario();
		// poseePermiso = Boolean.TRUE;
		poseePermiso = PermisoUtil
				.poseePermiso(Constante.PARAM_TRANSACCION_ORDENCOMPRA);
		if (usuario != null && poseePermiso){
			cargarValoresIniciales();
			//Agregado por cdelosrios, 25/11/2013
			deshabilitarPanelInferior();
			strMsgErrorBusquedaProveedor = "";
			//Fin agregado por cdelosrios, 25/11/2013
		}	
		else
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		
		return "";
	}
	
	private void cargarUsuario() {
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
	}

	public void cargarValoresIniciales() {
		try {
			personaFacade = (PersonaFacadeRemote) EJBFactory
					.getRemote(PersonaFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote) EJBFactory
					.getRemote(EmpresaFacadeRemote.class);
			logisticaFacade = (LogisticaFacadeLocal) EJBFactory
					.getLocal(LogisticaFacadeLocal.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory
					.getRemote(GeneralFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory
					.getRemote(TablaFacadeRemote.class);
			planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory
					.getRemote(PlanCuentaFacadeRemote.class);

			ordenCompraFiltro = new OrdenCompra();
			listaOrdenCompra = new ArrayList<OrdenCompra>();
			listaTablaBancos = tablaFacade.getListaTablaPorIdMaestro(Integer
					.parseInt(Constante.PARAM_T_BANCOS));
			listaDetraccion = generalFacade.getListaDetraccionTodos();
			listaTablaProveedor = tablaFacade.getListaTablaPorIdMaestro(Integer
					.parseInt(Constante.PARAM_T_TIPOPROVEEDOR));
			listaTablaDocumentoGeneral = tablaFacade
					.getListaTablaPorAgrupamientoA(Integer
							.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "O");
			//Autor: jchavez / Tarea: Creacion / Fecha: 01.10.2014
			lstTablaEstadoContribuyente = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOESTADOCONTRIB));
			lstTablaCondicionContribuyente = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCONDCONTRIBUYENTE));
			strMsgErrorBusquedaProveedor = "";
			listaTablaTipoCtaBancaria = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCUENTABANCARIA));
			listaTablaTipoMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			//Fin jchavez - 01.10.2014
			cargarListaSucursal();
			cargarListaAnios();
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
			// Agregado por cdelosrios, 29/09/2013
			blnHabilitarDetOrden = Boolean.FALSE;
			// Fin agregado por cdelosrios, 29/09/2013
			//Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
			blnExisteCancelado = false;
			blnExisteDocSunatRel = false;
			//Fin jchavez - 26.10.2014
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void deshabilitarPanelInferior() {
		registrarNuevo = Boolean.FALSE;
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
		//Agregado por cdelosrios, 29/09/2013
		blnHabilitarAdmin = Boolean.FALSE;
		blnHabilitarDetOrden = Boolean.FALSE;
		//Fin agregado por cdelosrios, 29/09/2013
		//Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
		blnExisteCancelado = false;
		blnExisteDocSunatRel = false;
		//Fin jchavez - 26.10.2014
	}

	public void grabar() {
		log.info("--grabar");
		try {

			if (ordenCompraNuevo.getTsFechaAtencionLog() == null) {
				mostrarMensaje(Boolean.FALSE,
						"Debe ingregar una fecha probable de atención.");
				return;
			}
			if (ordenCompraNuevo.getTsFechaAtencionReal() != null
					&& ordenCompraNuevo.getTsFechaAtencionLog().compareTo(
							ordenCompraNuevo.getTsFechaAtencionReal()) > 0) {
				mostrarMensaje(Boolean.FALSE,
						"La fecha de atención real debe ser mayor a la otra atención.");
				return;
			}
			if (ordenCompraNuevo.getDocumentoRequisicion() == null) {
				mostrarMensaje(Boolean.FALSE, "Debe agregar una Requisición.");
				return;
			}
			if (ordenCompraNuevo.getTsPlazoEntrega() == null) {
				mostrarMensaje(Boolean.FALSE,
						"Debe ingresar un plazo de entrega.");
				return;
			}
			if (ordenCompraNuevo.getStrLugarEntrega() == null
					|| ordenCompraNuevo.getStrLugarEntrega().isEmpty()) {
				mostrarMensaje(Boolean.FALSE,
						"Debe ingregar un lugar de entrega.");
				return;
			}
			if (ordenCompraNuevo.getBdGarantiaProductoServicio() != null
					&& ordenCompraNuevo.getBdGarantiaProductoServicio()
							.signum() <= 0) {
				mostrarMensaje(Boolean.FALSE,
						"Debe ingregar un monto de garantía válido.");
				return;
			}
			if (ordenCompraNuevo.getIntParaFormaPago().equals(
					Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA)
					&& ordenCompraNuevo.getCuentaBancariaPago() == null) {
				mostrarMensaje(Boolean.FALSE,
						"Debe ingregar la cuenta bancaria del pago.");
				return;
			}
			/*
			 * if(!ordenCompraNuevo.getIntParaTipoDetraccion().equals(new
			 * Integer(0)) &&
			 * ordenCompraNuevo.getCuentaBancariaImpuesto()==null){
			 * mostrarMensaje(Boolean.FALSE,
			 * "Debe ingregar la cuenta bancaria del impuesto."); return; }
			 */
			if (ordenCompraNuevo.getStrObservacion() == null
					|| ordenCompraNuevo.getStrObservacion().isEmpty()) {
				mostrarMensaje(Boolean.FALSE, "Debe ingregar una observacion.");
				return;
			}
			if (ordenCompraNuevo.getListaOrdenCompraDetalle() == null
					|| ordenCompraNuevo.getListaOrdenCompraDetalle().isEmpty()) {
				mostrarMensaje(Boolean.FALSE,
						"Debe ingregar al menos un detalle de orden.");
				return;
			}
			/*
			 * if(ordenCompraNuevo.getListaOrdenCompraDocumento()==null ||
			 * ordenCompraNuevo.getListaOrdenCompraDocumento().isEmpty()){
			 * mostrarMensaje(Boolean.FALSE,
			 * "Debe ingregar al menos un detalle adicional."); return; }
			 */
			if (ordenCompraNuevo.getPersonaProveedor() == null) {
				mostrarMensaje(Boolean.FALSE, "Debe ingregar un proveedor.");
				return;
			}
			//Agregado por cdelosrios, 15/10/2013
			ordenCompraNuevo.setIntPersEmpresaUsuarioPk(EMPRESA_USUARIO);
			ordenCompraNuevo.setIntPersPersonaUsuarioPk(PERSONA_USUARIO);
			//Fin agregado por cdelosrios, 15/10/2013

			if (ordenCompraNuevo.getId().getIntItemOrdenCompra() == null) {
				ordenCompraNuevo = logisticaFacade
						.grabarOrdenCompra(ordenCompraNuevo);
				buscar();
				mostrarMensaje(Boolean.TRUE,
						"Se registró correctamente la Orden de Compra.");

			} else {
				ordenCompraNuevo = logisticaFacade
						.modificarOrdenCompra(ordenCompraNuevo);
				buscar();
				mostrarMensaje(Boolean.TRUE,
						"Se modificó correctamente la Orden de Compra.");
			}

			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,
					"Ocurrió un error durante el proceso de registro de la Orden de Compra.");
			log.error(e.getMessage(), e);
		}
	}

	public void buscar() {
		try {
			cargarUsuario();
			ordenCompraFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);

			listaPersonaFiltro = new ArrayList<Persona>();
			if (strTextoPersonaFiltro != null
					&& !strTextoPersonaFiltro.isEmpty())
				buscarPersonaFiltro();

			if (ordenCompraFiltro.getIntParaEstado().equals(
					Constante.PARAM_T_ESTADOUNIVERSAL_TODOS))
				ordenCompraFiltro.setIntParaEstado(null);

			listaOrdenCompra = logisticaFacade.buscarOrdenCompra(
					ordenCompraFiltro, listaPersonaFiltro);

			for (OrdenCompra ordenCompra : listaOrdenCompra) {
				log.info(ordenCompra);
				Persona proveedor = personaFacade
						.devolverPersonaCargada(ordenCompra
								.getIntPersPersonaProveedor());
				ordenCompra.setPersonaProveedor(proveedor);
				ordenCompra.setIntParaTipoMoneda(ordenCompra
						.getListaOrdenCompraDetalle().get(0)
						.getIntParaTipoMoneda());
			}

			ocultarMensaje();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void buscarPersonaFiltro() {
		try {
			listaPersonaFiltro = new ArrayList<Persona>();
			Persona persona = null;

			if (intTipoBusquedaPersona
					.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)) {
				Natural natural = new Natural();
				natural.setStrNombres(strTextoPersonaFiltro);
				List<Natural> listaNatural = personaFacade
						.getListaNaturalPorBusqueda(natural);
				for (Natural natu : listaNatural)
					listaPersonaFiltro.add(personaFacade.getPersonaPorPK(natu
							.getIntIdPersona()));

				List<Juridica> listaJuridica = personaFacade
						.getListaJuridicaPorNombreComercial(strTextoPersonaFiltro);
				for (Juridica juridica : listaJuridica)
					listaPersonaFiltro.add(personaFacade
							.getPersonaPorPK(juridica.getIntIdPersona()));

			} else if (intTipoBusquedaPersona
					.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)) {
				persona = personaFacade
						.getPersonaNaturalPorDocIdentidadYIdEmpresa(
								Constante.PARAM_T_INT_TIPODOCUMENTO_DNI,
								strTextoPersonaFiltro, EMPRESA_USUARIO);
				if (persona != null)
					listaPersonaFiltro.add(persona);
			} else if (intTipoBusquedaPersona
					.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)) {
				persona = personaFacade.getPersonaPorRuc(strTextoPersonaFiltro);
				if (persona != null)
					listaPersonaFiltro.add(persona);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void seleccionarTipoServicio() {
		try {
			if (ordenCompraNuevo.getIntParaTipoDetraccion().equals(
					new Integer(0))
					|| ordenCompraNuevo.getPersonaProveedor() == null) {
				ordenCompraNuevo.setIntPersCuentaBancariaDetraccion(null);
				ordenCompraNuevo.setCuentaBancariaImpuesto(null);
				habilitarCuentaImpuesto = Boolean.FALSE;
			} else {
				habilitarCuentaImpuesto = Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void seleccionarTipoFormaPago() {
		try {
			if (!ordenCompraNuevo.getIntParaFormaPago().equals(
					Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA)
					|| ordenCompraNuevo.getPersonaProveedor() == null) {
				ordenCompraNuevo.setIntPersCuentaBancariaTransaccion(null);
				ordenCompraNuevo.setCuentaBancariaPago(null);
				habilitarCuentaFormaPago = Boolean.FALSE;
			} else {
				habilitarCuentaFormaPago = Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void seleccionarRegistro(ActionEvent event) {
		blnExisteCancelado = false;
		blnExisteDocSunatRel = false;
		List<DocumentoSunatOrdenComDoc> lista = null;
		try {
			cargarUsuario();
			registroSeleccionado = (OrdenCompra) event.getComponent()
					.getAttributes().get("item");
			// Si tiene enlazados documentos sunat o los adelantos están cancelados, 
			// se debe mostrar la opción Ver.
			if (registroSeleccionado.getListaOrdenCompraDocumento()!=null && !registroSeleccionado.getListaOrdenCompraDocumento().isEmpty()) {
				for (OrdenCompraDocumento o : registroSeleccionado.getListaOrdenCompraDocumento()) {
					lista = logisticaFacade.getListaPorOrdenCompraDoc(o);
					if (lista != null && !lista.isEmpty()) {
						blnExisteDocSunatRel = true;
					}
					if (o.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)) {
						blnExisteCancelado = true;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void verRegistro() {
		try {
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;

			ordenCompraNuevo = registroSeleccionado;

			RequisicionId requisicionId = new RequisicionId();
			requisicionId.setIntPersEmpresa(ordenCompraNuevo
					.getIntPersEmpresaRequisicion());
			requisicionId.setIntItemRequisicion(ordenCompraNuevo
					.getIntItemRequisicion());

			Requisicion requisicion = logisticaFacade
					.obtenerRequisicionPorId(requisicionId);
			/*
			 * DocumentoRequisicion documentoRequisicion = new
			 * DocumentoRequisicion();
			 * documentoRequisicion.setRequisicion(requisicion);
			 */
			DocumentoRequisicion documentoRequisicion = logisticaFacade
					.obtenerListaDocumentoRequisicionPorRequisicion(
							EMPRESA_USUARIO,
							requisicion,
							Constante.DOCUMENTOREQUISICION_LLAMADO_DESDEREQUISICION)
					.get(0);
			// añadirEtiquetaRequisicion(documentoRequisicion.getRequisicion());
			añadirEtiquetaRequisicion(documentoRequisicion);
			agregarSolcitante(documentoRequisicion.getRequisicion());
			ordenCompraNuevo.setDocumentoRequisicion(documentoRequisicion);

			Persona proveedor = ordenCompraNuevo.getPersonaProveedor();
			if (ordenCompraNuevo.getPersonaProveedor().getIntTipoPersonaCod()
					.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)) {
				proveedor.setStrEtiqueta(proveedor.getIntIdPersona() + "-"
						+ proveedor.getNatural().getStrNombreCompleto()
						+ "-DNI:"
						+ proveedor.getDocumento().getStrNumeroIdentidad());

			} else if (ordenCompraNuevo.getPersonaProveedor()
					.getIntTipoPersonaCod()
					.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)) {
				proveedor.setStrEtiqueta(proveedor.getIntIdPersona() + "-"
						+ proveedor.getJuridica().getStrRazonSocial() + "-RUC:"
						+ proveedor.getStrRuc());
			}
			ordenCompraNuevo.setPersonaProveedor(proveedor);

			if (ordenCompraNuevo.getIntPersCuentaBancariaTransaccion() != null) {
				CuentaBancariaPK cuentaBancariaIdPago = new CuentaBancariaPK();
				cuentaBancariaIdPago.setIntIdPersona(ordenCompraNuevo
						.getIntPersPersonaProveedor());
				cuentaBancariaIdPago.setIntIdCuentaBancaria(ordenCompraNuevo
						.getIntPersCuentaBancariaTransaccion());
				CuentaBancaria cuentaBancariaPago = personaFacade
						.getCuentaBancariaPorPK(cuentaBancariaIdPago);
//				//Autor: jchavez / Tarea: Se agrega descripcion / Fecha: 01.10.2014
//				cuentaBancariaPago
//				.setStrEtiqueta(obtenerEtiquetaBanco(cuentaBancariaPago
//						.getIntBancoCod())
//						+ " "
//						+ cuentaBancariaPago.getStrNroCuentaBancaria());
				cuentaBancariaPago
				.setStrEtiqueta(obtenerEtiquetaBanco(cuentaBancariaPago));
//				//Fin jchavez - 01.10.2014
				ordenCompraNuevo.setCuentaBancariaPago(cuentaBancariaPago);
			}

			if (ordenCompraNuevo.getIntPersCuentaBancariaDetraccion() != null) {
				CuentaBancariaPK cuentaBancariaIdImpuesto = new CuentaBancariaPK();
				cuentaBancariaIdImpuesto.setIntIdPersona(ordenCompraNuevo
						.getIntPersPersonaProveedor());
				cuentaBancariaIdImpuesto
						.setIntIdCuentaBancaria(ordenCompraNuevo
								.getIntPersCuentaBancariaDetraccion());
				CuentaBancaria cuentaBancariaImpuesto = personaFacade
						.getCuentaBancariaPorPK(cuentaBancariaIdImpuesto);
				
				
//				//Autor: jchavez / Tarea: Se agrega descripcion / Fecha: 01.10.2014
//				cuentaBancariaImpuesto
//				.setStrEtiqueta(obtenerEtiquetaBanco(cuentaBancariaImpuesto
//						.getIntBancoCod())
//						+ " "
//						+ cuentaBancariaImpuesto
//								.getStrNroCuentaBancaria());
				cuentaBancariaImpuesto
				.setStrEtiqueta(obtenerEtiquetaBanco(cuentaBancariaImpuesto));
//				//Fin jchavez - 01.10.2014
				ordenCompraNuevo
						.setCuentaBancariaImpuesto(cuentaBancariaImpuesto);
			}

			for (OrdenCompraDetalle ordenCompraDetalle : ordenCompraNuevo
					.getListaOrdenCompraDetalle()) {
				cargarPlanCuenta(ordenCompraDetalle);
				for (Sucursal sucursal : listaSucursal) {
					if (ordenCompraDetalle.getIntSucuIdSucursal().equals(
							sucursal.getId().getIntIdSucursal())) {
						ordenCompraDetalle.setSucursal(sucursal);
						for (Area area : sucursal.getListaArea()) {
							if (ordenCompraDetalle.getIntIdArea().equals(
									area.getId().getIntIdArea())) {
								ordenCompraDetalle.setArea(area);
								break;
							}
						}
					}
				}
			}

			ordenCompraNuevo.setBdMontoTotalDocumento(new BigDecimal(0));
			for (OrdenCompraDocumento ordenCompraDocumento : ordenCompraNuevo
					.getListaOrdenCompraDocumento()) {
				ordenCompraNuevo.setBdMontoTotalDocumento(ordenCompraNuevo
						.getBdMontoTotalDocumento().add(
								ordenCompraDocumento.getBdMontoDocumento()));
				for (Sucursal sucursal : listaSucursal) {
					if (ordenCompraDocumento.getIntSucuIdSucursal().equals(
							sucursal.getId().getIntIdSucursal())) {
						ordenCompraDocumento.setSucursal(sucursal);
						for (Area area : sucursal.getListaArea()) {
							if (ordenCompraDocumento.getIntIdArea().equals(
									area.getId().getIntIdArea())) {
								ordenCompraDocumento.setArea(area);
								break;
							}
						}
					}
				}
			}
			// Agregado por cdelosrios, 29/09/2013
			verificaAdmin();
			// Fin agregado por cdelosrios, 29/09/2013
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	// Agregado por cdelosrios, 29/09/2013
	private void verificaAdmin(){
		Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
		if (intIdPerfil.equals(ADMINISTRADOR_ZONAL_CENTRAL)
				|| intIdPerfil.equals(ADMINISTRADOR_ZONAL_FILIAL)
				|| intIdPerfil.equals(ADMINISTRADOR_ZONAL_LIMA)) {
			blnHabilitarAdmin = Boolean.TRUE;
			ordenCompraNuevo.setIntParaTipoDetraccion(Constante.PARAM_T_SELECCIONAR);
			ordenCompraNuevo.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
		} else {
			blnHabilitarAdmin = Boolean.FALSE;
		}
	}
	// Fin agregado por cdelosrios, 29/09/2013
	
	private void cargarPlanCuenta(OrdenCompraDetalle ordenCompraDetalle)
			throws Exception {
		PlanCuentaId planCuentaId = new PlanCuentaId();
		planCuentaId.setIntEmpresaCuentaPk(ordenCompraDetalle
				.getIntPersEmpresaCuenta());
		planCuentaId.setIntPeriodoCuenta(ordenCompraDetalle
				.getIntContPeriodoCuenta());
		planCuentaId.setStrNumeroCuenta(ordenCompraDetalle
				.getStrContNumeroCuenta());

		ordenCompraDetalle.setPlanCuenta(planCuentaFacade
				.getPlanCuentaPorPk(planCuentaId));
	}

	public void modificarRegistro() {
		try {
			log.info("--modificarRegistro");
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;

			ocultarMensaje();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void abrirPopUpBuscarRequisicion() {
		try {
			listaTablaAprobacion = tablaFacade.getListaTablaPorAgrupamientoA(
					Integer.parseInt(Constante.PARAM_T_APROBACION), "A");
			intTipoAprobacion = Constante.PARAM_T_APROBACION_CONTRATO;
			// Agregado por cdelosrios, 26/09/2013
			List<Tabla> lstTablaAprobacionTmp = new ArrayList<Tabla>();
			Tabla tablaAprobacionTmp = null;
			Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
			if (listaTablaAprobacion != null && !listaTablaAprobacion.isEmpty()) {
				for (Tabla tablaAprobacion : listaTablaAprobacion) {
					if (intIdPerfil.equals(ADMINISTRADOR_ZONAL_CENTRAL)
							|| intIdPerfil.equals(ADMINISTRADOR_ZONAL_FILIAL)
							|| intIdPerfil.equals(ADMINISTRADOR_ZONAL_LIMA)) {
						tablaAprobacionTmp = new Tabla();
						tablaAprobacionTmp.setIntIdMaestro(tablaAprobacion
								.getIntIdMaestro());
						tablaAprobacionTmp.setIntIdDetalle(Constante.PARAM_T_APROBACION_CAJACHICA);
						tablaAprobacionTmp.setStrDescripcion("Gastos menores - caja chica");
						tablaAprobacionTmp.setIntEstado(tablaAprobacion.getIntEstado());
						tablaAprobacionTmp.setStrIdAgrupamientoA(tablaAprobacion.getStrIdAgrupamientoA());
						lstTablaAprobacionTmp.add(tablaAprobacionTmp);
						listaTablaAprobacion = lstTablaAprobacionTmp;
						intTipoAprobacion = Constante.PARAM_T_APROBACION_CAJACHICA;
						break;
					}
				}
			}
			// Fin agregado por cdelosrios, 26/09/2013
			cargarListaRequisicion();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void abrirPopUpBuscarPersona() {
		try {
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
			strFiltroTextoPersona = "";
			listaProveedor = new ArrayList<Proveedor>();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	//Autor: jchavez / Tarea: Se modifican filtros de obtencion de la cuenta / Fecha: 02.10.2014
	public void abrirPopUpBuscarCuentaBancaria(ActionEvent event) {
		try {
			
			log.info(ordenCompraNuevo.getPersonaProveedor());
			intTipoCuentaBancaria = Integer.parseInt((String) event.getComponent().getAttributes().get("item"));
			ordenCompraNuevo.setListaCuentaBancariaUsar(new ArrayList<CuentaBancaria>());

			if (intTipoCuentaBancaria.equals(Constante.ORDERCOMPRA_CUENTA_FORMAPAGO)) {
				for (CuentaBancaria cuentaBancaria : ordenCompraNuevo.getPersonaProveedor().getListaCuentaBancaria()) {
					for (CuentaBancariaFin cuentaBancariaFin : cuentaBancaria.getListaCuentaBancariaFin()) {
						if (cuentaBancariaFin.getId().getIntParaTipoFinCuenta().equals(Constante.PARAM_T_TIPORAZONCUENTA_ABONOS)) {
							ordenCompraNuevo.getListaCuentaBancariaUsar().add(cuentaBancaria);
							break;
						}
					}
				}
			} else if (intTipoCuentaBancaria.equals(Constante.ORDERCOMPRA_CUENTA_IMPUESTO)) {
				for (CuentaBancaria cuentaBancaria : ordenCompraNuevo.getPersonaProveedor().getListaCuentaBancaria()) {
					if (cuentaBancaria.getIntBancoCod().equals(Constante.PARAM_T_BANCOS_BANCONACION))
						for (CuentaBancariaFin cuentaBancariaFin : cuentaBancaria.getListaCuentaBancariaFin()) {
							if (cuentaBancariaFin.getId().getIntParaTipoFinCuenta().equals(Constante.PARAM_T_TIPORAZONCUENTA_DETRACCION)) {
								ordenCompraNuevo.getListaCuentaBancariaUsar().add(cuentaBancaria);
								break;
							}
						}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void buscarPersona() {
		String strDescEstadoContribuyente = "";
		String strDescCondicionContribuyente = "";
		try {
			log.info("--buscarPersona");
			Persona persona = null;
			boolean esProveedor = false;
			boolean proveedorValido = false;
			if (intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)) {
				persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI,
																				   strFiltroTextoPersona, EMPRESA_USUARIO);

				proveedorValido = MyUtil.poseeRol(persona,Constante.PARAM_T_TIPOROL_PROVEEDOR);

			} else if (intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)) {
				persona = personaFacade.getPersonaJuridicaYListaPersonaPorRucYIdEmpresa2(strFiltroTextoPersona, EMPRESA_USUARIO);
				/* Autor: jchavez / Tarea: Modificacion / Fecha: 01.10.2014
				 * Funcionalidad: Se retringe busqueda a:
				 * Estado Contribuyente: Activo
				 * Condición Contribuyente: Habido
				 */ 
				esProveedor = MyUtil.poseeRol(persona,Constante.PARAM_T_TIPOROL_PROVEEDOR);
				
				if (esProveedor) {
					proveedorValido = (persona.getJuridica().getIntCondContribuyente() != null 
							&& persona.getJuridica().getIntCondContribuyente().equals(Constante.PARAM_T_TIPOCONDCONTRIBUYENTE_HABIDO))
							&& (persona.getJuridica().getIntEstadoContribuyenteCod() != null 
							&& persona.getJuridica().getIntEstadoContribuyenteCod().equals(Constante.PARAM_T_TIPOESTADOCONTRIB_ACTIVO));
					
					if (proveedorValido) {
						strMsgErrorBusquedaProveedor = "";
					}else{
						//Descripcion Estado Contribuyente
						for (Tabla tabla : lstTablaEstadoContribuyente) {
							if (tabla.getIntIdDetalle().equals(persona.getJuridica().getIntEstadoContribuyenteCod())) {
								strDescEstadoContribuyente = tabla.getStrDescripcion();
								break;
							}
						}
					      
						//Descripcion Condicion Contribuyente
						for (Tabla tabla : lstTablaCondicionContribuyente) {
							if (tabla.getIntIdDetalle().equals(persona.getJuridica().getIntCondContribuyente())) {
								strDescCondicionContribuyente = tabla.getStrDescripcion();
								break;
							}
						}		
						strMsgErrorBusquedaProveedor = "El proveedor se encuentra Estado: "+strDescEstadoContribuyente+
						   " Condición: "+strDescCondicionContribuyente+", no puede seleccionarse ";
					}
					//Fin jchavez - 01.10.2014
				}else {
					strMsgErrorBusquedaProveedor = "El RUC ingresado no es de tipo rol PROVEEDOR.";
				}
			}

			// proveedorValido = Boolean.TRUE;
			log.info(persona);
			if (proveedorValido) {
				ProveedorId proveedorId = new ProveedorId();
				proveedorId.setIntPersEmpresa(EMPRESA_USUARIO);
				proveedorId.setIntPersPersona(persona.getIntIdPersona());
				Proveedor proveedor = logisticaFacade
						.getProveedorPorPK(proveedorId);
				String strListaProveedorDetalle = "";
				for (ProveedorDetalle proveedorDetalle : proveedor
						.getListaProveedorDetalle()) {
					strListaProveedorDetalle = strListaProveedorDetalle
							+ " / "
							+ obtenerEtiquetaTipoProveedor(proveedorDetalle
									.getIntParaTipoProveedor());
				}
				if (!strListaProveedorDetalle.isEmpty())
					strListaProveedorDetalle = MyUtil
							.quitarPrimerCaracter(strListaProveedorDetalle);
				proveedor.setStrListaProveedorDetalle(strListaProveedorDetalle);
				proveedor.setPersona(persona);
				listaProveedor.add(proveedor);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void seleccionarPersona(ActionEvent event) {
		try {
			Persona personaSeleccionada = (Persona) event.getComponent()
					.getAttributes().get("item");
			if (intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)) {
				MyUtil.agregarNombreCompleto(personaSeleccionada);
				MyUtil.agregarDocumentoDNI(personaSeleccionada);

				personaSeleccionada.setStrEtiqueta(personaSeleccionada
						.getIntIdPersona()
						+ "-"
						+ personaSeleccionada.getNatural()
								.getStrNombreCompleto()
						+ "-DNI:"
						+ personaSeleccionada.getDocumento()
								.getStrNumeroIdentidad());

			} else if (intTipoPersona
					.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)) {

				personaSeleccionada.setStrEtiqueta(personaSeleccionada
						.getIntIdPersona()
						+ "-"
						+ personaSeleccionada.getJuridica().getStrRazonSocial()
						+ "-RUC:" + personaSeleccionada.getStrRuc());
			}

			ordenCompraNuevo.setPersonaProveedor(personaSeleccionada);
			ordenCompraNuevo.setIntPersEmpresaProveedor(EMPRESA_USUARIO);
			ordenCompraNuevo.setIntPersPersonaProveedor(personaSeleccionada
					.getIntIdPersona());

			seleccionarTipoServicio();
			seleccionarTipoFormaPago();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private String obtenerEtiquetaTipoProveedor(Integer intTipoProveedor) {
		for (Tabla tabla : listaTablaProveedor) {
			if (tabla.getIntIdDetalle().equals(intTipoProveedor)) {
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}

	public void abrirPopUpBuscarPlanCuenta() {
		try {
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			planCuentaFiltro.getId().setIntPeriodoCuenta(
					MyUtil.obtenerAñoActual());
			planCuentaFiltro
					.setIntTipoBusqueda(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION);
			// listaPlanCuenta =
			// planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);
			buscarPlanCuenta();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void buscarPlanCuenta() {
		try {
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(
					planCuentaFiltro, usuario,
					Constante.PARAM_TRANSACCION_ORDENCOMPRA);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void abrirPopUpAgregarOrdenCompraDetalle() {
		try {
			ordenCompraDetalle = new OrdenCompraDetalle();
			ordenCompraDetalle.setIntSucuIdSucursal(usuario.getSucursal()
					.getId().getIntIdSucursal());
			seleccionarSucursalDetalle();
			ordenCompraDetalle.setIntSudeIdSubsucursal(usuario.getSubSucursal()
					.getId().getIntIdSubSucursal());
			ordenCompraDetalle.setIntParaTipoMoneda(ordenCompraNuevo
					.getIntParaTipoMoneda());
			strMensajeDetalle = "";
			// Agregado por cdelosrios, 29/09/2013
			Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
			if (intIdPerfil.equals(ADMINISTRADOR_ZONAL_CENTRAL)
					|| intIdPerfil.equals(ADMINISTRADOR_ZONAL_FILIAL)
					|| intIdPerfil.equals(ADMINISTRADOR_ZONAL_LIMA)) {
				blnHabilitarDetOrden = Boolean.TRUE;
			} else {
				blnHabilitarDetOrden = Boolean.FALSE;
			}
			// Fin agregado por cdelosrios, 29/09/2013
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void verOrdenCompraDetalle(ActionEvent event) {
		try {
			ordenCompraDetalle = (OrdenCompraDetalle) event.getComponent()
					.getAttributes().get("item");
			seleccionarSucursalDetalle();
			strMensajeDetalle = "";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void verOrdenCompraDocumento(ActionEvent event) {
		try {
			ordenCompraDocumento = (OrdenCompraDocumento) event.getComponent()
					.getAttributes().get("item");
			seleccionarSucursalDocumento();
			strMensajeDocumento = "";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void abrirPopUpAgregarOrdenCompraDocumento() {
		try {
			ordenCompraDocumento = new OrdenCompraDocumento();
			ordenCompraDocumento.setIntSucuIdSucursal(usuario.getSucursal()
					.getId().getIntIdSucursal());
			seleccionarSucursalDocumento();
			ordenCompraDocumento.setIntSudeIdSubsucursal(usuario
					.getSubSucursal().getId().getIntIdSubSucursal());
			ordenCompraDocumento.setIntParaTipoMoneda(ordenCompraNuevo
					.getIntParaTipoMoneda());
			ordenCompraDocumento.setBdMontoPagado(new BigDecimal(0));
			ordenCompraDocumento.setBdMontoIngresado(new BigDecimal(0));
			ordenCompraDocumento
					.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			//Agregado por cdelosrios, 08/10/2013
			ordenCompraDocumento.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			ordenCompraDocumento.setIntPersPersonaUsuario(PERSONA_USUARIO);
			//Fin agregado por cdelosrios, 08/10/2013
			
			strMensajeDocumento = "";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void cargarListaRequisicion() {
		//Agregado por cdelosrios, 02/10/2013
		Integer intIdSucursal = usuario.getSucursal().getId().getIntIdSucursal();
		Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
		List<DocumentoRequisicion> listaDocumentoRequisicionTmp = new ArrayList<DocumentoRequisicion>();
		//Fin agregado por cdelosrios, 02/10/2013
		try {
			listaDocumentoRequisicion = logisticaFacade.obtenerListaDocumentoRequisicion(EMPRESA_USUARIO,
							intTipoAprobacion, intItemRequisicion);
			for (DocumentoRequisicion documentoRequisicion : listaDocumentoRequisicion) {
				log.info(documentoRequisicion.getIntParaTipoAprobacion());
				log.info(documentoRequisicion.getRequisicion());
				//Agregado por cdelosrios, 02/10/2013
				if (intIdPerfil.equals(ADMINISTRADOR_ZONAL_CENTRAL)
						|| intIdPerfil.equals(ADMINISTRADOR_ZONAL_FILIAL)
						|| intIdPerfil.equals(ADMINISTRADOR_ZONAL_LIMA)) {
					if(documentoRequisicion.getRequisicion().getIntSucuIdSucursal().equals(intIdSucursal)){
						listaDocumentoRequisicionTmp.add(documentoRequisicion);
						listaDocumentoRequisicion = listaDocumentoRequisicionTmp;
					}else {
						listaDocumentoRequisicion = new ArrayList<DocumentoRequisicion>();
					}
				}
				//Fin agregado por cdelosrios, 02/10/2013
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void seleccionarRequisicion(ActionEvent event) {
		try {
			DocumentoRequisicion documentoRequisicionAgregar = (DocumentoRequisicion) event
					.getComponent().getAttributes().get("item");

			agregarSolcitante(documentoRequisicionAgregar.getRequisicion());

			añadirEtiquetaRequisicion(documentoRequisicionAgregar);
			ordenCompraNuevo
					.setIntPersEmpresaRequisicion(documentoRequisicionAgregar
							.getRequisicion().getId().getIntPersEmpresa());
			ordenCompraNuevo.setIntItemRequisicion(documentoRequisicionAgregar
					.getRequisicion().getId().getIntItemRequisicion());
			ordenCompraNuevo
					.setDocumentoRequisicion(documentoRequisicionAgregar);

			if (documentoRequisicionAgregar.getIntParaTipoAprobacion().equals(
					Constante.PARAM_T_APROBACION_EVALUACIONPROVEEDORES)) {
				agregarProveedorCuadroComparativo(documentoRequisicionAgregar);

			} else if (documentoRequisicionAgregar.getIntParaTipoAprobacion()
					.equals(Constante.PARAM_T_APROBACION_CONTRATO)) {
				agregarProveedorContrato(documentoRequisicionAgregar);

			} else if (documentoRequisicionAgregar.getIntParaTipoAprobacion()
					.equals(Constante.PARAM_T_APROBACION_INFORME)) {
				agregarProveedorInforme(documentoRequisicionAgregar);

			} else if (documentoRequisicionAgregar.getIntParaTipoAprobacion()
					.equals(Constante.PARAM_T_APROBACION_ORDENCOMPRA)
					|| documentoRequisicionAgregar.getIntParaTipoAprobacion()
							.equals(Constante.PARAM_T_APROBACION_CAJACHICA)) {
				ordenCompraNuevo
						.setIntParaTipoMoneda(documentoRequisicionAgregar
								.getRequisicion().getIntParaTipoMoneda());
			}

			seleccionarTipoServicio();
			seleccionarTipoFormaPago();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void agregarProveedorContrato(
			DocumentoRequisicion documentoRequisicion) throws Exception {
		Contrato contrato = documentoRequisicion.getContrato();
		Persona empresaServicio = personaFacade.getPersonaPorPK(contrato
				.getIntPersPersonaServicio());

		if (empresaServicio.getIntTipoPersonaCod().equals(
				Constante.PARAM_T_TIPOPERSONA_NATURAL)) {
			empresaServicio = personaFacade
					.getPersonaNaturalPorIdPersona(contrato
							.getIntPersPersonaServicio());
			MyUtil.agregarNombreCompleto(empresaServicio);
			MyUtil.agregarDocumentoDNI(empresaServicio);
			empresaServicio.setStrEtiqueta(empresaServicio.getIntIdPersona()
					+ "-" + empresaServicio.getNatural().getStrNombreCompleto()
					+ "-DNI:"
					+ empresaServicio.getDocumento().getStrNumeroIdentidad());

		} else if (empresaServicio.getIntTipoPersonaCod().equals(
				Constante.PARAM_T_TIPOPERSONA_JURIDICA)) {
			empresaServicio = personaFacade
					.getPersonaJuridicaPorIdPersona(contrato
							.getIntPersPersonaServicio());
			empresaServicio.setStrEtiqueta(empresaServicio.getIntIdPersona()
					+ "-" + empresaServicio.getJuridica().getStrRazonSocial()
					+ "-RUC:" + empresaServicio.getStrRuc());
		}
		contrato.setEmpresaServicio(empresaServicio);
		ordenCompraNuevo.setPersonaProveedor(empresaServicio);
		ordenCompraNuevo.setIntPersEmpresaProveedor(contrato.getId()
				.getIntPersEmpresa());
		ordenCompraNuevo.setIntPersPersonaProveedor(empresaServicio
				.getIntIdPersona());
		ordenCompraNuevo.setIntPersEmpresaContrato(contrato.getId()
				.getIntPersEmpresa());
		ordenCompraNuevo.setIntItemContrato(contrato.getId()
				.getIntItemContrato());
		ordenCompraNuevo.setIntParaTipoMoneda(contrato.getIntParaTipoMoneda());
	}

	private void agregarProveedorInforme(
			DocumentoRequisicion documentoRequisicion) throws Exception {
		InformeGerencia informeGerencia = documentoRequisicion
				.getInformeGerencia();
		Persona empresaServicio = personaFacade
				.getPersonaJuridicaPorIdPersona(informeGerencia
						.getIntPersPersonaServicio());
		empresaServicio.setStrEtiqueta(empresaServicio.getIntIdPersona() + "-"
				+ empresaServicio.getJuridica().getStrRazonSocial() + "-RUC:"
				+ empresaServicio.getStrRuc());
		informeGerencia.setEmpresaServicio(empresaServicio);
		ordenCompraNuevo.setPersonaProveedor(empresaServicio);
		ordenCompraNuevo.setIntPersEmpresaProveedor(informeGerencia.getId()
				.getIntPersEmpresa());
		ordenCompraNuevo.setIntPersPersonaProveedor(empresaServicio
				.getIntIdPersona());
		ordenCompraNuevo.setIntPersEmpresaInformeGerencia(informeGerencia
				.getId().getIntPersEmpresa());
		ordenCompraNuevo.setIntItemInformeGerencia(informeGerencia.getId()
				.getIntItemInformeGerencia());
		ordenCompraNuevo.setIntParaTipoMoneda(informeGerencia
				.getIntParaTipoMoneda());
	}

	private void agregarProveedorCuadroComparativo(
			DocumentoRequisicion documentoRequisicion) throws Exception {
		CuadroComparativo cuadroComparativo = documentoRequisicion
				.getCuadroComparativo();
		for (CuadroComparativoProveedor cuadroComparativoProveedor : cuadroComparativo
				.getListaCuadroComparativoProveedor()) {
			if (cuadroComparativoProveedor.getIntParaEstadoSeleccion().equals(
					Constante.PARAM_T_SELECCIONPROVEEDOR_SELECCIONADO)) {
				cuadroComparativo
						.setProveedorAprobado(cuadroComparativoProveedor);
				break;
			}
		}
		CuadroComparativoProveedor proveedorAprobado = cuadroComparativo
				.getProveedorAprobado();
		Persona proveedor = personaFacade
				.getPersonaJuridicaPorIdPersona(proveedorAprobado
						.getIntPersPersonaProveedor());
		proveedor.setStrEtiqueta(proveedor.getIntIdPersona() + "-"
				+ proveedor.getJuridica().getStrRazonSocial() + "-RUC:"
				+ proveedor.getStrRuc());

		proveedorAprobado.setPersona(proveedor);
		cuadroComparativo.setProveedorAprobado(proveedorAprobado);
		ordenCompraNuevo.setPersonaProveedor(proveedor);
		ordenCompraNuevo.setIntPersEmpresaProveedor(cuadroComparativo.getId()
				.getIntPersEmpresa());
		ordenCompraNuevo
				.setIntPersPersonaProveedor(proveedor.getIntIdPersona());
		ordenCompraNuevo.setIntPersEmpresaCuadroComparativo(cuadroComparativo
				.getId().getIntPersEmpresa());
		ordenCompraNuevo.setIntItemCuadroComparativo(cuadroComparativo.getId()
				.getIntItemCuadroComparativo());
		ordenCompraNuevo.setIntParaTipoMoneda(cuadroComparativo
				.getProveedorAprobado().getIntParaTipoMoneda());
	}

	private Requisicion agregarSolcitante(Requisicion requisicion)
			throws Exception {
		Persona personaSolicitante = personaFacade
				.devolverPersonaCargada(requisicion
						.getIntPersPersonaSolicitante());
		personaSolicitante.setStrEtiqueta(personaSolicitante.getIntIdPersona()
				+ " " + personaSolicitante.getNatural().getStrNombreCompleto()
				+ " DNI : "
				+ personaSolicitante.getDocumento().getStrNumeroIdentidad());
		requisicion.setPersonaSolicitante(personaSolicitante);

		return requisicion;
	}

	private void añadirEtiquetaRequisicion(
			DocumentoRequisicion documentoRequisicionAgregar) throws Exception {
		TablaFacadeRemote tablaFacade = (TablaFacadeRemote) EJBFactory
				.getRemote(TablaFacadeRemote.class);
		Requisicion requisicionAgregar = documentoRequisicionAgregar
				.getRequisicion();

		Tabla tablaRequisicion = tablaFacade.getTablaPorIdMaestroYIdDetalle(
				Integer.parseInt(Constante.PARAM_T_REQUISICION),
				requisicionAgregar.getIntParaTipoRequisicion());
		Tabla tablaAprobacion = tablaFacade.getTablaPorIdMaestroYIdDetalle(
				Integer.parseInt(Constante.PARAM_T_APROBACION),
				requisicionAgregar.getIntParaTipoAprobacion());
		List<Tabla> listaTablaMoneda = tablaFacade
				.getListaTablaPorIdMaestro(Integer
						.parseInt(Constante.PARAM_T_TIPOMONEDA));

		String strEtiqueta = requisicionAgregar.getId().getIntItemRequisicion()
				+ " " + tablaRequisicion.getStrDescripcion();

		if (documentoRequisicionAgregar.getIntParaTipoAprobacion().equals(
				Constante.PARAM_T_APROBACION_EVALUACIONPROVEEDORES)) {
			CuadroComparativo cuadroComparativo = documentoRequisicionAgregar
					.getCuadroComparativo();
			strEtiqueta = strEtiqueta
					+ " - "
					+ cuadroComparativo.getId().getIntItemCuadroComparativo()
					+ " - "
					+ tablaAprobacion.getStrDescripcion()
					+ " - "
					+ MyUtil.convertirMonto(cuadroComparativo
							.getProveedorAprobado().getBdPrecioTotal())
					+ " "
					+ MyUtil.obtenerEtiquetaTabla(cuadroComparativo
							.getProveedorAprobado().getIntParaTipoMoneda(),
							listaTablaMoneda);

		} else if (documentoRequisicionAgregar.getIntParaTipoAprobacion()
				.equals(Constante.PARAM_T_APROBACION_CONTRATO)) {
			Contrato contrato = documentoRequisicionAgregar.getContrato();
			strEtiqueta = strEtiqueta
					+ " - "
					+ contrato.getId().getIntItemContrato()
					+ " - "
					+ tablaAprobacion.getStrDescripcion()
					+ " - "
					+ MyUtil.convertirMonto(contrato.getBdMontoContrato())
					+ " "
					+ MyUtil.obtenerEtiquetaTabla(
							contrato.getIntParaTipoMoneda(), listaTablaMoneda);

		} else if (documentoRequisicionAgregar.getIntParaTipoAprobacion()
				.equals(Constante.PARAM_T_APROBACION_INFORME)) {
			InformeGerencia informeGerencia = documentoRequisicionAgregar
					.getInformeGerencia();
			strEtiqueta = strEtiqueta
					+ " - "
					+ informeGerencia.getId().getIntItemInformeGerencia()
					+ " - "
					+ tablaAprobacion.getStrDescripcion()
					+ " - "
					+ MyUtil.convertirMonto(informeGerencia
							.getBdMontoAutorizado())
					+ " "
					+ MyUtil.obtenerEtiquetaTabla(
							informeGerencia.getIntParaTipoMoneda(),
							listaTablaMoneda);

		} else if (documentoRequisicionAgregar.getIntParaTipoAprobacion()
				.equals(Constante.PARAM_T_APROBACION_ORDENCOMPRA)
				|| documentoRequisicionAgregar.getIntParaTipoAprobacion()
						.equals(Constante.PARAM_T_APROBACION_CAJACHICA)) {
			strEtiqueta = strEtiqueta
					+ " - "
					+ requisicionAgregar.getId().getIntItemRequisicion()
					+ " - "
					+ tablaAprobacion.getStrDescripcion()
					+ " - "
					+ MyUtil.convertirMonto(requisicionAgregar
							.getBdMontoLogistica())
					+ " "
					+ MyUtil.obtenerEtiquetaTabla(
							requisicionAgregar.getIntParaTipoMoneda(),
							listaTablaMoneda);
		}

		for (Sucursal sucursal : listaSucursal) {
			if (sucursal.getId().getIntIdSucursal()
					.equals(requisicionAgregar.getIntSucuIdSucursalLogistica())) {
				requisicionAgregar.setSucursalLogistica(sucursal);
				break;
			}
		}

		strEtiqueta = strEtiqueta
				+ " "
				+ MyUtil.convertirFecha(requisicionAgregar
						.getTsFechaRequisicion())
				+ " - "
				+ requisicionAgregar.getSucursalLogistica().getJuridica()
						.getStrRazonSocial();
		requisicionAgregar.setStrEtiqueta(strEtiqueta);
	}

	public void eliminarRegistro() {
		try {
			OrdenCompra ordenCompraEliminar = registroSeleccionado;
			List<DocumentoSunat> listaDocumentoSunatAsociados = logisticaFacade
					.getListaDocumentoSunatPorOrdenCompra(ordenCompraEliminar);

			if (listaDocumentoSunatAsociados != null
					&& !listaDocumentoSunatAsociados.isEmpty()) {
				mostrarMensaje(
						Boolean.FALSE,
						"No se puede eliminar la Orden Compra porque esta asociada a un Documento Sunat.");
				return;
			}

			ordenCompraEliminar
					.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			//Agregado por cdelosrios, 15/10/2013
			ordenCompraEliminar.setTsFechaAnula(new Timestamp(new Date().getTime()));
			ordenCompraEliminar.setIntPersEmpresaAnulaPk(EMPRESA_USUARIO);
			ordenCompraEliminar.setIntPersPersonaAnulaPk(PERSONA_USUARIO);
			//Fin agregado por cdelosrios, 15/10/2013

			logisticaFacade.modificarOrdenCompraDirecto(ordenCompraEliminar);
			buscar();
			mostrarMensaje(Boolean.TRUE,
					"Se eliminó correctamente la Orden de Compra.");
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,
					"Hubo un error durante la eliminación de la Orden Compra.");
			log.error(e.getMessage(), e);
		}
	}

	private Timestamp obtenerFechaActual() {
		return new Timestamp(new Date().getTime());
	}

	public void habilitarPanelInferior() {
		try {
			cargarUsuario();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			//Agregado por cdelosrios, 29/09/2013
			blnHabilitarAdmin = Boolean.FALSE;
			blnHabilitarDetOrden = Boolean.FALSE;
			//Fin agregado por cdelosrios, 29/09/2013
			
			ordenCompraNuevo = new OrdenCompra();
			ordenCompraNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			ordenCompraNuevo.setTsFechaRegistro(obtenerFechaActual());
			ordenCompraNuevo
					.setIntParaEstadoOrden(Constante.PARAM_T_ESTADOORDEN_ABIERTO);
			ordenCompraNuevo
					.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_ORDENCOMPRA);
			ordenCompraNuevo
					.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			ordenCompraNuevo.setBdMontoTotalDetalle(new BigDecimal(0));
			ordenCompraNuevo
					.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			ordenCompraNuevo.setIntParaTipoDetraccion(new Integer(0));
			ordenCompraNuevo.setBdMontoTotalDocumento(new BigDecimal(0));

			seleccionarTipoServicio();
			seleccionarTipoFormaPago();

			habilitarGrabar = Boolean.TRUE;
			// Agregado por cdelosrios, 29/09/2013
			verificaAdmin();
			// Fin agregado por cdelosrios, 29/09/2013
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void seleccionarSucursalDocumento() {
		try {
			for (Sucursal sucursal : listaSucursal)
				if (sucursal.getId().getIntIdSucursal()
						.equals(ordenCompraDocumento.getIntSucuIdSucursal()))
					ordenCompraDocumento.setSucursal(sucursal);

			ordenCompraDocumento
					.getSucursal()
					.setListaSubSucursal(
							empresaFacade
									.getListaSubSucursalPorIdSucursal(ordenCompraDocumento
											.getIntSucuIdSucursal()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void seleccionarSucursalDetalle() {
		try {
			for (Sucursal sucursal : listaSucursal)
				if (sucursal.getId().getIntIdSucursal()
						.equals(ordenCompraDetalle.getIntSucuIdSucursal()))
					ordenCompraDetalle.setSucursal(sucursal);

			ordenCompraDetalle
					.getSucursal()
					.setListaSubSucursal(
							empresaFacade
									.getListaSubSucursalPorIdSucursal(ordenCompraDetalle
											.getIntSucuIdSucursal()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void cargarListaSucursal() throws Exception {
		listaSucursal = empresaFacade
				.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
		for (Sucursal sucursal : listaSucursal)
			sucursal.setListaArea(empresaFacade
					.getListaAreaPorSucursal(sucursal));

		// Ordenamos por nombre
		Collections.sort(listaSucursal, new Comparator<Sucursal>() {
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas()
						.compareTo(otro.getJuridica().getStrSiglas());
			}
		});
	}

	private void cargarListaAnios() {
		listaAnios = new ArrayList<Tabla>();
		Calendar cal = Calendar.getInstance();
		Tabla tabla = null;
		int cantidadAñosLista = 4;
		for (int i = 0; i < cantidadAñosLista; i++) {
			tabla = new Tabla();
			int year = cal.get(Calendar.YEAR);
			cal.add(Calendar.YEAR, 1);
			tabla.setIntIdDetalle(year);
			tabla.setStrDescripcion("" + year);
			listaAnios.add(tabla);
		}
	}

	public void mostrarMensaje(boolean exito, String mensaje) {
		if (exito) {
			mostrarMensajeExito = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mensajeOperacion = mensaje;
		} else {
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.TRUE;
			mensajeOperacion = mensaje;
		}
	}

	public void ocultarMensaje() {
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
	}

//	private boolean validarMontoPlanCuentaAcumulado(
//			OrdenCompraDetalle ordenCompraDetalleValidar) throws Exception {
//		//Modificado por cdelosrios, 11/11/2013
//		/*if (ordenCompraNuevo.getListaOrdenCompraDetalle() == null
//				|| ordenCompraNuevo.getListaOrdenCompraDetalle().isEmpty())
//			return Boolean.TRUE;*/
//		//Fin modificado por cdelosrios, 11/11/2013
//
//		BigDecimal bdMontoPlanCuenta = ordenCompraDetalleValidar.getBdPrecioTotal();
//		for (OrdenCompraDetalle ordenCompraDetalle : ordenCompraNuevo.getListaOrdenCompraDetalle()) {
//			//Modificado por cdelosrios, 11/11/2013
//			if (ordenCompraDetalle.getPlanCuenta()!=null &&
//					//Fin modificado por cdelosrios, 11/11/2013
//				ordenCompraDetalle.getPlanCuenta().getId().getStrNumeroCuenta()
//					.equalsIgnoreCase(ordenCompraDetalleValidar.getPlanCuenta().getId().getStrNumeroCuenta())) {
//					bdMontoPlanCuenta = bdMontoPlanCuenta.add(ordenCompraDetalleValidar.getBdPrecioTotal());
//			}
//		}
//
//		if (planCuentaFacade.validarMontoPlanCuenta(ordenCompraDetalleValidar.getPlanCuenta(), bdMontoPlanCuenta))
//			return Boolean.TRUE;
//		else
//			return Boolean.FALSE;
//	}
	
	public void agregarOrdenCompraDetalle() {
		List<OrdenCompraDetalle> lstOrdComDetTmp = new ArrayList<OrdenCompraDetalle>();
		List<OrdenCompraDetalle> lstOrdComDetNew = new ArrayList<OrdenCompraDetalle>();
		try {
			lstOrdComDetTmp.addAll(ordenCompraNuevo.getListaOrdenCompraDetalle());
			
			mostrarMensajeDetalle = Boolean.TRUE;
			if (ordenCompraDetalle.getBdCantidad() == null
					|| ordenCompraDetalle.getBdCantidad().signum() <= 0) {
				strMensajeDetalle = "Debe de ingresar una cantidad válida.";
				return;
			}
			if (ordenCompraDetalle.getBdPrecioUnitario() == null
					|| ordenCompraDetalle.getBdPrecioUnitario().signum() <= 0) {
				strMensajeDetalle = "Debe de ingresar un precio unitario válido.";
				return;
			}
			if (ordenCompraDetalle.getStrDescripcion() == null
					|| ordenCompraDetalle.getStrDescripcion().isEmpty()) {
				strMensajeDetalle = "Debe de ingresar una descripción.";
				return;
			}
			if (ordenCompraDetalle.getPlanCuenta() == null) {
				strMensajeDetalle = "Debe de agregar un tipo de gasto.";
				return;
			}
			if (ordenCompraDetalle.getIntSudeIdSubsucursal().equals(
					new Integer(0))) {
				strMensajeDetalle = "Debe de seleccionar un subsucursal válida.";
				return;
			}
			if (ordenCompraDetalle.getIntIdArea().equals(new Integer(0))) {
				strMensajeDetalle = "Debe de seleccionar un área válida.";
				return;
			}
			if (ordenCompraDetalle.getStrObservacion() == null
					|| ordenCompraDetalle.getStrObservacion().isEmpty()) {
				strMensajeDetalle = "Debe de ingresar una observación.";
				return;
			}

			ordenCompraDetalle.setBdPrecioTotal(ordenCompraDetalle
					.getBdPrecioUnitario().multiply(
							ordenCompraDetalle.getBdCantidad()));
			/*
			 * if(!planCuentaFacade.validarMontoPlanCuenta(ordenCompraDetalle.
			 * getPlanCuenta(), ordenCompraDetalle.getBdPrecioTotal())){
			 * strMensajeDetalle =
			 * "El monto ingresado no es valido para la configuración de la cuenta contable."
			 * ; return; }
			 */
			
			ordenCompraDetalle.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
			
			ordenCompraDetalle.setBdMontoSaldo(ordenCompraDetalle
					.getBdPrecioTotal());
			ordenCompraDetalle
					.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			ordenCompraDetalle.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			ordenCompraDetalle.setIntPersPersonaUsuario(PERSONA_USUARIO);
			
//			if (!validarMontoPlanCuentaAcumulado(ordenCompraDetalle)) {
//				strMensajeDetalle = "El monto ingresado no es valido para la configuración de la cuenta contable.";
//				return;
//			}
			if (!logisticaFacade.validarMontoOrdenCompraDetalle(ordenCompraDetalle, ordenCompraDetalle.getBdPrecioTotal())) {
				strMensajeDetalle = "El monto ingresado no es valido para la configuración de la cuenta contable.";
				return;
			}
			
			for (Area area : ordenCompraDetalle.getSucursal().getListaArea())
				if (area.getId().getIntIdArea()
						.equals(ordenCompraDetalle.getIntIdArea()))
					ordenCompraDetalle.setArea(area);

			if (!lstOrdComDetTmp.contains(ordenCompraDetalle)){
				lstOrdComDetTmp.add(ordenCompraDetalle);
				lstOrdComDetNew.add(ordenCompraDetalle);
			}
			
			calcularMontoTotalDetalle(lstOrdComDetTmp);
			if (ordenCompraNuevo.getBdMontoTotalDetalle().compareTo(ordenCompraNuevo.getDocumentoRequisicion().getRequisicion().getBdMontoLogistica())==1) {
				strMensajeDetalle = "El monto ingresado no puede ser mayor al monto del Documento Requisito.";
				return;
			}else {
				ordenCompraNuevo.getListaOrdenCompraDetalle().addAll(lstOrdComDetNew);
			}				
			mostrarMensajeDetalle = Boolean.FALSE;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void calcularMontoTotalDetalle(List<OrdenCompraDetalle> lstOrdComDetTmp) throws Exception {
//		ordenCompraNuevo.setBdMontoTotalDetalle(new BigDecimal(0));
//		for (OrdenCompraDetalle ordenCompraDetalle : ordenCompraNuevo
//				.getListaOrdenCompraDetalle())
//			ordenCompraNuevo.setBdMontoTotalDetalle(ordenCompraNuevo
//					.getBdMontoTotalDetalle().add(
//							ordenCompraDetalle.getBdPrecioTotal()));
		ordenCompraNuevo.setBdMontoTotalDetalle(new BigDecimal(0));
		for (OrdenCompraDetalle ordenCompraDetalle : lstOrdComDetTmp){
			ordenCompraNuevo.setBdMontoTotalDetalle(ordenCompraNuevo.getBdMontoTotalDetalle()
					.add(ordenCompraDetalle.getBdPrecioTotal()));
		}
			
	}

	private void calcularMontoTotalDocumento() throws Exception {
		ordenCompraNuevo.setBdMontoTotalDocumento(new BigDecimal(0));
		for (OrdenCompraDocumento ordenCompraDocumento : ordenCompraNuevo
				.getListaOrdenCompraDocumento())
			ordenCompraNuevo.setBdMontoTotalDocumento(ordenCompraNuevo
					.getBdMontoTotalDocumento().add(
							ordenCompraDocumento.getBdMontoDocumento()));
	}

	public void agregarOrdenCompraDocumento() {
		try {
			mostrarMensajeDocumento = Boolean.TRUE;
			if (ordenCompraDocumento.getTsFechaDocumento() == null) {
				strMensajeDocumento = "Debe de ingresar una fecha.";
				return;
			}
			//Autor: jchavez / Tarea: Nueva Validación / Fecha: 12.09.2014
			log.info("TIME EN DATE: "+new SimpleDateFormat("dd/MM/yyyy").format(new Date(ordenCompraDocumento.getTsFechaDocumento().getTime())));
			if (ordenCompraDocumento.getTsFechaDocumento() != null && new SimpleDateFormat("dd/MM/yyyy").format(new Date(ordenCompraDocumento.getTsFechaDocumento().getTime())).compareTo(new SimpleDateFormat("dd/MM/yyyy").format(new Date(ordenCompraNuevo.getTsFechaRegistro().getTime())))==-1) {
				strMensajeDocumento = "La fecha ingresada debe ser mayor o igual a la fecha de registro de la Orden Contable.";
				return;
			}
			//Fin jchavez - 12.09.2014
			if (ordenCompraDocumento.getBdMontoDocumento() == null
					|| ordenCompraDocumento.getBdMontoDocumento().signum() <= 0) {
				strMensajeDocumento = "Debe de ingresar un precio unitario válido.";
				return;
			}
			if (ordenCompraDocumento.getIntSudeIdSubsucursal().equals(
					new Integer(0))) {
				strMensajeDocumento = "Debe de seleccionar un subsucursal válida.";
				return;
			}
			if (ordenCompraDocumento.getIntIdArea().equals(new Integer(0))) {
				strMensajeDocumento = "Debe de seleccionar un área válida.";
				return;
			}
			if (ordenCompraDocumento.getStrObservacion() == null
					|| ordenCompraDocumento.getStrObservacion().isEmpty()) {
				strMensajeDocumento = "Debe de ingresar una observación.";
				return;
			}
			//Modificado por cdelosrios, 10/10/2013
			/*if (ordenCompraDocumento.getIntParaDocumentoGeneral().equals(
					Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)) {
				ordenCompraDocumento.setBdMontoPagado(ordenCompraDocumento
						.getBdMontoDocumento());
				ordenCompraDocumento.setBdMontoIngresado(new BigDecimal(0));
			}*/
			//Fin modificado por cdelosrios, 10/10/2013

			for (Area area : ordenCompraDocumento.getSucursal().getListaArea()) {
				if (area.getId().getIntIdArea()
						.equals(ordenCompraDocumento.getIntIdArea())) {
					ordenCompraDocumento.setArea(area);
					break;
				}
			}
			
			//Autor: jchavez / Tarea: Se agregan nuevos campos / Fecha: 04.10.2014
			ordenCompraDocumento.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			//Fin jchavez - 04.10.2014
			//Autor: jchavez / Tarea: Modificacion (OBS 1) / Fecha: 20.10.2014
			ordenCompraDocumento.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);
			ordenCompraDocumento.setBdMontoSaldo(BigDecimal.ZERO);
			//Fin jchavez - Fecha: 20.10.2014
			if (!ordenCompraNuevo.getListaOrdenCompraDocumento().contains(
					ordenCompraDocumento))
				ordenCompraNuevo.getListaOrdenCompraDocumento().add(
						ordenCompraDocumento);

			calcularMontoTotalDocumento();
			if (!validarMontoDetalleDocumento()) {
				ordenCompraNuevo.setBdMontoTotalDocumento(ordenCompraNuevo
						.getBdMontoTotalDocumento().subtract(
								ordenCompraDocumento.getBdMontoDocumento()));
				strMensajeDocumento = "El monto total de documento es mayor al monto total de detalle.";
				return;
			}
			mostrarMensajeDocumento = Boolean.FALSE;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private boolean validarMontoDetalleDocumento() {
		if (ordenCompraNuevo.getBdMontoTotalDetalle().compareTo(
				ordenCompraNuevo.getBdMontoTotalDocumento()) < 0)
			return Boolean.FALSE;

		return Boolean.TRUE;
	}

	private String obtenerEtiquetaBanco(CuentaBancaria cuentaBancaria) {
		String strDescBanco = "";
		String strDescTipoCuenta = "";
		String strDescTipoMoneda = "";
		try {
			//Autor: jchavez / Tarea: Se agrega descripcion / Fecha: 01.10.2014
			//Descripcion Banco
			for (Tabla tabla : listaTablaBancos) {
				if (tabla.getIntIdDetalle().equals(cuentaBancaria.getIntBancoCod())) {
					strDescBanco = tabla.getStrDescripcion();
					break;
				}
		    }
			//Descripcion Tipo Cuenta
			for (Tabla tabla : listaTablaTipoCtaBancaria) {
				if (tabla.getIntIdDetalle().equals(cuentaBancaria.getIntTipoCuentaCod())) {
					strDescTipoCuenta = tabla.getStrDescripcion();
					break;
				}
		    }
			//Descripcion Tipo Moneda
			for (Tabla tabla : listaTablaTipoMoneda) {
				if (tabla.getIntIdDetalle().equals(cuentaBancaria.getIntMonedaCod())) {
					strDescTipoMoneda = tabla.getStrDescripcion();
					break;
				}
		    }
			//Concatenamos descripcion Cuenta Bancaria:
			//Nombre de Banco – Tipo de Cuenta – Moneda – Nro. de Cuenta
			cuentaBancaria.setStrEtiqueta(strDescBanco +" - "+ strDescTipoCuenta +" - "+ strDescTipoMoneda +" - "+ cuentaBancaria.getStrNroCuentaBancaria());
			//Fin jchavez - 01.10.2014
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return cuentaBancaria.getStrEtiqueta();
//		for (Tabla tablaBanco : listaTablaBancos)
//		if (tablaBanco.getIntIdDetalle().equals(intBancoCod))
//			return tablaBanco.getStrDescripcion();
//
//	return "";
	}

	public void seleccionarCuentaBancaria(ActionEvent event) {
		try {
			CuentaBancaria cuentaBancaria = (CuentaBancaria) event
					.getComponent().getAttributes().get("item");
//			//Autor: jchavez / Tarea: Se agrega descripcion / Fecha: 01.10.2014
//			cuentaBancaria.setStrEtiqueta(obtenerEtiquetaBanco(cuentaBancaria
//					.getIntBancoCod())
//					+ " "
//					+ cuentaBancaria.getStrNroCuentaBancaria());
			cuentaBancaria
			.setStrEtiqueta(obtenerEtiquetaBanco(cuentaBancaria));
//			//Fin jchavez - 01.10.2014
			if (intTipoCuentaBancaria.equals(new Integer(1))) {
				ordenCompraNuevo.setIntPersCuentaBancariaTransaccion(cuentaBancaria.getId().getIntIdCuentaBancaria());
				ordenCompraNuevo.setCuentaBancariaPago(cuentaBancaria);

			} else if (intTipoCuentaBancaria.equals(new Integer(2))) {
				ordenCompraNuevo.setIntPersCuentaBancariaDetraccion(cuentaBancaria.getId().getIntIdCuentaBancaria());
				ordenCompraNuevo.setCuentaBancariaImpuesto(cuentaBancaria);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void seleccionarPlanCuenta(ActionEvent event) {
		try {
			PlanCuenta planCuenta = (PlanCuenta) event.getComponent()
					.getAttributes().get("item");
			ordenCompraDetalle.setIntPersEmpresaCuenta(planCuenta.getId()
					.getIntEmpresaCuentaPk());
			ordenCompraDetalle.setIntContPeriodoCuenta(planCuenta.getId()
					.getIntPeriodoCuenta());
			ordenCompraDetalle.setStrContNumeroCuenta(planCuenta.getId()
					.getStrNumeroCuenta());

			ordenCompraDetalle.setPlanCuenta(planCuenta);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void quitarOrdenCompraDetalle(ActionEvent event) {
		try {
			OrdenCompraDetalle ordenCompraDetalleQuitar = (OrdenCompraDetalle) event
					.getComponent().getAttributes().get("item");

			ordenCompraNuevo.setBdMontoTotalDetalle(ordenCompraNuevo
					.getBdMontoTotalDetalle().subtract(
							ordenCompraDetalleQuitar.getBdPrecioTotal()));

			if (!validarMontoDetalleDocumento()) {
				mostrarMensaje(
						Boolean.FALSE,
						"No se puede quitar el detalle. El monto total de documento sería mayor al monto total de detalle.");
				ordenCompraNuevo.setBdMontoTotalDetalle(ordenCompraNuevo
						.getBdMontoTotalDetalle().add(
								ordenCompraDetalleQuitar.getBdPrecioTotal()));
				return;
			}

			List<DocumentoSunatDetalle> listaDocumentoSunatDetalle = logisticaFacade
					.getListaDocumentoSunatDetallePorOrdenCompraDetalle(ordenCompraDetalleQuitar);
			if (listaDocumentoSunatDetalle != null
					&& !listaDocumentoSunatDetalle.isEmpty()) {
				mostrarMensaje(
						Boolean.FALSE,
						"No se puede quitar el detalle, esta siendo referenciado por un Documento Sunat Detalle.");
				ordenCompraNuevo.setBdMontoTotalDetalle(ordenCompraNuevo
						.getBdMontoTotalDetalle().add(
								ordenCompraDetalleQuitar.getBdPrecioTotal()));
				log.info(listaDocumentoSunatDetalle.get(0));
				return;
			}

			ordenCompraNuevo.getListaOrdenCompraDetalle().remove(
					ordenCompraDetalleQuitar);
			ocultarMensaje();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void quitarOrdenCompraDocumento(ActionEvent event) {
		try {
			OrdenCompraDocumento ordenCompraDocumentoQuitar = (OrdenCompraDocumento) event
					.getComponent().getAttributes().get("item");

			//Autor: jchavez / Tarea: Se comenta dado que la tabla AdelantoSunat SE ELIMINÓ / Fecha: 17.10.2014
//			List<AdelantoSunat> listaAdelantoSunat = logisticaFacade
//					.getListaAdelantoSunatPorOrdenCompraDocumento(ordenCompraDocumentoQuitar);
//			if (listaAdelantoSunat != null && !listaAdelantoSunat.isEmpty()) {
//				mostrarMensaje(
//						Boolean.FALSE,
//						"No se puede quitar el documento, esta siendo referenciado por un Documento Sunat.");
//				log.info(listaAdelantoSunat.get(0));
//				return;
//			}

			ordenCompraNuevo.getListaOrdenCompraDocumento().remove(
					ordenCompraDocumentoQuitar);
			ordenCompraNuevo.setBdMontoTotalDocumento(ordenCompraNuevo
					.getBdMontoTotalDocumento().subtract(
							ordenCompraDocumentoQuitar.getBdMontoDocumento()));
			ocultarMensaje();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	//Agregado por cdelosrios, 26/11/2013
	public void imprimirOrdenCompra(){
		String strNombreReporte = "";
		//Requisicion requisicion = (Requisicion)event.getComponent().getAttributes().get("requisicion");
//		OrdenCompra ordenCompra = registroSeleccionado;
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		String direccion = "";
		String telefono = "";
		Tabla tabla = null;
		String strArea = null;
		Tabla tablaFrecPago = null;
		Integer intIdPersonaProveedor = null;
		Persona personaProveedor = null;
		Tabla tablaTipoProceso = null;
		
		if(ordenCompraNuevo.getPersonaProveedor()!=null){
			intIdPersonaProveedor = ordenCompraNuevo.getPersonaProveedor().getIntIdPersona();
			if(intIdPersonaProveedor!=null){
				try {
					personaProveedor = personaFacade.getPersonaNaturalPorIdPersona(intIdPersonaProveedor);
				} catch (BusinessException e) {
					e.printStackTrace();
				}
				if(personaProveedor!=null){
					if(personaProveedor.getListaDomicilio()!=null && !personaProveedor.getListaDomicilio().isEmpty()){
						direccion = personaProveedor.getListaDomicilio().get(0).getStrDireccion();
					}
					if(personaProveedor.getListaComunicacion()!=null && !personaProveedor.getListaComunicacion().isEmpty()){
						for(Comunicacion comunicacion : personaProveedor.getListaComunicacion()){
							if(comunicacion.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPODIRECELECTRONICA_TELEFONO)){
								telefono = ""+comunicacion.getIntNumero();
								break;
							}
						}
					}
				}
			}
		}
		
		for(Sucursal sucursal : listaSucursal){
			if(ordenCompraNuevo.getDocumentoRequisicion().getRequisicion().getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())){
				for(Area area : sucursal.getListaArea()){
					if(area.getId().getIntIdArea().equals(ordenCompraNuevo.getDocumentoRequisicion().getRequisicion().getIntIdArea())){
						ordenCompraNuevo.getDocumentoRequisicion().getRequisicion().setArea(area);
						strArea = area.getStrDescripcion();
						break;
					}
				}
			}
		}
		
		try {
			tabla = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_FORMAPAGOEGRESO), ordenCompraNuevo.getIntParaFormaPago());
			tablaFrecPago = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_FRECUENCPAGOINT), ordenCompraNuevo.getIntParaTipoFrecuenca());
			tablaTipoProceso = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_APROBACION), ordenCompraNuevo.getDocumentoRequisicion().getRequisicion().getIntParaTipoAprobacion());
			
			parametro.put("P_TIPOORDEN", ordenCompraNuevo.getDocumentoRequisicion().getRequisicion().getIntParaTipoRequisicion());
			parametro.put("P_ITEMORDENCOMPRA", ordenCompraNuevo.getId().getIntItemOrdenCompra());
			parametro.put("P_FECHAORDENCOMPRA", Constante.sdf.format(ordenCompraNuevo.getTsFechaRegistro()));
			parametro.put("P_RAZONSOCIAL", ordenCompraNuevo.getPersonaProveedor().getJuridica().getStrRazonSocial());
			parametro.put("P_RUC", ordenCompraNuevo.getPersonaProveedor().getStrRuc());
			parametro.put("P_NROREQUISICION", ordenCompraNuevo.getDocumentoRequisicion().getRequisicion().getId().getIntItemRequisicion());
			parametro.put("P_SOLICITANTE", ordenCompraNuevo.getDocumentoRequisicion().getRequisicion().getPersonaSolicitante().getNatural().getStrNombreCompleto());
			parametro.put("P_AREA", strArea);
			parametro.put("P_DIRECCION", direccion);
			parametro.put("P_TELEFONO", telefono);
			parametro.put("P_TIPOPROCESO", ordenCompraNuevo.getDocumentoRequisicion().getRequisicion().getIntParaTipoAprobacion());
			parametro.put("P_DESC_TIPOPROCESO", tablaTipoProceso.getStrDescripcion());
			
			parametro.put("P_PLAZOENTREGA", Constante.sdf.format(ordenCompraNuevo.getTsPlazoEntrega()));
			parametro.put("P_LUGARENTREGA", ordenCompraNuevo.getStrLugarEntrega());
			parametro.put("P_FORMAPAGO", tabla.getStrDescripcion());
			parametro.put("P_NROCUENTABANCO", (ordenCompraNuevo.getCuentaBancariaPago()==null?"":ordenCompraNuevo.getCuentaBancariaPago().getStrEtiqueta()));
			parametro.put("P_NROCUENTABANCODETRAC", (ordenCompraNuevo.getCuentaBancariaImpuesto()==null?"":ordenCompraNuevo.getCuentaBancariaImpuesto().getStrEtiqueta()));
			parametro.put("P_OBSERVACION", ordenCompraNuevo.getStrObservacion());
			parametro.put("P_GARANTIA_PRODUCTO", new java.text.DecimalFormat("#,##0.00").format((ordenCompraNuevo.getBdGarantiaProductoServicio()==null?BigDecimal.ZERO:ordenCompraNuevo.getBdGarantiaProductoServicio())) + " " +tablaFrecPago.getStrDescripcion());
			
			//parametro.put("P_AREA", ordenCompraNuevo.getDocumentoRequisicion().getRequisicion().getArea().getStrDescripcion());
			//Autor: jchavez / Tarea: Modificacion / Fecha: 03.10.2014
			if (ordenCompraNuevo.getListaOrdenCompraDetalle()!=null && !ordenCompraNuevo.getListaOrdenCompraDetalle().isEmpty()) {
				for (OrdenCompraDetalle o : ordenCompraNuevo.getListaOrdenCompraDetalle()) {
					if (o.getIntAfectoIGV().equals(1)) {
						o.setStrAfecto("X");
					}else{
						o.setStrAfecto("");
					}
				}
			}
			//Fin jchavez - 03.10.2014
			strNombreReporte = "ordenCompra";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(ordenCompraNuevo.getListaOrdenCompraDetalle()), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	//Fin agregado por cdelosrios, 26/11/2013

//	public Auditoria beanAuditoria(Integer intTipoCambio, OrdenCompraDocumento beanAuditarOrdenCompraDocumento) {
//		Auditoria auditoria = new Auditoria();
//		Calendar fecHoy = Calendar.getInstance();
//		
//		auditoria.setListaAuditoriaMotivo(null);
//		auditoria.setStrTabla(Constante.PARAM_T_AUDITORIA_TES_ORDENCOMPRADOC);
//		auditoria.setIntEmpresaPk(EMPRESA_USUARIO);
//		auditoria.setStrLlave1(""+beanAuditarOrdenCompraDocumento.getId().getIntPersEmpresa());
//		auditoria.setStrLlave2(""+beanAuditarOrdenCompraDocumento.getId().getIntItemOrdenCompra());
//		auditoria.setStrLlave3(""+beanAuditarOrdenCompraDocumento.getId().getIntItemOrdenCompraDocumento());
//		auditoria.setIntTipo(intTipoCambio);
//		auditoria.setTsFecharegistro(new Timestamp(new Date().getTime()));
//		auditoria.setIntPersonaPk(PERSONA_USUARIO);
//		return auditoria;
//	}
//	
//	public List<Auditoria> generarAuditoria(Integer intTipoCambio, OrdenCompraDocumento beanAuditarOrdenCompraDocumento) throws BusinessException{
//		Auditoria auditoria = null;
//		List<Auditoria> lista = new ArrayList<Auditoria>();
//		try {
//			//Procedo de REGISTRO
//			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPOREGISTRO_INSERT)) {
//				auditoria = beanAuditoria(intTipoCambio, beanAuditarOrdenCompraDocumento);
//				auditoria.setStrColumna("FECHA_REGISTRO");
//				auditoria.setStrValoranterior(null);
//				auditoria.setStrValornuevo(""+new Timestamp(new Date().getTime()));
//				
//				lista.add(auditoria);
//			}
//		} catch (Exception e) {
//			log.error("Error en generarAuditoria --> "+e);
//		}
//		return lista;
//	}
//	//
//	public Auditoria grabarAuditoria(Auditoria auditoria)throws BusinessException {		
//		try {
//			//En el proceso de grabar Auditoria, para el caso de Presupuestos no se grabará tabla AUDITORIA_MOTIVO
//			auditoriaFacade.grabarAuditoria(auditoria);
//			
//		} catch (Exception e) {
//			log.error("Error en grabarAuditoria ---> "+e);
//		}		
//		return auditoria;
//	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}

	public String getMensajeOperacion() {
		return mensajeOperacion;
	}

	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}

	public boolean isMostrarBtnEliminar() {
		return mostrarBtnEliminar;
	}

	public void setMostrarBtnEliminar(boolean mostrarBtnEliminar) {
		this.mostrarBtnEliminar = mostrarBtnEliminar;
	}

	public boolean isMostrarMensajeExito() {
		return mostrarMensajeExito;
	}

	public void setMostrarMensajeExito(boolean mostrarMensajeExito) {
		this.mostrarMensajeExito = mostrarMensajeExito;
	}

	public boolean isMostrarMensajeError() {
		return mostrarMensajeError;
	}

	public void setMostrarMensajeError(boolean mostrarMensajeError) {
		this.mostrarMensajeError = mostrarMensajeError;
	}

	public boolean isDeshabilitarNuevo() {
		return deshabilitarNuevo;
	}

	public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
		this.deshabilitarNuevo = deshabilitarNuevo;
	}

	public boolean isMostrarPanelInferior() {
		return mostrarPanelInferior;
	}

	public void setMostrarPanelInferior(boolean mostrarPanelInferior) {
		this.mostrarPanelInferior = mostrarPanelInferior;
	}

	public boolean isRegistrarNuevo() {
		return registrarNuevo;
	}

	public void setRegistrarNuevo(boolean registrarNuevo) {
		this.registrarNuevo = registrarNuevo;
	}

	public boolean isHabilitarGrabar() {
		return habilitarGrabar;
	}

	public void setHabilitarGrabar(boolean habilitarGrabar) {
		this.habilitarGrabar = habilitarGrabar;
	}

	public boolean isPoseePermiso() {
		return poseePermiso;
	}

	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}

	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}

	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}

	public String getStrFiltroTextoPersona() {
		return strFiltroTextoPersona;
	}

	public void setStrFiltroTextoPersona(String strFiltroTextoPersona) {
		this.strFiltroTextoPersona = strFiltroTextoPersona;
	}

	public List<Persona> getListaPersona() {
		return listaPersona;
	}

	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}

	public OrdenCompra getOrdenCompraNuevo() {
		return ordenCompraNuevo;
	}

	public void setOrdenCompraNuevo(OrdenCompra ordenCompraNuevo) {
		this.ordenCompraNuevo = ordenCompraNuevo;
	}

	public OrdenCompra getOrdenCompraFiltro() {
		return ordenCompraFiltro;
	}

	public void setOrdenCompraFiltro(OrdenCompra ordenCompraFiltro) {
		this.ordenCompraFiltro = ordenCompraFiltro;
	}

	public OrdenCompra getRegistroSeleccionado() {
		return registroSeleccionado;
	}

	public void setRegistroSeleccionado(OrdenCompra registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}

	public List<OrdenCompra> getListaOrdenCompra() {
		return listaOrdenCompra;
	}

	public void setListaOrdenCompra(List<OrdenCompra> listaOrdenCompra) {
		this.listaOrdenCompra = listaOrdenCompra;
	}

	public Integer getIntTipoAprobacion() {
		return intTipoAprobacion;
	}

	public void setIntTipoAprobacion(Integer intTipoAprobacion) {
		this.intTipoAprobacion = intTipoAprobacion;
	}

	public List<Tabla> getListaTablaAprobacion() {
		return listaTablaAprobacion;
	}

	public void setListaTablaAprobacion(List<Tabla> listaTablaAprobacion) {
		this.listaTablaAprobacion = listaTablaAprobacion;
	}

	public List<DocumentoRequisicion> getListaDocumentoRequisicion() {
		return listaDocumentoRequisicion;
	}

	public void setListaDocumentoRequisicion(
			List<DocumentoRequisicion> listaDocumentoRequisicion) {
		this.listaDocumentoRequisicion = listaDocumentoRequisicion;
	}

	public OrdenCompraDetalle getOrdenCompraDetalle() {
		return ordenCompraDetalle;
	}

	public void setOrdenCompraDetalle(OrdenCompraDetalle ordenCompraDetalle) {
		this.ordenCompraDetalle = ordenCompraDetalle;
	}

	public OrdenCompraDocumento getOrdenCompraDocumento() {
		return ordenCompraDocumento;
	}

	public void setOrdenCompraDocumento(
			OrdenCompraDocumento ordenCompraDocumento) {
		this.ordenCompraDocumento = ordenCompraDocumento;
	}

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public String getStrMensajeDetalle() {
		return strMensajeDetalle;
	}

	public void setStrMensajeDetalle(String strMensajeDetalle) {
		this.strMensajeDetalle = strMensajeDetalle;
	}

	public boolean isMostrarMensajeDetalle() {
		return mostrarMensajeDetalle;
	}

	public void setMostrarMensajeDetalle(boolean mostrarMensajeDetalle) {
		this.mostrarMensajeDetalle = mostrarMensajeDetalle;
	}

	public List<Tabla> getListaAnios() {
		return listaAnios;
	}

	public void setListaAnios(List<Tabla> listaAnios) {
		this.listaAnios = listaAnios;
	}

	public String getStrMensajeDocumento() {
		return strMensajeDocumento;
	}

	public void setStrMensajeDocumento(String strMensajeDocumento) {
		this.strMensajeDocumento = strMensajeDocumento;
	}

	public boolean isMostrarMensajeDocumento() {
		return mostrarMensajeDocumento;
	}

	public void setMostrarMensajeDocumento(boolean mostrarMensajeDocumento) {
		this.mostrarMensajeDocumento = mostrarMensajeDocumento;
	}

	public List<Tabla> getListaTablaDocumentoGeneral() {
		return listaTablaDocumentoGeneral;
	}

	public void setListaTablaDocumentoGeneral(
			List<Tabla> listaTablaDocumentoGeneral) {
		this.listaTablaDocumentoGeneral = listaTablaDocumentoGeneral;
	}

	public Integer getIntTipoCuentaBancaria() {
		return intTipoCuentaBancaria;
	}

	public void setIntTipoCuentaBancaria(Integer intTipoCuentaBancaria) {
		this.intTipoCuentaBancaria = intTipoCuentaBancaria;
	}

	public PlanCuenta getPlanCuentaFiltro() {
		return planCuentaFiltro;
	}

	public void setPlanCuentaFiltro(PlanCuenta planCuentaFiltro) {
		this.planCuentaFiltro = planCuentaFiltro;
	}

	public List<PlanCuenta> getListaPlanCuenta() {
		return listaPlanCuenta;
	}

	public void setListaPlanCuenta(List<PlanCuenta> listaPlanCuenta) {
		this.listaPlanCuenta = listaPlanCuenta;
	}

	public List<Persona> getListaPersonaFiltro() {
		return listaPersonaFiltro;
	}

	public void setListaPersonaFiltro(List<Persona> listaPersonaFiltro) {
		this.listaPersonaFiltro = listaPersonaFiltro;
	}

	public Integer getIntTipoPersonaFiltro() {
		return intTipoPersonaFiltro;
	}

	public void setIntTipoPersonaFiltro(Integer intTipoPersonaFiltro) {
		this.intTipoPersonaFiltro = intTipoPersonaFiltro;
	}

	public Integer getIntTipoBusquedaPersona() {
		return intTipoBusquedaPersona;
	}

	public void setIntTipoBusquedaPersona(Integer intTipoBusquedaPersona) {
		this.intTipoBusquedaPersona = intTipoBusquedaPersona;
	}

	public String getStrTextoPersonaFiltro() {
		return strTextoPersonaFiltro;
	}

	public void setStrTextoPersonaFiltro(String strTextoPersonaFiltro) {
		this.strTextoPersonaFiltro = strTextoPersonaFiltro;
	}

	public List<Detraccion> getListaDetraccion() {
		return listaDetraccion;
	}

	public void setListaDetraccion(List<Detraccion> listaDetraccion) {
		this.listaDetraccion = listaDetraccion;
	}

	public List<Proveedor> getListaProveedor() {
		return listaProveedor;
	}

	public void setListaProveedor(List<Proveedor> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}

	public boolean isHabilitarCuentaFormaPago() {
		return habilitarCuentaFormaPago;
	}

	public void setHabilitarCuentaFormaPago(boolean habilitarCuentaFormaPago) {
		this.habilitarCuentaFormaPago = habilitarCuentaFormaPago;
	}

	public boolean isHabilitarCuentaImpuesto() {
		return habilitarCuentaImpuesto;
	}

	public void setHabilitarCuentaImpuesto(boolean habilitarCuentaImpuesto) {
		this.habilitarCuentaImpuesto = habilitarCuentaImpuesto;
	}

	// Agregado por cdelosrios, 29/09/2013
	public Integer getIntItemRequisicion() {
		return intItemRequisicion;
	}

	public void setIntItemRequisicion(Integer intItemRequisicion) {
		this.intItemRequisicion = intItemRequisicion;
	}

	public Boolean getBlnHabilitarDetOrden() {
		return blnHabilitarDetOrden;
	}

	public void setBlnHabilitarDetOrden(Boolean blnHabilitarDetOrden) {
		this.blnHabilitarDetOrden = blnHabilitarDetOrden;
	}

	public Boolean getBlnHabilitarAdmin() {
		return blnHabilitarAdmin;
	}

	public void setBlnHabilitarAdmin(Boolean blnHabilitarAdmin) {
		this.blnHabilitarAdmin = blnHabilitarAdmin;
	}
	// Fin agregado por cdelosrios, 29/09/2013
	//Autor: jchavez / Tarea: Creacion / Fecha: 01.10.2014
	public String getStrMsgErrorBusquedaProveedor() {
		return strMsgErrorBusquedaProveedor;
	}
	public void setStrMsgErrorBusquedaProveedor(String strMsgErrorBusquedaProveedor) {
		this.strMsgErrorBusquedaProveedor = strMsgErrorBusquedaProveedor;
	}	
	//Fin jchavez - 01.10.2014
	//Autor: jchavez / Tarea: Creacion / Fecha: 26.10.2014
	public Boolean getBlnExisteCancelado() {
		return blnExisteCancelado;
	}
	public void setBlnExisteCancelado(Boolean blnExisteCancelado) {
		this.blnExisteCancelado = blnExisteCancelado;
	}
	public Boolean getBlnExisteDocSunatRel() {
		return blnExisteDocSunatRel;
	}
	public void setBlnExisteDocSunatRel(Boolean blnExisteDocSunatRel) {
		this.blnExisteDocSunatRel = blnExisteDocSunatRel;
	}
	//Fin jchavez - 26.10.2014
}