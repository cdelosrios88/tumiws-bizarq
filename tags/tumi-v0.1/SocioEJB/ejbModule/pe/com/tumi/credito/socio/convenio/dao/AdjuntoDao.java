package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.Adjunto;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AdjuntoDao extends TumiDao{
	public Adjunto grabar(Adjunto o) throws DAOException;
	public Adjunto modificar(Adjunto o) throws DAOException;
	public List<Adjunto> getListaAdjuntoPorPK(Object o) throws DAOException;
	public List<Adjunto> getListaAdjuntoPorPKAdenda(Object o) throws DAOException;
}
