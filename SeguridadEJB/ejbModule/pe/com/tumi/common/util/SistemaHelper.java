package pe.com.tumi.common.util;

import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import pe.com.tumi.common.dao.GenericDao;

public class SistemaHelper{

	protected static Logger log = Logger.getLogger(SistemaHelper.class);
	private List listaSistema;
	private GenericDao sistemaDAO;
	
	/**Devuelve una lista de SelectItems con todos los sistemas registados**/
	public List  getListaSistema() throws DaoException{
		try{
			listaSistema = sistemaDAO.findAll();
			listaSistema = ItemHelper.listToListOfSelectItems(listaSistema, "id" , "codigo");
			SelectItem opcionSeleccionar = new SelectItem(Constante.NO_SELECTED,Constante.OPTION_SELECT);
			listaSistema.add(opcionSeleccionar);
			listaSistema = ItemHelper.sortSelectItemListByName(listaSistema);
			return listaSistema;
		}catch(DaoException e){
			throw new DaoException(e);
		}
	}

	public void setListaSistema(List listaSistema) {
		this.listaSistema = listaSistema;
	}

	public void setSistemaDAO(GenericDao sistemaDAO) {
		this.sistemaDAO = sistemaDAO;
	}

	public GenericDao getSistemaDAO() {
		return sistemaDAO;
	}

}
