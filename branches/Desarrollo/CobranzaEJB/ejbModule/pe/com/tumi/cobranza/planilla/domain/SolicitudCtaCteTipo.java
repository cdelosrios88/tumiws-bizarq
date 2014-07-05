package pe.com.tumi.cobranza.planilla.domain;

import java.util.Date;
import java.util.List;

import pe.com.tumi.common.domain.EntidadBase;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;

public class SolicitudCtaCteTipo extends TumiDomain{
	
	SolicitudCtaCteTipoId id;

	private Integer	intTaraEstado;
	private Integer	intParaTipoorigen;
	private Integer	intParaEstadoanalisis;
	private String	strScctObservacion;
	private Date	dtFechaDocumento;
	private Integer	intMotivoSolicitud;
	private Integer	intEmpresaLibro;
	private Integer	intContPeriodolibro;
	private Integer	intCodigoLibro;
	private Integer	intEmpresaCuenta;
	private Integer	intCcobItemefectuado;
	
	
	
	//Auxiliar
	
	private Cuenta cuenta;
	private Movimiento movimiento;
	private List<CuentaConcepto> listaCtaCptoExpCredito;
	private List<GarantiaCreditoComp> listaGarantiaCreditoComp;
	
	private Integer intPersUsuario;
	private Integer intEmpresa;
	private Integer radioOpcionTransferencias;
	private String  strDescripcion;

   private SocioEstructura SocioEstructuraOrigen;
   private SocioEstructura socioEstructuraOrigenNueva;
   
   private SocioEstructura SocioEstructuraPrestamo;
   private SocioEstructura socioEstructuraPrestamoNueva;
   
   private Integer intPeriodoEnvioPlanilla;
   
   private SocioComp socioComp;
   private SocioComp socioCompGarante;
   private String  strCasoEnvioPlanilla;
   private String  strSiNoEnvioPlanilla;
   private Integer intParaCondicionCuentaFinal;
   private List<DescuentoIndebido>  listaDesctoIndebidoOrigen;
   private List<DescuentoIndebido>  listaDesctoIndebidoPrestamo;
   private List<Expediente>  listaExpediente;
   private Integer idSucursalUsuario;
   private Integer idSubSucursalUsuario;
   private List<Transferencia> listaTransferencias;
   
	public SolicitudCtaCteTipo(){
	   id = new SolicitudCtaCteTipoId();	
	   movimiento = new Movimiento();
	   cuenta = new Cuenta();
	}
	
	public SolicitudCtaCteTipoId getId() {
		return id;
	}
	public void setId(SolicitudCtaCteTipoId id) {
		this.id = id;
	}

	public Integer getIntTaraEstado() {
		return intTaraEstado;
	}


	public void setIntTaraEstado(Integer intTaraEstado) {
		this.intTaraEstado = intTaraEstado;
	}


	public Integer getIntParaTipoorigen() {
		return intParaTipoorigen;
	}


	public void setIntParaTipoorigen(Integer intParaTipoorigen) {
		this.intParaTipoorigen = intParaTipoorigen;
	}


	public Integer getIntParaEstadoanalisis() {
		return intParaEstadoanalisis;
	}


	public void setIntParaEstadoanalisis(Integer intParaEstadoanalisis) {
		this.intParaEstadoanalisis = intParaEstadoanalisis;
	}


	public String getStrScctObservacion() {
		return strScctObservacion;
	}


	public void setStrScctObservacion(String strScctObservacion) {
		this.strScctObservacion = strScctObservacion;
	}


	public Date getDtFechaDocumento() {
		return dtFechaDocumento;
	}


	public void setDtFechaDocumento(Date dtFechaDocumento) {
		this.dtFechaDocumento = dtFechaDocumento;
	}


	public Integer getIntMotivoSolicitud() {
		return intMotivoSolicitud;
	}


	public void setIntMotivoSolicitud(Integer intMotivoSolicitud) {
		this.intMotivoSolicitud = intMotivoSolicitud;
	}


	public Integer getIntEmpresaLibro() {
		return intEmpresaLibro;
	}


	public void setIntEmpresaLibro(Integer intEmpresaLibro) {
		this.intEmpresaLibro = intEmpresaLibro;
	}


	public Integer getIntContPeriodolibro() {
		return intContPeriodolibro;
	}


	public void setIntContPeriodolibro(Integer intContPeriodolibro) {
		this.intContPeriodolibro = intContPeriodolibro;
	}


	public Integer getIntCodigoLibro() {
		return intCodigoLibro;
	}


	public void setIntCodigoLibro(Integer intCodigoLibro) {
		this.intCodigoLibro = intCodigoLibro;
	}


	public Integer getIntEmpresaCuenta() {
		return intEmpresaCuenta;
	}


	public void setIntEmpresaCuenta(Integer intEmpresaCuenta) {
		this.intEmpresaCuenta = intEmpresaCuenta;
	}


	public Integer getIntCcobItemefectuado() {
		return intCcobItemefectuado;
	}


	public void setIntCcobItemefectuado(Integer intCcobItemefectuado) {
		this.intCcobItemefectuado = intCcobItemefectuado;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	public Movimiento getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}

	public List<GarantiaCreditoComp> getListaGarantiaCreditoComp() {
		return listaGarantiaCreditoComp;
	}

	public void setListaGarantiaCreditoComp(
			List<GarantiaCreditoComp> listaGarantiaCreditoComp) {
		this.listaGarantiaCreditoComp = listaGarantiaCreditoComp;
	}

