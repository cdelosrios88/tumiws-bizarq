<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- 
-----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-006       			01/11/2014     Christian De los Ríos        Se agregó un atributo al reRender de retorno
 -->

<rich:modalPanel id="pBuscarBancoCuentaConciliacion" width="730" height="290"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Banco Cuenta"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarBancoCuentaConciliacion" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	

	<h:form>
	    	<h:panelGrid columns="8">
	    			<rich:column width="160">
	    				<h:selectOneMenu style="width: 160px;" 
	    					value="#{conciliacionController.bancoCuentaFiltroConciliacion.bancofondo.intBancoCod}">
	    					<tumih:selectItems var="sel" 
	    						cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
	    				</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column width="50">
           				<h:outputText value="Periodo : "/>
           			</rich:column>
           			<rich:column width="80">
           				<h:inputText value="#{conciliacionController.bancoCuentaFiltroConciliacion.intPeriodocuenta}"
           					style="background-color: #BFBFBF;font-weight:bold;"
           					size="10"
           					readonly="true"/>           				
           			</rich:column>
	    			<rich:column width="90">
           				<h:outputText value="Número Cuenta :"/>
           			</rich:column>
	    			<rich:column width="170">
	    				<h:inputText
	    					value="#{conciliacionController.bancoCuentaFiltroConciliacion.strNumerocuenta}"
	    					size="25"/>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton action="#{conciliacionController.buscarBancoCuentaConciliacion}" 
	    					value="Buscar" 
	    					reRender="tablaBuscarBancoCuentaConciliacion" 
	    					styleClass="btnEstilos"
	    					style="width:100px">
	    				</a4j:commandButton>
	    			</rich:column>	    		
	    	</h:panelGrid>
		    	
	    	<h:panelGroup id="tablaBuscarBancoCuentaConciliacion">
	    		<rich:dataTable id="tblSeleccionCuentaConciliacion" 
	    			var="item" 
	                value="#{conciliacionController.listaBancoCuenta}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="710px">
			                           
					<rich:column width="30px">
			        	<h:outputText value="#{rowKey + 1}"/>
			  		</rich:column>			                    
			        <rich:column width="150" style="text-align: left ">
                    	<f:facet name="header">
                      		<h:outputText value="Banco"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_BANCOS}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.bancofondo.intBancoCod}"/>
                  	</rich:column>
			        <rich:column width="80">
	                	<f:facet name="header">
							<h:outputText value="Periodo"/>
	             		</f:facet>
						<h:outputText value="#{item.intPeriodocuenta}"/>
					</rich:column> 
					<rich:column width="200">
						<f:facet name="header">
							<h:outputText value="Nombre de Cuenta"/>
						</f:facet>
						<h:outputText value="#{item.strNombrecuenta}"/>
					</rich:column>					
					<rich:column width="150">
						<f:facet name="header">
							<h:outputText value="Número de cuenta"/>
						</f:facet>
						<h:outputText value="#{item.strNumerocuenta}"/>
					</rich:column>
					<rich:column width="100" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Seleccionar"/>
						</f:facet>
						<a4j:commandLink
							value="Seleccionar"
							actionListener="#{conciliacionController.seleccionarBancoCuentaConciliacion}" 
							reRender="contPanelInferior" 
							oncomplete="Richfaces.hideModalPanel('pBuscarBancoCuentaConciliacion')">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			     	</rich:column>			                    
					
					<f:facet name="footer">
						<h:panelGroup layout="block">
							<rich:datascroller for="tblSeleccionCuentaConciliacion" maxPages="10"/>
					   	</h:panelGroup>
					</f:facet>
	            </rich:dataTable>
	    	</h:panelGroup>
	</h:form>
	</rich:modalPanel>