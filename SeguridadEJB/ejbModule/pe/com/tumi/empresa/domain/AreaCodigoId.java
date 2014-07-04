package pe.com.tumi.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AreaCodigoId extends TumiDomain {
	private Integer intIdEmpresaPk;
	private Integer intIdSucursalPk;
	private Integer intIdAreaPk;
	private Integer intIdTipoCodigoPk;
	
	
	public Integer getIntIdEmpresaPk() {
		return intIdEmpresaPk;
	}
	public void setIntIdEmpresaPk(Integer intIdEmpresaPk) {
		this.intIdEmpresaPk = intIdEmpresaPk;
	}
	public Integer getIntIdSucursalPk() {
		return intIdSucursalPk;
	}
	public void setIntIdSucursalPk(Integer intIdSucursalPk) {
		this.intIdSucursalPk = intIdSucursalPk;
	}
	public Integer getIntIdAreaPk() {
		return intIdAreaPk;
	}
	public void setIntIdAreaPk(Integer intIdAreaPk) {
		this.intIdAreaPk = intIdAreaPk;
	}
	public Integer getIntIdTipoCodigoPk() {
		return intIdTipoCodigoPk;
	}
	public void setIntIdTipoCodigoPk(Integer intIdTipoCodigoPk) {
		this.intIdTipoCodigoPk = intIdTipoCodigoPk;
	}
	
}
