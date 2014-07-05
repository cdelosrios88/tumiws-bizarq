package pe.com.tumi.persona.core.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaBancaria extends TumiDomain{

	private CuentaBancariaPK id;
	private Integer intBancoCod;
	private Integer intDepositaCts;
	private Integer intEstadoCod;
	private Integer intMarcaAbono;
	private Integer intMarcaCargo;
	private Integer intMonedaCod;
	private String strNroCuentaBancaria;
	private String strObservacion;
	private Integer intTipoCuentaCod;
	private String	strCodigoInterbancario;

	private Persona persona;
	private	List<CuentaBancariaFin>	listaCuentaBancariaFin;
	private String strEtiqueta;
	
	public CuentaBancariaPK getId() {
		return id;
	}
	public void setId(CuentaBancariaPK id) {
		this.id = id;
	}
	public Integer getIntBancoCod() {
		return intBancoCod;
	}
	public void setIntBancoCod(Integer intBancoCod) {
		this.intBancoCod = intBancoCod;
	}
	public Integer getIntDepositaCts() {
		return intDepositaCts;
	}
	public void setIntDepositaCts(Integer intDepositaCts) {
		this.intDepositaCts = intDepositaCts;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public Integer getIntMarcaAbono() {
		return intMarcaAbono;
	}
	public void setIntMarcaAbono(Integer intMarcaAbono) {
		this.intMarcaAbono = intMarcaAbono;
	}
	public Integer getIntMarcaCargo() {
		return intMarcaCargo;
	}
	public void setIntMarcaCargo(Integer intMarcaCargo) {
		this.intMarcaCargo = intMarcaCargo;
	}
	public Integer getIntMonedaCod() {
		return intMonedaCod;
	}
	public void setIntMonedaCod(Integer intMonedaCod) {
		this.intMonedaCod = intMonedaCod;
	}
	public String getStrNroCuentaBancaria() {
		return strNroCuentaBancaria;
	}
	public void setStrNroCuentaBancaria(String strNroCuentaBancaria) {
		this.strNroCuentaBancaria = strNroCuentaBancaria;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntTipoCuentaCod() {
		return intTipoCuentaCod;
	}
	public void setIntTipoCuentaCod(Integer intTipoCuentaCod) {
		this.intTipoCuentaCod = intTipoCuentaCod;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public List<CuentaBancariaFin> getListaCuentaBancariaFin() {
		return listaCuentaBancariaFin;
	}
	public void setListaCuentaBancariaFin(List<CuentaBancariaFin> listaCuentaBancariaFin) {
		this.listaCuentaBancariaFin = listaCuentaBancariaFin;
	}
	public String getStrEtiqueta() {
		return strEtiqueta;
	}
	public void setStrEtiqueta(String strEtiqueta) {
		this.strEtiqueta = strEtiqueta;
	}
	public String getStrCodigoInterbancario() {
		return strCodigoInterbancario;
	}
	public void setStrCodigoInterbancario(String strCodigoInterbancario) {
		this.strCodigoInterbancario = strCodigoInterbancario;
	}
	@Override
	public String toString() {
		return "CuentaBancaria [id=" + id + ", intBancoCod=" + intBancoCod
				+ ", intDepositaCts=" + intDepositaCts + ", intEstadoCod="
				+ intEstadoCod + ", intMarcaAbono=" + intMarcaAbono
				+ ", intMarcaCargo=" + intMarcaCargo + ", intMonedaCod="
				+ intMonedaCod + ", strNroCuentaBancaria="
				+ strNroCuentaBancaria + ", strObservacion=" + strObservacion
				+ ", intTipoCuentaCod=" + intTipoCuentaCod
				+ ", strCodigoInterbancario=" + strCodigoInterbancario
				+ ", strEtiqueta=" + strEtiqueta + "]";
	}	
}