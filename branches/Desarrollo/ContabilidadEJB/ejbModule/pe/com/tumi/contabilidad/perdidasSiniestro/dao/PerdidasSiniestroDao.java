package pe.com.tumi.contabilidad.perdidasSiniestro.dao;

import java.util.List;

import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface PerdidasSiniestroDao extends TumiDao{ 
	public PerdidasSiniestro grabarPerdidas(PerdidasSiniestro o) throws DAOException;
	public PerdidasSiniestro modificarPerdidas(PerdidasSiniestro o) throws DAOException;
	public List<PerdidasSiniestro> getListaJuridicaRuc(Object o) throws DAOException;
	public List<PerdidasSiniestro> getListaBuscar(Object o) throws DAOException;
}
