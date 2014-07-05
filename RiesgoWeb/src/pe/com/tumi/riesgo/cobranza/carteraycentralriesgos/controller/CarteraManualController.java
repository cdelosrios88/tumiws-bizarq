package pe.com.tumi.riesgo.cobranza.carteraycentralriesgos.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManual;
import pe.com.tumi.riesgo.cartera.domain.composite.CarteraCreditoManualGen;
import pe.com.tumi.riesgo.cartera.facade.CarteraFacade;
import pe.com.tumi.riesgo.cartera.facade.CarteraFacadeLocal;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.Password;
import pe.com.tumi.seguridad.permiso.domain.PasswordId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;

public class CarteraManualController {

	protected static Logger log;

	private CarteraFacadeLocal carteraFacade;
	private PermisoFacadeRemote permisoFacade;
	private LibroDiarioFacadeRemote libroDiarioFacade;

	// Campos del Panel Filtro
	private Integer intMesFiltro;
	private Integer intAnioFiltro;
	private Integer intTipoCarteraFiltro;
	private Integer intEstadoFiltro;

	// Sesión
	private Usuario usuarioSesion;
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;

	// Campos del Panel Inferior
	private Integer intMes;
	private Integer intAnio;
	private Integer intTipoCartera;
	private Integer intTipoCobranza;
	private String strTipoCobranza;
	private Integer intEstadoCierre;
	private Integer intMesActual;
	private Integer intAnioActual;
	private Integer intDiaActual;

	// Campos del Pop Up Asiento Cartera
	private Integer intMesAsiento;
	private Integer intAnioAsiento;
	private Integer intCodLibroAsiento;

	// Campo para seleccionar la fila
	private Integer indListaCarteraManual;

	private Double douProvCredito;
	private Double douProvProciclica;

	private String strContrasenia;
	private String mensaje;
	private String colorMensaje;

	private boolean poseePermisoCarteraManual;
	private boolean poseePermisoInfocorp;
	private boolean poseePermisoTercerosMinsa;
	private boolean mostrarMensaje;
	private boolean mostrarConfirmacion;

	private CarteraCreditoManual carteraCreditoManual;
	private CarteraCreditoManualGen carteraCreditoManualGen;
	private PasswordId passwordId;
	private Password password;

	private List<SelectItem> listaAnios;
	private List<SelectItem> listaTipoCartera;

	private List<CarteraCreditoManual> listaCarteraManual;
	private List<LibroDiarioDetalle> listaAsientoCartera;

	public CarteraManualController() {
		log = Logger.getLogger(CarteraManualController.class);
		cargarUsuario();
		if (usuarioSesion != null)
			cargarValoresIniciales();
		else
			log.error("--Usuario obtenido es NULL.");
	}

