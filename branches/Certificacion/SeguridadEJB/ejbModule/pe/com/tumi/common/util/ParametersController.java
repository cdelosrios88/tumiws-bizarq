package pe.com.tumi.common.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.domain.MaestroParametros;
import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.service.impl.GenericServiceImpl;
import pe.com.tumi.empresa.domain.PerNatural;
import pe.com.tumi.empresa.service.impl.EmpresaServiceImpl;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.service.impl.AccesoEspecialServiceImpl;
import pe.com.tumi.seguridad.service.impl.AdminMenuServiceImpl;
import pe.com.tumi.usuario.service.impl.UsuarioServiceImpl;

public class ParametersController extends GenericController {
	private GenericServiceImpl parametersService; 

	private		static final Integer 	intEstadoUniversal  = 1;
	private		static final Integer 	intConformacionEmpresa  = 2;
	private		static final Integer 	intTiposSucursal  = 3;
	private		static final Integer 	intTiposAreas  = 4;
	private		static final Integer 	intTiposZonal  = 5;
	private		static final Integer 	intTiposUsuario  = 6;
	private		static final Integer 	intTiposPersona  = 7;
	private		static final Integer 	intTiposDocumento  = 8;
	private		static final Integer 	intTiposPerfil  = 9;
	private		static final Integer 	intMotivosAcceso  = 10;
	private		static final Integer 	intTiposSucursalZonal  = 11;
	private		static final Integer 	intTiposSwTercero  = 12;
	private		static final Integer 	intTiposSesion  = 13;
	private		static final Integer 	intTiposAplicacion  = 14;
	private		static final Integer 	intTiposFrecuenciaConsulta  = 15;
	private		static final Integer 	intTiposOrdenAplicacion  = 16;
	private		static final Integer 	intTiposOpcionesConsultadas  = 17;
	private		static final Integer 	intTiposRangoFechas  = 18;
	private		static final Integer 	intTiposArchivo  = 19;
	private		static final Integer 	intTiposSolicitudCambio  = 20;
	private		static final Integer 	intTiposEstadoCambio  = 21;
	private		static final Integer 	intTiposClaseCambio  = 22;
	private		static final Integer 	intTipoDocInterno  = 23;
	private		static final Integer 	intOperadorLogico  = 24;
	private		static final Integer 	intTipoEntidad  = 25;
	private		static final Integer 	intNivelEntidad = 26;
	private		static final Integer 	intConfigEstructura = 27;
	private		static final Integer 	intTipoSocio = 28;
	private		static final Integer 	intModalidadEstructura = 29;
	private		static final Integer 	intEstadoPlanilla = 30;
	private		static final Integer 	intFechaEnvioCobro = 31;
	private		static final Integer 	intTipoEntidadTerceros = 32;
	private		static final Integer 	intTipoEntidadSalud  = 33;
	private		static final Integer 	intTipoConvenio = 34;
	private		static final Integer 	intEstadoDocumento = 35;
	private		static final Integer 	intRangoFecha = 36;
	private		static final Integer 	intTipoDomicilio = 37;
	private		static final Integer 	intTipoDireccion = 38;
	private		static final Integer 	intTipoVivienda = 39;
	private		static final Integer 	intTipoVia = 40;
	private		static final Integer 	intTipoZona = 41;
	private		static final Integer 	intTipoReferencia = 42;
	private		static final Integer 	intTipoComunicacion = 43;
	private		static final Integer 	intTipoLineaTelef = 44;
	private		static final Integer 	intTipoDireccElectronica = 45;
	private		static final Integer 	intTipoCuenta = 46;
	private		static final Integer 	intTipoMoneda = 47;
	private		static final Integer 	intRazonCuenta = 48;
	private		static final Integer 	intTipoVinculo = 49;
	private		static final Integer 	intEstadoCivil = 50;
	private		static final Integer 	intTipoDocConvenio = 51;
	private		static final Integer 	intTipoContribuyente = 54;
	private		static final Integer 	intTipoContabilidad = 55;
	private		static final Integer 	intCondicionContribuyente = 56;
	private		static final Integer 	intTipoEmisionComprovante = 57;
	private		static final Integer 	intTipoComercioExterior = 58;
	private		static final Integer 	intTipoRelacionPerJuridica = 59;
	private		static final Integer 	intTipoEstadoContrib = 60;
	private		static final Integer 	intTipoRol = 61;
	private		static final Integer 	intSexo = 62;
	private		static final Integer 	intEstadoAportacion = 63;
	private		static final Integer 	intTipoCobroAportacion = 64;
	private		static final Integer 	intCondicionSocio = 65;
	private		static final Integer 	intFechaVigenciaAport = 66;
	private		static final Integer 	intTipoDscto = 67;
	private		static final Integer 	intTipoInteres = 68;
	private		static final Integer 	intCalcInteres = 69;
	private		static final Integer 	intFrecPagoInt = 70;
	
