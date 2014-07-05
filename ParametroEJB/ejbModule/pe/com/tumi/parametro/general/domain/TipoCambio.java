package pe.com.tumi.parametro.general.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class TipoCambio extends TumiDomain{

	private TipoCambioId id;
	private BigDecimal bdCompra;
	private BigDecimal bdVenta;
	private BigDecimal bdPromedio;
	private Timestamp tsFechaRegistro;
	private Integer intEmpresaUsuario;
	private Integer intPersonaUsuario;
	
	private Date dtFechaTCDesde;
	private Date dtFechaTCHasta;
	
	public TipoCambioId getId() {
		return id;
	}
	public void setId(TipoCambioId id) {
		this.id = id;
	}
	public BigDecimal getBdCompra() {
		return bdCompra;
	}
	public void setBdCompra(BigDecimal bdCompra) {
		this.bdCompra = bdCompra;
	}
	public BigDecimal getBdVenta() {
		return bdVenta;
	}
	public void setBdVenta(BigDecimal bdVenta) {
		this.bdVenta = bdVenta;
	}
	public BigDecimal getBdPromedio() {
		return bdPromedio;
	}
	public void setBdPromedio(BigDecimal bdPromedio) {
		this.bdPromedio = bdPromedio;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntEmpresaUsuario() {
		return intEmpresaUsuario;
	}
	public void setIntEmpresaUsuario(Integer intEmpresaUsuario) {
		this.intEmpresaUsuario = intEmpresaUsuario;
	}
	public Integer getIntPersonaUsuario() {
		return intPersonaUsuario;
	}
	public void setIntPersonaUsuario(Integer intPersonaUsuario) {
		this.intPersonaUsuario = intPersonaUsuario;
	}
	public Date getDtFechaTCDesde() {
		return dtFechaTCDesde;
	}
	public void setDtFechaTCDesde(Date dtFechaTCDesde) {
		this.dtFechaTCDesde = dtFechaTCDesde;
	}
	public Date getDtFechaTCHasta() {
		return dtFechaTCHasta;
	}
	public void setDtFechaTCHasta(Date dtFechaTCHasta) {
		this.dtFechaTCHasta = dtFechaTCHasta;
	}
	@Override
	public String toString() {
		return "TipoCambio [id=" + id + ", bdCompra=" + bdCompra + ", bdVenta="
				+ bdVenta + ", bdPromedio=" + bdPromedio + ", tsFechaRegistro="
				+ tsFechaRegistro + ", intEmpresaUsuario=" + intEmpresaUsuario
				+ ", intPersonaUsuario=" + intPersonaUsuario
				+ ", dtFechaTCDesde=" + dtFechaTCDesde + ", dtFechaTCHasta="
				+ dtFechaTCHasta + "]";
	}	
}