	public String getInicioPage() {
		cargarUsuario();
		if (usuarioSesion != null) {
			limpiarFormulario();
		} else {
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		return "";
	}

	private void cargarUsuario() {
		usuarioSesion = (Usuario) getRequest().getSession().getAttribute(
				"usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId()
				.getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId()
				.getIntIdSubSucursal();
	}

	private void cargarValoresIniciales() {
		intMesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;
		intAnioActual = Calendar.getInstance().get(Calendar.YEAR);
		intDiaActual = Calendar.getInstance().get(Calendar.DATE);
		getListAnios();
		getListTipoCartera();
		carteraCreditoManual = new CarteraCreditoManual();
		carteraCreditoManualGen = new CarteraCreditoManualGen();
		listaCarteraManual = new ArrayList<CarteraCreditoManual>();
		listaAsientoCartera = new ArrayList<LibroDiarioDetalle>();

		intMesFiltro = intMesActual;
		intAnioFiltro = intAnioActual;
		intTipoCarteraFiltro = 23;
		intEstadoFiltro = -1;
		strContrasenia = "";
		poseePermisoCarteraManual = Boolean.TRUE;
		poseePermisoInfocorp = Boolean.TRUE;
		poseePermisoTercerosMinsa = Boolean.TRUE;

		carteraFacade = new CarteraFacade();
		try {
			permisoFacade = (PermisoFacadeRemote) EJBFactory
					.getRemote(PermisoFacadeRemote.class);
			libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory
					.getRemote(LibroDiarioFacadeRemote.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		obtenerCarteraManual();
	}

	public void limpiarFormulario() {
		intMesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;
		intAnioActual = Calendar.getInstance().get(Calendar.YEAR);
		intDiaActual = Calendar.getInstance().get(Calendar.DATE);
		getListAnios();
		getListTipoCartera();
		listaCarteraManual.clear();
		listaAsientoCartera.clear();

		intMesFiltro = intMesActual;
		intAnioFiltro = intAnioActual;
		intTipoCarteraFiltro = 23;
		intEstadoFiltro = -1;
		strContrasenia = null;
		poseePermisoCarteraManual = Boolean.TRUE;
		poseePermisoInfocorp = Boolean.TRUE;
		poseePermisoTercerosMinsa = Boolean.TRUE;

		mensaje = null;
		mostrarMensaje = Boolean.FALSE;
	}

	public void buscar() {
		int intMesInicio;
		int intMesFinal;
		int intEstadoMen;
		int intEstadoMay;
		try {
			if (intMesFiltro == -1) {
				intMesInicio = 1;
				intMesFinal = 12;
			} else {
				intMesInicio = intMesFinal = intMesFiltro;
			}
			if (intEstadoFiltro == -1) {
				intEstadoMen = 1;
				intEstadoMay = 2;
			} else {
				intEstadoMen = intEstadoMay = intEstadoFiltro;
			}
			listaCarteraManual = carteraFacade.buscaCarteraManual(intMesInicio,
					intMesFinal, intAnioFiltro, intEstadoMen, intEstadoMay);
			strContrasenia = null;
			mostrarMensaje = Boolean.FALSE;
			mensaje = null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void obtenerCarteraManual() {
		try {
			carteraCreditoManual = carteraFacade.obtenerCarteraManual();
			
			if(carteraCreditoManual.getIntTipoCobranza() == 1)
				strTipoCobranza = "Activa";
			else if(carteraCreditoManual.getIntTipoCobranza() == 2)
				strTipoCobranza = "Pasiva";
			else 
				strTipoCobranza = "Ninguna";
			
			intMes = carteraCreditoManual.getIntMes();
			intAnio = carteraCreditoManual.getIntAnio();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void verAsientoCartera() {
		String periodo;
		Integer intPeriodo;
		Integer DOCUMENTO_GENERAL = 900;
		LibroDiarioDetalle libroDiarioDetalle = null;
		try {
			CarteraCreditoManual carteraCreditoAux = listaCarteraManual
					.get(indListaCarteraManual);
			intMesAsiento = carteraCreditoAux.getIntMes();
			intAnioAsiento = carteraCreditoAux.getIntAnio();
			if (intMesAsiento < 10)
				periodo = String.valueOf(intAnioAsiento) + "0"
						+ String.valueOf(intMesAsiento);
			else
				periodo = String.valueOf(intAnioAsiento)
						+ String.valueOf(intMesAsiento);

			intPeriodo = Integer.parseInt(periodo);

			libroDiarioDetalle = new LibroDiarioDetalle();
			libroDiarioDetalle.setId(new LibroDiarioDetalleId());
			libroDiarioDetalle.getId().setIntPersEmpresaLibro(SESION_IDEMPRESA);
			libroDiarioDetalle.getId().setIntContPeriodoLibro(intPeriodo);
			libroDiarioDetalle.setIntParaDocumentoGeneral(DOCUMENTO_GENERAL);

			System.out.println("SESION_IDEMPRESA: " + SESION_IDEMPRESA);
			System.out.println("intPeriodo: " + intPeriodo);
			System.out.println("DOCUMENTO_GENERAL: " + DOCUMENTO_GENERAL);

			listaAsientoCartera = libroDiarioFacade
					.buscarLibroDiarioDetallePorPeriodoDocumento(libroDiarioDetalle);

			if (listaAsientoCartera.size() > 0)
				intCodLibroAsiento = listaAsientoCartera.get(0).getId()
						.getIntContCodigoLibro();
			else
				intCodLibroAsiento = null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void generar() {
		try {
			mostrarMensaje = Boolean.TRUE;
			if (carteraCreditoManual.getIntEstadoCierre() == 1) {
				carteraCreditoManualGen = carteraFacade.generarCarteraManual(
						intMes, intAnio, SESION_IDEMPRESA, SESION_IDUSUARIO);
				carteraCreditoManual.setDouMntProvCredito(carteraCreditoManualGen.getDouMntProvCredito());
				carteraCreditoManual.setDouMntProvProciclica(carteraCreditoManualGen.getDouMntProvProciclica());
				carteraCreditoManual.setDtFechaRegistro(carteraCreditoManualGen.getDtFechaRegistro());
				carteraCreditoManual.setIntAnio(carteraCreditoManualGen.getIntAnio());
				carteraCreditoManual.setIntEstadoCierre(carteraCreditoManualGen.getIntEstadoCierre());
				carteraCreditoManual.setIntMes(carteraCreditoManualGen.getIntMes());
				carteraCreditoManual.setIntPeriodo(carteraCreditoManualGen.getIntPeriodo());
				carteraCreditoManual.setIntTipoCartera(carteraCreditoManualGen.getIntTipoCartera());
				carteraCreditoManual.setIntTipoCobranza(carteraCreditoManualGen.getIntTipoCobranza());
				if(carteraCreditoManualGen.getIntGenerado() == 1) {
					mensaje = "Cartera Generada correctamente.";
					colorMensaje = "#0000FF";
				} else {
					mensaje = "No se puede realizar el proceso. La configuración de cartera de crédito ha vencido.";
					colorMensaje = "#FF0000";
				}
			} else {
				mensaje = "No se puede Generar la Cartera porque tiene estado Cerrado.";
				colorMensaje = "#FF0000";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void cerrar() {
		try {
			if (carteraCreditoManual.getIntEstadoCierre() == 1) {
				carteraCreditoManual = carteraFacade.cerrarCarteraManual(
						intMes, intAnio);
				mostrarMensaje = Boolean.TRUE;
				mensaje = "Cartera Cerrada correctamente.";
				colorMensaje = "#0000FF";
			} else {
				mostrarMensaje = Boolean.TRUE;
				mensaje = "No se puede Cerrar la Cartera porque tiene estado Cerrado.";
				colorMensaje = "#FF0000";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public boolean validaContrasenia() {
		boolean respuesta = Boolean.FALSE;
		Password password = null;
		Integer ID_TRANSACCION = 274;
		mensaje = "";
		mostrarMensaje = Boolean.FALSE;
		try {
			password = new Password();
			password.setId(new PasswordId());
			password.getId().setIntEmpresaPk(SESION_IDEMPRESA);
			password.getId().setIntIdTransaccion(ID_TRANSACCION);
			password.setStrContrasena(this.strContrasenia);
			password = permisoFacade.getPasswordPorPkYPass(password);
			if (password != null) {
				respuesta = Boolean.TRUE;
				mostrarConfirmacion = Boolean.TRUE;
				strContrasenia = null;
			} else {
				respuesta = Boolean.FALSE;
				mostrarMensaje = Boolean.TRUE;
				mensaje = "Contraseña Incorrecta.";
				colorMensaje = "#FF0000";
				mostrarConfirmacion = Boolean.FALSE;
				strContrasenia = null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return respuesta;
	}

	public void getListAnios() {
		listaAnios = new ArrayList<SelectItem>();
		try {
			int anio = intAnioActual;

			for (int i = 0; i < 5; i++) {
				listaAnios.add(new SelectItem(String.valueOf(anio), String
						.valueOf(anio)));
				anio--;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void getListTipoCartera() {
		listaTipoCartera = new ArrayList<SelectItem>();
		try {
			int anio = intAnioActual;
			int cont = 5;

			for (int i = 0; i < cont; i++) {
				listaTipoCartera.add(i, new SelectItem(anio));
				anio--;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	// Getters y Setters
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		CarteraManualController.log = log;
	}

	public CarteraFacadeLocal getCarteraFacade() {
		return carteraFacade;
	}

	public void setCarteraFacade(CarteraFacadeLocal carteraFacade) {
		this.carteraFacade = carteraFacade;
	}

	public Integer getIntMesFiltro() {
		return intMesFiltro;
	}

	public void setIntMesFiltro(Integer intMesFiltro) {
		this.intMesFiltro = intMesFiltro;
	}

	public Integer getIntAnioFiltro() {
		return intAnioFiltro;
	}

	public void setIntAnioFiltro(Integer intAnioFiltro) {
		this.intAnioFiltro = intAnioFiltro;
	}

	public Integer getIntTipoCarteraFiltro() {
		return intTipoCarteraFiltro;
	}

	public void setIntTipoCarteraFiltro(Integer intTipoCarteraFiltro) {
		this.intTipoCarteraFiltro = intTipoCarteraFiltro;
	}

	public Integer getIntEstadoFiltro() {
		return intEstadoFiltro;
	}

	public void setIntEstadoFiltro(Integer intEstadoFiltro) {
		this.intEstadoFiltro = intEstadoFiltro;
	}

	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}

	public Integer getSESION_IDEMPRESA() {
		return SESION_IDEMPRESA;
	}

	public void setSESION_IDEMPRESA(Integer sESION_IDEMPRESA) {
		SESION_IDEMPRESA = sESION_IDEMPRESA;
	}

	public Integer getSESION_IDUSUARIO() {
		return SESION_IDUSUARIO;
	}

	public void setSESION_IDUSUARIO(Integer sESION_IDUSUARIO) {
		SESION_IDUSUARIO = sESION_IDUSUARIO;
	}

	public Integer getSESION_IDSUCURSAL() {
		return SESION_IDSUCURSAL;
	}

	public void setSESION_IDSUCURSAL(Integer sESION_IDSUCURSAL) {
		SESION_IDSUCURSAL = sESION_IDSUCURSAL;
	}

	public Integer getSESION_IDSUBSUCURSAL() {
		return SESION_IDSUBSUCURSAL;
	}

	public void setSESION_IDSUBSUCURSAL(Integer sESION_IDSUBSUCURSAL) {
		SESION_IDSUBSUCURSAL = sESION_IDSUBSUCURSAL;
	}

	public Integer getIntMes() {
		return intMes;
	}

	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}

	public Integer getIntAnio() {
		return intAnio;
	}

	public void setIntAnio(Integer intAnio) {
		this.intAnio = intAnio;
	}

	public Integer getIntTipoCartera() {
		return intTipoCartera;
	}

	public void setIntTipoCartera(Integer intTipoCartera) {
		this.intTipoCartera = intTipoCartera;
	}

	public Integer getIntTipoCobranza() {
		return intTipoCobranza;
	}

	public void setIntTipoCobranza(Integer intTipoCobranza) {
		this.intTipoCobranza = intTipoCobranza;
	}

	public Integer getIntEstadoCierre() {
		return intEstadoCierre;
	}

	public void setIntEstadoCierre(Integer intEstadoCierre) {
		this.intEstadoCierre = intEstadoCierre;
	}

	public Double getDouProvCredito() {
		return douProvCredito;
	}

	public void setDouProvCredito(Double douProvCredito) {
		this.douProvCredito = douProvCredito;
	}

	public Double getDouProvProciclica() {
		return douProvProciclica;
	}

	public void setDouProvProciclica(Double douProvProciclica) {
		this.douProvProciclica = douProvProciclica;
	}

	public String getStrContrasenia() {
		return strContrasenia;
	}

	public void setStrContrasenia(String strContrasenia) {
		this.strContrasenia = strContrasenia;
	}

	public boolean isPoseePermisoCarteraManual() {
		return poseePermisoCarteraManual;
	}

	public void setPoseePermisoCarteraManual(boolean poseePermisoCarteraManual) {
		this.poseePermisoCarteraManual = poseePermisoCarteraManual;
	}

	public boolean isPoseePermisoInfocorp() {
		return poseePermisoInfocorp;
	}

	public void setPoseePermisoInfocorp(boolean poseePermisoInfocorp) {
		this.poseePermisoInfocorp = poseePermisoInfocorp;
	}

	public boolean isPoseePermisoTercerosMinsa() {
		return poseePermisoTercerosMinsa;
	}

	public void setPoseePermisoTercerosMinsa(boolean poseePermisoTercerosMinsa) {
		this.poseePermisoTercerosMinsa = poseePermisoTercerosMinsa;
	}

	public CarteraCreditoManual getCarteraCreditoManual() {
		return carteraCreditoManual;
	}

	public void setCarteraCreditoManual(
			CarteraCreditoManual carteraCreditoManual) {
		this.carteraCreditoManual = carteraCreditoManual;
	}

	public List<CarteraCreditoManual> getListaCarteraManual() {
		return listaCarteraManual;
	}

	public void setListaCarteraManual(
			List<CarteraCreditoManual> listaCarteraManual) {
		this.listaCarteraManual = listaCarteraManual;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public Password getPassword() {
		return password;
	}

	public PasswordId getPasswordId() {
		return passwordId;
	}

	public void setPasswordId(PasswordId passwordId) {
		this.passwordId = passwordId;
	}

	public Integer getIntMesActual() {
		return intMesActual;
	}

	public void setIntMesActual(Integer intMesActual) {
		this.intMesActual = intMesActual;
	}

	public Integer getIntAnioActual() {
		return intAnioActual;
	}

	public void setIntAnioActual(Integer intAnioActual) {
		this.intAnioActual = intAnioActual;
	}

	public Integer getIntDiaActual() {
		return intDiaActual;
	}

	public void setIntDiaActual(Integer intDiaActual) {
		this.intDiaActual = intDiaActual;
	}

	public List<SelectItem> getListaAnios() {
		return listaAnios;
	}

	public void setListaAnios(List<SelectItem> listaAnios) {
		this.listaAnios = listaAnios;
	}

	public List<SelectItem> getListaTipoCartera() {
		return listaTipoCartera;
	}

	public void setListaTipoCartera(List<SelectItem> listaTipoCartera) {
		this.listaTipoCartera = listaTipoCartera;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isMostrarMensaje() {
		return mostrarMensaje;
	}

	public void setMostrarMensaje(boolean mostrarMensaje) {
		this.mostrarMensaje = mostrarMensaje;
	}

	public void setMostrarConfirmacion(boolean mostrarConfirmacion) {
		this.mostrarConfirmacion = mostrarConfirmacion;
	}

	public boolean isMostrarConfirmacion() {
		return mostrarConfirmacion;
	}

	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}

	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
	}

	public LibroDiarioFacadeRemote getLibroDiarioFacade() {
		return libroDiarioFacade;
	}

	public void setLibroDiarioFacade(LibroDiarioFacadeRemote libroDiarioFacade) {
		this.libroDiarioFacade = libroDiarioFacade;
	}

	public Integer getIntMesAsiento() {
		return intMesAsiento;
	}

	public void setIntMesAsiento(Integer intMesAsiento) {
		this.intMesAsiento = intMesAsiento;
	}

	public Integer getIntAnioAsiento() {
		return intAnioAsiento;
	}

	public void setIntAnioAsiento(Integer intAnioAsiento) {
		this.intAnioAsiento = intAnioAsiento;
	}

	public void setListaAsientoCartera(
			List<LibroDiarioDetalle> listaAsientoCartera) {
		this.listaAsientoCartera = listaAsientoCartera;
	}

	public List<LibroDiarioDetalle> getListaAsientoCartera() {
		return listaAsientoCartera;
	}

	public void setIndListaCarteraManual(Integer indListaCarteraManual) {
		this.indListaCarteraManual = indListaCarteraManual;
	}

	public Integer getIndListaCarteraManual() {
		return indListaCarteraManual;
	}

	public void setIntCodLibroAsiento(Integer intCodLibroAsiento) {
		this.intCodLibroAsiento = intCodLibroAsiento;
	}

	public Integer getIntCodLibroAsiento() {
		return intCodLibroAsiento;
	}

	public void setColorMensaje(String colorMensaje) {
		this.colorMensaje = colorMensaje;
	}

	public String getColorMensaje() {
		return colorMensaje;
	}

	public String getStrTipoCobranza() {
		return strTipoCobranza;
	}

	public void setStrTipoCobranza(String strTipoCobranza) {
		this.strTipoCobranza = strTipoCobranza;
	}

	public void setCarteraCreditoManualGen(CarteraCreditoManualGen carteraCreditoManualGen) {
		this.carteraCreditoManualGen = carteraCreditoManualGen;
	}

	public CarteraCreditoManualGen getCarteraCreditoManualGen() {
		return carteraCreditoManualGen;
	}

}