	private    	ArrayList<SelectItem> 	cboEstados = new ArrayList<SelectItem>(); //1
	private    	ArrayList<SelectItem> 	cboConformacionEmpresa = new ArrayList<SelectItem>(); //2
	private    	ArrayList<SelectItem> 	cboTiposSucursal = new ArrayList<SelectItem>(); //3
	private    	ArrayList<SelectItem> 	cboTiposArea = new ArrayList<SelectItem>(); //4
	private    	ArrayList<SelectItem> 	cboTiposZonal = new ArrayList<SelectItem>(); //5
	private    	ArrayList<SelectItem> 	cboTiposUsuario = new ArrayList<SelectItem>(); //6
	private    	ArrayList<SelectItem> 	cboTiposPersona = new ArrayList<SelectItem>(); //7
	private    	ArrayList<SelectItem> 	cboTiposDocumento = new ArrayList<SelectItem>(); //8
	private    	ArrayList<SelectItem> 	cboTiposPerfil = new ArrayList<SelectItem>(); //9
	private    	ArrayList<SelectItem> 	cboMotivosAcceso = new ArrayList<SelectItem>(); //10
	private    	ArrayList<SelectItem> 	cboTiposSucursalZonal = new ArrayList<SelectItem>(); //11
	private    	ArrayList<SelectItem> 	cboTiposSwTerceros = new ArrayList<SelectItem>(); //12
	private    	ArrayList<SelectItem> 	cboTiposSesion = new ArrayList<SelectItem>(); //13
	private    	ArrayList<SelectItem> 	cboTiposAplicacion = new ArrayList<SelectItem>(); //14
	private    	ArrayList<SelectItem> 	cboFrecuenciaConsulta = new ArrayList<SelectItem>(); //15
	private    	ArrayList<SelectItem> 	cboTiposOrdenAplicacion = new ArrayList<SelectItem>(); //16
	private    	ArrayList<SelectItem> 	cboTiposOpcionesConsultadas = new ArrayList<SelectItem>(); //17
	private    	ArrayList<SelectItem> 	cboTiposRangoFechas = new ArrayList<SelectItem>(); //18
	private    	ArrayList<SelectItem> 	cboTiposArchivo = new ArrayList<SelectItem>(); //19
	private    	ArrayList<SelectItem> 	cboTiposSolicitudCambio = new ArrayList<SelectItem>(); //20
	private    	ArrayList<SelectItem> 	cboTiposEstadoCambio = new ArrayList<SelectItem>(); //21
	private    	ArrayList<SelectItem> 	cboTiposClaseCambio = new ArrayList<SelectItem>(); //22
	private    	ArrayList<SelectItem> 	cboTiposDocInterno = new ArrayList<SelectItem>(); //23
	private    	ArrayList<SelectItem> 	cboOperadoresLogicos = new ArrayList<SelectItem>(); //24
	private    	ArrayList<SelectItem> 	cboTipoEntidad = new ArrayList<SelectItem>(); //25
	private    	ArrayList<SelectItem> 	rdoTipoEntidad = new ArrayList<SelectItem>(); //25
	private    	ArrayList<SelectItem> 	cboNivelEntidad = new ArrayList<SelectItem>(); //26
	private    	ArrayList<SelectItem> 	cboConfigEstructura = new ArrayList<SelectItem>(); //27
	private    	ArrayList<SelectItem> 	cboTipoSocio = new ArrayList<SelectItem>(); //28
	private    	ArrayList<SelectItem> 	cboModalidadEstructura = new ArrayList<SelectItem>(); //29
	private    	ArrayList<SelectItem> 	cboEstadoPlanilla = new ArrayList<SelectItem>(); //30
	private    	ArrayList<SelectItem> 	cboFechaEnvioCobro = new ArrayList<SelectItem>(); //31
	private    	ArrayList<SelectItem> 	cboTipoEntidadTerceros = new ArrayList<SelectItem>(); //32
	private    	ArrayList<SelectItem> 	cboTipoEntidadSalud = new ArrayList<SelectItem>(); //33
	private    	ArrayList<SelectItem> 	cboTipoConvenio = new ArrayList<SelectItem>(); //34
	private    	ArrayList<SelectItem> 	cboEstadoDocumento = new ArrayList<SelectItem>(); //35
	private    	ArrayList<SelectItem> 	cboRangoFecha = new ArrayList<SelectItem>(); //36
	private    	ArrayList<SelectItem> 	cboTipoDomicilio = new ArrayList<SelectItem>(); //37
	private    	ArrayList<SelectItem> 	cboTipoDireccion = new ArrayList<SelectItem>(); //38
	private    	ArrayList<SelectItem> 	cboTipoVivienda = new ArrayList<SelectItem>(); //39
	private    	ArrayList<SelectItem> 	cboTipoVia = new ArrayList<SelectItem>(); //40
	private    	ArrayList<SelectItem> 	cboTipoZona = new ArrayList<SelectItem>(); //41
	private    	ArrayList<SelectItem> 	cboTipoReferencia = new ArrayList<SelectItem>(); //42
	private    	ArrayList<SelectItem> 	cboTipoComunicacion = new ArrayList<SelectItem>(); //43
	private    	ArrayList<SelectItem> 	cboTipoLineaTelef = new ArrayList<SelectItem>(); //44
	private    	ArrayList<SelectItem> 	cboTipoDireccElectronica = new ArrayList<SelectItem>(); //45
	private    	ArrayList<SelectItem> 	cboTipoCuenta = new ArrayList<SelectItem>(); //46
	private    	ArrayList<SelectItem> 	cboTipoMoneda = new ArrayList<SelectItem>(); //47
	private    	ArrayList<SelectItem> 	cboRazonCuenta = new ArrayList<SelectItem>(); //48
	private    	ArrayList<SelectItem> 	rdoRazonCuenta = new ArrayList<SelectItem>(); //48
	private    	ArrayList<SelectItem> 	cboTipoVinculo = new ArrayList<SelectItem>(); //49
	private    	ArrayList<SelectItem> 	cboEstadoCivil = new ArrayList<SelectItem>(); //50
	private    	ArrayList<SelectItem> 	cboTipoDocConvenio = new ArrayList<SelectItem>(); //51
	private    	ArrayList<SelectItem> 	cboTipoContribuyente = new ArrayList<SelectItem>(); //54
	private    	ArrayList<SelectItem> 	cboTipoContabilidad = new ArrayList<SelectItem>(); //55
	private    	ArrayList<SelectItem> 	cboCondicionContribuyente = new ArrayList<SelectItem>(); //56
	private    	ArrayList<SelectItem> 	cboTipoEmisionComprobante = new ArrayList<SelectItem>(); //57
	private    	ArrayList<SelectItem> 	cboTipoComercioExterior = new ArrayList<SelectItem>(); //58
	private    	ArrayList<SelectItem> 	cboTipoRelacionPerJuridica = new ArrayList<SelectItem>(); //59
	private    	ArrayList<SelectItem> 	cboTipoEstadoContrib = new ArrayList<SelectItem>(); //60
	private    	ArrayList<SelectItem> 	cboTipoRol = new ArrayList<SelectItem>(); //61
	private    	ArrayList<SelectItem> 	cboSexo = new ArrayList<SelectItem>(); //62
	private    	ArrayList<SelectItem> 	rdoSexo = new ArrayList<SelectItem>(); //62
	private    	ArrayList<SelectItem> 	cboEstadoAportacion = new ArrayList<SelectItem>(); //63
	private    	ArrayList<SelectItem> 	cboTipoCobroAport = new ArrayList<SelectItem>(); //64
	private    	ArrayList<SelectItem> 	cboCondicionSocio = new ArrayList<SelectItem>(); //65
	private    	ArrayList<SelectItem> 	cboFechaVigenciaAport = new ArrayList<SelectItem>(); //66
	private    	ArrayList<SelectItem> 	cboTipoDscto = new ArrayList<SelectItem>(); //67
	private    	ArrayList<SelectItem> 	cboTipoInteres = new ArrayList<SelectItem>(); //68
	private    	ArrayList<SelectItem> 	cboCalcInteres = new ArrayList<SelectItem>(); //69
	private    	ArrayList<SelectItem> 	cboFrecPagoInt = new ArrayList<SelectItem>(); //70


