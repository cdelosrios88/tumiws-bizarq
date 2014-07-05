<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : PROTOTIPO COMUNICACION   -->
	<!-- Fecha     : 25/01/2012               -->

<script language="JavaScript" type="text/javascript">
function accionSubMenu(pIdBoton){
	lControl = document.getElementById(pIdBoton);
	if(lControl){
	 	lControl.click();
	}
}
</script>

<h:panelGrid columns="1" style="border:0px">
<h:column>
<table border="0">
<tr>
  	<td style="width:50px;"></td>
  	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
  	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
</tr>
<tr>
	<td colspan="2">
		<a4j:commandButton value="Grabar" actionListener="#{adminMenuController.validarSubMenu}"
     		styleClass="btnEstilos" reRender="frmAgregarSubMenu"/>
     	
     	<h:panelGroup rendered="#{not empty adminMenuController.esPopPupValido && adminMenuController.esPopPupValido}"> 	
	     	<h:panelGroup rendered="#{!adminMenuController.subMenu.persiste}">
	     	<a4j:commandButton id="btnGrabarSubMenu" actionListener="#{adminMenuController.agregarSubMenu}"
	     		oncomplete="Richfaces.hideModalPanel('pAgregarSubMenu')" style="display:none;" reRender="pgMenu"/>
			<script>
				accionSubMenu('frmAgregarSubMenu:incAgregarSubMenu:btnGrabarSubMenu');
			</script>
			</h:panelGroup>
			
		    <h:panelGroup rendered="#{adminMenuController.subMenu.persiste}">
		    <a4j:commandButton id="btnModificarSubMenu" actionListener="#{adminMenuController.modificarSubMenu}"
	     		oncomplete="Richfaces.hideModalPanel('pAgregarSubMenu')" style="display:none;" reRender="pgMenu"/>
			<script>
				accionSubMenu('frmAgregarSubMenu:incAgregarSubMenu:btnModificarSubMenu');
			</script>
			</h:panelGroup>
     	</h:panelGroup>	
	</td>
	<td colspan="2">
		<a4j:commandButton value="Cancelar" actionListener="#{adminMenuController.cancelarSubMenu}"
     		oncomplete="Richfaces.hideModalPanel('pAgregarSubMenu')" 
     		styleClass="btnEstilos" reRender="pgMenu"/>
    </td>
    <td colspan="10"></td>   		
</tr>
<tr>
	<td colspan="2"><h:outputText value="Código :" /></td>
	<td colspan="2" align="left">
		<h:inputText value="#{adminMenuController.subMenu.id.intIdTransaccion}" disabled="true" size="14" style="padding-right:24px;"/>
	</td>
	<td colspan="2"><h:outputText value="Crecimiento :" /></td>
	<td colspan="2">
		<h:selectOneMenu value="#{adminMenuController.subMenu.intCrecimiento}" disabled="true">
            <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CRECIMIENTOMENU}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
        </h:selectOneMenu>
    </td>
    <td colspan="2"><h:outputText value="Orden :" /></td>
    <td colspan="2">
    	<h:selectOneMenu value="#{adminMenuController.subMenu.intOrden}">
    		<tumih:selectItems var="sel" value="#{adminMenuController.listaTablaOrden}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
    	</h:selectOneMenu>
    </td>
</tr>

<h:panelGroup rendered="#{not empty adminMenuController.msgMenu.msgSubMenu.tipoCrecimiento	||
						  not empty adminMenuController.msgMenu.msgSubMenu.tipoOrden}">
<tr>
	<td colspan="2"></td>
	<td colspan="2"></td>
	<td colspan="2"></td>
	<td colspan="2">
		<h:outputText value="#{adminMenuController.msgMenu.msgSubMenu.tipoCrecimiento}" styleClass="msgError" 
		        	rendered="#{not empty adminMenuController.msgMenu.msgSubMenu.tipoCrecimiento}"/>
    </td>
    <td colspan="2"></td>
    <td colspan="2">
    	<h:outputText value="#{adminMenuController.msgMenu.msgSubMenu.tipoOrden}" styleClass="msgError" 
		        	rendered="#{not empty adminMenuController.msgMenu.msgSubMenu.tipoOrden}"/>
    </td>
