package pe.com.tumi.contabilidad.reclamosDevoluciones.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.contabilidad.reclamosDevoluciones.domain.ReclamosDevoluciones;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface ReclamosDevolucionesFacadeLocal {
	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 08.08.2014 /
	public ReclamosDevoluciones grabarReclamos(ReclamosDevoluciones o)throws BusinessException;
	public ReclamosDevoluciones modificarReclamos(ReclamosDevoluciones o)throws BusinessException;
	public List<ReclamosDevoluciones> getListaParametro(ReclamosDevoluciones o)throws BusinessException;
	public List<ReclamosDevoluciones> getBuscar(ReclamosDevoluciones o)throws BusinessException;
}
