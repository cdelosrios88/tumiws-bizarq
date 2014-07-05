<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Eyder Danier Uceda Lopez -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Solicitud de Cambio      -->			
	<!-- Fecha     : 19/08/2011               -->
<script language="JavaScript" type="text/javascript">
function irAgregarSubMenu(pIdBoton){
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
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
</tr>
<tr>
	<td colspan="2"><h:outputText value="Empresa :"/></td>
	<td colspan="6">
		<h:panelGroup rendered="#{adminMenuController.strTipoMantMenu == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
			<h:selectOneMenu required="true" value="#{adminMenuController.menu.id.intPersEmpresaPk}" style="width:300px;">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" value="#{adminMenuController.listaJuridicaEmpresa}"
								   itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonSocial}"/>
			</h:selectOneMenu>
		</h:panelGroup>
		<h:panelGroup rendered="#{adminMenuController.strTipoMantMenu == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
			<h:selectOneMenu required="true" value="#{adminMenuController.menu.id.intPersEmpresaPk}" style="width:300px;" disabled="true">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" value="#{adminMenuController.listaJuridicaEmpresa}"
								   itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonSocial}"/>
			</h:selectOneMenu>
		</h:panelGroup>
	</td>
	<td colspan="8"></td>
</tr>

<h:panelGroup rendered="#{not empty adminMenuController.msgMenu.idEmpresa}">
<tr>
	<td colspan="2"></td>
	<td colspan="6">
		<h:outputText value="#{adminMenuController.msgMenu.idEmpresa}" styleClass="msgError" 
		        	rendered="#{not empty adminMenuController.msgMenu.idEmpresa}"/>
	</td>
	<td colspan="8"></td>
</tr>
</h:panelGroup>

<tr>
	<td colspan="2"><h:outputText value="Tipo de Menu :"/></td>
	<td colspan="2">
		<h:panelGroup rendered="#{adminMenuController.strTipoMantMenu == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
			<h:selectOneMenu value="#{adminMenuController.menu.intTipoMenu}">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOAPLICACION}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			</h:selectOneMenu>
		</h:panelGroup>
		<h:panelGroup rendered="#{adminMenuController.strTipoMantMenu == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
			<h:selectOneMenu value="#{adminMenuController.menu.intTipoMenu}" disabled="true">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOAPLICACION}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			</h:selectOneMenu>
		</h:panelGroup>	
	</td>
	<td colspan="2"><h:outputText value="Estado :"/></td>
	<td colspan="2">
		<h:selectOneMenu value="#{adminMenuController.menu.intIdEstado}" disabled="true">
		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="8"></td>
</tr>

<h:panelGroup rendered="#{not empty adminMenuController.msgMenu.tipoMenu ||
						  not empty adminMenuController.msgMenu.idEstado}">
<tr>
	<td colspan="2"></td>
	<td colspan="2">
		<h:outputText value="#{adminMenuController.msgMenu.tipoMenu}" styleClass="msgError" 
		        	rendered="#{not empty adminMenuController.msgMenu.tipoMenu}"/>
	</td>
	<td colspan="2"></td>
	<td colspan="2">
	<h:outputText value="#{adminMenuController.msgMenu.idEstado}" styleClass="msgError" 
		        	rendered="#{not empty adminMenuController.msgMenu.idEstado}"/>
	</td>
	<td colspan="8"></td>
</tr>
</h:panelGroup>

<tr>
	<td colspan="16" align="left"><h:outputText value="Estructura del Menú"/></td>
</tr>

<h:panelGroup rendered="#{not empty adminMenuController.msgMenu.listaMenu}">
<tr>
	<td colspan="16" align="left">
		<h:outputText value="#{adminMenuController.msgMenu.listaMenu}" styleClass="msgError" 
		        	rendered="#{not empty adminMenuController.msgMenu.listaMenu}"/>
	</td>
</tr>
</h:panelGroup>

