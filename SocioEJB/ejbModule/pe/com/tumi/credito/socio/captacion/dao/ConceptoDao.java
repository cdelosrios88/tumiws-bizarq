package pe.com.tumi.credito.socio.captacion.dao;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Concepto;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface ConceptoDao extends TumiDao{
	public Concepto grabar(Concepto o) throws DAOException;
	public Concepto modificar(Concepto o) throws DAOException;
	public List<Concepto> getListaConceptoPorPK(Object o) throws DAOException;
	public List<Concepto> getListaConceptoPorPKCaptacion(Object o) throws DAOException;
}
