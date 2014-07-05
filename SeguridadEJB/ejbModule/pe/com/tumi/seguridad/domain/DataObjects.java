package pe.com.tumi.seguridad.domain;

public class DataObjects {
	private 	int 	intCodigo;
	private 	String 	strNombreObjeto;
	private 	Boolean	blnSelectedObjecto = false;
	private		Integer intTipoObjecto;
	private 	Integer	intIdEmpresa;
	private 	String 	strIdTransaccion;
	private 	int 	intConta;
	private 	Integer intDiccNivel;
	private		Integer	intDiccSeguridad;
	private		Integer intDiccEstado;
	private 	Integer intIdSolicitud;
	
	
	public int getIntCodigo() {
		return intCodigo;
	}
	public void setIntCodigo(int intCodigo) {
		this.intCodigo = intCodigo;
	}
	public String getStrNombreObjeto() {
		return strNombreObjeto;
	}
	public void setStrNombreObjeto(String strNombreObjeto) {
		this.strNombreObjeto = strNombreObjeto;
	}
	public Boolean getBlnSelectedObjecto() {
		return blnSelectedObjecto;
	}
	public void setBlnSelectedObjecto(Boolean blnSelectedObjecto) {
		this.blnSelectedObjecto = blnSelectedObjecto;
	}
	public String getStrIdTransaccion() {
		return strIdTransaccion;
	}
	public void setStrIdTransaccion(String strIdTransaccion) {
		this.strIdTransaccion = strIdTransaccion;
	}
	public int getIntConta() {
		return intConta;
	}
	public void setIntConta(int intConta) {
		this.intConta = intConta;
	}
	public Integer getIntTipoObjecto() {
		return intTipoObjecto;
	}
	public void setIntTipoObjecto(Integer intTipoObjecto) {
		this.intTipoObjecto = intTipoObjecto;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntDiccNivel() {
		return intDiccNivel;
	}
	public void setIntDiccNivel(Integer intDiccNivel) {
		this.intDiccNivel = intDiccNivel;
	}
	public Integer getIntDiccSeguridad() {
		return intDiccSeguridad;
	}
	public void setIntDiccSeguridad(Integer intDiccSeguridad) {
		this.intDiccSeguridad = intDiccSeguridad;
	}
	public Integer getIntDiccEstado() {
		return intDiccEstado;
	}
	public void setIntDiccEstado(Integer intDiccEstado) {
		this.intDiccEstado = intDiccEstado;
	}
	public Integer getIntIdSolicitud() {
		return intIdSolicitud;
	}
	public void setIntIdSolicitud(Integer intIdSolicitud) {
		this.intIdSolicitud = intIdSolicitud;
	}
	
}
