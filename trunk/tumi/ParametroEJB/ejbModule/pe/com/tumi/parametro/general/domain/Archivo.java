package pe.com.tumi.parametro.general.domain;

import java.io.File;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Archivo extends TumiDomain{

	private ArchivoId id;
	private String strNombrearchivo;
	private Timestamp tsFechaRegistro;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaEliminacion;
	private TipoArchivo tipoarchivo;
	
	private	String 	rutaActual;
	private	String	rutaAntigua;
	
	//Auxiliar
	
	private File file;
	
	public ArchivoId getId() {
		return id;
	}
	public void setId(ArchivoId id) {
		this.id = id;
	}
	public TipoArchivo getTipoarchivo() {
		return tipoarchivo;
	}
	public void setTipoarchivo(TipoArchivo tipoarchivo) {
		this.tipoarchivo = tipoarchivo;
	}
	public String getStrNombrearchivo() {
		return strNombrearchivo;
	}
	public void setStrNombrearchivo(String strNombrearchivo) {
		this.strNombrearchivo = strNombrearchivo;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public String getRutaActual() {
		return rutaActual;
	}
	public void setRutaActual(String rutaActual) {
		this.rutaActual = rutaActual;
	}
	public String getRutaAntigua() {
		return rutaAntigua;
	}
	public void setRutaAntigua(String rutaAntigua) {
		this.rutaAntigua = rutaAntigua;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	
	
}
