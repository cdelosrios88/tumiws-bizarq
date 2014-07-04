package pe.com.tumi.seguridad.usuario.domain;

public class Menu {
	private int 	intIdTransaccion;
	private	int		intIdTransOpcion;
	private String	strDescripcion;
	private int		intHijos;
	private int		intMenuOrden;
	
	
	
	public int getIntIdTransaccion() {
		return intIdTransaccion;
	}
	public void setIntIdTransaccion(int intIdTransaccion) {
		this.intIdTransaccion = intIdTransaccion;
	}
	public int getIntIdTransOpcion() {
		return intIdTransOpcion;
	}
	public void setIntIdTransOpcion(int intIdTransOpcion) {
		this.intIdTransOpcion = intIdTransOpcion;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public int getIntHijos() {
		return intHijos;
	}
	public void setIntHijos(int intHijos) {
		this.intHijos = intHijos;
	}
	public int getIntMenuOrden() {
		return intMenuOrden;
	}
	public void setIntMenuOrden(int intMenuOrden) {
		this.intMenuOrden = intMenuOrden;
	}
}
