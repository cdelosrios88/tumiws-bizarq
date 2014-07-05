<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<rich:modalPanel id="pBuscarUnidadEjecutoraEfectuado" width="610" height="200"
	resizeable="false" style="background-color:#DEEBF5;">
	
	<f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Unidad Ejecutora"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarUnidadEjecutoraEfectuado" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="formBuscarUnidadEjecutora">
    	<h:panelGroup>
    		<h:panelGrid id="panelSucursal" columns="10">
    			<rich:column width="100">
    				<h:outputText value="Sucursal"/>
    			</rich:column>
    			<rich:column width="100">
    				<h:selectOneMenu value="#{efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intSeguSucursalPk}" style="width:150px;">
    					<tumih:selectItems var="sel" value="#{efectuadoController.listaJuridicaSucursalDePPEjecutoraEfectuado}"
						itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
    				</h:selectOneMenu>
    			</rich:column>
    			<rich:column width="100">
    				<a4j:commandButton value="Buscar" action="#{efectuadoController.buscarUnidadEjecutora}"
     				  			styleClass="btnEstilos" reRender="tablaUnidadEjecutora" style="width:100px;"/>
    			</rich:column>
    		</h:panelGrid>
    		<h:panelGrid columns="2" id="tablaUnidadEjecutora">
    			<rich:dataTable var="item" id="tblResultadoUE"
    					 value="#{efectuadoController.listaBusquedaDePPEjecutoraEfectuado}" rowKeyVar="rowKey"
    					 rows="5" sortMode="single" width="590px">
    					 
    					 <rich:column width="30px">
					      		<h:outputText value="#{rowKey+1}"/>
					      	</rich:column>
      
					      <rich:column width="50px">
					          <f:facet name="header">
					         	 <h:outputText value="Código"/>
					          </f:facet>
					          	<h:outputText value="#{item.id.intCodigo}"/>
					      </rich:column>
      
					      <rich:column width="570px">
					          <f:facet name="header">
					          	<h:outputText value="Unidad Ejecutora"/>
					          </f:facet>
					          	<h:outputText value="#{item.juridica.strRazonSocial}"/>
					      </rich:column>
      
					      <rich:column width="70px">
					          <f:facet name="header">
					         	 <h:outputText value="Seleccionar"/>
					          </f:facet>
					          <a4j:commandLink value="Seleccionar"
					          	actionListener ="#{efectuadoController.seleccionarUnidadEjecutora}"
					          	oncomplete ="Richfaces.hideModalPanel('pBuscarUnidadEjecutoraEfectuado')"
					          	reRender ="panelValidarB,panelValidarA,contPanelInferior">
					          <f:attribute name = "item" value = "#{item}"/>
					          </a4j:commandLink>
					      </rich:column>
    					 <f:facet name="footer">
    					 	<rich:datascroller for="tblResultadoUE" maxPages="10"/>
    					 </f:facet>
    			</rich:dataTable>		 
    		</h:panelGrid>
    		
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>	