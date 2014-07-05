package pe.com.tumi.popup.domain;

import java.util.List;

public class CuentaBancaria { 
	
	private 	String 		strNroCtaBancaria;
	private 	Integer 	intIdMoneda;
	private 	Integer 	intIdTipoCuenta;
	private 	Integer 	intIdPersona;
	private 	Integer		intIdBanco;
	private 	Integer		intIdCtaBancaria;
	private		Integer 	intIdEstado;
	private 	Integer 	intAbono;
	private 	Integer 	intDepositaCTS;
	private 	Integer 	intCargos;
	private 	String 		strObservacion;
	private 	String 		strRazonCuenta[];
	private 	Integer 	intIdCtaBancariaOut;
	private 	List 		cursorLista;
	
	
	public String getStrNroCtaBancaria() {
		return strNroCtaBancaria;
	}
	public void setStrNroCtaBancaria(String strNroCtaBancaria) {
		this.strNroCtaBancaria = strNroCtaBancaria;
	}
	public Integer getIntIdMoneda() {
		return intIdMoneda;
	}
	public void setIntIdMoneda(Integer intIdMoneda) {
		this.intIdMoneda = intIdMoneda;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntIdBanco() {
		return intIdBanco;
	}
	public void setIntIdBanco(Integer intIdBanco) {
		this.intIdBanco = intIdBanco;
	}
	public Integer getIntIdCtaBancaria() {
		return intIdCtaBancaria;
	}
	public void setIntIdCtaBancaria(Integer intIdCtaBancaria) {
		this.intIdCtaBancaria = intIdCtaBancaria;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Integer getIntDepositaCTS() {
		return intDepositaCTS;
	}
	public void setIntDepositaCTS(Integer intDepositaCTS) {
		this.intDepositaCTS = intDepositaCTS;
	}
	public Integer getIntIdTipoCuenta() {
		return intIdTipoCuenta;
	}
	public void setIntIdTipoCuenta(Integer intIdTipoCuenta) {
		this.intIdTipoCuenta = intIdTipoCuenta;
	}
	public String[] getStrRazonCuenta() {
		return strRazonCuenta;
	}
	public void setStrRazonCuenta(String[] strRazonCuenta) {
		this.strRazonCuenta = strRazonCuenta;
	}
	public Integer getIntAbono() {
		return intAbono;
	}
	public void setIntAbono(Integer intAbono) {
		this.intAbono = intAbono;
	}
	public Integer getIntCargos() {
		return intCargos;
	}
	public void setIntCargos(Integer intCargos) {
		this.intCargos = intCargos;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntIdCtaBancariaOut() {
		return intIdCtaBancariaOut;
	}
	public void setIntIdCtaBancariaOut(Integer intIdCtaBancariaOut) {
		this.intIdCtaBancariaOut = intIdCtaBancariaOut;
	}
	public List getCursorLista() {
		return cursorLista;
	}
	public void setCursorLista(List cursorLista) {
		this.cursorLista = cursorLista;
	}
	
}