	//Metodo comun para obtener los parametros
	public ArrayList obtenerListaParametros(Integer idParametros) throws DaoException{
		log.info("-----------------------Debugging ParametersController.obtenerListaParametros()-----------------------------");
		setService(parametersService);
		
		ArrayList listaParametros = new ArrayList();
		
		ArrayList arrayParametros = new ArrayList();
		HashMap prmtParametros = new HashMap();
		prmtParametros.put("pIdParametro", idParametros);
		
		//Obteniendo lista
		log.info("Obteniendo array de Parametros.");
		arrayParametros = getService().listarMotivosAcceso(prmtParametros);
		log.info("arrayMotivos.size(): "+arrayParametros.size());
        for(int i=0; i<arrayParametros.size() ; i++){
        	MaestroParametros parametro = (MaestroParametros) arrayParametros.get(i);
        	log.info("parametro.getIntIdMaestro(): "+parametro.getIntIdMaestro());
        	log.info("parametro.getIntIdDetalle(): "+parametro.getIntIdDetalle());
        	log.info("parametro.getStrDescripcion(): "+parametro.getStrDescripcion());
        	listaParametros.add(new SelectItem(parametro.getIntIdDetalle(), parametro.getStrDescripcion()));
        }
        
		return listaParametros;
	}
	
