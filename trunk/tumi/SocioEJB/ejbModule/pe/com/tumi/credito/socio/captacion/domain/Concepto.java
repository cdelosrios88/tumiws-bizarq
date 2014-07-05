package pe.com.tumi.credito.socio.captacion.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class Concepto extends TumiDomain {

	private ConceptoId id;
	private Captacion captacion;
	private Integer intMonto;
	private Integer intDia;
	private Integer intTipoMaxMinCod;
	private Boolean chkConcepto;
	private	Tabla	tabla;
	private	List<Tabla> listaTabla;
	
	public ConceptoId getId() {
		return id;
	}
	public void setId(ConceptoId id) {
		this.id = id;
	}
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	public Integer getIntMonto() {
		return intMonto;
	}
	public void setIntMonto(Integer intMonto) {
		this.intMonto = intMonto;
	}
	public Integer getIntDia() {
		return intDia;
	}
	public void setIntDia(Integer intDia) {
		this.intDia = intDia;
	}
	public Integer getIntTipoMaxMinCod() {
		return intTipoMaxMinCod;
	}
	public void setIntTipoMaxMinCod(Integer intTipoMaxMinCod) {
		this.intTipoMaxMinCod = intTipoMaxMinCod;
	}
	public Boolean getChkConcepto() {
		return chkConcepto;
	}
	public void setChkConcepto(Boolean chkConcepto) {
		this.chkConcepto = chkConcepto;
	}
	public Tabla getTabla() {
		return tabla;
	}
	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}
	public List<Tabla> getListaTabla() {
		return listaTabla;
	}
	public void setListaTabla(List<Tabla> listaTabla) {
		this.listaTabla = listaTabla;
	}
}
