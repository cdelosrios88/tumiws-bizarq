package pe.com.tumi.persona.empresa.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.empresa.domain.Juridica;

public interface JuridicaDao extends TumiDao{
	public Juridica grabar(Juridica o) throws DAOException;
	public Juridica modificar(Juridica o) throws DAOException;
	public List<Juridica> getListaJuridicaPorPK(Object o) throws DAOException;
	public List<Juridica> getListaJuridicaPorInPk(Object o) throws DAOException;
	public List<Juridica> getListaJuridicaPorInPkLikeRazon(Object o) throws DAOException;
	public List<Juridica> getListaJuridicaDeEmpresa(Object o) throws DAOException;
	public List<Juridica> getJuridicaBusqueda(Object o) throws DAOException;
	public List<Juridica> getListaJuridicaPorRazonSocial(Object o) throws DAOException;
	public List<Juridica> getListaJuridicaPorNombreComercial(Object o) throws DAOException;
	//flyalico
	public List<Juridica> listaJuridicaWithFile(Object o) throws DAOException;
}
