package pe.com.tumi.persona.vinculo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

public interface VinculoDao extends TumiDao{
	public Vinculo grabar(Vinculo o) throws DAOException;
	public Vinculo modificar(Vinculo o) throws DAOException;
	public List<Vinculo> getListaVinculoPorPK(Object o) throws DAOException;
	public List<Vinculo> getListaVinculoPorPKPersonaEmpresa(Object o) throws DAOException;
	public List<Vinculo> getListaPorPKPersonaEmpresaYTipoVinculo(Object o) throws DAOException;
	public List<Vinculo> getListaVinculoPorPKPersEmpresaYPkPersona(Object o) throws DAOException;
}
