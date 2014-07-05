<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form id="frmBuscarCuentaBancaria">
	   	<h:panelGroup layout="block">	    	


			<h:panelGroup>
	    		<rich:dataTable
	    			id="tablaCuenta"
	    			var="item" 
	                value="#{transferenciaController.personaSeleccionada.listaCuentaBancaria}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="400px">
			                           
					<rich:column width="300px">
						<f:facet name="header">
	                    	<h:outputText value="Numero"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.strNroCuentaBancaria}"/>
			      	</rich:column>
					<rich:column width="100px">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{transferenciaController.seleccionarCuentaBancaria}"
							oncomplete="Richfaces.hideModalPanel('pBuscarCuentaBancaria')"
							reRender="panelCuentaT">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
			      	<f:facet name="footer">
						<rich:datascroller for="tablaCuenta" maxPages="5"/>   
					</f:facet>
					
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>