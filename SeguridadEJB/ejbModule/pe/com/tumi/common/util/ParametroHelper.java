package pe.com.tumi.common.util;

import org.apache.log4j.Logger;

import pe.com.tumi.administracion.domain.Parametro;
import pe.com.tumi.common.dao.GenericDao;

public class ParametroHelper {
	protected static Logger log = Logger.getLogger(ParametroHelper.class);
	private GenericDao parametroDAO;
	
	public String getParametro(String codigo, Long sis) throws DaoException{
		String resultado = null;
		Parametro parametro = new Parametro();
		Parametro tmpParam = new Parametro();
		tmpParam.setCodigo(codigo);
		tmpParam.setIdSis(sis);
		try {
			parametro = (Parametro)getParametroDAO().findByObject(tmpParam).get(0);
			if (parametro!=null){
				resultado = parametro.getValor();
			}
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(e);	
		}
		return resultado;
	}

	public String getParametro1(String codigo){
		String resultado = null;
		Parametro parametro = new Parametro();
		Parametro tmpParam = new Parametro();
		tmpParam.setCodigo(codigo);
		try {
			parametro = (Parametro)getParametroDAO().findByObject(tmpParam).get(0);
			if (parametro!=null){
				resultado = parametro.getValor();
			}
		} catch (DaoException e) {
			log.debug(e);
			e.printStackTrace();
		}
		return resultado;
	}
	
	public void setParametroDAO(GenericDao parametroDAO) {
		this.parametroDAO = parametroDAO;
	}

	public GenericDao getParametroDAO() {
		return (GenericDao) parametroDAO;
	}
	
}
