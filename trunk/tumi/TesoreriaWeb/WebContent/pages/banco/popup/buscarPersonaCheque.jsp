<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form>
	   	<h:panelGroup>	    	
	    	<h:panelGrid columns="12" id="panelPopUpBuscarPersona">
	    		<rich:column width="100">
					<h:outputText value="Tipo de Persona :"/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu
						value="#{chequesController.intTipoPersona}"
						disabled="#{chequesController.intTipoBuscarPersona==2}"
						style="width: 100px;">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" reRender="panelPopUpBuscarPersona"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="30">
           			<h:outputText  value="DNI :"
           				rendered="#{chequesController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"/>
           			<h:outputText  value="RUC :"
           				rendered="#{chequesController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"/>
           		</rich:column>
           		<rich:column width="150">
           			<h:inputText size="20"
           				rendered="#{chequesController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
           				onkeydown="return validarNumDocIdentidad(this,event,8)"
           				value="#{chequesController.strFiltroTextoPersona}"/>
           			<h:inputText size="20"
           				rendered="#{chequesController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
           				onkeydown="return validarNumDocIdentidad(this,event,12)"
           				value="#{chequesController.strFiltroTextoPersona}"/>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="tablaPersona"
                    	action="#{chequesController.buscarPersona}" 
                    	style="width:100px"/>
           		</rich:column>
	    	</h:panelGrid>	    	

			<h:panelGroup id="tablaPersona">
	    		<rich:dataTable
	    			var="item"
	                value="#{chequesController.listaPersona}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="520px">
			        
					<rich:column width="400" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Nombre"/>
	                 	</f:facet>
			        	<h:outputText
			        		rendered="#{chequesController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}" 
			        		value="#{item.natural.strNombres} #{item.natural.strApellidoPaterno} #{item.natural.strApellidoMaterno}"/>
			        	<h:outputText
			        		rendered="#{chequesController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}" 
			        		value="#{item.juridica.strRazonSocial}"/>
			      	</rich:column>
					<rich:column width="120" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{chequesController.seleccionarPersona}"
							oncomplete="Richfaces.hideModalPanel('pBuscarPersonaCheque')"
							reRender="panelPersonaC,panelDocumentoC">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>