<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pBuscarRequisicionInforme" width="650" height="270"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Requisicion"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarRequisicionInforme" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<h:form>
	   	<h:panelGroup>
	   	
	   		<h:panelGroup>
	    		<rich:dataTable
	    			var="item"
	                value="#{informeController.listaRequisicion}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="600px">
			        
					<rich:column width="80" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Item"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.id.intItemRequisicion}"/>
			      	</rich:column>
					<rich:column width="120" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Requisición"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISICION}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoRequisicion}"/>
                    </rich:column>
                    <rich:column width="120" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Aprobación"/>                		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_APROBACION}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoAprobacion}"/>                      		
                  	</rich:column>
                    <rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Monto"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoLogistica}">
	                    	<f:converter converterId="ConvertidorMontos"/>
	                 	</h:outputText>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha Registro"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaRequisicion}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy" />
	                 	</h:outputText>
                  	</rich:column>
					<rich:column width="100" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{informeController.seleccionarRequisicion}"
							oncomplete="Richfaces.hideModalPanel('pBuscarRequisicionInforme')"
							reRender="panelRequisicionI">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>	
