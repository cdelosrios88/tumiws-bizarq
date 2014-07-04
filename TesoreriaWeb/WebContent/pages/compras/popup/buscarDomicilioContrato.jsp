<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pBuscarDomicilioContrato" width="860" height="270"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Seleccionar Contrato"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarDomicilioContrato" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<h:form>
	   	<h:panelGroup>
	   		<h:panelGrid columns="2">
	   			<rich:column width="610">
	   				<a4j:commandLink value="Registrar una Nueva Dirección"
	   					action="#{contratoController.abrirPopUpAgregarDomicilio}"
	   					onclick="Richfaces.hideModalPanel('pBuscarDomicilioContrato')"
	   					oncomplete="Richfaces.showModalPanel('pAgregarDomicilioContrato')"
	   					reRender="pAgregarDomicilioContrato"/>
	   			</rich:column>
	   		</h:panelGrid>
	   		
	   		<h:panelGrid columns="2">
	   			<rich:column width="610">
	    		<rich:dataTable
	    			var="item"
	                value="#{contratoController.contratoNuevo.empresaServicio.listaDomicilio}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="830px">
			        
			        <rich:column width="200" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Vía"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVIA}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.intTipoViaCod}"/>
						<h:outputText value=" - #{item.strNombreVia}"/>
                  	</rich:column>
					<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Número"/>
                      	</f:facet>
                      	<h:outputText value="#{item.intNumeroVia}"/>
                    </rich:column>
                    <rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Referencia"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strReferencia}" 
                      		title="#{item.strReferencia}"/>
                    </rich:column>
					<rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Departamento"/>
                        </f:facet>
                        <h:outputText value="#{item.strUbigeoDepartamento}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Provincia"/>
                        </f:facet>
                        <h:outputText value="#{item.strUbigeoProvincia}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Distrito"/>
                        </f:facet>
                        <h:outputText value="#{item.strUbigeoDistrito}"/>
                  	</rich:column>
					<rich:column width="80" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							rendered="#{item.intEstadoCod==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO}"
							actionListener="#{contratoController.seleccionarDomicilio}"
							oncomplete="Richfaces.hideModalPanel('pBuscarDomicilioContrato')"
							reRender="panelDomicilioC">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>			      	
	            </rich:dataTable>
	            </rich:column>
	    	</h:panelGrid>
			
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>	