	//Metodos Getter y Setter para llenar los Selects
	public ArrayList<SelectItem> getCboMotivosAcceso() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboMotivosAcceso()-----------------------------");
		ArrayList listMotivos = new ArrayList();
		this.cboMotivosAcceso.clear();
		listMotivos = obtenerListaParametros(intMotivosAcceso);
		cboMotivosAcceso = listMotivos;
		return cboMotivosAcceso;
	}

	public void setCboMotivosAcceso(ArrayList<SelectItem> cboMotivosAcceso) {
		this.cboMotivosAcceso = cboMotivosAcceso;
	}

	public ArrayList<SelectItem> getCboEstados() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboEstados()-----------------------------");
		ArrayList listEstados = new ArrayList();
		this.cboEstados.clear();
		listEstados = obtenerListaParametros(intEstadoUniversal);
		listEstados.add(0, new SelectItem(0, "Seleccionar.."));
		cboEstados = listEstados;
		return cboEstados;
	}

	public void setCboEstados(ArrayList<SelectItem> cboEstados) {
		this.cboEstados = cboEstados;
	}

	public ArrayList<SelectItem> getCboConformacionEmpresa() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboEstados()-----------------------------");
		ArrayList listConforEmpresa = new ArrayList();
		this.cboConformacionEmpresa.clear();
		listConforEmpresa = obtenerListaParametros(intConformacionEmpresa);
		cboConformacionEmpresa = listConforEmpresa;
		return cboConformacionEmpresa;
	}

	public void setCboConformacionEmpresa(
			ArrayList<SelectItem> cboConformacionEmpresa) {
		this.cboConformacionEmpresa = cboConformacionEmpresa;
	}

	public ArrayList<SelectItem> getCboTiposSucursal() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposSucursal()-----------------------------");
		ArrayList listTiposSucursal = new ArrayList();
		this.cboTiposSucursal.clear();
		listTiposSucursal = obtenerListaParametros(intTiposSucursal);
		cboTiposSucursal = listTiposSucursal;
		return cboTiposSucursal;
	}

	public void setCboTiposSucursal(ArrayList<SelectItem> cboTiposSucursal) {
		this.cboTiposSucursal = cboTiposSucursal;
	}

	public ArrayList<SelectItem> getCboTiposArea() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposArea()-----------------------------");
		ArrayList listTiposArea = new ArrayList();
		this.cboTiposArea.clear();
		listTiposArea = obtenerListaParametros(intTiposSucursal);
		cboTiposArea = listTiposArea;
		return cboTiposArea;
	}

	public void setCboTiposArea(ArrayList<SelectItem> cboTiposArea) {
		this.cboTiposArea = cboTiposArea;
	}

	public ArrayList<SelectItem> getCboTiposZonal() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposZonal()-----------------------------");
		ArrayList listTiposZonal = new ArrayList();
		this.cboTiposZonal.clear();
		listTiposZonal = obtenerListaParametros(intTiposZonal);
		cboTiposZonal = listTiposZonal;
		return cboTiposZonal;
	}

	public void setCboTiposZonal(ArrayList<SelectItem> cboTiposZonal) {
		this.cboTiposZonal = cboTiposZonal;
	}

	public ArrayList<SelectItem> getCboTiposUsuario() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposUsuario()-----------------------------");
		ArrayList listTiposUsuario = new ArrayList();
		this.cboTiposUsuario.clear();
		listTiposUsuario = obtenerListaParametros(intTiposUsuario);
		cboTiposUsuario = listTiposUsuario;
		return cboTiposUsuario;
	}

	public void setCboTiposUsuario(ArrayList<SelectItem> cboTiposUsuario) {
		this.cboTiposUsuario = cboTiposUsuario;
	}

	public ArrayList<SelectItem> getCboTiposPersona() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposPersona()-----------------------------");
		ArrayList listTiposPersona = new ArrayList();
		this.cboTiposPersona.clear();
		listTiposPersona = obtenerListaParametros(intTiposPersona);
		listTiposPersona.add(0, new SelectItem(0, "Seleccionar.."));
		cboTiposPersona = listTiposPersona;
		return cboTiposPersona;
	}

	public void setCboTiposPersona(ArrayList<SelectItem> cboTiposPersona) {
		this.cboTiposPersona = cboTiposPersona;
	}

	public ArrayList<SelectItem> getCboTiposDocumento() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposDocumento()-----------------------------");
		ArrayList listTiposDocumento = new ArrayList();
		this.cboTiposDocumento.clear();
		listTiposDocumento = obtenerListaParametros(intTiposDocumento);
		listTiposDocumento.add(0, new SelectItem(0, "Seleccionar.."));
		cboTiposDocumento = listTiposDocumento;
		return cboTiposDocumento;
	}

	public void setCboTiposDocumento(ArrayList<SelectItem> cboTiposDocumento) {
		this.cboTiposDocumento = cboTiposDocumento;
	}

	public ArrayList<SelectItem> getCboTiposPerfil() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposPerfil()-----------------------------");
		ArrayList listTiposPerfil = new ArrayList();
		this.cboTiposPerfil.clear();
		listTiposPerfil = obtenerListaParametros(intTiposPerfil);
		cboTiposPerfil = listTiposPerfil;
		return cboTiposPerfil;
	}

	public void setCboTiposPerfil(ArrayList<SelectItem> cboTiposPerfil) {
		this.cboTiposPerfil = cboTiposPerfil;
	}

	public ArrayList<SelectItem> getCboTiposSesion() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposPerfil()-----------------------------");
		ArrayList listTiposSesion = new ArrayList();
		this.cboTiposSesion.clear();
		listTiposSesion = obtenerListaParametros(intTiposSesion);
		cboTiposSesion = listTiposSesion;
		return cboTiposSesion;
	}

	public void setCboTiposSesion(ArrayList<SelectItem> cboTiposSesion) {
		this.cboTiposSesion = cboTiposSesion;
	}

	public ArrayList<SelectItem> getCboFrecuenciaConsulta() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboFrecuenciaConsulta()-----------------------------");
		ArrayList listTiposFrecConsulta = new ArrayList();
		this.cboFrecuenciaConsulta.clear();
		listTiposFrecConsulta = obtenerListaParametros(intTiposFrecuenciaConsulta);
		cboFrecuenciaConsulta = listTiposFrecConsulta;
		return cboFrecuenciaConsulta;
	}

	public void setCboFrecuenciaConsulta(ArrayList<SelectItem> cboFrecuenciaConsulta) {
		this.cboFrecuenciaConsulta = cboFrecuenciaConsulta;
	}

	public ArrayList<SelectItem> getCboTiposOrdenAplicacion() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboFrecuenciaConsulta()-----------------------------");
		ArrayList listTiposOrdenAplicacion = new ArrayList();
		this.cboTiposOrdenAplicacion.clear();
		listTiposOrdenAplicacion = obtenerListaParametros(intTiposOrdenAplicacion);
		cboTiposOrdenAplicacion = listTiposOrdenAplicacion;
		return cboTiposOrdenAplicacion;
	}

	public void setCboTiposOrdenAplicacion(
			ArrayList<SelectItem> cboTiposOrdenAplicacion) {
		this.cboTiposOrdenAplicacion = cboTiposOrdenAplicacion;
	}

	public ArrayList<SelectItem> getCboTiposOpcionesConsultadas() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboFrecuenciaConsulta()-----------------------------");
		ArrayList listTiposOpcionesConsultadas = new ArrayList();
		this.cboTiposOpcionesConsultadas.clear();
		listTiposOpcionesConsultadas = obtenerListaParametros(intTiposOpcionesConsultadas);
		cboTiposOpcionesConsultadas = listTiposOpcionesConsultadas;
		return cboTiposOpcionesConsultadas;
	}

	public void setCboTiposOpcionesConsultadas(
			ArrayList<SelectItem> cboTiposOpcionesConsultadas) {
		this.cboTiposOpcionesConsultadas = cboTiposOpcionesConsultadas;
	}

	public ArrayList<SelectItem> getCboTiposRangoFechas() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposPerfil()-----------------------------");
		ArrayList listTiposRangoFechas = new ArrayList();
		this.cboTiposRangoFechas.clear();
		listTiposRangoFechas = obtenerListaParametros(intTiposRangoFechas);
		cboTiposRangoFechas = listTiposRangoFechas;
		return cboTiposRangoFechas;
	}

	public void setCboTiposRangoFechas(ArrayList<SelectItem> cboTiposRangoFechas) {
		this.cboTiposRangoFechas = cboTiposRangoFechas;
	}

	public ArrayList<SelectItem> getCboTiposArchivo() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposArchivo()-----------------------------");
		ArrayList listTiposArchivo = new ArrayList();
		this.cboTiposArchivo.clear();
		listTiposArchivo = obtenerListaParametros(intTiposArchivo);
		cboTiposArchivo = listTiposArchivo;
		return cboTiposArchivo;
	}

	public void setCboTiposArchivo(ArrayList<SelectItem> cboTiposArchivo) {
		this.cboTiposArchivo = cboTiposArchivo;
	}

	public ArrayList<SelectItem> getCboTiposSolicitudCambio() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposSolicitudCambio()-----------------------------");
		ArrayList listTiposSolicitud = new ArrayList();
		this.cboTiposSolicitudCambio.clear();
		listTiposSolicitud = obtenerListaParametros(intTiposSolicitudCambio);
		cboTiposSolicitudCambio = listTiposSolicitud;
		return cboTiposSolicitudCambio;
	}

	public void setCboTiposSolicitudCambio(
			ArrayList<SelectItem> cboTiposSolicitudCambio) {
		this.cboTiposSolicitudCambio = cboTiposSolicitudCambio;
	}

	public ArrayList<SelectItem> getCboTiposEstadoCambio() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposEstadoCambio()-----------------------------");
		ArrayList listTiposEstadoCambio = new ArrayList();
		this.cboTiposEstadoCambio.clear();
		listTiposEstadoCambio = obtenerListaParametros(intTiposEstadoCambio);
		cboTiposEstadoCambio = listTiposEstadoCambio;
		return cboTiposEstadoCambio;
	}

	public void setCboTiposEstadoCambio(ArrayList<SelectItem> cboTiposEstadoCambio) {
		this.cboTiposEstadoCambio = cboTiposEstadoCambio;
	}

	public ArrayList<SelectItem> getCboTiposClaseCambio() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposClaseCambio()-----------------------------");
		ArrayList listTiposClaseCambio = new ArrayList();
		this.cboTiposClaseCambio.clear();
		listTiposClaseCambio = obtenerListaParametros(intTiposClaseCambio);
		cboTiposClaseCambio = listTiposClaseCambio;
		return cboTiposClaseCambio;
	}

	public void setCboTiposClaseCambio(ArrayList<SelectItem> cboTiposClaseCambio) {
		this.cboTiposClaseCambio = cboTiposClaseCambio;
	}

	public ArrayList<SelectItem> getCboTiposSwTerceros() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposSwTerceros()-----------------------------");
		ArrayList listTiposSwTercero = new ArrayList();
		this.cboTiposSwTerceros.clear();
		listTiposSwTercero = obtenerListaParametros(intTiposSwTercero);
		this.cboTiposSwTerceros = listTiposSwTercero;
		return cboTiposSwTerceros;
	}

	public void setCboTiposSwTerceros(ArrayList<SelectItem> cboTiposSwTerceros) {
		this.cboTiposSwTerceros = cboTiposSwTerceros;
	}

	public ArrayList<SelectItem> getCboTiposAplicacion() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposAplicacion()-----------------------------");
		ArrayList listTiposAplicacion = new ArrayList();
		this.cboTiposAplicacion.clear();
		listTiposAplicacion = obtenerListaParametros(intTiposAplicacion);
		this.cboTiposAplicacion = listTiposAplicacion;
		return cboTiposAplicacion;
	}

	public void setCboTiposAplicacion(ArrayList<SelectItem> cboTiposAplicacion) {
		this.cboTiposAplicacion = cboTiposAplicacion;
	}
	
	public ArrayList<SelectItem> getCboTiposDocInterno() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposDocInterno()-----------------------------");
		ArrayList listTiposDocInternos = new ArrayList();
		this.cboTiposDocInterno.clear();
		listTiposDocInternos = obtenerListaParametros(intTipoDocInterno);
		this.cboTiposDocInterno = listTiposDocInternos;
		return cboTiposDocInterno;
	}

	public void setCboTiposDocInterno(ArrayList<SelectItem> cboTiposDocInterno) {
		this.cboTiposDocInterno = cboTiposDocInterno;
	}

	public ArrayList<SelectItem> getCboOperadoresLogicos() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboOperadoresLogicos()-----------------------------");
		ArrayList listTiposAplicacion = new ArrayList();
		this.cboOperadoresLogicos.clear();
		listTiposAplicacion = obtenerListaParametros(intOperadorLogico);
		this.cboOperadoresLogicos = listTiposAplicacion;
		return cboOperadoresLogicos;
	}

	public void setCboOperadoresLogicos(ArrayList<SelectItem> cboOperadoresLogicos) {
		this.cboOperadoresLogicos = cboOperadoresLogicos;
	}

	public ArrayList<SelectItem> getCboRangoFecha() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboRangoFecha()-----------------------------");
		ArrayList listEstadoFec = new ArrayList();
		this.cboRangoFecha.clear();
		listEstadoFec = obtenerListaParametros(intRangoFecha);
		this.cboRangoFecha = listEstadoFec;
		return cboRangoFecha;
	}

	public void setCboRangoFecha(ArrayList<SelectItem> cboRangoFecha) {
		this.cboRangoFecha = cboRangoFecha;
	}
	
	public ArrayList<SelectItem> getCboTiposSucursalZonal() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTiposSucursalZonal()-----------------------------");
		ArrayList listTiposSucursalZonal = new ArrayList();
		this.cboTiposSucursalZonal.clear();
		listTiposSucursalZonal = obtenerListaParametros(intTiposSucursalZonal);
		this.cboTiposSucursalZonal = listTiposSucursalZonal;
		return cboTiposSucursalZonal;
	}

	public void setCboTiposSucursalZonal(ArrayList<SelectItem> cboTiposSucursalZonal) {
		this.cboTiposSucursalZonal = cboTiposSucursalZonal;
	}

	public ArrayList<SelectItem> getCboTipoDomicilio() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoDomicilio()-----------------------------");
		ArrayList listTiposDomicilio = new ArrayList();
		this.cboTipoDomicilio.clear();
		listTiposDomicilio = obtenerListaParametros(intTipoDomicilio);
		this.cboTipoDomicilio = listTiposDomicilio;
		return cboTipoDomicilio;
	}

	public void setCboTipoDomicilio(ArrayList<SelectItem> cboTipoDomicilio) {
		this.cboTipoDomicilio = cboTipoDomicilio;
	}

	public ArrayList<SelectItem> getCboTipoDireccion() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoDireccion()-----------------------------");
		ArrayList listTiposDireccion = new ArrayList();
		this.cboTipoDireccion.clear();
		listTiposDireccion = obtenerListaParametros(intTipoDireccion);
		this.cboTipoDireccion = listTiposDireccion;
		return cboTipoDireccion;
	}

	public void setCboTipoDireccion(ArrayList<SelectItem> cboTipoDireccion) {
		this.cboTipoDireccion = cboTipoDireccion;
	}

	public ArrayList<SelectItem> getCboTipoVivienda() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoVivienda()-----------------------------");
		ArrayList listTiposVivienda = new ArrayList();
		this.cboTipoVivienda.clear();
		listTiposVivienda = obtenerListaParametros(intTipoVivienda);
		this.cboTipoVivienda = listTiposVivienda;
		return cboTipoVivienda;
	}

	public void setCboTipoVivienda(ArrayList<SelectItem> cboTipoVivienda) {
		this.cboTipoVivienda = cboTipoVivienda;
	}

	public ArrayList<SelectItem> getCboTipoVia() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoVia()-----------------------------");
		ArrayList listTiposVia = new ArrayList();
		this.cboTipoVia.clear();
		listTiposVia = obtenerListaParametros(intTipoVia);
		this.cboTipoVia = listTiposVia;
		return cboTipoVia;
	}

	public void setCboTipoVia(ArrayList<SelectItem> cboTipoVia) {
		this.cboTipoVia = cboTipoVia;
	}

	public ArrayList<SelectItem> getCboTipoZona() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoZona()-----------------------------");
		ArrayList listTiposZona = new ArrayList();
		this.cboTipoZona.clear();
		listTiposZona = obtenerListaParametros(intTipoZona);
		this.cboTipoZona = listTiposZona;
		return cboTipoZona;
	}

	public void setCboTipoZona(ArrayList<SelectItem> cboTipoZona) {
		this.cboTipoZona = cboTipoZona;
	}

	public ArrayList<SelectItem> getCboTipoReferencia() {
		return cboTipoReferencia;
	}

	public void setCboTipoReferencia(ArrayList<SelectItem> cboTipoReferencia) {
		this.cboTipoReferencia = cboTipoReferencia;
	}

	public ArrayList<SelectItem> getCboTipoComunicacion() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoComunicacion()-----------------------------");
		ArrayList listTipoComunicacion = new ArrayList();
		this.cboTipoComunicacion.clear();
		listTipoComunicacion = obtenerListaParametros(intTipoComunicacion);
		this.cboTipoComunicacion = listTipoComunicacion;
		return cboTipoComunicacion;
	}

	public void setCboTipoComunicacion(ArrayList<SelectItem> cboTipoComunicacion) {
		this.cboTipoComunicacion = cboTipoComunicacion;
	}

	public ArrayList<SelectItem> getCboTipoLineaTelef() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoLineaTelef()-----------------------------");
		ArrayList listTipoLineaTelef = new ArrayList();
		this.cboTipoLineaTelef.clear();
		listTipoLineaTelef = obtenerListaParametros(intTipoLineaTelef);
		this.cboTipoLineaTelef = listTipoLineaTelef;
		return cboTipoLineaTelef;
	}

	public void setCboTipoLineaTelef(ArrayList<SelectItem> cboTipoLineaTelef) {
		this.cboTipoLineaTelef = cboTipoLineaTelef;
	}

	public ArrayList<SelectItem> getCboTipoDireccElectronica() {
		return cboTipoDireccElectronica;
	}

	public void setCboTipoDireccElectronica(
			ArrayList<SelectItem> cboTipoDireccElectronica) {
		this.cboTipoDireccElectronica = cboTipoDireccElectronica;
	}

	public ArrayList<SelectItem> getCboTipoDocConvenio() {
		return cboTipoDocConvenio;
	}

	public void setCboTipoDocConvenio(ArrayList<SelectItem> cboTipoDocConvenio) {
		this.cboTipoDocConvenio = cboTipoDocConvenio;
	}

	//Otras propiedades
	public GenericServiceImpl getParametersService() {
		return parametersService;
	}

	public void setParametersService(GenericServiceImpl parametersService) {
		this.parametersService = parametersService;
	}

	public ArrayList<SelectItem> getCboTipoEntidad() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoEntidad()-----------------------------");
		ArrayList listTiposEntidad = new ArrayList();
		this.cboTipoEntidad.clear();
		listTiposEntidad = obtenerListaParametros(intTipoEntidad);
		this.cboTipoEntidad = listTiposEntidad;
		this.cboTipoEntidad.add(0, new SelectItem(0, "Todos.."));
		return cboTipoEntidad;
	}

	public void setCboTipoEntidad(ArrayList<SelectItem> cboTipoEntidad) {
		this.cboTipoEntidad = cboTipoEntidad;
	}

	public ArrayList<SelectItem> getCboNivelEntidad() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboNivelEntidad()-----------------------------");
		ArrayList listNivelEntidad = new ArrayList();
		this.cboNivelEntidad.clear();
		listNivelEntidad = obtenerListaParametros(intNivelEntidad);
		this.cboNivelEntidad = listNivelEntidad;
		this.cboNivelEntidad.add(0, new SelectItem(0, "Todos.."));
		return cboNivelEntidad;
	}

	public void setCboNivelEntidad(ArrayList<SelectItem> cboNivelEntidad) {
		this.cboNivelEntidad = cboNivelEntidad;
	}

	public ArrayList<SelectItem> getCboConfigEstructura() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboConfigEstructura()-----------------------------");
		ArrayList listConfigEstructura = new ArrayList();
		this.cboConfigEstructura.clear();
		listConfigEstructura = obtenerListaParametros(intConfigEstructura);
		this.cboConfigEstructura = listConfigEstructura;
		this.cboConfigEstructura.add(0, new SelectItem(0, "Todos.."));
		return cboConfigEstructura;
	}

	public void setCboConfigEstructura(ArrayList<SelectItem> cboConfigEstructura) {
		this.cboConfigEstructura = cboConfigEstructura;
	}

	public ArrayList<SelectItem> getCboTipoSocio() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoSocio()-----------------------------");
		ArrayList listTipoSocio = new ArrayList();
		this.cboTipoSocio.clear();
		listTipoSocio = obtenerListaParametros(intTipoSocio);
		this.cboTipoSocio= listTipoSocio;
		this.cboTipoSocio.add(0, new SelectItem(0, "Todos.."));
		return cboTipoSocio;
	}

	public void setCboTipoSocio(ArrayList<SelectItem> cboTipoSocio) {
		this.cboTipoSocio = cboTipoSocio;
	}

	public ArrayList<SelectItem> getCboModalidadEstructura() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboModalidadEstructura()-----------------------------");
		ArrayList listModalidad = new ArrayList();
		this.cboModalidadEstructura.clear();
		listModalidad = obtenerListaParametros(intModalidadEstructura);
		this.cboModalidadEstructura = listModalidad;
		this.cboModalidadEstructura.add(0, new SelectItem(0, "Todos.."));
		return cboModalidadEstructura;
	}

	public void setCboModalidadEstructura(
			ArrayList<SelectItem> cboModalidadEstructura) {
		this.cboModalidadEstructura = cboModalidadEstructura;
	}

	public ArrayList<SelectItem> getCboEstadoPlanilla() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboEstadoPlanilla()-----------------------------");
		ArrayList listModalidad = new ArrayList();
		this.cboEstadoPlanilla.clear();
		listModalidad = obtenerListaParametros(intEstadoPlanilla);
		this.cboEstadoPlanilla = listModalidad;
		this.cboEstadoPlanilla.add(0, new SelectItem(0, "Todos.."));
		return cboEstadoPlanilla;
	}

	public void setCboEstadoPlanilla(ArrayList<SelectItem> cboEstadoPlanilla) {
		this.cboEstadoPlanilla = cboEstadoPlanilla;
	}

	public ArrayList<SelectItem> getCboEstadoDocumento() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboEstadoDocumento()-----------------------------");
		ArrayList listEstadoDocumento = new ArrayList();
		this.cboEstadoDocumento.clear();
		listEstadoDocumento = obtenerListaParametros(intEstadoDocumento);
		this.cboEstadoDocumento = listEstadoDocumento;
		this.cboEstadoDocumento.add(0, new SelectItem(0, "Todos.."));
		return cboEstadoDocumento;
	}

	public void setCboEstadoDocumento(ArrayList<SelectItem> cboEstadoDocumento) {
		this.cboEstadoDocumento = cboEstadoDocumento;
	}

	public ArrayList<SelectItem> getRdoTipoEntidad() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getRdoTipoEntidad()-----------------------------");
		ArrayList listTiposEntidad = new ArrayList();
		this.rdoTipoEntidad.clear();
		listTiposEntidad = obtenerListaParametros(intTipoEntidad);
		this.rdoTipoEntidad = listTiposEntidad;
		return rdoTipoEntidad;
	}

	public void setRdoTipoEntidad(ArrayList<SelectItem> rdoTipoEntidad) {
		this.rdoTipoEntidad = rdoTipoEntidad;
	}

	public ArrayList<SelectItem> getCboTipoEntidadTerceros() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoEntidadTerceros()-----------------------------");
		ArrayList listEstadoDocumento = new ArrayList();
		this.cboTipoEntidadTerceros.clear();
		listEstadoDocumento = obtenerListaParametros(intTipoEntidadTerceros);
		this.cboTipoEntidadTerceros = listEstadoDocumento;
		this.cboTipoEntidadTerceros.add(0, new SelectItem(0, "Todos.."));
		return cboTipoEntidadTerceros;
	}

	public void setCboTipoEntidadTerceros(
			ArrayList<SelectItem> cboTipoEntidadTerceros) {
		this.cboTipoEntidadTerceros = cboTipoEntidadTerceros;
	}

	public ArrayList<SelectItem> getCboFechaEnvioCobro() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboFechaEnvioCobro()-----------------------------");
		ArrayList listEstadoDocumento = new ArrayList();
		this.cboFechaEnvioCobro.clear();
		listEstadoDocumento = obtenerListaParametros(intFechaEnvioCobro);
		this.cboFechaEnvioCobro = listEstadoDocumento;
		return cboFechaEnvioCobro;
	}

	public void setCboFechaEnvioCobro(ArrayList<SelectItem> cboFechaEnvioCobro) {
		this.cboFechaEnvioCobro = cboFechaEnvioCobro;
	}

	public ArrayList<SelectItem> getCboTipoContribuyente() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoContribuyente()-----------------------------");
		ArrayList listTipoContribuyente = new ArrayList();
		this.cboTipoContribuyente.clear();
		listTipoContribuyente = obtenerListaParametros(intTipoContribuyente);
		listTipoContribuyente.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboTipoContribuyente = listTipoContribuyente;
		return cboTipoContribuyente;
	}

	public void setCboTipoContribuyente(ArrayList<SelectItem> cboTipoContribuyente) {
		this.cboTipoContribuyente = cboTipoContribuyente;
	}

	public ArrayList<SelectItem> getCboTipoContabilidad() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoContabilidad()-----------------------------");
		ArrayList listTipoContabilidad = new ArrayList();
		this.cboTipoContabilidad.clear();
		listTipoContabilidad = obtenerListaParametros(intTipoContabilidad);
		listTipoContabilidad.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboTipoContabilidad = listTipoContabilidad;
		return cboTipoContabilidad;
	}

	public void setCboTipoContabilidad(ArrayList<SelectItem> cboTipoContabilidad) {
		this.cboTipoContabilidad = cboTipoContabilidad;
	}

	public ArrayList<SelectItem> getCboCondicionContribuyente() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboCondicionContribuyente()-----------------------------");
		ArrayList listCondicionContribuyente = new ArrayList();
		this.cboCondicionContribuyente.clear();
		listCondicionContribuyente = obtenerListaParametros(intCondicionContribuyente);
		listCondicionContribuyente.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboCondicionContribuyente = listCondicionContribuyente;
		return cboCondicionContribuyente;
	}

	public void setCboCondicionContribuyente(
			ArrayList<SelectItem> cboCondicionContribuyente) {
		this.cboCondicionContribuyente = cboCondicionContribuyente;
	}

	public ArrayList<SelectItem> getCboTipoEmisionComprobante() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboCondicionContribuyente()-----------------------------");
		ArrayList listTipoEmisionComprovante = new ArrayList();
		this.cboTipoEmisionComprobante.clear();
		listTipoEmisionComprovante = obtenerListaParametros(intTipoEmisionComprovante);
		listTipoEmisionComprovante.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboTipoEmisionComprobante = listTipoEmisionComprovante;
		return cboTipoEmisionComprobante;
	}

	public void setCboTipoEmisionComprobante(
			ArrayList<SelectItem> cboTipoEmisionComprovante) {
		this.cboTipoEmisionComprobante = cboTipoEmisionComprovante;
	}

	public ArrayList<SelectItem> getCboTipoComercioExterior() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboCondicionContribuyente()-----------------------------");
		ArrayList listTipoComercioExterior = new ArrayList();
		this.cboTipoComercioExterior.clear();
		listTipoComercioExterior = obtenerListaParametros(intTipoComercioExterior);
		listTipoComercioExterior.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboTipoComercioExterior = listTipoComercioExterior;
		return cboTipoComercioExterior;
	}

	public void setCboTipoComercioExterior(
			ArrayList<SelectItem> cboTipoComercioExterior) {
		this.cboTipoComercioExterior = cboTipoComercioExterior;
	}

	public ArrayList<SelectItem> getCboTipoRelacionPerJuridica() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoRelacionPerJuridica()-----------------------------");
		ArrayList listTipoRelacionPerJuridica = new ArrayList();
		this.cboTipoRelacionPerJuridica.clear();
		listTipoRelacionPerJuridica = obtenerListaParametros(intTipoRelacionPerJuridica);
		this.cboTipoRelacionPerJuridica = listTipoRelacionPerJuridica;
		return cboTipoRelacionPerJuridica;
	}

	public void setCboTipoRelacionPerJuridica(
			ArrayList<SelectItem> cboTipoRelacionPerJuridica) {
		this.cboTipoRelacionPerJuridica = cboTipoRelacionPerJuridica;
	}

	public ArrayList<SelectItem> getCboTipoEstadoContrib() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoEstadoContrib()-----------------------------");
		ArrayList listTipoEstadoContrib = new ArrayList();
		this.cboTipoEstadoContrib.clear();
		listTipoEstadoContrib = obtenerListaParametros(intTipoEstadoContrib);
		this.cboTipoEstadoContrib = listTipoEstadoContrib;
		return cboTipoEstadoContrib;
	}

	public void setCboTipoEstadoContrib(ArrayList<SelectItem> cboTipoEstadoContrib) {
		this.cboTipoEstadoContrib = cboTipoEstadoContrib;
	}

	public ArrayList<SelectItem> getCboTipoRol() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoRol()-----------------------------");
		ArrayList listTipoRol = new ArrayList();
		this.cboTipoRol.clear();
		listTipoRol = obtenerListaParametros(intTipoRol);
		this.cboTipoRol = listTipoRol;
		return cboTipoRol;
	}

	public void setCboTipoRol(ArrayList<SelectItem> cboTipoRol) {
		this.cboTipoRol = cboTipoRol;
	}

	public ArrayList<SelectItem> getCboTipoVinculo() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoVinculo()-----------------------------");
		ArrayList listTipoVinculo = new ArrayList();
		this.cboTipoVinculo.clear();
		listTipoVinculo = obtenerListaParametros(intTipoVinculo);
		listTipoVinculo.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboTipoVinculo = listTipoVinculo;
		return cboTipoVinculo;
	}

	public void setCboTipoVinculo(ArrayList<SelectItem> cboTipoVinculo) {
		this.cboTipoVinculo = cboTipoVinculo;
	}

	public ArrayList<SelectItem> getCboEstadoCivil() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboEstadoCivilo()-----------------------------");
		ArrayList listEstadoCivil = new ArrayList();
		this.cboEstadoCivil.clear();
		listEstadoCivil = obtenerListaParametros(intEstadoCivil);
		listEstadoCivil.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboEstadoCivil = listEstadoCivil;
		return cboEstadoCivil;
	}

	public void setCboEstadoCivil(ArrayList<SelectItem> cboEstadoCivil) {
		this.cboEstadoCivil = cboEstadoCivil;
	}

	public ArrayList<SelectItem> getCboTipoMoneda() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoMoneda()-----------------------------");
		ArrayList listTipoMoneda = new ArrayList();
		this.cboTipoMoneda.clear();
		listTipoMoneda = obtenerListaParametros(intTipoMoneda);
		listTipoMoneda.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboTipoMoneda = listTipoMoneda;
		return cboTipoMoneda;
	}

	public void setCboTipoMoneda(ArrayList<SelectItem> cboTipoMoneda) {
		this.cboTipoMoneda = cboTipoMoneda;
	}

	public ArrayList<SelectItem> getCboTipoCuenta() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoCuenta()-----------------------------");
		ArrayList listTipoCuenta = new ArrayList();
		this.cboTipoCuenta.clear();
		listTipoCuenta = obtenerListaParametros(intTipoCuenta);
		listTipoCuenta.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboTipoCuenta = listTipoCuenta;
		return cboTipoCuenta;
	}

	public void setCboTipoCuenta(ArrayList<SelectItem> cboTipoCuenta) {
		this.cboTipoCuenta = cboTipoCuenta;
	}

	public ArrayList<SelectItem> getCboRazonCuenta() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboRazonCuenta()-----------------------------");
		ArrayList listRazonCuenta = new ArrayList();
		this.cboRazonCuenta.clear();
		listRazonCuenta = obtenerListaParametros(intRazonCuenta);
		listRazonCuenta.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboRazonCuenta = listRazonCuenta;
		return cboRazonCuenta;
	}

	public void setCboRazonCuenta(ArrayList<SelectItem> cboRazonCuenta) {
		this.cboRazonCuenta = cboRazonCuenta;
	}

	public ArrayList<SelectItem> getCboSexo() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboSexo()-----------------------------");
		ArrayList listSexo = new ArrayList();
		this.cboSexo.clear();
		listSexo = obtenerListaParametros(intSexo);
		listSexo.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboSexo = listSexo;
		return cboSexo;
	}

	public void setCboSexo(ArrayList<SelectItem> cboSexo) {
		this.cboSexo = cboSexo;
	}

	public ArrayList<SelectItem> getRdoSexo() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getRdoSexo()-----------------------------");
		ArrayList listSexo = new ArrayList();
		this.rdoSexo.clear();
		listSexo = obtenerListaParametros(intSexo);
		this.rdoSexo = listSexo;
		return rdoSexo;
	}

	public void setRdoSexo(ArrayList<SelectItem> rdoSexo) {
		this.rdoSexo = rdoSexo;
	}

	public ArrayList<SelectItem> getCboEstadoAportacion() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboEstadoAportacion()-----------------------------");
		ArrayList listEstadoAportacion = new ArrayList();
		this.cboEstadoAportacion.clear();
		listEstadoAportacion = obtenerListaParametros(intEstadoAportacion);
		this.cboEstadoAportacion = listEstadoAportacion;
		return cboEstadoAportacion;
	}

	public void setCboEstadoAportacion(ArrayList<SelectItem> cboEstadoAportacion) {
		this.cboEstadoAportacion = cboEstadoAportacion;
	}

	public ArrayList<SelectItem> getCboTipoCobroAport() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoCobroAport()-----------------------------");
		ArrayList listTipoCobroAportacion = new ArrayList();
		this.cboTipoCobroAport.clear();
		listTipoCobroAportacion = obtenerListaParametros(intTipoCobroAportacion);
		listTipoCobroAportacion.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboTipoCobroAport = listTipoCobroAportacion;
		return cboTipoCobroAport;
	}

	public void setCboTipoCobroAport(ArrayList<SelectItem> cboTipoCobroAport) {
		this.cboTipoCobroAport = cboTipoCobroAport;
	}

	public ArrayList<SelectItem> getCboCondicionSocio() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboCondicionSocio()-----------------------------");
		ArrayList listCondicionSocio = new ArrayList();
		this.cboCondicionSocio.clear();
		listCondicionSocio = obtenerListaParametros(intCondicionSocio);
		listCondicionSocio.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboCondicionSocio = listCondicionSocio;
		return cboCondicionSocio;
	}

	public void setCboCondicionSocio(ArrayList<SelectItem> cboCondicionSocio) {
		this.cboCondicionSocio = cboCondicionSocio;
	}

	public ArrayList<SelectItem> getCboFechaVigenciaAport() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboFechaVigenciaAport()-----------------------------");
		ArrayList listFechaVigenciaAport = new ArrayList();
		this.cboFechaVigenciaAport.clear();
		listFechaVigenciaAport = obtenerListaParametros(intFechaVigenciaAport);
		listFechaVigenciaAport.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboFechaVigenciaAport = listFechaVigenciaAport;
		return cboFechaVigenciaAport;
	}

	public void setCboFechaVigenciaAport(ArrayList<SelectItem> cboFechaVigenciaAport) {
		this.cboFechaVigenciaAport = cboFechaVigenciaAport;
	}

	public ArrayList<SelectItem> getCboTipoDscto() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoDscto()-----------------------------");
		ArrayList listTipoDscto = new ArrayList();
		this.cboTipoDscto.clear();
		listTipoDscto = obtenerListaParametros(intTipoDscto);
		listTipoDscto.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboTipoDscto = listTipoDscto;
		return cboTipoDscto;
	}

	public void setCboTipoDscto(ArrayList<SelectItem> cboTipoDscto) {
		this.cboTipoDscto = cboTipoDscto;
	}

	public ArrayList<SelectItem> getCboTipoInteres() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboTipoInteres()-----------------------------");
		ArrayList listTipoInteres = new ArrayList();
		this.cboTipoInteres.clear();
		listTipoInteres = obtenerListaParametros(intTipoInteres);
		this.cboTipoInteres = listTipoInteres;
		return cboTipoInteres;
	}

	public void setCboTipoInteres(ArrayList<SelectItem> cboTipoInteres) {
		this.cboTipoInteres = cboTipoInteres;
	}

	public ArrayList<SelectItem> getCboCalcInteres() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboCalcInteres()-----------------------------");
		ArrayList listCalcInteres = new ArrayList();
		this.cboCalcInteres.clear();
		listCalcInteres = obtenerListaParametros(intCalcInteres);
		listCalcInteres.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboCalcInteres = listCalcInteres;
		return cboCalcInteres;
	}

	public void setCboCalcInteres(ArrayList<SelectItem> cboCalcInteres) {
		this.cboCalcInteres = cboCalcInteres;
	}

	public ArrayList<SelectItem> getCboFrecPagoInt() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getCboFrecPagoInt()-----------------------------");
		ArrayList listFrecPagoInt = new ArrayList();
		this.cboFrecPagoInt.clear();
		listFrecPagoInt = obtenerListaParametros(intFrecPagoInt);
		listFrecPagoInt.add(0, new SelectItem(0, "Seleccionar.."));
		this.cboFrecPagoInt = listFrecPagoInt;
		return cboFrecPagoInt;
	}

	public void setCboFrecPagoInt(ArrayList<SelectItem> cboFrecPagoInt) {
		this.cboFrecPagoInt = cboFrecPagoInt;
	}

	public ArrayList<SelectItem> getRdoRazonCuenta() throws DaoException {
		log.info("-----------------------Debugging ParametersController.getRdoRazonCuenta()-----------------------------");
		ArrayList listFrecPagoInt = new ArrayList();
		this.rdoRazonCuenta.clear();
		listFrecPagoInt = obtenerListaParametros(intRazonCuenta);
		this.rdoRazonCuenta = listFrecPagoInt;
		return rdoRazonCuenta;
	}

	public void setRdoRazonCuenta(ArrayList<SelectItem> rdoRazonCuenta) {
		this.rdoRazonCuenta = rdoRazonCuenta;
	}
	
}
