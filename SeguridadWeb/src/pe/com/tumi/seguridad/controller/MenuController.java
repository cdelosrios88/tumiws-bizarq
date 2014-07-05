package pe.com.tumi.seguridad.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.component.state.TreeState;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNodeImpl;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.seguridad.domain.OpcionMenu;
import pe.com.tumi.seguridad.domain.Usuario;
import pe.com.tumi.seguridad.service.TipoArbolService;

public class MenuController extends GenericController {
	private TreeNodeImpl rootNode;
	private Long lastSelected;
	private TreeState treeState;
	private List menu;
	private HashMap constantes = new HashMap();
	private Set ocpionesMenu ;
	private TipoArbolService tipoArbolService;

	public MenuController(){
	}
	
	public void init(Long sistema){
		Usuario usuario = (Usuario)getSpringBean(Constante.SESSION_USER);
		List tree;
		try {
			tree = getTipoArbolService().getTreeByUser(usuario.getId(), sistema);
			ocpionesMenu = new HashSet();
			Iterator treeIt = tree.iterator();
			
			while(treeIt.hasNext()) {
				OpcionMenu opcionMenu = (OpcionMenu)treeIt.next();
				ocpionesMenu.add(opcionMenu);
			}
			List firstLevelNodes = getTipoArbolService().getNodes(null);
			rootNode = new TreeNodeImpl();		
			TreeNodeImpl child = null;
			Iterator arbol = firstLevelNodes.iterator();
			while(arbol.hasNext()) {
				Object o = arbol.next();
				if(ocpionesMenu.contains(o)){
					child = new TreeNodeImpl();
					child.setData(o);
					child = addNodes((OpcionMenu)o, child);
					rootNode.addChild(((OpcionMenu)o).getId(), child);
				}
			}
		} catch (DaoException e) {
			log.debug(e.getMessage());
			setCatchError(e);
		}
	}

	public void selectNode(NodeSelectedEvent event) throws IOException{
		HtmlTree tree = (HtmlTree)event.getComponent().getParent();
		OpcionMenu tipoArbol = (OpcionMenu)tree.getRowData();
		setLastSelected(tipoArbol.getId());
		tree.setSelected();
		if (tipoArbol.getController() != null && !tipoArbol.getController().trim().equals("")){
			Object bean = getSpringBean(tipoArbol.getController());
			Method method;
			try {
				method = bean.getClass().getMethod(tipoArbol.getInitMethod(),null);
				method.invoke(bean, null);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		forward(tipoArbol.getNavigationRule());
	}

	private TreeNodeImpl addNodes(OpcionMenu opcion, TreeNodeImpl parentNode) {
		try {
			opcion.setDescendientes(getTipoArbolService().getNodes(opcion.getId()));
			List opciones = (List)opcion.getDescendientes();
			TreeNodeImpl child = null;
			Iterator nodo = opciones.iterator();
			while(nodo.hasNext()){
				Object o = nodo.next();
				if(ocpionesMenu.contains(o)){
					child = new TreeNodeImpl();
					child.setData(o);
					parentNode.addChild(((OpcionMenu)o).getId(), child);
					child = addNodes(((OpcionMenu)o),child);
				}			
			}
			return parentNode;
		} catch (DaoException e) {
			log.debug(e.getMessage());
			setCatchError(e);
			return null;
		}
	}
	
	public TreeNodeImpl getRootNode() {
		return rootNode;
	}
	
	public void setRootNode(TreeNodeImpl rootNode) {
		this.rootNode = rootNode;
	}

	public void setLastSelected(Long lastSelected) {
		this.lastSelected = lastSelected;
	}

	public Long getLastSelected() {
		return lastSelected;
	}

	public TreeState getTreeState() {
		return treeState;
	}

	public void setTreeState(TreeState treeState) {
		this.treeState = treeState;
	}
	
	public List getMenu() {
		return menu;
	}

	public void setMenu(List menu) {
		this.menu = menu;
	}
	
	public HashMap getConstantes() {
		return constantes;
	}

	public void setConstantes(HashMap constantes) {
		this.constantes = constantes;
	}

	public void setTipoArbolService(TipoArbolService tipoArbolService) {
		this.tipoArbolService = tipoArbolService;
	}

	public TipoArbolService getTipoArbolService() {
		return tipoArbolService;
	}

}