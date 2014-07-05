<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pBuscarOrdenCompra" width="890" height="300"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Documento Requisito"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarOrdenCompra" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<h:form>
   	
	   	<h:panelGroup>
	   			<!-- Agregado por cdelosrios, 15/10/2013 -->
	   			<h:panelGrid columns="3">
	   				<rich:column>
	   					<h:outputText value="Nº Orden"/>
	   				</rich:column>
	   				<rich:column>
	   					<h:inputText value="#{sunatController.ordenCompraFiltro.id.intItemOrdenCompra}" size="10"/>
	   				</rich:column>
	   				<rich:column>
	   					<a4j:commandButton styleClass="btnEstilos"
	                		value="Buscar" 
	                		reRender="dtOrdenCompra"
	                    	action="#{sunatController.buscarOrdenCompra}" 
	                    	style="width:130px"/>
	   				</rich:column>
	   			</h:panelGrid>
	   			<!-- Fin agregado por cdelosrios, 15/10/2013 -->
	   		   	<rich:dataTable id="dtOrdenCompra"
	    			var="item"
	                value="#{sunatController.listaOrdenCompra}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="870px">
			        
					<rich:column width="40" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Item"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemOrdenCompra}"/>
                    </rich:column>
                  	<rich:column width="380" style="text-align: left">
                    	<f:facet name="header">
                      		<h:outputText value="Proveedor"/>                      		
                      	</f:facet>
                      	<h:outputText 
							rendered="#{item.personaProveedor.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
							value="DNI : #{item.personaProveedor.documento.strNumeroIdentidad} - #{item.personaProveedor.natural.strNombreCompleto}"
							title="DNI : #{item.personaProveedor.documento.strNumeroIdentidad} - #{item.personaProveedor.natural.strNombreCompleto}"
						/>
						<h:outputText
							rendered="#{item.personaProveedor.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
							value="RUC : #{item.personaProveedor.strRuc} - #{item.personaProveedor.juridica.strRazonSocial}"
							title="RUC : #{item.personaProveedor.strRuc} - #{item.personaProveedor.juridica.strRazonSocial}"
						/>
                  	</rich:column>               		
                  	<rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Orden"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOORDEN}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.intParaEstadoOrden}"/>
                  	</rich:column>
                  	<rich:column width="140" style="text-align: left">
                    	<f:facet name="header">
                      		<h:outputText value="Tipo de Aprobación"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_APROBACION}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.documentoRequisicion.requisicion.intParaTipoAprobacion}"/>
					</rich:column>	
                  	<rich:column width="80" style="text-align: right">
                    	<f:facet name="header">
                      		<h:outputText value="Monto"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoTotalDetalle}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="60" style="text-align: right">
                    	<f:facet name="header">
                      		<h:outputText value="Moneda"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.intParaTipoMoneda}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{sunatController.seleccionarOrdenCompra}"
							oncomplete="Richfaces.hideModalPanel('pBuscarOrdenCompra')"
							reRender="contPanelInferiorD">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	<!-- Agregado por cdelosrios, 15/10/2013 -->
			      	<f:facet name="footer">   
	                    <rich:datascroller for="dtOrdenCompra" maxPages="10"/>   
	                </f:facet> 
	                <!-- Fin agregado por cdelosrios, 15/10/2013 -->
	            </rich:dataTable>
	   	
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>	
