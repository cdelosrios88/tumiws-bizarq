<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pBuscarPersona" width="630" height="170"
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
           		<rich:componentControl for="pBuscarPersona" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	
   	<h:form>
	   	<h:panelGroup>	    	
	    	<h:panelGrid columns="12" id="panelTipoPersonaBus">
	    		<rich:column width="100">
					<h:outputText value="Tipo de Persona :"/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu
						value="#{solicitudPersonalController.personaFiltro.intTipoPersonaCod}"
						disabled="#{!solicitudPersonalController.habilitarComboTipoPersona}"
						style="width: 100px;">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" reRender="panelTipoPersonaBus"/>	
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="30">
           			<h:outputText  value="DNI :"
           				rendered="#{solicitudPersonalController.personaFiltro.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"/>
           			<h:outputText  value="RUC :"
           				rendered="#{solicitudPersonalController.personaFiltro.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"/>
           		</rich:column>
           		<rich:column width="150">
           			<h:inputText size="20"
           				rendered="#{solicitudPersonalController.personaFiltro.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
           				onkeydown="return validarNumDocIdentidad(this,event,8)"
           				value="#{solicitudPersonalController.personaFiltro.strRuc}"/>
           			<h:inputText size="20"
           				rendered="#{solicitudPersonalController.personaFiltro.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
           				onkeydown="return validarNumDocIdentidad(this,event,12)"
           				value="#{solicitudPersonalController.personaFiltro.strRuc}"/>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar"
                		reRender="tablaPersona"
                    	action="#{solicitudPersonalController.buscarPersona}" 
                    	style="width:100px"/>
           		</rich:column>
			</h:panelGrid>
	        
	        <h:panelGrid columns="2" id="tablaPersona">   
	    		<rich:dataTable
	    			var="item"
	                value="#{solicitudPersonalController.listaPersonaBuscar}"
	                rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="590px">			        
					<rich:column width="150" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Documento"/>
	                 	</f:facet>
			        	<h:outputText	
			        		rendered="#{item.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
			        		value="DNI : #{item.documento.strNumeroIdentidad}"/>
			        	<h:outputText
			        		rendered="#{item.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
			        		value="RUC : #{item.strRuc}"/>
			      	</rich:column>
					<rich:column width="350" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Nombre"/>
	                 	</f:facet>
			        	<h:outputText	
			        		rendered="#{item.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
			        		value="#{item.natural.strNombreCompleto}"
			        		title="#{item.natural.strNombreCompleto}"/>
			        	<h:outputText
			        		rendered="#{item.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
			        		value="#{item.juridica.strRazonSocial}"
			        		title="#{item.juridica.strRazonSocial}"/>	
			      	</rich:column>
					<rich:column width="90" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{solicitudPersonalController.seleccionarPersona}"
							oncomplete="Richfaces.hideModalPanel('pBuscarPersona')"
							reRender="panelEntidadPago, panelPersonaDetalle">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>			      	
	        	</rich:dataTable>
	        </h:panelGrid>	
	            	    	
		</h:panelGroup>
			
	</h:form>
</rich:modalPanel>	
