<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca        			-->
	<!-- Modulo    : Créditos                 		-->

<rich:modalPanel id="pBuscarProducto" width="800" height="420"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Producto"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarProducto" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>

    <h:form id="frmEntidad">
     	<rich:panel style="background-color:#DEEBF5" id="panelSeleccionarProducto">             
             
			<h:panelGrid columns="2">
				<rich:column style="border:none" width="170">
					<h:outputText value="Tipo de Producto : "></h:outputText>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
                  		style="width: 150px;"
                  		disabled="true"
                  		value="#{requisitosController.creditoFiltro.id.intParaTipoCreditoCod}">
			      		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>					
	            </rich:column>
             </h:panelGrid>
             
             <h:panelGrid columns="3">
				<rich:column style="border:none" width="170">
					<h:outputText value="Tipo de Crédito Empresa : "></h:outputText>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
                  		style="width: 150px;"
			        	value="#{requisitosController.creditoFiltro.intParaTipoCreditoEmpresa}">
			      		<tumih:selectItems var="sel" value="#{requisitosController.listaTipoCreditoEmpresa}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Buscar" action="#{requisitosController.buscarCreditoDetalle}"
	             		reRender="pgListaProductos" styleClass="btnEstilos"/>
				</rich:column>
             </h:panelGrid>
             
             <h:panelGrid id="pgListaProductos">
             	<rich:dataTable id="tbProductos" 
             		var="item" 
             		rowKeyVar="rowKey" 
             		sortMode="single" 
             		width="750px" 
    				value="#{requisitosController.listaCreditoComp}" 
    				rows="5">
	                <rich:column style="text-align:center" width="60">
	                	<f:facet name="header">
	             			<h:outputText value="Seleccione"/>
	                	</f:facet>
	                	<h:selectBooleanCheckbox value="#{item.checked}"/>	                    
	             	</rich:column>	                
	         		<rich:column width="150px" style="text-align: center">
						<f:facet name="header">
				       		<h:outputText value="Código"/>
						</f:facet>
						<h:outputText value="#{item.credito.id.intItemCredito}"/>   	
				 	</rich:column>
				    <rich:column width="220" style="text-align: center">
				    	<f:facet name="header">
				        	<h:outputText value="Tipo de Crédito Empresa"/>
				      	</f:facet>
				      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.credito.intParaTipoCreditoEmpresa}"/>
					</rich:column>
				    <rich:column width="320" style="text-align: center">
				    	<f:facet name="header">
				        	<h:outputText value="Descripción"/>
				      	</f:facet>
				      	<h:outputText value="#{item.credito.strDescripcion}"/>
				 	</rich:column>
                    <f:facet name="footer">
	                	<rich:datascroller for="tbProductos" maxPages="20"/>   
	               	</f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="1">
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
		             	action="#{requisitosController.agregarConfServCredito}"
		             	onclick="Richfaces.hideModalPanel('pBuscarProducto')"
		             	reRender="#{requisitosController.nombrePanelProducto}">
		             </a4j:commandButton>
             	</rich:column>
             </h:panelGrid>			
         </rich:panel>
     </h:form>
     
</rich:modalPanel>     