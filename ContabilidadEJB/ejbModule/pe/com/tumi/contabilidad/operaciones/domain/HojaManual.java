package pe.com.tumi.contabilidad.operaciones.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class HojaManual extends TumiDomain {//Hoja Manual es equivalente a Nota Contable
	private HojaManualId id;
	private Integer intParaDocumentoGeneralCod;
	private String strHomaGlosa;
	private Timestamp tsHomaFechaRegistro;
	private Integer intPersEmpresaLibroPk;
	private Integer intContPeriodoLibro;
	private Integer intContCodigoLibro;
	
	private LibroDiario libroDiario;
	private List<HojaManualDetalle> listHojaManualDetalle;
	
	//El Monto es la suma de todos los Debe o Haber de esta Nota Contable
	//ya que son iguales (Partida Doble)
	private BigDecimal bdMonto;

	private Timestamp tsFechaRegistroDesde;
	private Timestamp tsFechaRegistroHasta;
	
	public HojaManualId getId() {
		return id;
	}
	public void setId(HojaManualId id) {
		this.id = id;
	}
	public Integer getIntParaDocumentoGeneralCod() {
		return intParaDocumentoGeneralCod;
	}
	public void setIntParaDocumentoGeneralCod(Integer intParaDocumentoGeneralCod) {
		this.intParaDocumentoGeneralCod = intParaDocumentoGeneralCod;
	}
	public String getStrHomaGlosa() {
		return strHomaGlosa;
	}
	public void setStrHomaGlosa(String strHomaGlosa) {
		this.strHomaGlosa = strHomaGlosa;
	}
	public Timestamp getTsHomaFechaRegistro() {
		return tsHomaFechaRegistro;
	}
	public void setTsHomaFechaRegistro(Timestamp tsHomaFechaRegistro) {
		this.tsHomaFechaRegistro = tsHomaFechaRegistro;
	}
	public Integer getIntPersEmpresaLibroPk() {
		return intPersEmpresaLibroPk;
	}
	public void setIntPersEmpresaLibroPk(Integer intPersEmpresaLibroPk) {
		this.intPersEmpresaLibroPk = intPersEmpresaLibroPk;
	}
	public Integer getIntContPeriodoLibro() {
		return intContPeriodoLibro;
	}
	public void setIntContPeriodoLibro(Integer intContPeriodoLibro) {
		this.intContPeriodoLibro = intContPeriodoLibro;
	}
	public Integer getIntContCodigoLibro() {
		return intContCodigoLibro;
	}
	public void setIntContCodigoLibro(Integer intContCodigoLibro) {
		this.intContCodigoLibro = intContCodigoLibro;
	}
	public Timestamp getTsFechaRegistroDesde() {
		return tsFechaRegistroDesde;
	}
	public void setTsFechaRegistroDesde(Timestamp tsFechaRegistroDesde) {
		this.tsFechaRegistroDesde = tsFechaRegistroDesde;
	}
	public Timestamp getTsFechaRegistroHasta() {
		return tsFechaRegistroHasta;
	}
	public void setTsFechaRegistroHasta(Timestamp tsFechaRegistroHasta) {
		this.tsFechaRegistroHasta = tsFechaRegistroHasta;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public LibroDiario getLibroDiario() {
		return libroDiario;
	}
	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}
	public List<HojaManualDetalle> getListHojaManualDetalle() {
		return listHojaManualDetalle;
	}
	public void setListHojaManualDetalle(
			List<HojaManualDetalle> listHojaManualDetalle) {
		this.listHojaManualDetalle = listHojaManualDetalle;
	}
}
