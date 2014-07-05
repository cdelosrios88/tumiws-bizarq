package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.composite.ConvenioComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaPlaneamientoComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AdendaDao extends TumiDao{
	public Adenda grabar(Adenda o) throws DAOException;
	public Adenda modificar(Adenda o) throws DAOException;
	public List<Adenda> getListaAdendaPorPK(Object o) throws DAOException;
	public List<HojaPlaneamientoComp> getListaParaFiltro(Object o) throws DAOException;
	public Adenda eliminarAdenda(Adenda o) throws DAOException;
	public Adenda aprobarRechazarAdenda(Adenda o) throws DAOException;
	public List<ConvenioComp> getListaConvenio(Object o) throws DAOException;
	public List<ConvenioComp> getListaConvenioDet(Object o) throws DAOException;
	public List<ConvenioComp> getListaConvenioPorTipoConvenio(Object o) throws DAOException;
	public Adenda grabarConvenio(Adenda o) throws DAOException;
	public Adenda modificarConvenio(Adenda o) throws DAOException;
}
