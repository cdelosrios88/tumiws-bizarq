package pe.com.tumi.persona.contacto.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.persona.core.domain.Persona;

public class Domicilio extends TumiDomain{

	private DomicilioPK id;
	private Integer intEnviaCorrespondencia;
	private Boolean fgCorrespondencia;
	private Integer intEstadoCod;
	private String strInterior;
	private String strNombreVia;
	private Integer intNumeroVia;
	private Integer intInterior;
	private String strObservacion;
	private String strReferencia;
	private Boolean fgCroquis;
	//private String strRutaCroquis;
	private String strNombreZona;
	private String strDireccion;
	private Integer intIdDirUrl;
	private String  strDirUrl;
	private Integer intTipoDireccionCod;
	private Integer intTipoDomicilioCod;
	private Integer intTipoViaCod;
	private Integer intTipoViviendaCod;
	private Integer intTipoZonaCod;
	private Integer intParaUbigeoPk;
	private Integer intParaUbigeoPkDpto;
	private Integer intParaUbigeoPkProvincia;
	private Integer intParaUbigeoPkDistrito;
	private Timestamp tsFechaEliminacion;
	private Boolean	chkDir;
	
	private Persona persona;
	private Ubigeo 	ubigeo;
	private Archivo croquis;

	private	Integer	intParaTipoArchivo;
	private	Integer	intParaItemArchivo;
	private	Integer	intParaItemHistorico;
	
	private String	strUbigeoDepartamento;
	private String	strUbigeoProvincia;
	private String	strUbigeoDistrito;
	private	String	strEtiqueta;	
	
	private	Archivo	archivo;
	
	
	public DomicilioPK getId() {
		return id;
	}

	public void setId(DomicilioPK id) {
		this.id = id;
	}

	public Integer getIntEnviaCorrespondencia() {
		return intEnviaCorrespondencia;
	}

	public void setIntEnviaCorrespondencia(Integer intEnviaCorrespondencia) {
		this.intEnviaCorrespondencia = intEnviaCorrespondencia;
	}

	public Boolean getFgCorrespondencia() {
		return fgCorrespondencia;
	}

