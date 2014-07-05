package pe.com.tumi.credito.socio.creditos.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoTipoGarantia extends TumiDomain {
	private CreditoTipoGarantiaId 	id;
	private Integer 				intParaTipoOpcionCod;
	private Integer 				intParaTipoObligatorioCod;
	private Integer 				intEdadLimite;
	private Integer 				intTiempoMinimoContrato;
	private Integer 				intParaTipoTiempoContratoCod;
	private Integer 				intParaTipoDsctoJudicialCod;
	private BigDecimal 				bdPorcentajeAporteMinimo;
	private Integer 				intNumeroMaximoGarantia;
	private Integer 				intParaEstadoCod;
	
	//Listas del Tipo de Garantía
	private List<CondicionSocioTipoGarantia> listaCondicionSocio;
	private List<CondicionHabilTipoGarantia> listaTipoCondicion;
	private List<SituacionLaboralTipoGarantia> listaSituacionLaboral;
	private List<CondicionLaboralTipoGarantia> listaCondicionLaboral;
	
	//Getters y Setters
	public CreditoTipoGarantiaId getId() {
		return id;
	}
	public void setId(CreditoTipoGarantiaId id) {
		this.id = id;
	}
	public Integer getIntParaTipoOpcionCod() {
		return intParaTipoOpcionCod;
	}
	public void setIntParaTipoOpcionCod(Integer intParaTipoOpcionCod) {
		this.intParaTipoOpcionCod = intParaTipoOpcionCod;
	}
	public Integer getIntParaTipoObligatorioCod() {
		return intParaTipoObligatorioCod;
	}
	public void setIntParaTipoObligatorioCod(Integer intParaTipoObligatorioCod) {
		this.intParaTipoObligatorioCod = intParaTipoObligatorioCod;
	}
	public Integer getIntEdadLimite() {
		return intEdadLimite;
	}
	public void setIntEdadLimite(Integer intEdadLimite) {
		this.intEdadLimite = intEdadLimite;
	}
	public Integer getIntTiempoMinimoContrato() {
		return intTiempoMinimoContrato;
	}
	public void setIntTiempoMinimoContrato(Integer intTiempoMinimoContrato) {
		this.intTiempoMinimoContrato = intTiempoMinimoContrato;
	}
	public Integer getIntParaTipoTiempoContratoCod() {
		return intParaTipoTiempoContratoCod;
	}
	public void setIntParaTipoTiempoContratoCod(Integer intParaTipoTiempoContratoCod) {
		this.intParaTipoTiempoContratoCod = intParaTipoTiempoContratoCod;
	}
	public Integer getIntParaTipoDsctoJudicialCod() {
		return intParaTipoDsctoJudicialCod;
	}
	public void setIntParaTipoDsctoJudicialCod(Integer intParaTipoDsctoJudicialCod) {
		this.intParaTipoDsctoJudicialCod = intParaTipoDsctoJudicialCod;
	}
	public BigDecimal getBdPorcentajeAporteMinimo() {
		return bdPorcentajeAporteMinimo;
	}
	public void setBdPorcentajeAporteMinimo(BigDecimal bdPorcentajeAporteMinimo) {
		this.bdPorcentajeAporteMinimo = bdPorcentajeAporteMinimo;
	}
	public Integer getIntNumeroMaximoGarantia() {
		return intNumeroMaximoGarantia;
	}
	public void setIntNumeroMaximoGarantia(Integer intNumeroMaximoGarantia) {
		this.intNumeroMaximoGarantia = intNumeroMaximoGarantia;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public List<CondicionSocioTipoGarantia> getListaCondicionSocio() {
		return listaCondicionSocio;
	}
	public void setListaCondicionSocio(
			List<CondicionSocioTipoGarantia> listaCondicionSocio) {
		this.listaCondicionSocio = listaCondicionSocio;
	}
	public List<CondicionHabilTipoGarantia> getListaTipoCondicion() {
		return listaTipoCondicion;
	}
	public void setListaTipoCondicion(
			List<CondicionHabilTipoGarantia> listaTipoCondicion) {
		this.listaTipoCondicion = listaTipoCondicion;
	}
	public List<SituacionLaboralTipoGarantia> getListaSituacionLaboral() {
		return listaSituacionLaboral;
	}
	public void setListaSituacionLaboral(
			List<SituacionLaboralTipoGarantia> listaSituacionLaboral) {
		this.listaSituacionLaboral = listaSituacionLaboral;
	}
	public List<CondicionLaboralTipoGarantia> getListaCondicionLaboral() {
		return listaCondicionLaboral;
	}
	public void setListaCondicionLaboral(
			List<CondicionLaboralTipoGarantia> listaCondicionLaboral) {
		this.listaCondicionLaboral = listaCondicionLaboral;
	}
}
