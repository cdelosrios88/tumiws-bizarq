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
<rich:panel style="width:778px; border:1px solid #17356f;background-color: #dbe5f1">
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
	<td colspan="2"><h:outputText value="Empresa :"  /></td>
	<td colspan="5">
		<h:selectOneMenu value="#{adminMenuController.filtroMenu.intPersEmpresaPk}" style="width:250px;">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" value="#{adminMenuController.listaJuridicaEmpresa}"
							   itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonSocial}"/>
			<a4j:support event="onchange" reRender="frmPrincipal" actionListener="#{adminMenuController.onchangeEmpresa}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2"><h:outputText value="Tipo Menú :"  /></td>
	<td colspan="2">
		<h:selectOneMenu value="#{adminMenuController.filtroMenu.intTipoMenu}">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOAPLICACION}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2"><h:outputText value="Estado :"  /></td>
	<td colspan="2">
		<h:selectOneMenu value="#{adminMenuController.filtroMenu.intIdEstado}">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
			tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
		</h:selectOneMenu>
	</td>
	<td></td>	
</tr>

<tr>
	<td colspan="2"><h:outputText value="Nivel 1 :"  /></td>
	<td colspan="3">
	   <h:selectOneMenu value="#{adminMenuController.filtroMenu.listaStrIdTransaccion[0]}" style="width: 150px;"> 
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" value="#{adminMenuController.filtroMenu.listaMenu01}" 
				itemValue="#{sel.id.intIdTransaccion}" itemLabel="#{sel.strNombre}"/>
			<a4j:support event="onchange" actionListener="#{adminMenuController.onchangeNivel01}" reRender="frmPrincipal"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2"><h:outputText value="Nivel 2 :" /></td>
	<td colspan="3">
		<h:selectOneMenu value="#{adminMenuController.filtroMenu.listaStrIdTransaccion[1]}" style="width: 150px;">      
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" value="#{adminMenuController.filtroMenu.listaMenu02}" 
				itemValue="#{sel.id.intIdTransaccion}" itemLabel="#{sel.strNombre}"/>
			<a4j:support event="onchange" actionListener="#{adminMenuController.onchangeNivel02}" reRender="frmPrincipal"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2"><h:outputText value="Nivel 3 :" /></td>
	<td colspan="3">
		<h:selectOneMenu value="#{adminMenuController.filtroMenu.listaStrIdTransaccion[2]}" style="width: 150px;">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>      
			<tumih:selectItems var="sel" value="#{adminMenuController.filtroMenu.listaMenu03}" 
				itemValue="#{sel.id.intIdTransaccion}" itemLabel="#{sel.strNombre}"/>
	   	</h:selectOneMenu>
	</td>
	<td></td>
</tr>

<tr>
	<td colspan="14"></td>
	<td colspan="2">
		<h:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{adminMenuController.buscarMenu}"/>	
	</td>
</tr>

</table>
	
<div style="padding-left: 25px">
<h:panelGrid id="pgBusqAdminMenu" columns="1"  border="0">
<rich:scrollableDataTable id="tbAdminMenu" value="#{adminMenuController.listaMenuComp}" 
var="item" rowKeyVar="rowKey" sortMode="single"  rendered="#{not empty adminMenuController.listaMenuComp}"
onRowClick="jsSeleccionMenu('#{rowKey}');" width="625px" height="195px"> 
            <rich:column width="29"><h:outputText value="#{rowKey+1}"/></rich:column>
            <rich:column width="120">
                <f:facet name="header"><h:outputText value="Nivel 1"/></f:facet>
                <h:outputText value="#{item.listaStrNombre[0]}"/>
            </rich:column>
            <rich:column width="120">
                <f:facet name="header"><h:outputText value="Nivel 2"/></f:facet>
                <h:panelGroup rendered="#{fn:length(item.listaStrNombre) > 1}">
                	<h:outputText value="#{item.listaStrNombre[1]}"/>
                </h:panelGroup>
            </rich:column>
            <rich:column width="120">
                <f:facet name="header"><h:outputText value="Nivel 3"/></f:facet>
                <h:panelGroup rendered="#{fn:length(item.listaStrNombre) > 2}">
                	<h:outputText value="#{item.listaStrNombre[2]}"/>
                </h:panelGroup>
            </rich:column>
            <rich:column width="120">
                <f:facet name="header"><h:outputText value="Nivel 4"/></f:facet>
                <h:panelGroup rendered="#{fn:length(item.listaStrNombre) > 3}">
                	<h:outputText value="#{item.listaStrNombre[3]}"/>
                </h:panelGroup>
            </rich:column>
   </rich:scrollableDataTable>
   </h:panelGrid>
   <rich:spacer height="4px"/><rich:spacer height="4px"/>
   <h:panelGrid columns="2">
	<h:outputLink value="#" id="linkAdminMenu">
	<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
	<rich:componentControl for="mpUpdateDeleteMenu" attachTo="linkAdminMenu" operation="show" event="onclick"/>
	</h:outputLink>
	<h:outputText value="Para Modificar Hacer click en Registro" style="color:#8ca0bd" ></h:outputText>                                     
   </h:panelGrid>
   
</div>
   
<rich:spacer height="4px"/><rich:spacer height="4px"/>
<rich:spacer height="4px"/><rich:spacer height="4px"/>
  
<h:panelGrid columns="4">
	<h:commandButton value="Nuevo" actionListener="#{adminMenuController.irGrabarMenu}" styleClass="btnEstilos"/>
	<h:commandButton value="Guardar" actionListener="#{adminMenuController.grabarMenu}" styleClass="btnEstilos"
  		rendered="#{adminMenuController.strTipoMantMenu == applicationScope.Constante.MANTENIMIENTO_GRABAR}"/>
  	<h:commandButton value="Guardar" actionListener="#{adminMenuController.modificarMenu}" styleClass="btnEstilos"
  		rendered="#{adminMenuController.strTipoMantMenu == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}"/>
  	<h:commandButton value="Cancelar" actionListener="#{adminMenuController.cancelarMenu}" styleClass="btnEstilos"/>
</h:panelGrid>
<rich:spacer height="4px"/><rich:spacer height="4px"/>
<rich:spacer height="4px"/><rich:spacer height="4px"/>

<h:panelGroup id="pgMenu">  
<rich:panel rendered="#{adminMenuController.strTipoMantMenu != applicationScope.Constante.MANTENIMIENTO_NINGUNO}" style="width:800px;border:1px solid #17356f;" >
	<h:panelGroup rendered="#{adminMenuController.strTipoMantMenu != applicationScope.Constante.MANTENIMIENTO_CONSULTAR}">
		<a4j:include id="incMenuEdicion" viewId="/pages/permiso/menu/mantenimiento/edicion.jsp"/>
	</h:panelGroup>
	<h:panelGroup rendered="#{adminMenuController.strTipoMantMenu == applicationScope.Constante.MANTENIMIENTO_CONSULTAR}">
		<a4j:include id="incMenuVista" viewId="/pages/permiso/menu/mantenimiento/vista.jsp"/>
	</h:panelGroup>
</rich:panel>
</h:panelGroup>

</rich:panel>   