</tr>
</h:panelGroup>

<tr>
	<td colspan="2"><h:outputText value="Nivel :" /></td>
	<td colspan="2" align="left">
		<h:inputText value="#{adminMenuController.subMenu.intNivel}" size="3" disabled="true"/>
	</td>
	<td>
		<h:panelGroup rendered="#{adminMenuController.subMenu.intCrecimiento == applicationScope.Constante.PARAM_T_CRECIMIENTOMENU_HORIZONTAL}">
		<a4j:commandLink actionListener="#{adminMenuController.checkSubMenuFinal}" disabled="true" ajaxSingle="true" reRender="frmAgregarSubMenu">
			<h:panelGroup rendered="#{empty adminMenuController.subMenu.intFinal || adminMenuController.subMenu.intFinal == 0}">	
				<input type="checkbox"/>
			</h:panelGroup>
			<h:panelGroup rendered="#{not empty adminMenuController.subMenu.intFinal && adminMenuController.subMenu.intFinal == 1}">	
				<input type="checkbox" checked="checked" />
			</h:panelGroup>
			<f:param name="pIntFinal"  value="#{adminMenuController.subMenu.intFinal}" />
		</a4j:commandLink>
		</h:panelGroup>
		
		<h:panelGroup rendered="#{adminMenuController.subMenu.intCrecimiento == applicationScope.Constante.PARAM_T_CRECIMIENTOMENU_VERTICAL}">
		<a4j:commandLink actionListener="#{adminMenuController.checkSubMenuFinal}" ajaxSingle="true" reRender="frmAgregarSubMenu">
			<h:panelGroup rendered="#{empty adminMenuController.subMenu.intFinal || adminMenuController.subMenu.intFinal == 0}">	
				<input type="checkbox"/>
			</h:panelGroup>
			<h:panelGroup rendered="#{not empty adminMenuController.subMenu.intFinal && adminMenuController.subMenu.intFinal == 1}">	
				<input type="checkbox" checked="checked" />
			</h:panelGroup>
			<f:param name="pIntFinal"  value="#{adminMenuController.subMenu.intFinal}" />
		</a4j:commandLink>
		</h:panelGroup>
	</td>
	<td colspan="2"><h:outputText value="Final"/></td>
	<td colspan="7"></td>
</tr>

<h:panelGroup rendered="#{not empty adminMenuController.msgMenu.msgSubMenu.nivel ||
						  not empty adminMenuController.msgMenu.msgSubMenu.tipoFinal}">
<tr>
	<td colspan="2"></td>
	<td colspan="2">
		<h:outputText value="#{adminMenuController.msgMenu.msgSubMenu.nivel}" styleClass="msgError" 
		        	rendered="#{not empty adminMenuController.msgMenu.msgSubMenu.nivel}"/>
	</td>
	<td colspan="3">
		<h:outputText value="#{adminMenuController.msgMenu.msgSubMenu.tipoFinal}" styleClass="msgError" 
		        	rendered="#{not empty adminMenuController.msgMenu.msgSubMenu.tipoFinal}"/>
	</td>
	<td colspan="7"></td>
</tr>
</h:panelGroup>
	
<tr>
	<td colspan="2"><h:outputText value="Nombre :" /></td>
	<td colspan="12" align="left">
		<h:inputText value="#{adminMenuController.subMenu.strNombre}"size="60"/>
	</td>
</tr>

<h:panelGroup rendered="#{not empty adminMenuController.msgMenu.msgSubMenu.nombre}">
<tr>
	<td colspan="2"></td>
	<td colspan="12" align="left">
		<h:outputText value="#{adminMenuController.msgMenu.msgSubMenu.nombre}" styleClass="msgError" 
		        	rendered="#{not empty adminMenuController.msgMenu.msgSubMenu.nombre}"/>
	</td>
</tr>
</h:panelGroup>

</table>
</h:column>
</h:panelGrid>