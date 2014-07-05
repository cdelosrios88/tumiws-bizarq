package pe.com.tumi.tesoreria.ingreso.domain;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ReciboManual extends TumiDomain{

	private ReciboManualId	id;
	private Integer intParaTipoDocumentoGeneral;
	private Integer intSucuIdSucursal;
	private Integer intSudeIdSubsucursal;
	private Integer intSerieRecibo;
	private Integer intNumeroInicio;
	private Integer intNumeroFinal;
	private Integer intParaEstado;
	private Integer intParaEstadoCierre;
	
	private List<ReciboManualDetalle>	listaReciboManualDetalle;
	private ReciboManualDetalle reciboManualDetalleUltimo;
	private Integer	intNumeroActual;
	private GestorCobranza gestorCobranza;
	
	public ReciboManual(){
		id = new ReciboManualId();
		listaReciboManualDetalle = new ArrayList<ReciboManualDetalle>();
	}
	
	public ReciboManualId getId() {
		return id;
	}
	public void setId(ReciboManualId id) {
		this.id = id;
	}
	public Integer getIntParaTipoDocumentoGeneral() {
		return intParaTipoDocumentoGeneral;
	}
	public void setIntParaTipoDocumentoGeneral(Integer intParaTipoDocumentoGeneral) {
		this.intParaTipoDocumentoGeneral = intParaTipoDocumentoGeneral;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSubsucursal() {
		return intSudeIdSubsucursal;
	}
	public void setIntSudeIdSubsucursal(Integer intSudeIdSubsucursal) {
		this.intSudeIdSubsucursal = intSudeIdSubsucursal;
	}
	public Integer getIntSerieRecibo() {
		return intSerieRecibo;
	}
	public void setIntSerieRecibo(Integer intSerieRecibo) {
		this.intSerieRecibo = intSerieRecibo;
	}
	public Integer getIntNumeroInicio() {
		return intNumeroInicio;
	}
	public void setIntNumeroInicio(Integer intNumeroInicio) {
		this.intNumeroInicio = intNumeroInicio;
	}
	public Integer getIntNumeroFinal() {
		return intNumeroFinal;
	}
	public void setIntNumeroFinal(Integer intNumeroFinal) {
		this.intNumeroFinal = intNumeroFinal;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntParaEstadoCierre() {
		return intParaEstadoCierre;
	}
	public void setIntParaEstadoCierre(Integer intParaEstadoCierre) {
		this.intParaEstadoCierre = intParaEstadoCierre;
	}
	public List<ReciboManualDetalle> getListaReciboManualDetalle() {
		return listaReciboManualDetalle;
	}
	public void setListaReciboManualDetalle(List<ReciboManualDetalle> listaReciboManualDetalle) {
		this.listaReciboManualDetalle = listaReciboManualDetalle;
	}
	public ReciboManualDetalle getReciboManualDetalleUltimo() {
		return reciboManualDetalleUltimo;
	}
	public void setReciboManualDetalleUltimo(ReciboManualDetalle reciboManualDetalleUltimo) {
		this.reciboManualDetalleUltimo = reciboManualDetalleUltimo;
	}
	public Integer getIntNumeroActual() {
		return intNumeroActual;
	}
	public void setIntNumeroActual(Integer intNumeroActual) {
		this.intNumeroActual = intNumeroActual;
	}
	public GestorCobranza getGestorCobranza() {
		return gestorCobranza;
	}
	public void setGestorCobranza(GestorCobranza gestorCobranza) {
		this.gestorCobranza = gestorCobranza;
	}
	@Override
	public String toString() {
		return "ReciboManual [id=" + id + ", intParaTipoDocumentoGeneral="
				+ intParaTipoDocumentoGeneral + ", intSucuIdSucursal="
				+ intSucuIdSucursal + ", intSudeIdSubsucursal="
				+ intSudeIdSubsucursal + ", intSerieRecibo=" + intSerieRecibo
				+ ", intNumeroInicio=" + intNumeroInicio + ", intNumeroFinal="
				+ intNumeroFinal + ", intParaEstado=" + intParaEstado
				+ ", intParaEstadoCierre=" + intParaEstadoCierre
				+ ", listaReciboManualDetalle=" + listaReciboManualDetalle
				+ "]";
	}
}