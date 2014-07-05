package pe.com.tumi.seguridad.login.bean;

public class ComunicacionMsg {
	private String tipoComunicacion;
	private String subTipoComunicacion;
	private String descripcion;
	private String tipoLinea;
	private String numero;
	
	public String getTipoComunicacion() {
		return tipoComunicacion;
	}
	public void setTipoComunicacion(String tipoComunicacion) {
		this.tipoComunicacion = tipoComunicacion;
	}
	public String getSubTipoComunicacion() {
		return subTipoComunicacion;
	}
	public void setSubTipoComunicacion(String subTipoComunicacion) {
		this.subTipoComunicacion = subTipoComunicacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipoLinea() {
		return tipoLinea;
	}
	public void setTipoLinea(String tipoLinea) {
		this.tipoLinea = tipoLinea;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}
