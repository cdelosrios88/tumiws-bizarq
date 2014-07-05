<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	
	<h:form id="frmBuscarApoderado">
	   	<h:panelGroup layout="block">
	    	<h:panelGrid columns="5">
	    		<rich:column width="50">
           			<h:outputText value="DNI : "/>
           		</rich:column>
           		<rich:column width="250">
           			<h:inputText size="40" onkeydown="return validarNumDocIdentidad(this,event,8)"
           				value="#{giroPrevisionController.strDNIApoderadoFiltro}"/>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="tablaPersona"
                    	action="#{giroPrevisionController.buscarPersonaApoderado}" 
                    	style="width:100px"/>
           		</rich:column>
	    	</h:panelGrid>


			<h:panelGroup id="tablaPersona">
	    		<rich:dataTable
	    			var="item" 
	                value="#{giroPrevisionController.listaPersonaApoderado}" 
			  		rowKeyVar="rowKey" 
			  		rows="5"
			  		sortMode="single"
			  		width="500px">
			                           
					<rich:column width="150px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="DNI"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.documento.strNumeroIdentidad}"/>
			      	</rich:column>
			      	<rich:column width="250px" style="text-align: left;">
						<f:facet name="header">
	                    	<h:outputText value="Nombre"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.natural.strNombreCompleto}"/>
			      	</rich:column>
					<rich:column width="100px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Agregar"/>
	                 	</f:facet>
			        	<a4j:commandLink
			    			value="Seleccionar"
							actionListener="#{giroPrevisionController.seleccionarPersonaApoderado}"
							oncomplete="Richfaces.hideModalPanel('pBuscarApoderado')"
							reRender="panelApoderadoG,panelCartaPoderG">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>