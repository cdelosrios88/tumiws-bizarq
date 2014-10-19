<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form id="frmBuscarCuentaBancariaT">
	   	<h:panelGroup layout="block">	    	


			<h:panelGroup>
	    		<rich:dataTable
	    			id="tablaCuentaT"
	    			var="item" 
	                value="#{telecreditoController.personaSeleccionada.listaCuentaBancaria}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="600px">
			                           
					<rich:column width="500px">
						<f:facet name="header">
	                    	<h:outputText value="Numero"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.strEtiqueta}"/>
			      	</rich:column>
					<rich:column width="100px">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{telecreditoController.seleccionarCuentaBancaria}"
							oncomplete="Richfaces.hideModalPanel('pBuscarCuentaBancariaT')"
							reRender="panelCuentaT">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
			      	<f:facet name="footer">
						<rich:datascroller for="tablaCuentaT" maxPages="5"/>   
					</f:facet>
					
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>