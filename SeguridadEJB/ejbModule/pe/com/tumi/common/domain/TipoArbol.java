package pe.com.tumi.common.domain;

import java.util.ArrayList;
import java.util.List;

public class TipoArbol extends TipoBase {

	private Long superior;
	private List descendientes = new ArrayList();

	public Long getId() {
		return super.getId();
	}
	
	public Long getSuperior() {
		return superior;
	}

	public void setSuperior(Long superior) {
		this.superior = superior;
	}	

	public List getDescendientes() {
		return descendientes;
	}

	public void setDescendientes(List descendientes) {
		this.descendientes = descendientes;
	}

	public int hashCode() {
		final int prime = 10;
		int result = 0;
		if (getId() != null) {
			result = getId().hashCode() * prime;
		}
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		TipoArbol nodo = (TipoArbol) obj;

		if (getId() == null) {
			if (nodo.getId() != null)
				return false;
		} else if (!getId().equals(nodo.getId()))
			return false;

		return true;
	}
}
