package pe.com.tumi.credito.socio.convenio.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Poblacion extends TumiDomain {
	
	private PoblacionId id;
	private Integer intConvenio;
	private Integer intItemConvenio;
	private Adenda adenda;
	private String strDescripcion;
	private BigDecimal bdDistancia;
	private Integer intParaEstadoCod;
	private List<PoblacionDetalle> listaPoblacionDetalle;
	
	public PoblacionId getId() {
		return id;
	}
	public void setId(PoblacionId id) {
		this.id = id;
	}
	public Integer getIntConvenio() {
		return intConvenio;
	}
	public void setIntConvenio(Integer intConvenio) {
		this.intConvenio = intConvenio;
	}
	public Integer getIntItemConvenio() {
		return intItemConvenio;
	}
	public void setIntItemConvenio(Integer intItemConvenio) {
		this.intItemConvenio = intItemConvenio;
	}
	public Adenda getAdenda() {
		return adenda;
	}
	public void setAdenda(Adenda adenda) {
		this.adenda = adenda;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public BigDecimal getBdDistancia() {
		return bdDistancia;
	}
	public void setBdDistancia(BigDecimal bdDistancia) {
		this.bdDistancia = bdDistancia;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public List<PoblacionDetalle> getListaPoblacionDetalle() {
		return listaPoblacionDetalle;
	}
	public void setListaPoblacionDetalle(
			List<PoblacionDetalle> listaPoblacionDetalle) {
		this.listaPoblacionDetalle = listaPoblacionDetalle;
	}

}
