package pe.com.tumi.parametro.general.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ArchivoId extends TumiDomain{

	private Integer intParaTipoCod;
	private Integer intItemArchivo;
	private Integer intItemHistorico;
	
	public Integer getIntParaTipoCod() {
		return intParaTipoCod;
	}
	public void setIntParaTipoCod(Integer intParaTipoCod) {
		this.intParaTipoCod = intParaTipoCod;
	}
	public Integer getIntItemArchivo() {
		return intItemArchivo;
	}
	public void setIntItemArchivo(Integer intItemArchivo) {
		this.intItemArchivo = intItemArchivo;
	}
	public Integer getIntItemHistorico() {
		return intItemHistorico;
	}
	public void setIntItemHistorico(Integer intItemHistorico) {
		this.intItemHistorico = intItemHistorico;
	}
}
