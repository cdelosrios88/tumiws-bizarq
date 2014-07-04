package pe.com.tumi.persona.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

import java.util.List;

public class PersonaEmpresa extends TumiDomain{

	private PersonaEmpresaPK id;
	private PersonaRol personaRol;
	private List<PersonaRol> listaPersonaRol;
	private List<Vinculo> listaVinculo;
	private Vinculo vinculo;
	
	private Persona persona;
	
	public PersonaEmpresa(){
		
	}

	public PersonaEmpresa(PersonaEmpresaPK id) {
		super();
		this.id = id;
	}
	
	public PersonaEmpresa(Integer pIntIdEmpresa, Integer pIntIdPersona) {
		this.id = new PersonaEmpresaPK(pIntIdEmpresa, pIntIdPersona);
	}

	public PersonaEmpresaPK getId() {
		return id;
	}
	public void setId(PersonaEmpresaPK id) {
		this.id = id;
	}
	public List<PersonaRol> getListaPersonaRol() {
		return listaPersonaRol;
	}
	public void setListaPersonaRol(List<PersonaRol> listaPersonaRol) {
		this.listaPersonaRol = listaPersonaRol;
	}
	public List<Vinculo> getListaVinculo() {
		return listaVinculo;
	}
	public void setListaVinculo(List<Vinculo> listaVinculo) {
		this.listaVinculo = listaVinculo;
	}
	public Vinculo getVinculo() {
		return vinculo;
	}
	public void setVinculo(Vinculo vinculo) {
		this.vinculo = vinculo;
	}
	public PersonaRol getPersonaRol() {
		return personaRol;
	}
	public void setPersonaRol(PersonaRol personaRol) {
		this.personaRol = personaRol;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "PersonaEmpresa [id=" + id + "]";
	}
}