package pe.com.tumi.seguridad.domain;

import java.util.List;

public class Auditoria {
	private Integer					intIdEmpresa;
	private Integer 				intIdPersona;
	private String					strIdTransaccion;
	private String					strTransaccion;
	private String	    			daFecRegistro;
	private Integer					intCntAcceso;
	private String[]				lstFechas;
	private Integer					intSumCntAcceso;
	
	//Getters y Setters
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public String getStrIdTransaccion() {
		return strIdTransaccion;
	}
	public void setStrIdTransaccion(String strIdTransaccion) {
		this.strIdTransaccion = strIdTransaccion;
	}
	public String getStrTransaccion() {
		return strTransaccion;
	}
	public void setStrTransaccion(String strTransaccion) {
		this.strTransaccion = strTransaccion;
	}
	public String getDaFecRegistro() {
		return daFecRegistro;
	}
	public void setDaFecRegistro(String daFecRegistro) {
		this.daFecRegistro = daFecRegistro;
	}
	public Integer getIntCntAcceso() {
		return intCntAcceso;
	}
	public void setIntCntAcceso(Integer intCntAcceso) {
		this.intCntAcceso = intCntAcceso;
	}
	public String[] getLstFechas() {
		return lstFechas;
	}
	public void setLstFechas(String[] lstFechas) {
		this.lstFechas = lstFechas;
	}
	public Integer getIntSumCntAcceso() {
		return intSumCntAcceso;
	}
	public void setIntSumCntAcceso(Integer intSumCntAcceso) {
		this.intSumCntAcceso = intSumCntAcceso;
	}
	
}
