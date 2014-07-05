package pe.com.tumi.credito.socio.convenio.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;

public class Adenda extends TumiDomain {

	private AdendaId id;
	private Integer intPersEmpresaPk;
	private Integer intSeguSucursalPk;
	private Date dtInicio;
	private Date dtCese;
	private String strDtInicio;
	private String strDtCese;
	private Integer intOpcionFiltroCredito;
	private Date dtSuscripcion;
	private Integer intParaTipoRetencionCod;
	private BigDecimal bdRetencion;
	private Boolean boCartaAutorizacion;
	private Integer intCartaAutorizacion;
	private Boolean boDonacion;
	private Integer intDonacion;
	private Date dtFechaHojaPendiente;
	private String strFechaRegistro;
	private Integer intRenovacion;
	private Integer intParaEstadoHojaPlan;
	private Integer intParaEstadoValidacion;
	private Timestamp tsFechaHojaCulminado;
	private Timestamp tsFechaConvenio;
	private Integer intParaEstadoConvenioCod;
	
	private Integer intParaEstadoCod;
	
	private List<Adjunto> listaAdjunto;
	private List<Perfil> listaPerfil;
	private List<ConvenioEstructuraDetalle> listaConvenioDetalle;
	private List<ConvenioEstructuraDetalleComp> listaConvenioDetalleComp;
	private List<Poblacion> listaPoblacion;
	private List<Adenda> listaAdenda;
	private List<Competencia> listaCompetencia;
	
	//Parte del convenio
	private List<RetencionPlanilla> listaRetencionPlla;
	private List<DonacionRegalia> listaDonacion;
	private List<Firmante> listaFirmante;
	private List<AdendaCaptacion> listaAdendaCaptacionAportacion;
	private List<AdendaCaptacion> listaAdendaCaptacionFondoSepelio;
	private List<AdendaCaptacion> listaAdendaCaptacionFondoRetiro;
	private List<AdendaCaptacion> listaAdendaCaptacionMantCuenta;
	private List<AdendaCredito> listaAdendaCreditos;
	
	//Agregado por cdelosrios, 08/12/2013
	private Archivo	archivoDocumentoCartaPresent;
	private Archivo	archivoDocumentoConvSugerido;
	private Archivo	archivoDocumentoAdendaSugerida;
	//Fin agregado por cdelosrios, 08/12/2013
	
