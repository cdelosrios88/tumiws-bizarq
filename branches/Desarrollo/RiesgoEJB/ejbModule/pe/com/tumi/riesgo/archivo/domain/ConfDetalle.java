package pe.com.tumi.riesgo.archivo.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfDetalle extends TumiDomain{

	private ConfDetalleId id;
	private Configuracion configuracion;
	private Integer intParaTipoDatoCod;
	private Integer intParaTipoValorCod;
	private Integer intParaTipoDatoVariableCod;
	private String strDatoFijo;
	private String strValorCampo;
	private Integer intTamano;
	private Integer intParaTipoAlineacionCod;
	private Integer intParaTipoCompletaCod;
	private Integer intParaEstadoCod;
	
	public ConfDetalle(){
		super();
		id = new ConfDetalleId();
	}
	
	public ConfDetalleId getId() {
		return id;
	}
	public void setId(ConfDetalleId id) {
		this.id = id;
	}
	public Configuracion getConfiguracion() {
		return configuracion;
	}
	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}
	public Integer getIntParaTipoDatoCod() {
		return intParaTipoDatoCod;
	}
	public void setIntParaTipoDatoCod(Integer intParaTipoDatoCod) {
		this.intParaTipoDatoCod = intParaTipoDatoCod;
	}
	public Integer getIntParaTipoValorCod() {
		return intParaTipoValorCod;
	}
	public void setIntParaTipoValorCod(Integer intParaTipoValorCod) {
		this.intParaTipoValorCod = intParaTipoValorCod;
	}
	public Integer getIntParaTipoDatoVariableCod() {
		return intParaTipoDatoVariableCod;
	}
	public void setIntParaTipoDatoVariableCod(Integer intParaTipoDatoVariableCod) {
		this.intParaTipoDatoVariableCod = intParaTipoDatoVariableCod;
	}
	public String getStrDatoFijo() {
		return strDatoFijo;
	}
	public void setStrDatoFijo(String strDatoFijo) {
		this.strDatoFijo = strDatoFijo;
	}
	public String getStrValorCampo() {
		return strValorCampo;
	}
	public void setStrValorCampo(String strValorCampo) {
		this.strValorCampo = strValorCampo;
	}
	public Integer getIntTamano() {
		return intTamano;
	}
	public void setIntTamano(Integer intTamano) {
		this.intTamano = intTamano;
	}
	public Integer getIntParaTipoAlineacionCod() {
		return intParaTipoAlineacionCod;
	}
	public void setIntParaTipoAlineacionCod(Integer intParaTipoAlineacionCod) {
		this.intParaTipoAlineacionCod = intParaTipoAlineacionCod;
	}
	public Integer getIntParaTipoCompletaCod() {
		return intParaTipoCompletaCod;
	}
	public void setIntParaTipoCompletaCod(Integer intParaTipoCompletaCod) {
		this.intParaTipoCompletaCod = intParaTipoCompletaCod;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}

	@Override
	public String toString() {
		return "ConfDetalle [id=" + id + ", configuracion=" + configuracion
				+ ", intParaTipoDatoCod=" + intParaTipoDatoCod
				+ ", intParaTipoValorCod=" + intParaTipoValorCod
				+ ", intParaTipoDatoVariableCod=" + intParaTipoDatoVariableCod
				+ ", strDatoFijo=" + strDatoFijo + ", strValorCampo="
				+ strValorCampo + ", intTamano=" + intTamano
				+ ", intParaTipoAlineacionCod=" + intParaTipoAlineacionCod
				+ ", intParaTipoCompletaCod=" + intParaTipoCompletaCod
				+ ", intParaEstadoCod=" + intParaEstadoCod + "]";
	}
	

}
