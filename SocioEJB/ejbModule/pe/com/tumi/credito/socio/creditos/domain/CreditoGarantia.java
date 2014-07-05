package pe.com.tumi.credito.socio.creditos.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoTipoGarantiaComp;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoGarantia extends TumiDomain {
	private CreditoGarantiaId id;
	private Integer intParaClaseCod;
	private Integer intParaSubClaseCod;
	private Integer intParaNaturalezaGarantiaCod;
	private Integer intParaTipoCartaFianzaCod;
	private Boolean boDocAdjunto;
	//private Integer intDocAdjunto;
	private Integer intOpcionMinimoCta;
	private Boolean boOpcionMinimoCta;
	private BigDecimal bdPorcCuenta;
	private Integer intParaDerechoCarta;
	private Boolean boOpcionValorVenta;
	private Integer intOpcionValorVenta;
	private Boolean boValuadorInscrito;
	private Integer intValuadorInscrito;
	private Boolean boTasacionComercial;
	private Integer intTasacionComercial;
	private Boolean boNegociacionEndoso;
	private Integer intNegociacionEndoso;
	private Integer intParaEstadoCod;
	private Date dtFechaEliminacion;
	
	private List<CreditoTipoGarantiaComp> listaTipoGarantiaComp;
	private List<CreditoGarantiaTipoValorVenta> listaTipoValorVenta;
	
	// 10.09.2013 - CGD
	private CreditoTipoGarantia creditoTipoGarantia;
	// 12.09.2013 - CGD
	private Integer intNroGarantesConfigurados;
	private Integer intNroGarantesCorrectos;
	private List<CreditoTipoGarantia> listaTipoGarantia;
	
	//Getters y Setters
	public CreditoGarantiaId getId() {
		return id;
	}
	public void setId(CreditoGarantiaId id) {
		this.id = id;
	}
	public Integer getIntParaClaseCod() {
		return intParaClaseCod;
	}
	public void setIntParaClaseCod(Integer intParaClaseCod) {
		this.intParaClaseCod = intParaClaseCod;
	}
	public Integer getIntParaSubClaseCod() {
		return intParaSubClaseCod;
	}
	public void setIntParaSubClaseCod(Integer intParaSubClaseCod) {
		this.intParaSubClaseCod = intParaSubClaseCod;
	}
	public Integer getIntParaNaturalezaGarantiaCod() {
		return intParaNaturalezaGarantiaCod;
	}
	public void setIntParaNaturalezaGarantiaCod(Integer intParaNaturalezaGarantiaCod) {
		this.intParaNaturalezaGarantiaCod = intParaNaturalezaGarantiaCod;
	}
	public Integer getIntParaTipoCartaFianzaCod() {
		return intParaTipoCartaFianzaCod;
	}
	public void setIntParaTipoCartaFianzaCod(Integer intParaTipoCartaFianzaCod) {
		this.intParaTipoCartaFianzaCod = intParaTipoCartaFianzaCod;
	}
	public Boolean getBoDocAdjunto() {
		return boDocAdjunto;
	}
	public void setBoDocAdjunto(Boolean boDocAdjunto) {
		this.boDocAdjunto = boDocAdjunto;
	}
	/*public Integer getIntDocAdjunto() {
		return intDocAdjunto;
	}
	public void setIntDocAdjunto(Integer intDocAdjunto) {
		this.intDocAdjunto = intDocAdjunto;
	}*/
	public Integer getIntOpcionMinimoCta() {
		return intOpcionMinimoCta;
	}
	public void setIntOpcionMinimoCta(Integer intOpcionMinimoCta) {
		this.intOpcionMinimoCta = intOpcionMinimoCta;
	}
	public Boolean getBoOpcionMinimoCta() {
		return boOpcionMinimoCta;
	}
	public void setBoOpcionMinimoCta(Boolean boOpcionMinimoCta) {
		this.boOpcionMinimoCta = boOpcionMinimoCta;
	}
	public BigDecimal getBdPorcCuenta() {
		return bdPorcCuenta;
	}
	public void setBdPorcCuenta(BigDecimal bdPorcCuenta) {
		this.bdPorcCuenta = bdPorcCuenta;
	}
	public Integer getIntParaDerechoCarta() {
		return intParaDerechoCarta;
	}
	public void setIntParaDerechoCarta(Integer intParaDerechoCarta) {
		this.intParaDerechoCarta = intParaDerechoCarta;
	}
	public Boolean getBoOpcionValorVenta() {
		return boOpcionValorVenta;
	}
	public void setBoOpcionValorVenta(Boolean boOpcionValorVenta) {
		this.boOpcionValorVenta = boOpcionValorVenta;
	}
	public Integer getIntOpcionValorVenta() {
		return intOpcionValorVenta;
	}
	public void setIntOpcionValorVenta(Integer intOpcionValorVenta) {
		this.intOpcionValorVenta = intOpcionValorVenta;
	}
	public Integer getIntValuadorInscrito() {
		return intValuadorInscrito;
	}
	public void setIntValuadorInscrito(Integer intValuadorInscrito) {
		this.intValuadorInscrito = intValuadorInscrito;
	}
	public Integer getIntTasacionComercial() {
		return intTasacionComercial;
	}
	public void setIntTasacionComercial(Integer intTasacionComercial) {
		this.intTasacionComercial = intTasacionComercial;
	}
	public Boolean getBoValuadorInscrito() {
		return boValuadorInscrito;
	}
	public void setBoValuadorInscrito(Boolean boValuadorInscrito) {
		this.boValuadorInscrito = boValuadorInscrito;
	}
	public Boolean getBoTasacionComercial() {
		return boTasacionComercial;
	}
	public void setBoTasacionComercial(Boolean boTasacionComercial) {
		this.boTasacionComercial = boTasacionComercial;
	}
	public Boolean getBoNegociacionEndoso() {
		return boNegociacionEndoso;
	}
	public void setBoNegociacionEndoso(Boolean boNegociacionEndoso) {
		this.boNegociacionEndoso = boNegociacionEndoso;
	}
	public Integer getIntNegociacionEndoso() {
		return intNegociacionEndoso;
	}
	public void setIntNegociacionEndoso(Integer intNegociacionEndoso) {
		this.intNegociacionEndoso = intNegociacionEndoso;
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
	public List<CreditoTipoGarantiaComp> getListaTipoGarantiaComp() {
		return listaTipoGarantiaComp;
	}
	public void setListaTipoGarantiaComp(
			List<CreditoTipoGarantiaComp> listaTipoGarantiaComp) {
		this.listaTipoGarantiaComp = listaTipoGarantiaComp;
	}
	public List<CreditoGarantiaTipoValorVenta> getListaTipoValorVenta() {
		return listaTipoValorVenta;
	}
	public void setListaTipoValorVenta(
			List<CreditoGarantiaTipoValorVenta> listaTipoValorVenta) {
		this.listaTipoValorVenta = listaTipoValorVenta;
	}
	public CreditoTipoGarantia getCreditoTipoGarantia() {
		return creditoTipoGarantia;
	}
	public void setCreditoTipoGarantia(CreditoTipoGarantia creditoTipoGarantia) {
		this.creditoTipoGarantia = creditoTipoGarantia;
	}
	public Integer getIntNroGarantesConfigurados() {
		return intNroGarantesConfigurados;
	}
	public void setIntNroGarantesConfigurados(Integer intNroGarantesConfigurados) {
		this.intNroGarantesConfigurados = intNroGarantesConfigurados;
	}
	public List<CreditoTipoGarantia> getListaTipoGarantia() {
		return listaTipoGarantia;
	}
	public void setListaTipoGarantia(List<CreditoTipoGarantia> listaTipoGarantia) {
		this.listaTipoGarantia = listaTipoGarantia;
	}
	public Integer getIntNroGarantesCorrectos() {
		return intNroGarantesCorrectos;
	}
	public void setIntNroGarantesCorrectos(Integer intNroGarantesCorrectos) {
		this.intNroGarantesCorrectos = intNroGarantesCorrectos;
	}
}