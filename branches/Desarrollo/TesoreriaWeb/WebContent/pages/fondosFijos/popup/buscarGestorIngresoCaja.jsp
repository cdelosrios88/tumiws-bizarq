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
	    		<rich:column width="102">
					<h:outputText value="Tipo Búsqueda :"/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu 
						id="idTipoDeBusqueda" 
						value="#{cajaController.intTipoBusqDocuRazonSocial}" > 
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel"
								value="#{cajaController.listaTablaTipoBusqueda}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>					

						<a4j:support event="onchange" reRender="panelPopUpBuscarGestorIngresoC"/>
					</h:selectOneMenu>	
 				</rich:column>

           		<rich:column width="150">
           			<h:inputText readonly="true"
							rendered="#{cajaController.intTipoBusqDocuRazonSocial==0}"
							style="background-color: #BFBFBF;"
							size="32"/>
           			<h:inputText size="32"
           				rendered="#{cajaController.intTipoBusqDocuRazonSocial==applicationScope.Constante.PARAM_T_TIPOBUSQUEDA_PERSONA_NOMBRES}"
           				value="#{cajaController.strFiltroTextoPersonaNombres}"/>
           			<h:inputText size="32"
           				rendered="#{cajaController.intTipoBusqDocuRazonSocial==applicationScope.Constante.PARAM_T_TIPOBUSQUEDA_PERSONA_DOCUMENTO}"
           				onkeydown="return validarNumDocIdentidad(this,event,8)"
           				value="#{cajaController.strFiltroTextoPersonaDocumento}"/>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="tablaGestorIngresoC"
                    	action="#{cajaController.buscarGestor}" 
                    	style="width:100px"/>
           		</rich:column>
	    	</h:panelGrid>	    	

			<h:panelGroup id="tablaGestorIngresoC">
	    		<rich:dataTable
	    			var="item"
	                value="#{cajaController.listaGestorCobranza}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="520px" style=" width : 648px;">
			        
					<rich:column width="10" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value=""/>
	                 	</f:facet>
			        	<h:outputText
			        		value="#{item.id.intPersPersonaGestorPk}"/>
			      	</rich:column>
			      	<rich:column width="350" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Nombre"/>
	                 	</f:facet>
			        	<h:outputText
			        		value="#{item.persona.natural.strNombreCompleto}"/>
			      	</rich:column>
					<rich:column width="100" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="DNI"/>
	                 	</f:facet>
			        	<h:outputText
			        		value="#{item.persona.documento.strNumeroIdentidad}"/>
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