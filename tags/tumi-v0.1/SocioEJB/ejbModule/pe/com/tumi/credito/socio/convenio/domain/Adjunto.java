package pe.com.tumi.credito.socio.convenio.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Adjunto extends TumiDomain {

	private AdjuntoId id;
	private Adenda adenda;
	private Integer intParaTipoArchivoCod;
	private Integer intItemArchivo;
	private Integer intParaItemHistorico;
	
	public AdjuntoId getId() {
		return id;
	}
	public void setId(AdjuntoId id) {
		this.id = id;
	}
	public Adenda getAdemda() {
		return adenda;
	}
	public void setAdemda(Adenda adenda) {
		this.adenda = adenda;
	}
	public Integer getIntParaTipoArchivoCod() {
		return intParaTipoArchivoCod;
	}
	public void setIntParaTipoArchivoCod(Integer intParaTipoArchivoCod) {
		this.intParaTipoArchivoCod = intParaTipoArchivoCod;
	}
	public Integer getIntItemArchivo() {
		return intItemArchivo;
	}
	public void setIntItemArchivo(Integer intItemArchivo) {
		this.intItemArchivo = intItemArchivo;
	}
	public Integer getIntParaItemHistorico() {
		return intParaItemHistorico;
	}
	public void setIntParaItemHistorico(Integer intParaItemHistorico) {
		this.intParaItemHistorico = intParaItemHistorico;
	}
}
