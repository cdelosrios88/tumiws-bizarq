<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form>
	   	<h:panelGroup>	    	
	    	<h:panelGrid columns="12" id="panelPopUpBuscarGestorIngresoC">
	    		<rich:column width="30">
           			<h:outputText  value="DNI :"/>
           		</rich:column>
           		<rich:column width="150">
           			<h:inputText size="20"
           				onkeydown="return validarNumDocIdentidad(this,event,8)"
           				value="#{cajaController.strFiltroTextoPersona}"/>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="tablaGestorIngresoC"
                    	action="#{cajaController.buscarPersona}" 
                    	style="width:100px"/>
           		</rich:column>
	    	</h:panelGrid>	    	

			<h:panelGroup id="tablaGestorIngresoC">
	    		<rich:dataTable
	    			var="item"
	                value="#{cajaController.listaGestorIngreso}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="520px">
			        
					<rich:column width="400" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Nombre"/>
	                 	</f:facet>
			        	<h:outputText
			        		value="#{item.natural.strNombres} #{item.natural.strApellidoPaterno} #{item.natural.strApellidoMaterno}"/>
			      	</rich:column>
					<rich:column width="120" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink
			    			value="Seleccionar"
							actionListener="#{cajaController.seleccionarGestorIngreso}"
							oncomplete="Richfaces.hideModalPanel('pBuscarGestorIngresoCaja')"
							reRender="panelPersonaC,panelDocumentoC,panelGestorC,panelMensajeC,panelBotonesC">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>