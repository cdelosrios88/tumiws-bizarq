package pe.com.tumi.riesgo.cartera.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CarteraCredito extends TumiDomain{

    private CarteraCreditoId id;
    private Integer	intParaDocumentoGeneral;
	private Date	tsCacrFechaSaldos;
	private Integer	intCacrMontoMínimoCartera;
	private String	strCacCodigoSbs;
	private Integer	intCrieItemCartera;
	private Integer	intParaEstado;
	private Integer	intParaEstadocierre;
	private Integer	intPersEmpresaresgitro;
	private Integer	intPersPersonaregistro;
	private Date	tsCacrFecharegistro	;
	private Integer	intPersEmpresaelimina;
	private Integer	intPersPersonaelimina;
	private Date	tsCacrFechaelimina;
	private Integer	intPersEmpresalibro;
	private Integer	intContPeriodolibro;
	private Integer	intContCodigolibro;
	private Integer	intPersEmpresalibroan;
	private Integer	intContPeriodolibroan;
	private Integer	intContCodigolibroan;
	
	private List<CarteraCreditoDetalle>  listaCarteraCreDetalle;
	
	public CarteraCreditoId getId() {
		return id;
	}
	public void setId(CarteraCreditoId id) {
		this.id = id;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Date getTsCacrFechaSaldos() {
		return tsCacrFechaSaldos;
	}
	public void setTsCacrFechaSaldos(Date tsCacrFechaSaldos) {
		this.tsCacrFechaSaldos = tsCacrFechaSaldos;
	}
	public Integer getIntCacrMontoMínimoCartera() {
		return intCacrMontoMínimoCartera;
	}
	public void setIntCacrMontoMínimoCartera(Integer intCacrMontoMínimoCartera) {
		this.intCacrMontoMínimoCartera = intCacrMontoMínimoCartera;
	}
	public String getStrCacCodigoSbs() {
		return strCacCodigoSbs;
	}
	public void setStrCacCodigoSbs(String strCacCodigoSbs) {
		this.strCacCodigoSbs = strCacCodigoSbs;
	}
	public Integer getIntCrieItemCartera() {
		return intCrieItemCartera;
	}
	public void setIntCrieItemCartera(Integer intCrieItemCartera) {
		this.intCrieItemCartera = intCrieItemCartera;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntParaEstadocierre() {
		return intParaEstadocierre;
	}
	public void setIntParaEstadocierre(Integer intParaEstadocierre) {
		this.intParaEstadocierre = intParaEstadocierre;
	}
	public Integer getIntPersEmpresaresgitro() {
		return intPersEmpresaresgitro;
	}
	public void setIntPersEmpresaresgitro(Integer intPersEmpresaresgitro) {
		this.intPersEmpresaresgitro = intPersEmpresaresgitro;
	}
	public Integer getIntPersPersonaregistro() {
		return intPersPersonaregistro;
	}
	public void setIntPersPersonaregistro(Integer intPersPersonaregistro) {
		this.intPersPersonaregistro = intPersPersonaregistro;
	}
	public Date getTsCacrFecharegistro() {
		return tsCacrFecharegistro;
	}
	public void setTsCacrFecharegistro(Date tsCacrFecharegistro) {
		this.tsCacrFecharegistro = tsCacrFecharegistro;
	}
	public Integer getIntPersEmpresaelimina() {
		return intPersEmpresaelimina;
	}
	public void setIntPersEmpresaelimina(Integer intPersEmpresaelimina) {
		this.intPersEmpresaelimina = intPersEmpresaelimina;
	}
	public Integer getIntPersPersonaelimina() {
		return intPersPersonaelimina;
	}
	public void setIntPersPersonaelimina(Integer intPersPersonaelimina) {
		this.intPersPersonaelimina = intPersPersonaelimina;
	}
	
	public Date getTsCacrFechaelimina() {
		return tsCacrFechaelimina;
	}
	public void setTsCacrFechaelimina(Date tsCacrFechaelimina) {
		this.tsCacrFechaelimina = tsCacrFechaelimina;
	}
	public Integer getIntPersEmpresalibro() {
		return intPersEmpresalibro;
	}
	public void setIntPersEmpresalibro(Integer intPersEmpresalibro) {
		this.intPersEmpresalibro = intPersEmpresalibro;
	}
	public Integer getIntContPeriodolibro() {
		return intContPeriodolibro;
	}
	public void setIntContPeriodolibro(Integer intContPeriodolibro) {
		this.intContPeriodolibro = intContPeriodolibro;
	}
	public Integer getIntContCodigolibro() {
		return intContCodigolibro;
	}
	public void setIntContCodigolibro(Integer intContCodigolibro) {
		this.intContCodigolibro = intContCodigolibro;
	}
	public Integer getIntPersEmpresalibroan() {
		return intPersEmpresalibroan;
	}
	public void setIntPersEmpresalibroan(Integer intPersEmpresalibroan) {
		this.intPersEmpresalibroan = intPersEmpresalibroan;
	}
	public Integer getIntContPeriodolibroan() {
		return intContPeriodolibroan;
	}
	public void setIntContPeriodolibroan(Integer intContPeriodolibroan) {
		this.intContPeriodolibroan = intContPeriodolibroan;
	}
	public Integer getIntContCodigolibroan() {
		return intContCodigolibroan;
	}
	public void setIntContCodigolibroan(Integer intContCodigolibroan) {
		this.intContCodigolibroan = intContCodigolibroan;
	}
	public List<CarteraCreditoDetalle> getListaCarteraCreDetalle() {
		return listaCarteraCreDetalle;
	}
	public void setListaCarteraCreDetalle(
			List<CarteraCreditoDetalle> listaCarteraCreDetalle) {
		this.listaCarteraCreDetalle = listaCarteraCreDetalle;
	}
	 
	
}
