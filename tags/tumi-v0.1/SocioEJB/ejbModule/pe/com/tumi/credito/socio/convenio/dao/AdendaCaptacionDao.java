package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.AdendaCaptacion;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCredito;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AdendaCaptacionDao extends TumiDao{
	public AdendaCaptacion grabar(AdendaCaptacion o) throws DAOException;
	public AdendaCaptacion modificar(AdendaCaptacion o) throws DAOException;
	public List<AdendaCaptacion> getListaAdendaCaptacionPorPK(Object o) throws DAOException;
	public List<AdendaCaptacion> getListaAdendaCaptacionPorPKAdenda(Object o) throws DAOException;
}
