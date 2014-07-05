package pe.com.tumi.administracion.domain;

import pe.com.tumi.common.domain.TipoBase;

public class Parametro extends TipoBase {
	
	private String valor;
	private Long idSis;
	private String sistemaDescripcion;
	
	public Long getId() {
		return super.getId();
	}

	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setIdSis(Long idSis) {
		this.idSis = idSis;
	}

	public Long getIdSis() {
		return idSis;
	}

	public void setSistemaDescripcion(String sistemaDescripcion) {
		this.sistemaDescripcion = sistemaDescripcion;
	}

	public String getSistemaDescripcion() {
		return sistemaDescripcion;
	}
	
}
