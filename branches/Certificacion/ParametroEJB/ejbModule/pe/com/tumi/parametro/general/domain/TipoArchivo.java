package pe.com.tumi.parametro.general.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class TipoArchivo extends TumiDomain{

	private Integer intParaTipoCod;
	private String strDescripcion;
	private String strRuta;
	private Integer intParaGrupoArchivoCod;
	private String strPrefijo;
	private List<Archivo> listaArchivo;
	
	public Integer getIntParaTipoCod() {
		return intParaTipoCod;
	}
	public void setIntParaTipoCod(Integer intParaTipoCod) {
		this.intParaTipoCod = intParaTipoCod;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public String getStrRuta() {
		return strRuta;
	}
	public void setStrRuta(String strRuta) {
		this.strRuta = strRuta;
	}
	public Integer getIntParaGrupoArchivoCod() {
		return intParaGrupoArchivoCod;
	}
	public void setIntParaGrupoArchivoCod(Integer intParaGrupoArchivoCod) {
		this.intParaGrupoArchivoCod = intParaGrupoArchivoCod;
	}
	public String getStrPrefijo() {
		return strPrefijo;
	}
	public void setStrPrefijo(String strPrefijo) {
		this.strPrefijo = strPrefijo;
	}
	public List<Archivo> getListaArchivo() {
		return listaArchivo;
	}
	public void setListaArchivo(List<Archivo> listaArchivo) {
		this.listaArchivo = listaArchivo;
	}
	
}