	public AdendaId getId() {
		return id;
	}
	public void setId(AdendaId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntSeguSucursalPk() {
		return intSeguSucursalPk;
	}
	public void setIntSeguSucursalPk(Integer intSeguSucursalPk) {
		this.intSeguSucursalPk = intSeguSucursalPk;
	}
	public Date getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}
	public Date getDtCese() {
		return dtCese;
	}
	public void setDtCese(Date dtCese) {
		this.dtCese = dtCese;
	}
	public String getStrDtInicio() {
		return strDtInicio;
	}
	public void setStrDtInicio(String strDtInicio) {
		this.strDtInicio = strDtInicio;
	}
	public String getStrDtCese() {
		return strDtCese;
	}
	public void setStrDtCese(String strDtCese) {
		this.strDtCese = strDtCese;
	}
	public Integer getIntOpcionFiltroCredito() {
		return intOpcionFiltroCredito;
	}
	public void setIntOpcionFiltroCredito(Integer intOpcionFiltroCredito) {
		this.intOpcionFiltroCredito = intOpcionFiltroCredito;
	}
	public Date getDtSuscripcion() {
		return dtSuscripcion;
	}
	public void setDtSuscripcion(Date dtSuscripcion) {
		this.dtSuscripcion = dtSuscripcion;
	}
	public Integer getIntParaTipoRetencionCod() {
		return intParaTipoRetencionCod;
	}
	public void setIntParaTipoRetencionCod(Integer intParaTipoRetencionCod) {
		this.intParaTipoRetencionCod = intParaTipoRetencionCod;
	}
	public BigDecimal getBdRetencion() {
		return bdRetencion;
	}
	public void setBdRetencion(BigDecimal bdRetencion) {
		this.bdRetencion = bdRetencion;
	}
	public Boolean getBoCartaAutorizacion() {
		return boCartaAutorizacion;
	}
	public void setBoCartaAutorizacion(Boolean boCartaAutorizacion) {
		this.boCartaAutorizacion = boCartaAutorizacion;
	}
	public Integer getIntCartaAutorizacion() {
		return intCartaAutorizacion;
	}
	public void setIntCartaAutorizacion(Integer intCartaAutorizacion) {
		this.intCartaAutorizacion = intCartaAutorizacion;
	}
	public Boolean getBoDonacion() {
		return boDonacion;
	}
	public void setBoDonacion(Boolean boDonacion) {
		this.boDonacion = boDonacion;
	}
	public Integer getIntDonacion() {
		return intDonacion;
	}
	public void setIntDonacion(Integer intDonacion) {
		this.intDonacion = intDonacion;
	}
	public Date getDtFechaHojaPendiente() {
		return dtFechaHojaPendiente;
	}
	public void setDtFechaHojaPendiente(Date dtFechaHojaPendiente) {
		this.dtFechaHojaPendiente = dtFechaHojaPendiente;
	}
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}
	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}
	public Integer getIntRenovacion() {
		return intRenovacion;
	}
	public void setIntRenovacion(Integer intRenovacion) {
		this.intRenovacion = intRenovacion;
	}
	public Integer getIntParaEstadoHojaPlan() {
		return intParaEstadoHojaPlan;
	}
	public void setIntParaEstadoHojaPlan(Integer intParaEstadoHojaPlan) {
		this.intParaEstadoHojaPlan = intParaEstadoHojaPlan;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public List<Adjunto> getListaAdjunto() {
		return listaAdjunto;
	}
	public void setListaAdjunto(List<Adjunto> listaAdjunto) {
		this.listaAdjunto = listaAdjunto;
	}
	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}
	public List<ConvenioEstructuraDetalle> getListaConvenioDetalle() {
		return listaConvenioDetalle;
	}
	public void setListaConvenioDetalle(
			List<ConvenioEstructuraDetalle> listaConvenioDetalle) {
		this.listaConvenioDetalle = listaConvenioDetalle;
	}
	public List<ConvenioEstructuraDetalleComp> getListaConvenioDetalleComp() {
		return listaConvenioDetalleComp;
	}
	public void setListaConvenioDetalleComp(
			List<ConvenioEstructuraDetalleComp> listaConvenioDetalleComp) {
		this.listaConvenioDetalleComp = listaConvenioDetalleComp;
	}
	public List<Poblacion> getListaPoblacion() {
		return listaPoblacion;
	}
	public void setListaPoblacion(List<Poblacion> listaPoblacion) {
		this.listaPoblacion = listaPoblacion;
	}
	public List<Adenda> getListaAdenda() {
		return listaAdenda;
	}
	public void setListaAdenda(List<Adenda> listaAdenda) {
		this.listaAdenda = listaAdenda;
	}
	public List<Competencia> getListaCompetencia() {
		return listaCompetencia;
	}
	public void setListaCompetencia(List<Competencia> listaCompetencia) {
		this.listaCompetencia = listaCompetencia;
	}
	public Integer getIntParaEstadoValidacion() {
		return intParaEstadoValidacion;
	}
	public void setIntParaEstadoValidacion(Integer intParaEstadoValidacion) {
		this.intParaEstadoValidacion = intParaEstadoValidacion;
	}
	public Timestamp getTsFechaHojaCulminado() {
		return tsFechaHojaCulminado;
	}
	public void setTsFechaHojaCulminado(Timestamp tsFechaHojaCulminado) {
		this.tsFechaHojaCulminado = tsFechaHojaCulminado;
	}
	public Timestamp getTsFechaConvenio() {
		return tsFechaConvenio;
	}
	public void setTsFechaConvenio(Timestamp tsFechaConvenio) {
		this.tsFechaConvenio = tsFechaConvenio;
	}
	public Integer getIntParaEstadoConvenioCod() {
		return intParaEstadoConvenioCod;
	}
	public void setIntParaEstadoConvenioCod(Integer intParaEstadoConvenioCod) {
		this.intParaEstadoConvenioCod = intParaEstadoConvenioCod;
	}
	public List<RetencionPlanilla> getListaRetencionPlla() {
		return listaRetencionPlla;
	}
	public void setListaRetencionPlla(List<RetencionPlanilla> listaRetencionPlla) {
		this.listaRetencionPlla = listaRetencionPlla;
	}
	public List<DonacionRegalia> getListaDonacion() {
		return listaDonacion;
	}
	public void setListaDonacion(List<DonacionRegalia> listaDonacion) {
		this.listaDonacion = listaDonacion;
	}
	public List<Firmante> getListaFirmante() {
		return listaFirmante;
	}
	public void setListaFirmante(List<Firmante> listaFirmante) {
		this.listaFirmante = listaFirmante;
	}
	public List<AdendaCaptacion> getListaAdendaCaptacionAportacion() {
		return listaAdendaCaptacionAportacion;
	}
	public void setListaAdendaCaptacionAportacion(
			List<AdendaCaptacion> listaAdendaCaptacionAportacion) {
		this.listaAdendaCaptacionAportacion = listaAdendaCaptacionAportacion;
	}
	public List<AdendaCaptacion> getListaAdendaCaptacionFondoSepelio() {
		return listaAdendaCaptacionFondoSepelio;
	}
	public void setListaAdendaCaptacionFondoSepelio(
			List<AdendaCaptacion> listaAdendaCaptacionFondoSepelio) {
		this.listaAdendaCaptacionFondoSepelio = listaAdendaCaptacionFondoSepelio;
	}
	public List<AdendaCaptacion> getListaAdendaCaptacionFondoRetiro() {
		return listaAdendaCaptacionFondoRetiro;
	}
	public void setListaAdendaCaptacionFondoRetiro(
			List<AdendaCaptacion> listaAdendaCaptacionFondoRetiro) {
		this.listaAdendaCaptacionFondoRetiro = listaAdendaCaptacionFondoRetiro;
	}
	public List<AdendaCaptacion> getListaAdendaCaptacionMantCuenta() {
		return listaAdendaCaptacionMantCuenta;
	}
	public void setListaAdendaCaptacionMantCuenta(
			List<AdendaCaptacion> listaAdendaCaptacionMantCuenta) {
		this.listaAdendaCaptacionMantCuenta = listaAdendaCaptacionMantCuenta;
	}
	public List<AdendaCredito> getListaAdendaCreditos() {
		return listaAdendaCreditos;
	}
	public void setListaAdendaCreditos(List<AdendaCredito> listaAdendaCreditos) {
		this.listaAdendaCreditos = listaAdendaCreditos;
	}
	public Archivo getArchivoDocumentoCartaPresent() {
		return archivoDocumentoCartaPresent;
	}
	public void setArchivoDocumentoCartaPresent(Archivo archivoDocumentoCartaPresent) {
		this.archivoDocumentoCartaPresent = archivoDocumentoCartaPresent;
	}
	public Archivo getArchivoDocumentoConvSugerido() {
		return archivoDocumentoConvSugerido;
	}
	public void setArchivoDocumentoConvSugerido(Archivo archivoDocumentoConvSugerido) {
		this.archivoDocumentoConvSugerido = archivoDocumentoConvSugerido;
	}
	public Archivo getArchivoDocumentoAdendaSugerida() {
		return archivoDocumentoAdendaSugerida;
	}
	public void setArchivoDocumentoAdendaSugerida(
			Archivo archivoDocumentoAdendaSugerida) {
		this.archivoDocumentoAdendaSugerida = archivoDocumentoAdendaSugerida;
	}	
}