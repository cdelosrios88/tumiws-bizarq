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
		<a4j:commandButton value="Grabar" actionListener="#{adminMenuController.validarSubMenu}" disabled="true"
     		styleClass="btnEstilos" reRender="frmAgregarSubMenu"/>
	</td>
	<td colspan="2">
		<a4j:commandButton value="Cancelar" actionListener="#{adminMenuController.cancelarSubMenu}"
     		oncomplete="Richfaces.hideModalPanel('pConsultarSubMenu')" 
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
    	<h:selectOneMenu value="#{adminMenuController.subMenu.intOrden}" disabled="true">
    		<tumih:selectItems var="sel" value="#{adminMenuController.listaTablaOrden}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
    	</h:selectOneMenu>
    </td>
</tr>

<tr>
	<td colspan="2"><h:outputText value="Nivel :" /></td>
	<td colspan="2" align="left">
		<h:inputText value="#{adminMenuController.subMenu.intNivel}" size="3" disabled="true"/>
	</td>
	<td>
		<a4j:commandLink actionListener="#{adminMenuController.checkSubMenuFinal}" disabled="true" ajaxSingle="true" reRender="frmAgregarSubMenu">
			<h:panelGroup rendered="#{empty adminMenuController.subMenu.intFinal || adminMenuController.subMenu.intFinal == 0}">	
				<input type="checkbox"/>
			</h:panelGroup>
			<h:panelGroup rendered="#{not empty adminMenuController.subMenu.intFinal && adminMenuController.subMenu.intFinal == 1}">	
				<input type="checkbox" checked="checked" />
			</h:panelGroup>
			<f:param name="pIntFinal"  value="#{adminMenuController.subMenu.intFinal}" />
		</a4j:commandLink>
			
	</td>
	<td colspan="2"><h:outputText value="Final"/></td>
	<td colspan="7"></td>
</tr>

<tr>
	<td colspan="2"><h:outputText value="Nombre :" /></td>
	<td colspan="12" align="left">
		<h:inputText value="#{adminMenuController.subMenu.strNombre}"size="60" disabled="true"/>
	</td>
</tr>

</table>
</h:column>
</h:panelGrid>