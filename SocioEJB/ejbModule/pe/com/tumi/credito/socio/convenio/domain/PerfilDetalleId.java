package pe.com.tumi.credito.socio.convenio.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PerfilDetalleId extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intSeguPerfilPk;
	private Integer intItemAdendaPerfil;
	private Integer intParaValidacionCod;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntSeguPerfilPk() {
		return intSeguPerfilPk;
	}
	public void setIntSeguPerfilPk(Integer intSeguPerfilPk) {
		this.intSeguPerfilPk = intSeguPerfilPk;
	}
	public Integer getIntItemAdendaPerfil() {
		return intItemAdendaPerfil;
	}
	public void setIntItemAdendaPerfil(Integer intItemAdendaPerfil) {
		this.intItemAdendaPerfil = intItemAdendaPerfil;
	}
	public Integer getIntParaValidacionCod() {
		return intParaValidacionCod;
	}
	public void setIntParaValidacionCod(Integer intParaValidacionCod) {
		this.intParaValidacionCod = intParaValidacionCod;
	}
}
