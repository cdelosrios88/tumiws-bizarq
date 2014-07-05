<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form id="frmBuscarDocumento">
	   	<h:panelGroup layout="block">

			<h:panelGroup>
	    		<rich:dataTable
	    			id="tablaDocumento"
	    			var="item" 
	                value="#{fondosFijosController.listaDocumentoPorAgregar}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="600px">
			                           
					<rich:column width="150px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Documento"/>
	                 	</f:facet>
			        	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intTipoDocumento}"/>
			      	</rich:column>
					<rich:column width="170px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Número"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.strNroDocumento}"/>
			      	</rich:column>
					<rich:column width="100px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Monto"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.bdMonto}">
			        		<f:converter converterId="ConvertidorMontos" />
			        	</h:outputText>
			      	</rich:column>
			      	<rich:column width="80px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{fondosFijosController.seleccionarDocumento}"
							oncomplete="if(#{!fondosFijosController.blnNoExisteRequisito}){Richfaces.hideModalPanel('pBuscarDocumento')}"
							reRender="panelDocumentoF,pgMsgError">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
			      	<f:facet name="footer">
						<rich:datascroller for="tablaDocumento" maxPages="5"/>   
					</f:facet>
					
	            </rich:dataTable>
	    	</h:panelGroup>
	    	<h:panelGroup id="pgMsgError">
	    		<rich:column width="80px" style="text-align: center;">
	    			<h:outputText value="#{fondosFijosController.strMensajeError}" styleClass="msgError"/>
	    		</rich:column>
	    	</h:panelGroup>
	    	
			
	   	</h:panelGroup>
	</h:form>