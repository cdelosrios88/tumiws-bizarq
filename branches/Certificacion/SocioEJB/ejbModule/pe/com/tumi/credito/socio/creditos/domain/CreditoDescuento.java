package pe.com.tumi.credito.socio.creditos.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoDescuento extends TumiDomain {
	private CreditoDescuentoId id;
	private String strConcepto;
	private BigDecimal bdMonto;
	private BigDecimal bdPorcentaje;
	private Date dtFechaIni;
	private Date dtFechaFin;
	private String strDtFechaIni;
	private String strDtFechaFin;
	private Integer intParaEstadoCod;
	private Date dtFechaEliminacion;
	
	private List<CreditoDescuentoCaptacion> listaDescuento;
	
	//Getters y Setters
	public CreditoDescuentoId getId() {
		return id;
	}
	public void setId(CreditoDescuentoId id) {
		this.id = id;
	}
	public String getStrConcepto() {
		return strConcepto;
	}
	public void setStrConcepto(String strConcepto) {
		this.strConcepto = strConcepto;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public BigDecimal getBdPorcentaje() {
		return bdPorcentaje;
	}
	public void setBdPorcentaje(BigDecimal bdPorcentaje) {
		this.bdPorcentaje = bdPorcentaje;
	}
	public Date getDtFechaIni() {
		return dtFechaIni;
	}
	public void setDtFechaIni(Date dtFechaIni) {
		this.dtFechaIni = dtFechaIni;
	}
	public Date getDtFechaFin() {
		return dtFechaFin;
	}
	public void setDtFechaFin(Date dtFechaFin) {
		this.dtFechaFin = dtFechaFin;
	}
	public String getStrDtFechaIni() {
		return strDtFechaIni;
	}
	public void setStrDtFechaIni(String strDtFechaIni) {
		this.strDtFechaIni = strDtFechaIni;
	}
	public String getStrDtFechaFin() {
		return strDtFechaFin;
	}
	public void setStrDtFechaFin(String strDtFechaFin) {
		this.strDtFechaFin = strDtFechaFin;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Date getDtFechaEliminacion() {
		return dtFechaEliminacion;
	}
	public void setDtFechaEliminacion(Date dtFechaEliminacion) {
		this.dtFechaEliminacion = dtFechaEliminacion;
	}
	public List<CreditoDescuentoCaptacion> getListaDescuento() {
		return listaDescuento;
	}
	public void setListaDescuento(List<CreditoDescuentoCaptacion> listaDescuento) {
		this.listaDescuento = listaDescuento;
	}
}
