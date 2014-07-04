<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	<h:form>
	   	<h:panelGroup layout="block">
	    	
	    	<h:outputText value="Seleccione una persona responsable de la siguiente lista : "/>
	    	
	    	<rich:spacer height="10px"/>
	    	
	    	<h:panelGrid id="panelTablaResponsable">
	        	<rich:extendedDataTable id="tblResponsable" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{aperturaController.listaAccesoDetalleRes}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="550px" 
					height="170px" 
					align="center">
					
					<rich:column width="100px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Seleccionar"/>
                      	</f:facet>
                      	<a4j:commandLink
							value="Seleccionar"
					        actionListener="#{aperturaController.seleccionarResponsable}"
							reRender="panelResponsableCG"
							oncomplete="Richfaces.hideModalPanel('pSeleccionarResponsable')">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
                	</rich:column>
                	<rich:column width="50px" style="text-align: center">
		            	<f:facet name="header">
		                	<h:outputText value="Orden"/>
		               	</f:facet>
		                <h:outputText value="#{item.intOrden}"/>
		        	</rich:column>
                	<rich:column width="250px" style="text-align: center">
		            	<f:facet name="header">
		                	<h:outputText value="Nombre Completo"/>
		               	</f:facet>
		                <h:outputText value="#{item.persona.natural.strNombreCompleto}" title="#{item.persona.natural.strNombreCompleto}"/>
		        	</rich:column>
		        	<rich:column width="150px" style="text-align: center">
		            	<f:facet name="header">
		                	<h:outputText value="DNI"/>
		               	</f:facet>
		                <h:outputText value="#{item.persona.documento.strNumeroIdentidad}" title="#{item.persona.documento.strNumeroIdentidad}"/>
		        	</rich:column>
		        	
		        	<f:facet name="footer">
						<rich:datascroller for="tblResponsable" maxPages="10"/>   
					</f:facet>
				</rich:extendedDataTable>
				
			</h:panelGrid>
									
	    	
	   	</h:panelGroup>
	</h:form>