<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pBuscarCuentaBancariaOrden" width="650" height="270"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Cuenta Bancaria"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarCuentaBancariaOrden" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<h:form>
	   	<h:panelGroup>
	   	
	   		<h:panelGroup>
	   			<rich:dataTable
	    			var="item"
	                value="#{ordenController.ordenCompraNuevo.listaCuentaBancariaUsar}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="620px">
			        
					<rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Banco"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intBancoCod}"/>
                    </rich:column>
                    <rich:column width="200" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Numero de Cuenta"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strNroCuentaBancaria}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Moneda"/>
	                 	</f:facet>
			        	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intMonedaCod}"/>
			      	</rich:column>
					<rich:column width="110" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Tipo"/>
	                 	</f:facet>
			        	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTABANCARIA}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intTipoCuentaCod}"/>
			      	</rich:column>
					<rich:column width="80" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
	                 	<a4j:commandLink
			        		value="Seleccionar"
							actionListener="#{ordenController.seleccionarCuentaBancaria}"
							oncomplete="Richfaces.hideModalPanel('pBuscarCuentaBancariaOrden')"
							reRender="panelCuentasOC">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>	
