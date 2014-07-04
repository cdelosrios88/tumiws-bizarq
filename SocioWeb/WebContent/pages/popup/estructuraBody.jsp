<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca        			-->
	<!-- Modulo    : Cr�ditos                 		-->
	<!-- Prototipo : PROTOTIPO Entidadaes/estructura-->
	<!-- Fecha     : 08/05/2012               		-->

<h:form id="buscarEntidadPadronForm">
     	
             <h:panelGrid columns="3">
	             <rich:column style="border:none">
	             	<h:outputText value="Entidad "></h:outputText>
	             </rich:column>
	             <rich:column>
	             	<h:inputText value="#{padronController.estructuraFiltro.juridica.strRazonSocial}" size="20"></h:inputText>
	             </rich:column>
	             <rich:column>
	             	<a4j:commandButton value="Buscar" actionListener="#{padronController.buscarEstructuraPorNombre}"
	             					   reRender="pgListaEstructurasPadron2" styleClass="btnEstilos"/>
	             </rich:column>
             </h:panelGrid>             
             <h:panelGrid id="pgListaEstructurasPadron2">
             	<rich:dataTable id="tbEstructuras" var="item" rowKeyVar="rowKey" sortMode="single" width="500px" 
    							value="#{padronController.listaEstructuras}" rows="5">
	                	<rich:column style="border: 1px solid #C0C0C0" width="29px">
	                        <h:outputText value="#{rowKey + 1}"></h:outputText>
	                    </rich:column>
	                    <rich:column style="border: 1px solid #C0C0C0" width="90">
	                    	<f:facet name="header">
	                            <h:outputText value="Seleccione"></h:outputText>
	                        </f:facet>
	                        <a4j:commandLink value="Seleccionar"
		             			actionListener="#{padronController.seleccionarEntidad2}"
		             			onclick="Richfaces.hideModalPanel('pBuscarEntidadPadron')"
		             			reRender="cajaNombreEntidad">
		             			<f:attribute name="item" value="#{item}"/>
		             		</a4j:commandLink>
	                    </rich:column>
	                    <rich:column style="border: 1px solid #C0C0C0" width="420">
	                    	<f:facet name="header">
	                            <h:outputText value="Nombre"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.juridica.strRazonSocial}"></h:outputText>
	                    </rich:column>
                    <f:facet name="footer">
	                    <rich:datascroller for="tbEstructuras" maxPages="5"/>   
	                </f:facet> 
                </rich:dataTable>
             </h:panelGrid>	
</h:form>