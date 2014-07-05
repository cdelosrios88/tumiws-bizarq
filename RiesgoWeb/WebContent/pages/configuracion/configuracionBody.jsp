<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<rich:modalPanel id="pBuscarEntidad" width="548" height="348"
	resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Estructura"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="pBuscarEntidad" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<a4j:include viewId="/pages/estructura/popup/estructuraBody.jsp" />
</rich:modalPanel>

<rich:modalPanel id="pAlertaRegistro" width="400" height="150"
	resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Alerta"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="pAlertaRegistro" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<h:form id="frmAlertaRegistro">
		<rich:panel style="background-color: #DEEBF5; margin: 0 auto;">
			<h:panelGrid columns="1">
				<rich:column style="border:none">
					<h:outputText
						value="¿Qué operación desea realizar con el registro seleccionado?" />
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="12px" />
			<h:panelGrid columns="2" style="margin: 0 auto;">
				<rich:column style="border:none" id="colBtnModificar">
					<a4j:commandButton value="Modificar" styleClass="btnEstilos"
						action="#{confArchivoController.modificarRegistro}"
						onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
						reRender="contPanelInferior,panelMensaje" />
				</rich:column>
				<rich:column>
					<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
						action="#{confArchivoController.eliminarRegistro}"
						onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
						reRender="panelMensaje,contPanelInferior,panelTablaConfiguracion"
						rendered="#{confArchivoController.mostrarBtnEliminar}" />
				</rich:column>
			</h:panelGrid>
		</rich:panel>
	</h:form>
</rich:modalPanel>

<rich:modalPanel id="pAlertaRegistroCartera" width="400" height="150"
	resizeable="false" style="background-color: #DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Alerta"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="pAlertaRegistroCartera" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<h:form id="frmAlertaRegistroCartera">
		<rich:panel
			style="background-color: #DEEBF5; margin: 0 auto;">
			<h:panelGrid columns="1">
				<rich:column style="border:none">
					<h:outputText
						value="¿Qué operación desea realizar con el registro seleccionado?" />
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="12px" />
			<h:panelGrid columns="2" style="margin: 0 auto;">
				<rich:column style="border:none" id="colBtnModificar">
					<a4j:commandButton value="Modificar" styleClass="btnEstilos"
						action="#{confCarteraController.modificarRegistro}"
						onclick="Richfaces.hideModalPanel('pAlertaRegistroCartera')"
						reRender="contPanelInferiorCartera,panelMensajeCartera,panelBotonesCartera" />
				</rich:column>
				<rich:column>
					<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
						action="#{confCarteraController.eliminarRegistro}"
						onclick="Richfaces.hideModalPanel('pAlertaRegistroCartera')"
						reRender="panelMensajeCartera,contPanelInferiorCartera,panelTablaCartera"
						rendered="#{confCarteraController.mostrarBtnEliminar}" />
				</rich:column>
			</h:panelGrid>
		</rich:panel>
	</h:form>
</rich:modalPanel>


<rich:tabPanel style="margin:15px; width:970px">
	<rich:tab label="Cartera de Créditos">
		<a4j:include viewId="/pages/configuracion/tabCartera.jsp" />
	</rich:tab>
	<rich:tab label="Archivo de Inforcorp">
		<a4j:include viewId="/pages/configuracion/tabArchivo.jsp" />
	</rich:tab>
</rich:tabPanel>
