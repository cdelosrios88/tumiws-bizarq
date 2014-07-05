<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<rich:modalPanel id="pBuscarUnidadEjecutora" width="610" height="300"
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
           		<rich:componentControl for="pBuscarUnidadEjecutora" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    	<h:form>
    			<h:panelGroup>
    				<h:panelGrid id="panelSucursal" columns="10">
    					<rich:column width="100">
    						<h:outputText value="Sucursal"/>
    					</rich:column>
    					<rich:column width="100">
    						<h:selectOneMenu value="#{envioController.dtoFiltroDePPEjecutoraEnvio.juridicaSucursal.intIdPersona}" style="width:150px;">
    							<tumih:selectItems var="sel" value="#{envioController.listaJuridicaSucursalDePPEjecutoraEnvio}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
    						</h:selectOneMenu>
    					</rich:column>
    					<rich:column width="100">
    						<a4j:commandButton value="Buscar" actionListener="#{envioController.buscarUnidadEjecutora}"
     							styleClass="btnEstilos" reRender="tablaUnidadEjecutora" style="width:100px;"/>
    					</rich:column>
    				</h:panelGrid>
    				
    				<h:panelGrid columns="2" id="tablaUnidadEjecutora">
    					<rich:dataTable var="item" id="tblResultadoUnidadE"
    					 value="#{envioController.listaBusquedaDePPEjecutoraEnvio}" rowKeyVar="rowKey"
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
      
					      <rich:column width="580px">
					          <f:facet name="header">
					          	<h:outputText value="Unidad Ejecutora"/>
					          </f:facet>
					          	<h:outputText value="#{item.juridica.strRazonSocial}"/>
					      </rich:column>
      
					      <rich:column width="60px">
					          <f:facet name="header">
					         	 <h:outputText value="Seleccionar"/>
					          </f:facet>
					          <a4j:commandLink value="Seleccionar"
					          	actionListener="#{envioController.seleccionarUnidadEjecutora}"
					          	oncomplete="Richfaces.hideModalPanel('pBuscarUnidadEjecutora')"
					          	reRender="panelValidarB,panelValidarA,contPanelInferior">
					          <f:attribute name="item" value="#{item}"/>
					          </a4j:commandLink>
					      </rich:column>
    						<f:facet name="footer">
    							<rich:datascroller for="tblResultadoUnidadE" maxPages="10"/>
    						</f:facet>
    					</rich:dataTable>
    				</h:panelGrid>
    			</h:panelGroup>
    	</h:form>
</rich:modalPanel>