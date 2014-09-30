/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-004       			16/09/2014     Christian De los Ríos        Se agregan nuevos atributos al bean         
*/
package pe.com.tumi.contabilidad.cierre.domain;

import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Natural;

public class LibroMayor extends TumiDomain {

	private LibroMayorId id;
	private Timestamp tsFechaRegistro;
	private Integer intParaEstadoCierreCod;
	private Integer intPersEmpresaUsuario;
	private Integer intPersPersonaUsuario;
	private Integer intEstadoCod;
	//Inicio: REQ14-004 - bizarq - 16/09/2014
	private String strTablaIdentificador;
	private String strIpAddress;
	private Integer intPersPersonaUsuarioElimina;
	private Timestamp tsFechaRegistroElimina;
	//Fin: REQ14-004 - bizarq - 16/09/2014
	
	public String getStrTablaIdentificador() {
		return strTablaIdentificador;
	}

	public void setStrTablaIdentificador(String strTablaIdentificador) {
		this.strTablaIdentificador = strTablaIdentificador;
	}
	//para interfaz
	private Natural naturalPersonaUsuario;
	private List<LibroMayorDetalle> listaLibroMayorDetalle;
	private boolean esProcesado;
	
	public LibroMayor(){
		super();
		id = new LibroMayorId();
	}

	public LibroMayorId getId() {
		return id;
	}
	public void setId(LibroMayorId id) {
		this.id = id;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaEstadoCierreCod() {
		return intParaEstadoCierreCod;
	}
	public void setIntParaEstadoCierreCod(Integer intParaEstadoCierreCod) {
		this.intParaEstadoCierreCod = intParaEstadoCierreCod;
	}
	public Integer getIntPersEmpresaUsuario() {
		return intPersEmpresaUsuario;
	}
	public void setIntPersEmpresaUsuario(Integer intPersEmpresaUsuario) {
		this.intPersEmpresaUsuario = intPersEmpresaUsuario;
	}
	public Integer getIntPersPersonaUsuario() {
		return intPersPersonaUsuario;
	}
	public void setIntPersPersonaUsuario(Integer intPersPersonaUsuario) {
		this.intPersPersonaUsuario = intPersPersonaUsuario;
	}
	public Natural getNaturalPersonaUsuario() {
		return naturalPersonaUsuario;
	}
	public void setNaturalPersonaUsuario(Natural naturalPersonaUsuario) {
		this.naturalPersonaUsuario = naturalPersonaUsuario;
	}
	public List<LibroMayorDetalle> getListaLibroMayorDetalle() {
		return listaLibroMayorDetalle;
	}
	public void setListaLibroMayorDetalle(
			List<LibroMayorDetalle> listaLibroMayorDetalle) {
		this.listaLibroMayorDetalle = listaLibroMayorDetalle;
	}
	public boolean isEsProcesado() {
		return esProcesado;
	}
	public void setEsProcesado(boolean esProcesado) {
		this.esProcesado = esProcesado;
	}
	
	//Inicio: REQ14-004 - bizarq - 16/09/2014
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public String getStrIpAddress() {
		return strIpAddress;
	}
	public void setStrIpAddress(String strIpAddress) {
		this.strIpAddress = strIpAddress;
	}
	public Integer getIntPersPersonaUsuarioElimina() {
		return intPersPersonaUsuarioElimina;
	}
	public void setIntPersPersonaUsuarioElimina(Integer intPersPersonaUsuarioElimina) {
		this.intPersPersonaUsuarioElimina = intPersPersonaUsuarioElimina;
	}
	public Timestamp getTsFechaRegistroElimina() {
		return tsFechaRegistroElimina;
	}
	public void setTsFechaRegistroElimina(Timestamp tsFechaRegistroElimina) {
		this.tsFechaRegistroElimina = tsFechaRegistroElimina;
	}
	//Fin: REQ14-004 - bizarq - 16/09/2014
	@Override
	public String toString() {
		return "LibroMayor [id=" + id + ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intParaEstadoCierreCod=" + intParaEstadoCierreCod
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario + "]";
	}	
}