	public void setFgCorrespondencia(Boolean fgCorrespondencia) {
		this.fgCorrespondencia = fgCorrespondencia;
	}

	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}

	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}

	public String getStrInterior() {
		return strInterior;
	}

	public void setStrInterior(String strInterior) {
		this.strInterior = strInterior;
	}

	public String getStrNombreVia() {
		return strNombreVia;
	}

	public void setStrNombreVia(String strNombreVia) {
		this.strNombreVia = strNombreVia;
	}

	public Integer getIntNumeroVia() {
		return intNumeroVia;
	}

	public void setIntNumeroVia(Integer intNumeroVia) {
		this.intNumeroVia = intNumeroVia;
	}

	public Integer getIntInterior() {
		return intInterior;
	}

	public void setIntInterior(Integer intInterior) {
		this.intInterior = intInterior;
	}

	public String getStrObservacion() {
		return strObservacion;
	}

	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}

	public String getStrReferencia() {
		return strReferencia;
	}

	public void setStrReferencia(String strReferencia) {
		this.strReferencia = strReferencia;
	}

	public Boolean getFgCroquis() {
		return fgCroquis;
	}

	public void setFgCroquis(Boolean fgCroquis) {
		this.fgCroquis = fgCroquis;
	}
	/*
	public String getStrRutaCroquis() {
		return strRutaCroquis;
	}
	public void setStrRutaCroquis(String strRutaCroquis) {
		this.strRutaCroquis = strRutaCroquis;
	}*/

	public String getStrNombreZona() {
		return strNombreZona;
	}

	public void setStrNombreZona(String strNombreZona) {
		this.strNombreZona = strNombreZona;
	}

	public String getStrDireccion() {
		return strDireccion;
	}

	public void setStrDireccion(String strDireccion) {
		this.strDireccion = strDireccion;
	}

	public Integer getIntIdDirUrl() {
		return intIdDirUrl;
	}

	public void setIntIdDirUrl(Integer intIdDirUrl) {
		this.intIdDirUrl = intIdDirUrl;
	}

	public String getStrDirUrl() {
		return strDirUrl;
	}

	public void setStrDirUrl(String strDirUrl) {
		this.strDirUrl = strDirUrl;
	}

	public Integer getIntTipoDireccionCod() {
		return intTipoDireccionCod;
	}

	public void setIntTipoDireccionCod(Integer intTipoDireccionCod) {
		this.intTipoDireccionCod = intTipoDireccionCod;
	}

	public Integer getIntTipoDomicilioCod() {
		return intTipoDomicilioCod;
	}

	public void setIntTipoDomicilioCod(Integer intTipoDomicilioCod) {
		this.intTipoDomicilioCod = intTipoDomicilioCod;
	}

	public Integer getIntTipoViaCod() {
		return intTipoViaCod;
	}

	public void setIntTipoViaCod(Integer intTipoViaCod) {
		this.intTipoViaCod = intTipoViaCod;
	}

	public Integer getIntTipoViviendaCod() {
		return intTipoViviendaCod;
	}

	public void setIntTipoViviendaCod(Integer intTipoViviendaCod) {
		this.intTipoViviendaCod = intTipoViviendaCod;
	}

	public Integer getIntTipoZonaCod() {
		return intTipoZonaCod;
	}

	public void setIntTipoZonaCod(Integer intTipoZonaCod) {
		this.intTipoZonaCod = intTipoZonaCod;
	}

	public Integer getIntParaUbigeoPk() {
		return intParaUbigeoPk;
	}

	public void setIntParaUbigeoPk(Integer intParaUbigeoPk) {
		this.intParaUbigeoPk = intParaUbigeoPk;
	}

	public Integer getIntParaUbigeoPkDpto() {
		return intParaUbigeoPkDpto;
	}

	public void setIntParaUbigeoPkDpto(Integer intParaUbigeoPkDpto) {
		this.intParaUbigeoPkDpto = intParaUbigeoPkDpto;
	}

	public Integer getIntParaUbigeoPkProvincia() {
		return intParaUbigeoPkProvincia;
	}

	public void setIntParaUbigeoPkProvincia(Integer intParaUbigeoPkProvincia) {
		this.intParaUbigeoPkProvincia = intParaUbigeoPkProvincia;
	}

	public Integer getIntParaUbigeoPkDistrito() {
		return intParaUbigeoPkDistrito;
	}

	public void setIntParaUbigeoPkDistrito(Integer intParaUbigeoPkDistrito) {
		this.intParaUbigeoPkDistrito = intParaUbigeoPkDistrito;
	}

	public Boolean getChkDir() {
		return chkDir;
	}

	public void setChkDir(Boolean chkDir) {
		this.chkDir = chkDir;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}

	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}

	public Ubigeo getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
	}
	public Archivo getCroquis() {
		return croquis;
	}

	public void setCroquis(Archivo croquis) {
		this.croquis = croquis;
	}
	
	public Integer getIntParaTipoArchivo() {
		return intParaTipoArchivo;
	}
	public void setIntParaTipoArchivo(Integer intParaTipoArchivo) {
		this.intParaTipoArchivo = intParaTipoArchivo;
	}
	public Integer getIntParaItemArchivo() {
		return intParaItemArchivo;
	}
	public void setIntParaItemArchivo(Integer intParaItemArchivo) {
		this.intParaItemArchivo = intParaItemArchivo;
	}
	public Integer getIntParaItemHistorico() {
		return intParaItemHistorico;
	}
	public void setIntParaItemHistorico(Integer intParaItemHistorico) {
		this.intParaItemHistorico = intParaItemHistorico;
	}
	public Archivo getArchivo() {
		return archivo;
	}
	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}
	public String getStrUbigeoDepartamento() {
		return strUbigeoDepartamento;
	}
	public void setStrUbigeoDepartamento(String strUbigeoDepartamento) {
		this.strUbigeoDepartamento = strUbigeoDepartamento;
	}
	public String getStrUbigeoProvincia() {
		return strUbigeoProvincia;
	}
	public void setStrUbigeoProvincia(String strUbigeoProvincia) {
		this.strUbigeoProvincia = strUbigeoProvincia;
	}
	public String getStrUbigeoDistrito() {
		return strUbigeoDistrito;
	}
	public void setStrUbigeoDistrito(String strUbigeoDistrito) {
		this.strUbigeoDistrito = strUbigeoDistrito;
	}
	public String getStrEtiqueta() {
		return strEtiqueta;
	}
	public void setStrEtiqueta(String strEtiqueta) {
		this.strEtiqueta = strEtiqueta;
	}

	@Override
	public String toString() {
		return "Domicilio [id=" + id + ", intEnviaCorrespondencia="
				+ intEnviaCorrespondencia + ", fgCorrespondencia="
				+ fgCorrespondencia + ", intEstadoCod=" + intEstadoCod
				+ ", strInterior=" + strInterior + ", strNombreVia="
				+ strNombreVia + ", intNumeroVia=" + intNumeroVia
				+ ", intInterior=" + intInterior + ", strObservacion="
				+ strObservacion + ", strReferencia=" + strReferencia
				+ ", strNombreZona=" + strNombreZona + ", strDireccion="
				+ strDireccion + ", intIdDirUrl=" + intIdDirUrl
				+ ", strDirUrl=" + strDirUrl + ", intTipoDireccionCod="
				+ intTipoDireccionCod + ", intTipoDomicilioCod="
				+ intTipoDomicilioCod + ", intTipoViaCod=" + intTipoViaCod
				+ ", intTipoViviendaCod=" + intTipoViviendaCod
				+ ", intTipoZonaCod=" + intTipoZonaCod + ", intParaUbigeoPk="
				+ intParaUbigeoPk + ", intParaUbigeoPkDpto="
				+ intParaUbigeoPkDpto + ", intParaUbigeoPkProvincia="
				+ intParaUbigeoPkProvincia + ", intParaUbigeoPkDistrito="
				+ intParaUbigeoPkDistrito + ", intParaTipoArchivo="
				+ intParaTipoArchivo + ", intParaItemArchivo="
				+ intParaItemArchivo + ", intParaItemHistorico="
				+ intParaItemHistorico + "]";
	}	
}