package pe.com.tumi.parametro.general.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Tarifa extends TumiDomain{

	private TarifaId id;
	private Date dtTarifaHasta;
	private String strComentario;
	private Integer intParaFormaAplicar;
	private BigDecimal bdValor;
	private Timestamp tsFechaRegistro;
	private Integer intPersEmpresaUsuario;
	private Integer intPersPersonaUsuario;
	
	public TarifaId getId() {
		return id;
	}
	public void setId(TarifaId id) {
		this.id = id;
	}
	public Date getDtTarifaHasta() {
		return dtTarifaHasta;
	}
	public void setDtTarifaHasta(Date dtTarifaHasta) {
		this.dtTarifaHasta = dtTarifaHasta;
	}
	public String getStrComentario() {
		return strComentario;
	}
	public void setStrComentario(String strComentario) {
		this.strComentario = strComentario;
	}
	public Integer getIntParaFormaAplicar() {
		return intParaFormaAplicar;
	}
	public void setIntParaFormaAplicar(Integer intParaFormaAplicar) {
		this.intParaFormaAplicar = intParaFormaAplicar;
	}
	public BigDecimal getBdValor() {
		return bdValor;
	}
	public void setBdValor(BigDecimal bdValor) {
		this.bdValor = bdValor;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersEmpresaUsuario() {
		return intPersEmpresaUsuario;
	}
	public void setIntPersEmpresaUsuario(Integer intPersEmpresaUsuario) {
		this.intPersEmpresaUsuario = intPersEmpresaUsuario;
	}
	public Integer getIntPersPersonaUsuario() {
		return intPersPersonaUsuario;
	}
	public void setIntPersPersonaUsuario(Integer intPersPersonaUsuario) {
		this.intPersPersonaUsuario = intPersPersonaUsuario;
	}
}
