package pe.com.tumi.seguridad.domain;

import java.util.Date;
import java.util.List;

public class AccesoExternoPc {
	private Integer intIdEmpresa;
	private Integer intIdSucursal;
	private Integer intIdArea;
	private Integer intIdComputadora;
	private Integer intIdTipoAcceso;
	private String	strTipoAcceso;
	private Integer intIdEstado;
	private Integer intConta;
	private List 	cursorLista;
	
	
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public Integer getIntIdArea() {
		return intIdArea;
	}
	public void setIntIdArea(Integer intIdArea) {
		this.intIdArea = intIdArea;
	}
	public Integer getIntIdComputadora() {
		return intIdComputadora;
	}
	public void setIntIdComputadora(Integer intIdComputadora) {
		this.intIdComputadora = intIdComputadora;
	}
	public Integer getIntIdTipoAcceso() {
		return intIdTipoAcceso;
	}
	public void setIntIdTipoAcceso(Integer intIdTipoAcceso) {
		this.intIdTipoAcceso = intIdTipoAcceso;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Integer getIntConta() {
		return intConta;
	}
	public void setIntConta(Integer intConta) {
		this.intConta = intConta;
	}
	public String getStrTipoAcceso() {
		return strTipoAcceso;
	}
	public void setStrTipoAcceso(String strTipoAcceso) {
		this.strTipoAcceso = strTipoAcceso;
	}
	public List getCursorLista() {
		return cursorLista;
	}
	public void setCursorLista(List cursorLista) {
		this.cursorLista = cursorLista;
	}
	
}
