<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<rich:modalPanel id="pBuscarPersonaJuridica" width="580" height="250"
	resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Buscar Persona Juridica" />
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="pBuscarPersonaJuridica" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<h:form id="frmPersonaJuridica">
		<h:panelGroup id="panelPopUpBuscarPersonaJuridica">
			<h:panelGrid columns="12">
				<rich:column width="100">
					<h:outputText value="Razon Social :" />
				</rich:column>
				<rich:column width="100">
					<h:inputText size="20"
						value="#{controlLibroContableController.strFiltroTextoRazon}" />
				</rich:column>
				<rich:column width="30">
					<h:outputText value="RUC :" />
				</rich:column>
				<rich:column width="150">
					<h:inputText size="20"
						onkeydown="return validarNumDocIdentidad(this,event,11)"
						value="#{controlLibroContableController.strFiltroTextoRuc}" />
				</rich:column>
				<rich:column>
					<a4j:commandButton styleClass="btnEstilos" value="Buscar"
						reRender="tablaPersona"
						action="#{controlLibroContableController.buscarPersonaJuridica}"
						style="width:100px" />
				</rich:column>
			</h:panelGrid>
		
			<h:panelGroup id="tablaPersona">
				<rich:dataTable var="item"
					value="#{controlLibroContableController.listaPersonaJuridica}"
					rowKeyVar="rowKey" rows="5" sortMode="single" width="520px">
		
					<rich:column width="400" style="text-align: center;">
						<f:facet name="header">
							<h:outputText value="Razon Social" />
						</f:facet>
						<h:outputText value="#{item.strJuriRazonSocial}" />
					</rich:column>
					<rich:column width="120" style="text-align: center;">
						<f:facet name="header">
							<h:outputText value="Seleccionar" />
						</f:facet>
						<a4j:commandLink value="Seleccionar"
							actionListener="#{controlLibroContableController.seleccionarPersonaJuridica}"
							oncomplete="Richfaces.hideModalPanel('pBuscarPersonaJuridica')"
							reRender="txtNotariaPublica">
							<f:attribute name="item" value="#{item}" />
						</a4j:commandLink>
					</rich:column>
				</rich:dataTable>
			</h:panelGroup>
		</h:panelGroup>
	</h:form>
</rich:modalPanel>		