<h:panelGroup rendered="#{not empty adminMenuController.listaMenu}">
<a4j:repeat value="#{adminMenuController.listaMenu}" var="itemMenu" rowKeyVar="rowKeyMenu">
<h:panelGroup rendered="#{fn:length(itemMenu) != 0}">
<tr>
	<td colspan="2"><h:outputText value="Nivel #{rowKeyMenu + 1} :" /></td>
	<td colspan="2">
		<a4j:commandButton value="Agregar" actionListener="#{adminMenuController.irAgregarSubMenu}" reRender="frmAgregarSubMenu" styleClass="btnEstilos">
			<f:param name="pIntNivel"  value="#{rowKeyMenu + 1}" />
			<rich:componentControl for="pAgregarSubMenu" operation="show" event="onclick"/>      			
   		</a4j:commandButton>
	</td>
	<td colspan="8"></td>
</tr>
<a4j:repeat value="#{itemMenu}" var="itemSubMenu" rowKeyVar="rowKeySubMenu">
<h:panelGroup rendered="#{itemSubMenu.intIdEstado != applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
<tr>
	<td>
		<tumih:radioButton name="#{rowKeyMenu}" overrideName="true" 
				value="#{adminMenuController.listaSelectRadioMenu[rowKeyMenu]}" itemValue="#{rowKeySubMenu}">
			<a4j:support event="onClick" actionListener="#{adminMenuController.onclickRadioDeSubMenu}" reRender="pgMenu">
			<f:param name="pIndiceMenu"  value="#{rowKeyMenu}" />
			<f:param name="pIndiceSubMenu"  value="#{rowKeySubMenu}" />
			</a4j:support>
		</tumih:radioButton>										
	</td>
	<td colspan="5">
		<h:inputText value="#{itemSubMenu.strNombre}" style="width:250px;" disabled="true"/>
	</td>
	<td colspan="10"></td>
</tr>
<%--
<tr>
	<td colspan="5"><h:outputText value="#{(rowKeySubMenu + 1)}"/></td>
	<td colspan="5"><h:outputText value="#{adminMenuController.listaSubMenuActivo[rowKeyMenu]}"/></td>
</tr>
--%>
<h:panelGroup rendered="#{(rowKeySubMenu + 1) == adminMenuController.listaSubMenuActivo[rowKeyMenu]}">
<tr>
	<td colspan="5"></td>
	<td colspan="2">
		<h:panelGroup rendered="#{not empty itemSubMenu.checked && itemSubMenu.checked}">
		<a4j:commandButton value="Consultar" actionListener="#{adminMenuController.irConsultarSubMenu}" reRender="frmConsultarSubMenu" styleClass="btnEstilos">
			<f:param name="pIndiceMenu"  value="#{rowKeyMenu}" />
			<rich:componentControl for="pConsultarSubMenu" operation="show" event="onclick"/>      			
   		</a4j:commandButton>
   		</h:panelGroup>
   		<h:panelGroup rendered="#{empty itemSubMenu.checked || !itemSubMenu.checked}">
   		<a4j:commandButton value="Editar" actionListener="#{adminMenuController.irModificarSubMenu}" reRender="frmAgregarSubMenu" styleClass="btnEstilos">
			<f:param name="pIndiceMenu"  value="#{rowKeyMenu}" />
			<rich:componentControl for="pAgregarSubMenu" operation="show" event="onclick"/>      			
   		</a4j:commandButton>	
   		</h:panelGroup>
	</td>
	<td colspan="2">
		<a4j:commandButton value="Eliminar" actionListener="#{adminMenuController.eliminarSubMenu}" reRender="pgMenu" styleClass="btnEstilos">
			<f:param name="pIndiceMenu"  value="#{rowKeyMenu}" />
		</a4j:commandButton>
	</td>
	<td colspan="7"></td>
</tr>

<tr>
	<td colspan="16">&nbsp;</td>
</tr>

