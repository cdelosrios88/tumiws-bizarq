<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pBuscarPersonaCuadro" width="730" height="170"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Persona"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarPersonaCuadro" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<h:form>
	   	<h:panelGroup>	    	
	    	<h:panelGrid columns="12" id="panelBuscarPersonaContrato">
	    		<rich:column width="100">
					<h:outputText value="Tipo de Persona :"/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu
						value="#{cuadroController.intTipoPersona}"
						disabled="true"
						style="width: 100px;">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>				
					</h:selectOneMenu>
					<a4j:support event="onchange" reRender="panelBuscarPersonaContrato"  />
				</rich:column>
				<rich:column width="30">
           			<h:outputText  value="DNI :"
           				rendered="#{cuadroController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"/>
           			<h:outputText  value="RUC :"
           				rendered="#{cuadroController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"/>
           		</rich:column>
           		<rich:column width="150">
           			<h:inputText size="20"
           				rendered="#{cuadroController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
           				onkeydown="return validarNumDocIdentidad(this,event,8)"
           				value="#{cuadroController.strFiltroTextoPersona}"/>
           			<h:inputText size="20"
           				rendered="#{cuadroController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
           				onkeydown="return validarNumDocIdentidad(this,event,12)"
           				value="#{cuadroController.strFiltroTextoPersona}"/>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="tablaPersonaCuadro"
                    	action="#{cuadroController.buscarPersona}" 
                    	style="width:100px"/>
           		</rich:column>
	    	</h:panelGrid>	    	

			<h:panelGroup id="tablaPersonaCuadro">
	    		<rich:dataTable
	    			var="item"
	                value="#{cuadroController.listaPersona}"
	                rendered="#{cuadroController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="520px">
			        
					<rich:column width="300" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Nombre"/>
	                 	</f:facet>
			        	<h:outputText
			        		rendered="#{cuadroController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}" 
			        		value="#{item.natural.strNombres} #{item.natural.strApellidoPaterno} #{item.natural.strApellidoMaterno}"/>
			        	<h:outputText
			        		rendered="#{cuadroController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}" 
			        		value="#{item.juridica.strRazonSocial}"/>
			      	</rich:column>
					<rich:column width="80" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{cuadroController.seleccionarPersona}"
							oncomplete="Richfaces.hideModalPanel('pBuscarPersonaCuadro')"
							reRender="panelAprobacionCC">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
	            </rich:dataTable>
	            
	            <rich:dataTable
	    			var="item"
	                value="#{cuadroController.listaProveedor}"
	                rendered="#{cuadroController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="690px">
			        
					<rich:column width="150" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Tipo"/>
	                 	</f:facet>
	                 	<h:outputText value="#{item.strListaProveedorDetalle}"/>
	                </rich:column>
	                <rich:column width="300" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Nombre"/>
	                 	</f:facet>
			        	<h:outputText	value="#{item.persona.juridica.strRazonSocial}"
			        		title="#{item.persona.juridica.strRazonSocial}"/>
			      	</rich:column>
			      	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCONTRIB}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.persona.juridica.intEstadoContribuyenteCod}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Condición"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCONDCONTRIBUYENTE}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.persona.juridica.intCondContribuyente}"/>
                  	</rich:column>
					<rich:column width="80" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{cuadroController.seleccionarPersona}"
							oncomplete="Richfaces.hideModalPanel('pBuscarPersonaCuadro')"
							reRender="panelPersonaCC">
							<f:attribute name="item" value="#{item.persona}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>	
