package pe.com.tumi.riesgo.archivo.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConfEstructura extends TumiDomain{

	private ConfEstructuraId id;
	private Configuracion configuracion;
	private Integer intSociNivelPk;
	private Integer intSociCodigoPk;
	private Integer intParaTipoSocioCod;
	private Integer intParaModalidadCod;
	private Integer intParaEstadoCod;
	//Para interfaz
	private String strSiglas;
	
		
	public ConfEstructuraId getId() {
		return id;
	}
	public void setId(ConfEstructuraId id) {
		this.id = id;
	}
	public Configuracion getConfiguracion() {
		return configuracion;
	}
	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}
	public Integer getIntSociNivelPk() {
		return intSociNivelPk;
	}
	public void setIntSociNivelPk(Integer intSociNivelPk) {
		this.intSociNivelPk = intSociNivelPk;
	}
	public Integer getIntSociCodigoPk() {
		return intSociCodigoPk;
	}
	public void setIntSociCodigoPk(Integer intSociCodigoPk) {
		this.intSociCodigoPk = intSociCodigoPk;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntParaTipoSocioCod() {
		return intParaTipoSocioCod;
	}
	public void setIntParaTipoSocioCod(Integer intParaTipoSocioCod) {
		this.intParaTipoSocioCod = intParaTipoSocioCod;
	}
	public Integer getIntParaModalidadCod() {
		return intParaModalidadCod;
	}
	public void setIntParaModalidadCod(Integer intParaModalidadCod) {
		this.intParaModalidadCod = intParaModalidadCod;
	}
	public String getStrSiglas() {
		return strSiglas;
	}
	public void setStrSiglas(String strSiglas) {
		this.strSiglas = strSiglas;
	}
	
	@Override
	public String toString() {
		return "ConfEstructura [id=" + id + ", configuracion=" + configuracion
				+ ", intSociNivelPk=" + intSociNivelPk + ", intSociCodigoPk="
				+ intSociCodigoPk + ", intParaTipoSocioCod="
				+ intParaTipoSocioCod + ", intParaModalidadCod="
				+ intParaModalidadCod + ", intParaEstadoCod="
				+ intParaEstadoCod + "]";
	}

}