<h:panelGroup rendered="#{fn:length(adminMenuController.listaMenu) == rowKeyMenu + 1}">
<tr>
    <td colspan="2">
    	<h:outputText value="Nivel #{rowKeyMenu + 2}:" />
    </td>
	<td colspan="2">
		<a4j:commandButton value="Agregar" actionListener="#{adminMenuController.validarAgregarSubMenu}" reRender="pgMenu" styleClass="btnEstilos">
			<f:param name="pIntNivel"  value="#{rowKeyMenu + 2}" />
   		</a4j:commandButton>
	</td>
	<td colspan="12">&nbsp;</td>
</tr>
</h:panelGroup>

</h:panelGroup>
</h:panelGroup>

</a4j:repeat>
</h:panelGroup>

</a4j:repeat>

<tr>
    <td colspan="16">
	<a4j:commandButton id="btnIrAgregarSubMenu" actionListener="#{adminMenuController.irAgregarSubMenuInicial}" 
					   reRender="frmAgregarSubMenu" style="display:none;">
		<rich:componentControl for="pAgregarSubMenu" operation="show" event="onclick"/>      			
	</a4j:commandButton>
	
	<h:panelGroup rendered="#{not empty adminMenuController.esPopPupAccesible && adminMenuController.esPopPupAccesible == true}">
	<script>
		irAgregarSubMenu('frmPrincipal:incTabMenu:incMenuEdicion:btnIrAgregarSubMenu');
	</script>
	</h:panelGroup>
	</td>
</tr>

   		
</h:panelGroup>

<h:panelGroup rendered="#{empty adminMenuController.listaMenu}">
<tr>
    <td colspan="2"><h:outputText value="Nivel 1 :" /></td>
	<td colspan="2">
		<a4j:commandButton value="Agregar" actionListener="#{adminMenuController.irAgregarSubMenuInicial}" reRender="frmAgregarSubMenu" styleClass="btnEstilos">
			<f:param name="pIntNivel"  value="1" />
			<rich:componentControl for="pAgregarSubMenu" operation="show" event="onclick"/>      			
   		</a4j:commandButton>
	</td>
	<td colspan="12">&nbsp;</td>
</tr>	
</h:panelGroup>
            
</table>
  <%--
  <rich:panel style=" width: 645px ;border:2px solid #dbe5f1 ;background-color: #dbe5f1">
      <h:panelGrid columns="3" style="height: 80px;">
          <h:outputText value="Aplicaciones :"/>
          <h:inputText id="txtAplicacionesMenu" value="#{adminMenuController.strAplicacionesMenu}" size="60"/>                                    
          <a4j:commandLink actionListener="#{adminMenuController.listarAplicaciones}" oncomplete="Richfaces.showModalPanel('mpTablas').show()" reRender="tbTablasMenu" immediate="true">
          	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
          </a4j:commandLink>
          <h:outputText value="Tablas Relacionadas :"/>
          <h:inputText id="txtTablasMenu" value="#{adminMenuController.strTablasMenu}" size="60"/>
          <a4j:commandLink actionListener="#{adminMenuController.listarTablas}" oncomplete="Richfaces.showModalPanel('mpTablas').show()" reRender="frmTablas,tbTablasMenu" immediate="true">
          	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
          </a4j:commandLink>
          <h:outputText value="Vistas Relacionadas :"/>
          <h:inputText id="txtVistasMenu" value="#{adminMenuController.strVistasMenu}" size="60"/>
			<a4j:commandLink actionListener="#{adminMenuController.listarVistas}" oncomplete="Richfaces.showModalPanel('mpTablas').show()" reRender="tbTablasMenu" immediate="true">
          	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
          	</a4j:commandLink>
          <h:outputText value="Triggers Relacionados :"/>
          <h:inputText id="txtTriggersMenu" value="#{adminMenuController.strTriggersMenu}" size="60"/>
          <a4j:commandLink actionListener="#{adminMenuController.listarTriggers}" oncomplete="Richfaces.showModalPanel('mpTablas').show()" reRender="tbTablasMenu" immediate="true">
          	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
          </a4j:commandLink>
      </h:panelGrid>
  </rich:panel>
  --%>
</h:column>
</h:panelGrid>