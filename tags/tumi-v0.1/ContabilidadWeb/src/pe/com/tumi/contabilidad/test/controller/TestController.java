package pe.com.tumi.contabilidad.test.controller;

import pe.com.tumi.contabilidad.core.domain.PlanCuenta;

public class TestController {

	private PlanCuenta dto;

	public TestController() {
		if(dto== null)
			dto = new PlanCuenta();
		dto.setStrComentario("esto es una prueba");
	}

	public PlanCuenta getDto() {
		return dto;
	}
	public void setDto(PlanCuenta dto) {
		this.dto = dto;
	}
	
}
