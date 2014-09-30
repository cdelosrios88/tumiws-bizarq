/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripci�n
* -----------------------------------------------------------------------------------------------------------
* REQ14-004       			27/09/2014     Christian De los R�os        Se agregan nuevos m�todos para regularizar la mayorizaci�n         
*/
package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface LibroMayorDao extends TumiDao{

	public LibroMayor grabar(LibroMayor o) throws DAOException;
	public LibroMayor modificar(LibroMayor o) throws DAOException;
	public List<LibroMayor> getListaPorPk(Object o) throws DAOException;
	public List<LibroMayor> getListaPorBuscar(Object o) throws DAOException;
	//Inicio: REQ14-004 - bizarq - 16/09/2014
	public List<LibroMayor> getListMayorHist(Object o) throws DAOException;
	public Integer processMayorizacion(Object o) throws DAOException;
	public List<LibroMayor> getListAfterProcessedMayorizado(Object o) throws DAOException;
	public LibroMayor eliminarMayorizado(LibroMayor o) throws DAOException;
	//Fin: REQ14-004 - bizarq - 16/09/2014
}
