package pe.com.tumi.common.util;

import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import pe.com.tumi.common.dao.GenericDao;

public class CertificadoHelper {

	protected static Logger log = Logger.getLogger(SistemaHelper.class);
	private List listaMoneda;
	private GenericDao certificadoDAO;
	
	public List getListaMoneda() throws DaoException{
		try{
			listaMoneda = certificadoDAO.findAll();
			log.info("El tamaño de lista es CERTIFICADO HELPER " +listaMoneda.size());
			listaMoneda = ItemHelper.listToListOfSelectItems(listaMoneda, "id" , "moneda");
			SelectItem opcionSeleccionar = new SelectItem(Constante.NO_SELECTED,Constante.OPTION_SELECT);
			listaMoneda.add(opcionSeleccionar);
			listaMoneda = ItemHelper.sortSelectItemListByName(listaMoneda);
			return listaMoneda;
		}catch(DaoException e){
			throw new DaoException(e);
		}
	}

	public void setListaMoneda(List listaMoneda) {
		this.listaMoneda = listaMoneda;
	}

	public GenericDao getCertificadoDAO() {
		return certificadoDAO;
	}

	public void setCertificadoDAO(GenericDao certificadoDAO) {
		this.certificadoDAO = certificadoDAO;
	}

}
