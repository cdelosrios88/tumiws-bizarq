package pe.com.tumi.presupuesto.test.controller;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class TestController {

	private Tabla dto;

	public TestController() {
		if(dto== null)
			dto = new Tabla();
		dto.setIntEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	}

	public Tabla getDto() {
		return dto;
	}
	public void setDto(Tabla dto) {
		this.dto = dto;
	}
	
}
