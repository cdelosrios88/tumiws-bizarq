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
	<td colspan="3">
		<h:selectOneMenu value="#{usuarioPerfilController.perfil.id.intPersEmpresaPk}" style="width:150px;" disabled="true">
		<tumih:selectItems var="sel" value="#{usuarioPerfilController.listaJuridicaEmpresa}"
						   itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="10"></td>
</tr>

<tr>
	<td colspan="2"><h:outputText value="Nombre Perfil:"/></td>
	<td colspan="2">
		<h:inputText value="#{usuarioPerfilController.perfil.strDescripcion}" disabled="true" style="width:100px;"/>
	</td>
	<td></td>
	<td colspan="2"><h:outputText value="Tipo Perfil :"/></td>
	<td colspan="2">
		<h:selectOneMenu value="#{usuarioPerfilController.perfil.intTipoPerfil}" disabled="true" >
		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERFIL}" 
		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="7"></td>
</tr>

<tr>
	<td colspan="2"><h:outputText value="Estado:"/></td>
	<td colspan="2">
		<h:selectOneMenu value="#{usuarioPerfilController.perfil.intIdEstado}" style="width:100px;" disabled="true">
		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
						   itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="12"></td>
</tr>

<tr>
	<td colspan="2"><h:outputText value="Rango de Fecha:"/></td>
	<td colspan="3">
		<rich:calendar popup="true" inputStyle="width:100px;" datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" disabled="true" 
		    showApplyButton="true" defaultTime="00:00" value="#{usuarioPerfilController.perfil.dtDesde}" cellHeight="20px"/>
	</td>
	<td colspan="3" align="left">
		<rich:calendar popup="true" inputStyle="width:100px;" datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" disabled="true" 
			showApplyButton="true" defaultTime="00:00" value="#{usuarioPerfilController.perfil.dtHasta}" cellHeight="20px"/>
	</td>
	<td colspan="8"></td>
</tr>

<tr><td colspan="16">&nbsp;</td></tr>

<h:panelGroup rendered="#{not empty usuarioPerfilController.listaMenu}">

<tr>
	<td colspan="16" align="left"><h:outputText value="Estructura del Menú"/></td>
</tr>

<tr><td colspan="16">&nbsp;</td></tr>

<a4j:repeat value="#{usuarioPerfilController.listaMenu}" var="itemMenu" rowKeyVar="rowKeyMenu">
<h:panelGroup rendered="#{fn:length(itemMenu) != 0}">
<tr>
	<td colspan="2"><h:outputText value="Nivel #{rowKeyMenu + 1} :" /></td>
	<td colspan="14"></td>
</tr>
<a4j:repeat value="#{itemMenu}" var="itemSubMenu" rowKeyVar="rowKeySubMenu">
<h:panelGroup rendered="#{itemSubMenu.intIdEstado != applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
<tr>
	<td>
		<tumih:radioButton name="#{rowKeyMenu}" overrideName="true" 
				value="#{usuarioPerfilController.listaSelectRadioMenu[rowKeyMenu]}" itemValue="#{rowKeySubMenu}">
			<a4j:support event="onClick" actionListener="#{usuarioPerfilController.onclickRadioDeSubMenu}" reRender="pgMantenimiento">
			<f:param name="pIndiceMenu"  value="#{rowKeyMenu}" />
			<f:param name="pIndiceSubMenu"  value="#{rowKeySubMenu}" />
			</a4j:support>
		</tumih:radioButton>										
	</td>
	<td colspan="5">
		<h:inputText value="#{itemSubMenu.strNombre}" style="width:250px;" disabled="true"/>
	</td>
	<td align="center">
		<h:panelGroup rendered="#{not empty itemSubMenu.permisoPerfil}">
			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
			itemValue="intIdDetalle" itemLabel="strDescripcion"
			property="#{itemSubMenu.permisoPerfil.intIdEstado}"/>
		</h:panelGroup>
	</td>
	<td colspan="9"></td>
</tr>
</h:panelGroup>

<h:panelGroup rendered="#{fn:length(itemMenu) == rowKeySubMenu + 1}">
<tr>
    <td colspan="7"></td>
	<td colspan="2">
		<a4j:commandButton value="Consultar" actionListener="#{usuarioPerfilController.irPermiso}" reRender="idFormPopupPermisoConsulta" styleClass="btnEstilos">
			<f:param name="pIndiceMenu"  value="#{rowKeyMenu}" />
			<rich:componentControl for="pPermisoConsulta" operation="show" event="onclick"/>      			
   		</a4j:commandButton>
	</td>
	<td colspan="7">&nbsp;</td>
</tr>
</h:panelGroup>

</a4j:repeat>
</h:panelGroup>
<tr><td colspan="16">&nbsp;</td></tr>
</a4j:repeat>
</h:panelGroup>


</table>
</h:column>
</h:panelGrid>