	public List<CuentaConcepto> getListaCtaCptoExpCredito() {
		return listaCtaCptoExpCredito;
	}

	public void setListaCtaCptoExpCredito(
			List<CuentaConcepto> listaCtaCptoExpCredito) {
		this.listaCtaCptoExpCredito = listaCtaCptoExpCredito;
	}

	public String getStrDescripcion() {
		return strDescripcion;
	}

	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	public Integer getIntPersUsuario() {
		return intPersUsuario;
	}

	public void setIntPersUsuario(Integer intPersUsuario) {
		this.intPersUsuario = intPersUsuario;
	}

	public Integer getIntEmpresa() {
		return intEmpresa;
	}

	public void setIntEmpresa(Integer intEmpresa) {
		this.intEmpresa = intEmpresa;
	}

	public SocioEstructura getSocioEstructuraOrigen() {
		return SocioEstructuraOrigen;
	}

	public void setSocioEstructuraOrigen(SocioEstructura socioEstructuraOrigen) {
		SocioEstructuraOrigen = socioEstructuraOrigen;
	}

	
	public SocioEstructura getSocioEstructuraOrigenNueva() {
		return socioEstructuraOrigenNueva;
	}

	public void setSocioEstructuraOrigenNueva(
			SocioEstructura socioEstructuraOrigenNueva) {
		this.socioEstructuraOrigenNueva = socioEstructuraOrigenNueva;
	}

	public SocioEstructura getSocioEstructuraPrestamo() {
		return SocioEstructuraPrestamo;
	}

	public void setSocioEstructuraPrestamo(SocioEstructura socioEstructuraPrestamo) {
		SocioEstructuraPrestamo = socioEstructuraPrestamo;
	}

	public SocioEstructura getSocioEstructuraPrestamoNueva() {
		return socioEstructuraPrestamoNueva;
	}

	public void setSocioEstructuraPrestamoNueva(
			SocioEstructura socioEstructuraPrestamoNueva) {
		this.socioEstructuraPrestamoNueva = socioEstructuraPrestamoNueva;
	}

	public SocioComp getSocioComp() {
		return socioComp;
	}

	public void setSocioComp(SocioComp socioComp) {
		this.socioComp = socioComp;
	}

	public Integer getIntPeriodoEnvioPlanilla() {
		return intPeriodoEnvioPlanilla;
	}

	public void setIntPeriodoEnvioPlanilla(Integer intPeriodoEnvioPlanilla) {
		this.intPeriodoEnvioPlanilla = intPeriodoEnvioPlanilla;
	}

	public String getStrCasoEnvioPlanilla() {
		return strCasoEnvioPlanilla;
	}

	public void setStrCasoEnvioPlanilla(String strCasoEnvioPlanilla) {
		this.strCasoEnvioPlanilla = strCasoEnvioPlanilla;
	}

	public String getStrSiNoEnvioPlanilla() {
		return strSiNoEnvioPlanilla;
	}

	public void setStrSiNoEnvioPlanilla(String strSiNoEnvioPlanilla) {
		this.strSiNoEnvioPlanilla = strSiNoEnvioPlanilla;
	}

	

	public List<Expediente> getListaExpediente() {
		return listaExpediente;
	}

	public void setListaExpediente(List<Expediente> listaExpediente) {
		this.listaExpediente = listaExpediente;
	}

	public Integer getIntParaCondicionCuentaFinal() {
		return intParaCondicionCuentaFinal;
	}

	public void setIntParaCondicionCuentaFinal(Integer intParaCondicionCuentaFinal) {
		this.intParaCondicionCuentaFinal = intParaCondicionCuentaFinal;
	}

	public List<DescuentoIndebido> getListaDesctoIndebidoOrigen() {
		return listaDesctoIndebidoOrigen;
	}

	public void setListaDesctoIndebidoOrigen(
			List<DescuentoIndebido> listaDesctoIndebidoOrigen) {
		this.listaDesctoIndebidoOrigen = listaDesctoIndebidoOrigen;
	}

	public List<DescuentoIndebido> getListaDesctoIndebidoPrestamo() {
		return listaDesctoIndebidoPrestamo;
	}

	public void setListaDesctoIndebidoPrestamo(
			List<DescuentoIndebido> listaDesctoIndebidoPrestamo) {
		this.listaDesctoIndebidoPrestamo = listaDesctoIndebidoPrestamo;
	}

	public Integer getIdSucursalUsuario() {
		return idSucursalUsuario;
	}

	public void setIdSucursalUsuario(Integer idSucursalUsuario) {
		this.idSucursalUsuario = idSucursalUsuario;
	}

	public Integer getIdSubSucursalUsuario() {
		return idSubSucursalUsuario;
	}

	public void setIdSubSucursalUsuario(Integer idSubSucursalUsuario) {
		this.idSubSucursalUsuario = idSubSucursalUsuario;
	}

	public Integer getRadioOpcionTransferencias() {
		return radioOpcionTransferencias;
	}

	public void setRadioOpcionTransferencias(Integer radioOpcionTransferencias) {
		this.radioOpcionTransferencias = radioOpcionTransferencias;
	}

	public SocioComp getSocioCompGarante() {
		return socioCompGarante;
	}

	public void setSocioCompGarante(SocioComp socioCompGarante) {
		this.socioCompGarante = socioCompGarante;
	}

	public List<Transferencia> getListaTransferencias() {
		return listaTransferencias;
	}

	public void setListaTransferencias(List<Transferencia> listaTransferencias) {
		this.listaTransferencias = listaTransferencias;
	}
    
	
	
	

	
	
}
