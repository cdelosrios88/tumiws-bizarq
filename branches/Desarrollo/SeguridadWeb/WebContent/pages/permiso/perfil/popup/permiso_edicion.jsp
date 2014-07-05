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
		<a4j:commandButton value="Grabar" actionListener="#{usuarioPerfilController.validarPermiso}"
     		styleClass="btnEstilos" reRender="idFormPopupPermisoEdicion"/>
     	
     	<h:panelGroup rendered="#{not empty usuarioPerfilController.esPopPupValido && usuarioPerfilController.esPopPupValido}"> 	
	     	<h:panelGroup rendered="#{!usuarioPerfilController.permiso.persiste}">
	     	<a4j:commandButton id="btnGrabarPopup" actionListener="#{usuarioPerfilController.agregarPermiso}"
	     		oncomplete="Richfaces.hideModalPanel('pPermisoEdicion')" style="display:none;" reRender="pgMantenimiento"/>
			<script>
				accionSubMenu('idFormPopupPermisoEdicion:idPopupPermisoEdicion:btnGrabarPopup');
			</script>
			</h:panelGroup>
			
		    <h:panelGroup rendered="#{usuarioPerfilController.permiso.persiste}">
		    <a4j:commandButton id="btnModificarPopup" actionListener="#{usuarioPerfilController.modificarPermiso}"
	     		oncomplete="Richfaces.hideModalPanel('pPermisoEdicion')" style="display:none;" reRender="pgMantenimiento"/>
			<script>
				accionSubMenu('idFormPopupPermisoEdicion:idPopupPermisoEdicion:btnModificarPopup');
			</script>
			</h:panelGroup>
     	</h:panelGroup>	
	</td>
	<td colspan="2">
		<a4j:commandButton value="Cancelar" actionListener="#{usuarioPerfilController.cancelarPermiso}"
     		oncomplete="Richfaces.hideModalPanel('pPermisoEdicion')" 
     		styleClass="btnEstilos" reRender="pgMenu"/>
    </td>
    <td colspan="10"></td>   		
</tr>

<tr>
	<td colspan="2"><h:outputText value="Menu :" /></td>
	<td colspan="12"></td>
</tr>

<tr>
	<td colspan="2"><h:outputText value="Código :" /></td>
	<td colspan="2" align="left">
		<h:inputText value="#{usuarioPerfilController.permiso.transaccion.id.intIdTransaccion}" disabled="true" size="14" style="padding-right:24px;"/>
	</td>
	<td colspan="2"><h:outputText value="Crecimiento :" /></td>
	<td colspan="2">
		<h:selectOneMenu value="#{usuarioPerfilController.permiso.transaccion.intCrecimiento}" disabled="true">
            <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CRECIMIENTOMENU}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
        </h:selectOneMenu>
    </td>
    <td colspan="2"><h:outputText value="Orden :" /></td>
    <td colspan="2">
    	<h:selectOneMenu value="#{usuarioPerfilController.permiso.transaccion.intOrden}" disabled="true">
    		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ORDENMENU}"
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
    	</h:selectOneMenu>
    </td>
</tr>

<tr>
	<td colspan="2"><h:outputText value="Nivel :" /></td>
	<td colspan="2" align="left">
		<h:inputText value="#{usuarioPerfilController.permiso.transaccion.intNivel}" size="3" disabled="true"/>
	</td>
	<td>
		<h:panelGroup rendered="#{empty usuarioPerfilController.permiso.transaccion.intFinal || usuarioPerfilController.permiso.transaccion.intFinal == 0}">	
			<input type="checkbox" disabled="disabled"/>
		</h:panelGroup>
		<h:panelGroup rendered="#{not empty usuarioPerfilController.permiso.transaccion.intFinal && usuarioPerfilController.permiso.transaccion.intFinal == 1}">	
			<input type="checkbox" checked="checked" disabled="disabled"/>
		</h:panelGroup>
	</td>
	<td colspan="2"><h:outputText value="Final"/></td>
	<td colspan="7"></td>
</tr>
	
<tr>
	<td colspan="2"><h:outputText value="Nombre :" /></td>
	<td colspan="12" align="left">
		<h:inputText value="#{usuarioPerfilController.permiso.transaccion.strNombre}"size="60" disabled="true"/>
	</td>
</tr>

<tr><td colspan="14">&nbsp;</td></tr>

<tr>
	<td colspan="2"><h:outputText value="Permiso" /></td>
	<td colspan="12"></td>
</tr>

<tr>
	<td colspan="2"><h:outputText value="Estado :" /></td>
	<td colspan="12" align="left">
		<h:selectOneMenu value="#{usuarioPerfilController.permiso.intIdEstado}">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
    		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
    	</h:selectOneMenu>			
	</td>
</tr>

<h:panelGroup rendered="#{not empty usuarioPerfilController.msgPerfil.msgPermiso.idEstado}">
<tr>
	<td colspan="2"></td>
	<td colspan="12">
		<h:outputText value="#{usuarioPerfilController.msgPerfil.msgPermiso.idEstado}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgPerfil.msgPermiso.idEstado}"/>
	</td>
</tr>
</h:panelGroup>

</table>
</h:column>
</h:panelGrid>