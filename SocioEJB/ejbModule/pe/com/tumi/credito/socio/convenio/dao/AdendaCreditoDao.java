package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.AdendaCaptacion;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCredito;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AdendaCreditoDao extends TumiDao{
	public AdendaCredito grabar(AdendaCredito o) throws DAOException;
	public AdendaCredito modificar(AdendaCredito o) throws DAOException;
	public List<AdendaCredito> getListaAdendaCreditoPorPK(Object o) throws DAOException;
	public List<AdendaCredito> getListaAdendaCreditoPorPKAdenda(Object o) throws DAOException;
}