package pe.com.tumi.riesgo.test.controller;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;

public class TestController {

	private Configuracion dto;

	public TestController() {
		if(dto== null)
			dto = new Configuracion();
		dto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	}

	public Configuracion getDto() {
		return dto;
	}
	public void setDto(Configuracion dto) {
		this.dto = dto;
	}
	
}
