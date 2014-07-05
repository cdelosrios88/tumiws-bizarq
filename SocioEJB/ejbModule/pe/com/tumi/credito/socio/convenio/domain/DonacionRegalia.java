package pe.com.tumi.credito.socio.convenio.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DonacionRegalia extends TumiDomain {
	private DonacionRegaliaId id;
	private Integer intParaTipoDonacionCod;
	private String	strDescripcion;
	private Integer intParaTipoMonedaCod;
	private BigDecimal bdMonto;
	private Integer intDctoCompra;
	private Integer intParaEstadoPagoCod;
	private Integer intParaEstadoCod;
	private Integer intPersonaRegistraPk;
	private Timestamp tsFechaRegistra;
	private Integer intPersonaEliminaPk;
	private Timestamp tsFechaEliminacion;
	
	public DonacionRegaliaId getId() {
		return id;
	}
	public void setId(DonacionRegaliaId id) {
		this.id = id;
	}
	public Integer getIntParaTipoDonacionCod() {
		return intParaTipoDonacionCod;
	}
	public void setIntParaTipoDonacionCod(Integer intParaTipoDonacionCod) {
		this.intParaTipoDonacionCod = intParaTipoDonacionCod;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntParaTipoMonedaCod() {
		return intParaTipoMonedaCod;
	}
	public void setIntParaTipoMonedaCod(Integer intParaTipoMonedaCod) {
		this.intParaTipoMonedaCod = intParaTipoMonedaCod;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public Integer getIntDctoCompra() {
		return intDctoCompra;
	}
	public void setIntDctoCompra(Integer intDctoCompra) {
		this.intDctoCompra = intDctoCompra;
	}
	public Integer getIntParaEstadoPagoCod() {
		return intParaEstadoPagoCod;
	}
	public void setIntParaEstadoPagoCod(Integer intParaEstadoPagoCod) {
		this.intParaEstadoPagoCod = intParaEstadoPagoCod;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntPersonaRegistraPk() {
		return intPersonaRegistraPk;
	}
	public void setIntPersonaRegistraPk(Integer intPersonaRegistraPk) {
		this.intPersonaRegistraPk = intPersonaRegistraPk;
	}
	public Timestamp getTsFechaRegistra() {
		return tsFechaRegistra;
	}
	public void setTsFechaRegistra(Timestamp tsFechaRegistra) {
		this.tsFechaRegistra = tsFechaRegistra;
	}
	public Integer getIntPersonaEliminaPk() {
		return intPersonaEliminaPk;
	}
	public void setIntPersonaEliminaPk(Integer intPersonaEliminaPk) {
		this.intPersonaEliminaPk = intPersonaEliminaPk;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
}
