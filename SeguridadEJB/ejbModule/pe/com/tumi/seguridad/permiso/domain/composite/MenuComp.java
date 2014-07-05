package pe.com.tumi.seguridad.permiso.domain.composite;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;

public class MenuComp extends TumiDomain {
	private Integer intPersEmpresaPk;
	private List<String> listaStrIdTransaccion;
	private List<String>  listaStrNombre;
	private Integer intTipoMenu;
	private Integer intIdEstado;
	private List<Transaccion> listaMenu01;
	private List<Transaccion> listaMenu02;
	private List<Transaccion> listaMenu03;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public List<String> getListaStrIdTransaccion() {
		return listaStrIdTransaccion;
	}
	public void setListaStrIdTransaccion(List<String> listaStrIdTransaccion) {
		this.listaStrIdTransaccion = listaStrIdTransaccion;
	}
	public List<String> getListaStrNombre() {
		return listaStrNombre;
	}
	public void setListaStrNombre(List<String> listaStrNombre) {
		this.listaStrNombre = listaStrNombre;
	}
	public Integer getIntTipoMenu() {
		return intTipoMenu;
	}
	public void setIntTipoMenu(Integer intTipoMenu) {
		this.intTipoMenu = intTipoMenu;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public List<Transaccion> getListaMenu01() {
		return listaMenu01;
	}
	public void setListaMenu01(List<Transaccion> listaMenu01) {
		this.listaMenu01 = listaMenu01;
	}
	public List<Transaccion> getListaMenu02() {
		return listaMenu02;
	}
	public void setListaMenu02(List<Transaccion> listaMenu02) {
		this.listaMenu02 = listaMenu02;
	}
	public List<Transaccion> getListaMenu03() {
		return listaMenu03;
	}
	public void setListaMenu03(List<Transaccion> listaMenu03) {
		this.listaMenu03 = listaMenu03;
	}
}
