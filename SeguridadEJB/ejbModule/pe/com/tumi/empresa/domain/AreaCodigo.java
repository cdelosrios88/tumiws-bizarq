package pe.com.tumi.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AreaCodigo extends TumiDomain{
	private AreaCodigoId id;
	private String strDescripcion;
	private String strCodigo;
	private Integer intIdEstado;
	
	
	public AreaCodigoId getId() {
		return id;
	}
	public void setId(AreaCodigoId id) {
		this.id = id;
	}
	public String getStrCodigo() {
		return strCodigo;
	}
	public void setStrCodigo(String strCodigo) {
		this.strCodigo = strCodigo;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	
}
