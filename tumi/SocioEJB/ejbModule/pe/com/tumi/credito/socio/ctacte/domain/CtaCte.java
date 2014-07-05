package pe.com.tumi.credito.socio.ctacte.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CtaCte extends TumiDomain {

	private CtaCteId id;
	private String strDescripcion;
	private Date dtInicio;
	private Date dtFin;
	private Integer intParaTipopersonaCod;
	private Integer intParaRolPk;
	private Integer intTipohabil;
	private Integer intParaCondicionLaboralCod;
	private Integer intParaMonedaCod;
	private Integer intParaTipoConfiguracionCod;
	private BigDecimal bdValorConfiguracion;
	private Integer intParaAplicacionCod;
	private Integer intParaTipoCalculoInteresCod;
	private Integer intParaTipoChequeraCod;
	private Integer intTitulares;
	private Integer intEmisionesSinFondo;
	private Integer intInhabilitaciones;
	private Integer intTipoInhabilitaciones;
	private Date dtFechaRegistro;
	private Integer intParaEstadoSolicitudCod;
	private Integer intParaEstadoCod;
	private List<Linea> listaLinea;
	private List<Rango> listaRango;
	private List<CtaCteCondicion> listaCtaCteCondicion;
	
	public CtaCteId getId() {
		return id;
	}
	public void setId(CtaCteId id) {
		this.id = id;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Date getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}
	public Date getDtFin() {
		return dtFin;
	}
	public void setDtFin(Date dtFin) {
		this.dtFin = dtFin;
	}
	public Integer getIntParaTipopersonaCod() {
		return intParaTipopersonaCod;
	}
	public void setIntParaTipopersonaCod(Integer intParaTipopersonaCod) {
		this.intParaTipopersonaCod = intParaTipopersonaCod;
	}
	public Integer getIntParaRolPk() {
		return intParaRolPk;
	}
	public void setIntParaRolPk(Integer intParaRolPk) {
		this.intParaRolPk = intParaRolPk;
	}
	public Integer getIntTipohabil() {
		return intTipohabil;
	}
	public void setIntTipohabil(Integer intTipohabil) {
		this.intTipohabil = intTipohabil;
	}
	public Integer getIntParaCondicionLaboralCod() {
		return intParaCondicionLaboralCod;
	}
	public void setIntParaCondicionLaboralCod(Integer intParaCondicionLaboralCod) {
		this.intParaCondicionLaboralCod = intParaCondicionLaboralCod;
	}
	public Integer getIntParaMonedaCod() {
		return intParaMonedaCod;
	}
	public void setIntParaMonedaCod(Integer intParaMonedaCod) {
		this.intParaMonedaCod = intParaMonedaCod;
	}
	public Integer getIntParaTipoConfiguracionCod() {
		return intParaTipoConfiguracionCod;
	}
	public void setIntParaTipoConfiguracionCod(Integer intParaTipoConfiguracionCod) {
		this.intParaTipoConfiguracionCod = intParaTipoConfiguracionCod;
	}
	public BigDecimal getBdValorConfiguracion() {
		return bdValorConfiguracion;
	}
	public void setBdValorConfiguracion(BigDecimal bdValorConfiguracion) {
		this.bdValorConfiguracion = bdValorConfiguracion;
	}
	public Integer getIntParaAplicacionCod() {
		return intParaAplicacionCod;
	}
	public void setIntParaAplicacionCod(Integer intParaAplicacionCod) {
		this.intParaAplicacionCod = intParaAplicacionCod;
	}
	public Integer getIntParaTipoCalculoInteresCod() {
		return intParaTipoCalculoInteresCod;
	}
	public void setIntParaTipoCalculoInteresCod(Integer intParaTipoCalculoInteresCod) {
		this.intParaTipoCalculoInteresCod = intParaTipoCalculoInteresCod;
	}
	public Integer getIntParaTipoChequeraCod() {
		return intParaTipoChequeraCod;
	}
	public void setIntParaTipoChequeraCod(Integer intParaTipoChequeraCod) {
		this.intParaTipoChequeraCod = intParaTipoChequeraCod;
	}
	public Integer getIntTitulares() {
		return intTitulares;
	}
	public void setIntTitulares(Integer intTitulares) {
		this.intTitulares = intTitulares;
	}
	public Integer getIntEmisionesSinFondo() {
		return intEmisionesSinFondo;
	}
	public void setIntEmisionesSinFondo(Integer intEmisionesSinFondo) {
		this.intEmisionesSinFondo = intEmisionesSinFondo;
	}
	public Integer getIntInhabilitaciones() {
		return intInhabilitaciones;
	}
	public void setIntInhabilitaciones(Integer intInhabilitaciones) {
		this.intInhabilitaciones = intInhabilitaciones;
	}
	public Integer getIntTipoInhabilitaciones() {
		return intTipoInhabilitaciones;
	}
	public void setIntTipoInhabilitaciones(Integer intTipoInhabilitaciones) {
		this.intTipoInhabilitaciones = intTipoInhabilitaciones;
	}
	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public Integer getIntParaEstadoSolicitudCod() {
		return intParaEstadoSolicitudCod;
	}
	public void setIntParaEstadoSolicitudCod(Integer intParaEstadoSolicitudCod) {
		this.intParaEstadoSolicitudCod = intParaEstadoSolicitudCod;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public List<Linea> getListaLinea() {
		return listaLinea;
	}
	public void setListaLinea(List<Linea> listaLinea) {
		this.listaLinea = listaLinea;
	}
	public List<Rango> getListaRango() {
		return listaRango;
	}
	public void setListaRango(List<Rango> listaRango) {
		this.listaRango = listaRango;
	}
	public List<CtaCteCondicion> getListaCtaCteCondicion() {
		return listaCtaCteCondicion;
	}
	public void setListaCtaCteCondicion(List<CtaCteCondicion> listaCtaCteCondicion) {
		this.listaCtaCteCondicion = listaCtaCteCondicion;
	}
	
}
