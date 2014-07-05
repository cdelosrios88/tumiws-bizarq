package pe.com.tumi.riesgo.cartera.domain.composite;

import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

@SuppressWarnings("serial")
public class CarteraCreditoManual extends TumiDomain {

	private Integer intPeriodo;
	private Integer intMes;
	private Integer intAnio;
	private Integer intTipoCartera;
	private Integer intTipoCobranza;
	private Integer intEstadoCierre;
	private Double douMntProvCredito;
	private Double douMntProvProciclica;
	private Date dtFechaRegistro;

	public Integer getIntPeriodo() {
		return intPeriodo;
	}

	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
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

	public Integer getIntEstadoCierre() {
		return intEstadoCierre;
	}

	public void setIntEstadoCierre(Integer intEstadoCierre) {
		this.intEstadoCierre = intEstadoCierre;
	}

	public Double getDouMntProvCredito() {
		return douMntProvCredito;
	}

	public void setDouMntProvCredito(Double douMntProvCredito) {
		this.douMntProvCredito = douMntProvCredito;
	}

	public Double getDouMntProvProciclica() {
		return douMntProvProciclica;
	}

	public void setDouMntProvProciclica(Double douMntProvProciclica) {
		this.douMntProvProciclica = douMntProvProciclica;
	}

	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}

	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}

	public void setIntTipoCobranza(Integer intTipoCobranza) {
		this.intTipoCobranza = intTipoCobranza;
	}

	public Integer getIntTipoCobranza() {
		return intTipoCobranza;
	}

	public void setIntTipoCartera(Integer intTipoCartera) {
		this.intTipoCartera = intTipoCartera;
	}

	public Integer getIntTipoCartera() {
		return intTipoCartera;
	}

}
